<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals,
				com.talentPool.common.properties.TPApplicationProperties,
				com.talentPool.inbox.InboxConstants,				
				com.talentPool.applicant.dataobject.ApplicantData, 
                com.talentPool.selectionProcess.SelectionProcessConstants,
                 com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants,
                com.talentPool.applicant.dataobject.EducationalData, 
                com.talentPool.common.properties.GlobalApplicationProperties,
                com.talentPool.common.properties.GlobalConstants,
                java.lang.Boolean,
                java.util.BitSet,
                com.talentPool.user.manager.ModuleSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.common.db.SimpleDataObject"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.custom.dataobject.CustomFieldData"%>
<%@page import="com.talentPool.documents.utils.DocumentUtils"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page errorPage="/common/errorPage.jsp"%>
<%@page import="com.talentPool.applicant.ApplicantConstants"%>
<%@page import="com.talentPool.common.CommonConstants"%><script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js" type="text/javascript"></script>
<script src="js/submodal/subModal.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">	
<script src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<script src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<%
	PermissionSet permissionSet 		= (PermissionSet)request.getSession(false).getAttribute("permissionSet");
	boolean currentCTCViewable 			= ImportConfigurationManager.isCurrentCTCViewable(permissionSet);
	boolean expectedCTCViewable 		= ImportConfigurationManager.isExpectedCTCViewable(permissionSet);
	pageContext.setAttribute("currentCTCViewable",currentCTCViewable);
	pageContext.setAttribute("expectedCTCViewable",expectedCTCViewable);
	
	ApplicantData aDo = (ApplicantData)request.getAttribute("applicantData");
	boolean showButtons= true;
	String showUndoFeedBackButton=(String)request.getAttribute("showUndoFeedback");
	if(!GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_CAN_APPLY_FOR_JOB)) 
		&& SelectionProcessConstants.APPLICANT_JOINED.equals(aDo.getApplicantJoined())){ 
			showButtons= false;
	}else if(!GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_JOINED_CANDIDATE_IN_SEARCH)) 
			&& SelectionProcessConstants.APPLICANT_JOINED.equals(aDo.getApplicantJoined())){ 
		showButtons= false;
	}	
%>
<script>
/*Menu Script*/
var blackListed = false; 
<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED %>" name="applicantData" property="applicantStatus" scope="request" >
	blackListed = true;
</logic:equal>
<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_NORMAL %>" name="applicantData" property="applicantStatus" scope="request" >
	blackListed = false;
</logic:equal>


var smsEnabled = false;
<% if (ModuleSet.isMODULE_SMS() && GlobalConstants.ENABLED.equalsIgnoreCase(GlobalApplicationProperties.getProperty("sms_enabled")) ) { %>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SEND_SMS">
smsEnabled = true;
</logic:equal>
<% } %>			
var menu = new TpMenu();
var mnu_msg = new TpMenu({type:'menu', id: 'msg', image: 'images/ico_message.gif', title:'<bean:message key="select.label.message" />', onclick:'onClickMenu', width:'200px'});
menu.addItem(mnu_msg);
if(smsEnabled){
	var mnu_sms = new TpMenu({type:'menu', id: 'sms', image:'images/ico_sms.gif', title:'<bean:message key="select.label.sms" />', onclick:'onClickMenu'});
	menu.addItem(mnu_sms);
}
var mnu_call = new TpMenu({type:'menu', id: 'call', image:'images/ico_call.gif', title:'<bean:message key="select.label.log_call" />', onclick:'onClickMenu'});
menu.addItem(mnu_call);

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SEND_EMAIL">
var mnu_email = new TpMenu({type:'menu', id: 'email', image:'images/ico_mail.gif', title:'<bean:message key="select.label.send_mail" />', onclick:'onClickMenu'});
menu.addItem(mnu_email);
</logic:equal>

<logic:equal value="true" name="permissionSet" scope="session" property="SHOW_CONFIDENTIAL_PROFILE">
<logic:equal name="applicantData" property="isConfidential" scope="request" value="1">
var mnu_confi = new TpMenu({type:'menu', id: 'confi', image:'images/ico_confidential_on.gif', title:'<bean:message key="common.confidential" />', onclick:'onClickMenu'});
menu.addItem(mnu_confi);
</logic:equal>
<logic:equal name="applicantData" property="isConfidential" scope="request" value="0">
var mnu_confi = new TpMenu({type:'menu', id: 'confi', image:'images/ico_confidential_off.gif',title:'<bean:message key="common.confidential" />', onclick:'onClickMenu'});
menu.addItem(mnu_confi);
</logic:equal>
</logic:equal>

<logic:notEmpty name="showUndoFeedback" scope="request">
	if(!blackListed){
		var mnu_undo = new TpMenu({type:'menu', id: 'undo',image:'images/ico_undo_last_feedback.gif',  title:'<bean:message key="select.label.undo_feedback"/>', onclick:'onClickMenu'});
		menu.addItem(mnu_undo);	
	}
</logic:notEmpty>

var mnu_reminder = new TpMenu({type:'menu', id: 'reminder',image:'images/ico_set_reminder.gif',  title:'Set Reminder', onclick:'onClickMenu'});
menu.addItem(mnu_reminder);

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_VIEW_OFFER_PROPOSAL">
var mnu_ctc_comp_screen = new TpMenu({type:'menu', id: 'ctcCompScreen',image:'images/ico_upload.gif',  title:'<bean:message key="ctc_comparison_screen.label.view_ctc_comparison" />', onclick:'onClickMenu'});
menu.addItem(mnu_ctc_comp_screen);
</logic:equal>

var mnu_resume = new TpMenu({type:'menu', id: 'resume',image:'images/ico_resume_file.gif',title:'Resume File', onclick:'onClickMenu'});
menu.addItem(mnu_resume);

var mnu_print = new TpMenu({type:'menu', id: 'print',image:'images/ico_print.gif',  title:'Print', onclick:'onClickMenu'});
menu.addItem(mnu_print);

var mnu_print = new TpMenu({type:'menu', id: 'printFeedback',image:'images/ico_print.gif',  title:'<bean:message key="resume_summary.label.printFeedback" />', onclick:'onClickMenu'});
menu.addItem(mnu_print);

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_UPLOAD_DOCUMENT">
var mnu_upload = new TpMenu({type:'menu', id: 'upload',image:'images/ico_upload.gif',  title:'<bean:message key="resume_summary.label.upload_file" />', onclick:'onClickMenu'});
menu.addItem(mnu_upload);
</logic:equal>
var mnu_blackList = null;
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BLACKLIST_APPLICANT">
	if(blackListed){
		mnu_blackList = new TpMenu({type:'menu', id: 'blackList', image:'images/ico_whitelist.gif', title:'<bean:message key="black_list.label.unBlackList" />', onclick:'onClickMenu'});
		menu.addItem(mnu_blackList);
	}else {
		mnu_blackList = new TpMenu({type:'menu', id: 'blackList', image:'images/ico_blacklist.gif',title:'<bean:message key="black_list.label.blackList" />', onclick:'onClickMenu'});
		menu.addItem(mnu_blackList);	
	}
</logic:equal>

var mnuhandlermore = new TpMenuHandler(menu,{});
mnuhandlermore.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:0});

