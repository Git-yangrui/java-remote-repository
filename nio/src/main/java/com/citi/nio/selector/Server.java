package com.citi.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
	
	private ServerSocketChannel serverSocketChannel;
	private ServerSocket serverSocket;
    private Selector selector;
	public void init(){
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocket=serverSocketChannel.socket();
			serverSocket.bind(new InetSocketAddress(8888));
			selector=Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
		   throw new RuntimeException(e);
		}
	}
	
	public void startServer() throws Exception{
		init();
		while(true){
			int select = selector.select();
			if(select==0){
				continue;
			}
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while(iterator.hasNext()){
				SelectionKey selectionKey = iterator.next();
				if(selectionKey.isAcceptable()){
					SocketChannel acceptChannel = serverSocketChannel.accept();
					acceptChannel.register(selector, SelectionKey.OP_READ);
				}else if(selectionKey.isReadable()){
					SocketChannel channel = (SocketChannel)selectionKey.channel();
					ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
					while(channel.read(byteBuffer)>0){
						byteBuffer.flip();
						while(byteBuffer.hasRemaining()){
						    byteBuffer.get();
						}
					}
				}else if(selectionKey.isWritable()){
					SocketChannel channel = (SocketChannel)selectionKey.channel();
					ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
					byteBuffer.put("hello nihao".getBytes());
					channel.write(byteBuffer);
				}
			}
		}
		
	}
}
