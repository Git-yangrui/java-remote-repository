package com.yangrui.quartz.quartz;

import javax.sound.midi.MidiDevice.Info;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class LoadXMlTests {
	private Logger logger = Logger.getLogger(MultiSchedulerTests.class);

	private Scheduler createScheduler() throws Exception {
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		return scheduler;
	}
	
	@Test
	public void test_LoadXMl() throws Exception{
		LoadXMlTests loadXMlTests=new LoadXMlTests();	
		Scheduler scheduler = loadXMlTests.createScheduler();
		scheduler.start();
		logger.info("-----------------------");
		
		Thread.sleep(200000);
}
}