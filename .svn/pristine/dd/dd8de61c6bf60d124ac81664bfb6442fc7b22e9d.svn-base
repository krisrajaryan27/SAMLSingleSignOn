<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.applicant.dataobject.ApplicantData, 
                com.talentPool.selectionProcess.SelectionProcessConstants,
                com.talentPool.selectionProcess.form.SelectionProcessForm,
                com.talentPool.selectionProcess.dataobject.FeedbackData,
                com.talentPool.selectionProcess.dataobject.UserData,
                com.talentPool.positions.dataobject.StepData,
                com.talentPool.positions.dataobject.TraitData,
                com.talentPool.applicant.ApplicantConstants,
                com.talentPool.common.NavigationConstants,
                com.talentPool.positions.PositionConstants,
                java.lang.Boolean,
                java.util.List,java.util.Map, java.lang.StringBuffer,java.util.Arrays,
                com.talentPool.common.properties.TPApplicationProperties" %>
<%@page import="com.talentPool.masters.constants.FeedbackFormConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.masters.dataobject.RatingsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectsData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.masters.dataobject.RatingFieldsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectFieldsData"%>
<logic:present name="update" scope="request">
<script>
	window.top.hidePopWin(true);
</script>
</logic:present>   
<logic:present name="errors" scope="request">             
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
</logic:present> 
<logic:notPresent name="errors" scope="request">
<logic:notPresent name="update" scope="request">
<%
	String userId = (String) request.getSession(false).getAttribute("userId");
%>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>  
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>    
<script>
var chckdRadioImg = "images/checkedradiobutton.gif";
var radioImg = "images/radiobutton.gif";

function getSingleElement(parent,tagName,defVal){
	try {
		return parent.getElementsByTagName(tagName)[0].firstChild.nodeValue;
	} catch( myError ) {}
	return defVal;
}

function saveChanges() {
	if(requiredFields.length > 0) {
		var mn;
		for(mn = 0; mn < requiredFields.length; mn++) {
			var ratingFldId = '<%=SelectionProcessConstants.RATING_%>' + requiredFields[mn] + "_" + loggedInUserId;
			var commentFldId = '<%=SelectionProcessConstants.TRAIT_%>' + requiredFields[mn] + "_" + loggedInUserId;
			var multipleSelectFldId = '<%=SelectionProcessConstants.MULTIPLE_SELECT_%>' + requiredFields[mn] + "_" + loggedInUserId;
			if(($(ratingFldId) && $(ratingFldId).value != '') || ($(commentFldId) && $(commentFldId).value != '') || ($(multipleSelectFldId) && $(multipleSelectFldId).value != '')) {
				continue;
			} else {
				alert("<bean:message key='selection_feedback.error.mandatory_fields' />");
				break;
			}
		}
		if(mn < requiredFields.length) {
			return false;
		}
	}
	var text = '';
	if(systemFields){
		var commentFldId = '<%=SelectionProcessConstants.TRAIT_%>' + systemFields + "_" + loggedInUserId;	
		if($(commentFldId) && $(commentFldId).value != '') {
			text = $(commentFldId).value;
		}
	}
	
	document.selectionProcessForm.mode.value = 'saveTempTraits'; 
	document.selectionProcessForm.submit();
	window.top.setComment('<bean:write name="selectionProcessForm" property="applicantId"/>',text);
	return true; 
}

function radioBttnClicked(param) {
  var imgElem = document.getElementById("imgOtherPos");
  if (imgElem) {
	imgElem.innerHTML = '<img src="images/radiobutton.gif" />&nbsp;';
  }
  var divElem = document.getElementById('selectPositionAndStepDiv');
  if (divElem) {
  	divElem.style.display='none';
  }
  parts = nextStepsIds.split(',');
  for (var i = 0; i < parts.length; i++) {  	
    var elem = document.getElementById('img'+parts[i]);
    if (parts[i] == param) {
      elem.innerHTML = '<img src="images/checkedradiobutton.gif" />&nbsp;';
      document.selectionProcessForm.nextPositionStepId.value = param;
    } else {
      elem.innerHTML = '<img src="images/radiobutton.gif" />&nbsp;';
    }
  }
}

