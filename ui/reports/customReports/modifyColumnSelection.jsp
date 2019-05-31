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
	<s:form method="POST" action="customizeReport">
		<s:hidden name="selectedColumns" />
		<s:hidden name="stepTypes" />
		<s:hidden name="reportId" />
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
					<table class="innerCustomReport" cellspacing="0" cellpadding="0"
						border="0">
						<tr>
							<td><jsp:include page="positionColumnsGrid.jsp" /></td>
						</tr>
						<tr>
							<td><jsp:include page="candidateColumnsGrid.jsp" /></td>
						</tr>
						<tr>
							<td><jsp:include page="activityColumnsGrid.jsp" /></td>
						</tr>
						<tr>
							<td><jsp:include page="processColumnsGrid.jsp" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
     	<table class="tblPop" width="100%">
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><s:label key="common.next"/></a>
						<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:label key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>
	</s:form>
</div>
<script type="text/javascript"> 
function initAllColumns() {
	initAvailablePositionColumns();
	initAvailableCandidateColumns();
	initAvailableActivityColumns();
	initAvailableProcessColumns();
	
	initSelectedPositionColumns();
	initSelectedCandidateColumns();
	initSelectedActivityColumns();
	initSelectedProcessColumns();
	
	var columns = '<s:property value="selectedColumns" />';
	if(columns!='' && columns!=null){
		checkSelectedColumns(columns);	
	}	
}

function checkSelectedColumns(columns) {
	selectItems(columns,availablePositionColumns,selectedPositionColumns);
	selectItems(columns,availableCandidateColumns,selectedCandidateColumns);
	selectItems(columns,availableActivityColumns,selectedActivityColumns);
	selectItems(columns,availableProcessColumns,selectedProcessColumns);

	var steps = '<%=request.getAttribute("steps")%>';
	if(steps!='' && steps!=null){
		checkSubSteps(steps);	
	}
}

function checkSubSteps(steps) {
	if(steps.indexOf("IN") != -1) {
		toggleCheckbox('INstep');
	}
	if(steps.indexOf("OUT") != -1) {
		toggleCheckbox('OUTstep');
	}
	if(steps.indexOf("Reject") != -1) {
		toggleCheckbox('Rejectstep');
	}
	if(steps.indexOf("Inprocess") != -1) {
		toggleCheckbox('Processstep');
	}
}

function nextPage(){
	var selectedPositionColumnIds = selectedPositionColumns.getAllItemIds(',');
	var selectedCandidateColumnIds = selectedCandidateColumns.getAllItemIds(',');
	var selectedActivityColumnIds = selectedActivityColumns.getAllItemIds(',');
	var selectedProcessColumnIds = selectedProcessColumns.getAllItemIds(',');
	
	var allSelectedColumns = '';
	
	if(selectedPositionColumnIds!='' && selectedPositionColumnIds!=null) {
		allSelectedColumns = selectedPositionColumnIds;
	}
	
	if(selectedCandidateColumnIds!='' && selectedCandidateColumnIds!=null) {
		if(allSelectedColumns!='') {			
			allSelectedColumns = allSelectedColumns + ',' + selectedCandidateColumnIds;
		} else {
			allSelectedColumns = selectedCandidateColumnIds;
		}
	}
	
	if(selectedActivityColumnIds!='' && selectedActivityColumnIds!=null) {
		if(allSelectedColumns!='') {			
			allSelectedColumns = allSelectedColumns + ',' + selectedActivityColumnIds;
		} else {
			allSelectedColumns = selectedActivityColumnIds;
		}
	}
	
	if(selectedProcessColumnIds!='' && selectedProcessColumnIds!=null) {
		if(allSelectedColumns!='') {			
			allSelectedColumns = allSelectedColumns + ',' + selectedProcessColumnIds;
		} else {
			allSelectedColumns = selectedProcessColumnIds;
		}
	}
	
	var img = document.getElementById('INstep');
	var steps = '';
	if (img.src.indexOf(chkboxchked) != -1)
		steps = steps + 'IN';
	
	img = document.getElementById('OUTstep');
	if (img.src.indexOf(chkboxchked) != -1)
		steps = steps + 'OUT';
		
	img = document.getElementById('Rejectstep');
	if (img.src.indexOf(chkboxchked) != -1)
		steps = steps + 'Reject';
		
	img = document.getElementById('Processstep');
	if (img.src.indexOf(chkboxchked) != -1)
		steps = steps + 'Inprocess';
		
	document.customizeReport.selectedColumns.value=allSelectedColumns;
	document.customizeReport.stepTypes.value=steps;
	document.customizeReport.action="saveModifiedColumns.action";
	document.customizeReport.submit();
}

function onCancel(){
	var id = '<s:property value="reportId" />';
	window.location.href="customizeReport.action?reportId=" + id;
}

function doOnLoad() {
	initAllColumns();
}
window.onload = doOnLoad;
</script>