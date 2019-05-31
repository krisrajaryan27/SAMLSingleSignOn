/**
 * 
 */
package com.talentPool.inbox.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.notifier.CandidateStateTemplateEnums;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;
import com.talentPool.user.manager.ModuleSet;

/**
 * @author shivprasad
 * 
 */
public class AutoReplyScheduler {
	private static Scheduler scheduler;
	private static JobDetail autoReplyJob;
	private static JobDetail updateJob;
	private static JobDetail appliedJob;
	static {
		try {
			initAutoReplyJob();
			initCandidateUpdateJob();
			initCandidateAppliedJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init job for auto import", e);
		}
	}

	public static void initAutoReplyJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_AUTO_REPLY)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			autoReplyJob = new JobDetail(SchedulerConstants.JOB_AUTO_REPLY, Scheduler.DEFAULT_GROUP, AutoReplyJob.class, false, true, true);
			scheduler.addJob(autoReplyJob, false);
		}
	}

	private static void initCandidateAppliedJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_CANDIDATE_APPLIED)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			appliedJob = new JobDetail(SchedulerConstants.JOB_CANDIDATE_APPLIED, Scheduler.DEFAULT_GROUP, AutoReplyJob.class, false, true, true);
			scheduler.addJob(appliedJob, false);
		}
	}

	private static void initCandidateUpdateJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_CANDIDATE_UPDATE)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			updateJob = new JobDetail(SchedulerConstants.JOB_CANDIDATE_UPDATE, Scheduler.DEFAULT_GROUP, AutoReplyJob.class, false, true, true);
			scheduler.addJob(updateJob, false);
		}
	}

//	public static void addTrigger(String fromEmail) {
//		String triggerName = "";
//		SimpleTrigger simpleTrigger = null;
//		try {
//			if (ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL() && !isTriggerExist()) {
//				long ts = TriggerUtils.getNextGivenSecondDate(null, 1).getTime();
//				triggerName = SchedulerConstants.TRIGGER_AUTO_REPLY;
//				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_AUTO_REPLY, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
//				simpleTrigger.getJobDataMap().put("fromEmail", fromEmail);
//				scheduler.scheduleJob(simpleTrigger);
//			}
//		} catch (Exception e) {
//			TPLogger.getLogger().error("Error", e);
//		}
//	}
	
	public static void addTrigger(String fromEmail,CandidateStateTemplateEnums stateEnum,String positionId) {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			if (ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL() && !isTriggerExist(stateEnum)) {
				long ts = TriggerUtils.getNextGivenSecondDate(null, 1).getTime();
				switch(stateEnum){
					case ADDED: 
						triggerName = SchedulerConstants.TRIGGER_AUTO_REPLY;
						simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_AUTO_REPLY, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
						break;
					case APPLIED: 
						triggerName = SchedulerConstants.TRIGGER_CANDIDATE_APPLIED;
						simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_CANDIDATE_APPLIED, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
						break;
					case UPDATED: 
						triggerName = SchedulerConstants.TRIGGER_CANDIDATE_UPDATE;
						simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_CANDIDATE_UPDATE, Scheduler.DEFAULT_GROUP, new Date(ts), null, 0, 0);
						break;
				}
				simpleTrigger.getJobDataMap().put("fromEmail", fromEmail);
				simpleTrigger.getJobDataMap().put("stateEnum", stateEnum.toString());
				simpleTrigger.getJobDataMap().put("positionId", Utils.isBlankOrNull(positionId)?"":positionId);
				scheduler.scheduleJob(simpleTrigger);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

	private static boolean isTriggerExist(CandidateStateTemplateEnums stateEnum) {
		try {
			String triggerName="";
			switch(stateEnum){
				case ADDED: triggerName = SchedulerConstants.TRIGGER_AUTO_REPLY;break;
				case APPLIED: triggerName = SchedulerConstants.TRIGGER_CANDIDATE_APPLIED;break;
				case UPDATED: triggerName = SchedulerConstants.TRIGGER_CANDIDATE_UPDATE;break;
			}
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			if (triggerList.contains(triggerName)) {
				return true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return false;
	}

	public static void deleteTrigger() {
		try {
			if (ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL() && isTriggerExist(CandidateStateTemplateEnums.ADDED)) {
				scheduler.unscheduleJob(SchedulerConstants.TRIGGER_AUTO_REPLY, Scheduler.DEFAULT_GROUP);
			}
			if (ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL() && isTriggerExist(CandidateStateTemplateEnums.UPDATED)) {
				scheduler.unscheduleJob(SchedulerConstants.TRIGGER_CANDIDATE_UPDATE, Scheduler.DEFAULT_GROUP);
			}
			if (ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL() && isTriggerExist(CandidateStateTemplateEnums.APPLIED)) {
				scheduler.unscheduleJob(SchedulerConstants.TRIGGER_CANDIDATE_APPLIED, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}

	}

}
