package com.talentPool.positions.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.budget.manager.BudgetManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.inbox.scheduler.DeleteFilesThread;
import com.talentPool.masters.dataobject.LocationData;
import com.talentPool.masters.dataobject.SkillData;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.LocationManager;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.PositionParserConstants;
import com.talentPool.positions.constants.PositionDraftConstants;
import com.talentPool.positions.dataobject.DraftData;
import com.talentPool.positions.utils.PositionParser;
import com.talentPool.positions.utils.PositionParserUtils;
import com.talentPool.requisition.constants.RequisitionConstants;
import com.talentPool.requisition.manager.RequisitionManager;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.UserManager;

public class PositionDraftsManager {
	private static final String SEPARATOR_COMMA_WITH_SPACE = ", ";
	private static final String SEPARATOR_COMMA = ",";
	public ArrayList<SimpleDataObject> getAllDraftPosition(String userId,PermissionSet permissionSet){
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> draftPositions = null;
		try{
			dq = new DBPreparedQuery("dPositionDraftManager_GetAllPositionDrafts");
			dq.setString(1,userId);
			dq.setString(2,PositionDraftConstants.POSITION_DRAFT_STATUS_SHARED);
			draftPositions = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while populating Draft positions ", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return draftPositions;
	}
	
	public SimpleDataObject getDraftData(String draftId){
		DBPreparedQuery dq = null;
		SimpleDataObject sdo = null;
		try{
			dq = new DBPreparedQuery("dPositionDraftManager_getPositionDraftData");
			dq.setId(1, draftId);
			sdo = (SimpleDataObject)dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while populating Draft Data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}
	public SimpleDataObject getDraftDataByName(String draftName){
		DBPreparedQuery dq = null;
		SimpleDataObject sdo = null;
		try{
			dq = new DBPreparedQuery("dPositionDraftManager_getPositionDraftDataByName");
			dq.setString(1, draftName);
			sdo = (SimpleDataObject)dq.getSingleObjectResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}
	
	public void deleteDraft(String draftId, DBTransaction tran){
		DBPreparedQuery dq = null;
		try{
			SimpleDataObject sdo = getDraftData(draftId);
			if(tran != null) {
				dq = new DBPreparedQuery("dPositionDraftManager_deletePositionDrafts", tran);
			} else {
				dq = new DBPreparedQuery("dPositionDraftManager_deletePositionDrafts");
			}
			
			dq.setId(1, draftId);
			dq.execute();
			
			ArrayList<String> files = new ArrayList<String>();
			files.add(sdo.getString("filePath"));
			DeleteFilesThread t = new DeleteFilesThread(files, false);
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting Draft positions ", e);
		} finally {			
			if (dq != null) {
				if(tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}				
			}
		}
	}
	
	public void updateDraftStatus(String draftId,String showStatus){
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dPositionDraftManager_updatePositionDraftStatus");
			dq.setId(1, showStatus);
			dq.setId(2, draftId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while updating draft status", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateDraftTitle(String draftId,String fileName){
		DBPreparedQuery dq = null;
		try{
			dq = new DBPreparedQuery("dPositionDraftManager_updatePositionDraftTitle");
			dq.setString(1, fileName);
			dq.setId(2, draftId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while updating draft Title", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public String createPositionDraft(DraftData data, String userId) {
		String draftId = null;
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		DBQuery dbq = null;
		try{		
			tran = new DBTransaction();
			
			String fileName = getDraftName(data, userId, tran);			
			
			draftId = getUserDraftIdForName(fileName, userId, tran);			
			if(!Utils.isBlankOrNull(draftId)) {
				deleteDraft(draftId, tran);
			}
			
			dq = new DBPreparedQuery("dPositionDraftManager_CreatePositionDraft", tran);
			dq.setString(1, fileName);
			dq.setString(2, data.getRelativeFilePath());
			dq.setString(3, PositionDraftConstants.POSITION_DRAFT_STATUS_PRIVATE);
			dq.setString(4, userId);
			dq.execute();
			
			dbq = new DBQuery("dFetchLastInsertID", tran);
			draftId = dbq.getIdResult();
			
			tran.commit();
			
			data.setOriginalFileName(fileName);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if(tran != null) {
				try {
					tran.rollback();
				} catch (SQLException e1) {
					TPLogger.getLogger().error(GlobalConstants.ERROR, e1);
				}
			}
		} finally {
			if (tran != null) {
				tran.release();
			}
		}
		return draftId;
	}
	
	private String getDraftName(DraftData data, String userId, DBTransaction tran) throws SQLException, NoResultFoundException {
		String fileName = "";
		if(data != null && !Utils.isBlankOrNull(data.getOriginalFileName())) {
			fileName = data.getOriginalFileName();
		} else {
			DraftData draftData = getLastUserDraftHavingSystemGeneratedName(userId, tran);
			if(draftData == null) {
				fileName = TPLabels.getLabel("common.position_draft") + " 0";
			} else {
				String[] parts = draftData.getOriginalFileName().split(" ");
				if(parts != null && parts.length > 2) {
					if((Integer.parseInt(parts[2]) + 1) < PositionDraftConstants.MAX_DRAFTS_HAVING_SYSTEM__DEFINED_NAME_PER_USER) {
						fileName = TPLabels.getLabel("common.position_draft") + " " + (Integer.parseInt(parts[2]) + 1);
					} else {
						draftData = getOldestUserDraft(userId, tran);
						fileName = draftData.getOriginalFileName();
					}
				}
			}
		}		
		return fileName;
	}

	private Map<String, String> populatePositionDef(SimpleDataObject position) throws SQLException {
		List<CustomFieldData> customFields = (List<CustomFieldData>) position.getAttribute("customFields");		
		
		PositionParserUtils positionParserUtils = new PositionParserUtils();		
		Map<String, String> map = positionParserUtils.getPositionAttributesToBeImported(customFields);
		if(!Utils.isBlankOrNull(position.getString("positionName"))) {
			map.put(PositionParserConstants.POSITION_NAME, position.getString("positionName"));
		}
		if(!Utils.isBlankOrNull(position.getString("positionCode"))) {
			map.put(PositionParserConstants.POSITION_CODE, position.getString("positionCode"));
		}		
		populatePositionOwner(position, map);
		populatePositionRequisitioner(position, map);
		populatePositionLocation(position, map);
		populatePositionDepartment(position, map);
		populatePositionChildDepartment(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_2, "departmentId", "subDepartmentId");
		populatePositionChildDepartment(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_3, "subDepartmentId", "subSubDepartmentId");
		populatePositionChildDepartment(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_4, "subSubDepartmentId", "sub3DepartmentId");
		populatePositionChildDepartment(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_5, "sub3DepartmentId", "sub4DepartmentId");
		if(!Utils.isBlankOrNull(position.getString("vacancies"))) {
			map.put(PositionParserConstants.VACANCIES, position.getString("vacancies"));
		}
		if(!Utils.isBlankOrNull(position.getString("hireByDate"))) {
			map.put(PositionParserConstants.HIRE_BY_DATE, position.getString("hireByDate"));
		}
		if(!Utils.isBlankOrNull(position.getString("positionLevel"))) {
			map.put(PositionParserConstants.POSITION_LEVEL, position.getString("positionLevel"));
		}
		if(!Utils.isBlankOrNull(position.getString("positionReferalFees"))) {
			map.put(PositionParserConstants.REFERRAL_FEES, position.getString("positionReferalFees"));
		}
		if(!Utils.isBlankOrNull(position.getString("note"))) {
			map.put(PositionParserConstants.NOTE, position.getString("note"));
		}
		if(!Utils.isBlankOrNull(position.getString("responsibilities"))) {
			map.put(PositionParserConstants.JOB_RESPONSIBILITIES, position.getString("responsibilities"));
		}		
		populatePositionDegree(position, map);
		populatePositionBranch(position, map);
		if(!Utils.isBlankOrNull(position.getString("minimumExperience"))) {
			map.put(PositionParserConstants.MINIMUM_EXPERIENCE, position.getString("minimumExperience"));
		}
		if(!Utils.isBlankOrNull(position.getString("maximumExperience"))) {
			map.put(PositionParserConstants.MAXIMUM_EXPERIENCE, position.getString("maximumExperience"));
		}		
		populatePositionPrimarySkills(position, map);
		populatePositionSecondarySkills(position, map);
		if(!Utils.isBlankOrNull(position.getString("requirements"))) {
			map.put(PositionParserConstants.JOB_REQUIREMENTS, position.getString("requirements"));
		}	
		if(!Utils.isBlankOrNull(position.getString("approvalComment"))) {
			map.put(PositionParserConstants.APPROVAL_COMMENT, position.getString("approvalComment"));
		}
		populateBudgetItem(position, map);
		populateGrade(position, map);	
		populateBand(position, map);	
		populateNotifyUsers(position, map);
		populatePositionNextApprover(position, map);
		populatePositionApprovalDecision(position, map);
		populateRequisitionApprovalTemplate(position, map);
		populateCustomFieldsToPositionDef(position, map);
		populatePositionBU(position, map);
		populatePositionCostCenter(position, map);
		populateNaukriFields(position,map);
		return map;
	}
	
	private void populateNaukriFields(SimpleDataObject position,Map<String, String> map) {
		map.put(PositionParserConstants.CONTACT_PERSON_NAME,position.getString("contactPersonName"));
		map.put(PositionParserConstants.JOB_INDUSTRY_CODE, position.getString("jobIndustryCode"));
		map.put(PositionParserConstants.JOB_FUNCTION_CODE, position.getString("jobFunctionCode"));
		map.put(PositionParserConstants.JOB_ROLE_CODE, position.getString("jobRoleCode"));
		map.put(PositionParserConstants.JOB_KEY_WORDS, position.getString("jobKeywords"));
		map.put(PositionParserConstants.COUNTRY, position.getString("country"));
		map.put(PositionParserConstants.MINIMUM_SALARY, position.getString("minimumSalary"));
		map.put(PositionParserConstants.MAXIMUM_SALARY, position.getString("maximumSalary"));
		map.put(PositionParserConstants.BENEFITS_DESCRIPTION, position.getString("benefitsDescription"));
		map.put(PositionParserConstants.DISPLAY_SALARY, position.getString("displaySalary"));
		map.put(PositionParserConstants.DESIRED_CANDIDATE_SUMMARY_TEXT, position.getString("desiredCandidateSummaryText"));
		map.put(PositionParserConstants.CONTACT_PERSON_EMAIL, position.getString("contactPersonEmail"));
		map.put(PositionParserConstants.APPLY_BY_WEB_URL, position.getString("applyByWebURL"));
		map.put(PositionParserConstants.JOBFEED_RESPONSE_EMAIL, position.getString("jobFeedResponseEmail"));
		map.put(PositionParserConstants.SALARY_CURRENCY, position.getString("salaryCurrency"));
	}

	public void savePositionAsDraft(SimpleDataObject position, String draftName, String userId) throws Exception {
		Map<String, String> map = populatePositionDef(position);
		
		PositionParserUtils parserUtils = new PositionParserUtils();
		String contents = parserUtils.getFormattedPositionText(map);
		
		DocumentUploader documentUploader = new DocumentUploader();
		DocumentData documentData = documentUploader.saveTextAsDocument(contents, ".txt");
		documentData.setOriginalFileName(draftName);
		
		DraftData draftData = getDraftData(documentData);
		
		createPositionDraft(draftData, userId);			
	}
	
	public SimpleDataObject getPositionBO(String draftId) throws Exception {		
		SimpleDataObject draft = getDraftData(draftId);		
		SimpleDataObject position = parsePosition(draft.getString("filePath"));
		return position;
	}
	
	private void populateGrade(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("gradeId"))) {
			MastersManager mastersManager = new MastersManager();
			SimpleDataObject budgetGrade;
			try {
				budgetGrade = mastersManager.getBudgetGrade(position.getString("gradeId"));
				if(budgetGrade!= null){
					if(!Utils.isBlankOrNull(budgetGrade.getString("itemName"))) {
						map.put(PositionParserConstants.GRADE, budgetGrade.getString("itemName"));
					}
				}
			} catch (MasterExistException e) {
				
			}			
		}		
	}
	
	private void populateGradeId(SimpleDataObject position, Map<String, String> map) {
		String grade = (String)map.get(PositionParserConstants.GRADE);
		if(!Utils.isBlankOrNull(grade)) {
			MastersManager mastersManager = new MastersManager();
			String gradeId = mastersManager.getGradeId(grade);
			if(gradeId != null) {
				position.setAttribute("gradeId", gradeId);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.GRADE, grade, "");
			}
		}
	}
	
	private void populateBand(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("bandId"))) {
			MastersManager mastersManager = new MastersManager();
			SimpleDataObject band;
			try {
				band = mastersManager.getBudgetBand(position.getString("bandId"));
				if(band!= null){
					if(!Utils.isBlankOrNull(band.getString("itemName"))) {
						map.put(PositionParserConstants.BAND, band.getString("itemName"));
					}
				}
			} catch (MasterExistException e) {
				
			}			
		}		
	}
	
	private void populateBandId(SimpleDataObject position, Map<String, String> map) {
		String band = (String)map.get(PositionParserConstants.BAND);
		if(!Utils.isBlankOrNull(band)) {
			MastersManager mastersManager = new MastersManager();
			String bandId = mastersManager.getBandId(band);
			if(bandId != null) {
				position.setAttribute("bandId", bandId);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.BAND, band, "");
			}
		}
	}
	
