package com.talentPool.applicant.utils;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.HTMLUtils.HTMLUtils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.user.manager.PermissionSet;

public class ApplicantFieldUtils {
	
	public static String buildImportFieldUIForFeedbackForm(String fieldId, ApplicantData applicantData, Map<String,String> attributes, PermissionSet permissionSet){
		return buildImportFieldUI(fieldId, applicantData, attributes, true, permissionSet);
	}
	
	public static String buildImportFieldUI(String fieldId, ApplicantData applicantData, Map<String,String> attributes, boolean displayValueSeparate, PermissionSet permissionSet) {
		String importFieldUI = null;
		if(ImportConfigurationManager.isApplicantCustomField(fieldId)){
			importFieldUI = getCustomFieldUI(fieldId, applicantData.getCustomFieldsMap(), attributes, displayValueSeparate);
		}else {
			importFieldUI = getApplicantFieldUI(fieldId, applicantData, attributes, displayValueSeparate, permissionSet);
		}
		return importFieldUI;
	}
	
	public static String getCustomFieldUI(String fieldId,Map<String,CustomFieldData> cusFldMap,Map<String,String> attributes, boolean displayValueSeparate){
		CustomFieldData cData  = cusFldMap.get(fieldId);
		String cutomFieldUI = null;
		if(CustomFieldConstants.TYPE_TEXT.equals(cData.getFieldType())){
			cutomFieldUI = getDefaultTextInputUI(attributes, cData.getDisplayValue(), displayValueSeparate);
		}else if(CustomFieldConstants.TYPE_NUMBER.equals(cData.getFieldType())){
			cutomFieldUI = getDefaultNumberInputUI(attributes, cData.getDisplayValue(), displayValueSeparate);
		}else if(CustomFieldConstants.TYPE_DATE.equals(cData.getFieldType())){
			cutomFieldUI = getCustomFieldDateUI(cData, attributes, displayValueSeparate);
		}
		return cutomFieldUI;
	}
	
