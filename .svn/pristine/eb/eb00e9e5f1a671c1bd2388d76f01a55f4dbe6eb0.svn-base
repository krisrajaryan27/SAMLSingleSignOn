package com.talentPool.scheduler.action;

import javax.servlet.http.HttpServletRequest;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.scheduler.UpdateMasterTablesJob;
import com.talentPool.customReports.scheduler.UpdateMasterTablesScheduler;
import com.talentPool.customReports.scheduler.UpdateUsersJob;
import com.talentPool.customReports.scheduler.UpdateUsersScheduler;
import com.talentPool.scheduler.dataobject.SchedulerData;
import com.talentPool.scheduler.manager.SchedulerManager;
import com.talentPool.scheduler.manager.UserSchedulerManager;
import com.talentPool.scheduler.utils.SchedulerUtils;
import com.talentPool.struts2.common.TPActionSupport;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Sachin
 * @since  March 13, 2012
 */

@SuppressWarnings("serial")
public class SchedulerAction extends TPActionSupport {
	
	private String startHour;
	private String frequency;

	/**
	 * @return the startHour
	 */
	public String getStartHour() {
		return startHour;
	}

	/**
	 * @param startHour the startHour to set
	 */
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	
	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	
	public String schedulerStatus() throws Exception {
		SchedulerManager schedulerManager = new SchedulerManager();
		try {
			SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
			getRequest().setAttribute("startHour", Utils.getDateStringConvertedToOtherDateFormat(schedulerData.getSchedulerStartHour(), Utils.regDDMMYYYYHHMMSSsssFromat, "HH"));
			getRequest().setAttribute("frequency", schedulerData.getSchedulerFrequency());
			getRequest().setAttribute("lastStatus", SchedulerUtils.getSchedulerStatusAsText(schedulerData.getLastRunStatus()));
			getRequest().setAttribute("lastStartTime", schedulerData.getLastRunStarttime());
			getRequest().setAttribute("lastEndTime", schedulerData.getLastRunEndtime());
			getRequest().setAttribute("currentStatus", SchedulerUtils.getCurrentStatusAsText(schedulerData.getCurrentStatus()));
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	//simple validation
			public void validate(){
				PermissionSet permissionSet=getPermissionSet();
				TPDispatchAction tPDispatchAction=new TPDispatchAction();
				HttpServletRequest request=getRequest();
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
				if(!tPDispatchAction.isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
					addActionError("Users do not have permission");	
					
				}
			}
	
	public String saveSchedulerProperties() throws Exception {
		if(Utils.isBlankOrNull(getStartHour()) && Utils.isBlankOrNull(getFrequency())){
			TPLogger.getLogger().debug("No changes in scheduler properties");
			return SUCCESS;
		}
		
		SchedulerManager schedulerManager = new SchedulerManager();
		try {
			String startHour = getStartHour();
//			Calendar cal = Calendar.getInstance();
//			cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(startHour));
//			System.out.println(MyThreadLocal.get().toString());
//			startHour = Utils.getDateConvertedToStringWithTimezone(cal.getTime(), Utils.regDDMMYYYYHHMMSSsssFromat, MyThreadLocal.get().toString());
			int frequency = Integer.parseInt(getFrequency());
			
			schedulerManager.updateSchedulerProperties(startHour, frequency);
			
			SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
			getRequest().setAttribute("lastStatus", SchedulerUtils.getSchedulerStatusAsText(schedulerData.getLastRunStatus()));
			getRequest().setAttribute("lastStartTime", schedulerData.getLastRunStarttime());
			getRequest().setAttribute("lastEndTime", schedulerData.getLastRunEndtime());
			getRequest().setAttribute("currentStatus", SchedulerUtils.getCurrentStatusAsText(schedulerData.getCurrentStatus()));
			
			UpdateMasterTablesScheduler.updateScheduler();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public synchronized String runScheduler() throws Exception {
		UpdateMasterTablesJob manager = new UpdateMasterTablesJob();
		SchedulerManager schedulerManager = new SchedulerManager();
		try {
			manager.execute(null);
			
			SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
			getRequest().setAttribute("startHour", Utils.getDateStringConvertedToOtherDateFormat(schedulerData.getSchedulerStartHour(), Utils.regDDMMYYYYHHMMSSsssFromat, "HH:mm"));
			getRequest().setAttribute("frequency", schedulerData.getSchedulerFrequency());
			getRequest().setAttribute("lastStatus", SchedulerUtils.getSchedulerStatusAsText(schedulerData.getLastRunStatus()));
			getRequest().setAttribute("lastStartTime", schedulerData.getLastRunStarttime());
			getRequest().setAttribute("lastEndTime", schedulerData.getLastRunEndtime());
			getRequest().setAttribute("currentStatus", SchedulerUtils.getCurrentStatusAsText(schedulerData.getCurrentStatus()));
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String userSchedulerStatus() throws Exception {
		UserSchedulerManager schedulerManager = new UserSchedulerManager();
		try {
			SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
			getRequest().setAttribute("startHour", schedulerData.getSchedulerStartHour());
			getRequest().setAttribute("frequency", schedulerData.getSchedulerFrequency());
			getRequest().setAttribute("lastStatus", SchedulerUtils.getSchedulerStatusAsText(schedulerData.getLastRunStatus()));
			getRequest().setAttribute("lastStartTime", schedulerData.getLastRunStarttime());
			getRequest().setAttribute("lastEndTime", schedulerData.getLastRunEndtime());
			getRequest().setAttribute("currentStatus", SchedulerUtils.getCurrentStatusAsText(schedulerData.getCurrentStatus()));
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String saveUserSchedulerProperties() throws Exception {
		if(Utils.isBlankOrNull(getStartHour()) && Utils.isBlankOrNull(getFrequency())){
			TPLogger.getLogger().debug("No changes in scheduler properties");
			return SUCCESS;
		}
		
		UserSchedulerManager schedulerManager = new UserSchedulerManager();
		try {
			int startHour = Integer.parseInt(getStartHour());
			int frequency = Integer.parseInt(getFrequency());
			
			schedulerManager.updateSchedulerProperties(startHour, frequency);
			
			SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
			getRequest().setAttribute("lastStatus", SchedulerUtils.getSchedulerStatusAsText(schedulerData.getLastRunStatus()));
			getRequest().setAttribute("lastStartTime", schedulerData.getLastRunStarttime());
			getRequest().setAttribute("lastEndTime", schedulerData.getLastRunEndtime());
			getRequest().setAttribute("currentStatus", SchedulerUtils.getCurrentStatusAsText(schedulerData.getCurrentStatus()));
			
			UpdateUsersScheduler.updateScheduler();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public synchronized String runUserScheduler() throws Exception {
		UpdateUsersJob manager = new UpdateUsersJob();
		UserSchedulerManager schedulerManager = new UserSchedulerManager();
		try {
			manager.execute(null);
			
			SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
			getRequest().setAttribute("startHour", schedulerData.getSchedulerStartHour());
			getRequest().setAttribute("frequency", schedulerData.getSchedulerFrequency());
			getRequest().setAttribute("lastStatus", SchedulerUtils.getSchedulerStatusAsText(schedulerData.getLastRunStatus()));
			getRequest().setAttribute("lastStartTime", schedulerData.getLastRunStarttime());
			getRequest().setAttribute("lastEndTime", schedulerData.getLastRunEndtime());
			getRequest().setAttribute("currentStatus", SchedulerUtils.getCurrentStatusAsText(schedulerData.getCurrentStatus()));
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
}
