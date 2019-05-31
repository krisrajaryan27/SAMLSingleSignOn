package com.talentPool.common.dataobject;

public class DateTimePattern {
	private String patternId;
	private String patternValue;
	private String dbPatternValue;
	private int patternType;
	
	
	public DateTimePattern(String patternId,String patternValue,String dbPatternValue,int patternType) {
		this.patternValue=patternValue;
		this.patternId=patternId;
		this.patternType=patternType;
		this.dbPatternValue=dbPatternValue;
	}
	
	/**
	 * @return the patternId
	 */
	public String getPatternId() {
		return patternId;
	}
	/**
	 * @param patternId the patternId to set
	 */
	public void setPatternId(String patternId) {
		this.patternId = patternId;
	}
	/**
	 * @return the patternValue
	 */
	public String getPatternValue() {
		return patternValue;
	}
	/**
	 * @param patternValue the patternValue to set
	 */
	public void setPatternValue(String patternValue) {
		this.patternValue = patternValue;
	}

	/**
	 * @return the patternType
	 */
	public int getPatternType() {
		return patternType;
	}

	/**
	 * @param patternType the patternType to set
	 */
	public void setPatternType(int patternType) {
		this.patternType = patternType;
	}

	/**
	 * @return the dbPatternValue
	 */
	public String getDbPatternValue() {
		return dbPatternValue;
	}

	/**
	 * @param dbPatternValue the dbPatternValue to set
	 */
	public void setDbPatternValue(String dbPatternValue) {
		this.dbPatternValue = dbPatternValue;
	}
	
}
