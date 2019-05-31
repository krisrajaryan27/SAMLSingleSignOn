package com.talentPool.otherApplications.db;

import java.sql.SQLException;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;

public class OtherDBConnection {

	public Integer insertDataIntoDB(String dQuery) {
		Integer numberOfColRet = 0;
		OtherDBQueryManager dq = null;
		try {
			dq = new OtherDBQueryManager(dQuery);
			numberOfColRet = dq.execute();	
		} catch (SQLException sqle) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,sqle);
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}finally{
			dq.releaseConnection();
		}
		return numberOfColRet;
	}

	public List<DBDataObject> readDataFromDB(String dQuery) {
		List<DBDataObject> arr = null;
		OtherDBQueryManager dq = null;
		try {
			dq = new OtherDBQueryManager(dQuery);
			arr = dq.getResultPreparedStatement(new String[]{"TP_APPLICANTID","EMPLOYEENO"});
		} catch (SQLException sqle) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,sqle);
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}finally{
			dq.releaseConnection();
		}
		return arr;
	}
	
	public Integer updateDataOfDB(String dQuery) {
		Integer numberOfColRet = 0;
		OtherDBQueryManager dq = null;
		try {
			dq = new OtherDBQueryManager(dQuery);
			numberOfColRet = dq.execute();
		} catch (SQLException sqle) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,sqle);
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}finally{
			dq.releaseConnection();
		}
		return numberOfColRet;
	}	
}