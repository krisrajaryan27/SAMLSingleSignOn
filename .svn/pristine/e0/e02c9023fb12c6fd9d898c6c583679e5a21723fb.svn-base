<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.reportDesign.utils.ReportDesignUtils"%>


<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script type="text/javascript">
var selectReportType=null;
var selectGroupBy=null;
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
	    <img src="images/blank_small.gif" align="absmiddle" />Report Format</div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
	<tr>				  			
		<td colspan="3" class="head">
			<b>Select the format of the report</b>
		</td>
	</tr>
   	<tr>
		<logic:equal name="reportDesignForm" property="reportFormat" value="<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>">
			<td onclick="javascript:onRadioChange('imgFormat','<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>');">
				<img src="images/checkedradiobutton.gif" name='imgFormat' id='img_<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>'  style="margin-bottom:-1px;"/>&nbsp;<bean:message key="reportdesigner.format.tabular"/>
			</td>
			<td onclick="javascript:onRadioChange('imgFormat','<%=ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED%>');">
				<img src="images/radiobutton.gif" name='imgFormat' id='img_<%=ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED%>'  style="margin-bottom:-1px;"/>&nbsp;<bean:message key="reportdesigner.format.pre_formatted"/>
			</td>	
		</logic:equal>
		<logic:equal name="reportDesignForm" property="reportFormat" value="<%=ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED%>">
			<td onclick="javascript:onRadioChange('imgFormat','<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>');" >
				<img src="images/radiobutton.gif" name='imgFormat' id='img_<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>' style="margin-bottom:-1px;"/>&nbsp;<bean:message key="reportdesigner.format.tabular"/>
			</td>	
			<td onclick="javascript:onRadioChange('imgFormat','<%=ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED%>');" >
				<img src="images/checkedradiobutton.gif" name='imgFormat' id='img_<%=ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED%>' style="margin-bottom:-1px;"/>&nbsp;<bean:message key="reportdesigner.format.pre_formatted"/>
			</td>	
		</logic:equal>
		<logic:equal name="reportDesignForm" property="reportFormat" value="">
			<td onclick="javascript:onRadioChange('imgFormat','<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>');" >
				<img src="images/radiobutton.gif" name='imgFormat' id='img_<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>' style="margin-bottom:-1px;"/>&nbsp;<bean:message key="reportdesigner.format.tabular"/>
			</td>	
			<td onclick="javascript:onRadioChange('imgFormat','<%=ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED%>');" >
				<img src="images/radiobutton.gif" name='imgFormat' id='img_<%=ReportDesignConstants.REPORT_FORMAT_PRE_FORMATTED%>' style="margin-bottom:-1px;"/>&nbsp;<bean:message key="reportdesigner.format.pre_formatted"/>
			</td>	
		</logic:equal>
	</tr>
	<tr>
		<td style="border-top: 0px;">
			<img src="images/reporttabularstyle.gif">
		</td>
		<td style="border-top: 0px;">
		<img src="images/reportmatrixstyle.gif">
		</td>
		<!-- td style="border-top: 0px;">
		<img src="images/reportmatrixstyle.gif">
		</td -->
	</tr>
	<tr>
		<td style="border-top: 0px;">
			Tabular reports are the simplest and fastest way to list your data.
		</td>
		<td style="border-top: 0px;">
			Pre formatted reports are pre defined user reports.
		</td>
		<!--td style="border-top: 0px;">
			Summary reports list your data with subtotals and other summary information.
		</td>
		<td style="border-top: 0px;">
			Matrix reports list summaries of your data in a grid against both horizontal and vertical criteria.
		</td -->
	</tr>
   </table>
	<br/>
	<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="navBtn" style="float: right;">
					
					<a href="#" style="width:50px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>
	
</div>
</html:form>	

<script type="text/javascript">

function nextPage(){
	document.reportDesignForm.mode.value="selectReportType";
	document.reportDesignForm.submit();
}
	
function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}


function onRadioChange(imgGroupName, attachmentId){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img") > -1) {
			if( theImage.id == 'img_'+attachmentId){
				theImage.src = "images/checkedradiobutton.gif";
				document.reportDesignForm.reportFormat.value=attachmentId;
			}else{
				theImage.src = "images/radiobutton.gif";
			}
		}
	}
}

function onWindowLoad(){
	if(document.reportDesignForm.reportFormat.value==''){
		document.reportDesignForm.reportFormat.value='<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>';
		//document.getElementById('img_<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>').src = "images/checkedradiobutton.gif";
	}
}

window.onload=onWindowLoad;
</script>

