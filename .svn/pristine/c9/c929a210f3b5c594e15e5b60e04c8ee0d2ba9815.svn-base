<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
								com.talentPool.reports.form.ReportForm, 
								com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
								com.talentPool.common.properties.TPApplicationProperties,
								com.talentPool.reports.ReportUtils"%>	
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css">
<link rel="stylesheet" type="text/css" href="themes/default/reports.css">
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");	
%>
<script language="JavaScript">
var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";
</script>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="sourceId" name="reportForm"/>	
	<html:hidden property="sourceCategoryId"/>
	<html:hidden property="sourceName"/>
	<html:hidden property="sourceCategoryName"/>	
	<html:hidden property="degreeIds" name="reportForm"/>
	<html:hidden property="mode" value="applicantDetails"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="sourceFilter"/>
	<html:hidden property="sourceCategoryFilter"/>
	
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:170px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.applicant_details" /></div></td> 
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
			                   <bean:message key="report.label.date_range" /> :&nbsp;
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
			   <table class="innerReport">
				   <tr>
					   <td class="label">
						<bean:message key="report.label.applicant_details.experience" /> :&nbsp;
					   </td>
					   <td>	
						<html:text property="minExp" size="2" maxlength="3" onblur="javascript: validateNumber(this);"/>
						&nbsp;<bean:message key="report.label.to" />&nbsp;
						<html:text property="maxExp" size="2" maxlength="3" onblur="javascript: validateNumber(this);"/>&nbsp;<bean:message key="report.label.applicant_details.yrs"/>
					   </td>
				   </tr>
			   </table>
		   	   <%@ include file="sourceFilter.jsp" %>						  
				<table class="innerReport" >
					<tr>
						<td class="label" valign="top"><bean:message key="report.label.applicant_details.degree" /> : <td>
						<td>
							<script type="text/javascript">	
				 			        opts = <%=CommonUtils.getListJavaScriptArray(CommonUtils.getDegreeIds(),CommonUtils.getDegreeNames())%>;
					 			var checkBoxListDegrees = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:15, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
								document.write(checkBoxListDegrees.getHtml());
								checkBoxListDegrees.init();
					 		</script>	
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
   <br>
	</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function validateSetFormFields(){
	if(!validateSourceCategoryFilter() || !validateSourceFilter()){
		return false;
	}	
	document.reportForm.degreeIds.value=checkBoxListDegrees.getSelectedIds();	
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

window.onload=doOnLoad;

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>		
}

function validateNumber(obj) {
	val = obj.value;
	if (val != '') {
		if (isNaN(parseInt(val)) || isNaN(parseFloat(val))) {			
			alert("Invalid value.");
			obj.focus();
		}
	}
}
</script>