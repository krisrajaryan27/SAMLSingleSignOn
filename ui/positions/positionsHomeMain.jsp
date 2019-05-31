<%@page import="com.talentPool.common.properties.TPLabels"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.positions.PositionConstants,
                  com.talentPool.common.NavigationConstants,
                  com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="java.lang.Boolean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.custom.utils.CustomFieldUtils"%>
<%@page import="com.talentPool.custom.manager.CustomFieldManager"%>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%><link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">
<script language="JavaScript" src="js/criteriapane.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenu.js" type="text/javascript"></script>
<script language="JavaScript" src="js/tpmenu/tpmenuhandler.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="themes/default/searchTpMenu.css">
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script>
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == '<bean:message key="positions.home.id.datagrid" />'){
		switch(obj.cell._cellIndex){
			case 0:
				var pstat =obj.grid.getUserData(obj.cell.parentNode.idd,"positionStatus");
				var stTxt='<bean:message key="common.open" /> <bean:message key="common.position" />';
				if(pstat=='<%=PositionConstants.POSITION_STATUS_CLOSED%>'){
					stTxt='<bean:message key="common.closed" /> <bean:message key="common.position" />';
				}else if(pstat==<%=PositionConstants.POSITION_STATUS_INPROCESS %>){
					stTxt="<bean:message key="positions_home.label.tooltip_inprocess_requisition" />";
				}else if(pstat==<%=PositionConstants.POSITION_STATUS_REJECTED %>){
					stTxt="<bean:message key="positions_home.label.tooltip_rejected_requisition" />";
				}else if(pstat==<%=PositionConstants.POSITION_STATUS_HOLD %>){
					stTxt='<bean:message key="common.on_hold" /> <bean:message key="common.position" />';
				}
				return stTxt; 
				break;
			case 1:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"priorityLevel");				
				break;
			case 2:				
				return obj.grid.getUserData(obj.cell.parentNode.idd,"positionName");
				break;
			case 3:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"department");
				break;		
			case 5:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"inProcess");
				break;
			case 6:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"offered");
				break;
			case 7:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"joined");
				break;
			case 8:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"rejected");
				break;
		}	
	}
	return obj.cell.innerHTML;
}
/*
extension of dhtmlXGridCell.js cell for implementing attachment column and
change the css of rows if email from already existing candidate
*/
var _closedPosition = <%=PositionConstants.POSITION_STATUS_CLOSED%>;

function eXcell_estat(cell){
 this.cell = cell;
 this.grid = this.cell.parentNode.grid;
 this.getValue = function(){
 }
}
eXcell_estat.prototype = new eXcell;
eXcell_estat.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	if(val=="<%=PositionConstants.POSITION_STATUS_CLOSED%>" || val=="<%=PositionConstants.POSITION_STATUS_REJECTED%>"){
	 this.cell.parentNode.className='disabledrow';
	}else if(val=="<%=PositionConstants.POSITION_STATUS_HOLD%>" ){
	 this.cell.parentNode.className='onholdrow';
	}else if(val=="<%=PositionConstants.POSITION_STATUS_OVERDUE%>" ){
	 this.cell.parentNode.className='overdue';
	}
	
}
var datagrid='<bean:message key="positions.home.id.datagrid" />';
var pageSize=10;

var menu = new TpMenu();
<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CREATE_POSITION_TEMPLATE">
var mnu_temp = new TpMenu({type:'menu', id: 'template', image: 'images/ico_save_as_template.gif', title:'<bean:message key="position.home.save_as_template" />', onclick:'onClickMenu', width:'180px'});
menu.addItem(mnu_temp);
</logic:equal>
<logic:equal value="true" name="canAddRequisition" scope="request" >
var mnu_msg = new TpMenu({type:'menu', id: 'import', image: 'images/ico_csv_import_co.gif', title:'<bean:message key="position.home.import_position" />', onclick:'onClickMenu', width:'180px'});
menu.addItem(mnu_msg);
</logic:equal>

var mnu_bulk_approve = new TpMenu({type:'menu',image:'images/ico_requisition.gif', id: 'bulkApprove', title:'<bean:message key="position.home.approve_requisitions" />', onclick:'onClickMenu', width:'180px'});
menu.addItem(mnu_bulk_approve);

