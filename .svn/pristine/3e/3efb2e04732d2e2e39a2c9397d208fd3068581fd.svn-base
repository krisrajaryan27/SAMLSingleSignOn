/*
 * Created on Jul 31, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.user.action;

import java.security.KeyPair;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.ThreadLocalContextObject;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.CaptchaException;
import com.talentPool.common.utils.Exception.RSADecryptionException;
import com.talentPool.encryption.JCryptionUtil;
import com.talentPool.recaptcha.ReCaptchaImpl;
import com.talentPool.recaptcha.ReCaptchaResponse;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.dataobject.SourceTypeData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.socialNetwork.constants.SocialMediaConstants;
import com.talentPool.socialNetwork.dataobject.EducationHistory;
import com.talentPool.socialNetwork.dataobject.Person;
import com.talentPool.socialNetwork.dataobject.UploadPersonList;
import com.talentPool.socialNetwork.dataobject.WorkHistory;
import com.talentPool.socialNetwork.manager.SocialMediaManager;
import com.talentPool.socialNetwork.scheduler.ApplicantNodeUploadJob;
import com.talentPool.socialNetwork.utils.SocialMediaUtils;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.DataViewConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.dataobject.ViewData;
import com.talentPool.user.exception.InvalidLoginException;
import com.talentPool.user.form.UserForm;
import com.talentPool.user.helper.LoginHelper;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.user.manager.UserManager;
import com.talentPool.user.utils.DataViewUtils;
import com.talentPool.user.utils.UserUtils;
import com.talentPool.userConfiguration.constants.UserConfigurationConstants;
import com.talentPool.userConfiguration.manager.UserConfigurationManager;
import com.talentPool.userConfiguration.utils.UserConfigurationUtils;

/**
 * @author pallavi
 * @date Jul 31, 2006
 */
public class UserAction extends TPDispatchAction {
	
	public ActionForward changePassword(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "changePassword";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}

		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_CHANGE_PASSWORD;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		UserForm form = (UserForm) actionForm;
		if (Utils.isBlankOrNull(form.getUserId())) {
			String userId = (String) request.getSession(false).getAttribute("userId");
			form.setUserId(userId);
		}
		if (!Utils.isBlankOrNull(form.getAgePasswordChange()) || ("1").equals(form.getAgePasswordChange())){
			forward = "changePasswordOnAge";
		}
			
