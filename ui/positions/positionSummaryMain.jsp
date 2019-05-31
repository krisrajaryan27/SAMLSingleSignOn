<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>
<%@page import="com.talentPool.common.NavigationConstants"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%><script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script language="JavaScript" src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script language="JavaScript" src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script language="JavaScript"  src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.config.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/box.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/yahoo-dom-event.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/tip_ajaxcall.js"></script>
<script language="JavaScript" src="js/criteriapane.js" type="text/javascript"></script>

<style>
div.gridbox table.row20px tr  td{
	height:35px;
    white-space: nowrap;
    padding:0px 4px 0px 6px;
}
</style>
<link rel="stylesheet" type="text/css" href="themes/default/recentActivities.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">
<script>
/*Menu Script*/
var globalNum = '<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_GRID_RESULT_PAGE_SIZE)%>'
var smsEnabled = false;
<% if (ModuleSet.isMODULE_SMS() && GlobalConstants.ENABLED.equalsIgnoreCase(GlobalApplicationProperties.getProperty("sms_enabled"))) { %>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SEND_SMS">
smsEnabled = true;
</logic:equal>
<% } %>	

var menu = new TpMenu();

var mnu_msg = new TpMenu({type:'menu', id: 'msg', image: 'images/ico_message.gif', title:'<bean:message key="select.label.message" />', onclick:'onClickMenu', width:'175px'});
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

<% if(ModuleSet.isMODULE_MASS_EMAILS()){ %>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_MASS_EMAIL">
var mnu_mass_email = new TpMenu({type:'menu', id: 'massemail',image:'images/ico_multiple_email.gif', title:'<bean:message key="select.label.multi_email" />', onclick:'onClickMenu'});
menu.addItem(mnu_mass_email);
</logic:equal>		    	
<% } %>

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SET_FLAG">
	var mnu_fwd = new TpMenu({type:'menu', id: 'flags', image:'images/ico_set_flag.gif',title:'<bean:message key="select.label.set_flag"/>', onclick:'onClickMenu'});
	menu.addItem(mnu_fwd);
</logic:equal>

var mnu_fwd = new TpMenu({type:'menu', id: 'fwd', image:'images/ico_forward_resume.gif',title:'<bean:message key="common.forward" /> <bean:message key="common.resumes" />', onclick:'onClickMenu'});
menu.addItem(mnu_fwd);

var mnuhandlermore = new TpMenuHandler(menu,{});
mnuhandlermore.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:0});

function showMenuMore(applicantId, elementId){
	<%if(ModuleSet.isMODULE_SMS()){ %>
	if(smsEnabled){
		var mobile = ''+applicantsGrid.getUserData(applicantId,"mobile");			
		if(mobile == ''){
			mnuhandlermore.disableMenuItem(mnu_sms.getId(), true);
		}else{
			mnuhandlermore.disableMenuItem(mnu_sms.getId(), false);
		}
	}
	<%}%>
	mnuhandlermore.show(applicantId,elementId);
}
function onClickMenu(mnu, opt){
	var id = mnu.getId();
	if(id == "msg"){
		 showMessageBox(); 
	}else if(id == "sms"){
		sendSMS();
	}else if(id == "call"){
		addPhone();
	}else if(id == "email"){
		newEmail();
	}else if(id == "massemail"){
		massMail();
	}else if(id == "fwd"){
		forwardResumes();
	}else if(id == "flags"){
		showMenuFlag();
	}
}
/****************Function Related to Filter Menu**************************************************************/
var filterMenu = new TpMenu();

var userFilterMenu = new TpMenu({type:'menu', id: 'userFilterMenu', title:'<bean:message key="common.user"/>', onclick:'onClickFilterMenu'});
filterMenu.addItem(userFilterMenu);

var stageFilterMenu = new TpMenu({type:'menu', id: 'stageFilterMenu', title:'<bean:message key="common.stage"/>', onclick:'onClickFilterMenu'});
filterMenu.addItem(stageFilterMenu);

var resetMenuFilter = new TpMenu({type:'menu', id: 'resetFilterMenu', title:'<bean:message key="common.reset"/> <bean:message key="common.all"/>', onclick:'onClickFilterMenu', width:'120px'});
filterMenu.addItem(resetMenuFilter);

var filterMenuHandler = new TpMenuHandler(filterMenu,{});
filterMenuHandler.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:0});

function showFilterMenu(applicantId, elementId){
	filterMenuHandler.show(applicantId,elementId);
}

function onClickFilterMenu(mnu,opt){
	var id = mnu.getId();
	if(id == "resetFilterMenu"){
		resetApplicantGridFilters(); 
	}else if(id=="userFilterMenu"){
		setUserFilter();
	}else if(id=="stageFilterMenu"){
		setStageFilter();
	}
}

/****************END Filter Menu**************************************************************/
 
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	var tip="";
	if(grdId == applicantsGridId){
		switch(obj.cell._cellIndex){
			case 0:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_0_Comment");
				break;
			case 1:
				return "";
				//return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_I_Comment");
				break;
			case 2:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_II_Comment");
				break;
			case 3:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_III_Comment");
				break;
			case 4:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_IV_Comment");
				break;			
		}	
	}	else if (grdId == latestActivityGridId) {
			switch(obj.cell._cellIndex){
				case 1:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"owner");
					break;
				case 2:
					return "";
					//return obj.grid.getUserData(obj.cell.parentNode.idd,"applicantName");
					break;
				case 3:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"activity");
					break;
			}	
	} else if (grdId == rejectedCandidatesGridId) {
		switch(obj.cell._cellIndex){
		case 2:
			return "";
			//return obj.grid.getUserData(obj.cell.parentNode.idd,"applicantName");
			break;
		}	
	}	
	return obj.cell.innerHTML;
}

var sourcceSelectBox	= null;
var stepSelectBox 		= null; 
var actionSelectBox		= null;
var selectAll = [new SelectOption('','<bean:message key="common.selectlist.all"/>')];

var sort_img_ids = new Array();
sort_img_ids[sort_img_ids.length] = 'sort_img_1';
sort_img_ids[sort_img_ids.length] = 'sort_img_2';
sort_img_ids[sort_img_ids.length] = 'sort_img_3';

</script>
<html:form action="/selectionProcess">
	<html:hidden property="positionId" name="selectionProcessForm"/>
	<html:hidden property="selectedUserId" name="selectionProcessForm"/>
	<html:hidden property="stepLevel" name="selectionProcessForm" value='<%=PositionConstants.STEP_LEVEL_SHORTLIST+","+PositionConstants.STEP_LEVEL_SELECT+","+PositionConstants.STEP_LEVEL_ACCEPT%>'/>
