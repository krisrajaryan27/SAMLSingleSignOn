<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
				com.talentPool.common.properties.TPApplicationProperties,
				com.talentPool.custom.constants.CustomFieldConstants"%>
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript">
eXcell_link.prototype.getTitle=function(){
	return getCustomTitle(this);
}

function getCustomTitle(obj){
	switch(obj.cell._cellIndex){
	case 1:
		return obj.grid.getUserData(obj.cell.parentNode.idd,"displayName");
		break;	
	}
	//if no special tooltip - return current value
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
	    <td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;">Candidate Related Custom Fields</td>
			<td class="rightC"></td>
		</tr>
		</table>
	    </td> 
	    <td>	
	    	<div class="navBtnTab" style="width:105px;float: right;">
			<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
			<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
			<a href="#" onclick="javascript: showAddNewPopup('<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)%>');" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
			</div>   
	    </td> 
	  </tr> 
	</table> 
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="applicantGridBox"  style="width: 738px;height: 18px;" ></div>
			</td>
		</tr>
	</table>
	<br/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			 <td  valign="bottom">
			 	<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
					<tr>
						<td class="leftC"></td>
						<td class="content Grey" style="padding-left:10px; padding-right:10px;">Candidate Related Custom Tables</td>
						<td class="rightC"></td>
					</tr>
				</table>
			 </td>
			  <td>	
		    	<div class="navBtnTab" style="width:105px;float: right;">
				<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
				<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
				<a href="#" onclick="javascript: showAddNewPopup('<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE)%>');" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
				</div>   
		    </td> 
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="applicantTablesGridBox"  style="width: 738px;height: 18px;" ></div>
			</td>
		</tr>
	</table>
	<br/>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="content Grey">
				<a href="#" onclick="javascript: showCustomFieldMappingPopup('<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)%>');" style="width:80px;">Candidate Custom Fields for Summary Reports</a>
			</td>
		</tr>
	</table> 
	
	<br/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;">Position Related Custom Fields</td>
			<td class="rightC"></td>
		</tr>
		</table>
	    </td> 
	    <td>	
	    	<div class="navBtnTab" style="width:105px;float: right;">
			<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
			<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
			<a href="#" onclick="javascript: showAddNewPopup('<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_POSITION)%>');" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
			</div>   
	    </td> 
	  </tr> 
	</table> 
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="positionGridBox"  style="width: 738px;height: 18px;" ></div>
			</td>
		</tr>
	</table>
	<br/>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="content Grey">
				<a href="#" onclick="javascript: showCustomFieldMappingPopup('<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_POSITION)%>');" style="width:80px;">Position Custom Fields for Summary Reports</a>
			</td>
		</tr>
	</table>
	
</div>
<br/>
<br/>
<br/>

<script> 
window.onload = doOnLoad;
var applicantGridBox,positionGridBox,applicantTablesGridBox;

function doOnLoad(){
	initPopUp();	
	
	applicantGridBox = new dhtmlXGridObject('applicantGridBox'); 
	applicantGridBox.imgURL = "images/dhtmlxGrid/"; 
	applicantGridBox.setHeader("&nbsp;,<bean:message key='admin.custom_fields.label.field_name'/>,<bean:message key='admin.custom_fields.label.field_type'/>"); 
	applicantGridBox.setInitWidths("18,350,350");
	applicantGridBox.setColAlign("left,left,left");
	applicantGridBox.setColTypes("ro,link,ro"); 
	applicantGridBox.setColSorting("na,cstr,cstr");
	applicantGridBox.attachEvent("onRowDblClicked",onRowDoubleClickA);
	applicantGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	applicantGridBox.enableAutoHeigth(true,"400");
	applicantGridBox.init();
	applicantGridBox.loadXML("customFieldScreen.do?mode=getCustomFieldsXML&entityType=" + '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)+","+CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD%>');
	
	positionGridBox = new dhtmlXGridObject('positionGridBox'); 
	positionGridBox.imgURL = "images/dhtmlxGrid/"; 
	positionGridBox.setHeader("&nbsp;,<bean:message key='admin.custom_fields.label.field_name'/>,<bean:message key='admin.custom_fields.label.field_type'/>"); 
	positionGridBox.setInitWidths("18,350,350");
	positionGridBox.setColAlign("left,left,left");
	positionGridBox.setColTypes("ro,link,ro"); 
	positionGridBox.setColSorting("na,cstr,cstr");
	positionGridBox.attachEvent("onRowDblClicked",onRowDoubleClickP);
	positionGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	positionGridBox.enableAutoHeigth(true,"400");
	positionGridBox.init();
	positionGridBox.loadXML("customFieldScreen.do?mode=getCustomFieldsXML&entityType=" + '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_POSITION)%>');
	
	applicantTablesGridBox = new dhtmlXGridObject('applicantTablesGridBox'); 
	applicantTablesGridBox.imgURL = "images/dhtmlxGrid/"; 
	applicantTablesGridBox.setHeader("&nbsp;,Applicant Custom Field Table"); 
	applicantTablesGridBox.setInitWidths("18,700");
	applicantTablesGridBox.setColAlign("left,left");
	applicantTablesGridBox.setColTypes("ro,link"); 
	applicantTablesGridBox.setColSorting("na,cstr");
	applicantTablesGridBox.attachEvent("onRowDblClicked",onRowDoubleClickAT);
	applicantTablesGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	applicantTablesGridBox.enableAutoHeigth(true,"400");
	applicantTablesGridBox.init();
	applicantTablesGridBox.loadXML("customFieldScreen.do?mode=getCustomTabularFieldsXML&entityType="+'<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)%>');
}
var customFldEntityType;
var customFldId;
function deleteField(fldId, fldEntityType) {
	customFldEntityType = fldEntityType;
	customFldId = fldId;
	var fldName = '';
	if(fldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)%>') {
		fldName = applicantGridBox.getUserData(fldId,"displayName");		
	} else if(fldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_POSITION)%>') {
		fldName = positionGridBox.getUserData(fldId,"displayName");	
	}else if(fldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)%>'){
		fldName = applicantGridBox.getUserData(fldId,"displayName");	
	}else if(fldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE)%>'){
		fldName = applicantTablesGridBox.getUserData(fldId,"displayName");	
	}	
	retVal = confirm("You are about to delete custom field "+fldName+". Continue?");
	if(retVal){
		var pars = "mode=deleteCustomField&customFieldId="+fldId+"&entityType="+fldEntityType;
		var myAjax = ajaxCall("customFieldScreen.do",'get',pars,onDeleteResponse, reportError);
  	}
}