var mnu_sms = new TpMenu({type:'menu', id: 'sendformat', image: 'images/ico_email.gif', title:'<bean:message key="positions_home.label.send_position_request" />', onclick:'onClickMenu', width:'180px'});
menu.addItem(mnu_sms);

var mnu_print = new TpMenu({type:'menu', id: 'print',image:'images/ico_print.gif', title:'<bean:message key="positions_home.print.label" /> <bean:message key="common.position" />', onclick:'onClickMenu', width:'180px'});
menu.addItem(mnu_print);

var mnuhandlermore = new TpMenuHandler(menu,{});
mnuhandlermore.setOffsetOptions({setHeight: false, setWidth: false, offsetTop:30, offsetLeft:0});

function showMenuMore(applicantId, elementId){
	mnuhandlermore.show(applicantId,elementId);
}
function onClickMenu(mnu, opt){
	var id = mnu.getId();
	if(id == "import"){
		importPositions();
	}else if(id == "sendformat"){
		emailPositionRequest();
	}else if(id == "print"){
		printPosition();
	}else if(id == "template"){
		savePositionAsTemplate();
	}else if(id=="bulkApprove"){
		bulkApproveRequisitions();
	}
}

function emailPositionRequest(){
	var url="inbox.do?mode=positionRequestEmail";
	window.setTimeout("showInPopUp('"+url+"',810,513,'',true);", 10);
}

function printPosition(){
	var id = dataGrid.getSelectedId();
	if(id){
		var ids=id.split(",");
		if(ids.length<2){
			var url = "position.do?mode=printPosition&positionId="+id;
			var printWin = window.open(url,"_blank","height=500,left=100,top=100,width=800,toolbar=no,titlebar=0,status=0,menubar=no,location= no,scrollbars=1");
			//printWin.print();
		}else {
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
		}
    }else {
    	alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to Print.');
     	return;    
    }
}

/****************Custom Feild Filter related Changes**********************/
function onCustomFieldFilterSelected(retVal){
	var custonFieldData = retVal;
	var customFieldValue = ''; 
	document.positionForm.customFieldFilterId.value=custonFieldData.getFieldId();
	document.positionForm.customFieldFilterType.value=custonFieldData.getType();
	document.positionForm.customFieldFilterValue.value=custonFieldData.getFieldValue();
	customFieldValue = custonFieldData.getFieldValue().replace(/\|/g,',');
	criteriaPane.add(new criteriaOpt('<%=PositionConstants.FILTER_CUSTOM_FIELD%>', customFieldValue));
	criteriaPane.refreshCriteria();
	loadGrid();
}
</script>

<% 
String tokenId = (String)request.getSession().getAttribute("deletePositionTokenId");
%>

<div style="margin:23px; margin-bottom:0px;"> 	
<html:form action="/position">
<html:hidden property="t" name="positionForm"/>
<html:hidden property="mode"/>

<html:hidden property="positionOwnerId" name="positionForm"/>
<html:hidden property="departmentId" name="positionForm"/>
<html:hidden property="subDepartmentId" name="positionForm"/>
<html:hidden property="subSubDepartmentId" name="positionForm"/>
<html:hidden property="sub3DepartmentId" name="positionForm"/>
<html:hidden property="sub4DepartmentId" name="positionForm"/>
<html:hidden property="positionId" name="positionForm"/>
<html:hidden property="recruiterId" name="positionForm"/>
<html:hidden property="locationId" name="positionForm"/>
<html:hidden property="positionTypeExtInt" name="positionForm"/>
<html:hidden property="skillId" name="positionForm"/>
<html:hidden property="customFieldFilterId" name="positionForm"/>
<html:hidden property="customFieldFilterType" name="positionForm"/>
<html:hidden property="customFieldFilterValue" name="positionForm"/>
<% 
	if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
	<table id="m_errortable">
	    <tr>
        <td class="message"><html:errors/></td>               
	    </tr>
	</table><br>
<%
	}
