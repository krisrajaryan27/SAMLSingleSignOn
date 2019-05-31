<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@ page import="java.security.KeyPair"%>
<%@ page import="com.talentPool.encryption.JCryptionUtil"%>
<script src="encryption/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="encryption/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/encrypt.js" type="text/javascript"></script>
<script src="encryption/js/jquery.jcryption-1.1.js" type="text/javascript"></script>
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
	<html:hidden property="mode" value="setAdminUserName"/>
	<html:hidden property="ldapEnabled"/>
	<html:hidden property="serverURL"/>
	<html:hidden property="securityPrincipal"/>	
	<html:hidden property="rnd" name="ldapForm"/>
	
	<input type="hidden" name="isAuthSubmitted" value="1" />
	
	<div class="contentDivPop" style="padding-right:20px;">
		<table width="100%" class="boxHeader" style="margin-top:5px;" cellspacing="0" cellpading="0">
			<tr>
				<td class="header" height="18"><strong><bean:message key="admin.ldap_set_admin.label.title" /></strong></td>
			</tr>
		</table>
		<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
			<table border="0" cellspacing="0" cellpadding="0" class="posinput">
				<tr>
				  <td class="Grey" colspan="3">
				    <bean:message key="admin.ldap_set_admin.help.message"/>
				  </td>
				</tr>
				<tr>
				  <td class="label">
				    <bean:message key="admin.ldap_set_admin.label.admin_username"/>
				  </td>
				  <td ></td>
				  <td>
				    <html:text property="adminUserName" size="30" maxlength="50"/>
				  </td>
				</tr>
				<tr>
				  <td class="label">
				    <bean:message key="admin.ldap_set_admin.label.admin_password"/>
				  </td>
				  <td ></td>
				  <td>
				    <html:password property="adminPassword" size="30" maxlength="50"/>
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
var keys;
jQuery.ajaxSetup({ cache: false });

jQuery(document).ready(function() {
	jQuery.jCryption.getKeys("EncryptionServlet?generateKeypair=true",jsonCallback );
});		

function jsonCallback(receivedKeys) {
	keys = receivedKeys;
}
function submitForm(){
	rnd = Math.round(Math.random())+2;
	document.ldapForm.rnd.value=rnd;
	var newPass = Encrypt(document.ldapForm.adminPassword.value,rnd);	
	
		jQuery.jCryption.encrypt(newPass, keys, function(encryptedNewPasswd) {
			document.ldapForm.adminPassword.value=encryptedNewPasswd;
			document.ldapForm.submit();
		});
	
}
function goto(){
	window.location="ldapHome.do?mode=ldapSettings";
}
</script>