/**
 * 
 */
package com.talentPool.websiteservice.manager;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.admin.manager.WebsiteScreenSettingsManager;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ImportFieldData;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.dataobject.CustomFieldOption;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.dataobject.PositionFieldData;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.websiteservice.WebsiteServiceConstants;
import com.talentPool.websiteservice.dataobject.DynamicFieldDTO;
import com.talentPool.websiteservice.dataobject.FieldTypeEnum;
import com.talentPool.websiteservice.dataobject.WcustomFieldData;
import com.talentPool.websiteservice.dataobject.WcustomFieldDataList;
import com.talentPool.websiteservice.dataobject.WebsiteSettingsData;
import com.talentPool.websiteservice.dataobject.WidsNames;
import com.talentPool.websiteservice.dataobject.WimportFieldData;
import com.talentPool.websiteservice.dataobject.WimportFieldList;
import com.talentPool.websiteservice.dataobject.WinboxData;
import com.talentPool.websiteservice.dataobject.WitemData;
import com.talentPool.websiteservice.dataobject.WpositionFieldData;
import com.talentPool.websiteservice.dataobject.WpositionFieldList;
import com.talentPool.websiteservice.utils.WebsiteMarshaller;

/**
 * @author shivprasad
 * 
 */
public class WebsiteBufferDataManager {
	/**
	 * @param type
	 * @return getIdNamesData
	 */
	public WidsNames getIdNamesData(String type) {
		WidsNames widsNames = new WidsNames();
		try {
			if (type.equals(WebsiteServiceConstants.GET_BRANCHES)) {
				widsNames.setIds(CommonUtils.getBranchIds());
				widsNames.setNames(CommonUtils.getBranchNames());
			} else if (type.equals(WebsiteServiceConstants.GET_DEGREES)) {
				widsNames.setIds(CommonUtils.getDegreeIds());
				widsNames.setNames(CommonUtils.getDegreeNames());
			} else if (type.equals(WebsiteServiceConstants.GET_SKILLS)) {
				widsNames.setIds(CommonUtils.getSkillIds());
				widsNames.setNames(CommonUtils.getSkillNames());
			}else if (type.equals(WebsiteServiceConstants.GET_LOCATIONS)) {
				widsNames.setIds(CommonUtils.getLocationIds());
				widsNames.setNames(CommonUtils.getLocationNames());
			}else if (type.equals(WebsiteServiceConstants.GET_DEPARTMENTS)) {
				widsNames.setIds(CommonUtils.getDeptIds());
				widsNames.setNames(CommonUtils.getDeptNames());
			} else if (type.equals(WebsiteServiceConstants.GET_RESUME_TYPES)) {
				widsNames.setIds(CommonUtils.getResumeTypeIds());
				widsNames.setNames(CommonUtils.getResumeTypeNames());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return widsNames;
	}

	public WitemData getItem(String type) {
		WitemData witemData = new WitemData();
		try {
			if (type.equals(WebsiteServiceConstants.GET_SOURCE)) {
				AdminManager adminManager = new AdminManager();
				SourceData sourceData = adminManager.getCompanyWebSiteSource();
				witemData.setItemId(sourceData.getSourceId());
				witemData.setItemName(sourceData.getSourceTitle());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return witemData;
	}
	
	/**
	 * @return VcustomFieldDataList
	 */
	public WcustomFieldDataList getCustomFieldList() {
		WcustomFieldDataList wcustomFieldDataList = new WcustomFieldDataList();
		try {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			ArrayList<WcustomFieldData> wcustomFields = new ArrayList<WcustomFieldData>();
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData customFieldData = customFields.get(i);
				WcustomFieldData wcustomFieldData = new WcustomFieldData();
				if (!Utils.isBlankOrNull(customFieldData.getFieldName())) {
					wcustomFieldData.setFieldName(customFieldData.getFieldName());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldDisplayName())) {
					wcustomFieldData.setFieldDisplayName(customFieldData.getFieldDisplayName());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldAttributes())) {
					wcustomFieldData.setFieldAttributes(customFieldData.getFieldAttributes());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldOtherAttributes())) {
					wcustomFieldData.setFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldId())) {
					wcustomFieldData.setFieldId(customFieldData.getFieldId());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldOptions())) {
					wcustomFieldData.setFieldOptions(customFieldData.getFieldOptions());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldType())) {
					wcustomFieldData.setFieldType(customFieldData.getFieldType());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldDefaultValue())) {
					wcustomFieldData.setFieldDefaultValue(customFieldData.getFieldDefaultValue());
				}
				if (!Utils.isBlankOrNull(customFieldData.getFieldStringValue())) {
					wcustomFieldData.setFieldStringValue(customFieldData.getFieldStringValue());
				}
				if (customFieldData.getFieldDateValue() != null) {
					wcustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
				}
				
				wcustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
				wcustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
				wcustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
				wcustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
				wcustomFieldData.setFieldRank(customFieldData.getFieldRank());
				wcustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
				wcustomFields.add(wcustomFieldData);

			}
			wcustomFieldDataList.setCustomFields(wcustomFields);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return wcustomFieldDataList;
	}

	public WimportFieldList getImportFieldData() {
		WimportFieldList wimportFieldList = new WimportFieldList();
		try {
			WebsiteApplicantManager wAppManager = new WebsiteApplicantManager();
			ArrayList<ImportFieldData> importFieldDataList = wAppManager.getAllApplicantFieldsForCandidatePortal();
			ArrayList<WimportFieldData> vimportFieldDataList = new ArrayList<WimportFieldData>();
			for (int i = 0; i < importFieldDataList.size(); i++) {
				ImportFieldData importFieldData =  importFieldDataList.get(i);
				if (importFieldData != null) {
					DynamicFieldDTO wimportFieldData = new DynamicFieldDTO();
					wimportFieldData.setFieldId(importFieldData.getFieldId());
					wimportFieldData.setFieldTitle(importFieldData.getFieldTitle());
					wimportFieldData.setFieldType(importFieldData.getFieldType());
					if(importFieldData.getFieldWebsiteShow().equals("0")){
						continue;
					}
					wimportFieldData.setFieldWebsiteShow(importFieldData.getFieldWebsiteShow());
					wimportFieldData.setFieldWebsiteMandatory(importFieldData.getFieldWebsiteMandatory());
					wimportFieldData.setDisplayName(importFieldData.getDisplayName());
					wimportFieldData.setRank(i);
					wimportFieldData.setRequired("1".equals(importFieldData.getFieldWebsiteMandatory()));
					wimportFieldData.setId(Utils.isBlankOrNull(importFieldData.getId())?0:Integer.parseInt(importFieldData.getId()));
					wimportFieldData.setParentId(Utils.isBlankOrNull(importFieldData.getParentId())?0:Integer.parseInt(importFieldData.getParentId()));
					
					if(ImportConfigurationConstants.FIELD_TYPE_CUSTOM.equals(importFieldData.getFieldType())){
						FieldTypeEnum type = FieldTypeEnum.valueOf(importFieldData.getType().toUpperCase());
						wimportFieldData.setType(type);
						CustomFieldData cData = new CustomFieldManager().getCustomFieldByName(importFieldData.getFieldId(),true);
						ArrayList<CustomFieldOption> options = cData.getOptionsList(cData.getFieldOptions());
						ArrayList<String> optionList = new ArrayList<String>();
						for(CustomFieldOption option: options){
							optionList.add(option.getValue());
						}
						if(optionList.size()>0){
							if(wimportFieldData.getType().toString().toLowerCase().contains("dropdown")){
								wimportFieldData.setType(FieldTypeEnum.TEXT_DROPDOWN);
							}
						}
						wimportFieldData.setDefaultValue(cData.getFieldDefaultValue());
						wimportFieldData.setOptionList(optionList);
						wimportFieldData.setPropertyName(cData.getFieldName());
					}else if(ImportConfigurationConstants.FIELD_TYPE_CUSTOM_TABLE.equals(importFieldData.getFieldType())){
						CustomFieldManager cManager  = new CustomFieldManager();
						String tableId = cManager.getTableIdFromTableName(importFieldData.getFieldId());
						ArrayList<CustomFieldData> cTableFields = cManager.getCustomFieldsForTableType(""+CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD,tableId);
						List<List<DynamicFieldDTO>> list = new ArrayList<List<DynamicFieldDTO>>();
						List<DynamicFieldDTO> subList = new ArrayList<DynamicFieldDTO>();
						list.add(subList);
						wimportFieldData.setDynamicFieldDTO(list);
						wimportFieldData.setId(Integer.parseInt(tableId));
						wimportFieldData.setType(FieldTypeEnum.TABLE_GROUP);
						wimportFieldData.setDisplayName(importFieldData.getFieldId());
						for(CustomFieldData cData: cTableFields){
							DynamicFieldDTO wimportFieldData1 = new DynamicFieldDTO();
							wimportFieldData1.setFieldId(cData.getFieldId());
							wimportFieldData1.setFieldTitle(cData.getFieldDisplayName());
							wimportFieldData1.setFieldType(ImportConfigurationConstants.FIELD_TYPE_CUSTOM_TABLE_FIELD);
							wimportFieldData1.setFieldWebsiteShow("1");
							wimportFieldData1.setFieldWebsiteMandatory("0");
							wimportFieldData1.setDisplayName(cData.getFieldDisplayName());
							wimportFieldData1.setRequired("1".equals(importFieldData.getFieldWebsiteMandatory()));
							wimportFieldData1.setParentId(Integer.parseInt(tableId));
							FieldTypeEnum type = FieldTypeEnum.valueOf(cData.getFieldType().toUpperCase());
							wimportFieldData1.setType(type);
							ArrayList<CustomFieldOption> options = cData.getOptionsList(cData.getFieldOptions());
							ArrayList<String> optionList = new ArrayList<String>();
							for(CustomFieldOption option: options){
								optionList.add(option.getValue());
							}
							wimportFieldData1.setDefaultValue(cData.getFieldDefaultValue());
							wimportFieldData1.setOptionList(optionList);
							wimportFieldData1.setPropertyName(cData.getFieldName());
							subList.add(wimportFieldData1);
						}
					}else{
						wimportFieldData.setType(FieldTypeEnum.values()[Integer.parseInt(Utils.isBlankOrNull(importFieldData.getType())?"0":importFieldData.getType())]);
						wimportFieldData.setRegex(importFieldData.getRegex());
						wimportFieldData.setRegexMessage(importFieldData.getRegexMessage());
						wimportFieldData.setCssClassName(importFieldData.getCSSClassName());
						wimportFieldData.setApiURL(importFieldData.getApiUrl());
						wimportFieldData.setDefaultValue(importFieldData.getDefaultValue());
						wimportFieldData.setOptionList(Utils.isBlankOrNull(importFieldData.getOptionList())?null:Arrays.asList(importFieldData.getOptionList().split(",")));
						wimportFieldData.setPropertyName(importFieldData.getPropertyName());
						wimportFieldData.setWatermarkText(importFieldData.getWaterMarkPlaceHolder());
					}
					
					vimportFieldDataList.add(wimportFieldData);
				}
			}
			wimportFieldList.setImportFieldList(vimportFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in website service", e);
		}
		return wimportFieldList;
	}

	public WinboxData getInboxData() {
		WinboxData winboxData = new WinboxData();
		try {
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			if (inboxData != null) {
				if (!Utils.isBlankOrNull(inboxData.getInboxEmail())) {
					winboxData.setInboxEmail(inboxData.getInboxEmail());
				}
				if (!Utils.isBlankOrNull(inboxData.getUserName())) {
					winboxData.setUserName(inboxData.getUserName());
				}
				if (!Utils.isBlankOrNull(inboxData.getPassword())) {
					winboxData.setPassword(inboxData.getPassword());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxOutgoingPort())) {
					winboxData.setInboxOutgoingPort(inboxData.getInboxOutgoingPort());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxSmtpPassword())) {
					winboxData.setInboxSmtpPassword(inboxData.getInboxSmtpPassword());
				}
				if (!Utils.isBlankOrNull(inboxData.getInboxSmtpUserName())) {
					winboxData.setInboxSmtpUserName(inboxData.getInboxSmtpUserName());
				}
				if (!Utils.isBlankOrNull(inboxData.getSmtpHost())) {
					winboxData.setSmtpHost(inboxData.getSmtpHost());
				}

				winboxData.setInboxOutgoingSSLEnabled(inboxData.getInboxOutgoingSSLEnabled());
				winboxData.setInboxOutgoingTLSEnabled(inboxData.getInboxOutgoingTLSEnabled());
				winboxData.setInboxSmtpAuthRequired(inboxData.getInboxSmtpAuthRequired());
				winboxData.setInboxSmtpAuthSame(inboxData.getInboxSmtpAuthSame());
				
				winboxData.setInboxServerType(""+inboxData.getInboxServerType());
				
				winboxData.setDomainName(inboxData.getDomainName());
				winboxData.setExchangeServerName(inboxData.getExchangeServerName());
				winboxData.setExchangeServerVersion(inboxData.getExchangeServerVersion());
				winboxData.setExchangeSmtp(inboxData.getExchangeSmtp());
				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in website service", e);
		}
		return winboxData;
	}
	
	public WpositionFieldList getPositionFieldData() {
		WpositionFieldList wpositionFieldList = new WpositionFieldList();
		try {
			ArrayList<PositionFieldData> positionFieldDataList = PositionScreenConfigurationManager.getPositionDetailsFields();
			ArrayList<WpositionFieldData> vpositionFieldDataList = new ArrayList<WpositionFieldData>();
			for (int i = 0; i < positionFieldDataList.size(); i++) {
				PositionFieldData positionFieldData =  positionFieldDataList.get(i);
				if (positionFieldData != null) {
					WpositionFieldData wpositionFieldData = new WpositionFieldData();
					wpositionFieldData.setFieldId(positionFieldData.getFieldId());
					wpositionFieldData.setFieldTitle(positionFieldData.getFieldTitle());
					wpositionFieldData.setFieldType(positionFieldData.getFieldType());
					wpositionFieldData.setFieldWebsitePositionListShow(positionFieldData.getFieldOnPositionListShow());
					wpositionFieldData.setFieldWebsitePositionDetailsShow(positionFieldData.getFieldOnPositionDetailsShow());
					vpositionFieldDataList.add(wpositionFieldData);
				}
			}
			wpositionFieldList.setPositionFieldList(vpositionFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting position field service", e);
		}
		return wpositionFieldList;
	}
	
	public WpositionFieldList getFieldsDataOnPositionList() {
		WpositionFieldList wpositionFieldList = new WpositionFieldList();
		try {
			ArrayList<PositionFieldData> positionFieldDataList = PositionScreenConfigurationManager.getPositionListFields();
			ArrayList<WpositionFieldData> vpositionFieldDataList = new ArrayList<WpositionFieldData>();
			for (int i = 0; i < positionFieldDataList.size(); i++) {
				PositionFieldData positionFieldData =  positionFieldDataList.get(i);
				if (positionFieldData != null) {
					WpositionFieldData wpositionFieldData = new WpositionFieldData();
					wpositionFieldData.setFieldId(positionFieldData.getFieldId());
					wpositionFieldData.setFieldTitle(positionFieldData.getFieldTitle());
					wpositionFieldData.setFieldType(positionFieldData.getFieldType());
					wpositionFieldData.setFieldWebsitePositionListShow(positionFieldData.getFieldOnPositionListShow());
					wpositionFieldData.setFieldWebsitePositionIsFilter(positionFieldData.getFieldIsFilter());
					vpositionFieldDataList.add(wpositionFieldData);
				}
			}
			wpositionFieldList.setPositionFieldList(vpositionFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting position field service", e);
		}
		return wpositionFieldList;
	}
	
	public WebsiteSettingsData getWebsiteSettings() {
		WebsiteSettingsData websiteSettingsData = new WebsiteSettingsData();
		WebsiteScreenSettingsManager wScreenManager = null;
		String filtersHeaderText = null;
		try {
			wScreenManager = new WebsiteScreenSettingsManager();
			int isFiltersAvailableOnWebsite = wScreenManager.isFiltersAvailableOnWebsite();
			if(isFiltersAvailableOnWebsite>0){
				filtersHeaderText = wScreenManager.getWebsitePositionsHomeHeader();
				websiteSettingsData.setFiltersHeaderText(filtersHeaderText);
				websiteSettingsData.setFiltersAvailable(PositionConfigurationConstants.FILTERS_AVAILABLE);
			}else { 
				websiteSettingsData.setFiltersAvailable(PositionConfigurationConstants.FILTERS_NOT_AVAILABLE);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting position field service", e);
		}
		return websiteSettingsData;
	}
	
	public WimportFieldList getImportFieldDataForWebsite() {
		WimportFieldList wimportFieldList = new WimportFieldList();
		try {
			ArrayList<ImportFieldData> importFieldDataList = ImportConfigurationManager.getImportFields();
			ArrayList<WimportFieldData> vimportFieldDataList = new ArrayList<WimportFieldData>();
			for (int i = 0; i < importFieldDataList.size(); i++) {
				ImportFieldData importFieldData =  importFieldDataList.get(i);
				if (importFieldData != null) {
					WimportFieldData wimportFieldData = new WimportFieldData();
					wimportFieldData.setFieldId(importFieldData.getFieldId());
					wimportFieldData.setFieldTitle(importFieldData.getFieldTitle());
					wimportFieldData.setFieldType(importFieldData.getFieldType());
					wimportFieldData.setFieldWebsiteShow(importFieldData.getFieldWebsiteShow());
					wimportFieldData.setFieldWebsiteMandatory(importFieldData.getFieldWebsiteMandatory());
					vimportFieldDataList.add(wimportFieldData);
				}
			}
			wimportFieldList.setImportFieldList(vimportFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in vendor service", e);
		}
		return wimportFieldList;
	}
	
}