%>

				<% 
				boolean showDesc=false; 
				boolean delete=false; 
				boolean changeStatus = false;		
				boolean addPosition=false; 
				boolean addPositionTemplate = false;
				boolean forcePositionCreationFromTemplate = false;
				%>
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_POSITION_DETAILS">
					<% showDesc=true;  %>
				</logic:equal>
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_DELETE_POSITION">
					<% delete=true;  %>
				</logic:equal>  
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_OPEN_CLOSE_POSITION">
					<% changeStatus=true;  %>
				</logic:equal>			
				<logic:equal value="true" name="canAddRequisition" scope="request" >
					<% addPosition=true;  %>
				</logic:equal>		
				<logic:equal value="1" name="forcePositionCreationFromTemplate" scope="request" >
					<% forcePositionCreationFromTemplate=true;  %>
				</logic:equal>	
				<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CREATE_POSITION_TEMPLATE">
					<% addPositionTemplate = true; %>
				</logic:equal>
				
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0" align="right" style="margin-bottom:10px;padding: 0px; "> 
				<tr >
					<td>
				   		<div id="positions_0" style="display: block;">
					   		&nbsp;<bean:message key="common.positions"/>
				   		</div>
				   	</td>
				   	<td>&nbsp;|&nbsp;</td>
			 		<td>
				   		<div id="drafts_1" style="display: block;">
					   		<a href="#" onclick="loadOther(1);" class="green"><bean:message key="common.drafts"/></a>
			   			</div>	
			  	 	</td>
			  	 		<% if(addPosition || addPositionTemplate){%>
			  	 	<td>&nbsp;|&nbsp;</td>
			  	 	<td>
				   		<div id="templates_1" style="display: block;">
					   		<a href="#" onclick="loadOther(2);" class="green"><bean:message key="common.templates"/></a>
			   			</div>	
			  	 	</td>
			  	 	<%} %>
				</tr>
			</table>
		</td>
		</tr>	
	</table>	
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				
			
					<div class="navBtnTab" style="width:400px;float: left;">
						<% if(showDesc){%>
							<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
							<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  							  
						  	<a href="#" onclick="javascript: viewDetails();" style="width:50px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="position.home.view_position_details"/></a> 
						 	<% if(addPosition){ %>
						 	<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
						 	<a href="#" onclick="copyPosition();return false;" style="width:50px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="positions_home.label.clone"/></a> 				
						 	<% } %>		
						  	<% if(delete){ %>
						  		<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
								<a href="#" onclick="javascript: deletePosition();" style="width:65px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.delete"/></a> 
							<% } %>						
							<% if(changeStatus){ %>
								<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
								<a href="#" onclick="javascript: changeStatus();" style="width:105px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="position.home.change_status"/></a> 
							<% } %>
						  	<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
	  						<a href="#" style="width:65px;" onmouseover="javascript: showMenuMore(dataGrid.getSelectedId(),'more');" id="more"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_more.gif" width="14" height="14" border="0" align="absmiddle" style="margin-right: 3px;"/><bean:message key="select.label.more"/></a>
	  						<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>
	  						<a href="#" style="text-align:right;background:none;padding-left:20px" onmouseover="changeImage('exportToExl', 'images/excel_co.GIF')" onmouseout="changeImage('exportToExl', 'images/excel_bw.GIF')">
							<img src="images/excel_bw.GIF" width="16" height="16" style="border:0px;" id="exportToExl" onclick="javascript:exportToExcel(); return false;" />
							</a>
  						<% } %>
  							
					</div>
				
			</td>
			<td>		
				<% if(addPosition && !forcePositionCreationFromTemplate){%>
					<div class="navBtnTab"  style="width:100px;float: right;"	>					
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  		
						<a href="#" onclick="addNewPosition();return false;" style="width:70px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="positions_home.label.add_new"/></a> 				
					</div>
				<% }  %>
				<% if(addPosition && forcePositionCreationFromTemplate){%>
					<div class="navBtnTab"  style="width:200px;float: right;"	>					
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>	  		
						<a href="#" onclick="loadOther(2);return false;" style="width:170px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="positions_home.label.add_new_position_from_template"/></a> 				
					</div>
				<% }  %>
			</td>
		
		</tr>
	</table>	
	<!--Grig Box is here -->
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder" style="border-bottom: 0px;">
				<div id="datagrid" style="width:738px;height:290px;margin-top: -1px;"></div>
			</td>
		</tr>
	</table> 
	<div class="outerDiv" style="margin: 0px; padding: 0px;border-top:1px dashed #C4C4C4;background-color:#f9f9f9;font-weight:bold;">
		<table style="border: 0px; height: 20px;" cellpadding="0" cellspacing="0">
				<tr>
					<td style="width:18px;" class=""></td>        
					<td id="total" style="color:#666;" class="">Total: 0</td>       
				</tr>
		</table>
	</div> 
	
	</html:form>
