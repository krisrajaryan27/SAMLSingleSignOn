<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
	window.top.fetchParsedInfo('<bean:write name="desktopSearchForm" property="rowId"/>');
</logic:present>                

function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'GRD_SKILLS'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"skillName");				
				break;
		}	
	}if(grdId == 'GRD_SKILLS_TO'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"skillName");				
				break;
		}	
	}
	
	return obj.cell.innerHTML;
}
</script>

<div class="contentDivPop" >
<html:form action="/desktop" >
	<html:hidden property="mode" name="desktopSearchForm" value="updateParsedSkills"/>
	<html:hidden property="parsedSkillIds" name="desktopSearchForm"/>
	<html:hidden property="resultId" name="desktopSearchForm"/>
	<html:hidden property="rowId" name="desktopSearchForm"/>

	<table cellspacing="0" cellpadding="2">
		<tr>
			<td class="label" style="padding-top: 10px;"><bean:message key="common.update"/> <bean:message key="common.skills"/>:</td>										
		</tr>
		<tr>
			<td colspan="2" class="nopadding">	
			<table cellspacing="0" cellpadding="0">
					<tr>
						<td style="vertical-align: top;">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td class="gridborder">
										<div id="GRD_SKILLS" style="width:170px;height:105px;"></div>
									</td>
								</tr>
							</table>
						</td>		
						<td style="padding: 10px;">					
							<a href="#" onclick="javascript: selectItem(dataGridSkills,dataGridSkillsTo);return false;" title="Add" >
								<img src="images/ico_rightarrow.gif"  border="0" />
							</a>
							<br/>
							<a href="#" onclick="javascript: deselectItem(dataGridSkillsTo,dataGridSkills);return false;" title="Remove" >
								<img src="images/ico_leftarrow.gif"  border="0" />
							</a> 
						</td>					
						<td style="vertical-align: top;">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td class="gridborder">
										<div id="GRD_SKILLS_TO" style="width:170px;height:105px;"></div>
									</td>
								</tr>
							</table>
						</td>
					</tr>	
				</table>	
			</td>
		</tr>
	</table>
</html:form>

	<div class="navBtn" style="float: right;margin-top: 10px;">
		<a href="#" style="width:65px;" class="active" onclick="javascript:submitForm();" id="submit"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.update"/></a>
		<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
	</div>
</div>


<script language="javascript">

var dataGridSkills;
var dataGridSkillsTo;

//GRID For Skills
function initGridSkills() {	
	dataGridSkills = new dhtmlXGridObject('GRD_SKILLS'); 
	dataGridSkills.imgURL = "images/"; 
	dataGridSkills.setHeader("<bean:message key="common.skills"/>"); 
	dataGridSkills.setInitWidths("150");
	dataGridSkills.setColAlign("left");
	dataGridSkills.setColTypes("ro"); 
	dataGridSkills.setColSorting("Skills_Name_Sort");	
	dataGridSkills.init();
	loadGridSkills();	
	dataGridSkills.attachEvent("onXLE",doOnLoadingEndSkills);
	dataGridSkills.attachEvent("onKeyPress",onGridSkillsKeyPressed);
	dataGridSkills.attachEvent("onRowSelect",doOnDataGridSkillsRowSelectHandler);
	dataGridSkills.attachEvent("onRowDblClicked",doOnDataGridSkillsRowDblClicked);
	
	dataGridSkills.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	
	dataGridSkillsTo = new dhtmlXGridObject('GRD_SKILLS_TO'); 
	dataGridSkillsTo.imgURL = "images/"; 
	dataGridSkillsTo.setHeader("<bean:message key="common.selected"/> <bean:message key="common.skills"/>"); 
	dataGridSkillsTo.setInitWidths("150");
	dataGridSkillsTo.setColAlign("left");
	dataGridSkillsTo.setColTypes("ro"); 
	dataGridSkillsTo.setColSorting("Skills_Name_Sort_To");	
	dataGridSkillsTo.init();
	dataGridSkillsTo.sortRows(0,'str',"asc");
	dataGridSkillsTo.setSortImgState(true,0,"ASC");	
	dataGridSkillsTo.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	dataGridSkillsTo.attachEvent("onKeyPress",onGridSkillsToKeyPressed);
	dataGridSkillsTo.attachEvent("onRowSelect",doOnDataGridSkillsToRowSelectHandler);
}
function doOnDataGridSkillsRowDblClicked() {
	selectItem(dataGridSkills,dataGridSkillsTo);
}
function doOnDataGridSkillsRowSelectHandler() {
	dataGridSkillsTo.clearSelection();
}
function doOnDataGridSkillsToRowSelectHandler() {
	dataGridSkills.clearSelection();
}
function Skills_Name_Sort(a,b,order,aId,bId) {
	a0 = dataGridSkills.getUserData(aId,"skillName");
	b0 = dataGridSkills.getUserData(bId,"skillName");	
	return sort_data(a0,b0,order);
}
function Skills_Name_Sort_To(a,b,order,aId,bId) {
	a0 = dataGridSkillsTo.getUserData(aId,"skillName");
	b0 = dataGridSkillsTo.getUserData(bId,"skillName");	
	return sort_data(a0,b0,order);
}
function loadGridSkills(){
	dataGridSkills.clearAll();
	dataGridSkills.loadXML("desktop.do?mode=XMLSkills");	
}

function onGridSkillsKeyPressed(keyCode,ctrl,shift) {
	dataGridSkillsTo.clearSelection();
	onGridObjKeyPressed(dataGridSkills,dataGridSkillsTo,4,keyCode,ctrl,shift);
}

function onGridSkillsToKeyPressed(keyCode,ctrl,shift) {
	dataGridSkills.clearSelection();
	onGridObjKeyPressed(dataGridSkillsTo,dataGridSkills,4,keyCode,ctrl,shift);
}

function doOnLoadingEndSkills() {
	dataGridSkills.sortRows(0,'str',"asc");
	dataGridSkills.setSortImgState(true,0,"ASC");
	//selectItems("",dataGridSkills,dataGridSkillsTo);
	var parsedSkillIds = document.desktopSearchForm.parsedSkillIds.value;
	selectItems(parsedSkillIds,dataGridSkills,dataGridSkillsTo);
}
//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}



function submitForm(){
	var val = dataGridSkillsTo.getAllItemIds();
	document.desktopSearchForm.parsedSkillIds.value = val; 
	document.desktopSearchForm.submit();
}

function onWindowLoad(){
	initGridSkills();
	window.top.setPopTitle('<b><bean:message key="common.update"/> <bean:message key="common.parsed"/> <bean:message key="common.skills"/></b>');
}

	
window.onload=onWindowLoad;
</script>