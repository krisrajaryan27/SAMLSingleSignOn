/**
 * 
 */
package com.talentPool.masters.action;

import java.io.File;
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

import com.talentPool.Skills.Skills.SkillsUtils;
import com.talentPool.applicant.utils.ApplicantUtils;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.AliasData;
import com.talentPool.masters.dataobject.BranchesData;
import com.talentPool.masters.dataobject.DegreeData;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.FeedbackFieldCategoryData;
import com.talentPool.masters.dataobject.FeedbackFieldData;
import com.talentPool.masters.dataobject.InboxFolderData;
import com.talentPool.masters.dataobject.InstituteData;
import com.talentPool.masters.dataobject.LocationData;
import com.talentPool.masters.dataobject.MultipleSelectFieldsData;
import com.talentPool.masters.dataobject.MultipleSelectsData;
import com.talentPool.masters.dataobject.RatingFieldsData;
import com.talentPool.masters.dataobject.RatingsData;
import com.talentPool.masters.dataobject.SkillCategoryData;
import com.talentPool.masters.dataobject.SkillData;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.dataobject.SourceTypeData;
import com.talentPool.masters.exception.AliasExistException;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.form.MastersForm;
import com.talentPool.masters.manager.FeedbackFieldsManager;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.masters.manager.MultipleSelectsManager;
import com.talentPool.masters.manager.RatingsManager;
import com.talentPool.masters.manager.RecentlyUsedSources;
import com.talentPool.masters.utils.MultipleSelectUtils;
import com.talentPool.masters.utils.RatingUtils;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.repository.TPIndexEvent;
import com.talentPool.repository.TPIndexEventQueue;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.exception.EmployeeCodeExistException;
import com.talentPool.user.exception.SourceExistException;
import com.talentPool.user.exception.SourceOrEmployeeCodeExistException;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class MastersAction extends TPDispatchAction {
	public ActionForward masters(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "masters";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			request.setAttribute("t", NavigationConstants.T_MASTERS);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error masters home", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageBranchesMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageBranchesMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_BRANCH_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_BRANCH);

			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();

			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String branchId = mastersForm.getBranchId();
				String branchName = mastersForm.getBranchName();

				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "addBranch";
					if (!Utils.isBlankOrNull(branchName)) {

						mastersManager.addBranchToDB(branchName, mastersForm.getAliases());
						CommonUtils.setBranchIds(null);
						CommonUtils.setBranchNames(null);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "addBranch";
					if (Utils.isBlankOrNull(branchName)) {
						ArrayList branches = mastersManager.getBranchesWithAliases(branchId);
						BranchesData bdata = (BranchesData) branches.get(0);
						// mastersForm.setDepartmentName(departmentData.getItemName());
						mastersForm.setBranchName(bdata.getItemName());
						String[] aliases = new String[3];
						aliases[0] = "";
						aliases[1] = "";
						aliases[2] = "";
						ArrayList al = bdata.getAliases();
						for (int i = 0; al != null && i < al.size() && i < aliases.length; i++) {
							AliasData alias = (AliasData) al.get(i);
							aliases[i] = alias.getAlias();
						}
						mastersForm.setAliases(aliases);
						// request.setAttribute("update", "1");
					} else if (!Utils.isBlankOrNull(branchId) && !Utils.isBlankOrNull(branchName)) {
						mastersManager.updateBranch(branchName, branchId, mastersForm.getAliases());
						CommonUtils.setBranchIds(null);
						CommonUtils.setBranchNames(null);
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_BRANCH, branchId, TPIndexEvent.PRIORITY_NORMAL));
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Branch Master exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_branch_cat.error");
			errors.add("admin_master_branch_cat.error", error);
			saveErrors(request, errors);
		} catch (AliasExistException e) {
			TPLogger.getLogger().error("The Branch Alias exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_alias_cat.error");
			errors.add("admin_master_alias_cat.error", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display branches", e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward manageBranches(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String branchId = mastersForm.getBranchId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList branches = mastersManager.getBranchesWithAliases(null);
					xmlFile = mastersManager.getXMLforBranches(branches);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(branchId)) {
						mastersManager.deleteBranchFromDB(branchId);
						xmlFile = Utils.getXMLForIds(branchId);
						CommonUtils.setBranchIds(null);
						CommonUtils.setBranchNames(null);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing branches", e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("1");
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward manageInstitutesMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageInstitutesMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_INSTITUTES);

			request.setAttribute("t", NavigationConstants.T_MASTERS);

			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {

				String instituteId = mastersForm.getInstituteId();
				String instituteName = mastersForm.getInstituteName();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "addInstitute";
					if (!Utils.isBlankOrNull(instituteName)) {
						mastersManager.addInstituteToDB(instituteName, mastersForm.getAliases());
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "addInstitute";
					if (Utils.isBlankOrNull(instituteName)) {
						ArrayList institutes = mastersManager.getInstitutesWithAliases(instituteId);
						InstituteData bdata = (InstituteData) institutes.get(0);
						mastersForm.setInstituteName(bdata.getItemName());
						String[] aliases = new String[3];
						aliases[0] = "";
						aliases[1] = "";
						aliases[2] = "";
						ArrayList al = bdata.getAliases();
						for (int i = 0; al != null && i < aliases.length && i < al.size(); i++) {
							AliasData alias = (AliasData) al.get(i);
							aliases[i] = alias.getAlias();
						}
						mastersForm.setAliases(aliases);
						// request.setAttribute("update", "1");
					} else if (!Utils.isBlankOrNull(instituteId) && !Utils.isBlankOrNull(instituteName)) {
						mastersManager.updateInstitute(instituteName, instituteId, mastersForm.getAliases());
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_INSTITUTE, instituteId, TPIndexEvent.PRIORITY_NORMAL));
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Institute Master exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_institute_exists.error");
			errors.add("admin_master_institute_exists.error", error);
			saveErrors(request, errors);
		} catch (AliasExistException e) {
			TPLogger.getLogger().error("The Institute Alias exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_institute_alias_exists.error");
			errors.add("admin_master_institute_alias_exists.error", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display institutes with aliases", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageInstitutes(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String instituteId = mastersForm.getInstituteId();
				String instituteName = mastersForm.getInstituteName();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList institutes = mastersManager.getInstitutesWithAliases(null);
					xmlFile = mastersManager.getXMLforInstitutes(institutes);
				} else if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					if (!Utils.isBlankOrNull(instituteName)) {
						mastersManager.addInstituteToDB(instituteName, mastersForm.getAliases());
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					if (Utils.isBlankOrNull(instituteName)) {
						ArrayList institutes = mastersManager.getInstitutesWithAliases(instituteId);
						InstituteData bdata = (InstituteData) institutes.get(0);
						xmlFile = mastersManager.getXMLForEditInstitute(bdata);
					} else if (!Utils.isBlankOrNull(instituteId) && !Utils.isBlankOrNull(instituteName)) {
						mastersManager.updateInstitute(instituteName, instituteId, mastersForm.getAliases());
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_INSTITUTE, instituteId, TPIndexEvent.PRIORITY_NORMAL));
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(instituteId)) {
						mastersManager.deleteInstituteFromDB(instituteId);
						xmlFile = Utils.getXMLForIds(instituteId);
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing institutes", e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("1");
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward manageDegreeAliasesMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageDegreeAliasesMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;

			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_DEGREE_ALIASES);
			request.setAttribute("t", NavigationConstants.T_MASTERS);

			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String degreeId = mastersForm.getDegreeId();
				String degreeName = mastersForm.getDegreeName();
				forward = "addDegree";
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					if (!Utils.isBlankOrNull(degreeName)) {
						mastersManager.addDegreeToDB(degreeName, mastersForm.getAliases(),mastersForm.getDegreeType());
						CommonUtils.setDegreeIds(null);
						CommonUtils.setDegreeNames(null);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					if (Utils.isBlankOrNull(degreeName)) {
						ArrayList degrees = mastersManager.getDegreesWithAliases(degreeId);
						DegreeData data = (DegreeData) degrees.get(0);
						// xmlFile = mastersManager.getXMLForEditDegree(data);
						mastersForm.setDegreeName(data.getItemName());
						mastersForm.setDegreeType(data.getDegreeType());
						String[] aliases = new String[3];
						aliases[0] = "";
						aliases[1] = "";
						aliases[2] = "";
						ArrayList al = data.getAliases();
						for (int i = 0; al != null && i < aliases.length && i < al.size(); i++) {
							AliasData alias = (AliasData) al.get(i);
							aliases[i] = alias.getAlias();
						}
						mastersForm.setAliases(aliases);

					} else if (!Utils.isBlankOrNull(degreeId) && !Utils.isBlankOrNull(degreeName)) {
						mastersManager.updateDegreeTitle(degreeName, degreeId, mastersForm.getAliases(), mastersForm.getDegreeType());
						CommonUtils.setDegreeIds(null);
						CommonUtils.setDegreeNames(null);
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_DEGREE, degreeId, TPIndexEvent.PRIORITY_NORMAL));
						request.setAttribute("update", "1");
					}

				}
			}

		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The degree already exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_degree_cat.error");
			errors.add("admin_master_degree_cat.error", error);
			saveErrors(request, errors);
		} catch (AliasExistException e) {
			TPLogger.getLogger().error("The degree alias already exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_degree_alias_cat.error");
			errors.add("admin_master_degree_alias_cat.error", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display degrees with aliases", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageDegreeAliases(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String degreeId = mastersForm.getDegreeId();
				String degreeName = mastersForm.getDegreeName();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList degrees = mastersManager.getDegreesWithAliases(null);
					xmlFile = mastersManager.getXMLForDegreeAliases(degrees);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(degreeId)) {
						mastersManager.deleteDegreeTitle(degreeId);
						xmlFile = Utils.getXMLForIds(degreeId);
						CommonUtils.setDegreeIds(null);
						CommonUtils.setDegreeNames(null);
					}
				}
				request.setAttribute("degreeName", degreeName);

			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing degrees", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward manageSourceTypesMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageSourceTypesMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_SOURCE_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;

			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_SOURCE_TYPE);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();
			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String sourceTypeId = mastersForm.getSourceTypeId();
				String category = mastersForm.getSourceTypeName();
				
				forward = "addSourceTypes";
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					if (!Utils.isBlankOrNull(category)) {
						mastersManager.addSourceCategorytoDB(category);
						CommonUtils.setSourceIds(null);
						CommonUtils.setSourceNames(null);
						CommonUtils.setSourceTypeIds(null);
						CommonUtils.setSourceTypeNames(null);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					if (Utils.isBlankOrNull(category)) {
						SourceTypeData sdata = mastersManager.getSourceType(sourceTypeId);
						mastersForm.setSourceTypeName(sdata.getItemName());
						
						// xmlFile =
						// mastersManager.getXMLForEditSourceType(sdata);
					} else {
						mastersManager.updateSourceType(category, sourceTypeId);
						CommonUtils.setSourceIds(null);
						CommonUtils.setSourceNames(null);
						CommonUtils.setSourceTypeIds(null);
						CommonUtils.setSourceTypeNames(null);
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Source Category exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_source_category.error.duplicate");
			errors.add("admin_master_source_category.error.duplicate", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display sources", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageSourceTypes(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String sourceTypeId = mastersForm.getSourceTypeId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList<SourceTypeData> sources = mastersManager.getSourceTypes();
					xmlFile = mastersManager.getSourceTypesInXML(sources);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					mastersManager.deleteSourceType(sourceTypeId);
					CommonUtils.setSourceIds(null);
					CommonUtils.setSourceNames(null);
					CommonUtils.setSourceTypeIds(null);
					CommonUtils.setSourceTypeNames(null);
					
					xmlFile = Utils.getXMLForIds(sourceTypeId);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing sources", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward manageSourcesMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageSourcesMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_SOURCE_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();
			if (!Utils.isBlankOrNull(mastersForm.getSourceTypeId())) {
				SourceTypeData sdata = mastersManager.getSources(mastersForm.getSourceTypeId());
				mastersForm.setSourceTypeName(sdata.getItemName());
				mastersForm.setSourceCategoryType(sdata.getSourceTypeCategory());
				//mastersForm.setSourceCvLimit(sdata.getSourceCvLimit());
			}
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_SOURCE);
			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String sourceName = mastersForm.getSourceName();
				String sourceId = mastersForm.getSourceId();
				forward = "addSources";
				if (!Utils.isBlankOrNull(sourceName)) {
					ActionErrors errors = new ActionErrors();
					if ("1".equalsIgnoreCase(mastersForm.getSendEmailToSource()) && Utils.isBlankOrNull(mastersForm.getEmail())) {
						errors.add("admin_master_source.error.email_required_to_send_email_to_source", new ActionError("admin_master_source.error.email_required_to_send_email_to_source"));
					}
					if ("1".equalsIgnoreCase(mastersForm.getSendSMSToSource()) && Utils.isBlankOrNull(mastersForm.getMobile())) {
						errors.add("admin_master_source.error.mobile_required_to_send_sms_to_source", new ActionError("admin_master_source.error.mobile_required_to_send_sms_to_source"));
					}
					if (errors.size() > 0) {
						saveErrors(request, errors);
						return mapping.findForward(forward);
					}
				}
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					if (!Utils.isBlankOrNull(sourceName)) {
						SourceData data = populateSourceData(mastersForm);
						if(mastersManager.employeeCodeExists(data.getEmployeeCode())){
							throw new EmployeeCodeExistException();
						}
						mastersManager.addSourcetoSourceType(data, null);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					if (Utils.isBlankOrNull(sourceName)) {
						SourceData sdata = mastersManager.getSource(sourceId);
						mastersForm.setSourceName(sdata.getSourceTitle());
						mastersForm.setEmail(sdata.getSourceEmail());
						mastersForm.setPhone(sdata.getSourcePhone());
						mastersForm.setMobile(sdata.getSourceMobile());
						mastersForm.setEmployeeCode(sdata.getEmployeeCode());
						mastersForm.setSendEmailToSource(sdata.getSendEmailToSource());
						mastersForm.setSendSMSToSource(sdata.getSendSMSToSource());
						mastersForm.setSourceBlacklisted(sdata.getSourceBlacklisted());
						mastersForm.setLockInPeriodOnImport(sdata.getLockInPeriodOnImport());
						mastersForm.setSourceCvLimit(sdata.getSourceCvLimit());
					} else {
						SourceData data = populateSourceData(mastersForm);
						mastersManager.updateSources(data,null);
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_SOURCE, sourceId, TPIndexEvent.PRIORITY_NORMAL));
						request.setAttribute("update", "1");
					}
				}
			}
		} catch(EmployeeCodeExistException e) {
			TPLogger.getLogger().error("The Employee code already exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_source.error.duplicate_employee_code");
			errors.add("admin_master_source.error.duplicate_employee_code", error);
			saveErrors(request, errors);
		}catch (SourceExistException e) {
			TPLogger.getLogger().error("The Source Category exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_source.error.duplicate");
			errors.add("admin_master_source.error.duplicate", error);
			saveErrors(request, errors);
		} catch (SourceOrEmployeeCodeExistException e) {
			TPLogger.getLogger().error("The Source Category exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_source.error.duplicate_source_or_empcode");
			errors.add("admin_master_source.error.duplicate_source_or_empcode", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	private SourceData populateSourceData(MastersForm mastersForm) {
		SourceData data = new SourceData();
		data.setSourceTypeId(mastersForm.getSourceTypeId());
		data.setSourceId(mastersForm.getSourceId());
		data.setSourceTitle(mastersForm.getSourceName());
		data.setSourceEmail(mastersForm.getEmail());
		data.setSourcePhone(mastersForm.getPhone());
		data.setSourceMobile(mastersForm.getMobile());
		data.setSendEmailToSource(mastersForm.getSendEmailToSource());
		data.setSendSMSToSource(mastersForm.getSendSMSToSource());
		data.setLockInPeriodOnImport(mastersForm.getLockInPeriodOnImport());
		data.setEmployeeCode(mastersForm.getEmployeeCode());
		data.setSourceBlacklisted(mastersForm.getSourceBlacklisted());
		data.setSourceCvLimit(mastersForm.getSourceCvLimit());
		return data;
	}

	public ActionForward manageSources(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String sourceTypeId = mastersForm.getSourceTypeId();
				String sourceId = mastersForm.getSourceId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					SourceTypeData sdata = mastersManager.getSources(sourceTypeId);
					xmlFile = mastersManager.getSourcesInXML(sdata.getSources());
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					mastersManager.deleteSource(sourceId,null);
					CommonUtils.setSourceIds(null);
					CommonUtils.setSourceNames(null);
					CommonUtils.setSourceIdsWithoutEmployeeSource(null);
					CommonUtils.setSourceNamesWithoutEmployeeSource(null);
					RecentlyUsedSources.INSTANCE.setRecentlyUsedSources(null);
					xmlFile = Utils.getXMLForIds(sourceId);
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing sources", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward manageSkillCategoriesMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageSkillCategoriesMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_SKILL_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_SKILLS_CATEGORY);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();
			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String skillCategory = mastersForm.getSkillCategory();
				String skillCategoryId = mastersForm.getSkillCategoryId();
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "addSkillCategory";
					if (!Utils.isBlankOrNull(skillCategory)) {
						mastersManager.addSkillCategorytoDB(skillCategory);
						CommonUtils.setSkillIds(null);
						CommonUtils.setSkillNames(null);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "addSkillCategory";
					if (Utils.isBlankOrNull(skillCategory)) {
						SkillCategoryData skillCategoryData = mastersManager.getSkillCategory(skillCategoryId);
						mastersForm.setSkillCategory(skillCategoryData.getItemName());
					} else if (!Utils.isBlankOrNull(skillCategoryId) && !Utils.isBlankOrNull(skillCategory)) {
						mastersManager.updateSkillCategory(skillCategory, skillCategoryId);
						CommonUtils.setSkillIds(null);
						CommonUtils.setSkillNames(null);
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Skill Category exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_skill_cat.error.duplicate");
			errors.add("admin_master_skill_cat.error.duplicate", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display skills", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageSkillCategories(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String skillCategoryId = mastersForm.getSkillCategoryId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList skillCategories = mastersManager.getSkillCategories();
					xmlFile = mastersManager.getXMLForSkillCategoies(skillCategories);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(skillCategoryId)) {
						mastersManager.deleteSkillCategory(skillCategoryId);
						xmlFile = Utils.getXMLForIds(skillCategoryId);
						CommonUtils.setSkillIds(null);
						CommonUtils.setSkillNames(null);
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing skills", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward manageSkillsMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageSkillsMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_SKILL_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			MastersManager mastersManager = new MastersManager();
			String skillCategoryId = mastersForm.getSkillCategoryId();
			if (!Utils.isBlankOrNull(skillCategoryId)) {
				SkillCategoryData skillCategoryData = mastersManager.getSkillCategory(skillCategoryId);
				mastersForm.setSkillCategory(skillCategoryData.getItemName());
			}

			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_SKILLS);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				forward = "addSkills";
				String skillId = mastersForm.getSkillId();
				String skillName = mastersForm.getSkillName();
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					if (!Utils.isBlankOrNull(skillName)) {
						mastersManager.addSkilltoSkillCategory(skillName, skillCategoryId, mastersForm.getAliases());
						CommonUtils.setSkillIds(null);
						CommonUtils.setSkillNames(null);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					if (Utils.isBlankOrNull(skillName)) {
						SkillData skillData = mastersManager.getSkill(skillId);
						// xmlFile = mastersManager.getXMLForEditSkill(skillData);
						mastersForm.setSkillName(skillData.getItemName());
						String[] aliases = new String[3];
						aliases[0] = "";
						aliases[1] = "";
						aliases[2] = "";
						ArrayList al = skillData.getAliases();
						for (int i = 0; al != null && i < al.size(); i++) {
							AliasData alias = (AliasData) al.get(i);
							aliases[i] = alias.getAlias();
						}
						mastersForm.setAliases(aliases);
					} else if (!Utils.isBlankOrNull(skillId) && !Utils.isBlankOrNull(skillName)) {
						mastersManager.updateSkill(skillName, skillId, mastersForm.getAliases());
						CommonUtils.setSkillIds(null);
						CommonUtils.setSkillNames(null);
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_SKILLS, skillId, TPIndexEvent.PRIORITY_NORMAL));
						request.setAttribute("update", "1");

					}
				}

			}

		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The skill exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_skill.error.duplicate");
			errors.add("admin_master_skill.error.duplicate", error);
			saveErrors(request, errors);
		} catch (AliasExistException e) {
			TPLogger.getLogger().error("The Skill Alias exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_skill_alias.error.duplicate");
			errors.add("admin_master_skill_alias.error.duplicate", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display skills", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageSkills(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				// String skillCategory = mastersForm.getSkillCategory();
				String skillCategoryId = mastersForm.getSkillCategoryId();
				String skillId = mastersForm.getSkillId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					SkillCategoryData skillCategoryData = mastersManager.getSkills(skillCategoryId);
					xmlFile = mastersManager.getXMLForSkill(skillCategoryData.getSkills());
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(skillId)) {
						mastersManager.deleteSkill(skillId);
						xmlFile = Utils.getXMLForIds(skillId);
						CommonUtils.setSkillIds(null);
						CommonUtils.setSkillNames(null);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing skills", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	/**
	 * Action to goto screen of Department Master
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward manageDepartmentMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageDepartmentMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_DEPARTMENT);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();
			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String departmentId = mastersForm.getDeptId();
				String departmentName = mastersForm.getDepartmentName();
				String parentDepartmentId = mastersForm.getParentDepartmentId();
				String parentDepartmentLevel = mastersForm.getParentDepartmentLevel();
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "addDeptName";
					if (!Utils.isBlankOrNull(departmentName) && !Utils.isBlankOrNull(parentDepartmentId) && !Utils.isBlankOrNull(parentDepartmentLevel) && !parentDepartmentLevel.equals(MastersConstants.DEPARTMENT_LEVEL_5)) {
						mastersManager.addDeptToDB(departmentName,parentDepartmentId);
						CommonUtils.setDeptIds(null);
						CommonUtils.setDeptNames(null);
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "addDeptName";
					if (Utils.isBlankOrNull(departmentName)) {
						DepartmentData departmentData = mastersManager.getDeptData(departmentId);
						mastersForm.setDepartmentName(departmentData.getItemName());
					} else if (!Utils.isBlankOrNull(departmentId) && !Utils.isBlankOrNull(departmentName)) {
						mastersManager.updateDept(departmentName, departmentId);
						CommonUtils.setDeptIds(null);
						CommonUtils.setDeptNames(null);
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Department Master exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master_dept_exists.error");
			errors.add("admin_master_dept_exists.error", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display departments", e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * Action related to manage departments
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward manageDepartments(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String departmentId = mastersForm.getDeptId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList<DepartmentData> deptNames = mastersManager.getDepartments();
					xmlFile = mastersManager.getXMLForDepartments(deptNames);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(departmentId)) {
						mastersManager.deleteDeptFromDB(departmentId);
						xmlFile = Utils.getXMLForIds(departmentId);
						CommonUtils.setDeptIds(null);
						CommonUtils.setDeptNames(null);
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing departments", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward manageTemplateMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageTemplateMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_TEMPLATE_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;

			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_TEMPLATE);
			request.setAttribute("t", NavigationConstants.T_MASTERS);

			String subMode = mastersForm.getSubMode();
			String userId = (String) request.getSession().getAttribute("userId");
			if (!Utils.isBlankOrNull(subMode)) {
				String templateName = mastersForm.getTemplateName();
				String templateCode = mastersForm.getTemplateCode();
				TemplateManager templateManager = new TemplateManager();
				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "showTemplatePopup";
					if (!Utils.isBlankOrNull(templateName)) {
						TemplateData templateData = new TemplateData();
						templateData.setTemplateName(mastersForm.getTemplateName());
						templateData.setTemplateTypeId(mastersForm.getTemplateTypeId());
						templateData.setIsTemplateDefault(mastersForm.getTemplateIsDefault());
						templateData.setIsTemplateSaveAsDraft(mastersForm.getTemplateIsSaveAsDraft());
						templateData.setDoShowSaveAsDraftOption(mastersForm.getDoShowSaveAsDraftOption());
						templateData.setTemplateSubjectText(mastersForm.getTemplateSubject());
						templateData.setTemplateContentText(mastersForm.getTemplateContent());
						templateData.setTemplateAuto(TemplateConstants.AUTO_NOT_CREATED);
						templateData.setTemplatePrivate(mastersForm.getTemplatePrivate());
						templateData.setUserId(userId);
						templateData.setTemplateFormat(TemplateConstants.FORMAT_HTML);
						templateManager.createUserTemplate(templateData);
						request.setAttribute("update", "1");
					} else {
						mastersForm.setJsArrayTemplateFor(getJSArrayTemplateFor());
						mastersForm.setTemplateOwnerId(userId);
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "showTemplatePopup";
					if (Utils.isBlankOrNull(templateName)) {
						TemplateData data = templateManager.getTemplateData(templateCode);
						if (data != null) {
							mastersForm.setTemplateCode(data.getTemplateCode());
							mastersForm.setTemplateName(data.getTemplateName());
							mastersForm.setTemplateType(data.getTemplateType());
							mastersForm.setTemplateTypeId(data.getTemplateTypeId());
							mastersForm.setTemplateIsDefault(data.getIsTemplateDefault());
							mastersForm.setTemplateIsSaveAsDraft(data.getIsTemplateSaveAsDraft());
							mastersForm.setDoShowSaveAsDraftOption(data.getDoShowSaveAsDraftOption());
							mastersForm.setTemplatePrivate(data.getTemplatePrivate());
							VelocityManager velocityManager = new VelocityManager();
							mastersForm.setTemplateSubject(velocityManager.getContent(data.getTemplateSubjectFile()));
							mastersForm.setTemplateContent(velocityManager.getContent(data.getTemplateContentFile()));
							String variables = data.getTemplateVariables();
							StringBuffer buffer = new StringBuffer();
							if (variables != null && variables.length() > 0) {
								String[] parts = variables.split(",");
								for (int i = 0; i < parts.length; i++) {
									if (buffer.length() > 0) {
										buffer.append(",");
									}
									buffer.append("$");
									buffer.append(parts[i].trim());
								}
							}
							mastersForm.setTemplateVariables(buffer.toString());
							//mastersForm.setTemplateVariables(data.getTemplateVariables());
							mastersForm.setTemplateOwnerId(data.getUserId());
						}
						mastersForm.setJsArrayTemplateFor(getJSArrayTemplateFor());
					} else {
						TemplateData templateData = new TemplateData();
						templateData.setTemplateCode(mastersForm.getTemplateCode());
						templateData.setTemplateName(mastersForm.getTemplateName());
						templateData.setTemplateTypeId(mastersForm.getTemplateTypeId());
						templateData.setIsTemplateDefault(mastersForm.getTemplateIsDefault());
						templateData.setIsTemplateSaveAsDraft(mastersForm.getTemplateIsSaveAsDraft());
						templateData.setTemplateSubjectText(mastersForm.getTemplateSubject());
						templateData.setTemplateContentText(mastersForm.getTemplateContent());
						templateData.setTemplateAuto(TemplateConstants.AUTO_NOT_CREATED);
						templateData.setTemplatePrivate(mastersForm.getTemplatePrivate());
						templateData.setUserId(userId);
						templateData.setTemplateFormat(TemplateConstants.FORMAT_HTML);
						templateManager.editUserTemplate(templateData);
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in displaying templates", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward manageTemplates(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		String userId = (String) request.getSession(false).getAttribute("userId");
		int userRoleId = Integer.parseInt((String) request.getSession(false).getAttribute("userRoles"));
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String templateCode = mastersForm.getTemplateCode();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					xmlFile = mastersManager.getXMLForTemplates(userId, userRoleId,permissionSet);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(templateCode)) {
						mastersManager.deleteTemplate(templateCode);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing departments", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward getGenericVariables(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersManager mastersManager = new MastersManager();
				MastersForm form = (MastersForm) actionForm;
				String templateTypeId = form.getTemplateTypeId();
				TemplateManager templateManager = new TemplateManager();
				TemplateData data = templateManager.getTemplateDataByTemplateTypeId(templateTypeId);
				if(data != null) {
					xmlFile = mastersManager.getGenericVariablesInXML(data);
				}				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting generic variables", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	private String getJSArrayTemplateFor() {
		TemplateManager templateManager = new TemplateManager();
		ArrayList<SimpleDataObject> templateTypes = templateManager.getTemplateTypes();
		ArrayList<String> entityIds = new ArrayList<String>();
		ArrayList<String> entityNames = new ArrayList<String>();
		for (int i = 0; templateTypes != null && i < templateTypes.size(); i++) {
			String templateTypeId = templateTypes.get(i).getString("templateTypeId");
			String templateTypeTitle = templateTypes.get(i).getString("templateType");
			boolean templateAvailable = true;
			if (!ModuleSet.isMODULE_SMS()) {
				if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_APPLICANT) || templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_INTERVIEWER)
						|| templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_OWNER)) {
					templateAvailable = false;
				}
			}
			if (!ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL()) {
				if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_AUTO_REPLY_EMAIL_TEMPLATE)) {
					templateAvailable = false;
				}
			}
			if (!ModuleSet.isMODULE_OUTLOOK_MEETING_REQUEST()) {
				if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_NEW_APPOINTMENT_NOTIFICATION) || templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_MODIFIED_APPOINTMENT_NOTIFICATION)
						|| templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_CANCELLED_APPOINTMENT_NOTIFICATION)) {
					templateAvailable = false;
				}
			}
			if (!ModuleSet.isMODULE_VENDOR()) {
				if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_DUPLICATE_UPLOAD_TRIED_BY_VENDOR_NOTIFICATION)) {
					templateAvailable = false;
				}
			}
			if (!ModuleSet.isMODULE_REQUISITION()) {
				if (templateTypeId.equals(TemplateConstants.TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION)) {
					templateAvailable = false;
				}
			}
			if (templateAvailable) {
				entityIds.add(templateTypeId);
				entityNames.add(templateTypeTitle);
			}

		}
		String jsArrayTemplateFor = CommonUtils.getListJavaScriptArray((ArrayList) entityIds, (ArrayList) entityNames);
		return jsArrayTemplateFor;
	}

	public ActionForward manageFlagMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageFlagMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FLAG_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		MastersForm mastersForm = (MastersForm) actionForm;
		String userId = (String) request.getSession().getAttribute("userId");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_FLAG);
		request.setAttribute("t", NavigationConstants.T_MASTERS);

		String subMode = mastersForm.getSubMode();
		if (MastersConstants.SUB_MODE_GET.equalsIgnoreCase(subMode)) {
			List<SimpleDataObject> flags = CommonUtils.getFlagsList();
			MastersManager mastersManager = new MastersManager();
			String xmlFile = mastersManager.getFlagsInXml(flags, permissionSet, userId);
			request.setAttribute("xmlFile", xmlFile);			
			return mapping.findForward("xmlFile");
		} else if (MastersConstants.SUB_MODE_EDIT.equalsIgnoreCase(subMode)) {

		}
		return mapping.findForward(forward);
	}

	public ActionForward manageFlag(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "editFlag";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FLAG_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		MastersForm mastersForm = (MastersForm) actionForm;
		String flagText = mastersForm.getFlagText();
		String flagId = mastersForm.getFlagId();
		String flagType = mastersForm.getFlagType();
		String flagImage = mastersForm.getFlagImage();
		String userId = (String) request.getSession().getAttribute("userId");
		MastersManager mastersManager = new MastersManager();
		
		if (Utils.isBlankOrNull(flagText)) {
			if(!Utils.isBlankOrNull(flagId)) {
				SimpleDataObject flagData = mastersManager.getFlag(flagId);
				mastersForm.setFlagId(flagData.getString("flagId"));
				mastersForm.setFlagText(flagData.getString("flagText"));
				mastersForm.setFlagImage(flagData.getString("flagImage"));
				mastersForm.setFlagType(flagData.getString("flagType"));
			}	
			setFlagsInRequest(request, userId);
		} else {
			try {				
				if(!Utils.isBlankOrNull(flagId)) {
					mastersManager.modifyFlag(flagId, flagText, flagImage, flagType);
				} else {
					int count = mastersManager.getFlagsCount(userId, flagType);
					int limit = 0;
					if(MastersConstants.FLAG_TYPE_PRIVATE.equalsIgnoreCase(flagType)) {
						limit = Integer.parseInt(GlobalApplicationProperties.getProperty("maximum_no_of_private_flags_allowed"));
					} else {
						limit = Integer.parseInt(GlobalApplicationProperties.getProperty("maximum_no_of_public_flags_allowed"));
					}
					if(limit > count) {
						mastersManager.createFlag(flagId, flagText, flagImage, flagType, userId);
					} else {
						setFlagsInRequest(request, userId);
						ActionErrors actionErrors = new ActionErrors();
						actionErrors.add("flag_master.error.limit_exhausted", new ActionError("flag_master.error.limit_exhausted"));
						saveErrors(request, actionErrors);
						return mapping.findForward(forward);
					}					
				}
				CommonUtils.resetFlags();
				request.setAttribute("update", "1");
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while modifying the flag");
				ActionErrors actionErrors = new ActionErrors();
				actionErrors.add("flag_master.error.modify_flag", new ActionError("flag_master.error.modify_flag"));
				saveErrors(request, actionErrors);
			}
		}		
		return mapping.findForward(forward);
	}
	
	private void setFlagsInRequest(HttpServletRequest request, String userId) {
		MastersManager mastersManager = new MastersManager();
		
		List<String> images = listAllIcons();			
		List<String> flagImages = mastersManager.getExistingFlagImages(userId);
		
		request.setAttribute("images", images);
		request.setAttribute("flagImages", flagImages);		
	}

	private ArrayList<String> listAllIcons(){
		// get all present images
		String iconPath = DocumentConstants.iconsPath;
		File folder = new File(iconPath);
		File[] listOfFiles = folder.listFiles();	    
	    ArrayList<String> images = new ArrayList<String>();
	    for(int i=0; i<listOfFiles.length;i++){
	    	if(listOfFiles[i].isFile()){
	    		String tempImg = listOfFiles[i].getName();
		    	if(!Utils.isBlankOrNull(tempImg)){
		    		images.add(tempImg);
		    	}
	    	}
	    }
	    return images;
	}

	public ActionForward manageRatingMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageRatingMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_RATINGS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_RATINGS);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}

	public ActionForward getRatings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				RatingsManager ratingsManager = new RatingsManager();
				ArrayList<RatingsData> ratings = CommonUtils.getRatings();
				xmlFile = ratingsManager.getXMLForRatings(ratings);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting ratings xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward deleteRating(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				RatingsManager ratingsManager = new RatingsManager();
				String ratingId = mastersForm.getRatingId();
				int noOfActiveForms = ratingsManager.getCountOfActiveFeedbackFormForRating(ratingId);
				if (noOfActiveForms == 0) {
					// delete rating
					ratingsManager.deleteRating(ratingId);
					CommonUtils.populateRatings();
					xmlFile = Utils.getXMLForIds(ratingId);
				} else {
					xmlFile = Utils.getXMLForError(null);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting ratings xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteFlag(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String flagId = mastersForm.getFlagId();
				
				MastersManager mastersManager = new MastersManager();
				mastersManager.deleteFlagFromDB(flagId);
				CommonUtils.resetFlags();
				xmlFile = Utils.getXMLForIds(flagId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting ratings xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward addRating(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addRating";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_RATINGS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ActionErrors errors = new ActionErrors();
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			MastersForm mastersForm = (MastersForm) actionForm;
			String ratingId = mastersForm.getRatingId();
			RatingsManager ratingsManager = new RatingsManager();
			RatingUtils ratingUtils = new RatingUtils();
			ArrayList<RatingFieldsData> ratingFields = ratingUtils.getRatingFieldsListFromString(mastersForm.getRatingFieldsString());

			if (Utils.isBlankOrNull(ratingId)) {
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					// submitted
					validateRatingsForm(mastersForm, ratingFields, errors);
					if (errors.size() == 0) {
						ratingsManager.addRating(mastersForm.getRatingTitle(), userId, ratingFields);
						CommonUtils.populateRatings();
						forward = "closeModalCall";
					}
				}
			} else {
				// edit existing
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					// submitted
					validateRatingsForm(mastersForm, ratingFields, errors);
					if (errors.size() == 0) {
						// update rating
						try {
							ratingsManager.updateRating(ratingId, mastersForm.getRatingTitle(), ratingFields);
							CommonUtils.populateRatings();
							forward = "closeModalCall";
						} catch (Exception e) {
							errors.add("", new ActionError("master_add_rating.error.can_not_delete_field"));
						}
					}
				} else {
					// not submit, get the data from DB
					RatingsData ratingsData = ratingsManager.getRatingsData(ratingId);
					ratingFields = ratingsData.getRatingFields();
					mastersForm.setRatingTitle(ratingsData.getRatingTitle());
				}
			}
			// construct rating data
			request.setAttribute("ratingFields", ratingFields);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		saveErrors(request, errors);
		return mapping.findForward(forward);
	}

	private void validateRatingsForm(MastersForm mastersForm, ArrayList<RatingFieldsData> ratingFields, ActionErrors errors) {
		if (Utils.isBlankOrNull(mastersForm.getRatingTitle())) {
			errors.add("", new ActionError("master_add_rating.error.enter_rating_name"));
		}
		if (ratingFields == null || ratingFields.size() == 0) {
			errors.add("", new ActionError("master_add_rating.error.enter_options"));
		} else {
			for (int i = 0; i < ratingFields.size(); i++) {
				if (Utils.isBlankOrNull(ratingFields.get(i).getRatingFieldDesc())) {
					errors.add("", new ActionError("master_add_rating.error.enter_option_desc"));
					break;
				}
			}
		}

	}

	private void validateMultipleSelectsForm(MastersForm mastersForm, ArrayList<MultipleSelectFieldsData> multipleSelectFields, ActionErrors errors) {
		if (Utils.isBlankOrNull(mastersForm.getMultipleSelectTitle())) {
			errors.add("", new ActionError("master_add_multiple_select.error.enter_multiple_select_name"));
		}
		if (multipleSelectFields == null || multipleSelectFields.size() == 0) {
			errors.add("", new ActionError("master_add_multiple_select.error.enter_options"));
		} else {
			for (int i = 0; i < multipleSelectFields.size(); i++) {
				if (Utils.isBlankOrNull(multipleSelectFields.get(i).getSelectFieldDesc())) {
					errors.add("", new ActionError("master_add_multiple_select.error.enter_option_desc"));
					break;
				}
			}
		}

	}
	
	public ActionForward manageFeedbackFieldsCategoriesMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageFeedbackFieldsCategoriesMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FIELDS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_FEEDBACK_FIELDS);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display sources", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getFeedbackFieldCategories(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
				ArrayList<FeedbackFieldCategoryData> feedbackFieldCategories = feedbackFieldsManager.getActiveFeedbackCategories(null);
				xmlFile = feedbackFieldsManager.getFeedbackFieldCategoriesInXML(feedbackFieldCategories);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting ratings xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward addFeedbackFieldCategory(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addFeedbackFieldCategory";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FIELDS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ActionErrors errors = new ActionErrors();
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			String categoryId = mastersForm.getFeedbackFieldCategoryId();

			if (Utils.isBlankOrNull(categoryId)) {
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					validateFeedbackCategoryForm(mastersForm, errors);
					if (errors.size() == 0) {
						FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
						feedbackFieldsManager.AddFeedbackCategory(mastersForm.getFeedbackFieldCategory(),mastersForm.getFieldCategoryIsSummary());
						forward = "closeModalCall";
					}
				}
			} else {
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					validateFeedbackCategoryForm(mastersForm, errors);
					if (errors.size() == 0) {
						FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
						feedbackFieldsManager.UpdateFeedbackCategory(mastersForm.getFeedbackFieldCategory(),mastersForm.getFieldCategoryIsSummary(), categoryId);
						forward = "closeModalCall";
					}
				} else {
					// not submit, get the data from DB
					FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
					FeedbackFieldCategoryData feedbackFieldCategoryData = feedbackFieldsManager.getFeedbackCategoryData(categoryId);
					mastersForm.setFeedbackFieldCategory(feedbackFieldCategoryData.getFeedbackFieldCategory());
					mastersForm.setFieldCategoryIsSummary(feedbackFieldCategoryData.getIsSummaryField());
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			errors.add("", new ActionError("master_feedback_fields_categories.error.error"));
		}
		saveErrors(request, errors);
		return mapping.findForward(forward);
	}

	private void validateFeedbackCategoryForm(MastersForm mastersForm, ActionErrors errors) {
		if (Utils.isBlankOrNull(mastersForm.getFeedbackFieldCategory())) {
			errors.add("", new ActionError("master_feedback_fields_categories.error.enter_category"));
		}
	}

	public ActionForward deleteFeedbackFieldCategory(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String categoryId = mastersForm.getFeedbackFieldCategoryId();
				FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
				feedbackFieldsManager.deleteFeedbackCategory(categoryId);
				xmlFile = Utils.getXMLForIds(categoryId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward manageFeedbackFieldsMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageFeedbackFieldsMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FIELDS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_FEEDBACK_FIELDS);
			request.setAttribute("t", NavigationConstants.T_MASTERS);
			String categoryId = mastersForm.getFeedbackFieldCategoryId();
			FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
			FeedbackFieldCategoryData feedbackFieldCategoryData = feedbackFieldsManager.getFeedbackCategoryData(categoryId);
			mastersForm.setFeedbackFieldCategory(feedbackFieldCategoryData.getFeedbackFieldCategory());
			mastersForm.setFeedbackFieldCategoryId(feedbackFieldCategoryData.getFeedbackFieldCategoryId());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display sources", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getFeedbackFields(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String categoryId = mastersForm.getFeedbackFieldCategoryId();
				FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
				ArrayList<FeedbackFieldData> feedbackFields = feedbackFieldsManager.getActiveFeedbackFieldsForCategory(categoryId);
				xmlFile = feedbackFieldsManager.getFeedbackFieldsInXML(feedbackFields);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward addFeedbackFields(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addFeedbackFields";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_FEEDBACK_FIELDS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ActionErrors errors = new ActionErrors();
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			String categoryId = mastersForm.getFeedbackFieldCategoryId();
			String feedbackFieldId = mastersForm.getFeedbackFieldId();

			if (Utils.isBlankOrNull(feedbackFieldId)) {
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					validateFeedbackFieldForm(mastersForm, errors);
					if (errors.size() == 0) {
						FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
						feedbackFieldsManager.AddFeedbackField(categoryId, mastersForm.getFeedbackFieldTitle(), mastersForm.getFeedbackFieldDesc(),mastersForm.getFeedbackFieldType(),mastersForm.getApplicantFieldId());
						forward = "closeModalCall";
					}
				}
			} else {
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					validateFeedbackFieldForm(mastersForm, errors);
					if (errors.size() == 0) {
						FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
						feedbackFieldsManager.UpdateFeedbackField(feedbackFieldId, mastersForm.getFeedbackFieldTitle(), mastersForm.getFeedbackFieldDesc(),mastersForm.getFeedbackFieldType(),mastersForm.getApplicantFieldId());
						forward = "closeModalCall";
					}
				} else {
					// not submit, get the data from DB
					FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
					FeedbackFieldData feedbackFieldData = feedbackFieldsManager.getFeedbackFieldData(feedbackFieldId);
					mastersForm.setFeedbackFieldTitle(feedbackFieldData.getFeedbackFieldTitle());
					mastersForm.setFeedbackFieldDesc(feedbackFieldData.getFeedbackFieldDesc());
					mastersForm.setFeedbackFieldType(feedbackFieldData.getFeedbackFieldType());
					mastersForm.setApplicantFieldId(feedbackFieldData.getApplicantFieldId());
				}
			}
			String applicantFieldJsArray = ApplicantUtils.getFeedbackFormApplicantFieldsJSArray();
			request.setAttribute("applicantFieldJsArray", applicantFieldJsArray);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			errors.add("", new ActionError("master_feedback_fields.error.unknown"));
		}
		saveErrors(request, errors);
		return mapping.findForward(forward);
	}

	private void validateFeedbackFieldForm(MastersForm mastersForm, ActionErrors errors) {
		if (Utils.isBlankOrNull(mastersForm.getFeedbackFieldTitle())) {
			errors.add("", new ActionError("master_feedback_fields.error.enter_field_title"));
		}
	}

	public ActionForward deleteFeedbackField(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String feedbackFieldId = mastersForm.getFeedbackFieldId();
				FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
				feedbackFieldsManager.deleteFeedbackField(feedbackFieldId);
				xmlFile = Utils.getXMLForIds(feedbackFieldId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	
	
	public ActionForward manageInboxFoldersMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageInboxFoldersMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_INBOX_FOLDERS);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}

	public ActionForward manageExcelImport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}		
		String forward = "manageExcelImport";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		MastersForm mastersForm = (MastersForm) actionForm;
		request.setAttribute("masterType", MastersConstants.MASTER_EXCEL_IMPORT);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		String sessionId = "" + System.currentTimeMillis();
		mastersForm.setSessionId(sessionId);
		return mapping.findForward(forward);
	}
	
	public ActionForward getInboxFolders(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersManager mastersManager = new MastersManager();
				List<InboxFolderData> folders = mastersManager.getAllInboxFolders();
				xmlFile = mastersManager.getXMLForInboxFolders(folders);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting locations xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward addInboxFolder(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addInboxFolder";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			String folderId = mastersForm.getFolderId();
			if(!Utils.isBlankOrNull(folderId)) {
				MastersManager mastersManager = new MastersManager();
				InboxFolderData data = mastersManager.getInboxFolderByInboxFolderId(folderId);
				mastersForm.setFolderId(data.getFolderId());
				mastersForm.setFolderName(data.getFolderName());
				mastersForm.setSystemDefined(data.getSystemDefined());
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while populating data for add/ edit location", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward saveInboxFolder(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addInboxFolder";
		String userId = (String) request.getSession().getAttribute("userId");
		
		MastersForm mastersForm = (MastersForm) actionForm;
		String folderId = mastersForm.getFolderId();
		String folderName = mastersForm.getFolderName();
		
		try {
			MastersManager mastersManager = new MastersManager();
			InboxFolderData folder = mastersManager.getInboxFolderByName(folderName);
			ActionErrors errors = validateInboxFolderData(folderId, folder);
			if(errors != null && errors.size() > 0) {
				saveErrors(request, errors);
			} else {
				if(!Utils.isBlankOrNull(folderId)) {				
					mastersManager.updateInboxFolder(folderId, folderName);
				} else {
					mastersManager.addInboxFolder(folderName, userId);
				}
				request.setAttribute("update", "1");
			}			
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while saving folder", e);
			ActionErrors errors = new ActionErrors();
			if(Utils.isBlankOrNull(folderId)) {
				errors.add("master_inbox_folders.error.save_inbox_folder", new ActionError("master_inbox_folders.error.save_inbox_folder"));
			} else {
				errors.add("master_inbox_folders.error.save_inbox_folder", new ActionError("master_inbox_folders.error.save_inbox_folder"));
			}
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}

	private ActionErrors validateInboxFolderData(String folderId, InboxFolderData folder) {
		ActionErrors errors = new ActionErrors();
		if(folder != null) {
			if(!Utils.isBlankOrNull(folderId)) {
				if(!folderId.equalsIgnoreCase(folder.getFolderId())) {
					errors.add("master_inbox_folders.error.inbox_folder_name", new ActionError("master_inbox_folders.error.inbox_folder_name"));
				}
			} else {
				errors.add("master_inbox_folders.error.inbox_folder_name", new ActionError("master_inbox_folders.error.inbox_folder_name"));
			}
		}
		return errors;
	}
	
	public ActionForward deleteInboxFolder(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String folderId = mastersForm.getFolderId();
				MastersManager mastersManager = new MastersManager();
				int noOfEmailsInFolder = mastersManager.getNumberOfEmailsInFolder(folderId);
				if(noOfEmailsInFolder == 0) {
					mastersManager.deleteInboxFolder(folderId);
				} else {
					xmlFile = Utils.getXMLForError();
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting folder", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward manageBudgetGradeMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageBudgetGradeMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_BUDGET_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_GRADE);

			request.setAttribute("t", NavigationConstants.T_MASTERS);
			
			MastersManager mastersManager = new MastersManager();

			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String gradeId = mastersForm.getBudgetGradeId();
				String gradeName = mastersForm.getBudgetGradeName();
				SimpleDataObject gradeDuplicate = mastersManager.getBudgetGradeName(gradeName);
				ActionErrors errors = validateGradeData(gradeId, gradeDuplicate);
				if(errors != null && errors.size() > 0) {
					forward = "addBudgetGrade";
					saveErrors(request, errors);
				}
				else{
						if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
							forward = "addBudgetGrade";
								if (!Utils.isBlankOrNull(gradeName)) {
									mastersManager.addBudgetGradeToDB(gradeName, mastersForm.getDescription(),mastersForm.getHireByDuration());						
									request.setAttribute("update", "1");
								}else{
									mastersForm.setHireByDuration(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEFAULT_HIRE_BY_DURATION_IN_DAYS));
								}
						} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
							forward = "addBudgetGrade";
									if (Utils.isBlankOrNull(gradeName)) {
										SimpleDataObject grade = mastersManager.getBudgetGrade(gradeId);
										mastersForm.setBudgetGradeName(grade.getString("itemName"));
										mastersForm.setDescription(grade.getString("description"));
										mastersForm.setHireByDuration(grade.getString("hireByDuration"));
									} else if (!Utils.isBlankOrNull(gradeId) && !Utils.isBlankOrNull(gradeName)) {
										mastersManager.updateBudgetGradeToDB(gradeId, gradeName, mastersForm.getDescription(),mastersForm.getHireByDuration());						
										request.setAttribute("update", "1");
									}
						}	}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Budget Grade Master exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master.errors.cannot_add_grade");
			errors.add("admin_master.errors.cannot_add_grade", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display branches", e);
		}
		return mapping.findForward(forward);
	}
	
	private ActionErrors validateGradeData(String gradeId, SimpleDataObject gradeName) {
		ActionErrors errors = new ActionErrors();
		if(gradeName != null) {
			if(!Utils.isBlankOrNull(gradeId)) {
				if(!gradeId.equalsIgnoreCase(gradeName.getString("itemName"))) {
					errors.add("master_budget_grades.error.grade_name", new ActionError("master_budget_grades.error.grade_name"));
				}
			} else {
				errors.add("master_budget_grades.error.grade_name", new ActionError("master_budget_grades.error.grade_name"));
			}
		}
		return errors;
	}
	
	public ActionForward manageBudgetGrades(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String budgetGradeId = mastersForm.getBudgetGradeId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList budgetGrades = mastersManager.getAllBudgetGrades();
					xmlFile = mastersManager.getXMLForGrades(budgetGrades);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(budgetGradeId)) {
						mastersManager.deleteBudgetGrade(budgetGradeId);
						xmlFile = Utils.getXMLForIds(budgetGradeId);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("1");
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward moveGradesUpDown(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String gradeId = mastersForm.getBudgetGradeId();
				String gradeRank = mastersForm.getGradeRank();
				String rankUp = mastersForm.getGradeRankUp();
				MastersManager mastersManager = new MastersManager();

				mastersManager.moveGradesUpDown(gradeId,gradeRank,rankUp);
				xmlFile = Utils.getXMLForIds(gradeId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("1");
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward manageBudgetBandMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageBudgetBandMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_BUDGET_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_BAND);

			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();

			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String bandId = mastersForm.getBudgetBandId();
				String bandName = mastersForm.getBudgetBandName();
				
				SimpleDataObject bandDuplicate = mastersManager.getBudgetNameBand(bandName);
				ActionErrors errors = validateBandMasterData(bandId, bandDuplicate);
				if(errors != null && errors.size() > 0) {
					forward = "addBudgetBand";
					saveErrors(request, errors);
				}
				else{
						if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
							forward = "addBudgetBand";
							if (!Utils.isBlankOrNull(bandName)) {
								mastersManager.addBudgetBandToDB(bandName, mastersForm.getDescription());						
								request.setAttribute("update", "1");
							}
						} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
							forward = "addBudgetBand";
							if (Utils.isBlankOrNull(bandName)) {
								SimpleDataObject band = mastersManager.getBudgetBand(bandId);
								mastersForm.setBudgetBandName((String) band.getAttribute("itemName"));
								mastersForm.setDescription((String) band.getAttribute("description"));
								
							} else if (!Utils.isBlankOrNull(bandId) && !Utils.isBlankOrNull(bandName)) {
								mastersManager.updateBudgetBandToDB(bandId, bandName, mastersForm.getDescription());						
								request.setAttribute("update", "1");
							}
					}		}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Budget Band Master exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master.errors.cannot_add_band");
			errors.add("admin_master.errors.cannot_add_band", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display branches", e);
		}
		return mapping.findForward(forward);
	}
	
	private ActionErrors validateBandMasterData(String bandId, SimpleDataObject bandName) {
		ActionErrors errors = new ActionErrors();
		if(bandName != null) {
			if(!Utils.isBlankOrNull(bandId)) {
				if(!bandId.equalsIgnoreCase(bandName.getString("itemName"))) {
					errors.add("master_budget_bands.error.band_name", new ActionError("master_budget_bands.error.band_name"));
				}
			} else {
				errors.add("master_budget_bands.error.band_name", new ActionError("master_budget_bands.error.band_name"));
			}
		}
		return errors;
	}
	
	public ActionForward manageBudgetBands(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String budgetBandId = mastersForm.getBudgetBandId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					ArrayList budgetBands = mastersManager.getAllBudgetBands();
					xmlFile = mastersManager.getXMLForBands(budgetBands);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(budgetBandId)) {
						mastersManager.deleteBudgetBand(budgetBandId);
						xmlFile = Utils.getXMLForIds(budgetBandId);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing budget Bands", e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("1");
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward manageMultipleSelectMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageMultipleSelectMaster";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		//permissions[1] = PermissionConstants.PERMISSION_MULTIPLE_SELECTS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		request.setAttribute("masterType", MastersConstants.MASTER_TYPE_MULTIPLE_SELECT);
		request.setAttribute("t", NavigationConstants.T_MASTERS);
		return mapping.findForward(forward);
	}
	
	public ActionForward getMultipleSelects(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MultipleSelectsManager multipleSelectsManager = new MultipleSelectsManager();
				 ArrayList<MultipleSelectsData> multipleSelects = CommonUtils.getMultipleSelects();
				xmlFile = multipleSelectsManager.getXMLForMultipleSelects(multipleSelects);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting multiple selects xml", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward addMultipleSelect(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addMultipleSelect";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_MULTIPLE_SELECTS_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ActionErrors errors = new ActionErrors();
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			MastersForm mastersForm = (MastersForm) actionForm;
			String multipleSelectId = mastersForm.getMultipleSelectId();
			MultipleSelectsManager multipleSelectsManager = new MultipleSelectsManager();
			MultipleSelectUtils multipleSelectUtils = new MultipleSelectUtils();
			ArrayList<MultipleSelectFieldsData> multipleSelectFields = multipleSelectUtils.getMultipleSelectFieldsListFromString(mastersForm.getMultipleSelectFieldsString());
			
			if (Utils.isBlankOrNull(multipleSelectId)) {
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					validateMultipleSelectsForm(mastersForm, multipleSelectFields, errors);
					if (errors.size() == 0) {
						multipleSelectsManager.addMultipleSelect(mastersForm.getMultipleSelectTitle(), userId, multipleSelectFields);
						CommonUtils.populateMultipleSelects();
						forward = "closeModalCall";
					}
				}
			}else{
				if (!Utils.isBlankOrNull(mastersForm.getSubmitted())) {
					validateMultipleSelectsForm(mastersForm, multipleSelectFields, errors);
					if (errors.size() == 0) {
						try{
							multipleSelectsManager.updateMultipleSelect(multipleSelectId, mastersForm.getMultipleSelectTitle(), multipleSelectFields);
							CommonUtils.populateMultipleSelects();
							forward = "closeModalCall";
						}catch(Exception e){
							errors.add("", new ActionError("master_add_multiple_select.error.can_not_delete_field"));
						}
					}
				}else{
					MultipleSelectsData multipleSelectsData = multipleSelectsManager.getMultipleSelectsData(multipleSelectId);
					multipleSelectFields=multipleSelectsData.getMultipleSelectFields();
					mastersForm.setMultipleSelectTitle(multipleSelectsData.getSelectTitle());
				}
			}
			
			// construct rating data
			request.setAttribute("multipleSelectFields", multipleSelectFields);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		saveErrors(request, errors);
		return mapping.findForward(forward);
	}
	
	public ActionForward deleteMultipleSelect(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				MultipleSelectsManager multipleSelectsManager = new MultipleSelectsManager();
				String multipleSelectId = mastersForm.getMultipleSelectId();
				int noOfActiveForms = multipleSelectsManager.getCountOfActiveFeedbackFormForMultipleSelect(multipleSelectId);
				if (noOfActiveForms == 0) {
					// delete rating
					multipleSelectsManager.deleteMultipleSelect(multipleSelectId);
					CommonUtils.populateMultipleSelects();
					xmlFile = Utils.getXMLForIds(multipleSelectId);
				} else {
					xmlFile = Utils.getXMLForError(null);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting multiple select", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	 
	public ActionForward manageBusinessUnitMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageBusinessUnitMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_BUSINESS_UNIT_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_BUSINESS_UNIT);

			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();

			String subMode = mastersForm.getSubMode();
			if (!Utils.isBlankOrNull(subMode)) {
				String buId = mastersForm.getBuId();
				String buName = mastersForm.getBuName();

				if (subMode.equals(MastersConstants.SUB_MODE_ADD)) {
					forward = "addBusinessUnit";
					if (!Utils.isBlankOrNull(buName)) {
						mastersManager.addBusinessUnitToDB(buName, mastersForm.getDescription());						
						request.setAttribute("update", "1");
					}
				} else if (subMode.equals(MastersConstants.SUB_MODE_EDIT)) {
					forward = "addBusinessUnit";
					if (Utils.isBlankOrNull(buName)) {
						SimpleDataObject bu = mastersManager.getBusinessUnit(buId);
						mastersForm.setBuName((String) bu.getAttribute("buName"));
						mastersForm.setDescription((String) bu.getAttribute("buDescription"));
						
					} else if (!Utils.isBlankOrNull(buId) && !Utils.isBlankOrNull(buName)) {
						mastersManager.updateBusinessUnitToDB(buId, buName, mastersForm.getDescription());						
						request.setAttribute("update", "1");
					}
				}
			}
		} catch (MasterExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master.errors.cannot_add_band");
			errors.add("admin_master.errors.cannot_add_band", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward manageBusinessUnit(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String buId = mastersForm.getBuId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					List<SimpleDataObject> buNames = mastersManager.getAllBusinessUnitData();
					xmlFile = mastersManager.getXMLForBusinessUnit(buNames);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(buId)) {
						mastersManager.deleteBusinessUnitFromDB(buId);
						xmlFile = Utils.getXMLForIds(buId);
						//CommonUtils.setDeptIds(null);
						//CommonUtils.setDeptNames(null);
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing departments", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward manageCostCenterMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageCostCenterMaster";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_MASTERS;
		permissions[1] = PermissionConstants.PERMISSION_COST_CENTER_MASTER;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			MastersForm mastersForm = (MastersForm) actionForm;
			request.setAttribute("masterType", MastersConstants.MASTER_TYPE_COST_CENTER);

			request.setAttribute("t", NavigationConstants.T_MASTERS);
			MastersManager mastersManager = new MastersManager();

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
		} catch (MasterExistException e) {
			TPLogger.getLogger().error("The Cost Center Master exists.", e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("admin_master.errors.cannot_add_band");
			errors.add("admin_master.errors.cannot_add_band", error);
			saveErrors(request, errors);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward manageCostCenter(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm) actionForm;
				String subMode = mastersForm.getSubMode();
				String costCenterId = mastersForm.getCostCenterId();
				MastersManager mastersManager = new MastersManager();
				if (subMode.equals(MastersConstants.SUB_MODE_GET)) {
					List<SimpleDataObject> costCenterNames = mastersManager.getAllCostCenterData();
					xmlFile = mastersManager.getXMLForCostCenter(costCenterNames);
				} else if (subMode.equals(MastersConstants.SUB_MODE_DELETE)) {
					if (!Utils.isBlankOrNull(costCenterId)) {
						mastersManager.deleteCostCenterFromDB(costCenterId);
						xmlFile = Utils.getXMLForIds(costCenterId);
						//CommonUtils.setCostCenterIds(null);
						//CommonUtils.setCostCenterNames(null);
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
	
	public ActionForward getAllSkillCategories(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		
		MastersManager mastersManager = new MastersManager();
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {						
				List skillCategories = mastersManager.getSkillCategories();
				xmlFile = SkillsUtils.getXMLForSkillCategoryFilters(skillCategories);				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing skills", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getAllSkills(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";		
		PositionManager positionManager = new PositionManager();		
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				MastersForm mastersForm = (MastersForm)actionForm;
				String skillCategoryIds = mastersForm.getSkillCategoryId();
				MastersManager mastersManager = new MastersManager();
				List<SkillData> skills = new ArrayList<SkillData>();
				if(!Utils.isBlankOrNull(skillCategoryIds)){
					skills = mastersManager.getSkillListByCategoryIds(skillCategoryIds);
					xmlFile = SkillsUtils.getXmlForSkillsMaster(skills);
				}else{
					xmlFile = positionManager.getSkillsXML();
				}				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing skills", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	
	public ActionForward excludeSplChar(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";		
				
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String inpuTest=request.getParameter("validChar");
				String objId=request.getParameter("objId");				
				Boolean bool = Utils.hasValidCharacter(inpuTest);
				xmlFile = Utils.getXMLForTagName("validChar",bool.toString()+"|"+objId);				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while managing skills", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
}
