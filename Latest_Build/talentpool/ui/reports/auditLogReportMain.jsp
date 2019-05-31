<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
				com.talentPool.reports.form.ReportForm, 
				com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
				com.talentPool.user.dataobject.LoginData,com.talentPool.common.utils.CommonUtils,
				com.talentPool.positions.dataobject.PositionData,
				com.talentPool.common.properties.TPApplicationProperties,
				com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.audit.constants.AuditConstants"%>
<%@ page import="java.util.ArrayList"%>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/reports.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script type="text/javascript">
var selectFilter=null;
var selectPosition=null;
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
var selectDateRange2=null;
var selectDateRange3=null;
var checkedRadioImg = 'images/checkedradiobutton.gif';
var radioImg = 'images/radiobutton.gif';

</script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
%>

<html:form action="/reports">
<html:hidden property="reportName"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="mode" value="reportAuditTrail"/>
<html:hidden property="filterId"/>
<html:hidden property="positionId"/>
<html:hidden property="departmentId"/>
<html:hidden property="subDepartmentId"/>
<html:hidden property="subSubDepartmentId"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="selectedUserIds"/>
<html:hidden property="dateRange" name="reportForm"/>
<html:hidden property="entityType" name="reportForm"/>
<html:hidden property="auditType" name="reportForm"/>
<html:hidden property="fieldIds"/>
<html:hidden property="reportType"/>
<div class="contentDiv">
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:160px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="audit_trail_report.label.title"/></div></td> 
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
			 <table class="innerReport">
		          <tr>
		   			<td class="label">
	                 	 <bean:message key="report.label.as_of_date" /> :&nbsp;
	                 </td>
	                 <td> 
	                 	 <script type="text/javascript">
		                    var optDate = <%=ReportUtils.getJSArrayForAsOfDate()%>;
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
	  				<tr>				  			
		
			 <table class="innerReport">
		          <tr>
		   			<td class="label">
	                 	 <bean:message key="report.label.audit_entity_type" /> :&nbsp;
	                 </td>
	                 <td> 
	                 	 <script type="text/javascript">
		                    var optDate2 = <%=ReportUtils.getJSArrayForEntityTypes()%>;
		                    selectDateRange2 = new SelectBox(optDate2,'<%=AuditConstants.TYPE_ALL%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
		                    document.write(selectDateRange2.getHtml());
		                    selectDateRange2.init();
	                 	  </script>
		   			</td>
		   			
	  				</tr>
	  		
	     
				
				
							  			
		
			 
		          <tr>
		   			<td class="label">
	                 	 <bean:message key="report.label.audit_audit_type" /> :&nbsp;
	                 </td>
	                 <td> 
	                 	 <script type="text/javascript">
		                    var optDate3 = <%=ReportUtils.getJSArrayForAuditTypes()%>;
		                    selectDateRange3 = new SelectBox(optDate3,'<%=AuditConstants.AUDIT_ALL%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
		                    document.write(selectDateRange3.getHtml());
		                    selectDateRange3.init();
	                 	  </script>
		   			</td>
		   			
	  				</tr>
	  		</table>
	     
				</tr> 
					
					
				</div>
				
			</td>
		</tr>
		<%-- <tr>				  			
			<td class="head">
				<b><bean:message key="report.label.report_type" /></b>
			</td>
		</tr> --%>
		<%-- <tr>				  			
			<td>
				<img src="images/checkedradiobutton.gif" name='reportType' id='reportType_<%=ReportConstants.REPORT_TYPE_SUMMARY%>' 
					onclick="javascript:onReportTypeChange(this,'<%=ReportConstants.REPORT_TYPE_SUMMARY%>');" 
					style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.type_summary"/>&nbsp;&nbsp;
				<img src="images/radiobutton.gif" name='reportType' id='reportType_<%=ReportConstants.REPORT_TYPE_DETAILS%>' 
					onclick="javascript:onReportTypeChange(this,'<%=ReportConstants.REPORT_TYPE_DETAILS%>');" 
					style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.type_details"/>&nbsp;&nbsp;			
			</td>
		</tr> --%>
		<tr id = "optionalFieldsRowHeader" style="display:none;">
			<td class="head">
				<bean:message key="report.label.select_fields" />:
			</td>
		</tr>
		
		<tr id = "optionalFieldsRow" style="display:none;">
		<td> 
			<table class="innerReport" cellspacing="0" cellpadding="0" border="0">						
			<tr>
				<td>			
					<table cellpadding="0" cellspacing="0" >
						<tr>
							<td class="gridborder">
								<div id="GRD1" style="width:225px;height: 150px;overflow: visible;"></div>
							</td>
						</tr>
					</table>
				</td>
				<td width="34px" align="center">
					<a href="#" onclick="javascript: selectItem(dataGrid1,dataGrid2);return false;" title="Add" >
						<img src="images/ico_rightarrow.gif"  border="0" />
					</a>
					<br/>
					<a href="#" onclick="javascript: deselectItem(dataGrid2,dataGrid1);return false;" title="Remove" >
						<img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/>
					</a> 
				</td>
				<td>
					<table cellpadding="0" cellspacing="0">
					<tr>
						<td class="gridborder">
							<div id="GRD2" style="width:225px;height: 150px;overflow: visible;" ></div>
						</td>
					</tr>
					</table>
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
</table>
<br>
 <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>
	   	<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>	
		<%-- <% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		<div id="divSelectScheduleReport" class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:160px;margin-left:10px;" class="active" onclick="javascript: showScheduleReportPopup();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.schedule_this_report"/></a> 
		</div>	
		<% } %> --%>
		</td>
	</tr>
	</table> 
	<br>


<%-- <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   <tr> 
   		<td>
   			<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
				<%@ include file="showReportScheduled.jsp" %>
			<% } %>	
		</td> 	    
   </tr>   
   </table> --%>
