<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals"%>
<html:form action="/position">
<html:hidden property="mode" value="updateDraftName"/>
<div class="contentDivPop" style="width:450px;">
	<% if (request.getAttribute(Globals.ERROR_KEY) != null) { %>
	<table id="m_errortable">
		<tr>
			    <td class='header'>
		        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
		</tr>
		<tr>
			<td class="message"><html:errors /></td>
		</tr>
	</table>
	<br>	
	<% } %>	
	<div class="outerDiv">			
		<div class="popupTop">
			<table class="tblPop">
				   <tr>
					  <td class="header">
						  <bean:message key="position.draft.home.draft_name"/>:
					  </td>
					  <td>
						<html:text name="positionForm" property="draftName" size="50" />
					  </td>
				  </tr>
			 </table>
		 </div>
		 <div class="popupBody">
			<table class="tblPop" width="100%">
			<tr>
				<td>
				<div class="navBtn" style="float: right;">
				<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
				<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
				</td>
			</tr>
			</table>
		</div>
  	</div>	 
</div>
</html:form>
<script language="javascript">
var returnVal;
function submitForm(){
	if (validateForm()) {
		document.positionForm.submit();
	}
}

function validateForm(){
	var draftName = document.positionForm.draftName.value;
	if(draftName==""){
		alert('<bean:message key="position.draft.message.please_enter_name" />');
		return false;
	}else {
	  	var re = new RegExp('\\b'+'<bean:message key="common.position_draft" />'+'\\b \\b\\d{1,2}\\b');
		if (draftName.match(re)) {
		  alert('<bean:message key="position.draft.error.rename_draft" />');
		  return false;		  
		}
	}
	return true;
}

function actionOnLoad(){
	var title = '<b><bean:message key="title.common" /></b>';
	window.top.setPopTitle(title);

	setReturnValue();
}
function setReturnValue(){
	<logic:present name="update" scope="request">
		returnVal=document.positionForm.draftName.value;
		window.top.hidePopWin(true);
	</logic:present>
}

window.onload=actionOnLoad;
</script>