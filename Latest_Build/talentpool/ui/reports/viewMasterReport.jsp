<%@page import="com.talentPool.common.properties.TPLabels"%>
<%@ page import="com.talentPool.common.utils.Utils, 
				com.talentPool.reports.ReportConstants, 
				java.io.FileInputStream, 
				javax.servlet.*, java.io.BufferedInputStream"%>

<%
	String filePath = (String)request.getAttribute("filePath");
	String fileNameToDisplay = (String)request.getAttribute("fileNameToDisplay");
	String contentType = "application/vnd.ms-excel";
	
	response.setContentType(contentType);
	response.setHeader("Content-Disposition", "inline; filename="+fileNameToDisplay);

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

%>