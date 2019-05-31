<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,com.talentPool.selectionProcess.SelectionProcessConstants,
                com.talentPool.selectionProcess.form.SelectionProcessForm,
                com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                com.talentPool.selectionProcess.dataobject.FeedbackData" %>
<logic:present name="update" scope="request">
<script>
	window.top.hidePopWin(true);
</script>
</logic:present>                
<logic:notPresent name="update" scope="request">
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<bean:define id="feedbackData" name="feedbackData" scope="request" type="FeedbackData" />
<bean:define id="selectionProcessForm" name="selectionProcessForm" scope="request" type="SelectionProcessForm" />
<div class="contentDivPop"  >
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
<% } else { %>
<html:form action="/selectionProcess">
  <html:hidden property="mode"/>
  <html:hidden property="applicantId" name="selectionProcessForm"/>
  <html:hidden property="traitIds" name="selectionProcessForm"/>
  <html:hidden property="currentPositionStepId" value='<%=""+feedbackData.getFromStepData().getStepId()%>' />
  <html:hidden property="nextPositionStepId" value="<%=SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT%>"/>
  <html:hidden property="communicationId" name="selectionProcessForm"/>
  <html:hidden property="positionId" name="feedbackData"/>
	<div class="bottomDivSec" style="width:730px;">
	<div class="vpTop" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
      <tr>
        <td><strong id="VP_TITLE" class="Grey"><bean:write name="feedbackData" property="applicantName"/>&nbsp;	 				
				<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
					<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="feedbackData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/></strong>
				<%} %>
		</td>
      </tr>
    </table>
	</div>
	</div>
	<div class="outerDiv" style="border-top:0px;">
		<div class="popupTop">
			<table class="tblPop" >
				<tr>
		      <td class="header"><bean:message key="common.position"/></td>
		      <td><bean:write name="feedbackData" property="positionTitle"/></td>
		   	</tr>
		   	<tr>
          <td style="height:5px;"></td>
       	</tr>
		    <tr>
		       <td class="header"><bean:message key="selection_feedback.label.selectionStep"/></td>
		       <td><bean:write name="feedbackData" property="fromStepData.stepTitle"/></td>
		    </tr>
	    </table>
		</div>
		<div class="popupTop">
			<table class="tblPop" >
				<tr>
	        <td class="header"><bean:message key="selection_feedback.label.editSelectionStep"/></td>
	     	</tr>
	     	<tr>                          
          <td>
            <img src="images/checkedradiobutton.gif" />                
            &nbsp;<bean:message key="selection_feedback.label.notInterestedReject"/>
          </td>      
       </tr>       
		</table>	
	</div>
	<logic:notEmpty name="selectionProcessForm" property="traitIds">
	<div class="popupBody">
			<table class="tblPop" width="100%">
				<tr>
					<td>				
						<div class="outerDiv" style="width:700px;height:250px;overflow: auto; background-color: #ffffff; padding: 10px;">
							<table cellpadding="0" cellspacing="0" class="tblPop"> 
								<tr>
		              <tr>
		    						<td class="Grey" width="100" valign="top"><%=SelectionProcessConstants.COMMENT%>:</td>  												    								    						
										<td valign="top" ><textarea rows="2" cols="50" id="<%=SelectionProcessConstants.TRAIT_ + selectionProcessForm.getTraitIds()%>" name="<%=SelectionProcessConstants.TRAIT_ + selectionProcessForm.getTraitIds()%>"  onkeypress="javascript: return noenter();" class="txtArea"></textarea></td>
    	 					  </tr>
							</table>						
					</div>
				</td>
			</tr>
		</table>
	</div>
	</logic:notEmpty>
	<div class="navBtn" style="float: right;"><br/>
		<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: saveChanges();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
		<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
		</div>
</html:form>
<script language="JavaScript">
function setPopupTitle(){
	var title = '<b><bean:message key="selection_feedback.label.submit_feedback"/> - </b><bean:write name="feedbackData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
   		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="feedbackData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}
window.onload = setPopupTitle;

function saveChanges() {
  document.selectionProcessForm.mode.value = 'saveSelectionProcessResult'; 
  document.selectionProcessForm.submit();
  return true;
}
</script>
<% } %>
</logic:notPresent>
