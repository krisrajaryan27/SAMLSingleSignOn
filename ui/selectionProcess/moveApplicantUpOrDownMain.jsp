<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/tp-applicant.tld" prefix="appField" %>
<%@ page isELIgnored="false" %>
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
                com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                java.lang.Boolean,
                java.util.List,java.util.Map, java.lang.StringBuffer,java.util.Arrays,
                com.talentPool.common.properties.TPApplicationProperties,
                com.talentPool.masters.constants.FeedbackFieldsConstant" %>
<%@page import="com.talentPool.masters.constants.FeedbackFormConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.masters.dataobject.RatingsData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.masters.dataobject.RatingFieldsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectsData"%>
<%@page import="com.talentPool.masters.dataobject.MultipleSelectFieldsData"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="org.apache.struts.Globals"%><logic:present name="update" scope="request">
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
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
<%
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
<%} %> 
<logic:notPresent name="errors" scope="request">
<logic:notPresent name="update" scope="request">
<%
	String userId = (String) request.getSession(false).getAttribute("userId");
	PermissionSet permissionSet 		= (PermissionSet)request.getSession(false).getAttribute("permissionSet");
	boolean ctcOfferedViewable 			= ImportConfigurationManager.isCTCOfferedViewable(permissionSet);
	boolean basicOfferedViewable 		= ImportConfigurationManager.isBasicOfferedViewable(permissionSet);
	boolean designationOfferedViewable 	= ImportConfigurationManager.isDesignationOfferedViewable(permissionSet);
	boolean levelOfferedViewable 		= ImportConfigurationManager.isLevelOfferedViewable(permissionSet);
	boolean inputSalaryVariableViewable 		= ImportConfigurationManager.isInputSalaryVariableViewable(permissionSet);
	pageContext.setAttribute("ctcOfferedViewable",ctcOfferedViewable);
	pageContext.setAttribute("basicOfferedViewable",basicOfferedViewable);
	pageContext.setAttribute("designationOfferedViewable",designationOfferedViewable);
	pageContext.setAttribute("levelOfferedViewable",levelOfferedViewable);
	pageContext.setAttribute("inputSalaryVariableViewable",inputSalaryVariableViewable);
%>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>  
<link rel="stylesheet" type="text/css" href="themes/default/tpcalendar.css" /> 
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/calender.css"/>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/calender/monthYearCalender.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>    
<script language="JavaScript" src="js/calender/draggablePopUp.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script language="JavaScript" src="js/customfields/customfieldvalidator.js"></script>
<script language="JavaScript" src="js/calender/monthYearCalender.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.config.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/box.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/yahoo-dom-event.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/tip_ajaxcall.js"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script>
var isHIreStage 			= false;
var chckdRadioImg 			= "images/checkedradiobutton.gif";
var radioImg 				= "images/radiobutton.gif";
var enabled 				= <%=GlobalConstants.ENABLED%>;
var validateCtc 			= <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC)%>;
var inputSalaryVariableLabel = '<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL) %>';
var isEmployeeCodeMandatory = <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_IS_EMPLOYEE_CODE_MANDATORY)%>;



function getSingleElement(parent,tagName,defVal){
	try {
		return parent.getElementsByTagName(tagName)[0].firstChild.nodeValue;
	} catch( myError ) {}
	return defVal;
}

