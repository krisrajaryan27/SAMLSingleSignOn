/**
 * 
 */
package com.talentPool.common.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TimeZone;

import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.timeZone.TimeZoneUtils;

/**
 * @author shivprasad
 * 
 */
public class DBQuery extends QueryFrame {
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private String timeZone;

	/**
	 * Creates a new instance of DBQuery
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @throws SQLException 
	 */

	public DBQuery(String dqName) throws SQLException {
		super(dqName);
		try {
			this.con = DBManager.getConnection();
			this.stmt = con.createStatement();
			try {
				//this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				if (MyThreadLocal.get() != null && !Utils.isBlankOrNull(MyThreadLocal.get().getTimeZone())){
					this.timeZone = TimeZoneUtils.getOffset(MyThreadLocal.get().getTimeZone());
				}else {
					this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				}
			} catch(Exception e){
				this.timeZone = "+00:00";
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}		
	}

	/**
	 * Creates a new instance of DBQuery which behaves as statement object
	 * 
	 * @param dqName
	 *            name of the dataquery entry as defined in the xml file.
	 * @param tr
	 *            The DBTransaction object, of which this query wants to be a part.
	 * @throws SQLException 
	 */

	public DBQuery(String dqName, DBTransaction tr) throws SQLException {
		super(dqName);
		try {
			this.isTransaction = true;
			this.con = tr.getConnection();
			this.stmt = con.createStatement();
			try {
				//this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				if (MyThreadLocal.get() != null && !Utils.isBlankOrNull(MyThreadLocal.get().getTimeZone())){
					this.timeZone = TimeZoneUtils.getOffset(MyThreadLocal.get().getTimeZone());
				}else {
					this.timeZone = TimeZoneUtils.getOffset(TimeZone.getDefault().getID());
				}
			} catch(Exception e){
				this.timeZone = "+00:00";
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}		
	}

	/**
	 * Returns an ArrayList of IDataObject's. The actual implemented class of the IDataObject is as specified in the dataquery xml file.
	 * 
	 * @throws SQLException
	 * @return ArrayList of IDataObject objects.
	 */
	public ArrayList getResult() throws SQLException {
		ArrayList list = new ArrayList();
		try {
			// DQMetaData dq = getDQMetaData(dqName);
			Class cls = Class.forName(this.dqMetaData.getDataObjectClassName());
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			rs = stmt.executeQuery(this.sql);
			while (rs.next()) {
				IDataObject dObj = (IDataObject) (cls.newInstance());
				setAttributes(dObj, rs, this.dqMetaData.getDbColumnMaps());
				list.add(dObj);
			}		
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (InstantiationException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (IllegalAccessException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return list;
	}

	/**
	 * Returns an IDataObject. This is useful for queries which return a single row as the result. The actual implemented class of the IDataObject is as specified in the dataquery xml file.
	 * 
	 * @throws SQLException
	 * @return single IDataObject populated from the resultset of the query.
	 */
	public IDataObject getSingleObjectResult() throws SQLException {
		IDataObject dObj = null;
		try {
			// DQMetaData dq = getDQMetaData(dqName);
			Class cls = Class.forName(this.dqMetaData.getDataObjectClassName());
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			rs = stmt.executeQuery(this.sql);
			if (rs.next()) {
				dObj = (IDataObject) (cls.newInstance());
				setAttributes(dObj, rs, this.dqMetaData.getDbColumnMaps());
			}	
			rs.close();
			stmt.close();
		} catch (ClassNotFoundException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (InstantiationException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} catch (IllegalAccessException e) {
			TPLogger.getLogger().error("Error while getting result", e);
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return dObj;
	}

	/**
	 * This method is used to get the result of a query returning a single value. The returned Object needs to be typecasted to the desired class.
	 * 
	 * @throws SQLException
	 * @return Object
	 */
	public Object getSingleSimpleObjectResult() throws SQLException {
		Object result = null;
		try {
			// DQMetaData dq = getDQMetaData(dqName);
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			rs = stmt.executeQuery(this.sql);
			if (rs.next()) {
				result = rs.getObject(1);
			}		
			rs.close();
			stmt.close();
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return result;
	}

	/**
	 * Executes any query which does not expect a result set.
	 * 
	 * @throws SQLException
	 * @see getResult(); getSingleObjectResult()
	 */
	public int execute() throws SQLException {
		int numColsAffected = -1;
		try {
			String timeZoneSQL = "set time_zone = ";
			String timeZoneValue = "'"+timeZone+"'";
			stmt.execute(timeZoneSQL+timeZoneValue);
			boolean b = stmt.execute(this.sql);
			if (b) {
				// Ignore; This means that a resultset is being returned
				// Use the other methods to get objects populated from the resultset
			} else {
				// Update.
				numColsAffected = stmt.getUpdateCount();
			}	
			stmt.close();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error in execute() in DBQuery", e);
			throw e;
		} finally {
			if (!isTransaction) {
				releaseConnection();
			}
		}
		return numColsAffected;
	}
	
	/**
	 * Method to return statement.
	 */
	public Statement getStatement() {		
		return this.stmt;
	}
	
	/**
	 * Method to return result set.
	 */
	public ResultSet getResultSet() {
		return this.rs;
	}
}
