<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.MessageConstants"%>

<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%><link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css">
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>

<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>
<div class="contentDivPop" >
	<div class="outerDiv">
	<html:form action="/selectionProcess">
  	<html:hidden property="mode" name="selectionProcessForm"/>
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td style="width:190px;vertical-align: top;">
				<img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.SCREEN_TYPE_FWD%>" name="action" onclick="onRadioChange('action',this)" style="margin-bottom: -1px;"/>&nbsp;
				<bean:message key="bulkfeedback.action.move_to_next_step"/>
			</td>
		</tr>
		<tr>
			<td style="width:190px;vertical-align: top;">
				<img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.SCREEN_TYPE_POS%>" name="action" onclick="onRadioChange('action',this)" style="margin-bottom: -1px;"/>&nbsp;
				<bean:message key="common.position_move"/>&nbsp;<bean:message key="common.position"/>
			</td>
		</tr>
		<tr>
			<td style="width:190px;vertical-align: top;">
				<img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.SCREEN_TYPE_CAT%>" name="action" onclick="onRadioChange('action',this)" style="margin-bottom: -1px;"/>&nbsp;
				<bean:message key="bulkfeedback.action.confirm_attended"/>
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop">
		<tr>
			<td>
			<div class="navBtn" style="float: left;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
</div>
<script language="JavaScript">

var checkedRadio="images/checkedradiobutton.gif";
var uncheckedRadio="images/radiobutton.gif";
var returnVal = '';

function onRadioChange(radioGroupName, elm){
	var selId = elm.id;
	var imgs = document.getElementsByName(radioGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id== selId) {
				theImage.src = checkedRadio;			
			}else{
				theImage.src = uncheckedRadio;
			}
	}
	returnVal=selId;
}

function submitForm(){
	if(returnVal==""){
			alert('<bean:message key="bulkfeedback.action.error.select_action"/>');
		return false;
	}
	window.top.hidePopWin(true);
	//document.selectionProcessForm.submit();	
}

function onWinLoad(){
	var title = '<b><bean:message key="bulkfeedback.action.title"/></b>';
	window.top.setPopTitle(title);
}

window.onload = onWinLoad;
</script>