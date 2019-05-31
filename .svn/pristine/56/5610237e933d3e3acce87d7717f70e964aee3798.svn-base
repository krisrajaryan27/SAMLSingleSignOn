<%@page import="com.talentPool.masters.constants.FeedbackFieldsConstant"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.masters.constants.FeedbackFormConstants"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.masters.dataobject.FeedbackFormFieldData"%>
<%@page import="com.talentPool.masters.dataobject.RatingsData"%>
<%@page import="com.talentPool.masters.dataobject.RatingFieldsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectFieldsData"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.reports.ReportConstants"%>

<%@page import="com.talentPool.masters.form.FeedbackForm"%><script language="JavaScript" src="js/commonFunctions.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/feedbackFormField.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/RatingField.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/RatingData.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/MultipleSelectData.js"
	type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js"
	type="text/javascript"></script>



<link rel="stylesheet" type="text/css"
	href="themes/default/popupiframe.css">
<style type="text/css">
</style>

<div class="contentDivPop">
<%
if (request.getAttribute(Globals.ERROR_KEY) != null) {
%>
<table id="m_errortable">
	<tr>
		<td class='header'><b><bean:message
			key="errors.following_errors" /></b></td>
	</tr>
	<tr>
		<td class="message"><html:errors /></td>
	</tr>
</table>
<br />
<br />
<%
}
%> <script language="JavaScript">
var formFields = new Array();
<%
String isFeedbackFormUsed = (String)request.getAttribute("isFeedbackFormUsed");
ArrayList formFields = (ArrayList)request.getAttribute("formFields");
for(int i=0; formFields!=null && i<formFields.size(); i++){
	FeedbackFormFieldData data = (FeedbackFormFieldData)formFields.get(i);
	String fieldId= data.getFeedbackFormFieldId();
	String feedbackFieldId = data.getFeedbackFieldId();
	String fieldTitle = data.getFeedbackFieldTitle().replaceAll("'","\\\\'");
	String fieldDesc = Utils.isBlankOrNull(data.getFeedbackFormFieldDesc())? "": data.getFeedbackFormFieldDesc();
	fieldDesc = fieldDesc.replaceAll("'","\\\\'").replaceAll("\\r\\n","\\\\n");
	String fieldType = data.getFeedbackFormFieldType();	
	String commentRequired = data.getFeedbackFormFieldCommentRequired();
	String ratingId = Utils.isBlankOrNull(data.getRatingId())? "": data.getRatingId();
	String multipleSelectId = Utils.isBlankOrNull(data.getMultipleSelectId())? "": data.getMultipleSelectId();

	String displayType = data.getFieldDisplayType();
	String fieldIsMandatory = data.getFieldIsMandatory();
	String systemGenerated = data.getSystemGenerated();
	String feedbackFieldType = data.getFeedbackFieldType();
	String applicantFieldId = data.getApplicantFieldId();
	if(Utils.isBlankOrNull(displayType) && FeedbackFormConstants.SYSTEM_GENERATED.equals(systemGenerated)) {
		displayType = ((FeedbackForm)request.getAttribute("feedbackForm")).getDisplayType();		
	}
%>
var objR = new FeedbackFormField('<%=fieldId%>','<%=feedbackFieldId%>','<%=fieldTitle%>','<%=Utils.escapeHTML(fieldDesc)%>'.unescapeHTML(),'<%=fieldType%>','<%=commentRequired%>','<%=ratingId%>','<%=multipleSelectId%>','<%=displayType%>','<%=fieldIsMandatory%>','<%=systemGenerated%>','<%=feedbackFieldType%>','<%=applicantFieldId%>');
formFields[formFields.length]=objR;
<% 
}
%>

var ratings = new Array();
<%
ArrayList ratings = (ArrayList)request.getAttribute("ratings");
for(int i=0; ratings!=null && i<ratings.size(); i++){
	RatingsData ratingsData = (RatingsData)ratings.get(i);
	ArrayList ratingFields = ratingsData.getRatingFields();
%>
var ratingFields = new Array();
<%	
	for(int k=0; ratingFields!=null && k<ratingFields.size(); k++){
		//create javascript array
		RatingFieldsData ratingFieldsData = (RatingFieldsData)ratingFields.get(k);
		String fieldId = ratingFieldsData.getRatingFieldId();
		String fieldDesc = ratingFieldsData.getRatingFieldDesc().replaceAll("'","\\\\'");
%>
var objR = new RatingField('<%=fieldId%>','<%=fieldDesc%>');
ratingFields[ratingFields.length]=objR;
<%	
}
%>
var rDo = new RatingData('<%=ratingsData.getRatingId()%>','', ratingFields); 
ratings[ratings.length]=rDo;
<%
}
%>

