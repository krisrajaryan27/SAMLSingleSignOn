<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script src="js/tpSelectListFunctions.js"></script> 
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>							
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>		
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>											
<script type="text/javascript">
<!--
var selectBoxUsers=null;
//-->
</script>
<%
	String approvers = (String)request.getAttribute("jsArrayApprovers");
	boolean activation=false;
	if(approvers==null){
		activation= true;
	}
%>			
<div class="contentDiv">
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
	<br>
	<% } %>	
	<%@ include file="positionTabs.jsp"%>
	<div class="outerDiv" style="border-top:0px;padding-bottom:23px;">
		<html:form action="/position">
			<html:hidden property="mode" name="positionForm"/>
			<html:hidden property="dir" name="positionForm"/>
			<html:hidden property="dest" name="positionForm"/>
			<html:hidden property="step" name="positionForm"/>
			<html:hidden property="positionId" name="positionForm"/>	
			<html:hidden property="feedbackDecision" name="positionForm"/>	
			<html:hidden property="nextUserId" name="positionForm"/>
			<html:hidden property="fromApprovalStepId" name="positionForm"/>	
			<html:hidden property="toApprovalStepId" name="positionForm"/>	
			<html:hidden property="showCondition" name="positionForm"/>	
			<html:hidden property="notifyUserIds" name="positionForm" />
			<html:hidden property="requisitionApprovalTemplateId" name="positionForm" />
			<html:hidden property="draftId" name="positionForm"/>		
			<html:hidden property="draftName" name="positionForm"/>		
			<html:hidden property="positionStatus" name="positionForm"/>	
			<html:hidden property="approvalUserIds" name="positionForm" />
		<table>
			<tr>
				<td>		
				<div class="contentDiv">
					<logic:notEqual name="positionForm" property="dir"  value="<%=PositionConstants.DIR_VIEW_POSITION%>">
						<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_EDIT_POSITION%>">
							<logic:present name="jsArrayRequisitionApprovalTemplates" scope="request">
							<table class="posinput" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:15px;">
								<tr>
									<td><bean:message key="requisition_approval_feedback.label.select_requisition_approval_template" />&nbsp;</td>
									<td>
										<script language="JavaScript">
											var opts = <%=request.getAttribute("jsArrayRequisitionApprovalTemplates")%>;
											<logic:empty property="requisitionApprovalTemplateId" name="positionForm">
											var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
											opts = opt.concat(opts);
											</logic:empty>
											selectBoxRequisitionApprovalTemplate = new SelectBox(opts,'<bean:write property="requisitionApprovalTemplateId" name="positionForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											document.write(selectBoxRequisitionApprovalTemplate.getHtml());
											selectBoxRequisitionApprovalTemplate.init();
											selectBoxRequisitionApprovalTemplate.setOnChangeHandler('onChangeRequisitionApprovalTemplate');
										</script>
									</td>
								</tr>
							</table>
							</logic:present>
						</logic:notEqual>
					</logic:notEqual>
					<logic:notEmpty property="requisitionApprovalTemplateId" name="positionForm">
					<table class="posinput" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:15px;">
						<tr>
							<td class="label"><bean:message key="requisition_approval_feedback.label.requisition_approval_template_name"/>:</td>
							<td><bean:write name="positionForm" property="requisitionApprovalTemplateName" /><br/></td>
						</tr>
						<tr>
							<td>
							  <img 
								  <logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>" property="feedbackDecision" name="positionForm">
									  src="images/checkedradiobutton.gif" 
								  </logic:equal>
								  <logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>" property="feedbackDecision" name="positionForm">
								    src="images/radiobutton.gif" 
								  </logic:notEqual>
					    		name="rdo"
								id='img_<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE %>'
								onclick="onChangeFeedbackDecision('rdo','<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>');">&nbsp;
								<% if(activation){ %>
									<bean:message key="requisition_approval_feedback.label.activate"/>
								<%}else{ %>
									<bean:message key="requisition_approval_feedback.label.move_next"/>
								<%} %>
								&nbsp;
							</td>
							<td class="normal">
								<% if(!activation){ %>
								<script language="JavaScript">	
									var opts = <%=approvers%>						
									var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
									opts = opt.concat(opts);
									selectBoxUsers = new SelectBox(opts,'<bean:write property="nextUserId" name="positionForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:15, textboxclass:'Grey'});
									document.write(selectBoxUsers.getHtml());
									selectBoxUsers.init();
									selectBoxUsers.setOnChangeHandler('changeNotifyUsers');
								</script>
								<% } %>
							</td>
						</tr>
						<tr>
							<td>
							  <img 
								  <logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>" property="feedbackDecision" name="positionForm">
									  src="images/checkedradiobutton.gif" 
								  </logic:equal>
								  <logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>" property="feedbackDecision" name="positionForm">
								    src="images/radiobutton.gif" 
								  </logic:notEqual>
					    		name="rdo"
								id='img_<%=RequisitionConstants.FEEDBACK_ACTION_HOLD %>'
								onclick="onChangeFeedbackDecision('rdo','<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>');">&nbsp;
								<bean:message key="requisition_approval_feedback.label.keep_on_hold"/>
								&nbsp;
							</td>
							<td class="normal">
							</td>
						</tr>
					</table>
					<table class="posinput" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:20px;">
						<tr>
								<td class="label">
								<bean:message key="requisition_approval_feedback.label.comment"/>
								</td>
						</tr>
						<tr>
								<td>
								<html:textarea property="feedbackComment" rows="5" cols="65" styleClass="Grey"></html:textarea>
								</td>
						</tr>
					</table>
					<table class="posinput" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="label">
								<bean:message key="requisition_approval_feedback.label.notify"/>
							</td>
						</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0" style="margin-top:5px;margin-bottom: 5px;">
						<tr>
							<td width="250px"><div id="GRD1" style="height: 130px;width: 250px;overflow: visible;"></div></td>
							<td width="34px" align="center">
								<br/><br/>
								<a href="#" onclick="javascript: mySelectItem(dataGrid,dataGrid2);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a> 
								<br/>
								<a href="#" onclick="javascript: myDeselectItem(dataGrid2,dataGrid);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" /></a> 
							</td>
							<td><div id="GRD2" style="height: 130px;width: 250px;overflow: visible;"></div></td>		
						</tr>
					</table>
					</logic:notEmpty>
				</div>		
				</td>
			</tr>
		</table>			
		</html:form>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
					<br/>
					<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<div class="navBtn" style="float:left;">
						<a href="#" style="width:110px;" class="active" onclick="javascript:saveAsDraft();"><span class="rightC"></span><span class="leftC"></span><bean:message key="add_position.label.save_as_draft"/></a>
					</div>
					</logic:notEqual>
					<div class="navBtn" style="float:right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:backToRequirements();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
						<logic:notEmpty property="requisitionApprovalTemplateId" name="positionForm">
							<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
								<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
									<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:addApproval();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
								</logic:equal>
								<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
									<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:createPosition();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.finish"/></a>
								</logic:notEqual>
							</logic:equal>
							<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
								<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:copyPosition();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.finish"/></a>
							</logic:equal>
						</logic:notEmpty>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
	</table>
	<br/>	
</div>
<script language="JavaScript">
function saveAsDraft() {
	var url="position.do?mode=savePositionAsDraft&positionId="+document.positionForm.positionId.value;
	window.setTimeout("showInPopUp('"+url+"',500,250,onGetDraftName,true);",10);
}

function onGetDraftName(returnVal) {	
	document.positionForm.mode.value='savePositionAsDraft';
	document.positionForm.draftName.value=returnVal;
	if(document.positionForm.feedbackDecision.value == '<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>' && selectBoxUsers!=null){
		document.positionForm.nextUserId.value=selectBoxUsers.getSelectedId();
	}
	document.positionForm.notifyUserIds.value=dataGrid2.getAllItemIds(',');
	document.positionForm.submit();
	return true;
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function onChangeFeedbackDecision(imgGroupName, attachmentId){
	var prevId = document.positionForm.feedbackDecision.value;
	var nextId = onRadioChange(imgGroupName, attachmentId,prevId); 
	if(nextId!=-1){
		document.positionForm.feedbackDecision.value=nextId;
	}
}
function onRadioChange(imgGroupName, attachmentId, prevId){
	var imgs = document.getElementsByName(imgGroupName);
	var fId = -1;
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					theImage.src = "images/checkedradiobutton.gif";
					fId= attachmentId;
				}else{
					theImage.src = "images/radiobutton.gif";
				}
			}
	}
	return fId;
}
function validateAndSetFormVars(){	
	if(document.positionForm.feedbackDecision.value=='<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>'){		
		if(selectBoxUsers!=null){
			if (selectBoxUsers.getSelectedId() == '-1') {
				if(document.positionForm.positionStatus.value!=<%=PositionConstants.POSITION_STATUS_TEMPLATE %>){		
					alert('<bean:message key="requisition_approval_feedback.error.select_user"/>');
					return false;
				}
			}else{
				document.positionForm.nextUserId.value=selectBoxUsers.getSelectedId();
			}
		} else {
			document.positionForm.nextUserId.value='';			
		}
	}else{
		document.positionForm.nextUserId.value='-1';
		document.positionForm.toApprovalStepId.value='';
	}
	
	return true;
}

