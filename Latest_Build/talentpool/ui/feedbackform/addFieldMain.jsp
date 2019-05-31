<%@page import="com.talentPool.masters.constants.FeedbackFieldsConstant"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.FeedbackFormConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript">
	var selectboxCategory=null;
	var selectboxFields=null;
	var selectboxRatings=null;
	var selectboxMultipleSelects=null;
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
	<div class="outerDiv" style="border:0px;">
	<html:form action="/feedbackform">
  	<html:hidden property="mode" name="feedbackForm"/>
	<html:hidden property="feedbackFormId" name="feedbackForm"/>
	<html:hidden property="feedbackFormDesc" name="feedbackForm"/>
	<html:hidden property="feedbackFormTitle" name="feedbackForm"/>
	<html:hidden property="strFeedbackForm" name="feedbackForm"/>
	<html:hidden property="isSubmitted" name="feedbackForm"/>
	<html:hidden property="isCancelled" name="feedbackForm"/>
	
	<html:hidden property="feedbackFormFieldCategory" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldCategoryId" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldCommentRequired" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldRatingId" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldId" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldRatingRequired" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldType" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldIndex" name="feedbackForm"/>
	<html:hidden property="feedbackFieldType" name="feedbackForm" />
	<html:hidden property="applicantFieldId" name="feedbackForm" />
	<html:hidden property="feedbackFieldId" name="feedbackForm" />
	<html:hidden property="fieldDisplayType" name="feedbackForm"/>
	<html:hidden property="fieldIsMandatory" name="feedbackForm" />
	<html:hidden property="displayType" name="feedbackForm" />
	<html:hidden property="feedbackFormFieldMultipleSelectRequired" name="feedbackForm"/>
	<html:hidden property="feedbackFormFieldMultipleSelectId" name="feedbackForm"/>
	<table class="posinput">
		<tr>
			<td style="font-size: 14px; font-weight: bold;">
				<logic:empty name="feedbackForm" property="feedbackFormFieldIndex">
					<bean:message key="feedback_form_add_field.label.add"/>
				</logic:empty>
				<logic:notEmpty name="feedbackForm" property="feedbackFormFieldIndex">
					<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_FIELD %>" name="feedbackForm" property="feedbackFormFieldType">
						<bean:message key="feedback_form_add_field.label.edit"/>
					</logic:equal>
				</logic:notEmpty>
				<br/><br/>
			</td>
		</tr>
		<logic:empty name="feedbackForm" property="feedbackFormFieldIndex">
		<tr>
			<td class="label">
				<bean:message key="feedback_form_add_field.label.category"/>
			</td>
		</tr>
		<tr>
			<td>
				<script language="JavaScript">
					<%
					ArrayList categoryIds= (ArrayList)request.getAttribute("categoryIds");
					ArrayList categoryNames = (ArrayList)request.getAttribute("categoryNames");
					%>
					var options = <%=CommonUtils.getListJavaScriptArray(categoryIds,categoryNames)%>;
					var m = [new SelectOption('-1','<bean:message key="feedback_form_add_field.label.select_category"/>')];
					options = m.concat(options);
					
					selectboxCategory = new SelectBox(options,'<bean:write name="feedbackForm" property="feedbackFormFieldCategoryId"/>','images/btn_dropdown.gif',{namesonly:false, width:'300px', size:15});
					document.write(selectboxCategory.getHtml());
					selectboxCategory.setOnChangeHandler('onChangeCategory');
					selectboxCategory.init();
				</script>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key="feedback_form_add_field.label.field"/>
			</td>
		</tr>
		<tr>
			<td>
				<html:hidden property="feedbackFieldTitle" name="feedbackForm"/>
				<script language="JavaScript">
					var options = [new SelectOption('-1','<bean:message key="feedback_form_add_field.label.select_field"/>')];
					selectboxFields = new SelectBox(options,'-1','images/btn_dropdown.gif',{namesonly:false, width:'430px', size:15});
					document.write(selectboxFields.getHtml());
					selectboxFields.setOnChangeHandler('onChangeField');
					selectboxFields.init();
				</script>
				
			</td>
		</tr>
		</logic:empty>
		<logic:notEmpty name="feedbackForm" property="feedbackFormFieldIndex">
			<tr>
				<td class="label">
					<bean:message key="feedback_form_add_field.label.title"/>
				</td>
			</tr>
			<tr>
				<td>
					<bean:write property="feedbackFieldTitle" name="feedbackForm"/>
				</td>
			</tr>
		</logic:notEmpty>
	</table>
	<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_FIELD %>" name="feedbackForm" property="feedbackFormFieldType">
	<div id="fieldOptions" >
		<table>	
			<tr>
				<td class="label">
					<bean:message key="feedback_form_add_field.label.desc"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					<html:textarea property="feedbackFormFieldDesc" rows="5" cols="82"></html:textarea>
				</td>
			</tr>
			<tr>
				<td style="height: 10px;">
					
				</td>
			</tr>
			<tr>
				<td>
					<img src="images/checkboxunchecked.gif" onclick="onClickRatings();" id="chkRatings" style="margin-bottom: -1px;"/>&nbsp;&nbsp;&nbsp;<bean:message key="feedback_form_add_field.label.rating"/>
				</td>
			</tr>
			<tr>
				<td style="padding-left: 30px;">
					<script language="JavaScript">
						<%
						ArrayList ratingIds= (ArrayList)request.getAttribute("ratingIds");
						ArrayList ratingNames = (ArrayList)request.getAttribute("ratingNames");
						%>
						var options = <%=CommonUtils.getListJavaScriptArray(ratingIds,ratingNames)%>;
						var m = [new SelectOption('-1','<bean:message key="feedback_form_add_field.label.select_rating"/>')];
						options = m.concat(options);
						
						selectboxRatings = new SelectBox(options,'<bean:write name="feedbackForm" property="feedbackFormFieldRatingId"/>','images/btn_dropdown.gif',{namesonly:false, width:'270px', size:10});
						document.write(selectboxRatings.getHtml());
						selectboxRatings.init();
					</script>
				</td>
			</tr>		
			<tr>
				<td>
					<img src="images/checkboxunchecked.gif" onclick="onClickMultipleSelects();" id="chkMultiSelects" style="margin-bottom: -1px;"/>&nbsp;&nbsp;&nbsp;<bean:message key="feedback_form_add_field.label.multiple_select"/>
				</td>
			</tr>
			<tr>
				<td style="padding-left: 30px;">
					<script language="JavaScript">
						<%
						ArrayList multipleSelectIds= (ArrayList)request.getAttribute("multipleSelectIds");
						ArrayList multipleSelectNames = (ArrayList)request.getAttribute("multipleSelectNames");
						%>
						var options = <%=CommonUtils.getListJavaScriptArray(multipleSelectIds,multipleSelectNames)%>;
						var m = [new SelectOption('-1','<bean:message key="feedback_form_add_field.label.select_multiple_select"/>')];
						options = m.concat(options);
						
						selectboxMultipleSelects = new SelectBox(options,'<bean:write name="feedbackForm" property="feedbackFormFieldMultipleSelectId"/>','images/btn_dropdown.gif',{namesonly:false, width:'270px', size:10});
						document.write(selectboxMultipleSelects.getHtml());
						selectboxMultipleSelects.init();
					</script>
				</td>
			</tr>		
			<tr>
				<td>
					<img src="images/checkboxunchecked.gif" onclick="onClickCommentRequired();" id="chkCommentRequired" style="margin-bottom: -1px;"/>&nbsp;&nbsp;&nbsp;<bean:message key="feedback_form_add_field.label.comment"/>
				</td>
			</tr>
			<tr>
				<td>
					<img src="images/checkboxunchecked.gif" onclick="onClickFieldRequired();" id="chkFieldRequired" style="margin-bottom: -1px;"/>&nbsp;&nbsp;&nbsp;<bean:message key="feedback_form_add_field.label.is_required"/>
				</td>
			</tr>
		</table>
	</div>
	<div id="fieldDisplayTypeDiv" >
		<table>
			<tr>
				<td class="label">
					<bean:message key="feedback_form_add_field.label.display_type"/>
				</td>
			</tr>
			<tr>
				<td>
					<img  src="images/radiobutton.gif" name="rdo2" id='img1_<%=FeedbackFormConstants.GENERALISED %>'
					onclick="onRadioChange('rdo2','<%=FeedbackFormConstants.GENERALISED %>');" style="cursor: pointer;" >&nbsp;<bean:message key="feedback_form_add_field.label.generalised"/>
					&nbsp;&nbsp;
					<img  src="images/radiobutton.gif" name="rdo2" id='img1_<%=FeedbackFormConstants.COMPACT %>'
					onclick="onRadioChange('rdo2','<%=FeedbackFormConstants.COMPACT %>');"  style="cursor: pointer;" >&nbsp;<bean:message key="feedback_form_add_field.label.compact"/>				
				</td>
			</tr>
		</table>
	</div>
	</logic:equal>
	<div class="navBtn" style="float: left;padding-top: 30px;">
	<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.ok"/></a>
	<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript:cancelForm() ;return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
	</div>
	</html:form>	
