<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<div class="contentDivPop" style="width:490px;">
	<div class="outerDiv">
		<div class="popupTop">
			<table class="tblPop" width="100%">
			<tr>
				<td class="header" width="90">
					<bean:message key="offer_proposal.label.proposed_by"/>: 
				</td>
				<td>
					<bean:write name="offerDetailsModifiedInteractionData" property="userName" scope="request"/>
				</td>
				<td class="header right" >
					<bean:message key="offer_proposal.label.proposed_date"/>:
				</td>
				<td width="130">
					<bean:write name="offerDetailsModifiedInteractionData" property="dateCreatedToDisplay" scope="request" />
				</td>
			</tr>
			<logic:notEmpty name="offerDetailsModifiedInteractionData" property="offerCode" scope="request">
				<tr>
					<td class="header" width="90">
						<bean:message key="generate_offer_sheet.label.offer_code"/>: 
					</td>
					<td>
						<bean:write name="offerDetailsModifiedInteractionData" property="offerCode" scope="request"/>
					</td>
					<td class="header right" >
						<bean:message key="generate_offer_sheet.label.offer_sheet_name"/>:
					</td>
					<td width="130">
						<bean:write name="offerDetailsModifiedInteractionData" property="offerSheetName" scope="request" />
					</td>
				</tr>			
			</logic:notEmpty>
			<tr>
				<td class="header" width="90">
					<bean:message key="common.action"/>: 
				</td>
				<td colspan="3">
					<bean:write name="offerDetailsModifiedInteractionData" property="interactionTitle" scope="request"/>
				</td>
			</tr>
			</table>
		</div>
		<div class="popupBody">
			<table class="tblPop">
				<tr>
					<td width="90px">&nbsp;</td>
					<td class="header" width="100px">Previous</td>
					<td class="header" width="100px">Changed</td>
				</tr>
				<tr>
					<td width="80px"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)%></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="previousInputSalaryVariable" scope="request" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="changedInputSalaryVariable" scope="request" /></td>
				</tr>
				<tr>
					<td width="80px"><bean:message key="ctc_comparison_screen.text.comparison_grid_ctc" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="previousCtc" scope="request" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="changedCtc" scope="request" /></td>
				</tr>
				<tr>
					<td width="80px"><bean:message key="ctc_comparison_screen.text.comparison_grid_basic" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="previousBasic" scope="request" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="changedBasic" scope="request" /></td>
				</tr>
				<tr>
					<td width="80px"><bean:message key="ctc_comparison_screen.text.comparison_grid_designation" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="previousDesignation" scope="request" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="changedDesignation" scope="request" /></td>
				</tr>
				<tr>
					<td width="80px"><bean:message key="ctc_comparison_screen.text.comparison_grid_level" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="previousLevel" scope="request" /></td>
					<td width="80px"><bean:write name="offerDetailsModifiedInteractionData" property="changedLevel" scope="request" /></td>
				</tr>
			</table>
		</div>	
		<div class="navBtn" style="float: right;margin-top: 15px;">
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
		</div>
	</div>
</div>
<script language="JavaScript">
function onWinLoad(){
	var title = '';
	var interactionType = '<bean:write name="offerDetailsModifiedInteractionData" property="interactionType" scope="request" />';
	if(interactionType=='<%=SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL%>'){
		title = '<b><bean:message key="offer_proposal.title.offer_proposal"/> - </b><bean:write name="offerDetailsModifiedInteractionData" property="applicantName" scope="request" /> &nbsp';
	}else if(interactionType=='<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>'){
		title = '<b><bean:message key="generate_offer_sheet.text.offer_generated_modified"/> - </b><bean:write name="offerDetailsModifiedInteractionData" property="applicantName" scope="request" /> &nbsp';
	}else{
		title = '<b><bean:message key="offer_proposal.text.interaction_mesage"/> - </b><bean:write name="offerDetailsModifiedInteractionData" property="applicantName" scope="request" /> &nbsp';
	}
	window.top.setPopTitle(title);
}

window.onload = onWinLoad;
</script>