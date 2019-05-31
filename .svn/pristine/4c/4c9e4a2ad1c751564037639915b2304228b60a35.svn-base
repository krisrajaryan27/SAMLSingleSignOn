package com.talentPool.scheduler;

import java.util.TimeZone;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.ThreadLocalContextObject;
import com.talentPool.common.utils.Utils;

public class TriggerListenerImplementation implements TriggerListener {

	public static final String LISTENER_NAME = "defaultSchedulerTriggerListener";

	public String getName(){
		return LISTENER_NAME;
	};

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		String timeZoneId = "";
		if(context != null){
			timeZoneId = context.getTrigger().getJobDataMap().getString("timeZoneId");
		}
		if (Utils.isBlankOrNull(timeZoneId)){
			timeZoneId = TimeZone.getDefault().getID();
		}
		//TimeZone tzone = TimeZone.getTimeZone(timeZoneId);
		//TimeZone.setDefault(tzone);
		ThreadLocalContextObject obj = new ThreadLocalContextObject();
		obj.setTimeZone(timeZoneId);
		MyThreadLocal.set(obj);
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			int triggerInstructionCode) {
		MyThreadLocal.unset();
	}

}
