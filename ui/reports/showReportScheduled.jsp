<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
				com.talentPool.reports.form.ReportForm, 
				com.talentPool.common.utils.CommonUtils,
				com.talentPool.positions.dataobject.PositionData,
				com.talentPool.user.dataobject.LoginData,
				com.talentPool.common.properties.TPApplicationProperties,
				com.talentPool.reports.ReportUtils"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.user.UserConstants,com.talentPool.common.NavigationConstants"%>

<script>
/*
extension of dhtmlXGridCell.js cell for implementing attachment column and
change the css of rows if email from already existing candidate
*/
var _closedPosition = <%=UserConstants.DEACTIVE%>;

function eXcell_estat(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 }
}
eXcell_estat.prototype = new eXcell;
eXcell_estat.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	if(val=="<%=UserConstants.DEACTIVE%>"){
	 this.cell.parentNode.className='disabledrow';
	}
}

eXcell_link.prototype.getTitle=function(){
	return getCustomTitle(this);
}
dhtmlXGridCellObject.prototype.getTitle=function(){
	return getCustomTitle(this);
}

</script>

<html:form action="/reportScheduler">
	<table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr> 
   		<td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;"><bean:message key="report_scheduler.label.currently_scheduled"/></td>
			<td class="rightC"></td>
		</tr>
		</table>
	    </td> 	    
   	</tr>   
   	</table>
   	<div id="scheduledReportGridBox" style="width: 735px;height: 18px;"></div>
	<div style="border-bottom:1px solid #999999; position: relative;">
		<div id="noScheduledReportGridBox" class="noContent">
			<bean:message key="report_scheduler.error.noData" />
	</div>	
	</div>   
</html:form>
<br>
<script language="JavaScript">

var scheduledReportGridBox ;
var noScheduledReportGridBox;

//Start of Schedule Report

function doOnLoadScheduledGrid(){
	initPopUp();	
	scheduledReportGridBox = new dhtmlXGridObject('scheduledReportGridBox'); 
	scheduledReportGridBox.imgURL = "images/dhtmlxGrid/"; 
	scheduledReportGridBox.setHeader("&nbsp;,<bean:message key="report_scheduler.label.frequency"/>,Criteria,<bean:message key="report_scheduler.label.email_to"/>,&nbsp;"); 
	scheduledReportGridBox.setInitWidths("18,120,300,250,0");
	scheduledReportGridBox.setColAlign("left,left,left,left,left");
	scheduledReportGridBox.setColTypes("link,link,ro,ro,ro,ro"); 
	scheduledReportGridBox.setColSorting("na,cstr,cstr,cstr,cstr");	
	scheduledReportGridBox.attachEvent("onRowDblClicked",onRowDoubleClick);
	scheduledReportGridBox.enableAutoHeigth(true,"105");			
	scheduledReportGridBox.init();
	scheduledReportGridBox.loadXML("reportScheduler.do?mode=getReportSchedulerXMLFile&reportName="+'<%=reportForm.getReportName() %>');
	scheduledReportGridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
}
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'scheduledReportGridBox'){
		switch(obj.cell._cellIndex){
		case 0:
			return "delete";
			break;
		case 1:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"frequency");
			break;
		case 2:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"showCriteria");
			break;
		case 3:
			return obj.grid.getUserData(obj.cell.parentNode.idd,"emailIds");
			break;				
		}
	}
	//if no special tooltip - return current value
	return obj.cell.innerHTML;

}

/*
function onGridLoadEnd(grd, itemsCnt){
	var grdId = grd.entBox.id;
	if(grdId == 'scheduledReportGridBox'){		
		if(grdId.getRowsNum()==0){
			nodataDivId=noScheduledReportGridBox;
			showDiv(nodataDivId,show)
		}

		showDiv(nodataDivId,1);
	}
}


function showDiv(divId,show){
	if(show==0){
		$(divId).style.display="none";
	}else{
		$(divId).style.display="block";
	}
}
*/

function onRowDoubleClick(id){
	viewDetails();
}
function viewDetails() {
  var returnVal = false;
  var id = scheduledReportGridBox.getSelectedId();
  if (id) {
  	showPopWin("reportScheduler.do?mode=addReportScheduler&scheduleId="+id, "550", "450", reloadGrid,true);
  	  } else {
    alert("Please select a Report Scheduled to view Scheduled details.");
  }  
  return returnVal;
}  
function showScheduleReportPopup() {
	if(validateSetFormFields()){		
		var qStrFilterData = getFilterQueryString();	
		showPopWin("reportScheduler.do?mode=addReportScheduler&"+qStrFilterData, "550", "450", reloadGrid, true);
	}
}
function reloadGrid() {
	scheduledReportGridBox.clearAll();
	scheduledReportGridBox.loadXML("reportScheduler.do?mode=getReportSchedulerXMLFile&reportName="+'<%=reportForm.getReportName() %>');		
}

