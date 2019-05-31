<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.reports.ReportConstants"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%>
<%@ page import="java.util.ArrayList"%>

<script src="js/tpSelectListFunctions.js"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>

<script type="text/javascript">
var selectSourceCategoryFilter =null
var selectSourceFilter =null
</script>

<table class="innerReport">
<tr>
 	<td class="label"><bean:message key="common.source_category"/>:</td>
	<td>
		<script type="text/javascript">
			var opts = new Array();
			opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL%>','<bean:message key="common.all"/>');
			<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
				opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_SOURCE_CATEGORY%>','<bean:message key="common.specific"/> <bean:message key="common.source_category"/>');
			<%}%>
		
			selectSourceCategoryFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
			document.write(selectSourceCategoryFilter.getHtml());
			selectSourceCategoryFilter.setOnChangeHandler('onChangeSourceCategoryFilter');
			selectSourceCategoryFilter.init();
		</script>
	</td>
</tr>
</table>

<div id="divSelectSpecificSourceCategory" style="display:none;">	
<table class="sourceFilterGrid">
		<tr>
			<td>
			<table cellpadding="0" cellspacing="0" >
				<tr>
					<td class="gridborder" width="300px;">
						<div class="leftGrid" id="SOURCE_CATEGORY_LEFT" style="width:320px; height: 100px;"></div>
					</td>
				</tr>
			</table>
			</td>
			<td class="arrows" align="center">																												
				<a href="#" class="active" onclick="javascript: selectItem(sourceCategoryLeftGrid,sourceCategoryRightGrid);return false;" title="Add" >					
				<img src="images/ico_rightarrow.gif"  border="0" /></a>
				<br/> 
				<a href="#" class="active" onclick="javascript: deselectItem(sourceCategoryRightGrid,sourceCategoryLeftGrid);return false;" title="Remove" >					
				<img src="images/ico_leftarrow.gif"  border="0" /></a>
			</td>
			<td >
			<table cellpadding="0" cellspacing="0" >
				<tr>
					<td class="gridborder">
						<div class="rightGrid" id="SOURCE_CATEGORY_RIGHT" style="width:320px;height: 100px;"></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
