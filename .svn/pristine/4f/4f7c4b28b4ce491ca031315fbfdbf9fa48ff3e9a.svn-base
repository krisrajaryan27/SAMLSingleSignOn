package com.talentPool.inbox.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.form.InboxForm;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.dataobject.PositionVendorData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.user.dataobject.LoginData;

public class EmployeeNotificationJob extends Thread{

	private String positionIds = null;	
	private String templateCode = null;
	
	public EmployeeNotificationJob(String positionIds, String templateCode) {
		this.positionIds = positionIds;
		this.templateCode = templateCode;
	}
	
	@Override
	public void run() {
		try
		{
			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateData(templateCode);

			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			
			String[] positionIdArray = positionIds.split(",");
			String recipients = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_EMPLOYEES_GROUP_EMAIL_ID);
				
			for (int i = 0; i < positionIdArray.length; i++) {
				
				sendNotification(positionIdArray[i], recipients, inboxData, templateData);		
								
			}	
		}catch(Exception e){
			
		}
	}
	
	private void sendNotification(String positionId, String recipients, InboxData inboxData, TemplateData templateData){
		TemplateManager templateManager = new TemplateManager();
		try{			
			String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
			
			PositionManager positionManager = new PositionManager();
			PositionData positionData =positionManager.getPositionSummary(positionId);

			if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
				String positionStr = templateManager.getPositionStr(positionData,true);
				contentStr = TemplateUtils.appndToToken(contentStr, positionStr);
			}
			
			String PositionVendorPublishData = TemplateUtils.getConvertedPositionEmployeesPublishData(positionData);
			contentStr = TemplateUtils.appndToToken(contentStr, PositionVendorPublishData);
			
			HashMap keyValMap = TemplateUtils.getKeyValueMap(templateData.getTemplateVariables(), contentStr);
			VelocityManager velocityManager = new VelocityManager();
			String subject = velocityManager.handle(templateData.getTemplateSubjectFile(), keyValMap);
			String content = velocityManager.handle(templateData.getTemplateContentFile(), keyValMap);
			
			MessageData messageData = new MessageData();
			messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
			messageData.setHtmlBody(content);
			messageData.setSubject(subject);
			messageData.setTo(recipients);
			System.out.println(content);
			
			TPMailSender sender = new TPMailSender();
			sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("error while sending notification to employees for published position status changed", e);
		}
	}
}
