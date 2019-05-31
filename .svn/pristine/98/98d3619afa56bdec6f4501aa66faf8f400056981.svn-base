/**
 * 
 */
package com.talentPool.reportDesign.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import com.talentPool.budget.utils.BudgetUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBManager;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.generator.ExcelReportGenerator;
import com.talentPool.reportDesign.generator.PreFormattedReportGenerator;
import com.talentPool.reportDesign.report.ReportTypes;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.user.manager.ModuleSet;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class ReportDesignManager {

	public String getReportResultData(ReportTypes rt, FilterData filterData, ReportData reportData, String reportId, String reportType, String userId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		String newFileName = null;
		String query = null;
		try{		
			ArrayList<ColumnData> columns = getReportColumnData(reportId);
			query = rt.getQuery(filterData, reportData, columns, userId, permissionSet);
			
			TPLogger.getLogger().debug(query);
			
			if(!Utils.isBlankOrNull(query)){
				Connection con = DBManager.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				
				ResultSetMetaData metaData = rs.getMetaData();				
				String sortBy = reportData.getSortBy();
				String sortColumnType = ColumnUtils.columnDBMap.get(sortBy);
				if(!Utils.isBlankOrNull(sortColumnType)){
					int rowCount = metaData.getColumnCount();
					for (int i = 0; i < rowCount; i++) {
						if(sortColumnType.contains(metaData.getColumnName(i + 1))){
							sortColumnType = metaData.getColumnTypeName(i + 1);	
						}
			        }					
				}else{
					//mean Custom Field
					sortColumnType=ReportDesignConstants.SORT_TYPE_VARCHAR;
				}
				
				reportData.setSortType(sortColumnType);
				String filePath = "";
				if(reportData.getReportFormat().equals(ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED) && !Utils.isBlankOrNull(reportData.getReportFilePath())){
					filePath = Utils.concatFilePath(DocumentConstants.documentsPath, reportData.getReportFilePath());
					PreFormattedReportGenerator preFormattedReportGenerator = new PreFormattedReportGenerator();
					newFileName = preFormattedReportGenerator.generateReport(rs, filterData, columns, reportData, filePath, permissionSet);
				}else{
					filePath = Utils.concatFilePath(ReportConstants.JRXML_FOLDER_PATH, TPLabels.getLabel("reportdesigner.report.file_name"));
					ExcelReportGenerator excelReportGenerator = new ExcelReportGenerator();
					newFileName = excelReportGenerator.generateReport(rs, filterData, columns, reportData, filePath, permissionSet);
				}
				
				
				
				rs.close();
				stmt.close();
				DBManager.release(con);
				
			}

		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return newFileName;
	}

	public void saveReportDesign(String reportType, String reportName,String reportDescription,
			String groupBy, String sortBy, String sortWith, String columns, String filters, 
			String isSharedReport, String levelPermissions, String userId, String reportFormat, 
			String reportFilePath, String sheetIndex, String rowIndex) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportDesignManager_InsertReportDesign",tran);
			dq.setString(1, reportName);
			dq.setString(2, reportType);
			dq.setString(3, groupBy);
			dq.setString(4, sortBy);
			dq.setString(5, sortWith); 
			dq.setString(6, "1"); //Not in use
			dq.setString(7, reportFormat); //view type
			dq.setString(8, userId);
			dq.setString(9, reportDescription);			
			dq.setString(10, reportFilePath);
			dq.setString(11, sheetIndex);
			dq.setString(12, rowIndex);
			dq.execute();
			
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String reportId = dq.getIdResult();
			
			
			if(!Utils.isBlankOrNull(columns)){
				String[] cols = columns.split(",");
				for (int i = 0; i < cols.length; i++) {
					dq = new DBPreparedQuery("dReportDesignManager_InsertReportColumns",tran);
					dq.setString(1, reportId);
					dq.setString(2, cols[i]);
					try{
						Integer.parseInt(cols[i]);
						dq.setString(3, "1"); //custom field	
					}catch (Exception e) {
						dq.setString(3, "0"); //non custom field
					}
					dq.setInt(4, i+1);
					dq.execute();
				}
			}
			
			if(!Utils.isBlankOrNull(filters)){
				String[] fltrs = filters.split(",");
				for (int i = 0; i < fltrs.length; i++) {
					dq = new DBPreparedQuery("dReportDesignManager_InsertReportFilters",tran);
					dq.setString(1, reportId);
					dq.setString(2, fltrs[i]);
					dq.execute();
				}
			}
			
			if(isSharedReport.equals(ReportDesignConstants.REPORT_ACCESS_SHARED)){
				String[] levelIds = levelPermissions.split(",");				
				for (int i = 0; i < levelIds.length; i++) {
					dq = new DBPreparedQuery("dAdminManager_AddLevelReport", tran);
					dq.setString(1, levelIds[i]);
					dq.setString(2, reportId);
					dq.setString(3, "1"); //custom type report
					dq.execute();
				}
			}
			tran.commit();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}
	
	public void updateReportDesign(String reportId,String reportType, String reportName,String reportDescription,
			String groupBy, String sortBy, String sortWith, String columns, String filters, String isSharedReport, 
			String levelPermissions, String userId, String reportFormat, String reportFilePath, String sheetIndex, 
			String rowIndex) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportDesignManager_updateReportDesign",tran);
			dq.setString(1, reportName);
			dq.setString(2, reportType);
			dq.setString(3, groupBy);
			dq.setString(4, sortBy);
			dq.setString(5, sortWith); 
			dq.setString(6, "1");//remove this 
			dq.setString(7, reportFormat); //view type
			dq.setString(8, reportDescription);
			dq.setString(9, reportFilePath);
			dq.setString(10, sheetIndex);
			dq.setString(11, rowIndex);
			dq.setString(12, reportId);
			dq.execute();			
			
			dq = new DBPreparedQuery("dReportDesignManager_DeleteCustomReportColumns",tran);
			dq.setString(1, reportId);
			dq.execute();
			
			if(!Utils.isBlankOrNull(columns)){
				String[] cols = columns.split(",");
				for (int i = 0; i < cols.length; i++) {
					dq = new DBPreparedQuery("dReportDesignManager_InsertReportColumns",tran);
					dq.setString(1, reportId);
					dq.setString(2, cols[i]);
					try{
						Integer.parseInt(cols[i]);
						dq.setString(3, "1"); //custom field	
					}catch (Exception e) {
						dq.setString(3, "0"); //non custom field
					}
					dq.setInt(4, i+1);
					dq.execute();
				}
			}
			
			dq = new DBPreparedQuery("dReportDesignManager_DeleteCustomReportFilters",tran);
			dq.setString(1, reportId);
			dq.execute();
			
			if(!Utils.isBlankOrNull(filters)){
				String[] fltrs = filters.split(",");
				for (int i = 0; i < fltrs.length; i++) {
					dq = new DBPreparedQuery("dReportDesignManager_InsertReportFilters",tran);
					dq.setString(1, reportId);
					dq.setString(2, fltrs[i]);
					dq.execute();
				}
			}
			
			dq = new DBPreparedQuery("dReportDesignManager_DeleteCustomReportLevels",tran);
			dq.setString(1, reportId);
			dq.execute();		
			
			if(isSharedReport.equals(ReportDesignConstants.REPORT_ACCESS_SHARED)){
				String[] levelIds = levelPermissions.split(",");				
				for (int i = 0; i < levelIds.length; i++) {
					dq = new DBPreparedQuery("dAdminManager_AddLevelReport", tran);
					dq.setString(1, levelIds[i]);
					dq.setString(2, reportId);
					dq.setString(3, "1"); //custom type report
					dq.execute();
				}
			}
			tran.commit();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}

	public ArrayList<ReportData> getAllReports(){
		DBPreparedQuery dq = null;
		ArrayList<ReportData> list = new ArrayList<ReportData>();
		try {
			dq = new DBPreparedQuery("dReportDesignManager_GetAllReports");
			String excludedReportTypes = "";
			if(!ModuleSet.isMODULE_BUDGET()|| !BudgetUtils.isBudgetModuleActive()){
				excludedReportTypes = ReportDesignConstants.REPORT_TYPE_BUDGET;
			}
			dq.setString(1, excludedReportTypes);
			list = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return list;
	}

	public ArrayList<ReportData> getCustomReportsForUser(String userId){
		DBPreparedQuery dq = null;
		ArrayList<ReportData> list = new ArrayList<ReportData>();
		try {
			dq = new DBPreparedQuery("dReportDesignManager_GetCustomReportsForUser");
			dq.setString(1, userId);
			dq.setString(2, userId);
			dq.setString(3, userId);
			String excludedReportTypes = "";
			if(!ModuleSet.isMODULE_BUDGET()|| !BudgetUtils.isBudgetModuleActive()){
				excludedReportTypes = ReportDesignConstants.REPORT_TYPE_BUDGET;
			}
			dq.setString(4, excludedReportTypes);
			list = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return list;
	}
	
	public ReportData getReportData(String reportId) {
		DBPreparedQuery dq = null;
		ReportData data = null;
		try {
			dq = new DBPreparedQuery("dReportDesignManager_GetReportData");
			dq.setString(1, reportId);
			data = (ReportData)dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return data;
	}

	public void deleteCustomReport(String reportId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dReportDesignManager_DeleteCustomReportFilters",tran);
			dq.setString(1, reportId);
			dq.execute();
			
			dq = new DBPreparedQuery("dReportDesignManager_DeleteCustomReportColumns",tran);
			dq.setString(1, reportId);
			dq.execute();
			
			dq = new DBPreparedQuery("dReportDesignManager_DeleteCustomReportLevels",tran);
			dq.setString(1, reportId);
			dq.execute();			
			
			dq = new DBPreparedQuery("dReportDesignManager_DeleteCustomReport",tran);
			dq.setString(1, reportId);
			dq.execute();
			
			tran.commit();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}				
	}
	
	public ArrayList<ColumnData> getReportColumnData(String reportId) {
		DBPreparedQuery dq = null;
		ArrayList<ColumnData> data = new ArrayList<ColumnData>();
		try {
			dq = new DBPreparedQuery("dReportDesignManager_GetReportColumnData");
			dq.setString(1, reportId);
			data = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}	
		return data;
	}
}
