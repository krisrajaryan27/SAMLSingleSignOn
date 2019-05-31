<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.positions.dataobject.PositionData,
                  com.talentPool.common.properties.TPApplicationProperties,
                  com.talentPool.common.properties.GlobalApplicationProperties,
                  com.talentPool.common.properties.GlobalConstants,
                  com.talentPool.common.NavigationConstants,
                  com.talentPool.calendar.CalendarConstants,
                  com.talentPool.user.manager.ModuleSet,
                  java.util.Calendar, java.util.GregorianCalendar,
                  java.util.TimeZone, com.talentPool.user.utils.UserUtils" %>

<link rel="STYLESHEET" type="text/css" href="themes/default/dhtmlXMenu.css">
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">           
<%
  boolean userSMSEnabled = false;
  if(ModuleSet.isMODULE_SMS() && GlobalConstants.ENABLED.equalsIgnoreCase(GlobalApplicationProperties.getProperty("sms_enabled"))){
%>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SEND_SMS">
<% userSMSEnabled = true; %>
</logic:equal>
<% 
  }
%>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/tpcalendar.css" /> 
<link rel="stylesheet" type="text/css" href="themes/default/timePopUp.css"> 

<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script> 
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/calender/tpcalendar.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/draggablePopUp.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/TimePopUp.js" type="text/javascript"></script>
<script src="js/cookies.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>

<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script src="js/tpSelectListFunctions.js"></script> 		
<bean:define id="positions" name="positions" scope="request" type="java.util.List"/>

<script language="JavaScript">
var selectBoxStatus;
var selectBoxModeStatus;
var dataGridInterviewer = null;
var 	dataGridInterviewerUser= null;

function checkSessionExpiry(request) {
  xmlFile = request.responseXML;  
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return false;
  }
  return true;
}
function fetchAppointmentsForCurrentWeek(request){
    if (request != null) {
    	xmlFile = request.responseXML;
	    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
	      return;
	    }
    }
    var myAppointments =showMyAppointments();  
    var pars = "mode=getAppointmentXml&currentWeekDays=" + document.calendarForm.currentWeekDays.value+"&showMyAppointments="+myAppointments;
	var myAjax = ajaxCall("calendar.do",'get',pars,updateAppointments, reportError);
}
var chkedRadio = "images/checkedradiobutton.gif";
var unChkedRadio = "images/radiobutton.gif";
var chkedCheckBox = "images/checkboxchecked.gif";
var unChkedCheckBox = "images/checkboxunchecked.gif";
function linkOperations(obj, operation) {        
  if (operation == 'show') {    
    displayStyle1 = '';
    displayStyle2 = 'none';    
    selectDay(obj);
  } else if (operation == 'hide') {
    displayStyle1 = 'none';
    displayStyle2 = '';    
  }
  alterDisplay(obj, displayStyle1, displayStyle2);
  return false;
}

function selectDay(obj) {
  clearSelection();
  var elem1 = document.getElementById('day' + obj);
  var elem2 = document.getElementById('day00' + obj);
  var elem3 = document.getElementById('day000' + obj);
  elem1.className = elem1.className + ' dtSelected';
  elem2.className = elem2.className + ' weekDaySelected';
  elem3.className = elem3.className + ' daySelected';
}

function clearSelection() {
  for (var i = 0; i < 7; i++) {
     var elem1 = document.getElementById('day' + i);
     var elem2 = document.getElementById('day00' + i);
     var elem3 = document.getElementById('day000' + i);
     
     temp = elem1.className;
     parts = temp.split(' ');
     if (parts.length == 2) {  
       elem1.className = parts[0];       
     }
     
     temp = elem2.className;
     parts = temp.split(' ');
     if (parts.length == 2) {       
       elem2.className = parts[0];       
     }
     
     temp = elem3.className;
     parts = temp.split(' ');
     if (parts.length == 2) {  
       elem3.className = parts[0];       
     }
     
     alterDisplay(i, 'none', '');
  }
}

function alterDisplay(obj, displayStyle1, displayStyle2) {
  var elem1 = document.getElementById('appt' + obj);
  if (elem1) {
     elem1.style.display = displayStyle1;
  } 
  var elem2 = document.getElementById('prnView' + obj);
  if (elem2) {
     elem2.style.display = displayStyle1;
  }  
  var elem5 = document.getElementById('reminder' + obj);
  if (elem5) {
     elem5.style.display = displayStyle1;
  }  
  var elem3 = document.getElementById('sp0' + obj);
  if (elem3) {  
     elem3.style.display = displayStyle2;
  } 
  var elem4 = document.getElementById('sp1' + obj);
  if (elem4) {
     elem4.style.display = displayStyle2;
  } 
  var elem6 = document.getElementById('sp2' + obj);
  if (elem6) {
     elem6.style.display = displayStyle2;
  } 
}
function continueToSetAppointment(hideLayer, showLayer) {  
  var position = selectBoxPosition.getSelectedId();
  var applicant = selectBoxApplicant.getSelectedId();
  var errMsg = '';
  if (position == '-1') {
    errMsg += '<bean:message key='common.please_select' /> <bean:message key='common.position' />';
  }
  if (applicant == '-1') {
    if (errMsg.length > 0) {
      errMsg += '\n';
    }
    errMsg += '<bean:message key='new_appointment.error.selectApplicant' />';
  }
  if (errMsg.length > 0) {
    alert(errMsg);
    return false;
  }
  hide(hideLayer);   
  var pars = "mode=getNewAppointmentXml&selectedApplicant=" + applicant;
  var myAjax = ajaxCall("calendar.do",'get',pars,UpdateSetAppointmentScreen, reportError);
  return false;
}

function UpdateSetAppointmentScreen(request){  
	
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {    
    return;
  }  
  isAppointmentFullyEditable = true;
  var appointment = xmlFile.getElementsByTagName("setAppointmentData")[0];
  
  // Candidate
  setCandidate(appointment);
  
  // Subject
  setSubject(appointment);
  
  elem1 = document.getElementById("timeInput").style.display = '';
  elem2 = document.getElementById("timeText").style.display = 'none';
  elem3 = document.getElementById("dateInput").style.display = '';
  elem4 = document.getElementById("dateText").style.display = 'none';
  // Set default values for date and time.
  document.calendarForm.date.value = document.calendarForm.appointmentDate.value;
  getFormattedDate(document.calendarForm.date)
  document.calendarForm.time.value = '';
  
  // Status of Appointment
  setStatus(appointment);
  
  // Reminders of Appointment
  setNotifications(appointment);
  
  // Interviewers
  setInterviewers(appointment);
  setTimeZone(appointment);
  
  // Select default duration.
  setDuration('<%=GlobalApplicationProperties.getProperty("default_appointment_duration")%>');
  show('setAppointmentLayer');
  var elem = document.getElementById("saveBtnLink");
  var elem2 = document.getElementById("deleteBtnLink");
  elem.style.display = '';	  
  elem2.style.display = 'none';
  drop();
}

function UpdateEditAppointmentScreen(request){
	
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }

  var appointment = xmlFile.getElementsByTagName("setAppointmentData")[0];
  // Candidate
  setCandidate(appointment);
  
  // Subject  
  setSubject(appointment); 
  
  // Set date and time.
  setTimeValue(appointment);
  setTimeZone(appointment);
  // Status of Appointment
  setStatus(appointment);
  
  // Reminders of Appointment
  setNotifications(appointment);
  
  // Interviewers  
  setInterviewers(appointment);
  
  // Set duration.
  setDurationVal(appointment);
  
  setInterviewDetails(appointment);
  setInterview(appointment);
  
  show('setAppointmentLayer');
  
  var elem = document.getElementById("saveBtnLink");
  var elem2 = document.getElementById("deleteBtnLink");
  if (!isAppointmentFullyEditable) {
	   elem.style.display = 'none';
	   elem2.style.display = 'none';
  } else {
	   elem.style.display = '';	  
	   elem2.style.display = '';	  
  }  
  drop();  
}

function setCandidate(appointment) {
  // var candidate= getSingleElement(appointment, "candidate", "");
  var candidate = appointment.getElementsByTagName("candidate")[0];
  var candidateId = candidate.getAttribute("id");
  if (candidateId != null && candidateId != '') {
  	document.calendarForm.selectedApplicant.value = candidateId;
  }  
  var elem = $("candidate");
  if (elem) {
    elem.innerHTML = candidate.firstChild.nodeValue.escapeHTML();
  }
}

function setInterviewDetails(appointment){
	 var detailsInterviewMode= getSingleElement(appointment, "detailsInterviewMode", "");
	 if(detailsInterviewMode==''){
		 detailsInterviewMode="Deatils of Interview Not available";
	 }
	 
	  elem2= document.getElementById("detailsInterviewModeEdit");
	
	  if(elem2){
		 /*  elem2.value=detailsInterviewMode.escapeHTML(); */
		  document.getElementById("detailsInterviewModeEdit").innerHTML = detailsInterviewMode.escapeHTML(); 
		 }
	  if (isAppointmentFullyEditable) {
	   		 
	   		 document.getElementById('detailsInterviewModeNew').style.display = '';
	   		 document.getElementById('detailsInterviewModeEdit').style.display = 'none';
	   		 
	    }else{
	    	 document.getElementById('detailsInterviewModeNew').style.display = 'none';
	      	 document.getElementById('detailsInterviewModeEdit').style.display = '';
	      
	    }
}
function setInterview(appointment){
	 var interviewMode= getSingleElement(appointment, "interviewMode", "");
	 if(interviewMode==''){
		 interviewMode="Not Specified";
	 }
	  elem3=  document.getElementById("interviewModeEdit");
	  if(elem3){
		  document.getElementById("interviewModeEdit").innerHTML = interviewMode.escapeHTML(); 
	  }
	 if (isAppointmentFullyEditable) {
   		 
   		 document.getElementById('interviewModeNew').style.display = '';
   		 document.getElementById('interviewModeEdit').style.display = 'none';
   		 
    }else{
    	 document.getElementById('interviewModeNew').style.display = 'none';
      	 document.getElementById('interviewModeEdit').style.display = '';
      
    }
}


function setTimeZone(appointment) {
	var elem = document.getElementById("timezoneEdit");
 	if (isAppointmentFullyEditable) {
 		elem.style.display = '';
 	} else {
 		elem.style.display = 'none';
 		return;
 	}
 	var userTimeZone = '';
 	var timeZoneInfoElem = appointment.getElementsByTagName("timeZoneInfo")[0];

 	if (timeZoneInfoElem != null) {
  	timeZoneElems = timeZoneInfoElem.getElementsByTagName("timeZone");
   	for (var i = 0; i < timeZoneElems.length; i++) {
    	 var value = timeZoneElems[i].getAttribute("value");
     	var text = timeZoneElems[i].firstChild.nodeValue;    
     	if (timeZoneElems[i].getAttribute("checked") != null) {
     		userTimeZone = value;
     	} 
   	  }
 	}  
 	var TimeZone = <%=UserUtils.getJSTimeZoneArray()%>;	
 	selectBoxStatus = new SelectBox(TimeZone,userTimeZone,'images/btn_dropdown.gif',{namesonly:false, width:'250px'});
	selectBoxStatus.setOnChangeHandler('onChangeStatus');
 	document.getElementById("timeZone").innerHTML = selectBoxStatus.getHtml();
 	selectBoxStatus.init();
}


function setSubject(appointment) {
  var candidate= getSingleElement(appointment, "candidate", "");
  var subject = getSingleElement(appointment, "subject", "");
  if (subject == '') {
    stepElem = appointment.getElementsByTagName("step")[0];
    var step = stepElem.firstChild.nodeValue;
    var stepId = stepElem.getAttribute("id");
    document.calendarForm.applicantStepId.value = stepId;
    positionElem = appointment.getElementsByTagName("position")[0];
    var position = positionElem.firstChild.nodeValue;  
    var positionId = positionElem.getAttribute("id");
    document.calendarForm.applicantPositionId.value = positionId;
    subject = step + ' for ' + position;  
  }
  var temp = candidate + ' ' + subject
  if (temp.length > 47) {
    subject = subject.substring(0, (47 - candidate.length - 3)) + '...';
  }
  elem = document.getElementById("subject");
  if (elem) {
    elem.innerHTML = subject.escapeHTML();
  } 
}

function setInterviewers(appointment) {  
  interviewers = '';
  
  var interviewersElem = appointment.getElementsByTagName("interviewers")[0];
  if (interviewersElem != null) {
    elem = interviewersElem.getElementsByTagName("interviewer");
    var str = '';
    for (var i = 0; i < elem.length; i++) {
      var id = elem[i].getAttribute("id");
  	  var text = elem[i].firstChild.nodeValue;
  	  var checked = elem[i].getAttribute("checked");
  	  if (!isAppointmentFullyEditable) {
  	    if (checked == 'checked') {
     	  if (str.length > 0) {
     	    str += ', ';
     	  }
     	  str += text;
     	}
     	
  	  } else {
  	    if (checked == 'checked') {
     	
    	  if (interviewers.length > 0) {
    	    interviewers += ',';
    	  }
    	  interviewers += id;
    	}  
  	 
  	  }
  	 
    }   
    $('interviewer').innerHTML = str;
    if (isAppointmentFullyEditable) {
   		 initGridInterviewer(interviewers);
   		 document.getElementById('intRow1').style.display = 'none';
   		 document.getElementById('intRow2').style.display = '';
   		 
    }else{
    	 document.getElementById('intRow1').style.display = '';
       document.getElementById('intRow2').style.display = 'none';
      
    }
  }  
}

