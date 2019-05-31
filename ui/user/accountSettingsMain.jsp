<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
                  com.talentPool.common.NavigationConstants, 
                  com.talentPool.user.dataobject.RoleData,
                  com.talentPool.user.UserConstants,
                  com.talentPool.user.utils.UserUtils"%>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>        
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>       

<script>
var selectBoxStatus;
</script>
  
<html:form action="/user" onsubmit="return submitForm();">
<html:hidden property="mode" name="userForm" value="saveAccountSettings"/>
<html:hidden property="t" name="userForm"/>
<html:hidden property="st" name="userForm"/>
<html:hidden property="userId" name="userForm"/>
<html:hidden property="timeZone" name="userForm"/>


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
	<br>
	<% } %>
	<%
		String saved = (String)request.getAttribute("saved");
		if(saved !=null){
	%>
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b><bean:message key="my_account.label.update_success"/></b>
			    </td>               
				</tr>
			</table>
			<br>
	<%
		}
	%>
	</div>
</div>
<div class="contentDivPop" style="padding-right:20px;">
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CHANGE_PASSWORD">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;" >
		<tr>
			<td>
				<div class="navBtnTab" style="width:155px;float: right;"><img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  						 
				  <a href="#" onclick="javascript: changePassword();" style="width:130px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="my_account.label.change_password"/></a> 
				</div>
			</td>
		</tr>
	</table>
	</logic:equal>
	<table width="100%" class="boxHeader" cellspacing="0" cellpading="0">		
		<tr>
			<td class="header" height="18"><strong><bean:message key="my_account.label.profile"/></strong></td>
		</tr>
	</table>
	<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
		<table border="0" cellspacing="0" cellpadding="0" class="posinput">
	    <tr>
	        <td class="label" height="20"><bean:message key="account_settings.label.username"/></td>
	        <td></td>
	        <td><bean:write name="userForm" property="userName"/></td>
	    </tr>							            
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.fname"/></td>
	        <td></td>
	        <td><html:text property="firstName" size="20" maxlength="15" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.lname"/></td>
	        <td></td>
	        <td><html:text property="lastName" size="20" maxlength="15" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.email"/></td>
	        <td></td>
	        <td><html:text property="email" size="50" maxlength="50" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.home_phone"/></td>
	        <td></td>
	        <td><html:text property="homePhone" size="20" maxlength="25" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.cell_phone"/></td>
	        <td></td>
	        <td><html:text property="cellPhone" size="20" maxlength="25" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.role"/></td>
	        <td></td>
	        <td><bean:write property="role" name="userForm"/></td>
	    </tr>
	    <tr>
			  <td class="label">
				  TimeZone:
			  </td>
			  <td></td>
			  <td>
				<script language="JavaScript">	
					var opts = <%=UserUtils.getJSTimeZoneArray()%>;											
					selectBoxStatus = new SelectBox(opts,'<bean:write name="userForm" property="timeZone" />','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					selectBoxStatus.setOnChangeHandler('onChangeStatus');				
					document.write(selectBoxStatus.getHtml());
					selectBoxStatus.init();
				</script>
			</td>
		  </tr>
		</table>		
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="navBtn" style="margin-top:5px;"><a href="#" style="width:50px;" class="active" onclick="javascript:if(submitForm()){document.userForm.submit();return true;}"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				</div>
			</td>
		</tr>
	</table>
</div>										
</html:form>

<script> 
window.onload=doOnLoad;

function onChangeStatus(){
	document.userForm.timeZone.value=selectBoxStatus.getSelectedId();
}
   
function doOnLoad() {
	initPopUp();
}

function changePassword() {
  showPopWin("user.do?mode=changePassword", "550", "300", null,true);
}  

function submitForm(){
	return true;
}
</script>													