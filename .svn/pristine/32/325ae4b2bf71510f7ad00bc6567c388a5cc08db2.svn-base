package com.talentPool.otherApplications.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.otherApplications.properties.OtherApplicationProperties;


public final class OtherDBConnectionManager {

	private static String _driver;// = "com.mysql.jdbc.Driver";
	private static String _url;// =
	private static String _username;
	private static String _password;
	private static int _initialConnections = 20;
	private static int _maxConnections = 10;
	private static boolean _waitIfBusy = true;
	private static OtherDBConnectionPool _pool = null;

	static {
		try {
			init();
		} catch (Exception e) {
			//TPLogger.getLogger().fatal("Error initializing DBConnetion POOL", e);
		}
	}

	/** Creates a new instance of dbmanager */
	private OtherDBConnectionManager() {
	}

	private static void init() throws ClassNotFoundException, SQLException {
		//_driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		_driver = OtherApplicationProperties.getProperty("other.db.driver").trim();
		TPLogger.getLogger().debug("_driver=" + _driver);
		
		//_url = "jdbc:sqlserver://localhost:1433;DatabaseName=talentpool_1110";
		_url = OtherApplicationProperties.getProperty("other.db.url").trim();
		TPLogger.getLogger().debug("_url=" + _url);
		
		//_username = "sa";
		_username = OtherApplicationProperties.getProperty("other.db.username");
		TPLogger.getLogger().debug("_username=" + _username);
		
		//_password = "N!tm@n";
		_password =  OtherApplicationProperties.getProperty("other.db.password");
		TPLogger.getLogger().debug("_password=" + _password);
		
		try {
			//_initialConnections = 20;
			_initialConnections = Integer.parseInt(OtherApplicationProperties.getProperty("other.db.pool.initial_connections").trim());
			TPLogger.getLogger().debug("_initialConnections=" + _initialConnections);
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);						
		}
		try {
			//_maxConnections = 10;
			_maxConnections = Integer.parseInt(OtherApplicationProperties.getProperty("other.db.pool.max_connections").trim());
			TPLogger.getLogger().debug("_maxConnections=" + _maxConnections);
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);			
		}
		try {
			String waitIfBusy = OtherApplicationProperties.getProperty("other.db.pool.wait_if_busy");
			if (waitIfBusy != null)
				_waitIfBusy = waitIfBusy.equalsIgnoreCase("true");
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		
		_pool = new OtherDBConnectionPool(_driver, _url, _username, _password, _initialConnections, _maxConnections, _waitIfBusy);

	}

	public static void release(Connection con) throws SQLException {
		_pool.free(con);
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
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
			//TPLogger.getLogger().error("I think connection is closed, let's reconnect");
			con = _pool.reconnect(con);
		}
		return con;
	}

	public static Connection reconnect(Connection con) throws SQLException {
		return _pool.reconnect(con);
	}

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
}