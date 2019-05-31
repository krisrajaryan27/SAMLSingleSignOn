package com.talentPool.user.helper;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.ldap.manager.LDAPManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.exception.InvalidLoginException;
import com.talentPool.user.exception.UserDisabledException;
import com.talentPool.user.manager.LoginManager;

/**
 * @author PraveenK
 * @since  Nov 10, 2011
 */
public class LoginHelper {
	
	/**
	 * Validates authentication 
	 * @param userName
	 * @param userPassword
	 * @param validLdapUser
	 * @return {@link LoginData} if valid login else null
	 * @throws InvalidLoginException
	 * @author PraveenK
	 * @throws SQLException 
	 */
	public static Map<String, String> USER_ATTEMPTS_MAP = new ConcurrentHashMap<String, String>();
	
	public LoginData isValidlogin(String userName, String userPassword) throws InvalidLoginException, SQLException, UserDisabledException {
		LoginData loginData 		= new LoginData(userName, userPassword);
		LoginManager loginManager	= new LoginManager();
		loginData 					= loginManager.login(loginData);
		String userRoles 			= loginData.getRoleId();
		boolean validLdapUser		= false;
		if (UserConstants.ROLE_VENDOR == Integer.parseInt(userRoles) || UserConstants.ROLE_EMPLOYEE == Integer.parseInt(userRoles)) {
			throw new InvalidLoginException();
		}
		boolean simpleValidation = true;

		if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED)) {
			simpleValidation = false;
			LDAPManager manager = new LDAPManager();
			validLdapUser = manager.isValidLdapUserOnAnyServer(userName, userPassword);
			loginData.setIsValidLdapUser(validLdapUser);
			// if invalid login for admin check for normal
			// authentication
			if (!validLdapUser) {
				if (loginData.getUserId().equals(UserConstants.ADMIN_ID)) {
					simpleValidation = true;
				}
				// When User can skip ldap validations.
				else if (loginData.getIsUserLdapSetting().equals(UserConstants.IS_USER_LDAP_SETTING_DISABLED)) {
					simpleValidation = true;
				} else {
					throw new InvalidLoginException();
				}
			}

		}
		if (simpleValidation) {
			userName = loginData.getUserName();
			String encryptedPassword = EncryptionUtils.encryptString(userPassword);
			if (!(encryptedPassword.equals(loginData.getPassword()))) {
				if (loginData != null && loginData.getStatus() == UserConstants.ACTIVE) {
					if (!USER_ATTEMPTS_MAP.containsKey(userName)) {
						USER_ATTEMPTS_MAP.put(userName, "1");
					} else if (USER_ATTEMPTS_MAP.get(userName).equals("1")) {
						USER_ATTEMPTS_MAP.put(userName, "2");
					} else {
						USER_ATTEMPTS_MAP.remove(userName);
						AdminManager adminManager = new AdminManager();
						adminManager.changeUserStatus(loginData.getUserId(), UserConstants.DEACTIVE);
						TPLogger.getLogger().debug(userName+" has been disabled");
						throw new UserDisabledException();
					}
				}
				throw new InvalidLoginException();
			}else if(USER_ATTEMPTS_MAP.containsKey(userName)){
				USER_ATTEMPTS_MAP.remove(userName);
			}
		}
		return loginData;
	}
	
	public LoginData isValidCandidatelogin(String userName, String userPassword) throws InvalidLoginException, SQLException, UserDisabledException {
		LoginData loginData 		= new LoginData();
		loginData.setEmail(userName);
		LoginManager loginManager	= new LoginManager();
		loginData 					= loginManager.candidateLogin(loginData);
		String encryptedPassword = EncryptionUtils.encryptString(userPassword);
		if (!(encryptedPassword.equals(loginData.getPassword()))) {
			throw new InvalidLoginException();
		}
//			userName = loginData.getUserName();
//			String encryptedPassword = EncryptionUtils.encryptString(userPassword);
//			if (!(encryptedPassword.equals(loginData.getPassword()))) {
//				if (loginData != null && loginData.getStatus() == UserConstants.ACTIVE) {
//					if (!USER_ATTEMPTS_MAP.containsKey(userName)) {
//						USER_ATTEMPTS_MAP.put(userName, "1");
//					} else if (USER_ATTEMPTS_MAP.get(userName).equals("1")) {
//						USER_ATTEMPTS_MAP.put(userName, "2");
//					} else {
//						USER_ATTEMPTS_MAP.remove(userName);
//						AdminManager adminManager = new AdminManager();
//						adminManager.changeUserStatus(loginData.getUserId(), UserConstants.DEACTIVE);
//						TPLogger.getLogger().debug(userName+" has been disabled");
//						throw new UserDisabledException();
//					}
//				}
//				throw new InvalidLoginException();
//			}else if(USER_ATTEMPTS_MAP.containsKey(userName)){
//				USER_ATTEMPTS_MAP.remove(userName);
//			}
		return loginData;
	}
	
	public boolean enableForgotPassword(String userName, String isUserLdapSetting, String userId, String userRoles){
		boolean enableForgotPassword = true;
		if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LDAP_ENABLED).equals(GlobalConstants.ENABLED) && 
				!isUserLdapSetting.equals(UserConstants.IS_USER_LDAP_SETTING_DISABLED)
				&& !userId.equals(UserConstants.ADMIN_ID)) {
			enableForgotPassword = false;
		}
		if (UserConstants.ROLE_VENDOR == Integer.parseInt(userRoles) || UserConstants.ROLE_EMPLOYEE == Integer.parseInt(userRoles)) {
			enableForgotPassword = false;
		}
		return enableForgotPassword;
	}
}
