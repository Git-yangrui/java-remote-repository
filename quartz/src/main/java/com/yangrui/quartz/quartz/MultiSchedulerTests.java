package com.yangrui.quartz.quartz;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MultiSchedulerTests {
	private Logger logger = Logger.getLogger(MultiSchedulerTests.class);

	private Scheduler createScheduler() throws Exception {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		return scheduler;
	}

	private void scheduleJob(Scheduler scheduler, String jobName, Class<? extends Job> jobClass, String scanDir,
			String triggerName, int scanInterval) throws Exception {

		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, "Job-group").build();
		jobDetail.getJobDataMap().put("SCAN_DIR", scanDir);

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, "trigger-group").startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(scanInterval)).build();
		
		scheduler.scheduleJob(jobDetail, trigger);
	}
   @Test
   public void  multiScanTEst() throws Exception{
	   MultiSchedulerTests multiSchedulerTests=new MultiSchedulerTests();
	   Scheduler createScheduler = multiSchedulerTests.createScheduler();
	  
	   multiSchedulerTests.scheduleJob(createScheduler, "SCAn_Dir", ScanDirectoryJob.class, 
			   "c:\\iem\\", "iem-trigger", 10);
	   multiSchedulerTests.scheduleJob(createScheduler, "SCAn_Dir1", ScanDirectoryJob.class, 
			   "c:\\iem\\iem\\", "iem-iem-trigger", 15);
	   createScheduler.start();
	   
	   logger.info("started");
	   
	   Thread.sleep(10000);
   }
}
