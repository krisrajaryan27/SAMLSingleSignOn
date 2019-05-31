/**
 * 
 */
package com.talentPool.custom.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.search.SearchConstants;
/**
 * @author shivprasad
 * 
 */
public class CustomFieldUtils {
	public static String getArrayForCustomFields(ArrayList<CustomFieldData> fields) {
		StringBuffer sb = new StringBuffer();
		sb.append("var customFieldsList = new Array();\n");
		if (fields != null) {
			for (int i = 0; i < fields.size(); i++) {
				CustomFieldData cData = fields.get(i);
				sb.append("customFieldsList[customFieldsList.length]=new CustomFieldData('" + cData.getFieldName().replaceAll("'", "\\\\'") + "','"
						+ cData.getFieldDisplayName().replaceAll("'", "\\\\'") + "','" + cData.getFieldType() + "','" + cData.getFieldRequired() + "');\n");
			}
		}
		return sb.toString();
	}

	public static String getArrayForCustomFieldsAvailableWhileImportOrEdit(ArrayList<CustomFieldData> fields) {
		StringBuffer sb = new StringBuffer();
		sb.append("var customFieldsList = new Array();\n");
		if (fields != null) {
			for (int i = 0; i < fields.size(); i++) {
				CustomFieldData cData = fields.get(i);
				if (ImportConfigurationManager.isImportOrEditFieldShow(cData.getFieldName())) {
					sb.append("customFieldsList[customFieldsList.length]=new CustomFieldData('" + cData.getFieldName().replaceAll("'", "\\\\'") + "','"
							+ cData.getFieldDisplayName().replaceAll("'", "\\\\'") + "','" + cData.getFieldType() + "','" + cData.getFieldRequired() + "');\n");
				}
			}
		}
		return sb.toString();
	}

