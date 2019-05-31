/**
 * 
 */
package com.talentPool.stepsMigration.dao;

import java.sql.SQLException;
import java.util.List;

import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.stepsMigration.data.GroupedStepMapping;

/**
 * @author PraveenK
 * @since  Jan 5, 2012
 */
public interface IStepsMigrationWizardDao {
	
	public SimpleDataObject getUserMigrating() throws SQLException;
	
	public void saveMigrationUser(String userId) throws SQLException;
	
	public void resetGrouping(String migrateStepsFor, String migrationId) throws SQLException;
	
	public List<SimpleDataObject> getGroupedSteps(String migrationId);
	
	public boolean resetMigrationTempPositionSteps(String stepsFor);
	
	public void deleteMigrationTempPositionSteps(DBTransaction tran) throws SQLException;
	
	public void populateMigrationTempPositionSteps(String stepsFor, DBTransaction tran) throws SQLException;
	
	public boolean updateStepMapping(List<GroupedStepMapping> groupedStepMapping);
	
	public void updateTempPositionStepMapping(GroupedStepMapping groupedStepMapping, DBTransaction tran) throws SQLException;
	
	public void updateMigrationStepsGrouping(GroupedStepMapping groupedStepMapping, DBTransaction tran) throws SQLException;
	
	public List<PositionData> getExceptionPositions() throws SQLException;
	
	public boolean isValidMapping(String positionId) throws SQLException;
	
	public List<SimpleDataObject> getPositionStepsToMigrate(String positionId);
	
	public void saveTemporarily(String migrationId) throws SQLException;
	
	public boolean completeMigration(String migrationId);
	
	public List<SimpleDataObject> getMigrationStatus();
	
	public void resetMigrationProcess(String migrationId) throws SQLException ;
	
}
