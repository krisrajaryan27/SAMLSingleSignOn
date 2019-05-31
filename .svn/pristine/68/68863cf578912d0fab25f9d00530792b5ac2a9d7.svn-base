<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="org.apache.struts.Globals" %>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>

<s:if test="%{updated==1}">
	<script language="JavaScript">
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
	
<s:form action="saveEmployer" method="POST">
<s:hidden name="employerId" id="employerId"/>
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			  <td class="header"><s:label key="common.employer"/><span class="star">*</span>:
			  </td>
			  <td>
			  	  <s:textfield id="employerName" name="employerName" size="65" maxLength="150"/>
			  </td>
		</tr>
		<tr>
			  <td class="header"><s:label key="admin_master_skill.label.alias"/> 1:&nbsp;
			  </td>
			  <td>
			  	  <s:textfield id="alias[0]" name="alias[0]" size="65" maxLength="150"/>
			  </td>
		</tr>
		<tr>
			  <td class="header"><s:label key="admin_master_skill.label.alias"/> 2:&nbsp;
			  </td>
			  <td>
			  	  <s:textfield id="alias[1]" name="alias[1]" size="65" maxLength="150"/>
			  </td>
		</tr>
		<tr>
			  <td class="header"><s:label key="admin_master_skill.label.alias"/> 3:&nbsp;
			  </td>
			  <td>
			  	  <s:textfield id="alias[2]" name="alias[2]" size="65" maxLength="150"/>
			  </td>
		</tr>
	 	</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span>Submit</a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span>Cancel</a>
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
	var Name = $('employerName').value;
	if(Name.trim()==""){
		alert('Please enter Employer');
		return;
	}
	document.saveEmployer.submit();
}

function actionOnLoad(){	
		window.top.setPopTitle('<b>Employer</b>');
}
window.onload=actionOnLoad;
</script>