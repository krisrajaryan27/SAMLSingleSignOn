<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	var returnVal = '<bean:write property="key" name="adminForm"/>' + ':::' + '<bean:write property="value" name="adminForm" />';
	window.top.hidePopWin(true);
</logic:present>                
</script>

<div class="contentDivPop" style="width:600px;">
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
	<html:form action="/adminHome" onsubmit="submitForm();return false;">
	<html:hidden property="mode" name="adminForm" value="saveMessage"/>  	
  	<html:hidden property="key" name="adminForm"/>  	
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header" style="vertical-align:top;">
				<bean:message key="admin.messages.label.update_value"/>:
			</td>
			<td>
				<html:textarea property="value" name="adminForm" styleId="skillCategory" cols="100" rows="5"/>
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
document.onkeypress=checkEnterKey;
function checkEnterKey(e) {
	var retVal = checkEnter(e);
	return !retVal;
}
function submitForm(){
	var val = document.adminForm.value.value;
	if(val.trim()==""){
		return;
	}
	document.adminForm.submit();
}

function actionOnLoad(){
	window.top.setPopTitle('<b>Edit Value</b>');
}
window.onload=actionOnLoad;
</script>