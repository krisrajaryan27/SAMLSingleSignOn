package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.PositionStepData;
import com.talentPool.customReports.dataobject.PositionSummaryData;
import com.talentPool.positions.PositionConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class PositionActivitySummaryTableManager {
	public void updatePositionSummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<PositionSummaryData> events = new ArrayList<PositionSummaryData>();
		ArrayList<String> dynamicContent = new ArrayList<String>();
		String fromDateStr = null;
		String toDateStr = null;
		try {
			Date today = new Date();
			String dynamicPara = " where ? >= process_moved_date AND process_moved_date >= ? ";
			
			while(lastRunDate.before(today)){ //run for each day
				Date fromDate = lastRunDate;
				Date toDate = Utils.adjustDateBy(lastRunDate, Calendar.DATE, +1);
				fromDateStr = Utils.getDateConvertedToString(fromDate, DateConstants.DB_DATE_TIME_PATTERN);
				toDateStr = Utils.getDateConvertedToString(toDate, DateConstants.DB_DATE_TIME_PATTERN);
				
				//Insert new Data
				dynamicContent = new ArrayList<String>();
				dynamicContent.add(toDateStr);
				dynamicContent.add(fromDateStr);
				
				events = fetchPositionSummary(dynamicPara, dynamicContent, tran);
				
				if(events!=null & events.size()>0){
					for (int i = 0; i < events.size(); i++) {
						PositionSummaryData psData = events.get(i);	
						
						dq = new DBPreparedQuery("dReportTableManager_AddPositionnActivitySummaryRows", tran);
						int cnt=1;
						dq.setString(cnt++, psData.getPositionId());
						dq.setString(cnt++, psData.getUserId());
						dq.setString(cnt++, psData.getshortlist());
						dq.setString(cnt++, psData.getSelection());
						dq.setString(cnt++, psData.getOffer());
						dq.setString(cnt++, psData.getJoined());
						dq.setString(cnt++, psData.getReject());
						dq.setDate(cnt++, Utils.convertDateToSQLDate(fromDate));
						dq.execute();
					}
				}
				lastRunDate = toDate;
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	private ArrayList<PositionSummaryData> fetchPositionSummary(String dynamicPara, ArrayList<String> dynamicContent, DBTransaction tran){
		DBPreparedQuery dq = null;
		ArrayList<PositionSummaryData> events = new ArrayList<PositionSummaryData>();
		try {
			String[] dynParam = new String[1];	
			dynParam[0]= dynamicPara;
			dq = new DBPreparedQuery("dReportTableManager_GetPositionActivitySummaryRows",dynParam, tran);
			int cnt=1;
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SHORTLIST);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_SELECT);
			dq.setString(cnt++, PositionConstants.STEP_LEVEL_ACCEPT);
				
			dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_REPEAT);
			dq.setString(cnt++, SelectionProcessConstants.STEP_ATTENDED);
			
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
	
	public static void main(String[] agrs) {
		PositionActivitySummaryTableManager manager = new PositionActivitySummaryTableManager();
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String lastRunDateStr = "2006-10-13 00:00:00";
			Date lastRunDate = Utils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN);
			manager.updatePositionSummary(lastRunDate, tran);
			tran.commit();
		} catch (Exception e) {
			try {
				tran.rollback();
				
			} catch (SQLException e1) {
				
			}
		} 
		
	}
}
