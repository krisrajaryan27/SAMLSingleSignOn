<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.selectionProcess.form.SelectionProcessForm,
                com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                com.talentPool.common.properties.TPApplicationProperties"%>

<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<%@page import="com.talentPool.applicant.ApplicantConstants"%><logic:present name="update" scope="request">
<script>
	window.top.hidePopWin(true);
</script>
</logic:present>                
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<% 
if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
<table  id="m_errortable" > 
  <tr>
	<td class="header">
	  <b><bean:message key="errors.following_errors"/></b>
	</td>               
  </tr>
  <tr>
    <td class="message"><html:errors/></td>               
  </tr>
</table>
<br>
<% } %>
<div class="contentDivPop" style="width: 500px;">
<div class="outerDiv">
	<html:form action="/selectionProcess">
  	<html:hidden property="mode" name="selectionProcessForm"/>
  	<html:hidden property="applicantId" name="selectionProcessForm"/>
  	<html:hidden property="positionId" name="selectionProcessForm"/>
  	<html:hidden property="applicantStatus" name="selectionProcessForm"/>
	<div class="popupBody">
		<table class="tblPop">
		<tr>
			<td class="header">
			<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>" name="selectionProcessForm" property="applicantStatus">
				<bean:message key="black_list.label.blackList"/>&nbsp;<bean:message key="common.reason"/><span class="star">*</span>
			</logic:equal>
			<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>" name="selectionProcessForm" property="applicantStatus">
				<bean:message key="black_list.label.unBlackList"/>&nbsp;<bean:message key="common.reason"/><span class="star">*</span>
			</logic:equal>
			</td>
		</tr>
		<tr>
			<td>
				<html:textarea property="blackListReason"  name="selectionProcessForm" rows="7" cols="80"></html:textarea>
			</td>
		</tr>
		<tr>
			<td>
				<div class="navBtn" style="float: right;"><a href="#" style="width:90px;" class="active" onclick="javascript: blackList();"><span class="rightC"></span><span class="leftC"></span>
				<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>" name="selectionProcessForm" property="applicantStatus">
					<bean:message key="black_list.label.blackList"/>
				</logic:equal>
				<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>" name="selectionProcessForm" property="applicantStatus">
					<bean:message key="black_list.label.unBlackList"/>
				</logic:equal>
				</a>
				<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(true);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
		</table>
	</div>	
	</html:form>
	</div>
</div>
<script LANGUAGE="JavaScript">
function setPopupTitle(){
	var title ='<b>';
	<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>" name="selectionProcessForm" property="applicantStatus">
		title +='<bean:message key="black_list.label.blackList"/>';
	</logic:equal>
	<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>" name="selectionProcessForm" property="applicantStatus">
		title +='<bean:message key="black_list.label.unBlackList"/>';
	</logic:equal>
	title += '- </b>';
    title += '<bean:write name="selectionProcessForm" property="applicantName"/> &nbsp';
    // source need to be added
	window.top.setPopTitle(title);
}
function blackList(){
	var blackListReason = document.selectionProcessForm.blackListReason.value;
	if(blackListReason==''){
		alert('<bean:message key="selection_feedback.error.mandatory_fields" />');
		return false;
	}else{
		var positionId = '<bean:write property="positionId" name="selectionProcessForm"/>';
		if(positionId==''){
			document.selectionProcessForm.submit();
		}else {
			if(confirm('<bean:message key="black_list.confirm.applicant_rejection" />'))
				document.selectionProcessForm.submit();
		}
	}
}
window.onload = setPopupTitle;
</script>