remindMeBox = null;
remindInterviewersBox = null;
remindCandidateBox = null;
smsRemindMeBox = null;
smsRemindInterviewersBox = null;
smsRemindCandidateBox = null;
function setNotifications(appointment) {
  var notifications = appointment.getElementsByTagName("notifications")[0];
  if (notifications != null) {
  	selectBoxRemindMeTemplate.showMe(); 
    selectBoxRemindInterviewerTemplate.showMe(); 
    selectBoxRemindApplicantTemplate.showMe();
      
    notification = notifications.getElementsByTagName("notification");
    reminder = new Array();
        
    var selectedIndx = -1;
    var selectedIndxRemindMe = -1;
    var selectedIndxRemindInterviewer = -1;
    var selectedIndxRemindCandidate = -1;
   	    
    var selRemindMe = getSingleElement(appointment, "remindMe", "");
    var selRemindInterviewer = getSingleElement(appointment, "remindInterviewer", "");
    var selRemindCandidate = getSingleElement(appointment, "remindCandidate", "");
    
    var selRemindMeTemplate = getSingleElement(appointment, "remindMeTemplate", "");
    var selRemindInterviewerTemplate = getSingleElement(appointment, "remindInterviewerTemplate", "");
    var selRemindCandidateTemplate = getSingleElement(appointment, "remindCandidateTemplate", "");
    
    if (isAppointmentFullyEditable) {
      for (var i = 0; i < notification.length; i++) {
        var value = notification[i].getAttribute("value");
        var text = notification[i].firstChild.nodeValue;
        
        if (value == selRemindMe) {
          selectedIndxRemindMe=i;
        } 
        if (value == selRemindInterviewer) {
          selectedIndxRemindInterviewer=i;
        } 
        if(value == selRemindCandidate) {
          selectedIndxRemindCandidate=i;
        }        
        if (value == '<%=GlobalApplicationProperties.getProperty("default_reminder_duration")%>') {
          selectedIndx=i;
        }      
        reminder[i] = new SelectOption(value, text);
      }
      remindMeBox = new SelectBox(reminder,selectedIndx,'images/btn_dropdown.gif',{namesonly:false, width:'120px'});
      document.getElementById("remindMe").innerHTML = remindMeBox.getHtml();
      remindMeBox.init();
      
      remindInterviewersBox = new SelectBox(reminder,selectedIndx,'images/btn_dropdown.gif',{namesonly:false, width:'120px'});
      document.getElementById("remindInterviewers").innerHTML = remindInterviewersBox.getHtml();
      remindInterviewersBox.init();
    
      remindCandidateBox = new SelectBox(reminder,selectedIndx,'images/btn_dropdown.gif',{namesonly:false, width:'120px'});
      document.getElementById("remindCandidate").innerHTML = remindCandidateBox.getHtml();
      remindCandidateBox.init();
      
      document.getElementById('isRemindMe').style.display = '';
      document.getElementById('isRemindInterviewers').style.display = '';
      document.getElementById('isRemindCandidate').style.display = '';
            
      if (selRemindMe != "") {      	      	
        if (selRemindMe == "0") {
          remindMeBox.setSelected(selectedIndx);
          remindMeBox.disable();
          selectBoxRemindMeTemplate.setSelected(selectBoxRemindMeTemplate.getIndexWithId(defaultRemindMeTemplate));
          selectBoxRemindMeTemplate.disable();
          remindMe = 0;          
          document['isRemindMe'].src = unChkedCheckBox;
        } else {
          remindMeBox.setSelected(selectedIndxRemindMe);
          selectBoxRemindMeTemplate.enable();    
          selectBoxRemindMeTemplate.setSelected(selectBoxRemindMeTemplate.getIndexWithId(selRemindMeTemplate));      
          document['isRemindMe'].src = chkedCheckBox;
          remindMe = 1;
        }
      } else {
        remindMeBox.setSelected(selectedIndx);
        selectBoxRemindMeTemplate.enable();
        selectBoxRemindMeTemplate.setSelected(selectBoxRemindMeTemplate.getIndexWithId(defaultRemindMeTemplate));
        document['isRemindMe'].src = chkedCheckBox;
        remindMe = 1;
      }
      
      if (selRemindInterviewer != "") {      	
        if (selRemindInterviewer == "0") {
          remindInterviewersBox.setSelected(selectedIndx);
          remindInterviewersBox.disable();
          selectBoxRemindInterviewerTemplate.setSelected(selectBoxRemindInterviewerTemplate.getIndexWithId(defaultRemindInterviewerTemplate));
          selectBoxRemindInterviewerTemplate.disable();
          remindInterviewers = 0;
          document['isRemindInterviewers'].src = unChkedCheckBox;
        } else {
          remindInterviewersBox.setSelected(selectedIndxRemindInterviewer);
          selectBoxRemindInterviewerTemplate.enable();
          selectBoxRemindInterviewerTemplate.setSelected(selectBoxRemindInterviewerTemplate.getIndexWithId(selRemindInterviewerTemplate));
          remindInterviewers = 1;
          document['isRemindInterviewers'].src = chkedCheckBox;
        }
      } else {
        remindInterviewersBox.setSelected(selectedIndx);
        selectBoxRemindInterviewerTemplate.enable();
        selectBoxRemindInterviewerTemplate.setSelected(selectBoxRemindInterviewerTemplate.getIndexWithId(defaultRemindInterviewerTemplate));
        remindInterviewers = 1;
        document['isRemindInterviewers'].src = chkedCheckBox;
      }     
            
      if (selRemindCandidate != "") {      	
        if (selRemindCandidate == "0") {
          remindCandidateBox.setSelected(selectedIndx);
          remindCandidateBox.disable();
          selectBoxRemindApplicantTemplate.disable();
          selectBoxRemindApplicantTemplate.setSelected(selectBoxRemindApplicantTemplate.getIndexWithId(defaultRemindApplicantTemplate));
          remindCandidate = 0;
          document['isRemindCandidate'].src = unChkedCheckBox;
        } else {
          remindCandidateBox.setSelected(selectedIndxRemindCandidate);
          selectBoxRemindApplicantTemplate.enable();
          selectBoxRemindApplicantTemplate.setSelected(selectBoxRemindApplicantTemplate.getIndexWithId(selRemindCandidateTemplate));
          remindCandidate = 1;
          document['isRemindCandidate'].src = chkedCheckBox;
        }
      } else {
        remindCandidateBox.setSelected(selectedIndx);
        selectBoxRemindApplicantTemplate.enable();
        selectBoxRemindApplicantTemplate.setSelected(selectBoxRemindApplicantTemplate.getIndexWithId(defaultRemindApplicantTemplate));
        remindCandidate = 1;
        document['isRemindCandidate'].src = chkedCheckBox;
      }        
    } else {
      document.getElementById('isRemindMe').style.display = 'none';
      document.getElementById('isRemindInterviewers').style.display = 'none';
      document.getElementById('isRemindCandidate').style.display = 'none';
      
      document.getElementById("remindMe").innerHTML = getDescText(selRemindMe, notification);
      document.getElementById("remindInterviewers").innerHTML = getDescText(selRemindInterviewer, notification);
      document.getElementById("remindCandidate").innerHTML = getDescText(selRemindCandidate, notification);  
      
      selectBoxRemindMeTemplate.hideMe(); 
      selectBoxRemindInterviewerTemplate.hideMe(); 
      selectBoxRemindApplicantTemplate.hideMe();       
    } 
      
    smsRemindMe = 0;
    smsRemindInterviewers = 0;
    smsRemindCandidate = 0;
    /*************************************************************************************/
    <% if (userSMSEnabled) { %>
    var selectedIndxSMSRemindMe = -1;
    var selectedIndxSMSRemindInterviewer = -1;
    var selectedIndxSMSRemindCandidate = -1;
    
    var selSMSRemindMe = getSingleElement(appointment, "smsRemindMe", "");
    var selSMSRemindInterviewer = getSingleElement(appointment, "smsRemindInterviewer", "");
    var selSMSRemindCandidate = getSingleElement(appointment, "smsRemindCandidate", "");
    
    var selSMSRemindMeTemplate = getSingleElement(appointment, "smsRemindMeTemplate", "");
    var selSMSRemindInterviewerTemplate = getSingleElement(appointment, "smsRemindInterviewerTemplate", "");
    var selSMSRemindCandidateTemplate = getSingleElement(appointment, "smsRemindCandidateTemplate", "");
    
    if (isAppointmentFullyEditable) {
      selectBoxSmsRemindMeTemplate.showMe(); 
      selectBoxSmsRemindInterviewerTemplate.showMe(); 
      selectBoxSmsRemindApplicantTemplate.showMe();   
      
      for (var i = 0; i < notification.length; i++) {
        var value = notification[i].getAttribute("value");
        var text = notification[i].firstChild.nodeValue;
                
        if (value == selSMSRemindMe) {
          selectedIndxSMSRemindMe=i;
        } 
        if (value == selSMSRemindInterviewer) {
          selectedIndxSMSRemindInterviewer=i;
        } 
        if(value == selSMSRemindCandidate) {
          selectedIndxSMSRemindCandidate=i;
        } 
        if (value == '<%=GlobalApplicationProperties.getProperty("default_reminder_duration")%>') {
          selectedIndx=i;
        }  
      }
      smsRemindMeBox = new SelectBox(reminder,selectedIndx,'images/btn_dropdown.gif',{namesonly:false, width:'120px'});
	  document.getElementById("smsRemindMe").innerHTML = smsRemindMeBox.getHtml();
	  smsRemindMeBox.init();
	
	  smsRemindInterviewersBox = new SelectBox(reminder,selectedIndx,'images/btn_dropdown.gif',{namesonly:false, width:'120px'});
	  document.getElementById("smsRemindInterviewers").innerHTML = smsRemindInterviewersBox.getHtml();
	  smsRemindInterviewersBox.init();
	
	  smsRemindCandidateBox = new SelectBox(reminder,selectedIndx,'images/btn_dropdown.gif',{namesonly:false, width:'120px'});
	  document.getElementById("smsRemindCandidate").innerHTML = smsRemindCandidateBox.getHtml();
	  smsRemindCandidateBox.init();
	
	  document.getElementById('isSMSRemindMe').style.display = '';
      document.getElementById('isSMSRemindInterviewers').style.display = '';
      document.getElementById('isSMSRemindCandidate').style.display = '';
    
      if (selSMSRemindMe != "") {      	
        if (selSMSRemindMe == "0") {
          smsRemindMeBox.setSelected(selectedIndx);
          smsRemindMeBox.disable();
          selectBoxSmsRemindMeTemplate.disable();
          selectBoxSmsRemindMeTemplate.setSelected(selectBoxSmsRemindMeTemplate.getIndexWithId(defaultSmsRemindMeTemplate));
          smsRemindMe = 0;          
          document['isSMSRemindMe'].src = unChkedCheckBox;
        } else {
          smsRemindMeBox.setSelected(selectedIndxSMSRemindMe);
          selectBoxSmsRemindMeTemplate.enable();
          selectBoxSmsRemindMeTemplate.setSelected(selectBoxSmsRemindMeTemplate.getIndexWithId(selSMSRemindMeTemplate));
          document['isSMSRemindMe'].src = chkedCheckBox;
          smsRemindMe = 1;
        }
      } else {
      	selectBoxSmsRemindMeTemplate.setSelected(selectBoxSmsRemindMeTemplate.getIndexWithId(defaultSmsRemindMeTemplate));
        smsRemindMeBox.setSelected(selectedIndx);
        selectBoxSmsRemindMeTemplate.enable();
        document['isSMSRemindMe'].src = chkedCheckBox;
        smsRemindMe = 1;
      }      
      
      if (selSMSRemindInterviewer != "") {      	
        if (selSMSRemindInterviewer == "0") {
          smsRemindInterviewersBox.setSelected(selectedIndx);
          smsRemindInterviewersBox.disable();
          selectBoxSmsRemindInterviewerTemplate.disable();
          selectBoxSmsRemindInterviewerTemplate.setSelected(selectBoxSmsRemindInterviewerTemplate.getIndexWithId(defaultSmsRemindInterviewerTemplate));
          smsRemindInterviewers = 0;
          document['isSMSRemindInterviewers'].src = unChkedCheckBox;
        } else {
          smsRemindInterviewersBox.setSelected(selectedIndxSMSRemindInterviewer);
          selectBoxSmsRemindInterviewerTemplate.enable();
          selectBoxSmsRemindInterviewerTemplate.setSelected(selectBoxSmsRemindInterviewerTemplate.getIndexWithId(selSMSRemindInterviewerTemplate));
          smsRemindInterviewers = 1;
          document['isSMSRemindInterviewers'].src = chkedCheckBox;
        }
      } else {
        smsRemindInterviewersBox.setSelected(selectedIndx);
        selectBoxSmsRemindInterviewerTemplate.enable();
        selectBoxSmsRemindInterviewerTemplate.setSelected(selectBoxSmsRemindInterviewerTemplate.getIndexWithId(defaultSmsRemindInterviewerTemplate));
        smsRemindInterviewers = 1;
        document['isSMSRemindInterviewers'].src = chkedCheckBox;
      }
      
      if (selSMSRemindCandidate != "") {      	
        if (selSMSRemindCandidate == "0") {
          smsRemindCandidateBox.setSelected(selectedIndx);
          smsRemindCandidateBox.disable();
          selectBoxSmsRemindApplicantTemplate.disable();
          selectBoxSmsRemindApplicantTemplate.setSelected(selectBoxSmsRemindApplicantTemplate.getIndexWithId(defaultSmsRemindApplicantTemplate));
          smsRemindCandidate = 0;
          document['isSMSRemindCandidate'].src = unChkedCheckBox;
        } else {
          smsRemindCandidateBox.setSelected(selectedIndxSMSRemindCandidate);
          selectBoxSmsRemindApplicantTemplate.enable(); 
          selectBoxSmsRemindApplicantTemplate.setSelected(selectBoxSmsRemindApplicantTemplate.getIndexWithId(selSMSRemindCandidateTemplate));         
          smsRemindCandidate = 1;
          document['isSMSRemindCandidate'].src = chkedCheckBox;
        }
      } else {
        smsRemindCandidateBox.setSelected(selectedIndx);
        selectBoxSmsRemindApplicantTemplate.enable();
        selectBoxSmsRemindApplicantTemplate.setSelected(selectBoxSmsRemindApplicantTemplate.getIndexWithId(defaultSmsRemindApplicantTemplate));
        smsRemindCandidate = 1;
        document['isSMSRemindCandidate'].src = chkedCheckBox;
      }
  	} else {      
      document.getElementById('isSMSRemindMe').style.display = 'none';
      document.getElementById('isSMSRemindInterviewers').style.display = 'none';
      document.getElementById('isSMSRemindCandidate').style.display = 'none';
      
      document.getElementById("smsRemindMe").innerHTML = getDescText(selSMSRemindMe, notification);
      document.getElementById("smsRemindInterviewers").innerHTML = getDescText(selSMSRemindInterviewer, notification);
      document.getElementById("smsRemindCandidate").innerHTML = getDescText(selSMSRemindCandidate, notification);
      
      selectBoxSmsRemindMeTemplate.hideMe(); 
      selectBoxSmsRemindInterviewerTemplate.hideMe(); 
      selectBoxSmsRemindApplicantTemplate.hideMe();   
    } 	
    <% } %>
  }
}

