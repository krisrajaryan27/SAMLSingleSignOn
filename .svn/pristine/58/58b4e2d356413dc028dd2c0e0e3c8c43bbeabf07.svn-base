<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
								com.talentPool.reports.form.ReportForm, 
								com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
								com.talentPool.user.dataobject.LoginData,
								com.talentPool.common.properties.TPApplicationProperties"%>	
<%@ page import="java.util.ArrayList"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>

<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="interviewers" name="reportForm"/>
	<html:hidden property="mode" value="pendingActions"/>
	<html:hidden property="filterId"/>
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.pending_actions_report" /></div></td> 
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
		 			<script type="text/javascript">	
			 			var opts = new Array();
			 			var checkBoxListUsers=null;
			 		</script>
		 			<tr>
						<td>
							<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
			 			     <tr>
			 			     	 <td valign="top">
			 			     	 		<bean:message key="report.label.users" /> :<br/>
			 			     	 		<script type="text/javascript">	
							 			    opts = <%=CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)((ReportForm)request.getAttribute("reportForm")).getInterviewerList(), "userId", "name")%>;
								 			var checkBoxListUsers = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:15, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
											document.write(checkBoxListUsers.getHtml());
											checkBoxListUsers.init();
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
	<table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>
	   	<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
			<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
		</div>	
		<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		<div id="divSelectScheduleReport" style="display:block;"  class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
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
<script language="JavaScript">

window.onload=doOnLoad;

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>	
}

function validateSetFormFields(){
	document.reportForm.interviewers.value=checkBoxListUsers.getSelectedIds();
	return true;
}


function submitForm(){
	if( validateSetFormFields()){
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

</script>