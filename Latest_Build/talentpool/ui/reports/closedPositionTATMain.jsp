<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%@ page import="com.talentPool.reports.ReportConstants, 
				com.talentPool.reports.form.ReportForm, 
				com.talentPool.reports.ReportUtils"%>

<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectbox/dropdiv.js" type="text/javascript"></script>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script src="js/calender/CalendarPopup.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js" type="text/javascript"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js" type="text/javascript"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js" type="text/javascript"></script>
<script src="js/tpSelectListFunctions.js" type="text/javascript"></script>
<script src="js/cookies.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">

<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName" name="reportForm" value='<%=(String) request.getAttribute("reportName")%>'/>
	<html:hidden property="departmentId" name="reportForm"/>
	<html:hidden property="positionId" name="reportForm"/>
	<html:hidden property="mode" value="closedPositionTAT"/>
	<html:hidden property="filterId"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="positionFilter" name="reportForm"/>
	<html:hidden property="departmentFilter" name="reportForm"/>
	<html:hidden property="fieldIds"/>
	
   	<table style="width:500px; border-spacing: 0; padding: 0; border: 0"> 
	  <tr> 
	    <td>
	    	<div style="width:170px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    	<bean:message key="report.label.closed_position_tat" /></div>
	    </td> 
	  </tr> 
	</table> 
	<table class="reportHeader" style="width: 100%; border-spacing: 0; padding: 0; border: 1">
		<tr>				  			
			<td class="head">
				<b><bean:message key="report.label.filter" /></b>
			</td>
		</tr>
		<tr>				  			
			<td>
			<table class="innerReport" >
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
					 		<table class="innerReport" style="border-spacing: 0; padding: 0; border: 0" >
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
	   			<td><%@include file="reportFilters/departmentPositionFilter.jspf" %></td>	   			
	   		</tr>
	   		</table>
			</td>
		</tr>
		<tr>				  			
			<td class="head">
				<b><bean:message key="report.label.report_format" /></b>
			</td>
		</tr>
		<tr>				  			
			<td>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_HTML%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.format_html"/></html:radio>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_PDF%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.format_pdf"/></html:radio>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_EXCEL%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.format_excel"/></html:radio>
			</td>
		</tr>
		<tr id="fieldsDivHeader">				  			
			<td class="head">
				<b><bean:message key="report.label.closed_position_tat.select_steps_to_display" /></b>
			</td>
		</tr>
		<tr id="fieldsDiv">
			<td>
			<table style="padding: 0; border-spacing: 0" class="innerReport">
				<tr>
					<td>
						<table style="padding: 0; border-spacing: 0;padding-left: 6px;">
							<tr>
								<td class="gridborder">
									<div id="FIELDS_GRID" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td style="width: 34px; text-align: center;" >
						<a href="#" onclick="javascript: selectItem(fieldsGrid,selectedFieldsGrid);return false;" title="Add" >
							<img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
						<a href="#" onclick="javascript: deselectItem(selectedFieldsGrid,fieldsGrid);return false;" title="Remove" >
							<img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
					</td>
					<td>
						<table style="padding: 0; border-spacing: 0">
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
	<br>
	<br>
   <table style="width:100%; border-spacing: 0; padding: 0; border: 0">
   	<tr>
   		<td>
	   	<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>		
		</td>		
	</tr>
   </table>
   <br>	
   <br>
	</html:form>
</div>

<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>

<script type="text/javascript">

/**
 * Select Fields Grid Related Functions 
 */
var fieldsGrid = null;
var selectedFieldsGrid = null;
function initFieldsGrid() {	
	fieldsGrid = new dhtmlXGridObject('FIELDS_GRID'); 
	fieldsGrid.imgURL = "images/"; 
	fieldsGrid.setHeader("<bean:message key="common.fields"/>");
	fieldsGrid.setNoHeader(true);
	fieldsGrid.setInitWidths("200");
	fieldsGrid.setColAlign("left");
	fieldsGrid.setColTypes("ro"); 
	fieldsGrid.setColSorting("str");
	fieldsGrid.enableMultiselect(true);	
	fieldsGrid.attachEvent("onXLE",doOnFieldsGridLoadingEnd);	     
	fieldsGrid.attachEvent("onKeyPress",onFieldsGridKeyPressed);
	fieldsGrid.attachEvent("onRowDblClicked",doOnFieldsGridRowDblClicked);
	fieldsGrid.attachEvent("onRowSelect",doOnFieldsGridRowSelectHandler);
	fieldsGrid.enableDragAndDrop(true);
	fieldsGrid.init();
	loadFieldsGrid();
	fieldsGrid.setSortImgState(true,0,"ASC");
	
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
	selectedFieldsGrid.setNoHeader(true);
	selectedFieldsGrid.setInitWidths("200");
	selectedFieldsGrid.setColAlign("left");
	selectedFieldsGrid.setColTypes("ro"); 
	selectedFieldsGrid.enableMultiselect(true);
	selectedFieldsGrid.enableDragAndDrop(true);
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
	var vals = readCookie('<bean:message key="report.label.closed_position_tat"/>');
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

function setSelectedFields(){
	var selectedFields = selectedFieldsGrid.getAllItemIds(',');
	var arr =  selectedFields.split(",");
	var selectedFieldNames='';
	
	for(var i=0;i<arr.length;i++){
		selectedFieldNames = selectedFieldNames + selectedFieldsGrid.getUserData(arr[i],"key")+",";
	}
	if(selectedFieldNames=='' || selectedFieldNames==','){
	}else {
		document.reportForm.fieldIds.value=selectedFieldNames;
	}
	return true;
}

/**
 * END Select Fields Grid Related Functions 
 */

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function validateSetFormFields(){
	if(!validatePositionFilter()){
		return false;
	}
	if(!setSelectedFields())
		return false;
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	return true;
}

function submitForm(){	
	if( validateSetFormFields()){
		createCookie('<bean:message key="report.label.closed_position_tat"/>', selectedFieldsGrid.getAllItemIds(','), 100);
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
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

function onDateRangeChange(val){
	customDisplayHidden(selectDateRange.getSelectedId());
}

function customDisplayHidden(val){
	if(val=='<%=ReportConstants.CUSTOM%>'){
		$("divSelectDateRange").style.display="block";			
	}else {
		$("divSelectDateRange").style.display="none";			
	}  
}

window.onload=doOnLoad;
function doOnLoad() {
	initSelectedFieldsGrid();
	initFieldsGrid();	
	
	Event.observe($('department'), "keyup", onDepartmentPositionCriteriaChange.bindAsEventListener(this));
	Event.observe($('position'), "keyup", onDepartmentPositionCriteriaChange.bindAsEventListener(this));	
}
</script>