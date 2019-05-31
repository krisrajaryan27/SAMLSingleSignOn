<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>


<%@page import="com.talentPool.budget.BudgetConstants"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
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
			
<div class="contentDivPop" style="padding-bottom:23px;">
<html:form action="/budgetApproval">
<html:hidden property="mode" name="budgetForm" value="savefeedback"/>
<html:hidden property="budgetItemId" name="budgetForm"/>
<html:hidden property="status" name="budgetForm"/>
	
	<div class="outerDiv">
		<div class="popupTop">
		<table class="tblPop" width="570">
	    <tr>
	         <td class="header" width="90"><bean:message key="common.budget_item"/>:</td>
	         <td><bean:write name="budgetForm" property="budgetItemName"/></td>
	    </tr>
	    <tr>
	         <td class="header"><bean:message key="common.stage"/></td>
	         <td><bean:message key="common.activation"/></td>
	    </tr>
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
				<img src="images/checkedradiobutton.gif" name="rdo" id='img_<%=BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE %>'
				onclick="onRadioChange('rdo','<%=BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE%>');">&nbsp;	
				<bean:message key="common.approve"/>&nbsp;      
	         </td>
	    </tr>
		<tr>
			<td>
			  <img src="images/radiobutton.gif" name="rdo" id='img_<%=BudgetConstants.BUDGET_ITEM_STATUS_DELETED %>'
				onclick="onRadioChange('rdo','<%=BudgetConstants.BUDGET_ITEM_STATUS_DELETED%>');">&nbsp;
				<bean:message key="common.reject"/>&nbsp;
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
		         <td><html:textarea property="feedbackComment" rows="5" cols="65" ></html:textarea></td>
		    </tr>
		    </table>
	    </div>
		<div class="popupBody">
			<table class="tblPop" width="100%">
			<tr>
				<td>
				<div class="navBtn" style="float: right;"><a href="#" style="width:60px;" class="active" onclick="javascript: submitFeedback();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
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
	document.budgetForm.status.value=attachmentId;
}


function submitFeedback() {
		document.budgetForm.submit();			
}

function setPopupTitle(){
	document.budgetForm.status.value='<%=BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE%>';
	var title = '<b><bean:message key="budget_approval_feedback.label.budget_approval"/>';
	window.top.setPopTitle(title);
}

function doOnLoad() {
	setPopupTitle();
}

window.onload = doOnLoad;

</script>