var multipleSelects = new Array();
<%
ArrayList multipleSelects = (ArrayList)request.getAttribute("multipleSelects");
for(int i=0; multipleSelects!=null && i<multipleSelects.size(); i++){
	MultipleSelectsData multipleSelectsData = (MultipleSelectsData)multipleSelects.get(i);
	ArrayList multipleSelectFields = multipleSelectsData.getMultipleSelectFields();
%>
var multipleSelectFields = new Array();
<%	
	for(int k=0; multipleSelectFields!=null && k<multipleSelectFields.size(); k++){
		//create javascript array
		MultipleSelectFieldsData multipleSelectFieldsData = (MultipleSelectFieldsData)multipleSelectFields.get(k);
		String fieldId = multipleSelectFieldsData.getSelectFieldId();
		String fieldDesc = multipleSelectFieldsData.getSelectFieldDesc().replaceAll("'","\\\\'");
%>
var objR = new RatingField('<%=fieldId%>','<%=fieldDesc%>');
multipleSelectFields[multipleSelectFields.length]=objR;
<%	
}
%>
var rDo = new MultipleSelectData('<%=multipleSelectsData.getSelectId()%>','', multipleSelectFields); 
multipleSelects[multipleSelects.length]=rDo;
<%
}
%>
function getRatingData(rId){
	for(r=0;r<ratings.length;r++){
		if(ratings[r].getRatingId()==rId){
			return ratings[r];
		}
	}
	return null;
}
function getMultipleSelectData(sId){
	for(r=0;r<multipleSelects.length;r++){
		if(multipleSelects[r].getSelectId()==sId){
			return multipleSelects[r];
		}
	}
	return null;
}
</script> <html:form action="/feedbackform">
	<html:hidden property="mode" name="feedbackForm" value="addForm"/>
	<html:hidden property="feedbackFormId" name="feedbackForm" />
	<html:hidden property="feedbackFormTitle" name="feedbackForm" />
	<html:hidden property="feedbackFormDesc" name="feedbackForm" />
	<html:hidden property="strFeedbackForm" name="feedbackForm" />
	<html:hidden property="isSubmitted" name="feedbackForm" value="1" />

	<html:hidden property="feedbackFormFieldId" name="feedbackForm" />
	<html:hidden property="feedbackFieldId" name="feedbackForm" />
	<html:hidden property="feedbackFieldTitle" name="feedbackForm" />
	<html:hidden property="feedbackFormFieldDesc" name="feedbackForm" />
	<html:hidden property="feedbackFormFieldType" name="feedbackForm" />
	<html:hidden property="feedbackFormFieldRatingId" name="feedbackForm" />
	<html:hidden property="feedbackFormFieldMultipleSelectId" name="feedbackForm" />
	<html:hidden property="feedbackFormFieldMultipleSelectRequired" name="feedbackForm" />	 	 
	<html:hidden property="feedbackFormFieldCommentRequired"
		name="feedbackForm" />
	<html:hidden property="feedbackFormFieldIndex" name="feedbackForm" />
	<html:hidden property="feedbackFormFieldRatingRequired"
		name="feedbackForm" />
	<html:hidden property="displayType" name="feedbackForm" />
	<html:hidden property="fieldDisplayType" name="feedbackForm" />
	<html:hidden property="fieldIsMandatory" name="feedbackForm" />
	<html:hidden property="feedbackFieldType" name="feedbackForm" />
	<html:hidden property="applicantFieldId" name="feedbackForm" />
	<input type="hidden" name="tempId" value="<%=(String)request.getAttribute("tempId")%>" />	
<table cellspacing="0" cellpadding="0">
	<tr>
		<td style="font-size: 14px; font-weight: bold;"><bean:write
			name="feedbackForm" property="feedbackFormTitle" /></td>
		<td>&nbsp;<a href="#" class="green"
			onclick="javascript:editFormTitle();return false;">Edit</a></td>
	</tr>
</table>
<br>
<table cellspacing="0" cellpadding="0">
	<tr>
		<td><pre style="margin:0px;"><bean:write name="feedbackForm" property="feedbackFormDesc" /></pre>
<logic:empty property="feedbackFormDesc" name="feedbackForm">
			<a href="#" class="green"
				onclick="javascript:editFormDesc();return false;">Edit</a> template description
		</logic:empty><logic:notEmpty property="feedbackFormDesc" name="feedbackForm">
			&nbsp;<a href="#" class="green"
				onclick="javascript:editFormDesc();return false;">Edit</a>
		</logic:notEmpty>		
		</td>
		<td></td>
	</tr>
