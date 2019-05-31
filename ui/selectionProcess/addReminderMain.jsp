<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.documents.DocumentConstants,
				com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<link rel="stylesheet" type="text/css"	href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/timePopUp.css"> 
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script language="JavaScript" src="js/scripta/lib/prototype.js"	type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js"	type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js"	type="text/javascript"></script>
<script LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/TimePopUp.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js"	type="text/javascript"></script>

<div class="contentDivPop" style="width: 500px;">
<div class="outerDiv">
<html:form action="/selectionProcess" enctype="multipart/form-data" target="subForm">
<html:hidden property="mode" value="saveReminder" />
<html:hidden property="applicantId" />
	<logic:notEmpty property="applicantId" name="selectionProcessForm">
	<div class="popupTop">
	<table class="tblPop">
		<tr>
			<td class="header left" style="width:74px;"><bean:message key="add_reminder.label.about" /></td>
			<td><bean:write name="applicantData" property="applicantName"/>&nbsp;
			<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
				<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>
			<%} %>
			</td>
		</tr>
	</table>
	</div>
	</logic:notEmpty>
	<div class="popupBody">
	<table class="tblPop">
		<tr>
			<td class="header left" style="width:74px;"><bean:message key="add_reminder.label.date" /></td>
			<td><html:text name="selectionProcessForm" property="reminderDate" size="12" maxlength="10" styleId="reminderDate" onblur="javascript: getFormattedDate(this); "/> 
				<img src="images/ico_cal.gif" onClick="timePopUp.hidePopup(); cal.select(document.getElementById('reminderDate'),'reminderDate','dd/MM/yyyy'); return false;"	class="CalImg" name="imgCal" id="imgCal" />
			</td>
		</tr>
		<tr>
			<td class="header left" style="width:74px;"><bean:message key="add_reminder.label.time" /></td>
			<td><html:text name="selectionProcessForm" property="reminderTime" size="12" maxlength="10" styleId="reminderTime" onclick="timePopUp.hidePopup();" onblur="javascript: getFormattedTime(this);" /> 
				<img src="images/clock.gif"	style="margin-bottom:-3px;cursor:hand;"	onclick="timePopUp.showTime(document.getElementById('reminderTime'), 'reminderTime'); return false;" id="imgTime" />
			</td>
		</tr>
		<tr>
			<td class="header left" style="vertical-align:top;width:74px;"><bean:message key="add_reminder.label.desc" /></td>		
			<td><html:textarea property="reminderDesc" styleId="reminderDesc" name="selectionProcessForm" rows="10" cols="74" ></html:textarea></td>
		</tr>		
		<logic:empty property="applicantId" name="selectionProcessForm">		
		<tr>
			<td>&nbsp;</td>
			<td>
				<img src="images/checkboxunchecked.gif" onclick="javascript:showSelectCandidateDiv(this);" id="attachCandidateToReminder"/>&nbsp;&nbsp;<b><bean:message key="add_reminder.label.related_to_candidate" /></b>
			</td>			
		</tr>
		<tr id="selectCandidateDiv" style="display:none;">
			<td class="header left" style="width:74px;"><bean:message key="add_reminder.label.name"/></td>
			<td><input type="text" name="txtSearchCandidate" value="" id="txtSearchCandidate" size="35"/></td>
		</tr>
		<tr><td colspan="2"><div id="gridbox" style="width: 450px;margin-top: 5px;"></div></td></tr>
		</logic:empty>
	</table>
	</div>	
</html:form></div>
<logic:empty property="applicantId" name="selectionProcessForm">

</logic:empty>
<br/>
<div class="navBtn" style="float: right;">
	<a href="#" style="width:60px;" class="active" onclick="javascript: addReminder();"><span class="rightC"></span><span class="leftC"></span>
	<bean:message key="common.save" /></a> 
	<logic:notEmpty property="reminderId" name="selectionProcessForm">
	<a href="#" style="width:60px;margin-left: 5px;" class="active" onclick="javascript: onClickDeleteReminder();"><span class="rightC"></span><span class="leftC"></span>
	<bean:message key="common.delete" /></a> 
	</logic:notEmpty>
	<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span>
	<bean:message key="common.cancel" /></a>
</div>	
<DIV id="timePopUpDiv" style="position:absolute;z-index:1000;background-color:#eee;display:none;" ></DIV>  
<DIV ID="calDiv" STYLE="position:absolute;background-color:#FFF;"></DIV>
</div>
<script language="javascript">
<logic:empty property="applicantId" name="selectionProcessForm">
var chkedChkBox='images/checkboxchecked.gif';
var unchkedChkBox='images/checkboxunchecked.gif';
function showSelectCandidateDiv(obj) {
	if(obj.src.indexOf(chkedChkBox) != -1) {
		obj.src=unchkedChkBox;
		$("selectCandidateDiv").style.display='none';
		$("gridbox").style.display='none';		
	} else {
		obj.src=chkedChkBox;
		$("selectCandidateDiv").style.display='';
		$("gridbox").style.display='';
	}	
}
var gridBox;
function initGrid(){
	gridBox = new dhtmlXGridObject('gridbox');
	gridBox.imgURL = "images/"; 
	gridBox.setHeader("<bean:message key="add_reminder.label.name"/>");
	gridBox.setInitWidths("430")
	gridBox.setColAlign("left")
	gridBox.setColTypes("ro");
	gridBox.setColSorting("na");
	gridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	gridBox.enableAutoHeigth(true,140);
	gridBox.init();
	loadGrid();
}

