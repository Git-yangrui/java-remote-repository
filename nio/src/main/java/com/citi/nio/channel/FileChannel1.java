package com.citi.nio.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannel1 {
	public static void main(String[] args) throws Exception {
		RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\blahblah.txt", "r");
		long length = randomAccessFile.length();
		System.out.println(length);
		// Set the file position
		randomAccessFile.seek(1000); // Create a channel from the file
		FileChannel fileChannel = randomAccessFile.getChannel();
		// This will print "1000"
		System.out.println("file pos: " + fileChannel.position());
		// Change the position using the RandomAccessFile object
		randomAccessFile.seek(500);
		// This will print "500"
		System.out.println("file pos: " + fileChannel.position());
		// Change the position using the FileChannel object
		fileChannel.position(200);
		System.out.println(fileChannel.size());
		// This will print "200"
		System.out.println("file pos: " + randomAccessFile.getFilePointer());
	}
}
