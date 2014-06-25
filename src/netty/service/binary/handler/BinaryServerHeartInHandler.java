package netty.service.binary.handler;

import netty.service.message.BinaryRequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BinaryServerHeartInHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (((BinaryRequestMessage) msg).getCommand() == -1) {
			System.out.println("HeartBreath");
			/**
			 *  需要刷新超时定时器
			 */
		} else {
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}