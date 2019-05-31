/**
 * Created : Oct 10, 2013 1:34:33 PM
 * @author : Sachinm
 */
package com.talentPool.reports.manager;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.CustomizedReportData;

/**
 * @author Sachinm
 *
 */
public class CustomizedReportManager {
	
	private static ArrayList<String> allCustomizedReportNames = new ArrayList<String>();
	
	/**
	 * @return the allCustomizedReportNames
	 */
	public ArrayList<String> getAllCustomizedReportNames() {
		if(Utils.isListEmptyOrNull(allCustomizedReportNames)) {
			ArrayList<CustomizedReportData> customizedReports = getAllCustomizedReports();
			for(CustomizedReportData customizedReport : customizedReports) {
				allCustomizedReportNames.add(customizedReport.getReportName());
			}			
		}
		return allCustomizedReportNames;
	}

	/**
	 * @return list of all customized reports
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<CustomizedReportData> getAllCustomizedReports() {
		ArrayList<CustomizedReportData> reports = new ArrayList<CustomizedReportData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCustomizedReportManager_GetAllReports");
			reports = dq.getResult();
		} catch (Exception exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reports;		
	}

	/**
	 * @param reportName
	 * @return CustomizedReportData
	 */
	public CustomizedReportData getCustomizedReportDataForReport(String reportName) {
		CustomizedReportData crd = new CustomizedReportData();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCustomizedReportManager_GetCustomizedReportDataForReport");
			dq.setString(1, reportName);
			crd = (CustomizedReportData) dq.getSingleObjectResult();
		} catch (Exception exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return crd;
	}

	/**
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<CustomizedReportData> getAllCustomizedReportsForUser(String userId) {
		ArrayList<CustomizedReportData> reports = new ArrayList<CustomizedReportData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCustomizedReportManager_GetAllReportsForUser");
			dq.setInt(1, ReportVersionConstants.REPORT_TYPE_CUSTOMIZED);
			dq.setString(2, userId);
			reports = dq.getResult();
		} catch (Exception exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return reports;
	}
}
