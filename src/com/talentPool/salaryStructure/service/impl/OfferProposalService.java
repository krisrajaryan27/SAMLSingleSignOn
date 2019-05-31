/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.salaryStructure.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.applicant.dao.IApplicantCurrentDetailsDao;
import com.talentPool.applicant.dataobject.ApplicantCurrentDetails;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.salaryStructure.databject.ApplicantOfferDetails;
import com.talentPool.salaryStructure.databject.SalaryCategoriesComparisonData;
import com.talentPool.salaryStructure.databject.SalaryStructure;
import com.talentPool.salaryStructure.exception.SalaryCalculationException;
import com.talentPool.salaryStructure.manager.SalaryCalculator;
import com.talentPool.salaryStructure.manager.SalaryStructureManager;
import com.talentPool.salaryStructure.model.OfferProposalModel;
import com.talentPool.salaryStructure.service.IOfferProposalService;
import com.talentPool.selectionProcess.dataobject.FeedbackData;
import com.talentPool.selectionProcess.manager.CommunicationManager;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;

/**
 * @author PraveenK
 * @since  Apr 23, 2012
 */
public class OfferProposalService implements IOfferProposalService {
	
	private IApplicantCurrentDetailsDao _appCurrentDetailsDao;
	
	/**
	 * Injected though DI
	 * @param drTemplateService the drTemplateService to set
	 */
	public void setAppCurrentDetailsDao(IApplicantCurrentDetailsDao appCurrentDetailsDao) {
		this._appCurrentDetailsDao = appCurrentDetailsDao;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.salaryStructure.service.ICTCComparisonService#getSalCategoriesComparisonData(java.lang.String)
	 */
	@Override
	public List<SalaryCategoriesComparisonData> getSalCategoriesComparisonData(String applicantId) {
		List<SalaryCategoriesComparisonData> salCatComparisonData = null;
		ApplicantManager appManager = new ApplicantManager();
		SimpleDataObject sdo = appManager.getApplicantGradeCTCBasic(applicantId);
		if(sdo!=null){
			salCatComparisonData = getSalCategoriesComparisonData(applicantId, sdo.getString("offeredCtc"), sdo.getString("offeredBasic"), sdo.getString("inputSalaryVariable"), sdo.getString("gradeId"));	
		}else{
			salCatComparisonData = getSalCategoriesComparisonData(applicantId, null, null, null, null);			
		}
		return salCatComparisonData;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.salaryStructure.service.ICTCComparisonService#getSalCategoriesComparisonData(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<SalaryCategoriesComparisonData> getSalCategoriesComparisonData(String applicantId, String offeredCTC, String offferedBasic, String inputSalVariable, String gradeId) {
		List<SalaryCategoriesComparisonData> salCatComparisonDataLst = null;
		SalaryStructure salary = null;
		try {
			SalaryStructureManager ssm = new SalaryStructureManager();
			List<SimpleDataObject> existingSalDetails = ssm.getExistingSalaryDetails(applicantId);
			if(!Utils.isListEmptyOrNull(existingSalDetails)){
				salCatComparisonDataLst = new ArrayList<>();
				SalaryCalculator salCalculator = new SalaryCalculator();
				try {
					salary =  salCalculator.calculateSalary(gradeId, offeredCTC, offferedBasic, inputSalVariable);
				} catch (SalaryCalculationException e) {
					salary = null;
				}
				for (SimpleDataObject sdo : existingSalDetails) {
					SalaryCategoriesComparisonData salCatComparisonData = new SalaryCategoriesComparisonData();
					salCatComparisonData.setCategoryId(sdo.getString("categoryId"));
					salCatComparisonData.setCategoryName(sdo.getString("categoryName"));
					salCatComparisonData.setExistingVal(sdo.getInt("existingValue"));
					if(salary!=null){
						salCatComparisonData.setProposedVal(salary.getAnnualCatValue(sdo.getString("categoryId")));						
					}
					salCatComparisonDataLst.add(salCatComparisonData);
				}
			}else{
				TPLogger.getLogger().debug("No Salary Categories Defined in Application");
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Could Not retrieve Existing Salary Details for applicantId: "+ applicantId, e);
		}
		return salCatComparisonDataLst;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.salaryStructure.service.ICTCComparisonService#saveCTCComparisonGrid(com.talentPool.salaryStructure.model.CTCComparisonModel)
	 */
	@Override
	public void saveCTCComparisonGrid(OfferProposalModel offerProposalModel, String userId) throws Exception {
		DBTransaction tran = null;
		String applicantId = offerProposalModel.getApplicantId();
		boolean isApplciantHireStage;
		try {
			SelectionProcessManager spm = new SelectionProcessManager();
			isApplciantHireStage = spm.isApplicantInHireStage(applicantId);
			tran = new DBTransaction();
			saveApplicantCurrentDetails(offerProposalModel, tran);
			if(isApplciantHireStage){
				saveApplicantOfferedDetails(offerProposalModel, userId, tran);
			}
			saveExistingSalaryDetails(offerProposalModel, tran);
			tran.commit();			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving CTC Comparison grid for applicant: "+applicantId, e);
			try {
				if(tran!=null)
					tran.rollback();
			} catch (Exception te) {
				TPLogger.getLogger().error("error in transaction roll back", te);
			}
			throw e;
		} finally {
			if(tran!=null)
				tran.release();
		}
	}
	
	/**
	 * Saves the applicant current details : CTC, basic, designation and level
	 * @param offerProposalModel
	 * @param tran
	 * @throws SQLException
	 */
	private void saveApplicantCurrentDetails(OfferProposalModel offerProposalModel, DBTransaction tran) throws SQLException{
		ApplicantCurrentDetails applicantCurrentDetails = new ApplicantCurrentDetails();
		applicantCurrentDetails.setApplicantId(offerProposalModel.getApplicantId());
		if(!Utils.isBlankOrNull(offerProposalModel.getCurrentBasic()))
			applicantCurrentDetails.setCurrentBasic(Integer.parseInt(offerProposalModel.getCurrentBasic()));
		else
			applicantCurrentDetails.setCurrentBasic(0);
		applicantCurrentDetails.setCurrentDesignation(offerProposalModel.getCurrentDesignation());
		applicantCurrentDetails.setCurrentLevel(offerProposalModel.getCurrentLevel());
		applicantCurrentDetails.setCurrentCTC(offerProposalModel.getCurrentCTC());
		_appCurrentDetailsDao.insertOrUpdateApplicantCurrentDetails(applicantCurrentDetails, tran);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.salaryStructure.service.ICTCComparisonService#getApplicantCurrentDetails(java.lang.String)
	 */
	@Override
	public ApplicantCurrentDetails getApplicantCurrentDetails(String applicantId) {
		return _appCurrentDetailsDao.getApplicantCurrentDetails(applicantId);
	}
	
	/**
	 * Saves existing salary details : salary Category totals
	 * @param offerProposalModel
	 * @param tran
	 */
	private void saveExistingSalaryDetails(OfferProposalModel offerProposalModel, DBTransaction tran) throws SQLException{
		String applicantId = offerProposalModel.getApplicantId();
		if(!Utils.isMapEmptyOrNull(offerProposalModel.getSalCatsExistingValMap())){
			try {
				SalaryStructureManager ssm = new SalaryStructureManager();
				ssm.saveExisitngSalaryDetails(applicantId, offerProposalModel.getSalCatsExistingValMap(), tran);
			} catch (SQLException e) {
				TPLogger.getLogger().error("Error in saving exiting salary details for applicant:"+applicantId, e);
				throw e;
			}
		}
	}
	
	/**
	 * Saves the Applicant Offered details. 
	 * <BR> If due to permission or confidentiality ctcComparisonModel may not have some offered details. So if for missing values fetches the data and then saves.
	 * @param offerProposalModel
	 * @param tran
	 * @throws SQLException
	 */
	private void saveApplicantOfferedDetails(OfferProposalModel offerProposalModel, String userId, DBTransaction tran) throws SQLException {
		SelectionProcessManager spm = new SelectionProcessManager();
		CommunicationManager communicationManager = new CommunicationManager();
		ApplicantOfferDetails changedOfferDetails 	= null;
		ApplicantOfferDetails previousOfferDetails 	= null;
		FeedbackData fd = spm.getApplicantOfferDetails(offerProposalModel.getApplicantId());
		String offeredCTC 	= offerProposalModel.getOfferedCTC();
		String offeredBasic = offerProposalModel.getOfferedBasic();
		String offeredDesignation = offerProposalModel.getOfferedDesignation();
		String offeredLevel = offerProposalModel.getOfferedLevel();
		String offeredInputSalaryVariable = offerProposalModel.getOfferedInputSalaryVariable();
		if(offeredCTC!=null || offeredBasic!=null || offeredDesignation!=null || offeredLevel!=null){
			if(fd!=null){
				if(offeredCTC==null){
					offeredCTC = fd.getCtcOffered();
				}
				if(offeredBasic==null){
					offeredBasic = fd.getBasicOffered();
				}
				if(offeredDesignation==null){
					offeredDesignation = fd.getDesignationOffered();
				}
				if(offeredLevel==null){
					offeredLevel = fd.getLevelOffered();
				}
			}
			changedOfferDetails  = new ApplicantOfferDetails(offeredCTC, offeredInputSalaryVariable, offeredBasic, offeredDesignation, offeredLevel);
			previousOfferDetails = buildApplicantOfferDetails(fd);
			spm.saveOfferDetails(offerProposalModel.getApplicantId(), changedOfferDetails, tran);
			communicationManager.logOfferProposalInteraction(offerProposalModel.getApplicantId(), previousOfferDetails, changedOfferDetails, offerProposalModel.getOfferProposalAction(), userId, tran);
		}
	}
	
	private ApplicantOfferDetails buildApplicantOfferDetails(FeedbackData fd){
		return new ApplicantOfferDetails(fd.getCtcOffered(), fd.getInputSalaryVariable(), fd.getBasicOffered(), fd.getDesignationOffered(), fd.getLevelOffered());
	}
	
}
