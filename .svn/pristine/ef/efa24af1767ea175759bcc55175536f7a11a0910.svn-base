<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties" %>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
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
			return "<bean:message key="common.delete"/>";
			break;
		case 2:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"cost_type");
			break;	
		case 3:
			return "<bean:message key="common.view_details"/>";
			break;	
		case 4:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"remark");
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
			<td id="monthYear" class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="costs.label.title"/></td>
			<td class="rightC"></td>
		</tr>
		</table>
    </td> 
    <td>
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_ADD_EXPENSE">
	<div class="navBtnTab" style="width:105px;float: right;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
	<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
	<a href="#" style="width:80px;" onclick="addNewRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
	</div>			    
    </logic:equal>
    </td> 
  </tr> 
</table>
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="dataGrid"  width="738" style="height: 400px;"></div>
		</td>
	</tr>
</table> 

</div>

<script language="javascript">

var dataGrid=null;


function initGrid(){
	dataGrid = new dhtmlXGridObject('dataGrid'); 
	dataGrid.imgURL = "images/"; 
	dataGrid.setHeader("&nbsp;,<bean:message key="costs.label.cost_date"/>,<bean:message key="costs.label.cost_purpose"/>,<bean:message key="costs.label.amount"/>,<bean:message key="costs.label.remarks"/>,"); 
	dataGrid.setInitWidths("18,100,180,93,330,0");//total 721
	dataGrid.setColAlign("left,left,left,left,left,left");
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_ADD_EXPENSE">
	dataGrid.setColTypes("link,ro,ro,link,ro,ro"); 
	</logic:equal>
	<logic:notEqual value="true" name="permissionSet" scope="session" property="PERMISSION_ADD_EXPENSE">
	dataGrid.setColTypes("ro,ro,ro,ro,ro,ro"); 
	</logic:notEqual>
	dataGrid.setColSorting("na,cost_date_sort,cstr,na,na,na");
	dataGrid.enableMultiline(false);
	dataGrid.init(); 
	dataGrid.enableSmartRendering(true); //do not enable auto height
	dataGrid.setHeaderCursor(",pointer,pointer,,,");
	dataGrid.setSortImgState(true,1,"ASC");
	dataGrid.setColumnHidden(5,true);
	dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	loadGrid();
}

function loadGrid(){
	dataGrid.loadXML("costs.do?mode=getCosts");
}
function reLoadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML("costs.do?mode=getCosts");
}

function cost_date_sort(a,b,order,aId,bId){
	var a0=dataGrid.cells(aId,5).getValue();
	var b0=dataGrid.cells(bId,5).getValue();
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

function addNewRecord(){
	var url="costs.do?mode=addCost";
	window.setTimeout("showInPopUp('"+url+"',600, 570,reLoadGrid,true);", 100);
}
function editRecord(id){
    var url="costs.do?mode=addCost&costId="+id;
	window.setTimeout("showInPopUp('"+url+"',600, 570,reLoadGrid,true);", 100);
}
function deleteRecord(id){
    retVal = confirm('<bean:message key="costs.label.confirm_delete"/>. <bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=deleteCost&costId="+id;
		var myAjax = ajaxCall("costs.do","get",pars,onDeleteComplete,reportError);
    }
}
function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="costs.error.failed_delete"/>');
		return;
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		dataGrid.deleteRow(deletedIds[I]);
	}
	dataGrid.clearSelection();
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