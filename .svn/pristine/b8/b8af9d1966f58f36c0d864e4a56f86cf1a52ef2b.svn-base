<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.applicant.dataobject.ApplicantData,
                  com.talentPool.calendar.dataobject.AppointmentData,
                  com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                  com.talentPool.common.NavigationConstants"%>
<bean:define id="data" name="appointmentData" scope="request" type="AppointmentData" />
<div class="contentDivPop" style="width:500px;">
	<div class="outerDiv">
	<div class="popupTop">
		<table class="tblPop" width="100%">
		<tr>
			<td class="header" width="80">
			<bean:message key="view_appointment.label.candidate"/>
			</td>
			<td>
			<bean:write name="data" property="applicantName"/>
			</td>
			<td class="header right" width="70">
			<bean:message key="view_appointment.label.date"/>:
			</td>
			<td width="150">
			<bean:write name="data" property="appointmentFromDateTimeToDipslay" />
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td class="header">
			<bean:message key="view_appointment.label.appointmentMadeBy"/>:
			</td>
		</tr>
		<tr>
			<td>
				<bean:write name="data" property="appointmentCreatorName"/>
			</td>
		</tr>
		<tr>
			<td class="header">
			<bean:message key="view_appointment.label.interviewers"/>:
			</td>
		</tr>
		<tr>
			<td>
				<bean:write name="data" property="interviewer"/>
			</td>
		</tr>
		
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCHEDULE_INTERVIEW">
			<logic:notPresent name="nonEditable" scope="request">
			<a href="#" style="width:60px; margin-right: 5px;" class="active" onclick="javascript: editMe('<bean:write name="data" property="appointmentId"/>', '<bean:write name="data" property="applicantId"/>', '<bean:write name="data" property="isAppointmentFullyEditable"/>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
			</logic:notPresent>
            </logic:equal>    
			<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>	
	</div>
</div>

<script language="JavaScript">
var returnVal="";
function editMe(appointmentId, applicantId, isEditable) {
    returnVal = "calendar.do?mode=calendarHome&appointmentId=" + appointmentId + "&selectedApplicant=" + applicantId + "&isAppointmentFullyEditable=" + isEditable;
    window.top.hidePopWin(false);
    window.top.editAppointment(returnVal);
}

function onWinLoad(){
	var title = '<b><bean:message key="view_appointment.label.appointment"/> - </b><bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
window.top.setPopTitle(title);
}

window.onload = onWinLoad;
</script>