function saveChanges() {

	if (document.selectionProcessForm.nextPositionStepId.value == '<%=SelectionProcessConstants.STEP_INVALID%>') {
		<logic:equal name="selectionProcessForm" property="isUserAuthorizedToMove" value="<%=Boolean.TRUE.toString()%>">
			alert('<bean:message key='selection_feedback.error.please_select_step' />');
			return false;
		</logic:equal>	
		<logic:notEqual name="selectionProcessForm" property="isUserAuthorizedToMove" value="<%=Boolean.TRUE.toString()%>">
			document.selectionProcessForm.nextPositionStepId.value = '<%=SelectionProcessConstants.STEP_ON_HOLD%>';
		</logic:notEqual>	
	}
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
	var elem2 = document.getElementById('date');		 

    if (elem2) {
    	
    	if (document.selectionProcessForm.nextPositionStepId.value == '' && elem2.value == '') {
				// Applicant is moved to joined. Check for joining date.
				alert('<bean:message key='selection_feedback.error.please_enter_joining_date' />');
				return false;
		}
    	if(enabled == isEmployeeCodeMandatory){
    		if (document.selectionProcessForm.nextPositionStepId.value == '' && $("employeeCode").value == '') {
				// Applicant is moved to joined. Check for joining date.
				alert("<bean:message key="common.please_enter" /> <bean:message key="common.emp_code" />");
				return false;
			}
    	}
   		var valueOfElm = elem2.value;
   		var dtfo = new DateFormatter();
   		dtfo.setDisplayFormat("dd/mm/yyyy");
   		var date1 = dtfo.getDateObject(valueOfElm);
   		var date2 = new Date();
   		date2.setHours(0,0,0,0);
   		if (document.selectionProcessForm.nextPositionStepId.value != '<%=SelectionProcessConstants.STEP_REJECT%>'&&document.selectionProcessForm.nextPositionStepId.value !='<%=SelectionProcessConstants.STEP_ON_HOLD%>'){
   			
   			if(document.selectionProcessForm.nextPositionStepId.value == ''){
   			
   			document.selectionProcessForm.joiningDate.value=elem2.value;
   			
   		}
   		else{
   		if(date2>date1){
   			alert("Please enter date greater or equal to today's date");
   			return false;
   		}
   		document.selectionProcessForm.joiningDate.value=elem2.value;
   		}
   		}
    }
    var elem3 = document.getElementById('ctcOffered');		 
    if(elem3 && enabled == validateCtc){
	    var ctcOffered = document.selectionProcessForm.ctcOffered.value;
	    if(ctcOffered!= ''){
		    if(isNaN(ctcOffered)){
		    	alert('<bean:message key='selection_feedback.error.please_enter_ctc_number' />');
		    	return false;
			}else if(ctcOffered<0){
				alert('<bean:message key='selection_feedback.error.please_enter_ctc_number' />');
		    	return false;
			}
	    }
    }
    var elem4 = document.getElementById('basicOffered');
    if(elem4 && enabled == validateCtc){
	    var basicOffered = document.selectionProcessForm.basicOffered.value;
	    if(basicOffered!= ''){
		    if(isNaN(basicOffered)){
		    	alert('<bean:message key='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}else if(basicOffered<0){
				alert('<bean:message key='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}
	    }
    }
    
    var elem5 = document.getElementById('joiningBonus');
    if(elem5 && enabled == validateCtc){
	    var joiningBonus = document.selectionProcessForm.joiningBonus.value;
	    if(joiningBonus!= ''){
		    if(isNaN(joiningBonus)){
		    	alert('<bean:message key='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}else if(joiningBonus<0){
				alert('<bean:message key='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}
	    }
    }
    var elem6 = document.getElementById('variableOffered');
    if(elem6 && enabled == validateCtc){
	    var variableOffered = document.selectionProcessForm.variableOffered.value;
	    if(variableOffered!= ''){
		    if(isNaN(variableOffered)){
		    	alert('<bean:message key='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}else if(variableOffered<0){
				alert('<bean:message key='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}
	    }
    }
    
    if($('inputSalaryVariable') && (!isInt($('inputSalaryVariable').value) || $('inputSalaryVariable').value<0)){
    	alert('<bean:message key="selection_feedback.error.please_enter_input_salary_variable_number_as"  />' + ' ' + inputSalaryVariableLabel);
    	return false;
    }
    
    if (document.selectionProcessForm.nextPositionStepId.value == '<%=SelectionProcessConstants.STEP_REJECT%>'
    	|| document.selectionProcessForm.nextPositionStepId.value == '<%=SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT%>') {    	
       var pars = "mode=checkIfAppointmentExists&applicantId=" + document.selectionProcessForm.applicantId.value;
	   var myAjax = ajaxCall("selectionProcess.do",'get',pars,saveChanges2, reportError);
    } else {
       saveChanges2(null);
    }    
}

function isInt(n) {
   return (!isNaN(n) && n % 1 == 0);
}

function saveChanges2(request){
  if (request != null) {
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
      return;
    }
  }
  
  document.selectionProcessForm.deleteAppointments.value = false;
  if (document.selectionProcessForm.nextPositionStepId.value == '<%=SelectionProcessConstants.STEP_REJECT%>'
	  || document.selectionProcessForm.nextPositionStepId.value == '<%=SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT%>') {
    errors = xmlFile.getElementsByTagName("errors")[0]; 
    error = getSingleElement(errors, "error", "");
    if (error == 'selection_feedback.error.appointment_exists') {
      show('popUpWindow');
      drop();
    } else {
      saveChanges5();
    } 
  } else {
    saveChanges5();
  }  
}

function saveChanges5(){
	
	var pars;
	if(document.selectionProcessForm._nextPositionStepId.value == null || document.selectionProcessForm._nextPositionStepId.value.length ==0){
		pars = "mode=checkIfSourceEmployeeExistInSelectionProcessStepForPosition&applicantId=" + document.selectionProcessForm.applicantId.value+"&nextPositionStepId="+document.selectionProcessForm.nextPositionStepId.value;
	}
	else{
		pars = "mode=checkIfSourceEmployeeExistInSelectionProcessStepForPosition&applicantId=" + document.selectionProcessForm.applicantId.value+"&nextPositionStepId="+document.selectionProcessForm._nextPositionStepId.value;
	}
	var myAjax = ajaxCall("selectionProcess.do",'get',pars,onCheck, reportError);
}

function onCheck(request){
	xmlFile = request.responseXML;
	var errorMsg = '';
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;  	
  	if(isErrorXml(xmlFile)){
  		 errors = getErrors(xmlFile);  	    
  	   if(errors != null && errors.length > 0){  		
  		 errorMsg+=errors[0].split(',')[0]+", who referred "+errors[0].split(',')[1]+" is on the selection panel for the next step.\n";
 		}
  	}
  	saveChanges3();
}

function saveSelectionProcessResult(){
	document.selectionProcessForm.mode.value = 'saveSelectionProcessResult'; 
	document.selectionProcessForm.submit();
	return true;
}

/***
 *If next step is not On hold or reject, then check if all users have entered feedback.
 ***/
function saveChanges3() {
	var nextPosStepId = document.selectionProcessForm.nextPositionStepId.value;
	if (nextPosStepId =='<%=SelectionProcessConstants.STEP_REJECT%>' || 
	 	 nextPosStepId  == '<%=SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT%>'
			|| nextPosStepId  == '<%=SelectionProcessConstants.STEP_ON_HOLD%>' ) {
		saveSelectionProcessResult();
  }else {
	  var pars = "mode=checkStepUsersActionIsPending";
		  pars+= "&applicantId=" + document.selectionProcessForm.applicantId.value;
		  pars+= "&currentPositionStepId=" + document.selectionProcessForm.currentPositionStepId.value;
		  pars+= "&communicationId=" + document.selectionProcessForm.communicationId.value;
	  var myAjax = ajaxCall("selectionProcess.do",'post',pars,onCheckStepUsersActionIsPending, reportError);
  }
}

function onCheckStepUsersActionIsPending(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
    if(isErrorXml(xmlFile)){
    	errors = getErrors(xmlFile);
    	if(errors!=null && errors.length>0){
        	var pendingActionUsers = errors[0] + ' ' + '<bean:message key="selection_feedback.error.pending_action" />'; 
    		if(confirm(pendingActionUsers)){
        		saveSelectionProcessResult();
            }
        }else {
        	saveSelectionProcessResult();
        }
 	}else {
 		saveSelectionProcessResult();
 	}
}

function saveChanges4(val) {
  document.selectionProcessForm.deleteAppointments.value = val;
  hide('popUpWindow');
  saveChanges3();
}

function onChangeSelectionStep(index,Obj){
	var nextStepId = Obj.getSelectedId();
	if(nextStepId=='-11'){
		$('selectPositionAndStepDiv').show();
		selectBoxPosition.setSelected(selectBoxPosition.getIndexWithId('-1'));
		hideMandatoryStars();
	}else if(nextStepId==''){ // This implies a candaidte is being moved to joined
		$('selectPositionAndStepDiv').hide();
		document.selectionProcessForm.nextPositionStepId.value = nextStepId;
		showMandatoryStars();
	}else {
		$('selectPositionAndStepDiv').hide();
		document.selectionProcessForm.nextPositionStepId.value = nextStepId;
		hideMandatoryStars();
	}
}
function showMandatoryStars(){
	if($('joining_date_mand'))
		$('joining_date_mand').show();
	if(enabled == isEmployeeCodeMandatory){
		if($('emp_code_mand'))
			$('emp_code_mand').show();
	}
}

function hideMandatoryStars(){
	if($('joining_date_mand'))
		$('joining_date_mand').hide();
	if($('emp_code_mand'))
		$('emp_code_mand').hide();
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
	actionsJsArray.setSelected(actionsJsArray.getIndexWithId(document.selectionProcessForm.nextPositionStepId.value));	
	var elem = document.getElementById(divId);
	if (elem) {
		elem.style.display='none';
	}
}

function selectedPositionChanged() {
  var val = selectBoxPosition.getSelectedId();
  var pars = "mode=getStepsInXml&positionId=" + val;
  var myAjax = ajaxCall("selectionProcess.do",'get',pars,updateSteps, reportError);
}

function updateSteps(request){
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }
  //First remove all options
  var opts = new Array();
  
  var steps = xmlFile.getElementsByTagName("steps")[0];
  var step= steps.getElementsByTagName("step");
  if(step != null) {
  	for(var i = 0; i < step.length; i++){  		
  		var id = step[i].getAttribute("id");
  		var title = step[i].firstChild.nodeValue;
  		opts[i] = new SelectOption(id, title);
  	}
  }
  var m = [new SelectOption('-1', '<bean:message key='selection_feedback.label.selectStep' />')];
  opts = m.concat(opts);
  selectBoxStep.reInitialize(opts, '');
}

function moveToOtherPosition() {
	var error = '';
	var pos = selectBoxPosition.getSelectedId();
	if (pos == -1) {
		step += 'Please select the position.'
	}
	var step = selectBoxStep.getSelectedId();
	if (step == -1) {
		if (error.length > 0) {
			error += '\n';
		}
		error += 'Please select the step.'
	}
	if (error.length > 0) {
		alert(error);
		return;
	}
	alert("Future appointments for current position, if any will be deleted.");
	document.selectionProcessForm.nextPositionStepId.value = <%=SelectionProcessConstants.STEP_REJECT%>;
	document.selectionProcessForm._positionId.value = pos;
	document.selectionProcessForm._nextPositionStepId.value = step;
	saveChanges5();
}
function viewApplicant(aId){
	window.open("selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	return false;
}
</script>
<%
  SelectionProcessForm selectionProcessForm = (SelectionProcessForm) request.getAttribute("selectionProcessForm");
%>
<bean:define id="feedbackData" name="feedbackData" scope="request" type="FeedbackData" />
<bean:define id="interviewers" name="interviewers" scope="request" type="List" />
<bean:define id="traitData" name="traitData" scope="request" type="Map" />
<logic:equal name="selectionProcessForm" property="isUserAuthorizedToMove" value="<%=Boolean.TRUE.toString()%>">
	<logic:notEmpty name="feedbackData" property="nextSteps">
		<logic:iterate id="stepData" name="feedbackData" property="nextSteps" type="com.talentPool.positions.dataobject.StepData">
			<logic:equal value="<%=SelectionProcessConstants.STEP_TITLE_JOINED%>" property="stepTitle" name="stepData">
				<bean:define id="isHireStage" value="1"></bean:define>
				<script>isHIreStage = true;</script>
			</logic:equal>
			<logic:equal value="<%=PositionConstants.STEP_LEVEL_ACCEPT%>" property="stepLevel" name="stepData">
				<script>isHIreStage = true;</script>
				<bean:define id="isHireStage" value="1"></bean:define>
			</logic:equal>
		</logic:iterate>
	</logic:notEmpty>
</logic:equal>
<div class="contentDivPop" >
<html:form action="/selectionProcess">
  <html:hidden property="mode"/>
  <html:hidden property="applicantId" name="selectionProcessForm"/>
  <html:hidden property="traitIds" name="selectionProcessForm"/>
  <html:hidden property="currentPositionStepId" name="selectionProcessForm"/>
  <html:hidden property="nextPositionStepId" name="selectionProcessForm" />
  <html:hidden property="stepLevel" name="selectionProcessForm" />  
  <html:hidden property="communicationId" name="selectionProcessForm"/>
  <html:hidden property="positionId" name="feedbackData" styleId="positionId" />
  <html:hidden property="deleteAppointments" name="selectionProcessForm"/>
  <html:hidden property="moveUpOrDownResult" name="selectionProcessForm"/>
  <html:hidden property="_positionId" name="selectionProcessForm"/>
  <html:hidden property="_nextPositionStepId" name="selectionProcessForm"/>
  <html:hidden property="joiningDate" name="selectionProcessForm"/>
  <html:hidden property="isUserAuthorizedToMove" name="selectionProcessForm"/>
   <html:hidden property="gradeId" name="selectionProcessForm"/>
   <html:hidden property="feedbackFormId" name="feedbackData" />
	<div class="bottomDivSec">
	<div class="vpTop" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
      <tr>
        <td>
        	<strong id="VP_TITLE" class="Grey">
        		<a href="#" onclick="viewApplicant('<bean:write name="selectionProcessForm" property="applicantId"/>');"  onmouseover="showAjaxTip(event,'<bean:write name="selectionProcessForm" property="applicantId"/>')"  onmouseout="hideToolTip()" ><bean:write name="feedbackData" property="applicantName"/></a>&nbsp;
	        	<logic:present name="educationalInfo" scope="request">
	        		&nbsp;<span title="<bean:write name="educationalInfo" scope="request" />" ><bean:message key="common.education" />: <bean:write name="educationalInfoTrimmed" scope="request" /></span>
	        	</logic:present>
 			</strong>
 		</td>
      </tr>
    </table>
	</div>
	</div>
	<div class="outerDiv" style="border-top:0px;">
		<div class="popupTop">
			<table class="tblPop" >
				<tr>
	         		<td class="header" width="110px;" ><bean:message key="common.position"/>:</td>
	         		<td><bean:write name="feedbackData" property="positionTitle"/></td>
	     		</tr>
			     <tr>
			         <td class="header" width="110px;" ><bean:message key="selection_feedback.label.selectionStep"/></td>
			         <td><bean:write name="feedbackData" property="fromStepData.stepTitle"/></td>
			     </tr>
			     <logic:notEmpty name="pendingActionUsers" scope="request">
			     	<tr>
			         	<td class="header" width="110px;" ><bean:message key="selection_feedback.label.pending_action"/>:</td>
			         	<td><bean:write name="pendingActionUsers" scope="request" />&nbsp;<bean:message key="selection_feedback.message.pending_action" /></td>
			     	</tr>
			     </logic:notEmpty>
	    	</table>
		</div>
		<div class="popupBody">
			<table class="tblPop" width="100%">
				<tr>
					<td>
						<logic:present name="isHireStage" scope="page">
							<table class="tblPop">
								<tr>
									<td>
					   					<bean:message key="selection_feedback.label.joining_date"/>
					   					<span id="joining_date_mand" style="color:red;display: none;">*</span>
					   				</td>
					   				<td width="110px">
					   					<input type="text" name="date" id="date" size="11" value="<bean:write name="feedbackData" property="joiningDate" format="dd/MM/yyyy"/>" onblur="javascript: getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('date'),'date','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
					   				</td>
					   				<logic:equal value="true" name="designationOfferedViewable" >
					       				<td>
					       					<bean:message key="selection_feedback.label.designation_offered"/>
					       				</td>
					       				<td width="110px">
					       					<input type="text" name="designationOffered" id="designationOffered" size="11" maxlength="50"  value="<bean:write name="feedbackData" property="designationOffered"/>" />
					       				</td>
						         	</logic:equal>
						         	<logic:notEqual value="true" name="designationOfferedViewable" >
						         		<td>&nbsp;</td>
						         		<td width="110px">&nbsp;</td>
						         	</logic:notEqual>
						         	<logic:equal value="true" name="ctcOfferedViewable" >
										<td>
											<bean:message key="selection_feedback.label.ctc_offered"/>
										</td>
										<td width="110px">
											<input type="text" name="ctcOffered" id="ctcOffered" size="11"  maxlength="10"  value="<bean:write name="feedbackData" property="ctcOffered"/>" />
										</td>
									</logic:equal>		
									<logic:notEqual value="true" name="ctcOfferedViewable" >
										<td>&nbsp;</td>
						         		<td width="110px">&nbsp;</td>
									</logic:notEqual>	
									<logic:equal value="true" name="basicOfferedViewable" >
										<td>
											<bean:message key="selection_feedback.label.basic_offered"/>
										</td>
										<td width="110px">
				         				<input type="text" name="basicOffered" id="basicOffered" size="11"  maxlength="10"  value="<bean:write name="feedbackData" property="basicOffered"/>" />
										</td>
									</logic:equal>
									<logic:notEqual value="true" name="basicOfferedViewable" >
										<td>&nbsp;</td>
						         		<td width="110px">&nbsp;</td>
									</logic:notEqual>	
								</tr>
								<tr>
									<td>
				   						<bean:message key="selection_feedback.label.employee_code"/>
					   					<span id="emp_code_mand" style="color:red;display: none">*</span>
				   					</td>
				   					<td width="110px">
				   						<input type="text" name="employeeCode" id="employeeCode" size="11" maxlength="100"  value="<bean:write name="feedbackData" property="employeeCode"/>" />
				   					</td>
				   					<logic:equal value="true" name="levelOfferedViewable" >
										<td>
											<bean:message key="selection_feedback.label.level_offered"/>
										</td>
										<td width="110px">
											<input type="text" name="levelOffered" id="levelOffered" size="11"  maxlength="20" value="<bean:write name="feedbackData" property="levelOffered"/>" />
										</td>
				        			</logic:equal>
				        			<logic:notEqual value="true" name="levelOfferedViewable" >
				        				<td>&nbsp;</td>
						         		<td width="110px">&nbsp;</td>
				        			</logic:notEqual>
				        			<logic:equal value="true" name="inputSalaryVariableViewable" >
										<td>
											<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL) %>
										</td>
										<td width="180px" colspan="3">
				         				<input type="text" name="inputSalaryVariable" id="inputSalaryVariable" size="11"  maxlength="10"  value="<bean:write name="feedbackData" property="inputSalaryVariable"/>" />
						         		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_GENERATE_OFFER">	
							         		<logic:notEmpty name="selectionProcessForm" property="gradeId">
							         		<span id="waitToCalculate" style="width: 70px;">
							         			<a href="#" style="width:60px; margin-left: 5px;text-decoration:none;" class="active green" onclick="javascript: calculateSalary();return false;"><bean:message key="selection_feedback.link.preview" /></a>
							         		</span>	
							         		</logic:notEmpty>
						         		</logic:equal>
										</td>
									</logic:equal>
									<logic:notEqual value="true" name="inputSalaryVariableViewable" >
										<td>&nbsp;</td>
						         		<td width="180px">&nbsp;</td>
									</logic:notEqual>
									
								</tr>
									<tr>
									<td>
				   						<bean:message key="selection_feedback.label.variable_offered"/>
					   					<span id="emp_code_mand" style="color:red;display: none">*</span>
				   					</td>
				   					<td width="110px">
				   						<input type="text" name="variableOffered" id="variableOffered" size="11" maxlength="100"  value="<bean:write name="feedbackData" property="variableOffered"/>" />
				   					</td>
				   					<logic:equal value="true" name="levelOfferedViewable" >
										<td>
											<bean:message key="selection_feedback.label.joining_Bonus"/>
										</td>
										<td width="110px">
											<input type="text" name="joiningBonus" id="joiningBonus" size="11"  maxlength="20" value="<bean:write name="feedbackData" property="joiningBonus"/>" />
										</td>
				        			</logic:equal>
				        			<logic:notEqual value="true" name="levelOfferedViewable" >
				        				<td>&nbsp;</td>
						         		<td width="110px">&nbsp;</td>
				        			</logic:notEqual>
				        			
								
								</tr>
				       		</table>
						</logic:present>
						<logic:notPresent name="isHireStage" scope="page">
							<logic:notEmpty name="feedbackData" property="joiningDate">
								<script>
									document.selectionProcessForm.joiningDate.value='<bean:write name="feedbackData" property="joiningDate" format="dd/MM/yyyy"/>';
								</script>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="designationOffered">
								<input type="hidden" name="designationOffered"  value="<bean:write name="feedbackData" property="designationOffered" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="ctcOffered">
								<input type="hidden" name="ctcOffered" value="<bean:write name="feedbackData" property="ctcOffered" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="basicOffered">
								<input type="hidden" name="basicOffered" value="<bean:write name="feedbackData" property="basicOffered" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="employeeCode">
								<input type="hidden" name="employeeCode" value="<bean:write name="feedbackData" property="employeeCode" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="levelOffered">
								<input type="hidden" name="levelOffered" value="<bean:write name="feedbackData" property="levelOffered" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="inputSalaryVariable">
								<input type="hidden" name="inputSalaryVariable"  value="<bean:write name="feedbackData" property="inputSalaryVariable" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="levelOffered">
								<input type="hidden" name="levelOffered" value="<bean:write name="feedbackData" property="levelOffered" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="joiningBonus">
								<input type="hidden" name="joiningBonus" value="<bean:write name="feedbackData" property="joiningBonus" />"/>
							</logic:notEmpty>
							<logic:notEmpty name="feedbackData" property="variableOffered">
								<input type="hidden" name="variableOffered" value="<bean:write name="feedbackData" property="variableOffered" />"/>
							</logic:notEmpty>
						</logic:notPresent>
					</td>
				</tr>
				<tr>
					<td width="100%">
						<table width="100%">
							<tr>
								<td id="feedbackBy" style="color:#666666;font-weight:bold;"></td>
								<logic:notEmpty name="interviewers" >
									<logic:equal name="doDisplaySaveButton" scope="request" value='<%=""+Boolean.TRUE.booleanValue()%>'>
										<td style="text-align: right;">
											<bean:message key="selection_feedback.label.previous_feedback" />: 
											<a href="#" style="width:60px;" class="green"  onclick="javascript: printFeedback('<%=SelectionProcessConstants.SUMMARY_FEEDBACK %>');"><bean:message key="selection_feedback.label.summary_feedback" /></a>
											&nbsp;|&nbsp;
											<a href="#" style="width:60px;" class="green" onclick="javascript: printFeedback('<%=SelectionProcessConstants.DETAILED_FEEDBACK %>');"><bean:message key="selection_feedback.label.detail_feedback" /></a>
										</td>
									</logic:equal>
								</logic:notEmpty>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>				
						<div id="feedbackDiv" class="outerDiv" style="width:801px;height:250px;overflow: auto; background-color: #ffffff; padding: 5px;">
							<table cellpadding="0" cellspacing="0" class="feedbackform" style="width:784px; "> 								
								<logic:notEmpty property="fromStepData.traits" name="feedbackData">
								<logic:notEmpty name="interviewers" >
								<% UserData interviewer = (UserData) interviewers.get(0); %>
								<script language="JavaScript">
								$("feedbackBy").innerHTML='Feedback By <%=Utils.escapeHTML(interviewer.getUserName())%>';
								var requiredFields = new Array();
								var loggedInUserId = '<%=interviewer.getUserId()%>';
								</script>
								<% String prevRatingId = ""; %>
								<% String prevMultipleSelectId = ""; %>
								<% String fbCat=""; %>
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
									List selectedFields = Arrays.asList(multipleSelectFieldId.split(","));
									String multipleSelectFieldDesc = (String)traitData.get(SelectionProcessConstants.MULTIPLE_SELECTDESC_+ trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId());
									multipleSelectFieldDesc = Utils.isBlankOrNull(multipleSelectFieldDesc)?"":multipleSelectFieldDesc;
									String multipleSelectId = trait.getMultipleSelectId();
									
								%>	
								<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_CATEGORY%>" name="trait" property="feedbackFormFieldType">
									<tr>
										<td valign="top" colspan="4" class="category"><bean:write name="trait" property="feedbackFieldTitle"/></td>  												    								    						
									</tr>
								</logic:equal>
								<%								
								if(!Utils.isBlankOrNull(trait.getFeedbackFormCategoryTitle())&&!fbCat.equals(trait.getFeedbackFormCategoryTitle())){
									fbCat=trait.getFeedbackFormCategoryTitle();%>								
									<tr>
										<td valign="top" colspan="4" class="category"><bean:write name="trait" property="feedbackFormCategoryTitle"/></td>  												    								    						
									</tr>
								<% } %>
								<logic:equal value="<%=FeedbackFormConstants.FIELD_TYPE_FIELD%>" name="trait" property="feedbackFormFieldType">
								<logic:equal value="<%=FeedbackFieldsConstant.FIELD_TYPE_NORMAL%>" name="trait" property="feedbackFieldType">
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
														<td class="title">
															<%=Utils.escapeHTML(multipleSelectFieldData.getSelectFieldDesc())%>
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
								</logic:equal>
								<logic:equal value="<%=FeedbackFieldsConstant.FIELD_TYPE_APPLICANT%>" name="trait" property="feedbackFieldType" >
									<logic:equal value="<%=FeedbackFormConstants.GENERALISED%>" name="trait" property="feedbackFormFieldDisplayType">
										<tr>
											<td colspan="3">
												<strong><bean:write name="trait" property="feedbackFieldTitle"/></strong>
											</td>
										</tr>
										<tr>	
											<td colspan="3">
												<appField:feedbackFieldUI 
													id='<%=SelectionProcessConstants.TRAIT_ + trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>' 
													value="<%=interviewerComment%>"
													data = "${appplicantData}"
													fieldId="${trait.applicantFieldId}"
													permission="${sessionScope.permissionSet}"  /> 
											</td>
										</tr>
									</logic:equal>
									<logic:notEqual value="<%=FeedbackFormConstants.GENERALISED%>" name="trait" property="feedbackFormFieldDisplayType">
										<tr>
											<td width="240px" >
												<strong><bean:write name="trait" property="feedbackFieldTitle"/></strong>
											</td>
											<td>&nbsp;</td>
											<td colspan="1" class="label">
												<appField:feedbackFieldUI 
													id='<%=SelectionProcessConstants.TRAIT_ + trait.getFeedbackFormFieldId() + "_" + interviewer.getUserId()%>' 
													value="<%=interviewerComment%>"
													data = "${appplicantData}"
													fieldId="${trait.applicantFieldId}"
													permission="${sessionScope.permissionSet}"  /> 
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
		</table>
		</div>
		<logic:equal name="selectionProcessForm" property="isUserAuthorizedToMove" value="<%=Boolean.TRUE.toString()%>">
		<div  class="popupTop" style="border-top:1px dotted #999999;">
		<table class="tblPop" >
			<tr>
				<td  width="110px;" ><strong><bean:message key="selection_feedback.label.action"/></strong> :</td>
				<td>
					<script type="text/javascript">
						var actions = <bean:write name="stepsJSArray" filter="false" scope="request" />;
						var actionsJsArray = new SelectBox(actions,'<bean:write name="selectionProcessForm" property="nextPositionStepId" />','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:5});
						actionsJsArray.setOnChangeHandler('onChangeSelectionStep');
						document.write(actionsJsArray.getHtml());
						actionsJsArray.init();
					</script>
				</td>
			</tr>	
			<tr style="padding: 0px">
				<td></td>
				<td style="padding: 0px">
	          <!--  move to other position div -->
						 <div class="contentDivPop" id="selectPositionAndStepDiv" style="display:none;margin:5px 0px 5px 0px;">
							 <div class="outerDiv" >
									<div class="popupBody">
								      <table class="tblPop" >
							           <tr>
							        		  <td class="header"><bean:message key="common.position"/></td>
							              <td>
												        <script type="text/javascript">
													    	var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)request.getAttribute("positions"), "positionId", "positionTitle", "positionCode")%>;
			                       							 var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
															opts = m.concat(opts);
															selectBoxPosition = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:12});
									        				selectBoxPosition.setOnChangeHandler('selectedPositionChanged');
									        				document.write(selectBoxPosition.getHtml());
									        				selectBoxPosition.init();
																</script>
							              </td>
							           </tr>
							           <tr>
							              <td class="header"><bean:message key="selection_feedback.label.step"/></td>
							              <td>
						                    <script type="text/javascript">
																    var opts = new Array();	
																		opts[0] = new SelectOption('-1','<bean:message key="selection_feedback.label.selectStep"/>');
																		selectBoxStep = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:12});
										        				//selectBoxStep.setOnChangeHandler('selectedStepChanged');
										        				document.write(selectBoxStep.getHtml());
										        				selectBoxStep.init();
																</script>
										  			</td>
							           </tr>
							   	   </table>
									</div>    	      
									<div class="popupBody">
										<table class="tblPop" width="100%">
										<tr>
											<td>
											<div class="navBtn" style="float: right;"><a href="#" style="width:60px;" class="active" onclick="javascript: moveToOtherPosition();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.go"/></a>
												<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: hideDiv('selectPositionAndStepDiv');return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
											</div>
											</td>
										</tr>
										</table>
									</div>    	      
							 </div>    	      
						 </div>         
					</td>
				</tr>			 
			 </table>
			</div>
		</logic:equal>
		
	</div>
	<div class="navBtn" style="float: right;padding-top: 5px;">
				<logic:notEmpty name="interviewers" >
					<logic:equal name="doDisplaySaveButton" scope="request" value='<%=""+Boolean.TRUE.booleanValue()%>'>
						<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: saveChanges();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
					</logic:equal>
				</logic:notEmpty>
				<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
	</div>
	<br></br>
	<style type="text/css">
