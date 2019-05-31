<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>
<%@page import="com.talentPool.requisition.dataobject.RequisitionApprovalStepData"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>							
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/doClasses/RequisitionApprovalStepClass.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript">
var _requisitionSteps = new Array();
<logic:present name="steps" scope="request">
	<logic:iterate id="step" name="steps" scope="request" type="RequisitionApprovalStepData" indexId="cnt">
		var step = new RequisitionStep();
		step.setIndex(<%=cnt%>);
		step.setStepId('<bean:write name="step" property="requisitionApprovalStepId" />');
		step.setStepTitle('<bean:write name="step" property="requisitionApprovalStepName" />');
		step.setUserIds('<bean:write name="step" property="userIds" />');
		step.setUserNames('<bean:write name="step" property="userNames" />');
		_requisitionSteps[_requisitionSteps.length] = step;
	</logic:iterate>
</logic:present>
</script>
<div class="contentDiv">			
	<% if(request.getAttribute(Globals.ERROR_KEY)!=null){ %>
	<table id="m_errortable" >
		<tr><td class="header"><b><bean:message key="errors.following_errors"/></b></td></tr>
	    <tr><td class="message"><html:errors/></td></tr>
	</table>
	<br/>
	<% } %>	
	<html:form action="/requisition">
		<html:hidden property="mode" name="requisitionForm"/>
		<html:hidden property="requisitionApprovalTemplateId" name="requisitionForm"/>
		<html:hidden property="jsArrayRequisitionApprovalSteps" name="requisitionForm"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr>
				<td width="240px;" class="Grey"><bean:message key="requisition_approval_steps.label.template_name" />
				<span class="star">*</span>
				:</td>
				<td><html:text name="requisitionForm" property="requisitionApprovalTemplateName" size="30" maxlength="255"/></td>
			</tr>
		</table>
		<br/>
		<div style="width:190px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="requisition_approval_steps.label.title"/></div>
		<div class="outerDiv" style="height:287px;overflow:auto;">	
			<table id="requisitionSteps" class="hiringprocess" width="100%" border="0" cellspacing="0" cellpadding="0"> 
					
			</table>
		</div>
		<br class="br5"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr>
				<% if(ModuleSet.isMODULE_REQUISITION()){ %>
					<td>
						<div class="navBtn" style="float:left;">
						<a href="#" class="btn3" style="width:120px;" class="active" onclick="javascript: showStepPopup('');"><span class="rightC"></span><span class="leftC"></span><bean:message key="position.hiring_process.label.add_new_step"/></a>
						</div>
					</td>						
				<% } %>	
				<td>
					<div class="navBtn" style="float:right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:saveOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>		
	</html:form>
</div>
<script language="JavaScript">
function populateRequisitionSteps() {
	tbl = $('requisitionSteps');
	if (tbl) {
		var tBody= tbl.getElementsByTagName("tbody")[0];
		if(tBody){
			var tRows = tBody.childNodes;
			for(var i = tRows.length - 1; i >= 0; i--){
				tBody.removeChild(tRows[i]);
			}
		}
		
		for (var i = 0; i < _requisitionSteps.length; i++) {
			_step = _requisitionSteps[i];
			_step.setIndex(i);
			if(tBody){}else{
				tBody = document.createElement("TBODY");
			}
			var tRow = document.createElement("TR");
			var tCell0 = document.createElement("TD");
			tCell0.style.width="50px";
			tCell0.style.height="40px";
			tCell0.className="label";
			tCell0.innerHTML = '<bean:message key="requisition_approval_steps.label.step" />' + '&nbsp;' + (i + 1) + '<bean:message key="common.colon" />';		
			tRow.appendChild(tCell0);
			
			var tCell1 = document.createElement("TD");
			tCell1.style.height="40px";
			tCell1.className="label";
			innerHTML = '<a href="#" class="green" onclick="javascript: showStepPopup(' + i + ');"><strong>' + _step.stepTitle + '</strong></a>';
			innerHTML += '<br/>';
			innerHTML += _step.userNames;
			tCell1.innerHTML = innerHTML;		
			tRow.appendChild(tCell1);

			var tCell2 = document.createElement("TD");
			tCell2.style.width="30px";
			tCell2.style.height="40px";
			tCell2.className="label";
			tCell2.style.verticalAlign="middle";
			tCell2.innerHTML = '<img style="cursor:pointer;" title="<bean:message key="common.delete"/>" src="images/ico_delete.gif" onclick="javascript: deleteStep(' + i + ');"/>';
			tRow.appendChild(tCell2);
			
			var tCell3 = document.createElement("TD");
			tCell3.style.width="30px";
			tCell3.style.height="40px";
			tCell3.className="label";
			tCell3.style.verticalAlign="middle";
			tCell3.innerHTML = '<img style="cursor:pointer;margin-top:3px;" title="<bean:message key="common.move_up"/>" src="images/btn_uparrow.gif" onclick="javascript: changeRank(' + i + ',<%=RequisitionConstants.MOVE_UP %>);"/>		';
			tRow.appendChild(tCell3);
						
			var tCell4 = document.createElement("TD");
			tCell4.style.width="30px";
			tCell4.style.height="40px";
			tCell4.className="label";
			tCell4.style.verticalAlign="middle";
			tCell4.innerHTML = '<img style="cursor:pointer;margin-top:3px;" title="<bean:message key="common.move_down"/>" src="images/btn_dwnarrow.gif" onclick="javascript: changeRank(' + i + ',<%=RequisitionConstants.MOVE_DOWN %>);"/>		';
			tRow.appendChild(tCell4);
														
			tBody.appendChild(tRow);
			tbl.appendChild(tBody);
		}
	}
}

