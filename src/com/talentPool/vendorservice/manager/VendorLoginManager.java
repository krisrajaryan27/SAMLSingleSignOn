/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.axis.session.Session;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.ldap.manager.LDAPManager;
import com.talentPool.timeZone.TimeZoneUtils;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.vendorservice.dataobject.VloginData;

/**
 * @author shivprasad
 * 
 */
public class VendorLoginManager {
	/**
	 * Checks login for username and password
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	
	public static Map<String, String> USER_ATTEMPTS_MAP = new ConcurrentHashMap<String, String>();
	
	public VloginData isValidLogin(String userName, String password, Session session) {
		VloginData vloginData = null;
		try {
			boolean simpleValidation = true;
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.login(new LoginData(userName, password));
			vloginData = new VloginData();
			String userRoles = loginData.getRoleId();
			String userId = "";
			if (loginData != null){
				userId = loginData.getUserId();
				vloginData.setUserStatus(loginData.getStatus());
			}
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) {
				simpleValidation = false;
				LDAPManager manager = new LDAPManager();
				boolean validLdapUser = manager.isValidLdapUserOnAnyServer(userName, password);
				if (!validLdapUser) {
					if (userRoles.equals("" + UserConstants.ROLE_VENDOR) || userRoles.equals("" + UserConstants.ROLE_ADMIN) || userRoles.equals("" + UserConstants.ROLE_HR_MANAGER)
							|| userRoles.equals("" + UserConstants.ROLE_RECRUITER) || userRoles.equals("" + UserConstants.ROLE_CXO)) {
						simpleValidation = true;
					} else {
						loginData = null;
					}
				} else {
					session.set("userId", loginData.getUserId());
					if (!Utils.isBlankOrNull(loginData.getTimeZone())){
						session.set("timeZoneId", loginData.getTimeZone());
						//TimeZone.setDefault(TimeZone.getTimeZone( loginData.getTimeZone()));
					}
				}
			}
			if (simpleValidation && !Utils.isBlankOrNull(password)) {
				String encryptedPassword = EncryptionUtils.encryptString(password);
				if (!(encryptedPassword.equals(loginData.getPassword()))) {
					if (!Utils.isBlankOrNull(loginData.getUserName())){
						vloginData.setUserName(loginData.getUserName());
					}else {
						vloginData = null;
					}
					loginData = null;
				} else {
					session.set("userId", loginData.getUserId());
					if (!Utils.isBlankOrNull(loginData.getTimeZone())){
						session.set("timeZoneId", loginData.getTimeZone());
						//TimeZone.setDefault(TimeZone.getTimeZone( loginData.getTimeZone()));
					}
					loginManager.updateLastLogin(userId);
				}
			}
			userName = vloginData.getUserName();
			if (loginData != null && loginData.getStatus() == UserConstants.ACTIVE) {
				loginData = loginManager.login(new LoginData(loginData.getUserName(), password));
				userName = loginData.getUserName();
				vloginData = constructVLoginData(loginData, userRoles);
				if (USER_ATTEMPTS_MAP.containsKey(userName)){
					USER_ATTEMPTS_MAP.remove(userName);
				}
			}
			if (loginData == null && !Utils.isBlankOrNull(userId) && vloginData.getUserStatus() == UserConstants.ACTIVE){
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
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting vendor login", e);
		}
		return vloginData;
	}

	/**
	 * construst VLoginData from LoginData
	 * 
	 * @param loginData
	 * @param userRoles
	 * @return
	 */
	private VloginData constructVLoginData(LoginData loginData, String userRoles) {
		VloginData vloginData = null;
		if (loginData != null) {
			vloginData = new VloginData();
			vloginData.setFirstName(loginData.getFirstName());
			vloginData.setUserId(loginData.getUserId());
			vloginData.setUserName(loginData.getUserName());
			vloginData.setUserRoles(userRoles);
			vloginData.setUserEmail(loginData.getEmail());
			vloginData.setUserStatus(loginData.getStatus());
			if (!Utils.isBlankOrNull(loginData.getUserSourceId())) {
				vloginData.setSourceId(loginData.getUserSourceId());
				vloginData.setSourceName(CommonUtils.getSourceName(loginData.getUserSourceId()));
			}
			if (loginData.getLastLogin() == null) {
				vloginData.setLastLoginDate(Utils.getDateConvertedToString(new Date(), "dd-MMM-yy HH:mm aaa"));
			} else {
				vloginData.setLastLoginDate(Utils.getDateConvertedToString(loginData.getLastLogin(), "dd-MMM-yy HH:mm aaa"));
			}
			vloginData.setSecurityQuestion(loginData.getSecurityQuestion());
			vloginData.setSecurityQuestionId(loginData.getSecurityQuestionId());
			vloginData.setAnswer(loginData.getAnswer());
			vloginData.setPasswordDateModified(loginData.getPasswordDateModified());
			vloginData.setForcePasswordChange(loginData.getForcePasswordChange());
			vloginData.setPasswordAge(GlobalApplicationProperties.getProperty("default_password_expiry_duration"));
			vloginData.setTimeZone(loginData.getTimeZone());
		}
		return vloginData;
	}

	/**
	 * @param userId
	 * @return vloginData
	 */
	public VloginData getUserById(String userId) {
		LoginManager loginManager = new LoginManager();
		LoginData loginData = loginManager.getUser(userId);
		VloginData vloginData = constructVLoginData(loginData, loginData.getRoleId());
		return vloginData;
	}
}