	public static String getArrayForCustomFieldsWhileImport(ArrayList<CustomFieldData> customFields) {
		StringBuffer sb = new StringBuffer();
		sb.append("var customFieldsList = new Array();\n");
		if (customFields != null) {
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if (ImportConfigurationManager.isImportFieldShow(cData.getFieldName())) {
					sb.append("customFieldsList[customFieldsList.length]=new CustomFieldData('" + cData.getFieldName().replaceAll("'", "\\\\'") + "','"
							+ cData.getFieldDisplayName().replaceAll("'", "\\\\'") + "','" + cData.getFieldType() + "','" + cData.getFieldRequired() + "');\n");
				}
			}
		}
		return sb.toString();
	}

	public static String getArrayForCustomFieldsWhileEdit(ArrayList<CustomFieldData> customFields) {
		StringBuffer sb = new StringBuffer();
		sb.append("var customFieldsList = new Array();\n");
		if (customFields != null) {
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if (ImportConfigurationManager.isEditFieldShow(cData.getFieldName())) {
					sb.append("customFieldsList[customFieldsList.length]=new CustomFieldData('" + cData.getFieldName().replaceAll("'", "\\\\'") + "','"
							+ cData.getFieldDisplayName().replaceAll("'", "\\\\'") + "','" + cData.getFieldType() + "','" + cData.getFieldRequired() + "');\n");
				}
			}
		}
		return sb.toString();
	}

	public static ArrayList<CustomFieldData> getCustomFieldsConstructedForSearchUI(ArrayList<CustomFieldData> result) {
		try {
			for (int i = 0; result != null && i < result.size(); i++) {
				CustomFieldData cData = result.get(i);
				String[] vals = new String[1];
				String[] toVals = new String[1];
				if (cData.getFieldType().equals(CustomFieldConstants.TYPE_DATE)) {
					String val = null;
					if (cData.getFieldDateValue() != null) {
						val = Utils.getDateConvertedToString(cData.getFieldDateValue(), cData.getOtherAttribute(CustomFieldConstants.ATTRIBUTE_DATE_FORMAT));
					}
					vals[0] = val;
					if (cData.getRangeCriteria() != null && cData.getRangeCriteria().equalsIgnoreCase(SearchConstants.CRITERIA_BETWEEN)) {
						String toVal = null;
						if (cData.getFieldDateValueTo() != null) {
							toVal = Utils.getDateConvertedToString(cData.getFieldDateValueTo(), cData.getOtherAttribute(CustomFieldConstants.ATTRIBUTE_DATE_FORMAT));
						}
						toVals[0] = toVal;
					}
				} else if (cData.getFieldType().equals(CustomFieldConstants.TYPE_NUMBER)) {
					vals[0] = "" + cData.getFieldNumberValue();
					if (cData.getRangeCriteria() != null && cData.getRangeCriteria().equalsIgnoreCase(SearchConstants.CRITERIA_BETWEEN)) {
						toVals[0] = "" + cData.getFieldNumberValueTo();
					}
				} else {
					if (!Utils.isBlankOrNull(cData.getFieldStringValue())) {
						vals = cData.getFieldStringValue().split("\\|");
					}
				}
				cData.setFieldValues(vals);
				cData.setToValues(toVals);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return result;

	}
	
	public static void generateCustomFieldMap(Map customFieldMap, String customFields, String separator) {
		try {
			if(!Utils.isBlankOrNull(customFields)){
				String[] cusFlds = customFields.split(separator);
				for (int i = 0; i < cusFlds.length; i++) {
					String cusFld = cusFlds[i];
					if(!Utils.isBlankOrNull(cusFld)){
						String[] customFld = cusFld.split("=");
						String id = customFld[0];
						String value = customFld[1];
						if(customFieldMap.containsKey(id)){
							value = customFieldMap.get(id)+","+value;
						}
						customFieldMap.put(id, Utils.getBlankIfNull(value));
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	public static ArrayList<CustomFieldData> getPositonCustomFeids(){
		ArrayList<CustomFieldData> customFields = null;
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
		}
		return customFields;
	}
	
	public static String splitFieldName(String filedName, int entity){		
	     String  newFieldName="";
	     switch (entity) {
	     		case CustomFieldConstants.ENTITY_TYPE_APPLICANT:
	     			newFieldName = filedName.replaceFirst("app_","");	
	     			break;
	     		case CustomFieldConstants.ENTITY_TYPE_POSITION:
	     			newFieldName = filedName.replaceFirst("pos_","");
					break; 		
	     		case CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD:
	     			newFieldName = filedName.replaceFirst("appTab_", "");
	     			break;
	     		case CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE:
	     			newFieldName = filedName;
	     			break;
		}
	     		 
	     return newFieldName;
	}
	
	public static String appendCustomFieldVariables(String variableIds,String variableNames){
		List<CustomFieldData> allCustomFields =  new ArrayList<CustomFieldData>();
		List<CustomFieldData> posCustomFields =  null;
		try{
			posCustomFields = getPositionCustomFieldVariableList(variableIds);
			if(posCustomFields!=null){
				allCustomFields.addAll(posCustomFields);
			}
			if(allCustomFields.size()>0){
				String customFieldStr = getCommaSepString(allCustomFields);
				if(!Utils.isBlankOrNull(customFieldStr)){
					if(!Utils.isBlankOrNull(variableNames)){
						variableNames += "," + customFieldStr;
					}else{
						variableNames += customFieldStr;
					}
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return variableNames;
	}
	
	private static List<CustomFieldData> getPositionCustomFieldVariableList(String variableIds){
		CustomFieldManager customFieldManager = null;
		List<CustomFieldData> custList = null;
		if(TemplateUtils.isTypeExist(variableIds, TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)){
			customFieldManager = new CustomFieldManager();
			custList = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_POSITION, true);
		}
		return custList;
	}
	
	private static String getCommaSepString(List<CustomFieldData> custFields){
		String returnStr = "";
		String fieldName = "";
		CustomFieldData cData = null;
		if(custFields!=null && custFields.size()>0){
			for(int i=0 ;i<custFields.size();i++){
				cData = custFields.get(i);
				 fieldName = Utils.replaceSpaceWithUnderscore(cData.getFieldName());
				if(Utils.isBlankOrNull(returnStr)){
					returnStr += fieldName;
				}else{
					returnStr += "," + fieldName;
				}
			}
		}
		return returnStr;
	}
	
	public static String getArrayForCustomFieldsForPositionDescriptionShow(ArrayList<CustomFieldData> customFields) {
		StringBuffer sb = new StringBuffer();
		sb.append("var customFieldsList = new Array();\n");
		if (customFields != null) {
			for (int i = 0; i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if (PositionScreenConfigurationManager.isDescriptionFieldShow(cData.getFieldName())) {
					sb.append("customFieldsList[customFieldsList.length]=new CustomFieldData('" + cData.getFieldName().replaceAll("'", "\\\\'") + "','"
							+ cData.getFieldDisplayName().replaceAll("'", "\\\\'") + "','" + cData.getFieldType() + "','" + (PositionScreenConfigurationManager.isDescriptionFieldMandatory(cData.getFieldName())?  CustomFieldConstants.FIELD_REQUIRED: CustomFieldConstants.FIELD_NOT_REQUIRED)+ "');\n");
				}
			}
		}
		return sb.toString();
	}
}
