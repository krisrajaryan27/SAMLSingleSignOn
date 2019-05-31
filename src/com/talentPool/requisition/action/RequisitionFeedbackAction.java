/**
 * 
 */
package com.talentPool.requisition.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.audit.action.AuditAction;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.budget.utils.BudgetUtils;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.requisition.constants.RequisitionConstants;
import com.talentPool.requisition.dataobject.RequisitionApprovalStepData;
import com.talentPool.requisition.dataobject.RequisitionFeedbackData;
import com.talentPool.requisition.form.RequisitionFeedbackForm;
import com.talentPool.requisition.manager.RequisitionFeedbackManager;
import com.talentPool.requisition.manager.RequisitionManager;
import com.talentPool.todo.manager.ToDoManager;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class RequisitionFeedbackAction extends TPDispatchAction {

	public ActionForward feedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "feedback";
		try {
			RequisitionFeedbackForm requisitionFeedbackForm = (RequisitionFeedbackForm) actionForm;
			String positionId = requisitionFeedbackForm.getPositionId();
			String userId = (String) request.getSession().getAttribute("userId");
			RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
			RequisitionFeedbackData requisitionFeedbackData = requisitionFeedbackManager.isFeedbackPending(positionId, userId);
			if (requisitionFeedbackData == null) {
				// no rights
				forward = "modalError";
				ActionErrors errors = new ActionErrors();
				errors.add("requisition_approval_feedback.error.no_permission", new ActionError("requisition_approval_feedback.error.no_permission"));
				saveErrors(request, errors);
				request.setAttribute("callback", "1");
				return mapping.findForward(forward);
			} else {
				if (Utils.isBlankOrNull(requisitionFeedbackData.getToUserId())) {
					// this is edit feedback action
					requisitionFeedbackForm.setFeedbackId(requisitionFeedbackData.getFeedbackId());
					return editfeedback(mapping, actionForm, request, response);
				} else {
					// add new feedback
					// get current stepdata
					// get next step data
					setFeedbackFormForAddEdit(request,requisitionFeedbackData,requisitionFeedbackForm);
					
					if(ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive() && Utils.isBlankOrNull(requisitionFeedbackData.getBudgetItemId())){
						request.setAttribute("errors", true);
						ActionErrors errors = new ActionErrors();
						errors.add("budget.position_approval.error.no_budget_item",new ActionError("budget.position_approval.error.no_budget_item",TPLabels.getLabel("common.position"),TPLabels.getLabel("common.position")));
						saveErrors(request, errors);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	private void setFeedbackFormForAddEdit(HttpServletRequest request,RequisitionFeedbackData requisitionFeedbackData,RequisitionFeedbackForm requisitionFeedbackForm){
		String currentStepId = Utils.isBlankOrNull(requisitionFeedbackData.getToStepId()) ? requisitionFeedbackData.getFromStepId() : requisitionFeedbackData.getToStepId();		
		setFeedbackFormForAddEdit(request, requisitionFeedbackForm, requisitionFeedbackData.getPositionId(), currentStepId, requisitionFeedbackData.getBudgetItemName());
	}
	
	private void setFeedbackFormForAddEdit(HttpServletRequest request, RequisitionFeedbackForm requisitionFeedbackForm, String positionId, String currentStepId, String budgetItemName) {
		RequisitionManager requisitionManager = new RequisitionManager();
		try{
			RequisitionApprovalStepData currentStepData = requisitionManager.getRequisitionApprovalStepData(currentStepId,null);
			// int rank = currentStepData.getRequisitionApprovalStepRank() + 1;
			RequisitionApprovalStepData nextStepData = requisitionManager.getNextActiveRequisitionStepData(currentStepId, currentStepData.getRequisitionApprovalStepRank(), ""+currentStepData.getRequisitionApprovalTemplateId());
			if (nextStepData != null) {
				requisitionFeedbackForm.setToApprovalStepId("" + nextStepData.getRequisitionApprovalStepId());
				//setUserIdsNames(request, nextStepData);
				requisitionFeedbackForm.setNextStepUsersJsArray(getUserJsArray(nextStepData));
			}
			requisitionFeedbackForm.setFromApprovalStepId(currentStepId);
			requisitionFeedbackForm.setFromStepTitle(currentStepData.getRequisitionApprovalStepName());
			PositionManager positionManager = new PositionManager();
			String positionTitle = positionManager.getPositionTitle(positionId);
			requisitionFeedbackForm.setPositionTitle(positionTitle);
			requisitionFeedbackForm.setBudgetItemName(budgetItemName);
			
			if (Utils.isBlankOrNull(requisitionFeedbackForm.getFeedbackDecision())) {
				requisitionFeedbackForm.setFeedbackDecision(RequisitionConstants.FEEDBACK_ACTION_APPROVE);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	public ActionForward editfeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "feedback";
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		try {
			if (errors == null) {
				errors = new ActionErrors();
			}
			RequisitionFeedbackForm requisitionFeedbackForm = (RequisitionFeedbackForm) actionForm;
			String feedbackId = requisitionFeedbackForm.getFeedbackId();
			RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
			RequisitionFeedbackData requisitionFeedbackData = requisitionFeedbackManager.getRequisitionFeedbackData(feedbackId);
			if (requisitionFeedbackData != null) {
				requisitionFeedbackForm.setPositionId(requisitionFeedbackData.getPositionId());
				requisitionFeedbackForm.setFeedbackDecision(requisitionFeedbackData.getFeedbackDecision());
				requisitionFeedbackForm.setFromApprovalStepId(requisitionFeedbackData.getFromStepId());
				requisitionFeedbackForm.setToApprovalStepId(requisitionFeedbackData.getToStepId());
				requisitionFeedbackForm.setNextUserId(requisitionFeedbackData.getToUserId());
				requisitionFeedbackForm.setFeedbackComment(requisitionFeedbackData.getFeedbackComment());
				setFeedbackFormForAddEdit(request, requisitionFeedbackForm, requisitionFeedbackData.getPositionId(), requisitionFeedbackData.getFromStepId(),requisitionFeedbackData.getBudgetItemName());
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error While edit fb", e);
		}
		return mapping.findForward(forward);
	}

	private String getUserJsArray(RequisitionApprovalStepData stepData) {
		if (stepData != null) {
			ArrayList<LoginData> users = stepData.getUsers();
			if (users != null && users.size() > 0) {
				ArrayList<String> userIds = new ArrayList<String>();
				ArrayList<String> userNames = new ArrayList<String>();
				for (int i = 0; i < users.size(); i++) {
					LoginData loginData = users.get(i);
					userIds.add(loginData.getUserId());
					userNames.add(loginData.getName());
				}
				return CommonUtils.getListJavaScriptArray(userIds, userNames);
			}
		}
		return null;
	}

	public ActionForward viewfeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewFeedback";
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		try {
			if (errors == null) {
				errors = new ActionErrors();
			}
			boolean editAllowed = false;
			String userId = (String) request.getSession().getAttribute("userId");
			RequisitionFeedbackForm requisitionFeedbackForm = (RequisitionFeedbackForm) actionForm;
			String feedbackId = requisitionFeedbackForm.getFeedbackId();
			RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
			RequisitionFeedbackData requisitionFeedbackData = requisitionFeedbackManager.getFeedbackDataForDisplay(feedbackId);
			if (requisitionFeedbackData != null) {
				requisitionFeedbackForm.setPositionId(requisitionFeedbackData.getPositionId());
				requisitionFeedbackForm.setFeedbackId(requisitionFeedbackData.getFeedbackId());
				RequisitionFeedbackData lastRequisitionFeedbackData = requisitionFeedbackManager.getLastRequisitionFeedbackDataForPosition(requisitionFeedbackData.getPositionId());
				if (lastRequisitionFeedbackData != null) {
					if (lastRequisitionFeedbackData.getFeedbackId().equals(feedbackId) && lastRequisitionFeedbackData.getByUserId().equals(userId)) {
						PositionManager positionManager = new PositionManager();
						int noOfCandidates = positionManager.getNoOfCandidatesProcessed(requisitionFeedbackData.getPositionId());
						if (noOfCandidates == 0) {
							editAllowed = true;
						}
					}
				}
			}
			request.setAttribute("requisitionFeedbackData", requisitionFeedbackData);
			request.setAttribute("editAllowed", editAllowed);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While view fb", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward savefeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";
		ActionErrors errors = new ActionErrors();
		AuditAction auditAction = new AuditAction();
		try {
			RequisitionFeedbackForm requisitionFeedbackForm = (RequisitionFeedbackForm) actionForm;
			String userId = (String) request.getSession().getAttribute("userId");
			String positionId = requisitionFeedbackForm.getPositionId();
			String feedbackDecision = requisitionFeedbackForm.getFeedbackDecision();
			String fromStepId = requisitionFeedbackForm.getFromApprovalStepId();
			String toStepId = requisitionFeedbackForm.getToApprovalStepId();
			String toUserId = requisitionFeedbackForm.getNextUserId();
			String comment = requisitionFeedbackForm.getFeedbackComment();
			String feedbackId = requisitionFeedbackForm.getFeedbackId();			
			
			errors = validateApproval(userId, feedbackDecision, toUserId);
			if (errors.size() == 0) {
				RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
				if (Utils.isBlankOrNull(feedbackId)) {
					// add feed back
					requisitionFeedbackManager.addFeedback(positionId, fromStepId, toStepId, userId, toUserId, feedbackDecision, comment, requisitionFeedbackForm.getNotifyUserIds());
				} else {
					requisitionFeedbackManager.updateFeedback( feedbackId, positionId, fromStepId, toStepId, userId, toUserId, feedbackDecision, comment, requisitionFeedbackForm.getNotifyUserIds());					
				}
				String clientIpAddr = getClientIpAddr(request);
				auditAction.insertAuditInfo(TPLabels.getLabel("common.requisition"), getFeedbackAuditMessage(feedbackDecision), 
						positionId, AuditConstants.AUDIT_REQUISITION, userId, null, null, null, true, clientIpAddr);

				if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE) && Utils.isBlankOrNull(toUserId)) {
					forward = "openNotification";
				}

			} else {
				if (Utils.isBlankOrNull(requisitionFeedbackForm.getFeedbackId())) {
					return feedback(mapping, actionForm, request, response);
				} else {
					return editfeedback(mapping, actionForm, request, response);
				}

			}
			//regenerate todo
			ToDoManager toDoManager = new ToDoManager();
			toDoManager.regenerateToDo(positionId, null, null);
			
		} catch (Exception e) {
			forward = "modalError";
			errors.add("requisition_approval_feedback.error.select_action", new ActionError("requisition_approval_feedback.error.select_action"));
			TPLogger.getLogger().error("Error While edit fb", e);
		}
		return mapping.findForward(forward);
	}
	
	private String getFeedbackAuditMessage(String feedbackDecision){
		String message = "";
		if(feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_REJECT)){
			message = AuditConstants.TYPE_REJECTED;
		}else if(feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)){
			message = AuditConstants.TYPE_APPROVED;
		}else if(feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_HOLD)){
			message = AuditConstants.TYPE_ONHOLD;
		}
		return message;
	}
	private ActionErrors validateApproval(String userId, String approvalDecision, String nextUserId) {
		ActionErrors errors = new ActionErrors();
		if (Utils.isBlankOrNull(approvalDecision)) {
			errors.add("requisition_approval_feedback.error.select_action", new ActionError("requisition_approval_feedback.error.select_action"));
		} else if (approvalDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)) {
			if (nextUserId.equals("-1")) {
				errors.add("requisition_approval_feedback.error.select_user", new ActionError("requisition_approval_feedback.error.select_user"));
			}
		}
		return errors;
	}
	
	public ActionForward bulkRequisitionApproval(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "bulkRequisitionApproval";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		RequisitionFeedbackForm requisitionFeedbackForm = (RequisitionFeedbackForm) actionForm;
		String userId = (String) request.getSession().getAttribute("userId");
		List<RequisitionFeedbackForm> requisitionFeedbackFormList = null; 
		try {
			String[] positionIds = Utils.getBlankIfNull(requisitionFeedbackForm.getPositionId()).split(CommonConstants.DEFAULT_DELIMITER);
			requisitionFeedbackFormList = getRequisitionFeedbackFormList(request,positionIds, userId);
			if(requisitionFeedbackFormList!=null&&requisitionFeedbackFormList.size()==0){
				ActionErrors errors = new ActionErrors();
				errors.add("requisition_approval_feedback.error.position_already_processed", new ActionError("requisition_approval_feedback.error.position_already_processed"));
				saveErrors(request, errors);
			}
			request.setAttribute("requisitionFeedbackFormList", requisitionFeedbackFormList);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	private List<RequisitionFeedbackForm> getRequisitionFeedbackFormList(HttpServletRequest request,String[] positionIds,String userId){
		RequisitionFeedbackData requisitionFeedbackData = null;
		RequisitionFeedbackForm requisitionFeedbackForm = null;
		RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
		List<RequisitionFeedbackForm> requisitionFeedbackFormList = new ArrayList<RequisitionFeedbackForm>();
		for(String positionId:positionIds){
			requisitionFeedbackData = requisitionFeedbackManager.isFeedbackPending(positionId, userId);
			if(requisitionFeedbackData!=null){
				requisitionFeedbackForm = new RequisitionFeedbackForm();
				requisitionFeedbackForm.setPositionId(positionId);
				if(!Utils.isBlankOrNull(requisitionFeedbackData.getFeedbackDecision())){
					requisitionFeedbackForm.setFeedbackDecision(requisitionFeedbackData.getFeedbackDecision());
				}
				
				if (Utils.isBlankOrNull(requisitionFeedbackData.getToUserId())) {
					// this is edit feedback action
					requisitionFeedbackForm.setFeedbackId(requisitionFeedbackData.getFeedbackId());
					requisitionFeedbackForm.setFeedbackComment(requisitionFeedbackData.getFeedbackComment());
				}
				setFeedbackFormForAddEdit(request, requisitionFeedbackData, requisitionFeedbackForm);
				requisitionFeedbackFormList.add(requisitionFeedbackForm);
			}
		}
		return requisitionFeedbackFormList;
	}
	
	public ActionForward saveBulkRequisitionfeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";
		ActionErrors errors = null;
		ActionErrors bulkErrors = new ActionErrors();
		AuditAction auditAction = new AuditAction();
		RequisitionFeedbackManager requisitionFeedbackManager = new RequisitionFeedbackManager();
		try {
			RequisitionFeedbackForm requisitionFeedbackForm = (RequisitionFeedbackForm) actionForm;
			String userId = (String) request.getSession().getAttribute("userId");
			String[] positionIds = Utils.getBlankIfNull(requisitionFeedbackForm.getPositionId()).split(",");
			String feedbackId		= null;
			String feedbackDecision = null;
			String fromStepId 		= null;
			String toStepId 		= null;
			String toUserId 		= null;
			String comment 			= null;
			

			for(String positionId:positionIds){
				feedbackId			= request.getParameter("feedbackId_"+positionId);
				feedbackDecision 	= request.getParameter("feedbackDecision_"+positionId);
				fromStepId 			= request.getParameter("fromApprovalStepId_"+positionId);
				toStepId 			= request.getParameter("toApprovalStepId_"+positionId);
				toUserId 			= request.getParameter("nextUserId_"+positionId);
				comment 			= request.getParameter("comment_"+positionId);
				
				errors = validateApproval(userId, feedbackDecision, toUserId);
				
				String clientIpAddr = getClientIpAddr(request);
				if (errors.size() == 0) {
					if (Utils.isBlankOrNull(feedbackId)) {						// add feed back
						requisitionFeedbackManager.addFeedback(positionId, fromStepId, toStepId, userId, toUserId, feedbackDecision, comment, toUserId);
					} else {
						requisitionFeedbackManager.updateFeedback( feedbackId, positionId, fromStepId, toStepId, userId, toUserId, feedbackDecision, comment, toUserId);						
					}
					auditAction.insertAuditInfo(TPLabels.getLabel("common.requisition"), getFeedbackAuditMessage(feedbackDecision),
							positionId, AuditConstants.AUDIT_REQUISITION, userId, null, null, null, true, clientIpAddr);
				}else{
					bulkErrors.add(errors);
				} 
				ToDoManager toDoManager = new ToDoManager();
				toDoManager.regenerateToDo(positionId, null, null);
			}
			if(bulkErrors.size()>0){
				request.setAttribute("errors", bulkErrors);
				return bulkRequisitionApproval(mapping, actionForm, request, response);
			}
		} catch (Exception e) {
			forward = "modalError";
			bulkErrors.add("requisition_approval_feedback.error.select_action", new ActionError("requisition_approval_feedback.error.select_action"));
			TPLogger.getLogger().error("Error While edit fb", e);
		}
		return mapping.findForward(forward);
	}
	
	
}
