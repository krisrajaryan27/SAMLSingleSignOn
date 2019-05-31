<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<html:form action="/adminHome">
		<html:hidden property="submitFlag" name="adminForm" />
			<html:hidden property="mode" value="resetWebsiteCache"/>
		<p>
		<table>
			<tr>
				<td><logic:equal name="taskComplete" scope="request" value="1"> Success</logic:equal>
					<logic:equal name="taskComplete" scope="request" value="0"> Fail</logic:equal>
				</td>
			</tr>
			<tr>
				<td><a href="#" onclick="javascript: resetWebsiteCache();">Reset
						Website Cache</a></td>
			</tr>
			<tr>
				<td><a href="#"
					onclick="javascript: resetPositionFieldMapCache();">reset
						Position Field Map Cache</a></td>
			</tr>
		</table>

		</p>
	</html:form>
</body>
<script type="text/javascript">
	function doonload() {

	}

	function resetWebsiteCache() {
		document.adminForm.submitFlag.value = "1";
		document.forms[0].submit();
	}
	function resetPositionFieldMapCache() {
		document.adminForm.submitFlag.value = "1";
		document.forms[0].mode.value="resetPositionFieldMapCache";
		document.forms[0].submit();
	}

	window.onload = doonload;
</script>
</html>