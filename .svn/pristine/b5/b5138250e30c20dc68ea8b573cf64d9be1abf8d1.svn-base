<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.BitSet, 
				java.util.List,java.util.ArrayList,
				java.lang.Integer,
				com.talentPool.user.UserConstants,
				com.talentPool.positions.dataobject.PositionData,
				com.talentPool.common.NavigationConstants,
				com.talentPool.selectionProcess.SelectionProcessConstants,
				com.talentPool.inbox.InboxConstants,
				com.talentPool.common.properties.GlobalApplicationProperties,
                com.talentPool.common.properties.GlobalConstants,
				com.talentPool.common.utils.CommonUtils,
				com.talentPool.selectionProcess.form.SelectionProcessForm,
				com.talentPool.common.db.SimpleDataObject,
				com.talentPool.common.properties.TPApplicationProperties,
				com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.positions.PositionConstants"%>				

<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@page import="com.talentPool.applicant.ApplicantConstants"%><style>
div.gridbox table.row20px tr  td{
	  height:35px;
    white-space: nowrap;
    padding:0px 4px 0px 6px;
}
</style>
<link rel="stylesheet" type="text/css" href="themes/default/recentActivities.css"/>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/criteriapane.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script src="js/cookies.js"></script>
<bean:define id="positionFilters" name="positionFilters" scope="request" type="java.util.List"/>
<bean:define id="stepTitles" name="stepTitles" scope="request" type="java.util.List"/>

<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.config.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/box.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/yahoo-dom-event.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/tip_ajaxcall.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">	

<%
	SelectionProcessForm sForm = (SelectionProcessForm)request.getAttribute("selectionProcessForm");
    String userId = (String)request.getSession(false).getAttribute("userId");
%>   
<script>
//Global Count for grid
var globalNum = '<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_GRID_RESULT_PAGE_SIZE)%>'


/*Menu Script*/
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

var mnu_fwd = new TpMenu({type:'menu', id: 'fwd',image:'images/ico_forward_resume.gif', title:'<bean:message key="common.forward" /> <bean:message key="common.resumes" />', onclick:'onClickMenu'});
menu.addItem(mnu_fwd);

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_GENERATE_OFFER">
var mnu_offer = new TpMenu({type:'menu', id: 'offer',image:'images/ico_print_offer.gif', title:'<bean:message key="generate_offer_sheet.label.generate_offer" />', onclick:'onClickMenu'});
menu.addItem(mnu_offer);
</logic:equal>

var mnu_print_feedback = new TpMenu({type:'menu', id: 'printFeedback',image:'images/ico_print.gif', title:'<bean:message key="hire.label.print_feedback" />', onclick:'onClickMenu'});
menu.addItem(mnu_print_feedback);

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BLACKLIST_APPLICANT">
	var mnu_blackList = new TpMenu({type:'menu', id: 'blackList', image:'images/ico_blacklist.gif',title:'<bean:message key="black_list.label.blackList" />', onclick:'onClickMenu'});
	menu.addItem(mnu_blackList);
</logic:equal>

var mnuhandlermore = new TpMenuHandler(menu,{});
mnuhandlermore.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:0});

function showMenuMore(applicantId, elementId){
	<%if(ModuleSet.isMODULE_SMS()){ %>
	if(smsEnabled){
		var mobile = ''+dataGrid.getUserData(applicantId,"mobile");			
		if(mobile == ''){
			mnuhandlermore.disableMenuItem(mnu_sms.getId(), true);
		}else{
			mnuhandlermore.disableMenuItem(mnu_sms.getId(), false);
		}
	}
	<% } %>
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
	}else if(id == "fwd"){
		forwardResumes();
	}else if(id == "offer"){
		generateOfferSheet();
	}else if(id == "printFeedback"){
		printFeedback();
	}else if(id=="blackList"){
		blackListApplicant();
	}
}

function generateOfferSheet() {
	var selId = dataGrid.getSelectedId();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){		
			var url = 'manageApplicantOfferGeneration.action?applicantId='+selId;
			window.setTimeout("showInPopUp('"+url+"',400,210,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		 alert('<bean:message key="hire.error.select_applicant"/>');
	} 
}

function printFeedback(){
	var selId = dataGrid.getSelectedId();
	var params = null;
	if (selId) {
		params = '&applicantId='+selId;
		params+= '&positionId='+dataGrid.getUserData(selId,"positionId");
		var url = 'selectionProcess.do?mode=printFeedback'+params;
		printUrl(url);
	}else {
		 alert('<bean:message key="hire.error.select_applicant"/>');
	} 
}

function printUrl(url){
	var printWin = window.open(url,"_blank","height=500,left=100,top=100,width=800,toolbar=no,titlebar=0,status=0,menubar=no,location= no,scrollbars=1");
	//printWin.print();	
}

function showMenuFlag(){
	var selId = dataGrid.getSelectedId();
	if (selId) {		
		var selectedFlags = dataGrid.getUserData(selId,"flags");
		var url = 'selectionProcess.do?mode=setFlagToApplicants&applicantIds=' + selId + '&selectedIds=' + selectedFlags;
		window.setTimeout("showInPopUp('"+url+"',550,320,loadGrids,true);", 10);
	} else {
		alert("<bean:message key='select.error.select_applicant_to_set_flag' />");
	} 
}
</script>
<script>
var levelStyles = new Array();
levelStyles[0]="Level_0";
levelStyles[1]="Level_1";

var positions = new Array();
var steps = new Array();
var durations = new Array();

