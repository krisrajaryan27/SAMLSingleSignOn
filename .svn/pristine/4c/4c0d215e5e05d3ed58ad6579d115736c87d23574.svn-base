package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CRColumnCustomFieldMapData;
import com.talentPool.customReports.constants.CustomReportConstants;
import com.talentPool.customReports.dataobject.ApplicantMasterData;
import com.talentPool.masters.constants.FeedbackFormConstants;
import com.talentPool.masters.utils.StepStaticUtils;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class CandidateMasterTableManager {

	public void updateCandidateMaster(DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		ArrayList<ApplicantMasterData> applicants = new ArrayList<ApplicantMasterData>();
		try {
			TPLogger.getLogger().info("========= START Candidate Master Update=============");
			dq = new DBPreparedQuery("dReportTableManager_DeleteAllRows", tran);
			dq.execute();

			new PositionMasterTableManager().updateCustomFieldReportColumns(tran);
			dq = new DBPreparedQuery("dReportTableManager_FetchCandidateData",tran);
			dq.setString(1, SelectionProcessConstants.STATUS_SYSTEM_GENERATED);
			dq.setString(2, FeedbackFormConstants.FIELD_TYPE_FIELD);
			applicants = dq.getResult();

			applicants = formatApplicantMasterData(applicants, tran);

			for (int i = 0; i < applicants.size(); i++) {
				ApplicantMasterData aData = applicants.get(i);
				dq = new DBPreparedQuery(
						"dReportTableManager_AddApplicantData", tran);

				int cnt = 1;
				dq.setString(cnt++, aData.getApplicantId());
				dq.setString(cnt++, aData.getApplicantName());
				dq.setString(cnt++, aData.getApplicantCity());
				dq.setString(cnt++, aData.getApplicantEmail1());
				dq.setString(cnt++, aData.getApplicantEmail2());
				dq.setString(cnt++, aData.getApplicantHomePhone());
				dq.setString(cnt++, aData.getApplicantCellPhone());
				dq.setString(cnt++, aData.getApplicantWorkPhone());
				dq.setDate(cnt++, aData.getApplicantWorkingSince());
				dq.setDate(cnt++, aData.getApplicantDateCreated());
				dq.setString(cnt++, aData.getCurrentCtc());
				dq.setString(cnt++, aData.getExpectedCtc());
				dq.setString(cnt++, aData.getApplicantNoticePeriod());
				dq.setString(cnt++, aData.getIsConfidential());
				dq.setString(cnt++, aData.getEmployeeCode());
				dq.setString(cnt++, aData.getApplicantHrmsCode());
				dq.setString(cnt++, aData.getApplicantJoined());
				dq.setDate(cnt++, aData.getApplicantDateJoined());
				dq.setString(cnt++, aData.getApplicantCurrentEmployer());
				dq.setString(cnt++, aData.getApplicantPositionId());
				dq.setString(cnt++, aData.getPositionCode());
				dq.setString(cnt++, aData.getPositionTitle());
				dq.setString(cnt++, aData.getDeptName());
				dq.setString(cnt++, aData.getApplicantStepId());
				dq.setString(cnt++, aData.getPositionStepTitle());
				dq.setString(cnt++, aData.getPositionStepLevel());
				dq.setString(cnt++, aData.getSourceId());
				dq.setString(cnt++, aData.getSourceTitle());
				dq.setString(cnt++, aData.getSourceTypeId());
				dq.setString(cnt++, aData.getSourceType());
				dq.setString(cnt++, aData.getUserId());
				dq.setString(cnt++, aData.getUserName());
				dq.setDate(cnt++, aData.getYOP());
				dq.setString(cnt++, aData.getGrade());
				dq.setString(cnt++, aData.getDegreeTitle());
				dq.setString(cnt++, aData.getBranchName());
				dq.setString(cnt++, aData.getInstituteName());
				dq.setString(cnt++, aData.getFlags());
				dq.setString(cnt++, aData.getSkills());
				dq.setString(cnt++, aData.getStatusMessage());
				dq.setString(cnt++, aData.getTrait());
				dq.setDate(cnt++, aData.getDateOfBirth());
				dq.setString(cnt++, aData.getPassportNumber());
				dq.setString(cnt++, aData.getResumeType());
				dq.setString(cnt++, aData.getOfferedCtc());
				dq.setString(cnt++, aData.getLevelOffered());
				dq.setString(cnt++, aData.getDesignationOffered());
				dq.setDate(cnt++, aData.getOfferDate());
				dq.setString(cnt++, aData.getOfferCode());
				dq.setString(cnt++, aData.getDegrees());
				dq.setString(cnt++, aData.getEmployer2());
				dq.setString(cnt++, aData.getEmployer3());
				for (String cusField : aData.getCustomFields()) {
					dq.setString(cnt++, cusField);
				}
				dq.execute();
			}
			TPLogger.getLogger().info("========= END Candidate Master Update=============");
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	private ArrayList<ApplicantMasterData> formatApplicantMasterData(
			ArrayList<ApplicantMasterData> applicants, DBTransaction tran) {
		try {

			TreeMap<Integer, CRColumnCustomFieldMapData> reportCustomFieldIdMap = new PositionMasterTableManager()
					.getReportCustomFieldIdMapFor(CustomFieldConstants.ENTITY_TYPE_APPLICANT);
			Set<Integer> cusFieldIds = reportCustomFieldIdMap.keySet();
			Map<String, ApplicantMasterData> rejectedApplicantPositionDatas = getLastPositionDetailsForRejectedCandidates(tran); 
					
			for (int i = 0; i < applicants.size(); i++) {
				ApplicantMasterData aData = applicants.get(i);
				String custom = aData.getCustom();
				String[] cusFields = new String[CustomReportConstants.CANDIDATE_CUSTOM_FIELD_NUMBER];
				if (!Utils.isBlankOrNull(custom)) {
					String[] customStr = custom.split("##");
					if (customStr != null && customStr.length > 0) {
						for (int j = 0; j < customStr.length; j++) {
							String customField = customStr[j];
							String[] customFieldPair = customField.split("=");
							if (null != customFieldPair	&& customFieldPair.length > 1
									&& !Utils.isBlankOrNull(customFieldPair[1])
									&& cusFieldIds.contains(Integer.parseInt(customFieldPair[0]))) {
								CRColumnCustomFieldMapData data = reportCustomFieldIdMap.get(Integer.parseInt(customFieldPair[0]));
								String displayName = data.getColumnDisplayName();
								int cf_number = Integer.parseInt(displayName.substring(displayName.lastIndexOf(' ') + 1));
								cusFields[cf_number-1] = customFieldPair[1];
							}
						}
					}
				}
				aData.setCustomFields(cusFields);
				
				String degrees = aData.getDegrees();
				if(!Utils.isBlankOrNull(degrees)) {
					String[] allDegrees = degrees.split("##");
					if(allDegrees.length > 1) {
						if(allDegrees[0].equals(aData.getDegreeTitle())) {
							aData.setDegrees(allDegrees[1]);
						} else {
							aData.setDegrees(allDegrees[0]);
						}
					} else {
						aData.setDegrees("");
					}
				}
				
				String employers = aData.getEmployer2();
				if(!Utils.isBlankOrNull(employers)) {
					String[] allEmployers = employers.split("##");
					aData.setEmployer2("");
					aData.setEmployer3("");
					if(allEmployers.length > 1) {
						aData.setEmployer2(allEmployers[1]);
						if(allEmployers.length > 2) {
							aData.setEmployer3(allEmployers[2]);
						}
					} 
				}
				
				if(Utils.isBlankOrNull(aData.getApplicantPositionId()) && rejectedApplicantPositionDatas != null) {
					ApplicantMasterData appData = rejectedApplicantPositionDatas.get(aData.getApplicantId());
					if (appData != null) {
						updatePositionDetailsForRejectedCandidate(aData, appData);
					}
				}
			}
		
		}		
		 catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return applicants;
	}

	/**
	 * @param tran
	 * @return list of rejected applicants with their last position details 
	 */
	@SuppressWarnings("unchecked")
	private Map<String, ApplicantMasterData> getLastPositionDetailsForRejectedCandidates(DBTransaction tran) {
		Map<String, ApplicantMasterData> applicantMap = new HashMap<String, ApplicantMasterData>();
		DBPreparedQuery dq = null;
		ArrayList<ApplicantMasterData> applicants = new ArrayList<ApplicantMasterData>();
		try {
			int cnt = 1;
			dq = new DBPreparedQuery("dReportTableManager_RejectedCandidatePositionDetails", tran);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			applicants = (ArrayList<ApplicantMasterData>)dq.getResult();
			
			for(ApplicantMasterData applicant : applicants) {
				applicantMap.put(applicant.getApplicantId(), applicant);
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().info("Error while getting last position details for rejected candidates");			
		}
		return applicantMap;
	}

	/**
	 * @param aData
	 * @param applicantMasterDataWithPositionDetails
	 */
	private void updatePositionDetailsForRejectedCandidate(ApplicantMasterData aData,
			ApplicantMasterData applicantMasterDataWithPositionDetails) {
		aData.setApplicantPositionId(applicantMasterDataWithPositionDetails.getApplicantPositionId());
		aData.setPositionCode(applicantMasterDataWithPositionDetails.getPositionCode());
		aData.setPositionTitle(applicantMasterDataWithPositionDetails.getPositionTitle());
		aData.setDeptName(applicantMasterDataWithPositionDetails.getDeptName());
		aData.setApplicantStepId(applicantMasterDataWithPositionDetails.getApplicantStepId());
		aData.setPositionStepTitle(applicantMasterDataWithPositionDetails.getPositionStepTitle());
		aData.setPositionStepLevel(applicantMasterDataWithPositionDetails.getPositionStepLevel());
	}

	public static void main(String[] args) {
		CandidateMasterTableManager manager = new CandidateMasterTableManager();
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			manager.updateCandidateMaster(tran);
			tran.commit();
		}catch (Exception e) {
			try {
				tran.rollback();
			} catch (SQLException e1) {
			}
		}
	}
	
	public Map<String, ApplicantMasterData> getApplicantNameMapWithAttributes(){
		DBPreparedQuery dq = null;
		List<ApplicantMasterData> aDataList = null;
		Map<String, ApplicantMasterData> aDataMap = null;
		try {
			dq = new DBPreparedQuery("dCandidateMaster_getApplicantNamesWithAttributes");
			dq.setString(1, StepStaticUtils.getJoinedStepName());
			aDataList = dq.getResult();
			aDataMap = new HashMap<String,ApplicantMasterData>(aDataList.size(),1);
			for (ApplicantMasterData aData : aDataList) {
				aDataMap.put(aData.getApplicantId(), aData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching Applicant Names map wth attributes", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return aDataMap;
	}
	
	public Map<String, ApplicantMasterData> getApplicantNameMap(){
		DBPreparedQuery dq = null;
		List<ApplicantMasterData> aDataList = null;
		Map<String, ApplicantMasterData> aDataMap = null;
		try {
			dq = new DBPreparedQuery("dCandidateMaster_getApplicantNames");
			aDataList = dq.getResult();
			aDataMap = new HashMap<String,ApplicantMasterData>(aDataList.size(),1);
			for (ApplicantMasterData aData : aDataList) {
				aDataMap.put(aData.getApplicantId(), aData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching Applicant Names map", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return aDataMap;
	}
}

