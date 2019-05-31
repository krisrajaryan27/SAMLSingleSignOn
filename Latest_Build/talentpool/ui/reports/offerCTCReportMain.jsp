<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, com.talentPool.reports.form.ReportForm, com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.user.manager.ModuleSet,com.talentPool.common.properties.TPApplicationProperties,com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css">
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
<html:hidden property="positionId"/>
<html:hidden property="departmentId"/>
<html:hidden property="subDepartmentId"/>
<html:hidden property="subSubDepartmentId"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="reportFormat" name="reportForm" value="4"/>
<html:hidden property="reportTemplateId" name="reportForm"/>
<html:hidden property="selectedUserIds" name="reportForm" />
<html:hidden property="dateRange" name="reportForm"/>
<html:hidden property="positionFilter"/>
<html:hidden property="departmentFilter"/>

<div class="contentDiv">
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  	<tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC">
	    	</span><bean:message key="report.label.offer_ctc" /></div></td> 
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
				 <table class="innerReport">
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
			<img src="images/checkedradiobutton.gif" name='reportFormat' value="<%=ReportConstants.FORMAT_PRE_FORMATTED%>" id='reportFormat_<%=ReportConstants.FORMAT_PRE_FORMATTED%>'  
				 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_pre"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
		
			<script type="text/javascript">
				 var templatesOpts = <%=(String)request.getAttribute("reportTemplateJsArray")%>;
				 var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
				 templatesOpts = opt.concat(templatesOpts);
                 selectReportTempales = new SelectBox(templatesOpts,'','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
                 document.write(selectReportTempales.getHtml());
                 selectReportTempales.init();
			</script>
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

<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
</html:form>
</div>	
<script language="JavaScript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";

var checkedRadioImg = 'images/checkedradiobutton.gif';
var radioImg = 'images/radiobutton.gif';

var preFormattedFormat = '<%=ReportConstants.FORMAT_PRE_FORMATTED%>';

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
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	
	if(selectReportTempales.getSelectedId()=="-1"){
		alert('<bean:message key="report.error.select_template" />');
		return false;
	}else{
		document.reportForm.reportTemplateId.value=selectReportTempales.getSelectedId();
	}
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

function submitForm(){
	if(validateSetFormFields()){
		var d = new Date();
		document.reportForm.mode.value = 'offerCTCReport';
		document.reportForm.reportFormat.value=4;
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
	       doOnLoadScheduledGrid();
     <% } %>	
}

window.onload = doOnLoad;
</script>