</html:form>
 <div class="contentDiv">
  	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		  <tr >
		  	<td>
		  		<div style="margin-bottom: 10px;">
			  		<b style="font-size: 13px;">
						<bean:write name="positionForm" property="positionName" scope="request" />
					</b>
					<bean:message key="common.openingSquareBracket" /><bean:write name="positionForm" property="positionCode" scope="request" /><bean:message key="common.closingSquareBracket" />
		  		</div>
			</td>
			<td align="right">
				<div style="margin-bottom: 10px; margin-top: 0px;">
					<a href="#" onclick="viewDetails();return false;" class="green">
		   				<bean:message key="position_summary.link.viewDetails" />
		   			</a>
				</div>
			</td>
		  </tr>
		  <tr>
		  	<td> 
		    	<div id="GRID_NOTE" style="display: none;margin-bottom: 10px;">
		    		<bean:message key="select.label.grid_size_note"/>
		    	</div>
		    </td> 
		  </tr>
		  <tr>
		  	<td colspan="2">
		  		<div class="navBtnTab" style="width:738px;float: right;">
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  	
			  		<a href="#" onclick="javascript: moveUpOrDown();" style="width:135px;"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_status.gif" width="16" height="11" border="0" align="absmiddle" /> <bean:message key="select.label.enter_feedback"/></a> 
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SCHEDULE_INTERVIEW">
			  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/> 
			  		<a href="#" onclick="javascript: setAppointment();" style="width:90px;"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_calendar.gif" width="13" height="11" border="0" align="absmiddle" /> <bean:message key="select.label.schedule"/></a> 
			  		</logic:equal>
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_UPDATE_FOLLOWUP">
			  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
			  		<a href="#" onclick="javascript: changeStatus();" style="width:145px;"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_interaction.gif" width="13" height="11" border="0" align="absmiddle"/> <bean:message key="select.label.update_status"/></a> 	  		
			  		</logic:equal>
			  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/> 
			  		<a href="#" style="width:90px;" onclick="javascript: addNote();"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_note.gif" width="12" height="12" border="0" align="absmiddle"/> <bean:message key="select.label.add_note" /></a>  	  		
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
			  		<a href="#" style="width:65px;" onmouseover="javascript: showMenuMore(applicantsGrid.getSelectedId(),'more');" id="more"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_more.gif" width="14" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="select.label.more"/></a>
			  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
			  		<a href="#" style="width:65px;" onmouseover="javascript: showFilterMenu(applicantsGrid.getSelectedId(),'filtersMenu');" id="filtersMenu"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_more.gif" width="14" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="position_summary.menu.filters"/></a>
			  		<a href="#" style="text-align:right;background:none;padding-left: 102px;" onmouseover="changeImage('exportToExl', 'images/excel_co.GIF')" onmouseout="changeImage('exportToExl', 'images/excel_bw.GIF')">
						<img src="images/excel_bw.GIF" width="16" height="16" style="border:0px;" id="exportToExl" onclick="javascript:exportToExcel(); return false;" />
					</a>
				</div>
				<br/>
				<table class="boxHeader" cellspacing="0" cellpadding="0" border="0" style="border-bottom:0px;">
			  		<tr style="height: 18px;">		
			  			<td class="head" style="width:18px;cursor:default;">
			  				&nbsp;
			  				<span id="sort_img_0"></span>
			  			</td>		
			  			<td class="head" style="width:156px;cursor:default; "  >
			  				<bean:message key="common.name" />&nbsp;&nbsp;&nbsp;&nbsp;
			  				<span id="sort_img_1"></span>
			  			</td>
			  			<td class="head" style="width:140px;cursor:default;">
			  				<bean:message key="common.source" />&nbsp;&nbsp;&nbsp;&nbsp;
			  				<span id="sort_img_2"></span>
			  			</td>
			  			<td class="head" style="width:180px;cursor:default;" >
			  				<bean:message key="position_summary.label.steporstatus" />&nbsp;&nbsp;&nbsp;&nbsp;
			  				<span id="sort_img_3"></span>
			  			</td>
			  			<td class="head" style="width:222px;cursor:default;">
			  				<bean:message key="select.label.action_required" />&nbsp;&nbsp;&nbsp;&nbsp;
			  			</td>
			  		</tr>				  		
			  		<tr>				
			  			<td class="head" style="width:18px;cursor:default;">
			  				&nbsp;
			  			</td>  		
			  			<td class="head" style="width:156px;cursor:default;">
			  				<input type="text" id="applicantName" name="applicantName" style="width:150px;" value=""  onFocus="document.getElementById('applicantName').value=''"/>
			  			</td>
			  			<td class="head" style="width:140px;cursor:default;">
			  				<script>
								sourcceSelectBox = new SelectBox(selectAll,'','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:20});
								sourcceSelectBox.setOnChangeHandler('applyApplicantGridFilters');
								document.write(sourcceSelectBox.getHtml());
								sourcceSelectBox.init();
							</script>
			  			</td>
			  			<td  class="head" style="width:180px;cursor:default;">						  				
							<script>
								stepSelectBox = new SelectBox(selectAll,'','images/btn_dropdown.gif',{namesonly:false, width:'150px', size:20});
								stepSelectBox.setOnChangeHandler('applyApplicantGridFilters');
								document.write(stepSelectBox.getHtml());
								stepSelectBox.init();
							</script>
			  			</td>
			  			<td class="head" style="width:222px;cursor:default;">
			  				<script>
			  					actionSelectBox = new SelectBox(selectAll,'','images/btn_dropdown.gif',{namesonly:false, width:'150px', size:20});
				  				actionSelectBox.setOnChangeHandler('applyApplicantGridFilters');
								document.write(actionSelectBox.getHtml());
								actionSelectBox.init();
							</script>
			  			</td>
			  		</tr>
			  		<tr>
			  		<td style="height:7px;" colspan="5"></td>
			  		</tr>
			  	</table>
			  	<table cellpadding="0" cellspacing="0">
					<tr>
						<td class="gridborder" style="border-bottom: 0px;">
							<div id="APPLICANTS_DETAILS_GRID" style="width:738px;height:270px;margin-top: -1px; "></div>
						</td>
					</tr>
				</table>  
				<div class="outerDiv" style="margin: 0px; padding: 0px;border-top:1px dashed #C4C4C4;background-color:#f9f9f9;font-weight:bold;">
					<table style="border: 0px; height: 20px;" cellpadding="0" cellspacing="0">
							<tr>
								<td style="width:18px;" class=""></td>        
								<td id="total" style="color:#666;" class="">
									<div id="APPLICANTS_DETAILS_GRID_FOOTER">
									</div>
								</td>       
							</tr>
					</table>
				</div>
				<br/><br/>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="height: 150px;"> 
						<tr style="height: 19px;"> 
						  <td width="50%">
						  	<div style="width:140px;" class="boxTab">
						  		<span class="rightC"></span><span class="leftC"></span>&nbsp;
						  		<bean:message key="candidates.applied.forPosition"/>
						  	</div>				  	
						  </td>		
						  <td>
						  <div style="width:30px; float:right; text-align:center;cursor:pointer;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_down.gif" name="btnImg1" width="13" height="13" vspace="3" id="candidatesApplied" onclick="toggleCandidatesApplied(this.id);return false;" /></div>
						   <div style="width:30px; float:right; text-align:center;cursor:pointer;" onmouseover="changeImage('exportAppliedCanToExl', 'images/excel_co.GIF')" onmouseout="changeImage('exportAppliedCanToExl', 'images/excel_bw.GIF')" class="boxTab">
							<span class="rightC"></span><span class="leftC"></span>
							<img src="images/excel_bw.GIF" width="13" height="13" vspace="3" id="exportAppliedCanToExl" onclick="javascript:exportAppliedCandidatesToExcel();return false;" />
						  </div>
						  </td>   
						</tr>
						<tr>
							<td width="100%" colspan="2" valign="top">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td class="gridborder">
											<div id="CANDIDATES_APPLIED_GRID"
												style="width: 738px; height: 150px; margin-top: -1px;">
												
												</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>	
				<br/><br/>
				<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
					<tr> 
					  <td width="50%">
					  	<div style="width:140px;" class="boxTab">
					  		<span class="rightC"></span><span class="leftC"></span>&nbsp;
					  		<bean:message key="select.latest_activity.label.latest_activities" />
					  	</div>				  	
					  </td>		
					  <td><div style="width:30px; float:right; text-align:center;cursor:pointer;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_down.gif" name="btnImg1" width="13" height="13" vspace="3" id="imgup_1" onclick="toggleGrid(this.id,'GRD_LATEST_ACTIVITY');return false;" /></div></td>   
					</tr>
					<tr> 
					  <td width="100%" colspan="2">
					  	<table cellpadding="0" cellspacing="0">
							<tr>
								<td class="gridborder">
									<div id="LATEST_ACTIVITY_GRID" style="width:738px;height:170px;"></div>
								</td>
							</tr>
						</table>
					  </td>		  
					</tr> 
				</table>	
				<br/><br/>
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_VIEW_REJECTED_CANDIDATES">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="height: 219px;"> 
						<tr style="height: 19px;"> 
						  <td width="50%">
						  	<div style="width:140px;" class="boxTab">
						  		<span class="rightC"></span><span class="leftC"></span>&nbsp;
						  		<bean:message key="position_summary.header.rejected_candidates" />
						  	</div>				  	
						  </td>		
						  <td>
						  <div style="width:30px; float:right; text-align:center;cursor:pointer;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_down.gif" name="btnImg1" width="13" height="13" vspace="3" id="rejCandToggleImg" onclick="toggleRejCandidatesGrid(this.id);return false;" /></div>
						  <div style="width:30px; float:right; text-align:center;cursor:pointer;" onmouseover="changeImage('exportRejCanToExl', 'images/excel_co.GIF')" onmouseout="changeImage('exportRejCanToExl', 'images/excel_bw.GIF')" class="boxTab">
							<span class="rightC"></span><span class="leftC"></span>
							<img src="images/excel_bw.GIF" width="13" height="13" vspace="3" id="exportRejCanToExl" onclick="javascript:exportRejectedCandidatesToExcel();return false;" />
						  </div>
						  </td>   
						</tr>
						<tr>
							<td width="100%" colspan="2" valign="top">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" colspan="2" valign="top">
											<table class="boxHeader" cellspacing="0" cellpadding="0"
												border="0" style="border-bottom: 0px;">
												<tr style="height: 18px;">
													<td class="head" style="width: 18px; cursor: default;">
														&nbsp; <span id="sort_img_0"></span>
													</td>
													<td class="head" style="width: 196px; cursor: default;">
														<bean:message key="common.name" />&nbsp;&nbsp;&nbsp;&nbsp;
														<span id="sort_img_1"></span>
													</td>
													<td class="head" style="width: 160px; cursor: default;">
														<bean:message key="common.step" />&nbsp;&nbsp;&nbsp;&nbsp;
														<span id="sort_img_2"></span>
													</td>
													<td class="head" style="width: 200px; cursor: default;">
														<bean:message key="position_summary.label.rejected_by" />&nbsp;&nbsp;&nbsp;&nbsp;
														<span id="sort_img_3"></span>
													</td>
													<td class="head" style="width: 142px; cursor: default;">
														<bean:message key="position_summary.label.rejected_date" />&nbsp;&nbsp;&nbsp;&nbsp;
													</td>
												</tr>
												<tr>
													<td class="head" style="width: 18px; cursor: default;">
														&nbsp;</td>
													<td class="head" style="width: 196px; cursor: default;">
														<input type="text" id="rejectedApplicantName"
														name="rejectedApplicantName" style="width: 150px;"
														value=""
														onFocus="document.getElementById('rejectedApplicantName').value=''" />
													</td>
													<td class="head" style="width: 160px; cursor: default;">
														<script>
															rejectedStepSelectBox = new SelectBox(selectAll,'','images/btn_dropdown.gif',{namesonly : false,width : '120px',size : 20});
															rejectedStepSelectBox.setOnChangeHandler('applyRejectedCandidateGridFilters');
															document.write(rejectedStepSelectBox.getHtml());
															rejectedStepSelectBox.init();
														</script>
													</td>
													<td class="head" style="width: 200px; cursor: default;">
														<script>
															rejectedBySelectBox = new SelectBox(selectAll,'','images/btn_dropdown.gif',{namesonly : false,width : '150px',size : 20});
															rejectedBySelectBox.setOnChangeHandler('applyRejectedCandidateGridFilters');
															document.write(rejectedBySelectBox.getHtml());
															rejectedBySelectBox.init();
														</script>
													</td>
												</tr>
												<tr>
													<td style="height: 7px;" colspan="5"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="gridborder" style="border-bottom: 0px;">
											<div id="REJECTED_CANDIDATES_GRID"
												style="width: 738px; height: 135px; margin-top: -1px;"></div>
										</td>
									</tr>
								</table>
								<div class="outerDiv"
									style="margin: 0px; padding: 0px; border-top: 1px dashed #C4C4C4; background-color: #f9f9f9; font-weight: bold;">
									<table style="border: 0px; height: 20px;" cellpadding="0"
										cellspacing="0">
										<tr>
											<td style="width: 18px;" class=""></td>
											<td id="total" style="color: #666;" class="">
												<div id="REJECTED_CANDIDATES_GRID_FOOTER"></div> <A
												NAME="REJECTED"></A>
											<!-- Anchor for link coming from dashboard -->
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>	
					<br/><br/>
				</logic:equal>
		  	</td>
		  </tr> 
	</table> 
  </div> 
<br/><br/>
<script language="javascript">
var positionId = '<bean:write name="positionForm" property="positionId" />';
var positionTitle = '<bean:write name="positionForm" property="positionName" />';
var tenthMarksFilter = '<bean:write name="positionForm" property="tenthMarksFilter" />';
var twelvethMarksFilter = '<bean:write name="positionForm" property="twelvethMarksFilter" />';
var tenthMarks = '<bean:write name="positionForm" property="tenthMarks" />';
var twelvethMarks = '<bean:write name="positionForm" property="twelvethMarks" />';
var gradeMarksFilter = '<bean:write name="positionForm" property="gradeMarksFilter" />';
var gradeMarks = '<bean:write name="positionForm" property="gradeMarks" />';
var postGradeMarksFilter = '<bean:write name="positionForm" property="postGradeMarksFilter" />';
var postGradeMarks = '<bean:write name="positionForm" property="postGradeMarks" />';
var ageFilter = '<bean:write name="positionForm" property="ageFilter" />';
var age = '<bean:write name="positionForm" property="age" />';
var yearOfExperienceFilter = '<bean:write name="positionForm" property="yearOfExperienceFilter" />';
var yearOfExperience = '<bean:write name="positionForm" property="yearOfExperience" />';
var gender = '<bean:write name="positionForm" property="gender" />';
var gapInAcademics = '<bean:write name="positionForm" property="gapInAcademics" />';
var reqSentToLoadLatestActivities=false;
var selectedView='<%=NavigationConstants.T_POSITIONS%>';
var showRej='<%=request.getParameter("showRej")%>';

window.onload=doOnLoad;
function doOnLoad() {	
	initPopUp();
	initApplicantDetailsGrid();
	initLatestActivityGrid();
	Event.observe($('applicantName'), "keyup", onApplicantFilterChange.bindAsEventListener(this));
	criteriaPane= new criteriaPane('criteriaDiv'); // variable used in left pane
	loadApplicantGridFilters(); //function defined in left panel
	initAppliedCandidatesGrid();
	
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_VIEW_REJECTED_CANDIDATES">
		initRejectedCandidatesGrid();
		loadRejectedApplicantGridFilters();
		if(showRej=='1')
			showRejectedCandidatesGrid();
		Event.observe($('rejectedApplicantName'), "keyup", onRejectedApplicantFilterChange.bindAsEventListener(this));
	</logic:equal>
}
var observer=false;
function onApplicantFilterChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			if(observer) clearTimeout(observer);
			observer = setTimeout(applyApplicantGridFilters.bind(this), 300);
		}
		
	}
}
function onRejectedApplicantFilterChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			if(observer) clearTimeout(observer);
			observer = setTimeout(applyRejectedCandidateGridFilters.bind(this), 300);
		}
		
	}
}

