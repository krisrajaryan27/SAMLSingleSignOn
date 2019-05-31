/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.service.impl;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRDataSource;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.EncryptionUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CustomReportColumnConstants;
import com.talentPool.customReports.constants.CustomReportConstants;
import com.talentPool.customReports.dataobject.ApplicantMasterData;
import com.talentPool.customReports.dataobject.TPMapCollectionDataSource;
import com.talentPool.customReports.dataobject.TPResultSetDataSource;
import com.talentPool.customReports.jaxb.CandNamesSpecifications;
import com.talentPool.customReports.jaxb.Filters;
import com.talentPool.customReports.manager.CandidateMasterTableManager;
import com.talentPool.customReports.queryBuilder.QueryBuilder;
import com.talentPool.customReports.service.ICRDataSourceService;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author PraveenK
 * @since  Nov 10, 2011
 */
public class QueryBuilderDataSourceService implements ICRDataSourceService {
	
	private String _delimiter = "@@";

	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.IDRDataSourceService#getDataSorce()
	 */
	public JRDataSource getDataSorce(Connection con, String queryFilePath, Filters filters, String userId, 
			PermissionSet permissionSet, boolean isPositionPermissionCheckRequired) throws SQLException {
		JRDataSource jsd 	= null;
		ResultSet rs = null;
		Collection<Map<String,?>> data = new ArrayList<Map<String,?>>();
		try {
			rs = getReportData(con, queryFilePath, filters, userId, permissionSet, isPositionPermissionCheckRequired); 
//			jsd = new TPResultSetDataSource(rs);
			//rs.close();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			Map<String,Object> record = null;
			while(rs.next()){
				record = new HashMap<String,Object>();
				for (int i = 1; i <= columnCount; i++) {
					String key = metaData.getColumnLabel(i); 
					Object value=null;
					if(key.equals("P26")){
						String loc=getLocations(rs.getObject(i));
						value=loc;
					}
					else if(key.equals("S15")){
						//if S15 is negative then fire query to find out inprocess candiadte for positionId and step id
						value= rs.getObject(i);
						Object stepName=record.get("S09");
						Object positionCode=record.get("P01");
						if(stepName!=null && positionCode!=null){
						int inprocess=getNumberOfCandidateInprocess(stepName,positionCode);
						value=new Integer(inprocess);
						}
						else{
							value= rs.getObject(i);
						}
					}
					else{
						value= rs.getObject(i);
					}
					
					if(value!= null && DBConstants.getCustomReportColumnKeyMap().containsKey(key)){
						value = EncryptionUtils.decrypt((String) value, DBConstants.columnKeyMap.get(DBConstants.getCustomReportColumnKeyMap().get(key)));
					}
					record.put(key, value);
				}
				data.add(record);
			}
			rs.close();
			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}catch(Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return new TPMapCollectionDataSource(data) ;
	}
	
	public int getNumberOfCandidateInprocess(Object obj,Object obj2){
		int loc=0;
		try{
		if(obj!=null&&obj2!=null){
			String stepName=obj.toString().trim();
			String positionCode=obj2.toString().trim();
			int stepId=getStepIdForStepName(stepName,positionCode);
			if(stepId==0){
				return loc;
			}
			else{
			
			String PositionStepId=""+stepId;
			PositionStepId=PositionStepId.trim();
			loc=getNoOfCandidatesInProcessForStep(PositionStepId);
			}
			
		}
		}
		catch(Exception e){
			TPLogger.getLogger().info(e);
		}
		return loc;
	}
	
	public int getStepIdForStepName(String stepName,String positionCode) throws SQLException, NoResultFoundException {
		int positionStepId = 0;
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_GetStepIdForStepName");
			dq.setString(1, stepName);
			dq.setString(2, positionCode);
			positionStepId = dq.getIntResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return positionStepId;
	}
	
	public int getNoOfCandidatesInProcessForStep(String stepId) {
		DBPreparedQuery dq = null;
		int inProcessCandidates = 0;
		try {
			dq = new DBPreparedQuery("dPositionManager_GetNoOfCandidatesInProcessForStepCustom");
			dq.setString(1, stepId);
			inProcessCandidates = dq.getIntResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return inProcessCandidates;
	}
	public String getLocations(Object obj) {
		String loc=null;
		if(obj!=null){
			String str=obj.toString();
			String[] locs=str.split(",");
			List<String> list=new ArrayList<String>();
			for(String s:locs){
				list.add(s);
			}
			Set<String> set=new HashSet<String>();
			set.addAll(list);
			list.clear();
			list.addAll(set);
			StringBuilder sbd=new StringBuilder();
			int listSize=0;
			for(int i=0;i<list.size();i++){
				sbd.append(list.get(i));
				if(i<list.size()-2){
				sbd.append(",");
				}
			}
			/*for(String s:list){
				sbd.append(s);
				sbd.append(",");
			}*/
			loc=sbd.toString();
			
		}
		return loc;
	}
	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.IDRDataSourceService#getInactiveDataSorce()
	 */
	@Override
	public JRDataSource getInactiveDataSorce(Connection con, String queryFilePath, Filters filters, String userId, PermissionSet permissionSet) throws SQLException {
		JRDataSource jsd 	= null;
		ResultSet rs = null;
		try {
			rs = getInactiveReportData(con, queryFilePath, filters, userId, permissionSet); 
			jsd = new TPResultSetDataSource(rs);
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}
		return jsd;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.IDRDataSourceService#getDataSorce()
	 */
	@Override
	public JRDataSource getDataSorce(Connection con, String queryFilePath, Filters filters, CandNamesSpecifications cns, String userId, PermissionSet permissionSet) throws SQLException {
		Collection<Map<String,?>> data = new ArrayList<Map<String,?>>();
		Map<String, ApplicantMasterData> aDataMap = null;
		ResultSet rs = null;
		CandidateMasterTableManager cmtm = new CandidateMasterTableManager();
		try {
			Map<String,Object> record = null;
			rs = getReportData(con, queryFilePath, filters, userId, permissionSet, true);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			String[] attributesLst = null;
			
			if(Utils.isBlankOrNull(cns.getStepsToShowNamesAndAttributes())){
				aDataMap = cmtm.getApplicantNameMap();
			}else{
				attributesLst = cns.getExtraCandidateAttributes().split(DEFAULT_DELIMITER); 
				aDataMap = cmtm.getApplicantNameMapWithAttributes();
			}
			
			
			while (rs.next()) {
				record = new HashMap<String,Object>();
				for (int i = 1; i <= columnCount; i++) {
					String key = metaData.getColumnLabel(i); 
					Object value = rs.getObject(i);
					if(isMeasureField(key)){
						// TODO: remove hardcoded C_ from here
						String valueStr = (String) value;
						if(!Utils.isBlankOrNull(valueStr)){
							if(valueStr.startsWith("C_")){
								value = convertToNames(valueStr.substring(2), rs.getString("position_id"), aDataMap);	
							}else if(valueStr.startsWith("CA_")){
								value = convertToNamesAndAttributes(valueStr.substring(3), rs.getString("position_id"), aDataMap, attributesLst);	
							}	
						}
					}
					record.put(key, value);
				}
				data.add(record);
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}
		return new TPMapCollectionDataSource(data);
	}
	
	private ResultSet getReportData(Connection con, String queryFilePath, Filters filters, String userId, 
			PermissionSet permissionSet, boolean isPositionPermissionCheckRequired) throws SQLException {
		SQLQueryBuilderService builderService = new SQLQueryBuilderService();
		File file 			= new File(queryFilePath);
		String contents = builderService.getFileContents(file);
		ResultSet rs = null;
		try {
			QueryBuilder qb = new QueryBuilder();
			String[] dynamicParams = qb.getWhereClause(filters, userId, permissionSet);
			if(isPositionPermissionCheckRequired) {
				dynamicParams[0] += QueryBuilder.buildPermissionClause(userId, permissionSet);
			}
			String substQuery = getSubstitutedQuery(contents, dynamicParams);
			rs = builderService.getQueryResultSet(con, substQuery);
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}
		return rs;
	}
	
	private ResultSet getInactiveReportData(Connection con, String queryFilePath, Filters filters, String userId, PermissionSet permissionSet) throws SQLException{
		SQLQueryBuilderService builderService = new SQLQueryBuilderService();
		File file 			= new File(queryFilePath);
		String contents = builderService.getFileContents(file);
		ResultSet rs = null;
		try {
			QueryBuilder qb = new QueryBuilder();
			String[] dynamicParams = qb.getWhereClauseForInactivePositions(filters, userId, permissionSet);
			String substQuery = getSubstitutedQuery(contents, dynamicParams);
			rs = builderService.getQueryResultSet(con, substQuery);
			//rs.close();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}
		return rs;
	}
	
	
	/* (non-Javadoc)
	 * @see com.talentPool.poolReports.service.IDRDataSourceService#getDataSorce()
	 */
	@Override
	public JRDataSource getEmptyDataSorce(Connection con) throws SQLException {
		SQLQueryBuilderService builderService = new SQLQueryBuilderService();
		JRDataSource jsd 	= null;
		ResultSet rs = null;
		try {
			rs = builderService.getQueryResultSet(con, "SELECT ''");
			jsd = new TPResultSetDataSource(rs);
			//rs.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			throw e;
		}
		return jsd;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.service.ICRDataSourceService#getEmptyDataSorce()
	 */
	public JRDataSource getEmptyDataSorce(Connection con, CandNamesSpecifications cns) {
		Collection<Map<String,?>> data = new ArrayList<Map<String,?>>();
		Map<String,String> record = new HashMap<String,String>();
		data.add(record);
		return new TPMapCollectionDataSource(data);
	}
	
	/*
	 * Copied from QueryFrame
	 * @link QueryFrame
	 * */
	private String getSubstitutedQuery(String query, String[] dynamicParams) {
		int queryLength = query.length();
		StringBuffer sb = new StringBuffer();
		int delimiterLength = _delimiter.length();
		int index = query.indexOf(_delimiter);
		int lastIndex = 0;
		int paramIndex = 0;
		while (index != -1) {
			sb.append(query.substring(lastIndex, index));
			sb.append(dynamicParams[paramIndex]);
			lastIndex = index + delimiterLength;
			if (lastIndex < queryLength) {
				index = query.indexOf(_delimiter, lastIndex);
			} else {
				break;
			}
			paramIndex++;
		}
		if (lastIndex < queryLength) {
			sb.append(query.substring(lastIndex));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 */
	private String convertToNames(String ids, String positionId, Map<String, ApplicantMasterData> aDataMap) {
		StringBuilder convertedName = new StringBuilder();;
		if(!Utils.isBlankOrNull(ids)){
			ApplicantMasterData aData = null;
			boolean first = true;
			String[] appIds = ids.split(DEFAULT_DELIMITER);
			int noOfAppIds = Math.min(CustomReportConstants.MAX_CANDIDATE_NAMES_IN_REPORT, appIds.length);
			for (int i = 0; i < noOfAppIds; i++) {
				String appId = appIds[i];
				aData = aDataMap.get(appId);
				if(aData!=null){
					if(!first){
						convertedName.append(DEFAULT_DELIMITER).append("\n");
					}else {
						first = false;
					}
					convertedName.append(aData.getApplicantName());
				}
			}
			if(noOfAppIds!=appIds.length){
				convertedName.append(TPLabels.getLabel("custom_report.message.more_candidates_than_limit","common.candidates"));	
			}
		}
		return convertedName.toString();
	}
	
	private String convertToNamesAndAttributes(String ids, String positionId, Map<String, ApplicantMasterData> aDataMap, String[] attributesLst) {
		StringBuilder convertedName = new StringBuilder();
		if(!Utils.isBlankOrNull(ids)){
			ApplicantMasterData aData = null;
			boolean first = true;
			String[] appIds = ids.split(DEFAULT_DELIMITER);
			int noOfAppIds = Math.min(CustomReportConstants.MAX_CANDIDATE_NAMES_IN_REPORT, appIds.length);
			for (int i = 0; i < noOfAppIds; i++) {
				String appId = appIds[i];
				aData = aDataMap.get(appId);
				if(aData!=null){
					if(!first){
						convertedName.append(DEFAULT_DELIMITER).append(" ");
					}else {
						first = false;
					}
					convertedName.append(aData.getApplicantName());
					if(positionId.equals(aData.getApplicantPositionId())){
						convertedName.append(" [");
						int lstSize = attributesLst.length;
						for (int j = 0; j < lstSize; j++) {
							String attribute = attributesLst[j];
							if(CustomReportColumnConstants.SOURCE.equals(attribute)){
								convertedName.append(Utils.isBlankOrNull(aData.getSourceTitle())?"NA":aData.getSourceTitle());
							}else if(CustomReportColumnConstants.APPLICANT_CURRENT_STEP_NAME.equals(attribute)){
								convertedName.append(Utils.isBlankOrNull(aData.getPositionStepTitle())?"NA":aData.getPositionStepTitle());
							}else if(CustomReportColumnConstants.STATUS_MESSAGE.equals(attribute)){
								convertedName.append(Utils.isBlankOrNull(aData.getStatusMessage())?"NA":aData.getStatusMessage());
							}else if(CustomReportColumnConstants.APPLICANT_DATE_JOINED.equals(attribute)){
								convertedName.append(aData.getApplicantDateJoined()==null?"NA":aData.getApplicantDateJoined());									
							}
							if(j != lstSize-1)
								convertedName.append(DEFAULT_DELIMITER).append(" ");
						}
						convertedName.append(" ]");	
					}else {
						convertedName.append(" [").append(TPLabels.getLabel("common.rejected")).append("]");
					}
					convertedName.append("\n");
				}
			}
			if(noOfAppIds!=appIds.length){
				convertedName.append(TPLabels.getLabel("custom_report.message.more_candidates_than_limit","common.candidates"));	
			}
		}
		return convertedName.toString();
	}
	
	/**
	 * Checks if the key matches to any measure 
	 * @param key
	 * @return true if the param key is the key of measure field
	 * false if the param key is not key of measure field
	 */
	private boolean isMeasureField(String key){
		return CustomReportColumnConstants.EXISTING.equals(key)
		|| CustomReportColumnConstants.ADDED.equals(key)
		|| CustomReportColumnConstants.SELECTED.equals(key)
		|| CustomReportColumnConstants.REJECTED.equals(key)
		|| CustomReportColumnConstants.INPROCESS.equals(key)
		|| CustomReportColumnConstants.STATUS_AS_OF_DATE.equals(key);
	}
}
