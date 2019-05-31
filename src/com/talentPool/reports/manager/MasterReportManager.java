/**
 * 
 */
package com.talentPool.reports.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.MasterReportData;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class MasterReportManager {

	public ArrayList<MasterReportData> getApplicantsMasterData(FilterData filterData, PermissionSet permissionSet){
		DBPreparedQuery dq = null;
		ArrayList<MasterReportData> applicantData = new ArrayList<MasterReportData>();
		try{
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = " ";
			if(!filterData.getDepartmentId().equals("0")){
				dynParam[0]= " AND tp.dept_id = ? ";
				dynamicContent.add(filterData.getDepartmentId());					
			}
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[0] += " AND ta.is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			Date toDate = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
			toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
			
			dq = new DBPreparedQuery("dMasterReportManager_GetApplicantMasterData",dynParam);
			dq.setInt(1,UserConstants.ROLE_RECRUITER);
			dq.setDate(2,Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat));
			dq.setDate(3,Utils.convertDateToSQLDate(toDate));
			dq.setDate(4,Utils.convertToSQLDate(filterData.getFromDate(), Utils.regEUDateFormat));
			dq.setString(5, SelectionProcessConstants.STEP_REJECT);
			dq.setString(6, SelectionProcessConstants.STEP_JOIN);
			dq.setString(7, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(8, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(9, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			int cnt = 10;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			applicantData = dq.getResult();

		}catch (Exception e) {
			TPLogger.getLogger().error("Error while getting applicant master data", e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicantData;
	}
	
	public ArrayList<MasterReportData> getApplicantInteractionData(String applicantId, String positionId, 
			String lastProcessId){
		DBPreparedQuery dq = null;
		ArrayList<MasterReportData> iData = new ArrayList<MasterReportData>();
		try{
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = " "; 	// AND appointment_date_created &lt;''
			dq = new DBPreparedQuery("dMasterReportManager_GetApplicantInteractionData",dynParam);
			dq.setInt(1, UserConstants.ROLE_HR_MANAGER);
			dq.setInt(2, UserConstants.ROLE_RECRUITER);
			dq.setString(3, applicantId);
			dq.setString(4, positionId);
			dq.setString(5, lastProcessId);
			dq.setString(6, applicantId);
			dq.setString(7, positionId);
			dq.setString(8, lastProcessId);
			dq.setString(9, SelectionProcessConstants.STEP_REJECT);
			iData = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while getting applicant interaction data", e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return iData;
	}
	
	public ArrayList<SimpleDataObject> getPositionIdsWithJoiningCandidates(){
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> positionIds = new ArrayList();
		try{
			dq = new DBPreparedQuery("dMasterReportManager_GetPositionIdsWithJoiningCandidates");
			positionIds = dq.getResult();
			
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while getting PositionIds With JoiningCandidates", e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionIds;
	}
	
}
