<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.common.NavigationConstants"%>
<bean:parameter id="showDataViewConfigScreen" name="showDataViewConfigScreen" value="false" />
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHOW_POSITION_SUMMARY_DASHBOARD">
	<bean:parameter id="showDataViewConfigScreen" name="showDataViewConfigScreen" value="true" />
</logic:equal>
<div style="margin-left:18px; margin-right:18px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
    <tr> 
      <td height="20">&nbsp;</td> 
    </tr> 
    <tr> 
      <td height="20"><strong class="Grey"><bean:message key="my_account.label.my_account"/></strong></td> 
    </tr> 
    <tr> 
    	<td style="line-height:18px;">
			<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_PROFILE%>">
				<span class="greenBullet">&raquo;</span> <a href="user.do?mode=accountSettings" class="green"><bean:message key="my_account.label.profile"/></a>
			</logic:notEqual>
			<logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_PROFILE%>">
				<span class="greenBullet">&raquo;</span> <bean:message key="my_account.label.profile"/>
			</logic:equal>
			<br />
		</td>
	</tr>
	<tr> 
    	<td style="line-height:18px;">
			<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_CONFIGURATIONS%>">
				<span class="greenBullet">&raquo;</span> <a href="user.do?mode=userConfiguration" class="green"><bean:message key="my_account.label.user_configurations"/></a>
			</logic:notEqual>
			<logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_CONFIGURATIONS%>">
				<span class="greenBullet">&raquo;</span> <bean:message key="my_account.label.user_configurations"/>
			</logic:equal>
			<br />
		</td>
	</tr>
	<logic:equal value="true" name="showDataViewConfigScreen">
		<tr> 
	    	<td style="line-height:18px;">
				<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_DATA_VIEW_CONFIGURATION%>">
					<span class="greenBullet">&raquo;</span> <a href="user.do?mode=userDataViewConfiguration" class="green"><bean:message key="my_account.label.user_dataViewConfiguration"/></a>
				</logic:notEqual>
				<logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_DATA_VIEW_CONFIGURATION%>">
					<span class="greenBullet">&raquo;</span> <bean:message key="my_account.label.user_dataViewConfiguration"/>
				</logic:equal>
				<br />
			</td>
		</tr>
	</logic:equal>	
	<tr> 
	    	<td style="line-height:18px;">
				<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_TIME_ZONE%>">
					<span class="greenBullet">&raquo;</span> <a href="user.do?mode=showTimeZones" class="green"><bean:message key="my_account.label.user_timezone"/></a>
				</logic:notEqual>
				<logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_TIME_ZONE%>">
					<span class="greenBullet">&raquo;</span> <bean:message key="my_account.label.user_timezone"/>
				</logic:equal>
				<br />
			</td>
		</tr>
	<% if(ModuleSet.isMODULE_SOCIAL_NETWORK()){ %>	
		<tr> 
	    	<td style="line-height:18px;">
				<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_SOCIAL_PROFILE%>">
					<span class="greenBullet">&raquo;</span> <a href="user.do?mode=socialProfile" class="green"><bean:message key="my_account.label.user_socialProfile"/></a>
				</logic:notEqual>
				<logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_USER_SOCIAL_PROFILE%>">
					<span class="greenBullet">&raquo;</span> <bean:message key="my_account.label.user_socialProfile"/>
				</logic:equal>
				<br />
			</td>
		</tr>
	<% } %>
  </table> 
</div>