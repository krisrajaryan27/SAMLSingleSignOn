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
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script src="js/cookies.js"></script>
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
	<% } %> 
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div class="navBtnTab" style="width:170px;float: left;">
						<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
						<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
						<a href="#" onclick="javascript: publishToNaukri();" style="width:140px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.publish"/> /  <bean:message key="position.publish.label.unpublish"/> </a>
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
				<div id="GRD_WEBSITE_PUBLISH" style="width:736px;height:228px;"></div>
			</td>
		</tr>
	</table>
</div>
<br/><br/>
<script language="javascript">
var publishPositionsGrid = null;
var publishPositionsGridId = 'GRD_WEBSITE_PUBLISH';

function initGrid(){	
	publishPositionsGrid = new dhtmlXGridObject(publishPositionsGridId); 
	publishPositionsGrid.imgURL = "images/"; 
   	publishPositionsGrid.setHeader(",<bean:message key="common.position_code" />,<bean:message key="common.position_title" />,<bean:message key="position.publish_to_web_site.label.position_location" />,<bean:message key="position.publish_to_web_site.label.position_status" />");
   	publishPositionsGrid.attachHeader("&nbsp;,#text_filter,#text_filter,#text_filter,#select_filter_strict"); 
   	publishPositionsGrid.setInitWidths("10,219,219,150,119");
   	publishPositionsGrid.enableSmartRendering(true);
   	publishPositionsGrid.setColAlign("left,left,left,left,left");
   	publishPositionsGrid.setColTypes("ro,ro,ro,ro,ro"); 
   	publishPositionsGrid.setColSorting("na,cstr,cstr,cstr,cstr");
   	publishPositionsGrid.attachEvent("onRowDblClicked",onPublishGridRowDoubleClicked);
   	publishPositionsGrid.attachEvent("onKeyPress",onPublishGridKeyPressed);	
	publishPositionsGrid.enableAutoHeigth(true,"360");  	
	publishPositionsGrid.init(); 	   		   	
   	loadGrid();
   	publishPositionsGrid.setSortImgState(true,2,"ASC");
}

function loadGrid(){
	publishPositionsGrid.clearAll();
	publishPositionsGrid.loadXML("position.do?mode=XMLforPublishPositionsToNaukri");
}

function cancel() {
	window.location="position.do?mode=positionsHome";
}
function onPublishGridRowDoubleClicked(){
	publishToNaukri();
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
			publishToNaukri();	
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

function publishToNaukri(){
	var positionId = publishPositionsGrid.getSelectedId();
   	if(positionId){	
		var url="position.do?mode=publishPositionToNaukri&positionId="+positionId;
		window.setTimeout("showInPopUp('"+url+"',550,400,reloadWindow,true);",10);
   }else {
    	alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to <bean:message key="common.publish" />');
    	return false;
   }
   return true;
}

function reloadWindow(){
	window.location="position.do?mode=getAllPositionsToPublishToNaukri";
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function doOnLoad(){
	initPopUp();
	initGrid();
}

window.onload = doOnLoad;

</script>