<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.positions.PositionConstants"%>
<%@ page import="com.talentPool.user.manager.ModuleSet"%>
<%@ page import="com.talentPool.documents.utils.DocumentUtils"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.common.properties.GlobalApplicationProperties"%>


<%@page import="com.talentPool.positions.constants.PositionDraftConstants"%><link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script src="js/cookies.js"></script>

<div class="contentDiv">
<html:form action="/drafts">
<html:hidden property="mode" />
<html:hidden property="t" name="positionDraftForm"/>
<html:hidden property="draftId" name="positionDraftForm"/>
<html:hidden property="filePath" name="positionDraftForm"/>
<% 				
	boolean addPosition=false; 
	boolean addPositionTemplate = false;
%>
<logic:equal value="true" name="canAddRequisition" scope="request" >
	<% addPosition=true;  %>
</logic:equal>		
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CREATE_POSITION_TEMPLATE">
	<% addPositionTemplate = true; %>
</logic:equal>
				
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<table border="0" cellspacing="0" cellpadding="0" align="right"> 
				
					<tr>
						<td>
					   		<div id="positions_1" style="margin:0px; padding: 0px;">
						   		&nbsp;<a href="#" onclick="loadOther(1);" class="green"><bean:message key="common.positions"/></a>
					   		</div>	
					   	</td>
					   	<td >&nbsp;|&nbsp;</td>
				 		<td >
					   		<div id="drafts_0" style="margin:0px; padding: 0px;">
						   		<bean:message key="common.drafts"/>
					   		</div>
				  	 	</td>
				  	 	<% if(addPosition || addPositionTemplate){%>
				  	 	<td>&nbsp;|&nbsp;</td>
			  	 		<td>
				   		<div id="templates_1" style="display: block;">
					   		<a href="#" onclick="loadOther(2);" class="green"><bean:message key="common.templates"/></a>
			   			</div>	
			  	 	</td>
			  	 	<%} %>
					</tr>
				</table>
			</td>
		</tr>	
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
			<td>
				<% String width = "220px"; %>
				<logic:equal value="true" name="canAddRequisition" scope="request" >
					<% width = "300px"; %>
				</logic:equal>
				<div class="navBtnTab" style="width:<%=width%>;float: left;">
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  						 
				  	<a href="#" onclick="javascript: changeStatus();" style="width:110px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="position.home.change_status"/></a>
				  	<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
				  	<a href="#" onclick="javascript: renameDraft();" style="width:70px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="position.draft.rename"/></a>				  	
					<logic:equal value="true" name="canAddRequisition" scope="request" >
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
					<a href="#" onclick="javascript: loadPositionDraft();" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="position.draft.load_draft"/></a> 
					</logic:equal>	
				</div>
			</td>
	</tr>
</table>

<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id='<bean:message key="positions.home.id.datagrid" />' style="width:736px;height:228px;"></div>
		</td>
	</tr>
</table>
</html:form>
</div>

<script language="javascript">

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
				return obj.grid.getUserData(obj.cell.parentNode.idd,"fileName");				
				break;
			case 3:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"fullName");				
				break;
			case 4:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"dateCreated");				
				break;
			case 5:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"visibilityType");				
				break;
		}	
	}
	return obj.cell.innerHTML;
}

function loadOther(choice){
	if(choice == 1){
		window.location=uncache("position.do?mode=positionsHome");
	}else{
		window.location=uncache("positionTemplate.do?mode=positionTemplatesHome");
	}
}

function uncache(url){
	var d = new Date();
	var time = d.getTime();
	return url + '&ta='+time;
} 

function initGrid(){	
   	dataGrid = new dhtmlXGridObject('<bean:message key="positions.home.id.datagrid" />'); 
   	dataGrid.imgURL = "images/"; 
   	dataGrid.setHeader('&nbsp;,&nbsp;,<bean:message key="position.draft.home.draft_name" />,<bean:message key="position.draft.home.created_by" />,<bean:message key="position.draft.home.created_on" />,<bean:message key="position.draft.home.draft_type" />'); 
   	
   	dataGrid.setInitWidths("5,20,400,120,80,80");
   	dataGrid.setColAlign("left,left,left,left,left,left");
   	dataGrid.setColSorting("na,na,custom_draft_name_sort,str,custom_date_sort,str");
   	dataGrid.setColTypes("ro,ro,link,ro,ro,ro"); 
	dataGrid.enableAutoHeigth(true,"290");  	
   	dataGrid.init(); 	   	
   	dataGrid.enableSmartRendering(true);	   	
   	loadGrid();
}

