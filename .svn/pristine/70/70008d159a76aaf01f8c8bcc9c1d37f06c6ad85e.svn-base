package com.talentPool.vendorservice.dataobject;

import java.util.List;

public class CustomFieldTable {
	private List<CustomFieldRow> rows;
	
	private String tableId;
	private String tableName;

	public List<CustomFieldRow> getRows() {
		return rows;
	}

	public void setRows(List<CustomFieldRow> rows) {
		this.rows = rows;
	}
	
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getUI() {
		StringBuffer sb = new StringBuffer();
		
		int i = 0;
		for (CustomFieldRow row:rows){
			sb.append("<tr>");
			List<CustomFieldCell> cells = row.getCells();
			for (CustomFieldCell cell:cells){
				if (i>0){
					sb.append("<td>");
					sb.append(cell.getData().getUI(String.valueOf(i)));
					sb.append("</td>");
				}else {
					sb.append("<td>");
					sb.append(cell.getValue());
					sb.append("</td>");
				}
			}
			i++;
			sb.append("</tr>");
		}
		
		return sb.toString();
	}
	
}
