package com.talentPool.budget.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.budget.BudgetConstants;
import com.talentPool.budget.dataobject.BudgetFilterData;
import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.budget.form.BudgetForm;
import com.talentPool.budget.manager.BudgetManager;
import com.talentPool.budget.utils.BudgetUtils;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xsl.XSLTransformer;
import com.talentPool.department.manager.DepartmentManager;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

public class BudgetAction extends TPDispatchAction{

	public ActionForward budgetHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "budgetHome";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_BUDGETS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_BUDGET, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		BudgetForm form = (BudgetForm) actionForm;
		try {
			String userId = (String) request.getSession().getAttribute("userId");			
			request.setAttribute("t", NavigationConstants.T_BUDGETS);

			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in budget home", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageBudgetItem(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forward = "manageBudgetItem";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		BudgetForm form = (BudgetForm) actionForm;
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_BUDGETS;		
		String subMode = form.getSubMode();
		if (!Utils.isBlankOrNull(subMode)) {			
			if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
				permissions[1] = PermissionConstants.PERMISSION_BUDGET_EDIT;
				if(!isUserAuthorized(request, ModuleConstants.MODULE_BUDGET, permissions,null,null,null)) {
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
				}
				BudgetManager budgetManager = new BudgetManager();
				String userId = (String) request.getSession().getAttribute("userId");		
				if(!budgetManager.canModifyBudgetItem(form.getBudgetItemId(), userId)){
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
				}else{				
					BudgetItem budgetItem = budgetManager.getBudgetItemToEdit(form.getBudgetItemId());
					BudgetUtils.populateBudgetItemOnForm(budgetItem, form);
				}
			}else{		
				permissions[1] = PermissionConstants.PERMISSION_BUDGET_CREATE;
				if(!isUserAuthorized(request, ModuleConstants.MODULE_BUDGET, permissions,null,null,null)) {
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
				}
			}
			DepartmentManager departmentManager = new DepartmentManager();
			form.setJsArrayOwners(getJSArrayOwners());
			form.setJsArrayDepartments(departmentManager.getJSArrayDepartments());
			form.setJsArrayGrades(CommonUtils.getJSArrayGrades());
			form.setJsArrayBands(CommonUtils.getJSArrayBands());		
		}	
		request.setAttribute("t", NavigationConstants.T_BUDGETS);
		return mapping.findForward(forward);
	}
	
	private ActionErrors validateBudgetItem(BudgetForm form) {		
		ActionErrors errors = new ActionErrors();		
		if (Utils.isBlankOrNull(form.getStartTime())) {
			ActionError error = new ActionError("budget.manage.error.start_time");
			errors.add("budget.manage.error.start_time", error);
		}
		if (Utils.isBlankOrNull(form.getEndTime())) {
			ActionError error = new ActionError("budget.manage.error.end_time");
			errors.add("budget.manage.error.end_time", error);
		}
		if(errors.size()==0) {
			Date startDate = Utils.convertToDate(form.getStartTime(), Utils.regEUDateFormat);
			Date endDate = Utils.convertToDate(form.getEndTime(), Utils.regEUDateFormat);
			if (startDate.after(endDate)) {
				ActionError error = new ActionError("budget.manage.error.date_range");
				errors.add("budget.manage.error.date_range", error);
			}			
		}	
		if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_MODE).equals(BudgetConstants.BUDGET_MODE_ENFORCE)){
			if(!Utils.isBlankOrNull(form.getBudgetItemId())){
				BudgetFilterData budgetFilterData = new BudgetFilterData();
				budgetFilterData.setBudgetItemId(form.getBudgetItemId());
				BudgetManager budgetManager = new BudgetManager();
				Map restrictions = budgetManager.getForcedModeRestrictionsForBudgetItem(budgetFilterData);
				int minUpdateHeadCount = (Integer) restrictions.get(BudgetConstants.FORCED_MODE_RESTRICTION_MIN_UPDATE_HEAD_COUNT);
				if(Integer.parseInt(form.getAvailableHeadCount())< minUpdateHeadCount){
					ActionError error = new ActionError("budget.manage.error.min_update_head_count",minUpdateHeadCount);
					errors.add("budget.manage.error.min_update_head_count", error);
				}
			}
		}
		if(!Utils.isValidPattern(form.getAvailableHeadCount(), Utils.regNumberOnly)) {			
			ActionError error = new ActionError("budget.manage.error.head_count");
			errors.add("budget.manage.error.head_count", error);
		}	
		
		return errors;
	}
	
	public ActionForward saveBudgetItem(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forward = "manageBudgetItem";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_BUDGETS;			
		if(!isUserAuthorized(request, ModuleConstants.MODULE_BUDGET, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ActionErrors errors = null;
		BudgetForm form = null;
		try{
			form = (BudgetForm) actionForm;
			String userId = (String) request.getSession().getAttribute("userId");
			
			errors = validateBudgetItem(form);
			if (errors == null || errors.size() == 0) {
				BudgetItem budgetItem = new BudgetItem();
				BudgetUtils.populateFormOnBudgetItem(form, budgetItem);
				
				BudgetManager budgetManager = new BudgetManager();
				
				String budgetItemId = form.getBudgetItemId();
				if (Utils.isBlankOrNull(budgetItemId)) {
					budgetItem.setStatus(BudgetConstants.BUDGET_ITEM_STATUS_DRAFT);
				}		
				budgetManager.saveOrUpdateBudgetItem(budgetItem,userId);
				request.setAttribute("update", "1");
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(e);
			String errorMessage = e.getMessage();
			if(Utils.isBlankOrNull(errorMessage)){
				errorMessage = "budget.manage.error.save_budget_item";
			}
			ActionError error = new ActionError(errorMessage);
			if (errors == null) {
				errors = new ActionErrors();
			}
			errors.add(errorMessage, error);
		}
		if (errors != null && errors.size() > 0) {			
			saveErrors(request, errors);
			DepartmentManager departmentManager = new DepartmentManager();
			form.setJsArrayOwners(getJSArrayOwners());
			form.setJsArrayDepartments(departmentManager.getJSArrayDepartments());
			form.setJsArrayGrades(CommonUtils.getJSArrayGrades());
			form.setJsArrayBands(CommonUtils.getJSArrayBands());
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward searchBudgetItems(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forward = "xmlFile";
		String xmlFile = null;
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			}else{
				BudgetForm form = (BudgetForm) actionForm;
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				
				BudgetFilterData budgetFilterData = new BudgetFilterData();
				BudgetUtils.populateFormOnBudgetFilterData(form, budgetFilterData);
				
				BudgetManager budgetManager = new BudgetManager();
				ArrayList<BudgetItem> budgetItems = budgetManager.searchBudgetItems(budgetFilterData);
				
				xmlFile = BudgetUtils.getXMLforPositionsHome(budgetItems, userId, permissionSet);				
				request.setAttribute("budgetItems", budgetItems);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting XML:", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
				
	}
	
	public ActionForward transferBudget(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forward = "transferBudget";		
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		ActionErrors errors = new ActionErrors();
		BudgetForm form = (BudgetForm) actionForm;
		try {			
			Integer[] permissions = new Integer[2];
			permissions[0] = PermissionConstants.PERMISSION_BUDGETS;
			permissions[1] = PermissionConstants.PERMISSION_BUDGET_EDIT;
			if(!isUserAuthorized(request, ModuleConstants.MODULE_BUDGET, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
			String userId = (String) request.getSession(false).getAttribute("userId");
			String destBudgetItemId = form.getDestBudgetItemId();			
			BudgetManager budgetManager = new BudgetManager();
			
			if(!Utils.isBlankOrNull(destBudgetItemId)){	
				
				if (destBudgetItemId.equals("-1")) {
					ActionError error = new ActionError("budget.transfer.error.trasfer_to_budget_item");
					errors.add("budget.transfer.error.trasfer_to_budget_item", error);
				}
				if (Utils.isBlankOrNull(form.getTransferHeadCount())) {
					ActionError error = new ActionError("budget.transfer.error.number_of_heads");
					errors.add("budget.transfer.error.number_of_heads", error);
				}
				else if (!Utils.isValidPattern(form.getTransferHeadCount(), Utils.regNumberOnly)) {
					ActionError error = new ActionError("budget.transfer.error.number_of_heads.invalid");
					errors.add("budget.transfer.error.number_of_heads.invalid", error);
				}else if(!budgetManager.canModifyBudgetItem(destBudgetItemId, userId)){
					ActionError error = new ActionError("budget.manage.error.no_edit_permission");
					errors.add("budget.manage.error.no_edit_permission", error);
				}
				else{
					
					BudgetFilterData budgetFilterData = new BudgetFilterData();
					budgetFilterData.setBudgetItemId(form.getBudgetItemId());
					Map restrictions = budgetManager.getForcedModeRestrictionsForBudgetItem(budgetFilterData);
					int availableHeadCount = (Integer) restrictions.get(BudgetConstants.FORCED_MODE_RESTRICTION_AVAILABLE_COUNT);
					
					if(Integer.parseInt(form.getTransferHeadCount()) > availableHeadCount){						
						ActionError error = new ActionError("budget.transfer.error.number_of_heads.not_available",form.getBudgetItemName(),availableHeadCount);
						errors.add("budget.transfer.error.number_of_heads.not_available", error);
						form.setAvailableHeadCount(availableHeadCount+"");
					}
				}
				
				if (errors != null && errors.size() > 0) {
					form.setJsArrayBudgetItems(getJSArrayBudgetItems(form.getBudgetItemId()));	
					saveErrors(request, errors);
				} else {
					budgetManager.transferHeadCountFromBudgetItem(form.getBudgetItemId(), destBudgetItemId, Integer.parseInt(form.getTransferHeadCount()),userId);				
					
					request.setAttribute("update", "1");
				}				
			}
			else{				
				form.setJsArrayBudgetItems(getJSArrayBudgetItems(form.getBudgetItemId()));		
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionError error = new ActionError("budget.transfer.error.unknown");
			errors.add("budget.transfer.error.unknown", error);
			saveErrors(request, errors);
			form.setJsArrayBudgetItems(getJSArrayBudgetItems(form.getBudgetItemId()));		
		}

		return mapping.findForward(forward);
				
	}
	
	public ActionForward deleteBudget(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forward = "xmlFile";
		
		String xmlFile = "";
		ArrayList<String> errors = new ArrayList<String>();
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_BUDGETS;
		permissions[1] = PermissionConstants.PERMISSION_BUDGET_DELETE;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_BUDGET, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				BudgetForm form = (BudgetForm) actionForm;
				String userId = (String) request.getSession(false).getAttribute("userId");
				String budgetItemId = form.getBudgetItemId();
				String confirmDelete = form.getConfirmDelete();
				BudgetManager budgetManager = new BudgetManager();
				if(!Utils.isBlankOrNull(budgetItemId)){
					List<SimpleDataObject> positions = budgetManager.getListOfAssociatedPositionsWithBudgetItem(budgetItemId);
					if(positions!= null && !positions.isEmpty()){
						StringBuffer error = new StringBuffer(TPLabels.getLabel("budget.delete.error.associated_positions"));						
						for (SimpleDataObject object : positions) {
							error.append("\n - "+object.getAttribute("positionTitle"));
						}
						errors.add(error.toString());
						xmlFile = Utils.getXMLForError(errors);
					}else if(Utils.isBlankOrNull(confirmDelete) || !confirmDelete.equals("true")){
						BudgetFilterData budgetFilterData = new BudgetFilterData();
						budgetFilterData.setBudgetItemId(form.getBudgetItemId());
						Map restrictions = budgetManager.getForcedModeRestrictionsForBudgetItem(budgetFilterData);
						int availableHeadCount = (Integer) restrictions.get(BudgetConstants.FORCED_MODE_RESTRICTION_AVAILABLE_COUNT);
						errors.add("availableHeadCount_"+availableHeadCount);
						xmlFile = Utils.getXMLForError(errors);
					}
					else{
						try{
							budgetManager.changeBudgetStatus(budgetItemId, BudgetConstants.BUDGET_ITEM_STATUS_DELETED, userId);
							xmlFile = Utils.getXMLForIds(budgetItemId);
						}catch(Exception e){
							errors.add(e.getMessage());
							xmlFile = Utils.getXMLForError(errors);
						}
					}
				}				
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}

		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
				
	}
	
	public ActionForward canModifyBudgetItem(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String forward = "xmlFile";
		String xmlFile = "";
		ArrayList<String> errors = new ArrayList<String>();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				BudgetForm form = (BudgetForm) actionForm;
				String userId = (String) request.getSession(false).getAttribute("userId");
				String budgetItemId = form.getBudgetItemId();
				BudgetManager budgetManager = new BudgetManager();
				if(!Utils.isBlankOrNull(budgetItemId)){					
					if(budgetManager.canModifyBudgetItem(budgetItemId, userId)){
						xmlFile = Utils.getXMLForIds(budgetItemId);
					}else{
						xmlFile = Utils.getXMLForError(null);
					}			
				}				
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			errors.add(GlobalConstants.ERROR);
			xmlFile = Utils.getXMLForError(errors);
		}

		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
				
	}
	
	public ActionForward getHTMLForFilter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "Link";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
				forward = "xmlFile";
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				BudgetForm form = (BudgetForm) actionForm;
				
				BudgetManager budgetManager = new BudgetManager();
				ArrayList<SimpleDataObject> filters = null;				
				BudgetFilterData budgetFilterData = new BudgetFilterData();
				BudgetUtils.populateFormOnBudgetFilterData(form, budgetFilterData);
				filters = budgetManager.getBudgetFilters(permissionSet, userId, form.getFilterFor(), budgetFilterData);
				
				String xml = BudgetUtils.getXMLForFilter(filters, form.getFilterFor(), budgetFilterData);
				String selectedLink = BudgetUtils.getSelectedLink(filters, form.getFilterFor(),budgetFilterData);
				xmlFile = XSLTransformer.getTransformedXMLusingXSL(xml, XSLTransformer.XSL_SELECT_FILTER);
				xmlFile = form.getFilterFor() + "|" + selectedLink + "|" + xmlFile;
				TPLogger.getLogger().debug(xmlFile);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	private String getJSArrayOwners(){
		BudgetManager budgetManager= new BudgetManager();
		ArrayList users = budgetManager.getUsersForPermissions(PermissionConstants.CAN_OWN_BUDGET_ITEM+"");
		String jsArrayOwners = CommonUtils.getListJavaScriptArrayWithProperties(users,"userId","name");
		return jsArrayOwners;
	}
	
	private String getJSArrayBudgetItems(String budgetItemId) {
		BudgetManager budgetManager = new BudgetManager();
		BudgetFilterData budgetFilterData = new BudgetFilterData();
		budgetFilterData.setStatus(BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE);
		List<BudgetItem> budgetItems = budgetManager.searchBudgetItems(budgetFilterData);
		if(!Utils.isBlankOrNull(budgetItemId)){
			for (BudgetItem budgetItem : budgetItems) {
				if(budgetItemId.equals(budgetItem.getBudgetItemId())){
					budgetItems.remove(budgetItem);
					break;
				}
			}
		}
		return CommonUtils.getListJavaScriptArrayWithProperties((ArrayList<BudgetItem>) budgetItems, "budgetItemId", "budgetItemName");
		
	}
}
