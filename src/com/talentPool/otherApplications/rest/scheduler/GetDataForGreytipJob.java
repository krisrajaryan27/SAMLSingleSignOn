/**
 * 
 */
package com.talentPool.otherApplications.rest.scheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.otherApplications.db.DBFieldMapFromXMLFile;
import com.talentPool.otherApplications.db.OtherDBConnection;
import com.talentPool.otherApplications.rest.client.RestDataProcessorClient;
import com.talentPool.otherApplications.rest.model.ApplicantDetailModel;
import com.talentPool.otherApplications.rest.model.ListApplicantDetailModel;

/**
 * @author Shantanu
 *
 */
public class GetDataForGreytipJob implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TPLogger.getLogger().debug("************ GREYTIP GETDATA JOB STARTED ************");
		
		if (GetDataForGreytipScheduler.JOB_STATUS_BUZY) {
			TPLogger.getLogger().debug("GREYTIP GETDATA JOB ALREADY RUNNING. EXITING GREYTIP GETDATA JOB ");
			return;
		}
		GetDataForGreytipScheduler.JOB_STATUS_BUZY = true;
		
		RestDataProcessorClient restDataProcessorClient = new RestDataProcessorClient();
		OtherDBConnection otherDBConnection = new OtherDBConnection();
		try{
			ListApplicantDetailModel listApplicantDetails = restDataProcessorClient.getFromRestApplicantDetailData();
			if(listApplicantDetails==null || listApplicantDetails.applicantsDetail.size()==0){
				TPLogger.getLogger().debug("Exiting GETDATA JOB");
				GetDataForGreytipScheduler.JOB_STATUS_BUZY = false;
				return;
			}			
			if(listApplicantDetails.applicantsDetail!=null && !listApplicantDetails.applicantsDetail.isEmpty()){
				for(ApplicantDetailModel adm : listApplicantDetails.applicantsDetail){
					String dQuery = buidInsertQuery(adm);
					TPLogger.getLogger().debug("QUERY === "+dQuery);
					otherDBConnection.insertDataIntoDB(dQuery);
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		TPLogger.getLogger().debug("************ GREYTIP GETDATA JOB FINISHED ************");
		GetDataForGreytipScheduler.JOB_STATUS_BUZY = false;
	}
		

	public String buidInsertQuery(ApplicantDetailModel applicantDetailModel){	
		String dQuery = null;
		Map<String, String> map = new HashMap<String, String>(); 
		try{
			DBFieldMapFromXMLFile dbxmlmap = new DBFieldMapFromXMLFile();
			map = dbxmlmap.mapColumns(applicantDetailModel);			
			Iterator<String> itr = map.keySet().iterator();
			StringBuilder strBldrCol = new StringBuilder();
			StringBuilder strBldrVal = new StringBuilder();
			while(itr.hasNext()){
				String colName = itr.next();
				strBldrCol = strBldrCol.append("["+colName+"]");
				strBldrVal = strBldrVal.append(map.get(colName));
				if(itr.hasNext()){
					strBldrCol = strBldrCol.append(","); 
					strBldrVal = strBldrVal.append(",");
				}
			}
			dQuery = "INSERT INTO " + OtherApplicationConstants.GREYTIP_INSERT_DB_TABLE_NAME;  
			dQuery+=" ("+strBldrCol+") VALUES ";
			dQuery+=" ("+strBldrVal+")";
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		return dQuery;
	}
}
