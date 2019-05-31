<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<% 
String emailsProcessed = (String)request.getAttribute("emailsProcessed");
String emailsAttached = (String)request.getAttribute("emailsAttached");
%>
<div class="contentDivPop" >
	<table cellpadding="0" cellspacing="0" class="boxETab">
	  <tr>
		  <td class="leftC"></td>
		  <td class="content">Message</td>
		  <td class="rightC"></td>
	  </tr>
  	</table>  
	<div class="outerDiv" style="padding:20px;">
		<html:form action="/desktop" >
			<html:hidden property="result" name="desktopSearchForm"/>
			<html:hidden property="applicantName" name="desktopSearchForm"/>
		</html:form>
		<logic:empty name="desktopSearchForm" property="result">
		Unable to attach selected <bean:message key="common.emails"/>
		</logic:empty>
		<logic:notEmpty name="desktopSearchForm" property="result">
		Successfully attached <%=emailsProcessed%> of <%=emailsAttached%> <bean:message key="common.emails"/> selected in <bean:message key="title.common"/>.
		<br/>
		Click 'Done' button at the bottom of the window.
		</logic:notEmpty>
	</div>
</div>