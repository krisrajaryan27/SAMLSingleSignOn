/**
 * 
 */
package com.talentPool.selectionProcess.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

/**
 * @author shivprasad
 * 
 */
public class FeedbackNotificationScheduler {
	private static Scheduler scheduler;
	private static JobDetail feedbackNotificationJob;

	static {
		try {
			initFeedbackNotificationJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for not scheduled step feedback", e);
		}
	}

	public static void initFeedbackNotificationJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_FEEDBACK_NOTIFICATION)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			feedbackNotificationJob = new JobDetail(SchedulerConstants.JOB_FEEDBACK_NOTIFICATION, Scheduler.DEFAULT_GROUP, FeedbackNotificationJob.class, false, true, true);
			scheduler.addJob(feedbackNotificationJob, false);
		}

	}

	/**
	 * adds trigger to send feedback reminder. send only if send feedbcak reminder flag is set in
	 * properties
	 * 
	 * @param appointmentId
	 */
	public static void addTrigger(String applicantId, String positionId, String stepId, String attendeesId, String userId) {
		try {
			String sendFlag = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS);
			sendFlag = Utils.isBlankOrNull(sendFlag) ? "0" : sendFlag;
			if (sendFlag.trim().equals(GlobalConstants.ENABLED)) {
				String triggerName = "";
				SimpleTrigger simpleTrigger = null;

				long ts = TriggerUtils.getNextGivenSecondDate(null, 5).getTime();

				TPLogger.getLogger().debug("Adding trigger for not scheduled step feedback");
				triggerName = SchedulerConstants.TRIGGER_FEEDBACK_NOTIFICATION_SUFIX + applicantId + "_" + positionId + "_" + stepId;
				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, new Date(ts));

				simpleTrigger.getJobDataMap().put("applicantId", applicantId);
				simpleTrigger.getJobDataMap().put("positionId", positionId);
				simpleTrigger.getJobDataMap().put("stepId", stepId);
				simpleTrigger.getJobDataMap().put("attendeesId", attendeesId);
				simpleTrigger.getJobDataMap().put("userId", userId);
				simpleTrigger.setJobName(SchedulerConstants.JOB_FEEDBACK_NOTIFICATION);
				scheduler.scheduleJob(simpleTrigger);
			}
		} catch (SchedulerException se) {
			TPLogger.getLogger().error("Unable to schedule trigger for not scheduled step feedback", se);
		}
	}

	public static void deleteTrigger(String applicantId, String positionId, String stepId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for not scheduled step feedback");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send feedback reminder
			String triggerName = SchedulerConstants.TRIGGER_FEEDBACK_NOTIFICATION_SUFIX + applicantId + "_" + positionId + "_" + stepId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

	public static void updateTrigger(String applicantId, String positionId, String stepId, String attendeesId, String userId) {
		try {
			// delete the associated triggers and new triggers
			TPLogger.getLogger().debug("updating trigger for not scheduled step feedback");
			deleteTrigger(applicantId, positionId, stepId);
			addTrigger(applicantId, positionId, stepId, attendeesId, userId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

}
