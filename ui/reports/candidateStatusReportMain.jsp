<%@page import="com.talentPool.common.utils.Utils"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
				com.talentPool.reports.form.ReportForm, 
				com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
				com.talentPool.positions.dataobject.PositionData,
				com.talentPool.user.dataobject.LoginData,
				com.talentPool.common.properties.TPApplicationProperties,
				com.talentPool.reports.ReportUtils"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.talentPool.user.UserConstants,com.talentPool.common.NavigationConstants"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/reports.css">
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<style>
.usersFilterGrid DIV.leftGrid{width:280px; height: 80px;}
.usersFilterGrid DIV.rightGrid{width:280px;height: 100px;}
.usersFilterGrid input.usersTextFilter{width:276px;}
.usersFilterGrid TD.arrows{width:10px;}
</style>
<script type="text/javascript">
var selectFilter=null;
var selectOrderBy =null
var selectPosition=null;
</script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="mode" value="reportCandidateStatus"/>
	<html:hidden property="filterId"/>
	<html:hidden property="actionId"/>
	<html:hidden property="sourceId"/>
	<html:hidden property="sourceCategoryId"/>
	<html:hidden property="sourceName"/>
	<html:hidden property="sourceCategoryName"/>	
	<html:hidden property="positionId"/>
	<html:hidden property="positionTitle"/>
	<html:hidden property="selectedUserIds"/>
	<html:hidden property="sourceFilter"/>
	<html:hidden property="sourceCategoryFilter"/>
	<html:hidden property="dateRange" name="reportForm"/>
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:160px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.candidate_status" /></div></td> 
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
	  			 	<td class="label"><bean:message key="common.position" />:</td>
	  			 	<td>
						<script type="text/javascript">
							var opts = new Array();
							opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
							opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
							opts[2] = new SelectOption('<%=ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS%>','<bean:message key="report.label.filter_open_and_onhold_positions"/>');
							opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
							selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
							document.write(selectFilter.getHtml());
							selectFilter.setOnChangeHandler('onChangeFilter');
							selectFilter.init();
						</script>
					</td>
				</tr>
			</table>
			<div id="divSelectSpecificPosition" style="display:none;">
				<table class="innerReport" >
				<tr>		 					
	 				<td class="label"><bean:message key="common.positions"/>:</td>
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
			<table class="innerReport">
				<tr>
					<td class="label"><bean:message key="report.label.actions"/>:</td>
					<td>
				 		<script type="text/javascript">
							var optsAction = new Array();
							var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
							optsAction[0] = new SelectOption('<%=ReportConstants.FILTER_IMPORTED_BY%>','<bean:message key="report.label.filter_imported_by"/>');
							optsAction[1] = new SelectOption('<%=ReportConstants.FILTER_MOVED_BY%>','<bean:message key="report.label.filter_moved_by"/>');
							// optsAction[2] = new SelectOption('<%=ReportConstants.FILTER_REJECTED_BY%>','<bean:message key="report.label.filter_rejected_by"/>');
							optsAction = m.concat(optsAction);
							selectAction = new SelectBox(optsAction,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
							document.write(selectAction.getHtml());
							selectAction.setOnChangeHandler('onChangeFilter');
							selectAction.init();
						</script>		    	
			 		</td>
		 		</tr>
		 	</table>
		 	<div id="divSelectSpecificAction" style="display:none;">
		 	<table style="padding-left: 20px;" >	
		 		<tr>
			 		<td style="border:none;padding:2px 0px 2px 0px;" >
				 			<table class="innerReport">
					 			<tr>
							   		<td class="label"><bean:message key="report.label.date_range"/>:</td>
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
					  		        		  	<bean:message key="report.label.from" />:
					  		        		 </td>
					  		        		<td>
					  		        			<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
											</td>
											<td style="width: 20px;"></td>
											<td>
					  		          			<bean:message key="report.label.to" /> :</td>
					  		          		<td>	
					  		          			<html:text property="toDate" styleId="toDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
					  		      			</td>
						  		      	</tr>
					 			   		</table>
										</div>
							   		</td>
							  	</tr>
							  	<tr>
							  		<td class="label" valign="top"><bean:message key="report.label.position_recruiters" />:</td>
							  		<td colspan="2">
							  			<%@include file="reportFilters/usersFilter.jspf" %>
							  		</td>
							  	</tr>		 			
							</table>
					</td>	
				</tr>
			</table>
			</div>
			<%@ include file="sourceFilter.jsp" %>
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

function onChangeFilter(val){
	val = selectFilter.getSelectedId();
	valBy=selectAction.getSelectedId();
	
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		displayHidden("1");		
	}else{
		displayHidden("0");
	}	
	if(valBy=="<%=ReportConstants.FILTER_IMPORTED_BY%>" || valBy=="<%=ReportConstants.FILTER_MOVED_BY%>" || valBy=="<%=ReportConstants.FILTER_REJECTED_BY%>"){
		displayHidden("2");
	}else{
		displayHidden("3");
	}
}

function displayHidden(val){
	if(val=="0"){
		$("divSelectSpecificPosition").style.display="none";	
	}else if(val=="1"){
		$("divSelectSpecificPosition").style.display="block";
	}else if(val=="2"){
		$("divSelectSpecificAction").style.display="block";
	}else if(val=="3"){
		$("divSelectSpecificAction").style.display="none";
	}	 
}
function validateSetFormFields(){
	//if specific department or position option selected
	if(!validateSourceCategoryFilter() || !validateSourceFilter()){
		return false;
	}
	val = selectFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		if(selectPosition.getSelectedId()=="-1"){
			alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
			return false;
		}else{
			var positionTitle = selectPosition.getText(selectPosition.getSelectedIndex());
			document.reportForm.positionTitle.value=positionTitle;
		}	
	}	
	valAction = selectAction.getSelectedId();
	if(valAction=="<%=ReportConstants.FILTER_IMPORTED_BY%>" || valAction=="<%=ReportConstants.FILTER_MOVED_BY%>" || valAction=="<%=ReportConstants.FILTER_REJECTED_BY%>"){
		if(selectedUsersFilter && selectedUsersFilter.getAllItemIds(',')==''){
			alert('<bean:message key="report.error.select_User"/>');
			return false;
		}else{
			document.reportForm.actionId.value=valAction;
			document.reportForm.selectedUserIds.value=selectedUsersFilter.getAllItemIds(',');
		}
	}else{
		document.reportForm.actionId.value="";
		document.reportForm.selectedUserIds.value="";
	}
	document.reportForm.filterId.value=val;		
	//document.reportForm.sourceId.value=selectSource.getSelectedId();
	//document.reportForm.sourceName.value=selectSource.getText(selectSource.getSelectedIndex());
	document.reportForm.positionId.value=selectPosition.getSelectedId();
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	return true;
}

function submitForm(){	
	if( validateSetFormFields() ){
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
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

function loadUsersFilterGrid(){
	if(usersFilter){
		<logic:present name="usersXml" scope="request" >
			usersFilter.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("usersXml"))%>');
		</logic:present>
	}
}

function doOnLoad() {
	//initSourceCategoryGrids();	
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>		
	initUsersFilterGrids();
	if($('slectUsersDiv')){
		$('slectUsersDiv').hide();
		$('usersFilterDiv').show();
		$('usersTextFilter').size='53';
		usersFilter.setInitWidths("260");
		selectedUsersFilter.setInitWidths("260");
	}
}

window.onload=doOnLoad;
</script>