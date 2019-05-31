/**
 * 
 */
package com.talentPool.stepsMigration.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.stepsMigration.data.GroupedStepMapping;
import com.talentPool.stepsMigration.data.MigrationUser;

/**
 * @author PraveenK
 * @since  Jan 4, 2012
 */
public interface IStepsMigrationWizardService {
	
	
	public Map<String, String> getMigrationStatus();
	
	
	public MigrationUser getUserMigrating();
	
	
	public void saveMigrationUser(String userId) throws SQLException;
	
	public void resetGrouping(String migrateStepsFor,String migrationId);
	
	/**
	 * For given stepsFor constant all steps are grouped and returned in {@link Map} whose key is stage name and value is
	 * List array of size two: 1 - Schedulable list
	 * 2 - Non Schedulable List
	 * @param migrateStepsFor
	 * @return
	 */
	public Map<String,List<GroupedStepMapping>[]> getGroupedSteps(String migrationId);
	
	public Map<Integer,String> getStepLevelsMap();
	
	public List<MasterStepData> getMasterSteps();
	
	public List<MasterStepData>[] getMasterStepsGrouped();
	
	public boolean updateStepMapping(String stepsFor, List<GroupedStepMapping> groupedStepMappingList);
	
	public boolean updateStepMapping(List<GroupedStepMapping> groupedStepMappingList);
	
	public boolean isValidMapping(String positionId);
	
	public List<PositionData> getExceptionPositions() throws SQLException;
	
	public Map<String,List<GroupedStepMapping>> getPositionStepsToMigrate(String positionId);
	
	public String getPositionName(String positionId);
	
	public void saveTemporarily(String migrationId) throws SQLException;
	
	public boolean completeMigration(String migrationId);
	
	public void resetMigrationProcess(String migrationId);
	
}
