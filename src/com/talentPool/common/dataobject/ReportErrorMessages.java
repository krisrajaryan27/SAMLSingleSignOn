/**
 * 
 */
package com.talentPool.common.dataobject;

import java.util.HashMap;
import java.util.Map;

import com.talentPool.reports.ReportVersionConstants;
/**
 * @author pallavi
 *
 */
public class ReportErrorMessages {
	private static Map<String, String> messages = null;
	static {
		messages = new HashMap<String, String>();
		messages.put(String.valueOf(ReportVersionConstants.REPORT_HIRING_STATUS), "common.error.permission_hiring_status_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_HIRING_FUNNEL), "common.error.permission_hiring_funnel_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_POSITION_SUMMARY), "common.error.permission_position_summary_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_PENDING_ACTION), "common.error.permission_pending_action_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_HIRING_EFICIENCY), "common.error.permission_hiring_efficiency_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_SOURCE_WISE_HIRING), "common.error.permission_sourcewise_hiring_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_MONTHLY_JOINING_REPORT), "common.error.permission_monthly_joining_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_SOURCEWISE_IMPORT), "common.error.permission_sourcewise_import_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_OFFER_TO_JOINED), "common.error.permission_offer_to_joined_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_CANDIDATE_COMPARISON), "common.error.permission_candidate_comparison_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_RECRUITMENT_COST), "common.error.permission_recruitment_cost_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_CALL_LIST), "common.error.permission_call_list_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_INTERVIEW_LIST), "common.error.permission_interview_list_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_APPLICANT_DETAILS), "common.error.permission_applicant_details_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_IMPORT_REPORT), "common.error.permission_import_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_USER_ACTIVITY), "common.error.permission_user_activity_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_POSITION_ACTIVITY), "common.error.permission_position_activity_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_CANDIDATE_STATUS), "common.error.permission_candidate_status_report");
		messages.put(String.valueOf(ReportVersionConstants.MASTER_REPORT), "common.error.permission_master_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_CANDIDATE_OFFERS), "common.error.permission_candidate_offers_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_INTERVIEW_STATUS), "common.error.permission_interview_list_status_report");
		messages.put(String.valueOf(ReportVersionConstants.REPORT_JOINER), "common.error.permission_joiner_report");
	}
	
	public static String get(String reportId) {
		return messages.get(reportId);
	}
}
