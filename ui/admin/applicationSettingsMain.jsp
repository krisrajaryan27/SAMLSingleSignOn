<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.admin.AdminConstants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.admin.form.AdminForm"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.properties.GlobalConstants,
				com.talentPool.budget.BudgetConstants,
				com.talentPool.common.db.SimpleDataObject"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>

<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script type="text/javascript">
var cboDefaultAppointmentDuration=null;
var cboDefaultReminderDuration=null;
var cboDefaultFinancialMonthStart=null;
var cboDeptLevels=null;
var cboBudgetModuleStatus=null;
var cboBudgetMode=null;
var checkBoxListUsers=null;
var checkBoxListHrUsers=null;
var datePatternJsArray=null;
var timePatternJsArray=null;
var cboDefaultPasswordExpiryDuration=null;
var cboDefaultPasswordDifferentFromLast=null;
var cboOfferToJoinedDetailedSteps=null;
var cboDefaultPositionApprovalDuration=null;

var bu = null;
var costCenter = null;
</script>

<html:form action="/adminHome">
<html:hidden property="t" name="adminForm"/>
<html:hidden property="mode"/>
<input type="hidden" name="isSubmitted" value="1"/>

<%
	String attachmentSize = (String)request.getAttribute(GlobalConstants.PROPERTY_MAX_ATTACHMENT_SIZE_IN_MB);
	String noOfSkills= (String)request.getAttribute(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED);
	String vendor_resume_upload_limit= (String)request.getAttribute(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_PER_POSITION );
	String resultPageSize = (String)request.getAttribute(GlobalConstants.PROPERTY_SEARCH_RESULT_PAGE_SIZE);
	String durationAsNewResume = (String)request.getAttribute(GlobalConstants.PROPERTY_DURATION_AS_NEW_RESUME);
	String durationToDisplaySentMessages = (String)request.getAttribute(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_MESSAGES);
	String durationToDisplaySentEmails = (String)request.getAttribute(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS);
	String sendReminderToMe = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_REMINDER_TO_ME);
	String sendReminderToInterviewer = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_REMINDER_TO_INTERVIEWER);
	String sendReminderToCandidate = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_REMINDER_TO_CANDIDATE);
	String sendAppointment = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_APPOINTMENT);
	String defaultPasswordExpiryDuration = (String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_PASSWORD_EXPIRY_DURATION);
	String defaultPositionApprovalDuration = (String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_POSITION_APPROVAL_DURATION);
	String defaultPasswordDifferentFromLast = (String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_PASSWORD_DIFFERENT_FROM_LAST);
	String defaultAppointmentDuration = (String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_APPOINTMENT_DURATION);
	String defaultReminderDuration = (String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_REMINDER_DURATION);
	String sendFeedbackReminders = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS);
	String sendAutoReplyEmail = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL);
	String financial_year_start_month = (String)request.getAttribute(GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH);
	String max_dept_level = (String)request.getAttribute(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL);
	String notify_vendor_activity_to_hr = (String)request.getAttribute(GlobalConstants.PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR);
	String show_detailed_activity_to_vendor = (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
	String sendReminderOnDuplicateResumeUpload = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION);
	String sendEmailToHrForEmployeePortalUpload=(String)request.getAttribute(GlobalConstants.PROPERTY_SEND_EMAIL_TO_HR_FOR_EMPLOYEE_PORTAL);
	String sendEmailNotificationToHrForEmployeePortalUpload=(String)request.getAttribute(GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL);
	String sendReminderOnDuplicateResumeUploadToEmail = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL);	
	String showPositionCode = (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_POSITION_CODE);	
	String forcePositionCreationFromTemplate = (String)request.getAttribute(GlobalConstants.PROPERTY_FORCE_POSITION_CREATION_FROM_TEMPLATE);
	String departmentLevel1 = (String)request.getAttribute(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1);
	String departmentLevel2 = (String)request.getAttribute(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2);
	String departmentLevel3 = (String)request.getAttribute(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3);
	String departmentLevel4 = (String)request.getAttribute(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4);
	String departmentLevel5 = (String)request.getAttribute(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5);
	String careersPageUrl = (String)request.getAttribute(GlobalConstants.PROPERTY_CAREERS_PAGE_URL);
	String show_reminder = (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_REMINDER);
	String default_hire_by_duration_in_days = (String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_HIRE_BY_DURATION_IN_DAYS);
	String maximum_no_of_public_flags_allowed = (String)request.getAttribute(GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PUBLIC_FLAGS_ALLOWED);
	String maximum_no_of_private_flags_allowed = (String)request.getAttribute(GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PRIVATE_FLAGS_ALLOWED);
	String vendor_resume_upload_notification = (String)request.getAttribute(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION);
	String rejection_email_to_vendor = (String)request.getAttribute(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_VENDOR);
	String enable_single_sign_on = 	(String)request.getAttribute(GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON);
	String employee_resume_upload_notification = (String)request.getAttribute(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION);
	String rejection_email_to_employee = (String)request.getAttribute(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_EMPLOYEE);
	String rejection_email_to_candidate = (String)request.getAttribute(GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_CANDIDATE);
	String position_code_template = (String)request.getAttribute(GlobalConstants.PROPERTY_POSITION_CODE_TEMPLATE);
	String offer_code_template = (String)request.getAttribute(GlobalConstants.PROPERTY_OFFER_CODE_TEMPLATE);
	String allow_bulk_feedback = (String)request.getAttribute(GlobalConstants.PROPERTY_ALLOW_BULK_FEEDBACK);
	String candidate_progress_notification = (String)request.getAttribute(GlobalConstants.PROPERTY_NOTIFY_CANDIDATE_PROGRESS);
	String employee_can_apply_for_job = (String)request.getAttribute(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB);
	String show_bot_resistant_email_id = (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_BOT_RESISTANT_EMAIL_ID);
	String audit_trail_recovery_notification = (String)request.getAttribute(GlobalConstants.PROPERTY_AUDIT_TRAIL_RECOVERY_NOTIFICATION);
	String gridResultPageSize = (String)request.getAttribute(GlobalConstants.PROPERTY_GRID_RESULT_PAGE_SIZE);
	String progressEmailToEmployee = (String)request.getAttribute(GlobalConstants.PROPERTY_PROGRESS_EMAIL_TO_EMPLOYEE);
	String joinedEmailToEmployee = (String)request.getAttribute(GlobalConstants.PROPERTY_JOINED_EMAIL_TO_EMPLOYEE);
	String validateCtcAsNumeric = (String)request.getAttribute(GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC);
	String isEmployeeCodeMandatory = (String)request.getAttribute(GlobalConstants.PROPERTY_IS_EMPLOYEE_CODE_MANDATORY);
	String budget_module_status = (String)request.getAttribute(GlobalConstants.PROPERTY_BUDGET_MODULE_STATUS);
	String budget_mode = (String)request.getAttribute(GlobalConstants.PROPERTY_BUDGET_MODE);
	String budget_item_grade_label = (String)request.getAttribute(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL);
	String budget_item_band_label = (String)request.getAttribute(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL);
	String salary_variable_input_salary_label = (String)request.getAttribute(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL);
	String overdue_duration_for_requisition_approval_notification = (String)request.getAttribute(GlobalConstants.PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION);
	String send_reminder_to_vendor = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_REMINDER_TO_VENDOR);
	String send_referent_employee_in_selection_process_notification = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION);
	String user_display_in_grid_template = (String)request.getAttribute(GlobalConstants.PROPERTY_USER_DISPLAY_IN_GRID_TEMPLATE);
	String default_dateFormat = Utils.getBlankIfNull((String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_DATEFORMAT));
	String default_timeFormat = Utils.getBlankIfNull((String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_TIMEFORMAT));
	String position_display_in_grid_template = (String)request.getAttribute(GlobalConstants.PROPERTY_POSITION_DISPLAY_IN_GRID_TEMPLATE);
	String vendor_resume_upload_notification_to_vendor = (String)request.getAttribute(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR);
	String employee_resume_upload_notification_to_employee = (String)request.getAttribute(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE);
	/* String employee_resume_upload_notification_to_candidate = (String)request.getAttribute(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_CANDIDATE); */
	String all_employees_group_email_id = (String)request.getAttribute(GlobalConstants.PROPERTY_ALL_EMPLOYEES_GROUP_EMAIL_ID);
	String all_employees_hrgroup_email_id = (String)request.getAttribute(GlobalConstants.PROPERTY_ALL_EMPLOYEES_HRGROUP_EMAIL_ID);
	String send_position_published_notification_to_employees = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_POSITION_PUBLISHED_NOTIFICATION_TO_EMPLOYEES);
	String send_position_change_notification = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION);
	String duplicatePositionAlertNotificationToAdmin = (String)request.getAttribute(GlobalConstants.PROPERTY_DUPLICATE_POSITION_ALERT_NOTIFICATION_TO_ADMIN);
	String enable_org_hierarchy_for_visibility = (String)request.getAttribute(GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY);
	String enable_bcc_while_sending_email = (String)request.getAttribute(GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL);
	String enable_hr_manager_cc_while_sending_email= (String)request.getAttribute(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL);
	String keep_HRmanager_cc_for_all_position_approval= (String)request.getAttribute(GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL);
	String send_mail_to_recruiter_for_rejected_candidate= (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_REJECTED_CANDIDATE);
	String send_mail_to_recruiter_for_On_Hold_candidate= (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_EMAIL_TO_RECRUITER_FOR_ON_HOLD_CANDIDATE);
	String send_mail_to_Hrmanager_Pending_Requisition_approval= (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL);
	String send_mail_to_recruiter_for_candidate_status= (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS);
	String show_reason_for_reject= (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_REASON_FOR_REJECT_CANDIDATE);
	String show_step_details= (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_STEP_DETAILS);
	String show_step_details_for_vendor= (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_STEP_DETAILS_VENDOR);
	String send_mail_to_friend= (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING);
	String autoApproveRequisition= (String)request.getAttribute(GlobalConstants.PROPERTY_AUTO_APPROVE_REQUISITION);
	
	String rejectedCandidateMailToRecruiter = (String)request.getAttribute(GlobalConstants.PROPERTY_SEND_REJECTED_CANDIDATE_NOTIFICATION_TO_RECRUITER);
	
	

	
	String businessUnitProperty = (String)request.getAttribute(GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY);
	String businessUnitLabel = (String)request.getAttribute(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL);
	String costCenterProperty = (String)request.getAttribute(GlobalConstants.PROPERTY_COST_CENTER_PROPERTY);
	String costCenterLabel = (String)request.getAttribute(GlobalConstants.PROPERTY_COST_CENTER_LABEL);	
	String budgetForReplacement = (String)request.getAttribute(GlobalConstants.PROPERTY_BUDGET_FOR_REPLACEMENT);
	String copyPositionWithCode = (String)request.getAttribute(GlobalConstants.PROPERTY_COPY_POSITION_WITH_CODE);
	String showJoinedCandidateInSearch = (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH);
	String lockPeriodForJoinedCandidateShortlist = (String)request.getAttribute(GlobalConstants.PROPERTY_LOCK_PERIOD_FOR_JOINED_CANDIDATE_SHORTLIST);
	String defaultNumberOfVacancy = (String)request.getAttribute(GlobalConstants.PROPERTY_DEFAULT_NUMBER_OF_VACANCY);
	String offer_to_joined_step_id = (String)request.getAttribute(GlobalConstants.PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID);
	String applyPositionVacancyRestriction = (String)request.getAttribute(GlobalConstants.PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION);
	
	String isNaukriIntegration = (String)request.getAttribute(GlobalConstants.PROPERTY_IS_NAUKRI_INTEGRATION);
	String hiringOrgWebsite = (String)request.getAttribute(GlobalConstants.PROPERTY_HIRING_ORG_WEBSITE);
	String hiringOrgName = (String)request.getAttribute(GlobalConstants.PROPERTY_HIRING_ORG_NAME);
	String orgDescription = (String)request.getAttribute(GlobalConstants.PROPERTY_ORG_DESCRIPTION);
	String micrositeName = (String)request.getAttribute(GlobalConstants.PROPERTY_MICROSITE_NAME);
	String templateName = (String)request.getAttribute(GlobalConstants.PROPERTY_TEMPLATE_NAME);
	
	hiringOrgWebsite = Utils.isBlankOrNull(hiringOrgWebsite)?"":hiringOrgWebsite;
	hiringOrgName = Utils.isBlankOrNull(hiringOrgName)?"":hiringOrgName;
	orgDescription = Utils.isBlankOrNull(orgDescription)?"":orgDescription;
	micrositeName = Utils.isBlankOrNull(micrositeName)?"":micrositeName;	
	templateName = Utils.isBlankOrNull(templateName)?"":templateName;
	
	attachmentSize = Utils.isBlankOrNull(attachmentSize)?"":attachmentSize;
	noOfSkills = Utils.isBlankOrNull(noOfSkills)?"":noOfSkills;
	resultPageSize = Utils.isBlankOrNull(resultPageSize)?"":resultPageSize;
	vendor_resume_upload_limit = Utils.isBlankOrNull(vendor_resume_upload_limit)?"":vendor_resume_upload_limit;	
	durationAsNewResume = Utils.isBlankOrNull(durationAsNewResume)?"":durationAsNewResume;	
	sendReminderToMe = Utils.isBlankOrNull(sendReminderToMe)?"":sendReminderToMe;
	sendReminderToInterviewer = Utils.isBlankOrNull(sendReminderToInterviewer)?"":sendReminderToInterviewer;
	sendReminderToCandidate = Utils.isBlankOrNull(sendReminderToCandidate)?"":sendReminderToCandidate;
	sendAppointment = Utils.isBlankOrNull(sendAppointment)?"":sendAppointment;
	defaultAppointmentDuration = Utils.isBlankOrNull(defaultAppointmentDuration)?"":defaultAppointmentDuration;
	defaultReminderDuration = Utils.isBlankOrNull(defaultReminderDuration)?"":defaultReminderDuration;
	sendFeedbackReminders = Utils.isBlankOrNull(sendFeedbackReminders)?"":sendFeedbackReminders;
	sendAutoReplyEmail = Utils.isBlankOrNull(sendAutoReplyEmail)?"":sendAutoReplyEmail;	
	departmentLevel1 = Utils.isBlankOrNull(departmentLevel1)?"":departmentLevel1;
	departmentLevel2 = Utils.isBlankOrNull(departmentLevel2)?"":departmentLevel2;
	departmentLevel3 = Utils.isBlankOrNull(departmentLevel3)?"":departmentLevel3;
	departmentLevel4 = Utils.isBlankOrNull(departmentLevel4)?"":departmentLevel4;
	departmentLevel5 = Utils.isBlankOrNull(departmentLevel5)?"":departmentLevel5;
	careersPageUrl = Utils.isBlankOrNull(careersPageUrl)?"":careersPageUrl;
	
	default_hire_by_duration_in_days = Utils.isBlankOrNull(default_hire_by_duration_in_days)?"":default_hire_by_duration_in_days;
	maximum_no_of_public_flags_allowed = Utils.isBlankOrNull(maximum_no_of_public_flags_allowed)?"":maximum_no_of_public_flags_allowed;
	maximum_no_of_private_flags_allowed = Utils.isBlankOrNull(maximum_no_of_private_flags_allowed)?"":maximum_no_of_private_flags_allowed;
	position_code_template = Utils.isBlankOrNull(position_code_template)?"":position_code_template;
	offer_code_template = Utils.isBlankOrNull(offer_code_template)?"":offer_code_template;
	allow_bulk_feedback = Utils.isBlankOrNull(allow_bulk_feedback)?"":allow_bulk_feedback;
	candidate_progress_notification = Utils.isBlankOrNull(candidate_progress_notification)?"":candidate_progress_notification;
	employee_can_apply_for_job = Utils.isBlankOrNull(employee_can_apply_for_job)?"":employee_can_apply_for_job;
	show_bot_resistant_email_id = Utils.isBlankOrNull(show_bot_resistant_email_id)?"":show_bot_resistant_email_id;
	audit_trail_recovery_notification = Utils.isBlankOrNull(audit_trail_recovery_notification)?"":audit_trail_recovery_notification;
	gridResultPageSize = Utils.isBlankOrNull(gridResultPageSize)?"":gridResultPageSize;
	progressEmailToEmployee = Utils.isBlankOrNull(progressEmailToEmployee)?"":progressEmailToEmployee;
	joinedEmailToEmployee = Utils.isBlankOrNull(joinedEmailToEmployee)?"":joinedEmailToEmployee;
	validateCtcAsNumeric = Utils.isBlankOrNull(validateCtcAsNumeric)?"":validateCtcAsNumeric;
	isEmployeeCodeMandatory = Utils.isBlankOrNull(isEmployeeCodeMandatory)?"":isEmployeeCodeMandatory;
	budget_module_status = Utils.isBlankOrNull(budget_module_status)?"":budget_module_status;
	budget_mode = Utils.isBlankOrNull(budget_mode)?"":budget_mode;
	budget_item_grade_label=Utils.isBlankOrNull(budget_item_grade_label)?"":budget_item_grade_label;
	budget_item_band_label=Utils.isBlankOrNull(budget_item_band_label)?"":budget_item_band_label;
	salary_variable_input_salary_label=Utils.getBlankIfNull(salary_variable_input_salary_label);
	overdue_duration_for_requisition_approval_notification=Utils.isBlankOrNull(overdue_duration_for_requisition_approval_notification)?"":overdue_duration_for_requisition_approval_notification;
	send_reminder_to_vendor = Utils.isBlankOrNull(send_reminder_to_vendor)?"":send_reminder_to_vendor;
	send_referent_employee_in_selection_process_notification = Utils.isBlankOrNull(send_referent_employee_in_selection_process_notification)?"":send_referent_employee_in_selection_process_notification;
	user_display_in_grid_template = Utils.isBlankOrNull(user_display_in_grid_template)?"":user_display_in_grid_template;
	position_display_in_grid_template = Utils.isBlankOrNull(position_display_in_grid_template)?"":position_display_in_grid_template;
	vendor_resume_upload_notification_to_vendor = Utils.isBlankOrNull(vendor_resume_upload_notification_to_vendor)?"":vendor_resume_upload_notification_to_vendor;
	employee_resume_upload_notification_to_employee = Utils.isBlankOrNull(employee_resume_upload_notification_to_employee)?"":employee_resume_upload_notification_to_employee;
	/* employee_resume_upload_notification_to_candidate = Utils.isBlankOrNull(employee_resume_upload_notification_to_candidate)?"":employee_resume_upload_notification_to_candidate;
	 */
	
	all_employees_group_email_id = Utils.isBlankOrNull(all_employees_group_email_id)?"":all_employees_group_email_id;
	all_employees_hrgroup_email_id = Utils.isBlankOrNull(all_employees_hrgroup_email_id)?"":all_employees_hrgroup_email_id;
	send_position_published_notification_to_employees = Utils.isBlankOrNull(send_position_published_notification_to_employees)?"":send_position_published_notification_to_employees;
	send_position_change_notification = Utils.isBlankOrNull(send_position_change_notification)?"":send_position_change_notification;
	duplicatePositionAlertNotificationToAdmin = Utils.isBlankOrNull(duplicatePositionAlertNotificationToAdmin)?"":duplicatePositionAlertNotificationToAdmin;
	
	enable_org_hierarchy_for_visibility = Utils.isBlankOrNull(enable_org_hierarchy_for_visibility)?"":enable_org_hierarchy_for_visibility;
	enable_bcc_while_sending_email = Utils.isBlankOrNull(enable_bcc_while_sending_email)?"":enable_bcc_while_sending_email;
	enable_hr_manager_cc_while_sending_email = Utils.isBlankOrNull(enable_hr_manager_cc_while_sending_email)?"":enable_hr_manager_cc_while_sending_email;
	keep_HRmanager_cc_for_all_position_approval = Utils.isBlankOrNull(keep_HRmanager_cc_for_all_position_approval)?"":keep_HRmanager_cc_for_all_position_approval;
	send_mail_to_recruiter_for_rejected_candidate = Utils.isBlankOrNull(send_mail_to_recruiter_for_rejected_candidate)?"":send_mail_to_recruiter_for_rejected_candidate;
	send_mail_to_recruiter_for_On_Hold_candidate = Utils.isBlankOrNull(send_mail_to_recruiter_for_On_Hold_candidate)?"":send_mail_to_recruiter_for_On_Hold_candidate;
	send_mail_to_recruiter_for_candidate_status = Utils.isBlankOrNull(send_mail_to_recruiter_for_candidate_status)?"":send_mail_to_recruiter_for_candidate_status;
	show_reason_for_reject = Utils.isBlankOrNull(show_reason_for_reject)?"":show_reason_for_reject;
	show_step_details = Utils.isBlankOrNull(show_step_details)?"":show_step_details;
	show_step_details_for_vendor = Utils.isBlankOrNull(show_step_details_for_vendor)?"":show_step_details_for_vendor;
	send_mail_to_friend = Utils.isBlankOrNull(send_mail_to_friend)?"":send_mail_to_friend;
	rejectedCandidateMailToRecruiter = Utils.isBlankOrNull(rejectedCandidateMailToRecruiter)?"":rejectedCandidateMailToRecruiter;
	send_mail_to_Hrmanager_Pending_Requisition_approval = Utils.isBlankOrNull(send_mail_to_Hrmanager_Pending_Requisition_approval)?"":send_mail_to_Hrmanager_Pending_Requisition_approval;
	autoApproveRequisition = Utils.isBlankOrNull(autoApproveRequisition)?"":autoApproveRequisition;
	
	
	businessUnitLabel = Utils.isBlankOrNull(businessUnitLabel)?"":businessUnitLabel;
	costCenterLabel = Utils.isBlankOrNull(costCenterLabel)?"":costCenterLabel;
	lockPeriodForJoinedCandidateShortlist = Utils.isBlankOrNull(lockPeriodForJoinedCandidateShortlist)?"":lockPeriodForJoinedCandidateShortlist;
	defaultNumberOfVacancy = Utils.isBlankOrNull(defaultNumberOfVacancy)?"":defaultNumberOfVacancy;
