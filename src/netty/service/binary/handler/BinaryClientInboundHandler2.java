package netty.service.binary.handler;

import java.util.HashMap;
import java.util.Map;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BinaryClientInboundHandler2 extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("read2:" + (BinaryResponseMessage) msg);
		//ctx.close();
		ctx.fireChannelRead(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}