</div>
</div>
<script type="text/javascript">
<!--
function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			var imgIdPrefix = '';
			if (theImage.id.indexOf("img1") > -1) {
				imgIdPrefix = 'img1_';
			} 
			if( theImage.id == imgIdPrefix+attachmentId){
				theImage.src = "images/checkedradiobutton.gif";
			}else{
				theImage.src = "images/radiobutton.gif";
			}
	}
	document.feedbackForm.fieldDisplayType.value=attachmentId;
}
function onClickCommentRequired(){
	var fld = document.feedbackForm.feedbackFormFieldCommentRequired;
	if(fld.value=='<%=FeedbackFormConstants.RATING_NOT_REQUIRED%>' || fld.value==''){
		changeState($('chkCommentRequired'),fld, '<%=FeedbackFormConstants.RATING_REQUIRED%>');
	}else{
		changeState($('chkCommentRequired'),fld, '<%=FeedbackFormConstants.RATING_NOT_REQUIRED%>');
	}
}
function onClickFieldRequired() {
	var fld = document.feedbackForm.fieldIsMandatory;
	if(fld.value=='<%=FeedbackFormConstants.FIELD_NOT_REQUIRED%>' || fld.value==''){
		changeState($('chkFieldRequired'),fld, '<%=FeedbackFormConstants.FIELD_REQUIRED%>');
	}else{
		changeState($('chkFieldRequired'),fld, '<%=FeedbackFormConstants.FIELD_NOT_REQUIRED%>');
	}
}
function onClickRatings(){
	var fld = document.feedbackForm.feedbackFormFieldRatingRequired;
	if(fld.value=='<%=FeedbackFormConstants.RATING_NOT_REQUIRED%>' || fld.value==''){
		changeState($('chkRatings'),fld, '<%=FeedbackFormConstants.RATING_REQUIRED%>');
	}else{
		changeState($('chkRatings'),fld, '<%=FeedbackFormConstants.RATING_NOT_REQUIRED%>');
	}
}

