/**
 * 
 */
package com.talentPool.custom.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;

/**
 * @author shivprasad
 * 
 */
public class CustomFieldDataProcessor {
	
	public ArrayList<CustomFieldData> setCustomFieldValuesFromRequest(HttpServletRequest request, ArrayList<CustomFieldData> customFields) {
		try {
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				String val = request.getParameter(data.getFieldName());
				setCustomFieldValues(val, data);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return customFields;
	}
	
	public ArrayList<CustomFieldData> setTabularCustomFieldValuesFromRequest(HttpServletRequest request, ArrayList<CustomFieldData> customFields) {
		try {
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				int j= 1;
				String val = "";
				while(request.getParameter(data.getFieldName()+j)!=null){
					if (j==1){
						val = request.getParameter(data.getFieldName()+j);
					}else{
						val = val + "|" +request.getParameter(data.getFieldName()+j);
					}
					j++;
				}
				setCustomFieldValues(val, data);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return customFields;
	}
	
	public void setCustomFieldValues(String values, CustomFieldData cData) {
		String[] vals = new String[1];
		vals[0] = "";
		if (values != null) {
			if (cData.getFieldType().equals(CustomFieldConstants.TYPE_CHECKBOX) || cData.getFieldType().equals(CustomFieldConstants.TYPE_LISTBOX) || (cData.getFieldEntityType() ==CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
				vals = values.split("\\|");
			} else {
				vals[0] = values;
			}
			cData.setFieldValues(vals);
		}
	}
	
	public ArrayList<CustomFieldData> setCustomFieldValuesFromRequestForSearch(HttpServletRequest request, ArrayList<CustomFieldData> customFields) {
		try {
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				String fromVal="";
				String toVal="";
				if(!data.getFieldType().equals(CustomFieldConstants.TYPE_NUMBER) && !data.getFieldType().equals(CustomFieldConstants.TYPE_DATE)){
					fromVal = request.getParameter(data.getFieldName());
				}else{
					String rangeCriteria = request.getParameter(data.getFieldName()+CustomFieldConstants.POSTFIX_CRITERIA);
					fromVal = request.getParameter(data.getFieldName()+CustomFieldConstants.POSTFIX_FROM);
					toVal = request.getParameter(data.getFieldName()+CustomFieldConstants.POSTFIX_TO);
					String[] vals = new String[1];
					vals[0] = (toVal==null)? "" : toVal;
					data.setToValues(vals);
					data.setRangeCriteria(rangeCriteria);
				}
				String[] vals = new String[1];
				vals[0] = "";
				if (fromVal != null) {
					if (data.getFieldType().equals(CustomFieldConstants.TYPE_CHECKBOX) || data.getFieldType().equals(CustomFieldConstants.TYPE_LISTBOX)) {
						vals = fromVal.split("\\|");
					} else {
						vals[0] = fromVal;
					}
					data.setFieldValues(vals);
				}
				
				
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return customFields;
	}
	public ArrayList<CustomFieldData> setCustomFieldValuesFromPreviousValues(ArrayList<CustomFieldData> customFields, ArrayList<CustomFieldData> customFieldValues) {
		try {
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				for (int k = 0; customFieldValues != null && k < customFieldValues.size(); k++) {
					CustomFieldData cdata = customFieldValues.get(k);
					if (data.getFieldId().equals(cdata.getFieldId())) {
						data.setFieldValues(cdata.getFieldValues());
						break;
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return customFields;
	}
	
	public void setCustomFieldMapFromPreviousValues(ArrayList<CustomFieldData> customFields, Map<String,CustomFieldData> customFieldValues) {
		try {
			if(customFieldValues==null && customFields != null && customFields.size()>0){
				customFieldValues = new HashMap<String, CustomFieldData>();
			}
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				if(!customFieldValues.containsKey(data.getFieldName())){
					customFieldValues.put(data.getFieldName(), data);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
	}
	
	public ArrayList<CustomFieldData> setCustomFieldValuesFromPreviousValuesForRange(ArrayList<CustomFieldData> customFields, ArrayList<CustomFieldData> customFieldValues) {
		try {
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				for (int k = 0; customFieldValues != null && k < customFieldValues.size(); k++) {
					CustomFieldData cdata = customFieldValues.get(k);
					if (data.getFieldId().equals(cdata.getFieldId())) {
						data.setFieldValues(cdata.getFieldValues());
						data.setFieldStringValue(cdata.getFieldStringValue());
						data.setToValues(cdata.getToValues());
						data.setRangeCriteria(cdata.getRangeCriteria());
						break;
					}
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return customFields;
	}
	
	public ArrayList<CustomFieldData> setCustomFieldsInrequest(HttpServletRequest request, int entityType) {
		if (CustomFieldManager.isCustomFieldsAvailable(entityType)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(entityType, CustomFieldConstants.INPUT_ALLOWED, true);
			customFields = setCustomFieldValuesFromRequest(request, customFields);						
			request.setAttribute("customFields", customFields);
			return customFields;
		} else {
			return null;
		}
	}
	public ArrayList<CustomFieldData> setCustomFieldsInrequestFromGivenValues(HttpServletRequest request, int entityType, ArrayList<CustomFieldData> customFieldValues) {
		if (CustomFieldManager.isCustomFieldsAvailable(entityType)) {			
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(entityType, CustomFieldConstants.INPUT_ALLOWED, true);						
			customFields = setCustomFieldValuesFromPreviousValues(customFields, customFieldValues);						
			request.setAttribute("customFields", customFields);
			return customFields;
		} else {
			return null;
		}
	}
	
	//Validate Custom Fields Error
	public void validateCustomFields(ActionErrors errors, ArrayList<CustomFieldData> customFields, int entityType){
		
		if (CustomFieldManager.isCustomFieldsAvailable(entityType)) {
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				if (data.getFieldRequired() == CustomFieldConstants.FIELD_REQUIRED) {
					if (data.getFieldValues() == null || Utils.isBlankOrNull(data.getFieldValues()[0])) {
						errors.add("error", new ActionError("common.error.required.one_param", data.getFieldDisplayName()));
					}
				}
			}
		}
		
	}
	
	public ArrayList<CustomFieldData> setBulkPositionsCustomFieldValuesFromRequest(HttpServletRequest request, ArrayList<CustomFieldData> customFields, String requestParameterSuffix) {
		try {
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				String val = request.getParameter(data.getFieldName()+requestParameterSuffix);
				String[] vals = new String[1];
				vals[0] = "";
				if (val != null) {
					if (data.getFieldType().equals(CustomFieldConstants.TYPE_CHECKBOX) || data.getFieldType().equals(CustomFieldConstants.TYPE_LISTBOX)) {
						vals = val.split("\\|");
					} else {
						vals[0] = val;
					}
					data.setFieldValues(vals);
				}

			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return customFields;
	}
	
	public ArrayList<CustomFieldData> setCustomFieldValue(ArrayList<CustomFieldData> customFields, String customFieldName, String customFieldValue) {
		try {
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData data = customFields.get(i);
				if(data.getFieldName().equalsIgnoreCase(customFieldName)) {
					String[] vals = new String[1];
					vals[0] = customFieldValue;
					data.setFieldValues(vals);
					break;
				}				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}
		return customFields;
	}
}
