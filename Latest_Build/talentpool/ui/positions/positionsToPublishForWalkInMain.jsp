<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@ page import="com.talentPool.positions.dataobject.PositionData" %>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>   
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>			
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script src="js/cookies.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>								
<html:form action="/position">
<html:hidden property="mode" value="publishPositionToEmployeeOrWalkinPortal"/>
<html:hidden property="t" name="positionForm"/>
<html:hidden property="positionsToBePublished" name="positionForm"/>
<html:hidden property="positionsNotToBePublished" name="positionForm"/>
<html:hidden property="publishType" name="positionForm"/>
<script>
var pageSize=10;
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	var tip="";
	if(grdId == publishPositionsGridId){
		switch(obj.cell._cellIndex){
			case 0:
				return "";
				break;
			case 1:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_I_Comment");
				break;
			case 2:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_II_Comment");
				break;
			case 3:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_III_Comment");
				break;
			case 4:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_IV_Comment");
				break;
		}
	}	
	return obj.cell.innerHTML;
}

</script>
<div class="contentDiv">
	<div id="divError" style="display:block">
	<% if(request.getAttribute(Globals.ERROR_KEY)!=null){ %>
			<table id="m_errortable" > 
				<tr>
				    <td class='header'>
			        	<b><bean:message key="errors.following_errors"/></b>
				    </td>               
				</tr>
			    <tr>
		       		<td class="message"><html:errors/></td>               
			    </tr>
			</table><br/><br/>
	<% } else if(request.getAttribute("update") != null) { %> 
		<table  id="m_errortable" > 
			<tr>
		    <td class="header">
		        <b><bean:message key="common.position_s"/> <bean:message key="position.home.publish_successful"/></b>
		    </td>               
			</tr>
		</table>
		<br><br/>
	<% } %>
	</div> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="navBtnTab" style="width:170px;float: left;">
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
					<a href="#" onclick="javascript: publishToWalkIn();" style="width:140px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.publish"/> /  <bean:message key="position.publish.label.unpublish"/> </a>
				</div>
			</td>
			<td>
				<div style="float: right;height: 20px;">
			   		<a href="#" onclick="cancel();" class="green"><bean:message key="common.position"/> <bean:message key="header.label.st.home"/></a>
	   			</div>	
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="GRD_WALKIN_PUBLISH" style="width:736px;height:228px;"></div>
			</td>
		</tr>
	</table>
</div>
<br/><br/>
<script language="JavaScript">
var publishPositionsGrid = null;
var publishPositionsGridId = 'GRD_WALKIN_PUBLISH';

function initGrid(){	
	publishPositionsGrid = new dhtmlXGridObject(publishPositionsGridId); 
	publishPositionsGrid.imgURL = "images/"; 
   	publishPositionsGrid.setHeader(",<bean:message key="common.position_code" />,<bean:message key="common.position_title" />,<bean:message key="position.publish_to_web_site.label.position_location" />,<bean:message key="position.publish_to_web_site.label.position_status" />");
   	publishPositionsGrid.attachHeader("&nbsp;,#text_filter,#text_filter,#text_filter,#select_filter_strict"); 
   	publishPositionsGrid.setInitWidths("10,219,219,180,89");
   	publishPositionsGrid.enableSmartRendering(true);
   	publishPositionsGrid.setColAlign("left,left,left,left,left");
   	publishPositionsGrid.setColTypes("ro,ro,ro,ro,ro");
   	publishPositionsGrid.attachEvent("onRowDblClicked",onPublishGridRowDoubleClicked);
   	publishPositionsGrid.attachEvent("onKeyPress",onPublishGridKeyPressed); 
   	publishPositionsGrid.setColSorting("na,cstr,cstr,cstr,cstr");	
	publishPositionsGrid.enableAutoHeigth(true,"360");  	
	publishPositionsGrid.init(); 	   		   	
   	loadGrid();
   	publishPositionsGrid.setSortImgState(true,2,"ASC");
}

function loadGrid(){
	publishPositionsGrid.clearAll();
	publishPositionsGrid.loadXML("position.do?mode=XMLforPublishPositionsToWalkIn");
}

function onPublishGridRowDoubleClicked(){
	publishToWalkIn();
}
function onPublishGridKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(publishPositionsGridId,publishPositionsGrid,keyCode,ctrl,shift);
}

function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==publishPositionsGridId){
			publishToWalkIn();	
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

function setPopUpTitle() {	
	if(document.positionForm.publishType.value==<%=PositionConstants.PUBLISH_WALK_IN%>){
		window.top.setPopTitle('<b><bean:message key="common.publish"/> <bean:message key="common.positions"/> <bean:message key="position.home.title.publish_walkins" /></b>');
	}
	if(document.positionForm.publishType.value==<%=PositionConstants.PUBLISH_EMPLOYEE_PORTAL%>){
		window.top.setPopTitle('<b><bean:message key="common.publish"/> <bean:message key="common.positions"/> <bean:message key="position.home.title.publish_employee" /></b>');
	}	
}
function actionOnLoad(){
	initPopUp();
	initGrid();
}

window.onload=actionOnLoad;
function cancel() {
	window.location="position.do?mode=positionsHome";
}
/*function publish() {
	var selectedPositions = '';
	var deSelectedPositions = '';
	parts = posIds.split(',');
	if(parts.length > 0) {
		for(var i = 0; i < parts.length; i++) {
			var elemId = 'position_' + parts[i];
			var elemSrc = $(elemId).src;
			if(elemSrc.indexOf(chkBoxChked) != -1) {
				selectedPositions = appendString(selectedPositions, parts[i]);
			} else {
				deSelectedPositions = appendString(deSelectedPositions, parts[i]);
			}
		}
	}
	document.positionForm.positionsNotToBePublished.value = deSelectedPositions;
	document.positionForm.positionsToBePublished.value = selectedPositions;
	document.positionForm.submit();
}*/

function publishToWalkIn(){
	var positionId = publishPositionsGrid.getSelectedId();
	if(positionId){	
		var publishType = document.positionForm.publishType.value;
		var isPublished = publishPositionsGrid.getUserData(positionId,"positionPublished");
		var url = "position.do?mode=publishPositionToEmployeeOrWalkinPortal&publishOrUnPublish="+isPublished+"&positionId="+positionId+"&publishType="+publishType;
	    window.setTimeout("showInPopUp('"+url+"',550,400,reloadWindow,true);", 10);
	}else {
		alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to <bean:message key="common.publish" />');
		return false;
	}
	return true;
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function reloadWindow() {
	var publishType = document.positionForm.publishType.value;
	window.location="position.do?mode=getAllPositionsToPublish&publishType="+publishType;
}
function appendString(str1, str2) {
	if(str1.length > 0) {
		str1 += ',';
	}
	str1 += str2;
	return str1;
}
</script>
</html:form>