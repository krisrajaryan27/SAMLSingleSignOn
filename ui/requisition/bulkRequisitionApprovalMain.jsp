<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.budget.utils.BudgetUtils"%>
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
var positionIds = new Array();
var checkedRadio="images/checkedradiobutton.gif";
var uncheckedRadio="images/radiobutton.gif";
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
<html:hidden property="mode" name="requisitionFeedbackForm" value="saveBulkRequisitionfeedback"/>
<html:hidden property="positionId" name="requisitionFeedbackForm" />
	<div class="vpTop" style="">
		<table class="vpTopTab">
			<tr>
				<td style="width: 184px;height: 30px;" align="left">
					<bean:message key="common.position"/> / <bean:message key="common.step"/>
				</td>
				<td style="width: 160px;vertical-align: middle;">
					<img src="images/radiobutton.gif" name="master_radio" 
						id='<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE %>' onclick="onTopRadioChange(this)">&nbsp;
					<bean:message key="requisition_approval_feedback.label.activate"/> / Approve
				</td>
				<td style="width: 120px;vertical-align: middle;">
					<img src="images/radiobutton.gif" name="master_radio" 
						id='<%=RequisitionConstants.FEEDBACK_ACTION_HOLD %>' onclick="onTopRadioChange(this)">&nbsp;
					<bean:message key="requisition_approval_feedback.label.keep_on_hold"/>
				</td>
				<td style="width: 120px;vertical-align: middle;">
					<img src="images/radiobutton.gif" name="master_radio" 
						id='<%=RequisitionConstants.FEEDBACK_ACTION_REJECT %>' onclick="onTopRadioChange(this)">&nbsp;
					<bean:message key="requisition_approval_feedback.label.reject"/>
				</td>
				<td style="width: 150px;vertical-align: middle;">
					<input name="commentAllTextBox" id="commentAllTextBox" size="40" onkeyup="commentAll(this);"/>
				</td>
			</tr>
		</table>
	</div>
	<div class="outerDiv" style="">
	<div class="popupBody" style="overflow: auto;max-height: 300px;">
    	<logic:iterate id="positionRequisitionFeedbackForm" name="requisitionFeedbackFormList" 
    		 	type="com.talentPool.requisition.form.RequisitionFeedbackForm" scope="request" >
    		<bean:define id="positionId" name="positionRequisitionFeedbackForm" property="positionId"></bean:define>
    		<bean:define id="display" value="" ></bean:define>
			<logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>" property="feedbackDecision" name="positionRequisitionFeedbackForm">
				<bean:define id="display" value="none" ></bean:define> 
			</logic:notEqual>
    		<input type="hidden" name="feedbackDecision_<%=positionId%>" id="feedbackDecision_<%=positionId%>" 
    					value="<bean:write name="positionRequisitionFeedbackForm" property="feedbackDecision"/>" />
    		<input type="hidden" name="feedbackId_<%=positionId%>" id="feedbackId_<%=positionId%>" 
    					value="<bean:write name="positionRequisitionFeedbackForm" property="feedbackId"/>"/>
    		<input type="hidden" name="fromApprovalStepId_<%=positionId%>" id="fromApprovalStepId_<%=positionId%>" 
    					value="<bean:write name="positionRequisitionFeedbackForm" property="fromApprovalStepId"/>" />
    		<input type="hidden" name="toApprovalStepId_<%=positionId%>" id="toApprovalStepId_<%=positionId%>" 
    					value="<bean:write name="positionRequisitionFeedbackForm" property="toApprovalStepId"/>" />
    		<input type="hidden" name="nextUserId_<%=positionId%>" id="nextUserId_<%=positionId%>" />
    		<script>
    			positionIds[positionIds.length]='<bean:write name="positionId" scope="page" />';
    		</script>	
	    	<table class="tblPop" style="">
	    		<tr>
	    			<td style="width: 200px;vertical-align: top;" >
	    				<table cellpadding="0" cellspacing="0" style="margin-top: 0px;">
						    <tr>
						         <td><bean:write name="positionRequisitionFeedbackForm" property="positionTitle"/></td>
						    </tr>
						    <tr>
						    	<td><bean:write name="positionRequisitionFeedbackForm" property="fromStepTitle"/></td>
						    </tr>
	    				</table>
	    			</td>
    				<td style="width: 180px;vertical-align: top;">
						<img 
							<logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>" property="feedbackDecision" name="positionRequisitionFeedbackForm">
								  src="images/checkedradiobutton.gif" 
							</logic:equal>
							<logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>" property="feedbackDecision" name="positionRequisitionFeedbackForm">
							    src="images/radiobutton.gif" 
							</logic:notEqual>
				    		name="rdo_<%=positionId%>"
							id='rdo_<%=positionId%>_<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>'
							onclick="onFeedbackDecisionChange('<%=positionId%>','<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>',this);">&nbsp;
							<logic:empty name="positionRequisitionFeedbackForm" property="nextStepUsersJsArray">
								<bean:message key="requisition_approval_feedback.label.activate"/>
							</logic:empty>
							<logic:notEmpty name="positionRequisitionFeedbackForm" property="nextStepUsersJsArray">
								<bean:message key="requisition_approval_feedback.label.move_next"/>
							</logic:notEmpty>     
    				</td>
   					<td style="width: 136px;vertical-align: top;">
   						 <img 
						  <logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>" property="feedbackDecision" name="positionRequisitionFeedbackForm">
							  src="images/checkedradiobutton.gif" 
						  </logic:equal>
						  <logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>" property="feedbackDecision" name="positionRequisitionFeedbackForm">
						    src="images/radiobutton.gif" 
						  </logic:notEqual>
				    		name="rdo_<%=positionId%>"
							id='rdo_<%=positionId%>_<%=RequisitionConstants.FEEDBACK_ACTION_HOLD %>'
							onclick="onFeedbackDecisionChange('<%=positionId%>','<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>',this);">&nbsp;
							<bean:message key="requisition_approval_feedback.label.keep_on_hold"/>
							&nbsp;
					</td>
  					<td style="width: 136px;vertical-align: top;">
						<img 
						  <logic:equal value="<%=RequisitionConstants.FEEDBACK_ACTION_REJECT%>" property="feedbackDecision" name="positionRequisitionFeedbackForm">
							  src="images/checkedradiobutton.gif" 
						  </logic:equal>
						  <logic:notEqual value="<%=RequisitionConstants.FEEDBACK_ACTION_REJECT%>" property="feedbackDecision" name="positionRequisitionFeedbackForm">
						    src="images/radiobutton.gif" 
						  </logic:notEqual>
				    		name="rdo_<%=positionId%>"
							id='rdo_<%=positionId%>_<%=RequisitionConstants.FEEDBACK_ACTION_REJECT %>'
							onclick="onFeedbackDecisionChange('<%=positionId%>','<%=RequisitionConstants.FEEDBACK_ACTION_REJECT %>',this);">&nbsp;
							<bean:message key="requisition_approval_feedback.label.reject"/>
							&nbsp;
					</td>
    				<td style="width: 150px;vertical-align: top;">	
    					<table>
    						<tr>
    							<td>
    								<div id="usersDiv_<%=positionId%>" style="display: <%=display %>;">
										<script language="JavaScript">
											var selectBoxUsers_<%=positionId%> = null;
											<logic:notEmpty name="positionRequisitionFeedbackForm" property="nextStepUsersJsArray">
												var opts = <bean:write name="positionRequisitionFeedbackForm" property="nextStepUsersJsArray" filter="false" />;										
												selectBoxUsers_<%=positionId%> = new SelectBox(opts,'<bean:write property="nextUserId" name="positionRequisitionFeedbackForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
												document.write(selectBoxUsers_<%=positionId%>.getHtml());
												selectBoxUsers_<%=positionId%>.init();
											</logic:notEmpty>
										</script>
    								</div>
    								<logic:empty name="positionRequisitionFeedbackForm" property="nextStepUsersJsArray">
										<script type="text/javascript">
    										$('usersDiv_<%=positionId%>').hide();
    									</script>
									</logic:empty>
    							</td>
    						</tr>
    						<tr>
    							<td>
    								<input name="comment_<%=positionId %>" id="comment_<%=positionId %>" size="40" value="<bean:write name="positionRequisitionFeedbackForm" property="feedbackComment"/>" />
    							</td>
    						</tr>
    					</table>
    				</td>
	    		</tr>
		    </table>
    	</logic:iterate>
	</div>
</div>
<div>
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
</html:form>	
</div>
<script language="JavaScript">
function commentAll(obj){
	var len=positionIds.length;
	for(j=0;j<len;j++){
		var pId = positionIds[j];	
		if(document.getElementById('comment_'+pId)){
			document.getElementById('comment_'+pId).value = obj.value;
		}
	}
}

function onTopRadioChange(obj){
	var len=positionIds.length;
	var selId = obj.id;
	onRadioChange(obj);
	for(j=0;j<len;j++){
		var pId = positionIds[j];
		var name = "rdo_"+pId;
		var elmt = document.getElementById('rdo_'+pId+'_'+selId);
		if(elmt){
			onFeedbackDecisionChange(pId,selId,elmt)
		}
	}
}

function onRadioChange(obj){
	var selId = obj.id;
	var imgs = document.getElementsByName(obj.name);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id== selId) {
			theImage.src = checkedRadio;
		}else{
			theImage.src = uncheckedRadio;
		}
	}	
}

