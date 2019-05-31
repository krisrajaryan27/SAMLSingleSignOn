<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.documents.DocumentConstants"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script language="JavaScript">
//function called from child Iframe, to update attached list or display error message if any
function fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize, filePath){
    if(error!=''){
    	alert(error);	
    }else{
		window.top.hidePopWin(true);
	}
	hideUpdater('btnUpload');
}
</script>
<div class="contentDivPop" style="width: 500px;">
	<div class="outerDiv">
		<html:form action="/docs" enctype="multipart/form-data" target="subForm">
		<html:hidden property="mode" value="saveDocument"/>
		<html:hidden property="applicantId"/>
		<div class="popupTop">
			<table class="tblPop">
			<tr>
				<td class="header">
					<bean:message key="upload_document.label.select_file"/>
				</td>
				<td>
				   <html:file property="attachedFile" name="documentForm" style="width:300px;height:20px; "></html:file>  
				</td>
			</tr>
			</table>
		</div>
		<div class="popupBody">
			<table class="tblPop" width="100%" border="0">
			<tr>
				<td>
				<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();" id="btnUpload"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.upload"/></a>
						<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
				</td>
			</tr>
			</table>
		</div>	
		</html:form>
	</div>
</div>
<iframe src="" name="subForm" style="width:0px;height:0px;"></iframe>
<script language="javascript">
function submitForm(){
	if(document.documentForm.attachedFile.value==''){
		alert('<bean:message key="upload_document.error.no_file_selected"/>');
		return;
	}
	showUpdater('btnUpload',{setHeight: false, setWidth: false, offsetTop: -25, offsetLeft:-40});
	document.documentForm.submit();
}
function setPopupTitle(){
	var title = '<b><bean:message key="upload_document.title.upload_file"/> - </b>';
	title += '<bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}

window.onload = setPopupTitle;

</script>