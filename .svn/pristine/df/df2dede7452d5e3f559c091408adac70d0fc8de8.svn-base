/**
 * 
 */
package com.talentPool.stepsMigration.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.manager.MasterTablesManager;
import com.talentPool.customReports.scheduler.UpdateMasterTablesScheduler;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.data.GroupedStepMapping;
import com.talentPool.stepsMigration.data.MigrationUser;
import com.talentPool.stepsMigration.services.IStepsMigrationWizardService;
import com.talentPool.stepsMigration.utils.StepsMigrationWizardUtils;
import com.talentPool.stepsMigration.view.StepsMigrationView;
import com.talentPool.struts2.common.TPActionSupport;
import com.talentPool.user.UserConstants;


/**
 * @author PraveenK
 * @since  Jan 4, 2012
 */
public class StepsMigrationWizardAction extends TPActionSupport implements StepsMigrationWizardConstants {

	private static final long serialVersionUID = 1L;
	
	private String migrationId;
	private String stepsFor;
	private String groupedStepMappingJSON;
	
	private IStepsMigrationWizardService _stepsMigrationWizardService;
	
	public String stepsMigrationStatus() throws Exception {
		boolean canMigrate = false;
		Map<String, String> migrationStatusMap = _stepsMigrationWizardService.getMigrationStatus();
		getRequest().setAttribute("openPositions", StepsMigrationWizardUtils.getMigrationStatusAsText(migrationStatusMap.get("open")));
		getRequest().setAttribute("closedPositions", StepsMigrationWizardUtils.getMigrationStatusAsText(migrationStatusMap.get("closed")));
		getRequest().setAttribute("positionTemplates", StepsMigrationWizardUtils.getMigrationStatusAsText(migrationStatusMap.get("templates")));
		if(UserConstants.ROLE_ADMIN == getUserRoleId()){
			canMigrate=StepsMigrationWizardUtils.canMigrate(migrationStatusMap); 				
		}
		getRequest().setAttribute("canMigrate", canMigrate);
		return SUCCESS;
	}
	