%>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_ME %>" value="<%=sendReminderToMe %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_INTERVIEWER %>" value="<%=sendReminderToInterviewer %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_CANDIDATE %>" value="<%=sendReminderToCandidate %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_APPOINTMENT %>" value="<%=sendAppointment %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DEFAULT_APPOINTMENT_DURATION %>" value="<%=defaultAppointmentDuration %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DEFAULT_PASSWORD_EXPIRY_DURATION %>" value="<%=defaultPasswordExpiryDuration %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DEFAULT_POSITION_APPROVAL_DURATION %>" value="<%=defaultPositionApprovalDuration %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DEFAULT_PASSWORD_DIFFERENT_FROM_LAST %>" value="<%=defaultPasswordDifferentFromLast %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DEFAULT_REMINDER_DURATION %>" value="<%=defaultReminderDuration %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS %>" value="<%=sendFeedbackReminders %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL %>" value="<%=sendAutoReplyEmail %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH %>" value="<%=financial_year_start_month %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_MAX_DEPT_LEVEL %>" value="<%=max_dept_level %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR %>" value="<%=notify_vendor_activity_to_hr %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR %>" value="<%=show_detailed_activity_to_vendor %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION %>" value="<%=sendReminderOnDuplicateResumeUpload %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL %>" value="<%=sendReminderOnDuplicateResumeUploadToEmail %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_EMAIL_TO_HR_FOR_EMPLOYEE_PORTAL %>" value="<%=sendEmailToHrForEmployeePortalUpload %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL %>" value="<%=sendEmailNotificationToHrForEmployeePortalUpload %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_POSITION_CODE%>" value="<%=showPositionCode%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_FORCE_POSITION_CREATION_FROM_TEMPLATE%>" value="<%=forcePositionCreationFromTemplate%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_REMINDER %>" value="<%=show_reminder %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION %>" value="<%=vendor_resume_upload_notification %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_VENDOR %>" value="<%=rejection_email_to_vendor %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON %>" value="<%=enable_single_sign_on %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION %>" value="<%=employee_resume_upload_notification %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_EMPLOYEE %>" value="<%=rejection_email_to_employee %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_CANDIDATE %>" value="<%=rejection_email_to_candidate %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_ALLOW_BULK_FEEDBACK %>" value="<%=allow_bulk_feedback %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_NOTIFY_CANDIDATE_PROGRESS%>" value="<%=candidate_progress_notification %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB%>" value="<%=employee_can_apply_for_job %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_BOT_RESISTANT_EMAIL_ID%>" value="<%=show_bot_resistant_email_id %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_AUDIT_TRAIL_RECOVERY_NOTIFICATION%>" value="<%=audit_trail_recovery_notification%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_PROGRESS_EMAIL_TO_EMPLOYEE%>" value="<%=progressEmailToEmployee%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_JOINED_EMAIL_TO_EMPLOYEE%>" value="<%=joinedEmailToEmployee%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC%>" value="<%=validateCtcAsNumeric%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_IS_EMPLOYEE_CODE_MANDATORY%>" value="<%=isEmployeeCodeMandatory%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_BUDGET_MODULE_STATUS%>" value="<%=budget_module_status %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_BUDGET_MODE%>" value="<%=budget_mode%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_VENDOR %>" value="<%=send_reminder_to_vendor %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION %>" value="<%=send_referent_employee_in_selection_process_notification %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR %>" value="<%=vendor_resume_upload_notification_to_vendor %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE %>" value="<%=employee_resume_upload_notification_to_employee %>"/>
<%-- <input type="hidden" name="<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_CANDIDATE %>" value="<%=employee_resume_upload_notification_to_candidate %>"/>
 --%>

