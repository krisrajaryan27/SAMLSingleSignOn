<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>

<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
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
	<html:form action="/step" onsubmit="submitForm();return false;">
  	<html:hidden property="stepLevel" name="mastersForm"/>
 	<html:hidden property="mode" name="mastersForm" value="saveStage"/>
	<div class="popupTop">
		<table class="tblPop">
			<tr>
				<td class="header">
					<bean:message key="master_steps.label.stage_name" />
					<span class="star">*</span>:
				</td>
				<td>
					<html:text property="stage" name="mastersForm" size="45" maxlength="50" ></html:text>		
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
	var Name = document.mastersForm.stage.value;
	if(Name.trim()==""){
		alert('Please enter the Stage Name');
		return;
	}
	document.mastersForm.submit();
}

</script>