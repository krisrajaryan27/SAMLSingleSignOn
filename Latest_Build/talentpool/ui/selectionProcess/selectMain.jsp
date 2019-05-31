<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="java.util.List,java.util.ArrayList,
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
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/cookies.js"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/criteriapane.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
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

<% if(ModuleSet.isMODULE_MASS_EMAILS()){ %>
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_MASS_EMAIL">
var mnu_mass_email = new TpMenu({type:'menu', id: 'massemail',image:'images/ico_multiple_email.gif', title:'<bean:message key="select.label.multi_email" />', onclick:'onClickMenu'});
menu.addItem(mnu_mass_email);
</logic:equal>		    	
<% } %>

var mnu_fwd = new TpMenu({type:'menu', id: 'fwd', image:'images/ico_forward_resume.gif',title:'<bean:message key="common.forward" /> <bean:message key="common.resumes" />', onclick:'onClickMenu'});
menu.addItem(mnu_fwd);

<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BLACKLIST_APPLICANT">
	var mnu_blackList = new TpMenu({type:'menu', id: 'blackList',image:'images/ico_blacklist.gif', title:'<bean:message key="black_list.label.blackList" />', onclick:'onClickMenu'});
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
	}else if(id=="blackList"){
		blackListApplicant();
	}
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
	}
	return obj.cell.innerHTML;
}
datagrid='GRD_APPLICANT';
datagrid_latest_activities='GRD_LATEST_ACTIVITY';
</script> 
<html:form action="/selectionProcess">
<html:hidden property="departmentId" name="selectionProcessForm"/>
<html:hidden property="positionId" name="selectionProcessForm"/>
<html:hidden property="stepName" name="selectionProcessForm"/>
<html:hidden property="stepLevel" name="selectionProcessForm"/>
<html:hidden property="stepId" name="selectionProcessForm"/>
<html:hidden property="locationTitle" name="selectionProcessForm"/>
<html:hidden property="positionTypeExtInt" name="selectionProcessForm"/>
<html:hidden property="actionRequired" name="selectionProcessForm"/>
<html:hidden property="selectedUserId" name="selectionProcessForm"/>
</html:form>
	
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
  		<a href="#" style="width:85px;" onclick="javascript: showMenuFlag();" id="flags"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_set_flag.gif" width="10" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="select.label.set_flag"/></a> 
  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
  		</logic:equal>
  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
  		<a href="#" style="width:65px;" onmouseover="javascript: showMenuMore(dataGrid.getSelectedId(),'more');" id="more"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_more.gif" width="14" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="select.label.more"/></a>		
		
		<a href="#" style="text-align:right;background:none;padding-left: 82px;" onmouseover="changeImage('exportToExl', 'images/excel_co.GIF')" onmouseout="changeImage('exportToExl', 'images/excel_bw.GIF')">
			<img src="images/excel_bw.GIF" width="16" height="16" style="border:0px;" id="exportToExl" onclick="javascript:exportToExcel(); return false;" />
		</a>
		 
	</div>
	<br/>
  
  	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder" style="border-bottom: 0px;">
				<div id="GRD_APPLICANT" style="width:738px;height:290px;margin-top: -1px;"></div>
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

	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		<tr> 
		  <td width="50%">
		  	<div style="width:140px;" class="boxTab">
		  		<span class="rightC"></span><span class="leftC"></span>&nbsp;
		  		<bean:message key="select.latest_activity.label.latest_activities" />
		  	</div>				  	
		  </td>		
		  <td><div style="width:30px; float:right; text-align:center;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_down.gif" name="btnImg1" width="13" height="13" vspace="3" id="imgup_1" onclick="toggleGrid(this.id,'GRD_LATEST_ACTIVITY');return false;" style="cursor:hand;"/></div></td>   
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
		  </td>		  
		</tr> 
	</table>			
  </div> 
