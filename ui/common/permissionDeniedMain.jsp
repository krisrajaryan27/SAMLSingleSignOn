<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<div class="contentDivPop">
<div class="outerDiv" style="margin-top:50px;margin-left: 200px;">
	<div class="popupTop">
		<table class="tblPop" >
		<tr>
			<td class="header" style="color: red;">
			<bean:message key="permission_denied.title"/>
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody">
        	<bean:message key="common.permission_denied.view_resume"/> <bean:message key="common.position"/>.
	</div>
</div>
</div>