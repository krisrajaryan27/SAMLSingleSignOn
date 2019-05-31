<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>
<%@page import="com.talentPool.documents.DocumentConstants"%>
<%@page import="com.talentPool.documents.utils.DocumentUtils"%>
<%@page import="com.talentPool.masters.dataobject.InboxFolderData"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<link rel="STYLESHEET" type="text/css" href="themes/default/dhtmlXGridInbox.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">		
<script>
function getCustomTitle(obj){
		switch(obj.cell._cellIndex){
			case 0:
				return '';
				break;
			case 1:				
				return '';
				break;
			case 2:
				return obj.cell.innerHTML.replace("&lt;","<").replace("&gt;",">");
				break;
			case 3:				
				return obj.cell.innerHTML.replace("&lt;","<").replace("&gt;",">");
				break;
			
		}	
	return obj.cell.innerHTML;
}



/*Menu Script*/
var menu = new TpMenu();
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SEND_EMAIL_FROM_IMPORT">
var mnu_email = new TpMenu({type:'menu', id: 'email', image: 'images/ico_mail.gif', title:'<bean:message key="inbox.button.label.email"/>', onclick:'onClickMenu', width:'150px'});
menu.addItem(mnu_email);

var mnu_re = new TpMenu({type:'menu', id: 're', image:'images/ico_mail.gif', title:'<bean:message key="inbox.button.label.reply"/>', onclick:'onClickMenu'});
menu.addItem(mnu_re);

var mnu_fwd = new TpMenu({type:'menu', id: 'fwd', image:'images/ico_fwd.gif', title:'<bean:message key="inbox.button.label.forward"/>', onclick:'onClickMenu'});
menu.addItem(mnu_fwd);
</logic:equal>
var mnu_unread = new TpMenu({type:'menu', id: 'unread',  title:'<bean:message key="inbox.button.label.mark_unread"/>', onclick:'onClickMenu'});
menu.addItem(mnu_unread);

var mnuhandlermore = new TpMenuHandler(menu,{});
mnuhandlermore.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:-11});

function showMenuMore(applicantId, elementId){
	mnuhandlermore.show(applicantId,elementId);
}
function onClickMenu(mnu, opt){
	var id = mnu.getId();
	if(id == "email"){
		newEmail('<%=InboxConstants.EMAIL_TYPE_NEW%>'); 
	}else if(id == "re"){
		newEmail('<%=InboxConstants.EMAIL_TYPE_REPLY%>'); 
	}else if(id == "fwd"){
		newEmail('<%=InboxConstants.EMAIL_TYPE_FORWARD%>'); 
	}else if(id == "unread"){
		markUnread();
	}
}

var menu_moveto = new TpMenu();

<logic:iterate id="folder" name="systemFolders" scope="request" type="InboxFolderData">
	<logic:notEqual name="folder" property="folderId" value="<%=InboxConstants.INBOX_FOLDER_SENT%>">
	var mnu_moveto_menu = new TpMenu({type:'menu', id: '<bean:write name="folder" property="folderId"/>', width:'200px', title:'<bean:write name="folder" property="folderName"/>', onclick:'onClickMenuMoveTo'});
	menu_moveto.addItem(mnu_moveto_menu);
	</logic:notEqual>
</logic:iterate>

<logic:iterate id="folder" name="userFolders" scope="request" type="InboxFolderData">
	var mnu_moveto_menu = new TpMenu({type:'menu', id: '<bean:write name="folder" property="folderId"/>', width:'200px',  title:'<bean:write name="folder" property="folderName"/>', onclick:'onClickMenuMoveTo'});
	menu_moveto.addItem(mnu_moveto_menu);
</logic:iterate>

var mnuhandlermoveto = new TpMenuHandler(menu_moveto,{});
mnuhandlermoveto.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:-5});

