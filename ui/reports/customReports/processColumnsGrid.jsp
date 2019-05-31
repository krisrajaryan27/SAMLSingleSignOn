<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder" width="300px;">
						<div id="AVAILABLE_PROCESS_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
		<td width="42px" align="center"><a href="#"
			onclick="javascript: selectItem(availableProcessColumns,selectedProcessColumns);return false;"
			title="<bean:message key='common.add' />"> <img
				src="images/ico_rightarrow.gif" border="0" /> </a> <br /> <a href="#"
			onclick="javascript: deselectItem(selectedProcessColumns,availableProcessColumns);return false;"
			title="<bean:message key='common.remove' />"> <img
				src="images/ico_leftarrow.gif" border="0" /> </a>
		</td>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
						<div id="SELECTED_PROCESS_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan=3>
			<table style="width:100%">
				<tr>
					<td colspan=2>
						For Selected 'Process Steps', include in report : 
					</td>
					<td>&nbsp;IN&nbsp;&nbsp;<img id='INstep' src="images/checkboxunchecked.gif"
						onclick="javascript: toggleCheckbox(id);">
					</td>
					<td>&nbsp;OUT&nbsp;&nbsp;<img id='OUTstep' src="images/checkboxunchecked.gif"
						onclick="javascript: toggleCheckbox(id);">
					</td>
					<td>&nbsp;REJECT&nbsp;&nbsp;<img id='Rejectstep' src="images/checkboxunchecked.gif"
						onclick="javascript: toggleCheckbox(id);">
					</td>
					<td>&nbsp;IN-PROCESS&nbsp;&nbsp;<img id='Processstep' src="images/checkboxunchecked.gif"
						onclick="javascript: toggleCheckbox(id);">
					</td>
				</tr>
			</table>
		</td>
	</tr>	
</table>
<script type="text/javascript">
var availableProcessColumns 	= null;
var selectedProcessColumns 	= null;

function initAvailableProcessColumns() {	
	availableProcessColumns = new dhtmlXGridObject('AVAILABLE_PROCESS_COLUMNS'); 
	availableProcessColumns.imgURL = "images/"; 
	availableProcessColumns.setHeader('<s:label key="custom.report.category_position_steps" />,'); 
	availableProcessColumns.setInitWidths("300,0");
	availableProcessColumns.setColAlign("left,left");
	availableProcessColumns.setColTypes("ro,ro");
	availableProcessColumns.init();
	loadAvailableProcessColumnsGrid();
	availableProcessColumns.sortRows(0,'str',"asc");
	availableProcessColumns.attachEvent("onKeyPress",onAvailableProcessColumnsKeyPressed);
	availableProcessColumns.attachEvent("onRowSelect",doOnAvailableProcessColumnsGridRowSelectHandler);
	availableProcessColumns.attachEvent("onRowDblClicked",doOnAvailableProcessColumnsGirdRowDblClicked);
	
	availableProcessColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function loadAvailableProcessColumnsGrid(){
	var xmlString = '<%=Utils.escapeJavaScript((String)request.getAttribute("processTemplateArray"))%>';
	availableProcessColumns.loadXMLString(xmlString);
}

function initSelectedProcessColumns(){
	selectedProcessColumns = new dhtmlXGridObject('SELECTED_PROCESS_COLUMNS'); 
	selectedProcessColumns.imgURL = "images/"; 
	selectedProcessColumns.setHeader('<s:label key="custom_report.label.select_columns" />,'); 
	selectedProcessColumns.setInitWidths("300,0");
	selectedProcessColumns.setColAlign("left,left");
	selectedProcessColumns.setColTypes("ro,ro"); 	
	selectedProcessColumns.setNoHeader(true);
	selectedProcessColumns.init();
	selectedProcessColumns.sortRows(0,'str',"asc");
	selectedProcessColumns.setSortImgState(true,0,"ASC");	
	selectedProcessColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	selectedProcessColumns.attachEvent("onKeyPress",onSelectedProcessColumnsKeyPressed);
	selectedProcessColumns.attachEvent("onRowSelect",doOnSelectedProcessColumnsGridRowSelectHandler);
	selectedProcessColumns.attachEvent("onRowDblClicked",doOnSelectedProcessColumnsGridRowDblClicked);
}

function onAvailableProcessColumnsKeyPressed(keyCode,ctrl,shift) {	
	var text = (availableProcessColumns.cells(availableProcessColumns.getSelectedId(),0)).getValue();
	selectedProcessColumns.clearSelection();
	onGridObjKeyPressed(availableProcessColumns,selectedProcessColumns,4,keyCode,ctrl,shift);
}

function onSelectedProcessColumnsKeyPressed(keyCode,ctrl,shift) {
	availableProcessColumns.clearSelection();
	onGridObjKeyPressed(selectedProcessColumns,availableProcessColumns,4,keyCode,ctrl,shift);
}

function doOnAvailableProcessColumnsGridRowSelectHandler() {
	selectedProcessColumns.clearSelection();
}
function doOnSelectedProcessColumnsGridRowSelectHandler() {
	availableProcessColumns.clearSelection();
}

function doOnAvailableProcessColumnsGirdRowDblClicked() {	
	var text = (availableProcessColumns.cells(availableProcessColumns.getSelectedId(),0)).getValue();
	selectItem(availableProcessColumns,selectedProcessColumns);
}

function doOnSelectedProcessColumnsGridRowDblClicked() {	
	selectItem(selectedProcessColumns,availableProcessColumns);
}

var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";

function toggleCheckbox(id) {
	var img = document.getElementById(id);
	if (img.src.indexOf(chkboxchked) != -1) {
		img.src=chkboxunchked;
	} else {
		img.src=chkboxchked;
	}
}

</script>