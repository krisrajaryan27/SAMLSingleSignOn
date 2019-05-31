package com.talentPool.reports.views;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

public class hiringEfficiencyReportView extends SimpleDataObject {
	
	public hiringEfficiencyReportView(){
		super();
	}
    
	public String getPositionTitle() {
		return getString("positionTitle");
	}
	public String getApplicantName() {
		return getString("applicantName");
	}
	
	public String getDeptId(){
		return getString("deptId");
	}
	
	public String getDeptName(){
		return getString("deptName");
	}
	public String getDepartmentPosition(){
		return getDeptName()+" : " + getPositionTitle();
	}
	
	public int getNoOfOpenings() {
		return getInt("noOfOpenings");
	}
	public int getNoOfJoined() {
		return getInt("noOfJoined");
	}
	
	public String getDateCreated() {
		try {
			return DateUtils.getSystemDateFormat(getDate("dateCreated"));
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	public String getDateHireBy() {
		try {
			return DateUtils.getSystemDateFormat(getDate("dateHireBy"));
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	
	public String getDateJoined() {
		try {
			return DateUtils.getSystemDateFormat(getDate("dateJoined"));
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	
	public int getMinimumDays(){
		return getInt("minimumDays");
	}
	public int getMaximumDays(){
		return getInt("maximumDays");
	}
	public int getAverageDays(){
		return getInt("averageDays");
	}
	public int getAverageDeviation(){
		return getInt("averageDeviation");
	}
	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return getString("positionId");
	}

	/**
	 * @param positionId
	 *            the positionId to set
	 */
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	
}
