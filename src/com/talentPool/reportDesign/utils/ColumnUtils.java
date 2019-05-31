package com.talentPool.reportDesign.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.talentPool.budget.utils.BudgetUtils;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.ModuleSet;

public class ColumnUtils {

	public static HashMap<String, String> columnHeaderMap =  new HashMap<String, String>();
	public static HashMap<String, String> columnLabelMap =  new HashMap<String, String>();
	public static HashMap<String, String> columnDBMap =  new HashMap<String, String>();
	
	static{
		initializeColumnHeaderMap();
		initializeColumnLabelMap();
		initializeColumnDBMap();
	}
	
	public static LinkedHashMap<String, LinkedHashMap<String, String>> getColumnList(String reportType) {
		LinkedHashMap<String, LinkedHashMap<String, String>> columnMap = new LinkedHashMap<String, LinkedHashMap<String,String>>();

		if(reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES)){
			getCandidateColumns(columnMap);
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_POSITION)){
			getCandidateColumns(columnMap);
			getPositionColumns(columnMap);
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_CANDIDATES_WITH_ACTIVITY)){
			getCandidateColumns(columnMap);
			getActivityColumns(columnMap);
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_EXPENSE)){
			getExpensesColumns(columnMap);
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_POSITIONS)){
			getPositionColumns(columnMap);
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_POSITIONS_WITH_CANDIDATE)){
			getPositionColumns(columnMap);
			getCandidateColumns(columnMap);
			getStepColumns(columnMap);
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_POSITIONS_WITH_ACTIVITY)){
			getPositionColumns(columnMap);
			getCandidateColumns(columnMap);
			getActivityColumns(columnMap);
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_USERS)){
			
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_USERS_WITH_ACTIVITY)){
			
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_USERS_WITH_CANDIDATE)){
			
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_USERS_WITH_POSITION)){
			
		}else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_AUDIT)){
			getAuditColumns(columnMap);
		}
		else if(reportType.equals(ReportDesignConstants.REPORT_TYPE_BUDGET)){
			getBudgetColumns(columnMap);
		}
		return columnMap;
	}
	
	private static void initializeColumnHeaderMap() {
		columnHeaderMap.put(ReportDesignConstants.COLUMN_TYPE_CANDIDATES, "Candidate");		
		columnHeaderMap.put(ReportDesignConstants.COLUMN_TYPE_POSITION, "Position");
		columnHeaderMap.put(ReportDesignConstants.COLUMN_TYPE_PROCESS, "Process");	
		columnHeaderMap.put(ReportDesignConstants.COLUMN_TYPE_ACTIVITY, "Activity");		
		columnHeaderMap.put(ReportDesignConstants.COLUMN_TYPE_EXPENSE, "Expenses");
		columnHeaderMap.put(ReportDesignConstants.COLUMN_TYPE_AUDIT, "Audit");
		columnHeaderMap.put(ReportDesignConstants.COLUMN_TYPE_BUDGET, "Budget");
	}
	
	private static void initializeColumnLabelMap() {
		
		columnLabelMap.put(ReportDesignConstants.REPORT_SERIAL_NUMBER, "SrNo");
		
		/*** Candidate Columns***/
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_NAME, "Candidate");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CITY, "Current location");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1, "Email1");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2, "Email2");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE, "Mobile");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_HOME_PHONE, "Phone1");		
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_WORK_PHONE, "Phone2");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE, "Experience");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_CREATED, "Date created");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED, "Joining date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER, "Current employer");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC, "Current ctc");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC, "Expected ctc");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD, "Notice period");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED, "Level offered");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED, "Designation offered");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC, "Offered ctc");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMPLOYEE_CODE, "Employee code");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_IMPORTED_BY, "Imported by");		
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE, "Source");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE_TYPE, "Source Type");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS, "Skills");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SKILL_CATEGORIES, "Skill Categories");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_YOP, "Year of passing");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_BRANCH, "Branch");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DEGREE, "Degree");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_INSTITUTE, "Institute");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_GRADE, "Grade");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_FLAGS, "Flags");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STAGE_NAME, "Stage");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_GRADE_HIRE_BY_DURATION, "Hire By Duration");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_ID, "Candidate Id");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_HRMS_CODE, "HRMS Code");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_TEAM, "Team");
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_SUBJECT, "Appointment Subect");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_DATE, "Appointment From date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_TIME, "Appointment From time");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_TO_DATE, "Appointment To date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_TO_TIME, "Appointment To time");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_CREATED, "Appointment date created");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_CREATED_BY, "Appointment created by");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_MODIFIED, "Appointment date modified");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_MODIFIED_BY, "Appointment modified by");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_STATUS, "Appointment Status");
		columnLabelMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_ATTENDEES, "Interviewers");
			
		columnLabelMap.put(ReportDesignConstants.COLUMN_EXPENSE_DATE, "Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_EXPENSE_PURPOSE, "Purpose");
		columnLabelMap.put(ReportDesignConstants.COLUMN_EXPENSE_AMOUNT, "Amount");
		columnLabelMap.put(ReportDesignConstants.COLUMN_EXPENSE_REMARKS, "Remarks");
		columnLabelMap.put(ReportDesignConstants.COLUMN_EXPENSE_ENTERED_BY, "Entered By");
			
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				columnLabelMap.put(cData.getFieldId(), cData.getFieldDisplayName());		
			}
		}
		
		//RI
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SUB_CATEGORY, "Sub Category");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CATEGORY, "Category");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_PASSPORT_NUMBER, "Passport Number");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_OF_BIRTH, "Date of birth");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_BASIC, "Offered Basic");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_INPUT_SALARY_VARIABLE, "Input Salary Variable");
		
		/*** Position Columns***/
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_CODE, "Position Code");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_TITLE, "Position Title");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_OWNER, TPLabels.getLabel("global.position_owner"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_MAX_EXP, "Max Experience");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_MIN_EXP, "Min Experience");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_VACANCIES, "Vacancies");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE, "Hire By Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_DATE_CREATED, "Date created");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_CREATED_BY, "Created by");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_CLOSED_DATE, "Closed Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_CLOSED_BY, "Closed by");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_DELETED_DATE, "Deleted Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_DELETED_BY, "Deleted by");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_NOTE, "Note");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_STATUS, "Status");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_DEGREE, "Degree");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_BRANCH, "Branch");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_LOCATION, "Location");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY, "Hiring Manager");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_PRIORITY, "Priority");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_LEVEL, "Postion Level");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_REFERAL_FEE, "Referal fee");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_WALK_IN, "Walk in");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_EMPLOYEE_PORTAL, "Employee portal");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_WEB_SITE, "Web Site");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_PUBLISH_DATE, "Publish Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_RESPONSIBILITIES, "Responsibility");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_REQUIREMENTS, "Requirements");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_PRIMARY_SKILLS, "Primary Skills");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_SECONDARY_SKILLS, "Secondary Skills");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_BUSINESS_UNIT, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL));
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_COST_CENTER, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_LABEL));
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_TYPE_EXT_INT, "Position Type");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_VACANCY_TYPE, "Vacancy Type");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_REPLACEMENT_EMP_CODE, "Replaced Employee Code");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_REQUISITIONER,"Machine Requirement Contact Person");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_APPLIED_COUNT, "Applied");
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_DEPARTMENT, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1));
		columnLabelMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2));
		columnLabelMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3));
		columnLabelMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB3, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4));
		columnLabelMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB4, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5));
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				columnLabelMap.put(cData.getFieldId(), cData.getFieldDisplayName());		
			}
		}
		
		/*** ACIVITY COLUMNS***/		
		columnLabelMap.put(ReportDesignConstants.COLUMN_ACTIVITY, "Activity");
		columnLabelMap.put(ReportDesignConstants.COLUMN_ACTIVITY_BY, "Activity By");
		columnLabelMap.put(ReportDesignConstants.COLUMN_ACTIVITY_DATE, "Activity Date");
		
		/*** STEP COLUMNS***/
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_NAME, "Step Name");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_LEVEL, "Stage Name");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_IS_OPTIONAL, "Step is optional");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_IS_SCHEDULED, "Step schedulable");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_IS_INTERVIEWER_CAN_CONFIRM, "Step interviewer can confirm");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_IS_NOTIFY_TO_CANDIDATE, "Step notify to candidate");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_ASSIGN_TO, "Step Assign to users");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_SCHEDULED_BY, "Step scheduled by");
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_DECISION_MAKER, "Step decision maker");
		
		/*** AUDIT COLUMNS ***/
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_CRATED_BY, "Action By");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_TYPE, "Audit Type");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_ENTITY_FIELD, "Entity Field");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_DATE_CREATED, "Date Created");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_TIME_CREATED, "Time Created");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_DESC, "Description");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_USER_NAME, "User");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_CANDIDATE, "Candidate");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_POSITION, "Position");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_REPORT_LEVEL, "Report Level");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_ROLE, "Role");
		columnLabelMap.put(ReportDesignConstants.COLUMN_AUDIT_REPORT, "Report");
		
		/*** BUDGET COLUMNS ***/
		if(ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()){		
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_NAME, "Budget Name");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_OWNER, "Owner");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_DEPT, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1));
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_SUB_DEPT, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2));
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_SUB_SUB_DEPT, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3));
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_START_DATE, "Start Date");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_END_DATE, "End Date");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_STATUS, "Status");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_HEAD_COUNT, "Total Head Count");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_AVAILABLE_HEAD_COUNT, "Available Head Count");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_COMMITTED_HEAD_COUNT, "Committed Head Count");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_USED_HEAD_COUNT, "Used Head Count");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_POSITION_NAMES, "Positions Names");
			columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_POSITION_CODES, "Positions Codes");
		}
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_GRADE, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL));
		columnLabelMap.put(ReportDesignConstants.COLUMN_BUDGET_BAND, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL));
		
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED, TPLabels.getLabel("report.label.position_date_approved"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_RECRUITERS, TPLabels.getLabel("report.label.position_recruiters"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE, TPLabels.getLabel("report.label.applicant_status_message"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE_UPDATED_DATE, TPLabels.getLabel("report.label.applicant_status_message_created_at"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_TODO_DUE_DATE, TPLabels.getLabel("report.label.todo_due_date"));
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_TEMPLATE_NAME, "Requisition Approval Template");
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_NAME, "Process Step");
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_DATE, "Process Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STAGE, "Process Stage");
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_PROCESSED_BY, "Recruitment Manager");
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_EMPTY, TPLabels.getLabel("report.label.empty_column"));
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_REJECTED_BY, TPLabels.getLabel("position_summary.label.rejected_by"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_REJECTED_DATE, TPLabels.getLabel("position_summary.label.rejected_date"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_BLACKLISTED_BY, TPLabels.getLabel("position_summary.label.blacklisted_by"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_DATE_OF_BLACKLIST, TPLabels.getLabel("position_summary.label.blacklisted_date"));
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_REASON_FOR_BLACKLIST, TPLabels.getLabel("position_summary.label.blacklisted_reason"));
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_NAME, "Name");
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITIONER_NUMBER, "Rq.No");
		columnLabelMap.put(ReportDesignConstants.COLUMN_DOJ, "DOJ");
		columnLabelMap.put(ReportDesignConstants.COLUMN_DESIGNATION, "Designation");
		columnLabelMap.put(ReportDesignConstants.COLUMN_BAND, "Band");
		columnLabelMap.put(ReportDesignConstants.COLUMN_LEVEL, "Level");
		columnLabelMap.put(ReportDesignConstants.COLUMN_BAND_LEVEL, "Band-Level");
		columnLabelMap.put(ReportDesignConstants.COLUMN_ADDRESS_LINE_ONE, "Address Line 1");
		columnLabelMap.put(ReportDesignConstants.COLUMN_ADDRESS_LINE_TWO, "Address Line 2");
		columnLabelMap.put(ReportDesignConstants.COLUMN_ADDRESS_LINE_THREE, "Address Line 3");
		columnLabelMap.put(ReportDesignConstants.COLUMN_TARGET_CTC, "target CTC");
		
		columnLabelMap.put(ReportDesignConstants.COLUMN_HIRE_TYPE,"Hire Type");
		columnLabelMap.put(ReportDesignConstants.COLUMN_INTERVIEW_SCORE,"Interview Score/ PL Level");
		columnLabelMap.put(ReportDesignConstants.COLUMN_COMMUNICATION_SCORE,"Communication Score");
		columnLabelMap.put(ReportDesignConstants.COLUMN_LATERAL_CAMPUS_INTERN ,"Lateral/Campus/Intern");
		columnLabelMap.put(ReportDesignConstants.COLUMN_LOCATION ,"Location");
		columnLabelMap.put(ReportDesignConstants.COLUMN_LOCAL_OUTSTATION,"Local/Outstation");
		columnLabelMap.put(ReportDesignConstants.COLUMN_LAST_COMPANY ,"Last Company");
		columnLabelMap.put(ReportDesignConstants.COLUMN_TIER ,"Tier");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CONTACT_NO ,"Contact No");
		columnLabelMap.put(ReportDesignConstants.COLUMN_HIKE_PERCENT ,"% Hike");
		columnLabelMap.put(ReportDesignConstants.COLUMN_NOTICE_PERIOD_BUY_OUT_DAYS,"Notice Period Buy Out Days");
		columnLabelMap.put(ReportDesignConstants.COLUMN_JOINING_BONUS ,"Joining Bonus");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CONTRACTOR_COST ,"Contractor Cost");
		columnLabelMap.put(ReportDesignConstants.COLUMN_RECRUITER_NAME ,"Recruiter Name");
		columnLabelMap.put(ReportDesignConstants.COLUMN_OFFERED_APPROVAL_DATE ,"Offered Approval Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_OFFERED_DATE ,"Offered Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_EXPECTED_DOJ ,"Expected DOJ");
		columnLabelMap.put(ReportDesignConstants.COLUMN_EMP_ID,"Emp ID");
		columnLabelMap.put(ReportDesignConstants.COLUMN_POSITION_DATE_OPENED,"Requisition Allocation Date");
		columnLabelMap.put(ReportDesignConstants.COLUMN_CANDIDATE_GENDER,"Gender");
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITION_STATUS,"Requisition Status");
		columnLabelMap.put(ReportDesignConstants.COLUMN_REQUISITION_RAISE_DATE,"Requisition Raise Date");
		
	
	}
	
	private static void initializeColumnDBMap() {
		
		/*** Candidate DB Columns***/
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_NAME, "tap.applicant_name");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CITY,"tap.applicant_city");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1,"tap.applicant_email1");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2,"tap.applicant_email2");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE,"tap.applicant_cell_phone");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_HOME_PHONE,"tap.applicant_home_phone");		
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_WORK_PHONE,"tap.applicant_work_phone");			
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE,getExperinceFormat("tap.applicant_working_since"));
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_CREATED,getDateFormat("tap.applicant_date_created"));
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED,getDateFormat("tap.applicant_date_joined"));
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER,"tap.applicant_current_employer");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC,"tap.current_ctc");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC,"tap.expected_ctc");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD,"tap.applicant_notice_period");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED,"tap.applicant_level_offered");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED,"tap.applicant_designation_offered");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC,"tap.offered_ctc");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMPLOYEE_CODE,"tap.employee_code");	
		
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE,"tso.source_title");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE_TYPE,"tsy.source_type");
				
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_YOP,"DATE_FORMAT(taei.educational_info_year_of_passing,'%Y')");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_BRANCH,"tbr.branch_name");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DEGREE,"tde.degree_title");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_INSTITUTE,"tin.institute_name");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_GRADE,"taei.educational_info_grade");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS,"group_concat(distinct tsk.skill)");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_IMPORTED_BY,"group_concat(distinct tus.USER_FNAME,' ',tus.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SKILL_CATEGORIES,"group_concat(distinct tsc.skill_category_description)");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_FLAGS,"group_concat(distinct tfl.flag_text)");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CUSTOM_FIELDS,"group_concat(distinct tcfa.custom_field_id,'=',ifnull(tva.string_value,ifnull(tva.number_value,tva.date_value)))");

		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_TEAM, "td.dept_name");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_SUBJECT,"tpp.appointment_subject");
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_DATE,getDateFormat("tpp.appointment_from_date"));
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_TIME,getTimeFormat("tpp.appointment_from_date"));
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_TO_DATE,getDateFormat("tpp.appointment_to_date"));
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_TO_TIME,getTimeFormat("tpp.appointment_to_date"));
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_CREATED,getDateFormat("tpp.appointment_date_created"));
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_CREATED_BY,"group_concat(distinct tuac.USER_FNAME,' ',tuac.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_MODIFIED,getDateFormat("tpp.appointment_date_modified"));
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_MODIFIED_BY,"group_concat(distinct tuam.USER_FNAME,' ',tuam.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_STATUS,"tapps.appointment_status_text");
		columnDBMap.put(ReportDesignConstants.COLUMN_APPOINTMENT_ATTENDEES,"group_concat(distinct tuaa.USER_FNAME,' ',tuaa.USER_LNAME)");
	
		/*** Position DB Columns***/
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_CODE,"tpo.position_code");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_TITLE,"tpo.position_title");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_MAX_EXP,"tpo.position_min_exp");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_MIN_EXP,"tpo.position_max_exp");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_VACANCIES,"tpo.position_no_of_openings");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE,getDateFormat("tpo.position_date_expiry"));
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_DATE_CREATED,getDateFormat("tpo.position_date_created"));
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_CLOSED_DATE,getDateFormat("tpo.position_date_closed"));
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_DELETED_DATE,getDateFormat("tpo.position_date_deleted"));
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_NOTE,"tpo.position_note");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_STATUS,"CASE WHEN tpo.position_status="+PositionConstants.POSITION_STATUS_DELETED+" THEN '"+TPLabels.getLabel("position.status.label.deleted")+"' WHEN tpo.position_status="+PositionConstants.POSITION_STATUS_OPENED+" THEN '"+TPLabels.getLabel("position.status.label.open")+"' WHEN tpo.position_status="+PositionConstants.POSITION_STATUS_CLOSED+" THEN '"+TPLabels.getLabel("position.status.label.closed")+"' WHEN tpo.position_status="+PositionConstants.POSITION_STATUS_INPROCESS+" THEN '"+TPLabels.getLabel("position.status.label.inprocess")+"' WHEN tpo.position_status="+PositionConstants.POSITION_STATUS_HOLD+" THEN '"+TPLabels.getLabel("position.status.label.onhold")+"' END");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_DEGREE,"GROUP_CONCAT(DISTINCT tdep.degree_title SEPARATOR ', ')");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_BRANCH,"CASE WHEN GROUP_CONCAT(DISTINCT tbrp.branch_name) is null then '' ELSE GROUP_CONCAT(DISTINCT tbrp.branch_name SEPARATOR ', ') END");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_LOCATION,"CASE WHEN GROUP_CONCAT(DISTINCT tlo.location_name) is null then '' ELSE GROUP_CONCAT(DISTINCT tlo.location_name SEPARATOR ', ') END");		
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_PRIORITY,"CASE WHEN tpo.position_priority="+PositionConstants.POSITION_LEVEL_LOW+" THEN '"+TPLabels.getLabel("position.priority.low")+"' WHEN tpo.position_priority="+PositionConstants.POSITION_LEVEL_MEDIUM+" THEN '"+TPLabels.getLabel("position.priority.medium")+"' WHEN tpo.position_priority="+PositionConstants.POSITION_LEVEL_HIGH+" THEN '"+TPLabels.getLabel("position.priority.high")+"' END");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_LEVEL,"tpo.position_level");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_REFERAL_FEE,"tpo.position_referal_fees");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_WALK_IN,"tpo.is_published_for_walk_in");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_EMPLOYEE_PORTAL,"tpo.is_published_for_emp_portal");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_WEB_SITE,"tpo.is_published_to_web_site");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_PUBLISH_DATE,getDateFormat("tpo.position_publish_date"));
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_RESPONSIBILITIES,"tpo.responsibilities");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_REQUIREMENTS,"tpo.requirements");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY,"group_concat(distinct tureq.USER_FNAME,' ',tureq.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_CREATED_BY,"group_concat(distinct tucre.USER_FNAME,' ',tucre.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_CLOSED_BY,"group_concat(distinct tucls.USER_FNAME,' ',tucls.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_DELETED_BY,"group_concat(distinct tudel.USER_FNAME,' ',tudel.USER_LNAME)");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_PRIMARY_SKILLS,"group_concat(distinct tskp.skill)");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_SECONDARY_SKILLS,"group_concat(distinct tsks.skill)");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT,"tdp.dept_name");
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB,"tdps.dept_name");
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB,"tdpss.dept_name");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_CUSTOM_FIELDS,"group_concat(distinct tcfp.custom_field_id,'=',ifnull(tvp.string_value,ifnull(tvp.number_value,tvp.date_value)))");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_REQUISITIONER, "CONCAT(tu.user_fname,' ', tu.user_lname)");
		
		/*** Activity DB Columns***/
		columnDBMap.put(ReportDesignConstants.COLUMN_ACTIVITY,"tact.activity");
		columnDBMap.put(ReportDesignConstants.COLUMN_ACTIVITY_BY,"group_concat(distinct tactu.USER_FNAME,' ',tactu.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_ACTIVITY_DATE,getDateFormat("tact.date_created"));
	
		columnDBMap.put(ReportDesignConstants.COLUMN_EXPENSE_DATE, getDateFormat("tco.cost_paid_date"));
		columnDBMap.put(ReportDesignConstants.COLUMN_EXPENSE_PURPOSE, "tct.cost_type");
		columnDBMap.put(ReportDesignConstants.COLUMN_EXPENSE_AMOUNT, "tco.cost_amount");
		columnDBMap.put(ReportDesignConstants.COLUMN_EXPENSE_REMARKS, "tco.cost_remark");
		columnDBMap.put(ReportDesignConstants.COLUMN_EXPENSE_ENTERED_BY, "group_concat(distinct tus.USER_FNAME,' ',tus.USER_LNAME)");

		/*** Step DB Columns***/
		//columnDBMap.put(ReportDesignConstants.COLUMN_STEP_NAME,"CASE WHEN tap.applicant_joined="+ApplicantConstants.APPLICANT_JOINED+" AND tap.applicant_step_id IS NULL THEN '"+TPLabels.getLabel("reportdesigner.applicant.joined") +"' WHEN tap.applicant_step_id IS NULl THEN '' ELSE tps.position_step_title END");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_NAME,
			"CASE WHEN tasp.position_step_id_to IN ("+SelectionProcessConstants.STEP_REJECT+","+SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT+","+SelectionProcessConstants.STEP_NOT_ATTENDED+","+SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT+") THEN '"+SelectionProcessConstants.STEP_TITLE_REJECT+"'"+
			" WHEN tasp.position_step_id_to="+SelectionProcessConstants.STEP_ON_HOLD+" THEN '"+SelectionProcessConstants.STEP_TITLE_ON_HOLD+"'"+
			" WHEN tasp.position_step_id_to="+SelectionProcessConstants.STEP_JOIN+" THEN '"+SelectionProcessConstants.STEP_TITLE_JOINED+"'"+
			" WHEN tps.position_step_title IS NULl THEN ''"+	
			" ELSE tps.position_step_title END");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_LEVEL, "CASE WHEN tps.position_step_level=0 THEN 'Shortlist' WHEN tps.position_step_level=1 THEN 'Select' WHEN tps.position_step_level=2 THEN 'Hire' ELSE '' END");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_IS_OPTIONAL, "tps.position_step_isoptional");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_IS_SCHEDULED, "tps.position_step_isscheduled");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_IS_INTERVIEWER_CAN_CONFIRM, "tps.is_interviewer_can_confirm");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_IS_NOTIFY_TO_CANDIDATE, "tps.is_notify_to_candidate");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_ASSIGN_TO, "group_concat(distinct tusat.USER_FNAME,' ',tusat.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_SCHEDULED_BY, "group_concat(distinct tussb.USER_FNAME,' ',tussb.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_DECISION_MAKER, "group_concat(distinct tusdm.USER_FNAME,' ',tusdm.USER_LNAME)");
		
		/*** AUDIT DB COLUMNS ***/
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_CRATED_BY,"CONCAT(tuc.USER_FNAME,' ',tuc.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_TYPE, "tae.audit_type");
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_ENTITY_FIELD, "tae.entity_field");
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_DATE_CREATED, getDateFormat("tae.date_created"));
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_TIME_CREATED, getTimeFormat("tae.date_created"));
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_DESC, "tae.audit_desc");		
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_USER_NAME, "CONCAT(tus.USER_FNAME,' ',tus.USER_LNAME)");
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_CANDIDATE, "tap.applicant_name");
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_POSITION, "tpo.position_title");
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_REPORT_LEVEL, "tpl.level_name");	
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_ROLE, "tpr.role_title");
		columnDBMap.put(ReportDesignConstants.COLUMN_AUDIT_REPORT, "CASE WHEN tae.entity_type=13 THEN tae.entity_id END");	
	
		/*** BUDGET DB Columns ***/
		if(ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()){		
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_NAME, "budget_item_name");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_OWNER, "owner_name");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_DEPT, "dept_name");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_SUB_DEPT, "sub_dept_name");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_SUB_SUB_DEPT, "sub_sub_dept_name");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_GRADE, "grade_name");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_BAND,"band_name");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_START_DATE, getDateFormat("start_time"));
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_END_DATE, getDateFormat("end_time"));
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_STATUS, "status");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_HEAD_COUNT, "available_head_count");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_AVAILABLE_HEAD_COUNT, "available_head_count-committed_head_count-used_head_count");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_COMMITTED_HEAD_COUNT, "committed_head_count");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_USED_HEAD_COUNT, "used_head_count");
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_POSITION_NAMES, "position_names");
			
			columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_POSITION_CODES, "position_codes");
		}
	}

	private static void getCandidateColumns(LinkedHashMap<String, LinkedHashMap<String, String>> columnMap) {
		/* Candidate Columns */
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_NAME, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_NAME));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_CITY, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_CITY));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_HOME_PHONE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_HOME_PHONE));		
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_WORK_PHONE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_WORK_PHONE));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_CREATED, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_DATE_CREATED));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_EMPLOYEE_CODE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_EMPLOYEE_CODE));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_IMPORTED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_IMPORTED_BY));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_TEAM, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_TEAM));
		
		/**/
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				columns.put(cData.getFieldId(), columnLabelMap.get(cData.getFieldId()));		
			}
		}
		
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE_TYPE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE_TYPE));
				
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_SKILL_CATEGORIES, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_SKILL_CATEGORIES));
		
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_YOP, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_YOP));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_BRANCH, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_BRANCH));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_DEGREE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_DEGREE));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_INSTITUTE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_INSTITUTE));
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_GRADE, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_GRADE));
		
		/**/
		columns.put(ReportDesignConstants.COLUMN_CANDIDATE_FLAGS, columnLabelMap.get(ReportDesignConstants.COLUMN_CANDIDATE_FLAGS));
		
		columnMap.put(ReportDesignConstants.COLUMN_TYPE_CANDIDATES, columns);
	}
	
	
	private static void getPositionColumns(LinkedHashMap<String, LinkedHashMap<String, String>> columnMap) {
		/* Position Columns */
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		columns.put(ReportDesignConstants.COLUMN_POSITION_CODE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_CODE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_TITLE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_TITLE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_STATUS, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_STATUS));
		
		columns.put(ReportDesignConstants.COLUMN_POSITION_MAX_EXP, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_MAX_EXP));
		columns.put(ReportDesignConstants.COLUMN_POSITION_MIN_EXP, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_MIN_EXP));
		columns.put(ReportDesignConstants.COLUMN_POSITION_VACANCIES, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_VACANCIES));
		columns.put(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE));		
		columns.put(ReportDesignConstants.COLUMN_POSITION_DATE_CREATED, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_DATE_CREATED));
		columns.put(ReportDesignConstants.COLUMN_POSITION_CREATED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_CREATED_BY));
		columns.put(ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY));
		columns.put(ReportDesignConstants.COLUMN_POSITION_CLOSED_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_CLOSED_DATE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_CLOSED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_CLOSED_BY));
		columns.put(ReportDesignConstants.COLUMN_POSITION_DELETED_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_DELETED_DATE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_DELETED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_DELETED_BY));
		columns.put(ReportDesignConstants.COLUMN_POSITION_DEGREE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_DEGREE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_BRANCH, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_BRANCH));
		columns.put(ReportDesignConstants.COLUMN_POSITION_LOCATION, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_LOCATION));
		columns.put(ReportDesignConstants.COLUMN_POSITION_PRIORITY, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_PRIORITY));
		columns.put(ReportDesignConstants.COLUMN_POSITION_LEVEL, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_LEVEL));
		columns.put(ReportDesignConstants.COLUMN_POSITION_REFERAL_FEE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_REFERAL_FEE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_WALK_IN, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_WALK_IN));
		columns.put(ReportDesignConstants.COLUMN_POSITION_EMPLOYEE_PORTAL, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_EMPLOYEE_PORTAL));
		columns.put(ReportDesignConstants.COLUMN_POSITION_WEB_SITE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_WEB_SITE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_PUBLISH_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_PUBLISH_DATE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_RESPONSIBILITIES, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_RESPONSIBILITIES));
		columns.put(ReportDesignConstants.COLUMN_POSITION_REQUIREMENTS, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_REQUIREMENTS));
		columns.put(ReportDesignConstants.COLUMN_POSITION_PRIMARY_SKILLS, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_PRIMARY_SKILLS));
		columns.put(ReportDesignConstants.COLUMN_POSITION_SECONDARY_SKILLS, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_SECONDARY_SKILLS));
		columns.put(ReportDesignConstants.COLUMN_POSITION_NOTE, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_NOTE));
		columns.put(ReportDesignConstants.COLUMN_POSITION_APPLIED_COUNT, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_APPLIED_COUNT));
		/**/
		columns.put(ReportDesignConstants.COLUMN_DEPARTMENT, columnLabelMap.get(ReportDesignConstants.COLUMN_DEPARTMENT));
		columns.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB, columnLabelMap.get(ReportDesignConstants.COLUMN_DEPARTMENT_SUB));
		columns.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB, columnLabelMap.get(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB));
		
		columns.put(ReportDesignConstants.COLUMN_POSITION_REQUISITIONER, columnLabelMap.get(ReportDesignConstants.COLUMN_POSITION_REQUISITIONER));
		
		/**/
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				columns.put(cData.getFieldId(), columnLabelMap.get(cData.getFieldId()));		
			}
		}		
		columnMap.put(ReportDesignConstants.COLUMN_TYPE_POSITION, columns);

	}
	
	private static void getActivityColumns(LinkedHashMap<String, LinkedHashMap<String, String>> columnMap) {
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		columns.put(ReportDesignConstants.COLUMN_ACTIVITY, columnLabelMap.get(ReportDesignConstants.COLUMN_ACTIVITY));
		columns.put(ReportDesignConstants.COLUMN_ACTIVITY_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_ACTIVITY_BY));
		columns.put(ReportDesignConstants.COLUMN_ACTIVITY_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_ACTIVITY_DATE));
		columnMap.put(ReportDesignConstants.COLUMN_TYPE_ACTIVITY, columns);
	}
	
	private static void getExpensesColumns(LinkedHashMap<String, LinkedHashMap<String, String>> columnMap) {
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		columns.put(ReportDesignConstants.COLUMN_EXPENSE_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_EXPENSE_DATE));
		columns.put(ReportDesignConstants.COLUMN_EXPENSE_PURPOSE, columnLabelMap.get(ReportDesignConstants.COLUMN_EXPENSE_PURPOSE));
		columns.put(ReportDesignConstants.COLUMN_EXPENSE_AMOUNT, columnLabelMap.get(ReportDesignConstants.COLUMN_EXPENSE_AMOUNT));
		columns.put(ReportDesignConstants.COLUMN_EXPENSE_REMARKS, columnLabelMap.get(ReportDesignConstants.COLUMN_EXPENSE_REMARKS));
		columns.put(ReportDesignConstants.COLUMN_EXPENSE_ENTERED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_EXPENSE_ENTERED_BY));
		columnMap.put(ReportDesignConstants.COLUMN_TYPE_EXPENSE, columns);
	}	
	
	private static void getStepColumns(LinkedHashMap<String, LinkedHashMap<String, String>> columnMap) {
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		columns.put(ReportDesignConstants.COLUMN_STEP_NAME, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_NAME));
		columns.put(ReportDesignConstants.COLUMN_STEP_LEVEL, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_LEVEL));
		columns.put(ReportDesignConstants.COLUMN_STEP_IS_OPTIONAL, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_IS_OPTIONAL));
		columns.put(ReportDesignConstants.COLUMN_STEP_IS_SCHEDULED, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_IS_SCHEDULED));
		columns.put(ReportDesignConstants.COLUMN_STEP_IS_INTERVIEWER_CAN_CONFIRM, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_IS_INTERVIEWER_CAN_CONFIRM));
		columns.put(ReportDesignConstants.COLUMN_STEP_IS_NOTIFY_TO_CANDIDATE, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_IS_NOTIFY_TO_CANDIDATE));
		columns.put(ReportDesignConstants.COLUMN_STEP_ASSIGN_TO, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_ASSIGN_TO));
		columns.put(ReportDesignConstants.COLUMN_STEP_SCHEDULED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_SCHEDULED_BY));
		columns.put(ReportDesignConstants.COLUMN_STEP_DECISION_MAKER, columnLabelMap.get(ReportDesignConstants.COLUMN_STEP_DECISION_MAKER));
		
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_SUBJECT, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_SUBJECT));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_DATE));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_TIME, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_FROM_TIME));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_TO_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_TO_DATE));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_TO_TIME, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_TO_TIME));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_CREATED, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_CREATED));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_CREATED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_CREATED_BY));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_MODIFIED, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_DATE_MODIFIED));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_MODIFIED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_MODIFIED_BY));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_STATUS, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_STATUS));
		columns.put(ReportDesignConstants.COLUMN_APPOINTMENT_ATTENDEES, columnLabelMap.get(ReportDesignConstants.COLUMN_APPOINTMENT_ATTENDEES));
		
		columnMap.put(ReportDesignConstants.COLUMN_TYPE_PROCESS, columns);
	}
	
	private static void getAuditColumns(LinkedHashMap<String, LinkedHashMap<String, String>> columnMap) {
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		columns.put(ReportDesignConstants.COLUMN_AUDIT_DESC, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_DESC));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_DATE_CREATED, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_DATE_CREATED));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_TIME_CREATED, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_TIME_CREATED));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_TYPE, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_TYPE));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_CRATED_BY, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_CRATED_BY));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_ENTITY_FIELD, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_ENTITY_FIELD));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_USER_NAME, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_USER_NAME));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_CANDIDATE, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_CANDIDATE));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_POSITION, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_POSITION));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_REPORT_LEVEL, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_REPORT_LEVEL));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_ROLE, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_ROLE));
		columns.put(ReportDesignConstants.COLUMN_AUDIT_REPORT, columnLabelMap.get(ReportDesignConstants.COLUMN_AUDIT_REPORT));
		columnMap.put(ReportDesignConstants.COLUMN_TYPE_AUDIT, columns);
	}	
	

	private static void getBudgetColumns(LinkedHashMap<String, LinkedHashMap<String, String>> columnMap) {
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		columns.put(ReportDesignConstants.COLUMN_BUDGET_NAME, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_NAME));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_OWNER, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_OWNER));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_DEPT, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_DEPT));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_SUB_DEPT, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_SUB_DEPT));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_SUB_SUB_DEPT, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_SUB_SUB_DEPT));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_GRADE, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_GRADE));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_BAND, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_BAND));		
		columns.put(ReportDesignConstants.COLUMN_BUDGET_START_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_START_DATE));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_END_DATE, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_END_DATE));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_STATUS, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_STATUS));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_HEAD_COUNT, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_HEAD_COUNT));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_AVAILABLE_HEAD_COUNT, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_AVAILABLE_HEAD_COUNT));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_COMMITTED_HEAD_COUNT, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_COMMITTED_HEAD_COUNT));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_USED_HEAD_COUNT, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_USED_HEAD_COUNT));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_POSITION_NAMES, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_POSITION_NAMES));
		columns.put(ReportDesignConstants.COLUMN_BUDGET_POSITION_CODES, columnLabelMap.get(ReportDesignConstants.COLUMN_BUDGET_POSITION_CODES));
		columnMap.put(ReportDesignConstants.COLUMN_TYPE_BUDGET, columns);
	}	
	
	
	private static String getDateFormat(String dateStr){
		dateStr = "DATE_FORMAT("+dateStr+",'%d-%b-%Y')";
		return dateStr;
	}
	
	private static String getTimeFormat(String timeStr){
		timeStr = "DATE_FORMAT("+timeStr+",'%h:%i %p')";
		return timeStr;
	}
	
	public static String getExperinceFormat(String expStr){
		expStr = "concat(YEAR(FROM_DAYS(DATEDIFF(CURDATE() ,"+expStr+"))),' ','"+Utils.EXP_YRS+"',' ', MONTH(FROM_DAYS(DATEDIFF(CURDATE(),"+expStr+"))),' ','"+Utils.EXP_MOS+"')";
		return expStr;
	}
	
	public static String getColumnLabel(String key){
		return columnLabelMap.get(key);
	}
}
