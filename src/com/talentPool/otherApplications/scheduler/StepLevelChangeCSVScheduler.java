/**
 * 
 */
package com.talentPool.otherApplications.scheduler;

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
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.scheduler.TPDefaultScheduler;


/**
 * @author shantanu
 *
 */
public class StepLevelChangeCSVScheduler {
	
	private static Scheduler scheduler;
	private static JobDetail stepLevelChangeCSVJob;
	public static boolean JOB_STATUS_BUZY = false;
	public static Long timeExpression = Long.parseLong(TPApplicationProperties.getProperty("csv.scheduler_frequency.minutes"))*60*1000L;
	static {
		try {
			initStepLevelChangeCSVJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for Joined candidate", e);
		}
	}
	
	public static void initStepLevelChangeCSVJob() throws SchedulerException{
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(OtherApplicationConstants.JOB_STEP_LEVEL_CHANGE_CSV)) {		
			stepLevelChangeCSVJob = new JobDetail(OtherApplicationConstants.JOB_STEP_LEVEL_CHANGE_CSV, Scheduler.DEFAULT_GROUP, StepLevelChangeCSVJob.class, false, true, true);
			scheduler.addJob(stepLevelChangeCSVJob, false);
		}
	}
	
	public static void triggerStepLevelChangeCSV(){		
		deleteTrigger();
		addTrigger();
	}
	
	public static void deleteTrigger() {
		TPLogger.getLogger().debug("*************************** - Delete Trigger ++++++++++++++++++++++++++++++ ");
		try {			
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			if (triggerList.contains(OtherApplicationConstants.TRIGGER_STEP_LEVEL_CHANGE)) {
				scheduler.unscheduleJob(OtherApplicationConstants.TRIGGER_STEP_LEVEL_CHANGE, Scheduler.DEFAULT_GROUP);
			}
		}catch(Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	public static void addTrigger() {
		TPLogger.getLogger().debug("*************************** - Add Trigger ++++++++++++++++++++++++++++++ ");
		SimpleTrigger simpleTrigger = null;
		try{		
			long ts = TriggerUtils.getNextGivenSecondDate(null, 30).getTime() + 30000;
			simpleTrigger = new SimpleTrigger(OtherApplicationConstants.TRIGGER_STEP_LEVEL_CHANGE, Scheduler.DEFAULT_GROUP, 
					OtherApplicationConstants.JOB_STEP_LEVEL_CHANGE_CSV, Scheduler.DEFAULT_GROUP, new Date(ts), null, SimpleTrigger.REPEAT_INDEFINITELY,timeExpression);			
			scheduler.scheduleJob(simpleTrigger);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
}