package com.talentPool.notifier.scheduler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

public class EscalationEmailSchedular {
	private static Scheduler scheduler;
	private static JobDetail escalationEmailJob;
	public static boolean JOB_STATUS_BUZY = false;
	private static int jobStartHour = 8;
	private static int jobStartMinute = 0;
	private static int jobStartSecond = 0;
	
	static {
		try {
			initEscalationEmailJob();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	public static void initEscalationEmailJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_ESCALATION_EMAIL)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			escalationEmailJob = new JobDetail(SchedulerConstants.JOB_ESCALATION_EMAIL, Scheduler.DEFAULT_GROUP, TPEscalationEmailJob.class, false, true, true);
			scheduler.addJob(escalationEmailJob, false);
		}
	}
	public static void triggerEscalationEmail(){
		deleteTrigger();
		addTrigger();
	}
	public static void addTrigger() {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			Calendar calendar = new GregorianCalendar();
			if(calendar.get(Calendar.HOUR_OF_DAY)>= jobStartHour){
				calendar.add(Calendar.DATE, 1);
			}
			calendar.set(Calendar.HOUR_OF_DAY, jobStartHour);
			calendar.set(Calendar.MINUTE, jobStartMinute);
			calendar.set(Calendar.SECOND, jobStartSecond);
			
			triggerName = SchedulerConstants.TRIGGER_ESCALATION_EMAIL;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_ESCALATION_EMAIL, Scheduler.DEFAULT_GROUP, calendar.getTime(), null, SimpleTrigger.REPEAT_INDEFINITELY,
					24 * 60 * 60 * 1000L);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}

	public static void deleteTrigger() {
		try {
			TPLogger.getLogger().debug("deleting trigger for escalation email = ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_ESCALATION_EMAIL;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}

	}
	
}
