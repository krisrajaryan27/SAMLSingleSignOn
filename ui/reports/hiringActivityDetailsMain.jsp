<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, 
				com.talentPool.reports.form.ReportForm, 
				com.talentPool.common.utils.CommonUtils,com.talentPool.user.manager.ModuleSet,
				com.talentPool.positions.dataobject.PositionData,
				com.talentPool.user.dataobject.LoginData,
				com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.reports.ReportVersionConstants"%>
<%@page import="com.talentPool.reports.ReportUtils"%>

<%@page import="com.talentPool.positions.PositionConstants"%><link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script src="js/cookies.js"></script>	
<script type="text/javascript">
var selectFilter=null;
var selectPosition=null;
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";
</script>
<style>
.innerReport TD.label{width:135px;}
</style>
<bean:define id="reportForm" name="reportForm" type="com.talentPool.reports.form.ReportForm"></bean:define>
<html:form action="/reports">
<html:hidden property="reportName"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="mode" />
<html:hidden property="filterId"/>
<html:hidden property="fieldIds"/>
<html:hidden property="positionId"/>
<html:hidden property="selectedPositionOwnerIds"/>
<html:hidden property="departmentId"/>
<html:hidden property="subDepartmentId"/>
<html:hidden property="subSubDepartmentId"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="reportFormat" name="reportForm"/>
<html:hidden property="reportTemplateId" name="reportForm"/>
<html:hidden property="selectedUserIds" name="reportForm" />
<html:hidden property="dateRange" name="reportForm"/>
<html:hidden property="stages" name="reportForm"/>
<div class="contentDiv">
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.hiring_activity" /></div></td> 
	  </tr> 
   </table> 
   <table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>				  			
		<td class="head" colspan="2">
				<b><bean:message key="report.label.report_type" /></b>
		</td>
  </tr>
   <tr>				  			
		<td colspan="2">
			<img src="images/radiobutton.gif" name='reportType' id='reportType_<%=ReportConstants.REPORT_TYPE_SUMMARY%>' 
				 onclick="javascript:onReportTypeChange(this,'<%=ReportConstants.REPORT_TYPE_SUMMARY%>');" 
				 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.type_summary"/>&nbsp;&nbsp;
			<img src="images/checkedradiobutton.gif" name='reportType' id='reportType_<%=ReportConstants.REPORT_TYPE_DETAILS%>' 
				 onclick="javascript:onReportTypeChange(this,'<%=ReportConstants.REPORT_TYPE_DETAILS%>');" 
				 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.type_details"/>&nbsp;&nbsp;
		</td>
	</tr>
    <tr>				  			
		<td class="head" colspan="2">
			<b><bean:message key="report.label.filter" /></b>
		</td>
	</tr>
	<tr>				  			
		<td style="border-bottom: 0px;">
			 <table class="innerReport">
				 <tr>
	   				<td class="label">
                  	 	<bean:message key="report.label.date_range" />:&nbsp;
                  	</td>
                  	<td> 
                  	 <script type="text/javascript">
		                    var optDate = <%=ReportUtils.getJSArrayForDateRange()%>;
		                    selectDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
		                    document.write(selectDateRange.getHtml());
		                    selectDateRange.setOnChangeHandler('onDateRangeChange');
		                    selectDateRange.init();
                  	  </script>
		   			</td>
		   			<td>
						<div id="divSelectDateRange" style="display:none;">
					 		 <table class="innerReport" cellspacing="0" cellpadding="0" border="0" >
				      	 		<tr>
				      	 			<td style="width: 20px;"></td>
				      	 			<td>
	  		        		  			<bean:message key="report.label.from" /> :
	  		        					<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
									</td>
									<td style="width: 20px;"></td>
									<td>
		  		          				<bean:message key="report.label.to" /> :
		  		          				<html:text property="toDate" styleId="toDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
	  		      					</td>
	  		      				</tr>
		   						</table>
						</div>
					</td>
				</tr>
			</table>	
			<table class="innerReport">
				<tr>
					<td class="label">
						<bean:message key="report.label.search_in" />:&nbsp;
					</td>
					<td>
						<script type="text/javascript">
							var opts = new Array();
							opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
							opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
							opts[2] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
							opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
							selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_OPEN_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
							document.write(selectFilter.getHtml());
							selectFilter.setOnChangeHandler('onChangeFilter');
							selectFilter.init();
						</script>
					</td>
				</tr>
			</table>		  			  
			<div id="divSelectSpecificPosition" style="display:none;">
				<table class="innerReport">
					<tr>
						<td class="label">
							<bean:message key="common.select_position" /> :&nbsp;
						</td>
						<td>
							<script type="text/javascript">
							 var opts = new Array();
						       selectPosition = new SelectBox(new Array(),'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
						       document.write(selectPosition.getHtml());
						       selectPosition.init();					
							</script>
						</td>
					</tr>
				</table>		  			  
			</div>
			<div id="divSelectSpecificDepartment" style="display:none;">
				<table class="innerReport">
					<tr>
						<td class="label">
							<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>:&nbsp;
						</td>
						<td>
							<script type="text/javascript">
								var opts = <%=CommonUtils.getListJavaScriptArray(reportForm.getDepartmentIds(),reportForm.getDepartmentNames())%>;
								var m = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
								opts = m.concat(opts);
								selectBoxDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
								selectBoxDepartment.setOnChangeHandler('onChangeDepartment');
								document.write(selectBoxDepartment.getHtml());
								selectBoxDepartment.init();
							</script>
						</td>
					</tr>
				</table>
				<div id="divSelectSpecificSubDepartment" style="display:none;">
					<table class="innerReport" >
						<tr>
							<td class="label">
								<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) %>:&nbsp;
							</td>
							<td>
								<script type="text/javascript">
									var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
									selectBoxSubDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
									selectBoxSubDepartment.setOnChangeHandler('onChangeSubDepartment');
									document.write(selectBoxSubDepartment.getHtml());
									selectBoxSubDepartment.init();
								</script>
							</td>
						</tr>
					</table>
				</div>
				<div id="divSelectSpecificSubSubDepartment" style="display:none;">
					<table class="innerReport">
						<tr>
							<td class="label">
								<bean:message key="report.label.select" /> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) %>:&nbsp;
							</td>
							<td>
							<script type="text/javascript">
								var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
								selectBoxSubSubDepartment = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
								document.write(selectBoxSubSubDepartment.getHtml());
								selectBoxSubSubDepartment.init();
							</script>
							</td>
						</tr>
					</table>
				</div>
			</div>
  			<%@include file="reportFilters/pOwnersFilter.jspf" %>
  			<table class="innerReport">
				<tr>
					<td class="label">
						<bean:message key="report.label.position_recruiters" />:&nbsp;
					</td>
					<td>
						<script type="text/javascript">
							var opts = new Array();
							opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL%>','<bean:message key="common.all"/>');
							opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_USER%>','<bean:message key="report.label.filter_specific_user"/>');
							selectUserFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
							document.write(selectUserFilter.getHtml());
							selectUserFilter.setOnChangeHandler('onChangeUserFilter');
							selectUserFilter.init();
						</script>
					</td>
				</tr>
			</table>
			<div id="usersFilterDiv" style="display:none;padding: 0px;">	
			<table class="innerCustomReport" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td style="height: 100px; padding-top: 10px;"  >
						<table cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td >
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<input id="userFilter" name="userFilter" type="text" size="61" value="Filter" style="width:317px;color: grey; border-bottom: 0px;" onclick="onFilterFocus('userFilter','Filter');" onblur="onFilterUnfocus('userFilter','Filter')"/>
											</td>
										</tr>
										<tr>
											<td class="gridborder" width="300px;">
												<div id="USERS_FILTER_GRID" style="width:320px; height: 80px;"></div>
											</td>
										</tr>
									</table>
								</td>
								<td width="42px" align="center">	<a href="#" onclick="javascript: selectItem(usersFilter,selectedUsersFilter);return false;" title="<bean:message key='common.add' />" >
										<img src="images/ico_rightarrow.gif"  border="0" />
									</a>
									<br/> 
									<a href="#" onclick="javascript: deselectItem(selectedUsersFilter,usersFilter);return false;" title="<bean:message key='common.remove' />" >
										<img src="images/ico_leftarrow.gif"  border="0" />
									</a> </td>
								<td >
									<table cellpadding="0" cellspacing="0">
										<tr>
											<td class="gridborder">
												<div id="SELECTES_USERS_FILTER_GRID" style="width:320px;height: 100px;"></div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</div>
			<table class="innerReport">
				<tr>
			       <td class="label">
	  		          <bean:message key="report.label.user_activity.stage" /> :	
	  		       </td>
	  		       <td>		  		          
	  		          <img src="images/checkboxchecked.gif" id="shortlist" onclick="javascript: changeStage(this);">&nbsp;<bean:message key="report.label.shortlist"/>&nbsp;&nbsp;
	  		          <img src="images/checkboxchecked.gif" id="select" onclick="javascript: changeStage(this);">&nbsp;<bean:message key="report.label.select"/>&nbsp;&nbsp;
	  		          <img src="images/checkboxchecked.gif" id="hire" onclick="javascript: changeStage(this);">&nbsp;<bean:message key="report.label.hire"/>&nbsp;&nbsp;
	  		       </td>
			    </tr>
	 		</table>
	 	</td>
	</tr>		
	<tr>				  			
		<td class="head" colspan="2">
			<b><bean:message key="report.label.report_format" /></b>
		</td>
	</tr>
	<tr>				  			
		<td>
			<table class="innerReport">
				<tr>
					<td>
						<img src="images/checkedradiobutton.gif" name='reportFormat' id='reportFormat_<%=ReportConstants.FORMAT_HTML%>' 
							 onclick="javascript:onFormatChange(this,'<%=ReportConstants.FORMAT_HTML%>');" 
							 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_html"/>&nbsp;&nbsp;
						<img src="images/radiobutton.gif" name='reportFormat' id='reportFormat_<%=ReportConstants.FORMAT_PDF%>' 
							 onclick="javascript:onFormatChange(this,'<%=ReportConstants.FORMAT_PDF%>');" 
							 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_pdf"/>&nbsp;&nbsp;
						<img src="images/radiobutton.gif" name='reportFormat' id='reportFormat_<%=ReportConstants.FORMAT_EXCEL%>' 
							 onclick="javascript:onFormatChange(this,'<%=ReportConstants.FORMAT_EXCEL%>');" 
							 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_excel"/>&nbsp;&nbsp;
						<img src="images/radiobutton.gif" name='reportFormat' id='reportFormat_<%=ReportConstants.FORMAT_PRE_FORMATTED%>' 
							 onclick="javascript:onFormatChange(this,'<%=ReportConstants.FORMAT_PRE_FORMATTED%>');" 
							 style="margin-bottom:-1px;"/>&nbsp;<bean:message key="report.label.format_pre"/>&nbsp;&nbsp;
					</td>
					<td id="templatesDiv" width="60%" style="display: none;">
						<script type="text/javascript">
							 var templatesOpts = <%=(String)request.getAttribute("reportTemplateJsArray")%>;
							 var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
							 templatesOpts = opt.concat(templatesOpts);
			                 selectReportTempales = new SelectBox(templatesOpts,'','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
			                 document.write(selectReportTempales.getHtml());
			                 selectReportTempales.init();
						</script>
					</td>
				</tr>
			</table>
		</td>
   	</tr>
   	<tr id="fieldsDivHeader">				  			
		<td class="head">
			<b><bean:message key="report.label.select_fields" /></b>
		</td>
	</tr>
	<tr id="fieldsDiv">
		<td>
			<table cellpadding="0" cellspacing="0" class="innerReport">
				<tr>
					<td >
						<table cellpadding="0" cellspacing="0" style="padding-left: 6px;">
							<tr>
								<td class="gridborder">
									<div id="FIELDS_GRID" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td width="34px" align="center">
						<a href="#" onclick="javascript: selectItem(fieldsGrid,selectedFieldsGrid);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
						<a href="#" onclick="javascript: deselectItem(selectedFieldsGrid,fieldsGrid);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a> 
					</td>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td class="gridborder">
									<div id="FIELDS_GRID_SELECTED" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
   </table>
   <br>
   <table cellspacing="0" cellpadding="0" border="0"  width="100%">
   	<tr>
   		<td>  			
	   		<div class="navBtn" style="float:left;margin-right:5px;margin-top:5px;">
				<a href="#" style="width:120px;" class="active" onclick="javascript:submitForm();">
				<span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.view_report"/></a>
			</div>	
   		</td>
	</tr>
	</table>	
	<br/>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
</html:form>
<script language="JavaScript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function onChangeFilter(val){
	val = selectFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		displayHidden("1");		
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		displayHidden("2");
	}else{
		displayHidden("0");
	}
}
function onChangeUserFilter(){
	val = selectUserFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_ALL%>"){
		hideUserFilter();		
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
		showUserFilter();
	}
}

