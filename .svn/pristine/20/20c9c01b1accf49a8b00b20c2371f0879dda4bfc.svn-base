package com.talentPool.admin.manager;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.positions.constants.PositionConfigurationConstants;

public class WebsiteScreenSettingsManager {
	public SimpleDataObject getWebsiteSettings(){
		SimpleDataObject sdo = null;
		DBQuery dq = null;
		try {
			dq = new DBQuery("dWebsiteScreenManager_GetWebsiteSettings");
			sdo = (SimpleDataObject)dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}
	
	public String getWebsitePositionsHomeHeader(){
		String positionsHomeHeader = null;
		DBQuery dq = null;
		try {
			dq = new DBQuery("dWebsiteScreenManager_GetWebsitePositionsHomeHeader");
			positionsHomeHeader = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionsHomeHeader;
	}
	
	public void updateSettings(String isShowLabels,String firstLineField, String isOther){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dWebsiteScreenManager_UpdateWebsiteSettings");
			dq.setString(1, isShowLabels);
			dq.setString(2, firstLineField);
			dq.setString(3, isOther);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateWebsitePositionsHomeHeader(String positionsHomeHeader){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dWebsiteScreenManager_UpdateWebsitePositionsHomeHeader");
			dq.setString(1, positionsHomeHeader);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public int isFiltersAvailableOnWebsite(){
		DBPreparedQuery dq = null;
		int isFiltersAvailable = 0;
		try {
			dq = new DBPreparedQuery("dWebsiteScreenManager_isFiltersAvailable");
			dq.setString(1, PositionConfigurationConstants.FIELD_IS_FILTER);
			isFiltersAvailable = dq.getIntResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return isFiltersAvailable;
	}
}