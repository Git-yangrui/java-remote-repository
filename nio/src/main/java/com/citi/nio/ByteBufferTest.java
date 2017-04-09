package com.citi.nio;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ByteBufferTest {
  @Test
  public void test_bytebuffer(){
	  ByteBuffer byteBuffer=ByteBuffer.allocate(10);
	  byteBuffer.put((byte)'H').put((byte)'A').put((byte)'B').put((byte)'B').put((byte)'B');
	  int position = byteBuffer.position();
	  byteBuffer.flip();
	  int position1 = byteBuffer.position();
	  byte mybyteArray[] =new byte[5];
	  for(int i=0;byteBuffer.hasRemaining();i++){
		  mybyteArray[i]=byteBuffer.get();
	  }
	  System.out.println(mybyteArray.toString());
  }
}
