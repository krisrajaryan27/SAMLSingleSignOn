<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<%@ page import="com.talentPool.common.NavigationConstants"%>
<%@ page import="com.talentPool.dashboard.constants.DashboardConstants,
				com.talentPool.common.properties.GlobalConstants,
				com.talentPool.calendar.CalendarConstants"%>
<%@ page import="com.talentPool.positions.PositionConstants"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>

<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.user.constants.DataViewConstants"%>
<%@page import="com.talentPool.user.utils.DataViewUtils"%>
<%@page import="com.talentPool.user.UserConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%><script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/cookies.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>

<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.config.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/balloon.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/box.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/yahoo-dom-event.js"></script>
<script language="JavaScript" type="text/javascript" src="js/tooltip/tip_ajaxcall.js"></script>

<script>
function eXcell_estat(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 }
}
eXcell_estat.prototype = new eXcell;
eXcell_estat.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	if(val=="<%=PositionConstants.POSITION_STATUS_CLOSED%>" || val=="<%=PositionConstants.POSITION_STATUS_REJECTED%>"){
	 	this.cell.parentNode.className='disabledrow';
	}else if(val=="<%=PositionConstants.POSITION_STATUS_HOLD%>" ){
	 	this.cell.parentNode.className='onholdrow';
	}else if(val=="<%=PositionConstants.POSITION_STATUS_OVERDUE%>" ){
	 	this.cell.parentNode.className='overdue';
	}
	
}
var positionSummaryGroupBy = null;
var todoGroupBySelectBox = null;
</script>
<% 
	String userId = (String) request.getSession(false).getAttribute("userId");
%>
<div class="contentDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="68%">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td>
						<div style="width:140px;" class="boxTab" onclick="showEmailType(0);"
							id="msgReceived"><span class="rightC"></span><span
							class="leftC"></span>&nbsp;<img src="images/ico_message.gif"
							vspace="3" align="middle" /> <bean:message
							key="dashboard.label.messages" /> [<strong id="MSG_COUNT"></strong>]
						</div>
					</td>
					<td>
						<div style="width:140px;cursor: pointer;" class="boxDarkTab"
							onclick="showEmailType(1);" id="msgSent"><span class="rightC"></span><span
							class="leftC"></span>&nbsp;<img src="images/ico_message.gif"
							vspace="3" align="middle" /> <bean:message
							key="dashboard.label.sent_messages" /> [<strong id="MSG_SENT_COUNT"><bean:write name="totalSent" scope="request"/></strong>]
						</div>
					</td>
				</tr>
			</table>
		</td>
		<td width="32%" align="right"><!-- div style="width:30px; float:right; text-align:center;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_newwin.gif" width="13" height="13" vspace="3" /></div-->
		<div style="width:30px; float:right; text-align:center;"
			class="boxTab"><span class="rightC"></span><span class="leftC"></span><img
			src="images/ico_down.gif" name="btnImg1" width="13" height="13"
			vspace="3" id="imgup_1"
			onclick="toggleMessgesGrids(this.id);return false;"
			style="cursor:hand;" /></div>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="GRD_MSGBOX" width="738" style="height: 18px;display:block;"></div>
			<div id="GRD_SENT_MSGBOX" width="738" style="height: 18px;display: none;"></div>
			<div id="MSG_NO_MESSAGES" class="noContent"><bean:message key="dashboard.error.no_messages" /></div>
		</td>
	</tr>
</table>

<br />

<table border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="534px">
			<div style="width:140px;" class="boxTab"><span class="rightC"></span><span
				class="leftC"></span>&nbsp;<img src="images/ico_todo.gif" vspace="3"
				align="absmiddle" /> <bean:message key="dashboard.label.todo" /> [<strong
				id="TODO_COUNT"></strong>]</div>
		</td>
		<td>
		<bean:message key="dashboard.postionSummary.label.groupBy"/>:&nbsp;</td>
		<td>
			<script>
			 	var todoOpts = [new SelectOption('<%=DashboardConstants.TODO_LIST_TYPE_LIST%>','-----Select-----')];
				var todoOpt1 = [new SelectOption('<%=DashboardConstants.TODO_LIST_TYPE_ACTION%>','<bean:message key="dashboard.label.todo.action_wise" />')];
				var todoOpt2 = [new SelectOption('<%=DashboardConstants.TODO_LIST_TYPE_STEP%>','<bean:message key="dashboard.label.todo.selection_step_wise" />')];
				var todoOpt3 = [new SelectOption('<%=DashboardConstants.TODO_LIST_TYPE_POSITION%>','<bean:message key="common.position" />')];
				todoOpts = todoOpts.concat(todoOpt1).concat(todoOpt2).concat(todoOpt3);
				todoGroupBySelectBox = new SelectBox(todoOpts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'100px', size:5});
				todoGroupBySelectBox.setOnChangeHandler('onChangeToDoGrouping');
				document.write(todoGroupBySelectBox.getHtml());
				todoGroupBySelectBox.init();
			</script>	
		</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="GRD_GROUP_TODO_HEADER" width="738">
				<table class="todoBoxHeader" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="35px">&nbsp;</td>
						<td id="col_candidate" width="128px" onclick="javascript: sortData(this);"><bean:message key="dashboard.label.hdr.candidate"/></td>
						<td id="col_position" width="230px" onclick="javascript: sortData(this);"><bean:message key="common.position"/></td>
						<td id="col_todo" width="280px" onclick="javascript: sortData(this);"><bean:message key="dashboard.label.hdr.todo"/></td>
						<td id="col_due_date" width="85px" onclick="javascript: sortData(this);"><bean:message key="dashboard.label.hdr.duedate"/>&nbsp;&nbsp;<img id="sortImg" src="images/sort_desc.gif" /></td>
					</tr>
				</table>
			</div>
			<div id="GRD_TODO_ID" width="738" style="height: 18px;"></div>
			<div id="GRD_TODO" width="738" style="height: 18px;"></div>
			<div id="MSG_NO_TODO" class="noContent"><bean:message key="dashboard.error.no_todo" /></div>
		</td>
	</tr>
