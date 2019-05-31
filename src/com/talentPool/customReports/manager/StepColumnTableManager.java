package com.talentPool.customReports.manager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.StepManager;

public class StepColumnTableManager {
	private	Map<String, String> STEP_NAME_PREFIX = new HashMap<String, String>();
		
	public void updateStepColumns(DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			
			STEP_NAME_PREFIX.put(StepConstants.STEP_TYPE_RECIEVED, " IN");
			STEP_NAME_PREFIX.put(StepConstants.STEP_TYPE_CLEARED, "  OUT");
			STEP_NAME_PREFIX.put(StepConstants.STEP_TYPE_INPROCESS, " Inprocess");
			STEP_NAME_PREFIX.put(StepConstants.STEP_TYPE_REJECT, " Reject");
			Set<String> keySet = STEP_NAME_PREFIX.keySet();
			
			StepManager stepManager = new StepManager();
			List<MasterStepData> stepList = stepManager.getAllSteps("false");

			for (int i = 0; i < stepList.size(); i++) {
				MasterStepData stepData = stepList.get(i);
				String stepId = stepData.getStepId();
				String stepName = stepData.getStepName();
				Iterator<String> itr = keySet.iterator();
				while (itr.hasNext()) {
					int cnt = 1;
					String stepType = itr.next();
					dq = new DBPreparedQuery("dReportTableManager_UpdateStepColumns",tran);
					dq.setString(cnt++,stepName + STEP_NAME_PREFIX.get(stepType));
					dq.setString(cnt++,stepName);
					dq.setString(cnt++,StepConstants.STEP_ACTIVE);
					dq.setString(cnt++,stepId);
					dq.setString(cnt++,stepType);
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

	public static void main(String[] args) {
		StepColumnTableManager manager = new StepColumnTableManager();
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			manager.updateStepColumns(tran);
			tran.commit();
		}catch (Exception e) {
			try {
				tran.rollback();
			} catch (SQLException e1) {
			}
		}
	}

}
