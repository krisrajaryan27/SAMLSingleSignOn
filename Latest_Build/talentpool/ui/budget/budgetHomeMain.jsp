<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>

<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants,
									com.talentPool.common.properties.TPApplicationProperties,
                                    com.talentPool.budget.BudgetConstants"%>
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">	
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script>
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == 'BUDGET_GRID'){
		switch(obj.cell._cellIndex){
			case 0:
				var pstat =obj.grid.getUserData(obj.cell.parentNode.idd,"budgetItemStatus");
				var stTxt="";
				if(pstat=='<%=BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE%>'){
					stTxt="<bean:message key="budget.home.label.tooltip_active_budget_item" />";
				}else if(pstat==<%=BudgetConstants.BUDGET_ITEM_STATUS_DRAFT%>){
					stTxt="<bean:message key="budget.home.label.tooltip_draft_budget_item" />";
				}
				return stTxt; 
				break;
			case 1:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"budgetItemName");				
				break;
		}	
	}
	return obj.cell.innerHTML;
}
</script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script src="js/cookies.js"></script>
<script language="JavaScript" src="js/criteriapane.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>

<div style="margin:23px; margin-bottom:0px;"> 	
	<html:form action="/budgets">
	<html:hidden property="t" name="budgetForm"/>
	<html:hidden property="mode"/>
	<html:hidden property="budgetItemName" name="budgetForm"/>
	<html:hidden property="deptId" name="budgetForm"/>
	<html:hidden property="subDeptId" name="budgetForm"/>
	<html:hidden property="subSubDeptId" name="budgetForm"/>
	<html:hidden property="sub3DeptId" name="budgetForm"/>
	<html:hidden property="sub4DeptId" name="budgetForm"/>
	<html:hidden property="ownerId" name="budgetForm"/>
	<html:hidden property="positionId" name="budgetForm"/>
	<html:hidden property="gradeId" name="budgetForm"/>
	<html:hidden property="bandId" name="budgetForm"/>
	<html:hidden property="status" name="budgetForm"/>	
	<!-- Header Menu -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>						
				<div class="navBtnTab" style="width:265px;float: left;">
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	 
					<!-- 
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUDGET_VIEW">						 							  
					  	<a href="#" onclick="javascript: viewBudgetItem();" style="width:100px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.view_details"/></a> 
				 		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
				 	</logic:equal>
				 	
				 	 -->
				 	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUDGET_EDIT">				 	
					 	<a href="#" onclick="editBudgetItem();return false;" style="width:60px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a> 				
					 	<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
				 	</logic:equal>
				  	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUDGET_DELETE">
						<a href="#" onclick="javascript: deleteBudgetItem();" style="width:60px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.delete"/></a> 
						<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
					</logic:equal>		
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUDGET_EDIT">				 	
					 	<a href="#" style="width:120px;" onclick="javascript: transferBudget();"><span class="rightC"></span><span class="leftC"></span><bean:message key="budget.home.transfer_budget"/></a>				
				 	</logic:equal>							
 				</div>				
			</td>
			<td>		
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUDGET_CREATE">
					<div class="navBtnTab"  style="width:100px;float: right;"	>					
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  		
						<a href="#" onclick="addBudgetItem();return false;" style="width:80px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add_new"/></a> 				
					</div>
				</logic:equal>  
			</td>
		</tr>
	</table>	
	
	<!--Grig Box is here -->
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id=BUDGET_GRID style="width:736px;height:228px;"></div>
			</td>
		</tr>
	</table>
</html:form>
</div>

<script>
var datagrid='BUDGET_GRID';
function initGrid(){	
	   	dataGrid = new dhtmlXGridObject('BUDGET_GRID'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader(",<bean:message key="budget.home.budget_item_name" />,<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>,<bean:message key="budget.home.owner" />,<bean:message key="budget.home.availabe_head_count" />,<bean:message key="budget.home.committed_head_count" />,<bean:message key="budget.home.used_head_count" />,<bean:message key="budget.home.from_date" />,<bean:message key="budget.home.to_date" />"); 
	   	dataGrid.setInitWidths("20,138,138,108,58,68,48,75,75");
	   	dataGrid.setColAlign("left,left,left,left,left,left,left,left,left");
	   	dataGrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro"); 
	   	//dataGrid.setColSorting("na,custom_pos_priority_sort,custom_pos_name_sort,custom_dept_sort,sort_vacancies,sort_inprocess,sort_offered,sort_joined,custom_date_sort");	
	   	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUDGET_VIEW">
		dataGrid.attachEvent("onKeyPress",onPositionKeyPressed);
		dataGrid.attachEvent("onRowDblClicked",onPositionRowDoubleClicked);
		</logic:equal>
		dataGrid.attachEvent("onXLE",dataGridOnLoadingEnd);
		dataGrid.attachEvent("onRowSelect",onPositionRowSelected);
		dataGrid.attachEvent("onAfterSorting",onAfterSorting);
		dataGrid.enableAutoHeigth(true,"360");  	
	   	dataGrid.init(); 	   		   	
	   	loadGrid();
	   	dataGrid.setSortImgState(true,1,"ASC");
}	

function onPositionKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(datagrid,dataGrid,keyCode,ctrl,shift);
}

