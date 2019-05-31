<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>   

var selectBoxUserToMove = null;
var selectBoxManagedUser = null;
</script>
<div class="contentDivPop" style="width:500px;">
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
	<logic:present name="companyName" scope="request">
	<div class="outerDiv">
	<html:form action="/hierarchyHome" onsubmit="return submitForm();">
  	<html:hidden property="mode" name="adminForm" value="saveMovedUserToHierarchy"/>
  	<html:hidden property="userId" name="adminForm"/>
  	<html:hidden property="parentId" name="adminForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="common.select"/> <bean:message key="common.user"/> to <bean:message key="common.move"/>&nbsp;:&nbsp; 
			</td>
			<td>
				<script language="JavaScript">	
					var opts = <%=request.getAttribute("jsUserToMove")%>;											
					var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
					userToMove = opt.concat(opts);
					selectBoxUserToMove = new SelectBox(userToMove,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					document.write(selectBoxUserToMove.getHtml());
					selectBoxUserToMove.setOnChangeHandler('onUserToMoveChange');
					selectBoxUserToMove.init();
				</script>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="common.move"/> <bean:message key="common.selected"/> <bean:message key="common.user"/> <bean:message key="common.under"/> <bean:message key="common.user"/> :&nbsp; 
			</td>
			<td>
				<script language="JavaScript">								
					var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
					selectBoxManagedUser = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'220px', size:10, textboxclass:'Grey'});
					document.write(selectBoxManagedUser.getHtml());
					selectBoxManagedUser.init();
				</script>
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
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
</logic:present>
</div>

<script type="text/javascript">

function onUserToMoveChange(){
	var userId = selectBoxUserToMove.getSelectedId();
	var pars = "mode=getXMLAssignTouser&userId=" + userId;
    var myAjax = ajaxCall("hierarchyHome.do",'get',pars,loadManagedUser, reportError);
}

function loadManagedUser(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var content = xmlFile.getElementsByTagName("content")[0].firstChild.nodeValue;
		var opts = eval(content);
		var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
		var m = [new SelectOption('0','<bean:write  name="companyName" scope="request"/>')];
		opt = opt.concat(m);
		managedUser = opt.concat(opts);
		selectBoxManagedUser.reInitialize(managedUser, '-1');	
	}
}

function submitForm(){
	var userId = selectBoxUserToMove.getSelectedId();
	var parentId = selectBoxManagedUser.getSelectedId();
	if(userId==-1){
		alert('Please select -User to Move');
		return false;
	}
	if(parentId==-1){
		alert('Please select -Move Under User');
		return false;
	}
	document.adminForm.userId.value=userId;
	document.adminForm.parentId.value=parentId;
	document.adminForm.submit();
}
function actionOnLoad(){
	window.top.setPopTitle('<bean:message key="common.move"/> <bean:message key="common.user"/> <bean:message key="common.in"/> <bean:message key="common.hierarchy"/>');
}
window.onload=actionOnLoad;

</script>