function onClickReportScheduler(scheduleId){
  	showPopWin("reportScheduler.do?mode=addReportScheduler&scheduleId="+scheduleId, "550", "450", reloadGrid,true);
}

//Start Delete Schedule Report

function onClickDeleteReportSchedule(scheduleId){
	if(confirm("You are about to delete this Scheduled Report ?")){		
		var pars = "mode=deleteScheduleReport&scheduleId="+scheduleId;
		var myAjax = ajaxCall("reportScheduler.do",'get',pars,onDeleteReportSchedule, reportError);
  	}
}   
 
function onDeleteReportSchedule(request){
 	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){		
		alert('Error While Deleting');
		return;
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		scheduledReportGridBox.deleteRow(deletedIds[I]);
	}
	scheduledReportGridBox.clearSelection();  
}
//End Delete Schedule Report


//End of Schedule Report

//Start of Schedule Report


function getFilterQueryString(){
	var form = document.reportForm;
	var qStr = "reportName=" + form.reportName.value;
	if(form.filterId){
		qStr += "&filterId="+form.filterId.value;
	}
	if(form.departmentId){
		qStr += "&departmentId="+form.departmentId.value;
	}
	if(form.subDepartmentId){
		qStr += "&subDepartmentId="+form.subDepartmentId.value;
	}
	if(form.subSubDepartmentId){
		qStr += "&subSubDepartmentId="+form.subSubDepartmentId.value;
	}
	if(form.actionId){
		qStr += "&actionId="+form.actionId.value;
	}
	if(form.positionId){
		qStr += "&positionId="+form.positionId.value;
	}
	if(form.sourceId){
		qStr += "&sourceId="+form.sourceId.value;
	}		
	if(form.sourceCategoryId){
		qStr += "&sourceCategoryId="+form.sourceCategoryId.value;
	}		
	if(form.fromDate){
		qStr += "&fromDate="+form.fromDate.value;
	}
	if(form.toDate){
		qStr += "&toDate="+form.toDate.value;
	}
	if(form.orderBy){
		qStr += "&orderBy="+form.orderBy.value;
	}
	if(form.reportFormat){
		for(var k=0;k<form.reportFormat.length; k++){
			if(form.reportFormat[k].checked){
				qStr += "&reportFormat="+form.reportFormat[k].value;
				break;
			}			
		}
	}
	if(form.reportType){
		for(var j=0;j<form.reportType.length; j++){
			if(form.reportType[j].checked){				
				qStr += "&reportType="+form.reportType[j].value;
				break;
			}			
		}

	}
	if(form.fromMonth){
		qStr += "&fromMonth="+form.fromMonth.value;
	}	
	if(form.toMonth){
		qStr += "&toMonth="+form.toMonth.value;
	}
	if(form.fromYear){
		qStr += "&fromYear="+form.fromYear.value;
	}
	if(form.toYear){
		qStr += "&toYear="+form.toYear.value;
	}
	if(form.userId){
		qStr += "&userId="+form.userId.value;
	}
	if(form.interviewers){
		qStr += "&interviewers="+form.interviewers.value;
	}
	if(form.stages){
		qStr += "&stages="+form.stages.value;
	}
	if(form.degreeIds){
		qStr += "&degreeIds="+form.degreeIds.value;
	}
	if(form.minExp){
		qStr += "&minExp="+form.minExp.value;
	}
	if(form.maxExp){
		qStr += "&maxExp="+form.maxExp.value;
	}
	if(form.applicants){
		qStr += "&applicants="+form.applicants.value;
	}
	if(form.stepIds){
		qStr += "&stepIds="+form.stepIds.value;
	}
	if(form.users){
		qStr += "&users="+form.users.value;
	}
	if(form.selectedUserIds){
		qStr += "&selectedUserIds="+form.selectedUserIds.value;
	}
	if(form.recruitmentCostReportType){
		qStr += "&recruitmentCostReportType="+form.recruitmentCostReportType.value;
	}
	if(form.dateRange){
		qStr += "&dateRange="+form.dateRange.value;
	}	
	if(form.numberRange){
		qStr += "&numberRange="+form.numberRange.value;
	}
	if(form.activities){
		qStr += "&activities="+form.activities.value;
	}
	if(form.positionFilter) {
		qStr += "&positionFilter=" + form.positionFilter.value;
	}
	if(form.departmentFilter) {
		qStr += "&departmentFilter=" + form.departmentFilter.value;
	}
	return qStr;
}

//End of Schedule Report

</script>