.divPrintOptions{
 width: 300px;  
 border: 2px solid #666; 
 background-color: #fff; 
 padding: 10px;
 border-top: none;
 border-left: none;
}
.divPrintOptions th{
color:black;
 border-bottom: solid 1px;
 padding-right:2px;
 text-align:left;
}
</style>
<div id="divPrintOptions" class="divPrintOptions" style="top:0;left:0; position: absolute; display: none; ">
										
</div>
</html:form>	
<br/>	
</div>
<div id="calDiv" style="position:absolute;z-index:10000;background-color:#F3F9DC;"></div>
<div id="divCalender" class="myCalender"></div>
<div id="popUpWindow" class="confirmPop"  style="padding:10px;width:374px;height:100px;position:absolute;left:200px;top:100px;display:none;">
  <table cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <td colspan="2" class="head12"><bean:message key="selection_feedback.error.appointment_exists"/></td>
    </tr>
    <tr><td><br class="br3" /></td></tr>
    <tr>
      <td>
				<div class="navBtn" style="float: right;">
				<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: saveChanges4('true');return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.yes"/></a>
				<a href="#" style="width:60px;" class="active" onclick="javascript: saveChanges4('false');return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.no"/></a>
				</div>
      </td>
    </tr>
  </table>
</div>
<script language="JavaScript">
	if (document.selectionProcessForm.nextPositionStepId.value != '<%=SelectionProcessConstants.STEP_INVALID%>') {
    	//do nothing
    } else {
    //	document.selectionProcessForm.nextPositionStepId.value = '<%=SelectionProcessConstants.STEP_ON_HOLD%>';
    }
	<logic:present name="nextStepIds" scope="page">
	  nextStepsIds = '<bean:write name="nextStepIds" />';
      //radioBttnClicked(document.selectionProcessForm.nextPositionStepId.value);
    </logic:present>
    <logic:notPresent name="nextStepIds" scope="page">
	    nextStepsIds = '';
    </logic:notPresent>   