</table>
<br />
<table cellspacing="0" cellpadding="0">
	<tr>
		<td><a href="#" class="btn3" style="width:170px;margin-top:5px;"
			class="active" onclick="javascript: addField();"><span
			class="rightC"></span><span class="leftC"></span><bean:message
			key="feedback_form.label.add_new_field" /></a></td>
	</tr>
</table>
<br />
<table id="tblfields" class="fields">
	<tbody>
	</tbody>
</table>
<br />
<br />
<div class="navBtn" style="float:left;"><a href="#"
	style="width:60px;" class="active"
	onclick="javascript:submitForm();return false;"><span
	class="rightC"></span><span class="leftC"></span><bean:message
	key="common.submit" /></a> <a href="#" style="width:70px;margin-left:5px;"
	class="active"
	onclick="javascript:showPrintOptions();"><span
	class="rightC"></span><span class="leftC"></span><bean:message
	key="common.preview" /></a>
	<a href="#" style="width:60px;margin-left:5px;"
	class="active"
	onclick="javascript:window.top.hidePopWin(false);return false;"><span
	class="rightC"></span><span class="leftC"></span><bean:message
	key="common.cancel" /></a></div>
<br />
<br />
<br />
<br />
</div>
<style type="text/css">
.divPrintOptions{
 width: 250px; height: 100px; 
 border: 2px solid #666; 
 background-color: #fff; 
 padding: 10px;
 border-top: none;
 border-right: none;
}
</style>
<div id="divPrintOptions" class="divPrintOptions" style="top:0;right:0; position: absolute; display: none; ">
<table>	  	
	<tr>				  			
		<td style="padding-top: 10px;">
			<b><bean:message key="common.select"/> <bean:message key="selection_feedback.label.format"/></b>
		</td>
   	</tr>
	<tr>				  			
		<td>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_HTML%>" style="border:0px;background-color:#fff;" name="feedbackForm"><bean:message key="report.label.format_html"/></html:radio>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_PDF%>" style="border:0px;background-color:#fff;" name="feedbackForm"><bean:message key="report.label.format_pdf"/></html:radio>
			<html:radio property="reportFormat" value="<%=ReportConstants.FORMAT_EXCEL%>" style="border:0px;background-color:#fff;" name="feedbackForm"><bean:message key="report.label.format_excel"/></html:radio>
		</td>
   	</tr>
	<tr>				  			
		<td>
			<div class="navBtn" style="float: left;"><br/>
			<a href="#" style="width:110px;margin-right:5px;" class="active" onclick="javascript: printForm();return false;" id="printButton"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.print"/> <bean:message
	key="common.preview" /></a>
			<a href="#" style="width:60px;" class="active" onclick="javascript: hidePrintOptions();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
			</div>
		</td>
   	</tr>
   </table>										
</div>
</html:form>
<script type="text/javascript">
function showPrintOptions(){
	Effect.BlindDown('divPrintOptions',{duration:0.5});
}
function hidePrintOptions(){
	Effect.BlindUp('divPrintOptions',{duration:0.5});
}
function printForm(){
	var kk;
	for(kk = 0; kk < document.forms[0].reportFormat.length; kk++) {
		if(document.forms[0].reportFormat[kk].checked) {
			break;
		}
	}
	if(kk == document.forms[0].reportFormat.length) {		
		alert('<bean:message key="feedback_form.error.select_report_type"/>');
		return;
	}
	window.open("feedbackform.do?mode=previewFeedbackForm&feedbackFieldId="+document.feedbackForm.feedbackFormId.value+"&reportFormat="+document.forms[0].reportFormat[kk].value+"&tempId="+document.forms[0].tempId.value);
}
function submitForm(){
	if(!validFormSubmit()){
		return false;
	}
	setFieldsInStrFormat();
	document.feedbackForm.submit();
}
function validFormSubmit(){
	if(document.feedbackForm.feedbackFormTitle.value.trim()==''){
		alert('<bean:message key="feedback_form.error.set_title"/>');
		return false;
	}
	var fieldExists= false;
	for(i=0;i<formFields.length;i++){
		if(formFields[i].getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_FIELD%>'){
			fieldExists=true;
			break;		
		}
	}
	if(!fieldExists){
		alert('<bean:message key="feedback_form.error.field_required"/>');
		return false;
	}
	return true;
}

