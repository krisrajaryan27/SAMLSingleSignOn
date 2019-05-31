<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.common.db.SimpleDataObject"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>				
<script language="JavaScript" src="js/doClasses/RequisitionApprovalStepClass.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script> 	
<style>
body {
	background: #F2F2F2;
}
</style>
<%
ArrayList userIds = (ArrayList)request.getAttribute("userIds");
ArrayList userNames = (ArrayList)request.getAttribute("userNames");
%>
				
<script type="text/javascript">
var checkBoxListUsers=null;
var activeUserIds = new Array();
var activeUserNames = new Array();
<logic:present name="activeUsers" scope="request">
<logic:iterate id="activeUser" name="activeUsers" scope="request" type="SimpleDataObject">
	activeUserIds[activeUserIds.length] = <%=activeUser.getString("userId")%>;
	activeUserNames[activeUserNames.length] = '<%=activeUser.getString("name")%>';
</logic:iterate>
</logic:present>

function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'GRD_REQUISITIONER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;	
		}	
	}if(grdId == 'GRD_REQUISITIONER_USER'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"activeUserName");				
				break;
		}	
	}
	return obj.cell.innerHTML;
}

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
<html:hidden property="userIds" name="requisitionForm"/>
<input type="hidden" name="index" value="<%=request.getParameter("index")%>" />
<html:hidden property="save" name="requisitionForm" value="1"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
		<tr>
			<td class="label">
				<bean:message key="add_requisition_approval_steps.label.step_title"/>
				<span class="star">*</span>
			</td>
			<td>
				<html:text property="requisitionApprovalStepName" size="60"  name="requisitionForm" maxlength="250"></html:text>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key="add_requisition_approval_steps.label.step_users"/>
				<span class="star">*</span>
			</td>
		</tr>
		</table>		
		
		<table cellspacing="0" cellpadding="0" style="padding-left: 5px;">
		<tr>
			<td style="vertical-align: top;">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<input id="user" name="user" type="text" size="41" value="Filter" style="width:216px;color: grey; border-bottom: 0px;" onclick="onFilterFocus('user','Filter');" onblur="onFilterUnfocus('user','Filter')"/>
					</td>
				</tr>
				<tr>
					<td class="gridborder">
					<div id="GRD_REQUISITIONER" style="width:219px;height:80px;"></div>
					</td>
				</tr>
			</table>			
			</td>		
			<td style="padding: 10px;">					
				<a href="#" onclick="javascript: selectItem(dataGridRequisitioner,dataGridRequisitionerUser);return false;" title="<bean:message key='common.add' />" >
					<img src="images/ico_rightarrow.gif"  border="0" />
				</a>
				<br/>
				<a href="#" onclick="javascript: deselectItem(dataGridRequisitionerUser,dataGridRequisitioner);return false;" title="<bean:message key='common.remove' />" >
					<img src="images/ico_leftarrow.gif"  border="0" />
				</a> 
			</td>					
			<td style="vertical-align: top;">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
					<div id="GRD_REQUISITIONER_USER" style="width:220px;height:97px;"></div>
					</td>
				</tr>
			</table>
		</tr>	
	</table>
	<br/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
		<tr>
			<td class="label">
			</td>
			<td>
			<div class="navBtn" style="float:right;">
				<a href="#" style="width:50px;" class="active" onclick="javascript:saveStep();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelOperation();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
	</table>
</html:form>
</div>
<script language="JavaScript">
var _requisitionStep;
var dataGridRequisitioner;
var dataGridRequisitionerUser;

