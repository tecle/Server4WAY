package netty.service.http.server;
import netty.service.http.handler.HttpServerInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码   
        ch.pipeline().addLast("encoder", new HttpResponseEncoder());  
        // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码 
        ch.pipeline().addLast("decoder", new HttpRequestDecoder());  
        ch.pipeline().addLast("handler", new HttpServerInboundHandler());
        
	}  

}