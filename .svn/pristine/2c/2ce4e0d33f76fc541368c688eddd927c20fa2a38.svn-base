<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, com.talentPool.reports.form.ReportForm, com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.user.manager.ModuleSet,com.talentPool.common.properties.TPApplicationProperties, com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/monthyearOptions.js" type="text/javascript"></script>	
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
	<html:hidden property="mode" value="monthlyJoiningReportSummary"/>
	<html:hidden property="filterId"/>
	<html:hidden property="positionId"/>
	<html:hidden property="departmentId"/>
	<html:hidden property="subDepartmentId"/>
	<html:hidden property="subSubDepartmentId"/>
	<html:hidden property="orderBy"/>
	<html:hidden property="positionTitle"/>
	<html:hidden property="departmentTitle"/>
	<html:hidden property="userId"/>
	<html:hidden property="userName"/>
	<html:hidden property="fromMonth"/>
	<html:hidden property="fromYear"/>
	<html:hidden property="toMonth"/>
	<html:hidden property="toYear"/>
	<html:hidden property="fromDt"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="isReportFormatCSV"/>	
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>Monthly joining report</div></td> 
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
			   			<td>
			   			<table cellpadding="0" cellspacing="0">
			   			<tr>
			   				<td>
		                  	 <bean:message key="report.label.date_range" /> :
		                  	</td>
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
							 		 <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
						      	 		<tr>
											<td>
											<%@include file="monthlyCriteria.jsp" %>
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
						<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
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
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td>
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
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td>
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
					</td>
				</tr>
				<tr style="">
					<td>
						 &nbsp;CSV Report ?&nbsp;&nbsp;<img src="images/checkboxunchecked.gif" onclick="javascript: changeToCSVReport(this);" >
					</td>				
				</tr>		
				</table>
			</td>
		</tr>
		<tr id="reportTypeHead">				  			
			<td class="head">
				<b><bean:message key="report.label.report_type" /></b>
			</td>
		</tr>
		<tr id="reportTypeBox">				  			
			<td>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_SUMMARY%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_summary"/></html:radio>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_DETAILS%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_details"/></html:radio>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_CHART%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_chart"/></html:radio>
			</td>
		</tr>
		<tr id="reportFormatHead">				  			
			<td class="head">
			<b><bean:message key="report.label.report_format" /></b>
			</td>
		</tr>
		<tr id="reportFormatBox">				  			
			<td>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_HTML%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.format_html"/></html:radio>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_PDF%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.format_pdf"/></html:radio>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_EXCEL%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.format_excel"/></html:radio>
			</td>
		</tr>
	</table>
	<br/>
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
	<br/>
	
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

function validateSetFormFields(){
	document.reportForm.departmentId.value='';
	document.reportForm.subDepartmentId.value='';
	document.reportForm.subSubDepartmentId.value='';
	if(selectBoxDepartment.getSelectedId()!="-1"){
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
	document.reportForm.fromMonth.value=selectMonthFrom.getSelectedId();
	document.reportForm.fromYear.value=selectYearFrom.getSelectedId();
	document.reportForm.toMonth.value=selectMonthTo.getSelectedId();
	document.reportForm.toYear.value=selectYearTo.getSelectedId();
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

var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";

function changeToCSVReport(obj) {
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
		$("reportTypeHead").show();
		$("reportTypeBox").show();
		$("reportFormatHead").show();
		$("reportFormatBox").show();			
		document.reportForm.isReportFormatCSV.value=<%=ReportConstants.REPORT_FORMAT_NON_CSV%>;
	} else {
		obj.src=chkboxchked;
		$("reportTypeHead").hide();
		$("reportTypeBox").hide();
		$("reportFormatHead").hide();
		$("reportFormatBox").hide();	
		document.reportForm.isReportFormatCSV.value=<%=ReportConstants.REPORT_FORMAT_CSV%>;
	}
}

</script>