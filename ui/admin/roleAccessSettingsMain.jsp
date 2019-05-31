<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.admin.AdminConstants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.admin.form.AdminForm"%>
<%@ page import="com.talentPool.admin.dataobject.PermissionData" %>
<%@ page import="com.talentPool.admin.dataobject.ReportLevelData" %>
<script src="js/scripta/lib/prototype.js"></script>
<link rel="stylesheet" type="text/css"	href="themes/default/popupiframe.css">
<%
	String roleId = (String)request.getAttribute("roleId");
	String roleTitle = (String)request.getParameter("roleTitle");
	String userId = (String)request.getAttribute("userId");
	String userName = (String)request.getParameter("userName");
%>
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

<div class="outerDiv" style="width: 480px;">
<html:form action="/adminHome" >
<html:hidden property="mode" value="validateAccessSettings"/>
<html:hidden property="t" name="adminForm"/>
<html:hidden property="strPermissions" name="adminForm"/>
<html:hidden property="strLevelPermissions" name="adminForm"/>
<html:hidden property="roleId" name="adminForm"/>
<html:hidden property="roleTitle" name="adminForm"/>
<html:hidden property="userId" name="adminForm"/>
<script language="JavaScript">
	var permissionIds = new Array();
</script>
	<logic:iterate id="permission" name="permissions" scope="request">
			<script language="JavaScript">
					permissionIds[permissionIds.length] ='<bean:write name="permission" property="permissionId" />'+"_"+'<bean:write name="permission" property="parentId" />'+"_"+'<bean:write name="permission" property="permissionDesc" />';
			</script>
			<logic:equal name="permission" property="permissionId" value="1">	
				<table cellspacing=0 cellpadding=0 border=0 class="boxHeader" width="100%">
					<tr>
						<td style="font-weight:bold;padding:2px;">
							<bean:message key="admin.manage_roles.label.app_level_permissions"/>
						</td>
					</tr>
				</table>		
				<table cellspacing=0 cellpadding=0 border=0>
					<tr>
						<td style="font-weight:bold;padding:10px 0px 0px 20px;">
							<bean:message key="admin.manage_roles.label.data_access_permissions"/>
						</td>
					</tr>
				</table>		
			</logic:equal>
			<logic:equal name="permission" property="permissionId" value="3">
				<table cellspacing=0 cellpadding=0 border=0>
					<tr>
						<td style="font-weight:bold;padding:10px 0px 0px 20px;">
							<bean:message key="admin.manage_roles.label.confidential_data_permission"/>
						</td>
					</tr>
				</table>		
			</logic:equal>
			<logic:equal name="permission" property="permissionId" value="5">
				<table cellspacing=0 cellpadding=0 border=0>
					<tr>
						<td style="font-weight:bold;padding:10px 0px 0px 20px;">
							<bean:message key="admin.manage_roles.label.confidential_profile_permission"/>
						</td>
					</tr>
				</table>		
			</logic:equal>
			<logic:equal name="permission" property="permissionId" value="7">
				<table cellspacing=0 cellpadding=0 border=0>
					<tr>
						<td style="font-weight:bold;padding:10px 0px 0px 20px;">
							<bean:message key="admin.manage_roles.label.budget_ownership_permission"/>
						</td>
					</tr>
				</table>		
			</logic:equal>
			<logic:lessThan name="permission" property="permissionId" value="10">
				<table cellspacing=0 cellpadding=0 border=0>
					<tr>
						<td style="padding:5px 2px 0px 40px;">
							<logic:equal name="permission" property="permissionValue" value="0">
							<img src="images/radiobutton.gif" name='imgPermission' id='<bean:write name="permission" property="permissionId" />' onclick="javascript:onRadioChange(this);" style="margin-bottom:-1px;"/>
							</logic:equal>
							<logic:equal name="permission" property="permissionValue" value="1">
							<img src="images/checkedradiobutton.gif" name='imgPermission' id='<bean:write name="permission" property="permissionId" />' onclick="javascript:onRadioChange(this);" style="margin-bottom:-1px;"/>
							</logic:equal>
						</td>
						<td style="padding:5px 0px 0px 0px;">
							<bean:write name="permission" property="permissionDesc" />
						</td>
					</tr>
				</table>		
			</logic:lessThan>
			
			<logic:equal name="permission" property="permissionId" value="10">
				<br/>
				<table cellspacing=0 cellpadding=0 border=0 class="boxHeader" width="100%">
					<tr>
						<td style="font-weight:bold;padding:2px;">
							<bean:message key="admin.manage_roles.label.modules_level_permissions"/>
						</td>
					</tr>
				</table>		
			</logic:equal>
			
			<logic:greaterEqual name="permission" property="permissionId" value="10">			
				<logic:equal name="permission" property="parentId" value="0">
					<table cellspacing=0 cellpadding=0 border=0>
						<tr>
							<td style="padding:5px 2px 0px 20px;">
								<logic:equal name="permission" property="isPermission" value="1">
									<logic:equal name="permission" property="permissionValue" value="0">
										<img src="images/checkboxunchecked.gif" name='imgPermission' id='<bean:write name="permission" property="permissionId" />' onclick="javascript: checkAllChilds(this);"/>
									</logic:equal>
									<logic:equal name="permission" property="permissionValue" value="1">
										<img src="images/checkboxchecked.gif" name='imgPermission' id='<bean:write name="permission" property="permissionId" />' onclick="javascript: checkAllChilds(this);"/>
									</logic:equal>
								</logic:equal>
							</td>
							<td style="padding:5px 0px 0px 0px;">
								<bean:write name="permission" property="permissionDesc" />
							</td>
						</tr>
					</table>		
				</logic:equal>				
				
				<logic:notEqual name="permission" property="parentId" value="0">
					<table cellspacing=0 cellpadding=0 border=0>
						<tr>
							<td style="padding:5px 2px 0px 40px;">
								<logic:equal name="permission" property="permissionValue" value="0">
									<img src="images/checkboxunchecked.gif" name='imgPermission' id='<bean:write name="permission" property="permissionId" />' onclick="javascript: toggleChkBox(this);"/>
								</logic:equal>
								<logic:equal name="permission" property="permissionValue" value="1">
									<img src="images/checkboxchecked.gif" name='imgPermission' id='<bean:write name="permission" property="permissionId" />' onclick="javascript: toggleChkBox(this);"/>
								</logic:equal>
							</td>
							<td style="padding:5px 0px 0px 0px;">
								<bean:write name="permission" property="permissionDesc" />
							</td>
						</tr>
					</table>		
				</logic:notEqual>
				
				<logic:equal name="permission" property="permissionDesc" value="Reports">					
					<logic:iterate id="levels" name="reportLevel" scope="request">
					<table cellspacing=0 cellpadding=0 border=0>
						<tr>
							<td style="padding:5px 2px 0px 40px;">
								<logic:equal name="levels" property="levelIsSelected" value="1">
									<img src="images/checkboxchecked.gif" name='imgLevelPermission' id='<bean:write name="levels" property="levelId" />' onclick="javascript: toggleChkBox(this);">
								</logic:equal>
								<logic:equal name="levels" property="levelIsSelected" value="0">
									<img src="images/checkboxunchecked.gif" name='imgLevelPermission' id='<bean:write name="levels" property="levelId" />' onclick="javascript: toggleChkBox(this);">
								</logic:equal>
							</td>
							<td style="padding:5px 0px 0px 0px;">
								<bean:write name="levels" property="levelName" />
							</td>
						</tr>
					</table>		
					</logic:iterate>
				</logic:equal>

			</logic:greaterEqual>
	</logic:iterate>