<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_POSITION_PUBLISHED_NOTIFICATION_TO_EMPLOYEES %>" value="<%=send_position_published_notification_to_employees %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION %>" value="<%=send_position_change_notification %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DUPLICATE_POSITION_ALERT_NOTIFICATION_TO_ADMIN %>" value="<%=duplicatePositionAlertNotificationToAdmin %>"/>

<input type="hidden" name="<%=GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY %>" value="<%=enable_org_hierarchy_for_visibility %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL %>" value="<%=enable_bcc_while_sending_email %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL %>" value="<%=enable_hr_manager_cc_while_sending_email %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL %>" value="<%=keep_HRmanager_cc_for_all_position_approval %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_REJECTED_CANDIDATE %>" value="<%=send_mail_to_recruiter_for_rejected_candidate %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_EMAIL_TO_RECRUITER_FOR_ON_HOLD_CANDIDATE %>" value="<%=send_mail_to_recruiter_for_On_Hold_candidate %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS %>" value="<%=send_mail_to_recruiter_for_candidate_status %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL %>" value="<%=send_mail_to_Hrmanager_Pending_Requisition_approval %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_AUTO_APPROVE_REQUISITION %>" value="<%=autoApproveRequisition %>"/>



<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_REASON_FOR_REJECT_CANDIDATE %>" value="<%=show_reason_for_reject %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING %>" value="<%=send_mail_to_friend %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_STEP_DETAILS %>" value="<%=show_step_details %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_STEP_DETAILS_VENDOR %>" value="<%=show_step_details_for_vendor %>"/>

<input type="hidden" name="<%=GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY %>" value="<%=businessUnitProperty %>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_COST_CENTER_PROPERTY %>" value="<%=costCenterProperty%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_BUDGET_FOR_REPLACEMENT %>" value="<%=budgetForReplacement%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DEFAULT_DATEFORMAT %>" value="<%=default_dateFormat%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_DEFAULT_TIMEFORMAT %>" value="<%=default_timeFormat%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_COPY_POSITION_WITH_CODE %>" value="<%=copyPositionWithCode%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH %>" value="<%=showJoinedCandidateInSearch%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID %>" value="<%=offer_to_joined_step_id%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION %>" value="<%=applyPositionVacancyRestriction%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_IS_NAUKRI_INTEGRATION %>" value="<%=isNaukriIntegration%>"/>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SEND_REJECTED_CANDIDATE_NOTIFICATION_TO_RECRUITER %>" value="<%=rejectedCandidateMailToRecruiter %>"/>

<div class="contentDiv">
	<div id="divError" style="display:block">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
	<script>
		var isError=1;
	</script>
	<table  id="m_errortable" > 
		<tr>
		  <td class="header">
		    <b><bean:message key="errors.following_errors"/></b>
		  </td>               
		</tr>
		<tr>
		    <td class="message"><html:errors/></td>               
		</tr>
	</table>
	<br/>
	<% } %>
	<%
		String saved = (String)request.getAttribute("update");
		if(saved !=null){
	%>
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b>
			        <bean:message key="admin_application_settings.label.settings_updated_successfully"/>			        
			        </b>
			    </td>               
				</tr>
			</table>
			<br/>
	<%
		}
	%>
	</div>
