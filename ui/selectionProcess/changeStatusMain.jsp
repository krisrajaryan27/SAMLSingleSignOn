<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
window.top.hidePopWin(true);
</logic:present>                
var selectBoxMessage=null;
</script>
<div class="contentDivPop" style="width:500px;">
	<div class="outerDiv">
	<html:form action="/selectionProcess">
  	<html:hidden property="mode" name="selectionProcessForm"/>
  	<html:hidden property="applicantId" name="selectionProcessForm"/>
  	<html:hidden property="statusMessage" name="selectionProcessForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="change_status.label.select_status"/>
			</td>
			<td>
			<script type="text/javascript">
				var opts = <bean:write name="selectionProcessForm" property="JSMessagesArray" filter="false"/>;
				var m = [new SelectOption('-1','<bean:message key="change_status.label.select_option"/>')];
				opts = m.concat(opts);
				selectBoxMessage = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:12});        				
				document.write(selectBoxMessage.getHtml());
				selectBoxMessage.init();
			</script>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
</div>


<script language="JavaScript">
function submitForm(){
	var id = selectBoxMessage.getSelectedId();
	if (id == '-1') {
		alert('<bean:message key="change_status.error.select_status"/>');
		return;
	} else {
		// save the status message.		
		document.selectionProcessForm.statusMessage.value= selectBoxMessage.getText(selectBoxMessage.getSelectedIndex());
		document.selectionProcessForm.submit();
	}
}
function setPopupTitle(){
	var title = '<b><bean:message key="change_status.label.change_status"/> - </b><bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}

window.onload = setPopupTitle;

</script>