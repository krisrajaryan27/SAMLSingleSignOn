<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script src="js/dhtmlwindow/dhtmlwindow.js"></script>
<script src="js/dhtmlwindow/modal.js"></script>

<html:form action="/inbox" onsubmit="submitForm();return false;">
  	<html:hidden property="mode" name="inboxForm"/>
  	<html:hidden property="sessionId" name="inboxForm"/>
	<html:hidden property="mappings" name="inboxForm"/>
	<html:hidden property="filePath" name="inboxForm"/>
	<html:hidden property="isSessionComplete" name="inboxForm"/>
<div class="contentDiv">
	<table  cellspacing="0" width="100%" style="margin-bottom: 3px;">
		<tr>
			<td>
				<strong id="msg">Import process successfully started. Please wait for the process to finish.</strong>
			</td>			
		</tr>
	</table>
	<br/>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="dataGrid" style="width:950px;height: 25px;"></div>
			</td>
		</tr>
	</table>
	<br/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" > 
		<tr> 
			<td>						
	   			<div id="divImportAllButton" class="navBtn" style="display:none;margin-top:5px;float: right;">
					<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="onClickCancel('inbox.do?mode=inbox');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.finish"/> </a>
				</div>
			</td>
		</tr>
	</table>
</div>
</html:form>
<script language="javascript">
var dataGrid=null;
var maxHeight=380;

function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader(',<bean:message key="common.name"/>,Row Number,Status'); 
	   	dataGrid.setInitWidths("10,400,200,310");
	   	dataGrid.setColAlign("left,left,left,left");
	   	dataGrid.setColTypes("ro,ro,ro,ro"); 
	   	dataGrid.setColSorting("na,str,na,na");
		dataGrid.enableAutoHeigth(true,maxHeight);
		//dataGrid.setOnLoadingEnd(reloadInEveryFiveSecond);
		dataGrid.attachEvent("onXLE",reloadInEveryFiveSecond);
	   	dataGrid.init(); 
	   	loadGrid();
}

function loadGrid(){
	var rowIds = dataGrid.getAllItemIds();
	var rIds = rowIds.split(",")
	var lastRowId = rIds[rIds.length - 1];	
	currentRowCount = dataGrid.getRowsNum();

	dataGrid.loadXML('inbox.do?mode=viewCSVImportedProcessData&sessionId=<bean:write property="sessionId" name="inboxForm" />&lastRowId='+lastRowId);
	var newRowCount = dataGrid.getRowsNum();
	checkForSessionComplete(currentRowCount, newRowCount);
}

function reload(){
	loadGrid();
}

function checkForSessionComplete(oldCount,newCount){
	if(oldCount==newCount){
		var pars = 'mode=checkForSessionCompleteDuringImport&sessionId=<bean:write name="inboxForm" property="sessionId"/>';
		var myAjax = ajaxCall("inbox.do","get",pars,onCheckForSessionComplete,reportError);
	}
}

function onCheckForSessionComplete(request){
	//if yes the set isSessionComplete = 1
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('Fail to check for session complete');
		return;
	}
	//get returned deleted ids and delete them from grid
	var sessionComplete = getIds(xmlFile);	
	if(sessionComplete=='1'){
		$("divImportAllButton").style.display="block";
		$("msg").innerHTML="Import completed successfully.";
		document.inboxForm.isSessionComplete.value = sessionComplete;
	}
}

function reloadInEveryFiveSecond(){
	var sessionComplete = document.inboxForm.isSessionComplete.value;
	if(sessionComplete!=1){
		window.setTimeout("reload()", 5000);
	}
}

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function cancelImport(url){
	window.location.href=url;
}

function onWindowLoad(){
	initGrid();
}
window.onload=onWindowLoad;
function onClickCancel(url){
	window.location.href=url;
}
</script>

