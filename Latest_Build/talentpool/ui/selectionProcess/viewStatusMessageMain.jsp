<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<div class="contentDivPop" style="width:500px;">
	<div class="outerDiv">
	<div class="popupTop">
		<table class="tblPop" width="100%">
		<tr>
			<td class="header" width="80">
			<bean:message key="view_status_message.label.status_changed_by"/> 
			</td>
			<td>
			<bean:write name="communicationData" property="name" scope="request"/>
			</td>
			<td class="header right" width="70">
			<bean:message key="view_status_message.label.chnaged_date"/>
			</td>
			<td width="150">
			<bean:write name="communicationData" property="communicationDateToDisplay" scope="request" />
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td class="header">
			<bean:message key="view_status_message.label.changed_to"/>	
			</td>
		</tr>
		<tr>
			<td>
				<bean:write name="communicationData" property="communicationText" scope="request"/>   
			</td>
		</tr>
		<tr>
			<td>
			<div class="navBtn" style="float: right;"><a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a></div>
			</td>
		</tr>
		</table>
	</div>	
	</div>
</div>

<script language="JavaScript">
function onWinLoad(){
	var title = '<b><bean:message key="view_status_message.label.change_status"/> - </b><bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
window.top.setPopTitle(title);
}

window.onload = onWinLoad;
</script>