function editFormTitle(){
	document.feedbackForm.mode.value="editFormTitle";
	document.feedbackForm.isSubmitted.value="";
	setFieldsInStrFormat();
	document.feedbackForm.submit();
}

function editFormDesc(){
	document.feedbackForm.mode.value="editFormDesc";
	document.feedbackForm.isSubmitted.value="";
	setFieldsInStrFormat();
	document.feedbackForm.submit();
}
function addField(){
	document.feedbackForm.mode.value="addField";
	document.feedbackForm.isSubmitted.value="";
	
	document.feedbackForm.feedbackFormFieldId.value='';
	document.feedbackForm.feedbackFieldId.value='';
	document.feedbackForm.feedbackFieldTitle.value='';
	document.feedbackForm.feedbackFormFieldDesc.value='';
	document.feedbackForm.feedbackFormFieldType.value='<%=FeedbackFormConstants.FIELD_TYPE_FIELD%>';
	document.feedbackForm.feedbackFormFieldRatingId.value='';
	document.feedbackForm.feedbackFormFieldCommentRequired.value='<%=FeedbackFormConstants.COMMENT_NOT_REQUIRED%>';
	document.feedbackForm.feedbackFormFieldMultipleSelectId.value='';
	document.feedbackForm.feedbackFormFieldMultipleSelectRequired.value='<%=FeedbackFormConstants.MULTIPLE_SELECT_NOT_REQUIRED%>';
	document.feedbackForm.feedbackFormFieldIndex.value='';
	document.feedbackForm.feedbackFormFieldRatingRequired.value='';
	document.feedbackForm.fieldDisplayType.value=document.feedbackForm.displayType.value;
	document.feedbackForm.fieldIsMandatory.value='<%=FeedbackFormConstants.FIELD_NOT_REQUIRED%>';
	document.feedbackForm.feedbackFieldType.value='<%=FeedbackFieldsConstant.FIELD_TYPE_NORMAL%>';
	document.feedbackForm.applicantFieldId.value='';
	setFieldsInStrFormat();
	document.feedbackForm.submit();

}
function editField(idx){	
	if("0"!=<%=isFeedbackFormUsed%>){		
		alert("Feedback Form already used for feedback thus cannot be edited.");
		return;
	}
	document.feedbackForm.mode.value="addField";
	document.feedbackForm.isSubmitted.value="";
	
	var fld = formFields[idx];
	document.feedbackForm.feedbackFormFieldId.value=fld.getFieldId();
	document.feedbackForm.feedbackFieldId.value=fld.getFeedbackFieldId();
	document.feedbackForm.feedbackFieldTitle.value=fld.getFieldTitle();
	document.feedbackForm.feedbackFormFieldDesc.value=fld.getFieldDesc();
	document.feedbackForm.feedbackFormFieldType.value=fld.getFieldType();
	document.feedbackForm.feedbackFormFieldRatingId.value=fld.getRatingId();
	document.feedbackForm.feedbackFormFieldMultipleSelectId.value=fld.getMultipleSelectId();
	document.feedbackForm.feedbackFormFieldCommentRequired.value=fld.getCommentRequired();
	document.feedbackForm.feedbackFormFieldIndex.value=idx;
	document.feedbackForm.fieldDisplayType.value=fld.getDisplayType();
	document.feedbackForm.fieldIsMandatory.value=fld.getIsMandatory();
	document.feedbackForm.feedbackFieldType.value=fld.getFeedbackFieldType();
	document.feedbackForm.applicantFieldId.value=fld.getApplicantFieldId();
	if(fld.getRatingId()!=''){
		document.feedbackForm.feedbackFormFieldRatingRequired.value='<%=FeedbackFormConstants.RATING_REQUIRED%>';
	}else{
		document.feedbackForm.feedbackFormFieldRatingRequired.value='<%=FeedbackFormConstants.RATING_NOT_REQUIRED%>';
	}
	if(fld.getMultipleSelectId()!=''){
		document.feedbackForm.feedbackFormFieldMultipleSelectRequired.value='<%=FeedbackFormConstants.MULTIPLE_SELECT_REQUIRED%>';
	}else{
		document.feedbackForm.feedbackFormFieldMultipleSelectRequired.value='<%=FeedbackFormConstants.MULTIPLE_SELECT_NOT_REQUIRED%>';
	}
	setFieldsInStrFormat();
	document.feedbackForm.submit();	
}