function showUserFilter(){
	$("usersFilterDiv").style.display="";
}

function hideUserFilter(){
	$("usersFilterDiv").style.display="none";
}


function displayHidden(val){
	if(val=="0"){
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=="1"){
		$("divSelectSpecificPosition").style.display="block";
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=="2"){
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="block";
	} 
}

function validateSetFormFields(){
	//first do the validations
	document.reportForm.positionId.value='';
	document.reportForm.selectedPositionOwnerIds.value='';
	document.reportForm.departmentId.value='';
	document.reportForm.subDepartmentId.value='';
	document.reportForm.subSubDepartmentId.value='';
	
	//if specific department or position option selected
	val = selectFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION%>"){
		if(selectPosition.getSelectedId()=="-1"){
			alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
			return false;
		}else{
			var positionTitle = selectPosition.getText(selectPosition.getSelectedIndex());
			document.reportForm.positionTitle.value=positionTitle;
			document.reportForm.positionId.value=selectPosition.getSelectedId();
		}	
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>"){
		if(selectBoxDepartment.getSelectedId()=="-1"){
			alert("Please select "+'<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
			return false;
		}else{
			var departmentTitle = selectBoxDepartment.getText(selectBoxDepartment.getSelectedIndex());
			document.reportForm.departmentTitle.value=departmentTitle;
			document.reportForm.departmentId.value=selectBoxDepartment.getSelectedId();
			if(selectBoxSubDepartment.getSelectedId()!="-1"){
				document.reportForm.subDepartmentId.value=selectBoxSubDepartment.getSelectedId();
			}
			if(selectBoxSubSubDepartment.getSelectedId()!="-1"){
				document.reportForm.subSubDepartmentId.value=selectBoxSubSubDepartment.getSelectedId();
			}
		}	
	}	
	
	if(!setSelectedFields())
		return false;
	
	document.reportForm.filterId.value=val;
	document.reportForm.dateRange.value=selectDateRange.getSelectedId();
	if(document.reportForm.reportFormat.value==preFormattedFormat && selectReportTempales.getSelectedId()=="-1"){
		alert('<bean:message key="report.error.select_template" />');
		return false;
	}else{
		document.reportForm.reportTemplateId.value=selectReportTempales.getSelectedId();
	}
	if(selectUserFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_USER%>"){
		if(selectedUsersFilter.getAllItemIds(',')==''){
			alert('<bean:message key="report.error.select_User" />');
			return false;
		}else{
			document.reportForm.selectedUserIds.value=selectedUsersFilter.getAllItemIds(',');
		}
	}else {
		document.reportForm.selectedUserIds.value='';
	}
	
	if(!setPositionOwnerFilter()){
		return false;
	}

	if(!setSelectedStages())
		return false;
	
	return true;
}

