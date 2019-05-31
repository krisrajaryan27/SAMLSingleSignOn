/**
 * 
 */
package com.talentPool.vendorservice;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class VendorServiceConstants {
	public static String MAPPING_XML_FOLDER;
	public static String MAPPING_XML_FOLDER_ABSOLUTE_PATH;

	public static final String GET_BRANCHES = "BRANCH";
	public static final String GET_DEGREES = "DEGREE";
	public static final String GET_SKILLS = "SKILLS";
	public static final String GET_RESUME_TYPES="RESUME_TYPE";
	public static final String GET_SECURITY_QUESTIONS="SECURITY_QUESTIONS";
	public static final String GET_TIME_ZONE_TYPES="TIME_ZONE_TYPE";

	public static final String VERRORDATA_MAPPING = "VerrorData.xml";
	public static final String VLOGINDATA_MAPPING = "VloginData.xml";
	public static final String VIDSNAMES_MAPPING = "VidsNames.xml";
	public static final String VPOSITIONDATA_MAPPING = "VpositionData.xml";
	public static final String VPOSITIONLIST_MAPPING = "VpositionList.xml";
	public static final String VITEMDATA_MAPPING = "VitemData.xml";
	public static final String VAPPLICANTLIST_MAPPING = "VapplicantList.xml";
	public static final String VAPPLICANTDATA_MAPPING = "VapplicantData.xml";
	public static final String VACTIVITYLIST_MAPPING = "VactivityList.xml";
	public static final String VCUSTOMFIELDDATALIST_MAPPING = "VcustomFieldDataList.xml";
	public static final String VINBOXDATA_MAPPING = "VinboxData.xml";
	public static final String VCOMMUNICATIONDATA_MAPPING="VcommunicationData.xml";
	public static final String VIMPORTFIELDDATA_MAPPING = "VimportFieldData.xml";
	public static final String VIMPORTFIELDLIST_MAPPING = "VimportFieldList.xml";

	public static final String SORT_BY_NAME_ASC = "name_asc";
	public static final String SORT_BY_POSITION_ASC = "position_asc";
	public static final String SORT_BY_DATE_UPLOADED_ASC = "dateuploaded_asc";
	public static final String SORT_BY_CURRENT_STATUS_ASC = "currentstatus_asc";
	public static final String SORT_BY_NAME_DESC = "name_desc";
	public static final String SORT_BY_POSITION_DESC = "position_desc";
	public static final String SORT_BY_DATE_UPLOADED_DESC = "dateuploaded_desc";
	public static final String SORT_BY_CURRENT_STATUS_DESC = "currentstatus_desc";

	static {
		MAPPING_XML_FOLDER = TPApplicationProperties.getProperty("mapping.xml.folder");
		String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
		MAPPING_XML_FOLDER_ABSOLUTE_PATH = Utils.concatFilePath(basePath, MAPPING_XML_FOLDER);
	}
}
