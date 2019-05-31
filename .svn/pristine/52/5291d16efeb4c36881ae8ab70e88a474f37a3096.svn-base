package com.talentPool.masters.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.utils.StepLevelStaticUtils;
import com.talentPool.masters.utils.StepStaticUtils;
import com.talentPool.positions.manager.PositionManager;


public class StepManager {

	/**
	 * Gets All steps from Step master.
	 * <br> <li>If <code>hideDisabled</code> is true the disabled steps are not fetched.</li>
	 * <li>If <code>systemDefined</code> is true then fetches only systemDefined steps from master.</li>
	 * <li>If <code>systemDefined</code> is false then fetches only userDefined steps from master.</li>
	 * <li>If <code>systemDefined</code> is null or any other string then fetches systemDefined & userDefined steps from master.</li>
	 * @param hideDisabled
	 * @param systemDefined
	 * @return
	 * @throws SQLException
	 */
	public List<MasterStepData> getAllSteps(String hideDisabled, String systemDefined) throws SQLException {
		List<MasterStepData> steps = new ArrayList<MasterStepData>();
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = {""};
			if(CommonConstants.TRUE.equals(hideDisabled)) {
				dynParams[0]+= " AND step_disabled = " + StepConstants.FALSE;
			}			
			
			if(CommonConstants.TRUE.equals(systemDefined)){
				dynParams[0]+= " AND system_step = "+StepConstants.TRUE;
			}else if(CommonConstants.FALSE.equals(systemDefined)){
				dynParams[0]+= " AND system_step = "+StepConstants.FALSE;
			}else {
				dynParams[0]+= "";
			}
			
			dq = new DBPreparedQuery("dMastersManager_GetAllSteps", dynParams);			
			dq.setString(1, StepConstants.FALSE);
			steps = (List<MasterStepData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return steps;
	}
	
	/**
	 * Fetches all steps from Step Master.
	 * <br> <li>If <code>hideDisabled</code> is true the disabled steps are not fetched.</li>
	 * @param hideDisabled
	 * @return
	 * @throws SQLException
	 */
	public List<MasterStepData> getAllSteps(String hideDisabled) throws SQLException {
		return getAllSteps(hideDisabled, null);
	}
	
	/**
	 * Fetches all steps from Step Master.
	 * @param hideDisabled
	 * @return
	 * @throws SQLException
	 */
	public List<MasterStepData> getAllSteps() throws SQLException {
		return getAllSteps(CommonConstants.FALSE, null);
	}
	
	/**
	 * Fetches all Active and user defined steps from Step Master 
	 * @return
	 * @throws SQLException
	 */
	public List<MasterStepData> getAllActiveUserDefinedSteps() throws SQLException {
		return getAllSteps(StepConstants.TRUE, CommonConstants.FALSE);
	}
	
	public List<MasterStepData> getAllStages() throws SQLException {
		List<MasterStepData> steps = new ArrayList<MasterStepData>();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_GetAllStages");
			steps = (ArrayList<MasterStepData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return steps;
	}
	
	public List<MasterStepData> getStepsInStage(String stepLevel, String systemStep, String stepDisabled) 
			throws SQLException {
		ArrayList<MasterStepData> steps = new ArrayList<MasterStepData>();
		DBPreparedQuery dq = null;
		String[] dynParams = {""};
		try {
			if(systemStep != null){
				dynParams[0] += " AND system_step = " + systemStep;
			} else {
				dynParams[0] += "";
			}			
			
			if(stepDisabled != null){
				dynParams[0] += " AND step_disabled = " + stepDisabled;
			} else {
				dynParams[0] += "";
			}			
			
			dq = new DBPreparedQuery("dMastersManager_GetStepsInAStage", dynParams);
			dq.setString(1, stepLevel);
			dq.setString(2, StepConstants.FALSE);
			steps = (ArrayList<MasterStepData>) dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return steps;
	}
	
	public String getXMLForSteps(List<MasterStepData> steps) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(steps != null && steps.size() > 0) {
				for (int i = 0; steps != null && i < steps.size(); i++) {
					MasterStepData data = steps.get(i);
					String stepId = data.getStepId();
					String stepName = data.getStepName();
					String systemStep = data.getSystemStep();
					String stepRank = data.getStepRank();
					String stepLevel = data.getStepLevel();
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", stepId);
					wr.startElement("", "row", "", at);
					
					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "Col_I_Comment");
					wr.startElement("", "userdata", "", at);
					wr.characters("Delete");
					wr.endElement("userdata");

					wr.startElement("cell");
					if(systemStep.equals(StepConstants.SYSTEM_STEP)){
						wr.characters("");
					}else{
						wr.characters("<a href=\"#\" onclick=\"deleteRecord("+ stepId + ");\" title=\"Delete\"><img src=\"images/ico_delete.gif\" border=0></a>");
					}					
					wr.endElement("cell");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "stepName");
					wr.startElement("", "userdata", "", at);
					wr.characters(stepName);
					wr.endElement("userdata");

					wr.startElement("cell");
					if(systemStep.equals(StepConstants.SYSTEM_STEP)){
						wr.characters(wr.doubleEscape(stepName));
					} else {
						wr.characters("<a href=\"#\" onclick=\"editRecord("+ stepId + ");\" title="+wr.doubleEscape(stepName)+">"+wr.doubleEscape(stepName)+"</a>");
					}
					wr.endElement("cell");
									
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getStepDesc()));
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(data.getStage()));
					wr.endElement("cell");
					
