<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
				com.talentPool.reports.form.ReportForm, 
				com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
				com.talentPool.positions.dataobject.PositionData,
				com.talentPool.user.dataobject.LoginData,
				com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.reports.ReportVersionConstants"%>
<%@page import="com.talentPool.reports.ReportUtils"%>

<%@page import="com.talentPool.positions.PositionConstants"%>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script src="js/cookies.js"></script>
<script type="text/javascript">
var selectFilter=null;
var opts = new Array();
opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_OPEN_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});

</script>
<style>
.innerReport TD.label{width:135px;}
</style>


<bean:define id="reportForm" name="reportForm" type="com.talentPool.reports.form.ReportForm"></bean:define>
<html:form action="/reports">
<html:hidden property="reportName"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="mode" />
<html:hidden property="filterId"/>
<html:hidden property="fieldIds"/>
<html:hidden property="positionId"/>
<html:hidden property="selectedPositionOwnerIds"/>
<html:hidden property="departmentId"/>
<html:hidden property="subDepartmentId"/>
<html:hidden property="subSubDepartmentId"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="reportFormat" name="reportForm"/>
<html:hidden property="reportTemplateId" name="reportForm"/>
<html:hidden property="selectedUserIds" name="reportForm" />
<html:hidden property="dateRange" name="reportForm"/>
<html:hidden property="stages" name="reportForm"/>
<html:hidden property="positionFilter"/>
<html:hidden property="departmentFilter"/>

