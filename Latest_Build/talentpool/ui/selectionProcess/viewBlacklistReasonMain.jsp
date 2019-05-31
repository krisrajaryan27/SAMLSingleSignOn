<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<%@page import="com.talentPool.applicant.dataobject.ApplicantBlackListHistoryData"%><div class="contentDivPop" >
	<div class="outerDiv">
		<div class="popupTop">
			<table class="tblPop" width="100%">
				<tr>
					<td class="header" width="20%">
						<logic:equal value="<%=String.valueOf(SelectionProcessConstants.INTERACTION_BLACKLISTED) %>" name="blackListData" property="interactionType" scope="request">
							<bean:message key="black_list.label.blackListBy"/>
						</logic:equal>
						<logic:equal value="<%=String.valueOf(SelectionProcessConstants.INTERACTION_UNBLACKLISTED) %>" name="blackListData" property="interactionType" scope="request">
							<bean:message key="black_list.label.unBlackListBy"/>
						</logic:equal>
					</td>
					<td width="30%">
						<bean:write name="blackListData" property="userName" scope="request"/>
					</td>
					<td class="header right" width="10%">
						<bean:message key="black_list.label.date_time"/>
					</td>
					<td width="40%">
						<bean:write name="blackListData" property="dateCreatedToDisplay" scope="request"   />
					</td>
				</tr>
			</table>			
		</div>
		<div class="popupBody">
			<table class="tblPop">
				<tr>
					<td class="header">
						<logic:equal value="<%=String.valueOf(SelectionProcessConstants.INTERACTION_BLACKLISTED) %>" name="blackListData" property="interactionType" scope="request">
							<bean:message key="black_list.label.blackList_reason"/>
						</logic:equal>
						<logic:equal value="<%=String.valueOf(SelectionProcessConstants.INTERACTION_UNBLACKLISTED) %>" name="blackListData" property="interactionType" scope="request">
							<bean:message key="black_list.label.unBlackList_reason"/>
						</logic:equal>
					</td>
				</tr>
				<tr>
					<td>
						<html:textarea property="blackListReason"  name="blackListData" readonly="true" rows="10" cols="90"></html:textarea>
					</td>
				</tr>
				<tr>
					<td>
					<div class="navBtn" style="float: right;">
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
function onWinLoad(){
	var title = '<b>';
	<logic:equal value="<%=String.valueOf(SelectionProcessConstants.INTERACTION_BLACKLISTED) %>" name="blackListData" property="interactionType" scope="request">
		title ='<bean:message key="black_list.label.blackListed"/>';
	</logic:equal>
	<logic:equal value="<%=String.valueOf(SelectionProcessConstants.INTERACTION_UNBLACKLISTED) %>" name="blackListData" property="interactionType" scope="request">
		title ='<bean:message key="black_list.label.unBlackListed"/>';
	</logic:equal>
	title +='- </b>';
	title += '<bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}
window.onload = onWinLoad;
</script>