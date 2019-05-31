<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
				com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.user.UserConstants,com.talentPool.common.NavigationConstants"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.export.ExportConstants"%>
<%@page import="java.util.UUID"%>
<link rel="STYLESHEET" type="text/css" href="themes/default/dhtmlXMenu.css">
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript">
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	switch(obj.cell._cellIndex){
		case 0:
			return '<bean:message key="common.delete"/>';
			break;
		case 1:
			return '<bean:message key="common.Advance_permissions"/>';
			break;	
	}
	return unescapeHTML(obj.cell.innerHTML);
}
/*
extension of dhtmlXGridCell.js cell for implementing attachment column and
change the css of rows if email from already existing candidate
*/
var _closedPosition = <%=UserConstants.DEACTIVE%>;
var selectBoxRole = null;

function eXcell_estat(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 }
}
eXcell_estat.prototype = new eXcell;
eXcell_estat.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	if(val=="<%=UserConstants.DEACTIVE%>"){
	 this.cell.parentNode.className='disabledrow';
	}
}

var sort_img_ids = new Array();
sort_img_ids[sort_img_ids.length] = 'sort_img_0';
sort_img_ids[sort_img_ids.length] = 'sort_img_1';
sort_img_ids[sort_img_ids.length] = 'sort_img_2';

<% 
String tokenId = (String)request.getSession().getAttribute("changeUserStatusTokenId");
%>

</script>
<html:form action="/import">
<html:hidden property="mode"/>
<html:hidden property="filePath" name="importForm"/>
<html:hidden property="entityType" name="importForm"/>
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
			<td id="monthYear" class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="admin_manage_users"/></td>
			<td class="rightC"></td>
		</tr>
		</table>
	    </td> 
	    <td>	
	    	<div class="navBtnTab" style="width:395px;float: right;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  						 
				  <a href="#" onclick="javascript: showAddNewUserPopup();" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
				  <img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
				  <a href="#" onclick="javascript: performEnableDisable();" style="width:120px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.enable"/>/<bean:message key="common.disable"/></a> 
				  <img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
				  <a href="#" onclick="javascript: changePassword();" style="width:135px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="user_manager.label.change_password"/></a> 
				  <a href="#" style="text-align:right;background:none;padding-left: 10px;" onmouseover="changeImage('importFromExl', 'images/ico_csv_import_co.gif')" onmouseout="changeImage('importFromExl', 'images/ico_csv_import_bw.gif')">
				<img src="images/ico_csv_import_bw.gif" width="16" height="16" style="border:0px;" id="importFromExl" onclick="javascript:importFromExcel(); return false;" title="<bean:message key="common.import"/>"/>
		</a>  
				</div>   
	    </td> 
	  </tr> 
	</table> 
	<table class="boxHeader" cellspacing="0" cellpadding="0" border="0" style="border-bottom:0px;">
	  		<tr style="height: 18px;">		
	  			<td class="head" style="width:14px;cursor:default;">
	  				&nbsp;
	  			</td>		
	  			<td class="head" style="width:14px;cursor:default;">
	  				&nbsp;
	  			</td>
	  			<td class="head" style="width:185px; " onclick="javascript: sortGridRows(userGridBox, 2, sort_img_ids);">
	  				<bean:message key="common.username" />&nbsp;&nbsp;&nbsp;&nbsp;
	  				<span id="sort_img_0"></span>
	  			</td>
	  			<td class="head" style="width:210px;" onclick="javascript: sortGridRows(userGridBox, 3, sort_img_ids);">
	  				<bean:message key="common.user" />&nbsp;&nbsp;&nbsp;&nbsp;
	  				<span id="sort_img_1"></span>
	  			</td>
	  			<td class="head" style="width:130px;" onclick="javascript: sortGridRows(userGridBox, 4, sort_img_ids);">
	  				Empl. Code&nbsp;&nbsp;&nbsp;&nbsp;
	  				<span id="sort_img_2"></span>
	  			</td>
	  			<td class="head" style="width:200px;" onclick="javascript: sortGridRows(userGridBox, 5, sort_img_ids);">
	  				<bean:message key="common.roles" />&nbsp;&nbsp;&nbsp;&nbsp;
	  				<span id="sort_img_3"></span>
	  			</td>
	  		</tr>				  		

	  		<tr>				
	  			<td class="head" style="width:14px;cursor:default;">
	  				&nbsp;
	  			</td>  		
	  			<td class="head" style="width:14px;cursor:default;">
	  				&nbsp;
	  			</td>
	  			<td class="head" style="width:185px;cursor:default;">
	  				<input type="text" id="nameFilter" name="nameFilter" style="width:120px;" value="Search by Username" onKeyUp="applyFilters();" onFocus="document.getElementById('nameFilter').value=''"/>
	  			</td>
	  			<td class="head" style="width:210px;cursor:default;">
	  				<input type="text" id="userFilter" name="userFilter" style="width:120px;" value="Search by User" onKeyUp="applyFilters();" onFocus="document.getElementById('userFilter').value=''"/>
	  			</td>
	  			<td class="head" style="width:130px;cursor:default;">
	  				<input type="text" id="empCodeFilter" name="empCodeFilter" style="width:80px;" value="<bean:message  key="admin_manage_users.label.search_by_emp_code"/>" onKeyUp="applyFilters();" onFocus="document.getElementById('empCodeFilter').value=''"/>
	  			</td>
	  			<td  class="head" style="width:200px;cursor:default;">						  				
					<script>
						var opts = <%=request.getAttribute("jsArrayRole")%>;
						var n = [new SelectOption('<%=UserConstants.ROLE_ALL_EXCEPT_EMPLOYEE%>','<bean:message key="common.selectlist.all_except_employees"/>')];
						var m = [new SelectOption('-1','<bean:message key="common.selectlist.all"/>')];
						opts = n.concat(opts);
						opts = m.concat(opts);
						selectBoxRole = new SelectBox(opts,'<%=UserConstants.ROLE_ALL_EXCEPT_EMPLOYEE%>','images/btn_dropdown.gif',{namesonly:false, width:'150px', size:20});
						selectBoxRole.setOnChangeHandler('applyFilters');
						document.write(selectBoxRole.getHtml());
						selectBoxRole.init();
					</script>
	  			</td>
	  		</tr>
	  		<tr>
	  		<td style="height:7px;" colspan="5"></td>
	  		</tr>
	  	</table>
  	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="userGridBox" style="width:738px;height: 380px;"></div>
			</td>
		</tr>
	</table>
