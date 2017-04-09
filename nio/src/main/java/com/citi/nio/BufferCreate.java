package com.citi.nio;

import java.nio.CharBuffer;

public class BufferCreate {
  public static void main(String[] args) {
	  char [] myArray = new char [10]; 
	  //CharBuffer.allocate(10);
	  CharBuffer charbuffer = CharBuffer.wrap (myArray,1,myArray.length-2);
	  charbuffer.put('s');
	  charbuffer.put('s');
	  //[, s, s, , , , , , ,]
	  System.out.println(myArray);
}
}
