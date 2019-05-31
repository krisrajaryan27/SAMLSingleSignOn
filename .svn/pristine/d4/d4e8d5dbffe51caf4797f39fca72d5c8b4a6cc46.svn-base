<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<html>
<head><title>Post Login Screen</title></head>
<body>

</body>
</html>
<script>

function loadOther(){
	<logic:present name="passwordExpired" scope="request">
	<% int days =(Integer) request.getAttribute("passwordExpired"); %>
	var r = confirm('<bean:message key = "change_password.password_expire_soon" arg0="<%=String.valueOf(days)%>"/>');
	if(r==true){
		window.location=uncache("user.do?mode=changePassword&agePasswordChange=1");
	}else{
		window.location=uncache("login.do?loginmode=postLogin");
	}
	</logic:present> 
	<logic:notPresent name="passwordExpired" scope="request">
	<% response.sendRedirect("login.do?loginmode=postLogin");%>
	</logic:notPresent> 
}
window.onload=loadOther;
</script>