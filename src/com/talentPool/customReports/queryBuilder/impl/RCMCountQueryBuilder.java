/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.queryBuilder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CRColumnValueConstants;
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
public class RCMCountQueryBuilder extends RCMQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public RCMCountQueryBuilder(Map<String, CRColumn> columnsMap, Map<Long,CRTableType> tableTypeMap) {
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
		appendSelectFieldsAsSum(customReport.getMeasures(), customReport);
		query.append(COMMA).append("tres.position_id AS position_id");
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
	
	/**
	 * @param columns
	 * @param customReport
	 */
	protected void appendSelectFieldsAsSum(List<Field> columns, CustomReport customReport){
		for (Field column : columns) {
			query.append(COMMA);
			query.append(CASE);
			if(!Utils.isBlankOrNull(customReport.getCandNamesSpecifications().getStepsToShowNames())){
				if(CRColumnValueConstants.COLUMN_VALUE_TYPE_SUM.equals(column.getFieldValueType())){
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNames()+") THEN CONCAT('C_',GROUP_CONCAT("+getDBName(column)+"_ids))");
				}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_FIRST.equals(column.getFieldValueType())){
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNames()+") THEN SUBSTRING_INDEX(CONCAT('C_',GROUP_CONCAT(IFNULL("+getDBName(column)+"_ids,'') ORDER BY tres.process_date SEPARATOR '|')),'|',1)");					
				}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_LAST.equals(column.getFieldValueType())){
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNames()+") THEN SUBSTRING_INDEX(CONCAT('C_',GROUP_CONCAT(IFNULL("+getDBName(column)+"_ids,'') ORDER BY tres.process_date DESC SEPARATOR '|')),'|',1)");					
				}else {
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNames()+") THEN "+getDBName(column));					
				}
			}
			
			if(!Utils.isBlankOrNull(customReport.getCandNamesSpecifications().getStepsToShowNamesAndAttributes())){
				if(CRColumnValueConstants.COLUMN_VALUE_TYPE_SUM.equals(column.getFieldValueType())){
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNamesAndAttributes()+") THEN CONCAT('CA_',GROUP_CONCAT("+getDBName(column)+"_ids))");
				}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_FIRST.equals(column.getFieldValueType())){
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNamesAndAttributes()+") THEN SUBSTRING_INDEX(CONCAT('CA_',GROUP_CONCAT(IFNULL("+getDBName(column)+"_ids,'') ORDER BY tres.process_date SEPARATOR '|')),'|',1)");					
				}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_LAST.equals(column.getFieldValueType())){
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNamesAndAttributes()+") THEN SUBSTRING_INDEX(CONCAT('CA_',GROUP_CONCAT(IFNULL("+getDBName(column)+"_ids,'') ORDER BY tres.process_date DESC SEPARATOR '|')),'|',1)");					
				}else {
					query.append(" WHEN tres.step_id IN ("+customReport.getCandNamesSpecifications().getStepsToShowNamesAndAttributes()+") THEN "+getDBName(column));					
				}
			}
			
			query.append(ELSE);
			if(CRColumnValueConstants.COLUMN_VALUE_TYPE_FIRST.equals(column.getFieldValueType())){
				query.append(" SUBSTRING_INDEX(GROUP_CONCAT(IFNULL("+getDBName(column)+",'') ORDER BY tres.process_date SEPARATOR '|'),'|',1)");					
			}else if(CRColumnValueConstants.COLUMN_VALUE_TYPE_LAST.equals(column.getFieldValueType())){
				query.append(" SUBSTRING_INDEX(GROUP_CONCAT(IFNULL("+getDBName(column)+",'') ORDER BY tres.process_date DESC SEPARATOR '|'),'|',1)");					
			}else {
				query.append(" SUM("+getDBName(column)+") ");
			}
			
			query.append(END);
			query.append(AS).append(column.getKey());
		}
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
	
}
