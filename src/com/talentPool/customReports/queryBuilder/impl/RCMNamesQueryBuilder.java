/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.queryBuilder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.customReports.constants.CustomReportColumnConstants;
import com.talentPool.customReports.constants.TableTypeConstants;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.djhelper.constants.DJHelperConstants;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.customReports.queryBuilder.utils.QueryBuilderUtils;

/**
 * @author PraveenK
 * @since  Dec 18, 2011
 */
public class RCMNamesQueryBuilder extends AbstractQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public RCMNamesQueryBuilder(Map<String, CRColumn> columnsMap, Map<Long,CRTableType> tableTypeMap) {
		super(columnsMap, tableTypeMap);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildSelectQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildSelectQuery(CustomReport customReport) {
		query.append(SELECT);
		appendSelectFields(customReport.getRows(),true);
		appendSelectFields(customReport.getColumns());
		for (Field measure : customReport.getMeasures()) {
			measure.setTableType(TableTypeConstants.EVENT_LOG); 
			query.append(COMMA);
			query.append(groupConcatedDBColumn("trcm.applicant_name")).append(AS).append(measure.getKey());
		}
		for (Field column : customReport.getColumns()) {
			if(DJHelperConstants.DJ_COMPARATORS_MAP.containsKey(column.getDataType())){
				appendOrerByFieldToSelectQuery(column);				
			}
		}
		for (Field rows : customReport.getRows()) {
			if(DJHelperConstants.DJ_COMPARATORS_MAP.containsKey(rows.getDataType())){
				appendOrerByFieldToSelectQuery(rows);				
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildFromQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildFromQuery(CustomReport customReport) {
		List<String> tableSet = new ArrayList<String>();
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(customReport.getMeasures());
		fields.addAll(customReport.getColumns());
		fields.addAll(customReport.getRows());
		query.append(FROM);
		String joinTableShrtName = appendFromClause(fields, tableSet, true);
		buildJoinClause(CustomReportColumnConstants.APPLICANT_ID, joinTableShrtName, tableSet);
	}
	
	protected String buildJoinClause(String dbKey, String joinTableShrtName, List<String> tableSet){
		CRColumn crcolumn =  columnsMap.get(dbKey);
		if(!tableSet.contains(crcolumn.getCRTableType().getTableShortName())){
			String joinColumn = getJoinColumn(crcolumn);
			query.append(JOIN).append(crcolumn.getCRTableType().getTableDbName()).append(AS)
			  .append(crcolumn.getCRTableType().getTableShortName()).append(ON).append(OPEN_BRACE)
			  .append(joinTableShrtName).append(DOT).append(joinColumn)
			  .append(EQUAL)
			  .append(crcolumn.getCRTableType().getTableShortName()).append(DOT)
			  .append(crcolumn.getCRTableType().getTableColumnJoin())
			  .append(CLOSE_BRACE);
			
			tableSet.add(crcolumn.getCRTableType().getTableShortName());
		}
		return joinTableShrtName;
	}
	
	private String getJoinColumn(CRColumn crcolumn){
		String joinColumn = "";
		if(CustomReportColumnConstants.STEP_NAME.equals(crcolumn.getColumnProperty())){
			joinColumn = "step_id_to";
		}else if(CustomReportColumnConstants.STAGE_NAME.equals(crcolumn.getColumnProperty())){
			joinColumn = "position_step_level_to";
		}else{
			joinColumn = crcolumn.getCRTableType().getTableColumnJoin();
		}
		return joinColumn;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildWhereQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildWhereQuery(CustomReport customReport) {
		//super.query.append(" WHERE tres.step_id_to>0 AND tres.position_step_level_to=2 AND tres.step_id_to IN ("+customReport.getStepsToShowCandidateNames()+") @@ ");
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildGroupByQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildGroupByQuery(CustomReport customReport) {
		boolean haveSumFieldColumns = QueryBuilderUtils.haveGroupingFields(customReport.getMeasures());
		if(haveSumFieldColumns){
			List<Field> fields = new ArrayList<Field>();
			fields.addAll(customReport.getRows());
			fields.addAll(customReport.getColumns());
			query.append(GROUP_BY);
			appendGroupByClause(fields, true);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildOrderByQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildOrderByQuery(CustomReport customReport) {
		List<Field> rows = customReport.getRows();
		List<Field> columns = customReport.getColumns();
		List<Field> measures = customReport.getMeasures();
		query.append(ORDER_BY);
		appendOrderByClause(rows, true);
		appendOrderByClause(columns, false);
		appendOrderByClause(measures, false);
	}
	
	/**
	 * @param dbName
	 * @return
	 */
	protected String getDBName(String tableShortName, String dbName){
		if("tres".equals(tableShortName)){
			if("process_date".equals(dbName)){
				return tableShortName+DOT+"process_moved_date";
			}else if("step_id".equals(dbName)){
				return tableShortName+DOT+"step_id_to";
			}else if("step_level".equals(dbName)){
				return tableShortName+DOT+"position_step_level_to";
			}else{
				return tableShortName+DOT+dbName;
			}
		}else {
			return tableShortName+DOT+dbName;
		}
	}
	
	/**
	 * @param dbName
	 * @return
	 */
	private String groupConcatedDBColumn(String dbName){
		return "trimLongStrings(GROUP_CONCAT("+dbName+" ORDER BY "+dbName+" SEPARATOR ', '))"; 
	}
}