function setPositionOwnerFilter(){
	if(selectPositionOwnerFilter && selectPositionOwnerFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_POSITION_OWNER%>"){
		if(selectedPOwnersFilter.getAllItemIds(',')==''){
			alert('<bean:message key="common.please_select" />'+' '+'<bean:message key="global.position_owner" />');
			return false;
		}else{
			document.reportForm.selectedPositionOwnerIds.value=selectedPOwnersFilter.getAllItemIds(',');
		}
	}else {
		document.reportForm.selectedPositionOwnerIds.value='';
	}
	return true;
}


function setSelectedFields(){
	if(document.reportForm.reportFormat.value!=preFormattedFormat) {
		var selectedFields = selectedFieldsGrid.getAllItemIds(',');
		var arr =  selectedFields.split(",");
		var selectedFieldNames='';
		
		for(var i=0;i<arr.length;i++){
			selectedFieldNames = selectedFieldNames + selectedFieldsGrid.getUserData(arr[i],"key")+",";
		}
		if(selectedFieldNames=='' || selectedFieldNames==','){
			alert('<bean:message key="report.error.select_Fields" />');
			return false;
		}else {
			document.reportForm.fieldIds.value=selectedFieldNames;
		}
	}
	return true;
}

function setSelectedStages(){
	var stages = '';
	if($('shortlist').src.indexOf(chkboxchked) != -1) {
		stages = appendToStages('<%=PositionConstants.STEP_LEVEL_SHORTLIST%>',stages);
	}
	if($('select').src.indexOf(chkboxchked) != -1) {
		stages = appendToStages('<%=PositionConstants.STEP_LEVEL_SELECT%>',stages);
	}
	if($('hire').src.indexOf(chkboxchked) != -1) {
		stages = appendToStages('<%=PositionConstants.STEP_LEVEL_ACCEPT%>',stages);
	}
	if(stages==''){
		alert('<bean:message key="report.error.select_stage" />');
		return false;
	}else {
		document.reportForm.stages.value=stages;
	}
	return true;
}

