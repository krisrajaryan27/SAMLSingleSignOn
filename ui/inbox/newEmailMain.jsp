<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.inbox.InboxConstants,com.talentPool.applicant.dataobject.ApplicantData, 
                  com.talentPool.common.NavigationConstants,com.talentPool.inbox.form.InboxForm,
                  com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                  com.talentPool.common.utils.CommonUtils,com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<script language="javascript" type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/AttachmentClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script language="JavaScript" src="js/submodal/common.js" type="text/javascript"></script>
<script language="JavaScript" src="js/submodal/subModal.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css">
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
<logic:present name="update" scope="request">
window.top.hidePopWin(true);
</logic:present>                

tinyMCE.init({
	mode : "textareas",
	elements : "newEmailBody",
	theme : "advanced",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_buttons1 : "formatselect,fontselect,fontsizeselect,newdocument,bold,italic,underline,forecolor,backcolor,bullist,numlist,separator,undo,redo,cut,copy,paste,justifyleft,justifyright",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	force_br_newlines: true,
	theme_advanced_disable : "anchor",
	theme_advanced_path : false
});

var cboTemplate=null;
</script>

<script language="javascript">
//Populate the attachments array with attachment data objects
var attachments = new Array();
<logic:notEmpty name="messageData">
<logic:iterate id="attachment" name="messageData" property="attachments">
var attachmentId = '<bean:write name="attachment" property="attachmentId"/>';
var originalFileName='<bean:write name="attachment" property="originalFileName"/>';
var attachmentSize=<bean:write name="attachment" property="attachmentSize"/>;
var labeledAttachmentSize='<bean:write name="attachment" property="labeledSize"/>';
var att = new Attachment(attachmentId, originalFileName, attachmentSize, labeledAttachmentSize);
attachments[attachments.length]=att;
</logic:iterate>
</logic:notEmpty>
//This method will rmove the element from attachments list with given id and refresh the list of
//attachments displayed, also in the background it sends request using Ajax to delete attachment from DB
function removeAttachment(attachmentId){
   	var m_count = attachments.length;
	var index=0;
	for(I=0;I<m_count;I++){
		var attachment=attachments[I];
		if(attachment.attachmentId==attachmentId){
			index=I;
			break;
		}
	}
   	if ( m_count > 0 && index > -1 && index < m_count ) 
   	{
      	switch( index )
      	{
        	case m_count - 1:
            	attachments.pop();
            	break;
         	default:
            	var head   = attachments.slice( 0, index );
	            var tail   = attachments.slice( index + 1 );
	            attachments = head.concat( tail );
	            break;
    	}
   	}
	displayAttachments();
	
	var pars = "mode=deleteAttachment&attachmentId="+attachmentId+"&tmpEmailId=<bean:write name="inboxForm" property="tmpEmailId"/>";
	var myAjax = ajaxCall("inbox.do","get",pars,doNothing,reportError);
}

function viewAttachment(attachmentId) {
	var url = "inbox.do?mode=viewAttachment&tmpAttachmentId="+attachmentId;
	window.open(url);
}

//Clears the attachment list displayed on the screen and display it
function displayAttachments(){
	var divAtt = document.getElementById('DivAttachments');
	divAtt.innerHTML='';
	var strInner='<table cellspacing=2 cellpadding=1>';
	for(I=0;I<attachments.length;I++){
		var attachment=attachments[I];
		strInner +=  '<tr><td width=280px>';
		strInner +=  '<a href=\"#\" onclick=\"viewAttachment('+ attachment.attachmentId + ')\">'+attachment.originalFileName + '</a>&nbsp;' ;
		if(attachment.labeledAttachmentSize!=null && attachment.labeledAttachmentSize!=0){
		strInner +=  '('+attachment.labeledAttachmentSize+')&nbsp;';
		}
		strInner +=  '<a href=\"#\" onclick=\"removeAttachment('+ attachment.attachmentId + ')\">remove</a>&nbsp;&nbsp;&nbsp;';
		strInner +=  '</td></tr>'
	}
	strInner+='</table>';
	divAtt.innerHTML=strInner;
}
//called when child attachment screen is done
function setAttachment(addedAttachments){
	//attachments = at;
	for(var i=0;i<addedAttachments.length;i++){
		attachments[attachments.length]=addedAttachments[i];	
	}
	
	//attachments = new Array();
	var selIds='';
	for(I=0;I<attachments.length;I++){
		var attachment=attachments[I];
		//attachments[attachments.length]=new Attachment(attachment.attachmentId,attachment.originalFileName, attachment.attachmentSize, attachment.labeledAttachmentSize);
		selIds += attachment.attachmentId + ','; 
	}
	if(selIds!=''){
		selIds = selIds.substr(0,selIds.length-1);
		
		var pars = "mode=confirmAttachments&attachmentId="+selIds+"&tmpEmailId=<bean:write name="inboxForm" property="tmpEmailId"/>";
		var myAjax = ajaxCall("inbox.do","get",pars,doNothing,reportError);
	}
	displayAttachments();
}
function doNothing(request){
     xmlFile = request.responseXML;
     if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) return;
}

