<%@page import="org.apache.struts.Globals"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.positions.PositionConstants"%>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">

<div class="contentDivPop" style="width:510px;">
<% 
if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
<table  id="m_errortable" > 
  <tr>
	<td class="header">
	  <b><bean:message key="errors.following_errors"/></b>
	</td>               
  </tr>
  <tr>
    <td class="message"><html:errors/></td>               
  </tr>
</table>
<br>
<% } %>

<div class="outerDiv">
<html:form action="/selectionProcessFilters">
<input type="hidden" id="params" />
	<div class="popupTop">
		<table class="tblPop">
			<tr>
			<td style="vertical-align: top;">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<input id="involvedUser" name="involvedUser" type="text" size="41" onfocus="onFilterFocus('involvedUser')" value="Filter" style="width:216px;color: grey; border-bottom: 0px;" onclick="onFilterFocus('involvedUser','Filter');" onblur="onFilterUnfocus('involvedUser','Filter')"/>
					</td>
				</tr>
				<tr>
					<td class="gridborder">
					<div id="GRD_INVOLVED_USER" style="width:219px;height:80px;"></div>
					</td>
				</tr>
			</table>			
			</td>		
			<td style="padding: 10px;">					
				<a href="#" onclick="javascript: selectItem(involvedUsersGrid,involvedUsersGridFiltered);return false;" title="<bean:message key='common.add' />" >
					<img src="images/ico_rightarrow.gif"  border="0" />
				</a>
				<br/>
				<a href="#" onclick="javascript: deselectItem(involvedUsersGridFiltered,involvedUsersGrid);return false;" title="<bean:message key='common.remove' />" >
					<img src="images/ico_leftarrow.gif"  border="0" />
				</a> 
			</td>					
			<td style="vertical-align: top;">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
					<div id="GRD_FILTERED_INVOLVED_USER" style="width:220px;height:97px;"></div>
					</td>
				</tr>
			</table>
		</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript: submitUsersToFilter();"><span class="rightC"></span><span class="leftC"></span>Filter</a>
						<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>
	</div>
</html:form>
</div>
</div>



<script>
var params = '<%=request.getParameter("params")==null?"":request.getParameter("params")%>';
var selectedUserId = '<%=request.getParameter("selectedUserId")==null?"":request.getParameter("selectedUserId")%>';
var involvedUsersGrid = null;
var involvedUsersGridFiltered = null;
var involvedUsersGridId = 'GRD_INVOLVED_USER';
var involvedUsersGridFilteredId = 'GRD_FILTERED_INVOLVED_USER';
function initInvolvedUsersGrid() {	
	involvedUsersGrid = new dhtmlXGridObject(involvedUsersGridId); 
	involvedUsersGrid.imgURL = "images/"; 
	involvedUsersGrid.setHeader("User Name"); 
	involvedUsersGrid.setInitWidths("200");
	involvedUsersGrid.setColAlign("left");
	involvedUsersGrid.setColTypes("ro"); 
	involvedUsersGrid.enableMultiselect('true');
	involvedUsersGrid.setNoHeader(true);
	involvedUsersGrid.init();
	loadInvolvedUsersGrid();	
	//involvedUsersGrid.attachEvent("onXLE",doOnLoadingEndAssignedTo);
	involvedUsersGrid.attachEvent("onKeyPress",onInvolvedUsersGridKeyPressed);
	involvedUsersGrid.attachEvent("onRowSelect",doOninvolvedUsersGridRowSelectHandler);
	involvedUsersGrid.attachEvent("onRowDblClicked",doOninvolvedUsersGridRowDblClicked);
	
	involvedUsersGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}
