package com.talentPool.masters.scheduler;

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

/**
 * @author ArvindKhatik
 *
 */
public class AsianPaintsMasterSchedular {
	private static Scheduler scheduler;
	private static JobDetail asianPaintsMasterJob;
	public static boolean JOB_STATUS_BUZY = false;
	private static int jobStartHour = 22;
	private static int jobStartMinute = 0;
	private static int jobStartSecond = 0;
	
	static {
		try {
			initAsianPaintsMasterJob();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	public static void initAsianPaintsMasterJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calendar and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_ASIAN_PAINTS_MASTER)) {
			asianPaintsMasterJob = new JobDetail(SchedulerConstants.JOB_ASIAN_PAINTS_MASTER, Scheduler.DEFAULT_GROUP, AsianPaintsMasterJob.class, false, true, true);
			scheduler.addJob(asianPaintsMasterJob, false);
		}
	}
	public static void triggerAsianPaintsMasterSync(){
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
			
			triggerName = SchedulerConstants.TRIGGER_ASIAN_PAINTS_MASTER;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_ASIAN_PAINTS_MASTER, Scheduler.DEFAULT_GROUP, calendar.getTime(), null, SimpleTrigger.REPEAT_INDEFINITELY,
					24 * 60 * 60 * 1000L);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}

	public static void deleteTrigger() {
		try {
			TPLogger.getLogger().debug("deleting trigger for asian paints master data = ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger 
			String triggerName = SchedulerConstants.TRIGGER_ASIAN_PAINTS_MASTER;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}

	}

	public AsianPaintsMasterSchedular() {
		// TODO Auto-generated constructor stub
	}

}