</table>
<br />
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHOW_POSITION_SUMMARY_DASHBOARD">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="494">
			<div style="width:200px;" class="boxTab"><span class="rightC"></span><span
				class="leftC"></span>&nbsp;<img src="images/ico_postion1.gif"
				vspace="3" align="absmiddle" /> <bean:message key="common.position" />&nbsp;
				<bean:message key="common.summary" />  [<strong id="POSITION_COUNT"></strong>]</div>
		</td>
		<td>
			<bean:message key="dashboard.postionSummary.label.groupBy"/>:
		</td>
		<td>
			<script>
				var positionSummaryGroupByOpts = <bean:write name="positionSummaryGroupByJSArray" filter="false" scope="request" />
				positionSummaryGroupBy = new SelectBox(positionSummaryGroupByOpts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'140px', size:10});
				positionSummaryGroupBy.setOnChangeHandler('onChangePositionGridGrouping');
				document.write(positionSummaryGroupBy.getHtml());
				positionSummaryGroupBy.init();
			</script>
		</td>
		<td align="right"><!--  div style="width:30px; float:right; text-align:center;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_newwin.gif" width="13" height="13" vspace="3" /></div-->
			<div style="width:30px; float:right; text-align:center;"
				class="boxTab"><span class="rightC"></span><span class="leftC"></span><img
				src="images/ico_down.gif" name="btnImg1" width="13" height="13"
				vspace="3" id="imgup_3"
				onclick="toggleGrid(this.id,'GRD_POSITION');return false;"
				style="cursor:pointer;" /></div>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="GRD_POSITION_HEADER" >
				<table class="todoBoxHeader" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td style="width: 20px;">&nbsp;</td>  
						<td style="width: 246px;"><bean:message key="common.position"/></td>
						<td style="width: 125px;" >&nbsp;</td>
						<td style="width: 65px;" ><bean:message key="common.vacancies"/></td>
						<td style="width: 75px;" ><bean:message key="dashboard.label.hdr.inprocess"/></td>
						<td style="width: 80px;" ><bean:message key="common.pending_offers"/></td>
						<td style="width: 40px;" ><bean:message key="common.joined"/></td>
						<td style="width: 55px;" ><bean:message key="common.rejected"/></td>
						<td style="width: 0px;"></td>
						<td style="width: 0px;"></td>
					</tr>
				</table>
			</div>	
			<div id="GRD_POSITION" width="738" style="height: 18px;"></div>
			<div id="MSG_NO_POSITION" class="noContent"><bean:message key="common.no" />&nbsp;<bean:message key="common.positions" />&nbsp;<bean:message key="common.available" /></div>
		</td>
	</tr>
</table>
<br />
</logic:equal>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="20%">
		<div style="width:140px;" class="boxTab"><span class="rightC"></span><span
			class="leftC"></span>&nbsp;<img src="images/ico_calendar.gif"
			width="13" height="13" vspace="3" align="absmiddle" /> <bean:message
			key="dashboard.label.calendar" /> [<strong id="EVENT_COUNT"></strong>]
		</div>
		</td>
		<td>
		 	<% String show_reminder = (String)request.getAttribute(GlobalConstants.PROPERTY_SHOW_REMINDER); %>
		  	<% if("1".equals(show_reminder)){ %>
		  		<img src="images/checkboxchecked.gif" id="show_reminder" name="show_reminder" 
		  		onclick="changeCheckboxState(this);" />
		  	<%}else{ %>
		  		<img src="images/checkboxunchecked.gif" id="show_reminder" name="show_reminder" 
		  		onclick="changeCheckboxState(this);" />
		  	<%} %>
		  	<bean:message key="admin_application_settings.label.show_reminders_with_calendar"/></td>
		<td width="32%" align="right"><!-- div style="width:30px; float:right; text-align:center;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_newwin.gif" width="13" height="13" vspace="3" /></div-->
		<div style="width:30px; float:right; text-align:center; "
			class="boxTab"><span class="rightC"></span><span class="leftC"></span><img
			src="images/ico_down.gif" name="btnImg4" width="13" height="13"
			vspace="3" id="imgup_4"
			onclick="toggleGrid(this.id,'GRD_EVENT');return false;"
			style="cursor:pointer;" /></div>
		<div
			style="width:30px; float:right; text-align:center; margin-right:2px;"
			class="boxTab"><span class="rightC"></span><span class="leftC"></span><img
			src="images/ico_cal.gif" name="btnImg2" width="13" height="13"
			vspace="3" id="btnImg2"
			onclick="window.location='calendar.do?mode=calendarHome&t=4&st=41'"
			style="cursor:hand;" title="<bean:message key='dashboard.tooltip.set_appointment' />"/></div>
		<div
			style="width:30px; float:right; text-align:center; margin-right:2px;"
			class="boxTab"><span class="rightC"></span><span class="leftC"></span><img
			src="images/ico_reminder.gif" name="btnImg3" width="13" height="13"
			vspace="3" id="btnImg3"
			onclick="javascript: onClickReminder('', '');"
			style="cursor:hand;" title="<bean:message key='dashboard.tooltip.set_reminder' />"/></div>
		</td>
	</tr>
</table>
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="GRD_EVENT" width="738" style="height: 18px;"></div>
			<div id="MSG_NO_EVENT" class="noContent"><bean:message key="dashboard.error.no_events" /></div>
		</td>
	</tr>
</table>

<br />

<script language="javaScript">
var userId = '<%=Utils.getBlankIfNull(userId)%>';
var grdMessages;
var grdSentMessages;
var grdToDO;
var grdPositions;
var grdEvents;


var grdMessagesId='GRD_MSGBOX';
var grdSentMessagesId='GRD_SENT_MSGBOX';
var grdToDOId = 'GRD_TODO_ID';
var grdPositionsId='GRD_POSITION';
var grdEventsId='GRD_EVENT';


var msgNoMessages="MSG_NO_MESSAGES";
var msgNoToDO="MSG_NO_TODO";
var msgNoPositions="MSG_NO_POSITION";
var msgNoEvents="MSG_NO_EVENT";

var msgCount="MSG_COUNT";
var msgSentCount="MSG_SENT_COUNT";
var ToDOCount="TODO_COUNT";
var PositionCount="POSITION_COUNT";
var EventsCount="EVENT_COUNT";

var maxHeight=212;
var pageSize=10;

var sentMessagesLoaded=0;

function initMessages(){
		grdMessages = new dhtmlXGridObject(grdMessagesId);
		grdMessages.imgURL = "images/"; 
		grdMessages.setHeader("&nbsp;,<bean:message key="dashboard.label.hdr.from"/>,<bean:message key="dashboard.label.hdr.date"/>,<bean:message key="dashboard.label.hdr.message"/>,<bean:message key="dashboard.label.hdr.relatedto"/>,");
		grdMessages.setInitWidths("18,135,100,295,173,0");
		grdMessages.setColAlign("left,left,left,left,left,left");
		grdMessages.setColTypes("ro,ro,ro,link,link,ro");
		grdMessages.setColSorting("cstr,cstr,msg_date_sort,cstr,cstr,na");
		grdMessages.enableAutoHeigth(true,maxHeight);
		grdMessages.attachEvent("onXLE",onGridLoadEnd);
		grdMessages.attachEvent("onKeyPress",onMsgKeyPressed);
		grdMessages.attachEvent("onRowDblClicked",onMsgRowDoubleClicked);
		grdMessages.attachEvent("onRowSelect",onMsgRowSelected);
		
		grdMessages.init();
		grdMessages.setHeaderCursor(",pointer,pointer,pointer,pointer");
		grdMessages.setSortImgState(true,2,"DESC");
		grdMessages.setColumnHidden(5,true);
		grdMessages.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
		
}
function loadMessages(){
	grdMessages.loadXML("dashboard.do?mode=getMessagesForUser");
}
function onMsgRowSelected(id,idx_col){
	onRowSelected(grdMessagesId,grdMessages,id,idx_col);
}
function onMsgKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(grdMessagesId,grdMessages,keyCode,ctrl,shift);
}
function onMsgRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(grdMessagesId,grdMessages,id,idx_col);
}

