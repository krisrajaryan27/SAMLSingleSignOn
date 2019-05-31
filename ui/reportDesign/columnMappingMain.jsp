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
	    <td><div style="width:130px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Column Mapping</div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>				  			
		<td class="head">
			<b>Map the column headers</b>
		</td>
	</tr>
   </table>
   <div class="outerDiv" style="padding:0px 0px 0px 0px;border-top:none;">
		<table cellspacing="2" cellpadding="5" class="boxContent" style="border:0px;width: 100%">			   

			<logic:iterate id="names" name="excelFieldName" scope="request" indexId="counter">
				<tr>
				  <td style="width:300px;"><bean:write name="names" /></td>	
				   <td style="width:300px;">
				   <script type="text/javascript">
					    var columns = <%=ReportDesignUtils.getJSArrayForSelectedColumns((String)request.getAttribute("columns"))%>;
	                    var selectColumn_<%=counter%> = new SelectBox(columns,'','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
	                    document.write(selectColumn_<%=counter%>.getHtml());
	                    selectColumn_<%=counter%>.init();
                	</script>
				   </td>		  
				</tr>

			</logic:iterate>  		
		</table>
	</div>
	
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
	document.reportDesignForm.mode.value="selectReportSheet";
	document.reportDesignForm.submit();
}

function nextPage(){
	document.reportDesignForm.columns.value=selectedColumnIds();
	document.reportDesignForm.mode.value="selectFilters";
	document.reportDesignForm.submit();
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
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function onWindowLoad(){
	var column = '<%=(String)request.getAttribute("columns")%>'
	var array = column.split(',');
	if(document.reportDesignForm.reportId.value!=''){
		<logic:iterate id="names" name="excelFieldName" scope="request" indexId="counter">
			var obj = eval(selectColumn_<%=counter%>);
			obj.setSelected(obj.getIndexWithId(array[<%=counter%>]));
		</logic:iterate> 
	}
}

window.onload=onWindowLoad;
</script>