</div>
<div class="contentDivPop" style="padding-right:20px;">
	<table class="boxHeader" style="margin-top:5px; width: 100%;border-spacing: 0; padding: 0;">
		<tr>
			<td class="header" height="18"><strong><bean:message key="admin_application_settings.label.application_settings" /></strong></td>
		</tr>
	</table>
	<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
		<table style="border: 0; border-spacing: 0; padding: 0" class="posinput">
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.max_attachment_size"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_MAX_ATTACHMENT_SIZE_IN_MB %>" value="<%=attachmentSize %>" size="2" maxlength="2"/>
			  </td>
			</tr>
		    <tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.max_parsed_skills"/>
			  </td>
			  <td></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_MAX_SKILLS_PARSED %>" value="<%=noOfSkills %>" size="2" maxlength="2"/>
			  </td>
			</tr>						   
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.search_result_page_size"/>
			  </td>						      
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_SEARCH_RESULT_PAGE_SIZE %>" value="<%=resultPageSize %>" size="2" maxlength="3"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.grid_result_page_size"/>
			  </td>						      
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_GRID_RESULT_PAGE_SIZE %>" value="<%=gridResultPageSize %>" size="5" maxlength="5"/>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.vendor_resume_upload_limit"/>
			  </td>						      
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_PER_POSITION %>" value="<%=vendor_resume_upload_limit %>" size="2" maxlength="3"/>
			  </td>
			</tr>						  
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.days_as_new_applicant"/>
			  </td>						      
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DURATION_AS_NEW_RESUME %>" value="<%=durationAsNewResume %>" size="2" maxlength="3"/>
			  </td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.duration_to_display_sent_messages"/>
			  </td>						      
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_MESSAGES %>" value="<%=durationToDisplaySentMessages %>" size="2" maxlength="3"/>
			  </td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.duration_to_display_sent_emails"/>
			  </td>						      
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS %>" value="<%=durationToDisplaySentEmails %>" size="2" maxlength="3"/>
			  </td>
			</tr>					
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="default_hire_by_duration_in_days"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DEFAULT_HIRE_BY_DURATION_IN_DAYS%>" value="<%=default_hire_by_duration_in_days%>" size="2" maxlength="2"/>
			  </td>
			</tr>	
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.maximum_no_of_public_flags_allowed"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PUBLIC_FLAGS_ALLOWED%>" value="<%=maximum_no_of_public_flags_allowed%>" size="2" maxlength="2"/>
			  </td>
			</tr>	
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.maximum_no_of_private_flags_allowed"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_MAXIMUM_NO_OF_PRIVATE_FLAGS_ALLOWED%>" value="<%=maximum_no_of_private_flags_allowed%>" size="2" maxlength="2"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.overdue_duration_for_requisition_approval_notification"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_OVERDUE_DURATION_FOR_REQUISITION_APPROVAL_NOTIFICATION%>"  value="<%=overdue_duration_for_requisition_approval_notification%>" size="2" maxlength="2"/>
			  </td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.enable_single_signon"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(enable_single_sign_on)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_SINGLE_SIGN_ON %>);" />
				  <%} %>
			  </td>
			</tr>						   
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_reminders_to_me"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(sendReminderToMe)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_ME %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_ME %>);" />
				  <%} %>
			  </td>
			</tr>						   
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_reminders_to_interviewer"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(sendReminderToInterviewer)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_INTERVIEWER %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_INTERVIEWER %>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_reminders_to_candidate"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(sendReminderToCandidate)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_CANDIDATE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_CANDIDATE %>);" />
				  <%} %>
			  
			  </td>
			</tr>	
			
			<% if(ModuleSet.isMODULE_OUTLOOK_MEETING_REQUEST()){ %>								   
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_appointment_notification"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(sendAppointment)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_APPOINTMENT %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_APPOINTMENT %>);" />
				  <%} %>
			  
			  </td>
			</tr>
			<% } %>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_feedback_reminders"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(sendFeedbackReminders)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS %>);" />
				  <%} %>
			  
			  </td>
			</tr>
			<% if(ModuleSet.isMODULE_AUTO_RESPONSE_EMAIL()){ %>								   
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_auto_reply_email"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(sendAutoReplyEmail)){ %>
				  		<img src="images/checkboxchecked.gif" id="sendAutoReplyEmail" name="sendAutoReplyEmail" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="sendAutoReplyEmail" name="sendAutoReplyEmail" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL %>);" />
				  <%} %>
			  
			  </td>
			</tr>
			<% } %>			
			
			
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="common.show"/>&nbsp;<bean:message key="common.position_code"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
			  	  <% if("1".equals(showPositionCode)){ %>
				  		<img src="images/checkboxchecked.gif" id="showPositionCode" name="showPositionCode" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_POSITION_CODE %>);" />				  		
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="showPositionCode" name="showPositionCode" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_POSITION_CODE%>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.force_position_creation_from_template"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
			  	  <% if("1".equals(forcePositionCreationFromTemplate)){ %>
				  		<img src="images/checkboxchecked.gif" id="forcePositionCreationFromTemplate" name="forcePositionCreationFromTemplate" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_FORCE_POSITION_CREATION_FROM_TEMPLATE %>);" />				  		
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="forcePositionCreationFromTemplate" name="forcePositionCreationFromTemplate" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_FORCE_POSITION_CREATION_FROM_TEMPLATE%>);" />
				  <%} %>
			  </td>
			</tr>			
			<tr>
			   <td class="label" style="width:400px;">
			   <bean:message key="admin_application_settings.label.rejection_email_to_candidate"/>				
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(rejection_email_to_candidate)){ %>
				  		<img src="images/checkboxchecked.gif" id="rejection_email_to_candidate" name="rejection_email_to_candidate" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_CANDIDATE%>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="rejection_email_to_candidate" name="rejection_email_to_candidate"	onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_CANDIDATE%>);" />
				  <%} %>			  
			  </td>
			</tr>			
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.show_reminders_with_calendar"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(show_reminder)){ %>
				  		<img src="images/checkboxchecked.gif" id="show_reminder" name="show_reminder" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_REMINDER %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="show_reminder" name="show_reminder" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_REMINDER %>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.cadidate_progress_notification"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if(GlobalConstants.ENABLED.equalsIgnoreCase(candidate_progress_notification)){ %>
				  		<img src="images/checkboxchecked.gif" id="candidate_progress_notification" name="candidate_progress_notification" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_NOTIFY_CANDIDATE_PROGRESS %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="candidate_progress_notification" name="candidate_progress_notification" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_NOTIFY_CANDIDATE_PROGRESS %>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.employee_can_apply_for_job"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if(GlobalConstants.ENABLED.equalsIgnoreCase(employee_can_apply_for_job)){ %>
				  		<img src="images/checkboxchecked.gif" id="employee_can_apply_for_job" name="employee_can_apply_for_job" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="employee_can_apply_for_job" name="employee_can_apply_for_job" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB %>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.show_bot_resistant_email_id"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if(GlobalConstants.ENABLED.equalsIgnoreCase(show_bot_resistant_email_id)){ %>
				  		<img src="images/checkboxchecked.gif" id="show_bot_resistant_email_id" name="show_bot_resistant_email_id" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_BOT_RESISTANT_EMAIL_ID %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="show_bot_resistant_email_id" name="show_bot_resistant_email_id" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_BOT_RESISTANT_EMAIL_ID %>);" />
				  <%} %>
			  </td>
			</tr>			
			<tr>
				<td class="label" style="width:400px;">
					<bean:message key="admin_application_settings.label.audit_trail_recovery_notification"/>
				</td>						      
				<td ></td>
				<td height="20">
 					<% if(GlobalConstants.ENABLED.equalsIgnoreCase(audit_trail_recovery_notification)){ %>
						<img src="images/checkboxchecked.gif" id="audit_trail_recovery_notification" name="audit_trail_recovery_notification" 
						onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_AUDIT_TRAIL_RECOVERY_NOTIFICATION %>);" />
					<%}else{ %>
						<img src="images/checkboxunchecked.gif" id="audit_trail_recovery_notification" name="audit_trail_recovery_notification" 
						onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_AUDIT_TRAIL_RECOVERY_NOTIFICATION %>);" />
					<%} %>
  				</td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			   <bean:message key="admin_application_settings.label.allow_bulk_feedback"/>				
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(allow_bulk_feedback)){ %>
				  		<img src="images/checkboxchecked.gif" id="allow_bulk_feedback" name="allow_bulk_feedback" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ALLOW_BULK_FEEDBACK%>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="allow_bulk_feedback" name="allow_bulk_feedback"	onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ALLOW_BULK_FEEDBACK%>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.validate_ctc_as_numeric"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if("1".equals(validateCtcAsNumeric)){ %>
				  		<img src="images/checkboxchecked.gif" id="validate_ctc_as_numeric" name="validate_ctc_as_numeric" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="validate_ctc_as_numeric" name="validate_ctc_as_numeric" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC %>);" />
				  <%} %>
			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.is_employee_code_mandatory"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if(GlobalConstants.ENABLED.equals(isEmployeeCodeMandatory)){ %>
				  		<img src="images/checkboxchecked.gif" id="is_employee_code_mandatory" name="is_employee_code_mandatory" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_IS_EMPLOYEE_CODE_MANDATORY %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="is_employee_code_mandatory" name="is_employee_code_mandatory" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_IS_EMPLOYEE_CODE_MANDATORY %>);" />
				  <%} %>
			  
			  </td>
			</tr>		
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_referent_employee_in_selection_process_notification"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if("1".equals(send_referent_employee_in_selection_process_notification)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_referent_employee_in_selection_process_notification" name="send_referent_employee_in_selection_process_notification" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_referent_employee_in_selection_process_notification" name="send_referent_employee_in_selection_process_notification" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REFERENT_EMPLOYEE_IN_SELECTION_PROCESS_NOTIFICATION %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_position_change_notification"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if("1".equals(send_position_change_notification)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_position_change_notification" name="send_position_change_notification" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_position_change_notification" name="send_position_change_notification" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_duplicate_position_notification_to_admin"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if("1".equals(duplicatePositionAlertNotificationToAdmin)){ %>
				  		<img src="images/checkboxchecked.gif" id="duplicatePositionAlertNotificationToAdmin" name="duplicatePositionAlertNotificationToAdmin" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_DUPLICATE_POSITION_ALERT_NOTIFICATION_TO_ADMIN %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="duplicatePositionAlertNotificationToAdmin" name="duplicatePositionAlertNotificationToAdmin" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_DUPLICATE_POSITION_ALERT_NOTIFICATION_TO_ADMIN %>);" />
				  <%} %>			  
			  </td>
			</tr>
			
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.enable_org_hierarchy_for_visibility"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if("1".equals(enable_org_hierarchy_for_visibility)){ %>
				  		<img src="images/checkboxchecked.gif" id="enable_org_hierarchy_for_visibility" name="enable_org_hierarchy_for_visibility" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="enable_org_hierarchy_for_visibility" name="enable_org_hierarchy_for_visibility" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.enable_bcc_while_sending_email"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(enable_bcc_while_sending_email)){ %>
				  		<img src="images/checkboxchecked.gif" id="enable_bcc_while_sending_email" name="enable_bcc_while_sending_email" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="enable_bcc_while_sending_email" name="enable_bcc_while_sending_email" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL %>);" />
				  <%} %>			  
			  </td>
			</tr>
			
			
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.enable_HRmanager_cc_while_sending_email"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(enable_hr_manager_cc_while_sending_email)){ %>
				  		<img src="images/checkboxchecked.gif" id="enable_hr_manager_cc_while_sending_email" name="enable_hr_manager_cc_while_sending_email" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="enable_hr_manager_cc_while_sending_email" name="enable_hr_manager_cc_while_sending_email" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_WHILE_SENDING_EMAIL %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.keep_HRmanager_cc_for_all_position_approval"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(keep_HRmanager_cc_for_all_position_approval)){ %>
				  		<img src="images/checkboxchecked.gif" id="keep_HRmanager_cc_for_all_position_approval" name="keep_HRmanager_cc_for_all_position_approval" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="keep_HRmanager_cc_for_all_position_approval" name="keep_HRmanager_cc_for_all_position_approval" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_ENABLE_CC_HR_MANAGER_FOR_ALL_POSITION_APPROVAL %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_mail_to_recruiter_for_rejected_candidate"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(send_mail_to_recruiter_for_rejected_candidate)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_mail_to_recruiter_for_rejected_candidate" name="send_mail_to_recruiter_for_rejected_candidate" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_REJECTED_CANDIDATE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_mail_to_recruiter_for_rejected_candidate" name="send_mail_to_recruiter_for_rejected_candidate" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_REJECTED_CANDIDATE %>);" />
				  <%} %>			  
			  </td>
			</tr>
			
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_mail_to_recruiter_for_On_Hold_candidate"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(send_mail_to_recruiter_for_On_Hold_candidate)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_mail_to_recruiter_for_On_Hold_candidate" name="send_mail_to_recruiter_for_On_Hold_candidate" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_TO_RECRUITER_FOR_ON_HOLD_CANDIDATE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_mail_to_recruiter_for_On_Hold_candidate" name="send_mail_to_recruiter_for_On_Hold_candidate" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_TO_RECRUITER_FOR_ON_HOLD_CANDIDATE %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_mail_to_recruiter_for_candidate_status"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(send_mail_to_recruiter_for_candidate_status)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_mail_to_recruiter_for_candidate_status" name="send_mail_to_recruiter_for_candidate_status" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_mail_to_recruiter_for_candidate_status" name="send_mail_to_recruiter_for_candidate_status" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS %>);" />
				  <%} %>			  
			  </td>
			</tr>
			
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_rejected_candidate_notification_to_recruiter"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if("1".equals(rejectedCandidateMailToRecruiter)){ %>
				  		<img src="images/checkboxchecked.gif" id="rejectedCandidateMailToRecruiter" name="rejectedCandidateMailToRecruiter" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REJECTED_CANDIDATE_NOTIFICATION_TO_RECRUITER %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="rejectedCandidateMailToRecruiter" name="rejectedCandidateMailToRecruiter" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REJECTED_CANDIDATE_NOTIFICATION_TO_RECRUITER %>);" />
				  <%} %>			  
			  </td>
			</tr>
			
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_mail_to_Hrmanager_Pending_Requisition_approval"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(send_mail_to_Hrmanager_Pending_Requisition_approval)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_mail_to_Hrmanager_Pending_Requisition_approval" name="send_mail_to_Hrmanager_Pending_Requisition_approval" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_mail_to_Hrmanager_Pending_Requisition_approval" name="send_mail_to_Hrmanager_Pending_Requisition_approval" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_HRMANAGER_PENDING_REQUISITION_APPROVAL %>);" />
				  <%} %>			  
			  </td>
			</tr>
			
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.auto_approve_requisition"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(autoApproveRequisition)){ %>
				  		<img src="images/checkboxchecked.gif" id="autoApproveRequisition" name="autoApproveRequisition" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_AUTO_APPROVE_REQUISITION %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="autoApproveRequisition" name="autoApproveRequisition" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_AUTO_APPROVE_REQUISITION %>);" />
				  <%} %>			  
			  </td>
			</tr>
			
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.default_password_expiry_duration"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('30','30 days'),new SelectOption('45','45 days'),new SelectOption('60','60 days'),new SelectOption('90','90 days')];
					cboDefaultPasswordExpiryDuration = new SelectBox(opts,'<%=defaultPasswordExpiryDuration%>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:15});
					document.write(cboDefaultPasswordExpiryDuration.getHtml());
					cboDefaultPasswordExpiryDuration.init();
				</script>
			  
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.default_days_for_position_approval"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('1','1 day'),new SelectOption('2','2 days'),new SelectOption('3','3 days'),new SelectOption('4','4 days'),new SelectOption('5','5 days'),new SelectOption('6','6 days'),new SelectOption('7','7 days'),new SelectOption('10','10 days')];
					cboDefaultPositionApprovalDuration = new SelectBox(opts,'<%=defaultPositionApprovalDuration%>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:15});
					document.write(cboDefaultPositionApprovalDuration.getHtml());
					cboDefaultPositionApprovalDuration.init();
				</script>
			  
			  </td>
			</tr>
			
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.default_password_different_from_last"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('1','1 password '),new SelectOption('2','2 passwords'),new SelectOption('3','3 passwords'),new SelectOption('4','4 passwords'),new SelectOption('5','5 passwords')];
					cboDefaultPasswordDifferentFromLast = new SelectBox(opts,'<%=defaultPasswordDifferentFromLast%>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:15});
					document.write(cboDefaultPasswordDifferentFromLast.getHtml());
					cboDefaultPasswordDifferentFromLast.init();
				</script>
			  
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.default_appointment_duration"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('30','30 min'),new SelectOption('60','1 hr'),new SelectOption('120','2 hrs'),new SelectOption('180','3 hrs'), new SelectOption('240','4 hrs')];
					cboDefaultAppointmentDuration = new SelectBox(opts,'<%=defaultAppointmentDuration%>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:15});
					document.write(cboDefaultAppointmentDuration.getHtml());
					cboDefaultAppointmentDuration.init();
				</script>
			  
			  </td>
			</tr>						   
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.default_reminder_duration"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('15','15 min before'),new SelectOption('30','30 min before'),new SelectOption('60','1 hr before'),new SelectOption('120','2 hrs before'), new SelectOption('240','4 hrs before'), new SelectOption('1440','1 day before'), new SelectOption('2880','2 days before')];
					cboDefaultReminderDuration = new SelectBox(opts,'<%=defaultReminderDuration%>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:15});
					document.write(cboDefaultReminderDuration.getHtml());
					cboDefaultReminderDuration.init();

				</script>
			  </td>
			</tr>
			
			<% if(ModuleSet.isMODULE_COSTS()){ %>			
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.financial_year_start_month"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('0','January'),new SelectOption('1','February'),new SelectOption('2','March')
					,new SelectOption('3','April'),new SelectOption('4','May'),new SelectOption('5','June'),
					new SelectOption('6','July'),new SelectOption('7','August'),new SelectOption('8','September'),
					new SelectOption('9','October'),new SelectOption('10','November'),new SelectOption('11','December')];
					cboDefaultFinancialMonthStart = new SelectBox(opts,'<%=financial_year_start_month %>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:12});
					document.write(cboDefaultFinancialMonthStart.getHtml());
					cboDefaultFinancialMonthStart.init();
				</script>
			  </td>
			</tr>
			<% } %>
			<tr>
			   <td class="label" style="width:400px;">
			   	<bean:message key="admin_application_settings.label.default_dateformat"/>
			  </td>						      
			  <td ></td>
			  <td height="20"  style="padding-left: 3px;padding-bottom: 0px;">
				  <table >
				  	<tr>
				  		<td style="padding: 0px;margin: 0;">
				  			<script type="text/javascript"> 
								var datePatterns = <bean:write name="datePatternsJSArray" scope="request" filter="false" />;
								datePatternJsArray= new SelectBox(datePatterns,'<%=default_dateFormat %>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:12});
								datePatternJsArray.setOnChangeHandler('onChangeDatePattern');
								document.write(datePatternJsArray.getHtml());
								datePatternJsArray.init();
							</script>
				  		</td>
				  		<td>[<span id="dateDisplayDiv"><bean:write name="dateFormat" scope="request" /></span>]</td>
				  	</tr>
				  </table>
			  </td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			   	<bean:message key="admin_application_settings.label.default_timeformat"/>
			  </td>						      
			  <td ></td>
			 <td height="20" style="padding-left: 3px;padding-bottom: 0px;" >
				  <table >
				  	<tr>
				  		<td style="padding: 0px;">
						 <script type="text/javascript"> 
							var timePatterns = <bean:write name="timePatternsJSArray" scope="request" filter="false" />;
							timePatternJsArray= new SelectBox(timePatterns,'<%=default_timeFormat %>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:12});
							timePatternJsArray.setOnChangeHandler('onChangeTimePattern');
							document.write(timePatternJsArray.getHtml());
							timePatternJsArray.init();
						</script>
			  			</td>
			  			<td>[<span id="timeDisplayDiv"><bean:write name="timeFormat" scope="request" /></span>]</td>
			  		</tr>
			  	</table>	
			  </td>		
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.offer_to_joined_detailed_step"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = <bean:write name="stepsJsArray" filter="false" scope="request" />;
					cboOfferToJoinedDetailedSteps = new SelectBox(opts,'<%=offer_to_joined_step_id %>','images/btn_dropdown.gif',{namesonly:false, width:'150px', size:12});
					document.write(cboOfferToJoinedDetailedSteps.getHtml());
					cboOfferToJoinedDetailedSteps.setOnChangeHandler('onOfferToJoinedStepChange');
					cboOfferToJoinedDetailedSteps.init();
				</script>
			  </td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.max_dept_level"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('<%=MastersConstants.DEPARTMENT_LEVEL_3%>','Level 3'),
					            new SelectOption('<%=MastersConstants.DEPARTMENT_LEVEL_4%>','Level 4'),
					            new SelectOption('<%=MastersConstants.DEPARTMENT_LEVEL_5%>','Level 5')];
					cboDeptLevels = new SelectBox(opts,'<%=max_dept_level %>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:12});
					document.write(cboDeptLevels.getHtml());
					cboDeptLevels.setOnChangeHandler('onMaxDeptLevelChange');
					cboDeptLevels.init();
				</script>
			  </td>
			</tr>	
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.department_level_1"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1 %>" value="<%=departmentLevel1 %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.department_level_2"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2 %>" value="<%=departmentLevel2 %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.department_level_3"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3 %>" value="<%=departmentLevel3 %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<tr id="dept_level_4">
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.department_level_4"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4 %>" value="<%=departmentLevel4 %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<tr id="dept_level_5">
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.department_level_5"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5 %>" value="<%=departmentLevel5 %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<% if(ModuleSet.isMODULE_SOCIAL_NETWORK()) { %>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.careers_page_label"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_CAREERS_PAGE_URL %>" value="<%=careersPageUrl %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<% } %>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.budget_item_grade_label"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL %>" value="<%=budget_item_grade_label %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.budget_item_band_label"/>
			  </td>
			  <td></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL %>" value="<%=budget_item_band_label %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.salary_variable_input_salary_label"/>
			  </td>
			  <td></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL %>" value="<%=salary_variable_input_salary_label %>" size="40" maxlength="100"/>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			   		<bean:message key="master_business_unit.title.business_unit"/>			    
			  </td>						      
			  <td ></td>
			  <td style="height: 20px;">
			  	
		  			<% if("1".equals(businessUnitProperty)){ %>
			  			<img src="images/checkboxchecked.gif" id="businessUnitProperty" name="businessUnitProperty"  style="margin-bottom: -1px;"
			  			onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY %>);toggleCheckboxDisplay('buLabel', document.adminForm.<%=GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY %>,'bu');" />				  		
			  		<%}else{ %>
			  			<img src="images/checkboxunchecked.gif" id="businessUnitProperty" name="businessUnitProperty" style="margin-bottom: -1px;"
			  			onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY %>);toggleCheckboxDisplay('buLabel', document.adminForm.<%=GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY %>,'bu');" />
			  		<%} %>
				  	
					<span id="buLabel">
						<input type="text" name="<%=GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL %>" value="<%= businessUnitLabel %>" size="37" maxlength="100"/>
					</span>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			   <bean:message key="master_cost_center.title.cost_center"/>
			  </td>						      
			  <td></td>
			  <td style="height: 20px;">
		  			<% if("1".equals(costCenterProperty)){ %>
			  			<img src="images/checkboxchecked.gif" id="costCenterProperty" name="costCenterProperty" style="margin-bottom: -1px;"
			  			onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_COST_CENTER_PROPERTY %>);toggleCheckboxDisplay('ccLabel', document.adminForm.<%=GlobalConstants.PROPERTY_COST_CENTER_PROPERTY %>,'cc');" />				  		
			  		<%}else{ %>
			  			<img src="images/checkboxunchecked.gif" id="costCenterProperty" name="costCenterProperty" style="margin-bottom: -1px;"
			  				onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_COST_CENTER_PROPERTY %>);toggleCheckboxDisplay('ccLabel', document.adminForm.<%=GlobalConstants.PROPERTY_COST_CENTER_PROPERTY %>,'cc');" />
			  		<%} %>
					<span id="ccLabel">
						<input type="text" name="<%=GlobalConstants.PROPERTY_COST_CENTER_LABEL %>" value="<%= costCenterLabel %>" size="37" maxlength="100"/>
					</span>			  
				</td>
			</tr>			
			
			<tr>
			   <td class="label" style="width:400px;">
			   		<bean:message key="admin_application_settings.label.joined_candidate_search"/>
			  </td>						      
			  <td ></td>
			  <td style="height: 20px;">			  	
		  			<% if(GlobalConstants.ENABLED.equals(showJoinedCandidateInSearch)){ %>
			  			<img src="images/checkboxchecked.gif" id="showJoinedCandidateInSearch" name="showJoinedCandidateInSearch"  style="margin-bottom: -1px;" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH %>);" />				  		
			  		<%}else{ %>
			  			<img src="images/checkboxunchecked.gif" id="showJoinedCandidateInSearch" name="showJoinedCandidateInSearch" style="margin-bottom: -1px;" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH %>);" />
			  		<%} %>		
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			   		<bean:message key="admin_application_settings.label.locking_period_employee_shortlist"/>			   	 
			  </td>						      
			  <td ></td>
			  <td style="height: 20px;">
			  		<input type="text" name="<%=GlobalConstants.PROPERTY_LOCK_PERIOD_FOR_JOINED_CANDIDATE_SHORTLIST %>" value="<%=lockPeriodForJoinedCandidateShortlist %>" size="3" maxlength="3"/>			  		
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			   		<bean:message key="admin_application_settings.label.default_number_vacancy"/>			   	
			  </td>						      
			  <td ></td>			  
			  <td style="height: 20px;">
			  		<input type="text" name="<%=GlobalConstants.PROPERTY_DEFAULT_NUMBER_OF_VACANCY %>" value="<%=defaultNumberOfVacancy %>" size="3" maxlength="3"/>		
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			   		<bean:message key="admin_application_settings.label.position_vacancy_restriction"/>
			  </td>						      
			  <td ></td>
			  <td style="height: 20px;">			  	
		  			<% if(GlobalConstants.ENABLED.equals(applyPositionVacancyRestriction)){ %>
			  			<img src="images/checkboxchecked.gif" id="applyPositionVacancyRestriction" name="applyPositionVacancyRestriction"  style="margin-bottom: -1px;" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION %>);" />				  		
			  		<%}else{ %>
			  			<img src="images/checkboxunchecked.gif" id="applyPositionVacancyRestriction" name="applyPositionVacancyRestriction" style="margin-bottom: -1px;" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION %>);" />
			  		<%} %>		
			  </td>
			</tr>
										
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="common.position"/> <bean:message key="common.code"/> <bean:message key="common.template"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				 <input type="text" name="<%=GlobalConstants.PROPERTY_POSITION_CODE_TEMPLATE %>" value="<%=position_code_template %>" size="56" maxlength="100"/>
				 <br/>
				 Where<br/>
				 p: <bean:message key="common.position"/> <bean:message key="common.name"/><br/>
				 s: <bean:message key="common.department"/><br/>
				 l: <bean:message key="common.location"/><br/>
				 y: <bean:message key="common.year"/><br/>
				 m: <bean:message key="common.month"/><br/>
				 d: <bean:message key="common.day_of_month"/><br/>
				 n: <bean:message key="admin_application_settings.label.sequentially_generated_number"/>		 
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			   		Copy <bean:message key="common.position"/> <bean:message key="common.code"/> when position is Copied.  		    
			  </td>						      
			  <td ></td>
			  <td style="height: 20px;">			  	
		  			<% if("1".equals(copyPositionWithCode)){ %>
			  			<img src="images/checkboxchecked.gif" id="copyPositionWithCode" name="copyPositionWithCode"  style="margin-bottom: -1px;" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_COPY_POSITION_WITH_CODE %>);" />				  		
			  		<%}else{ %>
			  			<img src="images/checkboxunchecked.gif" id="copyPositionWithCode" name="copyPositionWithCode" style="margin-bottom: -1px;" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_COPY_POSITION_WITH_CODE %>);" />
			  		<%} %>		
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="common.position"/> <bean:message key="common.display_in_grid"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				 <input type="text" name="<%=GlobalConstants.PROPERTY_POSITION_DISPLAY_IN_GRID_TEMPLATE %>" value="<%=position_display_in_grid_template %>" size="56" maxlength="100"/>
				 <br/>
				 Where<br/>
				 n: <bean:message key="common.position"/> <bean:message key="common.name"/><br/>
				 c: <bean:message key="common.position"/> <bean:message key="common.code"/><br/>
				 d: <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %><br/>
				 s: <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) %><br/>
				 g: <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) %><br/>
				 l: <bean:message key="common.location"/><br/>
				 r: <bean:message key="common.grade"/><br/>		 		 
			  </td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="common.user"/> <bean:message key="common.display_in_grid"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				 <input type="text" name="<%=GlobalConstants.PROPERTY_USER_DISPLAY_IN_GRID_TEMPLATE %>" value="<%=user_display_in_grid_template %>" size="56" maxlength="100"/>
				 <br/>
				 Where<br/>
				 f: <bean:message key="common.user"/> <bean:message key="add_user.label.fname"/><br/>
				 n: <bean:message key="common.user"/> <bean:message key="add_user.label.lname"/><br/>
				 d: <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %><br/>
				 s: <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) %><br/>
				 g: <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) %><br/>
				 l: <bean:message key="common.location"/><br/>
				 r: <bean:message key="common.roles"/><br/>		 
				 e: <bean:message key="search_applicant.home.label.employee_id"/>		 
			  </td>
			</tr>		
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="generate_offer_sheet.label.offer_code"/> <bean:message key="common.template"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				 <input type="text" name="<%=GlobalConstants.PROPERTY_OFFER_CODE_TEMPLATE %>" value="<%=offer_code_template %>" size="56" maxlength="100"/>
				 <br/>
				 Where<br/>
				 	c: <bean:message key="admin_settings.label.company_name"/><br/>
					a: <bean:message key="common.candidate"/> <bean:message key="common.name"/><br/> 
					i: <bean:message key="common.candidate"/> <bean:message key="common.id"/><br/>
					p: <bean:message key="common.position"/> <bean:message key="common.name"/><br/> 
					s: <bean:message key="common.department"/><br/>
					b: <bean:message key="common.band"/><br/> 
					g: <bean:message key="common.grade"/><br/> 
					h: <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_LABEL)%><br/> 
					y: <bean:message key="common.year"/><br/>
					m: <bean:message key="common.month"/><br/>
					d: <bean:message key="common.day_of_month"/><br/>
					n: <bean:message key="admin_application_settings.label.sequentially_generated_number"/>				 		 
			  </td>
			</tr>
	</table>
		
	<% if(ModuleSet.isMODULE_BUDGET()){ %>			
	
			<table class="boxHeader" style="margin-top:5px; background-color:#CCCCCC; border-color:#A3A3A3; border-spacing: 0; padding: 0; width: 100%;">
			<tr>
				<td class="header" height="18"><strong><bean:message key="admin_application_settings.label.budget_module_settings" /></strong></td>
			</tr>
			</table>
			<table>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.budget_module_status"/>
			  </td>						      
			  <td></td>
			  <td height="20">
				<script type="text/javascript"> 
					var opts = [new SelectOption('<%=GlobalConstants.ENABLED %>','Active'),new SelectOption('<%=GlobalConstants.DISABLED %>','In-Active')];
					cboBudgetModuleStatus= new SelectBox(opts,'<%=budget_module_status %>','images/btn_dropdown.gif',{namesonly:false, width:'80px', size:12});
					document.write(cboBudgetModuleStatus.getHtml());
					cboBudgetModuleStatus.init();
				</script>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.budget_mode"/>
			  </td>						      
			  <td></td>
			  <td height="20" >
				<script type="text/javascript"> 
					var opts = [new SelectOption('<%=BudgetConstants.BUDGET_MODE_TRACK %>','Track'),new SelectOption('<%=BudgetConstants.BUDGET_MODE_ENFORCE %>','Enforce')];
					cboBudgetMode = new SelectBox(opts,'<%=budget_mode %>','images/btn_dropdown.gif',{namesonly:false, width:'80px', size:12});
					document.write(cboBudgetMode.getHtml());
					cboBudgetMode.init();
				</script>
			  </td>
			</tr>
			<tr>
				<td class="label" style="width:400px;">
			    	<bean:message key="admin_application_settings.label.replacement_against_budget"/>
			  	</td>
			  	<td></td>
			  	<td height="20">			  		
 	  				<% if("1".equals(budgetForReplacement)){ %>
				  		<img src="images/checkboxchecked.gif" id="budgetForReplacement" name="budgetForReplacement" style=""
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_BUDGET_FOR_REPLACEMENT %>);" />
				  	<%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="budgetForReplacement" name="budgetForReplacement" style="" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_BUDGET_FOR_REPLACEMENT %>);" />
				  	<%} %>			  
				</td>			
			</tr>			
		</table>
			<% } %>	
		<table class="boxHeader" style="margin-top:5px; background-color:#CCCCCC; border-color:#A3A3A3; border-spacing: 0; padding: 0; width: 100%">
			<tr>
				<td class="header" height="18"><strong><bean:message key="admin_application_settings.label.employee_portal_settings" /></strong></td>
			</tr>
		</table>
		<table>
		<tr>
				<td class="label" style="width:400px;">
			    	<bean:message key="admin_application_settings.label.intimate_referral_status"/>
			  	</td>
		  	    <td></td>
			    <td height="20">
				  <% if("1".equals(progressEmailToEmployee)){ %>
				  		<img src="images/checkboxchecked.gif" id="intimate" name="intimate" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_PROGRESS_EMAIL_TO_EMPLOYEE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="intimate" name="intimate" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_PROGRESS_EMAIL_TO_EMPLOYEE %>);" />
				  <%} %>
			  
			    </td>
			</tr>
				<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.show_reason_for_reject"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(show_reason_for_reject)){ %>
				  		<img src="images/checkboxchecked.gif" id="show_reason_for_reject" name="show_reason_for_reject" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_REASON_FOR_REJECT_CANDIDATE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="show_reason_for_reject" name="show_reason_for_reject" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_REASON_FOR_REJECT_CANDIDATE %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.show_step_details"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(show_step_details)){ %>
				  		<img src="images/checkboxchecked.gif" id="show_step_details" name="show_step_details" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_STEP_DETAILS %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="show_step_details" name="show_step_details" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_STEP_DETAILS %>);" />
				  <%} %>			  
			  </td>
			</tr>
				<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.send_mail_to_friend"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(send_mail_to_friend)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_mail_to_friend" name="send_mail_to_friend" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_mail_to_friend" name="send_mail_to_friend" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_MAIL_TO_FRIEND_FOR_OPENING %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
				<td class="label" style="width:400px;">
			    	<bean:message key="admin_application_settings.label.joined_mail_to_employee"/>
			  	</td>
		  	    <td></td>
			    <td height="20">
				  <% if("1".equals(joinedEmailToEmployee)){ %>
				  		<img src="images/checkboxchecked.gif" id="joined_email_to_employee" name="joined_email_to_employee" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_JOINED_EMAIL_TO_EMPLOYEE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="intimate" name="intimate" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_JOINED_EMAIL_TO_EMPLOYEE %>);" />
				  <%} %>
			  
			    </td>
			</tr>
		<!-- 	<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.employee_resume_upload_notification"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if("1".equals(employee_resume_upload_notification)){ %>
				  		<img src="images/checkboxchecked.gif" id="employee_resume_upload_notification" name="employee_resume_upload_notification" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="employee_resume_upload_notification" name="employee_resume_upload_notification" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION %>);" />
				  <%} %>
			  
			  </td>
			</tr> -->
			<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.labelresume_upload_successful_notification_to_employee"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if("1".equals(employee_resume_upload_notification_to_employee)){ %>
				  		<img src="images/checkboxchecked.gif" id="employee_resume_upload_notification_to_employee" name="employee_resume_upload_notification_to_employee" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="employee_resume_upload_notification_to_employee" name="employee_resume_upload_notification_to_employee" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE %>);" />
				  <%} %>
			  
			  </td>
			</tr>
				<%-- <tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.labelresume_upload_successful_notification_to_candidate"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if("1".equals(employee_resume_upload_notification_to_candidate)){ %>
				  		<img src="images/checkboxchecked.gif" id="employee_resume_upload_notification_to_candidate" name="employee_resume_upload_notification_to_candidate" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_CANDIDATE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="employee_resume_upload_notification_to_candidate" name="employee_resume_upload_notification_to_candidate" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_CANDIDATE %>);" />
				  <%} %>
			  
			  </td>
			</tr> --%>
			<tr>
			   <td class="label" style="width:400px;">
			   <bean:message key="admin_application_settings.label.rejection_email_to_employee"/>				
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(rejection_email_to_employee)){ %>
				  		<img src="images/checkboxchecked.gif" id="rejection_email_to_employee" name="rejection_email_to_employee" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_EMPLOYEE %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="rejection_email_to_employee" name="rejection_email_to_employee"	onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_EMPLOYEE %>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			   <bean:message key="admin_application_settings.label.position_published_notification_to_employees"/>				
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(send_position_published_notification_to_employees)){ %>
				  		<img src="images/checkboxchecked.gif" id="send_position_published_notification_to_employees" name="send_position_published_notification_to_employees" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_POSITION_PUBLISHED_NOTIFICATION_TO_EMPLOYEES %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="send_position_published_notification_to_employees" name="send_position_published_notification_to_employees"	onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_POSITION_PUBLISHED_NOTIFICATION_TO_EMPLOYEES %>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.all_employees_group_email_id"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_ALL_EMPLOYEES_GROUP_EMAIL_ID %>" value="<%=all_employees_group_email_id %>"  size="40" maxlength="250"/>
			  </td>
			</tr>
			
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_resume_upload_tried_notification"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
			  	  <% if("1".equals(sendEmailNotificationToHrForEmployeePortalUpload)){ %>
				  		<img src="images/checkboxchecked.gif" id="sendEmailNotificationToHrForEmployeePortalUpload" name="sendEmailNotificationToHrForEmployeePortalUpload" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL %>);toggleDisplay('list2', document.adminForm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL %>, checkBoxListHrUsers);" />				  		
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="sendEmailNotificationToHrForEmployeePortalUpload" name="sendEmailNotificationToHrForEmployeePortalUpload" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL %>);toggleDisplay('list2', document.adminForm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL %>, checkBoxListHrUsers);" />
				  <%} %>
			  </td>
			</tr>
			<tr id="list2">
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_resume_upload_notification_to_Hr_email"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
			  	<script type="text/javascript">	
			  		options = new Array();
	 			    <logic:iterate id="activeUser" name="activeUsers" scope="request" type="SimpleDataObject">
						options[options.length] = new SelectOption('<%=activeUser.getString("userId")%>','<%=activeUser.getString("userName")%>');
					</logic:iterate>
					checkBoxListHrUsers = new CheckBoxList(options,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
					document.write(checkBoxListHrUsers.getHtml());
					checkBoxListHrUsers.init();
					checkBoxListHrUsers.resetSelected('<%=sendEmailToHrForEmployeePortalUpload%>');
		 		</script>			  	
			  </td>
			</tr>
			</table>
			
		<% if(ModuleSet.isMODULE_VENDOR()){ %>			
		<table width="100%" class="boxHeader" style="margin-top:5px; background-color:#CCCCCC; border-color:#A3A3A3" cellspacing="0" cellpading="0">
			<tr>
				<td class="header" height="18"><strong><bean:message key="admin_application_settings.label.vendor_portal_settings" /></strong></td>
			</tr>
		</table>
		<table>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.vendor_resume_upload_notification"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if("1".equals(vendor_resume_upload_notification)){ %>
				  		<img src="images/checkboxchecked.gif" id="vendor_resume_upload_notification" name="vendor_resume_upload_notification" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="vendor_resume_upload_notification" name="vendor_resume_upload_notification" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION %>);" />
				  <%} %>
			  
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.resume_upload_successful_notification_to_vendor"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if("1".equals(vendor_resume_upload_notification_to_vendor)){ %>
				  		<img src="images/checkboxchecked.gif" id="vendor_resume_upload_notification_to_vendor" name="vendor_resume_upload_notification_to_vendor" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="vendor_resume_upload_notification_to_vendor" name="vendor_resume_upload_notification_to_vendor" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR %>);" />
				  <%} %>			  
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.rejection_email_to_vendor"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(rejection_email_to_vendor)){ %>
				  		<img src="images/checkboxchecked.gif" id="rejection_email_to_vendor" name="rejection_email_to_vendor" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_VENDOR %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="rejection_email_to_vendor" name="rejection_email_to_vendor" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_REJECTION_EMAIL_TO_VENDOR %>);" />
				  <%} %>
			  
			  </td>
			</tr>	
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.notify_vendor_activity_to_hr"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(notify_vendor_activity_to_hr)){ %>
				  		<img src="images/checkboxchecked.gif" id="notify_vendor_activity_to_hr" name="notify_vendor_activity_to_hr" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="notify_vendor_activity_to_hr" name="notify_vendor_activity_to_hr" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_NOTIFY_VENDOR_ACTIVITY_TO_HR %>);" />
				  <%} %>
			  
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.show_detailed_activity_to_vendor"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(show_detailed_activity_to_vendor)){ %>
				  		<img src="images/checkboxchecked.gif" id="show_detailed_activity_to_vendor" name="show_detailed_activity_to_vendor" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="show_detailed_activity_to_vendor" name="show_detailed_activity_to_vendor" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR %>);" />
				  <%} %>
			  </td>
			</tr>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_reminders_to_vendor"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% if("1".equals(send_reminder_to_vendor)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_VENDOR%>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_REMINDER_TO_VENDOR %>);" />
				  <%} %>
			  </td>
			</tr>	
				<tr>
			  <td class="label" style="width:400px;">
			  	<bean:message key="admin_application_settings.label.show_step_details_vendor"/>								
			  </td>						      
			  <td ></td>
			  <td height="20">
			 	  <% 
				  if(GlobalConstants.ENABLED.equals(show_step_details_for_vendor)){ %>
				  		<img src="images/checkboxchecked.gif" id="show_step_details_for_vendor" name="show_step_details_for_vendor" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_STEP_DETAILS_VENDOR %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="show_step_details_for_vendor" name="show_step_details_for_vendor" onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SHOW_STEP_DETAILS_VENDOR %>);" />
				  <%} %>			  
			  </td>
			</tr>			
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_duplicate_resume_upload_tried_notification"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
			  	  <% if("1".equals(sendReminderOnDuplicateResumeUpload)){ %>
				  		<img src="images/checkboxchecked.gif" id="sendReminderOnDuplicateResumeUpload" name="sendReminderOnDuplicateResumeUpload" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION %>);toggleDisplay('list1', document.adminForm.<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION %>, checkBoxListUsers);" />				  		
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="sendReminderOnDuplicateResumeUpload" name="sendReminderOnDuplicateResumeUpload" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION %>);toggleDisplay('list1', document.adminForm.<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION %>, checkBoxListUsers);" />
				  <%} %>
			  </td>
			</tr>
			<tr id="list1">
			   <td class="label" style="width:400px;">
			    <bean:message key="admin_application_settings.label.send_duplicate_resume_upload_tried_notification_to_email"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
			  	<script type="text/javascript">	
			  		options = new Array();
	 			    <logic:iterate id="activeUser" name="activeUsers" scope="request" type="SimpleDataObject">
						options[options.length] = new SelectOption('<%=activeUser.getString("userId")%>','<%=activeUser.getString("userName")%>');
					</logic:iterate>
		 			checkBoxListUsers = new CheckBoxList(options,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
					document.write(checkBoxListUsers.getHtml());
					checkBoxListUsers.init();
					checkBoxListUsers.resetSelected('<%=sendReminderOnDuplicateResumeUploadToEmail%>');
		 		</script>			  	
			  </td>
			</tr>
			</table>		
			<%} %>
		<table width="100%" class="boxHeader" style="margin-top:5px; background-color:#CCCCCC; border-color:#A3A3A3" cellspacing="0" cellpading="0">
			<tr>
				<td class="header" height="18"><strong><bean:message key="admin_application_settings.label.naukri_settings" /></strong></td>
			</tr>
		</table>
		<table>
			<tr>
			   <td class="label" style="width:400px;">
			    <bean:message key="naukri.is_integration_with_naukri"/>
			  </td>						      
			  <td ></td>
			  <td height="20">
				  <% 
				  if("1".equals(isNaukriIntegration)){ %>
				  		<img src="images/checkboxchecked.gif" id="isNaukriIntegration" name="isNaukriIntegration" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_IS_NAUKRI_INTEGRATION %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="isNaukriIntegration" name="isNaukriIntegration" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_IS_NAUKRI_INTEGRATION %>);" />
				  <%} %>
			  
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="naukri.hiring_org_website"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_HIRING_ORG_WEBSITE %>" value="<%=hiringOrgWebsite %>"  size="40" maxlength="250"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="naukri.hiring_org_name"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_HIRING_ORG_NAME %>" value="<%=hiringOrgName %>"  size="40" maxlength="250"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="naukri.summary_text"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_ORG_DESCRIPTION %>" value="<%=orgDescription %>"  size="40" maxlength="250"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="naukri.microsite_name"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_MICROSITE_NAME %>" value="<%=micrositeName %>"  size="40" maxlength="250"/>
			  </td>
			</tr>
			<tr>
			  <td class="label" style="width:400px;">
			    <bean:message key="naukri.template_name"/>
			  </td>
			  <td ></td>
			  <td>
			    <input type="text" name="<%=GlobalConstants.PROPERTY_TEMPLATE_NAME %>" value="<%=templateName %>"  size="40" maxlength="250"/>
			  </td>
			</tr>
			</table>