function backToRequirements() {
	<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
		document.positionForm.mode.value='<%=PositionConstants.MODE_ADD_POSITION%>';
	</logic:equal>
	<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
		document.positionForm.mode.value='<%=PositionConstants.MODE_COPY_POSITION%>';
	</logic:equal>
	document.positionForm.dest.value='<%=PositionConstants.DEST_REQUIREMENTS%>';
	if(document.positionForm.feedbackDecision.value == '<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>' && selectBoxUsers!=null){
		document.positionForm.nextUserId.value=selectBoxUsers.getSelectedId();
	}
	document.positionForm.notifyUserIds.value=dataGrid2.getAllItemIds(',');
	document.positionForm.submit();
	return true;
}

function createPosition() {
	if(validateAndSetFormVars()){
		document.positionForm.mode.value='<%=PositionConstants.MODE_ADD_POSITION%>';
		document.positionForm.dest.value='';
		document.positionForm.notifyUserIds.value=dataGrid2.getAllItemIds(',');
		document.positionForm.submit();
	}
}

function copyPosition() {
	if(validateAndSetFormVars()){
		document.positionForm.mode.value='<%=PositionConstants.MODE_COPY_POSITION%>';
		document.positionForm.dest.value='<%=PositionConstants.DEST_HIRING_PROCESS%>';
		if(document.positionForm.feedbackDecision.value == '<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>' && selectBoxUsers!=null){
			document.positionForm.nextUserId.value=selectBoxUsers.getSelectedId();
		}
		document.positionForm.notifyUserIds.value=dataGrid2.getAllItemIds(',');
		document.positionForm.submit();
	}
}

