<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<head>
	<s:head />
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_group.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
</head>
<div class="contentDiv">
	<s:form method="POST" action="saveReportColumns">
		<s:hidden name="reportCategory" />
		<s:hidden name="reportSubCategory" />
		<s:hidden name="selectedColumns" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		  <tr> 
		    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
		    <img src="images/blank_small.gif" align="absmiddle" /><s:label key="custom_report.label.select_columns" /></div></td> 
		  </tr> 
   		</table> 
	   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
			<tr>				  			
				<td class="head">
					<b><s:label key="custom_report.label.select_columns" />:</b>
				</td>
			</tr>
			<tr>
				<td>
					<table class="innerCustomReport" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td style="height: 350px; padding-top: 10px;"  >	
					<table cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td>
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td class="gridborder" width="300px;">
											<div id="AVAILABLE_COLUMNS" style="width:320px; height: 330px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="42px" align="center"><a href="#" onclick="javascript: selectItem(availableColumns,selectedColumns);return false;" title="<bean:message key='common.add' />" >
									<img src="images/ico_rightarrow.gif"  border="0" />
								</a>
								<br/> 
								<a href="#" onclick="javascript: deselectItem(selectedColumns,availableColumns);return false;" title="<bean:message key='common.remove' />" >
									<img src="images/ico_leftarrow.gif"  border="0" />
								</a>  
							</td>
							<td >
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td class="gridborder">
											<div id="SELECTED_COLUMNS" style="width:320px;height: 330px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td style="padding: 10px;" align="center">					
								<s:label key="custom_report.label.select_columns.up" /> <br/>
								<a href="#" onclick="javascript: moveRowUp(selectedColumns);return false;" title="Add" >
									<img src="images/ico_uparrow.gif"  border="0" />
								</a>
								<br/>
								<br/>
								<a href="#" onclick="javascript: moveRowDown(selectedColumns);return false;" title="Remove" >
									<img src="images/ico_downarrow.gif"  border="0" />
								</a> 
								<br/>
								<s:label key="custom_report.label.select_columns.down" />
							</td>			
						</tr>
					</table>
				</td>
			</tr>	
		</table>
				</td>
			</tr>
		</table>
     	<table class="tblPop" width="100%">
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><s:label key="common.back"/></a>
						<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><s:label key="common.next"/></a>
						<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:label key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>
	</s:form>
</div>
<script type="text/javascript"> 
var availableColumns 	= null;
var selectedColumns 	= null;

function initAvailableColumns() {	
	availableColumns = new dhtmlXGridObject('AVAILABLE_COLUMNS'); 
	availableColumns.imgURL = "images/"; 
	availableColumns.setHeader('<s:label key="custom_report.label.select_columns" />,'); 
	availableColumns.setInitWidths("300,0");
	availableColumns.setNoHeader(true);
	availableColumns.setColAlign("left,left");
	availableColumns.setColTypes("ro,ro");
	availableColumns.init();
	loadAvailableColumnsGrid();
	availableColumns.sortRows(0,'str',"asc");
	availableColumns.attachEvent("onKeyPress",onAvailableColumnsKeyPressed);
	availableColumns.attachEvent("onRowSelect",doOnAvailableColumnsGridRowSelectHandler);
	availableColumns.attachEvent("onRowDblClicked",doOnAvailableColumnsGirdRowDblClicked);
	
	availableColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function loadAvailableColumnsGrid(){
	var url = 'getReportColumns.action?';
	var params = 'reportCategory='+document.saveReportColumns.reportCategory.value;
	params+= '&reportSubCategory='+document.saveReportColumns.reportSubCategory.value;
	availableColumns.loadXML(url+params,function() {
		availableColumns.groupBy(1);
		availableColumns.collapseAllGroups();
	});
}

function initSelectedColumns(){
	selectedColumns = new dhtmlXGridObject('SELECTED_COLUMNS'); 
	selectedColumns.imgURL = "images/"; 
	selectedColumns.setHeader('<s:label key="custom_report.label.select_columns" />,'); 
	selectedColumns.setInitWidths("300,0");
	selectedColumns.setColAlign("left,left");
	selectedColumns.setColTypes("ro,ro"); 	
	selectedColumns.setNoHeader(true);
	selectedColumns.init();
	selectedColumns.sortRows(0,'str',"asc");
	selectedColumns.setSortImgState(true,0,"ASC");	
	selectedColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	selectedColumns.attachEvent("onKeyPress",onSelectedColumnsKeyPressed);
	selectedColumns.attachEvent("onRowSelect",doOnSelectedColumnsGridRowSelectHandler);
	selectedColumns.attachEvent("onRowDblClicked",doOnSelectedColumnsGridRowDblClicked);
}


function onAvailableColumnsKeyPressed(keyCode,ctrl,shift) {	
	var text = (availableColumns.cells(availableColumns.getSelectedId(),0)).getValue();
	selectedColumns.clearSelection();
	onGridObjKeyPressed(availableColumns,selectedColumns,4,keyCode,ctrl,shift);
}

function onSelectedColumnsKeyPressed(keyCode,ctrl,shift) {
	availableColumns.clearSelection();
	onGridObjKeyPressed(selectedColumns,availableColumns,4,keyCode,ctrl,shift);
}

function doOnAvailableColumnsGridRowSelectHandler() {
	selectedColumns.clearSelection();
}
function doOnSelectedColumnsGridRowSelectHandler() {
	availableColumns.clearSelection();
}

function doOnAvailableColumnsGirdRowDblClicked() {	
	var text = (availableColumns.cells(availableColumns.getSelectedId(),0)).getValue();
	selectItem(availableColumns,selectedColumns);
}

function doOnSelectedColumnsGridRowDblClicked() {	
	selectItem(selectedColumns,availableColumns);
}

function nextPage(){
	document.saveReportColumns.selectedColumns.value=selectedColumns.getAllItemIds(',');
	document.saveReportColumns.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function doOnLoad() {
	initAvailableColumns();
	initSelectedColumns();
}
window.onload = doOnLoad;
</script>