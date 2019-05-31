<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@ page import="com.talentPool.positions.dataobject.PositionData" %>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<html:form action="/applicantRegistrationFromWeb">
<html:hidden property="mode" value="applicantRegistrationFromSite"/>
<html:hidden property="positionId" name="applicantFromWebForm"/>
<div style="overflow:auto;">
	<div style="margin-left:5px; text-align:left;">
		<table cellspacing="0" cellpadding="4">
			<tr>
				<td class="colBorder" width="150px"><B><bean:message key="common.position_title" /></B></td>
				<td class="colBorder" width="400px"><bean:write name="positionForm" property="positionName"/></td>
			</tr>
			<logic:notEmpty name="positionForm" property="locationName">
				<tr>
					<td class="colBorder" width="150px"><b><bean:message key="position.publish_for_walk_in.label.position_location" /></b></td>
					<td class="colBorder" width="400px"><bean:write name="positionForm" property="locationName"/></td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td class="colBorder" width="150px"><B><bean:message key="common.position_code" /></B></td>
				<td class="colBorder" width="400px"><bean:write name="positionForm" property="positionCode"/></td>
			</tr>
			<tr>
				<td class="colBorder" style="vertical-align: top;" width="150px"><B><bean:message key="position.publish_to_web_site_job_responsibilities"/></B></td>
				<td class="colBorder" id="responsibilitiesTd" width="400px"></td>
			</tr>
			<tr>
				<td class="colBorder" style="vertical-align: top; " width="150px"><B><bean:message key="position.publish_to_web_site_job_requirements"/></B></td>
				<td class="colBorder" id="requirementsTd" width="400px"></td>
			</tr>
			<tr>
				<td class="colBorder" width="150px"><B><bean:message key="position.publish_to_web_site_experience"/></B></td>
				<td class="colBorder" width="400px"><bean:write name="positionForm" property="minimumExperience" />-<bean:write name="positionForm" property="maximumExperience" />
					<bean:message key="position.publish_to_web_site_years" />
				</td>
			</tr>
			<tr>
				<td class="colBorder" width="150px"><B><bean:message key="position.publish_to_web_site_primary_skills"/></B></td>
				<td class="colBorder" width="400px"><bean:write name="positionForm" property="primarySkills" /></td>
			</tr>
			<logic:notEmpty name="positionForm" property="secondarySkills">
				<tr>
					<td class="colBorder" width="150px"><B><bean:message key="position.publish_to_web_site_secondary_skills"/></B></td>
					<td class="colBorder" width="400px"><bean:write name="positionForm" property="secondarySkills" /></td>
				</tr>
			</logic:notEmpty>
			<tr>
				<td class="colBorder" width="150px"><B><bean:message key="position.publish_to_web_site_education"/></B></td>
				<td class="colBorder" width="400px"><bean:write name="positionForm" property="degreeTitle" /></td>
			</tr>
			<tr>
				<td class="colBorder" width="150px"><B><bean:message key="position.publish_to_web_site_branch"/></B></td>
				<td class="colBorder" width="400px"><bean:write name="positionForm" property="branchName" /></td>
			</tr>
			<tr>
				<td class="colBorder" width="150px"><B><bean:message key="position.publish_to_web_site_job_posted"/></B></td>
				<td class="colBorder" width="400px"><bean:write name="positionForm" property="positionCreateDate" /></td>
			</tr>
		</table>
		<br>
		<table>
		   <tr>
			   	<td>
					<html:button property="" onclick="onApply()" title="Apply" value="Apply" style="height:20px;"></html:button>
				</td>
			</tr>
		</table>
	</div>
</div>
</html:form>

<script language="javascript">
function onApply(){
	document.applicantFromWebForm.positionId.value = <bean:write name="positionId" scope="request"></bean:write>;
	document.applicantFromWebForm.submit();
}

function displayResponsibilities(){
    var tbl = document.getElementById('responsibilitiesTd');
	if (tbl && '<bean:write name="positionForm" property="responsibilities"/>'!='' ) {
		tbl.innerHTML = unescapeHTML('<bean:write name="positionForm" property="responsibilities"/>');	
	}else{
		tbl.innerHTML = "&nbsp;";
	}
}

function displayRequirements(){
    var tbl = document.getElementById('requirementsTd');
	if (tbl && '<bean:write name="positionForm" property="requirements"/>' != '') {
		tbl.innerHTML = unescapeHTML('<bean:write name="positionForm" property="requirements"/>');
	}else{
		tbl.innerHTML = "&nbsp;";
	}
}

function doOnLoad() {
	displayResponsibilities();
	displayRequirements();
}

window.onload=doOnLoad;
</script>