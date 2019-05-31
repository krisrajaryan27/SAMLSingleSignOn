<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties" %>
<%@page import="com.talentPool.masters.constants.MastersConstants,
	com.talentPool.common.CommonConstants"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_group.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>

<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<style>
div.gridbox table.obj td.group_row{
    vertical-align:middle; font-family:Tahoma; font-size:10pt; font-weight:bold; height:30px;  border:0px;  border-bottom: 1px solid #999; 
    }
</style>

<script language="JavaScript">
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	switch(obj.cell._cellIndex){
		case 0:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_I_Comment");
			break;
		case 1:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"stepName");
			break;	
		case 2:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"stepDesc");
			break;
		case 3:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"moveup");
			break;
	}
	return obj.cell.innerHTML;
}
</script>
<div class="contentDiv">
<% 
	if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
	<table  id="m_errortable" > 
		<tr>
	   	<td class="header">
	      <b><bean:message key="errors.following_errors"/></b>
		  </td>               
			</tr>
    	<tr>
        <td class="message"><html:errors/></td>               
    	</tr>
	</table>
	<br>
<% } %>
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td style="vertical-align: bottom;width: 160px;">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="admin_master_label_steps"/></td>
			<td class="rightC"></td>
		</tr>
		
		</table>
    </td> 
    <td style="vertical-align: bottom;"><img id="hideDisabled" src="images/checkboxunchecked.gif" onclick="javascript: toggleCheckBox(this);" /></td>
    <td style="vertical-align: bottom;"><bean:message key="master_steps.label.showDisabled" /></td>
	<td>
		<div class="navBtnTab" style="width:120px;float: right;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
			<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
			<a href="#" style="width:100px;" onclick="addNewRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="master_steps.label.add_step"/></a>
		</div>			    
    </td> 
  </tr> 
</table> 
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="dataGrid" style="height:400px;width:738px;"></div>
		</td>
	</tr>
</table>

</div>
<script language="javascript">
var dataGrid=null;
var maxHeight=800;

selectedCheckBox="images/checkboxchecked.gif";
deselectedCheckBox="images/checkboxunchecked.gif";

function toggleCheckBox(obj) {
	if(obj){
		var src = obj.src;
		if (src.indexOf(deselectedCheckBox) != -1) {
			obj.src = selectedCheckBox;
			loadGrid('<%=CommonConstants.FALSE%>');
		} else {
			obj.src = deselectedCheckBox;
			loadGrid('<%=CommonConstants.TRUE%>');
		}
	}
}

function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/dhtmlxGrid/";
	   	dataGrid.setHeader("&nbsp;,<bean:message key="master_step.label.step"/>,<bean:message key="master_step.label.description"/>,<bean:message key="master_steps.label.stage_name"/>,<bean:message key="master_steps.label.schedulable"/>,<bean:message key="master_steps.label.disabled"/>"); 	   	
	   	dataGrid.setInitWidths("18,200,300,0,100,100");
	   	dataGrid.setColAlign("left,left,left,left,center,center");
	   	dataGrid.setColTypes("ro,ro,ro,ro,ro,ro"); 
	   	dataGrid.setColSorting("na,na,na,na,na,na");
		dataGrid.enableMultiline(true);
		dataGrid.attachEvent("onXLE", function(grid_obj,count){
			grid_obj.customGroupFormat=function(name,count){
		       return name;
			}
			grid_obj.groupBy(3);
		});
	   	dataGrid.init(); 
		dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	   	loadGrid('<%=CommonConstants.TRUE%>');
}

function loadGrid(hideDisabled){
	dataGrid.clearAll();
	dataGrid.loadXML("step.do?mode=getSteps&hideDisabled=" + hideDisabled);
}

function addNewRecord(){
	var url="step.do?mode=addStep&subMode=<%=MastersConstants.SUB_MODE_ADD%>";
	showInPopUp(url,500,300,onWindowLoad,true);
}

function editRecord(id){
    var url="step.do?mode=addStep&stepId=" +id+"&subMode=<%=MastersConstants.SUB_MODE_EDIT%>";
	showInPopUp(url,500,300,onWindowLoad,true);
}

function deleteRecord(id){
    var val = dataGrid.getUserData(id,"stepName");
    retVal = confirm('<bean:message key="master_steps.label.confirm_delete_step"/>' + ' ' + val + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=deleteStep&stepId="+id;
		var myAjax = ajaxCall("step.do","get",pars,onDeleteComplete,reportError);
    }
}
function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_steps.error.can_not_delete_step"/>'+'<bean:message key="common.positions"/>');
		return;
	}
	loadGrid('<%=CommonConstants.TRUE%>');
	populateHideDisabled();
}

function moveUp(id,stage,rank,sys){
	var val = dataGrid.getUserData(id,"stepName");
	retVal = confirm('<bean:message key="master_steps.label.confirm_move_step"/>' + ' ' + val + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=moveStep&stepId="+id+"&stage="+stage+"&stepRank="+rank+"&systemStep="+sys+"&stepOrder=desc";
		var myAjax = ajaxCall("step.do","get",pars,onMoveComplete,reportError);
    }
}

function moveDown(id,stage,rank,sys){
	var val = dataGrid.getUserData(id,"stepName");
	retVal = confirm('<bean:message key="master_steps.label.confirm_move_step"/>' + ' ' + val + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=moveStep&stepId="+id+"&stage="+stage+"&stepRank="+rank+"&systemStep="+sys+"&stepOrder=asc";
		var myAjax = ajaxCall("step.do","get",pars,onMoveComplete,reportError);
    }
}

function onMoveComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_steps.error.can_not_move_step"/>'+'<bean:message key="common.positions"/>');
		return;
	}
	loadGrid();
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function populateHideDisabled() {
	var checkbox = $('hideDisabled');
	checkbox.src = deselectedCheckBox;
}

function onWindowLoad(){
	initPopUp();
	populateHideDisabled();
	initGrid();
}
window.onload=onWindowLoad;
</script>