					wr.startElement("cell");
					if(data.getStepSchedulable().equals(StepConstants.FALSE)){
						wr.characters("");
					}else{
						wr.characters("<img src=\"images/check.gif\" border=0>");
					}					
					wr.endElement("cell");
					
					wr.startElement("cell");
					if(data.getStepDisabled().equals(StepConstants.FALSE)){
						wr.characters("");
					}else{
						wr.characters("<img src=\"images/check.gif\" border=0>");
					}					
					wr.endElement("cell");
					
					/*wr.startElement("cell");
					if(systemStep.equals(StepConstants.SYSTEM_STEP)){
						wr.characters("");
					}else{
						wr.characters("<a href=\"#\" onclick=\"moveUp("+ stepId+","+stepLevel+","+stepRank+","+systemStep + ");\" title=\""+TPLabels.getLabel("common.move_up")+"\"><img src=\"images/btn_uparrow.gif\"  border=0></a>");
					}
					wr.endElement("cell");
					
					wr.startElement("cell");
					if(systemStep.equals(StepConstants.SYSTEM_STEP)){
						wr.characters("");
					}else{
						wr.characters("<a href=\"#\" onclick=\"moveDown("+ stepId +","+stepLevel+","+stepRank +","+systemStep + ");\" title=\""+TPLabels.getLabel("common.move_down")+"\"><img src=\"images/btn_dwnarrow.gif\" border=0></a>");
					}
					wr.endElement("cell");*/
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(stepRank));
					wr.endElement("cell");
					
					wr.endElement("row");
				}
			}			
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return sWr.getBuffer().toString();
	}

	public String getXMLForStages(List<MasterStepData> steps) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if(steps != null && steps.size() > 0) {
				for (int i = 0; steps != null && i < steps.size(); i++) {
					MasterStepData data = steps.get(i);
					String stepLevel = data.getStepLevel();
					String stageName = data.getStage();
					
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", stepLevel);
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "stage");
					wr.startElement("", "userdata", "", at);
					wr.characters(stageName);
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters(wr.doubleEscape(stepLevel));
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters("<a href=\"#\" onclick=\"editRecord("+ stepLevel + ");\" title="+wr.doubleEscape(stageName)+">"+wr.doubleEscape(stageName)+"</a>");
					wr.endElement("cell");
									
					wr.endElement("row");
				}
			}			
			wr.endElement("rows");
			wr.endDocument();
		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return sWr.getBuffer().toString();
	}

	public void deleteStep(String stepId, String userId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_DeleteStep");			
			dq.setString(1, StepConstants.TRUE);
			dq.setString(2, userId);
			dq.setString(3, stepId);
			dq.execute();
			StepStaticUtils.reloadStepsMap();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public void addStep(String stepId, String stepName, String stepDesc, String stepLevel, String prevStepId,
			String stepSchedulable, String stepDeleted, String stepActive, String userId) throws SQLException, MasterExistException {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;		
		try {
			if (checkIfStepExists(stepName, stepId)) {
				throw new MasterExistException();
			}
			
			tran = new DBTransaction();
			
			String[] dynParams = new String[1];
			dynParams[0] = "";
			if(Integer.parseInt(prevStepId) > 0)
				dynParams[0] = " AND step_id = " + prevStepId;
			
			dq = new DBPreparedQuery("dMastersManager_GetRankForStep", dynParams, tran);
			dq.setString(1, stepLevel);
			dq.setString(2, stepLevel);
			String stepRank = dq.getIdResult();
			
			// 1. If insert-after-step is selected, 
			// 		the rank of new step will be +1 the rank of selected insert-after-step
			// 2. If insert-after-step is not selected, new step will have the first rank within the stage,
			//		except for shortlist stage (where shortlist is always the first step)
			if(Integer.parseInt(prevStepId) > 0 || stepLevel.equals(StepConstants.STEP_STAGE_SHORTLIST))
				stepRank  = "" + (Integer.parseInt(stepRank) + 1);
			
			dq = new DBPreparedQuery("dMastersManager_IncrementRankForOtherSteps",tran);
			dq.setString(1, stepRank);
			dq.setString(2, StepConstants.FALSE);
			dq.execute();
			
			dq = new DBPreparedQuery("dMastersManager_AddStep",tran);
			dq.setString(1, stepId);			
			dq.setString(2, stepName);
			dq.setString(3, stepDesc);
			dq.setString(4, stepRank);
			dq.setString(5, stepLevel);
			dq.setString(6, stepSchedulable);
			dq.setId(7, userId);
			dq.execute();

			tran.commit();
			StepStaticUtils.reloadStepsMap();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		} catch (MasterExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}
	
	public boolean checkIfStepExists(String stepName, String stepId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dMastersManager_CheckDuplicateSteps");
			dq.setString(1, stepName);
			dq.setString(2, StepConstants.FALSE);
			dq.setString(3, stepId);
			int count = dq.getIntResult();
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Checking duplicate step", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return false;
	}
	
	public void updateStep(String stepId, String stepName, String stepDesc, String stepSchedulable, 
			String stepDislabled, String userId) throws SQLException, MasterExistException {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			MasterStepData data = null;
			
			if (checkIfStepExists(stepName, stepId)) {
				throw new MasterExistException();
			} else {
				data = getMasterStepData(stepId);
			}
			
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dMastersManager_UpdateStep", tran);
			dq.setString(1, stepName);
			dq.setString(2, stepDesc);
			dq.setString(3, stepSchedulable);
			dq.setString(4, stepDislabled);
			dq.setId(5, userId);
			dq.setString(6, stepId);			
			dq.execute();
			
			// If change in step name, update all corresponding steps in tp_position_steps
			if(!Utils.isBlankOrNull(stepName) && (data != null) 
					&& !Utils.isBlankOrNull(data.getStepName()) 
					&& !stepName.equalsIgnoreCase(data.getStepName())) {
				dq = new DBPreparedQuery("dMastersManager_UpdatePositionStepTitles", tran);
				dq.setString(1, stepName);
				dq.setString(2, stepDesc);
				dq.setString(3, stepId);
				dq.execute();
			}
			
			// If change in step disabled, delete all corresponding steps for templates
			if(!Utils.isBlankOrNull(stepDislabled) && (data != null) 
					&& !Utils.isBlankOrNull(data.getStepDisabled()) 
					&& !stepDislabled.equalsIgnoreCase(data.getStepDisabled()) 
					&& stepDislabled.equals(StepConstants.TRUE)) {
				PositionManager positionManager = new PositionManager();
				positionManager.updateTemplatesForDisabledStep(stepId, tran);								
			}
			StepStaticUtils.reloadStepsMap();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} catch (MasterExistException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}

	/**
	 * Update stepLevelName for given stepLevelId
	 * 
	 * Change History:
	 * Changed on: Feb 21, 2012
	 * Changed by: praveenK
	 * Changes: Added reload stepLevelMap statement after update of stepLevel Name
	 * @param stepLevel
	 * @param stepLevelName
	 * @param userId
	 * @throws SQLException
	 */
	public void updateStage(String stepLevel, String stepLevelName, String userId) throws SQLException {
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_UpdateStage");
			dq.setString(1, stepLevelName);
			dq.setString(2, userId);
			dq.setString(3, stepLevel);			
			dq.execute();
			StepLevelStaticUtils.reloadStepLevelMap();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}		
	}

	public MasterStepData getMasterStepData(String stepId) throws SQLException {
		MasterStepData data = null;
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_GetMasterStepData");
			dq.setString(1, stepId);			
			data = (MasterStepData)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
	
	public MasterStepData getMasterStepDataFromPositionStep(String stepId) throws SQLException {
		MasterStepData data = null;
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_GetMasterStepDataFromPositionStep");
			dq.setString(1, stepId);			
			data = (MasterStepData)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
	

	public MasterStepData getStageData(String stepLevel) throws SQLException {
		MasterStepData data = null;
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_GetStageData");
			dq.setString(1, stepLevel);			
			data = (MasterStepData)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public boolean isStepUsedInPosition(String stepId) throws SQLException {
		
		// 1. ( table - tp_applicant_selection_process ) position_step_id used in applicant interactions 
		// 2. ( table - tp_position_steps ) step_id from tp_step_master for each position_step_id 
		// 3. ( table - tp_cr_event_log ) step_id_to is from tp_position_steps, 
		//		which is migrated id for position_step_id from tp_applicant_selection_process
		// 		-	this will give if any applicant has been processed against that step for any position
		
		int positionCount = 0;
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_GetPositionsUsingStep");
			dq.setString(1, stepId);	
			positionCount = dq.getIntResult();
			TPLogger.getLogger().info("No. of positions using step_id " + stepId + " are : " + positionCount);
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} catch (NoResultFoundException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);			
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return (positionCount > 0);
	}

	public MasterStepData getStepIdTo(String stepRank, String order) throws SQLException {
		MasterStepData data = null;
		DBPreparedQuery dq = null;
		String[] dynParams = new String[2];
		try {
			if(order.equals("desc")){
				dynParams[0]= " < ";
				dynParams[1]= " desc ";
			}else{
				dynParams[0]= " > ";
				dynParams[1]= " asc ";
			}
			dq = new DBPreparedQuery("dMastersManager_GetStepIdTo", dynParams);
			dq.setString(1, stepRank);	
			data = (MasterStepData)dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	public void switchSteps(String stepId, String stage, String stepRank, String stepIdTo, String stepRankTo) throws SQLException {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;		
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dMastersManager_UpdateStepRank",tran);
			dq.setString(1, stepRank);
			dq.setString(2, stepIdTo);
			dq.execute();
			
			dq = new DBPreparedQuery("dMastersManager_UpdateStepRank",tran);
			dq.setString(1, stepRankTo);
			dq.setString(2, stepId);
			dq.execute();
			
			tran.commit();
			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			tran.rollback();
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}		
	}

	public String getStepIdToCreate() throws SQLException {
		String stepId = null;
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_GetStepIdToCreate");
			String lastStepId = dq.getIdResult();
			stepId = "" + (Integer.parseInt(lastStepId) + 1);
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return stepId;
	}
	
	public String getStepNameForStepId(String stepId) throws SQLException {
		String stepName = null;
		DBPreparedQuery dq = null;		
		try {
			dq = new DBPreparedQuery("dMastersManager_GetStepNameForStepId");
			dq.setString(1, stepId);
			stepName = dq.getStringResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		}finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return stepName;
	}
}