function showMenuMoveTo(applicantId, elementId){
	mnuhandlermoveto.show(applicantId,elementId);
}
function onClickMenuMoveTo(mnu, opt){
	var folderId = mnu.getId();
	var id = msgGrid.getSelectedId();		
	if(id!=null){		
		var pars = "mode=moveEmail&emailIds=" +id + "&folderId="+folderId;
		showWait(true);
		var myAjax = ajaxCall("inbox.do","get",pars,onEmailMoveTo,reportError);
	}else{
		alert('<bean:message key="inbox.error.email_not_selected"/>');
	}
}
function onEmailMoveTo(request) {	
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;	
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="inbox.error.can_not_move_email" />');
		return;
	} else {		
		temp = getIds(xmlFile);
		count = temp[0];
		destinationFolderId = temp[1];
		destinationFolderCount = $("MSG_COUNT_" + destinationFolderId).innerHTML;
		destinationFolderCount = destinationFolderCount.substring(1, destinationFolderCount.length-1)
		destinationFolderCount = parseInt(destinationFolderCount) + parseInt(count)
		$("MSG_COUNT_" + destinationFolderId).innerHTML = '(' + destinationFolderCount + ')';
		
		selectedFolderCount = $("MSG_COUNT_" + selectedFolder).innerHTML;
		selectedFolderCount = selectedFolderCount.substring(1, selectedFolderCount.length-1)
		selectedFolderCount = parseInt(selectedFolderCount) - parseInt(count)
		$("MSG_COUNT_" + selectedFolder).innerHTML = '(' + selectedFolderCount + ')';
	}
	emailIds = msgGrid.getSelectedId();
	if(emailIds != null) {
		temp = emailIds.split(',');
		for(var i = 0; i < temp.length; i++) {
			msgGrid.deleteRow(temp[i]);
		}
	}
	showWait(false);
}
</script>

<html:form action="/inbox">
<html:hidden property="t" name="inboxForm"/>
<html:hidden property="mode"/>
<html:hidden property="filePath" name="inboxForm"/>
<html:hidden property="sessionId" name="inboxForm"/>

<div class="contentDiv" style="margin-top: 15px;">
<table cellpadding="0" cellspacing="0" border="0">
	 <tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			  <tr> 
			  	<td>
				<div id="emailReceivedBtnBar" class="navBtnTab" 
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_EMAIL">
				style="width:210px;float: left;"
				</logic:equal>
				<logic:notEqual value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_EMAIL">
				style="width:150px;float: left;"
				</logic:notEqual>
				>
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
					<a href="#" style="width:50px;" onmouseover="javascript: showMenuMore('1','more'); return false;" id="more"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="inbox.label.more"/></a>
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" id="more_separator" align="left"/>
					<a href="#" style="width:70px;" onmouseover="javascript: showMenuMoveTo('1','moveto'); return false;" id="moveto"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="inbox.label.move_to"/></a>
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" id="moveto_separator" align="left"/>
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_EMAIL">
					<a href="#" style="width:55px;" onclick="DeleteEmail();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.delete"/></a> 				    
				    </logic:equal>				    
				</div>

				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_IMPORT_FROM_EMAIL">
				<div id="previewPaneBtnBar" class="navBtnTab" style="width:280px;float: right;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
				<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
			      <a href="#" style="width:60px;" onclick="ImportEmail();"><span class="rightC"></span><span class="leftC"></span><bean:message key="inbox.button.label.import"/></a> 
			      <img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
			      <a href="#" style="width:195px;" onclick="showForward();"><span class="rightC"></span><span class="leftC"></span><bean:message key="inbox.button.label.attach"/></a>
		        </div>
		        </logic:equal>	
						    
			    </td> 
			  </tr> 
			</table> 			
		</td>
	</tr> 
	<tr>
		<td valign="top">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="gridborder">
					<div id="gridbox" style="width:740px;height:185px;"></div>
				</td>
			</tr>
		</table>		
		</td>
	</tr>
	<tr>
		<td style="height:10px;"></td>
	</tr>
	<tr>
		<td valign="top">

		<div class="bottomDivSec">
		<div style="width:740px;" class="vpTop">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
	          <tr>
	            <td><strong id="VP_TITLE" class="Grey">&nbsp;</strong></td>
	          </tr>
	        </table>
		</div>
		</div>
		<div style="width:740px; height: 65px;" class="vpHeader" id="VP_HEADER_TABLE" >
			<table border="0" cellspacing="0" cellpadding="0" class="vpTopTab" style="margin-top:5px;">
	          <tr>
	            <td  id="VP_FROM"></td>
	          </tr>
	        </table>
			<table border="0" cellspacing="0" cellpadding="0" class="vpHeaderTab" >
	          <tr>
	            <td id="VP_HEADER" >
	            </td>
	          </tr>
	        </table>
		</div>
		<div style="width:740px;display: none; border-top: 0px;" class="erroDiv" id="errorDiv">
			<table border="0" cellspacing="0" cellpadding="0" style="margin:10px;border:0px;" id="m_errortable"> 
	          <tr>
	            <td class="message"><bean:message key="inbox.error.auto_import_error"/>
	            </td>
	          </tr>
	          <tr>
	            <td id="ERROR_AUTO" style="color:#000000;">
	            </td>
	          </tr>
	        </table>
		</div>
		<div class="bottomDiv"> 
		<div style="border-top:0px;border-bottom: 0px;" class="outerDiv">
		<iframe src="common/blank.html" style="height:200px;width:740px;" frameborder="0"  id="viewPort" name="viewPort"></iframe>
		</div>
		</div>
		</td>
	</tr>