<br/>
<br/>
</div>
</html:form>
<script> 
var userGridBox ;
var maxHeight=380;

function doOnLoad(){
	initPopUp();
	userGridBox = new dhtmlXGridObject('userGridBox'); 
	userGridBox.imgURL = "images/dhtmlxGrid/"; 
	userGridBox.setHeader("&nbsp;,&nbsp;,<bean:message key="common.username"/>,<bean:message key="common.user"/>,<bean:message key="common.emp_code"/>,<bean:message key="common.roles"/>,"); 
	userGridBox.setInitWidths("18,24,180,200,120,175,0");
	userGridBox.setColAlign("left,left,left,left,left,left,left");
	userGridBox.setColTypes("ro,ro,ro,ro,ro,ro,estat"); 
	userGridBox.setColSorting("na,na,cstr,cstr,cstr,cstr,cstr");
	userGridBox.attachEvent("onRowDblClicked",onRowDoubleClick);
	userGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	userGridBox.setNoHeader(true);
	userGridBox.init();
	applyFilters();
	//userGridBox.setSortImgState(true,2,"ASC");
	document.getElementById('sort_img_0').innerHTML='<img src="images/sort_asc.gif" />';
}

function onRowDoubleClick(id){
	viewDetails();
}
var roleId = "";
var nFilter ="";
var userFilter="";
var empCodeFilter="";
function applyFilters(){
	roleId = selectBoxRole.getSelectedId();
	if (roleId == -1) {
		roleId="";
	} 

	nFilter = document.getElementById("nameFilter").value;
	//if (nFilter == '' || nFilter == 'Search by Username') {
	if (nFilter == '' || nFilter == '<bean:message key="admin_manage_users.label.search_by_username"/>') {
		nFilter = "";
	}
	
	userFilter = document.getElementById("userFilter").value;
	//if (userFilter == '' || userFilter == 'Search by User') {
	if (userFilter == '' || userFilter == '<bean:message key="admin_manage_users.label.search_by_user"/>') {
		userFilter = "";
	}

	empCodeFilter = document.getElementById("empCodeFilter").value;
	if (empCodeFilter == '' || empCodeFilter == '<bean:message key="admin_manage_users.label.search_by_emp_code"/>') {
		empCodeFilter = "";
	}
	
	userGridBox.clearAll();
	userGridBox.loadXML("adminHome.do?mode=getUserXMLFile&filterByName=" + nFilter + "&filterByRole=" + roleId+"&filterByUser="+userFilter+"&filterByEmpCode="+empCodeFilter);
	userGridBox.clearSelection();
}

