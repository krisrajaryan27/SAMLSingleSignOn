package com.talentPool.customReports.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.exception.QueryBuilderException;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.queryBuilder.IQueryBuilder;
import com.talentPool.customReports.queryBuilder.factory.QueryBulderFactory;
import com.talentPool.customReports.service.ICRQueryBuilderService;
import com.talentPool.customReports.service.ICustomReportService;

/**
 * Builds SQL query for given report type
 * @author PraveenK
 * @since  Nov 7, 2011
 */
public class SQLQueryBuilderService implements ICRQueryBuilderService {
	
	private ICustomReportService _customReportService;
	
	/**
	 *  Injected though DI
	 * @param reportTemplateService the reportTemplateService to set
	 */
	public void setCustomReportService(
			ICustomReportService reportTemplateService) {
		this._customReportService = reportTemplateService;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.ICRQueryBuilderService#saveReportDataQuery(com.talentPool.poolReports.jaxb.ReportType)
	 */
	@Override
	public void saveReportDataQuery(CustomReport customReport, String filePath) throws Exception {
		String query = buildQuery(customReport, false);
		saveQueryInFile(query, filePath);
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.ICRQueryBuilderService#saveReportDataQuery(com.talentPool.poolReports.jaxb.ReportType)
	 */
	@Override
	public void saveInactiveReportDataQuery(CustomReport customReport, String filePath) throws Exception {
		String query = buildQuery(customReport, true);
		saveQueryInFile(query, filePath);
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.ICRQueryBuilderService#saveReportDataQuery(com.talentPool.poolReports.jaxb.ReportType)
	 */
	@Override
	public String buildQuery(final CustomReport customReport, boolean showInactivePoistions) throws QueryBuilderException {
		StringBuilder query 	= null;
		try {
			Map<String,CRColumn> columnsMap 	= _customReportService.getReportColumnsMap();
			Map<Long,CRTableType> tableTypeMap 	= _customReportService.getCRTableTypeMap();
			IQueryBuilder iqb		= QueryBulderFactory.getQueryBuilder(customReport, columnsMap, tableTypeMap, showInactivePoistions);
			
			query = iqb.build(customReport);
		
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return query.toString();
	}

	/**
	 * @param query
	 */
	protected void saveQueryInFile(String query,String filePath) {
		// TODO save report query
		BufferedWriter bufferedWriter = null;
		try {
			File file = new File(filePath);
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(query);
		} catch (FileNotFoundException ex) {
            TPLogger.getLogger().error(GlobalConstants.ERROR,ex);
        } catch (IOException ex) {
        	TPLogger.getLogger().error(GlobalConstants.ERROR,ex);
        }finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
            	TPLogger.getLogger().error(GlobalConstants.ERROR,ex);
            }
        }
	}

	public String getFileContents(File file) {
		StringBuilder contents = new StringBuilder();
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			try {
				String line = null; // not declared within while loop
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return contents.toString();
	}
	
	public ResultSet getQueryResultSet(Connection con, String query) throws SQLException {
		ResultSet rs = null;
		try {
			PreparedStatement stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error in executing substituted query: "+query, sqle);
			throw sqle;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}	
		return rs;
	}
	
	public static void main(String[] agrs) {
		SQLQueryBuilderService builderService = new SQLQueryBuilderService();
		String path = TPApplicationProperties.getProperty("application.path");
		File file = new File(Utils.concatFilePath(path,	"reportxml//test.sql"));
		String contents = builderService.getFileContents(file);
		ResultSet rs = null;
		try {
	//		rs = builderService.getQueryResultSet(rs, contents);
			System.out.println(rs);
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
	
}

