/**
 * 
 */
package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.utils.Utils;

/**
 * @author PraveenK
 * @since  Jan 14, 2012
 */
public class MasterTablesManager {
	
	public void updateSchedulerLastRunDate(Date lastRunDate, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dReportTableManager_UpdateLastRunDate", tran);
			} else {
				dq = new DBPreparedQuery("dReportTableManager_UpdateLastRunDate");
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
	
	/**
	 * @param tran
	 * @return Summary Report Last Run Date
	 * @throws SQLException
	 */
	public Date getLastRunDate(DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		String lastRunDateStr = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dReportTableManager_GetLastRunDate", tran);
			} else {
				dq = new DBPreparedQuery("dReportTableManager_GetLastRunDate");
			}
			lastRunDateStr = dq.getIdResult();
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
		Date lastRunDate = Utils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN);
		return lastRunDate;
	}
}
