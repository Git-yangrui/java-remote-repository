package com.yangrui.quartz.quartz;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SmipleSchedulerTests {
  private static Logger logger=Logger.getLogger(SmipleSchedulerTests.class);
  
  
  private Scheduler createScheduler() throws Exception{
	  SchedulerFactory schedulerFactory=new StdSchedulerFactory();
	  return schedulerFactory.getScheduler();
  }
  
  private void scheduleJob(Scheduler scheduler) throws Exception{
	JobDetail jobDetail = JobBuilder.newJob(ScanDirectoryJob.class).withIdentity("ScanDirectory", "Job-group")
	.withDescription("ScanDirectory form tomcat cond").build();
	
	jobDetail.getJobDataMap().put("SCAN_DIR", "c:\\iem\\");
	
	 Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ScanDirectoryTrigger", "ScanDirectoryTrigger-group").startNow()
	 .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(1000).withRepeatCount(10)).build();
	
	 
	 scheduler.scheduleJob(jobDetail, trigger);
  }
  
  @Test
  public void test_ScanJob() throws Exception{
	  SmipleSchedulerTests schedulerTests=new SmipleSchedulerTests();
	  Scheduler createScheduler = schedulerTests.createScheduler();
	  createScheduler.start();
	  
	  logger.info("Scheduler started at"+new Date());
	  schedulerTests.scheduleJob(createScheduler);
	  Thread.sleep(1000000);
  }
  
  @Test
  public void TEST_LOG4J(){
	  logger.info("ssssss");
  }
}
