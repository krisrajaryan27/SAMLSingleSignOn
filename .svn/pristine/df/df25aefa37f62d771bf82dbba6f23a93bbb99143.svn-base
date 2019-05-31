<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
 <%
	String filePath = (String)request.getAttribute("filePath");
	String contentType = (String)request.getAttribute("contentType");
	String fileName = (String)request.getAttribute("fileName");
	if(filePath !=null){
		response.setContentType(contentType);
		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		if(fileName == null){
			fileName = f.getName();
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentLength(bis.available());
		
		ServletOutputStream sos = response.getOutputStream();
		byte[] buffer = new byte[5000];
		while (true) {
		   int bytesRead = bis.read(buffer, 0, buffer.length);
		   if (bytesRead < 0)
		   break;
		   sos.write(buffer, 0, bytesRead);
		} 
		fis.close();
		sos.flush();
		sos.close(); 
	}
%>
