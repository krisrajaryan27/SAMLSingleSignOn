<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>   

var selectBoxUserToMove = null;
var selectBoxManagedUser = null;
</script>
<div class="contentDivPop" style="width:450px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
			<table id="m_errortable" > 
				<tr>
			    <td class='header'>
		        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
				</tr>
		    <tr>
	        <td class="message"><html:errors/></td>               
		    </tr>
			</table><br/><br/>
	<%
		}
	%> 
	<div class="outerDiv">
	<html:form action="/hierarchyHome" onsubmit="return submitForm();">
  	<html:hidden property="mode" name="adminForm" value="deleteUserFromHierarchy"/>
  	<html:hidden property="orgId" name="adminForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="common.remove"/> <bean:message key="common.user"/> :&nbsp; 
			</td>
			<td>
				<script language="JavaScript">	
					var opts = <%=request.getAttribute("jsRemovableUsers")%>;											
					var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
					unManagedUser = opt.concat(opts);
					selectBoxUserToMove = new SelectBox(unManagedUser,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					document.write(selectBoxUserToMove.getHtml());
					selectBoxUserToMove.init();
				</script>
			</td>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
	<table>
		<tr>
			<td class="Grey">
				<bean:message key="common.note"/> : <bean:message key="common.user"/> <bean:message key="admin.manage_hierarchy.label.remove_user_note"/>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">

function submitForm(){
	var orgId = selectBoxUserToMove.getSelectedId();
	if(orgId==-1){
		alert('<bean:message key="common.please_select"/> -<bean:message key="common.user"/> to <bean:message key="common.remove"/>');
		return false;
	}
	document.adminForm.orgId.value=orgId;
	document.adminForm.submit();
}
function actionOnLoad(){
	window.top.setPopTitle('<bean:message key="common.remove"/> <bean:message key="common.user"/> <bean:message key="common.from"/> <bean:message key="common.hierarchy"/>');
}
window.onload=actionOnLoad;

</script>