function repaintFields(){
	tbl = $('tblfields');
	if (tbl) {		
		var tBody= tbl.getElementsByTagName("tbody")[0];
		var tRows = tBody.childNodes;
		for(var i = tRows.length - 1; i >= 0; i--){
			tBody.removeChild(tRows[i]);
		}	
		if(formFields.length>0){
			var headerPrinted = false;
			var appendArrows = false;
			for (i = 0; i < formFields.length; i++) {	
				var fld = formFields[i];
				var fieldType = fld.getFieldType();
				var fieldId =fld.getFieldId();
				var fieldTitle=fld.getFieldTitle();
				var fieldDesc=fld.getFieldDesc();
				var ratingId = fld.getRatingId();
				var multipleSelectId = fld.getMultipleSelectId(); 
				var commentRequired = fld.getCommentRequired();
				var displayType = fld.getDisplayType();
				var systemGenerated = fld.getSystemGenerated();
				var feedbackFieldType = fld.getFeedbackFieldType();
				
				var isCategory = (fieldType=='<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>')?true:false;
				var tdClass = (isCategory)?'category':(displayType == '<%=FeedbackFormConstants.GENERALISED%>')?'fld':'fldcompact';
				var tdImgClss = (isCategory)?'categoryimg':(displayType == '<%=FeedbackFormConstants.GENERALISED%>')?'fldimg':'fldimgcompact';
								
				var tRow = document.createElement("TR");
				
				var tCell = document.createElement("TD");
				tCell.className=tdClass;
				if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
					tCell.colSpan="2";
				}
				
				if(isCategory){
					tCell.innerHTML = fieldTitle.escapeHTML();					
					headerPrinted = false;
					appendArrows = true;
				}else if(i > 0 && !headerPrinted && displayType != '<%=FeedbackFormConstants.GENERALISED%>') {
						tCell.colSpan="6";						
						var innertbl = document.createElement("TABLE");												
						innertbl.width="756px";
						innertbl.style.marginBottom="-4px";
						var innerbody = document.createElement("TBODY");											
						var innerRow = document.createElement("TR");
						var innerCell = document.createElement("TD");
						
						innerCell.width="150px";
						innerRow.appendChild(innerCell);
						var k;		
						//var ratingText = '';				
						if(ratingId!=''){
							var ratingText ='';
							var ratingData = getRatingData(ratingId);
							if(ratingData!=null){
								ratingText += "(";
								var ratingFlds = ratingData.getRatingFields();
								for(k=0;k<ratingFlds.length;k++){
									var ratingCell = document.createElement("TD");
									desc = ratingFlds[k].getFieldDescription().escapeHTML();
									ratingCell.width="10px";				
									ratingCell.style.padding="0px";							
									ratingCell.style.paddingLeft="2px";		
									ratingCell.style.paddingRight="2px";	
									ratingCell.style.color="#666666";		
									ratingCell.innerHTML=desc.substring(0,1);
									ratingText += "&nbsp;&nbsp;" + "<b>" + desc.substring(0,1) + "</b>" + desc.substring(1,desc.length);
									if(k != (ratingFlds.length - 1)) {
										ratingText += ",";
									}
									innerRow.appendChild(ratingCell);
								}
								ratingText += "&nbsp;&nbsp;" + ")";
							}															
							var ratingCell = document.createElement("TD");
							ratingCell.width=(756-154-(18.5*k))+"px";
							ratingCell.style.wordWrap="break-word";
							ratingCell.style.padding="0px";							
							ratingCell.style.paddingLeft="2px";		
							ratingCell.style.paddingRight="2px";
							ratingCell.style.color="#666666";	
							ratingCell.innerHTML=ratingText;
							innerRow.appendChild(ratingCell);							
						}						
					
						if(multipleSelectId!=''){
							var multipleSelectText = '';
							var multipleSelectData = getMultipleSelectData(multipleSelectId);
							if(multipleSelectData!=null){
								multipleSelectText += "(";
								var multipleSelectFields = multipleSelectData.getMultipleSelectFields();
								for(k=0;k<multipleSelectFields.length;k++){
									var multipleSelectCell = document.createElement("TD");
									desc = multipleSelectFields[k].getFieldDescription().escapeHTML();
									multipleSelectCell.width="10px";				
									multipleSelectCell.style.padding="0px";							
									multipleSelectCell.style.paddingLeft="2px";		
									multipleSelectCell.style.paddingRight="2px";	
									multipleSelectCell.style.color="#666666";		
									multipleSelectCell.innerHTML=desc.substring(0,1);
									multipleSelectText += "&nbsp;&nbsp;" + "<b>" + desc.substring(0,1) + "</b>" + desc.substring(1,desc.length);
									if(k != (multipleSelectFields.length - 1)) {
										multipleSelectText += ",";
									}
									innerRow.appendChild(multipleSelectCell);
								}
								multipleSelectText += "&nbsp;&nbsp;" + ")";
							}															
							var multipleSelectCell = document.createElement("TD");
							multipleSelectCell.width=(756-154-(18.5*k))+"px";
							multipleSelectCell.style.wordWrap="break-word";
							multipleSelectCell.style.padding="0px";							
							multipleSelectCell.style.paddingLeft="2px";		
							multipleSelectCell.style.paddingRight="2px";
							multipleSelectCell.style.color="#666666";	
							multipleSelectCell.innerHTML=multipleSelectText;
							innerRow.appendChild(multipleSelectCell);	
						}		
								
						innerbody.appendChild(innerRow);
						innertbl.appendChild(innerbody);
						tCell.appendChild(innertbl);						
						
						headerPrinted = true;
						appendArrows = false;
						i = i - 1;
				} else {					
					appendArrows = true;
					if((i+1) < formFields.length && formFields[i+1].getRatingId() != ratingId && formFields[i+1].getFieldType() !='<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>' &&  formFields[i+1].getDisplayType() != '<%=FeedbackFormConstants.GENERALISED%>') {
						headerPrinted = false;
					}
					if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
						headerPrinted = false;
					}
					
					var innertbl = document.createElement("TABLE");
					innertbl.className="fldcontent";
					if(displayType != '<%=FeedbackFormConstants.GENERALISED%>') {
						innertbl.width="300px";
					}
										
					var innerbody = document.createElement("TBODY");
										
					var innerRow = document.createElement("TR");
					var innerCell = document.createElement("TD");
					innerCell.className="criteria";
					if(displayType != '<%=FeedbackFormConstants.GENERALISED%>') {
						innerCell.width="150px";
						innerCell.style.wordWrap="break-word";
					}
					innerCell.innerHTML = fieldTitle.escapeHTML();
					innerRow.appendChild(innerCell);
					if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
						innerbody.appendChild(innerRow);
					}					
									
					if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {						
						if(fieldDesc!=''){
							var innerRow = document.createElement("TR");
							var innerCell = document.createElement("TD");							
							innerCell.className="desc";
							innerCell.innerHTML =fieldDesc.trim().escapeHTML().replace(/\n/g ,"<br>");
							innerRow.appendChild(innerCell);
							innerbody.appendChild(innerRow);
						}
					}				
					
					if(ratingId!=''){
						var ratingData = getRatingData(ratingId);
						if(ratingData!=null){
							var ratingFlds = ratingData.getRatingFields();
							if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
								innerRow = document.createElement("TR");
							}
							var innerCell = document.createElement("TD");
							innerCell.className="rating";			
										
							var ratingtbl = document.createElement("TABLE");
							ratingtbl.className="ratingcontent";
							if(displayType != '<%=FeedbackFormConstants.GENERALISED%>') {
								ratingtbl.style.marginTop="-3px";
							}	
							
							var ratingbody = document.createElement("TBODY");
							var ratingRow = document.createElement("TR");
							
							for(k=0;k<ratingFlds.length;k++){
								var ratingCell = document.createElement("TD");
								ratingCell.innerHTML="<img  src=\"images/radiobutton.gif\"/>";
								ratingRow.appendChild(ratingCell);
								
								if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
									ratingCell = document.createElement("TD");
									ratingCell.className="title";
									ratingCell.innerHTML=ratingFlds[k].getFieldDescription().escapeHTML();
									ratingRow.appendChild(ratingCell);
								}
							}
							ratingbody.appendChild(ratingRow);
							ratingtbl.appendChild(ratingbody);
							innerCell.appendChild(ratingtbl);
							innerRow.appendChild(innerCell);
							if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
								innerbody.appendChild(innerRow);
							}
						}
					}


					if(multipleSelectId!=''){
						var multipleSelectData = getMultipleSelectData(multipleSelectId);
						if(multipleSelectData!=null){
							var multipleSelectFields = multipleSelectData.getMultipleSelectFields();
							if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
								innerRow = document.createElement("TR");
							}
							var innerCell = document.createElement("TD");
							innerCell.className="rating";			
										
							var ratingtbl = document.createElement("TABLE");
							ratingtbl.className="ratingcontent";
							if(displayType != '<%=FeedbackFormConstants.GENERALISED%>') {
								ratingtbl.style.marginTop="-3px";
							}	
							
							var ratingbody = document.createElement("TBODY");
							var ratingRow = document.createElement("TR");
							
							for(k=0;k<multipleSelectFields.length;k++){
								var ratingCell = document.createElement("TD");  
								ratingCell.innerHTML="<img  src=\"images/checkboxunchecked.gif\"/>";
								ratingRow.appendChild(ratingCell);
								
								if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
									ratingCell = document.createElement("TD");
									ratingCell.className="title";
									ratingCell.innerHTML=multipleSelectFields[k].getFieldDescription().escapeHTML();
									ratingRow.appendChild(ratingCell);
								}
							}
							ratingbody.appendChild(ratingRow);
							ratingtbl.appendChild(ratingbody);
							innerCell.appendChild(ratingtbl);
							innerRow.appendChild(innerCell);
							if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
								innerbody.appendChild(innerRow);
							}
						}
					}

					
					if(displayType == '<%=FeedbackFormConstants.GENERALISED%>') {
						if(commentRequired=='<%=FeedbackFormConstants.COMMENT_REQUIRED%>'){
							var innerRow = document.createElement("TR");
							var innerCell = document.createElement("TD");
							innerCell.className="comment";
							if(feedbackFieldType=='<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>')
								innerCell.innerHTML = '<input type="text" name="S2" size="65" style="margin-top:-3px;"/>';
							else
								innerCell.innerHTML = '<textarea rows="3" name="S1" cols="80"></textarea>';
							innerRow.appendChild(innerCell);
							innerbody.appendChild(innerRow);
						}		
					}			
					if(displayType != '<%=FeedbackFormConstants.GENERALISED%>') {
						innerbody.appendChild(innerRow);
					}				
					innertbl.appendChild(innerbody);
					tCell.appendChild(innertbl);
				}
				
				tRow.appendChild(tCell);
				
				if(appendArrows && !isCategory && displayType=='<%=FeedbackFormConstants.COMPACT%>'){
					tCell = document.createElement("TD");
					tCell.className="comment";
					if(commentRequired=='<%=FeedbackFormConstants.COMMENT_REQUIRED%>') {
						tCell.innerHTML = '<input type="text" name="S2" size="65" style="margin-top:-3px;"/>';
					} else {
						tCell.innerHTML = '&nbsp;';
					}
					
					tRow.appendChild(tCell);
				} else if(isCategory && displayType=='<%=FeedbackFormConstants.COMPACT%>'){
					tCell = document.createElement("TD");
					tCell.className='category';		
					tCell.innerHTML = '&nbsp;';										
					tRow.appendChild(tCell);
				}
					
				if(appendArrows) {
					tCell = document.createElement("TD");
					tCell.className=tdImgClss;
					tCell.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: swapFields(' + i + ', ' + (i - 1) + ');"/>';		
					tRow.appendChild(tCell);
					
					tCell = document.createElement("TD");
					tCell.className=tdImgClss;
					tCell.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: swapFields(' + i + ', ' + (i + 1) + ');"/>';		
					tRow.appendChild(tCell);
					
					tCell = document.createElement("TD");
					tCell.className=tdImgClss;
					if(systemGenerated=='<%=FeedbackFormConstants.SYSTEM_GENERATED%>'){
						tCell.innerHTML = '';
					}else{
						tCell.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.delete"/>' + '" src="images/ico_delete.gif" onclick="javascript: doDeleteFields(' + i + ');"/>';
					}		
					tRow.appendChild(tCell);
					
					tCell = document.createElement("TD");
					tCell.className=tdImgClss;
					if(isCategory || systemGenerated=='<%=FeedbackFormConstants.SYSTEM_GENERATED%>'){
						tCell.innerHTML = '&nbsp;';		
					}else{
						tCell.innerHTML = '<a href="#" onclick="javascript: editField(' + i + ');return false;" class="green"><bean:message key="common.edit"/></a>';		
					}
					tRow.appendChild(tCell);
				}
				//alert(tRow.innerHTML);
				tBody.appendChild(tRow);				
			}
		}
	}	
	//alert(tBody.innerHTML);
}
function setFieldsInStrFormat(){
	var str='';
	for (i = 0; i < formFields.length; i++) {
		var rf = formFields[i];
		if(i>0){
			str +=':';
		}
		var desc = rf.getFieldDesc().replace(/[|]/g ,"&#124;").replace(/:/g ,"&#58;");
		var title= rf.getFieldTitle().replace(/[|]/g ,"&#124;").replace(/:/g ,"&#58;");
		str += rf.getFieldId()+'|'+ rf.getFeedbackFieldId() + '|' + title + '|'+desc+'|'+ rf.getFieldType()+'|'+rf.getRatingId()+'|'+rf.getMultipleSelectId()+'|'+rf.getCommentRequired()+'|'+rf.getDisplayType()+'|'+rf.getIsMandatory()+'|'+rf.getSystemGenerated()+'|'+rf.getFeedbackFieldType()+'|'+rf.getApplicantFieldId();
	}
	document.feedbackForm.strFeedbackForm.value=str;
}

