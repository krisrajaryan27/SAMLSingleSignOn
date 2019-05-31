<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<div class="contentDivPop" style="width:500px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
			<table id="m_errortable" > 
				<tr>
			    <td class='header'>
		        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
				</tr>
		    <tr>
	        <td class="message"><html:errors/></td>               
		    </tr>
			</table><br/><br/>
	<%
		}
	%>
	<div class="outerDiv" style="border:0px;">
	<b><bean:message key="feedback_form_edit_title.label.edit"/>
	<span class="star">*</span></b>
	<br/><br/>
	<html:form action="/feedbackform" focus="feedbackFormTitle">
  	<html:hidden property="mode" name="feedbackForm"/>
	<html:hidden property="feedbackFormId" name="feedbackForm"/>
	<html:hidden property="feedbackFormDesc" name="feedbackForm"/>
	<html:hidden property="strFeedbackForm" name="feedbackForm"/>
	<html:hidden property="isSubmitted" name="feedbackForm"/>
	<html:hidden property="isCancelled" name="feedbackForm"/>
	<html:hidden property="prevFeedbackFormTitle" name="feedbackForm"/>
	<html:hidden property="displayType" name="feedbackForm" />
	
		<table class="tblPop">
		<tr>
			<td class="header">
				<html:text property="feedbackFormTitle" size="50" maxlength="255"></html:text>
			</td>
		</tr>
		</table>
		<div class="navBtn" style="float: left;padding-top: 30px;">
		<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.ok"/></a>
		<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript:cancelForm() ;return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
		</div>
	</html:form>	
</div>
</div>

<script type="text/javascript">
<!--
function submitForm(){
	if(document.feedbackForm.feedbackFormTitle.value.trim()==''){
		alert('<bean:message key="feedback_form_edit_title.error.enter_name"/>');
		document.feedbackForm.feedbackFormTitle.focus();
		return;
	}
	document.feedbackForm.isSubmitted.value="1";
	document.feedbackForm.submit();
}
function cancelForm(){
	document.feedbackForm.isCancelled.value="1";
	document.feedbackForm.submit();
}

function actionOnLoad(){
	window.top.setPopTitle('<b><bean:message key="feedback_form_edit_title.label.edit"/></b>');
}
window.onload=actionOnLoad;
//-->
</script>
