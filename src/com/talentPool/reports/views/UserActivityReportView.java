/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

/**
 * @author pallavi
 * @date May 17, 2007
 */
public class UserActivityReportView extends SimpleDataObject {
	/**
	 * @return the candidate.
	 */
	public String getCandidate() {
		return getString("candidate");
	}
	/**
	 * @param candidate 
	 *			the candidate to set.
	 */
	public void setCandidate(String candidate) {
		setAttribute("candidate", candidate);
	}
	/**
	 * @return the interaction.
	 */
	public String getInteraction() {
		return getString("interaction");
	}
	/**
	 * @param interaction 
	 *			the interaction to set.
	 */
	public void setInteraction(String interaction) {
		setAttribute("interaction", interaction);
	}
	/**
	 * @return the interactionDate.
	 */
	public String getInteractionDate() {
		try {
			return DateUtils.getSystemDateFormat(getDate("interactionDate"));			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	/**
	 * @param interactionDate 
	 *			the interactionDate to set.
	 */
	public void setInteractionDate(String interactionDate) {
		setAttribute("interactionDate", interactionDate);
	}
	/**
	 * @return the positionDepartment.
	 */
	public String getPositionDepartment() {
		return getString("positionDepartment");
	}
	/**
	 * @param positionDepartment 
	 *			the positionDepartment to set.
	 */
	public void setPositionDepartment(String positionDepartment) {
		setAttribute("positionDepartment", positionDepartment);
	}
	/**
	 * @return the username.
	 */
	public String getUsername() {
		return getString("username");
	}
	/**
	 * @param username 
	 *			the username to set.
	 */
	public void setUsername(String username) {
		setAttribute("username", username);
	}
	/**
	 * @return the positionId.
	 */
	public String getPositionId() {
		return getString("positionId");
	}
	/**
	 * @param positionId 
	 *			the positionId to set.
	 */
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	
}
