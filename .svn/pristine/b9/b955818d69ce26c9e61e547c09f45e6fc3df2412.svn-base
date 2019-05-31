<%@page import="com.talentPool.common.utils.FileHandler"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%
	String filePath = (String) request.getAttribute("filePath");
	String contentType = (String) request.getAttribute("contentType");
	String fileName = (String) request.getAttribute("fileName");
	String contentDisposition = (String) request.getAttribute("contentDisposition");
	try {
		if (filePath != null) {
			response.setContentType(contentType);
			File f = new File(filePath);
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			if (fileName == null) {
				fileName = f.getName();
			}

			response.setHeader("Content-Disposition", contentDisposition + "; filename=" + fileName);
			// The below condition added because of the problem occured for pdf files not shown up in IE 
			// when application is made run on HTTPS prtocol. Can be remove if an alternative found for this issue.
			response.setHeader("cache-control", "private");
			response.setHeader("Pragma","");
			response.setContentLength(bis.available());

			ServletOutputStream sos = response.getOutputStream();
			byte[] buffer = new byte[4 * 1024];
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
	} catch (Exception e) {
		out.write("Error occured while reading file");
	}
%>
