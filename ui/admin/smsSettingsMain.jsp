<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.talentPool.admin.AdminConstants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.admin.form.AdminForm"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<script src="js/scripta/lib/prototype.js"></script>


<html:form action="/adminHome">
<html:hidden property="mode" value="saveSmsSettings"/>
<html:hidden property="property" name="adminForm"/>
<html:hidden property="isdefault" name="adminForm"/>
<%
	String smsEnabled = (String)request.getAttribute(GlobalConstants.PROPERTY_SMS_ENABLED);
	smsEnabled = Utils.isBlankOrNull(smsEnabled)?"":smsEnabled;
%>
<input type="hidden" name="<%=GlobalConstants.PROPERTY_SMS_ENABLED %>" value="<%=smsEnabled %>"/>

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
	<br>
	<% } %>
	<%
		String saved = (String)request.getAttribute("saved");
		if(saved !=null){
	%>
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b>
			        <bean:message key="admin.sms_settings.label.settings_updated_successfully"/>			        
			        </b>
			    </td>               
				</tr>
			</table>
			<br>
	<%
		}
	%>
	</div>
</div>
<div class="contentDivPop" style="padding-right:20px;">
	<table width="100%" class="boxHeader" style="margin-top:5px;" cellspacing="0" cellpading="0">
		<tr>
			<td class="header" height="18"><strong><bean:message key="admin.sms_settings.label.title.sms_settings"/></strong></td>
		</tr>
	</table>
	<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
		<table border="0" cellspacing="0" cellpadding="0" class="posinput">
			<tr>
			  <td class="label">
			    <bean:message key="admin.sms_settings.label.sms_enabled"/>
			  </td>
			  <td ></td>
			  <td>
				  <% if("1".equals(smsEnabled)){ %>
				  		<img src="images/checkboxchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SMS_ENABLED %>);" />
				  <%}else{ %>
				  		<img src="images/checkboxunchecked.gif" id="remindMe" name="remindMe" 
				  		onclick="changeCheckboxState(this, document.adminForm.<%=GlobalConstants.PROPERTY_SMS_ENABLED %>);" />
				  <%} %>
			    
			  </td>
			</tr>
			<tr>
			  <td class="label">
			    <bean:message key="admin.sms_settings.label.sms_provider"/>
			  </td>
			  <td ></td>
			  <td>
			    <html:text property="provider"  name="adminForm" size="91" />  
			  </td>
			</tr>
			<tr>
			  <td class="label">
			    <bean:message key="admin.sms_settings.label.sms_url"/>
			  </td>
			  <td ></td>
			  <td>
			    <html:textarea property="value"  name="adminForm" rows="5" cols="90"/>  
			  </td>
			</tr>
		</table>
	</div>
</div>
<div class="navBtn" style="padding-left:23px;margin-top:5px;"><a href="#" style="width:60px;" class="active" onclick="javascript:submit();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
</div>
</html:form>

<script language="javascript">
function changeCheckboxState(chkBox, fld){
	var prevId = fld.value;
	if(prevId!='1'){
		fld.value='1';
		chkBox.src='images/checkboxchecked.gif';
	}else{
		fld.value='0';
		chkBox.src='images/checkboxunchecked.gif';
	}
}

function submit(){
	var frm=document.adminForm;
	frm.submit();
}
</script>