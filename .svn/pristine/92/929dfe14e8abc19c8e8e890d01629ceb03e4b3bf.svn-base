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
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/cookies.js"></script>	
<script type="text/javascript">
var selectFilter=null;
var selectPosition=null;
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
</script>
<bean:define id="reportForm" name="reportForm" type="com.talentPool.reports.form.ReportForm"></bean:define>
<html:form action="/reports">
<html:hidden property="reportName"/>
<html:hidden property="t"/>
<html:hidden property="st"/>
<html:hidden property="mode" />
<html:hidden property="filterId"/>
<html:hidden property="fieldIds"/>
<html:hidden property="positionId"/>
<html:hidden property="departmentId"/>
<html:hidden property="subDepartmentId"/>
<html:hidden property="subSubDepartmentId"/>
<html:hidden property="positionTitle"/>
<html:hidden property="departmentTitle"/>
<html:hidden property="reportFormat" name="reportForm"/>
<html:hidden property="reportTemplateId" name="reportForm"/>
<html:hidden property="selectedUserIds" name="reportForm" />
<div class="contentDiv">
   <table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:150px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span><bean:message key="report.label.pending_offer" /></div></td> 
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
			 <table class="innerReport">
  			 	<tr>
  			 		<td>
						<table cellspacing="0" cellpadding="0">
						<tr>
							<td style="width: 80px;">
								<bean:message key="report.label.search_in" />:&nbsp;
							</td>
							<td>
								<script type="text/javascript">
									var opts = new Array();
									opts[0] = new SelectOption('<%=ReportConstants.FILTER_OPEN_POSITIONS%>','<bean:message key="common.open_positions"/>');
									opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_POSITION%>','<bean:message key="common.specific_position"/>');
									opts[2] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_DEPARTMENT%>','<bean:message key="report.label.filter_specific_department"/> <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
									selectFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_OPEN_POSITIONS%>','images/btn_dropdown.gif',{namesonly:false, width:'175px', size:20});
									document.write(selectFilter.getHtml());
									selectFilter.setOnChangeHandler('onChangeFilter');
									selectFilter.init();
								</script>
							</td>
						</tr>
						</table>		  			  
					</td>
				</tr>
  			 	<tr>
  			 		<td>
						<div id="divSelectSpecificPosition" style="display:none;">
						<table cellspacing="0" cellpadding="0">
						<tr>
							<td>
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
					
						<table cellspacing="0" cellpadding="0">
						<tr>
							<td style="width: 80px;">
								<bean:message key="report.label.recruiters" />:&nbsp;
							</td>
							<td>
								<script type="text/javascript">
									var opts = new Array();
									opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL%>','<bean:message key="common.all"/>');
									opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_USER%>','<bean:message key="report.label.filter_specific_user"/>');
									selectUserFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL%>','images/btn_dropdown.gif',{namesonly:false, width:'175px', size:20});
									document.write(selectUserFilter.getHtml());
									selectUserFilter.setOnChangeHandler('onChangeUserFilter');
									selectUserFilter.init();
								</script>
							</td>
						</tr>
						</table>		  			  
					</td>
				</tr>
			</table>	
		 </td>
	</tr>
	<tr id="usersFilterDivHeader" style="display: none;">				  			
		<td class="head" colspan="2">
			<b><bean:message key="report.label.select_recruiters" /></b>
		</td>
	</tr>
	<tr id="usersFilterDiv" style="display: none;">
		<td colspan="2">
		   	<table cellspacing="0" cellpadding="0" style="padding-left: 5px;" class="innerReport">
				<tr>
					<td style="vertical-align: top;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<input id="userFilter" name="userFilter" type="text" size="41" value="Filter" style="width:216px;color: grey; border-bottom: 0px;" onclick="onFilterFocus('userFilter','Filter');" onblur="onFilterUnfocus('userFilter','Filter')"/>
							</td>
						</tr>
						<tr>
							<td class="gridborder">
								<div id="USERS_FILTER_GRID" style="width:219px;height:80px; margin-top:-4px;overflow: visible;"></div>
							</td>
						</tr>
					</table>			
					</td>		
					<td style="padding: 10px;">					
						<a href="#" onclick="javascript: selectItem(usersFilter,selectedUsersFilter);return false;" title="<bean:message key='common.add' />" >
							<img src="images/ico_rightarrow.gif"  border="0" />
						</a>
						<br/> 
						<a href="#" onclick="javascript: deselectItem(selectedUsersFilter,usersFilter);return false;" title="<bean:message key='common.remove' />" >
							<img src="images/ico_leftarrow.gif"  border="0" />
						</a> 
					</td>					
					<td style="vertical-align: top;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td class="gridborder">
							<div id="SELECTES_USERS_FILTER_GRID" style="width:220px;height:97px;overflow: visible;"></div>
							</td>
						</tr>
					</table>
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
		<td >
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
</html:form>
<script language="JavaScript">
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
	$("usersFilterDivHeader").style.display="";
	$("usersFilterDiv").style.display="";
}

function hideUserFilter(){
	$("usersFilterDivHeader").style.display="none";
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
		createCookie('<bean:message key="report.label.pending_offer"/>', selectedFieldsGrid.getAllItemIds(','), 100);
		document.reportForm.mode.value = 'pendingOffer';
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
	var vals = readCookie('<bean:message key="report.label.pending_offer"/>');
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
	usersFilter.setInitWidths("200");
	usersFilter.setNoHeader(true);
	usersFilter.setColAlign("left");
	usersFilter.setColTypes("ro");
	usersFilter.setColSorting("requisition_userName_sort");
	usersFilter.enableMultiselect('true');	
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
	selectedUsersFilter.setInitWidths("200");
	selectedUsersFilter.setColAlign("left");
	selectedUsersFilter.setColTypes("ro"); 	
	selectedUsersFilter.enableMultiselect('true');
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

function doOnLoad() {
	fetchPositions();
	initSelectedFieldsGrid();
	initFieldsGrid();
	initUsersFilter();
	initSelectedUsersFilter();
	Event.observe($('userFilter'), "keyup", onCriteriaChange.bindAsEventListener(this));
}

window.onload = doOnLoad;
</script>