function loadInvolvedUsersGrid(){
	var url = "selectionProcessFilters.do?mode=getInvolvedUsersXmlForFilter"+unescape(params);
	involvedUsersGrid.loadXML(url);
}
function initInvolvedUsersGridFiltered(){
	involvedUsersGridFiltered = new dhtmlXGridObject(involvedUsersGridFilteredId); 
	involvedUsersGridFiltered.imgURL = "images/"; 
	involvedUsersGridFiltered.setHeader("User Name"); 
	involvedUsersGridFiltered.setInitWidths("200");
	involvedUsersGridFiltered.setColAlign("left");
	involvedUsersGridFiltered.setColTypes("ro"); 
	involvedUsersGridFiltered.setColSorting("assignedTo_userName_sort");	
	involvedUsersGridFiltered.enableMultiselect('true');
	involvedUsersGridFiltered.setNoHeader(true);
	involvedUsersGridFiltered.init();
	involvedUsersGridFiltered.sortRows(0,'str',"asc");
	involvedUsersGridFiltered.setSortImgState(true,0,"ASC");	
	involvedUsersGridFiltered.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	involvedUsersGridFiltered.attachEvent("onKeyPress",onInvolvedUsersGridFilteredKeyPressed);
	involvedUsersGridFiltered.attachEvent("onRowSelect",doOninvolvedUsersGridFilteredRowSelectHandler);
	involvedUsersGridFiltered.attachEvent("onRowDblClicked",doOninvolvedUsersGridFilteredRowDblClicked);
}

/*****************************Grid Event Handlers****************************************/
function doOninvolvedUsersGridRowDblClicked() {	
	var text = (involvedUsersGrid.cells(involvedUsersGrid.getSelectedId(),0)).getValue();
	selectItem(involvedUsersGrid,involvedUsersGridFiltered);
	removeIdFromBackUp(involvedUsersGrid, text);
}
function doOninvolvedUsersGridRowSelectHandler() {
	involvedUsersGridFiltered.clearSelection();
}
function doOninvolvedUsersGridFilteredRowDblClicked() {	
	selectItem(involvedUsersGridFiltered,involvedUsersGrid);
	resetFilterBackUp(involvedUsersGrid);
}
function doOninvolvedUsersGridFilteredRowSelectHandler() {
	involvedUsersGrid.clearSelection();
}
function onInvolvedUsersGridKeyPressed(keyCode,ctrl,shift){
	var text = (involvedUsersGrid.cells(involvedUsersGrid.getSelectedId(),0)).getValue();
	involvedUsersGridFiltered.clearSelection();
	onGridObjKeyPressed(involvedUsersGrid,involvedUsersGridFiltered,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		removeIdFromBackUp(involvedUsersGrid, text);
	}
}
function onInvolvedUsersGridFilteredKeyPressed(keyCode,ctrl,shift){
	involvedUsersGrid.clearSelection();
	onGridObjKeyPressed(involvedUsersGridFiltered,involvedUsersGrid,4,keyCode,ctrl,shift);
	if(keyCode=='13'){
		resetFilterBackUp(involvedUsersGrid);
	}
}
/**
 * TO Filter Involved Users Grid
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
			involvedUsersGrid.filterBy(0, $('involvedUser').value, false);		
		}
	}
}
/*****************************END OF Grid Event Handlers****************************************/


/*****************************ONLOAD FUCNTIONS****************************************/

/**
 * To Set the Selcted users to the second grid
 **/
function setSelectedUserIds(){
	selectItems(selectedUserId ,involvedUsersGrid,involvedUsersGridFiltered);	
}
function doOnLoad() {  	
	window.top.setPopTitle('<b><bean:message key="position_summary.title.user_filters"/></b>');
	initInvolvedUsersGrid();
	initInvolvedUsersGridFiltered();
	setTimeout("setSelectedUserIds()", 100);
	Event.observe($('involvedUser'), "keyup", onCriteriaChange.bindAsEventListener(this));
}
window.onload = doOnLoad;


/********************************Submit Functions**************************/

function submitUsersToFilter(){
	returnVal = involvedUsersGridFiltered.getAllItemIds();
	window.top.hidePopWin(true);
}
</script>	