<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%@page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.reportDesign.utils.ReportDesignUtils"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>
<%@page import="com.talentPool.reportDesign.report.factory.ReportTypeFactory"%>
<%@page import="com.talentPool.reportDesign.report.ReportTypes"%>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>

<script type="text/javascript">
var checkBoxFilters = null;
</script>

<style>
.selectFilter {border:1px solid #99CC33; border-top:none;}
.selectFilter TD.head{padding:2px 4px 3px 8px;border-bottom:1px solid #99CC33;border-top:1px solid #99CC33; color:#666666; font-weight: bold; background:#D0E4A3;}
</style>


<html:form action="/reportDesigner" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportDesignForm"/>
<html:hidden property="reportId" name="reportDesignForm"/>
<html:hidden property="reportType" name="reportDesignForm"/>
<html:hidden property="reportName" name="reportDesignForm"/>
<html:hidden property="reportFormat" name="reportDesignForm"/>
<html:hidden property="reportCategory" name="reportDesignForm"/>
<html:hidden property="columns" name="reportDesignForm"/>
<html:hidden property="filters" name="reportDesignForm"/>
<html:hidden property="sortBy" name="reportDesignForm"/>
<html:hidden property="sortWith" name="reportDesignForm"/>
<html:hidden property="reportDescription" name="reportDesignForm"/>
<html:hidden property="isSharedReport" name="reportDesignForm"/>
<html:hidden property="levelPermissions" name="reportDesignForm"/>
<html:hidden property="reportFilePath" name="reportDesignForm"/>
<html:hidden property="sheetIndex" name="reportDesignForm"/>
<html:hidden property="rowIndex" name="reportDesignForm"/>

<div class="contentDiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td>
	    	<div style="width:70px;" class="boxTab">
	    		<span class="rightC"></span><span class="leftC"></span>
	    		<img src="images/blank_small.gif" align="absmiddle" />Filters
	    	</div>
	    </td> 
	  </tr> 
	</table> 

	<table class="selectFilter" cellspacing="0" cellpadding="0" border="0"  width="100%">
		 <tr>				  			
			<td colspan="1" class="head">
				<b>Select filters to display when you run the report</b>
			</td>
		</tr>
		<tr>
			<td style="padding:20px 0px 20px 20px;">
     	 		<script type="text/javascript">	
     	 		<%ReportTypes reportType = ReportTypeFactory.getInstance().getReportType((String)request.getAttribute("reportType"));%>
	     	 		var opts = <%=reportType.getFilter()%>;
		 			var checkBoxFilters = new CheckBoxList(opts,'<bean:write property="filters" name="reportDesignForm"/>',{namesonly:false, layerclass:'checkboxlistdiv', width:'180px', size:15, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
					document.write(checkBoxFilters.getHtml());
					checkBoxFilters.init();
			 	</script>										
			</td>			
		</tr>
 	</table>

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

var chkedCheckBox="images/checkboxchecked.gif";
var unChkedCheckBox="images/checkboxunchecked.gif";


function nextPage(){
	document.reportDesignForm.mode.value="reportDescription";
	document.reportDesignForm.filters.value=checkBoxFilters.getSelectedIds();
	document.reportDesignForm.submit();
}

function previousPage(){
	var reportFormat = document.reportDesignForm.reportFormat.value;

	if(reportFormat=='<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>'){
		document.reportDesignForm.mode.value="selectSortByColumn";
	}else{
		document.reportDesignForm.mode.value="columnMapping";
	}
		
	document.reportDesignForm.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

</script>

