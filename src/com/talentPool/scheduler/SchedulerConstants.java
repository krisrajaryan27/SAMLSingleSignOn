/**
 * 
 */
package com.talentPool.scheduler;

/**
 * @author shivprasad
 * 
 */
public class SchedulerConstants {
	public static final String JOB_APPOINTMENT_REMAINDER = "JOB_APPOINTMENT_REMAINDER";
	public static final String JOB_FEEDBACK_REMAINDER = "JOB_FEEDBACK_REMAINDER";

	public static final String TRIGGER_APPOINTMENT_SUFIX = "APPT_";
	public static final String TRIGGER_APPOINTMENT_ME = "ME";
	public static final String TRIGGER_APPOINTMENT_INTERVIEWER = "IN";
	public static final String TRIGGER_APPOINTMENT_CANDIDATE = "CA";
	public static final String TRIGGER_APPOINTMENT_VENDOR = "VE";

	public static final String TRIGGER_FEEDBACK_SUFIX = "FBK_";

	public static final String JOB_INBOX = "JOB_INBOX";
	public static final String TRIGGER_INBOX_SUFIX = "INBOX";

	public static final String JOB_MASS_EMAIL = "JOB_MASS_EMAIL";
	public static final String TRIGGER_MASS_EMAIL = "MASS_EMAIL";

	public static final String JOB_AUTO_IMPORT = "JOB_AUTO_IMPORT";
	public static final String TRIGGER_AUTO_IMPORT = "AUTO_IMPORT";

	public static final String JOB_APPOINTMENT_NOTIFICATION = "JOB_APPOINTMENT_NOTIFICATION";
	public static final String TRIGGER_APPOINTMENT_NOTIFICATION = "APPOINTMENT_NOTIFICATION";

	public static final String JOB_SMS_REMAINDER = "JOB_SMS_REMAINDER";
	public static final String TRIGGER_SMS_SUFIX = "SMS_";

	public static final String JOB_AUTO_REPLY = "JOB_AUTO_REPLY";
	public static final String TRIGGER_AUTO_REPLY = "AUTO_REPLY";
	
	public static final String JOB_CANDIDATE_UPDATE = "JOB_CANDIDATE_UPDATE";
	public static final String TRIGGER_CANDIDATE_UPDATE = "CANDIDATE_UPDATE";
	
	public static final String JOB_CANDIDATE_APPLIED = "JOB_CANDIDATE_APPLIED";
	public static final String TRIGGER_CANDIDATE_APPLIED = "CANDIDATE_APPLIED";

	public static final String JOB_SOURCE_LOCKIN_CHECKER = "JOB_SOURCE_LOCKIN_CHECKER";
	public static final String TRIGGER_SOURCE_LOCKIN_CHECKER = "SOURCE_LOCKIN_CHECKER";
	
	public static final String JOB_VENDOR_DUPLICATE_ATTEMPT = "JOB_VENDOR_DUPLICATE_ATTEMPT";
	public static final String TRIGGER_VENDOR_DUPLICATE_ATTEMPT_SUFFIX = "DUPLICATE_ATTEMPT_";
	
	public static final String JOB_REQUISITION_APPROVAL_NOTIFICATION="JOB_REQUISITION_APPROVAL_NOTIFICATION";
	public static final String TRIGGER_REQUISITION_APPROVAL_NOTIFICATION_SUFFIX="REQUISITION_APPROVAL_NOTIFICATION_";
	
	public static final String JOB_REQUISITION_APPROVAL_NOTIFICATION_TO_USER="JOB_REQUISITION_APPROVAL_NOTIFICATION_TO_USER";
	public static final String TRIGGER_REQUISITION_APPROVAL_NOTIFICATION_TO_USER_SUFFIX="REQUISITION_APPROVAL_NOTIFICATION_TO_USER_";

	//  Start Report Scheduler
	public static final String JOB_REPORT_SCHEDULER = "JOB_REPORT_SCHEDULER";	
	public static final String TRIGGER_REPORT_SCHEDULER_SUFIX = "REPORT_SCHEDULER_";
	
