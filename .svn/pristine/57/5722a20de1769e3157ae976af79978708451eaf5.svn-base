/**
 * 
 */
package com.talentPool.reportDesign.report;

import java.util.ArrayList;

import com.talentPool.budget.BudgetConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
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
public class BudgetTypeReport extends FilterCriteria implements ReportTypes{
	
	/* (non-Javadoc)
	 * @see com.talentPool.reportDesign.report.BaseReport#getFilter()
	 */
	@Override
	public String getFilter() {
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");	
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_DATE + "','" + "Start Date Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_BUDGET_DEPARTMENT + "','" + "Department Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_BUDGET_GRADE + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL)+" Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_BUDGET_BAND + "','" +  GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL)+" Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_BUDGET_STATUS + "','" + " Budget Status Filter" + "')");
				sb.append("]");

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}

	/**
	 * Query making is split in to five section
	 * Select columns
	 * Select tables
	 * apply filters
	 * group by 
	 * order by
	 ***/
	@Override
	public String getQuery(FilterData filterData, ReportData reportData, ArrayList<ColumnData> columns, String userId, PermissionSet permissionSet) {
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
		 query += "(SELECT ";
		 query += " tbi.budget_item_id, tbi.budget_item_name,group_concat(distinct tu.USER_FNAME,' ',tu.USER_LNAME) as owner_name, td.dept_name, tds.dept_name as sub_dept_name, tdss.dept_name as sub_sub_dept_name, ";
		 query += " tbi.start_time, tbi.end_time , tbb.band_name, tbg.grade_name, tbi.head_count as available_head_count,";
		 query += " group_concat(distinct tpo.position_title SEPARATOR ', ') AS position_names, ";
		 query += " group_concat(distinct tpo.position_code SEPARATOR ', ') AS position_codes, ";
		 query += " ( SELECT CASE WHEN count(budget_item_id)=0 THEN 0 ELSE SUM(IF(committed_position_count > 0,committed_position_count,0)) END";
		 query += " FROM ";
		 query += " ( SELECT tp.budget_item_id, tp.position_no_of_openings-COUNT(distinct tajh.applicant_id) as committed_position_count ";
		 query += " FROM tp_positions tp ";
		 query += " LEFT JOIN tp_applicant_joining_history tajh ON(tajh.position_id=tp.position_id)";				
		 query += " WHERE tp.position_status IN (1,5)";
		 query += " AND tp.is_budget_committed = "+BudgetConstants.IS_BUDGET_COMMITTED;
		 query += " GROUP BY tp.position_id ";
		 query += " ) ";
		 query += " as tb ";			
		 query += " WHERE tb.budget_item_id=tbi.budget_item_id ";		
		 query += " ) ";
		 query += " as committed_head_count, ";
		 query += " ( SELECT  COUNT(applicant_id) ";
		 query += " FROM  tp_applicant_joining_history ";
		 query += " WHERE  budget_item_id=tbi.budget_item_id ";
		 query += " ) as used_head_count, ";
		 query += " CASE WHEN tbi.status="+BudgetConstants.BUDGET_ITEM_STATUS_DRAFT+" THEN 'Draft' WHEN tbi.status="+BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE+" THEN 'Active' ELSE 'Deleted' END as status ";
		 query += " FROM tp_budget_items tbi ";
		 query += " LEFT JOIN tp_budget_grades tbg ON (tbg.grade_id=tbi.grade_id) ";
		 query += " LEFT JOIN tp_budget_bands tbb ON(tbb.band_id=tbi.band_id) ";	
		 query += " LEFT JOIN tp_users tu ON (tu.user_id=tbi.owner_id) ";
		 query += " LEFT JOIN tp_departments td ON(td.dept_id=tbi.dept_id) ";	
		 query += " LEFT JOIN tp_departments tds ON(tds.dept_id=tbi.sub_dept_id) ";	
		 query += " LEFT JOIN tp_departments tdss ON(tdss.dept_id=tbi.sub_sub_dept_id) ";	
		 query += " LEFT JOIN tp_positions tpo ON (tpo.budget_item_id=tbi.budget_item_id)  ";
		
		/* Section 3*/
		query += " WHERE 1";
		query= addFilterCriteria(query, filterData, reportData, "tbi.start_time", "tbi.owner_id");
		query= addDoNotShowConfidentialProfileClause(query, userId, permissionSet);
		/* Section 4*/
		 query += " GROUP BY budget_item_id ";
		 query += " ORDER BY budget_item_id, budget_item_name ) as taa";
		return query;
	}

	

}
