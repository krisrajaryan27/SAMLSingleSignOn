/**
 * 
 */
package com.talentPool.employeeservice.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.employeeservice.dataobject.EerrorData;
import com.talentPool.employeeservice.dataobject.EloginData;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.dataobject.SourceTypeData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.exception.EmailExistException;
import com.talentPool.user.exception.EmployeeCodeExistException;
import com.talentPool.user.exception.SourceExistException;
import com.talentPool.user.exception.SourceOrEmployeeCodeExistException;
import com.talentPool.user.exception.UsernameExistException;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.UserManager;
import com.talentPool.user.utils.UserUtils;

/**
 * @author Shantanu
 * 
 */
public class EmployeeAccountManager {

	public EloginData createEmployeeAccount(String userName, String firstName, String lastName, String password, String emailAddress, String homePhone, String cellPhone, String employeeCode, String timeZone) throws SourceExistException, EmployeeCodeExistException, UsernameExistException, EmailExistException {
		EloginData eloginData = new EloginData();
		try {
			AdminManager adminManager = new AdminManager();
			eloginData = populateELoginData(userName, firstName, lastName, emailAddress, homePhone, cellPhone, employeeCode, null, timeZone);
			LoginData loginData = new LoginData();
			String roleId = String.valueOf(UserConstants.ROLE_EMPLOYEE);
			eloginData.setPassword(password);
			loginData = convertELoginToLogin(eloginData);
			String userId = adminManager.createUser(loginData, roleId, true);
			adminManager.updateUserAccount(userId, loginData);
			LoginManager loginManager = new LoginManager();
			loginData = loginManager.getUser(userId);
			eloginData.setUserRoleId(roleId);
			eloginData.setUserId(userId);
			eloginData.setSourceId(loginData.getUserSourceId());
			CommonUtils.setSourceIds(null);
			CommonUtils.setSourceNames(null);
		} catch (UsernameExistException unee) {
			TPLogger.getLogger().debug("Username already exist = " + userName);
			eloginData = null;
			throw unee;
		}  catch (SourceExistException snee) {
			TPLogger.getLogger().debug("Source name already exist = " + firstName + " " + lastName);
			eloginData = null;
			throw snee;
		} catch (EmployeeCodeExistException see) {
			TPLogger.getLogger().debug("Employee code already exist = " + employeeCode);
			eloginData = null;
			throw see;
		} catch (EmailExistException eee) {
			TPLogger.getLogger().debug("Email address already exist = " + emailAddress);
			eloginData = null;
			throw eee;
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error in Adding Employee", sqle);
			eloginData = null;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display Employee", e);
			eloginData = null;
		}
		return eloginData;
	}

	public EloginData populateELoginData(String userName, String firstName, String lastName, String emailAddress, String homePhone, String cellPhone, String employeeCode, String sourceId, String timeZone) {
		EloginData eloginData = new EloginData();
		eloginData.setUserName(userName);
		eloginData.setFirstName(firstName);
		eloginData.setLastName(lastName);
		eloginData.setEmailAddress(emailAddress);
		eloginData.setCellPhone(cellPhone);
		eloginData.setHomePhone(homePhone);
		eloginData.setSourceId(sourceId);
		eloginData.setEmployeeCode(employeeCode);
		eloginData.setTimeZone(timeZone);
		String sourceName = firstName + " " + lastName;
		eloginData.setSourceName(sourceName);
		return eloginData;
	}

	public SourceData populateSourceData(EloginData eloginData) {
		SourceData sourceData = new SourceData();
		sourceData.setSourceTypeId(eloginData.getSourceTypeId());
		sourceData.setSourceTitle(eloginData.getFirstName() + " " + eloginData.getLastName());
		sourceData.setSourceEmail(eloginData.getEmailAddress());
		sourceData.setSourcePhone(eloginData.getHomePhone());
		sourceData.setSourceMobile(eloginData.getCellPhone());
		sourceData.setSendEmailToSource(null);
		sourceData.setSendSMSToSource(null);
		sourceData.setLockInPeriodOnImport("0");
		return sourceData;
	}

	public LoginData convertELoginToLogin(EloginData eloginData) {
		LoginData loginData = new LoginData();
		loginData.setUserName(eloginData.getUserName());
		loginData.setPassword(eloginData.getPassword());
		loginData.setFirstName(eloginData.getFirstName());
		loginData.setPassword(eloginData.getPassword());
		loginData.setLastName(eloginData.getLastName());
		loginData.setEmail(eloginData.getEmailAddress());
		loginData.setCellPhone(eloginData.getCellPhone());
		loginData.setHomePhone(eloginData.getHomePhone());
		loginData.setUserSourceId(eloginData.getSourceId());
		loginData.setEmployeeCode(eloginData.getEmployeeCode());
		loginData.setTimeZone(eloginData.getTimeZone());
		return loginData;
	}

