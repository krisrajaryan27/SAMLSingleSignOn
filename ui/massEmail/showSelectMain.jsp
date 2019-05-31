<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.inbox.InboxConstants,com.talentPool.applicant.dataobject.ApplicantData, 
                  com.talentPool.common.NavigationConstants,com.talentPool.inbox.form.InboxForm,
                  com.talentPool.common.utils.CommonUtils,com.talentPool.common.properties.TPApplicationProperties,
                  com.talentPool.repository.RepositoryConstants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.selectionProcess.dataobject.SelectionProcessData"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<script language="javascript" type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.config.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/box.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/yahoo-dom-event.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/tip_ajaxcall.js"></script>
<script language="javascript" type="text/javascript">
tinyMCE.init({
	mode : "textareas",
	elements : "newEmailBody",
	theme : "advanced",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_buttons1 : "newdocument,bold,italic,underline,forecolor,backcolor,bullist,numlist,separator,undo,redo,cut,copy,paste,justifyleft,justifyright,separator,formatselect,fontselect,fontsizeselect",
	force_br_newlines: true,
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	theme_advanced_disable : "anchor",
	theme_advanced_path : false
});

var cboTemplate=null;
</script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<div class="contentDivPop" style="padding-right:20px;">
	<!--  common template for mass email -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td width="68%"><div style="width:140px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>&nbsp;<img src="images/ico_mail.gif" vspace="3" align="absmiddle" />&nbsp;Email</div></td> 
	    <td width="32%" align="right"><!-- div style="width:30px; float:right; text-align:center;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_newwin.gif" width="13" height="13" vspace="3" /></div--> 
	      <div style="width:30px; float:right; text-align:center;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_down1.gif" name="btnImg1" width="13" height="13" vspace="3" id="imgup_1" onclick="toggleEmailBody();return false;" style="cursor:hand;"/></div></td> 
	  </tr> 
	</table> 
	
	<div class="outerDiv">
	<html:form action="/massEmail" onsubmit="return submitForm(this);">
	<html:hidden property="mode" value="massEmailNotify"/>
	<html:hidden property="applicantId"/>
	<html:hidden property="emailId"/>
	<html:hidden property="newEmailType"/>
	<html:hidden property="emailLocation"/>
	<html:hidden property="t"/>
	<html:hidden property="tmpEmailId"/>
	<html:hidden property="templateCode"/>
	<html:hidden property="selectedIds"/>
	<html:hidden property="from"/>
		<div class="popupTop">
			<table class="tblPop" width="100%">
			  <tr>
				<td  style="text-align:left;" colspan="2">
				<img src="images/checkboxchecked.gif" onclick="changeFromEmail(this);" id="chkFromEmail"/>
          	  <bean:message key="new_email.label.send_email_from"/> [<bean:write property="from" name="inboxForm"/>]
          	  	</td>
          	  </tr>
			  <tr>
				<td class="header" style="text-align:left;">
				  Template
				</td>
				<td>   					  
		        <script type="text/javascript">
					var opts = <bean:write name="templateJSArray" scope="request" filter="false"/>;
					var m = [new SelectOption('0','<bean:message key="common.selectlist.default"/>')];
					opts = m.concat(opts);
					cboTemplate = new SelectBox(opts,'<bean:write name="inboxForm" property="templateCode"/>','images/btn_dropdown.gif',{namesonly:false, width:'280px', size:15});
					cboTemplate.setOnChangeHandler('onTemplateChange');
					document.write(cboTemplate.getHtml());
					cboTemplate.init();
				</script>								
				</td>
			  </tr>
			  <tr>
				<td class="header"><bean:message key="view_email.label.subject"/></td>
				<td>
	          	  <html:text property="subject" name="inboxForm" size="100" styleClass="whiteInput" styleId="emailSubject"></html:text>                              
	          </td>
			  </tr>
		    </table>
		</div>
		<div style="" id="tmc">
		<html:textarea property="newEmailBody" name="inboxForm" styleClass="inputBox" style="width:100%;height:230px;" styleId="emailBody">
		</html:textarea></div></html:form>
	</div>
	<br>
	<logic:present name="applicants" scope="request">
		<table width="100%" class="boxHeader" style="margin-top:5px;" cellspacing="0" cellpading="0">
		<tr>
	 		<td class="header" width="30">
		   	<img src="images/checkboxunchecked.gif" onclick="selectAll();" id="chkAll" style="padding-left:1px;">&nbsp;
	 		</td>
	 	</tr>
		</table>
		<div class="outerDiv" style="height:340px; overflow: auto; border-top: 0px;">
		<table  cellpadding="0" cellspacing="0" class="searchResult" style="width: 710px;">
			<logic:iterate id="applicant" name="applicants"  scope="request">
			<bean:define id="data" name="applicant" type="SelectionProcessData"></bean:define>
			<tr>
			   <td valign="top" style="padding-right:5px;">
			   <img src="images/checkboxunchecked.gif" name="indSelect" id="ind_<bean:write name="data" property="applicantId"/>" onclick="toggleMe(<bean:write name="data" property="applicantId"/>)">
			   </td>
			   <td valign="top" >
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
					
					String usersAndAction=Utils.escapeHTML(users) + "<br>" + Utils.escapeHTML(actionRequired);
					if (Utils.isBlankOrNull(users)) {
						usersAndAction = SelectionProcessConstants.STEP_TITLE_ON_HOLD;
					}
					
			    %>
			   <a href="#" onclick="viewApplicant(<bean:write name="data" property="applicantId"/>)" onmouseover="showAjaxTip(event,<bean:write name="data" property="applicantId"/>)" onmouseout="hideToolTip()"><%=Utils.escapeHTML(name)%></a>
			   </td>
			   <td valign="top">
			   	<%=Utils.escapeHTML(department)%><br><%=Utils.escapeHTML(position)%>
			   </td>
			   <td valign="top">
			   <%=Utils.escapeHTML(stage)%><br><%=Utils.escapeHTML(status)%>
			   </td>
			   <td valign="top">
			   <%=usersAndAction%>
			   </td>
			   
		   	</tr>
		   	<tr>
		   		<td colspan="5" class="seperator">&nbsp;
		   		</td>
		   	</tr>
			</logic:iterate>
		</table>
	</div>   
	</logic:present>
	<div class="navBtn" style="float: right; margin-top: 10px;margin-bottom: 10px;">
			<a href="#" style="width:60px;margin-right: 5px;" class="active" onclick="sendEmail();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="new_email.label.send"/></a>
			<!-- a href="#" style="width:70px;margin-right: 5px;" class="active" onclick=""><span class="rightC"></span><span class="leftC"></span><bean:message key="mass_email.label.preview"/></a-->
			<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
	</div>
	<!--  end common button strip for mass email -->
