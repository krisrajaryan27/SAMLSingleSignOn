/**
 * 
 */
package com.talentPool.common.properties;

import java.util.Locale;

/**
 * @author pallavi
 * 
 */
public class GlobalConstants {

	public static final String PROPERTY_MAX_ATTACHMENT_SIZE_IN_MB = "max_attachment_size_in_mb";
	public static final String PROPERTY_MAX_SKILLS_PARSED = "max_skills_parsed";
	public static final String PROPERTY_SEARCH_RESULT_PAGE_SIZE = "search_result_page_size";
	public static final String PROPERTY_DURATION_AS_NEW_RESUME = "duration_as_new_resume";
	public static final String PROPERTY_DURATION_TO_DISPLAY_SENT_MESSAGES = "duration_to_display_sent_messages";
	public static final String PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS = "duration_to_display_sent_emails";
	public static final String PROPERTY_SEND_REMINDER_TO_ME = "send_reminder_to_me";
	public static final String PROPERTY_SEND_REMINDER_TO_INTERVIEWER = "send_reminder_to_interviewer";
	public static final String PROPERTY_SEND_REMINDER_TO_CANDIDATE = "send_reminder_to_candidate";
	public static final String PROPERTY_SEND_APPOINTMENT = "send_appointment";
	public static final String PROPERTY_DEFAULT_APPOINTMENT_DURATION = "default_appointment_duration";
	public static final String PROPERTY_DEFAULT_PASSWORD_EXPIRY_DURATION = "default_password_expiry_duration";
	public static final String PROPERTY_DEFAULT_PASSWORD_DIFFERENT_FROM_LAST = "default_password_different_from_last";
	public static final String PROPERTY_DEFAULT_REMINDER_DURATION = "default_reminder_duration";
	public static final String PROPERTY_SMS_ENABLED = "sms_enabled";
	public static final String PROPERTY_SEND_FEEDBACK_REMINDERS = "send_feedback_reminders";
	public static final String PROPERTY_SEND_AUTO_REPLY_EMAIL = "send_auto_reply_email";
	public static final String PROPERTY_LDAP_ENABLED = "ldap_enabled";
	public static final String PROPERTY_FINANCIAL_YEAR_START_MONTH = "financial_yr_start_month";
	public static final String PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR = "notify_vendor_activity_to_hr";
	public static final String PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR = "show_detailed_activity_to_vendor";
	public static final String PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION = "send_duplicate_resume_upload_tried_notification";
	public static final String PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL = "send_duplicate_resume_upload_tried_notification_to_email";	
	public static final String PROPERTY_SHOW_POSITION_CODE = "show_position_code";
	public static final String PROPERTY_FORCE_POSITION_CREATION_FROM_TEMPLATE = "force_position_creation_from_template";
	public static final String PROPERTY_VENDOR_RESUME_UPLOAD_PER_POSITION = "vendor_resume_uplaod_limit";
	public static final String PROPERTY_MAX_DEPT_LEVEL = "max_dept_level";
	public static final String PROPERTY_DEPARTMENT_LEVEL_1="department_level_1";
	public static final String PROPERTY_DEPARTMENT_LEVEL_2="department_level_2";
	public static final String PROPERTY_DEPARTMENT_LEVEL_3="department_level_3";
	public static final String PROPERTY_DEPARTMENT_LEVEL_4="department_level_4";
	public static final String PROPERTY_DEPARTMENT_LEVEL_5="department_level_5";
	
	//Added for Asian Paints BEGIN
	public static final String PROPERTY_LOCATION_LEVEL_1="location_level_1";
	public static final String PROPERTY_LOCATION_LEVEL_2="location_level_2";
	public static final String PROPERTY_LOCATION_LEVEL_3="location_level_3";
	public static final String PROPERTY_FUNCTION="function";
	public static final String PROPERTY_PAY_GRADE="pay_grade";
	public static final String PROPERTY_JOB_CODE="job_code";
	public static final String PROPERTY_COUNTRIES="countries";
	public static final String PROPERTY_STATES="states";
	//Added for Asian Paints END
	
	public static final String PROPERTY_DEFAULT_POSITION_APPROVAL_DURATION = "default_position_approval_duration";
	
