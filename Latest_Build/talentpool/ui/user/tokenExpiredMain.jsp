<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals, com.talentPool.common.NavigationConstants"%>

<%@page import="java.util.ArrayList"%>
<%@ page import="com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.common.utils.Utils"%>
<%@ page import="java.security.KeyPair"%>
<%@ page import="com.talentPool.encryption.JCryptionUtil"%>
<script src="encryption/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="encryption/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/encrypt.js" type="text/javascript"></script>
<script src="encryption/js/jquery.jcryption-1.1.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/selectbox.css">

<%
ArrayList<String> securityQuestionIds = CommonUtils.getSecurityQuestionIds();
ArrayList<String> securityQuestionNames = CommonUtils.getSecurityQuestionNames();

%>
<script type="text/javascript">
var selectBoxSecurityQuestion=null;
var securityQuestionOptions = <%=CommonUtils.getListJavaScriptArray(securityQuestionIds,securityQuestionNames)%>;
</script>

<logic:present name="update" scope="request">
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present>                

<div class="contentDivPop" style="width: 450px;">

<logic:present name="tokenExpired" scope="request">
			<table id="m_errortable" > 
		    <tr>
	        <td class="message" style="padding: 20px;"><bean:message key="forgot_password.error.invalid_link"/></td>               
		    </tr>
			</table><br/><br/>
			<div class="navBtn" style="margin-top:5px;margin-left: 0px;">
			<a href="login.do?loginmode=login" style="width:60px; margin-left: 5px;" class="active" >
								<span class="rightC"></span><span class="leftC"></span>
								<bean:message key="common.ok"/>
			</a>
			</div>
	
</logic:present>
<script>
		function setPopupTitle(){
			var title = '<b>' + 'Change Password' + '</b>';
			
			window.top.setPopTitle(title);
		}
		
		window.onload = setPopupTitle;
		
	</script>
             

