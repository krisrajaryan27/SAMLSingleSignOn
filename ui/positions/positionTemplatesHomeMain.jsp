<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.positions.PositionConstants,
                  com.talentPool.common.NavigationConstants,
                  com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="java.lang.Boolean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.custom.utils.CustomFieldUtils"%>
<%@page import="com.talentPool.custom.manager.CustomFieldManager"%>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%><link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<script language="JavaScript" src="js/criteriapane.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script>
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == '<bean:message key="positions.home.id.datagrid" />'){
		switch(obj.cell._cellIndex){
			case 0:				
				return ''; 
				break;
			case 1:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"delete");				
				break;
			case 2:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"positionName");
				break;
			case 3:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"department");
				break;		
			case 5:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"createdBy");
				break;
			case 6:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"creationDate");
				break;
		}	
	}
	return obj.cell.innerHTML;
}
</script>
<% 
String tokenId = (String)request.getSession().getAttribute("deletePositionTokenId");
%>
<div style="margin:23px; margin-bottom:0px;"> 	
<html:form action="/position">
<html:hidden property="t" name="positionForm"/>
<html:hidden property="mode"/>
<html:hidden property="positionOwnerId" name="positionForm"/>
<html:hidden property="departmentId" name="positionForm"/>
<html:hidden property="subDepartmentId" name="positionForm"/>
<html:hidden property="subSubDepartmentId" name="positionForm"/>
<html:hidden property="sub3DepartmentId" name="positionForm"/>
<html:hidden property="sub4DepartmentId" name="positionForm"/>
<html:hidden property="positionId" name="positionForm"/>
<html:hidden property="recruiterId" name="positionForm"/>
<html:hidden property="locationId" name="positionForm"/>
<html:hidden property="skillId" name="positionForm"/>
<html:hidden property="customFieldFilterId" name="positionForm"/>
<html:hidden property="customFieldFilterType" name="positionForm"/>
<html:hidden property="customFieldFilterValue" name="positionForm"/>
<html:hidden property="positionTypeExtInt" name="positionForm"/>
<% 
	if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
	<table id="m_errortable">
	    <tr>
        <td class="message"><html:errors/></td>               
	    </tr>
	</table><br>
<%
	}
%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0" align="right" style="margin-bottom:10px;padding: 0px; "> 
				<tr >
					<td>
				   		<div id="positions_0" style="display: block;">
					   		<a href="#" onclick="loadOther(1);" class="green"><bean:message key="common.positions"/></a>
				   		</div>
				   	</td>
				   	<td>&nbsp;|&nbsp;</td>
			 		<td>
				   		<div id="drafts_1" style="display: block;">
					   		<a href="#" onclick="loadOther(2);" class="green"><bean:message key="common.drafts"/></a>
			   			</div>	
			  	 	</td>
			  	 	<td>&nbsp;|&nbsp;</td>
			  	 	<td>
				   		<div id="templates_1" style="display: block;">
					   		<bean:message key="common.templates"/>
			   			</div>	
			  	 	</td>
				</tr>
			</table>
		</td>
		</tr>	
	</table>	
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<% 
				boolean addPosition=false; 
				%>				
				<logic:equal value="true" name="canAddRequisition" scope="request" >
					<% addPosition=true;  %>
				</logic:equal>		
				<% if(addPosition){ %>
					<div class="navBtnTab" style="width:150px;float: left;">
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  					 	
					 	<a href="#" onclick="copyPosition();return false;" style="width:120px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="positions_home.label.create_position"/></a> 				
					</div>	
				<% } %>		
			</td>
			<td>
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CREATE_POSITION_TEMPLATE">					
					<div class="navBtnTab"  style="width:160px;float: right;"	>					
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  		
						<a href="#" onclick="addNewTemplate();return false;" style="width:130px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="positions_home.label.add_new"/>&nbsp;<bean:message key="common.template"/></a> 				
					</div>
				</logic:equal>	
			</td>
		</tr>
	</table>	
	<!--Grig Box is here -->
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder" style="border-bottom: 0px;">
				<div id="datagrid" style="width:736px;height:228px;"></div>
			</td>
		</tr>
	</table>
	<div class="outerDiv" style="margin: 0px; padding: 0px;border-top:1px dashed #C4C4C4;background-color:#f9f9f9;font-weight:bold;">
		<table style="border: 0px; height: 20px;" cellpadding="0" cellspacing="0">
				<tr>
					<td style="width:18px;" class=""></td>        
					<td id="total" style="color:#666;" class="">Total: 0</td>       
				</tr>
		</table>
	</div> 	
</html:form>
</div>

<script>

function loadOther(choice){
	if(choice==1){
		window.location=uncache("position.do?mode=positionsHome");
	}else{	
		window.location=uncache("drafts.do?mode=draftsHome");
	}
}

function addNewTemplate(){
	window.location.href="position.do?mode=description&showCondition="+<%=PositionConstants.POSITION_STATUS_TEMPLATE%>+"&positionStatus="+<%=PositionConstants.POSITION_STATUS_TEMPLATE%>;
}

function deleteTemplate(id){
   
    if(!confirm("You are about to delete <bean:message key="common.position_template" /> '"+dataGrid.getUserData(id,"positionName")+"'. Continue?")){
    	return;
    }
    var deletePositionTokenId = '<%=tokenId%>';
    var pars = "mode=deletePosfromDB&positionId=" + id +"&deletePositionTokenId="+deletePositionTokenId;
    var myAjax = ajaxCall("position.do",'get',pars,onDeleteResponse, reportError);
}

