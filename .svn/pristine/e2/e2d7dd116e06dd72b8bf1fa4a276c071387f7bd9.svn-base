<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
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
<link rel="stylesheet" type="text/css" href="themes/default/selectbox.css">

<script>
var selectBoxStatus;
</script>
  
<html:form action="/user" onsubmit="return submitForm();">
<html:hidden property="mode" name="userForm" value="saveTimeZone"/>
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
			 <script type="text/javascript">
			 window.location.href="login.do?loginmode=postLogin";
			 </script>
	<%
		}
	%>
	</div>
</div>
<div class="contentDivPop" style="padding-right:20px;">
	<table width="100%" class="boxHeader" cellspacing="0" cellpading="0">		
		<tr>
			<td class="header" height="18"><strong>Select Time Zone</strong></td>
		</tr>
	</table>
	<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
		<table border="0" cellspacing="0" cellpadding="0" class="posinput">
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
function onChangeStatus(){
	
}
   
function submitForm(){
	document.userForm.timeZone.value=selectBoxStatus.getSelectedId();
	return true;
}
</script>													