function showDiv(divId) { 	
	var imgElem = document.getElementById("imgOtherPos");
	if (imgElem) {
		imgElem.innerHTML = '<img src="images/checkedradiobutton.gif" />&nbsp;';
	}
	parts = nextStepsIds.split(',');
    for (var i = 0; i < parts.length; i++) {
	    var elem = document.getElementById('img'+parts[i]);
	    elem.innerHTML = '<img src="images/radiobutton.gif" />&nbsp;';	   
    }
	selectBoxPosition.setSelected(selectBoxPosition.getIndexWithId('-1'));  
	var elem = document.getElementById(divId);
	if (elem) {
		elem.style.display='';
	}
}

function hideDiv(divId) {
	radioBttnClicked(document.selectionProcessForm.nextPositionStepId.value);	
	var elem = document.getElementById(divId);
	if (elem) {
		elem.style.display='none';
	}
}

function viewApplicant(aId){
	window.open("selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	return false;
}
</script>
<%
  SelectionProcessForm selectionProcessForm = (SelectionProcessForm) request.getAttribute("selectionProcessForm");
%>
<bean:define id="traitData" name="traitData" scope="request" type="Map" />
<bean:define id="interviewers" name="interviewers" scope="request" type="ArrayList" />
<div class="contentDivPop" >
<html:form action="/selectionProcess">
  <html:hidden property="mode"/>
  <html:hidden property="applicantId" name="selectionProcessForm"/>
  <html:hidden property="traitIds" name="selectionProcessForm"/>
  <html:hidden property="communicationId" name="selectionProcessForm"/>
  <html:hidden property="sessionId" name="selectionProcessForm"/>
  <html:hidden property="positionId" name="feedbackData"/>
  
  
	<div class="bottomDivSec">
	<div class="vpTop" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
      <tr>
        <td><strong id="VP_TITLE" class="Grey"><a href="#" onclick="viewApplicant('<bean:write name="selectionProcessForm" property="applicantId"/>');"><bean:write name="feedbackData" property="applicantName"/></a>&nbsp;
 				<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="feedbackData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/></strong></td>
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
	         <td class="header"><bean:message key="selection_feedback.label.selectionStep"/></td>
	         <td><bean:write name="feedbackData" property="fromStepData.stepTitle"/></td>
	      </tr>
	    </table>
		</div>
		
		<div class="popupBody">
			<table class="tblPop" width="100%">
				<tr>
					<td id="feedbackBy" style="color:#666666;font-weight:bold;"></td>
				</tr>
				<tr>
					<td>				
						<div class="outerDiv" style="width:801px;height:250px;overflow: auto; background-color: #ffffff; padding: 5px;">
							<table cellpadding="0" cellspacing="0" class="feedbackform" style="width:784px; "> 								
								<logic:notEmpty property="fromStepData.traits" name="feedbackData">
								<logic:notEmpty name="interviewers" >
								<% UserData interviewer = (UserData) interviewers.get(0); %>
								<script language="JavaScript">
								$("feedbackBy").innerHTML='Feedback By <%=Utils.escapeHTML(interviewer.getUserName())%>';
								var requiredFields = new Array();
								var systemFields;
								var loggedInUserId = '<%=interviewer.getUserId()%>';
								</script>
								<% String prevRatingId = ""; %>
								<% String prevMultipleSelectId = ""; %>
								<logic:iterate id="trait" property="fromStepData.traits" name="feedbackData" indexId="cnt" type="TraitData">
								<%
									String ratingFieldId = (String)traitData.get(SelectionProcessConstants.RATING_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
									ratingFieldId = Utils.isBlankOrNull(ratingFieldId)? "":ratingFieldId;
									String ratingFieldDesc = (String)traitData.get(SelectionProcessConstants.RATINGDESC_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
									ratingFieldDesc = Utils.isBlankOrNull(ratingFieldDesc)?"":ratingFieldDesc;
									String ratingId = trait.getRatingId();
									String interviewerComment = traitData.get(trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()).toString().trim();
									
									String multipleSelectFieldId = (String)traitData.get(SelectionProcessConstants.MULTIPLE_SELECT_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
									multipleSelectFieldId = Utils.isBlankOrNull(multipleSelectFieldId)? "":multipleSelectFieldId;
									String multipleSelectFieldDesc = (String)traitData.get(SelectionProcessConstants.MULTIPLE_SELECTDESC_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
									multipleSelectFieldDesc = Utils.isBlankOrNull(multipleSelectFieldDesc)?"":multipleSelectFieldDesc;
									String multipleSelectId = trait.getMultipleSelectId();
									List selectedFields = Arrays.asList(multipleSelectFieldId.split(","));
									
								%>		
								<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>" name="trait" property="feedbackFormFieldType">
									<tr>
										<td valign="top" colspan="4" class="category"><bean:write name="trait" property="feedbackFieldTitle"/></td>  												    								    						
									</tr>
								</logic:equal>
								<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_FIELD%>" name="trait" property="feedbackFormFieldType">
									<logic:equal value="<%=FeedbackFormConstants.GENERALISED%>" name="trait" property="feedbackFormFieldDisplayType">
										<tr>									
											<td valign="top" colspan="3">
												<strong><bean:write name="trait" property="feedbackFieldTitle"/></strong>
												<logic:equal value="<%=FeedbackFormConstants.FIELD_REQUIRED%>" name="trait" property="feedbackFormFieldIsMandatory">												
												<span style="color:red;">*</span>
												<script language="JavaScript">
													requiredFields[requiredFields.length] = '<%=trait.getFeedbackFormFieldId()%>';
												</script>
												</logic:equal>
												<logic:equal value="<%=FeedbackFormConstants.SYSTEM_GENERATED%>" name="trait" property="systemGenerated">												
													<script language="JavaScript">
														systemFields = '<%=trait.getFeedbackFormFieldId()%>';
													</script>
												</logic:equal>
											</td>
										</tr>
										<logic:notEmpty name="trait" property="feedbackFormFieldDesc">  	
										<tr>
											<td valign="top" colspan="3"><pre class="criteriacomment"><bean:write name="trait" property="feedbackFormFieldDesc"/></pre></td>
										</tr>
										</logic:notEmpty>																										    	
										<% if(!Utils.isBlankOrNull(ratingId)){ %>
										<tr>
											<td colspan="3">
												<table class="ratingcontent">
													<tr>
														<%
															RatingsData ratingsData = CommonUtils.getRatingsData(ratingId);
															ArrayList ratingFields = ratingsData.getRatingFields();
														%>
														<%	
															for(int r=0; ratingFields!=null && r<ratingFields.size();r++) {
																RatingFieldsData ratingFieldData = (RatingFieldsData)ratingFields.get(r);
														%>												
														<td style="padding-right: 0px;">
															<input type="hidden" name="<%=SelectionProcessConstants.RATING_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=ratingFieldId %>" id = "<%=SelectionProcessConstants.RATING_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=ratingFieldId%>" />
																<% if(ratingFieldData.getRatingFieldId().equals(ratingFieldId)) { %>
																	<img src="images/checkedradiobutton.gif" name="<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=ratingFieldData.getRatingFieldId() %>" onclick="onRadioChange('<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=ratingFieldData.getRatingFieldId() %>');">
																<% } else { %>
																	<img src="images/radiobutton.gif" name="<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=ratingFieldData.getRatingFieldId() %>" onclick="onRadioChange('<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=ratingFieldData.getRatingFieldId() %>');">
																<% } %>
														</td>
														<td class="title">
															<%=Utils.escapeHTML(ratingFieldData.getRatingFieldDesc())%>
														</td>
														<% } %>
													</tr>
												</table>
											</td>
										</tr>
										<% } %>
										<% if(!Utils.isBlankOrNull(multipleSelectId)){ %>
										<tr>
											<td colspan="3">
												<table class="ratingcontent">
													<tr>
														<%
															MultipleSelectsData multipleSelectsData = CommonUtils.getMultipleSelectsData(multipleSelectId);
															ArrayList multipleSelectsFields = multipleSelectsData.getMultipleSelectFields();
														%>
														<%	
															for(int r=0; multipleSelectsFields!=null && r<multipleSelectsFields.size();r++) {
																MultipleSelectFieldsData multipleSelectFieldsData = (MultipleSelectFieldsData)multipleSelectsFields.get(r);
														%>												
														<td style="padding-right: 0px;">
															<input type="hidden" name="<%=SelectionProcessConstants.MULTIPLE_SELECT_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=ratingFieldId %>" id = "<%=SelectionProcessConstants.RATING_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=multipleSelectFieldId%>" />
																<% if(selectedFields.contains(multipleSelectFieldsData.getSelectFieldId())) { %>
																	<img src="images/checkboxchecked.gif" name="<%=SelectionProcessConstants.MULTIPLE_SELECT_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=multipleSelectFieldsData.getSelectFieldId() %>" onclick="onRadioChange('<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=multipleSelectFieldsData.getSelectFieldId() %>');">
																<% } else { %>
																	<img src="images/checkboxunchecked.gif" name="<%=SelectionProcessConstants.MULTIPLE_SELECT_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=multipleSelectFieldsData.getSelectFieldId() %>" onclick="onRadioChange('<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=multipleSelectFieldsData.getSelectFieldId() %>');">
																<% } %>
														</td>
														<td class="title">
															<%=Utils.escapeHTML(multipleSelectFieldsData.getSelectFieldDesc())%>
														</td>
														<% } %>
													</tr>
												</table>
											</td>
										</tr>
										<% } %>											
										<logic:equal value="<%=FeedbackFormConstants.COMMENT_REQUIRED %>" name="trait" property="feedbackFormFieldCommentRequired">
										<tr>
											<td colspan="3">
												<textarea rows="3" cols="80" name="<%=SelectionProcessConstants.TRAIT_ + trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="<%=SelectionProcessConstants.TRAIT_ + trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" class="txtArea"><%=interviewerComment%></textarea>
											</td>
										</tr>
										</logic:equal>
									</logic:equal>
									<logic:notEqual value="<%=FeedbackFormConstants.GENERALISED%>" name="trait" property="feedbackFormFieldDisplayType">
										<% if(!Utils.isBlankOrNull(ratingId) || !Utils.isBlankOrNull(multipleSelectId) ){ %>		
										
										
										<tr>
											<td>&nbsp;</td>
											<td colspan="2">
											
											<% if(!Utils.isBlankOrNull(ratingId)){ %>
										<%
											RatingsData ratingsData = CommonUtils.getRatingsData(ratingId);
											ArrayList ratingFields = ratingsData.getRatingFields();
										%>
										<logic:notEqual name="trait" property="ratingId" value="<%=prevRatingId%>">
											
												<% String ratingDescStr = "("; %>
												<table class="ratingcontent">
													<tr>
														<%	
															for(int r=0; ratingFields!=null && r<ratingFields.size();r++) {
																RatingFieldsData ratingFieldData = (RatingFieldsData)ratingFields.get(r);																
														%>												
														<td style="color:#666666;">
															<% String desc = ratingFieldData.getRatingFieldDesc(); %>
															<% ratingDescStr += "&nbsp;" + "<b>" + desc.substring(0,1) + "</b>" + desc.substring(1,desc.length()); %>
															<% if(r != (ratingFields.size() - 1)) { %>
															<% ratingDescStr += ","; %>
															<% } %>
															<% if(!Utils.isBlankOrNull(desc)){ %>
															<strong><%=desc.substring(0,1)%></strong>
															<% } %>
														</td>
														<% } %>
														<% ratingDescStr += "&nbsp;)"; %>
														<td style="color:#666666;"><%=ratingDescStr%></td>
													</tr>
												</table>												
												</logic:notEqual>
												<% } %>
											</td>										
										</tr>
										
										<% } %>
										
										<% if(!Utils.isBlankOrNull(ratingId) || !Utils.isBlankOrNull(multipleSelectId) ){ %>		
										
										<tr>
											<td>&nbsp;</td>																		
											<td colspan="2">
																						
												<% if(!Utils.isBlankOrNull(multipleSelectId)){ %>
												<%
														MultipleSelectsData multipleSelectsData = CommonUtils.getMultipleSelectsData(multipleSelectId);
														ArrayList multipleSelectFields = multipleSelectsData.getMultipleSelectFields();
												%>
												<logic:notEqual name="trait" property="multipleSelectId" value="<%=prevMultipleSelectId%>">
											
												<% String multipleSelectDescStr = "("; %>
												<table class="ratingcontent">
													<tr>
														<%	
																for(int s=0; multipleSelectFields!=null && s<multipleSelectFields.size();s++) {
																	MultipleSelectFieldsData multipleSelectFieldData = (MultipleSelectFieldsData)multipleSelectFields.get(s);														
														%>												
														<td style="color:#666666;">
															<% String desc = multipleSelectFieldData.getSelectFieldDesc(); %>
															<% multipleSelectDescStr += "&nbsp;" + "<b>" + desc.substring(0,1) + "</b>" + desc.substring(1,desc.length()); %>
															<% if(s != (multipleSelectFields.size() - 1)) { %>
															<% multipleSelectDescStr += ","; %>
															<% } %>
															<% if(!Utils.isBlankOrNull(desc)){ %>
															<strong><%=desc.substring(0,1)%></strong>
															<% } %>
														</td>
														<% } %>
														<% multipleSelectDescStr += "&nbsp;)"; %>
														<td style="color:#666666;"><%=multipleSelectDescStr%></td>
													</tr>
												</table>
												</logic:notEqual>
												<% } %>
											</td>
										</tr>
										
										<% } %>										
										
										<tr>									
											<td valign="top" style="width:240px;word-wrap:break-word;">
												<strong><bean:write name="trait" property="feedbackFieldTitle"/></strong>
												<logic:equal value="<%=FeedbackFormConstants.FIELD_REQUIRED%>" name="trait" property="feedbackFormFieldIsMandatory">
												<span style="color:red;">*</span>
												<script language="JavaScript">
													requiredFields[requiredFields.length] = '<%=trait.getFeedbackFormFieldId()%>';
												</script>
												</logic:equal>	
											</td>
											
											<% if(!Utils.isBlankOrNull(ratingId) || !Utils.isBlankOrNull(multipleSelectId) ){ %>		
											
												<% if(!Utils.isBlankOrNull(ratingId)){ %>								
														<td>					
												<table class="ratingcontent">
													<tr>
														<%
															RatingsData ratingsData = CommonUtils.getRatingsData(ratingId);
															ArrayList ratingFields = ratingsData.getRatingFields();
														%>
														<%	
															for(int r=0; ratingFields!=null && r<ratingFields.size();r++) {
																RatingFieldsData ratingFieldData = (RatingFieldsData)ratingFields.get(r);
														%>												
														<td style="padding-right: 0px;">
															<input type="hidden" name="<%=SelectionProcessConstants.RATING_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=ratingFieldId %>" id = "<%=SelectionProcessConstants.RATING_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=ratingFieldId %>" />
																<% if(ratingFieldData.getRatingFieldId().equals(ratingFieldId)) { %>
																	<img src="images/checkedradiobutton.gif" name="<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=ratingFieldData.getRatingFieldId() %>" onclick="onRadioChange('<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=ratingFieldData.getRatingFieldId() %>');">
																<% } else { %>
																	<img src="images/radiobutton.gif" name="<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=ratingFieldData.getRatingFieldId() %>" onclick="onRadioChange('<%=SelectionProcessConstants.RATING_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=ratingFieldData.getRatingFieldId() %>');">
																<% } %>
														</td>
														<% } %>
													</tr>
												</table>		
												</td>
											<% }%>
																		
												<%if(!Utils.isBlankOrNull(multipleSelectId)){ %>			
														<td>			
												 <table class="ratingcontent">
													<tr>
														<%
														MultipleSelectsData multipleSelectsData = CommonUtils.getMultipleSelectsData(multipleSelectId);
														ArrayList multipleSelectFields = multipleSelectsData.getMultipleSelectFields();
													%>
													<input type="hidden" name="<%=SelectionProcessConstants.MULTIPLE_SELECT_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=multipleSelectFieldId %>" id = "<%=SelectionProcessConstants.MULTIPLE_SELECT_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>"/>
														
													<%	
														for(int s=0; multipleSelectFields!=null && s<multipleSelectFields.size();s++) {
															MultipleSelectFieldsData multipleSelectFieldData = (MultipleSelectFieldsData)multipleSelectFields.get(s);	
														%>												
														
														<td style="padding-right: 0px;">
																<% if(selectedFields.contains(multipleSelectFieldData.getSelectFieldId())) { %>
																	<img src="images/checkboxchecked.gif" name="<%=SelectionProcessConstants.MULTIPLE_SELECT_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=multipleSelectFieldData.getSelectFieldId()%>" onclick="onMultipleSelectChange('<%=SelectionProcessConstants.MULTIPLE_SELECT_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=multipleSelectFieldData.getSelectFieldId()%>');">
																<% } else { %>
																	<img src="images/checkboxunchecked.gif" name="<%=SelectionProcessConstants.MULTIPLE_SELECT_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="img_<%=multipleSelectFieldData.getSelectFieldId()%>" onclick="onMultipleSelectChange('<%=SelectionProcessConstants.MULTIPLE_SELECT_+trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>','<%=multipleSelectFieldData.getSelectFieldId() %>');">
																<% } %>
														</td>
														<% } %>
													</tr>
												</table>		
												</td>
												<% }%>
												
											<% } else { %>
											<td>&nbsp;</td>								
											<% } %>
																						
											<td>
											<logic:equal value="<%=FeedbackFormConstants.COMMENT_REQUIRED %>" name="trait" property="feedbackFormFieldCommentRequired">
												<input type="text" size="43" name="<%=SelectionProcessConstants.TRAIT_ + trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" id="<%=SelectionProcessConstants.TRAIT_ + trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>" value="<%=interviewerComment%>" />
											</logic:equal>	
											<logic:notEqual value="<%=FeedbackFormConstants.COMMENT_REQUIRED %>" name="trait" property="feedbackFormFieldCommentRequired">
											&nbsp;
											</logic:notEqual>				    						
											</td>
										</tr>
									</logic:notEqual>																
								</logic:equal>	
								<% prevRatingId = (ratingId == null)?"":ratingId; %>
								<%  prevMultipleSelectId = (multipleSelectId == null)?"":multipleSelectId; %>		
								<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>" name="trait" property="feedbackFormFieldType">
								<% prevRatingId = ""; %>	
								<%  prevMultipleSelectId = ""; %>
								</logic:equal>	
								<logic:equal value="<%=FeedbackFormConstants.GENERALISED%>" name="trait" property="feedbackFormFieldDisplayType">
								<% prevRatingId = ""; %>	
								<%  prevMultipleSelectId = ""; %>
								</logic:equal>	
								</logic:iterate>
								</logic:notEmpty>
								</logic:notEmpty>
							</table>						
					</div>
				</td>
			</tr>
			<tr>
				<td><br/>
				<div class="navBtn" style="float: right;">
				<logic:notEmpty name="interviewers" >
				<logic:equal name="doDisplaySaveButton" scope="request" value='<%=""+Boolean.TRUE.booleanValue()%>'>
				<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: saveChanges();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				</logic:equal>
				</logic:notEmpty>
				<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
				</td>
			</tr>
			</table>
		</div>
	</div>
</html:form>		
</div>

<script language="JavaScript">

function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					if(theImage.src.indexOf(radioImg)>-1){
						$(imgGroupName).value=attachmentId;
						theImage.src = chckdRadioImg;						
					}else{
						$(imgGroupName).value='';
						theImage.src = radioImg;
					}
				}else{
					theImage.src = radioImg;
				}
			}
	}
}

function setPopupTitle(){
	var title = '<b><bean:message key="selection_feedback.label.submit_feedback"/> - </b><bean:write name="feedbackData" property="applicantName"/> &nbsp';
   	title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="feedbackData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	window.top.setPopTitle(title);
}

window.onload = setPopupTitle;

</script>
</logic:notPresent>
</logic:notPresent>