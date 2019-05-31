package com.talentPool.customReports.action;

import org.apache.struts2.ServletActionContext;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.struts2.common.TPActionSupport;

public class FilterAction extends TPActionSupport {

	public String getReportUsersXML() throws Exception {
		String xmlFile = "";
		try {
			ReportUtils reportUtils = new ReportUtils();
			xmlFile = reportUtils.getUsersFilterXMLForReports();
			ServletActionContext.getRequest().setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}
	
}