function appendToStages(stage,stages){
	if(stages==''){
		stages=stage;
	}else{
		stages+=','+stage;
	}
	return stages;  
}

function onChangeDepartment(){
  var val = selectBoxDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubdepartments, reportError);
}

function updateSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubDepartment.reInitialize(opts, '');
		m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
	    selectBoxSubSubDepartment.reInitialize(m, '');
		if(opts.length>1){
			Element.show('divSelectSpecificSubDepartment');
		}else{
			Element.hide('divSelectSpecificSubDepartment');
			Element.hide('divSelectSpecificSubSubDepartment');
		}
		
	}	
}
function onChangeSubDepartment(){
  var val = selectBoxSubDepartment.getSelectedId();
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubSubdepartments, reportError);
}
function updateSubSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubSubDepartment.reInitialize(opts, '');
		if(opts.length>1){
			Element.show('divSelectSpecificSubSubDepartment');
		}else{
			Element.hide('divSelectSpecificSubSubDepartment');
		}
		
	}	
}

function submitForm(){
	if(validateSetFormFields()){	
		var d = new Date();
		createCookie('<bean:message key="report.label.hiring_activity"/>'+'_DETAILS', selectedFieldsGrid.getAllItemIds(','), 100);
		document.reportForm.mode.value = 'hiringActivityDetails';
		document.reportForm.target=d;
		document.reportForm.submit();
	}
}

