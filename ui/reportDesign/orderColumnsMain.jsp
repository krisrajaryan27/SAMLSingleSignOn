<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>

<style>
.orderColumns {border:1px solid #99CC33; border-top:none;}

</style>

<script language="JavaScript">
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'GRD_COLUMNS'){
		switch(obj.cell._cellIndex){			
			case 0:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"columnName");				
				break;
		}	
	}
	return obj.cell.innerHTML;
}
</script>

<html:form action="/reportDesigner" onsubmit="submitForm();return false;">
<html:hidden property="mode" name="reportDesignForm"/>
<html:hidden property="reportId" name="reportDesignForm"/>
<html:hidden property="reportType" name="reportDesignForm"/>
<html:hidden property="reportName" name="reportDesignForm"/>
<html:hidden property="reportCategory" name="reportDesignForm"/>
<html:hidden property="columns" name="reportDesignForm"/>
<html:hidden property="sortBy" name="reportDesignForm"/>
<html:hidden property="sortWith" name="reportDesignForm"/>
<html:hidden property="filters" name="reportDesignForm"/>
<html:hidden property="reportDescription" name="reportDesignForm"/>
<html:hidden property="isSharedReport" name="reportDesignForm"/>
<html:hidden property="levelPermissions" name="reportDesignForm"/>
<html:hidden property="reportFormat" name="reportDesignForm"/>

<div class="contentDiv">
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td><div style="width:120px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
	    <img src="images/blank_small.gif" align="absmiddle" />Order Columns</div></td> 
	  </tr> 
   </table> 
	<table class="reportHeader" cellspacing="0" cellpadding="0" border="0"  width="100%">
		<tr>				  			
			<td class="head">
				<b>Order report columns in the way you wish to view them</b>
			</td>
		</tr>
	</table>
	<table class="orderColumns" cellspacing="10" cellpadding="10" border="0"  width="100%">
		<tr>
			<td colspan="2" class="nopadding">	
			<table cellspacing="0" cellpadding="0">
					<tr>
						<td style="vertical-align: top;">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td class="gridborder">
										<div id="GRD_COLUMNS" style="width:270px;height:300px;"></div>
									</td>
								</tr>
							</table>
						</td>		
						<td style="padding: 10px;" align="center">					
							UP
							<br/>
							<a href="#" onclick="javascript: moveRowUp(dataGrid);return false;" title="Add" >
								<img src="images/ico_uparrow.gif"  border="0" />
							</a>
							<br/>
							<br/>
							<a href="#" onclick="javascript: moveRowDown(dataGrid);return false;" title="Remove" >
								<img src="images/ico_downarrow.gif"  border="0" />
							</a> 
							<br/>
							Down
						</td>					
					</tr>	
				</table>	
			</td>
		</tr>
	</table>
	
	<br/>
	<table class="tblPop" width="100%">
		<tr>
			<td>
				<div class="navBtn" style="float: right;">
					<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
					<a href="#" style="width:50px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>
	<br/>
</div>
</html:form>



<script language="javascript">

var dataGrid;
var dataGridTo;

function initGrid() {	
	dataGrid = new dhtmlXGridObject('GRD_COLUMNS'); 
	dataGrid.imgURL = "images/"; 
	dataGrid.setHeader("Selected Columns"); 
	dataGrid.setInitWidths("250");
	dataGrid.setColAlign("left");
	dataGrid.setColTypes("ro"); 
	dataGrid.setColSorting("Column_Name_Sort");	
	dataGrid.init();
	loadGrid();	
	
	dataGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
}

function Column_Name_Sort(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"columnName");
	b0 = dataGrid.getUserData(bId,"columnName");	
	return sort_data(a0,b0,order);
}

function loadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML('reportDesigner.do?mode=getSelectedColumns&columns=<bean:write property="columns" name="reportDesignForm"/>');	
}


//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}



function submitForm(){
	var val = dataGridTo.getAllItemIds();
	document.reportDesignForm.columns.value = val; 
	document.reportDesignForm.submit();
}

function onWindowLoad(){
	initGrid();
}

function getSelectedIds(){
	return dataGrid.getAllRowIds(",");
}

function nextPage(){
	document.reportDesignForm.mode.value="selectSortByColumn";
	document.reportDesignForm.columns.value=getSelectedIds();
	document.reportDesignForm.submit();
}

function previousPage(){
	document.reportDesignForm.mode.value="selectColumns";
	document.reportDesignForm.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

window.onload=onWindowLoad;
</script>