package com.talentPool.positions.action;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.scheduler.UpdateMasterTablesJob;
import com.talentPool.customReports.scheduler.UpdateMasterTablesScheduler;
import com.talentPool.positions.manager.PositionStatusScheduleManager;
import com.talentPool.requisition.scheduler.RequisitionAutoApprovalJob;
import com.talentPool.requisition.scheduler.UpdatePositionStatusScheduler;
import com.talentPool.scheduler.dataobject.SchedulerData;
import com.talentPool.scheduler.manager.SchedulerManager;
import com.talentPool.scheduler.utils.SchedulerUtils;
import com.talentPool.struts2.common.TPActionSupport;

@SuppressWarnings("serial")
public class PositionStatusAction extends TPActionSupport{
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

	
	public String positionStatus() throws Exception {
		PositionStatusScheduleManager schedulerManager = new PositionStatusScheduleManager();
		try {
			SchedulerData schedulerData = schedulerManager.getSchedulerInfo();
			if(schedulerData==null){
				
			}
			else{
			getRequest().setAttribute("startHour", Utils.getDateStringConvertedToOtherDateFormat(schedulerData.getSchedulerStartHour(), Utils.regDDMMYYYYHHMMSSsssFromat, "HH"));
			getRequest().setAttribute("frequency", schedulerData.getSchedulerFrequency());
			getRequest().setAttribute("lastStatus", SchedulerUtils.getSchedulerStatusAsText(schedulerData.getLastRunStatus()));
			getRequest().setAttribute("lastStartTime", schedulerData.getLastRunStarttime());
			getRequest().setAttribute("lastEndTime", schedulerData.getLastRunEndtime());
			getRequest().setAttribute("currentStatus", SchedulerUtils.getCurrentStatusAsText(schedulerData.getCurrentStatus()));
			}
			} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String savePositionStatusProperties() throws Exception {
		if(Utils.isBlankOrNull(getStartHour()) && Utils.isBlankOrNull(getFrequency())){
			TPLogger.getLogger().debug("No changes in scheduler properties");
			return SUCCESS;
		}
		
		PositionStatusScheduleManager schedulerManager = new PositionStatusScheduleManager();
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
			getRequest().setAttribute("startHour", Utils.getDateStringConvertedToOtherDateFormat(schedulerData.getSchedulerStartHour(), Utils.regDDMMYYYYHHMMSSsssFromat, "HH"));
			
			UpdatePositionStatusScheduler.updateScheduler();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public synchronized String runPositionStatusScheduler() throws Exception {
		RequisitionAutoApprovalJob manager = new RequisitionAutoApprovalJob();
		PositionStatusScheduleManager schedulerManager = new PositionStatusScheduleManager();
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
}
