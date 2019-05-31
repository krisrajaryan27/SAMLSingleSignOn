<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.common.db.SimpleDataObject"%>
<%@page import="com.talentPool.desktop.constants.DesktopConstants"%>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<%
String imported = (String)request.getAttribute("imported");
%>
<div class="contentDivPop" >
	<table cellpadding="0" cellspacing="0" class="boxETab">
	  <tr>
		  <td class="leftC"></td>
		  <td class="content"><bean:message key="common.message"/></td>
		  <td class="rightC"></td>
	  </tr>
  	</table>  
	<div class="outerDiv" style="padding:20px;">
		<html:form action="/desktop" >
			<html:hidden property="emailId" name="desktopSearchForm"/>	
			<html:hidden property="result" name="desktopSearchForm"/>	
		</html:form>
			<% if(imported!=null && !imported.equals("0")) {%>
				Successfully <bean:message key="common.imported"/> <%=imported %> <bean:message key="common.resumes"/> in <bean:message key="title.common"/>.				
				<logic:notEqual property="sessionType" name="desktopSearchForm" value="<%=DesktopConstants.SESSION_TYPE_BROWSER_IMPORT%>">
				<br/><br/>
				Click 'Done' button at the bottom of the window to finish the process.
				</logic:notEqual>
			<% } else { %>
				No <bean:message key="common.resume"/> <bean:message key="common.imported"/>
			<% } %>
				
	</div>
	<br/>
	
	
	<div id="divImportAllButton">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
				<td>	
				<logic:equal property="sessionType" name="desktopSearchForm" value="<%=DesktopConstants.SESSION_TYPE_INBOX_BULK_IMPORT%>">
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript:done();return false;" id="ignore"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.done"/></a>
					</div>
				</logic:equal>			
				</td>
			</tr>
		</table> 
	</div>
	
</div>
<script language="JavaScript">
function done() {
	var temp = document.desktopSearchForm.result.value;
	parts1 = temp.split("$");
	var emailIds = '';
	for(i = 0; i < parts1.length; i++) {
		parts2 = parts1[i].split("|");		
		if(emailIds != '') {
			emailIds += ',';
		}
		emailIds += parts2[0];
	}
	window.opener.document.forms[0].emailIds.value=emailIds;
	window.opener.reloadGridWithSelectedFolder();
	window.close();
}

window.onload=doOnLoad;
function doOnLoad() {	
<logic:notEmpty name="commentUrls" scope="request">
<logic:iterate id="commentUrl" name="commentUrls" type="SimpleDataObject">
	var el = document.createElement("iframe");
	el.style.width="0px";
	el.style.height="0px";
	el.setAttribute('frameBorder','0');			
	document.body.appendChild(el);
	var url = '<%=commentUrl.getString("commentUrl")%>'.replace(/&amp;/g, '&');
	el.setAttribute('src', url);
</logic:iterate>	
</logic:notEmpty>
}
</script>