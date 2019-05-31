<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>

<%@page import="com.talentPool.desktop.constants.DesktopConstants"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>

<div class="contentDiv" style="margin-right: 0px; margin-top: 10px;">
	<logic:equal name="desktopSearchForm" property="duplicateImport" value="1">
		<table id="m_errortable"> 
			<tr>
		    <td class='header'>
	        	<b>Following resumes have not been imported due to:</b><br/>
					* <bean:message key="common.duplicate"/> <bean:message key="common.candidate"/> or<br/>
					* Insufficient information<br/>
		    </td>               
			</tr>
		</table>
		<br/>
	</logic:equal>



<html:form action="/desktop">
<html:hidden property="mode" name="desktopSearchForm" value="importAll"/>
<html:hidden property="sessionId" name="desktopSearchForm"/>
<html:hidden property="isSessionComplete" name="desktopSearchForm"/>
<html:hidden property="sessionType" name="desktopSearchForm"/>
<html:hidden property="parsedSkillIds" name="desktopSearchForm"/>
<html:hidden property="resultId" name="desktopSearchForm"/>
<html:hidden property="emailId" name="desktopSearchForm"/>
	
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="dataGrid" style="width: 910px; height: 210px;"></div>
			</td>
		</tr>
	</table>
	<br/>
	
	<table border="0" cellspacing="0" cellpadding="0"  style="width: 910px; "> 
		<tr>
			<td style="padding-right: 10px; vertical-align: top;width: 280px;;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="boxHeader">
				          <tr>
				          	<td style="padding-left: 12px;height: 18px;"><bean:message key="common.candidate"/> Information</td>
				          </tr>
				</table>
				<div class="outerDiv" style="overflow: auto;height: 262px;border:1px solid #99CC33;padding-left: 10px;">

				<table cellpadding="0" cellspacing="0" border="0">
					<tr style="padding-top: 20px;">
						<td class="label" style="width: 58px"><bean:message key="common.name"/>:</td>
						<td class="label"><html:text styleId="parsedName" property="parsedName" name="desktopSearchForm" size="30"	 /> 
						</td>
					</tr>
					<tr style="padding-top: 10px;">
						<td class="label"><bean:message key="common.phone1"/>:</td>
						<td class="label"><html:text styleId="parsedPhone1" property="parsedPhone1" name="desktopSearchForm" size="30"	 /> 
						</td>
					</tr>
					<tr style="padding-top: 10px;">
						<td class="label"><bean:message key="common.phone2"/>:</td>
						<td class="label"><html:text styleId="parsedPhone2" property="parsedPhone2" name="desktopSearchForm" size="30" /> 
						</td>
					</tr>
					<tr style="padding-top: 10px;">
						<td class="label"><bean:message key="common.email"/>:</td>
						<td class="label"><html:text styleId="parsedEmail" property="parsedEmail" name="desktopSearchForm" size="30" /> 
						</td>
					</tr>
					<tr style="padding-top: 10px;">
						<td class="label" valign="top"><bean:message key="common.skills"/>:<br/>
							<a href="#" style="width:60px;margin-right: 5px;text-decoration: none;"  onclick="javascript: onAddSkills();" class="green">(<bean:message key="common.add"/>)</a>
						</td>
						<td class="label">
						<div id="parsedSkills" style="width: 163px; height: 50px; overflow: auto; background-color: #F9F9F9; border: 1px solid #ccc;">
						</div>
						</td>
					</tr>
					<tr style="padding-top: 5px;">
						<td></td>
						<td>
							<a href="#" style="width:70px;margin-right: 5px;text-decoration: none;"  onclick="javascript: onClickSingleImport();" class="green"><span class="greenBullet">&raquo;</span>&nbsp;More Fields</a>
						</td>
					</tr>
				</table>
								
				<table border="0" cellspacing="0" cellpadding="0" width="100%"> 
					<tr> 
						<td>						
				   			<div class="navBtn" style="float: right;margin-top: 5px;">					
							<logic:equal name="desktopSearchForm" property="duplicateImport" value="1">
								<a href="#" style="width:120px;margin-right: 5px;margin-right:5px;" onclick="javascript: checkDuplicate();" class="active"><span class="rightC"></span><span class="leftC"></span>Check <bean:message key="common.duplicate"/></a> 
							</logic:equal>	
								<a href="#" style="width:63px;margin-right: 5px;" onclick="javascript: onClickChange();" class="active"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.update"/></a> 
								<a href="#" style="width:60px;margin-right: 5px;" onclick="javascript: deleteApplicant();" class="active"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.delete"/></a> 
							</div>
						</td>		  
					</tr>
				</table>				
				</div>
			</td>
			<td style="vertical-align: top;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="boxHeader">
			          <tr>
			          	<td style="padding-left: 12px;height: 18px;"><bean:message key="common.original"/> <bean:message key="common.resume"/></td>
			          </tr>
				</table>
				<div class="outerDiv" style="width:618px;">
				<iframe name="printFrame" id="printFrame" src="" style="width:615px;height:262px;z-index: 1;" marginheight="0" marginwidth="0" frameborder="0"></iframe>
				</div>
			</td>
		</tr>
	</table>
	<br/>
	<div id="divImportAllButton" style="display:none;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		<tr> 
			<td>						
	   			<div class="navBtn" style="float: right;">					
					<logic:equal name="desktopSearchForm" property="duplicateImport" value="1">
					<a href="#" style="width:140px;margin-right: 5px;" onclick="javascript: finishBulkImport();" class="active"><span class="rightC"></span><span class="leftC"></span>Ignore & Continue</a> 
					</logic:equal>				
					<a href="#" style="width:80px;" onclick="importAll();" class="active" id="importAll"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.import"/> <bean:message key="common.all"/></a> 	  
				</div>
			</td>
		</tr>
	</table> 
	</div>
	<div id="divWaitForParsing" style="display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" align="right"> 
		<tr> 
			<td style="text-align: right;" >						
				<img src="images/wait.gif" style="margin-bottom: -3px;"/> Please wait, <bean:message key="common.resume"/> parsing is in process &nbsp;
			</td>	
			<td><div id="divParsedNumber" style="display:block;margin-bottom: -3px;" />
			</td>
		</tr>
	</table> 
	</div>
