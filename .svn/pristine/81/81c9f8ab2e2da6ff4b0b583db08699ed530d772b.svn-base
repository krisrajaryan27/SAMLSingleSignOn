<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.export.ExportConstants"%>
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
			return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_I_Comment");
			break;
		case 1:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"degree");
			break;	
		case 3:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"alias");
			break;
	}
	return obj.cell.innerHTML;
}
</script>
<html:form action="/import">
<html:hidden property="mode"/>
<html:hidden property="filePath" name="importForm"/>
<html:hidden property="entityType" name="importForm"/>
<div class="contentDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td id="monthYear" class="content Grey" style="padding-left:10px; padding-right:10px;">Degrees Master</td>
			<td class="rightC"></td>
		</tr>
		</table>
    </td> 
    <td>
	<div class="navBtnTab" style="width:170px;float: right;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
		<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
		<a href="#" style="width:80px;" onclick="addNewRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a>
		<a href="#" style="text-align:right;background:none;padding-left: 10px;" onmouseover="changeImage('exportToExl', 'images/excel_co.GIF')" onmouseout="changeImage('exportToExl', 'images/excel_bw.GIF')">
			<img src="images/excel_bw.GIF" width="16" height="16" style="border:0px;" id="exportToExl" onclick="javascript:exportToExcel(); return false;" title="<bean:message key="common.export_to_excel"/>"/>
		</a>  
		
		<a href="#" style="text-align:right;background:none;padding-left: 10px;" onmouseover="changeImage('importFromExl', 'images/ico_csv_import_co.gif')" onmouseout="changeImage('importFromExl', 'images/ico_csv_import_bw.gif')">
			<img src="images/ico_csv_import_bw.gif" width="16" height="16" style="border:0px;" id="importFromExl" onclick="javascript:importFromExcel(); return false;" title="<bean:message key="common.import"/>"/>
		</a>  
		
	</div>			    
    </td> 
  </tr> 
</table> 
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="dataGrid" style="width: 738px;height: 18px;" ></div>
		</td>
	</tr>
</table>

</div>
</html:form>
<script language="javascript">
var dataGrid=null;
var maxHeight=400;

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function exportToExcel(){
	var url = "export.do?mode=exportMasters&exportEntityType=<%= ExportConstants.ENTITY_DEGREES%>&ids="+dataGrid.getAllItemIds();
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}

function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader("&nbsp;,<bean:message key="admin_master_degree_cat.label"/>,<bean:message key="common.type"/>,<bean:message key="admin_master_degree_alias_cat.label"/>"); 
	   	dataGrid.setInitWidths("18,220,100,380");
	   	dataGrid.setColAlign("left,left,left,left");
	   	dataGrid.setColTypes("link,link,ro,ro"); 
	   	dataGrid.setColSorting("str,str,str,str");
		dataGrid.enableAutoHeigth(true,maxHeight);
		dataGrid.enableMultiline(true);
	   	dataGrid.init(); 
		dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	   	loadGrid();
}

function loadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML("masters.do?mode=manageDegreeAliases&subMode=<%=MastersConstants.SUB_MODE_GET%>");
}

function addNewRecord(){
	var url="masters.do?mode=manageDegreeAliasesMaster&subMode=<%=MastersConstants.SUB_MODE_ADD%>";
	showInPopUp(url,500,350,loadGrid,true);
}

function editRecord(id){
    var url="masters.do?mode=manageDegreeAliasesMaster&subMode=<%=MastersConstants.SUB_MODE_EDIT%>&degreeName=&degreeId="+id;
	showInPopUp(url,500,350,loadGrid,true);
}
function deleteRecord(id){
    var val = dataGrid.getUserData(id,"degree");
    retVal = confirm('<bean:message key="admin_master_degree.label.confirm_delete"/>' + ' ' + val + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=manageDegreeAliases&subMode=<%=MastersConstants.SUB_MODE_DELETE%>&degreeId="+id;
		var myAjax = ajaxCall("masters.do","get",pars,onDeleteComplete,reportError);
    }
}

function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="admin_master_degree.errors.failed_delete"/>');
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

function importFromExcel(){
	var url = "import.do?mode=importMasters&importEntityType=<%= ExportConstants.ENTITY_DEGREES%>";
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
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
	document.importForm.filePath.value=filePath;
	document.importForm.entityType.value='<%= ExportConstants.ENTITY_DEGREES%>';
	document.importForm.mode.value = "showMasterCSVFieldMappings";
	document.importForm.submit();

}
</script>