positions[positions.length]=new SelectOption('-1','<bean:message key="common.positions_all"/>');
steps[steps.length]=new SelectOption('0','<bean:message key="hire.label.all_stages"/>');
durations[durations.length]=new SelectOption('-1','<bean:message key="hire.label.all"/>');
durations[durations.length]=new SelectOption('30','<bean:message key="hire.label.last_30_days"/>');
durations[durations.length]=new SelectOption('90','<bean:message key="hire.label.last_90_days"/>');

<%
	ArrayList deptIds = new ArrayList();
	ArrayList deptNames = new ArrayList();
	CommonUtils.populateIdsAndNames((ArrayList)positionFilters, deptIds, deptNames, "deptId", "deptName", null);
%>
var options = <%=CommonUtils.getListJavaScriptArray(deptIds,deptNames)%>;
<%
	for (int i = 0; i < deptIds.size(); i++) {
		SimpleDataObject obj = (SimpleDataObject) positionFilters.get(i);
		ArrayList positionIds = new ArrayList();
		ArrayList positionNames = new ArrayList();
		CommonUtils.populateIdsAndNames((ArrayList)obj.getAttribute("children"), positionIds, positionNames, "positionId", "positionTitle", null);
%>	
		options[<%=i%>].setId('d_' + options[<%=i%>].getId());
		children=<%=CommonUtils.getListJavaScriptArray(positionIds,positionNames)%>;
		
		for (indx = 0; indx < children.length; indx++) {			
			children[indx].setLevel(1);
		}
		options[<%=i%>].setChilds(children); 
<%
	}
%>
positions = positions.concat(options);

<%
	ArrayList stepIds = new ArrayList();
	ArrayList stepNames = new ArrayList();
	CommonUtils.populateIdsAndNames((ArrayList)stepTitles, stepIds, stepNames, "stepTitle", "stepTitle", null);
%>
var options = <%=CommonUtils.getListJavaScriptArray(stepIds,stepNames)%>;
steps = steps.concat(options);
steps[steps.length]=new SelectOption('<%=SelectionProcessConstants.STEP_ON_HOLD%>','<bean:message key="select.label.on_hold"/>');

function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	var tip="";
	if(grdId == datagrid){
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
	}	else if (grdId == datagrid_latest_activities) {
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
	} else if (grdId == joined_applicant_grid) {
			switch(obj.cell._cellIndex){
				case 0:
					return "";
					//return obj.grid.getUserData(obj.cell.parentNode.idd,"name");
					break;
				case 1:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"position");
					break;
			}	
	}
	return obj.cell.innerHTML;
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
<html:form action="/selectionProcess">
<html:hidden property="departmentId" name="selectionProcessForm"/>
<html:hidden property="positionId" name="selectionProcessForm"/>
<html:hidden property="stepName" name="selectionProcessForm"/>
<html:hidden property="stepLevel" name="selectionProcessForm"/>
<html:hidden property="stepId" name="selectionProcessForm"/>
<html:hidden property="locationTitle" name="selectionProcessForm"/>
<html:hidden property="positionTypeExtInt" name="selectionProcessForm"/>