	public static final String PROPERTY_SHOW_REMINDER="show_reminder";
	public static final String PROPERTY_DEFAULT_HIRE_BY_DURATION_IN_DAYS="default_hire_by_duration_in_days";
	public static final String PROPERTY_MAXIMUM_NO_OF_PUBLIC_FLAGS_ALLOWED="maximum_no_of_public_flags_allowed";
	public static final String PROPERTY_MAXIMUM_NO_OF_PRIVATE_FLAGS_ALLOWED="maximum_no_of_private_flags_allowed";
	public static final String PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION = "vendor_resume_upload_notification";
	public static final String PROPERTY_REJECTION_EMAIL_TO_VENDOR = "rejection_email_to_vendor";
	public static final String PROPERTY_ENABLE_SINGLE_SIGN_ON = "enable_single_sign_on";	
	public static final String PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION = "employee_resume_upload_notification";
	public static final String PROPERTY_REJECTION_EMAIL_TO_EMPLOYEE = "rejection_email_to_employee";
	public static final String PROPERTY_REJECTION_EMAIL_TO_CANDIDATE = "rejection_email_to_candidate";
	public static final String PROPERTY_POSITION_CODE_TEMPLATE = "position_code_template";
	public static final String PROPERTY_ALLOW_BULK_FEEDBACK = "allow_bulk_feedback";
	public static final String PROPERTY_NOTIFY_CANDIDATE_PROGRESS = "candidate_progress_notification";
	public static final String PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB = "employee_can_apply_for_job";
	public static final String PROPERTY_SHOW_BOT_RESISTANT_EMAIL_ID = "show_bot_resistant_email_id";
	public static final String PROPERTY_AUDIT_TRAIL_RECOVERY_NOTIFICATION = "audit_trail_recovery_notification";
	public static final String PROPERTY_GRID_RESULT_PAGE_SIZE = "grid_result_page_size";
	public static final String PROPERTY_PROGRESS_EMAIL_TO_EMPLOYEE = "progress_email_to_employee";
	public static final String PROPERTY_JOINED_EMAIL_TO_EMPLOYEE = "joined_email_to_employee";
	public static final String PROPERTY_VALIDATE_CTC_AS_NUMERIC = "validate_ctc_as_numeric";
	public static final String PROPERTY_IS_EMPLOYEE_CODE_MANDATORY = "is_employee_code_mandatory";
	public static final String PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION = "overdue_duration_for_requisition_approval_notification";
	public static final String PROPERTY_SEND_REMINDER_TO_VENDOR = "send_reminder_to_vendor";
	public static final String PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION = "send_referent_employee_in_selection_process_notification";
	public static final String PROPERTY_USER_DISPLAY_IN_GRID_TEMPLATE = "user_display_in_grid_template";
	public static final String PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR = "vendor_resume_upload_notification_to_vendor";
	public static final String PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE = "employee_resume_upload_notification_to_employee";
	public static final String PROPERTY_POSITION_DISPLAY_IN_GRID_TEMPLATE = "position_display_in_grid_template";
	public static final String PROPERTY_OFFER_CODE_TEMPLATE = "offer_code_template";
	public static final String PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_CANDIDATE = "employee_resume_upload_notification_to_candidate";
	
	public static final String PROPERTY_ALL_EMPLOYEES_GROUP_EMAIL_ID="all_employees_group_email_id";
	public static final String PROPERTY_ALL_EMPLOYEES_HRGROUP_EMAIL_ID="all_employees_hrgroup_email_id";
	public static final String PROPERTY_SEND_POSITION_PUBLISHED_NOTIFICATION_TO_EMPLOYEES = "send_position_published_notification_to_employees";
	public static final String PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION = "send_position_change_notification";
	public static final String PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY = "enable_org_hierarchy_for_visibility";
	public static final String PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL = "send_email_notification_to_hr_for_employee_portal";
	public static final String PROPERTY_SEND_EMAIL_TO_HR_FOR_EMPLOYEE_PORTAL = "send_email_to_hr_for_employee_portal";
	// Budget Module Related
	public static final String PROPERTY_BUDGET_MODULE_STATUS = "budget_module_status";
	public static final String PROPERTY_BUDGET_MODE = "budget_mode";
	public static final String PROPERTY_BUDGET_ITEM_GRADE_LABEL="budget_item_grade_label";
	public static final String PROPERTY_BUDGET_ITEM_BAND_LABEL="budget_item_band_label";
	public static final String PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL="salary_variable_input_salary_label";
	
