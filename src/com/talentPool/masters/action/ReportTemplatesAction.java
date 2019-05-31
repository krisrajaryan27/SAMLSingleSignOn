/**
 *
 */
package com.talentPool.masters.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.dynamicReports.form.ReportTemplatesForm;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.ReportTemplateData;
import com.talentPool.masters.manager.ReportTemplateManager;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reportDesign.utils.ReportDesignUtils;
import com.talentPool.reports.ReportUtils;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author praveen
 * 
 */
public class ReportTemplatesAction extends TPDispatchAction {
	
	public ActionForward manageReportTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageReportTemplates";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		//permissions[1] = PermissionConstants.PERMISSION_TEMPLATE_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_REPORTS);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward addReportTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addReportTemplate";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ReportTemplatesForm reportTemplatesForm = (ReportTemplatesForm) actionForm;
		ReportTemplateManager reportTemplateManager = null;
		ReportTemplateData reportTemplateData = null;
		String sheetDetails = "new Array()";
		try {
			reportTemplateManager = new ReportTemplateManager();
			if(!Utils.isBlankOrNull(reportTemplatesForm.getReportTemplateId())){
				reportTemplateData = reportTemplateManager.getReportTemplateData(reportTemplatesForm.getReportTemplateId());
				populateReportTemplateForm(reportTemplateData,reportTemplatesForm);
				sheetDetails = ReportDesignUtils.getReportSheetArray(reportTemplateData.getReportFilePath());
			}
			request.setAttribute("sheetDetails", sheetDetails);
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_REPORTS);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getExcelSheetDetails(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		}
		ReportTemplatesForm reportTemplatesForm = (ReportTemplatesForm) actionForm; 
		String filePath = reportTemplatesForm.getReportFilePath();
		String ext = filePath.substring(filePath.length() - 4, filePath.length());
		ActionErrors errors = null;
		if (ext.equals(".xls") || ext.equals("xlsx")) {
			xmlFile = ReportDesignUtils.getReportSheetArray(filePath);
		} else {
			reportTemplatesForm.setReportFilePath(null);
			errors = new ActionErrors();
			errors.add("common.please_upload.one_param", new ActionError("common.please_upload.one_param", TPLabels.getLabel("common.excel_file") + " " + TPLabels.getLabel("common.to") + " "
					+ TPLabels.getLabel("common.import")));
			saveErrors(request, errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward reportTemplateColumnMapping(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "reportTemplateColumnMapping";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ReportTemplatesForm reportTemplatesForm = (ReportTemplatesForm) actionForm;
		ReportTemplateManager reportTemplateManager = null;
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String filePath = reportTemplatesForm.getReportFilePath();
			int sheetAt = Integer.parseInt(reportTemplatesForm.getSheetIndex());
			int rowAt = Integer.parseInt(reportTemplatesForm.getRowIndex());
			ArrayList<String> excelFieldName = ReportUtils.excelFieldName(filePath,sheetAt,rowAt);
			String columns = ReportUtils.getJSArrayForFields(permissionSet,reportTemplatesForm.getReportId(), null);
			if(!Utils.isBlankOrNull(reportTemplatesForm.getReportTemplateId())){
				reportTemplateManager = new ReportTemplateManager();
				ArrayList<ColumnData>  columnDataList = reportTemplateManager.getReportTemplateColumnData(reportTemplatesForm.getReportTemplateId());
				request.setAttribute("columnDataList", columnDataList);
			}
			request.setAttribute("excelFieldName", excelFieldName);
			request.setAttribute("columns", columns);
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_REPORTS);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward saveReportTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "reportTemplateColumnMapping";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		String userId = (String) request.getSession(false).getAttribute("userId"); 
		ReportTemplatesForm reportTemplatesForm = (ReportTemplatesForm) actionForm;
		ReportTemplateManager reportTemplateManager = null; 
		ReportTemplateData reportTemplateData = null;
		try {
			reportTemplateManager = new ReportTemplateManager();
			reportTemplateData = new ReportTemplateData();
			populateReportTemplateData(reportTemplatesForm,reportTemplateData);
			reportTemplateManager.saveReportTemplateData(reportTemplateData, userId);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return mapping.findForward(forward);
		}
		return manageReportTemplates(mapping, actionForm, request, response);
	}
	
	public ActionForward getReportTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				ReportTemplateManager reportTemplateManager = new ReportTemplateManager();
				List<ReportTemplateData> templates = reportTemplateManager.getReportTemplates();
				xmlFile = reportTemplateManager.getXMLForReportTemplates(templates);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward deleteReportTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				ReportTemplatesForm reportTemplatesForm = (ReportTemplatesForm) actionForm;
				String reportTemplateId = reportTemplatesForm.getReportTemplateId();
				ReportTemplateManager reportTemplateManager = new ReportTemplateManager();
				reportTemplateManager.deleteReportTemplate(reportTemplateId);
				xmlFile = Utils.getXMLForIds(reportTemplateId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward uploadTemplateFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "addDocument";
		return mapping.findForward(forward);
	}
	
	public ActionForward saveTemplateFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "uploadSuccess";
		ReportTemplatesForm reportTemplatesForm = (ReportTemplatesForm) actionForm;
		String error = "";
		try {
			// get all attachments for this message
			FormFile formFile = reportTemplatesForm.getAttachedFile();
			if (formFile != null) {
				if (formFile.getFileSize() <= 0) {
					error = TPLabels.getLabel("report_templates.error.could_not_read_file");
				}
				if(!FileHandlerUtils.isExcelDoc(formFile.getFileName())){
					request.setAttribute("option", "1");
					error = TPLabels.getLabel("report_templates.error.upload_excel_file");
				}
				
				FormFileData formFileData = new FormFileData(formFile.getFileName(), formFile.getFileSize(), formFile.getContentType(), formFile.getInputStream());
				if (Utils.isBlankOrNull(error)) {
					ApplicantManager applicantManager = new ApplicantManager();
					AttachmentData attachmentData = applicantManager.uploadResumeTmp(formFileData);
					request.setAttribute("attachmentData", attachmentData);
				}
			}
		} catch (FileUploadException fe) {
			error = TPLabels.getLabel("report_templates.error.could_not_read_file");
		} catch (InvalidMimeTypeException e) {
			TPLogger.getLogger().error("Error while uploading user document", e);
			error = TPLabels.getLabel("upload_document.error.invalid_mime_type");
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("error", error);
		return mapping.findForward(forward);
	}
	
	private void populateReportTemplateData(ReportTemplatesForm reportTemplatesForm,ReportTemplateData reportTemplateData) {
		reportTemplateData.setReportTemplateId(reportTemplatesForm.getReportTemplateId());
		reportTemplateData.setReportId(reportTemplatesForm.getReportId());
		reportTemplateData.setTemplateName(reportTemplatesForm.getTemplateName());
		reportTemplateData.setReportFilePath(reportTemplatesForm.getReportFilePath());
		reportTemplateData.setOriginalFileName(reportTemplatesForm.getOriginalFileName());
		reportTemplateData.setSheetIndex(reportTemplatesForm.getSheetIndex());
		reportTemplateData.setRowIndex(reportTemplatesForm.getRowIndex());
		reportTemplateData.setTemplateColumns(reportTemplatesForm.getTemplateColumns());
	}
	
	private void populateReportTemplateForm(ReportTemplateData reportTemplateData,ReportTemplatesForm reportTemplatesForm) {
		reportTemplatesForm.setReportId(reportTemplateData.getReportId());
		reportTemplatesForm.setTemplateName(reportTemplateData.getTemplateName());
		reportTemplatesForm.setReportFilePath(reportTemplateData.getReportFilePath());
		reportTemplatesForm.setOriginalFileName(reportTemplateData.getOriginalFileName());
		reportTemplatesForm.setSheetIndex(reportTemplateData.getSheetIndex());
		reportTemplatesForm.setRowIndex(reportTemplateData.getRowIndex());
		reportTemplatesForm.setTemplateColumns(reportTemplateData.getTemplateColumns());
	}
}