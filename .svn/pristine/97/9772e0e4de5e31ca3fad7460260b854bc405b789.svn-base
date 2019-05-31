package com.talentPool.dynamicReports.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.user.manager.PermissionSet;

public class DynamicReportsColumnUtils {
	
	private static HashMap<String, String> columnDBMap =  new HashMap<String, String>(); 
	
	static {
		initColumnDBMapping();
	}
	
	public static LinkedHashMap<String,String> getOptionalFields(PermissionSet permissionSet, String reportName, String reportType) {
		return getColumnList(permissionSet, reportName, reportType, true, false);
	}
	
	public static LinkedHashMap<String,String> getMandatoryFields(PermissionSet permissionSet, String reportName, String reportType) {
		return getColumnList(permissionSet, reportName, reportType, false, true);
	}
	
	public static LinkedHashMap<String,String> getAllFields(PermissionSet permissionSet, String reportName, String reportType) {
		return getColumnList(permissionSet, reportName, reportType, true, true);
	}

	
	public static LinkedHashMap<String,String> getColumnList(PermissionSet permissionSet,String reportName, String reportType, boolean optional, boolean mandatory) {
		LinkedHashMap<String,String> columnMap = null;
		if(ReportVersionConstants.REPORT_PENDING_OFFER.equals(reportName)){
			columnMap = getPendingOfferColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_REJECTED_CANDIDATES.equals(reportName)){
			columnMap = getRejectedCandidatesColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_TIME_TO_HIRE.equals(reportName)){
			columnMap = getTimeToHireColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_HIRING_ACTIVITY.equals(reportName)){
			if(ReportConstants.REPORT_TYPE_DETAILS.equals(reportType)){
				columnMap = getHiringActivityDetailsColumnMap(permissionSet,optional,mandatory);
			}else if(ReportConstants.REPORT_TYPE_SUMMARY.equals(reportType)){
				columnMap = getHiringActivitySummaryColumnMap(optional,mandatory);
			}else{
				columnMap = getHiringActivityDetailsColumnMap(permissionSet,optional,mandatory);
			}
		}else if(ReportVersionConstants.REPORT_HIRING_FUNNEL.equals(reportName)){
			columnMap = getHiringFunnelDetailsColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_DATEWISE_HIRING_REPORT.equals(reportName)){
			columnMap = getDatewiseHiringColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_JOINER.equals(reportName)){
			columnMap = getJoinerReportColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_CLOSED_POSITION_TAT.equals(reportName)){
			columnMap = getClosedPositionTATColumnMap(optional,mandatory);
		}else if(ReportVersionConstants.REPORT_JOINED.equals(reportName)){
			columnMap = getJoinerReportColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_ALL_CANDIDATES.equals(reportName)){
			columnMap = getAllCandidatesColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.REPORT_OFFER_CTC.equals(reportName)){
			columnMap = getAllCTCDetailsColumnMap(permissionSet,optional,mandatory);
		}else if(ReportVersionConstants.ONE_STOP_FILE_REPORT.equals(reportName)){
			columnMap = getAllOneStopFileReortColumns(permissionSet,optional,mandatory);
		}
		return columnMap;
	}
	
	
	private static LinkedHashMap<String, String> getAllOneStopFileReortColumns(PermissionSet permissionSet,
			boolean optional, boolean mandatory) {
		   LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
			addColumn(columnMap,(ReportDesignConstants.REPORT_SERIAL_NUMBER),TPLabels.getLabel("one_stop_file_report.field.sr_no") );
			addColumn(columnMap,(ReportDesignConstants.COLUMN_REQUISITIONER_NUMBER), TPLabels.getLabel("one_stop_file_report.field.Requisition_Number"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_REQUISITION_RAISE_DATE), TPLabels.getLabel("one_stop_file_report.field.Requisition_Raise_Date"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_DEPARTMENT_SUB), TPLabels.getLabel("one_stop_file_report.field.Sub_BU"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_HIRE_TYPE), TPLabels.getLabel("one_stop_file_report.field.Hire_Type"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_POSITION_DATE_OPENED), TPLabels.getLabel("one_stop_file_report.field.Requisition_Allocation_Date"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE), TPLabels.getLabel("one_stop_file_report.field.Source"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE_TYPE), TPLabels.getLabel("one_stop_file_report.field.Source_Name"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_NAME), TPLabels.getLabel("one_stop_file_report.field.Candidate_Name"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_GENDER), TPLabels.getLabel("one_stop_file_report.field.Gender"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_DEGREE), TPLabels.getLabel("one_stop_file_report.field.Qualification"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS), TPLabels.getLabel("one_stop_file_report.field.Primary_Skills"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD), TPLabels.getLabel("one_stop_file_report.field.Notice_Period"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_INTERVIEW_SCORE), TPLabels.getLabel("one_stop_file_report.field.Interview_Score"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_COMMUNICATION_SCORE), TPLabels.getLabel("one_stop_file_report.field.Communication_Score"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_LATERAL_CAMPUS_INTERN), TPLabels.getLabel("one_stop_file_report.field.LateralCampusIntern"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_LOCATION), TPLabels.getLabel("one_stop_file_report.field.Location"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_LOCAL_OUTSTATION), TPLabels.getLabel("one_stop_file_report.field.LocalOutstation"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_LAST_COMPANY), TPLabels.getLabel("one_stop_file_report.field.Last_Company"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_TIER), TPLabels.getLabel("one_stop_file_report.field.Tier"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CONTACT_NO), TPLabels.getLabel("one_stop_file_report.field.Contact_Details"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED), TPLabels.getLabel("one_stop_file_report.field.Offered_Designation"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE), TPLabels.getLabel("one_stop_file_report.field.Experience"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC), TPLabels.getLabel("one_stop_file_report.field.Current_CTC"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC), TPLabels.getLabel("one_stop_file_report.field.Offered_CTC"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_HIKE_PERCENT), TPLabels.getLabel("one_stop_file_report.field.Hike_Percent"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_NOTICE_PERIOD_BUY_OUT_DAYS), TPLabels.getLabel("one_stop_file_report.field.Notice_Period_Buy_Out_Days"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_JOINING_BONUS), TPLabels.getLabel("one_stop_file_report.field.Joining_Bonus"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_CONTRACTOR_COST), TPLabels.getLabel("one_stop_file_report.field.Contractor_Cost"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_RECRUITER_NAME), TPLabels.getLabel("one_stop_file_report.field.Recruiter_Name"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_BAND), TPLabels.getLabel("one_stop_file_report.field.Band"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_OFFERED_APPROVAL_DATE), TPLabels.getLabel("one_stop_file_report.field.Offered_Approval_Date"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_OFFERED_DATE), TPLabels.getLabel("one_stop_file_report.field.Offered_Date"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_EXPECTED_DOJ), TPLabels.getLabel("one_stop_file_report.field.Expected_DOJ"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_EMP_ID), TPLabels.getLabel("one_stop_file_report.field.Emp_ID"));
			addColumn(columnMap,(ReportDesignConstants.COLUMN_DOJ), TPLabels.getLabel("one_stop_file_report.field.DOJ"));	
			addColumn(columnMap,(ReportDesignConstants.COLUMN_REQUISITION_STATUS), TPLabels.getLabel("one_stop_file_report.field.Requisition_Status"));
		 return columnMap;
	}
	
