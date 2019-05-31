/**
 * 
 */
package com.talentPool.reportDesign.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author Ajeet
 *
 */
public class ColumnData extends SimpleDataObject{

	public String getColumnName() {
		return getString("columnName");
	}

	public void setColumnName(String columnName) {
		setAttribute("columnName", columnName);
	}
	
	public String getColumnType() {
		return getString("columnType");
	}

	public void setColumnType(String columnType) {
		setAttribute("columnType", columnType);
	}
	
	public String getColumnRank() {
		return getString("columnRank");
	}

	public void setColumnRank(String columnRank) {
		setAttribute("columnRank", columnRank);
	}
}
