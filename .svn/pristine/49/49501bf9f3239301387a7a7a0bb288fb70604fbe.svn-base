/**
 * 
 */
package com.talentPool.employeeservice.scheduler;

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
 * @author Praveen
 *
 */
public class EmployeeProgressMailScheduler {
	
	private static Scheduler scheduler;

	private static JobDetail employeeProgressMailJob;

	static {
		try {
			initEmployeeProgressMailJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init job for progress email", e);
		}
	}
	
	public static void initEmployeeProgressMailJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_EMPLOYEE_PROGRESS_MAIL)) {
			employeeProgressMailJob = new JobDetail(SchedulerConstants.JOB_EMPLOYEE_PROGRESS_MAIL, Scheduler.DEFAULT_GROUP, EmployeeProgressMailJob.class, false, true, true);
			scheduler.addJob(employeeProgressMailJob, false);
		}
	}

	public static void resetTrigger(String templateTypeId, String applicantId, 
			String applicantPositionId, String applicantStepId, String userId) {
		deleteTrigger(applicantId,applicantStepId);
		addTrigger(templateTypeId, applicantId, applicantPositionId, applicantStepId, userId);
	}
	
	private static void addTrigger(String templateTypeId, String applicantId, 
			String applicantPositionId, String applicantStepId, String userId) {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			TPLogger.getLogger().debug("add trigger for progress email ");
			long ts = TriggerUtils.getNextGivenSecondDate(null, 3).getTime();
			triggerName = SchedulerConstants.TRIGGER_EMPLOYEE_PROGRESS_MAIL_SUFFIX + applicantId;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_EMPLOYEE_PROGRESS_MAIL, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
			simpleTrigger.getJobDataMap().put("templateTypeId", templateTypeId);
			simpleTrigger.getJobDataMap().put("userId", userId);
			simpleTrigger.getJobDataMap().put("applicantId", applicantId);
			simpleTrigger.getJobDataMap().put("applicantPositionId", applicantPositionId);
			simpleTrigger.getJobDataMap().put("applicantStepId", applicantStepId);
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	private static void deleteTrigger(String applicantId,String positionStepIdTo) {
		try {
			TPLogger.getLogger().debug("deleting trigger for progress email ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_EMPLOYEE_PROGRESS_MAIL_SUFFIX +applicantId+"_"+ positionStepIdTo;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

	}

}
