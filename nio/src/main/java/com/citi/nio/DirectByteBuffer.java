package com.citi.nio;

import java.nio.ByteBuffer;

import org.junit.Test;

public class DirectByteBuffer {
 @Test
 public void test_DirectByteBuffer(){
	 ByteBuffer.allocateDirect(10);
 }
}
