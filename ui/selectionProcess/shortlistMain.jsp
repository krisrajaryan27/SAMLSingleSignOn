<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<div class="contentDivPop" >
	<div class="outerDiv" style="width:580px;">
	<div class="popupTop">
		<table class="tblPop">
			<tr>
				<td class="header" style="vertical-align: top;">
					<bean:message key="common.select_position"/>
				</td>
			</tr>
			<tr>
				<td align="left" >
					<table cellspacing="0" cellpadding="0" border="0" class="filterGrid" >
						<tr>
							<td style="vertical-align: top;" align="left">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<input id="positionFilter" name="positionFilter" type="text" size="108" onfocus="onFilterFocus('positionFilter')" value="Filter" style="width:551px;color: graytext; border-bottom: 0px;" onclick="onFilterFocus('positionFilter','Filter');" onblur="onFilterUnfocus('positionFilter','Filter')"/>
										</td>
									</tr>
									<tr>
										<td class="gridborder" style="padding: 0px;">
											<div id="POSITION_GRD" class="gridbox" style="width:554px;height:100px;"></div>
										</td>
									</tr>
								</table>			
							</td>		
						</tr>
					</table>			
				</td>
			</tr>
									
		</table>
	</div>
	<div class="popupBody">
		<div id="divSelectStepOuter" style="display:none;">
			<table cellspacing="0" cellpadding="0" class="tblPop">
				<tr>
					<td class="header">
						<bean:message key="shortlist.label.select_step"/>
					</td>
				</tr>
				<tr>
					<td >
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<div id="divSelectStep"></Div>
					</td>
				</tr>
			</table>
		</div>							
	</div>	
</div>
<br/>
	<table cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td>
			<div id="buttonsDiv" class="navBtn" style="float: right;">
				<a href="#" style="width:65px;" class="active" onclick="javascript:shortlist();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
				<a href="#" style="width:65px;margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
	</table>
<br>
</div>
<script type="text/javascript">
var moveToStepId=0;
var returnVal =1;

function postPositionChange(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
      return;
    }
	var steps = xmlFile.getElementsByTagName('steps')[0];
	if(steps !=null){
		imgGroup="steps";
		var items = steps.getElementsByTagName("step");	
    	if(items.length>0){
    		var options ="";
    		for (var I = 0 ; I < items.length ; I++) {
				 var item = items[I];
			     
			     var id=getSingleElement(item,"id","");
			     var name=getSingleElement(item,"name","");
			     var opt=getSingleElement(item,"optional","");
			     if(opt ==1){
			     	name = name + " (<b>Optional</b>)";
			     }
			     options = options+'<img  src=\"images/radiobutton.gif\" name=\"'+imgGroup+'\" id=\"img_'+id+'\" ';
			     options = options+' onclick=\"onRadioChange(\''+imgGroup+'\','+id+')\"/>&nbsp;'+name+'<br/><br class="br5"/>';
			     if(I == 0){
			     	moveToStepId = id;
			     }
			}
			$('divSelectStepOuter').style.display='block';
			$('divSelectStep').innerHTML = options;
			onRadioChange(imgGroup,moveToStepId);
    	}
	}else{
    		$('divSelectStepOuter').style.display='none';
    		$('divSelectStep').innerHTML = '';
    		moveToStepId=0;
    }
}

function onRadioChange(imgGroupName, id){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id.indexOf("img") > -1) {
				if( theImage.id == 'img_'+id){
					moveToStepId = id;
					theImage.src = "images/checkedradiobutton.gif";
				}else{
					theImage.src = "images/radiobutton.gif";
				}
			}
	}
}

function shortlist(){
	showUpdater('buttonsDiv',{setHeight: false, setWidth: false, offsetLeft: 0});
	var applicantId = '<bean:write name="selectionProcessForm" property="applicantId" scope="request"/>';
	if(positionGrid.getSelectedId()!=null){
		shortlistSelected();
	}else{
		alert('Please select position');
	}
}

function shortlistSelected(){
	var applicantId = '<bean:write name="selectionProcessForm" property="applicantId" scope="request"/>';
	var positionId = positionGrid.getSelectedId();
	var pars = "mode=checkIfSourceEmployeeExistInSelectionProcessStepForPosition&applicantId="+applicantId+"&positionId="+positionId;
	var myAjax = ajaxCall("selectionProcess.do",'get',pars,onCheck, reportError);
}