<html:hidden property="filterByPosition" name="selectionProcessForm"/>
  <div class="contentDiv">
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
			<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SET_FLAG">
	  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
	  		<a href="#" style="width:85px;" onclick="javascript: showMenuFlag();" id="flags"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_set_flag.gif" width="10" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="select.label.set_flag"/></a> <img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
	  		</logic:equal>
	  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
	  		<a href="#" style="width:65px;" onmouseover="javascript: showMenuMore(dataGrid.getSelectedId(),'more');" id="more"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_more.gif" width="14" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="select.label.more"/></a>
	  		<a href="#" style="text-align:right;background:none;padding-left: 82px;" onmouseover="changeImage('exportToExl', 'images/excel_co.GIF')" onmouseout="changeImage('exportToExl', 'images/excel_bw.GIF')">
				<img src="images/excel_bw.GIF" width="16" height="16" style="border:0px;" id="exportToExl" onclick="javascript:exportToExcel(); return false;" />
			</a>
		</div>
		 	<br>
	  	<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="gridborder" style="border-bottom: 0px;">
					<div id="GRD_APPLICANT" style="width:738px;height:290px;"></div>
				</td>
			</tr>
		</table>
	 	<div class="outerDiv" style="margin: 0px; padding: 0px;border-top:1px dashed #C4C4C4;background-color:#f9f9f9;font-weight:bold;">
			<table style="border: 0px; height: 20px;" cellpadding="0" cellspacing="0">
					<tr>
						<td style="width:18px;" class=""></td>        
						<td id="total" style="color:#666;" class="">Total: 0</td>       
					</tr>
			</table>
		</div> 		
		<br/><br/>
			
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHOW_JOINED_CANDIDATES">			
			<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
				<tr> 
				  <td width="50%">
				  	<div style="width:140px;" class="boxTab">
				  		<span class="rightC"></span><span class="leftC"></span>&nbsp;
				  		<bean:message key="hire.label.joined_candidates" />
				  	</div>
				  </td>
				<td><div style="width:30px; float:right; text-align:center;cursor:pointer; " class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_down.gif" name="btnImg1" width="13" height="13" vspace="3" id="joinedGridImg" onclick="toggleJoinedGrid(this.id,'GRD_JOINED_APPLICANT');return false;" /></div></td>
				</tr>
			</table>
			<table class="boxHeaderGrey" cellspacing="0" cellpadding="0" border="0" style="border-bottom:0px;">
		  		<tr style="height: 18px;">				  			
		  			<td class="head" style="width:194px; " onclick="javascript: sortGridRows2(joinedApplicantGrid, 0, sort_img_ids00);">
		  				<bean:message key="hire.label.name" />&nbsp;&nbsp;&nbsp;&nbsp;
		  				<span id="sort_img_00"></span>
		  			</td>
		  			<td class="head" style="width:385px;" onclick="javascript: sortGridRows2(joinedApplicantGrid, 1, sort_img_ids00);">
		  				<bean:message key="common.position" />&nbsp;&nbsp;&nbsp;&nbsp;
		  				<span id="sort_img_10"></span>
		  			</td>
		  			<td class="head" style="width:135px;" onclick="javascript: sortGridRows2(joinedApplicantGrid, 2, sort_img_ids00);">
		  				<bean:message key="hire.label.joining_date" />
		  				<span id="sort_img_20"></span>
		  			</td>
		  		</tr>				  		
		  		<tr style="height: 25px;margin-bottom: 10px;">				  			
		  			<td class="head" style="width:194px;cursor:default;">
		  				<input type="text" id="nameFilter0" name="nameFilter0" style="width:85px;" value="Search name" onKeyUp="applyFilters0();" onFocus="document.selectionProcessForm.nameFilter0.value=''"/>
		  			</td>
		  			<td  class="head" style="width:385px;cursor:default;">
		  				<script>						  				
								pFilters0 = new SelectBox(positions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'124px', size:5, oStyles:levelStyles});
								pFilters0.setOnChangeHandler('applyFilters0');
								document.write(pFilters0.getHtml());
								pFilters0.init();
							</script>
		  			</td>
		  			<td class="head" style="width:135px;cursor:default;">
		  				<script>
		  					var selectedOption = '30';
		  					if (document.selectionProcessForm.filterByPosition.value != '') {
		  						selectedOption = '-1';
		  					}
								sFilters0 = new SelectBox(durations,selectedOption,'images/btn_dropdown.gif',{namesonly:false, width:'124px', size:5});
								sFilters0.setOnChangeHandler('applyFilters0');
								document.write(sFilters0.getHtml());
								sFilters0.init();
							</script>
		  			</td>
		  		</tr>
		  	</table>
		  	<table cellpadding="0" cellspacing="0" id="GRD_JOINED_APPLICANT_TABLE" style="display: none;" >
				<tr>
					<td class="gridborder" style="border-bottom: 0px;">
						<A NAME="JOINED"></A><!-- Anchor for link coming from dashboard -->
					  	<div id="GRD_JOINED_APPLICANT" style="width:738px;height:170px;"></div>
					</td>
				</tr>
			</table>
			<div class="outerDiv" id="GRD_JOINED_APPLICANT_TOTAL" style="margin: 0px; padding: 0px;border-top:1px dashed #C4C4C4;background-color:#f9f9f9;font-weight:bold; display: none;">
				<table style="border: 0px; height: 20px;" cellpadding="0" cellspacing="0">
						<tr>
							<td style="width:18px;" class=""></td>        
							<td id="totaljoin" style="color:#666;" class="">Total: 0</td>       
						</tr>
				</table>
			</div> 
			<br/><br/>
	  	</logic:equal>

		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
			  <td width="50%">
			  	<div style="width:140px;" class="boxTab">
			  		<span class="rightC"></span><span class="leftC"></span>&nbsp;
			  		<bean:message key="hire.latest_activity.label.latest_activities" />
			  	</div>
			  </td>
			<td><div style="width:30px; float:right; text-align:center;cursor:pointer; " class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_down.gif" name="btnImg1" width="13" height="13" vspace="3" id="latestGridImg" onclick="toggleGrid(this.id,'GRD_LATEST_ACTIVITY');return false;" /></div></td>		  
			</tr>
			<tr> 
			  <td width="100%" colspan="2">
			  	<table cellpadding="0" cellspacing="0">
					<tr>
						<td class="gridborder">
							<div id="GRD_LATEST_ACTIVITY" style="width:738px;height:170px;"></div>
						</td>
					</tr>
				</table>
				<br/><br/>
			  </td>		  
			</tr> 
		</table>			
  </div> 
</html:form>
<script language="javascript">
var pageSize=10;
var dataGrid;
var joinedApplicantGrid;
var myGrid;

var sort_img_ids = new Array();
sort_img_ids[sort_img_ids.length] = 'sort_img_0';
sort_img_ids[sort_img_ids.length] = 'sort_img_1';
sort_img_ids[sort_img_ids.length] = 'sort_img_2';
sort_img_ids[sort_img_ids.length] = 'sort_img_3';

var sort_img_ids00 = new Array();
sort_img_ids00[sort_img_ids00.length] = 'sort_img_00';
sort_img_ids00[sort_img_ids00.length] = 'sort_img_10';
sort_img_ids00[sort_img_ids00.length] = 'sort_img_20';

