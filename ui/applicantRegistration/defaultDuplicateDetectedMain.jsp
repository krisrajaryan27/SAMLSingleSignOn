<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="java.lang.String"%>
<%@ page import="com.talentPool.common.utils.Utils"%>
<div class="contentDiv">
<html:form action="/applicantRegistration">
<input type="hidden" name="mode" value="defaultStep1" />
<input type="hidden" name="showDuplicatePage" value="1" />
<input type="hidden" name="positionId" value='<%=request.getParameter("positionId")%>'/>
<input type="hidden" name="name" value='<%=request.getParameter("name")%>'/>
<input type="hidden" name="sourceType" value='<%=request.getParameter("sourceType")%>'/>
<input type="hidden" name="source" value='<%=request.getParameter("source")%>'/>
<input type="hidden" name="currentLocation" value='<%=request.getParameter("currentLocation")%>'/>
<input type="hidden" name="phone1" value='<%=request.getParameter("phone1")%>'/>
<input type="hidden" name="phone2" value='<%=request.getParameter("phone2")%>'/>
<input type="hidden" name="mobile" value='<%=request.getParameter("mobile")%>'/>
<input type="hidden" name="email1" value='<%=request.getParameter("email1")%>'/>
<input type="hidden" name="email2" value='<%=request.getParameter("email2")%>'/>
<input type="hidden" name="skillIds" value='<%=request.getParameter("skillIds")%>'/>
<input type="hidden" name="workingSince" value='<%=request.getParameter("workingSince")%>'/>
<input type="hidden" name="fresher" value='<%=request.getParameter("fresher")%>'/>
<input type="hidden" name="currentEmp" value='<%=request.getParameter("currentEmp")%>'/>
<input type="hidden" name="currentCTC" value='<%=request.getParameter("currentCTC")%>'/>
<input type="hidden" name="expectedCTC" value='<%=request.getParameter("expectedCTC")%>'/>
<input type="hidden" name="noticePeriod" value='<%=request.getParameter("noticePeriod")%>'/>
<input type="hidden" name="eduRowIds" value='<%=request.getParameter("eduRowIds")%>'/>
<% if(!Utils.isBlankOrNull(request.getParameter("eduRowIds"))) { %>
<% String[] temp = request.getParameter("eduRowIds").split(","); %>
<% for(int i = 0; i < temp.length; i++) { %>
<input type="hidden" name="yearOfPassing<%=temp[i]%>" value='<%=request.getParameter("yearOfPassing"+temp[i])%>'/>
<input type="hidden" name="institute<%=temp[i]%>" value='<%=request.getParameter("institute"+temp[i])%>'/>
<input type="hidden" name="degree<%=temp[i]%>" value='<%=request.getParameter("degree"+temp[i])%>'/>
<input type="hidden" name="branch<%=temp[i]%>" value='<%=request.getParameter("branch"+temp[i])%>'/>
<input type="hidden" name="grade<%=temp[i]%>" value='<%=request.getParameter("grade"+temp[i])%>'/>					
<% } %>
<% } %>
<bean:message key="applicant_registration.label.duplicate_detected" />
<br/><br/>

<div class="navBtn" style="float: left;">
	<a href="#" style="width:60px;" class="active" onclick="javascript:document.forms[0].submit();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back" /></a>
</div>
</html:form>
</div>