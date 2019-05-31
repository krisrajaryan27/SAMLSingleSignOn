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
import com.talentPool.customReports.jaxb.Field;

/**
 * @author PraveenK
 * @since  Dec 18, 2011
 */
public class RMQueryBuilder extends AbstractQueryBuilder {

	/**
	 * @param columnsMap
	 */
	public RMQueryBuilder(Map<String, CRColumn> columnsMap, Map<Long,CRTableType> tableTypeMap) {
		super(columnsMap, tableTypeMap);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildSelectQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildSelectQuery(CustomReport customReport) {
		query.append(SELECT);
		appendSelectFields(customReport.getRows(),true);
		appendSelectFieldsAsSum(customReport.getMeasures());
	}

	/* (non-Javadoc)
	 * @see com.talentPool.customReports.queryBuilder.impl.AbstractQueryBuilder#buildFromQuery(com.talentPool.customReports.jaxb.CustomReport)
	 */
	@Override
	protected void buildFromQuery(CustomReport customReport) {
		List<String> tableSet = new ArrayList<String>();
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(customReport.getMeasures());
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
		List<Field> rows = customReport.getRows();
		query.append(" GROUP BY ");
		boolean start = true;
		for (Field row : rows) {
			if(!start){
				query.append(COMMA).append(getDBName(columnsMap.get(row.getKey()).getGroupByColumn()));				
			}else {
				query.append(getDBName(columnsMap.get(row.getKey()).getGroupByColumn()));
				start = false;
			}
		}
	}

}
