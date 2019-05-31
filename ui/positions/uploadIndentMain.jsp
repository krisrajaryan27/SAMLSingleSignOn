<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/noColumnBody.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/default.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/subModal.css"/>		
<div class="contentDivPop" style="width:440px;">
	<% if (request.getAttribute(Globals.ERROR_KEY) != null) { %>
	<table id="m_errortable">
		<tr>
			<td class="header"><b><bean:message
				key="errors.following_errors" /></b></td>
		</tr>
		<tr>
			<td class="message"><html:errors /></td>
		</tr>
	</table>
	<br/>
	<% } %>	
	<div class="outerDiv">
	<html:form action="/position" enctype="multipart/form-data" >
	<html:hidden property="mode" value="parseIndent"/>
  	
	<div class="popupTop">
		<table class="tblPop" width="100%">
		  <tr>
			<td><bean:message key="common.select"/> <bean:message key="common.file"/>: 
				</td>
				<td>
					<html:file property="attachedFile" name="positionForm" size="64" style="width:340px;height:20px; " value=""></html:file>  
				</td>
			</tr>
		  
	 </table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
</div>
<script language="JavaScript">
//function called from child Iframe, to update attached list or display error message if any
function fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize, filePath){
    
}
function submitForm(){
	document.positionForm.submit();
}

function actionOnLoad(){	
	var title = '<b><bean:message key="common.select"/>&nbsp;<bean:message key="common.file"/></b>';
	window.top.setPopTitle(title)
}
window.onload=actionOnLoad;
</script>
