/**
 * 
 */
package com.talentPool.userConfiguration.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.userConfiguration.constants.UserConfigurationConstants;
import com.talentPool.userConfiguration.dataobject.UserConfData;
import com.talentPool.userConfiguration.manager.UserConfigurationManager;



/**
 * @author Ajeet
 *
 */
public class UserConfigurationUtils {
	
	private static HashMap<String, List<String>> applicantTooltipMap = null;

	static{
		setUserConfMap();
	}
	
	public static void setUserConfMap() {
		try {
			applicantTooltipMap = new HashMap<String, List<String>>();

			UserConfigurationManager userConfigurationManager = new UserConfigurationManager();
			ArrayList<UserConfData> dataList = userConfigurationManager.getAllUserConfigurations();
			if(dataList !=null){
				for (int i = 0; i < dataList.size(); i++) {
					UserConfData data = dataList.get(i);
					if(data!=null && data.getConfId().equals(UserConfigurationConstants.APPLICANT_TOOLTIP)){
						applicantTooltipMap.put(data.getUserId(), getStringConverToList(data.getConfValue()));
					}
				}
			}				
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private static List<String> getStringConverToList(String confValue) {
		List<String> confValueList = new ArrayList<String>();
		try {
			if(!Utils.isBlankOrNull(confValue)){
				String[] confValueStr = confValue.split(",");
				confValueList = Arrays.asList(confValueStr);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return confValueList;
	}

	public static HashMap<String, List<String>> getApplicantTooltipMap(){
		try {
			if(applicantTooltipMap==null){
				setUserConfMap();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return applicantTooltipMap;
	}
	
	public static String getJSArrayApplicantTooltip(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_NAME+ "','" + TPLabels.getLabel("common.name") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_ID+ "','" + TPLabels.getLabel("common.id") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_SOURCE+ "','" + TPLabels.getLabel("common.source") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_LOCATION+ "','" + TPLabels.getLabel("common.current_location") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_EMAIL1+ "','" + TPLabels.getLabel("common.email1") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_EMAIL2+ "','" + TPLabels.getLabel("common.email2") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_CELL_PHONE+ "','" + TPLabels.getLabel("common.mobile") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_HOME_PHONE+ "','" + TPLabels.getLabel("common.phone1") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_WORK_PHONE+ "','" + TPLabels.getLabel("common.phone2") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_CURRENT_CTC+ "','" + TPLabels.getLabel("common.current_ctc") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_EXPECTED_CTC+ "','" + TPLabels.getLabel("common.expected_ctc") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_CURRENT_EMPLOYER+ "','" + TPLabels.getLabel("common.current_employer") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_EXPERIENCE+ "','" + TPLabels.getLabel("common.experience") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_NOTICE_PERIOD+ "','" + TPLabels.getLabel("common.time_to_join") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_SKILLS+ "','" + TPLabels.getLabel("common.skills") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_EDUCATION+ "','" + TPLabels.getLabel("common.education") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_DATE_OF_BIRTH+ "','" + TPLabels.getLabel("common.date_of_birth") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_PASSPORT+ "','" + TPLabels.getLabel("common.passport_number") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_HRMS_CODE+ "','" + TPLabels.getLabel("common.hrms_code") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_RESUME_TYPE+ "','" + TPLabels.getLabel("common.resume_type") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_EMPLOYMENT_HISTORY+ "','" + TPLabels.getLabel("common.employment_history") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_CUSTOM_FIELDS+ "','" + TPLabels.getLabel("common.custom_fields") + "'),");
				sb.append("new SelectOption('" + UserConfigurationConstants.APP_FLAGS+ "','" + TPLabels.getLabel("common.flags") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}

	public static ArrayList<String> getDefaultApplicantTooltipList() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			list.add(UserConfigurationConstants.APP_NAME);
			list.add(UserConfigurationConstants.APP_LOCATION);
			list.add(UserConfigurationConstants.APP_SOURCE);
			list.add(UserConfigurationConstants.APP_EMAIL1);
			list.add(UserConfigurationConstants.APP_EMAIL2);
			list.add(UserConfigurationConstants.APP_CELL_PHONE);
			list.add(UserConfigurationConstants.APP_EXPERIENCE);
			list.add(UserConfigurationConstants.APP_EDUCATION);
			list.add(UserConfigurationConstants.APP_CURRENT_EMPLOYER);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return list;
	}
}