function backToPositionHome() {
	if(document.positionForm.showCondition.value==<%=PositionConstants.POSITION_STATUS_TEMPLATE%>){
		window.location=uncache("positionTemplate.do?mode=positionTemplatesHome");
	}else{
		window.location=uncache("position.do?mode=positionsHome");
	}
	return true;
}

window.onload=doOnLoad;
function doOnLoad() {
	initPopUp();
	<logic:notEmpty property="requisitionApprovalTemplateId" name="positionForm">
	initGrids();	
	</logic:notEmpty>
	<% if(activation){ %>
	document.positionForm.nextUserId.value='';
	<% } %>
}
var prevApprover = '';
//function changeNotifyUsers() {	
//	//selectItems(prevApprover,dataGrid2,dataGrid);
//	if(selectBoxUsers.getSelectedId() != -1) {
//		selectItems(selectBoxUsers.getAllSelectOptionIds(),dataGrid,dataGrid2);		
//	}
//	//selectItems(document.positionForm.notifyUserIds.value,dataGrid,dataGrid2);	
//	//prevApprover = selectBoxUsers.getSelectedId()
//}
function changeNotifyUsers() {	
	selectItems(prevApprover,dataGrid2,dataGrid);
	if(selectBoxUsers.getSelectedId() != -1) {
		selectItems(selectBoxUsers.getSelectedId(),dataGrid,dataGrid2);		
	}
	selectItems(document.positionForm.notifyUserIds.value,dataGrid,dataGrid2);	
	prevApprover = selectBoxUsers.getSelectedId()
}


function addApproval(){
	if(validateAndSetFormVars()){
		document.positionForm.mode.value='<%=PositionConstants.MODE_ADD_POSITION%>';
		document.positionForm.dest.value='<%=PositionConstants.DEST_HIRING_PROCESS%>';
		document.positionForm.notifyUserIds.value=dataGrid2.getAllItemIds(',');
		document.positionForm.submit();
		return true;
	}	
}

