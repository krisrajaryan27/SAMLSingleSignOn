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

var selectBoxUnManagedUser = null;
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
	<logic:present name="companyName" scope="request">
	<div class="outerDiv">
	<html:form action="/hierarchyHome" onsubmit="return submitForm();">
  	<html:hidden property="mode" name="adminForm" value="saveUserToHierarchy"/>
  	<html:hidden property="userId" name="adminForm"/>
  	<html:hidden property="parentId" name="adminForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="common.select"/> <bean:message key="common.user"/>:&nbsp; 
			</td>
			<td>
				<script language="JavaScript">	
					var opts = <%=request.getAttribute("jsUnManagedUser")%>;											
					var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
					unManagedUser = opt.concat(opts);
					selectBoxUnManagedUser = new SelectBox(unManagedUser,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					document.write(selectBoxUnManagedUser.getHtml());
					selectBoxUnManagedUser.init();
				</script>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="common.assign_under"/> :&nbsp; 
			</td>
			<td>
				<script language="JavaScript">	
					var opts = <%=request.getAttribute("jsManagedUser")%>;									
					var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
					var m = [new SelectOption('0','<bean:write  name="companyName" scope="request"/>')];
					opt = opt.concat(m);
					managedUser = opt.concat(opts);
					selectBoxManagedUser = new SelectBox(managedUser,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					document.write(selectBoxManagedUser.getHtml());
					selectBoxManagedUser.init();
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
</logic:present>
</div>

<script type="text/javascript">

function submitForm(){
	var userId = selectBoxUnManagedUser.getSelectedId();
	var parentId = selectBoxManagedUser.getSelectedId();
	if(userId==-1){
		alert('<bean:message key="common.please_select"/> -<bean:message key="common.user"/>');
		return false;
	}
	if(parentId==-1){
		alert('<bean:message key="common.please_select"/> -<bean:message key="common.assign_under"/>');
		return false;
	}
	document.adminForm.userId.value=userId;
	document.adminForm.parentId.value=parentId;
	document.adminForm.submit();
}
function actionOnLoad(){
	window.top.setPopTitle('<bean:message key="common.add"/> <bean:message key="common.user"/> <bean:message key="common.to"/> <bean:message key="common.hierarchy"/>');
}
window.onload=actionOnLoad;

</script>
