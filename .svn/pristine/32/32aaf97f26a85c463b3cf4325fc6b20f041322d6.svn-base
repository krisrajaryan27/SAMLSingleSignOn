package com.talentPool.inbox.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;

public class TPReceiverJob implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String inboxId = context.getTrigger().getJobDataMap().getString("inboxId");
		try {
			int isAlreadyRunning = InboxScheduler.getInboxThreadState(inboxId);
			TPLogger.getLogger().debug("IN JOB START EMAIL RECEIVER FOR inboxID = " + inboxId);
			TPLogger.getLogger().debug("AM I RUNNING ALREADY ? WHAT SAYS = " + isAlreadyRunning);
			if (isAlreadyRunning == 0) {
				receive(inboxId);
			} else {
				TPLogger.getLogger().debug("Quit silently");
			}
			TPLogger.getLogger().debug("========= JOB EXIT =============");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

	public void receive(String inboxId) {
		try {
			InboxScheduler.setinboxThreadState(inboxId, 1);
			TPEmailReceiver receiver = new TPEmailReceiver(InboxScheduler.getInboxData(inboxId));
			receiver.fetchAllMessages();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			InboxScheduler.setinboxThreadState(inboxId, 0);
		}

	}
}