function showMenuMore(applicantId, elementId){
	mnuhandlermore.show(applicantId,elementId);
}
function onClickMenu(mnu, opt){
	var id = mnu.getId();
	if(id == "msg"){
		 addMessage(); 
	}else if(id == "sms"){
		sendSMS();
	}else if(id == "call"){
		addPhone();
	}else if(id == "email"){
		newEmail();
	}else if(id == "resume"){
		openResumeFile();
	}else if(id=="print"){
		printResume();
	}else if(id=="upload"){
		addDocument();
	}else if(id=="reminder"){
		addReminder();
	}else if(id=="ctcCompScreen"){
		viewOfferProposalScreen();
	}else if(id=="undo"){
		undoLastFeedback();
	}else if(id=="confi"){
		changeConfidentiality();
	}else if(id=="printFeedback"){
		printFeedback();
	}else if(id='blackList'){
		blackListApplicant();
	}
}

function showPopupFlag(){	
	var url = 'selectionProcess.do?mode=setFlag&applicantId=' + '<bean:write name="applicantData" property="applicantId" scope="request" />' + '&selectedIds=' + '<bean:write name="applicantData" property="flagIds" scope="request" />';
	window.setTimeout("showInPopUp('"+url+"',550,320,reloadWindow,true);", 10);
}

function showSocialRelations(){	
	var url = 'importResume.do?mode=showSocialRelations&applicantId=' + '<bean:write name="applicantData" property="applicantId" scope="request" />' + '&selectedIds=' + '<bean:write name="applicantData" property="flagIds" scope="request" />';
	window.setTimeout("showInPopUp('"+url+"',825,620,reloadWindow,true);", 10);
}

function setApplicantFlag( flagIdToSet, flagStateToSet){
	window.location="selectionProcess.do?mode=setFlag&applicantId=" + '<bean:write name="applicantData" property="applicantId" scope="request" />' + "&flagIdToSet="+flagIdToSet + "&flagStateToSet="+flagStateToSet;
}
/*
extension of dhtmlXGridCell.js cell for implementing attachment column and
change the css of rows if email from already existing candidate
*/



function eXcell_att(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 	return this.cell.innerHTML;
 }
}

eXcell_att.prototype = new eXcell;
eXcell_att.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	var img = "";

	if(val==<%=SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED%>){
	 	img="ico_mail.gif";
	}else if(val==<%=SelectionProcessConstants.INTERACTION_EMAIL_SENT%>){
	 	img="ico_mail.gif";
	}else if(val==<%=SelectionProcessConstants.INTERACTION_APPOINTMENTS%>){
	 	img="ico_calendar.gif";	
	}else if(val==<%=SelectionProcessConstants.INTERACTION_INTERVIEW%>){
		img="ico_interaction.gif";	
	}else if(val==<%=SelectionProcessConstants.INTERACTION_PHONE%>){
		img="ico_phone.gif";	
	}else if(val==<%=SelectionProcessConstants.INTERACTION_NOTE%>){
		img="ico_note.gif";	
	}else if(val==<%=SelectionProcessConstants.INTERACTION_MESSAGE%>){
		img="ico_message.gif";	
	}if(val==<%=SelectionProcessConstants.INTERACTION_STATUS_MESSAGE%>){
		img="ico_change_status.gif";	
	}if(val==<%=SelectionProcessConstants.INTERACTION_SMS%>){
		img="ico_sms.gif";	
	}else if(val==<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>){
		img="";
	}if(val==<%=SelectionProcessConstants.INTERACTION_BLACKLISTED%>){
		img="ico_blacklist.gif";	
	}if(val==<%=SelectionProcessConstants.INTERACTION_UNBLACKLISTED%>){
		img="ico_whitelist.gif";	
	}
	if(img!="")
		this.cell.innerHTML = "<img src='"+this.grid.imgURL+""+img+"'>";
	else
		this.cell.innerHTML = "";
}

function eXcell_dochd(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 	return this.cell.innerHTML;
 }
}
eXcell_dochd.prototype = new eXcell;
eXcell_dochd.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	if(val==<%=SelectionProcessConstants.INTERACTION_HIDE%>){
	 	this.cell.parentNode.className='disabledrow';
	}
}


function eXcell_co(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 }
}

