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
<div id="divSelectSpecificPositionStatus">	
<table class="innerReport">
	<tr>
		<td class="labelTop"> 
			<s:text name="common.position_status" />
		</td>
		<td>		
			<div class="leftGrid" id="POSITION_STATUS_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>			
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(positionStatusGridLeft,positionStatusGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(positionStatusGridRight,positionStatusGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td >
			<div class="rightGrid" id="POSITION_STATUS_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>
		</td>
	</tr>
</table>
</div>
<script>
var positionStatusGridLeft;
var positionStatusGridRight;

function initPositionStatusGrids(defaultValue) {
	initPositionStatusGridLeft();
	initPositionStatusGridRight();
	loadGridPositionStatus(defaultValue);	
}

function initPositionStatusGridLeft(){
	positionStatusGridLeft = new dhtmlXGridObject('POSITION_STATUS_GRID_LEFT'); 
	positionStatusGridLeft.imgURL = "images/"; 
	positionStatusGridLeft.setHeader("positionStatus"); 
	positionStatusGridLeft.setInitWidths("295");
	positionStatusGridLeft.setNoHeader(true);
	positionStatusGridLeft.setColAlign("left");
	positionStatusGridLeft.setColTypes("ro");
	positionStatusGridLeft.setColSorting("positionStatus_name_sort");
	positionStatusGridLeft.init();
	loadGridPositionStatus();	
	positionStatusGridLeft.setSortImgState(true,0,"ASC");	
	positionStatusGridLeft.attachEvent("onKeyPress",onPositionStatusLeftGridKeyPressed);
	positionStatusGridLeft.attachEvent("onRowSelect",doOnPositionStatusLeftGridRowSelectHandler);
	positionStatusGridLeft.attachEvent("onRowDblClicked",doOnPositionStatusLeftGridRowDblClicked);
	
	positionStatusGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initPositionStatusGridRight(){
	positionStatusGridRight = new dhtmlXGridObject('POSITION_STATUS_GRID_RIGHT'); 
	positionStatusGridRight.imgURL = "images/"; 
	positionStatusGridRight.setHeader("positionStatus"); 
	positionStatusGridRight.setInitWidths("295");
	positionStatusGridRight.setColAlign("left");
	positionStatusGridRight.setColTypes("ro"); 	
	positionStatusGridRight.setNoHeader(true);
	positionStatusGridRight.setColSorting("positionStatus_name_sort");
	positionStatusGridRight.init();
	positionStatusGridRight.sortRows(0,'str',"asc");
	positionStatusGridRight.setSortImgState(true,0,"ASC");	
	positionStatusGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	positionStatusGridRight.attachEvent("onKeyPress",onPositionStatusRightGridKeyPressed);
	positionStatusGridRight.attachEvent("onRowSelect",doOnPositionStatusRightGridRowSelectHandler);
	positionStatusGridRight.attachEvent("onRowDblClicked",doOnPositionStatusRightGridRowDblClicked);
}

function loadGridPositionStatus(defaultValue){
	positionStatusGridLeft.clearAll();
	positionStatusGridLeft.loadXML("reports.do?mode=getPositionStatusXML", function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, positionStatusGridLeft, positionStatusGridRight);		
	});	
}

function positionStatus_name_sort(a,b,order,aId,bId) {
	a0 =positionStatusGridLeft.getUserData(aId,"positionStatus");
	b0 = positionStatusGridLeft.getUserData(bId,"positionStatus");	
	return sort_data(a0,b0,order);
}

function onPositionStatusLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(positionStatusGridLeft,positionStatusGridRight,4,keyCode,ctrl,shift);
}

function doOnPositionStatusLeftGridRowSelectHandler() {
	positionStatusGridRight.clearSelection();
}

function doOnPositionStatusLeftGridRowDblClicked() {	
	var text = (positionStatusGridLeft.cells(positionStatusGridLeft.getSelectedId(),0)).getValue();
	selectItem(positionStatusGridLeft,positionStatusGridRight);	
	
}

//for right PositionStatus grid
function onPositionStatusRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(positionStatusGridRight,positionStatusGridLeft,4,keyCode,ctrl,shift);
}

function doOnPositionStatusRightGridRowSelectHandler() {
	positionStatusGridLeft.clearSelection();
}

function doOnPositionStatusRightGridRowDblClicked() {	
	var text = (positionStatusGridRight.cells(positionStatusGridRight.getSelectedId(),0)).getValue();
	selectItem(positionStatusGridRight,positionStatusGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}
function validateAndGetPositionStatusFilterValue(){	
	return positionStatusGridRight.getAllItemIds(',');
}

function isPositionStatusGridInitialised(){
	return (positionStatusGridLeft!=null)
}

</script>