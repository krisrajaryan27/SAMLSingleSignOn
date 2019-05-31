<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>

<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.documents.utils.DocumentUtils"%>
<%@page import="com.talentPool.documents.DocumentConstants"%><script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script src="js/tpSelectListFunctions.js"></script> 
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>							
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>		
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script type="text/javascript">
function eXcell_dochide(cell){
	 this.cell = cell;
	 this.grid = this.cell.parentNode.grid;
	 this.getValue = function(){
	 	return this.cell.innerHTML;
	 }
	}
eXcell_dochide.prototype = new eXcell;
eXcell_dochide.prototype.setValue = function(val){
	if(!val || isNaN(Number(val))){
		val = 0;
	}
	if(val==<%=DocumentConstants.HIDE%>){
	 	this.cell.parentNode.className='disabledrow';
	}
}
</script>											
<div class="contentDiv">
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
<%@ include file="positionTabs.jsp"%>
<html:form action="/position">
	<html:hidden property="mode" name="positionForm"/>
	<html:hidden property="dir" name="positionForm"/>
	<html:hidden property="positionId" name="positionForm"/>
	<html:hidden property="positionStatus" name="positionForm"/>			
	<div class="outerDiv" style="border-top:0px;padding-bottom:23px;height:335px;">
		<table>
			<tr>
				<td>
					<div class="contentDiv">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="gridborder">
						<div id="DOCUMENT_GRID" style="width:688px; height:290px;margin-top: 10px;"></div>
					</td>
				</tr>
			</table>
		</div>
				</td>
			</tr>
		</table>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="2">
				<br/>
				<div class="navBtn" style="float:right;">
					<a href="#" style="width:50px;" class="active" onclick="javascript:addDocument();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add"/></a>
					<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>	
</html:form>
</div>
<script language="JavaScript">
var positionId = '<bean:write name="positionForm" property="positionId" />';
var positionName = '<bean:write name="positionForm" property="positionName" />';
//**************Initialization,Loading,Event Handlers of grid 'Document Applicant Details Grid'**************/
//Document Grid initialisation 
var docGrid;
var docGridId = 'DOCUMENT_GRID';
function initDocGrid(){
	docGrid = new dhtmlXGridObject(docGridId);
	docGrid.imgURL = "images/"; 
	docGrid.setHeader(",,,<bean:message key="position_documents.label.file_name"/>,<bean:message key="common.user"/>,<bean:message key="common.date"/>,");
	docGrid.setInitWidths("18,20,20,250,210,140,0");
	docGrid.setColAlign("left,left,left,left,left,left,left");
	docGrid.setColTypes("ro,ro,ro,link,ro,ro,dochide");
	docGrid.setColSorting("na,na,na,cstr,cstr,custom_date_sort,na");
	docGrid.attachEvent("onRowDblClicked",onDocRowDoubleClick);
	docGrid.attachEvent("onKeyPress",onDocKeyPressed);
	docGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	docGrid.init();
	docGrid.setHeaderCursor(",,,pointer,pointer,pointer,");
	docGrid.setSortImgState(true,4,"DESC");
	window.setTimeout("loadDocGrid()", 2);
}
//Loading Document Grid
function loadDocGrid(){
	docGrid.clearAll();
	var url = "docs.do?mode=getPositionDocuments&positionId="+positionId;
	docGrid.loadXML(url);
}
//Document Grid Events
function onDocRowDoubleClick(){
	onClickDocument(docGrid.getSelectedId());
}
function onDocKeyPressed(keyCode,ctrl,shift){
	return onKeyPressed(docGridId,docGrid,keyCode,ctrl,shift);
}
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	if(grdId == docGridId){
		switch(obj.cell._cellIndex){
				case 0:
					return "<bean:message key="resume_summary.tooltip.delete"/>";
					break;
				case 1:
					return '<bean:message key="resume_summary.tooltip.hideshow"/>';
					break;
				case 2:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"fileName");
					break;
				case 3:
					return obj.grid.getUserData(obj.cell.parentNode.idd,"fileName");
					break;
				case 4:
					return unescapeHTML(obj.grid.getUserData(obj.cell.parentNode.idd,"name"));
					break;
		}
	}	
	return obj.cell.innerHTML;
}
/**********************************COMMON FUNCTIONS*********************/
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function backToPositionHome() {
	if(document.positionForm.positionStatus.value==<%=PositionConstants.POSITION_STATUS_TEMPLATE%>){
		window.location=uncache("positionTemplate.do?mode=positionTemplatesHome");
	}else{
		document.positionForm.mode.value='<%=PositionConstants.MODE_POSITION_HOME%>';
		document.positionForm.submit();
	}
	return true;
}


/********************************Documet Related Functions***********************/
function onClickDocument(id){
	if(id!=null){
		var filePath = docGrid.getUserData(id,"filePath");		
		var url = "<%=DocumentUtils.getDocumentURL("@fileName@","" )%>";
		url = url.replace(/(@fileName@)/g,filePath);
		window.open(url);
	}
}
function onClickDeleteDocument(dId){
	if(dId==null){
		alert('<bean:message key="resume_summary.error.select_file_to_delete"/>');
		return;
	}
	var pars = "mode=deletePositionDocument&documentId=" + dId+'&positionId='+positionId;
	var myAjax = ajaxCall("docs.do","get",pars,deleteDocRow,reportError);
}
function deleteDocRow(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="position_documents.error.delete_file"/>');
		return;
	}
	var msgIds=getIds(xmlFile);
	for(var I=0; I<msgIds.length; I++){
		docGrid.deleteRow(msgIds[I]);
	}
	docGrid.clearSelection();
}
function onClickHideDocument(dId){
	if(dId==null){
		alert('<bean:message key="position_documents.error.select_file_to_hide"/>');
		return;
	}
	var pars = "mode=hidePositionDocument&documentId=" + dId;
	var myAjax = ajaxCall("docs.do","get",pars,hideDocRow,reportError);
}
function hideDocRow(request){
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="position_documents.error.hide_file"/>');
		return;
	}
	loadDocGrid();
}
function addDocument(){
	var url = 'docs.do?mode=uploadPositionDocument&positionId=' +positionId+'&positionTitle='+'';
	window.setTimeout("showInPopUp('"+url+"',550,220,loadDocGrid,true);", 10);
}

/*********************************Loading Events*******************************/
window.onload=doOnLoad;
function doOnLoad(){
	initPopUp();
	initDocGrid();
}

/*********************************Common Grid Function**************************/
 var docGridPageSize = 10;
function onKeyPressed(grdId, grdObj,keyCode,ctrl,shift){
	var id = grdObj.getSelectedId();
	switch(keyCode){
	case 13:
		//enter key
		if(grdId==docGridId){
			onClickDocument(id);
		}
		break;
	case 33:
		//page up
		if(grdId==docGridId){
			pageSize=docGridPageSize;
		}
		var idx = grdObj.getRowIndex(id)-pageSize;
		idx = (idx<0)?0:idx;
		grdObj.selectRow(idx);
		break;
	case 34:
		//page down
		if(grdId==docGridId){
			pageSize=docGridPageSize;
		}
		var idx = grdObj.getRowIndex(id)+pageSize;
		idx = (idx>=grdObj.getRowsNum())?grdObj.getRowsNum()-1:idx;
		grdObj.selectRow(idx);	
		break;
	case 46:
		if(grdId==docGridId){
			onClickDeleteDocument(id);
		}
	}
	return true;
}
</script>