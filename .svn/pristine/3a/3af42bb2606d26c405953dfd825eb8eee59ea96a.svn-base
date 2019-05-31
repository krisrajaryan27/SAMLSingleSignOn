package com.talentPool.vendorservice.dataobject;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.utils.Utils;

public class CustomFieldRow {
	private List<CustomFieldCell> cells;

	public List<CustomFieldCell> getCells() {
		return cells;
	}

	public void setCells(List<CustomFieldCell> cells) {
		this.cells = cells;
	}
	
	public void addCell(CustomFieldCell cell){
		if (Utils.isListEmptyOrNull(cells)){
			cells = new ArrayList<CustomFieldCell>();
		}
		cells.add(cell);
	}
}
