/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.UserHiringActivitySummaryData;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.constants.StepMasterConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.impl.StepsMigrationWizardDao;
import com.talentPool.stepsMigration.services.impl.StepsMigrationWizardService;

/**
 * @author PraveenK
 * @since  Aug 1, 2012
 */
public class UserHiringActivitySummaryManager {
	
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
	public void updateUserHiringActivitySummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		updateUserHiringActivitySummary(lastRunDate, new Date(), tran);
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
	 * @see UserHiringActivitySummaryManager#updateUserHiringActivitySummary
	 */
	public void updateUserHiringActivitySummary(Date fromDate, Date toDate, DBTransaction tran) throws SQLException {
		List<UserHiringActivitySummaryData> summary 	= null;
		long startTime = System.currentTimeMillis();
		try {
			TPLogger.getLogger().info("Populating User Hiring Summary from date: "+ fromDate + " to date: "+toDate);
			deleteUserHiringActivitySummary(fromDate, toDate, tran); 	// delete all records where process_date_created > lastRunDateStr
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
				TPLogger.getLogger().trace( "Populating User Wise Activity for the day " + fromDate.toString());
				summary = fetchUserHiringActivitySummary(fromDate, tran);
				populateUserHiringActivitySummary(summary, tran);
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
	 * Inserts each user wise summary record.
	 * @param summary
	 * @param tran
	 * @throws SQLException
	 */
	public void populateUserHiringActivitySummary(final List<UserHiringActivitySummaryData> summary, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq 				= null;
		try {
			if (!Utils.isListEmptyOrNull(summary)) {
				for (UserHiringActivitySummaryData esData : summary) {
					int cnt = 1;
					dq = new DBPreparedQuery("dUserHiringActivitySummaryManager_AddSummaryRows", tran);
					dq.setDate(cnt++, esData.getProcessDate());
					dq.setString(cnt++, esData.getPositionId());
					dq.setString(cnt++, esData.getStepId());
					dq.setString(cnt++, esData.getStepLevel());
					dq.setString(cnt++, esData.getUserId());
					dq.setInt(cnt++, esData.getReceived());
					dq.setInt(cnt++, esData.getCleared());
					dq.setInt(cnt++, esData.getRejected());
					dq.setString(cnt++, esData.getReceivedIds());
					dq.setString(cnt++, esData.getClearedIds());
					dq.setString(cnt++, esData.getRejectedIds());
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
	 * Calculates and fetches User wise activity summary for the given day from event log table.
	 * Fetches you ADDED, CLEARED and REJECTED values  
	 * @param day
	 * @param tran
	 * @return 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	protected List<UserHiringActivitySummaryData> fetchUserHiringActivitySummary(Date day, DBTransaction tran) throws SQLException  {
		DBPreparedQuery dq = null;
		List<UserHiringActivitySummaryData> summary = null;
		String dayStr= Utils.getDateConvertedToString(day, DateConstants.DB_DATE_PATTERN);
		try {
			dq = new DBPreparedQuery("dUserHiringActivitySummaryManager_GetSummaryRows", tran);
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
			TPLogger.getLogger().error("Error while fetching user wise activity summary data for the day  "+ day, sqle);
			throw sqle;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
		return summary;
	}
	
	/**
	 * Cleans up position wise activity summary data from database for the period fromDate+1 to toDate. 
	 * Usually needed when before calculating or recalculating 
	 * @param fromDate
	 * @param toDate
	 * @param tran
	 * @throws SQLException
	 */
	protected void deleteUserHiringActivitySummary(Date fromDate, Date toDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserHiringActivitySummaryManager_DeleteAllRows", tran);
			dq.setString(1, Utils.getDateConvertedToString(fromDate, DateConstants.DB_DATE_PATTERN));
			dq.setString(2, Utils.getDateConvertedToString(toDate, DateConstants.DB_DATE_PATTERN));
			dq.execute();			
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error while deleting user wise activity summary " +
										"for the period: " + fromDate +"to"+ toDate, sqle);
			throw sqle;
		}finally{
			if(dq!=null)
				dq.closeOpenCursors();
		}
	}
	
	public static void main(String[] agrs) {
		UserHiringActivitySummaryManager manager = new UserHiringActivitySummaryManager();
		DBTransaction tran = null;
		try {
		tran = new DBTransaction();
			String lastRunDateStr = "2011-02-15 00:00:00";
			Date lastRunDate = Utils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN);
			manager.updateUserHiringActivitySummary(lastRunDate, tran);
			tran.commit();
		} catch (Exception e) {
			try {
				tran.rollback();

			} catch (SQLException e1) {

			}
		}
	}

}