function initSentMessages(){
		grdSentMessages = new dhtmlXGridObject(grdSentMessagesId);
		grdSentMessages.imgURL = "images/"; 
		grdSentMessages.setHeader("<bean:message key="dashboard.label.hdr.to"/>,<bean:message key="dashboard.label.hdr.date"/>,<bean:message key="dashboard.label.hdr.message"/>,<bean:message key="dashboard.label.hdr.relatedto"/>,");
		grdSentMessages.setInitWidths("153,100,295,173,0");
		grdSentMessages.setColAlign("left,left,left,left,left");
		grdSentMessages.setColTypes("ro,ro,link,link,ro");
		grdSentMessages.setColSorting("cstr,sent_msg_date_sort,cstr,cstr,na");
		grdSentMessages.enableAutoHeigth(true,maxHeight);
		grdSentMessages.attachEvent("onXLE",onGridLoadEnd);
		grdSentMessages.attachEvent("onKeyPress",onMsgSentKeyPressed);
		grdSentMessages.attachEvent("onRowDblClicked",onMsgSentRowDoubleClicked);
		grdSentMessages.attachEvent("onRowSelect",onMsgSentRowSelected);
		
		grdSentMessages.init();
		grdSentMessages.setHeaderCursor("pointer,pointer,pointer,pointer");
		grdSentMessages.setSortImgState(true,1,"DESC");
		grdSentMessages.setColumnHidden(4,true);
		grdSentMessages.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	
}
function loadSentMessages(){
	grdSentMessages.loadXML("dashboard.do?mode=getSentMessages");
}
function onMsgSentRowSelected(id,idx_col){
	onRowSelected(grdSentMessagesId,grdSentMessages,id,idx_col);
}
function onMsgSentKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(grdSentMessagesId,grdSentMessages,keyCode,ctrl,shift);
}
function onMsgSentRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(grdSentMessagesId,grdSentMessages,id,idx_col);
}
var prevTab=0;
function showEmailType(typ){
	if(typ==0){
		if(prevTab!=0){
			$("msgReceived").className="boxTab";
			$("msgSent").className="boxDarkTab";
			$("msgSent").style.cursor="pointer";
			$("msgReceived").style.cursor="auto";
			$("GRD_SENT_MSGBOX").style.display="none";
			$("GRD_MSGBOX").style.display="block";
			prevTab=0;
			grdObj = grdMessages;
			nodataDivId = msgNoMessages;
			grdId = grdMessagesId;
			imgId="imgup_1";
			//if(!gids[grdId]){
				if(grdObj.getRowsNum()==0){
					showDiv(nodataDivId,1);
				}else{
					grdObj.objBox.className="objbox";
					hideAllRows(grdObj,false);
					showDiv(nodataDivId,0);
				}
				gids[grdId]=false;
				toggleImage(imgId, true);
				createCookie(grdId+userId,"1",360);
			//}
			
		}
	}else{
		if(prevTab!=1){
			$("msgReceived").className="boxDarkTab";
			$("msgSent").className="boxTab";
			$("msgReceived").style.cursor="pointer";
			$("msgSent").style.cursor="auto";
			$("GRD_SENT_MSGBOX").style.display="block";
			$("GRD_MSGBOX").style.display="none";
			prevTab=1;
			if(sentMessagesLoaded==0){
				loadSentMessages();	
				sentMessagesLoaded=1;
			}else{
			
			var grdObj = grdSentMessages;
			var nodataDivId = msgNoMessages;
			var grdId = grdSentMessagesId;
			var imgId="imgup_1";
			//if(!gids[grdId]){
				if(grdObj.getRowsNum()==0){
					showDiv(nodataDivId,1);
				}else{
					grdObj.objBox.className="objbox";
					hideAllRows(grdObj,false);
					showDiv(nodataDivId,0);
				}
				gids[grdId]=false;
				toggleImage(imgId, true);
				createCookie(grdId+userId,"1",360);
			//}
			}
		}
	}

}

function initToDos(){
		grdToDO = new dhtmlXGridObject(grdToDOId);
		grdToDO.imgURL = "images/"; 
		grdToDO.setHeader("&nbsp;,<bean:message key="dashboard.label.hdr.candidate"/>,<bean:message key="common.position"/>,<bean:message key="dashboard.label.hdr.todo"/>,<bean:message key="dashboard.label.hdr.duedate"/>,");
		grdToDO.setInitWidths("20,135,210,250,100,0");
		grdToDO.setColAlign("left,left,left,left,left,left");
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_POSITION_DETAILS">
			grdToDO.setColTypes("ro,link,link,link,ro,ro");
		</logic:equal>
		<logic:notEqual value="true" name="permissionSet" scope="session" property="PERMISSION_POSITION_DETAILS">
			grdToDO.setColTypes("ro,link,ro,link,ro,ro");
		</logic:notEqual>
		grdToDO.setColSorting("na,sort_name,cstr,cstr,todo_date_sort,na");
	    grdToDO.enableSmartRendering(true);
    	grdToDO.setAwaitedRowHeight(21);   
    	grdToDO.enableAutoHeight(true,maxHeight);
		grdToDO.attachEvent("onXLE",onGridLoadEnd);
		grdToDO.attachEvent("onKeyPress",onToDoKeyPressed);
		grdToDO.attachEvent("onRowDblClicked",onToDoRowDoubleClicked);
		grdToDO.attachEvent("onRowSelect",onToDoRowSelected);
		// grdToDO.setNoHeader(true);
		grdToDO.init();
		grdToDO.setHeaderCursor("pointer,pointer,pointer,pointer,pointer");
		grdToDO.setSortImgState(true,4,"ASC");
		grdToDO.setColumnHidden(5,true);
		grdToDO.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
}
function loadToDos(id, todo_type){	
	if(todo_type == '<%=DashboardConstants.TODO_LIST_TYPE_LIST%>') {
		$("GRD_TODO_ID").show();
		$("GRD_TODO").hide();
		$("GRD_GROUP_TODO_HEADER").hide();
		grdToDO.loadXML("dashboard.do?mode=getToDoList&toDoType=" + todo_type);
	} else {	
		var returnVal = getSortColumnAndOrder();
		var pars = "mode=getToDoList&toDoType=" + todo_type + "&sortOrder=" + returnVal[0] + "&sortByColumn=" + returnVal[1];		
		if(id != null)	 {
			id = id.replace(/__/g, " ");
			pars += "&groupItemId=" + id;
			var myAjax = ajaxCall("dashboard.do","get",pars,renderToDoDetails,reportError);
		} else {
			var myAjax = ajaxCall("dashboard.do","get",pars,renderToDoList,reportError);
		}		
	}
	eraseCookie("GRD_TODO" +userId);	
	createCookie("GRD_TODO"+userId,todo_type,360);
}

