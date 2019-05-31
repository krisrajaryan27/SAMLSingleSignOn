/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.applicant.dao;

import java.sql.SQLException;

import com.talentPool.applicant.dataobject.ApplicantCurrentDetails;
import com.talentPool.common.db.DBTransaction;

/**
 * @author PraveenK
 * @since  Apr 30, 2012
 */
public interface IApplicantCurrentDetailsDao {
	
	/**
	 * Fetches applicant current details for given applicantId
	 * @return
	 * @throws SQLException
	 */
	ApplicantCurrentDetails getApplicantCurrentDetails(String applicantId);
	
	/**
	 * Insert Applicant Current details into database 
	 * @return
	 * @throws SQLException
	 */
	void insertApplicantCurrentDetails(ApplicantCurrentDetails applicantCurrentDetails, DBTransaction tran) throws SQLException;
	
	/**
	 * Update applicant current details for given applicantId
	 * @return
	 * @throws SQLException
	 */
	void updateApplicantCurrentDetails(ApplicantCurrentDetails applicantCurrentDetails, DBTransaction tran) throws SQLException;
	
	/**
	 * Insert or update applicant current details.
	 * <br>If applicantId is not null then updates the data against that id
	 * <br>If there is no record with the given applicantId then inserts data 
	 * @throws SQLException
	 */
	void insertOrUpdateApplicantCurrentDetails(ApplicantCurrentDetails applicantCurrentDetails, DBTransaction tran) throws SQLException;

}
