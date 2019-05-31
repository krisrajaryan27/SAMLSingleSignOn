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
<div id="divSelectSpecificStage">	
<table class="innerReport">
	<tr>
		<td class="labelTop">
			<s:text name="common.stage" /> 
		</td>
		<td>
			<div class="leftGrid" id="STAGE_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>		
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(stageGridLeft,stageGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(stageGridRight,stageGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td>
			<div class="rightGrid" id="STAGE_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>
		</td>
	</tr>
</table>
</div>
<script>
var stageGridLeft;
var stageGridRight;

function initStageGrids(defaultValue) {
	intiStageGridLeft();
	initStageGridRight();
	loadGridStage(defaultValue);
}

//GRID For Stage
function intiStageGridLeft() {
	stageGridLeft = new dhtmlXGridObject('STAGE_GRID_LEFT'); 
	stageGridLeft.imgURL = "images/"; 
	stageGridLeft.setHeader("stage"); 
	stageGridLeft.setInitWidths("295");
	stageGridLeft.setNoHeader(true);
	stageGridLeft.setColAlign("left");
	stageGridLeft.setColTypes("ro");
	stageGridLeft.setColSorting("stage_name_sort");
	stageGridLeft.init();
	stageGridLeft.setSortImgState(true,0,"ASC");	
	stageGridLeft.attachEvent("onKeyPress",onStageLeftGridKeyPressed);
	stageGridLeft.attachEvent("onRowSelect",doOnStageLeftGridRowSelectHandler);
	stageGridLeft.attachEvent("onRowDblClicked",doOnStageLeftGridRowDblClicked);
	
	stageGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initStageGridRight(){
	stageGridRight = new dhtmlXGridObject('STAGE_GRID_RIGHT'); 
	stageGridRight.imgURL = "images/"; 
	stageGridRight.setHeader("stage"); 
	stageGridRight.setInitWidths("295");
	stageGridRight.setColAlign("left");
	stageGridRight.setColTypes("ro"); 	
	stageGridRight.setNoHeader(true);
	stageGridRight.setColSorting("stage_name_sort");
	stageGridRight.init();
	stageGridRight.sortRows(0,'str',"asc");
	stageGridRight.setSortImgState(true,0,"ASC");	
	stageGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	stageGridRight.attachEvent("onKeyPress",onStageRightGridKeyPressed);
	stageGridRight.attachEvent("onRowSelect",doOnStageRightGridRowSelectHandler);
	stageGridRight.attachEvent("onRowDblClicked",doOnStageRightGridRowDblClicked);	
}

function stage_name_sort(a,b,order,aId,bId) {
	a0 =stageGridLeft.getUserData(aId,"stage");
	b0 = stageGridLeft.getUserData(bId,"stage");	
	return sort_data(a0,b0,order);
}

function loadGridStage(defaultValue){	
	stageGridLeft.clearAll();
	stageGridLeft.loadXML("reports.do?mode=getStageXML", function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, stageGridLeft, stageGridRight);
	});	
}

function onStageLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(stageGridLeft,stageGridRight,4,keyCode,ctrl,shift);
}

function doOnStageLeftGridRowSelectHandler() {
	stageGridRight.clearSelection();
}

function doOnStageLeftGridRowDblClicked() {	
	var text = (stageGridLeft.cells(stageGridLeft.getSelectedId(),0)).getValue();
	selectItem(stageGridLeft,stageGridRight);	
	
}

//for right Stage grid
function onStageRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(stageGridRight,stageGridLeft,4,keyCode,ctrl,shift);
}

function doOnStageRightGridRowSelectHandler() {
	stageGridLeft.clearSelection();
}

function doOnStageRightGridRowDblClicked() {	
	var text = (stageGridRight.cells(stageGridRight.getSelectedId(),0)).getValue();
	selectItem(stageGridRight,stageGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isStageGridInitialised(){
	return stageGridLeft!=null;
}

function validateAndGetStageFilterValue(){	
	return stageGridRight.getAllItemIds(',');
}
</script>