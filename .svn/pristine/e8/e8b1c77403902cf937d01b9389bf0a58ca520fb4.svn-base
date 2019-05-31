/**
 * 
 */
package com.talentPool.costs.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.utils.Utils;
import com.talentPool.costs.dataobject.CostTypeData;
import com.talentPool.costs.form.CostTypeForm;
import com.talentPool.costs.manager.CostTypeManager;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class CostTypeAction extends TPDispatchAction {
	public ActionForward costTypes(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "costTypes";
		CostTypeForm costTypeForm = (CostTypeForm) actionForm;
		String subMode = costTypeForm.getSubMode();

		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_EXPENSES;
		if (subMode!=null && subMode.equals(MastersConstants.SUB_MODE_ADD)) {
			permissions[1] = PermissionConstants.PERMISSION_ADD_EXPENSE_TYPE;
		}else{
			permissions[1] = PermissionConstants.PERMISSION_EXPENSES;
		}
		if(!isUserAuthorized(request, ModuleConstants.MODULE_COSTS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}

		request.setAttribute("t", NavigationConstants.T_COSTS);
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_COSTS_TYPE);
		try {			
			CostTypeManager costTypeManager = new CostTypeManager();
			if (!Utils.isBlankOrNull(subMode)) {
				String costTypeId = costTypeForm.getCostTypeId();
				String costType = costTypeForm.getCostType();

				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "addCostType";
					if (!Utils.isBlankOrNull(costType)) {
						costTypeManager.addCostType(costType);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "addCostType";
					if (Utils.isBlankOrNull(costType)) {
						CostTypeData costTypeData = costTypeManager.getCostTypeData(costTypeId);
						costTypeForm.setCostType(costTypeData.getItemName());
					} else if (!Utils.isBlankOrNull(costTypeId) && !Utils.isBlankOrNull(costType)) {
						costTypeManager.updateCostType(costTypeId, costType);
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Cost type exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("cost_types.error.exists");
			errors.add("cost_types.error.exists", error);
			saveErrors(request, errors);
		}

		return mapping.findForward(forward);
	}

	public ActionForward manageCostTypes(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				CostTypeForm costTypeForm = (CostTypeForm) actionForm;
				String subMode = costTypeForm.getSubMode();
				String costTypeId = costTypeForm.getCostTypeId();
				CostTypeManager costTypeManager = new CostTypeManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList<CostTypeData> costTypes = costTypeManager.getCostTypes();
					xmlFile = costTypeManager.getXMLForCostTypes(costTypes);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(costTypeId)) {
						costTypeManager.deleteCostType(costTypeId);
						xmlFile = Utils.getXMLForIds(costTypeId);
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing cost types", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);

	}
}