</table>
</div>
</html:form>
<div id="updater" style="display: none; position: absolute; top: 55px; left: 820px;width:120px;">
	<table>
	<tr>
		<td><img src="images/wait.gif"/></td>
		<td>Please wait....</td>
	</tr>
	</table>
</div>


<script language="javascript">
var bccEnabled=<%=GlobalApplicationProperties.isEnabled(GlobalConstants.PROPERTY_ENABLE_BCC_WHILE_SENDING_EMAIL)%>;
function uploadDocument() {  
	var url="importResume.do?mode=addDocument&option="+<%=InboxConstants.OPTION_RESUME_UPLOAD%>;
	showInPopUp(url,500,160,null);
}
function showWait(state){
	showDiv('updater',state);
}

/*
function fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize, filePath){
	if(error==''){
		var url = "importResume.do?mode=importResume&parse=1&subMode=add&uploadedFilePath="+filePath;
		showImportScreen(url);
	}else{
		alert(error);
	}
	showWait(false);
}*/

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
	var img = "ico_attachmt.gif";
	if(val>0){
	 this.cell.innerHTML = "<img src='"+this.grid.imgURL+""+img+"'>";
	}else{
	 this.cell.innerHTML = "&nbsp;";
	}
}
function eXcell_app(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 	return this.cell.innerHTML;
 }
}

eXcell_app.prototype = new eXcell;
eXcell_app.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	var img = "ico_existing_candidate.gif";
	if(val>0){
	 this.cell.innerHTML = "<a href='#' onclick='viewApplicant("+val+");'><img src='"+this.grid.imgURL+""+img+"' border='0' ></a>";
	}else{
	 this.cell.innerHTML = "&nbsp;";
	}
}

function viewApplicant(aId){
	var app = window.open("selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	app.focus();
}



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
	//val=0 readed
	//val=1 notReaded
	if(val=="0"){
	 this.cell.parentNode.className='notReadEmail';
	}else if(val=="1"){
	 this.cell.parentNode.className='ReadEmail';
	}
}
/* extended functions ends here */

var msgGrid, msgGrid2;


var pageSize=15;
function doOnLoad() {
	showWait(true);
	initPopUp();
	loadMsgGrid();
	mnuhandlermoveto.hideMenuItem('<%=InboxConstants.INBOX_FOLDER_INBOX%>', true);	
	$("link"+selectedFolder).style.display="none";		
	$("nlink"+selectedFolder).style.display="inline";	
	loadInboxXML('<%=InboxConstants.INBOX_FOLDER_INBOX%>');		
}

