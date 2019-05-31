package com.talentPool.parser.converter;

import com.talentPool.common.Logger.TPLogger;

public class JNIProcess implements Runnable, Waiter {
	TimeOut timer;
	String cmd;
	Process process;
	boolean result;

	public JNIProcess(String cmd) {
		this.cmd = cmd;
	}

	public void run() {
		try {
			timer = new TimeOut(this, 1200000);
			Runtime runtime = Runtime.getRuntime();
			result = true;
			this.process = runtime.exec(cmd);
			int status = this.process.waitFor();
			this.process.destroy();
			timer.destroy();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
	}

	public void destroy() {
		try {
			this.process.destroy();
			result = false;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error",e);
		}
	}

	public boolean getProcessResult() {
		return result;
	}
}
