package com.talentPool.socialNetwork.scheduler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.socialNetwork.constants.SocialMediaConstants;
import com.talentPool.socialNetwork.dataobject.EducationHistory;
import com.talentPool.socialNetwork.dataobject.Person;
import com.talentPool.socialNetwork.dataobject.UploadPersonList;
import com.talentPool.socialNetwork.dataobject.WorkHistory;
import com.talentPool.socialNetwork.utils.SocialMediaUtils;

/**
 * @author SumeetS
 *
 */
public class ApplicantNodeUploadJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	
	/* :TODO
	1. insert applicant id , person id  in table graph table with pending status
	2. fetch all records from graph table with pending status 
	3. mould it into person object
	4. sequential graph insert api call with list of person objects
	5. response with person id and status map
	6. update the graph table
	*/
		JobDataMap map = context.getJobDetail().getJobDataMap();
		String applicantId = map.getString("applicantId");
		String userId = map.getString("userId");
		String uploadStatus = SocialMediaConstants.GRAPH_UPLOAD_PENDING;
		try{
			insertApplicantIntoGraphTable(applicantId,userId,uploadStatus);
			
			//fetches only Map of applicants ie where user_id is null
			@SuppressWarnings("unused")
			Map<String,Person> personMap  = fetchPendingApplicantUploads();
			UploadPersonList uploadPersonList = new UploadPersonList();
			List<Person> personList = uploadPersonList.getPerson();
			//Sequential Graph Api call
			for(Person person: personMap.values()){
				personList.add(person);
			}
			UploadPersonList returnPersonListObject = SocialMediaUtils.uploadPersonNodesIntoGraph(uploadPersonList);
			String personListString = "";
			for(Person person : returnPersonListObject.getPerson()){
				String tmp = person.getPersonId();
				if(!Utils.isNumeric(tmp)){
					continue;
				}
				if(!Utils.isBlankOrNull(personListString)){
					tmp = "," + tmp;
				}
				personListString += tmp;
			}	
			if(!Utils.isBlankOrNull(personListString)){
				updatepersonUploadStatusInDB(personListString, SocialMediaConstants.GRAPH_UPLOAD_COMPLETE);
			}
		}catch(Exception e){
			TPLogger.getLogger().error("Error Saving applicant for graph upload detail in table",e);
		}
	
	}
	
	public void updatepersonUploadStatusInDB(String personListString,String uploadStatus) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction trans = null;
		String[] dynParams = new String[1];
		dynParams[0] = personListString;
		try{
			trans = new DBTransaction();
			dq = new DBPreparedQuery("dUpdateApplicantStatusAfterGraphUpload", dynParams, trans);
			dq.setString(1, uploadStatus);
			dq.execute();
			trans.commit();
		}catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			trans.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(trans);
			}
		}
	}

	/**
	 * @return list of all applicants with no userids whose graph upload status is pending
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Person> fetchPendingApplicantUploads() throws Exception {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> result = new ArrayList<SimpleDataObject>();
		Map<String,Person> personMap = new HashMap<String,Person>();
		Person person = null;
		ArrayList<EducationHistory> eduHist = null;
		ArrayList<WorkHistory> workHist = null;
		try {
			
			//fetch Education history
			dq = new DBPreparedQuery("dFetchApplicantEduHistoryForUpload");
			result = dq.getResult();
			
			for(SimpleDataObject sdo: result){
				String personId = sdo.getString("personId");
				if(!personMap.containsKey(personId)){
					personMap.put(personId, new Person());
				}
				person = personMap.get(personId);
				person.setPersonId(personId);
				person.setName(sdo.getString("applicantName"));
				person.setLocation(sdo.getString("location"));
				eduHist = (ArrayList<EducationHistory>) person.getEducationHistory();
				String[] temp = sdo.getString("institute").split("\\|");
				String[] temp1 = sdo.getString("degree").split("\\|");
				String[] temp2 = sdo.getString("specialization").split("\\|");
				String[] temp3 = sdo.getString("year_of_passing").split("\\|");
				
				for(int i =0;i<temp.length;i++){
					EducationHistory edu = new EducationHistory();
					edu.setDegree(temp1[i]);
					edu.setInstitutionName(temp[i]);
					edu.setSpecialization(temp2[i]);
					edu.setYearOfPassing(temp3[i]);
					eduHist.add(edu);
				}
			}
			
			result.clear();
			
			//fetch Work history
			dq = new DBPreparedQuery("dFetchApplicantWorkHistoryForUpload");
			result = dq.getResult();
			
			for(SimpleDataObject sdo: result){
				String personId = sdo.getString("personId");
				if(!personMap.containsKey(personId)){
					personMap.put(personId, new Person());
				}
				person = personMap.get(personId);
				workHist = (ArrayList<WorkHistory>) person.getWorkHistory();
				String[] temp = sdo.getString("employer").split("\\|");
				String[] temp1 = sdo.getString("designation").split("\\|");
				String[] temp2 = sdo.getString("employerFromDate").split("\\|");
				String[] temp3 = sdo.getString("employerToDate").split("\\|");
				for(int i=0; i<temp.length;i++){
					WorkHistory work = new  WorkHistory();
					work.setEmployerName(temp[i]);
					work.setRole(temp1[i]);
					work.setFromDate(temp2[i]);
					work.setToDate(temp3[i]);
					workHist.add(work);
				}
			}
			
			result.clear();
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error fetching applicants in pending upload state",e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return personMap;
	}

	/**
	 * @param applicantId
	 * @param userId
	 * @param uploadStatus
	 * @throws Exception
	 */
	private void insertApplicantIntoGraphTable(String applicantId, String userId, String uploadStatus) throws Exception{
		DBPreparedQuery dq = null;
		DBTransaction trans = null;
		try {
			trans = new DBTransaction();
			dq = new DBPreparedQuery("dAddApplicantForGraphUpload",trans);
			int cnt=1;
			dq.setString(cnt++,applicantId);
			dq.setString(cnt++,userId);
			dq.setString(cnt++,uploadStatus);
			dq.execute();
			trans.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			trans.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(trans);
			}
		}
	}

}