function doOnLoad() {	
	initPopUp();
		
    dataGrid = new dhtmlXGridObject('GRD_APPLICANT'); 
    dataGrid.imgURL = "images/"; 
    dataGrid.setHeader("&nbsp;,<bean:message key="hire.label.name" />,<bean:message key="common.position" />,<bean:message key="hire.label.stage_status" />,<bean:message key="hire.label.joining_date" />"); 
    dataGrid.setInitWidths("18,156,192,226,124");
    dataGrid.setColAlign("left,left,left,left,left");
    dataGrid.setColTypes("link,ro,ro,ro,ro"); 
    dataGrid.setColSorting("na,sort_name,sort_dept_pos,sort_stage,custom_date_sort00");	    
	dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	dataGrid.attachEvent("onXLE",dataGridOnLoadingEnd);
	dataGrid.attachEvent("onKeyPress",onApplicantKeyPressed);
	dataGrid.attachEvent("onRowSelect",onApplicantRowSelected);
	dataGrid.attachEvent("onRowDblClicked",onApplicantRowDoubleClicked);
	dataGrid.attachEvent("onAfterSorting",onDataGridAfterSorting);	
	//dataGrid.setNoHeader(true);
    dataGrid.enableResizing("false,false,false,false,false");
    dataGrid.setAwaitedRowHeight(36);   
    dataGrid.init();     
    dataGrid.setHeaderCursor(",pointer,pointer,pointer,pointer"); 
    dataGrid.enableMultiselect(true);
    dataGrid.enableSmartRendering(true);
    dataGrid.setSortImgState(true,1,"asc");    
    window.setTimeout("loadApplicantGrid()", 2);	
  	
  	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHOW_JOINED_CANDIDATES">   
  	joinedApplicantGrid = new dhtmlXGridObject('GRD_JOINED_APPLICANT'); 
    joinedApplicantGrid.imgURL = "images/"; 
    joinedApplicantGrid.setHeader("<bean:message key="hire.label.name" />,<bean:message key="common.position" />,<bean:message key="hire.label.joining_date" />"); 
    joinedApplicantGrid.setInitWidths("200,390,127");
    joinedApplicantGrid.setColAlign("left,left,left");
    joinedApplicantGrid.setColTypes("link,ro,ro"); 
    joinedApplicantGrid.setColSorting("sort_II_name,sort_dept_pos0,custom_date_sort0");	
	joinedApplicantGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	joinedApplicantGrid.attachEvent("onXLE",joinedApplicantGridOnLoadingEnd);
	joinedApplicantGrid.attachEvent("onKeyPress",onJoinedApplicantKeyPressed);
	joinedApplicantGrid.attachEvent("onRowSelect",onJoinedApplicantRowSelected);
	joinedApplicantGrid.attachEvent("onRowDblClicked",onJoinedApplicantRowDoubleClicked);
	joinedApplicantGrid.attachEvent("onAfterSorting",onDataGrid2AfterSorting);
    joinedApplicantGrid.setNoHeader(true);
    //joinedApplicantGrid.enableAutoHeight(true,"170");
    joinedApplicantGrid.enableResizing("false,false,false");
    joinedApplicantGrid.setSkin("gray"); 
    joinedApplicantGrid.init();    
    joinedApplicantGrid.setHeaderCursor("pointer,pointer,pointer");  
    joinedApplicantGrid.setSortImgState(true,2,"desc");
    document.getElementById(sort_img_ids00[2]).innerHTML='<img src="images/sort_desc.gif" />';			       
    if (document.selectionProcessForm.filterByPosition.value != "") {
    	pFilters0.setSelected(pFilters0.getIndexWithId(document.selectionProcessForm.filterByPosition.value));
    } else {    	
		//applyFilters0();
  	} 
  	</logic:equal>
  	
  	
    myGrid = new dhtmlXGridObject('GRD_LATEST_ACTIVITY'); 
    myGrid.imgURL = "images/"; 
    myGrid.setHeader("<bean:message key="hire.latest_activity.label.date" />,<bean:message key="hire.latest_activity.label.by" />,<bean:message key="hire.latest_activity.label.candidate" />,<bean:message key="hire.latest_activity.label.action" />,"); 
    myGrid.setInitWidths("148,80,150,330,0");
    myGrid.setColAlign("left,left,left,left,left");
    myGrid.setColTypes("ro,ro,link,link,co"); 
    myGrid.setColSorting("custom_date_sort,sort_by,sort_applicant,na,na");	
	myGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	myGrid.attachEvent("onXLE",myGridOnLoadingEnd);
	myGrid.attachEvent("onKeyPress",onActivityKeyPressed);
	myGrid.attachEvent("onRowSelect",onActivityRowSelected);
	myGrid.attachEvent("onRowDblClicked",onActivityRowDoubleClicked);
	//myGrid.attachEvent("onAfterSorting",onDataGrid3AfterSorting);
    myGrid.setSkin("gray"); 
    myGrid.enableAutoHeight(true,"170"); 
    myGrid.setAwaitedRowHeight(21);       
    myGrid.init();  
    myGrid.enableSmartRendering(true);
    myGrid.setHeaderCursor("pointer,pointer,pointer,,");      
    //myGrid.setSortImgState(true,0,"desc");
    //myGrid.loadXML("hire.do?mode=getLatestActivityXmlForAcceptStage");

    
	<logic:present name="showfeedback" scope="request">
		<logic:notEmpty name="applicantId" scope="request">
			showMoveUpDownScreen('<bean:write name="applicantId" scope="request"/>');
		</logic:notEmpty>
	</logic:present>
    
    Event.observe($('applicantName'), "keyup", onApplicantFilterChange.bindAsEventListener(this));
    criteriaPane= new criteriaPane('criteriaDiv'); // variable used in left pane
	applyPreFilters(); //function defined in left panel
}
var userId = '';
function onDataGridAfterSorting(index,type,direction) {
	eraseCookie("GRD_HDGRID" +userId);	
	createCookie("GRD_HDGRID"+userId,index+"_"+direction,360);
}
function onDataGrid2AfterSorting(index,type,direction) {
	eraseCookie("GRD_HDGRID2" +userId);	
	createCookie("GRD_HDGRID2"+userId,index+"_"+direction,360);
}
function onDataGrid3AfterSorting(index,type,direction) {
	eraseCookie("GRD_HDGRID3" +userId);	
	createCookie("GRD_HDGRID3"+userId,index+"_"+direction,360);
}
function loadApplicantGrid(){
	dataGrid.clearAll();
	var url = "selectionProcess.do?mode=getHireApplicantXml" + getCriteriaQryString();
	dataGrid.loadXML(url);
}