</div>
<script>
function changeImage(imgObj, imgSrc){
	$(imgObj).src=imgSrc;
}

function exportToExcel(){
	var url = "export.do?mode=exportPositions&ids="+dataGrid.getAllItemIds();
	window.setTimeout("showInPopUp('"+url+"',550,320,null,true);", 10);
}

function loadOther(choice){
	if(choice==1){
		window.location=uncache("drafts.do?mode=draftsHome");
	}else{
		window.location=uncache("positionTemplate.do?mode=positionTemplatesHome");
	}
}

function addNewPosition(){
	window.location.href="position.do?mode=description&showCondition="+checkboxListPositions.getSelectedIds()
}
function initGrid(){	
	   	dataGrid = new dhtmlXGridObject('<bean:message key="positions.home.id.datagrid" />'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader(",,<bean:message key="common.position" />,<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>,<bean:message key="common.vacancies" />,<bean:message key="position.home.inProcess" />,<bean:message key="common.pending_offers" />,<bean:message key="common.joined" />,<bean:message key="common.rejected" />,<bean:message key="position.home.expiry" />,"); 
	   	dataGrid.setInitWidths("20,25,183,100,60,70,80,40,60,78,0");
	   	dataGrid.enableMultiselect(true);
	   	dataGrid.setColAlign("left,left,left,left,center,center,center,center,center,left,left");
	   	dataGrid.setColTypes("ro,link,ro,ro,ro,ro,ro,ro,ro,ro,estat"); 
	   	dataGrid.setColSorting("na,custom_pos_priority_sort,custom_pos_name_sort,custom_dept_sort,sort_vacancies,sort_inprocess,sort_offered,sort_joined,sort_rejected,custom_date_sort");	
	   	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_POSITION_DETAILS">
		dataGrid.attachEvent("onKeyPress",onPositionKeyPressed);
		dataGrid.attachEvent("onRowDblClicked",onPositionRowDoubleClicked);
		</logic:equal>
		dataGrid.attachEvent("onXLE",dataGridOnLoadingEnd);
		dataGrid.attachEvent("onRowSelect",onPositionRowSelected);
		dataGrid.attachEvent("onAfterSorting",onAfterSorting);
		dataGrid.enableAutoHeigth(true,"360");  	
	   	dataGrid.init(); 	   		   	
	   	loadGrid();
	   	dataGrid.setSortImgState(true,2,"ASC");
}	

function onAfterSorting(index,type,direction) {	
	eraseCookie("GRD_POS" +userId);	
	createCookie("GRD_POS"+userId,index+"_"+direction,360);
}

function sort_vacancies(a,b,order,aId,bId) {	
	return sort_int_data(a,b,order);
}

function sort_inprocess(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"inProcess");
	b0 = dataGrid.getUserData(bId,"inProcess");	
	return sort_int_data(a0,b0,order);
}

function sort_offered(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"offered");
	b0 = dataGrid.getUserData(bId,"offered");	
	return sort_int_data(a0,b0,order);
}

function sort_joined(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"joined");
	b0 = dataGrid.getUserData(bId,"joined");	
	return sort_int_data(a0,b0,order);
}

function sort_rejected(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"rejected");
	b0 = dataGrid.getUserData(bId,"rejected");	
	return sort_int_data(a0,b0,order);
}

function sort_int_data(a,b,order) {
	if(order=="asc")
		return parseInt(a)>parseInt(b)?1:-1;
	else
		return parseInt(a)<parseInt(b)?1:-1;
}

