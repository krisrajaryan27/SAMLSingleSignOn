/**
 * 
 */
package com.talentPool.calendar.scheduler;

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
public class FeedbackScheduler {
	private static Scheduler scheduler;
	private static JobDetail feedbackRemainderJob;

	static {
		try {
			initFeedbackScheduledJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for appointment", e);
		}
	}

	public static void initFeedbackScheduledJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_FEEDBACK_REMAINDER)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			feedbackRemainderJob = new JobDetail(SchedulerConstants.JOB_FEEDBACK_REMAINDER, Scheduler.DEFAULT_GROUP, SendFeedbackRemainders.class, false, true, true);
			scheduler.addJob(feedbackRemainderJob, false);
		}

	}

	/**
	 * adds trigger to send feedback reminder. send only if send feedbcak
	 * reminder flag is set in properties
	 * 
	 * @param appointmentId
	 */
	public static void addTrigger(String appointmentId, String attendeesId) {
		try {
			String sendFlag = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS);
			sendFlag = Utils.isBlankOrNull(sendFlag) ? "0" : sendFlag;
			if (sendFlag.trim().equals(GlobalConstants.ENABLED)) {
				String triggerName = "";
				SimpleTrigger simpleTrigger = null;
				// Date odt = aData.getAppointmentFromDateTime();
				// Also add trigger to send email to interviwer to submit f/b
				// Date todt = aData.getAppointmentFromDateTime();

				long ts = TriggerUtils.getNextGivenSecondDate(null, 5).getTime();

				TPLogger.getLogger().debug("Adding trigger for appointment feedback");
				triggerName = SchedulerConstants.TRIGGER_FEEDBACK_SUFIX + appointmentId;
				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, new Date(ts));
				// simpleTrigger = new SimpleTrigger(triggerName,
				// Scheduler.DEFAULT_GROUP,
				// SchedulerConstants.JOB_FEEDBACK_REMAINDER,
				// Scheduler.DEFAULT_GROUP, new Date(ts),null,null,0);

				simpleTrigger.getJobDataMap().put("appointmentId", appointmentId);
				simpleTrigger.getJobDataMap().put("attendeesId", attendeesId);
				simpleTrigger.setJobName(SchedulerConstants.JOB_FEEDBACK_REMAINDER);
				scheduler.scheduleJob(simpleTrigger);
			}
		} catch (SchedulerException se) {
			TPLogger.getLogger().error("Unable to schedule trigger for appointment", se);
		}
	}

	public static void deleteTrigger(String appointmentId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for appointment");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send feedback reminder
			String triggerName = SchedulerConstants.TRIGGER_FEEDBACK_SUFIX + appointmentId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

	public static void updateTrigger(String appointmentId, String attendeesId) {
		try {
			// delete the associated triggers and new triggers
			TPLogger.getLogger().debug("updating trigger for feedback");
			deleteTrigger(appointmentId);
			addTrigger(appointmentId,attendeesId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

}