function getDescText(val, notification) {
  if (val == 0) {
    //return 'Do not remind';
    return '<bean:message key="calendar.label.do_not_remind"/>';
  } else {    
    for (var i = 0; i < notification.length; i++) {      
      var value = notification[i].getAttribute("value");
      var text = notification[i].firstChild.nodeValue;  
      if (val == value) {
        return text;
      }
    }
  }  
}


var statusBox;
function setStatus(appointment) {
  var elem = document.getElementById("changeStatusRow");
  if (isAppointmentFullyEditable) {
  	elem.style.display = '';
  } else {
  	elem.style.display = 'none';
  	return;
  }
  var selStatus = '-1';
  var statusInfoElem = appointment.getElementsByTagName("statusInfo")[0];
 
  if (statusInfoElem != null) {
    statusElems = statusInfoElem.getElementsByTagName("status");
    statusValues = new Array();
    for (var i = 0; i < statusElems.length; i++) {
      var value = statusElems[i].getAttribute("value");
      var text = statusElems[i].firstChild.nodeValue;    
      if (statusElems[i].getAttribute("checked") != null) {
        selStatus = value;
      } 
      statusValues[i] = new SelectOption(value, text);
    }
  }  
  var m = [new SelectOption('-1', '<bean:message key="calendar.label.selectStatusMessage"/>')];
  statusValues = m.concat(statusValues);
  statusBox = new SelectBox(statusValues,selStatus,'images/btn_dropdown.gif',{namesonly:false, width:'250px'});
  document.getElementById("status").innerHTML = statusBox.getHtml();
  statusBox.init();
}

function setTimeValue(appointment) {
  var from = getSingleElement(appointment, "from", "");
  var fromDate = getSingleElement(appointment, "fromDate", "");
  var fromTime = getSingleElement(appointment, "fromTime", "");
  
  var time = getTime(from);
  
  parts = from.split(' ');
  if (parts) {
    dt = parts[0].split('-');
    dtVal = dt[2]+ '/' + dt[1] + '/' + dt[0];    
  }
  
  elem1 = document.getElementById("timeInput");
  elem2 = document.getElementById("timeText");
  elem3 = document.getElementById("dateInput");
  elem4 = document.getElementById("dateText");
  if (isAppointmentFullyEditable) {
    elem1.style.display = '';
    elem2.style.display = 'none';
    document.calendarForm.time.value = time;
    
    elem3.style.display = '';
    elem4.style.display = 'none';   
    document.calendarForm.date.value = dtVal;    
  } else {
    elem1.style.display = 'none';
    elem2.style.display = '';
    elem2.innerHTML = fromTime;
    
    elem3.style.display = 'none';
    elem4.style.display = '';
    elem4.innerHTML = fromDate;
  }
}

var duration;
var durations = new Array();
durations[0] = 30;
durations[1] = 60;
durations[2] = 120;
durations[3] = 180;
durations[4] = 240;
function setDurationVal(appointment) {
  var from = getSingleElement(appointment, "from", "");
  var to = getSingleElement(appointment, "to", "");
  
  var fromTimeComponents = from.split(' ')[1].split(':');
  var toTimeComponents = to.split(' ')[1].split(':');
  
  var hourDiff = eval(toTimeComponents[0] - fromTimeComponents[0]);
  var minuteDiff = eval(toTimeComponents[1] - fromTimeComponents[1]);
  
  var noOfMinutes = hourDiff * 60 + minuteDiff;
  setDuration(noOfMinutes);
}

function setDuration(noOfMinutes) {
  elem1 = document.getElementById("durationText");
  elem2 = document.getElementById("durationRadios");
  if (isAppointmentFullyEditable) {
    elem1.style.display = 'none';
    elem2.style.display = '';
    for (i = 0; i < durations.length; i++) {
      if (durations[i] == noOfMinutes) {
        duration = durations[i];
        document["duration" + durations[i]].src = chkedRadio;
      } else {
        document["duration" + durations[i]].src = unChkedRadio;
      }
    }
  } else {
    elem1.style.display = '';
    elem2.style.display = 'none';
    for (i = 0; i < durations.length; i++) {
      elem = document.getElementById("durationT" + durations[i]);
      if (durations[i] == noOfMinutes) {
        elem.style.display = '';
      } else {
        elem.style.display = 'none';
      }
    }
  }  
}

function selectedPositionChanged() {	
  var val = selectBoxPosition.getSelectedId();
  var pars = "mode=getApplicantXml&selectedPosition=" + val;
  var myAjax = ajaxCall("calendar.do",'get',pars,updateApplicants, reportError);
}

function updateApplicants(request){
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }
  //First remove all options
  var opts = new Array();
  
  var applicants = xmlFile.getElementsByTagName("applicants")[0];
  var applicant= applicants.getElementsByTagName("applicant");
  if(applicant != null) {
  	for(var i = 0; i < applicant.length; i++){
  		var id = applicant[i].getAttribute("id");
  		var name = applicant[i].firstChild.nodeValue;
  		opts[i] = new SelectOption(id, name);
  	}
  }
  var m = [new SelectOption('-1', '<bean:message key='new_appointment.label.selectApplicant' />')];
  opts = m.concat(opts);
  selectBoxApplicant.reInitialize(opts, '');
  document.title = '<bean:message key='title.common' />';
}

function showNewAppointmentScreen(dayNumber) {  
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCHEDULE_INTERVIEW">
	  // Check whether new appointment layer is visible.
	  newApptDiv = document.getElementById('newAppointmentLayer');
	  if (newApptDiv) {
	    if (newApptDiv.style.display == '') {
	      return false;
	    }
	  }
	  
	  // Check whether set appointment layer is visible.  
	  setApptDiv = document.getElementById('setAppointmentLayer');
	  if (setApptDiv) {
	    if (setApptDiv.style.display == '') {
	      return false;
	    }
	  }
	  
	  linkOperations(dayNumber, 'hide');  
	  // Clear the hidden variables.
	  document.calendarForm.appointmentId.value = '';
	  document.calendarForm.selectedApplicant.value = '';
	  document.calendarForm.applicantPositionId.value = '';
	  document.calendarForm.applicantStepId.value = '';
	  
	  // Show default values as selected.  
	  selectBoxPosition.setSelected(selectBoxPosition.getIndexWithId('-1'));  
	  // Set the curresnt appointment date.
	  document.calendarForm.appointmentDate.value = cal.getDayOfSelectedWeek(dayNumber);
	  
	  // Show new appointment layer.
	  show('newAppointmentLayer');
	  drop();
	  alterWindowTitle();  
  </logic:equal>
}

function showPrintableViewScreen(dayNumber) {
	
	obj=document.getElementById('show_myAppointments');
	
	var showAppointment=getCheckboxState(obj);
	
	
	
  linkOperations(dayNumber, 'hide');    
  // Set the curresnt appointment date.
  from = dtf2.getFormattedDate(cal.getDayOfSelectedWeek(dayNumber));
  //alert(dtf2.getFormattedDate(from))
  to = dtf2.getFormattedDate(cal.getDayOfSelectedWeek(eval(parseInt(dayNumber) + 1)));
  win = window.open('calendar.do?mode=printableView&fromTime='+from+'&toTime='+to+'&showMyAppointments='+showAppointment,'TalentPool', 'status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=1,scrollbars=1,height=500px,width=800px,left=100px,top=100px');
  //window.open('calendar.do?mode=printableView','', 'fullscreen=1');
  return false;
}

function showNewReminderScreen(dayNumber) {
  linkOperations(dayNumber, 'hide');    
  // Set the curresnt appointment date.
  from = dtf2.getFormattedDate(cal.getDayOfSelectedWeek(dayNumber));
  var url = 'selectionProcess.do?mode=addReminder&reminderDate='+from;
  window.setTimeout("showInPopUp('"+url+"',550, 490,refreshTheAppointmentsData,true);", 10);
}

function alterWindowTitle() {
  // To keep global window title
  document.title = '<bean:message key='title.common' />';
}
var remindMe;
var remindInterviewers;
var remindCandidate;
var smsRemindMe;
var smsRemindInterviewers;
var smsRemindCandidate;
var interviewers;
var remindMeTemplate;
var remindInterviewerTemplate;
var remindApplicantTemplate;
var smsRemindMeTemplate;
var smsRemindInterviewerTemplate;
var smsRemindApplicantTemplate;
var isSendAppointment;

