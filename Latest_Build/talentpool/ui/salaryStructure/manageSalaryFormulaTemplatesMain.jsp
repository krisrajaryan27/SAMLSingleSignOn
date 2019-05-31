<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_group.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/cookies.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<style>
div.gridbox table.obj td.group_row{
    vertical-align:middle; font-family:Tahoma; font-size:10pt; font-weight:bold; height:30px;  border:0px;  border-bottom: 1px solid #999; 
    }
</style>
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
    <td  valign="bottom">
	<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
	<tr>
		<td class="leftC"></td>
		<td id="salaryFormula" class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="master_salary_formula_label.header"/></td>
		<td class="rightC"></td>
	</tr>
	</table>
    </td> 
    <td>	
    	<div class="navBtnTab" style="width:105px;float: right;">
		<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
		<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
		<a href="#" onclick="javascript: addSalaryFormula();" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
		</div>   
    </td> 
  </tr> 
</table> 
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="salaryFormulaGridBox"  style="width: 738px;height: 350px;" ></div>	
		</td>
	</tr>
	<tr align="right" style="height: 25px;">
		<td>
			<a class="green" href="#" onclick="javascript: changeCTCRounding();" ><bean:message key="master_salary_structure.ctcRounding.link.changeCTCRounding"/> </a>
		</td>		
	</tr>
</table>
</div>
<script>
var salaryFormulaGridId='salaryFormulaGridBox';
var salaryFormulaGrid;
function initSalaryFormulaGrid(){
	salaryFormulaGrid = new dhtmlXGridObject(salaryFormulaGridId); 
	salaryFormulaGrid.imgURL = "images/dhtmlxGrid/"; 
	salaryFormulaGrid.setHeader('&nbsp;,<bean:message key="salary_component_label"/>,<bean:message key="master_salary_components_label.formula"/>,<bean:message key="master_salary_structure.salaryComponentType"/>,<bean:message key="master_salary_structure.max_limit"/>,'); 
	salaryFormulaGrid.setInitWidths("18,200,160,100,240,0");
	salaryFormulaGrid.setColAlign("center,left,left,left,left,left");
	salaryFormulaGrid.setColTypes("ro,ro,ro,ro,ro,ro"); 
	salaryFormulaGrid.setColSorting("na,cstr,na,cstr,na,na");
	salaryFormulaGrid.attachEvent("onRowDblClicked",onRowDoubleClick);
	salaryFormulaGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	//salaryFormulaGrid.enableAutoHeigth(true,"400");
	salaryFormulaGrid.init();
	salaryFormulaGrid.setHeaderCursor(",pointer,,pointer,,");
	loadSalaryFormulaGrid();
}
function loadSalaryFormulaGrid(){
	salaryFormulaGrid.loadXML("salaryStructure.do?mode=getSalaryFormulaXMLFile", grouptheGrid);
}
function reLoadSalaryFormulaGrid(){
	salaryFormulaGrid.clearAll();
	salaryFormulaGrid.loadXML("salaryStructure.do?mode=getSalaryFormulaXMLFile", grouptheGrid);
}
function grouptheGrid(){
	salaryFormulaGrid.customGroupFormat=function(name,count){
	       return name;
	}
	salaryFormulaGrid.groupBy(5);
}
function doOnLoad(){
	initPopUp();
	initSalaryFormulaGrid();
}
function onRowDoubleClick(id){
	if(id!=0)
		onClickSalaryFormula(id);
	else
		return false;
}
function onClickSalaryFormula(id){
	editSalaryFormula(id);
}
function onDeleteSalaryFormula(id){
	var pars = "mode=deleteSalaryFormula&formulaId="+id;
	var myAjax = ajaxCall("salaryStructure.do","get",pars,onDeleteComplete,reportError);
}
function onDeleteComplete(response){
	xmlFile = response.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_locations.error.can_not_delete_location"/>'+'<bean:message key="common.positions"/>');
		return;
	}
	reLoadSalaryFormulaGrid();
}
function addSalaryFormula(){
	var url = "salaryStructure.do?mode=addEditSalaryFormula";
	window.setTimeout("showInPopUp('"+url+"',550,250,reLoadSalaryFormulaGrid,true);", 10);
}
function editSalaryFormula(id){
	var url = "salaryStructure.do?mode=addEditSalaryFormula&formulaId="+id;
	window.setTimeout("showInPopUp('"+url+"',550,250,reLoadSalaryFormulaGrid,true);", 10);
}
function changeCTCRounding(){
	var url = "salaryStructure.do?mode=changeCTCRounding";
	window.setTimeout("showInPopUp('"+url+"',500,210,null,true);", 10);
}
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
window.onload = doOnLoad;
</script>