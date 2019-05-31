<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.ArrayList"%>
<div class="contentDiv" style="margin: 20px 0px 0px 10px;">
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
<table cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<table class="boxHeader" cellspacing="0" cellpading="0" width="100%">
				<tr>
					<td style="height: 18px; font-weight: bold;"><bean:message key="common.new"/> <bean:message key="common.resume"/></td>
				</tr>
			</table>
			<div class="outerDiv" style="border-top:0px;"><!--Original Resume IFRAME --> <iframe
				src="importResume.do?mode=getResumeToImport&applicantId=<bean:write name="desktopSearchForm" property="applicantId" />&originalResumePath=<bean:write name="desktopSearchForm" property="parsedResumePath" />&emailId=<bean:write name="desktopSearchForm" property="emailId"/>&noContext=1"
				style="width:485px;height:630px;visibility:block;border:0px; margin:0px;"
				frameborder="0" name="viewPort"></iframe> <!--Original Resume IFRAME -->
			</div>
		</td>
		<td style="padding-left:5px;" valign="top">
			<table class="boxHeader" cellspacing="0" cellpading="0" width="100%">
				<tr>
					<td style="height: 18px; font-weight: bold;"><bean:message key="common.duplicate"/> <bean:message key="common.resume"/> <bean:message key="common.found"/></td>
				</tr>
			</table>
			<div class="outerDiv" style="border-top:0px;"><!--Original Resume IFRAME --> <iframe
				src="importResume.do?mode=getResumeToImport&applicantId=<bean:write name="desktopSearchForm" property="duplicateApplicantId" />&originalResumePath=<bean:write name="desktopSearchForm" property="duplicateApplicantOriginalResumePath" />&noContext=1"
				style="width:485px;height:630px;visibility:block;border:0px; margin:0px;"
				frameborder="0" name="viewPort"></iframe> <!--Original Resume IFRAME -->
			</div>
		</td>
	</tr>
</table>

<div class="navBtn" style="float: left;margin-top:10px; "><a
	href="#" style="width:60px; margin-right: 5px;" class="active"
	onclick="javascript:same();return false;" id="ignore"><span
	class="rightC"></span><span class="leftC"></span><bean:message key="common.same"/></a> <a href="#"
	style="width:90px; margin-right: 5px;" class="active"
	onclick="javascript:different();return false;" id="ignore"><span
	class="rightC"></span><span class="leftC"></span><bean:message key="common.different"/></a></div>
</div>
<script language="JavaScript">
function same(){
	document.desktopSearchForm.mode.value="updateConfirm";
	document.desktopSearchForm.ignoreDuplicate.value="1"
	submitForm();
}
function different(){
	document.desktopSearchForm.mode.value="duplicateImport";
	submitForm();
}
function submitForm(){
	document.desktopSearchForm.submit();
}
</script>
