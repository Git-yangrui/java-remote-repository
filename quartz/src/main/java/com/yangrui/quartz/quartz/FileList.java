package com.yangrui.quartz.quartz;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import org.apache.commons.collections.map.StaticBucketMap;

public class FileList {
	static FilenameFilter filter=new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			if(dir.isDirectory()){
				return false;
			}
			return dir.isFile()&&(name.toLowerCase().indexOf(".xml")>0);
		}
	};
	static FileFilter fileFilters=new FileFilter() {
		private String extension=".xml";
		@Override
		public boolean accept(File pathname) {
			if(pathname.isDirectory()){
				return false;
			}
			String name=pathname.getName().toLowerCase();
			return pathname.isFile()&&(name.toLowerCase().indexOf(extension)>0);
		}
	};
	
	public static void main(String[] args) {
		File file=new File("c:\\iem\\");
		File[] listFiles = file.listFiles(fileFilters);
		
	}
}
