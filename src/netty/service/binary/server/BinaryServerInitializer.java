package netty.service.binary.server;
import java.util.concurrent.TimeUnit;

import netty.service.binary.handler.BinaryServerHeartInHandler;
import netty.service.binary.handler.BinaryServerMainHandler;
import netty.service.binary.handler.BinaryServerOutboundHandler;
import netty.service.decoder.BinaryRequestDecoder;
import netty.service.encoder.BinaryResponseEncoder;
import netty.service.http.handler.HttpServerInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class BinaryServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("ping", new IdleStateHandler(180, 60, 0,TimeUnit.SECONDS));

		// server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码   
        ch.pipeline().addLast("encoder", new BinaryResponseEncoder());
        ch.pipeline().addLast("outHandler",new BinaryServerOutboundHandler());
        // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码 
        ch.pipeline().addLast("decoder", new BinaryRequestDecoder());  
        ch.pipeline().addLast("heart", new BinaryServerHeartInHandler());//心跳处理
        ch.pipeline().addLast("inHandler", new BinaryServerMainHandler());
        
	}  

}