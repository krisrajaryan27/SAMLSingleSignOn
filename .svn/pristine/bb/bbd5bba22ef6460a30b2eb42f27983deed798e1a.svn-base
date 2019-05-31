package com.talentPool.notifier.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.custom.constants.CustomFieldConstants;
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
import com.talentPool.requisition.dataobject.RequisitionApprovalStepData;
import com.talentPool.requisition.manager.RequisitionManager;
import com.talentPool.todo.manager.ToDoManager;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author PraveenK
 * @modified  Jul 30, 2012
 */
public class TPEscalationEmailJob implements Job, TransportListener {

	/**
	 * When this job is triggered, it will fetch escalation timeout from application settings ( {@link GlobalConstants}#PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION ).
	 * Based on the timeout it will fetch all pending requisition and will shoot a escalation email to requested by.
	 * Eg: If timeout is defined as 2 days and job running at 8:00 AM of 30-07-12 then,
	 * <br> Job will fetch all requisitions which are pending before 28-07-12 (i.e., 27, 26, 25 etc).
	 * <br> If timeout is 0 -
	 * <br> Job will fetch all requisitions which are pending before 30-07-12 (i.e., 29, 28, 27 etc).
	 * <br> If timeout is -1 -
	 * <br> Job will not send any escaltion emails.
	 **/
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TPLogger.getLogger().debug("Start Escalation Email Job");
		if (EscalationEmailSchedular.JOB_STATUS_BUZY) {
			TPLogger.getLogger().debug("Escalation email is already running. Exiting Escalation email job");
			return;
		}
		EscalationEmailSchedular.JOB_STATUS_BUZY = true;
		int escalationDuration = Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION));
		try {
			if(escalationDuration>=0){
				ToDoManager toDoManager = new ToDoManager();
				ArrayList<SimpleDataObject> overdueApprovalList = toDoManager.getOverduePositionApprovalList(Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION)));
				
				TemplateManager templateManager = new TemplateManager();
				TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_OVERDUE_REQUISITION_APPROVAL_NOTIFICATION_TO_REQUESTOR);
				String contentVM = templateData.getTemplateContentFile();
				String subjectVM = templateData.getTemplateSubjectFile();
				String keyMap = templateData.getTemplateVariables();
				
				PositionManager positionManager = new PositionManager();
				LoginManager loginManager = new LoginManager();
				RequisitionManager requisitionManager = new RequisitionManager();
				
				for (SimpleDataObject overdueApproval : overdueApprovalList) {
					PositionData positionData =positionManager.getPositionSummary(overdueApproval.getString("positionId"));
					String contentStr = TemplateUtils.getConvertedPositionDescriptionData(positionData);
					
					if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
						String customFiledsStr = templateManager.getCustomFieldStr(overdueApproval.getString("positionId"), CustomFieldConstants.ENTITY_TYPE_POSITION);
						contentStr = TemplateUtils.appndToToken(contentStr, customFiledsStr);
					 }
					
					LoginData requestorData = loginManager.getUser(positionData.getRequestedById());
					
					RequisitionApprovalStepData requisitionApprovalStepData = requisitionManager.getRequisitionApprovalStepData(overdueApproval.getString("currentStepId"),overdueApproval.getString("userId"));
					String convertedRequisitionApprovalStepData = TemplateUtils.getConvertedRequisitionApprovalStepData(requisitionApprovalStepData);
					contentStr = TemplateUtils.appndToToken(contentStr, convertedRequisitionApprovalStepData);
			
					HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
					VelocityManager velocityManager = new VelocityManager();
					String subject = velocityManager.handle(subjectVM, keyValMap);
					String content = velocityManager.handle(contentVM, keyValMap);
				
					InboxManager inboxManager = new InboxManager();
					InboxData inboxData = inboxManager.getCurrentInboxSettings();
					
					MessageData messageData = new MessageData();
					messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
					messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
					messageData.setHtmlBody(content);
					messageData.setSubject(subject);
					messageData.setTo(requestorData.getEmail());
					TPMailSender sender = new TPMailSender();
					try{
						sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
					}catch(Exception e){
						TPLogger.getLogger().error("Failed to send notification email to "+requestorData.getEmail(), e);
					}
				}
			}else{
				TPLogger.getLogger().trace("Escalation duration is defined as negative:" + escalationDuration);	
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error Escalation Email Job", e);
		}
		TPLogger.getLogger().debug("End Escalation Email Job");
		EscalationEmailSchedular.JOB_STATUS_BUZY = false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
	 */
	public void messageDelivered(TransportEvent event) {
		// TODO Auto-generated method stub
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
	 */
	public void messageNotDelivered(TransportEvent event) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.mail.event.TransportListener#messagePartiallyDelivered(javax.mail.event.TransportEvent)
	 */
	public void messagePartiallyDelivered(TransportEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TPEscalationEmailJob teej = new TPEscalationEmailJob();
		try {
			teej.execute(null);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}

}