eXcell_co.prototype = new eXcell;
eXcell_co.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	if(val==<%=SelectionProcessConstants.INTERACTION_HIDE%>){
	 this.cell.parentNode.className='disabledrow';
	}
}
</script>
<style>
div.interactions table.hdr td {
     height: 20px;
}
</style>
<div class="contentDivPop" style="width:950px;margin:0px;padding:23px 0px 0px 23px;">
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

	<% if(showButtons){%>
	<table style="border: 0; padding: 0; border-spacing: 0;"> 
	<tr><td>
	<div class="navBtnTab" style="width:950px;float: left;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
	<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_EDIT_CANDIDATE_DETAILS">
			<a href="#" style="width:60px;" onclick="javascript: editApplicant();return false;">
				<span class="rightC"></span><span class="leftC"></span>
				<img src="images/ico_edit.gif" width="16" height="14" border="0" style="margin-right: 2px; vertical-align: middle;"/><bean:message key="common.edit"/>
			</a> 
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
		</logic:equal>
		
		<logic:empty name="inProcess" scope="request">
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHORTLIST">
				<logic:notEqual value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED %>" name="applicantData" property="applicantStatus" scope="request" >
					<a href="#" style="width:75px;" onclick="javascript: shortlist();return false;">
						<span class="rightC"></span><span class="leftC"></span>
						<bean:message key="applicant_listing.label.shortlist"/>
					</a> 
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
				</logic:notEqual>
			</logic:equal>
		</logic:empty>
		<logic:notEmpty name="inProcess" scope="request">
			<a href="#" style="width:135px;" onclick="javascript: moveUpOrDown(); return false;">
				<span class="rightC"></span><span class="leftC"></span>
				<img src="images/ico_status.gif" style="vertical-align: middle; width: 16; height: 11; border: 0;" /> 
				<bean:message key="select.label.enter_feedback"/>
			</a> 
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>		
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCHEDULE_INTERVIEW">
				<logic:notEmpty name="isSchedulable" scope="request">
					<a href="#" style="width:90px;" onclick="javascript: setAppointment();return false;">
						<span class="rightC"></span><span class="leftC"></span>
						<img src="images/ico_calendar.gif" width="13" height="11" border="0" style="vertical-align: middle;"/> 
						<bean:message key="select.label.schedule"/>
					</a> 
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
				</logic:notEmpty>
			</logic:equal>
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_UPDATE_FOLLOWUP">
			<a href="#" style="width:145px;" onclick="javascript: changeStatus(); return false;">
				<span class="rightC"></span><span class="leftC"></span>
				<img src="images/ico_interaction.gif" width="13" height="11" border="0" style="vertical-align: middle;"/> 
				<bean:message key="select.label.update_status"/>
			</a> 
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
			</logic:equal>
		</logic:notEmpty>
		<a href="#" style="width:90px;" onclick="javascript: addNote(); return false;">
			<span class="rightC"></span><span class="leftC"></span>
			<img src="images/ico_note.gif" width="12" height="12" border="0" style="vertical-align: middle;"/> 
			<bean:message key="select.label.add_note" />
		</a> 
		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SET_FLAG">
			<a href="#" style="width:85px;" onclick="javascript: showPopupFlag();" id="flags">
				<span class="rightC"></span><span class="leftC"></span>
				<img src="images/ico_set_flag.gif" width="10" height="14" border="0" style="margin-right: 3px; vertical-align: middle;"/>
				<bean:message key="select.label.set_flag"/>
			</a> 
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
		</logic:equal>
		
		<% if(ModuleSet.isMODULE_SOCIAL_NETWORK()) { %>
			<a href="#" style="width: 110px; " onclick="javascript: showSocialRelations();" id="flags">
				<span class="rightC"></span><span class="leftC"></span>
				<img src="images/social-icon.jpg" width="14" height="14" border="0" style="margin-right: 3px; vertical-align: middle;"/>Social Profile
			</a>
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
		<% } %>
		
		<a href="#" style="width:65px;" onmouseover="javascript: showMenuMore('<bean:write name="applicantData" property="applicantId" scope="request" />','more');" id="more">
			<span class="rightC"></span><span class="leftC"></span>
			<img src="images/ico_more.gif" width="14" height="14" border="0" style="margin-right: 3px; vertical-align: middle;"/>
			<bean:message key="select.label.more"/>
		</a>
		<logic:equal value="<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED %>" name="applicantData" property="applicantStatus" scope="request" >
			<h3 align="right" style="margin-top: 0px;"><bean:message key="black_list.label.blackListed_cap" /></h3>
		</logic:equal>	
	</div>	
	</td></tr>
	</table>		 
	<% }else{ // Show undoLastFeedback in case of Join
	%>	
	<table style="border: 0; padding: 0; border-spacing: 0;"> 
	<tr><td>
	<div class="navBtnTab" style="width:950px;float: left;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
	<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
	<%	
		if(showUndoFeedBackButton!=null){ 
	 %>
		<logic:notEmpty name="showUndoFeedback" scope="request">
			<a href="#" style="width:160px;" onclick="javascript: undoLastFeedback(); return false;">
				<span class="rightC"></span><span class="leftC"></span>
				<img src="images/ico_undo_feedback.gif" width="14" height="14" border="0" style="vertical-align: middle;"/>
				<bean:message key="select.label.undo_feedback"/>
			</a>
			<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
		</logic:notEmpty>
	<%}%>	
		<a href="#" style="width:90px;" onclick="javascript: addDocument(); return false;">
			<span class="rightC"></span><span class="leftC"></span>
			<bean:message key="resume_summary.label.upload_file"/>
		</a>
		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
		<a href="#" style="width:120px;" onclick="javascript: printFeedback(); return false;">
			<span class="rightC"></span><span class="leftC"></span>
			<bean:message key="resume_summary.label.printFeedback" />
		</a>
	</div>	
	</td></tr>
	</table>
	<%}%>


	  <table style="width: 100%; border: 0; padding: 0; border-spacing: 0;"> 
			<tr>
				<td width="50%" valign="top">
					<div style="margin-right:20px;" class="bottomDiv">
						<div class="bottomDivSec">
							<div class="vpTop">
								<table style="width: 100%; border: 0; padding: 0; border-spacing: 0;" class="vpTopTab">
						          <tr>
						            <td style="height:20px;"><b><bean:write name="applicantData" property="applicantName" scope="request" /></b>
						            		<% if(!Utils.isBlankOrNull(aDo.getApplicantSourceTitle())){ %>
							            		<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
												  <bean:message key="common.openingRoundBracket"/><bean:message key="resume_summary.label.source"/>&nbsp;<bean:write name="applicantData" property="applicantSourceTitle" scope="request" /><bean:message key="common.closingRoundBracket"/>
												<%} %>
											<% } %>
											<% if(aDo.getIsConfidential().equals("1")){ %>
												<img src="images/ico_confidential_on.gif" title="<bean:message key="common.profile"/> <bean:message key="common.hidden"/>" style="padding-left: 2px;margin-bottom: -4px;"/>
											<% }else{ %>
												<img src="images/ico_confidential_off.gif" title="<bean:message key="common.profile"/> <bean:message key="common.visible"/>" style="padding-left: 2px;margin-bottom: -4px;"/>
											<% } %>
											<% 
											String flagIds = aDo.getFlagIds();
											if(!Utils.isBlankOrNull(flagIds)){
												String[] fIds = flagIds.split(",");
												for(int i=0;i<fIds.length;i++){
													if(!Utils.isBlankOrNull(fIds[i])){
											%>
													<img src="<%=CommonUtils.getFlagImage(fIds[i]) %>" title="<%=CommonUtils.getFlagText(fIds[i]) %>" style="padding-left: 2px;margin-bottom: -4px;">													
											<%
													}
												}
											}
											%>
						            </td>
						            <td style="text-align: right;">
						            	<% if(!Utils.isBlankOrNull(aDo.getApplicantHRMSCode())){ %>
						            	<bean:message key="common.hrms_code"/> : 
						            	<bean:write name="applicantData" property="applicantHRMSCode" scope="request" />
						            	<%} %>
						            </td>
						            <td style="text-align: right;">
						            	ID: <bean:write name="applicantData" property="applicantId" scope="request" />
						            </td>
						          </tr>
						        </table>
							</div>
						</div>
						<% if(!Utils.isBlankOrNull(aDo.getApplicantPositionId())){ 
							if(showButtons){
						%>
						<div class="vpHeader" style="border-bottom: none;">
							<table class="vpTopTab" style="margin-top:5px; border: 0; padding: 0; border-spacing: 0;" >
					          <tr>
					            <td ><b class="Grey"><bean:message key="common.position"/>: </b> 
					            	<logic:notEmpty name="applicantData" property="applicantPositionTitle" scope="request" >
					            		<bean:write name="applicantData" property="applicantPositionTitle" scope="request" />(<bean:write name="applicantData" property="applicantPositionCode" scope="request" />)
					            	</logic:notEmpty>
					            </td>
					          </tr>
					          <logic:notEmpty name="applicantData" property="applicantStepTitle" scope="request" >
					          <tr>
					            <td ><b class="Grey"><bean:message key="resume_summary.label.step"/> </b> 
					            	<bean:write name="applicantData" property="applicantStepTitle" scope="request" />
					            <% if(aDo.getApplicantDateJoined()!=null){ %>
					            &nbsp;[ <bean:message key="resume_summary.label.joining_date"/>: <bean:write name="applicantData" property="applicantDateJoinedToDisplay" scope="request" /> ]
					            <% } %>
					            </td>
					          </tr>					          
					          <tr>
					            <td ><b class="Grey"><bean:message key="resume_summary.label.status"/> </b> <bean:write name="applicantData" property="currentStatus" scope="request" /></td>
					          </tr>					          
					          <tr>
					            <td ><b class="Grey"><bean:message key="resume_summary.label.action_required"/> </b> <%=(aDo.getUsersResponsible()==null)?"":aDo.getUsersResponsible() %>
					            <% 
					            String actionRequired = aDo.getActionRequired();
					            if(actionRequired.equals("schedule")){
					            %>
					            <bean:message key="resume_summary.label.to_schedule"/>
					            <% }else if(actionRequired.equals("conduct")){ %>
					            <bean:message key="resume_summary.label.to_conduct"/>
					            <% }else if(actionRequired.equals("confirm")){ %>
					            <bean:message key="resume_summary.label.to_confirm"/>
					            <% }else if(actionRequired.equals("feedback")){ %>
					            <bean:message key="resume_summary.label.to_feedback"/>
					            <% }else if(actionRequired.equals("hold")){ %>
					            <bean:message key="resume_summary.label.on_hold"/>
					            <% } %>
					            <bean:write name="applicantData" property="applicantStepTitle" scope="request" />
					            <% if(actionRequired.equals("conduct")){ %>
					            <bean:message key="resume_summary.label.on"/> <bean:write name="applicantData" property="appointmentDateToDisplay" scope="request" />					            <%} %>
					            </td>
					          </tr>
					          </logic:notEmpty>
					        </table>
						</div>
						<%
							}
						}
						%>
						<div class="outerDiv" style="overflow: auto;
						<% if(showButtons){ 
							if(Utils.isBlankOrNull(aDo.getApplicantPositionId())){
						%>
						height:320px;
						<% }else{ %>
						height:248px;
						<% } 
						}else{ %>
						height:320px;
						<%} %>						
						">
						<div class="outerDiv" style="border-width:0px 0px 1px 0px;">
						<table>
							<tr>
								<td>
									
								
						<table class="DataTable" style="padding: 0; border: 0; border-spacing: 0;">
							<tr>
							<td class="header">
								<bean:message key="resume_summary.label.contact"/>
							</td>
							</tr>
							<tr>
							<td class="content">
				                <%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_CURRENT_LOCATION)){%>
				                  <bean:message key="resume_summary.label.location"/>&nbsp;
				                  <% if(!Utils.isBlankOrNull(aDo.getApplicantCity())){	%>                                 
				                    <bean:write name="applicantData" property="applicantCity" scope="request"/>&nbsp;&nbsp;
				                   <%}else{%>
				                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                 <%}} %>
				                  <bean:message key="resume_summary.label.mobile"/>&nbsp;
				                   <% if(!Utils.isBlankOrNull(aDo.getApplicantCellPhone())){	
				                   		if(SelectionProcessConstants.PHONE_VALID.equals(aDo.getApplicantCellPhoneIsInvalid())){	
				                   %>
				                   <bean:write name="applicantData" property="applicantCellPhone" scope="request"/>&nbsp;&nbsp;
				                   <% 
				                   		}else{
				                   %>
				                   <font style="text-decoration:line-through;"><bean:write name="applicantData" property="applicantCellPhone" scope="request"/></font>&nbsp;&nbsp;                                    
				                  <% 	}
				                  } else{
				                  %>
				                  	<%if(ModuleSet.isMODULE_SMS()){ %>
				                  	<script>
				                  	if(smsEnabled){
										mnuhandlermore.disableMenuItem(mnu_sms.getId(), true);
				                  	}
				                  	</script>
				                  	<% } %>	
				                  <% } %>
							</td>
							</tr>
							<tr>
							<td class="content">
									<bean:message key="resume_summary.label.tel"/>&nbsp;
									<% if(!Utils.isBlankOrNull(aDo.getApplicantHomePhone())){	
									if(SelectionProcessConstants.PHONE_VALID.equals(aDo.getApplicantHomePhoneIsInvalid())){	
									%>
									<bean:write name="applicantData" property="applicantHomePhone" scope="request"/>&nbsp;&nbsp;
									<% 
									}else{
									%>
									<font style="text-decoration:line-through;"><bean:write name="applicantData" property="applicantHomePhone" scope="request"/></font>&nbsp;&nbsp;                                    
									<% 	}
										if	(!Utils.isBlankOrNull(aDo.getApplicantWorkPhone())){
									%>
										<bean:message key="resume_summary.label.or"/>
									<%
										}
									} 
									%>
									<% if(!Utils.isBlankOrNull(aDo.getApplicantWorkPhone())){	
									if(SelectionProcessConstants.PHONE_VALID.equals(aDo.getApplicantWorkPhoneIsInvalid())){	
									%>
									<bean:write name="applicantData" property="applicantWorkPhone" scope="request"/>&nbsp;&nbsp;
									<% 
									}else{
									%>
									<font style="text-decoration:line-through;"><bean:write name="applicantData" property="applicantWorkPhone" scope="request"/></font>&nbsp;&nbsp;                                    
									<% 	}
									} 
									%>
							</td>
							</tr>
							<tr>
							<td class="content">
								<% if(!Utils.isBlankOrNull(aDo.getApplicantEmail1())){ %>
				                    <a href="#" onclick="javascript: newEmail(); return false;"><%=Utils.stopBOTS(aDo.getApplicantEmail1())%></a>
				                	<% if(!Utils.isBlankOrNull(aDo.getApplicantEmail2())){ %>
				                      ,&nbsp;
				                <%		}
				                	}
				                	if(!Utils.isBlankOrNull(aDo.getApplicantEmail2())){
				                 %>  
				                    <a href="#" onclick="javascript: newEmail(); return false;"><%=Utils.stopBOTS(aDo.getApplicantEmail2())%></a>
								<% } %>
							</td>
							</tr>
							<tr>
							<td class="spacer">	</td>
							</tr>
						</table>
						</td>
						</tr>
						</table>
						</div>
						<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_SKILLS)){%>
						<div class="outerDiv" style="border-width:0px 0px 1px 0px;">
						<table class="DataTable" style="padding: 0; border: 0; border-spacing: 0;">
							<tr>
							<td class="header"><bean:message key="resume_summary.label.skills"/></td>
							</tr>
							<tr>
							<td class="content"><bean:write name="applicantData" property="skillsString" scope="request"/></td>
							</tr>
							<tr>
							<td class="spacer">	</td>
							</tr>
						</table>
						</div>
						<% } %>
						<div class="outerDiv" style="border-width:0px 0px 1px 0px;">
						<table class="DataTable" style="padding: 0; border-spacing: 0; border: 0;">
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_EXPERIENCE)){%>
							<tr>
							<td class="header"><bean:message key="resume_summary.label.experience"/></td>
							</tr>
							<tr>
							<td class="content"> 
							<bean:message key="resume_summary.label.totalExperience"/>&nbsp;
							<bean:write name="applicantData" property="applicantExperience" scope="request"/>
							</td>
							</tr>
							<% } %>
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER)){%>
							<tr>
							<td class="content"> 
							<bean:message key="resume_summary.label.employer"/> &nbsp;
							<% if(!Utils.isBlankOrNull(aDo.getApplicantCurrentEmployer())) {%>
							<bean:write name="applicantData" property="applicantCurrentEmployer" scope="request" />							
							<%} %>
							</td>
							</tr>
							<% } %>
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_CURRENT_CTC)){%>
							<logic:equal value="true" name="currentCTCViewable" scope="page">
								<tr>
									<td class="content"> 
										<bean:message key="resume_summary.label.current_ctc"/>&nbsp;
										<% if(!Utils.isBlankOrNull(aDo.getCurrentCTC())) {%>
											<bean:write name="applicantData" property="currentCTC" scope="request" /> as on <bean:write name="applicantData" property="currentCTCDateToDisplay" scope="request" />
										<%} %>
									</td>
								</tr>
							</logic:equal>	
							<% } %>
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_EXPECTED_CTC)){%>
							<logic:equal value="true" name="expectedCTCViewable" scope="page">
								<tr>
									<td class="content"> 
										<bean:message key="resume_summary.label.expected_ctc"/>&nbsp;
										<% if(!Utils.isBlankOrNull(aDo.getExpectedCTC())) {%>
											<bean:write name="applicantData" property="expectedCTC" scope="request" /> as on <bean:write name="applicantData" property="expectedCTCDateToDisplay" scope="request" />
										<%} %>
									</td>
								</tr>
							</logic:equal>	
							<% } %>
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_NOTICE_PERIOD)){%>
							<tr>
							<td class="content"> 
							<bean:message key="resume_summary.label.notice_period"/>: &nbsp;
							<% if(!Utils.isBlankOrNull(aDo.getNoticePeriod())) {%>
							<bean:write name="applicantData" property="noticePeriod" scope="request" />
							<%} %>
							</td>
							</tr>
							<% } %>
							<tr>
							<td class="spacer">	</td>
							</tr>
						</table>
						</div>
						<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_EDUCATION)){%>
						<table class="DataTable" style="padding: 0; border-spacing: 0; border: 0;">
							<tr>
							<td class="header"><bean:message key="resume_summary.label.education"/></td>
							</tr>
							<logic:notEmpty name="applicantData" property="educationalDetails" scope="request">
							<logic:iterate id="eData" name="applicantData" property="educationalDetails" scope="request">	
							<tr>
							<td class="content">
								<bean:write name="eData" property="formattedEducation" />
							</td>
							</tr>
							</logic:iterate>
							</logic:notEmpty>
							<tr>
							<td class="spacer">	</td>
							</tr>
						</table>
						<% } %>
						<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_EMPLOYMENT_HISTORY)){%>
						<table class="DataTable" style="padding: 0; border-spacing: 0; border: 0;">
							<tr>
								<td class="header"><bean:message key="common.employment_history"/></td>
							</tr>
							<logic:notEmpty name="applicantData" property="employmentHistoryDetails" scope="request">
							<logic:iterate id="empHisData" name="applicantData" property="employmentHistoryDetails" scope="request">	
							<tr>
								<td class="content">
									<bean:write name="empHisData" property="formattedEmploymentHistory" />
								</td>
							</tr>
							</logic:iterate>
							</logic:notEmpty>
							<tr>
								<td class="spacer">	</td>
							</tr>
						</table>
						<% } %>
						<% if(aDo.getCustomFields()!=null && aDo.getCustomFields().size()>0){ %>
						<div class="outerDiv" style="border-width:1px 0px 0px 0px;">
						<table class="DataTable" style="padding: 0; border-spacing: 0; border: 0;">
							<tr>
							<td class="header"><bean:message key="resume_summary.label.others"/></td>
							</tr>
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_DATE_OF_BIRTH)){%>
							<tr>
								<td class="content"> 
									<bean:message key="common.date_of_birth"/>: &nbsp;
									<% if(null!= aDo.getDateOfBirth()) {%>
									<bean:write name="applicantData" property="dateOfBirthToDisplay" scope="request" />
								<%} %>
								</td>
							</tr>
							<% } %>
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_PASSPORT_NUMBER)){%>
							<tr>
								<td class="content"> 
									<bean:message key="common.passport_number"/>: &nbsp;
									<% if(!Utils.isBlankOrNull(aDo.getPassportNumber())) {%>
									<bean:write name="applicantData" property="passportNumber" scope="request" />
								<%} %>
								</td>
							</tr>
							<% } %>
							<%if(ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_RESUME_TYPE)){%>
							<tr>
								<td class="content"> 
									<bean:message key="common.resume_type"/>: &nbsp;
									<% if(!Utils.isBlankOrNull(aDo.getResumeType())) {%>
									<bean:write name="applicantData" property="resumeType" scope="request" />
								<%} %>
								</td>
							</tr>
							<% } %>
							<logic:notEmpty name="applicantData" property="customFields" scope="request">
							<logic:iterate id="cData" name="applicantData" property="customFields" scope="request">	
							<tr>
							<td class="content">
								<bean:write name="cData" property="fieldDisplayName" />:&nbsp;<bean:write name="cData" property="displayValue" />
							</td>
							</tr>
							</logic:iterate>
							</logic:notEmpty>
							<tr>
							<td class="spacer">	</td>
							</tr>
						</table>
						</div>
						<%} %>
						<% if(aDo.getCustomTables()!=null && aDo.getCustomTables().size()>0){ %>
						<div class="outerDiv" style="border-width:1px 0px 0px 0px;">
						<table class="DataTable" style="padding: 0; border-spacing: 0; border: 0;">
						
						<logic:notEmpty name="applicantData" property="customTables" scope="request">
							<logic:iterate id="ctData" name="applicantData" property="customTables" scope="request">	
							<tr>
							<td class="header">
								<bean:write name="ctData" property="tableName" />
							</td>
							</tr>
							
							<logic:notEmpty name="ctData" property="rows">
							<logic:iterate id="row" name="ctData" property="rows" indexId="ctr">
							
							<logic:notEmpty name="row" property="cells">
							<tr>
							<logic:iterate id="cell" name="row" property="cells">
							<logic:equal value="0" name="ctr">
								<td class="content">
									<b><bean:write name="cell" property="value" /></b>
								</td>
							</logic:equal>
							<logic:notEqual value="0" name="ctr">
								<td class="content">
									<bean:write name="cell" property="value" />
								</td>
							</logic:notEqual>
							
							</logic:iterate>
							</tr>
							</logic:notEmpty>
							
							</logic:iterate>
							</logic:notEmpty>
							
							</logic:iterate>
							</logic:notEmpty>
							<tr>
							<td class="spacer">	</td>
							</tr>
						
						</table>
						</div>
						<%} %>
					</div>
					</div>
				</td>
				<td width="50%" valign="top">
					<table style="margin-top: 10px; padding: 0; border-spacing: 0;">
						<tr>
							<td>
								<div style="width:100px;cursor: pointer;" class="boxTab interactionTab" onclick="showAllInteractions(this);"
									id="allInteractions"><span class="rightC"></span><span
									class="leftC"></span>&nbsp; 
									<bean:message key="interaction.label.interaction_category_all" />
								</div>
							</td>
							<td>
								<div style="width:125px;cursor: pointer;" class="boxDarkTab interactionTab"
									onclick="showSelectionInteractions(this);" id="selectionInteractions"><span class="rightC"></span><span
									class="leftC"></span>&nbsp;
									<bean:message key="interaction.label.interaction_category_selection" /> 
								</div>
							</td>
							<td>
								<div style="width:125px;cursor: pointer;" class="boxDarkTab interactionTab"
									onclick="showCommunicationInteractions(this);" id="communicationInteractions"><span class="rightC"></span><span
									class="leftC"></span>&nbsp; 
									<bean:message key="interaction.label.interaction_category_communication" />
								</div>
							</td>
							<td>
								<div style="width:125px;cursor: pointer;" class="boxDarkTab interactionTab"
									onclick="showOtherInteractions(this);" id="otherInteraction"><span class="rightC"></span><span
									class="leftC"></span>&nbsp; 
									<bean:message key="interaction.label.interaction_category_others" />
								</div>
							</td>
						</tr>
					</table>
					<table style="padding: 0; border-spacing: 0; width: 100%;">
						<tr>
							<td colspan="2">
								<div id="gridbox" class="interactions" style="width:476px;height:140px"></div>					
								<div class="bottomDiv" style="margin-top:2px;"></div>
							</td>
						</tr>
						<tr><td style="height:5px;">
						</td></tr>
						<tr><td>
							<div id="docbox" style="width:476px;height:70px"></div>					
							<div class="bottomDiv" style="margin-top:2px;">
							</div>
						</td></tr>
						<tr><td style="height:5px;">
						</td></tr>
							<tr><td>
							<div id="resumeBox" style="width:476px;height:80px"></div>					
							<div class="bottomDiv" style="margin-top:2px;">
							</div>
						</td></tr>
					</table>
				</td>
			</tr>
	  </table>
