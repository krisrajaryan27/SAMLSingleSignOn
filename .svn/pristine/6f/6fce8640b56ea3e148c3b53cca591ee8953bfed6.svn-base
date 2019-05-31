<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.common.NavigationConstants"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<div style="margin-left:18px; margin-right:18px;">
	<table style="width: 100%; border: 0; padding: 0; border-spacing: 0;"> 
    <tr> 
      <td height="20">&nbsp;</td> 
    </tr> 
    <tr> 
      <td height="20"><strong class="Grey"><bean:message key="admin.label.admin"/></strong></td> 
    </tr>    
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_MANAGE_USERS">
    	<tr> 
      		<td style="line-height:18px;">
	      		<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_USERS%>">
		      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=manageUsers" class="green"><bean:message key="admin_manage_users"/></a>
		      	</logic:notEqual>
		      	<logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_USERS%>">
		      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_manage_users"/>
		      	</logic:equal>
		      	<br />
		  	</td>
		</tr>
	</logic:equal>
	 <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_MANAGE_USER_HIERARCHY">
    	<tr> 
      		<td style="line-height:18px;">
	      		<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_USERS_HIERARCHY%>">
		      	<span class="greenBullet">&raquo;</span> <a href="hierarchyHome.do?mode=manageUserHierarchy" class="green"><bean:message key="admin_manage_user_hierarchy"/></a>
		      	</logic:notEqual>
		      	<logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_USERS_HIERARCHY%>">
		      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_manage_user_hierarchy"/>
		      	</logic:equal>
		      	<br />
		  	</td>
		</tr>
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_MANAGE_ROLES">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_ROLE_ACCESS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=manageRoleAccess" class="green">Manage Role Access</a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_ROLE_ACCESS%>">
	      	<span class="greenBullet">&raquo;</span> Manage Role Access
	      </logic:equal>
		  </td> 
    	</tr> 
    	<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_REPORT_LEVEL_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=reportLevelSettings" class="green"><bean:message key="report_level.label.report_level_setting"/></a>	      	
	      	</logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MANAGE_REPORT_LEVEL_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="report_level.label.report_level_setting"/>
	      </logic:equal>
		  </td> 
    	</tr>
	</logic:equal>
</table>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
<div style="margin-left:18px; margin-right:18px;"> 
<br/>
<table style="width: 100%; border: 0; padding: 0; border-spacing: 0;">
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_APPLICATION_SETTINGS">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_APPLICATION_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=applicationSettings" class="green"><bean:message key="admin_application_settings.label.application_settings" /></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_APPLICATION_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_application_settings.label.application_settings" />
	      </logic:equal>
		  </td> 
	   </tr> 
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_INBOX_SETTINGS">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_ACCOUNT_SETTINGS%>">
		      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=inboxSettingsMaster" class="green"><bean:message key="admin_inbox_settings.label.inbox_settings"/></a>	      	
		      </logic:notEqual>
		      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_ACCOUNT_SETTINGS%>">
		      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_inbox_settings.label.inbox_settings"/>
		      </logic:equal>
		  </td> 
	    </tr> 
	</logic:equal>
    <% if(ModuleSet.isMODULE_SOCIAL_NETWORK()) { %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SOCIAL_MEDIA_ACCESS">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SOCIAL_NETWORK%>">
		      	<span class="greenBullet">&raquo;</span> <a href="socialSettings.action" class="green"><bean:message key="admin.social_network.label.social_settings"/></a>	      	
		      </logic:notEqual>
		      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SOCIAL_NETWORK%>">
		      	<span class="greenBullet">&raquo;</span> <bean:message key="admin.social_network.label.social_settings"/>
		      </logic:equal>
		  </td> 
	    </tr> 
	</logic:equal>
	<% } %>
    <%if(ModuleSet.isMODULE_SMS()){ %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SMS_SETTINGS">
	    <tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SMS_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=smsSettings" class="green"><bean:message key="admin.sms_settings.label.title.sms_settings"/></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SMS_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="admin.sms_settings.label.title.sms_settings"/>
	      </logic:equal>
		  </td> 
    	</tr>
	</logic:equal>
   	<%} %>
   	<%if(ModuleSet.isMODULE_LDAP()){ %>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_LDAP_SETTINGS">
    	<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_LDAP_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="ldapHome.do?mode=ldapSettings" class="green">LDAP Settings</a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_LDAP_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> LDAP Settings
	      </logic:equal>
		  </td> 
    	</tr>
	</logic:equal>
   	<%} %>
   	 <tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_TIME_ZONE_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=timeZoneSettings" class="green"><bean:message key="admin.time_zone_settings.label.title.time_zone_settings"/></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_TIME_ZONE_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="admin.time_zone_settings.label.title.time_zone_settings"/>
	      </logic:equal>
		  </td> 
    	</tr>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DUPLICATE_DETECTION_SETTINGS">
    	<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_DUPLICATE_DETECTION_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=duplicateDetectionSettings" class="green"><bean:message key="duplicate_detection_settings.label.duplicate_settings"/></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_DUPLICATE_DETECTION_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="duplicate_detection_settings.label.duplicate_settings"/>
	      </logic:equal>
		  </td> 
    	</tr>     	
	</logic:equal>
	
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DUPLICATE_DETECTION_SETTINGS">
    	<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_DUPLICATE_POSITION_DETECTION_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=duplicatePositionDetectionSettings" class="green"><bean:message key="duplicate_position_detection_settings.label.duplicate_settings"/></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_DUPLICATE_POSITION_DETECTION_SETTINGS%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="duplicate_position_detection_settings.label.duplicate_settings"/>
	      </logic:equal>
		  </td> 
    	</tr>     	
	</logic:equal>
   	
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_LABEL_MESSAGES">
	<tr>
	  <td style="line-height:18px;">
	  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MESSAGES%>">
      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=manageMessages" class="green">Messages</a>	      	
      </logic:notEqual>
      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_MESSAGES%>">
      	<span class="greenBullet">&raquo;</span> Messages
      </logic:equal>
	  </td> 
	</tr>
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CUSTOM_FIELDS">
	<tr>
	  <td style="line-height:18px;">
	  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_CUSTOM_FIELDS%>">
      	<span class="greenBullet">&raquo;</span> <a href="customFieldScreen.do?mode=manageCustomFields" class="green">Custom Fields</a>	      	
      </logic:notEqual>
      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_CUSTOM_FIELDS%>">
      	<span class="greenBullet">&raquo;</span> Custom Fields
      </logic:equal>
	  </td> 
	</tr>
	</logic:equal>
	<tr>
	  <td style="line-height:18px;">
	  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SALARY_COMPONENTS%>">
      	<span class="greenBullet">&raquo;</span> <a href="salaryStructure.do?mode=manageSalaryComponents" class="green"><bean:message key="admin_salary_components_label" /></a>	      	
      </logic:notEqual>
      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SALARY_COMPONENTS%>">
      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_salary_components_label" />
      </logic:equal>
	  </td> 
	</tr>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SUMMARY_REPORT_SCHEDULER">
	<tr>
	  <td style="line-height:18px;">
	  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_REPORT_SCHEDULER%>">
      	<span class="greenBullet">&raquo;</span> <a href="schedulerStatus.action" class="green"><bean:message key="admin.summary_report_scheduler.title" /></a>	      	
      </logic:notEqual>
      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_REPORT_SCHEDULER%>">
      	<span class="greenBullet">&raquo;</span> <bean:message key="admin.summary_report_scheduler.title" />
      </logic:equal>
	  </td> 
	</tr>
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SUMMARY_REPORT_SCHEDULER">
	<tr>
	  <td style="line-height:18px;">
	  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_REPORT_SCHEDULER%>">
      	<span class="greenBullet">&raquo;</span> <a href="positionStatus.action" class="green"><bean:message key="admin.position_scheduler.status" /></a>	      	
      </logic:notEqual>
      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_REPORT_SCHEDULER%>">
      	<span class="greenBullet">&raquo;</span> <bean:message key="admin.position_scheduler.status" />
      </logic:equal>
	  </td> 
	</tr>
	</logic:equal>
	<tr>
	  <td style="line-height:18px;">
      <span class="greenBullet">&raquo;</span> <a href="userSchedulerStatus.action" class="green"><bean:message key="admin.user_scheduler.title" /></a>
	  </td> 
	</tr>
