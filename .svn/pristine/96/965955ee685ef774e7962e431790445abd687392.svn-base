<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="org.apache.struts.Globals" %>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>

<s:if test="%{updated==1}">
	<script type="text/javascript">
	window.top.hidePopWin(true);
	</script>
</s:if>

<div class="contentDivPop" style="width:450px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>			
			<table id="m_errortable"> 
				<tr>
				    <td class='header'>
			        <b><s:actionmessage key="errors.following_errors"/></b>
				    </td>               
				</tr>
			    <tr>
		        	<td class="message"><s:actionerror/></td>               
			    </tr>
			</table><br/><br/>
	<%
		}
	%> 
	<div class="outerDiv">
	
<s:form action="saveSecurityQuestion" method="POST">
<s:hidden name="securityQuestionId" id="securityQuestionId"/>
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header">
			  	<s:label key="admin_master.title.security_question"/><span class="star">*</span>:
			  </td>
			  <td>
			  	  <s:textfield id="securityQuestion" name="securityQuestion" size="65" maxLength="250"/>
			  </td>
		  </tr>
	 </table>
	</div>
	<div class="popupBody">
		<table class="tblPop" style="width: 100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();">
				<span class="rightC"></span><span class="leftC"></span><s:label key="common.submit"/>
			</a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;">
				<span class="rightC"></span><span class="leftC"></span><s:label key="common.cancel"/>
			</a>
			</div>
			</td>
		</tr>
		</table>
	</div>
</s:form>	
</div>
</div>	
  	
<script type="text/javascript">

function submitForm(){
	var Name = $('securityQuestion').value;
	if(Name.trim()==""){
		alert('Please enter Security Question');
		return;
	}
	document.saveSecurityQuestion.submit();
}

function actionOnLoad(){	
		window.top.setPopTitle('<b>Security Question</b>');
}
window.onload=actionOnLoad;
</script>