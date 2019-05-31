/**
 * 
 */
package com.talentPool.offerSheet.acion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.offerSheet.form.OfferSheetForm;
import com.talentPool.offerSheet.manager.OfferSheetManager;
import com.talentPool.offerSheet.utils.OfferSheetUtils;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

/**
 * @author pallavi
 *
 */
public class OfferSheetAction extends TPDispatchAction {
	public ActionForward manageOfferSheetTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "manageOfferSheetTemplates";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}		
		if(!authorize(request)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_SALARY_SHEET_DESIGNER);		
		return mapping.findForward(forward);
	}
	
	public ActionForward getOfferSheetTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else if(!authorize(request)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			} else {
				OfferSheetManager manager = new OfferSheetManager();
				xmlFile = manager.getXmlForOfferSheetTemplates();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward addOfferSheetTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addOfferSheetTemplate";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}		
		if(!authorize(request)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ActionErrors actionErrors = new ActionErrors();
		try {
			if(!Utils.isBlankOrNull(request.getParameter("isSubmitted"))){
				String userId = (String) request.getSession().getAttribute("userId");			
				OfferSheetForm form = (OfferSheetForm) actionForm;
				actionErrors = validateOfferSheetForm(form);
				if (actionErrors.size() == 0) {
					FormFile ff = form.getTemplateDocument();
					FormFileData formFileData = new FormFileData(ff.getFileName(), ff.getFileSize(), ff.getContentType(), ff.getInputStream());
					OfferSheetManager manager = new OfferSheetManager();			
					manager.addNewOfferSheetTemplate(form.getTemplateName(), form.getTemplateDesc(), formFileData, userId);
					request.setAttribute("update", "1");
				}							
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
			actionErrors.add("offersheet_templates.error.failed_create_offer_template", new ActionError("offersheet_templates.error.failed_create_offer_template"));			
		}
		if(actionErrors.size() > 0) {
			saveErrors(request, actionErrors);
		}
		return mapping.findForward(forward);
	}
	
	private ActionErrors validateOfferSheetForm(OfferSheetForm form) {
		ActionErrors actionErrors = new ActionErrors();
		if(Utils.isBlankOrNull(form.getTemplateName())) {
			actionErrors.add("offersheet_templates.error.offersheet_template_name_required", new ActionError("offersheet_templates.error.offersheet_template_name_required"));
		} else {
			OfferSheetManager manager = new OfferSheetManager();
			boolean isOfferSheetTemplateNamePresent = manager.isOfferSheetTemplateNamePresent(form.getTemplateName());
			if(isOfferSheetTemplateNamePresent) {
				actionErrors.add("offersheet_templates.error.offersheet_template_name_already_exists", new ActionError("offersheet_templates.error.offersheet_template_name_already_exists"));
			}
		}
		if(form.getTemplateDocument() == null) {
			actionErrors.add("offersheet_templates.error.offersheet_template_document_required", new ActionError("offersheet_templates.error.offersheet_template_document_required"));
		} else {
			OfferSheetUtils offerSheetUtils = new OfferSheetUtils();
			String ext = FileHandlerUtils.getFileExtention(form.getTemplateDocument().getFileName(), "");
			if(!offerSheetUtils.isValidExtension(ext)) {				
				actionErrors.add("offersheet_templates.error.ext_not_allowed", new ActionError("offersheet_templates.error.ext_not_allowed", TPApplicationProperties.getProperty("offer.sheet.ext.allowed")));
			}
		}
		return actionErrors;
	}

	public ActionForward deleteOfferSheetTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else if(!authorize(request)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			} else {
				OfferSheetForm form = (OfferSheetForm) actionForm;
				String templateId = form.getTemplateId();
				OfferSheetManager manager = new OfferSheetManager();
				manager.deleteOfferSheetTemplate(templateId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	private boolean authorize(HttpServletRequest request) {
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_OFFER_SHEET_MASTER;
		return isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null);
	}
}
