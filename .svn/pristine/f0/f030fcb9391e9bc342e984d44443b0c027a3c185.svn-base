/**
 * 
 */
package com.talentPool.reportDesign.report;

import java.util.ArrayList;

import com.talentPool.applicant.ApplicantConstants;
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
public class AuditLogTypeReport extends FilterCriteria implements ReportTypes{
	
	/* (non-Javadoc)
	 * @see com.talentPool.reportDesign.report.BaseReport#getFilter()
	 */
	@Override
	public String getFilter() {
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_AUDIT_ENTITY + "','" + "Entity Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_USER + "','" + "Created By Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_DATE + "','" + "Date Filter" + "')");
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
			query += ", " + ColumnUtils.columnDBMap.get(ReportDesignConstants.COLUMN_CANDIDATE_CUSTOM_FIELDS);
		}
		
		query += " FROM  ";
		/* Section 2*/
		query += " tp_audit_entries tae ";
		query += " LEFT JOIN tp_users tuc ON (tuc.user_id=tae.user_id) ";
		query += " LEFT JOIN tp_users tus ON (tus.user_id=tae.entity_id AND tae.entity_type=1) ";
		if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
			query += " LEFT JOIN tp_applicants tap ON (tap.applicant_id=tae.entity_id AND tae.entity_type=3 " +
					"AND tap.is_confidential="+ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL+" ) ";
		}else {
			query += " LEFT JOIN tp_applicants tap ON (tap.applicant_id=tae.entity_id AND tae.entity_type=3) ";
		}
		query += " LEFT JOIN tp_positions tpo ON (tpo.position_id=tae.entity_id AND tae.entity_type IN(2,6)) ";
		query += " LEFT JOIN tp_levels tpl ON (tpl.level_id=tae.entity_id AND tae.entity_type=5) ";
		query += " LEFT JOIN tp_roles tpr ON (tpr.role_id=tae.entity_id AND tae.entity_type=4) ";
		
		/* Section 3*/
		query += " WHERE 1";
		query= addFilterCriteria(query, filterData, reportData, "tae.date_created", "tuc.user_id");
		//query= addDoNotShowConfidentialProfileClause(query, userId, permissionSet);
		/* Section 4*/
		query += " GROUP BY tae.audit_id ";
		return query;
	}

	

}
