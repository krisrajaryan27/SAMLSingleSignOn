<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.common.NavigationConstants"%>
<div style="margin-left:18px; margin-right:18px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td height="20">&nbsp;</td> 
    </tr> 
    <tr> 
      <td height="20"><strong class="Grey"><bean:message key="costs.label.title" /></strong></td> 
    </tr> 
    <tr> 
      <td style="line-height:18px;">
      	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_COSTS_HOME%>">
	      	<span class="greenBullet">&raquo;</span> <a href="costs.do?mode=costsHome" class="green">
	      	<bean:message key="costs.label.expenses" />
	      	</a>
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_COSTS_HOME%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="costs.label.expenses" />
	      </logic:equal>
	      <br />
		  </td>
		</tr>
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_ADD_EXPENSE_TYPE">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_COSTS_TYPE%>">
	      	<span class="greenBullet">&raquo;</span> <a href="costType.do?mode=costTypes" class="green">
	      	<bean:message key="costs.label.expenses_types" /></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_COSTS_TYPE%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="costs.label.expenses_types" />
	      </logic:equal>
		  </td> 
	   </tr> 
	   </logic:equal>
  </table> 
</div>