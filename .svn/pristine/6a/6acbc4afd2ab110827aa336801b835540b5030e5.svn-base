/**
 * 
 */
package com.talentPool.requisition.manager;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.positions.PositionConstants;
import com.talentPool.requisition.constants.RequisitionConstants;
import com.talentPool.requisition.dataobject.RequisitionApprovalStepData;
import com.talentPool.user.dataobject.LoginData;

/**
 * @author shivprasad
 * 
 */
public class RequisitionManager {

	/**
	 * @return list of all requisition approval steps with users for each step
	 */
	public ArrayList<RequisitionApprovalStepData> getRequisitionApprovalSteps(String requisitionApprovalTemplateId) {
		DBPreparedQuery dq = null;
		ArrayList<RequisitionApprovalStepData> results = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetAllSteps");			
			dq.setInt(1, RequisitionConstants.STATUS_ACTIVE);
			dq.setString(2, requisitionApprovalTemplateId);
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;
	}

	/**
	 * @param requisitionApprovalStepId
	 * @return RequisitionApprovalStepData with list of users for the step
	 */
	public RequisitionApprovalStepData getRequisitionApprovalStepData(String requisitionApprovalStepId, String userId) {
		DBPreparedQuery dq = null;
		RequisitionApprovalStepData requisitionApprovalStepData = null;
		String[] dynParams = new String[1];
		dynParams[0] = "";
		ArrayList<String> dynamicContent = new ArrayList<String>();
		ArrayList<RequisitionApprovalStepData> results = null;
		try {
			if (!Utils.isBlankOrNull(userId)) {				
				dynParams[0] += " AND tu.user_id = ? ";
				dynamicContent.add(userId);
			}						
			dq = new DBPreparedQuery("dRequisitionManager_GetRequisitionStepData", dynParams);
			dq.setId(1, requisitionApprovalStepId);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			results = dq.getResult();
			requisitionApprovalStepData = constructRequisitionApprovalData(results);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error getting requisition step data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionApprovalStepData;
	}

	private RequisitionApprovalStepData constructRequisitionApprovalData(ArrayList<RequisitionApprovalStepData> results) {
		RequisitionApprovalStepData requisitionApprovalStepData = null;
		if (results != null & results.size() > 0) {
			requisitionApprovalStepData = new RequisitionApprovalStepData();
			for (int i = 0; i < results.size(); i++) {
				RequisitionApprovalStepData data = results.get(i);
				if (i == 0) {
					requisitionApprovalStepData = data;
					requisitionApprovalStepData.setUsers(new ArrayList<LoginData>());
				}
				if (data.getString("userId") != null) {
					LoginData user = new LoginData();
					user.setUserId(data.getString("userId"));
					user.setName(data.getString("name"));					
					user.setFirstName(data.getString("firstName"));
					user.setLastName(data.getString("lastName"));
					user.setRoleTitle(data.getString("roleTitle"));
					user.setEmployeeId(data.getString("employeeId"));
					user.setLocation(data.getString("location"));
					user.setDepartment(data.getString("department"));
					user.setSubDepartment(data.getString("subDepartment"));
					user.setSubSubDepartment(data.getString("subSubDepartment"));
					requisitionApprovalStepData.getUsers().add(user);
				}
			}
		}
		return requisitionApprovalStepData;
	}

	/**
	 * 
	 * @param userId
	 * @return true if userId is envolved in any step of requisition approval steps
	 */
	public boolean isRequisitionAllowed(String userId) {
		boolean requisitionAllowed = false;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetStepCountForUser");
			dq.setId(1, userId);
			dq.setInt(2, RequisitionConstants.STATUS_ACTIVE);
			int noOfsteps = dq.getIntResult();
			if (noOfsteps > 0) {
				requisitionAllowed = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting step id for rank", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionAllowed;
	}
	
	/**
	 * 
	 * @param userId
	 * @return true if userId is envolved in any step of requisition approval steps
	 */
	public boolean isRequisitionAllowedForUser(String userId) {
		boolean requisitionAllowed = false;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetStepCountRequisitionForUser");
			dq.setId(1, userId);
			dq.setInt(2, RequisitionConstants.STEP_RANK);
			int noOfsteps = dq.getIntResult();
			if (noOfsteps > 0) {
				requisitionAllowed = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting step id for rank", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionAllowed;
	}

	/**
	 * return step data of first step in process for the step in which user exists
	 * 
	 * @param userId
	 * @return
	 */

	public RequisitionApprovalStepData getFirstRequisitionApprovalStepDataForUser(String userId, String requisitionApprovalTemplateId) {
		DBPreparedQuery dq = null;
		RequisitionApprovalStepData requisitionApprovalStepData = null;
		try {
			ArrayList<RequisitionApprovalStepData> results = null;
			dq = new DBPreparedQuery("dRequisitionManager_GetFirstRequisitionStepData");
			dq.setInt(1, RequisitionConstants.STATUS_ACTIVE);
			dq.setString(2, requisitionApprovalTemplateId);
			dq.setInt(3, RequisitionConstants.STATUS_ACTIVE);
			dq.setString(4, requisitionApprovalTemplateId);
			dq.setId(5, userId);
			results = dq.getResult();
			requisitionApprovalStepData = constructRequisitionApprovalData(results);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error getting requisition step data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionApprovalStepData;
	}

	/**
	 * @param stepId
	 * @param stepRank
	 * @return RequisitionApprovalStepData for next active step 
	 */
	public RequisitionApprovalStepData getNextActiveRequisitionStepData(String stepId, int stepRank, String requisitionApprovalTemplateId) {
		DBPreparedQuery dq = null;
		RequisitionApprovalStepData requisitionApprovalStepData = null;
		try {
			ArrayList<RequisitionApprovalStepData> results = null;
			dq = new DBPreparedQuery("dRequisitionManager_GetNextActiveRequisitionStepData");
			dq.setId(1, stepId);
			dq.setInt(2, stepRank);
			dq.setInt(3, RequisitionConstants.STATUS_ACTIVE);
			dq.setString(4, requisitionApprovalTemplateId);
			results = dq.getResult();
			requisitionApprovalStepData = constructRequisitionApprovalData(results);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error getting requisition step data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionApprovalStepData;
	}

	/**
	 * @param stepId
	 * @return list of requisitions in process for given step
	 */
	public ArrayList<SimpleDataObject> getRequisitionsInProcessForStep(String stepId) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> result = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetRequesitionsInProcessForStep");
			dq.setId(1, stepId);
			dq.setId(2, stepId);
			dq.setString(3, PositionConstants.POSITION_STATUS_INPROCESS);
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting step id for rank", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	/**
	 * @param stepId
	 * @return list of users who are active and their decision is pending for requisition
	 */
	public ArrayList<SimpleDataObject> getActiveUsersForStep(String stepId) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> result = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetActiveUsersForStep");
			dq.setId(1, stepId);
			dq.setId(2, stepId);
			dq.setString(3, PositionConstants.POSITION_STATUS_INPROCESS);
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting step id for rank", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	

	
	public ArrayList<SimpleDataObject> getRequisitionApprovalTemplates() throws Exception {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> templates = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetRequisitionApprovalTemplates");
			dq.setInt(1, RequisitionConstants.STATUS_ACTIVE);
			templates = dq.getResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return templates;
	}
	
	public ArrayList<SimpleDataObject> getUserRequisitionApprovalTemplates(String userId) throws Exception {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> templates = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetUserRequisitionApprovalTemplates");
			dq.setInt(1, RequisitionConstants.STATUS_ACTIVE);			
			dq.setInt(2, RequisitionConstants.STATUS_ACTIVE);
			dq.setString(3, userId);
			templates = dq.getResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return templates;
	}
	
	public String getXMLforRequisitionApprovalTemplates(ArrayList templates) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			for (int i = 0; i < templates.size(); i++) {
				SimpleDataObject sDo = (SimpleDataObject) templates.get(i);

				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "id", "", "", sDo.getString("templateId"));
				wr.startElement("", "row", "", at);

				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "templateName");
				wr.startElement("", "userdata", "", at);
				wr.characters(sDo.getString("templateName"));
				wr.endElement("userdata");
				
				at = new AttributesImpl();
				at.addAttribute("", "name", "", "", "imgTitle");
				wr.startElement("", "userdata", "", at);
				if(RequisitionConstants.TYPE_SYSTEM_GENERATED == sDo.getInt("templateType")) {
					wr.characters("");
				} else {
					wr.characters(TPLabels.getLabel("common.delete"));
				}
				wr.endElement("userdata");
				
				wr.startElement("cell");				
				if(RequisitionConstants.TYPE_SYSTEM_GENERATED == sDo.getInt("templateType")) {
					wr.characters(wr.doubleEscape("&nbsp;"));
				} else {
					wr.characters("<img src=\"images/ico_delete.gif\" border=0>^javascript:deleteRecord(" + sDo.getString("templateId") + ");^_self");
				}				
				wr.endElement("cell");
				
				wr.startElement("cell");
				wr.characters(wr.doubleEscape(sDo.getString("templateName"))+"^javascript:viewDetails(\"" + sDo.getString("templateId") + "\");^_self");				
				wr.endElement("cell");
				
				wr.endElement("row");
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for ExcelImport", e);
		}
		return sWr.getBuffer().toString();
	}
	
	public String getRequisitionApprovalTemplateName(String requisitionApprovalTemplateId) {
		String requisitionApprovalTemplateName = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetRequisitionApprovalTemplateName");
			dq.setString(1, requisitionApprovalTemplateId);
			requisitionApprovalTemplateName = dq.getStringResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return requisitionApprovalTemplateName;
	}
	
	public void createRequisitionApprovalTemplate(String requisitionApprovalTemplateName, List<RequisitionApprovalStepData> steps) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dRequisitionManager_InsertRequisitionApprovalTemplate", tran);
			dq.setString(1, requisitionApprovalTemplateName);
			dq.execute();
			
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			int requisitionApprovalTemplateId = dq.getIntResult();
			
			if(steps != null && steps.size() > 0) {
				for(int i = 0; i < steps.size(); i++) {
					RequisitionApprovalStepData step = steps.get(i);
					step.setRequisitionApprovalTemplateId(requisitionApprovalTemplateId);
					addRequisitionApprovalStep(step, tran);									
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(tran);
			}			
		}
	}

	public void updateRequisitionApprovalTemplate(String requisitionApprovalTemplateId, String requisitionApprovalTemplateName, List<RequisitionApprovalStepData> steps) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dRequisitionManager_UpdateRequisitionApprovalTemplate", tran);
			dq.setString(1, requisitionApprovalTemplateName);
			dq.setString(2, requisitionApprovalTemplateId);
			dq.execute();
			
			int[] stepIds = new int[steps.size()];
			if(steps != null && steps.size() > 0) {				
				for(int i = 0; i < steps.size(); i++) {
					RequisitionApprovalStepData step = steps.get(i);
					step.setRequisitionApprovalTemplateId(Integer.parseInt(requisitionApprovalTemplateId));
					if(step.getRequisitionApprovalStepId() == 0) {
						addRequisitionApprovalStep(step, tran);
					} else {
						updateRequisitionApprovalStep(step, tran);
					}	
					stepIds[i] = step.getRequisitionApprovalStepId();
				}
				
				String[] dynParams = new String[1]; 
				dynParams[0] = Utils.getQmarks(stepIds.length);
				dq = new DBPreparedQuery("dRequisitionManager_DeleteRequisitionApprovalSteps", dynParams, tran);
				dq.setInt(1, RequisitionConstants.STATUS_DELETED);
				int cnt = 2;
				for(int i = 0; i < stepIds.length; i++) {
					dq.setInt(cnt++, stepIds[i]);
				}
				dq.setString(cnt++, requisitionApprovalTemplateId);
				dq.execute();
			}		
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(tran);
			}			
		}
	}

	public void addRequisitionApprovalStep(RequisitionApprovalStepData stepData, DBTransaction tran) throws Exception {		
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_AddNewStep", tran);
			dq.setInt(1, stepData.getRequisitionApprovalTemplateId());
			dq.setString(2, stepData.getRequisitionApprovalStepName());
			dq.setInt(3, stepData.getRequisitionApprovalStepRank());
			dq.setInt(4, RequisitionConstants.TYPE_USER_GENERATED);
			dq.setInt(5, RequisitionConstants.STATUS_ACTIVE);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			int requisitionApprovalStepId = dq.getIntResult();
			stepData.setRequisitionApprovalStepId(requisitionApprovalStepId);

			addStepUsers(stepData, tran);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}

		}
	}
	
	public void addStepUsers(RequisitionApprovalStepData stepData, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (!Utils.isBlankOrNull(stepData.getUserIds())) {
				String[] uIds = stepData.getUserIds().split(",");
				for (int i = 0; i < uIds.length; i++) {
					dq = new DBPreparedQuery("dRequisitionManager_AddStepUsers", tran);
					dq.setInt(1, stepData.getRequisitionApprovalStepId());
					dq.setId(2, uIds[i].trim());
					dq.execute();
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	public void updateRequisitionApprovalStep(RequisitionApprovalStepData stepData, DBTransaction tran) throws Exception {		
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_UpdateNewStep", tran);
			dq.setString(1, stepData.getRequisitionApprovalStepName());
			dq.setInt(2, stepData.getRequisitionApprovalStepRank());
			dq.setInt(3, stepData.getRequisitionApprovalStepId());
			dq.execute();

			deleteStepUsers(stepData.getRequisitionApprovalStepId(), tran);
			addStepUsers(stepData, tran);
			tran.commit();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error creating applicant", e);
			tran.rollback();
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}

		}
	}
	
	public void deleteStepUsers(int requisitionApprovalStepId, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_DeleteStepUsers", tran);
			dq.setInt(1, requisitionApprovalStepId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	public void deleteRequisitionApprovalTemplate(String requisitionApprovalTemplateId) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dRequisitionManager_DeleteRequisitionApprovalTemplate", tran);
			dq.setInt(1, RequisitionConstants.STATUS_DELETED);
			dq.setString(2, requisitionApprovalTemplateId);
			dq.execute();
			
			String[] dynParams = new String[1]; 
			dynParams[0] = Utils.getQmarks(1);
			dq = new DBPreparedQuery("dRequisitionManager_DeleteRequisitionApprovalSteps", dynParams, tran);
			dq.setInt(1, RequisitionConstants.STATUS_DELETED);
			int cnt = 2;
			dq.setInt(cnt++, 0);
			dq.setString(cnt++, requisitionApprovalTemplateId);
			dq.execute();
			
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseTransaction(tran);
			}			
		}
	}
	
	public ArrayList<SimpleDataObject> getRequisitionsInProcessForTemplate(String templateId) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> result = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetRequesitionsInProcessForTemplate");
			dq.setId(1, templateId);
			dq.setId(2, templateId);
			dq.setString(3, PositionConstants.POSITION_STATUS_INPROCESS);
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting step id for rank", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
	public String getRequisitionApprovalTemplateId(String requisitionApprovalTemplateName) {
		String requisitionApprovalTemplateId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionManager_GetRequisitionApprovalTemplateId");
			dq.setString(1, requisitionApprovalTemplateName);
			requisitionApprovalTemplateId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return requisitionApprovalTemplateId;
	}
}
