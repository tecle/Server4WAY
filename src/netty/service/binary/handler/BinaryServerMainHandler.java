package netty.service.binary.handler;

import java.util.HashMap;
import java.util.Map;

import way.service.bean.User;
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
		System.out.println("read:" + (BinaryRequestMessage) msg);
		BinaryRequestMessage m = (BinaryRequestMessage) msg;
		BinaryResponseMessage response = new BinaryResponseMessage();
		Map<String, String> values = m.getValues();
		switch (m.getCommand()) {
		case MsgCode.LOGIN_CODE:// 登陆请求
			if (WayService.getService().login((String) values.get("username"),
					(String) values.get("password"))) {
				response.setResult(MsgCode.LOGIN_SUCCESS);
				response.setValues(WayService.getService().getUserByName(
						(String) values.get("username")));
			} else {
				response.setResult(MsgCode.LOGIN_FAILED);
			}
			break;
		case MsgCode.REGIST_CODE:// 注册请求
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
					response.setValue("id", WayService.getService()
							.getIdByName(values.get("username").toString()));
				} else {
					response.setResult(MsgCode.REGIST_FAILED);
				}
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
		case MsgCode.USER_SEARCH_CODE://搜索好友
			if (values.containsKey("id")) {//如果按id精确搜索
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
			} else if (values.containsKey("nickname")) {//如果按nick模糊搜索
				if (values.containsKey("start") && values.containsKey("limit")) {
					int start = Integer.parseInt(values.get("start"));
					int limit = Integer.parseInt(values.get("limit"));
					String list = WayService.getService().searchUsersByNick(
							values.get("nickname"), start, limit);
					if(list == null){
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
		}
		ctx.writeAndFlush(response);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state().equals(IdleState.READER_IDLE)) {
				System.out.println("READER_IDLE");
				// 超时关闭channel
				ctx.close();
			} else if (event.state().equals(IdleState.WRITER_IDLE)) {
				System.out.println("WRITER_IDLE");
				ctx.writeAndFlush("ping");
			}
		}
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}