function loadGrid(){
	gridBox.clearAll();
	if($('txtSearchCandidate').value.trim() != '') {
		gridBox.loadXML(uncache("selectionProcess.do?mode=searchApplicant&applicantName=" +$('txtSearchCandidate').value));	
	}	
}
</logic:empty>
	//Create calender object
	var cal = new CalendarPopup("calDiv"); 
	cal.showNavigationDropdowns();
	
	var timePopUp = new TimePopUp("timePopUpDiv");
	
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
      alert("<bean:message key="add_reminder.error.invalid_time_format"/>");
      obj.focus();
    }    
  }
  return true;  
}


function addReminder(){
	var errMsg = '';
	var reminderDate=document.getElementById('reminderDate').value;
	var reminderTime=document.getElementById('reminderTime').value;
	var reminderDesc=document.getElementById('reminderDesc').value;
	if(reminderDate==''){
		errMsg +='<bean:message key="add_reminder.label.no_date"/>';
	}
	if(reminderTime==''){
		if (errMsg.length > 0) {
        errMsg += '\n';
      }
      errMsg +='<bean:message key="add_reminder.label.no_time"/>';
	}
	if(reminderDesc==''){
		if (errMsg.length > 0) {
        errMsg += '\n';
      }
      errMsg +='<bean:message key="add_reminder.label.no_desc"/>';
	} 
	
    
    if (errMsg.length > 0) {
      alert(errMsg);
      return false;
    }
    <logic:empty property="applicantId" name="selectionProcessForm">
    var applicantId = gridBox.getSelectedId();
    if(applicantId == null) {
    	applicantId = '';
    }    
    </logic:empty>
    <logic:notEmpty property="applicantId" name="selectionProcessForm">
    	 var applicantId = '<bean:write property="applicantId" name="selectionProcessForm"/>';
    </logic:notEmpty>
	var pars = "mode=saveReminder&applicantId=" + applicantId + "&reminderId=<bean:write property="reminderId" name="selectionProcessForm"/>&reminderDate="+reminderDate+"&reminderTime="+reminderTime+"&reminderDesc="+reminderDesc;
	var myAjax = ajaxCall("selectionProcess.do","post",pars,addReminderResult,reportError);
}
<logic:notEmpty property="reminderId" name="selectionProcessForm">
function onClickDeleteReminder(remId){
	if(confirm("You are about to delete this Reminder ?")){
		var pars = "mode=deleteReminder&reminderId="+<bean:write property="reminderId" name="selectionProcessForm" />;
		var myAjax = ajaxCall("dashboard.do",'get',uncache(pars),onDeleteReminder, reportError);
  	}
}   
</logic:notEmpty> 
function onDeleteReminder(request){
 	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="dashboard.errors.reminder.cannot_delete"/>');
		return;
	}else{
		window.top.hidePopWin(true);
	}
}

function addReminderResult(request){
	xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
          return;
    }
    if(isErrorXml(xmlFile)){
		var errors=getErrors(xmlFile);
		alert(errors[0]);
		return;
	}else{
		window.top.hidePopWin(true);
	}
}

function setPopupTitle(){
	var title = '';
	<logic:notEmpty property="reminderId" name="selectionProcessForm">
		title += '<b><bean:message key="add_reminder.title.edit_reminder"/> </b>';
	</logic:notEmpty>
	<logic:empty property="reminderId" name="selectionProcessForm">
		title += '<b><bean:message key="add_reminder.title.add_reminder"/> </b>';
	</logic:empty>
	<logic:notEmpty property="applicantId" name="selectionProcessForm">
	title += ' - ';
	title += '<bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
   	</logic:notEmpty>
	window.top.setPopTitle(title);
}

window.onload=doOnLoad;

<logic:empty property="applicantId" name="selectionProcessForm">
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
			loadGrid();
		}
	}
}

function doOnLoad() {
	setPopupTitle();
	initGrid();
	Event.observe($('txtSearchCandidate'), "keyup", onCriteriaChange.bindAsEventListener(this));
	$("selectCandidateDiv").style.display='none';
	$("gridbox").style.display='none';		
}
</logic:empty>
<logic:notEmpty property="applicantId" name="selectionProcessForm">
function doOnLoad() {
	setPopupTitle();
}
</logic:notEmpty>
</script>
