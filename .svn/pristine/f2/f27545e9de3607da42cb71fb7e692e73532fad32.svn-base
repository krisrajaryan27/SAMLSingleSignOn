package com.talentPool.positions.dataobject;

import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

public class PositionDuplicateSearchData extends SimpleDataObject {
	ArrayList<String> matchedFields = new ArrayList<String>();
	ArrayList<String> matchedFieldValues = new ArrayList<String>();
	
	/**
	 * @return the matchedFields
	 */
	public ArrayList<String> getMatchedFields() {
		return matchedFields;
	}
	
	/**
	 * @param matchedFields
	 *            the matchedFields to set
	 */
	public void setMatchedFields(ArrayList<String> matchedFields) {
		this.matchedFields = matchedFields;
	}
	
	/**
	 * @param matchedFieldValues
	 *            the matchedFieldValues to set
	 */
	public void setMatchedFieldValues(ArrayList<String> matchedFieldValues) {
		this.matchedFieldValues = matchedFieldValues;
	}	
	
	/**
	 * @return the matchedFieldVales
	 */
	public ArrayList<String> getMatchedFieldValues() {
		return matchedFieldValues;
	}
	
	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}
	
	public String getPositionTitle() {
		return getString("positionTitle");
	}
	
	public void setPositionCode(String positionCode) {
		setAttribute("positionCode", positionCode);
	}
	
	public String getPositionCode() {
		return getString("positionCode");
	}
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	
	public String getPositionId() {
		return getString("positionId");
	}
	
	public int getPriority() {
		return getInt("priority");
	}

	public void setPriority(int priority) {
		setAttribute("priority", priority);
	}
}
