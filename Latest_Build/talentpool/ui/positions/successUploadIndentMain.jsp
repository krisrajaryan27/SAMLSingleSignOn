<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.positions.dataobject.DraftData" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.struts.Globals"%>
<logic:present name="relativeFilePath" scope="request">
<script>
var returnVal;
function load(relativeFilePath) {
	returnVal="file_" + relativeFilePath;
	window.top.hidePopWin(true);
}
load('<bean:write name="relativeFilePath" scope="request" />');
</script>
</logic:present>
<logic:notPresent name="draftId" scope="request">
<script language="JavaScript">
window.onload=doOnLoad;

function doOnLoad() {
	var title = '<b><bean:message key="position.home.import_position"/></b>';
	window.top.setPopTitle(title)
}
var returnVal;
function load(draftId) {
	returnVal="draft_"+draftId;
	window.top.hidePopWin(true);
}
</script>
<div class="contentDivPop">
	<% if (request.getAttribute(Globals.ERROR_KEY) != null) { %>
	<table id="m_errortable">
		<tr>
			<td class="header"><b><bean:message
				key="errors.following_errors" /></b></td>
		</tr>
		<tr>
			<td class="message"><html:errors /></td>
		</tr>
	</table>
	<br/>
	<% } %>	
	<div class="outerDiv" style="width:460px;">
		<div class="popupBody">
		<logic:present name="drafts" scope="request">
		<logic:notEmpty name="drafts" scope="request">
			<table width="100%">
				<tr>
					<td><%=((List)(request.getAttribute("drafts"))).size()%>&nbsp;<bean:message key="common.position_s" /> <bean:message key="position.label.upload_indent_success" /></td>
				</tr>
				<logic:iterate id="draft" name="drafts" scope="request" type="DraftData">
				<tr>
					<td><bean:write name="draft" property="originalFileName"/></td>
					<td><a href="#" style="width:48px;" class="btn3" onclick="javascript: load('<bean:write name="draft" property="draftId"/>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.load"/></a></td>
				</tr>
				</logic:iterate>
			</table>
		</logic:notEmpty>
		</logic:present>
		</div>
	</div>
</div>
</logic:notPresent>