function loadMsgGrid(){	
	msgGrid = new dhtmlXGridObject('gridbox');
	msgGrid.imgURL = "images/"; 
	msgGrid.setHeader(",,<bean:message key="inbox.label.from"/>,<bean:message key="inbox.label.subject"/>,<bean:message key="inbox.label.received"/>,");
	msgGrid.setInitWidths("20,20,270,270,130,0")
	msgGrid.setColAlign("left,left,left,left,left,left")
	msgGrid.setColTypes("att,app,ro,ro,ro,estat");
	msgGrid.setColSorting("str,na,str,str,custom_date_sort,na")
	msgGrid.enableMultiselect(true);	
	msgGrid.attachEvent("onHeaderClick",onEmailReceivedHeaderClick);
	msgGrid.attachEvent("onXLE",onGridLoadEnd);
	msgGrid.attachEvent("onKeyPress",onEmailReceivedKeyPressed);
	msgGrid.attachEvent("onRowSelect",OnRowSelected);
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_IMPORT_FROM_EMAIL">
	msgGrid.attachEvent("onRowDblClicked",onRowDoubleClicked);
	</logic:equal>
	
	//msgGrid.setSkin("light");
	msgGrid.setAwaitedRowHeight(21);
	msgGrid.init();
	msgGrid.setHeaderCursor(",pointer,pointer");
	msgGrid.enableSmartRendering(true);
	
	msgGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
			
	msgGrid.setSortImgState(true,4,"des");		
}
var selectedFolder = '<%=InboxConstants.INBOX_FOLDER_INBOX%>';
var selectedFolderType = '<%=MastersConstants.SYSTEM_FOLDER%>';
function loadInboxXML(folderId){
	msgGrid.loadXML("inbox.do?mode=getMessages&folderId="+folderId+"&un="+Date.parse(new Date()));	
}
function onGridLoadEnd(grd, itemsCnt){	
	if(selectedFolder == '<%=InboxConstants.INBOX_FOLDER_SENT%>') {
		msgGrid.setHeaderCol(4,'<bean:message key="inbox.label.sent" />');
		msgGrid.setHeaderCol(2,'<bean:message key="inbox.label.to" />');
	}else if(selectedFolderType == '<%=MastersConstants.DRAFT%>'){
		msgGrid.setHeaderCol(4,'<bean:message key="inbox.label.created" />');
	} else {
		msgGrid.setHeaderCol(4,'<bean:message key="inbox.label.received" />');
	}
	setRowCount();
	showWait(false);	
}

function setRowCount(){
	var itemsCnt = msgGrid.getRowsNum();
	$("MSG_COUNT_" + selectedFolder).innerHTML='('+itemsCnt+')';		
}

function onEmailReceivedHeaderClick(idx){
	onHeaderClick('msgGrid',msgGrid,idx);
}

function onHeaderClick(gridName,gridObj,idx){
	var sortBy=<%=InboxConstants.SORT_BY_DATE %>;
	var currentState = gridObj.getSortingState();
	var sortDir='DESC';
	switch(idx){
		case 0:
			sortBy=<%=InboxConstants.SORT_BY_ATTACHMENT %>;
			break;
		case 1:
			return;	
		case 2:
			sortBy=<%=InboxConstants.SORT_BY_FROM %>;
			break;
		case 3:
			sortBy=<%=InboxConstants.SORT_BY_SUBJECT%>;
		case 4:
			sortBy=<%=InboxConstants.SORT_BY_DATE%>;
		}
		if(currentState[0]==idx){
			if(currentState[1]=='des'){
				sortDir='ASC';
			}else{
				sortDir='DESC';
			}
		}
		gridObj.clearAll();
		showWait(true);
		if(sortDir=='DESC'){
			gridObj.setSortImgState(true,idx,"des");
		}else{
			gridObj.setSortImgState(true,idx,"asc");
		}
		gridObj.loadXML("inbox.do?mode=getMessages&folderId=" + selectedFolder + "&sortBy=" + sortBy+"&sortDir="+sortDir);						
}

function onEmailReceivedKeyPressed(keyCode,ctrl,shift){
	onKeyPressed('msgGrid',msgGrid,keyCode,ctrl,shift);
}

function onKeyPressed(gridName,gridObj,keyCode,ctrl,shift){
	var id = gridObj.getSelectedId();
	if(!isNaN(id)){
		switch(keyCode){
		case 13:
			//enter key
			if (selectedFolder != '<%=InboxConstants.INBOX_FOLDER_SENT%>') {
				ImportEmail();
				reloadPreviewPane(gridName,gridObj);				
			}
			break;
		case 46:
			//delete key
			DeleteEmail();
			reloadPreviewPane(gridName,gridObj);				
			break;
		case 33:
			//page up
			var idx = gridObj.getRowIndex(id)-pageSize;
			idx = (idx<0)?0:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);
			reloadPreviewPane(gridName,gridObj);
			break;
		case 34:
			var idx = gridObj.getRowIndex(id)+pageSize;
			idx = (idx>=gridObj.getRowsNum())?gridObj.getRowsNum()-1:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);	
			reloadPreviewPane(gridName,gridObj);
			break;
			//page down
		case 38:
			// up arrow key
			var idx = gridObj.getRowIndex(id)-1;
			idx = (idx<0)?0:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);
			reloadPreviewPane(gridName,gridObj);
			break;
		case 40:
			// down arrow key
			var idx = gridObj.getRowIndex(id)+1;
			idx = (idx>=gridObj.getRowsNum())?gridObj.getRowsNum()-1:idx;
			gridObj.showRow(gridObj.getRowId(idx));
			gridObj.selectRow(idx);	
			reloadPreviewPane(gridName,gridObj);
			break;
		}		
	}
	return true;
}
function reloadPreviewPane(gridName,gridObj) {
	id = gridObj.getSelectedId();
	if (id != null) {
		OnRowSelected(id);
	}
}