<br/><br/>
<script language="javascript">
var reqSentToLoadLatestActivities=false;

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function toggleGrid(imgId,grdId){
	if (myGrid.getRowsNum() != 0) {
		myGrid.clearAll();
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
var pageSize=10;
var dataGrid;
var myGrid;

function initApplicantGrid() {	
    dataGrid = new dhtmlXGridObject('GRD_APPLICANT'); 
    dataGrid.imgURL = "images/"; 
    dataGrid.setHeader("&nbsp;,<bean:message key="select.label.name" />,<bean:message key="common.position" />,<bean:message key="select.label.stage_status" />,<bean:message key="select.label.action_required" />"); 
    dataGrid.setInitWidths("18,156,140,200,202");
    dataGrid.setColAlign("left,left,left,left,left");
    dataGrid.setColTypes("link,ro,ro,ro,ro"); 
    dataGrid.setColSorting("na,sort_name,sort_position,sort_stage,na");	    
	dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	dataGrid.attachEvent("onXLE",dataGridOnLoadingEnd);
	dataGrid.attachEvent("onKeyPress",onApplicantKeyPressed);
	dataGrid.attachEvent("onRowSelect",onApplicantRowSelected);
	dataGrid.attachEvent("onRowDblClicked",onApplicantRowDoubleClicked);
	dataGrid.attachEvent("onAfterSorting",onDataGridAfterSorting);
	//dataGrid.setNoHeader(true);
    dataGrid.enableResizing("false,false,false,false,false");
    dataGrid.setXMLAutoLoading("selectionProcess.do?mode=getSelectApplicantXml");
    dataGrid.setAwaitedRowHeight(36);   
    dataGrid.init(); 
    dataGrid.setHeaderCursor(",pointer,pointer,pointer,");  
    dataGrid.enableMultiselect(true);
    dataGrid.enableSmartRendering(true);
    dataGrid.setSortImgState(true,1,"asc");		
    window.setTimeout("loadApplicantGrid()", 2);	    
  	      
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
	//myGrid.attachEvent("onAfterSorting",onMyGridAfterSorting);
    myGrid.setSkin("gray");
    myGrid.enableAutoHeight(true,"170");
    myGrid.setAwaitedRowHeight(21);      
    myGrid.init();   
    myGrid.enableSmartRendering(true);
    myGrid.setHeaderCursor("pointer,pointer,pointer,,"); 
    //myGrid.setSortImgState(true,0,"desc");
	<logic:present name="showfeedback" scope="request">
		<logic:notEmpty name="applicantId" scope="request">
			showMoveUpDownScreen('<bean:write name="applicantId" scope="request"/>');
		</logic:notEmpty>
	</logic:present>
}
var userId = '';
function onDataGridAfterSorting(index,type,direction) {
	eraseCookie("GRD_DGRID" +userId);	
	createCookie("GRD_DGRID"+userId,index+"_"+direction,360);
}

function loadLatestActivities(){
    myGrid.loadXML("select.do?mode=getLatestActivityXmlForSelectStage");
}

function loadApplicantGrid(){
	dataGrid.clearAll();
	var url = "selectionProcess.do?mode=getSelectApplicantXml" + getCriteriaQryString();
	dataGrid.loadXML(url);
}


function sort_name(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"name");
	b0 = dataGrid.getUserData(bId,"name");	
	return sort_data(a0,b0,order);
}

function sort_position(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"position");
	b0 = dataGrid.getUserData(bId,"position");	
	return sort_data(a0,b0,order);
}

function sort_stage(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"stage");
	b0 = dataGrid.getUserData(bId,"stage");	
	return sort_data(a0,b0,order);
}

function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
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
	var yr = a.substring(0,4);
	var mo = a.substring(5,7);
	var dd = a.substring(8,10);
	var hh = a.substring(11,13);
	var mm = a.substring(14,16);
	var ss = a.substring(17,19);
	var newdate=new Date(yr,mo-1,dd,hh,mm,ss);
	return newdate;
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
function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==datagrid){
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
	if(grdId==datagrid){
		onClickApplicant(id);		
	}else if(grdId==datagrid_latest_activities){
		var interactionId = grdObj.getUserData(id,"interactionId");
		var interactionType = grdObj.getUserData(id,"interactionType");
		var applicantId = grdObj.getUserData(id,"applicantId");
		var interactionIsHidden =  grdObj.getUserData(id,"interactionIsHidden");
		var documentId = grdObj.getUserData(id,"documentId");
		onClickActivity(interactionId,interactionType,applicantId,interactionIsHidden,documentId);
	}
}

