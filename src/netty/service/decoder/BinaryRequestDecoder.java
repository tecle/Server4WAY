package netty.service.decoder;

import java.util.List;

import netty.service.message.BinaryRequestMessage;
import netty.service.message.BinaryResponseMessage;
import netty.service.util.BinaryUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;


/** 
 * resquest（服务端）解码器
 */
public class BinaryRequestDecoder extends ByteToMessageDecoder  {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer,
			List<Object> out) throws Exception {
    	System.out.println(buffer);
        if (buffer.readableBytes()<16) {
            return;
        }
        buffer.markReaderIndex();
        byte encode=buffer.readByte();
        byte encrypt=buffer.readByte();
        byte extend1=buffer.readByte();
        byte extend2=buffer.readByte();
        int sessionid=buffer.readInt();
        int command=buffer.readInt();
        int length=buffer.readInt(); // 数据包长
        if (buffer.readableBytes()<length) {
            buffer.resetReaderIndex();
            return;
        }
        ByteBuf dataBuffer=Unpooled.buffer(length);
        buffer.readBytes(dataBuffer, length);
        
        BinaryRequestMessage request=new BinaryRequestMessage();
        request.setEncode(encode);
        request.setEncrypt(encrypt);
        request.setExtend1(extend1);
        request.setExtend2(extend2);
        request.setSessionid(sessionid);
        request.setCommand(command);
        request.setLength(length);
        request.setValues(BinaryUtil.decode(encode, dataBuffer));
        request.setIp(BinaryUtil.getClientIp(ctx));
        System.out.println("reques decoder");
        out.add(request);
    }

	

	

}