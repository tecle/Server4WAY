package netty.service.binary.server;
import netty.service.binary.handler.BinaryClientHeartInHandler;
import netty.service.binary.handler.BinaryClientInHandler;
import netty.service.binary.handler.BinaryClientOutboundHandler;
import netty.service.decoder.BinaryResponseDecoder;
import netty.service.encoder.BinaryRequestEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class BinaryClientInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码   
        ch.pipeline().addLast("encoder", new BinaryRequestEncoder());
        ch.pipeline().addLast("outHandler",new BinaryClientOutboundHandler());
        // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码 
        ch.pipeline().addLast("decoder", new BinaryResponseDecoder());  
        ch.pipeline().addLast("heart", new BinaryClientHeartInHandler());//心跳处理
        ch.pipeline().addLast("display", new BinaryClientInHandler());
	}  
}