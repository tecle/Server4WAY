package netty.service.binary.server;

import io.netty.bootstrap.ServerBootstrap;  
import io.netty.channel.ChannelFuture;  
import io.netty.channel.ChannelOption;  
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.nio.NioServerSocketChannel;  
  
public class BinaryServer {  //netty服务器
    public void start(int port) throws Exception {  
        EventLoopGroup bossGroup = new NioEventLoopGroup();   
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap b = new ServerBootstrap();    
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)   
                    .childHandler(new BinaryServerInitializer()).option(ChannelOption.SO_BACKLOG, 128) // (5)   
                    .childOption(ChannelOption.SO_KEEPALIVE, true);    
            System.out.println("server start up.....");
            ChannelFuture f = b.bind(port).sync();   
  
            f.channel().closeFuture().sync();  
            
        } finally {  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
    }  
  
    public static void main(String[] args) throws Exception {  
    	BinaryServer server = new BinaryServer();  
        server.start(8000);  
    }  
}  
