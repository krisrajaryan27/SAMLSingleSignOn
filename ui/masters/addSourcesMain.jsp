<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.admin.AdminConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>
<script>
	var chkedCheckBox = "images/checkboxchecked.gif";
	var unChkedCheckBox = "images/checkboxunchecked.gif";
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
	<html:form action="/masters" onsubmit="submitForm();return false;">
  	<html:hidden property="sourceTypeId" name="mastersForm"/>
  	<html:hidden property="sourceId" name="mastersForm"/>
  	<html:hidden property="sendEmailToSource" name="mastersForm"/>
  	<html:hidden property="sendSMSToSource" name="mastersForm"/>
  	<html:hidden property="sourceBlacklisted" name="mastersForm"/>
  	<html:hidden property="lockInPeriodOnImport" name="mastersForm"/>
  		
  	<html:hidden property="mode" name="mastersForm"/>
  	<html:hidden property="subMode" name="mastersForm"/>
  	
	<div class="popupTop">
		<table class="tblPop">
		<tr>
			<td class="header">
				<bean:message key="admin_master_source.label.source_name"/>
				<span class="star">*</span>
				:&nbsp; 
			</td>
			<td>
				<html:text property="sourceName" name="mastersForm" size="35" styleId="sourceName"></html:text>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="admin_master_source.label.email"/> :&nbsp; 
			</td>
			<td>
				<html:text property="email" name="mastersForm" size="35" styleId="email"></html:text>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="admin_master_source.label.phone"/> :&nbsp; 
			</td>
			<td>
				<html:text property="phone" name="mastersForm" size="35" styleId="phone"></html:text>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="admin_master_source.label.mobile"/> :&nbsp; 
			</td>
			<td>
				<html:text property="mobile" name="mastersForm" size="35" styleId="mobile"></html:text>
			</td>
		</tr>
		<logic:equal name="mastersForm" property="sourceCategoryType" value="<%=AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL%>">
		<tr>
			<td class="header">
				<bean:message key="admin_master_source.label.employee_code"/> :&nbsp; 
			</td>
			<td>
				<html:text property="employeeCode" name="mastersForm" size="35" styleId="employeeCode"></html:text>
			</td>
		</tr>
		</logic:equal>
		<tr style="height:20px;">
			<td class="header">
				<bean:message key="admin_master_source.label.send_email_to_source"/> :&nbsp; 
			</td>
			<td>
				<img src="" name="sendEmailToSource" onclick="javascript: toggleChkBox(this);"/>				
	      	  	<logic:equal property="sendEmailToSource" name="mastersForm" value="1">	      	  	
	      	  	<script>
	      	  		document["sendEmailToSource"].src = chkedCheckBox;
	      	  	</script>
	      	  	</logic:equal>
	      	  	<logic:notEqual property="sendEmailToSource" name="mastersForm" value="1">
	      	  	<script>
	      	  		document["sendEmailToSource"].src = unChkedCheckBox;
	      	  	</script>
	      	  	</logic:notEqual>
			</td>
		</tr>
		<tr style="height:20px;">
			<td class="header">
				<bean:message key="admin_master_source.label.send_sms_to_source"/> :&nbsp; 
			</td>
			<td>
				<img src="" name="sendSMSToSource" onclick="javascript: toggleChkBox(this);"/>				
	      	  	<logic:equal property="sendSMSToSource" name="mastersForm" value="1">
	      	  	<script>
	      	  		document["sendSMSToSource"].src = chkedCheckBox;
	      	  	</script>
	      	  	</logic:equal>
	      	  	<logic:notEqual property="sendSMSToSource" name="mastersForm" value="1">
	      	  	<script>
	      	  		document["sendSMSToSource"].src = unChkedCheckBox;
	      	  	</script>
	      	  	</logic:notEqual>
			</td>
		</tr>
		<logic:notEqual name="mastersForm" property="sourceCategoryType" value="<%=AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL%>">
			<tr style="height:20px;">
				<td class="header">
					<bean:message key="admin_master_source.label.blacklisted"/> :&nbsp; 
				</td>
				<td>
					<img src="" name="sourceBlacklisted" onclick="javascript: toggleChkBox(this);"/>
					<logic:equal property="sourceBlacklisted" name="mastersForm" value="<%= AdminConstants.SOURCE_BLACKLISTED%>">				
			      	  	<script>
			      	  		document["sourceBlacklisted"].src = chkedCheckBox;
			      	  	</script>
		      	  	</logic:equal>
		      	  	<logic:notEqual property="sourceBlacklisted" name="mastersForm" value="<%= AdminConstants.SOURCE_BLACKLISTED%>">
			      	  	<script>
			      	  		document["sourceBlacklisted"].src = unChkedCheckBox;
			      	  	</script>
		      	  	</logic:notEqual>
				</td>
			</tr>
		</logic:notEqual>
		<tr>
			<td class="header">
				<bean:message key="admin_master_source.label.lock_in_period_on_import"/> :&nbsp; 
			</td>
			<td>
				<script>
					var opts = new Array();
					opts[opts.length] = new SelectOption('0','None');
					opts[opts.length] = new SelectOption('3','3 Months');
					opts[opts.length] = new SelectOption('6','6 Months');
					opts[opts.length] = new SelectOption('9','9 Months');
					opts[opts.length] = new SelectOption('12','12 Months');	
					opts[opts.length] = new SelectOption('15','15 Months');	
					opts[opts.length] = new SelectOption('18','18 Months');	
					var selectBoxLockInPeriod = new SelectBox(opts,'<bean:write property="lockInPeriodOnImport" name="mastersForm" />','images/btn_dropdown.gif',{namesonly:false, width:'188px', size:20});
					document.write(selectBoxLockInPeriod.getHtml());
                    selectBoxLockInPeriod.init();
				</script>
			</td>
		</tr>
		<tr>
			<td class="header">
				<bean:message key="admin_master_source.label.vendor_resume_limit"/>
				:&nbsp; 
			</td>
			<td>
				<html:text property="sourceCvLimit" name="mastersForm" size="35" styleId="sourceCvLimit"></html:text>
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
<!--
function submitForm(){
	var Name = document.mastersForm.sourceName.value;
	if(Name.trim()==""){
		alert("Please enter Source name");
		return;
	}
	document.mastersForm.sendEmailToSource.value=getChkBoxValue(document["sendEmailToSource"]);
  	document.mastersForm.sendSMSToSource.value=getChkBoxValue(document["sendSMSToSource"]);
  	<logic:notEqual name="mastersForm" property="sourceCategoryType" value="<%=AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL%>">
  		document.mastersForm.sourceBlacklisted.value=getBlackListValue();
  	</logic:notEqual>
  	document.mastersForm.lockInPeriodOnImport.value=selectBoxLockInPeriod.getSelectedId();
	document.mastersForm.submit();
}
function actionOnLoad(){
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_ADD%>">
		window.top.setPopTitle('<b>Add Source</b>');
	</logic:equal>
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_EDIT%>">
		window.top.setPopTitle('<b>Edit Source</b>');
	</logic:equal>
}
window.onload=actionOnLoad;

function toggleChkBox(elem) {
	if (elem.src.indexOf(chkedCheckBox) == -1) {
		elem.src = chkedCheckBox;
	} else {
		elem.src = unChkedCheckBox;
	}
	return false;	
}

function getChkBoxValue(elem) {
	if (elem.src.indexOf(chkedCheckBox) == -1) {
		return 0;
	} else {
		return 1;
	}
}
function getBlackListValue(){
	var val = getChkBoxValue(document["sourceBlacklisted"]);
	if(val == 1){
		val = '<%=AdminConstants.SOURCE_BLACKLISTED%>';
	}else{
		val = '<%=AdminConstants.SOURCE_NOT_BLACKLISTED%>';
	}
	return val;
}
//-->
</script>