</div>	
<div class="navBtn" style="margin-top:5px;">
	<a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();">
	<span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
	
</div>
<br/><br/><br/>
</div>

</html:form>

<script type="text/javascript">
function onRadioChange(imgGroupName, attachmentId, fld){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					theImage.src = "images/checkedradiobutton.gif";
					fld.value= attachmentId;
				}else{
					theImage.src = "images/radiobutton.gif";
				}
			}
	}
}
function changeCheckboxState(chkBox, fld){
	var prevId = fld.value;
	if(prevId!='1'){
		fld.value='1';
		chkBox.src='images/checkboxchecked.gif';
	}else{
		fld.value='0';
		chkBox.src='images/checkboxunchecked.gif';
	}
}

function toggleDisplay(rowId, fld, chkBoxList) {	
	var prevId = fld.value;	
	if(prevId!='1'){		
		document.getElementById(rowId).style.display = 'none';
		chkBoxList.resetSelected();
	}else{
		document.getElementById(rowId).style.display = '';
	}
}

function toggleCheckboxDisplay(rowId, fld, chkBox) {	
	var prevId = fld.value;
	if(chkBox=='bu'){
		if(prevId!='1'){		
			document.getElementById(rowId).style.display = 'none';		
		}else{
			document.getElementById(rowId).style.display = '';
		}
	}
	if(chkBox=='cc'){
		if(prevId!='1'){		
			document.getElementById(rowId).style.display = 'none';		
		}else{
			document.getElementById(rowId).style.display = '';
		}
	}
}