</table>
</div>
<table class="innerReport">
<tr>
	<td class="label"><bean:message key="common.source"/>:</td>
  	<td>
		<script type="text/javascript">
			var opts = new Array();
			opts[0] = new SelectOption('<%=ReportConstants.FILTER_ALL%>','<bean:message key="common.all"/>');
			<% if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,((PermissionSet)request.getSession(false).getAttribute("permissionSet")).isSHOW_CONFIDENTIAL_DATA()) ){%>
				opts[1] = new SelectOption('<%=ReportConstants.FILTER_SPECIFIC_SOURCE%>','<bean:message key="common.specific"/> <bean:message key="common.sources"/>');
			<%}%>	
			selectSourceFilter = new SelectBox(opts,'<%=ReportConstants.FILTER_ALL%>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:20});
			document.write(selectSourceFilter.getHtml());
			selectSourceFilter.setOnChangeHandler('onChangeSourceFilter');
			selectSourceFilter.init();
		</script>
	</td>
</tr>
</table>

<div id="divSelectSpecificSource" style="display:none;">
<table class="sourceFilterGrid">	

		<tr>
			<td>
				<table cellpadding="0" cellspacing="0" class="innerReport">
				<tr>
					<td class="gridborder" width="300px;">
						<div class="leftGrid" id="SOURCE_LEFT"></div>
					</td>
				</tr>
				</table>
			</td>
			<td class="arrows" align="center">																												
				<a href="#" class="active" onclick="javascript: selectItem(sourceLeftGrid,sourceRightGrid);return false;" title="Add" >					
				<img src="images/ico_rightarrow.gif"  border="0" /></a>
				<br/> 
				<a href="#" class="active" onclick="javascript: deselectItem(sourceRightGrid,sourceLeftGrid);return false;" title="Remove" >					
				<img src="images/ico_leftarrow.gif"  border="0" /></a>													
			</td>
			<td>
				<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
						<div class="rightGrid" id="SOURCE_RIGHT"></div>
					</td>
				</tr>
				</table>
			</td>
		</tr>

</table>
</div>


<script language="JavaScript">
var sourceCategoryLeftGrid;
var sourceCategoryRightGrid;

//GRID For SourceCategory
function initSourceCategoryGrids() {
	sourceCategoryLeftGrid = new dhtmlXGridObject('SOURCE_CATEGORY_LEFT'); 
	sourceCategoryLeftGrid.imgURL = "images/"; 
	sourceCategoryLeftGrid.setHeader("sourceCategory"); 
	sourceCategoryLeftGrid.setInitWidths("295");
	sourceCategoryLeftGrid.setNoHeader(true);
	sourceCategoryLeftGrid.setColAlign("left");
	sourceCategoryLeftGrid.setColTypes("ro");
	sourceCategoryLeftGrid.setColSorting("source_category_name_sort");
	//sourceCategoryLeftGrid.enableMultiselect('true');	
	sourceCategoryLeftGrid.init();
	loadGridSourceCategory();	
	//sourceCategoryLeftGrid.sortRows(0,'str',"asc");
	sourceCategoryLeftGrid.enableSmartRendering(true);
	sourceCategoryLeftGrid.setSortImgState(true,0,"ASC");	
	//sourceCategoryLeftGrid.setAwaitedRowHeight(20);
	sourceCategoryLeftGrid.attachEvent("onKeyPress",onSourceCategoryLeftGridKeyPressed);
	sourceCategoryLeftGrid.attachEvent("onRowSelect",doOnSourceCategoryLeftGridRowSelectHandler);
	sourceCategoryLeftGrid.attachEvent("onRowDblClicked",doOnSourceCategoryLeftGridRowDblClicked);
	
	sourceCategoryLeftGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	sourceCategoryRightGrid = new dhtmlXGridObject('SOURCE_CATEGORY_RIGHT'); 
	sourceCategoryRightGrid.imgURL = "images/"; 
	sourceCategoryRightGrid.setHeader("sourceCategory"); 
	sourceCategoryRightGrid.setInitWidths("295");
	sourceCategoryRightGrid.setColAlign("left");
	sourceCategoryRightGrid.setColTypes("ro"); 	
	//sourceCategoryRightGrid.enableMultiselect('true');
	sourceCategoryRightGrid.setNoHeader(true);
	sourceCategoryRightGrid.setColSorting("source_category_name_sort");
	sourceCategoryRightGrid.init();
	sourceCategoryRightGrid.sortRows(0,'str',"asc");
	sourceCategoryRightGrid.setSortImgState(true,0,"ASC");	
	sourceCategoryRightGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	sourceCategoryRightGrid.attachEvent("onKeyPress",onSourceCategoryRightGridKeyPressed);
	sourceCategoryRightGrid.attachEvent("onRowSelect",doOnSourceCategoryRightGridRowSelectHandler);
	sourceCategoryRightGrid.attachEvent("onRowDblClicked",doOnSourceCategoryRightGridRowDblClicked);
	initSourceGrids();	
}

function source_category_name_sort(a,b,order,aId,bId) {
	a0 =sourceCategoryLeftGrid.getUserData(aId,"sourceCategoryName");
	b0 = sourceCategoryLeftGrid.getUserData(bId,"sourceCategoryName");	
	return sort_data(a0,b0,order);
}

function loadGridSourceCategory(){	
	sourceCategoryLeftGrid.clearAll();
	sourceCategoryLeftGrid.loadXML("reports.do?mode=XMLSourceCategories");	
}

function onSourceCategoryLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceCategoryLeftGrid,sourceCategoryRightGrid,4,keyCode,ctrl,shift);
}

function doOnSourceCategoryLeftGridRowSelectHandler() {
	sourceCategoryRightGrid.clearSelection();
}

