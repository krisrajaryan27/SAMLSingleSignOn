package com.talentPool.positions.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.PositionTemplateXMLGenerator;
import com.talentPool.positions.dataobject.PositionTemplateFilterData;
import com.talentPool.positions.form.PositionForm;
import com.talentPool.positions.manager.PositionTemplateManager;
import com.talentPool.positions.utils.PositionTemplateUtils;
import com.talentPool.requisition.manager.RequisitionManager;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.impl.StepsMigrationWizardDao;
import com.talentPool.stepsMigration.services.impl.StepsMigrationWizardService;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

public class PositionTemplateAction extends TPDispatchAction{

	public ActionForward positionTemplatesHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "positionTemplatesHome";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		PositionForm form = (PositionForm) actionForm;
		try {
			String userId = (String) request.getSession().getAttribute("userId");			
			request.setAttribute("t", NavigationConstants.T_POSITIONS);
			
			boolean canAddRequisition = true;
			if (PositionConstants.REQUISITION_MANAGEMENT_ENABLED) {
				RequisitionManager requisitionManager = new RequisitionManager();
				if (!requisitionManager.isRequisitionAllowed(userId)) {
					canAddRequisition = false;
				}
			}
			
			if(Utils.isBlankOrNull(form.getShowCondition())) {				
				form.setShowCondition(PositionConstants.POSITION_STATUS_TEMPLATE);
			}
			request.setAttribute("requisitionManagement", PositionConstants.REQUISITION_MANAGEMENT_ENABLED);
			request.setAttribute("canAddRequisition", canAddRequisition);
			setPositionSelectCritera(request);
			setMigrationStatus(request);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in position home", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward XMLforTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";

		try {
			PositionForm positionForm = (PositionForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			
			PositionTemplateManager positionTemplateManager = new PositionTemplateManager();
			PositionTemplateFilterData templateFilterData = PositionTemplateUtils.getTemplateFilterData(positionForm, userId);
			ArrayList positions = positionTemplateManager.getAllTemplates(templateFilterData, permissionSet);
			PositionTemplateXMLGenerator positionXMLGenerator = new PositionTemplateXMLGenerator();
			String xmlFile = positionXMLGenerator.getXMLforPositionTemplatesHome(positions, userId, permissionSet);
			request.setAttribute("xmlFile", xmlFile);
			request.setAttribute("positions", positions);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting XML:", e);
		}

		return mapping.findForward(forward);
	}
	
	private void setPositionSelectCritera(HttpServletRequest request) {
		ArrayList<String> ids = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		ids.add(PositionConstants.POSITION_STATUS_TEMPLATE);
		
		names.add(TPLabels.getLabel("position_templates_home.label.show_template_positions"));
		
		String JSCriteria = CommonUtils.getListJavaScriptArray(ids, names);
		request.setAttribute("JSCriteria", JSCriteria);
	}
	
	private void setMigrationStatus(HttpServletRequest request){
		try {
			StepsMigrationWizardDao stepsMigrationWizardDao = new StepsMigrationWizardDao();
			StepsMigrationWizardService stepsMigrationWizardService = new StepsMigrationWizardService();
			stepsMigrationWizardService.setStepsMigrationWizardDao(stepsMigrationWizardDao);
			Map<String, String> migrationStatusMap = stepsMigrationWizardService.getMigrationStatus();
			if(migrationStatusMap!=null && !migrationStatusMap.isEmpty()){
				if(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING.equals(migrationStatusMap.get("templates"))){
					request.setAttribute("migrationPending", true);
				}			
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
}
