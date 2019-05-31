<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@page import="com.talentPool.positions.utils.PositionUtils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.budget.utils.BudgetUtils"%>

<%@page import="com.talentPool.budget.BudgetConstants"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
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
	<html:form action="/position" onsubmit="submitForm();return false;">
  	<html:hidden property="showCondition" name="positionForm"/>
  	<html:hidden property="mode" value="changePositionStatus"/>
  	<html:hidden property="positionId" name="positionForm"/>
  	<html:hidden property="markCandidatesAndClosePosition" name="positionForm"/>
  	<html:hidden property="isBudgetCommitted" name="positionForm" />
  	<html:hidden property="dropReason" name="positionForm" />  	
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header">
				  <bean:message key="common.position_status"/>:
			  </td>
			  <td>
				<script language="JavaScript">	
					var opts = <%=PositionUtils.getJSArrayForPositionStatus((String)request.getAttribute("positionStatus"))%>;											
					selectBoxStatus = new SelectBox(opts,'<bean:write name="positionForm" property="showCondition" />','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					selectBoxStatus.setOnChangeHandler('onChangeStatus');				
					document.write(selectBoxStatus.getHtml());
					selectBoxStatus.init();
				</script>
			</td>
		  </tr>
		  <tr style="display:none;" id="budgetRow">
			<td class="header">			
			 <bean:message key="change_status.label.keep_budget_committed"/>&nbsp;&nbsp;<img src="images/checkboxunchecked.gif" onclick="javascript: changeToCommitted(this);" id="isCommitted">
			</td>
			<td></td>
		</tr>
		<tr id="dropReasonRow" style="display:none;">
			<td class="header">			
			 	<bean:message key="change_status.label.reason_for_dropping"/>:		 
			</td>
			<td>
				<script language="JavaScript">	
					var opts = <%=PositionUtils.getJSArrayForPositionDropReason()%>;											
					selectBoxDropReason = new SelectBox(opts,'<bean:write name="positionForm" property="dropReason" />','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					selectBoxDropReason.setOnChangeHandler('onChangeStatus');				
					document.write(selectBoxDropReason.getHtml());
					selectBoxDropReason.init();
				</script>
			</td>
		</tr>
		<tr id="dropComment" style="display:none;">
			<td class="header">			
			 	<bean:message key="change_status.label.comment_for_dropping"/>:			 
			</td>
			<td>
				<html:textarea name="positionForm" property="dropComment" cols="30" rows="2"/>
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
var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";

function submitForm(){

	if(selectBoxStatus.getSelectedId() == '<%=PositionConstants.POSITION_STATUS_CLOSED%>')
	{
		candidatesInProcess();
	}else{

		if(selectBoxStatus.getSelectedId() != '<%=PositionConstants.POSITION_STATUS_HOLD%>' )
		{
			document.positionForm.isBudgetCommitted.value= '<%=BudgetConstants.IS_BUDGET_COMMITTED%>';			
		}
		if(selectBoxStatus.getSelectedId() == '<%=PositionConstants.POSITION_STATUS_DROPPED%>') 
			// && selectBoxDropReason)
		{
			document.positionForm.dropReason.value='1';
		}
		document.positionForm.showCondition.value=selectBoxStatus.getSelectedId(); 
		document.positionForm.submit();
	}
	
}

function actionOnLoad(){
		var title = "<b>Change Position Status: " + "<%=Utils.escapeHTML((String)request.getAttribute("PositionName"))%>" + "</b>";
		window.top.setPopTitle(title);		
		 <% if (ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()) { %>			
			var positionStatus = '<%=Utils.escapeHTML((String)request.getAttribute("positionStatus"))%>';
			if(positionStatus == '<%=PositionConstants.POSITION_STATUS_HOLD%>'){
				var isBudgetCommitted =document.positionForm.isBudgetCommitted.value;
				if(isBudgetCommitted == '<%=BudgetConstants.IS_BUDGET_COMMITTED%>'){
					var checkBox = document.getElementById('isCommitted');
					checkBox.src=chkboxchked;			
				}
				Element.show('budgetRow');			
			}
			<% } %>					
			if(positionStatus == '<%=PositionConstants.POSITION_STATUS_DROPPED%>'){
				Element.show('dropReasonRow');
				Element.show('dropComment');
			}	
}


function candidatesInProcess(){
	var pars = "mode=checkForCandidatesInProcess&positionId=<bean:write name="positionForm" property="positionId" />"  ;
  	var myAjax = ajaxCall("position.do",'get',pars,onCheckCandidatesInProcess, reportError);

	
}

function onCheckCandidatesInProcess(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
 		errors = getErrors(xmlFile);
 		if(errors[0]=='position.home.error.candidates_inprocess'){
			retValue = confirm('<bean:message key="common.position"/> <bean:message key="position.home.error.confirm_position_close"/>');
			if (retValue == true) {
				document.positionForm.markCandidatesAndClosePosition.value="1";
				document.positionForm.showCondition.value=selectBoxStatus.getSelectedId(); 
				document.positionForm.submit();
			}
		}else{
			//alert(errors[0]);	
		}
		return;			
	}
 	document.positionForm.showCondition.value=selectBoxStatus.getSelectedId(); 
	document.positionForm.submit();
}

function onChangeStatus(){
	var id = selectBoxStatus.getSelectedId();
	 <% if (ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()) { %>			
		var id = selectBoxStatus.getSelectedId();
		if(id == '<%=PositionConstants.POSITION_STATUS_HOLD%>'){
			Element.show('budgetRow');
		}
		else{
			Element.hide('budgetRow');
		}
		<% } %>		
		if(id == '<%=PositionConstants.POSITION_STATUS_DROPPED%>'){
			// Element.show('dropReasonRow');
			Element.show('dropComment');		
		}
		else{
			// Element.hide('dropReasonRow');
			Element.hide('dropComment');
		}
}

function changeToCommitted(obj) {
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
		document.positionForm.isBudgetCommitted.value= '<%=BudgetConstants.IS_BUDGET_AVAILABLE%>';
	} else {
		obj.src=chkboxchked;
		document.positionForm.isBudgetCommitted.value= '<%=BudgetConstants.IS_BUDGET_COMMITTED%>';
	}
}

window.onload=actionOnLoad;
</script>

