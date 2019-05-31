/**
 * 
 */
package com.talentPool.common.dataobject;

import java.util.HashMap;
import java.util.Map;

import com.talentPool.user.constants.PermissionConstants;

/**
 * @author pallavi
 *
 */
public class PermissionErrorMessages {
	private static Map<String, String> messages = null;
	static {
		messages = new HashMap<String, String>();
		messages.put(String.valueOf(PermissionConstants.PERMISSION_ADMIN), "common.error.permission_admin");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_MANAGE_ROLES), "common.error.permission_manage_roles");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_MANAGE_USERS), "common.error.permission_manage_users");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_APPLICATION_SETTINGS), "common.error.permission_applicantion_settings");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_INBOX_SETTINGS), "common.error.permission_inbox_settings");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_DUPLICATE_DETECTION_SETTINGS), "common.error.permission_duplicate_detection_settings");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_REQUISITION_APPROVAL_STEPS_SETTINGS), "common.error.permission_requisition_approval_step_settings");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SMS_SETTINGS), "common.error.permission_sms_settings");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_LDAP_SETTINGS), "common.error.permission_ldap_settings");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_CHANGE_PASSWORD), "common.error.permission_change_password");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SHOW_POSITION_SUMMARY_DASHBOARD), "common.error.permission_show_position_summary_dashboard");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_IMPORT), "common.error.permission_import");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SHOW_SENT_EMAILS), "common.error.permission_show_sent_email");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_DELETE_EMAIL), "common.error.permission_delete_email");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_IMPORT_FROM_EMAIL), "common.error.permission_import_from_email");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_IMPORT_FROM_DESKTOP), "common.error.permission_import_from_desktop");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SEND_EMAIL_FROM_IMPORT), "common.error.permission_send_email_from_import");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SCREEN), "common.error.permission_screen");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_MASS_EMAIL), "common.error.permission_mass_email");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_DELETE_APPLICANT), "common.error.permission_delete_applicant");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_EDIT_CANDIDATE_DETAILS), "common.error.permission_edit_applicant");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SET_FLAG), "common.error.permission_set_flag");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SEND_SMS), "common.error.permission_send_sms");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SEND_EMAIL), "common.error.permission_send_email");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_UPLOAD_DOCUMENT), "common.error.permission_upload_document");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SHORTLIST), "common.error.permission_shortlist");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SCHEDULE_INTERVIEW), "common.error.permission_schedule_interview");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_UPDATE_FOLLOWUP), "common.error.permission_update_followup");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SELECT), "common.error.permission_select");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_CALL_LIST), "common.error.permission_call_list");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_HIRE), "common.error.permission_hire");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SHOW_JOINED_CANDIDATES), "common.error.permission_show_joined_candidate");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_REPORTS), "common.error.permission_reports");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_POSITIONS), "common.error.permission_positions");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_EDIT_POSITIONS), "common.error.permission_edit_positions");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_POSITION_DETAILS), "common.error.permission_view_positions");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_DELETE_POSITION), "common.error.permission_delete_positions");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_PUBLISH_POSITION), "common.error.permission_publish_positions");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_OPEN_CLOSE_POSITION), "common.error.permission_open_close_positions");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_MASTERS), "common.error.permission_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_BRANCH_MASTER), "common.error.permission_branch_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SKILL_MASTER), "common.error.permission_skill_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SOURCE_MASTER), "common.error.permission_source_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_TEMPLATE_MASTER), "common.error.permission_template_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_FLAG_MASTER), "common.error.permission_flag_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_RATINGS_MASTER), "common.error.permission_ratings_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_FEEDBACK_FIELDS_MASTER), "common.error.permission_feedback_fields_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_FEEDBACK_FORM_MASTER), "common.error.permission_feedback_form_masters");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_EXPENSES), "common.error.permission_expenses");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_ADD_EXPENSE), "common.error.permission_add_expenses");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_ADD_EXPENSE_TYPE), "common.error.permission_add_expenses_type");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_POSITION_PRIORITY), "common.error.permission_position_priority");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SEND_NOTIFICATION_EMAIL), "common.error.permission_send_notification_email");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_PUBLISH_POSITION_FOR_WALK_IN), "common.error.permission_publish_position_for_walk_in");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_MANAGE_ALL_FLAGS), "common.error.permission_manage_flags");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_PUBLISH_POSITION_FOR_EMPLOYEE_PORTAL), "common.error.permission_publish_position_for_employee_portal");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_CUSTOM_FIELDS), "common.error.permission_custom_fields");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_LABEL_MESSAGES), "common.error.permission_label_messages");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SCREEN_CONFIGURATION), "common.error.permission_screen_configuration");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_PUBLISH_POSITION_TO_WEB_SITE), "common.error.permission_publish_position_to_web_site");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_OFFER_SHEET_MASTER), "common.error.permission_offer_sheet_templates");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_GENERATE_OFFER), "common.error.permission_generate_offer_sheet");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_MANAGE_USER_HIERARCHY), "common.error.permission_manage_users_Hierarchy");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_SUMMARY_REPORT_SCHEDULER), "common.error.permission_summary_report");
		messages.put(String.valueOf(PermissionConstants.PERMISSION_CHANGE_OTHER_USER_PASSWORD), "common.error.permission_change_password_other_user");
	}
	
	public static String get(int permission) {
		return messages.get(String.valueOf(permission));
	}
}
