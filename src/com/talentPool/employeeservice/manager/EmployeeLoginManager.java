/**
 * 
 */
package com.talentPool.employeeservice.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.axis.session.Session;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.employeeservice.dataobject.EloginData;
import com.talentPool.ldap.dataobject.LDAPUserData;
import com.talentPool.ldap.manager.LDAPManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.UserManager;

/**
 * @author shivprasad
 * 
 */
public class EmployeeLoginManager {
	/**
	 * Checks login for username and password
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	
	public static Map<String, String> USER_ATTEMPTS_MAP = new ConcurrentHashMap<String, String>();
	
	public EloginData isValidLogin(String userName, String password, Session session) {
		EloginData eloginData = null;
		try {
			boolean simpleValidation = true;
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.login(new LoginData(userName, password));
			eloginData = new EloginData();
			String userRoleId=loginData.getRoleId();	
			String userId = "";
			if (loginData != null){
				userId = loginData.getUserId();
				eloginData.setUserStatus(loginData.getStatus());
			}
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) {
				simpleValidation = false;	
				LDAPManager ldapManager = new LDAPManager();
				boolean validLdapUser = ldapManager.isValidLdapUserOnAnyServer(userName, password);				
				if (!validLdapUser) {		
					if(loginData.getIsUserLdapSetting().equals(UserConstants.IS_USER_LDAP_SETTING_DISABLED)){
//					if (userRoleId.equals("" + UserConstants.ROLE_EMPLOYEE) || userRoleId.equals("" + UserConstants.ROLE_ADMIN) || userRoleId.equals("" + UserConstants.ROLE_HR_MANAGER)|| userRoleId.equals("" + UserConstants.ROLE_RECRUITER) || userRoleId.equals("" + UserConstants.ROLE_CXO)) {
						simpleValidation = true;
					} else {
						loginData = null;
						if (Utils.isBlankOrNull(password)){
							eloginData.setIsLDAPEnabled(GlobalConstants.ENABLED);
						} else {
							eloginData = null;
						}
					}
				} else {
					session.set("userId", loginData.getUserId());
					session.set("timeZoneId", loginData.getTimeZone());
					eloginData = constructEmployeeLoginData(loginData, userRoleId);
					eloginData.setIsLDAPEnabled(GlobalConstants.ENABLED);
				} 
			}
			if (simpleValidation && !Utils.isBlankOrNull(password)) {
				String encryptedPassword = EncryptionUtils.encryptString(password);
				if (!(encryptedPassword.equals(loginData.getPassword()))) {
					if (!Utils.isBlankOrNull(loginData.getUserName())){
						eloginData.setUserName(loginData.getUserName());
					}else {
						eloginData = null;
					}
					loginData = null;
				} else {
					session.set("userId", loginData.getUserId());
					session.set("timeZoneId", loginData.getTimeZone());
					loginManager.updateLastLogin(userId);
				}
			}
			userName = eloginData.getUserName();
			if (loginData != null && loginData.getStatus() == UserConstants.ACTIVE && simpleValidation) {
				userName = loginData.getUserName();
				eloginData = constructEmployeeLoginData(loginData, userRoleId);
				if (USER_ATTEMPTS_MAP.containsKey(userName)){
					USER_ATTEMPTS_MAP.remove(userName);
				}
			}
			if (loginData == null && !Utils.isBlankOrNull(userId) && eloginData.getUserStatus() == UserConstants.ACTIVE && simpleValidation){
				if (!USER_ATTEMPTS_MAP.containsKey(userName)) {
					USER_ATTEMPTS_MAP.put(userName, "1");
				} else if (USER_ATTEMPTS_MAP.get(userName).equals("1")) {
					USER_ATTEMPTS_MAP.put(userName, "2");
				} else {
					USER_ATTEMPTS_MAP.put(userName, "3");
					AdminManager adminManager = new AdminManager();
					adminManager.changeUserStatus(userId, UserConstants.DEACTIVE);
					TPLogger.getLogger().debug(userName+" has been disabled");
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while getting employee login", e);
		}		
		return eloginData;
	}

	/**
	 * construst EmployeeLoginData from LoginData
	 * 
	 * @param loginData
	 * @param userRoleId
	 * @return
	 */
	private EloginData constructEmployeeLoginData(LoginData loginData, String userRoleId) {
		EloginData eloginData = null;
		if (loginData != null) {
			eloginData = new EloginData();
			eloginData.setFirstName(loginData.getFirstName());
			eloginData.setLastName(loginData.getLastName());
			eloginData.setUserId(loginData.getUserId());
			eloginData.setPassword(loginData.getPassword());
			eloginData.setUserName(loginData.getUserName());
			eloginData.setUserRoleId(userRoleId);
			eloginData.setEmailAddress(loginData.getEmail());			
			eloginData.setCellPhone(loginData.getCellPhone());
			eloginData.setHomePhone(loginData.getHomePhone());
			eloginData.setEmployeeCode(loginData.getEmployeeCode());
			if (!Utils.isBlankOrNull(loginData.getUserSourceId())) {
				eloginData.setSourceId(loginData.getUserSourceId());				
				eloginData.setSourceName(CommonUtils.getSourceName(loginData.getUserSourceId()));				
			}
			if (loginData.getLastLogin() == null) {
				eloginData.setLastLoginDate(Utils.getDateConvertedToString(new Date(), "EEEEE, dd MMM yyyy"));
			} else {
				eloginData.setLastLoginDate(Utils.getDateConvertedToString(loginData.getLastLogin(), "EEEEE, dd MMM yyyy"));
			}
			eloginData.setSecurityQuestion(loginData.getSecurityQuestion());
			eloginData.setSecurityQuestionId(loginData.getSecurityQuestionId());
			eloginData.setAnswer(loginData.getAnswer());
			eloginData.setPasswordDateModified(loginData.getPasswordDateModified());
			eloginData.setForcePasswordChange(loginData.getForcePasswordChange());
			eloginData.setPasswordAge(GlobalApplicationProperties.getProperty("default_password_expiry_duration"));
			eloginData.setTimeZone(loginData.getTimeZone());
		}
		return eloginData;
	}
	/**
	 * construst EmployeeLoginData from LoginData
	 * 
	 * @param loginData
	 * @param userRoleId
	 * @return
	 */
	private EloginData createEmployeeLoginData(ArrayList<LDAPUserData> user, String userRoleId) {
		EloginData eloginData = null;
		LDAPUserData ldapUserData=new LDAPUserData();		
		for(int i=0;user != null && i<user.size();i++){
			ldapUserData=user.get(i);
			eloginData = new EloginData();
			eloginData.setFirstName(ldapUserData.getGivenName());			
			eloginData.setUserName(ldapUserData.getSamAccountName());			
			eloginData.setUserRoleId(userRoleId);
			eloginData.setLastName(ldapUserData.getSurName());			
		}
		return eloginData;
	}
	
	/**
	 * @param userId
	 * @return vloginData
	 */
	public EloginData getUserById(String userId) {
		LoginManager loginManager = new LoginManager();
		LoginData loginData = loginManager.getUser(userId);
		EloginData eloginData = constructEmployeeLoginData(loginData, loginData.getRoleId());
		return eloginData;
	}
	
	 //Added to get the User Details from UserName by Krishna
	public EloginData getUserDataFromUserName(String userName)  {
		EloginData eloginData 		= null;
		EmployeeAccountManager eacm = null;
		LoginManager loginManager 	=  null;
		LoginData loginData 		= null;
		try {
			eacm 			= new EmployeeAccountManager();
			loginManager 	= new LoginManager();
			loginData 		= loginManager.getUserFromUserName(userName);	
			eloginData 		= eacm.convertLoginToELogin(loginData);
		}catch(Exception e){
			TPLogger.getLogger().error("Error while geting userData for emailID: "+userName,e);
		}
		return eloginData;
	}
}
