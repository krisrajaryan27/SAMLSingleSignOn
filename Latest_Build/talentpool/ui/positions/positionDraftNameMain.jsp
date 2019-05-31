<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.talentPool.positions.PositionConstants"%>
<%@ page import="com.talentPool.positions.utils.PositionDraftUtils"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="com.talentPool.common.utils.Utils"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>

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
		<html:form action="/drafts" onsubmit="submitForm();return false;">
		<html:hidden property="mode" value="changePositionDraftName"/>
	  	<html:hidden property="draftId" name="positionDraftForm"/>
			
			<div class="popupTop">
				<table class="tblPop">
					   <tr>
						  <td class="header">
							  <bean:message key="position.draft.position_draft_name"/>:
						  </td>
						  <td>
							<html:text name="positionDraftForm" property="fileName" size="70" />
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

<script language="javascript">

function submitForm(){
	if (validateForm()) {
		document.positionDraftForm.submit();
	}
}

function validateForm(){
	var draftName = document.positionDraftForm.fileName.value;
	if(draftName==""){
		alert('<bean:message key="position.draft.message.please_enter_name" />');
		return false;
	} else {
	  	var re = new RegExp('\\b'+'<bean:message key="common.position_draft" />'+'\\b \\b\\d{1,2}\\b');
		if (draftName.match(re)) {
		  alert('<bean:message key="position.draft.error.rename_draft" />');
		  return false;		  
		}
	}
	return true;
}

function actionOnLoad(){
		var title = "<b>Change Position Draft Status: " + "<%=Utils.escapeHTML((String)request.getAttribute("draftName"))%>" + "</b>";
		window.top.setPopTitle(title);
}
window.onload=actionOnLoad;
</script>