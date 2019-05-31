<%@page import="com.talentPool.customReports.constants.CustomReportConstants"%>
<%@page import="com.talentPool.customReports.constants.CustomReportColumnConstants"%>
<%@page import="com.talentPool.reports.ReportConstants" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_group.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="JavaScript" src="js/reports/customizeReport.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script type="text/javascript">
var selectListReportTypes;
</script>
<title>Customize Report</title>
<style type="text/css">
div.gridbox table.obj tr.rowselected td.cellselected, div.gridbox table.obj td.cellselected {
	background-color:#FFF1CC;
	color:black;
}
div.gridbox table.obj tr.rowselected td{
	background-color:#FFF1CC;
	color:black;
}
div.gridbox table.obj tr.rowselected A{
	background-color:#FFF1CC;
	color:black;
}
</style> 
</head>
<body>
<s:form name="customizeReport" method="POST">
<%@include file="include/commonReportHiddenFields.jspf" %> 
<s:set name="statusAsOfDate" id="statusAsOfDate" value="@com.talentPool.customReports.constants.CustomReportColumnConstants@STATUS_AS_OF_DATE" />
	<div class="contentDiv" style="border: 1px solid #99CC33;">
		<s:if test="hasActionErrors()">
			<table id="m_errortable">
				<tr>
					<td class="header" style="padding: 4px">
						Error
					</td>
				</tr>  
				<tr>
				   <td style="padding: 2px">
				    	<s:actionerror />
				    </td>
				</tr>  
			</table>
		</s:if>	
		<table class="innerReport">
	<tr>
 	<td class="label"><s:text name="custom_report.customize_report.report_type" />:</td>
	<td>
		<div id="REPORT_TYPE" style="display:block;">			
			<s:property value="reportTypeName"/>
		</div>
		<div id="REPORT_TYPE_SELECT" style="display:none;">
			<script type="text/javascript">					
					var opts = <s:property value="#request.crReportTypesJSArray"/>;					
					if(document.customizeReport.reportTypeId.value!=''){						
						var typKnd=document.customizeReport.reportTypeId.value+"|"+document.customizeReport.reportTypeKindId.value;						
						selectListReportTypes = new SelectBox(opts,typKnd,'images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
					}else{
						selectListReportTypes = new SelectBox(opts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
					}
					document.write(selectListReportTypes.getHtml());
					selectListReportTypes.setOnChangeHandler('onChangeReportTypes');
					selectListReportTypes.init();
				
			</script>
		</div>
	</td>
	</tr>
	</table>		
		<table  style="padding: 5px;" >
		<tr>
			<td>
				<table>
				<tr>
					<td>
						<div id="AVAILABLE_ENTITIES"  style="width:450px; height: 435px; overflow: visible;" ></div>
					</td>
				</tr>
				</table>								
			</td>
			<td>
				<div>
					<div id="GRID_COLUMNS" style="margin-top: 0px;">
						<div id="REPORT_COLUMNS" style="width:450px; height:142px; overflow:visible;" ></div>
					</div>
					<div id="GRID_ROWS" style="display:none; margin-top: 5px;">				
						<div id="REPORT_ROWS"  style="width:450px; height:142px; overflow:visible;" ></div>
					</div>
					<div id="GRID_MEASURES" style="display:none; margin-top: 5px;">
						<div id="REPORT_MEASURES"  style="width:450px;height: 142px; overflow:visible;" ></div>
					</div>			
				</div>															
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="showNamesInReport" style="display: none;" >
					&nbsp;&nbsp;<s:text name="custom_report.message.show_canidate_names" />&nbsp;:&nbsp;<img id="namesInReport" src="images/checkboxunchecked.gif" style="cursor: pointer;" > 
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="showInactivePositionsInReport" style="display: none;" >
					&nbsp;&nbsp;<s:text name="custom_report.message.show_inactive_positions" />&nbsp;:&nbsp;<img id="inactivePositionsInReport" src="images/checkboxunchecked.gif" style="cursor: pointer;" > 
				</div>
			</td>
		</tr>
		</table>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: nextPage();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.next"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
		</div>
	</div>	
</s:form>	
</body>
<script type="text/javascript">
var chkedCheckBox="images/checkboxchecked.gif";
var unChkedCheckBox="images/checkboxunchecked.gif";

var availableEntities 	= new ColumnSelectionGrid('AVAILABLE_ENTITIES','<s:text name="custom_report.header.available_entities" />');
var reportColumns 		= new ColumnSelectionGrid('REPORT_COLUMNS','<s:text name="custom_report.header.columns" />');
var reportRows 			= new ColumnSelectionGrid('REPORT_ROWS','<s:text name="custom_report.header.rows" />');
var reportMeasures 		= new ColumnSelectionGrid('REPORT_MEASURES','<s:text name="custom_report.header.measures" />');

function loadDivOnReprtId(){
	var repKindId = document.customizeReport.reportTypeKindId.value;
	var repTypeId = document.customizeReport.reportTypeId.value;
	if(document.customizeReport.addEditMode.value==<%=CustomReportConstants.CR_ADD%>){				
		$("REPORT_TYPE").style.display="none";
		$("REPORT_TYPE_SELECT").style.display="block";
		if(document.customizeReport.reportTypeId.value!=''){			
			loadAvailableEntitiesForReportTypes(repTypeId);
			showSelectedGrids(repTypeId,repKindId);
		}						
	}else{			
		loadAvailableEntitiesForReportTypes(repTypeId);		
		showSelectedGrids(repTypeId,repKindId);		
	}	
} 


function loadSelectedFields(){	
	loadReportColumns();	
	loadReportRows();
	loadReportMeasures();	
}

function loadReportColumns(){
	selectItems(document.customizeReport.columnIds.value,availableEntities.getCsGrid(),reportColumns.getCsGrid());	
}


function loadReportRows(){
	selectItems(document.customizeReport.rowIds.value,availableEntities.getCsGrid(),reportRows.getCsGrid());	
}

function loadReportMeasures(){
	selectItems(document.customizeReport.measureIds.value,availableEntities.getCsGrid(),reportMeasures.getCsGrid());		
}

function nextPage(){	
	if(validateFields()){			
		document.customizeReport.action="selectFilters.action";		
		document.customizeReport.columnIds.value=reportColumns.getCsGrid().getAllItemIds(',');
		document.customizeReport.rowIds.value=reportRows.getCsGrid().getAllItemIds(',');
		document.customizeReport.measureIds.value=reportMeasures.getCsGrid().getAllItemIds(',');
		setShowCandidateNames();
		setInactivePositions();
		if(document.customizeReport.reportTypeId.value == '4') {
			document.customizeReport.action="selectFilters.action";
		}
		document.customizeReport.submit();
	}
}

function setShowCandidateNames(){	
	var measures = reportMeasures.getCsGrid().getAllItemIds(',');
	if(measures!=null && measures!='' && $('namesInReport').src.indexOf(chkedCheckBox)!=-1){		
		document.customizeReport.showCandidateNames.value=true;
		document.customizeReport.action="candidateNamesSpecification.action";
	}else {		
		document.customizeReport.showCandidateNames.value=false;
		document.customizeReport.action="configureReportTotals.action";
	}
}

function setInactivePositions(){	
	if($(showInactivePositionsInReport).style.display!='none' 
			&& $('inactivePositionsInReport').src.indexOf(chkedCheckBox)!=-1){		
		document.customizeReport.showInactivePositions.value=true;
	}else {		
		document.customizeReport.showInactivePositions.value=false;
	}
}

function validateFields(){	
	var repKindId = document.customizeReport.reportTypeKindId.value;
	var repTypeId = document.customizeReport.reportTypeId.value;
	var addEditMode = document.customizeReport.addEditMode.value;
	var errors='';	
	if(addEditMode==<%=CustomReportConstants.CR_ADD%>){
		var crTypeKind=selectListReportTypes.getSelectedId().split("|");
		if(crTypeKind=='' || crTypeKind=='-1'){
			errors = addError(errors , '<s:text name="custom_report.customize_report.report_type" />');	
		}
	}
	errors = addError(errors , validateEmptyGrid(reportColumns,'<s:text name="custom_report.header.columns" />'));
	if(repKindId==<%=CustomReportConstants.CR_KIND_CROSS_TAB%>){
		errors = addError(errors , validateEmptyGrid(reportRows,'<s:text name="custom_report.header.rows" />'));
		errors = addError(errors , validateEmptyGrid(reportMeasures,'<s:text name="custom_report.header.measures" />'));						
	}else if(repKindId==<%=CustomReportConstants.CR_KIND_GROUP%>){
		errors = addError(errors , validateEmptyGrid(reportRows,'<s:text name="custom_report.header.rows" />'));		
	}	
	if (errors.length > 0) {
		var errs='<s:text name="common.data_required" />';
		errors=addError(errs,errors);
		alert(errors);
		return false;
	}else {
		return validateProcessUser(reportColumns, reportRows, reportMeasures);
	}
	return true;
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function changeCheckBoxState(event){	
	var imgElem = event.element();	
	if(imgElem.src.indexOf(chkedCheckBox)!=-1){
		imgElem.src=unChkedCheckBox;	
	}else if(imgElem.src.indexOf(unChkedCheckBox)!=-1){
		imgElem.src=chkedCheckBox;
	}
}

Event.observe(window, "load", function() {	
	initColumnSelectionGrid(availableEntities);
	initColumnSelectionGrid(reportColumns);
	initColumnSelectionGrid(reportRows);
	initColumnSelectionGrid(reportMeasures);
	loadDivOnReprtId();			
	reportMeasures.getCsGrid().attachEvent('onGridReconstructed',onMeasureGridChanged);
	reportRows.getCsGrid().attachEvent('onGridReconstructed',onRowGridChanged);
	$("namesInReport").observe("click", changeCheckBoxState);
	$("inactivePositionsInReport").observe("click", changeCheckBoxState);
});

function onMeasureGridChanged(){
	showOrHideNamesInStatusReport();
}

function onRowGridChanged(){
	showOrHideInactivePositionsInReport();
}

function showOrHideNamesInStatusReport(){
	var measures = reportMeasures.getCsGrid().getAllItemIds(',');
	if(measures!=null && measures!=''){
		$('showNamesInReport').show();	
		if(document.customizeReport.showCandidateNames.value=='true'){
			$("namesInReport").src=chkedCheckBox;
		}
	}else {
		$('showNamesInReport').hide();
	}
}

function onChangeReportTypes(val){	
	var crTypeKind=selectListReportTypes.getSelectedId().split("|");
	var crTypeId = crTypeKind[0];
	var crKindId = crTypeKind[1];
	document.customizeReport.reportTypeId.value=crTypeId;
	document.customizeReport.reportTypeKindId.value=crKindId;
	showSelectedGrids(crTypeId,crKindId);	
	loadAvailableEntitiesForReportTypes(crTypeId);	
}

function showSelectedGrids(crTypeId,crKindId){
	if(crKindId==<%=CustomReportConstants.CR_KIND_CROSS_TAB%>){
		$("GRID_ROWS").style.display="block";
		$("GRID_MEASURES").style.display="block";		
	}else if(crKindId==<%=CustomReportConstants.CR_KIND_GROUP%>){
		$("GRID_ROWS").style.display="block";
		$("GRID_MEASURES").style.display="none";		
	}else{
		$("GRID_ROWS").style.display="none";
		$("GRID_MEASURES").style.display="none";
	}			
}

function showOrHideInactivePositionsInReport(){
	var rows = reportRows.getCsGrid().getAllItemIds(',');
	if(rows!=null && rows!='' 
			&& (rows.indexOf('<%=CustomReportColumnConstants.POSITION_CODE%>')!=-1 
					|| rows.indexOf('<%=CustomReportColumnConstants.POSITION_TITLE%>')!=-1)){
		$('showInactivePositionsInReport').show();	
		if(document.customizeReport.showInactivePositions.value=='true'){
			$("inactivePositionsInReport").src=chkedCheckBox;
		}
	}else {
		$('showInactivePositionsInReport').hide();
	}
}

function loadAvailableEntitiesForReportTypes(reprtTypId){		
	availableEntities.getCsGrid().clearAll();	
	reportColumns.getCsGrid().clearAll();
	reportRows.getCsGrid().clearAll();
	reportMeasures.getCsGrid().clearAll();	
	availableEntities.getCsGrid().loadXML("getReportColumns.action?reportTypeId="+reprtTypId+"&addEditMode="+document.customizeReport.addEditMode.value);		
}

function validateEmptyGrid(grid,val){ 
	if(grid.getCsGrid().getAllItemIds(',')==''){					
		return val;				
	}else{
		return '';
	}
}

function validateProcessUser(reportColumns, reportRows, reportMeasures){
	if(hasProcessUser(reportColumns, reportRows, reportMeasures) &&
			(hasInProcess(reportColumns, reportRows, reportMeasures) || hasExisting(reportColumns, reportRows, reportMeasures))){
		alert('<s:text name="custom_report.error.inprocess_exisiting_not_applicable" />');
		return false;
	}else{
		return true;
	}
}

function hasProcessUser(reportColumns, reportRows, reportMeasures){
	var columns = reportColumns.getCsGrid().getAllItemIds(',');
	if(columns!=null && columns!='' && (columns.indexOf('<%=CustomReportColumnConstants.PROCESS_USER%>')!=-1)){
		return true;
	}
	var rows = reportRows.getCsGrid().getAllItemIds(',');
	if(rows!=null && rows!='' && (rows.indexOf('<%=CustomReportColumnConstants.PROCESS_USER%>')!=-1)){
		return true;
	}
	var measures = reportMeasures.getCsGrid().getAllItemIds(',');
	if(measures!=null && measures!='' && (measures.indexOf('<%=CustomReportColumnConstants.PROCESS_USER%>')!=-1)){
		return true;
	}
	return false;
}

function hasInProcess(reportColumns, reportRows, reportMeasures){
	var columns = reportColumns.getCsGrid().getAllItemIds(',');
	if(columns!=null && columns!='' && (columns.indexOf('<%=CustomReportColumnConstants.INPROCESS%>')!=-1)){
		return true;
	}
	var rows = reportRows.getCsGrid().getAllItemIds(',');
	if(rows!=null && rows!='' && (rows.indexOf('<%=CustomReportColumnConstants.INPROCESS%>')!=-1)){
		return true;
	}
	var measures = reportMeasures.getCsGrid().getAllItemIds(',');
	if(measures!=null && measures!='' && (measures.indexOf('<%=CustomReportColumnConstants.INPROCESS%>')!=-1)){
		return true;
	}
	return false;
}

function hasExisting(reportColumns, reportRows, reportMeasures){
	var columns = reportColumns.getCsGrid().getAllItemIds(',');
	if(columns!=null && columns!='' && (columns.indexOf('<%=CustomReportColumnConstants.EXISTING%>')!=-1)){
		return true;
	}
	var rows = reportRows.getCsGrid().getAllItemIds(',');
	if(rows!=null && rows!='' && (rows.indexOf('<%=CustomReportColumnConstants.EXISTING%>')!=-1)){
		return true;
	}
	var measures = reportMeasures.getCsGrid().getAllItemIds(',');
	if(measures!=null && measures!='' && (measures.indexOf('<%=CustomReportColumnConstants.EXISTING%>')!=-1)){
		return true;
	}
	return false;
}

function addError(errors, error) {
	if (errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}
</script>
</html>