function onRowDoubleClicked(id,idx_col){
	if(selectedFolderType == '<%=MastersConstants.DRAFT%>') {
		showEmailInPopUp();
	} else {
		ImportEmail();
	}	
}

function showEmailInPopUp() {
	var id = msgGrid.getSelectedId();
	if(id==null ||(isNaN(id) && id.indexOf(",")<0 )){
		alert('<bean:message key="inbox.error.email_not_selected_to_send"/>');
		return;
	}else{
		if(id.indexOf(",")!=-1){
			alert("<bean:message key="inbox.error.multiple_email_selected_to_send"/>");
			return;
		}
		var url = 'inbox.do?mode=newEmail&newEmailType=<%=InboxConstants.EMAIL_TYPE_SEND_DRAFT%>&emailId=' + id + '&emailLocation=' + '<%=InboxConstants.EMAIL_LOCATION_INBOX%>&folderId=<%=InboxConstants.INBOX_FOLDER_INBOX%>';
		window.setTimeout("showInPopUp('"+url+"',810, 513,update,true);", 10);	
	}	
}

function update() {
	decrementRowCount();
	reloadSentGrid();
}

function decrementRowCount() {
	var itemsCnt = msgGrid.getRowsNum() - 1;
	$("MSG_COUNT_" + selectedFolder).innerHTML='('+itemsCnt+')';		
}

function OnRowSelected(id){ 
	//hideAllPopUpScreens();
	if(!isNaN(id)){
		var emailId = id;
		var emailLocation = '<%=InboxConstants.EMAIL_LOCATION_INBOX%>';
		if(selectedFolder == '<%=InboxConstants.INBOX_FOLDER_SENT%>') {
			emailId = msgGrid.getUserData(id,"mId");
			emailLocation = msgGrid.getUserData(id,"eLoc");
		}
   		getEmailBody(emailId, emailLocation);
   	}
}

function getEmailBody(emailId, emailLocation){
	window.frames["viewPort"].location="inbox.do?mode=getEmailBody&emailId="+ emailId+"&emailLocation=" + emailLocation;
	var pars = "mode=getEmailHeader&emailId=" +emailId+"&emailLocation=" + emailLocation;
	var myAjax = ajaxCall("inbox.do","get",pars,setMessageHeader,reportError);
}


