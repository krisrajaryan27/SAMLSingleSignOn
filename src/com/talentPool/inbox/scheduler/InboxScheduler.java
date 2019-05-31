package com.talentPool.inbox.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.TPDefaultScheduler;

public class InboxScheduler {
	private static Scheduler scheduler;
	private static JobDetail inboxJob;
	private static HashMap inboxList = null;
	private static HashMap stateList = new HashMap();
	static {
		try {
			initInboxJob();
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to init schedular for appointment", e);
		}
	}

	public static void initInboxJob() throws SchedulerException {
		if (scheduler == null) {
			scheduler = TPDefaultScheduler.getDefaultScheduler();
		}
		// Create a job for calender and then add triggers
		String[] jobNames = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
		List jobList = Arrays.asList(jobNames);
		if (!jobList.contains(SchedulerConstants.JOB_INBOX)) {
			// remmainderjob is volatility=false, durability=true, boolean
			// recover=true
			inboxJob = new JobDetail(SchedulerConstants.JOB_INBOX, Scheduler.DEFAULT_GROUP, TPReceiverJob.class, false, true, true);
			scheduler.addJob(inboxJob, false);
		}
	}

	public static void resetAllReceiver() throws Exception{
			InboxManager inboxManager = new InboxManager();
			inboxList = inboxManager.loadInboxSettings();
			if (inboxList != null) {
				Set rawSet = inboxList.keySet();
				Iterator rawIterator = rawSet.iterator();
				while (rawIterator.hasNext()) {
					InboxData inboxData = (InboxData) inboxList.get((String) rawIterator.next());
					deleteTrigger(inboxData.getInboxId());
					addTrigger(inboxData);
					if (!stateList.containsKey("" + inboxData.getInboxId())) {
						stateList.put("" + inboxData.getInboxId(), "0");
					}
				}
			}
	}

	public static void addTrigger(InboxData inboxData) {
		String triggerName = "";
		SimpleTrigger simpleTrigger = null;
		try {
			//inboxData.setPollingDuration(1);
			if (inboxData != null && inboxData.getPollingDuration() > 0) {
				long ts = TriggerUtils.getNextGivenSecondDate(null, 11).getTime() + 20000;
				triggerName = SchedulerConstants.TRIGGER_INBOX_SUFIX + inboxData.getInboxId();
				simpleTrigger = new SimpleTrigger(triggerName, Scheduler.DEFAULT_GROUP, SchedulerConstants.JOB_INBOX, Scheduler.DEFAULT_GROUP, new Date(ts), null, SimpleTrigger.REPEAT_INDEFINITELY,
						inboxData.getPollingDuration() * 60 * 1000L);
				simpleTrigger.getJobDataMap().put("inboxId", "" + inboxData.getInboxId());
				scheduler.scheduleJob(simpleTrigger);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
	}

	public static void deleteTrigger(int inboxId) {
		try {
			TPLogger.getLogger().debug("deleting trigger for inbox = " + inboxId);
			String[] triggerNames = scheduler.getTriggerNames(Scheduler.DEFAULT_GROUP);
			List triggerList = Arrays.asList(triggerNames);
			// delete trigger to send email to me
			String triggerName = SchedulerConstants.TRIGGER_INBOX_SUFIX + inboxId;
			if (triggerList.contains(triggerName)) {
				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}

	}

	public static synchronized void setinboxThreadState(String inboxId, int state) {
		try {
			stateList.put(inboxId, "" + state);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}

	}

	public static int getInboxThreadState(String inboxId) {
		try {
			return Integer.parseInt((String) stateList.get(inboxId));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
		return 0;
	}

	public static InboxData getInboxData(String inboxId) {
		try {
			return (InboxData) inboxList.get(inboxId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
		return null;
	}

}
