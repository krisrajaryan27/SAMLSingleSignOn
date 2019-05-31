<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
								com.talentPool.reports.form.ReportForm, 
								com.talentPool.user.manager.ModuleSet,
								com.talentPool.reports.ReportUtils"%>	
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

<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName" name="reportForm" value='<%=(String) request.getAttribute("reportName")%>'/>
	<html:hidden property="departmentId" name="reportForm"/>
	<html:hidden property="positionId" name="reportForm"/>
	<html:hidden property="selectedUserIds" name="reportForm"/>
	<html:hidden property="interviewers" name="reportForm"/>
	<html:hidden property="mode" value="interviewListStatus"/>
	<html:hidden property="filterId"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="positionFilter" name="reportForm"/>
	<html:hidden property="departmentFilter" name="reportForm"/>
	<html:hidden property="stepIds" name="reportForm" />
	
   	<table  border="0" cellspacing="0" cellpadding="0" style="width:500px;"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.interview_list_status" /></div></td> 
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
	   			<%@include file="reportFilters/recruiterLoad.jspf" %>	
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>
	   			<%@include file="reportFilters/interviewerListLoad.jspf" %>
	   			</td>
	   		</tr>
	   		<tr>
	   			<td>
	   			<%@include file="reportFilters/stepFilterLoad.jspf" %>	
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
	<br>
   <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>
	   	<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>	
		
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>		
		<div id="divSelectScheduleReport"   class="navBtn" style="float:left; margin-right:5px;margin-top:5px;" >
					<a href="#" style="width:160px;margin-left:10px;" class="active" onclick="javascript: showScheduleReportPopup();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.schedule_this_report"/></a> 
		</div>		
		<% } %>
		
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
   <br>
	</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function validateSetFormFields(){
	var val=selectDateRange.getSelectedId();
	if(!validatePositionFilter()){
		return false;
	}
	if(selectUserFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
		if(selectedUsersFilter.getAllItemIds(',')==''){
			alert('<bean:message key="report.error.select_Recruiter" />');
			return false;
		}else{
			document.reportForm.selectedUserIds.value=selectedUsersFilter.getAllItemIds(',');
		
		}
	}else {
		document.reportForm.selectedUserIds.value='';
	}
	if(selectInterviewListFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
		if(selectedinterviewersListFilter.getAllItemIds(',')==''){
			alert('<bean:message key="report.error.select_Interviewer" />');
			return false;
		}else{
			document.reportForm.interviewers.value=selectedinterviewersListFilter.getAllItemIds(',');
		
		}
	}else {
		document.reportForm.interviewers.value='';
	}
	if(selectStepsFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_STEP%>"){
		
		if(selectedStepsFilter.getAllItemIds(',')==''){
			alert('<bean:message key="report.error.select_steps" />');
			return false;
		}else{
			document.reportForm.stepIds.value=selectedStepsFilter.getAllItemIds(',');
		}
	}else {
		document.reportForm.stepIds.value='';
	}
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	return true;
}

function submitForm(){
	if( validateSetFormFields()){
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
		$("divSelectNumRange").style.display="none";
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
			$("divSelectScheduleReport").style.display="none";
		<% } %>
		document.getElementById("fromDate").value="";
	}else {
		$("divSelectNumRange").style.display="none";
		$("divSelectDateRange").style.display="none";
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
			$("divSelectScheduleReport").style.display="block";
		<% } %>	
	}  
}

window.onload=doOnLoad;
function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>	
	initUsersFilter();
	initSelectedUsersFilter();
	initStepsFilter();
 	initSelectedStepsFilter();
 	initinterviewersListFilter();
 	initSelectedinterviewersListFilter();

	Event.observe($('stepsFilter'), "keyup", onStepsCriteriaChange.bindAsEventListener(this));
	Event.observe($('department'), "keyup", onDepartmentPositionCriteriaChange.bindAsEventListener(this));
	Event.observe($('position'), "keyup", onDepartmentPositionCriteriaChange.bindAsEventListener(this));
	Event.observe($('userFilter'), "keyup", onUsersCriteriaChange.bindAsEventListener(this));
	Event.observe($('interviewListFilter'),"keyup",onInterviewerListCriteriaChange.bindAsEventListener(this));
	
}
</script>