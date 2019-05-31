<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<logic:notEmpty name="attachmentData" scope="request">
<%@ page import="com.talentPool.inbox.dataobject.AttachmentData"%>
<%
	AttachmentData aData = (AttachmentData) request.getAttribute("attachmentData");
	String error = (String) request.getAttribute("error");
	String id = "";
	String fName = "";
	String fPath="";
	long sz = 0;
	String labeledSz = "";
	try {
		if (aData != null) {
			id = aData.getAttachmentId();
			fName = aData.getOriginalFileName();
			sz = aData.getAttachmentSize();
			labeledSz = aData.getLabeledSize();
			fPath = aData.getAttachmentFilePath();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<script>
window.parent.fileUploaded('<%=error%>','<%=id%>','<%=fName%>',<%=sz%>,'<%=labeledSz%>', '<%=fPath%>');
</script>
</logic:notEmpty>