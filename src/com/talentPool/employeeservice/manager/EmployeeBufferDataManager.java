/**
 * 
 */
package com.talentPool.employeeservice.manager;

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
import com.talentPool.employeeservice.dataobject.EcustomFieldData;
import com.talentPool.employeeservice.dataobject.EcustomFieldDataList;
import com.talentPool.employeeservice.dataobject.EidsNames;
import com.talentPool.employeeservice.dataobject.EimportFieldData;
import com.talentPool.employeeservice.dataobject.EimportFieldList;
import com.talentPool.employeeservice.dataobject.EinboxData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.vendorservice.VendorServiceConstants;

/**
 * @author Shantanu
 *
 */
public class EmployeeBufferDataManager {
	/**
	 * @param type
	 * @return getIdNamesData
	 */
	public EidsNames getIdNamesData(String type) {
		EidsNames eidsNames = new EidsNames();
		try {
			if (type.equals(EmployeeServiceConstants.GET_BRANCHES)) {
				eidsNames.setIds(CommonUtils.getBranchIds());
				eidsNames.setNames(CommonUtils.getBranchNames());
			} else if (type.equals(EmployeeServiceConstants.GET_DEGREES)) {
				eidsNames.setIds(CommonUtils.getDegreeIds());
				eidsNames.setNames(CommonUtils.getDegreeNames());
			} else if (type.equals(EmployeeServiceConstants.GET_SKILLS)) {
				eidsNames.setIds(CommonUtils.getSkillIds());
				eidsNames.setNames(CommonUtils.getSkillNames());
			} else if (type.equals(EmployeeServiceConstants.GET_RESUME_TYPES)) {
				eidsNames.setIds(CommonUtils.getResumeTypeIds());
				eidsNames.setNames(CommonUtils.getResumeTypeNames());
			} else if (type.equals(VendorServiceConstants.GET_SECURITY_QUESTIONS)) {
				eidsNames.setIds(CommonUtils.getSecurityQuestionIds());
				eidsNames.setNames(CommonUtils.getSecurityQuestionNames());
			} else if (type.equals(EmployeeServiceConstants.GET_TIME_ZONE_TYPES)){
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
				eidsNames.setIds(zoneIds);
				eidsNames.setNames(zoneValues);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in employee service", e);
		}
		return eidsNames;
	}

	/**
	 * @return VcustomFieldDataList
	 */
	public EcustomFieldDataList getCustomFieldList() {
		EcustomFieldDataList ecustomFieldDataList = new EcustomFieldDataList();
		try {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, false);
			ArrayList<CustomFieldData> tabCustomFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
			customFields.addAll(tabCustomFields);
			ArrayList<EcustomFieldData> ecustomFields = new ArrayList<EcustomFieldData>();
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData customFieldData = customFields.get(i);
				EcustomFieldData ecustomFieldData = new EcustomFieldData();
				if (!Utils.isBlankOrNull(customFieldData.getFieldName())) {
					ecustomFieldData.setFieldName(customFieldData.getFieldName());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldDisplayName())) {
					ecustomFieldData.setFieldDisplayName(customFieldData.getFieldDisplayName());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldAttributes())) {
					ecustomFieldData.setFieldAttributes(customFieldData.getFieldAttributes());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldOtherAttributes())) {
					ecustomFieldData.setFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldId())) {
					ecustomFieldData.setFieldId(customFieldData.getFieldId());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldOptions())) {
					ecustomFieldData.setFieldOptions(customFieldData.getFieldOptions());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldType())) {
					ecustomFieldData.setFieldType(customFieldData.getFieldType());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldDefaultValue())) {
					ecustomFieldData.setFieldDefaultValue(customFieldData.getFieldDefaultValue());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldStringValue())) {
					ecustomFieldData.setFieldStringValue(customFieldData.getFieldStringValue());
				}
				ecustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
				if (customFieldData.getFieldDateValue() != null) {
					ecustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
				}
				ecustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
				ecustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
				ecustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
				ecustomFieldData.setFieldRank(customFieldData.getFieldRank());
				ecustomFieldData.setTableId(customFieldData.getTableId());
				ecustomFieldData.setTableName(customFieldData.getTableName());
				ecustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());				
				ecustomFields.add(ecustomFieldData);
			}		
			ecustomFieldDataList.setCustomFields(ecustomFields);
			

		} catch (Exception e) {
			TPLogger.getLogger().error("Error in employee service", e);
		}
		
		return ecustomFieldDataList;
	}

	/**
	 * @return EinboxData
	 */
	public EinboxData getInboxData() {
		EinboxData einboxData = new EinboxData();
		try {
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			if (inboxData != null) {
				if (!Utils.isBlankOrNull(inboxData.getInboxEmail())) {
					einboxData.setInboxEmail(inboxData.getInboxEmail());
				}
				if (!Utils.isBlankOrNull(inboxData.getUserName())) {
					einboxData.setUserName(inboxData.getUserName());
				}
				if (!Utils.isBlankOrNull(inboxData.getPassword())) {
					einboxData.setPassword(inboxData.getPassword());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxOutgoingPort())) {
					einboxData.setInboxOutgoingPort(inboxData.getInboxOutgoingPort());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxSmtpPassword())) {
					einboxData.setInboxSmtpPassword(inboxData.getInboxSmtpPassword());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxSmtpUserName())) {
					einboxData.setInboxSmtpUserName(inboxData.getInboxSmtpUserName());
				}
				if (!Utils.isBlankOrNull(inboxData.getSmtpHost())) {
					einboxData.setSmtpHost(inboxData.getSmtpHost());
				}

				einboxData.setInboxOutgoingSSLEnabled(inboxData.getInboxOutgoingSSLEnabled());
				einboxData.setInboxOutgoingTLSEnabled(inboxData.getInboxOutgoingTLSEnabled());
				einboxData.setInboxSmtpAuthRequired(inboxData.getInboxSmtpAuthRequired());
				einboxData.setInboxSmtpAuthSame(inboxData.getInboxSmtpAuthSame());
				
				einboxData.setInboxServerType(""+inboxData.getInboxServerType());
				
				einboxData.setDomainName(inboxData.getDomainName());
				einboxData.setExchangeServerName(inboxData.getExchangeServerName());
				einboxData.setExchangeServerVersion(inboxData.getExchangeServerVersion());
				einboxData.setExchangeSmtp(inboxData.getExchangeSmtp());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in employee service", e);
		}
		return einboxData;
	}
	
	//
	public EimportFieldList getImportFieldData() {
		EimportFieldList eimportFieldList = new EimportFieldList();
		try {
			ArrayList<ImportFieldData> importFieldDataList = ImportConfigurationManager.getImportFields();
			ArrayList<EimportFieldData> eimportFieldDataList = new ArrayList<EimportFieldData>();
			for (int i = 0; i < importFieldDataList.size(); i++) {
				ImportFieldData importFieldData =  importFieldDataList.get(i);
				if (importFieldData != null) {
					EimportFieldData eimportFieldData = new EimportFieldData();
					eimportFieldData.setFieldId(importFieldData.getFieldId());
					eimportFieldData.setFieldTitle(importFieldData.getFieldTitle());
					eimportFieldData.setFieldType(importFieldData.getFieldType());
					eimportFieldData.setFieldEmployeeShow(importFieldData.getFieldEmployeeShow());
					eimportFieldData.setFieldEmployeeMandatory(importFieldData.getFieldEmployeeMandatory());					
					eimportFieldDataList.add(eimportFieldData);
				}
			}
			eimportFieldList.setImportFieldList(eimportFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in Employee service", e);
		}
		
		return eimportFieldList;
	}
}
