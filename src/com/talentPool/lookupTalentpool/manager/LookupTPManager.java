/**
 * 
 */
package com.talentPool.lookupTalentpool.manager;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.lookupTalentpool.properties.LookupTPConstant;

/**
 * @author Shantanu
 *
 */
public class LookupTPManager {
	
	/*
	 * Getting all shortlisted applicant's data who is not checked for duplication before.
	 * there may be a question "why those who are not checked?" 
	 * because even if a candidate is shortlisted in lookup talentpool 
	 * this already checked candidate in local talentpool is not marked with any flag 
	 */
	public ArrayList getApplicantListForLookup(){
		ArrayList applicantListForLookup = new ArrayList();
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dLookupManager_ApplicantsForLookup");
			dq.setString(1, LookupTPConstant.LOOKUP_TP_UNCHECKED);
			applicantListForLookup = dq.getResult();
			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
		return applicantListForLookup;
	}
	/*
	 * Update those candidate who are checked for duplication
	 * need to write the query
	 */
	public ArrayList updateApplicantListCheckedForLookup(String applicantId, String flagId){
		ArrayList applicantListForLookup = new ArrayList();
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dLookupManager_UpdateApplicantsCheckedForLookup");
			dq.setString(1, LookupTPConstant.LOOKUP_TP_CHECKED);
			dq.setString(2, flagId);
			dq.setString(3, applicantId);						
			dq.execute();			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
		return applicantListForLookup;
	}
	
	public void addShortlistedForLookup(SimpleDataObject shortlistData){		
		DBPreparedQuery dq = null;
		try{			
			dq = new DBPreparedQuery("dLookupManager_AddEntryForLookup");
			dq.setId(1, shortlistData.getString("applicantId"));
			dq.setId(2, shortlistData.getString("positionId"));
			dq.setId(3, shortlistData.getString("positionStepIdTo"));
			dq.setId(4, shortlistData.getString("userId"));
			dq.execute();		
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR, e);
		}
	}
	
	/*
	 * This method will fetch all the shortlisted candidate from the local Talentpool when scheduler runs.
	 */
	//public ArrayList<SimpleDataObject> fetchShortlistedForLookup(String applicantId){
	public ArrayList<SimpleDataObject> duplicateApplicantLookupData(String applicantId){
		ArrayList<SimpleDataObject> shortlistedApplicantList = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dLookupManager_FetchShortlistedCandidate");
			dq.setString(1, applicantId);
			shortlistedApplicantList = dq.getResult();			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR, e);
		}
		return shortlistedApplicantList;
	}
	
	public ArrayList<SimpleDataObject> duplicateShortlistedFromLookup(String applicantName,String applicantEmail2,String applicantEmail1,String applicantCellPhone,String applicantHomePhone,String applicantWorkPhone){
		ArrayList<SimpleDataObject> duplicateShortlistedList = new ArrayList<SimpleDataObject>();
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dLookupManager_ApplicantsCheckDuplicateAtLookup");
			dq.setString(1, applicantName);
			dq.setString(2, applicantEmail1);
			dq.setString(3, applicantEmail1);
			dq.setString(4, applicantEmail2);			
			dq.setString(5, applicantEmail2);
			
			dq.setString(6, applicantCellPhone);
			dq.setString(7, applicantWorkPhone);
			dq.setString(8, applicantHomePhone);
			
			dq.setString(9, applicantCellPhone);
			dq.setString(10, applicantWorkPhone);
			dq.setString(11, applicantHomePhone);
			
			dq.setString(12, applicantCellPhone);
			dq.setString(13, applicantWorkPhone);
			dq.setString(14, applicantHomePhone);
			
			duplicateShortlistedList = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return duplicateShortlistedList;
	}
	
	public String getFlagIdForLookupStepLevel(String lookupStepLevel){
		String stepLevel = null;
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dLookupManager_GetFlagIdForLookupStepLevel");
			dq.setString(1, lookupStepLevel);
			stepLevel = dq.getIdResult();			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR, e);
		}
		return stepLevel;
	}
	
	
	public String responsibleUserEmails(String applicantId){
		String userEmails = null;
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dLookupManager_responsibleUserEmails");
			dq.setString(1, applicantId);
			dq.setString(2, LookupTPConstant.ROLE_RECRUITER);
			dq.setString(3, LookupTPConstant.ROLE_HR_MANAGER);
			userEmails = dq.getIdResult();			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR, e);
		}
		return userEmails;
	}

}


