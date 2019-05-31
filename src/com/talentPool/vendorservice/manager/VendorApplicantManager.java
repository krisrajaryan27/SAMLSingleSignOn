/**
 * 
 */
package com.talentPool.vendorservice.manager;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;


import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.vendorservice.VendorServiceConstants;
import com.talentPool.vendorservice.dataobject.CustomFieldCell;
import com.talentPool.vendorservice.dataobject.CustomFieldRow;
import com.talentPool.vendorservice.dataobject.CustomFieldTable;
import com.talentPool.vendorservice.dataobject.VapplicantData;
import com.talentPool.vendorservice.dataobject.VcustomFieldData;
import com.talentPool.vendorservice.dataobject.VeducationalData;
import com.talentPool.vendorservice.dataobject.VemploymentHistoryData;
import com.talentPool.vendorservice.dataobject.Vpagination;

/**
 * @author pallavi
 * 
 */
public class VendorApplicantManager {
	public List<VapplicantData> getApplicantsForVendor(boolean doShowApplicantsInProcess, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId) {
		List<SimpleDataObject> applicants = getApplicants(doShowApplicantsInProcess, sortBy, pageNo, pageSize, vendorId, sourceId);
		List<VapplicantData> vapplicants = construtsVapplicantData(applicants);
		return vapplicants;
	}

