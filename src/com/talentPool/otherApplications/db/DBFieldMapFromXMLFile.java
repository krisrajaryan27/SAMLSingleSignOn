/**
 * 
 */
package com.talentPool.otherApplications.db;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.otherApplications.rest.model.ApplicantDetailModel;
import com.talentPool.otherApplications.rest.model.CustomFieldDetailModel;
import com.talentPool.otherApplications.rest.model.EducationDetailModel;
import com.talentPool.otherApplications.rest.model.EmploymentHistoryDetailModel;
import com.talentPool.otherApplications.rest.model.SalaryComponentDetailModel;

/**
 * @author Shantanu
 *
 */
public class DBFieldMapFromXMLFile {
			 
	public ApplicantDetailModel readDBFieldMap(){
		ApplicantDetailModel admFromXml = new ApplicantDetailModel();
		try {
			String filePath = Utils.concatFilePath(OtherApplicationConstants.customerXMLPath, OtherApplicationConstants.GREYTIP_QUERY_XML_FILE);
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);
			Element varElement = doc.getDocumentElement();
			JAXBContext context = JAXBContext.newInstance(ApplicantDetailModel.class); 
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<ApplicantDetailModel> applicantsDetail = unmarshaller.unmarshal(varElement,ApplicantDetailModel.class);
			admFromXml = applicantsDetail.getValue();
		} catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return admFromXml;
	}
	
	public Map<String, String> mapColumns(ApplicantDetailModel adm){
		DBFieldMapFromXMLFile dbmfx = new DBFieldMapFromXMLFile();
		Map<String, String> dbmap = new HashMap<String, String>();		
		try{
			ApplicantDetailModel admGTColumn = dbmfx.readDBFieldMap();			
			createDbMap(dbmap, admGTColumn.applicantId, convertString(adm.applicantId));
			createDbMap(dbmap, admGTColumn.applicantName, convertString(adm.applicantName));
			createDbMap(dbmap, admGTColumn.applicantCellPhone, convertString(adm.applicantCellPhone));
			//createDbMap(dbmap, admGTColumn.applicantCellPhoneIsInvalid, adm.applicantCellPhoneIsInvalid);
			createDbMap(dbmap, admGTColumn.applicantHomePhone, convertString(adm.applicantHomePhone));
			//createDbMap(dbmap, admGTColumn.applicantHomePhoneIsInvalid, adm.applicantHomePhoneIsInvalid);
			createDbMap(dbmap, admGTColumn.applicantWorkPhone, convertString(adm.applicantWorkPhone));
			//createDbMap(dbmap, admGTColumn.applicantWorkPhoneIsInvalid, adm.applicantWorkPhoneIsInvalid);
			createDbMap(dbmap, admGTColumn.applicantEmail1, convertString(adm.applicantEmail1));
			createDbMap(dbmap, admGTColumn.applicantEmail2, convertString(adm.applicantEmail2));
			createDbMap(dbmap, admGTColumn.applicantCity, convertString(adm.applicantCity));
			createDbMap(dbmap, admGTColumn.applicantCurrentEmployer, convertString(adm.applicantCurrentEmployer));
			createDbMap(dbmap, admGTColumn.dateOfBirth, convertDate(adm.dateOfBirth,Utils.regDDMMMYYFormat));
			createDbMap(dbmap, admGTColumn.passportNumber, convertString(adm.passportNumber));
			//createDbMap(dbmap, admGTColumn.applicantSourceId, adm.applicantSourceId);
			createDbMap(dbmap, admGTColumn.applicantSourceTitle, convertString(adm.applicantSourceTitle));
			createDbMap(dbmap, admGTColumn.applicantDateJoined, convertDate(adm.applicantDateJoined,Utils.redYYYYMMDDFormat));
			//createDbMap(dbmap, admGTColumn.applicantHRMSCode, "'"+adm.applicantHRMSCode+"'");
			//createDbMap(dbmap, admGTColumn.applicantJoined, adm.applicantJoined);
			//createDbMap(dbmap, admGTColumn.applicantOriginalDocPath, adm.applicantOriginalDocPath);
			//createDbMap(dbmap, admGTColumn.applicantOriginalResumePath, adm.applicantOriginalResumePath);
			createDbMap(dbmap, admGTColumn.applicantPositionId, adm.applicantPositionId);
			createDbMap(dbmap, admGTColumn.applicantSkills, convertString(adm.applicantSkills));
			createDbMap(dbmap, admGTColumn.applicantStatus, convertString(adm.applicantStatus));
			createDbMap(dbmap, admGTColumn.applicantStepId, convertString(adm.applicantStepId));
			createDbMap(dbmap, admGTColumn.applicantWorkingSince, convertString(adm.applicantWorkingSince));
			createDbMap(dbmap, admGTColumn.basicOffered, convertNumber(adm.basicOffered));
			createDbMap(dbmap, admGTColumn.ctcOffered, convertNumber(adm.ctcOffered));
			createDbMap(dbmap, admGTColumn.currentCTC, convertNumber(adm.currentCTC));
			createDbMap(dbmap, admGTColumn.designationOffered, convertString(adm.designationOffered));
			createDbMap(dbmap, admGTColumn.expectedCTC, convertString(adm.expectedCTC));
			createDbMap(dbmap, admGTColumn.levelOffered, convertString(adm.levelOffered));
			createDbMap(dbmap, admGTColumn.noticePeriod, convertString(adm.noticePeriod));
			createDbMap(dbmap, admGTColumn.positionStepLevel, convertString(adm.positionStepLevel));
			createDbMap(dbmap, admGTColumn.resumeType, convertString(adm.resumeType));
			///createDbMap(dbmap, admGTColumn.resumeTypeId, adm.resumeTypeId);
			//createDbMap(dbmap, admGTColumn.sourceTypeId, adm.sourceTypeId);
			createDbMap(dbmap, admGTColumn.stepScheduled, convertString(adm.stepScheduled));
			//createDbMap(dbmap, admGTColumn.vendorId, adm.vendorId);
			//createDbMap(dbmap, admGTColumn.employeeCode, adm.employeeCode);
			
			createCustomFieldDBmap(dbmap,admGTColumn.customFields,adm.customFields);
			createSalaryComponentFieldDBmap(dbmap,admGTColumn.salaryComponents,adm.salaryComponents);
			createEducationFieldDBmap(dbmap,admGTColumn.educationDetails,adm.educationDetails);
			createEmploymentHistoryFieldDBmap(dbmap,admGTColumn.employmentHistoryDetail,adm.employmentHistoryDetail);			
			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return dbmap;
	}
	
	public void createDbMap(Map<String, String> dbmap, String colName, String colValue){
		try{
			if(!Utils.isBlankOrNull(colName)){
				dbmap.put(colName,colValue);
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		
	}
	
	public void createCustomFieldDBmap(Map<String, String> dbmap, List<CustomFieldDetailModel> customFieldsGT, List<CustomFieldDetailModel> customFieldsVal){
		try{
			for(CustomFieldDetailModel cfdmGT: customFieldsGT){
				for(CustomFieldDetailModel cfdmVal:customFieldsVal){
					if(cfdmGT.custmFieldName.equals(cfdmVal.custmFieldName)){
						if(cfdmGT.custmFieldValueDataType.contains("STRING")){
							createDbMap(dbmap,cfdmGT.custmFieldValue,convertString(cfdmVal.custmFieldValue));
						}else if(cfdmGT.custmFieldValueDataType.contains("DATE")){
							createDbMap(dbmap,cfdmGT.custmFieldValue,convertDate(cfdmVal.custmFieldValue, Utils.regDDMMMYYFormat));
						}else if(cfdmGT.custmFieldValueDataType.contains("NUMBER")){
							createDbMap(dbmap,cfdmGT.custmFieldValue,convertNumber(cfdmVal.custmFieldValue));
						}else{
							
						}							
						
					}
				}
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
	}
	
	public void createSalaryComponentFieldDBmap(Map<String, String> dbmap, List<SalaryComponentDetailModel> salCompFieldsGT, List<SalaryComponentDetailModel> salCompFieldsVal){
		try{
			for(SalaryComponentDetailModel scdmGT: salCompFieldsGT){
				for(SalaryComponentDetailModel scdmVal:salCompFieldsVal){
					if(scdmGT.salaryComponentName.equals(scdmVal.salaryComponentName)
							&& scdmGT.salaryComponentType.equals(scdmVal.salaryComponentType)){
						if(scdmGT.salaryComponentValueDataType.contains("STRING")){
							createDbMap(dbmap,scdmGT.salaryComponentValue,convertString(scdmVal.salaryComponentValue));
						}else if(scdmGT.salaryComponentValueDataType.contains("DATE")){							
							createDbMap(dbmap,scdmGT.salaryComponentValue,convertDate(scdmVal.salaryComponentValue,Utils.regDDMMMYYFormat));
						}else if(scdmGT.salaryComponentValueDataType.contains("NUMBER")){
							createDbMap(dbmap,scdmGT.salaryComponentValue,convertNumber(scdmVal.salaryComponentValue));
						}else{
							
						}
					}
				}
			}		
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
	}
	
	public void createEducationFieldDBmap(Map<String, String> dbmap, List<EducationDetailModel> educationDetailsGT, List<EducationDetailModel> educationDetailsVal){
		try{
			int k=0;
			for(EducationDetailModel edmGT: educationDetailsGT){				
				for(int i=0;i<educationDetailsVal.size();i++){
					EducationDetailModel edmVal = educationDetailsVal.get(k);
					createDbMap(dbmap,edmGT.degreeTitle,convertString(edmVal.degreeTitle));
					createDbMap(dbmap,edmGT.branchTitle,convertString(edmVal.branchTitle));
					createDbMap(dbmap,edmGT.instituteTitle,convertString(edmVal.instituteTitle));
					createDbMap(dbmap,edmGT.yearOfPassing,convertString(edmVal.yearOfPassing));
					createDbMap(dbmap,edmGT.grade,convertString(edmVal.grade));
					break;
				}
				k++;
			}		
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
	}
	public void createEmploymentHistoryFieldDBmap(Map<String, String> dbmap, List<EmploymentHistoryDetailModel> employmentHistoryDetailsGT, List<EmploymentHistoryDetailModel> employmentHistoryDetailsVal){
		try{
			int k=0;
			for(EmploymentHistoryDetailModel emphdmGT: employmentHistoryDetailsGT){				
				for(int i=0;i<employmentHistoryDetailsVal.size();i++){
					EmploymentHistoryDetailModel empHdmVal = employmentHistoryDetailsVal.get(k);
					createDbMap(dbmap,emphdmGT.employerFromDate,convertString(empHdmVal.employerFromDate));
					createDbMap(dbmap,emphdmGT.employerToDate,convertString(empHdmVal.employerToDate));
					createDbMap(dbmap,emphdmGT.employerName,convertString(empHdmVal.employerName));
					createDbMap(dbmap,emphdmGT.designationName,convertString(empHdmVal.designationName));
					createDbMap(dbmap,emphdmGT.employerExperience,convertString(empHdmVal.employerExperience));
					break;
				}
				k++;
			}		
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
	}
	
	private static String convertNumber(String number){
		try{
			if(!Utils.isBlankOrNull(number)){
				number = number.replace(" ", "");
				if(number.contains(","))
					return number.replace(",", "");
				else
					return number;
			}else{
				number="null";
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
			return "null";
		}		
		return number;
	}
	
	private static String convertDate(String strDate, String dateFormat){
		String date = "";
		try{
			if(!Utils.isBlankOrNull(strDate)){
				date = "'"+Utils.getDateStringConvertedToOtherDateFormat(strDate,dateFormat,Utils.redYYYYMMDDFormat)+"'";
			}else{
				date = "null";
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
			return "null";
		}
		return date;
	}
	
	private static String convertString(String paramValue){
		String dbStringVal="";
		try{
			if(!Utils.isBlankOrNull(paramValue)){
				dbStringVal="'"+paramValue+"'";
			}else{
				dbStringVal="null";
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
			return "null";
		}
		return dbStringVal;		
	}
}