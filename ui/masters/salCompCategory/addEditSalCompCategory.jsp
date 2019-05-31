<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="org.apache.struts.Globals" %>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<s:if test="%{#request['updated']==1}">
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
<s:form action="saveSalCompCategory" method="POST">
<s:hidden name="salCompCategoryId" id="salCompCategoryId"/>
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header"><s:label key="admin_master.label.salary_components_category"/><span class="star">*</span>:
			  </td>
			  <td>
			  	  <s:textfield id="salCompCategoryName" name="salCompCategoryName" size="65" maxLength="250"/>
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
	var Name = $('salCompCategoryName').value;
	if(Name.trim()==""){
		alert('<s:text name="admin_master.error.salary_components_category_name_mand" />');
		return;
	}
	document.saveSalCompCategory.submit();
}

function actionOnLoad(){	
		window.top.setPopTitle('<b><s:text name="admin_master.label.salary_components_category_master" /></b>');
}
window.onload=actionOnLoad;
</script>