/**
 * 
 */
package com.talentPool.employeeservice.dataobject;

/**
 * @author Shantanu
 *
 */
public class EinboxData {
	private int inboxOutgoingSSLEnabled;
	private int inboxOutgoingTLSEnabled;
	private String smtpHost;
	private String inboxOutgoingPort;
	private int inboxSmtpAuthRequired;
	private int inboxSmtpAuthSame;
	private String userName;
	private String password;
	private String inboxSmtpUserName;
	private String inboxSmtpPassword;
	private String inboxEmail;

	private String inboxServerType;	
	private String exchangeServerVersion;
	private String domainName;
	private String exchangeServerName;
	private String exchangeSmtp;
	
	/**
	 * @return the exchangeSmtp
	 */
	public String getExchangeSmtp() {
		return exchangeSmtp;
	}
	/**
	 * @param exchangeSmtp the exchangeSmtp to set
	 */
	public void setExchangeSmtp(String exchangeSmtp) {
		this.exchangeSmtp = exchangeSmtp;
	}
	/**
	 * @return the exchangeServerVersion
	 */
	public String getExchangeServerVersion() {
		return exchangeServerVersion;
	}
	/**
	 * @param exchangeServerVersion the exchangeServerVersion to set
	 */
	public void setExchangeServerVersion(String exchangeServerVersion) {
		this.exchangeServerVersion = exchangeServerVersion;
	}
	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	/**
	 * @return the exchangeServerName
	 */
	public String getExchangeServerName() {
		return exchangeServerName;
	}
	/**
	 * @param exchangeServerName the exchangeServerName to set
	 */
	public void setExchangeServerName(String exchangeServerName) {
		this.exchangeServerName = exchangeServerName;
	}
	
	
	/**
	 * @return the inboxEmail
	 */
	public String getInboxEmail() {
		return inboxEmail;
	}
	/**
	 * @param inboxEmail the inboxEmail to set
	 */
	public void setInboxEmail(String inboxEmail) {
		this.inboxEmail = inboxEmail;
	}
	/**
	 * @return the inboxOutgoingPort
	 */
	public String getInboxOutgoingPort() {
		return inboxOutgoingPort;
	}
	/**
	 * @param inboxOutgoingPort the inboxOutgoingPort to set
	 */
	public void setInboxOutgoingPort(String inboxOutgoingPort) {
		this.inboxOutgoingPort = inboxOutgoingPort;
	}
	/**
	 * @return the inboxOutgoingSSLEnabled
	 */
	public int getInboxOutgoingSSLEnabled() {
		return inboxOutgoingSSLEnabled;
	}
	/**
	 * @param inboxOutgoingSSLEnabled the inboxOutgoingSSLEnabled to set
	 */
	public void setInboxOutgoingSSLEnabled(int inboxOutgoingSSLEnabled) {
		this.inboxOutgoingSSLEnabled = inboxOutgoingSSLEnabled;
	}
	
	/**
	 * @return the inboxOutgoingTSLEnabled
	 */
	public int getInboxOutgoingTLSEnabled() {
		return inboxOutgoingTLSEnabled;
	}
	/**
	 * @param inboxOutgoingTSLEnabled the inboxOutgoingTSLEnabled to set
	 */
	public void setInboxOutgoingTLSEnabled(int inboxOutgoingTLSEnabled) {
		this.inboxOutgoingTLSEnabled = inboxOutgoingTLSEnabled;
	}
	
	/**
	 * @return the inboxSmtpAuthRequired
	 */
	public int getInboxSmtpAuthRequired() {
		return inboxSmtpAuthRequired;
	}
	/**
	 * @param inboxSmtpAuthRequired the inboxSmtpAuthRequired to set
	 */
	public void setInboxSmtpAuthRequired(int inboxSmtpAuthRequired) {
		this.inboxSmtpAuthRequired = inboxSmtpAuthRequired;
	}
	/**
	 * @return the inboxSmtpAuthSame
	 */
	public int getInboxSmtpAuthSame() {
		return inboxSmtpAuthSame;
	}
	/**
	 * @param inboxSmtpAuthSame the inboxSmtpAuthSame to set
	 */
	public void setInboxSmtpAuthSame(int inboxSmtpAuthSame) {
		this.inboxSmtpAuthSame = inboxSmtpAuthSame;
	}
	/**
	 * @return the inboxSmtpPassword
	 */
	public String getInboxSmtpPassword() {
		return inboxSmtpPassword;
	}
	/**
	 * @param inboxSmtpPassword the inboxSmtpPassword to set
	 */
	public void setInboxSmtpPassword(String inboxSmtpPassword) {
		this.inboxSmtpPassword = inboxSmtpPassword;
	}
	/**
	 * @return the inboxSmtpUserName
	 */
	public String getInboxSmtpUserName() {
		return inboxSmtpUserName;
	}
	/**
	 * @param inboxSmtpUserName the inboxSmtpUserName to set
	 */
	public void setInboxSmtpUserName(String inboxSmtpUserName) {
		this.inboxSmtpUserName = inboxSmtpUserName;
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
	/**
	 * @return the smtpHost
	 */
	public String getSmtpHost() {
		return smtpHost;
	}
	/**
	 * @param smtpHost the smtpHost to set
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
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
	 * @return the inboxServerType
	 */
	public String getInboxServerType() {
		return inboxServerType;
	}
	/**
	 * @param inboxServerType the inboxServerType to set
	 */
	public void setInboxServerType(String inboxServerType) {
		this.inboxServerType = inboxServerType;
	}
	
	
}