function doOnSourceCategoryLeftGridRowDblClicked() {	
	var text = (sourceCategoryLeftGrid.cells(sourceCategoryLeftGrid.getSelectedId(),0)).getValue();
	selectItem(sourceCategoryLeftGrid,sourceCategoryRightGrid);	
	loadSourcesForCategories();
}

//for right SourceCategory grid
function onSourceCategoryRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceCategoryRightGrid,sourceCategoryLeftGrid,4,keyCode,ctrl,shift);
}

function doOnSourceCategoryRightGridRowSelectHandler() {
	sourceCategoryLeftGrid.clearSelection();
}

function doOnSourceCategoryRightGridRowDblClicked() {	
	var text = (sourceCategoryRightGrid.cells(sourceCategoryRightGrid.getSelectedId(),0)).getValue();
	selectItem(sourceCategoryRightGrid,sourceCategoryLeftGrid);
	loadSourcesForCategories();
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function onChangeSourceCategoryFilter(val){
	val = selectSourceCategoryFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_ALL%>"){
		$("divSelectSpecificSourceCategory").style.display="none";		
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_SOURCE_CATEGORY%>"){
		$("divSelectSpecificSourceCategory").style.display="block";		
	}	
	initSourceCategoryGrids();
	loadSourcesForCategories();
}

function validateSourceCategoryFilter(){	
	//if specific SourceCategory option selected	
	var filterVal = selectSourceCategoryFilter.getSelectedId();	
	document.reportForm.sourceCategoryFilter.value=filterVal;
	if(filterVal=="<%=ReportConstants.FILTER_SPECIFIC_SOURCE_CATEGORY%>"){
		var selectedSourceCategory = sourceCategoryRightGrid.getAllItemIds(',');		
		if(!selectedSourceCategory){
			alert('<bean:message key="common.please_select"/> <bean:message key="common.source_category"/>');
			return false;
		}
		document.reportForm.sourceCategoryId.value=selectedSourceCategory;
	}
	return true;
}


var sourceLeftGrid;
var sourceRightGrid;

//GRID For Source
function initSourceGrids() {	
	sourceLeftGrid = new dhtmlXGridObject('SOURCE_LEFT'); 
	sourceLeftGrid.imgURL = "images/"; 
	sourceLeftGrid.setHeader("source"); 
	sourceLeftGrid.setInitWidths("295");
	sourceLeftGrid.setNoHeader(true);
	sourceLeftGrid.setColAlign("left");
	sourceLeftGrid.setColTypes("ro");
	sourceLeftGrid.setColSorting("source_name_sort");
	//sourceLeftGrid.enableMultiselect('true');	
	sourceLeftGrid.init();
	loadGridSource();
	sourceLeftGrid.enableSmartRendering(true);	
	sourceLeftGrid.setAwaitedRowHeight(21);
	sourceLeftGrid.sortRows(0,'str',"asc");
	sourceLeftGrid.setSortImgState(true,0,"ASC");
	sourceLeftGrid.attachEvent("onKeyPress",onSourceLeftGridKeyPressed);
	sourceLeftGrid.attachEvent("onRowSelect",doOnSourceLeftGridRowSelectHandler);
	sourceLeftGrid.attachEvent("onRowDblClicked",doOnSourceLeftGridRowDblClicked);
	
	sourceLeftGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	sourceRightGrid = new dhtmlXGridObject('SOURCE_RIGHT'); 
	sourceRightGrid.imgURL = "images/"; 
	sourceRightGrid.setHeader("source"); 
	sourceRightGrid.setInitWidths("295");
	sourceRightGrid.setColAlign("left");
	sourceRightGrid.setColTypes("ro"); 	
	//sourceRightGrid.enableMultiselect('true');
	sourceRightGrid.setNoHeader(true);
	sourceRightGrid.setColSorting("source_name_sort");
	sourceRightGrid.init();
	sourceRightGrid.sortRows(0,'str',"asc");
	sourceRightGrid.setSortImgState(true,0,"ASC");	
	sourceRightGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	sourceRightGrid.attachEvent("onKeyPress",onSourceRightGridKeyPressed);
	sourceRightGrid.attachEvent("onRowSelect",doOnSourceRightGridRowSelectHandler);
	sourceRightGrid.attachEvent("onRowDblClicked",doOnSourceRightGridRowDblClicked);
}