function custom_pos_name_sort(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"positionName");
	b0 = dataGrid.getUserData(bId,"positionName");	
	return sort_data(a0,b0,order);
}
function custom_pos_priority_sort(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"positionPriority");
	b0 = dataGrid.getUserData(bId,"positionPriority");	
	return sort_int_data(a0,b0,order);
}

function custom_dept_sort(a,b,order,aId,bId) {
	a0 = dataGrid.getUserData(aId,"department");
	b0 = dataGrid.getUserData(bId,"department");	
	if(order=="asc") {
		if (a.toLowerCase()>b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()<b.toLowerCase()) {
			return -1;
		} else {
			a0 = dataGrid.getUserData(aId,"positionName");
			b0 = dataGrid.getUserData(bId,"positionName");
			return sort_data(a0,b0,'asc');
		}
	} else {
		if (a.toLowerCase()<b.toLowerCase()) {
			return 1;
		} else if (a.toLowerCase()>b.toLowerCase()) {
			return -1;
		} else {
			a0 = dataGrid.getUserData(aId,"positionName");
			b0 = dataGrid.getUserData(bId,"positionName");
			return sort_data(a0,b0,'asc');
		}
	}
}

function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function onPositionRowSelected(id,idx_col){
	onRowSelected(datagrid,dataGrid,id,idx_col);
}

function onPositionKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(datagrid,dataGrid,keyCode,ctrl,shift);
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
			viewDetails();	
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
		viewDetails();	
	}
}

function onRowSelected(grdId, grdObj, id, idx_col){
	if(grdId!=datagrid){
		dataGrid.clearSelection();		
	}
}

function custom_date_sort(a,b,order){
	var n=getConvertedDate(a);
	var m=getConvertedDate(b);
	if(order=="asc")
		return n>m?1:-1;
	else
		return n<m?1:-1;
}

function getConvertedDate(a){
	var dt1=new Date();
	if(a.length>0){
		dt1 = new Date(a.substring(0,2) + ' ' + a.substring(3,6) + ' 20' + a.substring(7,9)); 		
	}
	return dt1;		
}

function loadGrid(){
		var type = dataGrid.getSortingState();
		dataGrid.clearAll();
		if(type){
			dataGrid.setSortImgState(true,type[0],type[1]);
		}
		dataGrid.loadXML(encodeURI("position.do?mode=XMLforPositions"+ getCriteriaQryString()));
}


function deletePosition(){
   	var id = dataGrid.getSelectedId();
   	var deletePositionTokenId = '<%=tokenId%>';
   	if(id){
   		var ids=id.split(",");
   		if(ids.length<2){
   			if(!confirm("You are about to delete <bean:message key="common.position" /> '"+dataGrid.getUserData(id,"positionName")+"'. Continue?")){
   	   	    	return;
   	   	    }
   	   	    var pars = "mode=deletePosfromDB&positionId=" + id+"&deletePositionTokenId="+deletePositionTokenId;
   	   	    var myAjax = ajaxCall("position.do",'get',pars,onDeleteResponse, reportError);
   	   	}else {
   	   		alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
   	   	}
   	 }else{
   		alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to Delete.');
     	return; 
   	 }
}

function onDeleteResponse(request){
  	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
  	if(isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
		alert(errors[0]);
		return;
	}
	//get returned deleted ids and delete them from grid
	var deletedIds = getIds(xmlFile);
	for(var I=0; I<deletedIds.length; I++){
		dataGrid.deleteRow(deletedIds[I]);
	}
	dataGrid.clearSelection();  
}

function changeStatus(){
	var id = dataGrid.getSelectedId();
	if(id){
		var ids=id.split(",");
		if(ids.length<2){
			var positionStatus = dataGrid.getUserData(id,"positionStatus");
			var isBudgetCommitted = dataGrid.getUserData(id,"isBudgetCommitted");
			var url="position.do?mode=showPositionStatus&positionId="+id+"&showCondition="+positionStatus+"&isBudgetCommitted="+isBudgetCommitted;
			showInPopUp(url,500,270,loadGrid,true);
		}else {
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
		}
	}else {
		alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />  to change Status.');
	   	return;
	}
}


