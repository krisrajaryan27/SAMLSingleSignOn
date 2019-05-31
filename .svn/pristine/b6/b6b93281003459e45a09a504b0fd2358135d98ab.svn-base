/**
 * 
 */
package com.talentPool.customReports.manager;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.customReports.dataobject.CustomReportData;

/**
 * @author Sachin
 *
 */
public class CustomReportManager {

	public ArrayList<CustomReportData> getAllCustomReports(){
		DBPreparedQuery dq = null;
		ArrayList<CustomReportData> list = new ArrayList<CustomReportData>();
		try {
			dq = new DBPreparedQuery("dCustomReportManager_GetAllCustomReports");
			list = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return list;
	}
	
}
