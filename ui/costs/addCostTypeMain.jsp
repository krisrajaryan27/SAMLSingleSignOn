<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page
	import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>

<div class="contentDivPop" style="width:450px;">
<%
if (request.getAttribute(Globals.ERROR_KEY) != null) {
%>
<table id="m_errortable">
	<tr>
		<td class='header'><b><bean:message
			key="errors.following_errors" /></b></td>
	</tr>
	<tr>
		<td class="message"><html:errors /></td>
	</tr>
</table>
<br />
<br />
<%
}
%>
<div class="outerDiv"><html:form action="/costType"
	onsubmit="submitForm();return false;">
	<html:hidden property="costTypeId" name="costTypeForm" />
	<html:hidden property="mode" name="costTypeForm" />
	<html:hidden property="subMode" name="costTypeForm" />

	<div class="popupTop">
	<table class="tblPop">
		<tr>
			<td class="header"><bean:message key="cost_types.label.cost_type" />:</td>
			<td><html:text property="costType" name="costTypeForm" size="35"></html:text>
		</tr>
	</table>
	</div>
	<div class="popupBody">
	<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;"><a href="#"
				style="width:60px;" class="active"
				onclick="javascript: submitForm();"><span class="rightC"></span><span
				class="leftC"></span><bean:message key="common.submit" /></a> <a
				href="#" style="width:60px; margin-left:5px;" class="active"
				onclick="javascript: window.top.hidePopWin(false);return false;"><span
				class="rightC"></span><span class="leftC"></span><bean:message
				key="common.cancel" /></a></div>
			</td>
		</tr>
	</table>
	</div>
</html:form></div>
</div>


<script type="text/javascript">

function submitForm(){
	var Name = document.costTypeForm.costType.value;
	if(Name.trim()==""){
		return;
	}
	document.costTypeForm.submit();
}

function actionOnLoad(){
	<logic:equal name="costTypeForm" property="subMode" value="<%=MastersConstants.SUB_MODE_ADD%>">
		window.top.setPopTitle('<b><bean:message key="cost_types.label.add_cost_type" /></b>');
	</logic:equal>
	<logic:equal name="costTypeForm" property="subMode" value="<%=MastersConstants.SUB_MODE_EDIT%>">
		window.top.setPopTitle('<b><bean:message key="cost_types.label.edit_cost_type" /></b>');
	</logic:equal>
}
window.onload=actionOnLoad;
</script>
