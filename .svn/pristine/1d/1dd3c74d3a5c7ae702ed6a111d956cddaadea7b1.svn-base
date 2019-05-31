<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<html>
<head>
<link rel="STYLESHEET" type="text/css" href="themes/default/inboxEmailBody.css">
</head>
<body>
<logic:present name="messageData" scope="request">
<logic:empty name="messageData" property="htmlBody" scope="request">
<PRE>
<bean:write name="messageData" property="textBody" scope="request"/>
</PRE>
</logic:empty>
<logic:notEmpty name="messageData" property="htmlBody" scope="request">
<bean:write name="messageData" property="htmlBody" scope="request" filter="false"/>
</logic:notEmpty>
</logic:present>
<logic:notPresent  name="messageData" scope="request"><b>
<bean:message key="inbox.error.email_doest_not_exist"/></b>
</logic:notPresent>
</body>
</html>
