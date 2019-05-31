<%@page import="com.talentPool.reports.ReportConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@ page import="java.io.FileInputStream, 
				javax.servlet.*, java.io.BufferedInputStream"%><%
String fileName = (String)request.getAttribute("fileName");
String fileNameToDisplay = (String)request.getAttribute("fileNameToDisplay");
if(fileName.endsWith(".html")){
	response.sendRedirect(Utils.concatFilePath(ReportConstants.REPORT_DESTINATION_FOLDER,fileName));
}else{
	String contentType = "application/pdf";
	if(fileName.endsWith(".xls") || fileName.endsWith(".csv")){
		contentType = "application/vnd.ms-excel";
	}
	response.setContentType(contentType);
	response.setHeader("Content-Disposition", "inline; filename=\"" + fileNameToDisplay + "\"");
		
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