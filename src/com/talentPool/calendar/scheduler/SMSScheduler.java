/**
 * 
 */
package com.talentPool.calendar.scheduler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;

import com.talentPool.calendar.dataobject.AppointmentData;
import com.talentPool.common.Logger.TPLogger;

import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

/**
 * @author pallavi
 * 
 */
public class SMSScheduler {
	private static Scheduler scheduler;
	private static JobDetail smsRemainderJob;

	static {
		try {
			initSMSScheduledJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for sms", e);
		}
	}

	public static void initSMSScheduledJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_SMS_REMAINDER)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			smsRemainderJob = new JobDetail(SchedulerConstants.JOB_SMS_REMAINDER, Scheduler.DEFAULT_GROUP, SendSMSRemainders.class, false, true, true);
			scheduler.addJob(smsRemainderJob, false);
		}
	}

	public static void addTrigger(AppointmentData aData) {
		try {
			String triggerName = "";
			SimpleTrigger simpleTrigger = null;
			Date odt = aData.getAppointmentFromDateTime();

			TPLogger.getLogger().debug("Adding trigger for sms");
			
			// SET TRIGGERS FOR ME, INTERVIEWER, CANDIDATE
			if (Integer.parseInt(aData.getSMSRemindMe()) != 0) {
				// Date dt = new Date(odt.getTime());
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(odt);
				cal.add(Calendar.MINUTE, 0 - Integer.parseInt(aData.getSMSRemindMe()));
				Date dt = cal.getTime();
				// dt.setMinutes(dt.getMinutes() -
				// Integer.parseInt(aData.getRemindMe()));
				// construct unique trigger name
				// set trigger to send email to me
				triggerName = SchedulerConstants.TRIGGER_SMS_SUFIX + aData.getAppointmentId() + "_" + SchedulerConstants.TRIGGER_APPOINTMENT_ME;
				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, dt);
				simpleTrigger.getJobDataMap().put("appointmentId", aData.getAppointmentId());
				simpleTrigger.getJobDataMap().put("appointmentWith", SchedulerConstants.TRIGGER_APPOINTMENT_ME);
				simpleTrigger.setJobName(SchedulerConstants.JOB_SMS_REMAINDER);
				scheduler.scheduleJob(simpleTrigger);
			}

			if (Integer.parseInt(aData.getSMSRemindInterviewer()) != 0) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(odt);
				cal.add(Calendar.MINUTE, 0 - Integer.parseInt(aData.getSMSRemindInterviewer()));
				Date dt = cal.getTime();
//				Date dt = new Date(odt.getTime());
//				dt.setMinutes(dt.getMinutes() - Integer.parseInt(aData.getRemindInterviewer()));
				// set trigger to send email to interviewer
				triggerName = SchedulerConstants.TRIGGER_SMS_SUFIX + aData.getAppointmentId() + "_" + SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER;
				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, dt);
				simpleTrigger.getJobDataMap().put("appointmentId", aData.getAppointmentId());
				simpleTrigger.getJobDataMap().put("appointmentWith", SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER);
				simpleTrigger.setJobName(SchedulerConstants.JOB_SMS_REMAINDER);
				scheduler.scheduleJob(simpleTrigger);
			}

			if (Integer.parseInt(aData.getSMSRemindCandidate()) != 0) {
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(odt);
				cal.add(Calendar.MINUTE, 0 - Integer.parseInt(aData.getSMSRemindCandidate()));
				Date dt = cal.getTime();

//				Date dt = new Date(odt.getTime());
//				dt.setMinutes(dt.getMinutes() - Integer.parseInt(aData.getRemindCandidate()));
				// set trigger to send email to candidate
				triggerName = SchedulerConstants.TRIGGER_SMS_SUFIX + aData.getAppointmentId() + "_" + SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE;
				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, dt);
				simpleTrigger.getJobDataMap().put("appointmentId", aData.getAppointmentId());
				simpleTrigger.getJobDataMap().put("appointmentWith", SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE);
				simpleTrigger.setJobName(SchedulerConstants.JOB_SMS_REMAINDER);
				scheduler.scheduleJob(simpleTrigger);
			}

		} catch (SchedulerException se) {
			TPLogger.getLogger().error("Unable to schedule trigger for sms", se);
		}
	}

	public static void deleteTrigger(String appointmentId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for sms");
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_SMS_SUFIX + appointmentId + "_" + SchedulerConstants.TRIGGER_APPOINTMENT_ME;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
			// delete trigger to send email to interviewer
			triggerName = SchedulerConstants.TRIGGER_SMS_SUFIX + appointmentId + "_" + SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
			// delete trigger to send email to candidate
			triggerName = SchedulerConstants.TRIGGER_SMS_SUFIX + appointmentId + "_" + SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

	public static void updateTrigger(AppointmentData aData) {
		try {
			// delete the associated triggers and new triggers
			TPLogger.getLogger().debug("updating trigger for sms");
			deleteTrigger(aData.getAppointmentId());
			addTrigger(aData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting trigger", e);
		}
	}

}
