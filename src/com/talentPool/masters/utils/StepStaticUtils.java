/**
 * 
 */
package com.talentPool.masters.utils;

import static com.talentPool.common.CommonConstants.DEFAULT_DELIMITER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.StepMasterConstants;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.StepManager;

/**
 * All static Utils methods related to step will be handled here.  
 * @author praveenk
 * @since  Feb 21, 2012
 */
public class StepStaticUtils {
	private static Map<String,String> _stepsMap = null;
		
	static {
		reloadStepsMap();
	}
	
	/**
	 * Reloads static Step level map on any change of steps or on startup 
	 */
	public static void reloadStepsMap() {
		try {
			_stepsMap = new HashMap<String, String>();
			StepManager stepManager = new StepManager();
			List<MasterStepData> stepDataList = stepManager.getAllSteps();
			for (MasterStepData masterStepData : stepDataList) {
				_stepsMap.put(masterStepData.getStepId(), masterStepData.getStepName());				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Populating Steps Map", e);
		} 
	}
	
	/**
	 * For given stepId retrieves stepName from static map
	 * @param stepId
	 * @return
	 */
	public static String getStepName(String stepId){
		if(_stepsMap==null){
			reloadStepsMap();
		}
		return _stepsMap.get(stepId);
	}
	
	/**
	 * For given comma delimited stepIds retrieves stepNames from static map
	 * @param stepId
	 * @return comma separated stepNames
	 */
	public static String getStepNames(String stepIds){
		if(_stepsMap==null){
			reloadStepsMap();
		}
		StringBuilder stepNames = new StringBuilder();
		if(!Utils.isBlankOrNull(stepIds)){
			boolean start = true;
			for (String stepId : stepIds.split(DEFAULT_DELIMITER)) {
				if(start){
					stepNames.append(_stepsMap.get(stepId));
					start = false;
				}else {
					stepNames.append(DEFAULT_DELIMITER).append(_stepsMap.get(stepId));
				}
			}
		}
		return stepNames.toString();
	}
	
	public static String getJoinedStepName(){
		return getStepName(StepMasterConstants.JOINED_MASTER_STEP_ID);
	}
}
