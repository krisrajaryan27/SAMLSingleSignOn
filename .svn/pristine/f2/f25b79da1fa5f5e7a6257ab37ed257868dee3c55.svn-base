/**
 * 
 */
package com.talentPool.common.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.quartz.utils.ConnectionProvider;

/**
 * @author pallavi
 *
 */
public class DBConnectionProvider implements ConnectionProvider {
	private static Connection conn;
	/* (non-Javadoc)
	 * @see org.quartz.utils.ConnectionProvider#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		if(conn == null) {
			conn = DBManager.getConnection();
		} else if(conn.isClosed()) {
			DBManager.release(conn);
			conn = DBManager.getConnection();
		}
		return conn;
	}

	/* (non-Javadoc)
	 * @see org.quartz.utils.ConnectionProvider#shutdown()
	 */
	public void shutdown() throws SQLException {
		// do Nothing
	}

}
