package com.citi.nio;

import java.nio.ByteOrder;
import java.nio.CharBuffer;

public class BufferMark {
 public static void main(String[] args) {
	CharBuffer charBuffer=CharBuffer.allocate(10);
	charBuffer.put('c').put('h').put('a').put('v').put('b').put('a');
	charBuffer.position(2).mark().position(4);
	while(charBuffer.hasRemaining()){
		System.out.println(charBuffer.get());
	}
	
	System.out.println("----------------------");
	charBuffer.reset();
	while(charBuffer.hasRemaining()){
		System.out.println(charBuffer.get());
	}
	System.out.println("-----------");
	ByteOrder nativeOrder = ByteOrder.nativeOrder();
}
}
