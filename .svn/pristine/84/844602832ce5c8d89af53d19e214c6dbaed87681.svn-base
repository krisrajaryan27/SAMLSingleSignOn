/**
 * 
 */
package com.talentPool.otherApplications.rest.scheduler;

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
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.otherApplications.properties.OtherApplicationProperties;
import com.talentPool.scheduler.TPDefaultScheduler;


/**
 * @author Shantanu
 *
 */
public class PostDataFromGreytipScheduler {
	private static Scheduler scheduler;
	private static JobDetail dataFromGreytipJob;
	public static boolean JOB_STATUS_BUZY = false;
	public static Long timeExpression = Long.parseLong(OtherApplicationProperties.getProperty("greytip.postdata.scheduler_frequency.minutes"))*60*1000L;
	static {
		try {
			initPostDataFromGreytipJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for Employee Code from Greytip", e);
		}
	}
	
	public static void initPostDataFromGreytipJob() throws SchedulerException{
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(OtherApplicationConstants.JOB_POST_DATA_FROM_GREYTIP)) {		
			dataFromGreytipJob = new JobDetail(OtherApplicationConstants.JOB_POST_DATA_FROM_GREYTIP, Scheduler.DEFAULT_GROUP, PostDataFromGreytipJob.class, false, true, true);
			scheduler.addJob(dataFromGreytipJob, false);
		}
	}
	
	public static void triggerPostDataFromGreytip(){		
		deleteTrigger();
		addTrigger();
	}
	
	public static void deleteTrigger() {
		TPLogger.getLogger().debug("*************************** - Delete Trigger ++++++++++++++++++++++++++++++ ");
		try {			
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			if (triggerList.contains(OtherApplicationConstants.TRIGGER_POST_DATA_FROM_GREYTIP)) {
				scheduler.unscheduleJob(OtherApplicationConstants.TRIGGER_POST_DATA_FROM_GREYTIP, Scheduler.DEFAULT_GROUP);
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
			simpleTrigger = new SimpleTrigger(OtherApplicationConstants.TRIGGER_POST_DATA_FROM_GREYTIP, Scheduler.DEFAULT_GROUP, 
					OtherApplicationConstants.JOB_POST_DATA_FROM_GREYTIP, Scheduler.DEFAULT_GROUP, new Date(ts), null, SimpleTrigger.REPEAT_INDEFINITELY,timeExpression);			
			scheduler.scheduleJob(simpleTrigger);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

}
