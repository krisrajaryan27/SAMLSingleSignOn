<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@page import="com.talentPool.reportDesign.constants.ReportDesignConstants"%>

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
	    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Working Sheet</div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>				  			
		<td class="head">
			<b>Select the sheet and row in which you wish to dump the data</b>
		</td>
	</tr>
	<tr>				  			
		<td>
			 <table class="innerReport">
  			 	<tr>
  			 		<td>
 			 			<table  cellspacing="0" cellpadding="0" >
							<tr>
								<td class="header" width="80px;">
								  	<bean:message key="reportdesigner.label.select_sheet"/>&nbsp;
							  	</td>
								<td style="vertical-align: top;">
									<script type="text/javascript">
					                    var columns = <%=request.getAttribute("sheetArray")%>;
					                    selectSheet = new SelectBox(columns,'<bean:write property="sheetIndex" name="reportDesignForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'150px', size:20});
					                    document.write(selectSheet.getHtml());
					                    selectSheet.init();
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
 			 						<td class="header" width="80px;">
								  	<bean:message key="reportdesigner.label.select_row"/>&nbsp;</span>
							  	</td>
 			 						<td style="vertical-align: top;">
									<script type="text/javascript">
										var opts = new Array();
										<%for(int i=0; i<50;i++){%>
										opts[<%=i%>] = new SelectOption('<%=i+1%>','<%=i+1%>');
										<%}%>
										selectRow = new SelectBox(opts,'<bean:write property="rowIndex" name="reportDesignForm"/>','images/btn_dropdown.gif',{namesonly:false, width:'80px', size:20});
										document.write(selectRow.getHtml());
										selectRow.init();
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
	document.reportDesignForm.mode.value="uploadReport";
	document.reportDesignForm.submit();
}

function nextPage(){	
	document.reportDesignForm.sheetIndex.value=selectSheet.getSelectedId();
	document.reportDesignForm.rowIndex.value=selectRow.getSelectedId();
	document.reportDesignForm.mode.value="columnMapping";
	document.reportDesignForm.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function onWindowLoad(){
	
}

window.onload=onWindowLoad;
</script>

