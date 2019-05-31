<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.user.UserConstants,com.talentPool.common.NavigationConstants"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>

<div class="contentDiv">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
		<table  id="m_errortable" > 
			<tr>
		   	<td class="header">
		      <b><bean:message key="errors.following_errors"/></b>
			  </td>               
				</tr>
	    	<tr>
	        <td class="message"><html:errors/></td>               
	    	</tr>
		</table>
		<br>
	<% } %>
<logic:present name="companyName" scope="request">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		<tr> 
			<td style="vertical-align: bottom;">
				<table cellpadding="0" cellspacing="0" class="boxETab">
				  <tr>
					  <td class="leftC"></td>
					  <td class="content"><bean:message key="common.manage"/> <bean:message key="common.hierarchy"/></td>
					  <td class="rightC"></td>
				  </tr>
			  	</table>
		  	</td>
			<td>						
	   			<div class="navBtnTab" style="float: right;">				
	   				<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>	
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
					<a href="#" style="width:80px;" onclick="addNewRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
					<a href="#" style="width:50px;" onclick="moveRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.move"/></a> 
					<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
					<a href="#" style="width:70px;" onclick="removeRecord();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.remove"/></a> 
				</div>
			</td>			  
		</tr>
	</table> 
	
	<div class="outerDiv" style="padding:5px 0px 10px 10px;">
	<table>
		<tr>
			<td style="border-bottom: dotted;border-bottom-width: 1px;border-bottom-color: #999;">
				<bean:write  name="companyName" scope="request"/>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
				<bean:write name="hierarchy" scope="request" filter="false"/>
			</td>
		</tr>
	</table>
	
	</div>
</logic:present>
</div>


<script> 

function addNewRecord(){
	var url="hierarchyHome.do?mode=addUserToHierarchy";
	 window.setTimeout("showInPopUp('"+url+"',500,230,reloadWindow,true);", 10); 
}

function moveRecord(){
	var url="hierarchyHome.do?mode=moveUserInHierarchy";
	showInPopUp(url,550,230,reloadWindow,true);
}

function removeRecord(){
	var url="hierarchyHome.do?mode=removeUserFromHierarchy";
	showInPopUp(url,500,200,reloadWindow,true);
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function reloadWindow(){
	window.location.reload();
	return true;
}

function onWindowLoad(){
	initPopUp();	
}
window.onload=onWindowLoad;

</script>
