<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>							
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													

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
<div class="contentDivPop">
<html:form action="/requisitionfeedback">
<html:hidden property="mode" name="requisitionFeedbackForm" value="editfeedback"/>
<html:hidden property="positionId" name="requisitionFeedbackForm"/>	
<html:hidden property="feedbackId" name="requisitionFeedbackForm"/>

	<div class="outerDiv">
		<div class="popupTop">
		<table class="tblPop" width="570">
		<tr>
	         <td class="header" width="95"><bean:message key="common.position"/>&nbsp;<bean:message key="common.colon"/>&nbsp;</td>
	         <td><bean:write name="requisitionFeedbackData" property="positionTitle"/></td>
	         <td width="5">&nbsp;</td>
	         <td class="header" width="30"><bean:message key="common.date"/>:&nbsp;</td>
	         <td width="150"><bean:write name="requisitionFeedbackData" property="feedbackDateToDisplay" /></td>
	    </tr>
	    </table>
		<table class="tblPop" width="570">
	    <tr>
	         <td class="header" width="95"><bean:message key="requisition_approval_feedback.label.approval_step"/>&nbsp;</td>
	         <td><bean:write name="requisitionFeedbackData" property="fromStepName"/></td>
	    </tr>
	    <tr>
	         <td class="header"><bean:message key="requisition_approval_feedback.label.feedback_by"/>&nbsp;</td>
	         <td><bean:write name="requisitionFeedbackData" property="fromUserName"/></td>
	    </tr>
	    <tr>
	         <td class="header"><bean:message key="requisition_approval_feedback.label.action_taken"/>&nbsp;</td>
	         <td>
	         <logic:equal name="requisitionFeedbackData" property="feedbackDecision" value="<%=RequisitionConstants.FEEDBACK_ACTION_APPROVE %>">
	         	<logic:empty name="requisitionFeedbackData" property="toStepId">
	         		<bean:message key="requisition_approval_feedback.label.is_approved"/>
	         	</logic:empty>
	         	<logic:notEmpty name="requisitionFeedbackData" property="toStepId">
	         		<bean:message key="requisition_approval_feedback.label.is_forworded"/><bean:write name="requisitionFeedbackData" property="toUserName"/>&nbsp;(<bean:write name="requisitionFeedbackData" property="toStepName"/>)
	         	</logic:notEmpty>
	         </logic:equal>
	         <logic:equal name="requisitionFeedbackData" property="feedbackDecision" value="<%=RequisitionConstants.FEEDBACK_ACTION_HOLD%>">
	         		<bean:message key="requisition_approval_feedback.label.is_on_hold"/>
	         </logic:equal>
	         <logic:equal name="requisitionFeedbackData" property="feedbackDecision" value="<%=RequisitionConstants.FEEDBACK_ACTION_REJECT%>">
	         		<bean:message key="requisition_approval_feedback.label.is_rejected"/>
	         </logic:equal>
	         
	         </td>
	    </tr>
	    </table>
	    </div>
	    <div class="popupBody">
		    <table class="tblPop">
			<tr>
		         <td class="header"><bean:message key="requisition_approval_feedback.label.comment"/></td>
		    </tr>
			<tr>
		         <td>
		         <textarea rows="5" cols="65" readonly="readonly"><bean:write name="requisitionFeedbackData" property="feedbackComment"/></textarea>
		         </td>
		    </tr>
		    </table>
	    </div>
		<div class="popupBody">
			<table class="tblPop" width="100%">
			<tr>
				<td>
				<div class="navBtn" style="float: right;">
				<logic:equal value="true" name="editAllowed" scope="request">
				<a href="#" style="width:60px;" class="active" onclick="javascript: editFeedback();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
				</logic:equal>
				<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
				</td>
			</tr>
			</table>
		</div>    	
	</div>
</html:form>	
</div>

<script language="JavaScript">

function editFeedback() {
		document.requisitionFeedbackForm.submit();
}


function setPopupTitle(){
	var title = '<b><bean:message key="requisition_approval_feedback.label.requisition_approval"/>';
	window.top.setPopTitle(title);
}
window.onload = setPopupTitle;
</script>