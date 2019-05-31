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
			return obj.grid.getUserData(obj.cell.parentNode.idd,"reportName");
			break;	
		case 2:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"templateName");
			break;
	}
	return obj.cell.innerHTML;
}
</script>
<html:form action="/masters">
<html:hidden property="mode"/>
<div class="contentDiv">
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="admin_master_label_report_templates"/></td>
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

</div>
</html:form>
<script language="javascript">
var dataGrid=null;
var maxHeight=400;

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function initReportTemplatesGrid(){
   	dataGrid = new dhtmlXGridObject('dataGrid'); 
   	dataGrid.imgURL = "images/";
   	dataGrid.setHeader("&nbsp;,<bean:message key="report_master.label.template_name"/>,<bean:message key="report_master.label.report_name"/>"); 	   	
   	dataGrid.setInitWidths("18,300,400");
   	dataGrid.setColAlign("left,left,left");
   	dataGrid.setColTypes("link,link,ro"); 
   	dataGrid.setColSorting("na,str,str");
	dataGrid.enableAutoHeigth(true,maxHeight);
	dataGrid.enableMultiline(true);
   	dataGrid.init(); 
	dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
   	loadReportTemplatesGrid();
}

function loadReportTemplatesGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML("reportTemplates.do?mode=getReportTemplates");
}

function addNewRecord(){
	window.location = "reportTemplates.do?mode=addReportTemplate";
}

function editRecord(id){
    var url="reportTemplates.do?mode=addReportTemplate&reportTemplateId=" +id;
    window.location = url;
}

function deleteRecord(id){
    var val = dataGrid.getUserData(id,"templateName");
    retVal = confirm('<bean:message key="report_master.label.confirm_delete_template"/>' + ' ' + val + '. ' + '<bean:message key="common.continue?"/>');
    if (retVal == true) {
		var pars = "mode=deleteReportTemplate&reportTemplateId="+id;
		var myAjax = ajaxCall("reportTemplates.do","get",pars,onDeleteComplete,reportError);
    }
}

function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="report_master.error.can_not_delete_template"/>');
		return;
	}
	//loadGrid();
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
	initReportTemplatesGrid();
}
window.onload=onWindowLoad;
</script>