function fetchPositions() {
	var pars = "mode=getPositions";
	var myAjax = ajaxCall("reports.do",'get',pars,updatePositions, reportError);
}

function updatePositions(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) return;
	
	var optsPositions = new Array();
	optsPositions[0] = new SelectOption('-1','<bean:message key="common.selectlist.default"/>');
	var positions = xmlFile.getElementsByTagName("positions")[0];	
	var position=positions.getElementsByTagName("position");
	if(position!=null){
		for(var i=0; i<position.length; i++){
			var nodeId = position[i].getElementsByTagName("id");
			var nodeName = position[i].getElementsByTagName("name");
			var id = nodeId[0].firstChild.nodeValue;
			var name = nodeName[0].firstChild.nodeValue;
			optsPositions[optsPositions.length]=new SelectOption(id,name);
			
		}
	}
	
	selectPosition.reInitialize(optsPositions,"");
}


/**
 * Select Fields Grid Related Functions 
 */
var fieldsGrid = null;
var selectedFieldsGrid = null;
function initFieldsGrid() {	
	fieldsGrid = new dhtmlXGridObject('FIELDS_GRID'); 
	fieldsGrid.imgURL = "images/"; 
	fieldsGrid.setHeader("<bean:message key="common.fields"/>"); 
	fieldsGrid.setInitWidths("200");
	fieldsGrid.setColAlign("left");
	fieldsGrid.setColTypes("ro"); 
	fieldsGrid.setColSorting("str");
	fieldsGrid.enableMultiselect(true);	
	fieldsGrid.attachEvent("onXLE",doOnFieldsGridLoadingEnd);	     
	fieldsGrid.attachEvent("onKeyPress",onFieldsGridKeyPressed);
	fieldsGrid.attachEvent("onRowDblClicked",doOnFieldsGridRowDblClicked);
	fieldsGrid.attachEvent("onRowSelect",doOnFieldsGridRowSelectHandler);
	fieldsGrid.init();
	loadFieldsGrid();
//	fieldsGrid.setSortImgState(true,0,"ASC");
	
	fieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function initSelectedFieldsGrid(){
	selectedFieldsGrid = new dhtmlXGridObject('FIELDS_GRID_SELECTED'); 
	selectedFieldsGrid.imgURL = "images/"; 
	selectedFieldsGrid.setHeader("<bean:message key="common.selected"/> <bean:message key="common.fields"/>"); 
	selectedFieldsGrid.setInitWidths("200");
	selectedFieldsGrid.setColAlign("left");
	selectedFieldsGrid.setColTypes("ro"); 
	selectedFieldsGrid.enableMultiselect(true);	
	selectedFieldsGrid.init();     
	
	selectedFieldsGrid.attachEvent("onKeyPress",onSelectedFieldsGridPressed);
	selectedFieldsGrid.attachEvent("onRowDblClicked",doOnSelectedFieldsGridRowDblClicked);
	selectedFieldsGrid.attachEvent("onRowSelect",doOnSelectedFieldsRowSelectHandler);
	selectedFieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function onFieldsGridKeyPressed(keyCode,ctrl,shift) {
	selectedFieldsGrid.clearSelection();
	onGridObjKeyPressed(fieldsGrid,selectedFieldsGrid,4,keyCode,ctrl,shift);
}

function onSelectedFieldsGridPressed(keyCode,ctrl,shift) {
	fieldsGrid.clearSelection();
	onGridObjKeyPressed(selectedFieldsGrid,fieldsGrid,4,keyCode,ctrl,shift);
}

function doOnFieldsGridRowDblClicked() {
	selectItem(fieldsGrid,selectedFieldsGrid);
}
function doOnSelectedFieldsGridRowDblClicked() {
	deselectItem(selectedFieldsGrid,fieldsGrid);
}

function doOnFieldsGridRowSelectHandler() {
	selectedFieldsGrid.clearSelection();
}

function doOnSelectedFieldsRowSelectHandler() {
	fieldsGrid.clearSelection();
}

function loadFieldsGrid(){
	fieldsGrid.clearAll();
	fieldsGrid.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("fieldsXml"))%>');
}

function setSelectedFromCookie(){
	var vals = readCookie('<bean:message key="report.label.hiring_activity"/>'+'_DETAILS');
	if(vals!='' && vals!=null){
		var arrVals = vals.split(',');
		for(var x=0;x<arrVals.length;x++){
			fieldsGrid.setSelectedRow(arrVals[x],true,false,false);
		}
		selectItem(fieldsGrid,selectedFieldsGrid);
	}
}
function doOnFieldsGridLoadingEnd(){
	setSelectedFromCookie();
}

/**
 * END Select Fields Grid Related Functions 
 */

 /**
  * Users Filter Gird 
  */

var usersFilter = null;
var selectedUsersFilter = null;

function initUsersFilter() {	
	usersFilter = new dhtmlXGridObject('USERS_FILTER_GRID'); 
	usersFilter.imgURL = "images/"; 
	usersFilter.setHeader("User Name"); 
	usersFilter.setInitWidths("300");
	usersFilter.setNoHeader(true);
	usersFilter.setColAlign("left");
	usersFilter.setColTypes("ro");
	usersFilter.setColSorting("requisition_userName_sort");
	//usersFilter.enableMultiselect('true');	
	usersFilter.init();
	loadUsersFilterGrid();	
	usersFilter.sortRows(0,'str',"asc");
	//usersFilter.attachEvent("onXLE",doOnLoadingEndRequisitioner);
	usersFilter.attachEvent("onKeyPress",onUsersFilterKeyPressed);
	usersFilter.attachEvent("onRowSelect",doOnUsersFilterGridRowSelectHandler);
	usersFilter.attachEvent("onRowDblClicked",doOnUsersFilterGirdRowDblClicked);
	
	usersFilter.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function initSelectedUsersFilter(){
	selectedUsersFilter = new dhtmlXGridObject('SELECTES_USERS_FILTER_GRID'); 
	selectedUsersFilter.imgURL = "images/"; 
	selectedUsersFilter.setHeader("User Name"); 
	selectedUsersFilter.setInitWidths("300");
	selectedUsersFilter.setColAlign("left");
	selectedUsersFilter.setColTypes("ro"); 	
	//selectedUsersFilter.enableMultiselect('true');
	selectedUsersFilter.setNoHeader(true);
	selectedUsersFilter.setColSorting("requisition_userName_sort");
	selectedUsersFilter.init();
	selectedUsersFilter.sortRows(0,'str',"asc");
	selectedUsersFilter.setSortImgState(true,0,"ASC");	
	selectedUsersFilter.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	selectedUsersFilter.attachEvent("onKeyPress",onSelectedUsersFilterKeyPressed);
	selectedUsersFilter.attachEvent("onRowSelect",doOnSelectedUsersFilterGridRowSelectHandler);
	selectedUsersFilter.attachEvent("onRowDblClicked",doOnSelectedUsersFilterGridRowDblClicked);
}

function loadUsersFilterGrid(){
	usersFilter.parse('<%=Utils.escapeJavaScript((String) request.getAttribute("usersXml"))%>');
}

function onUsersFilterKeyPressed(keyCode,ctrl,shift) {	
	var text = (usersFilter.cells(usersFilter.getSelectedId(),0)).getValue();
	selectedUsersFilter.clearSelection();
	onGridObjKeyPressed(usersFilter,selectedUsersFilter,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(usersFilter, text);
	}
}

function onSelectedUsersFilterKeyPressed(keyCode,ctrl,shift) {
	usersFilter.clearSelection();
	onGridObjKeyPressed(selectedUsersFilter,usersFilter,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(usersFilter);
	}
}

function doOnUsersFilterGridRowSelectHandler() {
	selectedUsersFilter.clearSelection();
}
function doOnSelectedUsersFilterGridRowSelectHandler() {
	usersFilter.clearSelection();
}

function doOnUsersFilterGirdRowDblClicked() {	
	var text = (usersFilter.cells(usersFilter.getSelectedId(),0)).getValue();
	selectItem(usersFilter,selectedUsersFilter);
	removeIdFromBackUp(usersFilter, text);	
}

function doOnSelectedUsersFilterGridRowDblClicked() {	
	selectItem(selectedUsersFilter,usersFilter);
	resetFilterBackUp(usersFilter);
}

function onCriteriaChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		case Event.KEY_UP:
		case Event.KEY_DOWN: 
		break;
		case Event.KEY_PAGEUP:
		case Event.KEY_PAGEDOWN:
		break;
		case Event.KEY_RETURN:
		break;
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			usersFilter.filterBy(0, $('userFilter').value, false);		
		}
	}
}
 /**
  * END Users Filter Gird 
  */

