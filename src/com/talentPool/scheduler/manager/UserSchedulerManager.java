package com.talentPool.scheduler.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.utils.Utils;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.scheduler.dataobject.SchedulerData;

/**
 * @author SiddharthK
 *
 */
public class UserSchedulerManager {
	
	@SuppressWarnings("unchecked")
	public SchedulerData getSchedulerInfo() {
		DBQuery dq = null;
		ArrayList<SchedulerData> schedulers = null;
		try {
			dq = new DBQuery("dUserSchedulerManager_GetUserSchedulerDetails");
			schedulers = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to get master tables' schedular info ", e);
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
			dq = new DBPreparedQuery("dUserSchedulerManager_UpdateUserSchedulerStartTime");
			dq.setString(1, SchedulerConstants.SCHEDULER_CURRENT_STATUS_RUNNING);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to update master tables' schedular start time ", e);
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
				
			dq = new DBPreparedQuery("dUserSchedulerManager_UpdateUserSchedulerEndTimeAndStatus", dynParam, tran);
			dq.setString(1, status);
			dq.setString(2, SchedulerConstants.SCHEDULER_CURRENT_STATUS_IDLE);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to update master tables' schedular end time ", e);
		} finally {
			if(dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void updateSchedulerProperties(int startHour, int frequency) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserSchedulerManager_UpdateUserSchedulerProperties");
			dq.setInt(1, startHour);
			dq.setInt(2, frequency);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Unable to update master tables' schedular properties ", e);
			throw e;
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}

}
