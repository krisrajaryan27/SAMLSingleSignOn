<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="com.talentPool.reports.ReportConstants, com.talentPool.reports.form.ReportForm, com.talentPool.common.utils.CommonUtils"%>
<%@ page import="com.talentPool.user.manager.ModuleSet,com.talentPool.common.properties.TPApplicationProperties, com.talentPool.reports.ReportUtils"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.reports.ReportVersionConstants"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/monthyearOptions.js" type="text/javascript"></script>	
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script src="js/cookies.js"></script>
<script type="text/javascript">
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
</script>
<%
	ReportForm reportForm = (ReportForm)request.getAttribute("reportForm");
%>
<div class="contentDiv">
	<html:form action="/reports">
	<html:hidden property="reportName"/>
	<html:hidden property="mode" />
	<html:hidden property="positionId"/>
	<html:hidden property="departmentId"/>
	<html:hidden property="subDepartmentId"/>
	<html:hidden property="subSubDepartmentId"/>
	<html:hidden property="positionTitle"/>
	<html:hidden property="departmentTitle"/>
	<html:hidden property="filterId"/>
	<html:hidden property="fieldIds"/>
	<html:hidden property="dateRange" name="reportForm"/>
	<html:hidden property="reportFormat" name="reportForm"/>
	<html:hidden property="reportTemplateId" name="reportForm"/>
   	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><%=ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_TIME_TO_HIRE)%></div></td> 
	  </tr> 
	</table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
		<tr>				  			
			<td class="head" colspan="2">
				<b><bean:message key="report.label.filter" /></b>
			</td>
		</tr>
		<tr>				  			
			<td colspan="2">
				<table class="innerReport" width="100%">
					<tr>
			   			<td>
			   				<table cellpadding="0" cellspacing="0">
			   				<tr>			   											
					    		<td style="width: 100px;">
						    		<bean:message key="monthly.joining.label.date_joined" />:&nbsp; </td>
						   		<td>
			                  	 <script type="text/javascript">
					                    var optDate = <%=ReportUtils.getJSArrayForDateRange()%>;
					                    selectDateRange = new SelectBox(optDate,'<%=ReportConstants.TODAY%>','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
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
				  		        		  			<bean:message key="report.label.from" /> :&nbsp; 
				  		        		  		</td>
				  		        		  		<td>
				  		        					<html:text property="fromDate" styleId="fromDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('fromDate'),'fromDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
												</td>
												<td style="width: 20px;"></td>
												<td>
					  		          				<bean:message key="report.label.to" /> :&nbsp; </td>
					  		          			<td>	
					  		          				<html:text property="toDate" styleId="toDate" size="12" maxlength="10" onblur="getFormattedDate(this);"/><img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('toDate'),'toDate','dd/MM/yyyy'); return false;" class="CalImg" name="imgCal" id="imgCal" style="margin-bottom:-3px;"/>
				  		      					</td>
				  		      				</tr>
					   						</table>
									</div>
								</td>
							</tr>
							</table>
	   					</td>
	  				</tr>
	 			    <tr>
	 			    	<td>
	 			    		<table cellpadding="0" cellspacing="0">
			   				<tr>
			   					<td style="width: 100px;">
				                   <bean:message key="report.label.search_in" />:&nbsp; 
				                </td>
				                <td>
				                   <script type="text/javascript">
						                    var opts = new Array();
						                    opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL_POSITIONS%>','<bean:message key="common.positions_all"/>');
						                    opts[1] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
						                    opts[2] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
						                    opts[3] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
						                    selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
						                    document.write(selectFilter.getHtml());
						                    selectFilter.setOnChangeHandler('onChangeFilter');
						                    selectFilter.init();
				                    </script>
				                </td>
				        	</tr>
				        	</table>         
				     	
							<div id="divSelectSpecificPosition" style="display:none;">
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td style="width: 100px;">
									<bean:message key="common.select_position" />:&nbsp;
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
							<table cellspacing="0" cellpadding="0">
							<tr>
								<td>
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
								<table cellspacing="0" cellpadding="0">
								<tr>
									<td>
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
								<table cellspacing="0" cellpadding="0">
								<tr>
									<td>
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
			<td width="40%">
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
	                 selectReportTempales = new SelectBox(templatesOpts,'','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:20});
	                 document.write(selectReportTempales.getHtml());
	                 selectReportTempales.init();
				</script>
			</td>
		</tr>
		<tr id="fieldsDivHeader">				  			
			<td class="head" colspan="2">
				<b><bean:message key="report.label.select_fields" /></b>
			</td>
		</tr>
		<tr id="fieldsDiv">				  			
			<td colspan="2">
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
	<br>
	</html:form>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function validateSetFormFields(){
	document.reportForm.positionId.value='';
	document.reportForm.departmentId.value='';
	document.reportForm.subDepartmentId.value='';
	document.reportForm.subSubDepartmentId.value='';

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
			alert('<bean:message key="report.error.select_department"/>');
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
	document.reportForm.mode.value = 'timeToHire';
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
	if( validateSetFormFields()){
		var d = new Date();
		createCookie('<bean:message key="report.label.time_to_hire"/>', selectedFieldsGrid.getAllItemIds(','), 100);
		document.reportForm.target=d;
		document.reportForm.submit();
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

function onChangeFilter(val){	
	displayHidden(selectFilter.getSelectedId());
}

function displayHidden(val){
	if(val=='<%=ReportConstants.FILTER_SPECIFIC_POSITION%>'){
		$("divSelectSpecificPosition").style.display="block";
		$("divSelectSpecificDepartment").style.display="none";
	}else if(val=='<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>'){
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="block";
	} else {
		$("divSelectSpecificPosition").style.display="none";
		$("divSelectSpecificDepartment").style.display="none";
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
	fieldsGrid.setSortImgState(true,0,"ASC");
	
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
	var vals = readCookie('<bean:message key="report.label.time_to_hire"/>');
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

function doOnLoad() {
	fetchPositions();
	initSelectedFieldsGrid();
	initFieldsGrid();
}

window.onload = doOnLoad;
</script>