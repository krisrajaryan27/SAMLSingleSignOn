 <%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.positions.PositionConstants, 
	com.talentPool.masters.constants.StepConstants,
	com.talentPool.common.properties.TPApplicationProperties" %>
<%@ page import="com.talentPool.common.properties.GlobalApplicationProperties" %>
<%@ page import="com.talentPool.common.properties.GlobalConstants" %>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/doClasses/IdValueClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/StatusMessageClass.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/doClasses/StepClass.js" type="text/javascript"></script>		
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script src="js/tpSelectListFunctions.js"></script> 		
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>

<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<style>
body {
	background: #F2F2F2;
}
</style>
<script language="JavaScript">
var selectBoxFeedbackForms=null;
var selectBoxApplicantFeedbackForms=null;

function getExistingSteps() {
	var options = new Array();
	for (i = 0; i < window.top._steps.length; i++) {
		options[i] = new SelectOption(i,window.top._steps[i].stepTitle);
	}
	return options;
}


function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'GRD_ASSIGNED_TO'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;	
		}	
	}if(grdId == 'GRD_ASSIGNED_TO_USER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;
		}	
	}if(grdId == 'GRD_SCHEDULED_BY'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;
			case 1:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"userRoleTitle");				
				break;
		}	
	}if(grdId == 'GRD_SCHEDULED_BY_USER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;
			case 1:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"userRoleTitle");				
				break;
		}	
	}
	if(grdId == 'GRD_DECISION_MAKER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;
			case 1:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"userRoleTitle");				
				break;
		}	
	}if(grdId == 'GRD_DECISION_MAKER_USER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;
			case 1:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"userRoleTitle");				
				break;
		}	
	}
	return obj.cell.innerHTML;
}

var selectBoxStage=null;