</div>

<div class="contentDivPop" style="margin-top:10px;">
<div class="outerDiv">
	<iframe name="printFrame" id="printFrame" 
		style="width:100%; height:350px; z-index: 1;" marginheight="0" marginwidth="0" frameborder="0">
	</iframe>
</div>
</div>

<script type="text/javascript">
var applicantId = <bean:write name="selectionProcessForm" property="applicantId" scope="request"/>;
var resumePath = '<bean:write property="originalDoc" name="selectionProcessForm"/>';
var positionId =  '<bean:write name="applicantData" property="applicantPositionId" scope="request" />';
function addDocument() {
	var url = 'docs.do?mode=uploadDocument&applicantId=' +applicantId;
	window.setTimeout("showInPopUp('"+url+"',550,220,reloadDocGrid,true);", 10);
}

function addReminder() {
	var url = 'selectionProcess.do?mode=addReminder&applicantId=' +applicantId;
	window.setTimeout("showInPopUp('"+url+"',550,320,reloadDocGrid,true);", 10);
}

function viewOfferProposalScreen(){
	var url = 'offerProposalScreen.action?applicantId='+applicantId;
	window.setTimeout("showInPopUp('"+url+"',800,500,reloadGrid,true);", 10);
}

function editApplicant(){
	window.location='importResume.do?mode=editApplicant&subMode=edit&applicantId='+applicantId;
}
function printResume(){
	window.frames['printFrame'].focus();
	window.frames['printFrame'].print();
}