</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">


//column select grid
var dataGrid1;
var dataGrid2;

function initOptionalFieldGrids() {	
	dataGrid1 = new dhtmlXGridObject('GRD1'); 
	dataGrid1.imgURL = "images/"; 
	dataGrid1.setHeader("<bean:message key="common.fields"/>"); 
	dataGrid1.setInitWidths("200");
	dataGrid1.setColAlign("left");
	dataGrid1.setColTypes("ro"); 
	dataGrid1.setColSorting("str");
	dataGrid1.setNoHeader(true);
	dataGrid1.enableMultiselect(true);	
	dataGrid1.init();     
	loadDataGrid1();
	
	dataGrid1.setSortImgState(true,0,"ASC");
	dataGrid1.attachEvent("onKeyPress",onGrid1KeyPressed);
	dataGrid1.attachEvent("onRowDblClicked",doOnGrid1RowDblClicked);
	dataGrid1.attachEvent("onRowSelect",doOnDataGrid1RowSelectHandler);
	dataGrid1.attachEvent("onXLE",doOnLoadingEnd);
	
	dataGrid1.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
		
	dataGrid2 = new dhtmlXGridObject('GRD2'); 
	dataGrid2.imgURL = "images/"; 
	dataGrid2.setHeader("<bean:message key="common.selected"/> <bean:message key="common.fields"/>"); 
	dataGrid2.setInitWidths("200");
	dataGrid2.setColAlign("left");
	dataGrid2.setColTypes("ro"); 
	dataGrid2.setColSorting("str");	
	dataGrid2.setNoHeader(true);
	dataGrid2.enableMultiselect(true);	
	dataGrid2.init();     
	
	dataGrid2.setSortImgState(true,0,"ASC");
	dataGrid2.attachEvent("onKeyPress",onGrid2KeyPressed);
	dataGrid2.attachEvent("onRowDblClicked",doOnGrid2RowDblClicked);
	dataGrid2.attachEvent("onRowSelect",doOnDataGrid2RowSelectHandler);
	dataGrid2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	
	/* dataGrid3 = new dhtmlXGridObject('GRD3'); 
	dataGrid3.imgURL = "images/"; 
	dataGrid3.setHeader("<bean:message key="common.selected"/> <bean:message key="common.fields"/>"); 
	dataGrid3.setInitWidths("200");
	dataGrid3.setColAlign("left");
	dataGrid3.setColTypes("ro"); 
	dataGrid3.setColSorting("str");	
	dataGrid3.setNoHeader(true);
	dataGrid3.enableMultiselect(true);	
	dataGrid3.init();     
	
	dataGrid3.setSortImgState(true,0,"ASC");
	dataGrid3.attachEvent("onKeyPress",onGrid2KeyPressed);
	dataGrid3.attachEvent("onRowDblClicked",doOnGrid2RowDblClicked);
	dataGrid3.attachEvent("onRowSelect",doOnDataGrid2RowSelectHandler);
	dataGrid3.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	} */
}
function loadDataGrid1(){
	dataGrid1.clearAll();	
	dataGrid1.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("fieldsXml"))%>');
}

