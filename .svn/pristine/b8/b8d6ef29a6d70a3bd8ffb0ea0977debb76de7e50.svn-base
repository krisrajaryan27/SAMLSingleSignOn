/**
 * 
 */
package com.talentPool.common.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.math.NumberUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 *
 */
public class DBConnectionPool {
    private ComboPooledDataSource cpds = null;
    
    public DBConnectionPool(String driver, String url, String username, String password, int initialConnections, int maxConnections, boolean waitIfBusy) throws SQLException {
    	cpds = new ComboPooledDataSource(); 
    	try {
			cpds.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			TPLogger.getLogger().error("error finding driver class for connection pool",e);
		}
    	if (initialConnections > maxConnections) {
            initialConnections = maxConnections;
        }
    	cpds.setJdbcUrl(url); 
    	cpds.setUser(username); 
    	cpds.setPassword(password);
    	cpds.setInitialPoolSize(initialConnections);
    	cpds.setMinPoolSize(initialConnections);
    	cpds.setMaxPoolSize(maxConnections);
    	cpds.setAcquireIncrement(5);
    	
    	/*
    	 * Below Parameters ensure Connection testing
    	 * 
    	*/
    	cpds.setIdleConnectionTestPeriod(7200);
    	cpds.setUnreturnedConnectionTimeout(7190);
    	cpds.setTestConnectionOnCheckin(true);
    	cpds.setPreferredTestQuery("SELECT 1");
    	cpds.setMaxIdleTimeExcessConnections(1200);
    	
    	/*
    	 * Below Parameters ensure Recovery From Database Outages
    	 * 
    	*/
    	cpds.setAcquireRetryAttempts(4);
    	cpds.setAcquireRetryDelay(1000);
    	
    	/*
    	 * 
    	 *Below parameter ensures the amount of time client will wait for getting connection to db 
    	 * 
    	 */
    	String timeout = TPApplicationProperties.getProperty("db.getConnection.timeout");
    	cpds.setCheckoutTimeout(NumberUtils.isNumber(timeout)?Integer.parseInt(timeout):0);
    	
    	// TODO : StatementPooling
    	
    	
//        this.waitIfBusy = waitIfBusy;
    	
    }
    
    public Connection getConnection() throws SQLException {
    	return cpds.getConnection();
    }
    
    public void free(Connection connection) throws SQLException  {
        connection.close();
    }
    
    public void closeConnections() throws SQLException {
		if(cpds != null){
			DataSources.destroy(cpds);
		}
	}
    
    public String toString() {
    	StringBuilder sb = new StringBuilder("");
    	if(cpds!=null){
    		try {
    			sb.append("No. of Connections: ").append(cpds.getNumConnectionsDefaultUser());
    			sb.append("\n");
        		sb.append("No. of busy Connections: ").append(cpds.getNumBusyConnectionsDefaultUser());
        		sb.append("\n");
				sb.append("No. of idle Connections: ").append(cpds.getNumIdleConnectionsDefaultUser());
				sb.append("\n");
			} catch (SQLException e) {
				sb.append("Unable to get conenctions information.");
			}
    	}
        return sb.toString();
    }
}