//called when form is submitted
function onSubmitForm(frm){
	if(attachments!=null){
		for(I=0;I<attachments.length;I++){
			var attachment=attachments[I];
			var elm = document.createElement("input");
			elm.type="hidden";
			elm.name="attachmentId";
			elm.value=attachment.attachmentId;
			frm.appendChild(elm);
		}
		
	}
	submitForm();
	//frm.submit();
}
var isFormSubmitted = 0;
function submitForm() {	
	showUpdater('sendDiv',{setHeight: false, setWidth: false, offsetLeft: 0});
	try{
		if (isFormSubmitted == 0) {		
			document.inboxForm.submit();
			isFormSubmitted = 1;
		}
	}catch(e){
		hideUpdater('sendDiv');
	}		
}

function toggleIncludeOriginal() {
  elem = document.getElementById("chkBox");
  if (document.inboxForm.includeOriginal.value == "true") {
    document.inboxForm.includeOriginal.value = "false";
    elem.innerHTML = '<img src="images/checkboxunchecked.gif" />';
  } else {
    document.inboxForm.includeOriginal.value = "true";
    elem.innerHTML = '<img src="images/checkboxchecked.gif" />';
  }
}
</script>
<%
	InboxForm inboxForm = (InboxForm)request.getAttribute("inboxForm");
%>
<div class="contentDivPop"  style="margin-top: 13px;">
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
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td>
				<div class="navBtnTab" style="width:150px;float: left;" >
					<div id="sendDiv">
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
					<a href="#" style="width:55px;" onclick="javascript: submitForm(document.inboxForm);" ><span class="rightC"></span><span class="leftC"></span><bean:message key="new_email.label.send"/></a> 
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
					<a href="#" style="width:60px;" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span>Discard</a>
					</div>
				</div>
			</td>
			<logic:equal name="inboxForm" property="newEmailType" value="<%=InboxConstants.EMAIL_TYPE_FORWARD_RESUME %>">
				<td align="right">
					<div class="navBtnTab" style="width:150px;float: right;">
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
						
							<a href="#" style="width:115px;" onclick="attachResume();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.attach"/> <bean:message key="common.resumes"/></a>
					</div>
				</td>
			</logic:equal>
		</tr>
	</table>
