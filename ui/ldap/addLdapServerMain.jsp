<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<div class="contentDiv">
	<div id="divError" style="display:block">
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
	</div>
</div>
<html:form action="/ldapHome">
	<html:hidden property="t" name="ldapForm"/>
	<html:hidden property="mode" value="addLdapServer"/>
	<html:hidden property="serverId"/>
	<html:hidden property="ldapEnabled"/>
	<input type="hidden" name="isSubmitted" value="1" />
	
	<div class="contentDivPop" style="padding-right:20px;">
		<table width="100%" class="boxHeader" style="margin-top:5px;" cellspacing="0" cellpading="0">
			<tr>
				<td class="header" height="18"><strong><bean:message key="admin.ldap_settings.label.ldap_server_settings" /></strong></td>
			</tr>
		</table>
		<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
			<table border="0" cellspacing="0" cellpadding="0" class="posinput">
				<tr>
				  <td class="label">
				    <bean:message key="admin.ldap_settings.label.ldap_server_url"/>
				  </td>
				  <td ></td>
				  <td>
				    <html:text property="serverURL" size="80" maxlength="100"></html:text>
				  </td>
				</tr>
				<tr>
				  <td class="label">
				  </td>
				  <td ></td>
				  <td class="Grey">
				    <bean:message key="admin.ldap_settings.help.ldap_server_url"/>
				  </td>
				</tr>
				<tr>
				  <td class="label">
				    <bean:message key="admin.ldap_settings.label.ldap_security_principal"/>
				  </td>
				  <td ></td>
				  <td>
				    <html:text property="securityPrincipal" size="80" maxlength="250"></html:text>
				  </td>
				</tr>
				<tr>
				  <td class="label">
				  </td>
				  <td ></td>
				  <td class="Grey">
				    <bean:message key="admin.ldap_settings.help.ldap_security_principal"/>
				  </td>
				</tr>
		</table>
	</div>
	<div class="navBtn" style="margin-top:5px;"><a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
	<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript:goto();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
	</div>
	</div>
</html:form>
<script language="JavaScript">
function submitForm(){
	document.ldapForm.submit();
}
function goto(){
	window.location="ldapHome.do?mode=ldapSettings";
}
</script>