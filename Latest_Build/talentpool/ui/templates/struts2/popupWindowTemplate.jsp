<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<script src="js/google/google_analytics.js" type="text/javascript"></script>
	    <script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<meta http-equiv="Cache-Control" content="no-store,no-cache"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/>
		<title>
			<tiles:insertAttribute name="pageTitle" ignore="true" />
		</title>
		<link rel="shortcut icon" href="images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="themes/default/noColumnBody.css"/>
		<link rel="stylesheet" type="text/css" href="themes/default/default.css"/>
		<link rel="stylesheet" type="text/css" href="themes/default/dhtmlXGrid.css"/> 
		<link rel="stylesheet" type="text/css" href="themes/default/selectbox.css"/>	
		<link rel="stylesheet" type="text/css" href="themes/default/checkboxlist.css"/>	
		<link rel="stylesheet" type="text/css" href="themes/default/subModal.css"/>
		<link rel="stylesheet" type="text/css" href="themes/default/checkboxAndRadioGroup.css"/>
	</head>
	<body onunload="return;" >
	  <table border="0" cellpadding="0" cellspacing="0">
	    <tr>
	      <td align="left" valign="top"><tiles:insertAttribute name="mainPane" /></td>
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
</html>
