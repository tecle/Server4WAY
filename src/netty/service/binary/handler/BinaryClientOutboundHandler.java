package netty.service.binary.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import netty.service.message.BinaryRequestMessage;
import netty.service.util.BinaryUtil;

public class BinaryClientOutboundHandler extends ChannelOutboundHandlerAdapter {
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) {
		BinaryRequestMessage m;
		if (msg.equals(BinaryUtil.PING)) {
			m = new BinaryRequestMessage();
			m.setCommand(-1);
		} else {
			m = (BinaryRequestMessage) msg;
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