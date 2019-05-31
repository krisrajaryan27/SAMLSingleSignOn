<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.search.dataobjects.SavedSearchData"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>

<%@page import="com.talentPool.common.properties.GlobalConstants"%><script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>     
</script>

<div class="contentDivPop" style="padding-right: 20px;">
<html:form action="/doSearch" onsubmit="submitForm();return false;">
	<html:hidden property="mode" name="searchForm"/>
	<html:hidden property="showShared" name="searchForm"/>
</html:form>
<% 
	ArrayList savedSearchList = (ArrayList)request.getAttribute("savedSearchList");
	String userId = (String) request.getSession(false).getAttribute("userId"); 
%>
<logic:notEqual name="searchForm" property="showShared" value="1">
	<img src="images/checkboxunchecked.gif" id="show_shared" name="show_shared" 
		onclick="changeCheckboxState(this);" />
</logic:notEqual>
<logic:equal name="searchForm" property="showShared" value="1">
		<img src="images/checkboxchecked.gif" id="show_shared" name="show_shared" 
	onclick="changeCheckboxState(this);" />
</logic:equal>
&nbsp;<bean:message key="saved_search.label.showAll" />
<table class="boxHeader" cellpadding="0" cellspacing="0" width="568px">
	<tr>
		<td width="26px;"></td>
		<td style="height: 20px;" width="250px;"><bean:message key="common.name" /></td>
		<td><bean:message key="saved_search.label.hdr.createdBy"/></td>
		<td><bean:message key="common.shared"/></td>
	</tr>
</table>
<div class="outerDiv" style="height: 270px; width:560px; overflow: auto; padding-left: 5px;">
<table class="boxContent"  style="border:0px;" width="540" cellpadding="0" cellspacing="0">
		
		<% 
			for (int i = 0; i < savedSearchList.size(); i++) {
				SavedSearchData sData = (SavedSearchData)savedSearchList.get(i);
		%>
	<tr>
		<td style="height: 20px;" width="20px;">
			<%if(userId.equalsIgnoreCase(sData.getUserID())){ %>
				<a href="#" onclick="javascript:onClickDelete(<%=sData.getSearchId()%>);"><img src="images/ico_delete.gif" style="border: 0"></a>
			<%}else{ %>
			&nbsp;
			<%} %>
		</td>
		<td width="250px;">
			<a href="#" onclick="javascript:doSaveSearch(<%=sData.getSearchId()%>);" ><%=sData.getSearchName() %></a>
		</td>
		<td>
			<%=sData.getOwner()%>
		</td>		
		<% 	if("1".equals(sData.getShared())){
		%>
			<td>yes</td>
		<%
		}else{
		%>
			<td>no</td>
		<%} %>
		
	</tr>
		<%		
			} 
		%>
</table>
</div>
<br/>
<div class="navBtn" style="float:right;margin-left:5px;margin-top:5px;">
<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
</div>

</div>

<script type="text/javascript">
var returnVal = null;
var chkedChkBox='images/checkboxchecked.gif';
var unchkedChkBox='images/checkboxunchecked.gif';

function submitForm(){
	document.searchForm.submit();	
}

function doSaveSearch(searchId){
	returnVal=searchId;
	window.top.hidePopWin(true);
}

function onClickDelete(id){
	var url="mode=deleteSavedSearch&searchId="+id;
	var myAjax = ajaxCall("doSearch.do","get",url,onDeleteComplete,reportError);
}

function onDeleteComplete(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="common.error.unable_to_process"/>');
		return;
	}
	reloadWindow();
}

function reloadWindow(){
	var showShared = document.searchForm.showShared.value;
	var url = 'doSearch.do?mode=showSavedSearches&showShared='+showShared;
	window.location=url;
}

function actionOnLoad(){
	var title = '<b><bean:message key="common.saved_searches"/></b>';
	window.top.setPopTitle(title);
}
function changeCheckboxState(obj) 
{
	var showShared = '';
	if(obj.src.indexOf(chkedChkBox) != -1) {
		showShared = '0';
	} else {
		showShared = '1';
	}
	document.searchForm.mode.value='showSavedSearches';
	document.searchForm.showShared.value=showShared; 
	document.searchForm.submit();
}	
window.onload=actionOnLoad;
</script>