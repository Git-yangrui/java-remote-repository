package com.yangrui.hadoop.hdfs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;

public class HdfsClient {
	
	private FileSystem fs = null;
	
	@Before
	public void getFs() throws IOException{
		
		//get a configuration object
		Configuration conf = new Configuration();
		//to set a parameter, figure out the filesystem is hdfs
		conf.set("fs.defaultFS", "hdfs://192.168.1.100:9000/");
		conf.set("dfs.replication","1");
		
		//get a instance of HDFS FileSystem Client
		fs = FileSystem.get(conf);
		
	}
	
	
	@Test
	public void testDownload() throws IllegalArgumentException, IOException{
		
		FSDataInputStream is = fs.open(new Path("hdfs://192.168.1.100:9000/jdk.tgz"));
		
		FileOutputStream os = new FileOutputStream("/home/hadoop/jdk.download");
		
		IOUtils.copy(is, os);
		
		
	}
	
	
	
	//upload a local file to hdfs
	public static void main(String[] args) throws IOException {
		
		//get a configuration object
		Configuration conf = new Configuration();
		//to set a parameter, figure out the filesystem is hdfs
		conf.set("fs.defaultFS", "hdfs://yun12-01:9000/");
		conf.set("dfs.replication","1");
		
		//get a instance of HDFS FileSystem Client
		FileSystem fs = FileSystem.get(conf);
		
		//open a outputstream of the dest file
		Path destFile = new Path("hdfs://yun12-01:9000/jdk.tgz");
		FSDataOutputStream os = fs.create(destFile);
		
		//open a inputstream of the local source file
		FileInputStream is = new FileInputStream("/home/hadoop/jdk-7u65-linux-i586.tar.gz");
		
		//write the bytes in "is" to "os"
		IOUtils.copy(is, os);
		
		
	}

}