function getCriteriaQryString(){
	var qryString = "&stepId="+encodeURIComponent(document.selectionProcessForm.stepId.value);
	qryString += "&stepLevel=<%=PositionConstants.STEP_LEVEL_ACCEPT%>";
	qryString += "&departmentId="+encodeURIComponent(document.selectionProcessForm.departmentId.value);
	qryString += "&positionId="+encodeURIComponent(document.selectionProcessForm.positionId.value);
	qryString += "&applicantName="+encodeURIComponent($('applicantName').value);
	qryString += "&stepName="+encodeURIComponent(document.selectionProcessForm.stepName.value);
	qryString += "&locationTitle="+encodeURIComponent(document.selectionProcessForm.locationTitle.value);
	qryString += "&positionTypeExtInt="+encodeURIComponent(document.selectionProcessForm.positionTypeExtInt.value);
	return qryString;
}

function custom_date_sort(a,b,order,a0,b0){
	a0 = getCustomDate(myGrid.getUserData(a0,"date"));
	b0 = getCustomDate(myGrid.getUserData(b0,"date"));		
	if (order=="asc")
		return (a0>b0)?1:-1;
	else
		return (a0<b0)?1:-1;
}

function getCustomDate(a){
	if(a=='Not Available'){
		return null;
	}else{
		var yr = a.substring(0,4);
		var mo = a.substring(5,7);
		var dd = a.substring(8,10);
		var hh = a.substring(11,13);
		var mm = a.substring(14,16);
		var ss = a.substring(17,19);
		var newdate=new Date(yr,mo-1,dd,hh,mm,ss);
		return newdate;
	}
}

function custom_date_sort0(a,b,order,a0,b0){
	a0 = getCustomDate(joinedApplicantGrid.getUserData(a0,"date"));
	b0 = getCustomDate(joinedApplicantGrid.getUserData(b0,"date"));		
	if (order=="asc")
		return (a0>b0)?1:-1;
	else
		return (a0<b0)?1:-1;
}

function custom_date_sort00(a,b,order,a0,b0){
	a0 = getCustomDate(dataGrid.getUserData(a0,"date"));
	b0 = getCustomDate(dataGrid.getUserData(b0,"date"));		
	if(a0==null && b0==null){
		return 1;
	}else{
		if (order=="asc"){
			if(a0==null){
				return 1;
			}else if(b0==null){
				return -1;
			}else
				return (a0>b0)?1:-1;
		}else{
			if(a0==null){
				return -1;
			}else if(b0==null){
				return 1;
			}
			return (a0<b0)?1:-1;
		}
	}
}

function onApplicantRowSelected(id,idx_col){
	onRowSelected(datagrid,dataGrid,id,idx_col);
}

function onApplicantKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(datagrid,dataGrid,keyCode,ctrl,shift);
}

function onApplicantRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(datagrid,dataGrid,id,idx_col);
}

function onJoinedApplicantRowSelected(id,idx_col){
	onRowSelected(joined_applicant_grid,joinedApplicantGrid,id,idx_col);
}

function onJoinedApplicantKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(joined_applicant_grid,joinedApplicantGrid,keyCode,ctrl,shift);
}

function onJoinedApplicantRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(joined_applicant_grid,joinedApplicantGrid,id,idx_col);
}

function onActivityRowSelected(id,idx_col){
	onRowSelected(datagrid_latest_activities,myGrid,id,idx_col);
}

function onActivityKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(datagrid_latest_activities,myGrid,keyCode,ctrl,shift);
}

function onActivityRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(datagrid_latest_activities,myGrid,id,idx_col);
}
datagrid='GRD_APPLICANT';
datagrid_latest_activities='GRD_LATEST_ACTIVITY';
joined_applicant_grid='GRD_JOINED_APPLICANT';
function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==datagrid || grdId==joined_applicant_grid){
			onClickApplicant(id);		
		}else if(grdId==datagrid_latest_activities){
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
		//page down
	}
	return true;
}

function onRowDoubleClicked(grdId, grdObj,id,idx_col){
	if(grdId==datagrid || grdId==joined_applicant_grid){
		onClickApplicant(id);		
	}else if(grdId==datagrid_latest_activities){
		var interactionId = grdObj.getUserData(id,"interactionId");
		var interactionType = grdObj.getUserData(id,"interactionType");
		var applicantId = grdObj.getUserData(id,"applicantId");
		var interactionIsHidden =  grdObj.getUserData(id,"interactionIsHidden");
		var documentId =  grdObj.getUserData(id,"documentId");
		onClickActivity(interactionId,interactionType,applicantId,interactionIsHidden,documentId);
	}
}

function onRowSelected(grdId, grdObj, id, idx_col){
	if(grdId!=datagrid){
		dataGrid.clearSelection();		
	}
	if(grdId!=joined_applicant_grid) {
		if(joinedApplicantGrid){
		joinedApplicantGrid.clearSelection();	
		}
	}
	if(grdId!=datagrid_latest_activities){
		if(myGrid){
		myGrid.clearSelection();
		}
	}
}


function setAppointment() {
	var selId = dataGrid.getSelectedId();
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
		window.location="calendar.do?mode=calendarHome&selectedApplicant=" + dataGrid.getRowId(dataGrid.getRowIndex(dataGrid.getSelectedId()));
	} else {
		alert(error);
	}
}

function onCancelMessageDiv(){
	selectBoxUser.setSelected(selectBoxUser.getIndexWithId('-1'));
	//alert(document.selectionProcessForm.messageText);
	document.selectionProcessForm.messageText.value='';
	hidePopUpDiv('divAddMessage');
}

