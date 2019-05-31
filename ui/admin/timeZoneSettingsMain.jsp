<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>	
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="javascript" type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>						
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/IdValueClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>		

<style type="text/css">
.selectListSkills {
color:#000000;
background-color:#f2f2f2;
font-size:12px;
width:130px;
border-color:#000000;
}
</style>

<html:form action="/adminHome">
			<html:hidden property="mode" name="adminForm"/>
			<html:hidden property="timeZoneId" name="adminForm"/>
				<div id="timeZoneDiv" style="margin-top: 5%;">						
							<table width="100%" border="0" cellspacing="0" cellpadding="0">									
									<tr>
										<!-- <td class="label2"><bean:message key="position.description.location" />
										&nbsp;
										</td> -->
										<td>
										&nbsp;
										</td>
										<td align="center" >
											<table cellspacing="0" cellpadding="0" border="0" class="filterGrid" >
												<tr>
													<td style="vertical-align: top;" align="left">
														<table cellpadding="0" cellspacing="0">
															<!-- <tr>
																<td>
																	<input id="locationFilter" name="locationFilter" type="text" size="49" onfocus="onFilterFocus('locationFilter')" value="Filter" style="width:256px;color: graytext; border-bottom: 0px;" onclick="onFilterFocus('locationFilter','Filter');" onblur="onFilterUnfocus('locationFilter','Filter')"/>
																</td>
															</tr> -->
															<tr>
																<td class="gridborder" style="padding: 0px;">
																	<div id="LOCATIONS_GRD" class="gridbox" style="width:259px;height:200px;"></div>
																</td>
															</tr>
														</table>			
													</td>		
													<td style="padding: 10px;vertical-align: middle;" >					
														<a href="#" onclick="javascript: selectLocationItem(locationsGrid,locationsGridSelected);return false;" title="<bean:message key='common.add' />" >
															<img src="images/ico_rightarrow.gif"  border="0" />
														</a>
														<br/>
														<a href="#" onclick="javascript: deselectLocationItem(locationsGridSelected,locationsGrid);return false;" title="<bean:message key='common.remove' />" >
															<img src="images/ico_leftarrow.gif"  border="0" />
														</a> 
													</td>					
													<td style="margin-top: 5%;">
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td class="gridborder">
																	<div id="LOCATIONS_GRD_SELECTED"  class="gridbox" style="width:240px;height:118px;"></div>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>			
										</td>
									</tr>	
								</table>
								
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
					<br/>
					<div class="navBtn" style="float:right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:addDescription();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
					</div>
				</td>
			</tr>
		</table>
		</div>
		</html:form>
		
<script type="text/javascript">
var locationsGridId = 'LOCATIONS_GRD';
var locationsGridSelectedId = 'LOCATIONS_GRD_SELECTED';
var locationsGrid = null;
var locationsGridSelected = null;

function initLocationsGrid(){
	locationsGrid = new dhtmlXGridObject(locationsGridId); 
	locationsGrid.imgURL = "images/"; 
	locationsGrid.setHeader('<bean:message key="position.requirements.primary_skills" />'); 
	locationsGrid.setInitWidths("240");
	locationsGrid.setColAlign("left");
	locationsGrid.setColTypes("ro");
	locationsGrid.setNoHeader(true);
	locationsGrid.setColSorting("location_name_sort");
	locationsGrid.enableMultiselect('true');	
	locationsGrid.init();	
	locationsGrid.sortRows(0,'str',"asc");
	locationsGrid.attachEvent("onXLE",doOnLoadingEndLocation);
	locationsGrid.attachEvent("onKeyPress",onLocationsGridKeyPressed);
	locationsGrid.attachEvent("onRowSelect",doOnLocationsGridRowSelectHandler);
	locationsGrid.attachEvent("onRowDblClicked",doOnLocationsGridRowDblClicked);
	locationsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	loadLocationsGrid();	
}
function loadLocationsGrid(){
	locationsGrid.clearAll();
	locationsGrid.parse('<bean:write name="adminForm" property="timeZoneXML" scope="request" filter="false" />');	
}