function onAfterSorting(index,type,direction) {	
	eraseCookie("GRD_POS" +userId);	
	createCookie("GRD_POS"+userId,index+"_"+direction,360);
}
function onPositionRowDoubleClicked(id,idx_col){
	onRowDoubleClicked(datagrid,dataGrid,id,idx_col);
}

function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==datagrid){
			editBudgetItem();	
		}
		break;
	case 33:
		//page up
		var idx = grdObj.getRowIndex(id)-pageSize;
		idx = (idx<0)?0:idx;
		grdObj.selectRow(idx);
		break;
	case 34:
		var idx = grdObj.getRowIndex(id)+pageSize;
		idx = (idx>=grdObj.getRowsNum())?grdObj.getRowsNum()-1:idx;
		grdObj.selectRow(idx);	
		//page down
	}
	return true;
}

function onRowDoubleClicked(grdId, grdObj,id,idx_col){
	if(grdId==datagrid){
		editBudgetItem();	
	}
}

function viewDetails(){
	   var id = dataGrid.getSelectedId();   
	   if(id){
		   var ids=id.split(",");
		   if(ids.length<2){
			   window.location.href=uncache("position.do?mode=description&positionId="+id + "&showCondition="+checkboxListPositions.getSelectedIds());
			}else {
				alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
			}	    
	   }else{
	     	alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> <bean:message key="positions_home.error.select_position_to_view_details" />');
	     	return false;
	     }
	     return true;
	}
function onRowSelected(grdId, grdObj, id, idx_col){
	if(grdId!=datagrid){
		dataGrid.clearSelection();		
	}
}

function loadGrid(){
	var type = dataGrid.getSortingState();
	dataGrid.clearAll();
	if(type){
		dataGrid.setSortImgState(true,type[0],type[1]);
	}
	dataGrid.loadXML("budgets.do?mode=searchBudgetItems"+ getCriteriaQryString());
}

function onPositionRowSelected(id,idx_col){
	onRowSelected(datagrid,dataGrid,id,idx_col);
}
function getCriteriaQryString(){
	var qryString = "&deptId="+document.budgetForm.deptId.value;
	qryString += "&subDeptId="+document.budgetForm.subDeptId.value;
	qryString += "&subSubDeptId="+document.budgetForm.subSubDeptId.value;
	qryString += "&sub3DeptId="+document.budgetForm.sub3DeptId.value;
	qryString += "&sub4DeptId="+document.budgetForm.sub4DeptId.value;
	qryString += "&positionId="+document.budgetForm.positionId.value;
	qryString += "&ownerId="+document.budgetForm.ownerId.value;
	qryString += "&budgetItemName="+$('budgetItemName').value;
	qryString += "&gradeId="+document.budgetForm.gradeId.value;
	qryString += "&bandId="+document.budgetForm.bandId.value;
	qryString += "&status="+document.budgetForm.status.value;
	return qryString;
}

var userId = '';
function dataGridOnLoadingEnd() {
	var type = dataGrid.getSortingState();
	var val = readCookie("GRD_BGT"+userId);
	if(val != null) {
		parts = val.split("_");
		sortGridRows(parts);
	} else {
		sortGridRows(type);
	}
		
}

function sortGridRows(type) {
	if (type[1].toUpperCase() == 'ASC') {
		order='asc';		
	} else {		
		order='desc';			
	}	
	switch(type[0]){
		case 4:
			dataGrid.sortRows(type[0], "int", order);
			break;
		/*default: 
			dataGrid.sortRows(type[0], "cus", order);//cus for custom
			break;*/
	}		
	dataGrid.setSortImgState(true,type[0],type[1]);		
}

