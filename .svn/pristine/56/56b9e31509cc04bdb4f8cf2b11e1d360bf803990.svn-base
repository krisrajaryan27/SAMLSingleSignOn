<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.search.SearchConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>

<div class="contentDivPop" style="width:350px;">
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
<html:form action="/doSearch" onsubmit="submitForm();return false;">
<html:hidden property="mode" value="saveThisSearch"/>
<html:hidden property="shared"/>

	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header">
				  <bean:message key="common.name"/>:
			  </td>
			  <td>
				  <html:text property="searchName" name="searchForm" size="35" styleId="searchName"></html:text>
			  </td>
		  </tr>
		  <tr>
			<td>&nbsp;</td>
			<td style="vertical-align:top;">
				<img src="images/checkboxunchecked.gif" style="margin-bottom:-2px;" onclick="javascript: toggleIsShared(this);" />&nbsp;&nbsp;<bean:message key="common.shared"/> ?
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
var chkedChkBoxSrc='images/checkboxchecked.gif';
var unchkedChkBoxSrc='images/checkboxunchecked.gif';

function submitForm(){
	var Name = document.searchForm.searchName.value;
	if(Name.trim()==""){
		return;
	}	
	document.searchForm.submit();
}

function toggleIsShared(obj) {
	src = obj.src;
	if(src.indexOf(chkedChkBoxSrc) == -1) {
		obj.src = chkedChkBoxSrc;
		document.searchForm.shared.value = '<%=SearchConstants.SEARCH_SHARED%>';
	} else {
		obj.src = unchkedChkBoxSrc;
		document.searchForm.shared.value = '<%=SearchConstants.SEARCH_NOT_SHARED%>';
	}
}

function actionOnLoad(){
	var title = '<b><bean:message key="common.save_search"/></b>';
	window.top.setPopTitle(title);
}
window.onload=actionOnLoad;
</script>