<div class="contentDiv">
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  	<tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC">
	    	</span><bean:message key="report.label.datewise_hiring_report" /></div></td> 
		</tr>
   	</table>
   	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
		<td class="head" colspan="2">
			<b><bean:message key="report.label.filter" /></b>
		</td>
	</tr>
	<tr>				  			
		<td>
			<table class="innerReport">
			<tr>
				<td>
					<table>
					<tr>
	   				<td class="label">
			            <bean:message key="report.label.date_range" />:&nbsp;
					</td>
					<td> 
                  	 	<script type="text/javascript">
		                    var optDate = <%=ReportUtils.getJSArrayForDateRange()%>;
		                    selectDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
		                    document.write(selectDateRange.getHtml());
		                    selectDateRange.setOnChangeHandler('onDateRangeChange');
		                    selectDateRange.init();
                  	  </script>
		   			</td>
		   			<td>
						<div id="divSelectDateRange" style="display:none;">
					 		 <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
				      	 		<tr>
				      	 			<td style="width: 20px;"></td>
				      	 			<td>
	  		        		  			<bean:message key="report.label.from" /> :
	  		        					<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
									</td>
									<td style="width: 20px;"></td>
									<td>
		  		          				<bean:message key="report.label.to" /> :
		  		          				<html:text property="toDate" styleId="toDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
	  		      					</td>
	  		      				</tr>
		   						</table>
						</div>
					</td>
				</tr>
					</table>
				</td>
			</tr>
			<tr>
	   			<td>
	   			<%@include file="reportFilters/departmentPositionFilter.jspf" %>
	   			</td>	   			
	   		</tr>
	   		<tr>
	   			<td>
	   			<%@include file="reportFilters/pOwnersFilter.jspf" %>
	   			</td>	   			
	   		</tr>
			<tr>
				<td>
					<table>
					<tr>					
						<td class="label">
		  					<bean:message key="report.label.user_activity.stage" /> :	
		  				</td>
		  				<td>
			  				<img src="images/checkboxchecked.gif" id="requisitionApproval" onclick="javascript: changeStage(this);">&nbsp;Requisition Approval&nbsp;&nbsp;
			  				<img src="images/checkboxchecked.gif" id="shortlist" onclick="javascript: changeStage(this);">&nbsp;<bean:message key="report.label.shortlist"/>&nbsp;&nbsp;
		  					<img src="images/checkboxchecked.gif" id="select" onclick="javascript: changeStage(this);">&nbsp;<bean:message key="report.label.select"/>&nbsp;&nbsp;
		  					<img src="images/checkboxchecked.gif" id="hire" onclick="javascript: changeStage(this);">&nbsp;<bean:message key="report.label.hire"/>&nbsp;&nbsp;
						</td>
					</tr>
					</table>
				</td>
			</tr>	
			</table>
		</td>
	</tr>
	<tr>
		<td class="head" colspan="2">
			<b><bean:message key="report.label.report_format" /></b>
		</td>
	</tr>
	<tr>				  			
		<td>
			<img src="images/checkedradiobutton.gif" name='reportFormat' id='reportFormat_<%=ReportConstants.FORMAT_HTML%>' 
				 onclick="javascript:onFormatChange(this,'<%=ReportConstants.FORMAT_HTML%>');" 
				 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_html"/>&nbsp;&nbsp;
			<img src="images/radiobutton.gif" name='reportFormat' id='reportFormat_<%=ReportConstants.FORMAT_PDF%>' 
				 onclick="javascript:onFormatChange(this,'<%=ReportConstants.FORMAT_PDF%>');" 
				 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_pdf"/>&nbsp;&nbsp;
			<img src="images/radiobutton.gif" name='reportFormat' id='reportFormat_<%=ReportConstants.FORMAT_EXCEL%>' 
				 onclick="javascript:onFormatChange(this,'<%=ReportConstants.FORMAT_EXCEL%>');" 
				 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_excel"/>&nbsp;&nbsp;		
		</td>
	</tr>
	<tr id="fieldsDivHeader">				  			
		<td class="head">
			<b><bean:message key="report.label.select_fields" /></b>
		</td>
	</tr>
	<tr id="fieldsDiv">
		<td>
			<table cellpadding="0" cellspacing="0" class="innerReport">
				<tr>
					<td >
						<table cellpadding="0" cellspacing="0" style="padding-left: 6px;">
							<tr>
								<td class="gridborder">
									<div id="FIELDS_GRID" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td width="34px" align="center">
						<a href="#" onclick="javascript: selectItem(fieldsGrid,selectedFieldsGrid);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
						<a href="#" onclick="javascript: deselectItem(selectedFieldsGrid,fieldsGrid);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
					</td>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td class="gridborder">
									<div id="FIELDS_GRID_SELECTED" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
   	</table>
   	<table cellspacing="0" cellpadding="0" border="0"  width="100%">
	<tr>
		<td>  			
	   		<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
				<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
				<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
			</div>	
		</td>
	</tr>
	</table> 
</div>	
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
</html:form>

<script language="JavaScript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

/**
 * Select Fields Grid Related Functions 
 */
