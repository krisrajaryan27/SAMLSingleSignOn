<%@page import="com.talentPool.common.utils.FileUtils.FileHandlerUtils"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import= "java.io.*"%>
<%@ page import= "java.net.*"%>
<%@ page import= "java.util.*"%>
<%@ page import= "javax.servlet.*"%>
<%@ page import= "javax.servlet.http.*, com.talentPool.applicant.form.ApplicantForm, com.talentPool.common.utils.Utils,com.talentPool.common.utils.FileHandler"%>
<%
String content = (String)request.getAttribute("content");
%>
<logic:present  scope="request" name="content">
<style>
<!--
html{
scrollbar-arrow-color:#99CC33;scrollbar-3dlight-color:#FFFFFF;	scrollbar-darkshadow-color:#FFFFFF;	scrollbar-face-color:#FFFFFF;	scrollbar-highlight-color:#99CC33;	scrollbar-shadow-color:#99CC33;	scrollbar-track-color:#F2F2F2;
}
-->

</style>
<logic:notPresent  scope="request" parameter="noContext">
<link rel="STYLESHEET" type="text/css" href="themes/default/dhtmlXMenu.css">
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script language="JavaScript" src="js/dhtmlxGrid/menu/js/dhtmlXProtobar.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/menu/js/dhtmlXMenuBar.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/menu/js/dhtmlXMenuBar_cp.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/applicants/contextmenu.js"></script>
</logic:notPresent>
<link rel="STYLESHEET" type="text/css" href="themes/default/hilite.css">
<script src="js/hilite.js"></script> 
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<style>
td{margin:0px; padding:0px;}
</style>
<DIV id="resume"></DIV> 
<script>

function encodeMyHtml() {
	encodedHtml = getReplaced("<%=content%>");
	document.getElementById('resume').innerHTML=unescape(encodedHtml);
}

function getReplaced(txt){
	txt = txt.replace(/(&amp;nbsp;)/g,'&nbsp;');
	txt = txt.replace(/(&gt;)/g,'>');
	txt = txt.replace(/(&lt;)/g,'<');
	txt = txt.replace(/(&amp;gt;)/g,'&gt;');
	txt = txt.replace(/(&amp;lt;)/g,'&lt;');
	return txt;
	}

window.onload=doOnLoad;
function doOnLoad() {
encodeMyHtml();
hiliteKeywords();
<logic:notPresent  scope="request" parameter="noContext">
	loadContextMenu();
</logic:notPresent>	
	
}  


</script>
</logic:present> 
<logic:notPresent  scope="request" name="content">
 <%
 	String filePath = (String)request.getAttribute("originalDocPath");
 	String contentType = (String)request.getAttribute("contentType");
 	//out.write("fileName " + fileName);
 	//out.write("contentType " + contentType);
 	//response.setContentType("application/x-download");
 	if(filePath !=null){
 		response.setContentType(contentType);
 		// The below condition added because of the problem occured for pdf files not shown up in IE 
 		// when application is made run on HTTPS prtocol. Can be remove if an alternative found for this issue.
 		if(FileHandlerUtils.getContentType("pdf").equals(contentType)){
 			response.setHeader("cache-control", "private");
	 		response.setHeader("Pragma",""); 
 		}
 		File f = new File(filePath);
 		String fileName = f.getName();
 		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
 		f = null;
 		ServletOutputStream os = response.getOutputStream();
 		ByteArrayOutputStream baos = new ByteArrayOutputStream();
 		FileHandler fileHandler = new FileHandler();
 		fileHandler.readFile(filePath, baos);
 		response.setContentLength(baos.size());
 		baos.writeTo(os);
 		os.flush();
 		os.close();  
 	}
 %>
</logic:notPresent>
