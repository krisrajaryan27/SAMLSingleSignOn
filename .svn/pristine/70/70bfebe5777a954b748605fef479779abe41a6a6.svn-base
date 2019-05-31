/**
 * 
 */
package com.talentPool.customReports.queryBuilder.impl;

import static com.talentPool.customReports.constants.CRColumnValueConstants.COLUMN_VALUE_TYPE_SUM;
import static com.talentPool.customReports.djhelper.constants.DJHelperConstants.ORDER_BY_FIELD;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.RegexUtils;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.exception.QueryBuilderException;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.customReports.queryBuilder.IQueryBuilder;
import com.talentPool.customReports.queryBuilder.constants.QueryBuilderConstants;

/**
 * @author PraveenK
 * @since  Dec 18, 2011
 */
public abstract class AbstractQueryBuilder implements IQueryBuilder, QueryBuilderConstants {
	
	protected Map<String,CRColumn> columnsMap = null;
	protected Map<Long,CRTableType> tableTypeMap = null;
	protected StringBuilder query = null;
	
	/**
	 * 
	 */
	public AbstractQueryBuilder(Map<String,CRColumn> columnsMap, Map<Long,CRTableType> tableTypeMap) {
		this.columnsMap = columnsMap;
		this.tableTypeMap=tableTypeMap;
		query = new StringBuilder();
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.IQueryBuilder#build(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	public StringBuilder build(CustomReport customReport)
			throws QueryBuilderException {
		buildSelectQuery(customReport);
		buildFromQuery(customReport);
		buildWhereQuery(customReport);
		buildGroupByQuery(customReport);
		buildOrderByQuery(customReport);
		buildClosingQuery(customReport);
		return query;
	}
	
	protected abstract void buildSelectQuery(CustomReport customReport);
	
	protected abstract void buildFromQuery(CustomReport customReport);
	
	protected abstract void buildWhereQuery(CustomReport customReport);
	
	/**
	 * Group by query is empty if not override by sub class
	 * @param customReport
	 */
	protected void buildGroupByQuery(CustomReport customReport){
		query.append("");
	}
	
	/**
	 * Order by query is empty if not override by sub class
	 * @param customReport
	 */
	protected void buildOrderByQuery(CustomReport customReport){
		query.append("");
	}
	
	/**
	 * Closing is empty if not override by sub class - used for outer query for inactive positions
	 * @param customReport
	 */
	protected void buildClosingQuery(CustomReport customReport){
		query.append("");
	}
	
	
	protected String getDBName(Field field){
		CRColumn crColumn = columnsMap.get(field.getKey());
		String dbName = crColumn.getColumnDbname();
		Matcher matcher = RegexUtils.getMatcher(dbName, formulaColumnRegex);
		int count =0;
	    while (matcher.find()) {
    	 	count++;
    		String dbKey = matcher.group().substring(2, matcher.group().length()-1);
    		dbName = dbName.replace(matcher.group(), getDBName(field, columnsMap.get(dbKey)));
	    }
	    if(count==0){
	    	dbName = getDBName(field, crColumn);
	    }
		return dbName;
	}
	
	protected String getDBName(CRColumn crColumn){
		String dbName = crColumn.getColumnDbname();
		Matcher matcher = RegexUtils.getMatcher(dbName, formulaColumnRegex);
		int count =0;
	    while (matcher.find()) {
    	 	count++;
    		String dbKey = matcher.group().substring(2, matcher.group().length()-1);
    		dbName = dbName.replace(matcher.group(), getDBName(null, columnsMap.get(dbKey)));
	    }
	    if(count==0){
	    	dbName = getDBName(null, crColumn);
	    }
		return dbName;
	}
	
	//if block check for column name exists in encrypted map added to avoid dateformat on encrypted string in mysql query
	protected String getDBName(Field field, CRColumn crColumn){
		String columnDbName = crColumn.getColumnDbname();
		if(field!=null && (Date.class.getName().equals(crColumn.getColumnDataType()) && String.class.getName().equals(field.getDataType())) && !DBConstants.columnKeyMap.containsKey(columnDbName)){
			StringBuilder sb = new StringBuilder();
			sb.append(DATE_FORMAT).append(OPEN_BRACE) 
			.append(getDBName(crColumn.getCRTableType().getTableShortName(), crColumn.getColumnDbname()))
			.append(COMMA)
			.append('\'').append(DateUtils.getSystemDbDatePattern()).append('\'')
			.append(CLOSE_BRACE);
			return sb.toString();			
		} else {
			return getDBName(crColumn.getCRTableType().getTableShortName(), crColumn.getColumnDbname());			
		}
		
	}
	
	protected String getDBName(String tableShortName, String dbName){
		return tableShortName+DOT+dbName;
	}
	
	protected void appendSelectFields(List<Field> columns, boolean start){
		for (Field column : columns) {
			if(!start){
				query.append(COMMA);
				appendSelectFields(column);
			}else {
				appendSelectFields(column);
				start = false;					
			}
		}
	}
	
	protected void appendSelectFields(List<Field> columns){
		for (Field column : columns) {
			query.append(COMMA);
			appendSelectFields(column);
		}
	}
	
	protected void appendSelectFields(Field column){
		appendSelectFields(column, column.getKey());
	}
	
	protected void appendSelectFields(Field column, String key){
		if(column!=null){
			query.append(getDBName(column)).append(AS).append(key);
		}else {
			TPLogger.getLogger().error("No column found");
		}
	}
	
	protected void appendSelectFields(CRColumn column){
		appendSelectFields(column, column.getColumnProperty());
	}
	
	protected void appendSelectFields(CRColumn column, String key){
		if(column!=null){
			query.append(getDBName(column)).append(AS).append(key);
		}else {
			TPLogger.getLogger().error("No column found");
		}
	}
	
	protected void appendOrerByFieldToSelectQuery(Field column){
		if(column!=null){
			CRColumn crColumn = columnsMap.get(column.getKey());
			query.append(COMMA);
			appendSelectFields(crColumn.getOrderByColumn(), column.getKey()+ORDER_BY_FIELD);			
		}
	}
	
	protected void appendSelectFieldsAsSum(List<Field> columns){
		for (Field column : columns) {
			query.append(COMMA);
			appendSelectFieldsAsSum(column);
		}
	}
	
	protected void appendSelectFieldsAsSum(Field column){
		CRColumn crColumn = columnsMap.get(column.getKey());
		if(crColumn!=null){
			if(COLUMN_VALUE_TYPE_SUM.equals(crColumn.getValueType())){
				query.append(SUM).append(OPEN_BRACE).append(getDBName(column)).append(CLOSE_BRACE).append(AS).append(column.getKey());	
			}else {
				query.append(getDBName(column)).append(AS).append(column.getKey());
			}				
		}else {
			TPLogger.getLogger().error("No column found with column property : "+column.getKey());
		}
	}
	
	protected void appendSelectFieldsAsGroupConcat(List<Field> columns){
		for (Field column : columns) {
			query.append(COMMA);
			appendSelectFieldsAsGroupConcat(column);
		}
	}
	
	protected void appendSelectFieldsAsGroupConcat(Field column){
		CRColumn crColumn = columnsMap.get(column.getKey());
		if(crColumn!=null){
			if(COLUMN_VALUE_TYPE_SUM.equals(crColumn.getValueType())){
				query.append(GROUP_CONCAT).append(OPEN_BRACE).append(getDBName(column)).append(CLOSE_BRACE).append(AS).append(column.getKey());	
			}else {
				query.append(getDBName(column)).append(AS).append(column.getKey());
			}				
		}else {
			TPLogger.getLogger().error("No column found with column property : "+column.getKey());
		}
	}
	
	protected String appendFromClause(List<Field> columns, List<String> tableSet, boolean start){
		String joinTableShrtName = null;
		for (Field column : columns) {
			if(!start) {
				joinTableShrtName = buildJoinClause(column.getKey(), joinTableShrtName, tableSet);
			} else {
				joinTableShrtName = buildFromClause(column.getKey(), column.getTableType());
				tableSet.add(joinTableShrtName);
				start = false;
			}
		}
		return joinTableShrtName;
	}
	
	protected String appendFromClause(List<Field> columns, List<String> tableSet, String joinTableShrtName){
		for (Field column : columns) {
			joinTableShrtName = buildJoinClause(column.getKey(), joinTableShrtName, tableSet);
		}
		return joinTableShrtName;
	}
	
	protected String buildJoinClause(String dbKey, String joinTableShrtName, List<String> tableSet){
		CRColumn crcolumn =  columnsMap.get(dbKey);
		if(!tableSet.contains(crcolumn.getCRTableType().getTableShortName())){
			query.append(LEFT_JOIN).append(crcolumn.getCRTableType().getTableDbName()).append(AS)
			  .append(crcolumn.getCRTableType().getTableShortName()).append(ON).append(OPEN_BRACE)
			  .append(joinTableShrtName).append(DOT).append(crcolumn.getCRTableType().getTableColumnJoin())
			  .append(EQUAL)
			  .append(crcolumn.getCRTableType().getTableShortName()).append(DOT)
			  .append(crcolumn.getCRTableType().getTableColumnJoin())
			  .append(CLOSE_BRACE);
			
			tableSet.add(crcolumn.getCRTableType().getTableShortName());
		}
		return joinTableShrtName;
	}
	
	private String buildFromClause(String dbKey, String tableType){
		CRColumn crcolumn =  columnsMap.get(dbKey);
		CRTableType crTableType  =  tableTypeMap.get(Long.parseLong(tableType));
		if(crTableType==null)
			crTableType = crcolumn.getCRTableType();
		String joinTableShrtName = crcolumn.getCRTableType().getTableShortName();
		query.append(crTableType.getTableDbName()).append(AS).append(crTableType.getTableShortName());
		return joinTableShrtName;
	}
	
	protected void appendOrderByClause(List<Field> fields, boolean start){
		for (Field field : fields) {
			if(columnsMap.get(field.getKey()).getOrderByColumn()!=null){
				if(!start){
					query.append(COMMA).append(getDBName(columnsMap.get(field.getKey()).getOrderByColumn()));				
				}else {
					query.append(getDBName(columnsMap.get(field.getKey()).getOrderByColumn()));
					start = false;
				}		
			}
		}
	}
	
	protected void appendGroupByClause(List<Field> fields, boolean start){
		for (Field field : fields) {
			if(columnsMap.get(field.getKey()).getGroupByColumn()!=null){
				if(!start){
					query.append(COMMA).append(getDBName(columnsMap.get(field.getKey()).getGroupByColumn()));				
				}else {
					query.append(getDBName(columnsMap.get(field.getKey()).getGroupByColumn()));
					start = false;
				}				
			}
		}
	}
	
	protected boolean haveOrderByFields(List<Field> fields){
		for (Field field : fields) {
			if(columnsMap.get(field.getKey()).getOrderByColumn()!=null){
				return true;
			}
		}
		return false;
	}
}