function setAppointment(layerName) {
  // Appointment Id
  var appointmentId = document.calendarForm.appointmentId.value;
  
  // Status
  var status = statusBox.getSelectedId();
  var InterviewModeError=selectBoxModeStatus.getSelectedId();
  var interviewMode=document.calendarForm.interviewMode.value;
  
  if(interviewMode != null && interviewMode.length==0){
	  interviewMode = selectBoxModeStatus.getSelectedId();
  }

  if (isAppointmentFullyEditable) {
    // Applicant Id
    var applicantId = document.calendarForm.selectedApplicant.value;
    
    // Position Id
    var positionId = document.calendarForm.applicantPositionId.value;
    
    // Position Step Id
    var stepId = document.calendarForm.applicantStepId.value;
    
    // Remind Me
    remindMeTemplate = '';
    if (remindMe != 0) {
    	remindMeTemplate = selectBoxRemindMeTemplate.getSelectedId();
    }
    remindMe = remindMe * remindMeBox.getSelectedId();
  
    // Remind Interviewers
    remindInterviewerTemplate = '';
    if (remindInterviewers != 0) {
    	remindInterviewerTemplate = selectBoxRemindInterviewerTemplate.getSelectedId();
    }
    remindInterviewers = remindInterviewers * remindInterviewersBox.getSelectedId();
  
    // Remind Candidate
    remindApplicantTemplate = '';
    if (remindCandidate != 0) {
    	remindApplicantTemplate = selectBoxRemindApplicantTemplate.getSelectedId();
    }
    remindCandidate = remindCandidate * remindCandidateBox.getSelectedId();
    
    // Subject
    var subject = '';
    elem = document.getElementById("subject");
    if (elem) {
      subject = elem.innerHTML;
    } 
  
    // Date
    var dt = document.calendarForm.date.value;
  
    // Time
    var time = document.calendarForm.time.value;
    
    var appointmentFromTime = getAppointmentFromTime(dt, time);
    
    var appointmentToTime = getAppointmentToTime(appointmentFromTime, duration);
   
    // Check the required data
    var errMsg = '';
    interviewers =  dataGridInterviewerUser.getAllItemIds();
    if (interviewers.length == 0) {
      errMsg += '<bean:message key="calendar.error.select_at_least_one_interviewer"/>';
    }
    if (dt.length == 0) {
      if (errMsg.length > 0) {
        errMsg += '\n';
      }
      errMsg += '<bean:message key="calendar.error.select_date_of_appointment"/>';
    }
    if (time.length == 0) {
      if (errMsg.length > 0) {
        errMsg += '\n';
      }
      errMsg += '<bean:message key="calendar.error.select_time_of_appointment"/>';
    }
    
    /*if (dt.length != 0 && time.length != 0) {
      dt2 = dtf2.getDateObject(dt);    
      parts = appointmentFromTime.split(' ');
      timecomponents = parts[1].split(':');
      hours = timecomponents[0];
      minutes = timecomponents[1];
      dt2.setHours(hours);
      dt2.setMinutes(minutes);    
      if (dt2 < new Date()) {
        if (errMsg.length > 0) {
          errMsg += '\n';
        }
        errMsg += '<bean:message key="calendar.error.select_valid_time_of_appointment"/>';
      }
    }  */
    
    if (status == '-1') {
    	errMsg += '<bean:message key="calendar.error.select_status_message"/>';
    }
    
    if (InterviewModeError == '-1') {
    	errMsg += '<bean:message key="calendar.error.select_status_message"/>';
    }
    
    if (errMsg.length > 0) {
      alert(errMsg);
      return false;
    }
    
 	// SMS Remind Me
    smsRemindMeTemplate = '';
    if (smsRemindMeBox) {
    	if (smsRemindMe != 0) {
    		smsRemindMeTemplate = selectBoxSmsRemindMeTemplate.getSelectedId();
    	}
	    smsRemindMe = smsRemindMe * smsRemindMeBox.getSelectedId();
	}
  
    // SMS Remind Interviewers
    smsRemindInterviewerTemplate = '';
    if (smsRemindInterviewersBox) {
    	if (smsRemindInterviewers != 0) {
    		smsRemindInterviewerTemplate = selectBoxSmsRemindInterviewerTemplate.getSelectedId();
    	}
	    smsRemindInterviewers = smsRemindInterviewers * smsRemindInterviewersBox.getSelectedId();
	}
  
    // SMS Remind Candidate
    smsRemindApplicantTemplate = '';
    if (smsRemindCandidateBox) {
    	if (smsRemindCandidate != 0) {
    		smsRemindApplicantTemplate = selectBoxSmsRemindApplicantTemplate.getSelectedId();
    	}
	    smsRemindCandidate = smsRemindCandidate * smsRemindCandidateBox.getSelectedId();
	}  	
  	
    isSendAppointment = getIsSendAppointment();
    hide(layerName);
    var timeZone = document.calendarForm.timeZone.value;
    var detailsInterviewMode=document.calendarForm.detailsInterviewMode.value;
   
   
    if (appointmentId == '') {
      var pars = "mode=createNewAppointment&subject=" + encodeURIComponent(subject) + 
                          "&selectedApplicant=" + applicantId +  
                          "&applicantPositionId=" + positionId +  
                          "&applicantStepId=" + stepId + 
                          "&remindMe=" + remindMe + 
                          "&remindInterviewers=" + remindInterviewers + 
                          "&remindCandidate=" + remindCandidate + 
                          "&smsRemindMe=" + smsRemindMe + 
                          "&smsRemindInterviewers=" + smsRemindInterviewers + 
                          "&smsRemindCandidate=" + smsRemindCandidate + 
                          "&remindMeTemplate=" + remindMeTemplate + 
                          "&remindInterviewersTemplate=" + remindInterviewerTemplate + 
                          "&remindCandidateTemplate=" + remindApplicantTemplate + 
                          "&smsRemindMeTemplate=" + smsRemindMeTemplate + 
                          "&smsRemindInterviewersTemplate=" + smsRemindInterviewerTemplate + 
                          "&smsRemindCandidateTemplate=" + smsRemindApplicantTemplate + 
                          "&appointmentFromDate=" + appointmentFromTime +
                          "&appointmentToDate=" + appointmentToTime + 
                          "&interviewers=" + interviewers +
                          "&status=" + statusBox.getText(statusBox.getSelectedIndex()) +
                          "&isSendAppointmentNotification=" + isSendAppointment +
						  "&detailsInterviewMode=" + detailsInterviewMode +
						  "&timeZone=" + timeZone +
						  "&interviewMode=" + interviewMode;
      	var myAjax = ajaxCall("calendar.do",'get',pars,checkErrors, reportError);
    } else {
      var pars = "mode=editAppointment&appointmentId=" + appointmentId + 
					      "&selectedApplicant=" + applicantId +  
                          "&remindMe=" + remindMe + 
                          "&remindInterviewers=" + remindInterviewers +  
                          "&remindCandidate=" + remindCandidate + 
                          "&smsRemindMe=" + smsRemindMe + 
                          "&smsRemindInterviewers=" + smsRemindInterviewers + 
                          "&smsRemindCandidate=" + smsRemindCandidate + 
                          "&remindMeTemplate=" + remindMeTemplate + 
                          "&remindInterviewersTemplate=" + remindInterviewerTemplate + 
                          "&remindCandidateTemplate=" + remindApplicantTemplate + 
                          "&smsRemindMeTemplate=" + smsRemindMeTemplate + 
                          "&smsRemindInterviewersTemplate=" + smsRemindInterviewerTemplate + 
                          "&smsRemindCandidateTemplate=" + smsRemindApplicantTemplate + 
                          "&appointmentFromDate=" + appointmentFromTime +
                          "&appointmentToDate=" + appointmentToTime + 
                          "&interviewers=" + interviewers +
                          "&status=" + statusBox.getText(statusBox.getSelectedIndex())  +
                          "&isSendAppointmentNotification=" + isSendAppointment+
						  "&detailsInterviewMode=" + detailsInterviewMode +
						  "&timeZone=" + timeZone +
						  "&interviewMode=" + interviewMode;
      var myAjax = ajaxCall("calendar.do",'get',pars,refreshTheAppointmentsData, reportError);
      var myAjax = ajaxCall("calendar.do",'get',pars,checkErrors, reportError);
	  
    }  
  } else {
    hide(layerName);
    var applicantId = document.calendarForm.selectedApplicant.value;
    var pars = "mode=updateAppointmentStatus" +
						 "&selectedApplicant=" + applicantId +  
                        "&status=" + statusBox.getText(statusBox.getSelectedIndex());
	var myAjax = ajaxCall("calendar.do",'get',pars,refreshTheAppointmentsData, reportError);
  }
}

function getIsSendAppointment() {
	var val = <%=CalendarConstants.SEND_EMAIL_NO%>;
	elem = document.getElementById("sendAppointment");
	if (elem) {
		if (elem.src.indexOf(chkedCheckBox) != -1) {
			val = <%=CalendarConstants.SEND_EMAIL_YES%>;
		}
	}	
	return val;
}

var dtf2 = new DateFormatter();
dtf2.setDisplayFormat('YYYY-MM-DD');

function getAppointmentFromTime(dt, time) {
  parts = time.split(' ');
  timecomponents = parts[0].split(':');
  hours = timecomponents[0];
  minutes = timecomponents[1];
  if (hours.substring(0, 1) == 0)  {
    hours = hours.substring(1, 2);
  }
  if (parts[1] == 'PM') {
    if (hours != 12) {
      hours = eval(parseInt(hours) + 12);
    }    
  } else {
    if (hours == 12) {
      hours = '00';
    }
  }
  return dtf2.getFormattedDate(dt) + ' ' + hours + ':' + minutes;
}

function getAppointmentToTime(dt, durationVal) {
  parts = dt.split(' ');
  timecomponents = parts[1].split(':');
  hours = timecomponents[0];
  minutes = timecomponents[1];
  if (hours.substring(0, 1) == 0)  {
    hours = hours.substring(1, 2);
  }
  if (minutes.substring(0, 1) == 0)  {
    minutes = minutes.substring(1, 2);
  }
  minutes = eval(parseInt(minutes) + (durationVal % 60));
  val = (durationVal - (durationVal % 60)) / 60;
  
  hours = eval(parseInt(hours) + val);
  
  if (minutes % 60 >= 0) {
    hours = eval(parseInt(hours) + ((minutes - (minutes % 60)) / 60));
    minutes = minutes % 60;
  }   
  if (minutes == 0) {	
  	minutes = '00';
  }
  if (hours >= 24) {
    hours = hours - 24;
  }
  return parts[0] + ' ' + hours + ':' + minutes;
}

function checkErrors(request){
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }
  var errors = xmlFile.getElementsByTagName("errors")[0];
  error = getSingleElement(errors, "error", "");
  if (error == 'calendar.error.future_appointment_exists') {
      retVal = confirm('<bean:message key="calendar.error.future_appointment_exists" />');
      if (retVal == true) {        
        // Status
        var status = statusBox.getSelectedId();
        
        // Applicant Id
        var applicantId = document.calendarForm.selectedApplicant.value;
        
        // Position Id
        var positionId = document.calendarForm.applicantPositionId.value;
        
        // Position Step Id
        var stepId = document.calendarForm.applicantStepId.value;
      
        // Subject
        var subject = '';
        elem = document.getElementById("subject");
        if (elem) {
          subject = elem.innerHTML;
        } 
      
        // Date
        var dt = document.calendarForm.date.value;
      
        // Time
        var time = document.calendarForm.time.value;
        
        var appointmentFromTime = getAppointmentFromTime(dt, time);
        var timeZone = document.calendarForm.timeZone.value;
        var detailsInterviewMode=document.calendarForm.detailsInterviewMode.value;
        var interviewMode=document.calendarForm.interviewMode.value;
        var appointmentToTime = getAppointmentToTime(appointmentFromTime, duration);
        var pars = "mode=saveNewAppointment&subject=" + encodeURIComponent(subject) + 
                            "&selectedApplicant=" + applicantId +  
                            "&applicantPositionId=" + positionId +  
                            "&applicantStepId=" + stepId + 
                            "&remindMe=" + remindMe + 
                            "&remindInterviewers=" + remindInterviewers + 
                            "&remindCandidate=" + remindCandidate + 
                            
							"&smsRemindMe=" + smsRemindMe + 
							"&smsRemindInterviewers=" + smsRemindInterviewers + 
							"&smsRemindCandidate=" + smsRemindCandidate + 
							"&remindMeTemplate=" + remindMeTemplate + 
							"&remindInterviewersTemplate=" + remindInterviewerTemplate + 
							"&remindCandidateTemplate=" + remindApplicantTemplate + 
							"&smsRemindMeTemplate=" + smsRemindMeTemplate + 
							"&smsRemindInterviewersTemplate=" + smsRemindInterviewerTemplate + 
							"&smsRemindCandidateTemplate=" + smsRemindApplicantTemplate + 
							
                            "&appointmentFromDate=" + appointmentFromTime +
                            "&appointmentToDate=" + appointmentToTime + 
                            "&interviewers=" + interviewers +
                            "&status=" + statusBox.getText(statusBox.getSelectedIndex()) +
							"&isSendAppointmentNotification=" + isSendAppointment +
							"&detailsInterviewMode=" + detailsInterviewMode +
							  "&timeZone=" + timeZone +
							  "&interviewMode=" + interviewMode;    

	     var myAjax = ajaxCall("calendar.do",'get',pars,refreshTheAppointmentsData, reportError);
	    
      } else {
        refreshTheAppointmentsData(null);        
      }
  } else if (error == 'calendar.error.past_appointment_exists') {
  	alert('<bean:message key="calendar.error.past_appointment_exists" />');
  } else if (error == 'calendar.error.can_not_set_appointments_in_past') {
  	alert('<bean:message key="calendar.error.can_not_set_appointments_in_past" />');
  	show('setAppointmentLayer');
  	drop();
  } else {
    refreshTheAppointmentsData(null);
  } 
}
function refreshTheAppointmentsData(request){
  if (request != null) {
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
      return;
    }
  }
  fetchAppointmentsForCurrentWeek(null);
}

