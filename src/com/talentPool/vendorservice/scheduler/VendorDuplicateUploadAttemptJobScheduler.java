/**
 * 
 */
package com.talentPool.vendorservice.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

/**
 * @author shivprasad
 * 
 */
public class VendorDuplicateUploadAttemptJobScheduler {
	private static Scheduler scheduler;

	private static JobDetail vendorDuplicateAttemptJob;

	static {
		try {
			initVendorDuplicateAttemptJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init job for duplicate attempt", e);
		}
	}

	public static void initVendorDuplicateAttemptJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_VENDOR_DUPLICATE_ATTEMPT)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			vendorDuplicateAttemptJob = new JobDetail(SchedulerConstants.JOB_VENDOR_DUPLICATE_ATTEMPT, Scheduler.DEFAULT_GROUP, VendorDuplicateUploadAttemptJob.class, false, true, true);
			scheduler.addJob(vendorDuplicateAttemptJob, false);
		}
	}

	public static void resetTrigger(String sourceName, String userId, String userFirstName, String positionId, String positionName, HashMap<String, String[]> nameValues) {
		deleteTrigger(sourceName, userId, userFirstName, positionId, positionName, nameValues);
		addTrigger(sourceName, userId, userFirstName, positionId, positionName, nameValues);
	}

	private static void addTrigger(String sourceName, String userId, String userFirstName, String positionId, String positionName, HashMap<String, String[]> nameValues) {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			long ts = TriggerUtils.getNextGivenSecondDate(null, 3).getTime();
			triggerName = SchedulerConstants.TRIGGER_VENDOR_DUPLICATE_ATTEMPT_SUFFIX + userId;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_VENDOR_DUPLICATE_ATTEMPT, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
			simpleTrigger.getJobDataMap().put("sourceName", sourceName);
			simpleTrigger.getJobDataMap().put("userId", userId);
			simpleTrigger.getJobDataMap().put("userFirstName", userFirstName);
			simpleTrigger.getJobDataMap().put("positionId", positionId);
			simpleTrigger.getJobDataMap().put("positionName", positionName);
			if (nameValues != null && nameValues.size() > 0) {
				Iterator<String> itrName = nameValues.keySet().iterator();
				while (itrName.hasNext()) {
					String key = itrName.next();
					String[] vals = nameValues.get(key);
					String val = Utils.convertArrayIntoCommaSptdString(vals);
					simpleTrigger.getJobDataMap().put("$"+key, val);
				}
			}
			//simpleTrigger.getJobDataMap().put("nameValues", nameValues);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

	private static void deleteTrigger(String sourceName, String userId, String userFirstName, String positionId, String positionName, HashMap<String, String[]> nameValues) {
		try {
			TPLogger.getLogger().debug("deleting trigger for duplicate attempt ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_VENDOR_DUPLICATE_ATTEMPT_SUFFIX + userId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}

	}

}