function addBudgetItem(){
	window.location.href="budgets.do?mode=manageBudgetItem&subMode="+'<%=BudgetConstants.SUB_MODE_ADD %>';
}

function canModifyBudgetItem(id, onPermissionResponse){
	 var pars = "mode=canModifyBudgetItem&budgetItemId=" + id;
	 var myAjax = ajaxCall("budgets.do",'get',pars,onPermissionResponse, reportError);
}

function editBudgetItem(){
	var id = dataGrid.getSelectedId();
	if(!id){
	   	alert("Please select the Budget Item to Edit.");
	   	return;
	}
	canModifyBudgetItem(id,onPermissionForEditBudget);
}

function onPermissionForEditBudget(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
  	if(isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
  		if(errors == null || errors.length==0){
  			alert("You don't have permission to edit this budget item.");
  		}
  		else{
  			alert("Unknown error occured while checking permission to edit this budget item.");
  		}
  		return;
  	}
	window.location.href="budgets.do?mode=manageBudgetItem&subMode="+'<%=BudgetConstants.SUB_MODE_EDIT %>'+"&budgetItemId="+dataGrid.getSelectedId();
}

function deleteBudgetItem(){
	var id = dataGrid.getSelectedId();
	if(!id){
	   	alert("Please select the Budget Item to Delete.");
	   	return;
	}
	  if(!confirm("You are about to delete Budget Item '"+dataGrid.getUserData(id,"budgetItemName")+"'. Continue?")){
	    	return;
	    }
	  canModifyBudgetItem(id,onPermissionForDeleteBudget);	   
}

function onPermissionForDeleteBudget(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
  	if(isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
  		if(errors == null || errors.length==0){
  			alert("You don't have permission to delete this budget item.");
  		}
  		else{
  			alert("Unknown error occured while checking permission to delete this budget item.");
  		}
  		return;
  	}
  	 var pars = "mode=deleteBudget&budgetItemId=" +dataGrid.getSelectedId();
	 var myAjax = ajaxCall("budgets.do",'get',pars,onDeleteResponse, reportError);
}

function onDeleteResponse(request){
  	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
  	if(isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
  		var error = errors[0];
  		if(error.indexOf('availableHeadCount')!= -1){
  			 if(!confirm("You have "+error.substring(error.indexOf("_")+1,error.length)+" available heads for this budget item. Still Continue to delete?")){
  		    	return;
  		    }else{
  		    	var id = dataGrid.getSelectedId();
  		      	var pars = "mode=deleteBudget&budgetItemId=" + id+"&confirmDelete=true";
  		    	var myAjax = ajaxCall("budgets.do",'get',pars,onDeleteResponse, reportError);
  		    	return;
  		    }
  		}else{
			alert(errors[0]);
			return;
  		}
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		dataGrid.deleteRow(deletedIds[I]);
	}
	dataGrid.clearSelection();  
}

function transferBudget(){
	var id = dataGrid.getSelectedId();
	if(!id){
	   	alert("Please select the Budget Item to transfer heads.");
	   	return;
	}
	canModifyBudgetItem(id,onPermissionForTransferBudget);	   
	
}

function onPermissionForTransferBudget(request){
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
  	if(isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
  		if(errors == null || errors.length==0){
  			alert("You don't have permission to transfer heads from this budget item.");
  		}
  		else{
  			alert("Unknown error occured while checking permission to transfer from this budget item.");
  		}
  		return;
  	}
  	var id = dataGrid.getSelectedId();
  	var budgetItemName = dataGrid.getUserData(id,"budgetItemName");
	var availableHeadCount = dataGrid.getUserData(id,"availableHeadCount");
	var url="budgets.do?mode=transferBudget&budgetItemId="+id+"&budgetItemName="+budgetItemName+"&availableHeadCount="+availableHeadCount;
	showInPopUp(url,500,270 ,loadGrid,true);
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function doOnLoad(){
	initPopUp();		
	initGrid();
	Event.observe($('budgetItemName'), "keyup", onTextTypeFilterChange.bindAsEventListener(this,'<%=BudgetConstants.FILTER_BUDGET_ITEM %>'));
	criteriaPane= new criteriaPane('criteriaDiv');
	applyPreFilters(); //function defined in left panel	
}

window.onload=doOnLoad;
</script>