		return mapping.findForward(forward);
	}
	
	public ActionForward savePassword(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "changePassword";
		UserForm form = (UserForm) actionForm;
		String forgotPassword = form.getForgotPassword();
		String forcePasswordChange = form.getForcePasswordChange();
		String forcePassChange = CommonConstants.NO;
		String securityQuestionId = form.getSecurityQuestionId();
		String securityAnswer = form.getAnswer();
		boolean seessionValidationRequired = !((!Utils.isBlankOrNull(forcePasswordChange) && forcePasswordChange.equals("1")) 
				|| (!Utils.isBlankOrNull(forgotPassword) && forgotPassword.equals("1"))
				|| form.getIsPasswordExpired());
		
		String loggedInUserId = (String) request.getSession(false).getAttribute("userId");
		String userId = form.getUserId();
		if (!Utils.isBlankOrNull(loggedInUserId) && !loggedInUserId.equalsIgnoreCase(userId)) {
			Integer[] permissions = new Integer[1];
			permissions[0] = PermissionConstants.PERMISSION_CHANGE_OTHER_USER_PASSWORD;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
		}
		if (seessionValidationRequired){
			if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
				return null;
			}
			Integer[] permissions = new Integer[1];
			permissions[0] = PermissionConstants.PERMISSION_CHANGE_PASSWORD;
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
		} else {
			forward = "setPassword";
		}
		
		String oldPassword = form.getOldPassword();
		String newPassword = form.getNewPassword();
		
		
		ActionErrors errors = new ActionErrors();
		LoginManager loginManager = new LoginManager();
		UserManager userManager = new UserManager();
		LoginData data = loginManager.getUser(userId);
		KeyPair keys = null;
		String confirmNewPassword = "";
		int rnd = form.getRnd();
		keys = (KeyPair) request.getSession().getAttribute("keys");
		try {
			// RSA decryption
			newPassword = JCryptionUtil.decrypt(newPassword, keys);
			// Regular decryption for encryt.js
			newPassword = EncryptionUtils.decryptPassword(newPassword, rnd);
			confirmNewPassword = newPassword;
		
			if (!Utils.isBlankOrNull(loggedInUserId) &&loggedInUserId.equalsIgnoreCase(userId) && (!Utils.isBlankOrNull(forgotPassword) && !forgotPassword.equals("1"))) {			
				if (Utils.isBlankOrNull(oldPassword)) {
					errors.add("change_password.error.old_password.nullOrBlank", new ActionError("change_password.error.old_password.nullOrBlank"));
				} else {
					oldPassword = JCryptionUtil.decrypt(oldPassword, keys);
					oldPassword = EncryptionUtils.decryptPassword(oldPassword, rnd);
					if (!EncryptionUtils.encryptString(oldPassword).equalsIgnoreCase(data.getPassword())) {
						errors.add("change_password.error.old_password.invalid", new ActionError("change_password.error.old_password.invalid"));
					}
				}
			}
			if (!Utils.isBlankOrNull(loggedInUserId) && !loggedInUserId.equalsIgnoreCase(userId) && (!Utils.isBlankOrNull(forgotPassword) && !forgotPassword.equals("1"))) {
				forcePassChange = CommonConstants.YES;
			}
			if (Utils.isBlankOrNull(newPassword)) {
				errors.add("change_password.error.new_password.nullOrBlank", new ActionError("change_password.error.new_password.nullOrBlank"));
			} else if (newPassword.length() < UserConstants.REQUIRED_MIN_PASSWORD_LENGTH || newPassword.length() > UserConstants.REQUIRED_MAX_PASSWORD_LENGTH) {
				errors.add("change_password.errors.short_password", new ActionError("change_password.errors.short_password"));
			} else if (!newPassword.equalsIgnoreCase(confirmNewPassword)) {
				errors.add("change_password.error.new_password.mismatch", new ActionError("change_password.error.new_password.mismatch"));
			}
			if (!Utils.isBlankOrNull(loggedInUserId) && loggedInUserId.equalsIgnoreCase(userId) && (!Utils.isBlankOrNull(forgotPassword) && !forgotPassword.equals("1")) || !seessionValidationRequired){
				if (!userManager.valdatePasswordAgainstLastPasswords(userId, newPassword)){
					errors.add("change_password.error.password_same_as_last", new ActionError("change_password.error.password_same_as_last"));
				}
				if (("1").equals(forcePasswordChange) && (Utils.isBlankOrNull(securityQuestionId)|| Utils.isBlankOrNull(securityAnswer))){
					errors.add("forgot_password.error.invalid_question_answer", new ActionError("forgot_password.error.invalid_question_answer"));
				}
			}
		} catch (RSADecryptionException e) {
			errors.add("change_password.error.password.decrypt", new ActionError("change_password.error.password.decrypt"));
		} catch (Exception e) {
			errors.add("change_password.error.password.update_failed", new ActionError("change_password.error.password.update_failed"));
		}
		
		if (errors.size() == 0) {
			// Update
			try {
				if((!Utils.isBlankOrNull(loggedInUserId) && loggedInUserId.equalsIgnoreCase(userId) && (!Utils.isBlankOrNull(forgotPassword) && !forgotPassword.equals("1")) || !seessionValidationRequired) && !Utils.isBlankOrNull(UserConstants.PASSWORD_PATTERN) && !UserUtils.isValidPasswordPattern(newPassword)) {
					errors.add("change_password.error.new_password.weak", new ActionError("change_password.error.new_password.weak"));
				}else {
					if (Utils.isBlankOrNull(securityQuestionId)){
						userManager.savePassword(userId, newPassword, forcePassChange);
						if(!Utils.isBlankOrNull(forgotPassword) && forgotPassword.equals("1") && userManager.isUserDisabled(userId)){
							AdminManager adminManager = new AdminManager();
							adminManager.changeUserStatus(userId, UserConstants.ACTIVE);
						}
					} else {
						userManager.savePasswordAndSecrityQuestion(userId, newPassword, securityQuestionId, securityAnswer);
					}
					if (seessionValidationRequired && !(!Utils.isBlankOrNull(form.getAgePasswordChange()) || 
							("1").equals(form.getAgePasswordChange()))){
						request.setAttribute("update", "1");
						forward="passwordSaved";
					}else {
						request.setAttribute("passwordChanged", "1");
					}
				}
			} catch (SQLException e) {
				errors.add("change_password.error.password.update_failed", new ActionError("change_password.error.password.update_failed"));
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while changing the user password", e);
			}
		}
		if (errors.size() > 0) {			
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward passwordSaved(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "changePassword";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		request.setAttribute("update", "1");
		return mapping.findForward(forward);
	}
	
	
	public ActionForward accountSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "accountSettings";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		UserForm form = (UserForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		try {
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(userId);
			
			form.setUserName(loginData.getUserName());			
			if (form.getMode().equals("accountSettings")) {
				form.setUserId(loginData.getUserId());
				form.setUserName(loginData.getUserName());
				form.setFirstName(loginData.getFirstName());
				form.setLastName(loginData.getLastName());
				form.setEmail(loginData.getEmail());
				form.setHomePhone(loginData.getHomePhone());
				form.setCellPhone(loginData.getCellPhone());
				form.setTimeZone(loginData.getTimeZone());
			} 
			form.setRole(loginData.getRoleTitle());
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting user info for userId = " + userId, e);
		}		
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_PROFILE);
		request.setAttribute("t", NavigationConstants.T_MYACCOUNT);		
		return mapping.findForward(forward);
	}
	
	public ActionForward saveAccountSettings(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		UserForm userForm = (UserForm) actionForm;
		// If userId is set then submit Eddited else create new User
		String userId = userForm.getUserId();

		// Do the validations here
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {	
			if (userForm.getFirstName().trim().length() < 1) {
				errors.add("add_user.errors.fname_required", new ActionError("add_user.errors.fname_required"));
			} else if (!Utils.isValidPattern(userForm.getFirstName(), Utils.regName)) {
				errors.add("add_user.errors.invalid_fname", new ActionError("add_user.errors.invalid_fname"));
			}
			if (userForm.getLastName().trim().length() < 1) {
				errors.add("add_user.errors.lname_required", new ActionError("add_user.errors.lname_required"));
			} else if (!Utils.isValidPattern(userForm.getLastName(), Utils.regName)) {
				errors.add("add_user.errors.invalid_lname", new ActionError("add_user.errors.invalid_lname"));
			}
			
			if (!Utils.isValidPattern(userForm.getEmail(), Utils.regEmail)) {
				errors.add("add_user.errors.invalid_email", new ActionError("add_user.errors.invalid_email"));
			}		

			if (errors.size() > 0) {
				request.setAttribute(Globals.ERROR_KEY, errors);
				return accountSettings(mapping, actionForm, request, response);
			}

			/*
			 * Save The record If valid Data First Create new User if User ID Is null
			 */
			LoginData loginData = new LoginData();
			loginData.setFirstName(userForm.getFirstName());
			loginData.setLastName(userForm.getLastName());
			loginData.setEmail(userForm.getEmail());
			loginData.setTimeZone(userForm.getTimeZone());
			if(!Utils.isBlankOrNull(userForm.getHomePhone())) {
				loginData.setHomePhone(userForm.getHomePhone().trim());
			}
			if (!Utils.isBlankOrNull(userForm.getCellPhone())) {
				loginData.setCellPhone(userForm.getCellPhone().trim());
			}
			AdminManager adminManager = new AdminManager();
			adminManager.updateUserAccount(userId, loginData);
			LoginManager loginManager = new LoginManager();
			LoginData newLoginData = loginManager.getUser(userId);
		
			request.getSession(false).setAttribute("userCellPhone", loginData.getCellPhone());
			request.getSession(false).setAttribute("timeZoneId", loginData.getTimeZone());
			request.getSession(false).setAttribute("lastLogin", DateUtils.getSystemDateTimeFormat(newLoginData.getLastLogin()));
			/*TimeZone tzone = TimeZone.getTimeZone(loginData.getTimeZone());
			TimeZone.setDefault(tzone);*/
			ThreadLocalContextObject obj = new ThreadLocalContextObject();
			obj.setTimeZone(loginData.getTimeZone());
			MyThreadLocal.set(obj);
			request.setAttribute("saved", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Saving User Data ", e);
			errors.add("my_account.error.save_account_settings", new ActionError("my_account.error.save_account_settings"));
			saveErrors(request, errors);
		}

		return accountSettings(mapping, actionForm, request, response);
	}
	
	public ActionForward saveTimeZone(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showTimeZone";
		UserForm userForm = (UserForm) actionForm;
		// If userId is set then submit Eddited else create new User
		String userId = (String) request.getSession(false).getAttribute("userId");

		// Do the validations here
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {	
			/*
			 * Save The record If valid Data First Create new User if User ID Is null
			 */
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(userId);
			loginData.setTimeZone(userForm.getTimeZone());
			AdminManager adminManager = new AdminManager();
			adminManager.updateUserAccount(userId, loginData);
			loginData = loginManager.getUser(userId);
			request.getSession(false).setAttribute("timeZoneId", loginData.getTimeZone());
			request.getSession(false).setAttribute("lastLogin", DateUtils.getSystemDateTimeFormat(loginData.getLastLogin()));
			/*TimeZone tzone = TimeZone.getTimeZone(loginData.getTimeZone());
			TimeZone.setDefault(tzone);*/
			ThreadLocalContextObject obj = new ThreadLocalContextObject();
			obj.setTimeZone(loginData.getTimeZone());
			MyThreadLocal.set(obj);
			request.setAttribute("saved", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Saving User Data ", e);
			errors.add("my_account.error.save_account_settings", new ActionError("my_account.error.save_account_settings"));
			saveErrors(request, errors);
		}

		return mapping.findForward(forward);
	}
	
	public ActionForward myAccount(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "myAccount";		
		
		request.setAttribute("t", NavigationConstants.T_MYACCOUNT);
		return mapping.findForward(forward);
	}
	
	public ActionForward userConfiguration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "userConfiguration";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String userId = (String) request.getSession(false).getAttribute("userId");
		try {
			UserConfigurationManager userConfigurationManager = new UserConfigurationManager();
			String values = userConfigurationManager.getUserConfigurations(userId,UserConfigurationConstants.APPLICANT_TOOLTIP);
			request.setAttribute("userConfValue", values);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}		
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_USER_CONFIGURATIONS);
		request.setAttribute("t", NavigationConstants.T_MYACCOUNT);		
		return mapping.findForward(forward);
	}
	
	public ActionForward saveUserConfigurations(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		UserForm userForm = (UserForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		try {	
			String value = userForm.getUserConfValue();
			String confId = UserConfigurationConstants.APPLICANT_TOOLTIP;
			String restoreConfig=userForm.getRestoreConfig();
			UserConfigurationManager userConfigurationManager = new UserConfigurationManager();
			if(restoreConfig != null && restoreConfig.equals(UserConfigurationConstants.RESTORE_CONFIG)){
				userConfigurationManager.deleteUserConfigurations(userId, confId);
			}else{
				userConfigurationManager.updateUserConfigurations(userId, confId, value);
			}
			UserConfigurationUtils.setUserConfMap();
			request.setAttribute("saved", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return userConfiguration(mapping, actionForm, request, response);
	}
	
	public ActionForward userDataViewConfiguration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "userDataViewConfiguration";
		UserForm userForm = (UserForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		try {	
			UserManager userManager = new UserManager();
			ArrayList<ViewData> viewDataList = userManager.getUserViewDataConfig(userId);
			if (viewDataList != null && viewDataList.size()>0) {
				for (int i = 0; i < viewDataList.size(); i++) {
					ViewData viewData = viewDataList.get(i);
					if (viewData.getViewType().equals(UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG)) {
						userForm.setColumn1_1(viewData.getColumn1());
						userForm.setColumn1_2(viewData.getColumn2());
					}
				}
			}
			setDataViewConfigDefaultValues(userForm);
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_USER_DATA_VIEW_CONFIGURATION);
			request.setAttribute("t", NavigationConstants.T_MYACCOUNT);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward saveDataViewConfig(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		UserForm form = (UserForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		try {
			UserManager userManager = new UserManager();
			userManager.saveDataViewConfig(userId, form.getColumn1_1(), form.getColumn1_2());
			request.setAttribute("saved", "1");
			DataViewUtils.setAllHeaderToMap();
			DataViewUtils.setDataViewMap();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return userDataViewConfiguration(mapping, actionForm, request, response);
	}
	
	public ActionForward resetUserDataViewConfiguration(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String userId = (String) request.getSession(false).getAttribute("userId");
		UserForm userForm = (UserForm) actionForm;
		try {
			UserManager userManager = new UserManager();
			userManager.deleteDataViewConfig(userId, null);
			request.setAttribute("reset", "1");
			resetUserForm(userForm);
			DataViewUtils.setDataViewMap();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return userDataViewConfiguration(mapping, userForm, request, response);
	}
	
	private void setDataViewConfigDefaultValues(UserForm userForm){
		setPositionDataViewDefaultValues(userForm);
	}
	private void setPositionDataViewDefaultValues(UserForm userForm){
		if(Utils.isBlankOrNull(userForm.getColumn1_1())){
			userForm.setColumn1_1(DataViewConstants.POSITION_DEPARTMENT);
		}
		if(Utils.isBlankOrNull(userForm.getColumn1_2())){
			userForm.setColumn1_2(DataViewConstants.POSITION_RECRUITERS);
		}
	}
	private void resetUserForm(UserForm userForm){
		userForm.setColumn1_1(null);
		userForm.setColumn1_2(null);
	}
	
	/**
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward forgotPassword(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "forgotPassword";
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			UserForm userForm = (UserForm) actionForm;
			String secQ = userForm.getSecurityQuestion();
			String email = userForm.getEmail();
			String userId = userForm.getUserId();
			String userIdFromRequest = (String) request.getSession(false).getAttribute("userId");
			request.setAttribute("userId", userIdFromRequest);
			if (!Utils.isBlankOrNull(request.getParameter("submitted"))) {
				if("1".equals(TPApplicationProperties.getProperty("forgotPassword_captcha_enabled")) 
						&& request.getParameterMap().containsKey("recaptcha_challenge_field")) {
					//validate Recaptcha Security code
					ReCaptchaImpl recaptcha = new ReCaptchaImpl();
					ReCaptchaResponse recResponse = recaptcha.checkAnswer(request.getRemoteAddr(),
							request.getParameter("recaptcha_challenge_field"), request.getParameter("recaptcha_response_field"));
					if(!recResponse.isValid()){
						throw new CaptchaException(TPLabels.getLabel("add_applicant.errors.challenge_not_valid"));
					}
				}	
				LoginManager loginManager = new LoginManager();
				int resetOption = userForm.getResetOption();
				switch (resetOption) {
				case 1:// security question
					String answer = userForm.getAnswer();
					if(Utils.isBlankOrNull(secQ) || Utils.isBlankOrNull(answer)) {
						errors.add("forgot_password.error.invalid_question_answer", new ActionError("forgot_password.error.invalid_question_answer"));
					} else {
						LoginData loginData = loginManager.login(new LoginData(userForm.getUserName(), ""));
						if(!Utils.isBlankOrNull(loginData.getUserId()) && loginData.getUserId().equals(userId)){
							if (loginData != null && loginData.getAnswer().equalsIgnoreCase(answer)) {
								forward = "setPassword";
								userForm.setForgotPassword("1");
								request.setAttribute("doNotShowOldPassword", "1");
							} else {
								errors.add("forgot_password.error.wrong_answer", new ActionError("forgot_password.error.wrong_answer"));
							}
						}else{
							errors.add("forgot_password.error.wrong_answer", new ActionError("forgot_password.error.wrong_answer"));
						}
					}
					break;
				case 2:// confirmation link
					if (Utils.isBlankOrNull(email) || !Utils.isValidPattern(email, Utils.regEmail)) {
						errors.add("forgot_password.error.invalid_email", new ActionError("forgot_password.error.invalid_email"));
					} else {
						UserManager userManager = new UserManager();
						boolean success = userManager.saveUUIDandSendConfirmationLink(email, CommonConstants.PRODUCT_TALENTPOOL,Utils.buildTalentPoolURLFromIncomingRequestObject(request));
						if (success) {
							request.setAttribute("saved", "1");
						} else {
							errors.add("forgot_password.error.unable_to_send_mail", new ActionError("forgot_password.error.unable_to_send_mail"));
						}
					}
					break;
				default:
					errors.add("forgot_password.error.invalid_reset_option", new ActionError("forgot_password.error.invalid_reset_option"));
				}
			}
		} catch (CaptchaException e) {
				errors.add("add_applicant.errors.challenge_not_valid", new ActionError("add_applicant.errors.challenge_not_valid"));
				TPLogger.getLogger().debug(e.getMessage());
		}catch (Exception e) {
			TPLogger.getLogger().error(e);
			errors.add("forgot_password.error.unable_to_process", new ActionError("forgot_password.error.unable_to_process"));
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward password(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "loginpage";
		LoginData loginData = null;
		LoginManager loginManager = new LoginManager();
		UserForm userForm = (UserForm) actionForm;
		String userName = userForm.getUserName();
		LoginHelper loginHelper = new LoginHelper();
		ActionErrors errors =  new ActionErrors();
		try {
			loginData = loginManager.login(new LoginData(userName, ""));
			boolean enableForgotPassword = loginHelper.enableForgotPassword(userName, loginData.getIsUserLdapSetting(),
					loginData.getUserId(), loginData.getRoleId());
			if (enableForgotPassword) {
				forward = "forgotPassword";
				userForm.setSecurityQuestion(loginData.getSecurityQuestion());
				userForm.setEmail(loginData.getEmail());
				userForm.setUserName(loginData.getUserName());
				userForm.setUserId(loginData.getUserId());
				userForm.setSecurityQuestionId(loginData.getSecurityQuestionId());
				userForm.setForgotPassword("1");
				request.getSession(false).setAttribute("userId", loginData.getUserId());
				request.setAttribute("userId", loginData.getUserId());
			} else {
				request.setAttribute("disableForgotPassword", "1");
			}
		} catch (InvalidLoginException e) {
			errors.add("userNameAndPassword", new ActionError("login.errors.username.password.nomatch"));
			TPLogger.getLogger().debug("Could not login loginName=" + userName);
		} catch (Exception e) {
			errors.add("userNameAndPassword", new ActionError("common.error.unable_to_process_request"));
			TPLogger.getLogger().debug("Error While Getting Login Info for " + userName);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward setPassword(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "setPassword";
		UserForm userForm = (UserForm) actionForm;
		if (request.getAttribute("forcePasswordChange") != null)
			userForm.setForcePasswordChange((String) request.getAttribute("forcePasswordChange"));
		if (request.getAttribute("passwordExpired") != null){
			userForm.setIsPasswordExpired((boolean) request.getAttribute("passwordExpired"));
		}else {
			userForm.setIsPasswordExpired(false);
		}
		userForm.setUserId((String) request.getAttribute("userId"));
		userForm.setForgotPassword("0");
		return mapping.findForward(forward);
	}
	
	public ActionForward changePasswordForLink(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forgotPassword = request.getParameter("forgotPassword");
		String uuid = request.getParameter("id");
		UserForm userForm = (UserForm) actionForm;
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		String forward = "";
		try {
			if(!Utils.isBlankOrNull(forgotPassword) && forgotPassword.equals("1") && !Utils.isBlankOrNull(uuid)) {
				forward = "setPassword";
				UserManager userManager = new UserManager();
				String userId = userManager.getUserIdForUuid(uuid);
				if (Utils.isBlankOrNull(userId)) {
					errors.add("forgot_password.error.invalid_link", new ActionError("forgot_password.error.invalid_link"));
					request.setAttribute("tokenExpired", "1");
					forward = "tokenExpired";
				} else {
					userForm.setUserId(userId);
					userForm.setForgotPassword("1");
					userManager.saveUUID(userId,null);
				}
				
				
			} else {
				if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
					return null;
				}
				forward = "changePassword";
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward socialProfile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "socialProfile";	
		String userId= (String) request.getSession(false).getAttribute("userId");
		SocialMediaManager socialMediaManager = new SocialMediaManager();
		UserForm form = (UserForm) actionForm;
		//if (!"1".equalsIgnoreCase(request.getParameter("update"))){
			MastersManager mastersManager = new MastersManager();
			ArrayList<SourceData> allSources;
			try {
				
				ArrayList<String> degreeIds = CommonUtils.getBranchIds();
				ArrayList<String> degreeNames = CommonUtils.getBranchNames();
				ArrayList<String> branchIds = CommonUtils.getDegreeIds();
				ArrayList<String> branchNames = CommonUtils.getDegreeNames();
				
				allSources = mastersManager.getSourcesBySorceCategory(SocialMediaConstants.SOCIAL_MEDIA_SOURCE_CATEGORY_TYPE);
			
				//boolean tokensCheck = socialMediaManager.checkTokenAndInformationSharedForUser(userId, "1");
				
//				if (!tokensCheck){
//					for(SourceData sd : allSources) {
//							sd.setTokenFlag("false");
//					}
//				}
				request.setAttribute("sources", allSources);
				request.setAttribute("degreeIds", degreeIds);
				request.setAttribute("degreeNames", degreeNames);
				request.setAttribute("branchIds", branchIds);
				request.setAttribute("branchNames", branchNames);
				String sourceId = request.getParameter("socialMediaTypeId");
				
				SourceData sData = null;
				SourceTypeData stData = null;
				if (!Utils.isBlankOrNull(sourceId)){
					sData = mastersManager.getSource(sourceId);
					stData = mastersManager.getSourceType(sData.getSourceTypeId());
				}
				if ("1".equalsIgnoreCase(request.getParameter("update")) || (stData != null && SocialMediaConstants.SOCIAL_MEDIA_SOURCE_CATEGORY_TYPE.equalsIgnoreCase(stData.getItemName()))){
					form.setSourceId(sourceId);
					for(SourceData sd : allSources) {
						if (sd.getSourceId().equals(sourceId)){
							allSources.remove(sd);
							break;
						}
					}
					Map<String,String> tokens = socialMediaManager.getTokenForUser(userId);
					Person person = socialMediaManager.getApplicantDetails(tokens.get(sourceId), sData.getSourceTitle());
					setDetailsInForm(person, form);
				} else{
					Person person = new Person();
					String personId = socialMediaManager.checkIfUserPresentInGraph(userId, SocialMediaConstants.GRAPH_UPLOAD_COMPLETE);
					if (!Utils.isBlankOrNull(personId)){
						person = socialMediaManager.getPersonDetails(personId);
					}
					if(person != null){
						setDetailsOfExistingPersonInForm(person, form);
					}
				}
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error in fetching social Profile", e);
			}
			
		request.setAttribute("t", NavigationConstants.T_MYACCOUNT);
		request.setAttribute("mainPane", NavigationConstants.MAINPANE_USER_SOCIAL_PROFILE);
		return mapping.findForward(forward);
	}
	
	private void setDetailsInForm(Person person, UserForm form){
		if (!Utils.isBlankOrNull(person.getName())){
			String[] name = person.getName().split(" ");
			form.setFirstName(name[0]);
			if (name.length>1)
				form.setLastName(name[name.length-1]);
		}
		form.setEmail(person.getEmail());
		form.setLocation(person.getLocation());
		List<EducationHistory> eduDetails = person.getEducationHistory();
		if (eduDetails == null){
			eduDetails = new ArrayList<EducationHistory>();
		}
		int size = eduDetails.size();
		String[] degreeId = new String[size];
		String[] branchId = new String[size];
		String[] yearOfPassing = new String[size];
		String[] institutes =  new String[size];
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getYearOfPassing()!=null){
				yearOfPassing[i] = String.valueOf(eduDetails.get(i).getYearOfPassing());
			} else{
				yearOfPassing[i] = "";
			}
		}
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getInstitutionName() != null){
				institutes[i] = eduDetails.get(i).getInstitutionName();
			}else{
				institutes[i] = "";
			}
		}
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getDegree()!=null){
				degreeId[i] = eduDetails.get(i).getDegree();
			}else{
				degreeId[i] = "";
			}
		}
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getSpecialization()!=null){
				branchId[i] = eduDetails.get(i).getSpecialization();
			}else{
				branchId[i] = "";
			}
		}
		List<WorkHistory> empHistory = person.getWorkHistory();
		if (empHistory == null){
			empHistory = new ArrayList<WorkHistory>();
		}
		setEmploymentHistoryInForm(form,empHistory);
		
		form.setEducationDegreeId(degreeId);
		form.setEducationMajorId(branchId);
		form.setEducationYearOfPassing(yearOfPassing);
		form.setEducationInstitute(institutes);
	}
	
	private void setDetailsOfExistingPersonInForm(Person person, UserForm form){
		if (!Utils.isBlankOrNull(person.getName())){
			String[] name = person.getName().split(" ");
			form.setFirstName(name[0]);
			if (name.length>1)
				form.setLastName(name[name.length-1]);
		}
		form.setEmail(person.getEmail());
		form.setLocation(person.getLocation());
		List<EducationHistory> eduDetails = person.getEducationHistory();
		if (eduDetails == null){
			eduDetails = new ArrayList<EducationHistory>();
		}
		int size = eduDetails.size();
		String[] degreeId = new String[size];
		String[] branchId = new String[size];
		String[] yearOfPassing = new String[size];
		String[] institutes =  new String[size];
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getYearOfPassing()!=null){
				yearOfPassing[i] = String.valueOf(eduDetails.get(i).getYearOfPassing());
			} else{
				yearOfPassing[i] = "";
			}
		}
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getInstitutionName() != null){
				institutes[i] = eduDetails.get(i).getInstitutionName();
			}else{
				institutes[i] = "";
			}
		}
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getDegree()!=null){
				degreeId[i] = eduDetails.get(i).getDegree();
			}else{
				degreeId[i] = "";
			}
		}
		for (int i=0; i<size; i++){
			if (eduDetails.get(i).getSpecialization()!=null){
				branchId[i] = eduDetails.get(i).getSpecialization();
			}else{
				branchId[i] = "";
			}
		}
		List<WorkHistory> empHistory = person.getWorkHistory();
		if (empHistory == null){
			empHistory = new ArrayList<WorkHistory>();
		}
		setEmploymentHistoryOfExistingPersonInForm(form,empHistory);
		
		form.setEducationDegreeId(degreeId);
		form.setEducationMajorId(branchId);
		form.setEducationYearOfPassing(yearOfPassing);
		form.setEducationInstitute(institutes);
	}
	
//	private String[] setFromeduDetailsToDegreeArray(ArrayList<String> ids, ArrayList<String> names, List<EducationHistory> eduDetails){
//		int size = eduDetails.size();
//		String[] id = new String[size];
//		for (int i=0; i<size; i++){
//			for (int j=0; j<names.size(); j++){
//				if (names.get(j).equalsIgnoreCase(eduDetails.get(i).getDegree())){
//					id[i] = ids.get(j);
//					break;
//				}
//			}
//			//id[i] = String.valueOf(i+1);
//		}
//		return id;
//	}
//	
//	private String[] setFromeduDetailsToBranchArray(ArrayList<String> ids, ArrayList<String> names, List<EducationHistory> eduDetails){
//		int size = eduDetails.size();
//		String[] id = new String[size];
//		for (int i=0; i<size; i++){
//			for (int j=0; j<names.size(); j++){
//				if (names.get(j).equalsIgnoreCase(eduDetails.get(i).getSpecialization())){
//					id[i] = ids.get(j);
//					break;
//				}
//			}
//		}
//		return id;
//	}
	
	private void setEmploymentHistoryInForm(UserForm form, List<WorkHistory> empHistory){
		if (empHistory == null){
			empHistory = new ArrayList<WorkHistory>();
		}
		int size = empHistory.size();
		String[] employmentEmployerId = new String[size];
		String[] employmentDesignationId = new String[size];
		String[] employmentFromDate = new String[size];
		String[] employmentToDate = new String[size];
		for (int i=0; i<size; i++){
			employmentEmployerId[i] = empHistory.get(i).getEmployerName()!=null?empHistory.get(i).getEmployerName():"";
		}
		for (int i=0; i<size; i++){
			employmentDesignationId[i] = empHistory.get(i).getRole()!=null?empHistory.get(i).getRole():"";
		}
		for (int i=0; i<size; i++){
			employmentFromDate[i] = empHistory.get(i).getFromDate()!=null?empHistory.get(i).getFromDate():"";
		}
		for (int i=0; i<size; i++){
			employmentToDate[i] = empHistory.get(i).getToDate()!=null?empHistory.get(i).getToDate():"";
		}
		form.setEmploymentEmployerId(employmentEmployerId);
		form.setEmploymentDesignationId(employmentDesignationId);
		form.setEmploymentFromDate(employmentFromDate);
		form.setEmploymentToDate(employmentToDate);
	}
	
	
	private void setEmploymentHistoryOfExistingPersonInForm(UserForm form, List<WorkHistory> empHistory){
		if (empHistory == null){
			empHistory = new ArrayList<WorkHistory>();
		}
		int size = empHistory.size();
		String[] employmentEmployerId = new String[size];
		String[] employmentDesignationId = new String[size];
		String[] employmentFromDate = new String[size];
		String[] employmentToDate = new String[size];
		for (int i=0; i<size; i++){
			employmentEmployerId[i] = empHistory.get(i).getEmployerName()!=null?empHistory.get(i).getEmployerName():"";
		}
		for (int i=0; i<size; i++){
			employmentDesignationId[i] = empHistory.get(i).getRole()!=null?empHistory.get(i).getRole():"";
		}
		for (int i=0; i<size; i++){
			if (!Utils.isBlankOrNull(empHistory.get(i).getFromDate())){
				employmentFromDate[i] = Utils.getDateStringConvertedToOtherDateFormat(empHistory.get(i).getFromDate(),"YYYY-MM" ,Utils.regMMMYYYYFormat);
			} else {
				employmentFromDate[i] = "";
			}
		}
		for (int i=0; i<size; i++){
			if (!Utils.isBlankOrNull(empHistory.get(i).getToDate())){
				employmentToDate[i] = Utils.getDateStringConvertedToOtherDateFormat(empHistory.get(i).getToDate(),"YYYY-MM" ,Utils.regMMMYYYYFormat);
			}else {
				employmentToDate[i] = "";
			}
		}
		form.setEmploymentEmployerId(employmentEmployerId);
		form.setEmploymentDesignationId(employmentDesignationId);
		form.setEmploymentFromDate(employmentFromDate);
		form.setEmploymentToDate(employmentToDate);
	}
	
	
	private Person setDetailsFromFormToPerson(UserForm form){
		Person person = new Person();
		person.setName(form.getFirstName()+" " +form.getLastName());
		person.setEmail(form.getEmail());
		person.setLocation(form.getLocation());
		String[] degreeId = form.getEducationDegreeId();
		String[] majorId = form.getEducationMajorId();
		String[] yearOfPassing = form.getEducationYearOfPassing();
		String[] institutes = form.getEducationInstitute();
		if (yearOfPassing != null){
			for (int i=0; i<yearOfPassing.length; i++){
				EducationHistory eHistory = new EducationHistory();
				eHistory.setDegree(degreeId[i]);
				eHistory.setInstitutionName(institutes[i]);
				eHistory.setSpecialization(majorId[i]);
				eHistory.setYearOfPassing(dateFormatter("JAN-"+yearOfPassing[i]));
				person.getEducationHistory().add(eHistory);
			}
		}
		String[] employmentEmployerId = form.getEmploymentEmployerId();
		String[] employmentDesignationId = form.getEmploymentDesignationId();
		String[] employmentFromDate = form.getEmploymentFromDate();
		String[] employmentToDate = form.getEmploymentToDate();
		if (employmentEmployerId != null){
			for (int i=0; i<employmentEmployerId.length; i++){
				WorkHistory wHistory = new WorkHistory();
				wHistory.setEmployerName(employmentEmployerId[i]);
				wHistory.setFromDate(dateFormatter(employmentFromDate[i]));
				wHistory.setRole(employmentDesignationId[i]);
				wHistory.setToDate(dateFormatter(employmentToDate[i]));
				person.getWorkHistory().add(wHistory);
			}
		}
		return person;
	}
	
	/**
	 * @param date
	 * @return formatted Date
	 */
	private String dateFormatter(String date){
		String result = null;
		if(!Utils.isBlankOrNull(date)){
			date = "01-"+date; // appending 01 (dd) before MMM-yyyy input date string
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				Date varDate = dateFormat.parse(date);
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				result = dateFormat.format(varDate);
			} catch (ParseException e) {
				TPLogger.getLogger().error("Error parsing date in person form to person object conversion for user info",e);
			}
		}
		return result;
		
	}
	
	
	public ActionForward saveSocialProfile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "socialProfile";	
		String formChangedFlag = (String) request.getParameter("formChangedFlag");
		UserForm form = (UserForm) actionForm;
		String userId= (String) request.getSession(false).getAttribute("userId");
		SocialMediaManager socialMediaManager = new SocialMediaManager();
		MastersManager mastersManager = new MastersManager();
		ArrayList<SourceData> allSources;
		try {
			ApplicantNodeUploadJob job = new ApplicantNodeUploadJob();
			String personId=socialMediaManager.addUserForGraphUpload(userId, SocialMediaConstants.GRAPH_UPLOAD_PENDING);
			if("1".equals(formChangedFlag)){
				SocialMediaUtils.removeUserInfoFromGraph(personId);
			}
			job.updatepersonUploadStatusInDB(personId, SocialMediaConstants.GRAPH_UPLOAD_PENDING);
			Person person = setDetailsFromFormToPerson(form);
			person.setPersonId(personId);
			UploadPersonList uploadPersonList = new UploadPersonList();
			uploadPersonList.getPerson().add(person);
			
			UploadPersonList responseList = SocialMediaUtils.uploadPersonNodesIntoGraph(uploadPersonList);
			job.updatepersonUploadStatusInDB(responseList.getPerson().get(0).getPersonId(),SocialMediaConstants.GRAPH_UPLOAD_COMPLETE);
			
			allSources = mastersManager.getSourcesBySorceCategory(SocialMediaConstants.SOCIAL_MEDIA_SOURCE_CATEGORY_TYPE);
			
//			String tokensCheck = socialMediaManager.checkTokenAndInformationSharedForUser(userId, "1");
//			for(SourceData sd : allSources) {
//				if(tokensCheck!=null && tokensCheck.contains(sd.getSourceId())){
//					sd.setTokenFlag("true");
//				}else{
//					sd.setTokenFlag("false");
//				}
//			}
			request.setAttribute("sources", allSources);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in Saving social Profile", e);
		}
		
		return mapping.findForward(forward);
	}
	
	public ActionForward showTimeZones(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "showTimeZone";	
		try{
			request.setAttribute("mainPane", NavigationConstants.MAINPANE_USER_TIME_ZONE);
			request.setAttribute("t", NavigationConstants.T_MYACCOUNT);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in Saving social Profile", e);
		}
		
		return mapping.findForward(forward);
	}
}
