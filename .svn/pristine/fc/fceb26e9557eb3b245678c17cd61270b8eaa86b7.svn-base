/**
 * 
 */
package com.talentPool.stepsMigration.action;

import java.util.List;
import java.util.Map;

import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.stepsMigration.data.GroupedStepMapping;
import com.talentPool.stepsMigration.services.IStepsMigrationWizardService;
import com.talentPool.stepsMigration.utils.StepMigrationWizardXMLUtils;
import com.talentPool.stepsMigration.utils.StepsMigrationWizardUtils;
import com.talentPool.stepsMigration.view.StepsMigrationView;
import com.talentPool.struts2.common.TPActionSupport;

/**
 * @author PraveenK
 * @since  Jan 8, 2012
 */
public class ExceptionPositionMigrationAction extends TPActionSupport {

	private static final long serialVersionUID = 5175518823045746128L;

	private IStepsMigrationWizardService _stepsMigrationWizardService;
	
	private String positionId;
	private String groupedStepMappingJSON;
	private String errorMessage;
	private String migrateStepsFor;
	
	public String getExceptionPositionsXML() throws Exception {
		List<PositionData> exceptionPositions = _stepsMigrationWizardService.getExceptionPositions();
		String exceptionPositionsXML = StepMigrationWizardXMLUtils.getExceptionPositionsXML(exceptionPositions);
		getRequest().setAttribute("xmlFile", exceptionPositionsXML);
		return SUCCESS;
	}
	
	public String positionMigrationScreen() throws Exception {
		if(!Utils.isBlankOrNull(getPositionId())){
			if(errorMessage!=null){
				 addActionError(errorMessage);
			}
			StepsMigrationView stepsMigrationView = new StepsMigrationView();
			String positionName = _stepsMigrationWizardService.getPositionName(getPositionId());
			Map<Integer,String> stepLevelMap = _stepsMigrationWizardService.getStepLevelsMap();
			Map<String,List<GroupedStepMapping>> groupedSteps = _stepsMigrationWizardService.getPositionStepsToMigrate(getPositionId());
			List<MasterStepData>[]  masterSteps = _stepsMigrationWizardService.getMasterStepsGrouped();
			StepsMigrationWizardUtils.setPositionStepsIntoView(groupedSteps, stepsMigrationView);
			stepsMigrationView.setStepLevelMap(stepLevelMap);
			StepsMigrationWizardUtils.setMasterStepsJSArrayIntoView(masterSteps, stepsMigrationView);
			getRequest().setAttribute("stepsMigrationView", stepsMigrationView);
			getRequest().setAttribute("positionName", positionName);
			return SUCCESS;
		}else {
			return ERROR;			
		}
	}
	
	public String migratePosition() throws Exception {
		if(!Utils.isBlankOrNull(groupedStepMappingJSON)){
			List<GroupedStepMapping> groupedStepMappingList = StepsMigrationWizardUtils.converToGroupedStepMappingList(groupedStepMappingJSON);
			boolean updatedSteps = _stepsMigrationWizardService.updateStepMapping(groupedStepMappingList);
			if(updatedSteps) {
				Boolean isValidMapping = _stepsMigrationWizardService.isValidMapping(getPositionId());
				if(isValidMapping)
					return SUCCESS;
				else {
					errorMessage = TPLabels.getLabel("steps_migration.error_message.mapping_invalid");
					return ERROR;					
				}
			}else {
				return ERROR;				
			}				
		}
		return ERROR;
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
	 * @return the positionId
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
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
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the migrateStepsFor
	 */
	public String getMigrateStepsFor() {
		return migrateStepsFor;
	}

	/**
	 * @param migrateStepsFor the migrateStepsFor to set
	 */
	public void setMigrateStepsFor(String migrateStepsFor) {
		this.migrateStepsFor = migrateStepsFor;
	}
}
