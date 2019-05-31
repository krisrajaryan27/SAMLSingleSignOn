<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
				com.talentPool.reports.form.ReportForm, 
				com.talentPool.positions.dataobject.PositionData,
				com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
				com.talentPool.common.properties.TPApplicationProperties,
				com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>

<%@page import="java.util.ArrayList"%><script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/monthyearOptions.js" type="text/javascript"></script>	
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="mode" value="recruitmentCost" />
	<html:hidden property="recruitmentCostReportType" />
	<html:hidden property="positionId" />
	<html:hidden property="positionTitle" />
	<html:hidden property="departmentId"/>
	<html:hidden property="departmentTitle"/>
	<html:hidden property="sourceId" />
	<html:hidden property="sourceName" />
	<html:hidden property="filterId" />
	<html:hidden property="dateRange" name="reportForm"/>
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:170px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.recruitment_cost" /></div></td> 
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
				<table class="innerReport" width="100%">
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
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
		 			    		<bean:message key="report.label.display"/> :&nbsp;
		 			    	</td>
		 			    	<td>
	 			    		<script type="text/javascript">
				                    var opts = new Array();
				                    opts[0] = new SelectOption('<bean:message key="report.label.overall_recruitment_cost_report"/>','<bean:message key="report.label.overall_recruitment_cost_report"/>');
				                    opts[1] = new SelectOption('<bean:message key="report.label.positionwise_recruitment_cost_report"/>','<bean:message key="report.label.positionwise_recruitment_cost_report"/>');
				                    opts[2] = new SelectOption('<bean:message key="report.label.sourcewise_recruitment_cost_report"/>','<bean:message key="report.label.sourcewise_recruitment_cost_report"/>');
				                    opts[3] = new SelectOption('<bean:message key="report.label.transaction_report"/>','<bean:message key="report.label.transaction_report"/>');
				                    var selectFilter = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'230px', size:20});
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
					<td width="100%">
						<div id="posSelectFilter" style="display:none;">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
				 			    		<bean:message key="report.label.search_in"/> :&nbsp;
				 			    	</td>
				 			    	<td>
								        <script type="text/javascript">
									        var opts = new Array();
											opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
											opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
											opts[2] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
											opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
											var selectFilterPosition = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'140px', size:20});
											document.write(selectFilterPosition.getHtml());
											selectFilterPosition.setOnChangeHandler('onChangeFilterPosition');
											selectFilterPosition.init();
								        </script>
				                    </td>
				                  </tr>
				               </table>     
					        <br/>
						</div>
						<div id="divSelectSpecificSource" style="display:none;">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
				 			    		<bean:message key="report.label.select_source"/> :&nbsp;
				 			    	</td>
				 			    	<td>
								        <script type="text/javascript">
									         var opts = <%=CommonUtils.getListJavaScriptArray(CommonUtils.getSourceIds(),CommonUtils.getSourceNames())%>;
									         var m = [new SelectOption('-1','<bean:message key="common.selectlist.all"/>')];
									         opts = m.concat(opts);
									         selectBoxSource = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
									         document.write(selectBoxSource.getHtml());
									         selectBoxSource.init();
								        </script>
				                    </td>
				                  </tr>
				               </table>     
					        <br/>
						</div>
					</td>
				</tr>
 			    <tr>
  			 		<td width="100%" >		  			 			
  			 			<div id="divSelectSpecificPosition" style="display:none;">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
				 			    		<bean:message key="common.select_position"/> :&nbsp;
				 			    	</td>
				 			    	<td>
								        <script type="text/javascript">
									        var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)request.getAttribute("positions"), "positionId", "positionTitle")%>;
											var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
											opts = m.concat(opts);
											selectBoxPosition = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'140px', size:20});
											document.write(selectBoxPosition.getHtml());
											selectBoxPosition.init();
								        </script>
				                    </td>
				                  </tr>
				               </table>     
					        <br/>
						</div>	
						<div id="divSelectSpecificDepartment" style="display:none;">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td>
				 			    		<bean:message key="report.label.select_department"/> :&nbsp;
				 			    	</td>
				 			    	<td>
										<script type="text/javascript">
											var opts = <%=CommonUtils.getListJavaScriptArray(CommonUtils.getDeptIds(),CommonUtils.getDeptNames())%>;
											var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
											opts = m.concat(opts);
											selectBoxDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
											document.write(selectBoxDepartment.getHtml());
											selectBoxDepartment.init();
										</script>
				                    </td>
				                  </tr>
				               </table>     
					        <br/>
						</div>													
					</td>
				</tr>
			</table>
		</td>
		</tr>
		<tr id="reportTypeHead">				  			
			<td class="head" >
				<b><bean:message key="report.label.report_type"/></b>
			</td>
		</tr>
		<tr id="reportTypes">				  			
			<td>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_SUMMARY%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_summary"/></html:radio>
			<html:radio property="reportType" value="<%=ReportConstants.REPORT_TYPE_DETAILS%>" style="border:0px;background-color:#fff;"><bean:message key="report.label.type_details"/></html:radio>			
			</td>
		</tr>
		<tr>				  			
			<td class="head">
			<b><bean:message key="report.label.report_format"/></b>
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