</script>
<script>

/*************
 * START: Calender and date format related functions
 **************/

function cloneElemPosition(elementId){
	Position.clone($(elementId),'calDiv',{setHeight: false, setWidth: false, offsetTop: $(elementId).offsetHeight});	 
}
 
var popUpCal = new CalendarPopup("calDiv");  
popUpCal.showNavigationDropdowns();

var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
  	  if(!dtf.checkDate(obj)){
  		obj.select();
  		alert('<bean:message key="calendar.error.invalid_date"/>');
  		obj.focus();
  		return false;
  	  }else {
  		return true;
  	  }
	}
	return true;
}

//DATE FORMATTER CODE AND FUNCTIONS
var dtfo = new DateFormatter();
function getFDate(obj,format){
	dtfo.setDisplayFormat(format);
	if(obj.value.trim()!=''){
	if(!dtfo.checkDate(obj)){
		obj.select();
		alert("Please enter date in " + format + " format");
		tempObj = obj;
		setTimeout("tempObj.focus();",1);
		//obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}

//CALENDER CONTROL CODE AND FUNCTIONS
var workingSinceCal = new MonthYearCalender('divCalender');
workingSinceCal.setDisplayFormat('MMM-YYYY');
workingSinceCal.setDateStyle('EU');


var monthYearFormat = new DateFormatter();
monthYearFormat.setDisplayFormat('MMM-YYYY');
function getWorkingSinceFormated(obj) {
    if(obj.value.trim()!=''){
	if(!monthYearFormat.checkDate(obj)){
		obj.select();
		alert("<bean:message key="add_applicant.errors.working_since_invalid_format"/>");
		tempObj = obj;
		setTimeout("tempObj.focus();",1);
		//obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}


/*************
 * END: Calender and date format related functions
 **************/

function getFNumber(obj){
	if(obj.value.trim()!=''){
		if(isNaN(obj.value)){
			alert('<bean:message key="common.please_enter_valid_number" />');
			tempObj = obj;
			setTimeout("tempObj.focus();",1);
			//obj.focus();
			return false;
		}
	}
}

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
				}else {
					theImage.src = radioImg;
				}
			}
	}
}

function onMultipleSelectChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					if(theImage.src.indexOf("images/checkboxunchecked.gif")>-1){
						addSelectValue(imgGroupName,attachmentId);
						theImage.src = "images/checkboxchecked.gif";
					}else{
						removeSelectValue(imgGroupName,attachmentId);
						theImage.src = "images/checkboxunchecked.gif";
					}
				}
			}
	}
}