function onMaxDeptLevelChange(){
	var val = cboDeptLevels.getSelectedId();
	
	if(val=='<%=MastersConstants.DEPARTMENT_LEVEL_4%>'){
		$("dept_level_4").style.display = '';
		$("dept_level_5").style.display = 'none';
	}else if(val=='<%=MastersConstants.DEPARTMENT_LEVEL_5%>'){
		$("dept_level_4").style.display = '';
		$("dept_level_5").style.display = '';
	}else{
		$("dept_level_4").style.display = 'none';
		$("dept_level_5").style.display = 'none';
	}
}

function validateFields(){
	errors = '';
	if(isNaN(document.adminForm.<%=GlobalConstants.PROPERTY_DEFAULT_NUMBER_OF_VACANCY%>.value)){
		alert("error");
		return false;	
	}	
	if(isNaN(document.adminForm.<%=GlobalConstants.PROPERTY_DEFAULT_NUMBER_OF_VACANCY%>.value)){
		alert("error");
		return false;	
	}
}

function onChangeDatePattern(index,obj){
	var pars = "mode=displayTodayDate&datePatternId=" + obj.getSelectedId();
  	var myAjax = ajaxCall("adminHome.do",'post',pars,displayDatePattern, reportError);
}

function onChangeTimePattern(index,obj){
	var pars = "mode=displayTodayDate&datePatternId=" + obj.getSelectedId();
  	var myAjax = ajaxCall("adminHome.do",'post',pars,displayTimePattern, reportError);
}