function getSortColumnAndOrder() {
	var sort_order = '';
	var sort_column = '';
	if($("col_candidate").innerHTML.indexOf(img_sort_asc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_ASC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_CANDIDATE%>';
	} else if($("col_candidate").innerHTML.indexOf(img_sort_desc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_DESC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_CANDIDATE%>';
	} else if($("col_position").innerHTML.indexOf(img_sort_asc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_ASC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_POSITION%>';
	} else if($("col_position").innerHTML.indexOf(img_sort_desc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_DESC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_POSITION%>';
	} else if($("col_todo").innerHTML.indexOf(img_sort_asc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_ASC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_TODO%>';
	} else if($("col_todo").innerHTML.indexOf(img_sort_desc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_DESC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_TODO%>';
	} else if($("col_due_date").innerHTML.indexOf(img_sort_asc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_ASC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_DUE_DATE%>';
	} else if($("col_due_date").innerHTML.indexOf(img_sort_desc) != -1) {
		sort_order = '<%=DashboardConstants.SORT_ORDER_DESC%>';
		sort_column = '<%=DashboardConstants.SORT_BY_DUE_DATE%>';
	}
	returnVal = new Array();
	returnVal[returnVal.length] = sort_order;
	returnVal[returnVal.length] = sort_column;
	return returnVal;
}

function renderToDoList(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var totalCount = op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		$("GRD_GROUP_TODO_HEADER").show();
		$("GRD_TODO").show();
		$("GRD_TODO_ID").hide();
		$('GRD_TODO').innerHTML=getReplaced(op);
		$('GRD_TODO').style.height="auto";
		$('TODO_COUNT').innerHTML=totalCount;
		
		if(lastOpenItem != null && lastOpenItem != '') {		
			var temp = lastOpenItem;
			lastOpenItem = '';
			var todo_type = getToDoType();	
			expandToDo(temp, todo_type);
		} 
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
		reloadWindow();
	}
	return false;
}
var lastOpenItem = null;
function expandToDo(id, type){
	if($('contentToDoId_'+id).style.display=='none'){				
		Effect.BlindDown('contentToDoId_'+id,{duration:0.3});
		if(lastOpenItem){
			Effect.BlindUp('contentToDoId_'+ lastOpenItem,{duration:0.3});
			$('imgtodo_'+lastOpenItem).src="images/ico_plus.gif";
			setSelected(lastOpenItem,false);
		}
		$('imgtodo_'+id).src="images/ico_minus.gif";
		lastOpenItem=id;
		setSelectedItem(lastOpenItem, true);
		setwaitToDo(id);
		loadToDos(id,type);		
	}else{		
		Effect.BlindUp('contentToDoId_'+id,{duration:0.3});
		$('imgtodo_'+id).src="images/ico_plus.gif";
		setSelectedItem(lastOpenItem,false);
		lastOpenItem='';
	}
}

function renderToDoDetails(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var divId = op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		$('contentToDoId_'+divId).innerHTML=getReplaced(op);
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
		reloadWindow();
	}
	return false;
}

function setSelected(idx, add){
	if(add){
		Element.addClassName('outercontent_'+idx,'divSelected');
	}else{
		Element.removeClassName('outercontent_'+idx,'divSelected');
	}
}

function setSelectedItem(idx, add){
	if(add){
		Element.addClassName('outerContentToDoId_'+idx,'divSelected');
	}else{
		Element.removeClassName('outerContentToDoId_'+idx,'divSelected');
	}
}

function setwaitToDo(id){
	$('contentToDoId_'+id).innerHTML="<table><tr><td><img src=images/wait.gif /></td><td>&nbsp;<bean:message key="common.please_wait"/></td></tr></table>";
}
function getReplaced(txt){
	txt = txt.replace(/(&amp;nbsp;)/g,'&nbsp;');
	txt = txt.replace(/(&gt;)/g,'>');
	txt = txt.replace(/(&lt;)/g,'<');
	txt = txt.replace(/(&amp;gt;)/g,'&gt;');
	txt = txt.replace(/(&amp;lt;)/g,'&lt;');
	txt = txt.replace(/(&amp;quot;)/g,'&quot;');
	return txt;
}
function refreshToDos(){
	if(grdToDO) {
		grdToDO.clearAll();
	}
	var todo_type = getToDoType();	
	loadToDos(null, todo_type);
}

function sortToDos(){
	 var state=grdToDO.getSortingState();
	 if (state[1].toUpperCase() == 'ASC') {
			order='asc';		
		} else {		
			order='desc';			
		}
	 grdToDO.sortRows(state[0],null,order);
	//grdToDO.sortRows(state[0],"cus",order);
}

function onChangeToDoGrouping(){
	lastOpenItem = null;
	var todo_type = todoGroupBySelectBox.getSelectedId();
	if(grdToDO) {
		grdToDO.clearAll();
	}
	loadToDos(null, todo_type);
}
function onToDoRowSelected(id,idx_col){
	onRowSelected(grdToDOId,grdToDO,id,idx_col);
}
function onToDoKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(grdToDOId,grdToDO,keyCode,ctrl,shift);
}
function onToDoRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(grdToDOId,grdToDO,id,idx_col);
}

function initPositionSummary(){
		grdPositions = new dhtmlXGridObject(grdPositionsId);
		grdPositions.imgURL = "images/";
		setPositionsSummaryGrid(grdPositions); 
		grdPositions.setInitWidths("20,153,100,110,65,80,80,50,55,0,0");
		grdPositions.setColAlign("left,left,left,left,center,center,center,center,center,center,left");
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_POSITION_DETAILS">
			grdPositions.setColTypes("ro,link,ro,ro,ro,ro,ro,ro,ro,ro,estat");
		</logic:equal>
		<logic:notEqual value="true" name="permissionSet" scope="session" property="PERMISSION_POSITION_DETAILS">
			grdPositions.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,estat");
		</logic:notEqual>
		grdPositions.setColSorting("position_summary_priority_sort,cstr,cstr,cstr,na,na,na,na,na,na,na");
		//grdPositions.enableAutoHeight(true,maxHeight); //disabled because autoheight don't work with 
	    grdPositions.enableSmartRendering(true);
    	grdPositions.setAwaitedRowHeight(21);   
		grdPositions.attachEvent("onXLE",onGridLoadEnd);
		grdPositions.attachEvent("onKeyPress",onPosKeyPressed);
		grdPositions.attachEvent("onRowSelect",onPosRowSelected);
		
		<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_POSITION_DETAILS">
		grdPositions.attachEvent("onRowDblClicked",onPosRowDoubleClicked);
		</logic:equal>
		
		grdPositions.init();
		grdPositions.setHeaderCursor("pointer,pointer,pointer,pointer,na,na,na,na,na,na");		
		grdPositions.setSortImgState(true,0,"ASC");
		grdPositions.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
		//grdPositions.setColumnHidden(7,true);
}
function setPositionsSummaryGrid(grdPositions){
	grdPositions.setHeader("&nbsp;,<bean:message key="common.position"/>,<%=Utils.getBlankIfNull(DataViewUtils.getHeaderMapping(userId,UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG,DataViewConstants.HEADER1))%>,<%=Utils.getBlankIfNull(DataViewUtils.getHeaderMapping(userId,UserConstants.DATA_VIEW_TYPE_DASHBOARD_POSITION_CONFIG,DataViewConstants.HEADER2))%>,<bean:message key="common.vacancies"/>,<bean:message key="dashboard.label.hdr.inprocess"/>,<bean:message key="common.pending_offers"/>,<bean:message key="common.joined"/>,<bean:message key="common.rejected"/>,&nbsp;,");
}
function loadPositionSummary(){
	grdPositions.loadXML("dashboard.do?mode=getPositionSummary");
}
function onPosRowSelected(id,idx_col){
	onRowSelected(grdPositionsId,grdPositions,id,idx_col);
}
function onPosKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(grdPositionsId,grdPositions,keyCode,ctrl,shift);
}
function onPosRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(grdPositionsId,grdPositions,id,idx_col);
}
function initUpcomingEvents(){
		grdEvents = new dhtmlXGridObject(grdEventsId);
		grdEvents.imgURL = "images/"; 
		grdEvents.setHeader(",<bean:message key="dashboard.label.hdr.candidate"/>,<bean:message key="dashboard.label.hdr.status"/>,<bean:message key="dashboard.label.hdr.event"/>,<bean:message key="dashboard.label.hdr.time"/>,");
		grdEvents.setInitWidths("20,130,140,286,140,0");
		grdEvents.setColAlign("left,left,left,left,left,left");
		grdEvents.setColTypes("ro,link,ro,ro,link,ro");
		grdEvents.setColSorting("na,cstr,cstr,cstr,event_date_sort,na");
		grdEvents.enableAutoHeight(true,maxHeight);
		grdEvents.attachEvent("onXLE",onGridLoadEnd);
		grdEvents.attachEvent("onKeyPress",onEventsKeyPressed);
		grdEvents.attachEvent("onRowDblClicked",onEventsRowDoubleClicked);
		grdEvents.attachEvent("onRowSelect",onEventsRowSelected);
		grdEvents.init();
		grdEvents.setHeaderCursor("pointer,pointer,pointer,pointer,");
		grdEvents.setSortImgState(true,4,"DESC");
		grdEvents.setColumnHidden(5,true);
		grdEvents.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
}
function loadUpcomingEvents(){
	grdEvents.loadXML("dashboard.do?mode=getUpcomingEvents");
}
function onEventsRowSelected(id,idx_col){
	onRowSelected(grdEventsId,grdEvents,id,idx_col);
}
function onEventsKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(grdEventsId,grdEvents,keyCode,ctrl,shift);
}
function onEventsRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(grdEventsId,grdEvents,id,idx_col);
}

