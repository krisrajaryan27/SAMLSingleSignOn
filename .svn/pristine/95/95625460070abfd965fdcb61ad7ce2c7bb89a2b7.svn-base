/**
 * 
 */
package com.talentPool.requisition.scheduler;

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

/**
 * @author shivprasad
 * 
 */
public class RequisitionApprovalNotificationScheduler {

	private static Scheduler scheduler;

	private static JobDetail requisitionApprovalJob;

	static {
		try {
			initRequisitionApprovalJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init requisition approval job", e);
		}
	}

	public static void initRequisitionApprovalJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_REQUISITION_APPROVAL_NOTIFICATION_TO_USER)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			requisitionApprovalJob = new JobDetail(SchedulerConstants.JOB_REQUISITION_APPROVAL_NOTIFICATION_TO_USER, Scheduler.DEFAULT_GROUP, RequisitionApprovalNotificationJob.class, false, true, true);
			scheduler.addJob(requisitionApprovalJob, false);
		}
	}

	public static void resetTrigger(String positionId, String userId) {
		deleteTrigger(positionId, userId);
		addTrigger(positionId, userId);
	}

	public static void addTrigger(String positionId, String userId) {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			long ts = TriggerUtils.getNextGivenSecondDate(null, 3).getTime();
			triggerName = SchedulerConstants.TRIGGER_REQUISITION_APPROVAL_NOTIFICATION_TO_USER_SUFFIX + positionId + "_" + userId;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_REQUISITION_APPROVAL_NOTIFICATION_TO_USER, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
			simpleTrigger.getJobDataMap().put("positionId", positionId);
			simpleTrigger.getJobDataMap().put("userId", userId);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

	private static void deleteTrigger(String positionId, String userId) {
		try {
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_REQUISITION_APPROVAL_NOTIFICATION_TO_USER_SUFFIX + positionId + "_" + userId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}

	}

}
