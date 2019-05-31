package com.talentPool.budget.scheduler;

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

public class BudgetOwnerNotificationScheduler {
	private static Scheduler scheduler;
	private static JobDetail budgetOwnerNotificationJob;

	static {
		try {
			initBudgetOwnerNotificationJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for budget owner notifications", e);
		}
	}

	public static void initBudgetOwnerNotificationJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_BUDGET_OWNER_NOTIFICATION)) {
			budgetOwnerNotificationJob = new JobDetail(SchedulerConstants.JOB_BUDGET_OWNER_NOTIFICATION, Scheduler.DEFAULT_GROUP, BudgetOwnerNotficationJob.class, false, true, true);
			scheduler.addJob(budgetOwnerNotificationJob, false);
		}

	}

	/**
	 * adds trigger to send email to candidate.
	 * 
	 * @param appointmentId
	 */
	public static void addTrigger(String budgetItemId, String templateTypeId,String userId, String transferredHeads, String positionId) {
		try {
			String triggerName = "";
			SimpleTrigger simpleTrigger = null;
			long ts = TriggerUtils.getNextGivenSecondDate(null, 5).getTime();

			TPLogger.getLogger().debug("Adding trigger for send email to owner when budget item changes");
			triggerName = SchedulerConstants.TRIGGER_BUDGET_OWNER_NOTIFICATION_SUFIX + budgetItemId+"_"+templateTypeId;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, new Date(ts));

			simpleTrigger.getJobDataMap().put("budgetItemId", budgetItemId);
			simpleTrigger.getJobDataMap().put("templateTypeId", templateTypeId);			
			simpleTrigger.getJobDataMap().put("userId", userId);
			simpleTrigger.getJobDataMap().put("transferredHeads", transferredHeads);
			simpleTrigger.getJobDataMap().put("positionId", positionId);			
			simpleTrigger.setJobName(SchedulerConstants.JOB_BUDGET_OWNER_NOTIFICATION);
			scheduler.scheduleJob(simpleTrigger);
		} catch (SchedulerException se) {
			TPLogger.getLogger().error("Unable to schedule trigger for send email to owner when budget item changes", se);
		}
	}

	public static void deleteTrigger(String budgetItemId, String templateTypeId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for send email to owner when budget item changes");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to owner when budget item changes
			String triggerName = SchedulerConstants.TRIGGER_BUDGET_OWNER_NOTIFICATION_SUFIX + budgetItemId+"_"+templateTypeId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

	public static void updateTrigger(String budgetItemId, String templateTypeId,String userId, String transferredHeads, String positionId) {
		try {
			// delete the associated triggers and new triggers
			TPLogger.getLogger().debug("updating trigger for send email to owner when budget item changes");
			deleteTrigger(budgetItemId, templateTypeId);
			addTrigger(budgetItemId, templateTypeId, userId,transferredHeads,positionId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}
}
