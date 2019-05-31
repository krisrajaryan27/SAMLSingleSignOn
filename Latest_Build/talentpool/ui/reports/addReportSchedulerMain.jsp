<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="org.apache.struts.Globals,com.talentPool.reports.ReportConstants,com.talentPool.reports.form.ReportForm,com.talentPool.common.utils.CommonUtils,com.talentPool.positions.dataobject.PositionData,com.talentPool.user.dataobject.LoginData,com.talentPool.reports.dataobject.FilterData,com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.MessageConstants"%>
<%@page import="com.talentPool.reports.ReportVersionConstants"%>
<%@page import="com.talentPool.reports.manager.CustomizedReportManager"%>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/timePopUp.css">
<link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css">
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/calender/TimePopUp.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js"	type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/dropdiv.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script type="text/javascript">
var selectFrequency=null;
var selectDailyFrequency=null;
var selectWeeklyFrequency=null;
var selectMonthlyFrequency=null;

</script>

<logic:present name="update" scope="request">	
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present>

<%
ReportForm reportForm = (ReportForm) request.getAttribute("reportForm");
String reportLabel=(String)ReportVersionConstants.mapReportIdName.get(reportForm.getReportName());
if(!ReportVersionConstants.mapReportIdName.containsKey(reportForm.getReportName())) {
	reportLabel = new CustomizedReportManager().getCustomizedReportDataForReport(reportForm.getReportName()).getReportLabel();
}
%>
<div class="contentDivPop" style="width: 500px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
		<table  id="m_errortable" > 
			<tr>
			    <td class="header">
			        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
			</tr>
		    <tr>
		        <td class="message"><html:errors/></td>               
		    </tr>
		</table>
		<br>
	<% } 

	%>	
