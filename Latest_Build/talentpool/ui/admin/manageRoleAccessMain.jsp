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
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="userGridBox"  style="width: 738px;height: 18px;" ></div>
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
	userGridBox.setHeader("<bean:message key="admin.manage_roles.label.title.manage_roles"/>,&nbsp;"); 
	userGridBox.setInitWidths("700,18");
	userGridBox.setColAlign("left,left");
	userGridBox.setColTypes("ro,ro"); 
	userGridBox.setColSorting("na,cstr");
	userGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	userGridBox.enableAutoHeigth(true,"500");
	userGridBox.init();
   userGridBox.loadXML("adminHome.do?mode=getRoleXMLFile");
}

function onRowDoubleClick(id){
	//viewDetails(id);
}

function viewDetails(id) {
  var returnVal = false;
 // var id = userGridBox.getSelectedId();
  var roleTitle=userGridBox.getUserData(id,"roleTitle");
  if (id) {
	showPopWin("adminHome.do?mode=roleAccessSettings&roleId="+id+"&roleTitle="+roleTitle, "550", "470", reloadGrid,true);
  }   
}    

function reloadGrid() {
	userGridBox.clearAll();
	userGridBox.loadXML("adminHome.do?mode=getRoleXMLFile");
	
}
</script>
