<%@ page import= "java.io.*"%>
<%@ page import= "java.net.*"%>
<%@ page import= "java.util.*"%>
<%@ page import= "javax.servlet.*"%>
<%@ page import= "javax.servlet.http.*, com.talentPool.applicant.form.ApplicantForm"%>
<%
	
	ApplicantForm applicantForm = (ApplicantForm)request.getAttribute("applicantForm");
	FileInputStream inputStream = new FileInputStream("C:/Talentpool/aaa.txt");
	String FileName = "aaa.txt";
    response.setHeader("Content-Disposition", "inline;filename="+FileName);
    response.setHeader("Content-Type", "application/msword;filename="+FileName);
     response.setHeader("Content-Disposition", "inline;filename="+"aaa.txt");	
	byte[] buffer=new byte[4096];
	int read,length=buffer.length;
	ServletOutputStream outst = response.getOutputStream();
	while((read=inputStream.read(buffer))!=-1) {
            outst.write(buffer,0,length);
    }
%>