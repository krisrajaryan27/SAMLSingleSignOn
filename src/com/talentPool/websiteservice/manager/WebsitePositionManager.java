/**
 * 
 */
package com.talentPool.websiteservice.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.talentPool.admin.manager.WebsiteScreenSettingsManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.constants.PositionConfigurationConstants;
import com.talentPool.positions.dataobject.PositionFieldData;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.socialNetwork.constants.SocialMediaConstants;
import com.talentPool.websiteservice.dataobject.WcustomFieldData;
import com.talentPool.websiteservice.dataobject.WpositionData;
import com.talentPool.websiteservice.dataobject.WpositionFilters;
import com.talentPool.websiteservice.dataobject.WpositionList;
import com.talentPool.websiteservice.dataobject.WsocialMediaSource;
import com.talentPool.websiteservice.dataobject.WsocialMediaSourceList;

/**
 * @author pallavi
 *
 */
public class WebsitePositionManager {
	
	public WpositionData getPositionDetails(String positionId) {
		WpositionData data = null;
		try {
			SimpleDataObject sDo = getPositionDetailsForWebsite(positionId);
			data = constructWPositionData(sDo);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the position details", e);
		}
		return data;
	}
	
	public WpositionData constructWPositionData(SimpleDataObject sDo) {		
		WpositionData data = new WpositionData();
		WebsiteScreenSettingsManager websiteScreenManager = new WebsiteScreenSettingsManager();
		data.setPositionId(sDo.getString("positionId"));
		data.setPositionCode(sDo.getString("positionCode"));
		data.setPositionTitle(sDo.getString("positionTitle"));
		data.setPrimarySkills(sDo.getString("primarySkills"));
		data.setSecondarySkills(sDo.getString("secondarySkills"));
		data.setExperience(sDo.getString("experience"));
		data.setEducation(sDo.getString("education"));
		data.setNoOfUnfilledVacancies(sDo.getString("noOfUnfilledVacancies"));
		data.setLocation(sDo.getString("location"));
		data.setPositionCreationDate(sDo.getString("positionCreationDate"));
		data.setDepartmentName(sDo.getString("departmentName"));
		data.setSubDeptName(sDo.getString("subDeptName"));
		data.setSub3DeptName(sDo.getString("sub3DeptName"));
		data.setSub4DeptName(sDo.getString("sub4DeptName"));
		data.setGroupName(sDo.getString("groupName"));
		data.setVacancies(sDo.getString("vacancies"));
		data.setHireByDate(sDo.getString("hireByDate"));
		data.setPositionLevel(sDo.getString("positionLevel"));
		data.setReferalFees(sDo.getString("referalFees"));
		data.setPositionNote(sDo.getString("positionNote"));
		data.setResponsibilities(sDo.getString("responsibilities"));
		data.setRequirements(sDo.getString("requirements"));
		data.setBranch(sDo.getString("branch"));
		data.setPositionPublishedDate(sDo.getString("positionPublishedDate"));

		Object custObj = sDo.getAttribute("customFields");
		ArrayList<CustomFieldData> customFields = (ArrayList<CustomFieldData>)sDo.getAttribute("customFields");
		
		ArrayList<WcustomFieldData> wCustomFieldsData = new ArrayList<WcustomFieldData>();
		wCustomFieldsData = constructWCustomFieldData(customFields);
		
		data.setCustomFields(wCustomFieldsData);

		//set first field to display and list of comma separated attributes
		SimpleDataObject sdo = websiteScreenManager.getWebsiteSettings();
		String firstFieldId = (String)sdo.getAttribute("fisrtFieldName");
		String isOther = (String)sdo.getAttribute("isOther");
		String isShowLabels = (String)sdo.getAttribute("isShowLabels");
		populateFirstFieldForWebsiteListPage(firstFieldId,isOther,data); 
		populatePositionAttributesForWebsiteListPage(data,isShowLabels);
		return data;
	}
	
