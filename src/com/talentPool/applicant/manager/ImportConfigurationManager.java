/**
 * 
 */
package com.talentPool.applicant.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ImportFieldData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.user.manager.PermissionSet;


/**
 * @author Ajeet
 * 
 */
public class ImportConfigurationManager {

	/* mapImportFields holds the map of fields */
	private static HashMap<String, ImportFieldData> mapImportFields = null;
	private static ArrayList<ImportFieldData> importFields = null;
	private static Map<String,String> importFieldsDbMapping = new HashMap<String, String>();
	
	static {
		reloadImportFieldsMaps();
		setImportFieldsDbMapping();
	}

	public static void reloadImportFieldsMaps() {
		DBQuery dq = null;
		try {
			// initialize all maps
			mapImportFields = new HashMap<String, ImportFieldData>();

			dq = new DBQuery("dImportConfigurationManager_GetAllFields");
			ArrayList<ImportFieldData> result = dq.getResult();
			if (result != null) {
				importFields = result;
				defineFieldHeaders();
				for (int i = 0; result != null && i < result.size(); i++) {
					ImportFieldData data = result.get(i);
					mapImportFields.put(data.getFieldId(), data);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public static ArrayList<ImportFieldData> getImportFields() {
		if (importFields == null) {
			reloadImportFieldsMaps();
		}
		return importFields;
	}

	public static boolean isImportFieldShow(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldImportShow().equals(ImportConfigurationConstants.FIELD_SHOW)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isImportFieldMandatory(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldImportMandatory().equals(ImportConfigurationConstants.FIELD_MANDATORY)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isEditFieldShow(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldEditShow().equals(ImportConfigurationConstants.FIELD_EDIT)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isImportOrEditFieldShow(String fieldId) {
		return (isImportFieldShow(fieldId) || isEditFieldShow(fieldId));
	}

	public static boolean isImportFieldShowAndMandatory(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldImportShow().equals(ImportConfigurationConstants.FIELD_SHOW)) {
				if (importFieldData != null && importFieldData.getFieldImportMandatory().equals(ImportConfigurationConstants.FIELD_MANDATORY)) {
					available = true;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static String importFieldShowValue(String fieldId) {
		String value = "";
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null) {
				value = importFieldData.getFieldImportShow();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return value;
	}

	public static String importFieldMandatoryValue(String fieldId) {
		String value = "";
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null) {
				value = importFieldData.getFieldImportMandatory();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return value;
	}

	public static String importEditFieldValue(String fieldId) {
		String value = "";
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null) {
				value = importFieldData.getFieldEditShow();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return value;
	}

	private static void defineFieldHeaders() {
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_APPLICANT, false);
		ArrayList<CustomFieldData> cTableDatas = customFieldManager.getApplicantTabularCustomFields();
		for (int i = 0; i < importFields.size(); i++) {
			ImportFieldData fieldData = importFields.get(i);
			if(!Utils.isBlankOrNull(fieldData.getParentId())){
				importFields.remove(i);
				continue;
			}
			String fieldId = fieldData.getFieldId();
			String fieldType = fieldData.getFieldType();
			if (fieldType.equals(ImportConfigurationConstants.FIELD_TYPE_CUSTOM)) {
				for (int j = 0; j < customFields.size(); j++) {
					CustomFieldData data = (CustomFieldData) customFields.get(j);
					String customFieldId = data.getFieldName();
					if (customFieldId.equals(fieldId)) {
						fieldData.setFieldTitle(data.getFieldDisplayName());
						break;
					}
				}
			} else if(fieldType.equals(ImportConfigurationConstants.FIELD_TYPE_CUSTOM_TABLE)){
				for (int j = 0; j < cTableDatas.size(); j++) {
					CustomFieldData data = (CustomFieldData) cTableDatas.get(j);
					String customFieldId = data.getTableName();
					if (customFieldId.equals(fieldId)) {
						fieldData.setFieldTitle(customFieldId);
						break;
					}
				}
			}else {
				if (fieldId.equals(ImportConfigurationConstants.FIELD_NAME)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.name"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL1)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.email1"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL2)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.email2"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_PHONE1)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.phone1"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_PHONE2)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.phone2"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_MOBILE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.mobile"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_DATE_OF_BIRTH)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.date_of_birth"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_SOURCE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.source"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_LOCATION)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.current_location"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_SKILLS)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.skills"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_EDUCATION)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.education"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_EXPERIENCE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.experience"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.current_employer"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_CTC)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.current_ctc"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_EXPECTED_CTC)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.expected_ctc"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_NOTICE_PERIOD)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.notice_period"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_DATE_OF_BIRTH)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.date_of_birth"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_PASSPORT_NUMBER)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.passport_number"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_RESUME_TYPE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.resume_type"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_NOTE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.note"));
				} else if (fieldId.equals(ImportConfigurationConstants.FIELD_Confidential)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.confidential"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_CTC_OFFERED)) {
					fieldData.setFieldTitle(TPLabels.getLabel("selection_feedback.label.ctc_offered"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_BASIC_OFFERED)) {
					fieldData.setFieldTitle(TPLabels.getLabel("selection_feedback.label.basic_offered"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_LEVEL_OFFERED)) {
					fieldData.setFieldTitle(TPLabels.getLabel("selection_feedback.label.level_offered"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_INPUT_SALARY_VARIABLE)) {
					fieldData.setFieldTitle(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_DESIGNATION_OFFERED)) {
					fieldData.setFieldTitle(TPLabels.getLabel("selection_feedback.label.designation_offered"));
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_EMPLOYMENT_HISTORY)) {
					fieldData.setFieldTitle("Employment History");
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_CATEGORY)) {
					fieldData.setFieldTitle("Category");
				}else if (fieldId.equals(ImportConfigurationConstants.FIELD_SUB_CATEGORY)) {
					fieldData.setFieldTitle("Sub_category");
				}
				
				
			}
			importFields.set(i, fieldData);
		}
	}
	
