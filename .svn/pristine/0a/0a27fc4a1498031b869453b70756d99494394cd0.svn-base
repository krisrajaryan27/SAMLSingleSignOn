<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.budget.utils.BudgetUtils"%>
<%@page import="com.talentPool.positions.manager.PositionScreenConfigurationManager"%>
<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<%@page import="com.talentPool.positions.PositionConstants"%>

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
<script type="text/javascript">
var selectBoxUsers=null;
</script>
			
			
 <div class="contentDivPop" style="padding-bottom:23px;">
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
	<br/>
	</logic:present> 


<html:form action="/requisitionfeedback">
<html:hidden property="mode" name="requisitionFeedbackForm" value="savefeedback"/>
<html:hidden property="positionId" name="requisitionFeedbackForm"/>	
<html:hidden property="feedbackId" name="requisitionFeedbackForm"/>
<html:hidden property="feedbackDecision" name="requisitionFeedbackForm"/>	
<html:hidden property="nextUserId" name="requisitionFeedbackForm"/>
<html:hidden property="fromApprovalStepId" name="requisitionFeedbackForm"/>	
<html:hidden property="toApprovalStepId" name="requisitionFeedbackForm"/>	
<html:hidden property="notifyUserIds" name="requisitionFeedbackForm" />

	<div class="outerDiv">
		<div class="popupTop">
		<table class="tblPop" width="570">
		<tr>
	        <td class="header" width="90"><bean:message key="common.position"/><bean:message key="common.colon"/></td>
	        <td>	        	
	         	<a href="#" onclick="viewPositionDetails('<bean:write name="requisitionFeedbackForm" property="positionId"/>');return false;" 
	         		class="grey">
	         		<bean:write name="requisitionFeedbackForm" property="positionTitle"/>
	         	</a>
	         </td>
	    </tr>
	    <tr>
	         <td class="header"><bean:message key="requisition_approval_feedback.label.approval_step"/></td>
	         <td><bean:write name="requisitionFeedbackForm" property="fromStepTitle"/></td>
	    </tr>
	  
	    <% if (ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()) { %>			
	    <tr>
	         <td class="header" width="90"><bean:message key="common.budget_item"/>:</td>
	         <td><bean:write name="requisitionFeedbackForm" property="budgetItemName"/></td>
	    </tr>
	    <% } %>
	    </table>
	    </div>
		<div class="popupTop">
		<table class="tblPop">
		<tr>
	         <td class="header"><bean:message key="requisition_approval_feedback.label.feedback_decision"/></td>
	    </tr>
	    </table>
		<table class="tblPop">
	    <tr>
	         <td>
				<img 
				<logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>" property="feedbackDecision" name="requisitionFeedbackForm">
					  src="images/checkedradiobutton.gif" 
				</logic:equal>
				<logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>" property="feedbackDecision" name="requisitionFeedbackForm">
				    src="images/radiobutton.gif" 
				</logic:notEqual>
	    		name="rdo"
				id='img_<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE %>'
				onclick="onChangeFeedbackDecision('rdo','<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>');">&nbsp;
				<logic:empty name="requisitionFeedbackForm" property="nextStepUsersJsArray">
					 <bean:message key="requisition_approval_feedback.label.activate"/>
				</logic:empty>
				<logic:notEmpty name="requisitionFeedbackForm" property="nextStepUsersJsArray">
					<bean:message key="requisition_approval_feedback.label.move_next"/>
				</logic:notEmpty>
				&nbsp;	        
	         </td>
			<td>
				<logic:notEmpty name="requisitionFeedbackForm" property="nextStepUsersJsArray">
					<script language="JavaScript">
						var opts = <bean:write name="requisitionFeedbackForm" property="nextStepUsersJsArray" filter="false" />;										
						var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
						opts = opt.concat(opts);
						selectBoxUsers = new SelectBox(opts,'<bean:write property="nextUserId" name="requisitionFeedbackForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
						document.write(selectBoxUsers.getHtml());
						selectBoxUsers.setOnChangeHandler('changeNotifyUsers');
						selectBoxUsers.init();
					</script>
				</logic:notEmpty>
			</td>
	    </tr>
		<tr>
			<td>
			  <img 
				  <logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>" property="feedbackDecision" name="requisitionFeedbackForm">
					  src="images/checkedradiobutton.gif" 
				  </logic:equal>
				  <logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>" property="feedbackDecision" name="requisitionFeedbackForm">
				    src="images/radiobutton.gif" 
				  </logic:notEqual>
	    		name="rdo"
				id='img_<%=RequisitionConstants.FEEDBACK_ACTION_HOLD %>'
				onclick="onChangeFeedbackDecision('rdo','<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>');">&nbsp;
				<bean:message key="requisition_approval_feedback.label.keep_on_hold"/>
				&nbsp;
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
			  <img 
				  <logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_REJECT%>" property="feedbackDecision" name="requisitionFeedbackForm">
					  src="images/checkedradiobutton.gif" 
				  </logic:equal>
				  <logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_REJECT%>" property="feedbackDecision" name="requisitionFeedbackForm">
				    src="images/radiobutton.gif" 
				  </logic:notEqual>
	    		name="rdo"
				id='img_<%=RequisitionConstants.FEEDBACK_ACTION_REJECT %>'
				onclick="onChangeFeedbackDecision('rdo','<%=RequisitionConstants.FEEDBACK_ACTION_REJECT%>');">&nbsp;
				<bean:message key="requisition_approval_feedback.label.reject"/>
				&nbsp;
			</td>
			<td></td>
		</tr>
	    </table>
	    </div>
	    <div class="popupBody">
		    <table class="tblPop">
			<tr>
		         <td class="header"><bean:message key="requisition_approval_feedback.label.comment"/></td>
		    </tr>
			<tr>
		         <td><html:textarea property="feedbackComment" rows="4" cols="65" ></html:textarea></td>
		    </tr>
		    </table>
		     <table class="tblPop">
				<tr>
			         <td class="header">
						<bean:message key="requisition_approval_feedback.label.notify"/>
					</td>
				</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0" border="0" style="margin-top:2px;">
				<tr>
					<td width="1px">&nbsp;</td>
					<td width="250px"><div id="GRD1" height="110px" width="250px"></div></td>
					<td width="34px" align="center">
						<br/><br/>
						<a href="#" onclick="javascript: mySelectItem(dataGrid,dataGrid2);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a> 
						<br/>
						<a href="#" onclick="javascript: myDeselectItem(dataGrid2,dataGrid);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" /></a> 
					</td>
					<td ><div id="GRD2" height="110px" width="250px"></div></td>								
				</tr>
				<tr><td colspan="4" height="5px"></td></tr>
			</table>
	    </div>
	 	<div class="popupBody">
			<table class="tblPop" width="100%">
			<tr>
				<td>
				<div class="navBtn" style="float: right;">
					<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
				<div class="navBtn" style="float: right;display: block;" id="submitBtn">
					<a href="#" style="width:60px;" class="active" onclick="javascript: submitFeedback();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
				</div>
				
				</td>
			</tr>
			</table>
		</div>    	
	</div>
</html:form>	
</div>

<script language="JavaScript">
function onChangeFeedbackDecision(imgGroupName, attachmentId){
	var prevId = document.requisitionFeedbackForm.feedbackDecision.value;
	var nextId = onRadioChange(imgGroupName, attachmentId,prevId); 
	if(nextId!=-1){
		document.requisitionFeedbackForm.feedbackDecision.value=nextId;
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
	if(document.requisitionFeedbackForm.feedbackDecision.value=='<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>'){
		if(selectBoxUsers!=null){
			if (selectBoxUsers.getSelectedId() == '-1') {
				alert('<bean:message key="requisition_approval_feedback.error.select_user"/>');
				return false;
			}else{
				document.requisitionFeedbackForm.nextUserId.value=selectBoxUsers.getSelectedId();
			}
		}
	}else{
		document.requisitionFeedbackForm.nextUserId.value='-1';
		document.requisitionFeedbackForm.toApprovalStepId.value='';
	}
	return true;
}


function submitFeedback() {
	if(validateAndSetFormVars()){
		document.requisitionFeedbackForm.notifyUserIds.value=dataGrid2.getAllItemIds(',');	
		document.requisitionFeedbackForm.submit();			
	}
}


function setPopupTitle(){
	var title = '<b><bean:message key="requisition_approval_feedback.label.requisition_approval"/>';
	window.top.setPopTitle(title);
}
window.onload = doOnLoad;
function doOnLoad() {
	<logic:present name="errors" scope="request">
		Element.hide('submitBtn');
	</logic:present>
	setPopupTitle();
	initGrids();	
}
var prevApprover = '';
//function changeNotifyUsers() {	
////selectItems(prevApprover,dataGrid2,dataGrid);	
//if(selectBoxUsers.getSelectedId() != -1) {		
//	selectItems(selectBoxUsers.getAllSelectOptionIds(),dataGrid,dataGrid2);		
//}
////selectItems(document.requisitionFeedbackForm.notifyUserIds.value,dataGrid,dataGrid2);	
////prevApprover = selectBoxUsers.getAllSelectOptionIds();
//}

function changeNotifyUsers() {	
	selectItems(prevApprover,dataGrid2,dataGrid);
	if(selectBoxUsers.getSelectedId() != -1) {
		selectItems(selectBoxUsers.getSelectedId(),dataGrid,dataGrid2);		
	}
	selectItems(document.requisitionFeedbackForm.notifyUserIds.value,dataGrid,dataGrid2);	
	prevApprover = selectBoxUsers.getSelectedId()
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
	dataGrid.init();  
	dataGrid.attachEvent("onXLE",doOnLoadingEnd);
	dataGrid.attachEvent("onKeyPress",onGrid1KeyPressed);
	dataGrid.attachEvent("onRowSelect",doOnDataGridRowSelectHandler);
	dataGrid.attachEvent("onRowDblClicked",doOnRowDblClicked);
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
	dataGrid2.setSortImgState(true,0,"ASC");
	dataGrid2.attachEvent("onRowSelect",doOnDataGrid2RowSelectHandler);
	dataGrid2.attachEvent("onRowDblClicked",doOnDataGrid2RowDblClicked);
	dataGrid2.attachEvent("onKeyPress",onGrid2KeyPressed);
	
	dataGrid2.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
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
	dataGrid.setSortImgState(true,0,"ASC");
	if(selectBoxUsers) {
		changeNotifyUsers();
	}
}

function mySelectItem(dataGrid,dataGrid2) {
	selectedId = dataGrid.getSelectedId();
	selectItem(dataGrid,dataGrid2);
	parts = document.requisitionFeedbackForm.notifyUserIds.value.split(',');
	isSelected = false;
	for(var i = 0; i < parts.length; i++) {
		if(parts[i] == selectedId) {
			isSelected = true;
		}
	}
	if(!isSelected) {
		parts[parts.length] = selectedId;
		document.requisitionFeedbackForm.notifyUserIds.value = parts.join(',');
	}
}

function myDeselectItem(dataGrid2,dataGrid) {
	deselectedId = dataGrid2.getSelectedId();
	deselectItem(dataGrid2,dataGrid);
	parts = document.requisitionFeedbackForm.notifyUserIds.value.split(',');
	isSelected = false;
	for(var i = 0; i < parts.length; i++) {
		if(parts[i] == deselectedId) {
			parts.splice(i, 1);			
		}
	}
	document.requisitionFeedbackForm.notifyUserIds.value = parts.join(',');
}

function viewPositionDetails(positionId){
	window.parent.location.href=uncache("position.do?mode=description&positionId="+positionId);	
}
</script>