function openInParent(url){
	var error;
	try {
		if(typeof(window.opener)!="undefined" && !window.opener.closed) {
			window.opener.location=url;
			window.opener.focus();
		}else{
			window.open(url);
		}
	}
	catch (error) {
	}
	window.close();
}

function custom_date_sort(a,b,order){
	var n=getConvertedDate(a);
	var m=getConvertedDate(b);
	if(order=="asc")
		return n>m?1:-1;
	else
		return n<m?1:-1;
}

function getConvertedDate(a){
	var dt1=new Date();
	if(a.length>0){
		dt1.setFullYear(a.substring(6,10), parseInt(a.substring(3,5)) - 1, a.substring(0,2));
		hrs = a.substring(11,13);
		if((a.indexOf('PM') != -1)) {
			hrs = (parseInt(hrs)==12)?parseInt(hrs):parseInt(hrs)+12;
		}
		dt1.setHours(hrs);
		dt1.setMinutes(a.substring(14,16));
	}
	return dt1;		
}

var selectedResumeDocFilePath;
var msgGrid;
var docGrid;
var resumeGrid;
var msgGridId='gridbox';
var docGridId='docbox';
var resumeGridId='resumeBox';
var maxMsgHeight=200;
var maxDocHeight=65;

function doOnLoad() {
	initPopUp();
	initMsgGrid();
	initDocGrid();
	initResumeGrid();
}
function initMsgGrid(){
	msgGrid = new dhtmlXGridObject(msgGridId);
	msgGrid.imgURL = "images/"; 
	msgGrid.setHeader(",,<bean:message key="interaction.label.subject"/>,<bean:message key="interaction.label.User"/>,<bean:message key="interaction.label.date"/>,,");
	msgGrid.setInitWidths("24,24,170,104,135,0,0")
	msgGrid.setColAlign("left,left,left,left,left,left,left")
	msgGrid.setColTypes("att,ro,ro,ro,ro,co,ro");
	msgGrid.setColSorting("na,na,cstr,cstr,custom_date_sort,na,na");
	msgGrid.attachEvent("onKeyPress",onInteractionKeyPressed);
	msgGrid.attachEvent("onRowDblClicked",onRowDoubleClick);	
	//msgGrid.enableAutoHeigth(true,maxMsgHeight);
	msgGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	msgGrid.init();
	msgGrid.setHeaderCursor(",,pointer,pointer,pointer,,");
	msgGrid.enableDistributedParsing(true,20); /*added to avoid grid load time*/	
	msgGrid.loadXML('selectionProcess.do?mode=getApplicantInteractions&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>');
	msgGrid.setSortImgState(true,3,"DESC");
}
function initDocGrid(){
	docGrid = new dhtmlXGridObject(docGridId);
	docGrid.imgURL = "images/"; 
	docGrid.setHeader(",,,<bean:message key="resume_summary.label.file_name"/>,<bean:message key="interaction.label.User"/>,<bean:message key="interaction.label.date"/>,");
	docGrid.setInitWidths("18,25,25,152,100,135,0")
	docGrid.setColAlign("left,left,left,left,left,left,left")
	docGrid.setColTypes("ro,ro,ro,link,ro,ro,dochd");
	docGrid.setColSorting("na,na,na,cstr,cstr,custom_date_sort,na");
	docGrid.attachEvent("onKeyPress",onDocKeyPressed);
	docGrid.attachEvent("onRowDblClicked",onDocRowDoubleClick);
	docGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	docGrid.init();
	docGrid.setHeaderCursor(",,,pointer,pointer,pointer,");  
	docGrid.loadXML('docs.do?mode=getApplicantDocuments&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>');
	docGrid.setSortImgState(true,4,"DESC");
}

