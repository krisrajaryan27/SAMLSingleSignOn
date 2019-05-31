/**
 * 
 */
package com.talentPool.user.form;


import com.talentPool.common.base.TPActionForm;

/**
 * @author shivprasad
 * 
 */
public class LoginForm extends TPActionForm {
	private String userId;
	private String userName;
	private String userPassword;
	private String automaticLogon;
	private String userRoles;
	private String ignoreSignedOn;
	private String singlesignonerror;
	private int rnd;
	
	private String forcePasswordChange;
	private boolean isPasswordExpired;
	int passwordAgeLeft;
	String forgotPassword;
	private String securityQuestionId;
	String securityQuestion;
	String userEmail;
	int resetOption;
	String answer;
	/**
	 * Creates a new instance of LoginForm
	 */
	public LoginForm() {
	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the userPassword.
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            The userPassword to set.
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return Returns the automaticLogon.
	 */
	public String getAutomaticLogon() {
		return automaticLogon;
	}

	/**
	 * @param automaticLogon
	 *            The automaticLogon to set.
	 */
	public void setAutomaticLogon(String automaticLogon) {
		this.automaticLogon = automaticLogon;
	}

	/**
	 * @param userRoles The userRoles to set.
	 */
	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * @return Returns the userRoles.
	 */
	public String getUserRoles() {
		return userRoles;
	}

	/**
	 * @return the ignoreSignedOn
	 */
	public String getIgnoreSignedOn() {
		return ignoreSignedOn;
	}

	/**
	 * @param ignoreSignedOn the ignoreSignedOn to set
	 */
	public void setIgnoreSignedOn(String ignoreSignedOn) {
		this.ignoreSignedOn = ignoreSignedOn;
	}

	/**
	 * @return the paramRnd
	 */
	public int getRnd() {
		return rnd;
	}

	/**
	 * @param paramRnd the paramRnd to set
	 */
	public void setRnd(int rnd) {
		this.rnd = rnd;
	}

	/**
	 * @return the singlesignonerror
	 */
	public String getSinglesignonerror() {
		return singlesignonerror;
	}

	/**
	 * @param singlesignonerror the singlesignonerror to set
	 */
	public void setSinglesignonerror(String singlesignonerror) {
		this.singlesignonerror = singlesignonerror;
	}

	public String getForcePasswordChange() {
		return forcePasswordChange;
	}

	public void setForcePasswordChange(String forcePasswordChange) {
		this.forcePasswordChange = forcePasswordChange;
	}

	public boolean isPasswordExpired() {
		return isPasswordExpired;
	}

	public void setPasswordExpired(boolean isPasswordExpired) {
		this.isPasswordExpired = isPasswordExpired;
	}

	public int getPasswordAgeLeft() {
		return passwordAgeLeft;
	}

	public void setPasswordAgeLeft(int passwordAgeLeft) {
		this.passwordAgeLeft = passwordAgeLeft;
	}
	
	/**
	 * @return the forgotPassword
	 */
	public String getForgotPassword() {
		return forgotPassword;
	}

	/**
	 * @param forgotPassword the forgotPassword to set
	 */
	public void setForgotPassword(String forgotPassword) {
		this.forgotPassword = forgotPassword;
	}
	
	public String getSecurityQuestionId() {
		return securityQuestionId;
	}

	public void setSecurityQuestionId(String securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	/**
	 * @return the resetOption
	 */
	public int getResetOption() {
		return resetOption;
	}

	/**
	 * @param resetOption the resetOption to set
	 */
	public void setResetOption(int resetOption) {
		this.resetOption = resetOption;
	}
	
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
