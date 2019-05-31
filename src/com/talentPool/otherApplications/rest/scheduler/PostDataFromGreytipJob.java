/**
 * 
 */
package com.talentPool.otherApplications.rest.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.otherApplications.db.DBDataObject;
import com.talentPool.otherApplications.db.OtherDBConnection;
import com.talentPool.otherApplications.rest.client.RestDataProcessorClient;
import com.talentPool.otherApplications.rest.model.ApplicantDetailModel;
import com.talentPool.otherApplications.rest.model.ListApplicantDetailModel;

/**
 * @author Shantanu
 *
 */
public class PostDataFromGreytipJob implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TPLogger.getLogger().debug("************ GREYTIP POSTDATA JOB STARTED ************");
		
		if (PostDataFromGreytipScheduler.JOB_STATUS_BUZY) {
			TPLogger.getLogger().debug("GREYTIP POSTDATA JOB ALREADY RUNNING. EXITING GREYTIP POSTDATA JOB ");
			return;
		}
		PostDataFromGreytipScheduler.JOB_STATUS_BUZY = true;
		
		ListApplicantDetailModel listApplicantDetails = new ListApplicantDetailModel();		
		RestDataProcessorClient restDataProcessorClient = new RestDataProcessorClient();
		OtherDBConnection otherDBConnection = new OtherDBConnection();
		try{
			String dquery = buidSelectQuery();
			
			List<DBDataObject> lstStr =  otherDBConnection.readDataFromDB(dquery);
			if(lstStr==null || lstStr.size()==0){
				TPLogger.getLogger().debug("Exiting POSTDATA JOB");
				PostDataFromGreytipScheduler.JOB_STATUS_BUZY = false;
				return;		
			}
			if(lstStr!=null && !lstStr.isEmpty()){
				List<ApplicantDetailModel> listAdm = new ArrayList<ApplicantDetailModel>();
				for (DBDataObject dbdo : lstStr) {
					ApplicantDetailModel applicantDetailModel = new ApplicantDetailModel();
					applicantDetailModel.employeeCode=dbdo.getAttribute("EMPLOYEENO").toString();
					applicantDetailModel.applicantId=dbdo.getAttribute("TP_APPLICANTID").toString();
					String dQry=buidUpdateQuery(applicantDetailModel);
					System.out.println("UPDATE == "+dQry);
					TPLogger.getLogger().debug("UPDATE == "+dQry);
					otherDBConnection.updateDataOfDB(dQry);
					listAdm.add(applicantDetailModel);
				}
				listApplicantDetails.applicantsDetail = listAdm;			
				restDataProcessorClient.postToRestApplicantDetailData(listApplicantDetails);
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		TPLogger.getLogger().debug("************ GREYTIP POSTDATA JOB FINISHED ************");
		PostDataFromGreytipScheduler.JOB_STATUS_BUZY = false;
	}	
	
	public String buidSelectQuery(){
		String dQuery = null;
		try{
			dQuery = "SELECT TP_APPLICANTID,EMPLOYEENO FROM "+OtherApplicationConstants.GREYTIP_FETCH_DB_TABLE_NAME;
			dQuery += " WHERE EMPLOYEENO is not NULL AND EMPLOYEECODEFLAG is NULL";
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return dQuery;
	}
	
	public String buidUpdateQuery(ApplicantDetailModel applicantDetailModel){
		String dQuery = null;
		try{
			dQuery = "UPDATE " + OtherApplicationConstants.GREYTIP_FETCH_DB_TABLE_NAME;
			dQuery +=  " SET EMPLOYEECODEFLAG = '1' WHERE EMPLOYEENO = '"+applicantDetailModel.employeeCode+"' ;";
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return dQuery;
	}
}