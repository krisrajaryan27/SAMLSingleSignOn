/**
 * 
 */
package com.talentPool.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;

/**
 * @author Ajeet
 * 
 */
public class closeOpenPositions {

	/**
	 * @param args
	 */
	public static String pos = "Position";
	public static int start = 4;
	public static int count = 5;

	public static void main(String[] args) {
		System.out.println("start closing positions");
		try {
			PositionManager positionManager = new PositionManager();
			String positionId = null;
			String positionCode = null;
			for (int i = start; i < count; i++) {
				positionCode = pos + i + "123";
				positionId = positionManager.getPositionIdFromPositionCode(positionCode);
				System.out.println(positionCode+"-"+positionId);
				if (!Utils.isBlankOrNull(positionId)) {
					markCandidatesAndClosePosition(positionId, "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void markCandidatesAndClosePosition(String positionId, String userId) throws Exception {
		DBTransaction tran = null;
		SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
		PositionManager positionManager = new PositionManager();
		try {
			tran = new DBTransaction();
			List candidates = positionManager.getCandidatesInProcessForPosition(positionId);
			Iterator<SimpleDataObject> itr = candidates.iterator();
			while (itr.hasNext()) {
				SimpleDataObject candidate = itr.next();
				System.out.println("candidateId="+candidate.getString("applicantId"));
				SimpleDataObject feedback = selectionProcessManager.getLatestFeedback(candidate.getString("applicantId"));
				if (feedback != null && !Utils.isBlankOrNull(feedback.getString("communicationId"))) {
					// Edit Feedback
					positionManager.editFeedback(feedback.getString("communicationId"), candidate.getString("stepId"), SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT, userId, tran);
				} else {
					// Add New Feedback
					positionManager.addFeedback(candidate.getString("applicantId"), candidate.getString("positionId"), candidate.getString("stepId"), SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT, userId, tran);
				}
				selectionProcessManager.updateApplicantPositionStatus(candidate.getString("applicantId"), null, null, SelectionProcessConstants.APPLICANT_NOT_JOINED, null, null, null, null, null, null, null, tran, null, null);
			}
			// Close Position
			changePositionStatus(positionId, PositionConstants.POSITION_STATUS_CLOSED, userId, tran);
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception e1) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
			throw new Exception();
		} finally {
			if (tran != null) {
				tran.release();
			}
		}
	}
	
	public static void changePositionStatus(String positionId, String positionStatus, String userId, DBTransaction tran) {
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (PositionConstants.POSITION_STATUS_CLOSED.equalsIgnoreCase(positionStatus)) {
				dynParam[0] = ", position_date_closed = now(),  position_closed_by = ? ";
				dynamicContent.add(userId);
			} else if (PositionConstants.POSITION_STATUS_OPENED.equalsIgnoreCase(positionStatus)) {
				dynParam[0] = ", position_date_closed = null,  position_closed_by = null";
			} else if (PositionConstants.POSITION_STATUS_DELETED.equalsIgnoreCase(positionStatus)) {
				dynParam[0] = ", position_date_deleted = now(),  position_deleted_by = ? ";
				dynamicContent.add(userId);
			} else {
				dynParam[0] = "";
			}
			if (tran != null) {
				dq = new DBPreparedQuery("dUpdatePositionStatus", dynParam, tran);
			} else {
				dq = new DBPreparedQuery("dUpdatePositionStatus", dynParam);
			}
			dq.setString(1, positionStatus);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setId(cnt, positionId);
			dq.execute();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
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
