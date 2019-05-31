<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.talentPool.reports.ReportUtils"%>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript">
var reportLevelCheckboxes=null;
</script>
<div class="contentDiv">
   <s:form method="POST" action="saveReport" >
   <%@include file="include/commonReportHiddenFields.jspf" %>
   <s:hidden name="reportLevelIds"/>
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif"  />Save Report</div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>				  			
		<td class="head">
			<b>Save report text</b>
		</td>
	</tr>
	<tr>				  			
		<td>
			 <table class="innerReport">  
			 	<tr>
				 	<td class="header" width="120px;">
				 	 	<s:label key="custom_report.save.report.name" /><span class="star">*&nbsp;</span>
				 	</td>
				 	<td><s:textfield name="reportTitle" id="reportTitle" size="70"/></td>
			 	</tr>
			 	<tr>
				 	<td class="header" style="vertical-align: top;">
				 	 	<s:label key="custom_report.save.report.desc" />
				 	</td>
				 	<td><s:textarea name="reportDesc" rows="2" cols="67"/></td>
			 	</tr>
			 	<tr>
					<td class="header" style="vertical-align: top;">
						<s:label key="common.report_level" />
					</td>
					<td>
						<script type="text/javascript">
							var opts = <%=ReportUtils.getJSArrayReportLevel()%>;
							reportLevelCheckboxes = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'175px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
							document.write(reportLevelCheckboxes.getHtml());
							reportLevelCheckboxes.init();
						</script>
					</td>
				</tr>
			 </table>
		 </td>
	 </tr>
	</table>
	<br/>
	<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="navBtn" style="float: right;">
					<a href="#" style="width:50px;cursor: pointer; " class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><s:label key="common.back"/></a>
					<a href="#" style="width:90px; margin-left:5px;cursor: pointer;" class="active" onclick="javascript: saveAs();"><span class="rightC"></span><span class="leftC"></span><s:label key="common.save"/></a>
					<a href="#" style="width:60px; margin-left:5px;cursor: pointer;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:label key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>	
	</s:form>
</div>
<script>
function previousPage(){
	document.saveReport.action="selectFilters.action";
	document.saveReport.submit();
}

function save(){
	if(validateName()) {
		document.saveReport.action='editReport.action';
		document.saveReport.submit();
	}
}

function saveAs(){
	if(validateName()) {
		if(reportLevelCheckboxes!=null){
			var levelIds = reportLevelCheckboxes.getSelectedIds();		
			document.saveReport.reportLevelIds.value=levelIds;
		}	
	
		document.saveReport.action='saveAsNewReport.action';	
		document.saveReport.submit();
	}
}

function validateName() {
	var reportName = $("reportTitle").value;
	if(reportName == null || reportName.trim() == "") {
		alert('<s:text name="custom_report.error.enter_name" />');
		return false;
	}
	return true;
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}
</script>