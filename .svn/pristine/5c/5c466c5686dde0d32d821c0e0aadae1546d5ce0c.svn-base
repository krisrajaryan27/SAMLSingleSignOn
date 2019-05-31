/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.offerSheet.acion;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.offerSheet.constants.OfferSheetConstants;
import com.talentPool.offerSheet.dataobject.ApplicantOfferSheetDetails;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateVariable;
import com.talentPool.offerSheet.manager.OfferSheetManager;
import com.talentPool.offerSheet.model.ApplicantOfferModel;
import com.talentPool.offerSheet.utils.OfferSheetUtils;
import com.talentPool.selectionProcess.dataobject.FeedbackData;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author praveenk
 * @since  May 10, 2012
 */
public class ManageApplicantOfferAction extends TPActionSupport implements ModelDriven<ApplicantOfferModel> {

	/**
	 * Auto Generated Serial Version Id
	 */
	private static final long serialVersionUID = -1278750518452044511L;
	
	private ApplicantOfferModel applicantOfferModel = new ApplicantOfferModel();
	
	private FeedbackData feedbackData;
	private ApplicantData applicantData;
	private String applicantTitle;
	
	private boolean previewSalary;
	
	private String jsArrayOfferSheetTemplates;
	private String jsArrayMapping;
	
	private String offerSheetPath;
	