function toggleRemindBoxes(sourceImg, elemName) {
  reminderValue = -1;
  elem = null;
  elem2 = null;
  if (elemName == 'isRemindMe') {
    elem = remindMeBox;
    elem2 = selectBoxRemindMeTemplate;
  } else if (elemName == 'isRemindInterviewers') {
    elem = remindInterviewersBox;
    elem2 = selectBoxRemindInterviewerTemplate;
  } else if (elemName == 'isRemindCandidate') {
    elem = remindCandidateBox;
    elem2 = selectBoxRemindApplicantTemplate;
  } else if (elemName == 'isSMSRemindMe') {
    elem = smsRemindMeBox;
    elem2 = selectBoxSmsRemindMeTemplate;
  } else if (elemName == 'isSMSRemindInterviewers') {
    elem = smsRemindInterviewersBox;
    elem2 = selectBoxSmsRemindInterviewerTemplate;
  } else if (elemName == 'isSMSRemindCandidate') {
    elem = smsRemindCandidateBox;
    elem2 = selectBoxSmsRemindApplicantTemplate;
  }  
  if (elem) {
    if (sourceImg.indexOf(unChkedCheckBox) != -1) {
      document[elemName].src = chkedCheckBox;
      elem.enable();
      reminderValue = 1;
      elem2.enable();
    } else {
      document[elemName].src = unChkedCheckBox;
      elem.disable();
      reminderValue = 0;
      elem2.disable();
    }
  }
  if (elemName == 'isRemindMe') {
    remindMe = reminderValue;
  } else if (elemName == 'isRemindInterviewers') {
    remindInterviewers = reminderValue;
  } else if (elemName == 'isRemindCandidate') {
    remindCandidate = reminderValue;
  } else if (elemName == 'isSMSRemindMe') {
    smsRemindMe = reminderValue;
  } else if (elemName == 'isSMSRemindInterviewers') {
    smsRemindInterviewers = reminderValue;
  } else if (elemName == 'isSMSRemindCandidate') {
    smsRemindCandidate = reminderValue;
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
      //alert("Please enter time in hh:mm format.");
      alert('<bean:message key="calendar.alert.enter_time_format"/>');      
      obj.focus();
    }    
  }
  return true;  
}

function refreshBackGround(obj) {
  if (obj.value.trim() != '') {
    val = dtf.getFormattedDate(obj.value.trim());
    if(!val){
      return;
    }    
    
    parts = val.split('/');
    if (parts) {      
      if (parts[0].substring(0, 1) == '0') {
        parts[0] = parts[0].substring(1, 2)
      }
      if (parts[1].substring(0, 1) == '0') {
        parts[1] = parts[1].substring(1, 2)
      }
      var dtStr = parts[0] + '-' + parts[1] + '-' + parts[2];
      var indx = cal.calendarString.split(',').indexOf(dtStr);
      if (indx >= 0) {
        indx = (indx - indx % 7) / 7
        appointmentDateChanged(indx);
      }      
    }
    return false;
  }
}

function cancelSetAppointmentOperation() {
  hide('setAppointmentLayer');
  hide('timePopUpDiv');
  popUpCal.hidePopup();
}
function showEditAppointmentScreen(event,appointmentId, dayNumber, isFullyEditable) {
	highlightSelectedAppointment(event);
  	isAppointmentFullyEditable = isFullyEditable;
  	document.calendarForm.appointmentId.value = appointmentId;
  	var pars = "mode=getEditAppointmentXml&appointmentId=" + appointmentId;
	var myAjax = ajaxCall("calendar.do",'get',pars,UpdateEditAppointmentScreen, reportError);
  	selectDay(dayNumber);
}

function setFocus(e) {
  if (!e) e = window.event;
  keyCode = e.keyCode;
  dir = '';
  returnValue = true;
  if (keyCode == Event.KEY_DOWN) {
    dir = 'up';
  } else if (keyCode == Event.KEY_UP) {
    dir = 'down';
  }
  if (dir != '') {
    keyDownEvent(dir, cal, document.getElementById('tbl'));
    prevTblId="";
    returnValue = false;
  } else if (keyCode == Event.KEY_ESC) {
    if (timePopUp.visible()) {
      timePopUp.hidePopup();
    } else if (document.getElementById('calDiv').style.visibility == 'visible') {
      //do Nothing.
    } else if (document.getElementById('newAppointmentLayer').style.display == '') {
      hide('newAppointmentLayer');
    } else if (document.getElementById('setAppointmentLayer').style.display == '') {
      cancelSetAppointmentOperation();
    }
  }
  
  clearSelection();
  return returnValue;
}

function stopEventPropagation(event) {
	if (!event) event = window.event;
	event.cancelBubble=true;
}

function initCal() {
  clearSelection();
  isPresent = false;
  var dt = '1-' + selectBoxMonth.getSelectedId() + '-' + selectBoxYear.getSelectedId();
  indx = cal.calendarString.split(',').indexOf(dt);
  if (indx != -1) {
    cal.selectedWeek = (indx - (indx % 7)) / 7;
  } else {
    year = selectBoxYear.getSelectedId();    
    for (i = 0; i < window.calendars.length; i++) {
      temp = window.calendars[i];
      indx = temp.calendarString.split(',').indexOf(dt);
      if (indx != -1) {
        cal = temp;        
        cal.selectedWeek = (indx - (indx % 7)) / 7;
        isPresent = true;
        break;
      }
    }    
    if (!isPresent) {
      cal = new Calendar("tpcal" + year, parseInt(year), parseInt(selectBoxMonth.getSelectedId() -1)); 
      cal.init();
    }
  }
  document.getElementById('tbl').focus();
  scroll(cal, null, null);
  updateRightPanel(cal);
}

function mouseClick(obj, dest) {
    clearSelection();
    mouseClickEvent(obj, dest);
}

