<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.requisition.constants.RequisitionConstants"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.requisition.dataobject.RequisitionFeedbackData"%>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>							
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/commonGridFunctions.js" type="text/javascript"></script>
<script language="JavaScript">
function getCustomTitle(obj){
	var grdId = obj.grid.entBox.id; 
	switch(obj.cell._cellIndex){
		case 0:
			return "<bean:message key="common.delete"/>";
			break;
		case 4:
			return "<bean:message key="common.view_details"/>";
			break;	
	}
	return obj.cell.innerHTML;
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
		<html:hidden property="dest" name="positionForm"/>
		<html:hidden property="step" name="positionForm"/>
		<html:hidden property="positionId" name="positionForm"/>	
		<html:hidden property="feedbackDecision" name="positionForm"/>	
		<html:hidden property="nextUserId" name="positionForm"/>
		<html:hidden property="fromApprovalStepId" name="positionForm"/>	
		<html:hidden property="toApprovalStepId" name="positionForm"/>	
		<html:hidden property="showCondition" name="positionForm"/>	
		<html:hidden property="feedbackId" name="positionForm" value=""/>
		<html:hidden property="positionStatus" name="positionForm"/>			
		<div class="outerDiv" style="border-top:0px;height:335px;">
		<table>
			<tr>
				<td>
					<div class="contentDiv">
						<table class="posinput" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="label"><bean:message key="position.approval.label.current_status"/> </td>
								<td><%=Utils.escapeHTML((String)request.getAttribute("currentStatus"))%></td>
								<td class="label"></td>
								<td>
								<% String requisitionFeedbackId = (String)request.getAttribute("requisitionFeedbackId");								
									if(!Utils.isBlankOrNull(requisitionFeedbackId)){ %>									
									<a href="#" onclick="onClickEnterFeedback('<bean:write name="positionForm" property="positionId"/>');return false;" 
			         					class="grey">
	    			     				<bean:message key="select.label.enter_feedback"/>
	         						</a>
	         						<%} else {}%>
								</td>
							</tr>
						</table>
						<br/>
						<div id="dataGrid" style="width: 688px;height: 18px;"></div>
						<div style="border-bottom:1px solid #999999; position: relative;"> </div>
					</div>		
				</td>
			</tr>
		</table>
		</div>
		<br/>
		<div class="navBtn" style="float:right;">
			<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
		</div>
	</html:form>
</div>
<script language="JavaScript">
var dataGrid=null;
var maxHeight=250;
function initGrid(){
        dataGrid = new dhtmlXGridObject('dataGrid'); 
	   	dataGrid.imgURL = "images/"; 
	   	dataGrid.setHeader("&nbsp;,<bean:message key="position.approval_summary.label.date"/>,<bean:message key="position.approval_summary.label.user"/>,<bean:message key="position.approval_summary.label.step"/>,<bean:message key="position.approval_summary.label.status"/>"); 
	   	dataGrid.setInitWidths("18,150,135,200,165");
	   	dataGrid.setColAlign("left,left,left,left,left");
	   	dataGrid.setColTypes("ro,ro,ro,ro,link"); 
	   	dataGrid.setColSorting("na,na,na,na,na");
		dataGrid.enableAutoHeigth(true,maxHeight);
		dataGrid.attachEvent("onRowDblClicked",onRowDoubleClick);
	   	dataGrid.init(); 
		dataGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	   	loadGrid();
}
function loadGrid(){
	dataGrid.clearAll();
	dataGrid.loadXML("position.do?mode=getApprovalHistory&positionId=<bean:write property="positionId" name="positionForm"/>");
}
function onRowDoubleClick(id,idx_col){
	onClickFeedback(id);
}
function onClickFeedback(feedbackId){
	var url = 'requisitionfeedback.do?mode=viewfeedback&feedbackId='+feedbackId;
	window.setTimeout("showInPopUp('"+url+"',650, 518,reloadPage,true);", 10);
}
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function reloadPage(feedbackId){
	document.positionForm.mode.value='<%=PositionConstants.MODE_APPROVAL%>';
	document.positionForm.feedbackId.value='';
	document.positionForm.submit();
}

function onClickDeleteFeedback(feedbackId){
	document.positionForm.mode.value='deleteApprovalFeedback';
	document.positionForm.feedbackId.value=feedbackId;
	document.positionForm.submit();
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


function onClickEnterFeedback(aId){	
	var url = 'requisitionfeedback.do?mode=feedback&positionId='+aId;
	window.setTimeout("showInPopUp('"+url+"',650, 518,loadGrids,true);", 10);
}

function loadGrids() {
	loadGrid();
}

function doOnLoad() {
	initGrid();
	initPopUp();
}
window.onload=doOnLoad;

</script>