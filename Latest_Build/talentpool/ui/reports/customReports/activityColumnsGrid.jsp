<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder" width="300px;">
						<div id="AVAILABLE_ACTIVITY_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
		<td width="42px" align="center"><a href="#"
			onclick="javascript: selectItem(availableActivityColumns,selectedActivityColumns);return false;"
			title="<bean:message key='common.add' />"> <img
				src="images/ico_rightarrow.gif" border="0" /> </a> <br /> <a href="#"
			onclick="javascript: deselectItem(selectedActivityColumns,availableActivityColumns);return false;"
			title="<bean:message key='common.remove' />"> <img
				src="images/ico_leftarrow.gif" border="0" /> </a>
		</td>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
						<div id="SELECTED_ACTIVITY_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
var availableActivityColumns 	= null;
var selectedActivityColumns 	= null;

function initAvailableActivityColumns() {	
	availableActivityColumns = new dhtmlXGridObject('AVAILABLE_ACTIVITY_COLUMNS'); 
	availableActivityColumns.imgURL = "images/"; 
	availableActivityColumns.setHeader('<s:label key="custom.report.category_activity" />,'); 
	availableActivityColumns.setInitWidths("300,0");
	availableActivityColumns.setColAlign("left,left");
	availableActivityColumns.setColTypes("ro,ro");
	availableActivityColumns.init();
	loadAvailableActivityColumnsGrid();
	availableActivityColumns.sortRows(0,'str',"asc");
	availableActivityColumns.attachEvent("onKeyPress",onAvailableActivityColumnsKeyPressed);
	availableActivityColumns.attachEvent("onRowSelect",doOnAvailableActivityColumnsGridRowSelectHandler);
	availableActivityColumns.attachEvent("onRowDblClicked",doOnAvailableActivityColumnsGirdRowDblClicked);
	
	availableActivityColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function loadAvailableActivityColumnsGrid(){
	var xmlString = '<%=Utils.escapeJavaScript((String)request.getAttribute("activityTemplateArray"))%>';
	availableActivityColumns.loadXMLString(xmlString);
}

function initSelectedActivityColumns(){
	selectedActivityColumns = new dhtmlXGridObject('SELECTED_ACTIVITY_COLUMNS'); 
	selectedActivityColumns.imgURL = "images/"; 
	selectedActivityColumns.setHeader('<s:label key="custom_report.label.select_columns" />,'); 
	selectedActivityColumns.setInitWidths("300,0");
	selectedActivityColumns.setColAlign("left,left");
	selectedActivityColumns.setColTypes("ro,ro"); 	
	selectedActivityColumns.setNoHeader(true);
	selectedActivityColumns.init();
	selectedActivityColumns.sortRows(0,'str',"asc");
	selectedActivityColumns.setSortImgState(true,0,"ASC");	
	selectedActivityColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	selectedActivityColumns.attachEvent("onKeyPress",onSelectedActivityColumnsKeyPressed);
	selectedActivityColumns.attachEvent("onRowSelect",doOnSelectedActivityColumnsGridRowSelectHandler);
	selectedActivityColumns.attachEvent("onRowDblClicked",doOnSelectedActivityColumnsGridRowDblClicked);
}

function onAvailableActivityColumnsKeyPressed(keyCode,ctrl,shift) {	
	var text = (availableActivityColumns.cells(availableActivityColumns.getSelectedId(),0)).getValue();
	selectedActivityColumns.clearSelection();
	onGridObjKeyPressed(availableActivityColumns,selectedActivityColumns,4,keyCode,ctrl,shift);
}

function onSelectedActivityColumnsKeyPressed(keyCode,ctrl,shift) {
	availableActivityColumns.clearSelection();
	onGridObjKeyPressed(selectedActivityColumns,availableActivityColumns,4,keyCode,ctrl,shift);
}

function doOnAvailableActivityColumnsGridRowSelectHandler() {
	selectedActivityColumns.clearSelection();
}
function doOnSelectedActivityColumnsGridRowSelectHandler() {
	availableActivityColumns.clearSelection();
}

function doOnAvailableActivityColumnsGirdRowDblClicked() {	
	var text = (availableActivityColumns.cells(availableActivityColumns.getSelectedId(),0)).getValue();
	selectItem(availableActivityColumns,selectedActivityColumns);
}

function doOnSelectedActivityColumnsGridRowDblClicked() {	
	selectItem(selectedActivityColumns,availableActivityColumns);
}

</script>