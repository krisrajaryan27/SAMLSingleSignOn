package com.talentPool.parser;

import gate.creole.SerialAnalyserController;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.parser.converter.TimeOut;
import com.talentPool.parser.converter.Waiter;

public class EduExpParserProcess implements Runnable, Waiter {
	
	TimeOut timer;
	SerialAnalyserController  eduExpApp;
	
	public EduExpParserProcess(SerialAnalyserController eduExpApp){
		this.eduExpApp=eduExpApp;
	}
	
	@Override
	public void run() {
		try {
			timer = new TimeOut(this, 30000);
			eduExpApp.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally{
			timer.destroy();
		}
	}

	@Override
	public void destroy() {
		try {
			eduExpApp.interrupt();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
}
