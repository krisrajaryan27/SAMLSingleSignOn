/**
 * 
 */
package com.talentPool.stepsMigration.services.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.dao.IStepsMigrationWizardDao;
import com.talentPool.stepsMigration.data.GroupedStepMapping;
import com.talentPool.stepsMigration.data.MigrationUser;
import com.talentPool.stepsMigration.services.IStepsMigrationWizardService;

/**
 * @author PraveenK
 * @since  Jan 4, 2012
 */
public class StepsMigrationWizardService implements IStepsMigrationWizardService {
	
	private IStepsMigrationWizardDao _stepsMigrationWizardDao;
	
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getMigrationStatus()
	 */
	@Override
	public Map<String, String> getMigrationStatus() {
		List<SimpleDataObject> sdoList = _stepsMigrationWizardDao.getMigrationStatus();
		Map<String, String> migrationStatusMap = new HashMap<String, String>();
		migrationStatusMap.put("open", StepsMigrationWizardConstants.MIGRATION_STATUS_DONE);
		migrationStatusMap.put("closed", StepsMigrationWizardConstants.MIGRATION_STATUS_DONE);
		migrationStatusMap.put("templates", StepsMigrationWizardConstants.MIGRATION_STATUS_DONE);
		if(!Utils.isListEmptyOrNull(sdoList)){
			for (SimpleDataObject simpleDataObject : sdoList) {
				String positionStatus = simpleDataObject.getString("positionStatus");
				String migrationStatus = simpleDataObject.getString("migrationStatus");
				int cnt =  simpleDataObject.getInt("count");
				
				if(PositionConstants.POSITION_STATUS_OPENED.equals(positionStatus)){
					if(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING.equals(migrationStatus)){
						if(cnt>0)
							migrationStatusMap.put("open", StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING);
					}
				}else if(PositionConstants.POSITION_STATUS_CLOSED.equals(positionStatus)){
					if(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING.equals(migrationStatus)){
						if(cnt>0)
							migrationStatusMap.put("closed", StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING);
					}
				}else if(PositionConstants.POSITION_STATUS_TEMPLATE.equals(positionStatus)){
					if(StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING.equals(migrationStatus)){
						if(cnt>0)
							migrationStatusMap.put("templates", StepsMigrationWizardConstants.MIGRATION_STATUS_PENDING);
					}
				}
			}
		}
		return migrationStatusMap;
	}
	

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getUserMigrating()
	 */
	@Override
	public MigrationUser getUserMigrating() {
		MigrationUser migrationUser = null;
		try {
			SimpleDataObject sdo =  _stepsMigrationWizardDao.getUserMigrating();
			if(sdo!=null){
				migrationUser = new MigrationUser();
				migrationUser.setMigrationId(sdo.getString("migrationId"));
				migrationUser.setUserId(sdo.getString("userId"));
				migrationUser.setUserName(sdo.getString("userName"));
				migrationUser.setMigrateStepsFor(sdo.getString("migrateStepsFor"));
				migrationUser.setMigrationCompletionStatus(sdo.getString("migrationCompletionStatus"));
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return migrationUser;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#saveMigrationUser()
	 */
	@Override
	public void saveMigrationUser(String userId) throws SQLException {
		_stepsMigrationWizardDao.saveMigrationUser(userId);			
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getGroupedSteps(java.lang.String)
	 */
	@Override
	public Map<String, List<GroupedStepMapping>[]> getGroupedSteps(String migrationId) {
		List<SimpleDataObject> sdoList =  _stepsMigrationWizardDao.getGroupedSteps(migrationId);
		Map<String, List<GroupedStepMapping>[]> gropedStepsMap = buildStepsMap(sdoList);
		return gropedStepsMap;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getStepLevelsMap()
	 */
	@Override
	public Map<Integer, String> getStepLevelsMap() {
		Map<Integer, String> stepLevelMap = new HashMap<Integer, String>();
		StepManager stepManager = new StepManager();
		try {
			List<MasterStepData> stepDataList = stepManager.getAllStages();
			for (MasterStepData masterStepData : stepDataList) {
				stepLevelMap.put(Integer.parseInt(masterStepData.getStepLevel()), masterStepData.getStage());				
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return stepLevelMap;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getMasterSteps()
	 */
	@Override
	public List<MasterStepData> getMasterSteps() {
		StepManager stepManager = new StepManager();
		List<MasterStepData>  masterStepsList = null;
		try {
			masterStepsList = stepManager.getAllActiveUserDefinedSteps();			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return masterStepsList;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getMasterSteps()
	 */
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<MasterStepData>[] getMasterStepsGrouped() {
		List<MasterStepData>[] masterStepData = new List[2];
		StepManager stepManager = new StepManager();
		try {
			List<MasterStepData>  masterStepsList = stepManager.getAllActiveUserDefinedSteps();
			masterStepData[0] = new ArrayList<MasterStepData>();
			masterStepData[1] = new ArrayList<MasterStepData>();
			for (MasterStepData masterStep : masterStepsList) {
				if(StepConstants.TRUE.equals(masterStep.getStepSchedulable())){
					masterStepData[1].add(masterStep);
				}else{
					masterStepData[0].add(masterStep);
				}
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return masterStepData;
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#updateMigrationTempPositionSteps()
	 */
	@Override
	public boolean updateStepMapping(String stepsFor, List<GroupedStepMapping> groupedStepMappingList) {
		boolean resetStatus = _stepsMigrationWizardDao.resetMigrationTempPositionSteps(stepsFor);
		if(resetStatus){
			return _stepsMigrationWizardDao.updateStepMapping(groupedStepMappingList);
		}else{
			return false;
		}
	}
	

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#updateStepMapping(java.util.List)
	 */
	@Override
	public boolean updateStepMapping(List<GroupedStepMapping> groupedStepMappingList) {
		return _stepsMigrationWizardDao.updateStepMapping(groupedStepMappingList);
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#isValidMapping(java.lang.String)
	 */
	@Override
	public boolean isValidMapping(String positionId) {
		try {
			return _stepsMigrationWizardDao.isValidMapping(positionId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getExceptionPositions()
	 */
	@Override
	public List<PositionData> getExceptionPositions() throws SQLException {
		return 	_stepsMigrationWizardDao.getExceptionPositions();
	}


	/**
	 * @return the _stepsMigrationWizardDao
	 */
	public IStepsMigrationWizardDao getStepsMigrationWizardDao() {
		return _stepsMigrationWizardDao;
	}

	/**
	 * @param _stepsMigrationWizardDao the _stepsMigrationWizardDao to set
	 */
	public void setStepsMigrationWizardDao(
			IStepsMigrationWizardDao _stepsMigrationWizardDao) {
		this._stepsMigrationWizardDao = _stepsMigrationWizardDao;
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getPositionStepsToMigrate()
	 */
	@Override
	public Map<String, List<GroupedStepMapping>> getPositionStepsToMigrate(String positionId) {
		List<SimpleDataObject> sdoList =  _stepsMigrationWizardDao.getPositionStepsToMigrate(positionId);
		Map<String, List<GroupedStepMapping>> gropedStepsMap = buildPositionStepsMap(sdoList);
		return gropedStepsMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, List<GroupedStepMapping>[]> buildStepsMap(List<SimpleDataObject> sdoList){
		Map<String, List<GroupedStepMapping>[]> groupedStepsMap = null;
		if(!Utils.isListEmptyOrNull(sdoList)){
			groupedStepsMap =  new HashMap<String, List<GroupedStepMapping>[]>();
			for (SimpleDataObject sdo : sdoList) {
				String key = sdo.getString("stepLevel");
				GroupedStepMapping groupedStep = new GroupedStepMapping();
				groupedStep.setGroupedStepId(sdo.getInt("groupingId"));
				groupedStep.setStepIds(sdo.getString("stepIds"));
				groupedStep.setStepName(sdo.getString("stepTitle"));
				groupedStep.setStepMappingId(sdo.getString("mappingStepId"));
				if(groupedStepsMap.containsKey(key)){
					if(GlobalConstants.ENABLED.equals(sdo.getString("isscheduled"))){
						groupedStepsMap.get(key)[1].add(groupedStep);
					}else{
						groupedStepsMap.get(key)[0].add(groupedStep);
					}
				}else{
					List[] gropedStepsList = {new ArrayList<GroupedStepMapping>(), new ArrayList<GroupedStepMapping>()};
					if(GlobalConstants.ENABLED.equals(sdo.getString("isscheduled"))){
						gropedStepsList[1].add(groupedStep);
					}else{
						gropedStepsList[0].add(groupedStep);
					}
					groupedStepsMap.put(key, gropedStepsList);
				}
			}
		}
		return groupedStepsMap;		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, List<GroupedStepMapping>> buildPositionStepsMap(List<SimpleDataObject> sdoList){
		Map<String, List<GroupedStepMapping>> groupedStepsMap = null;
		if(!Utils.isListEmptyOrNull(sdoList)){
			groupedStepsMap =  new HashMap<String, List<GroupedStepMapping>>();
			for (SimpleDataObject sdo : sdoList) {
				String key = sdo.getString("stepLevel");
				GroupedStepMapping groupedStep = new GroupedStepMapping();
				groupedStep.setGroupedStepId(sdo.getInt("groupingId"));
				groupedStep.setStepIds(sdo.getString("stepIds"));
				if(GlobalConstants.ENABLED.equals(sdo.getString("isscheduled"))){
					groupedStep.setStepName(sdo.getString("stepTitle")+ " [Sched.]");	
				}else{
					groupedStep.setStepName(sdo.getString("stepTitle") + " [Non Sched.]");
				}
				
				groupedStep.setStepMappingId(sdo.getString("mappingStepId"));
				groupedStep.setIsScheduled(sdo.getString("isscheduled"));
				if(groupedStepsMap.containsKey(key)){
						groupedStepsMap.get(key).add(groupedStep);
				}else{
					List<GroupedStepMapping> gropedStepsList = new ArrayList<GroupedStepMapping>();
					gropedStepsList.add(groupedStep);
					groupedStepsMap.put(key, gropedStepsList);
				}
			}
		}
		return groupedStepsMap;		
	}
	
	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#getPositionName()
	 */
	@Override
	public String getPositionName(String positionId) {
		PositionManager posManager = new PositionManager();
		return posManager.getPositionName(positionId);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#completeMigration()
	 */
	@Override
	public boolean completeMigration(String migrationId) {
		return _stepsMigrationWizardDao.completeMigration(migrationId);
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#resetGrouping(java.lang.String, java.lang.String)
	 */
	@Override
	public void resetGrouping(String migrateStepsFor, String migrationId) {
		try {
			_stepsMigrationWizardDao.resetGrouping(migrateStepsFor, migrationId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#resetMigrationProcess()
	 */
	@Override
	public void resetMigrationProcess(String migrationId) {
		try {
			_stepsMigrationWizardDao.resetMigrationProcess(migrationId);			
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}


	/* (non-Javadoc)
	 * @see com.talentPool.stepsMigration.services.IStepsMigrationWizardService#saveTemporarily(java.lang.String)
	 */
	@Override
	public void saveTemporarily(String migrationId) throws SQLException {
		_stepsMigrationWizardDao.saveTemporarily(migrationId);
	}
}
