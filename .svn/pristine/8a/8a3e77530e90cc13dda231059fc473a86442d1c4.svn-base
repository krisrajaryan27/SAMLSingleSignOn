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
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

public class CandidateProgressNotificationScheduler {
	private static Scheduler scheduler;
	private static JobDetail progressNotificationJob;

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
		if (!jobList.contains(SchedulerConstants.JOB_CANDIDATE_PROGRESS_NOTIFICATION)) {
			progressNotificationJob = new JobDetail(SchedulerConstants.JOB_CANDIDATE_PROGRESS_NOTIFICATION, Scheduler.DEFAULT_GROUP, CandidateProgressNotficationJob.class, false, true, true);
			scheduler.addJob(progressNotificationJob, false);
		}

	}

	/**
	 * adds trigger to send email to candidate.
	 * 
	 * @param appointmentId
	 */
	public static void addTrigger(String applicantId,String positionId, String positionStepId, String userId) {
		try {
			String triggerName = "";
			SimpleTrigger simpleTrigger = null;
			long ts = TriggerUtils.getNextGivenSecondDate(null, 5).getTime();

			TPLogger.getLogger().debug("Adding trigger for send email to candiate when moved up");
			triggerName = SchedulerConstants.TRIGGER_CANDIDATE_PROGRESS_NOTIFICATION_SUFIX + applicantId + "_" + positionStepId;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, new Date(ts));

			simpleTrigger.getJobDataMap().put("applicantId", applicantId);
			simpleTrigger.getJobDataMap().put("positionId", positionId);
			simpleTrigger.getJobDataMap().put("positionStepId", positionStepId);
			simpleTrigger.getJobDataMap().put("userId", userId);
			simpleTrigger.setJobName(SchedulerConstants.JOB_CANDIDATE_PROGRESS_NOTIFICATION);
			scheduler.scheduleJob(simpleTrigger);
		} catch (SchedulerException se) {
			TPLogger.getLogger().error("Unable to schedule trigger for send email to candiate when moved up", se);
		}
	}

	public static void deleteTrigger(String applicantId, String stepId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for send email to candiate when moved up");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to candiate when moved up
			String triggerName = SchedulerConstants.TRIGGER_CANDIDATE_PROGRESS_NOTIFICATION_SUFIX + applicantId + "_" + stepId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

	public static void updateTrigger(String applicantId,String positionId ,String positionStepId, String userId) {
		try {
			// delete the associated triggers and new triggers
			TPLogger.getLogger().debug("updating trigger for send email to candiate when moved up");
			deleteTrigger(applicantId, positionStepId);
			addTrigger(applicantId, positionId,positionStepId, userId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}
}
