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
<div id="divSelectSpecificUser" >	
	<table class="innerReport">
		<tr>
			<td class="labelTop">
				<s:text name="common.user" /> 
			</td>
			<td>
				<div class="leftGrid" id="USER_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>
			</td>
			<td class="arrows" align="center">																												
				<a href="#" class="active" onclick="javascript: selectItem(userGridLeft,userGridRight);return false;" title="Add" >					
				<img src="images/ico_rightarrow.gif"  border="0" /></a>
				<br/> 
				<a href="#" class="active" onclick="javascript: deselectItem(userGridRight,userGridLeft);return false;" title="Remove" >					
				<img src="images/ico_leftarrow.gif"  border="0" /></a>
			</td>
			<td>
				<div class="rightGrid" id="USER_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>
			</td>
		</tr>
	</table>
</div>
<script>
var userGridLeft;
var userGridRight;
function initUsersGrids(defaultValue){
	initUserGridLeft();
	initUserGridRight();
	loadGridUser(defaultValue);
}

//GRID For User
function initUserGridLeft() {
	userGridLeft = new dhtmlXGridObject('USER_GRID_LEFT'); 
	userGridLeft.imgURL = "images/"; 
	userGridLeft.setHeader("user"); 
	userGridLeft.setInitWidths("295");
	userGridLeft.setNoHeader(true);
	userGridLeft.setColAlign("left");
	userGridLeft.setColTypes("ro");
	userGridLeft.setColSorting("user_name_sort");
	userGridLeft.init();
	userGridLeft.setSortImgState(true,0,"ASC");	
	userGridLeft.attachEvent("onKeyPress",onUserLeftGridKeyPressed);
	userGridLeft.attachEvent("onRowSelect",doOnUserLeftGridRowSelectHandler);
	userGridLeft.attachEvent("onRowDblClicked",doOnUserLeftGridRowDblClicked);
	
	userGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initUserGridRight(){
	userGridRight = new dhtmlXGridObject('USER_GRID_RIGHT'); 
	userGridRight.imgURL = "images/"; 
	userGridRight.setHeader("user"); 
	userGridRight.setInitWidths("295");
	userGridRight.setColAlign("left");
	userGridRight.setColTypes("ro"); 	
	userGridRight.setNoHeader(true);
	userGridRight.setColSorting("user_name_sort");
	userGridRight.init();
	userGridRight.sortRows(0,'str',"asc");
	userGridRight.setSortImgState(true,0,"ASC");	
	userGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	userGridRight.attachEvent("onKeyPress",onUserRightGridKeyPressed);
	userGridRight.attachEvent("onRowSelect",doOnUserRightGridRowSelectHandler);
	userGridRight.attachEvent("onRowDblClicked",doOnUserRightGridRowDblClicked);	
}

function user_name_sort(a,b,order,aId,bId) {
	a0 =userGridLeft.getUserData(aId,"user");
	b0 = userGridLeft.getUserData(bId,"user");	
	return sort_data(a0,b0,order);
}

function loadGridUser(defaultValue){	
	userGridLeft.clearAll();
	userGridLeft.loadXML("reports.do?mode=XMLRolewiseUsers",function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, userGridLeft, userGridRight);
	});	
}

function onUserLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(userGridLeft,userGridRight,4,keyCode,ctrl,shift);
}

function doOnUserLeftGridRowSelectHandler() {
	userGridRight.clearSelection();
}

function doOnUserLeftGridRowDblClicked() {	
	var text = (userGridLeft.cells(userGridLeft.getSelectedId(),0)).getValue();
	selectItem(userGridLeft,userGridRight);	
	
}

//for right User grid
function onUserRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(userGridRight,userGridLeft,4,keyCode,ctrl,shift);
}

function doOnUserRightGridRowSelectHandler() {
	userGridLeft.clearSelection();
}

function doOnUserRightGridRowDblClicked() {	
	var text = (userGridRight.cells(userGridRight.getSelectedId(),0)).getValue();
	selectItem(userGridRight,userGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isUsersGridInitialised(){
	return userGridLeft!=null;
}

function validateAndGetUserFilterValue(){	
	return userGridRight.getAllItemIds(',');
}

</script>