<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>

<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
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
	<html:form action="/budgets" onsubmit="submitForm();return false;">
  	<html:hidden property="mode" value="transferBudget"/>
  	<html:hidden property="budgetItemId" name="budgetForm"/>
  	<html:hidden property="budgetItemName" name="budgetForm"/>  
  	<html:hidden property="destBudgetItemId" name="budgetForm"/>  	
  	<html:hidden property="availableHeadCount" name="budgetForm"/>  	  	
	<div class="popupTop">
		<table class="tblPop">			
		   <tr>
			  	<td class="header">
				  	<bean:message key="budget.transfer.number_of_heads"/>:
			  	</td>
			  	<td>
			  		<html:text name="budgetForm" property="transferHeadCount" size="10" styleClass="Grey" maxlength="10"/>	(<bean:write name="budgetForm" property="availableHeadCount" /> Max.)		  
				</td>
		  </tr>
		  <tr>
			  <td class="header">
				  <bean:message key="budget.transfer.trasfer_to_budget_item"/>:
			  </td>
			  <td>
			  	<script language="JavaScript">									
					var opts = <bean:write name="budgetForm" property="jsArrayBudgetItems" filter="false"/>;											
					var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
					budgetItems = opt.concat(opts);
					selectBoxBudgetItems = new SelectBox(budgetItems,'<bean:write name="budgetForm" property="destBudgetItemId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
					document.write(selectBoxBudgetItems.getHtml());
					selectBoxBudgetItems.init();
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
function validateData() {
	errors = '';
	var transferHeadCount = document.budgetForm.transferHeadCount.value;	
	var budgetItemId = selectBoxBudgetItems.getSelectedId();
	if (transferHeadCount == '') {
		errors = addError(errors, '- <bean:message key="budget.transfer.number_of_heads" />');
	}	
	if (budgetItemId == '-1') {
		errors = addError(errors, '- <bean:message key="budget.transfer.trasfer_to_budget_item" />');
	}
		
	if (errors.length > 0) {
		errors = addError('<bean:message key="common.data_required" />', errors);
	}
	return errors;
}
function addError(errors, error) {
	if (errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}
function submitForm(){
	errors = validateData();
	if (errors.length > 0) {
		alert(errors);
		return false;
	} 
	document.budgetForm.destBudgetItemId.value=selectBoxBudgetItems.getSelectedId();	
	document.budgetForm.submit();
}

function actionOnLoad(){
		var title = "<b>Transfer Budget Heads: "+document.budgetForm.budgetItemName.value+ "</b>";
		window.top.setPopTitle(title);
}

window.onload=actionOnLoad;
</script>

