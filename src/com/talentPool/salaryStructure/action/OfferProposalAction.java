/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.salaryStructure.action;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.ModelDriven;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantCurrentDetails;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.applicant.utils.ApplicantUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.utils.StepLevelStaticUtils;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.salaryStructure.databject.SalaryCategoriesComparisonData;
import com.talentPool.salaryStructure.model.OfferProposalModel;
import com.talentPool.salaryStructure.service.IOfferProposalService;
import com.talentPool.salaryStructure.utils.OfferProposalUtils;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author PraveenK
 * @since  Apr 23, 2012
 */
public class OfferProposalAction extends TPActionSupport implements ModelDriven<OfferProposalModel> {
	
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 7770062145563127871L;
	
	private IOfferProposalService _offerProposalService;
	
	/**
	 * Injected though DI
	 * @param drTemplateService the drTemplateService to set
	 */
	public void setOfferProposalService(IOfferProposalService offerProposalService) {
		this._offerProposalService = offerProposalService;
	}
	
	
	private OfferProposalModel offerProposalModel = new OfferProposalModel();
	private ApplicantData applicantData;
	private List<SalaryCategoriesComparisonData> salCatCompData;
	private boolean previewSalary;
	private String gradeId;
	
	
	/**
	 * Action which navigate to OfferProposal screen. 
	 * <br>
	 * <br>1. Populates screen with Applicant Data that will help user to compare salary details.
	 * <br>2. Salary details to be compared (Existing and Proposed)
	 * @return SUCCESS if no exception
	 * <BR>ERROR when problem in populating data or authorization error 
	 */
	public String offerProposalScreen(){
		String applicantId = null;
		try {
			applicantId = getApplicantId();
			ApplicantManager aManager = new ApplicantManager();
			ApplicantData aData = aManager.getApplicantData(applicantId);
			
			ApplicantCurrentDetails applicantCurrDetails = _offerProposalService.getApplicantCurrentDetails(applicantId);
			
			setApplicantData(aData);
			setApplicantOfferedDetails(aData);
			
			if(!ImportConfigurationManager.isCurrentCTCViewable(getPermissionSet())){
				offerProposalModel.setCurrentCTC(GlobalConstants.CONFIDENTIAL_CHARACTER);
				offerProposalModel.setCurrentCTCEditable(false);
			}else{
				offerProposalModel.setCurrentCTC(aData.getCurrentCTC());
				offerProposalModel.setCurrentCTCEditable(true);
			}
			
			ApplicantUtils.checkConfidentiality(aData, getPermissionSet());
			
			if(applicantCurrDetails!=null){
				offerProposalModel.setCurrentBasic(""+applicantCurrDetails.getCurrentBasic());
				offerProposalModel.setCurrentDesignation(applicantCurrDetails.getCurrentDesignation());
				offerProposalModel.setCurrentLevel(applicantCurrDetails.getCurrentLevel());
			}
			
			SimpleDataObject sdo = aManager.getApplicantGradeCTCBasic(applicantId);
			if(sdo!=null){
				salCatCompData = _offerProposalService.getSalCategoriesComparisonData(applicantId, sdo.getString("offeredCtc"), sdo.getString("offeredBasic"), sdo.getString("inputSalaryVariable"), sdo.getString("gradeId"));
				setGradeId(sdo.getString("gradeId"));
			}else{
				salCatCompData = _offerProposalService.getSalCategoriesComparisonData(applicantId, null, null, null, null);			
			}
			
			if(getPermissionSet().isPERMISSION_GENERATE_OFFER() && !Utils.isBlankOrNull(getGradeId())){
				previewSalary = true;
			} else{
				previewSalary = false;				
			}
			setReqAttr("proposalActionJSArray", OfferProposalUtils.getOfferProposalActionJSArray());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return SUCCESS;
	}
	
	public String saveCTCComparisonGrid(){
		try {
			if(validateModel()){
				_offerProposalService.saveCTCComparisonGrid(offerProposalModel, getUserId());				
			}else{
				offerProposalScreen();
				return ERROR;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			addActionError(TPLabels.getLabel("offer_proposal.error.error_saving"));
			offerProposalScreen();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * @return the applicantData
	 */
	public ApplicantData getApplicantData() {
		return applicantData;
	}


	/**
	 * @param aData the applicantData to set
	 */
	private void setApplicantData(final ApplicantData aData) {
		if(ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,getPermissionSet().isSHOW_CONFIDENTIAL_DATA())){
			String source = getText("common.openingRoundBracket")+getText("common.source")+" "+aData.getApplicantSourceTitle()+getText("common.closingRoundBracket");
			aData.setApplicantName(aData.getApplicantName()+source);
		}
		if(!Utils.isBlankOrNull(aData.getApplicantPositionId())){
			PositionManager posManager = new PositionManager();
			String posTitle = posManager.getPositionTitle(aData.getApplicantPositionId());
			aData.setApplicantPositionTitle(posTitle);
		}
		this.applicantData = aData;
	}
	
	/**
	 * @param aData
	 */
	private void setApplicantOfferedDetails(final ApplicantData aData){
		if (!Utils.isBlankOrNull(aData.getApplicantPositionId())) {
			if(SelectionProcessConstants.APPLICANT_JOINED.equals(aData.getApplicantJoined()) || StepConstants.STEP_STAGE_HIRE.equals(aData.getPositionStepLevel())){
				setActualOfferDetails(aData);
			}else {
				setReqAttr("ctc_comparison_warning", TPLabels.getLabel("ctc_comparison_screen.message.not_in_hirestage", new String[]{TPLabels.getLabel("common.candidate"),StepLevelStaticUtils.getStepLevelName(StepConstants.STEP_STAGE_HIRE)}));
				setOfferDetails("");
			offerProposalModel.setOfferedDetailsAsEditable();
			}
		}else {
			setReqAttr("ctc_comparison_warning", TPLabels.getLabel("ctc_comparison_screen.message.not_in_selection_process", "common.applicant"));
			setOfferDetails("");
			offerProposalModel.setOfferedDetailsAsEditable();
		}
	}
	
	/**
	 * @param aData
	 */
	private void setActualOfferDetails(final ApplicantData aData){
		if(!ImportConfigurationManager.isCTCOfferedViewable(getPermissionSet())){
			offerProposalModel.setOfferedCTC(GlobalConstants.CONFIDENTIAL_CHARACTER);
			offerProposalModel.setOfferedCTCEditable(false);
		}else{
			offerProposalModel.setOfferedCTC(aData.getCtcOffered());
			offerProposalModel.setOfferedCTCEditable(true);
		}
		
		if(!ImportConfigurationManager.isBasicOfferedViewable(getPermissionSet())){
			offerProposalModel.setOfferedBasic(GlobalConstants.CONFIDENTIAL_CHARACTER);
			offerProposalModel.setOfferedBasicEditable(false);
		}else{
			offerProposalModel.setOfferedBasic(aData.getBasicOffered());
			offerProposalModel.setOfferedBasicEditable(true);
		}
		
		if(!ImportConfigurationManager.isDesignationOfferedViewable(getPermissionSet())){
			offerProposalModel.setOfferedDesignation(GlobalConstants.CONFIDENTIAL_CHARACTER);
			offerProposalModel.setOfferedDesignationEditable(false);
		}else{
			offerProposalModel.setOfferedDesignation(aData.getDesignationOffered());
			offerProposalModel.setOfferedDesignationEditable(true);
		}
		
		if(!ImportConfigurationManager.isLevelOfferedViewable(getPermissionSet())){
			offerProposalModel.setOfferedLevel(GlobalConstants.CONFIDENTIAL_CHARACTER);
			offerProposalModel.setOfferedLevelEditable(false);
		}else{
			offerProposalModel.setOfferedLevel(aData.getLevelOffered());
			offerProposalModel.setOfferedLevelEditable(true);
		}
		
		if(!ImportConfigurationManager.isInputSalaryVariableViewable(getPermissionSet())){
			offerProposalModel.setOfferedInputSalaryVariable(GlobalConstants.CONFIDENTIAL_CHARACTER);
			offerProposalModel.setOfferedInputSalaryVariableEditable(false);
		}else{
			offerProposalModel.setOfferedInputSalaryVariable(aData.getInputSalaryVariable());
			offerProposalModel.setOfferedInputSalaryVariableEditable(true);
		}
	}
	
	/**
	 * @param message
	 */
	private void setOfferDetails(String message){
		offerProposalModel.setOfferedCTC(message);
		offerProposalModel.setOfferedBasic(message);
		offerProposalModel.setOfferedDesignation(message);
		offerProposalModel.setOfferedLevel(message);
		offerProposalModel.setOfferedInputSalaryVariable(message);
	}


	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public OfferProposalModel getModel() {
		return this.offerProposalModel;
	}

	/**
	 * @return the salCatCompData
	 */
	public List<SalaryCategoriesComparisonData> getSalCatCompData() {
		return salCatCompData;
	}

	/**
	 * @param salCatCompData the salCatCompData to set
	 */
	public void setSalCatCompData(List<SalaryCategoriesComparisonData> salCatCompData) {
		this.salCatCompData = salCatCompData;
	}
	
	/**
	 * Validates the OfferProposal Model
	 * @return true if all the data of the model is valid otherwise returns false.
	 */
	public boolean validateModel(){
		boolean error =  false;
		try {
			if(!Utils.isBlankOrNull(offerProposalModel.getCurrentBasic()))
				Integer.parseInt(offerProposalModel.getCurrentBasic());			
		} catch (NumberFormatException e) {
			addActionError(TPLabels.getLabel("offer_proposal.error.current_basic_integer"));
			error = true;
		}
		try {
			if(!Utils.isBlankOrNull(offerProposalModel.getOfferedInputSalaryVariable()))
				Integer.parseInt(offerProposalModel.getOfferedInputSalaryVariable());
			else
				offerProposalModel.setOfferedInputSalaryVariable(null);
		} catch (NumberFormatException e) {
			addActionError(TPLabels.getLabel("offer_proposal.error.offered_input_salary_variable")+" "+GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL));
			error = true;
		}
		try {
			Map<String,String> salCatsExistingMap = offerProposalModel.getSalCatsExistingValMap();		
			for (Entry<String, String> entry : salCatsExistingMap.entrySet()) {
				Integer.parseInt(entry.getValue());
			}
		} catch (NumberFormatException e) {
			addActionError(TPLabels.getLabel("offer_proposal.error.category_integer"));
		}
		return !error;
	}

	/**
	 * @return the previewSalary
	 */
	public boolean isPreviewSalary() {
		return previewSalary;
	}

	/**
	 * @param previewSalary the previewSalary to set
	 */
	public void setPreviewSalary(boolean previewSalary) {
		this.previewSalary = previewSalary;
	}

	/**
	 * @return the gradeId
	 */
	public String getGradeId() {
		return gradeId;
	}

	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	
}