function addSelectValue(imgGroupName,selectFieldId){
	var existingValue = $(imgGroupName).value;
	if(existingValue.length>0){
		$(imgGroupName).value +=','+selectFieldId;
	}
	else{
		$(imgGroupName).value +=selectFieldId;
	}
}

function removeSelectValue(imgGroupName,selectFieldId){
	var existingValue = $(imgGroupName).value;
	if(existingValue.indexOf(',') < 0){
		$(imgGroupName).value = '';
	}
	else if(existingValue.indexOf(selectFieldId) == 0){
		$(imgGroupName).value =existingValue.slice(existingValue.indexOf(',')+1);
	}
	else{
		$(imgGroupName).value = existingValue.replace(','+selectFieldId,'');
	}
}

function setPopupTitle(){
	var title = '<b><bean:message key="selection_feedback.label.submit_feedback"/> - </b><bean:write name="feedbackData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
	   	title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="feedbackData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
   	<%} %>
	window.top.setPopTitle(title);
}
function doOnLoad() {	
	setPopupTitle();
	if(!isHIreStage)
		$('feedbackDiv').style.height='290px';
}
window.onload=doOnLoad;


var elem2 = document.getElementById('date');
if (elem2) {
 	elem2.value='<bean:write name="feedbackData" property="joiningDate" format="dd/MM/yyyy"/>';
}
function calculateSalary(){
	showUpdater('waitToCalculate',{setHeight: false, setWidth: false, offsetLeft: 0});
	var pars = "mode=previewSalaryStructure&gradeId=" + document.selectionProcessForm.gradeId.value+"&ctcOffered="+document.selectionProcessForm.ctcOffered.value+"&basicOffered="+document.selectionProcessForm.basicOffered.value+"&inputSalaryVariable="+document.selectionProcessForm.inputSalaryVariable.value;
	var myAjax = ajaxCall("selectionProcess.do",'get',pars,onPreviewSalary, reportError);	
}

