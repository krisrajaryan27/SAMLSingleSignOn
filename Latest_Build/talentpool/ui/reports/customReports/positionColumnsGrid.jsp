<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder" width="300px;">
						<div id="AVAILABLE_POSITION_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
		<td width="42px" align="center"><a href="#"
			onclick="javascript: selectItem(availablePositionColumns,selectedPositionColumns);return false;"
			title="<bean:message key='common.add' />"> <img
				src="images/ico_rightarrow.gif" border="0" /> </a> <br /> <a href="#"
			onclick="javascript: deselectItem(selectedPositionColumns,availablePositionColumns);return false;"
			title="<bean:message key='common.remove' />"> <img
				src="images/ico_leftarrow.gif" border="0" /> </a>
		</td>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
						<div id="SELECTED_POSITION_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
var availablePositionColumns 	= null;
var selectedPositionColumns 	= null;

function initAvailablePositionColumns() {	
	availablePositionColumns = new dhtmlXGridObject('AVAILABLE_POSITION_COLUMNS'); 
	availablePositionColumns.imgURL = "images/"; 
	availablePositionColumns.setHeader('<s:label key="custom.report.category_position" />,'); 
	availablePositionColumns.setInitWidths("300,0");
	availablePositionColumns.setColAlign("left,left");
	availablePositionColumns.setColTypes("ro,ro");
	availablePositionColumns.init();
	loadAvailablePositionColumnsGrid();
	availablePositionColumns.sortRows(0,'str',"asc");
	availablePositionColumns.attachEvent("onKeyPress",onAvailablePositionColumnsKeyPressed);
	availablePositionColumns.attachEvent("onRowSelect",doOnAvailablePositionColumnsGridRowSelectHandler);
	availablePositionColumns.attachEvent("onRowDblClicked",doOnAvailablePositionColumnsGirdRowDblClicked);
	
	availablePositionColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function loadAvailablePositionColumnsGrid(){
	var xmlString = '<%=Utils.escapeJavaScript((String)request.getAttribute("positionTemplateArray"))%>';
	availablePositionColumns.loadXMLString(xmlString);
}

function initSelectedPositionColumns(){
	selectedPositionColumns = new dhtmlXGridObject('SELECTED_POSITION_COLUMNS'); 
	selectedPositionColumns.imgURL = "images/"; 
	selectedPositionColumns.setHeader('<s:label key="custom_report.label.select_columns" />,'); 
	selectedPositionColumns.setInitWidths("300,0");
	selectedPositionColumns.setColAlign("left,left");
	selectedPositionColumns.setColTypes("ro,ro"); 	
	selectedPositionColumns.setNoHeader(true);
	selectedPositionColumns.init();
	selectedPositionColumns.sortRows(0,'str',"asc");
	selectedPositionColumns.setSortImgState(true,0,"ASC");	
	selectedPositionColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	selectedPositionColumns.attachEvent("onKeyPress",onSelectedPositionColumnsKeyPressed);
	selectedPositionColumns.attachEvent("onRowSelect",doOnSelectedPositionColumnsGridRowSelectHandler);
	selectedPositionColumns.attachEvent("onRowDblClicked",doOnSelectedPositionColumnsGridRowDblClicked);
}

function onAvailablePositionColumnsKeyPressed(keyCode,ctrl,shift) {	
	var text = (availablePositionColumns.cells(availablePositionColumns.getSelectedId(),0)).getValue();
	selectedPositionColumns.clearSelection();
	onGridObjKeyPressed(availablePositionColumns,selectedPositionColumns,4,keyCode,ctrl,shift);
}

function onSelectedPositionColumnsKeyPressed(keyCode,ctrl,shift) {
	availablePositionColumns.clearSelection();
	onGridObjKeyPressed(selectedPositionColumns,availablePositionColumns,4,keyCode,ctrl,shift);
}

function doOnAvailablePositionColumnsGridRowSelectHandler() {
	selectedPositionColumns.clearSelection();
}
function doOnSelectedPositionColumnsGridRowSelectHandler() {
	availablePositionColumns.clearSelection();
}

function doOnAvailablePositionColumnsGirdRowDblClicked() {	
	var text = (availablePositionColumns.cells(availablePositionColumns.getSelectedId(),0)).getValue();
	selectItem(availablePositionColumns,selectedPositionColumns);
}

function doOnSelectedPositionColumnsGridRowDblClicked() {	
	selectItem(selectedPositionColumns,availablePositionColumns);
}

</script>
