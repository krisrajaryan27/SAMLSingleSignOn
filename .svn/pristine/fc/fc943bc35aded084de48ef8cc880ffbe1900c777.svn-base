/**
 * 
 */
package com.talentPool.admin.action;

import java.io.StringWriter;
import java.security.KeyPair;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xml.sax.SAXException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.talentPool.admin.dataobject.DuplicateSettingsData;
import com.talentPool.admin.dataobject.HierarchyData;
import com.talentPool.admin.dataobject.PermissionData;
import com.talentPool.admin.dataobject.ReportLevelData;
import com.talentPool.admin.form.AdminForm;
import com.talentPool.admin.manager.AdminManager;
import com.talentPool.admin.manager.DuplicatePositionSettings;
import com.talentPool.admin.manager.DuplicateSettings;
import com.talentPool.admin.manager.HierarchyManager;
import com.talentPool.admin.manager.WebsiteScreenSettingsManager;
import com.talentPool.applicant.dataobject.ImportFieldData;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.audit.action.AuditAction;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.RSADecryptionException;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.customReports.dataobject.CustomReportData;
import com.talentPool.customReports.manager.CustomReportManager;
import com.talentPool.department.manager.DepartmentManager;
import com.talentPool.encryption.JCryptionUtil;
import com.talentPool.inbox.scheduler.InboxScheduler;
import com.talentPool.ldap.LDAPQueryProcessor;
import com.talentPool.ldap.constants.LDAPConstants;
import com.talentPool.ldap.dataobject.LDAPServerData;
import com.talentPool.ldap.dataobject.LDAPUserData;
import com.talentPool.ldap.manager.LDAPManager;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.LocationManager;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.masters.utils.LocationUtils;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.dataobject.PositionFieldData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.positions.utils.PositionUtils;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.manager.ReportDesignManager;
import com.talentPool.reportDesign.utils.XmlUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.CustomizedReportData;
import com.talentPool.reports.dataobject.ReportIdName;
import com.talentPool.reports.manager.CustomizedReportManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.dataobject.RoleData;
import com.talentPool.user.exception.EmailExistException;
import com.talentPool.user.exception.SourceExistException;
import com.talentPool.user.exception.SourceOrEmployeeCodeExistException;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.user.manager.UserManager;

/**
 * @author shivprasad
 * 
 */
public class AdminAction extends TPDispatchAction {

	public ActionForward manageUsers(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageUsers";
		try {		
			Integer[] permissions = new Integer[2];
			permissions[0] = PermissionConstants.PERMISSION_ADMIN;
			permissions[1] = PermissionConstants.PERMISSION_MANAGE_USERS;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_MANAGE_USERS);
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("jsArrayRole", getJSArrayRole());
			UUID token = UUID.randomUUID();
			request.getSession().setAttribute("changeUserStatusTokenId", token.toString());

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward admin(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "admin";
		try {
			Integer[] permissions = new Integer[1];
			permissions[0] = PermissionConstants.PERMISSION_ADMIN;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
			request.setAttribute("t", NavigationConstants.T_ADMIN);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching User List", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getUserXMLFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		try {
			Integer[] permissions = new Integer[2];
			permissions[0] = PermissionConstants.PERMISSION_ADMIN;
			permissions[1] = PermissionConstants.PERMISSION_MANAGE_USERS;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			AdminForm adminForm = (AdminForm) actionForm;
			String filterByName = adminForm.getFilterByName();
			String filterByRole = adminForm.getFilterByRole();
			String filterByUser = adminForm.getFilterByUser();
			String filterByEmpCode = adminForm.getFilterByEmpCode();
			
			AdminManager adminManager = new AdminManager();
			ArrayList users = adminManager.getUsers(filterByName, filterByUser, filterByRole, filterByEmpCode);
			String xmlFile = adminManager.getXMLForUsers(users);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching User xmlFile", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward deleteUser(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				String loggedInUser = (String) request.getSession().getAttribute("userId");
				AdminForm adminForm = (AdminForm) actionForm;
				
				String deleteUserTokenId= (String)request.getSession().getAttribute("changeUserStatusTokenId");
				if (Utils.isBlankOrNull(deleteUserTokenId) || !deleteUserTokenId.equals(adminForm.getChangeUserStatusTokenId())){
					SessionManager.invalidateSession(request, response, loggedInUser);
					try {
						SessionManager.sessionExpireRedirect(mapping, actionForm, request, response, this, false);
						TPLogger.getLogger().error("Invalid CSRFToken while deleting user. Session invalidated because of suspicious activity.");
						return null;
					} catch (Exception e) {
						TPLogger.getLogger().error("Error While validating session", e);
					} 
				}
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USERS;
				if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
				}
				
				String userId = adminForm.getUserId();
				AdminManager adminManager = new AdminManager();
				int noOfAssociatedPositions = adminManager.getNoOfPositionsForUser(userId);
				if (noOfAssociatedPositions > 0) {
					throw new Exception("Can not delete this user as there are active positions available");
				}
				HierarchyManager hierarchyManager = new HierarchyManager();
				String parentId = hierarchyManager.getParentId(userId);
				if (!Utils.isBlankOrNull(parentId)) {
					hierarchyManager.removeUserFromHierarchy(parentId, userId);								
				}				
				
				AuditAction auditAction = new AuditAction();
				String clientIpAddr = getClientIpAddr(request);
				auditAction.insertAuditInfo(TPLabels.getLabel("common.profile"), AuditConstants.TYPE_DELETED, userId,
						AuditConstants.AUDIT_USER, loggedInUser, null, null, null, true, clientIpAddr);
				
				adminManager.deleteUser(userId, adminForm.getRoleId(), adminForm.getUserSourceId());
				
				xmlFile = Utils.getXMLForIds(userId);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting user", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);

	}

	public ActionForward changeStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		AdminForm adminForm = (AdminForm) actionForm;
		String userId = adminForm.getUserId();
		String userStatus = adminForm.getUserStatus();
		AdminManager adminManager = new AdminManager();
		ActionErrors errors = new ActionErrors();
		String loggedInUser = (String) request.getSession().getAttribute("userId");
		if (!Utils.isBlankOrNull(userId) && !Utils.isBlankOrNull(userStatus)) {
			try {
				String changeUserStatusTokenId= (String)request.getSession().getAttribute("changeUserStatusTokenId");
				if (Utils.isBlankOrNull(changeUserStatusTokenId) || !changeUserStatusTokenId.equals(adminForm.getChangeUserStatusTokenId())){
					SessionManager.invalidateSession(request, response, loggedInUser);
					try {
						SessionManager.sessionExpireRedirect(mapping, actionForm, request, response, this, false);
						TPLogger.getLogger().error("Invalid CSRFToken while deleting user. Session invalidated because of suspicious activity.");
						return null;
					} catch (Exception e) {
						TPLogger.getLogger().error("Error While validating session", e);
					} 
				}
				int status = Integer.parseInt(userStatus);
				// Change Status Only When Valid Status Code is received
				if (status == UserConstants.ACTIVE || status == UserConstants.DEACTIVE) {
					if (status == UserConstants.ACTIVE) {
						int noOfAssociatedPositions = adminManager.getNoOfPositionsForUser(userId);
						if (noOfAssociatedPositions > 0) {
							errors.add("add_user.errors.cannot_disable", new ActionError("add_user.errors.cannot_disable",TPLabels.getLabel("common.positions")));
							request.setAttribute(Globals.ERROR_KEY, errors);
						} else {
							status = UserConstants.DEACTIVE;
						}
					} else if (status == UserConstants.DEACTIVE) {
						if(adminManager.isUserSourceBlackListed(userId)){
							errors.add("add_user.errors.sourceblacklisted", new ActionError("add_user.errors.sourceblacklisted"));
							request.setAttribute(Globals.ERROR_KEY, errors);
							throw new Exception("add_user.errors.sourceblacklisted");
						}else{
							status = UserConstants.ACTIVE;	
						}
					}
					adminManager.changeUserStatus(userId, status);
					
					String stausStr = "";
					if (status == UserConstants.ACTIVE ) {
						stausStr = TPLabels.getLabel("common.active");
					}else{
						stausStr = TPLabels.getLabel("common.inactive");
					}
					AuditAction auditAction = new AuditAction();
					String clientIpAddr = getClientIpAddr(request);
					auditAction.insertAuditInfo(TPLabels.getLabel("common.status") + " " + TPLabels.getLabel("common.to") 
							+ " " + stausStr, AuditConstants.TYPE_MODIFIED, userId, AuditConstants.AUDIT_USER, 
							loggedInUser, null, null, null, true, clientIpAddr);
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while updating status for user", e);
			}
		}
		return manageUsers(mapping, actionForm, request, response);
	}

	public ActionForward addUser(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}

		AdminForm adminForm = (AdminForm) actionForm;
		String forward = "addUser";
		String userId = adminForm.getUserId();
		
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_MANAGE_USERS;
		if(!isUserAuthorized(request, 0, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			LoginData loginData = new LoginData("", "");
			if (!Utils.isBlankOrNull(userId)) {
				LoginManager loginManager = new LoginManager();
				loginData = loginManager.getUser(userId);
			}
			AdminManager adminManager = new AdminManager();
			ArrayList<RoleData> roles = adminManager.getAllRoles();

			// Populate AdminForm for display fields
			if (adminForm.getMode().equals("addUser")) {
				adminForm.setUserId(loginData.getUserId());
				adminForm.setUserName(loginData.getUserName());
				adminForm.setFirstName(loginData.getFirstName());
				adminForm.setLastName(loginData.getLastName());
				adminForm.setEmail(loginData.getEmail());
				adminForm.setHomePhone(loginData.getHomePhone());
				adminForm.setCellPhone(loginData.getCellPhone());
				adminForm.setUserSourceId(loginData.getUserSourceId());
				adminForm.setIsUserLdapSetting(loginData.getIsUserLdapSetting());
				adminForm.setSelectedRoleIds(loginData.getRoleId());
				adminForm.setEmployeeCode(loginData.getEmployeeCode());
				adminForm.setDepartmentId(loginData.getDepartmentId());
				adminForm.setSubDepartmentId(loginData.getSubDepartmentId());
				adminForm.setSubSubDepartmentId(loginData.getSubSubDepartmentId());
				adminForm.setSub3DepartmentId(loginData.getSub3DepartmentId());
				adminForm.setSub4DepartmentId(loginData.getSub4DepartmentId());
				adminForm.setLocationId(loginData.getLocationId());
				adminForm.setGradeId(loginData.getGradeId());
				adminForm.setBandId(loginData.getBandId());
				adminForm.setBuId(loginData.getBuId());
				adminForm.setCostCenterId(loginData.getCostCenterId());
				adminForm.setBusinessUnitXML(CommonUtils.getXMLForBusinessUnit());
				adminForm.setCostCenterXML(CommonUtils.getXMLForCostCenter());
				
				ArrayList employeeSource = adminManager.getEmployeeSource();
				adminForm.setJsArrayEmployeeSource(CommonUtils.getListJavaScriptArrayWithProperties(employeeSource, "itemId", "itemName"));
			}
			// remove role vendor if vendor module is not available
			for (int i = 0; roles != null && i < roles.size(); i++) {
				RoleData role = (RoleData) roles.get(i);
				if (!ModuleSet.isMODULE_VENDOR() && role.getRoleId() == UserConstants.ROLE_VENDOR) {
					roles.remove(i);
					i--;
				}
				if (!Utils.isBlankOrNull(loginData.getUserId()) && loginData.getRoleId().equals(String.valueOf(UserConstants.ROLE_EMPLOYEE)) && String.valueOf(role.getRoleId()).equals(String.valueOf(UserConstants.ROLE_VENDOR))) {
					roles.remove(i);
					i--;
				}
			}
			// check if valid LDAP user is logged in
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) {
				String validLdapUser = (String) request.getSession().getAttribute("validLdapUser");
				if (Utils.isBlankOrNull(validLdapUser)) {					
					errors.add("add_user.errors.ldap_user_not_loggedin", new ActionError("add_user.errors.ldap_user_not_loggedin"));
					request.setAttribute(Globals.ERROR_KEY, errors);
				}
			}
			
			// pass users in hierarchy
			DepartmentManager departmentManager = new DepartmentManager();
			LocationUtils locationUtils = new LocationUtils();
			HierarchyManager hierarchyManager = new HierarchyManager();
			SimpleDataObject sDo = adminManager.getInboxSettings();
			ArrayList<HierarchyData> managedUser = hierarchyManager.getManagedUser();
			String jsManagedUser = CommonUtils.getListJavaScriptArrayWithProperties(managedUser, "userId", "name");
			request.setAttribute("jsManagedUser", jsManagedUser);
			request.setAttribute("jsArrayDepartments", departmentManager.getJSArrayDepartments());
			request.setAttribute("jsArraySubDepartments", departmentManager.getJSArraySubDepartments(adminForm.getDepartmentId()));
			request.setAttribute("jsArraySubSubDepartments", departmentManager.getJSArraySubDepartments(adminForm.getSubDepartmentId()));
			request.setAttribute("jsArraySub3Departments", departmentManager.getJSArraySubDepartments(adminForm.getSubSubDepartmentId()));
			request.setAttribute("jsArraySub4Departments", departmentManager.getJSArraySubDepartments(adminForm.getSub3DepartmentId()));
			request.setAttribute("jsArrayLocations", locationUtils.getJSArrayLocations());
			request.setAttribute("jsArrayGrades", CommonUtils.getJSArrayGrades());
			request.setAttribute("jsArrayBands", CommonUtils.getJSArrayBands());
			
			//Added for Asian Paints BEGIN
			LocationManager locationManager = new LocationManager();
			request.setAttribute("jsArrayLocations", locationManager.getJSArrayLocations());
			request.setAttribute("jsArraySubLocations", locationManager.getJSArrayRegions(adminForm.getpSubLocationId()));
			request.setAttribute("jsArraySubSubLocations", locationManager.getJSArraySubLocations(adminForm.getpSubSubLocationId()) );
			//Added for Asian Paints END
			
			if(sDo!=null && !Utils.isBlankOrNull(sDo.getString("inboxDisplayName"))){
				request.setAttribute("companyName", sDo.getString("inboxDisplayName"));	
			}
			else{
				request.setAttribute("companyName", "");
			}
			request.setAttribute("roles", roles);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);		
		}

