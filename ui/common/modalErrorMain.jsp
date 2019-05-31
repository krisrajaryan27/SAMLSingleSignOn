<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<div class="contentDivPop">
<%
String callback = (String)request.getAttribute("callback");
if (request.getAttribute(Globals.ERROR_KEY) != null) {
%>
<table id="m_errortable">
	<tr>
		<td class="header"><b><bean:message
			key="common.error.header" /></b></td>
	</tr>
	<tr>
		<td class="message"><html:errors /></td>
	</tr>
</table>
<br>
<%
}
%>
<div class="navBtn" style="float:left;">
	<a href="#" style="width:60px;" class="active" onclick="javascript:closeme();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
</div>

</div>
<script language="JavaScript">
function setTitle(){
	window.top.setPopTitle("Error");
}
function closeme(){
	<% if(callback==null) { %>
	window.top.hidePopWin(false);
	<% } else { %>
	window.top.hidePopWin(true);
	<% } %>
}

window.onload=setTitle;
</script>

