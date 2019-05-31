<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
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
  	<html:hidden property="budgetGradeId" name="mastersForm"/>
  	<html:hidden property="mode" name="mastersForm"/>
  	<html:hidden property="subMode" name="mastersForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header">
				  <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %> <bean:message key="master_budget_grades.label.name"/>
				  <span class="star">*</span>
				  :
			  </td>
			  <td>
				  <html:text property="budgetGradeName" name="mastersForm" size="35" styleId="budgetGradeName"></html:text>
			  </td>
		  </tr>
		  <tr>
			  <td class="header">
				 <bean:message key="master_budget_grades.label.hire_by_duration"/> 
			  </td>
			  <td>
				 <html:text property="hireByDuration" name="mastersForm" size="35" styleId="hireByDuration"></html:text>
		     </td>
		 </tr>		 
		  <tr>
			  <td class="header">
				 <bean:message key="master_budget_grades.label.grade_description"/> 
			  </td>
			  <td>
				 <html:text property="description" name="mastersForm" size="35" styleId="description"></html:text>
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
	var errors = '';
	var Name = document.mastersForm.budgetGradeName.value;
	if(Name.trim()==""){		
		errors = addError(errors, '- <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %> <bean:message key="master_budget_grades.label.name"/>');
	}
	var hireByDuration = document.mastersForm.hireByDuration.value;
	if(hireByDuration.trim()==""){
		errors = addError(errors, '- <bean:message key="master_budget_grades.label.hire_by_duration"/>');
	}else if(isNaN(hireByDuration)){
		errors = addError(errors, '- <bean:message key="master_budget_grades.label.hire_by_duration"/>');
	}else if(hireByDuration < 0){
		errors = addError(errors, '- <bean:message key="master_budget_grades.label.hire_by_duration"/>');
	}else if(String(hireByDuration).indexOf('.') >= 0){
		errors = addError(errors, '- <bean:message key="master_budget_grades.label.hire_by_duration"/>');
	}
	if (errors.length > 0) {
		errors = addError('<bean:message key="errors.following_errors" />', errors);
		alert(errors);
		return;
	}
	document.mastersForm.submit();
}

function addError(errors, error) {
	if (errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}

function actionOnLoad(){
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_ADD%>">
		window.top.setPopTitle('<b><bean:message key="common.add"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %></b>');
	</logic:equal>
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_EDIT%>">
		window.top.setPopTitle('<b><bean:message key="common.edit"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %></b>');
	</logic:equal>
}
window.onload=actionOnLoad;
</script>