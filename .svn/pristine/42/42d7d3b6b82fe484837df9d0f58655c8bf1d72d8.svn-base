package com.talentPool.admin.manager;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.admin.DuplicateSettingsConstants;
import com.talentPool.admin.dataobject.DuplicateSettingsData;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;

public class DuplicatePositionSettings {
	private static ArrayList<DuplicateSettingsData> internalSettings;
	

	public static void loadDuplicateSettings() {
		setDuplicateFields();
		setDuplicateFieldsFromDB();
	}

	private static void setDuplicateFields() {
		internalSettings = new ArrayList<DuplicateSettingsData>();
		
		DuplicateSettingsData data = null;
		if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_NAME)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.POSITION_NAME_ID, DuplicateSettingsConstants.LABEL_POSITION_NAME, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM,
					DuplicateSettingsConstants.SETTINGS_INTERNAL);
			internalSettings.add(data);
		}		
	
		
	
	
		
	
		
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, false);
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData cdata = customFields.get(i);
				if(PositionScreenConfigurationManager.isDescriptionFieldShow(cdata.getFieldName())) {
					data = new DuplicateSettingsData(cdata.getFieldId(), cdata.getFieldDisplayName(), DuplicateSettingsConstants.FIELD_TYPE_CUSTOM, DuplicateSettingsConstants.SETTINGS_INTERNAL);
					internalSettings.add(data);
				}
				
			}
		}
	}
	
	public static List<DuplicateSettingsData> getSettingList() {
		List<DuplicateSettingsData> settings = new ArrayList<DuplicateSettingsData>();
		DuplicateSettingsData data = null;
		if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_NAME)) {
			data = new DuplicateSettingsData(DuplicateSettingsConstants.POSITION_NAME_ID, DuplicateSettingsConstants.LABEL_POSITION_NAME, DuplicateSettingsConstants.FIELD_TYPE_SYSTEM, null);
			settings.add(data);
		}
		
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, false);
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData cdata = customFields.get(i);
				if(PositionScreenConfigurationManager.isDescriptionFieldShow(cdata.getFieldName())
					) {
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
			dq = new DBPreparedQuery("dDuplicatePositionSettings_GetSettings");
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
			dq = new DBPreparedQuery("dDuplicatePositionSettings_DeleteSettings", tran);
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
			dq = new DBPreparedQuery("dDuplicatePositionSettings_InsertSetting", tran);
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
}
