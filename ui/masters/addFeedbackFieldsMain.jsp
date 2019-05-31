<%@page import="com.talentPool.masters.constants.FeedbackFieldsConstant"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script type="text/javascript">
var radioImg = 'images/radiobutton.gif';
var chckdRadioImg = 'images/checkedradiobutton.gif';
</script>
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
	<div class="outerDiv">
	<html:form action="/masters" onsubmit="submitForm();return false;">
  	<html:hidden property="feedbackFieldCategoryId" name="mastersForm"/>
  	<html:hidden property="feedbackFieldId" name="mastersForm"/>
  	<html:hidden property="feedbackFieldType" name="mastersForm"/>
  	<html:hidden property="applicantFieldId" name="mastersForm"/>
  	<html:hidden property="mode" name="mastersForm"/>
  	<html:hidden property="submitted" name="mastersForm" value="1"/>
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="master_add_feedback_fields.label.feedback_field_type"/>:	
			</td>
			<td>
				<logic:equal name="mastersForm" property="feedbackFieldType" value="<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>">
					<img src="images/radiobutton.gif" name='feedbackFieldTypeImg' id='img_<%=FeedbackFieldsConstant.FIELD_TYPE_NORMAL%>' onclick="javascript:toggleFieldType(this,'<%=FeedbackFieldsConstant.FIELD_TYPE_NORMAL%>');"  style="cursor: pointer;" />&nbsp;<bean:message key="feedback_fields.label.field_type_normal"/>
					<img src="images/checkedradiobutton.gif" name='feedbackFieldTypeImg' id='img_<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>' onclick="javascript:toggleFieldType(this,'<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>');" style="cursor: pointer;" />&nbsp;<bean:message key="common.applicant"/>
				</logic:equal>	
				<logic:notEqual name="mastersForm" property="feedbackFieldType" value="<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>">
					<img src="images/checkedradiobutton.gif" name='feedbackFieldTypeImg' id='img_<%=FeedbackFieldsConstant.FIELD_TYPE_NORMAL%>' onclick="javascript:toggleFieldType(this,'<%=FeedbackFieldsConstant.FIELD_TYPE_NORMAL%>');" style="cursor: pointer;" />&nbsp;<bean:message key="feedback_fields.label.field_type_normal"/>
					<img src="images/radiobutton.gif" name='feedbackFieldTypeImg' id='img_<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>' onclick="javascript:toggleFieldType(this,'<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>');" style="cursor: pointer;" />&nbsp;<bean:message key="common.applicant"/>
				</logic:notEqual>
			</td>
		</tr>
		<tr id="applicantFieldsRow" >
			<td class="header">
				<bean:message key="common.applicant" />&nbsp;<bean:message key="common.field" />:
			</td>
			<td>
				<logic:notEmpty name="applicantFieldJsArray" scope="request" >
					<script type="text/javascript">
						var applicantFields = <bean:write name="applicantFieldJsArray" filter="false" scope="request" />;
						var applicantFieldsJsArray = new SelectBox(applicantFields,'<bean:write property="applicantFieldId" name="mastersForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
						//applicantFieldsJsArray.setOnChangeHandler('onChangeApplicant');
						document.write(applicantFieldsJsArray.getHtml());
						applicantFieldsJsArray.init();
					</script>
				</logic:notEmpty>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="master_add_feedback_fields.label.feedback_field_title"/>
				<span class="star">*</span>
				:
			</td>
			<td>
				<html:text property="feedbackFieldTitle" name="mastersForm" size="71" styleId="skillCategory" maxlength="255"></html:text>
			</td>	
		</tr>
		<tr>
			<td class="header" style="vertical-align: top;">
				<bean:message key="master_add_feedback_fields.label.feedback_field_desc"/>:
			</td>
			<td>
				<html:textarea property="feedbackFieldDesc" rows="3" cols="68"></html:textarea>
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
	</html:form>	
</div>
</div>
<script type="text/javascript">
<!--
function submitForm(){
	var Name = document.mastersForm.feedbackFieldTitle.value;
	if(Name.trim()==""){
		return;
	}
	if(document.mastersForm.feedbackFieldType.value == ''){
		document.mastersForm.feedbackFieldType.value = '<%=FeedbackFieldsConstant.FIELD_TYPE_NORMAL%>';
	}
	if(document.mastersForm.feedbackFieldType.value == '<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>'){
		document.mastersForm.applicantFieldId.value=applicantFieldsJsArray.getSelectedId();
	}else{
		document.mastersForm.applicantFieldId.value='';
	}
	document.mastersForm.submit();
}
function actionOnLoad(){
	<logic:empty name="mastersForm" property="feedbackFieldId" >
		window.top.setPopTitle('<b><bean:message key="master_add_feedback_fields.label.add_field"/></b>');
	</logic:empty>
	<logic:notEmpty name="mastersForm" property="feedbackFieldId">
		window.top.setPopTitle('<b><bean:message key="master_add_feedback_fields.label.edit_field"/></b>');
	</logic:notEmpty>
	if(document.mastersForm.feedbackFieldType.value == '<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>'){
		$('applicantFieldsRow').show();
	}else {
		$('applicantFieldsRow').hide();
	}
}
function toggleFieldType(obj,fieldType){
	var imgs = document.getElementsByName(obj.name);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img") > -1) {
			if(theImage.id == 'img_'+fieldType){
				theImage.src = "images/checkedradiobutton.gif";
				document.mastersForm.feedbackFieldType.value=fieldType;
			}else{
				theImage.src = "images/radiobutton.gif";
			}
		}
		if(fieldType=='<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>'){
			$('applicantFieldsRow').show();
		}else {
			$('applicantFieldsRow').hide();
		}
	}
}
window.onload=actionOnLoad;
//-->
</script>