function onRowSelected(grdId, grdObj, id, idx_col){
	if(grdId!=datagrid){
		dataGrid.clearSelection();		
	}
	if(grdId!=datagrid_latest_activities){
		myGrid.clearSelection();
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
	var error = ''
	if (xmlFile != null && xmlFile != ''){
		var errors = xmlFile.getElementsByTagName("errors")[0];
		error = getSingleElement(errors, "error", "");	
	}
	if (error == '') {
		window.location="calendar.do?mode=calendarHome&selectedApplicant=" + dataGrid.getRowId(dataGrid.getRowIndex(dataGrid.getSelectedId()));
	} else {
		alert(error);
	}
}

function onCancelMessageDiv(){
	selectBoxUser.setSelected(selectBoxUser.getIndexWithId('-1'));
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

function getSingleElement(parent,tagName,defVal){
	try {
		return parent.getElementsByTagName(tagName)[0].firstChild.nodeValue;
	} catch( myError ) {}
	return defVal;
}

function onClickApplicant(aId){	
	url = "selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId;
	window.open(url,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	return false;

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
function sortGridRows(gridId, gridObj, column) {
	var type = gridObj.getSortingState();
	if (gridId == datagrid) {
		if (type[0] == column) {
			if (type[1] == 'asc') {
				//dataGrid.sortRows(column, "str", "desc");
				// write a sorting function 
				dataGrid.setSortImgState(true,column,"desc");
			} else {
				//dataGrid.sortRows(column, "str", "asc");
				dataGrid.setSortImgState(true,column,"asc");
			}
			
			for (var i = 0; i < sort_img_ids.length; i++) {
				if (i == (column - 1)) {
					if (type[1] == 'asc') {
						document.getElementById(sort_img_ids[i]).innerHTML='<img src="images/sort_desc.gif" />';
					} else {
						document.getElementById(sort_img_ids[i]).innerHTML='<img src="images/sort_asc.gif" />';
					}				
				} else {
					document.getElementById(sort_img_ids[i]).innerHTML='';
				}
			}
		} else {
			//dataGrid.sortRows(column, "cus", "asc");
			dataGrid.setSortImgState(true,column,"asc");
			for (var i = 0; i < sort_img_ids.length; i++) {
				if (i == (column - 1)) {
					document.getElementById(sort_img_ids[i]).innerHTML='<img src="images/sort_asc.gif" />';			
				} else {
					document.getElementById(sort_img_ids[i]).innerHTML='';
				}
			}
		}
		applyFilters();
	}
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
	dataGrid.loadXML("selectionProcess.do?mode=getSelectApplicantXml" + params);
	dataGrid.clearSelection();
}

function massMail(){
	var params = getCriteriaQryString();
	var url = "selectionProcess.do?mode=massEmailSelect" + params;
	window.setTimeout("showInPopUp('"+url+"',800,550,null,true);", 10);
	
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

	/*var val = readCookie("GRD_DGRID"+userId);
	var type = dataGrid.getSortingState();
	if(val != null) {
		parts = val.split("_");
		sortDataGridRows(parts);
	} else {
		sortDataGridRows(type);
	}*/
}

function sortDataGridRows(type) {
	if (type[1].toUpperCase() == 'ASC') {
		order='asc';		
	} else {		
		order='desc';			
	}
	
	switch(type[0]){
		case 1:
			dataGrid.sortRows(type[0], "cus", order);//cus for custom
			break;
		default: 
			dataGrid.sortRows(type[0], "str", order);
			break;
	}	
	
	dataGrid.setSortImgState(true,type[0],order);	
}

function myGridOnLoadingEnd() {
	var type = myGrid.getSortingState();
	//sortMyGridRows(type);
}

function sortMyGridRows(type) {
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
	toggleImage('imgup_1',true);
	myGrid.loadXML("select.do?mode=getLatestActivityXmlForSelectStage");
}

function moveUpOrDown() {
	var selId = dataGrid.getSelectedId();
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
	var ids = dataGrid.getSelectedId();
	var url = 'selectionProcess.do?mode=bulkMoveApplicantUpOrDown&applicantId='+ids+'&tab=<%=SelectionProcessConstants.TAB_SELECT%>';
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

function doOnLoad() {	
	initPopUp();
	initApplicantGrid();
//	loadApplicantGrid();
	Event.observe($('applicantName'), "keydown", onApplicantFilterChange.bindAsEventListener(this));
	criteriaPane= new criteriaPane('criteriaDiv'); // variable used in left pane
	applyPreFilters(); //function defined in left panel
}
window.onload=doOnLoad;

function exportToExcel(){
	var url = "export.do?mode=exportSelectApplicants&ids="+dataGrid.getAllItemIds();
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}

function getCriteriaQryString(){
	var qryString = "&stepId="+encodeURIComponent(document.selectionProcessForm.stepId.value);
	qryString += "&stepLevel="+encodeURIComponent(checkboxListSelectionStage.getSelectedIds());
	qryString += "&departmentId="+encodeURIComponent(document.selectionProcessForm.departmentId.value);
	qryString += "&positionId="+encodeURIComponent(document.selectionProcessForm.positionId.value);
	qryString += "&applicantName="+encodeURIComponent($('applicantName').value);
	qryString += "&stepName="+encodeURIComponent(document.selectionProcessForm.stepName.value);
	qryString += "&locationTitle="+encodeURIComponent(document.selectionProcessForm.locationTitle.value);
	qryString += "&positionTypeExtInt="+encodeURIComponent(document.selectionProcessForm.positionTypeExtInt.value);	
	qryString += "&actionRequired="+encodeURIComponent(document.selectionProcessForm.actionRequired.value);
	qryString += "&selectedUserId="+encodeURIComponent(document.selectionProcessForm.selectedUserId.value);
	return qryString;
}

function onClickPosition(pId){
	viewPositionSummary(pId);
}

</script>
