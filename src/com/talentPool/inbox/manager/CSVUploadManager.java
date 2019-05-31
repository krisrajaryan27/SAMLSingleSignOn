/**
 * 
 */
package com.talentPool.inbox.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.ImportData;

/**
 * @author shantanu
 *
 */
public class CSVUploadManager {

	public void updateExcelImportTable(String sessionId, String rowNumber, ArrayList applicantInfo){
		DBPreparedQuery dq = null;
		//String applicantName = (String)applicantInfo.get(0);
		TPLogger.getLogger().info("Applicant Info: " + applicantInfo);
		String applicantId = (String)applicantInfo.get(0);
		String rowNo = (String)applicantInfo.get(1);
		String status = (String)applicantInfo.get(2);
	
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_updateExcelImportTable");
			dq.setString(1, applicantId);
			dq.setString(2, status);
			dq.setLong(3, new Long(sessionId).longValue());
			dq.setString(4, rowNo);			
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updateExcelImportTable", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public ArrayList getSourceExists(){
		ArrayList<String> sourceExists = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetSourceExists");
			sourceExists = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceExists;
	}
	
	
	public String getDegreeId(String degree){
		String degreeId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetDegreeId");
			dq.setString(1, degree);
			dq.setString(2, degree);
			degreeId = dq.getIdResult();
			if(Utils.isBlankOrNull(degreeId)){
				degreeId="-1";
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return degreeId;
	}
	
	
	public String getDegreeAliasId(String degree){
		String degreeId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetDegreeAliasId");
			dq.setString(1, degree);
			degreeId = dq.getIdResult();
			if(Utils.isBlankOrNull(degreeId)){
				degreeId="-1";
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return degreeId;
	}
	
	
	public String getBranchId(String branch){
		String branchId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetBranchId");
			dq.setString(1, branch);
			dq.setString(2, branch);
			branchId = dq.getIdResult();
			if(Utils.isBlankOrNull(branchId)){
				branchId="-1";
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return branchId;
	}
	
	public String getSkillId(String skill){
		String skillId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetSkillId");
			dq.setString(1, skill);
			dq.setString(2, skill);
			skillId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return skillId;
	}
	
	public String getSkillAliasId(String skill){
		String skillId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetSkillAliasId");
			dq.setString(1, skill);
			skillId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return skillId;
	}
	
	public String getSourceId(String source){
		String sourceId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetSourceId");
			dq.setString(1, source);
			sourceId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sourceId;
	}
	
	
	public void saveRowData(ImportData iData, String sessionId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran= new DBTransaction();
			dq = new DBPreparedQuery("dExcelUploadManager_InsertExcelData",tran);
			int cnt = 1;
			dq.setLong(cnt++, new Long(sessionId).longValue());
			dq.setId(cnt++, iData.getRowNumber());
			dq.setString(cnt++, iData.getName());
			dq.setString(cnt++, iData.getEmail1());
			dq.setString(cnt++, iData.getEmail2());
			dq.setString(cnt++, iData.getPhone1());
			dq.setString(cnt++, iData.getPhone2());
			dq.setString(cnt++, iData.getMobile());
			dq.setString(cnt++, iData.getDateOfBirth());
			dq.setString(cnt++, iData.getExperience());
			dq.setString(cnt++, iData.getSource());
			dq.setString(cnt++, iData.getCurrentLocation());
			dq.setString(cnt++, iData.getSkills());
			dq.setString(cnt++, iData.getYOP1());
			dq.setString(cnt++, iData.getStartDate1());
			dq.setString(cnt++, iData.getEndDate1());
			dq.setString(cnt++, iData.getInstitute1());
			dq.setString(cnt++, iData.getDegree1());
			dq.setString(cnt++, iData.getBranch1());
			dq.setString(cnt++, iData.getUniversity1());
			dq.setString(cnt++, iData.getTypeOfProgram1());
			dq.setString(cnt++, iData.getPercentage1());
			dq.setString(cnt++, iData.getYOP2());
			dq.setString(cnt++, iData.getStartDate2());
			dq.setString(cnt++, iData.getEndDate2());
			dq.setString(cnt++, iData.getInstitute2());
			dq.setString(cnt++, iData.getDegree2());
			dq.setString(cnt++, iData.getBranch2());
			dq.setString(cnt++, iData.getUniversity2());
			dq.setString(cnt++, iData.getTypeOfProgram2());
			dq.setString(cnt++, iData.getPercentage2());
			dq.setString(cnt++, iData.getYOP3());
			dq.setString(cnt++, iData.getStartDate3());
			dq.setString(cnt++, iData.getEndDate3());
			dq.setString(cnt++, iData.getInstitute3());
			dq.setString(cnt++, iData.getDegree3());
			dq.setString(cnt++, iData.getBranch3());
			dq.setString(cnt++, iData.getUniversity3());
			dq.setString(cnt++, iData.getTypeOfProgram3());
			dq.setString(cnt++, iData.getPercentage3());
			dq.setString(cnt++, iData.getYOP4());
			dq.setString(cnt++, iData.getStartDate4());
			dq.setString(cnt++, iData.getEndDate4());
			dq.setString(cnt++, iData.getInstitute4());
			dq.setString(cnt++, iData.getDegree4());
			dq.setString(cnt++, iData.getBranch4());
			dq.setString(cnt++, iData.getUniversity4());
			dq.setString(cnt++, iData.getTypeOfProgram4());
			dq.setString(cnt++, iData.getPercentage4());
			dq.setString(cnt++, iData.getCurrentEmployer());
			dq.setString(cnt++, iData.getCurrentCTC());
			dq.setString(cnt++, iData.getExpectedCTC());			
			dq.setString(cnt++, iData.getNoticePeriod());
			dq.setString(cnt++, iData.getNote());			
			dq.setString(cnt++, iData.getError());			
			dq.setString(cnt++, iData.getOriginalResumePath());			
			dq.setString(cnt++, iData.getEmploymentFromDate1());
			dq.setString(cnt++, iData.getEmploymentToDate1());
			dq.setString(cnt++, iData.getEmploymentEmployer1());
			dq.setString(cnt++, iData.getEmploymentDesignation1());	
			dq.setString(cnt++, iData.getEmploymentType1());
			dq.setString(cnt++, iData.getEmploymentLocation1());
			dq.setString(cnt++, iData.getEmploymentCountry1());
			dq.setString(cnt++, iData.getEmploymentFromDate2());
			dq.setString(cnt++, iData.getEmploymentToDate2());
			dq.setString(cnt++, iData.getEmploymentEmployer2());
			dq.setString(cnt++, iData.getEmploymentDesignation2());
			dq.setString(cnt++, iData.getEmploymentType2());
			dq.setString(cnt++, iData.getEmploymentLocation2());
			dq.setString(cnt++, iData.getEmploymentCountry2());
			dq.setString(cnt++, iData.getEmploymentFromDate3());
			dq.setString(cnt++, iData.getEmploymentToDate3());
			dq.setString(cnt++, iData.getEmploymentEmployer3());
			dq.setString(cnt++, iData.getEmploymentDesignation3());			
			dq.setString(cnt++, iData.getEmploymentFromDate4());
			dq.setString(cnt++, iData.getEmploymentToDate4());
			dq.setString(cnt++, iData.getEmploymentEmployer4());
			dq.setString(cnt++, iData.getEmploymentDesignation4());			
			dq.setString(cnt++, iData.getEmploymentFromDate5());
			dq.setString(cnt++, iData.getEmploymentToDate5());
			dq.setString(cnt++, iData.getEmploymentEmployer5());
			dq.setString(cnt++, iData.getEmploymentDesignation5());
			dq.execute();
			
			//	Add custom Field values
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFieldManager.insertCustomFieldValues(iData.getCustomFields(), sessionId+iData.getRowNumber(), CustomFieldConstants.ENTITY_TYPE_EXCEL_IMPORT, tran);
			tran.commit();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (SQLException sq) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
			throw new Exception();
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}
	
	public ArrayList<ImportData> getExcelImportData(String sessionId, String lastRowId) {
		ArrayList<ImportData> excelList = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetExcelImportData");
			dq.setString(1, sessionId);
			dq.setString(2, lastRowId);
			excelList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return excelList;
	}
	
	public List getExcelImportFailData(String sessionId, String lastRowId) {
		DBPreparedQuery dq = null;
		List variables = null;
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetCSVImportFailData");			
			dq.setLong(1, new Long(sessionId).longValue());
			dq.setString(2, InboxConstants.EXCEL_IMPOT_SUCCESS);
			dq.setString(3, lastRowId);
			variables = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return variables;
	}
	
	public String getRowNumbersToEmport(String sessionId) {
		DBPreparedQuery dq = null;
		ArrayList rows = null;
		String rowNumbers = "";
		try {
			dq = new DBPreparedQuery("dExcelUploadManager_GetRowNumbersToEmport");
			dq.setLong(1, new Long(sessionId).longValue());
			dq.setString(2, InboxConstants.EXCEL_IMPOT_SUCCESS);
			rows = dq.getResult();
			for (int i = 0; rows!=null && i < rows.size(); i++) {
				SimpleDataObject row = (SimpleDataObject) rows.get(i);
				String rowNumber = (String) row.getString("rowId");
				if(Utils.isBlankOrNull(rowNumbers)){
					rowNumbers = rowNumber;
				}else{
					rowNumbers += ","+rowNumber;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return rowNumbers;
	}

}