	private void populateBudgetItem(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("budgetItemId"))) {
			BudgetManager budgetManager = new BudgetManager();
			BudgetItem budgetItem;
			budgetItem = budgetManager.getBudgetItemToView(position.getString("budgetItemId"));
			if(budgetItem!= null){
				if(!Utils.isBlankOrNull(budgetItem.getBudgetItemName())) {
					map.put(PositionParserConstants.BUDGET_ITEM, budgetItem.getBudgetItemName());
				}
			}				
		}		
	}
	
	
	private void populateBudgetItemId(SimpleDataObject position, Map<String, String> map) {
		String budgetItem = (String)map.get(PositionParserConstants.BUDGET_ITEM);
		if(!Utils.isBlankOrNull(budgetItem)) {
			BudgetManager budgetManager = new BudgetManager();
			String budgetItemId = budgetManager.getBudgetItemId(budgetItem);
			if(budgetItemId != null) {
				position.setAttribute("budgetItemId", budgetItemId);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.BUDGET_ITEM, budgetItem, "");
			}
		}
	}
	
	private SimpleDataObject populatePositionBO(Map<String, String> map) throws SQLException {		
		SimpleDataObject position = new SimpleDataObject();
		position.setAttribute("warnings", new ArrayList<List<String>>());
		List<CustomFieldData> customFields = null;
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);
			position.setAttribute("customFields", customFields);
		}
		String positionId = ""+System.currentTimeMillis();
		position.setAttribute("positionId", positionId);
		position.setAttribute("positionName", map.get(PositionParserConstants.POSITION_NAME));
		position.setAttribute("positionCode", map.get(PositionParserConstants.POSITION_CODE));
		populatePositionOwnerId(position, map);
		populatePositionRequisitionerId(position, map);
		populatePositionLocationId(position, map);
		populatePositionDepartmentId(position, map);
		populatePositionChildDepartmentId(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_2, "departmentId", "subDepartmentId", "subDepartment");
		populatePositionChildDepartmentId(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_3, "subDepartmentId", "subSubDepartmentId", "subSubDepartment");
		populatePositionChildDepartmentId(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_4, "subSubDepartmentId", "sub3DepartmentId", "sub3Department");
		populatePositionChildDepartmentId(position, map, PositionParserConstants.DEPT_HIERARCHY_LEVEL_5, "sub3DepartmentId", "sub4DepartmentId", "sub4Department");
		position.setAttribute("vacancies", map.get(PositionParserConstants.VACANCIES));
		position.setAttribute("hireByDate", map.get(PositionParserConstants.HIRE_BY_DATE));
		position.setAttribute("positionLevel", map.get(PositionParserConstants.POSITION_LEVEL));
		position.setAttribute("positionReferalFees", map.get(PositionParserConstants.REFERRAL_FEES));
		position.setAttribute("note", map.get(PositionParserConstants.NOTE));
		position.setAttribute("responsibilities", map.get(PositionParserConstants.JOB_RESPONSIBILITIES));		
		populatePositionDegreeId(position, map);
		populatePositionBranchId(position, map);
		position.setAttribute("minimumExperience", map.get(PositionParserConstants.MINIMUM_EXPERIENCE));
		position.setAttribute("maximumExperience", map.get(PositionParserConstants.MAXIMUM_EXPERIENCE));		
		populatePositionPrimarySkillIds(position, map);
		populatePositionSecondarySkillIds(position, map);		
		position.setAttribute("requirements", map.get(PositionParserConstants.JOB_REQUIREMENTS));				
		position.setAttribute("approvalComment", map.get(PositionParserConstants.APPROVAL_COMMENT));
		populateBudgetItemId(position, map);
		populateGradeId(position, map);
		populateBandId(position, map);
		populateNotifyUserIds(position, map);
		populatePositionNextApproverId(position, map);
		populatePositionApprovalDecisionId(position, map);
		populateRequisitionApprovalTemplateId(position, map);
		populateCustomFieldsToPositionBO(position, map);
		populatePositionBUId(position, map);
		populatePositionCostCenterId(position, map);
		populateNaukriFieldsToPosition(position,map);
		return position;
	}

	private void populateNaukriFieldsToPosition(SimpleDataObject position,Map<String, String> map) {
		position.setAttribute("contactPersonName", map.get(PositionParserConstants.CONTACT_PERSON_NAME));
		position.setAttribute("jobIndustryCode", map.get(PositionParserConstants.JOB_INDUSTRY_CODE));
		position.setAttribute("jobFunctionCode", map.get(PositionParserConstants.JOB_FUNCTION_CODE));
		position.setAttribute("jobRoleCode", map.get(PositionParserConstants.JOB_ROLE_CODE));
		position.setAttribute("jobKeywords", map.get(PositionParserConstants.JOB_KEY_WORDS));
		position.setAttribute("country", map.get(PositionParserConstants.COUNTRY));
		position.setAttribute("minimumSalary",map.get(PositionParserConstants.MINIMUM_SALARY));
		position.setAttribute("maximumSalary", map.get(PositionParserConstants.MAXIMUM_SALARY));
		position.setAttribute("benefitsDescription", map.get(PositionParserConstants.BENEFITS_DESCRIPTION));
		position.setAttribute("displaySalary", map.get(PositionParserConstants.DISPLAY_SALARY));
		position.setAttribute("desiredCandidateSummaryText", map.get(PositionParserConstants.DESIRED_CANDIDATE_SUMMARY_TEXT));
		position.setAttribute("contactPersonEmail", map.get(PositionParserConstants.CONTACT_PERSON_EMAIL));
		position.setAttribute("applyByWebURL", map.get(PositionParserConstants.APPLY_BY_WEB_URL));
		position.setAttribute("jobFeedResponseEmail", map.get(PositionParserConstants.JOBFEED_RESPONSE_EMAIL));
		position.setAttribute("salaryCurrency", map.get(PositionParserConstants.SALARY_CURRENCY));
	}

	private void populatePositionRequisitionerId(SimpleDataObject position, Map<String, String> map) {
		String requisitioner = (String)map.get(PositionParserConstants.REQUESTED_BY);
		if(!Utils.isBlankOrNull(requisitioner)) {
			UserManager userManager = new UserManager();			
			position.setAttribute("requisitioner", requisitioner);			
			com.talentPool.positions.dataobject.UserData data = userManager.getUserId(requisitioner);
			if(data != null) {
				position.setAttribute("requisitionerId", data.getUserId());
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.REQUESTED_BY, requisitioner, "");
			}
		}
	}
	
	private void populatePositionRequisitioner(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("requisitionerId"))) {
			LoginManager loginManager = new LoginManager();
			LoginData data = loginManager.getUser(position.getString("requisitionerId"));
			if(data != null) {
				map.put(PositionParserConstants.REQUESTED_BY, data.getName());
			}			
		}
	}
	
	private void populatePositionOwnerId(final SimpleDataObject position,final  Map<String, String> map) {
		String positionOwnerName = (String)map.get(PositionParserConstants.POSITION_OWNER);
		if(!Utils.isBlankOrNull(positionOwnerName)) {
			UserManager userManager = new UserManager();			
			position.setAttribute("positionOwnerName", positionOwnerName);			
			com.talentPool.positions.dataobject.UserData data = userManager.getUserId(positionOwnerName);
			if(data != null) {
				position.setAttribute("positionOwnerId", data.getUserId());
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.POSITION_OWNER, positionOwnerName, "");
			}
		}
	}
	
	private void populatePositionOwner(final SimpleDataObject position,final Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("positionOwnerId"))) {
			LoginManager loginManager = new LoginManager();
			LoginData data = loginManager.getUser(position.getString("positionOwnerId"));
			if(data != null) {
				map.put(PositionParserConstants.POSITION_OWNER, data.getName());
			}			
		}
	}
	
	private void populatePositionLocationId(SimpleDataObject position, Map<String, String> map) throws SQLException {
		String locationName = (String) map.get(PositionParserConstants.LOCATION);
		if(!Utils.isBlankOrNull(locationName)) {
			LocationManager locationManager = new LocationManager();
			position.setAttribute("locationName", locationName);			
			List<LocationData> locationData = locationManager.getLocationsListByName(locationName);
			if(locationData != null && locationData.size()>0) {
				String locationIds = "";
				for (int i = 0; i < locationData.size(); i++) {
					if(Utils.isBlankOrNull(locationIds))
						locationIds =locationData.get(i).getLocationId();
					else
						locationIds +=","+locationData.get(i).getLocationId();
				}
				position.setAttribute("locationId", locationIds);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.LOCATION, locationName, "");
			}			
		}
	}
	
	private void populatePositionLocation(SimpleDataObject position, Map<String, String> map) throws SQLException {
		if(!Utils.isBlankOrNull(position.getString("locationId"))) {			
			LocationManager locationManager = new LocationManager();
			List<LocationData> locationDataList = locationManager.getLocationsListByLocationId(position.getString("locationId"));
			if(locationDataList != null && locationDataList.size()>0) {
				String locationName = "";
				for (int i = 0; i < locationDataList.size(); i++) {
					if(Utils.isBlankOrNull(locationName))
						locationName=locationDataList.get(i).getLocationName();
					else {
						locationName+=","+locationDataList.get(i).getLocationName();
					}
				}
				map.put(PositionParserConstants.LOCATION, locationName);
			}			
		}
	}
	
	private void populatePositionDegreeId(SimpleDataObject position, Map<String, String> map) {
		String education = map.get(PositionParserConstants.EDUCATION);
		if(!Utils.isBlankOrNull(education)) {			
			String[] degrees = education.split(",");
			String degreeIds = "";
			MastersManager mastersManager = new MastersManager();
			
			for (String degree : degrees) {
				String degreeId = mastersManager.getDegreeId(degree);
				if(!Utils.isBlankOrNull(degreeId)) {
					if(degreeIds.length()>0){
						degreeIds += ",";
					}
					degreeIds += degreeId;
				}
			}	
			
			if(!Utils.isBlankOrNull(degreeIds)) {
				position.setAttribute("degreeId", degreeIds);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.EDUCATION, education, "");
			}
		}
	}
	
	private void populatePositionDegree(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("degreeId"))) {
			String[] degreeIds = position.getString("degreeId").split(",");
			String degreeNames = "";
			for (String degreeId : degreeIds) {
				String degreeName = CommonUtils.getDegreeName(degreeId);	
				if(!Utils.isBlankOrNull(degreeName)) {
					if(degreeNames.length()>0){
						degreeNames += ",";
					}
					degreeNames += degreeName;
				}
			}
			if(!Utils.isBlankOrNull(degreeNames)) {
				map.put(PositionParserConstants.EDUCATION, degreeNames);
			}		
		}
	}

	private void populatePositionBranchId(SimpleDataObject position, Map<String, String> map) {
		String branch = map.get(PositionParserConstants.BRANCH);
		if(!Utils.isBlankOrNull(branch) && !PositionConstants.ANY_BRANCH.equals(branch)) {
			
			String[] branches = branch.split(",");
			String branchIds = "";
			MastersManager mastersManager = new MastersManager();
			
			for (String string : branches) {
				String branchId = mastersManager.getBranchId(string);
				if(!Utils.isBlankOrNull(branchId)) {
					if(branchIds.length()>0){
						branchIds += ",";
					}
					branchIds += branchId;
				}
			}	
			if(!Utils.isBlankOrNull(branchIds)) {
				position.setAttribute("branchId", branchIds);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.BRANCH, branch, PositionConstants.ANY_BRANCH);
			}
		}		
	}
	
	private void populatePositionBranch(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("branchId"))) {					
			String[] branchIds = position.getString("branchId").split(",");
			String branchNames = "";
			for (String branchId : branchIds) {
				String branchName = CommonUtils.getBranchName(branchId);	
				if(!Utils.isBlankOrNull(branchName)) {
					if(branchNames.length()>0){
						branchNames += ",";
					}
					branchNames += branchName;
				}
			}		
			if(!Utils.isBlankOrNull(branchNames)) {
				map.put(PositionParserConstants.BRANCH, branchNames);
			}	
		}		
	}
	
	private void populatePositionPrimarySkillIds(SimpleDataObject position, Map<String, String> map) {		
		String skillIds = getPositionSkillIds(position, map, PositionParserConstants.PRIMARY_SKILLS);
		if(!Utils.isBlankOrNull(skillIds)) {
			position.setAttribute("primarySkills", skillIds);
		}
	}
	
	private void populatePositionPrimarySkills(SimpleDataObject position, Map<String, String> map) {		
		String skills = getPositionSkills(position, "primarySkills");
		if(!Utils.isBlankOrNull(skills)) {
			map.put(PositionParserConstants.PRIMARY_SKILLS, skills);
		}
	}

	private void populatePositionSecondarySkillIds(SimpleDataObject position, Map<String, String> map) {		
		String skillIds = getPositionSkillIds(position, map, PositionParserConstants.SECONDARY_SKILLS);
		if(!Utils.isBlankOrNull(skillIds)) {
			position.setAttribute("secondarySkills", skillIds);
		}		
	}
	
	private void populatePositionSecondarySkills(SimpleDataObject position, Map<String, String> map) {		
		String skills = getPositionSkills(position, "secondarySkills");
		if(!Utils.isBlankOrNull(skills)) {
			map.put(PositionParserConstants.SECONDARY_SKILLS, skills);
		}		
	}
	
	private String getPositionSkillIds(SimpleDataObject position, Map<String, String> map, String key) {
		StringBuffer skillIds = new StringBuffer();
		StringBuffer unparsedVals = new StringBuffer();
		if(!Utils.isBlankOrNull(map.get(key))) {
			String[] skills = map.get(key).split(SEPARATOR_COMMA);
			if(skills != null && skills.length > 0) {
				MastersManager mastersManager = new MastersManager();				
				for(int i = 0; i < skills.length; i++) {
					String skill = skills[i].trim();
					String skillId = mastersManager.getSkillId(skill);
					if(!Utils.isBlankOrNull(skillId)) {
						append(skillIds, skillId, SEPARATOR_COMMA);
					} else {
						append(unparsedVals, skill, SEPARATOR_COMMA_WITH_SPACE);
					}
				}				
			}			
		}
		if(unparsedVals.length() > 0) {
			populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), key, unparsedVals.toString(), "");
		}
		return skillIds.toString();
	}
	
	private String getPositionSkills(SimpleDataObject position, String key) {
		StringBuffer skills = new StringBuffer();
		if(!Utils.isBlankOrNull(position.getString(key))) {
			String[] _skills = position.getString(key).split(SEPARATOR_COMMA);
			if(_skills != null && _skills.length > 0) {
				MastersManager mastersManager = new MastersManager();				
				for(int i = 0; i < _skills.length; i++) {
					SkillData skillData = mastersManager.getSkill(_skills[i].trim());
					if(skillData != null) {
						append(skills, skillData.getItemName(), SEPARATOR_COMMA);
					}
				}				
			}			
		}
		return skills.toString();
	}
	
	private void populatePositionDepartmentId(SimpleDataObject position, Map<String, String> map) {
		String department = map.get(PositionParserConstants.DEPT_HIERARCHY_LEVEL_1);
		if(!Utils.isBlankOrNull(department)) {
			position.setAttribute("department", department);
			String deptId = CommonUtils.getDeptId(department);
			if(!Utils.isBlankOrNull(deptId)) {
				position.setAttribute("departmentId", deptId);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.DEPT_HIERARCHY_LEVEL_1, department, "");
			}
		}		
	}
	
	private void populatePositionChildDepartmentId(SimpleDataObject position, Map<String, String> map, String deptHeirarchyLevel, String deptIdKey, String childDeptIdKey, String childDeptNameKey) {
		String subDepartment = map.get(deptHeirarchyLevel);
		if(!Utils.isBlankOrNull(subDepartment)) {
			boolean isValueParsed = true;
			if(!Utils.isBlankOrNull(position.getString(deptIdKey))) {
				MastersManager mastersManager = new MastersManager();
				position.setAttribute(childDeptNameKey, subDepartment);			
				String deptId = mastersManager.getChildDeptId(subDepartment, position.getString(deptIdKey));
				if(!Utils.isBlankOrNull(deptId)) {
					position.setAttribute(childDeptIdKey, deptId);
				} else {
					isValueParsed = false;
				}
			} else {
				isValueParsed = false;
			}		
			if(!isValueParsed) {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), deptHeirarchyLevel, subDepartment, "");
			}
		}		
	}
	
	private void populatePositionDepartment(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("departmentId"))) {
			String department = CommonUtils.getDeptName(position.getString("departmentId"));
			if(!Utils.isBlankOrNull(department)) {
				map.put(PositionParserConstants.DEPT_HIERARCHY_LEVEL_1, department);
			}
		}		
	}	
	
	private void populatePositionChildDepartment(SimpleDataObject position, Map<String, String> map, String deptHeirarchyLevel, String deptIdKey, String childDeptIdKey) {
		if(!Utils.isBlankOrNull(position.getString(deptIdKey)) && !Utils.isBlankOrNull(position.getString(childDeptIdKey))) {
			MastersManager mastersManager = new MastersManager();						
			String deptName = mastersManager.getChildDeptName(position.getString(childDeptIdKey), position.getString(deptIdKey));
			if(!Utils.isBlankOrNull(deptName)) {
				map.put(deptHeirarchyLevel, deptName);
			}			
		}		
	}		
	
	private void populateNotifyUserIds(SimpleDataObject position, Map<String, String> map) {
		String notifyUsers = map.get(PositionParserConstants.NOTIFY_USERS);
		StringBuffer unparsedVals = new StringBuffer();
		if(!Utils.isBlankOrNull(notifyUsers)) {
			String[] users = (notifyUsers).split(SEPARATOR_COMMA);
			if(users != null && users.length > 0) {
				UserManager userManager = new UserManager();
				StringBuffer userIds = new StringBuffer();
				for(int i = 0; i < users.length; i++) {
					com.talentPool.positions.dataobject.UserData data = userManager.getUserId(users[i].trim());
					if(data != null) {
						append(userIds, ""+data.getUserId(), SEPARATOR_COMMA);
					} else {
						append(unparsedVals, users[i].trim(), SEPARATOR_COMMA_WITH_SPACE);
					}
				}
				position.setAttribute("notifyUserIds", userIds.toString());
			}
		}
		if(unparsedVals.length() > 0) {
			populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.NOTIFY_USERS, unparsedVals.toString(), "");
		}
	}
	
	private void populateNotifyUsers(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("notifyUserIds"))) {
			String[] userIds = position.getString("notifyUserIds").split(SEPARATOR_COMMA);
			if(userIds != null && userIds.length > 0) {
				LoginManager loginManager = new LoginManager();
				StringBuffer users = new StringBuffer();
				for(int i = 0; i < userIds.length; i++) {
					LoginData data = loginManager.getUser(userIds[i]);
					if(data != null) {
						append(users, data.getName(), SEPARATOR_COMMA);
					}	
				}
				map.put(PositionParserConstants.NOTIFY_USERS, users.toString());
			}
		}
	}
	
	private void populatePositionNextApproverId(SimpleDataObject position, Map<String, String> map) {
		String nextApprover = map.get(PositionParserConstants.NEXT_APPROVER);
		if(!Utils.isBlankOrNull(nextApprover)) {
			UserManager userManager = new UserManager();						
			com.talentPool.positions.dataobject.UserData data = userManager.getUserId(nextApprover);
			if(data != null) {
				position.setAttribute("nextUserId", data.getUserId());
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.NEXT_APPROVER, nextApprover, "");
			}
		}
	}
	
	private void populatePositionNextApprover(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("nextUserId"))) {
			LoginManager loginManager = new LoginManager();
			LoginData data = loginManager.getUser(position.getString("nextUserId"));
			if(data != null) {
				map.put(PositionParserConstants.NEXT_APPROVER, data.getName());
			}			
		}
	}
	
	private void populatePositionApprovalDecisionId(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull((String)map.get(PositionParserConstants.KEEP_ON_HOLD)) 
				&& PositionParserConstants.KEEP_POSITION_ON_HOLD.equalsIgnoreCase((String)map.get(PositionParserConstants.KEEP_ON_HOLD))) {
			position.setAttribute("approvalDecision", RequisitionConstants.FEEDBACK_ACTION_HOLD);
		} else {
			position.setAttribute("approvalDecision", "");
		}
	}
	
	private void populatePositionApprovalDecision(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("approvalDecision"))) {
			if(RequisitionConstants.FEEDBACK_ACTION_HOLD.equals(position.getString("approvalDecision"))) {
				map.put(PositionParserConstants.KEEP_ON_HOLD, PositionParserConstants.KEEP_POSITION_ON_HOLD);
			} else {
				map.put(PositionParserConstants.KEEP_ON_HOLD, "");
			}				
		}
	}
	
	private void populateRequisitionApprovalTemplateId(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull((String)map.get(PositionParserConstants.REQUISITION_APPROVAL_TEMPLATE))) {
			RequisitionManager requisitionManager = new RequisitionManager();
			String requisitionApprovalTemplateId = requisitionManager.getRequisitionApprovalTemplateId((String)map.get(PositionParserConstants.REQUISITION_APPROVAL_TEMPLATE));
			if(!Utils.isBlankOrNull(requisitionApprovalTemplateId)) {
				position.setAttribute("requisitionApprovalTemplateId", requisitionApprovalTemplateId);
			}
		}
	}
	
	private void populateRequisitionApprovalTemplate(SimpleDataObject position, Map<String, String> map) {
		if(!Utils.isBlankOrNull(position.getString("requisitionApprovalTemplateId"))) {
			RequisitionManager requisitionManager = new RequisitionManager();
			String requisitionApprovalTemplate = requisitionManager.getRequisitionApprovalTemplateName(position.getString("requisitionApprovalTemplateId"));
			if(!Utils.isBlankOrNull(requisitionApprovalTemplate)) {
				map.put(PositionParserConstants.REQUISITION_APPROVAL_TEMPLATE, requisitionApprovalTemplate);
			}
		}
	}
	
	private void populateCustomFieldsToPositionDef(SimpleDataObject position, Map<String, String> map) {
		List<CustomFieldData> customFields = (List<CustomFieldData>) position.getAttribute("customFields");		 
		if(customFields != null && customFields.size() > 0) {
			for(int i = 0; i < customFields.size(); i++) {
				CustomFieldData customFieldData = customFields.get(i);
				String[] fieldValues = customFieldData.getFieldValues();
				if(fieldValues != null && fieldValues.length > 0) {
					StringBuffer val = new StringBuffer();
					for(int j = 0; j < fieldValues.length; j++) {
						append(val, fieldValues[j].trim(), SEPARATOR_COMMA_WITH_SPACE);
					}
					map.put(customFieldData.getFieldDisplayName(), val.toString());
				}
			}
		}
	}
	
	private void populateCustomFieldsToPositionBO(SimpleDataObject position, Map<String, String> map) {
		List<CustomFieldData> customFields = (List<CustomFieldData>) position.getAttribute("customFields");		 
		if(customFields != null && customFields.size() > 0) {			
			for(int i = 0; i < customFields.size(); i++) {
				CustomFieldData customFieldData = customFields.get(i);
				StringBuffer unparsedVals = new StringBuffer();
				int cnt = 0;
				if(!Utils.isBlankOrNull(map.get(customFieldData.getFieldDisplayName()))) {
					String[] fieldValues = map.get(customFieldData.getFieldDisplayName()).split(SEPARATOR_COMMA);
					ArrayList<String> vals = customFieldData.getValues();
					if(fieldValues != null && fieldValues.length > 0) {
						String[] _fieldValues = new String[fieldValues.length];
						for(int j = 0; j < fieldValues.length; j++) {
							String fieldValue = fieldValues[j].trim();
							_fieldValues[j] = fieldValue;
							if(vals.size() > 0 && !vals.contains(fieldValue)) {
								cnt++;
								append(unparsedVals, fieldValue, SEPARATOR_COMMA_WITH_SPACE);
							}							
						}
						if(cnt == fieldValues.length) {
							String defaultValue = customFieldData.getFieldDefaultValue();
							if(!Utils.isBlankOrNull(defaultValue)) {
								String[] defaultVals = defaultValue.split(SEPARATOR_COMMA);
								customFieldData.setFieldValues(defaultVals);
								cnt = -1;
							}
						} else {
							customFieldData.setFieldValues(_fieldValues);
						}						
					}	
				}	
				if(unparsedVals.length() > 0) {
					String defaultVal = "";
					if(cnt == -1) {						
						defaultVal = customFieldData.getFieldDefaultValue();
					}
					populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), customFieldData.getFieldDisplayName(), unparsedVals.toString(), defaultVal);					
				}
			}
		}
	}
	
	private DraftData getDraftData(DocumentData documentData) {
		DraftData draftData = new DraftData();
		draftData.setRelativeFilePath(documentData.getRelativeFilePath());
		draftData.setOriginalFileName(documentData.getOriginalFileName());
		return draftData;
	}
	
	public SimpleDataObject parsePosition(String relativeFilePath) throws Exception {
		FileHandler fileHandler = new FileHandler();
		String absolutePath = Utils.concatFilePath(DocumentConstants.documentsPath, relativeFilePath);
		String content = fileHandler.getTextFileContent(absolutePath, null);
		
		List<CustomFieldData> customFields = null;
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_POSITION)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_POSITION, CustomFieldConstants.INPUT_ALLOWED, true);			
		}
		
		PositionParser parser = new PositionParser();	
		Map<String, String> map = parser.parsePosition(content, customFields);
		SimpleDataObject position = populatePositionBO(map);
		if(map.containsKey(PositionParserConstants.PARSING_ERRORS)) {
			String errors = map.get(PositionParserConstants.PARSING_ERRORS);
			position.setAttribute(PositionParserConstants.PARSING_ERRORS, errors);
		}
		return position;
		
	}
	
	public DraftData getLastUserDraftHavingSystemGeneratedName(String userId, DBTransaction tran) throws SQLException, NoResultFoundException {
		DraftData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionDraftManager_GetLastUserDraftHavingSystemGeneratedName", tran);			
			dq.setString(1, userId);
			dq.setString(2, PositionDraftConstants.POSITION_DRAFT_STATUS_PRIVATE);
			dq.setString(3, TPLabels.getLabel("common.position_draft"));
			dq.setString(4, TPLabels.getLabel("common.position_draft"));
			data = (DraftData) dq.getSingleObjectResult();
		} finally {
			if(dq != null) {
				dq.closeOpenCursors();
			}
		}
		return data;
	}
	
	public String getUserDraftIdForName(String fileName, String userId, DBTransaction tran) throws SQLException, NoResultFoundException {
		String draftId = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionDraftManager_GetUserDraftIdForName", tran);
			dq.setString(1, fileName);
			dq.setString(2, userId);
			dq.setString(3, PositionDraftConstants.POSITION_DRAFT_STATUS_PRIVATE);
			draftId = dq.getIdResult();
		} finally {
			if(dq != null) {
				dq.closeOpenCursors();
			}
		}
		return draftId;
	}
	
	public DraftData getOldestUserDraft(String userId, DBTransaction tran) throws SQLException, NoResultFoundException {
		DraftData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionDraftManager_GetOldestUserDraft", tran);			
			dq.setString(1, userId);
			dq.setString(2, PositionDraftConstants.POSITION_DRAFT_STATUS_PRIVATE);
			dq.setString(3, TPLabels.getLabel("common.position_draft"));
			dq.setString(4, TPLabels.getLabel("common.position_draft"));
			data = (DraftData) dq.getSingleObjectResult();
		} finally {
			if(dq != null) {
				dq.closeOpenCursors();
			}
		}
		return data;
	}
	
	private void populateParsingWarnings(List<List<String>> warnings, String unparsedAttribute, String unparsedVal, String defaultVal) {
		List<String> warning = new ArrayList<String>();
		warning.add(unparsedAttribute);
		warning.add(unparsedVal);
		warning.add(defaultVal);
		warnings.add(warning);
	}
	
	public void checkDraftNameExists(ActionErrors errors,String draftName){
		SimpleDataObject sdo = getDraftDataByName(draftName);
		if(sdo!=null){
			errors.add("position.draft.error.draft_name_exists", new ActionError("position.draft.error.draft_name_exists"));
		}
	}
	
	private void append(StringBuffer string1, String string2, String separator) {
		if(string1.length() > 0) {
			string1.append(separator);
		}
		string1.append(string2);
	}
	
	private void populatePositionBU(SimpleDataObject position, Map<String, String> map) throws SQLException {
		if(!Utils.isBlankOrNull(position.getString("buId"))) {			
			MastersManager masterManager = new MastersManager();
			ArrayList<SimpleDataObject> buDataList = masterManager.getBuListByIds(position.getString("buId"));
			if(buDataList != null && buDataList.size()>0) {
				String buName = "";
				for (int i = 0; i < buDataList.size(); i++) {
					if(Utils.isBlankOrNull(buName))
						buName=buDataList.get(i).getString("buName");
					else {
						buName+=","+buDataList.get(i).getString("buName");
					}
				}
				map.put(PositionParserConstants.BUSINESS_UNIT, buName);
			}			
		}
	}
	
	private void populatePositionBUId(SimpleDataObject position, Map<String, String> map) throws SQLException {
		String buName = (String) map.get(PositionParserConstants.BUSINESS_UNIT);
		if(!Utils.isBlankOrNull(buName)) {
			MastersManager mastersManager = new MastersManager();
			position.setAttribute("buName", buName);			
			ArrayList<SimpleDataObject> buData = mastersManager.getBuListByName(buName);
			if(buData != null && buData.size()>0) {
				String buIds = "";
				for (int i = 0; i < buData.size(); i++) {
					if(Utils.isBlankOrNull(buIds))
						buIds =buData.get(i).getString("buId");
					else
						buIds +=","+buData.get(i).getString("buId");
				}
				position.setAttribute("buId", buIds);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.BUSINESS_UNIT, buName, "");
			}			
		}
	}
	
	private void populatePositionCostCenter(SimpleDataObject position, Map<String, String> map) throws SQLException {
		if(!Utils.isBlankOrNull(position.getString("costCenterId"))) {			
			MastersManager masterManager = new MastersManager();
			ArrayList<SimpleDataObject> costCenterDataList = masterManager.getCostCenterListByIds(position.getString("costCenterId"));
			if(costCenterDataList != null && costCenterDataList.size()>0) {
				String costCenterName = "";
				for (int i = 0; i < costCenterDataList.size(); i++) {
					if(Utils.isBlankOrNull(costCenterName))
						costCenterName=costCenterDataList.get(i).getString("costCenterName");
					else {
						costCenterName+=","+costCenterDataList.get(i).getString("costCenterName");
					}
				}
				map.put(PositionParserConstants.COST_CENTER, costCenterName);
			}			
		}
	}
	
	private void populatePositionCostCenterId(SimpleDataObject position, Map<String, String> map) throws SQLException {
		String costCenterName = (String) map.get(PositionParserConstants.COST_CENTER);
		if(!Utils.isBlankOrNull(costCenterName)) {
			MastersManager mastersManager = new MastersManager();
			position.setAttribute("costCenterName", costCenterName);			
			ArrayList<SimpleDataObject> costCenterData = mastersManager.getCostCenterListByName(costCenterName);
			if(costCenterData != null && costCenterData.size()>0) {
				String costCenterIds = "";
				for (int i = 0; i < costCenterData.size(); i++) {
					if(Utils.isBlankOrNull(costCenterIds))
						costCenterIds =costCenterData.get(i).getString("costCenterId");
					else
						costCenterIds +=","+costCenterData.get(i).getString("costCenterId");
				}
				position.setAttribute("costCenterId", costCenterIds);
			} else {
				populateParsingWarnings((List<List<String>>) position.getAttribute("warnings"), PositionParserConstants.COST_CENTER, costCenterName, "");
			}			
		}
	}
}
