package netty.service.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import netty.service.message.BinaryResponseMessage;
import netty.service.util.BinaryUtil;

import org.apache.log4j.Logger;

/**
 * response（服务器）编码器
 */
public class BinaryResponseEncoder extends ChannelOutboundHandlerAdapter {
	Logger logger = Logger.getLogger(BinaryResponseEncoder.class);

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		BinaryResponseMessage response = (BinaryResponseMessage) msg;
		ByteBuf headBuffer = Unpooled.buffer(16);
		/** */
		/**
		 * 先组织报文头
		 */
		headBuffer.writeByte(response.getEncode());
		headBuffer.writeByte(response.getEncrypt());
		headBuffer.writeByte(response.getExtend1());
		headBuffer.writeByte(response.getExtend2());
		headBuffer.writeInt(response.getSessionid());
		headBuffer.writeInt(response.getResult());

		/** */
		/**
		 * 组织报文的数据部分
		 */
		ByteBuf dataBuffer = BinaryUtil.encode(response.getEncode(), response
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
		System.err.println("response encoder:" + totalBuffer);
		ctx.writeAndFlush(totalBuffer);
	}

}
