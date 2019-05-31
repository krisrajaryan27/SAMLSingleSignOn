package com.talentPool.porting.action;

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
import com.talentPool.export.ExportConstants;
import com.talentPool.export.bo.Fields;
import com.talentPool.export.bo.FieldsFactory;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.manager.ReadApplicantsFromCSV;
import com.talentPool.inbox.scheduler.CSVImportSessionProcessor;
import com.talentPool.porting.dataobject.FailedStatusObject;
import com.talentPool.porting.form.ImportForm;
import com.talentPool.porting.manager.ImportManager;
import com.talentPool.porting.manager.ImportManagerFactory;
import com.talentPool.porting.utils.ImportUtils;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

public class ImportAction extends TPDispatchAction{

	public ActionForward importMasters(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "addDocument";
		ImportForm importForm = (ImportForm) actionForm;
		importForm.setEntityType(request.getParameter("importEntityType"));
		return mapping.findForward(forward);
	}
	
	public ActionForward addDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addDocument";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_UPLOAD_DOCUMENT;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward uploadDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "uploadSuccess";
		ImportForm importForm = (ImportForm) actionForm;
		String error = "";
		try {
			// get all attachments for this message
			FormFile formFile = importForm.getAttachedFile();
			if (formFile != null) {
				if (formFile.getFileSize() <= 0) {
					error = TPLabels.getLabel("inbox.error.could_not_read_file");
				}
				if(!".csv".equalsIgnoreCase(FileHandlerUtils.getFileExtention(formFile.getFileName(), ""))) {
					request.setAttribute("option", "1");
					error = TPLabels.getLabel("inbox.error.upload_csv_file");
				}
				FormFileData formFileData = new FormFileData(formFile.getFileName(), formFile.getFileSize(), formFile.getContentType(), formFile.getInputStream());
				if (Utils.isBlankOrNull(error)) {
					ApplicantManager applicantManager = new ApplicantManager();
					AttachmentData attachmentData = applicantManager.uploadResumeTmp(formFileData);
					request.setAttribute("attachmentData", attachmentData);
				}
			}
		} catch (FileUploadException fe) {
			error = TPLabels.getLabel("inbox.error.could_not_read_file");
		} catch (InvalidMimeTypeException e) {
			TPLogger.getLogger().error("Error while uploading user document", e);
			error = TPLabels.getLabel("upload_document.error.invalid_mime_type");
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading resume from HD", e);
		}
		request.setAttribute("error", error);
		return mapping.findForward(forward);
	}
	
	public ActionForward showMasterCSVFieldMappings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showFieldMappings";
		try {
			ImportForm inboxForm = (ImportForm) actionForm;
			String filePath = inboxForm.getFilePath();
			
			String sessionId = "" + System.currentTimeMillis();
			String ext = filePath.substring(filePath.length() - 4, filePath.length());			
			ActionErrors errors = null;
			if (ext.equals(".csv")) {
				ReadApplicantsFromCSV readApplicantsFromCSV = new ReadApplicantsFromCSV();
				ArrayList<String> excelFieldName = readApplicantsFromCSV.csvFieldName(filePath);
				inboxForm.setSessionId(sessionId);
				
				FieldsFactory fieldsFactory = new FieldsFactory();
				Fields fields = fieldsFactory.getFields(inboxForm.getEntityType());
				String masterFields = ImportUtils.getListJavaScriptArrayForMasterFields(fields);
				inboxForm.setJsArrayMasterFields(masterFields);
				request.setAttribute("excelFieldName", excelFieldName);
			} else {
				errors = new ActionErrors();
				errors.add("common.please_select.one_param", new ActionError("common.please_select.one_param", TPLabels.getLabel("common.csv_file") + " " + TPLabels.getLabel("common.to") + " "
						+ TPLabels.getLabel("common.import")));
				saveErrors(request, errors);
				forward = "import";
			}
			if(!ExportConstants.ENTITY_USERS.equals(inboxForm.getEntityType())){
				request.setAttribute("t", NavigationConstants.T_MASTERS);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward importData(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewCSVImportStatus";
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			ImportForm inboxForm = (ImportForm) actionForm;
			String sessionId = inboxForm.getSessionId();
			String mappings = inboxForm.getMappings();
			CSVImportSessionProcessor csvImportSessionProcessor = new CSVImportSessionProcessor(sessionId, inboxForm.getFilePath(), mappings, userId);
			
			ImportManagerFactory factory = new ImportManagerFactory();
			ImportManager manager = factory.getManager(inboxForm.getEntityType());
			List<FailedStatusObject> failedRows = manager.importData(sessionId, mappings, inboxForm.getFilePath(), userId);
			inboxForm.setFailedObjects(failedRows);			
			
			request.setAttribute("excelFieldName", new ArrayList());
			request.setAttribute("failedObjects", failedRows);
			if(!ExportConstants.ENTITY_USERS.equals(inboxForm.getEntityType())){
				request.setAttribute("t", NavigationConstants.T_MASTERS);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}	

}