<div id="autocomplete" class="autocomplete" ></div>
<div class="outerDiv">
<html:form action="/inbox" onsubmit="return onSubmitForm(this);">
<html:hidden property="mode" value="sendEmail"/>
<html:hidden property="applicantId"/>
<html:hidden property="emailId"/>
<html:hidden property="newEmailType"/>
<html:hidden property="emailLocation"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="it"/>
<html:hidden property="tmpEmailId"/>
<html:hidden property="templateCode"/>
<html:hidden property="positionId"/>
<html:hidden property="folderId"/>
<html:hidden property="from"/>
	<div class="popupTop">
		<table class="tblPop" width="100%">
		  <tr>
			<td>
			</td>
			<td class="header" style="font-weight: normal;">   					  
          	  <img src="images/checkboxchecked.gif" onclick="changeFromEmail(this);" id="chkFromEmail"/>
          	  <bean:message key="new_email.label.send_email_from"/> [<bean:write property="from" name="inboxForm"/>]
			</td>
		  </tr>
		  <tr>
			<td class="header" <html:errormap property="new_email.error.invalid_to" errorStyleClass="labelError" styleClass="label"/> style="text-align:left;">
			  <bean:message key="view_email.label.to"/>
			</td>
			<td>   					  
          	  <html:text property="to" name="inboxForm" size="130" styleId="to" styleClass="whiteInput"></html:text>                                					  
          	  <script>
				new Ajax.Autocompleter("to", "autocomplete", "inbox.do?mode=getUsersAutoCompleteList&messageReceivedType=<%=InboxConstants.MAIL_RECEIVED_AS_TO%>", {frequency: 0.001, tokens: [',',';']});
			  </script>
			</td>
		  </tr>
		  <tr>
			<td class="header"><bean:message key="view_email.label.cc"/></td>
			<td>
          	  <html:text property="cc" name="inboxForm" size="130" styleId="cc" styleClass="whiteInput"></html:text> 
          	  <script>
				new Ajax.Autocompleter("cc", "autocomplete", "inbox.do?mode=getUsersAutoCompleteList&messageReceivedType=<%=InboxConstants.MAIL_RECEIVED_AS_CC%>", {frequency: 0.001, tokens: [',',';']});
			  </script>                             
			</td>
		  </tr>
		  <%if(GlobalApplicationProperties.isEnabled(GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL)){%>
		  <tr>
			<td class="header"><bean:message key="view_email.label.bcc"/></td>
			<td>
          	  <html:text property="bcc" name="inboxForm" size="130" styleId="bcc" styleClass="whiteInput"></html:text> 
          	  <script>
				new Ajax.Autocompleter("bcc", "autocomplete", "inbox.do?mode=getUsersAutoCompleteList&messageReceivedType=<%=InboxConstants.MAIL_RECEIVED_AS_BCC%>", {frequency: 0.001, tokens: [',',';']});
			  </script>                             
			</td>
		  </tr>
		  <%} %>
		  <tr>
			<td class="header"><bean:message key="view_email.label.subject"/></td>
			<td>
          	  <html:text property="subject" name="inboxForm" size="130" styleClass="whiteInput"></html:text>                              
          </td>
		  </tr>
		  <tr>
			<td class="header"><bean:message key="view_email.label.template"/></td>
			<td>
          	  <script type="text/javascript">
					var opts = <bean:write name="templateJSArray" scope="request" filter="false"/>;
					var m = [new SelectOption('0','<bean:message key="common.selectlist.default"/>')];
					opts = m.concat(opts);
					cboTemplate = new SelectBox(opts,'<bean:write name="inboxForm" property="templateCode"/>','images/btn_dropdown.gif',{namesonly:false, width:'280px', size:4});
					cboTemplate.setOnChangeHandler('onTemplateChange');
					document.write(cboTemplate.getHtml());
					cboTemplate.init();
			  </script>
			</td>
		  </tr>
	    </table>
		  <table cellpadding="0" cellspacing="0" width="100%" class="tblPop">
			<tr>    						  
			  <td class="header"valign="top" style="padding-top:3px;"><bean:message key="view_email.label.attachments"/></td>
			  <td width="295px">
			  	<table>
			  		<tr>
			  			<td>
				  			<a href="#" style="width:95px;" onclick="addAttachment();">(Add)</a>
						</td>
			  		</tr>
			  		<tr>
			  			<td>
			  				<DIV id="DivAttachments"></DIV>
							<script language="javascript">
								displayAttachments();
							</script>
						</td>
			  		</tr>
			  	</table>
			  </td>
			 <logic:notEmpty name="inboxForm" property="positionIds">
				 <td class="header" valign="top" style="padding-top:3px;">
		             Insert <bean:message key="common.position"/> Info for&nbsp;
		         </td>
		          <td align="right" valign="top">
		            <script type="text/javascript">
							var opts = <%=CommonUtils.getListJavaScriptArray(inboxForm.getPositionIds(),inboxForm.getPositionNames())%>;
							var m = [new SelectOption('0','<bean:message key="common.selectlist.default"/>')];
							opts = m.concat(opts);
							selectBox = new SelectBox(opts,'<%=inboxForm.getPositionId()%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
							selectBox.setOnChangeHandler('fetchPositionInfo');
							document.write(selectBox.getHtml());
							selectBox.init();
					</script>								
		          </td>		
	          	</logic:notEmpty>	  
	           <logic:empty name="inboxForm" property="positionIds">
	          	<td width="350px;">&nbsp;</td>     
	          	</logic:empty> 
			</tr>
		  </table>
	</div>
	<div><html:textarea property="newEmailBody" name="inboxForm" styleClass="inputBox" style="width:100%;height:245px;"></html:textarea></div>
</html:form>
</div>
</div>
<br></br>
<% 
String mailType = null;
if (InboxConstants.EMAIL_TYPE_REPLY.equalsIgnoreCase(inboxForm.getNewEmailType())) {
  mailType = "Reply Mail";
} else if(InboxConstants.EMAIL_TYPE_FORWARD.equalsIgnoreCase(inboxForm.getNewEmailType())) {
  mailType = "Forward Mail";
} else {
  mailType = "New Mail";
}
%>

<script>
frm = document.inboxForm;
var fromAdd = '<bean:write property="from" name="inboxForm"/>';

function addAttachment() {
	var url="inbox.do?mode=addAttachments&tmpEmailId=<bean:write name="inboxForm" property="tmpEmailId"/>&emailId=<bean:write name="inboxForm" property="emailId"/>&emailLocation=<bean:write property="emailLocation"  name="inboxForm"/>";
	showInPopUp(url,500,160,doNothing,true);
 // var frm1 = window.frames["frame1"];
 // frm1.document.getElementById('attachedFile').click();
}

function attachResume() {
	var url="inbox.do?mode=attachResumes&applicantId=<bean:write property="applicantId"  name="inboxForm"/>&tmpEmailId=<bean:write name="inboxForm" property="tmpEmailId"/>&emailId=<bean:write name="inboxForm" property="emailId"/>&emailLocation=<bean:write property="emailLocation"  name="inboxForm"/>";
	showInPopUp(url,300,100,doNothing,true);
}

function changeFromEmail(chkBox){
	if(frm.from.value==''){
		chkBox.src='images/checkboxchecked.gif';
		frm.from.value=fromAdd;
	}else{
		chkBox.src='images/checkboxunchecked.gif';
		frm.from.value='';
	}
}

function fetchPositionInfo(){
    frm.positionId.value = selectBox.getSelectedId();
	if(frm.positionId.value!=0){
		var pars = "mode=getPositionInfo&positionId=" + frm.positionId.value;
		var myAjax = ajaxCall("inbox.do","get",pars,appendPositionInfo,reportError);
	}
	//	document.getElementById('newEmailBody').focus();
}
function appendPositionInfo(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) return;
   
	var strInfo="";
	var info = xmlFile.getElementsByTagName("info")[0];

	var requirements = info.getElementsByTagName("requirements")[0];
	if(requirements!=null){
		strInfo += '<br/>REQUIREMENTS<br/>';
		strInfo += requirements.firstChild.nodeValue + '<br/>';
	}
	
	var responsibilities = info.getElementsByTagName("responsibilities")[0];
	if(responsibilities!=null){
		strInfo += '<br/>RESPONSIBILITIES<br/>';
		strInfo += responsibilities.firstChild.nodeValue + '<br/>';
	}
	tinyMCE.activeEditor.setContent(tinyMCE.activeEditor.getContent() +"<br>"+ strInfo );
}

