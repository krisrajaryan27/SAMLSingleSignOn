<%@page import="com.talentPool.common.utils.FileUtils.FileHandlerUtils"%>
<%@ page import="com.talentPool.common.utils.Utils, 
				com.talentPool.reports.ReportConstants, 
				java.io.FileInputStream, 
				javax.servlet.*, java.io.BufferedInputStream"%><%
String fileName = (String)request.getAttribute("fileName");
if(fileName.endsWith(".html")){
	response.sendRedirect(Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER,fileName));
}else{
	String contentType = "application/pdf";
	if(fileName.endsWith(".xls")){
		contentType = "application/vnd.ms-excel";
	} else if(fileName.endsWith(".xlsx")){
		contentType = "application/xlsx";
	} else if(fileName.endsWith(".csv")){
		contentType = "text/csv";
	}
	response.setContentType(contentType);
	response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
	// The below condition added because of the problem occured for pdf files not shown up in IE 
	// when application is made run on HTTPS prtocol. Can be remove if an alternative found for this issue.
	response.setHeader("cache-control", "private");
	response.setHeader("Pragma",""); 
		
	String filePath = Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH,fileName);
	FileInputStream fileInputStream =  new FileInputStream(filePath);
	BufferedInputStream bis = new BufferedInputStream(fileInputStream);
	
	ServletOutputStream sos = response.getOutputStream();
			byte[] buffer = new byte[10 * 1024];
			while (true) {
				int bytesRead = bis.read(buffer, 0, buffer.length);
				if (bytesRead < 0)
					break;
				sos.write(buffer, 0, bytesRead);
	}
			
	fileInputStream.close();
	sos.flush();
	sos.close();
}
%>