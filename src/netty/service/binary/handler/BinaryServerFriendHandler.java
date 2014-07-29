package netty.service.binary.handler;

import java.util.Map;

import way.service.channel.ClientChannels;
import way.service.logic.WayService;
import way.service.util.MsgCode;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BinaryServerFriendHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		BinaryRequestMessage m = (BinaryRequestMessage) msg;
		BinaryResponseMessage response = new BinaryResponseMessage();
		Map<String, String> values = m.getValues();
		switch (m.getCommand()) {
		case MsgCode.ADD_FRIEND:// 添加好友申请
			if (values.containsKey("id") && values.containsKey("fid")) {
				if (!(WayService.getService().isFriend(values.get("id")
						.toString(), values.get("fid").toString()))
						&& WayService.getService().addFriendREQ(
								values.get("id").toString(),
								values.get("fid").toString())) {
					response.setResult(MsgCode.ADD_FRIEND_SUCCESS);
					Map<String, String> user = WayService.getService().getUserById(
							Integer.parseInt(values.get("fid")));
					response.setValue("nick", user.get("nick"));
					response.setValue("id", user.get("id"));
					WayService.getService().pushToId(
							values.get("fid").toString(),
							new BinaryResponseMessage(MsgCode.PUSH_FRIEND_REQ));
				} else {
					response.setResult(MsgCode.ADD_FRIEND_FAILED);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.GET_FRIEND_REQ:// 获取好友申请
			if (values.containsKey("id") && values.containsKey("start")
					&& values.containsKey("limit")) {
				String list = WayService.getService().getFriendREQS(
						values.get("id").toString(),
						Integer.valueOf(values.get("start")),
						Integer.valueOf(values.get("limit")));
				if (null == list) {
					response.setResult(MsgCode.GET_FRIEND_REQ_FAILED);
				} else {
					response.setValue("users", list);
					response.setResult(MsgCode.GET_FRIEND_REQ_SUCCESS);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.OPERATE_FRIEND_REQ:// 接收或忽略好友请求
			if (values.containsKey("id") && values.containsKey("fid")
					&& values.containsKey("accept")) {
				int accept = Integer.parseInt(values.get("accept"));
				if (accept == 1) {
					WayService.getService().addFriend(
							values.get("id").toString(),
							values.get("fid").toString());
				}
				WayService.getService().deleteFriendREQ(
						values.get("id").toString(),
						values.get("fid").toString());
				response.setResult(accept == 1 ? MsgCode.OPERATE_FRIEND_REQ_ADD
						: MsgCode.OPERATE_FRIEND_REQ_IGNORE);
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.DELETE_FRIEND:// 删除好友
			if (values.containsKey("id") && values.containsKey("fid")) {
				if (WayService.getService().isFriend(
						values.get("id").toString(),
						values.get("fid").toString())) {
					if (WayService.getService().deleteFriend(
							values.get("id").toString(),
							values.get("fid").toString())) {
						response.setResult(MsgCode.DELETE_FRIEND_SUCESS);
					} else {
						response.setResult(MsgCode.DELETE_FRIEND_FAILED);
					}
				} else {
					response.setResult(MsgCode.DELETE_FRIEND_SUCESS);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.GET_FRIENDS:// 获取好友列表
			if (values.containsKey("id") && values.containsKey("start")
					&& values.containsKey("limit")) {
				String list = WayService.getService().getFriends(
						values.get("id"), Integer.valueOf(values.get("start")),
						Integer.valueOf(values.get("limit")));
				if (null == list) {
					response.setResult(MsgCode.GET_FRIENDS_FAILED);
				} else {
					response.setValue("users", list);
					response.setResult(MsgCode.GET_FRIENDS_SUCCESS);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		default:
			ctx.fireChannelRead(msg);
			return;
		}
		System.out.println("read:friend" + (BinaryRequestMessage) msg);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}