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
import com.talentPool.selectionProcess.utils.SelectionProcessUtils;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author shivprasad
 * 
 */
public class RequisitionApprovalNotificationJob implements Job {
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("IN REQUISITION APPROVAL NOTIFICATION SCHEDULER JOB");
		try {
			String positionId = context.getTrigger().getJobDataMap().getString("positionId");
			String userId = context.getTrigger().getJobDataMap().getString("userId");			
			
			constructAndSendEmail(positionId, userId);
		} catch (Exception e) {
			log.error("Error in requisition feedback scheduler job", e);
		}
		log.debug("EXIT REQUISITION APPROVAL NOTIFICATION SCHEDULER JOB");
	}

	private void constructAndSendEmail(String positionId, String userId) throws Exception{
		InboxManager inboxManager = new InboxManager();
		InboxData inboxData = inboxManager.getCurrentInboxSettings();
		String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
		// get template data
		TemplateManager templateManager = new TemplateManager();
		TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION_TO_USER);

		LoginManager loginManager = new LoginManager();
		LoginData loginData = loginManager.getUser(userId); 
		String userStr = TemplateUtils.getConvertedUserData(loginData);
		contentStr = TemplateUtils.appndToToken(contentStr, userStr);
		
		PositionManager positionManager = new PositionManager();
		PositionData positionData = positionManager.getPositionSummary(positionId);
	
		
		String requisitionStr = TemplateUtils.getConvertedRequisitionData(positionData);
		contentStr = TemplateUtils.appndToToken(contentStr, requisitionStr);
		
		if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
			String positionStr = templateManager.getPositionStr(positionData,true);
			contentStr = TemplateUtils.appndToToken(contentStr, positionStr);
		}
		
		//start of To send Position approval status details to Requested By
		
				if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL).equals(GlobalConstants.ENABLED)){
					try{
					
						//keeping Requested By in CC for all position related approval notification
						
						LoginData requestorData = loginManager.getUser(positionData.getRequestedById());
						String hrManagerEmail=requestorData.getEmail();
						TemplateManager newTemplateManager = new TemplateManager();
						TemplateData newTemplateData = newTemplateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION_TO_REQUESTED_BY);
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
}
