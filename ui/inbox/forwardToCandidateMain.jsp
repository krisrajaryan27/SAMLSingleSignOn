<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>

<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">

<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js"></script>
<script>
//global variable for select box of applicant search
var cboSelectCandidate=null;
var defaultOption = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
</script>
<div class="contentDivPop" >
	<div class="outerDiv" style="width:480px;">
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="inbox_label.search_candidate"/>:
			</td>
			<td>
				<input type="text" name="txtSearchCandidate" value="" id="txtSearchCandidate" size="35"/>
			</td>
			<td>
				<div class="navBtn" style="float: right;"><a href="#" style="width:30px;" class="active" onclick="javascript:requestApplicantSearch();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.go"/></a></div>
			</td>
		</tr>
		<tr>
			<td class="header right">
				<bean:message key="inbox_label.select_candidate"/>:
			</td>
			<td >
			<script language="JavaScript">
				var defaultOption = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
				cboSelectCandidate= new SelectBox(defaultOption,'-1','images/btn_dropdown.gif',{namesonly:false, width:'185px', size:15});
				document.write(cboSelectCandidate.getHtml());
				cboSelectCandidate.init();
			</script>	
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop">
		<tr>
			<td colspan="2">
				<img src="images/checkboxunchecked.gif" onclick="checkUpdateResume(this);" id="chkUpdateResume"/>&nbsp;&nbsp;&nbsp;<b><bean:message key="inbox_label.replace_original"/></b>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="DIV_UR" style="display:none;">
					<table cellspacing="0" cellpadding="0" class="tblPop">
						<tr><td height="5"></td></tr>
						<tr>
							<td >
								<img src="images/checkedradiobutton.gif" name="rdo" id="img_0" onclick="onRadioChange('rdo',0)"/>&nbsp;&nbsp;&nbsp;<bean:message key="inbox.import.content1"/>
							</td>
						</tr>
						<tr><td height="5"></td></tr>
						<tr>
							<td class="header">
								<b><bean:message key="inbox.import.content2"/></b>
							</td>
						</tr>
						<tr><td height="10"></td></tr>
						<tr>
							<td>
							<logic:present name="messageData" scope="request">
								<logic:iterate id="att" name="messageData" property="attachments" scope="request">
									<img  src="images/radiobutton.gif" name="rdo" id='img_<bean:write name="att" property="attachmentId"/>'
									onclick="onRadioChange('rdo','<bean:write name="att" property="attachmentId"/>');" >&nbsp;&nbsp;&nbsp;<bean:write name="att" property="originalFileName"/><br/><br class="br5"/>
								</logic:iterate>
							</logic:present>
							</td>
						</tr>
						<tr><td ></td></tr>
					</table>
				</div>				
			</td>
		</tr>
	</table>
	<br/>
	<table cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:65px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:65px;margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(true);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>	
	</div>
</div>


<script>
function requestApplicantSearch(){
	var pars = "mode=searchApplicantToForward&searchApplicantName=" +$('txtSearchCandidate').value;
	var myAjax = ajaxCall("inbox.do","get",pars,populateSearchResult,reportError);
	
}
function populateSearchResult(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	var applicantsNode = xmlFile.getElementsByTagName("applicants")[0];	
	var applicants = applicantsNode.getElementsByTagName("applicant");
	var selectOptions = new Array();
	for(var i=0; i<applicants.length; i++){
		var applicantId = getSingleElement(applicants[i],"applicantId","");
		var applicantName = getSingleElement(applicants[i],"applicantName","");
		selectOptions[selectOptions.length]= new SelectOption(applicantId,applicantName);
	}
	selectOptions = defaultOption.concat(selectOptions);
	cboSelectCandidate.reInitialize(selectOptions,'-1');
	cboSelectCandidate.dropDown();
}

var updateResume='0';
var selAttachment=0;
function checkUpdateResume(chkBox){
	if(updateResume=='0'){
		updateResume='1';
		chkBox.src='images/checkboxchecked.gif';
		$("DIV_UR").style.display="block";
	}else{
		updateResume='0';
		chkBox.src='images/checkboxunchecked.gif';
		$("DIV_UR").style.display="none";
	}
}
function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+attachmentId){
					selAttachment = attachmentId;
					theImage.src = "images/checkedradiobutton.gif";
				}else{
					theImage.src = "images/radiobutton.gif";
				}
			}
	}
}
function submitForm(){
	var applicantId=cboSelectCandidate.getSelectedId();

	if(applicantId==-1){
		alert('<bean:message key="inbox.error.select_candidate_to_forward_interactions"/>');
		return;
	}
	var pars = "mode=forwardToCandidateRecord&emailId=<bean:write name="messageData" property="messageId" scope="request"/>&applicantId="+applicantId+"&updateResume="+updateResume+"&selAttachment="+selAttachment;
	var myAjax = ajaxCall("inbox.do","get",pars,onCompleteForward,reportError);

}

function onCompleteForward(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('Unable to forward to candidate record');
		return;
	}
	var emailIds=getIds(xmlFile);
	window.top.onImportFinish(emailIds);
	window.top.hidePopWin(true);
}
function setPopupTitle(){
	window.top.setPopTitle('<b><bean:message key="inbox.button.label.attach"/></b>');
}
window.onload = setPopupTitle;
</script>