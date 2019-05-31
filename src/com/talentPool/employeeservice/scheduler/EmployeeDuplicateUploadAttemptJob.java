/**
 * 
 */
package com.talentPool.employeeservice.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
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
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author Shantanu
 *
 */
public class EmployeeDuplicateUploadAttemptJob implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			TPLogger.getLogger().debug("Start Employee duplicate attempt job");
			String sendDuplicateNotification = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION);
			String userIds = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL);
			if (sendDuplicateNotification.equals(GlobalConstants.ENABLED) && !Utils.isBlankOrNull(userIds)) {
				JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
				String sourceName = jobDataMap.getString("sourceName");
				String userFirstName = jobDataMap.getString("userFirstName");
				String positionName = jobDataMap.getString("positionName");

				HashMap<String, String> nameValues = getFileldsNameValueMap(jobDataMap);

				// get template data
				String DUPLICATE_DATA_INFO = getDuplicateBodyData(sourceName, userFirstName, positionName, nameValues);
				String contentStr = TemplateUtils.getConstructedToken("DUPLICATE_DATA_INFO", DUPLICATE_DATA_INFO);

				TemplateManager templateManager = new TemplateManager();
				TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_DUPLICATE_UPLOAD_TRIED_BY_EMPLOYEE_NOTIFICATION);
				String contentVM = templateData.getTemplateContentFile();
				String subjectVM = templateData.getTemplateSubjectFile();
				String keyMap = templateData.getTemplateVariables();

				HashMap<String, String> keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
				VelocityManager velocityManager = new VelocityManager();
				String subject = velocityManager.handle(subjectVM, keyValMap);
				String content = velocityManager.handle(contentVM, keyValMap);

				// send email to users
				InboxManager inboxManager = new InboxManager();
				InboxData inboxData = inboxManager.getCurrentInboxSettings();

				String[] uIds = userIds.split(",");
				for (int i = 0; i < uIds.length; i++) {
					try {
						if (!Utils.isBlankOrNull(uIds[i])) {
							LoginManager loginManager = new LoginManager();
							LoginData loginData = loginManager.getUser(uIds[i]);
							MessageData messageData = new MessageData();
							messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
							messageData.setHtmlBody(content);
							messageData.setSubject(subject);
							messageData.setTo(loginData.getEmail());
							TPMailSender sender = new TPMailSender();
							sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
						}
					} catch (Exception e) {
						TPLogger.getLogger().error("Unable to send email", e);
					}
				}

			}
			TPLogger.getLogger().debug("End Employee duplicate attempt job");
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}

	}

	private String getDuplicateBodyData(String sourceName, String userFirstName, String positionName, HashMap<String, String> nameValues) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("Source: " + sourceName + "<br/>");
			sb.append("Uploaded by: " + userFirstName + "<br/>");
			sb.append("Uploaded for position: " + positionName + "<br/><br/>");
			sb.append("<b>Duplicate resume data</b><br/>");
			Iterator<String> itr = nameValues.keySet().iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				sb.append(key + ": " + nameValues.get(key) + "<br/>");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while constructing duplicate data in job", e);
		}
		return sb.toString();
	}

	private HashMap<String, String> getFileldsNameValueMap(JobDataMap jobDataMap) {
		String[] keys = jobDataMap.getKeys();

		HashMap<String, String> nameValues = new HashMap<String, String>();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, false);

		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				if (keys[i].startsWith("$")) {
					String fieldName = keys[i].substring(1);
					for (int k = 0; customFields != null && k < customFields.size(); k++) {
						if (customFields.get(k).getFieldName().equals(fieldName)) {
							fieldName = customFields.get(k).getFieldDisplayName();
							break;
						}
					}
					nameValues.put(fieldName, jobDataMap.getString(keys[i]));
				}
			}
		}
		return nameValues;
	}

}
