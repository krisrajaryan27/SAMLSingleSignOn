/**
 * 
 */
package com.talentPool.vendorservice.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

/**
 * @author Ajeet
 *
 */
public class VendorRejectMailSchedular {

	private static Scheduler scheduler;

	private static JobDetail vendorRejectMailJob;

	static {
		try {
			initVendorRejectMailJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init job for reject email", e);
		}
	}

	public static void initVendorRejectMailJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_VENDOR_REJECT_MAIL)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			vendorRejectMailJob = new JobDetail(SchedulerConstants.JOB_VENDOR_REJECT_MAIL, Scheduler.DEFAULT_GROUP, VendorRejectMailJob.class, false, true, true);
			scheduler.addJob(vendorRejectMailJob, false);
		}
	}

	public static void resetTrigger(String sourceEmail, String userId, String applicantId, String positionId, String positionStepIdFrom ) {
		deleteTrigger(applicantId);
		addTrigger(sourceEmail, userId, applicantId, positionId, positionStepIdFrom);
	}

	private static void addTrigger(String sourceEmail, String userId, String applicantId, String positionId, String positionStepIdFrom) {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			TPLogger.getLogger().debug("add trigger for reject email ");
			long ts = TriggerUtils.getNextGivenSecondDate(null, 3).getTime();
			triggerName = SchedulerConstants.TRIGGER_VENDOR_REJECT_MAIL_SUFFIX + applicantId;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_VENDOR_REJECT_MAIL, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
			simpleTrigger.getJobDataMap().put("sourceEmail", sourceEmail);
			simpleTrigger.getJobDataMap().put("userId", userId);
			simpleTrigger.getJobDataMap().put("applicantId", applicantId);
			simpleTrigger.getJobDataMap().put("positionId", positionId);
			simpleTrigger.getJobDataMap().put("positionStepId", positionStepIdFrom);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	private static void deleteTrigger(String applicantId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for reject email ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_VENDOR_REJECT_MAIL_SUFFIX + applicantId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

	}

}

