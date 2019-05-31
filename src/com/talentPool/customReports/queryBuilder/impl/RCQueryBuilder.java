/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.queryBuilder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.jaxb.Field;
import com.talentPool.customReports.queryBuilder.utils.QueryBuilderUtils;

/**
 * @author PraveenK
 * @since  Dec 20, 2011
 */
public class RCQueryBuilder extends AbstractQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public RCQueryBuilder(Map<String, CRColumn> columnsMap, Map<Long,CRTableType> tableTypeMap) {
		super(columnsMap, tableTypeMap);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildSelectQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildSelectQuery(CustomReport customReport) {
		query.append(SELECT);
		appendSelectFields(customReport.getRows(),true);
		appendSelectFieldsAsSum(customReport.getColumns());
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildFromQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildFromQuery(CustomReport customReport) {
		List<String> tableSet = new ArrayList<String>();
		List<Field> fields = new ArrayList<Field>();
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
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildGroupByQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildGroupByQuery(CustomReport customReport) {
		boolean haveGroupingFieldColumns = QueryBuilderUtils.haveGroupingFields(customReport.getColumns());
		if(haveGroupingFieldColumns){
			List<Field> fields = new ArrayList<Field>();
			fields.addAll(customReport.getRows());
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
		query.append(ORDER_BY);
		appendOrderByClause(rows, true);
	}	

}