</html:form>
</div>

<script language="javascript">
var dataGrid=null;
var sessionType='<bean:write name="desktopSearchForm" property="sessionType"/>';

function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/"; 
	   	if(sessionType=='<%=DesktopConstants.SESSION_TYPE_EMAIL_IMPORT%>' || sessionType=='<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>'){
		   	dataGrid.setHeader("<bean:message key="common.email"/> <bean:message key="common.from"/>,<bean:message key="common.email"/> <bean:message key="common.subject"/>,<bean:message key="common.parsed"/> <bean:message key="common.name"/>,<bean:message key="common.parsed"/> <bean:message key="common.email"/>,&nbsp;"); 
		   	dataGrid.setInitWidths("220,270,190,180,20");
		   	dataGrid.setColAlign("left,left,left,left,left");
		   	dataGrid.setColTypes("ro,ro,ro,ro,ro"); 
		   	dataGrid.setColSorting("str,str,str,str,na");
	   	}else if(sessionType=='<%=DesktopConstants.SESSION_TYPE_BROWSER_IMPORT%>'){
		   	dataGrid.setHeader("<bean:message key="common.parsed"/> <bean:message key="common.name"/>,<bean:message key="common.parsed"/> <bean:message key="common.email"/>,&nbsp;"); 
		   	dataGrid.setInitWidths("450,420,20");
		   	dataGrid.setColAlign("left,left,left");
		   	dataGrid.setColTypes("ro,ro,ro"); 
		   	dataGrid.setColSorting("str,str,na");
	   	}else{
		   	dataGrid.setHeader("<bean:message key="common.file"/> <bean:message key="common.name"/>,<bean:message key="common.parsed"/> <bean:message key="common.name"/>,<bean:message key="common.parsed"/> <bean:message key="common.email"/>,&nbsp;"); 
		   	dataGrid.setInitWidths("400,250,220,20");
		   	dataGrid.setColAlign("left,left,left,left");
		   	dataGrid.setColTypes("ro,ro,ro,ro"); 
		   	dataGrid.setColSorting("str,str,str,na");
	   	}
		dataGrid.attachEvent("onRowSelect",onRowSelect);
		dataGrid.attachEvent("onXLE",reloadInEveryFiveSecond);
	   	dataGrid.init(); 
	   	loadGrid();
}

