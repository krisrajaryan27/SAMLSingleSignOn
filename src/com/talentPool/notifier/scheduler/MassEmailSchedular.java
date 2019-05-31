package com.talentPool.notifier.scheduler;

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

public class MassEmailSchedular {
	private static Scheduler scheduler;
	private static JobDetail massEmailJob;
	public static boolean JOB_STATUS_BUZY = false;

	static {
		try {
			initMassEmailJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for mass email", e);
		}
	}

	public static void initMassEmailJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_MASS_EMAIL)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			massEmailJob = new JobDetail(SchedulerConstants.JOB_MASS_EMAIL, Scheduler.DEFAULT_GROUP, TPMassEmailJob.class, false, true, true);
			scheduler.addJob(massEmailJob, false);
		}
	}
	public static void triggerMassEmail(){
		deleteTrigger();
		addTrigger();
	}
	public static void addTrigger() {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			long ts = TriggerUtils.getNextGivenSecondDate(null, 30).getTime() + 30000;
			triggerName = SchedulerConstants.TRIGGER_MASS_EMAIL;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_MASS_EMAIL, Scheduler.DEFAULT_GROUP, new Date(ts), null, SimpleTrigger.REPEAT_INDEFINITELY,
					1 * 60 * 1000L);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
	}

	public static void deleteTrigger() {
		try {
			TPLogger.getLogger().debug("deleting trigger for mass email = ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_MASS_EMAIL;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}

	}
	
}
