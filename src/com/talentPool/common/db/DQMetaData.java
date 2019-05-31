package com.talentPool.common.db;

import java.util.ArrayList;

/**
 * @author shivprasad
 * 
 * Stores metadata related to each query
 */

public class DQMetaData {
	private String dqName;
	private String dbQueryName;
	private String dataObjectClassName;
	private ArrayList dbColumnMaps;

	/**
	 * Create new instance of DQMetaData
	 */
	public DQMetaData(){
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\ndataQueryName = " + dqName);
		sb.append("\ndbQueryName = " + dbQueryName);
		sb.append("\ndataObjectClassName = " + dataObjectClassName);
		sb.append("\ndbColumnMaps = " + dbColumnMaps);
		return sb.toString();
	}

	/**
	 * @return Returns the dataObjectClassName.
	 */
	public String getDataObjectClassName() {
		return dataObjectClassName;
	}

	/**
	 * @param dataObjectClassName The dataObjectClassName to set.
	 */
	public void setDataObjectClassName(String dataObjectClassName) {
		this.dataObjectClassName = dataObjectClassName;
	}

	/**
	 * @return Returns the dbColumnMaps.
	 */
	public ArrayList getDbColumnMaps() {
		return dbColumnMaps;
	}

	/**
	 * @param dbColumnMaps The dbColumnMaps to set.
	 */
	public void setDbColumnMaps(ArrayList dbColumnMaps) {
		this.dbColumnMaps = dbColumnMaps;
	}

	/**
	 * @return Returns the dbQueryName.
	 */
	public String getDbQueryName() {
		return dbQueryName;
	}

	/**
	 * @param dbQueryName The dbQueryName to set.
	 */
	public void setDbQueryName(String dbQueryName) {
		this.dbQueryName = dbQueryName;
	}

	/**
	 * @return Returns the dqName.
	 */
	public String getDqName() {
		return dqName;
	}

	/**
	 * @param dqName The dqName to set.
	 */
	public void setDqName(String dqName) {
		this.dqName = dqName;
	}

}