function submitMessage(){
	var applicantId = dataGrid.getRowId(dataGrid.getRowIndex(dataGrid.getSelectedId()));
	var fromUser = '<%=userId%>';
	var toUser = selectBoxUser.getSelectedId();
	if(toUser == '-1'){
		alert('<bean:message key="applicant_listing.label.enter_message" />');
		return false;
	}
	var message = document.selectionProcessForm.messageText.value;
	message = encodeURI(message).replace(/\&/g, '%26');
	var pars = "mode=sendMessageToUser&applicantId="+applicantId+"&userIdFrom="+fromUser+"&userIdTo="+toUser+"&messageText="+message;
	var myAjax = ajaxCall("selectionProcess.do",'post',pars,postMessageSubmit, reportError);
}

function postMessageSubmit(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
      return;
    }
    
	errors = xmlFile.getElementsByTagName("errors")[0]; 
    error = getSingleElement(errors, "error", "");

    if (error == 'Error sending Message') {
      	alert('Error sending Message');
    } else {
		onCancelMessageDiv();		
		alert('Your message has been sent');
	}
}

function openReport(){
	var applicantIds = "";
	for (var indx=0; indx<dataGrid.getRowsNum();indx++) {
		if (applicantIds.length > 0) {
			applicantIds += ',';
		}
		applicantIds+=dataGrid.getRowId(indx);
	}
 	window.open('selectionProcess.do?mode=callListforApplicants&applicantIds='+applicantIds,'_new','status=0,toolbar=0,location=0,menubar=0,directories=0,resizable=0,scrollbars=1,height=400px,width=700px,left=10px,top=10px');
}

function getSingleElement(parent,tagName,defVal){
	try {
		return parent.getElementsByTagName(tagName)[0].firstChild.nodeValue;
	} catch( myError ) {}
	return defVal;
}

function onClickApplicant(aId){	
		viewApplicant(aId);
}
function onClickJoiningDate(aId){
	var url= 'selectionProcess.do?mode=setJoiningDate&applicantId='+aId;
	window.setTimeout("showInPopUp('"+url+"',550, 320,loadGrids,true);", 10);
}
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
		window.setTimeout("showInPopUp('"+url+"',550, 320,null,true);", 10);
		//win = window.open('selectionProcess.do?mode=viewMessage&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType,'_newMessage','width=520,height=300,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_STATUS_MESSAGE%> ){
		var url = 'selectionProcess.do?mode=viewStatusMessage&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType;
		window.setTimeout("showInPopUp('"+url+"',550, 200,null,true);", 10);
		//win = window.open('selectionProcess.do?mode=viewStatusMessage&communicationId='+interactionId+'&applicantId='+applicantId+'&communicationType='+ interactionType,'_newMessage','width=520,height=300,left=10,top=10,scrollbars=yes,resizable=no,status=yes');
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_SMS%> ){
		var url = 'selectionProcess.do?mode=viewSMS&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',550, 320,null,true);", 10);	
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL%>){
		var url = 'selectionProcess.do?mode=viewOfferDetailsModifiedInteraction&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',540, 260, null, true);", 10);	
	}else if(interactionType==<%=SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION%>){
		var url = 'selectionProcess.do?mode=viewOfferDetailsModifiedInteraction&communicationId='+interactionId;
		window.setTimeout("showInPopUp('"+url+"',540, 260, null, true);", 10);	
	}
	return;
}

function sortGridRows(gridObj, column, elemIds) {
	var type = gridObj.getSortingState();
	//if (gridId == datagrid) {
		if (type[0] == column) {
			if (type[1] == 'asc') {
				gridObj.sortRows(column, "cus", "desc");
				gridObj.setSortImgState(true,column,"desc");
			} else {
				gridObj.sortRows(column, "cus", "asc");
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
			gridObj.sortRows(column, "cus", "asc");
			gridObj.setSortImgState(true,column,"asc");
			for (var i = 0; i < elemIds.length; i++) {
				if (i == (column - 1)) {
					document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_asc.gif" />';			
				} else {
					document.getElementById(elemIds[i]).innerHTML='';
				}
			}
		}
	//}
}

function sortGridRows2(gridObj, column, elemIds) {
	var type = gridObj.getSortingState();
	//if (gridId == datagrid) {
		if (type[0] == column) {
			if (type[1] == 'asc') {
				gridObj.sortRows(column, "cus", "desc");
				gridObj.setSortImgState(true,column,"desc");
			} else {
				gridObj.sortRows(column, "cus", "asc");
				gridObj.setSortImgState(true,column,"asc");
			}
			
			for (var i = 0; i < elemIds.length; i++) {
				if (i == column) {
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
			gridObj.sortRows(column, "cus", "asc");
			gridObj.setSortImgState(true,column,"asc");
			for (var i = 0; i < elemIds.length; i++) {
				if (i == column) {
					document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_asc.gif" />';			
				} else {
					document.getElementById(elemIds[i]).innerHTML='';
				}
			}
		}
		
	//}
}

function sort_stage(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"stage");
	b0 = dataGrid.getUserData(bId,"stage");	
	return sort_data(a0,b0,order);
}

function sort_name(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"name");
	b0 = dataGrid.getUserData(bId,"name");	
	return sort_data(a0,b0,order);
}

function sort_II_name(a,b,order,aId,bId) {
	a0 = joinedApplicantGrid.getUserData(aId,"name");
	b0 = joinedApplicantGrid.getUserData(bId,"name");	
	return sort_data(a0,b0,order);
}

function sort_by(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"owner");
	b0 = dataGrid.getUserData(bId,"owner");	
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
	a0 = dataGrid.getUserData(aId,"applicantName");
	b0 = dataGrid.getUserData(bId,"applicantName");	
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

function sort_dept_pos(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"stepRank");
	b0 = dataGrid.getUserData(bId,"stepRank");
	if(order=="asc") {
		if (a.toLowerCase()>b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()<b.toLowerCase()) {
			return -1;
		} else {
			return sort_data(a0,b0,order);
		}
	} else {
		if (a.toLowerCase()<b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()>b.toLowerCase()) {
			return -1;
		} else {
			return sort_data(a0,b0,order);
		}
	}
}

