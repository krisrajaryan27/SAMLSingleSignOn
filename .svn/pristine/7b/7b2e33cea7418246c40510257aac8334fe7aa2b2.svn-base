<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<s:form name="stepsMigrationStatus" method="POST" action="migrationDisclaimer" >
<div class="contentDiv">
	<table>
		<tr>
		    <td class="header" colspan="2">
		    	<b><s:actionerror /></b>
		    </td>
		</tr>  
	</table>
	<table>
		<tr>
		    <td class="header" colspan="2">
		    	<b><s:actionmessage /></b>
		    </td>
		</tr>  
	</table>
	<div class="boxTab" style="width:140px;"><span class="rightC"></span><span class="leftC"></span><s:text name="steps_migration.label.migration_status" /></div>
	<div class="outerDiv" style="width: 300px;" >
		<table class="posinput" width="100%" style="padding: 15px 0px 15px 30px;"> 
		  <tr>
		  	<td class="label" width="120px">
			 	Open Positions: 
			 </td>
			 <td>
			 	<s:property value="#request['openPositions']"/>
			 </td>
		  </tr>
		  <tr>
		  	<td class="label" width="120px">
				Closed Positions: 
			 </td>
			 <td>
			 	<s:property value="#request['closedPositions']"/>
			 </td>
		  </tr>
		  <tr>
		  	<td class="label" width="120px"> 
			 	Position Templates:
			 </td>
			 <td>
			 	<s:property value="#request['positionTemplates']"/>
			 </td>
		  </tr> 
		</table>
		<div class="navBtn" style="float: right;margin-top: 5px;">
			<s:if test="#request['canMigrate']==true">
				<a href="#" style="width:120px; margin-left:5px;" class="active" onclick="javascript: migrate();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="steps_migration.button.migrate"/></a>
			</s:if>
		</div>
	</div> 
</div>
</s:form>
</body>
<script type="text/javascript">
function migrate(){
	document.stepsMigrationStatus.submit();
}
</script>
</html>
