package com.citi.nio.mapper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapperFile {
	public static void main(String[] args) throws Exception {
		File tempFile = File.createTempFile("mmaptest", null);
		RandomAccessFile file = new RandomAccessFile(tempFile, "rw");
		FileChannel channel = file.getChannel();
		ByteBuffer temp = ByteBuffer.allocate(100);
		temp.put("This is the file content".getBytes());
		temp.flip();
		channel.write(temp, 0);
		temp.clear( );
		temp.put ("This is more file content".getBytes( )); 
		temp.flip( ); 
		channel.write (temp, 8192); 
		MappedByteBuffer ro = channel.map ( FileChannel.MapMode.READ_ONLY, 0, channel.size( )); 
		MappedByteBuffer rw = channel.map ( FileChannel.MapMode.READ_WRITE, 0, channel.size( )); 
		MappedByteBuffer cow = channel.map ( FileChannel.MapMode.PRIVATE, 0, channel.size( )); 
		System.out.println ("Begin"); 
		showBuffers (ro, rw, cow); 
		cow.put ("COW".getBytes( )); 
		System.out.println ("Change to COW buffer"); 
		showBuffers (ro, rw, cow);
	}

	private static void showBuffers(MappedByteBuffer ro, MappedByteBuffer rw, MappedByteBuffer cow) {
		dumpBuffer ("R/O", ro); 
		dumpBuffer ("R/W", rw); 
		dumpBuffer ("COW", cow);
		System.out.println (""); 
		
	}

	private static void dumpBuffer(String prefix, MappedByteBuffer buffer) {
		System.out.print(prefix + "   :   '");
		int nulls = 0;
		int limit = buffer.limit();
		for (int i = 0; i < limit; i++) {
			char c = (char) buffer.get(i);
			if (c == '\u0000') {
				nulls++;
				continue;
			}
			if (nulls != 0) {
				System.out.print(" [[ " + nulls + " nulls ]] ");
				nulls = 0;
			}
			System.out.print(c);
		}
		System.out.println("'");

	}
}
