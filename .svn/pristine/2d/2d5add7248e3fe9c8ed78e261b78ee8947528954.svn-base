package com.talentPool.scheduler;

import java.util.TimeZone;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.ThreadLocalContextObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.timeZone.TimeZoneUtils;

public class JobListenerImplementation implements JobListener {
	
	public static final String LISTENER_NAME = "defaultSchedulerJobListener";

	public String getName(){
		return LISTENER_NAME;
	};

    public void jobToBeExecuted(JobExecutionContext context){
//    	String timeZoneId = "";
//		if(context != null){
//			timeZoneId = context.getTrigger().getJobDataMap().getString("timeZoneId");
//		}
//		if (Utils.isBlankOrNull(timeZoneId)){
//			timeZoneId = TimeZone.getDefault().getID();
//		}
//		TimeZone tzone = TimeZone.getTimeZone(timeZoneId);
//		TimeZone.setDefault(tzone);
//		ThreadLocalContextObject obj = new ThreadLocalContextObject();
//		obj.setTimeZone(timeZoneId);
//		MyThreadLocal.set(obj);
    }

    public void jobExecutionVetoed(JobExecutionContext context){}

    public void jobWasExecuted(JobExecutionContext context,
            JobExecutionException jobException){
    	//MyThreadLocal.unset();
    }
	
}