function doOnDataGrid2RowSelectHandler() {
	dataGrid1.clearSelection();
}

function doOnDataGrid1RowSelectHandler() {
	dataGrid2.clearSelection();
}

function doOnGrid1RowDblClicked() {
	selectItem(dataGrid1,dataGrid2);
}
function doOnGrid2RowDblClicked() {
	deselectItem(dataGrid2,dataGrid1);
}

function onGrid1KeyPressed(keyCode,ctrl,shift) {
	dataGrid2.clearSelection();
	onGridObjKeyPressed(dataGrid1,dataGrid2,4,keyCode,ctrl,shift);
}

function onGrid2KeyPressed(keyCode,ctrl,shift) {
	dataGrid.clearSelection();
	onGridObjKeyPressed(dataGrid2,dataGrid1,4,keyCode,ctrl,shift);
}

function doOnLoadingEnd() {
	//setSelectedFromCookie();
}


function onReportTypeChange(obj,reportType){	
	var imgs = document.getElementsByName(obj.name);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("reportType_") > -1) {
			if( theImage.id == 'reportType_'+reportType){
				theImage.src = checkedRadioImg;
				document.reportForm.reportType.value=reportType;
			}else{
				theImage.src = radioImg;
			}
		}
	}

	if(reportType==<%=ReportConstants.REPORT_TYPE_SUMMARY%>){
		$("optionalFieldsRowHeader").style.display="none";		
		$("optionalFieldsRow").style.display="none";
	}else{
		$("optionalFieldsRowHeader").style.display="";
		$("optionalFieldsRow").style.display="";
	}
}



function onReportFormatChange(obj,reportFormat){
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



function setSelectedFields(){
	//if(document.reportForm.reportFormat.value!=preFormattedFormat) {
		var selectedFields = dataGrid2.getAllItemIds(',');
		var arr =  selectedFields.split(",");
		var selectedFieldNames='';
		
		for(var i=0;i<arr.length;i++){
			selectedFieldNames = selectedFieldNames + dataGrid2.getUserData(arr[i],"key")+",";
		}
		if(selectedFieldNames=='' || selectedFieldNames==','){
			alert('<bean:message key="report.error.select_Fields" />');
			return false;
		}else {
			document.reportForm.fieldIds.value=selectedFieldNames;
		}
	//}
	return true;
}



var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function onChangeFilter(val){
	val = selectFilter.getSelectedId();
	var toDate = document.reportForm.toDate.value;
	
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		displayHidden("1");		
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		displayHidden("2");
	}else{
		displayHidden("0");
	}
}
function displayHidden(val){
	if(val=="0"){
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=="1"){
		$("divSelectSpecificPosition").style.display="block";
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=="2"){
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="block";
	} 
}