//******************Initialization,Loading,Event Handlers of grid 'Applicants Details Grid'**************/
//Applicants Details Grid initialisation 
var applicantsGrid;
var applicantsGridId='APPLICANTS_DETAILS_GRID';
function initApplicantDetailsGrid(){
	applicantsGrid = new dhtmlXGridObject(applicantsGridId); 
	applicantsGrid.imgURL = "images/";
	applicantsGrid.setHeader("#master_checkbox,<bean:message key="common.name" />,<bean:message key="common.source" />,<bean:message key="position_summary.label.steporstatus" />,<bean:message key="select.label.action_required" />"); 
	applicantsGrid.setInitWidths("40,150,132,175,215");
	applicantsGrid.setColAlign("left,left,left,left,left,left");
	applicantsGrid.setColTypes("ch,link,ro,ro,ro,ro"); 
	applicantsGrid.setColSorting("na,sort_name,sort_source,sort_step,na");	    
	applicantsGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	applicantsGrid.attachEvent("onXLE",applicantsGridOnLoadingEnd);
	applicantsGrid.attachEvent("onKeyPress",onInprocessApplicantKeyPressed);
	applicantsGrid.attachEvent("onRowSelect",onInProcessApplicantRowSelected);
	applicantsGrid.attachEvent("onRowDblClicked",onInprocessApplicantRowDoubleClicked);
	applicantsGrid.attachEvent("onCheckbox",doOnCheck);
  	applicantsGrid.enableResizing("false,false,false,false,false");
  	applicantsGrid.setAwaitedRowHeight(36);
  	applicantsGrid.setNoHeader(false);
  	applicantsGrid.checkAll(false);
  	applicantsGrid.init();   
  	applicantsGrid.setHeaderCursor(",pointer,pointer,pointer,"); 
  	applicantsGrid.enableMultiselect(true);
  	applicantsGrid.enableSmartRendering(true);
  	applicantsGrid.setSortImgState(true,1,"asc");
  	window.setTimeout("loadApplicantDetailsGrid()", 2);
}