<div class="outerDiv">
<html:form action="/reportScheduler">
	<html:hidden property="reportName" />
	<html:hidden property="mode" value="validateAddReportScheduler" />
	<html:hidden property="frequencyOfScheduler" />
	<html:hidden property="dayOfWeek" />
	<html:hidden property="dayOfMonth" />
	<html:hidden property="everyDay" />
	<html:hidden property="weekDay" />
	<html:hidden property="scheduleId" />
	<html:hidden property="reportId" />
	<html:hidden property="users" />
	<html:hidden property="selectedUserIds" />
		
	<html:hidden property="filterData" />

	<html:hidden property="fromDate" />
	<html:hidden property="toDate" />
	<html:hidden property="reportName" />
	<html:hidden property="filterId" />
	<html:hidden property="positionId" />
	<html:hidden property="departmentId" />
	<html:hidden property="subDepartmentId" />
	<html:hidden property="subSubDepartmentId" />
	<html:hidden property="orderBy" />
	<html:hidden property="reportFormat" />
	<html:hidden property="fromMonth" />
	<html:hidden property="fromYear" />
	<html:hidden property="toMonth" />
	<html:hidden property="toYear" />
	<html:hidden property="userId" />
	<html:hidden property="sourceId" />
	<html:hidden property="sourceCategoryId" />
	<html:hidden property="reportType" />
	<html:hidden property="interviewers" />
	<html:hidden property="stages" />
	<html:hidden property="maxExp" />
	<html:hidden property="minExp" />
	<html:hidden property="degreeIds" />
	<html:hidden property="applicants" />
	<html:hidden property="stepIds" />
	<html:hidden property="users" />
	<html:hidden property="recruitmentCostReportType" />
	<html:hidden property="actionId" />
	<html:hidden property="dateRange" />
	<html:hidden property="numberRange" />
	<html:hidden property="positionFilter" />
	<html:hidden property="departmentFilter" />	
	<html:hidden property="activities" />

	<DIV id="autocomplete" class="autocomplete"></DIV>	
	<div class="popupTop">
	<table class="tblPop">		
	<tr>
		<td style="text-align: left; vertical-align: top; width: 80px;" class="header">Criteria :</td>
		<td>					
			<bean:write name="CriteriaData" scope="request" filter="false"/>
		</td>		
	</tr>	
	</table>
	<table class="tblPop" style="margin-top: 10px;">	
	<tr>
		<td class="header" style="text-align: left;width: 80px"><bean:message key="report_scheduler.label.frequency"/> :</td>	
		<td>
			<script type="text/javascript">
           	var opts = new Array();
           	opts[0] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_DAILY%>','<bean:message key="report_scheduler.label.daily"/>');
           	opts[1] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY%>','<bean:message key="report_scheduler.label.weekly"/>');
           	opts[2] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_MONTHLY%>','<bean:message key="report_scheduler.label.monthly"/>');
			selectFrequency = new SelectBox(opts,'<bean:write property="frequencyOfScheduler" name="reportForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'70px', size:20});	
			document.write(selectFrequency.getHtml());
			selectFrequency.setOnChangeHandler('onChangeFilter');
			selectFrequency.init();			        
	        </script>
	    </td>
	    <td class="header"><bean:message key="report_scheduler.label.on_every"/>
	    </td>
	    <td>
    		<div id="divSelectDaily" style="display:block; padding: 0px;margin: 0px;">
			<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<script type="text/javascript">
	            		var optsDaily = new Array();
	            		var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
	            		optsDaily[0] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_DAILY_WEEKDAY%>','<bean:message key="report_scheduler.label.daily.weekday"/>');
	    		        optsDaily[1] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_DAILY_EVERYDAY %>','<bean:message key="report_scheduler.label.daily.everyday"/>');
	            		optsDaily = m.concat(optsDaily);
						<logic:empty name="reportForm" property="weekDay">
							selectDailyFrequency = new SelectBox(optsDaily,'<bean:write property="everyDay" name="reportForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'95px', size:20});	
						</logic:empty>
						<logic:empty name="reportForm" property="everyDay">
							selectDailyFrequency = new SelectBox(optsDaily,'<bean:write property="weekDay" name="reportForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'95px', size:20});	
						</logic:empty>													
								
						document.write(selectDailyFrequency.getHtml());
						selectDailyFrequency.init();				        								
					</script>
				</td>
			</tr>
			</table>
			</div>
			<div id="divSelectWeekly" style="display:none;padding: 0px;margin: 0px;">
			<table cellpadding="0" cellspacing="0">
			<tr>						
				<td><script type="text/javascript">
	            	var optsWeekly = new Array();
	            	var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
	  		        	optsWeekly[0] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY_MONDAY%>','<bean:message key="report_scheduler.label.weekly.monday"/>');
	          			optsWeekly[1] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY_TUESDAY%>','<bean:message key="report_scheduler.label.weekly.tuesday"/>');
	          			optsWeekly[2] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY_WEDNESDAY%>','<bean:message key="report_scheduler.label.weekly.wednesday"/>');
	          			optsWeekly[3] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY_THURSDAY%>','<bean:message key="report_scheduler.label.weekly.thursday"/>');
	          			optsWeekly[4] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY_FRIDAY%>','<bean:message key="report_scheduler.label.weekly.friday"/>');
	          			optsWeekly[5] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY_SATURDAY%>','<bean:message key="report_scheduler.label.weekly.saturday"/>');
	          			optsWeekly[6] = new SelectOption('<%=ReportConstants.REPORT_SCHEDULER_WEEKLY_SUNDAY%>','<bean:message key="report_scheduler.label.weekly.sunday"/>');	           			
					optsWeekly = m.concat(optsWeekly);
					selectWeeklyFrequency = new SelectBox(optsWeekly,'<bean:write property="dayOfWeek" name="reportForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'95px', size:20});	
					document.write(selectWeeklyFrequency.getHtml());
					selectWeeklyFrequency.init();				        								
			        </script>
			    </td>
			</tr>
			</table>
			</div>
			<div id="divSelectMonthly" style="display:none;padding: 0px;margin: 0px;">
			<table cellpadding="0" cellspacing="0">
			<tr>
				<td><script type="text/javascript">
	            	var optsMonthly = new Array();			            	
	            	var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
	            	/*for(var i=0; i<31;i++){
	            		optsMonthly[i] = new SelectOption(i+1,i+1);
	            	}*/	           			
	            	for(var i=0; i<31;i++){
						if(i==0 || i==20 || i==30 ){
							optsMonthly[i] = new SelectOption(i+1,i+1+'st');
						} else if(i==1 || i==21 ){
							optsMonthly[i] = new SelectOption(i+1,i+1+'nd');
						} else if(i==2 || i==22 ){
							optsMonthly[i] = new SelectOption(i+1,i+1+'rd');
						}else{
							optsMonthly[i] = new SelectOption(i+1,i+1+'th');
						}	
	            	}	          	
					optsMonthly = m.concat(optsMonthly);
					selectMonthlyFrequency = new SelectBox(optsMonthly,'<bean:write property="dayOfMonth" name="reportForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'95px', size:20});	
					document.write(selectMonthlyFrequency.getHtml());
					selectMonthlyFrequency.init();				        								
			        </script>
			    </td>
			</tr>
			</table>
			</div>	
	    </td>
	    <td  class="header"><bean:message key="common.at"/>:			    
			<html:text property="scheduleTime" styleId="scheduleTime" size="10" maxlength="10" onblur="getFormattedTime(this); " /> <img src="images/clock.gif"	style="margin-bottom:-3px;cursor:hand;"	onclick="timePopUp.showTime(document.getElementById('scheduleTime'), 'scheduleTime'); return false;" />
		</td>
   	</tr> 
   	</table>
   	<table class="tblPop">
   	<tr>
		<td class="header" style="text-align: left;width: 80px;">
			<bean:message key="report_scheduler.label.start_date"/> :
		</td>
   		<td>
			<html:text property="startDate" styleId="startDate" size="12" maxlength="10" onblur="getFormattedDate(this);" /> 
			<img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('startDate'),'startDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;" />
		</td>
			
	</tr>
	</table>
	</div>
	<div class="popupBody">	
	<table class="tblPop">					
	<tr>
		<td>
			<table cellspacing="0" cellpadding="0">						
			<tr>
				<td class="header" style="text-align: left; padding-top: 10px;width: 80px;">
					<bean:message key="report_scheduler.label.email_to"/> :</td>				
				<td style="padding-top: 10px">
				<html:text property="userNameTo" name="reportForm" style="width:380px;" styleId="userNameTo"/> 
				<script>
					new Ajax.Autocompleter("userNameTo", "autocomplete", "selectionProcess.do?mode=getUsersAutoCompleteList&messageReceivedType=<%=MessageConstants.MESSAGE_RECEIVED_AS_TO%>", {frequency: 0.001, tokens: [',',';']});
				</script>
				</td>
			</tr>	        		        
			<tr>	
			
				<td class="header" style="text-align: left; padding-top: 10px;">
					<bean:message key="report_scheduler.label.email_subject"/> :</td>
				<td style="padding-top: 10px">
		          	  <html:text property="mailSubject" name="reportForm" style="width:380px;" styleClass="mailSubject"></html:text>                              
		        </td>
			</tr>
			
			</table>
		</td>	
	</tr>	
	<tr>
		<td class="header" style="text-align: left; padding-top: 10px"> <bean:message key="report_scheduler.label.message"/> </td>
	</tr>	
	<tr>	
		<td>
			<html:textarea property="mailBody"  name="reportForm" rows="10" cols="75"></html:textarea>
		</td>
	</tr>		
	</table>
	<table class="tblPop" cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td>
			<div class="navBtn"	style="float:right;margin-left:5px;margin-top:5px;">
			<a href="#" style="width:50px;" class="active" onclick="submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="common.save" /></a>					
			<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
		</td>
	</tr>
	</table>
	</div>
</html:form>
</div>
<DIV id="calendarPopUpDiv" style="position:absolute;background:#FFFFFF;z-index:1000;"></DIV>
<DIV id="timePopUpDiv" style="position:absolute;z-index:1000;background-color:#eee;display:none;"></DIV>
</div>
<script language="JavaScript">
var popUpCal = new CalendarPopup("calendarPopUpDiv"); 
popUpCal.showNavigationDropdowns();
var timePopUp = new TimePopUp("timePopUpDiv");

function onChangeFilter(val){
	val = selectFrequency.getSelectedId();
	if(val=="2"){
		displayHidden("2");		
	}else if(val=="3"){
		displayHidden("3");
	}else{
		displayHidden("1");
	}
}

function displayHidden(val){
	if(val=="1"){
		$("divSelectDaily").style.display="block";
		$("divSelectWeekly").style.display="none";
		$("divSelectMonthly").style.display="none";
	}else if(val=="2"){
		$("divSelectDaily").style.display="none";
		$("divSelectWeekly").style.display="block";
		$("divSelectMonthly").style.display="none";
	}else if(val=="3"){
		$("divSelectDaily").style.display="none";
		$("divSelectWeekly").style.display="none";
		$("divSelectMonthly").style.display="block";
	} 
}

function submitForm(){
	
	valFrequency = selectFrequency.getSelectedId();			
	if(valFrequency!="-1"){			
		if(valFrequency=="<%=ReportConstants.REPORT_SCHEDULER_DAILY%>"){
			if(selectDailyFrequency.getSelectedId()=="-1"){
				alert("Please select any daily option");
				return;
			}	
			if(selectDailyFrequency.getSelectedId()=="1"){				
				document.reportForm.everyDay.value=selectDailyFrequency.getSelectedId();
				document.reportForm.weekDay.value="";
			}else{
				document.reportForm.weekDay.value=selectDailyFrequency.getSelectedId();
				document.reportForm.everyDay.value="";
			}
		}else if(valFrequency=="<%=ReportConstants.REPORT_SCHEDULER_WEEKLY%>"){
			if(selectWeeklyFrequency.getSelectedId()=="-1"){
				alert("Please select any day of the week");
				return;
			}else{
				document.reportForm.dayOfWeek.value=selectWeeklyFrequency.getSelectedId();
			}			
		}else if(valFrequency=="<%=ReportConstants.REPORT_SCHEDULER_MONTHLY%>"){
			if(selectMonthlyFrequency.getSelectedId()=="-1"){
				alert("Please select any date of the month");
				return;
			}else{				
				document.reportForm.dayOfMonth.value=selectMonthlyFrequency.getSelectedId();
			}
		}
	}
		
	var startDate=document.getElementById('startDate').value;
	var scheduleTime=document.getElementById('scheduleTime').value;
		
	//document.reportForm.startDate.value
	if($("startDate").value==""){	
 		alert('Please enter start date');
 		$("startDate").focus();
 		return;
 	} 	
 	
 	if($("scheduleTime").value==""){
	 	alert('Please enter start time');
 		$("scheduleTime").focus();
 		return;
 	}
 	
 	document.reportForm.frequencyOfScheduler.value=valFrequency;		
	
 	if($("userNameTo").value!=""){
	 	var pars = "mode=validateNames&userNamesCc="+$("userNameTo").value;
	 	var myAjax = ajaxCall("selectionProcess.do","get",pars,checkValidToNamesOnsubmit,reportError);
 	}else{
 		alert('Please enter user name to send message');
 		$("userNameTo").focus();
 		return;
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



window.onload=doOnLoad;
function doOnLoad(){
	onChangeFilter();
  	setPopupTitle();  	
}


//TIME FORMATTER CODE AND FUNCTION
function getFormattedTime(obj) {
  val = obj.value.trim();
  var errFlag=false;
  timeSlotStr = '';
  if(val!=''){
    //(T = /^(\d\d|\d)(:|.|-)(\d\d|\d)\s?(([ap])\.?m\.?)?$/i.exec(val)
	 var T;
    if ((T = /^(\d\d|\d)(:|.|-)(\d\d|\d)\s?(([ap])\.?m\.?)?$/i.exec(val)) == null) {
        errFlag=true;
    }
    if (!errFlag && T[1] > 23) {
      errFlag = true;
    }
    if (!errFlag && T[3] >= 60) {
      errFlag = true;
    }
    if (!errFlag && T[4] == '') {
      if (T[1] > 12) {
        T[1] = T[1] - 12;
        timeSlotStr = 'PM';
      } else {
        if (T[1] >= 1 && T[1] <= 8) {
          timeSlotStr = 'PM';
        } else if (T[1] > 8 && T[1] < 12) {
          timeSlotStr = 'AM';
        } else {
          timeSlotStr = 'PM';
        }
      }
    } else if (!errFlag) {
      timeSlotStr = T[4].toUpperCase();
    }
    if (!errFlag) {
      if (T[1].length == 1) {
        T[1] = '0' + T[1];
      }
      if (T[3].length == 1) {
        T[3] = '0' + T[3];
      }
      obj.value = T[1] + ':' + T[3] + ' ' + timeSlotStr;
    } else {
      alert("Please enter time in hh:mm format.");
      obj.focus();
    }    
  }
  return true;  
}


function checkValidToNames(request){
	var namesNotFound = checkValidNames(request);
	if(namesNotFound!=""){
		alert(namesNotFound + ' does not exist.');
		$('userNameTo').focus();
	}
	
}

function checkValidToNamesOnsubmit(request){
	var namesNotFound = checkValidNames(request);
	if(namesNotFound!=""){
		alert(namesNotFound + ' does not exist.');
		$('userNameTo').focus();
	}else{
		document.reportForm.submit();
	}
}
 

function checkValidNames(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		return;
	}
	//display names not avilable
	var names = xmlFile.getElementsByTagName("names")[0];	
	var namesNotFound = getSingleElement(names,"name","").escapeHTML();
	return namesNotFound;
	
}

function setPopupTitle(){
	var title = '<b>Report Scheduler [ <%=reportLabel%> ]</b>';
	window.top.setPopTitle(title);
}

</script>
