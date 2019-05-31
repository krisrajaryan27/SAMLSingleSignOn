/**
 * 
 */
package com.talentPool.requisition.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.talentPool.budget.BudgetConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.requisition.constants.RequisitionConstants;
import com.talentPool.requisition.dataobject.RequisitionFeedbackData;
import com.talentPool.requisition.scheduler.RequisitionApprovalFeedbackScheduler;
import com.talentPool.requisition.scheduler.RequisitionApprovalNotificationScheduler;

/**
 * @author shivprasad
 * 
 */
public class RequisitionFeedbackManager {
	/**
	 * Checks if the userId has pending approval or not, and return last feedback
	 * 
	 * @param positionId
	 * @param userId
	 * @return
	 */
	public RequisitionFeedbackData isFeedbackPending(String positionId, String userId) {
		RequisitionFeedbackData requisitionFeedbackData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_CheckLastFeedbackForPositionByUser");
			dq.setId(1, PositionConstants.POSITION_STATUS_INPROCESS);
			dq.setId(2, positionId);
			dq.setId(3, userId);
			dq.setId(4, userId);
			requisitionFeedbackData = (RequisitionFeedbackData) dq.getSingleObjectResult();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error in check for feedback rights", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionFeedbackData;
	}

	public String addFeedback(String positionId, String fromStepId, String toStepId, String byUserId, String toUserId, 
			String feedbackDecision, String comment, String notifyUserIds) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		String feedbackId = null;
		try {
			tran = new DBTransaction();
			if(!feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)){
				toStepId="";
				toUserId="";
			}
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_AddFeedback", tran);
			dq.setId(1, positionId);
			dq.setId(2, fromStepId);
			dq.setString(3, Utils.isBlankOrNull(toStepId) ? null : toStepId);
			dq.setId(4, byUserId);
			dq.setString(5, Utils.isBlankOrNull(toUserId) ? null : toUserId);
			dq.setId(6, feedbackDecision);
			if (!Utils.isBlankOrNull(comment)) {
				if (comment.length() > 255) {
					comment = comment.substring(0, 255);
				}
			}
			dq.setString(7, comment);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			feedbackId = dq.getIdResult();

