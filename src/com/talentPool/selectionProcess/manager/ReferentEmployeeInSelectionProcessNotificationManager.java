package com.talentPool.selectionProcess.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.dataobject.UserData;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

public class ReferentEmployeeInSelectionProcessNotificationManager {
	
	public static boolean sendReferentEmployeeInSelectionProcessNotification(ApplicantData applicantData, String positionId, String stepId){
		boolean isPresent=false;
		if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION))){
			if(applicantData.getSourceTypeId().equals("5")){  //Employee Referrals type
				ApplicantManager applicantManager = new ApplicantManager();
				ArrayList<LoginData> usersForSource = applicantManager.getUsersForSource(applicantData.getApplicantSourceId()+"");
				if(usersForSource.size()>0){
					LoginData sourceUser = usersForSource.get(0);
					
					SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
					List usersPresentInSelectionProcessStep = selectionProcessManager.getUsersPresentInSelectionProcessStep(stepId);
					for (Object object : usersPresentInSelectionProcessStep) {
						SimpleDataObject sdo = (SimpleDataObject) object;
						if (sourceUser.getUserId().equals(sdo.getString("userId"))) {
							isPresent = true;
							break;
						}
					}					
					if(isPresent){						
						try{								
							MastersManager mastersManager = new MastersManager();
							PositionManager positionManager = new PositionManager();
							TemplateManager templateManager = new TemplateManager();
							
							SourceData sourceData = mastersManager.getSource(String.valueOf(applicantData.getApplicantSourceId()));
							String contentStr = TemplateUtils.getConvertedEmployeeData(sourceData);											
							
							String convertedApplicantData = TemplateUtils.getConvertedApplicantData(applicantData, null);
							contentStr = TemplateUtils.appndToToken(contentStr, convertedApplicantData);
							
							// get template data
							TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION);
							
							String contentVM = templateData.getTemplateContentFile();
							String subjectVM = templateData.getTemplateSubjectFile();
							String keyMap = templateData.getTemplateVariables();
							
							
							if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
								String positionStr = templateManager.getPositionStr(positionId,true);
								contentStr = TemplateUtils.appndToToken(contentStr, positionStr);
							}
							
							String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME",positionManager.getPositionStepName(Integer.parseInt(stepId)));
							contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
							
							for (Object object : usersPresentInSelectionProcessStep) {							
								try{							
									LoginManager loginManager = new LoginManager();
									SimpleDataObject obj = (SimpleDataObject) object;
									String userId = obj.getString("userId");
									LoginData loginData = loginManager.getUser(userId);
									String convertedContactData = TemplateUtils.getConvertedContactData(loginData);
									String contentString = TemplateUtils.appndToToken(contentStr, convertedContactData);		
									
									HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentString);
				
									VelocityManager velocityManager = new VelocityManager();
									String subject = velocityManager.handle(subjectVM, keyValMap);
									String content = velocityManager.handle(contentVM, keyValMap);
									// send email
									MessageData messageData = new MessageData();
									// messageData.setFrom(inboxData.getInboxDisplayName() + " <" +
									// inboxData.getInboxEmail() + ">");
									InboxManager inboxManager = new InboxManager();
									InboxData inboxData = inboxManager.getCurrentInboxSettings();
									
									messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
									messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
									messageData.setHtmlBody(content);
									messageData.setSubject(subject);
									messageData.setTo(loginData.getEmail());
									TPMailSender sender = new TPMailSender();
									sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
								} catch (Exception e) {
									TPLogger.getLogger().error("Error in execute", e);
								}
							}
						} catch (Exception e) {
							TPLogger.getLogger().error("Error in execute", e);
						}
					}
				}
			}
		}	
		return isPresent;
	}
}
