package com.talentPool.common.db;

/**
 * @author shivprasad
 * Store DB column mapping
 */
public class DBColumnMap {
	private String attributeName;
	private String columnName;
	private int type;
	
	/**
	 * @param attributeName
	 * @param columnName
	 * @param type
	 */
	public DBColumnMap(String attributeName, String columnName, int type) {
		this.attributeName = attributeName;
		this.columnName = columnName;
		this.type = type;
	}
	/**
	 * @return Returns the attributeName.
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @param attributeName The attributeName to set.
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	/**
	 * @return Returns the columnName.
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName The columnName to set.
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}

}
