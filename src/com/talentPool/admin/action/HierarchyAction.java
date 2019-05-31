/**
 * 
 */
package com.talentPool.admin.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.admin.dataobject.HierarchyData;
import com.talentPool.admin.form.AdminForm;
import com.talentPool.admin.manager.AdminManager;
import com.talentPool.admin.manager.HierarchyManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class HierarchyAction extends TPDispatchAction {

	public ActionForward manageUserHierarchy(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "manageUserHierarchy";
		try{
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;		
			} else {
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
				if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
				}
		
				HierarchyManager hierarchyManager = new HierarchyManager();
				String hierarchy = hierarchyManager.getHierarchy();
	
				AdminManager adminManager = new AdminManager();
				SimpleDataObject sDo = adminManager.getInboxSettings();
				request.setAttribute("hierarchy", hierarchy);
				if(sDo!=null && !Utils.isBlankOrNull(sDo.getString("inboxDisplayName"))){
					request.setAttribute("companyName", sDo.getString("inboxDisplayName"));	
				}
				else{
					request.setAttribute("companyName", "");
				}
				request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_MANAGE_USERS_HIERARCHY);
				request.setAttribute("t", NavigationConstants.T_ADMIN);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward addUserToHierarchy(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addUserToHierarchy";
		try {
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;
			}		
			else{
				
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
				
				HierarchyManager hierarchyManager = new HierarchyManager();
				AdminManager adminManager = new AdminManager();
				SimpleDataObject sDo = adminManager.getInboxSettings();
				ArrayList<HierarchyData> unManagedUser = hierarchyManager.getUnManagedUser();
				ArrayList<HierarchyData> managedUser = hierarchyManager.getManagedUser();
				String jsUnManagedUser = CommonUtils.getListJavaScriptArrayWithProperties(unManagedUser, "userId", "name");
				String jsManagedUser = CommonUtils.getListJavaScriptArrayWithProperties(managedUser, "userId", "name");
				request.setAttribute("jsUnManagedUser", jsUnManagedUser);
				request.setAttribute("jsManagedUser", jsManagedUser);
				if(sDo!=null && !Utils.isBlankOrNull(sDo.getString("inboxDisplayName"))){
					request.setAttribute("companyName", sDo.getString("inboxDisplayName"));	
				}
				else{
					request.setAttribute("companyName", "");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveUserToHierarchy(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "closeModalCall";
		try {
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;
			}		
			else{
				
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
				if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
					forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
				AdminForm adminForm = (AdminForm) actionForm;
				String userId = adminForm.getUserId();
				String parentId = adminForm.getParentId();
				HierarchyManager hierarchyManager = new HierarchyManager();
				hierarchyManager.saveUserToHierarchy(userId, parentId, null);
				request.setAttribute("update", "1");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward moveUserInHierarchy(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "moveUserInHierarchy";
		try {
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;
			}		
			else{
				
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
				
				HierarchyManager hierarchyManager = new HierarchyManager();
				ArrayList<HierarchyData> managedUser = hierarchyManager.getManagedUser();
				AdminManager adminManager = new AdminManager();
				SimpleDataObject sDo = adminManager.getInboxSettings();
				// String jsManagedUser =
				// CommonUtils.getListJavaScriptArrayWithProperties(managedUser,
				// "userId", "name");
				String jsUserToMove = CommonUtils.getListJavaScriptArrayWithProperties(managedUser, "userId", "name");
				request.setAttribute("jsUserToMove", jsUserToMove);
				// request.setAttribute("jsManagedUser", jsManagedUser);
				if(sDo!=null && !Utils.isBlankOrNull(sDo.getString("inboxDisplayName"))){
					request.setAttribute("companyName", sDo.getString("inboxDisplayName"));	
				}
				else{
					request.setAttribute("companyName", "");
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getXMLAssignTouser(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		String JsArray = "new Array()";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				AdminForm adminForm = (AdminForm) actionForm;
				String userId = adminForm.getUserId();
				HierarchyManager hierarchyManager = new HierarchyManager();
				ArrayList<HierarchyData> assignToUsers = hierarchyManager.getXMLAssignToUser(userId);
				JsArray = CommonUtils.getListJavaScriptArrayWithProperties(assignToUsers, "userId", "name");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		xmlFile = Utils.getXMLForContent("content", JsArray);
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward saveMovedUserToHierarchy(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "closeModalCall";
		try {
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;
			}		
			else{
				
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
				if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
				
				AdminForm adminForm = (AdminForm) actionForm;
				String userId = adminForm.getUserId();
				String parentId = adminForm.getParentId();
				HierarchyManager hierarchyManager = new HierarchyManager();
				hierarchyManager.saveMovedUserToHierarchy(userId, parentId);
				request.setAttribute("update", "1");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward removeUserFromHierarchy(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "removeUserFromHierarchy";
		try {
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;
			}		
			else{
				
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
				
				HierarchyManager hierarchyManager = new HierarchyManager();
				ArrayList<HierarchyData> removableUsers = hierarchyManager.getRemovableUsers();
				String jsRemovableUsers = CommonUtils.getListJavaScriptArrayWithProperties(removableUsers, "orgId", "name");
				request.setAttribute("jsRemovableUsers", jsRemovableUsers);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward deleteUserFromHierarchy(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "closeModalCall";
		try {
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;
			}		
			else{
				
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_ADMIN;
				permissions[1] = PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY;
				if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
			}
				
				AdminForm adminForm = (AdminForm) actionForm;
				String orgId = adminForm.getOrgId();
				HierarchyManager hierarchyManager = new HierarchyManager();
				hierarchyManager.deleteUserFromHierarchy(orgId);
				request.setAttribute("update", "1");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
}
