<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>

<div class="contentDivPop" >
<html:form action="/desktop" >
	<html:hidden property="applicantId" name="desktopSearchForm"/>
	<html:hidden property="sessionId" name="desktopSearchForm"/>
	<html:hidden property="userId" name="desktopSearchForm"/>
	<html:hidden property="mode" name="desktopSearchForm" value="attachEmail"/>
	<table cellspacing="0" cellpadding="2">
	<tr>
	<td style="width: 40px;">Name:</td>
	<td><html:text property="fromName" styleId="fromName" size="30"/></td>
	</tr>
	<tr>
	<td>Email:</td>
	<td><html:text property="fromEmail" styleId="fromEmail" size="30"/></td>
	</tr>
	</table>
	</html:form>
	<br/>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="dataGrid" style="width:470px;height: 25px;"></div>
			</td>
		</tr>
	</table>
	<div class="navBtn" style="float: right;margin-top: 10px;">
		<a href="#" style="width:65px;" class="active" onclick="javascript:submitForm();" id="submit"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
	</div>
</div>

<script language="javascript">
var dataGrid=null;
var maxHeight=210;
var firstRequest='';
function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader("<bean:message key="common.name"/>,<bean:message key="common.email1"/>,<bean:message key="common.email2"/>"); 
	   	dataGrid.setInitWidths("150,150,150");
	   	dataGrid.setColAlign("left,left,left");
	   	dataGrid.setColTypes("ro,ro,ro"); 
	   	dataGrid.setColSorting("cstr,cstr,cstr");
		dataGrid.enableAutoHeigth(true,maxHeight);
		dataGrid.attachEvent("onRowSelect",OnRowSelected);
	   	dataGrid.init(); 
	   	loadGrid();
}

function loadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML(uncache("desktop.do?mode=getXMLForAttachToSearch&fromName=" + document.desktopSearchForm.fromName.value + "&fromEmail=" + document.desktopSearchForm.fromEmail.value + "&firstRequest=" + firstRequest));
	if(firstRequest!=''){
		document.desktopSearchForm.fromName.value='';
		document.desktopSearchForm.fromEmail.value='';
	}
	firstRequest=''
}

function OnRowSelected(id){ 
	if(!isNaN(id)){
		document.desktopSearchForm.applicantId.value=id;
   	}
}

function submitForm(){
	if(document.desktopSearchForm.applicantId.value==""){
		alert('<bean:message key="common.please_select"/> <bean:message key="common.candidate"/>');
	}else{
		showUpdater('submit',{setHeight: false, setWidth: false, offsetLeft: -50});
		document.desktopSearchForm.submit();
	}
}

function onCriteriaChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		case Event.KEY_UP:
		case Event.KEY_DOWN: 
		break;
		case Event.KEY_PAGEUP:
		case Event.KEY_PAGEDOWN:
		break;
		case Event.KEY_RETURN:
		break;
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			loadGrid();
		}
	}
}

function onWindowLoad(){
	firstRequest='1';
	initGrid();
	Event.observe($('fromName'), "keyup", onCriteriaChange.bindAsEventListener(this));
	Event.observe($('fromEmail'), "keyup", onCriteriaChange.bindAsEventListener(this));
}
window.onload=onWindowLoad;
</script>