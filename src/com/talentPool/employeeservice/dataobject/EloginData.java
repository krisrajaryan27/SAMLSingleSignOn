/**
 * 
 */
package com.talentPool.employeeservice.dataobject;

import java.sql.Date;

/**
 * @author shivprasad
 *
 */
public class EloginData {
	private String userId;
	private String lastLoginDate;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String emailAddress;
	private String sourceTypeId;
	private String sourceId;
	private String sourceName;
	private String homePhone;
	private String cellPhone;
	private String userRoleId;
	private String employeeCode;
	private String userLocationName;
	
	private String securityQuestion;
	private String securityQuestionId;
	private String answer;
	private String forcePasswordChange;
	private Date passwordDateModified;
	private int userStatus;
	private String passwordAge;
	private String isLDAPEnabled;
	private String timeZone;
	private String sourceTitle;
	private String sourceEmail;
	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}
	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}
	/**
	 * @param homePhone the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	/**
	 * @return the userRoleId
	 */
	public String getUserRoleId() {
		return userRoleId;
	}
	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}
	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	/**
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * @param sourceName the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the sourceTypeId
	 */
	public String getSourceTypeId() {
		return sourceTypeId;
	}
	/**
	 * @param sourceTypeId the sourceTypeId to set
	 */
	public void setSourceTypeId(String sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	/**
	 * @return the userLocationName
	 */
	public String getUserLocationName() {
		return userLocationName;
	}
	/**
	 * @param userLocationName the userLocationName to set
	 */
	public void setUserLocationName(String userLocationName) {
		this.userLocationName = userLocationName;
	}
	
	/**
	 * @return the securityQuestion
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	/**
	 * @param securityQuestion the securityQuestion to set
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
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
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	public String getForcePasswordChange() {
		return forcePasswordChange;
	}
	public void setForcePasswordChange(String forcePasswordChange) {
		this.forcePasswordChange = forcePasswordChange;
	}
	public Date getPasswordDateModified() {
		return passwordDateModified;
	}
	public void setPasswordDateModified(Date passwordDateModified) {
		this.passwordDateModified = passwordDateModified;
	}
	/**
	 * @return the securityQuestionId
	 */
	public String getSecurityQuestionId() {
		return securityQuestionId;
	}
	/**
	 * @param securityQuestionId the securityQuestionId to set
	 */
	public void setSecurityQuestionId(String securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
	}
	public String getPasswordAge() {
		return passwordAge;
	}
	public void setPasswordAge(String passwordAge) {
		this.passwordAge = passwordAge;
	}
	public String getIsLDAPEnabled() {
		return isLDAPEnabled;
	}
	public void setIsLDAPEnabled(String isLDAPEnabled) {
		this.isLDAPEnabled = isLDAPEnabled;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getSourceTitle() {
		return sourceTitle;
	}
	public void setSourceTitle(String sourceTitle) {
		this.sourceTitle = sourceTitle;
	}
	public String getSourceEmail() {
		return sourceEmail;
	}
	public void setSourceEmail(String sourceEmail) {
		this.sourceEmail = sourceEmail;
	}
	
}
