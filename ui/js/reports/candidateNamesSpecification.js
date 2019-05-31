/****
 * @author praveenk
 * @since  Feb 27, 2012
 *****/

var StepsGrid = Class.create({
	initialize: function(gridId, name) {
	  this.id  = gridId; 
	  this.stepsGridObj	=  new dhtmlXGridObject(gridId);
	  this.name=name;
	},
	/*** Getters ***/
	getId: function(){
		return this.id;
	},
	getGridObj: function(){
		return this.stepsGridObj;
	},
	getName: function(){
		return this.name;
	}
});

function initStepsGrid(stepsGrid){
	var stepsGridObj = stepsGrid.getGridObj();
	stepsGridObj.imgURL = "images/dhtmlxGrid/"; 
	stepsGridObj.setHeader(stepsGrid.getName());  
	stepsGridObj.setInitWidths('235');
	stepsGridObj.setColAlign("left");
	stepsGridObj.setColTypes("ro"); 
	stepsGridObj.setColSorting("cstr");
	stepsGridObj.enableMultiselect('true');
	stepsGridObj.enableDragAndDrop(true);
	stepsGridObj.attachEvent("onXLE", function(grid_obj, count){
		loadSelectedFields();
	});
	stepsGridObj.init();
	stepsGridObj.setHeaderCursor("pointer");
}