</script>
<div class="contentDiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
	<tr>
		<td class="label">
			<bean:message key="common.stage" />
		</td>
		<td>
		<div id="stageDiv" style="display: block;">
			<script language="JavaScript">
				var options = [new SelectOption('-1', '<bean:message key='master_steps.label.select_stage' />')];				
				selectBoxStage = new SelectBox(options,'-1','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10});
				document.write(selectBoxStage.getHtml());
				selectBoxStage.setOnChangeHandler('onChangeStage');
				selectBoxStage.init();
			</script>
		</div>
		<logic:notEqual name="stepIndex" scope="request" value="-1">
			<input id="stage" name="stage" type="text" size="37" disabled="true" />
		</logic:notEqual>
		</td>
	</tr>
	<tr>
		<td class="label"><bean:message key="position.hiring_process.step" /></td>
		<logic:equal name="stepIndex" scope="request" value="-1">
		<td>
			<script language="JavaScript">
				var options = [new SelectOption('-1', '<bean:message key='master_steps.label.select_step' />')];											
				selectBoxStep = new SelectBox(options,(options.length -1),'images/btn_dropdown.gif',{namesonly:false, width:'200px', size:15});
				document.write(selectBoxStep.getHtml());
				selectBoxStep.setOnChangeHandler('onChangeStep');
				selectBoxStep.init();
			</script>
		</td>
		</logic:equal>
		<logic:notEqual name="stepIndex" scope="request" value="-1">
		<td>
			<input id="stepTitle" name="stepTitle" type="text" size="37" disabled="true" />
		</td>
		</logic:notEqual>		
	</tr>
	</table>		
	<table border="0" cellspacing="0" cellpadding="0" class="posinput">
	<tr>
		<td class="label"><bean:message key="position.hiring_process.step.assigned_to" /></td>
		
	</tr>
	</table>
	<table cellspacing="0" cellpadding="0" style="padding-left: 5px;">
		<tr>
			<td style="vertical-align: top;">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<input id="assignTo" name="assignTo" type="text" size="41" onfocus="setUserSelection('assignTo')" value="Filter" style="width:216px;color: grey; border-bottom: 0px;" onclick="onFilterFocus('assignTo','Filter');" onblur="onFilterUnfocus('assignTo','Filter')"/>
					</td>
				</tr>
				<tr>
					<td class="gridborder">
					<div id="GRD_ASSIGNED_TO" style="width:219px;height:80px;"></div>
					</td>
				</tr>
			</table>			
			</td>		
			<td style="padding: 10px;">					
				<a href="#" onclick="javascript: selectItem(dataGridAssignedTo,dataGridAssignedToUser);return false;" title="<bean:message key='common.add' />" >
					<img src="images/ico_rightarrow.gif"  border="0" />
				</a>
				<br/>
				<a href="#" onclick="javascript: deselectItem(dataGridAssignedToUser,dataGridAssignedTo);return false;" title="<bean:message key='common.remove' />" >
					<img src="images/ico_leftarrow.gif"  border="0" />
				</a> 
			</td>					
			<td style="vertical-align: top;">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
					<div id="GRD_ASSIGNED_TO_USER" style="width:220px;height:97px;"></div>
					</td>
				</tr>
			</table>
		</tr>	
	</table>			
	<table border="0" cellspacing="0" cellpadding="0" class="posinput">	
	<tr>
		<td style="height: 20px;vertical-align: bottom;" class="label"><bean:message key="position.hiring_process.step.scheduled" /></td>
		<td style="height: 20px;vertical-align: bottom;">
			<img id="scheduled" src="" />
		</td>
	</tr>	
	</table>		
	
	<div id="schedulers" style="display: block;">
	<table  cellspacing="0" cellpadding="0" class="posinput">
	<tr>
		<td class="label"><bean:message key="position.hiring_process.step.scheduled_by" /></td>
	</tr>	
	</table>
	<table  cellspacing="0" cellpadding="0" style="padding-left: 5px;">
	<tr>
		<td style="vertical-align: top;">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<input id="scheduledBy" name="scheduledBy" type="text" size="41" onfocus="setUserSelection('scheduledBy')" value="Filter" style="width:216px;color: grey; border-bottom: 0px;" onclick="onFilterFocus('scheduledBy','Filter');" onblur="onFilterUnfocus('scheduledBy','Filter')"/>
				</td>
			</tr>
			<tr>
				<td class="gridborder">
				<div id="GRD_SCHEDULED_BY" style="width:219px;height:80px;"></div>		
				</td>
			</tr>
		</table>
		</td>
		<td style="padding: 10px;">					
			<a href="#" onclick="javascript: selectItem(dataGridScheduledBy,dataGridScheduledByUser);return false;" title="<bean:message key='common.add' />" >
				<img src="images/ico_rightarrow.gif"  border="0" />
			</a>
			<br/>
			<a href="#" onclick="javascript: deselectItem(dataGridScheduledByUser,dataGridScheduledBy);return false;" title="<bean:message key='common.remove' />" >
				<img src="images/ico_leftarrow.gif"  border="0" />
			</a> 
		</td>					
		<td style="vertical-align: top;">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="gridborder">
				<div id="GRD_SCHEDULED_BY_USER" style="width:220px;height:97px;"></div>				
				</td>
			</tr>
		</table>
		</td>
	</tr>	
	</table>		
	</div>			
	<table border="0" cellspacing="0" cellpadding="0" class="posinput">
	<tr>
		<td style="height: 20px;vertical-align: bottom;" class="label">
			<bean:message key="position.hiring_process.step.isDecisionMakerSameAsAssignedTo" />
		</td>	
		<td style="height: 20px;vertical-align: bottom;">
			<img id="isDecisionMakerSameAsAssignedTo" src="" onclick="javascript: toggleCheckBoxShowDiv('deciders',this);" />
		</td>
	</tr>
	</table>
	
	<div id="deciders" style="display: block;">
	<table cellspacing="0" cellpadding="0" class="posinput">
	<tr>
		<td class="label"><bean:message key="position.hiring_process.step.decision_maker" /></td>	
	</tr>		
	</table>	
	<table border="0" cellspacing="0" cellpadding="0" style="padding-left: 5px;">
	<tr>
		<td style="vertical-align: top;" >

		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<input id="decisionMaker" name="decisionMaker" type="text" size="41" onfocus="setUserSelection('decisionMaker')" value="Filter" style="width:216px;color: grey;border-bottom: 0px;" onclick="onFilterFocus('decisionMaker','Filter');" onblur="onFilterUnfocus('decisionMaker','Filter')"/>
				</td>
			</tr>
			<tr>
				<td class="gridborder">
				<div id="GRD_DECISION_MAKER" style="width:219px;height:80px;"></div>
				</td>
			</tr>
		</table>
		</td>		
		<td style="padding: 10px;">					
			<a href="#" onclick="javascript: selectItem(dataGridDecisionMaker,dataGridDecisionMakerUser);return false;" title="<bean:message key='common.add' />" >
				<img src="images/ico_rightarrow.gif"  border="0" />
			</a>
			<br/>
			<a href="#" onclick="javascript: deselectItem(dataGridDecisionMakerUser,dataGridDecisionMaker);return false;" title="<bean:message key='common.remove' />" >
				<img src="images/ico_leftarrow.gif"  border="0" />
			</a> 
		</td>					
		<td style="vertical-align: top;">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="gridborder">
				<div id="GRD_DECISION_MAKER_USER" style="width:220px;height:97px;"></div>
				</td>		
			</tr>
		</table>
		
		
	</tr>
	</table>
	</div>		
	<table border="0" cellspacing="0" cellpadding="0" class="posinput">
	<tr>
		<td style="height: 20px;vertical-align: bottom;" class="label">
			<bean:message key="position.hiring_process.step.is.interviewer.can.confirm" />
		</td>	
		<td style="height: 20px;vertical-align: bottom;">
			<img id="isInterviewerCanConfirm" src="" onclick="javascript: toggleCheckBox(this);" />
		</td>
	</tr>
	<tr>
		<td style="height: 20px;vertical-align: bottom;"class="label"><bean:message key="position.hiring_process.step.optional" /></td>
		<td style="height: 20px;vertical-align: bottom;"><img id="optional" src="" onclick="javascript: toggleCheckBox(this);" /></td>
	</tr>
	<tr>
		<td class="label"><bean:message key="position.hiring_process.step.feedback_form" /></td>
		<td>
			<script language="JavaScript">
				var options = [new SelectOption('-1', '<bean:message key='position.hiring_process.label.select_feedback_form' />')];				
				selectBoxFeedbackForms = new SelectBox(options,'-1','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10});
				document.write(selectBoxFeedbackForms.getHtml());
				//selectBoxFeedbackForms.setOnChangeHandler('onChangeFeedbackForm');				
				selectBoxFeedbackForms.init();
			</script>
		
		</td>
	</tr>
	<!-- <tr>
		<td class="label">Applicant feedback form</td>
		<td>
			<script language="JavaScript">
				var options = [new SelectOption('-1', '<bean:message key='position.hiring_process.label.select_feedback_form' />')];				
				selectBoxApplicantFeedbackForms = new SelectBox(options,'-1','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10});
				document.write(selectBoxApplicantFeedbackForms.getHtml());
				//selectBoxFeedbackForms.setOnChangeHandler('onChangeFeedbackForm');				
				selectBoxApplicantFeedbackForms.init();
			</script>
		
		</td>
	</tr> -->
	<tr>
		<td class="label"><bean:message key="position.hiring_process.step.follow_up_messages" /></td>
		<td>
			<table id="messages" width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
				
			</table>
			<table class="posinput" width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="normal">
						<input type="text" id="_txtNewMessage" name="_txtNewMessage" size="36" />
					</td>
					<td style="vertical-align:bottom;">
							<a href="#" style="width:50px;" class="btn3" onclick="javascript:addNewMessage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add"/></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="label"><bean:message key="position.hiring_process.step.notification_to_candidate_when_moved_to_this_step" /></td>
		<td>
			<img id="notifyCandidate" src="" onclick="javascript: toggleCheckBox(this);" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div class="navBtn" style="float:right;">
				<a href="#" style="width:50px;" class="active" onclick="javascript:saveStep();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
		</td>
	</tr>	
	</table>	
</div>
<br/>
<script language="JavaScript">
selectedCheckBox="images/checkboxchecked.gif";
deselectedCheckBox="images/checkboxunchecked.gif";
selectedCheckBoxGrey="images/checkboxchecked_grey.gif";
deselectedCheckBoxGrey="images/checkboxunchecked_grey.gif";
selectedRadioButton="images/checkedradiobutton.gif";
deselectedRadioButton="images/radiobutton.gif";
checkedImg="images/check.gif";
uncheckedImg="images/ico_close.gif";

var dataGridAssignedTo;
var dataGridAssignedToUser;
var dataGridScheduledBy;
var dataGridScheduledByUser;
var dataGridDecisionMaker;
var dataGridDecisionMakerUser;

var _stepIndex = '<bean:write name="stepIndex" scope="request"/>';
var _step;
var _messages;
window.onload = onOnPopUpLoad;

function onOnPopUpLoad() {
	setPopupTitle();
	if (_stepIndex != -1) {
		_step = window.top._steps[_stepIndex];
		_messages = _step.messages.slice(0, _step.messages.length);
	} else {		
		_step = new Step();		
		_step.setIndex(-1);
		_step.setStepId(-1);
		_step.setStepLevel("");
		_messages = new Array();
	}	

	populateFields();	
	initGridAssignedTo();
	initGridScheduledBy();
	initGridDecisionMaker();	

	Event.observe($('assignTo'), "keyup", onCriteriaChange.bindAsEventListener(this));
	Event.observe($('scheduledBy'), "keyup", onCriteriaChange.bindAsEventListener(this));
	Event.observe($('decisionMaker'), "keyup", onCriteriaChange.bindAsEventListener(this));
	
}

var userSelection='';

function setUserSelection(text){
	userSelection=text;
}

function onCriteriaChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		case Event.KEY_UP:
		case Event.KEY_DOWN: 
		break;
		case Event.KEY_PAGEUP:
		case Event.KEY_PAGEDOWN:
		break;
		case Event.KEY_RETURN:
		break;
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			if(userSelection=='assignTo'){
				dataGridAssignedTo.filterBy(0, $('assignTo').value, false);
			}
			if(userSelection=='scheduledBy'){
				dataGridScheduledBy.filterBy(0, $('scheduledBy').value, false);
			}
			if(userSelection=='decisionMaker'){
				dataGridDecisionMaker.filterBy(0, $('decisionMaker').value, false);
			}			
		}
	}
}