function source_name_sort(a,b,order,aId,bId) {
	a0 =sourceLeftGrid.getUserData(aId,"sourceName");
	b0 = sourceLeftGrid.getUserData(bId,"sourceName");	
	return sort_data(a0,b0,order);
}

function loadGridSource(){
	sourceLeftGrid.clearAll();	
	sourceLeftGrid.loadXML("reports.do?mode=XMLActiveSources");	
}

function onSourceLeftGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceLeftGrid,sourceRightGrid,4,keyCode,ctrl,shift);
}

function doOnSourceLeftGridRowSelectHandler() {
	sourceRightGrid.clearSelection();
}

function doOnSourceLeftGridRowDblClicked() {	
	var text = (sourceLeftGrid.cells(sourceLeftGrid.getSelectedId(),0)).getValue();
	selectItem(sourceLeftGrid,sourceRightGrid);
}

//for right Source grid
function onSourceRightGridKeyPressed(keyCode,ctrl,shift) {
	onGridObjKeyPressed(sourceRightGrid,sourceLeftGrid,4,keyCode,ctrl,shift);
}

function doOnSourceRightGridRowSelectHandler() {
	sourceLeftGrid.clearSelection();
}

function doOnSourceRightGridRowDblClicked() {	
	var text = (sourceRightGrid.cells(sourceRightGrid.getSelectedId(),0)).getValue();
	selectItem(sourceRightGrid,sourceLeftGrid);
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function onChangeSourceFilter(val){
	val = selectSourceFilter.getSelectedId();
	if(val=="<%=ReportConstants.FILTER_ALL%>"){
		$("divSelectSpecificSource").style.display="none";
	}else if(val=="<%=ReportConstants.FILTER_SPECIFIC_SOURCE%>"){
		$("divSelectSpecificSource").style.display="block";
		initSourceGrids();
		loadSourcesForCategories();
	}
}

function validateSourceFilter(){	
	//if specific Source option selected	
	var filterVal = selectSourceFilter.getSelectedId();
	document.reportForm.sourceFilter.value=filterVal;
	if(filterVal=="<%=ReportConstants.FILTER_SPECIFIC_SOURCE%>"){
		var selectedSource = sourceRightGrid.getAllItemIds(',');	 		
		if(!selectedSource){
			alert('<bean:message key="common.please_select"/> <bean:message key="common.source"/>');
			return false;
		}
		document.reportForm.sourceId.value=selectedSource;
	}
	return true;
}


function loadSourcesForCategories(){
	var selectedSourceCategories='';	
	sourceRightGrid.clearAll();		
	sourceLeftGrid.clearAll();
	if(selectSourceFilter.getSelectedId()=="<%=ReportConstants.FILTER_SPECIFIC_SOURCE%>"){
		var categoryFilter = selectSourceCategoryFilter.getSelectedId();		
		if(categoryFilter == "<%=ReportConstants.FILTER_SPECIFIC_SOURCE_CATEGORY%>"){
			selectedSourceCategories = 	sourceCategoryRightGrid.getAllItemIds();
			if(selectedSourceCategories==''){
				sourceLeftGrid.loadXML("reports.do?mode=XMLActiveSources");
			}else{
				sourceLeftGrid.loadXML("reports.do?mode=XMLActiveSources&sourceCategoryId="+selectedSourceCategories);
			}
		}else{
			sourceLeftGrid.loadXML("reports.do?mode=XMLActiveSources");
		}				
	}else{
		sourceLeftGrid.loadXML("reports.do?mode=XMLActiveSources");
	}
}

</script>