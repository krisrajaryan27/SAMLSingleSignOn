package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.EventSummaryData;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.impl.StepsMigrationWizardDao;
import com.talentPool.stepsMigration.services.impl.StepsMigrationWizardService;
import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;

public class EventSummaryTableManager {
	public void updateEventSummary(Date lastRunDate, DBTransaction tran)
			throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<EventSummaryData> events = new ArrayList<EventSummaryData>();
		ArrayList<String> dynamicContent = new ArrayList<String>();
		String fromDateStr = null;
		String toDateStr = null;
		int cnt = 1;
		Date today = new Date();
		Date fromDate = null;
		Date toDate = null;

		try {
			// delete all records where process_date_created > lastRunDateStr 
			dq = new DBPreparedQuery("dReportTableManager_DeleteAllEventSummaryRows", tran);
			dq.setString(1, Utils.getDateConvertedToString(lastRunDate, "yyyy-MM-dd HH:mm:ss"));
			dq.execute();
			
			// handle migration status
			StepsMigrationWizardDao stepsMigrationWizardDao = new StepsMigrationWizardDao();
			StepsMigrationWizardService stepsMigrationWizardService = new StepsMigrationWizardService();
			stepsMigrationWizardService.setStepsMigrationWizardDao(stepsMigrationWizardDao);
			Map<String, String> migrationStatusMap = stepsMigrationWizardService.getMigrationStatus();
			String positionClause = "";
						
			// if both status (for open and closed) are pending, do not run scheduler and return
			if (migrationStatusMap.get("open").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)
					&& migrationStatusMap.get("closed").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)) {
				TPLogger.getLogger().error("migration not completed, can't run scheduler", new Exception());
				return;
			}
						
						
			String dynamicPara = " WHERE ? >= process_moved_date AND process_moved_date >= ? "
					+ "AND step_id_to IS NOT NULL AND step_id_from IS NOT NULL ";

			while (lastRunDate.before(today)) { // run for each day
				TPLogger.getLogger().debug(
						"running event summary job for "
								+ lastRunDate.toString());

				fromDate = lastRunDate;
				toDate = Utils.adjustDateBy(lastRunDate, Calendar.DATE, +1);
				fromDateStr = Utils.getDateConvertedToString(fromDate,
						DateConstants.DB_DATE_PATTERN);
				toDateStr = Utils.getDateConvertedToString(toDate,
						DateConstants.DB_DATE_PATTERN);

				// Insert new Data
				dynamicContent = new ArrayList<String>();
				dynamicContent.add(toDateStr);
				dynamicContent.add(fromDateStr);
				
				events = fetchEventSummary(dynamicPara, dynamicContent, tran);
				
				// TODO :
				// for given date
				// 1. first get all data from event log table
				// 2. insert the in, out, rejected, onhold counts
				// 3. read all records from event summary table
				// 4. get previous inprocess count for each record matching its position_id and step_id
				// 5. update previous inprocess as backlog and then update current inprocess

				if (!Utils.isListEmptyOrNull(events)) {
					for (EventSummaryData esData : events) {
						
						dq = new DBPreparedQuery("dReportTableManager_GetEventSummaryBacklog", tran);
						cnt = 1;
						dq.setString(cnt++, esData.getPositionId());
						dq.setString(cnt++, esData.getStepId());
						dq.setString(cnt++, esData.getProcessDate());

						SimpleDataObject sdo = (SimpleDataObject)dq.getSingleObjectResult();
						int backlog = 0;
						String backlogIds = null;
						if(sdo!=null){
							backlog = sdo.getInt("inprocess");
							backlogIds = sdo.getString("inprocessIds");	
						}
						
						String recievedIds = esData.getReceivedIds();
						String clearedIds = esData.getClearedIds();
						String rejectedIds = esData.getRejectedIds();
						String inprocessIds = getInprocessIds(backlogIds, recievedIds, clearedIds, rejectedIds);
						String inprocess = "";
						try {
							inprocess = esData.getInprocess(); 
						} catch (Exception e) {
							
						}
						if (backlog > 0 && inprocess != null) {
							int temp = (inprocess == "") ? 0 : Integer.parseInt(inprocess);
							inprocess = (backlog + temp) + "";
						}
						
						cnt = 1;
						dq = new DBPreparedQuery("dReportTableManager_AddEventSummaryRows", tran);
						dq.setString(cnt++, esData.getPositionId());
						dq.setString(cnt++, esData.getUserId());
						dq.setString(cnt++, esData.getStepId());
						dq.setString(cnt++, esData.getStepLevel());
						dq.setString(cnt++, esData.getRejected());
						dq.setString(cnt++, esData.getOnhold());
						dq.setString(cnt++, esData.getJoined());
						dq.setString(cnt++, esData.getReceived());
						dq.setString(cnt++, esData.getCleared());
						dq.setString(cnt++, inprocess);
						dq.setString(cnt++, "" + backlog);
						dq.setString(cnt++, inprocessIds);
						dq.setString(cnt++, backlogIds);
						dq.setString(cnt++, recievedIds);
						dq.setString(cnt++, clearedIds);
						dq.setString(cnt++, rejectedIds);
						dq.setString(cnt++, esData.getProcessDate());
						dq.execute();
					}
				}
				lastRunDate = toDate;
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<EventSummaryData> fetchEventSummary(String dynamicPara,
			ArrayList<String> dynamicContent, DBTransaction tran) {
		DBPreparedQuery dq = null;
		ArrayList<EventSummaryData> events = new ArrayList<EventSummaryData>();
		try {
			String[] dynParam = {dynamicPara, dynamicPara};
			
			dq = new DBPreparedQuery("dReportTableManager_GetEventSummaryRows",
					dynParam, tran);
			int cnt = 1;
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++,
					SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++,
					SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++,
					SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++,
					SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);

			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			
			dq.setString(cnt++, StepConstants.STEP_STAGE_HIRE);
			dq.setString(cnt++, StepConstants.STEP_STAGE_HIRE);
			
			
			// where process_moved_date - 4 date values
			// 2 from and to dates for received steps
			if (!Utils.isListEmptyOrNull(dynamicContent)) {
				for (String dynCont : dynamicContent) {
					dq.setString(cnt++, dynCont);
				}
			}
			
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			
			dq.setString(cnt++, StepConstants.STEP_STAGE_HIRE);
			dq.setString(cnt++, StepConstants.STEP_STAGE_HIRE);

			// 2 from and to dates for cleared steps
			if (!Utils.isListEmptyOrNull(dynamicContent)) {
				for (String dynCont : dynamicContent) {
					dq.setString(cnt++, dynCont);
				}
			}

			events = dq.getResult();
			TPLogger.getLogger().info("fetched " + events.size() + " records");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
		return events;
	}
	
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
		EventSummaryTableManager manager = new EventSummaryTableManager();
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String lastRunDateStr = "2011-07-01 00:00:00";
			Date lastRunDate = Utils.convertToDate(lastRunDateStr,
					DateConstants.DB_DATE_PATTERN);
			manager.updateEventSummary(lastRunDate, tran);
			tran.commit();
		} catch (Exception e) {
			try {
				tran.rollback();

			} catch (SQLException e1) {

			}
		}
	}

}
