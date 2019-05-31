/**
 * 
 */
package com.talentPool.reportDesign.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.positions.PositionConstants;

/**
 * @author Ajeet
 *
 */
public class FilterUtils {


	public static String getJSArrayForPositionStages(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");				
				sb.append("new SelectOption('" + PositionConstants.STEP_LEVEL_SHORTLIST+ "','" + TPLabels.getLabel("report.label.position_stages.shortlist") + "'),");
				sb.append("new SelectOption('" + PositionConstants.STEP_LEVEL_SELECT+ "','" + TPLabels.getLabel("report.label.position_stages.select") + "'),");
				sb.append("new SelectOption('" + PositionConstants.STEP_LEVEL_ACCEPT+ "','" + TPLabels.getLabel("report.label.position_stages.accept") + "'),");
				sb.append("new SelectOption('" + PositionConstants.STEP_LEVEL_JOIN+ "','" + TPLabels.getLabel("report.label.position_stages.join") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for Future dates ", e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
}
