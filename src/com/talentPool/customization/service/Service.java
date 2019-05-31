package com.talentPool.customization.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author SiddharthK
 * Interface for customized report services.
 *
 */
public interface Service {
	   public void setReportListAndParams(ArrayList reportList, HashMap<String, String> params, FilterData filterData, String userId, PermissionSet permissionSet);
	   public String getName();
	   public ArrayList getReportData(FilterData filterData, String userId, PermissionSet permissionSet);
	   public void updateCustomizedReportSchedule(String scheduleId, FilterData filterData) throws SQLException;
}
