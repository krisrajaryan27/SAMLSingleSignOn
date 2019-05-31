package com.talentPool.customReports.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;
import com.talentPool.scheduler.dataobject.SchedulerData;
import com.talentPool.scheduler.manager.UserSchedulerManager;

/**
 * @author SiddharthK
 *
 */
public class UpdateUsersScheduler {
	
	private static Scheduler scheduler;
	private static JobDetail updateUsersJob;
	public static boolean JOB_STATUS_BUZY = false;
	public static Long repeatInterval;
	public static int startHour;

	static {
		try {
			initSchedulerInfo();
			initUpdateUsersJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for updating Users", e);
		}
	}
	
	public static void initUpdateUsersJob() throws SchedulerException{
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List<String> jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_UPDATE_USERS)) {
			updateUsersJob = new JobDetail(SchedulerConstants.JOB_UPDATE_USERS, Scheduler.DEFAULT_GROUP, UpdateUsersJob.class, false, true, true);
			scheduler.addJob(updateUsersJob, false);
		}
	}
	
	public static void initSchedulerInfo() {
		UserSchedulerManager schedulerManager = new UserSchedulerManager();
		SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
		
		try {
			startHour = Integer.parseInt(schedulerData.getSchedulerStartHour());
			repeatInterval = Long.parseLong(schedulerData.getSchedulerFrequency()) * 60 * 60 * 1000L;
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init users' schedular details ", e);
		}
	}
	
	public static void updateScheduler() {
		initSchedulerInfo();
		triggerUpdateUsersJob();
	}
	
	public static void triggerUpdateUsersJob(){		
		deleteTrigger();
		addTrigger();
	}
	
	public static void addTrigger() {
		TPLogger.getLogger().debug("*************************** - Add Trigger for Updating Users ++++++++++++++++++++++++++++++ ");		
		SimpleTrigger simpleTrigger = null;
		try {			
			TPLogger.getLogger().debug("Adding trigger for Updating Users");

			if(startHour < 0) {
				TPLogger.getLogger().error("Start Hour for Scheduler not set, so not scheduling the " + SchedulerConstants.JOB_UPDATE_USERS);
				return;
			}
			Date startTime = new Date(System.currentTimeMillis());
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(startTime);
			cal.set(java.util.Calendar.HOUR_OF_DAY, startHour);
			cal.set(java.util.Calendar.MINUTE, 0);
			cal.set(java.util.Calendar.SECOND, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			startTime = cal.getTime();
			simpleTrigger = new SimpleTrigger(SchedulerConstants.TRIGGER_UPDATE_USERS, Scheduler.DEFAULT_GROUP, startTime);
			simpleTrigger.setJobName(SchedulerConstants.JOB_UPDATE_USERS);
			simpleTrigger.setJobGroup(Scheduler.DEFAULT_GROUP);
			if(repeatInterval > 0) {
				simpleTrigger.setEndTime(null);
				simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
				simpleTrigger.setRepeatInterval(repeatInterval);
			} 
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding trigger for updating USERs", e);
		}
	}
	
	public static void deleteTrigger() {
		TPLogger.getLogger().debug("************************* - deleting trigger for Updating Users Scheduler *************************");
		try {			
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			if (triggerList.contains(SchedulerConstants.TRIGGER_UPDATE_USERS)) {
				scheduler.unscheduleJob(SchedulerConstants.TRIGGER_UPDATE_USERS, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger for updating users",e);
		}
	}
	
}
