<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>

<% 
	HashMap headerMap = (HashMap)request.getAttribute("headerMap");
	String mappings = (String)request.getAttribute("mappings");
	String[] mappingStr = mappings.split(",");
%>



<html:form action="/inbox" onsubmit="submitForm();return false;">
  	<html:hidden property="mode" name="inboxForm"/>
  	<html:hidden property="filePath" name="inboxForm"/>
  	<html:hidden property="sessionId" name="inboxForm"/>
	<html:hidden property="mappings" name="inboxForm"/>
	<html:hidden property="isSessionComplete" name="inboxForm"/>
<div class="contentDiv">
	<table cellpadding="0" cellspacing="0" style="margin-bottom: 3px;">
		<tr>
			<td>
				<strong>Following data found in import file. Please check "Errors" before importing.</strong>
			</td>
		</tr>
	</table>
	<br/>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="dataGrid" style="width: 950px; height: 400px;"></div>
			</td>
		</tr>
	</table>
	<br/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" > 
		<tr> 
			<td>						
	   			<div id="divImportAllButton" class="navBtn" style="display:none;margin-top:5px;float: right;">
					<a href="#" style="width:90px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span>Import All</a>
					<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="onClickCancel('inbox.do?mode=inbox');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/> </a>
				</div>
					
				<div id="divWaitForParsing" style="display:block;text-align: right;">
					<table cellspacing="0" cellpadding="0"> 
						<tr> 
							<td>						
								<img src="images/wait.gif" style="margin-bottom: -3px;"/>
							</td>
							<td>						
								<div id="divParsedNumber" style="display:block;margin-bottom: -3px;" /></div>
							</td>
							<td>
								<div class="navBtn">
									<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="cancelImport();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/> </a>
								</div>
							</td>
						</tr>
					</table> 
				</div>
				
				<div id="divWaitForImporting" style="display:none;margin-top:5px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="right"> 
						<tr> 
							<td style="text-align: right;" >						
								<img src="images/wait.gif" style="margin-bottom: -3px;"/> Please wait, Excel import is in process &nbsp;
							</td>	
						</tr>
					</table> 
				</div>
	
			</td>			  
		</tr>
	</table> 
</div>
	
</html:form>


<script language="javascript">
var dataGrid=null;
var rowCount = 0;
var headers = "&nbsp;";
function initGrid(){
	   	var widths = "10";
	   	var colAlign = "left";
	   	var colTypes = "ro";
	   	<%for(int j=0 ; j< mappingStr.length; j++){ 
				if(!mappingStr[j].equals("0")){
		%>	
			headers += "," +"<%=headerMap.get(mappingStr[j])%>";
			widths += ",200";
			colAlign += ",left";
			colTypes += ",ro";		
			
		<%}} %>
		headers += "," +"Errors";
		widths += ",300";
		colAlign += ",left";
		colTypes += ",ro";
		
		dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/";
	   	dataGrid.setHeader(headers); 
	   	dataGrid.setInitWidths(widths);
	   	dataGrid.setColAlign(colAlign);
	   	dataGrid.setColTypes(colTypes); 
	   	dataGrid.enableMultiline(true);
		//dataGrid.setOnLoadingEnd(reloadInEveryFiveSecond);
		dataGrid.attachEvent("onXLE",reloadInEveryFiveSecond);
	   	dataGrid.init(); 
	   	loadGrid();
}
			
function loadGrid(){
	var rowIds = dataGrid.getAllItemIds();
	var rIds = rowIds.split(",");
	var lastRowId = rIds[rIds.length - 1];	
	currentRowCount = dataGrid.getRowsNum();

	dataGrid.loadXML('inbox.do?mode=viewCSVImportData&sessionId=<bean:write name="inboxForm" property="sessionId"/>&mappings=<bean:write name="inboxForm" property="mappings"/>&lastRowId='+lastRowId);
	var newRowCount = dataGrid.getRowsNum();
	checkForSessionComplete(currentRowCount, newRowCount);
}

function reload(){	
	loadGrid();
}

function refreshGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML('inbox.do?mode=viewCSVImportData&sessionId=<bean:write name="inboxForm" property="sessionId"/>&mappings=<bean:write name="inboxForm" property="mappings"/>&lastRowId=');
}

function checkForSessionComplete(oldCount,newCount){
	if(oldCount==newCount){
		var pars = 'mode=checkForSessionComplete&sessionId=<bean:write name="inboxForm" property="sessionId"/>';
		var myAjax = ajaxCall("inbox.do","get",pars,onCheckForSessionComplete,reportError);
	}
}

function checkHavingError(){
	var rowIds = dataGrid.getAllItemIds();
	var rIds = rowIds.split(",");
	var headerArr = headers.split(",");
	for(var i=0;i<=rIds.length;i++){
		if(rIds[i]!=null && rIds[i]!=undefined){
			if(dataGrid.cells(rIds[i],headerArr.length-1).getValue()!=''){
				return true;
			}
		}
	}
	return false;
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
		$("divWaitForParsing").style.display="none";
		$("divParsedNumber").style.display="none";	
		document.inboxForm.isSessionComplete.value = sessionComplete;
	}
}

function submitForm(){

	var frm=document.inboxForm;	
	frm.mode.value="startExcelImport";
	if(checkHavingError()){
		alert('<bean:message key="common.error.import.rectify_errors" />');
		return false;
	}
	$("divImportAllButton").style.display="none";
	$("divWaitForImporting").style.display="block";
	frm.submit();
}

function onClickCancel(url){
	window.location.href=url;
}

function cancelImport(){
	var pars = 'mode=cancelImportProcess&sessionId=<bean:write name="inboxForm" property="sessionId"/>';
		var myAjax = ajaxCall("inbox.do","get",pars,onCancelImportProcess,reportError);
}

function onCancelImportProcess(request){
	//if yes the set isSessionComplete = 1
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('Fail to cancel import process');
		return;
	}

	var sessionId = getIds(xmlFile);	
}

function onWindowLoad(){
	initGrid();
}

function reloadInEveryFiveSecond(){
	var newRowCount = dataGrid.getRowsNum();
	$("divParsedNumber").innerHTML = ' Please wait, '+ newRowCount +' Records fetched form file.';
	var sessionComplete = document.inboxForm.isSessionComplete.value;
	if(sessionComplete!=1){
		window.setTimeout("reload()", 5000);
	}
}

window.onload=onWindowLoad;
</script>