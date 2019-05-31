<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.talentPool.applicant.ApplicantConstants"%><div class="contentDiv" style="margin: 20px 0px 0px 10px;">
<div style="width:180px;" class="boxTab"><span class="rightC"></span>
	<span class="leftC"></span>&nbsp;<strong><bean:message key="common.bulk_import"/>-<bean:message key="common.duplicate"/></strong></div>
	<html:form	action="/desktop">
	<html:hidden property="mode" value="" />
	<html:hidden property="ignoreDuplicate" value="" />
	<html:hidden property="duplicateString" name="desktopSearchForm" />
	<html:hidden property="duplicateApplicantId" name="desktopSearchForm" />
	<html:hidden property="duplicateApplicantOriginalResumePath" name="desktopSearchForm" />
	<html:hidden property="duplicateApplicantStatus" name="desktopSearchForm" />
	<html:hidden property="resultId" name="desktopSearchForm" />
	<html:hidden property="sessionId" name="desktopSearchForm" />
	
</html:form>
<div class="outerDiv"	style="width:450px;padding:10px;height:60px; overflow: auto; padding: 20px;">
<table cellspacing="0" cellpading="0" width="100%">
	<tr>
		<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>" name="desktopSearchForm" property="duplicateApplicantStatus">
			<td style="color: red;">
				<bean:message key="common.applicant"/>&nbsp;<bean:message key="black_list.import.duplicate" />
			</td>
		</logic:equal>
		<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>" name="desktopSearchForm" property="duplicateApplicantStatus">
			<td>Do you want to replace the <bean:message key="common.duplicate"/> <bean:message key="common.resume"/> with <bean:message key="common.new"/> <bean:message key="common.resume"/>? 
			</td>			
		</logic:equal>
	</tr>
</table>
<div class="navBtn" style="float: left;margin-top:20px; ">
	<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>" name="desktopSearchForm" property="duplicateApplicantStatus">
		<a href="#"
		style="width:60px; margin-right: 5px;" class="active"
		onclick="javascript:no();return false;" id="ignore"><span
		class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
	</logic:equal>
	<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>" name="desktopSearchForm" property="duplicateApplicantStatus">
		<a href="#" style="width:60px; margin-right: 5px;" class="active"
		onclick="javascript:yes();return false;" id="ignore"><span
		class="rightC"></span><span class="leftC"></span><bean:message key="common.yes"/></a> <a href="#"
		style="width:60px; margin-right: 5px;" class="active"
		onclick="javascript:no();return false;" id="ignore"><span
		class="rightC"></span><span class="leftC"></span><bean:message key="common.no"/></a>
	</logic:equal>
</div>
</div>
</div>
<script language="JavaScript">
function yes(){
	document.desktopSearchForm.mode.value="updateApplicantWithNewResume";
	//document.desktopSearchForm.applicantId.value= document.desktopSearchForm.duplicateApplicantId.value;	
	//document.desktopSearchForm.ignoreDuplicate.value="1"
	submitForm();
}
function no(){
	document.desktopSearchForm.mode.value="doNotUpdateResume";
	submitForm();
	//window.close();
}
function submitForm(){
	document.desktopSearchForm.submit();
}
</script>
