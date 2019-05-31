<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript">
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	switch(obj.cell._cellIndex){
		case 0:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_I_Comment");
			break;
		case 1:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"officeName");
			break;	
		case 2:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"officeAddress");
			break;
	}
	return obj.cell.innerHTML;
}
</script>
<div class="contentDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="master_locations.label.offices"/> for <b><bean:write property="locationName" name="mastersForm"/></b></td>
			<td class="rightC"></td>
		</tr>
		
		</table>
    </td> 
    <td>
		<div class="navBtnTab" style="width:105px;float: right;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
			<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
			<a href="#" style="width:80px;" onclick="addNewRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a>
		</div>			    
    </td> 
  </tr> 
</table> 
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="dataGrid"  width="738"></div>
		</td>
	</tr>
</table>

<div class="navBtn" style="float: right;padding-top:10px;">
	<a href="#" style="width:150px; margin-left:5px;" class="active" onclick="gotoMaster('location.do?mode=manageLocationsMaster');"><span class="rightC"></span><span class="leftC"></span><bean:message key="master_locations.label.back_to_locations"/></a>
</div>

</div>

<script language="javascript">
var dataGrid=null;
var maxHeight=400;

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/";
	   	dataGrid.setHeader("&nbsp;,<bean:message key="master_locations.label.offices"/>,<bean:message key="common.address"/>"); 	   	
	   	dataGrid.setInitWidths("18,200,500");
	   	dataGrid.setColAlign("left,left,left");
	   	dataGrid.setColTypes("link,link,ro"); 
	   	dataGrid.setColSorting("str,str,str");
		dataGrid.enableAutoHeigth(true,maxHeight);
		dataGrid.enableMultiline(true);
	   	dataGrid.init(); 
		dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	   	loadGrid();
}

function loadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML("location.do?mode=getOffices&locationId=<bean:write property="locationId" name="mastersForm"/>");
}

function addNewRecord(){
	var url="location.do?mode=addOffice&locationId=<bean:write property="locationId" name="mastersForm"/>";
	showInPopUp(url,510,280,loadGrid,true);
}

function editRecord(id){
    var url="location.do?mode=addOffice&locationId=<bean:write property="locationId" name="mastersForm"/>&officeId=" +id;
	showInPopUp(url,510,280,loadGrid,true);
}

function deleteRecord(id){
    var val = dataGrid.getUserData(id,"officeName");
    retVal = confirm('<bean:message key="master_locations.label.confirm_delete_office"/>' + ' ' + val + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=deleteOffice&officeId="+id;
		var myAjax = ajaxCall("location.do","get",pars,onDeleteComplete,reportError);
    }
}
function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_locations.error.can_not_delete_office"/>');
		return;
	}
	loadGrid();
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function onWindowLoad(){
	initPopUp();
	initGrid();
}
window.onload=onWindowLoad;
</script>