function performDelete(id,roleId,userSrcId) {
	var deleteUserTokenId = '<%=tokenId%>';
	var userName = userGridBox.getUserData(id,"userName");
	if(confirm("You are about to delete "+userName+". Continue?")){
		var pars = "mode=deleteUser&userId="+id+"&roleId="+roleId+"&userSourceId="+userSrcId+"&changeUserStatusTokenId="+deleteUserTokenId;
		var myAjax = ajaxCall("adminHome.do",'get',pars,onDeleteResponse, reportError);
  	}
}   

function AdvancePermissions(id){
	var userName = userGridBox.getUserData(id,"userName");
	if(id){
		showPopWin("adminHome.do?mode=roleAccessSettings&userId="+id+"&userName="+userName, "650", "500", reloadGrid,true);
	}else{
		//alert("Please select a user.");
		alert('<bean:message key="admin_manage_users.label.select_a_user"/>');
	}
}
 
function onDeleteResponse(request){
 	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="user_manager.errors.cannot_delete"/> <bean:message key="common.positions"/>.');
		return;
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		userGridBox.deleteRow(deletedIds[I]);
	}
	userGridBox.clearSelection();  
}

function performEnableDisable() {
  var returnVal = false;
  var changeUserStatusTokenId = '<%=tokenId%>';
  var id = userGridBox.getSelectedId();
  if (id) {
  	var userStat = userGridBox.getUserData(id,"status");
    window.open(uncache('adminHome.do?mode=changeStatus&userId='+id+'&userStatus='+userStat+"&changeUserStatusTokenId="+changeUserStatusTokenId+'&t=<bean:write name="adminForm" property="t"/>&st=<bean:write name="adminForm" property="st"/>'),'_self');
    returnVal = true;
  } else {
    //alert("Please select a user to enable / disable.");
    alert('<bean:message key="admin_manage_users.label.select_user_to_enable_disable"/>');
  }  
  return returnVal;
}    

function viewDetails() {
  var returnVal = false;
  var id = userGridBox.getSelectedId();
  if (id) {
  	showPopWin("adminHome.do?mode=addUser&userId="+id, "650", "500", reloadGrid,true);
  	//window.open('adminHome.do?mode=addUser&t=<bean:write name="adminForm" property="t"/>&st=<bean:write name="adminForm" property="st"/>&userId='+id,'_self');
    //returnVal = true;
  } else {
    //alert("Please select a user to view details.");
    alert('<bean:message key="admin_manage_users.label.select_user_to_view_details"/>');
  }  
  return returnVal;
}    

function changePassword() {
  var returnVal = false;
  var id = userGridBox.getSelectedId();
  if (id) {
  	showPopWin("user.do?mode=changePassword&userId="+id, "550", "250", null,true);
  } else {
    //alert("Please select a user.");
    alert('<bean:message key="admin_manage_users.label.select_a_user"/>');
  }  
  return returnVal;
}  

function sortGridRows(gridObj, column, elemIds) {
	var type = gridObj.getSortingState();
		if (type[0] == column) {
			if (type[1] == 'asc') {
				gridObj.sortRows(column, "cstr", "desc");
				gridObj.setSortImgState(true,column,"desc");
			} else {
				gridObj.sortRows(column, "cstr", "asc");
				gridObj.setSortImgState(true,column,"asc");
			}
			
			for (var i = 0; i < elemIds.length; i++) {
				if (i == (column - 2)) {
					if (type[1] == 'asc') {
						document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_desc.gif" />';
					} else {
						document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_asc.gif" />';
					}				
				} else {
					document.getElementById(elemIds[i]).innerHTML='';
				}
			}
		} else {
			gridObj.sortRows(column, "cstr", "asc");
			gridObj.setSortImgState(true,column,"asc");
			for (var i = 0; i < elemIds.length; i++) {
				if (i == (column - 2)) {
					document.getElementById(elemIds[i]).innerHTML='<img src="images/sort_asc.gif" />';			
				} else {
					document.getElementById(elemIds[i]).innerHTML='';
				}
			}
		}
}

function showAddNewUserPopup() {
	showPopWin("adminHome.do?mode=addUser", "650", "530", reloadGrid,true);
}

function reloadGrid() {
	userGridBox.clearAll();
	applyFilters();
	userGridBox.loadXML("adminHome.do?mode=getUserXMLFile&filterByName=" + nFilter + "&filterByRole=" + roleId+"&filterByUser="+userFilter);
	
}

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function importFromExcel(){
	var url = "import.do?mode=importMasters&importEntityType=<%= ExportConstants.ENTITY_USERS%>";
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
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
	document.importForm.entityType.value='<%= ExportConstants.ENTITY_USERS%>';
	document.importForm.mode.value = "showMasterCSVFieldMappings";
	document.importForm.submit();
}

window.onload = doOnLoad;
</script>
