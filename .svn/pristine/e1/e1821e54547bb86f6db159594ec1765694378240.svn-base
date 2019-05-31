<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
								com.talentPool.reports.form.ReportForm, 
								com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
								com.talentPool.user.dataobject.LoginData,
								com.talentPool.common.properties.TPApplicationProperties,
								com.talentPool.positions.PositionConstants,
								com.talentPool.reports.ReportUtils,
								com.talentPool.selectionProcess.SelectionProcessConstants"%>	
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.talentPool.common.utils.CommonUtils"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css">
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
	<html:hidden property="interviewers" name="reportForm"/>
	<html:hidden property="positionId" name="reportForm"/>
	<html:hidden property="departmentId" name="reportForm"/>
	<html:hidden property="stages" name="reportForm"/>
	<html:hidden property="activities" name="reportForm"/>	
	<html:hidden property="mode" value="userActivity"/>
	<html:hidden property="filterId"/>
	<html:hidden property="dateRange" name="reportForm"/>
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:130px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.user_activity" /></div></td> 
	  </tr> 
	</table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
		<tr>				  			
			<td class="head">
				<b><bean:message key="report.label.filter"/></b>
			</td>
		</tr>
		<tr>				  			
			<td>
				<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
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
				  		        		  			<bean:message key="report.label.from"/> :
				  		        					<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
												</td>
												<td style="width: 20px;"></td>
												<td>
					  		          				<bean:message key="report.label.to"/> :
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
		 			<script type="text/javascript">	
			 			var opts = new Array();
			 			var checkBoxListInterviewers=null;
			 		</script>
		 			<tr>
						<td>
							<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
			 			     <tr>
			 			     	 <td valign="top" width="50">
			 			     	 		<bean:message key="report.label.users"/> :			 			     	 											
									</td>
									<td>
										<script type="text/javascript">	
											var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)((ReportForm)request.getAttribute("reportForm")).getInterviewerList(), "userId", "name")%>;
								 			var checkBoxListInterviewers = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:15, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
											document.write(checkBoxListInterviewers.getHtml());
											checkBoxListInterviewers.init();
								 		</script>	
									</td>
			 			     </tr>
			 			   </table>
			 			 </td>
		 			</tr>
		 			<tr>
						<td>
							<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
								<tr>
							       <td width="50" valign="top">
					  		          <bean:message key="report.label.user_activity.stage" /> :	
					  		       </td>
					  		       <td>		  		          
					  		          <img src="images/checkboxchecked.gif" id="stage0" onclick="javascript: changeStage(this);">&nbsp;All&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="stage1" onclick="javascript: changeStage(this);">&nbsp;Select&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="stage2" onclick="javascript: changeStage(this);">&nbsp;Hire&nbsp;&nbsp;
					  		       </td>
			 			     	</tr>
		 			    	</table>
						</td>
					</tr>
					<tr>
						<td>
							<table class="innerReport" cellspacing="0" cellpadding="0" border="0"  width="100%">
								<tr>
							       <td width="50" valign="top">
					  		          <bean:message key="report.label.user_activity.interaction" /> :  
					  		       </td>
					  		       <td>&nbsp;		  		          
					  		          <img src="images/checkboxchecked.gif" id="activity0" onclick="javascript: changeActivity(this);">&nbsp;All&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="activity1" onclick="javascript: changeActivity(this);">&nbsp;Import&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="activity2" onclick="javascript: changeActivity(this);">&nbsp;Phone&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="activity3" onclick="javascript: changeActivity(this);">&nbsp;Emails&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="activity4" onclick="javascript: changeActivity(this);">&nbsp;Appointment&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="activity5" onclick="javascript: changeActivity(this);">&nbsp;Feedback&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="activity6" onclick="javascript: changeActivity(this);">&nbsp;Note&nbsp;&nbsp;
					  		          <img src="images/checkboxchecked.gif" id="activity7" onclick="javascript: changeActivity(this);">&nbsp;SMS&nbsp;&nbsp;
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
				                   <bean:message key="report.label.search_in"/> :&nbsp;
				                 </td>
				                 <td>  
				                   <script type="text/javascript">
						                    var opts = new Array();
						                    /*opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL%>','<bean:message key="common.selectlist.select"/>');*/
						                    opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
						                    opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
						                    opts[2] = new SelectOption('<%=ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS%>','<bean:message key="report.label.filter_open_and_onhold_positions"/>');
						                    opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
						                    opts[4] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
						                    selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL%>','images/btn_dropdown.gif',{namesonly:false, width:'175px', size:20});
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
							<div id="divSelectSpecificPosition" style="display:none;">
						         <table class="tabinput">
						           <tr>
						        	<td class="firstColumn">
								     <bean:message key="common.select_position"/> :
							        </td>
							        <td>
							         <script type="text/javascript">
								       var opts = new Array();
								       selectPosition = new SelectBox(new Array(),'','images/btn_dropdown.gif',{namesonly:false, width:'140px', size:20});
								       document.write(selectPosition.getHtml());
								       selectPosition.init();								       
							         </script>
							       </td>
						         </tr>
						        </table>
							</div>
							<div id="divSelectSpecificDepartment" style="display:none;">
						         <table class="tabinput">
						          <tr>
							        <td  class="firstColumn">
							        	<bean:message key="report.label.select_department"/> :
							        </td>
							       <td>
							          <script type="text/javascript">
								         var opts = <%=CommonUtils.getListJavaScriptArray(reportForm.getDepartmentIds(),reportForm.getDepartmentNames())%>;
								         var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
								         opts = m.concat(opts);
								         selectBoxDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
								         document.write(selectBoxDepartment.getHtml());
								         selectBoxDepartment.init();
								         
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
	//first do the validations
	//if specific department or position option selected
	val = selectFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		if(selectPosition.getSelectedId()=="-1"){
			alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
			return false;
		}
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		if(selectBoxDepartment.getSelectedId()=="-1"){
			alert('<bean:message key="report.error.select_department"/>');
			return false;
		}
	}
	if ($("stage1").src.indexOf(chkboxunchked) != -1 &&
		$("stage2").src.indexOf(chkboxunchked) != -1) {
		alert("Please select the stage.");
		return false;
	}
	
	if ($("activity1").src.indexOf(chkboxunchked) != -1 &&
			$("activity2").src.indexOf(chkboxunchked) != -1 &&
			$("activity3").src.indexOf(chkboxunchked) != -1 &&
			$("activity4").src.indexOf(chkboxunchked) != -1 &&
			$("activity5").src.indexOf(chkboxunchked) != -1 &&
			$("activity6").src.indexOf(chkboxunchked) != -1 &&
			$("activity7").src.indexOf(chkboxunchked) != -1) {
		alert("Please select the activity.");
		return false;
	}
	document.reportForm.filterId.value=val;
	document.reportForm.positionId.value=selectPosition.getSelectedId();
	document.reportForm.departmentId.value=selectBoxDepartment.getSelectedId();
	document.reportForm.stages.value=getSelectedStages();
	document.reportForm.activities.value=getSelectedActivities();
	document.reportForm.interviewers.value=checkBoxListInterviewers.getSelectedIds();
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

