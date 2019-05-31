<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals" %>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<logic:present name="update" scope="request">
<script language="JavaScript">
window.top.hidePopWin(true);
</script>
</logic:present> 
<div class="contentDivPop" style="width: 500px;">
	<% if(request.getAttribute(Globals.ERROR_KEY)!=null){ %>
	<table  id="m_errortable" >
		<tr><td class="header"><b><bean:message key="errors.following_errors"/></b></td></tr>
	   	<tr><td class="message"><html:errors/></td></tr>
	</table>
	<br>
	<% } %>
	<div class="outerDiv">
		<html:form action="/offerSheet" enctype="multipart/form-data">
		<html:hidden property="mode" name="offerSheetForm" />
		<html:hidden property="isSubmitted" value="1"/>
		<div class="popupTop">
  		<table class="tblPop"> 
  			<tr>
				<td nowrap="nowrap">
					<bean:message key="offersheet_templates.label.offersheet_template_name"/>
					<span class="star">*</span>
					:
				</td>
				<td>
					<html:text property="templateName" styleId="templateName" name="offerSheetForm" size="25" styleClass="Grey" maxlength="100" />
				</td>	  			
  			</tr>
  			<tr>
				<td nowrap="nowrap" style="vertical-align: top;">
					<bean:message key="offersheet_templates.label.offersheet_template_description"/>
					:
				</td>
				<td>
					<html:textarea property="templateDesc" name="offerSheetForm"  rows="4" cols="45" styleClass="Grey" />
				</td>	  			
  			</tr>  			
  			<tr>
				<td nowrap="nowrap">
					<bean:message key="offersheet_templates.label.offersheet_template_document"/>
					<span class="star">*</span>
					:
				</td>
				<td>
					<html:file property="templateDocument" name="offerSheetForm" style="width:300px;height:20px; "></html:file>
				</td>	  			
  			</tr>
  		</table> 
  		</div>
  		<div class="popupBody">
		<table class="tblPop" width="100%">
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript: addTemplate();"><span class="rightC"></span><span class="leftC"></span>
						<bean:message key="common.save" /></a> 
						<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span>
						<bean:message key="common.cancel" /></a>
					</div>
				</td>
			</tr>
		</table>  
		</div>
		</html:form>
	</div>
</div>	
<script language="JavaScript">
window.onload=doOnLoad;

function doOnLoad() {
	setPopupTitle();
}

function setPopupTitle(){
	var popupTitle = '<b><bean:message key="common.add_new"/> <bean:message key="offersheet_templates.label.offersheet_template" /></b>';	
	window.top.setPopTitle(popupTitle);
}

function addTemplate() {
	document.offerSheetForm.submit();
}
</script>