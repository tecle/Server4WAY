package netty.service.binary.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import way.service.channel.ClientChannels;
import way.service.logic.WayService;
import way.service.util.MsgCode;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BinaryServerChatHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		BinaryRequestMessage m = (BinaryRequestMessage) msg;
		BinaryResponseMessage response = new BinaryResponseMessage();
		Map<String, String> values = m.getValues();
		switch (m.getCommand()) {
		case MsgCode.SEND_CHAT:// 更新自己的位置
			if (values.containsKey("id") && values.containsKey("nick")
					&& values.containsKey("fid")
					&& values.containsKey("message")) {
				String fid = values.get("fid");
				BinaryResponseMessage message = new BinaryResponseMessage(
						MsgCode.PUSH_CHAT);
				message.setValue("nick", values.get("nick"));
				message.setValue("message", values.get("message"));
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String dateStr = sdf.format(date);
				message.setValue("date", dateStr);
				WayService.getService().pushToId(fid, message);
				response.setResult(MsgCode.SEND_CHAT_SUCCESS);
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		default:
			ctx.fireChannelRead(msg);
			return;
		}
		System.out.println("read:chat" + (BinaryRequestMessage) msg);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}