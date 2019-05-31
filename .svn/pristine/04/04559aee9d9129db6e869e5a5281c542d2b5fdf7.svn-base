/**
 * 
 */
package com.talentPool.search.utils;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.selectionProcess.SelectionProcessConstants;

/**
 * @author shivprasad
 * 
 */
public class SearchUtils {
	public static void setViewed(HttpServletRequest request, String applicantId) {
		try {
			ArrayList<String> viewed = (ArrayList<String>) request.getSession(false).getAttribute("viewed");
			if (viewed == null) {
				viewed = new ArrayList<String>();
			}
			if (!viewed.contains(applicantId)) {
				viewed.add(applicantId);
			}
			request.getSession(false).setAttribute("viewed", viewed);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
	}

	public static String getStatusText(ArrayList<SimpleDataObject> feedbacks) {
		StringBuffer sb = new StringBuffer();
		if (feedbacks != null) {
			for (int i = 0; i < feedbacks.size(); i++) {
				SimpleDataObject sdo = feedbacks.get(i);
				String positionStepIdTo = sdo.getString("positionStepIdTo");
				//String positionStepIdFrom = sdo.getString("positionStepIdFrom");
				String positionTitle = sdo.getString("positionTitle");
				Date processMovedDate = sdo.getDate("processMovedDate");
				String positionStepTitle = sdo.getString("positionStepTitle");

				if (positionStepIdTo.equals(SelectionProcessConstants.STEP_REJECT) || positionStepIdTo.equals(SelectionProcessConstants.STEP_NOT_ATTENDED)
						|| positionStepIdTo.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT)) {
					sb.append("Rejected [" + Utils.getDateConvertedToString(processMovedDate, Utils.regDDMMMYYFormat) + "]");
				} else if (positionStepIdTo.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)) {
					sb.append("Rejected position closed [" + Utils.getDateConvertedToString(processMovedDate, Utils.regDDMMMYYFormat) + "]");
				} else {
					sb.append("In process");
				}
				
				sb.append(": ");
				sb.append(positionTitle + " (" + positionStepTitle + ")");

			}
		}
		return sb.toString();
	}
}
