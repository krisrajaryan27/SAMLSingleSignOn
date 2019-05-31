/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.common.db;

import java.sql.SQLException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.Exception.NoResultFoundException;

/**
 * @author PraveenK
 * @since  May 15, 2012
 */
public class CustomIncrementCounter {

	/**
	 * Current value of counterVariable is fetched
	 * @param counterVariable
	 * @throws SQLException
	 */
	public Long getCurrentCounter(String counterVariable, DBTransaction tran) {		
		DBPreparedQuery dq = null;
		Long val = 0l;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dCustomIncrementCounter_getCurrentCounter", tran);
			} else {
				dq = new DBPreparedQuery("dCustomIncrementCounter_getCurrentCounter");
			}
			dq.setString(1, counterVariable);
			val = dq.getLongResult();
		} catch(SQLException | NoResultFoundException e){
			return 0l;
		} finally {
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}	
		}
		return val;
	}
	
	/**
	 * @param counterVariable
	 * @param tran
	 * @throws SQLException
	 */
	public void incrementCounterVariable(String counterVariable, DBTransaction tran) throws SQLException {		
		DBPreparedQuery dq = null;
		try {
			if(tran!=null)
				dq = new DBPreparedQuery("dCustomIncrementCounter_incrementCounterVariable", tran);
			else
				dq = new DBPreparedQuery("dCustomIncrementCounter_incrementCounterVariable");
			
			dq.setString(1, counterVariable);
			dq.execute();
		} catch(SQLException e){
			TPLogger.getLogger().error("Unable to increment counter for variable: ", e);
			throw e;
		} finally {
			if(dq != null) {
				if(tran != null) {
					dq.closeOpenCursors();
				} else {
					dq.releaseConnection();
				}
			}		
		}
	}
}
