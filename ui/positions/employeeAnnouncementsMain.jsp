<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<script language="javascript" type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script>
tinyMCE.init({
	mode : "exact",
	elements : "announcements",
	theme : "advanced",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_buttons1 : "newdocument,bold,italic,underline,separator,forecolor,backcolor,separator,bullist,numlist,",
	theme_advanced_buttons2 : "undo,redo,separator,cut,copy,paste,separator,justifyleft,justifyright,",
	theme_advanced_buttons3 : "formatselect,fontselect,fontsizeselect",
	force_br_newlines: true,
	theme_advanced_disable : "anchor",
	theme_advanced_path : false
});
</script>

<div class="contentDiv">			
	<%
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
	<table  id="m_errortable" >
		<tr>
		    <td class="header" colspan="2">
		    	<b><bean:message key="errors.following_errors"/></b>
		    </td>
		</tr>
	    <tr>
	    	<td class="message" colspan="2"><html:errors/></td>
	    </tr>
    </table>
    <% } %>
</div>

<html:form action="/position">
<html:hidden property="mode" name="positionForm"/>

<div class="contentDiv">	
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<logic:notEqual name="positionForm" property="subMode" value="<%=PositionConstants.SUB_MODE_EDIT%>">
		<tr>
			<td>
				<div style="width: 250px;">
				<bean:write property="employeeAnnouncements" name="positionForm" filter="false"/>
				</div>
			</td>
		</tr>
	</logic:notEqual>
	<logic:equal name="positionForm" property="subMode" value="<%=PositionConstants.SUB_MODE_EDIT%>">
		<tr>
			<td>
				<html:textarea  styleId="announcements" property="employeeAnnouncements" name="positionForm" rows="35" cols="53"></html:textarea>
			</td>
		</tr>
	</logic:equal>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<logic:notEqual name="positionForm" property="subMode" value="<%=PositionConstants.SUB_MODE_EDIT%>">			
			<tr>
				<td colspan="2"><br/>
					<div class="navBtn" style="float:left;">
					<a href="#" style="width:50px;" class="active" onclick="javascript:editAnnouncemnts();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
					</div>
			</tr>
		</logic:notEqual>
		<logic:equal name="positionForm" property="subMode" value="<%=PositionConstants.SUB_MODE_EDIT%>">
			<tr>
				<td colspan="2">
					<br/>
					<div class="navBtn" style="float:left;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:saveAnnouncemnts();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelAnnouncemnts();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</logic:equal>
	</table>	
</div>
</html:form>
<br/>

<script>
function editAnnouncemnts(){
	window.location="position.do?mode=getEmployeeAnnouncementsToPublish&subMode=<%=PositionConstants.SUB_MODE_EDIT%>";
}
function saveAnnouncemnts(){
	document.positionForm.mode.value="saveEmployeeAnnouncementsToPublish";
	document.positionForm.submit();
	
}
function cancelAnnouncemnts(){
	window.location="position.do?mode=getEmployeeAnnouncementsToPublish";
}
</script>