/**
 * 
 */
package com.talentPool.ldap.action;

import java.security.KeyPair;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.CommunicationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.encryption.JCryptionUtil;
import com.talentPool.ldap.LDAPQueryProcessor;
import com.talentPool.ldap.dataobject.LDAPServerData;
import com.talentPool.ldap.form.LDAPForm;
import com.talentPool.ldap.manager.LDAPManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shivprasad
 * 
 */
public class LDAPAction extends TPDispatchAction {
	public ActionForward ldapSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "ldapSettings";
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_ADMIN;
		permissions[1] = PermissionConstants.PERMISSION_LDAP_SETTINGS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_LDAP, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_LDAP_SETTINGS);
			request.setAttribute(GlobalConstants.PROPERTY_LDAP_ENABLED, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED));

		} catch (Exception e) {
			TPLogger.getLogger().error("Error While display ldap settings", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getLdapServerXMLFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				LDAPManager manager = new LDAPManager();
				ArrayList<LDAPServerData> serverList = manager.getLDAPServerList();
				xmlFile = manager.getLdapServerXML(serverList);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting user", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward setLdapEnabled(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "ldapSettings";
		try {
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_LDAP_SETTINGS);
			String ldapEnabled = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED);
			LDAPManager manager = new LDAPManager();
			ArrayList<LDAPServerData> ldapServerList = manager.getLDAPServerList();
			if (ldapEnabled.equals(GlobalConstants.DISABLED)) {
				ldapEnabled = GlobalConstants.ENABLED;
			} else {
				ldapEnabled = GlobalConstants.DISABLED;
			}
			if (ldapServerList.size() > 0 || ldapEnabled == GlobalConstants.DISABLED) {
				HashMap<String, String> props = new HashMap<String, String>();
				props.put(GlobalConstants.PROPERTY_LDAP_ENABLED, ldapEnabled);
				AdminManager adminManager = new AdminManager();
				adminManager.updateGlobalSettings(props);
				GlobalApplicationProperties.resetPropertiesMap();
				request.setAttribute("update", "1");
				request.setAttribute(GlobalConstants.PROPERTY_LDAP_ENABLED, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED));
			} else {
				forward = "addLdapServer";
				LDAPForm ldapForm = (LDAPForm) actionForm;
				ldapForm.setLdapEnabled(ldapEnabled);
			}

		} catch (Exception e) {
			e.printStackTrace();
			TPLogger.getLogger().error("Error While display ldap settings", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward addLdapServer(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addLdapServer";
		try {
			LDAPManager manager = new LDAPManager();
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_LDAP_SETTINGS);
			LDAPForm ldapForm = (LDAPForm) actionForm;
			if (!Utils.isBlankOrNull(request.getParameter("isSubmitted"))) {
				// add or update ldap server data
				ActionErrors errors = (ActionErrors) request.getAttribute(Globals.ERROR_KEY);
				if (errors == null) {
					errors = new ActionErrors();
				}
				if (Utils.isBlankOrNull(ldapForm.getServerURL())) {
					errors.add("admin.ldap_settings.error.please_enter_server_url", new ActionError("admin.ldap_settings.error.please_enter_server_url"));
				}
				if (Utils.isBlankOrNull(ldapForm.getSecurityPrincipal())) {
					errors.add("admin.ldap_settings.error.please_enter_security_principal", new ActionError("admin.ldap_settings.error.please_enter_security_principal"));
				}
				if (errors.size() == 0) {
					LDAPServerData ldapDO = new LDAPServerData();
					ldapDO.setServerId(ldapForm.getServerId());
					ldapDO.setServerURL(ldapForm.getServerURL());
					ldapDO.setSecurityPrincipal(ldapForm.getSecurityPrincipal());
					if (Utils.isBlankOrNull(ldapForm.getServerId())) {
						// add new server
						ArrayList<LDAPServerData> ldapServerList = manager.getLDAPServerList();
						if (ldapServerList.size() == 0) {
							return setAdminUserName(mapping, actionForm, request, response);
						} else {
							manager.addLDAPServerInfo(ldapDO);
						}
					} else {
						// update server data
						manager.updateLDAPServerInfo(ldapDO);
					}
					return ldapSettings(mapping, actionForm, request, response);
				} else {
					request.setAttribute(Globals.ERROR_KEY, errors);
				}
			} else {
				if (!Utils.isBlankOrNull(ldapForm.getServerId())) {
					LDAPServerData ldapServerData = manager.getLDAPServerData(ldapForm.getServerId());
					if (ldapServerData != null) {
						ldapForm.setServerURL(ldapServerData.getServerURL());
						ldapForm.setSecurityPrincipal(ldapServerData.getSecurityPrincipal());
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			TPLogger.getLogger().error("Error While display ldap settings", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward setAdminUserName(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "setAdminUserName";
		try {
			request.setAttribute("t", NavigationConstants.T_ADMIN);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_ADMIN_LDAP_SETTINGS);
			LDAPForm ldapForm = (LDAPForm) actionForm;
			String userPassword = ldapForm.getAdminPassword();
			int rnd = ldapForm.getRnd();
			KeyPair keys = null;
			if (!Utils.isBlankOrNull(request.getParameter("isAuthSubmitted"))) {
				// add or update ldap server data
				ActionErrors errors = new ActionErrors();
				if (Utils.isBlankOrNull(ldapForm.getAdminUserName())) {
					errors.add("admin.ldap_set_admin.error.enter_username", new ActionError("admin.ldap_set_admin.error.enter_username"));
				}
				if (Utils.isBlankOrNull(ldapForm.getAdminPassword())) {
					errors.add("admin.ldap_set_admin.error.enter_password", new ActionError("admin.ldap_set_admin.error.enter_password"));
				}
				if (errors.size() == 0) {
					// update server data
					keys = (KeyPair) request.getSession().getAttribute("keys");
					LDAPServerData ldapDO = new LDAPServerData();
					ldapDO.setServerURL(ldapForm.getServerURL());
					ldapDO.setSecurityPrincipal(ldapForm.getSecurityPrincipal());
					LDAPQueryProcessor ldapQuery = new LDAPQueryProcessor();
					try {
						// RSA decryption					
						userPassword = JCryptionUtil.decrypt(userPassword, keys);
						// Regular decryption for encryt.js
						userPassword = EncryptionUtils.decryptPassword(userPassword, rnd);
						if (!ldapQuery.isValidLDAPUser(ldapDO, ldapForm.getAdminUserName().trim(), userPassword.trim())) {
							errors.add("admin.ldap_set_admin.error.authentication_failed", new ActionError("admin.ldap_set_admin.error.authentication_failed"));
						} else {
							// change admin name
							try {
								LDAPManager manager = new LDAPManager();
								manager.updateUserName(UserConstants.ADMIN_ID, ldapForm.getAdminUserName());
								// add ldap server
								manager.addLDAPServerInfo(ldapDO);
								// update global settings
								HashMap<String, String> props = new HashMap<String, String>();
								props.put(GlobalConstants.PROPERTY_LDAP_ENABLED, GlobalConstants.ENABLED);
								AdminManager adminManager = new AdminManager();
								adminManager.updateGlobalSettings(props);
								GlobalApplicationProperties.resetPropertiesMap();
								
								
								HttpSession session = request.getSession();
								session.setAttribute("username", ldapForm.getAdminUserName());
								session.setAttribute("userpassword", userPassword.trim());
								session.setAttribute("validLdapUser", "1");
								return ldapSettings(mapping, actionForm, request, response);
							} catch (SQLException e) {
								errors.add("admin.ldap_set_admin.error.duplicate_username", new ActionError("admin.ldap_set_admin.error.duplicate_username"));
							}
						}
					} catch (CommunicationException cme) {
						errors.add("admin.ldap_set_admin.error.communicatio_failed", new ActionError("admin.ldap_set_admin.error.communicatio_failed"));
						request.setAttribute(Globals.ERROR_KEY, errors);
						return addLdapServer(mapping, actionForm, request, response);
					}
				}
				if (errors.size() > 0) {
					request.setAttribute(Globals.ERROR_KEY, errors);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TPLogger.getLogger().error("Error While display ldap settings", e);
		}
		return mapping.findForward(forward);
	}

}