function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == grdMessagesId){
		switch(obj.cell._cellIndex){
			case 0:
				return "<bean:message key="dashboard.tooltip.delete"/>";
				break;
			case 3:
				return "<bean:message key="dashboard.tooltip.details"/>";
				break;
			case 4:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"applicantName");
				break;
		}
	}else if(grdId == grdSentMessagesId){
		switch(obj.cell._cellIndex){
			case 2:
				return "<bean:message key="dashboard.tooltip.details"/>";
				break;
			case 3:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"applicantName");
				break;
		}
	}else if(grdId == grdToDOId){
		switch(obj.cell._cellIndex){
			case 0:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"flags");
				break;
			case 1:
				return "";
				//return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_I_Title");
				break;
			case 2:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_II_Title");
				break;	
			case 3:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"todo");
				break;
		}
	}else if(grdId == grdPositionsId){
		switch(obj.cell._cellIndex){
		case 0:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"priorityLevel");
			break;
		case 1:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"positionTitle");
			break;
		case 2:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"dynamicColumn1");
			break;
		case 3:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"dynamicColumn2");
			break;
		case 4:
			return "<bean:message key="dashboard.tooltip.details"/>";
			break;
		case 5:
			return "<bean:message key="dashboard.tooltip.details"/>";
			break;
		case 6:
			return "<bean:message key="dashboard.tooltip.details"/>";
			break;
		}
	}else if(grdId == grdEventsId){
		switch(obj.cell._cellIndex){
		case 0:
			if(obj.grid.getUserData(obj.cell.parentNode.idd,"type") == "<%=CalendarConstants.CALENDAR_ITEM_APPOINTMENT%>") {
				return "";
			} else {
				return "<bean:message key="common.delete"/>";
			}
			
			break;
		case 1:
			return "";
			//return obj.grid.getUserData(obj.cell.parentNode.idd,"applicantName");
			break;
		case 4:
			if(obj.grid.getUserData(obj.cell.parentNode.idd,"type") == "<%=CalendarConstants.CALENDAR_ITEM_APPOINTMENT%>") {
				return "<bean:message key="dashboard.tooltip.appointment"/>";
			} else {
				return "<bean:message key="dashboard.tooltip.reminder"/>";
			}			
			break;
		}
	}
	//if no special tooltip - return current value
	return unescapeHTML(obj.cell.innerHTML);

}

eXcell_link.prototype.getTitle=function(){
	return getCustomTitle(this);
}

dhtmlXGridCellObject.prototype.getTitle=function(){
	return getCustomTitle(this);
}

function msg_date_sort(a,b,order,aId,bId){
	var a0=grdMessages.cells(aId,5).getValue();
	var b0=grdMessages.cells(bId,5).getValue();
	return custom_date_sort(a,b,order,a0,b0);
}
function sent_msg_date_sort(a,b,order,aId,bId){
	var a0=grdSentMessages.cells(aId,4).getValue();
	var b0=grdSentMessages.cells(bId,4).getValue();
	return custom_date_sort(a,b,order,a0,b0);
}

function todo_date_sort(a,b,order,aId,bId){
	var a0=grdToDO.getUserData(aId,"actualDueDate");
	var b0=grdToDO.getUserData(bId,"actualDueDate");
	return custom_date_sort(a,b,order,a0,b0);
}

function event_date_sort(a,b,order,aId,bId){
	var a0=grdEvents.cells(aId,4).getValue();
	var b0=grdEvents.cells(bId,4).getValue();
	return custom_date_sort(a,b,order,a0,b0);
}
function custom_date_sort(a,b,order,a0,b0){
	a0 = getCustomDate(a0);
	b0 = getCustomDate(b0);
	if (a0==b0) {
		if (order=="asc")
			return (a>b)?1:-1;
		else
			return (a<b)?1:-1;
	}
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
	return newdate.getTime();
}

function sort_name(a,b,order,aId,bId) {
	a0 = grdToDO.getUserData(aId,"Col_I_Title");
	b0 = grdToDO.getUserData(bId,"Col_I_Title");	
	return sort_data(a0,b0,order);
}

function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

/*Added by shantanu */
function position_summary_priority_sort(a,b,order,aId,bId){	
	var a0=grdPositions.cells(aId,9).getValue();
	var b0=grdPositions.cells(bId,9).getValue();
	return custom_value_sort(a,b,order,a0,b0);
}
function custom_value_sort(a,b,order,a0,b0){	
	if (a0==b0) {
		if (order=="asc")
			return (a>b)?1:-1;
		else
			return (a<b)?1:-1;
	}
	if (order=="asc")
		return (a0>b0)?1:-1;
	else
		return (a0<b0)?1:-1;
}
/**/

/* grid on click functions and links in grid */
function onClickApplicant(aId){
	//function defined in left panel
	viewApplicant(aId);
}
function onClickMessage(mId){
	var url='selectionProcess.do?mode=viewMessage&communicationId='+mId+'&communicationType=<%=SelectionProcessConstants.INTERACTION_MESSAGE%>&showDelete=1';
	window.setTimeout("showInPopUp('"+url+"',550, 340,onClickDeleteMessage,false);", 10);
}
function onClickSentMessage(mId){
	var url='selectionProcess.do?mode=viewMessage&communicationId='+mId+'&communicationType=<%=SelectionProcessConstants.INTERACTION_MESSAGE%>';
	window.setTimeout("showInPopUp('"+url+"',550, 340,onClickDeleteMessage,false);", 10);
}

function onClickPosition(pId){
	//function defined in left panel
	viewPositionSummary(pId);
}


function refreshPosition(){
	if(grdPositions)
		grdPositions.clearAll();
	var positionGridType = getPositionGrdType();
	loadPositionSummaryGrouped(null,positionGridType);
}
function onClickPositionPriority(pId){
	var url = "position.do?mode=openPositionPriority&positionId="+pId;
	window.setTimeout("showInPopUp('"+url+"',350, 180,refreshPosition,true);", 10);	
}

