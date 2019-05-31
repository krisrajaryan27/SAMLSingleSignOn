/**
 * 
 */
package com.talentPool.reportDesign.report;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
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
public class PositionTypeReport extends FilterCriteria implements ReportTypes {

	/* (non-Javadoc)
	 * @see com.talentPool.reportDesign.report.ReportTypes#getFilter()
	 */
	@Override
	public String getFilter() {
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_DEPARTMENT + "','" + "Department Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_POSITION + "','" + "Position Filter" + "')");
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
	public String getQuery(FilterData filterData, ReportData reportData, ArrayList<ColumnData> columns, String userId, PermissionSet permissionSet){
		String query = "";
		query = " SELECT ";
		/* Section 1*/
		
		boolean customFieldPresent = false;
		for (int i = 0; i < columns.size(); i++) {
			ColumnData columnData = columns.get(i);
			String columnType = columnData.getColumnType();
			if(columnType.equals("1")){
				customFieldPresent=true;
			}else{
				String field = ColumnUtils.columnDBMap.get(columnData.getColumnName());
				if(!Utils.isBlankOrNull(field)){
					if(i!=0){
						query +=", ";	
					}
					query += field;
				}
			}
		}
		if(customFieldPresent){
			query += ", " + ColumnUtils.columnDBMap.get(ReportDesignConstants.COLUMN_POSITION_CUSTOM_FIELDS);
		}
		
		query += " FROM  ";
		
		/* Section 2*/
		query += " tp_positions tpo ";		
		
		query += " LEFT JOIN tp_departments tdp ON (tdp.dept_id=tpo.dept_id) ";
		query += " LEFT JOIN tp_departments tdps ON (tdps.dept_id=tpo.sub_dept_id) ";
		query += " LEFT JOIN tp_departments tdpss ON (tdpss.dept_id=tpo.sub_sub_dept_id) ";
		
		query += " LEFT JOIN tp_position_branches tpb ON (tpb.position_id=tpo.position_id)";
		query += " LEFT JOIN tp_position_degrees tpd ON (tpd.position_id=tpo.position_id)";
		query += " LEFT JOIN tp_branches tbrp ON (tbrp.branch_id=tpb.branch_id) ";
		query += " LEFT JOIN tp_degrees tdep ON (tdep.degree_id=tpd.degree_id) ";
		query += " LEFT JOIN tp_position_locations tplo on (tplo.position_id=tpo.position_id)";
				
		query += " LEFT JOIN tp_locations tlo on(tlo.location_id=tplo.location_id)";
		
		query += " LEFT JOIN tp_users tureq on(tureq.user_id=tpo.position_requested_by)";
		query += " LEFT JOIN tp_users tucre on(tucre.user_id=tpo.position_created_by)";
		query += " LEFT JOIN tp_users tucls on(tucls.user_id=tpo.position_closed_by)";
		query += " LEFT JOIN tp_users tudel on(tudel.user_id=tpo.position_deleted_by)";
		
		query += " LEFT JOIN tp_position_skills tpsp ON(tpsp.position_id=tpo.position_id AND tpsp.skill_type=0) ";
		query += " LEFT JOIN tp_skills tskp ON(tskp.skill_id=tpsp.skill_id) ";
		query += " LEFT JOIN tp_position_skills tpss ON(tpss.position_id=tpo.position_id AND tpss.skill_type=1) ";
		query += " LEFT JOIN tp_skills tsks ON(tsks.skill_id=tpss.skill_id) ";
		
		query += " LEFT JOIN tp_custom_field_values_position tvp ON (tvp.entity_id=tpo.position_id) ";
		query += " LEFT JOIN tp_custom_fields tcfp ON (tcfp.custom_field_id=tvp.custom_field_id) ";
		
		query += " WHERE tpo.position_status != " + PositionConstants.POSITION_STATUS_TEMPLATE;
		query= addFilterCriteria(query, filterData, reportData, "tpo.position_date_created", "tucre.user_id");
		query=addShowPositionWithRightsClause(query, userId, permissionSet);
		
		/* Section 3*/
		query += " GROUP BY tpo.position_id ";
		return query;
	}

}
