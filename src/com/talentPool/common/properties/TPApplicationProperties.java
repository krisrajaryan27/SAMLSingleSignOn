/**
 * 
 */
package com.talentPool.common.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 * 
 */
public class TPApplicationProperties {
	private static ResourceBundle rb = null;
	
	static {
		rb = ResourceBundle.getBundle("talentpool");
	}

	/**
	 * @param key
	 * @return property value set in properties file
	 */
	public static String getProperty(String key) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException mre) {
			TPLogger.getLogger().debug("Resource not found for key:" + key);
			return "";
		}
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return property value, if not found return default value
	 */
	public static String getProperty(String key, String defaultValue) {
		String val = null;
		try {
			val = rb.getString(key);
		} catch (MissingResourceException mre) {
			TPLogger.getLogger().debug("Resource not found for key:" + key);
		}
		if (val == null) {
			val = defaultValue;
		}
		return val;
	}

}