function setPopupTitle(){
	var popupTitle = '';
	<logic:present name="positionTitle" scope="request">
		popupTitle = '<b><bean:write name="positionTitle" scope="request" /></b>';
	</logic:present>
	<logic:notPresent name="positionTitle" scope="request">
		popupTitle = '<b><bean:message key="position.hiring_process.step.add_new_process_step" /></b>';
	</logic:notPresent>
	window.top.setPopTitle(popupTitle);
}

function populateFields() {
	populateStages();
	populateStepTitle();	
	//populateScheduled();
	//populateIsDecisionMakerSameAsAssignedTo();
	populateIsInterviewerCanConfirm();	
	populateOptional();
	populateFeedbackForms();
	//populateApplicantFeedbackForms();
	populateMessages();
	populateNotification();	
}
function onChangeFeedbackForm(newIdx){
	var selId = selectBoxFeedbackForms.getSelectedId();
	if (selId!=null && selId!='-1') {
		var currentFId = _step.feedbackFormId;
		if (currentFId!=null && currentFId!='-1') {
			if(selId != _step.feedbackFormId ){
				var pars = "mode=isChangeFeedbackFormPossible&feedbackFormId=" + selId + "&positionStepId=" + _step.stepId;	
				var myAjax = ajaxCall("position.do",'get',pars,onChangeFeedbackFormResponse, reportError);	
			}
		}
	}
}

/* function onChangeApplicantFeedbackForm(newIdx){
	var selId = selectBoxApplicantFeedbackForms.getSelectedId();
	if (selId!=null && selId!='-1') {
		var currentFId = _step.ApplicantFeedbackFormId;
		if (currentFId!=null && currentFId!='-1') {
			if(selId != _step.applicantFeedbackFormId ){
				var pars = "mode=isChangeFeedbackFormPossible&feedbackFormId=" + selId + "&positionStepId=" + _step.stepId;	
				var myAjax = ajaxCall("position.do",'get',pars,onChangeApplicantFeedbackFormResponse, reportError);	
			}
		}
	}
} */

function onChangeFeedbackFormResponse(request) {
	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="position.hiring_process.step.error.change_feedback_form"/>');
		selectFeedbackForm();
		return;
	}	
}

<%-- function onChangeApplicantFeedbackFormResponse(request) {
	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="position.hiring_process.step.error.change_feedback_form"/>');
		selectApplicantFeedbackForm();
		return;
	}	
} --%>

function deleteTrait(index) {
	_traits.splice(index, 1);
	populateTraits();
}
function populateFeedbackForms() {
	var pars = "mode=getFeedbackFormsListInXML";
	var myAjax = ajaxCall("position.do","get",pars,onCompleteFormRequest,reportError);
}
/* function populateApplicantFeedbackForms() {
	var pars = "mode=getFeedbackFormsListInXML";
	var myAjax = ajaxCall("position.do","get",pars,onCompleteApplicantFormRequest,reportError);
} */
function onCompleteFormRequest(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_feedback_fields_categories.error.can_not_delete_category"/>');
		return;
	}
	var formsArray= xmlFile.getElementsByTagName("forms")[0].firstChild.nodeValue;
	selectBoxFeedbackForms.reInitialize(eval(formsArray),'-1');
	selectFeedbackForm();
}
function onCompleteApplicantFormRequest(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_feedback_fields_categories.error.can_not_delete_category"/>');
		return;
	}
	var formsArray= xmlFile.getElementsByTagName("forms")[0].firstChild.nodeValue;
	selectBoxApplicantFeedbackForms.reInitialize(eval(formsArray),'-1');
	//selectApplicantFeedbackForm();
}
function selectFeedbackForm(){
	selectBoxFeedbackForms.setSelected(selectBoxFeedbackForms.getIndexWithId(_step.feedbackFormId));
}
/* function selectApplicantFeedbackForm(){
	selectBoxApplicantFeedbackForms.setSelected(selectBoxApplicantFeedbackForms.getIndexWithId(_step.applicantFeedbackFormId));
} */
function populateStepTitle() {
	elem = $('stepTitle');
	if (_stepIndex == -1) {
	} else {
		elem.value = _step.stepTitle;
	}	
}

function toggleCheckBox(obj) {
	if(obj){
		var src = obj.src;
		if (src.indexOf(deselectedCheckBox) != -1) {
			obj.src = selectedCheckBox;
		} else {
			obj.src = deselectedCheckBox;
		}
	}
}

/*Toggle Div*/
function toggleCheckBoxHideDiv(divId,obj) {	
	if(obj){
		var src = obj.src;
		if (src.indexOf(uncheckedImg) != -1) {
			obj.src = checkedImg;			
			Element.show(divId);				
		} else {
			obj.src = uncheckedImg;
			Element.hide(divId);
		}
	}
}
function toggleCheckBoxShowDiv(divId,obj) {	
	if(obj){
		var src = obj.src;
		if (src.indexOf(deselectedCheckBox) != -1) {
			obj.src = selectedCheckBox;			
			Element.hide(divId);							
		} else {
			obj.src = deselectedCheckBox;
			Element.show(divId);
		}
	}
}
/*Grid for User List Control*/