function doOnCheck(rowId,cellInd,state){
	return true;
}

//Applicants Details Grid loading
function loadApplicantDetailsGrid(){
	applicantsGrid.clearAll();
	var url = "positionSummary.do?mode=getInprocessApplicantsXml"+getCriteriaQryStringForGrid();
	applicantsGrid.loadXML(url);
}
//Applicants Details Grid Events
function onInprocessApplicantKeyPressed(keyCode,ctrl,shift){
	//alert('applicantsGridId',applicantsGrid.cells(id,0));
	return onKeyPressed(applicantsGridId,applicantsGrid,keyCode,ctrl,shift);
}
function onInProcessApplicantRowSelected(id,idx_col){
//	alert('applicantsGridId',applicantsGrid.cells(id,0));
//	alert('applicantsGridId',applicantsGrid.getCheckedRows(0));
	onRowSelected(applicantsGridId,applicantsGrid,id,idx_col);
}
function onInprocessApplicantRowDoubleClicked(id,idx_col){
	//alert('applicantsGridId',applicantsGrid.getCheckedRows(0));
	onRowDoubleClicked(applicantsGridId,applicantsGrid,id,idx_col);
}
function applicantsGridOnLoadingEnd(){
	updateFooterText(applicantsGrid, 'APPLICANTS_DETAILS_GRID_FOOTER');
}
//******************Initialization,Loading,Event Handlers of grid 'Latest Activities Grid'**************/
//Latest Activities Grid initialisation 
var latestActivityGrid;
var latestActivityGridId = 'LATEST_ACTIVITY_GRID';
function initLatestActivityGrid(){
	latestActivityGrid = new dhtmlXGridObject(latestActivityGridId); 
	latestActivityGrid.imgURL = "images/"; 
	latestActivityGrid.setHeader("<bean:message key="hire.latest_activity.label.date" />,<bean:message key="hire.latest_activity.label.by" />,<bean:message key="hire.latest_activity.label.candidate" />,<bean:message key="hire.latest_activity.label.action" />,"); 
	latestActivityGrid.setInitWidths("148,80,150,330,0");
	latestActivityGrid.setColAlign("left,left,left,left,left");
	latestActivityGrid.setColTypes("ro,ro,link,link,co"); 
	latestActivityGrid.setColSorting("custom_date_sort,sort_by,sort_applicant,na,na");	       
	latestActivityGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	//latestActivityGrid.attachEvent("onXLE",latestActivityGridOnLoadingEnd);
	latestActivityGrid.attachEvent("onKeyPress",onActivityKeyPressed);
	latestActivityGrid.attachEvent("onRowSelect",onActivityRowSelected);
	latestActivityGrid.attachEvent("onRowDblClicked",onActivityRowDoubleClicked);
	//latestActivityGrid.attachEvent("onAfterSorting",onlatestActivityGridAfterSorting);
	latestActivityGrid.setSkin("gray");
	latestActivityGrid.enableAutoHeight(true,"170");
	latestActivityGrid.setAwaitedRowHeight(21);      
	latestActivityGrid.init();   
	latestActivityGrid.enableSmartRendering(true);
	latestActivityGrid.setHeaderCursor("pointer,pointer,pointer,,"); 
	//latestActivityGrid.setSortImgState(true,0,"desc");
	<logic:present name="showfeedback" scope="request">
		<logic:notEmpty name="applicantId" scope="request">
			showMoveUpDownScreen('<bean:write name="applicantId" scope="request"/>');
		</logic:notEmpty>
	</logic:present>
}

function loadGrids() {
	applyApplicantGridFilters();
	latestActivityGrid.clearAll();
	toggleImage('imgup_1',true);
	latestActivityGrid.loadXML("select.do?mode=getLatestActivityXmlForPostionSummary&positionId="+positionId);
}
function onActivityKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(latestActivityGridId,latestActivityGrid,keyCode,ctrl,shift);
}
function onActivityRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(latestActivityGridId,latestActivityGrid,id,idx_col);
}
function onActivityRowSelected(id,idx_col){
	onRowSelected(latestActivityGridId,latestActivityGrid,id,idx_col);
}

function toggleGrid(imgId,grdId){
	if (latestActivityGrid.getRowsNum() != 0) {
		latestActivityGrid.clearAll();
		toggleImage(imgId,false);
	} else if (reqSentToLoadLatestActivities == false){
		reqSentToLoadLatestActivities=true;
		loadLatestActivities();
		toggleImage(imgId,true);		
	}
}
function toggleImage(imgId, down){
	if(down){
		$(imgId).src="images/ico_down1.gif";//$(imgId).src.replace(".gif","1.gif");
	}else{
		$(imgId).src="images/ico_down.gif";//$(imgId).src.replace("1.gif",".gif");
	}
	reqSentToLoadLatestActivities=false;
}

function toggleRejCandidatesGrid(){
	if($('rejCandToggleImg') && $('rejCandToggleImg').src.indexOf('images/ico_down.gif')!=-1){
		showRejectedCandidatesGrid();
	}else{
		hideRejectedCandidatesGrid();
	}
}

function showRejectedCandidatesGrid(imgId){
	$('rejCandToggleImg').src = "images/ico_down1.gif";
	$('REJECTED_CANDIDATES_GRID_FOOTER').show();
	$('REJECTED_CANDIDATES_GRID').show();
	loadRejectedCandidatesGrid();	
}

function hideRejectedCandidatesGrid(imgId){
	$('rejCandToggleImg').src = "images/ico_down.gif";
	rejectedCandidatesGrid.clearAll();
	$('REJECTED_CANDIDATES_GRID_FOOTER').hide();
	$('REJECTED_CANDIDATES_GRID').hide();
}

function loadLatestActivities(){
	latestActivityGrid.loadXML("select.do?mode=getLatestActivityXmlForPostionSummary&positionId="+positionId);
}
/**************************End Latest Activities Grid initialisation************************************/

