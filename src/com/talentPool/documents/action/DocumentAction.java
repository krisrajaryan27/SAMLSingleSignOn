/**
 * 
 */
package com.talentPool.documents.action;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.common.utils.Exception.PathTraversalException;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.form.DocumentForm;
import com.talentPool.documents.manager.DocumentManager;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class DocumentAction extends TPDispatchAction {
	public ActionForward getDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "getDocument";
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			DocumentForm documentForm = (DocumentForm) actionForm;
			String fileName = documentForm.getFileName();
			String contentDisposition = Utils.isBlankOrNull(documentForm.getContentDisposition()) ? DocumentConstants.CONTENT_DISPOSITION_ATTACHMENT : documentForm.getContentDisposition();
			if (!Utils.isBlankOrNull(fileName)) {
				fileName = DocumentUtils.sanitize(DocumentConstants.documentsPath, fileName);
				String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, fileName);
				FileHandler fileHandler = new FileHandler();
				String contentType = fileHandler.getContentType(filePath);
				request.setAttribute("filePath", filePath);
				request.setAttribute("contentType", contentType);

				File f = new File(filePath);
				if (!f.exists()) {
					errors.add("resume_summary.error.file_not_exist", new ActionError("resume_summary.error.file_not_exist"));
				} else {
					request.setAttribute("fileName", f.getName());
				}
				request.setAttribute("contentDisposition", contentDisposition);
			} else {
				errors.add("resume_summary.error.file_not_exist", new ActionError("resume_summary.error.file_not_exist"));
			}
		} catch (PathTraversalException e) {
			TPLogger.getLogger().error("Invalid file path", e);
			errors.add("resume_summary.error.invalid_file_path", new ActionError("resume_summary.error.invalid_file_path"));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while retriving about info", e);
		}
		if (errors.size() > 0) {
			request.setAttribute(Globals.ERROR_KEY, errors);
			forward = "error";
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getIcon(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "getDocument";
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			DocumentForm documentForm = (DocumentForm) actionForm;
			String fileName = documentForm.getFileName();
			String contentDisposition = Utils.isBlankOrNull(documentForm.getContentDisposition()) ? DocumentConstants.CONTENT_DISPOSITION_ATTACHMENT : documentForm.getContentDisposition();
			if (!Utils.isBlankOrNull(fileName)) {
				fileName = DocumentUtils.sanitize(DocumentConstants.documentsPath, fileName);
				String filePath = Utils.concatFilePath(DocumentConstants.iconsPath, fileName);
				FileHandler fileHandler = new FileHandler();
				String contentType = fileHandler.getContentType(filePath);
				request.setAttribute("filePath", filePath);
				request.setAttribute("contentType", contentType);

				File f = new File(filePath);
				if (!f.exists()) {
					errors.add("resume_summary.error.file_not_exist", new ActionError("resume_summary.error.file_not_exist"));
				} else {
					request.setAttribute("fileName", f.getName());
				}
				request.setAttribute("contentDisposition", contentDisposition);
			} else {
				errors.add("resume_summary.error.file_not_exist", new ActionError("resume_summary.error.file_not_exist"));
			}
		} catch (PathTraversalException e) {
			TPLogger.getLogger().error("Invalid file path", e);
			errors.add("resume_summary.error.invalid_file_path", new ActionError("resume_summary.error.invalid_file_path"));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while retriving about info", e);
		}
		if (errors.size() > 0) {
			request.setAttribute(Globals.ERROR_KEY, errors);
			forward = "error";
		}
		return mapping.findForward(forward);
	}

	public ActionForward uploadDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "uploadDocument";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_UPLOAD_DOCUMENT;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			DocumentForm documentForm = (DocumentForm) actionForm;
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(documentForm.getApplicantId());
			request.setAttribute("applicantData", applicantData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading user document", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "uploadSuccess";
		String error = "";
		try {
			DocumentForm documentForm = (DocumentForm) actionForm;
			// get all attachments for this message
			FormFile formFile 	= documentForm.getAttachedFile();
			FileHandler fh 		= new FileHandler();
			if (formFile != null) {
				error = fh.commonFormFileValidator(formFile);
				FormFileData formFileData = new FormFileData(formFile.getFileName(), formFile.getFileSize(), formFile.getContentType(), formFile.getInputStream());
				if (Utils.isBlankOrNull(error)) {
					String userId = (String) request.getSession().getAttribute("userId");
					String applicantId = documentForm.getApplicantId();
					DocumentManager documentManager = new DocumentManager();
					AttachmentData attachmentData = documentManager.uploadDocument(formFileData, applicantId, userId);
					request.setAttribute("attachmentData", attachmentData);
				}
			}
		} catch (FileUploadException fe) {
			error = TPLabels.getLabel("upload_document.error.could_not_read_file");
		} catch (InvalidMimeTypeException e) {
			TPLogger.getLogger().error("Error while uploading user document", e);
			error = TPLabels.getLabel("upload_document.error.invalid_mime_type");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading user document", e);
			error = TPLabels.getLabel("upload_document.error.unknown");
		}
		request.setAttribute("error", error);
		return mapping.findForward(forward);
	}
	public ActionForward savePositionDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "uploadSuccess";
		String error = "";
		try {
			DocumentForm documentForm = (DocumentForm) actionForm;
			// get all attachments for this message
			FormFile formFile = documentForm.getAttachedFile();
			FileHandler fh = new FileHandler();
			if (formFile != null) {
				error = fh.commonFormFileValidator(formFile);
				FormFileData formFileData = new FormFileData(formFile.getFileName(), formFile.getFileSize(), formFile.getContentType(), formFile.getInputStream());
				if (Utils.isBlankOrNull(error)) {
					String userId = (String) request.getSession().getAttribute("userId");
					String positionId = documentForm.getPositionId();
					DocumentManager documentManager = new DocumentManager();
					AttachmentData attachmentData = documentManager.uploadPositionDocument(formFileData, positionId, userId);
					request.setAttribute("attachmentData", attachmentData);
				}
			}
		} catch (InvalidMimeTypeException e) {
			TPLogger.getLogger().error("Error while uploading user document", e);
			error = TPLabels.getLabel("upload_document.error.invalid_mime_type");
		} catch (FileUploadException fe) {
			error = TPLabels.getLabel("upload_document.error.could_not_read_file");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			error = TPLabels.getLabel("upload_document.error.unknown");
		}
		request.setAttribute("error", error);
		return mapping.findForward(forward);
	}

	public ActionForward getApplicantDocuments(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DocumentManager documentManager = new DocumentManager();
				DocumentForm documentForm = (DocumentForm) actionForm;
				String userId = (String) request.getSession().getAttribute("userId");
				String userRoles = (String) request.getSession().getAttribute("userRoles");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				
				ArrayList<DocumentData> docs = documentManager.getApplicantDocuments(documentForm.getApplicantId());
				xmlFile = documentManager.getXMLForApplicantDocuments(docs, userId, userRoles, permissionSet);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error getting interactions in xml", e);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward deleteDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DocumentForm documentForm = (DocumentForm) actionForm;
				DocumentManager documentManager = new DocumentManager();
				documentManager.deleteApplicantDocument(documentForm.getDocumentId());
				// Create deleted xml
				xmlFile = Utils.getXMLForIds(documentForm.getDocumentId());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting message", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward deletePositionDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DocumentForm documentForm = (DocumentForm) actionForm;
				DocumentManager documentManager = new DocumentManager();
				documentManager.deletePositionDocument(documentForm.getDocumentId(),documentForm.getPositionId());
				// Create deleted xml
				xmlFile = Utils.getXMLForIds(documentForm.getDocumentId());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward hideDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DocumentForm documentForm = (DocumentForm) actionForm;
				DocumentManager documentManager = new DocumentManager();
				documentManager.hideShowApplicantDocument(documentForm.getDocumentId());

				// Create deleted xml
				xmlFile = Utils.getXMLForIds(documentForm.getDocumentId());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in hide show", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward hidePositionDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DocumentForm documentForm = (DocumentForm) actionForm;
				DocumentManager documentManager = new DocumentManager();
				documentManager.hideShowPositionDocument(documentForm.getDocumentId());

				// Create deleted xml
				xmlFile = Utils.getXMLForIds(documentForm.getDocumentId());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in hide show", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getPositionDocuments(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DocumentManager documentManager = new DocumentManager();
				DocumentForm documentForm = (DocumentForm) actionForm;
				String userId = (String) request.getSession().getAttribute("userId");
				String userRoles = (String) request.getSession().getAttribute("userRoles");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				
				ArrayList<DocumentData> docs = documentManager.getPositionDocuments(documentForm.getPositionId());
				xmlFile = documentManager.getXMLForPositionDocuments(docs, userId, userRoles, permissionSet);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error getting interactions in xml", e);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}
	
	public ActionForward uploadPositionDocument(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "uploadPositionDocument";
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String positionId = request.getParameter("positionId");
			String positionTitle = request.getParameter("positionTitle");
			request.setAttribute("positionId", positionId);
			request.setAttribute("permissionSet", permissionSet);
			request.setAttribute("positionTitle", positionTitle);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
}
