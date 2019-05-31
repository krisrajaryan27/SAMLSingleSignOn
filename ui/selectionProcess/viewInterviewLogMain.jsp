<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.applicant.dataobject.ApplicantData,
                  com.talentPool.selectionProcess.SelectionProcessConstants,
                  com.talentPool.selectionProcess.dataobject.UserData,
                    com.talentPool.selectionProcess.dataobject.FeedbackData,
                  com.talentPool.common.NavigationConstants,
                  com.talentPool.positions.dataobject.TraitData,
                  com.talentPool.user.manager.PermissionSet,
                 com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                  java.util.List,java.util.Map,java.util.Arrays"%>
<%@page import="com.talentPool.masters.constants.FeedbackFormConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.masters.dataobject.RatingsData"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.masters.dataobject.RatingFieldsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectFieldsData"%>
<%@page import="com.talentPool.reports.ReportConstants"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<bean:define id="interviewers" name="interviewers" scope="request" type="List" />
<bean:define id="traitData" name="traitData" scope="request" type="Map" />
<bean:define id="feedbackData" name="feedbackData" scope="request" type="FeedbackData" />
<link rel="stylesheet" type="text/css" href="themes/default/print.css" media="print">
<style type="text/css">
.feedbackDiv {
width:800px;
height:350px;
overflow:auto;
background-color: #ffffff; 
padding: 10px;
}
</style>

<script language="JavaScript">
/*
function beforeprint() {
	if ($('feedbackDataDiv')) {	
		$('feedbackDataDiv').style.overflow="visible";
	}
}
function afterprint(){
	if ($('feedbackDataDiv')) {
		$('feedbackDataDiv').style.overflow="auto";
	}
}

window.onbeforeprint = beforeprint;
window.onafterprint = afterprint;
*/
</script>
<%
	PermissionSet permissionSet 		= (PermissionSet)request.getSession(false).getAttribute("permissionSet");
	boolean ctcOfferedViewable 			= ImportConfigurationManager.isCTCOfferedViewable(permissionSet);
	boolean inputSalaryVariableViewable = ImportConfigurationManager.isInputSalaryVariableViewable(permissionSet);
	boolean basicOfferedViewable 		= ImportConfigurationManager.isBasicOfferedViewable(permissionSet);
	boolean designationOfferedViewable 	= ImportConfigurationManager.isDesignationOfferedViewable(permissionSet);
	boolean levelOfferedViewable 		= ImportConfigurationManager.isLevelOfferedViewable(permissionSet);
	pageContext.setAttribute("ctcOfferedViewable",ctcOfferedViewable);
	pageContext.setAttribute("basicOfferedViewable",basicOfferedViewable);
	pageContext.setAttribute("designationOfferedViewable",designationOfferedViewable);
	pageContext.setAttribute("levelOfferedViewable",levelOfferedViewable);
	pageContext.setAttribute("inputSalaryVariableViewable",inputSalaryVariableViewable);
%>
<html:form action="/selectionProcess">
  <html:hidden property="mode" value="viewFeedbackReport"/>
  <html:hidden property="applicantId" name="selectionProcessForm"/>
  <html:hidden property="communicationId" name="selectionProcessForm"/>
  <html:hidden property="positionId" name="feedbackData"/>
  <html:hidden property="deleteAppointments" name="selectionProcessForm"/>
  <html:hidden property="interviewerIds" name="selectionProcessForm"/>
   <html:hidden property="showIfExist" name="selectionProcessForm"/>
   <html:hidden property="reportFormat" name="selectionProcessForm"/>  
   <html:hidden property="reportType" name="selectionProcessForm"/>   
