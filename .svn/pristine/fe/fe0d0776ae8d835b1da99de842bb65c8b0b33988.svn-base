package com.talentPool.customReports.manager;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import com.talentPool.application.manager.ApplicationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.customReports.dataobject.EventLogData;
import com.talentPool.masters.constants.StepMasterConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.impl.StepsMigrationWizardDao;
import com.talentPool.stepsMigration.services.impl.StepsMigrationWizardService;

public class EventLogTableManager {
	
	public void updateEventLog(String lastRunDateStr, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<EventLogData> events = new ArrayList<EventLogData>();
		ArrayList<String> dynamicContent = new ArrayList<String>();
		try {
			TPLogger.getLogger().info("========= START Event log update from : "+lastRunDateStr+" ===============");
			// delete all records where process_date_created > lastRunDateStr 
			dq = new DBPreparedQuery("dReportTableManager_DeleteAllEventLogRows", tran);
			dq.setString(1, lastRunDateStr);
			dq.execute();

			// handle migration status
			StepsMigrationWizardDao stepsMigrationWizardDao = new StepsMigrationWizardDao();
			StepsMigrationWizardService stepsMigrationWizardService = new StepsMigrationWizardService();
			stepsMigrationWizardService.setStepsMigrationWizardDao(stepsMigrationWizardDao);
			Map<String, String> migrationStatusMap = stepsMigrationWizardService.getMigrationStatus();
			String positionClause = "";
			
			// if both status (for open and closed) are pending, do not run scheduler and return
			// if only 'open' has 'done' status, add position_id where clause to take only open positions
												
			if (migrationStatusMap.get("open").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)
					&& migrationStatusMap.get("closed").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)) {
				TPLogger.getLogger().error("migration not completed, can't run scheduler", new Exception());
				return;
			}
			
