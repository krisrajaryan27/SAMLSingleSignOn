/**
 * 
 */
package com.talentPool.todo.manager;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.budget.BudgetConstants;
import com.talentPool.calendar.CalendarConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.todo.constants.ToDoConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.UserManager;



/**
 * @author Ajeet
 *
 */
@Component
public class ToDoManager {
	/**
	 * Add todo entry in todo table. always called witin transaction
	 * 
	 * @param userId
	 * @param processId
	 * @param positionId
	 * @param applicantId
	 * @param currentStepId
	 * @param appointmentId
	 * @param appointmentTime
	 * @param todoType
	 * @param dueDate
	 * @param tran
	 * @throws Exception
	 */
	private void addToDo(String userId, String processId, String positionId, String applicantId, String budgetItemId,
			String currentStepId, String appointmentId,	Date appointmentTime, String appointmentStatusId, 
			String userResponsibleForScheduling, String isAuthorizedToMove, String isInterviewer,
			String interviewerCanConfirm, String stepIsscheduled, String feedback, String todoType,	
			Date dueDate, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if(!Utils.isBlankOrNull(applicantId)) {
				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData data = applicantManager.getApplicantData(applicantId);
				if(data == null) {
					return;
				}
			}
			
			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_AddToDo");
			} else {
				dq = new DBPreparedQuery("dToDoManager_AddToDo", tran);
			}
			int cnt = 1;
			dq.setString(cnt++, userId);
			setNullOrString(dq, cnt++, processId);
			setNullOrString(dq, cnt++, positionId);
			setNullOrString(dq, cnt++, applicantId);
			setNullOrString(dq, cnt++, budgetItemId);
			setNullOrString(dq, cnt++, currentStepId);
			setNullOrString(dq, cnt++, stepIsscheduled);
			setNullOrString(dq, cnt++, userResponsibleForScheduling);
			setNullOrString(dq, cnt++, isAuthorizedToMove);
			setNullOrString(dq, cnt++, isInterviewer);
			setNullOrString(dq, cnt++, interviewerCanConfirm);
			setNullOrString(dq, cnt++, appointmentId);
			if (appointmentTime == null) {
				dq.setNull(cnt++, Types.DATE);
			} else {
				dq.setTimestamp(cnt++, new Timestamp(appointmentTime.getTime()));
			}
			setNullOrString(dq, cnt++, appointmentStatusId);
			dq.setString(cnt++, feedback);
			dq.setString(cnt++, todoType);
			dq.setTimestamp(cnt++, new Timestamp(dueDate.getTime()));
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	private void setNullOrString(DBPreparedQuery dq, int ind, String val) throws Exception {
		if (Utils.isBlankOrNull(val)) {
			dq.setNull(ind, Types.BIGINT);
		} else {
			dq.setString(ind, val);
		}
	}