function loadGrid(){
	var rowIds = dataGrid.getAllItemIds();
	var rIds = rowIds.split(",")
	var lastRowId = rIds[rIds.length - 1];	
	var currentRowCount = dataGrid.getRowsNum();

	//dataGrid.loadXML('desktop.do?mode=viewBulkImportProgress&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>&sessionType=<bean:write name="desktopSearchForm" property="sessionType"/>&lastRowId='+lastRowId);
	dataGrid.loadXML('desktop.do?mode=viewBulkImportProgress&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>&sessionType=<bean:write name="desktopSearchForm" property="sessionType"/>&lastRowId=');
	
	if(document.desktopSearchForm.sessionType.value == '<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>') {
		$("divImportAllButton").style.display="block";
		$("divWaitForParsing").style.display="none";
		document.desktopSearchForm.isSessionComplete.value = "1";
	} else {
		var newRowCount = dataGrid.getRowsNum();	
		checkForSessionComplete(currentRowCount,newRowCount);
		getNumberOfParsedResume();
	}
}

function getNumberOfParsedResume(){
		var pars = 'mode=getNumberOfParsedResume&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>&sessionType=<bean:write name="desktopSearchForm" property="sessionType"/>';
		var myAjax = ajaxCall("desktop.do","get",pars,onGetNumberOfParsedResume,reportError);
}

function onGetNumberOfParsedResume(request){
	//if yes the set isSessionComplete = 1
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('Fail to get the number of <bean:message key="common.resume"/> <bean:message key="common.parsed"/>');
		return;
	}
	//get returned deleted ids and delete them from grid
	var number = getIds(xmlFile);	
	$('divParsedNumber').innerHTML=number;
}

function reload(){	// only used updateFromXML function
	var rowIds = dataGrid.getAllItemIds();
	var rIds = rowIds.split(",")
	var lastRowId = rIds[rIds.length - 1];	
	var currentRowCount = dataGrid.getRowsNum();

	dataGrid.updateFromXML('desktop.do?mode=viewBulkImportProgress&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>&sessionType=<bean:write name="desktopSearchForm" property="sessionType"/>&lastRowId='+lastRowId,true);
	//dataGrid.loadXML('desktop.do?mode=viewBulkImportProgress&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>&sessionType=<bean:write name="desktopSearchForm" property="sessionType"/>&lastRowId=');
	
	if(document.desktopSearchForm.sessionType.value == '<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>') {
		$("divImportAllButton").style.display="block";
		$("divWaitForParsing").style.display="none";
		document.desktopSearchForm.isSessionComplete.value = "1";
	} else {
		var newRowCount = dataGrid.getRowsNum();	
		checkForSessionComplete(currentRowCount,newRowCount);
		getNumberOfParsedResume();
	}
}