</div>
<script>
var selectedBoxes="";
var frm = document.inboxForm;
var fromAdd = '<bean:write property="from" name="inboxForm"/>';
function changeFromEmail(chkBox){
	if(frm.from.value==''){
		chkBox.src='images/checkboxchecked.gif';
		frm.from.value=fromAdd;
	}else{
		chkBox.src='images/checkboxunchecked.gif';
		frm.from.value='';
	}
}

function onTemplateChange(){
    frm.templateCode.value = cboTemplate.getSelectedId();
	if(frm.templateCode.value!=0){
		var pars = "mode=getTemplateXML&templateCode="+frm.templateCode.value;
		var myAjax = ajaxCall("massEmail.do","get",pars,appendTemplateContent,reportError);
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
	
	var subject = getSingleElement(template,"subject","").unescapeHTML();
	var content = getSingleElement(template,"content","");//.escapeHTML();
	content = content.unescapeHTML();
	frm.subject.value=subject;
	tinyMCE.activeEditor.setContent(content);
//	frm.emailBody.value=content;

	
}

var down=false;
function toggleEmailBody(){
	if(down){
		$('tmc').style.display="block";
		toggleImage();
		down=false;
	}else{
		$('tmc').style.display="none";
		toggleImage();
		down=true;
	}
}
function toggleImage(){
	if(down){
		$('imgup_1').src="images/ico_down1.gif";//$(imgId).src.replace(".gif","1.gif");
	}else{
		$('imgup_1').src="images/ico_down.gif";//$(imgId).src.replace("1.gif",".gif");
	}
}
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
function preselect(){
	var selIds = frm.selectedIds.value;
	var arrIds = selIds.split(",");
	if(arrIds.length>0){
		for(var i=0;i<arrIds.length;i++ ){
			selectMe(arrIds[i]);
		}
	}
	
}
function sendEmail(){
	setSelectedBoxes();
	frm.selectedIds.value=selectedBoxes;
	frm.submit();
}
buildArray();
preselect();

function viewApplicant(aId){
	var app = window.open("selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	app.focus();
}
function onWinLoad(){
	window.top.setPopTitle("<b><bean:message key='mass_email.label.mass_email' /></b>");
}
window.onload = onWinLoad;
</script>