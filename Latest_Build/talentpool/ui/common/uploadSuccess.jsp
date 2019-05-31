<%@page import="com.talentPool.inbox.InboxConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<logic:empty name="attachmentData" scope="request">
	<logic:empty name="documentData" scope="request">
		<logic:present name="error" scope="request">
			<logic:notEmpty name="error" scope="request" >
				<script>
					window.parent.fileUploaded('<bean:write name="error" scope="request" />');
				</script>
			</logic:notEmpty>
		</logic:present>
	</logic:empty>
</logic:empty>
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

	}
%>
<script>
window.parent.fileUploaded('<%=error%>','<%=id%>','<%=fName%>',<%=sz%>,'<%=labeledSz%>', '<%=fPath%>');
</script>
</logic:notEmpty>
<logic:notEmpty name="documentData" scope="request">
<%@ page import="com.talentPool.documents.dataobject.DocumentData"%>
<%
	DocumentData data = (DocumentData) request.getAttribute("documentData");
	String error = (String) request.getAttribute("error");
	String relativeFilePath = "";
	try {
		if (data != null) {
			relativeFilePath = data.getRelativeFilePath();
		}
	} catch (Exception e) {

	}
%>
<script>
window.parent.fileUploaded('<%=error%>','<%=relativeFilePath%>');
</script>
</logic:notEmpty>