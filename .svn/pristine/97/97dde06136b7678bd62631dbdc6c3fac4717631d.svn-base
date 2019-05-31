/**
 * 
 */
package com.talentPool.common.utils;

import javax.mail.*;

/**
 * @author shivprasad Authenticator populates the username and password from the property files. This class is used by {@link com.talentPool.common.utils.MailManager}
 */
public class MailAuthenticator extends Authenticator {
	/** Creates a new instance of MailAuthenticator */
	String userName;
	String password;

	public MailAuthenticator() {

	}

	public MailAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 
	 * @return
	 */
	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		javax.mail.PasswordAuthentication retValue;

		retValue = new PasswordAuthentication(this.userName, this.password);
		return retValue;
	}
}
