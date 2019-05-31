package com.talentPool.parser.converter;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad starts a thread which will wait for given duration, on
 *         timeout it will call the destroy method of the waiter object it have
 */
public class TimeOut implements Runnable {
	Thread timeoutThread;
	Waiter waiter;
	long duration;

	public TimeOut(Waiter waiter, long duration) {
		this.waiter = waiter;
		this.duration = duration;
		timeoutThread = new Thread(this);
		timeoutThread.start();
	}

	public void run() {
		try {
			TPLogger.getLogger().debug("waiting for " + duration + " secs");
			Thread.sleep(duration);
			TPLogger.getLogger().debug("Timeout Resumed after wait");
			waiter.destroy();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
	}

	public void destroy() {
		try {
			timeoutThread.stop();
			timeoutThread = null;
		} catch (Exception e) {
			// TODO: handle exception
			TPLogger.getLogger().debug("Error while destroying timer thread", e);
		}
	}
}