</html:form>
<br/>
</div>		
<br/>
	<div class="navBtn" style="float:left;margin-top:5px;"><a href="#" style="width:50px;" class="active" onclick="javascript:submitFrom();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
	<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a></div>
<br/>
<br/>
<br/>
<br/>
<br/>
</div>		

<script language="javascript">

var chkedChkBox = 'images/checkboxchecked.gif';
var unChkedChkBox = 'images/checkboxunchecked.gif';
var chkedRadioButton='images/radiobutton.gif';
var unchkedRadioButton='images/checkedradiobutton.gif';

function toggleChkBox(obj) {
	var source = obj.src;
	var objectId = obj.id;
	if (source.indexOf(chkedChkBox) != -1) {
		obj.src = unChkedChkBox;		
	} else {
		obj.src = chkedChkBox;

		for (var i = 0; i < permissionIds.length; i++) {
			var parts = permissionIds[i].split('_');			
			if(objectId == parts[0] && document.getElementById(parts[1]) != null){							
				document.getElementById(parts[1]).src =chkedChkBox;									
			}			
		}
	}	
}

function checkAllChilds(object) {
var objectId = object.id;
var imageSource = object.src;

if (imageSource.indexOf(chkedChkBox) != -1) {
		object.src = unChkedChkBox;		
	} else {
		object.src = chkedChkBox;
	}
for (var i = 0; i < permissionIds.length; i++) {
	var parts = permissionIds[i].split('_');
		if(objectId == parts[1]){						
			if (imageSource.indexOf(chkedChkBox) != -1) {
				document.getElementById(parts[0]).src =unChkedChkBox;	
			} else {
				document.getElementById(parts[0]).src = chkedChkBox;
			}
		}			
	}
}