	private static void setImportFieldsDbMapping(){
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_NAME, "applicant_name");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_EMAIL1, "applicant_email1");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_EMAIL2, "applicant_email2");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_PHONE1, "applicant_home_phone");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_PHONE2, "applicant_work_phone");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_MOBILE, "applicant_cell_phone");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_DATE_OF_BIRTH, "date_of_birth");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_SOURCE, "source_id");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_CURRENT_LOCATION, "applicant_city");
		//importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_SKILLS, "");
		//importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_EDUCATION, "");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_EXPERIENCE, "applicant_working_since");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER, "applicant_current_employer");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_CURRENT_CTC, "current_ctc");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_EXPECTED_CTC, "expected_ctc");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_NOTICE_PERIOD, "applicant_notice_period");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_DATE_OF_BIRTH, "date_of_birth");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_PASSPORT_NUMBER, "passport_number");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_RESUME_TYPE, "resume_type_id");
		//importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_NOTE, "");
		//importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_Confidential, "");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_CTC_OFFERED, "offered_ctc");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_BASIC_OFFERED, "offered_basic");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_LEVEL_OFFERED, "applicant_level_offered");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_DESIGNATION_OFFERED, "applicant_designation_offered");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_INPUT_SALARY_VARIABLE, "input_salary_variable");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_CATEGORY, "category");
		importFieldsDbMapping.put(ImportConfigurationConstants.FIELD_SUB_CATEGORY, "sub_category");
	}
	
	public static String getImportFieldDbMapping(String fieldId){
		return importFieldsDbMapping.get(fieldId);
	} 

	public static boolean isVendorFieldShow(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldVendorShow().equals(ImportConfigurationConstants.FIELD_SHOW)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isVendorFieldMandatory(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldVendorMandatory().equals(ImportConfigurationConstants.FIELD_MANDATORY)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}
	
	public static boolean isVendorFieldShowAndMandatory(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldVendorShow().equals(ImportConfigurationConstants.FIELD_SHOW)) {
				if (importFieldData != null && importFieldData.getFieldVendorMandatory().equals(ImportConfigurationConstants.FIELD_MANDATORY)) {
					available = true;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}
	
	
	
	//Employee 
	public static boolean isEmployeeFieldShow(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldEmployeeShow().equals(ImportConfigurationConstants.FIELD_SHOW)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isEmployeeFieldMandatory(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldEmployeeMandatory().equals(ImportConfigurationConstants.FIELD_MANDATORY)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}
	
	public static boolean isEmployeeFieldShowAndMandatory(String fieldId) {
		boolean available = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null && importFieldData.getFieldEmployeeShow().equals(ImportConfigurationConstants.FIELD_SHOW)) {
				if (importFieldData != null && importFieldData.getFieldEmployeeMandatory().equals(ImportConfigurationConstants.FIELD_MANDATORY)) {
					available = true;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}
		
	public static boolean isApplicantFieldViewable(String fieldId, boolean isShowConfidentialData) {
		boolean viewable = false;		
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null){ 
				if(importFieldData.getFieldConfidential().equals(ImportConfigurationConstants.FIELD_CONFIDENTIAL)) {			
					if(isShowConfidentialData){
						viewable = true;
					}
				}else{
					viewable = true;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return viewable;
	}
	
	public static boolean isApplicantCustomField(String fieldId) {
		boolean isCustomField = false;
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData!=null && ImportConfigurationConstants.FIELD_TYPE_CUSTOM.equals(importFieldData.getFieldType())) {
				isCustomField = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return isCustomField;
	}
	
	
	public static boolean isProcessFieldViewable(String fieldId,PermissionSet permissionSet) {
		if(isSalaryFieldViewable(permissionSet)){
			return true;
		}else {
			return isApplicantFieldViewable(fieldId, permissionSet.isSHOW_CONFIDENTIAL_DATA());
		}
	}
	
	public static boolean isCTCOfferedViewable(PermissionSet permissionSet) {
		return isProcessFieldViewable(ImportConfigurationConstants.FIELD_CTC_OFFERED, permissionSet);
	}
	
	public static boolean isBasicOfferedViewable(PermissionSet permissionSet) {
		return isProcessFieldViewable(ImportConfigurationConstants.FIELD_BASIC_OFFERED, permissionSet);
	}
	
	public static boolean isDesignationOfferedViewable(PermissionSet permissionSet) {
		return isProcessFieldViewable(ImportConfigurationConstants.FIELD_DESIGNATION_OFFERED, permissionSet);
	}
	
	public static boolean isLevelOfferedViewable(PermissionSet permissionSet) {
		return isProcessFieldViewable(ImportConfigurationConstants.FIELD_LEVEL_OFFERED, permissionSet);
	}
	
	public static boolean isInputSalaryVariableViewable(PermissionSet permissionSet) {
		return isProcessFieldViewable(ImportConfigurationConstants.FIELD_INPUT_SALARY_VARIABLE, permissionSet);
	}
	
	public static boolean isCurrentCTCViewable(PermissionSet permissionSet) {
		if(isSalaryFieldViewable(permissionSet)){
			return true;	
		} else {
			return isApplicantFieldViewable(ImportConfigurationConstants.FIELD_CURRENT_CTC, permissionSet.isSHOW_CONFIDENTIAL_DATA());
		}
	}
	
	public static boolean isExpectedCTCViewable(PermissionSet permissionSet) {
		if(isSalaryFieldViewable(permissionSet)){
			return true;	
		} else {
			return isApplicantFieldViewable(ImportConfigurationConstants.FIELD_EXPECTED_CTC, permissionSet.isSHOW_CONFIDENTIAL_DATA());
		}
	}
	
	public static boolean isSalaryFieldViewable(PermissionSet permissionSet){
		return permissionSet.isPERMISSION_GENERATE_OFFER();
	}
	
	public static String getFieldTitle(String fieldId) {
		String fieldTitle = "";
		try {
			ImportFieldData importFieldData = mapImportFields.get(fieldId);
			if (importFieldData != null) {
				fieldTitle = importFieldData.getFieldTitle();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return fieldTitle;
	}
}

