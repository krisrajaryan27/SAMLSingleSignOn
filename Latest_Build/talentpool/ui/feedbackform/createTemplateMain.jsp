<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.masters.constants.FeedbackFormConstants"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="java.util.ArrayList"%>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	

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
<script type="text/javascript">
var selectBoxFeedbackForms=null;
</script>	
	<div class="outerDiv" style="border:0px;">
	<html:form action="/feedbackform" focus="feedbackFormTitle">
  	<html:hidden property="mode" name="feedbackForm"/>
  	<html:hidden property="optionCreateAs" name="feedbackForm"/>
  	<html:hidden property="copyFromId" name="feedbackForm"/>
  	<html:hidden property="displayType" name="feedbackForm"/>
  	
		<table class="tblPop">
		<tr>
			<td style="font-size: 14px; font-weight: bold;">
			<b><bean:message key="feedback_form.title.add_new"/></b>
			<br/><br/><br/>
			</td>
		</tr>
		</table>
		<table class="tblPop">
		<tr>
			<td>
				<bean:message key="feedback_form.label.new_form_name"/>
				<span class="star">*</span>
				:
			</td>
			<td>
				<html:text property="feedbackFormTitle" size="50" maxlength="255"></html:text>
			</td>
		</tr>
		</table>
		<br>
		<table class="tblPop">
		<tr>
			<td class="header">
				<img  src="images/radiobutton.gif" name="rdo" id='img_<%=FeedbackFormConstants.CREATE_NEW_FORM %>'
				onclick="onRadioChange('rdo','<%=FeedbackFormConstants.CREATE_NEW_FORM %>');" >&nbsp;<bean:message key="feedback_form.label.new_form_from_scratch"/>
			</td>
		</tr>
		</table>
		<div id="divDisplayType" style="padding: 10px 0px 10px 15px;">
		<table class="tblPop">
		<tr>
			<td>
				&nbsp;&nbsp;&nbsp;
				<img  src="images/radiobutton.gif" name="rdo2" id='img1_<%=FeedbackFormConstants.GENERALISED %>'
				onclick="onRadioChange('rdo2','<%=FeedbackFormConstants.GENERALISED %>');" >&nbsp;<bean:message key="feedback_form.label.generalised"/>
				&nbsp;&nbsp;
				<img  src="images/radiobutton.gif" name="rdo2" id='img1_<%=FeedbackFormConstants.COMPACT %>'
				onclick="onRadioChange('rdo2','<%=FeedbackFormConstants.COMPACT %>');" >&nbsp;<bean:message key="feedback_form.label.compact"/>				
			</td>
		</tr>
		</table>
		</div>		
		<table class="tblPop">
		<tr>
			<td class="header" style="padding-top: 15px;">
				<img  src="images/radiobutton.gif" name="rdo" id='img_<%=FeedbackFormConstants.CREATE_FROM_EXISTING_FORM %>'
				onclick="onRadioChange('rdo','<%=FeedbackFormConstants.CREATE_FROM_EXISTING_FORM %>');" >&nbsp;<bean:message key="feedback_form.label.from_existing_form"/>
			</td>
		</tr>
		</table>
		<div id="divExisting" style="padding: 10px 0px 10px 15px;">
		<table class="tblPop">
		<tr>
			<td>
				<script language="JavaScript">
					<%
					ArrayList feedbackFormIds = (ArrayList)request.getAttribute("feedbackFormIds");
					ArrayList feedbackFormNames = (ArrayList)request.getAttribute("feedbackFormNames");
					%>
					var options = <%=CommonUtils.getListJavaScriptArray(feedbackFormIds,feedbackFormNames)%>;
					var m = [new SelectOption('-1','<bean:message key="feedback_form.label.select_feedback_form"/>')];
					options = m.concat(options);
					
					selectBoxFeedbackForms = new SelectBox(options,'-1','images/btn_dropdown.gif',{namesonly:false, width:'350px', size:15});
					document.write(selectBoxFeedbackForms.getHtml());
					selectBoxFeedbackForms.init();
				</script>
			</td>
		</tr>
		</table>
		</div>
			<div class="navBtn" style="float: left;padding-top: 40px;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
	</html:form>	
</div>
</div>

<script type="text/javascript">
<!--
function submitForm(){
		if(document.feedbackForm.feedbackFormTitle.value.trim()==''){
			alert('<bean:message key="feedback_form.error.please_enter_name"/>');
			document.feedbackForm.feedbackFormTitle.focus();
			return;
		}
	if(document.feedbackForm.optionCreateAs.value=='<%=FeedbackFormConstants.CREATE_FROM_EXISTING_FORM%>'){
		if(selectBoxFeedbackForms.getSelectedId()=='-1'){
			alert('<bean:message key="feedback_form.error.please_select_feedback_form"/>');
			selectBoxFeedbackForms.setFocus();
			return;
		}
	}
	document.feedbackForm.copyFromId.value=selectBoxFeedbackForms.getSelectedId();
	document.feedbackForm.submit();
}
function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			var imgIdPrefix = '';
			if (theImage.id.indexOf("img1") > -1) {
				imgIdPrefix = 'img1_';
			} else if (theImage.id.indexOf("img") > -1) {			
				imgIdPrefix = 'img_';
			}
			if( theImage.id == imgIdPrefix+attachmentId){
				theImage.src = "images/checkedradiobutton.gif";
			}else{
				theImage.src = "images/radiobutton.gif";
			}
	}
	if(imgGroupName == 'rdo') {
		if(attachmentId=='<%=FeedbackFormConstants.CREATE_NEW_FORM%>'){
			$('divExisting').hide();
			$('divDisplayType').show();
		}else{
			$('divDisplayType').hide();
			$('divExisting').show();
		}
		document.feedbackForm.optionCreateAs.value=attachmentId;
	}else {	
		document.feedbackForm.displayType.value=attachmentId;
	}	
}

function actionOnLoad(){
	onRadioChange('rdo',document.feedbackForm.optionCreateAs.value);
	onRadioChange('rdo2',document.feedbackForm.displayType.value);
	window.top.setPopTitle('<b><bean:message key="feedback_form.title.add_new"/></b>');
}
window.onload=actionOnLoad;
//-->
</script>
