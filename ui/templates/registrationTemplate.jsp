<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script src="js/google/google_analytics.js" type="text/javascript"></script>
	    <script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title><bean:write name="pageTitle" scope="request"/></title>
		<link rel="shortcut icon" href="images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="themes/default/default.css"/>
		<link rel="stylesheet" type="text/css" href="themes/default/dhtmlXGrid.css"/>
	</head>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	style="border-bottom:5px solid #99CC33;">
	<tr>
		<td>
		<table width="1002" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="218" height="60" align="center" valign="top"
					bgcolor="#FFFFFF"><a href="login.do?loginmode=login"><img src="images/logo.jpg"
					width="179" height="49" border="0"/></a></td>
				<td valign="bottom" bgcolor="#FFFFFF">
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
	  <table border="0" cellpadding="0" cellspacing="0" width="1002">
	    <tr>
	      <td align="left" valign="top"><tiles:insert attribute="mainPane"/></td>
	    </tr>
	  </table><script type="text/javascript">
		var csrf_token = '<%= request.getSession().getAttribute("org.apache.struts.action.TOKEN") %>';
			Ajax.Responders.register({
			onCreate: function(e){
			var options = e.options;
			if(options.method === 'post') {
				options.parameters += 'org.apache.struts.taglib.html.TOKEN=' + csrf_token;
				}
			}
		});
	</script>
	</body>
</html>
