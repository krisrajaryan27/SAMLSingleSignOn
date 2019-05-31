<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.talentPool.custom.dataobject.CRColumnCustomFieldMapData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<style type="text/css">
tr.stepsTable {
	border-left: 1px solid #ccc;
	border-right: 1px solid #ccc;
	border-top: 1px solid #ccc;
}
tr.stepsTable td {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight:normal;
	color:#666666;
	background-Color:#D0E4A3;
	border: 0px solid;
	border-color : white white white white;
	text-align: left;
	margin:0px;
	padding:0px 4px 0px 0px ;
	font-weight:normal;
    -moz-user-select:none;
	-moz-user-select:-moz-none;    
    overflow:input type="hidden";
    height:25px;
    empty-cellshow;
}
</style>
</head>
<logic:present name="save" scope="request">	
	<script>
		window.top.hidePopWin(true);
	</script>
</logic:present> 
<logic:notPresent name="save" scope="request">
<body>
<html:form action="/customFieldScreen" method="POST">
<html:hidden property="mode" name="customFieldForm" value="saveCustomFieldMapping"/>				
<html:hidden property="selectedCustomFieldMapping" name="customFieldForm"/>
<script>									
	var opts = <%=request.getAttribute("jsArrayCustomFields")%>;
	var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
    opts = m.concat(opts);
    var columnPropertyArray = new Array();
</script>
<%
	String entityType = request.getParameter("entityType");
	String entity = "1".equals(entityType) ? "Candidate" : "Position";
%>			
<div class="contentDiv">
	<div class="boxTab" style="width:540px;">
		<span class="rightC"></span><span class="leftC"></span>
		<%=entity%> Custom Field Mapping for Summary Reports
	</div>
	<div class="outerDiv" >
		<table width="100%" cellpadding="0" cellspacing="0" id="stepsMappingTable"> 
		  <tr class="stepsTable" >
		  	 <td width="5%">
				  &nbsp;
			  </td>
		  	  <td width="35%" align="left">
				  <bean:message key="admin.custom_field_mapping.label.report_fields" />
			  </td>
			  <td width="60%" align="right">
			  	<%=entity%> <bean:message key="common.custom_fields" />
			  </td>
		  </tr> 
		   <tr>
		  		<td colspan="3">
		   			<div>
		   				<table width="100%">
		   					<logic:iterate id="customField" name="cfArray" type="CRColumnCustomFieldMapData" indexId="index">
								<tr>
									<td style="align:center">
										<bean:write name="customField" property="columnDisplayName" />&nbsp;&nbsp;
									</td>
									<td width="60%">
										<script>
											var columnProperty = '<bean:write name="customField" property="columnProperty" />';
											eval('var selectBoxCustomField_' + columnProperty + ' = new SelectBox(opts,\'<bean:write name="customField" property="customFieldId" />\',\'images/btn_dropdown.gif\',{namesonly:false, width:\'250px\', size:10, textboxclass:\'\'})');
											eval('document.write(selectBoxCustomField_' + columnProperty + '.getHtml())');
											eval('selectBoxCustomField_' + columnProperty + '.init()');
											columnPropertyArray[columnPropertyArray.length] = columnProperty;
										</script>							  
									</td>
								</tr>
							</logic:iterate>
			   			</table>
		   			</div>			   			
		   		</td>
		  </tr>			   
		</table>
	</div>
	<div class="navBtn" style="margin-top: 5px;">
			<a href="#" style="width:80px; margin-left:5px;float: left;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				<a href="#" style="width:80px; margin-left:5px;float: right;" class="active" onclick="javascript: submitForm()"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
	</div> 
</div>
</html:form>
</body>
</logic:notPresent>
<script type="text/javascript">

function submitForm(){
	var selectedMapping = '';
	for(i=0;i<columnPropertyArray.length;i++){
		var property = columnPropertyArray[i];
		var customFieldIdForProperty = eval('selectBoxCustomField_' + property + '.getSelectedId()');
		selectedMapping = selectedMapping + property + '=' + customFieldIdForProperty + '#';
	}
	document.customFieldForm.selectedCustomFieldMapping.value=selectedMapping;
	document.customFieldForm.mode.value = "saveCustomFieldMapping";
	document.customFieldForm.submit();
}

</script>
</html>