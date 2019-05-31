<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>

<div class="contentDivPop" style="width:460px;">
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
	<html:form action="/location" onsubmit="submitForm();return false;">
  	<html:hidden property="locationId" name="mastersForm"/>
  	<html:hidden property="officeId" name="mastersForm"/>
 	<html:hidden property="mode" name="mastersForm"/>
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header">
				  <bean:message key="common.office"/> <bean:message key="common.name"/>
				  <span class="star">*</span>
			  </td>
			  <td>
				  <html:text property="officeName" name="mastersForm" size="41" styleId="officeName"></html:text>
			  </td>
		  </tr>
		  <tr>
			  <td class="header" style="vertical-align: top;">
				  <bean:message key="common.office"/> <bean:message key="common.address"/>
			  </td>
			  <td>
				  <html:textarea property="officeAddress" name="mastersForm" rows="5"  cols="40" styleId="officeAddress"></html:textarea>
			  </td>
		  </tr>
		  <tr>
			  <td class="header" style="vertical-align: top;">
				 <bean:message key="common.office"/> <bean:message key="common.description"/>
			  </td>
			  <td>
				  <html:textarea property="officeDesc" name="mastersForm" rows="5"  cols="40" styleId="officeDesc"></html:textarea>
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
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
</div>


<script type="text/javascript">

function submitForm(){
	var Name = document.mastersForm.officeName.value;
	if(Name.trim()==""){
		return;
	}
	document.mastersForm.mode.value='saveOffice';
	document.mastersForm.submit();
}

function actionOnLoad(){
	<logic:empty name="mastersForm" property="officeId">
		window.top.setPopTitle('<b><bean:message key="common.add"/> <bean:message key="common.office"/></b>');
	</logic:empty>
	<logic:notEmpty name="mastersForm" property="officeId">
		window.top.setPopTitle('<b><bean:message key="common.edit"/> <bean:message key="common.office"/></b>');
	</logic:notEmpty>
}
window.onload=actionOnLoad;
</script>