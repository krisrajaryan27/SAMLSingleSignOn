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
	<html:hidden property="mode" value="indiaHiringReqReport"/>
	<html:hidden property="filterId"/>
	<html:hidden property="fieldIds"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="positionFilter" name="reportForm"/>
	<html:hidden property="departmentFilter" name="reportForm"/>
	
   	<table  border="0" cellspacing="0" cellpadding="0" style="width:500px;"> 
	  <tr> 
	    <td><div style="width: 200px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.india_hiring_req_report" /></div></td> 
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
	   			<%@include file="reportFilters/departmentPositionFilterWithoutPositionStatus.jspf" %>
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
			<img src="images/checkedradiobutton.gif">   <bean:message key="report.label.format_excel"/>
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
		<div id="divSelectScheduleReport" class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
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
   </div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
	</html:form>
<script language="JavaScript">


var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();
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
			 document.getElementById("fromDate").value="";
			 document.getElementById("fromDate").focus();
			 return false;
		}
		if(date2 > new Date()){
			alert('<bean:message key="calendar.alert.toGreaterThanToday"/>');
			 document.getElementById("toDate").value=newDate;
			 document.getElementById("toDate").focus();
			 return false;
		}
	}
	
	if(!validatePositionFilter()){
		return false;
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
		document.getElementById("fromDate").value="";
		$("divSelectDateRange").style.display="block";
		
	}else {
		$("divSelectDateRange").style.display="none";
	}  
}

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
	       doOnLoadScheduledGrid();
     <% } %>	
}

window.onload = doOnLoad;
</script>