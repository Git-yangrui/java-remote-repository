package com.citi.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

public class ByteBufferToCharView {
	public static void main(String[] argv) throws Exception {
		ByteBuffer byteBuffer = ByteBuffer.allocate(10).order(ByteOrder.BIG_ENDIAN);
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		byteBuffer.put(0, (byte) 0);
		byteBuffer.put(1, (byte) 'H');
		byteBuffer.put(2, (byte) 0);
		byteBuffer.put(3, (byte) 'i');
		byteBuffer.put(4, (byte) 0);
		byteBuffer.put(5, (byte) '!');
		byteBuffer.put(6, (byte) 0);
		println(byteBuffer);
		println(charBuffer);
		while(byteBuffer.hasRemaining()){
			System.out.println(byteBuffer.get());
		}
		while(charBuffer.hasRemaining()){
			System.out.println(charBuffer.get());
		}
	}

	private static void println(Buffer buffer) {
		System.out.println("pos=" + buffer.position() + ", limit=" + buffer.limit() + ", capacity=" + buffer.capacity()
				+ ": '" + buffer.toString() + "'");
	}
}