function onDeleteResponse(request){
 	xmlFile = request.responseXML; 	
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="admin.custom_fields.error.delete"/>');
		return;
	} 
	if(customFldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)%>') {
		applicantGridBox.deleteRow(customFldId);
		applicantGridBox.clearSelection();  
	} else if(customFldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_POSITION)%>') {
		positionGridBox.deleteRow(customFldId);
		positionGridBox.clearSelection();  
	}else if(customFldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)%>'){
		applicantGridBox.deleteRow(customFldId);
		applicantGridBox.clearSelection();  
	}else if(customFldEntityType == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE)%>'){
		applicantTablesGridBox.deleteRow(customFldId);
		applicantTablesGridBox.clearSelection();  
	}
}

function onRowDoubleClickA(id){
	viewDetails(id, '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)%>');
}

function onRowDoubleClickP(id){
	viewDetails(id, '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_POSITION)%>');
}

function onRowDoubleClickAT(id){
	viewDetails(id,'<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE)%>');
}

function viewDetails(id, entityType) {
  showPopWin("customFieldScreen.do?mode=addCustomField&entityType="+entityType+"&customFieldId="+id, "650", "500", reloadGrid,true);
}    

function showAddNewPopup(entityType) {
	showPopWin("customFieldScreen.do?mode=addCustomField&entityType="+entityType, "650", "500", reloadGrid,true);
}

function reloadGrid(returnVal) {
	retVal = returnVal;
	if((retVal == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT)%>') || (retVal == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)%>')) {
		retVal = '<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT+","+CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD%>';
		applicantGridBox.clearAll();
		applicantGridBox.loadXML("customFieldScreen.do?mode=getCustomFieldsXML&entityType="+retVal);
	} else if(retVal == '<%=String.valueOf(CustomFieldConstants.ENTITY_TYPE_POSITION)%>') {
		positionGridBox.clearAll();
		positionGridBox.loadXML("customFieldScreen.do?mode=getCustomFieldsXML&entityType="+retVal);
	}
}

function showCustomFieldMappingPopup(entityType) {
	showPopWin("customFieldScreen.do?mode=customFieldMapping&entityType="+entityType, "650", "520", reloadGrid,true);
}

</script>
