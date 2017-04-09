package com.citi.nio;

import java.nio.CharBuffer;

public class BufferGetsAndPuts {
	public static void main(String[] args) {
		//gets();
		puts();
	}

	private static void gets() {
		CharBuffer charBuffer = CharBuffer.allocate(10);
		charBuffer.put('c').put('h').put('a').put('v').put('b').put('a').put('s').put('a').put('a').put('a');
		char[] bigArray = new char[10];
		// charBuffer.limit(4);
		charBuffer.flip();
		charBuffer.get(bigArray, 0, charBuffer.remaining());
		System.out.println("-------------");
		System.out.print(bigArray);
	}
	
	private static void puts() {
		CharBuffer charBuffer = CharBuffer.allocate(10);
		char[] bigArray = new char[10];
		bigArray[0]='a';
		bigArray[1]='a';
		bigArray[2]='a';
		bigArray[3]='a';
		bigArray[4]='a';
		charBuffer.put(bigArray,0,bigArray.length);
		// charBuffer.limit(4);
		charBuffer.flip();
		charBuffer.get(bigArray, 0, charBuffer.remaining());
		System.out.println("-------------");
		System.out.print(bigArray);
	}
}
