package com.talentPool.scheduler;

import java.util.TimeZone;

import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;

public class SchedulerListenerImplementation implements SchedulerListener {

	@Override
	public void jobScheduled(Trigger trigger) {
		trigger.getJobDataMap().put("timeZoneId", TimeZone.getDefault().getID());
	}

	@Override
	public void jobUnscheduled(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobsPaused(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jobsResumed(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerError(String arg0, SchedulerException arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void schedulerShutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerFinalized(Trigger arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggersPaused(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggersResumed(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
