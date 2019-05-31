<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<div class="contentDivPop" >
	<div class="outerDiv">
		<div class="popupTop">
			<table class="tblPop" width="100%">
				<tr>
					<td class="header" width="70">
	                   <bean:message key="view_sms.label.sent_by"/>
					</td>
					<td>
						<bean:write name="communicationData" property="name" scope="request"/>
					</td>
					<td class="header right" width="70">
						<bean:message key="view_sms.label.date_time"/>
					</td>
					<td width="170">
						<bean:write name="communicationData" property="communicationDateToDisplay" scope="request" />
					</td>
				</tr>
			</table>			
			<table class="tblPop" width="100%">
		        <tr>
		          <td class="header" width="70"><bean:message key="view_sms.label.number_sent"/></td>
		  		  <td><bean:write name="communicationData" property="communicationPhoneNo" scope="request"/></td> 			                
		        </tr>
			</table>
		</div>
		<div class="popupBody">
			<table class="tblPop">
				<tr>
					<td class="header">
						<bean:message key="applicant_listing.label.message_text"/>	
					</td>
				</tr>
				<tr>
					<td>
						<html:textarea property="communicationText"  name="communicationData" readonly="true" rows="10" cols="90"></html:textarea>
					</td>
				</tr>
				<tr>
					<td>
					<div class="navBtn" style="float: right;">
					<logic:present parameter="showDelete" scope="request"> 
					<a href="#" style="width:60px;margin-right: 5px;" class="active" onclick="javascript: window.top.hidePopWin(true);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.delete"/></a>
					</logic:present>
					<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
					</div>
					</td>
				</tr>
			</table>
		</div>	
	</div>
</div>

<script language="JavaScript">
var returnVal='<bean:write name="selectionProcessForm" property="communicationId"/>';
function gotoDetails(){
	window.top.hidePopWin(false);
	window.top.viewApplicant(<bean:write property="applicantId" name="selectionProcessForm"/>);
}
function onWinLoad(){
	
	var title = '<b><bean:message key="applicant_listing.label.sms"/> - </b><bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}

window.onload = onWinLoad;
</script>