package com.talentPool.bulktools.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.inbox.utils.InboxUtils;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.parser.converter.WordToHtmlConverter;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 *
 */
public class BulkToolAction extends TPDispatchAction {
	/**
	 * This action is not called from any code, it is used to convert all attachments to html format
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward convertInboxAttachmentsToHtml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			// get All Applicants
			InboxManager inboxManager = new InboxManager();
			ArrayList messages = inboxManager.getAllEmails(InboxConstants.INBOX_FOLDER_INBOX, InboxConstants.SORT_BY_DATE, CommonConstants.SORT_DIR_DESC, false, permissionSet, userId);

			if (messages != null) {
				for (int i = 0; i < messages.size(); i++) {
					try {
						MessageData mData = (MessageData) messages.get(i);
						mData = inboxManager.getEmailHeader(mData.getMessageId(), InboxConstants.EMAIL_LOCATION_INBOX, true);
						InboxUtils inboxUtils = new InboxUtils();
						inboxUtils.convertDocAttachments(mData, DocumentConstants.documentsPath);
					} catch (Exception ex) {
						TPLogger.getLogger().debug("Error while updating text resume for applicant ", ex);
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating text resumes for applicants", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	/**
	 * This action is not called from any code, this is used externally using
	 * url to update all the text resumes
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateTextResume(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			ApplicantManager applicantManager = new ApplicantManager();
			// get All Applicants
			ArrayList applicants = applicantManager.getAllApplicantsToUpdateTextResume();

			if (applicants != null) {
				for (int i = 0; i < applicants.size(); i++) {
					try {
						ApplicantData aData = (ApplicantData) applicants.get(i);

						String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, aData.getApplicantOriginalResumePath());
						GenericConverter conv = new GenericConverter();
						String fileContent = conv.convert(filePath);
						applicantManager.updateOriginalResume("" + aData.getApplicantId(), aData.getApplicantPositionId(),aData.getApplicantOriginalResumePath(), aData.getApplicantOriginalDocPath(), fileContent);
					} catch (Exception ex) {
						TPLogger.getLogger().debug("Error while updating text resume for applicant ", ex);
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating text resumes for applicants", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward convertAllWordResumes(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			ApplicantManager applicantManager = new ApplicantManager();
			// get All Applicants
			ArrayList applicants = applicantManager.getAllApplicantsToUpdateTextResume();

			if (applicants != null) {
				for (int i = 0; i < applicants.size(); i++) {
					try {
						ApplicantData aData = (ApplicantData) applicants.get(i);
						String originalDocPath = aData.getApplicantOriginalDocPath();

						WordToHtmlConverter wordConverter = new WordToHtmlConverter();
						String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, originalDocPath);
						TPLogger.getLogger().debug("Converting == " + filePath);

						String absoluteHtmlPath = wordConverter.convertToHtml(filePath);
						TPLogger.getLogger().debug("Html after Converting == " + absoluteHtmlPath);
						if (!absoluteHtmlPath.equalsIgnoreCase(filePath)) {
							String relativeResumePath = absoluteHtmlPath.substring(DocumentConstants.documentsPath.length() + 1, absoluteHtmlPath.length());
							TPLogger.getLogger().debug("relative resume path == " + relativeResumePath);
							aData.setApplicantOriginalResumePath(relativeResumePath);

						}
						GenericConverter conv = new GenericConverter();
						String fileContent = conv.convert(absoluteHtmlPath);
						applicantManager.updateOriginalResume("" + aData.getApplicantId(), aData.getApplicantPositionId(),aData.getApplicantOriginalResumePath(), aData.getApplicantOriginalDocPath(), fileContent);
					} catch (Exception ex) {
						TPLogger.getLogger().debug("Error while updating text resume for applicant ", ex);
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating text resumes for applicants", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

}
