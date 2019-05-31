<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/scripta/src/effects.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<div class="contentDivPop" >
	<div class="outerDiv" style="width:580px;">
	<div class="popupTop">
		<table class="tblPop">
			<tr>
				<td class="header" style="vertical-align: top;">
					<bean:message key="common.select_position"/>
				</td>
			</tr>
			<tr>
				<td align="left" >
					<table cellspacing="0" cellpadding="0" border="0" class="filterGrid" >
						<tr>
							<td style="vertical-align: top;" align="left">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input id="positionFilter" name="positionFilter" type="text" size="108" onfocus="onFilterFocus('positionFilter')" value="Filter" style="width:551px;color: graytext; border-bottom: 0px;" onclick="onFilterFocus('positionFilter','Filter');" onblur="onFilterUnfocus('positionFilter','Filter')"/>
										</td>
									</tr>
									<tr>
										<td class="gridborder" style="padding: 0px;">
											<div id="POSITION_GRD" class="gridbox" style="width:554px;height:160px;"></div>
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
</div>
<br/>
	<table cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:65px;" class="active" onclick="javascript:shortlist();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:65px;margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
	</table>
<br>
</div>
<script type="text/javascript">
function shortlist(){
	if(positionGrid.getSelectedId()==null){
		alert('Please select position');
		return false;
	}
	window.top.hidePopWin(true);
}

/******************* Locationd Grid Related Code ***************/
var positionGridId = 'POSITION_GRD';
var positionGrid = null;

function initPositionGrid(){
	positionGrid = new dhtmlXGridObject(positionGridId); 
	positionGrid.imgURL = "images/"; 
	positionGrid.setHeader('<bean:message key="position.requirements.primary_skills" />'); 
	positionGrid.setInitWidths("535");
	positionGrid.setColAlign("left");
	positionGrid.setColTypes("ro");
	positionGrid.setNoHeader(true);
	positionGrid.setColSorting("position_name_sort");
	positionGrid.enableMultiselect('false');	
	positionGrid.init();	
	positionGrid.sortRows(0,'str',"asc");
	positionGrid.attachEvent("onXLE",doOnLoadingEndLocation);
	positionGrid.attachEvent("onRowSelect",doOnPositionGridRowSelectHandler);
	positionGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	loadPositionGrid();	
}
function loadPositionGrid(){
	positionGrid.clearAll();
	positionGrid.loadXML('position.do?mode=getPositionForShortlist&subMode=<bean:write name="positionForm" property="subMode"/>');	
}
function doOnLoadingEndLocation() {
	positionGrid.sortRows(0,'str',"asc");
	positionGrid.setSortImgState(true,0,"ASC");
}

function doOnPositionGridRowSelectHandler() {
	var positionId = positionGrid.getSelectedId();
	returnVal = positionId;
}

function position_name_sort(a,b,order,aId,bId) {
	a0 = dataGridRequisitioner.getUserData(aId,"positionName");
	b0 = dataGridRequisitioner.getUserData(bId,"positionName");	
	return sort_data(a0,b0,order);
}
//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}
/**
 * TO Filter Position Grid
 */
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
			positionGrid.filterBy(0, $('positionFilter').value, false);		
		}
	}
}

function setPopupTitle(){
	var title = '<b><bean:message key="common.select_position"/></b>';
	window.top.setPopTitle(title);
	initPositionGrid();
	Event.observe($('positionFilter'), "keyup", onCriteriaChange.bindAsEventListener(this));
	
}
window.onload = setPopupTitle;

</script>
