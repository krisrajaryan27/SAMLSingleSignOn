package com.talentPool.inbox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.bc.InboxBC;
import com.talentPool.inbox.form.InboxForm;
import com.talentPool.inbox.manager.MassEmailManager;
import com.talentPool.inbox.scheduler.EmployeeNotificationJob;
import com.talentPool.inbox.scheduler.VendorNotificationJob;
import com.talentPool.notifier.OutBoundConstants;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.OutBoundManager;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.search.dataobjects.SearchResultData;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.SessionManager;

public class MassEmailAction extends TPDispatchAction {

	public ActionForward showScreened(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showScreened";
		try {
			String excludeEmailed = request.getParameter("excludeEmailed");
			if (Utils.isBlankOrNull(excludeEmailed)) {
				excludeEmailed = "0";
			}
			request.setAttribute("excludeEmailed", excludeEmailed);

			SearchResultData searchResultData = (SearchResultData) request.getAttribute("searchResultData");
			MassEmailManager massEmailManager = new MassEmailManager();
			massEmailManager.setEmailSentStatus(searchResultData.getRecords());
			if (excludeEmailed.equals("1")) {
				massEmailManager.excludeEmailSent(searchResultData.getRecords());
				searchResultData.setRecordCount(searchResultData.getRecords().size());
				searchResultData.getPager().setRecordCount(searchResultData.getRecordCount());
			}
			InboxBC inboxBC = new InboxBC();
			inboxBC.getTemplates(request);
			
			InboxForm inboxForm = (InboxForm)actionForm;
			LoginManager loginManager = new LoginManager();
			String userId = (String) request.getSession().getAttribute("userId");
			LoginData userData = loginManager.getUser(userId);
			inboxForm.setFrom(userData.getName() + " <" + userData.getEmail() + ">");

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting screened candidates", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward showSelect(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showSelect";
		try {
			InboxBC inboxBC = new InboxBC();
			inboxBC.getTemplates(request);
			InboxForm inboxForm = (InboxForm)actionForm;
			LoginManager loginManager = new LoginManager();
			String userId = (String) request.getSession().getAttribute("userId");
			LoginData userData = loginManager.getUser(userId);
			inboxForm.setFrom(userData.getName() + " <" + userData.getEmail() + ">");
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting screened candidates", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getTemplateXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				InboxForm inboxForm = (InboxForm) actionForm;

				if (!Utils.isBlankOrNull(inboxForm.getTemplateCode())) {
					TemplateManager templateManager = new TemplateManager();
					TemplateData templateData = templateManager.getTemplateData(inboxForm.getTemplateCode());
					xmlFile = templateManager.getTemplateDataInXML(templateData);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting templateData in XML", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward massEmailNotify(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "massEmailNotify";
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			InboxForm inboxForm = (InboxForm) actionForm;
			String templateCode = inboxForm.getTemplateCode();
			String templateSubject = inboxForm.getSubject();
			String templateBody = inboxForm.getNewEmailBody();
			String applicantIds = inboxForm.getSelectedIds();

			TemplateData templateData = new TemplateData();
			templateData.setTemplateCode(templateCode);
			templateData.setTemplateSubjectText(templateSubject);
			templateData.setTemplateContentText(templateBody);
			templateData.setTemplateAuto(TemplateConstants.AUTO_CREATED);
			templateData.setTemplatePrivate(TemplateConstants.TEMPLATE_GLOBAL);
			templateData.setUserId(userId);
			templateData.setTemplateFormat(TemplateConstants.FORMAT_HTML);
			templateData.setTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_EMAIL_MASS_EMAIL_APPLICANT);
			if (!Utils.isBlankOrNull(applicantIds)) {
				TemplateManager templateManager = new TemplateManager();
				String newTemplateCode = templateManager.createTemplate(templateData);
				OutBoundManager outBoundManager = new OutBoundManager();
				outBoundManager.insertNotification(applicantIds, newTemplateCode, userId, OutBoundConstants.ENTITY_TYPE_APPLICANTS, OutBoundConstants.MODE_EMAIL, inboxForm.getFrom());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting screened candidates", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward sendVendorNotificationEmails(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "massEmailNotify";
		try {
			InboxForm inboxForm = (InboxForm) actionForm;
		
			VendorNotificationJob job = new VendorNotificationJob(inboxForm.getSelectedIds(), inboxForm.getTemplateCode());
			job.start();			

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting screened candidates", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward vendorNotificationEmail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showPositionVendorNotify";
		InboxForm inboxForm = (InboxForm) actionForm;
		try {			
			LoginManager loginManager = new LoginManager();
			String userId = (String) request.getSession().getAttribute("userId");
			LoginData userData = loginManager.getUser(userId);
			inboxForm.setFrom(userData.getName() + " <" + userData.getEmail() + ">");			
			
			InboxBC inboxBC = new InboxBC();
			inboxBC.getTemplates(request);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward sendEmployeesNotificationEmails(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "massEmailNotify";
		try {
			InboxForm inboxForm = (InboxForm) actionForm;
		
			EmployeeNotificationJob job = new EmployeeNotificationJob(inboxForm.getSelectedIds(), inboxForm.getTemplateCode());
			job.start();			

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting screened candidates", e);
		}
		return mapping.findForward(forward);
	}
	
}
