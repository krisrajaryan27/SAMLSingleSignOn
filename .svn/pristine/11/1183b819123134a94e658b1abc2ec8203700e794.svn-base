<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.applicant.dataobject.ImportFieldData"%>
<%@page import="com.talentPool.export.ExportConstants"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/cookies.js"></script> 
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

<html:form action="/import" onsubmit="submitForm();return false;">
  	<html:hidden property="mode" name="importForm"/>
  	<html:hidden property="filePath" name="importForm"/>
  	<html:hidden property="sessionId" name="importForm"/>
	<html:hidden property="mappings" name="importForm"/>
	<html:hidden property="entityType" name="importForm"/>	
<div class="contentDiv">
	<div id="divError" style="display:block">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
	<script>
		var isError=1;
	</script>
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
	<br/>
	<% } %>
	</div>
</div>
<div class="contentDivPop" style="padding-right:20px;" >
	<strong>Map the following column from excel file</strong>
	<br/><br/>
	<table width="100%" class="boxHeader" cellspacing="2" cellpading="2">
		<tr>
			<td class="header" height="18" style="width:300px;" ><b>Import file columns</b></td>
			<td style="width:300px;"><b>Fields in application</b></td>					
		</tr>
	</table>
	<div class="outerDiv" style="padding:0px 0px 0px 0px;border-top:none;">
		<table cellspacing="2" cellpadding="5" class="boxContent" style="border:0px;width: 100%">			   
			<% int ctlNo=0;%>
			<script>
			var arrVals = null;
			var vals = readCookie('<bean:write name="importForm" property="entityType"/>');
			if(vals!='' && vals!=null){
				arrVals = vals.split(',');				
			}
			</script>
			<logic:iterate id="names" name="excelFieldName" scope="request">
				<tr>
				  <td style="width:300px;"><bean:write name="names" /></td>
				  <td style="width:300px;">
					<script type="text/javascript">
						var opts = new Array();
						var opts = <bean:write name="importForm" property="jsArrayMasterFields" filter="false"/>;							
						if(arrVals!= null){
							col_<%=ctlNo%> = new SelectBox(opts,arrVals[<%= ctlNo%>],'images/btn_dropdown.gif',{namesonly:false, width:'160px', size:10, controlname:'col_<%=ctlNo%>'});
						}else{
							col_<%=ctlNo%> = new SelectBox(opts,'fld<%= ctlNo%>','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:10, controlname:'col_<%=ctlNo%>'});
						}
						document.write(col_<%=ctlNo%>.getHtml());
						col_<%=ctlNo%>.init();												
						</script>
					</td>					
				</tr>
				<%ctlNo++;%>
			</logic:iterate>  		
		</table>
	</div>

	<div id="divImportAllButton" class="navBtn" style="margin-top:15px;float: right;">
		<a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span>Import</a>
		<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="cancelImport();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/> </a>
	</div>

	<div id="divWaitForParsing" style="margin-top:15px;display:none;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="right"> 
			<tr> 
				<td style="text-align: right;" >						
					<img src="images/wait.gif" style="margin-bottom: -3px;"/>
					<bean:message key="common.please_wait"/> ,					 
					 Excel import is in process &nbsp;
				</td>	
				<td><div id="divParsedNumber" style="display:block;margin-bottom: -3px;" />
				</td>
			</tr>
		</table> 
	</div>
	
</div>

</html:form>
<script language="javascript">
function getAllColumnMappings(){
	var mappings = "";
	<%
		ArrayList fieldNames = (ArrayList) request.getAttribute("excelFieldName");
		for(int j=0; j<fieldNames.size();j++){
		String fieldName = (String)fieldNames.get(j);
	%>
		if(mappings==""){
			mappings = col_<%=j%>.getSelectedId();
		}else{
			mappings += "," + col_<%=j%>.getSelectedId();
		}
	<%}%>
	
	return mappings;
}

function submitForm(){
	var mappings = getAllColumnMappings();
	var frm=document.importForm;	
	frm.mappings.value=mappings;
	frm.mode.value="importData";	
	frm.submit();
}

function cancelImport(){	
	window.location.href=findRedirectUrl();	
}

function onWindowLoad(){

}

window.onload=onWindowLoad;

</script>