/**
 * 
 */
package com.talentPool.positions;

import com.talentPool.common.properties.TPLabels;

/**
 * @author ahmed
 * 
 */
public class PositionConstants {
	public static final String POSITION_STATUS_DELETED = "0";
	public static final String POSITION_STATUS_OPENED = "1";
	public static final String POSITION_STATUS_CLOSED = "2";
	public static final String POSITION_STATUS_INPROCESS = "3";
	public static final String POSITION_STATUS_REJECTED = "4";
	public static final String POSITION_STATUS_HOLD = "5";
	public static final String POSITION_STATUS_TEMPLATE = "6";
	
	
	public static final String POSITION_STATUS_TO_BE_OPENED = "7";
	public static final String POSITION_STATUS_MY_APPROVAL_PENDING = "8";
	
	public static final String POSITION_STATUS_DROPPED = "9";
	    
	public static final String POSITION_STATUS_OVERDUE="10"; //this status is not stored in DB used only for display purposes 
    
	public static final String POSITION_SKILL_PRIMARY = "0";
	public static final String POSITION_SKILL_SECONDARY = "1";

	public static final String STEP_DELETED = "0";
	public static final String STEP_ACTIVE = "1";

	public static final int STEP_OPTIONAL = 1;
	public static final int STEP_MANDATORY = 0;

	public static final int STEP_DEFAULT = 1;
	public static final int STEP_NOT_DEFAULT = 0;

	public static final int STEP_SCHEDULED = 1;
	public static final int STEP_NOT_SCHEDULED = 0;
	
	public static final int STEP_NOTIFY_PROGRESS_TO_CANDIDATE = 1;
	public static final int STEP_DONT_NOTIFY_PROGRESS_TO_CANDIDATE = 0;

	public static final int STEP_INTERVIEWER_CAN_CONFIRM = 1;
	public static final int STEP_INTERVIEWER_CANNOT_CONFIRM = 0;
	
	public static final int STEP_DECISION_MAKER_SAMEAS_ASSIGNED_TO = 1;
	public static final int STEP_DECISION_MAKER_NOT_SAMEAS_ASSIGNED_TO = 0;

	public static final int RESPONSIBLE_FOR_SCHEDULING = 1;
	public static final int NOT_RESPONSIBLE_FOR_SCHEDULING = 0;
	
	public static final int RESPONSIBLE_FOR_INTERVIEW = 1;
	public static final int RESPONSIBLE_FOR_DECISION = 1;
	

	public static final String MESSAGE_DEFAULT = "1";
	public static final String MESSAGE_NOT_DEFAULT = "0";

	public static final String STEP_LEVEL_NONE = "-2";
	public static final String STEP_LEVEL_IMPORT = "-1";
	public static final String STEP_LEVEL_SHORTLIST = "0";
	public static final String STEP_LEVEL_SELECT = "1";
	public static final String STEP_LEVEL_ACCEPT = "2";
	public static final String STEP_LEVEL_JOIN = "3";

	public static final String DIR_ADD_POSITION = "a";
	public static final String DIR_VIEW_POSITION = "v";
	public static final String DIR_EDIT_POSITION = "e";
	public static final String DIR_COPY_POSITION = "c";
	
	public static final String MODE_POSITION_HOME = "positionsHome";

	public static final String MODE_DESCRIPTION = "description";
	public static final String MODE_REQUIREMENTS = "requirements";
	public static final String MODE_HIRING_PROCESS = "hiringProcess";
	public static final String MODE_APPROVAL = "approval";

	public static final String MODE_SAVE_DESCRIPTION = "saveDescription";
	public static final String MODE_SAVE_REQUIREMENTS = "saveRequirements";
	public static final String MODE_SAVE_HIRING_PROCESS = "saveHiringProcess";

	public static final String MODE_ADD_POSITION = "addPosition";
	public static final String MODE_COPY_POSITION = "copyPosition";
	
	public static final String DEST_DESCRIPTION = "description";
	public static final String DEST_REQUIREMENTS = "requirements";
	public static final String DEST_HIRING_PROCESS = "hiringProcess";
	public static final String DEST_APPROVAL = "approval";

	public static final String MODE_COPY_HIRING_PROCESS = "copyHiringProcess";

	public static final int AUTHORIZED_TO_MOVE = 1;
	public static final int NOT_AUTHORIZED_TO_MOVE = 0;

	public static final int ACTIVE = 1;
	public static final int DEACTIVE = 0;

	public static final boolean REQUISITION_MANAGEMENT_ENABLED = true;

	public static final String POSITION_LEVEL_LOW = "0";
	public static final String POSITION_LEVEL_MEDIUM = "1";
	public static final String POSITION_LEVEL_HIGH = "2";

	public static final String SUB_MODE_ADD = "A";
	public static final String SUB_MODE_EDIT = "E";
	public static final String SUB_MODE_VIEW = "V";
	
	public static final String PUBLISH_WALK_IN = "1";
	public static final String PUBLISH_EMPLOYEE_PORTAL = "2";
	public static final String PUBLISH_VENDOR_PORTAL = "3";
	public static final String PUBLISH_SOCIAL_MEDIA = "4";
	
	public static final String PUBLISHED = "1";
	public static final String UNPUBLISHED = "0";
	