function setMessageHeader(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="inbox.error.unable_to_get_email_header"/>');
		return;
	}
	
	clearMessageHeader();
	
	var headerHeight=55;
	
	var email = xmlFile.getElementsByTagName("email")[0];	
	var emailLocation = email.getAttribute("emailLocation");	
	var emailId = email.getAttribute("id");
	var subject = getSingleElement(email,"subject","").escapeHTML();
    var from =  getSingleElement(email,"from","").escapeHTML();
    var to =  getSingleElement(email,"to","").escapeHTML();
    var cc = getSingleElement(email,"cc","").escapeHTML();
    var bcc = getSingleElement(email,"bcc","").escapeHTML();
    var received =  getSingleElement(email,"sendDate","").escapeHTML();
	var errorIds = getSingleElement(email,"errorIds","");
	
	showAutoImportErrors(errorIds);	
	
	$("VP_TITLE").innerHTML=(subject=="")?"&nbsp;":subject;
	
	$("VP_FROM").innerHTML="<strong class=\"Grey\">From: </strong>" + from + "<br/><strong class=\"Grey\">To: </strong>" + to;
	
	//var headerStr = "<strong class=\"Grey\">To: </strong>" + to + "</br>";
	var headerStr = "";
	if(cc!=""){
		headerStr += "<strong class=\"Grey\">Cc: </strong>";
		headerStr += cc + "</br>";
		headerHeight+=18;
	}
	if(bccEnabled && bcc!=""){
		headerStr += "<strong class=\"Grey\">Bcc: </strong>";
		headerStr += bcc + "</br>";
		headerHeight+=18;
	}
	headerStr += "<strong class=\"Grey\">Attachments: </strong>";
    var attachments = xmlFile.getElementsByTagName("attachments")[0];
    if(attachments!=null){
	    var items = attachments.getElementsByTagName("attachment");	
	    var attachmentList="";
	    for (var I = 0 ; I < items.length ; I++) {
		     var item = items[I];
		     var attachmentId = item.getAttribute("id");
		     var fileName=getSingleElement(item,"fName","");
		     var originalName=getSingleElement(item,"oName","");
		     attachmentList += "<a href=\"#\" onclick=\"javaScript:showDoc(\'"+ fileName + "\');\" class=\"green\">" + originalName+"<\/a>";
		     if(I<items.length-1){
		     	attachmentList += ", ";
		     }
		}
	
	headerStr += attachmentList + "</br>";
	}
    $("VP_HEADER_TABLE").setStyle({height: headerHeight+'px'});
	$("VP_HEADER").innerHTML=headerStr;
	msgGrid.setRowTextNormal(emailId);
}
function showDoc(fileName){
	var url = "<%=DocumentUtils.getDocumentURL("@fileName@",DocumentConstants.CONTENT_DISPOSITION_INLINE )%>";
	url = url.replace(/(@fileName@)/g,fileName);
	window.open(url,"_new");
}
function showAutoImportErrors(errorIds){
	var errMsg = "";
	if(errorIds!=""){
		var errs = errorIds.split(",");
		for(I=0; I<errs.length; I++){
			var erId = errs[I].trim();
			switch(erId) {
		       case "1":
		        errMsg = appendError(errMsg,"Applicant name is blank");
				break;
		       case "2":
		        errMsg = appendError(errMsg,"Email ID not present");
				break;
		       case "3":
		        errMsg = appendError(errMsg,"Current location is blank");
				break;
		       case "4":
		        errMsg = appendError(errMsg,"Duplicate resume found");
				break;
		       case "5":
		        errMsg = appendError(errMsg,"Source found, does not match with master");
				break;
		       case "6":
		        errMsg = appendError(errMsg,"Working since date is not valid");
				break;
		       case "7":
		        errMsg = appendError(errMsg,"Email ID is invalid");
				break;
		       case "8":
		        errMsg = appendError(errMsg,"No resume file is attached");
				break;
		       case "9":
		        errMsg = appendError(errMsg,"Unknown error occured while importing");
				break;
		       case "10":
		        errMsg = appendError(errMsg,"Applicant failed to pass mandatory conditions for this position.");
				break;
			}
		}
	}
	if(errMsg !=""){
		$("ERROR_AUTO").innerHTML= errMsg;
		$("errorDiv").style.display="block";
	}else{
		$("errorDiv").style.display="none";
	}
}
function appendError(errMsg, errStr){
	if(errMsg !="") {
		errMsg +="<br>";
	}
	errMsg += "&nbsp;-&nbsp;" + errStr;
	return errMsg;
}
/* code to delete email */
function DeleteEmail(){
	//hideAllPopUpScreens();
	var id = msgGrid.getSelectedId();	
	if(id!=null && ( !isNaN(id) || id.indexOf(",")>0) ){
		if(selectedFolder == '<%=InboxConstants.INBOX_FOLDER_SENT%>'){
			var selIds = id.split(",");
			var sIds ="";
			for(x=0;x<selIds.length;x++){
				var emailId = msgGrid.getUserData(selIds[x],"mId");
				var emailLocation = msgGrid.getUserData(selIds[x],"eLoc");
				if(emailLocation == '<%=InboxConstants.EMAIL_LOCATION_INBOX%>' ) {
					if(sIds !=""){sIds += ",";}
					sIds += emailId;
				}
			}
			id = sIds;
			if(id==''){
				alert('<bean:message key="inbox.error.can_not_delete_communication_email"/>');
				return;
			}
			
		}
		var pars = "mode=deleteEmail&emailId=" +id;
		showWait(true);
		var myAjax = ajaxCall("inbox.do","get",pars,deleteInboxRow,reportError);
	}else{
		alert('<bean:message key="inbox.error.email_not_selected_to_delete"/>');
	}
}
function deleteInboxRow(request){
	deleteRow(request,msgGrid);
}
function deleteRow(request, grdObj){
	showWait(false);
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="inbox.error.can_not_delete_email"/>');
		return;
	}
	var emailIds=getIds(xmlFile);
	deleteGridRows(grdObj,emailIds);
}