function displayDatePattern(response){
	xmlFile = response.responseXML;
	if(xmlFile && !isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    	return;
  	}
	responseText = response.responseText;
	$('dateDisplayDiv').update(responseText);
}

function displayTimePattern(response){
	xmlFile = response.responseXML;
	if(xmlFile && !isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    	return;
  	}
	responseText = response.responseText;
	$('timeDisplayDiv').update(responseText);
}


function submitForm(){
	var frm=document.adminForm;
	//validateFields();
	frm.<%=GlobalConstants.PROPERTY_DEFAULT_PASSWORD_EXPIRY_DURATION %>.value = cboDefaultPasswordExpiryDuration.getSelectedId();
	frm.<%=GlobalConstants.PROPERTY_DEFAULT_PASSWORD_DIFFERENT_FROM_LAST %>.value = cboDefaultPasswordDifferentFromLast.getSelectedId();
	frm.<%=GlobalConstants.PROPERTY_DEFAULT_APPOINTMENT_DURATION %>.value = cboDefaultAppointmentDuration.getSelectedId();
	frm.<%=GlobalConstants.PROPERTY_DEFAULT_REMINDER_DURATION %>.value = cboDefaultReminderDuration.getSelectedId();
	frm.<%=GlobalConstants.PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID %>.value = cboOfferToJoinedDetailedSteps.getSelectedId();
	frm.<%=GlobalConstants.PROPERTY_DEFAULT_POSITION_APPROVAL_DURATION %>.value = cboDefaultPositionApprovalDuration.getSelectedId();
	if(cboDefaultFinancialMonthStart){
		frm.<%=GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH %>.value = cboDefaultFinancialMonthStart.getSelectedId();
	}
	if(cboDeptLevels){
		frm.<%=GlobalConstants.PROPERTY_MAX_DEPT_LEVEL %>.value = cboDeptLevels.getSelectedId();
	}
	if(checkBoxListUsers){
	frm.<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION_TO_EMAIL %>.value = checkBoxListUsers.getSelectedIds();
	}
	if(checkBoxListHrUsers){
		frm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_TO_HR_FOR_EMPLOYEE_PORTAL %>.value = checkBoxListHrUsers.getSelectedIds();
		}
	if(cboBudgetModuleStatus){
		frm.<%=GlobalConstants.PROPERTY_BUDGET_MODULE_STATUS %>.value = cboBudgetModuleStatus.getSelectedId();
	}
	if(cboBudgetMode){
		frm.<%=GlobalConstants.PROPERTY_BUDGET_MODE %>.value = cboBudgetMode.getSelectedId();
	}
	if(datePatternJsArray){
		frm.<%=GlobalConstants.PROPERTY_DEFAULT_DATEFORMAT %>.value = datePatternJsArray.getSelectedId();
	}
	if(timePatternJsArray){
		frm.<%=GlobalConstants.PROPERTY_DEFAULT_TIMEFORMAT %>.value = timePatternJsArray.getSelectedId();
	}
	frm.submit();
}