function onRadioChange(obj){
	var source = obj.src;
	var objectId = parseInt(obj.id);	
	if (source.indexOf(chkedRadioButton) != -1) {
		if(objectId%2 == 0){
			document.getElementById(objectId-1).src =chkedRadioButton;
			document.getElementById(objectId).src =unchkedRadioButton;
		}
		if(objectId%2 == 1){
			document.getElementById(objectId).src = unchkedRadioButton;
			document.getElementById(objectId+1).src =chkedRadioButton;
		}
	}
}

function setPopupTitle(){
	var name = '<bean:write property="roleTitle" name="adminForm" />';
	var title;
	if(name != null && name !=""){
	title ='<b>Manage Role'+ '-<bean:write property="roleTitle" name="adminForm" /></b>';
	}else{
	title ='<b>Manage User'+ '-<bean:write property="userName" name="adminForm" /></b>';
	}
	window.top.setPopTitle(title);
}

function setStrPermissions(){
	var name=document.getElementsByName("imgPermission");
	var AllPermissions='';
	for (var i = 0; i < name.length; i++) {
		var obj = name[i];
		var objId = obj.id;
		var imageSource = obj.src;
		if(imageSource.indexOf(unChkedChkBox)!=-1 || imageSource.indexOf(chkedRadioButton)!=-1){
				AllPermissions = AllPermissions+","+objId+":"+"0";
		}else {
				AllPermissions = AllPermissions+","+objId+":"+"1";
		}
	}
	document.adminForm.strPermissions.value=AllPermissions;
}

function setStrLevelPermissions(){
	var name=document.getElementsByName("imgLevelPermission");
	var levelPermissions='';
	for (var i = 0; i < name.length; i++) {
		var obj = name[i];
		var objId = obj.id;
		var imageSource = obj.src;
		if(imageSource.indexOf(unChkedChkBox)!=-1){
				levelPermissions = levelPermissions+","+objId+":"+"0";
		}else {
				levelPermissions = levelPermissions+","+objId+":"+"1";
		}
	}
	document.adminForm.strLevelPermissions.value=levelPermissions;
}

function submitFrom(){
		setStrPermissions();
		setStrLevelPermissions();
		document.adminForm.submit();
}

window.onload = setPopupTitle;
</script>