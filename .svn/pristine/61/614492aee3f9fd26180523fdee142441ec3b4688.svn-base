/**
 * 
 */
package com.talentPool.employeeservice.manager;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.employeeservice.dataobject.CustomFieldCell;
import com.talentPool.employeeservice.dataobject.CustomFieldRow;
import com.talentPool.employeeservice.dataobject.CustomFieldTable;
import com.talentPool.employeeservice.dataobject.EapplicantData;
import com.talentPool.employeeservice.dataobject.EcustomFieldData;
import com.talentPool.employeeservice.dataobject.EeducationalData;
import com.talentPool.employeeservice.dataobject.EemploymentHistoryData;
import com.talentPool.employeeservice.dataobject.Epagination;
import com.talentPool.employeeservice.dataobject.EpositionData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.search.manager.SearchManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.vendorservice.dataobject.VeducationalData;
import com.talentPool.vendorservice.dataobject.VemploymentHistoryData;

/**
 * @author Shantanu
 *
 */
public class EmployeeApplicantManager {
	public List<EapplicantData> getApplicantsForEmployee(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId,String isEmployeeApply,String position,String location) {
		List<SimpleDataObject> applicants = getApplicants(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, vendorId, sourceId,isEmployeeApply,position,location);
		List<EapplicantData> eapplicants = construtsEapplicantData(applicants);		
		return eapplicants;
	}

