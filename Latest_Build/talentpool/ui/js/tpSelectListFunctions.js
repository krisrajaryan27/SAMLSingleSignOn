function onGridObjKeyPressed(gridObj,gridObjD,pageSize,keyCode,ctrl,shift) {
	var id = gridObj.getSelectedId();
	
	if(!isNaN(keyCode)){
		switch(keyCode){		
		case 33:
			//page up
			var idx = gridObj.getRowIndex(id)-pageSize;
			idx = (idx<0)?0:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);
			break;
		case 34:
			var idx = gridObj.getRowIndex(id)+pageSize;
			idx = (idx>=gridObj.getRowsNum())?gridObj.getRowsNum()-1:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);	
			break;
			//page down
		case 38:
			// up arrow key
			var idx = gridObj.getRowIndex(id)-1;
			idx = (idx<0)?0:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);
			break;
		case 40:
			// down arrow key
			var idx = gridObj.getRowIndex(id)+1;
			idx = (idx>=gridObj.getRowsNum())?gridObj.getRowsNum()-1:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);	
			break;
		case 13:
			// enter key
			selectItem(gridObj,gridObjD);
			break;
		case 46:
			//delete key
			deselectItem(gridObjD,gridObj);
			break;
		default:
			var type = gridObj.getSortingState();
			if((keyCode >= 48 && keyCode <= 57)||(keyCode >= 65 && keyCode <= 90)) {
				var ch = String.fromCharCode(keyCode);
				if(gridObj.getSelectedId() != null) {
					var indx = gridObj.getRowIndex(gridObj.getSelectedId());
					var val = gridObj.cells2(indx,type[0]).getValue();
					if(val != null && val.substring(0,1) == ch && (indx+1) < gridObj.getRowsNum()) {				
						var nextRow = gridObj.cells2(indx+1,type[0]);
						if(nextRow) {
							val = nextRow.getValue();							
							if(val != null && val.substring(0,1).toUpperCase() == ch) {
								gridObj.setSelectedRow(gridObj.getRowId(indx+1),false,true,false);
								return;
							}
						}			
					}
				}		
				for(var i=0;i<gridObj.getRowsNum();i++){ 
					var val = gridObj.cells2(i,type[0]).getValue();
					
					if(val != null && val.substring(0,1).toUpperCase() == ch) {
						
						gridObj.setSelectedRow(gridObj.getRowId(i),false,true,false);
						break;			
					}
				}	
			}	
		}		
	}
	return true;	
}

function selectItem(srcGrid,destGrid) {
	moveRowBetweenGrids(srcGrid,destGrid);
}

function deselectItem(srcGrid,destGrid) {
	moveRowBetweenGrids(srcGrid,destGrid);
}

function moveRowBetweenGrids(srcGrid,destGrid) {
	selectedId = srcGrid.getSelectedId();
	selectItems(selectedId,srcGrid,destGrid);
	/*
	if(selectedId != null) {
		srcGrid.moveRow(selectedId,"row_sibling",selectedId,destGrid);
		
		var type = destGrid.getSortingState();
		if (type[1] == 'ASC') {
			destGrid.sortRows(type[0],'str',"asc");
		} else {
			destGrid.sortRows(type[0],'str',"desc");
		}
			
	}	
	*/			
}

function selectItems(items,srcGrid,destGrid) {
	if(items!=null && items.trim()!=''){
		parts = items.split(',');
		for(var mm = 0; mm < parts.length; mm++) {
			try {
				srcGrid.moveRow(parts[mm],"row_sibling",destGrid.getRowId(destGrid.getRowsNum()-1),destGrid);
			} catch(err) {
			
			}
		}
	}	
	
	var type = destGrid.getSortingState();	
	if (type[1] == 'asc') {
		destGrid.sortRows(type[0],'str',"asc");
	} else if (type[1] == 'des') {
		destGrid.sortRows(type[0],'str',"des");
	}		
}
String.prototype.trim = function() {
 // skip leading and trailing whitespace
 // and return everything in between
  var x=this;
  x=x.replace(/^\s*(.*)/, "$1");
  x=x.replace(/(.*?)\s*$/, "$1");
  return x;
}

function moveRowUp(srcGrid) {
	selectedId = srcGrid.getSelectedId();
	if(selectedId != null) {
		srcGrid.moveRowUp(selectedId);
	}
}

function moveRowDown(srcGrid) {
	selectedId = srcGrid.getSelectedId();
	if(selectedId != null) {
		srcGrid.moveRowDown(selectedId);
	}
}

function onFilterFocus(val,text){
	if($(val).value==text){
		$(val).value='';
	}		
}

function onFilterUnfocus(val,text){
	if($(val).value==''){
		$(val).value=text
	}
}

function deleteSelectedItems(items,destGrid) {
	var destGridItems=destGrid.getAllItemIds();
	var indexToDel;
	if(destGridItems!=null && destGridItems.trim()!=''){
		var destGridItemsArr = destGridItems.split(',');
		try {
			if(items!=null && items.trim()!=''){
				var parts = items.split(',');
				for(var j=0;j<parts.length;j++){
					destGrid.deleteRow(parts[j]);
				}
			}
		} catch(err) {		
		}
	}		
}
