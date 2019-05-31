<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>

<%@page import="com.talentPool.masters.constants.FeedbackFormConstants"%><script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script type="text/javascript">
var chkedCheckBox = "images/checkboxchecked.gif";
var unChkedCheckBox = "images/checkboxunchecked.gif";
</script>
<div class="contentDivPop" style="width:450px;">
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
  	<html:hidden property="fieldCategoryIsSummary" name="mastersForm"/>
  	<html:hidden property="mode" name="mastersForm"/>
  	<html:hidden property="submitted" name="mastersForm" value="1"/>
	<div class="popupTop">
		<table class="tblPop">
			<tr>
				<td class="header">
					<bean:message key="master_add_feeedback_field_category.label.category_name"/>
					<span class="star">*</span>
					:
				</td>
				<td>
					<html:text property="feedbackFieldCategory" name="mastersForm" size="55" styleId="skillCategory" maxlength="500"></html:text>
				</td>
			</tr>
			<tr>
				<td class="header">
					Is <bean:message key="common.summary_feedback_field"/>:
				</td>
				<td>
					<img src="" name="isSummaryCategory" onclick="javascript: toggleChkBox(this);">
					<logic:equal value="<%=FeedbackFormConstants.SUMMARY_FIELD %>" name="mastersForm" property="fieldCategoryIsSummary" >				
			      	  	<script>
			      	  		document["isSummaryCategory"].src = chkedCheckBox;
			      	  	</script>
		      	  	</logic:equal>
		      	  	<logic:notEqual value="<%=FeedbackFormConstants.SUMMARY_FIELD%>" name="mastersForm" property="fieldCategoryIsSummary" >
			      	  	<script>
			      	  		document["isSummaryCategory"].src = unChkedCheckBox;
			      	  	</script>
		      	  	</logic:notEqual>
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
	var Name = document.mastersForm.feedbackFieldCategory.value;
	if(Name.trim()==""){
		return;
	}
	document.mastersForm.fieldCategoryIsSummary.value=getIsSummaryCategory();
	document.mastersForm.submit();
}
function actionOnLoad(){
	<logic:empty name="mastersForm" property="feedbackFieldCategoryId" >
		window.top.setPopTitle('<b><bean:message key="master_add_feeedback_field_category.label.add_category"/></b>');
	</logic:empty>
	<logic:notEmpty name="mastersForm" property="feedbackFieldCategoryId">
		window.top.setPopTitle('<b><bean:message key="master_add_feeedback_field_category.label.edit_category"/></b>');
	</logic:notEmpty>
}
function toggleChkBox(elem) {
	if (elem.src.indexOf(chkedCheckBox) == -1) {
		elem.src = chkedCheckBox;
	} else {
		elem.src = unChkedCheckBox;
	}
	return false;	
}

function getChkBoxValue(elem) {
	if (elem.src.indexOf(chkedCheckBox) == -1) {
		return 0;
	} else {
		return 1;
	}
}

function getIsSummaryCategory(){
	var val = getChkBoxValue(document["isSummaryCategory"]);
	if(val == 1){
		val = '<%=FeedbackFormConstants.SUMMARY_FIELD%>';
	}else{
		val = '<%=FeedbackFormConstants.NON_SUMMARY_FIELD%>';
	}
	return val;
}

window.onload=actionOnLoad;
//-->
</script>
