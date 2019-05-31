<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.MessageConstants"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css">
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
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
  	<html:hidden property="applicantId" name="selectionProcessForm"/>
  	<div id="autocomplete" class="autocomplete" ></div>
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="applicant_listing.label.send_to"/>
			</td>
			<td>
				<html:text property="userNameTo" name="selectionProcessForm" size="95"  styleId="userNameTo" styleClass="Grey" />
				<script>
					new Ajax.Autocompleter("userNameTo", "autocomplete", "selectionProcess.do?mode=getUsersAutoCompleteList&messageReceivedType=<%=MessageConstants.MESSAGE_RECEIVED_AS_TO%>", {frequency: 0.001, tokens: [',',';']});
				</script>
			
			</td>
		</tr>
		<tr>
			<td class="header">
			<bean:message key="applicant_listing.label.send_cc"/>
			</td>
			<td>
				<html:text property="userNamesCc" name="selectionProcessForm" size="95"  styleId="userNamesCc" styleClass="Grey" />
				<script>
					new Ajax.Autocompleter("userNamesCc", "autocomplete", "selectionProcess.do?mode=getUsersAutoCompleteList&messageReceivedType=<%=MessageConstants.MESSAGE_RECEIVED_AS_CC%>", {frequency: 0.001, tokens: [',',';']});
				</script>
			</td>
		</tr>
		<tr>
			<td class="header">
			</td>
			<td class="Grey">
				<bean:message key="applicant_listing.content.info"/>
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop">
		<tr>
			<td class="header">
			<bean:message key="applicant_listing.label.message_text"/>	
			</td>
		</tr>
		<tr>
			<td>
				<html:textarea property="messageText"  name="selectionProcessForm" rows="10" cols="100"></html:textarea>
			</td>
		</tr>
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
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

function submitForm(){
	if(document.selectionProcessForm.messageText.value.trim()==""){
		alert('<bean:message key="applicant_listing.error.enter_message"/>');
		document.selectionProcessForm.messageText.focus();
		return false;
	}
	validateToAndCCUsers();
}

function validateToAndCCUsers(){
	if($("userNameTo").value!=""){
	 	var pars = "mode=validateNames&userNamesCc="+$("userNameTo").value;
	 	var myAjax = ajaxCall("selectionProcess.do","get",pars,checkValidToNames,reportError);
 	}else{
 		alert('<bean:message key="applicant_listing.error.enter_username"/>');
 		$("userNameTo").focus();
 	}
}

function checkValidToNames(request){
	var namesNotFound = checkValidNames(request);
	if(namesNotFound!=""){
		alert(namesNotFound + ' <bean:message key="applicant_listing.error.does_not_exist"/>');
		$('userNameTo').focus();
	}else{
		validateCCUsers();
	}
}

function validateCCUsers(){
	if($("userNamesCc").value!=""){
	 	var pars = "mode=validateNames&userNamesCc="+$("userNamesCc").value;
	 	var myAjax = ajaxCall("selectionProcess.do","get",pars,checkValidCCNames,reportError);
 	}else {
		sendMessage();
	}
}

function checkValidCCNames(request){
	var namesNotFound = checkValidNames(request);
	if(namesNotFound!=""){
		alert(namesNotFound + ' <bean:message key="applicant_listing.error.does_not_exist"/>');
		$('userNamesCc').focus();
	}else{
		sendMessage();
	}
}
function sendMessage(){
	document.selectionProcessForm.submit();	
}

function checkValidNames(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		return;
	}
	//display names not avilable
	var names = xmlFile.getElementsByTagName("names")[0];	
	var namesNotFound = getSingleElement(names,"name","").escapeHTML();
	return namesNotFound;
	
}

String.prototype.trim = function() {
	 // skip leading and trailing whitespace
	 // and return everything in between
	  var x=this;
	  x=x.replace(/^\s*(.*)/, "$1");
	  x=x.replace(/(.*?)\s*$/, "$1");
	  return x;
}

function onWinLoad(){
	var title = '<b><bean:message key="applicant_listing.label.message_text"/> - </b><bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	window.top.setPopTitle(title);
}

//Event.observe("userNamesCc", "blur", this.onCCBlur.bindAsEventListener(this));
//Event.observe("userNameTo", "blur", this.onToBlur.bindAsEventListener(this));

window.onload = onWinLoad;
</script>