function onCheck(request){
	xmlFile = request.responseXML;
	var errorMsg = '';
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;  	
  	if(isErrorXml(xmlFile)){
  		 errors = getErrors(xmlFile);  	    
  	   if(errors != null && errors.length > 0){
  	  	   for(var i=0;i<errors.length;i++){
  	  			errorMsg+=errors[i].split(',')[0]+", who referred "+errors[i].split(',')[1]+" is on the selection panel for the next step.\n";
  	  	  	}
 	  	  	alert(errorMsg);
 		}
  	}
  	shortlistSelectedAfterCheck();
}

function shortlistSelectedAfterCheck(){
	var applicantId = '<bean:write name="selectionProcessForm" property="applicantId" scope="request"/>';
	var positionId = positionGrid.getSelectedId();
	var pars = "mode=shortListApplicant&applicantId="+applicantId+"&positionId="+positionId;
	if(moveToStepId>0){
		pars = pars + "&nextPositionStepId="+moveToStepId;	
	}
	var myAjax = ajaxCall("selectionProcess.do","post",pars,postShortList,reportError);			
}

function postShortList(request){
    xmlFile = request.responseXML;
    if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
      return;
    }
    if(isErrorXml(xmlFile)){
		errors = getErrors(xmlFile);
		alert(errors[0]);
		hideUpdater('buttonsDiv');
		return;
	}	
	//refresh the window
	hideUpdater('buttonsDiv');
	window.top.hidePopWin(true);
	//window.location="selectionProcess.do?mode=viewOriginalResume&originalResume=<bean:write name="selectionProcessForm" property="originalResume" scope="request"/>&applicantId="+applicantId;
}


/******************* Locationd Grid Related Code ***************/
var positionGridId = 'POSITION_GRD';
var positionGrid = null;

function initPositionGrid(){
	positionGrid = new dhtmlXGridObject(positionGridId); 
	positionGrid.imgURL = "images/"; 
	positionGrid.setHeader('<bean:message key="position.requirements.primary_skills" />'); 
	positionGrid.setInitWidths("535");
	positionGrid.setColAlign("left");
	positionGrid.setColTypes("ro");
	positionGrid.setNoHeader(true);
	positionGrid.setColSorting("position_name_sort");
	positionGrid.enableMultiselect('false');	
	positionGrid.init();	
	positionGrid.sortRows(0,'str',"asc");
	positionGrid.attachEvent("onXLE",doOnLoadingEndLocation);
	positionGrid.attachEvent("onRowSelect",doOnPositionGridRowSelectHandler);
	positionGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	loadPositionGrid();	
}
function loadPositionGrid(){
	positionGrid.clearAll();
	positionGrid.loadXML("position.do?mode=getPositionForShortlist");	
}
function doOnLoadingEndLocation() {
	// sortRows doesn't work in IE11, skip it
	if (!(navigator.appName  == 'Netscape' && navigator.userAgent.indexOf("Trident") != -1)) {
		positionGrid.sortRows(0,'str',"asc");
	}
	positionGrid.setSortImgState(true,0,"ASC");
}

function doOnPositionGridRowSelectHandler() {
	moveToStepId=0;
	var positionId = positionGrid.getSelectedId();
	var pars = "mode=getStepsIfOptional&positionId="+positionId;
	var myAjax = ajaxCall("selectionProcess.do","post",pars,postPositionChange,reportError);	
		
	//positionGridSelected.clearSelection();
}

function position_name_sort(a,b,order,aId,bId) {
	a0 = dataGridRequisitioner.getUserData(aId,"positionName");
	b0 = dataGridRequisitioner.getUserData(bId,"positionName");	
	return sort_data(a0,b0,order);
}
//Common Function
function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}
/**
 * TO Filter Position Grid
 */
function onCriteriaChange(event){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		case Event.KEY_UP:
		case Event.KEY_DOWN: 
		break;
		case Event.KEY_PAGEUP:
		case Event.KEY_PAGEDOWN:
		break;
		case Event.KEY_RETURN:
		break;
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			positionGrid.filterBy(0, $('positionFilter').value, false);		
		}
	}
}


function setPopupTitle(){
	var title = '<b>';
	title+='<bean:message key="shortlist.label.shortlist"/>';
	<logic:notEmpty name="selectionProcessForm" property="applicantName" scope="request">
		title +='- <bean:write name="selectionProcessForm" property="applicantName" scope="request"/>';
	</logic:notEmpty>
	title +='</b>';
	window.top.setPopTitle(title);
	initPositionGrid();
	Event.observe($('positionFilter'), "keyup", onCriteriaChange.bindAsEventListener(this));
	
}
window.onload = setPopupTitle;

//-->
</script>
