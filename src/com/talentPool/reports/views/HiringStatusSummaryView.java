/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;


/**
 * @author Dhakane
 * 
 */
public class HiringStatusSummaryView extends SimpleDataObject {	
	/**
	 * @return the inProcess
	 */
	public String getInProcess() {
		return getString("inProcess");
	}
	/**
	 * @param inProcess the inProcess to set
	 */
	public void setInProcess(String inProcess) {
		setAttribute("inProcess", inProcess);
	}
	/**
	 * @return the joined
	 */
	public String getJoined() {
		return getString("joined");
	}
	/**
	 * @param joined the joined to set
	 */
	public void setJoined(String joined) {
		setAttribute("joined", joined);
	}
	/**
	 * @return the pendingOffers
	 */
	public String getPendingOffers() {
		return getString("pendingOffers");
	}
	/**
	 * @param pendingOffers the pendingOffers to set
	 */
	public void setPendingOffers(String pendingOffers) {
		setAttribute("pendingOffers", pendingOffers);
	}
	/**
	 * @return the position
	 */
	public String getPosition() {
		return getString("position");
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		setAttribute("position", position);
	}
	/**
	 * @return the vacancies
	 */
	public String getVacancies() {
		return getString("vacancies");
	}
	/**
	 * @param vacancies the vacancies to set
	 */
	public void setVacancies(String vacancies) {
		setAttribute("vacancies", vacancies);
	}	
	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return getString("positionId");
	}
	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	
	/**
	 * @return the applied
	 */
	public String getApplied() {
		return getString("applied");
	}
	/**
	 * @param applied the applied to set
	 */
	public void setApplied(String applied) {
		setAttribute("applied", applied);
	}
}