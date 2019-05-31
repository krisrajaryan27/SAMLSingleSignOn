<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
								com.talentPool.reports.form.ReportForm, 
								com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
								com.talentPool.user.dataobject.LoginData,
								com.talentPool.common.properties.TPApplicationProperties,
								com.talentPool.reports.ReportUtils"%>	
<%@page import="com.talentPool.common.utils.Utils"%>
<%@ page import="java.util.ArrayList"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/dropdiv.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css">
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="interviewers" name="reportForm"/>
	<html:hidden property="mode" value="interviewList"/>
	<html:hidden property="filterId"/>
	<html:hidden property="dateRange" name="reportForm"/>
   	<table  border="0" cellspacing="0" cellpadding="0" style="width:500px;"> 
	  <tr> 
	    <td><div style="width:100px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.interview_list" /></div></td> 
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
				<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
				   <tr>
				   		<td>
						   	<table cellspacing="0" cellpadding="0" border="0" >	
						   		<tr>
						   			<td>
				                   		<bean:message key="report.label.date_range" /> :&nbsp;
				                   	</td>
				                   	<td>	
					                   <script type="text/javascript">
							                    var optDate = <%=ReportUtils.getJSArrayForDateRangeOfFutureDates()%>;
							                    selectDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
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
		 			<script type="text/javascript">	
			 			var opts = new Array();
			 			var checkBoxListInterviewers=null;
			 		</script>
		 			<tr>
						<td>
							<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
			 			     <tr>
			 			     	 <td valign="top">
			 			     	 		<bean:message key="report.label.users" /> :<br/>
			 			     	 		<script type="text/javascript">	
											opts = <%=CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)((ReportForm)request.getAttribute("reportForm")).getInterviewerList(), "userId", "name")%>;
								 			var checkBoxListInterviewers = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:15, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
											document.write(checkBoxListInterviewers.getHtml());
											checkBoxListInterviewers.init();
								 		</script>										
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
	<br>
   <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>
	   	<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>	
		<logic:notPresent scope="request" parameter="popup">
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		<div id="divSelectScheduleReport" style="display:block;"  class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:160px;margin-left:10px;" class="active" onclick="javascript: showScheduleReportPopup();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.schedule_this_report"/></a> 
		</div>	
		<% } %>
		</logic:notPresent>
		
		</td>
	</tr>
	</table>
	<br>	
   <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   <tr> 
   		<td>
   			<logic:notPresent scope="request" parameter="popup">
   			<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
				<%@ include file="showReportScheduled.jsp" %>
			<% } %>	
			</logic:notPresent>
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
	document.reportForm.interviewers.value=checkBoxListInterviewers.getSelectedIds();
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	if(val=='<%=ReportConstants.NEXT_N_DAYS%>' || val=='<%=ReportConstants.NEXT_N_WEEKS%>'){
		if(document.reportForm.numberRange.value==""){
			alert("Please Enter the Number");
			return false;
		}
	}
	
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
	}else if(val=='<%=ReportConstants.NEXT_N_DAYS%>' || val=='<%=ReportConstants.NEXT_N_WEEKS%>'){
		$("divSelectNumRange").style.display="block";
		$("divSelectDateRange").style.display="none";
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
			$("divSelectScheduleReport").style.display="block";
		<% } %>	
	}else {
		$("divSelectNumRange").style.display="none";
		$("divSelectDateRange").style.display="none";
		<logic:notPresent scope="request" parameter="popup">
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
			$("divSelectScheduleReport").style.display="block";
		<% } %>	
		</logic:notPresent>
	}  
}

window.onload=doOnLoad;
function doOnLoad() {
	<logic:present scope="request" parameter="popup">
		window.top.setPopTitle("<b>Interview List</b>");
	</logic:present> 
	<logic:notPresent scope="request" parameter="popup">
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>	
	</logic:notPresent> 
}
</script>