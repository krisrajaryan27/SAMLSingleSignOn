var ColumnSelectionGrid = Class.create({
  initialize: function(gridId,name) {
	  this.id  = gridId; 
	  this.csGrid =  new dhtmlXGridObject(gridId);
	  this.name=name;
  },
  
    /*** Getters ***/
    getId: function(){
		return this.id;
	},
	getCsGrid: function(){
		return this.csGrid;
	},
	getName: function(){
		return this.name;
	}
});

function initColumnSelectionGrid(columnSelectionGrid){
	var csGrid = columnSelectionGrid.getCsGrid();
	csGrid.imgURL = "images/dhtmlxGrid/"; 
	csGrid.setHeader(columnSelectionGrid.getName()+',');  
	csGrid.setInitWidthsP('95,0');
	csGrid.setColAlign("left,left");
	csGrid.setColTypes("ro,ro"); 
	csGrid.setColSorting("cstr,na");
	csGrid.enableMultiselect('true');
	csGrid.enableDragAndDrop(true);
	//csGrid.attachEvent("onRowDblClicked",onRowDoubleClick);
	// TODO csGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	csGrid.attachEvent("onXLE", function(grid_obj,count){
		grid_obj.customGroupFormat=function(name,count){
		       return name;
		}
		grid_obj.groupBy(1);
		grid_obj.collapseAllGroups();
		loadSelectedFields();
	});
	csGrid.init();
	csGrid.setHeaderCursor("pointer,");
}

