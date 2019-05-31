/**
 * 
 */
package com.talentPool.websiteservice;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class WebsiteServiceConstants {
	public static String MAPPING_XML_FOLDER;
	public static String MAPPING_XML_FOLDER_ABSOLUTE_PATH;

	public static final String GET_BRANCHES = "BRANCH";
	public static final String GET_DEGREES = "DEGREE";
	public static final String GET_SKILLS = "SKILLS";
	public static final String GET_SOURCE = "SOURCE";
	public static final String GET_LOCATIONS = "LOCATIONS";
	public static final String GET_DEPARTMENTS = "DEPARTMENTS";
	public static final String GET_RESUME_TYPES = "RESUME_TYPES";

	public static final String WERRORDATA_MAPPING = "WerrorData.xml";
	public static final String WIDSNAMES_MAPPING = "WidsNames.xml";
	public static final String WPOSITIONDATA_MAPPING = "WpositionData.xml";
	public static final String WPOSITIONLIST_MAPPING = "WpositionList.xml";
	public static final String WITEMDATA_MAPPING = "WitemData.xml";
	public static final String WAPPLICANTDATA_MAPPING = "WapplicantData.xml";
	public static final String WCUSTOMFIELDDATALIST_MAPPING = "WcustomFieldDataList.xml";
	public static final String WIMPORTFIELDDATA_MAPPING = "WimportFieldData.xml";
	public static final String WIMPORTFIELDLIST_MAPPING = "WimportFieldList.xml";
	public static final String WINBOXDATA_MAPPING = "WinboxData.xml";
	public static final String WPOSITIONFIELDDATA_MAPPING = "WpositionFieldData.xml";
	public static final String WPOSITIONFIELDLIST_MAPPING = "WpositionFieldList.xml";
	public static final String WEBSITE_SETTINGS_MAPPING = "WebsiteSettingData.xml";
	public static final String WEBSITE_POSITION_FILTERS_MAPPING = "WpositionFilters.xml";
	public static String WSOCIALMEDIALIST_MAPPING = "WsocialMediaList.xml";
	public static String WSOCIALMEDIASOURCE_MAPPING = "WsocialMediaSource.xml";
	public static String WSOCIALMEDIASOURCELIST_MAPPING = "WsocialMediaList.xml";
	
	static {
		MAPPING_XML_FOLDER = TPApplicationProperties.getProperty("mapping.xml.folder");
		String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
		MAPPING_XML_FOLDER_ABSOLUTE_PATH = Utils.concatFilePath(basePath, MAPPING_XML_FOLDER);
	}
}
