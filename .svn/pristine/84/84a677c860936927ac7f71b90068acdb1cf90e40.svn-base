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
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class PositionStepSummaryTableManager {
	public void updatePositionSummary(Date lastRunDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		ArrayList<PositionStepData> events = new ArrayList<PositionStepData>();
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
						PositionStepData psData = events.get(i);	
						
						dq = new DBPreparedQuery("dReportTableManager_AddPositionStepSummaryRows", tran);
						int cnt=1;
						dq.setString(cnt++, psData.getPositionId());
						dq.setString(cnt++, psData.getUserId());
						
						dq.setInt(cnt++, converToInteger(psData.getStep1rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep1rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep1clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep1rejAtt()));
						
						dq.setInt(cnt++, converToInteger(psData.getStep2rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep2rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep2clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep2rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep3rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep3rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep3clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep3rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep4rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep4rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep4clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep4rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep5rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep5rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep5clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep5rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep6rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep6rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep6clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep6rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep7rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep7rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep7clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep7rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep8rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep8rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep8clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep8rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep9rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep9rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep9clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep9rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep10rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep10rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep10clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep10rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep11rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep11rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep11clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep11rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep12rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep12rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep12clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep12rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep13rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep13rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep13clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep13rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep14rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep14rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep14clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep14rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep15rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep15rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep15clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep15rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep16rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep16rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep16clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep16rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep17rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep17rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep17clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep17rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep18rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep18rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep18clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep18rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep19rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep19rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep19clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep19rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep20rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep20rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep20clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep20rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep21rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep21rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep21clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep21rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep22rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep22rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep22clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep22rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep23rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep23rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep23clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep23rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep24rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep24rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep24clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep24rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep25rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep25rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep25clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep25rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep26rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep26rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep26clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep26rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep27rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep27rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep27clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep27rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep28rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep28rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep28clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep28rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep29rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep29rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep29clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep29rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep30rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep30rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep30clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep30rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep31rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep31rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep31clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep31rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep32rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep32rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep32clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep32rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep33rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep33rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep33clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep33rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep34rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep34rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep34clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep34rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep35rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep35rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep35clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep35rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep36rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep36rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep36clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep36rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep37rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep37rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep37clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep37rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep38rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep38rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep38clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep38rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep39rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep39rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep39clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep39rejAtt()));
						dq.setInt(cnt++, converToInteger(psData.getStep40rec()));
						dq.setInt(cnt++, converToInteger(psData.getStep40rej()));
						dq.setInt(cnt++, converToInteger(psData.getStep40clr()));
						dq.setInt(cnt++, converToInteger(psData.getStep40rejAtt()));
						
						dq.setString(cnt++, psData.getOnHold());
						dq.setString(cnt++, psData.getJoined());
						
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
	
	private int converToInteger(String countText) {
		if(!Utils.isBlankOrNull(countText)){
			try{
				return Integer.parseInt(countText);
			}catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	private ArrayList<PositionStepData> fetchPositionSummary(String dynamicPara, ArrayList<String> dynamicContent, DBTransaction tran){
		DBPreparedQuery dq = null;
		ArrayList<PositionStepData> events = new ArrayList<PositionStepData>();
		try {
			String[] dynParam = new String[1];	
			dynParam[0]= dynamicPara;
			dq = new DBPreparedQuery("dReportTableManager_GetPositionStepSummaryRows",dynParam, tran);
			int cnt=1;
			for (int i = 0; i < 40; i++) {
				dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
				dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
				dq.setString(cnt++, SelectionProcessConstants.STEP_REJECT);
				dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
				dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);
				dq.setString(cnt++, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
				dq.setString(cnt++, SelectionProcessConstants.STEP_NOT_ATTENDED);	
			}
			dq.setString(cnt++, SelectionProcessConstants.STEP_ON_HOLD);
			dq.setString(cnt++, SelectionProcessConstants.STEP_JOIN);
			
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
		PositionStepSummaryTableManager manager = new PositionStepSummaryTableManager();
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
