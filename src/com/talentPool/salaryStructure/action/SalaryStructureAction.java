package com.talentPool.salaryStructure.action;

import java.util.ArrayList;
import java.util.List;

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
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dao.impl.SalaryComponentCategoryDAOImpl;
import com.talentPool.salaryStructure.databject.SalaryComponentsData;
import com.talentPool.salaryStructure.databject.SalaryFormulaData;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;
import com.talentPool.salaryStructure.form.SalaryStructureForm;
import com.talentPool.salaryStructure.manager.SalaryStructureManager;
import com.talentPool.salaryStructure.utils.SalaryStructureUtils;
import com.talentPool.salaryStructure.utils.SalaryStructureXMLUtils;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

public class SalaryStructureAction extends TPDispatchAction {
	public ActionForward manageSalaryComponents(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageSalaryComponents";
		try {
			Integer[] permissions = new Integer[1];
			permissions[0] = PermissionConstants.PERMISSION_ADMIN;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_SALARY_COMPONENTS);
			request.setAttribute("t", NavigationConstants.T_ADMIN);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getSalaryComponentXMLFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile ="";
		List<SalaryComponentsData> salaryComponents = null;
		SalaryStructureManager salaryStructureManager = null;
		try {
			salaryStructureManager = new SalaryStructureManager();
			salaryComponents = salaryStructureManager.getSalaryComponents();
			xmlFile = SalaryStructureXMLUtils.getSalaryComponentXML(salaryComponents);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	public ActionForward addEditSalaryComponent(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addEditSalaryComponent";
		SalaryStructureManager salaryStructureManager = null;
		SalaryComponentsData salaryComponentsData = null;
		SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
		try {
			if(!Utils.isBlankOrNull(salaryStructureForm.getSalaryComponentId())){
				salaryStructureManager = new SalaryStructureManager();
				salaryComponentsData = salaryStructureManager.getSalaryComponentById(salaryStructureForm.getSalaryComponentId());
				salaryStructureForm.setSalaryComponentName(salaryComponentsData.getSalaryComponentName());
				salaryStructureForm.setSalaryComponentDescription(salaryComponentsData.getSalaryComponentDescription());
				salaryStructureForm.setSalaryComponentType(salaryComponentsData.getSalaryComponentType());
				salaryStructureForm.setSalaryComponentCategoryId(salaryComponentsData.getSalaryComponentCategoryId());
			}
			SalaryComponentCategoryDAOImpl sccdi = new SalaryComponentCategoryDAOImpl();
			List<SalaryComponentCategory>  salCompCatLst = sccdi.findAll();
			
			String salCompCatJSArray = SalaryStructureUtils.getSalaryCompCategoryJSArray(salCompCatLst);
			request.setAttribute("salCompCatJSArray", salCompCatJSArray);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward saveSalaryComponent(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addEditSalaryComponent";
		SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
		SalaryStructureManager salaryStructureManager = null;
		try {
			salaryStructureManager = new SalaryStructureManager();
			salaryStructureManager.saveSalaryComponent(salaryStructureForm.getSalaryComponentId(), salaryStructureForm.getSalaryComponentName(), 
								salaryStructureForm.getSalaryComponentDescription(),salaryStructureForm.getSalaryComponentType(), salaryStructureForm.getSalaryComponentCategoryId());
			request.setAttribute("update", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteSalaryComponent(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
				String salaryComponentId = salaryStructureForm.getSalaryComponentId();
				SalaryStructureManager salaryStructureManager = new SalaryStructureManager();
				salaryStructureManager.deleteSalaryComponent(salaryComponentId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward manageSalaryFormulaTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageSalaryFormulaTemplates";
		try {
			Integer[] permissions = new Integer[2];
			permissions[0] = PermissionConstants.PERMISSION_MASTERS;
			permissions[1] = PermissionConstants.PERMISSION_SALARY_CALCULATION_MASTER;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_SALARY_FORMULA_DESIGNER);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	public ActionForward getSalaryFormulaXMLFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile ="";
		List<SalaryFormulaData> salaryFormula = null;
		SalaryStructureManager salaryStructureManager = null;
		try {
			salaryStructureManager = new SalaryStructureManager();
			salaryFormula = salaryStructureManager.getSalaryFormulae();
			xmlFile = SalaryStructureXMLUtils.getSalaryFormulaXML(salaryFormula);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward addEditSalaryFormula(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addEditSalaryFormula";
		SalaryStructureManager salaryStructureManager = null;
		SalaryFormulaData salaryFormulaData = null;
		SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
		String jsArraySalaryComponents = "[]";
		int hasAdjustableComponent=0;
		String gradeName = "";
		String salaryComponentName = "";
		try {
			salaryStructureManager = new SalaryStructureManager();
			if(salaryStructureForm.getFormulaId()!=0){
				salaryFormulaData = salaryStructureManager.getSalaryFormulaById(salaryStructureForm.getFormulaId());
				populateSalaryStructureForm(salaryFormulaData,salaryStructureForm);
				ArrayList salaryComponents = (ArrayList) salaryStructureManager.getSalaryComponentsNotDefinedForAGrade(salaryStructureForm.getGradeId());
				//jsArraySalaryComponents = SalaryStructureUtils.getJSArraySalaryComponents(salaryComponents);
				hasAdjustableComponent = salaryStructureManager.isGradeHasAdjustableComponent(salaryStructureForm.getGradeId());
				gradeName = salaryFormulaData.getGradeName();
				salaryComponentName = salaryFormulaData.getSalaryComponentName();
			 }
			 String jsArrayGrades = CommonUtils.getJSArrayGrades();
			 request.setAttribute("jsArrayGrades",jsArrayGrades);
			 request.setAttribute("jsArraySalaryComponents",jsArraySalaryComponents);
			 request.setAttribute("jsArraySalaryVariables",SalaryStructureUtils.getSalaryFormulaVariablesJSArray());
			 request.setAttribute("jsArrayRoundingOptions",SalaryStructureUtils.getRoundingJSArray());
			 request.setAttribute("hasAdjustableComponent",""+hasAdjustableComponent);
			 request.setAttribute("gradeName",gradeName);
			 request.setAttribute("salaryComponentName",salaryComponentName);
		} catch (NumberFormatException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward saveSalaryFormula(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
		SalaryStructureManager salaryStructureManager = null;
		SalaryFormulaData salaryFormulaData = new SalaryFormulaData();
		String loggedInUser = (String) request.getSession().getAttribute("userId");
		try {
			populateSalaryStructureData(salaryFormulaData,salaryStructureForm,loggedInUser);
			salaryStructureManager = new SalaryStructureManager();
			salaryStructureManager.saveSalaryFormula(salaryFormulaData);
			request.setAttribute("update", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return addEditSalaryFormula(mapping, actionForm, request, response);
	}
	
	private void populateSalaryStructureForm(SalaryFormulaData salaryFormulaData,SalaryStructureForm salaryStructureForm){
		salaryStructureForm.setFormulaId(salaryFormulaData.getFormulaId());
		salaryStructureForm.setGradeId(salaryFormulaData.getGradeId());
		salaryStructureForm.setSalaryComponentId(""+salaryFormulaData.getSalaryComponentId());
		salaryStructureForm.setMaxLimit(salaryFormulaData.getMaxLimit());
		salaryStructureForm.setVariable1(salaryFormulaData.getVariable1());
		salaryStructureForm.setVariable1Factor(salaryFormulaData.getVariable1Factor());
		salaryStructureForm.setConstantFactor(salaryFormulaData.getConstantFactor());
		salaryStructureForm.setIsAdjustable(salaryFormulaData.getIsAdjustable());
		salaryStructureForm.setRoundingType(salaryFormulaData.getRoundingType());
	}
	
	private void populateSalaryStructureData(SalaryFormulaData salaryFormulaData,SalaryStructureForm salaryStructureForm,String userId){
		salaryFormulaData.setFormulaId(salaryStructureForm.getFormulaId());
		salaryFormulaData.setGradeId(salaryStructureForm.getGradeId());
		salaryFormulaData.setSalaryComponentId(Integer.parseInt(salaryStructureForm.getSalaryComponentId()));
		salaryFormulaData.setMaxLimit(salaryStructureForm.getMaxLimit());
		salaryFormulaData.setVariable1(salaryStructureForm.getVariable1());
		salaryFormulaData.setVariable1Factor(salaryStructureForm.getVariable1Factor());
		salaryFormulaData.setConstantFactor(salaryStructureForm.getConstantFactor());
		salaryFormulaData.setIsAdjustable(salaryStructureForm.getIsAdjustable());
		salaryFormulaData.setRoundingType(salaryStructureForm.getRoundingType());
		salaryFormulaData.setUserId(Integer.parseInt(userId.trim()));
		salaryFormulaData.setDateCreated("");
	}
	
	public ActionForward getSalaryComponentsForaGrade(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile ="";
		SalaryStructureManager salaryStructureManager = null;
		ArrayList salaryComponents = null;
		int hasAdjustableComponent = 0;
		SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			}else {
				salaryStructureManager = new SalaryStructureManager();
				salaryComponents = (ArrayList) salaryStructureManager.getSalaryComponentsNotDefinedForAGrade(salaryStructureForm.getGradeId());
				hasAdjustableComponent = salaryStructureManager.isGradeHasAdjustableComponent(salaryStructureForm.getGradeId());
				xmlFile = hasAdjustableComponent + "|" + SalaryStructureUtils.getJSArraySalaryComponents(salaryComponents);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteSalaryFormula(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
				int salaryFormulaId = salaryStructureForm.getFormulaId();
				SalaryStructureManager salaryStructureManager = new SalaryStructureManager();
				salaryStructureManager.deleteSalaryFormula(salaryFormulaId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward changeCTCRounding(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "changeCTCRounding";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_SALARY_CALCULATION_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			SalaryStructureManager salaryStructureManager = new SalaryStructureManager();
			SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
			String ctcRoundingType = salaryStructureManager.getSalaryRoundingPolicy();
			salaryStructureForm.setCtcRoundingType(ctcRoundingType);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward savCTCRounding(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		SalaryStructureForm salaryStructureForm = (SalaryStructureForm) actionForm;
		SalaryStructureManager salaryStructureManager = null;
		try {
			salaryStructureManager = new SalaryStructureManager();
			if(!Utils.isBlankOrNull(salaryStructureForm.getCtcRoundingType()))
				salaryStructureManager.saveSalaryRoundingPolicy(salaryStructureForm.getCtcRoundingType());
			
			request.setAttribute("update", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return changeCTCRounding(mapping, actionForm, request, response);
	}
}
