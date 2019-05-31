<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, com.talentPool.reports.form.ReportForm, com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.user.manager.ModuleSet,com.talentPool.common.properties.TPApplicationProperties,com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css">
<script type="text/javascript">
var selectFilter=null;
var selectOrderBy =null
var selectPosition=null;
var selectBoxDepartment=null;
var selectSource=null;
var selectUser=null;
</script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
	
%>
<div class="contentDiv">
<html:form action="/reports">
<html:hidden property="reportName"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="mode" value="importReport"/>
<html:hidden property="filterId"/>
<html:hidden property="positionId"/>
<html:hidden property="departmentId"/>
<html:hidden property="userId"/>
<html:hidden property="userName"/>
<html:hidden property="sourceId"/>
<html:hidden property="sourceName"/>
<html:hidden property="orderBy"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="dateRange" name="reportForm"/>

   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:100px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>Import report</div></td> 
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
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
						   			<td>
				                  	 <bean:message key="report.label.date_range"/> :&nbsp;
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
					 		<table cellspacing="0" cellpadding="0" border="0" >
				      	 		<tr>
				      	 			<td>
				  			           <bean:message key="report.label.search_in" /> :&nbsp;
									</td>
									<td>
				  			            <script type="text/javascript">
									       var opts = new Array();
									       opts[0] = new SelectOption('-1','<bean:message key="common.selectlist.default"/>');
									       opts[1] = new SelectOption('<%=ReportConstants.FILTER_IMPORTED_BY%>','<bean:message key="report.label.filter_imported_by"/>');
									       <% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
										       opts[2] = new SelectOption('<%=ReportConstants.FILTER_SOURCE%>','<bean:message key="report.label.filter_source"/>');
										   <% } %>
										   selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'140px', size:20});
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
	  			         <div id="divSelectSpecificUser" style="display:none;">
			            	<table class="tabinput">
				              <tr>
				             	<td class="label">
					            	<bean:message key="report.label.select_user" /> :
					            </td>
					            <td class="hgap"></td>
					            <td>
					               <script type="text/javascript">
	                               var opts = <%=CommonUtils.getListJavaScriptArray(reportForm.getUserIds(),reportForm.getUserNames())%>;
	                               var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
	                               opts = m.concat(opts);
	                               selectUser = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
	                               document.write(selectUser.getHtml());
	                               selectUser.init();
                                  </script>
					           </td>
				            </tr>
				         </table>
			           </div>
	  			       </td>
	  			     </tr>
	  			     <tr>
	  			       <td>
	  			          <div id="divSelectSpecificSource" style="display:none;">
			            	<table class="tabinput">
			                	<tr>
				                	<td class="label">
						            <bean:message key="report.label.select_source" /> :
					                </td>
					                <td class="hgap"></td>
					                <td>
					                <script type="text/javascript">
	                                   var opts = <%=CommonUtils.getListJavaScriptArray(reportForm.getSourceIds(),reportForm.getSourceNames())%>;
	                                   var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
	                                   opts = m.concat(opts);
	                                   selectSource = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
	                                   document.write(selectSource.getHtml());
	                                   selectSource.init();
                                   </script> 
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
	  			<td class="head">
	  				<b><bean:message key="report.label.order_by" /></b>
	  			</td>
	</tr>
	 <tr>				  			
	  			<td>
			 		<table cellspacing="0" cellpadding="0" border="0" class="tabinput">
		      	 		<tr>
		      	 			<td style="border:0px;">
					  			<bean:message key="report.label.order_by" />:&nbsp;
							</td>
							<td style="border:0px;">
								<script type="text/javascript">
									var opts = new Array();
									opts[0] = new SelectOption('<%=ReportConstants.SORT_BY_DATE_OF_IMPORT%>','<bean:message key="report.label.sort_by_date_of_import"/>');
									opts[1] = new SelectOption('<%=ReportConstants.SORT_BY_SOURCE%>','<bean:message key="report.label.sort_by_source"/>');
									opts[2] = new SelectOption('<%=ReportConstants.SORT_BY_IMPORTED_BY%>','<bean:message key="report.label.sort_by_imported_by"/>');
									selectOrderBy = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'140px', size:20});
									document.write(selectOrderBy.getHtml());
									selectOrderBy.init();
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

window.onload=doOnLoad;

function doOnLoad() {
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>	
}

function onChangeFilter(val){
	val = selectFilter.getSelectedId();
	var toDate = document.reportForm.toDate.value;
	var fromDate = document.reportForm.fromDate.value;
	
	if(val=="<%=ReportConstants.FILTER_SOURCE%>"){
                    displayHidden("2");
	}else if(val=="<%=ReportConstants.FILTER_IMPORTED_BY%>"){
	       displayHidden("1");
	}else{
	   displayHidden("0");
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

function displayHidden(val){
	if(val=="1"){
		$("divSelectSpecificUser").style.display="block";
		$("divSelectSpecificSource").style.display="none";
	}else if(val=="2"){
		$("divSelectSpecificUser").style.display="none";
		$("divSelectSpecificSource").style.display="block";
	} else{
	    $("divSelectSpecificUser").style.display="none";
		$("divSelectSpecificSource").style.display="none";
	}
}

function validateSetFormFields(){
	
    val = selectFilter.getSelectedId();
    	if(val=="<%=ReportConstants.FILTER_IMPORTED_BY%>"){
		if(selectUser.getSelectedId()=="-1"){
			alert('<bean:message key="report.error.select_User"/>');
			return false;
		}else{
		    var userId = selectUser.getText(selectUser.getSelectedId);
			var userName = selectUser.getText(selectUser.getSelectedIndex());
			document.reportForm.userName.value=userName;
			document.reportForm.userId.value=userId;	
		}	
	}else if(val=="<%=ReportConstants.FILTER_SOURCE%>"){
		if(selectSource.getSelectedId()=="-1"){
			alert('<bean:message key="report.error.select_Source"/>');
			return false;
		}else{
			var sourceId = selectSource.getText(selectSource.getSelectedId());
			document.reportForm.sourceId.value=sourceId;
		}	
	}
    
    document.reportForm.filterId.value=val;
    document.reportForm.userId.value=selectUser.getSelectedId();    
    document.reportForm.sourceId.value=selectSource.getSelectedId();
    document.reportForm.orderBy.value=selectOrderBy.getSelectedId();
    document.reportForm.userName.value=selectUser.getText(selectUser.getSelectedIndex());
    document.reportForm.sourceName.value=selectSource.getText(selectSource.getSelectedIndex());
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
</script>
<script language="JavaScript">
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
</script>