		return mapping.findForward(forward);
	}

	public ActionForward saveUser(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		
		AdminForm adminForm = (AdminForm) actionForm;
		
		String loginUser = (String) request.getSession().getAttribute("userId");
		if (!isTokenValid(request)){
			SessionManager.invalidateSession(request, response, loginUser);
			try {
				SessionManager.sessionExpireRedirect(mapping, actionForm, request, response, this, false);
				TPLogger.getLogger().error("Invalid CSRFToken while saving user. Session invalidated because of suspicious activity.");
				return null;
			} catch (Exception e) {
				TPLogger.getLogger().error("Error While validating session", e);
			} 
		}
		AuditAction auditAction = new AuditAction();
		// If userId is set then submit Eddited else create new User
		String userId = adminForm.getUserId();
		String prevSourceId = adminForm.getUserSourceId();

		// Do the validations here
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			AdminManager adminManager = new AdminManager();
			if (adminForm.getUserName().length() < UserConstants.REQUIRED_MIN_USERNAME_LENGTH || adminForm.getUserName().length() > UserConstants.REQUIRED_MAX_USERNAME_LENGTH) {
				errors.add("add_user.errors.short_username", new ActionError("add_user.errors.short_username"));
			}
			if (adminForm.getUserName().indexOf(" ") != -1) {
				errors.add("add_user.errors.username_nospace", new ActionError("add_user.errors.username_nospace"));
			}
			if (Utils.isBlankOrNull(userId)) {
				// Following checks will be done only for new records
				// else if (!Utils.isValidPattern(adminForm.getUserName(),
				// Utils.regUserName)) {
				// errors.add("add_user.errors.invalid_username", new
				// ActionError("add_user.errors.invalid_username"));
				// }
				KeyPair keys = null;
				int rnd = adminForm.getRnd();
				keys = (KeyPair) request.getSession().getAttribute("keys");
				String password = adminForm.getPassword();
				String reTypePassword = adminForm.getReTypedPassword();
				if(!Utils.isBlankOrNull(password) && !Utils.isBlankOrNull(reTypePassword)) {
					// RSA decryption
					password = JCryptionUtil.decrypt(password, keys);
					reTypePassword = JCryptionUtil.decrypt(reTypePassword, keys);
					// Regular decryption for encryt.js
					reTypePassword = EncryptionUtils.decryptPassword(reTypePassword, rnd);
					password = EncryptionUtils.decryptPassword(password, rnd);
					adminForm.setPassword(password);
					adminForm.setReTypedPassword(reTypePassword);
				}
				if (adminForm.getPassword().length() < UserConstants.REQUIRED_MIN_PASSWORD_LENGTH || adminForm.getPassword().length() > UserConstants.REQUIRED_MAX_PASSWORD_LENGTH) {
					errors.add("add_user.errors.short_password", new ActionError("add_user.errors.short_password"));
				} else if (!adminForm.getPassword().equals(adminForm.getReTypedPassword())) {
					errors.add("add_user.errors.retype_password", new ActionError("add_user.errors.retype_password"));
				}
			}
			if (adminForm.getFirstName().trim().length() < 1) {
				errors.add("add_user.errors.fname_required", new ActionError("add_user.errors.fname_required"));
			} else if (!Utils.isValidPattern(adminForm.getFirstName(), Utils.regName)) {
				errors.add("add_user.errors.invalid_fname", new ActionError("add_user.errors.invalid_fname"));
			}
			if (adminForm.getLastName().trim().length() < 1) {
				errors.add("add_user.errors.lname_required", new ActionError("add_user.errors.lname_required"));
			} else if (!Utils.isValidPattern(adminForm.getLastName(), Utils.regName)) {
				errors.add("add_user.errors.invalid_lname", new ActionError("add_user.errors.invalid_lname"));
			}

			if (!Utils.isValidPattern(adminForm.getEmail(), Utils.regEmail)) {
				errors.add("add_user.errors.invalid_email", new ActionError("add_user.errors.invalid_email"));
			}
			if (Utils.isBlankOrNull(adminForm.getSelectedRoleIds())) {
				errors.add("add_user.errors.role_required", new ActionError("add_user.errors.role_required"));
			}
			if (!(""+UserConstants.ROLE_VENDOR).equals(adminForm.getSelectedRoleIds()) && Utils.isBlankOrNull(adminForm.getEmployeeCode())) {
				errors.add("add_user.errors.employee_code_required", new ActionError("add_user.errors.employee_code_required"));
			}
			if (Utils.isBlankOrNull(userId) && Utils.isBlankOrNull(adminForm.getParentId())) {
				errors.add("common.please_select.one_param", new ActionError("common.please_select.one_param", TPLabels.getLabel("common.assign_under")));
			}
			// if
			// (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)
			// && UserConstants.ROLE_VENDOR !=
			// Integer.parseInt(adminForm.getSelectedRoleIds())) {
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED) && UserConstants.ROLE_VENDOR != Integer.parseInt(adminForm.getSelectedRoleIds()) && UserConstants.IS_USER_LDAP_SETTING_ENABLED.equals(adminForm.getIsUserLdapSetting())) {
				LDAPQueryProcessor ldapQuery = new LDAPQueryProcessor();
				LDAPManager manager = new LDAPManager();
				ArrayList<LDAPServerData> serverList = manager.getLDAPServerList();
				String loggedInUser = (String) request.getSession().getAttribute("username");
				String loggedInUserPass = (String) request.getSession().getAttribute("userpassword");
				if (!Utils.isBlankOrNull(adminForm.getUserName())) {
					ArrayList<LDAPUserData> users = ldapQuery.getUsersList(serverList, LDAPConstants.SEARCH_BY_USER_NAME, adminForm.getUserName().trim(), loggedInUser, loggedInUserPass);
					if (users.size() == 0) {
						errors.add("add_user.errors.not_valid_ldap_username", new ActionError("add_user.errors.not_valid_ldap_username"));
					} else {
						for(LDAPUserData user : users) {
							if(adminForm.getUserName().equals(user.getAttribute(LDAPConstants.LDAP_SAM_ACCOUNT_NAME))) {
								if(!adminForm.getFirstName().equals(user.getAttribute(LDAPConstants.LDAP_GIVEN_NAME))) {
									errors.add("add_user.errors.incorrect_fname", new ActionError("add_user.errors.incorrect_fname"));
								}
								if(!adminForm.getLastName().equals(user.getAttribute(LDAPConstants.LDAP_SUR_NAME))) {
									errors.add("add_user.errors.incorrect_lname", new ActionError("add_user.errors.incorrect_lname"));
								}
								if(!adminForm.getEmail().equals(user.getAttribute(LDAPConstants.LDAP_USER_EMAIL))) {
									errors.add("add_user.errors.invalid_email", new ActionError("add_user.errors.invalid_email"));
								}
							}
						}
					}					
				}

			}
			if (UserConstants.ROLE_VENDOR == Integer.parseInt(adminForm.getSelectedRoleIds())) {
				if(Utils.isBlankOrNull(adminForm.getUserSourceId())){
					errors.add("add_user.errors.vendor_source_required", new ActionError("add_user.errors.vendor_source_required"));
				}else if(adminManager.isSourceBlackListed(adminForm.getUserSourceId())){
					errors.add("add_user.errors.vendor_source_blacklisted", new ActionError("add_user.errors.vendor_source_blacklisted"));
				}
			}

			
			// check for source present
			boolean createSource = true;
			if (UserConstants.ROLE_VENDOR != Integer.parseInt(adminForm.getSelectedRoleIds())) {
				String userSourceId = adminManager.getSourceIdForEmployeeCode(adminForm.getEmployeeCode());
				if (!Utils.isBlankOrNull(userSourceId)) {
					createSource = false;
					adminForm.setUserSourceId(userSourceId);
				
					// Check for the previous Employee User
					int count = adminManager.getEmployeeSourceUser(adminForm.getUserId(), adminForm.getUserSourceId());
					if (count > 0) {
						errors.add("add_user.errors.employee_user_exist", new ActionError("add_user.errors.employee_user_exist"));
					}
				}
			}
			/*
			 * Save The record If valid Data First Create new User if User ID Is
			 * null
			 */
			if (errors.size() == 0) {
				LoginData loginData = new LoginData();
				loginData.setUserName(adminForm.getUserName());
				loginData.setFirstName(adminForm.getFirstName());
				loginData.setLastName(adminForm.getLastName());
				loginData.setEmail(adminForm.getEmail());
				loginData.setParentId(adminForm.getParentId());
				loginData.setDepartmentId(adminForm.getDepartmentId());
				loginData.setSubDepartmentId(adminForm.getSubDepartmentId());
				loginData.setSubSubDepartmentId(adminForm.getSubSubDepartmentId());
				loginData.setSub3DepartmentId(adminForm.getSub3DepartmentId());
				loginData.setSub4DepartmentId(adminForm.getSub4DepartmentId());
				loginData.setLocationId(adminForm.getLocationId());
				loginData.setGradeId(adminForm.getGradeId());
				loginData.setBandId(adminForm.getBandId());
				loginData.setBuId(adminForm.getBuId());
				loginData.setCostCenterId(adminForm.getCostCenterId());
				
				if (!Utils.isBlankOrNull(adminForm.getHomePhone())) {
					loginData.setHomePhone(adminForm.getHomePhone().trim());
				}
				if (!Utils.isBlankOrNull(adminForm.getCellPhone())) {
					loginData.setCellPhone(adminForm.getCellPhone().trim());
				}
				if (!Utils.isBlankOrNull(adminForm.getIsUserLdapSetting())) {
					loginData.setIsUserLdapSetting(adminForm.getIsUserLdapSetting());
				}
				if (!Utils.isBlankOrNull(adminForm.getEmployeeCode())) {
					loginData.setEmployeeCode(adminForm.getEmployeeCode());
				}
				loginData.setUserSourceId(adminForm.getUserSourceId());
				String clientIpAddr = getClientIpAddr(request);				
				if (Utils.isBlankOrNull(userId)) {
					loginData.setPassword(adminForm.getPassword());
					// update permissions for New User
					LoginData duplicateData = null;
					try {
						LoginManager loginManager = new LoginManager();
						duplicateData = loginManager.getLoginDataFor(adminForm.getUserName());
					} catch (Exception e) {
						TPLogger.getLogger().error(GlobalConstants.ERROR, e);
					}
					if (duplicateData != null) {
						errors.add("add_user.errors.duplicate_username_exists", new ActionError("add_user.errors.duplicate_username_exists"));
						throw new Exception();
					}
					UserManager userManager = new UserManager();
					String duplicateUserId = userManager.getUserIdForEmail(adminForm.getEmail());
					if (!Utils.isBlankOrNull(duplicateUserId)) {
						errors.add("add_user.errors.duplicate_email_exists", new ActionError("add_user.errors.duplicate_email_exists"));
						throw new Exception();
					}

					userId = adminManager.createUser(loginData, adminForm.getSelectedRoleIds(), createSource);
					auditAction.insertAuditInfo(TPLabels.getLabel("common.profile"), AuditConstants.TYPE_ADDED, userId,
							AuditConstants.AUDIT_USER, loginUser, null, null, null, true, clientIpAddr);
					
				} else {
					adminManager.updateUser(userId, loginData, adminForm.getSelectedRoleIds(), createSource);
					auditAction.insertAuditInfo(TPLabels.getLabel("common.profile"), AuditConstants.TYPE_MODIFIED, userId,
							AuditConstants.AUDIT_USER, loginUser, null, null, null, true, clientIpAddr);
				}

				request.setAttribute("update", "1");
			}

		} catch (SQLException sqle) {
			errors.add("add_user.errors.duplicate_username", new ActionError("add_user.errors.duplicate_username"));
		} catch (MasterExistException e) {
			errors.add("add_user.errors.employee_source_exist", new ActionError("add_user.errors.employee_source_exist"));
		}catch (SourceExistException e) {
			errors.add("add_user.errors.employee_source_exist", new ActionError("add_user.errors.employee_source_exist"));
		}catch (SourceOrEmployeeCodeExistException e) {
			errors.add("add_user.errors.employee_source_exist", new ActionError("add_user.errors.employee_source_exist"));
		} catch (EmailExistException e) {
			errors.add("add_user.errors.duplicate_email_exists", new ActionError("add_user.errors.duplicate_email_exists"));
		} catch (RSADecryptionException e) {
			errors.add("add_user.errors.decrypt_error", new ActionError("add_user.errors.decrypt_error"));
		}
		catch (Exception e) {
			TPLogger.getLogger().error("Error While Saving User Data ", e);
		}

		if (errors.size() > 0) {
			AdminManager adminManager = new AdminManager();
			ArrayList employeeSource = adminManager.getEmployeeSource();
			adminForm.setJsArrayEmployeeSource(CommonUtils.getListJavaScriptArrayWithProperties(employeeSource, "itemId", "itemName"));
			request.setAttribute(Globals.ERROR_KEY, errors);
			adminForm.setUserSourceId(prevSourceId);
		}
		return addUser(mapping, actionForm, request, response);
	}

	public ActionForward inboxSettingsMaster(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "inboxSettings";
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_INBOX_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		AdminForm adminForm = (AdminForm) actionForm;
		try {
			AdminManager adminManager = new AdminManager();
			SimpleDataObject sDo = adminManager.getInboxSettings();
			adminForm.setInboxEmail(sDo.getString("inboxEmail"));
			adminForm.setInboxUserName(sDo.getString("inboxUserName"));
			adminForm.setInboxPassword(sDo.getString("inboxPassword"));
			adminForm.setInboxSmtpHost(sDo.getString("inboxSmtpHost"));
			adminForm.setInboxPollingDuration(sDo.getId("inboxPollingDuration"));
			adminForm.setInboxpopHost(sDo.getString("inboxpopHost"));
			adminForm.setInboxDisplayName(sDo.getString("inboxDisplayName"));
			adminForm.setInboxServerType("" + sDo.getInt("inboxServerType"));
			adminForm.setInboxSmtpAuthRequired("" + sDo.getInt("inboxSmtpAuthRequired"));
			adminForm.setInboxSmtpAuthSame("" + sDo.getInt("inboxSmtpAuthSame"));
			adminForm.setInboxSmtpUserName(sDo.getString("inboxSmtpUserName"));
			adminForm.setInboxSmtpPassword(sDo.getString("inboxSmtpPassword"));
			adminForm.setInboxOutgoingPort(sDo.getString("inboxOutgoingPort"));
			adminForm.setInboxOutgoingSSLEnabled("" + sDo.getInt("inboxOutgoingSSLEnabled"));
			adminForm.setInboxOutgoingTLSEnabled("" + sDo.getInt("inboxOutgoingTLSEnabled"));
			adminForm.setInboxIncomingPort(sDo.getString("inboxIncomingPort"));
			adminForm.setInboxIncomingSSLEnabled("" + sDo.getInt("inboxIncomingSSLEnabled"));			
			adminForm.setDomainName(Utils.isBlankOrNull(sDo.getString("domainName"))?"":sDo.getString("domainName"));
			adminForm.setExchangeServerName(Utils.isBlankOrNull(sDo.getString("exchangeServerName"))?"":sDo.getString("exchangeServerName"));			
			adminForm.setExchangeServerVersion(sDo.getString("exchangeServerVersion"));			
			adminForm.setExchangeSmtp(sDo.getString("exchangeSmtp"));

			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_ACCOUNT_SETTINGS);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching inbox settings", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward submitInboxSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "inboxSettings";
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_INBOX_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			AdminForm adminForm = (AdminForm) actionForm;
			ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
			if (errors == null) {
				errors = new ActionErrors();
			}
			if (!Utils.isValidPattern(adminForm.getInboxEmail(), Utils.regEmail)) {
				errors.add("admin_settings.error.invalid_email", new ActionError("admin_settings.error.invalid_email"));
				request.setAttribute(Globals.ERROR_KEY, errors);
			}
			if (errors.size() == 0) {
				String inboxEmail = adminForm.getInboxEmail();
				String inboxUserName = adminForm.getInboxUserName();
				String inboxPassword = adminForm.getInboxPassword();
				String inboxSmtpHost = adminForm.getInboxSmtpHost();
				String inboxpopHost = adminForm.getInboxpopHost();
				String inboxPollingDuration = adminForm.getInboxPollingDuration();
				String inboxDisplayName = adminForm.getInboxDisplayName();

				String inboxServerType = adminForm.getInboxServerType();
				String inboxSmtpAuthRequired = adminForm.getInboxSmtpAuthRequired();
				String inboxSmtpAuthSame = adminForm.getInboxSmtpAuthSame();
				String inboxSmtpUserName = adminForm.getInboxSmtpUserName();
				String inboxSmtpPassword = adminForm.getInboxSmtpPassword();
				String inboxOutgoingPort = adminForm.getInboxOutgoingPort();
				String inboxOutgoingSSLEnabled = adminForm.getInboxOutgoingSSLEnabled();
				String inboxIncomingPort = adminForm.getInboxIncomingPort();
				String inboxIncomingSSLEnabled = adminForm.getInboxIncomingSSLEnabled();
				String inboxOutgoingTLSEnabled = adminForm.getInboxOutgoingTLSEnabled();
				
				String domainName = adminForm.getDomainName();
				String exchangeServerName = adminForm.getExchangeServerName();
				String exchangeServerVersion = adminForm.getExchangeServerVersion();
				String exchangeSmtp = adminForm.getExchangeSmtp(); 
					
				AdminManager adminManager = new AdminManager();
				adminManager.InsertUpdateSettings(inboxEmail, inboxUserName, inboxPassword, inboxSmtpHost, inboxpopHost, inboxPollingDuration, inboxDisplayName, inboxServerType, inboxSmtpAuthRequired, inboxSmtpAuthSame, inboxSmtpUserName, inboxSmtpPassword, inboxOutgoingPort, inboxOutgoingSSLEnabled, inboxOutgoingTLSEnabled, inboxIncomingPort, inboxIncomingSSLEnabled, exchangeServerName, domainName, exchangeServerVersion, exchangeSmtp);
				request.setAttribute("saved", "1");
				InboxScheduler.resetAllReceiver();
			}
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_ACCOUNT_SETTINGS);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating inbox settings", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward applicationSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "applicationSettings";
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_APPLICATION_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			ActionErrors errors = new ActionErrors();
			if (!Utils.isBlankOrNull(request.getParameter("isSubmitted"))) {
				String max_attachment_size_in_mb = request.getParameter(GlobalConstants.PROPERTY_MAX_ATTACHMENT_SIZE_IN_MB);
				String max_skills_parsed = request.getParameter(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED);
				String search_result_page_size = request.getParameter(GlobalConstants.PROPERTY_SEARCH_RESULT_PAGE_SIZE);
				String duration_as_new_resume = request.getParameter(GlobalConstants.PROPERTY_DURATION_AS_NEW_RESUME);
				String vendor_resume_uplaod_limit = request.getParameter(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_PER_POSITION);
				String duration_to_display_sent_messages = request.getParameter(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_MESSAGES);
				String duration_to_display_sent_emails = request.getParameter(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS);
				String send_reminder_to_me = request.getParameter(GlobalConstants.PROPERTY_SEND_REMINDER_TO_ME);
				String send_reminder_to_interviewer = request.getParameter(GlobalConstants.PROPERTY_SEND_REMINDER_TO_INTERVIEWER);
				String send_reminder_to_candidate = request.getParameter(GlobalConstants.PROPERTY_SEND_REMINDER_TO_CANDIDATE);
				String send_appointment = request.getParameter(GlobalConstants.PROPERTY_SEND_APPOINTMENT);
				String default_appointment_duration = request.getParameter(GlobalConstants.PROPERTY_DEFAULT_APPOINTMENT_DURATION);
				String default_reminder_duration = request.getParameter(GlobalConstants.PROPERTY_DEFAULT_REMINDER_DURATION);
				String send_feedback_reminders = request.getParameter(GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS);
				String send_auto_reply_email = request.getParameter(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL);
				String financial_year_start_month = request.getParameter(GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH);
				String max_dept_level = request.getParameter(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL);
				String notify_vendor_activity_to_hr = request.getParameter(GlobalConstants.PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR);
				String show_detailed_activity_to_vendor = request.getParameter(GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
				String send_duplicate_resume_upload_tried_notification = request.getParameter(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION);
				String send_duplicate_resume_upload_tried_notification_to_email = request.getParameter(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL);
				String sendEmailNotificationToHrForEmployeePortalUpload = request.getParameter(GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL);
				String sendEmailToHrForEmployeePortalUpload = request.getParameter(GlobalConstants.PROPERTY_SEND_EMAIL_TO_HR_FOR_EMPLOYEE_PORTAL);
				String send_mail_to_Hrmanager_Pending_Requisition_approval = request.getParameter(GlobalConstants.PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL);
				String autoApproveRequisition = request.getParameter(GlobalConstants.PROPERTY_AUTO_APPROVE_REQUISITION);
				String department_level_1 = request.getParameter(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1);
				String department_level_2 = request.getParameter(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2);
				String department_level_3 = request.getParameter(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3);
				String department_level_4 = request.getParameter(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4);
				String department_level_5 = request.getParameter(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5);
				String careers_page_url = request.getParameter(GlobalConstants.PROPERTY_CAREERS_PAGE_URL);
				String show_reminder = request.getParameter(GlobalConstants.PROPERTY_SHOW_REMINDER);
				String default_hire_by_duration_in_days = request.getParameter(GlobalConstants.PROPERTY_DEFAULT_HIRE_BY_DURATION_IN_DAYS);
				String maximum_no_of_public_flags_allowed = request.getParameter(GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PUBLIC_FLAGS_ALLOWED);
				String maximum_no_of_private_flags_allowed = request.getParameter(GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PRIVATE_FLAGS_ALLOWED);
				String vendor_resume_upload_notification = request.getParameter(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION);
				String rejection_email_to_vendor = request.getParameter(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_VENDOR);
				String enable_single_sign_on = request.getParameter(GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON);
				String show_position_code = request.getParameter(GlobalConstants.PROPERTY_SHOW_POSITION_CODE);
				String force_position_creation_from_template = request.getParameter(GlobalConstants.PROPERTY_FORCE_POSITION_CREATION_FROM_TEMPLATE);
				String employee_resume_upload_notification = request.getParameter(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION);
				String rejection_email_to_employee = request.getParameter(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_EMPLOYEE);
				String rejection_email_to_candidate = request.getParameter(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_CANDIDATE);
				String position_code_template = request.getParameter(GlobalConstants.PROPERTY_POSITION_CODE_TEMPLATE);
				String offer_code_template = request.getParameter(GlobalConstants.PROPERTY_OFFER_CODE_TEMPLATE);
				String allow_bulk_feedback = request.getParameter(GlobalConstants.PROPERTY_ALLOW_BULK_FEEDBACK);
				String candidate_progress_notification = request.getParameter(GlobalConstants.PROPERTY_NOTIFY_CANDIDATE_PROGRESS);
				String employee_can_apply_for_job = request.getParameter(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB);
				String show_bot_resistant_email_id = request.getParameter(GlobalConstants.PROPERTY_SHOW_BOT_RESISTANT_EMAIL_ID);
				String audit_trail_recovery_notification = request.getParameter(GlobalConstants.PROPERTY_AUDIT_TRAIL_RECOVERY_NOTIFICATION);
				String grid_result_page_size = request.getParameter(GlobalConstants.PROPERTY_GRID_RESULT_PAGE_SIZE);
				String progress_email_to_employee = request.getParameter(GlobalConstants.PROPERTY_PROGRESS_EMAIL_TO_EMPLOYEE);
				String joined_email_to_employee = request.getParameter(GlobalConstants.PROPERTY_JOINED_EMAIL_TO_EMPLOYEE);
				String validate_ctc_as_numeric = request.getParameter(GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC);
				String is_employee_code_mandatory = request.getParameter(GlobalConstants.PROPERTY_IS_EMPLOYEE_CODE_MANDATORY);
				String budget_module_status = request.getParameter(GlobalConstants.PROPERTY_BUDGET_MODULE_STATUS);
				String budget_mode = request.getParameter(GlobalConstants.PROPERTY_BUDGET_MODE);
				String budget_item_grade_label = request.getParameter(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL);
				String budget_item_band_label = request.getParameter(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL);
				String salary_variable_input_salary_label = request.getParameter(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL);
				String overdue_duration_for_requisition_approval_notification = request.getParameter(GlobalConstants.PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION);
				String send_reminder_to_vendor = request.getParameter(GlobalConstants.PROPERTY_SEND_REMINDER_TO_VENDOR);
				String send_referent_employee_in_selection_process_notification = request.getParameter(GlobalConstants.PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION);
				String user_display_in_grid_template = request.getParameter(GlobalConstants.PROPERTY_USER_DISPLAY_IN_GRID_TEMPLATE);
				String default_dateformat = request.getParameter(GlobalConstants.PROPERTY_DEFAULT_DATEFORMAT);
				String default_timeformat = request.getParameter(GlobalConstants.PROPERTY_DEFAULT_TIMEFORMAT);
				String position_display_in_grid_template = request.getParameter(GlobalConstants.PROPERTY_POSITION_DISPLAY_IN_GRID_TEMPLATE);
				String vendor_resume_upload_notification_to_vendor = request.getParameter(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR);
				String employee_resume_upload_notification_to_employee = request.getParameter(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE);
				String employee_resume_upload_notification_to_candidate = request.getParameter(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_CANDIDATE);
				String all_employees_group_email_id = request.getParameter(GlobalConstants.PROPERTY_ALL_EMPLOYEES_GROUP_EMAIL_ID);
				String all_employees_hrgroup_email_id = request.getParameter(GlobalConstants.PROPERTY_ALL_EMPLOYEES_HRGROUP_EMAIL_ID);
				String send_position_published_notification_to_employees = request.getParameter(GlobalConstants.PROPERTY_SEND_POSITION_PUBLISHED_NOTIFICATION_TO_EMPLOYEES);
				String send_position_change_notification = request.getParameter(GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION);
				String duplicatePositionAlertNotificationToAdmin = request.getParameter(GlobalConstants.PROPERTY_DUPLICATE_POSITION_ALERT_NOTIFICATION_TO_ADMIN);
				
				String enable_org_hierarchy_for_visibility = request.getParameter(GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY);
				
				String business_unit_property = request.getParameter(GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY);
				String business_unit_label = Utils.isBlankOrNull(request.getParameter(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL).trim())?TPLabels.getLabel("master_business_unit.title.business_unit"):request.getParameter(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL).trim();
				String cost_center_property = request.getParameter(GlobalConstants.PROPERTY_COST_CENTER_PROPERTY);
				String cost_center_label = Utils.isBlankOrNull(request.getParameter(GlobalConstants.PROPERTY_COST_CENTER_LABEL).trim())?TPLabels.getLabel("master_cost_center.title.cost_center"):request.getParameter(GlobalConstants.PROPERTY_COST_CENTER_LABEL).trim();
				String budget_for_replacement = request.getParameter(GlobalConstants.PROPERTY_BUDGET_FOR_REPLACEMENT);
				String enable_bcc_while_sending_email = request.getParameter(GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL);
				String enable_hr_manager_cc_while_sending_email = request.getParameter(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL);
				String keep_HRmanager_cc_for_all_position_approval = request.getParameter(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL);
				String send_mail_to_recruiter_for_rejected_candidate = request.getParameter(GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_REJECTED_CANDIDATE);
				String send_mail_to_recruiter_for_On_Hold_candidate = request.getParameter(GlobalConstants.PROPERTY_SEND_EMAIL_TO_RECRUITER_FOR_ON_HOLD_CANDIDATE);
				String send_mail_to_recruiter_for_candidate_status= request.getParameter(GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS);
				String show_reason_for_reject= request.getParameter(GlobalConstants.PROPERTY_SHOW_REASON_FOR_REJECT_CANDIDATE);
				String show_step_details= request.getParameter(GlobalConstants.PROPERTY_SHOW_STEP_DETAILS);
				String show_step_details_for_vendor= request.getParameter(GlobalConstants.PROPERTY_SHOW_STEP_DETAILS_VENDOR);
				String send_mail_to_friend= request.getParameter(GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING);
				
				
			
				String copy_position_with_code = request.getParameter(GlobalConstants.PROPERTY_COPY_POSITION_WITH_CODE);
				String show_joined_candidate_in_seach = request.getParameter(GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH);
				String lock_period_for_joined_candidate_shortlist = request.getParameter(GlobalConstants.PROPERTY_LOCK_PERIOD_FOR_JOINED_CANDIDATE_SHORTLIST);
				String default_number_of_vacancy =request.getParameter(GlobalConstants.PROPERTY_DEFAULT_NUMBER_OF_VACANCY);
				String default_password_expiry_duration =request.getParameter(GlobalConstants.PROPERTY_DEFAULT_PASSWORD_EXPIRY_DURATION);
				String default_position_approval_duration =request.getParameter(GlobalConstants.PROPERTY_DEFAULT_POSITION_APPROVAL_DURATION);
				String default_password_different_from_last =request.getParameter(GlobalConstants.PROPERTY_DEFAULT_PASSWORD_DIFFERENT_FROM_LAST);
				String offer_to_joined_step_id =request.getParameter(GlobalConstants.PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID);
				String apply_position_vacancy_restriction = request.getParameter(GlobalConstants.PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION);
				
				//Naukri fields
				String is_naukri_integration = request.getParameter(GlobalConstants.PROPERTY_IS_NAUKRI_INTEGRATION);
				String hiring_org_website =request.getParameter(GlobalConstants.PROPERTY_HIRING_ORG_WEBSITE);
				String hiring_org_name =request.getParameter(GlobalConstants.PROPERTY_HIRING_ORG_NAME);
				String org_description =request.getParameter(GlobalConstants.PROPERTY_ORG_DESCRIPTION);
				String microsite_name =request.getParameter(GlobalConstants.PROPERTY_MICROSITE_NAME);
				String template_name = request.getParameter(GlobalConstants.PROPERTY_TEMPLATE_NAME);
				String rejectedCandidateMailToRecruiter = request.getParameter(GlobalConstants.PROPERTY_SEND_REJECTED_CANDIDATE_NOTIFICATION_TO_RECRUITER);
				// do validation

				if (!Utils.isValidPattern(max_attachment_size_in_mb, Utils.regNumberOnly)) {
					errors.add("admin_application_settings.invalid_attachment_size", new ActionError("admin_application_settings.invalid_attachment_size"));
				}
				// if (!Utils.isValidPattern(max_skills_parsed,
				// Utils.regNumberOnly)) {
				if (!max_skills_parsed.equals("-1") && !Utils.isValidPattern(max_skills_parsed, Utils.regNumberOnly)) {
					errors.add("admin_application_settings.invalid_skills_number", new ActionError("admin_application_settings.invalid_skills_number"));
				}
				if (!Utils.isValidPattern(search_result_page_size, Utils.regNumberOnly)) {
					errors.add("admin_application_settings.invalid_result_page_size", new ActionError("admin_application_settings.invalid_result_page_size"));
				}
				if (!Utils.isValidPattern(duration_as_new_resume, Utils.regNumberOnly)) {
					errors.add("admin_application_settings.invalid_duration_for_new", new ActionError("admin_application_settings.invalid_duration_for_new"));
				}
				if (!Utils.isValidPattern(vendor_resume_uplaod_limit, Utils.regNumberOnly)) {
					errors.add("admin_application_settings.invalid_limit_for_resume_upload", new ActionError("admin_application_settings.invalid_limit_for_resume_upload"));
				}
				if (GlobalConstants.ENABLED.equalsIgnoreCase(send_duplicate_resume_upload_tried_notification) && Utils.isBlankOrNull(send_duplicate_resume_upload_tried_notification_to_email)) {
					errors.add("admin_application_settings.error.email_id_required_to_send_duplicate_resume_upload_notification", new ActionError("admin_application_settings.error.email_id_required_to_send_duplicate_resume_upload_notification"));
				}
				if (GlobalConstants.ENABLED.equalsIgnoreCase(sendEmailNotificationToHrForEmployeePortalUpload) && Utils.isBlankOrNull(sendEmailToHrForEmployeePortalUpload)) {
					errors.add("admin_application_settings.error.email_id_required_to_send_email_for_resume_upload_notification", new ActionError("admin_application_settings.error.email_id_required_to_send_email_for_resume_upload_notification"));
				}
				if (Utils.isBlankOrNull(department_level_1)) {
					errors.add("admin_application_settings.error.department_level1_required", new ActionError("admin_application_settings.error.department_level1_required"));
				}
				if (Utils.isBlankOrNull(department_level_2)) {
					errors.add("admin_application_settings.error.department_level2_required", new ActionError("admin_application_settings.error.department_level2_required"));
				}
				if (Utils.isBlankOrNull(department_level_3)) {
					errors.add("admin_application_settings.error.department_level3_required", new ActionError("admin_application_settings.error.department_level3_required"));
				}
				if(MastersConstants.DEPARTMENT_LEVEL_4.equals(max_dept_level) || MastersConstants.DEPARTMENT_LEVEL_5.equals(max_dept_level)){
					if (Utils.isBlankOrNull(department_level_4)) {
						errors.add("admin_application_settings.error.department_level4_required", new ActionError("admin_application_settings.error.department_level4_required"));
					}
				}
				if(MastersConstants.DEPARTMENT_LEVEL_5.equals(max_dept_level)){
					if (Utils.isBlankOrNull(department_level_5)) {
						errors.add("admin_application_settings.error.department_level5_required", new ActionError("admin_application_settings.error.department_level5_required"));
					}
				}
				if (Utils.isBlankOrNull(default_hire_by_duration_in_days)) {
					errors.add("admin_application_settings.error.default_hire_by_duration_in_days_required", new ActionError("admin_application_settings.error.default_hire_by_duration_in_days_required"));
				}
				if (Utils.isBlankOrNull(maximum_no_of_public_flags_allowed)) {
					errors.add("admin_application_settings.error.maximum_no_of_public_flags_allowed", new ActionError("admin_application_settings.error.maximum_no_of_public_flags_allowed"));
				}
				if (Utils.isBlankOrNull(maximum_no_of_private_flags_allowed)) {
					errors.add("admin_application_settings.error.maximum_no_of_private_flags_allowed", new ActionError("admin_application_settings.error.maximum_no_of_private_flags_allowed"));
				}
				if (Utils.isBlankOrNull(position_code_template)) {
					ActionError error = new ActionError("admin_application_settings.error.sequentially_generated_number",TPLabels.getLabel("common.position"));
				   errors.add("admin_application_settings.error.sequentially_generated_number", error);
				}
				
				if (Utils.isBlankOrNull(offer_code_template)) {
				   ActionError error = new ActionError("admin_application_settings.error.inavlid_offer_code_template");
				   errors.add("admin_application_settings.error.inavlid_offer_code_template", error);
				}else if(offer_code_template.length()>30){
					ActionError error = new ActionError("admin_application_settings.error.inavlid_offer_code_length_exceeded");
					errors.add("admin_application_settings.error.inavlid_offer_code_length_exceeded", error);
				}
				
				if (Utils.isBlankOrNull(user_display_in_grid_template)) {
					ActionError error = new ActionError("admin_application_settings.error.invalid_user_display_in_grid_template",TPLabels.getLabel("common.user"));
				   errors.add("admin_application_settings.error.invalid_user_display_in_grid_template", error);
				}
				if (Utils.isBlankOrNull(position_display_in_grid_template)) {
					ActionError error = new ActionError("admin_application_settings.error.invalid_user_display_in_grid_template",TPLabels.getLabel("common.position"));
				   errors.add("admin_application_settings.error.invalid_user_display_in_grid_template", error);
				}
				if (!Utils.isValidPattern(grid_result_page_size, Utils.regNumberOnly)) {
					errors.add("admin_application_settings.invalid_grid_page_size", new ActionError("admin_application_settings.invalid_grid_page_size"));
				}						
				if (Utils.isBlankOrNull(budget_item_grade_label)) {
					errors.add("admin_application_settings.error.budget_grade_label_required", new ActionError("admin_application_settings.error.budget_grade_label_required"));
				}
				if (Utils.isBlankOrNull(budget_item_band_label)) {
					errors.add("admin_application_settings.error.budget_band_label_required", new ActionError("admin_application_settings.error.budget_band_label_required"));
				}

				if (Utils.isBlankOrNull(salary_variable_input_salary_label)) {
					errors.add("admin_application_settings.error.salary_variable_input_salary_label", new ActionError("admin_application_settings.error.salary_variable_input_salary_label"));
				}

				if (!Utils.isInteger(overdue_duration_for_requisition_approval_notification)) {
					errors.add("admin_application_settings.invalid_overdue_duration", new ActionError("admin_application_settings.invalid_overdue_duration"));
				}
				if ( send_position_published_notification_to_employees.equals("1") && Utils.isBlankOrNull(all_employees_group_email_id)) {
					errors.add("admin_application_settings.necessary_valid_employee_group_email_id", new ActionError("admin_application_settings.necessary_valid_employee_group_email_id"));
				}
				if (!Utils.isBlankOrNull(all_employees_group_email_id) && !Utils.isValidPattern(all_employees_group_email_id, Utils.regEmail)) {
					errors.add("admin_application_settings.invalid_employee_group_email_id", new ActionError("admin_application_settings.invalid_employee_group_email_id"));
				}
				if (!Utils.isBlankOrNull(all_employees_hrgroup_email_id) && !Utils.isValidPattern(all_employees_hrgroup_email_id, Utils.regEmail)) {
					errors.add("admin_application_settings.invalid_employee_hrgroup_email_id", new ActionError("admin_application_settings.invalid_employee_hrgroup_email_id"));
				}
				HashMap<String, String> props = new HashMap<String, String>();
				props.put(GlobalConstants.PROPERTY_MAX_ATTACHMENT_SIZE_IN_MB, max_attachment_size_in_mb);
				props.put(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED, max_skills_parsed);
				props.put(GlobalConstants.PROPERTY_SEARCH_RESULT_PAGE_SIZE, search_result_page_size);
				props.put(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_PER_POSITION, vendor_resume_uplaod_limit);
				props.put(GlobalConstants.PROPERTY_DURATION_AS_NEW_RESUME, duration_as_new_resume);
				props.put(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_MESSAGES, duration_to_display_sent_messages);
				props.put(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS, duration_to_display_sent_emails);
				props.put(GlobalConstants.PROPERTY_SEND_REMINDER_TO_ME, send_reminder_to_me);
				props.put(GlobalConstants.PROPERTY_SEND_REMINDER_TO_INTERVIEWER, send_reminder_to_interviewer);
				props.put(GlobalConstants.PROPERTY_SEND_REMINDER_TO_CANDIDATE, send_reminder_to_candidate);
				props.put(GlobalConstants.PROPERTY_SEND_APPOINTMENT, send_appointment);
				props.put(GlobalConstants.PROPERTY_DEFAULT_APPOINTMENT_DURATION, default_appointment_duration);
				props.put(GlobalConstants.PROPERTY_DEFAULT_REMINDER_DURATION, default_reminder_duration);
				props.put(GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS, send_feedback_reminders);
				props.put(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL, send_auto_reply_email);
				props.put(GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH, financial_year_start_month);
				props.put(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL, max_dept_level);
				props.put(GlobalConstants.PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR, notify_vendor_activity_to_hr);
				props.put(GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR, show_detailed_activity_to_vendor);
				props.put(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION, send_duplicate_resume_upload_tried_notification);
				props.put(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL, send_duplicate_resume_upload_tried_notification_to_email);
				
				props.put(GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL, sendEmailNotificationToHrForEmployeePortalUpload);
				props.put(GlobalConstants.PROPERTY_SEND_EMAIL_TO_HR_FOR_EMPLOYEE_PORTAL, sendEmailToHrForEmployeePortalUpload);
				props.put(GlobalConstants.PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL, send_mail_to_Hrmanager_Pending_Requisition_approval);
				props.put(GlobalConstants.PROPERTY_AUTO_APPROVE_REQUISITION, autoApproveRequisition);
				props.put(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1, department_level_1);
				props.put(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2, department_level_2);
				props.put(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3, department_level_3);
				props.put(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4, department_level_4);
				props.put(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5, department_level_5);
				props.put(GlobalConstants.PROPERTY_CAREERS_PAGE_URL, careers_page_url);
				props.put(GlobalConstants.PROPERTY_SHOW_REMINDER, show_reminder);
				props.put(GlobalConstants.PROPERTY_DEFAULT_HIRE_BY_DURATION_IN_DAYS, default_hire_by_duration_in_days);
				props.put(GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PUBLIC_FLAGS_ALLOWED, maximum_no_of_public_flags_allowed);
				props.put(GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PRIVATE_FLAGS_ALLOWED, maximum_no_of_private_flags_allowed);
				props.put(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION, vendor_resume_upload_notification);
				props.put(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_VENDOR, rejection_email_to_vendor);
				props.put(GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON, enable_single_sign_on);
				props.put(GlobalConstants.PROPERTY_SHOW_POSITION_CODE, show_position_code);
				props.put(GlobalConstants.PROPERTY_FORCE_POSITION_CREATION_FROM_TEMPLATE, force_position_creation_from_template);
				props.put(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION, employee_resume_upload_notification);
				props.put(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_EMPLOYEE, rejection_email_to_employee);
				props.put(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_CANDIDATE, rejection_email_to_candidate);
				props.put(GlobalConstants.PROPERTY_POSITION_CODE_TEMPLATE, position_code_template);
				props.put(GlobalConstants.PROPERTY_OFFER_CODE_TEMPLATE, offer_code_template);
				props.put(GlobalConstants.PROPERTY_ALLOW_BULK_FEEDBACK, allow_bulk_feedback);
				props.put(GlobalConstants.PROPERTY_NOTIFY_CANDIDATE_PROGRESS, candidate_progress_notification);
				props.put(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB, employee_can_apply_for_job);
				props.put(GlobalConstants.PROPERTY_SHOW_BOT_RESISTANT_EMAIL_ID, show_bot_resistant_email_id);
				props.put(GlobalConstants.PROPERTY_AUDIT_TRAIL_RECOVERY_NOTIFICATION, audit_trail_recovery_notification);
				props.put(GlobalConstants.PROPERTY_GRID_RESULT_PAGE_SIZE, grid_result_page_size);
				props.put(GlobalConstants.PROPERTY_PROGRESS_EMAIL_TO_EMPLOYEE, progress_email_to_employee);
				props.put(GlobalConstants.PROPERTY_JOINED_EMAIL_TO_EMPLOYEE, joined_email_to_employee);
				props.put(GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC, validate_ctc_as_numeric);
				props.put(GlobalConstants.PROPERTY_IS_EMPLOYEE_CODE_MANDATORY, is_employee_code_mandatory);
				props.put(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL, budget_item_grade_label);
				props.put(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL, budget_item_band_label);
				props.put(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL, salary_variable_input_salary_label);
				props.put(GlobalConstants.PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION, overdue_duration_for_requisition_approval_notification);
				props.put(GlobalConstants.PROPERTY_SEND_REMINDER_TO_VENDOR, send_reminder_to_vendor);
				props.put(GlobalConstants.PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION, send_referent_employee_in_selection_process_notification);
				props.put(GlobalConstants.PROPERTY_USER_DISPLAY_IN_GRID_TEMPLATE, user_display_in_grid_template);
				props.put(GlobalConstants.PROPERTY_DEFAULT_DATEFORMAT, default_dateformat);
				props.put(GlobalConstants.PROPERTY_DEFAULT_TIMEFORMAT, default_timeformat);
				props.put(GlobalConstants.PROPERTY_POSITION_DISPLAY_IN_GRID_TEMPLATE, position_display_in_grid_template);
				props.put(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR, vendor_resume_upload_notification_to_vendor);
				props.put(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE, employee_resume_upload_notification_to_employee);
				props.put(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_CANDIDATE, employee_resume_upload_notification_to_candidate);
				props.put(GlobalConstants.PROPERTY_ALL_EMPLOYEES_GROUP_EMAIL_ID, all_employees_group_email_id);
				props.put(GlobalConstants.PROPERTY_ALL_EMPLOYEES_HRGROUP_EMAIL_ID, all_employees_hrgroup_email_id);
				props.put(GlobalConstants.PROPERTY_SEND_POSITION_PUBLISHED_NOTIFICATION_TO_EMPLOYEES, send_position_published_notification_to_employees);
				props.put(GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION, send_position_change_notification);
				props.put(GlobalConstants.PROPERTY_DUPLICATE_POSITION_ALERT_NOTIFICATION_TO_ADMIN, duplicatePositionAlertNotificationToAdmin);
				props.put(GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY, enable_org_hierarchy_for_visibility);
				props.put(GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL, enable_bcc_while_sending_email);
				props.put(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL, enable_hr_manager_cc_while_sending_email);
				props.put(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL, keep_HRmanager_cc_for_all_position_approval);
				props.put(GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_REJECTED_CANDIDATE, send_mail_to_recruiter_for_rejected_candidate);
				props.put(GlobalConstants.PROPERTY_SEND_EMAIL_TO_RECRUITER_FOR_ON_HOLD_CANDIDATE, send_mail_to_recruiter_for_On_Hold_candidate);
				props.put(GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS, send_mail_to_recruiter_for_candidate_status);
				props.put(GlobalConstants.PROPERTY_SHOW_REASON_FOR_REJECT_CANDIDATE, show_reason_for_reject);
				props.put(GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING, send_mail_to_friend);
				props.put(GlobalConstants.PROPERTY_SHOW_STEP_DETAILS, show_step_details);
				props.put(GlobalConstants.PROPERTY_SHOW_STEP_DETAILS_VENDOR, show_step_details_for_vendor);
				props.put(GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY, business_unit_property);
				props.put(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL, business_unit_label);
				props.put(GlobalConstants.PROPERTY_COST_CENTER_PROPERTY, cost_center_property);
				props.put(GlobalConstants.PROPERTY_COST_CENTER_LABEL, cost_center_label);
				
				props.put(GlobalConstants.PROPERTY_COPY_POSITION_WITH_CODE, copy_position_with_code);
				props.put(GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH,show_joined_candidate_in_seach);
				props.put(GlobalConstants.PROPERTY_LOCK_PERIOD_FOR_JOINED_CANDIDATE_SHORTLIST,lock_period_for_joined_candidate_shortlist);
				props.put(GlobalConstants.PROPERTY_DEFAULT_NUMBER_OF_VACANCY,default_number_of_vacancy);
				props.put(GlobalConstants.PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION, apply_position_vacancy_restriction);
				
				props.put(GlobalConstants.PROPERTY_IS_NAUKRI_INTEGRATION, is_naukri_integration);
				props.put(GlobalConstants.PROPERTY_HIRING_ORG_WEBSITE,hiring_org_website);
				props.put(GlobalConstants.PROPERTY_HIRING_ORG_NAME,hiring_org_name);
				props.put(GlobalConstants.PROPERTY_ORG_DESCRIPTION,org_description);
				props.put(GlobalConstants.PROPERTY_MICROSITE_NAME, microsite_name);
				props.put(GlobalConstants.PROPERTY_TEMPLATE_NAME, template_name);
				
				props.put(GlobalConstants.PROPERTY_DEFAULT_PASSWORD_EXPIRY_DURATION,default_password_expiry_duration);
				props.put(GlobalConstants.PROPERTY_DEFAULT_PASSWORD_DIFFERENT_FROM_LAST,default_password_different_from_last);
				props.put(GlobalConstants.PROPERTY_DEFAULT_POSITION_APPROVAL_DURATION,default_position_approval_duration);
				
				props.put(GlobalConstants.PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID,offer_to_joined_step_id);
				props.put(GlobalConstants.PROPERTY_SEND_REJECTED_CANDIDATE_NOTIFICATION_TO_RECRUITER, rejectedCandidateMailToRecruiter);
				if(ModuleSet.isMODULE_BUDGET()){
					props.put(GlobalConstants.PROPERTY_BUDGET_MODULE_STATUS, budget_module_status);
					props.put(GlobalConstants.PROPERTY_BUDGET_MODE, budget_mode);
					props.put(GlobalConstants.PROPERTY_BUDGET_FOR_REPLACEMENT, budget_for_replacement);
				}
				
				if (errors.size() == 0) {
					// update settings
					AdminManager adminManager = new AdminManager();
					adminManager.updateGlobalSettings(props);
					GlobalApplicationProperties.resetPropertiesMap();
					PositionScreenConfigurationManager.reloadPositionFieldsMaps();
					request.setAttribute("update", "1");
				} else {
					request.setAttribute(Globals.ERROR_KEY, errors);
					for (Iterator<String> itProp = props.keySet().iterator(); itProp.hasNext();) {
						String propertyName = itProp.next();
						String propertyValue = props.get(propertyName);
						request.setAttribute(propertyName, propertyValue);
					}
				}
			}
			if (errors.size() == 0) {
				HashMap<String, String> props = GlobalApplicationProperties.getPropertiesMap();
				for (Iterator<String> itProp = props.keySet().iterator(); itProp.hasNext();) {
					String propertyName = (String) itProp.next();
					String propertyValue = props.get(propertyName);
					request.setAttribute(propertyName, propertyValue);
				}
			}
			StepManager stepManager = new StepManager();
			List<MasterStepData> steps = stepManager.getStepsInStage(StepConstants.STEP_STAGE_HIRE, "0", "0");
			ArrayList<String> stepIds = new ArrayList<String>();
			ArrayList<String> stepNames = new ArrayList<String>();
			
			for (int i = 0; steps != null && i < steps.size(); i++) {
				MasterStepData stepData = steps.get(i);
				stepIds.add(stepData.getStepId());
				stepNames.add(stepData.getStepName());
			}
			String stepsJsArray = CommonUtils.getListJavaScriptArray(stepIds, stepNames);
			request.setAttribute("stepsJsArray", stepsJsArray);
			
			PositionManager positionManager = new PositionManager();
			List activeUsers = positionManager.getActiveUsers();

			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_APPLICATION_SETTINGS);
			request.setAttribute("activeUsers", activeUsers);
			setDateParameters(request);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	private void setDateParameters(HttpServletRequest request){
		request.setAttribute("datePatternsJSArray",DateUtils.buildDatePatternsJSArray(DateConstants.PATTERN_TYPE_DATE));
		request.setAttribute("timePatternsJSArray",DateUtils.buildDatePatternsJSArray(DateConstants.PATTERN_TYPE_TIME));
		request.setAttribute("dateFormat",DateUtils.getSystemDateFormat(new Date()));
		request.setAttribute("timeFormat",DateUtils.getSystemTimeFormat(new Date()));
	}

	public ActionForward updateApplicationSetting(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				AdminForm adminForm = (AdminForm) actionForm;
				AdminManager adminManager = new AdminManager();
				String propertyName = adminForm.getPropertyName();
				String propertyValue = adminForm.getPropertyValue();
				adminManager.updateApplicationProperty(propertyName, propertyValue, null);
				
				String loggedInUser = (String) request.getSession().getAttribute("userId");
				AuditAction auditAction = new AuditAction();
				String clientIpAddr = getClientIpAddr(request);
				auditAction.insertAuditInfo(TPLabels.getLabel("admin_application_settings.label.application_settings"),
						AuditConstants.TYPE_MODIFIED, loggedInUser, AuditConstants.AUDIT_USER, loggedInUser, null,
						null, null, false, clientIpAddr);
				
				GlobalApplicationProperties.resetPropertiesMap();
			}
		} catch (SQLException e) {
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward smsSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "smsSettings";
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_SMS_SETTINGS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_SMS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			AdminForm adminForm = (AdminForm) actionForm;
			AdminManager adminManager = new AdminManager();
			SimpleDataObject setting = adminManager.getSMSProviderSettings();
			adminForm.setProvider(setting.getString("provider"));
			adminForm.setProperty(setting.getString("property"));
			adminForm.setValue(setting.getString("value"));
			adminForm.setIsdefault(setting.getString("isDefault"));
			String sms_enabled = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SMS_ENABLED);
			request.setAttribute(GlobalConstants.PROPERTY_SMS_ENABLED, sms_enabled);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching sms settings", e);
		}
		request.setAttribute("t", NavigationConstants.T_ADMIN);
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_SMS_SETTINGS);

		return mapping.findForward(forward);
	}

	public ActionForward saveSmsSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "smsSettings";

		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_SMS_SETTINGS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_SMS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		ActionErrors errors = new ActionErrors();
		request.setAttribute("t", NavigationConstants.T_ADMIN);
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_SMS_SETTINGS);

		try {
			AdminForm adminForm = (AdminForm) actionForm;

			if (Utils.isBlankOrNull(adminForm.getProvider())) {
				errors.add("admin.sms_settings.error.provider_name", new ActionError("admin.sms_settings.error.provider_name"));
			}
			if (Utils.isBlankOrNull(adminForm.getValue())) {
				errors.add("admin.sms_settings.error.provider_url", new ActionError("admin.sms_settings.error.provider_url"));
			}
			if (errors.size() == 0) {
				// Save Settings.
				String sms_enabled = request.getParameter(GlobalConstants.PROPERTY_SMS_ENABLED);
				HashMap<String, String> props = new HashMap<String, String>();
				props.put(GlobalConstants.PROPERTY_SMS_ENABLED, sms_enabled);
				AdminManager adminManager = new AdminManager();
				adminManager.updateGlobalSettings(props);
				adminManager.saveSMSSettings(adminForm.getProvider(), adminForm.getValue());
				
				String loggedInUser = (String) request.getSession().getAttribute("userId");
				AuditAction auditAction = new AuditAction();
				String clientIpAddr = getClientIpAddr(request);
				auditAction.insertAuditInfo(TPLabels.getLabel("admin.sms_settings.label.title.sms_settings"),
						AuditConstants.TYPE_MODIFIED, loggedInUser, AuditConstants.AUDIT_USER, loggedInUser, null,
						null, null, false, clientIpAddr);
				
				GlobalApplicationProperties.resetPropertiesMap();				
				
				request.setAttribute("saved", "1");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving sms settings", e);
			errors.add("admin.sms_settings.error.save_setting", new ActionError("admin.sms_settings.error.save_setting"));
		}
		if (errors.size() > 0) {
			request.setAttribute(Globals.ERROR_KEY, errors);
			return mapping.findForward(forward);
		}
		return smsSettings(mapping, actionForm, request, response);
	}

	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward duplicateDetectionSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "duplicateDetectionSettings";
		
		Integer[] permission = new Integer[2];
		permission[0] = PermissionConstants.PERMISSION_ADMIN;
		permission[1] = PermissionConstants.PERMISSION_DUPLICATE_DETECTION_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permission,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			if (!Utils.isBlankOrNull(request.getParameter("isSubmitted"))) {
				ArrayList<DuplicateSettingsData> internalSettings = DuplicateSettings.getInternalSettings();
				ArrayList<DuplicateSettingsData> vendorSettings = DuplicateSettings.getVendorSettings();
				ArrayList<DuplicateSettingsData> employeeSettings = DuplicateSettings.getEmployeeSettings();
				// Save settings to database
				ArrayList<DuplicateSettingsData> settingsList = new ArrayList<DuplicateSettingsData>();
				for (int i = 0; internalSettings != null && i < internalSettings.size(); i++) {
					DuplicateSettingsData duplicateSettingsData = internalSettings.get(i);
					String val = request.getParameter(duplicateSettingsData.getFieldId() + "_" + duplicateSettingsData.getFieldType() + "_" + duplicateSettingsData.getCheckFor());
					if (!Utils.isBlankOrNull(val)) {
						settingsList.add(new DuplicateSettingsData(duplicateSettingsData.getFieldId(), "", duplicateSettingsData.getFieldType(), duplicateSettingsData.getCheckFor(), val));
					}
				}
				for (int i = 0; vendorSettings != null && i < vendorSettings.size(); i++) {
					DuplicateSettingsData duplicateSettingsData = vendorSettings.get(i);
					String val = request.getParameter(duplicateSettingsData.getFieldId() + "_" + duplicateSettingsData.getFieldType() + "_" + duplicateSettingsData.getCheckFor());
					if (!Utils.isBlankOrNull(val)) {
						settingsList.add(new DuplicateSettingsData(duplicateSettingsData.getFieldId(), "", duplicateSettingsData.getFieldType(), duplicateSettingsData.getCheckFor(), val));
					}
				}
				for (int i = 0; employeeSettings != null && i < employeeSettings.size(); i++) {
					DuplicateSettingsData duplicateSettingsData = employeeSettings.get(i);
					String val = request.getParameter(duplicateSettingsData.getFieldId() + "_" + duplicateSettingsData.getFieldType() + "_" + duplicateSettingsData.getCheckFor());
					if (!Utils.isBlankOrNull(val)) {
						settingsList.add(new DuplicateSettingsData(duplicateSettingsData.getFieldId(), "", duplicateSettingsData.getFieldType(), duplicateSettingsData.getCheckFor(), val));
					}
				}
				DuplicateSettings duplicateSettings = new DuplicateSettings();
				duplicateSettings.updateDuplicateSettings(settingsList);
				DuplicateSettings.loadDuplicateSettings();
				request.setAttribute("saved", "1");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating duplicate detection settings", e);
			ActionErrors errors = new ActionErrors();
			errors.add("duplicate_detection_settings.error.setting_not_saved", new ActionError("duplicate_detection_settings.error.setting_not_saved"));
			saveErrors(request, errors);
		}
		ArrayList<DuplicateSettingsData> internalSettings = DuplicateSettings.getInternalSettings();
		ArrayList<DuplicateSettingsData> vendorSettings = DuplicateSettings.getVendorSettings();
		ArrayList<DuplicateSettingsData> employeeSettings = DuplicateSettings.getEmployeeSettings();
		List<DuplicateSettingsData> settings = DuplicateSettings.getSettingList();
		request.setAttribute("t", NavigationConstants.T_ADMIN);
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_DUPLICATE_DETECTION_SETTINGS);
		request.setAttribute("internalSettings", internalSettings);
		request.setAttribute("vendorSettings", vendorSettings);
		request.setAttribute("employeeSettings", employeeSettings);
		request.setAttribute("settings", settings);
		return mapping.findForward(forward);
	}
	
	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward duplicatePositionDetectionSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "duplicatePositionDetectionSettings";
		
		Integer[] permission = new Integer[2];
		permission[0] = PermissionConstants.PERMISSION_ADMIN;
		permission[1] = PermissionConstants.PERMISSION_DUPLICATE_DETECTION_SETTINGS;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permission,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			if (!Utils.isBlankOrNull(request.getParameter("isSubmitted"))) {
				ArrayList<DuplicateSettingsData> internalSettings = DuplicatePositionSettings.getInternalSettings();
			
				ArrayList<DuplicateSettingsData> settingsList = new ArrayList<DuplicateSettingsData>();
				for (int i = 0; internalSettings != null && i < internalSettings.size(); i++) {
					DuplicateSettingsData duplicateSettingsData = internalSettings.get(i);
					String val = request.getParameter(duplicateSettingsData.getFieldId() + "_" + duplicateSettingsData.getFieldType() + "_" + duplicateSettingsData.getCheckFor());
					if (!Utils.isBlankOrNull(val)) {
						settingsList.add(new DuplicateSettingsData(duplicateSettingsData.getFieldId(), "", duplicateSettingsData.getFieldType(), duplicateSettingsData.getCheckFor(), val));
					}
				}
			
				DuplicatePositionSettings duplicateSettings = new DuplicatePositionSettings();
				duplicateSettings.updateDuplicateSettings(settingsList);
				DuplicatePositionSettings.loadDuplicateSettings();
				request.setAttribute("saved", "1");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating duplicate detection settings", e);
			ActionErrors errors = new ActionErrors();
			errors.add("duplicate_detection_settings.error.setting_not_saved", new ActionError("duplicate_detection_settings.error.setting_not_saved"));
			saveErrors(request, errors);
		}
		ArrayList<DuplicateSettingsData> internalSettings = DuplicatePositionSettings.getInternalSettings();
		
		List<DuplicateSettingsData> settings = DuplicatePositionSettings.getSettingList();
		request.setAttribute("t", NavigationConstants.T_ADMIN);
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_DUPLICATE_POSITION_DETECTION_SETTINGS);
		request.setAttribute("internalSettings", internalSettings);
		
		request.setAttribute("settings", settings);
		return mapping.findForward(forward);
	}
	
	/**
	 * Action to goto screen of Manage Roles
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward manageRoleAccess(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageRoleAccess";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_MANAGE_ROLES;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_MANAGE_ROLE_ACCESS);
			request.setAttribute("t", NavigationConstants.T_ADMIN);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching Role List", e);
		}
		return mapping.findForward(forward);

	}

	public ActionForward getRoleXMLFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		try {
			AdminManager adminManager = new AdminManager();
			ArrayList roles = adminManager.getRoles();
			String xmlFile = adminManager.getXMLForRoles(roles);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching User xmlFile", e);
		}
		return mapping.findForward(forward);
	}

	/**
	 * Action to get permissions
	 */
	public ActionForward roleAccessSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		AdminForm adminForm = (AdminForm) actionForm;
		String forward = "roleAccessSettings";
		Integer[] permission = new Integer[1];
		permission[0] = PermissionConstants.PERMISSION_ADMIN;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permission,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			String roleId = adminForm.getRoleId();
			String roleTitle = adminForm.getRoleTitle();
			String userId = adminForm.getUserId();
			String userName = adminForm.getUserName();
			AdminManager adminManager = new AdminManager();
			ArrayList<PermissionData> modulePermissions = adminManager.getAvailableModulePermissions();
			ArrayList<PermissionData> permissions = null;
			ArrayList<ReportLevelData> reportLevels = null;
			if (!Utils.isBlankOrNull(roleId)) {
				permissions = adminManager.getRolePermissions(roleId);
				reportLevels = adminManager.getAvailableReportLevelForRole(roleId);
				permissions = removeModulesNotAvailable(permissions, modulePermissions);
				request.setAttribute("roleId", roleId);
				request.setAttribute("roleTitle", roleTitle);
			} else {
				permissions = adminManager.getUserPermissions(userId);
				reportLevels = adminManager.getAvailableReportLevelForUser(userId);
				permissions = removeModulesNotAvailable(permissions, modulePermissions);
				request.setAttribute("userId", userId);
				request.setAttribute("userName", userName);
			}
			request.setAttribute("permissions", permissions);
			request.setAttribute("reportLevel", reportLevels);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching Permissions", e);
		}

		return mapping.findForward(forward);
	}

	private ArrayList<PermissionData> removeModulesNotAvailable(ArrayList<PermissionData> permissions, ArrayList<PermissionData> modulePermissions) {
		for (int i = 0; modulePermissions != null && i < modulePermissions.size(); i++) {
			PermissionData permissionModule = modulePermissions.get(i);
			if (!ModuleSet.isModuleAvailable(Integer.parseInt(permissionModule.getModuleId()))) {
				for (int p = 0; p < permissions.size(); p++) {
					if (permissions.get(p).getPermissionId().equals(permissionModule.getPermissionId()) || permissions.get(p).getParentId().equals(permissionModule.getPermissionId())) {
						permissions.remove(p);
						p--;
					}
				}
			}
		}
		return permissions;
	}

	/**
	 * Action to save permissions
	 */

	public ActionForward saveAccessSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			AdminForm adminForm = (AdminForm) actionForm;
			String roleId = adminForm.getRoleId();
			String strPermissions = adminForm.getStrPermissions();
			String strLevelPermissions = adminForm.getStrLevelPermissions();
			String changePermissionIds = adminForm.getChangePermissionIds();
			String changePermissionIdValues = adminForm.getChangePermissionIdValue();
			String changedReportLevelIds = adminForm.getChangedReportLevelIds();
			String changedReportLevelValues = adminForm.getChangedReportLevelValue();
			AdminManager adminManager = new AdminManager();
			if (!Utils.isBlankOrNull(roleId)) {
				adminManager.updateRolePermissions(roleId, strPermissions, strLevelPermissions);
			}
			ArrayList<LoginData> userIds = adminManager.getActiveUsersForRole(roleId);
			String clientIpAddr = getClientIpAddr(request);
			AuditAction auditAction = new AuditAction();
			for (int i = 0; i < userIds.size(); i++) {
				String usrId = userIds.get(i).getUserId();
				adminManager.updateUserPermissionForChangedRolePermission(usrId, changePermissionIds, changePermissionIdValues, changedReportLevelIds, changedReportLevelValues);
				
				auditAction.insertAuditInfo(TPLabels.getLabel("common.permissions"), AuditConstants.TYPE_MODIFIED, usrId,
						AuditConstants.AUDIT_USER, userId, null, null, null, true, clientIpAddr);
			}
			auditAction.insertAuditInfo(TPLabels.getLabel("common.permissions"), AuditConstants.TYPE_MODIFIED, roleId,
					AuditConstants.AUDIT_ROLE, userId, null, null, null, true, clientIpAddr); 
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			errors.add("add_applicant.errors.permissions", new ActionError("add_applicant.errors.permissions"));
			saveErrors(request, errors);
			return roleAccessSettings(mapping, actionForm, request, response);
		}
		return mapping.findForward(forward);
	}

	/**
	 * Action to validate permissions
	 */

	public ActionForward validateAccessSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "validateAccessSettings";
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			String loggedInUser = (String) request.getSession().getAttribute("userId");
			
			AdminForm adminForm = (AdminForm) actionForm;
			AdminManager adminManager = new AdminManager();
			String roleId = (String) request.getParameter("roleId");
			String userId = (String) request.getParameter("userId");

			String strPermissions = (String) adminForm.getStrPermissions();
			String strLevelPermissions = (String) adminForm.getStrLevelPermissions();
			String changePermissionIds = "";
			String changePermissionIdValue = "";
			String changedReportLevelIds = "";
			String changedReportLevelValue = "";

			// Upadting A User Permissions
			if (!Utils.isBlankOrNull(userId)) {
				adminManager.updateUserPermissions(userId, strPermissions, strLevelPermissions);
				
				AuditAction auditAction = new AuditAction();
				String clientIpAddr = getClientIpAddr(request);
				auditAction.insertAuditInfo(TPLabels.getLabel("common.permissions"), AuditConstants.TYPE_MODIFIED, userId,
						AuditConstants.AUDIT_USER, loggedInUser, null, null, null, true, clientIpAddr);
				
				forward = "closeModalCall";
			} else {
				forward = "validateAccessSettings";
				ArrayList<PermissionData> rolePermissions = adminManager.getRolePermissions(roleId);
				ArrayList<ReportLevelData> reportLevelForRole = adminManager.getAvailableReportLevelForRole(roleId);
				// Confirmation for Users permission according to role
				String[] newRolePermission = strPermissions.split(",");
				for (int i = 1; i < newRolePermission.length; i++) {

					String[] permissionValueId = newRolePermission[i].split(":");
					String iPermissionId = permissionValueId[0];
					String iPermissionValue = permissionValueId[1];

					for (int p = 0; p < rolePermissions.size(); p++) {
						String pPermissionId = rolePermissions.get(p).getPermissionId();
						String pPermissionValue = rolePermissions.get(p).getPermissionValue();

						if (pPermissionId.equals(iPermissionId)) {
							if (!iPermissionValue.equals(pPermissionValue)) {
								if (changePermissionIds.equals("")) {
									changePermissionIds = iPermissionId;
									changePermissionIdValue = iPermissionValue;
								} else {
									changePermissionIds += "," + iPermissionId;
									changePermissionIdValue += "," + iPermissionValue;
								}
							}
						}
					}
				}
				ArrayList<PermissionData> changeInUserPermission = adminManager.getChangeInUserPermission(roleId, changePermissionIds);
				String changeInUserPermissionAll = "";
				for (int u = 0; u < changeInUserPermission.size(); u++) {
					if (changeInUserPermissionAll.equals("")) {
						changeInUserPermissionAll = changeInUserPermission.get(u).getPermissionId() + ":" + changeInUserPermission.get(u).getPermissionDesc() + ":" + changeInUserPermission.get(u).getName() + ":" + changeInUserPermission.get(u).getIsPermission();
					} else {
						changeInUserPermissionAll += "," + changeInUserPermission.get(u).getPermissionId() + ":" + changeInUserPermission.get(u).getPermissionDesc() + ":" + changeInUserPermission.get(u).getName() + ":" + changeInUserPermission.get(u).getIsPermission();
					}
				}

				// Evaluate change in ReportLevels
				String[] newReportLevelPermission = strLevelPermissions.split(",");
				for (int j = 1; j < newReportLevelPermission.length; j++) {
					String[] reportLevelValueId = newReportLevelPermission[j].split(":");
					String reportLevelId = reportLevelValueId[0];
					String reportLevelValue = reportLevelValueId[1];

					for (int r = 0; r < reportLevelForRole.size(); r++) {
						String rReportLevelId = String.valueOf(reportLevelForRole.get(r).getLevelId());
						String rReportLevelValue = reportLevelForRole.get(r).getLevelIsSelected();

						if (rReportLevelId.equals(reportLevelId)) {
							if (!rReportLevelValue.equals(reportLevelValue)) {
								if (changedReportLevelIds.equals("")) {
									changedReportLevelIds = reportLevelId;
									changedReportLevelValue = reportLevelValue;
								} else {
									changedReportLevelIds += "," + reportLevelId;
									changedReportLevelValue += "," + reportLevelValue;
								}
							}
						}
					}
				}
				ArrayList<ReportLevelData> changeInReportLevel = adminManager.getChangeInReportLevel(roleId, changedReportLevelIds);
				String changeInReportLevelAll = "";
				for (int u = 0; u < changeInReportLevel.size(); u++) {
					if (changeInReportLevelAll.equals("")) {
						changeInReportLevelAll = String.valueOf(changeInReportLevel.get(u).getLevelId()) + ":" + changeInReportLevel.get(u).getLevelName() + ":" + changeInReportLevel.get(u).getName() + ":" + changeInReportLevel.get(u).getLevelIsSelected();
					} else {
						changeInReportLevelAll += "," + String.valueOf(changeInReportLevel.get(u).getLevelId()) + ":" + changeInReportLevel.get(u).getLevelName() + ":" + changeInReportLevel.get(u).getName() + ":" + changeInReportLevel.get(u).getLevelIsSelected();
					}
				}

				// If there is noo change in USERS permission then update role
				// and close the page.
				if (Utils.isBlankOrNull(changeInUserPermissionAll) && Utils.isBlankOrNull(changeInReportLevelAll)) {
					adminManager.updateRolePermissions(roleId, strPermissions, strLevelPermissions);
					forward = "closeModalCall";
				}

				request.setAttribute("roleId", roleId);
				request.setAttribute("changeInUserPermissionAll", changeInUserPermissionAll);
				request.setAttribute("changeInReportLevelAll", changeInReportLevelAll);
				request.setAttribute("changePermissionIds", changePermissionIds);
				request.setAttribute("changePermissionIdValue", changePermissionIdValue);
				request.setAttribute("changedReportLevelIds", changedReportLevelIds);
				request.setAttribute("changedReportLevelValue", changedReportLevelValue);
				request.setAttribute("strPermissions", strPermissions);
				request.setAttribute("strLevelPermissions", strLevelPermissions);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating inbox settings", e);
			errors.add("add_applicant.errors.permissions", new ActionError("add_applicant.errors.permissions"));
			saveErrors(request, errors);
			return roleAccessSettings(mapping, actionForm, request, response);
		}
		return mapping.findForward(forward);
	}

	/**
	 * Description: For Fetching Report Level Setting
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reportLevelSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "reportLevelSettings";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_MANAGE_REPORT_LEVEL_SETTINGS);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching Level List", e);
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
	public ActionForward getReportLevelXMLFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		try {
			AdminManager adminManager = new AdminManager();
			ArrayList levels = adminManager.getLevels();
			String xmlFile = adminManager.getXMLForReportLevel(levels);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching Report Level xmlFile", e);
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
	public ActionForward addLevel(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addLevel";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
			if (errors == null) {
				errors = new ActionErrors();
			}

			AdminForm adminForm = (AdminForm) actionForm;
			String levelId = adminForm.getLevelId();
			ArrayList<ReportIdName> reportIdNameList = getAvailableReportsIdNames();
			AdminManager adminManager = new AdminManager();
			if (!Utils.isBlankOrNull(levelId) && Utils.isBlankOrNull(adminForm.getLevelsReportId()) && errors.size() == 0) {
				ReportLevelData reportLevelData = adminManager.getLevelData(levelId);
				ArrayList<String> levelReportIds = adminManager.getReportsForLevel(levelId);
				for (int i = 0; i < reportIdNameList.size(); i++) {
					if (levelReportIds.contains(reportIdNameList.get(i).getReportId()+"_"+reportIdNameList.get(i).getReportType())) {
						reportIdNameList.get(i).setIsReportSelected(true);
					}
				}
				adminForm.setLevelName(reportLevelData.getLevelName());
			} else {
				if (!Utils.isBlankOrNull(adminForm.getLevelsReportId())) {
					String[] reportSelected = adminForm.getLevelsReportId().split(",");
					ArrayList<String> levelReportIds = (ArrayList<String>) Arrays.asList(reportSelected);
					for (int i = 0; i < reportIdNameList.size(); i++) {
						if (levelReportIds.contains(reportIdNameList.get(i).getReportId())) {
							reportIdNameList.get(i).setIsReportSelected(true);
						}
					}

				}
			}
			request.setAttribute("reportIdNameList", reportIdNameList);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}

		return mapping.findForward(forward);
	}

	private ArrayList<ReportIdName> getAvailableReportsIdNames() {
		ArrayList<ReportIdName> reportIdNames = ReportVersionConstants.reportDetails;
		ArrayList<String> reportsAvailable = ReportVersionConstants.availableReports;
		ArrayList<ReportIdName> availableReportDetails = new ArrayList<ReportIdName>();
		for (int i = 0; i < reportIdNames.size(); i++) {
			ReportIdName reportIdName = reportIdNames.get(i);
			for (int k = 0; k < reportsAvailable.size(); k++) {
				if (reportIdName.getReportId().equals(reportsAvailable.get(k))) {
					ReportIdName reportIdNameData = new ReportIdName(reportIdName.getReportId(), reportIdName.getReportName(), reportIdName.getReportCategory());
					availableReportDetails.add(reportIdNameData);
				}
			}
		}
		ReportDesignManager reportDesignManager = new ReportDesignManager();
		ArrayList<ReportData> customReports = reportDesignManager.getAllReports();
		ReportIdName reportIdName = null;
		for (ReportData reportData :customReports) {
			reportIdName = new ReportIdName(reportData.getReportId(), reportData.getReportName(), ReportVersionConstants.CATEGORY_REPORT_CUSTOM,1);
			availableReportDetails.add(reportIdName);
		}
		
		CustomReportManager customReportManager = new CustomReportManager();
		ArrayList<CustomReportData> summaryReports = customReportManager.getAllCustomReports();
		for (CustomReportData customReportData : summaryReports) {
			reportIdName = new ReportIdName(customReportData.getReportId(), customReportData.getReportName(), ReportVersionConstants.CATEGORY_REPORT_SUMMARY, 2);
			availableReportDetails.add(reportIdName);
		}
		
		CustomizedReportManager crm = new CustomizedReportManager();
		ArrayList<CustomizedReportData> crds = crm.getAllCustomizedReports();
		for(CustomizedReportData crd : crds) {
			reportIdName = new ReportIdName(crd.getReportId(), crd.getReportLabel(), 
					ReportVersionConstants.CATEGORY_REPORT_CUSTOMIZED, 
					ReportVersionConstants.REPORT_TYPE_CUSTOMIZED);
			availableReportDetails.add(reportIdName);
		}
		
		return availableReportDetails;
	}

	public ActionForward saveLevel(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";
		// Do the validations here
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			// If levelId is set then submit Edited else create new User
			String loggedInUser = (String) request.getSession().getAttribute("userId");
			AuditAction auditAction = new AuditAction();
			AdminForm adminForm = (AdminForm) actionForm;
			String levelId = adminForm.getLevelId();
			String pipeSeparatedlevelReportID = adminForm.getLevelsReportId();
			String[] separatedlevelReportID = pipeSeparatedlevelReportID.split(",");
			if (Utils.isBlankOrNull(adminForm.getLevelName()) || adminForm.getLevelName().length() > 50) {
				errors.add("add_level.errors.short_levelName", new ActionError("add_level.errors.short_levelName"));
			}
			if (Utils.isBlankOrNull(pipeSeparatedlevelReportID)) {
				errors.add("add_level.errors.select_report", new ActionError("add_level.errors.select_report"));
			}
			/*
			 * Save The record If valid Data First Create new Level if Level ID
			 * Is null
			 */
			if (errors.size() == 0) {
				ReportLevelData reportLevelData = new ReportLevelData();
				reportLevelData.setLevelName(adminForm.getLevelName());
				AdminManager adminManager = new AdminManager();
				String clientIpAddr = getClientIpAddr(request);
				if (Utils.isBlankOrNull(levelId)) {
					levelId = adminManager.createLevel(reportLevelData, separatedlevelReportID);
					auditAction.insertAuditInfo(TPLabels.getLabel("common.level"), AuditConstants.TYPE_ADDED, levelId,
							AuditConstants.AUDIT_REPORT_LEVEL, loggedInUser, null, null, null, false, clientIpAddr);
					
				} else {
					adminManager.updateLevel(levelId, reportLevelData, separatedlevelReportID);
					auditAction.insertAuditInfo(TPLabels.getLabel("common.level"), AuditConstants.TYPE_MODIFIED, levelId, 
							AuditConstants.AUDIT_REPORT_LEVEL, loggedInUser, null, null, null, false, clientIpAddr);
				}
			}
		} catch (SQLException sqle) {
			errors.add("add_level.errors.duplicate_levelname", new ActionError("add_level.errors.duplicate_levelname"));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Saving Level Data ", e);
			errors.add("add_level.errors.level", new ActionError("add_level.errors.level"));
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return addLevel(mapping, actionForm, request, response);
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
	public ActionForward deleteLevel(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		// Do the validations here
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			String loggedInUser = (String) request.getSession().getAttribute("userId");
			AdminForm adminForm = (AdminForm) actionForm;
			String levelId = adminForm.getLevelId();
			AdminManager adminManager = new AdminManager();
			adminManager.removeLevel(levelId);
			
			AuditAction auditAction = new AuditAction();
			String clientIpAddr = getClientIpAddr(request);
			auditAction.insertAuditInfo(TPLabels.getLabel("common.level"), AuditConstants.TYPE_DELETED, levelId,
					AuditConstants.AUDIT_REPORT_LEVEL, loggedInUser, null, null, null, false, clientIpAddr);
			
			xmlFile = Utils.getXMLForIds(levelId);
		}/*
		 * catch (SQLException sqle) {
		 * errors.add("add_level.errors.duplicate_levelname", new
		 * ActionError("add_level.errors.duplicate_levelname")); }
		 */
		catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting Level", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);

	}

	private String getJSArrayRole() {
		String jsArrayRole = "new Array()";
		AdminManager adminManager = new AdminManager();
		ArrayList<RoleData> roles = adminManager.getAllRoles();
		for (int i = 0; roles != null && i < roles.size(); i++) {
			RoleData role = (RoleData) roles.get(i);
			if (!ModuleSet.isMODULE_VENDOR() && role.getRoleId() == UserConstants.ROLE_VENDOR) {
				roles.remove(i);
				i--;
			}
		}
		jsArrayRole = CommonUtils.getListJavaScriptArrayWithProperties(roles, "roleId", "roleTitle");
		return jsArrayRole;
	}

	public ActionForward manageMessages(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "manageMessages";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_LABEL_MESSAGES;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		request.setAttribute("t", NavigationConstants.T_ADMIN);
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_MESSAGES);
		return mapping.findForward(forward);
	}

	public ActionForward getMessages(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String xmlFile = new String();
		AdminManager manager = new AdminManager();
		xmlFile = manager.getXMLForMessages();
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward editMessage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "editMessage";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_LABEL_MESSAGES;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}

		return mapping.findForward(forward);
	}

	public ActionForward saveMessage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "editMessage";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_LABEL_MESSAGES;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		AdminForm adminForm = (AdminForm) actionForm;
		try {
			String key = adminForm.getKey();
			String value = adminForm.getValue();

			AdminManager manager = new AdminManager();
			manager.addOrUpdateLabel(key, value);

			request.setAttribute("update", "1");
			// ((TPPropertyMessageResources)getResources(request)).reload(null);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving label", e);
			ActionErrors errors = new ActionErrors();
			errors.add("admin.messages.error.save_label", new ActionError("admin.messages.error.save_label"));
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}

	public ActionForward restoreDefault(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		try {
			AdminManager adminManager = new AdminManager();
			adminManager.deleteCustomLabels();
			Properties properties = Utils.loadAppliocationLabels("talentpoollabels.txt");
			Utils.saveAppliocationLabels(properties);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting custom labels", e);
			ActionErrors errors = new ActionErrors();
			errors.add("admin.messages.error.restore_default", new ActionError("admin.messages.error.restore_default"));
			saveErrors(request, errors);
		}
		return manageMessages(mapping, actionForm, request, response);
	}

	public ActionForward manageScreenConfiguration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "importScreenConfiguration";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_SCREEN_CONFIGURATION;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			AdminForm adminForm = (AdminForm) actionForm;
			if (!Utils.isBlankOrNull(request.getParameter("isSubmitted"))) {
				ArrayList<ImportFieldData> array = new ArrayList<ImportFieldData>();
				String importFieldsString = adminForm.getImportFieldsString();
				String[] _fields = importFieldsString.split(",");
				if (_fields != null && _fields.length > 0) {
					for (int i = 0; i < _fields.length; i++) {
						String _field = _fields[i];
						if (!Utils.isBlankOrNull(_field)) {
							String[] attributes = _field.split("[|]");
							if (attributes != null && attributes.length > 0) {
								ImportFieldData importData = new ImportFieldData();
								importData.setFieldId(Utils.getFormattedRecord(attributes[0]).trim());
								importData.setFieldType(Utils.getFormattedRecord(attributes[1]).trim());
								importData.setFieldImportShow(Utils.getFormattedRecord(attributes[2]).trim());
								importData.setFieldEditShow(Utils.getFormattedRecord(attributes[3]).trim());
								importData.setFieldImportMandatory(Utils.getFormattedRecord(attributes[4]).trim());
								importData.setFieldVendorShow(Utils.getFormattedRecord(attributes[5]).trim());
								importData.setFieldVendorMandatory(Utils.getFormattedRecord(attributes[6]).trim());								
								importData.setFieldEmployeeShow(Utils.getFormattedRecord(attributes[7]).trim());								
								importData.setFieldEmployeeMandatory(Utils.getFormattedRecord(attributes[8]).trim());
								importData.setFieldWebsiteShow(Utils.getFormattedRecord(attributes[9]).trim());								
								importData.setFieldWebsiteMandatory(Utils.getFormattedRecord(attributes[10]).trim());
								importData.setFieldConfidential(Utils.getFormattedRecord(attributes[11]).trim());
								importData.setFieldRank("" + (i + 1));
								array.add(importData);
							}
						}
					}
					// update settings
					AdminManager adminManager = new AdminManager();
					adminManager.updateImportConfiguration(array);

					// update custom fields - not in use any more
					// updateCustomFieldsForImportScreen(array);

				}
				ImportConfigurationManager.reloadImportFieldsMaps();
				DuplicateSettings.loadDuplicateSettings();
				request.setAttribute("update", "1");
			}

			ArrayList<ImportFieldData> fieldList = ImportConfigurationManager.getImportFields();			
			adminForm.setFieldList(fieldList);

			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_SCREEN_CONFIGURATION);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward managePositionScreen(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "positionScreen";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_PUBLISH_POSITION_TO_WEB_SITE;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			AdminForm adminForm = (AdminForm) actionForm;
			if (!Utils.isBlankOrNull(request.getParameter("isSubmitted"))) {
				/* start updating position fields for list view */
				String positionScreenFieldsString = adminForm.getPositionScreenFieldsString();
				ArrayList<PositionFieldData> array = new ArrayList<PositionFieldData>();
				String[] _fields = positionScreenFieldsString.split(",");
				if (_fields != null && _fields.length > 0) {
					for (int i = 0; i < _fields.length; i++) {
						String _field = _fields[i];
						if (!Utils.isBlankOrNull(_field)) {
							String[] attributes = _field.split("[|]");
							if (attributes != null && attributes.length > 0) {
								PositionFieldData positionData = new PositionFieldData();
								positionData.setFieldId(Utils.getFormattedRecord(attributes[0]).trim());
								positionData.setFieldType(Utils.getFormattedRecord(attributes[1]).trim());
								positionData.setFieldOnPositionPrintShow(Utils.getFormattedRecord(attributes[2]).trim());
								positionData.setFieldPositionShow(Utils.getFormattedRecord(attributes[3]).trim());
								positionData.setFieldPositionMandatory(Utils.getFormattedRecord(attributes[4]).trim());
								positionData.setFieldVendorShow(Utils.getFormattedRecord(attributes[5]).trim());
								positionData.setFieldEmployeeShow(Utils.getFormattedRecord(attributes[6]).trim());
								positionData.setFieldRank("" + (i + 1));
								array.add(positionData);
							}
						}
					}
					AdminManager adminManager = new AdminManager();
					if (Utils.isBlankOrNull(adminForm.getPositionScreenViewOption())
							|| PositionConfigurationConstants.VIEW_OPTION_DESCRIPTION.equals(adminForm.getPositionScreenViewOption())) {
						adminManager.updatePositionDescriptionScreenConfiguration(array);					
					}else if(PositionConfigurationConstants.VIEW_OPTION_PRINT.equals(adminForm.getPositionScreenViewOption())){
						adminManager.updatePositionScreenConfiguration(array);
					}else if(PositionConfigurationConstants.VIEW_OPTION_REQUIREMENTS.equals(adminForm.getPositionScreenViewOption())){
						adminManager.updatePositionRequirementsScreenConfiguration(array);
					} else if(PositionConfigurationConstants.VIEW_OPTION_VENDOR.equals(adminForm.getPositionScreenViewOption())
						|| PositionConfigurationConstants.VIEW_OPTION_EMPLOYEE.equals(adminForm.getPositionScreenViewOption())) {
						adminManager.updatePositionDescriptionScreenConfiguration(array);						
						adminManager.updatePositionRequirementsScreenConfiguration(array);
					}
				}
				
				if (Utils.isBlankOrNull(adminForm.getPositionScreenViewOption())
						|| PositionConfigurationConstants.VIEW_OPTION_DESCRIPTION.equals(adminForm.getPositionScreenViewOption())) {
					PositionScreenConfigurationManager.reloadPositionDescriptionFields();
				}else if(PositionConfigurationConstants.VIEW_OPTION_PRINT.equals(adminForm.getPositionScreenViewOption())){
					PositionScreenConfigurationManager.reloadPositionScreenFields();
				}else if(PositionConfigurationConstants.VIEW_OPTION_REQUIREMENTS.equals(adminForm.getPositionScreenViewOption())){
					PositionScreenConfigurationManager.reloadPositionRequirementsFields();
				} else if (PositionConfigurationConstants.VIEW_OPTION_VENDOR.equals(adminForm.getPositionScreenViewOption())
						|| PositionConfigurationConstants.VIEW_OPTION_EMPLOYEE.equals(adminForm.getPositionScreenViewOption())) {
					PositionScreenConfigurationManager.reloadPositionDescriptionFields();
					PositionScreenConfigurationManager.reloadPositionRequirementsFields();
				}
				request.setAttribute("update", "1");
			}
			
			ArrayList<PositionFieldData> posScreenFieldList = null;
			
			if (Utils.isBlankOrNull(adminForm.getPositionScreenViewOption())
					|| PositionConfigurationConstants.VIEW_OPTION_DESCRIPTION.equals(adminForm.getPositionScreenViewOption())) {
				posScreenFieldList = PositionScreenConfigurationManager.getPositionDescriptionFields();				
			}else if(PositionConfigurationConstants.VIEW_OPTION_PRINT.equals(adminForm.getPositionScreenViewOption())){
				posScreenFieldList = PositionScreenConfigurationManager.getPositionScreenFields();
			}else if(PositionConfigurationConstants.VIEW_OPTION_REQUIREMENTS.equals(adminForm.getPositionScreenViewOption())){
				posScreenFieldList = PositionScreenConfigurationManager.getPositionRequirementsFields();
			} else if(PositionConfigurationConstants.VIEW_OPTION_VENDOR.equals(adminForm.getPositionScreenViewOption())
					|| PositionConfigurationConstants.VIEW_OPTION_EMPLOYEE.equals(adminForm.getPositionScreenViewOption())) {
				ArrayList<PositionFieldData> posDesFieldList = PositionScreenConfigurationManager.getPositionDescriptionFields();
				ArrayList<PositionFieldData> posReqFieldList = PositionScreenConfigurationManager.getPositionRequirementsFields();
				
				posScreenFieldList = new ArrayList<PositionFieldData>(posDesFieldList);
				posScreenFieldList.addAll(posReqFieldList);
			}
			adminForm.setPositionListFieldList(posScreenFieldList);
			
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_POSITIONS_SCREEN_CONFIGURATION);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward managePositionScreenConfiguration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "positionScreenConfiguration";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_PUBLISH_POSITION_TO_WEB_SITE;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			AdminForm adminForm = (AdminForm) actionForm;
			WebsiteScreenSettingsManager websiteScreenManager = new WebsiteScreenSettingsManager();
			if (!Utils.isBlankOrNull(request.getParameter("isSubmitted"))) {
				
				/* start updating position fields for list view in website*/
				String positionFieldsString = adminForm.getPositionListFieldsString();
				ArrayList<PositionFieldData> array = new ArrayList<PositionFieldData>();
				String[] _fields = positionFieldsString.split(",");
				if (_fields != null && _fields.length > 0) {
					for (int i = 0; i < _fields.length; i++) {
						String _field = _fields[i];
						if (!Utils.isBlankOrNull(_field)) {
							String[] attributes = _field.split("[|]");
							if (attributes != null && attributes.length > 0) {
								PositionFieldData positionData = new PositionFieldData();
								positionData.setFieldId(Utils.getFormattedRecord(attributes[0]).trim());
								positionData.setFieldType(Utils.getFormattedRecord(attributes[1]).trim());
								positionData.setFieldOnPositionListShow(Utils.getFormattedRecord(attributes[2]).trim());
								positionData.setFieldIsFilter(Utils.getFormattedRecord(attributes[3]).trim());
								positionData.setFieldIsFilterEditable(Utils.getFormattedRecord(attributes[4]).trim());
								positionData.setFieldRank("" + (i + 1));
								array.add(positionData);
							}
						}
					}
					AdminManager adminManager = new AdminManager();
					adminManager.updatePositionListConfiguration(array);
				}
				
				/* start updating position fields for Details view in website*/
				String positionDetailsFieldsString = adminForm.getPositionDetailsFieldsString();
				array.clear();
				_fields = positionDetailsFieldsString.split(",");
				if (_fields != null && _fields.length > 0) {
					for (int i = 0; i < _fields.length; i++) {
						String _field = _fields[i];
						if (!Utils.isBlankOrNull(_field)) {
							String[] attributes = _field.split("[|]");
							if (attributes != null && attributes.length > 0) {
								PositionFieldData positionData = new PositionFieldData();
								positionData.setFieldId(Utils.getFormattedRecord(attributes[0]).trim());
								positionData.setFieldType(Utils.getFormattedRecord(attributes[1]).trim());
								positionData.setFieldOnPositionDetailsShow(Utils.getFormattedRecord(attributes[2]).trim());
								positionData.setFieldPositionDetailsRank("" + (i + 1));
								array.add(positionData);
							}
						}
					}
					AdminManager adminManager = new AdminManager();
					adminManager.updatePositionDetailsConfiguration(array);
				}
				PositionScreenConfigurationManager.reloadPositionFieldsMaps();
				
				/* start saving web site screen settings*/
				String isShowLabels = adminForm.getIsShowLabel();
				String firstLineFieldId = adminForm.getFirstLineFieldId();
				if(firstLineFieldId.equalsIgnoreCase("-1")){
					adminForm.setFirstLineField(adminForm.getOtherFieldValue().trim());
				}else{
					adminForm.setFirstLineField(firstLineFieldId);
				}
				websiteScreenManager.updateSettings(isShowLabels,adminForm.getFirstLineField(),adminForm.getIsOtherField());
				
				request.setAttribute("update", "1");
			}
			
			ArrayList<PositionFieldData> posListFieldList = PositionScreenConfigurationManager.getPositionListFields();
			adminForm.setPositionListFieldList(posListFieldList);
			
			ArrayList<PositionFieldData> posDetailsFieldList = PositionScreenConfigurationManager.getPositionDetailsFields();
			adminForm.setPositionDetailsFieldList(posDetailsFieldList);

			SimpleDataObject sdo = websiteScreenManager.getWebsiteSettings();
			populateForm(adminForm,sdo); 
			
			String jsArrayPositionFields = PositionUtils.getJSArrayForPositionFields();
			adminForm.setJsArrayPositionFields(jsArrayPositionFields);
			
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_POSITION_SCREEN_CONFIGURATION);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	private void populateForm(AdminForm adminForm, SimpleDataObject sdo){
		adminForm.setIsShowLabel((String)sdo.getAttribute("isShowLabels"));
		adminForm.setFirstLineField((String)sdo.getAttribute("fisrtFieldName"));
		String isOther = (String)sdo.getAttribute("isOther");
		adminForm.setIsOtherField(isOther);
		if(isOther.equalsIgnoreCase(PositionConfigurationConstants.OTHER_SELECTED)){
			adminForm.setOtherFieldValue(adminForm.getFirstLineField());
			adminForm.setFirstLineField("-1");
		}else{
			adminForm.setOtherFieldValue("");
		}
	}
	
	public ActionForward getLdapUserAutoCompleteList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		try {
			String xmlFile = "";
			AdminForm adminForm = (AdminForm) actionForm;
			// String userNameLike = adminForm.getUserName();
			LDAPQueryProcessor ldapQuery = new LDAPQueryProcessor();
			LDAPManager manager = new LDAPManager();
			ArrayList<LDAPServerData> serverList = manager.getLDAPServerList();
			String loggedInUser = (String) request.getSession().getAttribute("username");
			String loggedInUserPass = (String) request.getSession().getAttribute("userpassword");

			ArrayList<LDAPUserData> users = ldapQuery.getUsersList(serverList, LDAPConstants.SEARCH_BY_USER_NAME, adminForm.getUserName().trim(), loggedInUser, loggedInUserPass);

			// startsWith comparison is case-sensitive
			// commenting it to get case-insensitive list of users
			/*ArrayList<LDAPUserData> newUsers = new ArrayList<LDAPUserData>();
			if(users!=null && users.size()>0){
				for (int i = 0; i < users.size(); i++) {
					LDAPUserData ldapData = users.get(i);
					if(ldapData.getSamAccountName().startsWith(userNameLike)){
						newUsers.add(ldapData);
					}
				}	
			}*/			
			xmlFile = getListForUserInXML(users);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);

		}
		return mapping.findForward(forward);
	}
	
	public static String getListForUserInXML(ArrayList lst) {
		StringWriter sWr = new StringWriter();
		try {
			if (lst != null) {
				XMLWriter wr = new XMLWriter(sWr);
				wr.startDocument();
				wr.startElement("ul");
				for (int i = 0; i < lst.size(); i++) {
					SimpleDataObject sdo = (SimpleDataObject) lst.get(i);
					String userName = sdo.getString(LDAPConstants.LDAP_SAM_ACCOUNT_NAME);
					String firstName = sdo.getString(LDAPConstants.LDAP_GIVEN_NAME);
					String lastName = sdo.getString(LDAPConstants.LDAP_SUR_NAME);
					String email = sdo.getString(LDAPConstants.LDAP_USER_EMAIL);
					
					wr.startElement("li");
					wr.characters(userName+"("+firstName + " " + lastName + ", " + email +")");
					wr.endElement("li");
				}
				wr.endElement("ul");
				wr.endDocument();
			}
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public ActionForward websitePositionHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "websitePositionHome";		
		AdminForm form = (AdminForm) actionForm;
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		int module = ModuleConstants.MODULE_WEB_INTEGRATION;
		permissions[1] = PermissionConstants.PERMISSION_PUBLISH_POSITION_TO_WEB_SITE;
				
		if(!isUserAuthorized(request, module, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			WebsiteScreenSettingsManager websiteScreenManager = new WebsiteScreenSettingsManager();
			String positionsHomeHeader = websiteScreenManager.getWebsitePositionsHomeHeader();
			form.setPositionsHomeHeader(positionsHomeHeader);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("position.publish_for_walk_in.error.get_announcements");
		    errors.add("position.publish_for_walk_in.error.get_announcements", error);
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward savePositionsHomeHeader(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "websitePositionHome";		
		AdminForm form = (AdminForm) actionForm;
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_POSITIONS;
		int module = ModuleConstants.MODULE_WEB_INTEGRATION;
		permissions[1] = PermissionConstants.PERMISSION_PUBLISH_POSITION_TO_WEB_SITE;
				
		if(!isUserAuthorized(request, module, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			WebsiteScreenSettingsManager websiteScreenManager = new WebsiteScreenSettingsManager();
			websiteScreenManager.updateWebsitePositionsHomeHeader(form.getPositionsHomeHeader());
			request.setAttribute("update", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		/*	ActionErrors errors = new ActionErrors();
			ActionError error = new ActionError("position.publish_for_walk_in.error.update_announcements");
		    errors.add("position.publish_for_walk_in.error.update_announcements", error);
			saveErrors(request, errors);*/
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward displayTodayDate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				xmlFile = DateUtils.getDateFormated(new Date(),DateUtils.getPattern(request.getParameter("datePatternId")));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while determining whether position step is schedulable or not", e);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward timeZoneSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "timeZoneSettings";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			AdminForm form = (AdminForm) actionForm;
			
			String timeZoneId = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_SELECTED_TIME_ZONES);
			form.setTimeZoneId(timeZoneId);
			
			String [] ids = TimeZone.getAvailableIDs();
			ArrayList<SimpleDataObject> zones = new ArrayList<SimpleDataObject>();
			for(String id:ids) {
				SimpleDataObject sdo = new SimpleDataObject();
				sdo.setAttribute("zoneId", id);
				TimeZone zone = TimeZone.getTimeZone(id);
				int offset = 0;
				if (zone.inDaylightTime(new Date())){
					offset = (zone.getRawOffset() + zone.getDSTSavings())/1000;
				} else {
					offset = zone.getRawOffset()/1000;
				}
				int hour = offset/3600;
				int minutes = (offset % 3600)/60;
				sdo.setAttribute("zoneValue", String.format("(GMT%+d:%02d) %s", hour, minutes, id));
				zones.add(sdo);
			}
			String timeZoneXML = XmlUtils.getXMLFromMasterDataList(zones, "zoneId", "zoneValue");
			timeZoneXML = timeZoneXML.replace("\n", "").replace("\r", "");
			form.setTimeZoneXML(timeZoneXML);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveTimeZoneSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "timeZoneSettings";
		
		AdminForm form = (AdminForm) actionForm;
		
		try {
			AdminManager adminManager = new AdminManager();
			String timeZoneIds = form.getTimeZoneId();
			adminManager.updateApplicationProperty(GlobalConstants.PROPERTY_ALL_SELECTED_TIME_ZONES, timeZoneIds, null);
			
			String [] ids = TimeZone.getAvailableIDs();
			ArrayList<SimpleDataObject> zones = new ArrayList<SimpleDataObject>();
			for(String id:ids) {
				SimpleDataObject sdo = new SimpleDataObject();
				sdo.setAttribute("zoneId", id);
				TimeZone zone = TimeZone.getTimeZone(id);
				int offset = zone.getRawOffset()/1000;
				int hour = offset/3600;
				int minutes = (offset % 3600)/60;
				sdo.setAttribute("zoneValue", String.format("(GMT%+d:%02d) %s", hour, minutes, id));
				zones.add(sdo);
			}
			String timeZoneXML = XmlUtils.getXMLFromMasterDataList(zones, "zoneId", "zoneValue");
			timeZoneXML = timeZoneXML.replace("\n", "").replace("\r", "");
			form.setTimeZoneXML(timeZoneXML);
			GlobalApplicationProperties.resetPropertiesMap();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ActionErrors errors = new ActionErrors();
			errors.add("master_roles.error.save_role", new ActionError("master_roles.error.save_role"));
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward resetWebsiteCache(ActionMapping mapping,ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "resetWebsiteCache";
		request.setAttribute("mainPane",NavigationConstants.MAINPANE_ADMIN_RESET_WEBSITE_CACHE);
		AdminForm form = (AdminForm) actionForm;
		String flag = form.getSubmitFlag();
		if("1".equals(flag)){
			try{
				Client client = Client.create();
				WebResource webResource = client.resource(TPApplicationProperties.getProperty("candidate.portal.reset.appFields.url"));
				ClientResponse response1 = webResource.type("text/plain").get(ClientResponse.class);
				String res = response1.getEntity(String.class);
				TPLogger.getLogger().debug("Applicant add index event push status : " + res);
				response1.close();
				if(HttpStatus.SC_OK == response1.getClientResponseStatus().getStatusCode()){
					request.setAttribute("taskComplete", "1");
				}else{
					throw new Exception("failed to reset cache");
				}
				
			}catch(Exception e){
				TPLogger.getLogger().debug("URL is -"+TPApplicationProperties.getProperty("candidate.portal.reset.appFields.url"));
				TPLogger.getLogger().debug("Error while reseting website cache");
				request.setAttribute("taskComplete", "0");
			}
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward resetPositionFieldMapCache(ActionMapping mapping,ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "resetPositionFieldMapCache";
		request.setAttribute("mainPane",NavigationConstants.MAINPANE_ADMIN_RESET_WEBSITE_CACHE);
		AdminForm form = (AdminForm) actionForm;
		String flag = form.getSubmitFlag();
		if("1".equals(flag)){
			try{
				Client client = Client.create();
				WebResource webResource = client.resource(TPApplicationProperties.getProperty("candidate.portal.resetPositionFieldMapCache.appFields.url"));
				ClientResponse response1 = webResource.type("text/plain").get(ClientResponse.class);
				String res = response1.getEntity(String.class);
				TPLogger.getLogger().debug("reset Position Field Map event push status : " + res);
				response1.close();
				if(HttpStatus.SC_OK == response1.getClientResponseStatus().getStatusCode()){
					request.setAttribute("taskComplete", "1");
				}else{
					throw new Exception("failed to reset cache");
				}
				
			}catch(Exception e){
				TPLogger.getLogger().debug("URL is -"+TPApplicationProperties.getProperty("candidate.portal.resetPositionFieldMapCache.appFields.url"));
				TPLogger.getLogger().debug("Error while reseting position field map cache" );
				request.setAttribute("taskComplete", "0");
			}
		}
		return mapping.findForward(forward);
	}
}