	public static String getApplicantFieldUI(String fieldId, ApplicantData aData, Map<String,String> attributes, boolean displayValueSeparate,PermissionSet permissionSet){
		String applicantFieldUI = "";
		if(ImportConfigurationConstants.FIELD_NAME.equals(fieldId)){
			applicantFieldUI = getApplicantNameUI(attributes, aData.getApplicantName(), displayValueSeparate);
		} else if(ImportConfigurationConstants.FIELD_EMAIL1.equals(fieldId)){
			applicantFieldUI = getApplicantEmail1UI(attributes, aData.getApplicantEmail1(), displayValueSeparate);
		}else if(ImportConfigurationConstants.FIELD_EMAIL2.equals(fieldId)){
			applicantFieldUI = getApplicantEmail2UI(attributes, aData.getApplicantEmail2(), displayValueSeparate);
		}else if(ImportConfigurationConstants.FIELD_PHONE1.equals(fieldId)){
			applicantFieldUI = getApplicantHomePhoneUI(attributes, aData.getApplicantHomePhone(), displayValueSeparate);
		}else if(ImportConfigurationConstants.FIELD_PHONE2.equals(fieldId)){
			applicantFieldUI = getApplicantWorkPhoneUI(attributes, aData.getApplicantWorkPhone(), displayValueSeparate);
		}else if(ImportConfigurationConstants.FIELD_MOBILE.equals(fieldId)){
			applicantFieldUI = getApplicantCellPhoneUI(attributes, aData.getApplicantCellPhone(), displayValueSeparate);
		}else if(ImportConfigurationConstants.FIELD_SOURCE.equals(fieldId)){
			//Need To Implement
		}else if(ImportConfigurationConstants.FIELD_CURRENT_LOCATION.equals(fieldId)){
			applicantFieldUI = getApplicantCityUI(attributes, aData.getApplicantCity(), displayValueSeparate);
		}
		else if(ImportConfigurationConstants.FIELD_CATEGORY.equals(fieldId)){
			applicantFieldUI = getCategory(attributes, aData.getCategory(), displayValueSeparate);
		}
		else if(ImportConfigurationConstants.FIELD_SUB_CATEGORY.equals(fieldId)){
			applicantFieldUI = getSubCategory(attributes, aData.getSubCategory(), displayValueSeparate);
		}
		else if(ImportConfigurationConstants.FIELD_EXPERIENCE.equals(fieldId)){
			applicantFieldUI = getApplicantWorkingSinceUI(attributes, aData.getApplicantWorkingSince(), displayValueSeparate);
		}else if(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER.equals(fieldId)){
			applicantFieldUI = getApplicantCurrentEmployerUI(attributes, aData.getApplicantCurrentEmployer(), displayValueSeparate);
		}else if(ImportConfigurationConstants.FIELD_CURRENT_CTC.equals(fieldId)){
			if(ImportConfigurationManager.isCurrentCTCViewable(permissionSet))
				applicantFieldUI = getApplicantCurrentCTCUI(attributes, aData.getCurrentCTC(), displayValueSeparate);
			else{
				attributes.put("disabled", "disabled");
				attributes.put("class", "Grey");
				applicantFieldUI = getApplicantCurrentCTCUI(attributes, GlobalConstants.CONFIDENTIAL_CHARACTER, displayValueSeparate);
			}
		}else if(ImportConfigurationConstants.FIELD_EXPECTED_CTC.equals(fieldId)){
			if(ImportConfigurationManager.isExpectedCTCViewable(permissionSet)){
				applicantFieldUI = getApplicantExpectedCTCUI(attributes, aData.getExpectedCTC(), displayValueSeparate);
			}else{
				attributes.put("disabled", "disabled");
				attributes.put("class", "Grey");
				applicantFieldUI = getApplicantExpectedCTCUI(attributes, GlobalConstants.CONFIDENTIAL_CHARACTER, displayValueSeparate);
			}
		}else if(ImportConfigurationConstants.FIELD_NOTICE_PERIOD.equals(fieldId)){
			applicantFieldUI = getApplicantNoticePeriodUI(attributes, aData.getNoticePeriod(), displayValueSeparate);
		}
		
		/*else if(ImportConfigurationConstants.FIELD_NOTE.equals(fieldId)){
			// Not Present in note
		}else if(ImportConfigurationConstants.FIELD_Confidential.equals(fieldId)){
			//value = applicantData.getIsConfidential();
		}else if(ImportConfigurationConstants.FIELD_CTC_OFFERED.equals(fieldId)){
			//value = applicantData.getCtcOffered();
		}else if(ImportConfigurationConstants.FIELD_BASIC_OFFERED.equals(fieldId)){
			//value = applicantData.getBasicOffered();
		}else if(ImportConfigurationConstants.FIELD_LEVEL_OFFERED.equals(fieldId)){
			//value = applicantData.getLevelOffered();
		}else if(ImportConfigurationConstants.FIELD_DESIGNATION_OFFERED.equals(fieldId)){
			//value = applicantData.getDesignationOffered();
		}else if(ImportConfigurationConstants.FIELD_INPUT_SALARY_VARIABLE.equals(fieldId)){
			//value = applicantData.getInputSalaryVariable();
		}*/
		return applicantFieldUI;
	}
	