function toggleCheckBoxGrey(obj) {
	if(obj){
		var src = obj.src;
		if (src.indexOf(deselectedCheckBoxGrey) != -1) {
			obj.src = selectedCheckBoxGrey;
		} else {
			obj.src = deselectedCheckBoxGrey;
		}
	}
}

function populateScheduled() {
	elem = $('scheduled');
	divId = $('schedulers');
	if (_step.isScheduled == '<%=PositionConstants.STEP_SCHEDULED%>') {
		elem.src = checkedImg;
		Element.show(divId);
	} else {
		elem.src = uncheckedImg;
		Element.hide(divId);
	}
}

function populateNotification() {
	elem = $('notifyCandidate');
	elem.src = deselectedCheckBox; // default deselected
	if(_step.stepId==-1 && <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_NOTIFY_CANDIDATE_PROGRESS)%>==<%=GlobalConstants.ENABLED%> ){ // if it is new step
		elem.src = selectedCheckBox;
	}else if (_step.stepId!=-1 && _step.isNotifyToCandidate == '<%=PositionConstants.STEP_NOTIFY_PROGRESS_TO_CANDIDATE%>') {
		elem.src = selectedCheckBox;
	}
}

function populateIsInterviewerCanConfirm() {
	elem = $('isInterviewerCanConfirm');
	if (_step.isInterviewerCanConfirm == '<%=PositionConstants.STEP_INTERVIEWER_CAN_CONFIRM%>') {
		elem.src = selectedCheckBox;
	} else {
		elem.src = deselectedCheckBox;
	}
}

function populateIsDecisionMakerSameAsAssignedTo() {
	elem = $('isDecisionMakerSameAsAssignedTo');
	divId = $('deciders');
	if (_step.isDecisionMakerSameAsAssignedTo == '<%=PositionConstants.STEP_DECISION_MAKER_SAMEAS_ASSIGNED_TO%>') {
		elem.src = selectedCheckBox;
		Element.hide(divId);		
	} else {
		elem.src = deselectedCheckBox;		
		Element.show(divId);
	}
}

function populateOptional() {
	elem = $('optional');
	if (_step.isOptional == '<%=PositionConstants.STEP_OPTIONAL%>') {
		elem.src = selectedCheckBox;
	} else {
		elem.src = deselectedCheckBox;
	}
}

function populateLevel(level) {
	elem0 = $('level0');
	elem1 = $('level1');
	elem2 = $('level2');
	elem0.src = deselectedRadioButton;
	elem1.src = deselectedRadioButton;
	elem2.src = deselectedRadioButton;
	if (level == '<%=PositionConstants.STEP_LEVEL_SHORTLIST%>') {
		elem0.src = selectedRadioButton;
	}else if (level == '<%=PositionConstants.STEP_LEVEL_SELECT%>') {
		elem1.src = selectedRadioButton;
	} else if (level == '<%=PositionConstants.STEP_LEVEL_ACCEPT%>') {
		elem2.src = selectedRadioButton;
	}
}

function populateMessages() {
	tbl = $('messages');
	if (tbl) {
		var tBody= tbl.getElementsByTagName("tbody")[0];
		if(tBody){
			var tRows = tBody.childNodes;
			for(var i = tRows.length - 1; i >= 0; i--){
					tBody.removeChild(tRows[i]);
			}	
		}
		
		if (_messages && _messages.length > 0) {
			for (j = 0; j < _messages.length; j++) {			
				if(tBody){
				}else{
					tBody = document.createElement("TBODY");
				}
				var tRow = document.createElement("TR");
				
				var tCell0 = document.createElement("TD");
				tCell0.style.width="5px";
				tCell0.className="normal";	
				tCell0.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.delete"/>' + '" src="images/ico_delete.gif" onclick="javascript: deleteMessage(' + j + ');"/>';		
				tRow.appendChild(tCell0);
			
				var tCell1 = document.createElement("TD");	
				tCell1.style.width="160px";				
				tCell1.style.wordWrap="break-word";	
				tCell1.className="green";		
				tCell1.innerHTML = _messages[j].message.escapeHTML();		
				tRow.appendChild(tCell1);
				
				var tCell2 = document.createElement("TD");
				tCell2.style.width="5px";
				//tCell2.className="normal";						
				tCell2.innerHTML = '<img id="message' + j + '" title="' + '<bean:message key="common.set_default"/>' + '" src="' + deselectedRadioButton + '" onclick="javascript: changeDefaultMessage(' + j + ');"/>';		
				tRow.appendChild(tCell2);
				
				var tCell3 = document.createElement("TD");
				tCell3.style.width="5px";
				//tCell3.className="normal";						
				tCell3.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: swapMessages(' + (j - 1) + ', ' + j + ');"/>';		
				tRow.appendChild(tCell3);
				
				var tCell4 = document.createElement("TD");
				tCell4.style.width="5px";
				//tCell4.className="normal";	
				tCell4.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: swapMessages(' + j + ', ' + (j + 1) + ');"/>';		
				tRow.appendChild(tCell4);
			
				tBody.appendChild(tRow);
				tbl.appendChild(tBody);
			}
			
			for (j = 0; j < _messages.length; j++) {
				if (_messages[j].isDefault == '<%=PositionConstants.MESSAGE_DEFAULT%>') {
					elem = $('message' + j);
					elem.src = selectedRadioButton;
					break;
				}
			}
		}
	}
}

function swapMessages(id1, id2) { 	
	if (id2 != 0 && id2 != _messages.length) {              
		_temp = _messages[id1];
		_messages[id1] = _messages[id2];
		_messages[id2] = _temp;
		populateMessages();
	}
}

function deleteMessage(index) {
	_messages.splice(index, 1);
	populateMessages();
}

function changeDefaultMessage(id) {
	for (j = 0; j < _messages.length; j++) {
		_messages[j].setIsDefault('<%=PositionConstants.MESSAGE_NOT_DEFAULT%>');
	}
	_messages[id].setIsDefault('<%=PositionConstants.MESSAGE_DEFAULT%>');
	populateMessages();
}