function selectedApplicantChanged() {
  document.calendarForm.selectedApplicant.value=selectBoxApplicant.getSelectedId();  
}
</script>
<div class="contentDiv">
<table>
<tr><td>
<logic:present scope="request" parameter="popup">
<div class="navBtn" style="float: left; "><a href="#" style="width:200px;" class="active" onclick="javascript: window.location='selectionProcess.do?mode=viewOriginalResume&applicantId=<bean:write property="selectedApplicant" name="calendarForm"/>';return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="calendar.label.back_to_applicant"/></a></div>
</logic:present>                
</td></tr>
</table>
	<html:form action="calendar">
	  <html:hidden property="mode" name="calendarForm"/>
	  <html:hidden property="t" name="calendarForm"/>
	  <html:hidden property="st" name="calendarForm"/>
	  <html:hidden property="currentWeekDays" name="calendarForm"/>
	  <html:hidden property="selectedApplicant" name="calendarForm"/>
	  <html:hidden property="appointmentDate" name="calendarForm"/>
	  <html:hidden property="applicantPositionId" name="calendarForm"/>
	  <html:hidden property="applicantStepId" name="calendarForm"/>
	  <html:hidden property="appointmentId" name="calendarForm"/>
	  <html:hidden property="isAppointmentFullyEditable" name="calendarForm"/>
	  <html:hidden property="timeZone" name="calendarForm"/>
	  <html:hidden property="interviewMode" name="calendarForm"/>
	    
	    
	  <table cellpadding="0" cellspacing="0" border="0">
	  	<tr>
	  		<td colspan="3" height="18">&nbsp;</td>
	  	<tr>
	  	<tr>
	  		<td valign="top">
	  			<table cellpadding="0" cellspacing="0" border="0">
	  				<tr>
	  					<td height="19">&nbsp;</td>
	  				</tr>
	  				<tr>
	  					<td valign="top" width="194px">
					      <table cellspacing="0" cellpadding="0" border="0">
					        <tr>
					          <td align="left" width="97px">
					            <script type="text/javascript">
												var opts = new Array();				
												opts[0] = new SelectOption('1','January');
												opts[1] = new SelectOption('2','February');
												opts[2] = new SelectOption('3','March');
												opts[3] = new SelectOption('4','April');
												opts[4] = new SelectOption('5','May');
												opts[5] = new SelectOption('6','June');
												opts[6] = new SelectOption('7','July');
												opts[7] = new SelectOption('8','August');
												opts[8] = new SelectOption('9','September');
												opts[9] = new SelectOption('10','October');
												opts[10] = new SelectOption('11','November');
												opts[11] = new SelectOption('12','December');	
												selectBoxMonth = new SelectBox(opts,'<%=GregorianCalendar.getInstance().get(Calendar.MONTH)+1%>','images/btn_dropdown_green.gif',{namesonly:false, width:'80px', size:12});
												selectBoxMonth.setOnChangeHandler('initCal');
												document.write(selectBoxMonth.getHtml());
												selectBoxMonth.init();
											</script>
					          </td>
					          <td align="right" width="97px">
					            <script type="text/javascript">
												var opts = new Array();	
												var year = new Date().getFullYear();			
												opts[0] = new SelectOption(parseInt(year - 2),parseInt(year - 2));
												opts[1] = new SelectOption(parseInt(year - 1),parseInt(year - 1));
												opts[2] = new SelectOption(parseInt(year),parseInt(year));
												opts[3] = new SelectOption(parseInt(year + 1),parseInt(year + 1));
												opts[4] = new SelectOption(parseInt(year + 2),parseInt(year + 2));	
												selectBoxYear = new SelectBox(opts,parseInt(year),'images/btn_dropdown_green.gif',{namesonly:false, width:'80px', size:5});
												selectBoxYear.setOnChangeHandler('initCal');
												document.write(selectBoxYear.getHtml());
												selectBoxYear.init();
											</script>
					          </td>
					        </tr>
					      </table>
	  					</td>
	  				</tr>
	  				<tr>
				  		<td height="22">&nbsp;</td>
				  	<tr>
	  				<tr>
	  					<td valign="top">
					      <table id="tbl" class="tpcal">
					        <tr>
					          <td class="weekDay"><bean:message key="calendar.label.mo"/></td>
					          <td class="weekDay"><bean:message key="calendar.label.tu"/></td>
					          <td class="weekDay"><bean:message key="calendar.label.we"/></td>
					          <td class="weekDay"><bean:message key="calendar.label.th"/></td>
					          <td class="weekDay"><bean:message key="calendar.label.fr"/></td>
					          <td class="weekDay"><bean:message key="calendar.label.sa"/></td>
					          <td class="weekDay"><bean:message key="calendar.label.su"/></td>
					          <td class="ScrollBar weekDay" style="border-left:none;"></td>
					        </tr>
					        <tr>
						  			<td style="height:2px;background-color: #DBDBDB;" colspan="8"></td>
						  		</tr>
					        <tr id="-2">
					          <td id="-20" onClick="javascript: mouseClick(cal, 0);"></td>
					          <td id="-21" onClick="javascript: mouseClick(cal, 0);"></td>
					          <td id="-22" onClick="javascript: mouseClick(cal, 0);"></td>
					      	  <td id="-23" onClick="javascript: mouseClick(cal, 0);"></td>
					      	  <td id="-24" onClick="javascript: mouseClick(cal, 0);"></td>
					      	  <td id="-25" onClick="javascript: mouseClick(cal, 0);"></td>
							      <td id="-26" onClick="javascript: mouseClick(cal, 0);"></td>
							      <td id="-27" class="ScrollBar"><img src="images/btn_up_calendar.gif" onClick="javascript: scroll(cal, null, 'down');"/></td>
					        </tr>
					        <tr id="-1">
							      <td id="-10" onClick="javascript: mouseClick(cal, 1);"></td>
							      <td id="-11" onClick="javascript: mouseClick(cal, 1);"></td>
							      <td id="-12" onClick="javascript: mouseClick(cal, 1);"></td>
							      <td id="-13" onClick="javascript: mouseClick(cal, 1);"></td>
						    	  <td id="-14" onClick="javascript: mouseClick(cal, 1);"></td>
							      <td id="-15" onClick="javascript: mouseClick(cal, 1);"></td>
							      <td id="-16" onClick="javascript: mouseClick(cal, 1);"></td>
						    	  <td id="-17" class="ScrollBar">&nbsp;</td>
					        </tr>
					        <tr id="0">
							      <td id="00" class="TPCell" onClick="javascript: mouseClick(cal, 2);"></td>
							      <td id="01" class="TPCell" onClick="javascript: mouseClick(cal, 2);"></td>
							      <td id="02" class="TPCell" onClick="javascript: mouseClick(cal, 2);"></td>
							      <td id="03" class="TPCell" onClick="javascript: mouseClick(cal, 2);"></td>
							      <td id="04" class="TPCell" onClick="javascript: mouseClick(cal, 2);"></td>
							      <td id="05" class="TPCell" onClick="javascript: mouseClick(cal, 2);"></td>
							      <td id="06" class="TPCell" onClick="javascript: mouseClick(cal, 2);"></td>
							      <td id="07" class="ScrollBar">&nbsp;</td>
					        </tr>
					        <tr id="1">
						    	  <td id="10" onClick="javascript: mouseClick(cal, 3);"></td>
							      <td id="11" onClick="javascript: mouseClick(cal, 3);"></td>
							      <td id="12" onClick="javascript: mouseClick(cal, 3);"></td>
							      <td id="13" onClick="javascript: mouseClick(cal, 3);"></td>
							      <td id="14" onClick="javascript: mouseClick(cal, 3);"></td>
							      <td id="15" onClick="javascript: mouseClick(cal, 3);"></td>
							      <td id="16" onClick="javascript: mouseClick(cal, 3);"></td>
							      <td id="17" class="ScrollBar">&nbsp;</td>
					        </tr>
					        <tr id="2">
						    	  <td id="20" onClick="javascript: mouseClick(cal, 4);"></td>
							      <td id="21" onClick="javascript: mouseClick(cal, 4);"></td>
							      <td id="22" onClick="javascript: mouseClick(cal, 4);"></td>
							      <td id="23" onClick="javascript: mouseClick(cal, 4);"></td>
							      <td id="24" onClick="javascript: mouseClick(cal, 4);"></td>
							      <td id="25" onClick="javascript: mouseClick(cal, 4);"></td>
							      <td id="26" onClick="javascript: mouseClick(cal, 4);"></td>
							      <td id="27" class="ScrollBar">&nbsp;</td>
					        </tr>
					        <tr id="3">
							      <td id="30" onClick="javascript: mouseClick(cal, 5);"></td>
							      <td id="31" onClick="javascript: mouseClick(cal, 5);"></td>
							      <td id="32" onClick="javascript: mouseClick(cal, 5);"></td>
							      <td id="33" onClick="javascript: mouseClick(cal, 5);"></td>
							      <td id="34" onClick="javascript: mouseClick(cal, 5);"></td>
							      <td id="35" onClick="javascript: mouseClick(cal, 5);"></td>
							      <td id="36" onClick="javascript: mouseClick(cal, 5);"></td>
							      <td id="37" class="ScrollBar">&nbsp;</td>
					        </tr>
					        <tr id="4">
					          <td id="40" onClick="javascript: mouseClick(cal, 6);"></td>
					      	  <td id="41" onClick="javascript: mouseClick(cal, 6);"></td>
					  	      <td id="42" onClick="javascript: mouseClick(cal, 6);"></td>
							      <td id="43" onClick="javascript: mouseClick(cal, 6);"></td>
							      <td id="44" onClick="javascript: mouseClick(cal, 6);"></td>
							      <td id="45" onClick="javascript: mouseClick(cal, 6);"></td>
							      <td id="46" onClick="javascript: mouseClick(cal, 6);"></td>
							      <td id="47" class="ScrollBar">&nbsp;</td>
					        </tr>
					        <tr id="5">
							      <td id="50" onClick="javascript: mouseClick(cal, 7);"></td>
							      <td id="51" onClick="javascript: mouseClick(cal, 7);"></td>
							      <td id="52" onClick="javascript: mouseClick(cal, 7);"></td>
							      <td id="53" onClick="javascript: mouseClick(cal, 7);"></td>
							      <td id="54" onClick="javascript: mouseClick(cal, 7);"></td>
							      <td id="55" onClick="javascript: mouseClick(cal, 7);"></td>
							      <td id="56" onClick="javascript: mouseClick(cal, 7);"></td>
					  	      <td id="57" class="ScrollBar"><img src="images/btn_down_calendar.gif" onClick="javascript: scroll(cal, null, 'up');"/></td>
					        </tr>
					      </table>
	  					</td>
	  				</tr>
	  			</table>	
	  		</td>
	  		<td valign="top" width="23">
	  			&nbsp;
	  		</td>
	  		<td valign="top">
	  			<table cellpadding="0" cellspacing="0" border="0">
	  				<tr>
	  					<td colspan="7">
	  					<table cellpadding="0" cellspacing="0" border="0" width="100%"><tr>
	  					<td align="left" width="20%"><a href="#" class="green" onclick="javascript: showInterviewListFilter();"><bean:message key="calendar.label.interview_list" /></a></td>	  						  					
	  					<td align="right">	  					
	  						<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		  						<tr>
		  							<td class="normal">
		  								<img src="images/checkboxchecked.gif" id="show_myAppointments" name="show_myAppointments" onclick="changeMyAppointments(this);" />
		  								<bean:message key="calendar.label.myAppointments"/>
		  							</td>
		  							<td class="normal">
		  								<% String show_reminder = (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_REMINDER); %>
									  	<% if("1".equals(show_reminder)){ %>
					  						<img src="images/checkboxchecked.gif" id="show_reminder" name="show_reminder" onclick="showReminders(this);" />
									  	<%}else{ %>
					  						<img src="images/checkboxunchecked.gif" id="show_reminder" name="show_reminder" onclick="showReminders(this);" />
									  	<%} %>
									  	<bean:message key="admin_application_settings.label.show_reminders_with_calendar"/>
		  							</td>
		  							<td class="leftC"></td>
		  							<td id="monthYear" class="content" style="padding-left:20px; padding-right:20px;">sdsds</td>
		  							<td class="rightC"></td>
		  						</tr>
	  						</table>
						</td>
						</tr>
						</table>
						  	
			  			</td>
			  		</tr>
	  				<tr>
	  					<td align="left" valign="top">         					      
					      <table class="tpweek" id="tblWeeklyView">
					        <tr>
					          <td id="day0000" class="day"><bean:message key="calendar.label.monday"/></td>
					          <td id="day0001" class="day"><bean:message key="calendar.label.tuesday"/></td>
					          <td id="day0002" class="day"><bean:message key="calendar.label.wednesday"/></td>
					          <td id="day0003" class="day"><bean:message key="calendar.label.thursday"/></td>
					          <td id="day0004" class="day"><bean:message key="calendar.label.friday"/></td>
					          <td id="day0005" class="day"><bean:message key="calendar.label.saturday"/></td>
					          <td id="day0006" class="day"><bean:message key="calendar.label.sunday"/></td>
					        </tr>  
					        <tr>
						  			<td style="height:1px;background-color: #99CC01;" colspan="7"></td>
						  		</tr>    
					        <tr>
					          <td id="day0" class="dt"></td>
					          <td id="day1" class="dt"></td>
					          <td id="day2" class="dt"></td>
					          <td id="day3" class="dt"></td>
					          <td id="day4" class="dt"></td>
					          <td id="day5" class="dt"></td>
					          <td id="day6" class="dt"></td>
					        </tr>
					        <tr>
					          <%
					            for (int i = 0; i < 7; i++) {
					          %>
					          <td id='<%="day00"+i%>' class="appointments" valign="top" onclick="javascript: linkOperations('<%=i%>', 'show'); " ondblclick="javascript: showNewAppointmentScreen('<%=i%>');" > 
					            <table id='<%="day0"+i%>' >
					              <tr><td id='<%="sp0"+i%>' class="newAppointment">&nbsp;</td></tr>
					              <tr><td id='<%="sp2"+i%>' class="newAppointment">&nbsp;</td></tr>
					              <tr><td id='<%="sp1"+i%>' class="newAppointment">&nbsp;</td></tr>
					              <tr>
					                <td id='<%="appt"+i%>' class="newAppointment" style="display:none;">
										<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCHEDULE_INTERVIEW">
					                    <a href="#" class="metallink" onclick="javascript: showNewAppointmentScreen('<%=i%>'); stopEventPropagation(event);"><bean:message key="calendar.text.newAppointmentLink"/></a>                                   
					                 	</logic:equal>
					                </td>
					              </tr>   
					              <tr>
					                <td id='<%="reminder"+i%>' class="newAppointment" style="display:none;">
					                    <a href="#" class="metallink" onclick="javascript: showNewReminderScreen('<%=i%>'); stopEventPropagation(event);"><bean:message key="calendar.text.newReminderLink"/></a>                                   
					                </td>
					              </tr>                            
					              <tr>
					                <td id='<%="prnView"+i%>' class="newAppointment" style="display:none;"><a href="#" class="metallink" onclick="javascript: showPrintableViewScreen('<%=i%>'); stopEventPropagation(event);"><bean:message key="calendar.text.printableViewLink"/></a></td>
					              </tr>
					            </table>
					          </td>
					          <%  
					            }
					          %>
					        </tr>        
					      </table>
	  					</td>
	  				</tr>
	  				<tr>
			  			<td style="height:1px;background-color: #999999;" colspan="7"></td>
			  		</tr>
	  			</table>	
	  		</td>
	  	</tr>	  	 
	  </table>
</div>
<div id="newAppointmentLayer" style="background:#ffffff;width:374px;position:absolute;left:400px;top:250px;padding:0px;display:none;overflow:visible;">  
  <div style="overflow:visible;">     
    <div class="outerDiv" style="border-width:5px;padding:0px;height:13px;overflow:visible;cursor:move;border-bottom:none;" onmousedown="grab(document.getElementById('newAppointmentLayer'));"></div>
      <div style="padding:0px;overflow:visible;border-width:5px;background:#D0E4A3;border-top:none;" class="outerDiv">  
        <table>
          <tr>
            <td width="100%">
              <table>
                <tr>
                  <td class="label"><bean:message key="common.position"/>:</td>
                  <td>&nbsp;</td>
                  <td>
						        <script type="text/javascript">
								    var opts = <bean:write name="calendarForm" property="jsArrayPositions" filter="false"/>;
               						var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
									opts = m.concat(opts);											
									selectBoxPosition = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:12});
			        				selectBoxPosition.setOnChangeHandler('selectedPositionChanged');
			        				document.write(selectBoxPosition.getHtml());
			        				selectBoxPosition.init();
										</script>
                  </td>
                </tr>
                <tr>
                  <td class="label"><bean:message key="new_appointment.label.candidate"/></td>
                  <td>&nbsp;</td>
                  <td>
                    <script type="text/javascript">
									    var opts = new Array();	
											opts[0] = new SelectOption('-1','<bean:message key="new_appointment.label.selectApplicant"/>');
											selectBoxApplicant = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:12});
			        				selectBoxApplicant.setOnChangeHandler('selectedApplicantChanged');
			        				document.write(selectBoxApplicant.getHtml());
			        				selectBoxApplicant.init();
										</script>
                  </td>
                </tr>
                <tr>
                  <td colspan="3">
                  	<div class="navBtn" style="float: right;margin-left:50px;">
								    	<a href="#" style="width:70px;" class="active" onclick="javascript: continueToSetAppointment('newAppointmentLayer', 'setAppointmentLayer'); return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.continue"/></a>
											<a href="#" style="width:55px; margin-left:5px;" class="active" onclick="window.hide('newAppointmentLayer');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
										</div>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table> 
      </div>      
    </div> 
