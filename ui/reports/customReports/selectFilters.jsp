<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script src="js/reports/dataObj/CustomReportFilter.js" type="text/javascript"></script>
<style type="text/css">
.customReport {border:1px solid #99CC33; border-top:none;}
.customReport TD.normal{border-bottom:1px dashed #C4C4C4;padding-left:4px; padding-right:4px; padding-top: 4px; padding-bottom: 4px;}
.customReportOuter TD.head{border-bottom:1px solid #F9FCF3; color:#666666; font-weight: bold; background:#D0E4A3;padding:2px 4px 4px 8px;}
.customReportOuter TD.none{border-bottom:0px;padding: 0px;}
</style>
<s:form action="saveFilters" method="POST">
<%@include file="include/commonReportHiddenFields.jspf" %>
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
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
		<tr> 
			<td>
				<div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
					<s:label key="custom_report.title.select_filters" />
				</div>
			</td> 
		</tr> 
	</table>
	<table class="customReportOuter" width="100%" border="0" cellspacing="0" cellpadding="0">
	 	<tr>
			<td class="head"><s:label key="custom_report.filters.header.filter_name" /></td>			
		</tr>
		<tr>
			<td>	
				<table id="filters" class="customReport" style="width: 100%">
					<s:iterator value="crFilters" status="filterStatus">
					<tr>
						<td>
							<table id="<s:property value="filterId" />" class="filterTable" style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td class="normal">
											<img id="selectFilterImg_<s:property value="filterId" />" class="selectFilterImg" src="images/checkboxunchecked.gif" onclick="changeFilterOptionsDivState(this,'<s:property value="filterId" />');" style="cursor: pointer;" />
											&nbsp;&nbsp;<s:property value="filterName" />
										</td>
									</tr>
									<tr>
										<td >
											<div id="filterOptionsDiv_<s:property value="filterId" />" class="divSelected filterOptionsDiv" style="display: none;">
												<table style="width: 100%;">
													<tr>
														<td>&nbsp;</td>
														<td style="border-bottom:0px;"><img class="showFilterImg" src="images/checkboxunchecked.gif" style="cursor: pointer;" >&nbsp;<s:text name="custom_report.filters.header.show_in_report" /></td>
													</tr>
													<tr>
														<td>&nbsp;</td>
														<td style="border-bottom:0px;"><img class="applyDefaultFilterImg" src="images/checkboxunchecked.gif" style="cursor: pointer;" >&nbsp;<s:text name="custom_report.filters.header.default_in_report" /></td>
													</tr>
													<tr>
														<td class="normal">&nbsp;</td>
														<td class="normal" >
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
																	<jsp:include page='../crFilters/dateFilter.jsp'/>
																</s:if>
																<s:if test="filterId==#joiningDateFilter">
																	<jsp:include page='../crFilters/dateFilter.jsp'/>
																</s:if>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
							</table>							
						</td>
					</tr>
					</s:iterator>
				</table>			
			</td>
		</tr>
	</table> 
	<table class="tblPop" width="100%">
	<tr>
		<td>
			<div class="navBtn" style="float: right;">
				<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.back"/></a>
				<s:if test="reportId!='' && reportId!=null">
					<a href="#" style="width:140px; margin-left:5px;" class="active" onclick="javascript: saveAsNewReport();"><span class="rightC"></span><span class="leftC"></span><s:text  name="common.save_as"/> New Report</a>
					<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="javascript: saveCustomReport();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.save"/></a>
				</s:if>
				<s:else>
					<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="javascript: saveAsNewReport();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.next"/></a>
				</s:else>	
				<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
			</div>
		</td>
	</tr>
	</table>
</div>
</s:form>
<br/>
<br/>
<script type="text/javascript">
var chkedCheckBox="images/checkboxchecked.gif";
var unChkedCheckBox="images/checkboxunchecked.gif";

var expandPlus="images/ico_plus.gif";
var collapseMinus="images/ico_minus.gif"; 

Event.observe(window, "load", function() {	
	$$("img.showFilterImg").each(function(element) {
		element.observe("click", changeCheckBoxState)
	});
	$$("img.applyDefaultFilterImg").each(function(element) {
		element.observe("click", changeCheckBoxState)
	});	
});

function changeCheckBoxState(event){	
	var imgElem = event.element();	
	if(imgElem.src.indexOf(chkedCheckBox)!=-1){
		imgElem.src=unChkedCheckBox;	
	}else if(imgElem.src.indexOf(unChkedCheckBox)!=-1){
		imgElem.src=chkedCheckBox;
	}
}

function changeFilterOptionsDivState(obj, filterId){
	if(obj.src.indexOf(chkedCheckBox)!=-1){
		obj.src=unChkedCheckBox;
		$('filterOptionsDiv_'+filterId).hide();
	}else if(obj.src.indexOf(unChkedCheckBox)!=-1){
		obj.src=chkedCheckBox;
		$('filterOptionsDiv_'+filterId).show();
		initFilters(filterId);
	}
	if(filterId == <s:property value="#offerDateFilter" /> && obj.src.indexOf(chkedCheckBox)!=-1) {
		$('selectFilterImg_' + <s:property value="#joiningDateFilter" />).src = unChkedCheckBox;
		$('filterOptionsDiv_' + <s:property value="#joiningDateFilter" />).hide();
	} else if(filterId == <s:property value="#joiningDateFilter" /> && obj.src.indexOf(chkedCheckBox)!=-1) {
		$('selectFilterImg_' + <s:property value="#offerDateFilter" />).src = unChkedCheckBox;
		$('filterOptionsDiv_' + <s:property value="#offerDateFilter" />).hide();
	}
}

function initFilters(filterId, defaultValue, defaultValue1, defaultValue2){
	if(filterId==<s:property value="#asOfDateFilter" />){
		initAsOfDateFilter(defaultValue, defaultValue1);
	}else if(filterId==<s:property value="#dateFilter" />){
		initDateFilter(defaultValue, defaultValue1, defaultValue2);
	}else if(filterId==<s:property value="#filterPositionStatus" />){
		if(!isPositionStatusGridInitialised()){
			initPositionStatusGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterPositionOwner" />){
		if(!isPositionOwnerGridInitialised()){
			initPositionOwnerGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterDepartment" />){
		if(!isDepartmentGridInitialised()){
			initDepartmentGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterPosition" />){
		if(!isPositionGridInitialised()){
			initPositionGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterUserRole" />){
		if(!isUserRoleInitialised()){
			initUserRoleGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterActivityUser" />){
		if(!isUsersGridInitialised()){
			initUsersGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterSourceCategory" />){
		if(!isSourceCategoryGridInitialised()){
			initSourceCategoryGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterSource" />){
		if(!isSourcesGridInitialised()){
			initSourceGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterStage" />){
		if(!isStageGridInitialised()){
			initStageGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterStep" />){
		if(!isStepGridInitialised()){
			initStepGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#filterProcessUser" />){
		if(!isUsersGridInitialised()){
			initUsersGrids(defaultValue);
		}
	}else if(filterId==<s:property value="#activityDateFilter" />){
		initDateFilter(defaultValue, defaultValue1, defaultValue2);
	}else if(filterId==<s:property value="#offerDateFilter" />){
		initDateFilter(defaultValue, defaultValue1, defaultValue2);
	}else if(filterId==<s:property value="#joiningDateFilter" />){
		initDateFilter(defaultValue, defaultValue1, defaultValue2);
	}
} 

function previousPage(){
	var filterList = populateFilters();
	if(filterList!=null){
		document.saveFilters.filtersJSON.value=Object.toJSON(filterList);
		document.saveFilters.action="configureReportTotals.action";
		if(document.saveFilters.reportTypeId.value == '4') {
			document.saveFilters.action="customizeReport.action";
		}		
		document.saveFilters.submit();		
	}
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

function nextPage(){
	var filterList = populateFilters();
	if(filterList!=null){
		document.saveFilters.filtersJSON.value=Object.toJSON(filterList);
		document.saveFilters.submit();
	}
}

function saveAsNewReport(){
	var filterList = populateFilters();
	if(filterList!=null){
		document.saveFilters.filtersJSON.value=Object.toJSON(filterList);
		document.saveFilters.submit();
	}
}

function saveCustomReport(){
	var filterList = populateFilters();
	if(filterList!=null){
		document.saveFilters.filtersJSON.value=Object.toJSON(filterList);
		document.saveFilters.action="editReport.action";
		document.saveFilters.submit();	
	}
}

function populateFilters(){
	var filterList = new Array();	
	var size=0;	
	$$('#filters table.filterTable').each(function(node) {
		var selectFilterImg = node.select('img.selectFilterImg')[0];
		if(selectFilterImg.src.indexOf(chkedCheckBox)!=-1){
			var filterId = node.id;
			var crFilter = new CustomReportFilter(filterId);
			if(node.select('img.showFilterImg')[0].src.indexOf(chkedCheckBox)!=-1){
				crFilter.setVisibility(true);
			}else {
				crFilter.setVisibility(false);
			}
			if(node.select('img.applyDefaultFilterImg')[0].src.indexOf(chkedCheckBox)!=-1){
				var value=getFilterDefaultValue(filterId);
				if(value==-1){
					filterList = null;	
					throw $break;
				}
				crFilter.setValue(value[0]);
				crFilter.setValue1(value[1]);
				crFilter.setValue2(value[2]);				
			}
			filterList[size++]=crFilter;
		}
	});
	return filterList;
} 

function getFilterDefaultValue(filterId){
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

function populateSelectedFilters(){
	if($('filtersJSON').value!=null && $('filtersJSON').value!=''){
		var selectedFilters = $('filtersJSON').value.evalJSON();
		for(var k=0; k<selectedFilters.length; k++){
			var selectedFilter = selectedFilters[k];
			var filterId = selectedFilter.filterId;
			if($(filterId) && $(filterId).select('img.selectFilterImg')[0]){
				$(filterId).select('img.selectFilterImg')[0].src=chkedCheckBox;
				$('filterOptionsDiv_'+filterId).show();
				if(selectedFilter.visibility){
					$(filterId).select('img.showFilterImg')[0].src=chkedCheckBox;
				}else{
					$(filterId).select('img.showFilterImg')[0].src=unChkedCheckBox;
				}
				if(selectedFilter.value!=null && selectedFilter.value!=''){
					$(filterId).select('img.applyDefaultFilterImg')[0].src=chkedCheckBox;
					initFilters(filterId, selectedFilter.value);
				}else {
					$(filterId).select('img.applyDefaultFilterImg')[0].src=unChkedCheckBox;
					initFilters(filterId);	
				}
			}
		}
	}
}

Event.observe(window, "load", function() {
	populateSelectedFilters();
});

</script>