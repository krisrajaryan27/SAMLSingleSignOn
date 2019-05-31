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
<div id="divSelectSpecificSource" >	
<table class="innerReport">
	<tr>
		<td class="labelTop">
			<s:text name="common.source" />  
		</td>
		<td>
			<div class="leftGrid" id="SOURCE_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>				
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(sourceGridLeft,sourceGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(sourceGridRight,sourceGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td >
			<div class="rightGrid" id="SOURCE_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>		
		</td>
	</tr>
</table>
</div>

<script>
var sourceGridLeft;
var sourceGridRight;

function initSourceGrids(defaultValue) {
	initSourceGridLeft();
	initSourceGridRight();
	loadGridSource(defaultValue);
}

//GRID For Source
function initSourceGridLeft() {
	sourceGridLeft = new dhtmlXGridObject('SOURCE_GRID_LEFT'); 
	sourceGridLeft.imgURL = "images/"; 
	sourceGridLeft.setHeader("source"); 
	sourceGridLeft.setInitWidths("295");
	sourceGridLeft.setNoHeader(true);
	sourceGridLeft.setColAlign("left");
	sourceGridLeft.setColTypes("ro");
	sourceGridLeft.setColSorting("source_name_sort");
	//sourceGridLeft.enableMultiselect('true');	
	sourceGridLeft.init();
	//sourceGridLeft.sortRows(0,'str',"asc");
	//sourceGridLeft.enableSmartRendering(true);
	sourceGridLeft.setSortImgState(true,0,"ASC");	
	//sourceGridLeft.setAwaitedRowHeight(20);
	sourceGridLeft.attachEvent("onKeyPress",onSourceLeftGridKeyPressed);
	sourceGridLeft.attachEvent("onRowSelect",doOnSourceLeftGridRowSelectHandler);
	sourceGridLeft.attachEvent("onRowDblClicked",doOnSourceLeftGridRowDblClicked);
	
	sourceGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initSourceGridRight(){
	sourceGridRight = new dhtmlXGridObject('SOURCE_GRID_RIGHT'); 
	sourceGridRight.imgURL = "images/"; 
	sourceGridRight.setHeader("source"); 
	sourceGridRight.setInitWidths("295");
	sourceGridRight.setColAlign("left");
	sourceGridRight.setColTypes("ro"); 	
	//sourceGridRight.enableMultiselect('true');
	sourceGridRight.setNoHeader(true);
	sourceGridRight.setColSorting("source_name_sort");
	sourceGridRight.init();
	sourceGridRight.sortRows(0,'str',"asc");
	sourceGridRight.setSortImgState(true,0,"ASC");	
	sourceGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	sourceGridRight.attachEvent("onKeyPress",onSourceRightGridKeyPressed);
	sourceGridRight.attachEvent("onRowSelect",doOnSourceRightGridRowSelectHandler);
	sourceGridRight.attachEvent("onRowDblClicked",doOnSourceRightGridRowDblClicked);
}

function source_name_sort(a,b,order,aId,bId) {
	a0 =sourceGridLeft.getUserData(aId,"source");
	b0 = sourceGridLeft.getUserData(bId,"source");	
	return sort_data(a0,b0,order);
}

function loadGridSource(defaultValue){	
	sourceGridLeft.clearAll();
	sourceGridLeft.loadXML("reports.do?mode=XMLActiveSources", function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, sourceGridLeft, sourceGridRight);
	});	
}

function onSourceLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceGridLeft,sourceGridRight,4,keyCode,ctrl,shift);
}

function doOnSourceLeftGridRowSelectHandler() {
	sourceGridRight.clearSelection();
}

function doOnSourceLeftGridRowDblClicked() {	
	var text = (sourceGridLeft.cells(sourceGridLeft.getSelectedId(),0)).getValue();
	selectItem(sourceGridLeft,sourceGridRight);	
	
}

//for right Source grid
function onSourceRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceGridRight,sourceGridLeft,4,keyCode,ctrl,shift);
}

function doOnSourceRightGridRowSelectHandler() {
	sourceGridLeft.clearSelection();
}

function doOnSourceRightGridRowDblClicked() {	
	var text = (sourceGridRight.cells(sourceGridRight.getSelectedId(),0)).getValue();
	selectItem(sourceGridRight,sourceGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isSourcesGridInitialised(){
	return sourceGridLeft!=null;
}

function validateAndGetSourceFilterValue(){	
	return sourceGridRight.getAllItemIds(',');
}
</script>