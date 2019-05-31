/*
 * Created on Aug 1, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.user.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.UserData;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.dataobject.ViewData;

/**
 * @author pallavi
 * @date Aug 1, 2006
 */
@Component
public class UserManager {

	/**
	 * Method to save the updated password.
	 * 
	 * @param userId
	 *            The identifier of the user whose password is to updated.
	 * @param password
	 *            The updated password.
	 * @param tran
	 * 			  Database transaction
	 * @param forcePasswordChange
	 * 			  flag to force/not-force users to change their password           
	 * @throws Exception
	 */
	public void savePassword(String userId, String password, DBTransaction tran, String forcePasswordChange) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran == null){
				dq = new DBPreparedQuery("dChangeUserPassword");
			} else {
				dq = new DBPreparedQuery("dChangeUserPassword", tran);
			}
			dq.setString(1, EncryptionUtils.encryptString(password));
			dq.setString(2, userId);
			dq.execute();
			
			if(!Utils.isBlankOrNull(forcePasswordChange)) {
				updateForcePasswordChange(userId, forcePasswordChange, tran);
			}
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	/**
	 * Method to save the updated password.
	 * 
	 * @param userId
	 *            The identifier of the user whose password is to updated.
	 * @param password
	 *            The updated password.
	 * @param tran
	 * 			  Database transaction
	 * @param forcePasswordChange
	 * 			  flag to force/not-force users to change their password           
	 * @throws Exception
	 */
	public void saveCandidatePassword(String userId, String password, String forcePasswordChange) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dChangeCandidatePassword");
			dq.setString(1, EncryptionUtils.encryptString(password));
			dq.setString(2, userId);
			dq.execute();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	/**
	 * @param userId
	 * @param password
	 * @param forcePasswordChange
	 * @throws Exception
	 */
	public void savePassword(String userId, String password, String forcePasswordChange) throws Exception {
		savePassword(userId, password, null, forcePasswordChange);
	}
	
	/**
	 * @param userId
	 * @param password
	 * @throws Exception
	 */
	public void savePassword(String userId, String password) throws Exception {
		savePassword(userId, password, null, CommonConstants.NO);
	}
	
	/**
	 * @param userId
	 * @param password
	 * @param securityQuestionId
	 * @param securityAnswer
	 * @throws Exception
	 */
	public void savePasswordAndSecrityQuestion (String userId, String password, String securityQuestionId, String securityAnswer) throws Exception{
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		if (!Utils.isBlankOrNull(securityQuestionId)){
			try{
				tran = new DBTransaction();
				savePassword(userId, password, tran, CommonConstants.NO);
				
				dq = new DBPreparedQuery("dUserManager_deleteSecurityQuestion", tran);
				dq.setString(1, userId);
				dq.execute();
				
				dq = new DBPreparedQuery("dUserManager_insertSecurityQuestion", tran);
				dq.setString(1, userId);
				dq.setString(2, securityQuestionId);
				dq.setString(3, securityAnswer);
				dq.execute();
				
				tran.commit();
			} catch (SQLException e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				try {
					tran.rollback();
				} catch (SQLException ex) {
					TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				}
				throw new Exception();
			} finally {
				if (tran != null) {
					dq.releaseTransaction(tran);
				}
			}
		}
	}
	
	/**
	 * @param userId
	 * @param forcePasswordChange
	 * @param tran
	 * @throws SQLException
	 */
	public void updateForcePasswordChange(String userId, String forcePasswordChange, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran != null) { 
				dq = new DBPreparedQuery("dUserManager_updateForcePassword", tran);
			} else {
				dq = new DBPreparedQuery("dUserManager_updateForcePassword");
			}
			dq.setString(1, forcePasswordChange);
			dq.setString(2, userId);
			dq.execute();
		} catch(SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	/**
	 * @param userId
	 * @param uuid
	 * @throws Exception
	 */
	public void saveUUID(String userId, String uuid) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDeleteUserUUID");
			dq.setString(1, userId);
			dq.execute();
			
			dq = new DBPreparedQuery("dSaveUserUUID");
			dq.setString(1, userId);
			dq.setString(2, uuid);			
			dq.execute();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void saveCandidateUUID(String userId, String uuid) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDeleteCandidateUUID");
			dq.setString(1, userId);
			dq.execute();
			
			dq = new DBPreparedQuery("dSaveCandidateUUID");
			dq.setString(1, userId);
			dq.setString(2, uuid);			
			dq.execute();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public ArrayList getUsersLike(String userName, String email, boolean doShowVendor) {
		ArrayList users = null;
		String[] dynParam = new String[2];
		DBPreparedQuery dq = null;
		try {
			if(doShowVendor) {
				dynParam[0] = "?, ?";
			} else {
				dynParam[0] = "?";
			}
			// if rolesIds is null then populate all users
			dynParam[1] = "1";
			if (!Utils.isBlankOrNull(userName)) {
				dynParam[1] = " name like '" + userName + "%'";
			} else if (!Utils.isBlankOrNull(email)) {
				dynParam[1] = " user_email like '" + email + "%'";
			}
			dq = new DBPreparedQuery("dUser_GetUsersLike", dynParam);
			dq.setInt(1, UserConstants.ACTIVE);
			int count = 2;
			if(doShowVendor) {
				dq.setInt(count++, UserConstants.ROLE_VENDOR);
			}			
			dq.setInt(count++, UserConstants.ROLE_EMPLOYEE);
			users = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting users autocomplete list", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return users;
	}

	/**
	 * @param emailFrom
	 * @return the first userId found for the email
	 */
	public String getUserIdForEmail(String emailFrom) {
		String userId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUser_GetUserIdForEmail");
			dq.setString(1, emailFrom);
			userId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching Checking emails in autoreply", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userId;
	}

	public boolean isDuplicateEmailAddressExists(String emailAddress, String userId) {
		boolean exists = false;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUser_GetUserIdForEmailOtherThenUserId");
			dq.setString(1, emailAddress);
			dq.setId(2, userId);
			String newUserId = dq.getIdResult();
			if(!Utils.isBlankOrNull(newUserId)){
				exists = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return exists;
	}

	public boolean isEmailAddressExists(String emailFrom) {
		boolean exists = false;
		String userId = getUserIdForEmail(emailFrom);
		if (!Utils.isBlankOrNull(userId)) {
			exists = true;
		}
		return exists;
	}
	/**
	 * @param emailFrom
	 * @return the first userId found for the email
	 */
	public String getUserEmail(String userId) {
		String email = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUser_GetUserEmail");
			dq.setString(1, userId);
			email = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching emails of user", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return email;
	}

	/**
	 * @param emailAddress
	 * @return success - true or false
	 */
	public boolean saveUUIDandSendConfirmationLink(String emailAddress, int product,String requestURI) {
		boolean success = false;
		try {
			String userId = getUserIdForEmail(emailAddress);
			if (!Utils.isBlankOrNull(userId)) {
				String uuid = UUID.randomUUID().toString();
				saveUUID(userId, uuid);
				sendConfirmationLink(userId, uuid, product,requestURI);
				success = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return success;
	}
	
	
	/**
	 * @param emailAddress
	 * @return success - true or false
	 */
	public boolean saveUUIDandSendConfirmationLinkForCandidate(String emailAddress, int product, String userId,String requestURI) {
		boolean success = false;
		try {
			if (!Utils.isBlankOrNull(userId)) {
				String uuid = UUID.randomUUID().toString();
				saveCandidateUUID(userId, uuid);
				sendConfirmationLink(userId, uuid, product,requestURI);
				success = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return success;
	}

	/**
	 * @param userId
	 * @param newPassword
	 * @throws Exception
	 */
	public void sendConfirmationLink(String userId, String uuid, int product, String requestURI) throws Exception {
		TemplateManager templateManager = new TemplateManager();
		TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_CONFIRMATION_LINK);
		String contentVM = templateData.getTemplateContentFile();
		String subjectVM = templateData.getTemplateSubjectFile();
		String keyMap = templateData.getTemplateVariables();

		InboxManager inboxManager = new InboxManager();
		InboxData inboxData = inboxManager.getCurrentInboxSettings();
		String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
		String confirmationLink = TemplateUtils.getConfirmationLink(uuid, product,requestURI);
		confirmationLink = TemplateUtils.getConstructedToken("CONFIRMATION_LINK", confirmationLink);
		contentStr = TemplateUtils.appndToToken(contentStr, confirmationLink);

		LoginManager loginManager = new LoginManager();
		LoginData userData = null;
		if (CommonConstants.PRODUCT_CANDIDATE_PORTAL== product){
			userData = loginManager.getCandidateUser(userId);
		}else {
			userData = loginManager.getUser(userId);
		}
		String cStr = TemplateUtils.getConvertedUserData(userData);
		cStr = TemplateUtils.appndToToken(contentStr, cStr);

		HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, cStr);
		VelocityManager velocityManager = new VelocityManager();
		String subject = velocityManager.handle(subjectVM, keyValMap);
		String content = velocityManager.handle(contentVM, keyValMap);

		MessageData messageData = new MessageData();
		messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");

		messageData.setHtmlBody(content);
		messageData.setSubject(subject);
		messageData.setTo(userData.getEmail());
		TPMailSender sender = new TPMailSender();
		sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null,true);
	}
		
	public void sendNewPasswordEmail(String userId, String newPassword) throws Exception {
		TemplateManager templateManager = new TemplateManager();
		TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_FORGOT_PASSWORD);
		String contentVM = templateData.getTemplateContentFile();
		String subjectVM = templateData.getTemplateSubjectFile();
		String keyMap = templateData.getTemplateVariables();

		InboxManager inboxManager = new InboxManager();
		InboxData inboxData = inboxManager.getCurrentInboxSettings();
		String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);

		LoginManager loginManager = new LoginManager();
		LoginData userData = loginManager.getUser(userId);
		userData.setPassword(newPassword);
		String cStr = TemplateUtils.getConvertedUserData(userData);
		cStr = TemplateUtils.appndToToken(contentStr, cStr);

		HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, cStr);
		VelocityManager velocityManager = new VelocityManager();
		String subject = velocityManager.handle(subjectVM, keyValMap);
		String content = velocityManager.handle(contentVM, keyValMap);

		MessageData messageData = new MessageData();
		messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");

		messageData.setHtmlBody(content);
		messageData.setSubject(subject);
		messageData.setTo(userData.getEmail());
		TPMailSender sender = new TPMailSender();
		sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null,true);

	}
	
	public UserData getUserId(String userFullName) {
		UserData data = null;
		DBPreparedQuery dq = null;		
		try{
			dq = new DBPreparedQuery("dUser_GetUserIdForName");
			dq.setString(1, userFullName);
			dq.setString(2, userFullName);
			dq.setString(3, userFullName);
			data = (UserData) dq.getSingleObjectResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return data;
	}
	public String getUserIdFromName(String userName) {
		String userId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUser_GetUserIdFromName");
			dq.setString(1, userName);
			userId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userId;
	}
	
	public ArrayList<ViewData> getUserViewDataConfig(String userId) {
		DBPreparedQuery dq = null;
		ArrayList<ViewData> viewDataList = null;
		try {	
			dq = new DBPreparedQuery("dUserManager_GetUserViewDataConfig");
			dq.setId(1, userId);
			viewDataList = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return viewDataList;
	}
	
	public void saveDataViewConfig(String userId, String column1, String column2) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		int cnt = 1;
		try {
			tran = new DBTransaction();
			deleteDataViewConfig(userId, tran);
			if(!Utils.isBlankOrNull(column1) || !Utils.isBlankOrNull(column2)){
				dq = new DBPreparedQuery("dUserManager_SaveDataViewConfig",tran);
				cnt = 1;
				dq.setId(cnt++, userId);
				dq.setString(cnt++, UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG);
				dq.setString(cnt++, column1);
				dq.setString(cnt++, column2);
				dq.execute();
			}
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (SQLException ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
			throw new Exception();
		} finally {
			if (tran != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	public void deleteDataViewConfig(String userId, DBTransaction tran)throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dUserManager_DeleteDataViewConfig", tran);
			}else{
				dq = new DBPreparedQuery("dUserManager_DeleteDataViewConfig");
			}
			dq.setId(1, userId);
			dq.execute();
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	public ArrayList<ViewData> getUserViewConfigData() {
		DBPreparedQuery dq = null;
		ArrayList<ViewData> viewDataList = null;
		try {	
			dq = new DBPreparedQuery("dUserManager_GetUserViewConfigData");
			viewDataList = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return viewDataList;
	}

	public String getRoleForUser(String userId) {
		String roleId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserManager_GetRoleForUser");
			dq.setString(1, userId);
			roleId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return roleId;
	}
	
	/**
	 * Delete user Line of business master
	 * @param userId
	 * @param tran
	 */
	public void deleteUserBU(String userId, DBTransaction tran){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserManager_DeleteUserBU", tran);
			dq.setString(1, userId);
			dq.execute();
		}  catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}		
	}
	
	
	public void insertUserBU(String buId, String userId, DBTransaction tran) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserManager_InsertUserBU", tran);
			dq.setString(1, userId);
			dq.setString(2, buId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	/**
	 * delete User Cost Center Master
	 * @param userId
	 * @param tran
	 */
	public void deleteUserCostCenter(String userId, DBTransaction tran){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserManager_DeleteUserCostCenter", tran);
			dq.setString(1, userId);
			dq.execute();
		}  catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}		
	}
	
	
	public void insertUserCostCenter(String ccId, String userId, DBTransaction tran) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserManager_InsertUserCostCenter", tran);
			dq.setString(1, userId);
			dq.setString(2, ccId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	public ArrayList<LoginData> getUsersForPermission(int permissionId) {
		ArrayList<LoginData> users = null;
		DBPreparedQuery dq = null;		
		try {					
			dq = new DBPreparedQuery("dUserManager_GetUsersForPermissions");
			dq.setInt(1, permissionId);
			dq.setInt(2, UserConstants.ACTIVE);
			users = dq.getResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR in fetching users having permission_id:"+permissionId, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
		return users;
	}
	
	public LoginData getUserDataToPopulateApplicantDetails(String userId) {
		DBPreparedQuery dq = null;
		LoginData loginData = null;
		try {
			dq = new DBPreparedQuery("dUserManager_UserDataToPopulateApplicantDetails");
			dq.setId(1, userId);
			loginData = (LoginData) (dq.getSingleObjectResult());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While getting UserInfo from db", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return loginData;
	}
	
	public ArrayList<SimpleDataObject> getRoles() {
		ArrayList<SimpleDataObject> roles = null;
		DBPreparedQuery dq = null;		
		try {					
			dq = new DBPreparedQuery("dUserManager_GetAllRoles");
			roles = dq.getResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
		return roles;
		
	}
	
	/**
	 * @param userId
	 * @return true or false
	 */
	public boolean isUserDisabled(String userId) {
		boolean isUserDisabled = true;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserManager_isUserDisabled");
			dq.setString(1, userId);
			String userDisabled = dq.getStringResult();
			if(userDisabled.equals(""+UserConstants.ACTIVE)) {
				isUserDisabled = false;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while checking isFirstTimeLogin", e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return isUserDisabled;
	}

	
	public boolean valdatePasswordAgainstLastPasswords(String userId, String password) throws Exception{
		boolean validPassword = true;
		int passwordDifferentFromLast = Integer.parseInt(GlobalApplicationProperties.getProperty("default_password_different_from_last"));
		String oldPassword = "";
		password = EncryptionUtils.encryptString(password);
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dUserManager_fetchOldPasswords");
			dq.setString(1, userId);
			oldPassword = dq.getStringResult();
			String[] newOldPasswords = new String[passwordDifferentFromLast];
			if (!Utils.isBlankOrNull(oldPassword)){
				String[] oldPasswords = oldPassword.split(",");
				newOldPasswords = Arrays.copyOf(oldPasswords, passwordDifferentFromLast);
				for (int i=0; i<newOldPasswords.length; i++){
					if (newOldPasswords[i] != null && newOldPasswords[i].equals(password)){
						validPassword = false;
						break;
					}
				}
			} else{
				passwordDifferentFromLast = 1;
			}
			if (validPassword){
				String newOldPassword = password;
				for (int i=0; i<passwordDifferentFromLast -1; i++){
					if (newOldPasswords[i] != null)
						newOldPassword = newOldPassword + "," + newOldPasswords[i];
				}
				dq = new DBPreparedQuery("dUserManager_updateOldPasswords");
				dq.setString(1, newOldPassword);
				dq.setString(2, userId);
				dq.execute();
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while comparing password with last Passwords", e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return validPassword;
	}

	
	/**
	 * @param uuid
	 * @return userId
	 */
	public String getUserIdForUuid(String uuid) {
		String userId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUser_GetUserIdForUuid");
			dq.setString(1, uuid);
			userId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching userId for UUID", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userId;
	}
	
	/**
	 * @param uuid
	 * @return userId
	 */
	public String getCandidateIdForUuid(String uuid) {
		String userId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUser_GetCandidateIdForUuid");
			dq.setString(1, uuid);
			userId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching userId for UUID", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userId;
	}
	
	public String createCandidate(LoginData loginData){
		String userId = null;
		DBPreparedQuery dq = null;
		try {
			String encryptedPassword = EncryptionUtils.encryptString(loginData.getPassword());
			dq = new DBPreparedQuery("dCreateCandidate");
			dq.setString(1, loginData.getUserName());
			dq.setString(2, loginData.getFirstName());
			dq.setString(3, loginData.getLastName());
			dq.setString(4, encryptedPassword);
			dq.setString(5, loginData.getEmail());
			dq.setString(6, loginData.getCellPhone());
			dq.execute();
			dq = new DBPreparedQuery("dUserManager_getLatestCreatedCandidateId");
			int cnt=1;
			dq.setString(cnt++,loginData.getUserName());
			dq.setString(cnt++, loginData.getEmail());
			dq.setString(cnt++, encryptedPassword);
			userId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching userId for UUID", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return userId;
	}
	
}
