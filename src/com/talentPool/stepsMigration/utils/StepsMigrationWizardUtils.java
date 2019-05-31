/**
 * 
 */
package com.talentPool.stepsMigration.utils;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.dataobject.CustomReportColumn;
import com.talentPool.masters.constants.StepConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.stepsMigration.constants.StepsMigrationWizardConstants;
import com.talentPool.stepsMigration.data.GroupedStepMapping;
import com.talentPool.stepsMigration.view.StepsMigrationView;

/**
 * @author PraveenK
 * @since  Jan 4, 2012
 */
public class StepsMigrationWizardUtils implements StepsMigrationWizardConstants {
	
	/**
	 * Builds JS array of options for which step to be fetched
	 * @return stepsForJSArray
	 */
	public static String getStepsForJSArray(String migrateStepsFor){
		if(STEPS_FOR_ALL_POSITIONS_AND_TEMPLATES.equals(migrateStepsFor) || STEPS_FOR_OPEN_POSITIONS_AND_TEMPLATES.equals(migrateStepsFor)){
			return StepsMigrationWizardUtils.getStepsForJSArray();
		}else if(STEPS_FOR_CLOSED_POSITIONS.equals(migrateStepsFor)){
			return StepsMigrationWizardUtils.getStepsForClosedPositionsJSArray();
		}else{
			return StepsMigrationWizardUtils.getStepsForJSArray();
		}
	}
	