	private List<SimpleDataObject> getApplicants(boolean doShowApplicantsInProcess, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId) {
		List<SimpleDataObject> applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			if (doShowApplicantsInProcess) {
				dynParam[0] = " AND ta.applicant_position_id is not null AND ta.applicant_step_id is not null AND tp.position_id is not null ";
			}
			if (VendorServiceConstants.SORT_BY_NAME_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name";
			} else if (VendorServiceConstants.SORT_BY_NAME_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name desc";
			} else if (VendorServiceConstants.SORT_BY_POSITION_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title";
			} else if (VendorServiceConstants.SORT_BY_POSITION_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title desc";
			} else if (VendorServiceConstants.SORT_BY_DATE_UPLOADED_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created";
			} else if (VendorServiceConstants.SORT_BY_DATE_UPLOADED_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created desc";
			} else if (VendorServiceConstants.SORT_BY_CURRENT_STATUS_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus";
			} else if (VendorServiceConstants.SORT_BY_CURRENT_STATUS_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus desc";
			}
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;
			dq = new DBPreparedQuery("dVendorApplicantManager_GetApplicants", dynParam);
			dq.setId(1, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(2, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(3, GlobalConstants.ENABLED);
			dq.setInt(4, PositionConstants.STEP_SCHEDULED);
			dq.setId(5, sourceId);
			dq.setId(6, vendorId);
			dq.setId(7, sourceId);
			dq.setInt(8, lowerLimit);
			dq.setInt(9, pageSize);
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting applicants", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}
	
	private List<SimpleDataObject> getApplicants(boolean doShowApplicantsInProcess,  boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId) {
		List<SimpleDataObject> applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			dynParam[1] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			boolean appendOR=false;
			if (doShowApplicantsInProcess ||doShowApplicantsJoined || doShowApplicantsRejected) {
				dynParam[0] += " AND (";
			}
			if (doShowApplicantsInProcess) {
				dynParam[0] += " ( ta.applicant_position_id is not null AND ta.applicant_step_id is not null AND tp.position_id is not null )";
				appendOR=true;
			}
			if (doShowApplicantsJoined) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				dynParam[0] += " tasp.position_step_id_to= ? " ;
				dynamicContent.add(SelectionProcessConstants.STEP_JOIN);
				appendOR=true;
			}
			if (doShowApplicantsRejected) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				//for showing reject candidate in employee portal with position title
				dynParam[0] += " (ta.applicant_step_id is null )";
			}
			if(dynParam[0].length()>0){
				dynParam[0] += " ) ";
			}
			
			if (EmployeeServiceConstants.SORT_BY_NAME_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name";
			} else if (EmployeeServiceConstants.SORT_BY_NAME_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name desc";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title desc";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created desc";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus desc";
			}
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetApplicants", dynParam);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(cnt++, GlobalConstants.ENABLED);
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setId(cnt++, vendorId);
			dq.setId(cnt++, sourceId);
			
			for(int i=0;i<dynamicContent.size();i++){
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setInt(cnt++, lowerLimit);
			dq.setInt(cnt++, pageSize);
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	private List<VapplicantData> construtsVapplicantData(List<SimpleDataObject> applicants) {
		List<VapplicantData> vapplicants = new ArrayList<VapplicantData>();
		Iterator<SimpleDataObject> itr = applicants.iterator();
		while (itr.hasNext()) {
			SimpleDataObject sDo = itr.next();
			VapplicantData vapplicant = new VapplicantData();
			vapplicant.setApplicantId(sDo.getString("applicantId"));
			vapplicant.setApplicantName(sDo.getString("applicantName"));
			vapplicant.setPositionTitle(sDo.getString("positionTitle"));
			try {
				vapplicant.setDateUploaded(DateUtils.getSystemDateFormat(sDo.getDate("dateUploaded")));				
			} catch (ClassCastException cce) {
				vapplicant.setDateUploaded("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			vapplicant.setCurrentStatus(sDo.getString("currentStatus"));
			if (sDo.getDate("applicantWorkingSince") != null) {
				vapplicant.setApplicantWorkingSince(sDo.getDate("applicantWorkingSince"));
			}
			vapplicant.setCurrentEmployer(sDo.getString("currentEmployer"));
			vapplicants.add(vapplicant);
		}
		return vapplicants;
	}

	public Vpagination getPaginationData(boolean doShowApplicantsInProcess, String pageNo, int pageSize, String vendorId, String sourceId) {
		long recordCount = getRecordCount(doShowApplicantsInProcess, vendorId, sourceId);
		Vpagination vpagination = new Vpagination(recordCount, pageSize, Integer.parseInt(pageNo));
		return vpagination;
	}

	private long getRecordCount(boolean doShowApplicantsInProcess, String vendorId, String sourceId) {
		long recordCount = 0;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "";
			if (doShowApplicantsInProcess) {
				dynParam[0] = " AND ta.applicant_position_id is not null AND ta.applicant_step_id is not null AND tp.position_id is not null ";
			}
			dq = new DBPreparedQuery("dVendorApplicantManager_GetApplicantsCount", dynParam);
			dq.setId(1, sourceId);
			dq.setId(2, vendorId);
			dq.setId(3, sourceId);
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

	public VapplicantData getApplicantDisplayData(String applicantId, String userId, String sourceId) {
		VapplicantData vapplicantData = getVapplicantData(applicantId, userId, sourceId);
		if (!Utils.isBlankOrNull(applicantId)){
		ArrayList<VeducationalData> veducationalDataList = getVeducationalData(applicantId);
		vapplicantData.setEducationalDetails(veducationalDataList);
		ArrayList<VcustomFieldData> vcustomFields = getVcustomFieldData(applicantId);
		vapplicantData.setCustomFields(vcustomFields);
		}
		return vapplicantData;
	}

	private ArrayList<VcustomFieldData> getVcustomFieldData(String applicantId) {
		ArrayList<VcustomFieldData> vcustomFields = new ArrayList<VcustomFieldData>();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
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
			vcustomFieldData.setTableId(customFieldData.getTableId());
			vcustomFieldData.setTableName(customFieldData.getTableName());
			vcustomFieldData.setFieldRank(customFieldData.getFieldRank());
			vcustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
			vcustomFields.add(vcustomFieldData);

		}
		return vcustomFields;
	}
	
	private VcustomFieldData getVcustomFieldDataFromCustomField(CustomFieldData customFieldData) {
		
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
			vcustomFieldData.setTableId(customFieldData.getTableId());
			vcustomFieldData.setTableName(customFieldData.getTableName());

		return vcustomFieldData;
	}

	private ArrayList<VeducationalData> getVeducationalData(String applicantId) {
		ArrayList<VeducationalData> veducationalDataList = new ArrayList<VeducationalData>();
		ApplicantManager applicantManager = new ApplicantManager();
		ArrayList<EducationalData> educationalDataList = applicantManager.getEducationalInfo(applicantId);
		for (int i = 0; educationalDataList != null && i < educationalDataList.size(); i++) {
			EducationalData educationalData = educationalDataList.get(i);
			VeducationalData veducationalData = new VeducationalData();
			veducationalData.setDegreeTitle(educationalData.getDegreeTitle());
			veducationalData.setMajor(educationalData.getMajor());
			veducationalData.setInstitute(educationalData.getInstitute());
			veducationalData.setGrade(educationalData.getGrade());
			if(educationalData.getFromYear()!=null){
				veducationalData.setFromYear(educationalData.getFromYear());
			}
			veducationalData.setRemarks(educationalData.getRemarks());
			if (educationalData.getYearOfPassing() != null) {
				veducationalData.setYearOfPassing(educationalData.getYearOfPassing());
			}
			veducationalDataList.add(veducationalData);
		}
		return veducationalDataList;
	}

	/**
	 * @param applicantId
	 * @param userId
	 * @param sourceId
	 * @return
	 */
	private VapplicantData getVapplicantData(String applicantId, String userId, String sourceId) {
		SimpleDataObject sDo = getApplicantDisplaySimpleData(applicantId, userId, sourceId);
		VapplicantData vapplicantData = new VapplicantData();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		try {
			if (!Utils.isBlankOrNull(applicantId)){
			vapplicantData.setApplicantId(sDo.getString("applicantId"));
			vapplicantData.setApplicantName(sDo.getString("applicantName"));
			vapplicantData.setCurrentLocation(sDo.getString("currentLocation"));
//			CustomFieldTable cft = new CustomFieldTable();
//			cft.setTableId("11");
//			cft.setTableName("test");
//			List<CustomFieldTable> tabs = new ArrayList<CustomFieldTable>();
//			tabs.add(cft);
//			vapplicantData.setCustomTables(tabs);
			try {
				vapplicantData.setDateUploaded(DateUtils.getSystemDateFormat(sDo.getDate("dateUploaded")));				
			} catch (ClassCastException cce) {
				vapplicantData.setDateUploaded("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			vapplicantData.setOriginalResumePath(sDo.getString("originalResumePath"));
			vapplicantData.setCurrentEmployer(sDo.getString("currentEmployer"));
			if (sDo.getDate("applicantWorkingSince") != null) {
				vapplicantData.setApplicantWorkingSince(sDo.getDate("applicantWorkingSince"));
			}
			vapplicantData.setApplicantJoined(sDo.getString("applicantJoined").equals(ApplicantConstants.APPLICANT_JOINED) ? true : false);
			vapplicantData.setCurrentCTC(sDo.getString("currentCTC"));
			vapplicantData.setExpectedCTC(sDo.getString("expectedCTC"));
			try {
				vapplicantData.setCurrentCTCDate(DateUtils.getSystemDateFormat(sDo.getDate("cureentCTCDate")));				
			} catch (ClassCastException cce) {
				vapplicantData.setCurrentCTCDate("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			try {
				vapplicantData.setExpectedCTCDate(DateUtils.getSystemDateFormat(sDo.getDate("expectedCTCDate")));				
			} catch (ClassCastException cce) {
				vapplicantData.setExpectedCTCDate("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			vapplicantData.setSkills(sDo.getString("skills"));

			vapplicantData.setApplicantEmail1(sDo.getString("applicantEmail1"));
			vapplicantData.setApplicantEmail2(sDo.getString("applicantEmail2"));
			vapplicantData.setApplicantHomePhone(sDo.getString("applicantHomePhone"));
			vapplicantData.setApplicantCellPhone(sDo.getString("applicantCellPhone"));
			vapplicantData.setApplicantWorkPhone(sDo.getString("applicantWorkPhone"));
			vapplicantData.setNoticePeriod(sDo.getString("noticePeriod"));
			
			if (!Utils.isBlankOrNull(sDo.getString("applicantPositionId"))) {
				vapplicantData.setApplicantPositionId(sDo.getString("applicantPositionId"));
			}
			if (!Utils.isBlankOrNull(sDo.getString("applicantStepId"))) {
				vapplicantData.setApplicantStepId(sDo.getString("applicantStepId"));
			}
			
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
				ArrayList<CustomFieldData> tabularCustomFields = customFieldManager.getCustomFieldDataForTables(applicantId);
				
				ArrayList<CustomFieldData> tabularFields = customFieldManager.getApplicantTabularCustomFields();
				List<CustomFieldTable> customTables = new ArrayList<CustomFieldTable>();
				for(CustomFieldData tabularData:tabularFields){
					CustomFieldTable cft = new CustomFieldTable();
					cft.setTableId(tabularData.getTableId());
					cft.setTableName(tabularData.getTableName());
					List<CustomFieldRow> rows = new ArrayList<CustomFieldRow>();
					boolean tableFieldPresent = false;
					boolean isFirstIteration = true;
					for (CustomFieldData data:tabularCustomFields){
						CustomFieldData tData = (CustomFieldData) data;
						if (!Utils.isBlankOrNull(tData.getTableId()) && tData.getTableId().equals(tabularData.getTableId())){
							tableFieldPresent = true;
							if (isFirstIteration){
								CustomFieldRow row = new CustomFieldRow();
								CustomFieldCell cell = new CustomFieldCell();
								cell.setValue(tData.getFieldDisplayName());
								row.addCell(cell);
								rows.add(row);
								String[] values = tData.getFieldValues();
								for (int i=0; i<values.length; i++){
									CustomFieldRow row1 = new CustomFieldRow();
									CustomFieldCell cell1 = new CustomFieldCell();
									cell1.setValue(values[i]);
									row1.addCell(cell1);
									rows.add(row1);
								}
								isFirstIteration= false;
							}else{
								CustomFieldRow row = rows.get(0);
								CustomFieldCell cell = new CustomFieldCell();
								cell.setValue(tData.getFieldDisplayName());
								row.addCell(cell);
								String[] values = tData.getFieldValues();
								for (int i=0; i<values.length; i++){
									CustomFieldRow row1 = rows.get(i+1);
									CustomFieldCell cell1 = new CustomFieldCell();
//									tData.setFieldStringValue(values[i]);
//									cell1.setData(tData);
									cell1.setValue(values[i]);
									row1.addCell(cell1);
								}
							}
							
						}
					}
					if (!tableFieldPresent){
						ArrayList<CustomFieldData> tabCustomFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
						for (CustomFieldData data:tabCustomFields){
							CustomFieldData tData = (CustomFieldData) data;
							if (!Utils.isBlankOrNull(tData.getTableId()) && tData.getTableId().equals(tabularData.getTableId())){
								if (isFirstIteration){
									CustomFieldRow row = new CustomFieldRow();
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
									rows.add(row);
									isFirstIteration= false;
								}else{
									CustomFieldRow row = rows.get(0);
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
								}
							}
						}
					}
					cft.setRows(rows);
					customTables.add(cft);
				}
				
				
				vapplicantData.setCustomTables(customTables);
			}
			}else{
				if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
					ArrayList<CustomFieldData> tabularFields = customFieldManager.getApplicantTabularCustomFields();
					List<CustomFieldTable> customTables = new ArrayList<CustomFieldTable>();
					for(CustomFieldData tabularData:tabularFields){
						CustomFieldTable cft = new CustomFieldTable();
						cft.setTableId(tabularData.getTableId());
						cft.setTableName(tabularData.getTableName());
						List<CustomFieldRow> rows = new ArrayList<CustomFieldRow>();
						ArrayList<CustomFieldData> tabCustomFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
						boolean isFirstIteration = true;
						for (CustomFieldData data:tabCustomFields){
							CustomFieldData tData = (CustomFieldData) data;
							if (!Utils.isBlankOrNull(tData.getTableId()) && tData.getTableId().equals(tabularData.getTableId())){
								if (isFirstIteration){
									CustomFieldRow row = new CustomFieldRow();
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
									rows.add(row);
									CustomFieldRow row1 = new CustomFieldRow();
									CustomFieldCell cell1 = new CustomFieldCell();
									CustomFieldData tabData = (CustomFieldData)tData.clone();
									tabData.setFieldStringValue("");
									String[] fieldValues = new String[1];
									fieldValues[0] = "";
									tabData.setFieldValues(fieldValues);
									cell1.setData(getVcustomFieldDataFromCustomField(tabData));
									cell1.setValue("");
									row1.addCell(cell1);
									rows.add(row1);
									isFirstIteration = false;
								}else{
									CustomFieldRow row = rows.get(0);
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
									CustomFieldRow row1 = rows.get(1);
									CustomFieldCell cell1 = new CustomFieldCell();
									CustomFieldData tabData = (CustomFieldData)tData.clone();
									tabData.setFieldStringValue("");
									String[] fieldValues = new String[1];
									fieldValues[0] = "";
									tabData.setFieldValues(fieldValues);
									cell1.setData(getVcustomFieldDataFromCustomField(tabData));
									cell1.setValue("");
									row1.addCell(cell1);
								}
							}
						}
						cft.setRows(rows);
						customTables.add(cft);
					}
					vapplicantData.setCustomTables(customTables);
				}
			}
			

		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vapplicantData;
	}
	public VapplicantData getVapplicantDataFromApplicantData(ApplicantData applicantData) {
		VapplicantData vapplicantData = new VapplicantData();
		
		
		try {
			
			String noticePeriod=applicantData.getNoticePeriod();
			if(!Utils.isBlankOrNull(noticePeriod)){
				vapplicantData.setNoticePeriod(noticePeriod);
			}
			
			String applicantEmail1=applicantData.getApplicantEmail1();
			if(!Utils.isBlankOrNull(applicantEmail1)){
				vapplicantData.setApplicantEmail1(applicantEmail1);
			}
			String applicantEmail2=applicantData.getApplicantEmail2();
			if(!Utils.isBlankOrNull(applicantEmail2)){
				vapplicantData.setApplicantEmail2(applicantEmail2);
				}
		// get phone
			String cellPhone=applicantData.getApplicantCellPhone();
			if(!Utils.isBlankOrNull(cellPhone)){
				vapplicantData.setApplicantCellPhone(cellPhone);
		
				
			}
			String homePhone=applicantData.getApplicantHomePhone();
			if(!Utils.isBlankOrNull(homePhone)){
				vapplicantData.setApplicantHomePhone(homePhone);
			}
			
			String workPhone=applicantData.getApplicantWorkPhone();
			if(!Utils.isBlankOrNull(workPhone)){
				vapplicantData.setApplicantWorkPhone(workPhone);
			}
		// get name
			String applicantName=applicantData.getApplicantName();
			if(!Utils.isBlankOrNull(applicantName)){
				vapplicantData.setApplicantName(applicantName);
			}
			String applicantCity=applicantData.getApplicantCity();
			if(!Utils.isBlankOrNull(applicantCity)){
				vapplicantData.setCurrentLocation(applicantCity);
			}
			String applicantCurrentEmployer=applicantData.getApplicantCurrentEmployer();
			if(!Utils.isBlankOrNull(applicantName)){
				vapplicantData.setCurrentEmployer(applicantCurrentEmployer);
			}
			
			String dateOfBirth=(String)applicantData.getDateOfBirthForDisplay();
			if (!Utils.isBlankOrNull(dateOfBirth)){
				vapplicantData.setDateOfBirth(dateOfBirth);
			}
			String currentCTC = (String) applicantData.getCurrentCTC();
			if (!Utils.isBlankOrNull(currentCTC)){
				vapplicantData.setCurrentCTC(currentCTC);
			}
			String expectedCTC = (String) applicantData.getExpectedCTC();
			if (!Utils.isBlankOrNull(expectedCTC)) {
				vapplicantData.setExpectedCTC(expectedCTC);
			}
			
			String wrkExp = (String) applicantData.getApplicantTotalExperience();
			String applicantId=applicantData.getApplicantId();
			vapplicantData.setApplicantId(applicantId);
			
			String applicantJoined=applicantData.getApplicantJoined();
			if(!Utils.isBlankOrNull(applicantJoined)){
			if(applicantJoined=="1"){
				vapplicantData.setApplicantJoined(true);
			}
			else{
				vapplicantData.setApplicantJoined(false);
			}
			}
			
			String applicantOriginalResumePath=applicantData.getApplicantOriginalResumePath();
			if(!Utils.isBlankOrNull(applicantOriginalResumePath)){
				
					vapplicantData.setOriginalResumePath(applicantOriginalResumePath);
				}
			ArrayList<EducationalData> educationalDetails=applicantData.getEducationalDetails();
			ArrayList<VeducationalData> vEducationalDetails=new ArrayList<VeducationalData>();
			VeducationalData vEducationalData=null;
			
			if (educationalDetails != null && educationalDetails.size() > 0) {
				int sz = educationalDetails.size();
				
				for (int i = 0; i < sz; i++) {
					vEducationalData=new VeducationalData();
					EducationalData eData = (EducationalData) educationalDetails.get(i);
					String degreeTitle=eData.getDegreeTitle();
					if(!Utils.isBlankOrNull(degreeTitle)){
						vEducationalData.setDegreeTitle(degreeTitle);
					}
					String major=eData.getMajor();
					if(!Utils.isBlankOrNull(major)){
						vEducationalData.setMajor(major);
					}
					String institute=eData.getInstitute();
					if(!Utils.isBlankOrNull(institute)){
						vEducationalData.setInstitute(institute);
					}
					String grade=eData.getGrade();
					if(!Utils.isBlankOrNull(grade)){
						vEducationalData.setGrade(grade);
					}
					
					if (eData.getYearOfPassing() != null) {
						vEducationalData.setYearOfPassing(eData.getYearOfPassing());
					}
					if(eData.getFromYear()!=null){
						vEducationalData.setFromYear(eData.getFromYear());
					}
					vEducationalData.setRemarks(eData.getRemarks());
					vEducationalDetails.add(vEducationalData);
				}
				
			}
			
			vapplicantData.setEducationalDetails(vEducationalDetails);
			
			ArrayList<EmploymentHistoryData> employmentHistoryDetails=applicantData.getEmploymentHistoryDetails();
			ArrayList<VemploymentHistoryData> vemploymentHistoryDetails=new ArrayList<VemploymentHistoryData>();
			VemploymentHistoryData vemploymentHistoryData=null;
			if (employmentHistoryDetails != null && employmentHistoryDetails.size() > 0) {
				int sz = employmentHistoryDetails.size();
				
				for (int i = 0; i < sz; i++) {
					vemploymentHistoryData=new VemploymentHistoryData();
					EmploymentHistoryData empData = (EmploymentHistoryData) employmentHistoryDetails.get(i);								
					String designation=empData.getDesignationName();
					if(!Utils.isBlankOrNull(designation)){
						vemploymentHistoryData.setDesignationName(designation);
					}
					int employerId=empData.getEmployerId();
					if(employerId!=0){
						vemploymentHistoryData.setEmployerId(String.valueOf(employerId));
					}
					int designationId=empData.getDesignationId();
					if(designationId!=0){
						vemploymentHistoryData.setDesignationId(String.valueOf(designationId));
					}
						String employerexp=empData.getEmployerExperience();
						if(!Utils.isBlankOrNull(employerexp)){
							vemploymentHistoryData.setEmployerExperience(empData.getEmployerExperience());
						}
					Date toDate=empData.getEmployerToDate();
					if(toDate!=null){
						vemploymentHistoryData.setEmployerToDate(Utils.getDateConvertedToString(toDate, Utils.regMMMYYYYFormat));
					}
					Date fromDate=empData.getEmployerFromDate();
					if(fromDate!=null){
						vemploymentHistoryData.setEmployerFromDate(Utils.getDateConvertedToString(fromDate, Utils.regMMMYYYYFormat));
					}
					String employerName=empData.getEmployerName();
					if(!Utils.isBlankOrNull(employerName)){
						vemploymentHistoryData.setEmployerName(employerName);
					}
					vemploymentHistoryData.setGrossSalary(empData.getGrossSalary());
					vemploymentHistoryData.setAllowance(empData.getAllowance());
					vemploymentHistoryData.setDutiesInvolved(empData.getDutiesInvolved());
					vemploymentHistoryData.setReasonForLeaving(empData.getReasonForLeaving());
					vemploymentHistoryDetails.add(vemploymentHistoryData);
				}
				
			}
			
			vapplicantData.setEmploymentDetails(vemploymentHistoryDetails);
			String skills=applicantData.getSkillIds();
			if(!Utils.isBlankOrNull(skills)){
				vapplicantData.setSkills(skills);
			}
			
			String workExp = (String) applicantData.getApplicantTotalExperience();
			
			if (!Utils.isBlankOrNull(workExp)) {
								vapplicantData.setTotalExperience(workExp);
				}
					
				
			String applicantPositionId=applicantData.getApplicantPositionId();

			if (!Utils.isBlankOrNull(applicantPositionId)) {
				vapplicantData.setApplicantPositionId(applicantPositionId);
			}
			
			String applicantStepId=applicantData.getApplicantStepId();

			if (!Utils.isBlankOrNull(applicantStepId)) {
				vapplicantData.setApplicantStepId(applicantStepId);
			}
			String dateUploaded=DateUtils.getSystemDateFormat(applicantData.getResumeDateUpdated());
			if (!Utils.isBlankOrNull(dateUploaded)) {
				vapplicantData.setDateUploaded(dateUploaded);
			}
			

		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vapplicantData;
	}
	private SimpleDataObject getApplicantDisplaySimpleData(String applicantId, String userId, String sourceId) {
		DBPreparedQuery dq = null;
		SimpleDataObject sdo = null;
		try {
			dq = new DBPreparedQuery("dVendorApplicantManager_GetApplicantDisplayData");
			dq.setId(1, ApplicantConstants.APPLICANT_JOINED);
			dq.setId(2, ApplicantConstants.APPLICANT_NOT_JOINED);
			dq.setId(3, userId);
			dq.setId(4, sourceId);
			dq.setId(5, applicantId);
			sdo = (SimpleDataObject) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the sdo for applicant", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}

	public String getApplicantHtmlResume(String resumePath, String applicantId) {
		String content = "";
		try {
			String absolutePath = Utils.concatFilePath(DocumentConstants.documentsPath, resumePath);
			FileHandler fileHandler = new FileHandler();
			content = fileHandler.getTextFileContent(absolutePath, null);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return content;
	}

	public String getApplicantInteractionsXML(ArrayList<SimpleDataObject> interactions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			if (interactions != null) {
				wr.startDocument();
				wr.startElement("rows");
				for (int indx = 0; indx < interactions.size(); indx++) {
					SimpleDataObject data = (SimpleDataObject) interactions.get(indx);
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", "" + (indx + 1));
					wr.startElement("", "row", "", atr);

					String interactionId = (data.getAttribute("interactionId") == null) ? "0" : ("" + data.getAttribute("interactionId"));
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionId");
					wr.startElement("", "userdata", "", atr);
					wr.characters(interactionId);
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionType");
					wr.startElement("", "userdata", "", atr);
					wr.characters("" + data.getAttribute("interactionType"));
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "type");
					wr.startElement("", "userdata", "", atr);
					wr.characters((String) SelectionProcessConstants.INTERACTION_TYPES.get("" + data.getAttribute("interactionType")));
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters("" + data.getAttribute("interactionType"));
					wr.endElement("cell");

					String interactionSubject = (data.getString("interactionSubject") == null) ? "" : "" + data.getString("interactionSubject");
					if (Utils.isBlankOrNull(interactionSubject)) {
						interactionSubject = "&nbsp;";
					}
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "subject");
					wr.startElement("", "userdata", "", at);
					wr.characters(wr.doubleEscape(interactionSubject));
					wr.endElement("userdata");

					if (interactionSubject.length() > 37) {
						interactionSubject = interactionSubject.substring(0, 34) + "...";
					}
					wr.startElement("cell");
					if (data.getAttribute("interactionType").equals(SelectionProcessConstants.INTERACTION_NOTE)) {
						wr.characters("<a href=\"#\" onclick=\"javascript:viewDetails(" + (indx + 1) + ");return false;\">" + wr.doubleEscape(interactionSubject) + "</a>");
					} else {
						wr.characters(wr.doubleEscape(interactionSubject));
					}
					wr.endElement("cell");
					String interactionDate = "";
					if (data.getAttribute("interactionDate") != null) {
						try {
							interactionDate = DateUtils.getSystemDateTimeFormat(data.getDate("interactionDate"));
						} catch (Exception e) {
							e.printStackTrace();
							interactionDate = "UNKNOWN";
						}
					}
					wr.startElement("cell");
					wr.characters(interactionDate);
					wr.endElement("cell");

					wr.endElement("row");
				}
				wr.endElement("rows");
				wr.endDocument();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return sWr.toString();

	}

	public ArrayList<SimpleDataObject> getApplicantInteractions(String applicantId, String userId, String sourceId) {
		ArrayList<SimpleDataObject> interactions = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dVendorApplicantManager_GetApplicantInteractions");
			dq.setInt(1, SelectionProcessConstants.INTERACTION_INTERVIEW);
			dq.setString(2, SelectionProcessConstants.STEP_JOIN);
			dq.setString(3, SelectionProcessConstants.STEP_REJECT);
			dq.setString(4, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(5, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(6, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(7, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(8, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(9, GlobalConstants.ENABLED);
			dq.setInt(10, PositionConstants.STEP_SCHEDULED);
			dq.setId(11, userId);
			dq.setId(12, sourceId);
			dq.setId(13, applicantId);
			dq.setId(14, applicantId);
			dq.setInt(15, SelectionProcessConstants.INTERACTION_NOTE);
			dq.setId(16, userId);
			interactions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting interactions", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return interactions;

	}
	public ApplicantData convertVApplicantDataToApplicantData(VapplicantData vapplicantData){
		ApplicantData applicantData=null;
		applicantData=new ApplicantData();
		if(vapplicantData!=null){
			String applicantId=vapplicantData.getApplicantId();
			String applicantName=vapplicantData.getApplicantName();
			String positionId=vapplicantData.getApplicantPositionId();
			String originalResumePath=vapplicantData.getOriginalResumePath();
			Date applicantWorkingSince=vapplicantData.getApplicantWorkingSince();
			String positionTitle=vapplicantData.getPositionTitle();
			String exp=vapplicantData.getTotalExperience();
			String currentCtc=vapplicantData.getCurrentCTC();
			String expectedCtc=vapplicantData.getExpectedCTC();
			String email1=vapplicantData.getApplicantEmail1();
			String email2=vapplicantData.getApplicantEmail2();
			String cellPhone=vapplicantData.getApplicantCellPhone();
			String homePhone=vapplicantData.getApplicantHomePhone();
			String workPhone=vapplicantData.getApplicantWorkPhone();
			String passport=vapplicantData.getPassport();
			String noticePeriod=vapplicantData.getNoticePeriod();
			String skills = vapplicantData.getSkills();
			String sourceTitle=vapplicantData.getSource();
			if(!Utils.isBlankOrNull(sourceTitle)){
				applicantData.setApplicantSourceTitle(sourceTitle);
			}
			if(!Utils.isBlankOrNull(skills)){
				applicantData.setSkillIds(skills);
			}
			if(!Utils.isBlankOrNull(currentCtc)){
				applicantData.setCurrentCTC(currentCtc);
			}
			if(!Utils.isBlankOrNull(expectedCtc)){
				applicantData.setExpectedCTC(expectedCtc);
			}
			if(!Utils.isBlankOrNull(email1)){
				applicantData.setApplicantEmail1(email1);
			}
			if(!Utils.isBlankOrNull(email2)){
				applicantData.setApplicantEmail2(email2);
			}
			if(!Utils.isBlankOrNull(cellPhone)){
				applicantData.setApplicantCellPhone(cellPhone);
			}
			if(!Utils.isBlankOrNull(homePhone)){
				applicantData.setApplicantHomePhone(homePhone);
			}
			if(!Utils.isBlankOrNull(workPhone)){
				applicantData.setApplicantWorkPhone(workPhone);
			}
			if(!Utils.isBlankOrNull(passport)){
				applicantData.setPassportNumber(passport);
			}
			if(!Utils.isBlankOrNull(exp)){
				applicantData.setApplicantExperience(exp);
			}
			if(!Utils.isBlankOrNull(applicantId)){
				applicantData.setApplicantId(applicantId);
			}
			if(!Utils.isBlankOrNull(applicantName)){
				applicantData.setApplicantName(applicantName);
			}
			if(!Utils.isBlankOrNull(originalResumePath)){
				applicantData.setApplicantOriginalResumePath(originalResumePath);
			}
			if(!Utils.isBlankOrNull(positionId)){
				applicantData.setApplicantPositionId(positionId);
			}
			if(!Utils.isBlankOrNull(originalResumePath)){
				applicantData.setApplicantOriginalResumePath(originalResumePath);
			}
			if(!Utils.isBlankOrNull(positionTitle)){
				applicantData.setApplicantPositionTitle(positionTitle);
			}
		
			if(!Utils.isBlankOrNull(noticePeriod)){
				applicantData.setNoticePeriod(noticePeriod);
			}
			
			if(!Utils.isBlankOrNull(positionTitle)){
				applicantData.setApplicantPositionTitle(positionTitle);
			}
			String dateOfBirth=vapplicantData.getDateOfBirth();
			if(!Utils.isBlankOrNull(dateOfBirth)){
				applicantData.setDateOfBirth(Utils.convertToSQLDate(dateOfBirth, Utils.regEUDateFormat));
			}
			applicantData.setResumeTypeId(vapplicantData.getResumeType());
			
			java.sql.Date dtWorkingFrom = null;
			if (!Utils.isBlankOrNull(vapplicantData.getTotalExperience())){
				Calendar calendar = Calendar.getInstance();
				if(vapplicantData.getTotalExperience().contains(".")){
					String[] yysmms = vapplicantData.getTotalExperience().split("\\.");
					calendar.add(Calendar.YEAR, -Integer.parseInt(yysmms[0]));
					calendar.add(Calendar.MONTH, -Integer.parseInt(yysmms[1]));
				}else{
					calendar.add(Calendar.YEAR, -Integer.parseInt(vapplicantData.getTotalExperience()));
				}
				dtWorkingFrom = Utils.convertDateToSQLDate(calendar.getTime());
				applicantData.setApplicantWorkingSince(dtWorkingFrom);
			}
			applicantData.setApplicantCity(vapplicantData.getCurrentLocation());
			applicantData.setNoticePeriod(vapplicantData.getNoticePeriod());
			
			ArrayList<EducationalData> educationalDetails=new ArrayList<EducationalData>();
			
			ArrayList<VeducationalData> eEducationalDetails=vapplicantData.getEducationalDetails();
			MastersManager mastermanager=new MastersManager();
			if (eEducationalDetails != null && eEducationalDetails.size() > 0) {
				int sz = eEducationalDetails.size();
				String[] eduYop = new String[sz];
				String[] eduInstitute = new String[sz];
				String[] eduDegree = new String[sz];
				String[] eduMajor = new String[sz];
				String[] eduGrades = new String[sz];
				String[] remarks = new String[sz];
				String[] fromYear = new String[sz];
				for (int i = 0; i < sz; i++) {
					EducationalData eEducationalData=new EducationalData();
					VeducationalData eData = (VeducationalData) eEducationalDetails.get(i);
					String degreeTitle=eData.getDegreeTitle();
					String degreeId=null;
					if(!Utils.isBlankOrNull(degreeTitle)){
						try{
							eEducationalData.setDegreeId(Integer.parseInt(degreeTitle));
						}catch (NumberFormatException nfe){
							
						}
//						eEducationalData.setDegreeTitle(degreeTitle);
//						degreeId=mastermanager.getDegreeId(degreeTitle);
//						if(Utils.isInteger(degreeId)){
//							eEducationalData.setDegreeId(Integer.parseInt(degreeId));
//						}
					}
					eEducationalData.setInstitute(eData.getInstitute());
					
					
					eEducationalData.setGrade(eData.getGrade());
					if (eData.getFromYear()!=null){
						eEducationalData.setFromYear(Utils.convertDateToSQLDate(eData.getFromYear()));
					}
					if (eData.getYearOfPassing()!=null){
						eEducationalData.setYearOfPassing(Utils.convertDateToSQLDate(eData.getYearOfPassing()));
					}
					String branchTitle=eData.getMajor();
					String branchId=null;
					if(!Utils.isBlankOrNull(branchTitle)){
						try{
							eEducationalData.setMajorId(Integer.parseInt(branchTitle));
						}catch (NumberFormatException nfe){
							
						}
//						branchId=mastermanager.getBranchId(degreeTitle);
//						if(Utils.isInteger(branchId)){
//							eEducationalData.setMajorId(Integer.parseInt(branchId));
//						}
					}
					eEducationalData.setRemarks(Utils.isBlankOrNull(eData.getRemarks())?"":eData.getRemarks());
					educationalDetails.add(eEducationalData);
				}
				
			}
			
			applicantData.setEducationalDetails(educationalDetails);
			ArrayList<EmploymentHistoryData> employmentHistoryDetails=new ArrayList<EmploymentHistoryData>();
			
			ArrayList<VemploymentHistoryData> eemploymentHistoryDetails=vapplicantData.getEmploymentDetails();
			
			
			if (eemploymentHistoryDetails != null && eemploymentHistoryDetails.size() > 0) {
				int sz = eemploymentHistoryDetails.size();
				for (int i = 0; i < sz; i++) {
					EmploymentHistoryData eemploymentHistoryData=new EmploymentHistoryData();
					VemploymentHistoryData empData = (VemploymentHistoryData) eemploymentHistoryDetails.get(i);								
					String employerFromDate = empData.getEmployerFromDate();
					if (employerFromDate != null) {
						
						eemploymentHistoryData.setEmployerFromDate(Utils.convertToSQLDate(employerFromDate, Utils.regMMMYYYYFormat));
					}
					String employerId=empData.getEmployerId();
					if(!Utils.isBlankOrNull(employerId)){
						employerId=employerId.trim();
						eemploymentHistoryData.setEmployerName(employerId);
					}
					
					String designationId=empData.getDesignationId();
					if(!Utils.isBlankOrNull(designationId)){
						designationId=designationId.trim();
						eemploymentHistoryData.setDesignationName(designationId);
						
					}
					
						String empExp=empData.getEmployerExperience();
						if(!Utils.isBlankOrNull(empExp)){
							empExp=changeExpInYear(empExp);
							eemploymentHistoryData.setEmployerExperience(empExp);
						}
						
					eemploymentHistoryData.setEmployerToDate(Utils.convertToSQLDate(empData.getEmployerToDate(),Utils.regMMMYYYYFormat));
					eemploymentHistoryData.setGrossSalary(empData.getGrossSalary());
					eemploymentHistoryData.setAllowance(empData.getAllowance());
					eemploymentHistoryData.setDutiesInvolved(empData.getDutiesInvolved());
					eemploymentHistoryData.setReasonForLeaving(empData.getReasonForLeaving());
					employmentHistoryDetails.add(eemploymentHistoryData);
				}
				
			}
			
			applicantData.setEmploymentHistoryDetails(employmentHistoryDetails);
			ArrayList<VcustomFieldData> wCustomFieldList = vapplicantData.getCustomFields();
			ArrayList<CustomFieldData> customFieldList = new ArrayList<CustomFieldData>();
			if (!Utils.isListEmptyOrNull(wCustomFieldList)){
				for (VcustomFieldData wcustomFieldData:wCustomFieldList){
					CustomFieldData cData = new CustomFieldData();
					cData.setFieldName(wcustomFieldData.getFieldName());
					cData.setFieldDisplayName(wcustomFieldData.getFieldDisplayName());
					cData.setFieldRequired(wcustomFieldData.getFieldRequired());
					cData.setFieldValues(wcustomFieldData.getFieldValues());
					cData.setToValues(wcustomFieldData.getToValues());
					cData.setFieldAttributes(wcustomFieldData.getFieldAttributes());
					cData.setFieldEntityType(wcustomFieldData.getFieldEntityType());
					cData.setFieldOtherAttributes(wcustomFieldData.getFieldOtherAttributes());
					cData.setFieldId(wcustomFieldData.getFieldId());
					cData.setFieldInputAllowed(wcustomFieldData.getFieldInputAllowed());
					cData.setFieldOptions(wcustomFieldData.getFieldOptions());
					cData.setFieldRank(wcustomFieldData.getFieldRank());
					cData.setFieldSearchable(wcustomFieldData.getFieldSearchable());
					cData.setFieldType(wcustomFieldData.getFieldType());
					cData.setFieldDefaultValue(wcustomFieldData.getFieldDefaultValue());
					cData.setFieldStringValue(wcustomFieldData.getFieldStringValue());
					cData.setFieldNumberValue(wcustomFieldData.getFieldNumberValue());
					cData.setFieldDateValue(wcustomFieldData.getFieldDateValue());
					cData.setTableId(wcustomFieldData.getTableId());
					cData.setTableName(wcustomFieldData.getTableName());
					customFieldList.add(cData);
				}
			}
			applicantData.setCustomFields(customFieldList);
		}
		return applicantData;
	}
	public static String changeExpInYear(String experience ){
		  String [] s=experience.split(" ");
		  if (s.length>0 && s[0].indexOf("yrs")!=-1){
			  s[0]=s[0].substring(0, s[0].indexOf("yrs"));
			  experience = s[0];
		  }
		  if (s.length>1 && s[1].indexOf("months")!=-1){
			  s[1]=s[1].substring(0, s[1].indexOf("months"));
			  experience = experience +"."+s[1] ;
		  }
		  return experience;
	}
	
}
