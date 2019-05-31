<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import=" com.talentPool.customReports.constants.CustomReportConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/scriptaculous.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<script src="js/reports/dataObj/CustomReportFilter.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">
<script>
var minusImg = 'images/dhtmlxGrid/minus.gif';
var plusImg = 'images/dhtmlxGrid/plus.gif';
var exportMenu = new TpMenu();
exportMenu.addItem(new TpMenu({type:'menu', 
						 id: 'xlsExport',
						 image: 'images/excel_co.GIF',
						 title:'<s:text name="custom_report.export.excel_03"  />', 
						 onclick:'exportToXLS'}));
exportMenu.addItem(new TpMenu({type:'menu', 
						 id: 'xlsxExport',
						 image: 'images/excel_co.GIF',
						 title:'<s:text name="custom_report.export.excel_07"  />', 
						 onclick:'exportToXLSX'}));
exportMenu.addItem(new TpMenu({type:'menu', 
						id: 'pdfExport',
						image: 'images/logo_pdf.gif',
						title:'<s:text name="custom_report.export.pdf"  />', 
						onclick:'exportToPDF'}));
exportMenu.addItem(new TpMenu({type:'menu', 
						id: 'csvExport',
						image: 'images/excel_bw.GIF',
						title:'<s:text name="custom_report.export.csv"  />', 
						onclick:'exportToCSV'}));
exportMenu.addItem(new TpMenu({type:'menu', 
						 id: 'plainXlsExport',
						 image: 'images/excel_co.GIF',
						 title:'<s:text name="custom_report.export.plainExcel"  />', 
						 onclick:'exportToPlainXLS'}));

var exportMenuHandler = new TpMenuHandler(exportMenu,{});
exportMenuHandler.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:-50});
function showExportMenu(obj){
	exportMenuHandler.show('',obj.id);
}
</script>
<div class="contentDiv">
<s:if test="hasActionErrors()">
	<table id="m_errortable">
		<tr>
			<td class="header" style="padding: 4px">
				Error
			</td>
		</tr>  
		<tr>
		   <td style="padding: 2px">
		    	<s:actionerror />
		    </td>
		</tr>  
	</table>
