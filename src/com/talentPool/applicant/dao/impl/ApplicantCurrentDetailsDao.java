/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.applicant.dao.impl;

import java.sql.SQLException;
import java.sql.Types;

import com.talentPool.applicant.dao.IApplicantCurrentDetailsDao;
import com.talentPool.applicant.dataobject.ApplicantCurrentDetails;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.utils.Utils;

/**
 * @author PraveenK
 * @since  Apr 30, 2012
 */
public class ApplicantCurrentDetailsDao implements IApplicantCurrentDetailsDao {

	
	/* (non-Javadoc)
	 * @see com.talentPool.applicant.dao.IApplicantCurrentDetailsDao#getApplicantCurrentDetails(java.lang.String)
	 */
	@Override //TODO: Fetch the data using hibernate after Entity beans are ready for tp_applicant_current_details and tp_applicants   
	public ApplicantCurrentDetails getApplicantCurrentDetails(String applicantId) {
		DBPreparedQuery dq = null;
		ApplicantCurrentDetails aCurrentDetails = null;
		try {
			dq = new DBPreparedQuery("dGetApplicantCurrentDetails");
			dq.setId(1, applicantId);
			aCurrentDetails = (ApplicantCurrentDetails) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while fetching applicant current details for applicant:"+applicantId, e);
			return null;
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return aCurrentDetails;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.applicant.dao.IApplicantCurrentDetailsDao#insertApplicantCurrentDetails(java.lang.String)
	 */
	@Override
	public void insertApplicantCurrentDetails(ApplicantCurrentDetails applicantCurrentDetails, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dInsertApplicantCurrentDetails",tran);				
			}else{
				dq = new DBPreparedQuery("dInsertApplicantCurrentDetails");
			}
			dq.setString(1, applicantCurrentDetails.getApplicantId());
			dq.setInt(2, applicantCurrentDetails.getCurrentBasic().intValue());
			dq.setString(3, applicantCurrentDetails.getCurrentDesignation());
			dq.setString(4, applicantCurrentDetails.getCurrentLevel());
			dq.execute();
		}catch(SQLException e){
			TPLogger.getLogger().error("SQL Exception in inserting the current details of applicant: "+applicantCurrentDetails.getApplicantId(), e);
			throw e;
		}catch(Exception e){
			TPLogger.getLogger().error("Some Exception in inserting the current details of applicant: "+applicantCurrentDetails.getApplicantId(), e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.applicant.dao.IApplicantCurrentDetailsDao#updateApplicantCurrentDetails(java.lang.String)
	 */
	@Override
	public void updateApplicantCurrentDetails(ApplicantCurrentDetails applicantCurrentDetails, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dUpdateApplicantCurrentDetails",tran);				
			}else{
				dq = new DBPreparedQuery("dUpdateApplicantCurrentDetails");
			}
			dq.setInt(1, applicantCurrentDetails.getCurrentBasic().intValue());
			dq.setString(2, applicantCurrentDetails.getCurrentDesignation());
			dq.setString(3, applicantCurrentDetails.getCurrentLevel());
			dq.setString(4, applicantCurrentDetails.getApplicantId());
			dq.execute();
		}catch(SQLException e){
			TPLogger.getLogger().error("SQL Exception in updating the current details of applicant: "+applicantCurrentDetails.getApplicantId(), e);
			throw e;
		}catch(Exception e){
			TPLogger.getLogger().error("Some Exception in updating the current details of applicant: "+applicantCurrentDetails.getApplicantId(), e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.applicant.dao.IApplicantCurrentDetailsDao#insertOrUpdateApplicantCurrentDetails(com.talentPool.applicant.dataobject.ApplicantCurrentDetails)
	 */
	@Override
	public void insertOrUpdateApplicantCurrentDetails(ApplicantCurrentDetails applicantCurrentDetails, DBTransaction tran) throws SQLException {
		String applicantId = applicantCurrentDetails.getApplicantId();
		if(isApplicantCurrentDetailsPresent(applicantId, tran)){
			updateApplicantCurrentDetails(applicantCurrentDetails, tran);
			if(applicantCurrentDetails.getCurrentCTC()!=null){
				saveCurrentCTC(applicantId, applicantCurrentDetails.getCurrentCTC(), tran);
			}
		}else{
			insertApplicantCurrentDetails(applicantCurrentDetails, tran);
			if(applicantCurrentDetails.getCurrentCTC()!=null){
				saveCurrentCTC(applicantId, applicantCurrentDetails.getCurrentCTC(), tran);
			}
		}
	}
	
	private void saveCurrentCTC(String applicantId, String currentCTC, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dUpdateApplicantCurrentCTC", tran);				
			}else{
				dq = new DBPreparedQuery("dUpdateApplicantCurrentCTC");
			}
			dq.setString(1, currentCTC);
			dq.setString(2, applicantId);
			dq.execute();
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	/**
	 * If a record is present given applicantId in Applicant Current details table then returns true else return false.
	 * @param applicantId
	 * @param tran
	 * @return
	 * @throws SQLException
	 */
	private boolean isApplicantCurrentDetailsPresent(String applicantId, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		int noOfRec = -1;
		try {
			if(tran!=null){
				dq = new DBPreparedQuery("dIsApplicantCurrentDetailsPresent",tran);				
			}else{
				dq = new DBPreparedQuery("dIsApplicantCurrentDetailsPresent");
			}
			dq.setString(1, applicantId);
			noOfRec = dq.getIntResult();
			if(noOfRec>0)
				return true;
		}catch (NoResultFoundException e) {
			return false;
		}  finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
		return false;
	}
}
