package com.talentPool.user.utils;

import java.util.ArrayList;


import java.util.HashMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.DataViewConstants;
import com.talentPool.user.dataobject.ViewData;
import com.talentPool.user.manager.UserManager;


public class DataViewUtils {
	private static HashMap<String, HashMap<String, String>> dataViewMap = null;
	
	private static HashMap<String, HashMap<String, String>> headerMap = null;
	
	private static HashMap<String, String> allHeaderMap = null;
	
	static{
		setAllHeaderToMap();
		setDataViewMap();
	}
	
	public static String getJSDashboardPositionSummaryArrayForDataView() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_LOCATION + "','" + TPLabels.getLabel("position.description.location") + "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_HIRE_BY_DATE + "','" + TPLabels.getLabel("position.description.hire_by_date") + "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_DEPARTMENT + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) + "'),");
			getJSDepartmentLevelsAdded(sb);
			if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_OWNER)){
				sb.append("new SelectOption('" + DataViewConstants.POSITION_OWNER + "','" + TPLabels.getLabel("global.position_owner") + "'),");
			}
			sb.append("new SelectOption('" + DataViewConstants.POSITION_REQUESTED_BY + "','" + TPLabels.getLabel("position.description.requested_by") + "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_RECRUITERS + "','" + TPLabels.getLabel("common.recruiters") + "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_LEVEL+ "','" + TPLabels.getLabel("common.position")+" "+TPLabels.getLabel("common.level")+ "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_CREATED_ON + "','" + TPLabels.getLabel("position.description.created_on") + "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_GRADE + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) + "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_BAND + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL) + "'),");
			sb.append("new SelectOption('" + DataViewConstants.POSITION_REFERRAL_FEES + "','" + TPLabels.getLabel("position.description.position_referal_fees") + "')");
			// Custom Fileds
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int c = 0; c < customFields.size(); c++) {
					CustomFieldData cData = customFields.get(c);
					sb.append(",");
					sb.append("new SelectOption('" + cData.getFieldId() + "','" + cData.getFieldDisplayName() + "')");
				}
			}
			
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
	private static void getJSDepartmentLevelsAdded(StringBuffer sb){
		String maxDeptLevel = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL);
		if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_1)){
			sb.append("new SelectOption('" + DataViewConstants.POSITION_SUB_DEPARTMENT + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) + "'),");
			if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_2)){
				sb.append("new SelectOption('" + DataViewConstants.POSITION_SUB_SUB_DEPARTMENT + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) + "'),");
				if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_3)){
					sb.append("new SelectOption('" + DataViewConstants.POSITION_SUB3_DEPARTMENT + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4) + "'),");
					if(!maxDeptLevel.equals(MastersConstants.DEPARTMENT_LEVEL_4)){
						sb.append("new SelectOption('" + DataViewConstants.POSITION_SUB4_DEPARTMENT + "','" + GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5) + "'),");
					}
				}
			}
		}
	}
	
	public static HashMap setDataViewMap() {
		try {
			dataViewMap = new HashMap<String, HashMap<String,String>>();
			headerMap = new HashMap<String, HashMap<String,String>>();
			UserManager userManager = new UserManager();
			ArrayList<ViewData> viewDataList = userManager.getUserViewConfigData();
			if(viewDataList !=null){
				HashMap<String, String> dataMap = null;
				for (int i = 0; i < viewDataList.size(); i++) {
					ViewData viewData = viewDataList.get(i);
					if(viewData!=null){
						dataMap = new HashMap<String, String>();
						dataMap.put(DataViewConstants.KEY_COLUMN1, viewData.getColumn1());
						dataMap.put(DataViewConstants.KEY_COLUMN2, viewData.getColumn2());
					}
					dataViewMap.put(viewData.getViewType()+"_"+viewData.getUserId(), dataMap);
					setHeaderMap(viewData.getViewType()+"_"+viewData.getUserId(), dataMap);
				}
				dataViewMap.put(UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG, getDefaultDataMap(UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG));
				setHeaderMap(UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG, getDefaultDataMap(UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG));
			}				
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return dataViewMap;
	}
	
	public static HashMap<String, String> getDefaultDataMap(String key){
		HashMap<String, String> dataMap = new HashMap<String, String>();
		try {			
			if(key.equals(UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG)){
				dataMap.put(DataViewConstants.KEY_COLUMN1, DataViewConstants.POSITION_DEPARTMENT);
				dataMap.put(DataViewConstants.KEY_COLUMN2, DataViewConstants.POSITION_RECRUITERS);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return dataMap;
	}
	public static String getColumnMapping(String column, String userId, String key){
		String value = "";
		try {
			if(dataViewMap != null){
				HashMap<String, String> dataMap = dataViewMap.get(key+"_"+userId);
				if(dataMap == null){
					dataMap = dataViewMap.get(key);
				}
				value = dataMap.get(column);
			}else{
				HashMap<String, String> dataMap = getDefaultDataMap(key);
				value = dataMap.get(column);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return value;
	}
	
	public static void setAllHeaderToMap() {
		try{
			allHeaderMap = new HashMap<String, String>();
			allHeaderMap.put(DataViewConstants.POSITION_LOCATION,TPLabels.getLabel("position.description.location"));
			allHeaderMap.put(DataViewConstants.POSITION_HIRE_BY_DATE,TPLabels.getLabel("position.description.hire_by_date"));
			allHeaderMap.put(DataViewConstants.POSITION_DEPARTMENT,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1));
			allHeaderMap.put(DataViewConstants.POSITION_SUB_DEPARTMENT,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2));
			allHeaderMap.put(DataViewConstants.POSITION_SUB_SUB_DEPARTMENT,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3));
			allHeaderMap.put(DataViewConstants.POSITION_SUB3_DEPARTMENT,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4));
			allHeaderMap.put(DataViewConstants.POSITION_SUB4_DEPARTMENT,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5));
			allHeaderMap.put(DataViewConstants.POSITION_OWNER,TPLabels.getLabel("global.position_owner"));
			allHeaderMap.put(DataViewConstants.POSITION_REQUESTED_BY,TPLabels.getLabel("position.description.requested_by"));
			allHeaderMap.put(DataViewConstants.POSITION_RECRUITERS,TPLabels.getLabel("common.recruiters"));
			allHeaderMap.put(DataViewConstants.POSITION_LEVEL,TPLabels.getLabel("common.position")+" "+TPLabels.getLabel("common.level"));
			allHeaderMap.put(DataViewConstants.POSITION_CREATED_ON,TPLabels.getLabel("position.description.created_on"));
			allHeaderMap.put(DataViewConstants.POSITION_GRADE,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL));
			allHeaderMap.put(DataViewConstants.POSITION_BAND,GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL));
			allHeaderMap.put(DataViewConstants.POSITION_REFERRAL_FEES,TPLabels.getLabel("position.description.position_referal_fees"));
			
			// Custom Fileds
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int c = 0; c < customFields.size(); c++) {
					CustomFieldData cData = customFields.get(c);
					allHeaderMap.put(cData.getFieldId(), cData.getFieldDisplayName());
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public static void setHeaderMap(String key, HashMap<String, String> dataMap){
		try{
			String header1 = "";
			String header2 = "";
			if(!Utils.isBlankOrNull(dataMap.get(DataViewConstants.KEY_COLUMN1))){
				header1 = allHeaderMap.get(dataMap.get(DataViewConstants.KEY_COLUMN1));
			}
			if(!Utils.isBlankOrNull(dataMap.get(DataViewConstants.KEY_COLUMN2))){
				header2 = allHeaderMap.get(dataMap.get(DataViewConstants.KEY_COLUMN2));
			}
			
			HashMap<String, String> header = new HashMap<String, String>();
			header.put(DataViewConstants.HEADER1, header1);
			header.put(DataViewConstants.HEADER2, header2);
			headerMap.put(key, header);

		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	} 
	
	public static String getHeaderMapping(String userId, String key, String header){
		String value = "";
		try {
			try {
				if(headerMap != null){
					HashMap<String, String> localMap = headerMap.get(key+"_"+userId);
					if(localMap == null){
						localMap = headerMap.get(key);
					}
					value = localMap.get(header);
				}else{
					HashMap<String, String> localMap = headerMap.get(key);
					value = localMap.get(header);
				}
			} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return value;
	}
}