function validateSetFormFields(){
	if(selectDateRange.getSelectedId()==<%=ReportConstants.CUSTOM%>){
		if (document.reportForm.fromDate.value == '') {
			alert('Please select the from date.');
			return false;
		}
	}
	document.reportForm.recruitmentCostReportType.value=selectFilter.getSelectedId();
	var val = selectFilter.getSelectedId();
	if (val == '<bean:message key="report.label.positionwise_recruitment_cost_report"/>') {
		var val1 = selectFilterPosition.getSelectedId();
		if(val1=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
			if(selectBoxPosition.getSelectedId()=="-1"){
				alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
				return false;
			}
		}else if(val1=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
			if(selectBoxDepartment.getSelectedId()=="-1"){
				alert('<bean:message key="report.error.select_department"/>');
				return false;
			}
		}
		document.reportForm.filterId.value=val1;		
	} 
	val = selectBoxPosition.getSelectedId();
	if (val == -1) {
		document.reportForm.positionId.value='';
	} else {
		document.reportForm.positionId.value=val;
	}
	val = selectBoxDepartment.getSelectedId();
	if (val == -1) {
		document.reportForm.departmentId.value='';
	} else {
		document.reportForm.departmentId.value=val;
	}	
	val = selectBoxSource.getSelectedId();
	if (val == -1) {
		document.reportForm.sourceId.value='';
	} else {
		document.reportForm.sourceId.value=val;
	}	
	document.reportForm.positionTitle.value=selectBoxPosition.getText(selectBoxPosition.getSelectedIndex());
	document.reportForm.departmentTitle.value=selectBoxDepartment.getText(selectBoxDepartment.getSelectedIndex());
	document.reportForm.sourceName.value=selectBoxSource.getText(selectBoxSource.getSelectedIndex());
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

function onChangeFilterPosition() {
	var val = selectFilterPosition.getSelectedId();
	if(val=='<%=ReportConstants.FILTER_SPECIFIC_POSITION%>'){
		selectBoxPosition.setSelected(selectBoxPosition.getIndexWithId('-1'));
		$("divSelectSpecificPosition").style.display="block";
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=='<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>'){
		selectBoxDepartment.setSelected(selectBoxDepartment.getIndexWithId('-1'));
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="block";
	} else {
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="none";
	}  
}

function onChangeFilter() {
	var val = selectFilter.getSelectedId();
	$("divSelectSpecificPosition").style.display="none";
	$("divSelectSpecificDepartment").style.display="none";
	if (val == '<bean:message key="report.label.overall_recruitment_cost_report"/>') {
		$("posSelectFilter").style.display="none";
		$("divSelectSpecificSource").style.display="none";
	} else if (val == '<bean:message key="report.label.positionwise_recruitment_cost_report"/>') {		
		selectFilterPosition.setSelected(selectFilterPosition.getIndexWithId('-1'));	
		$("posSelectFilter").style.display="block";
		$("divSelectSpecificSource").style.display="none";		
	} else if (val == '<bean:message key="report.label.sourcewise_recruitment_cost_report"/>') {
		selectBoxSource.setSelected(selectBoxSource.getIndexWithId('-1'));
		$("posSelectFilter").style.display="none";
		$("divSelectSpecificSource").style.display="block";
	} else if (val == '<bean:message key="report.label.transaction_report"/>') {
		$("posSelectFilter").style.display="none";
		$("divSelectSpecificSource").style.display="none";
	}
	if (val == '<bean:message key="report.label.transaction_report"/>') {
		$("reportTypeHead").style.display="none";
		$("reportTypes").style.display="none";
	} else {
		$("reportTypeHead").style.display="block";
		$("reportTypes").style.display="block";
	}
}
</script>