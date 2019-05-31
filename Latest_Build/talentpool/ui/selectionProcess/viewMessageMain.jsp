<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<div class="contentDivPop" >
	<div class="outerDiv">
	<div class="popupTop">
		<table class="tblPop" width="100%" >
		<tr>
			<td class="header right">
			<bean:message key="view_message.label.message_from"/>
			</td>
			<td>
			<bean:write name="selectionProcessForm" property="userNameFrom"/>
			</td>
			<td>
				<table align="right">
					<tr>
					<td class="header right" >
					<bean:message key="view_message.label.date_time"/>
					</td>
					<td >
						<bean:write name="selectionProcessForm" property="logDate"/>
					</td>
					</tr>
				</table>
			</td>			
		</tr>		
		<tr>
			<td class="header right">
			<bean:message key="view_message.label.message_to"/>
			</td>
			<td colspan="2">
			<bean:write name="selectionProcessForm" property="userNameTo"/>
			</td>
		</tr>
		<tr>
			<td class="header right">
			<bean:message key="view_message.label.message_cc"/>
			</td>
			<td colspan="2">
			<bean:write name="selectionProcessForm" property="userNamesCc"/>
			</td>
		</tr>
		<tr>
			<td class="header right" width="40">
			<bean:message key="view_message.label.message_about"/>
			</td>
			<td colspan="2">
			<a href="#" onclick="gotoDetails();"><bean:write name="selectionProcessForm" property="applicantName" /></a>
			</td>
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
				<html:textarea property="messageText"  name="selectionProcessForm" readonly="true" rows="10" cols="90"></html:textarea>
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
	
	var title = '<b><bean:message key="applicant_listing.label.message_text"/> - </b><bean:write name="selectionProcessForm" property="applicantName"/> &nbsp';
	title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="selectionProcessForm" property="sourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	window.top.setPopTitle(title);
}

window.onload = onWinLoad;
</script>