var fieldsGrid = null;
var selectedFieldsGrid = null;
function initFieldsGrid() {	
	fieldsGrid = new dhtmlXGridObject('FIELDS_GRID'); 
	fieldsGrid.imgURL = "images/"; 
	fieldsGrid.setHeader("<bean:message key="common.fields"/>"); 
	fieldsGrid.setInitWidths("200");
	fieldsGrid.setColAlign("left");
	fieldsGrid.setColTypes("ro"); 
	fieldsGrid.setColSorting("str");
	fieldsGrid.enableMultiselect(true);	
	fieldsGrid.attachEvent("onXLE",doOnFieldsGridLoadingEnd);	     
	fieldsGrid.attachEvent("onKeyPress",onFieldsGridKeyPressed);
	fieldsGrid.attachEvent("onRowDblClicked",doOnFieldsGridRowDblClicked);
	fieldsGrid.attachEvent("onRowSelect",doOnFieldsGridRowSelectHandler);
	fieldsGrid.init();
	loadFieldsGrid();
//	fieldsGrid.setSortImgState(true,0,"ASC");
	
	fieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function initSelectedFieldsGrid(){
	selectedFieldsGrid = new dhtmlXGridObject('FIELDS_GRID_SELECTED'); 
	selectedFieldsGrid.imgURL = "images/"; 
	selectedFieldsGrid.setHeader("<bean:message key="common.selected"/> <bean:message key="common.fields"/>"); 
	selectedFieldsGrid.setInitWidths("200");
	selectedFieldsGrid.setColAlign("left");
	selectedFieldsGrid.setColTypes("ro"); 
	selectedFieldsGrid.enableMultiselect(true);	
	selectedFieldsGrid.init();     
	
	selectedFieldsGrid.attachEvent("onKeyPress",onSelectedFieldsGridPressed);
	selectedFieldsGrid.attachEvent("onRowDblClicked",doOnSelectedFieldsGridRowDblClicked);
	selectedFieldsGrid.attachEvent("onRowSelect",doOnSelectedFieldsRowSelectHandler);
	selectedFieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function onFieldsGridKeyPressed(keyCode,ctrl,shift) {
	selectedFieldsGrid.clearSelection();
	onGridObjKeyPressed(fieldsGrid,selectedFieldsGrid,4,keyCode,ctrl,shift);
}

function onSelectedFieldsGridPressed(keyCode,ctrl,shift) {
	fieldsGrid.clearSelection();
	onGridObjKeyPressed(selectedFieldsGrid,fieldsGrid,4,keyCode,ctrl,shift);
}

function doOnFieldsGridRowDblClicked() {
	selectItem(fieldsGrid,selectedFieldsGrid);
}
function doOnSelectedFieldsGridRowDblClicked() {
	deselectItem(selectedFieldsGrid,fieldsGrid);
}

function doOnFieldsGridRowSelectHandler() {
	selectedFieldsGrid.clearSelection();
}

function doOnSelectedFieldsRowSelectHandler() {
	fieldsGrid.clearSelection();
}

function loadFieldsGrid(){
	fieldsGrid.clearAll();
	fieldsGrid.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("fieldsXml"))%>');
}

function setSelectedFromCookie(){
	var vals = readCookie('<bean:message key="report.label.datewise_hiring_report"/>'+'_DETAILS');
	if(vals!='' && vals!=null){
		var arrVals = vals.split(',');
		for(var x=0;x<arrVals.length;x++){
			fieldsGrid.setSelectedRow(arrVals[x],true,false,false);
		}
		selectItem(fieldsGrid,selectedFieldsGrid);
	}
}
function doOnFieldsGridLoadingEnd(){
	setSelectedFromCookie();
}

/**
 * END Select Fields Grid Related Functions 
 */


var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";

var checkedRadioImg = 'images/checkedradiobutton.gif';
var radioImg = 'images/radiobutton.gif';

function onFormatChange(obj,reportFormat){
	var imgs = document.getElementsByName(obj.name);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("reportFormat_") > -1) {
			if( theImage.id == 'reportFormat_'+reportFormat){
				theImage.src = checkedRadioImg;
				document.reportForm.reportFormat.value=reportFormat;
			}else{
				theImage.src = radioImg;
			}
		}
	}
}

function changeStage(obj){
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
	} else {
		obj.src=chkboxchked;
	}
}

function setSelectedStages(){
	var stages = '';
	if($('requisitionApproval').src.indexOf(chkboxchked) != -1) {
		stages = appendToStages('<%=RequisitionConstants.STEP_REQUISITION_APPROVAL%>',stages);
	}
	if($('shortlist').src.indexOf(chkboxchked) != -1) {
		stages = appendToStages('<%=PositionConstants.STEP_LEVEL_SHORTLIST%>',stages);
	}
	if($('select').src.indexOf(chkboxchked) != -1) {
		stages = appendToStages('<%=PositionConstants.STEP_LEVEL_SELECT%>',stages);
	}
	if($('hire').src.indexOf(chkboxchked) != -1) {
		stages = appendToStages('<%=PositionConstants.STEP_LEVEL_ACCEPT%>',stages);
	}
	if(stages==''){
		alert('<bean:message key="report.error.select_stage" />');
		return false;
	}else {
		document.reportForm.stages.value=stages;
	}
	return true;
}

