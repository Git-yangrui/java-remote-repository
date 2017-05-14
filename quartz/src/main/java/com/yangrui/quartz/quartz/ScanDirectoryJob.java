package com.yangrui.quartz.quartz;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobDetailImpl;

public class ScanDirectoryJob implements Job {
	private static Logger logger = Logger.getLogger(ScanDirectoryJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		JobDetail jobDetail = context.getJobDetail();
		String jobName = ((JobDetailImpl) jobDetail).getName();
		logger.info(jobName + " fired out" + new Date());
		JobDataMap dataMap = jobDetail.getJobDataMap();
		String dirName = dataMap.getString("SCAN_DIR");
		if (dirName == null) {
			throw new JobExecutionException("Directory not configured");
		}
		File dir = new File(dirName);
		if (!dir.exists()) {
			throw new JobExecutionException("invalid Dir" + dirName);
		}

		FileFilter fileFilters = new FileFilter() {
			private String extension = ".xml";

			@Override
			public boolean accept(File pathname) {
				if (pathname.isDirectory()) {
					return false;
				}
				String name = pathname.getName().toLowerCase();
				return pathname.isFile() && (name.toLowerCase().indexOf(extension) > 0);
			}
		};
		// FilenameFilter filter=new FilenameFilter() {
		// @Override
		// public boolean accept(File dir, String name) {
		// if(dir.isDirectory()){
		// return false;
		// }
		// return dir.isFile()&&(name.toLowerCase().indexOf(".xml")>0);
		// }
		// };
		File[] files = dir.listFiles(fileFilters);
		if (files == null || files.length <= 0) {
			logger.info("No such file find in" + dir);
			return;
		}
		int size = files.length;
		logger.info("The number of XML files" + size);

		for (File file : files) {
			File aFile = file.getAbsoluteFile();
			long fileSize = file.length();
			String msg = aFile + "- size" + fileSize;
			logger.info(msg);
		}
	}

}
