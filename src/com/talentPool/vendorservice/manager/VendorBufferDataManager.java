/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.util.ArrayList;
import java.util.TimeZone;

import com.talentPool.applicant.dataobject.ImportFieldData;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.vendorservice.VendorServiceConstants;
import com.talentPool.vendorservice.dataobject.VcustomFieldData;
import com.talentPool.vendorservice.dataobject.VcustomFieldDataList;
import com.talentPool.vendorservice.dataobject.VidsNames;
import com.talentPool.vendorservice.dataobject.VimportFieldData;
import com.talentPool.vendorservice.dataobject.VimportFieldList;
import com.talentPool.vendorservice.dataobject.VinboxData;

/**
 * @author shivprasad
 * 
 */
public class VendorBufferDataManager {
	/**
	 * @param type
	 * @return getIdNamesData
	 */
	public VidsNames getIdNamesData(String type) {
		VidsNames vidsNames = new VidsNames();
		try {
			if (type.equals(VendorServiceConstants.GET_BRANCHES)) {
				vidsNames.setIds(CommonUtils.getBranchIds());
				vidsNames.setNames(CommonUtils.getBranchNames());
			} else if (type.equals(VendorServiceConstants.GET_DEGREES)) {
				vidsNames.setIds(CommonUtils.getDegreeIds());
				vidsNames.setNames(CommonUtils.getDegreeNames());
			} else if (type.equals(VendorServiceConstants.GET_SKILLS)) {
				vidsNames.setIds(CommonUtils.getSkillIds());
				vidsNames.setNames(CommonUtils.getSkillNames());
			} else if (type.equals(VendorServiceConstants.GET_RESUME_TYPES)) {
				vidsNames.setIds(CommonUtils.getResumeTypeIds());
				vidsNames.setNames(CommonUtils.getResumeTypeNames());
			} else if (type.equals(VendorServiceConstants.GET_SECURITY_QUESTIONS)) {
				vidsNames.setIds(CommonUtils.getSecurityQuestionIds());
				vidsNames.setNames(CommonUtils.getSecurityQuestionNames());
			}else if (type.equals(EmployeeServiceConstants.GET_TIME_ZONE_TYPES)){
				String [] ids = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALL_SELECTED_TIME_ZONES).split(",");
				ArrayList<String> zoneIds = new ArrayList<String>();
				ArrayList<String> zoneValues = new ArrayList<String>();
				for(String id:ids) {
					zoneIds.add(id);
					TimeZone zone = TimeZone.getTimeZone(id);
					int offset = zone.getRawOffset()/1000;
					int hour = offset/3600;
					int minutes = (offset % 3600)/60;
					zoneValues.add(String.format("(GMT%+d:%02d) %s", hour, minutes, id));
				}   
				vidsNames.setIds(zoneIds);
				vidsNames.setNames(zoneValues);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return vidsNames;
	}

	/**
	 * @return VcustomFieldDataList
	 */
	public VcustomFieldDataList getCustomFieldList() {
		VcustomFieldDataList vcustomFieldDataList = new VcustomFieldDataList();
		try {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			ArrayList<CustomFieldData> tabCustomFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
			customFields.addAll(tabCustomFields);
			getVcustomFieldsFromCustomFields(customFields);
			ArrayList<VcustomFieldData> vcustomFields = new ArrayList<VcustomFieldData>();
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData customFieldData = customFields.get(i);
				VcustomFieldData vcustomFieldData = new VcustomFieldData();
				if (!Utils.isBlankOrNull(customFieldData.getFieldName())) {
					vcustomFieldData.setFieldName(customFieldData.getFieldName());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldDisplayName())) {
					vcustomFieldData.setFieldDisplayName(customFieldData.getFieldDisplayName());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldAttributes())) {
					vcustomFieldData.setFieldAttributes(customFieldData.getFieldAttributes());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldOtherAttributes())) {
					vcustomFieldData.setFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldId())) {
					vcustomFieldData.setFieldId(customFieldData.getFieldId());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldOptions())) {
					vcustomFieldData.setFieldOptions(customFieldData.getFieldOptions());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldType())) {
					vcustomFieldData.setFieldType(customFieldData.getFieldType());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldDefaultValue())) {
					vcustomFieldData.setFieldDefaultValue(customFieldData.getFieldDefaultValue());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldStringValue())) {
					vcustomFieldData.setFieldStringValue(customFieldData.getFieldStringValue());
				}
				if (customFieldData.getFieldDateValue() != null) {
					vcustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
				}
				
				vcustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
				vcustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
				vcustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
				vcustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
				vcustomFieldData.setFieldRank(customFieldData.getFieldRank());
				vcustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
				vcustomFieldData.setTableId(customFieldData.getTableId());
				vcustomFieldData.setTableName(customFieldData.getTableName());
				vcustomFields.add(vcustomFieldData);

			}
			vcustomFieldDataList.setCustomFields(vcustomFields);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return vcustomFieldDataList;
	}
	
	/**
	 * @param customFields
	 * @return
	 */
	public static ArrayList<VcustomFieldData> getVcustomFieldsFromCustomFields(
			ArrayList<CustomFieldData> customFields) {
		ArrayList<VcustomFieldData> vcustomFields = new ArrayList<VcustomFieldData>();
		VcustomFieldData vcustomFieldData;
		for (CustomFieldData customFieldData : customFields) {
			vcustomFieldData = new VcustomFieldData();
			if (!Utils.isBlankOrNull(customFieldData.getFieldName())) {
				vcustomFieldData.setFieldName(customFieldData.getFieldName());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldDisplayName())) {
				vcustomFieldData.setFieldDisplayName(customFieldData.getFieldDisplayName());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldAttributes())) {
				vcustomFieldData.setFieldAttributes(customFieldData.getFieldAttributes());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldOtherAttributes())) {
				vcustomFieldData.setFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldId())) {
				vcustomFieldData.setFieldId(customFieldData.getFieldId());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldOptions())) {
				vcustomFieldData.setFieldOptions(customFieldData.getFieldOptions());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldType())) {
				vcustomFieldData.setFieldType(customFieldData.getFieldType());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldDefaultValue())) {
				vcustomFieldData.setFieldDefaultValue(customFieldData.getFieldDefaultValue());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldStringValue())) {
				vcustomFieldData.setFieldStringValue(customFieldData.getFieldStringValue());
			}
			if (customFieldData.getFieldDateValue() != null) {
				vcustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
			}
			
			vcustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
			vcustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
			vcustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
			vcustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
			vcustomFieldData.setFieldRank(customFieldData.getFieldRank());
			vcustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
			vcustomFields.add(vcustomFieldData);
		}
		
		return vcustomFields;
	}

	/**
	 * @return VinboxData
	 */
	public VinboxData getInboxData() {
		VinboxData vinboxData = new VinboxData();
		try {
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			if (inboxData != null) {
				if (!Utils.isBlankOrNull(inboxData.getInboxEmail())) {
					vinboxData.setInboxEmail(inboxData.getInboxEmail());
				}
				if (!Utils.isBlankOrNull(inboxData.getUserName())) {
					vinboxData.setUserName(inboxData.getUserName());
				}
				if (!Utils.isBlankOrNull(inboxData.getPassword())) {
					vinboxData.setPassword(inboxData.getPassword());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxOutgoingPort())) {
					vinboxData.setInboxOutgoingPort(inboxData.getInboxOutgoingPort());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxSmtpPassword())) {
					vinboxData.setInboxSmtpPassword(inboxData.getInboxSmtpPassword());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxSmtpUserName())) {
					vinboxData.setInboxSmtpUserName(inboxData.getInboxSmtpUserName());
				}
				if (!Utils.isBlankOrNull(inboxData.getSmtpHost())) {
					vinboxData.setSmtpHost(inboxData.getSmtpHost());
				}

				vinboxData.setInboxOutgoingSSLEnabled(inboxData.getInboxOutgoingSSLEnabled());
				vinboxData.setInboxOutgoingTLSEnabled(inboxData.getInboxOutgoingTLSEnabled());
				vinboxData.setInboxSmtpAuthRequired(inboxData.getInboxSmtpAuthRequired());
				vinboxData.setInboxSmtpAuthSame(inboxData.getInboxSmtpAuthSame());
				
				vinboxData.setInboxServerType(""+inboxData.getInboxServerType());
				
				vinboxData.setDomainName(inboxData.getDomainName());
				vinboxData.setExchangeServerName(inboxData.getExchangeServerName());
				vinboxData.setExchangeServerVersion(inboxData.getExchangeServerVersion());
				vinboxData.setExchangeSmtp(inboxData.getExchangeSmtp());

			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return vinboxData;
	}

	public VimportFieldList getImportFieldData() {
		VimportFieldList vimportFieldList = new VimportFieldList();
		try {
			ArrayList<ImportFieldData> importFieldDataList = ImportConfigurationManager.getImportFields();
			ArrayList<VimportFieldData> vimportFieldDataList = new ArrayList<VimportFieldData>();
			for (int i = 0; i < importFieldDataList.size(); i++) {
				ImportFieldData importFieldData =  importFieldDataList.get(i);
				if (importFieldData != null) {
					VimportFieldData vimportFieldData = new VimportFieldData();
					vimportFieldData.setFieldId(importFieldData.getFieldId());
					vimportFieldData.setFieldTitle(importFieldData.getFieldTitle());
					vimportFieldData.setFieldType(importFieldData.getFieldType());
					vimportFieldData.setFieldVendorShow(importFieldData.getFieldVendorShow());
					vimportFieldData.setFieldVendorMandatory(importFieldData.getFieldVendorMandatory());
					vimportFieldDataList.add(vimportFieldData);
				}
			}
			vimportFieldList.setImportFieldList(vimportFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return vimportFieldList;
	}

}