function addNewMessage() {
	var txtArea = $('_txtNewMessage');
	var val = txtArea.value;	
	if (val.trim() != '') {
		var _message = new StatusMessage();
		_message.setMessageId('0');
		_message.setMessage(val);
		if (_messages.length == 0) {
			_message.setIsDefault('<%=PositionConstants.MESSAGE_DEFAULT%>');
		} else {
			_message.setIsDefault('<%=PositionConstants.MESSAGE_NOT_DEFAULT%>');
		}
		_messages[_messages.length] = _message;
		populateMessages();
	}
	txtArea.value = '';
}
var _newstep = null;
var returnVal=null;
function saveStep() {
	_newstep = new Step();
	if (_stepIndex == -1) {
		_newstep.setIndex(-1);
		_newstep.setStepMasterId(selectBoxStep.getSelectedId());		
	} else {
		_newstep.setIndex(_step.index);
		_newstep.setStepMasterId(_step.stepMasterId);		
	}
	_newstep.setStepId(_step.stepId);
	_newstep.setIsDefault('<%=PositionConstants.STEP_NOT_DEFAULT%>');
	errors ='';
	errors = saveLevel(errors);	
	errors = saveStepTitle(errors);
	errors = saveAssignedToUsers(errors);
	saveScheduled();
	errors = saveScheduledByUsers(errors);
	saveInterviewerCanConfirm();
	saveDecisionMakerSameAsAssignedTo();
	errors = saveDecisionMakerUsers(errors);
	
	saveOptional(errors);
	errors = saveFeedbackForm(errors);
	//errors = saveApplicantFeedbackForm(errors);
	errors = saveMessages(errors);
	saveNotifyToCandidate();
	if (errors.length > 0) {
		errors = addError('<bean:message key="common.data_required" />', errors);
	}
	if (errors.length == 0) {
		var level = getStepLevel();
	}
	if (errors.length > 0) {
		alert(errors);
		return false;
	}
	returnVal = _newstep;
	window.top.hidePopWin(true);
	return true;
}

function saveStepTitle(errors) {
	var val;
	if (_stepIndex == -1) {
		val = selectBoxStep.getText(selectBoxStep.getSelectedIndex());		
	} else {
		val = _step.stepTitle;
	}
	if (val == null || val.trim() == '') {
		errors = addError(errors, '- <bean:message key="add_position.label.addStep.stepTitle" />');
	} else {
		_newstep.setStepTitle(val.trim());
	}
	return errors;
}

function saveAssignedToUsers(errors) {
	var val = dataGridAssignedToUser.getAllItemIds();
	if (val.trim() == '') {
		errors = addError(errors, '- <bean:message key="add_position.label.addStep.assignedToUsers" />');
	} else {
		_newstep.setAssignedTo(val.trim());
		var userIds = val.trim().split(',');
		_newstep.setAssignedToUsers( getUserNameByIds(dataGridAssignedToUser,userIds));
	
	}
	return errors;
}

function getUserNameByIds(gridObject, userIds){
	var userNames = '';
	for (i = 0; i < userIds.length; i++) {			
		var username= gridObject.getUserData(userIds[i],"userName");			
		if (userNames.length > 0) {
			userNames += ', ';
		}
		userNames += username;
	}
	return userNames;
}
function buildUserString(tblName) {
	var val = '';
	for (i = 0; i < window.top._users.length; i++) {			
		var obj = $(tblName + window.top._users[i].id.trim());
		var src = obj.src;
		if (src.indexOf(selectedCheckBoxGrey) != -1) {
			if (val.length > 0) {
				val += ', ';
			}
			val += window.top._users[i].id.trim();
		}
	}
	return val;
}

function saveScheduled() {
	var obj = $('scheduled');
	var src = obj.src;
	if (src.indexOf(checkedImg) != -1) {
		_newstep.setIsScheduled('<%=PositionConstants.STEP_SCHEDULED%>');
	} else {
		_newstep.setIsScheduled('<%=PositionConstants.STEP_NOT_SCHEDULED%>');
	}
}
function saveNotifyToCandidate(){
	var obj = $('notifyCandidate');
	var src = obj.src;
	if (src.indexOf(selectedCheckBox) != -1) {
		_newstep.setIsNotifyToCandidate('<%=PositionConstants.STEP_NOTIFY_PROGRESS_TO_CANDIDATE%>');
	} else {
		_newstep.setIsNotifyToCandidate('<%=PositionConstants.STEP_DONT_NOTIFY_PROGRESS_TO_CANDIDATE%>');
	}
}

function saveScheduledByUsers(errors) {
	var val = '';
	if (_newstep.isScheduled == '<%=PositionConstants.STEP_SCHEDULED%>') {
		val = dataGridScheduledByUser.getAllItemIds();
		if (val.trim() == '') {
				errors = addError(errors, '- <bean:message key="add_position.label.addStep.scheduledByUsers" />');
			} else {
				_newstep.setScheduledBy(val.trim());
				var userIds = val.trim().split(',');
				_newstep.setScheduledByUsers( getUserNameByIds(dataGridScheduledByUser,userIds));
			}
	}	
	return errors;
}

function saveInterviewerCanConfirm() {
	var obj = $('isInterviewerCanConfirm');
	var src = obj.src;
	if (src.indexOf(selectedCheckBox) != -1) {
		_newstep.setIsInterviewerCanConfirm('<%=PositionConstants.STEP_INTERVIEWER_CAN_CONFIRM%>');
	} else {
		_newstep.setIsInterviewerCanConfirm('<%=PositionConstants.STEP_INTERVIEWER_CANNOT_CONFIRM%>');
	}
}

function saveDecisionMakerSameAsAssignedTo() {
	var obj = $('isDecisionMakerSameAsAssignedTo');
	var src = obj.src;	
	if (src.indexOf(selectedCheckBox) != -1) {
		_newstep.setIsDecisionMakerSameAsAssignedTo('<%=PositionConstants.STEP_DECISION_MAKER_SAMEAS_ASSIGNED_TO%>');		
	} else {
		_newstep.setIsDecisionMakerSameAsAssignedTo('<%=PositionConstants.STEP_DECISION_MAKER_NOT_SAMEAS_ASSIGNED_TO%>');
	}
}

function saveDecisionMakerUsers(errors) {		
	var val='';
	if (_newstep.isDecisionMakerSameAsAssignedTo == '<%=PositionConstants.STEP_DECISION_MAKER_SAMEAS_ASSIGNED_TO%>') {
		val = dataGridAssignedToUser.getAllItemIds();		
		_newstep.setDecisionMaker(val.trim());		
		var userIds = val.trim().split(',');
		_newstep.setDecisionMakerUsers( getUserNameByIds(dataGridAssignedToUser,userIds));
	}else{
		val = dataGridDecisionMakerUser.getAllItemIds();
		if (val.trim() == '') {
			errors = addError(errors, '- <bean:message key="add_position.label.addStep.decisionMakerUsers" />');
		} else {
			_newstep.setDecisionMaker(val.trim());
			var userIds = val.trim().split(',');
			_newstep.setDecisionMakerUsers( getUserNameByIds(dataGridDecisionMakerUser,userIds));
		}
	}
	return errors;
}

