/**
 * 
 */
package com.talentPool.websiteservice.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.utils.ApplicantUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
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
 * @author pallavi
 *
 */
public class WebsiteNotificationManager {

	public void sendNotification(MessageData data) {
		try {
			String sendDuplicateNotification = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION);
			String userIds = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL);
			if (sendDuplicateNotification.equals(GlobalConstants.ENABLED) && !Utils.isBlankOrNull(userIds)) {
				String tmpEmailId = Utils.getRandomString(12, 0, 0, true, true, null, new Random());

				InboxManager inboxManager = new InboxManager();
				inboxManager.copyAttachmentsDataToTemp(data.getMessageId(), InboxConstants.EMAIL_LOCATION_INBOX, tmpEmailId);
				
				MessageData messageData = new MessageData();
				ArrayList attachments = inboxManager.getTmpAttachments(tmpEmailId);
				if (attachments != null) {
					messageData.setAttachments(attachments);
				}				
				String subject = TPLabels.getLabel("website.duplicate_resume_notification.subject");
				messageData.setSubject(subject);
				if (!Utils.isBlankOrNull(data.getHtmlBody())) {
					messageData.setHtmlBody(data.getHtmlBody());
				} else {
					messageData.setTextBody(data.getTextBody());
				}			
				InboxData inboxData = inboxManager.getCurrentInboxSettings();
				messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
				
				LoginManager loginManager = new LoginManager();
				TPMailSender sender = new TPMailSender();
				String[] uIds = userIds.split(",");				
				for (int i = 0; i < uIds.length; i++) {
					try {
						if (!Utils.isBlankOrNull(uIds[i])) {							
							LoginData loginData = loginManager.getUser(uIds[i]);
							messageData.setTo(loginData.getEmail());							
							sender.send(messageData, InboxConstants.EMAIL_BODY_TEXT, DocumentConstants.documentsPath);
						}
					} catch (Exception e) {
						TPLogger.getLogger().error("Unable to send email", e);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
}
