<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.inbox.InboxConstants,com.talentPool.applicant.dataobject.ApplicantData,com.talentPool.common.NavigationConstants,com.talentPool.inbox.form.InboxForm,com.talentPool.common.utils.CommonUtils,com.talentPool.common.properties.TPApplicationProperties,com.talentPool.repository.RepositoryConstants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page	import="com.talentPool.selectionProcess.dataobject.SelectionProcessData"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page	import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<link rel="stylesheet" type="text/css"	href="themes/default/popupiframe.css">
<script src="js/scripta/lib/prototype.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js"	type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.config.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/box.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/yahoo-dom-event.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/tip_ajaxcall.js"></script>
<div class="contentDivPop" style="padding-right:20px;"><logic:present
	name="applicants" scope="request">
	<table width="100%" class="boxHeader" style="margin-top:5px;"
		cellspacing="0" cellpading="0">
		<tr>
			<td class="header" height="25"><img
				src="images/checkboxunchecked.gif" onclick="selectAll();"
				id="chkAll" style="padding-left:1px;">&nbsp;</td>
				<td>&nbsp;</td>
		</tr>
	</table>
	<div class="outerDiv"
		style="height:340px; overflow: auto; border-top: 0px;">
	<table cellpadding="0" cellspacing="0" class="searchResult"
		style="width: 710px;">
		<logic:iterate id="applicant" name="applicants" scope="request">
			<bean:define id="data" name="applicant" type="SelectionProcessData"></bean:define>
			<tr>
				<td valign="top" style="padding-right:5px;"><img
					src="images/checkboxunchecked.gif" name="indSelect"
					id="ind_<bean:write name="data" property="applicantId"/>"
					onclick="toggleMe(<bean:write name="data" property="applicantId"/>)">
				</td>
				<td valign="top">
				<%
						String name = (data.getApplicantName() == null) ? "" : data.getApplicantName();
						String experience = (data.getApplicantExperience() == null) ? "" : data.getApplicantExperience();
						String currentEmployer = (data.getApplicantCurrentEmployer() == null) ? "" : data.getApplicantCurrentEmployer();
						String department = (data.getApplicantPositionDepartment() == null) ? "" : data.getApplicantPositionDepartment();
						String position = (data.getApplicantPosition() == null) ? "" : data.getApplicantPosition();
						String stage = (data.getApplicantStep() == null) ? "" : data.getApplicantStep();
						String status = (data.getApplicantStatus() == null) ? "" : data.getApplicantStatus();
						String users = (data.getResponsibleUsers() == null) ? "" : data.getResponsibleUsers();
						String actionRequired = (data.getActionRequired() == null) ? "" : data.getActionRequired();

						String experienceAndCurrentEmployer = null;
						if (!Utils.isBlankOrNull(currentEmployer)) {
							experienceAndCurrentEmployer = experience + " - " + currentEmployer;
						} else {
							experienceAndCurrentEmployer = experience;
						}
						if (name.length() > 22) {
							name = name.substring(0, 19) + "...";
						}
						if (experienceAndCurrentEmployer.length() > 22) {
							experienceAndCurrentEmployer = experienceAndCurrentEmployer.substring(0, 19) + "...";
						}
						if (department.length() > 17) {
							department = department.substring(0, 14) + "...";
						}
						if (position.length() > 17) {
							position = position.substring(0, 14) + "...";
						}
						if (stage.length() > 27) {
							stage = stage.substring(0, 24) + "...";
						}
						if (status.length() > 27) {
							status = status.substring(0, 24) + "...";
						}
						if (Utils.isBlankOrNull(status)) {
							status = "&nbsp;";
						}
						if (users.length() > 32) {
							users = users.substring(0, 29) + "...";
						}
						if (actionRequired.length() > 32) {
							actionRequired = actionRequired.substring(0, 29) + "...";
						}

						String usersAndAction = Utils.escapeHTML(users) + "<br>" + Utils.escapeHTML(actionRequired);
						if (Utils.isBlankOrNull(users)) {
							usersAndAction = SelectionProcessConstants.STEP_TITLE_ON_HOLD;
						}
				%> 
				<a href="#" onclick="viewApplicant(<bean:write name="data" property="applicantId"/>)" onmouseover="showAjaxTip(event,<bean:write name="data" property="applicantId"/>)" onmouseout="hideToolTip()"><%=Utils.escapeHTML(name)%></a>
				<td valign="top"><%=Utils.escapeHTML(department)%><br>
				<%=Utils.escapeHTML(position)%></td>
				<td valign="top"><%=Utils.escapeHTML(stage)%><br>
				<%=Utils.escapeHTML(status)%></td>
				<td valign="top"><%=usersAndAction%></td>

			</tr>
			<tr>
				<td colspan="5" class="seperator">&nbsp;</td>
			</tr>
		</logic:iterate>
	</table>
	</div>