/*
function closePosition(){
  var id = dataGrid.getSelectedId();
  if(!id){
     	alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to Open/Close.');
     	return;
  }
  var positionStatus = dataGrid.getUserData(id,"positionStatus"); 
  var pars = "mode=changePositionStatus&positionId=" + id + "&positionStatus=" + positionStatus;
  var myAjax = ajaxCall("position.do",'get',pars,onChangeStatusResponse, reportError);
}

function onChangeStatusResponse(request){
  	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	 	if(isErrorXml(xmlFile)){
	 		errors = getErrors(xmlFile);
	 		if(errors[0]=='position.home.error.candidates_inprocess'){
				retValue = confirm('<bean:message key="common.position"/> <bean:message key="position.home.error.confirm_position_close"/>');
				if (retValue == true) {
					closePositionAndMarkCandidates();
				}
			}else{
				alert(errors[0]);	
			}
			return;			
		}
	  loadGrid();
}

function closePositionAndMarkCandidates() {
	  var id = dataGrid.getSelectedId();	  
	  var positionStatus = dataGrid.getUserData(id,"positionStatus"); 
	  var pars = "mode=markCandidatesAndClosePosition&positionId=" + id;
	  var myAjax = ajaxCall("position.do",'get',pars,onClosePositionResponse, reportError);
}

function onClosePositionResponse(request) {
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="position.home.error.position_close"/>');		
	} else {
		loadGrid();
	}	
}
*/
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


function viewCandidates(){
  var returnVal = false;
  var id = dataGrid.getSelectedId();
  if (id) {
	var ids=selId.split(",");
	if(ids.length<2){
		window.open('selectionProcess.do?mode=select&positionId='+id,'_self');
	}else {
		alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
	}
    returnVal = true;
  } else {
    alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> <bean:message key="positions_home.error.select_position_to_view_candidates" />');
  }  
  return returnVal;
}

function copyPosition(){
	var selId = dataGrid.getSelectedId();   
  	if(selId){
   		var ids=selId.split(",");
		if(ids.length<2){
			var positionStatus = dataGrid.getUserData(ids,"positionStatus");
			var canCopy = checkMigrationStatus(positionStatus);
			if(canCopy){
				window.location.href=uncache("position.do?mode=copyPosition&positionId="+selId + "&showCondition="+checkboxListPositions.getSelectedIds()+"&positionStatus="+<%= PositionConstants.POSITION_STATUS_INPROCESS%>);	
			}else{
				alert('<bean:message key="steps_migration.message.complete_position_migration_process" />');
			}
		}else {
			alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
		}
 	}else{
   		alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> <bean:message key="positions_home.error.select_position_to_clone" />');
   		return false;
   	}
   	return true;
}

function checkMigrationStatus(positionStatus){
	var closedPositionsMigrationPending=true;
	var openPositionsMigrationPending=true;
	<logic:present name="closedPositionsMigrationPending" scope="request">
		closedPositionsMigrationPending = false;
	</logic:present>
	<logic:present name="openPositionsMigrationPending" scope="request">
		openPositionsMigrationPending = false;
	</logic:present>
	if(_closedPosition==positionStatus)
		return closedPositionsMigrationPending;
	else
		return openPositionsMigrationPending;
} 
	

function savePositionAsTemplate(){
	var id = dataGrid.getSelectedId();   
   	if(id){	
   		var ids=id.split(",");
   		if(ids.length<2){
   			window.location.href=uncache("position.do?mode=copyPosition&positionId="+id + "&showCondition="+checkboxListPositions.getSelectedIds()+"&positionStatus="+<%=PositionConstants.POSITION_STATUS_TEMPLATE%>);
  	   	}else {
  	   		alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
  	  	}
	    
   }else {
    	alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to <bean:message key="position.home.save_as_template" />');
    	return false;
   }
   return true;
}

function onClickInProcess(pId) {
	window.location=uncache("selectionProcess.do?mode=select&positionId="+pId);
}

function onClickOffered(pId) {
	window.location=uncache("selectionProcess.do?mode=accept&filterByPosition="+pId+"&positionId="+pId);
}

function onClickJoined(pId) {
	window.location="selectionProcess.do?mode=accept&filterByPosition="+pId;
}