var dataGrid;
var dataGrid2;
function initGrids() {	
	dataGrid = new dhtmlXGridObject('GRD1'); 
	dataGrid.imgURL = "images/"; 
	dataGrid.setHeader("Users"); 
	dataGrid.setInitWidths("230");
	dataGrid.setColAlign("left");
	dataGrid.setColTypes("ro"); 
	dataGrid.setColSorting("str");	
	dataGrid.enableMultiselect('true');
	dataGrid.attachEvent("onXLE",doOnLoadingEnd);
	dataGrid.attachEvent("onKeyPress",onGrid1KeyPressed);
	dataGrid.attachEvent("onRowSelect",doOnDataGridRowSelectHandler);
	dataGrid.attachEvent("onRowDblClicked",doOnRowDblClicked);
	dataGrid.init();  
	dataGrid.loadXML("position.do?mode=XMLActiveUsers");	
	dataGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
		
	dataGrid2 = new dhtmlXGridObject('GRD2'); 
	dataGrid2.imgURL = "images/"; 
	dataGrid2.setHeader("Selected Users"); 
	dataGrid2.setInitWidths("230");
	dataGrid2.setColAlign("left");
	dataGrid2.setColTypes("ro"); 
	dataGrid2.setColSorting("str");	
	dataGrid2.init();     
	dataGrid2.sortRows(0,'str',"asc");
	dataGrid2.setSortImgState(true,0,"asc");
	dataGrid2.attachEvent("onRowSelect",doOnDataGrid2RowSelectHandler);
	dataGrid2.attachEvent("onRowDblClicked",doOnDataGrid2RowDblClicked);
	dataGrid2.attachEvent("onKeyPress",onGrid2KeyPressed);
	dataGrid2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	
	dataGrid.loadXML("position.do?mode=XMLActiveUsers");
}

function doOnDataGrid2RowSelectHandler() {
	dataGrid.clearSelection();
}

function doOnDataGridRowSelectHandler() {
	dataGrid2.clearSelection();
}

function doOnRowDblClicked() {
	mySelectItem(dataGrid,dataGrid2);
}

function doOnDataGrid2RowDblClicked() {
	myDeselectItem(dataGrid2,dataGrid);
}

function onGrid1KeyPressed(keyCode,ctrl,shift) {
	dataGrid2.clearSelection();
	onGridObjKeyPressed(dataGrid,dataGrid2,4,keyCode,ctrl,shift);
}

function onGrid2KeyPressed(keyCode,ctrl,shift) {
	dataGrid.clearSelection();
	onGridObjKeyPressed(dataGrid2,dataGrid,4,keyCode,ctrl,shift);
}

function doOnLoadingEnd() {
	dataGrid.sortRows(0,'str',"asc");
	dataGrid.setSortImgState(true,0,"asc");
	selectItems('<bean:write property="nextUserId" name="positionForm"/>',dataGrid,dataGrid2);	
	selectItems('<bean:write property="notifyUserIds" name="positionForm"/>',dataGrid,dataGrid2);	
}

function mySelectItem(dataGrid,dataGrid2) {
	selectedId = dataGrid.getSelectedId();
	selectItem(dataGrid,dataGrid2);
	parts = document.positionForm.notifyUserIds.value.split(',');
	isSelected = false;
	for(var i = 0; i < parts.length; i++) {
		if(parts[i] == selectedId) {
			isSelected = true;
		}
	}
	if(!isSelected) {
		parts[parts.length] = selectedId;
		document.positionForm.notifyUserIds.value = parts.join(',');
	}
}

function myDeselectItem(dataGrid2,dataGrid) {
	deselectedId = dataGrid2.getSelectedId();
	deselectItem(dataGrid2,dataGrid);
	parts = document.positionForm.notifyUserIds.value.split(',');
	isSelected = false;
	for(var i = 0; i < parts.length; i++) {
		if(parts[i] == deselectedId) {
			parts.splice(i, 1);			
		}
	}
	document.positionForm.notifyUserIds.value = parts.join(',');
}
function onChangeRequisitionApprovalTemplate() {
	document.positionForm.requisitionApprovalTemplateId.value = selectBoxRequisitionApprovalTemplate.getSelectedId();
	document.positionForm.mode.value = 'requisitionApprovalTemplateChosen';
	document.positionForm.approvalUserIds.value = '';
	document.positionForm.nextUserId.value = '-1';
	document.positionForm.submit();
	return true;
}
</script>