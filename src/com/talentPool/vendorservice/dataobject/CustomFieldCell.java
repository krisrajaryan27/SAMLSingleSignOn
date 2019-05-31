package com.talentPool.vendorservice.dataobject;

public class CustomFieldCell {
	
	private String columnId;
	private String rowId;
	private String value;
	private VcustomFieldData data;
	
	public String getColumnId() {
		return columnId;
	}
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public VcustomFieldData getData() {
		return data;
	}
	public void setData(VcustomFieldData data) {
		this.data = data;
	}
	
	
}
