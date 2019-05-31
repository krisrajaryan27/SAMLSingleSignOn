package com.talentPool.selectionProcessFilters.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xsl.XSLTransformer;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.utils.PositionUtils;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.form.SelectionProcessForm;
import com.talentPool.selectionProcess.manager.FilterManager;
import com.talentPool.selectionProcess.utils.SelectionProcessUtils;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

public class SelectionProcessFiltersAction extends TPDispatchAction {
	
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
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String selectedView = request.getParameter("selectedView");
				FilterManager filterManager = new FilterManager();
				List<SimpleDataObject> filters = filterManager.getFilters(permissionSet, userId, selectionProcessForm.getFilterFor(), selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getStepName(), selectionProcessForm.getApplicantName(), selectionProcessForm.getStepLevel(), selectionProcessForm.getStepId(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getActionRequired(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId(),selectedView);				
				String xml = SelectionProcessUtils.getXMLForFilter(filters, selectionProcessForm.getFilterFor(),selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getStepName(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getActionRequired(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId());				
				String selectedLink = getSelectedLink(filters, selectionProcessForm.getFilterFor(), selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getStepName(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getActionRequired(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId());
				xmlFile = XSLTransformer.getTransformedXMLusingXSL(xml, XSLTransformer.XSL_SELECT_FILTER);
				xmlFile = selectionProcessForm.getFilterFor() + "|" + selectedLink + "|" + xmlFile;
			}

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public String getSelectedLink(List<SimpleDataObject> filters, String filterFor, String departmentId, 
			String positionId, String stepName, String locationTitle, String positionTypeExtInt, String actionRequired, 
			String selectedUserId, String sourceTypeId) {
		String selLink = "";
		try {
			String selectedId = "";
			if (filterFor.equals(SelectionProcessConstants.FILTER_DEPARTMENT)) {
				selectedId = departmentId;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION)) {
				selectedId = positionId;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_STEP)) {
				selectedId = stepName;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_LOCATION)) {
				selectedId = locationTitle;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION_TYPE)) {
				selectedId = positionTypeExtInt;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_ACTION)) {
				selectedId = actionRequired;
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_USER)) {
				selectedId = selectedUserId;
			}else if (filterFor.equals(SelectionProcessConstants.FILTER_SOURCE)) {
				selectedId = sourceTypeId;
			}

			for (int i = 0; filters != null && i < filters.size(); i++) {
				SimpleDataObject sdo = filters.get(i);
				String filterId = sdo.getString("filterId");
				String filterFullName = sdo.getString("filterFullName");
				String filterShortName = sdo.getString("filterShortName");
				
				if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION_TYPE)){
					if(filterId.equals(PositionConstants.POSITIONS_TYPE_INTERNAL)){
						filterFullName = TPLabels.getLabel("position.description.position_type_internal");
						filterShortName = TPLabels.getLabel("position.description.position_type_internal");
					}else if(filterId.equals(PositionConstants.POSITIONS_TYPE_EXTERNAL)){
						filterFullName = TPLabels.getLabel("position.description.position_type_external");
						filterShortName= TPLabels.getLabel("position.description.position_type_external");						
					}else{
						filterFullName = TPLabels.getLabel("position.description.position_type_not_set");
						filterShortName= TPLabels.getLabel("position.description.position_type_not_set");
					}
				}
				
				if (filterId.equals(selectedId)) {
					if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION) &&
							GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
						selLink = filterShortName;
					} else {
						selLink = filterFullName;
					}					
					break;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return selLink;
	}
	
	public ActionForward populateApplicantGridFilters(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "Link";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
				forward = "xmlFile";
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String selectedView = request.getParameter("selectedView");
				FilterManager filterManager = new FilterManager();
				ArrayList<SimpleDataObject> filters = filterManager.getFilters(permissionSet, userId, selectionProcessForm.getFilterFor(), selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getStepName(), selectionProcessForm.getApplicantName(), selectionProcessForm.getStepLevel(), selectionProcessForm.getStepId(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getActionRequired(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId(),selectedView);				
				xmlFile = CommonUtils.getListJavaScriptArrayWithProperties(filters, "filterId","filterFullName");				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward populateRejectedApplicantsGridFilters(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "Link";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
				forward = "xmlFile";
			} else {
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				if (permissionSet.isPERMISSION_VIEW_REJECTED_CANDIDATES()) {
					SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
					String rejectedApplicantName = request.getParameter("rejectedApplicantName");
					String stepName = request.getParameter("stepName");
					String rejectedBy = request.getParameter("rejectedBy");
					FilterManager filterManager = new FilterManager();
					ArrayList<SimpleDataObject> filters = filterManager.getRejectedApplicantsGridFilters(selectionProcessForm.getFilterFor(),selectionProcessForm.getPositionId(),stepName, rejectedApplicantName, rejectedBy);
					xmlFile = CommonUtils.getListJavaScriptArrayWithProperties(filters, "filterId", "filterFullName");
				} else {
					xmlFile = "";
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		} 
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward stageFilterScreen(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "stageFilterScreen";
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		String stageFilterOptions = PositionUtils.getJSArrayForSelectionStageOnPostionSummary();
		request.setAttribute("JSSelectionStageArray", stageFilterOptions);
		request.setAttribute("stepLevel", selectionProcessForm.getStepLevel());
		return mapping.findForward(forward);
	}
	
	public ActionForward userFilterScreen(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "userFilterScreen";
		return mapping.findForward(forward);
	}
	public ActionForward getInvolvedUsersXmlForFilter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "Link";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
				forward = "xmlFile";
			} else {
				String userId = (String) request.getSession(false).getAttribute("userId");
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String selectedView = request.getParameter("selectedView");
				FilterManager filterManager = new FilterManager();
				List<SimpleDataObject> filters = filterManager.getFilters(permissionSet, userId, selectionProcessForm.getFilterFor(), selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getStepName(), selectionProcessForm.getApplicantName(), selectionProcessForm.getStepLevel(), selectionProcessForm.getStepId(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getActionRequired(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId(),selectedView);
				xmlFile = SelectionProcessUtils.getInvolvedUsersFilterXml(filters);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
}
