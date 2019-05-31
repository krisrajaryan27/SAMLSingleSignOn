package com.talentPool.masters.action;

import java.sql.SQLException;
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
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.form.MastersForm;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.reports.ReportUtils;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

public class StepAction extends TPDispatchAction {

	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward managePositionStepMaster1(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "managePositionStepMaster";
		Integer[] permissions = new Integer[2];
//		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
//		permissions[1] = PermissionConstants.PERMISSION_COST_CENTER_MASTER;
//		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
//			forward = "authorizationFailure";			
//			return mapping.findForward(forward);
//		}
		MastersForm mastersForm = (MastersForm) actionForm;
		MastersManager mastersManager = new MastersManager();
		
		try {
			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String costCenterId = mastersForm.getCostCenterId();
				String costCenterName = mastersForm.getCostCenterName();

				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "addCostCenter";
					if (!Utils.isBlankOrNull(costCenterName)) {
						mastersManager.addCostCenterToDB(costCenterName, mastersForm.getDescription());						
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "addCostCenter";
					if (Utils.isBlankOrNull(costCenterName)) {
						SimpleDataObject costCenter = mastersManager.getCostCenter(costCenterId);
						mastersForm.setCostCenterName((String) costCenter.getAttribute("costCenterName"));
						mastersForm.setDescription((String) costCenter.getAttribute("costCenterDescription"));
						
					} else if (!Utils.isBlankOrNull(costCenterId) && !Utils.isBlankOrNull(costCenterName)) {
						mastersManager.updateCostCenterToDB(costCenterId, costCenterName, mastersForm.getDescription());						
						request.setAttribute("update", "1");
					}
				}
			}
			
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_STEPS);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
		} catch (MasterExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master.errors.cannot_add_step");
			errors.add("admin_master.errors.cannot_add_step", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward managePositionStepMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "managePositionStepMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, 0, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_STEPS);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
	public ActionForward manageStageMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageStageMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, 0, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_STAGES);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
	public ActionForward getSteps(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String hideDisabled = mastersForm.getHideDisabled();
		
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				StepManager stepManager = new StepManager();
				List<MasterStepData> steps = stepManager.getAllSteps(hideDisabled);
				xmlFile = stepManager.getXMLForSteps(steps);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getStages(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				StepManager stepManager = new StepManager();
				List<MasterStepData> steps = stepManager.getAllStages();
				xmlFile = stepManager.getXMLForStages(steps);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward addStep(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addStep";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			String subMode = mastersForm.getSubMode();
			String stepId = mastersForm.getStepId();
			if(subMode.equals(MastersConstants.SUB_MODE_ADD)){
				StepManager stepManager = new StepManager();
				stepId = stepManager.getStepIdToCreate();
				mastersForm.setStepId(stepId);
			}else if(subMode.equals(MastersConstants.SUB_MODE_EDIT)){
				StepManager stepManager = new StepManager();
				MasterStepData data = stepManager.getMasterStepData(stepId);
				mastersForm.setStepId(data.getStepId());
				mastersForm.setStepName(data.getStepName());
				mastersForm.setStepDesc(data.getStepDesc());
				mastersForm.setStepRank(data.getStepRank());
				mastersForm.setStepLevel(data.getStepLevel());
				mastersForm.setStage(data.getStage());
				mastersForm.setStepSchedulable(data.getStepSchedulable());
				mastersForm.setStepDisabled(data.getStepDisabled());
			}
			if(Utils.isBlankOrNull(stepId)){
				ActionErrors errors = new ActionErrors();
				errors.add("master_steps.error.save_step", new ActionError("master_steps.error.save_step"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward editStage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "editStage";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			String stepLevel = mastersForm.getStepLevel();
			
			StepManager stepManager = new StepManager();
			MasterStepData data = stepManager.getStageData(stepLevel);
			
			if(Utils.isBlankOrNull(data.getStepLevel())){
				ActionErrors errors = new ActionErrors();
				errors.add("master_stages.error.update_stage", new ActionError("master_stages.error.update_stage"));
				saveErrors(request, errors);
			}

			mastersForm.setStepLevel(data.getStepLevel());
			mastersForm.setStage(data.getStage());

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward saveStage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "editStage";
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String stepLevel = mastersForm.getStepLevel();
		String stepLevelName = mastersForm.getStage();
		
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			StepManager stepManager = new StepManager();
			stepManager.updateStage(stepLevel, stepLevelName, userId);
			request.setAttribute("update", "1");
			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors errors = new ActionErrors();
			errors.add("master_stages.error.update_stage", new ActionError("master_stages.error.update_stage"));
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getStageListInXML(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				StepManager stepManager = new StepManager();
				List<MasterStepData> stages = stepManager.getAllStages();
				ArrayList<String> stageIds = new ArrayList<String>();
				ArrayList<String> stageNames = new ArrayList<String>();
				stageIds.add("-1");
				stageNames.add(TPLabels.getLabel("master_steps.label.select_stage"));
				for (int i = 0; stages != null && i < stages.size(); i++) {
					MasterStepData stepData = stages.get(i);
					stageIds.add(stepData.getStepLevel());
					stageNames.add(stepData.getStage());
				}
				String jsArray = CommonUtils.getListJavaScriptArray(stageIds, stageNames);

				xmlFile = Utils.getXMLForTagName("forms", jsArray);
			}
		} catch (Exception e) {
			xmlFile = Utils.getXMLForError(null);
		}

		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getStepsInStage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String stepLevel = mastersForm.getStepLevel();
		String systemStep = mastersForm.getSystemStep();
		String stepDisabled = mastersForm.getStepDisabled();
		
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				StepManager stepManager = new StepManager();
				List<MasterStepData> steps = stepManager.getStepsInStage(stepLevel, systemStep, stepDisabled);
				ArrayList<String> stepIds = new ArrayList<String>();
				ArrayList<String> stepNames = new ArrayList<String>();
				
				stepIds.add("-1");
				stepNames.add(TPLabels.getLabel("master_steps.label.select_step"));
				
				for (int i = 0; steps != null && i < steps.size(); i++) {
					MasterStepData stepData = steps.get(i);
					// Do not allow step insertion after Joined step, as Joined is default last step.
					if(!(stepLevel.equals(StepConstants.STEP_STAGE_HIRE) 
							&& stepData.getSystemStep().equals(StepConstants.SYSTEM_STEP))) {						
						stepIds.add(stepData.getStepId());
						stepNames.add(stepData.getStepName());
					}
				}
				String jsArray = CommonUtils.getListJavaScriptArray(stepIds, stepNames);
				
				xmlFile = Utils.getXMLForTagName("forms", jsArray);
			}
		} catch (Exception e) {
			xmlFile = Utils.getXMLForError(null);
		}

		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getStepsXMLInStage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String stepLevel = mastersForm.getStepLevel();
		String systemStep = mastersForm.getSystemStep();
		String stepDisabled = mastersForm.getStepDisabled();
		
		ReportUtils reportUtils = new ReportUtils();
		
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				StepManager stepManager = new StepManager();
				List<MasterStepData> steps = stepManager.getStepsInStage(stepLevel, systemStep, stepDisabled);
				xmlFile = reportUtils.getStepGridXML(steps);
			}
		} catch (Exception e) {
			xmlFile = Utils.getXMLForError(null);
		}

		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward saveStep(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addStep";
		ActionErrors errors = null; 
				
		MastersForm mastersForm = (MastersForm) actionForm;
		String stepId = mastersForm.getStepId();
		String stepName = mastersForm.getStepName();
		String stepDesc = mastersForm.getStepDesc();
		String stepLevel = mastersForm.getStepLevel();
		String stepSchedulable = mastersForm.getStepSchedulable();
		String prevStepId = mastersForm.getStepRank();
		
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			StepManager stepManager = new StepManager();
			stepManager.addStep(stepId, stepName, stepDesc, stepLevel, prevStepId, stepSchedulable, StepConstants.STEP_ALIVE, StepConstants.STEP_ACTIVE, userId);
			request.setAttribute("update", "1");
			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			errors = new ActionErrors();
			errors.add("master_steps.error.save_step", new ActionError("master_steps.error.save_step"));
			saveErrors(request, errors);
		} catch (MasterExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			errors = new ActionErrors();
			errors.add("master_steps.error.duplicate_step", new ActionError("master_steps.error.duplicate_step"));
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward updateStep(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addStep";
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String stepId = mastersForm.getStepId();
		String stepName = mastersForm.getStepName();
		String stepDesc = mastersForm.getStepDesc();
		String stepSchedulable = mastersForm.getStepSchedulable();
		String stepDisabled = mastersForm.getStepDisabled();
		
		ActionErrors errors = null;
		
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			StepManager stepManager = new StepManager();
			stepManager.updateStep(stepId, stepName, stepDesc, stepSchedulable, stepDisabled, userId);
			request.setAttribute("update", "1");
			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			errors = new ActionErrors();
			errors.add("master_steps.error.update_step", new ActionError("master_steps.error.update_step"));
			saveErrors(request, errors);
		} catch (MasterExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			errors = new ActionErrors();
			errors.add("master_steps.error.duplicate_step", new ActionError("master_steps.error.duplicate_step"));
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteStep(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String userId = (String) request.getSession().getAttribute("userId");
				MastersForm mastersForm = (MastersForm) actionForm;
				String stepId = mastersForm.getStepId();
				StepManager stepManager = new StepManager();
				// TODO: check if the step can be deleted or not
				if(!stepManager.isStepUsedInPosition(stepId)) {
					stepManager.deleteStep(stepId,userId);
				} else {
					ArrayList<String> errors = new ArrayList<String>();
					errors.add("step in use, can't delete");
					xmlFile = Utils.getXMLForError(errors);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward moveStep(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				StepManager stepManager = new StepManager();
				String stepId = mastersForm.getStepId();
				String stage = mastersForm.getStage();
				String stepRank = mastersForm.getStepRank();
				String stepOrder = mastersForm.getStepOrder();
				String systemStep = mastersForm.getSystemStep();
				//implement this method
//				boolean used = stepManager.isStepUsedInPosition(stepId);
				boolean used = false;
				if(!used && !systemStep.equals(StepConstants.SYSTEM_STEP)){
					MasterStepData stepDataTo = stepManager.getStepIdTo(stepRank, stepOrder); 
					if(null!=stepDataTo && !Utils.isBlankOrNull(stepDataTo.getStepId()) && !stepDataTo.getSystemStep().equals(StepConstants.SYSTEM_STEP)){
						stepManager.switchSteps(stepId, stage, stepRank , stepDataTo.getStepId(), stepDataTo.getStepRank());
					}
				}else{
					xmlFile = Utils.getXMLForError(null);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
}
