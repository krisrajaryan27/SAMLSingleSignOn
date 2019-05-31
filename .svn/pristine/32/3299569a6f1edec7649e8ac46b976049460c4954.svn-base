/**
 * 
 */
package com.talentPool.user;

import com.talentPool.common.utils.Utils;
import com.talentPool.user.manager.LoginManager;


/**
 * @author pallavi
 *
 */
public class LoginConstants {
	private static String VERSION;
	
	/**
	 * @return the vERSION
	 */
	public static String getVERSION() {
		if(Utils.isBlankOrNull(VERSION)){
			VERSION = new LoginManager().getLatestVersion();
		}
		return VERSION;
	}

}