function swapFields(id1, id2) { 	
	if (id2 != -1 && id2 != formFields.length) {              
		var _temp = formFields[id1];
		if(_temp.getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_FIELD%>'){
			if(id2>0){
				if(formFields[id2].getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_FIELD%>'){
					formFields[id1] = formFields[id2];
					formFields[id2] = _temp;
					repaintFields();
				}
			}
		}else{
			//get category index
			var srcStart=id1;
			var srcEnd=id1;
			for(i=id1+1;i<formFields.length;i++){
				srcEnd=i;
				if(formFields[i].getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>'){
					srcEnd-=1;
					break;
				}
			}
			var dstStart=-1;
			if(id1<id2){
				for(i=id1+1;i<formFields.length;i++){
					if(formFields[i].getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>'){
						dstStart=i;
						break;
					}
				}
			}else{
				for(i=id1-1;i>=0;i--){
					if(formFields[i].getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>'){
						dstStart=i;
						break;
					}
				}
			}
			var dstEnd=-1;
			if(dstStart>=0){
				for(i=dstStart+1;i<formFields.length;i++){
					dstEnd=i;
					if(formFields[i].getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>'){
						dstEnd-=1;
						break;
					}
				}
			}
			
			if(dstEnd>=0){
				var newFormFields = new Array();
				for(i=0;i<formFields.length;i++){
					if(i==srcStart){
					  for(k=dstStart;k<=dstEnd;k++){
					  	newFormFields[newFormFields.length]=formFields[k];
					  }
					}else if(i==dstStart){
					  for(k=srcStart;k<=srcEnd;k++){
					  	newFormFields[newFormFields.length]=formFields[k];
					  }
					} else if( (i>srcStart && i<=srcEnd) || (i>dstStart && i<=dstEnd)){
					 
					}else{
					  newFormFields[newFormFields.length]=formFields[i];
					}
				}
				formFields = newFormFields;
				repaintFields();
			}			
		}
	}
}
function doDeleteFields(index){
	if("0"!=<%=isFeedbackFormUsed%>){		
		alert("Feedback Form already used for feedback thus cannot be Deleted.");
		return;
	}	
	var _temp = formFields[index];
	var idx = index;
	var ids = '';
	if(_temp.getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_FIELD%>'){
		if(_temp.getFieldId()!='0'){
			ids = _temp.getFieldId();
		}
	}else{
		for(i=index+1;i<formFields.length;i++){
			if(formFields[i].getFieldType()=='<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>'){
				break;
			}
			if(formFields[i].getFieldId()!='0'){
				if(ids==''){
					ids = formFields[i].getFieldId();
				}else{
					ids = ids + ',' + formFields[i].getFieldId();
				}
			}
			idx = idx + ','+i;	
		}
	}
	if(ids==''){
		deleteFields(idx+'');
	}else{
		var pars = "mode=checkDeleteFields&feedbackFormFieldId="+ids+"&feedbackFormFieldIndex="+idx;
		var myAjax = ajaxCall("feedbackform.do","get",pars,onDeleteComplete,reportError);
	}
}
function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="feedback_form.error.unable_to_delete_field"/>');
		return;
	}
	
	//get returned deleted ids and delete them from grid
	var deletedIndexes = getIds(xmlFile);
	deleteFields(deletedIndexes+'');
}

function deleteFields(fldIds){	
	var flds = fldIds.split(',');
	var cnt=0;
	for(i=0;i<flds.length;i++){
		formFields.splice(flds[i]-cnt, 1);
		cnt++;
	}
	repaintFields();
}
function setPopupTitle(){
	<logic:empty property="feedbackFormId" name="feedbackForm">
		window.top.setPopTitle('<b><bean:message key="feedback_form.title.add_new"/></b>');
	</logic:empty>
	<logic:notEmpty property="feedbackFormId" name="feedbackForm">
		window.top.setPopTitle('<b><bean:message key="feedback_form.title.update_form"/></b>');
	</logic:notEmpty>
}
function actionOnLoad(){
	setPopupTitle();
	repaintFields();
}
window.onload=actionOnLoad;
//-->
</script>
