<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@ page import="com.talentPool.reports.ReportConstants, com.talentPool.reports.form.ReportForm, com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.user.manager.ModuleSet,com.talentPool.common.properties.TPApplicationProperties, com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/monthyearOptions.js" type="text/javascript"></script>	
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/reports.css"> 
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/commonGridFunctions.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script type="text/javascript">
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
</script>

<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="mode" />
	<html:hidden property="positionId"/>
	<html:hidden property="departmentId"/>
	<html:hidden property="subDepartmentId"/>
	<html:hidden property="subSubDepartmentId"/>
	<html:hidden property="positionTitle"/>
	<html:hidden property="departmentTitle"/>
	<html:hidden property="filterId"/>
	<html:hidden property="stepIds"/>
	<html:hidden property="stepTitles"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="selectedUserIds"/>
	<html:hidden property="sourceId"/>
	<html:hidden property="sourceCategoryId"/>
	<html:hidden property="sourceName"/>
	<html:hidden property="sourceCategoryName"/>
	<html:hidden property="sourceFilter"/>
	<html:hidden property="sourceCategoryFilter"/>
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:190px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>Offer to Joined Detailed Report</div></td> 
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
				<table class="innerReport" width="100%">
					<tr>
			   			<td>
			   				<table cellpadding="0" cellspacing="0">
			   				<tr>			   											
					    		<td class="label">
						    		<bean:message key="report.label.date_range" /> :&nbsp; </td>
						   		<td>
			                  	 <script type="text/javascript">
					                    var optDate = <%=ReportUtils.getJSArrayForDateRange()%>;
					                    selectDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
					                    document.write(selectDateRange.getHtml());
					                    selectDateRange.setOnChangeHandler('onDateRangeChange');
					                    selectDateRange.init();
			                  	  </script>
					   			</td>
					   			<td>
									<div id="divSelectDateRange" style="display:none;">
								 		 <table class="innerReport" >
							      	 		<tr>
							      	 			<td style="width: 20px;"></td>
							      	 			<td>
				  		        		  			<bean:message key="report.label.from" /> :&nbsp; 
				  		        		  		</td>
				  		        		  		<td>
				  		        					<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
												</td>
												<td style="width: 20px;"></td>
												<td>
					  		          				<bean:message key="report.label.to" /> :&nbsp; </td>
					  		          			<td>	
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
				</table>
				<%@include file="reportFilters/usersFilter.jspf" %>	
				<%@ include file="sourceFilter.jsp" %>
			</td>
		</tr>
		<!--<tr>				  			
			<td class="head">
				<b><bean:message key="report.label.report_type" /></b>
			</td>
		</tr>
		 <tr>				  			
			<td>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_SUMMARY%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_summary"/></html:radio>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_DETAILS%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_details"/></html:radio>			
			</td>
		</tr> -->
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
	</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script type="text/javascript">


var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function loadUsersFilterGrid(){
	<logic:present name="usersXml" scope="request" >
		usersFilter.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("usersXml"))%>');
	</logic:present>
}

function submitForm(){
	if( validateSetFormFields()){
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

function validateSetFormFields(){
	val = '<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>';
	if(selectUserFilter && selectUserFilter.getSelectedId()!= '<%=ReportConstants.FILTER_ALL%>'){
		document.reportForm.selectedUserIds.value=selectedUsersFilter.getAllItemIds(',');
	}else if (selectUserFilter.getSelectedId()== '<%=ReportConstants.FILTER_ALL%>'){
		document.reportForm.selectedUserIds.value='';
	}
	
	if (sourceRightGrid && selectSourceFilter.getSelectedId()!= '<%=ReportConstants.FILTER_ALL%>'){
		var selectedSource = sourceRightGrid.getAllItemIds(',');	 		
		document.reportForm.sourceId.value=selectedSource;
	}else if (selectSourceFilter.getSelectedId()== '<%=ReportConstants.FILTER_ALL%>'){
		document.reportForm.sourceId.value='';
	}
	if (sourceCategoryRightGrid && selectSourceCategoryFilter.getSelectedId()!= '<%=ReportConstants.FILTER_ALL%>'){
		var selectedSourceCategories = 	sourceCategoryRightGrid.getAllItemIds();	
		document.reportForm.sourceCategoryId.value=selectedSourceCategories;
	}else if (selectSourceCategoryFilter.getSelectedId()== '<%=ReportConstants.FILTER_ALL%>'){
		document.reportForm.sourceCategoryId.value='';
	}
	
	document.reportForm.mode.value = 'offerToJoinedDetailedReport';
	document.reportForm.filterId.value=val;
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	return true;
}

function onChangeStep(){	
	
}

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

window.onload=doOnLoad;

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>
	initUsersFilterGrids();
}

</script>