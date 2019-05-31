/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.employeeservice.manager.EmployeeApplicantManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionFieldData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.manager.PositionScreenConfigurationManager;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.vendorservice.dataobject.VcustomFieldData;
import com.talentPool.vendorservice.dataobject.VimportFieldData;
import com.talentPool.vendorservice.dataobject.VimportFieldList;
import com.talentPool.vendorservice.dataobject.VpositionData;

/**
 * @author pallavi
 *
 */
public class VendorPositionManager {
	
	public VpositionData getPositionDetails(String positionId, String userId) {
		VpositionData data = null;
		try {
			PermissionSet permissionSet = new ReportManager().getUserPermission(userId);
			PositionManager positionManager = new PositionManager();
			SimpleDataObject positionData = (SimpleDataObject) positionManager.getPositionDescriptionToView(positionId, userId, permissionSet);
			SimpleDataObject positionRequirement = (SimpleDataObject) positionManager.getPositionRequirementsToView(positionId);
			data = constructVPositionData(positionId, positionData, positionRequirement);
			ArrayList<VcustomFieldData> vcustomFields = getVcustomFieldData(positionId);
			data.setCustomFields(vcustomFields);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the position details", e);
		}
		return data;
	}
	
	private ArrayList<VcustomFieldData> getVcustomFieldData(String positionId) {
		ArrayList<VcustomFieldData> vcustomFields = new ArrayList<VcustomFieldData>();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(positionId,
						CustomFieldConstants.ENTITY_TYPE_POSITION);
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
			vcustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
			if (customFieldData.getFieldDateValue() != null) {
				vcustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
			}
			vcustomFieldData.setFieldValues(customFieldData.getFieldValues());
			vcustomFieldData.setToValues(customFieldData.getToValues());
			vcustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
			vcustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
			vcustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
			vcustomFieldData.setFieldRank(customFieldData.getFieldRank());
			vcustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
			vcustomFields.add(vcustomFieldData);
		}
		return vcustomFields;
	}

	private VpositionData constructVPositionData(String positionId, SimpleDataObject sDo, SimpleDataObject positionRequirement) {		
		VpositionData data = new VpositionData();
		data.setPositionId(positionId);
		data.setPositionTitle(sDo.getString("positionName"));
		data.setContactPerson(sDo.getString("contactPerson"));
		data.setPrimarySkills(sDo.getString("primarySkills"));
		data.setSecondarySkills(sDo.getString("secondarySkills"));
		data.setExperience(sDo.getString("experience"));
		data.setEducation(sDo.getString("education"));
		data.setNoOfUnfilledVacancies(sDo.getString("vacancies"));
		data.setResponsibilities(sDo.getString("responsibilities"));
		data.setRequirements(sDo.getString("requirements"));		
		data.setPositionCode(sDo.getString("positionCode"));
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
		data.setLocation(sDo.getString("locationName"));		
		data.setBudgetItem(sDo.getString("budgetItemName"));
		data.setGrade(sDo.getString("gradeName"));
		data.setBand(sDo.getString("bandName"));
		data.setBu(sDo.getString("buName"));
		data.setCostCenter(sDo.getString("costCenterName"));
		data.setTypeOfVacancy(sDo.getString("typeOfVacancy"));
		data.setReplacementEmpCode(sDo.getString("replacementEmpCode"));
		data.setPositionTypeExtInt(sDo.getString("positionTypeExtInt"));
		
		data.setEducation(positionRequirement.getString("degreeTitle"));
		data.setBranch(positionRequirement.getString("branchName"));
		data.setExperience(positionRequirement.getString("minimumExperience")
				+ " To " + positionRequirement.getString("maximumExperience")
				+ " Years");
		data.setPrimarySkills(positionRequirement.getString("primarySkills"));
		data.setSecondarySkills(positionRequirement.getString("secondarySkills"));
		data.setRequirements(positionRequirement.getString("requirements"));
		
		return data;
	}
	
	public List<SimpleDataObject> getPositionsOpenToVendor(String vendorId) {
		List<SimpleDataObject> positions = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dVendorPositionManager_GetPositionsOpenToVendor");
			dq.setString(1, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setInt(2, UserConstants.ROLE_HR_MANAGER);
			dq.setInt(3, UserConstants.ROLE_RECRUITER);
			dq.setString(4, vendorId);
			dq.setString(5, PositionConstants.POSITION_STATUS_OPENED);
			dq.setTimestamp(6, new Timestamp(new Date().getTime()));
			dq.setTimestamp(7, new Timestamp(new Date().getTime()));
			positions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting positions open to vendor", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}
	
	private SimpleDataObject getPositionDetailsForVendor(String positionId) throws Exception {
		SimpleDataObject positionData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dVendorPositionManager_GetPositionDetailsForVendor");
			dq.setString(1, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setString(2, PositionConstants.POSITION_SKILL_SECONDARY);
			dq.setString(3, positionId);
			positionData = (SimpleDataObject) dq.getSingleObjectResult();
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting positions details for vendor", e);
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionData;
	}
	
	public String getPositionXml(List positions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (positions != null && positions.size() > 0) {
				for (int i = 0; i < positions.size(); i++) {
					SimpleDataObject sDo = (SimpleDataObject) positions.get(i);
					
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(sDo.getString("positionId")));
					wr.startElement("", "row", "", atr);
					
					String positionName = (sDo.getString("positionTitle") == null) ? "" : sDo.getString("positionTitle");
					String positionCode = (sDo.getString("positionCode") == null) ? "" : sDo.getString("positionCode");
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionName");
					wr.startElement("", "userdata", "", atr);
					wr.characters(positionName);
					wr.endElement("userdata");			
					
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionCode");
					wr.startElement("", "userdata", "", atr);
					wr.characters(positionCode);
					wr.endElement("userdata");			
					
					wr.startElement("cell");
					wr.characters("<a href=\"#\" onclick=\"javascript:onClickPositionName(" + sDo.getString("positionId") + ");\">" + Utils.escapeHTML(positionName) + "</a>");
					wr.endElement("cell");
					
					String primarySkills = sDo.getString("primarySkills");
					if (primarySkills == null) {
						primarySkills = "";
					}
					wr.startElement("cell");
					wr.characters(primarySkills);
					wr.endElement("cell");
					
					String experience = sDo.getString("experience");
					if (experience == null) {
						experience = "";
					}
					wr.startElement("cell");
					wr.characters(experience);
					wr.endElement("cell");
					
					String location = sDo.getString("location");
					if (location == null) {
						location = "";
					}
					wr.startElement("cell");
					wr.characters(location);
					wr.endElement("cell");
					
					String noOfUnfilledVacancies = sDo.getString("noOfUnfilledVacancies");
					if (noOfUnfilledVacancies == null) {
						noOfUnfilledVacancies = "";
					}
					wr.startElement("cell");
					wr.characters(noOfUnfilledVacancies);
					wr.endElement("cell");
									
					String contactPerson = sDo.getString("contactPerson");
					if (contactPerson == null) {
						contactPerson = "";
					}
					wr.startElement("cell");
					wr.characters(contactPerson);
					wr.endElement("cell");
					
					wr.endElement("row");					
				}
			}
			wr.endElement("rows");
			wr.endDocument();
			
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml for Positions", e);
		}
		return sWr.getBuffer().toString();
	}
	
	public VimportFieldList getPositionFieldList() {
		VimportFieldList vimportFieldList = new VimportFieldList();
		try {
			ArrayList<PositionFieldData> positionFields = new ArrayList<PositionFieldData>(); 
			positionFields.addAll(PositionScreenConfigurationManager.getPositionDescriptionFields());
			positionFields.addAll(PositionScreenConfigurationManager.getPositionRequirementsFields());			
			ArrayList<VimportFieldData> vimportFieldDataList = new ArrayList<VimportFieldData>();
			for (PositionFieldData positionFieldData : positionFields) {
				if (positionFieldData != null) {
					VimportFieldData vimportFieldData = new VimportFieldData();
					vimportFieldData.setFieldId(positionFieldData.getFieldId());
					vimportFieldData.setFieldTitle(positionFieldData.getFieldTitle());
					vimportFieldData.setFieldType(positionFieldData.getFieldType());
					vimportFieldData.setFieldVendorShow(positionFieldData.getFieldVendorShow());
					vimportFieldDataList.add(vimportFieldData);
				}
			}
			vimportFieldList.setImportFieldList(vimportFieldDataList);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting vendor position field list", e);
		}
		return vimportFieldList;
	}
	
	public ArrayList<SimpleDataObject> getpositionlocations(String loc) {
		ArrayList<SimpleDataObject> sDo = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetPositions_Location");
			dq.setString(1,loc + "%");
			sDo = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting interactions", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDo;

	}
	
	public ArrayList<SimpleDataObject> getPositionsSearch(String positionTitle,String userId) {
		ArrayList<SimpleDataObject> sDo = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetPositions_vendor");
			dq.setString(1, positionTitle + "%");
			dq.setString(2, PositionConstants.POSITION_STATUS_OPENED);
			dq.setString(3, userId);
			dq.setTimestamp(4, new Timestamp(new Date().getTime()));
			dq.setTimestamp(5, new Timestamp(new Date().getTime()));
			sDo = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting interactions", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDo;

	}
	
	public String getLocationsXML(ArrayList<SimpleDataObject> locations,String param) {
		StringWriter sWr = new StringWriter();
		try {
			if (locations != null) {
				XMLWriter wr = new XMLWriter(sWr);
				wr.startDocument();
				wr.startElement("ul");
				for (int i = 0; i < locations.size(); i++) {
					SimpleDataObject sdo = (SimpleDataObject) locations.get(i);
					String name = sdo.getString(param);
					wr.startElement("li");
					wr.characters(name);
					wr.endElement("li");
				}
				wr.endElement("ul");
				wr.endDocument();
			}
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while getting xml from list", e);
		}
		return sWr.getBuffer().toString();

	}
	
	public List<SimpleDataObject> getPositionsOpenToVendorForFilter(String vendorId,String positionTitle,String location) {
		List<SimpleDataObject> positions = null;
		EmployeeApplicantManager employeeApplicantManager=new EmployeeApplicantManager();
		String positionId=null;
		String locationId=null;
		if(!Utils.isBlankOrNull(positionTitle)){
			if(!Utils.isBlankOrNull(positionTitle)){
				positionTitle=positionTitle.trim();
			}
			 positionId=employeeApplicantManager.getPositionId(positionTitle);
			
		}
		if(!Utils.isBlankOrNull(location)){
			if(!Utils.isBlankOrNull(location)){
				location=location.trim();
			}
			 locationId=employeeApplicantManager.getLocationId(location);
			
		}
		
		
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if(!Utils.isBlankOrNull(positionId)){
				dynParam[0] += " AND tp.position_id=?";
				dynamicContent.add(positionId);
			}
			if(!Utils.isBlankOrNull(locationId)){
				dynParam[0] += " AND tpl.location_id=?";
				dynamicContent.add(locationId);
			}
			dq = new DBPreparedQuery("dVendorPositionManager_GetPositionsOpenToVendor_search", dynParam);
			int cnt = 1;
			dq.setString(cnt++, PositionConstants.POSITION_SKILL_PRIMARY);
			dq.setInt(cnt++, UserConstants.ROLE_HR_MANAGER);
			dq.setInt(cnt++, UserConstants.ROLE_RECRUITER);
			dq.setString(cnt++, vendorId);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);
			dq.setTimestamp(cnt++, new Timestamp(new Date().getTime()));
			dq.setTimestamp(cnt++, new Timestamp(new Date().getTime()));
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
				}
			positions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting positions open to vendor", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}
}