<div class="contentDivPop" style="width:845px;">
	<div class="bottomDivSec">
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
           <td class="header"><bean:message key="selection_feedback.label.selectionStep"/></td>
           <td><bean:write name="feedbackData" property="fromStepData.stepTitle"/></td>
        </tr>
        <tr>
           <td class="header"><bean:message key="selection_feedback.label.result" /></td>
           <td><bean:write name="feedbackData" property="feedbackResultInStringFormat"/>
           </td>
        </tr>
      </table>
		</div>
		<% if (!SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT.equalsIgnoreCase(""+feedbackData.getToStepData().getStepId())
			&& !SelectionProcessConstants.STEP_NOT_ATTENDED.equalsIgnoreCase(""+feedbackData.getToStepData().getStepId())
			&& !SelectionProcessConstants.STEP_REPEAT.equalsIgnoreCase(""+feedbackData.getToStepData().getStepId())
			&& !SelectionProcessConstants.STEP_ATTENDED.equalsIgnoreCase(""+feedbackData.getToStepData().getStepId())) { %>
	<logic:notEmpty property="fromStepData.traits" name="feedbackData">
	<logic:notEmpty name="interviewers" >
	<div class="popupBody">
		<table class="tblPop" width="100%">					
			<tr>
			<logic:notEmpty name="feedbackData" property="joiningDate">
				<td>
					<table>
         			<tr>
		         		<td> <bean:message key="selection_feedback.label.joining_date"/>: </td>
		         		<td>		         		
  			      		<bean:write name="feedbackData" property="joiningDateToDisplay" />
         				</td>
         			</tr>
         		</table>
				</td>			
			</logic:notEmpty>
			<logic:equal value="true" name="designationOfferedViewable" scope="page">
				<logic:notEmpty name="feedbackData" property="designationOffered">
					<td>
						<table>
		         			<tr>
				         		<td> <bean:message key="selection_feedback.label.designation_offered"/>: </td>
				         		<td>		         		
		  			      		<bean:write name="feedbackData" property="designationOffered" />
		         				</td>
		         				<td>&nbsp;</td>
		         			</tr>
	         			</table>
					</td>			
				</logic:notEmpty>
			</logic:equal>
			<logic:equal value="true" name="ctcOfferedViewable" scope="page">
				<logic:notEmpty name="feedbackData" property="ctcOffered">
					<td>
						<table>
		         			<tr>
				         		<td> <bean:message key="selection_feedback.label.ctc_offered"/>: </td>
				         		<td>		         		
		  			      		<bean:write name="feedbackData" property="ctcOffered" />
		         				</td>
		         			</tr>
	         			</table>
					</td>			
				</logic:notEmpty>
			</logic:equal>
			<logic:equal value="true" name="basicOfferedViewable" scope="page">
				<logic:notEmpty name="feedbackData" property="basicOffered">
					<td>
						<table>
	         			<tr>
			         		<td> <bean:message key="selection_feedback.label.basic_offered"/>: </td>
			         		<td>		         		
	  			      		<bean:write name="feedbackData" property="basicOffered" />
	         				</td>
	         				<td>&nbsp;</td>
	         			</tr>
	         		</table>
					</td>			
				</logic:notEmpty>
			</logic:equal>
			</tr>
			<tr>
			<logic:notEmpty name="feedbackData" property="employeeCode">
				<td>
					<table>
	         			<tr>
			         		<td> <bean:message key="selection_feedback.label.employee_code"/>: </td>
			         		<td>		         		
	  			      		<bean:write name="feedbackData" property="employeeCode" />
	         				</td>
	         				<td>&nbsp;</td>
	         			</tr>
         			</table>
				</td>	
			</logic:notEmpty>
			<logic:equal value="true" name="levelOfferedViewable" scope="page">
				<logic:notEmpty name="feedbackData" property="levelOffered">
					<td>
						<table>
	         			<tr>
			         		<td> <bean:message key="selection_feedback.label.level_offered"/>: </td>
			         		<td>		         		
	  			      		<bean:write name="feedbackData" property="levelOffered" />
	         				</td>
	         				<td>&nbsp;</td>
	         			</tr>
	         		</table>
					</td>			
				</logic:notEmpty>
			</logic:equal>
			<logic:equal value="true" name="inputSalaryVariableViewable" scope="page">
				<logic:notEmpty name="feedbackData" property="inputSalaryVariable">
					<td>
						<table>
	         			<tr>
			         		<td><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL) %>:</td>
			         		<td>		         		
	  			      		<bean:write name="feedbackData" property="inputSalaryVariable" />
	         				</td>
	         				<td>&nbsp;</td>
	         			</tr>
	         		</table>
					</td>			
				</logic:notEmpty>
			</logic:equal>
			</tr>			
			<tr>
			<logic:notEmpty name="feedbackData" property="joiningBonus">
				<td>
					<table>
	         			<tr>
			         		<td> <bean:message key="selection_feedback.label.joining_Bonus"/>: </td>
			         		<td>		         		
	  			      		<bean:write name="feedbackData" property="joiningBonus" />
	         				</td>
	         				<td>&nbsp;</td>
	         			</tr>
         			</table>
				</td>	
			</logic:notEmpty>
			<logic:equal value="true" name="levelOfferedViewable" scope="page">
				<logic:notEmpty name="feedbackData" property="variableOffered">
					<td>
						<table>
	         			<tr>
			         		<td> <bean:message key="selection_feedback.label.variable_offered"/>: </td>
			         		<td>		         		
	  			      		<bean:write name="feedbackData" property="variableOffered" />
	         				</td>
	         				<td>&nbsp;</td>
	         			</tr>
	         		</table>
					</td>			
				</logic:notEmpty>
			</logic:equal>
			
			</tr>			
			</table>
			<table class="tblPop" width="100%">		
			<tr>
					<td>				
						<div id="feedbackDataDiv" class="outerDiv feedbackDiv" style="padding: 5px;">
							<table cellpadding="0" cellspacing="0" class="feedbackform" style="width:784px; ">
							 
								<logic:notEmpty property="fromStepData.traits" name="feedbackData">
								<% String formTitle=""; %>
								<logic:iterate id="trait" property="fromStepData.traits" name="feedbackData" indexId="cnt" type="TraitData">
								<% if(!formTitle.equals(trait.getFeedbackFormTitle())){
									formTitle=trait.getFeedbackFormTitle();
								%>
								<tr>
		    						<td valign="top" colspan="3" class="category">
		    							<bean:write name="trait" property="feedbackFormTitle"/>
		    						</td>  												    								    						
		    					</tr>
		    					<% } %>				      			
				      			
		              			<tr>
		              				<td>&nbsp;</td>
		              				<td width="5px"></td>
		    						<td class="criteria" valign="top" style="word-wrap:break-word;">		    						
		    							<bean:write name="trait" property="feedbackFieldTitle"/>
		    						</td>  												    								    						
		    					</tr>
		    					<logic:notEmpty name="trait" property="feedbackFormFieldDesc">
		              			<tr>
		              				<td>&nbsp;</td>
		              				<td width="5px"></td>
		    						<td valign="top" style="word-wrap:break-word;"><pre class="criteriacomment"><bean:write name="trait" property="feedbackFormFieldDesc"/></pre></td>  												    								    						
		    					</tr>
		    					</logic:notEmpty>
								<logic:iterate id="interviewer" name="interviewers" type="UserData"> 
										<%
										String ratingFieldId = (String)traitData.get(SelectionProcessConstants.RATING_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
										String ratingFieldDesc = (String)traitData.get(SelectionProcessConstants.RATINGDESC_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
										String ratingId = trait.getRatingId();
										String interviewerComment = traitData.get(trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()).toString().trim();
										
										String multipleSelectFieldId = (String)traitData.get(SelectionProcessConstants.MULTIPLE_SELECT_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());									
										String multipleSelectId = trait.getMultipleSelectId();
										
												
										if(!Utils.isBlankOrNull(ratingFieldId) || !Utils.isBlankOrNull(interviewerComment) || !Utils.isBlankOrNull(multipleSelectFieldId)){
										%>
									<tr>
										<td valign="top" class="Grey"><strong><bean:write name="interviewer" property="userName"/></strong></td>
										<td width="5px"></td>
			    						<td valign="top" style="word-wrap:break-word;width:620px;">
										<% 
										if(!Utils.isBlankOrNull(ratingId)){
										%>
										<table class="ratingcontent">
										<tr>
										<%
											RatingsData ratingsData = CommonUtils.getRatingsData(ratingId);
											ArrayList ratingFields = ratingsData.getRatingFields();
											for(int r=0; ratingFields!=null && r<ratingFields.size();r++){
												RatingFieldsData ratingFieldData = (RatingFieldsData)ratingFields.get(r);
										%>
										<td style="padding-right: 0px;">
										<%		
												if(ratingFieldData.getRatingFieldId().equals(ratingFieldId)){
										%>
											<img src="images/checkedradiobutton.gif" name="rdo">
										<%
												}else{
										%>
											<img src="images/radiobutton.gif" name="rdo">
										<%
												}
										%>
										</td>
										<td class="title">
										<%=Utils.escapeHTML(ratingFieldData.getRatingFieldDesc())%>
										</td>
										<%		
											}
										%>
										</tr>
										</table>
										<%	 
										}
										%>
										
										<% 
										if(!Utils.isBlankOrNull(multipleSelectId)){				
											List selectedFields = new ArrayList();
											if(!Utils.isBlankOrNull(multipleSelectFieldId)){
												selectedFields = Arrays.asList(multipleSelectFieldId.split(","));
											}
											String multipleSelectFieldDesc = (String)traitData.get(SelectionProcessConstants.MULTIPLE_SELECTDESC_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
											
										%>
										<table class="ratingcontent">
										<tr>
										<%
											MultipleSelectsData multipleSelectsData = CommonUtils.getMultipleSelectsData(multipleSelectId);
											ArrayList multipleSelectFields = multipleSelectsData.getMultipleSelectFields();
											for(int s=0; multipleSelectFields!=null && s<multipleSelectFields.size();s++) {
												MultipleSelectFieldsData multipleSelectFieldData = (MultipleSelectFieldsData)multipleSelectFields.get(s);											
										%>
										<td style="padding-right: 0px;">
										<%		
												if(selectedFields.contains(multipleSelectFieldData.getSelectFieldId())){
										%>
											<img src="images/checkboxchecked.gif" name="rdo">
										<%
												}else{
										%>
											<img src="images/checkboxunchecked.gif" name="rdo">
										<%
												}
										%>
										</td>
										<td class="title">
										<%=Utils.escapeHTML(multipleSelectFieldData.getSelectFieldDesc())%>
										</td>
										<%		
											}
										%>
										</tr>
										</table>
										<%	 
										}
										%>
			    						<pre class="criteriafeedback"><%=Utils.escapeHTML(traitData.get(trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()).toString().trim())%></pre>
			    						</td>					    						
			                      </tr>
			                      <%} %>               
								</logic:iterate>
								<tr><td style="height:5px;" colspan="3">&nbsp;</td></tr>
				      			
								</logic:iterate> 						
								</logic:notEmpty>
							</table>						
					</div>
				</td>
			</tr>
		</table>
	</div>
	</logic:notEmpty>
	</logic:notEmpty>
			<% } %>
			<% if (SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT.equalsIgnoreCase(""+feedbackData.getToStepData().getStepId())) { %>
			<div class="popupBody">
				<table class="tblPop" width="100%">					
					<tr>
						<td>
							<div id="feedbackDataDiv" class="outerDiv feedbackDiv" >
								<table cellpadding="0" cellspacing="0" class="tblPop"> 
		              <tr>
		    						<td class="Grey" width="100" valign="top"><%=SelectionProcessConstants.COMMENT%>:</td>  												    								    						
										<td>
											<logic:notEmpty property="fromStepData.traits" name="feedbackData">
											<logic:iterate id="trait" property="fromStepData.traits" name="feedbackData" indexId="cnt" type="TraitData">									
					              <pre><bean:write name="trait" property="traitComment"/></pre>
											</logic:iterate> 
											</logic:notEmpty>
										</td>
									</tr>
								</table>	
							</div>								
						</td>				
					</tr>
				</table>
			</div>
			<% } %>
					<div class="navBtn noprint" style="float: right;"><br/>
					<logic:present name="isEditable" scope="request">
					<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: editInteraction();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
					</logic:present>
					<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: showPrintOptions();return false;" id="printButton"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.print"/></a>
					<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
</div>
</div>
<style type="text/css">
.divPrintOptions{
 width: 250px; height: 250px; 
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
			<b><bean:message key="selection_feedback.label.print_feedback_by"/></b>
		</td>
   	</tr>
	<tr>				  			
		<td>
			<script type="text/javascript">	
			var opts = new Array();
			<logic:notEmpty name="interviewers">
			<logic:iterate id="interviewer" name="interviewers" type="UserData">
				opts[opts.length] = new SelectOption('<bean:write name="interviewer" property="userId"/>','<bean:write name="interviewer" property="userName" filter="false"/>');
			</logic:iterate>
			</logic:notEmpty>
			var checkBoxListUsers = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'210px', size:5, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
			document.write(checkBoxListUsers.getHtml());
			checkBoxListUsers.init();
			checkBoxListUsers.selectAll(true);
			</script>
		</td>
   	</tr>	
   	<tr>				  			
		<td style="padding-top: 10px;">
			<img src="images/checkboxchecked.gif" onclick="javascript: collateFeedback(this);" >&nbsp;<bean:message key="selection_feedback.label.collate_feedback"/>
		</td>
   	</tr> 	
   	<tr>				  			
		<td style="padding-top: 10px;">
			<img src="images/checkboxchecked.gif" onclick="javascript: changeToShowIfExist(this);" >&nbsp;<bean:message key="selection_feedback.label.show_if_exist"/>
		</td>
   	</tr> 	
	<tr>				  			
		<td>
			<div class="navBtn" style="float: left;"><br/>
			<a href="#" style="width:80px;margin-right:5px;" class="active" onclick="javascript: showFormReport('<%=ReportConstants.FORMAT_HTML%>');return false;" id="printButton"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.print"/></a>
			<a href="#" style="width:80px;margin-right:5px;" class="active" onclick="javascript: showFormReport('<%=ReportConstants.FORMAT_PDF%>');return false;" id="printButton"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.export"/></a>
			<a href="#" style="width:60px;" class="active" onclick="javascript: hidePrintOptions();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
		</td>
   	</tr>
   </table>
										
</div>
</html:form>

<script language="JavaScript">
function showPrintOptions(){
	Effect.BlindDown('divPrintOptions',{duration:0.5});
}
function hidePrintOptions(){
	Effect.BlindUp('divPrintOptions',{duration:0.5});
}

var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";

function changeToShowIfExist(obj) {
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;			
		document.selectionProcessForm.showIfExist.value="true";
	} else {
		obj.src=chkboxchked;	
		document.selectionProcessForm.showIfExist.value="false";
	}
}

function collateFeedback(obj) {
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;			
		document.selectionProcessForm.reportType.value='<%=SelectionProcessConstants.REPORT_TYPE_USERWISE%>';
	} else {
		obj.src=chkboxchked;	
		document.selectionProcessForm.reportType.value='<%=SelectionProcessConstants.REPORT_TYPE_CONSOLIDATED%>';
	}
}

function showFormReport(reportFormat){	
	var feedbackBy = checkBoxListUsers.getSelectedIds();
	if(feedbackBy==''){
		alert( '<bean:message key="selection_feedback.error.select_feedback_users"/>');
		return false;
	}	
	document.selectionProcessForm.interviewerIds.value=checkBoxListUsers.getSelectedIds();
	document.selectionProcessForm.reportFormat.value=reportFormat;
	var d = new Date();
	document.selectionProcessForm.target=d;
	document.selectionProcessForm.submit();
}

function editInteraction(){
	window.location= 'selectionProcess.do?mode=moveApplicantUpOrDown&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>&communicationType=<bean:write property="communicationType" name="selectionProcessForm"/>&communicationId=<bean:write property="communicationId" name="selectionProcessForm"/>';
}
function setPopupTitle(){
	var title = '<b><bean:message key="selection_feedback.label.feedback_for"/> - </b><bean:write name="feedbackData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
   		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="feedbackData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
	document.selectionProcessForm.reportType.value='<%=SelectionProcessConstants.REPORT_TYPE_CONSOLIDATED%>';
}
window.onload = setPopupTitle;
</script>