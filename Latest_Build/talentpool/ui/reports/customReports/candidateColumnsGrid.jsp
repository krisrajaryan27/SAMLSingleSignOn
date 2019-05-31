<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<table cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder" width="300px;">
						<div id="AVAILABLE_CANDIDATE_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
		<td width="42px" align="center"><a href="#"
			onclick="javascript: selectItem(availableCandidateColumns,selectedCandidateColumns);return false;"
			title="<bean:message key='common.add' />"> <img
				src="images/ico_rightarrow.gif" border="0" /> </a> <br /> <a href="#"
			onclick="javascript: deselectItem(selectedCandidateColumns,availableCandidateColumns);return false;"
			title="<bean:message key='common.remove' />"> <img
				src="images/ico_leftarrow.gif" border="0" /> </a>
		</td>
		<td>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
						<div id="SELECTED_CANDIDATE_COLUMNS" style="width: 320px; height: 150px;"></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">
var availableCandidateColumns 	= null;
var selectedCandidateColumns 	= null;

function initAvailableCandidateColumns() {	
	availableCandidateColumns = new dhtmlXGridObject('AVAILABLE_CANDIDATE_COLUMNS'); 
	availableCandidateColumns.imgURL = "images/"; 
	availableCandidateColumns.setHeader('<s:label key="custom.report.category_cadidate" />,'); 
	availableCandidateColumns.setInitWidths("300,0");
	availableCandidateColumns.setColAlign("left,left");
	availableCandidateColumns.setColTypes("ro,ro");
	availableCandidateColumns.init();
	loadAvailableCandidateColumnsGrid();
	availableCandidateColumns.sortRows(0,'str',"asc");
	availableCandidateColumns.attachEvent("onKeyPress",onAvailableCandidateColumnsKeyPressed);
	availableCandidateColumns.attachEvent("onRowSelect",doOnAvailableCandidateColumnsGridRowSelectHandler);
	availableCandidateColumns.attachEvent("onRowDblClicked",doOnAvailableCandidateColumnsGirdRowDblClicked);
	
	availableCandidateColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function loadAvailableCandidateColumnsGrid(){
	var xmlString = '<%=Utils.escapeJavaScript((String)request.getAttribute("candidateTemplateArray"))%>';
	availableCandidateColumns.loadXMLString(xmlString);
}

function initSelectedCandidateColumns(){
	selectedCandidateColumns = new dhtmlXGridObject('SELECTED_CANDIDATE_COLUMNS'); 
	selectedCandidateColumns.imgURL = "images/"; 
	selectedCandidateColumns.setHeader('<s:label key="custom_report.label.select_columns" />,'); 
	selectedCandidateColumns.setInitWidths("300,0");
	selectedCandidateColumns.setColAlign("left,left");
	selectedCandidateColumns.setColTypes("ro,ro"); 	
	selectedCandidateColumns.setNoHeader(true);
	selectedCandidateColumns.init();
	selectedCandidateColumns.sortRows(0,'str',"asc");
	selectedCandidateColumns.setSortImgState(true,0,"ASC");	
	selectedCandidateColumns.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	selectedCandidateColumns.attachEvent("onKeyPress",onSelectedCandidateColumnsKeyPressed);
	selectedCandidateColumns.attachEvent("onRowSelect",doOnSelectedCandidateColumnsGridRowSelectHandler);
	selectedCandidateColumns.attachEvent("onRowDblClicked",doOnSelectedCandidateColumnsGridRowDblClicked);
}

function onAvailableCandidateColumnsKeyPressed(keyCode,ctrl,shift) {	
	var text = (availableCandidateColumns.cells(availableCandidateColumns.getSelectedId(),0)).getValue();
	selectedCandidateColumns.clearSelection();
	onGridObjKeyPressed(availableCandidateColumns,selectedCandidateColumns,4,keyCode,ctrl,shift);
}

function onSelectedCandidateColumnsKeyPressed(keyCode,ctrl,shift) {
	availableCandidateColumns.clearSelection();
	onGridObjKeyPressed(selectedCandidateColumns,availableCandidateColumns,4,keyCode,ctrl,shift);
}

function doOnAvailableCandidateColumnsGridRowSelectHandler() {
	selectedCandidateColumns.clearSelection();
}
function doOnSelectedCandidateColumnsGridRowSelectHandler() {
	availableCandidateColumns.clearSelection();
}

function doOnAvailableCandidateColumnsGirdRowDblClicked() {	
	var text = (availableCandidateColumns.cells(availableCandidateColumns.getSelectedId(),0)).getValue();
	selectItem(availableCandidateColumns,selectedCandidateColumns);
}

function doOnSelectedCandidateColumnsGridRowDblClicked() {	
	selectItem(selectedCandidateColumns,availableCandidateColumns);
}

</script>