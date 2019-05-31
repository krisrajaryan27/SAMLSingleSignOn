<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.inbox.dataobject.AttachmentData"%>

<%@page import="org.apache.struts.Globals"%><script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/AttachmentClass.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/noColumnBody.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/default.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/subModal.css"/>	

<%
	ArrayList aList = (ArrayList) request.getAttribute("dataList");
	String error = (String) request.getAttribute("error");
	String id = "";
	String fName = "";
	String fPath="";
	long sz = 0;
	String labeledSz = "";

%>

<div class="contentDivPop" style="width:240px;">

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
	
	<html:form action="/inbox" enctype="multipart/form-data" target="subForm">
	<html:hidden property="mode" value="attachResumes"/>
	<html:hidden property="applicantId"/>
	<html:hidden property="tmpEmailId"/>	
	<html:hidden property="emailLocation"/>
	
	<table style="border:0px;vertical-align: top;" align="center">
		<tr>
			<td style="border:0px;">
				<img src=images/wait.gif />
			</td>
			<td style="border:0px;">&nbsp;<bean:message key="common.please_wait"/>
			</td>
		</tr>
	</table>

</html:form>	
</div>
<iframe src="" name="subForm" style="width:0px;height:0px;"></iframe>

<script language="JavaScript">

//function called from child Iframe, to update attached list or display error message if any
function fileUploaded(error,attachments){
	if(error==''){	
	    window.parent.setAttachment(attachments);	
	}else{
		alert(error);
	}
	
}

function attachResumeOneByOne(){
	var attachments = new Array();
	<%
	if(aList!=null){
		for(int i=0; i<aList.size(); i++){
			AttachmentData aData = (AttachmentData) aList.get(i);
			try {
				if (aData != null) {
					id = aData.getAttachmentId();
					fName = aData.getOriginalFileName();
					sz = aData.getAttachmentSize();
					labeledSz = aData.getLabeledSize();
					fPath = aData.getAttachmentFilePath();
					%>
					attachments[attachments.length]= new Attachment('<%=id%>', '<%=fName.replaceAll("'","\\\\'")%>',<%=sz%>, '<%=labeledSz%>', '<%=fPath%>');
					<%
				}
			} catch (Exception e) {

			}
		}
	}else{
		if(error!=null){
			%>
			alert('<%=error%>');
			<%
		}
	}
	%>
	fileUploaded('<%=error%>',attachments);
	window.parent.hidePopWin(false);
}

function submitForm(){
	document.inboxForm.submit();
}

function onWindowLoad(){
	attachResumeOneByOne();
}

window.onload=onWindowLoad;

</script>