function onDeleteResponse(request){
  	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
  	if(isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
		alert(errors[0]);
		return;
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		dataGrid.deleteRow(deletedIds[I]);
	}
	dataGrid.clearSelection();  
}

function viewDetails(id){
	window.location.href=uncache("position.do?mode=description&positionId="+id + "&showCondition="+<%=PositionConstants.POSITION_STATUS_TEMPLATE%>+"&positionStatus="+<%=PositionConstants.POSITION_STATUS_TEMPLATE%>);
     return true;
}

function uncache(url){
	var d = new Date();
	var time = d.getTime();
	return url + '&ta='+time;
} 

function getCriteriaQryString(){
	var qryString = "&positionOwnerId="+document.positionForm.positionOwnerId.value; 
	qryString += "&departmentId="+document.positionForm.departmentId.value;
	qryString += "&subDepartmentId="+document.positionForm.subDepartmentId.value;
	qryString += "&subSubDepartmentId="+document.positionForm.subSubDepartmentId.value;
	qryString += "&sub3DepartmentId="+document.positionForm.sub3DepartmentId.value;
	qryString += "&sub4DepartmentId="+document.positionForm.sub4DepartmentId.value;
	qryString += "&positionId="+document.positionForm.positionId.value;
	qryString += "&recruiterId="+document.positionForm.recruiterId.value;
	qryString += "&locationId="+document.positionForm.locationId.value;
	qryString += "&positionTypeExtInt="+document.positionForm.positionTypeExtInt.value;
	qryString += "&skillId="+document.positionForm.skillId.value;
	qryString += "&positionName="+$('positionName').value;
	qryString += "&showCondition="+<%=PositionConstants.POSITION_STATUS_TEMPLATE%>;
	qryString += "&customFieldFilterId="+document.positionForm.customFieldFilterId.value;
	qryString += "&customFieldFilterType="+document.positionForm.customFieldFilterType.value;
	qryString += "&customFieldFilterValue="+document.positionForm.customFieldFilterValue.value;
	return qryString;
}

function doOnLoad(){
	initPopUp();	
	initGrid();
	Event.observe($('positionName'), "keyup", onPositionFilterChange.bindAsEventListener(this));
	criteriaPane= new criteriaPane('criteriaDiv'); // variable used in left pane
	applyPreFilters(); //function defined in left panel	
}

function initGrid(){	
   	dataGrid = new dhtmlXGridObject('datagrid'); 
   	dataGrid.imgURL = "images/"; 
   	dataGrid.setHeader('&nbsp;,&nbsp;,<bean:message key="position_templates_home.label.template_name" />,<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>,<bean:message key="position_templates_home.label.created_by" />,<bean:message key="position_templates_home.label.created_on" />'); 
   	dataGrid.setInitWidths("5,20,320,160,120,90");
   	dataGrid.setColAlign("left,left,left,left,left,left");
   	dataGrid.setColTypes("ro,ro,link,ro,ro,ro"); 
   	dataGrid.setColSorting("na,na,cstr,cstr,cstr,str");
	dataGrid.enableAutoHeigth(true,"360");
	dataGrid.attachEvent("onXLE",dataGridOnLoadingEnd);
   	dataGrid.init(); 	   		   	
   	loadGrid();
   	dataGrid.setSortImgState(true,2,"ASC");
}	

function loadGrid(){
	var type = dataGrid.getSortingState();
	dataGrid.clearAll();
	if(type){
		dataGrid.setSortImgState(true,type[0],type[1]);
	}
	dataGrid.loadXML("positionTemplate.do?mode=XMLforTemplates"+ getCriteriaQryString());
}

function dataGridOnLoadingEnd() {
	var footerTxt='';
	var obj = $('total');
	var rowCnt = dataGrid.getRowsNum();
	footerTxt = '<bean:message key="positions_home.text.total_templates" arg0="'+rowCnt+'"  />';
	obj.innerHTML=footerTxt;
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function onCustomFieldFilterSelected(retVal){
	var custonFieldData = retVal;
	var customFieldValue = ''; 
	document.positionForm.customFieldFilterId.value=custonFieldData.getFieldId();
	document.positionForm.customFieldFilterType.value=custonFieldData.getType();
	document.positionForm.customFieldFilterValue.value=custonFieldData.getFieldValue();
	customFieldValue = custonFieldData.getFieldValue().replace(/\|/g,',');
	criteriaPane.add(new criteriaOpt('<%=PositionConstants.FILTER_CUSTOM_FIELD%>', customFieldValue));
	criteriaPane.refreshCriteria();
	loadGrid();
}

function copyPosition(){
	var migrationPending = false;
	<logic:present name="migrationPending" scope="request">
		migrationPending = true;
	</logic:present>	

	if(!migrationPending){
	   var id = dataGrid.getSelectedId();   
	   if(id){	    
			    window.location.href=uncache("position.do?mode=copyPosition&positionId="+id + "&showCondition="+<%=PositionConstants.POSITION_STATUS_TEMPLATE%>+"&positionStatus="+<%=PositionConstants.POSITION_STATUS_INPROCESS%>);
	   }else{
	     	alert('<bean:message key="common.please_select" /> <bean:message key="common.template" /> to <bean:message key="positions_home.label.create_position" />');
	     	return false;
	   }
	   return true;
	}else {
		alert('<bean:message key="steps_migration.message.complete_template_migration_process" />');
	}
}

window.onload=doOnLoad;
</script>