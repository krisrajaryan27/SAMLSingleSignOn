<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.desktop.constants.DesktopConstants"%>
<div class="contentDivPop" >
	<table cellpadding="0" cellspacing="0" class="boxETab">
	  <tr>
		  <td class="leftC"></td>
		  <td class="content"><bean:message key="common.message"/></td>
		  <td class="rightC"></td>
	  </tr>
  	</table>  
	<div class="outerDiv" style="padding:20px;">

	<html:form action="/importResume">
		<html:hidden property="result" name="applicantForm"/>
		<html:hidden property="applicantName" name="applicantForm"/>
	</html:form>
	<logic:empty name="applicantForm" property="result">
	Unable to <bean:message key="common.import"/>
	</logic:empty>
	<logic:notEmpty name="applicantForm" property="result">
	<logic:equal value="<%=DesktopConstants.SUCCESS %>" name="applicantForm" property="result">
	Successfully <bean:message key="common.imported"/> / <bean:message key="common.updated"/> <bean:write name="applicantForm" property="applicantName"/> in <bean:message key="title.common"/>.
	<br/>
	</logic:equal>
	Click 'Done' button at the bottom of the window.
	</logic:notEmpty>
	</div>
	
	<div class="outerDiv" style="width:500px;height:40px; border: none;">
		<table class="tabinput" width="100%">
			<tr>
				<td class="vGap"></td>
			</tr>
			<tr>
				<td align="right">
				<logic:notEmpty name="applicantForm" property="resultId">
				<div class="navBtn" style="float: right; ">
					<a href="#" style="width:60px;" class="active"
						onclick="javascript: onDone();" id="submit"><span class="rightC"></span><span
						class="leftC"></span><bean:message key="common.done" /></a>
				</div>
				</logic:notEmpty>	
				</td>
			</tr>
		</table>
	</div>
</div>

<script>
function onDone(){
	window.top.refreshGrid(); 
	window.top.hidePopWin(true);
	
}
function onOnPopUpLoad() {
	<logic:notEmpty name="commentUrl" scope="request">
		var el = document.createElement("iframe");
		el.style.width="0px";
		el.style.height="0px";
		el.setAttribute('frameBorder','0');			
		document.body.appendChild(el);
		var url = '<bean:write name="commentUrl" scope="request"/>'.replace(/&amp;/g, '&');
		el.setAttribute('src', url);
	</logic:notEmpty>
}
window.onload = onOnPopUpLoad;
</script>