	/**
	 * Delete all todos mathing passed criteria. If user passes positionId and other params as null
	 * then all todos with positionid is deleted
	 * 
	 * @param todoType
	 * @param userId
	 * @param positionId
	 * @param applicantId
	 * @param currentStepId
	 * @param appointmentId
	 * @throws Exception
	 */
	public void deleteToDo(String positionId, String applicantId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(positionId)) {
				dynParams[0] = addWhereAnd(dynParams[0]);
				dynParams[0] += " position_id = ? ";
				dynamicContent.add(positionId);
			}
			if (!Utils.isBlankOrNull(applicantId)) {
				dynParams[0] = addWhereAnd(dynParams[0]);
				dynParams[0] += " applicant_id = ? ";
				dynamicContent.add(applicantId);
			}

			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_DeleteToDo", dynParams);
			} else {
				dq = new DBPreparedQuery("dToDoManager_DeleteToDo", dynParams, tran);
			}
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	private String addWhereAnd(String dynaQuery) {
		if (dynaQuery.length() == 0) {
			dynaQuery += " WHERE ";
		} else {
			dynaQuery += " AND ";
		}
		return dynaQuery;
	}

	/**
	 * user can regenerate todo, 1)For position and candidate and todo type selection 2)For single
	 * position and todo type selection 3)For single position and todo type position approval
	 * 
	 * @param positionId
	 * @param applicantId
	 */
	public void regenerateToDo(String positionId, String applicantId, DBTransaction tran) throws Exception {
		boolean isCommitRequired = false;
		try {
			if (tran == null) {
				tran = new DBTransaction();
				isCommitRequired = true;

			}
			deleteToDo(positionId, applicantId, tran);
			// in case both are blank just all todos are deleted
			if (!Utils.isBlankOrNull(applicantId) && !Utils.isBlankOrNull(positionId)) {
				// todo applicant/position specific
				ArrayList<SimpleDataObject> processList = getProcessList(positionId, applicantId, tran);
				addProcessToDo(processList, tran);

			} else if (!Utils.isBlankOrNull(positionId) && Utils.isBlankOrNull(applicantId)) {
				// todo position specific
				String positionStatus = getPositionStatus(positionId, tran);
				if (positionStatus.equals(PositionConstants.POSITION_STATUS_OPENED)) {
					ArrayList<SimpleDataObject> processList = getProcessList(positionId, null, tran);
					addProcessToDo(processList, tran);
				} else if (positionStatus.equals(PositionConstants.POSITION_STATUS_INPROCESS)) {
					// add position approval todo
					ArrayList<SimpleDataObject> approvalList = getPositionApprovalList(positionId, tran);
					addPositionApprovalToDo(approvalList, tran);
				}
			}else if(Utils.isBlankOrNull(positionId) && !Utils.isBlankOrNull(applicantId)){
				// todo applicant specific
				ArrayList<SimpleDataObject> processList = getProcessList(null, applicantId, tran);
				addProcessToDo(processList, tran);
			} else {
				// regenerate todo for all positions
				ArrayList<SimpleDataObject> approvalList = getPositionApprovalList(null, tran);
				addPositionApprovalToDo(approvalList, tran);
				ArrayList<SimpleDataObject> processList = getProcessList(null, null, tran);
				addProcessToDo(processList, tran);
			}
			if (isCommitRequired) {
				tran.commit();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if (isCommitRequired) {
				try {
					tran.rollback();
				} catch (Exception se) {
					TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				}
			}
			throw e;
		} finally {
			if (isCommitRequired) {
				tran.release();
			}
		}
	}


	/**
	 * Regenerate all todos
	 */
	public void regenerateAllToDos() {
		try {
			deleteToDo(null, null, null);
			
			TPLogger.getLogger().info("Starting regenerateAllToDos");
			long time = System.nanoTime();
			
			
			
			
			ArrayList<SimpleDataObject> approvalList = getPositionApprovalList(null, null);
			addPositionApprovalToDoRegenerate(approvalList, null);
			
			ArrayList<SimpleDataObject> budgetList = getBudgetApprovalList(null, null);
			addBudgetApprovalToDoRegenerate(budgetList, null);
			
			ArrayList<SimpleDataObject> processList = getProcessList(null, null, null);
			addProcessToDoRegenerate(processList, null);
			
			TPLogger.getLogger().info("Ending regenerateAllToDos "+ " Time taken : "+ (System.nanoTime()-time));
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	/**
	 * @param positionId
	 * @param applicantId
	 * @return list of all active candidates and positions if positionId and applicantId not present
	 *         else returns specific data
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<SimpleDataObject> getProcessList(String positionId, String applicantId, DBTransaction tran) {
		ArrayList<SimpleDataObject> result = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(positionId)) {
				dynParams[0] = addWhereAnd(dynParams[0]);
				dynParams[0] += " tasp.position_id=? ";
				dynamicContent.add(positionId);
			}
			if (!Utils.isBlankOrNull(applicantId)) {
				dynParams[0] = addWhereAnd(dynParams[0]);
				dynParams[0] += " tasp.applicant_id=? ";
				dynamicContent.add(applicantId);
			}
			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_GetProcessDataToAddToDo", dynParams);
			} else {
				dq = new DBPreparedQuery("dToDoManager_GetProcessDataToAddToDo", dynParams, tran);
			}
			
			int cnt = 1;
			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);

			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setId(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setId(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setId(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setId(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			
			result = dq.getResult();
			//filter the resulted process list to remove extra users from the list 
			result = filterProcessList(result);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				if (tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}

			}
		}
		return result;
	}

	
	private class ProcessApproval implements Callable <Void>{
		private String userId;
		private String processId;
		private String positionId;
		private String applicantId;
		private String currentStepId;
		private String appointmentId;
		private Date appointmentTime;
		private String appointmentStatusId;
		private String userResponsibleForScheduling;
		private String stepIsscheduled;
		private String interviewerCanConfirm;
		private String isAuthorizedToMove;
		private String isInterviewer;
		private String feedback;
		private Date dueDate;
		
		
		public ProcessApproval(String userId,String positionId,String currentStepId,Date dueDate,String processId,String applicantId,String appointmentId,Date appointmentTime,String appointmentStatusId,
				String userResponsibleForScheduling,String stepIsscheduled,String interviewerCanConfirm,String isAuthorizedToMove,String isInterviewer,String feedback) {
			this.userId=userId;
			this.positionId=positionId;
			this.currentStepId=currentStepId;
			this.dueDate=dueDate;
			this.processId=processId;
			this.applicantId=applicantId;
			this.appointmentId=appointmentId;
			this.appointmentTime=appointmentTime;
			this.appointmentStatusId=appointmentStatusId;
			this.userResponsibleForScheduling=userResponsibleForScheduling;
			this.stepIsscheduled=stepIsscheduled;
			this.interviewerCanConfirm=interviewerCanConfirm;
			this.isAuthorizedToMove=isAuthorizedToMove;
			this.isInterviewer=isInterviewer;
			this.feedback=feedback;
		}
		
		public  Void call() throws Exception {
			TPLogger.getLogger().info("Starting processApprovalThread thread "+ Thread.currentThread().getName());
			long time = System.nanoTime();
			addToDo(userId, processId, positionId, applicantId, null, currentStepId, appointmentId, 
					appointmentTime, appointmentStatusId, userResponsibleForScheduling, isAuthorizedToMove, isInterviewer,
					interviewerCanConfirm, stepIsscheduled,	feedback, ToDoConstants.TODO_TYPE_SELECTION, dueDate, null);
			TPLogger.getLogger().info("Ending processApprovalThread thread "+ Thread.currentThread().getName() +" Time taken : "+ (System.nanoTime()-time));
			return null;
		}
		
	}
	
	private void addProcessToDoRegenerate(ArrayList<SimpleDataObject> processList, DBTransaction tran) throws Exception {
		if (processList != null) {
			ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for (int i = 0; i < processList.size(); i++) {				
				try {
					SimpleDataObject sdo = processList.get(i);
					String userId = sdo.getString("userId");
					String processId = sdo.getString("processId");
					String positionId = sdo.getString("positionId");
					String applicantId = sdo.getString("applicantId");
					String currentStepId = sdo.getString("currentStepId");
					String appointmentId = sdo.getString("appointmentId");
					Date appointmentTime = sdo.getDate("appointmentFromDate");
					String appointmentStatusId = sdo.getString("appointmentStatusId");
					String userResponsibleForScheduling = sdo.getString("userResponsibleForScheduling");
					String stepIsscheduled = sdo.getString("stepIsscheduled");
					String interviewerCanConfirm = sdo.getString("interviewerCanConfirm");
					String isAuthorizedToMove = sdo.getString("isAuthorizedToMove");
					String isInterviewer = sdo.getString("isInterviewer");
					String feedback = sdo.getString("feedback");
					Date dueDate = sdo.getDate("dateModified");					
					
					ProcessApproval processApproval = new ProcessApproval(userId, positionId, currentStepId,dueDate,processId,applicantId,appointmentId,appointmentTime
							,appointmentStatusId,userResponsibleForScheduling,stepIsscheduled,interviewerCanConfirm,isAuthorizedToMove,isInterviewer,feedback);
					Future<Void> result = executorService.submit(processApproval);
					//addToDo(userId, processId, positionId, applicantId, null, currentStepId, appointmentId, 
						//	appointmentTime, appointmentStatusId, userResponsibleForScheduling, isAuthorizedToMove, isInterviewer,
							//interviewerCanConfirm, stepIsscheduled,	feedback, ToDoConstants.TODO_TYPE_SELECTION, dueDate, tran);
					
				} catch (Exception e) {
					if (tran != null) {
						throw e;
					}
				}
			}
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Add todo for all active candidates and positions
	 * 
	 * @param processList
	 * @param tran
	 * @throws Exception
	 */
	private void addProcessToDo(ArrayList<SimpleDataObject> processList, DBTransaction tran) throws Exception {
		if (processList != null) {
			for (int i = 0; i < processList.size(); i++) {				
				try {
					SimpleDataObject sdo = processList.get(i);
					String userId = sdo.getString("userId");
					String processId = sdo.getString("processId");
					String positionId = sdo.getString("positionId");
					String applicantId = sdo.getString("applicantId");
					String currentStepId = sdo.getString("currentStepId");
					String appointmentId = sdo.getString("appointmentId");
					Date appointmentTime = sdo.getDate("appointmentFromDate");
					String appointmentStatusId = sdo.getString("appointmentStatusId");
					String userResponsibleForScheduling = sdo.getString("userResponsibleForScheduling");
					String stepIsscheduled = sdo.getString("stepIsscheduled");
					String interviewerCanConfirm = sdo.getString("interviewerCanConfirm");
					String isAuthorizedToMove = sdo.getString("isAuthorizedToMove");
					String isInterviewer = sdo.getString("isInterviewer");
					String feedback = sdo.getString("feedback");
					Date dueDate = sdo.getDate("dateModified");					
					
					addToDo(userId, processId, positionId, applicantId, null, currentStepId, appointmentId, 
							appointmentTime, appointmentStatusId, userResponsibleForScheduling, isAuthorizedToMove, isInterviewer,
							interviewerCanConfirm, stepIsscheduled,	feedback, ToDoConstants.TODO_TYPE_SELECTION, dueDate, tran);
					
				} catch (Exception e) {
					if (tran != null) {
						throw e;
					}
				}
			}
		}
	}

	/**
	 * This will filter the process list to remove extra user when we mark a candidate attended.
	 * Generate Action for all interviewers and HR managers + recruiters.
	 * Include all DM only if there are no DM present in interview list.
	 * */
	private ArrayList<SimpleDataObject> filterProcessList(ArrayList<SimpleDataObject> processList) {
		// run this filter to each set of group whose process id is same 
		ArrayList<SimpleDataObject> finalProcessList = new ArrayList<SimpleDataObject>();
		ArrayList<SimpleDataObject> newProcessList = new ArrayList<SimpleDataObject>();
		ArrayList<SimpleDataObject> interViewerList = new ArrayList<SimpleDataObject>();
		ArrayList<SimpleDataObject> otherUserList = new ArrayList<SimpleDataObject>();
		UserManager userManager = new UserManager();
		String oldProcessId="";
		String newProcessId="";
		try {
			for (int i = 0; i < processList.size(); i++) {
				SimpleDataObject sdo = processList.get(i);
				String processId = sdo.getString("processId");
				newProcessId=processId;
				if(!oldProcessId.equals(newProcessId)){	
				 //reset list and run for new process id
					renderProcessList(finalProcessList, newProcessList, interViewerList, otherUserList);
					
					newProcessList = new ArrayList<SimpleDataObject>();
					interViewerList = new ArrayList<SimpleDataObject>();
					otherUserList = new ArrayList<SimpleDataObject>();
					
				}
				checkFilter(newProcessList, interViewerList, otherUserList, userManager, sdo);
				oldProcessId=newProcessId;
			}
			
			//repeat the same for last record
			renderProcessList(finalProcessList, newProcessList, interViewerList, otherUserList);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return finalProcessList;
	}

	/**
	 * @param finalProcessList
	 * @param newProcessList
	 * @param interViewerList
	 * @param otherUserList
	 */
	private void renderProcessList(
			ArrayList<SimpleDataObject> finalProcessList,
			ArrayList<SimpleDataObject> newProcessList,
			ArrayList<SimpleDataObject> interViewerList,
			ArrayList<SimpleDataObject> otherUserList) {
		boolean isInterviewerDM=false;
		//interViewerList do not have DM then
		for (int j = 0; interViewerList !=null && j < interViewerList.size(); j++) {
			SimpleDataObject data = interViewerList.get(j);
			if(data!=null){
				String isDM = data.getString("isAuthorizedToMove");
				if(isDM.equals("1")){
					isInterviewerDM=true;
					break;
				}
			}
		}
		if(!isInterviewerDM && otherUserList!=null){
			newProcessList.addAll(otherUserList);
		}
		
		if(isInterviewerDM) {
			finalProcessList.addAll(interViewerList);
		} else if(newProcessList!=null){
			finalProcessList.addAll(newProcessList);
		}		
	}

	/**
	 * @param newProcessList
	 * @param interViewerList
	 * @param otherUserList
	 * @param userManager
	 * @param sdo
	 * @param userId
	 * @param positionStepIdTo
	 * @param stepIsscheduled
	 * @param isAuthorizedToMove
	 * @param isInterviewer
	 */
	private void checkFilter(ArrayList<SimpleDataObject> newProcessList,
			ArrayList<SimpleDataObject> interViewerList, ArrayList<SimpleDataObject> otherUserList, 
			UserManager userManager, SimpleDataObject sdo) {
		try {
			if(sdo!=null){
				String userId = sdo.getString("userId");
				String positionStepIdTo = sdo.getString("positionStepIdTo");
				String stepIsscheduled = sdo.getString("stepIsscheduled");
				String isAuthorizedToMove = sdo.getString("isAuthorizedToMove");
				String isInterviewer = sdo.getString("isInterviewer");
				
				if(stepIsscheduled.equals(""+PositionConstants.STEP_SCHEDULED) && positionStepIdTo.equals(SelectionProcessConstants.STEP_ATTENDED)){
					if(isInterviewer.equals(""+PositionConstants.RESPONSIBLE_FOR_INTERVIEW)){
						newProcessList.add(sdo);
						interViewerList.add(sdo);
					}else if(isAuthorizedToMove.equals(""+PositionConstants.RESPONSIBLE_FOR_DECISION)){
						if(userManager.getRoleForUser(userId).equals(""+UserConstants.ROLE_RECRUITER) ||
								userManager.getRoleForUser(userId).equals(""+UserConstants.ROLE_HR_MANAGER)){
							newProcessList.add(sdo);
						}else{
							otherUserList.add(sdo);
						}
					}
				}else{
					newProcessList.add(sdo);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		
	}
	
	
	private class PositionApproval implements Callable <Void>{
		private String positionId;
		private String currentStepId;
		private String userId;
		private Date dueDate;
		
		
		public PositionApproval(String userId,String positionId,String currentStepId,Date dueDate) {
			this.userId=userId;
			this.positionId=positionId;
			this.currentStepId=currentStepId;
			this.dueDate=dueDate;
		}
		
		public  Void call() throws Exception {
			TPLogger.getLogger().info("Starting positionApprovalThread thread "+ Thread.currentThread().getName());
			long time = System.nanoTime();
			addToDo(userId, null, positionId, null,null, currentStepId, null, null,null,null,null,null,null,null, null, ToDoConstants.TODO_TYPE_POSITION_APPROVAL, dueDate, null);
			TPLogger.getLogger().info("Ending positionApprovalThread thread "+ Thread.currentThread().getName() +" Time taken : "+ (System.nanoTime()-time));
			return null;
		}
		
	}
	
	
	private void addPositionApprovalToDoRegenerate(ArrayList<SimpleDataObject> approvalList, DBTransaction tran) throws Exception {
		if (approvalList != null) {
			ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for (int i = 0; i < approvalList.size(); i++) {
				try {

					SimpleDataObject sdo = approvalList.get(i);
					String userId = sdo.getString("userId");
					String positionId = sdo.getString("positionId");
					String currentStepId = sdo.getString("currentStepId");
					Date dueDate = sdo.getDate("dateCreated");
					PositionApproval positionApprovalThread = new PositionApproval(userId, positionId, currentStepId, dueDate);
					Future<Void> result = executorService.submit(positionApprovalThread);
					//addToDo(userId, null, positionId, null,null, currentStepId, null, null,null,null,null,null,null,null, null, ToDoConstants.TODO_TYPE_POSITION_APPROVAL, dueDate, tran);
				} catch (Exception e) {
					if (tran != null) {
						throw e;
					}
				}
			}
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);
		}
		
	}
	

	/**
	 * Add todo for all approvals
	 * 
	 * @param approvalList
	 * @param tran
	 * @throws Exception
	 */
	private void addPositionApprovalToDo(ArrayList<SimpleDataObject> approvalList, DBTransaction tran) throws Exception {
		if (approvalList != null) {
			for (int i = 0; i < approvalList.size(); i++) {
				try {

					SimpleDataObject sdo = approvalList.get(i);
					String userId = sdo.getString("userId");
					String positionId = sdo.getString("positionId");
					String currentStepId = sdo.getString("currentStepId");
					Date dueDate = sdo.getDate("dateCreated");
					addToDo(userId, null, positionId, null,null, currentStepId, null, null,null,null,null,null,null,null, null, ToDoConstants.TODO_TYPE_POSITION_APPROVAL, dueDate, tran);
				} catch (Exception e) {
					if (tran != null) {
						throw e;
					}
				}
			}
		}
	}

	/**
	 * @param positionId
	 * @return list of all positions pending for approval else specific position if positionId
	 *         present
	 */
	private ArrayList<SimpleDataObject> getPositionApprovalList(String positionId, DBTransaction tran) {
		ArrayList<SimpleDataObject> result = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(positionId)) {
				dynParams[0] += " AND tp.position_id=? ";
				dynamicContent.add(positionId);
			}
			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_GetApprovalData", dynParams);
			} else {
				dq = new DBPreparedQuery("dToDoManager_GetApprovalData", dynParams, tran);
			}
			int cnt = 1;
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_INPROCESS);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				if (tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}
		}
		return result;

	}

	public ArrayList<SimpleDataObject> getOverduePositionApprovalList(int numberOfDays) {
		ArrayList<SimpleDataObject> result = null;
		DBPreparedQuery dq = null;
		try {			
			dq = new DBPreparedQuery("dToDoManager_GetOverdueApprovalData");
			Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.DATE, -numberOfDays);
			dq.setDate(1, Utils.convertDateToSQLDate(calendar.getTime()));
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {				
				dq.releaseConnection();
			}
		}
		return result;

	}
	
	/**
	 * @param positionId
	 * @return position status
	 */
	private String getPositionStatus(String positionId, DBTransaction tran) {
		DBPreparedQuery dq = null;
		String status = "";
		try {
			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_GetPositionStatus");
			} else {
				dq = new DBPreparedQuery("dToDoManager_GetPositionStatus", tran);
			}
			dq.setString(1, positionId);
			status = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				if (tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}
		}
		return status;

	}

	public void deleteToDoForAppointment(String appointmentId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_DeleteToDoForAppointment");
			} else {
				dq = new DBPreparedQuery("dToDoManager_DeleteToDoForAppointment", tran);
			}
			dq.setString(1, appointmentId);
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}		
	}
	
	
	private class BudgetApproval implements Callable <Void>{
		private String budgetItemId;
		private String userId;
		private Date dueDate;
		
		
		public BudgetApproval(String userId,String budgetItemId,Date dueDate) {
			this.userId=userId;
			this.budgetItemId=budgetItemId;
			this.dueDate=dueDate;
		}
		
		public  Void call() throws Exception {
			TPLogger.getLogger().info("Starting BudgetApprovalThread thread "+ Thread.currentThread().getName());
			long time = System.nanoTime();
			addToDo(userId, null, null, null,budgetItemId, null, null, null,null,null,null,null,null,null, null, ToDoConstants.TODO_TYPE_BUDGET_APPROVAL, dueDate, null);
			TPLogger.getLogger().info("Ending BudgetApprovalThread thread "+ Thread.currentThread().getName() +" Time taken : "+ (System.nanoTime()-time));
			return null;
		}
		
	}
	
	
	private void addBudgetApprovalToDoRegenerate(ArrayList<SimpleDataObject> budgetList, DBTransaction tran) throws Exception {
		if (budgetList != null) {
			ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			for (int i = 0; i < budgetList.size(); i++) {
				try {

					SimpleDataObject sdo = budgetList.get(i);
					String userId = sdo.getString("ownerId");
					String budgetItemId = sdo.getString("budgetItemId");
					Date dueDate = sdo.getDate("dateCreated");
					BudgetApproval budgetApproval = new BudgetApproval(userId, budgetItemId, dueDate);
					Future<Void> result = executorService.submit(budgetApproval);
					//addToDo(userId, null, null, null,budgetItemId, null, null, null,null,null,null,null,null,null, null, ToDoConstants.TODO_TYPE_BUDGET_APPROVAL, dueDate, tran);
				} catch (Exception e) {
					if (tran != null) {
						throw e;
					}
				}
			}
			executorService.shutdown();
			executorService.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);
		}
	}
	/**
	 * Add todo for all budget approvals
	 * 
	 * @param approvalList
	 * @param tran
	 * @throws Exception
	 */
	private void addBudgetApprovalToDo(ArrayList<SimpleDataObject> budgetList, DBTransaction tran) throws Exception {
		if (budgetList != null) {
			for (int i = 0; i < budgetList.size(); i++) {
				try {

					SimpleDataObject sdo = budgetList.get(i);
					String userId = sdo.getString("ownerId");
					String budgetItemId = sdo.getString("budgetItemId");
					Date dueDate = sdo.getDate("dateCreated");
					addToDo(userId, null, null, null,budgetItemId, null, null, null,null,null,null,null,null,null, null, ToDoConstants.TODO_TYPE_BUDGET_APPROVAL, dueDate, tran);
				} catch (Exception e) {
					if (tran != null) {
						throw e;
					}
				}
			}
		}
	}
	
	/**
	 * @param positionId
	 * @return list of all positions pending for approval else specific position if positionId
	 *         present
	 */
	private ArrayList<SimpleDataObject> getBudgetApprovalList(String budgetItemId, DBTransaction tran) {
		ArrayList<SimpleDataObject> result = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(budgetItemId)) {
				dynParams[0] += " AND budget_item_id=? ";
				dynamicContent.add(budgetItemId);
			}
			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_GetBudgetApprovalData", dynParams);
			} else {
				dq = new DBPreparedQuery("dToDoManager_GetBudgetApprovalData", dynParams, tran);
			}
			int cnt = 1;
			dq.setString(cnt++, BudgetConstants.BUDGET_ITEM_STATUS_DRAFT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				if (tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}
		}
		return result;

	}
	
	/**
	 * user can regenerate todo, 1)For position and candidate and todo type selection 2)For single
	 * position and todo type selection 3)For single position and todo type position approval
	 * 
	 * @param positionId
	 * @param applicantId
	 */
	public void regenerateToDoForBudget(String budgetItemId, DBTransaction tran) throws Exception {
		boolean isCommitRequired = false;
		try {
			if (tran == null) {
				tran = new DBTransaction();
				isCommitRequired = true;
			}
			
			deleteBudgetToDo(budgetItemId, tran);
			
			if (!Utils.isBlankOrNull(budgetItemId)) {
				// todo budget specific
				ArrayList<SimpleDataObject> approvalList = getBudgetApprovalList(budgetItemId, tran);
				addBudgetApprovalToDo(approvalList, tran);
			} else {
				// regenerate todo for all budget
				ArrayList<SimpleDataObject> approvalList = getBudgetApprovalList(null, tran);
				addBudgetApprovalToDo(approvalList, tran);
			}
			if (isCommitRequired) {
				tran.commit();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if (isCommitRequired) {
				try {
					tran.rollback();
				} catch (Exception se) {
					TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				}
			}
			throw e;
		} finally {
			if (isCommitRequired) {
				tran.release();
			}
		}
	}
	
	public void deleteBudgetToDo(String budgetItemId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(budgetItemId)) {
				dynParams[0] = addWhereAnd(dynParams[0]);
				dynParams[0] += " budget_item_id = ? ";
				dynamicContent.add(budgetItemId);
			}else{
				dynParams[0] = addWhereAnd(dynParams[0]);
				dynParams[0] += " budget_item_id is NOT NULL ";
			}

			if (tran == null) {
				dq = new DBPreparedQuery("dToDoManager_DeleteToDo", dynParams);
			} else {
				dq = new DBPreparedQuery("dToDoManager_DeleteToDo", dynParams, tran);
			}
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		try {
			System.out.println("in main");
			ToDoManager m = new ToDoManager();
			//m.regenerateAllToDos();
			ArrayList<SimpleDataObject> processList = m.getProcessList("10", null, null);
			System.out.println("processList="+processList.size());
			processList=m.filterProcessList(processList);
			System.out.println("processList="+processList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