	public static final String PROPERTY_BUDGET_FOR_REPLACEMENT="budget_for_replacement";

	public static final String PROPERTY_BUSINESS_UNIT_PROPERTY="business_unit_property";
	public static final String PROPERTY_BUSINESS_UNIT_LABEL="business_unit_label";

	
	public static final String PROPERTY_COST_CENTER_PROPERTY="cost_center_property";
	public static final String PROPERTY_COST_CENTER_LABEL="cost_center_label";
	public static final String PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL="enable_bcc_while_sending_email";
	public static final String PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL="enable_hr_manager_cc_while_sending_email";
	
	public static final String PROPERTY_DEFAULT_DATEFORMAT="default_dateformat";
	public static final String PROPERTY_DEFAULT_TIMEFORMAT="default_timeformat";
	
	 
	public static final String PROPERTY_COPY_POSITION_WITH_CODE="copy_position_with_code";
	public static final String PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH ="show_joined_candidate_in_search";
	public static final String PROPERTY_LOCK_PERIOD_FOR_JOINED_CANDIDATE_SHORTLIST ="lock_period_for_joined_candidate_shortlist";
	public static final String PROPERTY_DEFAULT_NUMBER_OF_VACANCY = "default_number_of_vacancy";
	public static final String PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION ="apply_position_vacancy_restriction";
	
	public static final String ENABLED = "1";
	public static final String DISABLED = "0";
	
	public static final String ERROR="ERROR";
	public static final String CONFIDENTIAL_CHARACTER = TPLabels.getLabel("global.confidential_character");
	public static final Locale LOCALE= new Locale("en", "IN"); 
	
	public static final String PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID="offer_to_joined_step_id";
	
	public static final String PROPERTY_CAREERS_PAGE_URL="careers_page_url";
	
	public static final String PROPERTY_ALL_SELECTED_TIME_ZONES="all_selected_time_zones";
	
	public static final String PROPERTY_ALL_INTERVIEW_MODE="applicant_interview_mode";
	
	public static final String PROPERTY_IS_NAUKRI_INTEGRATION="is_naukri_integration";
	public static final String PROPERTY_HIRING_ORG_WEBSITE="hiring_org_website";
	public static final String PROPERTY_HIRING_ORG_NAME="hiring_org_name";
	public static final String PROPERTY_ORG_DESCRIPTION="org_description";
	public static final String PROPERTY_MICROSITE_NAME="microsite_name";
	public static final String PROPERTY_TEMPLATE_NAME="template_name";
	
	public static final String PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL="keep_HRmanager_cc_for_all_position_approval";
	public static final String PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_REJECTED_CANDIDATE="send_mail_to_recruiter_for_rejected_candidate";
	public static final String PROPERTY_SEND_EMAIL_TO_RECRUITER_FOR_ON_HOLD_CANDIDATE="send_mail_to_recruiter_for_On_Hold_candidate";
	public static final String PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS="send_mail_to_recruiter_for_candidate_status";
	public static final String PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL="send_mail_to_Hrmanager_Pending_Requisition_approval";
	public static final String PROPERTY_AUTO_APPROVE_REQUISITION="autoApproveRequisition";
	public static final String PROPERTY_DUPLICATE_POSITION_ALERT_NOTIFICATION_TO_ADMIN="duplicatePositionAlertNotificationToAdmin";
	// change for Sbi and Axis
	public static final String PROPERTY_SHOW_REASON_FOR_REJECT_CANDIDATE="show_reason_for_reject";
	public static final String PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING="send_mail_to_friend";
	public static final String PROPERTY_SHOW_STEP_DETAILS="show_step_details";
	public static final String PROPERTY_SHOW_STEP_DETAILS_VENDOR="show_step_details_for_vendor";
	 public static final String PROPERTY_SEND_REJECTED_CANDIDATE_NOTIFICATION_TO_RECRUITER = "rejectedCandidateMailToRecruiter";
}