function appendToStages(stage,stages){
	if(stages==''){
		stages=stage;
	}else{
		stages+=','+stage;
	}
	return stages;  
}

function setPositionOwnerFilter(){
	if(selectPositionOwnerFilter && selectPositionOwnerFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_POSITION_OWNER%>"){
		if(selectedPOwnersFilter.getAllItemIds(',')==''){
			alert('<bean:message key="common.please_select" />'+' '+'<bean:message key="global.position_owner" />');
			return false;
		}else{
			document.reportForm.selectedPositionOwnerIds.value=selectedPOwnersFilter.getAllItemIds(',');
		}
	}else {
		document.reportForm.selectedPositionOwnerIds.value='';
	}
	return true;
}

function validateSetFormFields(){
	document.reportForm.positionId.value='';
	document.reportForm.selectedPositionOwnerIds.value='';	
	
	var val = selectFilter.getSelectedId();	
	document.reportForm.filterId.value=val;
	
	if(!validatePositionFilter())
		return false;
	
	if(!setSelectedFields())
		return false;
	
	if(!setPositionOwnerFilter()){
		return false;
	}
	if(!setSelectedStages()){
		return false;
	}		
	
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();

	return true;
}
function onDateRangeChange(val){
	customDisplayHidden(selectDateRange.getSelectedId());
}

function customDisplayHidden(val){
	if(val=='<%=ReportConstants.CUSTOM%>'){
		$("divSelectDateRange").style.display="block";
	} else {
		$("divSelectDateRange").style.display="none";
	}  
}

//DATE FORMATTER CODE AND FUNCTIONS
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
  	  if(!dtf.checkDate(obj)){
  		obj.select();
  		alert('<bean:message key="calendar.error.invalid_date"/>');
  		obj.focus();
  		return false;
  	  }else {
  		return true;
  	  }
	}
	return true;
}

function setSelectedFields(){	
	var selectedFields = selectedFieldsGrid.getAllItemIds(',');
	var arr =  '';
	if(selectedFields.length>0){
		arr =  selectedFields.split(",");	
		var selectedFieldNames='';
		for(var i=0;i<arr.length;i++){
			selectedFieldNames = selectedFieldNames + selectedFieldsGrid.getUserData(arr[i],"key")+",";
		}
		document.reportForm.fieldIds.value=selectedFieldNames;
	}else{
		document.reportForm.fieldIds.value='';
	}
	return true;
}


function submitForm(){
	if(validateSetFormFields()){		
		var d = new Date();
		createCookie('<bean:message key="report.label.datewise_hiring_report"/>'+'_DETAILS', selectedFieldsGrid.getAllItemIds(','), 100);
		document.reportForm.mode.value = 'datewiseHiringReport';
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

function doOnLoad() {	
	initSelectedFieldsGrid();
	initFieldsGrid();
	initPOwnersFilter();
	initSelectedPOwnersFilter();	
	Event.observe($('department'), "keyup", onDepartmentPositionCriteriaChange.bindAsEventListener(this));
	Event.observe($('position'), "keyup", onDepartmentPositionCriteriaChange.bindAsEventListener(this));
	Event.observe($('pOwnersFilter'), "keyup", onPOwnersCriteriaChange.bindAsEventListener(this));
}

window.onload = doOnLoad;

function onChangePositionOwnerFilter(index,obj){
	val = obj.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_ALL%>"){
		hidePOFilter();		
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION_OWNER%>"){
		showPOFilter();
	}
}

function showPOFilter(){
	$("pOwnerFilterDiv").style.display="";
}

function hidePOFilter(){
	$("pOwnerFilterDiv").style.display="none";
}
</script>