function custom_draft_name_sort(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"fileName");
	b0 = dataGrid.getUserData(bId,"fileName");	
	return sort_data(a0,b0,order);
}

function custom_date_sort(a,b,order){
	var n=getConvertedDate(a);
	var m=getConvertedDate(b);
	if(order=="asc")
		return n>m?1:-1;
	else
		return n<m?1:-1;
}

function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function getConvertedDate(a){
	var dt1=new Date();
	if(a.length>0){
		dt1 = new Date(a.substring(0,2) + ' ' + a.substring(3,6) + ' 20' + a.substring(7,9)); 		
	}
	return dt1;		
}

function loadGrid(){
	dataGrid.loadXML("drafts.do?mode=xmlForPositionDrafts");
}

function onClickPositionDraft(draftFilePath){
	var url = "<%=DocumentUtils.getDocumentURL("$fileName","" )%>";
	url = url.replace("$fileName",draftFilePath);
	window.open(url);
}

function onClickDeleteDraft(draftId){
	var retVal = confirm('<bean:message key="common.confirm.delete"/> ?');
	if(retVal==true){
		var pars = 'mode=deletePositionDraft&draftId='+draftId;
		var myAjax = ajaxCall("drafts.do","get",pars,onCompleteDeleteDraft,reportError);
	}
}

function onCompleteDeleteDraft(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		initGrid();
	}else{
		alert('<bean:message key="position.draft.error.delete_draft" />');
	}
	return false;
}

function loadPositionDraft(){
	var id = dataGrid.getSelectedId();
	if(!id){
	   	alert('<bean:message key="position.draft.message.please_select_draft_to_load" />');
	   	return;
	}
	var url = 'position.do?mode=loadDraft&draftId=' + id;
	window.location.href=url;
}

function renameDraft(){
	var id = dataGrid.getSelectedId();
	if(!id){
	   	alert('<bean:message key="position.draft.message.please_select_draft" />');
	   	return;
	}
	var draftOwner = dataGrid.getUserData(id,"draftOwner");
	if(draftOwner=="1"){
		var url="drafts.do?mode=showDraftName&draftId="+id;
		window.setTimeout("showInPopUp('"+url+"',500,250,initGrid,true);",10);
	}else{
		alert('<bean:message key="position.draft.message.not_allowed_to_rename_draft" />');
		return;
	}
}

function changeStatus(){
	var id = dataGrid.getSelectedId();
	if(!id){
	   	alert('<bean:message key="position.draft.message.please_select_draft" />');
	   	return;
	}

	//if draft is shared don't allow to make it as Private
	var draftType = dataGrid.getUserData(id,"draftType");
	if(draftType=='<%=PositionDraftConstants.POSITION_DRAFT_STATUS_SHARED%>'){
		alert('<bean:message key="position.draft.message.status_can_not_be_changed" />');
		return
	}

	var fileName = dataGrid.getUserData(id,"fileName");
	var re = new RegExp('\\b'+'<bean:message key="common.position_draft" />'+'\\b \\b\\d{1,2}\\b');
	if (fileName.match(re)) {
	  alert('<bean:message key="position.draft.error.change_status" />');
	  return false;		  
	}
		
	//only owner or administartor can change status
	var draftOwner = dataGrid.getUserData(id,"draftOwner");
	if(draftOwner=="1"){
		var url="drafts.do?mode=showDraftStatus&draftId="+id;
		window.setTimeout("showInPopUp('"+url+"',500,150,initGrid,true);",10);
	}else{
		alert('<bean:message key="position.draft.message.not_allowed_to_change_status" />');
		return;
	}
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function doOnLoad(){
	initPopUp();
	initGrid();
}
window.onload=doOnLoad;
</script>