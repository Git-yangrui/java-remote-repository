package com.citi.nio.mapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class MapperHttp {
	private static final String OUTPUT_FILE = "MapperHttp.out";
	private static final String LINE_SEP = "\r\n";
	private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
	private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
	private static final String HTTP_404_HDR = "HTTP/1.0 400 NOT FOUND" + LINE_SEP + SERVER_ID + LINE_SEP;
	private static final String MSG_404 = "Could not open file :";

	private static byte[] bytes(String string)  {
		try {
			return (string.getBytes("US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args){
		String file = "test.txt";
		ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
		ByteBuffer dynhdrs = ByteBuffer.allocate(128);
		ByteBuffer[] gather = { header, dynhdrs, null };
		String contentType = "unknown/unknown";
		long contentLength = -1;
		try {
			FileInputStream fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			MappedByteBuffer filedata = fc.map(MapMode.READ_ONLY, 0, fc.size());
			gather[2] = filedata;
			contentLength = fc.size();
			contentType = URLConnection.guessContentTypeFromName(file);
			System.out.println(contentType);
		} catch (IOException e) {
			ByteBuffer buf = ByteBuffer.allocate(128);
			String msg = MSG_404 + e + LINE_SEP;
			buf.put(bytes(msg));
			buf.flip();
			gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
			gather[2] = buf;
			contentLength = msg.length();
			contentType = "text/plain";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("Content-Length: " + contentLength);
		sb.append(LINE_SEP);
		sb.append("Content-Type: ").append(contentType);
		sb.append(LINE_SEP).append(LINE_SEP);
		dynhdrs.put(bytes(sb.toString()));
		dynhdrs.flip();
		try {
			FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
			FileChannel out = fos.getChannel();
			while (out.write(gather) > 0){
				
			}
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println("output written to " + OUTPUT_FILE);
	}

}
