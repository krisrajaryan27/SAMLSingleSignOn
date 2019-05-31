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
<div id="divSelectSpecificUserRole" >	
<table class="innerReport">
	<tr>
		<td class="labelTop"> 
			<s:text name="common.roles" />
		</td>
		<td>
			<div class="leftGrid" id="USER_ROLE_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(userRoleGridLeft,userRoleGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(userRoleGridRight,userRoleGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td >
			<div class="rightGrid" id="USER_ROLE_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>
		</td>
	</tr>
</table>
</div>

<script>
var userRoleGridLeft;
var userRoleGridRight;

function initUserRoleGrids(defaultValue){
	intiUserRoleGridLeft();
	initUserRoleGridRight();
	loadGridUserRole(defaultValue);
}

function intiUserRoleGridLeft(){
	userRoleGridLeft = new dhtmlXGridObject('USER_ROLE_GRID_LEFT'); 
	userRoleGridLeft.imgURL = "images/"; 
	userRoleGridLeft.setHeader("userRole"); 
	userRoleGridLeft.setInitWidths("295");
	userRoleGridLeft.setNoHeader(true);
	userRoleGridLeft.setColAlign("left");
	userRoleGridLeft.setColTypes("ro");
	userRoleGridLeft.setColSorting("userRole_name_sort");
	userRoleGridLeft.init();
	userRoleGridLeft.setSortImgState(true,0,"ASC");	
	userRoleGridLeft.attachEvent("onKeyPress",onUserRoleLeftGridKeyPressed);
	userRoleGridLeft.attachEvent("onRowSelect",doOnUserRoleLeftGridRowSelectHandler);
	userRoleGridLeft.attachEvent("onRowDblClicked",doOnUserRoleLeftGridRowDblClicked);
	
	userRoleGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

//GRID For UserRole
function initUserRoleGridRight() {
	userRoleGridRight = new dhtmlXGridObject('USER_ROLE_GRID_RIGHT'); 
	userRoleGridRight.imgURL = "images/"; 
	userRoleGridRight.setHeader("userRole"); 
	userRoleGridRight.setInitWidths("295");
	userRoleGridRight.setColAlign("left");
	userRoleGridRight.setColTypes("ro"); 	
	//userRoleGridRight.enableMultiselect('true');
	userRoleGridRight.setNoHeader(true);
	userRoleGridRight.setColSorting("userRole_name_sort");
	userRoleGridRight.init();
	userRoleGridRight.sortRows(0,'str',"asc");
	userRoleGridRight.setSortImgState(true,0,"ASC");	
	userRoleGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	userRoleGridRight.attachEvent("onKeyPress",onUserRoleRightGridKeyPressed);
	userRoleGridRight.attachEvent("onRowSelect",doOnUserRoleRightGridRowSelectHandler);
	userRoleGridRight.attachEvent("onRowDblClicked",doOnUserRoleRightGridRowDblClicked);		
}

function loadGridUserRole(defaultValue){	
	userRoleGridLeft.clearAll();
	userRoleGridLeft.loadXML("reports.do?mode=XMLUserRoles", function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, userRoleGridLeft, userRoleGridRight);
	});	
}

function userRole_name_sort(a,b,order,aId,bId) {
	a0 =userRoleGridLeft.getUserData(aId,"userRole");
	b0 = userRoleGridLeft.getUserData(bId,"userRole");	
	return sort_data(a0,b0,order);
}

function onUserRoleLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(userRoleGridLeft,userRoleGridRight,4,keyCode,ctrl,shift);
}

function doOnUserRoleLeftGridRowSelectHandler() {
	userRoleGridRight.clearSelection();
}

function doOnUserRoleLeftGridRowDblClicked() {	
	var text = (userRoleGridLeft.cells(userRoleGridLeft.getSelectedId(),0)).getValue();
	selectItem(userRoleGridLeft,userRoleGridRight);	
	
}

//for right UserRole grid
function onUserRoleRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(userRoleGridRight,userRoleGridLeft,4,keyCode,ctrl,shift);
}

function doOnUserRoleRightGridRowSelectHandler() {
	userRoleGridLeft.clearSelection();
}

function doOnUserRoleRightGridRowDblClicked() {	
	var text = (userRoleGridRight.cells(userRoleGridRight.getSelectedId(),0)).getValue();
	selectItem(userRoleGridRight,userRoleGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isUserRoleInitialised(){
	return userRoleGridLeft!=null;
}

function validateAndGetUserRoleFilterValue(){	
	return userRoleGridRight.getAllItemIds(',');
}

</script>