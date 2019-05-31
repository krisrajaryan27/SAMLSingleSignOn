/**
 * 
 */
package com.talentPool.customReports.service;

import java.sql.Connection;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRDataSource;

import com.talentPool.customReports.jaxb.CandNamesSpecifications;
import com.talentPool.customReports.jaxb.Filters;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author PraveenK
 * @since  Nov 10, 2011
 */
public interface ICRDataSourceService {
	
	/**
	 * Returns data
	 * @param filePath
	 * @param dynamicParams
	 * @return
	 * @throws SQLException
	 */
	JRDataSource getDataSorce(Connection con, String queryFilePath, Filters filters, CandNamesSpecifications cns, String userId, PermissionSet permissionSet) throws SQLException;
	
	/**
	 * @param queryFilePath
	 * @param filters
	 * @return
	 * @throws SQLException
	 */
	JRDataSource getDataSorce(Connection con, String queryFilePath, Filters filters, String userId, PermissionSet permissionSet, boolean isCheckPositionPermission) throws SQLException;
	
	/**
	 * @param queryFilePath
	 * @param filters
	 * @return
	 * @throws SQLException
	 */
	JRDataSource getInactiveDataSorce(Connection con, String queryFilePath, Filters filters, String userId, PermissionSet permissionSet) throws SQLException;
	
	/**
	 * As Cross tab cursor moves one record in start without using it to display report 
	 * we add empty data source for cursor movement
	 * @return emptyDataSource
	 */
	JRDataSource getEmptyDataSorce(Connection con) throws SQLException;
	
	/**
	 * As Cross tab cursor moves one record in start without using it to display report 
	 * we add empty data source for cursor movement
	 * @return emptyDataSource
	 */
	//JRDataSource getEmptyDataSorce(Connection con, CandNamesSpecifications cns);
}