	private static LinkedHashMap<String, String> getAllCTCDetailsColumnMap(PermissionSet permissionSet,
			boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			addColumn(columnMap,ReportDesignConstants.COLUMN_EMPTY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_REQUISITIONER_NUMBER);
			addColumn(columnMap,ReportDesignConstants.COLUMN_DOJ);
			addColumn(columnMap,ReportDesignConstants.COLUMN_DESIGNATION);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BAND);
			addColumn(columnMap,ReportDesignConstants.COLUMN_LEVEL);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BAND_LEVEL);
			addColumn(columnMap,ReportDesignConstants.COLUMN_ADDRESS_LINE_ONE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_ADDRESS_LINE_TWO);
			addColumn(columnMap,ReportDesignConstants.COLUMN_ADDRESS_LINE_THREE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_TARGET_CTC);
			addColumn(columnMap,ReportDesignConstants.COLUMN_JOINING_BONUS);
		}
		return columnMap;
	}

	/**
	 * @param permissionSet
	 * @param optional
	 * @param mandatory
	 * @return CustomField Map
	 */
	private static LinkedHashMap<String, String> getJoinerReportColumnMap(PermissionSet permissionSet, boolean optional, boolean mandatory) {
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId(),cData.getFieldDisplayName());
				}
			}	
		}
		return columnMap;
	}

	public static LinkedHashMap<String,String> getPendingOfferColumnMap(PermissionSet permissionSet,boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			addColumn(columnMap,ReportDesignConstants.COLUMN_EMPTY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_TITLE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_CREATED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED);
			addDepartmentColumns(columnMap);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_GRADE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_LOCATION);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_PROCESSED_BY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_TODO_DUE_DATE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_NAME);
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SOURCE);
			}
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE_UPDATED_DATE,TPLabels.getLabel("report.label.last_interaction_date"));
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId());
				}
			}
		}
		return columnMap;
	}
	
	/**
	 * Returns map of columns allowed for Rejected Candidates Report
	 * @param permissionSet
	 * @param optional
	 * @param mandatory
	 * @return
	 */
	public static LinkedHashMap<String,String> getRejectedCandidatesColumnMap(PermissionSet permissionSet, boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER);
			
			if (ImportConfigurationManager.isCurrentCTCViewable(permissionSet)){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC);
			}	
			if (ImportConfigurationManager.isExpectedCTCViewable(permissionSet)){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC);
			}	
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SOURCE);
			}	
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId(),cData.getFieldDisplayName());
				}
			}	
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_TITLE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_CODE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_DEPARTMENT);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_LEVEL);
			addColumn(columnMap,ReportDesignConstants.COLUMN_REJECTED_BY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_REJECTED_DATE);
		}
		return columnMap;
	}
	
	/**
	 * Returns map of columns allowed for Rejected Candidates Report
	 * @param permissionSet
	 * @param optional
	 * @param mandatory
	 * @return
	 */
	public static LinkedHashMap<String,String> getAllCandidatesColumnMap(PermissionSet permissionSet, boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER);
			
			if (ImportConfigurationManager.isCurrentCTCViewable(permissionSet)){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC);
			}	
			if (ImportConfigurationManager.isExpectedCTCViewable(permissionSet)){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC);
			}	
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SOURCE);
			}	
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId(),cData.getFieldDisplayName());
				}
			}	
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_TITLE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_CODE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_DEPARTMENT);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_LEVEL);
			
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SUB_CATEGORY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CATEGORY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_WORK_PHONE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_HOME_PHONE);
			
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_PASSPORT_NUMBER);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_DATE_OF_BIRTH);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CITY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_NOTICE_PERIOD);
			
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_BASIC);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_INPUT_SALARY_VARIABLE);
			
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SKILLS);
		}
		return columnMap;
	}
	
	/**
	 * @param permissionSet
	 * @param optional
	 * @param mandatory
	 * @return
	 */
	public static LinkedHashMap<String,String> getClosedPositionTATColumnMap(boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		String departmentLevel1=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1);
		if(mandatory) {
			addColumn(columnMap, "srNo", "Sr No");
			addColumn(columnMap, "positionCode", "Req Id");
			addColumn(columnMap, "positionCreatedDate", "Req open date");
			addColumn(columnMap, "positionTitle", "Position");
			addColumn(columnMap, "positionLocation", "Location");
			if(!Utils.isBlankOrNull(departmentLevel1)){
				addColumn(columnMap, "client", departmentLevel1);
			}
			else{
				addColumn(columnMap, "client", "Client");
			}
			//addColumn(columnMap, "positionSkill", "Position Skill");
			//addColumn(columnMap, "positionLevel", "Position Level");
			//addColumn(columnMap, "positionOwner", "Position Owner");
			//addColumn(columnMap, "noOfOpenings", "No of openings");
			//addColumn(columnMap, "positionCreatedDate", "Date Position Created");
			//addColumn(columnMap, "positionOpendDate", "Date Position Opened");
			//addColumn(columnMap, "positionClosedDate", "Date Position Closed");
			//addColumn(columnMap, "positionStatus", "Position Status");
			//addColumn(columnMap, "positionApprovedDate", "Date Position Approved");
			//addColumn(columnMap, "positionExpireDate", "Date Position Expired");
			//addColumn(columnMap, "positionAge", "Position Age");
			//addColumn(columnMap, "defaultTat", "Expected TAT");
			//addColumn(columnMap, "actualTat", "Actual TAT");
			//addColumn(columnMap, "tatDiff", "TAT Diff");
			addColumn(columnMap, "applicantName", "Candidate Name");
			addColumn(columnMap, "sourceTitle", "Source");
			addColumn(columnMap, "qualification", "Qlf");
			addColumn(columnMap, "experience", "Total Experience");
			addColumn(columnMap, "realExperience", "Rel Experience");
			addColumn(columnMap, "applicantCurrentEmployer", "Current Company");
			addColumn(columnMap, "currentDesignation", "Current Designation");
			addColumn(columnMap, "currentCtc", "Current CTC");
			addColumn(columnMap, "expectedCtc", "Expected CTC ");
			addColumn(columnMap, "applicantNoticePeriod", "Notice");
			addColumn(columnMap, "candidateStatus", "Final Status");
			addColumn(columnMap, "applicantDesignationOffered", "Offered Designation");
			addColumn(columnMap, "applicantLevelOffered", "Offered Grade");
			addColumn(columnMap, "applicantOfferStatus", "Offer Status");
			addColumn(columnMap, "offerAcceptedDate", "Offer Accepted Date");
			addColumn(columnMap, "applicantJoiningDate", "Expected Joining Date");
			addColumn(columnMap, "joinedDate", "Actual Joined Date");
			addColumn(columnMap, "rejectReason", "Reason for Reject");
			addColumn(columnMap, "comment", "Any Remarks");
			
		}
		if(optional) {
			StepManager stepManager = new StepManager();
			List<MasterStepData> steps = new ArrayList<MasterStepData>();
			try {
				steps = stepManager.getAllSteps(CommonConstants.TRUE, CommonConstants.FALSE);
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error while getting step list for closed position TAT report", e);
			}
			
			for(MasterStepData step : steps) {
				addColumn(columnMap, step.getStepId(), step.getStepName());
			}
			
		}
		return columnMap;
	}
	
	public static LinkedHashMap<String,String> getTimeToHireColumnMap(PermissionSet permissionSet, boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			addColumn(columnMap,ReportDesignConstants.COLUMN_EMPTY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_TITLE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_GRADE_HIRE_BY_DURATION);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_CREATED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED);
			addDepartmentColumns(columnMap);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_GRADE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_BAND);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_LOCATION);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STAGE_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_RECRUITERS);
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SOURCE);
			}
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId());
				}
			}		
		}
		return columnMap;
	}
	
	public static LinkedHashMap<String,String> getHiringActivityDetailsColumnMap(PermissionSet permissionSet, boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			addColumn(columnMap,ReportDesignConstants.COLUMN_EMPTY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_NAME);
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SOURCE);
			}
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_TITLE);
			addPositionOwner(columnMap,ReportDesignConstants.COLUMN_POSITION_OWNER);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_CREATED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_VACANCIES);
			addDepartmentColumns(columnMap);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_GRADE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_BAND);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_LOCATION);
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_ACTIVITY_DATE);
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId());
				}
			}		
		}
		return columnMap;
	}
	
	public static LinkedHashMap<String,String> getHiringActivitySummaryColumnMap(boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_TITLE);
			addPositionOwner(columnMap,ReportDesignConstants.COLUMN_POSITION_OWNER);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_CREATED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_VACANCIES);
			addDepartmentColumns(columnMap);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_GRADE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_BAND);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_LOCATION);
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId());
				}
			}	
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_APPLIED_COUNT);
		}
		return columnMap;
	}
	
	
	public static LinkedHashMap<String,String> getHiringFunnelDetailsColumnMap(PermissionSet permissionSet, boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(optional){
			//addColumn(columnMap,ReportDesignConstants.COLUMN_EMPTY);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_ID);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_HRMS_CODE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_EMPLOYEE_CODE);
					
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_TITLE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_BUSINESS_UNIT);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_COST_CENTER);
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE);		
			addDepartmentColumns(columnMap);		
			addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_NAME);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_GRADE);
			addColumn(columnMap,ReportDesignConstants.COLUMN_BUDGET_BAND);
			/*
			if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){
				addColumn(columnMap,ReportDesignConstants.COLUMN_CANDIDATE_SOURCE);
			}*/
			//addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE);
			//addColumn(columnMap,ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE_UPDATED_DATE,TPLabels.getLabel("report.label.last_interaction_date"));
		}
		return columnMap;
	}
	
	public static LinkedHashMap<String,String> getDatewiseHiringColumnMap(PermissionSet permissionSet, boolean optional, boolean mandatory){
		LinkedHashMap<String,String> columnMap = new LinkedHashMap<String, String>();
		if(mandatory){
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_TYPE_EXT_INT);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_CODE);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_TITLE);			
			addColumn(columnMap, ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STAGE);			
			addColumn(columnMap, ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_NAME);			
			addColumn(columnMap, ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_DATE);
			addColumn(columnMap, ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_PROCESSED_BY);			
			addColumn(columnMap, ReportDesignConstants.COLUMN_CANDIDATE_NAME);
			addColumn(columnMap, ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_VACANCY_TYPE);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_REPLACEMENT_EMP_CODE);
		}
		if(optional){			
			addDepartmentColumns(columnMap);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY);			
			addColumn(columnMap, ReportDesignConstants.COLUMN_BUDGET_GRADE);
			addColumn(columnMap, ReportDesignConstants.COLUMN_BUDGET_BAND);
			addColumn(columnMap, ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_STATUS);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_PRIMARY_SKILLS);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_SECONDARY_SKILLS);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_LOCATION);
			addColumn(columnMap, ReportDesignConstants.COLUMN_POSITION_LEVEL);
			
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int i = 0; i < customFields.size(); i++) {
					CustomFieldData cData = customFields.get(i);
					addColumn(columnMap,cData.getFieldId());
				}
			}
		}
		return columnMap;
	}

		
	
	public static void initColumnDBMapping() {
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_NAME,"applicantName");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CELL_PHONE,"applicantCellPhone");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL1,"applicantEmail1");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMAIL2,"applicantEmail2");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_JOINED,"applicantDateJoined");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_TITLE,"positionTitle");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_HIRE_BY_DATE,"positionDateExpiry");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_GRADE_HIRE_BY_DURATION,"hireByDuration");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_DATE_CREATED,"positionDateCreated");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_DATE_APPROVED,"positionDateApproved");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_VACANCIES,"positionNoOfOpenings");
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT,"deptName");
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB,"subDeptName");
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB,"subSubDeptName");
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB3,"sub3DeptName");
		columnDBMap.put(ReportDesignConstants.COLUMN_DEPARTMENT_SUB4,"sub4DeptName");
		columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_GRADE,"gradeName");
		columnDBMap.put(ReportDesignConstants.COLUMN_BUDGET_BAND,"bandName");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_LOCATION,"locationName");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_NAME,"positionStepTitle");
		columnDBMap.put(ReportDesignConstants.COLUMN_STAGE_NAME,"stage");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_RECRUITERS,"recruiters");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_OWNER,"positionOwnerName");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE,"sourceTitle");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_EMPLOYER,"applicantCurrentEmployer");
		columnDBMap.put(ReportDesignConstants.COLUMN_TODO_DUE_DATE,"dueDate");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPERIENCE,"applicantWorkingSince");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC,"currentCTC");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC,"expectedCTC");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE,"statusMessage");
		columnDBMap.put(ReportDesignConstants.COLUMN_STEP_STATUS_MESSAGE_UPDATED_DATE,"lastInteractionDate");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_CUSTOM_FIELDS,"posCustomFieldValues");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CUSTOM_FIELDS,"appCustomFieldValues");
		columnDBMap.put(ReportDesignConstants.COLUMN_ACTIVITY_DATE,"processMovedDate");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_ID,"applicantId");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_HRMS_CODE,"applicantHrmsCode");		
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_EMPLOYEE_CODE,"employeeCode");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_BUSINESS_UNIT,"buName");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_COST_CENTER,"costCenterName");	
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_REQUESTED_BY,"positionRequestedBy");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_TYPE_EXT_INT,"positionTypeExtInt");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_VACANCY_TYPE,"typeOfVacancy");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_REPLACEMENT_EMP_CODE,"replacementEmpCode");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_TEMPLATE_NAME,"processTemplateName");
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_NAME,"processStepName");
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STEP_DATE,"processStepDate");
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_STAGE,"processStage");
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITION_APPROVAL_AND_HIRING_PROCESSED_BY,"processedByName");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_APPLIED_COUNT,"appliedCount");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SUB_CATEGORY,"subCategory");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_CATEGORY,"category");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_PASSPORT_NUMBER,"passportNumber");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_DATE_OF_BIRTH,"dateOfBirth");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_BASIC,"offeredBasic");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_INPUT_SALARY_VARIABLE,"inputSalaryVariable");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_SKILLS,"skills");
		
		
		columnDBMap.put(ReportDesignConstants.COLUMN_NAME,"name");
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITIONER_NUMBER,"requisitionerNumber");
		columnDBMap.put(ReportDesignConstants.COLUMN_DOJ,"doj");
		columnDBMap.put(ReportDesignConstants.COLUMN_DESIGNATION,"designation");
		columnDBMap.put(ReportDesignConstants.COLUMN_BAND,"band");
		columnDBMap.put(ReportDesignConstants.COLUMN_LEVEL,"level");
		columnDBMap.put(ReportDesignConstants.COLUMN_BAND_LEVEL,"bandLevel");
		columnDBMap.put(ReportDesignConstants.COLUMN_ADDRESS_LINE_ONE,"addressLine1");
		columnDBMap.put(ReportDesignConstants.COLUMN_ADDRESS_LINE_TWO,"addressLine2");
		columnDBMap.put(ReportDesignConstants.COLUMN_ADDRESS_LINE_THREE,"addressLine3");
		columnDBMap.put(ReportDesignConstants.COLUMN_TARGET_CTC,"targetCTC");
		
		columnDBMap.put(ReportDesignConstants.COLUMN_HIRE_TYPE,"hireType");
		columnDBMap.put(ReportDesignConstants.COLUMN_INTERVIEW_SCORE,"interviewScore");
		columnDBMap.put(ReportDesignConstants.COLUMN_COMMUNICATION_SCORE,"communicationScore");
		columnDBMap.put(ReportDesignConstants.COLUMN_LATERAL_CAMPUS_INTERN ,"lateralCampusIntern");
		columnDBMap.put(ReportDesignConstants.COLUMN_LOCATION ,"location");
		columnDBMap.put(ReportDesignConstants.COLUMN_LOCAL_OUTSTATION,"localOutstation");
		columnDBMap.put(ReportDesignConstants.COLUMN_LAST_COMPANY ,"lastCompany");
		columnDBMap.put(ReportDesignConstants.COLUMN_TIER ,"tier");
		columnDBMap.put(ReportDesignConstants.COLUMN_CONTACT_NO ,"contactNo");
		columnDBMap.put(ReportDesignConstants.COLUMN_HIKE_PERCENT ,"hikePercent");
		columnDBMap.put(ReportDesignConstants.COLUMN_NOTICE_PERIOD_BUY_OUT_DAYS,"noticePeriodBuyOutDays");
		columnDBMap.put(ReportDesignConstants.COLUMN_JOINING_BONUS ,"joiningBonus");
		columnDBMap.put(ReportDesignConstants.COLUMN_CONTRACTOR_COST ,"contractorCost");
		columnDBMap.put(ReportDesignConstants.COLUMN_RECRUITER_NAME ,"recruiterName");
		columnDBMap.put(ReportDesignConstants.COLUMN_OFFERED_APPROVAL_DATE ,"offeredApprovalDate");
		columnDBMap.put(ReportDesignConstants.COLUMN_OFFERED_DATE ,"offeredDate");
		columnDBMap.put(ReportDesignConstants.COLUMN_EXPECTED_DOJ ,"expectedDOJ");
		columnDBMap.put(ReportDesignConstants.COLUMN_EMP_ID,"empID");
		columnDBMap.put(ReportDesignConstants.COLUMN_POSITION_DATE_OPENED,"requisitionAllocationDate");
		columnDBMap.put(ReportDesignConstants.COLUMN_CANDIDATE_GENDER,"gender");
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITION_STATUS,"requisitionStatus");
		columnDBMap.put(ReportDesignConstants.COLUMN_REQUISITION_RAISE_DATE,"requisitionRaiseDate");
		
		
	}
	
	private static void addColumn(LinkedHashMap<String,String> columnMap,String key){
		columnMap.put(key, ColumnUtils.getColumnLabel(key));
	}
	
	private static void addColumn(LinkedHashMap<String,String> columnMap,String key,String label){
		columnMap.put(key, label);
	}
	
	
	private static void addDepartmentColumns(LinkedHashMap<String,String> columnMap){
		String maxDeptLevel = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL);
		addColumn(columnMap,ReportDesignConstants.COLUMN_DEPARTMENT);
		if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_1)){
			addColumn(columnMap,ReportDesignConstants.COLUMN_DEPARTMENT_SUB);
			if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_2)){
				addColumn(columnMap,ReportDesignConstants.COLUMN_DEPARTMENT_SUB_SUB);
				if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_3)){
					addColumn(columnMap,ReportDesignConstants.COLUMN_DEPARTMENT_SUB3);
					if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_4)){
						addColumn(columnMap,ReportDesignConstants.COLUMN_DEPARTMENT_SUB4);
					}
				}
			}
		}
	}
	
	public static String getColumnDBMapping(String fieldId){
		return columnDBMap.get(fieldId); 	
	}
	
	private static void addPositionOwner(final LinkedHashMap<String,String> columnMap, String fieldId){
		if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_OWNER)){
			addColumn(columnMap,ReportDesignConstants.COLUMN_POSITION_OWNER);	
		}
	}
	
	public static void getJSArraySelectOption(String key, StringBuilder sb){
		Utils.getJSArraySelectOption(key, ColumnUtils.getColumnLabel(key), sb);
	}
}
