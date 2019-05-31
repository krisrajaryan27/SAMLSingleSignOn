/**
 * 
 */
package com.talentPool.desktop.action;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.applicant.bc.ApplicantBC;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldDataProcessor;
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.desktop.dataobjects.BulkImportResultData;
import com.talentPool.desktop.dataobjects.BulkImportSessionData;
import com.talentPool.desktop.form.DesktopSearchForm;
import com.talentPool.desktop.manager.BulkImportManager;
import com.talentPool.desktop.manager.DesktopSearchManager;
import com.talentPool.desktop.manager.EmailAttacher;
import com.talentPool.desktop.scheduler.BulkImportSessionProcessor;
import com.talentPool.desktop.utils.BulkImportSessionUtils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class DesktopSearchAction extends TPDispatchAction {
	public ActionForward searchToAttach(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "searchToAttach";
		try {
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getXMLForAttachToSearch(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			DesktopSearchManager desktopSearchManager = new DesktopSearchManager();
			String firstRequest = desktopSearchForm.getFirstRequest();
			ArrayList<ApplicantData> applicants = null;
			if (!Utils.isBlankOrNull(firstRequest)) {
				applicants = desktopSearchManager.getApplicantToAttach("", desktopSearchForm.getFromEmail(), permissionSet);
				if ((applicants == null || applicants.size() == 0) && !Utils.isBlankOrNull(desktopSearchForm.getFromName())) {
					applicants = desktopSearchManager.getApplicantToAttach(desktopSearchForm.getFromName(), "", permissionSet);
				}
			} else {
				applicants = desktopSearchManager.getApplicantToAttach(desktopSearchForm.getFromName(), desktopSearchForm.getFromEmail(), permissionSet);
			}

			xmlFile = desktopSearchManager.getXMLforAttachToSearch(applicants);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward attachEmail(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "attachEmail";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			EmailAttacher emailAttacher = new EmailAttacher();
			ArrayList<ArrayList<String>> result = emailAttacher.addEmailToCandidateHistory(desktopSearchForm.getSessionId(), desktopSearchForm.getApplicantId(), desktopSearchForm.getUserId());
			ArrayList<String> emailsToAttach = result.get(0);
			ArrayList<String> attachedEmails = result.get(1);

			if (attachedEmails.size() > 0) {
				DesktopSearchManager desktopSearchManager = new DesktopSearchManager();
				String applicantName = desktopSearchManager.getApplicantName(desktopSearchForm.getApplicantId());
				StringBuffer sb = new StringBuffer();
				int sz = attachedEmails.size();
				for (int i = 0; i < sz; i++) {
					if (i != (sz - 1)) {
						sb.append(attachedEmails.get(i) + "|" + applicantName + "$");
					} else {
						sb.append(attachedEmails.get(i) + "|" + applicantName);
					}
				}
				desktopSearchForm.setResult(sb.toString());
			}

			request.setAttribute("emailsProcessed", "" + emailsToAttach.size());
			request.setAttribute("emailsAttached", "" + attachedEmails.size());

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward autologin(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "autologin";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String screenType = desktopSearchForm.getScreenType();
			String sessionType = desktopSearchForm.getSessionType();
			String userId = desktopSearchForm.getUserId();
			String emailId = desktopSearchForm.getEmailId();
			String sessionId = desktopSearchForm.getSessionId();
			String url = "";
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(userId);
			BitSet permissions = loginManager.getUserPermissionsBitSet(loginData.getUserId());
			BitSet reportBitSet = loginManager.getUserReportsBitSet(loginData.getUserId());
			PermissionSet permissionSet = new PermissionSet(permissions);

			HttpSession session = request.getSession();
			SessionManager sessionManager = new SessionManager();

			if (!Utils.isBlankOrNull(loginData.getTimeZone())){
				sessionManager.setSessionVariables(session, loginData, permissionSet, reportBitSet, loginData.isValidLdapUser(), loginData.getRoleId(), null, loginData.getTimeZone());
			}else{
				sessionManager.setSessionVariables(session, loginData, permissionSet, reportBitSet, loginData.isValidLdapUser(), loginData.getRoleId(), null);
			}
			if (screenType.equalsIgnoreCase(DesktopConstants.SCREEN_TYPE_SINGLE_IMPORT)) {
				url = "importResume.do?mode=importResume&parse=1&subMode=add&emailId=" + emailId + "&requestSource=" + DesktopConstants.REQUEST_SOURCE_DESKTOP;
				request.setAttribute("url", url);
			} else if (screenType.equalsIgnoreCase(DesktopConstants.SCREEN_TYPE_BULK_IMPORT)) {
				url = "desktop.do?mode=showBulkImportProgress&sessionId=" + sessionId + "&isSessionComplete=&sessionType=" + sessionType + "&emailId=" + emailId;
				request.setAttribute("url", url);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward selectBulkImportParameters(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "selectBulkImportParameters";
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			if (DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(desktopSearchForm.getSessionType()) &&
					!SessionManager.validateSession(mapping, actionForm, request, response, this)) {				
				return null;
			}
			if (DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(desktopSearchForm.getSessionType())) {
				desktopSearchForm.setUserId(userId);
				if(!Utils.isBlankOrNull(desktopSearchForm.getPortal())){
					setJobPortalInSource(desktopSearchForm,desktopSearchForm.getPortal());
				}
			}
			if(Utils.isBlankOrNull(desktopSearchForm.getSourceId())){
				LoginManager loginManager = new LoginManager();
				LoginData data = loginManager.getUser(desktopSearchForm.getUserId());
				desktopSearchForm.setSourceId(data.getUserSourceId());
			}
			ArrayList<CustomFieldData> customFields = null;
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
				CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
				customFields = customFieldDataProcessor.setCustomFieldValuesFromRequest(request, customFields);
				request.setAttribute("customFields", customFields);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	private void setJobPortalInSource(DesktopSearchForm desktopSearchForm, String portal) {
		DesktopSearchManager desktopSearchManager = new DesktopSearchManager();
		try {
			if(portal.contains(DesktopConstants.PORTAL_NAUKRI)){
				portal = DesktopConstants.PORTAL_NAUKRI;
			}else if(portal.contains(DesktopConstants.PORTAL_MONSTER)){
				portal = DesktopConstants.PORTAL_MONSTER;
			}else if(portal.contains(DesktopConstants.PORTAL_TIMESJOB)){
				portal = DesktopConstants.PORTAL_TIMESJOB;
			}
			desktopSearchForm.setSourceId(desktopSearchManager.getSourceId(portal));
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		
	}

	public ActionForward saveBulkImportParameters(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "success";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			BulkImportManager bulkImportManager = new BulkImportManager();
			ApplicantData aData = getApplicantDataConstructed(desktopSearchForm);
			// custom fields validations
			ArrayList<CustomFieldData> customFields = null;
			CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
				customFields = customFieldDataProcessor.setCustomFieldValuesFromRequest(request, customFields);
				request.setAttribute("customFields", customFields);
			}
			aData.setCustomFields(customFields);

			// bulkImportManager.saveBulkImportParameters(desktopSearchForm.getSessionId(),
			// desktopSearchForm.getSourceId(), desktopSearchForm.getCurrentLocation(),
			// desktopSearchForm.getSkillIds(),desktopSearchForm.getResumeTypeId(), workingSince,
			// DesktopConstants.SESSION_STATUS_INPROCESS, desktopSearchForm.getSessionType(),
			// desktopSearchForm.getUserId());
			bulkImportManager.saveBulkImportParameters(aData, desktopSearchForm.getSessionId(), DesktopConstants.SESSION_STATUS_INPROCESS, desktopSearchForm.getSessionType(), desktopSearchForm
					.getUserId(), desktopSearchForm.getSkillIds(), null, desktopSearchForm.getNote());

			String sessionType = desktopSearchForm.getSessionType();

			desktopSearchForm.setResult(DesktopConstants.SUCCESS);

			if (DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT.equalsIgnoreCase(sessionType)) {
				String emailIds = desktopSearchForm.getEmailId();
				if (!Utils.isBlankOrNull(emailIds)) {
					BulkImportSessionData bulkImportSessionData = bulkImportManager.getBulkImportSessionData(desktopSearchForm.getSessionId());
					String[] arrEmailIds = emailIds.split(",");
					InboxManager inboxManager = new InboxManager();
					BulkImportSessionUtils bulkImportSessionUtils = new BulkImportSessionUtils();
					for (int i = 0; i < arrEmailIds.length; i++) {
						MessageData data = inboxManager.getEmailHeader(arrEmailIds[i].trim(), InboxConstants.EMAIL_LOCATION_INBOX, true);
						MessageData emailBody = inboxManager.getEmailBody(arrEmailIds[i].trim(), InboxConstants.EMAIL_LOCATION_INBOX);
						if (data != null) {
							data.setTextBody(emailBody.getTextBody());
							data.setHtmlBody(emailBody.getHtmlBody());
							data.setEntryId(arrEmailIds[i]);
							data.setSessionId(bulkImportSessionData.getSessionId());
							data.setImportStatus(DesktopConstants.IMPORT_STATUS_PARSING_DONE);
							if (data.getAttachments() != null && data.getAttachments().size() > 0) {
								for (int j = 0; j < data.getAttachments().size(); j++) {
									((AttachmentData) data.getAttachments().get(j)).setImportStatus(DesktopConstants.IMPORT_STATUS_PARSING_DONE);
								}
							}
							String emailId = bulkImportManager.saveMessage(data);
							data.setMessageId(emailId);
							bulkImportSessionUtils.processSingleEmail(data, bulkImportSessionData);
						}
					}
				}
				forward = "showBulkImportProgress";
			}  else if(DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equalsIgnoreCase(sessionType)) {
				BulkImportSessionProcessor bulkImportSessionProcessor = new BulkImportSessionProcessor(desktopSearchForm.getSessionId());
				forward = "showBrowserBulkImportProgress";
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	private Date calculateWorkingSince(String exp) {
		Date workingSince = null;
		if (!Utils.isBlankOrNull(exp)) {
			String[] yrsAndMonth = exp.split(",");
			int years = 0;
			int months = 0;
			if (!yrsAndMonth[0].equals("-1")) {
				years = Integer.parseInt(yrsAndMonth[0]);
			}
			if (!yrsAndMonth[1].equals("-1")) {
				months = Integer.parseInt(yrsAndMonth[1]);
			}

			Calendar date = new GregorianCalendar();
			if (years != 0 || months != 0) {
				date.add(Calendar.YEAR, -years);
				date.add(Calendar.MONTH, -months);
				workingSince = date.getTime();
			}
		}
		return workingSince;
	}

	private ApplicantData getApplicantDataConstructed(DesktopSearchForm aForm) {
		ApplicantData aData = new ApplicantData();
		if (!Utils.isBlankOrNull(aForm.getSourceId())) {
			aData.setApplicantSourceId(Integer.parseInt(aForm.getSourceId()));
		}
		aData.setApplicantCity(aForm.getCurrentLocation());
		aData.setApplicantCurrentEmployer(aForm.getCurrentEmployer());
		aData.setCurrentCTC(aForm.getCurrentCTC());
		aData.setExpectedCTC(aForm.getExpectedCTC());
		aData.setNoticePeriod(aForm.getNoticePeriod());
		Date workingSince = calculateWorkingSince(aForm.getExperience());
		aData.setApplicantWorkingSince(Utils.convertDateToSQLDate(workingSince));

		String[] eduYop = aForm.getEducationYearOfPassing();
		String[] eduInstitute = aForm.getEducationInstitute();
		String[] eduDegree = aForm.getEducationDegreeId();
		String[] eduMajor = aForm.getEducationMajorId();
		String[] eduGrades = aForm.getEducationalGrade();
		ArrayList<EducationalData> educationalDetails = new ArrayList<EducationalData>();

		for (int i = 0; eduYop != null && i < eduYop.length; i++) {
			if (!"null".equals(eduYop[i])) {
				if (!(Utils.isBlankOrNull(eduYop[i]) && Utils.isBlankOrNull(eduInstitute[i]) && eduDegree[i].equals("-1") && eduMajor[i].equals("-1") && Utils.isBlankOrNull(eduGrades[i]))) {
					EducationalData eData = new EducationalData();
					Date tmpDt = null;
					if (!Utils.isBlankOrNull(eduYop[i].trim())) {
						tmpDt = Utils.convertToDate("1" + Utils.dateDescSeparator + "1" + Utils.dateDescSeparator + eduYop[i], Utils.redDDMMYYYYFormat);
						if (tmpDt != null) {
							eData.setYearOfPassing(new java.sql.Date(tmpDt.getTime()));
						}
					} else {
						eData.setYearOfPassing(null);
					}
					eData.setInstitute(eduInstitute[i]);
					eData.setDegreeId(Integer.parseInt(eduDegree[i]));
					eData.setMajorId(Integer.parseInt(eduMajor[i]));
					eData.setGrade(eduGrades[i]);
					educationalDetails.add(eData);
				}
			}
		}
		aData.setEducationalDetails(educationalDetails);
		return aData;

	}

	public ActionForward showBulkImportProgress(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showBulkImportProgress";
		try {
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward viewBulkImportProgress(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
				String sessionId = desktopSearchForm.getSessionId();
				String lastRowId = desktopSearchForm.getLastRowId();
				String sessionType = desktopSearchForm.getSessionType();
				BulkImportManager bulkImportManager = new BulkImportManager();
				ArrayList<BulkImportResultData> data = bulkImportManager.getBulkImportProcessData(sessionType, sessionId, lastRowId);
				xmlFile = bulkImportManager.getXMLforBulkImport(sessionType, data);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward fetchParsedInfo(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			BulkImportManager bulkImportManager = new BulkImportManager();
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String resultId = desktopSearchForm.getResultId();
			BulkImportResultData bulkImportResultData = bulkImportManager.getParsedData(resultId);
			xmlFile = bulkImportManager.getXMLFromParsedData(bulkImportResultData);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		// xmlFile = Utils.getXMLForContent("content", JsArray);
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward deleteParsedResult(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String resultId = desktopSearchForm.getResultId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			bulkImportManager.deleteParsedResult(resultId);
			xmlFile = Utils.getXMLForIds(resultId);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward changeParsedResultData(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			BulkImportResultData resultdata = new BulkImportResultData();
			resultdata.setResultId(desktopSearchForm.getResultId());
			resultdata.setParsedName(desktopSearchForm.getParsedName());
			resultdata.setParsedCellPhone(desktopSearchForm.getParsedPhone1());
			resultdata.setParsedWorkPhone(desktopSearchForm.getParsedPhone2());
			resultdata.setParsedEmail1(desktopSearchForm.getParsedEmail());
			BulkImportManager bulkImportManager = new BulkImportManager();
			bulkImportManager.changeParsedResultData(resultdata);
			xmlFile = bulkImportManager.getXMLFromParsedData(resultdata);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward importAll(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String sessionId = desktopSearchForm.getSessionId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			BulkImportSessionData bulkImportSessionData = bulkImportManager.getBulkImportSessionData(sessionId);

			bulkImportManager.importBulkParsedResumes(bulkImportSessionData);
			int notImported = bulkImportManager.getCountForImportStatus(sessionId, DesktopConstants.IS_NOT_IMPORTED);
			if (notImported > 0) {
				desktopSearchForm.setDuplicateImport("1");
			} else {
				return finishBulkImport(mapping, actionForm, request, response);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return showBulkImportProgress(mapping, actionForm, request, response);
	}

	public ActionForward checkForSessionComplete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String sessionId = desktopSearchForm.getSessionId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			String isSessionComplete = bulkImportManager.checkForSessionComplete(sessionId);
			xmlFile = Utils.getXMLForIds(isSessionComplete);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward finishBulkImport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "finishBulkImport";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String sessionId = desktopSearchForm.getSessionId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			BulkImportSessionData bulkImportSessionData = bulkImportManager.getBulkImportSessionData(sessionId);
			int imported = bulkImportManager.getCountForImportStatus(sessionId, DesktopConstants.IS_IMPORTED);
			request.setAttribute("imported", "" + imported);
			String applicantIds = bulkImportManager.getApplicantIdsForImportedResume(sessionId);
			desktopSearchForm.setApplicantId(applicantIds);
			if (bulkImportSessionData.getSessionType().equalsIgnoreCase(DesktopConstants.SESSION_TYPE_EMAIL_IMPORT)
					|| bulkImportSessionData.getSessionType().equalsIgnoreCase(DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT)
					|| bulkImportSessionData.getSessionType().equalsIgnoreCase(DesktopConstants.SESSION_TYPE_BROWSER_IMPORT)) {
				// get email entryIds in comma separated list
				ArrayList<SimpleDataObject> results = bulkImportManager.getEmailEntryIdsForImportedResults(sessionId, DesktopConstants.IS_IMPORTED, bulkImportSessionData.getSessionType());
				String result = getResultString(results);
				desktopSearchForm.setResult(result);
			}
			if (Utils.isBlankOrNull(desktopSearchForm.getResult()) && !bulkImportSessionData.getSessionType().equalsIgnoreCase(DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT)) {
				desktopSearchForm.setResult(DesktopConstants.SUCCESS);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	private String getResultString(ArrayList<SimpleDataObject> results) {
		StringBuffer sb = new StringBuffer();
		if (results != null) {
			int sz = results.size();
			for (int i = 0; i < sz; i++) {
				SimpleDataObject sdo = results.get(i);
				String entryId = sdo.getString("entryId");
				String applicants = sdo.getString("applicants");
				sb.append(entryId + "|" + applicants);
				if (i < sz - 1) {
					sb.append("$");
				}
			}
		}
		return sb.toString();
	}

	public ActionForward duplicateImport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "showBulkImportProgress";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String resultId = desktopSearchForm.getResultId();

			BulkImportManager bulkImportManager = new BulkImportManager();
			BulkImportResultData bulkImportResultData = bulkImportManager.getParsedData(resultId);
			ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
			ArrayList<ApplicantDuplicateSearchData> duplicates = applicantDuplicateChecker.getInternalDuplicateChecked("", bulkImportResultData.getParsedName(),
					bulkImportResultData.getParsedEmail1(), bulkImportResultData.getParsedEmail2(), bulkImportResultData.getParsedCellPhone(), null);
			ApplicantBC applicantBC = new ApplicantBC();
			if (!Utils.isBlankOrNull(desktopSearchForm.getDuplicateApplicantId()) && !Utils.isBlankOrNull(desktopSearchForm.getSessionId())) {
				duplicates = applicantBC.getDuplicateDataFromString(desktopSearchForm.getDuplicateString(), desktopSearchForm.getDuplicateApplicantId());
			}
			if (duplicates != null && duplicates.size() > 0) {
				request.setAttribute("duplicates", duplicates);

				desktopSearchForm.setDuplicateString(applicantBC.getDuplicateStringFromData(duplicates));

				forward = "showDuplicates";
			} else {
				request.setAttribute("duplicates", duplicates);
				desktopSearchForm.setDuplicateString("");
				forward = "showDuplicates";
			}
			desktopSearchForm.setParsedResumePath(bulkImportResultData.getParsedOriginalResumePath());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward compareResume(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "compareResume";
		try {
			// do nothing
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward updateConfirm(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "updateConfirm";
		try {
			// do nothing
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward updateApplicantWithNewResume(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "importSuccess";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String sessionId = desktopSearchForm.getSessionId();
			String resultId = desktopSearchForm.getResultId();
			String applicantId = desktopSearchForm.getDuplicateApplicantId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			BulkImportResultData brData = bulkImportManager.getParsedData(resultId);
			BulkImportSessionData bulkImportSessionData = bulkImportManager.getBulkImportSessionData(sessionId);
			bulkImportManager.updateApplicantWithNewResume(applicantId, brData.getParsedOriginalResumePath(), brData.getParsedOriginalDocPath(), brData.getParsedTextResume());
			// applicantManager.addOriginalResumeToDocument(applicantId,
			// brData.getParsedOriginalDocPath(), bulkImportSessionData.getUserId());
			bulkImportManager.updateImportStatusForResult(resultId, applicantId, DesktopConstants.IS_IMPORTED);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward importSingleApplicant(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "importSuccess";
		try {
			ApplicantManager applicantManager = new ApplicantManager();
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String sessionId = desktopSearchForm.getSessionId();
			String resultId = desktopSearchForm.getResultId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			BulkImportSessionData bulkImportSessionData = bulkImportManager.getBulkImportSessionData(sessionId);
			BulkImportResultData bulkImportResultData = bulkImportManager.getParsedData(resultId);
			bulkImportResultData = overwriteCommonSelectParameter(bulkImportResultData, bulkImportSessionData);
			bulkImportManager.importSingleApplicant(bulkImportResultData, bulkImportSessionData.getUserId(), applicantManager, bulkImportSessionData.getSessionType());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	private BulkImportResultData overwriteCommonSelectParameter(BulkImportResultData bulkImportResultData, BulkImportSessionData bulkImportSessionData) {
		bulkImportResultData.setSourceId(bulkImportSessionData.getSourceId());
		bulkImportResultData.setApplicantWorkingSince(bulkImportSessionData.getApplicantWorkingSince());
		
		if(!Utils.isBlankOrNull(bulkImportSessionData.getApplicantCity())){
			bulkImportResultData.setApplicantCity(bulkImportSessionData.getApplicantCity());
		}
		
		if(!Utils.isBlankOrNull(bulkImportSessionData.getCurrentEmployer())){
			bulkImportResultData.setCurrentEmployer(bulkImportSessionData.getCurrentEmployer());
		}
		
		if(!Utils.isBlankOrNull(bulkImportSessionData.getCurrentCtc())){
			bulkImportResultData.setCurrentCtc(bulkImportSessionData.getCurrentCtc());
		}
		
		if(!Utils.isBlankOrNull(bulkImportSessionData.getExpectedCtc())){
			bulkImportResultData.setExpectedCtc(bulkImportSessionData.getExpectedCtc());
		}
		
		if(!Utils.isBlankOrNull(bulkImportSessionData.getTimeToJoin())){
			bulkImportResultData.setTimeToJoin(bulkImportSessionData.getTimeToJoin());
		}
		
		if(!Utils.isBlankOrNull(bulkImportSessionData.getNote())){
			bulkImportResultData.setNote(bulkImportSessionData.getNote());
		}
		
		return bulkImportResultData;
	}

	public ActionForward XMLSkills(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		try {
			DesktopSearchManager desktopSearchManager = new DesktopSearchManager();
			PositionManager positionManager = new PositionManager();
			List skills = positionManager.getSkillsMasterList();
			String xmlFile = desktopSearchManager.getXMLSkills(skills);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward doNotUpdateResume(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "importSuccess";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String resultId = desktopSearchForm.getResultId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			bulkImportManager.deleteParsedResult(resultId);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward addSkills(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addSkills";
		try {

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward updateParsedSkills(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addSkills";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String resultId = desktopSearchForm.getResultId();
			String skillIds = desktopSearchForm.getParsedSkillIds();
			BulkImportManager bulkImportManager = new BulkImportManager();
			bulkImportManager.updateParsedSkills(resultId, skillIds);
			request.setAttribute("update", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getNumberOfParsedResume(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String sessionId = desktopSearchForm.getSessionId();
			String sessionType = desktopSearchForm.getSessionType();
			BulkImportManager bulkImportManager = new BulkImportManager();
			String numberOfParsedResume = bulkImportManager.getNumberOfParsedResume(sessionId, sessionType);
			xmlFile = Utils.getXMLForIds(numberOfParsedResume);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward detailedImport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "autologin";
		try {
			DesktopSearchForm desktopSearchForm = (DesktopSearchForm) actionForm;
			String resultId = desktopSearchForm.getResultId();
			BulkImportManager bulkImportManager = new BulkImportManager();
			BulkImportResultData data = bulkImportManager.getSingleImportData(resultId);
			String emailId = data.getEmailId();
			String url = "";
			if (!Utils.isBlankOrNull(emailId)) {
				String inboxEmailId = bulkImportManager.addEmailToInboxEmails(emailId);
				String attachmentId = "0";			
				if(!DesktopConstants.SESSION_TYPE_BROWSER_IMPORT.equals(desktopSearchForm.getSessionType())) { 
					attachmentId = bulkImportManager.getAttachmentId(inboxEmailId,data.getParsedOriginalDocPath());
				}
				url = "importResume.do?mode=importResume&parse=1&subMode=add&emailId=" + inboxEmailId + "&resultId=" + resultId + "&selAttachment=" + attachmentId + "&requestSource="
						+ DesktopConstants.REQUEST_SOURCE_DESKTOP;
			} else if (desktopSearchForm.getSessionType().equals(DesktopConstants.SESSION_TYPE_DESKTOP_IMPORT)) {
				url = "importResume.do?mode=importResume&parse=1&subMode=add&emailId=&resultId=" + resultId + "&selAttachment=&requestSource=" + DesktopConstants.REQUEST_SOURCE_DESKTOP + "&uploadedFilePath=" + desktopSearchForm.getParsedResumePath();
			}
			request.setAttribute("url", url);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
}