	public EloginData convertLoginToELogin(LoginData loginData) {
		EloginData eloginData = new EloginData();
		eloginData.setUserId(loginData.getUserId());
		eloginData.setUserName(loginData.getUserName());
		eloginData.setFirstName(loginData.getFirstName());
		eloginData.setLastName(loginData.getLastName());
		eloginData.setEmailAddress(loginData.getEmail());
		eloginData.setPassword(loginData.getPassword());
		eloginData.setCellPhone(loginData.getCellPhone());
		eloginData.setHomePhone(loginData.getHomePhone());
		eloginData.setSourceId(loginData.getUserSourceId());
		eloginData.setUserLocationName(loginData.getLocation());
		eloginData.setSourceTitle(loginData.getSourceTitle());
		eloginData.setSourceEmail(loginData.getSourceEmail());
		eloginData.setUserRoleId(loginData.getRoleId());
		return eloginData;
	}

	public String getEmployeeSourceId(EloginData eloginData) {
		AdminManager adminManager = new AdminManager();
		MastersManager mastersManager = new MastersManager();
		String sourceId = null;
		try {
			ArrayList<SourceTypeData> sourceTypeDetail = adminManager.getEmployeeSourceTypeId();
			SourceTypeData sourceTypeData = new SourceTypeData();
			for (int i = 0; i < sourceTypeDetail.size(); i++) {
				sourceTypeData = sourceTypeDetail.get(i);
			}
			eloginData.setSourceTypeId(sourceTypeData.getSourceTypeId());
			SourceData data = populateSourceData(eloginData);
			sourceId = mastersManager.addSourcetoSourceType(data, null);
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error in Adding Employee", sqle);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display Employee", e);
		}
		return sourceId;
	}

	public EloginData updateEmployeeAccount(String userName, String firstName, String lastName, String emailAddress, String homePhone, String cellPhone, String employeeCode, String userId, String sourceId, String roleId, String timeZone) throws SourceOrEmployeeCodeExistException, EmailExistException, SQLException, Exception {
		EloginData eloginData = new EloginData();
		try {
			AdminManager adminManager = new AdminManager();
			LoginData loginData = new LoginData();
			eloginData = populateELoginData(userName, firstName, lastName, emailAddress, homePhone, cellPhone, employeeCode, sourceId, timeZone);
			loginData = convertELoginToLogin(eloginData);

			adminManager.updateUser(userId, loginData, roleId, false);
			adminManager.updateUserAccount(userId, loginData);
			CommonUtils.setSourceIds(null);
			CommonUtils.setSourceNames(null);
		} catch (SourceOrEmployeeCodeExistException see) {
			TPLogger.getLogger().debug("Source name already exist = " + firstName + " " + lastName);
			eloginData = null;
			throw see;
		} catch (EmailExistException eee) {
			TPLogger.getLogger().debug("Email address already exist = " + emailAddress);
			eloginData = null;
			throw eee;
		}
		return eloginData;
	}

	public EerrorData updateEmployeePassword(String userName, String userId, String newPassword, String oldPassword, 
			String securityQuestionId, String securityAnswer, boolean forgotPassword) {
		EerrorData errorData = new EerrorData();
		LoginData data = new LoginData();
		UserManager userManager = new UserManager();
		try {
			if (!forgotPassword) {
				if (Utils.isBlankOrNull(oldPassword)) {
					errorData.putError("change_password.error.old_password.nullOrBlank");
				} else {
					LoginManager loginManager = new LoginManager();
					data = loginManager.getUser(userId);
					if (!EncryptionUtils.encryptString(oldPassword).equalsIgnoreCase(data.getPassword())) {
						errorData.putError("change_password.error.old_password.invalid");
					}
				}
			}
			if (!Utils.isBlankOrNull(securityQuestionId) && Utils.isBlankOrNull(securityAnswer)) {
				errorData.putError("change_password.error.security_Answer.null");
			} else if(!Utils.isBlankOrNull(UserConstants.PASSWORD_PATTERN) && !UserUtils.isValidPasswordPattern(newPassword)) {
				errorData.putError("change_password.error.new_password.weak");
			} else if (newPassword.length() < UserConstants.REQUIRED_MIN_PASSWORD_LENGTH || newPassword.length() > UserConstants.REQUIRED_MAX_PASSWORD_LENGTH) {
				errorData.putError("change_password.errors.short_password");
			} 
			
			if (errorData.getErrors().size() == 0) {
				if (!userManager.valdatePasswordAgainstLastPasswords(userId, newPassword)){
					errorData.putError("change_password.error.password_same_as_last");
				} else if (Utils.isBlankOrNull(securityQuestionId)){
					userManager.savePassword(userId, newPassword);
					if(forgotPassword && userManager.isUserDisabled(userId)){
						AdminManager adminManager = new AdminManager();
						adminManager.changeUserStatus(userId, UserConstants.ACTIVE);
					}
				} else {
					userManager.savePasswordAndSecrityQuestion(userId, newPassword, securityQuestionId, securityAnswer);
				}
			}

		} catch (Exception e) {
			errorData.putError("change_password.error.password.update_failed");
		}
		return errorData;
	}
	
	public EloginData createEmployeeAccount(String userName, String firstName, String lastName, String password, String emailAddress, String homePhone, String cellPhone, String employeeCode) throws SourceExistException, EmployeeCodeExistException, UsernameExistException, EmailExistException {
		EloginData eloginData = new EloginData();
		try {
			AdminManager adminManager = new AdminManager();
			eloginData = populateELoginData(userName, firstName, lastName, emailAddress, homePhone, cellPhone, employeeCode, null);
			LoginData loginData = new LoginData();
			String roleId = String.valueOf(UserConstants.ROLE_EMPLOYEE);
			eloginData.setPassword(password);
			loginData = convertELoginToLogin(eloginData);
			String userId = adminManager.createUser(loginData, roleId, true);
			LoginManager loginManager = new LoginManager();
			loginData = loginManager.getUser(userId);
			eloginData.setUserRoleId(roleId);
			eloginData.setUserId(userId);
			eloginData.setSourceId(loginData.getUserSourceId());
			CommonUtils.setSourceIds(null);
			CommonUtils.setSourceNames(null);
		} catch (UsernameExistException unee) {
			TPLogger.getLogger().debug("Username already exist = " + userName);
			eloginData = null;
			throw unee;
		}  catch (SourceExistException snee) {
			TPLogger.getLogger().debug("Source name already exist = " + firstName + " " + lastName);
			eloginData = null;
			throw snee;
		} catch (EmployeeCodeExistException see) {
			TPLogger.getLogger().debug("Employee code already exist = " + employeeCode);
			eloginData = null;
			throw see;
		} catch (EmailExistException eee) {
			TPLogger.getLogger().debug("Email address already exist = " + emailAddress);
			eloginData = null;
			throw eee;
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error in Adding Employee", sqle);
			eloginData = null;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display Employee", e);
			eloginData = null;
		}
		return eloginData;
	}

	public EloginData populateELoginData(String userName, String firstName, String lastName, String emailAddress, String homePhone, String cellPhone, String employeeCode, String sourceId) {
		EloginData eloginData = new EloginData();
		eloginData.setUserName(userName);
		eloginData.setFirstName(firstName);
		eloginData.setLastName(lastName);
		eloginData.setEmailAddress(emailAddress);
		eloginData.setCellPhone(cellPhone);
		eloginData.setHomePhone(homePhone);
		eloginData.setSourceId(sourceId);
		eloginData.setEmployeeCode(employeeCode);
		String sourceName = firstName + " " + lastName;
		eloginData.setSourceName(sourceName);
		return eloginData;
	}


	public EloginData updateEmployeeAccount(String userName, String firstName, String lastName, String emailAddress, String homePhone, String cellPhone, String employeeCode, String userId, String sourceId, String roleId) throws SourceOrEmployeeCodeExistException, EmailExistException, SQLException, Exception {
		EloginData eloginData = new EloginData();
		try {
			AdminManager adminManager = new AdminManager();
			LoginData loginData = new LoginData();
			eloginData = populateELoginData(userName, firstName, lastName, emailAddress, homePhone, cellPhone, employeeCode, sourceId);
			loginData = convertELoginToLogin(eloginData);

			adminManager.updateUser(userId, loginData, roleId, false);
			CommonUtils.setSourceIds(null);
			CommonUtils.setSourceNames(null);
		} catch (SourceOrEmployeeCodeExistException see) {
			TPLogger.getLogger().debug("Source name already exist = " + firstName + " " + lastName);
			eloginData = null;
			throw see;
		} catch (EmailExistException eee) {
			TPLogger.getLogger().debug("Email address already exist = " + emailAddress);
			eloginData = null;
			throw eee;
		}
		return eloginData;
	}

	public boolean updateEmployeePassword(String userName, String userId, String newPassword) {
		boolean success = false;
		try {
			UserManager userManager = new UserManager();
			userManager.savePassword(userId, newPassword);
			success = true;
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error in Updatinging Employee", sqle);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in Updating Password", e);
		}
		return success;
	}
}
