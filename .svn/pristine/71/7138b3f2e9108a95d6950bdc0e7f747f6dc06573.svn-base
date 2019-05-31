<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/tpSelectListFunctions.js"></script>
<script src="js/cookies.js"></script> 
<html:form action="/export" >
<html:hidden property="mode" name="exportForm"/>
<html:hidden property="entityType" name="exportForm"/>
<html:hidden property="selectedFields" name="exportForm"/>
<html:hidden property="ids" name="exportForm"/>
<html:hidden property="positionId" name="exportForm"/>
<html:hidden property="applicantName" name="exportForm"/>
<html:hidden property="stepName" name="exportForm"/>
<html:hidden property="rejectedBy" name="exportForm"/>

<div class="contentDiv">
<table cellspacing="0" cellpadding="0">
	<tr>
		<td class="Grey"><b><bean:message key="exportToExcel.label.select_fields_to_export" /></b><br/><br/></td>
	</tr>
	<tr>
		<td style="vertical-align: top;">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="gridborder">
				<div id="GRD_FIELDS" style="width:220px;height:200px;"></div>
				</td>
			</tr>
		</table>			
		</td>		
		<td style="padding: 10px;">					
			<a href="#" onclick="javascript: selectItem(dataGridFields,dataGridSelectedFields);return false;" title="<bean:message key='common.add' />" >
				<img src="images/ico_rightarrow.gif"  border="0" />
			</a>
			<br/>
			<a href="#" onclick="javascript: deselectItem(dataGridSelectedFields,dataGridFields);return false;" title="<bean:message key='common.remove' />" >
				<img src="images/ico_leftarrow.gif"  border="0" />
			</a> 
		</td>					
		<td style="vertical-align: top;">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="gridborder">
				<div id="GRD_SELECTED_FIELDS" style="width:220px;height:200px;"></div>
				</td>
			</tr>
		</table>
	</tr>	
	<tr>
		<td colspan="3"><br/>
			<div class="navBtn" style="float:right;margin-left:5px;margin-top:5px;"><a href="#" style="width:70px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a></div>
		</td>
	</tr>
</table>	
</div>
</html:form>
<script language="JavaScript">
function submitForm() {
	var d = new Date();
	document.exportForm.target=d;
	document.exportForm.mode.value="export";
	document.exportForm.selectedFields.value=dataGridSelectedFields.getAllItemIds();	
	createCookie('<bean:write name="exportForm" property="entityType"/>', dataGridSelectedFields.getAllItemIds(','), 100);	
	document.exportForm.submit();
	return true;
}
var dataGridFields, dataGridSelectedFields;
function initGrid() {	
	dataGridFields = new dhtmlXGridObject('GRD_FIELDS'); 
	dataGridFields.imgURL = "images/"; 
	dataGridFields.setHeader("<bean:message key='exportToExcel.label.fields' />"); 
	dataGridFields.setInitWidths("200");
	dataGridFields.setColAlign("left");
	dataGridFields.setColTypes("ro"); 
	dataGridFields.setColSorting("str");	
	dataGridFields.enableMultiselect(true);	
	dataGridFields.init();
	loadGridFields();	
	dataGridFields.attachEvent("onXLE",doOnLoadingEndFields);
	dataGridFields.attachEvent("onKeyPress",onGridFieldsKeyPressed);
	dataGridFields.attachEvent("onRowSelect",doOnDataGridFieldsRowSelectHandler);
	dataGridFields.attachEvent("onRowDblClicked",doOnDataGridFieldsRowDblClicked);
	
	dataGridFields.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridSelectedFields = new dhtmlXGridObject('GRD_SELECTED_FIELDS'); 
	dataGridSelectedFields.imgURL = "images/"; 
	dataGridSelectedFields.setHeader("<bean:message key='exportToExcel.label.selected_fields' />"); 
	dataGridSelectedFields.setInitWidths("200");
	dataGridSelectedFields.setColAlign("left");
	dataGridSelectedFields.setColTypes("ro"); 
	dataGridSelectedFields.setColSorting("str");	
	dataGridSelectedFields.enableMultiselect(true);
	dataGridSelectedFields.init();
	//dataGridSelectedFields.sortRows(0,'str',"asc");
	//dataGridSelectedFields.setSortImgState(true,0,"ASC");	
	dataGridSelectedFields.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	dataGridSelectedFields.attachEvent("onKeyPress",onGridSelectedFieldsKeyPressed);
	dataGridSelectedFields.attachEvent("onRowSelect",doOnDataGridSelectedFieldsRowSelectHandler);
	dataGridSelectedFields.attachEvent("onRowDblClicked",doOnDataGridSelectedFieldsRowDblClicked);
	//selectItems("2,4",dataGridFields,dataGridSelectedFields);
}

function doOnDataGridFieldsRowDblClicked() {	
	selectItem(dataGridFields,dataGridSelectedFields);
}
function doOnDataGridFieldsRowSelectHandler() {
	dataGridSelectedFields.clearSelection();
}
function doOnDataGridSelectedFieldsRowSelectHandler() {
	dataGridFields.clearSelection();
}
function doOnDataGridSelectedFieldsRowDblClicked() {	
	selectItem(dataGridSelectedFields,dataGridFields);
}
function loadGridFields(){
	dataGridFields.clearAll();
	dataGridFields.loadXML("export.do?mode=getExportFields&entityType=" + document.exportForm.entityType.value);
}

function onGridFieldsKeyPressed(keyCode,ctrl,shift) {	
	dataGridSelectedFields.clearSelection();
	onGridObjKeyPressed(dataGridFields,dataGridSelectedFields,4,keyCode,ctrl,shift);
}

function onGridSelectedFieldsKeyPressed(keyCode,ctrl,shift) {
	dataGridFields.clearSelection();
	onGridObjKeyPressed(dataGridSelectedFields,dataGridFields,4,keyCode,ctrl,shift);
}

function doOnLoadingEndFields() {
	// sortRows doesn't work in IE11, skip it
	if (!(navigator.appName  == 'Netscape' && navigator.userAgent.indexOf("Trident") != -1)) {
		dataGridFields.sortRows(0,'str',"asc");
	}
	dataGridFields.setSortImgState(true,0,"ASC");
	setSelectedFromCookie();
}
function setSelectedFromCookie(){
	var vals = readCookie('<bean:write name="exportForm" property="entityType"/>');
	if(vals!='' && vals!=null){
		var arrVals = vals.split(',');
		for(var x=0;x<arrVals.length;x++){
			dataGridFields.setSelectedRow(arrVals[x],true,false,false);
		}
		selectItem(dataGridFields,dataGridSelectedFields);
	}
}
function doOnLoad() {
	setPopupTitle();
	initGrid();
}
function setPopupTitle() {
	var popupTitle = '<b><bean:message key="common.select" />&nbsp;<bean:message key="common.fields" /></b>';
	window.top.setPopTitle(popupTitle);
}
window.onload=doOnLoad;
</script>