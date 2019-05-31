package com.talentPool.requisition.scheduler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.manager.CandidateMasterTableManager;
import com.talentPool.customReports.manager.MasterTablesManager;
import com.talentPool.customReports.manager.PositionMasterTableManager;
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
import com.talentPool.positions.manager.PositionStatusScheduleManager;
import com.talentPool.requisition.dataobject.RequisitionFeedbackData;
import com.talentPool.requisition.manager.RequisitionFeedbackManager;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.manager.SchedulerManager;
import com.talentPool.todo.manager.ToDoManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author amit
 * 
 */
public class RequisitionAutoApprovalJob implements Job{
	private static Logger log = TPLogger.getLogger();
	private static final String SLASH = "/";
	//position id, check position current status ,if it's in approval stage,check days ,if days >= approve , forward to next step
	public void execute(JobExecutionContext context) throws JobExecutionException {
		PositionStatusScheduleManager schedulerManager = new PositionStatusScheduleManager();
		try {
			schedulerManager.updateSchedulerStartTimeAndStatus();
			String status = runAllSchedulers();
			schedulerManager.updateSchedulerEndTimeAndStatus(status);
		} catch (SQLException e) {
			log.error(GlobalConstants.ERROR, e);
		}
	}
	
	private String runAllSchedulers() throws SQLException {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		try {
			tran = new DBTransaction();
			log.info("========= START TABLE UPDATE JOB ============= : " + new Date());
			
			updatePositionMaster(tran);
			
			log.info("========= END TABLE UPDATE JOB ============= : " + new Date());
			
			PositionStatusScheduleManager positionStatusScheduleManager = new PositionStatusScheduleManager();			
			positionStatusScheduleManager.updateSchedulerLastRunDate(new Date(), tran);
			
			tran.commit();
			return SchedulerConstants.SCHEDULER_LAST_STATUS_SUCCESS;
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR, e);
			if(tran != null)
				tran.rollback();
			return SchedulerConstants.SCHEDULER_LAST_STATUS_FAIL;
		} finally {
			if(dq != null) {
				if(tran != null)
					dq.releaseTransaction(tran);
				else
					dq.releaseConnection();
			}
		}
	}
	
	private void updatePositionMaster(DBTransaction tran) throws Exception {
		PositionManager positionManager = new PositionManager();
		ArrayList positions =positionManager.getAllPendingPositions();
		if (positions != null && positions.size() > 0) {
			for (int i = 0; i < positions.size(); i++) {
				SimpleDataObject sDo = (SimpleDataObject) positions.get(i);
				String positionId=sDo.getString("positionId");
				checkPositionApprovalStatus( positionId);
			}
		}
	}
	private void checkPositionApprovalStatus(String positionId){
		//check position approval status 
		//if it's not appoved
		//check date when it was sent for approval
		//check number of days for default approval
		//add number of days to date sent for approval
		//if new date is equal to or greater than Today then
		//call autoApprovePosition
		RequisitionFeedbackManager requisitionFeedbackManager=new RequisitionFeedbackManager();
		RequisitionFeedbackData requisitionFeedbackData =requisitionFeedbackManager.getLastRequisitionFeedbackDataForPosition(positionId);
		Date lastFeedbackDate=requisitionFeedbackData.getFeedbackDate();
		
		String feedbackDatetocalculate=requisitionFeedbackData.getFeedbackDateToDisplay();
		String numberOfDays=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEFAULT_POSITION_APPROVAL_DURATION);
		String toStepId=requisitionFeedbackData.getToStepId();
		String toUserId=requisitionFeedbackData.getToUserId();
		if(!Utils.isBlankOrNull(toStepId)){
			boolean check=checkPositionApprovalExpiryDate(lastFeedbackDate,numberOfDays);
			
					if(check){
						//send email to Hr Manager for pending requisition approval
						try{
							 if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL))){ 
						sendEmailForPendingRequisitionApproval(TemplateConstants.TEMPLATE_TYPE_REQUISITION_PENDING_APPROVAL_NOTIFICATION_TO_HRMANAGER,"",positionId,toStepId,toUserId);
							 }
						}
						catch(Exception e){
							TPLogger.getLogger().error(e.getStackTrace());
						}
					}
					else{
						 if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_AUTO_APPROVE_REQUISITION))){ 
								autoApprovePosition(positionId,requisitionFeedbackData);
						 }
					
					}
		}
		else{
			//nothing; if null it means position is approved for all stage
		}
		
	}
	private void autoApprovePosition(String positionId,RequisitionFeedbackData requisitionFeedbackData){
		try {
		//approve position
		//send position approval Notification to next person call RequisitionApprovalNotificationJob
		RequisitionFeedbackManager requisitionFeedbackManager=new RequisitionFeedbackManager();
		//requisitionFeedbackManager.addFeedback(String positionId, String fromStepId, String toStepId, String byUserId, String toUserId, 
		//String feedbackDecision, String comment, String notifyUserIds)
		RequisitionFeedbackData nextStepFeedbackData=requisitionFeedbackManager.getNextRequisitionFeedbackDataForPosition(positionId);
		String byUserId =requisitionFeedbackData.getToUserId() ;
		
		String feedbackDecision = requisitionFeedbackData.getFeedbackDecision();
		String fromStepId = requisitionFeedbackData.getToStepId();
		
		String comment = "Position got auto approved due to default duration passed";
		
		String toStepId = "";
		String toUserId = "";
		String notifyUserIds="";
		if(nextStepFeedbackData!=null){
			 toStepId = nextStepFeedbackData.getToStepId();
			 toUserId = nextStepFeedbackData.getToUserId();
			 notifyUserIds=nextStepFeedbackData.getToUserId();
		}
		
		String feedbackId ="";
		if(!Utils.isBlankOrNull(toUserId)&&!Utils.isBlankOrNull(toStepId)&&!Utils.isBlankOrNull(notifyUserIds)){
			feedbackId= requisitionFeedbackManager.addFeedback( positionId,  fromStepId,  toStepId,  byUserId,  toUserId, 
					 feedbackDecision,  comment,  notifyUserIds);	
		}
		else{
			feedbackId= requisitionFeedbackManager.addFeedback( positionId,  fromStepId,  "",  byUserId,  "", 
					 feedbackDecision,  comment,  "");
		}
		
		//regenerate todo
		ToDoManager toDoManager = new ToDoManager();
		toDoManager.regenerateToDo(positionId, null, null);
		}
		catch (Exception e) {
			
			TPLogger.getLogger().error("Error While edit fb", e);
		}
	}
	
	private final boolean checkPositionApprovalExpiryDate(Date lastFeedbackDate, String noOfDays) {
		boolean isValidDate = false;
		try {
			Date approvalExpiryDate = new Date();
			Date today = new Date();
			if (lastFeedbackDate == null || noOfDays == null) {
				isValidDate = false;
			}
			
			/*approvalExpiryDate = Utils.convertToDate(lastFeedbackDate, "dd" + SLASH
						+ "MM" + SLASH + "yyyy");*/
			approvalExpiryDate = Utils.adjustDateBy(lastFeedbackDate, Calendar.DATE,
						Integer.parseInt(noOfDays));
				if (!today.before(approvalExpiryDate)) {
					isValidDate = false;
				} else {
					isValidDate = true;
				}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
			isValidDate = false;
		}
		return isValidDate;
	}
	
	public void sendEmailForPendingRequisitionApproval(String templateTypeId,String userId, String positionId, String toStepId,String toUserId) throws Exception {
		TemplateManager templateManager = new TemplateManager();
		TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(templateTypeId);
		String contentVM = templateData.getTemplateContentFile();
		String subjectVM = templateData.getTemplateSubjectFile();
		String keyMap = templateData.getTemplateVariables();

		LoginManager loginManager = new LoginManager();
		LoginData userData = loginManager.getUser(toUserId);		
		String contentStr = TemplateUtils.getConvertedUserData(userData);
		String employeeStr = "";
		String globalVar = "";
		//String userStr = TemplateUtils.getConvertedUserData(loginData);
		//contentStr = TemplateUtils.appndToToken(contentStr, userStr);
		
		
		PositionManager positionManager = new PositionManager();
		PositionData pData = positionManager.getPositionSummary(positionId);
		String positionTitle = TemplateUtils.getConstructedToken("REQUISITION_TITLE", pData.getPositionTitle());
		contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
		
		RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
		
		String requisitionCurrentStatus=requisitionFeedbackManager.getRequisitionCurrentStatus(positionId,toStepId);
		String positionStatus = TemplateUtils.getConstructedToken("REQUISITION_STATUS", requisitionCurrentStatus);
		contentStr = TemplateUtils.appndToToken(contentStr, positionStatus);
		
		String toUserName=TemplateUtils.getConstructedToken("USER_FNAME", userData.getFirstName());
		contentStr = TemplateUtils.appndToToken(contentStr, toUserName);
		
		if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
			String positionStr = templateManager.getPositionStr(positionId,true);
			contentStr = TemplateUtils.appndToToken(contentStr, positionStr);
		}
		InboxManager inboxManager = new InboxManager();
		InboxData inboxData = inboxManager.getCurrentInboxSettings();
		
		if(inboxData!=null && !Utils.isBlankOrNull(inboxData.getInboxDisplayName()) ){
			globalVar = TemplateUtils.getConvertedGlobalVars(inboxData);
			contentStr = TemplateUtils.appndToToken(contentStr, globalVar);
		}

		HashMap<String, String> keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
		VelocityManager velocityManager = new VelocityManager();
		String subject = velocityManager.handle(subjectVM, keyValMap);
		String content = velocityManager.handle(contentVM, keyValMap);

		
		MessageData messageData = new MessageData();
		messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
		messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
		messageData.setHtmlBody(content);
		messageData.setSubject(subject);
		TPMailSender sender = new TPMailSender();
		
		String[] roles = new String[1];
		roles[0] = "" + UserConstants.ROLE_HR_MANAGER;
		
		ArrayList<com.talentPool.positions.dataobject.UserData> users = positionManager.getUsersForPosition(positionId, roles);
		for (com.talentPool.positions.dataobject.UserData userData2 : users) {
				messageData.setTo(userData2.getUserEmail());
				try{
					sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
				}catch(Exception e){
					TPLogger.getLogger().error("Failed to send Requisition Approval  notification email to "+userData2.getUserEmail(), e);
				}							
			}
	}
}