function onChangeFilter(val){	
	displayHidden(selectFilter.getSelectedId());
}

function fetchPositions() {
	var pars = "mode=getPositions";
	var myAjax = ajaxCall("reports.do",'get',pars,updatePositions, reportError);
}

function updatePositions(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) return;
	
	var optsPositions = new Array();
	optsPositions[0] = new SelectOption('-1','<bean:message key="common.selectlist.default"/>');
	var positions = xmlFile.getElementsByTagName("positions")[0];	
	var position=positions.getElementsByTagName("position");
	if(position!=null){
		for(var i=0; i<position.length; i++){
			var nodeId = position[i].getElementsByTagName("id");
			var nodeName = position[i].getElementsByTagName("name");
			var id = nodeId[0].firstChild.nodeValue;
			var name = nodeName[0].firstChild.nodeValue;
			optsPositions[optsPositions.length]=new SelectOption(id,name);
			
		}
	}
	
	selectPosition.reInitialize(optsPositions,"");
}

function displayHidden(val){
	if(val=='<%=ReportConstants.FILTER_SPECIFIC_POSITION%>'){
		$("divSelectSpecificPosition").style.display="block";
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=='<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>'){
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="block";
	} else {
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="none";
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

window.onload=doOnLoad;

function doOnLoad() {
	fetchPositions();
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>	
}

function changeStage(obj) {
	toggleImage(obj);
}

function toggleImage(obj) {
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
		toggleMaster(obj, chkboxunchked);
	} else {
		obj.src=chkboxchked;
		toggleMaster(obj, chkboxchked);
	}
}

function toggleMaster(obj, imageVar) {
	if (obj.id == "stage0") {
		$("stage1").src = imageVar;
		$("stage2").src = imageVar;		
	} else if (imageVar == chkboxchked) {
		if ($("stage1").src.indexOf(imageVar) != -1 &&
			$("stage2").src.indexOf(imageVar) != -1) {
			$("stage0").src = imageVar;
		}
	} else if (imageVar == chkboxunchked) {
		if ($("stage1").src.indexOf(imageVar) != -1 ||
			$("stage2").src.indexOf(imageVar) != -1) {
			$("stage0").src = imageVar;
		}
	}
}
function getSelectedStages() {
	stages = '';
	if ($("stage0").src.indexOf(chkboxchked) != -1) {
		return stages;	
	}
	if ($("stage1").src.indexOf(chkboxchked) != -1) {
		if (stages.length > 0) {
			stages += ', ';			
		}
		stages += '<%=PositionConstants.STEP_LEVEL_SELECT%>';
	}
	if ($("stage2").src.indexOf(chkboxchked) != -1) {
		if (stages.length > 0) {
			stages += ', ';			
		}
		stages += '<%=PositionConstants.STEP_LEVEL_ACCEPT%>';
	}	
	return stages;
}

function changeActivity(obj) {
	toggleActivityImage(obj);
}

function toggleActivityImage(obj) {
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
		toggleActivityMaster(obj, chkboxunchked);
	} else {
		obj.src=chkboxchked;
		toggleActivityMaster(obj, chkboxchked);
	}
}

