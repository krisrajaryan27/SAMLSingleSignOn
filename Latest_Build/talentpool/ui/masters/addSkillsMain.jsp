<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
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
  	<html:hidden property="skillCategoryId" name="mastersForm"/>
  	<html:hidden property="skillId" name="mastersForm"/>
  	<html:hidden property="mode" name="mastersForm"/>
  	<html:hidden property="subMode" name="mastersForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="admin_master_skill.label.skill_name"/>
				<span class="star">*</span>
				:&nbsp; 
			</td>
			<td>
				<html:text property="skillName" name="mastersForm" size="35" styleId="skillName" onblur="excludeSplChar(this);"/>
		</tr>
		  <tr>
			  <td class="header">
				 <bean:message key="admin_master_skill.label.alias"/> 1:&nbsp; 
			  </td>
			  <td>
				 <html:text property="aliases[0]" name="mastersForm" size="35" styleId="aliases[0]" onblur="excludeSplChar(this);"/>
		     </td>
		 </tr>
		 <tr>
			 <td class="header">
				<bean:message key="admin_master_skill.label.alias"/> 2:&nbsp; 
		     </td>
			 <td>
				<html:text property="aliases[1]" name="mastersForm" size="35" styleId="aliases[1]" onblur="excludeSplChar(this);"/>
			 </td>
		</tr>
		<tr>
			 <td class="header">
				<bean:message key="admin_master_skill.label.alias"/> 3:&nbsp; 
			 </td>
			 <td>
				<html:text property="aliases[2]" name="mastersForm" size="35" styleId="aliases[2]" onblur="excludeSplChar(this);"/>
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
<!--
function submitForm(){
	var Name = document.mastersForm.skillName.value;
	if(Name.trim()==""){
		return;
	}
	document.mastersForm.submit();
}

function excludeSplChar(obj){	
	var validChar = encodeURIComponent(obj.value);	
	var pars = "mode=excludeSplChar&validChar="+validChar+"&objId="+obj.id;
	var myAjax = ajaxCall("masters.do",'post',pars,getValidatedSplChr,reportError);	
}

function getValidatedSplChr(request){
	xmlFile = request.responseXML;	
	var op = xmlFile.getElementsByTagName("validChar")[0].firstChild.nodeValue;
	var validChar = op.split('|');
	var validity = validChar[0];
	var objName = validChar[1];	
	if(validity=="false"){
		alert('No special characters are allowed');	
		if(objName=="skillName"){
			document.mastersForm.skillName.select();
			document.mastersForm.skillName.focus();
		}else if(objName=="aliases[0]"){
			document.mastersForm.aliases[0].select();
			document.mastersForm.aliases[0].focus();
		}else if(objName=="aliases[1]"){
			document.mastersForm.aliases[1].select();
			document.mastersForm.aliases[1].focus();
		}else if(objName=="aliases[2]"){
			document.mastersForm.aliases[2].select();
			document.mastersForm.aliases[2].focus();
		}
		return false;
	}else{		
		return true;
	}
	return true;			
}

function actionOnLoad(){
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_ADD%>">
		window.top.setPopTitle('<b>Add Skills</b>');
	</logic:equal>
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_EDIT%>">
		window.top.setPopTitle('<b>Edit Skills</b>');
	</logic:equal>
}
window.onload=actionOnLoad;
//-->
</script>