function onClickRejected(positionId){
	window.location="position.do?mode=positionSummary&showRej=1&positionId="+positionId+"#REJECTED";
}

function onClickPositionPriority(pId){
	var url = "position.do?mode=openPositionPriority&positionId="+pId;
	window.setTimeout("showInPopUp('"+url+"',350, 180,loadGrid,true);", 10);	
}
var userId = '';
function dataGridOnLoadingEnd() {
	var type = dataGrid.getSortingState();
	var val = readCookie("GRD_POS"+userId);
	if(val != null) {
		parts = val.split("_");
		sortGridRows(parts);
	} else {
		sortGridRows(type);
	}
	var footerTxt='';
	var obj = $('total');
	var rowCnt = dataGrid.getRowsNum();
	footerTxt = '<bean:message key="positions_home.text.total_positions" arg0='<%=TPLabels.getLabel("common.position") %>' arg1="'+rowCnt+'"  />';
	obj.innerHTML=footerTxt;
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
		default: 
			dataGrid.sortRows(type[0], "cus", order);//cus for custom
			break;
	}		
	dataGrid.setSortImgState(true,type[0],type[1]);		
}
function uncache(url){
	var d = new Date();
	var time = d.getTime();
	return url + '&ta='+time;
} 


function importPositions(){
	var url = "position.do?mode=uploadIndent";
	window.setTimeout("showInPopUp('"+url+"',520, 400,onLoadDraft);", 10);	
}

function onLoadDraft(returnVal) {
	var url = '';
	parts = returnVal.split("_");
	if(parts[0] == "file") {
		url = 'position.do?mode=loadDraft&relativeFilePath=' + parts[1];	
	} else {
		url = 'position.do?mode=loadDraft&draftId=' + parts[1];	
	}	
	window.location.href=url;
}

function getCriteriaQryString(){
	var qryString = "&positionOwnerId="+document.positionForm.positionOwnerId.value;
	qryString += "&departmentId="+document.positionForm.departmentId.value;
	qryString += "&subDepartmentId="+document.positionForm.subDepartmentId.value;
	qryString += "&subSubDepartmentId="+document.positionForm.subSubDepartmentId.value;
	qryString += "&sub3DepartmentId="+document.positionForm.sub3DepartmentId.value;
	qryString += "&sub4DepartmentId="+document.positionForm.sub4DepartmentId.value;
	qryString += "&positionId="+document.positionForm.positionId.value;
	qryString += "&recruiterId="+document.positionForm.recruiterId.value;
	qryString += "&locationId="+document.positionForm.locationId.value;
	qryString += "&positionTypeExtInt="+document.positionForm.positionTypeExtInt.value;	
	qryString += "&skillId="+document.positionForm.skillId.value;
	qryString += "&positionName="+$('positionName').value;
	qryString += "&showCondition="+checkboxListPositions.getSelectedIds();
	qryString += "&customFieldFilterId="+document.positionForm.customFieldFilterId.value;
	qryString += "&customFieldFilterType="+document.positionForm.customFieldFilterType.value;
	qryString += "&customFieldFilterValue="+document.positionForm.customFieldFilterValue.value;
	return qryString;
}

function bulkApproveRequisitions(){
	var positionIds = dataGrid.getSelectedId();
	if(positionIds){
		var url="requisitionfeedback.do?mode=bulkRequisitionApproval&positionId="+positionIds;
		window.setTimeout("showInPopUp('"+url+"',970,450,loadGrid,true);", 10);
	}else {
		alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to <bean:message key="position.home.approve_requisition" />');
    	return false;
	}
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function doOnLoad(){
	if('<bean:write property="finishCopyPosition" name="positionForm"/>'=='true'){
		window.location="position.do?mode=positionsHome";
		return;
	}
	initPopUp();	
	initGrid();
	Event.observe($('positionName'), "keyup", onPositionFilterChange.bindAsEventListener(this));
	criteriaPane= new criteriaPane('criteriaDiv'); // variable used in left pane
	applyPreFilters(); //function defined in left panel	
}

function viewPositionDetails(positionId){
	window.location.href=uncache("position.do?mode=description&positionId="+positionId);
}
window.onload=doOnLoad;
</script>