/**
 * 
 */
package com.talentPool.applicant.scheduler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

/**
 * @author shivprasad
 * 
 */
public class SourceLockinCheckerJobScheduler {
	private static Scheduler scheduler;
	private static JobDetail lockinCheckerJob;

	static {
		try {
			initSourceLockinCheckerJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init job for source checker", e);
		}
	}

	public static void initSourceLockinCheckerJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_SOURCE_LOCKIN_CHECKER)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			lockinCheckerJob = new JobDetail(SchedulerConstants.JOB_SOURCE_LOCKIN_CHECKER, Scheduler.DEFAULT_GROUP, SourceLockinCheckerJob.class, false, true, true);
			scheduler.addJob(lockinCheckerJob, false);
		}
	}

	public static void resetTrigger() {
		deleteTrigger();
		addTrigger();
	}

	private static void addTrigger() {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 1);
			triggerName = SchedulerConstants.TRIGGER_SOURCE_LOCKIN_CHECKER;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_SOURCE_LOCKIN_CHECKER, Scheduler.DEFAULT_GROUP, cal.getTime(), null,
					SimpleTrigger.REPEAT_INDEFINITELY, 24 * 60 * 60 * 1000L);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

	private static void deleteTrigger() {
		try {
			TPLogger.getLogger().debug("deleting trigger for source checker ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_SOURCE_LOCKIN_CHECKER;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}

	}

}