function setPopupTitle(){
	var title = '<b><bean:message key="view_email.label.email"/></b>';
	<logic:notEmpty name="applicantData">
	title += ' - <bean:write name="applicantData" property="applicantName"/> &nbsp';
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		title += '<bean:message key="common.openingRoundBracket"/><bean:message key="common.source"/>:&nbsp;<bean:write name="applicantData" property="applicantSourceTitle"/><bean:message key="common.closingRoundBracket"/>';
	<%} %>
	</logic:notEmpty>
	window.top.setPopTitle(title);
}

var frm = document.inboxForm;
function onTemplateChange(){
    frm.templateCode.value = cboTemplate.getSelectedId();
	if(frm.templateCode.value!=0){
		var pars = "mode=getTemplateXML&templateCode=" + frm.templateCode.value + "&applicantId=" + frm.applicantId.value;		
		var myAjax = ajaxCall("inbox.do","get",pars,appendTemplateContent,reportError);
	}else{
		frm.subject.value="";
		tinyMCE.activeEditor.setContent("");
		frm.templateCode.value ="";
	}
}

function appendTemplateContent(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) return;
	
	var template = xmlFile.getElementsByTagName("template")[0];
	
	var subject = unescapeHTML(getSingleElement(template,"subject","")); /*.escapeHTML();*/
	var content = getSingleElement(template,"content","");//.escapeHTML();
	content = content.unescapeHTML();
	frm.subject.value=subject;
	tinyMCE.activeEditor.setContent(content);

	
}

function doNothing(){
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function onWindowLoad(){
	initPopUp();
	setPopupTitle();
}
window.onload=onWindowLoad;

</script>