function refreshGrid(){
	dataGrid.clearAll();
	clearUpdateForm();
	dataGrid.loadXML('desktop.do?mode=viewBulkImportProgress&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>&sessionType=<bean:write name="desktopSearchForm" property="sessionType"/>&lastRowId=');
}
function checkForSessionComplete(oldCount,newCount){
	if(oldCount==newCount){
		//check for session complete
		var pars = 'mode=checkForSessionComplete&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>';
		var myAjax = ajaxCall("desktop.do","get",pars,onCheckForSessionComplete,reportError);
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
	if(sessionComplete==1){
		$("divImportAllButton").style.display="block";
		$("divWaitForParsing").style.display="none";
		document.desktopSearchForm.isSessionComplete.value = sessionComplete;
	}
}

function onRowSelect(){
	fetchParsedInfo(dataGrid.getSelectedId());
}

function fetchParsedInfo(rowId){
	if(rowId==null){
		/*alert('Please select a row');*/
		clearUpdateForm();
		return;
	}
	var pars = "mode=fetchParsedInfo&resultId=" + rowId;
	var myAjax = ajaxCall("desktop.do","get",pars,setApplicantInfo,reportError);
}
var docPath='';
function setApplicantInfo(request){
	xmlFile = request.responseXML;
	/*
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	*/
	if(isErrorXml(xmlFile)){
		alert('unable to fetch the parsed information');
		return;
	}

	var tree = xmlFile.getElementsByTagName("content")[0];
	var nodes=tree.childNodes.length;
	var moz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
	var ie = (typeof window.ActiveXObject != 'undefined');
	var name, phone1, phone2, email1, email2, skills, resumePath, resultId, skillIds;
	if(ie) {
		name =  tree.childNodes(0).text;
		phone1 = tree.childNodes(1).text;
		phone2 = tree.childNodes(2).text;
		email1 = tree.childNodes(3).text;
		email2 = tree.childNodes(4).text;
		skills = tree.childNodes(5).text;
		resumePath = tree.childNodes(6).text;
		resultId = tree.childNodes(7).text;
		skillIds = tree.childNodes(8).text;
		docPath = tree.childNodes(9).text;
	} else {			
		name =  (tree.childNodes[0].childNodes[0]) ? tree.childNodes[0].childNodes[0].nodeValue : "";
		phone1 = (tree.childNodes[1].childNodes[0]) ? tree.childNodes[1].childNodes[0].nodeValue : "";
		phone2 = (tree.childNodes[2].childNodes[0]) ? tree.childNodes[2].childNodes[0].nodeValue : "";
		email1 = (tree.childNodes[3].childNodes[0]) ? tree.childNodes[3].childNodes[0].nodeValue : "";
		email2 = (tree.childNodes[4].childNodes[0]) ? tree.childNodes[4].childNodes[0].nodeValue : "";
		skills = (tree.childNodes[5].childNodes[0]) ? tree.childNodes[5].childNodes[0].nodeValue : "";
		resumePath = (tree.childNodes[6].childNodes[0]) ? tree.childNodes[6].childNodes[0].nodeValue : "";
		resultId = (tree.childNodes[7].childNodes[0]) ? tree.childNodes[7].childNodes[0].nodeValue : "";
		skillIds = (tree.childNodes[8].childNodes[0]) ? tree.childNodes[8].childNodes[0].nodeValue : "";
		docPath = (tree.childNodes[9].childNodes[0]) ? tree.childNodes[9].childNodes[0].nodeValue : "";
	}
	
    //set these to text field
    document.desktopSearchForm.parsedName.value = name;
    document.desktopSearchForm.parsedPhone1.value = phone1;
    document.desktopSearchForm.parsedPhone2.value = phone2;
    document.desktopSearchForm.parsedEmail.value = email1;
    
    document.desktopSearchForm.parsedSkillIds.value = skillIds;
    document.desktopSearchForm.resultId.value = resultId;
    $('parsedSkills').innerHTML=skills;

	var hilite = name+","+phone1+","+phone2+","+email1;	
	var url = "importResume.do?mode=getResumeToImport&originalResumePath="+resumePath+"&noContext=1&hilite="+hilite;
	showResume(url);
}

function clearUpdateForm(){
	document.desktopSearchForm.parsedName.value = '';
    document.desktopSearchForm.parsedPhone1.value = '';
    document.desktopSearchForm.parsedPhone2.value = '';
    document.desktopSearchForm.parsedEmail.value = '';
    $('parsedSkills').innerHTML='';
    showResume('common/blank.html');
    
}

function showResume(url){
	if(url!=""){
		document.getElementById("printFrame").src = url
		//window.frames['printFrame'].focus();
		//window.frames['printFrame'].print();
	}
}

function onClickSingleImport(){
	var resultId = dataGrid.getSelectedId();
	var sessionId = document.desktopSearchForm.sessionId.value;
	var sessionType = document.desktopSearchForm.sessionType.value;
	if(resultId =='' || resultId == null){
		alert('Please select a <bean:message key="common.candidate"/>');
	}else{	
		var url="desktop.do?mode=detailedImport&resultId="+resultId+"&sessionId="+sessionId+"&sessionType="+sessionType+"&parsedResumePath="+docPath;
		showInPopUp(url,940,580,loadGrid,true);
	}

}

function onClickChange(){
	var resultId = dataGrid.getSelectedId();
	var name = document.desktopSearchForm.parsedName.value;
    var phone1 = document.desktopSearchForm.parsedPhone1.value;
	var phone2 = document.desktopSearchForm.parsedPhone2.value;
    var email = document.desktopSearchForm.parsedEmail.value;
    var skills = ''; /* $('parsedSkills').innerHTML; */
    if(resultId =='' || resultId == null){
		alert('Please select a <bean:message key="common.candidate"/> to change');
	}else{	
		var pars = "mode=changeParsedResultData&resultId="+resultId+"&parsedName="+name+"&parsedPhone1="+phone1+"&parsedPhone2="+phone2+"&parsedEmail="+email+"&parsedSkills="+skills;
		var myAjax = ajaxCall("desktop.do","get",pars,onChangeComplete,reportError);
	}
}

function onChangeComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="common.error.unable_to_process_request"/>');
		return;
	}
	//get returned changed id and modified data them from grid
	var tree = xmlFile.getElementsByTagName("content")[0];
	var nodes=tree.childNodes.length;
	var moz = (typeof document.implementation != 'undefined') && (typeof document.implementation.createDocument != 'undefined');
	var ie = (typeof window.ActiveXObject != 'undefined');
	var name, email1, resultId;
	if(ie) {
		name =  tree.childNodes(0).text;
		email1 = tree.childNodes(3).text;
		resultId = tree.childNodes(7).text;
	} else {			
		name =  (tree.childNodes[0].childNodes[0]) ? tree.childNodes[0].childNodes[0].nodeValue : "";
		email1 = (tree.childNodes[3].childNodes[0]) ? tree.childNodes[3].childNodes[0].nodeValue : "";
		resultId = (tree.childNodes[7].childNodes[0]) ? tree.childNodes[7].childNodes[0].nodeValue : "";
	}
	
	if(sessionType=='<%=DesktopConstants.SESSION_TYPE_EMAIL_IMPORT%>' || sessionType=='<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>'){
		// set parsedName with new Name
		dataGrid.cells(resultId,"2").setValue(name);
		// set parsedEmail with new Email
		dataGrid.cells(resultId,"3").setValue(email1);
	}else if(sessionType=='<%=DesktopConstants.SESSION_TYPE_BROWSER_IMPORT%>'){
		dataGrid.cells(resultId,"0").setValue(name);
		// set parsedEmail with new Email
		dataGrid.cells(resultId,"1").setValue(email1);
	}else{
		dataGrid.cells(resultId,"1").setValue(name);
		// set parsedEmail with new Email
		dataGrid.cells(resultId,"2").setValue(email1);
	}
	alert('<bean:message key="common.candidate"/> data updated');
}

