<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<div class="contentDivPop" >
	<table cellpadding="0" cellspacing="0" class="boxETab">
	  <tr>
		  <td class="leftC"></td>
		  <td class="content"><bean:message key="common.message"/></td>
		  <td class="rightC"></td>
	  </tr>
  	</table>  
	<div class="outerDiv" style="padding:20px;">

		<html:form action="/desktop" >
			<html:hidden property="result" name="desktopSearchForm"/>
		</html:form>
		<logic:empty name="desktopSearchForm" property="result">
		Unable to save <bean:message key="common.bulk_import"/> parameters
		</logic:empty>
		<logic:notEmpty name="desktopSearchForm" property="result">
		<bean:message key="common.bulk_import"/> parameters Successfully saved
		</logic:notEmpty>
	</div>
</div>