function deleteGridRows(grdObj,emailIds){
	var grdId = grdObj.entBox.id;
	clearPreviewPane();	
	for(var I=0; I<emailIds.length; I++){
		grdObj.deleteRow(emailIds[I]);
	}
	grdObj.clearSelection();
	//hide display screen
	setRowCount();
}
/* forward to candidate record */
function showForward(){
	var id = msgGrid.getSelectedId();
	if(isNaN(id) || !id){
		alert('<bean:message key="inbox.error.email_not_selected"/>');
		return;
	}else{
		if(id.indexOf(",")!=-1){			
			alert("<bean:message key="inbox.error.multiple_email_selected"/>");
			return;
		}
		var url='inbox.do?mode=showCandidateForward&emailId=' + id + '&emailLocation=<%=InboxConstants.EMAIL_LOCATION_INBOX%>';
		window.setTimeout("showInPopUp('"+url+"',550, 320, null,false);", 10);
	}
}

/* import email */
function ImportEmail(){
	var id = msgGrid.getSelectedId();
	if(id==null ||(isNaN(id) && id.indexOf(",")<0 )){
		alert('<bean:message key="inbox.error.email_not_selected_to_import"/>');
		return;
	}else{
		if(id.indexOf(",")!=-1){
			alert("<bean:message key="inbox.error.multiple_email_selected_to_import"/>");
			return;
		}
		var url = "importResume.do?mode=importResume&parse=1&subMode=add&emailId="+id;
		showImportScreen(url);
	}
	return true;
	
}
function showImportScreen(url){
	var win = window.open(url, '_new', 'width=1024,height=748,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	if(win){
		win.focus();
	}
}

/* mark as un read */
function markUnread(){
	var id = msgGrid.getSelectedId();
	if(id!=null && ( !isNaN(id) || id.indexOf(",")>0) ){
		var pars = "mode=changeReadStatus&emailId=" +id;
		var myAjax = ajaxCall("inbox.do","get",pars,onUnReadComplete,reportError);
	}else{
		alert('<bean:message key="inbox.error.email_not_selected_to_unread"/>');
	}
}

function onUnReadComplete(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="inbox.error.can_not_mark_unread"/>');
		return;
	}
	var emailIds=getIds(xmlFile);
	for(var I=0; I<emailIds.length; I++){
		msgGrid.setRowTextBold(emailIds[I]);
	}
}

function clearPreviewPane(){
	clearMessageHeader();
	window.frames["viewPort"].location="common/blank.html";
}

function clearMessageHeader(){
	$("VP_TITLE").innerHTML="&nbsp;";
	$("VP_HEADER").innerHTML="";
	$("VP_FROM").innerHTML="";
}

