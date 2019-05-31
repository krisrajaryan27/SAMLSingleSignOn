/**
 * 
 */
package com.talentPool.employeeservice;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;


/**
 * @author shivprasad
 * 
 */
public class EmployeeServiceConstants {
	public static String MAPPING_XML_FOLDER;
	public static String MAPPING_XML_FOLDER_ABSOLUTE_PATH;
	
	public static final String GET_BRANCHES="BRANCH";
	public static final String GET_DEGREES="DEGREE";
	public static final String GET_SKILLS="SKILLS";
	public static final String GET_RESUME_TYPES="RESUME_TYPE";
	public static final String GET_TIME_ZONE_TYPES="TIME_ZONE_TYPE";
	
	public static final String EERRORDATA_MAPPING = "EerrorData.xml";
	public static final String ELOGINDATA_MAPPING = "EloginData.xml";	
	public static final String EIDSNAMES_MAPPING = "EidsNames.xml";	
	public static final String EPOSITIONDATA_MAPPING = "EpositionData.xml";
	public static final String EPOSITIONLIST_MAPPING = "EpositionList.xml";
	public static final String EITEMDATA_MAPPING = "EitemData.xml";			
	public static final String ECUSTOMFIELDDATALIST_MAPPING = "EcustomFieldDataList.xml";
	public static final String EINBOXDATA_MAPPING = "EinboxData.xml";
	public static final String ECOMMUNICATIONDATA_MAPPING="EcommunicationData.xml";	
	public static final String EAPPLICANTLIST_MAPPING = "EapplicantList.xml";
	public static final String EAPPLICANTDATA_MAPPING = "EapplicantData.xml";
	
	public static final String EIMPORTFIELDDATA_MAPPING = "EimportFieldData.xml";
	public static final String EIMPORTFIELDLIST_MAPPING = "EimportFieldList.xml";
	
	public static final String EPOSITIONS_SCREEN_FILTERS_MAPPING = "EpositionScreenFilters.xml";
	
	public static final String EPORTAL_SETTINGS = "EportalSettings.xml";
	
	
	public static final String SORT_BY_NAME_ASC="name_asc";
	public static final String SORT_BY_POSITION_ASC="position_asc";
	public static final String SORT_BY_DATE_UPLOADED_ASC="dateuploaded_asc";
	public static final String SORT_BY_CURRENT_STATUS_ASC="currentstatus_asc";
	public static final String SORT_BY_NAME_DESC="name_desc";
	public static final String SORT_BY_POSITION_DESC="position_desc";
	public static final String SORT_BY_DATE_UPLOADED_DESC="dateuploaded_desc";
	public static final String SORT_BY_CURRENT_STATUS_DESC="currentstatus_desc";
	
	public static final String SORT_BY_POSITION_CREATE_DATE_ASC = "position_creation_date_asc";
	public static final String SORT_BY_POSITION_CREATE_DATE_DESC = "position_creation_date_desc";

	public static final String SORT_BY_POSITION_REF_FEE_ASC = "position_ref_fee_asc";
	public static final String SORT_BY_POSITION_REF_FEE_DESC = "position_ref_fee_desc";
	
	static {
		MAPPING_XML_FOLDER = TPApplicationProperties.getProperty("mapping.xml.folder");
		String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
		MAPPING_XML_FOLDER_ABSOLUTE_PATH = Utils.concatFilePath(basePath, MAPPING_XML_FOLDER);
	}
}
