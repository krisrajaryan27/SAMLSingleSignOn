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
import com.talentPool.customReports.dataobject.ActivitySummaryData;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class ActivitySummaryTableManager {
	public void updateActivitySummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<ActivitySummaryData> events = new ArrayList<ActivitySummaryData>();
		ArrayList<String> dynamicContent = new ArrayList<String>();
		String fromDateStr = null;
		String toDateStr = null;
		try {
			TPLogger.getLogger().info("========= START Activity summary update from : "+lastRunDate+" ===============");
			// delete all records where activity_date >= lastRunDate 
			dq = new DBPreparedQuery("dReportTableManager_DeleteAllActivitySummaryRows", tran);
			dq.setString(1, Utils.getDateConvertedToString(lastRunDate, "yyyy-MM-dd HH:mm:ss"));
			dq.execute();						
						
			Date today = new Date();
			String dynamicPara = " where ? >= interaction_date AND interaction_date >= ? ";
			
			while(lastRunDate.before(today)){ //run for each day
				Date fromDate = lastRunDate;
				Date toDate = Utils.adjustDateBy(lastRunDate, Calendar.DATE, +1);
				fromDateStr = Utils.getDateConvertedToString(fromDate, DateConstants.DB_DATE_TIME_PATTERN);
				toDateStr = Utils.getDateConvertedToString(toDate, DateConstants.DB_DATE_TIME_PATTERN);
				
				//Insert new Data
				dynamicContent = new ArrayList<String>();
				dynamicContent.add(toDateStr);
				dynamicContent.add(fromDateStr);
				
				events = fetchActivitySummary(dynamicPara, dynamicContent, tran);
				
				if(events!=null & events.size()>0){
					for (int i = 0; i < events.size(); i++) {
						ActivitySummaryData psData = events.get(i);	
						
						dq = new DBPreparedQuery("dReportTableManager_AddActivitySummaryRows", tran);
						int cnt=1;
						dq.setString(cnt++, psData.getUserId());
						dq.setString(cnt++, psData.getImported());
						dq.setString(cnt++, psData.getEmailRecieved());
						dq.setString(cnt++, psData.getEmailSent());
						dq.setString(cnt++, psData.getAppointment());
						dq.setString(cnt++, psData.getInterview());
						dq.setString(cnt++, psData.getMessages());
						dq.setString(cnt++, psData.getPhone());
						dq.setString(cnt++, psData.getNote());
						dq.setString(cnt++, psData.getStatusMessage());
						dq.setString(cnt++, psData.getSms());
						dq.setString(cnt++, psData.getShortlisted());
						dq.setString(cnt++, psData.getOfferDetailModified());
						dq.setString(cnt++, psData.getBlacklisted());
						dq.setString(cnt++, psData.getUnblacklisted());
						dq.setDate(cnt++, Utils.convertDateToSQLDate(fromDate));
						dq.execute();
					}
				}
				lastRunDate = toDate;
			}
			TPLogger.getLogger().info("========= End Activity summary update ==============");
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}
	}
	
	private ArrayList<ActivitySummaryData> fetchActivitySummary(String dynamicPara, ArrayList<String> dynamicContent, DBTransaction tran){
		DBPreparedQuery dq = null;
		ArrayList<ActivitySummaryData> events = new ArrayList<ActivitySummaryData>();
		try {
			String[] dynParam = new String[1];	
			dynParam[0]= dynamicPara;
			dq = new DBPreparedQuery("dReportTableManager_GetActivitySummaryRows",dynParam, tran);
			int cnt=1;
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_IMPORT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_EMAIL_SENT);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_APPOINTMENTS);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_INTERVIEW);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_MESSAGE);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_PHONE);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_NOTE);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_STATUS_MESSAGE);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_SMS);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_SHORTLISTED);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_OFFER_DETAILS_MODIFIED);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_BLACKLISTED);
			dq.setInt(cnt++, SelectionProcessConstants.INTERACTION_UNBLACKLISTED);
			
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
		ActivitySummaryTableManager manager = new ActivitySummaryTableManager();
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String lastRunDateStr = "2006-10-13 00:00:00";
			Date lastRunDate = Utils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN);
			manager.updateActivitySummary(lastRunDate, tran);
			tran.commit();
		} catch (Exception e) {
			try {
				tran.rollback();
				
			} catch (SQLException e1) {
				
			}
		} 
		
	}
}