			if (migrationStatusMap.get("open").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_DONE)
					&& migrationStatusMap.get("closed").equals(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING)) {
				// Positions closed after migration can be considered as well, since 
				// 1. either their migration is completed when they were open or
				// 2. they are created after migration is completed i.e. using the step master
				
				// 1. get 12.2.0 installation date
				ApplicationManager applicationManager = new ApplicationManager();
				ArrayList<SimpleDataObject> releaseHistory = applicationManager.getReleaseHistory();
				Date release1220InstallationDate = null;
				for(SimpleDataObject release : releaseHistory) {
					if("v12.2.0".equals(release.getAttribute("buildNumber"))){
						release1220InstallationDate = release.getDate("installDate");
						break;
					}
				}
				// 2. consider positions closed after the above date
				if(release1220InstallationDate != null) {
					positionClause = " WHERE (tp.position_status = " + PositionConstants.POSITION_STATUS_OPENED 
							+ " OR tp.position_date_closed > '" + release1220InstallationDate.toString() + "')"; 
				} else {
					positionClause = " WHERE tp.position_status = " + PositionConstants.POSITION_STATUS_OPENED;
				}
			}
			
			//Insert new Data
			String[] dynamicPara = new String[2];
			dynamicPara[0] = positionClause;
			dynamicPara[1] = " AND process_date_created > ? ";
			dynamicContent.add(lastRunDateStr);
			events = fetchEventLogRows(dynamicPara, dynamicContent, tran);
			
			if(events!=null & events.size()>0){
				for (int i = 0; i < events.size(); i++) {
					EventLogData elData = events.get(i);	
					
					dq = new DBPreparedQuery("dReportTableManager_AddEventLogRows", tran);
					int cnt=1;
					dq.setString(cnt++, elData.getProcessId());
					dq.setString(cnt++, elData.getApplicantId());
					dq.setString(cnt++, elData.getApplicantName());
					dq.setString(cnt++, elData.getPositionId());
					dq.setString(cnt++, elData.getPositionCode());
					dq.setString(cnt++, elData.getPositionTitle());
					dq.setString(cnt++, elData.getPositionStepIdFrom());
					dq.setString(cnt++, elData.getStepIdFrom());
					dq.setString(cnt++, elData.getPositionStepTitleFrom());
					dq.setString(cnt++, elData.getPositionStepLevelFrom());
					dq.setString(cnt++, elData.getPositionStepIdTo());
					dq.setString(cnt++, elData.getStepIdTo());
					dq.setString(cnt++, elData.getPositionStepTitleTo());
					dq.setString(cnt++, elData.getPositionStepLevelTo());
					dq.setString(cnt++, elData.getAppointmentId());
					dq.setString(cnt++, elData.getAppointmentFromDate());
					dq.setString(cnt++, elData.getAppointmentStatusId());
					dq.setString(cnt++, elData.getUserId());
					dq.setString(cnt++, elData.getUserName());
					dq.setString(cnt++, elData.getProcessDateCreated());
					dq.setString(cnt++, elData.getProcessMovedDate());
					dq.setString(cnt++, elData.getSelectionProcessIsHidden());
					dq.execute();
				}
			}
			
			//Update Old Data
			dynamicContent = new ArrayList<String>();
			dynamicPara = new String[2]; 
			dynamicPara[0] = "";
			dynamicPara[1] = " AND process_moved_date > ? AND process_date_created < ? ";
			dynamicContent.add(lastRunDateStr);
			dynamicContent.add(lastRunDateStr);
			events = fetchEventLogRows(dynamicPara, dynamicContent, tran);
			if(events!=null & events.size()>0){
				for (int i = 0; i < events.size(); i++) {
					EventLogData elData = events.get(i);
					dq = new DBPreparedQuery("dReportTableManager_UpdateEventLogRows", tran);
					int cnt=1;
					dq.setString(cnt++, elData.getApplicantId());
					dq.setString(cnt++, elData.getApplicantName());
					dq.setString(cnt++, elData.getPositionId());
					dq.setString(cnt++, elData.getPositionCode());
					dq.setString(cnt++, elData.getPositionTitle());
					dq.setString(cnt++, elData.getPositionStepIdFrom());
					dq.setString(cnt++, elData.getStepIdFrom());
					dq.setString(cnt++, elData.getPositionStepTitleFrom());
					dq.setString(cnt++, elData.getPositionStepLevelFrom());
					dq.setString(cnt++, elData.getPositionStepIdTo());
					dq.setString(cnt++, elData.getStepIdTo());
					dq.setString(cnt++, elData.getPositionStepTitleTo());
					dq.setString(cnt++, elData.getPositionStepLevelTo());
					dq.setString(cnt++, elData.getAppointmentId());
					dq.setString(cnt++, elData.getAppointmentFromDate());
					dq.setString(cnt++, elData.getAppointmentStatusId());
					dq.setString(cnt++, elData.getUserId());
					dq.setString(cnt++, elData.getUserName());
					dq.setString(cnt++, elData.getProcessDateCreated());
					dq.setString(cnt++, elData.getProcessMovedDate());
					dq.setString(cnt++, elData.getSelectionProcessIsHidden());
					dq.setString(cnt++, elData.getProcessId());
					dq.execute();
				}
			}
			TPLogger.getLogger().info("========= End Event log update from : "+lastRunDateStr+" ===============");
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<EventLogData> fetchEventLogRows(String[] dynamicPara, ArrayList<String> dynamicContent, DBTransaction tran){
		DBPreparedQuery dq = null;
		ArrayList<EventLogData> events = new ArrayList<EventLogData>();
		try {
			
			dq = new DBPreparedQuery("dReportTableManager_GetEventLogRows", dynamicPara, tran);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_SHORTLIST);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setInt(cnt++, Integer.parseInt(StepMasterConstants.JOINED_MASTER_STEP_ID));
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_REJECT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_ON_HOLD);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_JOINED);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_RESCHEDULE);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_REJECT);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_ATTENDED);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_NOT_ATTENDED);			
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_TITLE_POSITION_CLOSED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, StepMasterConstants.JOINED_MASTER_STEP_ID);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));	
			}
			events = dq.getResult();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
		return events;
	}
	
	public static void main(String[] args){
		EventLogTableManager eventLogTableManager = new EventLogTableManager();
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String lastRunDateStr = "2012-07-19 00:00:00";
			eventLogTableManager.updateEventLog(lastRunDateStr, tran);
			tran.commit();
			
		} catch (Exception e) {
			try {
				tran.rollback();
				
			} catch (SQLException e1) {
				
			}
		}finally{
			tran.release();
		}
	}
}
