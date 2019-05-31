/**
 * 
 */
package com.talentPool.budget.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.budget.BudgetConstants;
import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.budget.form.BudgetForm;
import com.talentPool.budget.manager.BudgetManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.todo.manager.ToDoManager;
import com.talentPool.user.manager.SessionManager;

/**
 * @author Ajeet
 *
 */
public class BudgetApprovalAction extends TPDispatchAction{

	public ActionForward feedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "feedback";
		try {
			BudgetForm budgetForm = (BudgetForm) actionForm;
			String budgetItemId = budgetForm.getBudgetItemId();
			BudgetManager budgetManager = new BudgetManager();
			BudgetItem budgetItem = budgetManager.getBudgetItemToEdit(budgetItemId);
			if (budgetItem.getStatus().equals(BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE)) {
				// no rights
				forward = "modalError";
				ActionErrors errors = new ActionErrors();
				errors.add("budget_approval_feedback.error.no_permission", new ActionError("budget_approval_feedback.error.no_permission"));
				saveErrors(request, errors);
				request.setAttribute("callback", "1");
				return mapping.findForward(forward);
			} else {
				budgetForm.setBudgetItemName(budgetItem.getBudgetItemName());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward savefeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";
		ActionErrors errors = new ActionErrors();
		try {
			BudgetForm budgetForm = (BudgetForm) actionForm;
			String userId = (String) request.getSession().getAttribute("userId");
			String budgetItemId = budgetForm.getBudgetItemId();
			String budgetStatus = budgetForm.getStatus();
			

			BudgetManager budgetManager = new BudgetManager();
			budgetManager.changeBudgetStatus(budgetItemId, budgetStatus, userId);
			
			//regenerate budget todo
			ToDoManager toDoManager = new ToDoManager();
			toDoManager.regenerateToDoForBudget(budgetItemId, null);
			
		} catch (Exception e) {
			forward = "modalError";
			errors.add("requisition_approval_feedback.error.select_action", new ActionError("requisition_approval_feedback.error.unknown_error"));
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
}