//GRID For Requitioner
function initGridRequisitioner() {	
	dataGridRequisitioner = new dhtmlXGridObject('GRD_REQUISITIONER'); 
	dataGridRequisitioner.imgURL = "images/"; 
	dataGridRequisitioner.setHeader("User Name"); 
	dataGridRequisitioner.setInitWidths("200");
	dataGridRequisitioner.setNoHeader(true);
	dataGridRequisitioner.setColAlign("left");
	dataGridRequisitioner.setColTypes("ro");
	dataGridRequisitioner.setColSorting("requisition_userName_sort");
	dataGridRequisitioner.enableMultiselect('true');	
	dataGridRequisitioner.init();
		
	dataGridRequisitioner.sortRows(0,'str',"asc");
	dataGridRequisitioner.attachEvent("onXLE",doOnLoadingEndRequisitioner);
	dataGridRequisitioner.attachEvent("onKeyPress",onGridRequisitionerKeyPressed);
	dataGridRequisitioner.attachEvent("onRowSelect",doOndataGridRequisitionerRowSelectHandler);
	dataGridRequisitioner.attachEvent("onRowDblClicked",doOndataGridRequisitionerRowDblClicked);
	dataGridRequisitioner.attachEvent("onXLE",setRequisitionStepData);
	
	dataGridRequisitioner.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridRequisitionerUser = new dhtmlXGridObject('GRD_REQUISITIONER_USER'); 
	dataGridRequisitionerUser.imgURL = "images/"; 
	dataGridRequisitionerUser.setHeader("User Name"); 
	dataGridRequisitionerUser.setInitWidths("200");
	dataGridRequisitionerUser.setColAlign("left");
	dataGridRequisitionerUser.setColTypes("ro"); 	
	dataGridRequisitionerUser.enableMultiselect('true');
	dataGridRequisitionerUser.setNoHeader(true);
	dataGridRequisitionerUser.setColSorting("requisition_userName_sort");
	dataGridRequisitionerUser.init();
	dataGridRequisitionerUser.sortRows(0,'str',"asc");
	dataGridRequisitionerUser.setSortImgState(true,0,"ASC");	
	dataGridRequisitionerUser.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	dataGridRequisitionerUser.attachEvent("onKeyPress",onGridRequisitionerUserKeyPressed);
	dataGridRequisitionerUser.attachEvent("onRowSelect",doOndataGridRequisitionerUserRowSelectHandler);
	dataGridRequisitionerUser.attachEvent("onRowDblClicked",doOndataGridRequisitionerUserRowDblClicked);

	loadGridRequisitioner();

}
function doOndataGridRequisitionerRowDblClicked() {	
	var text = (dataGridRequisitioner.cells(dataGridRequisitioner.getSelectedId(),0)).getValue();
	selectItem(dataGridRequisitioner,dataGridRequisitionerUser);
	removeIdFromBackUp(dataGridRequisitioner, text);	
}

function doOndataGridRequisitionerRowSelectHandler() {
	dataGridRequisitionerUser.clearSelection();
}
function doOndataGridRequisitionerUserRowSelectHandler() {
	dataGridRequisitioner.clearSelection();
}
function doOndataGridRequisitionerUserRowDblClicked() {	
	selectItem(dataGridRequisitionerUser,dataGridRequisitioner);
	resetFilterBackUp(dataGridRequisitioner);
}

function requisition_userName_sort(a,b,order,aId,bId) {
	a0 = dataGridRequisitioner.getUserData(aId,"activeUserName");
	b0 = dataGridRequisitioner.getUserData(bId,"activeUserName");	
	return sort_data(a0,b0,order);
}

function loadGridRequisitioner(){
	dataGridRequisitioner.clearAll();
	dataGridRequisitioner.loadXML("requisition.do?mode=XMLActiveUsers");
}

function onGridRequisitionerKeyPressed(keyCode,ctrl,shift) {	
	var text = (dataGridRequisitioner.cells(dataGridRequisitioner.getSelectedId(),0)).getValue();
	dataGridRequisitionerUser.clearSelection();
	onGridObjKeyPressed(dataGridRequisitioner,dataGridRequisitionerUser,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(dataGridRequisitioner, text);
	}
}

