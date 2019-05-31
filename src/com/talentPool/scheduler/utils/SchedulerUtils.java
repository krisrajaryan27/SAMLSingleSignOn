/**
 * 
 */
package com.talentPool.scheduler.utils;

import com.talentPool.scheduler.SchedulerConstants;

/**
 * @author Sachin
 * @since  Mar 13, 2012
 */
public class SchedulerUtils {
	
	public static String getSchedulerStatusAsText(String schedulerStatus){
		if(SchedulerConstants.SCHEDULER_LAST_STATUS_SUCCESS.equals(schedulerStatus)){
			return "SUCCESS";
		}else if(SchedulerConstants.SCHEDULER_LAST_STATUS_FAIL.equals(schedulerStatus)){
			return "FAIL";
		}else{
			return "-";
		}
	}
	
	public static String getCurrentStatusAsText(String currentStatus){
		if(SchedulerConstants.SCHEDULER_CURRENT_STATUS_IDLE.equals(currentStatus)){
			return "IDLE";
		}else if(SchedulerConstants.SCHEDULER_CURRENT_STATUS_RUNNING.equals(currentStatus)){
			return "RUNNING";
		}else{
			return "-";
		}
	}
	
}