function onClickCandidates(positionId){
	//TO DO redirect user to select page with filter on selected position
	window.location="selectionProcess.do?mode=select&positionId="+positionId;
}
function onClickHired(positionId){
	//TO DO redirect user to select page with filter on selected position
	window.location="selectionProcess.do?mode=accept&positionId="+positionId;
}
function onClickJoined(positionId){
	//TO DO redirect user to select page with filter on selected position
	window.location="selectionProcess.do?mode=accept&filterByPosition="+positionId+"#JOINED";
}

function onClickRejected(positionId){
	window.location="position.do?mode=positionSummary&showRej=1&positionId="+positionId+"#REJECTED";
}

function onClickToDo(aId,actionRequired){	
	if(actionRequired==<%=DashboardConstants.ACTION_REQUIRED_SCHEDULE%>){
		window.location="calendar.do?mode=calendarHome&selectedApplicant=" + aId;	
	}else if(actionRequired==<%=DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL%>){
		var url = 'requisitionfeedback.do?mode=feedback&positionId='+aId;
		window.setTimeout("showInPopUp('"+url+"',650, 518,refreshToDos,true);", 10);
	}else if(actionRequired==<%=DashboardConstants.ACTION_REQUIRED_BUDGET_APPROVAL%>){
		var url = 'budgetApproval.do?mode=feedback&budgetItemId='+aId;
		window.setTimeout("showInPopUp('"+url+"',650, 380,refreshToDos,true);", 10);
	} else if(actionRequired==<%=DashboardConstants.ACTION_CLEAR_DRAFT%>){
		var url = 'inbox.do?mode=newEmail&newEmailType=<%=InboxConstants.EMAIL_TYPE_SEND_DRAFT%>&emailId=' + aId + '&emailLocation=' + '<%=InboxConstants.EMAIL_LOCATION_INBOX%>&folderId=<%=InboxConstants.INBOX_FOLDER_INBOX%>';
		window.setTimeout("showInPopUp('"+url+"',800, 650,refreshToDos,true);", 10);	
	}else {
		var url = 'selectionProcess.do?mode=moveApplicantUpOrDown&applicantId='+aId;
		window.setTimeout("showInPopUp('"+url+"',900, 570,refreshToDos,true);", 10);
	}
}
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function onClickAppointment(appointmentId, aId){
    window.location= "calendar.do?mode=calendarHome&appointmentId=" + appointmentId + "&selectedApplicant=" + aId + "&isAppointmentFullyEditable=true";
}

function onClickReminder(remId,applicantId){
	//function defined in left panel
	var url = 'selectionProcess.do?mode=addReminder&reminderId='+remId+'&applicantId='+applicantId;
	if(applicantId == "0" || applicantId == '') {
		window.setTimeout("showInPopUp('"+url+"',550, 490,refreshReminder,true);", 10);
	} else {
		window.setTimeout("showInPopUp('"+url+"',550, 320,refreshReminder,true);", 10);
	}
}

function refreshReminder(){
	grdEvents.clearAll();
	loadUpcomingEvents();
}

function onClickDeleteMessage(mId){
	var pars = "mode=deleteMessage&messageIds=" +mId;
	var myAjax = ajaxCall("dashboard.do","get",pars,deleteRow,reportError);
}
//Start Delete Reminder

function onClickDeleteReminder(remId){
	if(confirm("You are about to delete this Reminder ?")){
		var pars = "mode=deleteReminder&reminderId="+remId;
		var myAjax = ajaxCall("dashboard.do",'get',uncache(pars),onDeleteReminder, reportError);
  	}
}   
 
function onDeleteReminder(request){
 	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="dashboard.errors.reminder.cannot_delete"/>');
		return;
	}
	//get returned deleted ids and delete them from grid
	id = parseIds(xmlFile);
	grdEvents.deleteRow("<%=CalendarConstants.CALENDAR_ITEM_REMINDER%>_" + id);
	grdEvents.clearSelection();  
}
//End Delte Reminder

function deleteRow(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="dashboard.error.delete_message"/>');
		return;
	}
	var msgIds=getIds(xmlFile);
	for(var I=0; I<msgIds.length; I++){
		grdMessages.deleteRow(msgIds[I]);
	}
	grdMessages.clearSelection();
	setRowCount(grdMessages,msgCount);
	checkNoMessages(grdMessages,msgNoMessages);
	adjustWidth(grdMessages,0);
}



//common grid event handlers
function adjustWidth(grdObj,onLoadCheck){
//pageSize
	if((onLoadCheck==1 && pageSize>= grdObj.getRowsNum()) || (onLoadCheck==0 && pageSize == grdObj.getRowsNum())){
		var idx = getLastvisibleColumn(grdObj,grdObj.getColumnCount()-1);
		//grdObj.setColWidth(idx, grdObj.getColWidth(idx)+16);
	}
}

function getLastvisibleColumn(grdObj,idx){
	if(!grdObj.isColumnHidden(idx)){
		return idx;
	}else if(idx>0){
		return getLastvisibleColumn(grdObj,idx-1);
	}else {
		return -1;
	}
	
}

function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==grdMessagesId){
			onClickMessage(id);		
		}else if(grdId==grdSentMessagesId){
			onClickSentMessage(id);		
		}else if(grdId==grdToDOId){
			var actionRequired = grdObj.getUserData(id,"actionRequired");
			var param = grdObj.getUserData(id,"param");
			onClickToDo(param,actionRequired);
		}else if(grdId==grdPositionsId){
			onClickPosition(id);
		}else if(grdId==grdEventsId){
			var appId = grdObj.getUserData(id,"appId");
			onClickAppointment(appId, id);
		}		
		break;
	case 46:
		//delete key
		if(grdId==grdMessagesId){
			onClickDeleteMessage(id);
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
	if(grdId==grdMessagesId){
		onClickMessage(id);		
	} else if(grdId==grdSentMessagesId){
		onClickSentMessage(id);		
	} else if(grdId==grdToDOId){		
		var actionRequired = grdObj.getUserData(id,"actionRequired");
		var param = grdObj.getUserData(id,"param");		
		onClickToDo(param,actionRequired);
	}else if(grdId==grdPositionsId){
		onClickPosition(id);
	}else if(grdId==grdEventsId){
		var appId = grdObj.getUserData(id,"appId");
		onClickAppointment(appId, id);
	}
	
}
function onRowSelected(grdId, grdObj, id, idx_col){
	if(grdMessages && grdId!=grdMessagesId){
		grdMessages.clearSelection();		
	}
	if(grdSentMessages && grdId!=grdSentMessagesId){
		grdSentMessages.clearSelection();		
	}
	if(grdToDO && grdId!=grdToDOId){
		grdToDO.clearSelection();
	}
	if(grdPositions && grdPositions && grdId!=grdPositionsId){
		grdPositions.clearSelection();
	}
	if(grdEvents && grdId!=grdEventsId){
		grdEvents.clearSelection();
	}
	
}

