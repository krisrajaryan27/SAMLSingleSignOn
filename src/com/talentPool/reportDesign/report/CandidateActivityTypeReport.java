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
public class CandidateActivityTypeReport extends FilterCriteria implements ReportTypes {

	/* (non-Javadoc)
	 * @see com.talentPool.reportDesign.report.ReportTypes#getFilter()
	 */
	@Override
	public String getFilter() {
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_SOURCE + "','" + "Source Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_IMPORTED_BY + "','" + "Imported By Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_ACTIVITY_BY + "','" + "Activity By Filter" + "'),");
				sb.append("new SelectOption('" + ReportDesignConstants.FILTER_ACTIVITY_TYPE + "','" + "Activity Type Filter" + "'),");
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
			query += ", " + ColumnUtils.columnDBMap.get(ReportDesignConstants.COLUMN_CANDIDATE_CUSTOM_FIELDS);
		}
		
		query += " FROM  ";
		/* Section 2*/
		
		query += " tp_user_activity tact ";
		query += " JOIN tp_applicants tap ON (tact.applicant_id=tap.applicant_id) ";		

		query += " LEFT JOIN tp_sources tso ON(tso.source_id=tap.source_id) ";
		query += " LEFT JOIN tp_source_types tsy ON(tsy.source_type_id=tso.source_type_id) ";
		query += " LEFT JOIN tp_applicant_flags taf ON(taf.applicant_id=tap.applicant_id) ";
		query += " LEFT JOIN tp_flags tfl ON(tfl.flag_id=taf.flag_id) ";
		query += " LEFT JOIN tp_applicant_skills tas ON(tas.applicant_id=tap.applicant_id) ";
		query += " LEFT JOIN tp_skills tsk ON(tsk.skill_id=tas.skill_id) ";
		query += " LEFT JOIN tp_skill_category tsc ON(tsc.skill_category_id=tsk.skill_category_id) ";
		query += " LEFT JOIN tp_applicant_educational_info taei ON (taei.applicant_id=tap.applicant_id) ";
		query += " LEFT JOIN tp_branches tbr ON (tbr.branch_id=taei.branch_id) ";
		query += " LEFT JOIN tp_degrees tde ON (tde.degree_id=taei.degree_id) ";
		query += " LEFT JOIN tp_institutes tin ON (tin.institute_id=taei.institute_id) ";
		query += " LEFT JOIN tp_users tus ON(tus.user_id=tap.user_id) ";
		
		// Custom Fields Applicant
		query += " LEFT JOIN tp_custom_field_values_applicant tva ON (tva.entity_id=tact.applicant_id) ";
		query += " LEFT JOIN tp_custom_fields tcfa ON (tcfa.custom_field_id=tva.custom_field_id) ";
		
		//Activity tables
		query += " LEFT JOIN tp_users tactu ON (tactu.user_id=tact.user_id) ";
		
		/* Section 3*/
		query += " WHERE tact.applicant_id IS NOT NULL ";
		query= addFilterCriteria(query, filterData, reportData, "tact.date_created", "tact.user_id");
		query=addDoNotShowConfidentialProfileClause(query, userId, permissionSet);
		/* Section 4*/

		query += " GROUP BY tact.activity_id ";
		return query;
	}

}
