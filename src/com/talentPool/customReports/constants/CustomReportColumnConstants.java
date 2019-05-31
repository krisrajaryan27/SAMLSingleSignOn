package com.talentPool.customReports.constants;

import org.apache.commons.collections.bidimap.DualHashBidiMap;

/**
 * @author PraveenK
 * @since  Jan 23, 2012
 */
public class CustomReportColumnConstants {
	
	public static final String APPLICANT_ID = "C01";
	public static final String APPLICANT_NAME = "C02";
	public static final String APPLICANT_EMAIL1 = "C03";
	public static final String APPLICANT_EMAIL2 = "C04";
	public static final String APPLICANT_CELL_PHONE = "C05";
	public static final String APPLICANT_HOME_PHONE = "C06";
	public static final String APPLICANT_WORK_PHONE = "C07";
	public static final String APPLICANT_LOCATION = "C08";
	public static final String APPLICANT_SKILLS = "C09";
	public static final String BRANCH = "C10";
	public static final String DEGREE = "C11";
	public static final String YOP = "C12";
	public static final String INSTITUTE = "C13";
	public static final String DATE_OF_BIRTH = "C14";
	public static final String EXPERIENCE = "C15";
	public static final String CURRENT_EMPLOYER = "C16";
	public static final String APPLICANT_DATE_CREATED = "C17";
	public static final String APPLICANT_DATE_JOINED = "C18";
	public static final String EMPLOYEE_CODE = "C19";
	public static final String CURRENT_CTC = "C20";
	public static final String EXPECTED_CTC = "C21";
	public static final String APPLICANT_NOTICE_PERIOD = "C22";
	public static final String APPLICANT_HRMS_CODE = "C23";
	public static final String FLAGS = "C24";
	public static final String PASSPORT_NUMBER = "C25";
	public static final String RESUME_TYPE = "C26";
	public static final String SOURCE = "C27";
	public static final String SOURCE_TYPE = "C28";
	public static final String LAST_STATUS_MESSAGE = "C29";
	public static final String LAST_FEEDBACK_ENTERED = "C30";
	public static final String APPLICANT_IMPORTED_BY = "C31";
	public static final String APPLICANT_CURRENT_STEP_NAME = "C32";
	public static final String CANDIDATE_CUSTOM_FIELD_PREFIX = "CCF";
	
	public static final String POSITION_CODE = "P01";
	public static final String POSITION_TITLE = "P02";
	public static final String VACANCIES = "P03";
	public static final String POSITION_STATUS = "P04";
	public static final String OWNER = "P05";
	public static final String REQUESTED_BY = "P06";
	public static final String HIRE_BY_DATE = "P07";
	public static final String CREATED_BY = "P08";
	public static final String DATE_CREATED = "P09";
	public static final String DATE_APPROVED = "P10";
	public static final String POSITION_DEPARTMENT = "P11";
	public static final String SUB_DEPARTMENT = "P12";
	public static final String GROUP = "P13";
	public static final String BUDGET = "P14";
	public static final String GRADE = "P15";
	public static final String BAND = "P16";
	public static final String DEPT_LEVEL_3 = "P17";
	public static final String DEPT_LEVEL_4 = "P18";
	public static final String POSITION_NOTE = "P19";
	public static final String PRIORITY = "P20";
	public static final String LEVEL = "P21";
	public static final String REFERRAL_FEE = "P22";
	public static final String VACANCY_TYPE = "P23";
	public static final String REPLACEMENT_EMP_CODE = "P24";
	public static final String SKILLS = "P25";
	public static final String LOCATIONS = "P26";
	public static final String COST_CENTER = "P27";
	public static final String BUSINESS_UNIT = "P28";
	public static final String ACTIVITY_USER_ID = "A01";
	public static final String ACTIVITY_BY = "A02";
	public static final String ACTIVITY_DATE = "A03";
	public static final String ACTIVITY_DATE_STRING_FORMAT = "A031";
	public static final String ACTIVITY_WEEK = "A04";
	public static final String ACTIVITY_MONTH = "A05";
	public static final String IMPORTED = "A06";
	public static final String EMAIL_RECIEVED = "A07";
	public static final String EMAIL_SENT = "A08";
	public static final String APPOINTMENT = "A09";
	public static final String INTERVIEW = "A10";
	public static final String MESSAGES = "A11";
	public static final String PHONE = "A12";
	public static final String NOTE = "A13";
	public static final String STATUS_MESSAGE = "A14";
	public static final String SHORTLISTED = "A15";
	public static final String PROCESS_USER_ID = "S01";
	public static final String PROCESS_USER = "S02";
	public static final String PROCESS_DATE = "S03";
	public static final String PROCESS_DATE_STRING_FORMAT = "S031";
	public static final String PROCESS_MONTH_SEQUENCE = "S04";
	public static final String PROCESS_MONTH = "S05";
	public static final String PROCESS_WEEK = "S06";
	public static final String STEP_ID = "S07";
	public static final String STEP_RANK = "S08";
	public static final String STEP_NAME = "S09";
	public static final String STEP_LEVEL = "S10";
	public static final String STAGE_NAME = "S11";
	public static final String EXISTING = "S12";
	public static final String ADDED = "S13";
	public static final String SELECTED = "S14";
	public static final String INPROCESS = "S15";
	public static final String REJECTED = "S16";
	public static final String STATUS_AS_OF_DATE = "S17";
	public static final String PROCESS_WEEK_SEQUENCE = "S18";
	
	public static final String USER_PROCESS_DATE  = "S19";
	public static final String USER_PROCESS_DATE_STRING_FORMAT 	= "S191";
	public static final String USER_PROCESS_MONTH_SEQUENCE 		= "S20";
	public static final String USER_PROCESS_MONTH = "S21";
	public static final String USER_PROCESS_WEEK  = "S22";
	public static final String USER_ADDED = "S23";
	public static final String USER_SELECTED = "S24";
	public static final String USER_REJECTED = "S25";
	public static final String USER_STATUS_AS_OF_DATE 		= "S26";
	public static final String USER_PROCESS_WEEK_SEQUENCE 	= "S27";
	
	private static DualHashBidiMap processUserColumnsMap = null;
	
	static {
		processUserColumnsMap = new DualHashBidiMap();
		processUserColumnsMap.put(PROCESS_DATE, USER_PROCESS_DATE);
		processUserColumnsMap.put(PROCESS_DATE_STRING_FORMAT, USER_PROCESS_DATE_STRING_FORMAT);
		processUserColumnsMap.put(PROCESS_MONTH_SEQUENCE, USER_PROCESS_MONTH_SEQUENCE);
		processUserColumnsMap.put(PROCESS_MONTH, USER_PROCESS_MONTH);
		processUserColumnsMap.put(PROCESS_WEEK, USER_PROCESS_WEEK);
		processUserColumnsMap.put(ADDED, USER_ADDED);
		processUserColumnsMap.put(SELECTED, USER_SELECTED);
		processUserColumnsMap.put(REJECTED, USER_REJECTED);
		processUserColumnsMap.put(STATUS_AS_OF_DATE, USER_STATUS_AS_OF_DATE);
		processUserColumnsMap.put(PROCESS_WEEK_SEQUENCE, USER_PROCESS_WEEK_SEQUENCE);
	}
	
	public static DualHashBidiMap getProcessUserColumnsMap(){
		return processUserColumnsMap;
	}
		
	public static final String POSITION_ID = "P29";

}
