/**
 * 
 */
package com.talentPool.admin.manager;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.admin.DuplicateSettingsConstants;
import com.talentPool.admin.dataobject.DuplicateSettingsData;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;

/**
 * @author shivprasad
 * 
 */
public class DuplicateSettings {
	private static ArrayList<DuplicateSettingsData> internalSettings;
	private static ArrayList<DuplicateSettingsData> vendorSettings;
	private static ArrayList<DuplicateSettingsData> employeeSettings;

	public static void loadDuplicateSettings() {
		setDuplicateFields();
		setDuplicateFieldsFromDB();
	}

	private static void setDuplicateFields() {
		internalSettings = new ArrayList<DuplicateSettingsData>();
		vendorSettings = new ArrayList<DuplicateSettingsData>();
		employeeSettings = new ArrayList<DuplicateSettingsData>();
		DuplicateSettingsData data = null;
		if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_NAME)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.NAME_ID, DuplicateSettingsConstants.LABEL_NAME, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_INTERNAL);
			internalSettings.add(data);
		}		
		if(ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_NAME)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.NAME_ID, DuplicateSettingsConstants.LABEL_NAME, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_FOR_VENDOR);
			vendorSettings.add(data);
		}
		
		if(ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_NAME)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.NAME_ID, DuplicateSettingsConstants.LABEL_NAME, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_FOR_EMPLOYEE);
			employeeSettings.add(data);
		}
		
		if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EMAIL1) ||
				ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EMAIL2)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.EMAIL_ID, DuplicateSettingsConstants.LABEL_EMAIL, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_INTERNAL);
			internalSettings.add(data);
		}
		if(ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL1) ||
				ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL2)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.EMAIL_ID, DuplicateSettingsConstants.LABEL_EMAIL, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_FOR_VENDOR);
			vendorSettings.add(data);
		}
		
		if(ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL1) ||
				ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL2)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.EMAIL_ID, DuplicateSettingsConstants.LABEL_EMAIL, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_FOR_EMPLOYEE);
			employeeSettings.add(data);
		}
		
		if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_MOBILE)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.PHONE_ID, DuplicateSettingsConstants.LABEL_PHONE, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_INTERNAL);
			internalSettings.add(data);
		}
		if(ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_MOBILE)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.PHONE_ID, DuplicateSettingsConstants.LABEL_PHONE, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_FOR_VENDOR);
			vendorSettings.add(data);
		}	
		if(ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_MOBILE)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.PHONE_ID, DuplicateSettingsConstants.LABEL_PHONE, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_FOR_EMPLOYEE);
			employeeSettings.add(data);
		}	
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, false);
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData cdata = customFields.get(i);
				if(ImportConfigurationManager.isImportFieldShow(cdata.getFieldName())) {
					data = new DuplicateSettingsData(cdata.getFieldId(), cdata.getFieldDisplayName(), DuplicateSettingsConstants.FIELD_TYPE_CUSTOM, DuplicateSettingsConstants.SETTINGS_INTERNAL);
					internalSettings.add(data);
				}
				if(ImportConfigurationManager.isVendorFieldShow(cdata.getFieldName())) {
					data = new DuplicateSettingsData(cdata.getFieldId(), cdata.getFieldName(), DuplicateSettingsConstants.FIELD_TYPE_CUSTOM, DuplicateSettingsConstants.SETTINGS_FOR_VENDOR);
					vendorSettings.add(data);
				}
				if(ImportConfigurationManager.isEmployeeFieldShow(cdata.getFieldName())) {
					data = new DuplicateSettingsData(cdata.getFieldId(), cdata.getFieldName(), DuplicateSettingsConstants.FIELD_TYPE_CUSTOM, DuplicateSettingsConstants.SETTINGS_FOR_EMPLOYEE);
					employeeSettings.add(data);
				}
			}
		}
	}
	
	public static List<DuplicateSettingsData> getSettingList() {
		List<DuplicateSettingsData> settings = new ArrayList<DuplicateSettingsData>();
		DuplicateSettingsData data = null;
		if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_NAME)
			|| ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_NAME)
			|| ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_NAME)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.NAME_ID, DuplicateSettingsConstants.LABEL_NAME, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM, null);
			settings.add(data);
		}
		if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EMAIL1)
			|| ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EMAIL2)
			|| ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL1)
			|| ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_EMAIL2)
			|| ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_EMAIL1)
			|| ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_EMAIL2)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.EMAIL_ID, DuplicateSettingsConstants.LABEL_EMAIL, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM, null);
			settings.add(data);
		}
		if(ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_MOBILE)
			|| ImportConfigurationManager.isVendorFieldShow(ImportConfigurationConstants.FIELD_MOBILE)
			|| ImportConfigurationManager.isEmployeeFieldShow(ImportConfigurationConstants.FIELD_MOBILE)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.PHONE_ID, DuplicateSettingsConstants.LABEL_PHONE, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM, null);
			settings.add(data);
		}	
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, false);
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData cdata = customFields.get(i);
				if(ImportConfigurationManager.isImportFieldShow(cdata.getFieldName())
					|| ImportConfigurationManager.isVendorFieldShow(cdata.getFieldName())
					|| ImportConfigurationManager.isEmployeeFieldShow(cdata.getFieldName())) {
					data = new DuplicateSettingsData(cdata.getFieldId(), cdata.getFieldDisplayName(), DuplicateSettingsConstants.FIELD_TYPE_CUSTOM, null);
					settings.add(data);
				}
			}
		}
		return settings;
	}

	private static void setDuplicateFieldsFromDB() {
		ArrayList<DuplicateSettingsData> results = getDuplicateSettings();
		for (int i = 0; results != null && i < results.size(); i++) {
			DuplicateSettingsData data = results.get(i);
			if (data.getCheckFor().equals(DuplicateSettingsConstants.SETTINGS_INTERNAL)) {
				for (int k = 0; k < internalSettings.size(); k++) {
					DuplicateSettingsData idata = internalSettings.get(k);
					if (idata.getFieldId().equals(data.getFieldId()) && idata.getFieldType().equals(data.getFieldType())) {
						idata.setFieldCheckType(data.getFieldCheckType());
					}
				}
			} else if(data.getCheckFor().equals(DuplicateSettingsConstants.SETTINGS_FOR_VENDOR)) {
				for (int k = 0; k < vendorSettings.size(); k++) {
					DuplicateSettingsData idata = vendorSettings.get(k);
					if (idata.getFieldId().equals(data.getFieldId()) && idata.getFieldType().equals(data.getFieldType())) {
						idata.setFieldCheckType(data.getFieldCheckType());
					}
				}
			}else if(data.getCheckFor().equals(DuplicateSettingsConstants.SETTINGS_FOR_EMPLOYEE)) {
				for (int k = 0; k < employeeSettings.size(); k++) {
					DuplicateSettingsData idata = employeeSettings.get(k);
					if (idata.getFieldId().equals(data.getFieldId()) && idata.getFieldType().equals(data.getFieldType())) {
						idata.setFieldCheckType(data.getFieldCheckType());
					}
				}
			}
		}
	}

	/**
	 * @return
	 */
	private static ArrayList<DuplicateSettingsData> getDuplicateSettings() {
		ArrayList<DuplicateSettingsData> results = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDuplicateSettings_GetSettings");
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting duplicate detection settings", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;

	}

	/**
	 * @param settingsList
	 * @throws Exception
	 */
	public void updateDuplicateSettings(ArrayList<DuplicateSettingsData> settingsList) throws Exception {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			deleteDuplicateSettings(tran);
			for (int i = 0; settingsList != null && i < settingsList.size(); i++) {
				DuplicateSettingsData duplicateSettingsData = settingsList.get(i);
				insertDuplicateSetting(duplicateSettingsData, tran);
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating duplicate detection settings");
			tran.rollback();
			throw e;
		} finally {
			if (tran != null) {
				tran.release();
			}
		}
	}

	/**
	 * @param tran
	 * @throws Exception
	 */
	private void deleteDuplicateSettings(DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDuplicateSettings_DeleteSettings", tran);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting duplicate detection settings", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	/**
	 * @param duplicateSettingsData
	 * @param tran
	 * @throws Exception
	 */
	private void insertDuplicateSetting(DuplicateSettingsData duplicateSettingsData, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dDuplicateSettings_InsertSetting", tran);
			dq.setString(1, duplicateSettingsData.getFieldId());
			dq.setString(2, duplicateSettingsData.getFieldType());
			dq.setString(3, duplicateSettingsData.getFieldCheckType());
			dq.setString(4, duplicateSettingsData.getCheckFor());
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting duplicate detection settings", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	/**
	 * 
	 * @return the internalSettings with all settings data including those also which are not set
	 */
	public static ArrayList<DuplicateSettingsData> getInternalSettings() {
		if (internalSettings == null) {
			loadDuplicateSettings();
		}
		return internalSettings;
	}

	/**
	 * @return the vendorSettings with all settings data including those also which are not set
	 */
	public static ArrayList<DuplicateSettingsData> getVendorSettings() {
		if (vendorSettings == null) {
			loadDuplicateSettings();
		}
		return vendorSettings;
	}
	
	/**
	 * @return the employeeSettings with all settings data including those also which are not set
	 */
	public static ArrayList<DuplicateSettingsData> getEmployeeSettings() {
		if (employeeSettings == null) {
			loadDuplicateSettings();
		}
		return employeeSettings;
	}
}
