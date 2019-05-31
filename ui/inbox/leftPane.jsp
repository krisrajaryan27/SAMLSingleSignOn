<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.masters.dataobject.InboxFolderData"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_IMPORT_FROM_DESKTOP">
<div style="width:180px;margin-left:5px;"> 
	<div style="margin-top:5px;width: 195px;border: 1px dotted #cccccc; padding: 5px; background-color: #fcfcfc;">
	<table cellpadding="2" cellspacing="2" >
		<tr>
			<td>
				<div class="navBtn" style="float: left;">
					<a href="#" style="width:120px;" class="active" onclick="javascript:uploadDocument();"><span class="rightC"></span><span class="leftC"></span><bean:message key="inbox.button.label.upload_resume"/></a>
				</div>
			</td>
		</tr>
		<tr>
			<td class="Grey">
			<bean:message key="inbox.label.or_import_text"/>
			</td>
		</tr>	
	</table>
	</div>
	<br/>
	<div style="margin-top:5px;width: 195px;border: 1px dotted #cccccc; padding: 5px; background-color: #fcfcfc;">
	<table cellpadding="2" cellspacing="2" >
		<tr>
			<td>
				<div class="navBtn" style="float: left;">
					<a href="#" style="width:120px;" class="active" onclick="javascript:excelImport();"><span class="rightC"></span><span class="leftC"></span><bean:message key="inbox.button.label.upload_csv_file" /></a>
				</div>
			</td>
		</tr>
		<tr>
			<td class="Grey">
				from csv file
			</td>
		</tr>	
	</table>
	</div>
	<br/>
	<table cellpadding="2">
		<tr>
			<td class="Grey" style="font-weight: bold;"><bean:message key="inbox.label.folders"/></td>
		</tr>
		
		<logic:iterate id="folder" name="systemFolders" scope="request" type="InboxFolderData">
		<logic:equal name="folder" property="folderId" value="<%=InboxConstants.INBOX_FOLDER_SENT%>">
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHOW_SENT_EMAILS">
		<tr>
	  		<td style="padding-left: 10px;"><a href="#" id="nlink<bean:write name="folder" property="folderId" />" style="text-decoration:none;cursor:default;display:none;"><bean:write name="folder" property="folderName" /></a><a href="#" style="display:inline;" id="link<bean:write name="folder" property="folderId" />" class="green"  onclick="javascript: toggleFolderDisplay('<bean:write name="folder" property="folderId" />', '<bean:write name="folder" property="systemDefined" />')"><bean:write name="folder" property="folderName" /></a> <div id="MSG_COUNT_<bean:write name="folder" property="folderId" />" style="display: inline;" class="Grey">(<bean:write name="folder" property="emailCount" />)</div> </td>
	  	</tr>
	  	</logic:equal>  
		</logic:equal>
		
		<logic:notEqual name="folder" property="folderId" value="<%=InboxConstants.INBOX_FOLDER_SENT%>">
		<tr>
	  		<td style="padding-left: 10px;"><a href="#" id="nlink<bean:write name="folder" property="folderId" />" style="text-decoration:none;cursor:default;display:none;"><bean:write name="folder" property="folderName" /></a><a href="#" style="display:inline;" id="link<bean:write name="folder" property="folderId" />" class="green" onclick="javascript: toggleFolderDisplay('<bean:write name="folder" property="folderId" />', '<bean:write name="folder" property="systemDefined" />')"><bean:write name="folder" property="folderName" /></a> <div id="MSG_COUNT_<bean:write name="folder" property="folderId" />" style="display: inline;" class="Grey">(<bean:write name="folder" property="emailCount" />)</div></td>
	  	</tr>
		</logic:notEqual>
	  	</logic:iterate>
	  	<logic:iterate id="folder" name="drafts" scope="request" type="InboxFolderData">	
		<tr>
	  		<td style="padding-left: 10px;"><a href="#" id="nlink<bean:write name="folder" property="folderId" />" style="text-decoration:none;cursor:default;display:none;"><bean:write name="folder" property="folderName" /></a><a href="#" style="display:inline;" id="link<bean:write name="folder" property="folderId" />" class="green" onclick="javascript: toggleFolderDisplay('<bean:write name="folder" property="folderId" />', '<bean:write name="folder" property="systemDefined" />')"><bean:write name="folder" property="folderName" /></a> <div id="MSG_COUNT_<bean:write name="folder" property="folderId" />" style="display: inline;" class="Grey">(<bean:write name="folder" property="emailCount" />)</div></td>
	  	</tr>
	  	</logic:iterate>
		<tr>
			<td style="height: 15px;"></td>
		</tr>
		<tr>
			<td class="Grey" style="font-weight: bold;"><bean:message key="inbox.label.all_mail_folders"/></td>
		</tr>
		<logic:iterate id="folder" name="userFolders" scope="request" type="InboxFolderData">	
		<tr>
	  		<td style="padding-left: 10px;"><a href="#" id="nlink<bean:write name="folder" property="folderId" />" style="text-decoration:none;cursor:default;display:none;"><bean:write name="folder" property="folderName" /></a><a href="#" style="display:inline;" id="link<bean:write name="folder" property="folderId" />" class="green" onclick="javascript: toggleFolderDisplay('<bean:write name="folder" property="folderId" />', '<bean:write name="folder" property="systemDefined" />')"><bean:write name="folder" property="folderName" /></a> <div id="MSG_COUNT_<bean:write name="folder" property="folderId" />" style="display: inline;" class="Grey">(<bean:write name="folder" property="emailCount" />)</div></td>
	  	</tr>
	  	</logic:iterate>
	</table>
</div>
</logic:equal>	