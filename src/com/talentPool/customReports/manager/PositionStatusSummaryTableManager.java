package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.PositionSummaryData;
import com.talentPool.positions.PositionConstants;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class PositionStatusSummaryTableManager {
	public void updatePositionSummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<PositionSummaryData> events = new ArrayList<PositionSummaryData>();
		ArrayList<String> dynamicContent = new ArrayList<String>();
		try {
			String dynamicPara = " ";
			events = fetchPositionSummary(dynamicPara, dynamicContent, tran);
			
			if(events!=null & events.size()>0){
				for (int i = 0; i < events.size(); i++) {
					PositionSummaryData psData = events.get(i);	
					
					dq = new DBPreparedQuery("dReportTableManager_AddPositionnStatusSummaryRows", tran);
					int cnt=1;
					dq.setString(cnt++, psData.getPositionId());
					dq.setString(cnt++, psData.getshortlist());
					dq.setString(cnt++, psData.getSelection());
					dq.setString(cnt++, psData.getOffer());
					dq.setString(cnt++, psData.getJoined());
					dq.setString(cnt++, psData.getReject());
					dq.execute();
				}
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
			dq = new DBPreparedQuery("dReportTableManager_GetPositionStatusSummaryRows",dynParam, tran);
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
		PositionStatusSummaryTableManager manager = new PositionStatusSummaryTableManager();
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
