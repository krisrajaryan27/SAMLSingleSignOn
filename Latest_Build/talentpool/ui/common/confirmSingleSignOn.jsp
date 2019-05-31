<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<html:form action="/login" >
	<html:hidden property="ignoreSignedOn" value="1"/>
	<html:hidden property="loginmode" value="login"/>
</html:form>
<script type="text/javascript">
window.onload=confirmLogin;
	function confirmLogin(){
		var confirmed = confirm('<bean:message key="login.errors.single_signon_error"/>');
		if(confirmed){
			document.loginForm.submit();
		} else{
			document.loginForm.ignoreSignedOn.value="0";
			document.loginForm.submit();
		}
	}
</script>
</body>
</html>