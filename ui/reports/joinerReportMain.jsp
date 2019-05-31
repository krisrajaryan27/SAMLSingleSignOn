<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
								com.talentPool.reports.form.ReportForm, 
								com.talentPool.user.manager.ModuleSet,
								com.talentPool.reports.ReportUtils"%>	
<%@page import="com.talentPool.common.utils.Utils"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/dropdiv.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
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
<script src="js/cookies.js"></script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName" name="reportForm" value='<%=(String) request.getAttribute("reportName")%>'/>
	<html:hidden property="departmentId" name="reportForm"/>
	<html:hidden property="positionId" name="reportForm"/>
	<html:hidden property="mode" value="joinerReport"/>
	<html:hidden property="filterId"/>
	<html:hidden property="fieldIds"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="positionFilter" name="reportForm"/>
	<html:hidden property="departmentFilter" name="reportForm"/>
	
   	<table  border="0" cellspacing="0" cellpadding="0" style="width:500px;"> 
	  <tr> 
	    <td><div style="width: 200px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.joiner_report_upcoming" /></div></td> 
	  </tr> 
	</table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
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
				   		<td>
						   	<table cellspacing="0" cellpadding="0" border="0" >	
						   		<tr>
						   			<td  class="label">
				                   		<bean:message key="report.label.date_range" /> :&nbsp;
				                   	</td>
				                   	<td>	
					                   <script type="text/javascript">
							                    var optDate = <%=ReportUtils.getJSArrayForDateRangeOfFutureDates()%>;
							                    selectDateRange = new SelectBox(optDate,'-1','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
							                    document.write(selectDateRange.getHtml());
							                    selectDateRange.setOnChangeHandler('onDateRangeChange');
							                    selectDateRange.init();
					                    </script>
							   		</td>
							   		<td>
										<div id="divSelectNumRange" style="display:none;">
										 <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
									     <tr>
									     	<td style="width: 20px;"></td>
									       	<td><bean:message key="report.label.number" />=<html:text property="numberRange" styleId="numberRange" size="4" maxlength="2"/>
											</td>
											<td></td>
											<td></td>
						  		      	 </tr>
					 			   		 </table>
										</div>							
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
					  		          			  	<html:text property="toDate" styleId="toDate" value="" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
						  		      			</td>
						  		      		</tr>
					 			   		 </table>
										</div>
									</td>
								</tr>
							</table>			
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
	<br>
	<br>
   <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>
	   	<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>	
		

   <br>
	</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

/**
 * Select Fields Grid Related Functions 
 */
var fieldsGrid = null;
var selectedFieldsGrid = null;
var newDate=null;
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
	var vals = readCookie('<bean:message key="report.label.joiner_report_upcoming"/>');
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

function validateSetFormFields(){
	var val=selectDateRange.getSelectedId();
	
	if(val=='<%=ReportConstants.CUSTOM%>'){
		var fromdate= document.getElementById("fromDate").value.split("/");
		var todate= document.getElementById("toDate").value.split("/");
		var date1 = new Date();
		var date2 = new Date();
		date1.setDate(fromdate[0]);
		date1.setMonth(fromdate[1]-1);
		date1.setFullYear(fromdate[2]);
		date2.setDate(todate[0]);
		date2.setMonth(todate[1]-1);
		date2.setFullYear(todate[2]);
		if(date2 <= date1){
			 alert('<bean:message key="calendar.alert.toandfromdateSelection"/>');
			 document.getElementById("toDate").value="";
			 document.getElementById("toDate").focus();
			 return false;
		}
		if(date1 < new Date()){
			alert('<bean:message key="calendar.alert.fromLessThanToday"/>');
			 document.getElementById("fromDate").value=newDate;
			 document.getElementById("fromDate").focus();
			 return false;
		}
	}
	if(!validatePositionFilter()){
		return false;
	}
	if(!setSelectedFields())
		return false;
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	return true;
}

function setSelectedFields(){
	var selectedFields = selectedFieldsGrid.getAllItemIds(',');
	var arr =  selectedFields.split(",");
	var selectedFieldNames='';
	
	for(var i=0;i<arr.length;i++){
		selectedFieldNames = selectedFieldNames + selectedFieldsGrid.getUserData(arr[i],"key")+",";
	}
	document.reportForm.fieldIds.value=selectedFieldNames;
	
	return true;
}

function submitForm(){
	if( validateSetFormFields()){
		createCookie('<bean:message key="report.label.joiner_report_upcoming"/>', selectedFieldsGrid.getAllItemIds(','), 100);
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
		var date = new Date();
		var dateParts = date.toJSON().slice(0,10).split("-");
		newDate = dateParts[2]+"/"+dateParts[1]+"/"+dateParts[0];
		document.getElementById("fromDate").value = newDate ;
		document.getElementById("toDate").value = "" ;
		$("divSelectDateRange").style.display="block";
		$("divSelectNumRange").style.display="none";
	}else if(val=='<%=ReportConstants.NEXT_N_DAYS%>' || val=='<%=ReportConstants.NEXT_N_WEEKS%>'){
		$("divSelectNumRange").style.display="block";
		$("divSelectDateRange").style.display="none";
	}else {
		$("divSelectNumRange").style.display="none";
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