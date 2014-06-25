package netty.service.binary.server;

import java.util.Scanner;

import org.apache.log4j.Logger;

import netty.service.binary.handler.BinaryClientInboundHandler2;
import netty.service.binary.handler.BinaryClientOutboundHandler;
import netty.service.decoder.BinaryResponseDecoder;
import netty.service.encoder.BinaryRequestEncoder;
import netty.service.encoder.BinaryResponseEncoder;
import netty.service.message.BinaryRequestMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class BinaryClient {
	private Channel channel;
	private String host;
	private int port;
	EventLoopGroup workerGroup;
	Logger logger = Logger.getLogger(BinaryResponseEncoder.class);

	public BinaryClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public boolean connect() {// connect to server
		try {
			start();
			return true;
		} catch (Exception e) {
			logger.error("建立连接失败");
			e.printStackTrace();
			return false;
		}
	}

	public void write(BinaryRequestMessage msg) {// write msg
		try {
			if (channel.isOpen()) {
				ChannelFuture f = channel.writeAndFlush(msg);
			} else {
				System.out.println("消息发送失败，未建立连接");
			}

		} catch (Exception e) {
			logger.error("消息发送失败，未建立连接");
			e.printStackTrace();
		}
	}

	public boolean addInHandler(String name,
			ChannelInboundHandlerAdapter handler) {// add in handler
		try {
			channel.pipeline().addLast(name, handler);
			return true;

		} catch (Exception e) {
			logger.error("添加处理事件失败，未建立连接");
			e.printStackTrace();
			return false;
		}
	}

	public boolean addOutHandler(String name,
			ChannelOutboundHandlerAdapter handler) {// add in handler
		try {
			channel.pipeline().addLast(name, handler);
			return true;

		} catch (Exception e) {
			logger.error("添加处理事件失败，未建立连接");
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeHandler(String name) {// remove handler
		try {
			channel.pipeline().remove(name);
			return true;

		} catch (Exception e) {
			logger.error("添加处理事件失败，未建立连接");
			e.printStackTrace();
			return false;
		}
	}

	public void close() {// close the connection
		try {
			workerGroup.shutdownGracefully();
		} catch (Exception e) {
			logger.error("未建立连接");
			e.printStackTrace();
		}
	}

	private void start() throws Exception {// begin to connect !
		workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup).channel(NioSocketChannel.class).handler(
				new BinaryClientInitializer()).option(
				ChannelOption.SO_KEEPALIVE, true);

		channel = b.connect(host, port).sync().channel();

	}

	/*public static void main(String[] args) throws Exception {
		BinaryClient client = new BinaryClient("localhost", 8000);
		client.connect();
		BinaryRequestMessage obj = new BinaryRequestMessage();
		Scanner s = new Scanner(System.in);
		client.addInHandler("inHandler2", new BinaryClientInboundHandler2());
		while (s.hasNext()) {
			int i = s.nextInt();
			if (i == 1) {
				obj.setValue("name", "wrq");
				client.write(obj);
				Thread.sleep(2000);
			}
		}

	}*/
}