/******************Initialization, Loading, Event Handlers of grid 'Rejected Candidates Grid'**************/
var rejectedCandidatesGrid;
var rejectedCandidatesGridId = 'REJECTED_CANDIDATES_GRID';
function initRejectedCandidatesGrid(){
	rejectedCandidatesGrid = new dhtmlXGridObject(rejectedCandidatesGridId); 
	rejectedCandidatesGrid.imgURL = "images/"; 
	rejectedCandidatesGrid.setHeader('&nbsp;,&nbsp;,<bean:message key="common.name" />,<bean:message key="common.step" />,<bean:message key="position_summary.label.rejected_by" />,<bean:message key="position_summary.label.rejected_date" />');
	rejectedCandidatesGrid.attachHeader('&nbsp;,&nbsp;,<input type="text" id="nameFilter" onkeyup="filterApplicant(this);" />,#select_filter_strict,#select_filter_strict,',["","","color:red;","","",""]);
	rejectedCandidatesGrid.setInitWidths("18,0,196,160,200,142");
	rejectedCandidatesGrid.setColAlign("left,left,left,left,left,left");
	rejectedCandidatesGrid.setColTypes("ro,ro,link,ro,ro,ro"); 
	rejectedCandidatesGrid.setColSorting("na,na,na,sort_by,sort_applicant,sort_applicant");	       
	rejectedCandidatesGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	rejectedCandidatesGrid.attachEvent("onXLE",rejectedCandidatesOnLoadingEnd);
	rejectedCandidatesGrid.attachEvent("onKeyPress",onRejectedCandidateKeyPressed);
	rejectedCandidatesGrid.attachEvent("onRowSelect",onRejectedCandidateRowSelected);
	rejectedCandidatesGrid.attachEvent("onRowDblClicked",onRejectedCandidateRowDoubleClicked);
	rejectedCandidatesGrid.attachEvent("onFilterEnd", onRejectedCandidateFilterEnd);
	//rejectedCandidatesGrid.enableAutoHeight(true,"170");
	rejectedCandidatesGrid.setAwaitedRowHeight(21);
	rejectedCandidatesGrid.setNoHeader(true);
	rejectedCandidatesGrid.init();   
	rejectedCandidatesGrid.enableSmartRendering(true);
	rejectedCandidatesGrid.setHeaderCursor(",,pointer,pointer,pointer,pointer");
	if($('nameFilter')){
		Event.observe($('nameFilter').ancestors()[0], "click",empty);
	}
}

function onRejectedCandidateFilterEnd(){
	rejectedCandidatesOnLoadingEnd();
}

function empty(){
	return false;
}

function filterApplicant(obj){
	rejectedCandidatesGrid.refreshFilters();
	rejectedCandidatesGrid.filterBy(1,obj.value);
	onRejectedCandidateFilterEnd();
}

function onRejectedCandidateKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(rejectedCandidatesGridId,rejectedCandidatesGrid,keyCode,ctrl,shift);
}

function onRejectedCandidateRowSelected(id,idx_col){
	onRowSelected(rejectedCandidatesGridId,rejectedCandidatesGrid,id,idx_col);
}

function onRejectedCandidateRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(rejectedCandidatesGridId,rejectedCandidatesGrid,id,idx_col);
}

//Rejected Candiates Grid loading
function loadRejectedCandidatesGrid(){
	rejectedCandidatesGrid.clearAll();
	var url = "positionSummary.do?mode=getRejectedCandidatesXml&positionId="+positionId;
	rejectedCandidatesGrid.loadXML(url);
}

function rejectedCandidatesOnLoadingEnd(){
	updateFooterText(rejectedCandidatesGrid, 'REJECTED_CANDIDATES_GRID_FOOTER');
}


/**************************End Rejected Candiadates Grid initialisation************************************/
 
/**************************Event Handlers for all grids****************************************/
function onRowSelected(grdId, grdObj, id, idx_col){
	if(grdId!=applicantsGridId){
		applicantsGrid.clearSelection();		
	}
}
function onRowDoubleClicked(grdId, grdObj,id,idx_col){
	if(grdId==applicantsGridId){
		viewApplicant(id);		
	}else if(grdId==latestActivityGridId){
		var interactionId = grdObj.getUserData(id,"interactionId");
		var interactionType = grdObj.getUserData(id,"interactionType");
		var applicantId = grdObj.getUserData(id,"applicantId");
		var interactionIsHidden =  grdObj.getUserData(id,"interactionIsHidden");
		var documentId = grdObj.getUserData(id,"documentId");
		onClickActivity(interactionId,interactionType,applicantId,interactionIsHidden,documentId);
	}
}
function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	//var id=getAllCheckedRows();
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==applicantsGridId){
			viewApplicant(id);		
		}else if(grdId==latestActivityGridId){
			var interactionId = grdObj.getUserData(id,"interactionId");
			var interactionType = grdObj.getUserData(id,"interactionType");
			var applicantId = grdObj.getUserData(id,"applicantId");
			var interactionIsHidden =  grdObj.getUserData(id,"interactionIsHidden");
			var documentId =  grdObj.getUserData(id,"documentId");
			onClickActivity(interactionId,interactionType,applicantId,interactionIsHidden,documentId);
		}
		break;
	case 33:
		//page up
		var idx = grdObj.getRowIndex(id)-pageSize;
		idx = (idx<0)?0:idx;
		grdObj.selectRow(idx);
		break;
	case 34:
		var idx = grdObj.getRowIndex(id)+pageSize;
		idx = (idx>=grdObj.getRowsNum())?grdObj.getRowsNum()-1:idx;
		grdObj.selectRow(idx);
	case 46:
		if(grdId==docGridId){
			onClickDeleteDocument(id);
		}	
		//page down
	}
	return true;
}

function updateFooterText(grd, footerId){
	var footerTxt='';
	var lastRowId = grd.getRowId(grd.getRowsNum()-1);
	var rowCnt = grd.getRowsNum();

	if(rowCnt==1){
		footerTxt = '<bean:message key="position_summary.text.total.candidate" arg0="'+rowCnt+'"  />';
	}else if(rowCnt>globalNum){
		grd.deleteRow(lastRowId);
		rowCnt = grd.getRowsNum();
		footerTxt = '<bean:message key="position_summary.text.total.candidates.limit" arg0="'+rowCnt+'"  />';
	}else if(rowCnt<=globalNum){
		footerTxt = '<bean:message key="position_summary.text.total.candidates" arg0="'+rowCnt+'"  />';
	}
	$(footerId).innerHTML=footerTxt;
}

/**********************************Filter Related Functions*********************/
var sourceFilter = '<%=SelectionProcessConstants.FILTER_SOURCE%>';
var actionReqFilter = '<%=SelectionProcessConstants.FILTER_ACTION%>';
var stepFilter = '<%=SelectionProcessConstants.FILTER_STEP%>';
var userFilter = '<%=SelectionProcessConstants.FILTER_USER%>';
var applicantNameFilter = '<%=SelectionProcessConstants.FILTER_APPLICANT%>';
	
function loadApplicantGridFilters(){
	<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
		loadFilter(sourceFilter);
	<%} %>	
	loadFilter(actionReqFilter);
	loadFilter(stepFilter);
}

function loadRejectedApplicantGridFilters(){
	loadRejectedApplicantGridFilter(userFilter);
	loadRejectedApplicantGridFilter(stepFilter);
}

function loadFilter(filterFor){
	var pars ='mode=populateApplicantGridFilters&filterFor='+filterFor+'&selectedView='+selectedView;
	pars+= getCriteriaQryStringForGrid();
	var myAjax= ajaxCall("selectionProcessFilters.do","get",pars,function (request){renderFilter(request,filterFor)},reportError);	
}

function loadRejectedApplicantGridFilter(filterFor){
	var pars ='mode=populateRejectedApplicantsGridFilters&filterFor='+filterFor+'&selectedView='+selectedView;
	pars+= getCriteriaQryStringForRejectedApplicantsGrid();
	var myAjax= ajaxCall("selectionProcessFilters.do","get",pars,function (request){renderRejectedApplicantGridFilters(request,filterFor)},reportError);	
}

function renderFilter(request,filterFor){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		op = eval(op);
		op = selectAll.concat(op);
		if(filterFor==sourceFilter){
			sourcceSelectBox.reInitialize(op,sourcceSelectBox.getSelectedId());
		} else if(filterFor==stepFilter){
			stepSelectBox.reInitialize(op,stepSelectBox.getSelectedId());			
		} else if(filterFor==actionReqFilter){
			actionSelectBox.reInitialize(op,actionSelectBox.getSelectedId());
		}
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
	}
	return false;
}

