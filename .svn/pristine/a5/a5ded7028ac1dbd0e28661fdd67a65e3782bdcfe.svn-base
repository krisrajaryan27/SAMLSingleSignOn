<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<%@ page import="org.apache.struts.Globals,
				com.talentPool.common.properties.TPApplicationProperties"%>

<link rel="STYLESHEET" type="text/css" href="themes/default/dhtmlXMenu.css">
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">

<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>

<script language="JavaScript">
eXcell_link.prototype.getTitle=function(){
	return getCustomTitle(this);
}

function getCustomTitle(obj){
	switch(obj.cell._cellIndex){
	case 1:
		return obj.grid.getUserData(obj.cell.parentNode.idd,"value");
		break;	
	}
	//if no special tooltip - return current value
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
	<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	  <tr> 
	    <td  valign="bottom">
		<table cellpadding="0" cellspacing="0" border="0" class="boxETab">
		<tr>
			<td class="leftC"></td>
			<td class="content Grey" style="padding-left:10px; padding-right:10px;">Messages</td>
			<td class="rightC"></td>
		</tr>
		</table>
	    </td> 
	    <td>	
	    	&nbsp;
	    </td> 
	  </tr> 
	</table> 
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td class="gridborder">
				<div id="gridBox" style="height:355px; width:738px;" ></div>
			</td>
		</tr>
	</table>
	
	<br/>
	<table>
	<tr>
	<td class="Grey">Note: The new changes will be applied only after restarting the TalentPool service</td>
	</tr>
	</table>
	<br/>
	<div class="navBtn" style="float:left;">
		<a href="#" style="width:120px;" class="active" onclick="javascript: restoreDefault();"><span class="rightC"></span><span class="leftC"></span><bean:message key="admin.messages.label.restore_default"/></a>
	</div>
</div>
<script> 
window.onload = doOnLoad;
var gridBox ;

function doOnLoad(){
	initPopUp();	
	gridBox = new dhtmlXGridObject('gridBox'); 
	gridBox.imgURL = "images/dhtmlxGrid/"; 
	gridBox.setHeader("<bean:message key="admin.messages.label.key"/>,<bean:message key="admin.messages.label.value"/>"); 
	gridBox.setInitWidths("360,360");
	gridBox.setColAlign("left,left");
	gridBox.setColTypes("ro,link"); 
	gridBox.setColSorting("cstr,cstr");
	gridBox.attachEvent("onRowDblClicked",onRowDoubleClick);
	gridBox.attachEvent("onKeyPress",onGridObjKeyPressed);
	gridBox.checkSessionExpiry(true,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>');
	gridBox.init();
	
  	gridBox.loadXML("adminHome.do?mode=getMessages");
  	gridBox.setSortImgState(true,0,"ASC");
}

function restoreDefault() {
	window.location.href="adminHome.do?mode=restoreDefault";
}

function onGridObjKeyPressed(keyCode,ctrl,shift) {
	var pageSize = 10;
	var id = gridBox.getSelectedId();	
	switch(keyCode){				
		case 33:
			//page up
			var idx = gridBox.getRowIndex(id)-pageSize;
			idx = (idx<0)?0:idx;
			gridBox.showRow(gridBox.getRowId(idx));
			gridBox.selectRow(idx);
			break;
		case 34:
			var idx = gridBox.getRowIndex(id)+pageSize;
			idx = (idx>=gridBox.getRowsNum())?gridBox.getRowsNum()-1:idx;
			gridBox.showRow(gridBox.getRowId(idx));
			gridBox.selectRow(idx);	
			break;
			//page down
		case 38:
			// up arrow key
			var idx = gridBox.getRowIndex(id)-1;			
			idx = (idx<0)?0:idx;
			idx=gridBox.getRowIndex(gridBox.getRowId(idx)) + 1;
			gridBox.showRow(gridBox.getRowId(idx));
			gridBox.selectRow(idx);
			break;
		case 40:
			// down arrow key
			var idx = gridBox.getRowIndex(id)+1;			
			idx = (idx>=gridBox.getRowsNum())?gridBox.getRowsNum()-1:idx;
			idx=gridBox.getRowIndex(gridBox.getRowId(idx)) - 1;
			gridBox.showRow(gridBox.getRowId(idx));
			gridBox.selectRow(idx);	
			break;
		case 13:
			// enter key
			viewDetails(id);
			break;
		default:
			var type = gridBox.getSortingState();			
			if((keyCode >= 48 && keyCode <= 57)||(keyCode >= 65 && keyCode <= 90)) {
				var ch = String.fromCharCode(keyCode);
				if(gridBox.getSelectedId() != null) {
					var indx = gridBox.getRowIndex(gridBox.getSelectedId());										
					var val = gridBox.cells2(indx,type[0]).getValue();										
					if(val != null && val.substring(0,1).toUpperCase() == ch && (indx+1) < gridBox.getRowsNum()) {										
						var nextRow = gridBox.cells2(indx+1,type[0]);
						if(nextRow) {
							val = nextRow.getValue();
							if(val != null && val.substring(0,1).toUpperCase() == ch) {
								gridBox.setSelectedRow(gridBox.getRowId(indx+1),false,true,false);
								return;
							}
						}			
					}
				}		
				for(var i=0;i<gridBox.getRowsNum();i++){ 										
					var val = gridBox.cells2(i,type[0]).getValue();
					if(val != null && val.substring(0,1).toUpperCase() == ch) {
						gridBox.setSelectedRow(gridBox.getRowId(i),false,true,false);
						break;			
					}
				}	
			}	
	}	
	return true;	
}

function onRowDoubleClick(id){
	viewDetails(id);
}

function viewDetails(key) {  
  var val = gridBox.getUserData(key,"value");  
  showPopWin("adminHome.do?mode=editMessage&key="+key+"&value="+escape(val), "650", "200", reloadGrid,true);
}    

function reloadGrid(returnVal) {
	var retVal = returnVal;
	var parts = retVal.split(':::');
	var id = parts[0];
	var val = parts[1];
	gridBox.setUserData(id,"value",val.replace("&lt;","<").replace("&gt;",">"));
	cell = gridBox.cells(id,1);
	val = val + '^javascript:viewDetails("'+ id + '");^_self';
	cell.setValue(val);
}
</script>
