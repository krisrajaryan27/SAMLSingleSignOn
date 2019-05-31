<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.reportDesign.utils.ReportDesignUtils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script type="text/javascript">
var selectReportType=null;
</script>

<html:form action="/reportDesigner" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportDesignForm"/>
<html:hidden property="reportId" name="reportDesignForm"/>
<html:hidden property="reportType" name="reportDesignForm"/>
<html:hidden property="reportName" name="reportDesignForm"/>
<html:hidden property="columns" name="reportDesignForm"/>
<html:hidden property="reportFormat" name="reportDesignForm"/>
<html:hidden property="sortBy" name="reportDesignForm"/>
<html:hidden property="sortWith" name="reportDesignForm"/>
<html:hidden property="filters" name="reportDesignForm"/>
<html:hidden property="reportDescription" name="reportDesignForm"/>
<html:hidden property="isSharedReport" name="reportDesignForm"/>
<html:hidden property="levelPermissions" name="reportDesignForm"/>
<html:hidden property="reportFilePath" name="reportDesignForm"/>
<html:hidden property="sheetIndex" name="reportDesignForm"/>
<html:hidden property="rowIndex" name="reportDesignForm"/>

<div class="contentDiv">
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Report Type</div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>				  			
		<td class="head">
			<b>Select the type of data you wish to report on</b>
		</td>
	</tr>
	<tr>				  			
		<td>
			 <table class="innerReport">  			 	
				<tr>
  			 		<td>
						<table cellspacing="0" cellpadding="0">
							<tr>
							  <td class="header" width="110px;">
								  <bean:message key="reportdesigner.design_new.report_type.label"/><span class="star">*&nbsp;</span>
							  </td>
							 
							  <td>
							  		<select  id="reportTypeSelect" size="10"  style="width: 250px; border:1px solid #99CC01;" >				
							  		 <%
							  				LinkedHashMap reportTypesMap = ReportDesignUtils.getJSArrayForReportType();
											Iterator listItr = reportTypesMap.keySet().iterator();
											while(listItr.hasNext()){
												String columnKey = (String)listItr.next();
												String reportTypeName =(String)reportTypesMap.get(columnKey);												
									%>			  			
									   		<option value="<%=columnKey %>"><%= reportTypeName%></option>												   			
									<%
											}									    
									%>				
									</select>							  
							  </td>
						  </tr>
					  </table>		  			  
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
					<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
					<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>	
</div>
</html:form>	

<script type="text/javascript">

function validateReportIsSelected(reportType){
	if(reportType==null || reportType==''){
		alert("Please select "+'<bean:message key="reportdesigner.design_new.report_type.label" />');
		return false;
	}
	return true;
}

function previousPage(){
	document.reportDesignForm.mode.value="designNewReport";
	document.reportDesignForm.submit();
}

function nextPage(){
	var reportType = document.getElementById('reportTypeSelect').value;
	if(validateReportIsSelected(reportType)){
		document.reportDesignForm.reportType.value=reportType;
		document.reportDesignForm.mode.value="selectColumns";
		document.reportDesignForm.submit();
	}
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function onWindowLoad(){
	document.getElementById('reportTypeSelect').value=document.reportDesignForm.reportType.value;
}

window.onload=onWindowLoad;
</script>

