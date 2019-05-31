/**
 * 
 */
package com.talentPool.reportDesign.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.admin.dataobject.ReportLevelData;
import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.costs.dataobject.CostTypeData;
import com.talentPool.costs.manager.CostTypeManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.form.ReportDesignForm;
import com.talentPool.reportDesign.manager.ReportDesignManager;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reportDesign.utils.ReportDesignUtils;
import com.talentPool.reportDesign.utils.XmlUtils;
import com.talentPool.reports.ReportUtils;
import com.talentPool.user.UserConstants;
import com.talentPool.user.manager.SessionManager;


/**
 * @author Ajeet
 *
 */
public class ReportDesignAction extends TPDispatchAction {

	public ActionForward designNewReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectReportFormat";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}
	
	public ActionForward editReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectReportFormat";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;

		try {
			ReportDesignManager designManager = new ReportDesignManager();
			ReportData reportData = designManager.getReportData(reportDesignForm.getReportId());
			ReportDesignUtils.populateReportDataInReportDesignForm(reportData, reportDesignForm);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}
	
	public ActionForward selectReportFormat(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectReportFormat";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		if(Utils.isBlankOrNull(reportDesignForm.getReportFormat())){
			reportDesignForm.setReportFormat(ReportDesignConstants.REPORT_FORMAT_TABULAR);
		}
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}
	
	public ActionForward selectReportType(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectReportType";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		if(Utils.isBlankOrNull(reportDesignForm.getReportFormat())){
			reportDesignForm.setReportFormat(ReportDesignConstants.REPORT_FORMAT_TABULAR);
		}
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}
	
	public ActionForward selectColumns(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectColumns";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		try {
			HashMap<String, LinkedHashMap<String, String>> columnList = ColumnUtils.getColumnList(reportDesignForm.getReportType());
			request.setAttribute("columnList", columnList);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}

	public ActionForward orderColumns(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "orderColumns";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		try {

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}
	
	public ActionForward selectSortByColumn(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectSortByColumn";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;	
		if(Utils.isBlankOrNull(reportDesignForm.getSortBy())){
			String[] columns = reportDesignForm.getColumns().split(",");
			reportDesignForm.setSortBy(columns[0]);
		}
		request.setAttribute("t", NavigationConstants.T_REPORT);
		request.setAttribute("columns", reportDesignForm.getColumns());
		return mapping.findForward(forward);
	}
	
	public ActionForward selectFilters(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectFilters";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		try {
			request.setAttribute("reportType", reportDesignForm.getReportType());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}
	
	public ActionForward reportDescription(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "reportDescription";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		ArrayList<ReportLevelData> reportLevels = null;
		try {
			AdminManager adminManager = new AdminManager();
			reportLevels = adminManager.getAllReportLevels();		
			if( Utils.isBlankOrNull(reportDesignForm.getIsSharedReport()))
				reportDesignForm.setIsSharedReport(ReportDesignConstants.REPORT_ACCESS_PRIVATE);
			request.setAttribute("reportType", reportDesignForm.getReportType());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("reportLevels",reportLevels);
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}
	
	public ActionForward saveReportDesign(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showReports";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			ReportDesignManager designManager = new ReportDesignManager();
			if(Utils.isBlankOrNull(reportDesignForm.getReportId())){
				designManager.saveReportDesign(reportDesignForm.getReportType(), reportDesignForm.getReportName(), 
				reportDesignForm.getReportDescription(), reportDesignForm.getGroupBy(),reportDesignForm.getSortBy(), 
				reportDesignForm.getSortWith(),	reportDesignForm.getColumns(), reportDesignForm.getFilters(),
				reportDesignForm.getIsSharedReport(),reportDesignForm.getLevelPermissions(),userId,
				reportDesignForm.getReportFormat(), reportDesignForm.getReportFilePath(), reportDesignForm.getSheetIndex(), reportDesignForm.getRowIndex());
			}
			else{
				designManager.updateReportDesign(reportDesignForm.getReportId(),reportDesignForm.getReportType(), 
						reportDesignForm.getReportName(), reportDesignForm.getReportDescription(), reportDesignForm.getGroupBy(),
						reportDesignForm.getSortBy(), reportDesignForm.getSortWith(), reportDesignForm.getColumns(), 
						reportDesignForm.getFilters(),reportDesignForm.getIsSharedReport(),reportDesignForm.getLevelPermissions(),
						userId, reportDesignForm.getReportFormat(), reportDesignForm.getReportFilePath(), 
						reportDesignForm.getSheetIndex(), reportDesignForm.getRowIndex());
			
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	} 
	
	public ActionForward getSelectedColumns(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		try {
			ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
			String columns = reportDesignForm.getColumns();
			XmlUtils xmlUtils = new XmlUtils();
			String xmlFile = xmlUtils.getXMLColumns(columns);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return mapping.findForward(forward);
	}
	
	public ActionForward getExpenseTypesInXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			CostTypeManager costTypeManager = new CostTypeManager();
			ArrayList<CostTypeData> costTypes = costTypeManager.getCostTypes();
			xmlFile = XmlUtils.getXMLFromMasterDataList(costTypes,"itemId","itemName");			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}
	
	public ActionForward getActivityTypesInXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			HashMap activityTypesMap = ReportDesignUtils.getActivityTypesMap();
			xmlFile = XmlUtils.getXMLFromDataMap(activityTypesMap);			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}
	
	public ActionForward getUsersInXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			PositionManager positionManager = new PositionManager();
			ArrayList users = positionManager.getUsersForRole(UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_RECRUITER);
			xmlFile = XmlUtils.getXMLFromMasterDataList(users,"userId","name");	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}
	
	
	public ActionForward uploadReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "uploadReport";
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}

	public ActionForward selectReportSheet(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "selectReportSheet";
		ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
		String filePath = reportDesignForm.getReportFilePath();
		String ext = filePath.substring(filePath.length() - 4, filePath.length());
		ActionErrors errors = null;
		if (ext.equals(".xls") || ext.equals("xlsx")) {
		//if (ext.equals(".xls")) {
			String sheetArray = ReportDesignUtils.getReportSheetArray(filePath);
			request.setAttribute("sheetArray", sheetArray);
		} else {
			reportDesignForm.setReportFilePath(null);
			errors = new ActionErrors();
			errors.add("common.please_upload.one_param", new ActionError("common.please_upload.one_param", TPLabels.getLabel("common.excel_file") + " " + TPLabels.getLabel("common.to") + " "
					+ TPLabels.getLabel("common.import")));
			saveErrors(request, errors);
			forward = "uploadReport";
		}
		
		request.setAttribute("t", NavigationConstants.T_REPORT);
		return mapping.findForward(forward);
	}

	public ActionForward columnMapping(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "columnMapping";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}		
		try {
			ReportDesignForm reportDesignForm = (ReportDesignForm) actionForm;
			String filePath = reportDesignForm.getReportFilePath();
			int sheetAt = Integer.parseInt(reportDesignForm.getSheetIndex());
			int rowAt = Integer.parseInt(reportDesignForm.getRowIndex());
			ArrayList<String> excelFieldName = ReportUtils.excelFieldName(filePath,sheetAt,rowAt);
			request.setAttribute("excelFieldName", excelFieldName);
			
			request.setAttribute("columns", reportDesignForm.getColumns());
			request.setAttribute("t", NavigationConstants.T_REPORT);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
}
