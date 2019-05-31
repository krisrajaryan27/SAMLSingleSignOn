<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.reportDesign.utils.ReportDesignUtils"%>
<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunction.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<style>
.orderColumns {border:1px solid #99CC33; border-top:none;}

</style>
<script type="text/javascript">
var selectSortByColumn=null;
var selectSortOrder=null;
var selectColumnType=null;
</script>
<html:form action="/reportDesigner" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportDesignForm"/>
<html:hidden property="reportId" name="reportDesignForm"/>
<html:hidden property="reportType" name="reportDesignForm"/>
<html:hidden property="reportName" name="reportDesignForm"/>
<html:hidden property="reportFormat" name="reportDesignForm"/>
<html:hidden property="reportCategory" name="reportDesignForm"/>
<html:hidden property="columns" name="reportDesignForm"/>
<html:hidden property="sortBy" name="reportDesignForm"/>
<html:hidden property="sortWith" name="reportDesignForm"/>
<html:hidden property="filters" name="reportDesignForm"/>
<html:hidden property="reportDescription" name="reportDesignForm"/>
<html:hidden property="isSharedReport" name="reportDesignForm"/>
<html:hidden property="levelPermissions" name="reportDesignForm"/>

<div class="contentDiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Sort By Column</div></td> 
	  </tr> 
   </table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
		<tr>				  			
			<td class="head">
				<b>Select the report column for sorting </b>
			</td>
		</tr>
		<tr>				  			
			<td>
				 <table class="innerReport">
	  			 	<tr>
	  			 		<td>
  			 				<table  cellspacing="0" cellpadding="0" >
								<tr>
									<td class="header" width="110px;">
									  	<bean:message key="reportdesigner.label.sortbycolumn"/>&nbsp;</span>
								  	</td>
									<td style="vertical-align: top;">
										<script type="text/javascript">
						                    var columns = <%=ReportDesignUtils.getJSArrayForSelectedColumns((String)request.getAttribute("columns"))%>;
						                    var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
						                    columns = opt.concat(columns);
						                    selectSortByColumn = new SelectBox(columns,'<bean:write property="sortBy" name="reportDesignForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:20});
						                    document.write(selectSortByColumn.getHtml());
						                    selectSortByColumn.init();
						               	  </script>
									</td>		
								</tr>											
							</table>	
						</td>
	  			 	</tr>
	  			 	<tr>
	  			 		<td>
  			 				<table  cellspacing="0" cellpadding="0" >
  			 					<tr>
  			 						<td class="header" width="110px;">
									  	<bean:message key="reportdesigner.label.sortorder"/>&nbsp;</span>
								  	</td>
  			 						<td style="vertical-align: top;">
										<script type="text/javascript">
											var opts = new Array();
											opts[0] = new SelectOption('<%=ReportDesignConstants.SORT_ORDER_ASC%>','<bean:message key="reportdesigner.sortorder.asc"/>');
											opts[1] = new SelectOption('<%=ReportDesignConstants.SORT_ORDER_DESC%>','<bean:message key="reportdesigner.sortorder.desc"/>');
											selectSortOrder = new SelectBox(opts,'<bean:write property="sortWith" name="reportDesignForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'100px', size:20});
											document.write(selectSortOrder.getHtml());
											selectSortOrder.init();
										</script>
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
	<br/>
</div>
</html:form>



<script language="javascript">

function submitForm(){
	
	document.reportDesignForm.submit();
}

function onWindowLoad(){

}

function getSelectedIds(){
	return dataGrid.getAllRowIds(",");
}

function nextPage(){
	document.reportDesignForm.mode.value="selectFilters";
	document.reportDesignForm.sortBy.value=selectSortByColumn.getSelectedId();
	document.reportDesignForm.sortWith.value=selectSortOrder.getSelectedId();
	document.reportDesignForm.submit();
}

function previousPage(){
	document.reportDesignForm.mode.value="orderColumns";
	document.reportDesignForm.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

window.onload=onWindowLoad;
</script>