function validateSetFormFields(){
//first do the validations

<%-- 	if(document.reportForm.reportType.value==<%=ReportConstants.REPORT_TYPE_SUMMARY%>){
		document.reportForm.mode.value = 'reportAuditTrail';
	}if(document.reportForm.reportType.value==<%=ReportConstants.REPORT_TYPE_DETAILS%>){
		document.reportForm.mode.value = 'hiringFunnelDetailReport';
	} --%>

	document.reportForm.positionId.value='';
	document.reportForm.departmentId.value='';
	document.reportForm.subDepartmentId.value='';
	document.reportForm.subSubDepartmentId.value='';
	

	

	if(document.reportForm.reportType.value==<%=ReportConstants.REPORT_TYPE_DETAILS%>){
		if(!setSelectedFields())
			return false;	
	}
		
	

	
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	
	
	return true;
}

function setSelectedFields(){
	var selectedFields = dataGrid2.getAllItemIds(',');
	var arr =  selectedFields.split(",");
	var selectedFieldNames='';
	
	for(var i=0;i<arr.length;i++){
		selectedFieldNames = selectedFieldNames + dataGrid2.getUserData(arr[i],"key")+",";
	}
	if(selectedFieldNames=='' || selectedFieldNames==','){
		alert('<bean:message key="report.error.select_Fields" />');
		return false;
	}else {
		document.reportForm.fieldIds.value=selectedFieldNames;
	}
	return true;
}

function setSelectedUsers(){
	if(selectUserFilter && selectUserFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
		if(selectedUsersFilter){
			if(selectedUsersFilter.getAllItemIds(',')==''){
				alert('<bean:message key="report.error.select_User" />');
				return false;
			}else{
				document.reportForm.selectedUserIds.value=selectedUsersFilter.getAllItemIds(',');
			}
		}
	}else {
		document.reportForm.selectedUserIds.value='';
	}
	return true;
}

function submitForm(){
	if( validateSetFormFields()){
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.entityType.value=selectDateRange2.getSelectedId();
		document.reportForm.auditType.value=selectDateRange3.getSelectedId();
		document.reportForm.submit();
	}
}

function onDateRangeChange(val){
	customDisplayHidden(selectDateRange.getSelectedId());
}

function customDisplayHidden(val){
	if(val=='<%=ReportConstants.CUSTOM%>'){
		$("divSelectDateRange").style.display="block";
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
			$("divSelectScheduleReport").style.display="none";
		<% } %>	
	} else {
		$("divSelectDateRange").style.display="none";
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>	
			$("divSelectScheduleReport").style.display="block";
		<% } %>	
	}  
} 

//DATE FORMATTER CODE AND FUNCTIONS
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value != ''){
		if(!dtf.checkDate(obj)){
			obj.select();
			alert("<bean:message key="report.error.invalid_date"/>");
			obj.focus();
			return false;
		}else {
			return true;
		}
	}
}

function onChangeDepartment(){
  var val = selectBoxDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubdepartments, reportError);
}

function updateSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubDepartment.reInitialize(opts, '');
		m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
	    selectBoxSubSubDepartment.reInitialize(m, '');
		if(opts.length>1){
			Element.show('divSelectSpecificSubDepartment');
		}else{
			Element.hide('divSelectSpecificSubDepartment');
			Element.hide('divSelectSpecificSubSubDepartment');
		}
		
	}	
}
function onChangeSubDepartment(){
  var val = selectBoxSubDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubSubdepartments, reportError);
}
function updateSubSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubSubDepartment.reInitialize(opts, '');
		if(opts.length>1){
			Element.show('divSelectSpecificSubSubDepartment');
		}else{
			Element.hide('divSelectSpecificSubSubDepartment');
		}
		
	}	
}

function loadUsersFilterGrid(){
	if(usersFilter){
		<logic:present name="usersXml" scope="request" >
			usersFilter.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("usersXml"))%>');
		</logic:present>
	}
}

window.onload=doOnLoad;

function doOnLoad() {
	initOptionalFieldGrids();
	
	initUsersFilterGrids();
}

</script>