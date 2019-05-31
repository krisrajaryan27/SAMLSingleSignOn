/**
 * 
 */
package com.talentPool.employeeservice.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.employeeservice.dataobject.EcustomFieldData;
import com.talentPool.employeeservice.dataobject.EimportFieldData;
import com.talentPool.employeeservice.dataobject.EimportFieldList;
import com.talentPool.employeeservice.dataobject.Epagination;
import com.talentPool.employeeservice.dataobject.EpositionData;
import com.talentPool.employeeservice.dataobject.EpositionFilters;
import com.talentPool.employeeservice.dataobject.EpositionSkills;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionFieldData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.manager.PositionPublishManager;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Shantanu
 * 
 */
public class EmployeePositionManager {

	public EpositionData getPositionDetails(String positionId, String userId) {
		EpositionData data = null;
		try {
			PermissionSet permissionSet = null; //new ReportManager().getUserPermission(userId);
			PositionManager positionManager = new PositionManager();
			SimpleDataObject positionData = (SimpleDataObject) positionManager.getPositionDescriptionToView(positionId, userId, permissionSet);
			SimpleDataObject positionRequirement = (SimpleDataObject) positionManager.getPositionRequirementsToView(positionId);
			data = constructEPositionData(positionId, positionData, positionRequirement);
			ArrayList<EcustomFieldData> ecustomFields = getEcustomFieldData(positionId);
			data.setCustomFields(ecustomFields);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the position details", e);
		}
		return data;
	}