function onGridRequisitionerUserKeyPressed(keyCode,ctrl,shift) {
	dataGridRequisitioner.clearSelection();
	onGridObjKeyPressed(dataGridRequisitionerUser,dataGridRequisitioner,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(dataGridRequisitioner);
	}
}

function doOnLoadingEndRequisitioner() {
	dataGridRequisitioner.sortRows(0,'str',"asc");
	dataGridRequisitioner.setSortImgState(true,0,"ASC");
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
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
			dataGridRequisitioner.filterBy(0, $('user').value, false);		
		}
	}
}

function saveStep(){
	errors = validateRequisitionStep();	
	if(errors != '') {
		alert(errors);
		return false;
	}
	returnVal = new RequisitionStep();
	if(_requisitionStep) {
		returnVal.setIndex(_requisitionStep.index);
		returnVal.setStepId(_requisitionStep.stepId);
	}	
	returnVal.setStepTitle(document.requisitionForm.requisitionApprovalStepName.value);
	returnVal.setUserIds(dataGridRequisitionerUser.getAllItemIds());
	returnVal.setUserNames(getAllItemNames(dataGridRequisitionerUser));
	window.top.hidePopWin(true);
}
function getAllItemNames(dataGrid){
	var names ='';
	var selectedUsers = dataGrid.getAllItemIds().split(',');	
	for(var j = 0; j < selectedUsers.length; j++) {
		if(names!=''){names+=',';}
		names += (dataGrid.cells(selectedUsers[j],0)).getValue();
	}
	return names
}



function validateRequisitionStep() {
	errors = '';
	if(document.requisitionForm.requisitionApprovalStepName.value.trim() == '') {
		errors = addError(errors, '<bean:message key="add_requisition_approval_steps.error.step_name_required" />');
	}
	if(dataGridRequisitionerUser.getAllItemIds() == '') {
		errors = addError(errors, '<bean:message key="add_requisition_approval_steps.error.user_required" />');
	} else if(activeUserIds.length > 0) {
		var removedActiveUsers = new Array();
		var selectedUsers = dataGridRequisitionerUser.getAllItemIds().split(',');
		for(var i = 0; i < activeUserIds.length; i++) {
			for(var j = 0; j < selectedUsers.length; j++) {				
				if(activeUserIds[i] == parseInt(selectedUsers[j].trim())) {					
					break;
				}
				if(j == (selectedUsers.length - 1)) {
					removedActiveUsers[removedActiveUsers.length] = activeUserNames[i];
				}
			}	
		}
		if(removedActiveUsers.length > 0) {
			errors = addError(errors, '<bean:message key="add_requisition_approval_steps.error.can_not_remove_user" />' + '\n\n' + removedActiveUsers.toString());
		}
	}
	return errors;
}

function cancelOperation(){
	window.top.hidePopWin(false);
}

function setPopupTitle(){
	var popupTitle = '';
	if(_requisitionStep) {
		popupTitle = '<b>'+_requisitionStep.stepTitle+'</b>';
	} else {
		popupTitle = '<b><bean:message key="add_requisition_approval_steps.label.title" /></b>';
	}
	window.top.setPopTitle(popupTitle);
}

function setRequisitionStepData() {
	var index = document.forms[0].index.value;
	if(index != '') {		
		for(var i = 0; i < window.top._requisitionSteps.length; i++) {
			if(window.top._requisitionSteps[i].index == index) {
				_requisitionStep = window.top._requisitionSteps[i];
				break;
			}
		}
		document.requisitionForm.requisitionApprovalStepName.value = unescapeHTML(_requisitionStep.stepTitle);
		selectItems(_requisitionStep.userIds ,dataGridRequisitioner,dataGridRequisitionerUser) 
	}
}

function doOnLoad() {	
	initGridRequisitioner();
	setPopupTitle();
	//setRequisitionStepData();
	//setTimeout("setRequisitionStepData()", 10);
	Event.observe($('user'), "keyup", onCriteriaChange.bindAsEventListener(this));
}

window.onload = doOnLoad;
</script>
