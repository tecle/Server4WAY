package netty.service.http.server;

import io.netty.bootstrap.ServerBootstrap;  
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelOption;  
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.nio.NioServerSocketChannel;  
  
public class HttpServer {  
    public void start(int port) throws Exception {  
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)   
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap(); // (2)   
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)   
                    .childHandler(new HttpServerInitializer()).option(ChannelOption.SO_BACKLOG, 128) // (5)   
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)   
  
            ChannelFuture f = b.bind(port).sync(); // (7)   
  
            f.channel().closeFuture().sync();  
        } finally {  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    }  
  
   /* public static void main(String[] args) throws Exception {  
        HttpServer server = new HttpServer();  
        server.start(8000);  
    } */ 
}  
