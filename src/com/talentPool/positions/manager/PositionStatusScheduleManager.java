package com.talentPool.positions.manager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.utils.Utils;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.dataobject.SchedulerData;

public class PositionStatusScheduleManager {
	@SuppressWarnings("unchecked")
	public SchedulerData getSchedulerInfo() {
		DBPreparedQuery dq = null;
		ArrayList<SchedulerData> schedulers = null;
		try {
			dq = new DBPreparedQuery("dSchedulerManager_GetPosSchedulerDetails");
			schedulers = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get autoRequisitionApproval schedular info ", e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		if(Utils.isListEmptyOrNull(schedulers))
			return null;
		
		return schedulers.get(0);
	}

	public void updateSchedulerStartTimeAndStatus() {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSchedulerManager_UpdatePosSchedulerStartTime");
			dq.setString(1, SchedulerConstants.SCHEDULER_CURRENT_STATUS_RUNNING);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to autoRequisitionApproval' schedular start time ", e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateSchedulerEndTimeAndStatus(String status) {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			String[] dynParam = {""};
			if(status.equals(SchedulerConstants.SCHEDULER_LAST_STATUS_SUCCESS))
				dynParam[0] = ", last_run_endtime = now(),  last_success_run = now() ";
			else
				dynParam[0] = ", last_run_endtime = null ";
				
			dq = new DBPreparedQuery("dSchedulerManager_UpdatePosSchedulerEndTimeAndStatus", dynParam, tran);
			dq.setString(1, status);
			dq.setString(2, SchedulerConstants.SCHEDULER_CURRENT_STATUS_IDLE);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to autoRequisitionApproval' schedular end time ", e);
		} finally {
			if(dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void updateSchedulerProperties(String startHour, int frequency) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dSchedulerManager_UpdatePosSchedulerProperties");
			dq.setTimestamp(1, new Timestamp(Utils.convertHoursToDateWithTimeZone(startHour, "HH:mm").getTime()));
			dq.setInt(2, frequency);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to autoRequisitionApproval' schedular properties ", e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateSchedulerLastRunDate(Date lastRunDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dpositionApproval_AutoLastRunDate", tran);
			} else {
				dq = new DBPreparedQuery("dpositionApproval_AutoLastRunDate");
			}
			dq.setTimestamp(1, new Timestamp( lastRunDate.getTime()));
			dq.execute();
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	
}
