<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
				com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.user.UserConstants,com.talentPool.common.NavigationConstants"%>
<link rel="STYLESHEET" type="text/css" href="themes/default/dhtmlXMenu.css">
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script language="JavaScript" src="js/dhtmlxGrid/menu/js/dhtmlXProtobar.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/menu/js/dhtmlXMenuBar.js"></script>
<script language="JavaScript" src="js/dhtmlxGrid/menu/js/dhtmlXMenuBar_cp.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script>
/*
extension of dhtmlXGridCell.js cell for implementing attachment column and
change the css of rows if email from already existing candidate
*/
var _closedPosition = <%=UserConstants.DEACTIVE%>;

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
			<td id="reportLevel_Setting" class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="report_level.label.report_level_setting"/></td>
			<td class="rightC"></td>
		</tr>
		</table>
	    </td> 
	    <td>	
	    	<div class="navBtnTab" style="width:105px;float: right;">
			<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
			<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
			<a href="#" onclick="javascript: showAddNewUserPopup();" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
			</div>   
	    </td> 
	  </tr> 
	</table> 
	<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="userGridBox" style="width:738px; height: 18px;" ></div>
		</td>
	</tr>
</table>
	
</div>
<script> 
window.onload = doOnLoad;
var userGridBox ;

function doOnLoad(){
	initPopUp();	
	userGridBox = new dhtmlXGridObject('userGridBox'); 
	userGridBox.imgURL = "images/dhtmlxGrid/"; 
	userGridBox.setHeader("&nbsp;,<bean:message key="common.LevelName"/>,&nbsp;,&nbsp;,"); 
	userGridBox.setInitWidths("18,210,240,250,0");
	userGridBox.setColAlign("left,left,left,left,left");
	userGridBox.setColTypes("ro,ro,ro,ro,estat"); 
	userGridBox.setColSorting("na,cstr,cstr,cstr,cstr");
	userGridBox.attachEvent("onRowDblClicked",onRowDoubleClick);
	userGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	userGridBox.enableAutoHeigth(true,"400");
	userGridBox.init();
 	userGridBox.loadXML("adminHome.do?mode=getReportLevelXMLFile");
}

function onRowDoubleClick(id){
	viewDetails();
}

function performDelete(id) {
 	var levelName = userGridBox.getUserData(id,"levelName");
	//if(confirm("You are about to delete "+levelName+". Continue?")){
	if(confirm('<bean:message key="report_level.label.about_to_delete"/> '+levelName+' <bean:message key="common.continue?"/> ')){
	
		var pars = "mode=deleteLevel&levelId="+id;
		var myAjax = ajaxCall("adminHome.do",'get',pars,onDeleteResponse, reportError);
  	}
}   
 
function onDeleteResponse(request){
 	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="report_level.errors.cannot_delete"/>');
		return;
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		userGridBox.deleteRow(deletedIds[I]);
	}
	userGridBox.clearSelection();  
}

function viewDetails() {
  var returnVal = false;
  var id = userGridBox.getSelectedId();
  if (id) {
  	showPopWin("adminHome.do?mode=addLevel&levelId="+id, "650", "500", reloadGrid,true);
  	  } else {
    //alert("Please select a Level to view Level details.");
    alert('<bean:message key="report_level.errors.level_view_details"/>');
  }  
  return returnVal;
}    

function showAddNewUserPopup() {
	showPopWin("adminHome.do?mode=addLevel", "650", "500", reloadGrid,true);
}

function reloadGrid() {
	userGridBox.clearAll();
	userGridBox.loadXML("adminHome.do?mode=getReportLevelXMLFile");
	
}
</script>
