/**
 * 
 */
package com.talentPool.audit.action;

import java.io.File;
import java.util.HashMap;

import com.talentPool.audit.manager.AuditManager;
import com.talentPool.common.Logger.TPAudit;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
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
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author Ajeet
 *
 */
public class AuditAction extends TPDispatchAction {
	
	public void insertAuditInfo(String entityField, String auditType, String entityId, String entityType, String userId,
			String positionIdInHiringStep,String positionStepIdFrom,String positionStepIdTo, boolean entryToReport, String sourceIp){
		try {
			AuditManager auditManager = new AuditManager();
			String auditDesc = auditManager.addAudit(entityField, auditType, entityId, entityType, userId, positionIdInHiringStep,
					positionStepIdFrom, positionStepIdTo, entryToReport, sourceIp);
			TPAudit.getLogger().debug(auditDesc);			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void auditLogRecoveryNotification(){
		try{
			String logDir = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("log.dir", "./"));
			String logFileName = TPApplicationProperties.getProperty("audit.file.name", "audit.log").trim();
			logFileName = Utils.concatFilePath(logDir, logFileName);
			File auditFile = new File(logFileName);//calculating the file size so as to shoot a mail to super admin to recover the audit trail file, value is in bytes
			long notifyFileSize=Long.parseLong(TPApplicationProperties.getProperty("audit.max.file.size.notification"));//Max size of the file after which talentpool will start notifying super admin, is set in talentpool.properties, value is in bytes
			if (auditFile.exists()) {				
				if ((auditFile.length()>notifyFileSize*0.8 & auditFile.length()<notifyFileSize*0.82) | (auditFile.length()>notifyFileSize*0.9 & auditFile.length()<notifyFileSize*0.92)
						| (auditFile.length()>notifyFileSize*0.95 & auditFile.length()<notifyFileSize*0.97)) {	
				//if ((db1>db*0.8 & db1<db*0.82) | (db1>db*0.9 & db1<db*0.92) | (db1>db*0.95 & db1<db*0.97)) {	
					sendNotificationMails("auditTrailFileRecoveryNotification");
				}				
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
	}

	/*
	 *Send Mails using the template type only
	 */
	
	public void sendNotificationMails(String templateType) {
		try {
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(UserConstants.ADMIN_ID);
	
			TPLogger.getLogger().debug("SEND NOTIFICATION TO SUPER ADMIN ");
			// Construct token data
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);

			String userStr = TemplateUtils.getConvertedContactData(loginData);
			contentStr = TemplateUtils.appndToToken(contentStr, userStr);

			// get template data
			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateData(templateType);

			String contentVM = templateData.getTemplateContentFile();
			String subjectVM = templateData.getTemplateSubjectFile();
			String keyMap = templateData.getTemplateVariables();
			HashMap<String, String> keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);

			VelocityManager velocityManager = new VelocityManager();
			String subject = velocityManager.handle(subjectVM, keyValMap);
			String content = velocityManager.handle(contentVM, keyValMap);
		
			String	toAddress = loginData.getName() + " <" + loginData.getEmail() + ">;";		
				
			MessageData messageData = new MessageData();
			messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setReplyTo(loginData.getName() + " <" + loginData.getEmail() + ">");
			messageData.setTo(toAddress);
			messageData.setHtmlBody(content);
			messageData.setSubject(subject);

			TPMailSender mailer = new TPMailSender();
			mailer.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);

			TPLogger.getLogger().debug("END NOTIFICATION TO SUPER ADMIN");

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
}
