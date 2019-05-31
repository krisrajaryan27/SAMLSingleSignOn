<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, com.talentPool.reports.form.ReportForm, com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.user.manager.ModuleSet,com.talentPool.common.properties.TPApplicationProperties"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css">	
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
	
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="mode" value="candidateComparison" />
	<html:hidden property="positionId"/>
	<html:hidden property="positionTitle"/>
	<html:hidden property="stepIds"/>
	<html:hidden property="applicants"/>
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:190px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.candidate_comparison" /></div></td> 
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
							     <bean:message key="common.select_position" />:
							</td>
							<td>
						         <script type="text/javascript">
							       var opts = new Array();
							       selectPosition = new SelectBox(new Array(),'','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:20});
							       selectPosition.setOnChangeHandler('onPositionChange');
							       document.write(selectPosition.getHtml());
							       selectPosition.init();								       
						         </script>
					         </td>
					    </tr>     
					    </table>     
						</td>
					</tr>
				</table>
				<script type="text/javascript">	
		 			var checkBoxListSteps=null;
		 		</script>
				<table class="innerReport" width="100%" id="stepsTbl" style="display:none;">						 			    
	                 <tr>
	  			 		<td width="120" valign="top"><bean:message key="report.label.select_steps" />:</td>
	  			 		<td>
					         <script type="text/javascript">
						      	checkBoxListSteps = new CheckBoxList(new Array(),'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:5, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
								checkBoxListSteps.setOnChangeHandler('onStepSelectionChange');
								document.write(checkBoxListSteps.getHtml());								
								checkBoxListSteps.init();							       
					         </script>
						</td>
					</tr>
				</table>
				<script type="text/javascript">	
		 			var checkBoxListApplicants=null;
		 		</script>
				<table class="innerReport" width="100%" id="applicantsTbl" style="display:none;">						 			    
	                 <tr>
	  			 		<td width="120" valign="top"><bean:message key="report.label.select_applicants" />:</td>
	  			 		<td>
					         <script type="text/javascript">
						      	checkBoxListApplicants = new CheckBoxList(new Array(),'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:5, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});								
								document.write(checkBoxListApplicants.getHtml());								
								checkBoxListApplicants.init();							       
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
<script language="JavaScript">

function validateSetFormFields(){
	if(selectPosition.getSelectedId()=="-1"){
		alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
		return false;
	}
	document.reportForm.positionId.value=selectPosition.getSelectedId();
	document.reportForm.positionTitle.value=selectPosition.getText(selectPosition.getSelectedIndex());
	document.reportForm.stepIds.value=checkBoxListSteps.getSelectedIds();
	document.reportForm.applicants.value=checkBoxListApplicants.getSelectedIds();
	return true;
}

function submitForm(){
	if( validateSetFormFields()){
		var d = new Date();
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

function onStepSelectionChange() {
	steps = checkBoxListSteps.getSelectedIds();
	var elem = document.getElementById("applicantsTbl");
	if (steps != '') {
		var pars = "mode=getApplicantsForStep&stepIds=" + steps;
		var myAjax = ajaxCall("reports.do",'get',pars,updateApplicants, reportError);
		elem.style.display='';
	} else {
		elem.style.display='none';	
	}
	
}

function updateApplicants(request){
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }
  //First remove all options
  var opts = new Array();
  
  var steps = xmlFile.getElementsByTagName("applicants")[0];
  var step= steps.getElementsByTagName("applicant");
  if(step != null) {
  	for(var i = 0; i < step.length; i++){  		
  		var id = step[i].getAttribute("id");
  		var title = step[i].firstChild.nodeValue;
  		opts[i] = new SelectOption(id, title);
  	}
  }
  checkBoxListApplicants.reInitialize(opts, '');
}

function onPositionChange() {
	positionId = selectPosition.getSelectedId();
	var elem = document.getElementById("stepsTbl");
	var elem1 = document.getElementById("applicantsTbl");
	if (positionId != -1) {
		var pars = "mode=getStepsInXml&positionId=" + positionId;
		var myAjax = ajaxCall("selectionProcess.do",'get',pars,updateSteps, reportError);
		elem.style.display='';
		elem1.style.display='none';	
	} else {
		elem.style.display='none';	
		elem1.style.display='none';	
	}
}

function updateSteps(request){
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }
  //First remove all options
  var opts = new Array();
  
  var steps = xmlFile.getElementsByTagName("steps")[0];
  var step= steps.getElementsByTagName("step");
  if(step != null) {
  	for(var i = 0; i < step.length; i++){  		
  		var id = step[i].getAttribute("id");
  		var title = step[i].firstChild.nodeValue;
  		opts[i] = new SelectOption(id, title);
  	}
  }
  //var m = [new SelectOption('-1', '<bean:message key='selection_feedback.label.selectStep' />')];
  //opts = m.concat(opts);
  checkBoxListSteps.reInitialize(opts, '');
}

window.onload=doOnLoad;

function doOnLoad() {
	fetchPositions();
	<% if(ModuleSet.isMODULE_REPORT_SCHEDULER()){ %>
		doOnLoadScheduledGrid();
	<% } %>	
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
</script>