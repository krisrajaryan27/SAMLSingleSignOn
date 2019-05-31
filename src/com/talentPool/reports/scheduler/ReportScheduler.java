/**
 * 
 */
package com.talentPool.reports.scheduler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;

import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.ReportScheduleData;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

public class ReportScheduler {
	private static Scheduler scheduler;

	private static JobDetail reportSchedulerJob;

	static {
		try {
			initReportScheduledJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for Report", e);
		}
	}

	public static void initReportScheduledJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);

		if (!jobList.contains(SchedulerConstants.JOB_REPORT_SCHEDULER)) {			
			reportSchedulerJob = new JobDetail(SchedulerConstants.JOB_REPORT_SCHEDULER, Scheduler.DEFAULT_GROUP, SendScheduledReportJob.class, false, true, true);
			scheduler.addJob(reportSchedulerJob, false);
		}
	}

	public static void addTrigger(ReportScheduleData reportScheduleData) {
		try {
			String cornTriggerName = "";
			CronTrigger cornTrigger = null;
			String cornTimeExpression=getCronString(reportScheduleData);			
			TPLogger.getLogger().debug("Adding trigger for Report Schedule");
			cornTriggerName = SchedulerConstants.TRIGGER_REPORT_SCHEDULER_SUFIX + reportScheduleData.getScheduleId();			
			cornTrigger = new CronTrigger(cornTriggerName, Scheduler.DEFAULT_GROUP, cornTimeExpression);			
			cornTrigger.getJobDataMap().put("scheduleId", ""+reportScheduleData.getScheduleId());
			cornTrigger.setJobName(SchedulerConstants.JOB_REPORT_SCHEDULER);
			cornTrigger.setStartTime(reportScheduleData.getStartDate());
			scheduler.scheduleJob(cornTrigger);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting Scheduled Report", e);
		}
	}

	public static void deleteTrigger(String scheduleId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for report scheduler");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_REPORT_SCHEDULER_SUFIX + scheduleId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

	public static void updateTrigger(ReportScheduleData reportScheduleData, String scheduleId) {
		try {
			// delete the associated triggers and new triggers
			TPLogger.getLogger().debug("updating trigger for report scheduler");
			deleteTrigger(scheduleId + "");
			reportScheduleData.setScheduleId(Integer.parseInt(scheduleId));
			addTrigger(reportScheduleData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating trigger", e);
		}
	}
	private static String getCronString(ReportScheduleData reportScheduleData){
		StringBuffer sb = new StringBuffer();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(reportScheduleData.getScheduleTime());
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		sb.append("0 " + min +" " + hr + " " );
		
		String freq =reportScheduleData.getFrequency();		
		if(freq.equalsIgnoreCase(ReportConstants.REPORT_SCHEDULER_DAILY)){
			if(!Utils.isBlankOrNull(reportScheduleData.getWeekdays())){
				sb.append("? * "+"MON-FRI");				
			}else if(!Utils.isBlankOrNull(reportScheduleData.getEveryday())){				
				sb.append("* * ?");				
			}
		}else if(freq.equalsIgnoreCase(ReportConstants.REPORT_SCHEDULER_WEEKLY)){
			sb.append("? * "+ReportConstants.mapReportSchedulerConstant.get(reportScheduleData.getDayOfWeek()));
		}else if(freq.equalsIgnoreCase(ReportConstants.REPORT_SCHEDULER_MONTHLY)){
			sb.append(reportScheduleData.getDayOfMonth()+" * ?" );
		}	
		
		return sb.toString();
	}
}
