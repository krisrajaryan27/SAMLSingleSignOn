<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties" %>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript">
function getCustomTitle(obj){
	switch(obj.cell._cellIndex){
		case 0:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"imgTitle");
			break;
		case 1:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"templateName");
			break;
	}
	return obj.cell.innerHTML;
}
</script>
<div class="contentDiv">
<% if(request.getAttribute(Globals.ERROR_KEY)!=null){ %>
<table id="m_errortable" >
	<tr><td class="header"><b><bean:message key="errors.following_errors"/></b></td></tr>
    <tr><td class="message"><html:errors/></td></tr>
</table>
<br/>
<% } %>	
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="requisition_approval_templates.label.title" /></td>
			<td class="rightC"></td>
		</tr>
		</table>
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
<div class="navBtn" style="float: left;">
	<a href="#" style="width:80px;" class="active" onclick="javascript:addNewRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a>
</div>
</div>
<script language="javascript">
var dataGrid=null;
var maxHeight=400;

function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader("&nbsp;,<bean:message key="requisition_approval_templates.label.template_name"/>"); 
	   	dataGrid.setInitWidths("18,700");
	   	dataGrid.setColAlign("left,left");
	   	dataGrid.setColTypes("link,link"); 
	   	dataGrid.setColSorting("str,str");
		dataGrid.enableAutoHeigth(true,maxHeight);
		dataGrid.enableMultiline(true);
	   	dataGrid.init(); 
		dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	   	loadGrid();
}

function loadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML("requisition.do?mode=loadRequisitionApprovalTemplates");
}

function addNewRecord(){
	window.location = "requisition.do?mode=requisitionApprovalTemplate";
}

function viewDetails(id){
 	window.location="requisition.do?mode=requisitionApprovalTemplate&requisitionApprovalTemplateId="+id;
}

function onWindowLoad(){
	initGrid();
}

function deleteRecord(id) {
	window.location="requisition.do?mode=deleteRequisitionApprovalTemplate&requisitionApprovalTemplateId="+id;
}
window.onload=onWindowLoad;
</script>