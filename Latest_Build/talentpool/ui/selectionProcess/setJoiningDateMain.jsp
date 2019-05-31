<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.selectionProcess.SelectionProcessConstants,
                com.talentPool.applicant.dataobject.ApplicantData,
                com.talentPool.common.utils.CommonUtils,
                com.talentPool.selectionProcess.form.SelectionProcessForm,
                com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                java.util.ArrayList,
                com.talentPool.common.properties.TPApplicationProperties"%>
<logic:present name="update" scope="request">
<script>
window.top.hidePopWin(true);
</script>
</logic:present>                
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"> 
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
<% } %>
<bean:define id="applicantData" name="applicantData" scope="request" type="ApplicantData"/>

<div class="contentDivPop" style="width: 500px;">
	<div class="outerDiv">
	<html:form action="/selectionProcess">
  	<html:hidden property="mode" name="selectionProcessForm"/>
  	<input type="hidden" name="logDate" value="<bean:write name="applicantData" property="applicantDateJoined" format="dd/MM/yyyy"/>">
  	<html:hidden property="applicantId" name="selectionProcessForm"/>
	<input type="hidden" name="updated" value="1">
	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="set_joining_date.joining_date"/>
			</td>
			<td>
                <html:text name="selectionProcessForm" property="joiningDate" size="12" maxlength="10" styleId="joiningDate" onblur="getFormattedDate(this);"/>
                <img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('joiningDate'),'joiningDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal"/>
			</td>
		</tr>
		</table>
	</div>

	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;"><a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
			<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>	
	</html:form>
	</div>
</div>
<DIV ID="calDiv" STYLE="position:absolute;visibility:hidden;background-color:#FFF;"></DIV>	

<script LANGUAGE="JavaScript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();
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

//submit for if notes is submitted	
function submitForm() {
	document.selectionProcessForm.submit(); 
   	return true;
}


function setPopupTitle(){
	var title = '<b><bean:message key="set_joining_date.title"/> - </b>';
	title += '<bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}

window.onload = setPopupTitle;
</script>