package com.talentPool.reports.views;

import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;

public class monthlyJoiningView extends SimpleDataObject {

	public monthlyJoiningView() {
		super();
	}

	public String getApplicantName() {
		return getString("applicantName");
	}

	public Date getDateJoined() {
		Date dt = null;
		try {
			dt = Utils.convertToDate(getString("dateJoined"), Utils.redYYYYMMDDFormat);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			// TODO: handle exception
		}
		return dt;
	}

	public Date getMonthJoined() {
		Date dt = null;
		try {
			dt = Utils.convertToDate(getString("monthJoined"), Utils.redYYYYMMDDFormat);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			// TODO: handle exception
		}
		return dt;
	}
	
	public String getPositionTitle() {
		return getString("positionTitle");
	}

	public String getDeptName() {
		return getString("deptName");
	}

	public int getNoOfJoined() {
		return getInt("noOfJoined");
	}
	public String getDepartmentPosition(){
		return getDeptName() + " : " + getPositionTitle();
	}

}