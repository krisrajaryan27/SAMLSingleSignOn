<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="org.apache.struts.Globals"%>
<% if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
<br class="br100"/>
<table align="center" cellpadding="0" cellspacing="0" border="0"> 
    <tr>
        <td>
			<div class="contentDivPop">
			<div class="outerDiv" >
				<div class="popupTop">
					<table class="tblPop" >
					<tr>
						<td class="header" style="color: red;">
						<bean:message key="common.error.error_occured"/>
						</td>
					</tr>
					</table>
				</div>
				<div class="popupBody">
			        	<html:errors/>
				</div>
			</div>
			</div>        
        </td>               
    </tr>
</table><br>
<%
}
%>

