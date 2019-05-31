<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,com.talentPool.selectionProcess.SelectionProcessConstants,
                com.talentPool.selectionProcess.form.SelectionProcessForm,
                com.talentPool.selectionProcess.dataobject.FeedbackData,
                com.talentPool.common.properties.GlobalConstants,
                com.talentPool.common.properties.GlobalApplicationProperties,
                com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                com.talentPool.common.utils.Utils" %>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
                
<logic:present name="update" scope="request">
<script>
	window.top.hidePopWin(true);
</script>
</logic:present>                
<logic:notPresent name="update" scope="request">
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<bean:define id="feedbackData" name="feedbackData" scope="request" type="FeedbackData" />
<div class="contentDivPop"  >
<%
	String sendFlag = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_FEEDBACK_REMINDERS);
	sendFlag = Utils.isBlankOrNull(sendFlag) ? "0" : sendFlag;
	String sendFeedbackReminderEnable=GlobalConstants.ENABLED;
	if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
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
<br>
<% } else { %>
<html:form action="/selectionProcess">
  <html:hidden property="mode"/>
  <html:hidden property="applicantId" name="selectionProcessForm"/>
  <html:hidden property="currentPositionStepId" value='<%=""+feedbackData.getFromStepData().getStepId()%>' />
  <html:hidden property="nextPositionStepId" name="selectionProcessForm" value="<%=SelectionProcessConstants.STEP_INVALID%>"/>
  <html:hidden property="communicationId" name="selectionProcessForm"/>
  <html:hidden property="appointmentId" name="selectionProcessForm"/>
  <html:hidden property="positionId" name="feedbackData"/>
  <html:hidden property="attendeeId" name="selectionProcessForm"/>
  <html:hidden property="attendee" name="selectionProcessForm"/>
	<div class="bottomDivSec" style="width:730px;">
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
          <td style="height:5px;"></td>
       	</tr>
		    <tr>
		       <td class="header"><bean:message key="selection_feedback.label.selectionStep"/></td>
		       <td><bean:write name="feedbackData" property="fromStepData.stepTitle"/></td>
		    </tr>
	    </table>
		</div>
		<div class="popupTop">
			<table class="tblPop" >
				<tr>
	        <td class="header"><bean:message key="selection_feedback.label.editSelectionStep"/></td>
	     	</tr>
	     	<tr>                          
	       <td>
	         <span id="<%="img" + SelectionProcessConstants.STEP_ATTENDED%>" onclick="javascript: radioBttnClicked('<%=SelectionProcessConstants.STEP_ATTENDED%>');showDiv('showAllAttendee');">                        
	           <img src="images/radiobutton.gif" />
	         </span>                  
	         &nbsp;<bean:message key="selection_feedback.label.attended"/>
	         <!--  Show all attendee  -->
			   <div class="contentDivPop" id="showAllAttendee" style="display:none;margin:5px 0px 5px 0px;">
			 	 <div class="outerDiv" >
					<div class="popupBody">
				       <table class="tblPop" >
				         <tr>
				         <td>
				         	<bean:message key="selection_feedback.label.send_feedback_reminder_email"/>
				         </td>
				         </tr>
				         <tr>
				           <td> 
			             	 <script type="text/javascript">
			                    var opts = <bean:write name="attendeesList" scope="request" filter="false"/>;
			                    checkboxListAttendee = new CheckBoxList(opts,'<bean:write name="attendeesId" scope="request"/>',{namesonly:false, layerclass:'checkboxlistdiv', width:'190px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
			                    document.write(checkboxListAttendee.getHtml());
			                    checkboxListAttendee.init();
			                    checkboxListAttendee.selectAll(true);
			             	 </script>
			              </td>  
				         </tr>
				   	  </table>
				   </div>    	      
				</div>    	      
			 </div>               
	       </td>
				    
	     </tr>
	     <tr>                          
	       <td>
	         <span id="<%="img" + SelectionProcessConstants.STEP_NOT_ATTENDED%>" onclick="javascript: radioBttnClicked('<%=SelectionProcessConstants.STEP_NOT_ATTENDED%>');hideDiv('showAllAttendee');">                        
	           <img src="images/radiobutton.gif" />
	         </span>                  
	         &nbsp;<bean:message key="selection_feedback.label.do_not_attended"/>
	       </td>      
	     </tr>
	     <tr>                          
	       <td>
	         <span id="<%="img" + SelectionProcessConstants.STEP_REPEAT%>" onclick="javascript: radioBttnClicked('<%=SelectionProcessConstants.STEP_REPEAT%>');hideDiv('showAllAttendee');">                        
	           <img src="images/radiobutton.gif" />
	         </span>                  
	         &nbsp;<bean:message key="selection_feedback.label.repeatStep"/>
	       </td>      
	     </tr>  
	     <script language="JavaScript">
	     		nextStepsIds = "<%=SelectionProcessConstants.STEP_ATTENDED%>" + "," + "<%=SelectionProcessConstants.STEP_NOT_ATTENDED%>" + "," +"<%=SelectionProcessConstants.STEP_REPEAT%>";
	     		stepAttended = "<%=SelectionProcessConstants.STEP_ATTENDED%>";
	     </script>  
		</table>	
	</div>
	
	<div class="navBtn" style="float: right;"><br/>
		<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: saveChanges();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
		<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
		</div>
</html:form>
<script language="JavaScript">

function radioBttnClicked(param) {
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
  if ('<%=sendFeedbackReminderEnable%>'=='<%=sendFlag %>'){	
	var elem = document.getElementById(divId);
	if (elem) {
		elem.style.display='';
	}
  }
}
function hideDiv(divId) {
	var elem = document.getElementById(divId);
	if (elem) {
		elem.style.display='none';
	}
}

function saveChanges() {
		if (document.selectionProcessForm.nextPositionStepId.value == '<%=SelectionProcessConstants.STEP_INVALID%>') {
			alert('<bean:message key="selection_feedback.error.please_select_step"/>');
			return false;
		}
		
		//get attendees
		var frm = document.selectionProcessForm;
		frm.attendeeId.value="";
		frm.attendee.value="";
		var aIds=checkboxListAttendee.getSelectedIds();
		if(aIds==""){
			alert('<bean:message key="selection_feedback.error.please_select_interviewer"/>');
			return false;
		}
		if(aIds!=""){
			frm.attendeeId.value= aIds; 
			frm.attendee.value=checkboxListAttendee.getSelectedText(", ");
		}
		
	  document.selectionProcessForm.mode.value = 'saveSelectionProcessResult'; 
	  document.selectionProcessForm.submit();
	  return true;  
}

function setPopupTitle(){
	var title = '<b><bean:message key="selection_feedback.label.submit_feedback"/> - </b><bean:write name="feedbackData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="feedbackData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}
window.onload = setPopupTitle;
</script>
<% } %>
</logic:notPresent>
