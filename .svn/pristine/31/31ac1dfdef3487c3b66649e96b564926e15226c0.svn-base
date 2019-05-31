<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<s:form action="stepsMigrationWizard" method="POST" name="migrationDisclaimer">
	<div class="contentDiv" >
	<s:if test="hasActionErrors()">
		<table id="m_errortable">
			<tr>
				<td class="header" style="padding: 4px">
					Error
				</td>
			</tr>  
			<tr>
			   <td style="padding: 2px">
			    	<s:actionerror />
			    </td>
			</tr>  
		</table>
	</s:if>	
	<div class="outerDiv" style="margin-top: 5px;">	
		<table>
			<tr>
			    <td>
			    	<s:text name="steps_migration.message.discalimer"></s:text>
			    </td>
			</tr>  
		</table>
	</div>
	</div>
	<div class="navBtn" style="float: right;margin-top: 5px;">
		<a href="#" style="width:120px; margin-left:5px;" class="active" onclick="javascript: migrate();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="steps_migration.button.proceed"/></a>
	</div>
</s:form>
</body>
<script type="text/javascript">
function migrate(){
	document.migrationDisclaimer.submit();
}
</script>
</html>