function onPreviewSalary(request){	
	  if (request != null) {
	    xmlFile = request.responseText;		   
	    var div = document.getElementById('divPrintOptions');		    
	    div.innerHTML = xmlFile.toString();		    
	 }
	Effect.BlindDown('divPrintOptions',{duration:0.5});
	hideUpdater('waitToCalculate');
}

function updateSalary(basic, annnualTotal){
	if(($('ctcOffered') && $('ctcOffered').value!=null) || ($('basicOffered') && $('basicOffered').value!=null)){
		if(confirm('<bean:message key="selection_feedback.error.override_basic_and_target_ctc"/>')) {
			if($('ctcOffered'))
				$('ctcOffered').value=annnualTotal;
			if($('basicOffered'))
				$('basicOffered').value=basic;
		}else{
			return false;
		}
	}else {
		if($('ctcOffered'))
			$('ctcOffered').value=annnualTotal;
		if($('basicOffered'))
			$('basicOffered').value=basic;
	}
}

function hidePreviewSalary(){
	Effect.BlindUp('divPrintOptions',{duration:0.5});
}
function printFeedback(feedbackType){
	var params = '';
	var url = '';
	params+= '&applicantId='+document.selectionProcessForm.applicantId.value;
	params+= '&positionId='+$('positionId').value;
	params+= '&feedbackType='+feedbackType;
	url = 'selectionProcess.do?mode=printFeedback'+params;
	printUrl(url);
}
function printUrl(url){
	var printWin = window.open(url,"_blank","height=500,left=100,top=100,width=800,toolbar=no,titlebar=0,status=0,menubar=no,location= no,scrollbars=1");
	//printWin.print();	
}
function validateEmail(obj) {
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var emailAdd = obj.value;
	 if(reg.test(emailAdd) == false) {
		alert('Please Enter Valid Email Address.');
		tempObj = obj;
		setTimeout("tempObj.focus();",1);
		//obj.focus();
		return false;
	}
}

function validateEmail1(obj){
	validateEmail(obj);
}

function validateEmail2(obj){
	validateEmail(obj);
}

</script>
</logic:notPresent>
</logic:notPresent>