package com.talentPool.customReports.scheduler;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.manager.ActivitySummaryTableManager;
import com.talentPool.customReports.manager.CandidateMasterTableManager;
import com.talentPool.customReports.manager.EventLogTableManager;
import com.talentPool.customReports.manager.EventSummaryTableManager;
import com.talentPool.customReports.manager.MasterTablesManager;
import com.talentPool.customReports.manager.PositionHiringSummaryManager;
import com.talentPool.customReports.manager.PositionMasterTableManager;
import com.talentPool.customReports.manager.UserHiringActivitySummaryManager;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.manager.SchedulerManager;

public class UpdateMasterTablesJob implements Job{
	
	private static Logger log = TPLogger.getLogger();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SchedulerManager schedulerManager = new SchedulerManager();
		try {
			schedulerManager.updateSchedulerStartTimeAndStatus();
			String status = runAllSchedulers();
			schedulerManager.updateSchedulerEndTimeAndStatus(status);
		} catch (SQLException e) {
			log.error(GlobalConstants.ERROR, e);
		}
	}
	
	private String runAllSchedulers() throws SQLException {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		try {
			tran = new DBTransaction();
		
			//getLastRunDate
			dq = new DBPreparedQuery("dReportTableManager_GetLastRunDate", tran);
			String lastRunDateStr = dq.getIdResult();
		
			Date lastRunDate = Utils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN);
		
			log.info("========= START TABLE UPDATE JOB ============= : " + new Date());
			updateCandidateMaster(tran);
			updatePositionMaster(tran);
			updateEventLog(lastRunDateStr, tran);
			updateActivitySummary(lastRunDate, tran);
			//updateEventSummary(lastRunDate, tran);
			updateUserHiringSummary(lastRunDate, tran);
			updatePositionHiringSummary(lastRunDate, tran);
			log.info("========= END TABLE UPDATE JOB ============= : " + new Date());
			
			MasterTablesManager masterTablesManager = new MasterTablesManager();			
			masterTablesManager.updateSchedulerLastRunDate(new Date(), tran);
			
			tran.commit();
			return SchedulerConstants.SCHEDULER_LAST_STATUS_SUCCESS;
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR, e);
			if(tran != null)
				tran.rollback();
			return SchedulerConstants.SCHEDULER_LAST_STATUS_FAIL;
		} finally {
			if(dq != null) {
				if(tran != null)
					dq.releaseTransaction(tran);
				else
					dq.releaseConnection();
			}
		}
	}

	private void updateCandidateMaster(DBTransaction tran) throws Exception {
		CandidateMasterTableManager reportTableManager = new CandidateMasterTableManager();
		reportTableManager.updateCandidateMaster(tran);
	}
	
	private void updatePositionMaster(DBTransaction tran) throws Exception {
		PositionMasterTableManager reportTableManager = new PositionMasterTableManager();
		reportTableManager.updatePositionMaster(tran);
	}
	
	private void updateEventLog(String lastRunDateStr, DBTransaction tran) throws SQLException {
		EventLogTableManager reportTableManager = new EventLogTableManager();
		reportTableManager.updateEventLog(lastRunDateStr, tran);
	}

	private void updateActivitySummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		ActivitySummaryTableManager reportTableManager = new ActivitySummaryTableManager();
		reportTableManager.updateActivitySummary(lastRunDate, tran);
	}
	
	private void updateEventSummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		EventSummaryTableManager reportTableManager = new EventSummaryTableManager();
		reportTableManager.updateEventSummary(lastRunDate, tran);
	}
	
	private void updateUserHiringSummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		UserHiringActivitySummaryManager userHiringSummaryManager = new UserHiringActivitySummaryManager();
		userHiringSummaryManager.updateUserHiringActivitySummary(lastRunDate, tran);
	}
	
	/**
	 * Cleans and updates position hiring summary from lastRunDate 
	 * @param lastRunDate
	 * @param tran
	 * @throws SQLException
	 */
	private void updatePositionHiringSummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		PositionHiringSummaryManager positionHiringSummaryManager = new PositionHiringSummaryManager();
		positionHiringSummaryManager.updatePositionHiringSummary(lastRunDate, tran);
	}
	
	public static void main(String[] agrs) {
		UpdateMasterTablesJob manager = new UpdateMasterTablesJob();
		try {
			manager.execute(null);
		} catch (Exception e) {
			log.error(GlobalConstants.ERROR,e);
		} 
	}
}
