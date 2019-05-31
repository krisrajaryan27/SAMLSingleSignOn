/**
 * 
 */
package com.talentPool.rest.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.masters.dao.impl.SalaryComponentCategoryDAOImpl;
import com.talentPool.masters.service.impl.SalCompCategoryService;
import com.talentPool.otherApplications.manager.OtherApplicationManager;
import com.talentPool.rest.model.ApplicantDetailModel;
import com.talentPool.rest.model.CustomFieldDetailModel;
import com.talentPool.rest.model.EducationDetailModel;
import com.talentPool.rest.model.EmploymentHistoryDetailModel;
import com.talentPool.rest.model.ListApplicantDetailModel;
import com.talentPool.rest.model.SalaryComponentDetailModel;
import com.talentPool.salaryStructure.constants.SalaryStructureConstants;
import com.talentPool.salaryStructure.databject.SalaryFormulaData;
import com.talentPool.salaryStructure.databject.SalaryStructure;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;
import com.talentPool.salaryStructure.manager.SalaryCalculator;
import com.talentPool.salaryStructure.manager.SalaryStructureManager;
import com.talentPool.user.UserConstants;

/**
 * @author Shantanu
 *
 */

public class RestServiceDataManager{

	public ListApplicantDetailModel fetchApplicantsDetails(){		
		ApplicantManager applicantManager = new ApplicantManager();
		ListApplicantDetailModel listApplicantDetailModel = new ListApplicantDetailModel();
		OtherApplicationManager oam = new OtherApplicationManager();
		List<ApplicantDetailModel> listADM = new ArrayList<ApplicantDetailModel>();
		ApplicantData applicantData = new ApplicantData();
		ApplicantDetailModel adm = new ApplicantDetailModel();
		try{
			ArrayList<SimpleDataObject> sdoList = oam.getApplicantsForStepLevelChangeForGreytip();
			if(!sdoList.isEmpty() && sdoList!=null){				
				for(SimpleDataObject sdo:sdoList){
					applicantData = applicantManager.getApplicantDisplayData(sdo.getString("applicantId"),UserConstants.ADMIN_ID);
					adm = convertToApplicantDetailModel(applicantData);				
					listADM.add(adm);
				}
				listApplicantDetailModel.applicantsDetail = listADM;
				oam.updateStepLevelChangeForGreytip(sdoList);
			}else{
				//listApplicantDetailModel.applicantsDetail = null; 
			}
			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return listApplicantDetailModel;
	}
	
	private ApplicantDetailModel convertToApplicantDetailModel(ApplicantData applicantData){
		ApplicantDetailModel applicantDetailMode = new ApplicantDetailModel();
		try{
			applicantDetailMode.applicantId=Utils.isBlankOrNull(applicantData.getApplicantId())?"":applicantData.getApplicantId();
			applicantDetailMode.applicantName=Utils.isBlankOrNull(applicantData.getApplicantName())?"":applicantData.getApplicantName();
			applicantDetailMode.dateOfBirth=Utils.isBlankOrNull(applicantData.getDateOfBirthToDisplay())?"":applicantData.getDateOfBirthToDisplay();
			applicantDetailMode.passportNumber=Utils.isBlankOrNull(applicantData.getPassportNumber())?"":applicantData.getPassportNumber();
			applicantDetailMode.applicantCellPhone=Utils.isBlankOrNull(applicantData.getApplicantCellPhone())?"":applicantData.getApplicantCellPhone();
			//applicantDetailMode.applicantCellPhoneIsInvalid=Utils.isBlankOrNull(applicantData.getApplicantCellPhoneIsInvalid())?"":applicantData.getApplicantCellPhoneIsInvalid();		
			applicantDetailMode.applicantHomePhone=Utils.isBlankOrNull(applicantData.getApplicantHomePhone())?"":applicantData.getApplicantHomePhone();
			//applicantDetailMode.applicantHomePhoneIsInvalid=Utils.isBlankOrNull(applicantData.getApplicantHomePhoneIsInvalid())?"":applicantData.getApplicantHomePhoneIsInvalid();		
			applicantDetailMode.applicantWorkPhone=Utils.isBlankOrNull(applicantData.getApplicantWorkPhone())?"":applicantData.getApplicantWorkPhone();
			//applicantDetailMode.applicantHomePhoneIsInvalid=Utils.isBlankOrNull(applicantData.getApplicantHomePhoneIsInvalid())?"":applicantData.getApplicantHomePhoneIsInvalid();
			applicantDetailMode.applicantEmail1=Utils.isBlankOrNull(applicantData.getApplicantEmail1())?"":applicantData.getApplicantEmail1();		
			applicantDetailMode.applicantEmail2=Utils.isBlankOrNull(applicantData.getApplicantEmail2())?"":applicantData.getApplicantEmail2();
			applicantDetailMode.applicantCity=Utils.isBlankOrNull(applicantData.getApplicantCity())?"":applicantData.getApplicantCity();
			applicantDetailMode.applicantCurrentEmployer=Utils.isBlankOrNull(applicantData.getApplicantCurrentEmployer())?"":applicantData.getApplicantCurrentEmployer();
			applicantDetailMode.applicantSourceId=Utils.isBlankOrNull(applicantData.getApplicantSourceId()+"")?"":applicantData.getApplicantSourceId()+"";		
			applicantDetailMode.applicantSourceTitle=Utils.isBlankOrNull(applicantData.getApplicantSourceTitle())?"":applicantData.getApplicantSourceTitle();
			applicantDetailMode.applicantHRMSCode=Utils.isBlankOrNull(applicantData.getApplicantHRMSCode())?"":applicantData.getApplicantHRMSCode();
			applicantDetailMode.applicantWorkingSince=Utils.isBlankOrNull(applicantData.getApplicantWorkingSince()+"")?"":applicantData.getApplicantWorkingSince()+"";
			applicantDetailMode.currentCTC=Utils.isBlankOrNull(applicantData.getCurrentCTC())?"":applicantData.getCurrentCTC();
			applicantDetailMode.basicOffered=Utils.isBlankOrNull(applicantData.getBasicOffered())?"":applicantData.getBasicOffered();
			applicantDetailMode.applicantOriginalDocPath=Utils.isBlankOrNull(applicantData.getApplicantOriginalDocPath())?"":applicantData.getApplicantOriginalDocPath();
			applicantDetailMode.levelOffered=Utils.isBlankOrNull(applicantData.getLevelOffered())?"":applicantData.getLevelOffered();
			applicantDetailMode.noticePeriod=Utils.isBlankOrNull(applicantData.getNoticePeriod())?"":applicantData.getNoticePeriod();
			applicantDetailMode.positionStepLevel=Utils.isBlankOrNull(applicantData.getPositionStepLevel())?"":applicantData.getPositionStepLevel();
			applicantDetailMode.resumeType=Utils.isBlankOrNull(applicantData.getResumeType())?"":applicantData.getResumeType();
			//applicantDetailMode.resumeTypeId=Utils.isBlankOrNull(applicantData.getResumeTypeId())?"":applicantData.getResumeTypeId();
			//applicantDetailMode.sourceTypeId=Utils.isBlankOrNull(applicantData.getSourceTypeId())?"":applicantData.getSourceTypeId();
			applicantDetailMode.applicantDateJoined=applicantData.getApplicantDateJoined()+"";
			
			applicantDetailMode.educationDetails=convertToEducationModel(applicantData.getEducationalDetails());
			applicantDetailMode.customFields=convertToCustomFieldModel(applicantData.getCustomFields());
			applicantDetailMode.salaryComponents=convertToSalaryComponentModel(applicantData.getApplicantId());
			applicantDetailMode.employmentHistoryDetail=convertToEmploymentHistoryDetailModel(applicantData.getEmploymentHistoryDetails());
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		
		return applicantDetailMode;
	}
	
	private List<CustomFieldDetailModel> convertToCustomFieldModel(List<CustomFieldData> cusDataList){
		List<CustomFieldDetailModel> cusModelList = new ArrayList<CustomFieldDetailModel>();
		try{
			for(CustomFieldData cusData:cusDataList){
				CustomFieldDetailModel cusModel = new CustomFieldDetailModel();
				cusModel.custmFieldId=cusData.getFieldId();
				cusModel.custmFieldName=cusData.getFieldDisplayName();
				cusModel.custmFieldValue=cusData.getFieldStringValue();
				cusModelList.add(cusModel);
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return cusModelList;
	}
	
	private List<EducationDetailModel> convertToEducationModel(List<EducationalData> eduDataList){
		List<EducationDetailModel> eduModelList = new ArrayList<EducationDetailModel>();
		try{
			for(EducationalData eduData:eduDataList){
				EducationDetailModel eduModel = new EducationDetailModel();
				eduModel.educationInfoId=eduData.getEducationalInfoId()+"";
				eduModel.degreeId=eduData.getDegreeId()+"";
				eduModel.degreeTitle=eduData.getDegreeTitle();
				eduModel.branchId=eduData.getMajorId()+"";
				eduModel.branchTitle=eduData.getMajor();
				eduModel.instituteId=eduData.getInstituteId();
				eduModel.instituteTitle=eduData.getInstitute();
				eduModel.yearOfPassing=Utils.getDateConvertedToString(eduData.getYearOfPassing(), Utils.regYYYYFormat);
				eduModel.grade=eduData.getGrade();
				eduModelList.add(eduModel);
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return eduModelList;
	}
	
	private List<SalaryComponentDetailModel> convertToSalaryComponentModel(String applicantId){
		List<SalaryComponentDetailModel> salCompList= new ArrayList<SalaryComponentDetailModel>();
		SalaryCalculator salCal = new SalaryCalculator();
		SalaryStructureManager salaryStructureManager 	= new SalaryStructureManager();
		ApplicantManager applicantManager 				= new ApplicantManager();
		try{			
			SimpleDataObject sdo = applicantManager.getApplicantGradeCTCBasic(applicantId);
			if(sdo!=null && !Utils.isBlankOrNull(sdo.getString("gradeId"))){
				SalaryStructure salary = salCal.calculateSalary(sdo.getString("gradeId"),sdo.getString("offeredCtc"),sdo.getString("offeredBasic"),sdo.getString("inputSalaryVariable"));
				Map<String, List<SalaryFormulaData>> salaryFormulae = salaryStructureManager.getSalaryFormulaeCategoryWiseForaGrade(Integer.parseInt(sdo.getString("gradeId")));
				SalaryComponentCategoryDAOImpl salCompCategoryDao = new SalaryComponentCategoryDAOImpl();
				SalCompCategoryService salCmpCatService = new SalCompCategoryService();
				salCmpCatService.setSalCompCategoryDao(salCompCategoryDao);
				Map<String,SalaryComponentCategory> salCompMap = salCmpCatService.getSalaryComponentCategoryMap();
				SalaryComponentDetailModel monthlyDetailModel 	= null;
				SalaryComponentDetailModel annualDetailModel 	= null;
				for (Entry<String,List<SalaryFormulaData>> entry : salaryFormulae.entrySet()) {
					List<SalaryFormulaData> salaryFormulaDataLst = entry.getValue();
					if(!Utils.isListEmptyOrNull(salaryFormulaDataLst)){
						for (SalaryFormulaData salaryFormulaData : salaryFormulaDataLst) {
							if(SalaryStructureConstants.SALARY_PERIOD_MONTHLY.equals(salaryFormulaData.getSalaryComponentType())){
								monthlyDetailModel 	= new SalaryComponentDetailModel(""+salaryFormulaData.getSalaryComponentId(), 
										salaryFormulaData.getSalaryComponentName(), 
										SalaryStructureConstants.SALARY_PERIOD_MONTHLY);
								annualDetailModel 	= new SalaryComponentDetailModel(""+salaryFormulaData.getSalaryComponentId(), 
										salaryFormulaData.getSalaryComponentName(), 
										SalaryStructureConstants.SALARY_PERIOD_YEARLY);
								monthlyDetailModel.salaryComponentValue	= ""+salary.getMonthlyValue(salaryFormulaData.getSalaryComponentId());
								annualDetailModel.salaryComponentValue	= ""+salary.getAnnualValue(salaryFormulaData.getSalaryComponentId());
								salCompList.add(monthlyDetailModel);
								salCompList.add(annualDetailModel);
							}else{
								monthlyDetailModel 	= new SalaryComponentDetailModel(""+salaryFormulaData.getSalaryComponentId(), 
										salaryFormulaData.getSalaryComponentName(), 
										SalaryStructureConstants.SALARY_PERIOD_MONTHLY);
								monthlyDetailModel.salaryComponentValue	= ""+salary.getAnnualValue(salaryFormulaData.getSalaryComponentId());
								salCompList.add(monthlyDetailModel);
							}
						}
						monthlyDetailModel 	= new SalaryComponentDetailModel(salaryFormulaDataLst.get(0).getSalaryCategoryId(), 
												salCompMap.get(salaryFormulaDataLst.get(0).getSalaryCategoryId()).getCategoryName(), 
													SalaryStructureConstants.SALARY_PERIOD_MONTHLY);
						annualDetailModel 	= new SalaryComponentDetailModel(salaryFormulaDataLst.get(0).getSalaryCategoryId(), 
												salCompMap.get(salaryFormulaDataLst.get(0).getSalaryCategoryId()).getCategoryName(), 
													SalaryStructureConstants.SALARY_PERIOD_YEARLY);
						
						monthlyDetailModel.salaryComponentValue	= ""+salary.getMonthlyCatValue(salaryFormulaDataLst.get(0).getSalaryCategoryId());
						annualDetailModel.salaryComponentValue	= ""+salary.getAnnualCatValue(salaryFormulaDataLst.get(0).getSalaryCategoryId());
						salCompList.add(monthlyDetailModel);
						salCompList.add(annualDetailModel);
					}
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return salCompList;
	}
	
	private List<EmploymentHistoryDetailModel> convertToEmploymentHistoryDetailModel(List<EmploymentHistoryData> empHistDataList){
		List<EmploymentHistoryDetailModel> eduModelList = new ArrayList<EmploymentHistoryDetailModel>();
		try{
			for(EmploymentHistoryData empHisData:empHistDataList){
				EmploymentHistoryDetailModel empHistModel = new EmploymentHistoryDetailModel();
				
				empHistModel.employmentHistoryId=empHisData.getEmploymentHistoryId()+"";
				empHistModel.employerName=empHisData.getEmployerName()+"";
				empHistModel.designationName=empHisData.getDesignationName()+"";
				empHistModel.employerFromDate=empHisData.getEmployerFromDate()+"";
				empHistModel.employerToDate=empHisData.getEmployerToDate()+"";
				empHistModel.employerExperience=empHisData.getEmployerExperience();
				empHistModel.employerId=empHisData.getEmployerId()+"";
				empHistModel.designationId=empHisData.getDesignationId()+"";
				eduModelList.add(empHistModel);
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return eduModelList;
	}
}