	private ArrayList<EcustomFieldData> getEcustomFieldData(String positionId) {
		ArrayList<EcustomFieldData> ecustomFields = new ArrayList<EcustomFieldData>();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(positionId,
						CustomFieldConstants.ENTITY_TYPE_POSITION);
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
			ecustomFieldData.setFieldValues(customFieldData.getFieldValues());
			ecustomFieldData.setToValues(customFieldData.getToValues());
			ecustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
			ecustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
			ecustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
			ecustomFieldData.setFieldRank(customFieldData.getFieldRank());
			ecustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
			ecustomFields.add(ecustomFieldData);
		}
		return ecustomFields;
	}
	private EpositionData constructEPositionData(String positionId, SimpleDataObject sDo, SimpleDataObject positionRequirement) {
		EpositionData data = null;
		if(sDo!=null){
			data = new EpositionData();
			data.setPositionId(positionId);
			data.setPositionTitle(sDo.getString("positionName"));
			data.setPositionCode(sDo.getString("positionCode"));
			data.setContactPerson(sDo.getString("contactPerson"));
			data.setLocations(sDo.getString("locationName"));
			try {
				data.setPositionCreationDate(DateUtils.getSystemDateFormat(sDo.getDate("positionPublishDate")));
			} catch (ClassCastException cce) {
				data.setPositionCreationDate("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			data.setPositionReferalFees(sDo.getString("positionReferalFees"));
			data.setNoOfUnfilledVacancies(sDo.getString("vacancies"));
			data.setResponsibilities(sDo.getString("responsibilities"));
			
			data.setCanEmployeeApply(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB));
			data.setEmployeeApplyRefer(sDo.getString("employeeApplyRefer"));
			data.setEmployeeCanEmail(sDo.getString("employeeCanEmail"));
			
			data.setPositionOwner(sDo.getString("positionOwnerName"));
			data.setRequisitioner(sDo.getString("requisitioner"));
			data.setHireByDate(sDo.getString("hireByDate"));
			data.setNote(sDo.getString("note"));
			data.setDepartment(sDo.getString("department"));
			data.setSubDepartment(sDo.getString("subDepartment"));
			data.setSubSubDepartment(sDo.getString("subSubDepartment"));
			data.setSub3Department(sDo.getString("sub3Department"));
			data.setSub4Department(sDo.getString("sub4Department"));
			data.setPositionLevel(sDo.getString("positionLevel"));
			data.setPositionReferalFees(sDo.getString("positionReferalFees"));
			data.setBudgetItem(sDo.getString("budgetItemName"));
			data.setGrade(sDo.getString("gradeName"));
			data.setBand(sDo.getString("bandName"));
			data.setBu(sDo.getString("buName"));
			data.setCostCenter(sDo.getString("costCenterName"));
			data.setTypeOfVacancy(sDo.getString("typeOfVacancy"));
			data.setReplacementEmpCode(sDo.getString("replacementEmpCode"));
			data.setPositionTypeExtInt(sDo.getString("positionTypeExtInt"));
			
			data.setEducation(positionRequirement.getString("degreeTitle"));
			data.setBranches(positionRequirement.getString("branchName"));
			data.setExperience(positionRequirement.getString("minimumExperience")
					+ " To " + positionRequirement.getString("maximumExperience")
					+ " Years");
			data.setPrimarySkills(positionRequirement.getString("primarySkills"));
			data.setSecondarySkills(positionRequirement.getString("secondarySkills"));
			data.setRequirements(positionRequirement.getString("requirements"));
		}
		return data;
	}

	public List<EpositionData> getPositionsOpenToEmployee(String userId, String sourceId, String skillId, 
								String exp, String applyOrReferFilter, String sortBy, String pageNo, 
									int pageSize) {
		List<EpositionData> epositions = null;
		try {
			List<SimpleDataObject> positions = getPositions(userId, sourceId, skillId, exp, applyOrReferFilter, sortBy, pageNo, pageSize);
			epositions = construtsEpositionData(positions);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting positions for Employee", e);
		}		
		return epositions;
	}

	public List<SimpleDataObject> getPositions(String userId, String sourceId, String skillId, String exp, 
										String applyOrReferFilter, String sortBy, String pageNo, int pageSize) {
		List<SimpleDataObject> positions = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			dynParam[1] = "";

			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(skillId)) {
				dynParam[0] += " AND tp.position_id in (SELECT tpsi.position_id FROM tp_position_skills tpsi WHERE skill_id = ?) ";
				dynamicContent.add(skillId);
			}
			
			if (!Utils.isBlankOrNull(exp)) {
				dynParam[0] += " AND tp.position_min_exp <= ? AND tp.position_max_exp >= ?";
				dynamicContent.add(exp);
				dynamicContent.add(exp);
			}
			
			if(!Utils.isBlankOrNull(applyOrReferFilter)){
				dynParam[0] += " AND (tp.employee_apply_refer='0' OR tp.employee_apply_refer = ? )";
				dynamicContent.add(applyOrReferFilter);
			}
			
			if (EmployeeServiceConstants.SORT_BY_POSITION_CREATE_DATE_ASC.equalsIgnoreCase(sortBy)) {
				//dynParam[1] = " tp.position_date_created";
				dynParam[1] = " tp.position_publish_date";				
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_CREATE_DATE_DESC.equalsIgnoreCase(sortBy)) {
				//dynParam[1] = " tp.position_date_created desc";
				dynParam[1] = " tp.position_publish_date desc";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_REF_FEE_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " tp.position_referal_fees";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_REF_FEE_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " tp.position_referal_fees desc";
			}
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;

			dq = new DBPreparedQuery("dEmployeePositionManager_GetPositionsOpenToEmployee", dynParam);
			int cnt = 1;
			dq.setString(cnt++, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setString(cnt++, PositionConstants.POSITION_SKILL_SECONDARY);
			dq.setInt(cnt++, UserConstants.ROLE_HR_MANAGER);
			dq.setInt(cnt++, UserConstants.ROLE_RECRUITER);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(cnt++, PositionConstants.POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL);
			dq.setTimestamp(cnt++, new Timestamp(new Date().getTime()));
			dq.setTimestamp(cnt++, new Timestamp(new Date().getTime()));
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setInt(cnt++, lowerLimit);
			dq.setInt(cnt++, pageSize);
			positions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting positions for Employee", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}
	
						public List<EpositionData> getPositionsSearch(String userId, String sourceId, String skillId, 
								String exp, String applyOrReferFilter, String sortBy, String pageNo, 
									int pageSize, String position, String location) {
					List<EpositionData> epositions = null;
					try {
						EmployeeApplicantManager employeeApplicantManager=new EmployeeApplicantManager();
						String positionId=null;
						String locationId=null;
						if(!Utils.isBlankOrNull(position)){
							if(!Utils.isBlankOrNull(position)){
								position=position.trim();
							}
							 positionId=employeeApplicantManager.getPositionId(position);
							
						}
					
					List<SimpleDataObject> positions = getPositionsSearchForTitle(userId, sourceId, skillId, exp, applyOrReferFilter, sortBy, pageNo, pageSize,positionId,location);
					epositions = construtsEpositionData(positions);
					} catch (Exception e) {
					TPLogger.getLogger().error("Error while getting positions for Employee", e);
					}		
					return epositions;
					}
					
						
					public List<SimpleDataObject> getPositionsSearchForTitle(String userId, String sourceId, String skillId, String exp, 
										String applyOrReferFilter, String sortBy, String pageNo, int pageSize, String positionId, String location) {
					List<SimpleDataObject> positions = null;
					DBPreparedQuery dq = null;
					try {
						EmployeeApplicantManager employeeApplicantManager=new EmployeeApplicantManager();
						String locationId=null;
						if(!Utils.isBlankOrNull(location)){
							if(!Utils.isBlankOrNull(location)){
								location=location.trim();
							}
							 locationId=employeeApplicantManager.getLocationId(location);
							
						}
					String[] dynParam = new String[2];
					dynParam[0] = "";
					dynParam[1] = "";
					
					ArrayList<String> dynamicContent = new ArrayList<String>();
					if (!Utils.isBlankOrNull(skillId)) {
					dynParam[0] += " AND tp.position_id in (SELECT tpsi.position_id FROM tp_position_skills tpsi WHERE skill_id = ?) ";
					dynamicContent.add(skillId);
					}
					
					if (!Utils.isBlankOrNull(exp)) {
					dynParam[0] += " AND tp.position_min_exp <= ? AND tp.position_max_exp >= ?";
					dynamicContent.add(exp);
					dynamicContent.add(exp);
					}
					
					if(!Utils.isBlankOrNull(applyOrReferFilter)){
					dynParam[0] += " AND (tp.employee_apply_refer='0' OR tp.employee_apply_refer = ? )";
					dynamicContent.add(applyOrReferFilter);
					}
					if(!Utils.isBlankOrNull(positionId)){
						dynParam[0] += " AND tp.position_id=?";
						dynamicContent.add(positionId);
					}
					if(!Utils.isBlankOrNull(location)){
						if(!Utils.isBlankOrNull(locationId)){
							dynParam[0] += " AND tpl.location_id=?";
							dynamicContent.add(locationId);
						}
						else{
							dynParam[0] += " AND tpl.location_id is NULL";
						}
					}
					
					if (EmployeeServiceConstants.SORT_BY_POSITION_CREATE_DATE_ASC.equalsIgnoreCase(sortBy)) {
					//dynParam[1] = " tp.position_date_created";
					dynParam[1] = " tp.position_publish_date";				
					} else if (EmployeeServiceConstants.SORT_BY_POSITION_CREATE_DATE_DESC.equalsIgnoreCase(sortBy)) {
					//dynParam[1] = " tp.position_date_created desc";
					dynParam[1] = " tp.position_publish_date desc";
					} else if (EmployeeServiceConstants.SORT_BY_POSITION_REF_FEE_ASC.equalsIgnoreCase(sortBy)) {
					dynParam[1] = " tp.position_referal_fees";
					} else if (EmployeeServiceConstants.SORT_BY_POSITION_REF_FEE_DESC.equalsIgnoreCase(sortBy)) {
					dynParam[1] = " tp.position_referal_fees desc";
					}
					int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;
					
					dq = new DBPreparedQuery("dEmployeePositionManager_GetPositionsOpenToSearch", dynParam);
					int cnt = 1;
					dq.setString(cnt++, PositionConstants.POSITION_SKILL_PRIMARY);
					dq.setString(cnt++, PositionConstants.POSITION_SKILL_SECONDARY);
					dq.setInt(cnt++, UserConstants.ROLE_HR_MANAGER);
					dq.setInt(cnt++, UserConstants.ROLE_RECRUITER);
					dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
					dq.setString(cnt++, PositionConstants.POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL);
					dq.setTimestamp(cnt++, new Timestamp(new Date().getTime()));
					dq.setTimestamp(cnt++, new Timestamp(new Date().getTime()));
					for (int i = 0; i < dynamicContent.size(); i++) {
					dq.setString(cnt++, dynamicContent.get(i));
					}
					dq.setInt(cnt++, lowerLimit);
					dq.setInt(cnt++, pageSize);
					positions = dq.getResult();
					} catch (Exception e) {
					TPLogger.getLogger().error("Error while getting positions for Employee", e);
					} finally {
					if (dq != null) {
					dq.releaseConnection();
					}
					}
					return positions;
					}
	private List<EpositionData> construtsEpositionData(List<SimpleDataObject> positions) {
		List<EpositionData> epositions = new ArrayList<EpositionData>();
		Iterator<SimpleDataObject> itr = positions.iterator();
		while (itr.hasNext()) {
			SimpleDataObject sDo = itr.next();
			EpositionData eposition = new EpositionData();
			eposition.setPositionId(sDo.getString("positionId"));
			eposition.setPositionTitle(sDo.getString("positionTitle"));
			eposition.setPrimarySkills(sDo.getString("primarySkills"));
			eposition.setSecondarySkills(sDo.getString("secondarySkills"));
			eposition.setContactPerson(sDo.getString("contactPerson"));
			eposition.setExperience(sDo.getString("experience"));
			eposition.setEducation(sDo.getString("education"));
			eposition.setPositionReferalFees(sDo.getString("positionReferalFees"));
			try {
				eposition.setPositionCreationDate(DateUtils.getSystemDateFormat(sDo.getDate("positionPublishDate")));				
			} catch (ClassCastException cce) {
				eposition.setPositionCreationDate("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			eposition.setPositionPriority(sDo.getString("positionPriority"));

			epositions.add(eposition);
		}
		return epositions;
	}

	public Epagination getPositionPaginationData(String userId, String sourceId, String skillId, String exp, String sortBy, String pageNo, int pageSize) {
		long recordCount = getPositionRecordCount(userId, sourceId, skillId, exp, sortBy, pageNo, pageSize);
		Epagination epagination = new Epagination(recordCount, pageSize, Integer.parseInt(pageNo));
		return epagination;
	}

	private long getPositionRecordCount(String userId, String sourceId, String skillId, String exp, String sortBy, String pageNo, int pageSize) {
		long recordCount = 0;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			dynParam[1] = "";

			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(skillId)) {
				dynParam[0] += " AND tp.position_id in (SELECT tpsi.position_id FROM tp_position_skills tpsi WHERE skill_id = ?) ";
				dynamicContent.add(skillId);
			}
			if (!Utils.isBlankOrNull(exp)) {
				dynParam[0] += " AND tp.position_min_exp <= ? ";
				dynamicContent.add(exp);
			}
			if (EmployeeServiceConstants.SORT_BY_POSITION_CREATE_DATE_ASC.equalsIgnoreCase(sortBy)) {
				//dynParam[1] = " tp.position_date_created";
				dynParam[1] = " tp.position_publish_date";				
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_CREATE_DATE_DESC.equalsIgnoreCase(sortBy)) {
				//dynParam[1] = " tp.position_date_created desc";
				dynParam[1] = " tp.position_publish_date desc";				
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_REF_FEE_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " tp.position_referal_fees";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_REF_FEE_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " tp.position_referal_fees desc";
			}
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;	
			
			dq = new DBPreparedQuery("dEmployeePositionManager_GetPositionsCount", dynParam);
			int cnt = 1;
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);			
			dq.setString(cnt++, PositionConstants.POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL);
			dq.setString(cnt++, Utils.getDateConvertedToString(new Date(), Utils.redYYYYMMDDFormat));
			dq.setString(cnt++, Utils.getDateConvertedToString(new Date(), Utils.redYYYYMMDDFormat));
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			recordCount = new Long(dq.getIdResult()).longValue();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the record count", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return recordCount;
	}

	private SimpleDataObject getPositionDetailsForEmployee(String positionId) throws Exception {
		SimpleDataObject positionData = null;

		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dEmployeePositionManager_GetPositionDetailsForEmployee");
			dq.setString(1, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setString(2, PositionConstants.POSITION_SKILL_SECONDARY);
			dq.setString(3, positionId);
			dq.setString(4, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(5, PositionConstants.POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL);
			dq.setString(6, Utils.getDateConvertedToString(new Date(), Utils.redYYYYMMDDFormat));
			dq.setString(7, Utils.getDateConvertedToString(new Date(), Utils.redYYYYMMDDFormat));
			positionData = (SimpleDataObject) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting positions details for Employee", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionData;
	}
	
	public String getAnnouncements(){
		String announcements = "";
		DBPreparedQuery dq = null;
		try {
			PositionPublishManager manager = new PositionPublishManager();
			announcements = manager.getAnnouncementsToPublish(PositionConstants.EMPLOYEE_ANNOUNCEMENTS);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return announcements;
	}
	
	public List<EpositionSkills> getPositionSkills(boolean primarySkills, boolean secondarySkills){
		List<EpositionSkills> positionSkillsList = null;
		List<SimpleDataObject> positionSkillsSdo = null;
		DBPreparedQuery dq = null;
		String[] dynParam = {""};
		List<String> dynamicContent = new ArrayList<String>();
		int cnt = 0;
		try {
			if(primarySkills && secondarySkills){
				dynParam[0] = "AND (tps.skill_type=? OR tps.skill_type=?)";
				dynamicContent.add(PositionConstants.POSITION_SKILL_PRIMARY);
				dynamicContent.add(PositionConstants.POSITION_SKILL_SECONDARY);
			}else if(primarySkills){
				dynParam[0] = "AND tps.skill_type=?";
				dynamicContent.add(PositionConstants.POSITION_SKILL_PRIMARY);
			}else if(secondarySkills){
				dynParam[0] = "AND tps.skill_type=?";
				dynamicContent.add(PositionConstants.POSITION_SKILL_SECONDARY);
			}
			
			dq = new DBPreparedQuery("dEmployeePositionManager_GetSkillsToFilterPositions",dynParam);
			dq.setString(1, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(2, PositionConstants.POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL);
			cnt=3;
			for (String param : dynamicContent) {
				dq.setString(cnt++, param);
			}
			positionSkillsSdo = dq.getResult();
			positionSkillsList = convertSdoToSkillDataObj(positionSkillsSdo);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionSkillsList;
	}
	
	public EpositionFilters getPositionScreenFilters() {
		EpositionFilters epositionFilters = null;
		List<EpositionSkills> epositionSkills = getPositionSkills(true,true);
		Map<String,String> applyReferOptions = getApplyReferOptions();
		if(epositionSkills!=null){
			epositionFilters = new EpositionFilters();
			epositionFilters.setSkillsList((ArrayList<EpositionSkills>)epositionSkills);
			epositionFilters.setApplyReferOptions(applyReferOptions);
		}
		return epositionFilters;
	}
	
	private List<EpositionSkills> convertSdoToSkillDataObj(List<SimpleDataObject> positionSkillsSdo){
		List<EpositionSkills> epsList = null;
		if(positionSkillsSdo!=null){
			EpositionSkills eps = null;
			epsList = new ArrayList<EpositionSkills>();
			for (SimpleDataObject sdo : positionSkillsSdo) {
				eps = new EpositionSkills();
				eps.setSkillId(""+sdo.getAttribute("skillId"));
				eps.setSkillName((String)sdo.getAttribute("skillName"));
				epsList.add(eps);
			}
		}	
		return epsList;
	}
	
	private Map<String,String> getApplyReferOptions(){
		Map<String,String> applyReferOptions = null;
		if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB))){
			applyReferOptions = new LinkedHashMap<String, String>();
			applyReferOptions.put(PositionConstants.POSITIONS_EMPLOYEE_APPLY, TPLabels.getLabel("position.publish.label.apply"));
			applyReferOptions.put(PositionConstants.POSITIONS_EMPLOYEE_REFER, TPLabels.getLabel("position.publish.label.refer"));
			applyReferOptions.put(PositionConstants.POSITIONS_EMPLOYEE_APPLY_REFER, TPLabels.getLabel("position.publish.label.apply_and_refer"));
		}
		return applyReferOptions;
	}
	
	public EimportFieldList getPositionFieldList() {
		EimportFieldList eimportFieldList = new EimportFieldList();
		try {
			ArrayList<PositionFieldData> positionFields = new ArrayList<PositionFieldData>(); 
			positionFields.addAll(PositionScreenConfigurationManager.getPositionDescriptionFields());
			positionFields.addAll(PositionScreenConfigurationManager.getPositionRequirementsFields());
			ArrayList<EimportFieldData> eimportFieldDataList = new ArrayList<EimportFieldData>();
			for (PositionFieldData positionFieldData : positionFields) {
				if (positionFieldData != null) {
					EimportFieldData eimportFieldData = new EimportFieldData();
					eimportFieldData.setFieldId(positionFieldData.getFieldId());
					eimportFieldData.setFieldTitle(positionFieldData.getFieldTitle());
					eimportFieldData.setFieldType(positionFieldData.getFieldType());
					eimportFieldData.setFieldEmployeeShow(positionFieldData.getFieldEmployeeShow());
					eimportFieldDataList.add(eimportFieldData);
				}
			}
			eimportFieldList.setImportFieldList(eimportFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting employee position field list", e);
		}
		return eimportFieldList;
	}
}
