package com.talentPool.positions.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.dataobject.PositionFieldData;

public class PositionScreenConfigurationManager {

	private static HashMap<String, PositionFieldData> mapPositionListFields = null;
	private static ArrayList<PositionFieldData> positionListFields = null;

	private static HashMap<String, PositionFieldData> mapPositionDetailsFields = null;
	private static ArrayList<PositionFieldData> positionDetailsFields = null;

	private static HashMap<String, PositionFieldData> mapPositionScreenFields = null;
	private static ArrayList<PositionFieldData> positionScreenFields = null;

	private static HashMap<String, PositionFieldData> mapPositionDescriptionFields = null;
	private static ArrayList<PositionFieldData> positionDescriptionFields = null;

	private static HashMap<String, PositionFieldData> mapPositionRequirementsFields = null;
	private static ArrayList<PositionFieldData> positionRequirementsFields = null;

	public static void reloadPositionListFieldsMaps() {
		DBQuery dq = null;
		try {
			// initialize all maps
			mapPositionListFields = new HashMap<String, PositionFieldData>();

			dq = new DBQuery("dPositionConfigurationManager_GetAllFieldsOfPositionListView");
			ArrayList<PositionFieldData> result = dq.getResult();
			if (result != null) {
				positionListFields = result;
				defineListFieldHeaders();
				for (int i = 0; result != null && i < result.size(); i++) {
					PositionFieldData data = result.get(i);
					mapPositionListFields.put(data.getFieldId(), data);
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

	public static void reloadPositionDetailsFieldsMaps() {
		DBQuery dq = null;
		try {
			// initialize all maps
			mapPositionDetailsFields = new HashMap<String, PositionFieldData>();

			dq = new DBQuery("dPositionConfigurationManager_GetAllFieldsOfPositionDetailsView");
			ArrayList<PositionFieldData> result = dq.getResult();
			if (result != null) {
				positionDetailsFields = result;
				defineDetailsFieldHeaders();
				for (int i = 0; result != null && i < result.size(); i++) {
					PositionFieldData data = result.get(i);
					mapPositionDetailsFields.put(data.getFieldId(), data);
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

	public static void reloadPositionScreenFields() {
		DBQuery dq = null;
		try {
			// initialize all maps
			mapPositionScreenFields = new HashMap<String, PositionFieldData>();
			dq = new DBQuery("dPositionConfigurationManager_GetAllFieldsOfPositionScreenView");
			ArrayList<PositionFieldData> result = dq.getResult();
			if (result != null) {
				positionScreenFields = new ArrayList<PositionFieldData>();
				for (int i = 0; result != null && i < result.size(); i++) {
					PositionFieldData data = result.get(i);
					positionScreenFields.add(data);
				}
				defineScreenFieldHeaders();
				for (int i = 0; positionScreenFields != null && i < positionScreenFields.size(); i++) {
					PositionFieldData data = positionScreenFields.get(i);
					mapPositionScreenFields.put(data.getFieldId(), data);
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

	public static void reloadPositionDescriptionFields() {
		DBPreparedQuery dq = null;
		try {
			// initialize all maps
			mapPositionDescriptionFields = new HashMap<String, PositionFieldData>();
			String[] dynParam = new String[1];
			dynParam[0] = "";
			ArrayList<String> dynamicContentForDynParam = new ArrayList<String>();
			if (!GlobalConstants.ENABLED
					.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_IS_NAUKRI_INTEGRATION))) {
				dynParam[0] += " WHERE is_field_naukri != ? ";
				dynamicContentForDynParam.add(GlobalConstants.ENABLED);
			}
			dq = new DBPreparedQuery("dPositionConfigurationManager_GetAllFieldsOfPositionScreenDescription", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContentForDynParam.size(); i++) {
				dq.setString(cnt++, dynamicContentForDynParam.get(i));
			}
			ArrayList<PositionFieldData> result = dq.getResult();
			if (result != null) {
				positionDescriptionFields = new ArrayList<PositionFieldData>();
				for (int i = 0; result != null && i < result.size(); i++) {
					PositionFieldData data = result.get(i);
					positionDescriptionFields.add(data);
				}
				defineDescriptionFieldHeaders();
				for (int i = 0; positionDescriptionFields != null && i < positionDescriptionFields.size(); i++) {
					PositionFieldData data = positionDescriptionFields.get(i);
					mapPositionDescriptionFields.put(data.getFieldId(), data);
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

	public static void reloadPositionRequirementsFields() {
		DBQuery dq = null;
		try {
			// initialize all maps
			mapPositionRequirementsFields = new HashMap<String, PositionFieldData>();

			dq = new DBQuery("dPositionConfigurationManager_GetAllFieldsOfPositionScreenRequirements");
			ArrayList<PositionFieldData> result = dq.getResult();
			if (result != null) {
				positionRequirementsFields = new ArrayList<PositionFieldData>();
				for (int i = 0; result != null && i < result.size(); i++) {
					PositionFieldData data = result.get(i);
					positionRequirementsFields.add(data);
				}
				defineRequirementsFieldHeaders();
				for (int i = 0; positionRequirementsFields != null && i < positionRequirementsFields.size(); i++) {
					PositionFieldData data = positionRequirementsFields.get(i);
					mapPositionRequirementsFields.put(data.getFieldId(), data);
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

	public static ArrayList<PositionFieldData> getPositionListFields() {
		if (positionListFields == null) {
			reloadPositionListFieldsMaps();
		}
		return positionListFields;
	}

	public static ArrayList<PositionFieldData> getPositionScreenFields() {
		if (positionScreenFields == null) {
			reloadPositionScreenFields();
		}
		return positionScreenFields;
	}

	public static ArrayList<PositionFieldData> getPositionDetailsFields() {
		if (positionDetailsFields == null) {
			reloadPositionDetailsFieldsMaps();
		}
		return positionDetailsFields;
	}

	public static ArrayList<PositionFieldData> getPositionDescriptionFields() {
		if (positionDescriptionFields == null) {
			reloadPositionDescriptionFields();
		}
		return positionDescriptionFields;
	}

	public static HashMap<String, PositionFieldData> getPositionDescriptionFieldsMap() {
		if (mapPositionDescriptionFields == null) {
			reloadPositionDescriptionFields();
		}
		return mapPositionDescriptionFields;
	}

	public static ArrayList<PositionFieldData> getPositionRequirementsFields() {
		if (positionRequirementsFields == null) {
			reloadPositionRequirementsFields();
		}
		return positionRequirementsFields;
	}

	public static void reloadPositionFieldsMaps() {
		reloadPositionListFieldsMaps();
		reloadPositionDetailsFieldsMaps();
		reloadPositionScreenFields();
		reloadPositionDescriptionFields();
		reloadPositionRequirementsFields();
	}

	private static void defineListFieldHeaders() {
		positionListFields = defineTitle(positionListFields);
	}

	private static void defineDetailsFieldHeaders() {
		positionDetailsFields = defineTitle(positionDetailsFields);
	}

	private static void defineScreenFieldHeaders() {
		positionScreenFields = defineTitle(positionScreenFields);
	}

	private static void defineDescriptionFieldHeaders() {
		positionDescriptionFields = defineTitle(positionDescriptionFields);
	}

	private static void defineRequirementsFieldHeaders() {
		positionRequirementsFields = defineTitle(positionRequirementsFields);
	}

	private static ArrayList<PositionFieldData> defineTitle(ArrayList<PositionFieldData> posFieldList) {
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager
				.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_POSITION, false);
		for (int i = 0; i < posFieldList.size(); i++) {
			PositionFieldData fieldData = posFieldList.get(i);
			String fieldId = fieldData.getFieldId();
			String fieldType = fieldData.getFieldType();
			if (fieldType.equalsIgnoreCase(PositionConfigurationConstants.FIELD_TYPE_CUSTOM)) {
				for (int j = 0; j < customFields.size(); j++) {
					CustomFieldData data = (CustomFieldData) customFields.get(j);
					String customFieldId = data.getFieldName();
					if (customFieldId.equalsIgnoreCase(fieldId)) {
						fieldData.setFieldTitle(data.getFieldDisplayName());
						break;
					}
				}
			} else {
				if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_NAME)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.position_name"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CODE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.position_code"));
					// Added for Asian Paints
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CODE_AP)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.position_code_ap"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CREATED_ON)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.position") + " "
							+ TPLabels.getLabel("position.description.created_on"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LOCATION)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.location"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVELS)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.department_levels"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_1)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_2)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_3)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_4)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5));
					// Added for Asian Paints BEGIN
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LOCATION_LEVEL_1)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LOCATION_LEVEL_1));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LOCATION_LEVEL_2)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LOCATION_LEVEL_2));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LOCATION_LEVEL_3)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_LOCATION_LEVEL_3));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_FUNCTION)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_FUNCTION));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_PAY_GRADE)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_PAY_GRADE));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_JOB_CODE)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_JOB_CODE));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_COUNTRIES)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COUNTRIES));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_STATES)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_STATES));
					// Added for Asian Paints END
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_VACANCIES)) {
					fieldData.setFieldTitle(TPLabels.getLabel("common.vacancies"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_HIRE_BY_DATE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.hire_by_date"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LEVEL)) {
					fieldData.setFieldTitle(
							TPLabels.getLabel("common.position") + " " + TPLabels.getLabel("common.level"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REFERAL_FEES)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.position_referal_fees"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_RESPONSIBILITIES)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.job_responsibilities"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REQUIREMENTS)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.requirements.job_requirements"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.requirements.primary_skills"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_SECONDARY_SKILLS)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.requirements.secondary_skills"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_EDUCATION)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.requirements.minimum_education"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_BRANCH)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.requirements.branch"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_EXPERIENCE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.requirements.experience"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_NOTE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.note"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REQUESTEDBY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.requestedBy"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_APPROVEDBY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.approvedBy"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_GRADE)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_BAND)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_BUDGET_ITEM)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.budget_item"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_BUSINESS_UNIT)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_COST_CENTER)) {
					fieldData.setFieldTitle(
							GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_LABEL));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_TYPE_OF_VACANCY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.type_of_vacancy"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REPLACEMENT_EMP_CODE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.replacement_emp_code"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_POSITION_OWNER)) {
					fieldData.setFieldTitle(TPLabels.getLabel("global.position_owner"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_POSITION_TYPE_EXT_INT)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.description.position_type_ext_int"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CONTACT_PERSON_NAME)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.contact_peerson_name"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_JOB_INDUSTRY_CODE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.job_industry_code"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_JOB_FUNCTION_CODE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.job_function_code"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_JOB_ROLE_CODE)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.job_role_code"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_JOB_KEYWORDS)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.job_keywords"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_COUNTRY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.country"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MINIMUM_SALARY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.minimum_salary"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MAXIMUM_SALARY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.maximum_salary"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_BENEFITS_DESCRIPTION)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.benefits_description"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DISPLAY_SALARY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.display_salary"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DESIRED_CANDIDATE_SUMMARY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.desired_candidate_summary"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CONTACT_PERSON_EMAIL)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.contact_person_email"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_APPLY_BY_WEB_URL)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.apply_by_web_url"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_JOB_FIELD_RESPONSE_EMAIL)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.job_field_response_email"));
				} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.SALARY_CURRENCY)) {
					fieldData.setFieldTitle(TPLabels.getLabel("position.naukri.salary_currency"));
				}

			}
			posFieldList.set(i, fieldData);
		}
		return posFieldList;
	}

	public static boolean isPositionFieldShow(String fieldId) {
		boolean available = false;
		try {
			PositionFieldData positionFieldData = mapPositionScreenFields.get(fieldId);
			if (positionFieldData != null
					&& positionFieldData.getFieldPositionShow().equals(PositionConfigurationConstants.FIELD_SHOW)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isPositionFieldMandatory(String fieldId) {
		boolean available = false;
		try {
			PositionFieldData positionFieldData = mapPositionScreenFields.get(fieldId);
			if (positionFieldData != null && positionFieldData.getFieldPositionMandatory()
					.equals(PositionConfigurationConstants.FIELD_MANDATORY)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isDescriptionFieldShow(String fieldId) {
		boolean available = false;
		try {
			PositionFieldData positionFieldData = getPositionDescriptionFieldsMap().get(fieldId);
			if (positionFieldData != null
					&& positionFieldData.getFieldPositionShow().equals(PositionConfigurationConstants.FIELD_SHOW)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static boolean isDescriptionFieldMandatory(String fieldId) {
		boolean available = false;
		try {
			PositionFieldData positionFieldData = getPositionDescriptionFieldsMap().get(fieldId);
			if (positionFieldData != null && positionFieldData.getFieldPositionMandatory()
					.equals(PositionConfigurationConstants.FIELD_MANDATORY)) {
				available = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return available;
	}

	public static String getPositionFieldListPageRank(String fieldId) {
		String fieldRank = null;
		try {
			PositionFieldData positionFieldData = mapPositionListFields.get(fieldId);
			if (positionFieldData != null) {
				fieldRank = positionFieldData.getFieldRank();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return fieldRank;
	}

	public static String getPositionFieldDetailsPageRank(String fieldId) {
		String fieldRank = null;
		try {
			PositionFieldData positionFieldData = mapPositionDetailsFields.get(fieldId);
			if (positionFieldData != null) {
				fieldRank = positionFieldData.getFieldPositionDetailsRank();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return fieldRank;
	}

	public static boolean isPositionFieldFilterOnWebsite(String fieldId) {
		boolean isFilter = false;
		try {
			if (mapPositionListFields == null)
				reloadPositionListFieldsMaps();
			PositionFieldData positionFieldData = mapPositionListFields.get(fieldId);
			if (positionFieldData != null
					&& positionFieldData.getFieldIsFilter().equals(PositionConfigurationConstants.FIELD_IS_FILTER)) {
				isFilter = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return isFilter;
	}

}