function onFeedbackDecisionChange(pId,selId,elmt){
	$('feedbackDecision_'+pId).value=selId;
	if(selId=='<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>'){
		$('usersDiv_'+pId).show();
	}else {
		$('usersDiv_'+pId).hide();
	}
	onRadioChange(elmt);
}

function submitFeedback() {
	if(validateAndSetFormVars()){
		document.requisitionFeedbackForm.positionId.value= positionIds.join(',');
		document.requisitionFeedbackForm.submit();			
	}
}

function validateAndSetFormVars(){
	var len=positionIds.length;
	for(j=0;j<len;j++){
		var pId = positionIds[j];	
		if($('feedbackDecision_'+pId).value=='<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE%>'){
			if(eval('selectBoxUsers_'+pId)){
				var selectBoxUsers = eval('selectBoxUsers_'+pId);
				if(selectBoxUsers!=null){
					if (selectBoxUsers.getSelectedId() == '-1') {
						alert('<bean:message key="requisition_approval_feedback.error.select_user"/>');
						return false;
					}else{
						$('nextUserId_'+pId).value=selectBoxUsers.getSelectedId();
					}
				}	
			}
		}else {
			$('nextUserId_'+pId).value='-1';
			$('toApprovalStepId_'+pId).value='';
		}
	}
	return true;
}
window.onload = doOnLoad;
function doOnLoad() {
	setPopupTitle();
}
function setPopupTitle(){
	var title = '<b><bean:message key="requisition_approval_feedback.label.requisition_approval"/>';
	window.top.setPopTitle(title);
}
</script>