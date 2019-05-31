package com.talentPool.common.Logger.scheduler;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

public class LogFileArchiveSchedular {
	private static Scheduler scheduler;
	private static JobDetail logFileArchiveJob;
	public static boolean JOB_STATUS_BUZY = false;

	static {
		try {
			initLogFileArchiveJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for mass email", e);
		}
	}

	public static void initLogFileArchiveJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_LOG_FILE_ARCHIVE)) {
		
			logFileArchiveJob = new JobDetail(SchedulerConstants.JOB_LOG_FILE_ARCHIVE, Scheduler.DEFAULT_GROUP, LogFileArchiveJob.class, false, true, true);
			scheduler.addJob(logFileArchiveJob, false);
		}
	}
	public static void triggerLogFileArchive(){
		deleteTrigger();
		addTrigger();
	}
	public static void addTrigger() {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			long ts = TriggerUtils.getNextGivenSecondDate(null, 30).getTime() + 30000;
			triggerName = SchedulerConstants.TRIGGER_LOG_FILE_ARCHIVE;
			simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_LOG_FILE_ARCHIVE, Scheduler.DEFAULT_GROUP, new Date(ts), null, SimpleTrigger.REPEAT_INDEFINITELY,
					7*24*60*60*1000L);
			simpleTrigger.getJobDataMap().put("zipFolderPath",Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("archive.folder.zipsPath")));
			simpleTrigger.getJobDataMap().put("logFolderPath", Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"),TPApplicationProperties.getProperty("archive.folder.logsPath")));
			simpleTrigger.getJobDataMap().put("logFileDateFormat", TPApplicationProperties.getProperty("archive.pattern.logFileDate"));
			simpleTrigger.getJobDataMap().put("zipFileDateFormat", TPApplicationProperties.getProperty("archive.pattern.zipFileDate"));
			simpleTrigger.getJobDataMap().put("archiveLogFileNames", TPApplicationProperties.getProperty("archive.filesNames.logs"));
		
			scheduler.scheduleJob(simpleTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
	}

	public static void deleteTrigger() {
		try {
			TPLogger.getLogger().debug("deleting trigger for mass email = ");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_LOG_FILE_ARCHIVE;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}

	}
	
}