</div>
<script language="JavaScript">
var defaultRemindMeTemplate = '<bean:write name="defaultRemindMeTemplate" scope="request" />';
var defaultRemindInterviewerTemplate = '<bean:write name="defaultRemindInterviewerTemplate" scope="request" />';
var defaultRemindApplicantTemplate = '<bean:write name="defaultRemindApplicantTemplate" scope="request" />';
<% if (userSMSEnabled) { %>
var defaultSmsRemindMeTemplate = '<bean:write name="defaultSmsRemindMeTemplate" scope="request" />';
var defaultSmsRemindInterviewerTemplate = '<bean:write name="defaultSmsRemindInterviewerTemplate" scope="request" />';
var defaultSmsRemindApplicantTemplate = '<bean:write name="defaultSmsRemindApplicantTemplate" scope="request" />';
<% } %>
</script>
<div id="setAppointmentLayer" style="background:#ffffff;position:absolute;width:800px;left:300px;top:235px;display:none;">

  <div style="overflow:visible;"> 
    <div class="outerDiv" style="border-width:5px;padding:0px;height:15px;overflow:visible;cursor:move;border-bottom:none;" onmousedown="grab(document.getElementById('setAppointmentLayer'));"></div>
      <div style="padding:0px;overflow:visible;border-width:5px;background:#D0E4A3;border-top:none;" class="outerDiv">  
        <table >
          <tr>
            <td>
              <table>
                <tr>
                  <td valign="top"><bean:message key="new_appointment.label.candidate"/></td>
                  <td class="vGap"></td>
                  <td nowrap="nowrap"><span id="candidate"></span>&nbsp;&nbsp;&nbsp;<bean:message key='common.openingSquareBracket' /><span id="subject"></span><bean:message key='common.closingSquareBracket' /></td>
                </tr>
                <tr id="intRow1">
                  <td valign="top"><bean:message key="new_appointment.label.interviewer"/></td>
                  <td class="vGap"></td>
                  <td id="interviewer" valign="top" style="height:15px;"></td>
                </tr>
                
                 <tr id="intRow2">
                  <td valign="top"><bean:message key="new_appointment.label.interviewer"/></td>
                  <td class="vGap"></td>
                  
                <td colspan="3">
                <table><tr><td>
                <table cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<input id="interviewernew" name="interviewernew" type="text" size="41" onfocus="setUserSelection('interviewernew')" value="Filter" style="width:216px; color: grey;border-bottom: 0px;" onclick="onFilterFocus('interviewernew','Filter');" onblur="onFilterUnfocus('interviewernew','Filter')"/>
						</td>
					</tr>
					<tr>
						<td class="gridborder">
						<div id="GRD_INTERVIEWER" style="width:219px;height:80px;"></div>
						</td>
					</tr>
				</table>
				</td>		
				<td style="padding: 10px;">					
					<a href="#" onclick="javascript: selectItem(dataGridInterviewer,dataGridInterviewerUser);return false;" title="<bean:message key='common.add' />" >
						<img src="images/ico_rightarrow.gif"  border="0" />
					</a>
					<br/>
					<a href="#" onclick="javascript: deselectItem(dataGridInterviewerUser,dataGridInterviewer);return false;" title="<bean:message key='common.remove' />" >
						<img src="images/ico_leftarrow.gif"  border="0" />
					</a> 
				</td>					
				<td style="vertical-align: top;">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td class="gridborder">
						<div id="GRD_INTERVIEWER_USER" style="width:220px;height:97px;"></div>
						</td>		
					</tr>
				</table>
		</td></tr></table>
		</td></tr>
                
		<tr id="timezoneEdit">
			  <td class="label">
				  TimeZone:
			  </td>
			   <td></td>
			   <td id="timeZone" >
			   <script language="JavaScript">
					var opts = <%=UserUtils.getJSTimeZoneArray()%>;											
					selectBoxStatus = new SelectBox(opts,'<bean:write name="calendarForm" property="timeZone" />','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					selectBoxStatus.setOnChangeHandler('onChangeStatus');
					document.write(selectBoxStatus.getHtml());
					selectBoxStatus.init();
				</script>
			</td>
		  </tr>
                <tr>
                  <td ><bean:message key="new_appointment.label.date"/></td>
                  <td class="vGap"></td>
                  <td id="dateInput"><input type="text" name="date" id="date" size="11" onfocusout="javascript: refreshBackGround(this);" onblur="javascript: getFormattedDate(this); "/><img src="images/ico_cal.gif" onClick=" popUpCal.select(document.getElementById('date'),'date','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/></td>
                  <td id="dateText" style="height:15px;"></td>
                  
                </tr>
                <tr>
                  <td><bean:message key="new_appointment.label.time"/></td>
                  <td class="vGap"></td>
                  <td id="timeInput"><input type="text" name="time" id="time" size="11" onblur="getFormattedTime(this); "/><img src="images/clock.gif" style="margin-bottom:-3px;cursor:hand;" onclick="timePopUp.showTime(document.getElementById('time'), 'time'); return true;"/></td>
                  <td id="timeText" style="height:15px;"></td>
                </tr>
                <tr>
                  <td><bean:message key="new_appointment.label.duration"/></td>
                  <td class="vGap"></td>
                  <td nowrap="nowrap" valign="bottom" id="durationRadios" style="display:none;">
                    <img src="images/radiobutton.gif" name="duration30" onclick="javascript: setDuration(30);"/>&nbsp;30 min&nbsp;
                    <img src="images/radiobutton.gif" name="duration60" onclick="javascript: setDuration(60);"/>&nbsp;1 hr&nbsp;
                    <img src="images/radiobutton.gif" name="duration120" onclick="javascript: setDuration(120);"/>&nbsp;2 hrs&nbsp;
                    <img src="images/radiobutton.gif" name="duration180" onclick="javascript: setDuration(180);"/>&nbsp;3 hrs&nbsp;
                    <img src="images/radiobutton.gif" name="duration240" onclick="javascript: setDuration(240);"/>&nbsp;4 hrs
                  </td>
                  <td nowrap="nowrap" valign="bottom" id="durationText" style="display:none;height:15px;">
                    <span id="durationT30">30 min</span>
                    <span id="durationT60">1 hr</span>
                    <span id="durationT120">2 hrs</span>
                    <span id="durationT180">3 hrs</span>
                    <span id="durationT240">4 hrs</span>
                  </td>
                </tr>
               
                		 <tr>
			  <td class="label">
				  Interview Mode:
			  </td>
			    <td class="vGap"></td>
			    <td id="interviewModeEdit" valign="top" style="height:15px;"> </td>
			 
			  
			   <td id="interviewModeNew" >
				<script language="JavaScript">	
					
					var InterviewMode = <%=UserUtils.getInterviewMode()%>;											
					selectBoxModeStatus = new SelectBox(InterviewMode,'<bean:write name="calendarForm" property="interviewMode" />','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:20});
    				selectBoxModeStatus.setOnChangeHandler('onChangeModeStatus');
    				document.write(selectBoxModeStatus.getHtml());
    				selectBoxModeStatus.init();
				</script>
				
				
			</td> 
			
		  </tr>	
		  <tr>
										<td class="label">
										Interview Details:
										</td>
										<td class="vGap"></td>
			    <td id="detailsInterviewModeEdit" valign="top" style="height:15px;"> </td>
										&nbsp;&nbsp;&nbsp;&nbsp;</td><td id="detailsInterviewModeNew"><html:textarea styleId="detailsInterviewMode" property="detailsInterviewMode"	name="calendarForm"  style="width: 216px; height: 51px;" rows="3" cols="40" ></html:textarea></td>
									</tr> 
                <tr id="changeStatusRow">
                  <td><bean:message key="new_appointment.label.status"/></td>
                  <td class="vGap"></td>
                  <td id="status" nowrap="nowrap" style="height:15px;"></td>
                </tr>
                <tr><td colspan="3" style="height:5px;"></td></tr>
                <% if ("1".equalsIgnoreCase(GlobalApplicationProperties.getProperty("send_appointment")) && ModuleSet.isMODULE_OUTLOOK_MEETING_REQUEST()) { %>
                <tr>
                  <td ></td>
                  <td class="vGap"></td>	
                  <td><img src="images/checkboxchecked.gif" id="sendAppointment" name="sendAppointment" onclick="javascript: toggleChkBox(this);"/>&nbsp;<bean:message key="calendar.alert.send_notification"/></td>
                </tr> 
                <% } %>
              </table>
              <table>
                <tr>
                  <td colspan="4"><bean:message key="new_appointment.label.notifications"/></td>
                </tr>
                <tr>
                  <td class="vGap" style="width:67px;"></td>
                  <td nowrap="nowrap">&nbsp;&nbsp;<img src="" id="isRemindMe" name="isRemindMe" onclick="javascript: toggleRemindBoxes(this.src, 'isRemindMe');" height="13" width="13"/>&nbsp;<bean:message key="new_appointment.label.remindMe"/>&nbsp;</td>
                  <td id="remindMe" style="height:18px;"></td>
                  <td>
                  	<script type="text/javascript">
					    var options = new Array();
					    <logic:iterate id="remindMeTemplate" name="remindMeTemplates" type="com.talentPool.notifier.dataobject.TemplateData" indexId="indxId">
					    options[<bean:write name='indxId' />] = new SelectOption('<bean:write name="remindMeTemplate" property="templateCode"/>','<bean:write name="remindMeTemplate" property="templateName"/>');                       
        	            </logic:iterate>
                      	selectBoxRemindMeTemplate = new SelectBox(options,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:12});			        	
			        	document.write(selectBoxRemindMeTemplate.getHtml());
			        	selectBoxRemindMeTemplate.init();
					</script>
                  </td>
                </tr>
                <tr>
                  <td class="vGap"></td>
                  <td nowrap="nowrap">&nbsp;&nbsp;<img src="" id="isRemindInterviewers" name="isRemindInterviewers" onclick="javascript: toggleRemindBoxes(this.src, 'isRemindInterviewers');"/>&nbsp;<bean:message key="new_appointment.label.remindInterviewers"/>&nbsp;</td>
                  <td id="remindInterviewers" style="height:18px;"></td>
                  <td>
                  	<script type="text/javascript">
					    var options = new Array();
					    <logic:iterate id="remindInterviewerTemplate" name="remindInterviewerTemplates" type="com.talentPool.notifier.dataobject.TemplateData" indexId="indxId">
					    options[<bean:write name='indxId' />] = new SelectOption('<bean:write name="remindInterviewerTemplate" property="templateCode"/>','<bean:write name="remindInterviewerTemplate" property="templateName"/>');                       
        	            </logic:iterate>
                      	selectBoxRemindInterviewerTemplate = new SelectBox(options,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:12});			        	
			        	document.write(selectBoxRemindInterviewerTemplate.getHtml());
			        	selectBoxRemindInterviewerTemplate.init();
					</script>
                  </td>
                </tr>
                <tr>   
                  <td class="vGap"></td>     
                  <td nowrap="nowrap">&nbsp;&nbsp;<img src="" id="isRemindCandidate" name="isRemindCandidate" onclick="javascript: toggleRemindBoxes(this.src, 'isRemindCandidate');"/>&nbsp;<bean:message key="new_appointment.label.remindCandidate"/>&nbsp;</td>
                  <td id="remindCandidate" style="height:18px;"></td>
                  <td>
                  	<script type="text/javascript">
					    var options = new Array();
					    <logic:iterate id="remindApplicantTemplate" name="remindApplicantTemplates" type="com.talentPool.notifier.dataobject.TemplateData" indexId="indxId">
					    options[<bean:write name='indxId' />] = new SelectOption('<bean:write name="remindApplicantTemplate" property="templateCode"/>','<bean:write name="remindApplicantTemplate" property="templateName"/>');                       
        	            </logic:iterate>
                      	selectBoxRemindApplicantTemplate = new SelectBox(options,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:12});			        	
			        	document.write(selectBoxRemindApplicantTemplate.getHtml());
			        	selectBoxRemindApplicantTemplate.init();
					</script>
                  </td>
                </tr>
                <% if (userSMSEnabled) { %>
                <tr>
                  <td colspan="3"><bean:message key="new_appointment.label.sms_notifications"/></td>
                </tr>           
                <tr>
                  <td class="vGap"></td>
                  <td nowrap="nowrap">&nbsp;&nbsp;<img src="" id="isSMSRemindMe" name="isSMSRemindMe" onclick="javascript: toggleRemindBoxes(this.src, 'isSMSRemindMe');" height="13" width="13"/>&nbsp;<bean:message key="new_appointment.label.remindMe"/>&nbsp;</td>
                  <td id="smsRemindMe" style="height:18px;"></td>
                  <td>
                  	<script type="text/javascript">
					    var options = new Array();
					    <logic:iterate id="smsRemindMeTemplate" name="smsRemindMeTemplates" type="com.talentPool.notifier.dataobject.TemplateData" indexId="indxId">
					    options[<bean:write name='indxId' />] = new SelectOption('<bean:write name="smsRemindMeTemplate" property="templateCode"/>','<bean:write name="smsRemindMeTemplate" property="templateName"/>');                       
        	            </logic:iterate>
                      	selectBoxSmsRemindMeTemplate = new SelectBox(options,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:12});			        	
			        	document.write(selectBoxSmsRemindMeTemplate.getHtml());
			        	selectBoxSmsRemindMeTemplate.init();
					</script>
                  </td>
                </tr>
                <tr>
                  <td class="vGap"></td>
                  <td nowrap="nowrap">&nbsp;&nbsp;<img src="" id="isSMSRemindInterviewers" name="isSMSRemindInterviewers" onclick="javascript: toggleRemindBoxes(this.src, 'isSMSRemindInterviewers');"/>&nbsp;<bean:message key="new_appointment.label.remindInterviewers"/>&nbsp;</td>
                  <td id="smsRemindInterviewers" style="height:18px;"></td>
                  <td>
                  	<script type="text/javascript">
					    var options = new Array();
					    <logic:iterate id="smsRemindInterviewerTemplate" name="smsRemindInterviewerTemplates" type="com.talentPool.notifier.dataobject.TemplateData" indexId="indxId">
					    options[<bean:write name='indxId' />] = new SelectOption('<bean:write name="smsRemindInterviewerTemplate" property="templateCode"/>','<bean:write name="smsRemindInterviewerTemplate" property="templateName"/>');                       
        	            </logic:iterate>
                      	selectBoxSmsRemindInterviewerTemplate = new SelectBox(options,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:12});			        	
			        	document.write(selectBoxSmsRemindInterviewerTemplate.getHtml());
			        	selectBoxSmsRemindInterviewerTemplate.init();
					</script>
                  </td>
                </tr>
                <tr>   
                  <td class="vGap"></td>     
                  <td nowrap="nowrap">&nbsp;&nbsp;<img src="" id="isSMSRemindCandidate" name="isSMSRemindCandidate" onclick="javascript: toggleRemindBoxes(this.src, 'isSMSRemindCandidate');"/>&nbsp;<bean:message key="new_appointment.label.remindCandidate"/>&nbsp;</td>
                  <td id="smsRemindCandidate" style="height:18px;"></td>
                  <td>
                  	<script type="text/javascript">
					    var options = new Array();
					    <logic:iterate id="smsRemindApplicantTemplate" name="smsRemindApplicantTemplates" type="com.talentPool.notifier.dataobject.TemplateData" indexId="indxId">
					    options[<bean:write name='indxId' />] = new SelectOption('<bean:write name="smsRemindApplicantTemplate" property="templateCode"/>','<bean:write name="smsRemindApplicantTemplate" property="templateName"/>');                       
        	            </logic:iterate>
                      	selectBoxSmsRemindApplicantTemplate = new SelectBox(options,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:12});			        	
			        	document.write(selectBoxSmsRemindApplicantTemplate.getHtml());
			        	selectBoxSmsRemindApplicantTemplate.init();
					</script>
                  </td>
                </tr>    
                <% } %>	           
                
                <tr>
                  <td colspan="3"><br/>
                  	<div class="navBtn" style="float: left;margin-left:100px;">                  		
									    <a href="#" id="saveBtnLink" style="width:65px;" class="active" onclick="javascript: setAppointment('setAppointmentLayer');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
									    <a href="#" id="deleteBtnLink" style="width:65px;margin-left:5px;" class="active" onclick="javascript: deleteAppointment();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.delete"/></a>
									    <a href="#" style="width:65px; margin-left:5px;" class="active" onclick="javascript: cancelSetAppointmentOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
										</div>
									<br/></td>
                </tr>
                
              </table>
            </td>
          </tr>
        </table> 
      </div>   
  </div> 
