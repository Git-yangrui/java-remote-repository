package com.citi.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelAccept {
//	private static final String GREETING = "Hello I must be going .\r\n";

	public static void main(String[] args) throws Exception {
		int port = 1234;
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		while (true) {
			System.out.println("Waiting for connections");
			SocketChannel sChannel = serverSocketChannel.accept();
			if (sChannel == null) {
				Thread.sleep(2000);
			} else {
				System.out.println("Incoming connection from:" + sChannel.socket().getRemoteSocketAddress());
				sChannel.read(byteBuffer);
				sChannel.close();
				byteBuffer.flip();
				while(byteBuffer.hasRemaining()){
					System.out.print((char)byteBuffer.get());
				}
				System.out.println("------------------");
			}

		}
	}
}
