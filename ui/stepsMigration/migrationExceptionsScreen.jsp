<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_nxml.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script src="js/cookies.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
</head>
<body>
<s:form name="migrationExceptionScreen" method="POST">
<s:hidden name="migrationId" />
<s:hidden name="stepsFor" />
<div class="contentDiv">
<s:if test="hasActionErrors()">
	<table id="m_errortable">
		<tr>
			<td class="header" style="padding: 4px">
				Error
			</td>
		</tr>  
		<tr>
		   <td style="padding: 2px">
		    	<s:actionerror />
		    </td>
		</tr>  
	</table>
</s:if>	
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td valign="bottom">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" > 
		  <tr> 
		    <td valign="bottom">
		    	<div class="boxTab"><span class="rightC"></span><span class="leftC"></span>
		    		<img src="images/blank_small.gif" /><s:text name="steps_migration.label.exception_positions"></s:text>&nbsp;&nbsp;
		    	</div>
		    </td>
		    <td align="right">
				<div class="navBtnTab" style="width:110px;float: right;">
					<img src="images/btn_tabRightC.gif" width="10" height="40" align="right"  style="margin-top:-10px;"/>
					<img src="images/btn_tabLeftC.gif" width="10" height="40" align="left" style="margin-top:-10px;"/>
					<a href="#" class="btnExport" style="width:60px; margin-left:5px;cursor: pointer;" onclick="javascript: printExceptions();return false;" id="exportBtn" ><span class="rightC"></span><span class="leftC"></span><s:text name="common.print"/></a>
				</div>
			</td> 
		  </tr> 
	   </table> 
    </td> 
  </tr> 
</table> 
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="gridborder">
			<div id="EXCEPTION_POSITIONS" style="width: 738px;"  >
			</div>	
		</td>
	</tr>
</table>
<div class="outerDiv" style="margin: 0px; padding: 0px;border-top:0px dashed #C4C4C4;background-color:#f9f9f9;font-weight:bold;">
	<table style="border: 0px; height: 20px;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width:18px;" class=""></td>        
				<td id="totalPositions" style="color:#666;" class="">Total: 0</td>       
			</tr>
	</table>
</div> 	
<div id="wait">
	<table width="738px">
		<tr>
			<td width="50%">
				<div class="navBtn" style="float: left;margin-top: 5px;">
					<a href="#" style="width:60px;" class="active" onclick="javascript: goBack();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.back"/></a>
					<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript: resetMigration();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.reset"/></a>
				</div>
			</td>
			<td width="50%">
				<div class="navBtn" style="float: right;margin-top: 5px;">
					<a href="#" style="width:140px;" class="active" onclick="javascript: saveTemporarily();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="steps_migration.button.save_temporarily"/></a>
					<a href="#" style="width:140px; margin-left:5px;" class="active" onclick="javascript: confirmMigration();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="steps_migration.button.confirm_migrate"/></a>
				</div>				
			</td>
		</tr>
	</table>
</div>
</div>
</s:form>
</body>
<script type="text/javascript">
var exceptionPositionsGrid;
function initExceptionPositionsGrid(){
	exceptionPositionsGrid = new dhtmlXGridObject('EXCEPTION_POSITIONS'); 
	exceptionPositionsGrid.imgURL = "images/dhtmlxGrid/"; 
	exceptionPositionsGrid.setHeader("<s:text name="common.name"/>,<s:text name="common.position_status"/>"); 
	exceptionPositionsGrid.setInitWidths("510,200");
	exceptionPositionsGrid.setColAlign("left,left");
	exceptionPositionsGrid.setColTypes("ro,ro"); 
	exceptionPositionsGrid.setColSorting("cstr,cstr");
	exceptionPositionsGrid.attachEvent("onRowDblClicked",onRowDoubleClick);
	exceptionPositionsGrid.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	exceptionPositionsGrid.enableAutoHeigth(true,"400");
	exceptionPositionsGrid.enableMultiline(true);
	exceptionPositionsGrid.init();
	exceptionPositionsGrid.setHeaderCursor("pointer,pointer");
	loadExceptionPositionsGrid();
}
function loadExceptionPositionsGrid(){
	showUpdater('wait',{setHeight: false, setWidth: false, offsetLeft: 0});
	exceptionPositionsGrid.loadXML("getExceptionPositionsXML.action", onLoadEnd);	
}

function printExceptions(){
	exceptionPositionsGrid.printView();
}

function onLoadEnd(){
	var footerTxt='';
	var obj = $('totalPositions');
	var rowCnt = exceptionPositionsGrid.getRowsNum();
	footerTxt = '<s:text name="steps_migration.text.total_exceptions"><s:param>'+rowCnt+'</s:param></s:text>';
	obj.innerHTML=footerTxt;
	hideUpdater('wait');	
}
function reLoadExceptionPositionsGrid(){
	showUpdater('wait',{setHeight: false, setWidth: false, offsetLeft: 0});
	exceptionPositionsGrid.clearAll();
	exceptionPositionsGrid.loadXML("getExceptionPositionsXML.action", onLoadEnd);	
}
function doOnLoad(){
	initPopUp();
	initExceptionPositionsGrid();
}
function onRowDoubleClick(id){
	onClickExceptionPosition(id);
}
function onClickExceptionPosition(id){
	validatePosition(id);
}
function validatePosition(id){
	var url = "positionMigrationScreen.action?positionId="+id;
	window.setTimeout("showInPopUp('"+url+"',620,540,reLoadExceptionPositionsGrid,true);", 10);
}
function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function confirmMigration(){
	showUpdater('wait',{setHeight: false, setWidth: false, offsetLeft: 0});
	var exceptionsPositionLength = exceptionPositionsGrid.getRowsNum();
	if(exceptionsPositionLength>0){
		alert('<s:text name="steps_migration.alert.migrate_all_positions" />');
	}else {
		alert('<s:text name="steps_migration.message.migration_completed_successfully" />');
		document.migrationExceptionScreen.action="confirmAndMigrate.action";
		document.migrationExceptionScreen.submit();
	}
	hideUpdater('wait');	
}

function saveTemporarily(){
	document.migrationExceptionScreen.action="saveTemporarily.action";
	document.migrationExceptionScreen.submit();
}
function goBack(){
	if(confirm('<s:text name="steps_migration.confirm.back_to_stepmapping_warning" />')){
		document.migrationExceptionScreen.action="stepMapping.action";
		document.migrationExceptionScreen.submit();	
	}else{
		return false;
	}
}

function resetMigration(){
	if(confirm('<s:text name="steps_migration.confirm.reset_warning" />')){
		document.migrationExceptionScreen.action="cancelOrResetMigration.action";
		document.migrationExceptionScreen.submit();
	}
} 

window.onload = doOnLoad;
</script>
</html>