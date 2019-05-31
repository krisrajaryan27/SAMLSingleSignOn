<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
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
	    <div class="popupBody">
		    <table class="tblPop">
			<tr>
		         <td>Please open the <bean:message key="common.position"/>&nbsp;<bean:message key="requisition_approval_feedback.label.please_open_position"/></td>
		    </tr>
		    </table>
	    </div>
		<div class="popupBody">
			<table class="tblPop" width="100%">
			<tr>
				<td>
				<div class="navBtn" style="float: left;">
				<a href="#" style="width:60px; " class="active" onclick="javascript: window.top.hidePopWin(true);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
				</div>
				</td>
			</tr>
			</table>
		</div>    	
	</div>
</html:form>	
</div>

<script language="JavaScript">
function setPopupTitle(){
	var title = '<b><bean:message key="requisition_approval_feedback.label.requisition_approval"/>';
	window.top.setPopTitle(title);
}
window.onload = setPopupTitle;
</script>