	public static final String POSITION_PUBLISHED_FOR_WALK_IN = "1";
	public static final String POSITION_NOT_PUBLISHED_FOR_WALK_IN = "0";
	
	public static final String POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL = "1";
	public static final String POSITION_NOT_PUBLISHED_FOR_EMPLOYEE_PORTAL = "0";
	
	public static final String FIELD_SHOW = "1";
	public static final String FIELD_NOT_SHOW = "0";
	public static final String FIELD_MANDATORY = "1";
	public static final String FIELD_NOT_MANDATORY = "0";
	
	public static final String FIELD_TYPE_MANDATORY = "0";
	public static final String FIELD_TYPE_NORMAL = "1";
	public static final String FIELD_TYPE_CUSTOM = "2";
	public static final String FIELD_TYPE_REQUIRMENT = "3";
	public static final String FIELD_TYPE_CUSTOM_REQUIRMENT = "4";
	
	/***************** Auto Generate Code **********************/
	public static final String PATTERN_POSITION_CODE_COMPONENT = "(\\{[pslymdn]+\\})";
	
	public static final String RULE_GO_TO_DATABASE = "0";
	public static final String RULE_GO_TO_INBOX = "1";
	public static final String RULE_MANDATORY = "2";
	public static final String MANDATORY_EXPRIENCE = "0";
	public static final String MANDATORY_EDUCATION = "1";
	public static final String MANDATORY_BRANCH = "2";
	public static final String MANDATORY_INSTITUTE = "3";
	public static final String MANDATORY_CURRENT_LOCATION = "4";
	
	public static final String RULE_MANDATORY_CHK_BOX_SELECTED = "1";
	public static final String RULE_MANDATORY_CHK_BOX_NOT_SELECTED = "0";
	public static final String AUTOCOMPLETE_INSTITUTE = "INST";
	public static final String AUTOCOMPLETE_REPLACE_CODE = "REC";
	public static final String FRESHER = "1";
	
	public static final String POSITION_PUBLISHED_TO_WEBSITE = "1";
	public static final String POSITION_NOT_PUBLISHED_TO_WEBSITE = "0";
	
	public static final String VENDOR_PORTAL = "vp";
	public static final String EMPLOYEE_PORTAL = "ep";
	public static final String CORPORATE_WEBSITE = "cw";
	public static final String WALK_INS = "wi";
	public static final String SOCIAL_MEDIA = "sm";
	public static final String NAUKRI_PORTAL = "np";
	
	public static final String EMPLOYEE_ANNOUNCEMENTS = "ea";
	
	public static final String POSITION_PUBLISHED = "1";
	public static final String POSITION_NOT_PUBLISHED = "0";
	
	public static final String LIST_BY_POSITIONS = "0";
	public static final String LIST_BY_DRAFTS = "1";
	
	public static final String ANY_BRANCH = TPLabels.getLabel("common.selectlist.any");
	
	public static final String FILTER_POSITION = "1";
	public static final String FILTER_DEPARTMENT = "2";
	public static final String FILTER_SUB_DEPARTMENT = "3";
	public static final String FILTER_SUB_SUB_DEPARTMENT = "4";
	public static final String FILTER_SUB3_DEPARTMENT = "11";
	public static final String FILTER_SUB4_DEPARTMENT = "12";
	public static final String FILTER_LOCATION = "5";
	public static final String FILTER_STATUS = "6";
	public static final String FILTER_RECRUITER = "7";
	public static final String FILTER_PRIMARY_SKILLS = "8";
	public static final String FILTER_POSITION_STATUS = "9";
	public static final String FILTER_CUSTOM_FIELD = "10";
	public static final String FILTER_POSITION_OWNER = "13";
	public static final String FILTER_POSITION_TYPE = "14";
	
	public static final String LIST_POSITIONS = "1";
	public static final String LIST_TEMPLATES = "2";
	
	//Constant for position published to employee for referring and applying 
	public static final String POSITIONS_EMPLOYEE_APPLY_REFER = "0";
	public static final String POSITIONS_EMPLOYEE_APPLY = "1";
	public static final String POSITIONS_EMPLOYEE_REFER = "2";

	//Position Type of Vacancy
	public static final String POSITIONS_TYPE_OF_VACANCY_FRESH = "1";
	public static final String POSITIONS_TYPE_OF_VACANCY_REPLACEMENT = "2";
	
	// Type of Position External or Internal 
	public static final String POSITIONS_TYPE_EXTERNAL = "1";
	public static final String POSITIONS_TYPE_INTERNAL = "2";
	
	//Is the position a clone of a existing position.
	public static final String POSITIONS_NOT_CLONE = "0";
	public static final String POSITIONS_CLONE = "1";	

	// Position Drop Reasons
		public static final String POSITION_DROP_OPPORTUNITY_LOST = "1";
		public static final String POSITION_DROP_PROJECT_DROPPED = "2";
		public static final String POSITION_DROP_BUFFER_INDENT = "3";
		public static final String POSITION_DROP_TBD1 = "4";
		public static final String POSITION_DROP_TBD2 = "5";
		public static final String POSITION_DROP_OTHERS = "6";
	//position applied
		public static final String POSITION_STATUS_APPLIED = "1";
}