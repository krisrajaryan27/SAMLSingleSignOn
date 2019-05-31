/**
 * 
 */
package com.talentPool.applicant.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.form.ApplicantRegistrationForm;
import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.audit.action.AuditAction;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldCell;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.dataobject.CustomFieldRow;
import com.talentPool.custom.dataobject.CustomFieldTable;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldDataProcessor;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.employeeservice.utils.WebsiteUnMarshaller;
import com.talentPool.masters.dataobject.LocationData;
import com.talentPool.masters.manager.LocationManager;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.parser.converter.WordToHtmlConverter;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.websiteservice.dataobject.WapplicantData;

/**
 * @author shivprasad
 * 
 */
public class ApplicantRegistrationAction extends TPDispatchAction {
	public ActionForward register(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if("capita".equalsIgnoreCase(TPApplicationProperties.getProperty("walk_in.module.client"))) {
			return capitaStep1(mapping, actionForm, request, response);
		}
		return defaultStep0(mapping, actionForm, request, response);
	}
	
	/********************Start::Default Actions********************************************/
	public ActionForward defaultStep1(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "defaultStep1";
		try {			
			MastersManager mastersManager = new MastersManager();
			ArrayList<SimpleDataObject> sourceTypes = (ArrayList<SimpleDataObject>)mastersManager.getAllSourceTypes();
			String jsArraySourceTypes = CommonUtils.getListJavaScriptArrayWithProperties(sourceTypes, "sourceTypeId", "sourceType");
			request.setAttribute("jsArraySourceTypes", jsArraySourceTypes);
			// set custom fields to request
			ArrayList<CustomFieldData> customFields = null;
			CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			customFields = customFieldDataProcessor.setCustomFieldValuesFromRequest(request, customFields);
			request.setAttribute("customFields", customFields);//end custom fields
			
			//set custom tables to request 
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
								cell.setData(tData);
								row.addCell(cell);
								rows.add(row);
								CustomFieldRow row1 = new CustomFieldRow();
								CustomFieldCell cell1 = new CustomFieldCell();
								CustomFieldData tabData = (CustomFieldData)tData.clone();
								tabData.setFieldStringValue("");
								String[] fieldValues = new String[1];
								fieldValues[0] = "";
								tabData.setFieldValues(fieldValues);
								cell1.setData(tabData);
								cell1.setValue("");
								row1.addCell(cell1);
								rows.add(row1);
								isFirstIteration = false;
							}else{
								CustomFieldRow row = rows.get(0);
								CustomFieldCell cell = new CustomFieldCell();
								cell.setValue(tData.getFieldDisplayName());
								cell.setData(tData);
								row.addCell(cell);
								CustomFieldRow row1 = rows.get(1);
								CustomFieldCell cell1 = new CustomFieldCell();
								CustomFieldData tabData = (CustomFieldData)tData.clone();
								tabData.setFieldStringValue("");
								String[] fieldValues = new String[1];
								fieldValues[0] = "";
								tabData.setFieldValues(fieldValues);
								cell1.setData(tabData);
								cell1.setValue("");
								row1.addCell(cell1);
							}
						}
					}
					cft.setRows(rows);
					customTables.add(cft);
				}
				request.setAttribute("customTables", customTables);
			
			}//end custom tables
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting source types", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getSourcesInJSArrayOfSourceType(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			String sourceTypeId = request.getParameter("sourceTypeId");
			
			MastersManager mastersManager = new MastersManager();
			ArrayList<SimpleDataObject> sources = (ArrayList<SimpleDataObject>)mastersManager.getSourcesOfSourceType(sourceTypeId);
			xmlFile = CommonUtils.getListJavaScriptArrayWithProperties(sources, "sourceId", "sourceTitle");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting published positions for a location", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward defaultStep2(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "defaultStep2";
		try {
			
			ApplicantRegistrationForm applicantRegistrationForm=(ApplicantRegistrationForm)(actionForm);
			String error = "";
			DocumentData documentData=null;
			ArrayList<CustomFieldData> customFields = null;
			CustomFieldManager customFieldManager = new CustomFieldManager();
			CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
				customFields = customFieldDataProcessor.setCustomFieldValuesFromRequest(request, customFields);
			}
			
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
				ArrayList<CustomFieldData> tabularCustomFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
				tabularCustomFields = customFieldDataProcessor.setTabularCustomFieldValuesFromRequest(request, tabularCustomFields);
				customFields.addAll(tabularCustomFields);
			}
			
			try {
				// get all attachments for this message
				FormFile formFile = applicantRegistrationForm.getAttachedFile();
				if (formFile != null) {
					if (formFile.getFileSize() <= 0) {
						error = TPLabels.getLabel("inbox.error.could_not_read_file");
					}
					FormFileData formFileData = new FormFileData(formFile.getFileName(), formFile.getFileSize(), formFile.getContentType(), formFile.getInputStream());
					if (Utils.isBlankOrNull(error)) {
						DocumentUploader documentUploader = new DocumentUploader();
						 documentData= documentUploader.saveFormFile(formFileData);
					}
				}
			} catch (FileUploadException fe) {
				error = TPLabels.getLabel("inbox.error.could_not_read_file");
			} catch (InvalidMimeTypeException e) {
				TPLogger.getLogger().error("Error while uploading user document", e);
				error = TPLabels.getLabel("upload_document.error.invalid_mime_type");
			}catch (Exception e) {
				TPLogger.getLogger().error("Error while uploading resume from HD", e);
			}
			
			ApplicantData data = getDefaultApplicantDataConstructed(request);
			if(documentData!=null){
				data=setResumePath(data,documentData.getRelativeFilePath());
			}
			if(customFields!=null&&!customFields.isEmpty()){
				data.setCustomFields(customFields);
			}
			ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
			ArrayList<ApplicantDuplicateSearchData> duplicates = applicantDuplicateChecker.getInternalDuplicateChecked(data.getApplicantId(), data.getApplicantName(),
					data.getApplicantEmail1(), data.getApplicantEmail2(), data.getApplicantCellPhone(), null);			
			if (duplicates != null && duplicates.size() > 0) {
				forward = "defaultDuplicateDetected";				
			} else {
				ApplicantManager applicantManager = new ApplicantManager();
				String clientIpAddr = getClientIpAddr(request);
				
				String applicantId = applicantManager.addApplicant(data, request.getParameter("skillIds"), clientIpAddr);
				
				AuditAction auditAction = new AuditAction();
				auditAction.insertAuditInfo(TPLabels.getLabel("common.applicant"), AuditConstants.TYPE_ADDED, 
						applicantId, AuditConstants.AUDIT_CANDIDATE, data.getUserId(), null,
						null, null, true, clientIpAddr);
				String positionId = request.getParameter("positionId");
				if(!Utils.isBlankOrNull(positionId)) {
					SelectionProcessManager selectionProcessManager = new SelectionProcessManager();				
					selectionProcessManager.shortListApplicant(applicantId, positionId, null, data.getUserId(), true);
				}
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving applicant data", e);
			ActionErrors errors = new ActionErrors();
			errors.add("applicant_registration.error.save_data", new ActionError("applicant_registration.error.save_data"));
			saveErrors(request, errors);
			return defaultStep1(mapping, actionForm, request, response);			
		}
		return mapping.findForward(forward);
	}
	
	private ApplicantData setResumePath(ApplicantData aData,String originalResumePath){
		aData.setApplicantOriginalResumePath(originalResumePath);
		String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, aData.getApplicantOriginalResumePath());
		
		
		DocumentUploader documentUploader = new DocumentUploader();
		String filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);

		String applicantOriginalResumePath = "";
		if (Utils.isBlankOrNull(filePathFinal)) {
			filePathFinal = filePath;
		}
		if (filePathFinal.length() > DocumentConstants.documentsPath.length()) {
			applicantOriginalResumePath = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
		}					

		// get text content of the file
		GenericConverter conv = new GenericConverter();
		String textContent = conv.convert(filePathFinal);

	
		WordToHtmlConverter converter = new WordToHtmlConverter();
		converter.convertToHtml(filePath);
		filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);
		if(!Utils.isBlankOrNull(filePathFinal) && filePathFinal.length() > DocumentConstants.documentsPath.length()) {
			filePathFinal = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
		} else {
			filePathFinal = applicantOriginalResumePath;
		}
		
		aData.setApplicantOriginalResumePath(filePathFinal);
		aData.setApplicantOriginalDocPath(applicantOriginalResumePath);
		aData.setApplicantTextResume(textContent);
		return aData;
	}
	private ApplicantData getDefaultApplicantDataConstructed(HttpServletRequest request) {
		ApplicantData aData = new ApplicantData();
		MastersManager masterManager=new MastersManager();
		Integer sourceId=masterManager.getExistingSourceID("Campus Portal");
		aData.setApplicantSourceId(sourceId);		
		aData.setApplicantName(request.getParameter("name"));
		aData.setApplicantCity(request.getParameter("currentLocation"));
		aData.setApplicantEmail1(request.getParameter("email1"));
		aData.setApplicantEmail2(request.getParameter("email2"));
		aData.setApplicantWorkPhone(request.getParameter("phone1"));
		aData.setApplicantHomePhone(request.getParameter("phone2"));
		aData.setApplicantCellPhone(request.getParameter("mobile"));
		aData.setApplicantCurrentEmployer(request.getParameter("currentEmp"));
		aData.setCurrentCTC(request.getParameter("currentCTC"));
		aData.setExpectedCTC(request.getParameter("expectedCTC"));
		aData.setNoticePeriod(request.getParameter("noticePeriod"));
		aData.setApplicantDeclaration(request.getParameter("declaration"));
		aData.setApplicantOriginalResumePath("");
		aData.setApplicantOriginalDocPath("");
		aData.setUserId("1");
		java.sql.Date dateOfBirth = null;
		dateOfBirth= Utils.convertToSQLDate( request.getParameter("dateOfBirth"), Utils.regEUDateFormat);
		if(dateOfBirth!=null){
			aData.setDateOfBirth(dateOfBirth);
		}
		java.sql.Date dtWorkingFrom = null;
		if ("0".equals(request.getParameter("fresher"))) {
			dtWorkingFrom = Utils.convertToSQLDate("1" + Utils.dateDescSeparator + request.getParameter("workingSince"), Utils.regDDMMMYYYYFormat);
		}
		aData.setApplicantWorkingSince(dtWorkingFrom);
				
		ArrayList<EducationalData> educationalDetails = new ArrayList<EducationalData>();
		String eduRowIds = request.getParameter("eduRowIds");
		if(!Utils.isBlankOrNull(eduRowIds)) {
			String[] temp = eduRowIds.split(",");
			for(int i = 0; i < temp.length; i++) {
				//String strtDat = request.getParameter("startDate"+temp[i]);
				//String endDat = request.getParameter("endDate"+temp[i]);
				java.sql.Date strtDat = null;
				java.sql.Date endDat = null;
				strtDat = Utils.convertToSQLDate("1" + Utils.dateDescSeparator + request.getParameter("startDate"+temp[i]), Utils.regDDMMMYYYYFormat);
				endDat = Utils.convertToSQLDate("1" + Utils.dateDescSeparator + request.getParameter("endDate"+temp[i]), Utils.regDDMMMYYYYFormat);
				String institute = request.getParameter("institute"+temp[i]);
				String degree = request.getParameter("degree"+temp[i]);
				String university = request.getParameter("university"+temp[i]);
				String branch = request.getParameter("branch"+temp[i]);
				String typeOfProgram = request.getParameter("typeOfProgram"+temp[i]);
				String grade = request.getParameter("grade"+temp[i]);
				if (!(Utils.isBlankOrNull(institute) && "-1".equals(degree) && Utils.isBlankOrNull(university) && "-1".equals(branch) && "-1".equals(typeOfProgram) && Utils.isBlankOrNull(grade))) {
					EducationalData eData = new EducationalData();
					if (null != strtDat) {
						eData.setStartDate(strtDat);
					} else {
						eData.setStartDate(null);
					}
					if (null != endDat) {
						eData.setEndDate(endDat);
					} else {
						eData.setEndDate(null);
					}
					eData.setInstitute(institute);
					eData.setDegreeId(Integer.parseInt(degree));
					eData.setUniversity(university);
					eData.setMajorId(Integer.parseInt(branch));
					eData.setTypeOfProgram(typeOfProgram);
					eData.setGrade(grade);
					educationalDetails.add(eData);
				}
			}
		}
		aData.setEducationalDetails(educationalDetails);
		
		
		
		ArrayList<EmploymentHistoryData> empDetails = new ArrayList<EmploymentHistoryData>();
		String empRowIds = request.getParameter("empRowIds");
		if(!Utils.isBlankOrNull(empRowIds)) {
			String[] temp = empRowIds.split(",");
			for(int i = 0; i < temp.length; i++) {
				java.sql.Date empStartDate = null;
				java.sql.Date empEndDate = null;
				empStartDate = Utils.convertToSQLDate("1" + Utils.dateDescSeparator + request.getParameter("empStartDate"+temp[i]), Utils.regDDMMMYYYYFormat);
				empEndDate = Utils.convertToSQLDate("1" + Utils.dateDescSeparator + request.getParameter("empEndDate"+temp[i]), Utils.regDDMMMYYYYFormat);
				String employer = request.getParameter("employer"+temp[i]);
				String designation = request.getParameter("designation"+temp[i]);
				String empType = request.getParameter("empType"+temp[i]);
				String location = request.getParameter("location"+temp[i]);
				String country = request.getParameter("country"+temp[i]);
				String reasonForLeaving = request.getParameter("reasonForLeaving"+temp[i]);
				String lastCtc = request.getParameter("lastCtc"+temp[i]);
				if (!(Utils.isBlankOrNull(empType) && "-1".equals(country) && Utils.isBlankOrNull(location)&& Utils.isBlankOrNull(lastCtc) && Utils.isBlankOrNull(designation))) {
					EmploymentHistoryData eData = new EmploymentHistoryData();
					if (null != empStartDate) {
						eData.setEmployerFromDate(empStartDate);
					} else {
						eData.setEmployerFromDate(null);
					}
					if (null != empEndDate) {
						eData.setEmployerToDate(empEndDate);
					} else {
						eData.setEmployerToDate(null);
					}
					if(!Utils.isBlankOrNull(employer)){
						eData.setEmployerId(Integer.parseInt(employer));
					}else{
						eData.setEmployerId(-1);
					}
					if(!Utils.isBlankOrNull(designation)){
						eData.setDesignationName(designation);
					}else{
						eData.setDesignationId(-1);
					}
					
					eData.setEmpType(empType);
					eData.setReasonForLeaving(reasonForLeaving);
					eData.setLastCtc(lastCtc);
					eData.setCountry(country);
					eData.setLocation(location);
					empDetails.add(eData);
				}
			}
		}
		aData.setEmploymentHistoryDetails(empDetails);
		aData.setEducationalDetails(educationalDetails);
		
		return aData;
	}
	
	public ActionForward defaultStep0(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "defaultStep0";
		try {
		/*	LocationManager locationManager = new LocationManager();
			ArrayList<LocationData> locations = (ArrayList<LocationData>) locationManager.getAllLocations();
			ArrayList<String> locationIds = new ArrayList<String>();
			ArrayList<String> locationNames = new ArrayList<String>();
			CommonUtils.populateIdsAndNames(locations, locationIds, locationNames, "locationId", "locationName", null);
			String strLocations = CommonUtils.getListJavaScriptArray(locationIds, locationNames);
			request.setAttribute("strLocations", strLocations);	*/		
			
			PositionManager positionManager = new PositionManager();
			List<PositionData> positions = positionManager.getAllPublishedForWalkInPositions();
			String strPositions = CommonUtils.getListJavaScriptArrayWithProperties((ArrayList<PositionData>)positions, "positionId", "positionTitle");
			request.setAttribute("strPositions", strPositions);	
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR", e);
		}
		return mapping.findForward(forward);
	}
	/********************End::Default Actions********************************************/
	
	/********************Start::Capita Specific Actions********************************************/
	public ActionForward capitaStep1(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "capitaStep1";
		try {
			LocationManager locationManager = new LocationManager();
			ArrayList<LocationData> locations = (ArrayList<LocationData>) locationManager.getAllLocations();
			ArrayList<String> locationIds = new ArrayList<String>();
			ArrayList<String> locationNames = new ArrayList<String>();
			CommonUtils.populateIdsAndNames(locations, locationIds, locationNames, "locationId", "locationName", null);
			String strLocations = CommonUtils.getListJavaScriptArray(locationIds, locationNames);
			request.setAttribute("strLocations", strLocations);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getPublishedForWalkInPositionsForALocation(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			String locationId = request.getParameter("locationId");
			
			PositionManager positionManager = new PositionManager();
			List<PositionData> positions = positionManager.getAllPublishedForWalkInPositionsForALocation(locationId);
			xmlFile = CommonUtils.getListJavaScriptArrayWithProperties((ArrayList<PositionData>)positions, "positionId", "positionTitle");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting published positions for a location", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward capitaStep2(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "capitaStep2";
		try {
			MastersManager mastersManager = new MastersManager();
			List<SimpleDataObject> sourceTypes = mastersManager.getAllSourceTypes();
			request.setAttribute("sourceTypes", sourceTypes);
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while getting source types", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getSourcesOfSourceType(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			String sourceTypeId = request.getParameter("sourceTypeId");
			
			MastersManager mastersManager = new MastersManager();
			List<SimpleDataObject> sources = mastersManager.getSourcesOfSourceType(sourceTypeId);
			xmlFile = buildStringOfSourceData(sources, "sourceId", "sourceTitle");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting published positions for a location", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward capitaStep3(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "capitaStep3";
		try {
			ArrayList<CustomFieldData> customFields = null;
			CustomFieldManager customFieldManager = new CustomFieldManager();
			CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			customFields = customFieldDataProcessor.setCustomFieldValuesFromRequest(request, customFields);
			ApplicantData data = getCapitaApplicantDataConstructed(request);
			data.setCustomFields(customFields);
			ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
			ArrayList<ApplicantDuplicateSearchData> duplicates = applicantDuplicateChecker.getInternalDuplicateChecked(data.getApplicantId(), data.getApplicantName(),
					data.getApplicantEmail1(), data.getApplicantEmail2(), data.getApplicantCellPhone(), customFields);			
			if (duplicates != null && duplicates.size() > 0) {
				forward = "duplicateDetected";				
				for (int i = 0; customFields != null && i < customFields.size(); i++) {
					CustomFieldData customFieldData = customFields.get(i);
					if(customFieldData.getFieldValues() != null && customFieldData.getFieldValues().length > 0) {
						request.setAttribute(customFieldData.getFieldName(), customFieldData.getFieldValues()[0]);
					}					
				}
			} else {
				ApplicantManager applicantManager = new ApplicantManager();
				String clientIpAddr = getClientIpAddr(request);
				String applicantId = applicantManager.addApplicant(data, null, clientIpAddr);
				AuditAction auditAction = new AuditAction();
				auditAction.insertAuditInfo(TPLabels.getLabel("common.applicant"), AuditConstants.TYPE_ADDED, 
						applicantId, AuditConstants.AUDIT_CANDIDATE, data.getUserId(), null,
						null, null, true, clientIpAddr);
				String positionId = request.getParameter("positionId");
				if(!Utils.isBlankOrNull(positionId)) {
					SelectionProcessManager selectionProcessManager = new SelectionProcessManager();				
					selectionProcessManager.shortListApplicant(applicantId, positionId, null, data.getUserId(), true);
				}				
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving applicant data", e);
			ActionErrors errors = new ActionErrors();
			errors.add("applicant_registration.error.save_data", new ActionError("applicant_registration.error.save_data"));
			saveErrors(request, errors);
			forward = "capitaStep2";
		}
		return mapping.findForward(forward);
	}

	private ApplicantData getCapitaApplicantDataConstructed(HttpServletRequest request) {
		ApplicantData data = new ApplicantData();		
		data.setApplicantName(request.getParameter("app_firstName") + " " + request.getParameter("app_lastName"));
		data.setApplicantCity(request.getParameter("app_emergencyCity"));
		data.setApplicantEmail1(request.getParameter("email"));
		data.setApplicantCellPhone(request.getParameter("phone1"));
		data.setApplicantHomePhone(request.getParameter("phone2"));
		data.setApplicantSourceId(Integer.parseInt(request.getParameter("source")));
		data.setApplicantCurrentEmployer(request.getParameter("currentEmp"));
		data.setCurrentCTC(request.getParameter("currentCTC"));
		
		if(!Utils.isBlankOrNull(request.getParameter("app_prevEmpFromDate"))) {			
			data.setApplicantWorkingSince(Utils.convertToSQLDate(request.getParameter("app_prevEmpFromDate"), Utils.regEUDateFormat));
		} else if(!Utils.isBlankOrNull(request.getParameter("app_currentEmpFromDate"))) {
			data.setApplicantWorkingSince(Utils.convertToSQLDate(request.getParameter("app_currentEmpFromDate"), Utils.regEUDateFormat));
		}
		data.setApplicantOriginalResumePath("");
		data.setUserId("1");
		ArrayList<EducationalData> educationData = new ArrayList<EducationalData>();
		EducationalData eduData = new EducationalData();
		eduData.setDegreeId(Integer.parseInt(request.getParameter("degree")));
		eduData.setInstitute(request.getParameter("institute"));
		educationData.add(eduData);
		data.setEducationalDetails(educationData);
		return data;
	}
	
	private String buildStringOfSourceData(List<SimpleDataObject> src, String paramId, String paramName) {
		StringBuffer sbId = new StringBuffer();
		StringBuffer sbName = new StringBuffer();
		if(src != null && src.size() > 0) {
			for(int i = 0; i < src.size(); i++) {
				SimpleDataObject sdo = src.get(i);
				String id = sdo.getString(paramId);
				String name = sdo.getString(paramName);
				if(sbId.length() > 0) {
					sbId.append(",");
				}
				if(sbName.length() > 0) {
					sbName.append(",");
				}
				sbId.append(id.replaceAll("'", "\\\\'"));
				sbName.append(name.replaceAll("'", "\\\\'"));
			}
			sbId.append("()");
			sbId.append(sbName);
		}		
		return sbId.toString();		
	}
	
	public WapplicantData sendResume(String attachedFileName, String originalFileName, String isParsingRequired) throws Exception {
		String result = "";
		WapplicantData wapplicantData = null;
		try {
			String appURL = TPApplicationProperties.getProperty("talentpool.url");
			URL url = new URL(appURL + "/importServlet.servlet");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, attachedFileName);
			String extension = filePath.substring(filePath.lastIndexOf("."), filePath.length());
			if(".html".equals(extension)) {
				conn.setRequestProperty("Content-Type", "text/html");
			} else if(".doc".equals(extension)) {
				conn.setRequestProperty("Content-Type", "application/msword");				
			} else if(".rtf".equals(extension)) {
				conn.setRequestProperty("Content-Type", "application/rtf");
			}			
			conn.addRequestProperty("fileName", attachedFileName);
			//conn.addRequestProperty("sessionId", sessionId);
			conn.addRequestProperty("mode", "uploadFileAndParse");
			conn.addRequestProperty("fromPortal", "webPortal");
			conn.addRequestProperty("isParsingRequired", isParsingRequired);
			//conn.addRequestProperty("emailBodyFile", emailBodyFile);
			//conn.addRequestProperty("commentUrl", commentUrl);
			
			if(".doc".equals(extension) || ".rtf".equals(extension) || ".docx".equals(extension)  || ".pdf".equals(extension)) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				readBinaryFile(filePath, outputStream);			
				
				conn.setRequestProperty("Content-Length", ""+outputStream.size());
				outputStream.writeTo(conn.getOutputStream());
				
				conn.getOutputStream().flush();
				conn.getOutputStream().close();				
			} else {
				String content = readFile(filePath);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(content);
				wr.flush();
				wr.close();
			}
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (result.equals("")) {
					result = line;
				} else {
					result += line;
				}
			}			
			rd.close();
			WebsiteUnMarshaller websiteUnMarshaller = new WebsiteUnMarshaller();
			wapplicantData = websiteUnMarshaller.unmarshallApplicantData(result);
			
//			String[] arrayResult = result.split(" ");
//			String htmlPath = arrayResult[1];
//			String path = htmlPath.substring(0, htmlPath.length()-5);
			
			
//			File file = new File(filePath);
//			if(file.exists()) {
//				file.delete();
//			}
		} catch (Exception e) {
			//writeToLog("sendFilesToServer==" + e.toString());
			result = "false";
		}
		
		return wapplicantData;
	}
	
	private static String readFile(String filePath) {
		StringBuffer contents = new StringBuffer();
		BufferedReader input = null;
		try {
			File file = new File(filePath);			
			if (file.exists()) {
				input = new BufferedReader(new FileReader(file));
				
				String line = null; // not declared within while loop
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append("\n");
				}				
			}
		} catch (Exception ex) {
			//writeToLog("readFile==" + ex.toString());
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				//writeToLog("Exception => " + e.getMessage());
			}
		}
		//writeToLog(filePath + "file not found");
		return contents.toString();
	}
	
	private static void readBinaryFile(String filePath, ByteArrayOutputStream outputStream) {
		InputStream input = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				input = new BufferedInputStream(new FileInputStream(filePath));
				
				byte[] buf = new byte[8 * 1024];
				int bytesRead = 0;
				while ((bytesRead = input.read(buf)) > 0) {				
					outputStream.write(buf, 0, bytesRead);
				}	
				outputStream.close();
			}			
		} catch (Exception ex) {
			//writeToLog("readFile==" + ex.toString());
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				//writeToLog("Exception => " + e.getMessage());
			}
		}		
	}
	
	/********************End::Capita Specific Actions********************************************/
}

