/**
 * 
 */
package com.talentPool.user.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;

/**
 * @author shivprasad
 * 
 */
public class SingleSignOnManager {
	private static HashMap<String, String> userIdIPMap;
	private static HashMap<String, Date> userIdTimeMap;
	static {
		userIdIPMap = new HashMap<String, String>();
		userIdTimeMap = new HashMap<String, Date>();
	}

	public static boolean isValidIPRequest(String userId, String ipAddress) {
		if (GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON))) {
			if (ipAddress != null) {
				if (!ipAddress.equals(userIdIPMap.get(userId))) {
					return false;
				}
			}
		}
		return true;
	}

	public static void addUserIp(String userId, String ipAddress) {
		if (GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON))) {
			userIdIPMap.put(userId, ipAddress);
			userIdTimeMap.put(userId, Calendar.getInstance().getTime());
		}
	}

	public static void removeUserIp(String userId) {
		if (GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON))) {
			userIdIPMap.remove(userId);
			userIdTimeMap.remove(userId);
		}
	}

	public static boolean isUserLoggedInFromDifferentIP(String userId, String ipAddress) {
		if (GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON))) {
			String prevIPAddress = userIdIPMap.get(userId);
			if (prevIPAddress != null) {
				//if (!userIdTimeMap.get(userId).before(Utils.adjustDateBy(Calendar.getInstance().getTime(), Calendar.HOUR, -3))) {
				if(!prevIPAddress.equals(ipAddress)){
					return true;
				}
			}
		}
		return false;
	}
}
