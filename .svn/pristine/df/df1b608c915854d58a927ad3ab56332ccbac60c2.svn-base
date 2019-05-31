/**
 * 
 */
package com.talentPool.costs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.costs.dataobject.CostData;
import com.talentPool.costs.form.CostForm;
import com.talentPool.costs.manager.CostManager;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class CostAction extends TPDispatchAction {

	public ActionForward costsHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "costsHome";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_EXPENSES;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_COSTS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			request.setAttribute("t", NavigationConstants.T_COSTS);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_COSTS_HOME);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting cost home", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getCosts(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				CostManager costManager = new CostManager();
				ArrayList<CostData> costs = costManager.getCostsForCurrentFinancialYear();
				xmlFile = costManager.getXMLForCosts(costs, permissionSet);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing departments", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);

	}

	private void valiadteCostForm(ActionErrors errors, String cPaidDate, String amt, String costTypeId, String relatedToPosition, String relatedToSource, String relatedToSubscription,
			String positionId, String sourceId, String subDateFrom, String subDateTo) {

		Date costPaidDate = Utils.convertToDate(cPaidDate, Utils.regEUDateFormat);
		if (costPaidDate == null) {
			errors.add("add_cost.error.invalid_payment_date", new ActionError("add_cost.error.invalid_payment_date"));
		}
		try {
			double amount = Double.parseDouble(amt);
			if (amount == 0) {
				errors.add("add_cost.error.invalid_amount", new ActionError("add_cost.error.invalid_amount"));
			}
		} catch (NumberFormatException e) {
			errors.add("add_cost.error.invalid_amount", new ActionError("add_cost.error.invalid_amount"));
		}
		if (costTypeId.equals("0")) {
			errors.add("add_cost.error.please_select_purpose", new ActionError("add_cost.error.please_select_purpose"));
		}

		if (relatedToPosition.equals("1")) {
			if (Utils.isBlankOrNull(positionId)) {
				ActionError error = new ActionError("add_cost.error.please_select_position",TPLabels.getLabel("common.position"));
			       errors.add("add_cost.error.please_select_position", error);
			}
		}
		if (relatedToSource.equals("1")) {
			if (Utils.isBlankOrNull(sourceId)) {
				errors.add("add_cost.error.please_select_source", new ActionError("add_cost.error.please_select_source"));
			}
		}
		if (relatedToSubscription.equals("1")) {
			Date subscriptionDateFrom = Utils.convertToDate(subDateFrom, Utils.regEUDateFormat);
			if (subscriptionDateFrom == null) {
				errors.add("add_cost.error.invalid_from_date", new ActionError("add_cost.error.invalid_from_date"));
			}
			Date subscriptionDateTo = Utils.convertToDate(subDateTo, Utils.regEUDateFormat);
			if (subscriptionDateTo == null) {
				errors.add("add_cost.error.invalid_to_date", new ActionError("add_cost.error.invalid_to_date"));
			}
		}

	}

	public ActionForward addCost(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addCost";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_EXPENSES;
		permissions[1] = PermissionConstants.PERMISSION_ADD_EXPENSE;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_COSTS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

			CostForm costForm = (CostForm) actionForm;
			boolean add = true;
			if (!Utils.isBlankOrNull(costForm.getCostId())) {
				add = false;
			}
			String isSubmit = costForm.getSubmitted();
			// handle errors
			ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
			if (errors == null) {
				errors = new ActionErrors();
			}
			if (!Utils.isBlankOrNull(isSubmit)) {
				String cPaidDate = costForm.getCostPaidDate();
				String amt = costForm.getAmount();
				String costTypeId = costForm.getCostTypeId();
				String relatedToPosition = costForm.getRelatedToPosition();
				String relatedToSource = costForm.getRelatedToSource();
				String relatedToSubscription = costForm.getRelatedToSubscription();
				String positionIds = costForm.getStrPositionIds();
				String sourceId = costForm.getSourceId();
				String subDateFrom = costForm.getSubscriptionDateFrom();
				String subDateTo = costForm.getSubscriptionDateTo();
				String remarks = costForm.getRemarks();

				valiadteCostForm(errors, cPaidDate, amt, costTypeId, relatedToPosition, relatedToSource, relatedToSubscription, positionIds, sourceId, subDateFrom, subDateTo);

				if (errors.size() > 0) {
					request.setAttribute(Globals.ERROR_KEY, errors);
				} else {
					CostManager costManager = new CostManager();
					double amount = Double.parseDouble(amt);
					Date costPaidDate = Utils.convertToDate(cPaidDate + " " + Utils.getCurrentTimeStamp(), Utils.regDDMMYYYYHHMMSSFromat);
					Date subscriptionDateFrom = null;
					Date subscriptionDateTo = null;
					if (relatedToSubscription.equals("1")) {
						subscriptionDateFrom = Utils.convertToDate(subDateFrom, Utils.regEUDateFormat);
						subscriptionDateTo = Utils.convertToDate(subDateTo, Utils.regEUDateFormat);
					}
					if (add) {
						// add
						String costId = costManager.addCost(costPaidDate, amount, costTypeId, positionIds, sourceId, subscriptionDateFrom, subscriptionDateTo, remarks, userId);
						if (costId != null) {
							request.setAttribute("update", "1");
						}
					} else {
						boolean updated = costManager.updateCost(costForm.getCostId(), costPaidDate, amount, costTypeId, positionIds, sourceId, subscriptionDateFrom, subscriptionDateTo, remarks,
								userId);
						if (updated) {
							request.setAttribute("update", "1");
						}
					}
				}
			}

			if (Utils.isBlankOrNull(isSubmit)) {
				if (add) {
					costForm.setRelatedToPosition("0");
					costForm.setRelatedToSource("0");
					costForm.setRelatedToSubscription("0");
					GregorianCalendar cal = new GregorianCalendar();
					costForm.setCostPaidDate(Utils.getDateConvertedToString(cal.getTime(), Utils.regEUDateFormat));
				} else {
					CostManager costManager = new CostManager();
					CostData costData = costManager.getCostData(costForm.getCostId());
					costForm.setCostTypeId(costData.getCostTypeId());
					costForm.setCostPaidDate(Utils.getDateConvertedToString(costData.getCostPaidDate(), Utils.regEUDateFormat));
					costForm.setAmount(costData.getCostAmount());
					costForm.setRelatedToPosition("0");
					if (!Utils.isBlankOrNull(costData.getPositionIds())) {
						costForm.setRelatedToPosition("1");
						costForm.setStrPositionIds(costData.getPositionIds());
					}

					costForm.setRelatedToSource("0");
					if (!Utils.isBlankOrNull(costData.getSourceId())) {
						costForm.setRelatedToSource("1");
						costForm.setSourceId(costData.getSourceId());
					}

					costForm.setRelatedToSubscription("0");
					if (costData.getCostDateFrom() != null) {
						costForm.setRelatedToSubscription("1");
						costForm.setSubscriptionDateFrom(Utils.getDateConvertedToString(costData.getCostDateFrom(), Utils.regEUDateFormat));
						costForm.setSubscriptionDateTo(Utils.getDateConvertedToString(costData.getCostDateTo(), Utils.regEUDateFormat));
					}
					costForm.setRemarks(costData.getCostRemark());
				}
			}

			setPositionsForCost(costForm, userId, permissionSet);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting cost home", e);
			e.printStackTrace();
		}
		return mapping.findForward(forward);
	}

	private void setPositionsForCost(CostForm costForm, String userId, PermissionSet permissionSet) {
		ReportManager reportManager = new ReportManager();
		// here we need all positions which are not deleted, so we passed role_admin
		ArrayList<PositionData> positions = reportManager.getPositions(userId, permissionSet, null);
		ArrayList<String> positionIdsSelected = new ArrayList<String>();
		ArrayList<String> positionNamesSelected = new ArrayList<String>();
		if (!Utils.isBlankOrNull(costForm.getStrPositionIds())) {
			String[] pIds = costForm.getStrPositionIds().split(",");
			for (int i = 0; i < pIds.length; i++) {
				for (int k = 0; positions != null && k < positions.size(); k++) {
					PositionData cData = positions.get(k);
					if (cData.getPositionId().equals(pIds[i])) {
						positionIdsSelected.add(cData.getPositionId());
						positionNamesSelected.add(cData.getPositionTitle());
						positions.remove(k);
						break;
					}
				}
			}
		}
		costForm.setPositionIdsSelected(positionIdsSelected);
		costForm.setPositionNamesSelected(positionNamesSelected);

		ArrayList<String> positionIds = new ArrayList<String>();
		ArrayList<String> positionNames = new ArrayList<String>();
		for (int i = 0; positions != null && i < positions.size(); i++) {
			PositionData cData = positions.get(i);
			positionIds.add(cData.getPositionId());
			positionNames.add(cData.getPositionTitle());
		}
		costForm.setPositionIds(positionIds);
		costForm.setPositionNames(positionNames);
	}

	public ActionForward deleteCost(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				CostForm costForm = (CostForm) actionForm;
				CostManager costManager = new CostManager();
				costManager.deleteCost(costForm.getCostId());
				xmlFile = Utils.getXMLForIds(costForm.getCostId());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting cost", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);

	}

}
