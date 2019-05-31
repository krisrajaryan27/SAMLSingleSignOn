<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<logic:present name="update" scope="request">
<script language="JavaScript">
	window.top.hidePopWin(true);
</script>
</logic:present> 
<logic:notPresent name="update" scope="request">               
<script language="javascript">
var checkboxListCategories=null;
</script>
<div class="contentDivPop">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
		<table  id="m_errortable" > 
			<tr>
			    <td class="header">
			        <b><bean:message key="errors.following_errors"/></b>
			    </td>               
			</tr>
		    <tr>
		        <td class="message"><html:errors/></td>               
		    </tr>
		</table>
		<br>
	<% } 

	%>
	
	<html:form action="/selectionProcess" >
	<html:hidden property="mode" name="selectionProcessForm"/>
	<html:hidden property="selectedIds" name="selectionProcessForm"/>
	<html:hidden property="tristateIds" name="selectionProcessForm"/>
	<html:hidden property="applicantId" name="selectionProcessForm"/>
	<html:hidden property="applicantIds" name="selectionProcessForm"/>
	<table cellspacing="0" cellpadding="0">
		<tr>
			<td style="padding-bottom:10px;font-weight: bold; ">
				<bean:message key="set_flag.label.info"/>
			</td>
		</tr>
		<tr>
			<td>
				<script type="text/javascript">
					var opts = <bean:write name="selectionProcessForm" property="jsArrayFlags" filter="false"/>;
					checkboxListCategories = new CheckBoxList(opts,'<bean:write name="selectionProcessForm" property="selectedIds"/>',{namesonly:false, layerclass:'checkboxlistdiv', width:'350px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif', tristatecheckedimg:'images/checkboxtristate.gif', tristateids:'<bean:write name="selectionProcessForm" property="tristateIds"/>', contentImgClass:'contentImgClass'});
					document.write(checkboxListCategories.getHtml());
					checkboxListCategories.init();
				</script>
			</td>
		</tr>
		<tr>
			<td>
				<div class="navBtn" style="float:right;margin-left:5px;margin-top:5px;"><a href="#" style="width:70px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
				<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a></div>
			</td>
		</tr>
	</table>
	</html:form>
</div>
<script>
function submitForm(){
	document.selectionProcessForm.selectedIds.value=checkboxListCategories.getSelectedIds();
	document.selectionProcessForm.tristateIds.value=checkboxListCategories.getTristateIds();
	document.selectionProcessForm.submit();
}
function doOnLoad() {  	
	window.top.setPopTitle("<b><bean:message key="set_flag.label.set_flag"/></b>");
}

window.onload = doOnLoad;
</script>
</logic:notPresent> 