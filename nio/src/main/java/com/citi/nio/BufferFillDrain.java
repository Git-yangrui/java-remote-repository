package com.citi.nio;

import java.nio.CharBuffer;

public class BufferFillDrain {
	
	public static void main(String[] args) {
		CharBuffer charBuffer=CharBuffer.allocate(1000);
		while(fillBuffer(charBuffer)){
			charBuffer.flip();
			drainBuffer(charBuffer);
			charBuffer.clear();
		}
	}
	private static void drainBuffer(CharBuffer buffer) {
		while (buffer.hasRemaining()) {
			System.out.println(buffer.get());
		}
		System.out.println("-------------------");
	}

	private static boolean fillBuffer(CharBuffer buffer) {
        if(index>=strings.length){
        	return false;
        }
        String string=strings[index++];
        for (int i = 0; i < string.length(); i++) {
			buffer.put(string.charAt(i));
		}
		return true;
	}

	private static int index = 0;
	private static String[] strings = { "A random string value", "The product of an infinite number of monkeys",
			"Hey hey we're the Monkees", "Opening act for the Monkees: Jimi Hendrix",
			"'Scuse me while I kiss this fly", };
}
