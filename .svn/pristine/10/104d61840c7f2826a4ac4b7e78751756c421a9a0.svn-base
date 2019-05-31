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
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.manager.StepManager;

/**
 * All Utils methods related to step level will be handled here.  
 * @author praveenk
 * @since  Feb 21, 2012
 */
public class StepLevelStaticUtils {
	private static Map<String,String> _stepLevelMap = null;
		
	static {
		reloadStepLevelMap();
	}
	
	/**
	 * Reloads static Step level map on any change of step levels or on startup 
	 */
	public static void reloadStepLevelMap() {
		try {
			_stepLevelMap = new HashMap<String, String>();
			StepManager stepManager = new StepManager();
			List<MasterStepData> stepDataList = stepManager.getAllStages();
			for (MasterStepData masterStepData : stepDataList) {
				_stepLevelMap.put(masterStepData.getStepLevel(), masterStepData.getStage());				
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Populating Step Level Map", e);
		} 
	}
	
	/**
	 * For given stepLevelId retrieves stepLevelName from static map
	 * @param stepLevelId
	 * @return
	 */
	public static String getStepLevelName(String stepLevelId){
		if(_stepLevelMap==null){
			reloadStepLevelMap();
		}
		return _stepLevelMap.get(stepLevelId);
	}
	
	/**
	 * For given comma delimited stepLevelIds retrieves stepLevelNames from static map
	 * @param stepLevelId
	 * @return comma separated stepLevelNames
	 */
	public static String getStepLevelNames(String stepLevelIds){
		if(_stepLevelMap==null){
			reloadStepLevelMap();
		}
		StringBuilder stepLevelNames = new StringBuilder();
		if(!Utils.isBlankOrNull(stepLevelIds)){
			boolean start = true;
			for (String stepLevelId : stepLevelIds.split(DEFAULT_DELIMITER)) {
				if(start){
					stepLevelNames.append(_stepLevelMap.get(stepLevelId));
					start = false;
				}else {
					stepLevelNames.append(DEFAULT_DELIMITER).append(_stepLevelMap.get(stepLevelId));
				}
			}
		}
		return stepLevelNames.toString();
	}
}
