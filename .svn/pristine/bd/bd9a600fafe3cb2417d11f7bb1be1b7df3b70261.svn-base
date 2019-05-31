/**
 * 
 */
package com.talentPool.reports.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.MasterReportData;
import com.talentPool.reports.form.ReportForm;
import com.talentPool.reports.manager.MasterReportManager;
import com.talentPool.reports.utils.MasterReportCapitaUtils;
import com.talentPool.reports.utils.MasterReportUtils;
import com.talentPool.reports.utils.MasterReportZensarUtils;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author Ajeet
 * 
 */
public class MasterReportAction extends TPDispatchAction {

	public ActionForward createMmasterReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewMasterReport";
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			ReportForm reportForm = (ReportForm) actionForm;
			FilterData filterData = reportForm.getFilterData();
			ReportGenerator reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			MasterReportManager masterReportManager = new MasterReportManager();
			ArrayList<MasterReportData> applicantData = masterReportManager.getApplicantsMasterData(filterData,permissionSet);
			String newFileName = "";
			
			//for custom clients-zensar
			String customReport = TPApplicationProperties.getProperty("master.report.custom_report");
			if(customReport.equals("zensar")){
				newFileName = MasterReportZensarUtils.generateZensarMasterReport(applicantData, filterData,permissionSet);
			}else if(customReport.equals("capita")){
				newFileName = MasterReportCapitaUtils.generateCapitaMasterReport(applicantData, filterData,permissionSet);
			}else{
				newFileName = MasterReportUtils.generateMasterReport(applicantData, filterData,permissionSet);
			}		
			
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			String filePath = Utils.concatFilePath(destinationPath, newFileName);
			request.setAttribute("filePath", filePath);
			String filePostFix = Utils.getDateConvertedToString(Calendar.getInstance().getTime(), "-dd-MMM-yyyy");
			String fileNameToDisplay = TPLabels.getLabel("master.report.file_name_prefix") + filePostFix + ".xls";
			request.setAttribute("fileNameToDisplay", fileNameToDisplay);
		} catch (Exception e) {
			TPLogger.getLogger().error("Caught Exception while generating Master Report", e);
		}
		return mapping.findForward(forward);
	}	
	
	public ActionForward createMasterDataReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewMasterReport";
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			ReportForm reportForm = (ReportForm) actionForm;
			FilterData filterData = reportForm.getFilterData();
			ReportGenerator reportGenerator = new ReportGenerator();
			reportGenerator.setReportsToAndFromDate(filterData);
			MasterReportManager masterReportManager = new MasterReportManager();
			ArrayList<MasterReportData> applicantData = masterReportManager.getApplicantsMasterData(filterData,permissionSet);
			String newFileName = "";
			
			//for custom clients-zensar
			
			newFileName = MasterReportUtils.generateMasterDataReport(applicantData, filterData,permissionSet);
			
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			String filePath = Utils.concatFilePath(destinationPath, newFileName);
			request.setAttribute("filePath", filePath);
			String filePostFix = Utils.getDateConvertedToString(Calendar.getInstance().getTime(), "-dd-MMM-yyyy");
			String fileNameToDisplay = TPLabels.getLabel("master.report.file_name_prefix") + filePostFix + ".xls";
			request.setAttribute("fileNameToDisplay", fileNameToDisplay);
		} catch (Exception e) {
			TPLogger.getLogger().error("Caught Exception while generating Master Report", e);
		}
		return mapping.findForward(forward);
	}

}
