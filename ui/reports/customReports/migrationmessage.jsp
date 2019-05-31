<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<script src="js/scripta/lib/prototype.js"></script>
</head>
<body>
<s:form action="runReport" method="POST">
<s:hidden name="reportId" />
<s:hidden name="migrationMessageFlag" />
<div class="contentDiv">
 <s:set name="ROLE_ADMIN" value="@com.talentPool.user.UserConstants@ROLE_ADMIN" id="ROLE_ADMIN" />
	<div class="outerDiv" style="width: 500px;" >
		<table class="posinput" width="100%" style="padding: 15px 0px 15px 30px;"> 
		  <tr>
		  	<td>
			 	<s:text name="steps_migration.message.report_migration_message" /> 
			 </td>
		  </tr>
		  <s:if test="#session['userRoles'] == #ROLE_ADMIN">
			  <tr>
			  	<td>
				  	<s:text name="steps_migration.message.report_migration_message_link_message" />&nbsp;<a href="stepsMigrationStatus.action" class="green" ><s:text name="common.click_here" /></a>
				 </td>
			  </tr>
			  <tr>
			  	<td>
				  	<img class="checkBoxImg" id="migrationMessageFlagImg" src="images/checkboxunchecked.gif" style="cursor: pointer;" />&nbsp;<s:text name="steps_migration.message.report_migration_do_not_show_message" />
				 </td>
			  </tr>
		  </s:if>
		</table>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<a href="#" style="width:120px; margin-left:5px;" class="active" onclick="javascript: saveMigrationMessageFlag();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.ok"/></a>
		</div>
	</div> 
</div>
</s:form>
</body>
<script type="text/javascript">
var chkedCheckBox="images/checkboxchecked.gif";
var unChkedCheckBox="images/checkboxunchecked.gif";

Event.observe(window, "load", function() {	
	$$("img.checkBoxImg").each(function(element) {
		element.observe("click", changeCheckBoxState)
	});
});

function changeCheckBoxState(event){	
	var imgElem = event.element();	
	if(imgElem.src.indexOf(chkedCheckBox)!=-1){
		imgElem.src=unChkedCheckBox;	
	}else if(imgElem.src.indexOf(unChkedCheckBox)!=-1){
		imgElem.src=chkedCheckBox;
	}
}

function saveMigrationMessageFlag(){
	if($('migrationMessageFlagImg')) {
		if($('migrationMessageFlagImg').src.indexOf(chkedCheckBox)!=-1){
			document.runReport.migrationMessageFlag.value='0';
		}else if($('migrationMessageFlagImg').src.indexOf(unChkedCheckBox)!=-1){
			document.runReport.migrationMessageFlag.value='1';
		}
	}
	document.runReport.action="saveMigrationMessageFlag.action";
	document.runReport.submit();
}

</script>
</html>