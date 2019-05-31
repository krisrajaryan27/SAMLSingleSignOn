package com.talentPool.notifier;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

public class TemplateConstants {
	public static final String TEMPLATE_TYPE_GLOBAL = "1";
	public static final String TEMPLATE_TYPE_USERS = "2";
	public static final String TEMPLATE_TYPE_APPLICANTS = "3";
	
	/**
	 * This is a constant Which Will provide all Position related Variable (Including Custom Fields)
	 * 
	 */
	public static final String TEMPLATE_VAR_TYPE_POSITIONS = "18";
	
	public static final String TEMPLATE_VAR_TYPE_USER = "2";
 
	
	public static final String TEMPLATE_TYPE_USERS_TEXT = "Users";
	public static final String TEMPLATE_TYPE_APPLICANTS_TEXT = "Applicants";

	public static final String FORMAT_TEXT = "1";
	public static final String FORMAT_HTML = "0";

	public static final String TEMPLATE_SYSTEM = "0";
	public static final String TEMPLATE_GLOBAL = "1";
	public static final String TEMPLATE_PRIVATE = "2";

	public static final String AUTO_CREATED = "1";
	public static final String AUTO_NOT_CREATED = "0";

	public static final String POSTFIX_SUBJECT = "Subject.vm";
	public static final String POSTFIX_CONTENT = "Content.vm";
	
	public static String VELOCITY_LOG_PATH = "";
	public static String VELOCITY_TEMPLATE_DIR = "";
	static {
		String installationPath = TPApplicationProperties.getProperty("installation.path");
		String logDir = Utils.concatFilePath(installationPath, TPApplicationProperties.getProperty("log.dir", "./"));
		VELOCITY_LOG_PATH = Utils.concatFilePath(logDir, "velocity.log");
		VELOCITY_TEMPLATE_DIR = Utils.concatFilePath(installationPath, TPApplicationProperties.getProperty("templates.dir"));
	}

	public static final String regEEEEdMMMMyyyyFormat = "EEEE d MMMM yyyy zzz";
	public static final String regEEEdMMMyyyyhhmmaaaFormat = "EEE d MMM yyyy hh:mm aaa zzz";

	public static final String TEMPLATE_AUTO_REPLY_EMAIL = "autoReplyEmail";

	public static final String TEMPLATE_DEFAULT = "1";
	public static final String TEMPLATE_NOT_DEFAULT = "0";
	
	public static final String TEMPLATE_SAVE_AS_DRAFT = "1";
	public static final String TEMPLATE_DONT_SAVE_AS_DRAFT = "0";
	
	public static final String TEMPLATE_SHOW_SAVE_AS_DRAFT_OPTION = "1";
	public static final String TEMPLATE_DONT_SHOW_SAVE_AS_DRAFT_OPTION = "0";
	
	public static final String REPEAT = "&lt;REPEAT&gt;";
	public static final String END_REPEAT = "&lt;END REPEAT&gt;";
	
	public static final String TEXT_HAVING_REPEAT_TAG = "1";
	public static final String TEXT_NOT_HAVING_REPEAT_TAG = "0";

	public static final String TEMPLATE_TYPE_APPOINTMENT_REMINDER_APPLICANT = "1";
	public static final String TEMPLATE_TYPE_APPOINTMENT_REMINDER_INTERVIEWER = "2";
	public static final String TEMPLATE_TYPE_APPOINTMENT_REMINDER_OWNER = "3";
	public static final String TEMPLATE_TYPE_NEW_APPOINTMENT_NOTIFICATION = "4";
	public static final String TEMPLATE_TYPE_MODIFIED_APPOINTMENT_NOTIFICATION = "5";
	public static final String TEMPLATE_TYPE_SMS_REMINDER_APPLICANT = "6";
	public static final String TEMPLATE_TYPE_SMS_REMINDER_INTERVIEWER = "7";
	public static final String TEMPLATE_TYPE_SMS_REMINDER_OWNER = "8";
	public static final String TEMPLATE_TYPE_FEEDBACK_REMINDER_INTERVIEWER = "9";
	public static final String TEMPLATE_TYPE_EMAIL_MASS_EMAIL_APPLICANT = "10";
	public static final String TEMPLATE_TYPE_EMAIL_USER = "11";
	public static final String TEMPLATE_TYPE_AUTO_REPLY_EMAIL_TEMPLATE = "12";
	public static final String TEMPLATE_TYPE_CANCELLED_APPOINTMENT_NOTIFICATION = "13";
	public static final String TEMPLATE_TYPE_DUPLICATE_UPLOAD_TRIED_BY_VENDOR_NOTIFICATION = "14";
	public static final String TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION = "15";
	public static final String TEMPLATE_TYPE_FORGOT_PASSWORD = "16";
	public static final String TEMPLATE_TYPE_FEEDBACK_REMINDER_TO_ASSIGNED_PERSON = "17";
	public static final String TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION_TO_USER = "18";
	public static final String TEMPLATE_TYPE_VENDOR_RESUME_UPLOAD_NOTIFICATION12 = "19";
	public static final String TEMPLATE_TYPE_REJECTION_EMAIL_TO_VENDOR = "20";
	
