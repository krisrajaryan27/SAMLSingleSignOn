package com.talentPool.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.CommunicationsException;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.DBUtils;

/**
 * @author shivprasad
 * 
 * 
 */
public final class DBManager {
	private static String _driver;// = "com.mysql.jdbc.Driver";
	private static String _url;// =
	// "jdbc:mysql://127.0.0.1:3306/addb?user=root&password=password";
	private static String _username;
	private static String _password;
	private static int _initialConnections = 20;
	private static int _maxConnections = 100;
	private static boolean _waitIfBusy = true;
	private static DBConnectionPool _pool = null;

	static {
		try {
			init();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error initializing DBConnetion POOL", e);
		}
	}

	/** Creates a new instance of dbmanager */
	private DBManager() {
	}

	private static void init() throws ClassNotFoundException, SQLException {
		_driver = TPApplicationProperties.getProperty("db.driver");
		TPLogger.getLogger().debug("_driver=" + _driver);
		_url = TPApplicationProperties.getProperty("db.url");
		TPLogger.getLogger().debug("_url=" + _url);
		
		_username = TPApplicationProperties.getProperty("db.username");
		TPLogger.getLogger().debug("_username=" + _username);
		_username = DBUtils.decryptString(_username);
		
		_password = TPApplicationProperties.getProperty("db.password");
		TPLogger.getLogger().debug("_password=" + _password);
		_password = DBUtils.decryptString(_password);
		
		try {
			_initialConnections = Integer.parseInt(TPApplicationProperties.getProperty("db.pool.initial_connections"));
			TPLogger.getLogger().debug("_initialConnections=" + _initialConnections);
		} catch (Exception e) {
			TPLogger.getLogger().debug("initial_db_connections not configured correctly. Setting _initialConnections to 20.");
		}
		try {
			_maxConnections = Integer.parseInt(TPApplicationProperties.getProperty("db.pool.max_connections"));
			TPLogger.getLogger().debug("_maxConnections=" + _maxConnections);
		} catch (Exception e) {
			TPLogger.getLogger().debug("maximum_db_connections not configured correctly. Setting _maxConnections to 100.");
		}
		try {
			String waitIfBusy = TPApplicationProperties.getProperty("db.pool.wait_if_busy");
			if (waitIfBusy != null)
				_waitIfBusy = waitIfBusy.equalsIgnoreCase("true");
		} catch (Exception e) {
		}

		_pool = new DBConnectionPool(_driver, _url, _username, _password, _initialConnections, _maxConnections, _waitIfBusy);

	}

	public static void release(Connection con) throws SQLException {
		con.close();
		//_pool.free(con);
	}

	/**
	 * while getting connection from MYSQL there is no way to determine valid
	 * connection, unless u check with "SELCT 1"
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection con = _pool.getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.execute("SELECT 1");
			stmt.close();
		} catch (CommunicationsException e) {
			TPLogger.getLogger().error("I think connection is closed, let's reconnect");
			//con = _pool.reconnect(con);
		}
		return con;
	}

	/*public static Connection reconnect(Connection con) throws SQLException {
		return _pool.reconnect(con);
	}*/

	/**
	 * @return the _password
	 */
	public static String getDBPassword() {
		return _password;
	}
	
	/**
	 * @return the _username
	 */
	public static String getDBUsername() {
		return _username;
	}	
	
	public static String getPoolStatus() throws SQLException {
		return _pool.toString();
	}

}