</table>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
<div style="margin-left:18px; margin-right:18px;"> 
<br/>
<table style="width: 100%; border: 0; padding: 0; border-spacing: 0;">
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCREEN_CONFIGURATION">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SCREEN_CONFIGURATION%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=manageScreenConfiguration" class="green"><bean:message key="admin_screen_configuration.label.applicant_screen_configuration" /></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_SCREEN_CONFIGURATION%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_screen_configuration.label.applicant_screen_configuration" />
	      </logic:equal>
		  </td> 
    	</tr>   			
	</logic:equal>
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_POSITIONS_SCREEN_CONFIGURATION%>">
	      		<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=managePositionScreen" class="green"><bean:message key="common.position" />&nbsp;<bean:message key="admin_screen_configuration.label.position_screen_configuration" /></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_POSITIONS_SCREEN_CONFIGURATION%>">
	      		<span class="greenBullet">&raquo;</span> <bean:message key="common.position" />&nbsp;<bean:message key="admin_screen_configuration.label.position_screen_configuration" />
	      </logic:equal>
		  </td> 
    	</tr>   			
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_PUBLISH_POSITION_TO_WEB_SITE">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_POSITION_SCREEN_CONFIGURATION%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=managePositionScreenConfiguration" class="green"><bean:message key="admin_position_screen_configuration_on_website.label.screen_configuration" /></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_POSITION_SCREEN_CONFIGURATION%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_position_screen_configuration_on_website.label.screen_configuration" />
	      </logic:equal>
		  </td> 
    	</tr>   			
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_PUBLISH_POSITION_TO_WEB_SITE">
		<tr>
		  <td style="line-height:18px;">
		  	<logic:notEqual name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_RESET_WEBSITE_CACHE%>">
	      	<span class="greenBullet">&raquo;</span> <a href="adminHome.do?mode=resetWebsiteCache" class="green"><bean:message key="admin_position_screen_configuration_on_website.label.candidate_portal_reset_cache" /></a>	      	
	      </logic:notEqual>
	      <logic:equal name="mainPane" scope="request" value="<%=NavigationConstants.MAINPANE_ADMIN_RESET_WEBSITE_CACHE%>">
	      	<span class="greenBullet">&raquo;</span> <bean:message key="admin_position_screen_configuration_on_website.label.candidate_portal_reset_cache" />
	      </logic:equal>
		  </td> 
    	</tr>   			
	</logic:equal>
  </table> 
</div>