	public String migrationDisclaimer() throws Exception {
		Map<String, String> migrationStatus = _stepsMigrationWizardService.getMigrationStatus();
		if(MIGRATION_STATUS_DONE.equals(migrationStatus.get("open")) 
				&& MIGRATION_STATUS_DONE.equals(migrationStatus.get("templates"))
				&& MIGRATION_STATUS_DONE.equals(migrationStatus.get("closed"))){
			getRequest().setAttribute("errormessage", TPLabels.getLabel("steps_migration.error.migration_completed"));
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String stepsMigrationWizard() throws Exception {
		MigrationUser migrationUser = _stepsMigrationWizardService.getUserMigrating();
		if(migrationUser==null){
			_stepsMigrationWizardService.saveMigrationUser(getUserId());
			migrationUser = _stepsMigrationWizardService.getUserMigrating();
			migrationId = migrationUser.getMigrationId(); 
			getRequest().setAttribute("stepsForJSArray", getStepsForJSArray());
			return SUCCESS;
		}else {
			if(!getUserId().equals(migrationUser.getUserId())){
				addActionError(TPLabels.getLabel("steps_migration.error.only_one_user",new Object[]{migrationUser.getUserName()}));
				return ERROR;	
			}else {
				migrationId = migrationUser.getMigrationId();
				stepsFor = migrationUser.getMigrateStepsFor();
				if(MIGRATION_COMPLETION_SAVED_TEMPORARILY.equals(migrationUser.getMigrationCompletionStatus()))
					return FORWARD_MIGRATION_EXCEPTIONS;
				else if(MIGRATION_COMPLETION_STATUS_IN_PROCESS.equals(migrationUser.getMigrationCompletionStatus())){
					if(!Utils.isBlankOrNull(stepsFor) && !DEFAULT_SELECT_OPTION.equals(stepsFor)){
						return FORWARD_STEP_MAPPING;	
					}else{
						String stepsForJSArray = StepsMigrationWizardUtils.getStepsForJSArray(stepsFor);
						getRequest().setAttribute("stepsForJSArray", stepsForJSArray);
						return SUCCESS;
					}
					
				} else {
					return ERROR;
				}
			}
		}
	}
	
	private String getStepsForJSArray(){
		Map<String, String> migrationStatus = _stepsMigrationWizardService.getMigrationStatus();
		String tempStepsFor = "";
		if(MIGRATION_STATUS_DONE.equals(migrationStatus.get("open")) 
				&& MIGRATION_STATUS_DONE.equals(migrationStatus.get("templates"))){
			tempStepsFor = STEPS_FOR_CLOSED_POSITIONS;
		}
		return StepsMigrationWizardUtils.getStepsForJSArray(tempStepsFor);
	}
	
	public String groupAllStepsForMigration() throws Exception {
		if(!Utils.isBlankOrNull(stepsFor) && !DEFAULT_SELECT_OPTION.equals(stepsFor)){
			_stepsMigrationWizardService.resetGrouping(stepsFor, migrationId);
		}else {
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String stepMapping() throws Exception {
		StepsMigrationView stepsMigrationView 				= new StepsMigrationView();
		Map<Integer,String> stepLevelMap 					= _stepsMigrationWizardService.getStepLevelsMap();
		Map<String,List<GroupedStepMapping>[]> groupedSteps = _stepsMigrationWizardService.getGroupedSteps(migrationId);
		List<MasterStepData>[] masterSteps 	= _stepsMigrationWizardService.getMasterStepsGrouped();
		StepsMigrationWizardUtils.setGroupedStepsIntoView(groupedSteps, stepsMigrationView);
		StepsMigrationWizardUtils.setMasterStepsJSArrayIntoView(masterSteps, stepsMigrationView);
		stepsMigrationView.setStepLevelMap(stepLevelMap);
		getRequest().setAttribute("stepsMigrationView", stepsMigrationView);
		getRequest().setAttribute("stepsForJSArray", StepsMigrationWizardUtils.getStepsForJSArray(stepsFor));
		return SUCCESS;
	}
	
	public String validateMigration() throws Exception {
		if(!Utils.isBlankOrNull(groupedStepMappingJSON)){
			List<GroupedStepMapping> groupedStepMappingList = StepsMigrationWizardUtils.converToGroupedStepMappingList(groupedStepMappingJSON);
			boolean updatedSteps = _stepsMigrationWizardService.updateStepMapping(stepsFor, groupedStepMappingList);
			if(updatedSteps) {
				return SUCCESS;		
			}else {
				return ERROR;				
			}				
		}
		return ERROR;
	} 
	
	public String migrationExceptionsScreen(){
		return SUCCESS;
	}
	
	public String confirmAndMigrate() throws Exception {
		try {
			List<PositionData> exceptionPositions = _stepsMigrationWizardService.getExceptionPositions();
			if(Utils.isListEmptyOrNull(exceptionPositions)){
				boolean migrationStatus = _stepsMigrationWizardService.completeMigration(migrationId);
				if(migrationStatus){
					MasterTablesManager masterTablesManager = new MasterTablesManager();
					String lastRunDateStr = "2006-01-01 00:00:00";
					Date lastRunDate = DateUtils.convertToDate(lastRunDateStr, DateConstants.DB_DATE_TIME_PATTERN); 
					masterTablesManager.updateSchedulerLastRunDate(lastRunDate, null);
					UpdateMasterTablesScheduler.resetRegenerateMasterTableTrigger();
					return SUCCESS;
				}else{
					return ERROR;
				}
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return ERROR;
		}
		return ERROR;
	}
	
	public String cancelOrResetMigration() throws Exception {
		_stepsMigrationWizardService.resetMigrationProcess(migrationId);
		return SUCCESS;
	}
	
	public String saveTemporarily() throws Exception {
		_stepsMigrationWizardService.saveTemporarily(migrationId);
		return SUCCESS;
	}
	
	/**
	 * @return the _stepsMigrationWizardService
	 */
	public IStepsMigrationWizardService getStepsMigrationWizardService() {
		return _stepsMigrationWizardService;
	}

	/**
	 * @param _stepsMigrationWizardService the _stepsMigrationWizardService to set
	 */
	public void setStepsMigrationWizardService(
			IStepsMigrationWizardService _stepsMigrationWizardService) {
		this._stepsMigrationWizardService = _stepsMigrationWizardService;
	}

	/**
	 * @return the groupedStepMappingJSON
	 */
	public String getGroupedStepMappingJSON() {
		return groupedStepMappingJSON;
	}

	/**
	 * @param groupedStepMappingJSON the groupedStepMappingJSON to set
	 */
	public void setGroupedStepMappingJSON(String groupedStepMappingJSON) {
		this.groupedStepMappingJSON = groupedStepMappingJSON;
	}

	/**
	 * @return the migrationId
	 */
	public String getMigrationId() {
		return migrationId;
	}

	/**
	 * @param migrationId the migrationId to set
	 */
	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
	}

	/**
	 * @return the stepsFor
	 */
	public String getStepsFor() {
		return stepsFor;
	}

	/**
	 * @param stepsFor the stepsFor to set
	 */
	public void setStepsFor(String stepsFor) {
		this.stepsFor = stepsFor;
	}
}
