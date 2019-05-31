<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
                  com.talentPool.common.NavigationConstants"%>         

<%@page import="com.talentPool.userConfiguration.utils.UserConfigurationUtils"%>
<%@page import="com.talentPool.userConfiguration.constants.UserConfigurationConstants"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
    
<script>
var checkBoxList = null;
</script>
     
<html:form action="/user" onsubmit="return submitForm();">
<html:hidden property="mode" name="userForm" value="saveUserConfigurations"/>
<html:hidden property="t" name="userForm"/>
<html:hidden property="st" name="userForm"/>
<html:hidden property="userId" name="userForm"/>
<html:hidden property="userConfValue" name="userForm"/>
<html:hidden property="restoreConfig" name="userForm"/>
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
	
	<table width="100%" class="boxHeader" cellspacing="0" cellpading="0">		
		<tr>
			<td class="header" height="18"><strong><bean:message key="my_account.label.user_configurations"/></strong></td>
		</tr>
	</table>
	
	<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
		<table border="0" cellspacing="0" cellpadding="0" class="posinput">
	    <tr>
	        <td class="label" height="20"><bean:message key="my_account.label.applicant_tooltip"/>:</td>

	         <td height="20">
			  	<script type="text/javascript">	
			  		options = <%=UserConfigurationUtils.getJSArrayApplicantTooltip()%>
			  		checkBoxList = new CheckBoxList(options,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'310px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
					document.write(checkBoxList.getHtml());
					checkBoxList.init();
					checkBoxList.resetSelected('<%=request.getAttribute("userConfValue")%>');
		 		</script>			  	
			  </td>
	    </tr>							            
		</table>		
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="navBtn" style="margin-top:5px;float:left;">
				<a href="#" style="width:116px;" class="active" onclick="javascript: restoreToDefault();"><span class="rightC"></span><span class="leftC"></span><bean:message key="admin.messages.label.restore_default"/></a>
				<a href="#" style="width:50px;margin-left: 5px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				</div>
			</td>
		</tr>
	</table>
</div>										
</html:form>

<script> 
  

function submitForm(){
	document.userForm.userConfValue.value = checkBoxList.getSelectedIds();
	document.userForm.restoreConfig.value='';
	document.userForm.submit();
	return true;
}

function restoreToDefault(){
	document.userForm.restoreConfig.value=<%= UserConfigurationConstants.RESTORE_CONFIG%>;
	document.userForm.userConfValue.value = '';
	document.userForm.submit();
	return true;
}
</script>													