package com.talentPool.positions.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.constants.PositionDraftConstants;

public class PositionDraftUtils {
	public static String getJSArrayForPositionDraftStatus(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + PositionDraftConstants.POSITION_DRAFT_STATUS_PRIVATE+ "','" + TPLabels.getLabel("position.draft.status.label.private") + "'),");
				sb.append("new SelectOption('" + PositionDraftConstants.POSITION_DRAFT_STATUS_SHARED+ "','" + TPLabels.getLabel("position.draft.status.label.shared") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for position status ", e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
}