function onClickMultipleSelects(){
	var fld = document.feedbackForm.feedbackFormFieldMultipleSelectRequired;
	if(fld.value=='<%=FeedbackFormConstants.MULTIPLE_SELECT_NOT_REQUIRED%>' || fld.value==''){
		changeState($('chkMultiSelects'),fld, '<%=FeedbackFormConstants.MULTIPLE_SELECT_REQUIRED%>');
	}else{
		changeState($('chkMultiSelects'),fld, '<%=FeedbackFormConstants.MULTIPLE_SELECT_NOT_REQUIRED%>');
	}
}
function changeState(ctl,fld,state){
	if(state=='1'){
		ctl.src='images/checkboxchecked.gif';
	}else{
		ctl.src='images/checkboxunchecked.gif';
	}
	fld.value=state;
}

function onChangeCategory(newIdx){
	var selId = selectboxCategory.getSelectedId();
	if (selId!=null && selId!='-1') {
		var pars = "mode=getCategoryFields&feedbackFormFieldCategoryId=" + selId;	
		var myAjax = ajaxCall("feedbackform.do",'get',pars,onChangeCategoryResponse, reportError);	
	}else{
		document.feedbackForm.feedbackFormFieldDesc.value='';
		selectboxFields.setSelected(selectboxFields.getIndexWithId('-1'));
	}
}

