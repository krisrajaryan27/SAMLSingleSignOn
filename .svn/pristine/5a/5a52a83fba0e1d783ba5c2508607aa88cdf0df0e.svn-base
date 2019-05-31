<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties,
							com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.applicant.ApplicantConstants"%><div class="contentDiv" style="margin: 20px 0px 0px 10px;">
<div style="width:180px;" class="boxTab"><span class="rightC"></span>
	<span class="leftC"></span>&nbsp;<strong>Bulk Import-Duplicate</strong></div>
<div class="outerDiv" style="width:900px;padding:10px;height:320px; overflow: auto;">
<html:form action="/desktop">
	<html:hidden property="mode" value="" />
	<html:hidden property="ignoreDuplicate" value="desktopSearchForm" />
	<html:hidden property="duplicateString" name="desktopSearchForm" />
	<html:hidden property="duplicateApplicantId" value="desktopSearchForm" />
	<html:hidden property="duplicateApplicantOriginalResumePath" value="desktopSearchForm" />
	<html:hidden property="parsedResumePath" name="desktopSearchForm" />
	<html:hidden property="duplicateApplicantStatus" name="desktopSearchForm" />
	<html:hidden property="resultId" name="desktopSearchForm" />
	<html:hidden property="sessionId" name="desktopSearchForm" />
	
</html:form>
<table cellspacing="0" cellpading="0">
	<tr>
		<td class="Grey" style="padding-bottom: 15px;">This <bean:message key="common.candidates"/>
		identity is similar to the following. Please check to prevent duplication</td>
	</tr>
</table>

<table class="boxHeader" cellspacing="0" cellpading="0">
	<tr>
		<td width="18px"></td>
		<td style="width:250px; height: 18px; font-weight: bold;"><bean:message key="common.name"/></td>
		<td
			style="width:250px; height: 18px; font-weight: bold;padding-left:0px;"><bean:message key="common.fields"/> matched</td>
		<td
			style="width:150px; height: 18px; font-weight: bold;padding-left:0px;"><bean:message key="common.resume"/> updated <bean:message key="common.date"/></td>
		<td	style="width:200px; height: 18px; font-weight: bold;padding-left:0px;">
			<bean:message key="common.status"/>
		</td>
	</tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" class="posinput">
	<logic:iterate id="data" scope="request" name="duplicates">
		<tr>
			<td class="row2"><img style="cursor:pointer;" title="compare" src="images/image_compare.jpg" id="img_0" width="18px" onclick="compare('<bean:write name="data"	property="applicantId" />','<bean:write name="data"	property="originalResumePath" />','<bean:write name="data" property="applicantStatus" />'); return false;" />
			</td>
			<td style="width:250px;" class="row2">
			<a href="#" onclick="onClickApplicant('<bean:write name="data"	property="applicantId" />')">
			<bean:write name="data"	property="applicantName" /></a></td>
			<td style="width:300px;" class="row2"><logic:iterate
				id="matchedField" name="data" property="matchedFields" indexId="idx">
				<logic:notEqual value="0" name="idx">,&nbsp;</logic:notEqual>
				<bean:write name="matchedField" />
			</logic:iterate></td>
			<td style="width:150px;" class="row2"><bean:write name="data"
				property="resumeDateUpdated" format="dd-MMM-yy" /></td>
			<td style="width:150px;" class="row2">
				<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>" name="data" property="applicantStatus">
					<bean:message key="black_list.label.blackList" />
				</logic:equal>
				<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>" name="data" property="applicantStatus">
					<%if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {%>
					<bean:write name="data"	property="positionCode" />
					<%}else{ %>
					<bean:write name="data"	property="positionTitle" />
					<%} %>
					
					
					<br/><bean:write name="data"	property="processStatus" /><br/>
					<bean:write name="data"	property="processMovedDate"  format="dd-MMM-yy" />
				</logic:equal>
			</td>	
		</tr>
	</logic:iterate>
</table>
</div>

<div class="navBtn" style="float: left;margin-top:10px; ">
	<a	href="#" style="width:60px; margin-right: 5px;" class="active"	onclick="closethis();" id="ignore"><span
	class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a> 
	<a href="#"	style="width:310px; margin-right: 5px;" class="active"	onclick="createNew();" id="ignore"><span
	class="rightC"></span><span class="leftC"></span>Create <bean:message key="common.new"/> <bean:message key="common.candidate"/> by ignoring duplicates</a>
</div>

<script language="JavaScript">
function compare(aId,rPath,aStatus){
	document.desktopSearchForm.mode.value="compareResume";
	document.desktopSearchForm.duplicateApplicantId.value=aId;
	document.desktopSearchForm.duplicateApplicantOriginalResumePath.value=rPath;
	document.desktopSearchForm.duplicateApplicantStatus.value=aStatus;
	submitForm();
}
function closethis(){
	window.close();
}
function onClickApplicant(aId){	
	url = "selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId;
	window.open(url,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	return false;

}

function createNew(){
	document.desktopSearchForm.mode.value="importSingleApplicant";
	document.desktopSearchForm.ignoreDuplicate.value="1";
	submitForm();
}
function submitForm(){
	document.desktopSearchForm.submit();
}
</script>
