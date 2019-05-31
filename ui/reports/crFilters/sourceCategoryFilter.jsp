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
<div id="divSelectSpecificSourceCategory" style="display:block;">	
<table class="innerReport">
	<tr>
		<td class="labelTop"> 
			<s:text name="common.source_category" /> 
		</td>
		<td>
			<div class="leftGrid" id="SOURCE_CATEGORY_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(sourceCategoryGridLeft,sourceCategoryGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(sourceCategoryGridRight,sourceCategoryGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td>	
			<div class="rightGrid" id="SOURCE_CATEGORY_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>			
		</td>
	</tr>
</table>
</div>

<script>
var sourceCategoryGridLeft;
var sourceCategoryGridRight;

function initSourceCategoryGrids(defaultValue){
	initSourceCategoryGridLeft();
	initSourceCategoryGridRight();
	loadGridSourceCategory(defaultValue);	
}

//GRID For SourceCategory
function initSourceCategoryGridLeft() {
	sourceCategoryGridLeft = new dhtmlXGridObject('SOURCE_CATEGORY_GRID_LEFT'); 
	sourceCategoryGridLeft.imgURL = "images/"; 
	sourceCategoryGridLeft.setHeader("sourceCategory"); 
	sourceCategoryGridLeft.setInitWidths("295");
	sourceCategoryGridLeft.setNoHeader(true);
	sourceCategoryGridLeft.setColAlign("left");
	sourceCategoryGridLeft.setColTypes("ro");
	sourceCategoryGridLeft.setColSorting("sourceCategory_name_sort");
	sourceCategoryGridLeft.init();
	sourceCategoryGridLeft.setSortImgState(true,0,"ASC");	
	sourceCategoryGridLeft.attachEvent("onKeyPress",onSourceCategoryLeftGridKeyPressed);
	sourceCategoryGridLeft.attachEvent("onRowSelect",doOnSourceCategoryLeftGridRowSelectHandler);
	sourceCategoryGridLeft.attachEvent("onRowDblClicked",doOnSourceCategoryLeftGridRowDblClicked);
	
	sourceCategoryGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initSourceCategoryGridRight(){
	sourceCategoryGridRight = new dhtmlXGridObject('SOURCE_CATEGORY_GRID_RIGHT'); 
	sourceCategoryGridRight.imgURL = "images/"; 
	sourceCategoryGridRight.setHeader("sourceCategory"); 
	sourceCategoryGridRight.setInitWidths("295");
	sourceCategoryGridRight.setColAlign("left");
	sourceCategoryGridRight.setColTypes("ro"); 	
	sourceCategoryGridRight.setNoHeader(true);
	sourceCategoryGridRight.setColSorting("sourceCategory_name_sort");
	sourceCategoryGridRight.init();
	sourceCategoryGridRight.sortRows(0,'str',"asc");
	sourceCategoryGridRight.setSortImgState(true,0,"ASC");	
	sourceCategoryGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	sourceCategoryGridRight.attachEvent("onKeyPress",onSourceCategoryRightGridKeyPressed);
	sourceCategoryGridRight.attachEvent("onRowSelect",doOnSourceCategoryRightGridRowSelectHandler);
	sourceCategoryGridRight.attachEvent("onRowDblClicked",doOnSourceCategoryRightGridRowDblClicked);
}

function sourceCategory_name_sort(a,b,order,aId,bId) {
	a0 =sourceCategoryGridLeft.getUserData(aId,"sourceCategory");
	b0 = sourceCategoryGridLeft.getUserData(bId,"sourceCategory");	
	return sort_data(a0,b0,order);
}

function loadGridSourceCategory(defaultValue){	
	sourceCategoryGridLeft.clearAll();
	sourceCategoryGridLeft.loadXML("reports.do?mode=XMLSourceCategories", function (){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, sourceCategoryGridLeft, sourceCategoryGridRight);
	});	
}

function onSourceCategoryLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceCategoryGridLeft,sourceCategoryGridRight,4,keyCode,ctrl,shift);
}

function doOnSourceCategoryLeftGridRowSelectHandler() {
	sourceCategoryGridRight.clearSelection();
}

function doOnSourceCategoryLeftGridRowDblClicked() {	
	var text = (sourceCategoryGridLeft.cells(sourceCategoryGridLeft.getSelectedId(),0)).getValue();
	selectItem(sourceCategoryGridLeft,sourceCategoryGridRight);	
	
}

//for right SourceCategory grid
function onSourceCategoryRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceCategoryGridRight,sourceCategoryGridLeft,4,keyCode,ctrl,shift);
}

function doOnSourceCategoryRightGridRowSelectHandler() {
	sourceCategoryGridLeft.clearSelection();
}

function doOnSourceCategoryRightGridRowDblClicked() {	
	var text = (sourceCategoryGridRight.cells(sourceCategoryGridRight.getSelectedId(),0)).getValue();
	selectItem(sourceCategoryGridRight,sourceCategoryGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}
function isSourceCategoryGridInitialised(){
	return sourceCategoryGridLeft!=null;
}

function validateAndGetSourceCategoryFilterValue(){	
	return sourceCategoryGridRight.getAllItemIds(',');
}
</script>