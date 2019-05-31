/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 *
 */
public class PendingActionsView extends SimpleDataObject {
	/**
	 * @return the action
	 */
	public String getAction() {
		return getString("action");
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		setAttribute("action", action);
	}
	/**
	 * @return the candidate
	 */
	public String getCandidate() {
		return getString("applicantName");
	}
	/**
	 * @param candidate the candidate to set
	 */
	public void setCandidate(String applicantName) {
		setAttribute("applicantName", applicantName);
	}
	/**
	 * @return the dueOn
	 */
	public String getDueOn() {
		return getString("pendingDate");
	}
	/**
	 * @param dueOn the dueOn to set
	 */
	public void setDueOn(String pendingDate) {
		setAttribute("pendingDate", pendingDate);
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return getString("positionTitle");
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}	
	/**
	 * @return the user
	 */
	public String getUser() {
		return getString("user");
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		setAttribute("user", user);
	}	
	/**
	 * @return the stepTitle
	 */
	public String getStepTitle() {
		return getString("stepTitle");
	}
	/**
	 * @param stepTitle the stepTitle to set
	 */
	public void setStepTitle(String stepTitle) {
		setAttribute("stepTitle", stepTitle);
	}	
}