window.onload=doOnLoad;

function doOnLoad() {
	<% if(ModuleSet.isMODULE_VENDOR()){ %>			
	toggleDisplay('list1', document.adminForm.<%=GlobalConstants.PROPERTY_SEND_DUPLICATE_RESUME_UPLOAD_TRIED_NOTIFICATION %>, checkBoxListUsers);
	<% } %>
				
	toggleDisplay('list2', document.adminForm.<%=GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL %>, checkBoxListHrUsers);
	
	toggleCheckboxDisplay('buLabel', document.adminForm.<%= GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY %>, 'bu');
	toggleCheckboxDisplay('ccLabel', document.adminForm.<%= GlobalConstants.PROPERTY_COST_CENTER_PROPERTY %>, 'cc');
	
	var val = document.adminForm.max_dept_level.value;
	
	if(val=='<%=MastersConstants.DEPARTMENT_LEVEL_4%>'){
		$("dept_level_4").style.display = '';
		$("dept_level_5").style.display = 'none';
	}else if(val=='<%=MastersConstants.DEPARTMENT_LEVEL_5%>'){
		$("dept_level_4").style.display = '';
		$("dept_level_5").style.display = '';
	}else{
		$("dept_level_4").style.display = 'none';
		$("dept_level_5").style.display = 'none';
	}
}
</script>