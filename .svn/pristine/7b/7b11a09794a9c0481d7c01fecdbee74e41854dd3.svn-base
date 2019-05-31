/**
 * 
 */
package com.talentPool.requisition.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.requisition.dataobject.RequisitionApprovalStepData;
import com.talentPool.requisition.form.RequisitionForm;
import com.talentPool.requisition.manager.RequisitionManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.user.utils.UserUtils;

/**
 * @author shivprasad
 * 
 */
public class RequisitionAction extends TPDispatchAction {
	public ActionForward addRequisitionStep(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addRequisitionStep";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_REQUISITION_APPROVAL_STEPS_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			RequisitionForm requisitionForm = (RequisitionForm) actionForm;			
			String requisitionApprovalStepId = requisitionForm.getRequisitionApprovalStepId();

			if (!Utils.isBlankOrNull(requisitionApprovalStepId) && !"0".equals(requisitionApprovalStepId)) {
				RequisitionManager requisitionManager = new RequisitionManager();
				ArrayList<SimpleDataObject> activeUsers = requisitionManager.getActiveUsersForStep(requisitionForm.getRequisitionApprovalStepId());				
				request.setAttribute("activeUsers", activeUsers);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward XMLActiveUsers(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		try {
			PositionManager positionManager = new PositionManager();
			ArrayList<SimpleDataObject> users = positionManager.getUsersForRoleForGridDisplay(UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_RECRUITER + ", " + UserConstants.ROLE_ADMIN + ", "
					+ UserConstants.ROLE_CXO + ", " + UserConstants.ROLE_REQUISITIONER);
			String xmlFile = UserUtils.getXMLforActiveUsers(users);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward manageRequisitionApprovalTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "manageRequisitionApprovalTemplates";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_REQUISITION_APPROVAL_STEPS_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		request.setAttribute("masterType", MastersConstants.MASTER_REQUISITION_TEMPLATES);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
	public ActionForward loadRequisitionApprovalTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				RequisitionManager requisitionManager = new RequisitionManager();
				ArrayList<SimpleDataObject> templates = requisitionManager.getRequisitionApprovalTemplates();
				xmlFile = requisitionManager.getXMLforRequisitionApprovalTemplates(templates);				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	} 
	
	public ActionForward requisitionApprovalTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "requisitionApprovalTemplate";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_REQUISITION_APPROVAL_STEPS_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}

		RequisitionForm form = (RequisitionForm) actionForm;
		try {			
			String requisitionApprovalTemplateId = form.getRequisitionApprovalTemplateId();
			if(!Utils.isBlankOrNull(requisitionApprovalTemplateId)) {
				RequisitionManager requisitionManager = new RequisitionManager();
				ArrayList<RequisitionApprovalStepData> steps = requisitionManager.getRequisitionApprovalSteps(requisitionApprovalTemplateId);
				request.setAttribute("steps", steps);
				String requisitionApprovalTemplateName = requisitionManager.getRequisitionApprovalTemplateName(requisitionApprovalTemplateId);
				form.setRequisitionApprovalTemplateName(requisitionApprovalTemplateName);
			}					
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("masterType", MastersConstants.MASTER_REQUISITION_TEMPLATES);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
	public ActionForward saveRequisitionApprovalTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "requisitionApprovalTemplate";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_REQUISITION_APPROVAL_STEPS_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}

		RequisitionForm form = (RequisitionForm) actionForm;
		try {			
			String requisitionApprovalTemplateId = form.getRequisitionApprovalTemplateId();
			List<RequisitionApprovalStepData> steps = getRequisitionApprovalSteps(form.getJsArrayRequisitionApprovalSteps());
			RequisitionManager manager = new RequisitionManager();
			if(!Utils.isBlankOrNull(requisitionApprovalTemplateId)) {
				// Update
				manager.updateRequisitionApprovalTemplate(requisitionApprovalTemplateId, form.getRequisitionApprovalTemplateName(), steps);
			} else {
				// Save
				manager.createRequisitionApprovalTemplate(form.getRequisitionApprovalTemplateName(), steps);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors actionErrors = new ActionErrors();
			actionErrors.add("requisition_approval_steps.error.create_template", new ActionError("requisition_approval_steps.error.create_template"));
			request.setAttribute("masterType", MastersConstants.MASTER_REQUISITION_TEMPLATES);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
			return mapping.findForward(forward);
		}
		return manageRequisitionApprovalTemplates(mapping, actionForm, request, response);
	}
	
	private List<RequisitionApprovalStepData> getRequisitionApprovalSteps(String jsArrayRequisitionApprovalSteps) {
		List<RequisitionApprovalStepData> steps = new ArrayList<RequisitionApprovalStepData>();
		if(!Utils.isBlankOrNull(jsArrayRequisitionApprovalSteps)) {
			String[] _steps = jsArrayRequisitionApprovalSteps.split(",");
			for (int i = 0; _steps.length > 0 && i < _steps.length; i++) {
				String _step = _steps[i];
				if (!Utils.isBlankOrNull(_step)) {
					String[] attributes = _step.split("[|]");
					if (attributes != null && attributes.length > 0) {
						RequisitionApprovalStepData sdata = new RequisitionApprovalStepData();
						sdata.setRequisitionApprovalStepId(Integer.parseInt(Utils.getFormattedRecord(attributes[0].trim())));
						sdata.setRequisitionApprovalStepName(Utils.getFormattedRecord(attributes[1].trim()));
						sdata.setUserIds(Utils.getFormattedRecord(attributes[2].trim()));
						sdata.setRequisitionApprovalStepRank(i + 1);
						steps.add(sdata);
					}
				}
			}
		}
		return steps;
	}
	
	public ActionForward isDeleteRequisitionStepPossible(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				RequisitionForm requisitionForm = (RequisitionForm) actionForm;
				RequisitionManager requisitionManager = new RequisitionManager();
				String requisitionApprovalStepId = requisitionForm.getRequisitionApprovalStepId();
				ArrayList<SimpleDataObject> requisitions = requisitionManager.getRequisitionsInProcessForStep(requisitionApprovalStepId);
				if (requisitions != null && requisitions.size() > 0) {
					xmlFile = Utils.getXMLForError();
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteRequisitionApprovalTemplate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageRequisitionApprovalTemplates";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_REQUISITION_APPROVAL_STEPS_SETTINGS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_REQUISITION, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		RequisitionForm form = (RequisitionForm) actionForm;
		try {			
			String requisitionApprovalTemplateId = form.getRequisitionApprovalTemplateId();
			if(!Utils.isBlankOrNull(requisitionApprovalTemplateId)) {
				RequisitionManager requisitionManager = new RequisitionManager();				
				ArrayList<SimpleDataObject> requisitions = requisitionManager.getRequisitionsInProcessForTemplate(requisitionApprovalTemplateId);
				if (requisitions != null && requisitions.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for(int i = 0; i < requisitions.size(); i++) {
						if(sb.length() > 0) {
							sb.append("\n");
						}
						sb.append(requisitions.get(i).getString("positionTitle"));
					}
					ActionErrors actionErrors = new ActionErrors();
					actionErrors.add("requisition_approval_steps.error.requisitions_inprocess_for_template", new ActionError("requisition_approval_steps.error.requisitions_inprocess_for_template", sb.toString()));
					saveErrors(request, actionErrors);
				} else {
					requisitionManager.deleteRequisitionApprovalTemplate(requisitionApprovalTemplateId);
				}
			}					
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors actionErrors = new ActionErrors();
			actionErrors.add("requisition_approval_steps.error.error_while_delete_template", new ActionError("requisition_approval_steps.error.error_while_delete_template"));
		}
		request.setAttribute("masterType", MastersConstants.MASTER_REQUISITION_TEMPLATES);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
}
