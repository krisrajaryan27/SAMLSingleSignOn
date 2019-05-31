package com.talentPool.budget.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talentPool.budget.BudgetConstants;
import com.talentPool.budget.dataobject.BudgetFilterData;
import com.talentPool.budget.dataobject.BudgetItem;
import com.talentPool.budget.scheduler.BudgetOwnerNotificationScheduler;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.positions.utils.PositionWithRightsClause;
import com.talentPool.todo.manager.ToDoManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.PermissionSet;

public class BudgetManager {
	
	public void saveOrUpdateBudgetItem(BudgetItem budgetItem, String userId)throws Exception{
		DBPreparedQuery dq = null;
		String budgetItemId = budgetItem.getBudgetItemId();
		try {
			String subDepartmentId = budgetItem.getSubDeptId().equals("-1")?null:budgetItem.getSubDeptId();
			String subSubDepartmentId = budgetItem.getSubSubDeptId().equals("-1")?null:budgetItem.getSubSubDeptId();
			String sub3DepartmentId = budgetItem.getSub3DeptId().equals("-1")?null:budgetItem.getSub3DeptId();
			String sub4DepartmentId = budgetItem.getSub4DeptId().equals("-1")?null:budgetItem.getSub4DeptId();
			
			if(Utils.isBlankOrNull(budgetItemId)){
				dq = new DBPreparedQuery("dBudgetManager_AddBudgetItem");
			}
			else{
				if(!Utils.isBlankOrNull(budgetItem.getOwnerId()) && !userId.equals(budgetItem.getOwnerId()) && !userId.equals(budgetItem.getCreatedByUserId())){
					throw new Exception("budget.manage.error.update_no_access");
				}
				dq = new DBPreparedQuery("dBudgetManager_updateBudgetItem");
			}
			dq.setString(1, budgetItem.getBudgetItemName());
			dq.setString(2,budgetItem.getOwnerId());
			dq.setDate(3, budgetItem.getStartTime());
			dq.setDate(4, budgetItem.getEndTime());
			dq.setString(5,budgetItem.getDeptId());
			dq.setString(6,subDepartmentId);
			dq.setString(7,subSubDepartmentId);
			dq.setString(8,sub3DepartmentId);
			dq.setString(9,sub4DepartmentId);
			dq.setString(10, budgetItem.getGradeId());
			dq.setString(11,budgetItem.getBandId() );
			dq.setInt(12, budgetItem.getAvailableHeadCount());
			dq.setString(13,budgetItem.getStatus());
	
			if(Utils.isBlankOrNull(budgetItemId)){
				dq.setString(14, userId);
			}
			else{
				dq.setString(14, budgetItemId);
			}
			dq.execute();
			if(Utils.isBlankOrNull(budgetItemId)){
				dq = new DBPreparedQuery("dFetchLastInsertID");
				budgetItemId = dq.getIdResult();
				BudgetOwnerNotificationScheduler.addTrigger(budgetItemId, TemplateConstants.TEMPLATE_TYPE_BUDGET_ITEM_CREATION_NOTIFICATION_TO_OWNER, userId,null,null);
			}
			else{
				BudgetOwnerNotificationScheduler.addTrigger(budgetItemId, TemplateConstants.TEMPLATE_TYPE_BUDGET_ITEM_UPDATE_NOTIFICATION_TO_OWNER, userId,null,null);
			}
			//regenerate budget todo
			ToDoManager toDoManager = new ToDoManager();
			toDoManager.regenerateToDoForBudget(budgetItemId, null);
			
		} catch (SQLException exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
			throw new Exception();
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public BudgetItem getBudgetItemToEdit(String budgetItemId) {
		DBPreparedQuery dq = null;
		BudgetItem budgetItem = null;
		try{
			dq = new DBPreparedQuery("dBudgetManager_GetBudgetItemToEdit");
			dq.setString(1, budgetItemId);
			budgetItem = (BudgetItem) dq.getSingleObjectResult();
		} catch (SQLException exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetItem;
	}	
	
	public BudgetItem getBudgetItemToView(String budgetItemId) {
		DBPreparedQuery dq = null;
		BudgetItem budgetItem = null;
		try{
			dq = new DBPreparedQuery("dBudgetManager_GetBudgetItemToView");
			dq.setString(1, budgetItemId);
			budgetItem = (BudgetItem) dq.getSingleObjectResult();			
		} catch (SQLException exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetItem;
	}	
	
	public String getBudgetItemId(String budgetItemName){
		String budgetItemId = "";
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dBudgetManager_GetBudgetItemId");
			dq.setString(1, budgetItemName);
			budgetItemId = dq.getIdResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetItemId;
	}
	
	public List getListOfAssociatedPositionsWithBudgetItem(String budgetItemId) {
		DBPreparedQuery dq = null;
		List positionTitles= null;
		try{
			dq = new DBPreparedQuery("dBudgetManager_GetListOfAssociatedPositionsWithBudgetItem");
			dq.setId(1, budgetItemId);
			positionTitles =  dq.getResult();
		} catch (SQLException exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionTitles;
	}	
	
	public Map getForcedModeRestrictionsForBudgetItem(BudgetFilterData budgetFilterData) {
		DBPreparedQuery dq = null;
		Map restrictions = new HashMap();
		try{			
			dq = new DBPreparedQuery("dBudgetManager_GetForcedModeRestrictionsForBudgetItem");
			dq.setString(1, budgetFilterData.getBudgetItemId());
			dq.setString(2, budgetFilterData.getPositionId()+"");
			dq.setString(3, budgetFilterData.getPositionId()+"");
			dq.setString(4, budgetFilterData.getBudgetItemId());
			BudgetItem budgetItem =  (BudgetItem) dq.getSingleObjectResult();
			restrictions.put(BudgetConstants.FORCED_MODE_RESTRICTION_AVAILABLE_COUNT, budgetItem.getAvailableHeadCount()-budgetItem.getCommittedHeadCount()-budgetItem.getUsedHeadCount());
			restrictions.put(BudgetConstants.FORCED_MODE_RESTRICTION_MIN_UPDATE_HEAD_COUNT, budgetItem.getCommittedHeadCount()+budgetItem.getUsedHeadCount());
			restrictions.put(BudgetConstants.FORCED_MODE_RESTRICTION_END_DATE, budgetItem.getEndTime());
		} catch (Exception exep) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return restrictions;
	}	
	
	public ArrayList<BudgetItem> searchBudgetItems(BudgetFilterData budgetFilterData) {
		DBPreparedQuery dq = null;
		ArrayList budgetItems = null;
		try {
			String[] dynParam = new String[2];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = " ";
			dynParam[1] = " ";

			if (!Utils.isBlankOrNull(budgetFilterData.getBudgetItemName())) {
				dynParam[1] += " AND tbi.budget_item_name LIKE ? ";
				dynamicContent.add(budgetFilterData.getBudgetItemName() + "%");
			}

			if (!Utils.isBlankOrNull(budgetFilterData.getDeptId())) {
				dynParam[1] += " AND tbi.dept_id=? ";
				dynamicContent.add(budgetFilterData.getDeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSubDeptId())) {
				dynParam[1] += " AND tbi.sub_dept_id=? ";
				dynamicContent.add(budgetFilterData.getSubDeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSubSubDeptId())) {
				dynParam[1] += " AND tbi.sub_sub_dept_id=? ";
				dynamicContent.add(budgetFilterData.getSubSubDeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSub3DeptId())) {
				dynParam[1] += " AND tbi.sub3_dept_id=? ";
				dynamicContent.add(budgetFilterData.getSub3DeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSub4DeptId())) {
				dynParam[1] += " AND tbi.sub4_dept_id=? ";
				dynamicContent.add(budgetFilterData.getSub4DeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getOwnerId())) {
				dynParam[1] += " AND tbi.owner_id=? ";
				dynamicContent.add(budgetFilterData.getOwnerId());
			}

//			if (!Utils.isBlankOrNull(budgetFilterData.getStartTime())) {
//				dynParam[1] += " AND tbi.start_time &gt;=? ";
//				dynamicContent.add(budgetFilterData.getStartTime());
//			}
//
//			if (!Utils.isBlankOrNull(budgetFilterData.getEndTime())) {
//				dynParam[1] += " AND tbi.end_time &lt;=? ";
//				dynamicContent.add(budgetFilterData.getEndTime());
//			}

			if (!Utils.isBlankOrNull(budgetFilterData.getGradeId())) {
				dynParam[1] += " AND tbi.grade_id=? ";
				dynamicContent.add(budgetFilterData.getGradeId());
			}

			if (!Utils.isBlankOrNull(budgetFilterData.getBandId())) {
				dynParam[1] += " AND tbi.band_id=? ";
				dynamicContent.add(budgetFilterData.getBandId());
			}

			if (!Utils.isBlankOrNull(budgetFilterData.getPositionId())) {
				dynParam[0] += " , tp_positions tp ";
				dynParam[1] += " AND tp.budget_item_id=tbi.budget_item_id ";
				dynParam[1] += " AND tp.position_id = ? ";
				dynamicContent.add(budgetFilterData.getPositionId());
			}			

			if (!Utils.isBlankOrNull(budgetFilterData.getStatus())) {
				dynParam[1] += " AND tbi.status=? ";
				dynamicContent.add(budgetFilterData.getStatus());
			}	

			dq = new DBPreparedQuery("dBudgetManager_SearchBudgetItems", dynParam);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			budgetItems = dq.getResult();
		} catch (SQLException exep) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return budgetItems;
	}
	
	public ArrayList<SimpleDataObject> getBudgetFilters(PermissionSet permissionSet, String userId, String filterFor, 
			BudgetFilterData budgetFilterData) {
		ArrayList<SimpleDataObject> filters = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[2];
			dynParams[0] = "";
			dynParams[1] = "";
			// first dynamic part
			if (filterFor.equals(BudgetConstants.FILTER_DEPARTMENT)) {
				dynParams[0] = " td.dept_id AS filter_id, td.dept_name AS filter_short_name, td.dept_name AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB_DEPARTMENT)) {
				dynParams[0] = " tds.dept_id AS filter_id, tds.dept_name AS filter_short_name, tds.dept_name AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB_SUB_DEPARTMENT)) {
				dynParams[0] = " tdss.dept_id AS filter_id, tdss.dept_name AS filter_short_name, tdss.dept_name AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB3_DEPARTMENT)) {
				dynParams[0] = " tdsss.dept_id AS filter_id, tdsss.dept_name AS filter_short_name, tdsss.dept_name AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_SUB4_DEPARTMENT)) {
				dynParams[0] = " tdssss.dept_id AS filter_id, tdssss.dept_name AS filter_short_name, tdssss.dept_name AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_OWNER)) {
				dynParams[0] = " tu.user_id AS filter_id, CONCAT(tu.user_fname, ' ', tu.user_lname) AS filter_short_name, CONCAT(tu.user_fname, ' ', tu.user_lname) AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_POSITION)) {
				dynParams[0] = " tp.position_id AS filter_id, tp.position_code AS filter_short_name, tp.position_title AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_GRADE)) {
				dynParams[0] = " tbg.grade_id AS filter_id, tbg.grade_name AS filter_short_name, tbg.grade_name AS filter_full_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_BAND)) {
				dynParams[0] = " tbb.band_id AS filter_id, tbb.band_name AS filter_short_name, tbb.band_name AS filter_full_name ";
			} 	 	 			
			
			// second dynamic part
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParams[1] += PositionWithRightsClause.getClauseForUserInList(userId, "tpsu.user_id", dynamicContent, permissionSet);

			// third dynamic part
			
			if (!Utils.isBlankOrNull(budgetFilterData.getBudgetItemName())) {
				dynParams[1] += " AND tbi.budget_item_name LIKE ? ";
				dynamicContent.add(budgetFilterData.getBudgetItemName() + "%");
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getDeptId())) {
				dynParams[1] += " AND td.dept_id=? ";
				dynamicContent.add(budgetFilterData.getDeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSubDeptId())) {
				dynParams[1] += " AND tds.dept_id=? ";
				dynamicContent.add(budgetFilterData.getSubDeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSubSubDeptId())) {
				dynParams[1] += " AND tdss.dept_id=? ";
				dynamicContent.add(budgetFilterData.getSubSubDeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSub3DeptId())) {
				dynParams[1] += " AND tdsss.dept_id=? ";
				dynamicContent.add(budgetFilterData.getSub3DeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getSub4DeptId())) {
				dynParams[1] += " AND tdssss.dept_id=? ";
				dynamicContent.add(budgetFilterData.getSub4DeptId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getOwnerId())) {
				dynParams[1] += " AND tu.user_id=? ";
				dynamicContent.add(budgetFilterData.getOwnerId());
			}
			if (!Utils.isBlankOrNull(budgetFilterData.getPositionId())) {
				dynParams[1] += " AND tp.position_id=? ";
				dynamicContent.add(budgetFilterData.getPositionId());
			}
			
			if (!Utils.isBlankOrNull(budgetFilterData.getGradeId())) {
				dynParams[1] += " AND tbi.grade_id=? ";
				dynamicContent.add(budgetFilterData.getGradeId());
			}

			if (!Utils.isBlankOrNull(budgetFilterData.getBandId())) {
				dynParams[1] += " AND tbi.band_id=? ";
				dynamicContent.add(budgetFilterData.getBandId());
			}	

			if (filterFor.equals(BudgetConstants.FILTER_DEPARTMENT)) {
				dynParams[1] += " GROUP BY td.dept_id ";
				dynParams[1] += " ORDER BY td.dept_name ";
			} else if (filterFor.equals(BudgetConstants.FILTER_SUB_DEPARTMENT)) {
				dynParams[1] += " AND tds.dept_id is not null ";
				dynParams[1] += " GROUP BY tds.dept_id ";
				dynParams[1] += " ORDER BY tds.dept_name ";
			} else if (filterFor.equals(BudgetConstants.FILTER_SUB_SUB_DEPARTMENT)) {
				dynParams[1] += " AND tdss.dept_id is not null ";
				dynParams[1] += " GROUP BY tdss.dept_id ";
				dynParams[1] += " ORDER BY tdss.dept_name ";
			} else if (filterFor.equals(BudgetConstants.FILTER_SUB3_DEPARTMENT)) {
				dynParams[1] += " AND tdsss.dept_id is not null ";
				dynParams[1] += " GROUP BY tdsss.dept_id ";
				dynParams[1] += " ORDER BY tdsss.dept_name ";
			} else if (filterFor.equals(BudgetConstants.FILTER_SUB4_DEPARTMENT)) {
				dynParams[1] += " AND tdssss.dept_id is not null ";
				dynParams[1] += " GROUP BY tdssss.dept_id ";
				dynParams[1] += " ORDER BY tdssss.dept_name ";
			} else if (filterFor.equals(BudgetConstants.FILTER_OWNER)) {
				dynParams[1] += " AND tu.user_id is not null ";	
				dynParams[1] += " GROUP BY tu.user_id ";
				dynParams[1] += " ORDER BY tu.user_fname, tu.user_lname ";
			}else if (filterFor.equals(BudgetConstants.FILTER_POSITION)) {
				dynParams[1] += " GROUP BY tp.position_id ";
				dynParams[1] += " ORDER BY tp.position_code ";
			}else if (filterFor.equals(BudgetConstants.FILTER_GRADE)) {
				dynParams[1] += " GROUP BY tbg.grade_id ";
				dynParams[1] += " ORDER BY tbg.grade_name ";
			}else if (filterFor.equals(BudgetConstants.FILTER_BAND)) {
				dynParams[1] += " GROUP BY tbb.band_id ";
				dynParams[1] += " ORDER BY tbb.band_name ";
			}
			

			dq = new DBPreparedQuery("dBudgetManager_GetBudgetFilters", dynParams);
			int cnt = 1;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			filters = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return filters;
	}
	
	public ArrayList<LoginData> getUsersForPermissions(String permissionIds) {
		ArrayList<LoginData> users = null;
		DBPreparedQuery dq = null;		
		try {					
			dq = new DBPreparedQuery("dGetUsersForPermissions");
			dq.setString(1, permissionIds);
			dq.setInt(2, UserConstants.ACTIVE);
			users = dq.getResult();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
		return users;
	}
	
	public void transferHeadCountFromBudgetItem(String sourceBudgetItemId, String destBudgetItemId, int headCount, String userId)throws Exception {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try{			
			tran = new DBTransaction();
			String[] dynParams = new String[1];
			
			dynParams[0]="-";
			dq = new DBPreparedQuery("dBudgetManager_UpdateHeadCountForBudgetItem",dynParams,tran);
			dq.setInt(1, headCount);
			dq.setString(2, sourceBudgetItemId);
			dq.execute();
			
			dynParams[0]="+";
			dq = new DBPreparedQuery("dBudgetManager_UpdateHeadCountForBudgetItem",dynParams,tran);
			dq.setInt(1, headCount);
			dq.setString(2, destBudgetItemId);
			dq.execute();	
			
			tran.commit();
			
			BudgetOwnerNotificationScheduler.addTrigger(sourceBudgetItemId, TemplateConstants.TEMPLATE_TYPE_BUDGET_ITEM_HEADS_TRANSFER_NOTIFICATION_TO_OWNER, userId, headCount+"",null);
			
		} catch (SQLException exep) {			
			TPLogger.getLogger().error(GlobalConstants.ERROR, exep);
			
			try {
				tran.rollback();
			} catch (SQLException sq) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, sq);
			}
			throw new Exception(GlobalConstants.ERROR, exep);
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}	
	
	public void changeBudgetStatus(String budgetItemId, String status, String userId)throws Exception{
		DBPreparedQuery dq = null;	
		try {					
			dq = new DBPreparedQuery("dBudgetManager_ChangeStatus");
			dq.setString(1, status);
			dq.setString(2, budgetItemId);
			dq.setString(3, userId);
			dq.setString(4, userId);
			int execute = dq.execute();
			if(execute == 0){
				throw new Exception(TPLabels.getLabel("budget.delete.error.no_privilege"));
			}
			if(status.equals(BudgetConstants.BUDGET_ITEM_STATUS_DELETED)){
				BudgetOwnerNotificationScheduler.addTrigger(budgetItemId, TemplateConstants.TEMPLATE_TYPE_BUDGET_ITEM_DELETION_NOTIFICATION_TO_OWNER, userId, null, null);
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new Exception(GlobalConstants.ERROR,e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
	}
	
	public boolean canModifyBudgetItem(String budgetItemId, String userId) throws Exception{
		DBPreparedQuery dq = null;	
		boolean canModify = false;
		try {					
			dq = new DBPreparedQuery("dBudgetManager_CanModifyBudgetItem");
			dq.setString(1, budgetItemId);
			dq.setString(2, userId);
			dq.setString(3, userId);
			String canModify2 = dq.getIdResult();
			canModify = Boolean.parseBoolean(canModify2);
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw new Exception(GlobalConstants.ERROR,e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return canModify;
	}

}
