<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.user.utils.DataViewUtils"%>
<%@page import="org.apache.struts.Globals"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script type="text/javascript">
var opt = null;
var dashboardPositionSummaryColumns = <%=DataViewUtils.getJSDashboardPositionSummaryArrayForDataView()%>;
if(opt!==null)
	dashboardPositionSummaryColumns = opt.concat(dashboardPositionSummaryColumns);
var column11List= null;
var column12List= null;
</script>
<html:form action="/user" onsubmit="return submitDataConfigForm();">
<html:hidden property="mode" name="userForm" value="saveDataViewConfig"/>
<html:hidden property="t" name="userForm"/>
<html:hidden property="userId" name="userForm"/>
<html:hidden property="column1_1" name="userForm"/>
<html:hidden property="column1_2" name="userForm"/>
<div class="contentDiv">
	<div id="divError" style="display:block">
		<% 
			if(request.getAttribute(Globals.ERROR_KEY)!=null){
		%>
		<script>
			var isError=1;
		</script>
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
		<br/>
		<% } %>
		<logic:notEmpty name="saved" scope="request">
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b><bean:message key="data_view_configuration.message.update.success"/></b>
			    </td>               
				</tr>
			</table>
			<br/>	
		</logic:notEmpty>
		<logic:notEmpty name="reset" scope="request">
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b><bean:message key="data_view_configuration.message.reset.success"/></b>
			    </td>               
				</tr>
			</table>
			<br/>		
		</logic:notEmpty>
	</div>
	<table>
		<tr>
			<td class="header" colspan="2">
				<span>
					<strong><bean:message key="data_view_configuration.message.display.settings"/>:</strong>&nbsp;<bean:message key="dashboard.label.title_dashboard"/>&nbsp;<bean:message key="common.position"/>&nbsp;<bean:message key="common.summary"/>
				</span>
			</td>
		</tr>
	</table>
	<div class="outerDiv" style="height: 25px;">
	<table class="todoBoxHeader" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td style="width: 20px;">&nbsp;</td>  
			<td style="width: 103px;"><bean:message key="common.position"/></td>
			<td style="width: 148px;"><bean:message key="data_view_configuration.coulumn"/> - 2</td>
			<td style="width: 148px;"><bean:message key="data_view_configuration.coulumn"/> - 3</td>
			<td style="width: 70px;" ><bean:message key="common.vacancies"/></td>
			<td style="width: 70px;" ><bean:message key="dashboard.label.hdr.inprocess"/></td>
			<td style="width: 90px;" ><bean:message key="common.pending_offers"/></td>
			<td style="width: 60px;" ><bean:message key="common.joined"/></td>
			<td>&nbsp;</td>
		</tr>
	</table>
	</div>
	<div class="outerDiv" style="height: 30px;border-top: 0px;padding-top: 5px;">
	<table cellpadding="4" cellspacing="0">
		<tr >
			<td style="width: 20px;"  >&nbsp;</td>
			<td style="width: 100px;">N/A</td>
			<td style="width: 148px;" align="left">
            	<script type="text/javascript">
		            column11List = new SelectBox(dashboardPositionSummaryColumns,'<bean:write name="userForm" property="column1_1"/>','images/btn_dropdown.gif',{namesonly:false, width:'128px', size:6});
		            document.write(column11List.getHtml());
		            column11List.init();
                </script>
		   	</td>
	        <td style="width: 148px;" align="left">
            	<script type="text/javascript">
		            column12List = new SelectBox(dashboardPositionSummaryColumns,'<bean:write name="userForm" property="column1_2"/>','images/btn_dropdown.gif',{namesonly:false, width:'128px', size:6});
		            document.write(column12List.getHtml());
		            column12List.init();
                </script>
		   	</td>
		   	<td style="width: 70px;" >N/A</td>
		   	<td style="width: 70px;" >N/A</td>
		   	<td style="width: 90px;" >N/A</td>
		   	<td style="width: 60px;" >N/A</td>
		</tr>
	</table>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="2" width="100%">
				<div class="navBtn" style="width:100%; margin-top:5px;float:left;">
				<a href="#" style="width:75px;" class="active" onclick="javascript:submitDataConfigForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				<a href="#" style="width:75px;margin-left: 5px;" class="active" onclick="javascript:resetuserDataViewConfiguration()"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.reset"/></a>
				</div>
			</td>
		</tr>
	</table>
</div>
</html:form>
<script>
function submitDataConfigForm(){
	if(column11List.getSelectedId() == -1){
		document.userForm.column1_1.value='';
	}else{
		document.userForm.column1_1.value=column11List.getSelectedId();
	}
	
	if(column12List.getSelectedId() == -1){
		document.userForm.column1_2.value='';
	}else{
		document.userForm.column1_2.value=column12List.getSelectedId();
	}
	
	document.userForm.submit();
}
function resetuserDataViewConfiguration(){
	document.userForm.mode.value="resetUserDataViewConfiguration";
	document.userForm.submit();	
}
</script>