function saveOperation() {
	errors = validateRequisitionApprovalTemplate();
	if(errors != '') {
		alert(errors);
		return false;
	}
	document.requisitionForm.mode.value = 'saveRequisitionApprovalTemplate';
	document.requisitionForm.jsArrayRequisitionApprovalSteps.value = _requisitionSteps.toString();
	document.requisitionForm.submit();
	return true;
}

function validateRequisitionApprovalTemplate() {
	errors = '';
	if(document.requisitionForm.requisitionApprovalTemplateName.value.trim() == '') {
		errors = addError(errors, '<bean:message key="requisition_approval_steps.error.template_name" />');	
	}
	if(_requisitionSteps.length == 0) {
		errors = addError(errors, '<bean:message key="requisition_approval_steps.error.at_least_one_step" />');	
	}
	return errors;
}

function cancelOperation() {
	window.location="requisition.do?mode=manageRequisitionApprovalTemplates";
}

function showStepPopup(indx) {
	url = "requisition.do?mode=addRequisitionStep&index=" + indx;
	if(indx != '') {
		 url += "&requisitionApprovalStepId="+_requisitionSteps[indx].stepId;
	}
	showPopWin(url, "580", "320", updateRequisitionApprovalSteps,true);
}

function actionOnLoad(){
	initPopUp();
	populateRequisitionSteps();
}
<logic:empty name="requisitionForm" property="requisitionApprovalTemplateId">
function deleteStep(indx){
	deleteStepFromJSArray(indx);
}
</logic:empty>
function deleteStepFromJSArray(indx){
	_requisitionSteps.splice(indx, 1);
	populateRequisitionSteps();
}
<logic:notEmpty name="requisitionForm" property="requisitionApprovalTemplateId">
var indxOfStepToDelete;
function deleteStep(indx){	
	if(_requisitionSteps[indx].stepId == 0) {
		deleteStepFromJSArray(indx);
	} else {
		indxOfStepToDelete = indx;
		var pars = "mode=isDeleteRequisitionStepPossible&requisitionApprovalStepId="+_requisitionSteps[indx].stepId;
		var myAjax = ajaxCall("requisition.do",'get',pars,onDoDeleteRequisitionStepResponse, reportError);
	}
}

function onDoDeleteRequisitionStepResponse(request) {
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){	 		
		alert('<bean:message key="requisition_approval_steps.error.requisitions_inprocess" />');
		return;
	} else {
		deleteStepFromJSArray(indxOfStepToDelete);
	}
}
</logic:notEmpty>
function changeRank(indx, moveDirection){
	if(_requisitionSteps.length > 1) {
		if(moveDirection == <%=RequisitionConstants.MOVE_DOWN %>) {
			if(indx != (_requisitionSteps.length - 1)) {
				swapSteps(indx, indx+1);
			}
		} else if(moveDirection == <%=RequisitionConstants.MOVE_UP %>) {
			if(indx != 0) {
				swapSteps(indx, indx-1);
			}
		}
		populateRequisitionSteps();
	}
}
function swapSteps(id1, id2) {               
	_temp = _requisitionSteps[id1];
	_requisitionSteps[id1]=_requisitionSteps[id2];
	_requisitionSteps[id2]=_temp;
}

window.onload=actionOnLoad;

function updateRequisitionApprovalSteps(returnVal) {
	if(returnVal.index == -1) {
		returnVal.setStepId(0);
		returnVal.setIndex(_requisitionSteps.length);
		_requisitionSteps[_requisitionSteps.length] = cloneRequisitionStep(returnVal);
	} else {
		for(var i = 0; i < _requisitionSteps.length; i++) {
			if(_requisitionSteps[i].index == returnVal.index) {
				_requisitionSteps[i] = cloneRequisitionStep(returnVal);
			}
		}
	}
	populateRequisitionSteps();
}

function cloneRequisitionStep(_step) {
	var step = new RequisitionStep();
	step.setIndex(_step.index);
	step.setStepId(_step.stepId);
	step.setStepTitle(_step.stepTitle.escapeHTML());
	step.setUserIds(_step.userIds);
	step.setUserNames(_step.userNames);
	return step;
}
</script>