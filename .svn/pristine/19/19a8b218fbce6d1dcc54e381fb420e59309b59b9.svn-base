/**
 * Created : Oct 10, 2013 5:27:42 PM
 * @author : Sachinm
 */
package com.talentPool.reports.action;

import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.customization.service.Service;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.manager.CustomizedReportManager;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Sachinm
 *
 */
public class CustomizedReportGenerator {

	/**
	 * @param reportName
	 * @param filterData
	 * @param userId
	 * @param permissionSet
	 * @param sessionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String generateCustomizedReport(String reportName, FilterData filterData, String userId, 
			PermissionSet permissionSet, String sessionId, String clientIpAddr) {
		
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;

		try {
			String jrxml = new CustomizedReportManager().getCustomizedReportDataForReport(reportName).getReportJrxml();
			ReportManager reportManager = new ReportManager();	
			
			ReportGenerator reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			
			/*
			 * Convention decided for service class name which will fetch data for report is servicepackage + reportName with first letter in caps
			 * and a constant 'ReportService' appended at the end.
			 * 
			 */
			
			String serviceName = ReportUtils.getServiceNameForCustomizedReport(reportName);
			
			HashMap<String, String> params = new HashMap<String, String>();
			ArrayList reportList = new ArrayList();
			
			if (!Utils.isBlankOrNull(jrxml)){
				try{
					Class<Service> serv = (Class<Service>) Class.forName(serviceName);
					Service reportService= serv.newInstance();
					reportList = reportService.getReportData(filterData, userId, permissionSet);
					
					reportService.setReportListAndParams(reportList, params, filterData, userId, permissionSet);
					reportManager.generateReport(jrxml, outputFileName, params,
							reportList, filterData.getReportFormat(), userId, clientIpAddr);
					
				}catch (ClassNotFoundException e){
					TPLogger.getLogger().error("Report Class is not present.");
					reportManager.generateErrorReport(outputFileName, params, reportList, ReportConstants.FORMAT_HTML, userId, clientIpAddr);
				}
			} else {
				reportManager.generateErrorReport(outputFileName, params, reportList, ReportConstants.FORMAT_HTML, userId, clientIpAddr);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generation report : " + reportName, e);
		}
		
		return outputFileName;
	}		

}