function toggleActivityMaster(obj, imageVar) {
	if (obj.id == "activity0") {
		$("activity1").src = imageVar;
		$("activity2").src = imageVar;		
		$("activity3").src = imageVar;
		$("activity4").src = imageVar;		
		$("activity5").src = imageVar;
		$("activity6").src = imageVar;		
		$("activity7").src = imageVar;		
	} else if (imageVar == chkboxchked) {
		if ($("activity1").src.indexOf(imageVar) != -1 &&
			$("activity2").src.indexOf(imageVar) != -1 &&
			$("activity3").src.indexOf(imageVar) != -1 &&
			$("activity4").src.indexOf(imageVar) != -1 &&
			$("activity5").src.indexOf(imageVar) != -1 &&
			$("activity6").src.indexOf(imageVar) != -1 &&
			$("activity7").src.indexOf(imageVar) != -1) {
			$("activity0").src = imageVar;
		}
	} else if (imageVar == chkboxunchked) {
		if ($("activity1").src.indexOf(imageVar) != -1 &&
			$("activity2").src.indexOf(imageVar) != -1 &&
			$("activity3").src.indexOf(imageVar) != -1 &&
			$("activity4").src.indexOf(imageVar) != -1 &&
			$("activity5").src.indexOf(imageVar) != -1 &&
			$("activity6").src.indexOf(imageVar) != -1 &&
			$("activity7").src.indexOf(imageVar) != -1) {
			$("activity0").src = imageVar;
		}
	}
}
function getSelectedActivities() {
	activities = '';
	if ($("activity0").src.indexOf(chkboxchked) != -1) {
		return activities;	
	}
	if ($("activity1").src.indexOf(chkboxchked) != -1) {
		if (activities.length > 0) {
			activities += ',';			
		}
		activities += '<%=SelectionProcessConstants.INTERACTION_IMPORT%>';
	}
	if ($("activity2").src.indexOf(chkboxchked) != -1) {
		if (activities.length > 0) {
			activities += ',';			
		}
		activities += '<%=SelectionProcessConstants.INTERACTION_PHONE%>';
	}	
	if ($("activity3").src.indexOf(chkboxchked) != -1) {
		if (activities.length > 0) {
			activities += ',';			
		}
		activities += '<%=SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED%>' + ',' + '<%=SelectionProcessConstants.INTERACTION_EMAIL_SENT%>';
	}	
	if ($("activity4").src.indexOf(chkboxchked) != -1) {
		if (activities != '') {
			activities += ',';			
		}
		activities += '<%=SelectionProcessConstants.INTERACTION_APPOINTMENTS%>';
	}	
	if ($("activity5").src.indexOf(chkboxchked) != -1) {
		if (activities.length > 0) {
			activities += ',';			
		}
		activities += '<%=SelectionProcessConstants.INTERACTION_INTERVIEW%>';
	}	
	if ($("activity6").src.indexOf(chkboxchked) != -1) {
		if (activities.length > 0) {
			activities += ',';			
		}
		activities += '<%=SelectionProcessConstants.INTERACTION_NOTE%>';
	}	
	if ($("activity7").src.indexOf(chkboxchked) != -1) {
		if (activities.length > 0) {
			activities += ',';			
		}
		activities += '<%=SelectionProcessConstants.INTERACTION_SMS%>';
	}				  		          
	return activities;
}
</script>