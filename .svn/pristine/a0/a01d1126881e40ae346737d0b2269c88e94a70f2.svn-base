/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.salaryStructure.service;

import java.util.List;

import com.talentPool.applicant.dataobject.ApplicantCurrentDetails;
import com.talentPool.salaryStructure.databject.SalaryCategoriesComparisonData;
import com.talentPool.salaryStructure.model.OfferProposalModel;

/**
 * @author PraveenK
 * @since  Apr 23, 2012
 */
public interface IOfferProposalService {
	
	/**
	 * Builds Comparison data for given applicant.
	 * <BR>Fetches Existing values if any exist.
	 * <BR>Fetches Offered CTC and Basic  details.
	 * <BR>Calculates the Proposed values based on offered CTC and Basic.
	 * @param applicantId
	 * @return {@link SalaryCategoriesComparisonData} for given applicantId
	 */
	List<SalaryCategoriesComparisonData> getSalCategoriesComparisonData(String applicantId);
	
	/**
	 * Builds Comparison data for given applicant.
	 * <BR>Fetches Existing values if any exist.
	 * <BR>Calculates the Proposed values based on offered CTC and Basic provided.
	 * @param applicantId
	 * @param offeredCTC
	 * @param offferedBasic
	 * @param grade
	 * @return {@link SalaryCategoriesComparisonData} for given applicantId and Offered details.
	 */
	List<SalaryCategoriesComparisonData> getSalCategoriesComparisonData(String applicantId, String offeredCTC, String offferedBasic, String inputSalVariable, String grade);
	
	
	/**
	 * Saves Current details and some of the proposed values
	 * <BR>Current Details: CTC, Basic, Designation, Level and salary totals category level (like fixed, variable, reimbursements etc..) 
	 * <BR>Proposed Details: CTC, Basic, Designation, Level
	 * @param ctcComparisonModel
	 * @throws Exception
	 */
	void saveCTCComparisonGrid(final OfferProposalModel ctcComparisonModel, String userId) throws Exception;
	
	
	/**
	 * Fetches Applicant current details (basic, designation, level) for given applciantId 
	 * @param applicantId
	 * @return null if no current details present
	 */
	ApplicantCurrentDetails getApplicantCurrentDetails(String applicantId);
	
}
