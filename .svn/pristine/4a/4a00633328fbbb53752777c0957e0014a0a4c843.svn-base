/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.manager;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.PositionHiringSummaryData;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.constants.StepMasterConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.impl.StepsMigrationWizardDao;
import com.talentPool.stepsMigration.services.impl.StepsMigrationWizardService;

/**
 * @author PraveenK
 * @since  Jul 25, 2012
 */
public class PositionHiringSummaryManager {
	
	/**
	 * From last run date to today
	 * <li>Executes Cleans up</li>
	 * <li>Calculates summarized values (ADDED, CLEARED, REJECTED)</li>
	 * <li>Fetches BACKLOG (previous interactions IN-PROCESS) and calculates for each day IN-PROCESS </li>
	 * <li>Inserts data into Position Wise Activity Summary</li>
	 * @param lastRunDate
	 * @param tran
	 * @throws SQLException
	 */
	public void updatePositionHiringSummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		updatePositionHiringSummary(lastRunDate, new Date(), tran);
	}
	
	/**
	 * From last run date to today
	 * <li>Executes Cleans up</li>
	 * <li>Calculates summarized values (ADDED, CLEARED, REJECTED)</li>
	 * <li>Fetches BACKLOG (previous interactions IN-PROCESS) and calculates for each day IN-PROCESS </li>
	 * <li>Inserts data into Position Wise Activity Summary</li>
	 * @param fromDate
	 * @param toDate
	 * @param tran
	 * @throws SQLException
	 * @see PositionHiringSummaryManager#updatePositionHiringSummary
	 */
	public void updatePositionHiringSummary(Date fromDate, Date toDate, DBTransaction tran) throws SQLException {
		List<PositionHiringSummaryData> summary 	= null;
		long startTime = System.currentTimeMillis();
		try {
			TPLogger.getLogger().info("Populating Position Hiring Summary from date: "+ fromDate + " to date: "+toDate);
			deletePositionHiringSummary(fromDate, toDate, tran); 	// delete all records where process_date_created > lastRunDateStr
			// handle migration status
			StepsMigrationWizardDao stepsMigrationWizardDao = new StepsMigrationWizardDao();
			StepsMigrationWizardService stepsMigrationWizardService = new StepsMigrationWizardService();
			stepsMigrationWizardService.setStepsMigrationWizardDao(stepsMigrationWizardDao);
			Map<String, String> migrationStatusMap = stepsMigrationWizardService.getMigrationStatus();
						
			// if both status (for open and closed) are pending, do not run scheduler and return
			if (migrationStatusMap.get("open").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)
					&& migrationStatusMap.get("closed").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)) {
				TPLogger.getLogger().error("migration not completed, can't run scheduler", new Exception());
				return;
			}
			while (fromDate.before(toDate) || DateUtils.isSameDay(fromDate, toDate)) { // runs for each day till today
				TPLogger.getLogger().trace( "Populating Position Wise Activity for the day " + fromDate.toString());
				summary = fetchPositionHiringSummary(fromDate, tran);
				populatePositionHiringSummary(summary, tran);
				fromDate = Utils.datePlusPlus(fromDate); 
			}
			TPLogger.getLogger().info("Completed Populating Position Hiring Summary from date: "+ fromDate + " to date: "+toDate +". Total time taken: "+((System.currentTimeMillis()-startTime)/100) + " secs");
		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
			throw sqle;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} 
	}
	
	/**
	 * For each summary record fetches backlog count and then calculates in-process count.
	 * @param summary
	 * @param tran
	 * @throws SQLException
	 */
	public void populatePositionHiringSummary(final List<PositionHiringSummaryData> summary, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq 				= null;
		try {
			if (!Utils.isListEmptyOrNull(summary)) {
				for (PositionHiringSummaryData esData : summary) {
					int backlog = 0;
					String backlogIds = null;
					
					SimpleDataObject sdo = getBacklogValues(esData.getPositionId(), esData.getStepId(), esData.getProcessDate(), tran);
					if(sdo!=null){
						backlog = sdo.getInt("backlog");
						backlogIds = sdo.getString("backlogIds");	
					}
					
					String inprocessIds = getInprocessIds(esData, backlogIds);
					int inprocess 		= getInprocessCount(esData, backlog);
					
					int cnt = 1;
					dq = new DBPreparedQuery("dPositionHiringSummaryManager_AddSummaryRows", tran);
					dq.setDate(cnt++, esData.getProcessDate());
					dq.setString(cnt++, esData.getPositionId());
					dq.setString(cnt++, esData.getStepId());
					dq.setString(cnt++, esData.getStepLevel());
					dq.setInt(cnt++, backlog);
					dq.setInt(cnt++, esData.getReceived());
					dq.setInt(cnt++, esData.getCleared());
					dq.setInt(cnt++, esData.getRejected());
					dq.setInt(cnt++, inprocess);
					dq.setString(cnt++, backlogIds);
					dq.setString(cnt++, esData.getReceivedIds());
					dq.setString(cnt++, esData.getClearedIds());
					dq.setString(cnt++, esData.getRejectedIds());
					dq.setString(cnt++, inprocessIds);
					dq.execute();
				}
			}
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error while updating position hiring sumary", sqle);
			throw sqle;
		}finally{
			if (dq != null) 
				dq.closeOpenCursors();
		}
	}

	/**
	 * Calculates and fetches Position wise activity summary for the given day from event log table.
	 * Note: Following method fetches you only ADDED, CLEARED and REJECTED values  
	 * @param day
	 * @param tran
	 * @return 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected List<PositionHiringSummaryData> fetchPositionHiringSummary(Date day, DBTransaction tran) throws SQLException  {
		DBPreparedQuery dq = null;
		List<PositionHiringSummaryData> summary = null;
		String dayStr= Utils.getDateConvertedToString(day, DateConstants.DB_DATE_PATTERN);
		try {
			dq = new DBPreparedQuery("dPositionHiringSummaryManager_GetSummaryRows", tran);
			int cnt = 1;
			dq.setString(cnt++, StepMasterConstants.SHORTLIST_MASTER_STEP_ID);
			// following constants injected to represent rejection state
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setString(cnt++, StepConstants.STEP_STAGE_HIRE);
			dq.setString(cnt++, StepConstants.STEP_STAGE_HIRE);
			// following constants injected to represent rejection state
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setString(cnt++, dayStr);
			
			dq.setString(cnt++, StepConstants.STEP_STAGE_HIRE);
			
			dq.setString(cnt++, dayStr);  
			summary = dq.getResult();
			TPLogger.getLogger().trace("Fetched " + (Utils.isListEmptyOrNull(summary)?" ":summary.size()) + " summary records for the day - " + day);
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error while fetching position wise activity summary data for the day  "+ day, sqle);
			throw sqle;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
		return summary;
	}
	
	/**
	 * Calculates and fetches Position wise activity summary for the given day from event log table
	 * Note: Following method fetches you only ADDED, CLEARED and REJECTED values
	 * @param day
	 * @param positionId
	 * @param tran
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected List<PositionHiringSummaryData> fetchPositionHiringSummary(String day, String positionId, DBTransaction tran) throws SQLException  {
		// TODO: implement for position specific
		return null;
	}
	
	/**
	 * Cleans up position wise activity summary data from database for the period fromDate+1 to toDate. 
	 * Usually needed when before calculating or recalculating 
	 * @param fromDate
	 * @param toDate
	 * @param tran
	 * @throws SQLException
	 */
	protected void deletePositionHiringSummary(Date fromDate, Date toDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dPositionHiringSummaryManager_DeleteAllRows", tran);
			dq.setString(1, Utils.getDateConvertedToString(fromDate, DateConstants.DB_DATE_PATTERN));
			dq.setString(2, Utils.getDateConvertedToString(toDate, DateConstants.DB_DATE_PATTERN));
			dq.execute();			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error while deleting Position wise activity summary " +
										"for the period: " + fromDate +"to"+ toDate, sqle);
			throw sqle;
		}finally{
			if(dq!=null)
				dq.closeOpenCursors();
		}
	}
	
	public SimpleDataObject getBacklogValues(String positionId, String stepId, java.sql.Date processDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		SimpleDataObject sdo = null;
		try {
			dq = new DBPreparedQuery("dPositionHiringSummaryManager_GetBacklog", tran);
			int cnt = 1;
			dq.setString(cnt++, positionId);
			dq.setString(cnt++, stepId);
			dq.setDate(cnt++, processDate);
			sdo = (SimpleDataObject)dq.getSingleObjectResult();			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error while fetching backlog values for position:  " + positionId +
										" step: " + stepId +
										" processDate: "+ processDate, sqle);
			throw sqle;
		}finally{
			if(dq!=null)
				dq.closeOpenCursors();
		}
		return sdo;
	}
	
	/**
	 * Calculates In-process count as (Backlog+Received)-(Cleared+Rejected)
	 * @param esData
	 * @param backlog
	 * @return
	 */
	private int getInprocessCount(final PositionHiringSummaryData esData, int backlog){
		return (backlog+esData.getReceived()) - (esData.getCleared()+esData.getRejected());
	}
	
	/**
	 *  Calculates In-process Ids as (Backlogids+ReceivedIds)-(ClearedIds+RejectedIds)
	 * @param esData
	 * @param backlogIds
	 * @return
	 */
	private String getInprocessIds(final PositionHiringSummaryData esData, String backlogIds){
		return getInprocessIds(backlogIds, esData.getReceivedIds(), esData.getClearedIds(), esData.getRejectedIds());
	}
	
	/**
	 * Calculates In-process Ids as (Backlogids+ReceivedIds)-(ClearedIds+RejectedIds)
	 * @param backlogIds
	 * @param recievedIds
	 * @param clearedIds
	 * @param rejectedIds
	 * @return
	 */
	private String getInprocessIds(String backlogIds, String recievedIds, String clearedIds, String rejectedIds){
		String totalIn = "";
		String totalOut = "";
		
		if(!Utils.isBlankOrNull(backlogIds) && !Utils.isBlankOrNull(recievedIds)){
			totalIn=backlogIds+DEFAULT_DELIMITER+recievedIds;
		}else if(Utils.isBlankOrNull(backlogIds) && !Utils.isBlankOrNull(recievedIds)){
			totalIn=recievedIds;
		}else if(!Utils.isBlankOrNull(backlogIds) && Utils.isBlankOrNull(recievedIds)){
			totalIn=backlogIds;
		}
		
		if(!Utils.isBlankOrNull(clearedIds) && !Utils.isBlankOrNull(rejectedIds)){
			totalOut=clearedIds+DEFAULT_DELIMITER+rejectedIds;
		}else if(Utils.isBlankOrNull(clearedIds) && !Utils.isBlankOrNull(rejectedIds)){
			totalOut=rejectedIds;
		}else if(!Utils.isBlankOrNull(clearedIds) && Utils.isBlankOrNull(rejectedIds)){
			totalOut=clearedIds;
		}
		
		return getInprocessIds(totalIn, totalOut);
	}
	
	/**
	 * Calculates In-process Ids as (Total In Ids)-(Total out Ids)
	 * @param totalIn
	 * @param totalOut
	 * @return
	 */
	private String getInprocessIds(String totalIn, String totalOut){
		Set<String> totalInSet = getSetFromCommaDelimitedString(totalIn);
		Set<String> totalOutSet = getSetFromCommaDelimitedString(totalOut);
		totalInSet.removeAll(totalOutSet);
		return StringUtils.join(totalInSet, DEFAULT_DELIMITER); 
	}
	
	private Set<String> getSetFromCommaDelimitedString(String commaDelimited){
		if(Utils.isBlankOrNull(commaDelimited)){
			return new HashSet<String>();
		}else{
			return new HashSet<String>(Arrays.asList(commaDelimited.split(DEFAULT_DELIMITER)));
		}
	}

	public static void main(String[] agrs) {
		PositionHiringSummaryManager manager = new PositionHiringSummaryManager();
		DBTransaction tran = null;
		try {
		tran = new DBTransaction();
			String lastRunDateStr = "2011-02-15 00:00:00";
			Date lastRunDate = Utils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN);
			manager.updatePositionHiringSummary(lastRunDate, tran);
			tran.commit();
		} catch (Exception e) {
			try {
				tran.rollback();

			} catch (SQLException e1) {

			}
		}
	}

}