function initResumeGrid(){
	resumeGrid = new dhtmlXGridObject(resumeGridId);
	resumeGrid.imgURL = "images/"; 
	resumeGrid.setHeader(",,,<bean:message key='positions.applied.for.byCandidate'/>,Resume");
	resumeGrid.setInitWidths("2,2,2,193,250")
	resumeGrid.setColAlign("left,left,left,left,left")
	resumeGrid.setColTypes("ro,ro,ro,ro,ro");
	resumeGrid.setColSorting("na,na,na,na,na");
	resumeGrid.attachEvent("onKeyPress",onDocKeyPressed);
	resumeGrid.attachEvent("onRowDblClicked",onResRowDoubleClick);
	resumeGrid.attachEvent("onRowSelect",onResRowDoubleClick);
	resumeGrid.attachEvent("onXLE",function(){resumeGrid.selectRow(0,true,false,true);});
	resumeGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	resumeGrid.setColumnHidden(2,true);
	resumeGrid.init();
	resumeGrid.setHeaderCursor(",,,pointer,pointer");  
	resumeGrid.loadXML('positionSummary.do?mode=getPositionsApplicantAppliedFor&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>');
}

function onInteractionKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(msgGridId,msgGrid,keyCode,ctrl,shift);
}
function onDocKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(docGridId,docGrid,keyCode,ctrl,shift);
}

var msgGridPageSize=10;
var docGridPageSize=3;
function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==msgGridId){
			viewDetails(id);	
		}else if(grdId==docGridId){
			onClickDocument(id);
		}
		break;
	case 33:
		//page up
		var pageSize=msgGridPageSize;
		if(grdId==docGridId){
			pageSize=docGridPageSize;
		}
		var idx = grdObj.getRowIndex(id)-pageSize;
		idx = (idx<0)?0:idx;
		grdObj.selectRow(idx);
		break;
	case 34:
		//page down
		var pageSize=msgGridPageSize;
		if(grdId==docGridId){
			pageSize=docGridPageSize;
		}
		var idx = grdObj.getRowIndex(id)+pageSize;
		idx = (idx>=grdObj.getRowsNum())?grdObj.getRowsNum()-1:idx;
		grdObj.selectRow(idx);	
		break;
	case 46:
		if(grdId==docGridId){
			onClickDeleteDocument(id);
		}
	}
	
	
	return true;
}

function onBeforeShowMenu(id){
	msgGrid.selectRow(msgGrid.getRowIndex(id));
	return true;
}

function onRowDoubleClick(id){
	viewDetails(id);
}

