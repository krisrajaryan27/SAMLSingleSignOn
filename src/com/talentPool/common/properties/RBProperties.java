package com.talentPool.common.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 * 
 * This is wrapper for resource bundle. instance of this can be created at run time and used to access the specific property from the specified resource bundle
 * 
 */
public class RBProperties {
	private ResourceBundle rb = null;

	public RBProperties(String resourceBundle) {
		rb = ResourceBundle.getBundle(resourceBundle);
	}

	public String getProperty(String key) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException mre) {
			TPLogger.getLogger().debug("Resource not found for key:" + key);
			return "";
		}
	}

	public String getProperty(String key, String defaultValue) {
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
