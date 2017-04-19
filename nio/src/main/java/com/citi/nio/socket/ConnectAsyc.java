package com.citi.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

public class ConnectAsyc {
	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 1234;
		InetSocketAddress addr = new InetSocketAddress(host, port);
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		System.out.println("initiating connection");
		sc.connect(addr);
		while (!sc.finishConnect()) {
			doSomethingUseful();
		}
		ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
		byteBuffer1.put("initiating connection".getBytes());
		byteBuffer1.flip();
		sc.write(byteBuffer1);
		System.out.println ("connection established"); 
		sc.close( );
//		ThreadPoolExecutor 
	}

	private static void doSomethingUseful() {
		System.out.println ("doing something useless"); 
		
	}
}
