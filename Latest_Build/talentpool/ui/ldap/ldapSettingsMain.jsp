<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<%
	String ldapEnabled = (String)request.getAttribute(GlobalConstants.PROPERTY_LDAP_ENABLED);
	ldapEnabled = Utils.isBlankOrNull(ldapEnabled)?"":ldapEnabled;
%>
<script language="JavaScript">
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	switch(obj.cell._cellIndex){
		case 0:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"serverURL");
			break;
	}
	return obj.cell.innerHTML;
}
</script>

<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<div class="contentDiv">
<table cellspacing="0" cellpadding="0">
<tr>
<td>
  <% if("1".equals(ldapEnabled)){ %>
  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
  		onclick="setLDAPEnabled();" />
  <%}else{ %>
  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
  		onclick="setLDAPEnabled();" />
  <%} %>
</td>
<td> &nbsp;
  <bean:message key="admin.ldap_settings.label.ldap_enabled"/>
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
			<td id="monthYear" class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="admin.ldap_settings.label.ldap_server"/></td>
			<td class="rightC"></td>
		</tr>
		</table>
	    </td> 
	  </tr> 
	</table> 
	<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="ldapServerBox"  style="width: 738px;height: 18px;" ></div>
		</td>
	</tr>
</table>
	

</div>
<script language="JavaScript">
function setLDAPEnabled(){
	window.location="ldapHome.do?mode=setLdapEnabled";
}
</script>

<script> 

window.onload = doOnLoad;
var ldapServerBox ;

function doOnLoad(){
	ldapServerBox = new dhtmlXGridObject('ldapServerBox'); 
	ldapServerBox.imgURL = "images/dhtmlxGrid/"; 
	ldapServerBox.setHeader("<bean:message key="admin.ldap_settings.col.label.ldap_server_url"/>,<bean:message key="admin.ldap_settings.col.label.ldap_security_principal"/>"); 
	ldapServerBox.setInitWidths("350,368");
	ldapServerBox.setColAlign("left,left");
	ldapServerBox.setColTypes("link,ro"); 
	ldapServerBox.setColSorting("na,na");
	ldapServerBox.attachEvent("onRowDblClicked",onRowDoubleClick);
	ldapServerBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	ldapServerBox.enableAutoHeigth(true,"400");
	ldapServerBox.init();
	ldapServerBox.loadXML("ldapHome.do?mode=getLdapServerXMLFile");
}

function onRowDoubleClick(id){
	viewDetails();
}

function viewDetails() {
  var returnVal = false;
  var id = ldapServerBox.getSelectedId();
  if (id) {
  	editRecord(id);
  } else {
    alert("<bean:message key="admin.ldap_settings.error.select_server_to_edit"/>");
  }  
  return returnVal;
}    

function editRecord(id){
window.location="ldapHome.do?mode=addLdapServer&serverId="+id;
}
</script>
