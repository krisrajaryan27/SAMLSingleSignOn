/**
 * 
 */
package com.talentPool.stepsMigration.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao;
import com.talentPool.stepsMigration.data.GroupedStepMapping;

/**
 * @author PraveenK
 * @since  Jan 5, 2012
 */
public class StepsMigrationWizardDao implements IStepsMigrationWizardDao {
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#getMigrationStatus()
	 */
	@Override
	public List<SimpleDataObject> getMigrationStatus() {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> sdoList = null;
		try {
			dq = new DBPreparedQuery("dStepsMigration_getMigrationStatus");
			sdoList = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdoList;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#getUserMigrating()
	 */
	@Override
	public SimpleDataObject getUserMigrating() throws SQLException {
		DBPreparedQuery dq = null;
		SimpleDataObject sdo = null;
		try {
			dq = new DBPreparedQuery("dStepsMigration_getUserMigrating");
			sdo = (SimpleDataObject) dq.getSingleObjectResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdo;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#saveMigrationUser(java.lang.String)
	 */
	@Override
	public void saveMigrationUser(String userId) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dStepsMigration_saveMigrationUser");
			dq.setId(1, userId);
			dq.setString(2, StepsMigrationWizardConstants.MIGRATION_COMPLETION_STATUS_IN_PROCESS);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#resetGrouping()
	 */
	@Override
	public void resetGrouping(String migrateStepsFor, String migrationId) throws SQLException {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		String[] dynParam = {""};
		ArrayList<String> dynamicContent = new ArrayList<String>();
		int cnt  = 1;
		try {
			tran = new DBTransaction();
			
			dq = new DBPreparedQuery("dStepsMigration_deletePreviousGrouping", tran);
			dq.setId(1, migrationId);
			dq.execute();
			
			dq = new DBPreparedQuery("dStepsMigration_setGroupConcatMaxLen", tran);
			dq.execute();
			
			cnt  = 1;
			if(StepsMigrationWizardConstants.STEPS_FOR_ALL_POSITIONS_AND_TEMPLATES.equals(migrateStepsFor)){
				dynParam[0] = "?,?,?,?,?";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_CLOSED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_INPROCESS);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent.add(PositionConstants.POSITION_STATUS_TEMPLATE);
			}else if(StepsMigrationWizardConstants.STEPS_FOR_OPEN_POSITIONS_AND_TEMPLATES.equals(migrateStepsFor)){
				dynParam[0] = "?,?,?,?";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_INPROCESS);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent.add(PositionConstants.POSITION_STATUS_TEMPLATE);
			}else if(StepsMigrationWizardConstants.STEPS_FOR_CLOSED_POSITIONS.equals(migrateStepsFor)){
				dynParam[0] = "?";
				dynamicContent.add(PositionConstants.POSITION_STATUS_CLOSED);
			}
			dq = new DBPreparedQuery("dStepsMigration_insertNewGrouping", dynParam, tran);
			dq.setId(cnt++, migrationId);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setString(cnt++, PositionConstants.STEP_ACTIVE);
			dq.setInt(cnt++, PositionConstants.STEP_NOT_DEFAULT);
			dq.execute();
			
			dq = new DBPreparedQuery("dStepsMigration_updateMigrationUserStepsFor", tran);
			dq.setString(1, migrateStepsFor);
			dq.setId(2, migrationId);
			dq.execute();
			
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if(tran!=null){
				try {
					tran.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw e;
		}finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#getAllStepsGrouped(java.lang.String)
	 */
	@Override
	public List<SimpleDataObject> getGroupedSteps(String migrationId) {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> sdoList = null;
		try {
			dq = new DBPreparedQuery("dStepsMigration_getGroupedSteps");
			dq.setString(1, migrationId);
			sdoList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdoList;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#resetMigrationTempPositionSteps(java.lang.String)
	 */
	@Override
	public boolean resetMigrationTempPositionSteps(String stepsFor) {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			deleteMigrationTempPositionSteps(tran);
			populateMigrationTempPositionSteps(stepsFor, tran);
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if(tran!=null){
				try {
					tran.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}finally {
			if (tran != null) {
				tran.release();
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#deleteMigrationTempPositionSteps(com.talentPool.common.db.DBTransaction)
	 */
	@Override
	public void deleteMigrationTempPositionSteps(DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_deleteMigrationTempPositionSteps", tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_deleteMigrationTempPositionSteps");
			}	
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

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#insertMigrationTempPositionSteps(java.lang.String, com.talentPool.common.db.DBTransaction)
	 */
	@Override
	public void populateMigrationTempPositionSteps(String migrateStepsFor, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		ArrayList<String> dynamicContent = new ArrayList<String>();
		int cnt  = 1;
		try {
			
			if(StepsMigrationWizardConstants.STEPS_FOR_ALL_POSITIONS_AND_TEMPLATES.equals(migrateStepsFor)){
				dynParam[0] = "?,?,?,?,?";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_CLOSED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_INPROCESS);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent.add(PositionConstants.POSITION_STATUS_TEMPLATE);
			}else if(StepsMigrationWizardConstants.STEPS_FOR_OPEN_POSITIONS_AND_TEMPLATES.equals(migrateStepsFor)){
				dynParam[0] = "?,?,?,?";
				dynamicContent.add(PositionConstants.POSITION_STATUS_OPENED);
				dynamicContent.add(PositionConstants.POSITION_STATUS_INPROCESS);
				dynamicContent.add(PositionConstants.POSITION_STATUS_HOLD);
				dynamicContent.add(PositionConstants.POSITION_STATUS_TEMPLATE);
			}else if(StepsMigrationWizardConstants.STEPS_FOR_CLOSED_POSITIONS.equals(migrateStepsFor)){
				dynParam[0] = "?";
				dynamicContent.add(PositionConstants.POSITION_STATUS_CLOSED);
			}
			
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_populateMigrationTempPositionSteps", dynParam, tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_populateMigrationTempPositionSteps", dynParam);
			}
			dq.setString(cnt++, PositionConstants.STEP_ACTIVE);
			dq.setInt(cnt++, PositionConstants.STEP_NOT_DEFAULT);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
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
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#updateStepMapping(java.util.List)
	 */
	@Override
	public boolean updateStepMapping(List<GroupedStepMapping> groupedStepMappingList) {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			if(!Utils.isListEmptyOrNull(groupedStepMappingList)){
				for (GroupedStepMapping groupedStepMapping : groupedStepMappingList) {
					updateTempPositionStepMapping(groupedStepMapping, tran);
					updateMigrationStepsGrouping(groupedStepMapping, tran);
				}	
			}
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if(tran!=null){
				try {
					tran.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}finally {
			if (tran != null) {
				tran.release();
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#updateStepMapping(com.talentPool.stepsMigration.data.GroupedStepMapping, com.talentPool.common.db.DBTransaction)
	 */
	@Override
	public void updateTempPositionStepMapping(GroupedStepMapping groupedStepMapping, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		String[] dynParams = {""};
		ArrayList<String> dynamicContent = new ArrayList<String>();
		int cnt = 1;
		try {
			String qMarks = Utils.setDynamicParamsAndReturnQmarks(groupedStepMapping.getStepIds(), dynamicContent);
			dynParams[0] = qMarks; 
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_updateTempPositionStepMapping", dynParams, tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_updateTempPositionStepMapping", dynParams);
			}
			dq.setString(cnt++, groupedStepMapping.getStepMappingId());
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
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
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#updateMigrationStepsGrouping(com.talentPool.stepsMigration.data.GroupedStepMapping, com.talentPool.common.db.DBTransaction)
	 */
	@Override
	public void updateMigrationStepsGrouping(GroupedStepMapping groupedStepMapping, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_updateMigrationStepsGrouping", tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_updateMigrationStepsGrouping");
			}
			dq.setString(1, groupedStepMapping.getStepMappingId());
			dq.setInt(2, groupedStepMapping.getGroupedStepId());
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

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#getExceptionPositions()
	 */
	@Override
	public List<PositionData> getExceptionPositions() throws SQLException {
		return getExceptionPositions(null);
	}
	
	public List<PositionData> getExceptionPositions(String positionId) throws SQLException {
		DBPreparedQuery dq = null;
		List<PositionData> sdoList = null;
		String[] dynParam = new String[3];
		ArrayList<String> dynamicContent = new ArrayList<String>();
		int cnt=1;
		try {
			if(!Utils.isBlankOrNull(positionId)){
				dynParam[0]=dynParam[1]=dynParam[2]=" tmtps.position_id = ? ";
				dynamicContent.add(positionId);
				dynamicContent.add(positionId);
				dynamicContent.add(positionId);
			}else {
				dynParam[0]=dynParam[1]=" NOT EXISTS (SELECT NULL FROM (SELECT DISTINCT position_id , step_id FROM tp_migration_temp_position_steps) A WHERE  A.step_id =- 1 AND  tmtps.position_id = A.position_id ) ";
				dynParam[2]="1";
			}
			
			dq = new DBPreparedQuery("dStepsMigration_getExceptionPositions",dynParam);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			sdoList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdoList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#getExceptionPositions()
	 */
	@Override
	public boolean isValidMapping(String positionId) throws SQLException {
		List<PositionData> posData = getExceptionPositions(positionId);
		if(posData.size()==0){
			return Boolean.TRUE;			
		}else {
			return Boolean.FALSE;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#getAllStepsGrouped(java.lang.String)
	 */
	@Override
	public List<SimpleDataObject> getPositionStepsToMigrate(String positionId) {
		DBPreparedQuery dq = null;
		List<SimpleDataObject> sdoList = null;
		int cnt  = 1;
		try {
			dq = new DBPreparedQuery("dStepsMigration_getPositionStepsToMigrate");
			dq.setString(cnt++, PositionConstants.STEP_ACTIVE);
			dq.setInt(cnt++, PositionConstants.STEP_NOT_DEFAULT);
			dq.setString(cnt++, positionId);
			sdoList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return sdoList;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#completeMigration()
	 */
	@Override
	public boolean completeMigration(String migrationId) {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			updateActualPositionsSteps(tran);
			updateMigrationStatus(tran);
			resetMigrationTempTable(tran);
			updateMigrationCompletionStatus(migrationId, StepsMigrationWizardConstants.MIGRATION_COMPLETION_STATUS_COMPLETED, tran);
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if(tran!=null){
				try {
					tran.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}finally {
			if (tran != null) {
				tran.release();
			}
		}
		return true;
	}
	
	public void updateActualPositionsSteps(DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_updateActualPositionsSteps", tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_updateActualPositionsSteps");
			}
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
	
	public void updateMigrationStatus(DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_updateMigrationStatus", tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_updateMigrationStatus");
			}
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
	
	public void resetMigrationTempTable(DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_resetMigrationTempTable", tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_resetMigrationTempTable");
			}
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

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#resetMigrationProcess(java.lang.String)
	 */
	@Override
	public void resetMigrationProcess(String migrationId) throws SQLException  {
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();

			deleteMigrationTempPositionSteps(tran);
			
			updateMigrationCompletionStatus(migrationId, StepsMigrationWizardConstants.MIGRATION_COMPLETION_STATUS_RESET, tran);
			
			tran.commit();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			if(tran!=null){
				try {
					tran.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw e;
		}finally {
			if (tran != null) {
				tran.release();
			}
		}
	}
	
	
	public void updateMigrationCompletionStatus(String migrationId, String migrationStatus, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dStepsMigration_updateMigrationCompletionStatus", tran);
			} else {
				dq = new DBPreparedQuery("dStepsMigration_updateMigrationCompletionStatus");
			}
			dq.setString(1, migrationStatus);
			dq.setId(2, migrationId);
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

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao#saveTemporarily(java.lang.String)
	 */
	@Override
	public void saveTemporarily(String migrationId) throws SQLException {
		updateMigrationCompletionStatus(migrationId, StepsMigrationWizardConstants.MIGRATION_COMPLETION_SAVED_TEMPORARILY, null);
	}
}
