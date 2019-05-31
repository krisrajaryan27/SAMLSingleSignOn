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
	<html:form action="/masters" onsubmit="submitForm();return false;">
  	<html:hidden property="deptId" name="mastersForm"/>
  	<html:hidden property="mode" name="mastersForm"/>
  	<html:hidden property="subMode" name="mastersForm"/>
  	<html:hidden property="parentDepartmentId" name="mastersForm"/>
  	<html:hidden property="parentDepartmentLevel" name="mastersForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="admin_master_dept_cat.label"/>
				<span class="star">*</span>
				:
			</td>
			<td>
				<html:text property="departmentName" name="mastersForm" size="35" styleId="skillCategory"></html:text>
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
	var Name = document.mastersForm.departmentName.value;
	if(Name.trim()==""){
		return;
	}
	document.mastersForm.submit();
}

function actionOnLoad(){
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_ADD%>">
		window.top.setPopTitle('<b><bean:message key="admin_master_dept.label.add_department"/></b>');
	</logic:equal>
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_EDIT%>">
		window.top.setPopTitle('<b><bean:message key="admin_master_dept.label.edit_department"/></b>');
	</logic:equal>
}
window.onload=actionOnLoad;
</script>










