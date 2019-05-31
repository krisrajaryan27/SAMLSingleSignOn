<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.talentPool.reports.ReportConstants"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@ page import="java.util.ArrayList"%>
<script src="js/tpSelectListFunctions.js"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<div id="divSelectSpecificStep">	
<table class="innerReport">
	<tr>
		<td class="labelTop">
			<s:text name="custom_report.label.filter_criteria_steps" /> 
		</td>
		<td>
			<div class="leftGrid" id="STEP_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(stepGridLeft,stepGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(stepGridRight,stepGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td >
			<div class="rightGrid" id="STEP_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>		
		</td>
	</tr>
</table>
</div>

<script>
var stepGridLeft;
var stepGridRight;

function initStepGrids(defaultValue) {
	initStepGridLeft();
	initStepGridRight();
	loadGridStep(defaultValue);	
}

//GRID For Step
function initStepGridLeft() {
	stepGridLeft = new dhtmlXGridObject('STEP_GRID_LEFT'); 
	stepGridLeft.imgURL = "images/"; 
	stepGridLeft.setHeader("step"); 
	stepGridLeft.setInitWidths("295");
	stepGridLeft.setNoHeader(true);
	stepGridLeft.setColAlign("left");
	stepGridLeft.setColTypes("ro");
	stepGridLeft.setColSorting("step_name_sort");
	stepGridLeft.init();
	stepGridLeft.setSortImgState(true,0,"ASC");	
	stepGridLeft.attachEvent("onKeyPress",onStepLeftGridKeyPressed);
	stepGridLeft.attachEvent("onRowSelect",doOnStepLeftGridRowSelectHandler);
	stepGridLeft.attachEvent("onRowDblClicked",doOnStepLeftGridRowDblClicked);
	
	stepGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initStepGridRight(){
	stepGridRight = new dhtmlXGridObject('STEP_GRID_RIGHT'); 
	stepGridRight.imgURL = "images/"; 
	stepGridRight.setHeader("step"); 
	stepGridRight.setInitWidths("295");
	stepGridRight.setColAlign("left");
	stepGridRight.setColTypes("ro"); 	
	stepGridRight.setNoHeader(true);
	stepGridRight.setColSorting("step_name_sort");
	stepGridRight.init();
	stepGridRight.sortRows(0,'str',"asc");
	stepGridRight.setSortImgState(true,0,"ASC");	
	stepGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	stepGridRight.attachEvent("onKeyPress",onStepRightGridKeyPressed);
	stepGridRight.attachEvent("onRowSelect",doOnStepRightGridRowSelectHandler);
	stepGridRight.attachEvent("onRowDblClicked",doOnStepRightGridRowDblClicked);
}

function loadGridStep(defaultValue){	
	stepGridLeft.clearAll();
	stepGridLeft.loadXML("reports.do?mode=getStepXML", function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, stepGridLeft, stepGridRight);
	});	
}

function step_name_sort(a,b,order,aId,bId) {
	a0 =stepGridLeft.getUserData(aId,"step");
	b0 = stepGridLeft.getUserData(bId,"step");	
	return sort_data(a0,b0,order);
}

function onStepLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(stepGridLeft,stepGridRight,4,keyCode,ctrl,shift);
}

function doOnStepLeftGridRowSelectHandler() {
	stepGridRight.clearSelection();
}

function doOnStepLeftGridRowDblClicked() {	
	var text = (stepGridLeft.cells(stepGridLeft.getSelectedId(),0)).getValue();
	selectItem(stepGridLeft,stepGridRight);	
	
}

//for right Step grid
function onStepRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(stepGridRight,stepGridLeft,4,keyCode,ctrl,shift);
}

function doOnStepRightGridRowSelectHandler() {
	stepGridLeft.clearSelection();
}

function doOnStepRightGridRowDblClicked() {	
	var text = (stepGridRight.cells(stepGridRight.getSelectedId(),0)).getValue();
	selectItem(stepGridRight,stepGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isStepGridInitialised(){
	return stepGridLeft!=null;
}

function validateAndGetStepFilterValue(){	
	return stepGridRight.getAllItemIds(',');
}

</script>