function onChangeCategoryResponse(request) {
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="feedback_form_add_field.error.unable_to_load_fields"/>');
		return;
	}
	var options= xmlFile.getElementsByTagName("fields")[0].firstChild.nodeValue;
	var m = [new SelectOption('-1','<bean:message key="feedback_form_add_field.label.select_field"/>')];
	options = m.concat(eval(options));
	selectboxFields.reInitialize(options,'<bean:write property="feedbackFieldId" name="feedbackForm"/>');
	$('fieldOptions').show();	
}

function onChangeField(newIdx){
	var selId = selectboxFields.getSelectedId();
	if (selId!=null && selId!='-1') {
		var pars = "mode=getCategoryFieldDesc&feedbackFieldId=" + selId;	
		var myAjax = ajaxCall("feedbackform.do",'get',pars,onChangeFieldResponse, reportError);	
	}else{
		document.feedbackForm.feedbackFormFieldDesc.value='';
	}
}

function onChangeFieldResponse(request) {
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="feedback_form_add_field.error.unbale_to_load_desc"/>');
		return;
	}
	var fieldDesc = getSingleElement(xmlFile,'desc',''); //xmlFile.getElementsByTagName("desc")[0].firstChild.nodeValue;
	var feedbackFieldType = getSingleElement(xmlFile,'feedbackFieldType','');
	var applicantFieldId = getSingleElement(xmlFile,'applicantFieldId','');
	document.feedbackForm.feedbackFormFieldDesc.value=fieldDesc;
	document.feedbackForm.feedbackFieldType.value=feedbackFieldType;
	document.feedbackForm.applicantFieldId.value=applicantFieldId;
	toggleFieldOptions(feedbackFieldType);
}

function toggleFieldOptions(feedbackFieldType){
	if(feedbackFieldType=='<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>'){
		document.feedbackForm.feedbackFormFieldCommentRequired.value='<%=FeedbackFormConstants.COMMENT_REQUIRED%>';
		// document.feedbackForm.fieldDisplayType.value='<%=FeedbackFormConstants.COMPACT %>';
		$('fieldOptions').hide();
	}else {
		$('fieldOptions').show();
		// onRadioChange('rdo2','<%=FeedbackFormConstants.GENERALISED %>');
	}
}


function submitForm(){
	<logic:empty name="feedbackForm" property="feedbackFormFieldIndex">
	if(!validAdd()){
		return false;
	}
	document.feedbackForm.feedbackFormFieldCategoryId.value=selectboxCategory.getSelectedId();
	document.feedbackForm.feedbackFormFieldCategory.value=selectboxCategory.getText(selectboxCategory.getSelectedIndex());
	document.feedbackForm.feedbackFieldId.value=selectboxFields.getSelectedId();
	document.feedbackForm.feedbackFieldTitle.value=selectboxFields.getText(selectboxFields.getSelectedIndex());
	</logic:empty>
	<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_FIELD %>" name="feedbackForm" property="feedbackFormFieldType">
		var feedbackFieldType = document.feedbackForm.feedbackFieldType.value;
		if(feedbackFieldType!='<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>'){
			if(!validRatingOrComment()){
				return false;
			}		
			document.feedbackForm.feedbackFormFieldRatingId.value=selectboxRatings.getSelectedId();
			document.feedbackForm.feedbackFormFieldMultipleSelectId.value=selectboxMultipleSelects.getSelectedId();
		}
	</logic:equal>
	
	document.feedbackForm.isSubmitted.value="1";
	document.feedbackForm.isCancelled.value="";
	document.feedbackForm.submit();
	
}

