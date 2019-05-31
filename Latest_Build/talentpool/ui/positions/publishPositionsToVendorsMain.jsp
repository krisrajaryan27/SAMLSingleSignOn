<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="com.talentPool.positions.PositionConstants"%>
<%@ page import="com.talentPool.positions.dataobject.PositionData" %>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>   
<script src="js/cookies.js"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
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
			case 5:
				return obj.grid.getUserData(obj.cell.parentNode.idd,"Col_V_Comment");
				break;			
		}
	}	
	return obj.cell.innerHTML;
}

</script>
<div class="contentDiv" >
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
				<div class="navBtnTab" style="width:270px;float: left;">
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
						<a href="#" onclick="javascript: publishToVendor();" style="width:140px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.publish"/> /  <bean:message key="position.publish.label.unpublish"/> </a>
						<img src="images/dot_gray.gif" width="1" height="16" vspace="3" align="left"/>	  							  
					  	<a href="#" onclick="javascript: notify();" style="width:100px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.send"/> <bean:message key="common.email"/></a> 
				</div>
			</td>
			<td>
				<div style="float: right;">
			   		<a href="#" onclick="cancel();" class="green"><bean:message key="common.position"/> <bean:message key="header.label.st.home"/></a>
	   			</div>	
			</td>
		</tr>
	</table>	
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="GRD_VENDOR_PUBLISH" style="width:736px;height:228px;"></div>
			</td>
		</tr>
	</table>
</div>
<br/><br/>
<script language="JavaScript">
var publishPositionsGrid = null;
var publishPositionsGridId = 'GRD_VENDOR_PUBLISH';
function initGrid(){	
	publishPositionsGrid = new dhtmlXGridObject(publishPositionsGridId); 
	publishPositionsGrid.imgURL = "images/"; 
   	publishPositionsGrid.setHeader(",<bean:message key="common.position_code" />,<bean:message key="common.position_title" />,<bean:message key="position.publish.label.position_location" />,<bean:message key="position.publish.label.position_vendors" />,<bean:message key="position.publish.label.position_status" />");
   	publishPositionsGrid.attachHeader("&nbsp;,#text_filter,#text_filter,#text_filter,#text_filter,#select_filter_strict"); 
   	publishPositionsGrid.setInitWidths("10,225,189,150,150,99");
   	publishPositionsGrid.enableMultiselect(true);
   	publishPositionsGrid.enableSmartRendering(true);
   	publishPositionsGrid.setColAlign("left,left,left,left,left,left");
   	publishPositionsGrid.setColTypes("ro,ro,ro,ro,ro,ro"); 
   	publishPositionsGrid.setColSorting("na,cstr,cstr,cstr,cstr,cstr");
   	publishPositionsGrid.attachEvent("onRowDblClicked",onPublishGridRowDoubleClicked);
   	publishPositionsGrid.attachEvent("onKeyPress",onPublishGridKeyPressed);	
	publishPositionsGrid.enableAutoHeigth(true,"360");  	
	publishPositionsGrid.init(); 	   		   	
   	loadGrid();
   	publishPositionsGrid.setSortImgState(true,2,"ASC");
}

function loadGrid(){
	publishPositionsGrid.clearAll();
	publishPositionsGrid.loadXML("position.do?mode=XMLforPublishPositionsToVendors");
}

function onPublishGridRowDoubleClicked(){
	publishToVendor();
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
			publishToVendor();	
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

function publishToVendor() {
	var positionId = publishPositionsGrid.getSelectedId();
   	if(positionId){	
   		var ids=positionId.split(",");
   		if(ids.length<2){
   			var url = "position.do?mode=publishPosition&positionId="+positionId;
   		    window.setTimeout("showInPopUp('"+url+"',550,400,reloadWindow,true);", 10);
  	   	}else {
  	   		alert("<bean:message key="common.please_select"/> <bean:message key="common.single"/> <bean:message key="common.position"/>");
  	  	}
   }else {
    	alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to <bean:message key="common.publish" />');
    	return false;
   }
   return true;
}

function notify(){	
	var positionIds=publishPositionsGrid.getSelectedId();
	if(positionIds){
		var url="massEmail.do?mode=vendorNotificationEmail&selectedIds="+positionIds+"&newEmailType=v";
		window.setTimeout("showInPopUp('"+url+"',760,430,'',true);", 10);
	}else {
		alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />(s) to send notifications');
		return;
	}
}

function reloadWindow() {
	window.location="position.do?mode=publishPositionsToVendors";
}

function cancel() {
	window.location="position.do?mode=positionsHome";
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function actionOnLoad(){
	initPopUp();
	initGrid();	
}

window.onload=actionOnLoad;
</script>