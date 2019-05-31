<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<html:html>
	<head>
	<script src="js/google/google_analytics.js" type="text/javascript"></script>
	<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
    <title> 
      <logic:notEmpty name="pageTitle" scope="request">
				<bean:write name="pageTitle" scope="request"/>
	 </logic:notEmpty> 
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
   	<meta http-equiv="Cache-Control" content="no-store,no-cache"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
    <link rel="shortcut icon" href="images/favicon.ico" />
    <link rel="stylesheet" type="text/css" href="themes/default/noColumnBody.css"/>
		<link rel="stylesheet" media="all" type="text/css" href="themes/default/default.css" />
	</head>
	<body onunload="return;">
		<table border="0" cellpadding="0" cellspacing="0" width="1002">
	    <tr>
	      <td align="left" valign="top"><tiles:insert attribute="mainPane"/></td>
	    </tr>
	  </table>
	  <script type="text/javascript">
		(function(){
			var csrf_token = '<%= request.getSession().getAttribute("org.apache.struts.action.TOKEN") %>';
			Ajax.Responders.register({
			onCreate: function(request){
				if(request.method === 'post') {
					var parameters = request.parameters;
					parameters.CSRF_TOKEN = csrf_token;
					request.options.postBody = Object.toQueryString(parameters);
				}
			}
		});
		})()
	</script>
	</body>
</html:html>