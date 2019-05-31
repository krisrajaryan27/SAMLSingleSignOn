package com.talentPool.customReports.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;
import com.talentPool.scheduler.dataobject.SchedulerData;
import com.talentPool.scheduler.manager.SchedulerManager;

public class UpdateMasterTablesScheduler {

	private static Scheduler scheduler;
	private static JobDetail updateMasterTablesJob;
	public static boolean JOB_STATUS_BUZY = false;
	public static Long repeatInterval;
	public static int startHour;
	public static int startMin;

	static {
		try {
			initSchedulerInfo();
			initUpdateMasterTablesJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for updating master tables", e);
		}
	}
	
	public static void initUpdateMasterTablesJob() throws SchedulerException{
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List<String> jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_UPDATE_MASTER_TABLES)) {
			updateMasterTablesJob = new JobDetail(SchedulerConstants.JOB_UPDATE_MASTER_TABLES, Scheduler.DEFAULT_GROUP, UpdateMasterTablesJob.class, false, true, true);
			scheduler.addJob(updateMasterTablesJob, false);
		}
	}
	
	public static void initSchedulerInfo() {
		SchedulerManager schedulerManager = new SchedulerManager();
		SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
		
		try {
			startHour = Integer.parseInt(Utils.getDateStringConvertedToOtherDateFormat(schedulerData.getSchedulerStartHour(), Utils.regDDMMYYYYHHMMSSsssFromat, "HH"));
			startMin = Integer.parseInt(Utils.getDateStringConvertedToOtherDateFormat(schedulerData.getSchedulerStartHour(), Utils.regDDMMYYYYHHMMSSsssFromat, "mm"));
			repeatInterval = Long.parseLong(schedulerData.getSchedulerFrequency()) * 60 * 60 * 1000L;
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init master tables' schedular details ", e);
		}
	}
	
	public static void updateScheduler() {
		initSchedulerInfo();
		triggerUpdateMasterTablesJob();
	}
	
	public static void triggerUpdateMasterTablesJob(){		
		deleteTrigger();
		addTrigger();
	}
	
	public static void resetRegenerateMasterTableTrigger(){		
		deleteRegenerateMasterTableTrigger();
		addRegenerateMasterTableTrigger();
	}
	
	public static void addRegenerateMasterTableTrigger() {
		TPLogger.getLogger().debug("*************************** - Adding Trigger for Regenerating Master Tables ++++++++++++++++++++++++++++++ ");		
		SimpleTrigger simpleTrigger = null;
		try {
			long ts = TriggerUtils.getNextGivenSecondDate(null, 30).getTime()+10*60*1000L;
			simpleTrigger = new SimpleTrigger(SchedulerConstants.TRIGGER_REGENERATE_MASTER_TABLES, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_APPOINTMENT_NOTIFICATION, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
			simpleTrigger.setJobName(SchedulerConstants.JOB_UPDATE_MASTER_TABLES);
			simpleTrigger.setJobGroup(Scheduler.DEFAULT_GROUP);
			scheduler.scheduleJob(simpleTrigger);
			TPLogger.getLogger().debug("*************************** - Added Trigger for Regenerating Master Tables ++++++++++++++++++++++++++++++ ");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding trigger for Regenerating Master Tables", e);
		}
	}

	
	public static void addTrigger() {
		TPLogger.getLogger().debug("*************************** - Add Trigger for Updating Master Tables ++++++++++++++++++++++++++++++ ");		
		SimpleTrigger simpleTrigger = null;
		try {			
			TPLogger.getLogger().debug("Adding trigger for Updating Master Tables");

			if(startHour < 0) {
				TPLogger.getLogger().error("Start Hour for Scheduler not set, so not scheduling the " + SchedulerConstants.JOB_UPDATE_MASTER_TABLES);
				return;
			}
			Date startTime = new Date(System.currentTimeMillis());
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(startTime);
			cal.set(java.util.Calendar.HOUR_OF_DAY, startHour);
			cal.set(java.util.Calendar.MINUTE, startMin);
			cal.set(java.util.Calendar.SECOND, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			startTime = cal.getTime();
			simpleTrigger = new SimpleTrigger(SchedulerConstants.TRIGGER_UPDATE_MASTER_TABLES, Scheduler.DEFAULT_GROUP, startTime);
			simpleTrigger.setJobName(SchedulerConstants.JOB_UPDATE_MASTER_TABLES);
			simpleTrigger.setJobGroup(Scheduler.DEFAULT_GROUP);
			if(repeatInterval > 0) {
				simpleTrigger.setEndTime(null);
				simpleTrigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
				simpleTrigger.setRepeatInterval(repeatInterval);
			} 
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding trigger for updating master tables", e);
		}
	}
	
	public static void deleteTrigger() {
		TPLogger.getLogger().debug("************************* - deleting trigger for Updating Master Tables Scheduler *************************");
		try {			
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			if (triggerList.contains(SchedulerConstants.TRIGGER_UPDATE_MASTER_TABLES)) {
				scheduler.unscheduleJob(SchedulerConstants.TRIGGER_UPDATE_MASTER_TABLES, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger for updating master tables",e);
		}
	}
	
	public static void deleteRegenerateMasterTableTrigger() {
		TPLogger.getLogger().debug("************************* - deleting trigger for Regenerating Master Tables Scheduler *************************");
		try {			
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			if (triggerList.contains(SchedulerConstants.TRIGGER_REGENERATE_MASTER_TABLES)) {
				scheduler.unscheduleJob(SchedulerConstants.TRIGGER_REGENERATE_MASTER_TABLES, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger for Regenerating Master Tables",e);
		}
	}
}