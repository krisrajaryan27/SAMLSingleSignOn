/**
 * 
 */
package com.talentPool.customReports.queryBuilder.factory;

import java.util.Map;

import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.CRColumn;
import com.talentPool.customReports.dataobject.CRTableType;
import com.talentPool.customReports.jaxb.CustomReport;
import com.talentPool.customReports.queryBuilder.IQueryBuilder;
import com.talentPool.customReports.queryBuilder.impl.CBuilder;
import com.talentPool.customReports.queryBuilder.impl.RCMAllPositionsQueryBuilder;
import com.talentPool.customReports.queryBuilder.impl.RCMCandidateNamesQueryBuilder;
import com.talentPool.customReports.queryBuilder.impl.RCMQueryBuilder;
import com.talentPool.customReports.queryBuilder.impl.RCQueryBuilder;
import com.talentPool.customReports.queryBuilder.impl.RMQueryBuilder;
import com.talentPool.customReports.utils.CustomReportUtils;

/**
 * @author PraveenK
 * @since  Dec 16, 2011
 */
public class QueryBulderFactory {

	/**
	 * Returns the QueryBuilder based on the availability of rows column and measures.
	 * @param customReport
	 * @return
	 */
	public static IQueryBuilder getQueryBuilder(final CustomReport customReport, final Map<String,CRColumn> columnsMap, final Map<Long,CRTableType> tableTypeMap, boolean showInactivePoistions){
		boolean columns = !Utils.isListEmptyOrNull(customReport.getColumns());
		boolean rows = !Utils.isListEmptyOrNull(customReport.getRows());
		boolean measures = !Utils.isListEmptyOrNull(customReport.getMeasures());
		if(columns && rows && measures){
			if(showInactivePoistions){
				return new RCMAllPositionsQueryBuilder(columnsMap, tableTypeMap);
			}else if(CustomReportUtils.showCandidateNamesInReport(customReport)){
				return new RCMCandidateNamesQueryBuilder(columnsMap, tableTypeMap);
			}else {
				return new RCMQueryBuilder(columnsMap, tableTypeMap);				
			}
		}else if(rows && measures){
			return new RMQueryBuilder(columnsMap, tableTypeMap);
		}else if(rows && columns){
			return new RCQueryBuilder(columnsMap, tableTypeMap);
		}else {
			return new CBuilder(columnsMap, tableTypeMap);
		} 
	}
}