</logic:present>
<div class="navBtn" style="float: right; margin-top: 10px;"><a
	href="#" style="width:135px;" class="active"
	onclick="sendEmail();return false;"><span class="rightC"></span><span
	class="leftC"></span><bean:message key="call_list.label.generate_call_list"/></a> 
<% if (request.getParameter("isPopup") == null) { %> <a href="#" style="width:60px;margin-left: 5px;" class="active"
	onclick="javascript: window.top.hidePopWin(false);return false;"><span
	class="rightC"></span><span class="leftC"></span><bean:message
	key="common.close" /></a> <% } %> </div>
<!--  end common button strip for mass email --></div>
<script>

var prevSelectAll=false;
var imgChecked="images/checkboxchecked.gif";
var imgUnChecked="images/checkboxunchecked.gif";
var waiting =false;
function selectAll(){	
	if(waiting)return;
	waiting=true;
	$('chkAll').style.display="none";
	window.setTimeout("toggleSelect()",5);
}
function toggleSelect(){
	var chk = document.getElementsByName("indSelect");
	var imgSrc = imgChecked;
	if(prevSelectAll){
		imgSrc = imgUnChecked;
	}
	if(chk){
		for(var i=0; i<chk.length; i++){
			chk[i].src=imgSrc;
		}
	}
	if(prevSelectAll){
		prevSelectAll=false;
		checkAllInArray(false);
	}else{
		prevSelectAll=true;
		checkAllInArray(true);
	}	
	$('chkAll').src=imgSrc;
	$('chkAll').style.display="block";
	waiting=false;
}
function toggleMe(id){
	if(waiting)return;
	waiting=true;
	for(var i=0; i<imgs.length; i++){
		var img = imgs[i];
		if(img[0]==id){
			if(img[1]){
				img[1]=false;
				$('ind_'+id).src=imgUnChecked;
			}else{
				img[1]=true;
				$('ind_'+id).src=imgChecked;
			}
		}
	}
	waiting=false;
}
function selectMe(id){
	for(var i=0; i<imgs.length; i++){
		var img = imgs[i];
		if(img[0]==id){
			img[1]=true;
			$('ind_'+id).src=imgChecked;
		}
	}
}

var imgs = new Array();
function buildArray(){
	var chk = document.getElementsByName("indSelect");
	if(chk){
		for(var i=0; i<chk.length; i++){
			var idm = chk[i].id;
			var id = idm.substring(idm.indexOf("_")+1);
			imgs[i]= new Array(id,false); 					
		}
	}
}
function checkAllInArray(condition){
	for(var i=0; i<imgs.length; i++){
		var img = imgs[i];
		img[1]=condition;
	}	
}
function setSelectedBoxes(){
	selectedBoxes="";
	for(var i=0; i<imgs.length; i++){
		var img = imgs[i];
		if(img[1]){
			selectedBoxes +=img[0]+",";
		}
	}
	if(selectedBoxes.length>0){
		selectedBoxes = selectedBoxes.substring(0,selectedBoxes.length-1);
	}
}
function sendEmail(){
	setSelectedBoxes();
	if ('<%=request.getParameter("isPopup")%>' == 'null') {
		window.open('selectionProcess.do?mode=callListforApplicants&applicantIds='+selectedBoxes,'_self');
	} else {		
		window.open('selectionProcess.do?mode=callListforApplicants&isNewWin=1&applicantIds='+selectedBoxes,'_new', 'status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=1,scrollbars=1,height=500px,width=800px,left=100px,top=100px');
	}
}
buildArray();

function viewApplicant(aId){
	var app = window.open("selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	app.focus();
}
function onWinLoad(){
	if ('<%=request.getParameter("isPopup")%>' == 'null') {
		window.top.setPopTitle("<b><bean:message key="call_list.label.call_list"/></b>");
	}
}
window.onload = onWinLoad;
</script>
