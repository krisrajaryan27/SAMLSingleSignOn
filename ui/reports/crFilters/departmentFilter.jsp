<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.reports.ReportConstants"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@ page import="com.talentPool.common.properties.GlobalConstants"%>

<script src="js/tpSelectListFunctions.js"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>


<div id="divSelectSpecificDepartment" style="display:block;">	
<table class="innerReport">	
	<tr>
		<td class="label"> 
			<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>
		</td>
		<td>
			<div class="leftGrid" id="DEPARTMENT_GRID_LEFT" style="width:320px; height: 100px; overflow: visible;"></div>				
		</td>
		<td class="arrows" align="center">																												
			<a href="#" class="active" onclick="javascript: selectItem(departmentGridLeft,departmentGridRight);return false;" title="Add" >					
			<img src="images/ico_rightarrow.gif"  border="0" /></a>
			<br/> 
			<a href="#" class="active" onclick="javascript: deselectItem(departmentGridRight,departmentGridLeft);return false;" title="Remove" >					
			<img src="images/ico_leftarrow.gif"  border="0" /></a>
		</td>
		<td >
			<div class="rightGrid" id="DEPARTMENT_GRID_RIGHT" style="width:320px;height: 100px; overflow: visible;"></div>
		</td>
	</tr>
</table>
</div>

<script>
var departmentGridLeft;
var departmentGridRight;

function initDepartmentGrids(defaultValue){
	initDepartmentGridLeft();
	intiDepartmentGridRight();
	loadGridDepartment(defaultValue);
}

//GRID For Department
function initDepartmentGridLeft() {
	departmentGridLeft = new dhtmlXGridObject('DEPARTMENT_GRID_LEFT'); 
	departmentGridLeft.imgURL = "images/"; 
	departmentGridLeft.setHeader("department"); 
	departmentGridLeft.setInitWidths("295");
	departmentGridLeft.setNoHeader(true);
	departmentGridLeft.setColAlign("left");
	departmentGridLeft.setColTypes("ro");
	departmentGridLeft.setColSorting("department_name_sort");
	//departmentGridLeft.enableMultiselect('true');	
	departmentGridLeft.init();
		
	//departmentGridLeft.sortRows(0,'str',"asc");
	//departmentGridLeft.enableSmartRendering(true);
	departmentGridLeft.setSortImgState(true,0,"ASC");	
	//departmentGridLeft.setAwaitedRowHeight(20);
	departmentGridLeft.attachEvent("onKeyPress",onDepartmentLeftGridKeyPressed);
	departmentGridLeft.attachEvent("onRowSelect",doOnDepartmentLeftGridRowSelectHandler);
	departmentGridLeft.attachEvent("onRowDblClicked",doOnDepartmentLeftGridRowDblClicked);
	
	departmentGridLeft.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function intiDepartmentGridRight(){
	departmentGridRight = new dhtmlXGridObject('DEPARTMENT_GRID_RIGHT'); 
	departmentGridRight.imgURL = "images/"; 
	departmentGridRight.setHeader("department"); 
	departmentGridRight.setInitWidths("295");
	departmentGridRight.setColAlign("left");
	departmentGridRight.setColTypes("ro"); 	
	departmentGridRight.setNoHeader(true);
	departmentGridRight.setColSorting("department_name_sort");
	departmentGridRight.init();
	departmentGridRight.sortRows(0,'str',"asc");
	departmentGridRight.setSortImgState(true,0,"ASC");	
	departmentGridRight.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	departmentGridRight.attachEvent("onKeyPress",onDepartmentRightGridKeyPressed);
	departmentGridRight.attachEvent("onRowSelect",doOnDepartmentRightGridRowSelectHandler);
	departmentGridRight.attachEvent("onRowDblClicked",doOnDepartmentRightGridRowDblClicked);	
}

function loadGridDepartment(defaultValue){	
	departmentGridLeft.clearAll();
	departmentGridLeft.loadXML("reports.do?mode=getDepartmentsXML", function(){
		if(defaultValue && defaultValue!=null)
			selectItems(defaultValue, departmentGridLeft, departmentGridRight);
	});	
}

function department_name_sort(a,b,order,aId,bId) {
	a0 =departmentGridLeft.getUserData(aId,"department");
	b0 = departmentGridLeft.getUserData(bId,"department");	
	return sort_data(a0,b0,order);
}

function onDepartmentLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(departmentGridLeft,departmentGridRight,4,keyCode,ctrl,shift);
}

function doOnDepartmentLeftGridRowSelectHandler() {
	departmentGridRight.clearSelection();
}

function doOnDepartmentLeftGridRowDblClicked() {	
	var text = (departmentGridLeft.cells(departmentGridLeft.getSelectedId(),0)).getValue();
	selectItem(departmentGridLeft,departmentGridRight);	
	
}

//for right Department grid
function onDepartmentRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(departmentGridRight,departmentGridLeft,4,keyCode,ctrl,shift);
}

function doOnDepartmentRightGridRowSelectHandler() {
	departmentGridLeft.clearSelection();
}

function doOnDepartmentRightGridRowDblClicked() {	
	var text = (departmentGridRight.cells(departmentGridRight.getSelectedId(),0)).getValue();
	selectItem(departmentGridRight,departmentGridLeft);	
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function isDepartmentGridInitialised(){
	return departmentGridLeft!=null;
}

function validateAndGetDepartmentFilterValue(){
	return departmentGridRight.getAllItemIds(',');
}

</script>