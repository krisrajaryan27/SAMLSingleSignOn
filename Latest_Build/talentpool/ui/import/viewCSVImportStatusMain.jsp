<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.export.ExportConstants"%>
<%@page import="com.talentPool.porting.dataobject.FailedStatusObject"%>

<%@page import="java.util.ArrayList"%><script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script src="js/dhtmlwindow/dhtmlwindow.js"></script>
<script src="js/dhtmlwindow/modal.js"></script>
<script>
function findRedirectUrl(){
	var entityType = document.importForm.entityType.value;
	var url = '';
	if('<%=ExportConstants.ENTITY_BRANCHES%>'==entityType) {
		url = "masters.do?mode=manageBranchesMaster";
	} else if('<%=ExportConstants.ENTITY_DEGREES%>'==entityType) {
		url = "masters.do?mode=manageDegreeAliasesMaster";
	} else if('<%=ExportConstants.ENTITY_INSTITUTES%>'==entityType) {
		url = "masters.do?mode=manageInstitutesMaster";
	} else if('<%=ExportConstants.ENTITY_LOCATIONS%>'==entityType) {
		url = "location.do?mode=manageLocationsMaster";
	} else if('<%=ExportConstants.ENTITY_SKILLS%>'==entityType) {
		url = "masters.do?mode=manageSkillCategoriesMaster";
	} else if('<%=ExportConstants.ENTITY_SOURCES%>'==entityType) {
		url = "masters.do?mode=manageSourceTypesMaster";
	}  else if('<%=ExportConstants.ENTITY_USERS%>'==entityType) {
		url = "adminHome.do?mode=manageUsers";
	}
	return url;
}
</script>    
<html:form action="/import" onsubmit="submitForm();return false;" >
  	<html:hidden property="entityType" name="importForm"/>
	<html:hidden property="failedObjects" name="importForm"/>
	<div class="contentDiv">
	<table  cellspacing="0" width="100%" style="margin-bottom: 3px;">
		<tr>
			<td>
				<strong id="msg">Import Completed Successfully.</strong>
			</td>			
		</tr>
	</table>
	<br/>
	<div class="outerDiv">
	<table cellpadding="0" cellspacing="0"  width="100%"  class="todoBoxHeader" >	
			<tr >
				<td style="width:10px;"><div class="hdrcell"></div></td>
				<td style="width:200px;"><div class="hdrcell">Name</div></td>
				<td style="width:200px;"><div class="hdrcell">Row Number</div></td>
				<td style="width:400px;"><div class="hdrcell">Status</div></td>
			</tr>
			</table>
			<table cellpadding="0" cellspacing="0"  width="100%"  class="posinput">
			<logic:iterate id="failedObject" name="failedObjects" type="FailedStatusObject" indexId="counter" scope="request">
				<tr style="">
					<td style="width:10px;" class="row2"></td>
					<td style="width:200px;"  class="row2"><bean:write name='failedObject' property='name'/></td>
					<td style="width:200px;" class="row2"><bean:write name='failedObject' property='rowNumber'/></td>
					<td style="width:400px;" class="row2"><bean:write name="failedObject" property="errorMessage" filter="false"/></td>					
				</tr>
			</logic:iterate>
			
	</table>
	</div>
	<br/>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" > 
		<tr> 
			<td>						
	   			<div id="divImportAllButton" class="navBtn" style="margin-top:5px;float: right;">
					<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="finishImport();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.ok"/> </a>
				</div>
			</td>
		</tr>
	</table>
</div>
</html:form>
<script language="javascript">

function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function finishImport(){
	window.location.href=findRedirectUrl();
}

function onWindowLoad(){

}
window.onload=onWindowLoad;
</script>

