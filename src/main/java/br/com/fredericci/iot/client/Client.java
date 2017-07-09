package br.com.fredericci.iot.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import br.com.fredericci.iot.common.Message;

public class Client {

	EventLoopGroup workerGroup = new NioEventLoopGroup();
	private ChannelFuture channelFuture = null;

	public void connect(String host, Integer port) throws Exception {

		Bootstrap bootstrap = new Bootstrap(); // (1)
		bootstrap.group(workerGroup); // (2)
		bootstrap.channel(NioSocketChannel.class); // (3)
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new ObjectEncoder());
			}
		});

		// Start the client.
		channelFuture = bootstrap.connect(host, port).sync(); // (5)

	}
	
	public void send(Message message) {
		channelFuture.channel().writeAndFlush(message);
	}

	public void close() throws Exception {

		try {
			channelFuture.channel().close().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}

	}

}

