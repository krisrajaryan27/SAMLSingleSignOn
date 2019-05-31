<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.common.utils.Utils"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/noColumnBody.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/default.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/subModal.css"/>		
<% 
String option = Utils.escapeHTML((String)request.getParameter("option"));
%>

<div class="contentDivPop" style="width:440px;">
	<div class="outerDiv">
	<html:form action="/drafts" enctype="multipart/form-data" target="subForm">
	<html:hidden property="mode" value="uploadPositions"/>
  	
	<div class="popupTop">
		<table class="tblPop" width="100%">
		  <tr>
			<td><bean:message key="common.select"/> <bean:message key="common.file"/>: 
				</td>
				<td>
					<html:file property="attachedFile" name="positionDraftForm" size="64" style="width:340px;height:20px; " value=""></html:file>  
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
<iframe src="" name="subForm" style="width:0px;height:0px;display:none;"></iframe>

<script language="JavaScript">
//function called from child Iframe, to update attached list or display error message if any
function fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize, filePath){
    window.parent.fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize,filePath, '<%=option%>');	
	document.forms['positionDraftForm'].attachedFile.value='';	
	//parent.modalpopup.hidepopup(false);
	window.top.hidePopWin(true);
}
function submitForm(){
	//window.parent.showWait(true);
	document.positionDraftForm.submit();
}

function actionOnLoad(){	
	//parent.modalpopup.settitle('<bean:message key="common.select"/> <bean:message key="common.file"/>');
	var title = '<bean:message key="common.select"/>&nbsp;<bean:message key="common.file"/>'
	window.top.setPopTitle(title)
}
window.onload=actionOnLoad;
</script>