function saveOptional() {
	var obj = $('optional');
	var src = obj.src;
	if (src.indexOf(selectedCheckBox) != -1) {
		_newstep.setIsOptional('<%=PositionConstants.STEP_OPTIONAL%>');
	} else {
		_newstep.setIsOptional('<%=PositionConstants.STEP_MANDATORY%>');
	}
}

function saveLevel(errors) {
	var level = getStepLevel();
	
	if (level == -1) {
		errors = addError(errors, '- <bean:message key="add_position.label.addStep.Stage" />');
	} else {
		_newstep.setStepLevel(level);
	}
	return errors;
}

function getStepLevel() {
	var level;
	if (_stepIndex == -1) {
		level = selectBoxStage.getSelectedId();
	} else {
		level = _step.stepLevel;
	}	
	return level;
}

function saveFeedbackForm(errors) {
	var fFormId = selectBoxFeedbackForms.getSelectedId();
	if (fFormId==null || fFormId=='-1') {
		errors = addError(errors, '- Feedback form');
	} else {
		_newstep.setFeedbackFormId(fFormId);
	}
	return errors;
}

/* function saveApplicantFeedbackForm(errors) {
	var fFormId = selectBoxApplicantFeedbackForms.getSelectedId();
	if (fFormId==null || fFormId=='-1') {
		fFormId=' ';
		_newstep.setApplicantFeedbackFormId(fFormId);
	} else {
		_newstep.setApplicantFeedbackFormId(fFormId);
	}
	return errors;
} */

function saveMessages(errors) {
	if (_messages.length == 0) {
		errors = addError(errors, '- <bean:message key="add_position.label.addStep.at_least_one_status_message" />');
	} else {
		var mn;
		for(var mn = 0; mn < _messages.length; mn++) {
			if (_messages[mn].isDefault == '<%=PositionConstants.MESSAGE_DEFAULT%>') {
				break;
			}
		}
		if (mn == _messages.length) {
			errors = addError(errors, '- <bean:message key="add_position.label.addStep.default_status_message" />');
		} else {
			_newstep.setMessages(_messages);
		}
	}
	return errors;
}

function cancelOperation() {
	window.top.hidePopWin(false);
	return false;
}

