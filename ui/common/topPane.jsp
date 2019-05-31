<%@page import="com.talentPool.socialNetwork.constants.SocialMediaConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page
	import="java.util.BitSet,com.talentPool.positions.PositionConstants,com.talentPool.socialNetwork.manager.SocialMediaManager"%>
<%
	String tab = (String) request.getAttribute("t");
%>
<%@page import="com.talentPool.common.NavigationConstants"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.budget.utils.BudgetUtils"%>
<%@page import="com.talentPool.common.CommonConstants"%>
<script src="encryption/js/jquery-2.0.3.min.js" type="text/javascript"></script>
<script src="encryption/js/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/pluginMenu.css">	
<table style="width: 100%; border: 0; padding: 0; border-spacing: 0;">
	<tr>
		<td width="218" height="18" bgcolor="#FFFFFF">&nbsp;</td>
		<td align="center" valign="bottom" bgcolor="#FFFFFF">
			<div class="TopNavR" style="float:right; margin-right:15px;">
				<% if (NavigationConstants.T_MYACCOUNT.equals(tab)){%> 
				<strong	class="Grey"> <bean:message	key="my_account.label.my_account" /> </strong> 
				<% } else { %> 
				<a href="user.do?mode=accountSettings"> <bean:message key="my_account.label.my_account" /> </a> 
				<% } %>
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_ADMIN">
				|
				<% if (NavigationConstants.T_ADMIN.equals(tab)) { %>
				<strong class="Grey"> <bean:message key="admin.label.admin" />
				</strong>
				<% } else {	%>
				<a href="adminHome.do?mode=admin"> <bean:message key="admin.label.admin" /> </a>
				<% } %>
				</logic:equal>
				|  
				<a href="#" onmouseover="javascript: showHelpMenu();"  id="help">
				<bean:message key="help.label.help" /> </a>
				|
				<a href="login.do?loginmode=logout"><bean:message key="header.label.log_off" /> </a>
			</div>
			<strong class="Grey"><bean:message key="header.label.welcome" />
			<bean:write name="userFirstName" scope="session" /></strong> 		
		</td>
	
		<td>
			<div class="TopNavR" id="helpMenu" style="position:absolute;width:85px;right:62px;background-color:#FFFFFF;display:none;margin-top: 10px;"  onmouseover="javascript: showHelpMenu();" onmouseout="javascript: hideHelpMenu();">
				<table style="border-color:#D0E4A3;border-style:solid;border-width:1px;cursor:pointer;font-size: 10; width: 100%;" class="tblTpMnu">
					<tr><td>
						<a href="application.do?mode=getHelp" style="width:100%;"> 
						<bean:message key="help.label.helpDoc" /></a>
					</td></tr>
					<tr><td>
						<a href="application.do?mode=getHelpMobile" style="width:100%;"> 
						<bean:message key="mobile.help.label.helpDoc" /></a>
					</td></tr>
					<tr><td >
						<a href="#" onmouseover="javascript: showPluginMenu();" onmouseout="javascript: hidePluginMenu();" > 
						<bean:message key="help.label.plugins" />&nbsp;&nbsp;&nbsp;</a>
					</td></tr>
					<tr><td>
						<a href="#" onclick="about();"> 
						<bean:message key="help.label.about" /></a>
					</td></tr>
				</table>
			</div>
			<div class="TopNavR" id="pluginMenu" style="position:absolute;width:75px;right:30px;background-color:#FFFFFF;display:none;margin-top: 38px;"  onmouseover="javascript: showPluginMenu();" onmouseout="javascript: hidePluginMenu();">
				<table style="border-color:#D0E4A3;border-style:solid;border-width:1px;cursor:pointer;font-size: 10; width: 100%;" class="tblTpMnu">
					<tr><td>
						<a href="application.do?mode=downloadPlugin&pluginId=<%=CommonConstants.IE_PLUGIN %>"  style="width:100%;"> 
						<bean:message key="plugin.label.ieplugin" /></a>
					</td></tr>
					<tr><td >
						<a href="application.do?mode=downloadPlugin&pluginId=<%=CommonConstants.FIREFOX_PLUGIN %>" > 
						<bean:message key="plugin.label.firefoxplugin" /></a>
					</td></tr>
					<tr><td>
						<a href="application.do?mode=downloadPlugin&pluginId=<%=CommonConstants.CHROME_PLUGIN %>" > 
						<bean:message key="plugin.label.chromePlugin" /></a>
					</td></tr>
					<tr><td>
						<a href="application.do?mode=downloadPlugin&pluginId=<%=CommonConstants.OUTLOOK_PLUGIN %>" > 
						<bean:message key="plugin.label.outlookplugin" /></a>
					</td></tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<table style="border-bottom:5px solid #99CC33; width: 100%; padding: 0; border-spacing: 0;">
	<tr>
		<td>
		<table style="width: 832px; border: 0; padding: 0; border-spacing: 0;">
			<tr>
				<td width="218" height="60" align="center" valign="top"	bgcolor="#FFFFFF">
				<a href="dashboard.do?mode=dashboard">
				<img src="images/logo_talentpool.gif" width="175" height="50" border="0" /></a>
				</td>
				<td valign="bottom" bgcolor="#FFFFFF">
				<a href="#" style="float:left;display:none;" onclick="JavaScript:TOGGLE_MENU(0);return false;" id="btn_left">
				<img src="images/btn_arrowleft.gif" width="29" height="22" border="0" style="margin-top:4px;vertical-align:bottom;">
				</a>
				<div class="TopNav">
					<a	<% if (NavigationConstants.T_DASHBOARD.equals(tab)){%>	class="active" <% } else {%> href="dashboard.do?mode=dashboard"	<%} %> style="display:block;" id="m_1">
					<span class="rightC"></span><span class="leftC">
					</span>
					<bean:message key="dashboard.label.title_dashboard"/>
					</a>
					<logic:equal value="true"
					name="permissionSet" scope="session"
					property="PERMISSION_POSITIONS">
					<a <% if (NavigationConstants.T_POSITIONS.equals(tab)){%>
						class="active" <%} %>
						href="position.do?mode=positionsHome&showCondition=<%=PositionConstants.POSITION_STATUS_OPENED%>"
						 style="display:none;" id="m_2"><span class="rightC"></span><span
						class="leftC"></span>
						<bean:message key="common.positions"/>
						</a>
					</logic:equal> 
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_IMPORT">
					<a <% if (NavigationConstants.T_IMPORT.equals(tab)){%> class="active" <% } else {%> href="inbox.do?mode=inbox" <%} %>
						style="display:block;" id="m_3"><span class="rightC"></span>
						<span class="leftC"></span>
						<bean:message key="import.label.title_import"/>						
						</a>
					</logic:equal>
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCREEN">
						<a <% if (NavigationConstants.T_SCREEN.equals(tab)){%> class="active" <% } else {%> href="doSearch.do?mode=search" <%} %> style="display:block;" id="m_4"><span class="rightC"></span><span
						class="leftC"></span>
						<bean:message key="search_applicant.label.title_screen"/>				
						</a>
					</logic:equal>
				 	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SELECT">
						<a <% if (NavigationConstants.T_SELECT.equals(tab)){%>
						class="active" <% } else {%>
						href="selectionProcess.do?mode=select" <%} %>
						style="display:block;" id="m_5"><span class="rightC"></span><span
						class="leftC"></span>
						<bean:message key="select.label.title_select"/>
						</a>
					</logic:equal>
					<logic:equal value="true" name="permissionSet" scope="session"
						property="PERMISSION_HIRE">
						<a <% if (NavigationConstants.T_HIRE.equals(tab)){%> class="active"
						<% } else {%> href="selectionProcess.do?mode=accept" <%} %>
						style="display:block;" id="m_6"><span class="rightC"></span><span
						class="leftC"></span>
						<bean:message key="hire.label.title_hire"/>
						</a>
					</logic:equal>
				 	<logic:equal value="true" name="permissionSet" scope="session"
					property="PERMISSION_REPORTS">
					<a <% if (NavigationConstants.T_REPORT.equals(tab)){%>
						class="active"
						 <%} %>
						href="reports.do?mode=reportFilter&reportName="
						style="display:block;" id="m_7"><span class="rightC"></span><span
						class="leftC"></span>
						<bean:message key="report.label.title_report"/>
						</a>
					</logic:equal> 
					<a <% if (NavigationConstants.T_CALENDAR.equals(tab)){%>
					class="active" <% } else {%> href="calendar.do?mode=calendarHome"
					<%} %> style="display:none;" id="m_8"><span class="rightC"></span><span
					class="leftC"></span>
					<bean:message key="calendar.label.title_calendar"/>
					</a> 
					<logic:equal value="true" name="permissionSet" scope="session"
					property="PERMISSION_MASTERS">
					<a <% if (NavigationConstants.T_MASTERS.equals(tab)){%>
						class="active" <% } else {%> href="masters.do?mode=masters" <%} %>
						style="display:none;width:105px;" id="m_9"><span
						class="rightC"></span><span class="leftC"></span>
						<bean:message key="master_lists.label.title_master_list"/>
						</a>
					</logic:equal> 
					<% if (ModuleSet.isMODULE_COSTS()) { %> 
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_EXPENSES">
					<a <% if (NavigationConstants.T_COSTS.equals(tab)){%>
						class="active" <% } else {%> href="costs.do?mode=costsHome" <%} %>
						style="display:none;" id="m_10"><span class="rightC"></span>
						<span class="leftC"></span> <bean:message key="costs.label.title" /> </a>
					</logic:equal> 
					<% } %>					
					 <% if (ModuleSet.isMODULE_BUDGET()) { %>			
					 <% if (BudgetUtils.isBudgetModuleActive()) { %>	
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUDGETS">						
					<a <% if (NavigationConstants.T_BUDGETS.equals(tab)){%>
							class="active" 
						<% } else {%> 
							href="budgets.do?mode=budgetHome" 
						<%} %>
						 id="m_11"><span class="rightC"></span>
						<span class="leftC"></span> <bean:message key="budget.label.title" /> </a>
					</logic:equal> 
					<% } %>					
					<% } %>					
				</div>
				<a href="#" style="float:left;display:block;"
					onclick="TOGGLE_MENU(1);return false;" id="btn_right"><img
					src="images/btn_arrowright.gif" width="29" height="22" border="0"
					style="margin-top:4px;"></a></td>
			</tr>
		</table>
		</td>
		
		<%if(ModuleSet.isMODULE_SOCIAL_NETWORK() && !SocialMediaManager.checkTokenAndInformationSharedForUser((String)request.getSession().getAttribute("userId"), SocialMediaConstants.GRAPH_UPLOAD_COMPLETE)
				&& !request.getRequestURL().toString().contains("socialProfile")) { %>
			<td id="social_auth" >
				<div id='profile_share' style='display:none;left:40%;background: none repeat scroll 0 0 #D0E4A3;border: 1px solid #99CC33;border-radius: 8px;padding: 15px;'>
					<a style='font-weight:bold;text-decoration:none;color:#666666' href="user.do?mode=socialProfile"><bean:message key="dashboard.label.title_social_info"/></a>
				</div>
			</td>
		<% } %>	
	</tr>
