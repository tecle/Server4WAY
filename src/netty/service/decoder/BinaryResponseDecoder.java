package netty.service.decoder;

import java.util.List;

import netty.service.message.BinaryResponseMessage;
import netty.service.util.BinaryUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


/** 
 * response（客户端）解码器
 */
public class BinaryResponseDecoder extends ByteToMessageDecoder  {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
			List<Object> out) throws Exception {
        if (buffer.readableBytes()<16) {
            return;
        }
        buffer.markReaderIndex();
        byte encode=buffer.readByte();
        byte encrypt=buffer.readByte();
        byte extend1=buffer.readByte();
        byte extend2=buffer.readByte();
        int sessionid=buffer.readInt();
        int result=buffer.readInt();
        int length=buffer.readInt(); // 数据包长
        if (buffer.readableBytes()<length) {
            buffer.resetReaderIndex();
            return;
        }
        ByteBuf dataBuffer=Unpooled.buffer(length);
        buffer.readBytes(dataBuffer, length);
        
        BinaryResponseMessage response=new BinaryResponseMessage();
        response.setEncode(encode);
        response.setEncrypt(encrypt);
        response.setExtend1(extend1);
        response.setExtend2(extend2);
        response.setSessionid(sessionid);
        response.setResult(result);
        response.setLength(length);
        response.setValues(BinaryUtil.decode(encode, dataBuffer));
        response.setIp(BinaryUtil.getClientIp(ctx));
        System.out.println("response decoder");
        out.add(response);
    }

	

	

}