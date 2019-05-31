/**
 * 
 */
package com.talentPool.otherApplications.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author Shantanu
 *
 */
public class OtherApplicationProperties {
	
private static ResourceBundle rbLookup = null;
	
	static {
		rbLookup = ResourceBundle.getBundle("OtherApplication");
	}
	
	public static String getProperty(String key){
		try{
			return rbLookup.getString(key);
		}catch (MissingResourceException e) {
			TPLogger.getLogger().error("Resource not found for key", e) ;
			return "";
		}
	}
	
	public static String getProperty(String key, String defaultValue) {
		String val = null;
		try {
			val = rbLookup.getString(key);
		} catch (MissingResourceException mre) {
			TPLogger.getLogger().debug("Resource not found for key:" + key);
		}
		if (val == null) {
			val = defaultValue;
		}
		return val;
	}
}