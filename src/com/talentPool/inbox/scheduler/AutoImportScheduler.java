package com.talentPool.inbox.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

public class AutoImportScheduler {
	private static Scheduler scheduler;
	private static JobDetail autoImportJob;
	static {
		try {
			initAutoImportJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init job for auto import", e);
		}
	}

	public static void initAutoImportJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_AUTO_IMPORT)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			autoImportJob = new JobDetail(SchedulerConstants.JOB_AUTO_IMPORT, Scheduler.DEFAULT_GROUP, AutoImportJob.class, false, true, true);
			scheduler.addJob(autoImportJob, false);
		}
	}

	public static void triggerAutoImport() {
		deleteTrigger();
		addTrigger();
	}

	public static void addTrigger() {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			if (!isTriggerExist()) {
				long ts = TriggerUtils.getNextGivenSecondDate(null, 1).getTime();
				triggerName = SchedulerConstants.TRIGGER_AUTO_IMPORT;
				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_AUTO_IMPORT, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
				scheduler.scheduleJob(simpleTrigger);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
	}

	private static boolean isTriggerExist() {
		try {
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			String triggerName = SchedulerConstants.TRIGGER_AUTO_IMPORT;
			if (triggerList.contains(triggerName)) {
				return true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
		return false;
	}

	public static void deleteTrigger() {
		try {
			if (isTriggerExist()) {
				scheduler.unscheduleJob(SchedulerConstants.TRIGGER_AUTO_IMPORT, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}

	}

}
