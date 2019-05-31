/**
 * 
 */
package com.talentPool.common.properties;

import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.DateUtils;

/**
 * @author shivprasad
 * 
 */
public class GlobalApplicationProperties {
	private static HashMap<String, String> globalProperties;

	/**
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		if (globalProperties == null) {
			fetchGlobalProperties();
		}
		return globalProperties.get(key);
	}

	/**
	 * @return
	 */
	public static HashMap<String, String> getPropertiesMap() {
		if (globalProperties == null) {
			fetchGlobalProperties();
		}
		return globalProperties;
	}
	
	public static void resetPropertiesMap(){
		globalProperties = null;
		fetchGlobalProperties();
		DateUtils.resetSystemDateTimeFormats();
	}
	/**
	 * Fetches all
	 */
	public static void fetchGlobalProperties() {
		AdminManager adminManager = new AdminManager();
		ArrayList<SimpleDataObject> results = adminManager.fetchGlobalProperties();
		try {
			globalProperties = new HashMap<String, String>();
			for (int i = 0; results != null && i < results.size(); i++) {
				SimpleDataObject sdo = results.get(i);
				String propertyName = sdo.getString("propertyName");
				String propertyValue = sdo.getString("propertyValue");
				globalProperties.put(propertyName, propertyValue);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting application properties", e);
		}
	}
	
	public static boolean isEnabled(String property){
		return GlobalConstants.ENABLED.equals(getProperty(property));
	}

}
