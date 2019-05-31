<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<script language="javascript" type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script>
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                

tinyMCE.init({
	mode : "exact",
	elements : "positionsHomeHeader",
	theme : "advanced",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_buttons1 : "newdocument,bold,italic,underline,separator,forecolor,backcolor,separator,bullist,numlist,undo,redo,separator,cut,copy,paste,separator,justifyleft,justifycenter,justifyright,separator,code,",
	theme_advanced_buttons2 : "formatselect,fontselect,fontsizeselect",
	theme_advanced_buttons3 : "",
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
<html:form action="/adminHome">
<html:hidden property="mode" name="adminForm"/>
<div class="contentDivPop" >
<div class="outerDiv">
	<div class="popupTop">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tblPop">
			<tr>
				<td>
					<html:textarea  styleId="positionsHomeHeader" property="positionsHomeHeader" name="adminForm" rows="21" cols="103"></html:textarea>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
					<br/>
					<div class="navBtn" style="float:left;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:savePositionsHomeHeader();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>		
</div>
</html:form>
<br/>
<script>
function savePositionsHomeHeader(){
	document.adminForm.mode.value="savePositionsHomeHeader";
	document.adminForm.submit();
}
function actionOnLoad(){
	window.top.setPopTitle('<bean:message key="admin_website_screen_configuration.label.filters_description" />');
}
window.onload=actionOnLoad;
</script>