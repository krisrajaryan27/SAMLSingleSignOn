/**
 * 
 */
package com.talentPool.requisition.scheduler;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.requisition.dataobject.RequisitionFeedbackData;
import com.talentPool.requisition.manager.RequisitionFeedbackManager;
import com.talentPool.selectionProcess.utils.SelectionProcessUtils;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author shivprasad
 * 
 */
public class RequisitionApprovalFeedbackJob implements Job {
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("IN REQUISITION FEEDBACK SCHEDULER JOB");
		try {
			String feedbackId = context.getTrigger().getJobDataMap().getString("feedbackId");
			RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
			RequisitionFeedbackData requisitionFeedbackData = requisitionFeedbackManager.getFeedbackDataForDisplay(feedbackId);
			if (requisitionFeedbackData != null && !Utils.isBlankOrNull(requisitionFeedbackData.getToUserId())) {
				constructAndSendEmail(requisitionFeedbackData);
			}
		} catch (Exception e) {
			log.error("Error in requisition feedback scheduler job", e);
		}
		log.debug("EXIT REQUISITION FEEDBACK SCHEDULER JOB");
	}

	private void constructAndSendEmail(RequisitionFeedbackData requisitionFeedbackData) throws Exception{
		InboxManager inboxManager = new InboxManager();
		TemplateManager templateManager = new TemplateManager();
		
		// get template data
		TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION);
		
		InboxData inboxData = inboxManager.getCurrentInboxSettings();
		String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);

		String fbStr = TemplateUtils.getConvertedRequisitionFeedbackData(requisitionFeedbackData);
		contentStr = TemplateUtils.appndToToken(contentStr, fbStr);
		
		//start of To send Position approval status details to Requested By
		
		if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL).equals(GlobalConstants.ENABLED)){
			try{
				SelectionProcessUtils selectionProcessUtils=new SelectionProcessUtils();
				PositionManager positionManager =new PositionManager();
				PositionData positionData = positionManager.getPositionSummary(requisitionFeedbackData.getPositionId());
				//keeping Requested By in CC for all position related approval notification
				LoginManager loginManager = new LoginManager();
				LoginData requestorData = loginManager.getUser(positionData.getRequestedById());
				String hrManagerEmail=requestorData.getEmail();
				TemplateManager newTemplateManager = new TemplateManager();
				TemplateData newTemplateData = newTemplateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_TRACK_REQUISITION_APPROVAL);
				String contentVM = newTemplateData.getTemplateContentFile();
				String subjectVM = newTemplateData.getTemplateSubjectFile();
				String keyMap = newTemplateData.getTemplateVariables();
				HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
				VelocityManager velocityManager = new VelocityManager();
				String subject = velocityManager.handle(subjectVM, keyValMap);
				String content = velocityManager.handle(contentVM, keyValMap);

				InboxManager newInboxManager = new InboxManager();
				InboxData newInboxData = newInboxManager.getCurrentInboxSettings();
				
				MessageData messageData = new MessageData();
				messageData.setFrom(newInboxData.getInboxDisplayName() + " <" + newInboxData.getInboxEmail() + ">");
				messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
				messageData.setHtmlBody(content);
				messageData.setSubject(subject);
				messageData.setTo(hrManagerEmail);
				
				
				TPMailSender sender = new TPMailSender();
				sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
			}
			 catch (Exception e) {
					log.error("error while sending Position approval track status to HR Manager", e);
				}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		//end to send Position approval status details to Requested By

		LoginManager loginManager = new LoginManager();
		LoginData loginData = loginManager.getUser(requisitionFeedbackData.getToUserId());
		String userStr = TemplateUtils.getConvertedUserData(loginData);
		contentStr = TemplateUtils.appndToToken(contentStr, userStr);

		String feedbackQueryString = getFeedbackQueryString();
		String linkToFeedback = Utils.buildTalentPoolURL(feedbackQueryString);
		linkToFeedback = TemplateUtils.getConstructedToken("LINK_TO_REQUISITION_APPROVAL_FEEDBACK", linkToFeedback);
		contentStr = TemplateUtils.appndToToken(contentStr, linkToFeedback);
		
		//external link
		String externalLinkToFeedback = Utils.buildExternalTalentPoolURL(feedbackQueryString);
		externalLinkToFeedback = TemplateUtils.getConstructedToken("LINK_TO_REQUISITION_APPROVAL_FEEDBACK_EXTERNAL", externalLinkToFeedback);
		contentStr = TemplateUtils.appndToToken(contentStr, externalLinkToFeedback);
		
		if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
			String positionStr = templateManager.getPositionStr(requisitionFeedbackData.getPositionId(),true);
			contentStr = TemplateUtils.appndToToken(contentStr, positionStr);
		}
		
		String contentVM = templateData.getTemplateContentFile();
		String subjectVM = templateData.getTemplateSubjectFile();
		String keyMap = templateData.getTemplateVariables();
		
		HashMap<String, String> keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
		VelocityManager velocityManager = new VelocityManager();
		String subject = velocityManager.handle(subjectVM, keyValMap);
		String content = velocityManager.handle(contentVM, keyValMap);
		
		MessageData messageData = new MessageData();
		messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
		messageData.setHtmlBody(content);
		messageData.setSubject(subject);
		messageData.setTo(loginData.getEmail());
		TPMailSender sender = new TPMailSender();
		sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
		
	}

	/**
	 * Builds queryString for link which navigates to dahsboard  
	 * @param positionId
	 * @param userId
	 * @return queryString
	 * @author PraveenK
	 */
	private String getFeedbackQueryString(){
		StringBuilder feedbackQueryString = new StringBuilder();
		feedbackQueryString.append("dashboard.do?");
		feedbackQueryString.append("mode").append("=").append("dashboard");
		return feedbackQueryString.toString();
	}
}