function onGridLoadEnd(grd, itemsCnt){
	var grdId = grd.entBox.id;
	if(grdId==grdMessagesId){
		setGridStatusFromCookies(grdId);
		toggleGrid("imgup_1",grdId);
		setRowCount(grdMessages,msgCount);
		adjustWidth(grdMessages,1);
	} else if(grdId==grdSentMessagesId){
		/* not required as this is not called on page load*/
		setGridStatusFromCookies(grdId);		
		toggleGrid("imgup_1",grdId);		
		setRowCount(grdSentMessages,msgSentCount);
		adjustWidth(grdSentMessages,1);		
	}else if(grdId==grdToDOId){
		setRowCount(grdToDO,ToDOCount);
		adjustWidth(grdToDO,1);
		sortToDos();
	}else if(grdId==grdPositionsId){
		setGridStatusFromCookies(grdId);
		toggleGrid("imgup_3",grdId);
		setRowCount(grdPositions,PositionCount);
		adjustWidth(grdPositions,1);
	}else if(grdId==grdEventsId){
		setGridStatusFromCookies(grdId);
		toggleGrid("imgup_4",grdId);
		setRowCount(grdEvents,EventsCount);
		adjustWidth(grdEvents,1);
	}
}


/*Common Functions */
function setRowCount(gId,countDivId){
	$(countDivId).innerHTML=gId.getRowsNum();
}
function checkNoMessages(gId,nodataDivId){
	if(gId.getRowsNum()==0){
		//showDiv(nodataDivId,1);
	}else{
		//showDiv(nodataDivId,0);
	}
}
function showDiv(divId,show){
	if(show==0){
		$(divId).style.display="none";
	}else{
		$(divId).style.display="block";
	}
}

var hidden=false;
var gids = Array();
gids[grdMessagesId]=false;
gids[grdSentMessagesId]=true;
gids[grdToDOId]=false;
gids[grdPositionsId]=false;
gids[grdEventsId]=false;

function toggleMessgesGrids(imgId){
	if($("GRD_MSGBOX").style.display=="block"){
		toggleGrid(imgId,"GRD_MSGBOX");
	}else{
		toggleGrid(imgId,"GRD_SENT_MSGBOX");
	}
}

function toggleGrid(imgId,grdId){
	var grdObj ;
	var nodataDivId="";
	if(grdId==grdMessagesId){
		grdObj = grdMessages;
		nodataDivId = msgNoMessages;
	}else if(grdId==grdSentMessagesId){
		grdObj = grdSentMessages;
		nodataDivId = msgNoMessages;
	}else if(grdId==grdPositionsId){
		grdObj = grdPositions;
		nodataDivId=msgNoPositions;
	}else if(grdId==grdEventsId){
		grdObj = grdEvents;
		nodataDivId=msgNoEvents;
	}
	toggleGD(imgId,grdObj,grdId, nodataDivId);
}
function  toggleGD(imgId,grdObj,grdId, nodataDivId){
	var positionGridType = readCookie("GRD_POSITIONS_"+userId);
	if(grdObj==grdPositions && '<%=DashboardConstants.POSITION_GRID_TYPE_LIST%>'!=positionGridType){
		togglePositionGridGrouped(imgId,grdObj,grdId, nodataDivId);
	}else{
		toggle(imgId,grdObj,grdId, nodataDivId);	
	}
}
function toggle(imgId,grdObj,grdId,nodataDivId){

	eraseCookie(grdId +userId);	

	if(gids[grdId]){ 
		if(grdObj.getRowsNum()==0){
			showDiv(nodataDivId,1);
			if(grdId==grdPositionsId || grdId==grdToDOId){
				$(grdId).style.height='20px';
			}
		}else{
			grdObj.objBox.className="objbox";
			if(grdId==grdPositionsId || grdId==grdToDOId){
				$(grdId).style.height='232px';
			}
			hideAllRows(grdObj,false);
			showDiv(nodataDivId,0);
		}
		gids[grdId]=false;
	    toggleImage(imgId, true);
		createCookie(grdId+userId,"1",360);
	    
	}else{
		grdObj.objBox.className="objboxMy";
		if(grdId==grdPositionsId || grdId==grdToDOId){
			$(grdId).style.height='19px';
		}
		hideAllRows(grdObj,true);
		gids[grdId]=true;
		toggleImage(imgId, false);
		showDiv(nodataDivId,0);
		createCookie(grdId+userId,"0",360);
	}
}
function togglePositionGridGrouped(imgId,grdObj,grdId, nodataDivId){
	eraseCookie(grdId +userId);	
	if(gids[grdId]){
		showAllPositionGrids();
		gids[grdId]=false;
		toggleImage(imgId, true);
		createCookie(grdId+userId,"1",360);
	}else{
		hideAllPositonGrids();
		gids[grdId]=true;
		toggleImage(imgId, false);
		createCookie(grdId+userId,"0",360);
	}
}
function toggleImage(imgId, down){
	if(down){
		$(imgId).src="images/ico_down1.gif";//$(imgId).src.replace(".gif","1.gif");
	}else{
		$(imgId).src="images/ico_down.gif";//$(imgId).src.replace("1.gif",".gif");
	}
}

function hideAllRows(grdObj, state){
	if(grdObj){
		 var ids = grdObj.getAllRowIds("|");
		 var aIds = ids.split("|");
		 for(var i=0; i<aIds.length; i++){
		 	grdObj.setRowHidden(aIds[i],state);
		 }
	}
}
function doOnLoad(){
	initPopUp();	
	initMessages();
	initUpcomingEvents();
	initSentMessages();
	initToDos();
	window.setTimeout('loadContent()',10);
}
function loadContent(){
	loadMessages();	
	var todo_type = getToDoType();
	
	loadToDos(null, todo_type);

	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SHOW_POSITION_SUMMARY_DASHBOARD">
		var positionGridType = getPositionGrdType();
		loadPositionSummaryGrouped(null,positionGridType);
	</logic:equal>
	
	loadUpcomingEvents();
	<logic:present name="approval" scope="request">
		<logic:present name="pId" scope="request">
			onClickToDo('<bean:write name="pId" scope="request"/>','<%=DashboardConstants.ACTION_REQUIRED_REQUISITION_APPROVAL%>');
		</logic:present>
	</logic:present>
}
window.onload = doOnLoad;


//cookies functions
function setGridStatusFromCookies(grdId){
	var grdStatus = readCookie(grdId+userId);
	if(grdStatus==1){
		gids[grdId]=true;
	}else{
		gids[grdId]=false;
	}
}

var chkedChkBox='images/checkboxchecked.gif';
var unchkedChkBox='images/checkboxunchecked.gif';	

function changeCheckboxState(obj) {
	var propertyVal = '';
	if(obj.src.indexOf(chkedChkBox) != -1) {
		obj.src = unchkedChkBox;
		propertyVal = '0';
	} else {
		obj.src = chkedChkBox;
		propertyVal = '1';
	}
	var pars = "mode=updateApplicationSetting&propertyName=<%=GlobalConstants.PROPERTY_SHOW_REMINDER%>&propertyValue=" + propertyVal;
	var myAjax = ajaxCall("adminHome.do","get",pars,onPropertyUpdate,reportError);
}	

