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
var selectOrderBy =null
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
	<html:hidden property="t"/>
	<html:hidden property="st"/>
	<html:hidden property="mode" value="hiringEfficiencyReportSummary"/>
	<html:hidden property="filterId"/>
	<html:hidden property="positionId"/>
	<html:hidden property="departmentId"/>
	<html:hidden property="subDepartmentId"/>
	<html:hidden property="subSubDepartmentId"/>
	<html:hidden property="orderBy"/>
	<html:hidden property="positionTitle"/>
	<html:hidden property="departmentTitle"/>
	<html:hidden property="dateRange" name="reportForm"/>
	
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>Hiring efficiency report</div></td> 
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
				                  	 <bean:message key="hiring_efficiency_report.label.date_range" /> :&nbsp;
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
								      	 			<td style="width: 20px;"></td>
								      	 			<td>
					  		        		  			<bean:message key="hiring_efficiency_report.label.from" /> :
					  		        					<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
													</td>
													<td style="width: 20px;"></td>
													<td>
						  		          				<bean:message key="hiring_efficiency_report.label.to" /> :
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
							<table class="tabinput">
								<tr>
									<td  class="firstColumn">
										<bean:message key="report.label.search_in" /> :
									</td>
									<td>
										<script type="text/javascript">
										var opts = new Array();
										opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
										opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
										opts[2] = new SelectOption('<%=ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS%>','<bean:message key="report.label.filter_open_and_onhold_positions"/>');
										opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
										selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'175px', size:20});
										document.write(selectFilter.getHtml());
										selectFilter.setOnChangeHandler('onChangeFilter');
										selectFilter.init();
										</script>
									</td>
								</tr>
							</table>
						</td>
					</tr>
  			 	<tr>
  			 		<td>
						<div id="divSelectSpecificDepartment" style="display:none;">
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
								
						</div>
					</td>
				</tr>
				</table>
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

	</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

window.onload=doOnLoad;

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>	
}

function onChangeFilter(val){
	val = selectFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		displayHidden("1");
	}else{
		displayHidden("0");
	}
}

function displayHidden(val){
	if(val=="0"){
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=="1"){
		$("divSelectSpecificDepartment").style.display="block";
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

function validateSetFormFields(){
	document.reportForm.departmentId.value='';
	document.reportForm.subDepartmentId.value='';
	document.reportForm.subSubDepartmentId.value='';
    val = selectFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		if(selectBoxDepartment.getSelectedId()=="-1"){
			alert('<bean:message key="report.error.select_department"/>');
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

</script>