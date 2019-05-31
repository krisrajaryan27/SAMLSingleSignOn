<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.ErrorConstants"%>
<div class="contentDiv">
<logic:equal value="<%=ErrorConstants.ERR_FEEDBACK %>"  scope="request" name="errorCode">
	<table id="m_errortable">
	<tr>
	<td class="message">
	<br>
	<A href="#" onclick="viewApplicant(<bean:write property="applicantId" name="applicantData" scope="request"/>);"><bean:write property="applicantName" name="applicantData" scope="request"/></A> 
	<bean:message key="common.error.feedback_not_available"/>
	<br><br>
	</td>
	</tr>
	</table>
</logic:equal>
</div>