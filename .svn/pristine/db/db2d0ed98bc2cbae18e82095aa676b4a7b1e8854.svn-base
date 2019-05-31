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
			<td class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="master_stages.title.stages"/></td>
			<td class="rightC"></td>
		</tr>
		
		</table>
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

function initGrid(){
	   	dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/";
	   	dataGrid.setHeader("<bean:message key="master_stages.label.stage_id"/>,<bean:message key="master_stages.label.stage_name"/>"); 	   	
	   	dataGrid.setInitWidths("100,600");
	   	dataGrid.setColAlign("left,left");
	   	dataGrid.setColTypes("ro,link"); 
	   	dataGrid.setColSorting("str,str");
		dataGrid.enableAutoHeigth(true,maxHeight);
		dataGrid.enableMultiline(true);
	   	dataGrid.init(); 
		dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	   	loadGrid();
}

function loadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML("step.do?mode=getStages");
}

function editRecord(id){
    var url="step.do?mode=editStage&stepLevel=" +id;
	showInPopUp(url,500,250,loadGrid,true);
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