	private static String getDefaultTextInputUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		if(displayValueSeparate){
			return HTMLUtils.writeInputTag("text", attributes)+HTMLUtils.HTML_SPACE+"["+Utils.getBlankIfNull(value)+"]";
		}else {
			attributes.put("value", value);
			return HTMLUtils.writeInputTag("text", attributes);
		}
	}
	
	private static String getDefaultNumberInputUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("onblur", "getFNumber(this);");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getCustomFieldDateUI(CustomFieldData cData, Map<String,String> attributes, boolean displayValueSeparate){
		StringBuffer dateUI = new StringBuffer();
		String format 		= cData.getOtherAttribute(CustomFieldConstants.ATTRIBUTE_DATE_FORMAT);
		String id 			= attributes.get("id");
		
		attributes.put("onblur", "getFDate(this,'" + format + "');\" ");
		
		if(displayValueSeparate){
			dateUI.append(HTMLUtils.writeInputTag("text", attributes))
					.append(getImageElementForDate(id,format))
					.append(HTMLUtils.HTML_SPACE+"["+Utils.getBlankIfNull(cData.getDisplayValue())+"]");	
		}else {
			attributes.put("value", cData.getDisplayValue());
			dateUI.append(HTMLUtils.writeInputTag("text", attributes))
			.append(getImageElementForDate(id,format));
		}
		return dateUI.toString();
	}
	
	private static String getApplicantNameUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "100");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantEmail1UI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "50");
		attributes.put("onblur", "validateEmail1(this);");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantEmail2UI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "50");
		attributes.put("onblur", "validateEmail2(this);");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	
	private static String getApplicantHomePhoneUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "25");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantWorkPhoneUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "25");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantCellPhoneUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "25");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantCityUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "50");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getCategory(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "50");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getSubCategory(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "50");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	private static String getApplicantWorkingSinceUI(Map<String,String> attributes, Date workingSince, boolean displayValueSeparate){
		StringBuffer workingSinceUI = new StringBuffer();
		String value = "";
		String id 			= attributes.get("id");
		if(workingSince!=null)
			value = Utils.getDateConvertedToString(workingSince, Utils.regMMMYYYYFormat);
		else
			value = "Fresher";
		
		attributes.put("onblur", "getWorkingSinceFormated(this,'MMM-YYYY');");
		
		if(displayValueSeparate){
			workingSinceUI.append(HTMLUtils.writeInputTag("text", attributes))
					.append("<img src=\"images/ico_cal.gif\" onclick=\"workingSinceCal.showCalender('"+id+"','"+id+"');\" class=\"CalImg\" />")
					.append(HTMLUtils.HTML_SPACE+"["+Utils.getBlankIfNull(value)+"]");	
		}else {
			attributes.put("value", value);
			workingSinceUI.append(HTMLUtils.writeInputTag("text", attributes))
			.append("<img src=\"images/ico_cal.gif\" onclick=\"workingSinceCal.showCalender('"+id+"','"+id+"');\" class=\"CalImg\" />");
		}
		return workingSinceUI.toString();
	}
	
	
	private static String getApplicantCurrentEmployerUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "250");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantCurrentCTCUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "10");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantExpectedCTCUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "10");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	private static String getApplicantNoticePeriodUI(Map<String,String> attributes, String value, boolean displayValueSeparate){
		attributes.put("maxLength", "25");
		return getDefaultTextInputUI(attributes, value, displayValueSeparate);
	}
	
	public static String getImageElementForDate(String fieldName, String format){
		Map<String,String> attributes = new HashMap<String, String>();
		attributes.put("style", HTMLUtils.DEFAULT_DATE_IMG_STYLE);
		attributes.put("onclick", getDateOnClickEvet(fieldName,format,true));
		HTMLUtils.writeImageTag(HTMLUtils.DEFAULT_DATE_IMG, attributes);
		return HTMLUtils.writeImageTag(HTMLUtils.DEFAULT_DATE_IMG, attributes);
	}
	
	public static String getDateOnClickEvet(String elmentId,String format,boolean clonePopUpPosition){
		String onclickEvent = "popUpCal.select($('"+elmentId+"'),'"+elmentId+"','"+format+"'); ";
		if(clonePopUpPosition)
			onclickEvent+="cloneElemPosition('"+ elmentId +"'); ";
		onclickEvent +="return false;";
		return onclickEvent;
	}
}