function hideInteraction(id){
	var interactionType = msgGrid.getUserData(id,"interactionType");
	var interactionId = msgGrid.getUserData(id,"interactionId");
	var pars = "mode=hideShowInteraction&communicationId="+interactionId+"&communicationType="+ interactionType;
	var myAjax = ajaxCall("selectionProcess.do","get",pars,hideInteractionRowError,reportError);
	reloadGrid();
}

function hideInteractionRowError(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="resume_summary.error.hide_interaction"/>');
		return;
	}
}

function viewDetails(id){	
	var interactionIsHidden=msgGrid.getUserData(id,"interactionIsHidden");
	var interactionType = msgGrid.getUserData(id,"interactionType");
	var interactionId = msgGrid.getUserData(id,"interactionId");
	var documentId = msgGrid.getUserData(id,"documentId");
	var win =null;

	if(interactionIsHidden==<%=SelectionProcessConstants.INTERACTION_HIDE%>){
		<logic:equal value="true" name="permissionSet" scope="session" property="DO_NOT_SHOW_CONFIDENTIAL_DATA">
		return;
		</logic:equal>
	}

	if(interactionType==<%=SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED%> || interactionType==<%=SelectionProcessConstants.INTERACTION_EMAIL_SENT%>){
		var url = 'inbox.do?mode=viewEmail&emailId='+interactionId+'&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>&emailLocation=<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>';
		window.setTimeout("showInPopUp('"+url+"',800,650,reloadGrid,true);", 10);
		//win = window.open('','_newEmail','width=800,height=600,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_PHONE%> || interactionType=="<%=SelectionProcessConstants.INTERACTION_NOTE%>"){
		var url = 'selectionProcess.do?mode=viewPhoneLog&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType;
		if(documentId == '') {
			window.setTimeout("showInPopUp('"+url+"',550, 320,reloadGrid,true);", 10);
		} else {
			window.open(url);
		}
		//win = window.open('selectionProcess.do?mode=viewPhoneLog&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType,'_newPhone','width=520,height=300,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_INTERVIEW%> || interactionType==<%=SelectionProcessConstants.INTERACTION_APPLICANT_RESPONSE%>){		
		var url = 'selectionProcess.do?mode=viewInterviewLog&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType;
		window.setTimeout("showInPopUp('"+url+"',900,600,reloadWindow,true);", 10);
		//win = window.open('selectionProcess.do?mode=viewInterviewLog&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType,'_newInterview','width=520,height=350,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_APPOINTMENTS%>){
		var url = 'selectionProcess.do?mode=viewAppointment&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',550, 320,editAppointment,true);", 10);
		//win = window.open('selectionProcess.do?mode=viewAppointment&communicationId='+interactionId,'_viewAppointment','width=520,height=250,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_MESSAGE%> ){
		var url = 'selectionProcess.do?mode=viewMessage&communicationId='+interactionId+'&communicationType='+ interactionType;
		window.setTimeout("showInPopUp('"+url+"',550, 340,null,true);", 10);
		//win = window.open('selectionProcess.do?mode=viewMessage&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType,'_newMessage','width=520,height=300,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_STATUS_MESSAGE%> ){
		var url = 'selectionProcess.do?mode=viewStatusMessage&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType;
		window.setTimeout("showInPopUp('"+url+"',550, 200,null,true);", 10);
		//win = window.open('selectionProcess.do?mode=viewStatusMessage&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType,'_newMessage','width=520,height=300,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_SMS%> ){
		var url = 'selectionProcess.do?mode=viewSMS&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',550, 320,reloadGrid,true);", 10);	
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_BLACKLISTED%> || interactionType==<%=SelectionProcessConstants.INTERACTION_UNBLACKLISTED%>){
		var url = 'selectionProcess.do?mode=viewBlacklistReason&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',550, 320,reloadGrid,true);", 10);	
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL%>){
		var url = 'selectionProcess.do?mode=viewOfferDetailsModifiedInteraction&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',540, 275, null, true);", 10);	
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>){
		var url = 'selectionProcess.do?mode=viewOfferDetailsModifiedInteraction&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',540, 275, null, true);", 10);	
	}
	return; 
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function onClickDocument(id){
	if(id!=null){
		var filePath = docGrid.getUserData(id,"filePath");		
		var url = "<%=DocumentUtils.getDocumentURL("@fileName@","" )%>";
		url = url.replace(/(@fileName@)/g,filePath);
		window.open(url);
	}
}
function onDocRowDoubleClick(id){
	onClickDocument(id);
}

function onResRowDoubleClick(id){
	if(id!=null){
		var filePath = resumeGrid.cellById(id,4).getValue();
		selectedResumeDocFilePath = resumeGrid.cellById(id,2).getValue();
		var url ='importResume.do?mode=getResumeToImport&applicantId=<bean:write name="applicantData" property="applicantId" scope="request" />&originalResumePath='+filePath+'&noContext=1&hilite='+'<%=request.getParameter("hilite")%>';
		document.getElementById("printFrame").src=url;
	}
}

function onClickDeleteDocument(dId){
	if(dId==null){
		alert('<bean:message key="resume_summary.error.select_file_to_delete"/>');
		return;
	}
	var pars = "mode=deleteDocument&documentId=" + dId;
	var myAjax = ajaxCall("docs.do","get",pars,deleteDocRow,reportError);
	
}

function deleteDocRow(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="resume_summary.error.delete_file"/>');
		return;
	}
	var msgIds=getIds(xmlFile);
	for(var I=0; I<msgIds.length; I++){
		docGrid.deleteRow(msgIds[I]);
	}
	docGrid.clearSelection();
}

function onClickHideDocument(dId){
	if(dId==null){
		alert('<bean:message key="resume_summary.error.select_file_to_hide"/>');
		return;
	}
	var pars = "mode=hideDocument&documentId=" + dId;
	var myAjax = ajaxCall("docs.do","get",pars,hideDocRow,reportError);
	
}

function hideDocRow(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="resume_summary.error.hide_file"/>');
		return;
	}
	reloadDocGrid();
}

eXcell_link.prototype.getTitle=function(){
	return getCustomTitle(this);
}

dhtmlXGridCellObject.prototype.getTitle=function(){
	return getCustomTitle(this);
}

function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == msgGridId){	
		switch(obj.cell._cellIndex){
				case 0:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"type");
					break;	
				case 1:
					return '<bean:message key="resume_summary.tooltip.hideshow"/>';
					break;	
				case 2:
					if(obj.cell.parentNode.className!='disabledrow'){
						return unescapeHTML(obj.grid.getUserData(obj.cell.parentNode.idd,"subject"));
					}else{
						return "";
					}
					break;
				case 3:
					return unescapeHTML(obj.grid.getUserData(obj.cell.parentNode.idd,"name"));
					break;
		}
	}else if(grdId == docGridId){
		switch(obj.cell._cellIndex){
				case 0:
					return "<bean:message key="resume_summary.tooltip.delete"/>";
					break;
				case 1:
					return '<bean:message key="resume_summary.tooltip.hideshow"/>';
					break;
				case 2:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"fileName");
					break;
				case 3:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"fileName");
					break;
				case 4:
					return unescapeHTML(obj.grid.getUserData(obj.cell.parentNode.idd,"name"));
					break;
		}
	}	
	return obj.cell.innerHTML;
}