</table>

<script src="js/topMenu.js"></script>
<script type="text/javascript">
<% 
if ( NavigationConstants.T_CALENDAR.equals(tab)|| NavigationConstants.T_REPORT.equals(tab) || NavigationConstants.T_MASTERS.equals(tab) || NavigationConstants.T_COSTS.equals(tab)|| NavigationConstants.T_BUDGETS.equals(tab)){
%>
TOGGLE_MENU(1);
<% 
 }else{
%>
TOGGLE_MENU(0);
<% } %>
if(top_menus.length<=6){
	setArrows("none","none" );
}

function showHelpMenu(){
	document.getElementById("helpMenu").style.display="block";
}

function hideHelpMenu(){
	document.getElementById("helpMenu").style.display="none";
}

function showPluginMenu(){
	document.getElementById("pluginMenu").style.display="block";
	document.getElementById("helpMenu").style.display="block";
}

function hidePluginMenu(){
	document.getElementById("pluginMenu").style.display="none";
	document.getElementById("helpMenu").style.display="none";
}

function about(){
	var url="application.do?mode=showAbout";
	showInPopUp(url,400,300,'',true);
}

window.onload = doOnLoad();

function doOnLoad() {
	$("#social_auth").fadeOut(8000);
	$("#profile_share").slideDown(500);
}

</script>