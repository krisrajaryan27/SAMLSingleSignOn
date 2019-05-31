/**
 * 
 */
package com.talentPool.otherApplications.scheduler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.otherApplications.manager.ExcelGenerator;
import com.talentPool.otherApplications.manager.OtherApplicationManager;
import com.talentPool.user.UserConstants;


/**
 * @author shantanu
 *
 */
public class StepLevelChangeCSVJob implements Job {
	
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TPLogger.getLogger().debug("************ STEP LEVEL CHANGE CSV JOB STARTED ************");
		
		if (StepLevelChangeCSVScheduler.JOB_STATUS_BUZY) {
			TPLogger.getLogger().debug("STEP LEVEL CHANGE CSV JOB ALREADY RUNNING. EXITING STEP LEVEL CHANGE CSV JOB ");
			return;
		}
		StepLevelChangeCSVScheduler.JOB_STATUS_BUZY = true;
		
		OtherApplicationManager oam =new OtherApplicationManager();
		ExcelGenerator exlg = new ExcelGenerator();
		ArrayList<String> fieldList = new ArrayList<String>();
		ArrayList<SimpleDataObject> applicantsInStepLevelChange = new ArrayList<SimpleDataObject>();
		try{
			applicantsInStepLevelChange = oam.getApplicantsForStepLevelChangeCSV();
			if(!applicantsInStepLevelChange.isEmpty()){
				ArrayList<ApplicantData> applicantsDataInStepLevelChange = getAllAcceptCandidateData(applicantsInStepLevelChange);			
				LinkedHashMap<String, String> fieldsMap = oam.getAllFieldsMap();			
				fieldList = exlg.getAllCandidateFieldsToExport(fieldsMap);
				exlg.exportToCSV(applicantsDataInStepLevelChange, fieldList, fieldsMap);			
				oam.updateStepLevelChangeSchedulerJob(applicantsInStepLevelChange);
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		TPLogger.getLogger().debug("************ STEP LEVEL CHANGE CSV JOB FINISHED ************");
		StepLevelChangeCSVScheduler.JOB_STATUS_BUZY = false;
	}
	
	private static ArrayList<ApplicantData> getAllAcceptCandidateData(ArrayList<SimpleDataObject> applicantsInAccept){
		//ArrayList<SimpleDataObject> applicantsInAccept = new ArrayList<SimpleDataObject>();
		ArrayList<ApplicantData> applicantsDataInAccept = new ArrayList<ApplicantData>();
		OtherApplicationManager oam = new OtherApplicationManager();
		ApplicantManager applicantManager = new ApplicantManager();
		try{			
			for(SimpleDataObject sdo : applicantsInAccept){
				ApplicantData applicantData = applicantManager.getApplicantDisplayData(sdo.getString("applicantId"), UserConstants.ADMIN_ID);
				applicantsDataInAccept.add(applicantData);
			}
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR, e);
		}
		return applicantsDataInAccept;
	}
	
//	public static void main(String[] args) {
//		try{
//			ArrayList<SimpleDataObject> applicantsInAccept = new ArrayList<SimpleDataObject>();
//			OtherApplicationManager oam =new OtherApplicationManager();
//			applicantsInAccept = oam.getApplicantsForAckrutiCSV();
//			ArrayList<ApplicantData> applicantsDataInAccept = getAllAcceptCandidateData(applicantsInAccept);
//			ExcelGenerator exlg = new ExcelGenerator();
//			LinkedHashMap<String, String> fieldsMap = OtherApplicationManager.getAllFieldsMap();
//			ArrayList<String> fieldList = new ArrayList<String>();
//			fieldList = exlg.getAllCandidateFieldsToExport(fieldsMap);						
//			exlg.exportToCSV(applicantsDataInAccept, fieldList, fieldsMap);
//			String str = Utils.convertArrayListIntoCommaSptdString(applicantsInAccept);
//			//System.out.println(str);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public static void main(String[] args) {
		OtherApplicationManager oam =new OtherApplicationManager();
		ArrayList<SimpleDataObject> arrLst = oam.getDoc();
		try{
			for (SimpleDataObject sdo : arrLst) {
				String resumePath = sdo.getString("resumePath");
				String orignalPath = sdo.getId("originalPath");
				String resText = sdo.getId("textResume");
				String destinationPath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"),TPApplicationProperties.getProperty("documents.dir"));
				String docFileName = Utils.concatFilePath(destinationPath,orignalPath);
				File f = new File(docFileName);
				String dirPath = docFileName.replace(f.getName(),"") ;
				File fl = new File(dirPath);
				if(!f.exists()){
					fl.mkdirs();
					f.createNewFile();				
					//if(){
						FileWriter fwrt = new FileWriter(docFileName);
						BufferedWriter out = new BufferedWriter(fwrt);
					
						out.write(resText);
						out.close();
					//}
				}				
			}
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
}