</div>
</html:form>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<DIV id="timePopUpDiv" style="position:absolute;z-index:1000;background-color:#eee;display:none;" ></DIV>
<script language="JavaScript">
	var prevTblId="";
	//Create calender object
	var popUpCal = new CalendarPopup("calDiv"); 
	popUpCal.showNavigationDropdowns();
	var timePopUp = new TimePopUp("timePopUpDiv");
	
	cal = new Calendar("tpcal" + new Date().getFullYear(), "today", "now");
	cal.init();
function initScreen(){	
	document.getElementById('tbl').focus();
	scroll(cal, null, null);	
	updateRightPanel(cal);

	  var isAppointmentFullyEditable = document.calendarForm.isAppointmentFullyEditable.value == "true" ? true : false;
	  if(document.calendarForm.appointmentId.value != '') {
		 
	    var pars = "mode=getEditAppointmentXml&appointmentId=" + document.calendarForm.appointmentId.value;
	    var myAjax = ajaxCall("calendar.do",'get',pars,UpdateEditAppointmentScreen, reportError);
	  } else if (document.calendarForm.selectedApplicant.value != '') {
		 
	    var pars = "mode=getNewAppointmentXml&selectedApplicant=" + document.calendarForm.selectedApplicant.value;
	    var myAjax = ajaxCall("calendar.do",'get',pars,UpdateSetAppointmentScreen, reportError);
	  }
	  document.onkeydown = setFocus;
}

window.onload=doOnLoad;

function doOnLoad() {
	initPopUp();
	initScreen();
	Event.observe($('interviewernew'), "keyup", onCriteriaChange.bindAsEventListener(this));
}

function onChangeStatus(){
	document.calendarForm.timeZone.value=selectBoxStatus.getSelectedId();
}

function onChangeModeStatus(){
	document.calendarForm.interviewMode.value=selectBoxModeStatus.getSelectedId();
}

function showInterviewListFilter() {
	window.setTimeout("showInPopUp('reports.do?mode=interviewListFilter&popup=true',800,420,null,true);", 10);
}

function reloadWindow(){
	window.location="calendar.do?mode=calendarHome";
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function viewApplicantDetails(event,applicantId) {
	highlightSelectedAppointment(event);
	<logic:present scope="request" parameter="popup">
		window.location='selectionProcess.do?mode=viewOriginalResume&applicantId=<bean:write property="selectedApplicant" name="calendarForm"/>';
	</logic:present>
	<logic:notPresent scope="request" parameter="popup">
		var app = window.open("selectionProcess.do?mode=viewOriginalResume&applicantId=" + applicantId,applicantId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
		app.focus();
	</logic:notPresent>	
}

function deleteAppointment() {
		var appointmentId = document.calendarForm.appointmentId.value;
		var applicantId = document.calendarForm.selectedApplicant.value;
		var positionId = document.calendarForm.applicantPositionId.value;
		var isSendAppointment = getIsSendAppointment();
	 	var pars = "mode=deleteAppointment&appointmentId=" + appointmentId + "&isSendAppointmentNotification=" + isSendAppointment+
	 	"&selectedApplicant=" + applicantId +"&applicantPositionId=" + positionId;
	 	hide('setAppointmentLayer');	 	
	 	var myAjax = ajaxCall("calendar.do",'get',pars,fetchAppointmentsForCurrentWeek, reportError);		
}

function highlightSelectedAppointment(event) {
	var el = Event.findElement(event, 'table');
	if(el.id!=undefined && el.id.substring(0,5)=='appt_') {
		tblId = el.id;
		if(prevTblId!=""){
	  	Element.removeClassName($(prevTblId),'selectedAppointment');
	  }
	  Element.addClassName($(tblId),'selectedAppointment');
	  prevTblId=tblId;
	}
}

function toggleChkBox(elem) {
	if(elem.src.indexOf(chkedCheckBox) != -1) {
		elem.src = unChkedCheckBox;
	} else {
		elem.src = chkedCheckBox;
	}
}

var chkedChkBox='images/checkboxchecked.gif';
var unchkedChkBox='images/checkboxunchecked.gif';	
var userId = '';

function changeCheckboxState(obj){
	var propertyVal = '';
	if(obj.src.indexOf(chkedChkBox) != -1) {
		obj.src = unchkedChkBox;
		propertyVal = '0';
	} else {
		obj.src = chkedChkBox;
		propertyVal = '1';
	}
	return 	propertyVal;
}

function getCheckboxState(obj){
	var propertyVal = '';
	if(obj.src.indexOf(chkedChkBox) != -1) {
		
		propertyVal = '1';
	} else {
		
		propertyVal = '0';
	}
	return 	propertyVal;
}

function showReminders(obj) {
	var showReminder = changeCheckboxState(obj);
	var pars = "mode=updateApplicationSetting&propertyName=<%=GlobalConstants.PROPERTY_SHOW_REMINDER%>&propertyValue=" + showReminder;
	var myAjax = ajaxCall("adminHome.do","get",pars,onPropertyUpdate,reportError);
}

function changeMyAppointments(obj){
	var myAppointments = changeCheckboxState(obj);
	eraseCookie("MY_APPOINTMENT_" +userId);	
	createCookie("MY_APPOINTMENT_"+userId,myAppointments,360);
	fetchAppointmentsForCurrentWeek(null);
}

function showMyAppointments(){
	var myAppointments = readCookie("MY_APPOINTMENT_" +userId);
	if(myAppointments==null)
		myAppointments='<%=CalendarConstants.MY_APPOINTMENTS%>';
	toggleMyAppointmentsCheckBox(myAppointments);
	return myAppointments;
}
function toggleMyAppointmentsCheckBox(myAppointments){
	if(myAppointments=='<%=GlobalConstants.ENABLED%>')
		$('show_myAppointments').src=chkedChkBox;
	else
		$('show_myAppointments').src=unchkedChkBox;
}
function onPropertyUpdate(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="admin_application_settings.error.save_show_reminder_setting"/>');
		showReminders($("show_reminder"));
		return;
	}
	fetchAppointmentsForCurrentWeek(null);
}

function onClickReminder(remId,applicantId){
	//function defined in left panel
	var url = 'selectionProcess.do?mode=addReminder&reminderId='+remId+'&applicantId='+applicantId;
	if(applicantId == "0" || applicantId == '') {
		window.setTimeout("showInPopUp('"+url+"',550, 490,refreshTheAppointmentsData,true);", 10);
	} else {
		window.setTimeout("showInPopUp('"+url+"',550, 320,refreshTheAppointmentsData,true);", 10);
	}
}

function initGridInterviewer(interviewers) {	
	dataGridInterviewer = new dhtmlXGridObject('GRD_INTERVIEWER'); 
	dataGridInterviewer.imgURL = "images/"; 
	dataGridInterviewer.setHeader("User Name"); 
	dataGridInterviewer.setInitWidths("200");
	dataGridInterviewer.setColAlign("left");
	dataGridInterviewer.setColTypes("ro"); 
	dataGridInterviewer.setColSorting("interviewer_userName_sort");
	dataGridInterviewer.setNoHeader(true);	
	dataGridInterviewer.init();
	
	dataGridInterviewer.attachEvent("onXLE",doOnLoadingEndInterviewer);
	dataGridInterviewer.attachEvent("onKeyPress",onGridInterviewerKeyPressed);
	dataGridInterviewer.attachEvent("onRowSelect",doOnDataGridInterviewerRowSelectHandler);
	dataGridInterviewer.attachEvent("onRowDblClicked",doOnDataGridInterviewerRowDblClicked);
	dataGridInterviewer.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridInterviewerUser = new dhtmlXGridObject('GRD_INTERVIEWER_USER'); 
	dataGridInterviewerUser.imgURL = "images/"; 
	dataGridInterviewerUser.setHeader("User Name"); 
	dataGridInterviewerUser.setInitWidths("200");
	dataGridInterviewerUser.setColAlign("left");
	dataGridInterviewerUser.setColTypes("ro"); 
	dataGridInterviewerUser.setColSorting("interviewer_userName_sort");
	dataGridInterviewerUser.setNoHeader(true);	
	dataGridInterviewerUser.init();
	loadGridInterviewer(interviewers);	
	dataGridInterviewerUser.sortRows(0,'str',"asc");
	dataGridInterviewerUser.setSortImgState(true,0,"ASC");	
	dataGridInterviewerUser.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	dataGridInterviewerUser.attachEvent("onKeyPress",onGridInterviewerUserKeyPressed);
	dataGridInterviewerUser.attachEvent("onRowSelect",doOnDataGridInterviewerUserRowSelectHandler);
	dataGridInterviewerUser.attachEvent("onRowDblClicked",doOnDataGridInterviewerUserRowDblClicked);

	 
}

function doOnLoadingEndInterviewer() {
	dataGridInterviewer.sortRows(0,'str',"asc");
	dataGridInterviewer.setSortImgState(true,0,"ASC");	
}

function onGridInterviewerKeyPressed(keyCode,ctrl,shift) {
	var text = (dataGridInterviewer.cells(dataGridInterviewer.getSelectedId(),0)).getValue();
	dataGridInterviewerUser.clearSelection();
	onGridObjKeyPressed(dataGridInterviewer,dataGridInterviewerUser,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(dataGridInterviewer, text);
	}
}

function onGridInterviewerUserKeyPressed(keyCode,ctrl,shift) {
	dataGridInterviewer.clearSelection();
	onGridObjKeyPressed(dataGridInterviewerUser,dataGridInterviewer,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(dataGridInterviewer);
	}
}

function doOnDataGridInterviewerRowSelectHandler() {
	dataGridInterviewerUser.clearSelection();
}
function doOnDataGridInterviewerUserRowSelectHandler() {
	dataGridInterviewer.clearSelection();
}

function doOnDataGridInterviewerRowDblClicked() {
	var text = (dataGridInterviewer.cells(dataGridInterviewer.getSelectedId(),0)).getValue();
	selectItem(dataGridInterviewer,dataGridInterviewerUser);
	removeIdFromBackUp(dataGridInterviewer, text);		
}

function doOnDataGridInterviewerUserRowDblClicked() {
	selectItem(dataGridInterviewerUser,dataGridInterviewer);
	resetFilterBackUp(dataGridInterviewer);
}

function interviewer_userName_sort(a,b,order,aId,bId) {
	a0 = dataGridInterviewer.getUserData(aId,"activeUserName");
	b0 = dataGridInterviewer.getUserData(bId,"activeUserName");	
	return sort_data(a0,b0,order);
}

function loadGridInterviewer(interviewers){
	dataGridInterviewer.clearAll();
	var applicant = null;
	if(document.calendarForm.selectedApplicant.value != ""){
		applicant = document.calendarForm.selectedApplicant.value;
	}else{
		applicant = selectBoxApplicant.getSelectedId();
	}
	dataGridInterviewer.loadXML("calendar.do?mode=XMLInterviewers&appointmentId=" + document.calendarForm.appointmentId.value+"&selectedApplicant=" + applicant);
	setTimeout(" selectItems(interviewers+'' ,dataGridInterviewer,dataGridInterviewerUser) ", 100);
	
}

function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'GRD_INTERVIEWER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;	
		}	
	}if(grdId == 'GRD_INTERVIEWER_USER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;
		}	
	}
	return obj.cell.innerHTML;
}

var userSelection='';

function setUserSelection(text){
	userSelection=text;
}
function onCriteriaChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		case Event.KEY_UP:
		case Event.KEY_DOWN: 
		break;
		case Event.KEY_PAGEUP:
		case Event.KEY_PAGEDOWN:
		break;
		case Event.KEY_RETURN:
		break;
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			if(userSelection=='interviewernew'){
				dataGridInterviewer.filterBy(0, $('interviewernew').value, false);
			}		
		}
	}
}
</script>