function deleteApplicant(){
	var resultId = dataGrid.getSelectedId();
	if(resultId =='' || resultId == null){
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>');
	}else{
		onClickDelete(resultId);
	}
}

function onClickDelete(id){
		var pars = "mode=deleteParsedResult&resultId="+id;
		var myAjax = ajaxCall("desktop.do","get",pars,onDeleteComplete,reportError);
}

function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="common.error.unable_to_process_request"/>');
		return;
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	var prevIndex = 0;
	for(var I=0; I<deletedIds.length; I++){
		prevIndex = dataGrid.getRowIndex(deletedIds[I]);
		dataGrid.deleteRow(deletedIds[I]);
	}
	
	if(dataGrid.getRowId(prevIndex)!=null){
		dataGrid.selectRow(prevIndex,true, false);
	}else if(dataGrid.getRowId(prevIndex-1)!=null){
		dataGrid.selectRow(prevIndex-1,true, false);
	}else{
		dataGrid.clearSelection();
		clearUpdateForm();
	}

}

function onAddSkills(){
	var skillIds = document.desktopSearchForm.parsedSkillIds.value;
	var resultId = document.desktopSearchForm.resultId.value;
	var rowId = dataGrid.getSelectedId();
	if(resultId =='' || resultId == null){
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>');
	}else{	
		var url="desktop.do?mode=addSkills&resultId="+resultId+"&parsedSkillIds="+skillIds+"&rowId="+rowId;
		showInPopUp(url,450,270,loadGrid,true);
	}
	
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}


function importAll(){
	document.desktopSearchForm.sessionId.value = '<bean:write name="desktopSearchForm" property="sessionId"/>';
	showUpdater('importAll',{setHeight: false, setWidth: false, offsetLeft: -50});
	document.desktopSearchForm.submit();
}

function checkDuplicate(){
	var resultId = dataGrid.getSelectedId();
	if(resultId != "" && resultId != null){
		if(document.desktopSearchForm.sessionType.value == '<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>'  || document.desktopSearchForm.sessionType.value == '<%=DesktopConstants.SESSION_TYPE_BROWSER_IMPORT%>') {
			window.open('desktop.do?mode=duplicateImport&resultId='+resultId+'&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>','About','width=800,height=600,scrollbars=yes,resizable=yes,status=no');
		} else {
			window.open('desktop.do?mode=duplicateImport&resultId='+resultId+'&sessionId=<bean:write name="desktopSearchForm" property="sessionId"/>','_new');		
		}
	}else{
		alert("Select a <bean:message key="common.candidate"/> to <bean:message key="common.import"/>");
	}
	
}

function finishBulkImport(){
	document.desktopSearchForm.mode.value = "finishBulkImport";
	document.desktopSearchForm.sessionId.value = '<bean:write name="desktopSearchForm" property="sessionId"/>';
	document.desktopSearchForm.submit();
}

function onWindowLoad(){
	initPopUp();
	initGrid();
	clearUpdateForm();
}

function reloadInEveryFiveSecond(){
	var sessionComplete = document.desktopSearchForm.isSessionComplete.value;
	if(sessionComplete!=1){
		window.setTimeout("reload()", 10000);
	}
}

window.onload=onWindowLoad;
</script>

