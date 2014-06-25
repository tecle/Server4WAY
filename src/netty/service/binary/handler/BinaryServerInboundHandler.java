package netty.service.binary.handler;

import java.util.HashMap;
import java.util.Map;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class BinaryServerInboundHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("read:" + (BinaryRequestMessage) msg);
		BinaryResponseMessage response = new BinaryResponseMessage();
		response.setEncode((byte) 0);
		response.setEncrypt((byte) 0);
		response.setResult(((BinaryRequestMessage) msg).getCommand());
		ctx.write(response);
		// ctx.fireChannelRead(obj)
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