</s:if>	
<s:form action="runReport" method="POST">
	<s:hidden name="reportId" />
	<s:hidden name="reportName" />
	<s:hidden name="filtersJSON" id="filtersJSON" />
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td valign="bottom">
	    	<div class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    		<img src="images/blank_small.gif" /><s:property value="reportName" />&nbsp;&nbsp;
	    	</div>
	    </td>
	    <td align="right">
	    	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SUMMARY_REPORTS_ADD_MODIFY">
			<div class="navBtnTab" style="width:220px;float: right;">
			</logic:equal>
			<logic:notEqual value="true" name="permissionSet" scope="session" property="PERMISSION_SUMMARY_REPORTS_ADD_MODIFY">
			<div class="navBtnTab" style="width:100px;float: right;">
			</logic:notEqual>
				<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
				<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SUMMARY_REPORTS_ADD_MODIFY">
					<a href="#" style="width:120px; cursor: pointer;" onclick="javascript: customizeReport();"><span class="rightC"></span><span class="leftC"></span><s:text name="custom_report.button.customize_report"  /></a>
				</logic:equal>
				<a href="#" class="btnExport" style="width:70px; margin-left:5px;cursor: pointer;" onmouseover="javascript: showExportMenu(this);return false;" id="exportBtn" ><span class="rightC"></span><span class="leftC"></span><s:text name="common.export"/></a>
			</div>
		</td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">    
	<tr>
		<td class="head">
			<img src="images/dhtmlxGrid/minus.gif" id="showHideFilters"  align="middle" />&nbsp;
			<b><s:text name="custom_report.header.filters"  /></b>
		</td>
	</tr>
	<tr>
		<td style="padding: 0px;">
			<div id="filterPane" style="padding: 10px;">
				<s:set name="asOfDateFilter" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_AS_OF_DATE" id="asOfDateFilter"/>	
				<s:set name="dateFilter" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_DATE" id="dateFilter"/>
				<s:set name="filterPositionStatus" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_POSITION_STATUS" id="filterPositionStatus"/>
				<s:set name="filterPositionOwner" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_POSITION_OWNER" id="filterPositionOwner"/>
				<s:set name="filterDepartment" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_DEPARTMENT" id="filterDepartment"/>
				<s:set name="filterPosition" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_POSITION" id="filterPosition"/>				
				<s:set name="filterUserRole" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_USER_ROLE" id="filterUserRole"/>				
				<s:set name="filterActivityUser" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_ACTIVITY_USER" id="filterActivityUser"/>				
				<s:set name="filterSourceCategory" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_SOURCE_CATEGORY" id="filterSourceCategory"/>
				<s:set name="filterSource" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_SOURCE" id="filterSource"/>				
				<s:set name="filterStage" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_STAGE" id="filterStage"/>
				<s:set name="filterStep" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_STEP" id="filterStep"/>				
				<s:set name="filterProcessUser" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_PROCESS_USER" id="filterProcessUser"/>
				<s:set name="activityDateFilter" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_ACTIVITY_DATE" id="activityDateFilter"/>
				<s:set name="offerDateFilter" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_OFFER_DATE" id="offerDateFilter"/>
				<s:set name="joiningDateFilter" value="@com.talentPool.customReports.constants.CustomReportConstants@FILTER_JOINING_DATE" id="joiningDateFilter"/>
					<s:iterator value="crFilters">
						<div class="filterDiv" id="<s:property value="filterId"/>" >
						<s:if test="visibility==true">
							<s:if test="filterId==#asOfDateFilter">
								<jsp:include page='../crFilters/asOfdateFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#dateFilter">
								<jsp:include page='../crFilters/dateFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterPositionStatus">
								<jsp:include page='../crFilters/positionStatusFilter.jsp'/>
							</s:if>									
							<s:if test="filterId==#filterPositionOwner">
								<jsp:include page='../crFilters/positionOwnerFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterDepartment">
								<jsp:include page='../crFilters/departmentFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterPosition">
								<jsp:include page='../crFilters/positionFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterUserRole">
								<jsp:include page='../crFilters/userRoleFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterActivityUser">
								<jsp:include page='../crFilters/userFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterSourceCategory">
								<jsp:include page='../crFilters/sourceCategoryFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterSource">
								<jsp:include page='../crFilters/sourceFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterStage">
								<jsp:include page='../crFilters/stageFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#filterStep">
								<jsp:include page='../crFilters/stepFilter.jsp'/>								
							</s:if>
							<s:if test="filterId==#filterProcessUser">
								<jsp:include page='../crFilters/userFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#activityDateFilter">
								<jsp:include page='../crFilters/dateFilter.jsp'/>
							</s:if>
							<s:if test="filterId==#offerDateFilter">
								<jsp:include page='../crFilters/dateFilter.jsp'>
									<jsp:param value="Offer" name="type"/>
								</jsp:include>
							</s:if>
							<s:if test="filterId==#joiningDateFilter">
								<jsp:include page='../crFilters/dateFilter.jsp'>
									<jsp:param value="Joining" name="type"/>
								</jsp:include>
							</s:if>
						</s:if>	
						</div>						
					</s:iterator>
			</div>
		</td>
	</tr>
	</table>
	<table width="100%">
		<tr>
			<td style="border-top: 0;">
				<div class="navBtn">
					<a href="#" style="width:90px; cursor: pointer;" class="active" onclick="javascript: run();"><span class="rightC"></span><span class="leftC"></span><s:text name="custom_report.button.run_report" /></a>
					<a href="#" style="width:50px; margin-left:5px;cursor: pointer;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>
	<br></br>
	<s:if test="showReport==true">
		<script>
			Effect.SlideUp('filterPane');
			$('showHideFilters').src='images/dhtmlxGrid/plus.gif';
		</script>
		<div class="outerDiv">
			<iframe name="printFrame" id="printFrame" 
				src="<s:property value="outputFileName" />"  style="width:100%;height:380px;z-index: 1;" marginheight="0" marginwidth="0" frameborder="0" >
			</iframe>
		</div>
		<br></br>
	</s:if>
</s:form>
</div>
<script>
var frm = document.runReport;

Event.observe(window, "load", function() {
	$('showHideFilters').observe("click", showHideFilterPane);	
});

function populateFilters(){
	var filterList = new Array();
	var size =0;
	$$('div.filterDiv').each(function(node) {
			var filterId = node.id;
			var crFilter = new CustomReportFilter(filterId);
			crFilter.setVisibility(true);
			var value = getFilterSelectedValue(filterId);
			if(value==-1){
				filterList = null;	
				throw $break;
			}
			crFilter.setValue(value[0]);
			crFilter.setValue1(value[1]);
			crFilter.setValue2(value[2]);
			filterList[size++]=crFilter;
	});
	return filterList;
}

