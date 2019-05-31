package com.talentPool.employeeservice.manager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.employeeservice.dataobject.EPortalSettings;

public class EmployeeAdminManager {
	
	/**
	 * Fetches all Employee portal Settings
	 * @return ePortalSettings
	 */
	public EPortalSettings getEPortalSettings()  {	
		DBPreparedQuery dq = null;
		List<SimpleDataObject> results = null;
		EPortalSettings ePortalSettings = null;
		Map<String,String> ePortalSettingsMap = null;
		try {
			dq = new DBPreparedQuery("dAdminManager_FetchEmployeePortalSettings");
			results = dq.getResult();
			if(results!=null){
				ePortalSettings		= new EPortalSettings();
				ePortalSettingsMap 	= new HashMap<String, String>();
				for (SimpleDataObject sdo : results) {
					ePortalSettingsMap.put(sdo.getString("propertyName"), sdo.getString("propertyValue"));
				}
				ePortalSettings.setEPortalSettingsMap(ePortalSettingsMap);				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return ePortalSettings;
	}
	
	/**
	 * Updates all the property values present in employeePortalProperties Map 
	 * @param employeePortalProperties
	 * @param updatedBy
	 * @return
	 */
	public boolean updateEmpoyeePortalProperties(Map<String,String> employeePortalProperties,String updatedBy){
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if(employeePortalProperties!=null){
				for(Map.Entry<String, String> entry : employeePortalProperties.entrySet()){
					updateApplicationProperty(entry.getKey(), entry.getValue(), updatedBy, tran);
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
		} finally {
			if (tran != null) {
				tran.release();
			}
		}
		return true;
	}
	
	public void updateApplicationProperty(String propertyName, String propertyValue,String updatedBy, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran == null) {
				dq = new DBPreparedQuery("dAdminManager_UpdateEmployeePortalSettings");
			} else {
				dq = new DBPreparedQuery("dAdminManager_UpdateEmployeePortalSettings", tran);
			}
			dq.setString(1, propertyValue);
			dq.setString(2, updatedBy);
			dq.setString(3, propertyName);
			dq.execute();
		} finally {
			if(tran == null) {
				dq.releaseConnection();
			}
		}
	}
	
	/**
	 * @return
	 */
	public String getHrTeamEmailId(){
		String hrTeamEmailId = "";
		try {
			hrTeamEmailId = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_EMPLOYEES_HRGROUP_EMAIL_ID);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return hrTeamEmailId;
	}
	
}