function onImportFinish(emailId){
	var ids = Array();
	if(emailId){
		ids[0]=emailId;
		deleteGridRows(msgGrid,ids);
	}
	return;
}
window.onload = doOnLoad;
var visibleTab=1;
var reloadingSent=0;
var reloadingInbox=0;
var prevSelected='';
function toggleFolderDisplay(folderId, folderType) {		
	$("link"+folderId).style.display="none";		
	$("nlink"+folderId).style.display="inline";		
	$("link"+selectedFolder).style.display="inline";		
	$("nlink"+selectedFolder).style.display="none";	
	if (folderId != '<%=InboxConstants.INBOX_FOLDER_SENT%>' && folderType != '<%=MastersConstants.DRAFT%>') {	
		mnuhandlermoveto.hideMenuItem(folderId, true);
	}
	if (selectedFolder != '<%=InboxConstants.INBOX_FOLDER_SENT%>' && selectedFolderType != '<%=MastersConstants.DRAFT%>') {	
		mnuhandlermoveto.hideMenuItem(selectedFolder, false);
	}
	selectedFolder = folderId;	
	selectedFolderType = folderType;
	clearPreviewPane();
	clearMessageHeader();
	showDiv('errorDiv', 0);
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_EMAIL">
		btnBarWidth=210;
	</logic:equal>
	<logic:notEqual value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_EMAIL">
		btnBarWidth=150
	</logic:notEqual>
	
	if (folderId != '<%=InboxConstants.INBOX_FOLDER_SENT%>' && folderType != '<%=MastersConstants.DRAFT%>') {	
			$("emailReceivedBtnBar").style.width=btnBarWidth+"px";
			$("previewPaneBtnBar").style.display="block";			
			$("moveto").style.display="block";		
			$("moveto_separator").style.display="block";			
			$("more").style.display="block";		
			$("more_separator").style.display="block";	
			mnuhandlermore.hideMenuItem(mnu_unread.getId(), false);
			mnuhandlermore.hideMenuItem(mnu_re.getId(), false);					
	} else {
			$("emailReceivedBtnBar").style.width=(btnBarWidth-62)+"px";
			$("previewPaneBtnBar").style.display="none";
			$("moveto").style.display="none";		
			$("moveto_separator").style.display="none";	
			mnuhandlermore.hideMenuItem(mnu_re.getId(), true);
			mnuhandlermore.hideMenuItem(mnu_unread.getId(), true);
			if(folderType == '<%=MastersConstants.DRAFT%>') {
				$("more").style.display="none";		
				$("more_separator").style.display="none";
			} else {
				$("more").style.display="block";		
				$("more_separator").style.display="block";
			}
	}
	
	showWait(true);
	msgGrid.clearSelection();
	msgGrid.clearAll();
	loadInboxXML(folderId);
}

function showDiv(divId,show){
	if(show==0){
		$(divId).style.display="none";
	}else{
		$(divId).style.display="block";
	}
}

function newEmail(emailType){
	var emailId=''; 
	var emailLocation='<%=InboxConstants.EMAIL_LOCATION_INBOX%>';
	var applicantId='';
	
	var grdObj = msgGrid;	
	if(emailType=='<%=InboxConstants.EMAIL_TYPE_FORWARD%>' || emailType=='<%=InboxConstants.EMAIL_TYPE_REPLY%>'){
		if(selectedFolder != '<%=InboxConstants.INBOX_FOLDER_SENT%>'){
			emailId = grdObj.getSelectedId();
		}else{
			emailId = grdObj.getUserData(grdObj.getSelectedId(),"mId");
			emailLocation = grdObj.getUserData(grdObj.getSelectedId(),"eLoc");
			if(emailLocation=='<%=InboxConstants.EMAIL_LOCATION_COMMUNICATIONS%>'){
				applicantId = grdObj.getUserData(grdObj.getSelectedId(),"applicantId");
			}
		}
		
		if(isNaN(emailId) || !emailId){
			alert('<bean:message key="inbox.error.email_not_selected"/>');
			return;
		}else{
			if(emailId.indexOf(",")!=-1){
				alert("<bean:message key="inbox.error.multiple_email_selected"/>");
				return;
			}
		}
	}
	var url = 'inbox.do?mode=newEmail&newEmailType=' + emailType + '&emailId=' + emailId + '&emailLocation=' + emailLocation + '&applicantId=' + applicantId + '&folderId=<%=InboxConstants.INBOX_FOLDER_INBOX%>';
	window.setTimeout("showInPopUp('"+url+"',810, 513,reloadSentGrid,true);", 10);
}

function reloadSentGrid(returnVal){
	toggleFolderDisplay('<%=InboxConstants.INBOX_FOLDER_SENT%>', '<%=MastersConstants.SYSTEM_FOLDER%>');
}
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function doNothing(){
}
function sendMail() {
	alert("inside send");
}

function excelImport(){	
	var url="importResume.do?mode=addDocument&option=1";
	showInPopUp(url,500,140,null);
}

function fileUploaded(error,attachmentId, originalFileName, attachmentSize, labeledAttachmentSize, filePath, option){	
	if(error==''){
		if(option=='0'){
			var url = "importResume.do?mode=importResume&parse=1&subMode=add&uploadedFilePath="+filePath;
			showImportScreen(url);
		}else{				
			startExcelImport(filePath);
		}
	}else{
		alert(error);
	}
	showWait(false);
}
function startExcelImport(filePath){	
	document.inboxForm.filePath.value=filePath;
	document.inboxForm.mode.value = "showResumeCSVFieldMappings";
	document.inboxForm.submit();
}
</script>