var checkedRadioImg = 'images/checkedradiobutton.gif';
var radioImg = 'images/radiobutton.gif';
var preFormattedFormat = '<%=ReportConstants.FORMAT_PRE_FORMATTED%>';
function onFormatChange(obj,reportFormat){
	var imgs = document.getElementsByName(obj.name);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("reportFormat_") > -1) {
			if( theImage.id == 'reportFormat_'+reportFormat){
				theImage.src = checkedRadioImg;
				document.reportForm.reportFormat.value=reportFormat;
			}else{
				theImage.src = radioImg;
			}
		}
	}

	if(reportFormat==preFormattedFormat){
		$('fieldsDiv').style.display='none';
		$('fieldsDivHeader').style.display='none';
		$('templatesDiv').style.display='';
	}else{
		$('fieldsDiv').style.display='';
		$('fieldsDivHeader').style.display='';
		$('templatesDiv').style.display='none';
	}
}

function onReportTypeChange(obj,reportType){
	if(reportType=='<%=ReportConstants.REPORT_TYPE_SUMMARY%>'){
		var reportName = '<%=ReportVersionConstants.REPORT_HIRING_ACTIVITY%>';
		param = "mode=reportFilter&reportName="+reportName+'&reportType='+reportType;
		window.location.href="reports.do?"+param;
	}
}