function sort_dept_pos0(a,b,order,aId,bId) {		
	a0 = dataGrid.getUserData(aId,"position");
	b0 = dataGrid.getUserData(bId,"position");
	return sort_data(a0,b0,order);
}

function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function applyFilters() {
	dataGrid.clearAll();
	//following lines commented as params are loaded dynamically for each request 
	var params = getCriteriaQryString();
	dataGrid.loadXML("selectionProcess.do?mode=getHireApplicantXml" + params);
	dataGrid.clearSelection();
}

function applyFilters0() {
	var pId = pFilters0.getSelectedId();
	if (pId == -1) {
		pId="";
	} else if (pId.substring(0,2) == 'd_') {		
		for (var indx=0; indx < positions.length; indx++) {			
			option = positions[indx];
			if (option.getId() == pId) {
				children = option.getChilds();
				pId="";
				for (var k=0; k < children.length; k++) {
					if(pId.length > 0) {
						pId += ',';
					}
					pId+=children[k].getId();
				}
				break;
			}
		}
	}
	var sId = sFilters0.getSelectedId();
	if (sId == -1) {
		sId="";
	}
	var nFilter = document.selectionProcessForm.nameFilter0.value;
	if (nFilter == '' || nFilter == 'Search name') {
		nFilter = "";
	}
	joinedApplicantGrid.clearAll();
	$('GRD_JOINED_APPLICANT_TOTAL').show();
	$('GRD_JOINED_APPLICANT_TABLE').show();
	joinedApplicantGrid.loadXML("selectionProcess.do?mode=getJoinedApplicantXml&filterByPosition=" + pId + "&filterByDuration=" + sId + "&filterByName=" + nFilter);
}


function dataGridOnLoadingEnd() {

	var footerTxt='';
	var lastRowId = dataGrid.getRowId(dataGrid.getRowsNum()-1);
	var obj = document.getElementById('total');
	var rowCnt = dataGrid.getRowsNum();

	if(rowCnt==1){
		footerTxt = '<bean:message key="position_summary.text.total.candidate" arg0="'+rowCnt+'"  />';
	}else if(rowCnt>globalNum){
		dataGrid.deleteRow(lastRowId);
		rowCnt = dataGrid.getRowsNum();
		footerTxt = '<bean:message key="position_summary.text.total.candidates.limit" arg0="'+rowCnt+'"  />';
	}else if(rowCnt<=globalNum){
		footerTxt = '<bean:message key="position_summary.text.total.candidates" arg0="'+rowCnt+'"  />';
	}
	obj.innerHTML=footerTxt;

	
	var type = dataGrid.getSortingState();
	var val = readCookie("GRD_HDGRID"+userId);
	if(val != null) {
		parts = val.split("_");
		sortDataGridRows(parts);
	} else {
		sortDataGridRows(type);
	}
}


function sortDataGridRows(type) {
	if (type[1].toUpperCase() == 'ASC') {
		order='asc';		
	} else {		
		order='desc';			
	}
	
	dataGrid.sortRows(type[0], "cus", order);//cus for custom
	dataGrid.setSortImgState(true,type[0],order);	
}
function joinedApplicantGridOnLoadingEnd() {
	var type = joinedApplicantGrid.getSortingState();
	var val = readCookie("GRD_HDGRID2"+userId);	
	
	if(val != null) {
		parts = val.split("_");
		sortDataGrid2Rows(parts);
	} else {
		sortDataGrid2Rows(type);
	}

	var footerTxt='';
	var obj = document.getElementById('totaljoin');
	var rowCnt = joinedApplicantGrid.getRowsNum();

	if(rowCnt==1){
		footerTxt = '<bean:message key="position_summary.text.total.candidate" arg0="'+rowCnt+'"  />';
	}else {
		footerTxt = '<bean:message key="position_summary.text.total.candidates" arg0="'+rowCnt+'"  />';
	}
	obj.innerHTML=footerTxt;
	
}function sortDataGrid2Rows(type) {
	if (type[1].toUpperCase() == 'ASC') {
		order='asc';		
	} else {		
		order='desc';			
	}
	joinedApplicantGrid.sortRows(type[0], "cus", order);//cus for custom
	joinedApplicantGrid.setSortImgState(true,type[0],order);	
	for (var i = 0; i < sort_img_ids00.length; i++) {
		if (i == type[0]) {
			if (type[1].toUpperCase() == 'DESC') {
				document.getElementById(sort_img_ids00[i]).innerHTML='<img src="images/sort_desc.gif" />';
			} else {
				document.getElementById(sort_img_ids00[i]).innerHTML='<img src="images/sort_asc.gif" />';
			}				
		} else {
			document.getElementById(sort_img_ids00[i]).innerHTML='';
		}
	}
}

function myGridOnLoadingEnd() {
	var type = myGrid.getSortingState();
	var val = readCookie("GRD_HDGRID3"+userId);	
	
	/*if(val != null) {
		parts = val.split("_");
		sortDataGrid3Rows(parts);
	} else {
		sortDataGrid3Rows(type);
	}*/
}

