<%@ taglib prefix="s" uri="/struts-tags" %>
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
<div id="divSelectSpecificPosition" style="display:block;">	
<table class="innerReport">
	<tr>
		<td class="labelTop">
			<s:text name="common.position" /> 
		</td>
		<td>
			<div class="leftGrid" id="POSITION_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>			
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(positionGridLeft,positionGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(positionGridRight,positionGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td >
			<div class="rightGrid" id="POSITION_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>			
		</td>
	</tr>
</table>
</div>

<script>
var positionGridLeft;
var positionGridRight;

//GRID For Position
function initPositionGrids(defaultValue) {
	initPositionGridLeft();
	initPositionGridRight();
	loadGridPosition(defaultValue);	
}

function initPositionGridLeft(){
	positionGridLeft = new dhtmlXGridObject('POSITION_GRID_LEFT'); 
	positionGridLeft.imgURL = "images/";
	positionGridLeft.setHeader("position"); 
	positionGridLeft.setInitWidths("295");
	positionGridLeft.setNoHeader(true);
	positionGridLeft.setColAlign("left");
	positionGridLeft.setColTypes("ro");
	positionGridLeft.setColSorting("position_name_sort");
	positionGridLeft.init();
	positionGridLeft.setSortImgState(true,0,"ASC");	
	positionGridLeft.attachEvent("onKeyPress",onPositionLeftGridKeyPressed);
	positionGridLeft.attachEvent("onRowSelect",doOnPositionLeftGridRowSelectHandler);
	positionGridLeft.attachEvent("onRowDblClicked",doOnPositionLeftGridRowDblClicked);
	
	positionGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initPositionGridRight(){
	positionGridRight = new dhtmlXGridObject('POSITION_GRID_RIGHT'); 
	positionGridRight.imgURL = "images/"; 
	positionGridRight.setHeader("position"); 
	positionGridRight.setInitWidths("295");
	positionGridRight.setColAlign("left");
	positionGridRight.setColTypes("ro"); 	
	positionGridRight.setNoHeader(true);
	positionGridRight.setColSorting("position_name_sort");
	positionGridRight.init();
	positionGridRight.sortRows(0,'str',"asc");
	positionGridRight.setSortImgState(true,0,"ASC");	
	positionGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	positionGridRight.attachEvent("onKeyPress",onPositionRightGridKeyPressed);
	positionGridRight.attachEvent("onRowSelect",doOnPositionRightGridRowSelectHandler);
	positionGridRight.attachEvent("onRowDblClicked",doOnPositionRightGridRowDblClicked);
}

function loadGridPosition(defaultValue){
	positionGridLeft.clearAll();
	positionGridLeft.loadXML("reports.do?mode=getPositionsXML", function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, positionGridLeft, positionGridRight);		
	});	
}

function position_name_sort(a,b,order,aId,bId) {
	a0 =positionGridLeft.getUserData(aId,"position");
	b0 = positionGridLeft.getUserData(bId,"position");	
	return sort_data(a0,b0,order);
}

function onPositionLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(positionGridLeft,positionGridRight,4,keyCode,ctrl,shift);
}

function doOnPositionLeftGridRowSelectHandler() {
	positionGridRight.clearSelection();
}

function doOnPositionLeftGridRowDblClicked() {	
	var text = (positionGridLeft.cells(positionGridLeft.getSelectedId(),0)).getValue();
	selectItem(positionGridLeft,positionGridRight);	
	
}

//for right Position grid
function onPositionRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(positionGridRight,positionGridLeft,4,keyCode,ctrl,shift);
}

function doOnPositionRightGridRowSelectHandler() {
	positionGridLeft.clearSelection();
}

function doOnPositionRightGridRowDblClicked() {	
	var text = (positionGridRight.cells(positionGridRight.getSelectedId(),0)).getValue();
	selectItem(positionGridRight,positionGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isPositionGridInitialised(){
	return (positionGridLeft!=null);
}

function validateAndGetPositionFilterValue(){
	return positionGridRight.getAllItemIds(',');	
}

</script>