<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>

<div class="contentDivPop" style="width:450px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
			<table id="m_errortable" > 
				<tr>
			    <td class='header'>
		        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
				</tr>
		    <tr>
	        <td class="message"><html:errors/></td>               
		    </tr>
			</table><br/><br/>
	<%
		}
	%> 
	<div class="outerDiv">
	<html:form action="/masters" onsubmit="submitForm();return false;">
  	<html:hidden property="folderId" name="mastersForm"/>
 	<html:hidden property="mode" name="mastersForm"/>
 	
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header">
				  <bean:message key="master_inbox_folders.label.inbox_folder_name"/>
				  <span class="star">*</span>
				  :
			  </td>
			  <td>
				  <html:text property="folderName" name="mastersForm" size="35" styleId="skillCategory"></html:text>
			  </td>
		  </tr>
	 </table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<logic:notEqual name="mastersForm" property="systemDefined" value="<%=MastersConstants.SYSTEM_FOLDER%>">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			</logic:notEqual>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
</div>


<script type="text/javascript">

function submitForm(){
	var Name = document.mastersForm.folderName.value;
	if(Name.trim()==""){
		return;
	}
	document.mastersForm.mode.value='saveInboxFolder';
	document.mastersForm.submit();
}

function actionOnLoad(){
	<logic:empty name="mastersForm" property="folderId">
		window.top.setPopTitle('<b><bean:message key="master_inbox_folders.label.add_folder"/></b>');
	</logic:empty>
	<logic:notEmpty name="mastersForm" property="folderId">
		window.top.setPopTitle('<b><bean:message key="master_inbox_folders.label.edit_folder"/></b>');
	</logic:notEmpty>
}
window.onload=actionOnLoad;
</script>