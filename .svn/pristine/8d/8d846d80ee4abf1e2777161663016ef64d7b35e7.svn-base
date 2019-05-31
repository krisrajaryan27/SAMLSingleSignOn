<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>
<%@page import="com.talentPool.reportDesign.utils.ReportDesignUtils"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script type="text/javascript">
var columns = <%=(String) request.getAttribute("columns")%>;
</script>
<html:form action="/reportTemplates" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportTemplatesForm"/>
<html:hidden property="templateColumns" name="reportTemplatesForm"/>
<html:hidden property="reportFilePath" name="reportTemplatesForm"/>
<html:hidden property="reportId" name="reportTemplatesForm"/>
<html:hidden property="sheetIndex" name="reportTemplatesForm"/>
<html:hidden property="rowIndex" name="reportTemplatesForm"/>
<html:hidden property="originalFileName" name="reportTemplatesForm"/>
<html:hidden property="reportTemplateId" name="reportTemplatesForm"/>
<html:hidden property="sheetsArray" name="reportTemplatesForm"/>
<div class="contentDiv">
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:200px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" /><bean:message key="report_templates.header.template_column_mapping" /></div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
  	 <tr>				  			
		<td class="head" colspan="2">
			<b><bean:message key="report_templates.label.template_details"/></b>
		</td>
	</tr>
	<tr>
		<td style="width:200px;">
			<bean:message key="report_master.label.template_name"/>
			<span class="star">*</span>
		</td>
		<td>
			<html:text property="templateName" name="reportTemplatesForm" />
		</td>
	</tr>	    
	<tr>				  			
		<td class="head" colspan="2">
			<b><bean:message key="report_templates.header.column_mapping"/></b>
		</td>
	</tr>
   </table>
   <div class="outerDiv" style="padding:0px 0px 0px 0px;border-top:none;">
		<table cellspacing="2" cellpadding="5" class="boxContent" style="border:0px;width: 100%">	
		<logic:notEmpty name="excelFieldName" scope="request">
			<logic:iterate id="names" name="excelFieldName" scope="request" indexId="counter">
				<bean:size id="excelFieldNameSize" name="excelFieldName" scope="request" />
				<tr>
				  <td style="width:200px;"><bean:write name="names" /></td>	
				   <td>
				   <script type="text/javascript">
	                    var selectColumn_<%=counter%> = new SelectBox(columns,'','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
	                    document.write(selectColumn_<%=counter%>.getHtml());
	                    selectColumn_<%=counter%>.init();
                	</script>
				   </td>		  
				</tr>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="excelFieldName" scope="request">
			<tr>
				<td style="border-bottom: 0px;">
					<bean:message key="report_templates.error.no_columns" />
				</td>
			</tr>
		</logic:empty>		   
		</table>
	</div>
	<br/>
	<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="navBtn" style="float: right;">
					<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
					<logic:notEmpty name="excelFieldName" scope="request">
						<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: saveReportTemplate();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
					</logic:notEmpty>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>	
</div>
</html:form>
<script type="text/javascript">
function previousPage(){
	document.reportTemplatesForm.mode.value="addReportTemplate";
	document.reportTemplatesForm.submit();
}

function saveReportTemplate(){
	if(validateForm()){
		document.reportTemplatesForm.templateColumns.value=selectedColumnIds();
		document.reportTemplatesForm.mode.value="saveReportTemplate";
		document.reportTemplatesForm.submit();
	}
}

function validateForm(){
	if(document.reportTemplatesForm.templateName.value==''){
		alert('<bean:message key="report_templates.error.mandatory_fields" />');
		return false;
	}
	return true;
}

function selectedColumnIds(){	
	var colm = '';
	<logic:iterate id="names" name="excelFieldName" scope="request" indexId="counter">
		var obj = eval(selectColumn_<%=counter%>);
		if(colm!=''){
			colm +=',';
		}
		colm += obj.getSelectedId();
	</logic:iterate> 
	return colm;
}

function onCancel(){
	window.location.href="reportTemplates.do?mode=manageReportTemplates";
}

function onWindowLoad(){
	var columnListSize =0;
	<logic:notEmpty name="excelFieldNameSize" >
		columnListSize = '<bean:write name="excelFieldNameSize" />';
	</logic:notEmpty>	 
	<logic:notEmpty name="columnDataList" scope="request">
		<logic:iterate id="columnData" name="columnDataList" type="com.talentPool.reportDesign.dataobject.ColumnData" scope="request" indexId="counter" >
			if(<%=counter%><columnListSize){
				var columnId = '<bean:write name="columnData" property="columnName" />';
				var obj = eval(selectColumn_<%=counter%>);
				obj.setSelected(obj.getIndexWithId(columnId));
			}
		</logic:iterate>
	</logic:notEmpty>
}

window.onload=onWindowLoad;
</script>
