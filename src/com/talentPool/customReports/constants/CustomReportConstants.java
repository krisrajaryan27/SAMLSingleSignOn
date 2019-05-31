package com.talentPool.customReports.constants;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;


public class CustomReportConstants {
	
	public static final String CUSTOM_REPORT_FILE_PATH;
	public static final String CUSTOM_REPORT_ABSOLUTE_FILE_PATH;
	
	static {
		CUSTOM_REPORT_FILE_PATH = TPApplicationProperties.getProperty("custom_reports.file_path");
		CUSTOM_REPORT_ABSOLUTE_FILE_PATH = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), CUSTOM_REPORT_FILE_PATH);
	}
	
	public static final int CUSTOM_FIELD_NUMBER=20;
	public static final int CANDIDATE_CUSTOM_FIELD_NUMBER=30;
	
	//Table Type
	public static final String CATEGORY_CANDIDATE 		= "1";
	public static final String CATEGORY_POSITION 		= "2";
	public static final String CATEGORY_PROCESS_STATUS 	= "3";
	public static final String CATEGORY_ACTIVITY 		= "4";
	public static final String CATEGORY_USER 			= "5";
	
	//FILTERS
	public static final String FILTER_AS_OF_DATE = "1";
	public static final String FILTER_DATE = "2";
	public static final String FILTER_POSITION_STATUS = "3";
	public static final String FILTER_POSITION_OWNER = "4";	
	public static final String FILTER_DEPARTMENT = "5";
	public static final String FILTER_POSITION = "6";
	public static final String FILTER_USER_ROLE	= "7";
	public static final String FILTER_ACTIVITY_USER = "8";
	public static final String FILTER_SOURCE_CATEGORY = "9";
	public static final String FILTER_SOURCE = "10";
	public static final String FILTER_STAGE	= "11";
	public static final String FILTER_STEP = "12";
	public static final String FILTER_PROCESS_USER = "13";
	public static final String FILTER_ACTIVITY_DATE = "14";
	public static final String FILTER_OFFER_DATE = "15";
	public static final String FILTER_JOINING_DATE = "16";
	
	public static final int MAX_CANDIDATE_NAMES_IN_REPORT	= 20;
	
	public static final String CR_ADD = "1";
	public static final String CR_EDIT = "2";
	
	public static final String CR_KIND_CROSS_TAB = "1";
	public static final String CR_KIND_GROUP = "2";
	public static final String CR_KIND_NORMAL = "3";
	
}
