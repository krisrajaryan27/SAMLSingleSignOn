/**
 * 
 */
package com.talentPool.customReports.queryBuilder.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.jaxb.CustomReport;

/**
 * @author PraveenK
 * @since  Dec 18, 2011
 */
public class CBuilder extends AbstractQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public CBuilder(Map<String, CRColumn> columnsMap, Map<Long,CRTableType> tableTypeMap) {
		super(columnsMap, tableTypeMap);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildSelectQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildSelectQuery(CustomReport customReport) {
		query.append(SELECT);
		appendSelectFields(customReport.getColumns(),true);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildFromQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildFromQuery(CustomReport customReport) {
		List<String> tableSet = new ArrayList<String>();
		query.append(FROM);
		appendFromClause(customReport.getColumns(), tableSet, true);
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
		if(haveOrderByFields(customReport.getColumns())){
			query.append(ORDER_BY);
			appendOrderByClause(customReport.getColumns(), true);			
		}
	}	

}
