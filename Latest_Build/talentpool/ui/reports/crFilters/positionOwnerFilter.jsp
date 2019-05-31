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
<div id="divSelectSpecificPositionOwner" style="display:block;">	
<table class="innerReport">
	<tr>
		<td class="labelTop">
		 	<s:text name="global.position_owner" />
		</td>
		<td>
			<div class="leftGrid" id="POSITION_OWNER_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>		
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(positionOwnerGridLeft,positionOwnerGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(positionOwnerGridRight,positionOwnerGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td>		
			<div class="rightGrid" id="POSITION_OWNER_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>		
		</td>
	</tr>
</table>
</div>

<script>
var positionOwnerGridLeft;
var positionOwnerGridRight;

function initPositionOwnerGrids(defaultValue){
	initPositionOwnerGridLeft();
	initPositionOwnerGridRight();
	loadGridPositionOwner(defaultValue);
}

//GRID For PositionOwner
function initPositionOwnerGridLeft(){
	positionOwnerGridLeft = new dhtmlXGridObject('POSITION_OWNER_GRID_LEFT'); 
	positionOwnerGridLeft.imgURL = "images/"; 
	positionOwnerGridLeft.setHeader("positionOwner"); 
	positionOwnerGridLeft.setInitWidths("295");
	positionOwnerGridLeft.setNoHeader(true);
	positionOwnerGridLeft.setColAlign("left");
	positionOwnerGridLeft.setColTypes("ro");
	positionOwnerGridLeft.setColSorting("positionOwner_name_sort");
	positionOwnerGridLeft.init();
	positionOwnerGridLeft.setSortImgState(true,0,"ASC");	
	positionOwnerGridLeft.attachEvent("onKeyPress",onPositionOwnerLeftGridKeyPressed);
	positionOwnerGridLeft.attachEvent("onRowSelect",doOnPositionOwnerLeftGridRowSelectHandler);
	positionOwnerGridLeft.attachEvent("onRowDblClicked",doOnPositionOwnerLeftGridRowDblClicked);
	
	positionOwnerGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initPositionOwnerGridRight(){
	positionOwnerGridRight = new dhtmlXGridObject('POSITION_OWNER_GRID_RIGHT'); 
	positionOwnerGridRight.imgURL = "images/"; 
	positionOwnerGridRight.setHeader("positionOwner"); 
	positionOwnerGridRight.setInitWidths("295");
	positionOwnerGridRight.setColAlign("left");
	positionOwnerGridRight.setColTypes("ro"); 	
	positionOwnerGridRight.setNoHeader(true);
	positionOwnerGridRight.setColSorting("positionOwner_name_sort");
	positionOwnerGridRight.init();
	positionOwnerGridRight.sortRows(0,'str',"asc");
	positionOwnerGridRight.setSortImgState(true,0,"ASC");	
	positionOwnerGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	positionOwnerGridRight.attachEvent("onKeyPress",onPositionOwnerRightGridKeyPressed);
	positionOwnerGridRight.attachEvent("onRowSelect",doOnPositionOwnerRightGridRowSelectHandler);
	positionOwnerGridRight.attachEvent("onRowDblClicked",doOnPositionOwnerRightGridRowDblClicked);		
}

function loadGridPositionOwner(defaultValue){	
	positionOwnerGridLeft.clearAll();
	positionOwnerGridLeft.loadXML("reports.do?mode=getPositionOwnersXML",function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, positionOwnerGridLeft, positionOwnerGridRight);		
	});	
}


function positionOwner_name_sort(a,b,order,aId,bId) {
	a0 =positionOwnerGridLeft.getUserData(aId,"positionOwner");
	b0 = positionOwnerGridLeft.getUserData(bId,"positionOwner");	
	return sort_data(a0,b0,order);
}

function onPositionOwnerLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(positionOwnerGridLeft,positionOwnerGridRight,4,keyCode,ctrl,shift);
}

function doOnPositionOwnerLeftGridRowSelectHandler() {
	positionOwnerGridRight.clearSelection();
}

function doOnPositionOwnerLeftGridRowDblClicked() {	
	var text = (positionOwnerGridLeft.cells(positionOwnerGridLeft.getSelectedId(),0)).getValue();
	selectItem(positionOwnerGridLeft,positionOwnerGridRight);	
	
}

//for right PositionOwner grid
function onPositionOwnerRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(positionOwnerGridRight,positionOwnerGridLeft,4,keyCode,ctrl,shift);
}

function doOnPositionOwnerRightGridRowSelectHandler() {
	positionOwnerGridLeft.clearSelection();
}

function doOnPositionOwnerRightGridRowDblClicked() {	
	var text = (positionOwnerGridRight.cells(positionOwnerGridRight.getSelectedId(),0)).getValue();
	selectItem(positionOwnerGridRight,positionOwnerGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isPositionOwnerGridInitialised(){
	return positionOwnerGridLeft!=null;
}

function validateAndGetPositionOwnerFilterValue(){	
	return positionOwnerGridRight.getAllItemIds(',');
}


</script>