function addError(errors, error) {
	if (errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}

//GRID For Assigned To
function initGridAssignedTo() {	
	dataGridAssignedTo = new dhtmlXGridObject('GRD_ASSIGNED_TO'); 
	dataGridAssignedTo.imgURL = "images/"; 
	dataGridAssignedTo.setHeader("User Name"); 
	dataGridAssignedTo.setInitWidths("200");
	dataGridAssignedTo.setColAlign("left");
	dataGridAssignedTo.setColTypes("ro"); 
	dataGridAssignedTo.setColSorting("assignedTo_userName_sort");	
	dataGridAssignedTo.enableMultiselect('true');
	dataGridAssignedTo.setNoHeader(true);
	dataGridAssignedTo.init();
	loadGridAssignedTo();	
	dataGridAssignedTo.attachEvent("onXLE",doOnLoadingEndAssignedTo);
	dataGridAssignedTo.attachEvent("onKeyPress",onGridAssignedToKeyPressed);
	dataGridAssignedTo.attachEvent("onRowSelect",doOnDataGridAssignedToRowSelectHandler);
	dataGridAssignedTo.attachEvent("onRowDblClicked",doOnDataGridAssignedToRowDblClicked);
	
	dataGridAssignedTo.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridAssignedToUser = new dhtmlXGridObject('GRD_ASSIGNED_TO_USER'); 
	dataGridAssignedToUser.imgURL = "images/"; 
	dataGridAssignedToUser.setHeader("User Name"); 
	dataGridAssignedToUser.setInitWidths("200");
	dataGridAssignedToUser.setColAlign("left");
	dataGridAssignedToUser.setColTypes("ro"); 
	dataGridAssignedToUser.setColSorting("assignedTo_userName_sort");	
	dataGridAssignedToUser.enableMultiselect('true');
	dataGridAssignedToUser.setNoHeader(true);
	dataGridAssignedToUser.init();
	dataGridAssignedToUser.sortRows(0,'str',"asc");
	dataGridAssignedToUser.setSortImgState(true,0,"ASC");	
	dataGridAssignedToUser.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	dataGridAssignedToUser.attachEvent("onKeyPress",onGridAssignedToUserKeyPressed);
	dataGridAssignedToUser.attachEvent("onRowSelect",doOnDataGridAssignedToUserRowSelectHandler);
	dataGridAssignedToUser.attachEvent("onRowDblClicked",doOnDataGridAssignedToUserRowDblClicked);

}
function doOnDataGridAssignedToRowDblClicked() {	
	var text = (dataGridAssignedTo.cells(dataGridAssignedTo.getSelectedId(),0)).getValue();
	selectItem(dataGridAssignedTo, dataGridAssignedToUser);
	removeIdFromBackUp(dataGridAssignedTo, text);		
}
function doOnDataGridAssignedToRowSelectHandler() {
	dataGridAssignedToUser.clearSelection();
}
function doOnDataGridAssignedToUserRowSelectHandler() {
	dataGridAssignedTo.clearSelection();
}
function doOnDataGridAssignedToUserRowDblClicked() {	
	selectItem(dataGridAssignedToUser,dataGridAssignedTo);
	resetFilterBackUp(dataGridAssignedTo);
}
function assignedTo_userName_sort(a,b,order,aId,bId) {
	a0 = dataGridAssignedTo.getUserData(aId,"activeUserName");
	b0 = dataGridAssignedTo.getUserData(bId,"activeUserName");	
	return sort_data(a0,b0,order);
}

function assignedTo_userRole_sort(a,b,order,aId,bId) {
	a0 = dataGridAssignedTo.getUserData(aId,"userRoleTitle");
	b0 = dataGridAssignedTo.getUserData(bId,"userRoleTitle");	
	return sort_data(a0,b0,order);
}

function loadGridAssignedTo(){
	dataGridAssignedTo.clearAll();
	dataGridAssignedTo.loadXML("position.do?mode=XMLActiveUsers");
}

function onGridAssignedToKeyPressed(keyCode,ctrl,shift) {	
	var text = (dataGridAssignedTo.cells(dataGridAssignedTo.getSelectedId(),0)).getValue();
	dataGridAssignedToUser.clearSelection();
	onGridObjKeyPressed(dataGridAssignedTo,dataGridAssignedToUser,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(dataGridAssignedTo, text);
	}
}

function onGridAssignedToUserKeyPressed(keyCode,ctrl,shift) {
	dataGridAssignedTo.clearSelection();
	onGridObjKeyPressed(dataGridAssignedToUser,dataGridAssignedTo,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(dataGridAssignedTo);
	}
}

function doOnLoadingEndAssignedTo() {
	dataGridAssignedTo.sortRows(0,'str',"asc");
	dataGridAssignedTo.setSortImgState(true,0,"ASC");
	if (_step.assignedTo != '' && _step.assignedTo.length > 0) {
		selectItems(_step.assignedTo,dataGridAssignedTo,dataGridAssignedToUser);
	}else{
		selectItems("",dataGridAssignedTo,dataGridAssignedToUser);
	}
}

//GRID For Scheduled By
function initGridScheduledBy() {	
	dataGridScheduledBy = new dhtmlXGridObject('GRD_SCHEDULED_BY'); 
	dataGridScheduledBy.imgURL = "images/"; 
	dataGridScheduledBy.setHeader("User Name"); 
	dataGridScheduledBy.setInitWidths("200");
	dataGridScheduledBy.setColAlign("left");
	dataGridScheduledBy.setColTypes("ro"); 
	dataGridScheduledBy.setColSorting("scheduledBy_userName_sort");
	dataGridScheduledBy.setNoHeader(true);	
	dataGridScheduledBy.init();
	loadGridScheduledBy();	
	dataGridScheduledBy.attachEvent("onXLE",doOnLoadingEndScheduledBy);
	dataGridScheduledBy.attachEvent("onKeyPress",onGridScheduledByKeyPressed);
	dataGridScheduledBy.attachEvent("onRowSelect",doOnDataGridScheduledByRowSelectHandler);
	dataGridScheduledBy.attachEvent("onRowDblClicked",doOnDataGridScheduledByRowDblClicked);
	
	dataGridScheduledBy.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridScheduledByUser = new dhtmlXGridObject('GRD_SCHEDULED_BY_USER'); 
	dataGridScheduledByUser.imgURL = "images/"; 
	dataGridScheduledByUser.setHeader("User Name"); 
	dataGridScheduledByUser.setInitWidths("200");
	dataGridScheduledByUser.setColAlign("left");
	dataGridScheduledByUser.setColTypes("ro"); 
	dataGridScheduledByUser.setColSorting("scheduledBy_userName_sort");
	dataGridScheduledByUser.setNoHeader(true);		
	dataGridScheduledByUser.init();
	dataGridScheduledByUser.sortRows(0,'str',"asc");
	dataGridScheduledByUser.setSortImgState(true,0,"ASC");	
	dataGridScheduledByUser.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	dataGridScheduledByUser.attachEvent("onKeyPress",onGridScheduledByUserKeyPressed);
	dataGridScheduledByUser.attachEvent("onRowSelect",doOnDataGridScheduledByUserRowSelectHandler);
	dataGridScheduledByUser.attachEvent("onRowDblClicked",doOnDataGridScheduledByUserRowDblClicked);
}
function doOnDataGridScheduledByRowDblClicked() {
	var text = (dataGridScheduledBy.cells(dataGridScheduledBy.getSelectedId(),0)).getValue();
	selectItem(dataGridScheduledBy,dataGridScheduledByUser);
	removeIdFromBackUp(dataGridScheduledBy, text);		
}
function doOnDataGridScheduledByRowSelectHandler() {
	dataGridScheduledByUser.clearSelection();
}
function doOnDataGridScheduledByUserRowSelectHandler() {
	dataGridScheduledBy.clearSelection();
}
function doOnDataGridScheduledByUserRowDblClicked() {
	selectItem(dataGridScheduledByUser,dataGridScheduledBy);
	resetFilterBackUp(dataGridScheduledBy);
}
function scheduledBy_userName_sort(a,b,order,aId,bId) {
	a0 = dataGridScheduledBy.getUserData(aId,"activeUserName");
	b0 = dataGridScheduledBy.getUserData(bId,"activeUserName");	
	return sort_data(a0,b0,order);
}

function scheduledBy_userRole_sort(a,b,order,aId,bId) {
	a0 = dataGridScheduledBy.getUserData(aId,"userRoleTitle");
	b0 = dataGridScheduledBy.getUserData(bId,"userRoleTitle");	
	return sort_data(a0,b0,order);
}

function loadGridScheduledBy(){
	dataGridScheduledBy.clearAll();
	dataGridScheduledBy.loadXML("position.do?mode=XMLActiveHRUsers");
}

function onGridScheduledByKeyPressed(keyCode,ctrl,shift) {
	var text = (dataGridScheduledBy.cells(dataGridScheduledBy.getSelectedId(),0)).getValue();
	dataGridScheduledByUser.clearSelection();
	onGridObjKeyPressed(dataGridScheduledBy,dataGridScheduledByUser,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(dataGridScheduledBy, text);
	}
}

function onGridScheduledByUserKeyPressed(keyCode,ctrl,shift) {
	dataGridScheduledBy.clearSelection();
	onGridObjKeyPressed(dataGridScheduledByUser,dataGridScheduledBy,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(dataGridScheduledBy);
	}
}

function doOnLoadingEndScheduledBy() {
	populateScheduled();
	dataGridScheduledBy.sortRows(0,'str',"asc");
	dataGridScheduledBy.setSortImgState(true,0,"ASC");
	if (_step.scheduledBy != '' && _step.scheduledBy.length > 0) {
		selectItems(_step.scheduledBy,dataGridScheduledBy,dataGridScheduledByUser);
	}else{
		selectItems("",dataGridScheduledBy,dataGridScheduledByUser);
	}
}


//Grid for DECISION_MAKER

function initGridDecisionMaker() {	
	dataGridDecisionMaker = new dhtmlXGridObject('GRD_DECISION_MAKER'); 
	dataGridDecisionMaker.imgURL = "images/"; 
	dataGridDecisionMaker.setHeader("User Name"); 
	dataGridDecisionMaker.setInitWidths("200");
	dataGridDecisionMaker.setColAlign("left");
	dataGridDecisionMaker.setColTypes("ro"); 
	dataGridDecisionMaker.setColSorting("decisionMaker_userName_sort");
	dataGridDecisionMaker.setNoHeader(true);	
	dataGridDecisionMaker.init();
	loadGridDecisionMaker();	
	dataGridDecisionMaker.attachEvent("onXLE",doOnLoadingEndDecisionMaker);
	dataGridDecisionMaker.attachEvent("onKeyPress",onGridDecisionMakerKeyPressed);
	dataGridDecisionMaker.attachEvent("onRowSelect",doOnDataGridDecisionMakerRowSelectHandler);
	dataGridDecisionMaker.attachEvent("onRowDblClicked",doOnDataGridDecisionMakerRowDblClicked);
	dataGridDecisionMaker.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridDecisionMakerUser = new dhtmlXGridObject('GRD_DECISION_MAKER_USER'); 
	dataGridDecisionMakerUser.imgURL = "images/"; 
	dataGridDecisionMakerUser.setHeader("User Name"); 
	dataGridDecisionMakerUser.setInitWidths("200");
	dataGridDecisionMakerUser.setColAlign("left");
	dataGridDecisionMakerUser.setColTypes("ro"); 
	dataGridDecisionMakerUser.setColSorting("decisionMaker_userName_sort");
	dataGridDecisionMakerUser.setNoHeader(true);	
	dataGridDecisionMakerUser.init();
	dataGridDecisionMakerUser.sortRows(0,'str',"asc");
	dataGridDecisionMakerUser.setSortImgState(true,0,"ASC");	
	dataGridDecisionMakerUser.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	dataGridDecisionMakerUser.attachEvent("onKeyPress",onGridDecisionMakerUserKeyPressed);
	dataGridDecisionMakerUser.attachEvent("onRowSelect",doOnDataGridDecisionMakerUserRowSelectHandler);
	dataGridDecisionMakerUser.attachEvent("onRowDblClicked",doOnDataGridDecisionMakerUserRowDblClicked);
}
function doOnDataGridDecisionMakerRowDblClicked() {
	var text = (dataGridDecisionMaker.cells(dataGridDecisionMaker.getSelectedId(),0)).getValue();
	selectItem(dataGridDecisionMaker,dataGridDecisionMakerUser);
	removeIdFromBackUp(dataGridDecisionMaker, text);	
}
function doOnDataGridDecisionMakerRowSelectHandler() {
	dataGridDecisionMakerUser.clearSelection();
}
function doOnDataGridDecisionMakerUserRowSelectHandler() {
	dataGridDecisionMaker.clearSelection();
}
function doOnDataGridDecisionMakerUserRowDblClicked() {
	selectItem(dataGridDecisionMakerUser,dataGridDecisionMaker);
	resetFilterBackUp(dataGridDecisionMaker);
}
function decisionMaker_userName_sort(a,b,order,aId,bId) {
	a0 = dataGridDecisionMaker.getUserData(aId,"activeUserName");
	b0 = dataGridDecisionMaker.getUserData(bId,"activeUserName");	
	return sort_data(a0,b0,order);
}
function decisionMaker_userRole_sort(a,b,order,aId,bId) {
	a0 = dataGridDecisionMaker.getUserData(aId,"userRoleTitle");
	b0 = dataGridDecisionMaker.getUserData(bId,"userRoleTitle");	
	return sort_data(a0,b0,order);
}

function loadGridDecisionMaker(){
	dataGridDecisionMaker.clearAll();
	dataGridDecisionMaker.loadXML("position.do?mode=XMLActiveUsers");
}

function onGridDecisionMakerKeyPressed(keyCode,ctrl,shift) {
	var text = (dataGridDecisionMaker.cells(dataGridDecisionMaker.getSelectedId(),0)).getValue();
	dataGridDecisionMakerUser.clearSelection();
	onGridObjKeyPressed(dataGridDecisionMaker,dataGridDecisionMakerUser,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(dataGridDecisionMaker, text);
	}
}

function onGridDecisionMakerUserKeyPressed(keyCode,ctrl,shift) {
	dataGridDecisionMaker.clearSelection();
	onGridObjKeyPressed(dataGridDecisionMakerUser,dataGridDecisionMaker,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(dataGridDecisionMaker);
	}
}

function doOnLoadingEndDecisionMaker() {
	populateIsDecisionMakerSameAsAssignedTo();
	dataGridDecisionMaker.sortRows(0,'str',"asc");
	dataGridDecisionMaker.setSortImgState(true,0,"ASC");	
	if (_step.decisionMaker != '' && _step.decisionMaker.length > 0) {
		selectItems(_step.decisionMaker,dataGridDecisionMaker,dataGridDecisionMakerUser);
	}else{
		selectItems("",dataGridDecisionMaker,dataGridDecisionMakerUser);
	}
}


//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

/*Grid for User List Control*/

function populateStages() {
	var pars = "mode=getStageListInXML";
	var myAjax = ajaxCall("step.do","get",pars,onCompleteStageRequest,reportError);
}

function onCompleteStageRequest(request){
	xmlFile = request.responseXML;
	
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))
  		return;
	
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_steps.error.unable_to_get_stages"/>');
		return;
	}
	var formsArray= xmlFile.getElementsByTagName("forms")[0].firstChild.nodeValue;
	selectStage(formsArray);
}
function selectStage(formsArray){
	selectBoxStage.reInitialize(eval(formsArray),'-1');
	if(_stepIndex == -1) {
				
	} else {
		elem = $('stage');
		elem.value = selectBoxStage.getText(selectBoxStage.getIndexWithId(_step.stepLevel));
		
		stageDivId = $('stageDiv');
		Element.hide(stageDivId);
	}
}

function onChangeStage(){
	var selId = selectBoxStage.getSelectedId();
	if (selId!=null && selId!='-1') {
		var pars = "mode=getStepsInStage&stepLevel=" + selId + "&systemStep=0&stepDisabled=0";	
		var myAjax = ajaxCall("step.do",'get',pars,onChangeStageResponse, reportError);	
	}
}

function onChangeStageResponse(request) {
	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_steps.error.change_stage"/>');
		selectStage();
		return;
	}	
 	var formsArray= xmlFile.getElementsByTagName("forms")[0].firstChild.nodeValue;
 	selectBoxStep.reInitialize(eval(formsArray),'-1');
}

function onChangeStep(){	
	var selId = selectBoxStep.getSelectedId();
	var map = <bean:write name="map" scope="request" filter="false" />;
	populateScheduledImg(map[selId]);
}

function populateScheduledImg(scheduled) {
	elem = $('scheduled');
	divId = $('schedulers');
	if (scheduled == '<%=StepConstants.TRUE%>') {
		elem.src = checkedImg;
		Element.show(divId);
	} else {
		elem.src = uncheckedImg;
		Element.hide(divId);
	}
}

</script>
