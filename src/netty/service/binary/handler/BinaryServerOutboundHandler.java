package netty.service.binary.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;

public class BinaryServerOutboundHandler extends ChannelOutboundHandlerAdapter {
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) {
		BinaryResponseMessage m;
		if (msg.toString().equals("ping")) {
			m = new BinaryResponseMessage();
			m.setResult(-1);
		} else {
			m = (BinaryResponseMessage) msg;
		}
		System.out.println("write msg:" + m.toString());
		ctx.write(m);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}