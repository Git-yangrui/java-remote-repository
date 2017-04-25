package com.newtouc.threadGuardedSuspend.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TestSetToReader {
  private Set<String> set=new HashSet<String>();
  
   public BufferedReader makeRederFrom(final Set<String> log){
	BufferedReader logFile;
	InputStream in=new SequenceInputStream(new Enumeration<InputStream>() {
		private Iterator<String> iterator=log.iterator();
		@Override
		public boolean hasMoreElements() {
			
			return iterator.hasNext();
		}
		
		@Override
		public InputStream nextElement() {
			String fileName=iterator.next();
			InputStream in=null;
			try{
				in.read(fileName.getBytes());
			}catch(Exception e){
				System.out.println("cuolelelelelele");
				e.printStackTrace();
			}
			return in;
		}
	});
	logFile=new BufferedReader(new InputStreamReader(in));
	return logFile;
	   
   }
   
   public static void main(String[] args) {
	   TestSetToReader reader=new TestSetToReader();
	   Set<String> set2 = reader.set;
	   set2.add("2313131");
	   set2.add("21313231313131");
	   set2.add("23131eqeqeqeqe31");
	   set2.add("2313wewew131");
	   
	   try {
		BufferedReader makeRederFrom = reader.makeRederFrom(set2);
		  //OutputStream os=new FileOutputStream(new File("d:\\dt.txt"));
		   BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(
				   new FileOutputStream(new File("d:\\dt.txt"))));
		   String a;;
		   while((a=makeRederFrom.readLine())!=null){
			   bw.write(a); 
			   bw.flush();
		   }
	} catch (FileNotFoundException e) {
		System.out.println("cuolelelelelele1111");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("cuolelelelelele2222222222");
		e.printStackTrace();
	}
	   
}
}