function getFilterSelectedValue(filterId){
	var value = new Array();
	if(filterId==<s:property value="#asOfDateFilter" />){
		value = validateAndGetAsOfDateFilterValue();
		if(value == false)
			return -1;
	}else if(filterId==<s:property value="#dateFilter" />){
		value = validateAndGetDateFilterValue();
		if(value == false)
			return -1;
	}else if(filterId==<s:property value="#filterPositionStatus" />){
		value[0] = validateAndGetPositionStatusFilterValue();
	}else if(filterId==<s:property value="#filterPositionOwner" />){
		value[0] = validateAndGetPositionOwnerFilterValue();
	}else if(filterId==<s:property value="#filterDepartment" />){
		value[0] = validateAndGetDepartmentFilterValue();
	}else if(filterId==<s:property value="#filterPosition" />){
		value[0] = validateAndGetPositionFilterValue();
	}else if(filterId==<s:property value="#filterUserRole" />){
		value[0] = validateAndGetUserRoleFilterValue();
	}else if(filterId==<s:property value="#filterActivityUser" />){
		value[0] = validateAndGetUserFilterValue();
	}else if(filterId==<s:property value="#filterSourceCategory" />){
		value[0] = validateAndGetSourceCategoryFilterValue();
	}else if(filterId==<s:property value="#filterSource" />){
		value[0] = validateAndGetSourceFilterValue();
	}else if(filterId==<s:property value ="#filterStage" />){
		value[0] = validateAndGetStageFilterValue();
	}else if(filterId==<s:property value="#filterStep" />){
		value[0] = validateAndGetStepFilterValue();
	}else if(filterId==<s:property value="#filterProcessUser" />){
		value[0] = validateAndGetUserFilterValue();
	}else if(filterId==<s:property value="#activityDateFilter" />){
		value = validateAndGetDateFilterValue();
		if(value == false)
			return -1;
	}else if(filterId==<s:property value="#offerDateFilter" />){
		value = validateAndGetDateFilterValue();
		if(value == false)
			return -1;
	}else if(filterId==<s:property value="#joiningDateFilter" />){
		value = validateAndGetDateFilterValue();
		if(value == false)
			return -1;
	}
	return value;
}

function run(){
	runOrExport("runReport.action");
}

function exportToXLSX(){
	runOrExport("exportToXLSX.action");
}
function exportToXLS(){
	runOrExport("exportToXLS.action");
}

function exportToPDF(){
	runOrExport("exportToPDF.action");
}

function exportToCSV(){
	runOrExport("exportToCSV.action");
}

function exportToPlainXLS(){
	runOrExport("exportToPlainXLS.action");
}

function runOrExport(action){
	var filterList = populateFilters();
	if(filterList!=null){
		frm.filtersJSON.value=Object.toJSON(filterList);
		frm.action=action;
		frm.submit();
	}
}

function customizeReport(){
	var param = "reportId="+document.runReport.reportId.value;
	window.location.href="editCustomReport.action?"+param;
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function hideExportMenu(ev){
	if (browser_ie){
		currElement=window.event.srcElement;}
	else if (browser_nn4 || browser_nn6){
		currElement=ev.target;}
	if (currElement.id!="btnExport"){
	if (getObj("dropDownMenu").style.display=="block"){
		getObj("dropDownMenu").style.display="none";}}
}


function showHideFilterPane(event){
	var imgElem = event.element();
	if(imgElem.src.indexOf(minusImg)!=-1){
		imgElem.src=plusImg;
		Effect.SlideUp('filterPane',{duration: 0.3});
	}else if(imgElem.src.indexOf(plusImg)!=-1){
		imgElem.src=minusImg;
		Effect.SlideDown('filterPane',{duration: 0.3});
	}
}

function doOnLoad(){
	<s:iterator value="crFilters">
		<s:if test="visibility==true">
			<s:if test="filterId==#asOfDateFilter">
				initAsOfDateFilter('<s:property value="value" />','<s:property value="value1" />');
			</s:if>
			<s:if test="filterId==#dateFilter">
				initDateFilter('<s:property value="value" />','<s:property value="value1" />','<s:property value="value2" />');
			</s:if>
			<s:if test="filterId==#filterPositionStatus">
				initPositionStatusGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterPositionOwner">
				initPositionOwnerGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterDepartment">
				initDepartmentGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterPosition">
				initPositionGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterUserRole">
				initUserRoleGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterActivityUser">			
				initUsersGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterSourceCategory">
				initSourceCategoryGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterSource">
				initSourceGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterStage">
				initStageGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterStep">
				initStepGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#filterProcessUser">			
				initUsersGrids('<s:property value="value" />');
			</s:if>
			<s:if test="filterId==#activityDateFilter">
				initDateFilter('<s:property value="value" />','<s:property value="value1" />','<s:property value="value2" />');
			</s:if>
			<s:if test="filterId==#offerDateFilter">
				initDateFilter('<s:property value="value" />','<s:property value="value1" />','<s:property value="value2" />');
			</s:if>
			<s:if test="filterId==#joiningDateFilter">
				initDateFilter('<s:property value="value" />','<s:property value="value1" />','<s:property value="value2" />');
			</s:if>
		</s:if>
	</s:iterator>
}
window.onload=doOnLoad;

</script>