	/**
	 * Builds JS array of options for which step to be fetched
	 * @return stepsForJSArray
	 */
	public static String getStepsForJSArray(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Utils.getJSArraySelectOption(DEFAULT_SELECT_OPTION, TPLabels.getLabel("common.select"), sb);
		Utils.getJSArraySelectOption(STEPS_FOR_ALL_POSITIONS_AND_TEMPLATES, TPLabels.getLabel("steps_migration.option.all_positions",new Object[]{TPLabels.getLabel("common.positions")}), sb.append(DEFAULT_DELIMITER));
		Utils.getJSArraySelectOption(STEPS_FOR_OPEN_POSITIONS_AND_TEMPLATES, TPLabels.getLabel("steps_migration.option.open_positions",new Object[]{TPLabels.getLabel("common.positions")}), sb.append(DEFAULT_DELIMITER));
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Builds JS array of options for which step to be fetched
	 * @return stepsForJSArray
	 */
	public static String getStepsForClosedPositionsJSArray(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		Utils.getJSArraySelectOption(DEFAULT_SELECT_OPTION, TPLabels.getLabel("common.select"), sb);
		Utils.getJSArraySelectOption(STEPS_FOR_CLOSED_POSITIONS, TPLabels.getLabel("steps_migration.option.closed_positions",new Object[]{TPLabels.getLabel("common.positions")}), sb.append(DEFAULT_DELIMITER));
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Builds JS array of options for which step to be fetched
	 * @return stepsForJSArray
	 */
	public static String getStepsJSArray(List<MasterStepData> stepsList){
		StringBuilder sb = new StringBuilder();
		if(!Utils.isListEmptyOrNull(stepsList)){
			sb.append("[");
			Utils.getJSArraySelectOption("-1","----"+ TPLabels.getLabel("common.select")+"----", sb);
			for (MasterStepData step : stepsList) {
				Utils.getJSArraySelectOption(step.getStepId(), step.getStepName(), sb.append(DEFAULT_DELIMITER));				
			}
			sb.append("]");			
		}else {
			sb.append("[");
			Utils.getJSArraySelectOption("-1","----"+ TPLabels.getLabel("common.select")+"----", sb);
			sb.append("]");
		}
		return sb.toString();
	}
	
	/**
	 * Builds JS array of options for which step to be fetched
	 * @return stepsForJSArray
	 */
	public static String getStepsJSArrayWithStageName(List<MasterStepData> stepsList){
		StringBuilder sb = new StringBuilder();
		if(!Utils.isListEmptyOrNull(stepsList)){
			sb.append("[");
			Utils.getJSArraySelectOption("-1","----"+ TPLabels.getLabel("common.select")+"----", sb);
			for (MasterStepData step : stepsList) {
				Utils.getJSArraySelectOption(step.getStepId(), step.getStepName() +" ["+step.getStage()+"] " , sb.append(DEFAULT_DELIMITER));				
			}
			sb.append("]");			
		}else {
			sb.append("[");
			Utils.getJSArraySelectOption("-1","----"+ TPLabels.getLabel("common.select")+"----", sb);
			sb.append("]");	
		}
		return sb.toString();
	}
	
	/**
	 * Converts JSON string to <code>{@link List}<{@link CustomReportColumn}></code> Object. 
	 * @param columnsJSON
	 * @return <li><code>{@link List}<{@link CustomReportColumn}></code></li>
	 * <li>NULL when Gson attempts to read (or write) a malformed JSON element.</li>
	 */
	public static List<GroupedStepMapping> converToGroupedStepMappingList(String groupedStepMappingJSON){
		List<GroupedStepMapping> groupedStepMappingList = null;
		try {
			Gson gson = new Gson();
			Type columnsListType = new TypeToken<List<GroupedStepMapping>>(){}.getType();
			groupedStepMappingList = gson.fromJson(groupedStepMappingJSON, columnsListType);				
		} catch (JsonSyntaxException jse) {
			TPLogger.getLogger().error("groupedStepMappingJSON: "+groupedStepMappingJSON, jse);
		} catch (JsonParseException jpe) {
			TPLogger.getLogger().error("groupedStepMappingJSON: "+groupedStepMappingJSON, jpe);
		}
		return groupedStepMappingList;
	}
	

	public static void setGroupedStepsIntoView(final Map<String,List<GroupedStepMapping>[]> groupedSteps,final StepsMigrationView stepsMigrationView){
		if(groupedSteps.containsKey(StepConstants.STEP_STAGE_SHORTLIST)){
			stepsMigrationView.setShortListScheduled(groupedSteps.get(StepConstants.STEP_STAGE_SHORTLIST)[1]);
			stepsMigrationView.setShortListNonScheduled(groupedSteps.get(StepConstants.STEP_STAGE_SHORTLIST)[0]);	
		}
		
		if(groupedSteps.containsKey(StepConstants.STEP_STAGE_SELECT)){
			stepsMigrationView.setSelectScheduled(groupedSteps.get(StepConstants.STEP_STAGE_SELECT)[1]);
			stepsMigrationView.setSelectNonScheduled(groupedSteps.get(StepConstants.STEP_STAGE_SELECT)[0]);	
		}
		
		if(groupedSteps.containsKey(StepConstants.STEP_STAGE_HIRE)){
			stepsMigrationView.setHireScheduled(groupedSteps.get(StepConstants.STEP_STAGE_HIRE)[1]);
			stepsMigrationView.setHireNonScheduled(groupedSteps.get(StepConstants.STEP_STAGE_HIRE)[0]);		
		}
	}
	
	public static void setPositionStepsIntoView(final Map<String,List<GroupedStepMapping>> groupedSteps,final StepsMigrationView stepsMigrationView){
		if(groupedSteps.containsKey(StepConstants.STEP_STAGE_SHORTLIST)){
			stepsMigrationView.setShortListLst(groupedSteps.get(StepConstants.STEP_STAGE_SHORTLIST));
		}
		
		if(groupedSteps.containsKey(StepConstants.STEP_STAGE_SELECT)){
			stepsMigrationView.setSelectLst(groupedSteps.get(StepConstants.STEP_STAGE_SELECT));
		}
		
		if(groupedSteps.containsKey(StepConstants.STEP_STAGE_HIRE)){
			stepsMigrationView.setHireLst(groupedSteps.get(StepConstants.STEP_STAGE_HIRE));
		}
	}
	
	public static void  setMasterStepsJSArrayIntoView(final List<MasterStepData>[] masterSteps, final StepsMigrationView stepsMigrationView){
		if(masterSteps!=null){
			String shortListScheduledMasterJsArray = StepsMigrationWizardUtils.getStepsJSArrayWithStageName(masterSteps[1]);
			String shortListNonScheduledMasterJsArray = StepsMigrationWizardUtils.getStepsJSArrayWithStageName(masterSteps[0]);
			stepsMigrationView.setScheduledMasterStepsJsArray(shortListScheduledMasterJsArray);
			stepsMigrationView.setNonScheduledMasterStepsJsArray(shortListNonScheduledMasterJsArray);
		}
	}
	
	public static String getMigrationStatusAsText(String migrationStatus){
		if(MIGRATION_STATUS_DONE.equals(migrationStatus)){
			return "DONE";
		}else if(MIGRATION_STATUS_PENDING.equals(migrationStatus)){
			return "PENDING";
		}else{
			return "-";
		}
	}
	
	public static boolean canMigrate(Map<String, String> migrationStatusMap){
		if(MIGRATION_STATUS_DONE.equals(migrationStatusMap.get("open"))
				&& MIGRATION_STATUS_DONE.equals(migrationStatusMap.get("closed"))
						&& MIGRATION_STATUS_DONE.equals(migrationStatusMap.get("templates"))){
			return false;
		}
		return true;
	}
	
}
