package netty.service.binary.handler;

import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BinaryClientHeartInHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (((BinaryResponseMessage) msg).getResult() == -1) {
			ctx.writeAndFlush("ping");
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