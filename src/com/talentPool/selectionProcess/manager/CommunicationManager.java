package com.talentPool.selectionProcess.manager;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.latestActivity.manager.LatestActivityManager;
import com.talentPool.salaryStructure.databject.ApplicantOfferDetails;
import com.talentPool.salaryStructure.utils.OfferProposalUtils;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.dataobject.CommunicationData;
import com.talentPool.selectionProcess.dataobject.OfferDetailsModifiedInteractionData;

public class CommunicationManager {
	
	public void logOfferModifiedInteraction(String applicantId, String userId){
		try {
			logOfferModifiedInteraction(applicantId, userId, new DBTransaction());
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void logOfferModifiedInteraction(String applicantId, String userId, DBTransaction tran){
		SelectionProcessManager selectionProcessManager = null;
		CommunicationData cData = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			cData = getCommunicationData(applicantId, userId, SelectionProcessConstants.INTERACTION_OFFER_DETAILS_MODIFIED,null);
			selectionProcessManager.addPhoneLog(cData, tran);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	private CommunicationData getCommunicationData(String applicantId,String userId,int interactiontype,String note){
		CommunicationData cData = new CommunicationData();
		Calendar cal = new GregorianCalendar();
		Date dtLogDate = cal.getTime();
		if(note==null)
			note = (String) SelectionProcessConstants.INTERACTION_TYPES.get(""+interactiontype);
		cData.setApplicantId(applicantId);
		cData.setUserId(userId);
		cData.setCommunicationType(interactiontype);
		cData.setCommunicationDate(new java.sql.Timestamp(dtLogDate.getTime()));
		cData.setCommunicationText(note);
		return cData;
	}
	
	public void logOfferProposalInteraction(String applicantId, ApplicantOfferDetails previousOfferDetails, ApplicantOfferDetails changedOfferDetails, int offerProposalAction, String userId, DBTransaction tran) throws SQLException{
		DBPreparedQuery dq = null;
		try {
			String activity = OfferProposalUtils.getOfferProposalActionTitle(offerProposalAction);
			if(tran!=null)
				dq = new DBPreparedQuery("dLogOfferProposalInteraction", tran);
			else
				dq = new DBPreparedQuery("dLogOfferProposalInteraction");
			dq.setId(1, applicantId);
			dq.setInt(2, SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL);
			dq.setString(3, activity);
			dq.setString(4, previousOfferDetails.getOfferedCTC());
			dq.setString(5, changedOfferDetails.getOfferedCTC());
			dq.setString(6, previousOfferDetails.getOfferedBasic());
			dq.setString(7, changedOfferDetails.getOfferedBasic());
			dq.setString(8, previousOfferDetails.getOfferedDesignation());
			dq.setString(9, changedOfferDetails.getOfferedDesignation());
			dq.setString(10, previousOfferDetails.getOfferedLevel());
			dq.setString(11, changedOfferDetails.getOfferedLevel());
			dq.setString(12, previousOfferDetails.getInputSalaryVariable());
			dq.setString(13, changedOfferDetails.getInputSalaryVariable());
			dq.setString(14, SelectionProcessConstants.INTERACTION_HIDE);
			dq.setId(15, userId);
			dq.execute();
			
			if(tran!=null)
				dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			else
				dq = new DBPreparedQuery("dFetchLastInsertID");
			String communicationId = dq.getIdResult();
			LatestActivityManager activityManager = new LatestActivityManager(); 
			String positionId = activityManager.getPositionIdWithApplicantId(applicantId);
			activity = TPLabels.getLabel("offer_proposal.title.offer_proposal") + " (" + activity + ")";
			activityManager.addUserActivity(activity, communicationId, SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL, positionId, applicantId, userId, tran);
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
	
	public void logOfferGeneratedInteraction(String applicantId, String offerCode, String offerSheetName, 
						ApplicantOfferDetails previousOfferDetails, ApplicantOfferDetails changedOfferDetails, 
						boolean isModifyOffer, String userId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		String activity = null;
		try {
			if(isModifyOffer)
				activity = TPLabels.getLabel("generate_offer_sheet.text.offer_modified");
			else
				activity = TPLabels.getLabel("generate_offer_sheet.text.offer_generated");
			
			if(tran!=null)
				dq = new DBPreparedQuery("dLogOfferGenerationInteraction", tran);
			else
				dq = new DBPreparedQuery("dLogOfferGenerationInteraction");
			dq.setId(1, applicantId);
			dq.setInt(2, SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION);
			dq.setString(3, activity);
			dq.setString(4, previousOfferDetails.getOfferedCTC());
			dq.setString(5, changedOfferDetails.getOfferedCTC());
			dq.setString(6, previousOfferDetails.getOfferedBasic());
			dq.setString(7, changedOfferDetails.getOfferedBasic());
			dq.setString(8, previousOfferDetails.getOfferedDesignation());
			dq.setString(9, changedOfferDetails.getOfferedDesignation());
			dq.setString(10, previousOfferDetails.getOfferedLevel());
			dq.setString(11, changedOfferDetails.getOfferedLevel());
			dq.setId(12, Utils.isBlankOrNull(previousOfferDetails.getInputSalaryVariable())?null:previousOfferDetails.getInputSalaryVariable());
			dq.setId(13, Utils.isBlankOrNull(changedOfferDetails.getInputSalaryVariable())?null:changedOfferDetails.getInputSalaryVariable());
			dq.setString(14, SelectionProcessConstants.INTERACTION_HIDE);
			dq.setString(15, offerCode);
			dq.setString(16, offerSheetName);
			dq.setId(17, userId);
			dq.execute();
			
			if(tran!=null)
				dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			else
				dq = new DBPreparedQuery("dFetchLastInsertID");
			String communicationId = dq.getIdResult();
			LatestActivityManager activityManager = new LatestActivityManager(); 
			String positionId = activityManager.getPositionIdWithApplicantId(applicantId);
			activityManager.addUserActivity(activity, communicationId, SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL, positionId, applicantId, userId, tran);
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
	
	public OfferDetailsModifiedInteractionData getOfferProposalInteractionData(String interactionId)  {
		DBPreparedQuery dq = null;
		OfferDetailsModifiedInteractionData odmi = null;
		try {
			dq = new DBPreparedQuery("dCommunicationManager_getOfferProposalInteractionData");
			dq.setId(1, interactionId);
			odmi = (OfferDetailsModifiedInteractionData)dq.getSingleObjectResult();
		}catch(SQLException e){
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return odmi;
	}
}
