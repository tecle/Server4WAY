package netty.service.binary.handler;

import java.util.HashMap;
import java.util.Map;

import way.service.bean.User;
import way.service.channel.ClientChannels;
import way.service.logic.WayService;
import way.service.util.MsgCode;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class BinaryServerMainHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		BinaryRequestMessage m = (BinaryRequestMessage) msg;
		BinaryResponseMessage response = new BinaryResponseMessage();
		Map<String, String> values = m.getValues();
		switch (m.getCommand()) {
		case MsgCode.LOGIN_CODE:// 登陆请求
			if (values.containsKey("username")
					&& values.containsKey("password")) {
				if (WayService.getService().login(
						(String) values.get("username"),
						(String) values.get("password"))) {
					response.setResult(MsgCode.LOGIN_SUCCESS);
					Map<String, String> user = WayService.getService()
							.getUserByName((String) values.get("username"));
					response.setValues(user);
					ClientChannels.setChannel(user.get("id"), ctx.channel());
					if (null != WayService.getService().getFriendREQS(
							user.get("id"), 0, 1)) {
						response.setExtend1((byte) 1);
					}
					if (WayService.getService().getActivityInvites(
							user.get("id")).size() > 0) {
						response.setExtend2((byte) 1);
					}
				} else {
					response.setResult(MsgCode.LOGIN_FAILED);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.REGIST_CODE:// 注册请求
			if (values.containsKey("username")
					&& values.containsKey("password")
					&& values.containsKey("nickname")
					&& values.containsKey("sex")) {
				if (WayService.getService().userExists(
						(String) values.get("username"))) {
					response.setResult(MsgCode.REGIST_FAILED);
				} else {
					User user = new User();
					user.setName(values.get("username").toString());
					user.setPw(values.get("password").toString());
					user.setNick(values.get("nickname").toString());
					user.setSex(values.get("sex").charAt(0));
					if (WayService.getService().addUser(user)) {
						response.setResult(MsgCode.REGIST_SUCCESS);
						response
								.setValue("id", WayService.getService()
										.getIdByName(
												values.get("username")
														.toString()));
					} else {
						response.setResult(MsgCode.REGIST_FAILED);
					}
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.USER_CHECK_CODE:// 查重请求
			if (WayService.getService().userExists(
					(String) values.get("username"))) {
				response.setResult(MsgCode.USER_EXISTED);
			} else {
				response.setResult(MsgCode.USER_NOT_EXISTED);
			}
			break;
		case MsgCode.USER_SEARCH_CODE:// 搜索好友
			if (values.containsKey("id") && values.containsKey("self")
					&& !values.get("id").equals(values.get("self"))) {// 如果按id精确搜索
				Map user = WayService.getService().getUserById(
						Integer.valueOf((String) values.get("id")));
				if (user == null) {
					response.setResult(MsgCode.USER_SEARCH_EMPTY);
				} else {
					String list = "[{'nick':'" + user.get("nick") + "','id':'"
							+ user.get("id") + "'}]";
					response.setValue("users", list);
					response.setResult(MsgCode.USER_SEARCH_SUCCESS);
				}
			} else if (values.containsKey("nickname")) {// 如果按nick模糊搜索
				if (values.containsKey("start") && values.containsKey("limit")
						&& values.containsKey("self")) {
					int start = Integer.parseInt(values.get("start"));
					int limit = Integer.parseInt(values.get("limit"));
					String list = WayService.getService().searchUsersByNick(
							values.get("nickname"), start, limit,
							values.get("self"));
					if (list == null) {
						response.setResult(MsgCode.USER_SEARCH_EMPTY);
					} else {
						response.setValue("users", list);
						response.setResult(MsgCode.USER_SEARCH_SUCCESS);
					}
				} else {
					response.setResult(MsgCode.WRONG_PARAMETER);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.GET_USER_INFO:// 获取好友信息
			if (values.containsKey("id")) {
				Map user = WayService.getService().getUserById(
						Integer.valueOf((String) values.get("id")));
				if (user == null) {
					response.setResult(MsgCode.GET_USER_INFO_FAILED);
				} else {
					response.setValues(user);
					response.setResult(MsgCode.GET_USER_INFO_SUCCESS);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		default:
			ctx.fireChannelRead(msg);
			return;
		}
		System.out.println("read:main" + (BinaryRequestMessage) msg);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}