function doOnLoadingEndLocation() {
	locationsGrid.sortRows(0,'str',"asc");
	locationsGrid.setSortImgState(true,0,"ASC");
}

function location_name_sort(a,b,order,aId,bId) {
	a0 = dataGridRequisitioner.getUserData(aId,"locationName");
	b0 = dataGridRequisitioner.getUserData(bId,"locationName");	
	return sort_data(a0,b0,order);
}

//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function initLocationsGridSelected(){
	locationsGridSelected = new dhtmlXGridObject(locationsGridSelectedId); 
	locationsGridSelected.imgURL = "images/"; 
	locationsGridSelected.setHeader(""); 
	locationsGridSelected.setInitWidths("220");
	locationsGridSelected.setColAlign("left");
	locationsGridSelected.setColTypes("ro"); 	
	locationsGridSelected.enableMultiselect('true');
	locationsGridSelected.setNoHeader(true);
	locationsGridSelected.setColSorting("location_name_sort");
	locationsGridSelected.init();
	locationsGridSelected.sortRows(0,'str',"asc");
	locationsGridSelected.setSortImgState(true,0,"ASC");	
	locationsGridSelected.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	locationsGridSelected.attachEvent("onKeyPress",onLocationsGridSelectedGridKeyPressed);
	locationsGridSelected.attachEvent("onRowSelect",doOnLocationsGridSelectedRowSelectHandler);
	locationsGridSelected.attachEvent("onRowDblClicked",doOnLocationsGridSelectedRowDblClicked);
	loadLocationsGridSelected();
}
function loadLocationsGridSelected(){
	var pLocations = '<bean:write name="adminForm" property="timeZoneId" />';
	selectItems(pLocations,locationsGrid,locationsGridSelected);
}
function onLocationsGridKeyPressed(keyCode,ctrl,shift) {
	var text = (locationsGrid.cells(locationsGrid.getSelectedId(),0)).getValue();
	locationsGridSelected.clearSelection();
	onGridObjKeyPressed(locationsGrid,locationsGridSelected,5,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(locationsGrid, text);
	}
}
function onLocationsGridSelectedGridKeyPressed(keyCode,ctrl,shift) {
	locationsGrid.clearSelection();
	onGridObjKeyPressed(locationsGridSelected,locationsGrid,5,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(locationsGrid);
	}
}
function doOnLocationsGridSelectedRowSelectHandler() {
	locationsGrid.clearSelection();
}
function doOnLocationsGridRowSelectHandler() {
	locationsGridSelected.clearSelection();
}
function doOnLocationsGridRowDblClicked() {
	var text = (locationsGrid.cells(locationsGrid.getSelectedId(),0)).getValue();
	selectLocationItem(locationsGrid,locationsGridSelected);
	removeIdFromBackUp(locationsGrid, text);
}
function doOnLocationsGridSelectedRowDblClicked() {	
	deselectLocationItem(locationsGridSelected,locationsGrid);
	selectItem(locationsGridSelected,locationsGrid);
	resetFilterBackUp(locationsGrid);
}

function selectLocationItem(srcGrid,destGrid){
	selectItem(srcGrid,destGrid);
}

function deselectLocationItem(srcGrid,destGrid){
	deselectItem(srcGrid,destGrid);
}

function addDescription(){
	var locationIds = locationsGridSelected.getAllItemIds();
	if(locationIds==''){
		alert('- <bean:message key="position.description.location" />');
	}
	document.adminForm.timeZoneId.value=locationsGridSelected.getAllItemIds();
	document.adminForm.mode.value='saveTimeZoneSettings';
	document.adminForm.submit();
}

function doOnLoad(){
	initLocationsGrid();
	initLocationsGridSelected();
}

window.onload=doOnLoad;
</script>