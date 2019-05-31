/**
 * 
 */
package com.talentPool.lookupTalentpool.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.lookupTalentpool.properties.LookupTPConstant;
import com.talentPool.scheduler.TPDefaultScheduler;

/**
 * @author Shantanu
 *
 */
public class LookupTPShortlistCompareScheduler{
	
	private static Scheduler scheduler;
	private static JobDetail lookupTPShortlistedJob;
	public static boolean JOB_STATUS_BUZY = false;
	public static Long timeExpression = Long.parseLong((LookupTPConstant.SCHEDULER_FREQUENCY))*60*1000L;
	static {
		try {
			initLookupTPShortlistedJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for lookup db shorlisted candidate", e);
		}
	}
	
	public static void initLookupTPShortlistedJob() throws SchedulerException{
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(LookupTPConstant.JOB_LOOKUP_TP_SHORTLIST_COMPARE)) {		
			lookupTPShortlistedJob = new JobDetail(LookupTPConstant.JOB_LOOKUP_TP_SHORTLIST_COMPARE, Scheduler.DEFAULT_GROUP, LookupTPShortlistCompareJob.class, false, true, true);
			scheduler.addJob(lookupTPShortlistedJob, false);
		}
	}
		
	
	public static void triggerLookupTPShortlisted(){		
		deleteTrigger();
		addTrigger();
	}

	
	public static void addTrigger() {
		TPLogger.getLogger().debug("*************************** - Add Trigger for Cron Job ++++++++++++++++++++++++++++++ ");		
		SimpleTrigger simpleTrigger = null;
		try {			
			TPLogger.getLogger().debug("Adding trigger for Lookup TP Schedule");
			long ts = TriggerUtils.getNextGivenSecondDate(null, 30).getTime() + 30000;			
			simpleTrigger = new SimpleTrigger(LookupTPConstant.TRIGGER_LOOKUP_TP_SHORTLIST_COMPARE, Scheduler.DEFAULT_GROUP, 
					LookupTPConstant.JOB_LOOKUP_TP_SHORTLIST_COMPARE, Scheduler.DEFAULT_GROUP, new Date(ts), null, SimpleTrigger.REPEAT_INDEFINITELY,timeExpression);
			scheduler.scheduleJob(simpleTrigger);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding ", e);
		}
	}
	
	public static void deleteTrigger() {
		TPLogger.getLogger().debug("************************* - deleting trigger for Lookup TP Shortlisted Scheduler *************************");
		try {			
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			if (triggerList.contains(LookupTPConstant.TRIGGER_LOOKUP_TP_SHORTLIST_COMPARE)) {
				scheduler.unscheduleJob(LookupTPConstant.TRIGGER_LOOKUP_TP_SHORTLIST_COMPARE, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting",e);
		}
	}
}