function sortDataGrid3Rows(type) {
	if (type[1].toUpperCase() == 'ASC') {
		order='asc';		
	} else {		
		order='desc';			
	}
	
	myGrid.sortRows(type[0], "cus", order);//cus for custom
	myGrid.setSortImgState(true,type[0],order);	
}

function loadGrids() {
	applyFilters();
	myGrid.clearAll();
	myGrid.loadXML("hire.do?mode=getLatestActivityXmlForAcceptStage");
}
function loadLatestActivities(){
	myGrid.loadXML("hire.do?mode=getLatestActivityXmlForAcceptStage");
}
function moveUpOrDown() {
	var selId = dataGrid.getSelectedId();
	if (selId) {
		var ids=selId.split(",");
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
	var ids = dataGrid.getSelectedId();
	var url = 'selectionProcess.do?mode=bulkMoveApplicantUpOrDown&applicantId='+ids+'&tab=<%=SelectionProcessConstants.TAB_HIRE%>';
	url += '&screenType='+returnVal;
	window.location=url;
}

function changeStatus() {
	var selId = dataGrid.getSelectedId();
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
function addPhone(){
	var selId = dataGrid.getSelectedId();
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
function addNote(){
	var selId = dataGrid.getSelectedId();
	if (selId) {
		var url = 'selectionProcess.do?mode=addPhoneLog&applicantId=' +selId+'&communicationType=<%=SelectionProcessConstants.INTERACTION_NOTE%>';
		window.setTimeout("showInPopUp('"+url+"',550,250,loadGrids,true);", 10);
	} else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	} 
}
function newEmail(){
	var selId = dataGrid.getSelectedId();
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

function forwardResumes(){
	var selId = dataGrid.getSelectedId();
	if (selId) {
		var url = 'inbox.do?mode=forwardResumes&newEmailType=<%=InboxConstants.EMAIL_TYPE_FORWARD_RESUME%>&emailLocation=<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>&applicantId='+selId;
		window.setTimeout("showInPopUp('"+url+"',810, 513,loadGrids,true);", 10);
	}else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	} 
}

function showMessageBox(){
	var selId = dataGrid.getSelectedId();
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

function editAppointment(returnVal){
	if(returnVal){
		window.location=returnVal;
	}
}

function sendSMS() {
	var selId = dataGrid.getSelectedId();
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

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}



function viewApplicant(aId){
	//var app = 
	var hilite = '';
	if($("searchText")) {		
		var val = $("searchText").value;
		if(val != '') {			
			parts = val.split(',');
			for(i = 0; i < parts.length; i++) {
				parts2 = parts[i].split(/[\s]+/);	
				for(j = 0; j < parts2.length; j++) {
					if(hilite != '') {
						hilite += ',';
					}
					hilite += formatString(parts2[j].trim()) + "|hl1";
				}
			}
		}		
	}
	url = "selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId;
	if(hilite != '') {
		url += '&hilite='+hilite;
	}
	window.open(url,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	//app.focus();
	return false;
}

window.onload=doOnLoad;

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function exportToExcel(){
	var url = "export.do?mode=exportHireApplicants&ids="+dataGrid.getAllItemIds();
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}
/*********************Toggle Added for the Joined and Latest Activity Grids****************/
var reqSentToLoadLatestActivities = false;
var reqSentToLoadJoinedApplicantGrid= false;

function toggleGrid(imgId,gridId){
	if(gridId == datagrid_latest_activities){
		if(myGrid.getRowsNum()!=0){
			myGrid.clearAll();
			toggleImage(imgId,false);
			reqSentToLoadLatestActivities=false;
		}else if(reqSentToLoadLatestActivities==false){
			reqSentToLoadLatestActivities=true;
			loadLatestActivities();
			toggleImage(imgId,true);
			reqSentToLoadLatestActivities=false;
		}
	}
}
function toggleJoinedGrid(imgId,gridId){
	if(gridId == joined_applicant_grid){
		if(reqSentToLoadJoinedApplicantGrid==true){
			joinedApplicantGrid.clearAll();
			$('GRD_JOINED_APPLICANT_TOTAL').hide();
			$('GRD_JOINED_APPLICANT_TABLE').hide();
			toggleImage(imgId,false);
			reqSentToLoadJoinedApplicantGrid=false;
		}else if(reqSentToLoadJoinedApplicantGrid==false){
			reqSentToLoadJoinedApplicantGrid=true;
			applyFilters0();
			toggleImage(imgId,true);
			
		}
	}	
} 
function blackListApplicant(){
	var selId = dataGrid.getSelectedId();
	if (selId) {
		var ids=selId.split(",");
		if(ids.length<2){	
			var applicantName = dataGrid.getUserData(selId,"name");	
			var positionId = dataGrid.getUserData(selId,"positionId");
			var applicantStatus = '<%=ApplicantConstants.APPLICANT_STATUS_NORMAL%>';
			var url = 'selectionProcess.do?mode=blackListApplicant&applicantId=' +selId+'&applicantName='+applicantName+'&positionId='+positionId+'&applicantStatus='+applicantStatus;
			window.setTimeout("showInPopUp('"+url+"',550,250,loadGrids,true);", 10);
		}else{
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.candidate"/>");
		}
	} else {
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>'); 
	} 
}
function toggleImage(imgId, down){
	if(down){
		$(imgId).src="images/ico_down1.gif";//$(imgId).src.replace(".gif","1.gif");
	}else{
		$(imgId).src="images/ico_down.gif";//$(imgId).src.replace("1.gif",".gif");
	}
}

function onClickPosition(pId){
	viewPositionSummary(pId);
}
</script>