function renderRejectedApplicantGridFilters(request,filterFor){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		op = eval(op);
		op = selectAll.concat(op);
		if(filterFor==stepFilter){
			rejectedStepSelectBox.reInitialize(op,rejectedStepSelectBox.getSelectedId());
		} else if(filterFor==userFilter){
			rejectedBySelectBox.reInitialize(op,rejectedBySelectBox.getSelectedId());			
		} 
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
	}
	return false;
}

function resetApplicantGridFilters(){
	$('applicantName').value='';
	stepSelectBox.setSelected(0);
	actionSelectBox.setSelected(0);
	sourcceSelectBox.setSelected(0);
	document.selectionProcessForm.stepLevel.value='<%=PositionConstants.STEP_LEVEL_SHORTLIST+","+PositionConstants.STEP_LEVEL_SELECT+","+PositionConstants.STEP_LEVEL_ACCEPT%>';
	document.selectionProcessForm.selectedUserId.value='';
	applyApplicantGridFilters();
} 
function getCriteriaQryStringForGrid(){
	var qryString = "&positionId="+positionId;
	qryString += "&applicantName="+encodeURIComponent($('applicantName').value);
	qryString += "&stepLevel="+encodeURIComponent(document.selectionProcessForm.stepLevel.value);
	qryString +="&stepName="+encodeURIComponent(stepSelectBox.getText(stepSelectBox.getSelectedIndex()));
	qryString += "&actionRequired="+encodeURIComponent(actionSelectBox.getSelectedId());
	qryString += "&sourceId="+encodeURIComponent(sourcceSelectBox.getSelectedId());
	qryString += "&selectedUserId="+encodeURIComponent(document.selectionProcessForm.selectedUserId.value);
	if(tenthMarksFilter){
		qryString += "&tenthMarksFilter="+tenthMarksFilter;
	}
	if(tenthMarks){
		qryString += "&tenthMarks="+tenthMarks;
	}
	if(twelvethMarksFilter){
		qryString += "&twelvethMarksFilter="+twelvethMarksFilter;
	}
	if(twelvethMarks){
		qryString += "&twelvethMarks="+twelvethMarks;
	}
	if(gradeMarksFilter){
		qryString += "&gradeMarksFilter="+gradeMarksFilter;
	}
	if(gradeMarks){
		qryString += "&gradeMarks="+gradeMarks;
	}
	if(postGradeMarksFilter){
		qryString += "&postGradeMarksFilter="+postGradeMarksFilter;
	}
	if(postGradeMarks){
		qryString += "&postGradeMarks="+postGradeMarks;
	}
	if(ageFilter){
		qryString += "&ageFilter="+ageFilter;
	}
	if(age){
		qryString += "&age="+age;
	}
	if(yearOfExperienceFilter){
		qryString += "&yearOfExperienceFilter="+yearOfExperienceFilter;
	}
	if(yearOfExperience){
		qryString += "&yearOfExperience="+yearOfExperience;
	}
	if(gender){
		qryString += "&gender="+gender;
	}
	if(gapInAcademics){
		qryString += "&gapInAcademics="+gapInAcademics;
	}
	console.log('qryString',qryString);
	return qryString;
}


function getCriteriaQryStringForRejectedApplicantsGrid(){
	var qryString = "&positionId="+positionId;
	qryString += "&rejectedApplicantName="+encodeURIComponent($('rejectedApplicantName').value);
	qryString +="&stepName="+encodeURIComponent(rejectedStepSelectBox.getText(rejectedStepSelectBox.getSelectedIndex()));
	qryString += "&rejectedBy="+encodeURIComponent(rejectedBySelectBox.getSelectedId());
	return qryString;
}

function getCriteriaQryStringForFilters(filter){
	var qryString = "&positionId="+positionId;
	qryString += "&applicantName="+$('applicantName').value;
	qryString += "&stepLevel="+document.selectionProcessForm.stepLevel.value;
	qryString +="&stepName="+stepSelectBox.getText(stepSelectBox.getSelectedIndex());
	qryString += "&actionRequired="+actionSelectBox.getSelectedId();
	qryString += "&sourceId="+sourcceSelectBox.getSelectedId();
	if(filter!=userFilter)
		qryString += "&selectedUserId="+document.selectionProcessForm.selectedUserId.value;
	return qryString;
}
function applyApplicantGridFilters(){
	var params = getCriteriaQryStringForGrid();
	applicantsGrid.clearAll();
	applicantsGrid.loadXML("positionSummary.do?mode=getInprocessApplicantsXml" + params);
	applicantsGrid.clearSelection();
	loadApplicantGridFilters();	
}

function applyRejectedCandidateGridFilters(){
	var params = getCriteriaQryStringForRejectedApplicantsGrid();
	rejectedCandidatesGrid.clearAll();
	rejectedCandidatesGrid.loadXML("positionSummary.do?mode=getRejectedCandidatesXml" + params);
	rejectedCandidatesGrid.clearSelection();
	loadRejectedApplicantGridFilters();	
}

function setStageFilter(){
	var stepLevel= document.selectionProcessForm.stepLevel.value;
	var url = 'selectionProcessFilters.do?mode=stageFilterScreen&stepLevel='+stepLevel;
	window.setTimeout("showInPopUp('"+url+"',350, 200,onStageFilterSet,true);", 10);
}
function onStageFilterSet(retVal){
	document.selectionProcessForm.stepLevel.value = retVal;
	applyApplicantGridFilters();
}
function setUserFilter(){
	var queryParams = getCriteriaQryStringForFilters(userFilter);
	queryParams += '&filterFor='+userFilter;
	queryParams += '&selectedView='+selectedView;
	var selectedUserId = document.selectionProcessForm.selectedUserId.value;
	var url = 'selectionProcessFilters.do?mode=userFilterScreen&params='+escape(queryParams)+'&selectedUserId='+selectedUserId;
	window.setTimeout("showInPopUp('"+url+"',580, 230,onUserFilterSet,true);", 10);
}
function onUserFilterSet(retval){
	document.selectionProcessForm.selectedUserId.value=retval;
	applyApplicantGridFilters();
}
/**************************Filter Related ENDS*****************************************/


/**************************Navigation Functions****************************************/
function viewDetails(){
	window.location.href=uncache("position.do?mode=description&positionId="+positionId);
}