	private void populateFirstFieldForWebsiteListPage(String firstFieldId,String isOther, WpositionData data){
		if(isOther.equalsIgnoreCase(PositionConfigurationConstants.OTHER_SELECTED)){
			data.setFirstFieldToDisplay(firstFieldId);
		}else{
			if(firstFieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_NAME)){
				data.setFirstFieldToDisplay(data.getPositionTitle());
			}else if(firstFieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CODE)){
				data.setFirstFieldToDisplay(data.getPositionCode());
			}
		}
	}
	
	private void populatePositionAttributesForWebsiteListPage(WpositionData data,String isShowLabels){
		StringBuffer sb = new StringBuffer();
		ArrayList<PositionFieldData> fieldList = PositionScreenConfigurationManager.getPositionListFields();
		int count = 0;
		int count1 = 0;
		CustomFieldManager customFieldManager = new CustomFieldManager();
		//ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsFor(CustomFieldConstants.ENTITY_TYPE_POSITION, false);
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(data.getPositionId(), CustomFieldConstants.ENTITY_TYPE_POSITION);
		if(fieldList!=null && fieldList.size()>0){
			//for loop for checking the list count
			for (int i = 0; i < fieldList.size(); i++) {
				PositionFieldData fieldData = fieldList.get(i);
				String isShowOnList = fieldData.getFieldOnPositionListShow();
				String fieldId = fieldData.getFieldId();
				String fieldType = fieldData.getFieldType();
				if(isShowOnList.equalsIgnoreCase(PositionConfigurationConstants.FIELD_SHOW)){
					if (fieldType.equalsIgnoreCase(PositionConfigurationConstants.FIELD_TYPE_CUSTOM)) {
						for (int j = 0; j < customFields.size(); j++) {
							CustomFieldData custData = (CustomFieldData) customFields.get(j);
							String customFieldId = custData.getFieldName();
							if (customFieldId.equalsIgnoreCase(fieldId)) {
								if(!Utils.isBlankOrNull(custData.getDisplayValue())){
									count++;
								}
								break;
							}
						}
					} else {// if normal field
						if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_NAME)) {
							if(!Utils.isBlankOrNull(data.getPositionTitle())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CODE)) {
							if(!Utils.isBlankOrNull(data.getPositionCode())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CREATED_ON)) {
							if(!Utils.isBlankOrNull(data.getPositionCreationDate())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LOCATION)) {
							if(!Utils.isBlankOrNull(data.getLocation())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_1)) {
							if(!Utils.isBlankOrNull(data.getDepartmentName())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_2)) {
							if(!Utils.isBlankOrNull(data.getSubDeptName())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_3)) {
							if(!Utils.isBlankOrNull(data.getGroupName())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_4)) {
							if(!Utils.isBlankOrNull(data.getSub3DeptName())){
								count++;
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5)) {
							if(!Utils.isBlankOrNull(data.getSub4DeptName())){
								count++;
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_VACANCIES)) {
							if(!Utils.isBlankOrNull(data.getVacancies())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_HIRE_BY_DATE)) {
							if(!Utils.isBlankOrNull(data.getHireByDate())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LEVEL)) {
							if(!Utils.isBlankOrNull(data.getPositionLevel())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REFERAL_FEES)) {
							if(!Utils.isBlankOrNull(data.getReferalFees())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_RESPONSIBILITIES)) {
							if(!Utils.isBlankOrNull(data.getResponsibilities())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REQUIREMENTS)) {
							if(!Utils.isBlankOrNull(data.getRequirements())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)) {
							if(!Utils.isBlankOrNull(data.getPrimarySkills())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_SECONDARY_SKILLS)) {
							if(!Utils.isBlankOrNull(data.getSecondarySkills())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_EDUCATION)) {
							if(!Utils.isBlankOrNull(data.getEducation())){
								count++;
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_BRANCH)) {
							if(!Utils.isBlankOrNull(data.getBranch())){
								count++;
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_EXPERIENCE)) {
							if(!Utils.isBlankOrNull(data.getExperience())){
								count++;
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_NOTE)) {
							if(!Utils.isBlankOrNull(data.getPositionNote())){
								count++;
							}
						}
					}
				}
			}
			
			//start creating comma separated list to display on website home page. 
			for (int i = 0; i < fieldList.size(); i++) {
				PositionFieldData fieldData = fieldList.get(i);
				String fieldId = fieldData.getFieldId();
				String fieldType = fieldData.getFieldType();
				String fieldTitle = fieldData.getFieldTitle();
				String isShowOnList = fieldData.getFieldOnPositionListShow(); 
				
				if(isShowOnList.equalsIgnoreCase(PositionConfigurationConstants.FIELD_SHOW)){
					
					if (fieldType.equalsIgnoreCase(PositionConfigurationConstants.FIELD_TYPE_CUSTOM)) {
						for (int j = 0; j < customFields.size(); j++) {
							CustomFieldData custData = (CustomFieldData) customFields.get(j);
							String customFieldId = custData.getFieldName();
							if (customFieldId.equalsIgnoreCase(fieldId)) {
								if(!Utils.isBlankOrNull(custData.getDisplayValue())){
									count1++;
									if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
										sb.append(fieldTitle+": ");
									}
									sb.append(custData.getDisplayValue());
									if(count1<count){
										sb.append(", ");
									}
								}
								break;
							}
						}
					} else {// if normal field
						if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_NAME)) {
							if(!Utils.isBlankOrNull(data.getPositionTitle())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getPositionTitle());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CODE)) {
							if(!Utils.isBlankOrNull(data.getPositionCode())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getPositionCode());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_CREATED_ON)) {
							if(!Utils.isBlankOrNull(data.getPositionCreationDate())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getPositionCreationDate());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LOCATION)) {
							if(!Utils.isBlankOrNull(data.getLocation())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getLocation());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_1)) {
							if(!Utils.isBlankOrNull(data.getDepartmentName())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getDepartmentName());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_2)) {
							if(!Utils.isBlankOrNull(data.getSubDeptName())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getSubDeptName());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_3)) {
							if(!Utils.isBlankOrNull(data.getGroupName())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getGroupName());
								if(count1<count){
									sb.append(", ");
								}
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_4)) {
							if(!Utils.isBlankOrNull(data.getSub3DeptName())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getSub3DeptName());
								if(count1<count){
									sb.append(", ");
								}
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5)) {
							if(!Utils.isBlankOrNull(data.getSub4DeptName())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getSub4DeptName());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_VACANCIES)) {
							if(!Utils.isBlankOrNull(data.getVacancies())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getVacancies());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_HIRE_BY_DATE)) {
							if(!Utils.isBlankOrNull(data.getHireByDate())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getHireByDate());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_LEVEL)) {
							if(!Utils.isBlankOrNull(data.getPositionLevel())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getPositionLevel());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REFERAL_FEES)) {
							if(!Utils.isBlankOrNull(data.getReferalFees())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getReferalFees());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_RESPONSIBILITIES)) {
							if(!Utils.isBlankOrNull(data.getResponsibilities())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getResponsibilities());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_REQUIREMENTS)) {
							if(!Utils.isBlankOrNull(data.getRequirements())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getRequirements());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)) {
							if(!Utils.isBlankOrNull(data.getPrimarySkills())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getPrimarySkills());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_SECONDARY_SKILLS)) {
							if(!Utils.isBlankOrNull(data.getSecondarySkills())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getSecondarySkills());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_EDUCATION)) {
							if(!Utils.isBlankOrNull(data.getEducation())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getEducation());
								if(count1<count){
									sb.append(", ");
								}
							}
						} else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_BRANCH)) {
							if(!Utils.isBlankOrNull(data.getBranch())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getBranch());
								if(count1<count){
									sb.append(", ");
								}
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_EXPERIENCE)) {
							if(!Utils.isBlankOrNull(data.getExperience())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getExperience());
								if(count1<count){
									sb.append(", ");
								}
							}
						}else if (fieldId.equalsIgnoreCase(PositionConfigurationConstants.FIELD_NOTE)) {
							if(!Utils.isBlankOrNull(data.getPositionNote())){
								count1++;
								if(isShowLabels.equalsIgnoreCase(PositionConfigurationConstants.LABEL_SHOW)){
									sb.append(fieldTitle+": ");
								}
								sb.append(data.getPositionNote());
								if(count1<count){
									sb.append(", ");
								}
							}
						}
					}
					
				}//end of if show
			}//end of for
			data.setCommaSeparatedFieldList(sb.toString());
		}
	}
	
	private ArrayList<WcustomFieldData> constructWCustomFieldData(ArrayList<CustomFieldData> customFieldDataList){
		ArrayList<WcustomFieldData> wCustomFieldsData = new ArrayList<WcustomFieldData>();
		if(customFieldDataList!=null && customFieldDataList.size()>0){
			for (Iterator iterator = customFieldDataList.iterator(); iterator.hasNext();) {
				CustomFieldData customFieldData = (CustomFieldData) iterator.next();
				WcustomFieldData wCustomFieldData = new WcustomFieldData();
				wCustomFieldData.setFieldAttributes(customFieldData.getFieldAttributes());
				wCustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
				wCustomFieldData.setFieldDefaultValue(customFieldData.getFieldDefaultValue());
				wCustomFieldData.setFieldDisplayName(customFieldData.getFieldDisplayName());
				wCustomFieldData.setFieldName(customFieldData.getFieldName());
				wCustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
				wCustomFieldData.setFieldValues(customFieldData.getFieldValues());
				wCustomFieldData.setToValues(customFieldData.getToValues());
				wCustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
				wCustomFieldData.setFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
				wCustomFieldData.setFieldId(customFieldData.getFieldId());
				wCustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
				wCustomFieldData.setFieldOptions(customFieldData.getFieldOptions());
				wCustomFieldData.setFieldRank(customFieldData.getFieldRank());
				wCustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
				wCustomFieldData.setFieldType(customFieldData.getFieldType());
				wCustomFieldData.setFieldStringValue(customFieldData.getFieldStringValue());
				wCustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
				
				wCustomFieldsData.add(wCustomFieldData);
			}
		}
		return wCustomFieldsData;
	}
	
	public WpositionList getPositionsOpenToWebsite(String pSkillId, String locationId, String deptId) {
		WpositionList positions = new WpositionList();
		DBPreparedQuery dq = null;
		String qMarks = null;
		int cnt = 0;
		try {
			String[] dynParam = new String[3];
			ArrayList<String> dynamicContentForDynParam1 = new ArrayList<String>();
			dynParam[0] = "";
			dynParam[1] = "";
			dynParam[2] = getWebsitePositionsClause(dynamicContentForDynParam1);
			if (!Utils.isBlankOrNull(pSkillId)) {
				dynParam[1] += " , tp_position_skills tps ";
				dynParam[2] += " AND tps.position_id = tp.position_id ";
				dynParam[2] += " AND tps.skill_type=? ";
				dynamicContentForDynParam1.add(PositionConstants.POSITION_SKILL_PRIMARY);
				qMarks = Utils.setDynamicParamsAndReturnQmarks(pSkillId, dynamicContentForDynParam1);
				dynParam[2] += " AND tps.skill_id in ("+qMarks+") ";
			}
			
			if (!Utils.isBlankOrNull(locationId)) {
				qMarks = Utils.setDynamicParamsAndReturnQmarks(locationId, dynamicContentForDynParam1);
				dynParam[2] += " AND tl.location_id in ("+qMarks+") ";
			}
			
			if (!Utils.isBlankOrNull(deptId)) {
				dynParam[2] += " AND tp.dept_id=? ";
				dynamicContentForDynParam1.add(deptId);
			}
			
			dq = new DBPreparedQuery("dWebsitePositionManager_GetPositionDetailsForWebsite",dynParam);

			dq.setString(1, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setString(2, PositionConstants.POSITION_SKILL_SECONDARY);
			cnt=3;
			for (int i = 0; i < dynamicContentForDynParam1.size(); i++) {
				dq.setString(cnt++, dynamicContentForDynParam1.get(i));
			}
			
			List<SimpleDataObject> result = dq.getResult();
			ArrayList<WpositionData> list = new ArrayList<WpositionData>();
			if(result != null && result.size() > 0) {
				Iterator<SimpleDataObject> itr = result.iterator();
				while(itr.hasNext()) {
					SimpleDataObject sdo = itr.next();
					CustomFieldManager customFieldManager = new CustomFieldManager();
					ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(sdo.getString("positionId"), CustomFieldConstants.ENTITY_TYPE_POSITION);
					sdo.setAttribute("customFields", customFields);
					WpositionData data = constructWPositionData(sdo);
					list.add(data);
				}
			}	
			positions.setPositions(list);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}
	
	
	public Map<String,String> getPositionPrimarySkills() {
		return getPositionSkills(PositionConstants.POSITION_SKILL_PRIMARY);
	}
	
	public Map<String,String> getPositionSkills(String skillType) {
		Map<String,String> positionSkills = null;
		DBPreparedQuery dq = null;
		int cnt = 1;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContentForDynParam1 = new ArrayList<String>();
			dynParam[0] = getWebsitePositionsClause(dynamicContentForDynParam1);
			
			dq = new DBPreparedQuery("dWebsitePositionManager_GetPositionSkills",dynParam);

			for (int i = 0; i < dynamicContentForDynParam1.size(); i++) {
				dq.setString(cnt++, dynamicContentForDynParam1.get(i));
			}
			dq.setString(cnt++,skillType);
			
			List<SimpleDataObject> result = dq.getResult();
			positionSkills = new LinkedHashMap<String,String>();
			if(result != null && result.size() > 0) {
				Iterator<SimpleDataObject> itr = result.iterator();
				while(itr.hasNext()) {
					SimpleDataObject sdo = itr.next();
					positionSkills.put(sdo.getString("skillId"), sdo.getString("skillName"));				
				}
			}	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionSkills;
	}
	
	public Map<String,String> getPositionDepartments() {
		Map<String,String> positionDepartments = null;
		DBPreparedQuery dq = null;
		int cnt = 1;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContentForDynParam1 = new ArrayList<String>();
			dynParam[0] = getWebsitePositionsClause(dynamicContentForDynParam1);
			
			dq = new DBPreparedQuery("dWebsitePositionManager_GetPositionDepartments",dynParam);

			for (int i = 0; i < dynamicContentForDynParam1.size(); i++) {
				dq.setString(cnt++, dynamicContentForDynParam1.get(i));
			}
			
			List<SimpleDataObject> result = dq.getResult();
			positionDepartments = new LinkedHashMap<String,String>();
			if(result != null && result.size() > 0) {
				Iterator<SimpleDataObject> itr = result.iterator();
				while(itr.hasNext()) {
					SimpleDataObject sdo = itr.next();
					positionDepartments.put(sdo.getString("departmentId"), sdo.getString("departmentName"));				
				}
			}	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionDepartments;
	}
	
	public Map<String,String> getPositionLocations() {
		Map<String,String> positionLocations = null;
		DBPreparedQuery dq = null;
		int cnt = 1;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContentForDynParam1 = new ArrayList<String>();
			dynParam[0] = getWebsitePositionsClause(dynamicContentForDynParam1);
			
			dq = new DBPreparedQuery("dWebsitePositionManager_GetPositionLocations",dynParam);

			for (int i = 0; i < dynamicContentForDynParam1.size(); i++) {
				dq.setString(cnt++, dynamicContentForDynParam1.get(i));
			}
			List<SimpleDataObject> result = dq.getResult();
			positionLocations = new LinkedHashMap<String,String>();
			if(result != null && result.size() > 0) {
				Iterator<SimpleDataObject> itr = result.iterator();
				while(itr.hasNext()) {
					SimpleDataObject sdo = itr.next();
					positionLocations.put(sdo.getString("locationId"), sdo.getString("locationName"));				
				}
			}	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionLocations;
	}
	
	public WpositionFilters getPositionFilters() {
		WpositionFilters wpositionFilters = new WpositionFilters();
		if(PositionScreenConfigurationManager.isPositionFieldFilterOnWebsite(PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_1)){
			wpositionFilters.setDepartmentsMap(getPositionDepartments());
		}
		if(PositionScreenConfigurationManager.isPositionFieldFilterOnWebsite(PositionConfigurationConstants.FIELD_LOCATION)){
			wpositionFilters.setLocationsMap(getPositionLocations());
		}
		if(PositionScreenConfigurationManager.isPositionFieldFilterOnWebsite(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)){
			wpositionFilters.setSkillsMap(getPositionPrimarySkills());
		}
		return wpositionFilters;
	}
	
	public WsocialMediaSourceList getSocialMediaSources() {
		WsocialMediaSourceList wsocialMediaSourceList = new WsocialMediaSourceList();
		try {
			MastersManager mastersManager = new MastersManager();
			ArrayList<SourceData> result =mastersManager.getSourcesBySorceCategory(SocialMediaConstants.SOCIAL_MEDIA_SOURCE_CATEGORY_TYPE);
			ArrayList<WsocialMediaSource> list = new ArrayList<WsocialMediaSource>();
			if(result != null && result.size() > 0) {
				Iterator<SourceData> itr = result.iterator();
				while(itr.hasNext()) {
					SourceData sdo = itr.next();
					WsocialMediaSource data = new WsocialMediaSource();
					data.setSourceId(sdo.getSourceId());
					data.setSourceTitle(sdo.getSourceTitle());
					list.add(data);
				}
			}	
			wsocialMediaSourceList.setSources(list);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return wsocialMediaSourceList;
	}
	
	private SimpleDataObject getPositionDetailsForWebsite(String positionId) throws Exception {
		SimpleDataObject positionData = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[3];
			dynParam[0]="tp.responsibilities,tp.requirements,";			
			dynParam[1]="";
			dynParam[2] = "tp.position_id=?";
			dq = new DBPreparedQuery("dWebsitePositionManager_GetPositionDetailsForWebsite", dynParam);
			
			dq.setString(1, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setString(2, PositionConstants.POSITION_SKILL_SECONDARY);
			dq.setString(3, positionId);
			positionData = (SimpleDataObject) dq.getSingleObjectResult();
			
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(positionId, CustomFieldConstants.ENTITY_TYPE_POSITION);
			positionData.setAttribute("customFields", customFields);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionData;
	}
	
	private String getWebsitePositionsClause(ArrayList<String> dynContent){
		String dynParam = " tp.position_status = ? AND tp.is_published_to_web_site = ? AND tp.website_publish_from_date <= ? AND tp.website_publish_to_date >= ? ";
		if(dynContent==null){
			dynContent = new ArrayList<String>();
		}
		dynContent.add(PositionConstants.POSITION_STATUS_OPENED);
		dynContent.add(PositionConstants.POSITION_PUBLISHED_TO_WEBSITE);
		dynContent.add(Utils.getDateConvertedToString(new Date(), Utils.redYYYYMMDDFormat));
		dynContent.add(Utils.getDateConvertedToString(new Date(), Utils.redYYYYMMDDFormat));
		return dynParam;
	}
}
