package netty.service.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import netty.service.util.BinaryUtil;

import org.apache.log4j.Logger;

/**
 * request（客户端）编码器
 */
public class BinaryRequestEncoder extends ChannelOutboundHandlerAdapter {
	Logger logger = Logger.getLogger(BinaryRequestEncoder.class);
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		BinaryRequestMessage request = (BinaryRequestMessage) msg;
		ByteBuf headBuffer = Unpooled.buffer(16);
		/** */
		/**
		 * 先组织报文头
		 */
		headBuffer.writeByte(request.getEncode());
		headBuffer.writeByte(request.getEncrypt());
		headBuffer.writeByte(request.getExtend1());
		headBuffer.writeByte(request.getExtend2());
		headBuffer.writeInt(request.getSessionid());
		headBuffer.writeInt(request.getCommand());

		/** */
		/**
		 * 组织报文的数据部分
		 */
		ByteBuf dataBuffer = BinaryUtil.encode(request.getEncode(), request
				.getValues());
		int length = dataBuffer.readableBytes();
		headBuffer.writeInt(length);
		/** */
		/**
		 * 非常重要 ByteBuffer需要手动flip()，ChannelBuffer不需要
		 */

		ByteBuf totalBuffer = Unpooled.buffer(16 + length);
		totalBuffer.writeBytes(headBuffer);
		logger.info("totalBuffer size=" + totalBuffer.readableBytes());
		totalBuffer.writeBytes(dataBuffer);
		logger.info("totalBuffer size=" + totalBuffer.readableBytes());
		System.err.println("request encode :" + totalBuffer);
		ctx.writeAndFlush(totalBuffer);
	}

}
