package com.talentPool.customReports.queryBuilder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.djhelper.constants.DJHelperConstants;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;

/**
 * @author PraveenK
 * @since  Dec 18, 2011
 */
public class RCMQueryBuilder extends AbstractQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public RCMQueryBuilder(Map<String, CRColumn> columnsMap,  Map<Long,CRTableType> tableTypeMap) {
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
		appendSelectFields(customReport.getMeasures());
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
		appendFromClause(fields, tableSet, true);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildWhereQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildWhereQuery(CustomReport customReport) {
		super.query.append(" WHERE 1 @@ ");
	}
	
	
	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildOrderByQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildOrderByQuery(CustomReport customReport) {
		List<Field> orderByFields = new ArrayList<Field>();
		orderByFields.addAll(customReport.getRows());
		orderByFields.addAll(customReport.getColumns());
		orderByFields.addAll(customReport.getMeasures());
		if(haveOrderByFields(orderByFields)){
			query.append(ORDER_BY);
			appendOrderByClause(orderByFields, true);
		}
	}
}