/**************************Applicant Related Functions****************************************/
function onClickApplicant(id){
	viewApplicant(id);
}
function viewApplicant(aId){
	url = "selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId;
	window.open(url,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	//showPopWin(url, 1024, 768, showPositionClicked, true); 
}

/*function showPositionClicked(returnVal){
	window.location = "position.do?mode=description&positionId="+returnVal;
}*/

function onClickActivity(interactionId, interactionType, applicantId, interactionIsHidden,documentId){	
	if(interactionIsHidden==<%=SelectionProcessConstants.INTERACTION_HIDE%>){
		<logic:equal value="true" name="permissionSet" scope="session" property="DO_NOT_SHOW_CONFIDENTIAL_DATA">
		return;
		</logic:equal>
	}
	if(interactionType==<%=SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED%> || interactionType==<%=SelectionProcessConstants.INTERACTION_EMAIL_SENT%>){
		var url = 'inbox.do?mode=viewEmail&emailId='+interactionId+'&applicantId=' + applicantId + '&emailLocation=<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>';
		window.setTimeout("showInPopUp('"+url+"',800,650,loadGrids,true);", 10);	
		//win = window.open('','_newEmail','width=800,height=600,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_PHONE%> || interactionType=="<%=SelectionProcessConstants.INTERACTION_NOTE%>"){
		var url = 'selectionProcess.do?mode=viewPhoneLog&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType;
		if(documentId == undefined || documentId == '') {
			window.setTimeout("showInPopUp('"+url+"',550, 320,loadGrids,true);", 10);
		} else {
			window.open(url);
		}
		//win = window.open('selectionProcess.do?mode=viewPhoneLog&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType,'_newPhone','width=520,height=300,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_INTERVIEW%>){
		var url = 'selectionProcess.do?mode=viewInterviewLog&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType;
		window.setTimeout("showInPopUp('"+url+"',900,600,loadGrids,true);", 10);
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
		window.setTimeout("showInPopUp('"+url+"',550, 320,null,true);", 10);	
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_IMPORT%> ){
		var url = "selectionProcess.do?mode=viewOriginalResume&applicantId=" + applicantId;
		window.open(url,applicantId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');		
	}
	return;
}
function editAppointment(returnVal){
	if(returnVal){
		window.location=returnVal;
	}
}

function getAllCheckedRows(){
/* 	var id='';
	applicantsGrid.forEachRow(function(rowId){
  		var cell=applicantsGrid.cells(rowId,0);
  		var value=applicantsGrid.cellById(rowId,0).getValue();
  		if(value==1){
  			if(id){
  				id=id+","+rowId;
  			}else{
  				id=rowId;
  			}
  			
  		}
  		})
  		return id; */
  		return applicantsGrid.getCheckedRows(0);
}
function moveUpOrDown() {
	
	/* applicantsGrid.forEachRow(function(rowId){
  		var cell=applicantsGrid.cells(rowId,0);
  		var value=applicantsGrid.cellById(rowId,0).getValue();
  		//var st=state
  		alert('rowid is'+rowId+'value'+value);
  		}) */
  		
  		var selId=getAllCheckedRows();
		//alert('seId'+seId);
  		
	//var seId = applicantsGrid.getSelectedId();
	//alert('selId'+selId);
	if (selId) {
		var ids=selId.split(",");
		var singleFeedback = false;
		if(ids.length<2){		
			showMoveUpDownScreen(selId);
		}else{
			<%if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ALLOW_BULK_FEEDBACK).equals("1")){%>
				moveUpOrDownAll(selId);	
			<%}else{%>
				alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
			<%}%>
		}
	} else {
		alert('<bean:message key="applicant_home.error.select_applicant_to_move_upOrDown"/>');
	} 
}

function showMoveUpDownScreen(id){
	var url = 'selectionProcess.do?mode=moveApplicantUpOrDown&applicantId='+id;
	window.setTimeout("showInPopUp('"+url+"',900, 570,loadGrids,true);", 10);
}

function moveUpOrDownAll(ids) {
	var url = 'selectionProcess.do?mode=selectBulkAction';
	window.setTimeout("showInPopUp('"+url+"',270, 170, showBulkFeedback, true);", 10);
}

