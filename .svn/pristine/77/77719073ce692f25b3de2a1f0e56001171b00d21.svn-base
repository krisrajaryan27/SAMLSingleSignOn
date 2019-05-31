<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="java.lang.String"%>
<div class="contentDiv">
<html:form action="/applicantRegistration">
<input type="hidden" name="mode" value="capitaStep2" />
<input type="hidden" name="showDuplicatePage" value="1" />
<input type="hidden" name="positionId" value='<%=request.getParameter("positionId")%>'/>
<input type="hidden" name="locationId" value='<%=request.getParameter("locationId")%>'/>
<input type="hidden" name="currentEmpFromDate" value='<%=(String)request.getAttribute("currentEmpFromDate")%>'/>
<input type="hidden" name="currentEmpToDate" value='<%=(String)request.getAttribute("currentEmpToDate")%>'/>
<input type="hidden" name="prevEmp" value='<%=(String)request.getAttribute("prevEmp")%>'/>
<input type="hidden" name="prevEmpFromDate" value='<%=(String)request.getAttribute("prevEmpFromDate")%>'/>
<input type="hidden" name="prevEmpToDate" value='<%=(String)request.getAttribute("prevEmpToDate")%>'/>
<input type="hidden" name="title" value='<%=(String)request.getAttribute("title")%>'/>
<input type="hidden" name="firstName" value='<%=(String)request.getAttribute("firstName")%>'/>
<input type="hidden" name="lastName" value='<%=(String)request.getAttribute("lastName")%>'/>
<input type="hidden" name="gender" value='<%=(String)request.getAttribute("gender")%>'/>
<input type="hidden" name="dateOfBirth" value='<%=(String)request.getAttribute("dateOfBirth")%>'/>
<input type="hidden" name="refererEmployeeName" value='<%=(String)request.getAttribute("refererEmployeeName")%>'/>
<input type="hidden" name="refererEmployeeCode" value='<%=(String)request.getAttribute("refererEmployeeCode")%>'/>
<input type="hidden" name="emergencyLine1" value='<%=(String)request.getAttribute("emergencyLine1")%>'/>
<input type="hidden" name="emergencyLine2" value='<%=(String)request.getAttribute("emergencyLine2")%>'/>
<input type="hidden" name="emergencyDist" value='<%=(String)request.getAttribute("emergencyDist")%>'/>
<input type="hidden" name="emergencyCity" value='<%=(String)request.getAttribute("emergencyCity")%>'/>
<input type="hidden" name="emergencyZip" value='<%=(String)request.getAttribute("emergencyZip")%>'/>
<input type="hidden" name="permanentLine1" value='<%=(String)request.getAttribute("permanentLine1")%>'/>
<input type="hidden" name="permanentLine2" value='<%=(String)request.getAttribute("permanentLine2")%>'/>
<input type="hidden" name="permanentDist" value='<%=(String)request.getAttribute("permanentDist")%>'/>
<input type="hidden" name="permanentCity" value='<%=(String)request.getAttribute("permanentCity")%>'/>
<input type="hidden" name="permanentZip" value='<%=(String)request.getAttribute("permanentZip")%>'/>
<input type="hidden" name="process" value='<%=(String)request.getAttribute("process")%>'/>
<input type="hidden" name="industry" value='<%=(String)request.getAttribute("industry")%>'/>
<input type="hidden" name="vertical" value='<%=(String)request.getAttribute("vertical")%>'/>

<input type="hidden" name="phone1" value='<%=request.getParameter("phone1")%>'/>
<input type="hidden" name="phone2" value='<%=request.getParameter("phone2")%>'/>
<input type="hidden" name="email" value='<%=request.getParameter("email")%>'/>
<input type="hidden" name="sourceTypeId" value='<%=request.getParameter("sourceType")%>'/>
<input type="hidden" name="sourceId" value='<%=request.getParameter("source")%>'/>
<input type="hidden" name="degreeId" value='<%=request.getParameter("degree")%>'/>
<input type="hidden" name="institute" value='<%=request.getParameter("institute")%>'/>
<input type="hidden" name="fresher" value='<%=request.getParameter("fresher")%>'/>
<input type="hidden" name="currentEmp" value='<%=request.getParameter("currentEmp")%>'/>
<input type="hidden" name="currentCTC" value='<%=request.getParameter("currentCTC")%>'/>
<font style="font-size: 20px; font-weight: bold;" ><bean:message key="applicant_registration.label.registration_form" /></font>
<br/><br/>
<bean:message key="applicant_registration.label.duplicate_detected" />
<br/><br/>

<div class="navBtn" style="float: left;">
	<a href="#" style="width:60px;" class="active" onclick="javascript:document.forms[0].submit();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back" /></a>
</div>
</html:form>
</div>