	public static final String JOB_FEEDBACK_NOTIFICATION = "JOB_FEEDBACK_NOTIFICATION";
	public static final String TRIGGER_FEEDBACK_NOTIFICATION_SUFIX = "FBN_";
	// End Report Scheduler
 	
	public static final String JOB_VENDOR_REJECT_MAIL = "JOB_VENDOR_REJECT_MAIL";
	public static final String TRIGGER_VENDOR_REJECT_MAIL_SUFFIX = "REJECT_MAIL_";
	
		
	public static final String JOB_EMPLOYEE_REJECT_MAIL = "JOB_EMPLOYEE_REJECT_MAIL";
	public static final String TRIGGER_EMPLOYEE_REJECT_MAIL_SUFFIX = "EMPLOYEE_REJECT_MAIL_";

	public static final String JOB_EMPLOYEE_DUPLICATE_ATTEMPT = "JOB_EMPLOYEE_DUPLICATE_ATTEMPT";
	public static final String TRIGGER_EMPLOYEE_DUPLICATE_ATTEMPT_SUFFIX = "EMPLOYEE_DUPLICATE_ATTEMPT_";
	
	public static final String JOB_CANDIDATE_PROGRESS_NOTIFICATION = "JOB_CANDIDATE_PROGRESS_NOTIFICATION";
	public static final String TRIGGER_CANDIDATE_PROGRESS_NOTIFICATION_SUFIX = "CPN_";
	
	public static final String JOB_LOG_FILE_ARCHIVE = "JOB_LOG_FILE_ARCHIVE";
	public static final String TRIGGER_LOG_FILE_ARCHIVE = "LOG_FILE_ARCHIVE";
	
	public static final String JOB_BUDGET_OWNER_NOTIFICATION = "JOB_BUDGET_OWNER_NOTIFICATION";
	public static final String TRIGGER_BUDGET_OWNER_NOTIFICATION_SUFIX = "BON_";
	
	public static final String JOB_EMPLOYEE_PROGRESS_MAIL = "JOB_EMPLOYEE_PROGRESS_MAIL";
	public static final String TRIGGER_EMPLOYEE_PROGRESS_MAIL_SUFFIX = "EMPLOYEE_PROGRESS_MAIL_";
	
	public static final String JOB_ESCALATION_EMAIL = "JOB_ESCALATION_EMAIL";
	public static final String TRIGGER_ESCALATION_EMAIL = "ESCALATION_EMAIL";
	
	public static final String JOB_UPDATE_MASTER_TABLES = "JOB_UPDATE_MASTER_TABLES";
	public static final String TRIGGER_UPDATE_MASTER_TABLES = "UPDATE_MASTER_TABLES";
	
	public static final String JOB_REGENERATE_MASTER_TABLES = "JOB_REGENERATE_MASTER_TABLES";
	public static final String TRIGGER_REGENERATE_MASTER_TABLES = "REGENERATE_MASTER_TABLES";
	
	public static final String JOB_POSITION_AUTO_APPROVAL = "JOB_POSITION_AUTO_APPROVAL";
	public static final String TRIGGER_POSITION_AUTO_APPROVAL = "POSITION_AUTO_APPROVAL";
	
	/*Added for Asian Paints */
	public static final String JOB_ASIAN_PAINTS_MASTER = "JOB_ASIAN_PAINTS_MASTER";
	public static final String TRIGGER_ASIAN_PAINTS_MASTER = "TRIGGER_ASIAN_PAINTS_MASTER";
	// Scheduler UI Start
	public static final String SCHEDULER_LAST_STATUS_FAIL = "0";
	public static final String SCHEDULER_LAST_STATUS_SUCCESS = "1";
	
	public static final String SCHEDULER_CURRENT_STATUS_IDLE = "0";
	public static final String SCHEDULER_CURRENT_STATUS_RUNNING = "1";
	// Scheduler UI End
	
	//User Import
	public static final String JOB_UPDATE_USERS = "JOB_UPDATE_USERS";
	public static final String TRIGGER_UPDATE_USERS = "UPDATE_USERS";
}