function showBulkFeedback(returnVal){
	//var ids = applicantsGrid.getSelectedId();
	 var ids = getAllCheckedRows();
	var url = 'selectionProcess.do?mode=bulkMoveApplicantUpOrDown&applicantId='+ids+'&tab=<%=SelectionProcessConstants.TAB_SELECT%>';
	url += '&screenType='+returnVal;
	window.location=url;
}
function changeStatus() {
	//var selId = applicantsGrid.getSelectedId();
	var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var url = 'selectionProcess.do?mode=changeStatus&applicantId=' +selId;
			window.setTimeout("showInPopUp('"+url+"',550,250,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	} 
}
function setAppointment() {
	//var selId = applicantsGrid.getSelectedId();
	var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var pars = "mode=getPositionStepSchedulable&applicantId=" + selId;
		  	var myAjax = ajaxCall("selectionProcess.do",'get',pars,checkErrors, reportError);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		 alert('<bean:message key="applicant_home.error.select_applicant_to_set_appointment"/>');  
	}  
}
function checkErrors(request) {
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    	return;
  	}	
	var errors = xmlFile.getElementsByTagName("errors")[0];
	error = getSingleElement(errors, "error", "");		
	if (error == '') {
		window.location="calendar.do?mode=calendarHome&selectedApplicant=" + applicantsGrid.getCheckedRows(0);
	} else {
		alert(error);
	}
}
function addNote(){
	//var selId = applicantsGrid.getSelectedId();
	var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var url = 'selectionProcess.do?mode=addPhoneLog&applicantId=' +selId+'&communicationType=<%=SelectionProcessConstants.INTERACTION_NOTE%>';
			window.setTimeout("showInPopUp('"+url+"',550,250,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	} 
}
function showMenuFlag(){
	//var selId = applicantsGrid.getSelectedId();
		var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var selectedFlags = applicantsGrid.getUserData(selId,"flags");
			var url = 'selectionProcess.do?mode=setFlag&applicantId=' + selId + '&selectedIds=' + selectedFlags;
			window.setTimeout("showInPopUp('"+url+"',550,320,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		alert("<bean:message key='select.error.select_applicant_to_set_flag' />");
	}  	
}
function showMessageBox(){
	//var selId = applicantsGrid.getSelectedId();
		var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var url = 'selectionProcess.do?mode=addMessage&applicantId=' +selId;
			window.setTimeout("showInPopUp('"+url+"',610,350,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		alert('<bean:message key="applicant_listing.label.select_applicant"/>');	
	} 
}
function sendSMS() {
	//var selId = applicantsGrid.getSelectedId();
		var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var url = 'selectionProcess.do?mode=sendSMS&applicantId='+selId;
			window.setTimeout("showInPopUp('"+url+"',450, 320,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		 alert('<bean:message key="applicant_home.error.select_applicant_to_send_sms"/>');
	}
}
function addPhone(){
	//var selId = applicantsGrid.getSelectedId();
	var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var url = 'selectionProcess.do?mode=addPhoneLog&applicantId=' +selId+'&communicationType=<%=SelectionProcessConstants.INTERACTION_PHONE%>';
			window.setTimeout("showInPopUp('"+url+"',550,320,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	}  
}
function newEmail(){
	//var selId = applicantsGrid.getSelectedId();
		var selId=getAllCheckedRows();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var url = 'inbox.do?mode=newEmail&newEmailType=<%=InboxConstants.EMAIL_TYPE_NEW%>&emailLocation=<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>&applicantId='+selId;
			window.setTimeout("showInPopUp('"+url+"',810, 513,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		 alert('<bean:message key="applicant_home.error.select_applicant_to_send_email"/>');
	} 
}
function massMail(){
	var params = getCriteriaQryStringForGrid();
	var url = "selectionProcess.do?mode=massEmailSelect" + params;
	window.setTimeout("showInPopUp('"+url+"',800,550,null,true);", 10);
}
function forwardResumes(){
	//var selId = applicantsGrid.getSelectedId();
	var selId=getAllCheckedRows();
	if (selId) {
		var url = 'inbox.do?mode=forwardResumes&newEmailType=<%=InboxConstants.EMAIL_TYPE_FORWARD_RESUME%>&emailLocation=<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>&applicantId='+selId;
		window.setTimeout("showInPopUp('"+url+"',810, 513,loadGrids,true);", 10);
	}else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	} 
}
function exportToExcel(){
	var url = "export.do?mode=exportPositionSummaryApplicants&ids="+applicantsGrid.getCheckedRows(0);
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}

function exportRejectedCandidatesToExcel(){
	var params = getCriteriaQryStringForRejectedApplicantsGrid();
	var url = "export.do?mode=exportRejectedApplicants&ids="+rejectedCandidatesGrid.getAllItemIds()+ params;
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}

function exportAppliedCandidatesToExcel(){
	var url = "export.do?mode=exportSelectApplicants&ids="+appliedCandidatesGrid.getAllItemIds();
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}
/******************SORTING RELATED FUNCTION*****************************/
function sortGridRows(gridObj, column, elemIds,sortFunc) {
	var type = gridObj.getSortingState();
	if (type[0] == column) {
		if (type[1] == 'asc') {
			gridObj.sortRows(column, sortFunc, "desc");
			gridObj.setSortImgState(true,column,"desc");
		} else {
			gridObj.sortRows(column, sortFunc, "asc");
			gridObj.setSortImgState(true,column,"asc");
		}
		
		for (var i = 0; i < elemIds.length; i++) {
			if (i == (column - 1)) {
				if (type[1] == 'asc') {
					document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_desc.gif" />';
				} else {
					document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_asc.gif" />';
				}				
			} else {
				document.getElementById(elemIds[i]).innerHTML='';
			}
		}
	} else {
		gridObj.sortRows(column, sortFunc, "asc");
		gridObj.setSortImgState(true,column,"asc");
		for (var i = 0; i < elemIds.length; i++) {
			if (i == (column - 1)) {
				document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_asc.gif" />';			
			} else {
				document.getElementById(elemIds[i]).innerHTML='';
			}
		}
	}
}
function sort_name(a,b,order,aId,bId) {
	a0 = applicantsGrid.getUserData(aId,"name");
	b0 = applicantsGrid.getUserData(bId,"name");	
	return sort_data(a0,b0,order);
}
function sort_source(a,b,order,aId,bId) {
	a0 = applicantsGrid.getUserData(aId,"source");
	b0 = applicantsGrid.getUserData(bId,"source");	
	return sort_data(a0,b0,order);
}
function sort_step(a,b,order,aId,bId) {
	a0 = applicantsGrid.getUserData(aId,"step");
	b0 = applicantsGrid.getUserData(bId,"step");	
	return sort_data(a0,b0,order);
}

function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function custom_date_sort(a,b,order,a0,b0){
	a0 = getCustomDate(latestActivityGrid.getUserData(a0,"date"));
	b0 = getCustomDate(latestActivityGrid.getUserData(b0,"date"));		
	if (order=="asc")
		return (a0>b0)?1:-1;
	else
		return (a0<b0)?1:-1;
}
function getCustomDate(a){
	var yr = a.substring(0,4);
	var mo = a.substring(5,7);
	var dd = a.substring(8,10);
	var hh = a.substring(11,13);
	var mm = a.substring(14,16);
	var ss = a.substring(17,19);
	var newdate=new Date(yr,mo-1,dd,hh,mm,ss);
	return newdate;
}
function sort_by(a,b,order,aId,bId) {
	a0 = latestActivityGrid.getUserData(aId,"owner");
	b0 = latestActivityGrid.getUserData(bId,"owner");	
	if(order=="asc") {
		if (a.toLowerCase()>b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()<b.toLowerCase()) {
			return -1;
		}
	} else {
		if (a.toLowerCase()<b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()>b.toLowerCase()) {
			return -1;
		}
	}
	return custom_date_sort(a,b,"desc",aId,bId);
}
function sort_applicant(a,b,order,aId,bId) {
	a0 = latestActivityGrid.getUserData(aId,"applicantName");
	b0 = latestActivityGrid.getUserData(bId,"applicantName");	
	if(order=="asc") {
		if (a.toLowerCase()>b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()<b.toLowerCase()) {
			return -1;
		}
	} else {
		if (a.toLowerCase()<b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()>b.toLowerCase()) {
			return -1;
		}
	}
	return custom_date_sort(a,b,"desc",aId,bId);
}
/**********************************COMMON FUNCTIONS*********************/
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function viewPositionSummary(positionId){
	window.location.href=uncache("position.do?mode=positionSummary&positionId="+positionId);
}


/************CANDIDATE APPLIED GRID******/
var appliedCandidatesGrid = null;
var appliedCandidatesGridId = 'CANDIDATES_APPLIED_GRID';
function initAppliedCandidatesGrid(){
	appliedCandidatesGrid = new dhtmlXGridObject(appliedCandidatesGridId); 
	appliedCandidatesGrid.imgURL = "images/"; 
	appliedCandidatesGrid.setHeader("#master_checkbox,<bean:message key="candidates.name.applied"/>,<bean:message key="candidates.source.applied"/>,<bean:message key="candidates.source.applied_date"/>");
	appliedCandidatesGrid.setInitWidths("35,259,259,185");
	appliedCandidatesGrid.setColAlign("left,left,left,left");
	appliedCandidatesGrid.setColTypes("ch,link,ro,ro"); 
	appliedCandidatesGrid.setColSorting("na,str,str,date");	       	       
	appliedCandidatesGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	appliedCandidatesGrid.attachEvent("onKeyPress",onAppliedCandidatesKeyPressed);
	appliedCandidatesGrid.attachEvent("onRowSelect",onAppliedCandidatesRowSelected);
	appliedCandidatesGrid.attachEvent("onRowDblClicked",onAppliedCandidatesRowDblClicked);
	appliedCandidatesGrid.attachEvent("onMouseOver",function(){});
	//rejectedCandidatesGrid.enableAutoHeight(true,"170");
	appliedCandidatesGrid.setAwaitedRowHeight(15);
	appliedCandidatesGrid.setNoHeader(false);
	appliedCandidatesGrid.init();   
	appliedCandidatesGrid.enableSmartRendering(true);
	appliedCandidatesGrid.setHeaderCursor("pointer,pointer");
	loadAppliedCandidatesGrid();
}
function toggleCandidatesApplied(){
	if($('candidatesApplied') && $('candidatesApplied').src.indexOf('images/ico_down.gif')!=-1){
		showCandidatesAppliedGrid();
	}else{
		hideCandidatesAppliedGrid();
	}
}

function showCandidatesAppliedGrid(imgId){
	$('candidatesApplied').src = "images/ico_down1.gif";
	//$('CANDIDATES_APPLIED_GRID_FOOTER').show();
	$('CANDIDATES_APPLIED_GRID').show();
	loadAppliedCandidatesGrid();	
}

function hideCandidatesAppliedGrid(imgId){
	$('candidatesApplied').src = "images/ico_down.gif";
	//appliedCandidatesGrid.clearAll();
	//$('CANDIDATES_APPLIED_GRID_FOOTER').hide();
	$('CANDIDATES_APPLIED_GRID').hide();
}

function loadAppliedCandidatesGrid(){
	var url = "positionSummary.do?mode=getCandidatesAppliedForPosition"+getCriteriaQryStringForAppliedForGrid();
	appliedCandidatesGrid.loadXML(url);
}

function getCriteriaQryStringForAppliedForGrid(){
	var qryString = "&positionId="+positionId;
	if(tenthMarksFilter){
		qryString += "&tenthMarksFilter="+tenthMarksFilter;
	}
	if(tenthMarks){
		qryString += "&tenthMarks="+tenthMarks;
	}
	if(twelvethMarksFilter){
		qryString += "&twelvethMarksFilter="+twelvethMarksFilter;
	}
	if(twelvethMarks){
		qryString += "&twelvethMarks="+twelvethMarks;
	}
	if(gradeMarksFilter){
		qryString += "&gradeMarksFilter="+gradeMarksFilter;
	}
	if(gradeMarks){
		qryString += "&gradeMarks="+gradeMarks;
	}
	if(postGradeMarksFilter){
		qryString += "&postGradeMarksFilter="+postGradeMarksFilter;
	}
	if(postGradeMarks){
		qryString += "&postGradeMarks="+postGradeMarks;
	}
	if(ageFilter){
		qryString += "&ageFilter="+ageFilter;
	}
	if(age){
		qryString += "&age="+age;
	}
	if(yearOfExperienceFilter){
		qryString += "&yearOfExperienceFilter="+yearOfExperienceFilter;
	}
	if(yearOfExperience){
		qryString += "&yearOfExperience="+yearOfExperience;
	}
	if(gender){
		qryString += "&gender="+gender;
	}
	if(gapInAcademics){
		qryString += "&gapInAcademics="+gapInAcademics;
	}
	console.log('qryString',qryString);
	return qryString;
}

function onAppliedCandidatesKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(appliedCandidatesGridId,appliedCandidatesGrid,keyCode,ctrl,shift);
}

function onAppliedCandidatesRowSelected(id,idx_col){
	onRowSelected(appliedCandidatesGridId,appliedCandidatesGrid,id,idx_col);
}

function onAppliedCandidatesRowDblClicked(id,idx_col){
	onRowDoubleClicked(appliedCandidatesGridId,appliedCandidatesGrid,id,idx_col);
}



</script>