<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%><div class="contentDiv">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/cookies.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
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
    <td  valign="bottom">
	<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
	<tr>
		<td class="leftC"></td>
		<td id="salaryComponents" class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="admin_salary_components_label.header"/></td>
		<td class="rightC"></td>
	</tr>
	</table>
    </td> 
    <td>	
    	<div class="navBtnTab" style="width:105px;float: right;">
		<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
		<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
		<a href="#" onclick="javascript: addSalaryComponent();" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
		</div>   
    </td> 
  </tr> 
</table> 
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="salaryComponentGridBox"  style="width: 738px;height: 18px;"></div>	
		</td>
	</tr>
</table>
</div>
<script>
var salaryComponentGridId='salaryComponentGridBox';
var salaryComponentGrid;
function initSalaryComponentGrid(){
	salaryComponentGrid = new dhtmlXGridObject(salaryComponentGridId); 
	salaryComponentGrid.imgURL = "images/dhtmlxGrid/"; 
	salaryComponentGrid.setHeader("&nbsp;,<bean:message key="common.name"/>,<bean:message key="common.description"/>,<bean:message key="common.type"/>,<bean:message key="master_salary_structure.salaryComponentCategory"/>"); 
	salaryComponentGrid.setInitWidths("18,150,250,100,200");
	salaryComponentGrid.setColAlign("center,left,left,left,left");
	salaryComponentGrid.setColTypes("ro,link,ro,ro,ro"); 
	salaryComponentGrid.setColSorting("na,cstr,cstr,cstr,cstr");
	salaryComponentGrid.attachEvent("onRowDblClicked",onRowDoubleClick);
	salaryComponentGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	salaryComponentGrid.enableAutoHeigth(true,"400");
	salaryComponentGrid.init();
	loadSalaryComponentGrid();
}
function loadSalaryComponentGrid(){
	salaryComponentGrid.loadXML("salaryStructure.do?mode=getSalaryComponentXMLFile");	
}
function reLoadSalaryComponentGrid(){
	salaryComponentGrid.clearAll();
	salaryComponentGrid.loadXML("salaryStructure.do?mode=getSalaryComponentXMLFile");	
}
function doOnLoad(){
	initPopUp();
	initSalaryComponentGrid();
}
function onRowDoubleClick(id){
	onClickSalaryComponent(id);
}
function onClickSalaryComponent(id){
	editSalaryComponent(id);
}
function onDeleteSalaryComponent(id){
	var pars = "mode=deleteSalaryComponent&salaryComponentId="+id;
	var myAjax = ajaxCall("salaryStructure.do","get",pars,onDeleteComplete,reportError);
}
function onDeleteComplete(response){
	xmlFile = response.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_locations.error.can_not_delete_location"/>'+'<bean:message key="common.positions"/>');
		return;
	}
	reLoadSalaryComponentGrid();
}
function addSalaryComponent(){
	var url = "salaryStructure.do?mode=addEditSalaryComponent";
	window.setTimeout("showInPopUp('"+url+"',550,320,reLoadSalaryComponentGrid,true);", 10);
}
function editSalaryComponent(id){
	var url = "salaryStructure.do?mode=addEditSalaryComponent&salaryComponentId="+id;
	window.setTimeout("showInPopUp('"+url+"',550,320,reLoadSalaryComponentGrid,true);", 10);
}
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function reloadWindow(){
	loadSalaryComponentGrid();
}
window.onload = doOnLoad;
</script>