			String positionStatus = "";
			if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_REJECT)) {
				positionStatus = PositionConstants.POSITION_STATUS_REJECTED;
			}
			if (!Utils.isBlankOrNull(positionStatus)) {
				PositionManager positionManager = new PositionManager();
				positionManager.changePositionStatus(positionId, positionStatus, byUserId, BudgetConstants.IS_BUDGET_COMMITTED,tran);
			}
			if(Utils.isBlankOrNull(toStepId) && feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)){
				dq = new DBPreparedQuery("dRequisitionFeedbackManager_UpdatePositionApproveDate", tran);
				dq.setId(1, positionId);
				dq.execute();
			}
			
			tran.commit();
			
			if(!Utils.isBlankOrNull(notifyUserIds)) {
				String[] userIds = notifyUserIds.split(",");
				for (int i = 0; i < userIds.length; i++) {
					if(!userIds[i].equalsIgnoreCase(toUserId)) {
						RequisitionApprovalNotificationScheduler.addTrigger(positionId, userIds[i]);
					} else {
						if (!Utils.isBlankOrNull(toUserId)) {
							RequisitionApprovalFeedbackScheduler.addTrigger(feedbackId);
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in add step approval fb", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
		return feedbackId;
	}

	public void updateFeedback(String feedbackId, String positionId, String fromStepId, String toStepId, String byUserId, 
			String toUserId, String feedbackDecision, String comment, String notifyUserIds) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			RequisitionFeedbackData oldFeedbackData = getRequisitionFeedbackData(feedbackId);
			tran = new DBTransaction();
			if(!feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)){
				toStepId="";
				toUserId="";
			}
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_UpdateFeedback", tran);
			dq.setId(1, fromStepId);
			dq.setString(2, Utils.isBlankOrNull(toStepId) ? null : toStepId);
			dq.setId(3, byUserId);
			dq.setString(4, Utils.isBlankOrNull(toUserId) ? null : toUserId);
			dq.setId(5, feedbackDecision);
			if (!Utils.isBlankOrNull(comment)) {
				if (comment.length() > 255) {
					comment = comment.substring(0, 255);
				}
			}
			dq.setString(6, comment);
			dq.setId(7, feedbackId);
			dq.execute();
			String positionStatus = "";
			if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_HOLD)) {
				positionStatus = PositionConstants.POSITION_STATUS_HOLD;
			} 
			else if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)) {
				positionStatus = PositionConstants.POSITION_STATUS_INPROCESS;
			} else if (feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_REJECT)) {
				positionStatus = PositionConstants.POSITION_STATUS_REJECTED;
			}
			if (!Utils.isBlankOrNull(positionStatus)) {
				PositionManager positionManager = new PositionManager();
				positionManager.changePositionStatus(positionId, positionStatus, byUserId,BudgetConstants.IS_BUDGET_COMMITTED, tran);
			}	
			if(Utils.isBlankOrNull(toStepId) && feedbackDecision.equals(RequisitionConstants.FEEDBACK_ACTION_APPROVE)){
				dq = new DBPreparedQuery("dRequisitionFeedbackManager_UpdatePositionApproveDate", tran);
				dq.setId(1, positionId);
				dq.execute();
			}
			
			tran.commit();

			if(!Utils.isBlankOrNull(notifyUserIds)) {
				String[] userIds = notifyUserIds.split(",");
				for (int i = 0; i < userIds.length; i++) {
					if(!userIds[i].equalsIgnoreCase(toUserId)) {
						RequisitionApprovalNotificationScheduler.addTrigger(positionId, userIds[i]);
					} else {
						if (oldFeedbackData.getToUserId() != toUserId && !Utils.isBlankOrNull(toUserId)) {
							RequisitionApprovalFeedbackScheduler.addTrigger(feedbackId);
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in add step approval fb", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	public void deleteFeedback(String feedbackId, String positionId, String userId) throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_DeleteFeedback", tran);
			dq.setId(1, feedbackId);
			dq.execute();

			PositionManager positionManager = new PositionManager();
			positionManager.changePositionStatus(positionId, PositionConstants.POSITION_STATUS_INPROCESS, userId,BudgetConstants.IS_BUDGET_COMMITTED, tran);
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in add step approval fb", e);
			tran.rollback();
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	/**
	 * @param feedbcakId
	 * @return
	 */
	public RequisitionFeedbackData getRequisitionFeedbackData(String feedbackId) {
		RequisitionFeedbackData requisitionFeedbackData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_GetFeedbackData");
			dq.setId(1, feedbackId);
			requisitionFeedbackData = (RequisitionFeedbackData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in get approval data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionFeedbackData;
	}

	public ArrayList<RequisitionFeedbackData> getPositionApprovalHistory(String positionId) {
		ArrayList<RequisitionFeedbackData> results = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_GetPositionApprovalHistory");
			dq.setId(1, positionId);
			results = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in get approval data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return results;
	}

	public RequisitionFeedbackData getLastRequisitionFeedbackDataForPosition(String positionId) {
		RequisitionFeedbackData requisitionFeedbackData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_GetLastFeedbackDataForPosition");
			dq.setId(1, positionId);
			requisitionFeedbackData = (RequisitionFeedbackData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in get Last feedback data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionFeedbackData;
	}

	public RequisitionFeedbackData getFirstRequisitionFeedbackDataForPosition(String positionId) {
		RequisitionFeedbackData requisitionFeedbackData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_GetFirstFeedbackDataForPosition");
			dq.setId(1, positionId);
			requisitionFeedbackData = (RequisitionFeedbackData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in get First feedback data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionFeedbackData;
	}
	
	public RequisitionFeedbackData getFeedbackDataForDisplay(String feedbackId) {
		RequisitionFeedbackData requisitionFeedbackData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_GetFeedbackDataToView");
			dq.setId(1, feedbackId);
			requisitionFeedbackData = (RequisitionFeedbackData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in get feedback data to display", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionFeedbackData;
	}
		
	public void editPositionDescription(String positionId,String positionTypeExtInt, DBTransaction tran){
		DBPreparedQuery dq = null;		
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = " position_type_ext_int = ? ";
			dynamicContent.add(positionTypeExtInt);
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_EditPositionDescription",dynParam, tran);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setString(cnt, positionId);
			dq.execute();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally {
			if (dq != null) {
				dq.closeOpenCursors();
			}
		}	
	}
	
	public RequisitionFeedbackData getNextRequisitionFeedbackDataForPosition(String positionId) {
		RequisitionFeedbackData requisitionFeedbackData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_GetNextFeedbackDataForPosition");
			dq.setId(1, positionId);
			dq.setId(2, positionId);
			requisitionFeedbackData = (RequisitionFeedbackData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in get Next feedback data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionFeedbackData;
	}
	
	public String getRequisitionCurrentStatus(String positionId,String toStepId) {
		DBPreparedQuery dq = null;
		String requisitionStatus = "";
		try {
			dq = new DBPreparedQuery("dRequisitionFeedbackManager_GetRequisitionCurrentStatus");
			dq.setString(1, positionId);
			dq.setString(2, toStepId);
			SimpleDataObject data = (SimpleDataObject) dq.getSingleObjectResult();
			requisitionStatus = data.getString("requisitionStatus");
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error While Retreiving position title", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return requisitionStatus;
	}
}
