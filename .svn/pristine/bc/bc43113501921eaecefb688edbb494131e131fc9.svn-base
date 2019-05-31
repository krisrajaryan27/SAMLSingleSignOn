/**
 * 
 */
package com.talentPool.reportDesign.report;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class ExpenseTypeReport extends FilterCriteria implements ReportTypes {

	/* (non-Javadoc)
	 * @see com.talentPool.reportDesign.report.ReportTypes#getFilter()
	 */
	@Override
	public String getFilter() {
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_EXPENSE_TYPE + "','" + "Expense Type Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_DATE + "','" + "Date Filter" + "')");
				sb.append("]");

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.talentPool.reportDesign.report.ReportTypes#getQuery(com.talentPool.reports.dataobject.FilterData, com.talentPool.reportDesign.dataobject.ReportData, java.util.ArrayList, java.lang.String, com.talentPool.user.manager.PermissionSet)
	 */
	@Override
	public String getQuery(FilterData filterData, ReportData reportData, ArrayList<ColumnData> columns,String userId, PermissionSet permissionSet){
		String query = "";
		query = " SELECT ";
		/* Section 1*/
		
		for (int i = 0; i < columns.size(); i++) {
			ColumnData columnData = columns.get(i);			
			String field = ColumnUtils.columnDBMap.get(columnData.getColumnName());
			if(!Utils.isBlankOrNull(field)){
				if(i!=0){
					query +=", ";	
				}
				query += field;
			}
			
		}
		query += " FROM  ";
		
		/* Section 2*/
		query += " tp_costs tco ";		
		query += " LEFT JOIN tp_users tus on(tco.user_id=tus.user_id)";
		query += " LEFT JOIN tp_cost_types tct on(tco.cost_type_id=tct.cost_type_id) ";			
		query += " LEFT JOIN tp_cost_positions tcp on(tcp.cost_id=tco.cost_id) ";
		/* Section 3*/
		query += " WHERE 1";
		query= addFilterCriteria(query, filterData, reportData, "tco.cost_paid_date", null);
		
		query += " GROUP BY tco.cost_id ";
		return query;
	}

}
