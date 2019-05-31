/**
 * 
 */
package com.talentPool.otherApplications.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.otherApplications.utils.PostingDataToURL;

/**
 * @author Shantanu
 *
 */
public class OtherApplicationManager {
	/**
	 * 
	 * @param applicantId
	 */
	public ArrayList<String> onMoveToAcceptAtKotak(String applicantId){
		ArrayList<String> hrmsCode = null;
		ApplicantManager appplicantManager = new ApplicantManager();
		KotakXMLFileProcessor xmlFileProcessor = new KotakXMLFileProcessor();
		PostingDataToURL postingDataToURL = new PostingDataToURL();		
		try{			
			SimpleDataObject applicantDetail = appplicantManager.getApplicantDetails(applicantId);						
			String hrmsCodeXMLFile = Utils.concatFilePath(OtherApplicationConstants.customerXMLPath,"req_applicant_"+applicantId+".xml");			
			boolean isFile = xmlFileProcessor.createKotakXML(OtherApplicationConstants.customerXMLPath, hrmsCodeXMLFile, applicantDetail, OtherApplicationConstants.KOTAK_XML_TYPE_HRMS_CODE);
			if(isFile){
				hrmsCode = postingDataToURL.postFileToUrl(hrmsCodeXMLFile,OtherApplicationConstants.KOTAK_XML_TYPE_HRMS_CODE);
			}
		}catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,sqle);
		}catch(Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return hrmsCode;
	}
	
	public ArrayList<String> onMoveToJoinedAtKotak(String applicantId){
		ArrayList<String> employeeCode = null;
		ApplicantManager appplicantManager = new ApplicantManager();
		KotakXMLFileProcessor xmlFileProcessor = new KotakXMLFileProcessor();
		PostingDataToURL postingDataToURL = new PostingDataToURL();
		try{		
			SimpleDataObject applicantDetail = appplicantManager.getApplicantDetails(applicantId);
			String empCodeXMLFile =Utils.concatFilePath(OtherApplicationConstants.customerXMLPath,"req_applicant_emp"+applicantId+".xml");
			boolean isFile = xmlFileProcessor.createKotakXML(OtherApplicationConstants.customerXMLPath, empCodeXMLFile, applicantDetail, OtherApplicationConstants.KOTAK_XML_TYPE_EMPLOYEE_CODE);
			if(isFile){
				employeeCode = postingDataToURL.postFileToUrl(empCodeXMLFile,OtherApplicationConstants.KOTAK_XML_TYPE_EMPLOYEE_CODE);
			}
		}catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,sqle);
		}catch(Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}		
		return employeeCode;
	}
		
	public ArrayList<ApplicantData> getApplicantsForPositionStepLevel(String positionStepLevel) {
		DBPreparedQuery dq = null;
		ArrayList<ApplicantData> appData = null;
		try {
			dq = new DBPreparedQuery("dOtherApplicationManager_GetApplicantsForPositionStepLevel");
			dq.setId(1, positionStepLevel);
			appData = dq.getResult();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return appData;
	}
	
	public void addWhenStepLevelChange(String applicantId,String positionId,String stepLevelId) {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dOtherApplicationManager_addWhenStepLevelChange");
			dq.setId(1, applicantId);
			dq.setId(2, positionId);
			dq.setId(3, stepLevelId);			
			dq.execute();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<SimpleDataObject> getApplicantsForStepLevelChangeCSV() {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sdo = null;
		try {
			dq = new DBPreparedQuery("dOtherApplicationManager_selectFromStepLevelChange");			
			sdo = dq.getResult();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}

	public void updateStepLevelChangeSchedulerJob(ArrayList<SimpleDataObject> applicantsInAccept){
		DBPreparedQuery dq = null;
		ArrayList<String> applicants= new ArrayList<String>();
		try {
			for (SimpleDataObject simpleDataObject : applicantsInAccept) {
				applicants.add(simpleDataObject.getString("applicantId"));
			}
			String[] dynParam = new String[1];			
			dynParam[0] = Utils.convertArrayListIntoCommaSptdString(applicants);			
			dq = new DBPreparedQuery("dOtherApplicationManager_updateStepLevelChange",dynParam);						
			dq.execute();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
	}
	
	
	public static LinkedHashMap<String, String> getAllFieldsMap() {
		LinkedHashMap<String, String> fieldsMap = new LinkedHashMap<String, String>();
		try{
			fieldsMap.put(InboxConstants.FLD_NAME, TPLabels.getLabel("common.name"));
			fieldsMap.put(InboxConstants.FLD_EMAIL_1, TPLabels.getLabel("common.email1"));
			fieldsMap.put(InboxConstants.FLD_EMAIL_2, TPLabels.getLabel("common.email2"));
			fieldsMap.put(InboxConstants.FLD_PHONE_1, TPLabels.getLabel("common.phone1"));
			fieldsMap.put(InboxConstants.FLD_PHONE_2, TPLabels.getLabel("common.phone2"));
			fieldsMap.put(InboxConstants.FLD_MOBILE, TPLabels.getLabel("common.mobile"));
			fieldsMap.put(InboxConstants.FLD_DATE_OF_BIRTH, TPLabels.getLabel("common.date_of_birth"));
			fieldsMap.put(InboxConstants.FLD_EXPERIENCE, TPLabels.getLabel("common.experience"));
		
			if (ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_CURRENT_LOCATION)) {
				fieldsMap.put(InboxConstants.FLD_CURRENT_LOCATION, TPLabels.getLabel("common.current_location"));
			}
			if (ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_SKILLS)) {
				fieldsMap.put(InboxConstants.FLD_SKILLS, TPLabels.getLabel("common.skills"));
			}
			if (ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EDUCATION)) {
				fieldsMap.put(InboxConstants.FLD_YEAR_OF_PASSING_1, TPLabels.getLabel("common.year_of_passing_1"));
				fieldsMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_1, TPLabels.getLabel("common.start_date_of_passing_1"));
				fieldsMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_1, TPLabels.getLabel("common.end_date_of_passing_1"));
				fieldsMap.put(InboxConstants.FLD_INSTITUTE_1, TPLabels.getLabel("common.institute_1"));
				fieldsMap.put(InboxConstants.FLD_DEGREE_1, TPLabels.getLabel("common.degree_1"));
				fieldsMap.put(InboxConstants.FLD_BRANCH_1, TPLabels.getLabel("common.branch_1"));
				fieldsMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_1, TPLabels.getLabel("common.university_of_passing_1"));
				fieldsMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_1, TPLabels.getLabel("common.type_of_program_of_passing_1"));
				fieldsMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_1, TPLabels.getLabel("common.percentage_passing_1"));
				fieldsMap.put(InboxConstants.FLD_YEAR_OF_PASSING_2, TPLabels.getLabel("common.year_of_passing_2"));
				fieldsMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_2, TPLabels.getLabel("common.start_date_of_passing_2"));
				fieldsMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_2, TPLabels.getLabel("common.end_date_of_passing_2"));
				fieldsMap.put(InboxConstants.FLD_INSTITUTE_2, TPLabels.getLabel("common.institute_2"));
				fieldsMap.put(InboxConstants.FLD_DEGREE_2, TPLabels.getLabel("common.degree_2"));
				fieldsMap.put(InboxConstants.FLD_BRANCH_2, TPLabels.getLabel("common.branch_2"));
				fieldsMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_2, TPLabels.getLabel("common.university_of_passing_2"));
				fieldsMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_2, TPLabels.getLabel("common.type_of_program_of_passing_2"));
				fieldsMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_2, TPLabels.getLabel("common.percentage_passing_2"));
				fieldsMap.put(InboxConstants.FLD_YEAR_OF_PASSING_3, TPLabels.getLabel("common.year_of_passing_3"));
				fieldsMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_3, TPLabels.getLabel("common.start_date_of_passing_3"));
				fieldsMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_3, TPLabels.getLabel("common.end_date_of_passing_3"));
				fieldsMap.put(InboxConstants.FLD_INSTITUTE_3, TPLabels.getLabel("common.institute_3"));
				fieldsMap.put(InboxConstants.FLD_DEGREE_3, TPLabels.getLabel("common.degree_3"));
				fieldsMap.put(InboxConstants.FLD_BRANCH_3, TPLabels.getLabel("common.branch_3"));
				fieldsMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_3, TPLabels.getLabel("common.university_of_passing_3"));
				fieldsMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_3, TPLabels.getLabel("common.type_of_program_of_passing_3"));
				fieldsMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_3, TPLabels.getLabel("common.percentage_passing_3"));
				fieldsMap.put(InboxConstants.FLD_YEAR_OF_PASSING_4, TPLabels.getLabel("common.year_of_passing_4"));
				fieldsMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_4, TPLabels.getLabel("common.start_date_of_passing_4"));
				fieldsMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_4, TPLabels.getLabel("common.end_date_of_passing_4"));
				fieldsMap.put(InboxConstants.FLD_INSTITUTE_4, TPLabels.getLabel("common.institute_4"));
				fieldsMap.put(InboxConstants.FLD_DEGREE_4, TPLabels.getLabel("common.degree_4"));
				fieldsMap.put(InboxConstants.FLD_BRANCH_4, TPLabels.getLabel("common.branch_4"));
				fieldsMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_4, TPLabels.getLabel("common.university_of_passing_4"));
				fieldsMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_4, TPLabels.getLabel("common.type_of_program_of_passing_4"));
				fieldsMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_4, TPLabels.getLabel("common.percentage_passing_4"));
				
			}
			if (ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER)) {
				fieldsMap.put(InboxConstants.FLD_CURRENT_EMPLOYER, TPLabels.getLabel("common.current_employer"));
			}
			if (ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_CURRENT_CTC)) {
				fieldsMap.put(InboxConstants.FLD_CURRENT_CTC, TPLabels.getLabel("common.current_ctc"));
			}
			if (ImportConfigurationManager.isImportFieldShow(ImportConfigurationConstants.FIELD_EXPECTED_CTC)) {
				fieldsMap.put(InboxConstants.FLD_EXPECTED_CTC, TPLabels.getLabel("common.expected_ctc"));
			}

			// Custom Fileds
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
				for (int c = 0; c < customFields.size(); c++) {
					CustomFieldData cData = customFields.get(c);
					fieldsMap.put(cData.getFieldName(), cData.getFieldDisplayName());
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}		
		return fieldsMap;
	}

	public ArrayList<SimpleDataObject> getDoc(){
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> arrlst = new ArrayList<SimpleDataObject>();
		try {
			dq = new DBPreparedQuery("dOtherApplicationManager_fetchdoc");						
			arrlst=dq.getResult();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return arrlst;		
	}
	
	public void addWhenStepLevelChangeForGreytip(String applicantId,String positionId,String stepLevelId) {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dOtherApplicationManager_addWhenStepLevelChangeForGreytip");
			dq.setId(1, applicantId);
			dq.setId(2, positionId);
			dq.setId(3, stepLevelId);			
			dq.execute();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
	}
	
	
	public ArrayList<SimpleDataObject> getApplicantsForStepLevelChangeForGreytip() {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sdo = null;
		try {
			dq = new DBPreparedQuery("dOtherApplicationManager_selectFromStepLevelChangeForGreytip");			
			sdo = dq.getResult();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}
	
		
	public void updateStepLevelChangeForGreytip(ArrayList<SimpleDataObject> applicantsInJoined){
		DBPreparedQuery dq = null;
		ArrayList<String> applicants= new ArrayList<String>();
		try {
			for (SimpleDataObject simpleDataObject : applicantsInJoined) {
				applicants.add(simpleDataObject.getString("applicantId"));
			}
			String[] dynParam = new String[1];			
			dynParam[0] = Utils.convertArrayListIntoCommaSptdString(applicants);			
			dq = new DBPreparedQuery("dOtherApplicationManager_updateStepLevelChangeForGreytip",dynParam);						
			dq.execute();
		} catch (Exception e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
	}

}