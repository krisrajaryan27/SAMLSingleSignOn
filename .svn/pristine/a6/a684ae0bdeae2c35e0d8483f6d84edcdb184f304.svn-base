<%@page import="com.talentPool.common.utils.Utils"%>
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
<%@ page import="java.util.ArrayList"%>
<link rel="stylesheet" type="text/css" href="themes/default/reports.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script type="text/javascript">
var selectFilter=null;
var selectOrderBy =null
var selectPosition=null;
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
</script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");	
%>

<html:form action="/reports">
<html:hidden property="reportName"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="mode" />
<html:hidden property="filterId"/>
<html:hidden property="positionId"/>
<html:hidden property="departmentId"/>
<html:hidden property="subDepartmentId"/>
<html:hidden property="subSubDepartmentId"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="selectedUserIds"/>

<div class="contentDiv">
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.hiring_status_report" /></div></td> 
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
						<bean:message key="report.label.search_in" />:&nbsp;
					</td>
					<td>
						<script type="text/javascript">
							var opts = new Array();
							opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
							opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
							opts[2] = new SelectOption('<%=ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS%>','<bean:message key="report.label.filter_open_and_onhold_positions"/>');
							opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
							opts[4] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
							selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
							document.write(selectFilter.getHtml());
							selectFilter.setOnChangeHandler('onChangeFilter');
							selectFilter.init();
						</script>
					</td>
				</tr>
			</table>		  			  
			<div id="divSelectSpecificPosition" style="display:none;">
			<table class="innerReport">
				<tr>
					<td class="label">
						<bean:message key="common.select_position" /> :&nbsp;
					</td>
					<td>
						<script type="text/javascript">
							var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)request.getAttribute("positions"), "positionId", "positionTitle")%>;
							var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
							opts = m.concat(opts);
							selectPosition = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
							document.write(selectPosition.getHtml());
							selectPosition.init();
						</script>
					</td>
				</tr>
			</table>		  			  
			</div>
			<div id="divSelectSpecificDepartment" style="display:none;">
			<table class="innerReport">
			<tr>
				<td class="label">
					<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>:&nbsp;
				</td>
				<td>
					<script type="text/javascript">
						var opts = <%=CommonUtils.getListJavaScriptArray(reportForm.getDepartmentIds(),reportForm.getDepartmentNames())%>;
						var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
						opts = m.concat(opts);
						selectBoxDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
						selectBoxDepartment.setOnChangeHandler('onChangeDepartment');
						document.write(selectBoxDepartment.getHtml());
						selectBoxDepartment.init();
					</script>
				</td>
			</tr>
			</table>
			<div id="divSelectSpecificSubDepartment" style="display:none;">
				<table class="innerReport">
				<tr>
					<td class="label">
						<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) %>:&nbsp;
					</td>
					<td>
						<script type="text/javascript">
							var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
							selectBoxSubDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
							selectBoxSubDepartment.setOnChangeHandler('onChangeSubDepartment');
							document.write(selectBoxSubDepartment.getHtml());
							selectBoxSubDepartment.init();
						</script>
					</td>
				</tr>
				</table>
			</div>
			<div id="divSelectSpecificSubSubDepartment" style="display:none;">
				<table class="innerReport">
				<tr>
					<td class="label">
						<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) %>:&nbsp;
					</td>
					<td>
					<script type="text/javascript">
						var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
						selectBoxSubSubDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
						document.write(selectBoxSubSubDepartment.getHtml());
						selectBoxSubSubDepartment.init();
					</script>
					</td>
				</tr>
				</table>
			</div>
			</div>
			<%@include file="reportFilters/usersFilter.jspf" %>	
		 </td>
	</tr>
	<tr>				  			
		<td class="head">
			<b><bean:message key="report.label.report_type" /></b>
		</td>
	</tr>
	<tr>				  			
		<td>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_SUMMARY%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_summary"/></html:radio>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_DETAILS%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_details"/></html:radio>			
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
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		<div id="divSelectScheduleReport" class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:160px;margin-left:10px;" class="active" onclick="javascript: showScheduleReportPopup();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.schedule_this_report"/></a> 
		</div>	
		<% } %>
   			
   			</td>
	</tr>
	</table>	
   <br>
   <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   <tr> 
   		<td>
   			<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
				<%@ include file="showReportScheduled.jsp" %>
			<% } %>	
		</td> 	    
   </tr>   
   </table>
</div>
</html:form>
<script language="JavaScript">
function onChangeFilter(val){
	val = selectFilter.getSelectedId();
	
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
	for (i = 0; i < document.reportForm.reportType.length; i++) {
		if (document.reportForm.reportType[i].checked && (document.reportForm.reportType[i].value == '<%=ReportConstants.REPORT_TYPE_SUMMARY%>')) {
			document.reportForm.mode.value = 'hiringStatusSummaryReport';	
		} 
		if (document.reportForm.reportType[i].checked && (document.reportForm.reportType[i].value == '<%=ReportConstants.REPORT_TYPE_DETAILS%>')) {
			document.reportForm.mode.value = 'hiringStatusDetailReport';	
		}
	}
	document.reportForm.positionId.value='';
	document.reportForm.departmentId.value='';
	document.reportForm.subDepartmentId.value='';
	document.reportForm.subSubDepartmentId.value='';
	
	//if specific department or position option selected
	val = selectFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		if(selectPosition.getSelectedId()=="-1"){
			alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
			return false;
		}else{
			var positionTitle = selectPosition.getText(selectPosition.getSelectedIndex());
			document.reportForm.positionTitle.value=positionTitle;
			document.reportForm.positionId.value=selectPosition.getSelectedId();
		}	
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		if(selectBoxDepartment.getSelectedId()=="-1"){
			alert("Please select "+'<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
			return false;
		}else{
			var departmentTitle = selectBoxDepartment.getText(selectBoxDepartment.getSelectedIndex());
			document.reportForm.departmentTitle.value=departmentTitle;
			document.reportForm.departmentId.value=selectBoxDepartment.getSelectedId();
			if(selectBoxSubDepartment.getSelectedId()!="-1"){
				document.reportForm.subDepartmentId.value=selectBoxSubDepartment.getSelectedId();
			}
			if(selectBoxSubSubDepartment.getSelectedId()!="-1"){
				document.reportForm.subSubDepartmentId.value=selectBoxSubSubDepartment.getSelectedId();
			}
		}	
	}	
	document.reportForm.filterId.value=val;
	if(!setSelectedUsers()){
		return false;
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

function submitForm(){
	if( validateSetFormFields()){	
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>
	initUsersFilterGrids();
}

window.onload=doOnLoad;
</script>