function validAdd(){
	if(selectboxCategory.getSelectedId()=='-1'){
		alert('<bean:message key="feedback_form_add_field.error.select_category"/>');
		selectboxCategory.setFocus();
		return false;
	}
	if(selectboxFields.getSelectedId()=='-1'){
		alert('<bean:message key="feedback_form_add_field.error.select_field"/>');
		selectboxFields.setFocus();
		return false;
	}	
	return true;
}

function validRatingOrComment(){
	var commentRequired = document.feedbackForm.feedbackFormFieldCommentRequired.value;
	var ratingRequired = document.feedbackForm.feedbackFormFieldRatingRequired.value;
	var multipleSelectRequired = document.feedbackForm.feedbackFormFieldMultipleSelectRequired.value;
	if(commentRequired!='<%=FeedbackFormConstants.COMMENT_REQUIRED%>' && ratingRequired!='<%=FeedbackFormConstants.RATING_REQUIRED%>' && multipleSelectRequired!='<%=FeedbackFormConstants.MULTIPLE_SELECT_REQUIRED%>'){
		alert('<bean:message key="feedback_form_add_field.error.select_rating_or_comment_or_multiple_select"/>');
		return false;
	}
	if(ratingRequired=='<%=FeedbackFormConstants.RATING_REQUIRED%>'){
		if(selectboxRatings.getSelectedId()=='-1'){
			alert('<bean:message key="feedback_form_add_field.error.select_rating"/>');
			selectboxRatings.setFocus();
			return false;
		}
	}
	if(multipleSelectRequired=='<%=FeedbackFormConstants.MULTIPLE_SELECT_REQUIRED%>'){
		if(selectboxMultipleSelects.getSelectedId()=='-1'){
			alert('<bean:message key="feedback_form_add_field.error.select_multiple_select"/>');
			selectboxMultipleSelects.setFocus();
			return false;
		}
	}
	if(document.feedbackForm.fieldDisplayType.value == '<%=FeedbackFormConstants.COMPACT %>'){
		if(multipleSelectRequired== '<%=FeedbackFormConstants.MULTIPLE_SELECT_REQUIRED %>' && ratingRequired== '<%=FeedbackFormConstants.RATING_REQUIRED %>'){
			alert("<bean:message key='feedback_form_add_field.error.both_cant_exist'/>");
			return false;
		}
	}
	return true;
}

function cancelForm(){
	document.feedbackForm.isCancelled.value="1";
	document.feedbackForm.isSubmitted.value="";
	document.feedbackForm.submit();
}
function actionOnLoad(){
	setTitle();
	toggleFieldOptions(document.feedbackForm.feedbackFieldType.value);
	<logic:empty name="feedbackForm" property="feedbackFormFieldIndex">
		onChangeCategory('');
	</logic:empty>
	<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_FIELD %>" name="feedbackForm" property="feedbackFormFieldType">
		changeState($('chkRatings'),document.feedbackForm.feedbackFormFieldRatingRequired,document.feedbackForm.feedbackFormFieldRatingRequired.value);
		changeState($('chkMultiSelects'),document.feedbackForm.feedbackFormFieldMultipleSelectRequired,document.feedbackForm.feedbackFormFieldMultipleSelectRequired.value);
		changeState($('chkCommentRequired'),document.feedbackForm.feedbackFormFieldCommentRequired,document.feedbackForm.feedbackFormFieldCommentRequired.value);
		changeState($('chkFieldRequired'),document.feedbackForm.fieldIsMandatory,document.feedbackForm.fieldIsMandatory.value);
		onRadioChange('rdo2',document.feedbackForm.fieldDisplayType.value);
	</logic:equal>
}
function setTitle(){
	<logic:empty name="feedbackForm" property="feedbackFormFieldIndex">
		window.top.setPopTitle('<b><bean:message key="feedback_form_add_field.label.add"/></b>');
	</logic:empty>
	<logic:notEmpty name="feedbackForm" property="feedbackFormFieldIndex">
		<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_FIELD %>" name="feedbackForm" property="feedbackFormFieldType">
			window.top.setPopTitle('<b><bean:message key="feedback_form_add_field.label.edit"/></b>');
		</logic:equal>
	</logic:notEmpty>
}

window.onload = actionOnLoad;
//-->
</script>