function addPhone(){
	var url = 'selectionProcess.do?mode=addPhoneLog&applicantId=' +applicantId+'&communicationType=<%=SelectionProcessConstants.INTERACTION_PHONE%>';
	window.setTimeout("showInPopUp('"+url+"',550,320,reloadWindow,true);", 10);
}
function addNote(){
	var url = 'selectionProcess.do?mode=addPhoneLog&applicantId=' +applicantId+'&communicationType=<%=SelectionProcessConstants.INTERACTION_NOTE%>';
	window.setTimeout("showInPopUp('"+url+"',550,250,reloadGrid,true);", 10);
}
function addMessage(){
	var url = 'selectionProcess.do?mode=addMessage&applicantId=' +applicantId;
	window.setTimeout("showInPopUp('"+url+"',610,350,reloadGrid,true);", 10);
}
function changeStatus() {
	var url = 'selectionProcess.do?mode=changeStatus&applicantId=' +applicantId;
	window.setTimeout("showInPopUp('"+url+"',550,250,reloadWindow,true);", 10);
}
function shortlist(){
	var url = 'selectionProcess.do?mode=shortlist&&applicantId='+applicantId;
	window.setTimeout("showInPopUp('"+url+"',650, 350,onShortListDone,true);", 10);
}
function moveUpOrDown() {
	var url = 'selectionProcess.do?mode=moveApplicantUpOrDown&applicantId='+applicantId;
	window.setTimeout("showInPopUp('"+url+"',900, 600,reloadWindow,true);", 10);
}
function undoLastFeedback() {
	var pars = "mode=undoLastFeedback&applicantId="+applicantId;
	var myAjax = ajaxCall("selectionProcess.do","get",pars,undoLastFeedbackError,reportError);
}
function undoLastFeedbackError(request){
	var xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		var errors=getErrors(xmlFile);
		alert(errors[0]);
		return;
	}else{
		reloadWindow();
	}
	
}
function newEmail(){
	var url = 'inbox.do?mode=newEmail&newEmailType=<%=InboxConstants.EMAIL_TYPE_NEW%>&emailLocation=<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>&applicantId='+applicantId;
	window.setTimeout("showInPopUp('"+url+"',810, 530,reloadGrid,true);", 10);
}
function setAppointment(){
 	window.location="calendar.do?mode=calendarHome&popup=1&selectedApplicant=" + applicantId;
}


function reloadGrid() {
	msgGrid.clearAll();
	msgGrid.loadXML('selectionProcess.do?mode=getApplicantInteractions&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>');
}


function showAllInteractions(obj) {
	msgGrid.filterBy(6,'');
	chageTabSelection(obj.id);
}

function showSelectionInteractions(obj){
	msgGrid.filterBy(6,function(data){
	    return data=='<%=SelectionProcessConstants.INTERACTION_INTERVIEW%>'
	    	|| data=='<%=SelectionProcessConstants.INTERACTION_SHORTLISTED%>'
	    	|| data=='<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>'
		    || data=='<%=SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL%>'
		    || data=='<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>';
	});	
	chageTabSelection(obj.id);
}

function showCommunicationInteractions(obj){
	msgGrid.filterBy(6,function(data){
	    return data=='<%=SelectionProcessConstants.INTERACTION_PHONE%>'
	    	|| data=='<%=SelectionProcessConstants.INTERACTION_SMS%>'
	    	|| data=='<%=SelectionProcessConstants.INTERACTION_EMAIL_SENT%>'
	    	|| data=='<%=SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED%>';
	});
	chageTabSelection(obj.id);
}

function showOtherInteractions(obj){
	msgGrid.filterBy(6,function(data){
	    return data!='<%=SelectionProcessConstants.INTERACTION_INTERVIEW%>'
    		&& data!='<%=SelectionProcessConstants.INTERACTION_SHORTLISTED%>'
    		&& data!='<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>'
    		&& data!='<%=SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL%>'
    		&& data!='<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>'	
    		&& data!='<%=SelectionProcessConstants.INTERACTION_PHONE%>'
    		&& data!='<%=SelectionProcessConstants.INTERACTION_SMS%>'
    		&& data!='<%=SelectionProcessConstants.INTERACTION_EMAIL_SENT%>'
    		&& data!='<%=SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED%>';
	});	
	chageTabSelection(obj.id);
}

function chageTabSelection(id){
	$$('.interactionTab').each(function(node){
		if(node.id==id){ 
			node.addClassName('boxTab');
			node.removeClassName('boxDarkTab');
		}else{
			node.addClassName('boxDarkTab');
			node.removeClassName('boxTab');
		}
	});
}

function reloadDocGrid() {
	docGrid.clearAll();
	docGrid.loadXML('docs.do?mode=getApplicantDocuments&applicantId=<bean:write property="applicantId" name="selectionProcessForm"/>');
}
function reloadWindow(){
	refreshParentTODO();
	window.location="selectionProcess.do?mode=viewOriginalResume&applicantId="+applicantId;
}

function refreshParentTODO(){
	var openerExist=false;
	if (window.opener != null && !window.opener.closed){
		openerExist=true;
	}
	if(openerExist){
		try{
			window.opener.refreshToDos();
		}catch(e){}
	}
}
function openResumeFile() {
	if (resumePath == '') {
		alert("<bean:message key="resume_summary.error.resume_file_not_available"/>");
	} else {
		var url = "<%=DocumentUtils.getDocumentURL("@fileName@","" )%>";
		url = url.replace(/(@fileName@)/g,selectedResumeDocFilePath);
		window.open(url);
	}
}
function editAppointment(returnVal){
	if(returnVal){
		returnVal = returnVal + "&popup=1";
		window.location=returnVal;
	}
}
function onShortListDone(rVal){
	reloadWindow();
}

function actionOnLoad(){
	initPopUp();
	doOnLoad();
}

function sendSMS() {
	var url = 'selectionProcess.do?mode=sendSMS&applicantId='+applicantId;
	window.setTimeout("showInPopUp('"+url+"',450, 320,reloadWindow,true);", 10);
}

function changeConfidentiality(){
	var confi = '<bean:write name="applicantData" property="isConfidential" scope="request"/>';
	if(confi=="0"){
		confi = "1";
	}else{
		confi = "0";
	}
	var pars = "mode=changeConfidentiality&applicantId="+applicantId+"&applicantIsConfidential="+confi;
	var myAjax = ajaxCall("selectionProcess.do","get",pars,onChangeConfidentiality,reportError);
}

function onChangeConfidentiality(request){
	var xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		var errors=getErrors(xmlFile);
		alert(errors[0]);
		return;
	}else{
		reloadWindow();
	}
}
function printFeedback(){
	if(positionId!=''){
		var url = 'selectionProcess.do?mode=printFeedback&applicantId='+applicantId+'&positionId='+positionId;
		var myWindow = window.open(url,"_blank","modal=yes,height=500,left=100,top=100,width=800,toolbar=no,titlebar=0,status=0,menubar=no,location= no,scrollbars=1");
	}else {
		alert('<bean:message key="resume_summary.error.notInvolvedInProcess"/>');
	}
}

function blackListApplicant(){
	var applicantName = '<bean:write name="applicantData" property="applicantName" scope="request" />';	
	var applicantStatus = '<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>';

	if(blackListed)
		applicantStatus = '<%=ApplicantConstants.APPLICANT_STATUS_BLACKLISTED%>';
	else
		applicantStatus = '<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>';

	var url = 'selectionProcess.do?mode=blackListApplicant&applicantId=' +applicantId+'&applicantName='+applicantName+'&positionId='+positionId+'&applicantStatus='+applicantStatus;
	window.setTimeout("showInPopUp('"+url+"',550,250,reloadWindow,true);", 10);
}

window.onload=actionOnLoad;
</script>
