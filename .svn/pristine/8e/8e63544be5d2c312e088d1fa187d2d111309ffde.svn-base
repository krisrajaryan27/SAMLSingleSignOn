<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.applicant.dataobject.ApplicantData,
				com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                  com.talentPool.selectionProcess.SelectionProcessConstants"%>

<div class="contentDivPop" style="width: 500px;">
	<div class="outerDiv">
	
	<div class="popupTop">
		<table class="tblPop" width="100%">
		<tr>
			<td class="header" width="70">
                 <logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_PHONE %>' property="communicationType" name="selectionProcessForm">
                   <bean:message key="view_phone.label.called_by"/>
                 </logic:equal>	
	            <logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_NOTE%>' property="communicationType" name="selectionProcessForm">
		          <bean:message key="view_phone.label.note_by"/>
	            </logic:equal>                            
			</td>
			<td>
				<bean:write name="communicationData" property="name" scope="request"/>
			</td>
			<td class="header right" width="70">
			<bean:message key="view_phone.label.date_time"/>
			</td>
			<td width="170">
				<bean:write name="communicationData" property="communicationDateToDisplay" scope="request" />
			</td>
		</tr>
	   </table>	
		<logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_PHONE %>' property="communicationType" name="selectionProcessForm">
		<table class="tblPop" width="100%">
        <tr>
          <td class="header" width="105"><bean:message key="view_phone.label.number_called"/></td>
  		  <td><bean:write name="communicationData" property="communicationPhoneNo" scope="request"/></td> 			                
        </tr>
		</table>
        </logic:equal>
	</div>
	<div class="popupBody">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="add_note.label.note"/>
			</td>
		</tr>
		<tr>
			<td>
			<html:textarea property="communicationText"  name="communicationData" readonly="true" rows="10" cols="90" ></html:textarea>
			</td>
		</tr>
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<logic:notPresent name="nonEditable" scope="request">
			  <a href="#" style="width:60px; margin-right: 5px;" class="active" onclick="javascript: editInteraction();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
			  </logic:notPresent>
			  <a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>	
	</div>
</div>

<script language="JavaScript">
function editInteraction(){
	var url = 'selectionProcess.do?mode=addPhoneLog&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>&communicationType=<bean:write property="communicationType" name="selectionProcessForm"/>&communicationId=<bean:write property="communicationId" name="selectionProcessForm"/>';
	window.location=uncache(url);	
}
function onWinLoad(){
	var title = '<b><bean:message key="view_phone.label.phone"/> - </b>';
    <logic:equal value='<%=""+SelectionProcessConstants.INTERACTION_NOTE %>' property="communicationType" name="selectionProcessForm">
      title = '<b><bean:message key="view_note.label.note"/> - </b>';
    </logic:equal>
	var title = '<bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
window.top.setPopTitle(title);
}
function uncache(url){
	var d = new Date();
	var time = d.getTime();
	return url + '&ta='+time;
} 

window.onload = onWinLoad;
</script>