/**
 * 
 */
package com.talentPool.application.manager;

import java.util.ArrayList;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;

/**
 * @author shivprasad
 * 
 */
public class ApplicationManager {

	public ArrayList<SimpleDataObject> getReleaseHistory() {
		ArrayList<SimpleDataObject> result = null;
		DBQuery dq = null;
		try {
			dq = new DBQuery("dApplicationManager_GetReleaseInfo");
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	public ArrayList<SimpleDataObject> getCustomLabels() {
		ArrayList<SimpleDataObject> result = null;
		DBQuery dq = null;
		try {
			dq = new DBQuery("dApplicationManager_GetCustomLabels");
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	public String getPluginFileName(String pluginId){
		try{
			if(pluginId.equals(CommonConstants.IE_PLUGIN)){
				return TPApplicationProperties.getProperty("plugin.file.name.ie_plugin");
			}else if(pluginId.equals(CommonConstants.FIREFOX_PLUGIN)){
				return TPApplicationProperties.getProperty("plugin.file.name.firefox_plugin");
			}else if(pluginId.equals(CommonConstants.OUTLOOK_PLUGIN)){
				return TPApplicationProperties.getProperty("plugin.file.name.outlook_plugin");
			}else if(pluginId.equals(CommonConstants.CHROME_PLUGIN)){
				return TPApplicationProperties.getProperty("plugin.file.name.chrome_plugin");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return null;
	}

	public String getProductVersion() {
		String result = null;
		DBQuery dq = null;
		try {
			dq = new DBQuery("dApplicationManager_GetProductVersion");
			result = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
}
