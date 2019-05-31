<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.applicant.dataobject.ApplicantData,
                com.talentPool.inbox.InboxConstants,
                com.talentPool.user.manager.PermissionSet,
                 com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                com.talentPool.common.NavigationConstants"%>
                <%@page import="com.talentPool.documents.DocumentConstants"%>
<%@page import="com.talentPool.documents.utils.DocumentUtils"%>
<%@page import="com.talentPool.inbox.dataobject.AttachmentData"%>
<script src="js/scripta/lib/prototype.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/print.css" media="print">                
<script language="javascript">
function replyEmail(){
	var url="inbox.do?mode=newEmail&emailId=<bean:write name="inboxForm" property="emailId"/>&newEmailType=<%=InboxConstants.EMAIL_TYPE_REPLY%>&emailLocation=<bean:write property="emailLocation"  name="inboxForm"/>&applicantId=<bean:write name="inboxForm" property="applicantId"/>";
	window.location=url;
}
function forwardEmail(){
	var url="inbox.do?mode=newEmail&emailId=<bean:write name="inboxForm" property="emailId"/>&newEmailType=<%=InboxConstants.EMAIL_TYPE_FORWARD%>&emailLocation=<bean:write property="emailLocation"  name="inboxForm"/>&applicantId=<bean:write name="inboxForm" property="applicantId"/>";
	window.location=url;
}

function beforeprint()
{
$('prnMsgBody').innerHTML =  document.frames["viewPort"].document.body.innerHTML;
$('prnMsgBody').style.display="block";
}
function afterprint(){
$('prnMsgBody').style.display="none";
}

window.onbeforeprint = beforeprint;
window.onafterprint = afterprint;
</script>
<div class="contentDivPop" >
	<div class="bottomDivSec" style="width:752px;">
	<div class="vpTop" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
          <tr>
            <td><strong id="VP_TITLE" class="Grey"><bean:write name="messageData" property="subject"/>&nbsp;</strong></td>
          </tr>
        </table>
	</div>
	</div>
	<div class="outerDiv" style="border-top:0px;">
	<div class="popupTop" style="width:730px;" >
		<table class="tblPop" width="100%">
		<tr>
        	<td class="header" width="55"><bean:message key="view_email.label.from"/></td>
            <td ><bean:write name="messageData" property="from"/></td>
            <td class="header" width="30"><bean:message key="view_email.label.date"/></td>
            <td width="250" ><bean:write name="messageData" property="sendDateToDisplay" /></td>
       	</tr>
        <tr>
        	<td class="header"><bean:message key="view_email.label.to"/></td>
			<td colspan="3"><bean:write name="messageData" property="to"/></td>
        </tr>
        <logic:notEmpty name="messageData" property="cc">
        <tr>
         	<td class="header"><bean:message key="view_email.label.cc"/></td>
         	<td colspan="3"><bean:write name="messageData" property="cc"/></td>
        </tr>
        </logic:notEmpty>
        <%if(GlobalApplicationProperties.isEnabled(GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL)){%>
        <logic:notEmpty name="messageData" property="bcc">
        <tr>
         	<td class="header"><bean:message key="view_email.label.bcc"/></td>
         	<td colspan="3"><bean:write name="messageData" property="bcc"/></td>
        </tr>
        </logic:notEmpty>
        <%} %>
        </table>
		<table class="tblPop" >
        <tr>
         	<td class="header right"><bean:message key="view_email.label.attachments"/></td>
         	<td>
				<logic:notEmpty name="messageData" property="attachments">
                <logic:iterate id="attachment" name="messageData" property="attachments">
                	<bean:define id="attachment" name="attachment"></bean:define>
                	<% 
                	AttachmentData attachmentData = (AttachmentData)attachment;
                	%>
                  <a href="<%=DocumentUtils.getDocumentURL(attachmentData.getAttachmentFilePath(),"") %>" 
                  target="_anew"><bean:write name="attachment" property="originalFileName"/></a>&nbsp;&nbsp;&nbsp;
                </logic:iterate>
                </logic:notEmpty>
         	</td>
        </tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="outerDiv">
				<DIV id="prnMsgBody" class="MSGBODY" style="display:none"></DIV>
				<iframe src="inbox.do?mode=getEmailBody&emailId=<bean:write name="inboxForm" property="emailId"/>&emailLocation=<bean:write property="emailLocation"  name="inboxForm"/>" style="width:720px;;height:380px;border:none;visibility: visible;" frameborder="0" id="viewPort" name="viewPort" class="noprint"></iframe>
				</div>
			</td>
		</tr>
		<tr >
			<td><br/>
			<div class="navBtn noprint" style="float: right;" id="navBtn">
			<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript:window.print();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.print"/></a>
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SEND_EMAIL">
			   <a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: replyEmail();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="view_email.label.reply"/></a>
			   <a href="#" style="width:70px;margin-right:5px;" class="active" onclick="javascript: forwardEmail();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="view_email.label.forward"/></a>
			</logic:equal>
			<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>	
	</div>

</div>

<script>
function setPopupTitle(){
	var title = '<b><bean:message key="view_email.label.email"/> - </b><bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}

window.onload = setPopupTitle;
</script>