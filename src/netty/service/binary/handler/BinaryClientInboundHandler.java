package netty.service.binary.handler;

import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BinaryClientInboundHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("read:" + (BinaryResponseMessage) msg);
		//ctx.close();
		ctx.fireChannelRead(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}