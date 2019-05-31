<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.socialNetwork.utils.SocialEventStack"%>
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
				<div class="navBtnTab" style="width:170px;float: left;" title='<bean:message key ="position.home.unpublish_tooltip" />'>
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
					<a href="#" onclick="javascript: publishToWalkIn();" style="width:140px;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.publish"/> </a>
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
<div id="tokenResponse"></div>
<br/><br/>
<script language="JavaScript">
var publishPositionsGrid = null;
var publishPositionsGridId = 'GRD_WALKIN_PUBLISH';
var positionId= "";
var returnLocation = window.location.href;
var socialMediaTypeId="";
var socialId = "";
var postTitle ="";
var postContent = "";
var templateCode = "";
var redirect = '0';
var positionSelectedOnPage='';

function initGrid(){	
	publishPositionsGrid = new dhtmlXGridObject(publishPositionsGridId); 
	publishPositionsGrid.imgURL = "images/"; 
   	publishPositionsGrid.setHeader(",<bean:message key="common.position_title" />,<bean:message key="position.home.label.last_posted_on" />,<bean:message key="position.home.label.last_posted_date" />");
   	publishPositionsGrid.attachHeader("&nbsp;,#text_filter,#text_filter,#text_filter"); 
   	publishPositionsGrid.setInitWidths("10,219,268,220");
   	publishPositionsGrid.enableSmartRendering(true);
   	publishPositionsGrid.setColAlign("left,left,left,left");
   	publishPositionsGrid.setColTypes("ro,ro,ro,ro");
   	publishPositionsGrid.attachEvent("onRowDblClicked",onPublishGridRowDoubleClicked);
   	publishPositionsGrid.attachEvent("onKeyPress",onPublishGridKeyPressed); 
   	publishPositionsGrid.setColSorting("na,cstr,cstr,cstr");	
	publishPositionsGrid.enableAutoHeigth(true,"360");  	
	publishPositionsGrid.init(); 	   		   	
   	loadGrid();
   	publishPositionsGrid.setSortImgState(true,2,"ASC");
}

function loadGrid(){
	publishPositionsGrid.clearAll();
	publishPositionsGrid.loadXML("position.do?mode=XMLforPublishPositionsToSocialMedia");
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

function reloadPopup(){
	var queryParam = '';
	var params = (document.location.search).split("?");
	if(params[1] != null){
		queryParam = params[1].split("&"); 
	}
	for(var i=0;i<queryParam.length;i++){
		var temp = queryParam[i].split("=");
		if(temp[0]=="positionId"){
			positionId = temp[1];
		}else if(temp[0] == "socialMediaTypeId"){
			socialMediaTypeId = temp[1];
		}else if(temp[0] == "socialId"){
			socialId = temp[1];
		}else if(temp[0] == "postTitle"){
			postTitle = temp[1];
		}else if(temp[0] == "postContent"){
			postContent = temp[1];
		}else if(temp[0] == "templateCode"){
			templateCode = temp[1];
		}
	}
	
	if(positionId.length > 0 && positionId != 'null'){
		createPopUp();
	}
		
}



function cancel() {
	window.location="position.do?mode=positionsHome";
}

function publishToWalkIn(){
	positionSelectedOnPage = publishPositionsGrid.getSelectedId();
	if(positionSelectedOnPage != positionId){
	  redirect = '1';
	}else{
	  redirect = '0';
	}
	if(positionSelectedOnPage){	
	  createPopUp();
	}else {
	  alert('<bean:message key="common.please_select" /> <bean:message key="common.position" /> to <bean:message key="common.publish" />');
	  return false;
	}
	  return true;
}

function createPopUp(){
	var publishType = document.positionForm.publishType.value;
	var isPublished = publishPositionsGrid.getUserData(positionId,"positionPublished");
	var url = '';
	if(redirect == '0'){
		url="publishPosition.action?publishOrUnPublish="+isPublished+"&positionId="+positionId+"&publishType="+publishType+"&selectedSocialMediaTypeId="+socialMediaTypeId+"&templateCode="+templateCode+"&postTitle="+postTitle+"&postContent="+postContent+"&socialId="+socialId;
	}else{
		url = "publishPosition.action?publishOrUnPublish="+isPublished+"&positionId="+positionSelectedOnPage;
	}
    window.setTimeout("showInPopUp('"+url+"',750,730,reloadWindow,true);", 10);
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function reloadWindow(redirectFlag) {
	if(redirectFlag){
		var pars= redirectFlag.split("|");
		window.location="./oauthredirect.action?socialMediaId="+pars[0]+"&socialId="+pars[1]+"&postTitle="+pars[2]+"&postContent="+pars[3]+"&templateCode="+pars[4]+"&positionId="+positionSelectedOnPage+"&returnPath="+returnLocation.toString().replace("&","|");
	}
}

function appendString(str1, str2) {
	if(str1.length > 0) {
		str1 += ',';
	}
	str1 += str2;
	return str1;
}

window.onload=actionOnLoad;

function actionOnLoad(){
	initPopUp();
	initGrid();
	reloadPopup();
}
</script>
</html:form>