package netty.service.binary.handler;

import java.util.Map;

import way.service.channel.ClientChannels;
import way.service.logic.WayService;
import way.service.util.MsgCode;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BinaryServerPositionHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("read:position" + (BinaryRequestMessage) msg);
		BinaryRequestMessage m = (BinaryRequestMessage) msg;
		BinaryResponseMessage response = new BinaryResponseMessage();
		Map<String, String> values = m.getValues();
		switch (m.getCommand()) {
		case MsgCode.UPDATE_POSITION:// 更新自己的位置
			if (values.containsKey("id") && values.containsKey("longi")
					&& values.containsKey("lati")) {
				if (WayService.getService().updatePosition(values.get("id"),
						values.get("longi"), values.get("lati"))) {
					response.setResult(MsgCode.UPDATE_POSITION_SUCCESS);
				} else {
					response.setResult(MsgCode.UPDATE_POSITION_FAILED);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		case MsgCode.GET_POSITION:// 获取好友位置
			if (values.containsKey("id")) {
				Map pos = WayService.getService().getPosition(values.get("id"));
				if(null == pos){
					response.setResult(MsgCode.GET_POSITION_FAILED);
				} else{
					response.setValues(pos);
					response.setResult(MsgCode.GET_POSITION_SUCCESS);
				}
			} else {
				response.setResult(MsgCode.WRONG_PARAMETER);
			}
			break;
		default:
			ctx.fireChannelRead(msg);
			return;
		}
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}