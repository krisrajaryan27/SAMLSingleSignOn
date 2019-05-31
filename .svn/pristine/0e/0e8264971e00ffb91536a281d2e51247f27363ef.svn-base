<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/cookies.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript">
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	switch(obj.cell._cellIndex){
		case 0:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"deptName");
			break;	
		case 1:
			return "delete";
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
			<td id="monthYear" class="content Grey" style="padding-left:10px; padding-right:10px;"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %> <bean:message key="admin_master_label_master"/></td>
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
			<div id="dataGrid"  style="width: 738px;height: 18px;"></div>
		</td>
	</tr>
</table>
<br/>
<div class="Grey">
Note: Please select row and click "Add new" to add sub <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>
</div>
</div>
<script language="javascript">
var dataGrid=null;
var maxHeight=400;
var selectedId="";
function initGrid(){
        dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader("<bean:message key="admin_master_dept_cat.label"/>,&nbsp;"); 
	   	dataGrid.setInitWidths("700,18");
	   	dataGrid.setColAlign("left,left");
	   	dataGrid.setColTypes("ro,ro"); 
	   	dataGrid.setColSorting("str,str");
		dataGrid.enableAutoHeigth(true,maxHeight);
		dataGrid.attachEvent("onXLE",onGridLoadEnd);
		dataGrid.enableMultiline(true);
	   	dataGrid.init(); 
		dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	   	loadGrid();
       }

function loadGrid(){
    selectedId = dataGrid.getSelectedId();
	dataGrid.clearAll();
	dataGrid.loadXML("masters.do?mode=manageDepartments&subMode=<%=MastersConstants.SUB_MODE_GET%>");
}
function onGridLoadEnd(grd, itemsCnt){
	if(selectedId){
	dataGrid.setSelectedRow(selectedId,false,true,false);
	}
}
function addNewRecord(){
	var id = dataGrid.getSelectedId();
	if(id){
		var level = dataGrid.getUserData(id,"level");
		if(level != <%=MastersConstants.DEPARTMENT_LEVEL_5%>){
			var url="masters.do?mode=manageDepartmentMaster&subMode=<%=MastersConstants.SUB_MODE_ADD%>&parentDepartmentId="+id+"&parentDepartmentLevel="+level;
			showInPopUp(url,500,300,loadGrid,true);
		}else{
			alert('You can only add upto 4 level');
		}
	}else{
		alert('Please select row to add under it');
	}
}

function editRecord(id){
    var url="masters.do?mode=manageDepartmentMaster&subMode=<%=MastersConstants.SUB_MODE_EDIT%>&departmentName=&deptId="+id;
	showInPopUp(url,500,300,loadGrid,true);
}

function deleteRecord(id){
    var val = dataGrid.getUserData(id,"deptName");
    retVal = confirm('<bean:message key="admin_master_dept.label.confirm_delete_department"/>' + ' ' + val + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=manageDepartments&subMode=<%=MastersConstants.SUB_MODE_DELETE%>&deptId="+id;
		var myAjax = ajaxCall("masters.do","get",pars,onDeleteComplete,reportError);
    }
}
function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="admin_master_dept.error.failed_delete"/>');
		return;
	}
	//get returned deleted ids and delete them from grid
	/***
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		dataGrid.deleteRow(deletedIds[I]);
	}
	dataGrid.clearSelection();
	**/
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