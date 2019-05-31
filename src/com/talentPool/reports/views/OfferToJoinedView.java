/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 *
 */
public class OfferToJoinedView extends SimpleDataObject {	
	/**
	 * @return the joined
	 */
	public String getJoined() {
		return getString("joined");
	}
	/**
	 * @param joined 
	 * 			the joined to set
	 */
	public void setJoined(String joined) {
		setAttribute("joined", joined);
	}
	/**
	 * @return the monthYear
	 */
	public String getMonthYear() {
		return getString("monthYear");
	}
	/**
	 * @param monthYear 
	 * 			the monthYear to set
	 */
	public void setMonthYear(String monthYear) {
		setAttribute("monthYear", monthYear);
	}
	/**
	 * @return the notJoined
	 */
	public String getNotJoined() {
		return getString("notJoined");
	}
	/**
	 * @param notJoined 
	 * 			the notJoined to set
	 */
	public void setNotJoined(String notJoined) {
		setAttribute("notJoined", notJoined);
	}
	/**
	 * @return the offers
	 */
	public String getOffers() {
		return getString("offers");
	}
	/**
	 * @param offers 
	 * 			the offers to set
	 */
	public void setOffers(String offers) {
		setAttribute("offers", offers);
	}
	/**
	 * @return the willJoin
	 */
	public String getWillJoin() {
		return getString("willJoin");
	}
	/**
	 * @param willJoin 
	 * 			the willJoin to set
	 */
	public void setWillJoin(String willJoin) {
		setAttribute("willJoin", willJoin);
	}
	/**
	 * @return the applicantName
	 */
	public String getApplicantName() {
		return getString("applicantName");
	}
	/**
	 * @param applicantName the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}
	/**
	 * @return the positionDept
	 */
	public String getPositionDept() {
		return getString("positionDept");
	}
	/**
	 * @param positionDept the positionDept to set
	 */
	public void setPositionDept(String positionDept) {
		setAttribute("positionDept", positionDept);
	}	
	/**
	 * @return the flag
	 */
	public Integer getFlag() {
		return new Integer(getInt("flag"));
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(int flag) {
		setAttribute("flag", flag);
	}
	/**
	 * @return the statusText
	 */
	public String getStatusText() {
		return getString("statusText");
	}
	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		setAttribute("statusText", statusText);
	}
	/**
	 * @return the joinedFlag
	 */
	public Integer getJoinedFlag() {
		return new Integer(getInt("joinedFlag"));
	}
	/**
	 * @param joinedFlag the joinedFlag to set
	 */
	public void setJoinedFlag(String joinedFlag) {
		setAttribute("joinedFlag", joinedFlag);
	}
	/**
	 * @return the notJoinFlag
	 */
	public Integer getNotJoinFlag() {
		return new Integer(getInt("notJoinFlag"));
	}
	/**
	 * @param notJoinFlag the notJoinFlag to set
	 */
	public void setNotJoinFlag(String notJoinFlag) {
		setAttribute("notJoinFlag", notJoinFlag);
	}
	/**
	 * @return the toJoinFlag
	 */
	public Integer getToJoinFlag() {
		return new Integer(getInt("toJoinFlag"));
	}
	/**
	 * @param toJoinFlag the toJoinFlag to set
	 */
	public void setToJoinFlag(String toJoinFlag) {
		setAttribute("toJoinFlag", toJoinFlag);
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
	
}