	public String manageApplicantOfferGeneration(){
		try {
			OfferSheetManager offerSheetManager = new OfferSheetManager();  
			boolean isOfferAlreadyGenerated =  offerSheetManager.isOfferAlreadygenerated(applicantOfferModel.getApplicantId());
			setReqAttr("isOfferAlreadyGenerated", isOfferAlreadyGenerated);
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			feedbackData = selectionProcessManager.getCurrentStepData(applicantOfferModel.getApplicantId());
			StepManager stepManager = new StepManager();
			String stepId = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_OFFER_TO_JOINED_DEFAULT_STEP_ID);
			if (!Utils.isBlankOrNull(stepId)){
				MasterStepData data = stepManager.getMasterStepData(stepId);
				if(feedbackData!=null){
					MasterStepData stepData = stepManager.getMasterStepDataFromPositionStep(String.valueOf(feedbackData.getFromStepData().getStepId()));
					if (Integer.parseInt(data.getStepRank()) <= Integer.parseInt(stepData.getStepRank())){
						setReqAttr("isOfferGenerationStep", "1");
					}	
				}
				
			}
			ApplicantManager applicantManager = new ApplicantManager();
			applicantTitle = applicantManager.getApplicantTitleConstructed(applicantOfferModel.getApplicantId(), getPermissionSet());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String manageApplicantOfferDetails(){
		try {
			SelectionProcessManager selectionProcessManager =  new SelectionProcessManager();  
			OfferSheetManager offerSheetManager 			= new OfferSheetManager();
			FeedbackData feedbackData =  selectionProcessManager.getApplicantOfferDetails(applicantOfferModel.getApplicantId());
			if(applicantOfferModel.isModifyOffer()){
				OfferSheetManager osm = new OfferSheetManager();
				ApplicantOfferSheetDetails aosd = osm.getExistingOfferSheetDetails(applicantOfferModel.getApplicantId(), null);
				applicantOfferModel.setOfferSheetTemplateId(aosd.getOfferSheetTemplateId());
			}
			
			setFeedbackData(feedbackData);
			if(feedbackData.getGradeId()!=null){
				setPreviewSalary(true);
			} else {
				setPreviewSalary(false);
			}
			List<OfferSheetTemplateData> templates = offerSheetManager.getOfferSheetTemplates();
			String jsArrayOfferSheetTemplates = CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)templates, "templateId", "templateName");
			setJsArrayOfferSheetTemplates(jsArrayOfferSheetTemplates);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String offerTemplateVariableMapping(){
		try {
			OfferSheetManager offerSheetManager = new OfferSheetManager();
			SimpleDataObject variableDataMapping = null;
			String offerCode = null;
			if(!Utils.isBlankOrNull(applicantOfferModel.getOfferSheetTemplateId())) {
				OfferSheetUtils offerSheetUtils = new OfferSheetUtils();
				List<SimpleDataObject> attributeMapping = offerSheetUtils.getTemplateVariableAndApplicantAttributeMapping();
				String jsArrayMapping = CommonUtils.getListJavaScriptArrayWithProperties((ArrayList<SimpleDataObject>)attributeMapping, "key", "value");
				setJsArrayMapping(jsArrayMapping);
				List<OfferSheetTemplateVariable> templateVariables = offerSheetManager.getOfferSheetTemplateVariables(applicantOfferModel.getOfferSheetTemplateId());
				if(templateVariables != null && templateVariables.size() > 0) {
					variableDataMapping = offerSheetManager.getTemplateVariableDataMapping(applicantOfferModel.getApplicantId(), applicantOfferModel.getAppOfferDetails());
					if(applicantOfferModel.isModifyOffer()){
						ApplicantOfferSheetDetails aosd = offerSheetManager.getExistingOfferSheetDetails(applicantOfferModel.getApplicantId(), null);
						offerCode = aosd.getOfferCode();
						if(Utils.isBlankOrNull(offerCode))
							offerCode = offerSheetManager.generateOfferCode(variableDataMapping);
					}else {
						offerCode = offerSheetManager.generateOfferCode(variableDataMapping);						
					}
					applicantOfferModel.setOfferCode(offerCode);
					variableDataMapping.setAttribute(OfferSheetConstants.OFFER_CODE, offerCode);
					offerSheetManager.populateTemplateVariableVals(variableDataMapping, templateVariables);
					setReqAttr("templateVariables", templateVariables);
					if(variableDataMapping!=null){
						Utils.escapeJavaScript(variableDataMapping);
						setReqAttr("variableDataMap", variableDataMapping.getAttributes());
					}
				}
			}
			ApplicantManager applicantManager = new ApplicantManager();
			applicantTitle = applicantManager.getApplicantTitleConstructed(applicantOfferModel.getApplicantId(), getPermissionSet());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String generateOfferSheet() {
		try {
			OfferSheetManager offerSheetManager = new OfferSheetManager();
			String[] offerSheetPath = offerSheetManager.generateOfferSheet(applicantOfferModel, "generate", getUserId(),getRequest());
			String offerSheetName = offerSheetManager.saveGeneratedOfferData(applicantOfferModel, offerSheetPath, getUserId(),getRequest());
			setReqAttr("fileName", offerSheetName);
		}catch (Exception e) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			addActionError(TPLabels.getLabel("generate_offer_sheet.error.error_generating_offer"));
			offerTemplateVariableMapping();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @return
	 */
	public String previewOfferSheet() {
		try {
			OfferSheetManager offerSheetManager = new OfferSheetManager();
			String[] offerSheetPath = offerSheetManager.generateOfferSheet(applicantOfferModel, "preview", getUserId(),getRequest());
			FileHandler fileHandler = new FileHandler();
			String contentType = fileHandler.getContentType(offerSheetPath[0]);				
			setReqAttr("filePath", offerSheetPath[0]);
			setReqAttr("contentType", contentType);
			setReqAttr("fileName", offerSheetPath[2]);
			setReqAttr("contentDisposition", DocumentConstants.CONTENT_DISPOSITION_ATTACHMENT);				
		}catch (Exception e) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			addActionError(TPLabels.getLabel("generate_offer_sheet.error.error_generating_offer"));
			offerTemplateVariableMapping();
			return ERROR;
		}
		return OUTPUT_AS_DOCUMENT;
	}
	
	public String exportOffer(){
		try {
			OfferSheetManager offerSheetManager = new OfferSheetManager();
			String offerSheetPath = offerSheetManager.getApplicantCurrentOfferSheet(applicantOfferModel.getApplicantId());
			if(!Utils.isBlankOrNull(offerSheetPath)){
				setOfferSheetPath(offerSheetPath);
			}else{
				addActionError(TPLabels.getLabel("generate_offer_sheet.error.offer_does_not_exist"));
				return ERROR;
			}
		} catch (Exception e) {
			addActionError(TPLabels.getLabel("generate_offer_sheet.error.could_not_get_offer_sheet"));
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return SUCCESS;
	}


	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	@Override
	public ApplicantOfferModel getModel() {
		return applicantOfferModel;
	}


	/**
	 * @return the feedbackData
	 */
	public FeedbackData getFeedbackData() {
		return feedbackData;
	}


	/**
	 * @param feedbackData the feedbackData to set
	 */
	public void setFeedbackData(final FeedbackData feedbackData) {
		if(ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,getPermissionSet().isSHOW_CONFIDENTIAL_DATA())){
			String source = getText("common.openingRoundBracket")+getText("common.source")+" "+feedbackData.getApplicantSourceTitle()+getText("common.closingRoundBracket");
			feedbackData.setApplicantName(feedbackData.getApplicantName()+" "+source);
		}
		this.feedbackData = feedbackData;
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
	 * @return the jsArrayOfferSheetTemplates
	 */
	public String getJsArrayOfferSheetTemplates() {
		return jsArrayOfferSheetTemplates;
	}


	/**
	 * @param jsArrayOfferSheetTemplates the jsArrayOfferSheetTemplates to set
	 */
	public void setJsArrayOfferSheetTemplates(String jsArrayOfferSheetTemplates) {
		this.jsArrayOfferSheetTemplates = jsArrayOfferSheetTemplates;
	}


	/**
	 * @return the jsArrayMapping
	 */
	public String getJsArrayMapping() {
		return jsArrayMapping;
	}


	/**
	 * @param jsArrayMapping the jsArrayMapping to set
	 */
	public void setJsArrayMapping(String jsArrayMapping) {
		this.jsArrayMapping = jsArrayMapping;
	}


	/**
	 * @return the applicantData
	 */
	public ApplicantData getApplicantData() {
		return applicantData;
	}


	/**
	 * @param applicantData the applicantData to set
	 */
	public void setApplicantData(ApplicantData applicantData) {
		this.applicantData = applicantData;
	}

	/**
	 * @return the offerSheetPath
	 */
	public String getOfferSheetPath() {
		return offerSheetPath;
	}


	/**
	 * @param offerSheetPath the offerSheetPath to set
	 */
	public void setOfferSheetPath(String offerSheetPath) {
		this.offerSheetPath = offerSheetPath;
	}


	/**
	 * @return the applicantTitle
	 */
	public String getApplicantTitle() {
		return applicantTitle;
	}


	/**
	 * @param applicantTitle the applicantTitle to set
	 */
	public void setApplicantTitle(String applicantTitle) {
		this.applicantTitle = applicantTitle;
	}
}
