/**
 * 
 */
package com.talentPool.rest.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.masters.dao.impl.SalaryComponentCategoryDAOImpl;
import com.talentPool.masters.service.impl.SalCompCategoryService;
import com.talentPool.rest.model.ApplicantDetailModel;
import com.talentPool.rest.model.CustomFieldDetailModel;
import com.talentPool.rest.model.EducationDetailModel;
import com.talentPool.rest.model.EmploymentHistoryDetailModel;
import com.talentPool.rest.model.SalaryComponentDetailModel;
import com.talentPool.salaryStructure.constants.SalaryStructureConstants;
import com.talentPool.salaryStructure.databject.SalaryComponentsData;
import com.talentPool.salaryStructure.entity.SalaryComponentCategory;
import com.talentPool.salaryStructure.manager.SalaryStructureManager;

/**
 * @author Shantanu
 *
 */
@Path("/dbmap")
public class TalentpoolGreytipColumnMap {
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public ApplicantDetailModel fetchColumnMaps(){		
		ApplicantDetailModel adm = new ApplicantDetailModel(true);
		adm.customFields = fetchApplicantCustomField();
		adm.educationDetails = fetchEducationalDetails();
		adm.salaryComponents = fetchSalaryComponents();
		adm.employmentHistoryDetail=fetchEmploymentHistoryDetails();
		return adm;
	}
	
	private  List<CustomFieldDetailModel> fetchApplicantCustomField(){
		 List<CustomFieldDetailModel> lstCfdm = new ArrayList<CustomFieldDetailModel>();
		 ArrayList<CustomFieldData> customFields = null;
		 try{
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);				
				for(CustomFieldData cusData:customFields){
					CustomFieldDetailModel cusModel = new CustomFieldDetailModel();
					cusModel.custmFieldId=cusData.getFieldId();
					cusModel.custmFieldName=cusData.getFieldDisplayName();
					cusModel.custmFieldValue="@Greytip Column Name pertaining to Talentpool custom field@";
					cusModel.custmFieldValueDataType="@Greytip Column Data Type pertaining to Talentpool custom field@";
					lstCfdm.add(cusModel);
				}
			}			 
		 }catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return lstCfdm;
	}
	private  List<EducationDetailModel> fetchEducationalDetails(){
		List<EducationDetailModel> eduModelList = new ArrayList<EducationDetailModel>();
		try{
			int i=0;
			for(;i<3;i++){
				EducationDetailModel eduModel = new EducationDetailModel("@Give here the Greytip Column Name@");
				eduModel.educationInfoId=i+1+"";
				eduModelList.add(eduModel);
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return eduModelList;
	}
	
	/**
	 * Fetches all SalaryComponents and builds List&lt;SalaryComponentDetailModel&gt;
	 * @return
	 */
	private List<SalaryComponentDetailModel> fetchSalaryComponents(){
		List<SalaryComponentDetailModel> listScdm = new ArrayList<SalaryComponentDetailModel>();
		List<SalaryComponentsData> salaryComponents = null;
		SalaryStructureManager salaryStructureManager = null;
		try{
			salaryStructureManager = new SalaryStructureManager();
			salaryComponents = salaryStructureManager.getSalaryComponents();
			if(salaryComponents!=null){
				for (SalaryComponentsData salaryComponentsData : salaryComponents) {
					if(SalaryStructureConstants.SALARY_PERIOD_MONTHLY.equals(salaryComponentsData.getSalaryComponentType())){
						listScdm.add(buildSalaryComponentDetailModel(salaryComponentsData, SalaryStructureConstants.SALARY_PERIOD_YEARLY));
					}
					listScdm.add(buildSalaryComponentDetailModel(salaryComponentsData, null));
				}					
			}
			SalaryComponentCategoryDAOImpl salCompCategoryDao = new SalaryComponentCategoryDAOImpl();
			SalCompCategoryService salCmpCatService = new SalCompCategoryService();
			salCmpCatService.setSalCompCategoryDao(salCompCategoryDao);
			List<SalaryComponentCategory> salCompList = salCmpCatService.findAll();
			if(salCompList!=null){
				for (SalaryComponentCategory salaryCategoryData : salCompList) {
					listScdm.add(buildSalaryComponentCategoryModel(salaryCategoryData, SalaryStructureConstants.SALARY_PERIOD_MONTHLY));
					listScdm.add(buildSalaryComponentCategoryModel(salaryCategoryData, SalaryStructureConstants.SALARY_PERIOD_YEARLY));
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return listScdm;
	}
	
	/**
	 * Builds SalaryComponentDetailModel with details from salaryComponentsData. 
	 * componentType if not null then used prior to salaryComponentsData.getSalaryComponentType() 
	 * @param salaryComponentsData
	 * @param componentType
	 * @return
	 */
	private SalaryComponentDetailModel buildSalaryComponentDetailModel(SalaryComponentsData salaryComponentsData, String componentType){
		SalaryComponentDetailModel salModel = new SalaryComponentDetailModel();
		salModel.salaryComponentId=salaryComponentsData.getSalaryComponentId();
		salModel.salaryComponentName=salaryComponentsData.getSalaryComponentName();
		salModel.salaryComponentType=Utils.isBlankOrNull(componentType)?salaryComponentsData.getSalaryComponentType():componentType;
		salModel.salaryComponentValue="@Greytip Column Name pertaining to Talentpool salary component@";
		salModel.salaryComponentValueDataType="@Greytip Column Data Type pertaining to Talentpool salary component@";
		return salModel;
	}
	
	/**
	 * Builds SalaryComponentDetailModel with details from salaryComponentsData. 
	 * componentType if not null then used prior to salaryComponentsData.getSalaryComponentType() 
	 * @param salaryComponentsData
	 * @param componentType
	 * @return
	 */
	private SalaryComponentDetailModel buildSalaryComponentCategoryModel(SalaryComponentCategory salaryCategoryData, String categoryType){
		SalaryComponentDetailModel salModel = new SalaryComponentDetailModel();
		salModel.salaryComponentId=""+salaryCategoryData.getCategoryId();
		salModel.salaryComponentName=salaryCategoryData.getCategoryName();
		salModel.salaryComponentType=categoryType;
		salModel.salaryComponentValue="@Greytip Column Name pertaining to Talentpool salary categroy@";
		salModel.salaryComponentValueDataType="@Greytip Column Data Type pertaining to Talentpool salary categroy@";
		return salModel;
	}
	
	private  List<EmploymentHistoryDetailModel> fetchEmploymentHistoryDetails(){
		List<EmploymentHistoryDetailModel> eduModelList = new ArrayList<EmploymentHistoryDetailModel>();
		try{
			int i=0;
			for(;i<5;i++){
				EmploymentHistoryDetailModel empHistModel = new EmploymentHistoryDetailModel("@Give here the Greytip Column Name@");
				empHistModel.employmentHistoryId=i+1+"";
				eduModelList.add(empHistModel);
			}			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return eduModelList;
	}
}