function onPropertyUpdate(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="admin_application_settings.error.save_show_reminder_setting"/>');
		changeCheckboxState($("show_reminder"));
		return;
	}
	grdEvents.clearAll();
	loadUpcomingEvents();
}
var img_sort_asc = "images/sort_asc.gif";
var img_sort_desc = "images/sort_desc.gif";
function sortData(obj) {
	if($(obj).innerHTML.indexOf(img_sort_asc) != -1) {
		$("sortImg").src = img_sort_desc;
	} else if($(obj).innerHTML.indexOf(img_sort_desc) != -1) {
		$("sortImg").src = img_sort_asc;
	} else {
		$("col_candidate").innerHTML= '<bean:message key="dashboard.label.hdr.candidate"/>';
		$("col_position").innerHTML= '<bean:message key="common.position"/>';
		$("col_todo").innerHTML= '<bean:message key="dashboard.label.hdr.todo"/>';
		$("col_due_date").innerHTML= '<bean:message key="dashboard.label.hdr.duedate"/>';
		obj.innerHTML = obj.innerHTML += '&nbsp;&nbsp;<img style="mergin-left:10px;" id="sortImg" src="images/sort_asc.gif" />'
	}
	if(lastOpenItem != null && lastOpenItem != '') {
		var todo_type = getToDoType();		
		loadToDos(lastOpenItem, todo_type);
	}
}
function getToDoType() {
	var todo_type = readCookie("GRD_TODO"+userId);
	if(todo_type == null) {
		todo_type = '<%=DashboardConstants.TODO_LIST_TYPE_LIST%>';
		todoGroupBySelectBox.setSelected(0);
	}else{
		todoGroupBySelectBox.setSelected(todoGroupBySelectBox.getIndexWithId(todo_type));
	}	
	return todo_type;
}
function searchForPosition(){
	var url = 'doSearch.do?mode=selectPositionForSearch';
	showInPopUp(url, 520, 200,doSearchForPosition);
}

function doSearchForPosition(returnVal){
	if(returnVal !='' && returnVal !=null){
		var retArr = returnVal.split("_");
		var posId = retArr[0];
		var deptId = retArr[1];
		var locationId = retArr[2];
		
		if(posId!='' && posId!='-1'){
			var url = 'doSearch.do?mode=doSearchForPosition&positionId='+posId+"&departmentId="+deptId+"&locationId="+locationId;
			window.location=url;
		}
	}
	return true;
}

/*****************Postion Summary Grid Grouping related functions***************************************/
var lastOpenPostionGroupItem = '';
function onChangePositionGridGrouping(){
	lastOpenPostionGroupItem='';
	var positionGrd_type = positionSummaryGroupBy.getSelectedId();
	if(grdPositions){
		grdPositions.clearAll();
	}
	loadPositionSummaryGrouped(null,positionGrd_type);
}
function loadPositionSummaryGrouped(id,positionGrd_type){
	var params="mode=getPositionSummaryDetails&postionSummaryGridType="+positionGrd_type;
	var params2 = "mode=getPositionSummaryGrouped&postionSummaryGridType="+positionGrd_type;
	if(positionGrd_type=='<%=DashboardConstants.POSITION_GRID_TYPE_LIST%>'){
		$('GRD_POSITION_HEADER').hide();
		showAllPositionGrids();
		initPositionSummary();
		grdPositions.loadXML("dashboard.do?"+params);
	}else{
		if(id!=null){
			id = id.replace(/__/g, " ");
			params += "&positionGroupItemId=" + id;
			var myAjax = ajaxCall("dashboard.do","get",params,renderPositionDetails,reportError);
		}else{
			var myAjax = ajaxCall("dashboard.do","get",params2,renderPositionSummaryGrid,reportError);
		}
	}
	eraseCookie("GRD_POSITIONS_"+userId);	
	createCookie("GRD_POSITIONS_"+userId,positionGrd_type,360);
}

function renderPositionSummaryGrid(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))
			return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var totalCount = op.substring(0,op.indexOf("|"));
		if(totalCount!=0){
			op = op.substring(op.indexOf("|")+1);
			$('GRD_POSITION_HEADER').show();
			$('GRD_POSITION').innerHTML=getReplaced(op);
			$('GRD_POSITION').style.height="auto";
			$('POSITION_COUNT').innerHTML=totalCount;
			if(lastOpenPostionGroupItem != null && lastOpenPostionGroupItem != '') {		
				var temp = lastOpenPostionGroupItem;
				lastOpenPostionGroupItem = '';
				var positionGrd_type = getPositionGrdType();	
				expandPostionGroup(lastOpenPostionGroupItem, positionGrd_type);
			}
			setGridStatusFromCookies(grdPositionsId);
			toggleGrid("imgup_3",grdPositionsId);
		}else{
			$('POSITION_COUNT').innerHTML=totalCount;
			$('GRD_POSITION').innerHTML='';
		//	$('GRD_POSITION').innerHTML='<bean:message key="common.no" />&nbsp;<bean:message key="common.positions" />&nbsp;<bean:message key="common.available" />';
		}
		
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
		reloadWindow();
	}
	return false;
}
function renderPositionDetails(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var divId = op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		$('positionContentDivId_'+divId).innerHTML=getReplaced(op);
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
		reloadWindow();
	}
	return false;
}
function expandPostionGroup(id,positionGrd_type){
	if($('positionContentDivId_'+id).style.display=='none'){				
		Effect.BlindDown('positionContentDivId_'+id,{duration:0.3});
		if(lastOpenPostionGroupItem!=''){
			Effect.BlindUp('positionContentDivId_'+ lastOpenPostionGroupItem,{duration:0.3});
			$('imgPostionGroup_'+lastOpenPostionGroupItem).src="images/ico_plus.gif";
			setPositionGroupSelected(lastOpenPostionGroupItem,false);
		}
		$('imgPostionGroup_'+id).src="images/ico_minus.gif";
		lastOpenPostionGroupItem=id;
		setPositionGroupSelectedItem(lastOpenPostionGroupItem, true);
		setwaitPositionGrid(id);
		loadPositionSummaryGrouped(id,positionGrd_type);		
	}else{		
		Effect.BlindUp('positionContentDivId_'+id,{duration:0.3});
		$('imgPostionGroup_'+id).src="images/ico_plus.gif";
		setPositionGroupSelectedItem(lastOpenPostionGroupItem,false);
		lastOpenPostionGroupItem='';
	}
}
function setwaitPositionGrid(id){
	$('positionContentDivId_'+id).innerHTML="<table><tr><td><img src=images/wait.gif /></td><td>&nbsp;<bean:message key="common.please_wait"/></td></tr></table>";
}
function getPositionGrdType() {
	var positionGridType = readCookie("GRD_POSITIONS_"+userId);
	if(positionGridType==null || positionGridType == '<%=DashboardConstants.POSITION_GRID_TYPE_LIST%>'){
		positionGridType = '<%=DashboardConstants.POSITION_GRID_TYPE_LIST%>';
		positionSummaryGroupBy.setSelected(0);		
	}else {
		positionSummaryGroupBy.setSelected(positionSummaryGroupBy.getIndexWithId(positionGridType));
	}
	return positionGridType;
}
function setPositionGroupSelectedItem(idx, add){
	if(idx!=null){
		if(add){
			Element.addClassName('positionOuterContentDivId_'+idx,'divSelected');
		}else{
			Element.removeClassName('positionOuterContentDivId_'+idx,'divSelected');
		}
	}
}
function setPositionGroupSelected(idx, add){
	if(idx!=null){
		if(add){
			Element.addClassName('positionContentDivId_'+idx,'divSelected');
		}else{
			Element.removeClassName('positionContentDivId_'+idx,'divSelected');
		}
	}
}
function showAllPositionGrids(){
	$('GRD_POSITION').show();
}
function hideAllPositonGrids(){
	$('GRD_POSITION').hide();
}
</script>