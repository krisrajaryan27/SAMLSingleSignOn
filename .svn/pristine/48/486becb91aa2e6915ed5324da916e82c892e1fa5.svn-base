/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.dataobject.UserData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author Ajeet
 * 
 */
public class VendorNotificationManager {

	public void sendNotification(String applicantId, ApplicantData aData) {
		try {
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(aData.getUserId());
			if (loginData.getRoleId().equals("" + UserConstants.ROLE_VENDOR)) {
				TPLogger.getLogger().debug("SEND NOTIFICATION TO HR MANAGER AND RECRUITERS");

				// Construct token data
				InboxManager inboxManager = new InboxManager();
				InboxData inboxData = inboxManager.getCurrentInboxSettings();
				String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);

				String userStr = TemplateUtils.getConvertedContactData(loginData);
				contentStr = TemplateUtils.appndToToken(contentStr, userStr);

				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantData(applicantId);
				String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
				String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
				contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);

				PositionManager positionManager = new PositionManager();
				PositionData pData = positionManager.getPositionSummary("" + applicantData.getApplicantPositionId());
				String positionTitle = TemplateUtils.getConstructedToken("POSITION_NAME", pData.getPositionTitle());
				contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);

				String positionStepId = applicantData.getApplicantStepId();
				if (!Utils.isBlankOrNull(positionStepId)) {
					String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME", positionManager.getPositionStepName(Integer.parseInt(positionStepId)));
					contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
				}

				// get template data
				TemplateManager templateManager = new TemplateManager();
				TemplateData templateData = templateManager.getTemplateData("vendorResumeUploadNotification");

				String contentVM = templateData.getTemplateContentFile();
				String subjectVM = templateData.getTemplateSubjectFile();
				String keyMap = templateData.getTemplateVariables();
				HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);

				VelocityManager velocityManager = new VelocityManager();
				String subject = velocityManager.handle(subjectVM, keyValMap);
				String content = velocityManager.handle(contentVM, keyValMap);

				String[] roles = new String[2];
				roles[0] = "" + UserConstants.ROLE_HR_MANAGER;
				roles[1] = "" + UserConstants.ROLE_RECRUITER;

				ArrayList<UserData> users = positionManager.getUsersForPosition(applicantData.getApplicantPositionId(), roles);
				String toAddress = "";
				for (int i = 0; users != null && i < users.size(); i++) {
					if (Utils.isBlankOrNull(toAddress)) {
						toAddress = users.get(i).getUserName() + " <" + users.get(i).getUserEmail() + ">;";
					} else {
						toAddress += users.get(i).getUserName() + " <" + users.get(i).getUserEmail() + ">;";
					}
				}
				MessageData messageData = new MessageData();
				messageData.setFrom(loginData.getName() + " <" + inboxData.getInboxEmail() + ">");
				messageData.setReplyTo(loginData.getName() + " <" + loginData.getEmail() + ">");
				messageData.setTo(toAddress);
				messageData.setHtmlBody(content);
				messageData.setSubject(subject);

				TPMailSender mailer = new TPMailSender();
				mailer.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);

				TPLogger.getLogger().debug("End vendor resume upload job");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void sendNotificationToVendor(String applicantId, ApplicantData aData) {
		try {
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(aData.getUserId());
			if (loginData.getRoleId().equals("" + UserConstants.ROLE_VENDOR)) {
				TPLogger.getLogger().debug("SEND NOTIFICATION TO VENDOR");

				// Construct token data
				InboxManager inboxManager = new InboxManager();
				InboxData inboxData = inboxManager.getCurrentInboxSettings();
				String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);

				String userStr = TemplateUtils.getConvertedContactData(loginData);
				contentStr = TemplateUtils.appndToToken(contentStr, userStr);

				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantData(applicantId);
				String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
				String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
				contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);

				PositionManager positionManager = new PositionManager();
				PositionData pData = positionManager.getPositionSummary("" + applicantData.getApplicantPositionId());
				String positionTitle = TemplateUtils.getConstructedToken("POSITION_NAME", pData.getPositionTitle());
				contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);

				String positionStepId = applicantData.getApplicantStepId();
				if (!Utils.isBlankOrNull(positionStepId)) {
					String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME", positionManager.getPositionStepName(Integer.parseInt(positionStepId)));
					contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
				}

				// get template data
				TemplateManager templateManager = new TemplateManager();
				TemplateData templateData = templateManager.getTemplateData("resumeUploadNotificationToVendor");

				String contentVM = templateData.getTemplateContentFile();
				String subjectVM = templateData.getTemplateSubjectFile();
				String keyMap = templateData.getTemplateVariables();
				HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);

				VelocityManager velocityManager = new VelocityManager();
				String subject = velocityManager.handle(subjectVM, keyValMap);
				String content = velocityManager.handle(contentVM, keyValMap);
				
				MessageData messageData = new MessageData();
				messageData.setFrom(loginData.getName() + " <" + inboxData.getInboxEmail() + ">");
				messageData.setReplyTo(loginData.getName() + " <" + loginData.getEmail() + ">");
				messageData.setTo(loginData.getEmail());
				messageData.setHtmlBody(content);
				messageData.setSubject(subject);

				TPMailSender mailer = new TPMailSender();
				mailer.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
}