	public static final String TEMPLATE_TYPE_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION = "21";
	public static final String TEMPLATE_TYPE_REJECTION_EMAIL_TO_EMPLOYEE = "22";	
	public static final String TEMPLATE_TYPE_DUPLICATE_UPLOAD_TRIED_BY_EMPLOYEE_NOTIFICATION = "23";
	
	public static final String TEMPLATE_TYPE_REJECTION_EMAIL_TO_CANDIDATE = "24";
	public static final String TEMPLATE_TYPE_PROGRESS_EMAIL_TO_CANDIDATE = "25";
	
	public static final String TEMPLATE_TYPE_PROGRESS_EMAIL_TO_EMPLOYEE = "27";
	public static final String TEMPLATE_TYPE_JOINED_EMAIL_TO_EMPLOYEE = "28";
	
	public static final String TEMPLATE_TYPE_BUDGET_ITEM_CREATION_NOTIFICATION_TO_OWNER="29";

	public static final String TEMPLATE_TYPE_BUDGET_ITEM_UPDATE_NOTIFICATION_TO_OWNER="30";

	public static final String TEMPLATE_TYPE_BUDGET_ITEM_DELETION_NOTIFICATION_TO_OWNER="31";

	public static final String TEMPLATE_TYPE_TRACK_MODE_VACANCY_NOTIFICATION_TO_OWNER="32";

	public static final String TEMPLATE_TYPE_TRACK_MODE_HIRE_BY_DATE_NOTIFICATION_TO_OWNER="33";

	public static final String TEMPLATE_TYPE_BUDGET_ITEM_HEADS_TRANSFER_NOTIFICATION_TO_OWNER="34";
	
	public static final String TEMPLATE_TYPE_OVERDUE_REQUISITION_APPROVAL_NOTIFICATION_TO_REQUESTOR="35";
	
	public static final String TEMPLATE_TYPE_APPOINTMENT_REMINDER_VENDOR = "36";
	
	public static final String TEMPLATE_TYPE_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION = "37";
	
	public static final String TEMPLATE_TYPE_POSITION_CHANGE_NOTIFICATION = "38";
	
	public static final String TEMPLATE_TYPE_POSITION_NOTIFICATION_TO_VENDOR = "39";
	
	public static final String TEMPLATE_TYPE_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR = "40";
	
	public static final String TEMPLATE_TYPE_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE = "41";
	
	public static final String TEMPLATE_TYPE_POSITION_NOTIFICATION_TO_EMPLOYEES = "42";
	
	public static final String TEMPLATE_TYPE_CONFIRMATION_LINK = "43";
	
	public static final String TEMPLATE_TYPE_SOCIAL_MEDIA= "44";
	
	public static final String TEMPLATE_TYPE_ON_HOLD_EMAIL_TO_RECRUITER = "56";
	public static final String TEMPLATE_TYPE_CANDIDATE_PROGRESS_TO_RECRUITER = "57";
	public static final String TEMPLATE_TYPE_TRACK_REQUISITION_APPROVAL = "58";
	public static final String TEMPLATE_TYPE_REQUISITION_APPROVAL_NOTIFICATION_TO_REQUESTED_BY = "59";
	public static final String TEMPLATE_TYPE_REQUISITION_PENDING_APPROVAL_NOTIFICATION_TO_HRMANAGER = "60";
	public static final String TEMPLATE_TYPE_POSITION_DUPLICATE_ALERT_NOTIFICATION_TO_ADMIN = "61";
	
	public static final String TEMPLATE_TYPE_CANDIDATE_PROFILE_UPDATE="62";
	public static final String TEMPLATE_TYPE_CANDIDATE_POSITION_APPLIED="63";
	
}
