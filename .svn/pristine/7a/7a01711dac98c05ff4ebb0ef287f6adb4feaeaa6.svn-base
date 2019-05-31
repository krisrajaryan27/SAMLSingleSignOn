/**
 * 
 */
package com.talentPool.otherApplications.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;

/**
 * @author Shantanu
 *
 */
public class OtherDBQueryManager {	
	
	Connection con;
	String sql;
	private PreparedStatement preparedStatement = null;
	private CallableStatement callableStatement = null;
	private Statement statement; 
	
	private ResultSet rs = null;
	
	public OtherDBQueryManager(String dQuery) {		
		try {		
			this.sql = dQuery;
			this.con = OtherDBConnectionManager.getConnection();
			this.preparedStatement = con.prepareStatement(this.sql);
			this.callableStatement= con.prepareCall(this.sql);
			this.statement = con.createStatement();
		} catch (SQLException e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
	}
	
	public List<String> getResultStatement() throws SQLException {
		List<String> list = new ArrayList<String>();
		try {			
			rs = statement.executeQuery(this.sql);
			while (rs.next()) {
				list.add(rs.getObject("name").toString());
			}			
			rs.close();
			statement.close();
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		} 
		return list;
	}
	
	public List<String> getResultCallableStatement() throws SQLException {
		List<String> list = new ArrayList<String>();
		try {			
			rs = callableStatement.executeQuery();
			while (rs.next()) {
				list.add(rs.getObject("name").toString());
			}
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}finally{
			rs.close();
			callableStatement.close();
		}
		return list;
	}
	
	public List<DBDataObject> getResultPreparedStatement(String[] columnNames) throws SQLException {
		List<DBDataObject> list = new ArrayList<DBDataObject>();
		try {			
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DBDataObject dbdo = new DBDataObject();			
				for (String columnName : columnNames) {
					dbdo.setAttribute(columnName, rs.getObject(columnName).toString());
				}
				list.add(dbdo);				
			}			
			rs.close();
			preparedStatement.close();
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}		
		return list;
	}
	
	public int execute() throws SQLException {
		int numColsAffected = -1;
		try {
			boolean b = statement.execute(this.sql);
			if (b) {
				// Ignore; This means that a resultset is being returned
				// Use the other methods to get objects populated from the resultset
			} else {
				// Update.
				numColsAffected = statement.getUpdateCount();
			}	
			
		} catch (SQLException sqle) {			
			sqle.printStackTrace();
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		} finally {
			statement.close();
		}
		return numColsAffected;
	}
	
	
	/**
	 * closes all the open cursors and release the connection back to connection pool. 
	 * Call this method at the end when it is not a transaction
	 */
	public void releaseConnection() {
		closeOpenCursors();
		if (con != null) {
			try {
				OtherDBConnectionManager.release(con);
				this.con = null;
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while closing connection", e);
			}
		}
	}
	
	/**
	 * close the resultset and statement object opened for this query
	 */
	public void closeOpenCursors() {
		if (this.getResultSet() != null) {
			try {
				this.getResultSet().close();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while closing result set", e);
			}
		}
		if (this.getStatement() != null) {
			try {
				this.getStatement().close();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while closing statement", e);
			}
		}
	}
	
	/**
	 * Method to return prepared statement.
	 */
	public Statement getStatement() {		
		return this.preparedStatement;
	}
	
	
	/**
	 * Method to return result set.
	 */
	public ResultSet getResultSet() {
		return this.rs;
	}
	
}