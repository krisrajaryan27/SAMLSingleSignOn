/**
 * 
 */
package com.talentPool.customReports.queryBuilder.impl;

import static com.talentPool.customReports.djhelper.constants.DJHelperConstants.ORDER_BY_FIELD;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.customReports.constants.TableTypeConstants;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.djhelper.constants.DJHelperConstants;
import com.talentPool.customReports.djhelper.wrappers.JasperDateWrapper;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.customReports.queryBuilder.utils.QueryBuilderUtils;

/**
 * @author SachinM
 * @since  July 17, 2012
 */
public class RCMAllPositionsQueryBuilder extends RCMQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public RCMAllPositionsQueryBuilder(Map<String, CRColumn> columnsMap,  Map<Long,CRTableType> tableTypeMap) {
		super(columnsMap, tableTypeMap);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildSelectQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildSelectQuery(CustomReport customReport) {
		
		query.append(SELECT);
		appendSelectFieldsToOuterQuery(customReport.getRows(), true);
		appendSelectFieldsToOuterQuery(customReport.getColumns());
		appendSelectFieldsToOuterQuery(customReport.getMeasures());
		for (Field column : customReport.getColumns()) {
			if(DJHelperConstants.DJ_COMPARATORS_MAP.containsKey(column.getDataType())){
				query.append(COMMA).append("'1'").append(AS).append(column.getKey()+ORDER_BY_FIELD);				
			}
		}
		for (Field rows : customReport.getRows()) {
			if(DJHelperConstants.DJ_COMPARATORS_MAP.containsKey(rows.getDataType())){
				query.append(COMMA).append("'1'").append(AS).append(rows.getKey()+ORDER_BY_FIELD);
			}
		}
		query.append(" from tp_cr_position_master trpm ")
			.append(" join tp_position_steps tps on (trpm.position_id = tps.position_id) ")
			.append(" join tp_step_master tsm on (tps.step_id = tsm.step_id) ")
			.append(" where trpm.position_id not in ( ")
			.append(" select distinct position_id from ( ");
		
		query.append(SELECT);
		
		appendSelectFields(customReport.getRows(),true);
		appendSelectFields(customReport.getColumns());
		appendSelectFields(customReport.getMeasures());
		
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
	
	protected void appendSelectFieldsToOuterQuery(List<Field> fields){
		appendSelectFieldsToOuterQuery(fields, false);
	}
	
	/**
	 * @param fields
	 * @param customReport
	 */
	protected void appendSelectFieldsToOuterQuery(List<Field> fields, boolean isStart){
		for (Field field : fields) {
			if(!isStart) {
				query.append(COMMA);				
			} else {
				isStart = false;
			}				
			if(TableTypeConstants.POSITION_MASTER.equals(field.getTableType())){
				appendSelectFields(field);
			} else { 
				if (field.getDataType().equalsIgnoreCase(Integer.class.getName())){
					query.append("0");					
				} else if (field.getDataType().equals(JasperDateWrapper.class.getName())) {
					query.append("NULL");
				} else if (DJHelperConstants.DJ_COMPARATORS_MAP.containsKey(field.getDataType())) {				
					query.append("'All Selected/Applicable'");
				} else {
					query.append("'0'");
				}
				query.append(AS).append(field.getKey());
			}			
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
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildWhereQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildClosingQuery(CustomReport customReport) {
		query.append(" ) as X ) @@ ");
	}
	
}