	private List<SimpleDataObject> getApplicants(boolean doShowApplicantsInProcess,  boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId,String isEmployeeApply,String position,String location) {
		List<SimpleDataObject> applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			dynParam[1] = "";
		
			ArrayList<String> dynamicContent = new ArrayList<String>();
			boolean appendOR=false;
			if (doShowApplicantsInProcess ||doShowApplicantsJoined || doShowApplicantsRejected) {
				dynParam[0] += " AND (";
			}
			if (doShowApplicantsInProcess) {
				dynParam[0] += " ( ta.applicant_position_id is not null AND ta.applicant_step_id is not null AND tp.position_id is not null )";
				appendOR=true;
			}
			if (doShowApplicantsJoined) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				dynParam[0] += " tasp.position_step_id_to= ? " ;
				dynamicContent.add(SelectionProcessConstants.STEP_JOIN);
				appendOR=true;
			}
			if (doShowApplicantsRejected) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				//for showing reject candidate in employee portal with position title
				dynParam[0] += " (ta.applicant_step_id is null ) AND ta.applicant_joined!='1'";
			}
			if(dynParam[0].length()>0){
				dynParam[0] += " ) ";
			}
			if(!Utils.isBlankOrNull(position)){
				if(!Utils.isBlankOrNull(position)){
					position=position.trim();
				}
				String positionId=getPositionId(position);
				dynParam[0] += "and tp.position_id =?";
				dynamicContent.add(positionId);
			}
			if(!Utils.isBlankOrNull(location)){
				if(!Utils.isBlankOrNull(location)){
					location=location.trim();
				}
				String locationId=getLocationId(location);
				dynParam[0] += "and tpl.location_id=?";
				dynamicContent.add(locationId);
			}
			if (EmployeeServiceConstants.SORT_BY_NAME_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name";
			} else if (EmployeeServiceConstants.SORT_BY_NAME_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name desc";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title desc";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created desc";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus desc";
			}
			
			
			
			
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetApplicants", dynParam);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(cnt++, GlobalConstants.ENABLED);
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setId(cnt++, vendorId);
			dq.setId(cnt++, sourceId);
			dq.setString(cnt++,isEmployeeApply);
			
			for(int i=0;i<dynamicContent.size();i++){
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setInt(cnt++, lowerLimit);
			dq.setInt(cnt++, pageSize);
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	private List<EapplicantData> construtsEapplicantData(List<SimpleDataObject> applicants) {
		List<EapplicantData> eapplicants = new ArrayList<EapplicantData>();
		Iterator<SimpleDataObject> itr = applicants.iterator();
		ReportManager reportManager =new ReportManager();
		while (itr.hasNext()) {
			SimpleDataObject sDo = itr.next();
			String processId="";
			String reasonForReject="";
			String positionTitleForRejected="";
			String currentStatus=sDo.getString("currentStatus");
			String applicantId=sDo.getString("applicantId");
			if(!Utils.isBlankOrNull(applicantId)){
				processId=reportManager.getProcessId(applicantId);
			}
			if(!Utils.isBlankOrNull(processId)){
				reasonForReject=reportManager.getFeedbackComment(processId);
			}
			if(!Utils.isBlankOrNull(processId)){
				positionTitleForRejected=reportManager.getPositionTitle(processId);
			}
			EapplicantData eapplicant = new EapplicantData();
			eapplicant.setApplicantId(sDo.getString("applicantId"));
			eapplicant.setApplicantName(sDo.getString("applicantName"));
			eapplicant.setPositionTitle(sDo.getString("positionTitle"));
			try {
				eapplicant.setDateUploaded(DateUtils.getSystemDateFormat(sDo.getDate("dateUploaded")));				
			} catch (ClassCastException cce) {
				eapplicant.setDateUploaded("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			eapplicant.setCurrentStatus(sDo.getString("currentStatus"));
			eapplicant.setPositionReferalFees(sDo.getString("positionReferalFees")) ;
			if (sDo.getDate("applicantWorkingSince") != null) {
				eapplicant.setApplicantWorkingSince(sDo.getDate("applicantWorkingSince"));
			}
			eapplicant.setCurrentEmployer(sDo.getString("currentEmployer"));
			if(Utils.isBlankOrNull(currentStatus)){
				eapplicant.setCurrentStatus("Rejected");
				eapplicant.setReasonForReject(reasonForReject);
				eapplicant.setPositionTitle(positionTitleForRejected);
			}
			else{
				eapplicant.setReasonForReject(" ");
			}
			eapplicants.add(eapplicant);
		}
		return eapplicants;
	}
	
	//added for Employee search as Applicant applied for Internal position
	public List<EapplicantData> getEmployeeApplicationDetails(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId,String isEmployeeApply,String positionTitle) {
		String applicantId=getApplicantId(vendorId, sourceId,isEmployeeApply);
		String processId=null;
		if(!Utils.isBlankOrNull(positionTitle)){
			positionTitle=positionTitle.trim();
		}
		String positionId=getPositionId(positionTitle);
		if(!Utils.isBlankOrNull(positionId)&&!Utils.isBlankOrNull(applicantId)){
			processId=getProcessId(applicantId,positionId);
		}
		
		List<SimpleDataObject> applicants = getEmployeeApplicationDetails(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, vendorId, sourceId,isEmployeeApply,positionId,processId);
		List<EapplicantData> eapplicants = construtsEapplicantData(applicants);		
		return eapplicants;
	}
	public List<EapplicantData> getEmployeeDetails(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId,String isEmployeeApply,String positionTitle) {
		String applicantId=getApplicantId(vendorId, sourceId,isEmployeeApply);
		/*String processId=null;
		if(!Utils.isBlankOrNull(positionTitle)){
			positionTitle=positionTitle.trim();
		}
		String positionId=getPositionId(positionTitle);
		if(!Utils.isBlankOrNull(positionId)&&!Utils.isBlankOrNull(applicantId)){
			processId=getProcessId(applicantId,positionId);
		}*/
		
		List<SimpleDataObject> applicants = getEmployeeApplicationDetail(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, sortBy, pageNo, pageSize, vendorId, sourceId,isEmployeeApply);
		List<EapplicantData> eapplicants = construtsEapplicantData(applicants);		
		return eapplicants;
	}
	public String getProcessId(String applicantId,String positionId) {
		DBPreparedQuery dq = null;
		String processId = null;
		try {
			dq = new DBPreparedQuery("d_EmployeePortal_processId");
			//select max(process_id) as process_id from tp_applicant_selection_process tps where tps.applicant_id='10' and position_id='8'

			dq.setString(1,applicantId);
			dq.setString(2,positionId);
			 processId = dq.getIdResult();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return processId;
	} 
	public String getApplicantId(String vendorId, String sourceId,String isEmployeeApply) {
		DBPreparedQuery dq = null;
		String applicantId = null;
		try {
			dq = new DBPreparedQuery("d_EmployeePortal_applicantId");
			// select ta.applicant_id from tp_applicants ta where ta.source_id='11' and ta.vendor_id='2' and ta.is_employee_apply='1' and
			//ta.applicant_name= (select ts.source_title from tp_sources ts where ts.source_id='11')
			dq.setString(1,sourceId);
			dq.setString(2,vendorId);
			dq.setString(3,isEmployeeApply);
			dq.setString(4,sourceId);
			applicantId=dq.getIdResult();
		
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicantId;
	} 
	public String getPositionId(String positionTitle) {
		DBPreparedQuery dq = null;
		String positionId = null;
		try {
			dq = new DBPreparedQuery("d_EmployeePortal_positionId");
			//select tp.position_id from tp_positions tp where  tp.position_title=''
			dq.setString(1,positionTitle);
			positionId=dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionId;
	} 
	public String getLocationId(String location) {
		DBPreparedQuery dq = null;
		String locationId = null;
		try {
			dq = new DBPreparedQuery("d_EmployeePortal_locationId");
			//select tp.position_id from tp_positions tp where  tp.position_title=''
			dq.setString(1,location);
			locationId=dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return locationId;
	} 
	private List<SimpleDataObject> getEmployeeApplicationDetail(boolean doShowApplicantsInProcess,  boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId,String isEmployeeApply) {
		List<SimpleDataObject> applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			dynParam[1] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			boolean appendOR=false;
			if (doShowApplicantsInProcess ||doShowApplicantsJoined || doShowApplicantsRejected) {
				dynParam[0] += " AND (";
			}
			if (doShowApplicantsInProcess) {
				dynParam[0] += " ( ta.applicant_position_id is not null AND ta.applicant_step_id is not null AND tp.position_id is not null )";
				appendOR=true;
			}
			if (doShowApplicantsJoined) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				dynParam[0] += " tasp.position_step_id_to= ? " ;
				dynamicContent.add(SelectionProcessConstants.STEP_JOIN);
				appendOR=true;
			}
			if (doShowApplicantsRejected) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				//for showing reject candidate in employee portal with position title
				dynParam[0] += " (ta.applicant_step_id is null )";
			}
			if(dynParam[0].length()>0){
				dynParam[0] += " ) ";
			}
			
			if (EmployeeServiceConstants.SORT_BY_NAME_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name";
			} else if (EmployeeServiceConstants.SORT_BY_NAME_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name desc";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title desc";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created desc";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus desc";
			}
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;
			dq = new DBPreparedQuery("dEmployeeApplicantion_details", dynParam);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(cnt++, GlobalConstants.ENABLED);
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setId(cnt++, vendorId);
			dq.setId(cnt++, sourceId);
			dq.setString(cnt++,isEmployeeApply);
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}
	private List<SimpleDataObject> getEmployeeApplicationDetails(boolean doShowApplicantsInProcess,  boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String sortBy, String pageNo, int pageSize, String vendorId, String sourceId,String isEmployeeApply,String positionId,String processId) {
		List<SimpleDataObject> applicants = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[2];
			dynParam[0] = "";
			dynParam[1] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			boolean appendOR=false;
			if (doShowApplicantsInProcess ||doShowApplicantsJoined || doShowApplicantsRejected) {
				dynParam[0] += " AND (";
			}
			if (doShowApplicantsInProcess) {
				dynParam[0] += " ( ta.applicant_position_id is not null AND ta.applicant_step_id is not null AND tp.position_id is not null )";
				appendOR=true;
			}
			if (doShowApplicantsJoined) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				dynParam[0] += " tasp.position_step_id_to= ? " ;
				dynamicContent.add(SelectionProcessConstants.STEP_JOIN);
				appendOR=true;
			}
			if (doShowApplicantsRejected) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				//for showing reject candidate in employee portal with position title
				dynParam[0] += " (ta.applicant_step_id is null )";
			}
			if(dynParam[0].length()>0){
				dynParam[0] += " ) ";
			}
			
			if (EmployeeServiceConstants.SORT_BY_NAME_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name";
			} else if (EmployeeServiceConstants.SORT_BY_NAME_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_name desc";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title";
			} else if (EmployeeServiceConstants.SORT_BY_POSITION_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " position_title desc";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created";
			} else if (EmployeeServiceConstants.SORT_BY_DATE_UPLOADED_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " ta.applicant_date_created desc";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_ASC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus";
			} else if (EmployeeServiceConstants.SORT_BY_CURRENT_STATUS_DESC.equalsIgnoreCase(sortBy)) {
				dynParam[1] = " currentStatus desc";
			}
			int lowerLimit = (Integer.parseInt(pageNo) - 1) * pageSize;
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetApplicantsForEmployee", dynParam);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(cnt++, GlobalConstants.ENABLED);
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setId(cnt++, vendorId);
			dq.setId(cnt++, sourceId);
			dq.setString(cnt++,isEmployeeApply);
			dq.setString(cnt++,positionId);
			dq.setString(cnt++,processId);
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}
	
	public Epagination getPaginationData(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String pageNo, int pageSize, String vendorId, String sourceId) {
		long recordCount = getRecordCount(doShowApplicantsInProcess, doShowApplicantsJoined, doShowApplicantsRejected, vendorId, sourceId);
		Epagination epagination = new Epagination(recordCount, pageSize, Integer.parseInt(pageNo));
		return epagination;
	}

	private long getRecordCount(boolean doShowApplicantsInProcess, boolean doShowApplicantsJoined, boolean doShowApplicantsRejected, String vendorId, String sourceId) {
		long recordCount = 0;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			boolean appendOR=false;
			if (doShowApplicantsInProcess ||doShowApplicantsJoined || doShowApplicantsRejected) {
				dynParam[0] += " AND (";
			}
			if (doShowApplicantsInProcess) {
				dynParam[0] += " ( ta.applicant_position_id is not null AND ta.applicant_step_id is not null AND tp.position_id is not null )";
				appendOR=true;
			}
			if (doShowApplicantsJoined) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				dynParam[0] += " ta.applicant_joined= ? " ;
				dynamicContent.add(SelectionProcessConstants.APPLICANT_JOINED);
				appendOR=true;
			}
			if (doShowApplicantsRejected) {
				if(appendOR){
					dynParam[0] += " OR "; 
				}
				dynParam[0] += " ( ta.applicant_position_id is null AND ta.applicant_step_id is null )";
			}
			if(dynParam[0].length()>0){
				dynParam[0] += " ) ";
			}
			
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetApplicantsCount", dynParam);
			int cnt=1;
			dq.setId(cnt++, vendorId);
			dq.setId(cnt++, sourceId);
			for(int i=0;i<dynamicContent.size();i++){
				dq.setString(cnt++, dynamicContent.get(i));
			}
			recordCount = new Long(dq.getIdResult()).longValue();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the record count", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return recordCount;
	}

	public EapplicantData getApplicantDisplayData(String applicantId, String userId, String sourceId) {
		EapplicantData eapplicantData = getEapplicantData(applicantId, userId, sourceId);
		if (!Utils.isBlankOrNull(applicantId)){
		ArrayList<EeducationalData> eeducationalDataList = getEeducationalData(applicantId);
		eapplicantData.setEducationalDetails(eeducationalDataList);
		ArrayList<EcustomFieldData> ecustomFields = getEcustomFieldData(applicantId);
		eapplicantData.setCustomFields(ecustomFields);
		}
		return eapplicantData;
	}

	private ArrayList<EcustomFieldData> getEcustomFieldData(String applicantId) {
		ArrayList<EcustomFieldData> ecustomFields = new ArrayList<EcustomFieldData>();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
		for (int i = 0; customFields != null && i < customFields.size(); i++) {
			CustomFieldData customFieldData = customFields.get(i);
			EcustomFieldData ecustomFieldData = new EcustomFieldData();
			if (!Utils.isBlankOrNull(customFieldData.getFieldName())) {
				ecustomFieldData.setFieldName(customFieldData.getFieldName());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldDisplayName())) {
				ecustomFieldData.setFieldDisplayName(customFieldData.getFieldDisplayName());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldAttributes())) {
				ecustomFieldData.setFieldAttributes(customFieldData.getFieldAttributes());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldOtherAttributes())) {
				ecustomFieldData.setFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldId())) {
				ecustomFieldData.setFieldId(customFieldData.getFieldId());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldOptions())) {
				ecustomFieldData.setFieldOptions(customFieldData.getFieldOptions());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldType())) {
				ecustomFieldData.setFieldType(customFieldData.getFieldType());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldDefaultValue())) {
				ecustomFieldData.setFieldDefaultValue(customFieldData.getFieldDefaultValue());
			}
			if (!Utils.isBlankOrNull(customFieldData.getFieldStringValue())) {
				ecustomFieldData.setFieldStringValue(customFieldData.getFieldStringValue());
			}
			ecustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
			if (customFieldData.getFieldDateValue() != null) {
				ecustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
			}
			if (customFieldData.getFieldValues() != null) {
				ecustomFieldData.setFieldValues(customFieldData.getFieldValues());
			}
			ecustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
			ecustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
			ecustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
			ecustomFieldData.setTableId(customFieldData.getTableId());
			ecustomFieldData.setTableName(customFieldData.getTableName());
			ecustomFieldData.setFieldRank(customFieldData.getFieldRank());
			ecustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
			ecustomFields.add(ecustomFieldData);

		}
		return ecustomFields;
	}
	
	private EcustomFieldData getVcustomFieldDataFromCustomField(CustomFieldData customFieldData) {
		
		EcustomFieldData vcustomFieldData = new EcustomFieldData();
		if (!Utils.isBlankOrNull(customFieldData.getFieldName())) {
			vcustomFieldData.setFieldName(customFieldData.getFieldName());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldDisplayName())) {
			vcustomFieldData.setFieldDisplayName(customFieldData.getFieldDisplayName());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldAttributes())) {
			vcustomFieldData.setFieldAttributes(customFieldData.getFieldAttributes());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldOtherAttributes())) {
			vcustomFieldData.setFieldOtherAttributes(customFieldData.getFieldOtherAttributes());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldId())) {
			vcustomFieldData.setFieldId(customFieldData.getFieldId());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldOptions())) {
			vcustomFieldData.setFieldOptions(customFieldData.getFieldOptions());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldType())) {
			vcustomFieldData.setFieldType(customFieldData.getFieldType());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldDefaultValue())) {
			vcustomFieldData.setFieldDefaultValue(customFieldData.getFieldDefaultValue());
		}
		if (!Utils.isBlankOrNull(customFieldData.getFieldStringValue())) {
			vcustomFieldData.setFieldStringValue(customFieldData.getFieldStringValue());
		}
		vcustomFieldData.setFieldNumberValue(customFieldData.getFieldNumberValue());
		if (customFieldData.getFieldDateValue() != null) {
			vcustomFieldData.setFieldDateValue(customFieldData.getFieldDateValue());
		}
		vcustomFieldData.setFieldValues(customFieldData.getFieldValues());
		vcustomFieldData.setToValues(customFieldData.getToValues());
		vcustomFieldData.setFieldRequired(customFieldData.getFieldRequired());
		vcustomFieldData.setFieldEntityType(customFieldData.getFieldEntityType());
		vcustomFieldData.setFieldInputAllowed(customFieldData.getFieldInputAllowed());
		vcustomFieldData.setFieldRank(customFieldData.getFieldRank());
		vcustomFieldData.setFieldSearchable(customFieldData.getFieldSearchable());
		vcustomFieldData.setTableId(customFieldData.getTableId());
		vcustomFieldData.setTableName(customFieldData.getTableName());

	return vcustomFieldData;
}

	private ArrayList<EeducationalData> getEeducationalData(String applicantId) {
		ArrayList<EeducationalData> veducationalDataList = new ArrayList<EeducationalData>();
		ApplicantManager applicantManager = new ApplicantManager();
		ArrayList<EducationalData> educationalDataList = applicantManager.getEducationalInfo(applicantId);
		for (int i = 0; educationalDataList != null && i < educationalDataList.size(); i++) {
			EducationalData educationalData = educationalDataList.get(i);
			EeducationalData veducationalData = new EeducationalData();
			veducationalData.setDegreeTitle(educationalData.getDegreeTitle());
			veducationalData.setMajor(educationalData.getMajor());
			veducationalData.setInstitute(educationalData.getInstitute());
			veducationalData.setGrade(educationalData.getGrade());
			veducationalData.setRemarks(educationalData.getRemarks());
			if(educationalData.getFromYear()!=null){
				veducationalData.setFromYear(educationalData.getFromYear());
			}
			if (educationalData.getYearOfPassing() != null) {
				veducationalData.setYearOfPassing(educationalData.getYearOfPassing());
			}
			veducationalDataList.add(veducationalData);
		}
		return veducationalDataList;
	}

	/**
	 * @param applicantId
	 * @param userId
	 * @param sourceId
	 * @return
	 */
	private EapplicantData getEapplicantData(String applicantId, String userId, String sourceId) {
		SimpleDataObject sDo = getApplicantDisplaySimpleData(applicantId, userId, sourceId);
		EapplicantData vapplicantData = new EapplicantData();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		try {
			if (!Utils.isBlankOrNull(applicantId)){
			vapplicantData.setApplicantId(sDo.getString("applicantId"));
			vapplicantData.setApplicantName(sDo.getString("applicantName"));
			vapplicantData.setCurrentLocation(sDo.getString("currentLocation"));
			try {
				vapplicantData.setDateUploaded(DateUtils.getSystemDateFormat(sDo.getDate("dateUploaded")));				
			} catch (ClassCastException cce) {
				vapplicantData.setDateUploaded("");
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			vapplicantData.setOriginalResumePath(sDo.getString("originalResumePath"));
			vapplicantData.setCurrentEmployer(sDo.getString("currentEmployer"));
			if (sDo.getDate("applicantWorkingSince") != null) {
				vapplicantData.setApplicantWorkingSince(sDo.getDate("applicantWorkingSince"));
			}
			vapplicantData.setApplicantJoined(sDo.getString("applicantJoined").equals(ApplicantConstants.APPLICANT_JOINED) ? true : false);
			vapplicantData.setCurrentCTC(sDo.getString("currentCTC"));
			vapplicantData.setExpectedCTC(sDo.getString("expectedCTC"));
			vapplicantData.setCurrentCTCDate(sDo.getDate("cureentCTCDate"));
			vapplicantData.setExpectedCTCDate(sDo.getDate("expectedCTCDate"));
			vapplicantData.setSkills(sDo.getString("skills"));

			if (!Utils.isBlankOrNull(sDo.getString("applicantPositionId"))) {
				vapplicantData.setApplicantPositionId(sDo.getString("applicantPositionId"));
			}
			if (!Utils.isBlankOrNull(sDo.getString("applicantStepId"))) {
				vapplicantData.setApplicantStepId(sDo.getString("applicantStepId"));
			}
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
				ArrayList<CustomFieldData> tabularCustomFields = customFieldManager.getCustomFieldDataForTables(applicantId);
				
				ArrayList<CustomFieldData> tabularFields = customFieldManager.getApplicantTabularCustomFields();
				List<CustomFieldTable> customTables = new ArrayList<CustomFieldTable>();
				for(CustomFieldData tabularData:tabularFields){
					CustomFieldTable cft = new CustomFieldTable();
					cft.setTableId(tabularData.getTableId());
					cft.setTableName(tabularData.getTableName());
					List<CustomFieldRow> rows = new ArrayList<CustomFieldRow>();
					boolean tableFieldPresent = false;
					boolean isFirstIteration = true;
					for (CustomFieldData data:tabularCustomFields){
						CustomFieldData tData = (CustomFieldData) data;
						if (!Utils.isBlankOrNull(tData.getTableId()) && tData.getTableId().equals(tabularData.getTableId())){
							tableFieldPresent = true;
							if (isFirstIteration){
								CustomFieldRow row = new CustomFieldRow();
								CustomFieldCell cell = new CustomFieldCell();
								cell.setValue(tData.getFieldDisplayName());
								row.addCell(cell);
								rows.add(row);
								String[] values = tData.getFieldValues();
								for (int i=0; i<values.length; i++){
									CustomFieldRow row1 = new CustomFieldRow();
									CustomFieldCell cell1 = new CustomFieldCell();
									cell1.setValue(values[i]);
									row1.addCell(cell1);
									rows.add(row1);
								}
								isFirstIteration= false;
							}else{
								CustomFieldRow row = rows.get(0);
								CustomFieldCell cell = new CustomFieldCell();
								cell.setValue(tData.getFieldDisplayName());
								row.addCell(cell);
								String[] values = tData.getFieldValues();
								for (int i=0; i<values.length; i++){
									CustomFieldRow row1 = rows.get(i+1);
									CustomFieldCell cell1 = new CustomFieldCell();
//									tData.setFieldStringValue(values[i]);
//									cell1.setData(tData);
									cell1.setValue(values[i]);
									row1.addCell(cell1);
								}
							}
							
						}
					}
					if (!tableFieldPresent){
						ArrayList<CustomFieldData> tabCustomFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
						for (CustomFieldData data:tabCustomFields){
							CustomFieldData tData = (CustomFieldData) data;
							if (!Utils.isBlankOrNull(tData.getTableId()) && tData.getTableId().equals(tabularData.getTableId())){
								if (isFirstIteration){
									CustomFieldRow row = new CustomFieldRow();
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
									rows.add(row);
									isFirstIteration= false;
								}else{
									CustomFieldRow row = rows.get(0);
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
								}
							}
						}
					}
					cft.setRows(rows);
					customTables.add(cft);
				}
				
				
				vapplicantData.setCustomTables(customTables);
			}
			}else{
				if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
					ArrayList<CustomFieldData> tabularFields = customFieldManager.getApplicantTabularCustomFields();
					List<CustomFieldTable> customTables = new ArrayList<CustomFieldTable>();
					for(CustomFieldData tabularData:tabularFields){
						CustomFieldTable cft = new CustomFieldTable();
						cft.setTableId(tabularData.getTableId());
						cft.setTableName(tabularData.getTableName());
						List<CustomFieldRow> rows = new ArrayList<CustomFieldRow>();
						ArrayList<CustomFieldData> tabCustomFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
						boolean isFirstIteration = true;
						for (CustomFieldData data:tabCustomFields){
							CustomFieldData tData = (CustomFieldData) data;
							if (!Utils.isBlankOrNull(tData.getTableId()) && tData.getTableId().equals(tabularData.getTableId())){
								if (isFirstIteration){
									CustomFieldRow row = new CustomFieldRow();
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
									rows.add(row);
									CustomFieldRow row1 = new CustomFieldRow();
									CustomFieldCell cell1 = new CustomFieldCell();
									CustomFieldData tabData = (CustomFieldData)tData.clone();
									tabData.setFieldStringValue("");
									String[] fieldValues = new String[1];
									fieldValues[0] = "";
									tabData.setFieldValues(fieldValues);
									cell1.setData(getVcustomFieldDataFromCustomField(tabData));
									cell1.setValue("");
									row1.addCell(cell1);
									rows.add(row1);
									isFirstIteration = false;
								}else{
									CustomFieldRow row = rows.get(0);
									CustomFieldCell cell = new CustomFieldCell();
									cell.setValue(tData.getFieldDisplayName());
									row.addCell(cell);
									CustomFieldRow row1 = rows.get(1);
									CustomFieldCell cell1 = new CustomFieldCell();
									CustomFieldData tabData = (CustomFieldData)tData.clone();
									tabData.setFieldStringValue("");
									String[] fieldValues = new String[1];
									fieldValues[0] = "";
									tabData.setFieldValues(fieldValues);
									cell1.setData(getVcustomFieldDataFromCustomField(tabData));
									cell1.setValue("");
									row1.addCell(cell1);
								}
							}
						}
						cft.setRows(rows);
						customTables.add(cft);
					}
					vapplicantData.setCustomTables(customTables);
				}
			}
			

		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return vapplicantData;
	}

	public EapplicantData getEapplicantDataFromApplicantData(ApplicantData applicantData) {
	
		EapplicantData eapplicantData = new EapplicantData();
		try {
			
			String noticePeriod=applicantData.getNoticePeriod();
			if(!Utils.isBlankOrNull(noticePeriod)){
				eapplicantData.setNoticePeriod(noticePeriod);
			}
			
			String applicantEmail1=applicantData.getApplicantEmail1();
			if(!Utils.isBlankOrNull(applicantEmail1)){
				eapplicantData.setApplicantEmail1(applicantEmail1);
			}
			String applicantEmail2=applicantData.getApplicantEmail2();
			if(!Utils.isBlankOrNull(applicantEmail2)){
				eapplicantData.setApplicantEmail2(applicantEmail2);
				}
		// get phone
			String cellPhone=applicantData.getApplicantCellPhone();
			if(!Utils.isBlankOrNull(cellPhone)){
				eapplicantData.setApplicantCellPhone(cellPhone);
		
				
			}
			String homePhone=applicantData.getApplicantHomePhone();
			if(!Utils.isBlankOrNull(homePhone)){
		
				eapplicantData.setApplicantHomePhone(homePhone);
			}
			
			String workPhone=applicantData.getApplicantWorkPhone();
			if(!Utils.isBlankOrNull(workPhone)){
		
				eapplicantData.setApplicantWorkPhone(workPhone);
			}
		// get name
			String applicantName=applicantData.getApplicantName();
			if(!Utils.isBlankOrNull(applicantName)){
				eapplicantData.setApplicantName(applicantName);
			}
			String applicantCity=applicantData.getApplicantCity();
			if(!Utils.isBlankOrNull(applicantCity)){
				eapplicantData.setCurrentLocation(applicantCity);
			}
			String applicantCurrentEmployer=applicantData.getApplicantCurrentEmployer();
			if(!Utils.isBlankOrNull(applicantCurrentEmployer)){
				eapplicantData.setCurrentEmployer(applicantCurrentEmployer);
			}
			
			String dateOfBirth=applicantData.getDateOfBirthForDisplay();
			if (!Utils.isBlankOrNull(dateOfBirth)){
				eapplicantData.setDateOfBirth(dateOfBirth);
			}
			String currentCTC =  applicantData.getCurrentCTC();
			if (!Utils.isBlankOrNull(currentCTC)){
				eapplicantData.setCurrentCTC(currentCTC);
			}
			String expectedCTC =  applicantData.getExpectedCTC();
			if (!Utils.isBlankOrNull(expectedCTC)) {
				eapplicantData.setExpectedCTC(expectedCTC);
			}
			
			String workExp =  applicantData.getApplicantTotalExperience();
			
			if (!Utils.isBlankOrNull(workExp)) {
				eapplicantData.setTotalExperience(workExp);
				}
			String applicantId=applicantData.getApplicantId();
			eapplicantData.setApplicantId(applicantId);
			
			String applicantJoined=applicantData.getApplicantJoined();
			if(!Utils.isBlankOrNull(applicantJoined)){
			if(applicantJoined=="1"){
				eapplicantData.setApplicantJoined(true);
			}
			else{
				eapplicantData.setApplicantJoined(false);
			}
			}
			
			String applicantOriginalResumePath=applicantData.getApplicantOriginalResumePath();
			if(!Utils.isBlankOrNull(applicantOriginalResumePath)){
				
				eapplicantData.setOriginalResumePath(applicantOriginalResumePath);
				}
			ArrayList<EducationalData> educationalDetails=applicantData.getEducationalDetails();
			ArrayList<EeducationalData> eEducationalDetails=new ArrayList<EeducationalData>();
			EeducationalData eEducationalData=null;
			
			if (educationalDetails != null && educationalDetails.size() > 0) {
				int sz = educationalDetails.size();
				
				for (int i = 0; i < sz; i++) {
					eEducationalData=new EeducationalData();
					EducationalData eData = (EducationalData) educationalDetails.get(i);
					String degreeTitle=eData.getDegreeTitle();
					if(!Utils.isBlankOrNull(degreeTitle)){
						eEducationalData.setDegreeTitle(degreeTitle);
					}
					String major=eData.getMajor();
					if(!Utils.isBlankOrNull(major)){
						eEducationalData.setMajor(major);
					}
					String institute=eData.getInstitute();
					if(!Utils.isBlankOrNull(institute)){
						eEducationalData.setInstitute(institute);
					}
					String grade=eData.getGrade();
					if(!Utils.isBlankOrNull(grade)){
						eEducationalData.setGrade(grade);
					}
					
					if (eData.getYearOfPassing() != null) {
						eEducationalData.setYearOfPassing(eData.getYearOfPassing());
					}
					if(eData.getFromYear()!=null){
						eEducationalData.setFromYear(eData.getFromYear());
					}
					eEducationalData.setRemarks(eData.getRemarks());
					
					eEducationalDetails.add(eEducationalData);
				}
				
			}
			
			eapplicantData.setEducationalDetails(eEducationalDetails);
			
			ArrayList<EmploymentHistoryData> employmentHistoryDetails=applicantData.getEmploymentHistoryDetails();
			ArrayList<EemploymentHistoryData> eemploymentHistoryDetails=new ArrayList<EemploymentHistoryData>();
			EemploymentHistoryData eemploymentHistoryData=null;
			if (employmentHistoryDetails != null && employmentHistoryDetails.size() > 0) {
				int sz = employmentHistoryDetails.size();
				
				for (int i = 0; i < sz; i++) {
					eemploymentHistoryData=new EemploymentHistoryData();
					EmploymentHistoryData empData = (EmploymentHistoryData) employmentHistoryDetails.get(i);								
					String designation=empData.getDesignationName();
					if(!Utils.isBlankOrNull(designation)){
						eemploymentHistoryData.setDesignationName(designation);
					}
					int employerId=empData.getEmployerId();
					if(employerId!=0){
						eemploymentHistoryData.setEmployerId(String.valueOf(employerId));
					}
					int designationId=empData.getDesignationId();
					if(designationId!=0){
						eemploymentHistoryData.setDesignationId(String.valueOf(designationId));
					}
						String employerexp=empData.getEmployerExperience();
						if(!Utils.isBlankOrNull(employerexp)){
							eemploymentHistoryData.setEmployerExperience(empData.getEmployerExperience());
						}
					Date toDate=empData.getEmployerToDate();
					if(toDate!=null){
						eemploymentHistoryData.setEmployerToDate(toDate);
					}
					Date fromDate=empData.getEmployerFromDate();
					if(fromDate!=null){
						eemploymentHistoryData.setEmployerFromDate(fromDate);
					}
					String employerName=empData.getEmployerName();
					if(!Utils.isBlankOrNull(employerName)){
						eemploymentHistoryData.setEmployerName(employerName);
					}
					eemploymentHistoryData.setGrossSalary(empData.getGrossSalary());
					eemploymentHistoryData.setAllowance(empData.getAllowance());
					eemploymentHistoryData.setDutiesInvolved(empData.getDutiesInvolved());
					eemploymentHistoryData.setReasonForLeaving(empData.getReasonForLeaving());
					eemploymentHistoryDetails.add(eemploymentHistoryData);
				}
				
			}
			
			eapplicantData.setEmploymentHistoryDetails(eemploymentHistoryDetails);
			String skills=applicantData.getSkillIds();
			if(!Utils.isBlankOrNull(skills)){
				eapplicantData.setSkills(skills);
			}
			
			String applicantPositionId=applicantData.getApplicantPositionId();

			if (!Utils.isBlankOrNull(applicantPositionId)) {
				eapplicantData.setApplicantPositionId(applicantPositionId);
			}
			
			String applicantStepId=applicantData.getApplicantStepId();

			if (!Utils.isBlankOrNull(applicantStepId)) {
				eapplicantData.setApplicantStepId(applicantStepId);
			}
			String dateUploaded=DateUtils.getSystemDateFormat(applicantData.getResumeDateUpdated());
			if (!Utils.isBlankOrNull(dateUploaded)) {
				eapplicantData.setDateUploaded(dateUploaded);
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return eapplicantData;
	}
	
	private SimpleDataObject getApplicantDisplaySimpleData(String applicantId, String userId, String sourceId) {
		DBPreparedQuery dq = null;
		SimpleDataObject sdo = null;
		try {
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetApplicantDisplayData");
			dq.setString(1, ApplicantConstants.APPLICANT_JOINED);
			dq.setString(2, ApplicantConstants.APPLICANT_NOT_JOINED);
			dq.setId(3, userId);
			dq.setId(4, sourceId);
			dq.setId(5, applicantId);
			sdo = (SimpleDataObject) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting the sdo for applicant", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}

	public String getApplicantHtmlResume(String resumePath, String applicantId) {
		String content = "";
		try {
			String absolutePath = Utils.concatFilePath(DocumentConstants.documentsPath, resumePath);
			FileHandler fileHandler = new FileHandler();
			content = fileHandler.getTextFileContent(absolutePath, null);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return content;
	}

	public String getApplicantInteractionsXML(ArrayList<SimpleDataObject> interactions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			if (interactions != null) {
				wr.startDocument();
				wr.startElement("rows");
				for (int indx = 0; indx < interactions.size(); indx++) {
					SimpleDataObject data = (SimpleDataObject) interactions.get(indx);
					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", "" + (indx + 1));
					wr.startElement("", "row", "", atr);

					String interactionId = (data.getAttribute("interactionId") == null) ? "0" : ("" + data.getAttribute("interactionId"));
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionId");
					wr.startElement("", "userdata", "", atr);
					wr.characters(interactionId);
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "interactionType");
					wr.startElement("", "userdata", "", atr);
					wr.characters("" + data.getAttribute("interactionType"));
					wr.endElement("userdata");

					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "type");
					wr.startElement("", "userdata", "", atr);
					wr.characters((String) SelectionProcessConstants.INTERACTION_TYPES.get("" + data.getAttribute("interactionType")));
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters("" + data.getAttribute("interactionType"));
					wr.endElement("cell");

					String interactionSubject = (data.getString("interactionSubject") == null) ? "" : "" + data.getString("interactionSubject");
					if (Utils.isBlankOrNull(interactionSubject)) {
						interactionSubject = "&nbsp;";
					}
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "subject");
					wr.startElement("", "userdata", "", at);
					wr.characters(wr.doubleEscape(interactionSubject));
					wr.endElement("userdata");

					if (interactionSubject.length() > 37) {
						interactionSubject = interactionSubject.substring(0, 34) + "...";
					}
					wr.startElement("cell");
					if (data.getAttribute("interactionType").equals(SelectionProcessConstants.INTERACTION_NOTE)) {
						wr.characters("<a href=\"#\" onclick=\"javascript:viewDetails(" + (indx + 1) + ");return false;\">" + wr.doubleEscape(interactionSubject) + "</a>");
					} else {
						wr.characters(wr.doubleEscape(interactionSubject));
					}
					wr.endElement("cell");
					String interactionDate = "";
					if (data.getAttribute("interactionDate") != null) {
						try {
							Date dt = Utils.convertToDate(data.getString("interactionDate"), "yyyy-MM-dd hh:mm:ss");
							interactionDate = Utils.getDateConvertedToString(dt, "dd-MM-yyyy hh:mm a");
						} catch (Exception e) {
							e.printStackTrace();
							interactionDate = "UNKNOWN";
						}
					}
					wr.startElement("cell");
					wr.characters(interactionDate);
					wr.endElement("cell");

					wr.endElement("row");
				}
				wr.endElement("rows");
				wr.endDocument();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return sWr.toString();

	}

	public ArrayList<SimpleDataObject> getApplicantInteractions(String applicantId, String userId, String sourceId) {
		ArrayList<SimpleDataObject> interactions = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetApplicantInteractions");
			dq.setInt(1, SelectionProcessConstants.INTERACTION_INTERVIEW);
			dq.setString(2, SelectionProcessConstants.STEP_JOIN);
			dq.setString(3, SelectionProcessConstants.STEP_REJECT);
			dq.setString(4, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(5, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(6, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(7, PositionConstants.STEP_LEVEL_ACCEPT);
			dq.setString(8, GlobalConstants.PROPERTY_SHOW_DETAILED_ACTIVITY_TO_VENDOR);
			dq.setString(9, GlobalConstants.ENABLED);
			dq.setInt(10, PositionConstants.STEP_SCHEDULED);
			dq.setId(11, userId);
			dq.setId(12, sourceId);
			dq.setId(13, applicantId);
			dq.setId(14, applicantId);
			dq.setInt(15, SelectionProcessConstants.INTERACTION_NOTE);
			dq.setId(16, userId);
			interactions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting interactions", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return interactions;

	}
	
	public ArrayList<SimpleDataObject> getpositionlocations(String loc) {
		ArrayList<SimpleDataObject> sDo = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dEmployeeApplicantManager_GetPositions_Location");
			dq.setString(1,  loc + "%");
			sDo = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting interactions", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sDo;

	}
	
	
	public String getLocationsXML(ArrayList<SimpleDataObject> locations) {
		StringWriter sWr = new StringWriter();
		try {
			if (locations != null) {
				XMLWriter wr = new XMLWriter(sWr);
				wr.startDocument();
				wr.startElement("ul");
				for (int i = 0; i < locations.size(); i++) {
					SimpleDataObject sdo = (SimpleDataObject) locations.get(i);
					String name = sdo.getString("location");
					wr.startElement("li");
					wr.characters(name);
					wr.endElement("li");
				}
				wr.endElement("ul");
				wr.endDocument();
			}
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while getting xml from list", e);
		}
		return sWr.getBuffer().toString();

	}
	
	public ApplicantData convertEApplicantDataToApplicantData(EapplicantData eapplicantData){
		ApplicantData applicantData=null;
		applicantData=new ApplicantData();
		if(eapplicantData!=null){
			String applicantId=eapplicantData.getApplicantId();
			String applicantName=eapplicantData.getApplicantName();
			String positionId=eapplicantData.getApplicantPositionId();
			String originalDocPath=eapplicantData.getOriginal_doc_path();
			String originalResumePath=eapplicantData.getOriginalResumePath();
			String exp=eapplicantData.getTotalExperience();
			Date applicantWorkingSince=eapplicantData.getApplicantWorkingSince();
			String positionTitle=eapplicantData.getPositionTitle();
			String currentCtc=eapplicantData.getCurrentCTC();
			String expectedCtc=eapplicantData.getExpectedCTC();
			String email1=eapplicantData.getApplicantEmail1();
			String email2=eapplicantData.getApplicantEmail2();
			String cellPhone=eapplicantData.getApplicantCellPhone();
			String homePhone=eapplicantData.getApplicantHomePhone();
			String passport=eapplicantData.getPassport();
			String skills = eapplicantData.getSkills();
			String dateOfBirth = eapplicantData.getDateOfBirth();
			String resumeType=eapplicantData.getResumeType();
			 String category=eapplicantData.getCategory();
			 String subCategory=eapplicantData.getSubCategory();
			 String isEmployeeApply=eapplicantData.getIsEmployeeApply();
			 if(!Utils.isBlankOrNull(isEmployeeApply)){
					applicantData.setIsEmployeeApply(isEmployeeApply);
				}
			 if(!Utils.isBlankOrNull(category)){
					applicantData.setCategory(category);
				}
			 if(!Utils.isBlankOrNull(subCategory)){
					applicantData.setSubCategory(subCategory);
				}
			if(!Utils.isBlankOrNull(skills)){
				applicantData.setSkillIds(skills);
			}
			if(!Utils.isBlankOrNull(currentCtc)){
				applicantData.setCurrentCTC(currentCtc);
			}
			if(!Utils.isBlankOrNull(expectedCtc)){
				applicantData.setExpectedCTC(expectedCtc);
			}
			if(!Utils.isBlankOrNull(email1)){
				applicantData.setApplicantEmail1(email1);
			}
			if(!Utils.isBlankOrNull(email2)){
				applicantData.setApplicantEmail2(email2);
			}
			if(!Utils.isBlankOrNull(cellPhone)){
				applicantData.setApplicantCellPhone(cellPhone);
			}
			if(!Utils.isBlankOrNull(homePhone)){
				applicantData.setApplicantHomePhone(homePhone);
			}
			if(!Utils.isBlankOrNull(passport)){
				applicantData.setPassportNumber(passport);
			}
			if(!Utils.isBlankOrNull(exp)){
				applicantData.setApplicantExperience(exp);
			}
			if(!Utils.isBlankOrNull(applicantId)){
				applicantData.setApplicantId(applicantId);
			}
			if(!Utils.isBlankOrNull(applicantName)){
				applicantData.setApplicantName(applicantName);
			}
			if(!Utils.isBlankOrNull(originalDocPath)){
				applicantData.setApplicantOriginalDocPath(originalDocPath);
			}
			if(!Utils.isBlankOrNull(originalResumePath)){
				applicantData.setApplicantOriginalResumePath(originalResumePath);
			}
			if(!Utils.isBlankOrNull(positionId)){
				applicantData.setApplicantPositionId(positionId);
			}
			if(!Utils.isBlankOrNull(originalResumePath)){
				applicantData.setApplicantOriginalResumePath(originalResumePath);
			}
			if(!Utils.isBlankOrNull(positionTitle)){
				applicantData.setApplicantPositionTitle(positionTitle);
			}
			if(!Utils.isBlankOrNull(dateOfBirth)){
				applicantData.setDateOfBirth(Utils.convertToSQLDate(dateOfBirth, Utils.regEUDateFormat));
			}
			if(!Utils.isBlankOrNull(resumeType)){
				if(resumeType.equals("-1")){
					applicantData.setResumeTypeId("");
				}
				else{
					applicantData.setResumeTypeId(resumeType);
				}
			}
			
			
			java.sql.Date dtWorkingFrom = null;
			if (!Utils.isBlankOrNull(eapplicantData.getTotalExperience())){
				Calendar calendar = Calendar.getInstance();
				if(eapplicantData.getTotalExperience().contains(".")){
					String[] yysmms = eapplicantData.getTotalExperience().split("\\.");
					calendar.add(Calendar.YEAR, -Integer.parseInt(yysmms[0]));
					calendar.add(Calendar.MONTH, -Integer.parseInt(yysmms[1]));
				}else{
					calendar.add(Calendar.YEAR, -Integer.parseInt(eapplicantData.getTotalExperience()));
				}
				dtWorkingFrom = Utils.convertDateToSQLDate(calendar.getTime());
				applicantData.setApplicantWorkingSince(dtWorkingFrom);
			}
			
			applicantData.setApplicantCity(eapplicantData.getCurrentLocation());
			applicantData.setNoticePeriod(eapplicantData.getNoticePeriod());
			ArrayList<EducationalData> educationalDetails=new ArrayList<EducationalData>();
			ArrayList<EeducationalData> eEducationalDetails=eapplicantData.getEducationalDetails();
			MastersManager mastermanager=new MastersManager();
			if (eEducationalDetails != null && eEducationalDetails.size() > 0) {
				int sz = eEducationalDetails.size();
				String[] eduYop = new String[sz];
				String[] eduInstitute = new String[sz];
				String[] eduDegree = new String[sz];
				String[] eduMajor = new String[sz];
				String[] eduGrades = new String[sz];
				String[] remarks = new String[sz];
				String[] fromYear = new String[sz];
				for (int i = 0; i < sz; i++) {
					EducationalData eEducationalData=new EducationalData();
					EeducationalData eData = (EeducationalData) eEducationalDetails.get(i);
					
					eEducationalData.setInstitute(eData.getInstitute());
					String degreeTitle=eData.getDegreeTitle();
					String degreeId=null;
					if(!Utils.isBlankOrNull(degreeTitle)){
						try {
							eEducationalData.setDegreeId(Integer.parseInt(degreeTitle));
						} catch (NumberFormatException nfe){
							
						}
//						degreeId=mastermanager.getDegreeId(degreeTitle);
//						if(Utils.isInteger(degreeId)){
//							eEducationalData.setDegreeId(Integer.parseInt(degreeTitle));
//						}
					}
					eEducationalData.setGrade(eData.getGrade());
					if (eData.getFromYear()!=null){
						eEducationalData.setFromYear(Utils.convertDateToSQLDate(eData.getFromYear()));
					}
					if (eData.getYearOfPassing()!=null){
						eEducationalData.setYearOfPassing(Utils.convertDateToSQLDate(eData.getYearOfPassing()));
					}
					String branchTitle=eData.getMajor();
					String branchId=null;
					if(!Utils.isBlankOrNull(branchTitle)){
						try {
							eEducationalData.setMajorId(Integer.parseInt(branchTitle));
						} catch (NumberFormatException nfe){
							
						}
//						branchId=mastermanager.getBranchId(degreeTitle);
//						if(Utils.isInteger(branchId)){
//							eEducationalData.setMajorId(Integer.parseInt(branchId));
//						}
					}
					eEducationalData.setRemarks(Utils.isBlankOrNull(eData.getRemarks())?"":eData.getRemarks());
					educationalDetails.add(eEducationalData);
				}
				
			}
			
			applicantData.setEducationalDetails(educationalDetails);
			
			ArrayList<EmploymentHistoryData> employmentHistoryDetails=new ArrayList<EmploymentHistoryData>();
			ArrayList<EemploymentHistoryData> eemploymentHistoryDetails=eapplicantData.getEmploymentHistoryDetails();
			
			if (eemploymentHistoryDetails != null && eemploymentHistoryDetails.size() > 0) {
				int sz = eemploymentHistoryDetails.size();
				for (int i = 0; i < sz; i++) {
					EmploymentHistoryData eemploymentHistoryData=new EmploymentHistoryData();
					EemploymentHistoryData empData = (EemploymentHistoryData) eemploymentHistoryDetails.get(i);								
					Date employerFromDate = empData.getEmployerFromDate();
					String empHistFromDate = "";
					if (employerFromDate != null) {
						empHistFromDate = Utils.getDateConvertedToString(employerFromDate, Utils.regDDMMMYYYYFormat);
						eemploymentHistoryData.setEmployerFromDate(Utils.convertDateToSQLDate(employerFromDate));
					}
					String employerId=empData.getEmployerId();
					if(!Utils.isBlankOrNull(employerId)){
						employerId=employerId.trim();
						if(!Utils.isBlankOrNull(employerId)){
							eemploymentHistoryData.setEmployerName(employerId);
						}
						
					}
					
					String designationId=empData.getDesignationId();
					if(!Utils.isBlankOrNull(designationId)){
						designationId=designationId.trim();
						
							eemploymentHistoryData.setDesignationName(designationId);
					}
					
					String empExp=empData.getEmployerExperience();
					if(!Utils.isBlankOrNull(empExp)){
					       empExp=changeExpInYear(empExp);
					       eemploymentHistoryData.setEmployerExperience(empExp);
					 }
						
					
					eemploymentHistoryData.setEmployerToDate(Utils.convertDateToSQLDate(empData.getEmployerToDate()));
					//String employerName=empData.getEmployerName();
//					if(!Utils.isBlankOrNull(employerName)){
//						eemploymentHistoryData.setEmployerName(employerName);
//					}
					eemploymentHistoryData.setGrossSalary(empData.getGrossSalary());
					eemploymentHistoryData.setAllowance(empData.getAllowance());
					eemploymentHistoryData.setDutiesInvolved(empData.getDutiesInvolved());
					eemploymentHistoryData.setReasonForLeaving(empData.getReasonForLeaving());
					employmentHistoryDetails.add(eemploymentHistoryData);
				}
				
			}
			
			applicantData.setEmploymentHistoryDetails(employmentHistoryDetails);
			
			ArrayList<EcustomFieldData> wCustomFieldList = eapplicantData.getCustomFields();
			ArrayList<CustomFieldData> customFieldList = new ArrayList<CustomFieldData>();
			if (!Utils.isListEmptyOrNull(wCustomFieldList)){
				for (EcustomFieldData wcustomFieldData:wCustomFieldList){
					CustomFieldData cData = new CustomFieldData();
					cData.setFieldName(wcustomFieldData.getFieldName());
					cData.setFieldDisplayName(wcustomFieldData.getFieldDisplayName());
					cData.setFieldRequired(wcustomFieldData.getFieldRequired());
					cData.setFieldValues(wcustomFieldData.getFieldValues());
					cData.setToValues(wcustomFieldData.getToValues());
					cData.setFieldAttributes(wcustomFieldData.getFieldAttributes());
					cData.setFieldEntityType(wcustomFieldData.getFieldEntityType());
					cData.setFieldOtherAttributes(wcustomFieldData.getFieldOtherAttributes());
					cData.setFieldId(wcustomFieldData.getFieldId());
					cData.setFieldInputAllowed(wcustomFieldData.getFieldInputAllowed());
					cData.setFieldOptions(wcustomFieldData.getFieldOptions());
					cData.setFieldRank(wcustomFieldData.getFieldRank());
					cData.setFieldSearchable(wcustomFieldData.getFieldSearchable());
					cData.setFieldType(wcustomFieldData.getFieldType());
					cData.setFieldDefaultValue(wcustomFieldData.getFieldDefaultValue());
					cData.setFieldStringValue(wcustomFieldData.getFieldStringValue());
					cData.setFieldNumberValue(wcustomFieldData.getFieldNumberValue());
					cData.setFieldDateValue(wcustomFieldData.getFieldDateValue());
					cData.setTableId(wcustomFieldData.getTableId());
					cData.setTableName(wcustomFieldData.getTableName());
					customFieldList.add(cData);
				}
			}
			applicantData.setCustomFields(customFieldList);
		}
		return applicantData;
	}
	
	public static String changeExpInYear(String experience ){
		  String [] s=experience.split(" ");
		  if (s.length>0 && s[0].indexOf("yrs")!=-1){
			  s[0]=s[0].substring(0, s[0].indexOf("yrs"));
			  experience = s[0];
		  }
		  if (s.length>1 && s[1].indexOf("months")!=-1){
			  s[1]=s[1].substring(0, s[1].indexOf("months"));
			  experience = experience +"."+s[1] ;
		  }
		  return experience;
	}
	
	public List<EapplicantData> getApplicantNames(String applicantName) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sDo = null;
		List<EapplicantData> eapplicants=null;
		try {
			SearchManager searchManager=new SearchManager();
			sDo=searchManager.getApplicantNames(applicantName);
			eapplicants = construtsEapplicantData(sDo);	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return eapplicants;
		
	}
	
	private List<EapplicantData> construtsEapplicantDataForSearch(List<SimpleDataObject> applicants) {
		List<EapplicantData> eapplicants = new ArrayList<EapplicantData>();
		Iterator<SimpleDataObject> itr = applicants.iterator();
		ReportManager reportManager =new ReportManager();
		while (itr.hasNext()) {
			SimpleDataObject sDo = itr.next();
			EapplicantData eapplicant = new EapplicantData();
			String name=sDo.getString("applicantName");
			if(!Utils.isBlankOrNull(name)){
				eapplicant.setApplicantName(name);
			}
			eapplicants.add(eapplicant);
		}
		return eapplicants;
	}
	
	public List<EpositionData> getPositions(String position) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> sDo = null;
		
		List<EpositionData> epositions=null;
		try {
			
			
			try {
				dq = new DBPreparedQuery("dEmployeeApplicantManager_GetPositions");
				dq.setString(1, position + "%");
				dq.setString(2, PositionConstants.POSITION_PUBLISHED_FOR_EMPLOYEE_PORTAL);
				sDo = dq.getResult();
			} catch (Exception e) {
				TPLogger.getLogger().error("Error while getting interactions", e);
			} finally {
				if (dq != null) {
					dq.releaseConnection();
				}
			}
			
			epositions = construtsEPositionDataForSearch(sDo);	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return epositions;
		
	}
	
	private List<EpositionData> construtsEPositionDataForSearch(List<SimpleDataObject> applicants) {
		List<EpositionData> epositions = new ArrayList<EpositionData>();
		Iterator<SimpleDataObject> itr = applicants.iterator();
		ReportManager reportManager =new ReportManager();
		while (itr.hasNext()) {
			SimpleDataObject sDo = itr.next();
			EpositionData eposition = new EpositionData();
			String positionTitle=sDo.getString("positionTitle");
			if(!Utils.isBlankOrNull(positionTitle)){
				eposition.setPositionTitle(positionTitle);
			}
			epositions.add(eposition);
		}
		return epositions;
	}
}
