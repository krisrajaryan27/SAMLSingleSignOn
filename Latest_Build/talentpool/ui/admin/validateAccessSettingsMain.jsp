<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.admin.AdminConstants"%>
<%@ page import="com.talentPool.admin.form.AdminForm"%>
<%@ page import="com.talentPool.admin.dataobject.PermissionData" %>
<%@ page import="com.talentPool.admin.dataobject.ReportLevelData" %>
<%@page import="org.apache.struts.Globals"%>
<script src="js/scripta/lib/prototype.js"></script>
<link rel="stylesheet" type="text/css"	href="themes/default/popupiframe.css">
<%
	String roleId = (String)request.getAttribute("roleId");
	String strPermissions = (String)request.getAttribute("strPermissions");
	String strLevelPermissions = (String)request.getAttribute("strLevelPermissions");
	String changePermissionIds = (String)request.getAttribute("changePermissionIds");
	String changePermissionIdValue = (String)request.getAttribute("changePermissionIdValue");
	String changedReportLevelIds = (String)request.getAttribute("changedReportLevelIds");
	String changedReportLevelValue = (String)request.getAttribute("changedReportLevelValue");
	String changeInUserPermissionAll = (String)request.getAttribute("changeInUserPermissionAll");
	String changeInReportLevelAll = (String)request.getAttribute("changeInReportLevelAll");
	
%>
<html:form action="/adminHome">
<html:hidden property="mode" value="saveAccessSettings"/>
<html:hidden property="strPermissions" name="adminForm"/>
<html:hidden property="strLevelPermissions" name="adminForm"/>
<html:hidden property="changePermissionIds" name="adminForm" value="<%=changePermissionIds%>"/>
<html:hidden property="changePermissionIdValue" name="adminForm" value="<%=changePermissionIdValue%>"/>
<html:hidden property="changedReportLevelIds" name="adminForm" value="<%=changedReportLevelIds%>"/>
<html:hidden property="changedReportLevelValue" name="adminForm" value="<%=changedReportLevelValue%>"/>
<html:hidden property="roleId" name="adminForm"/>

<div class="contentDivPop" style="width: 480px;">
	<%
	if (request.getAttribute(Globals.ERROR_KEY) != null) {
	%>
	<table id="m_errortable">
		<tr>
			<td class="header"><b><bean:message
				key="errors.following_errors" /></b></td>
		</tr>
		<tr>
			<td class="message"><html:errors /></td>
		</tr>
	</table>
	<br>
	<%
	}
	%>
 <div class="outerDiv" >
  <table  border="0" cellspacing="0" cellpadding="0" class="posinput" width="480px">
	<tr>
		<td class="boxHeader" style="font-weight:bold" width="100%">
			Following <bean:message key="common.position" /> <bean:message key="admin.manage_roles.label.Following_position_going_tobe_changed" />
		</td>
	</tr>
			
<%
	if(!changeInUserPermissionAll.equals("")){
		String [] changeInUserPermission=changeInUserPermissionAll.split(",");
		String chk = "";
		for(int i = 0 ; i<changeInUserPermission.length;i++ ){
			String [] singleRow = changeInUserPermission[i].split(":");
			if(!chk.equals(singleRow[0])){
		%>
			<tr>
				<td>
					<%=singleRow[1]%>
				</td>
			</tr>
			<%
				}
			%>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;
				<%
					if(singleRow[3].equals("1")){
				%>
					<img src="images/item_chk1_dis.gif" name='imgLevelPermission' id='<%=singleRow[2]%>'>
				<%
					}else{
				%>
					<img src="images/item_chk0_dis.gif" name='imgLevelPermission' id='<%=singleRow[2]%>'>
				<%
					}
				%>
					<%=singleRow[2]%>
				</td>
			</tr>
		<%
			chk =  singleRow[0];
		}
	}

		if(!changeInReportLevelAll.equals("")){
			String [] changeInReportLevel=changeInReportLevelAll.split(",");
			String chkLevel = "";
			for(int i = 0 ; i<changeInReportLevel.length;i++ ){
				String [] singleRow = changeInReportLevel[i].split(":");
				if(!chkLevel.equals(singleRow[0])){
		%>
				<tr>
					<td>
						<%=singleRow[1]%>
					</td>
				</tr>
				<%
					}
				%>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;
				<%
					if(singleRow[3].equals("1")){
				%>
					<img src="images/item_chk1_dis.gif" name='imgLevelPermission' id='<%=singleRow[2]%>'>
				<%
					}else{
				%>
					<img src="images/item_chk0_dis.gif" name='imgLevelPermission' id='<%=singleRow[2]%>'>
				<%
					}
				%>
					<%=singleRow[2]%>
					</td>
				</tr>
		<%
				chkLevel =  singleRow[0];
		}
	}
 %>	
</table>				
</div>
<br/>
		<div class="navBtn" style="float:left;margin-top:5px;"><a href="#" style="width:50px;" class="active" onclick="document.adminForm.submit()"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.ok"/></a>
		<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a></div>
<br/>
<br/>
<br/>
<br/>
<br/>
</div>
</html:form>
