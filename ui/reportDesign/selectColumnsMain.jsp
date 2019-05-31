<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.reportDesign.utils.ReportDesignUtils"%>


<%@page import="com.talentPool.reportDesign.utils.ColumnUtils"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>

<% 
	HashMap columnList = (HashMap)request.getAttribute("columnList");
%>

<html:form action="/reportDesigner" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportDesignForm"/>
<html:hidden property="reportId" name="reportDesignForm"/>
<html:hidden property="reportType" name="reportDesignForm"/>
<html:hidden property="reportName" name="reportDesignForm"/>
<html:hidden property="reportFormat" name="reportDesignForm"/>
<html:hidden property="columns" name="reportDesignForm"/>
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
	    <img src="images/blank_small.gif" align="absmiddle" />Select Columns</div></td>
	    <td align="right" style="padding-bottom: 5px;">
		    	<a href="#" class="green" onclick="javascript: selectAllColumns();"><bean:message key="reportdesigner.column.select_all_columns" /></a>
		    	/<a href="#" class="green" style="margin-left: 5px;" onclick="javascript: deselectAllColumns();"><bean:message key="reportdesigner.column.deselect_all_columns" /></a>
	    </td>  
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
   
   
   <%
		Iterator listItr = columnList.keySet().iterator();
		while(listItr.hasNext()){
			String columnKey = (String)listItr.next();
			HashMap columns = (HashMap)columnList.get(columnKey);
			Iterator itr = columns.keySet().iterator();
			%>
			<tr style="border-bottom:1px solid #99CC33">				  			
				<td colspan="3" class="head" style="border-bottom:1px solid #99CC33;"><%=ColumnUtils.columnHeaderMap.get(columnKey)%></td>
				<td align="right" class="head" style="border-bottom:1px solid #99CC33;padding-bottom: 5px;font-weight:normal;">
			    	<a href="#" class="green" style="text-decoration:none" onclick="javascript: selectAll('<%=columnKey%>');"><bean:message key="reportdesigner.column.select_all" /></a>
			    	/<a href="#" class="green" style="margin-left: 5px;text-decoration:none" onclick="javascript: deselectAll('<%=columnKey%>');"><bean:message key="reportdesigner.column.deselect_all" /></a>
	    		</td> 
			</tr>
			<%
			int count = 0;
			while(itr.hasNext()){
				String key = (String)itr.next();
				if((count%4)==0){
					%><tr><%
				}
				%>
				  <td style="border-top: 0px; padding:6px 4px 6px 8px;">
					  <img id="<%=key%>" name="col" src="images/checkboxunchecked.gif" title="<%=columnKey%>" onclick="toggleChkBox(this)" style="margin-bottom: -2px;">&nbsp;<%=columns.get(key)%>
				  </td>
				<%
				if((count%2)==2 || count == columns.keySet().size()-1){
					%></tr><%
				}
				count++;
			}
		}
		%>
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
	<br/>
</div>
</html:form>	

<script type="text/javascript">

var chkedCheckBox="images/checkboxchecked.gif";
var unChkedCheckBox="images/checkboxunchecked.gif";

function toggleChkBox(elem) {
	if (elem.src.indexOf(chkedCheckBox) == -1) {
		elem.src = chkedCheckBox;
	} else {
		elem.src = unChkedCheckBox;
	}
	return false;	
}

function  selectAllColumns(){
	var imgs = document.getElementsByName('col');
	for (i = 0; i < imgs.length; i++) {
		var elem = imgs[i];
		elem.src =chkedCheckBox;
	}
}

function  deselectAllColumns(){
	var imgs = document.getElementsByName('col');
	for (i = 0; i < imgs.length; i++) {
		var elem = imgs[i];
		elem.src = unChkedCheckBox;
	}
}

function  selectAll(title){
	var imgs = document.getElementsByName('col');
	for (i = 0; i < imgs.length; i++) {
		var elem = imgs[i];
		if(elem.title==title){
			elem.src =chkedCheckBox;
		}
	}
}

function  deselectAll(title){
	var imgs = document.getElementsByName('col');
	for (i = 0; i < imgs.length; i++) {
		var elem = imgs[i];
		if(elem.title==title){
			elem.src = unChkedCheckBox;
		}
	}
}

function selectedColumnIds(){
	var columns = '<bean:write property="columns" name="reportDesignForm" />'.split(','); 
	var notTheSame = false;
	var ids = [];
	var imgs = document.getElementsByName('col');

	for (i = 0; i < imgs.length; i++) {
		var elem = imgs[i];
		if (elem.src.indexOf(chkedCheckBox) == -1) {
			
		} else {
			
			if(columns.indexOf(elem.id)<0){
				notTheSame = true;
			}			
				ids[ids.length] = elem.id;
			
		}
	}
	if(!notTheSame && columns.length==ids.length && '<bean:write property="columns" name="reportDesignForm" />'!=ids){
		return '<bean:write property="columns" name="reportDesignForm" />'
	}else{		
		var selectedIds = '';
		for(i = 0; i < ids.length; i++){
			selectedIds += ','+ ids[i];
		}
		return selectedIds.substring(1);
	}
	
}
function nextPage(){
	if(selectedColumnIds()==''){
		alert('Please select atleast one column');
		return false;
	}
	var reportFormat = document.reportDesignForm.reportFormat.value;
	if(reportFormat=='<%=ReportDesignConstants.REPORT_FORMAT_TABULAR%>'){
		document.reportDesignForm.mode.value="orderColumns";
	}else{
		document.reportDesignForm.mode.value="uploadReport";
	}

	document.reportDesignForm.columns.value=selectedColumnIds();
	document.reportDesignForm.submit();
}

function previousPage(){
	document.reportDesignForm.mode.value="selectReportType";
	document.reportDesignForm.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function checkSelectedColumns(cloumns){
	var columnStr = cloumns.split(",");
	for (var j = 0; j< columnStr.length; j++ ){
		var imgs = document.getElementsByName('col');
		for (i = 0; i < imgs.length; i++) {
			var elem = imgs[i];
			if(elem.id==columnStr[j]){
				elem.src =chkedCheckBox;
			}
		}
	}
}

function onWindowLoad(){
	var cloumns = '<bean:write property="columns" name="reportDesignForm" />'; 
	if(cloumns!='' && cloumns!=null){
		checkSelectedColumns(cloumns);	
	}
}

window.onload=onWindowLoad;
</script>