function onDateRangeChange(val){
	customDisplayHidden(selectDateRange.getSelectedId());
}

function customDisplayHidden(val){
	if(val=='<%=ReportConstants.CUSTOM%>'){
		$("divSelectDateRange").style.display="block";
	} else {
		$("divSelectDateRange").style.display="none";
	}  
}

function changeStage(obj){
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
	} else {
		obj.src=chkboxchked;
	}
}



//DATE FORMATTER CODE AND FUNCTIONS
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
	  if(!dtf.checkDate(obj)){
		obj.select();
		alert('<bean:message key="calendar.error.invalid_date"/>');
		obj.focus();
		return false;
	  }else {
		return true;
	  }
	}
	return true;
}

function onChangePositionOwnerFilter(index,obj){
	val = obj.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_ALL%>"){
		hidePOFilter();		
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_POSITION_OWNER%>"){
		showPOFilter();
	}
}

function showPOFilter(){
	$("pOwnerFilterDiv").style.display="";
}

function hidePOFilter(){
	$("pOwnerFilterDiv").style.display="none";
}

function doOnLoad() {
	fetchPositions();
	initSelectedFieldsGrid();
	initFieldsGrid();
	initUsersFilter();
	initSelectedUsersFilter();
	initPOwnersFilter();
	initSelectedPOwnersFilter();
	Event.observe($('userFilter'), "keyup", onCriteriaChange.bindAsEventListener(this));
	Event.observe($('pOwnersFilter'), "keyup", onPOwnersCriteriaChange.bindAsEventListener(this));
}

window.onload = doOnLoad;
</script>