<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<%@page import="com.talentPool.common.NavigationConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.positions.manager.PositionScreenConfigurationManager"%>
<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<%@page import="java.lang.String"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<%@page import="com.talentPool.positions.PositionConstants"%><style>
<!--
.filterPanel{padding-left: 15px;padding-top: 5px;}
-->
</style>
<script>
var checkboxListSelectionStage=null;
function onChangeSelectionStageFilter(lst){
   var stepLevel = lst.getSelectedIds();
   var selectedText = lst.getSelectedOptions();
   if(stepLevel==''){
	   noStageSelected();
   }else{
	   	addStageCriteria(selectedText);
	   	applyFilter('<%=SelectionProcessConstants.FILTER_SELECTION_STAGE%>',stepLevel);
   }
}
function noStageSelected(){
	dataGrid.clearAll();
	alert('<bean:message key="applicant_home.error.select_stage_criteria_to_view_applicant"/> ');
	addStageCriteria('');
	return false;	
}
function addStageCriteria(selectedText){
   	criteriaPane.add(new criteriaOpt('<%=SelectionProcessConstants.FILTER_SELECTION_STAGE%>', selectedText));
   	criteriaPane.refreshCriteria();
}
</script>
<div style="margin-left:5px; ">
	<div style="margin-top:5px;width: 195px;background-color: #fdfdfd;border: 1px solid #99CC01; padding: 5px;">
	<table width="100%%">
		<tr>
		<td class="Grey"><bean:message key="common.filters"/></td>
		<td align="right" class="Grey">[<a href="#" onclick="resetFilters();return false;" class="green"><bean:message key="common.reset"/></a>]</td>
		</tr>
	</table>
	<div style="margin-top:5px; width: 195px; display: none" id="criteriaDiv" >
	</div>
	</div>		
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_down.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_APPLICANT %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_APPLICANT %>')" class="leftTitle"><bean:message key="common.candidate"/></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_APPLICANT %>" class="filterPanel" style="display: block;">
		<table cellspacing="2" cellpadding="2">
			<tr> 
		        <td><input type="text" name="applicantName" id="applicantName" style="width: 180px;" ></td> 
		    </tr> 
		</table>
	</div>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_DEPARTMENT %>" class="filterPanel" style="display: none;">
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_POSITION %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_POSITION %>')" class="leftTitle"><bean:message key="common.position"/></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_POSITION %>" class="filterPanel" style="display: none;">
	</div>
	<logic:equal name="t" scope="request" value="<%=NavigationConstants.T_SELECT%>">
		<div style="padding-top:15px;">
			<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>')" class="leftTitle"><bean:message key="common.stage"/></a>
		</div>
		<div id="filterpanel_<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>" class="filterPanel" style="display: none;">
			 <script type="text/javascript">
		         var opts = <bean:write name="JSSelectionStageArray" scope="request" filter="false"/>;
		         checkboxListSelectionStage = new CheckBoxList(opts,'<%=PositionConstants.STEP_LEVEL_SELECT%>,<%=PositionConstants.STEP_LEVEL_SHORTLIST%>',{namesonly:false, layerclass:'checkboxlistdiv', width:'150px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
		         document.write(checkboxListSelectionStage.getHtml());
		         checkboxListSelectionStage.setOnChangeHandler(onChangeSelectionStageFilter);
		         checkboxListSelectionStage.init();
		    </script>
		</div>
	</logic:equal>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_STEP %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_STEP %>')" class="leftTitle"><bean:message key="common.step"/></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_STEP %>" class="filterPanel" style="display: none;">
	</div>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_LOCATION %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_LOCATION %>')" class="leftTitle"><bean:message key="common.location"/></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_LOCATION %>" class="filterPanel" style="display: none;">
	</div>
	<%
		if(PositionScreenConfigurationManager.isDescriptionFieldShow(PositionConfigurationConstants.FIELD_POSITION_TYPE_EXT_INT)){
	%>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>')" class="leftTitle"><bean:message key="position.description.position_type_ext_int"/></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>" class="filterPanel" style="display: none;">
	</div>
	<% } %>
	<logic:equal name="t" scope="request" value="<%=NavigationConstants.T_SELECT%>">
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_ACTION %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_ACTION %>')" class="leftTitle"><bean:message key="common.action"/></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_ACTION %>" class="filterPanel" style="display: none;">
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=SelectionProcessConstants.FILTER_USER %>">&nbsp;<a href="#" onclick="activatePanel('<%=SelectionProcessConstants.FILTER_USER %>')" class="leftTitle"><bean:message key="common.user"/></a>
	</div>
	<div id="filterpanel_<%=SelectionProcessConstants.FILTER_USER %>" class="filterPanel" style="display: none;">
	</div>
	</logic:equal>
</div>
<script>
var selectedView = null;
<logic:equal name="t" scope="request" value="<%=NavigationConstants.T_SELECT%>">
	selectedView='<%=NavigationConstants.T_SELECT%>';
</logic:equal>
<logic:equal name="t" scope="request" value="<%=NavigationConstants.T_HIRE%>">
	selectedView='<%=NavigationConstants.T_HIRE%>';
</logic:equal>
var rightArrow='images/ico_arrow_right.gif';
var downArrow='images/ico_arrow_down.gif';
function activatePanel(panelId){
	if(checkStageSelected()){
		if($('filterimg_'+panelId).src.endsWith(rightArrow)){
			showPanel(panelId);
		}else{
			hidePanel(panelId);
		}	
	}
}
function checkStageSelected(){
	if(selectedView == '<%=NavigationConstants.T_SELECT%>'){
		var selctedStages = checkboxListSelectionStage.getSelectedIds();
		if(selctedStages==''){
			noStageSelected();
		}else{
			return true;
		}
	}else{
		return true;
	}
}
function setwait(panelId){
	if(panelId!='<%=SelectionProcessConstants.FILTER_APPLICANT %>'){
		$('filterpanel_'+ panelId).innerHTML="<table style=\"border:0px;\"><tr><td style=\"border:0px;\"><img src=images/wait.gif /></td><td style=\"border:0px;\">&nbsp;<bean:message key="common.please_wait"/></td></tr></table>";
	}
}
function showPanel(panelId){
	$('filterimg_'+panelId).src = downArrow;
	Effect.BlindDown('filterpanel_'+panelId,{duration:0.2});
	loadFilter(panelId);
	hideOtherPanels(panelId);
} 
function hidePanel(panelId){
	$('filterimg_'+panelId).src = rightArrow;
	Effect.BlindUp('filterpanel_'+panelId,{duration:0.2});
}
function hideOtherPanels(panelId){

	if(panelId!='<%=SelectionProcessConstants.FILTER_DEPARTMENT %>'){
		hidePanel('<%=SelectionProcessConstants.FILTER_DEPARTMENT %>');
	}
	if(panelId!='<%=SelectionProcessConstants.FILTER_POSITION %>'){
		hidePanel('<%=SelectionProcessConstants.FILTER_POSITION %>');
	}
	if(panelId!='<%=SelectionProcessConstants.FILTER_STEP %>'){
		hidePanel('<%=SelectionProcessConstants.FILTER_STEP %>');
	}
	if(panelId!='<%=SelectionProcessConstants.FILTER_LOCATION%>'){
		hidePanel('<%=SelectionProcessConstants.FILTER_LOCATION %>');
	}
	if(panelId!='<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>'){
		hidePanel('<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>');
	}
	if(panelId!='<%=SelectionProcessConstants.FILTER_APPLICANT %>'){
		//hidePanel('<%=SelectionProcessConstants.FILTER_APPLICANT %>');
	}
	<logic:equal name="t" scope="request" value="<%=NavigationConstants.T_SELECT%>">
		if(panelId!='<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>'){
			hidePanel('<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>');
		}
		if(panelId!='<%=SelectionProcessConstants.FILTER_ACTION %>'){
			hidePanel('<%=SelectionProcessConstants.FILTER_ACTION %>');
		}
		if(panelId!='<%=SelectionProcessConstants.FILTER_USER %>'){
			hidePanel('<%=SelectionProcessConstants.FILTER_USER %>');
		}
	</logic:equal>
}
function loadFilter(panelId){
	if(panelId!='<%=SelectionProcessConstants.FILTER_APPLICANT %>' && panelId!='<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>'){
		setwait(panelId);		
		var pars = 'mode=getHTMLForFilter&filterFor='+panelId+'&selectedView='+selectedView+getCriteriaQryString();		
		var myAjax = ajaxCall("selectionProcessFilters.do","get",pars,renderFilter,reportError);
	}
}
function renderFilter(request){
	xmlFile = request.responseXML;
	
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;		
		var panelId = op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		var selectedLink =  op.substring(0,op.indexOf("|"));
		op = op.substring(op.indexOf("|")+1);
		$('filterpanel_'+ panelId).innerHTML=getReplaced(op);
		criteriaPane.add(new criteriaOpt(panelId, selectedLink));
		criteriaPane.refreshCriteria();
	}else{
		alert('<bean:message key="common.error.unable_to_process_request"/>');
	}
	return false;
}
function getReplaced(txt){
	txt = txt.replace(/(&amp;nbsp;)/g,'&nbsp;');
	txt = txt.replace(/(&gt;)/g,'>');
	txt = txt.replace(/(&lt;)/g,'<');
	txt = txt.replace(/(&amp;gt;)/g,'&gt;');
	txt = txt.replace(/(&amp;lt;)/g,'&lt;');
	txt = txt.replace(/(&amp;quot;)/g,'&quot;');
	return txt;
}
function applyFilter(panelId, filterId){
	if(panelId=='<%=SelectionProcessConstants.FILTER_DEPARTMENT %>'){
		document.selectionProcessForm.departmentId.value=filterId;
	}else if(panelId=='<%=SelectionProcessConstants.FILTER_POSITION %>'){
		document.selectionProcessForm.positionId.value=filterId;
	}else if(panelId=='<%=SelectionProcessConstants.FILTER_LOCATION %>'){
		document.selectionProcessForm.locationTitle.value=filterId;
	}else if(panelId=='<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>'){
		document.selectionProcessForm.positionTypeExtInt.value=filterId;
	}else if(panelId=='<%=SelectionProcessConstants.FILTER_STEP %>'){
		document.selectionProcessForm.stepName.value=filterId;
	}else if(panelId=='<%=SelectionProcessConstants.FILTER_ACTION %>'){
		document.selectionProcessForm.actionRequired.value=filterId;
	}else if(panelId=='<%=SelectionProcessConstants.FILTER_USER %>'){
		document.selectionProcessForm.selectedUserId.value=filterId;
	}
	
	loadApplicantGrid();
	loadFilter(panelId);
	if(panelId=='<%=SelectionProcessConstants.FILTER_APPLICANT %>'){
		if(filterId!=''){
			filterId = filterId+'...';
		}
		criteriaPane.add(new criteriaOpt('<%=SelectionProcessConstants.FILTER_APPLICANT %>', filterId));
		criteriaPane.refreshCriteria();
	}
}

function resetFilters(){
	document.selectionProcessForm.departmentId.value='';
	document.selectionProcessForm.positionId.value='';
	document.selectionProcessForm.stepName.value='';
	document.selectionProcessForm.stepId.value='';
	document.selectionProcessForm.locationTitle.value='';
	document.selectionProcessForm.positionTypeExtInt.value='';
	<logic:equal name="t" scope="request" value="<%=NavigationConstants.T_SELECT%>">
		checkboxListSelectionStage.resetSelected('<%=PositionConstants.STEP_LEVEL_SHORTLIST%>,<%=PositionConstants.STEP_LEVEL_SELECT%>',opts);
		document.selectionProcessForm.actionRequired.value='';
		document.selectionProcessForm.selectedUserId.value='';
	</logic:equal>
	$('applicantName').value='';
	loadApplicantGrid();
	loadActivePanel();
	resetCriteriaPane();
}

function resetCriteriaPane(){
	criteriaPane.clearAll();
	criteriaPane.refreshCriteria();
	if(selectedView == '<%=NavigationConstants.T_SELECT%>')
		addStageCriteria(checkboxListSelectionStage.getSelectedOptions());
}

function loadActivePanel(){
	if($('filterimg_'+<%=SelectionProcessConstants.FILTER_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=SelectionProcessConstants.FILTER_DEPARTMENT %>);
	}else if($('filterimg_'+<%=SelectionProcessConstants.FILTER_POSITION %>).src.endsWith(downArrow)){
		loadFilter(<%=SelectionProcessConstants.FILTER_POSITION %>);
	}else if($('filterimg_'+<%=SelectionProcessConstants.FILTER_LOCATION %>).src.endsWith(downArrow)){
		loadFilter(<%=SelectionProcessConstants.FILTER_LOCATION %>);
	}else if($('filterimg_'+<%=SelectionProcessConstants.FILTER_ACTION %>)){
		if($('filterimg_'+<%=SelectionProcessConstants.FILTER_ACTION %>).src.endsWith(downArrow))
				loadFilter(<%=SelectionProcessConstants.FILTER_ACTION %>);
	}else if($('filterimg_'+<%=SelectionProcessConstants.FILTER_USER %>)){
		if($('filterimg_'+<%=SelectionProcessConstants.FILTER_USER %>).src.endsWith(downArrow))
				loadFilter(<%=SelectionProcessConstants.FILTER_USER %>);
	}
	if($('filterimg_'+<%=SelectionProcessConstants.FILTER_STEP %>)){
		if($('filterimg_'+<%=SelectionProcessConstants.FILTER_STEP %>).src.endsWith(downArrow)){
			loadFilter(<%=SelectionProcessConstants.FILTER_STEP %>);
		}
	}
	else if($('filterimg_'+<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>).src.endsWith(downArrow)){
		loadFilter(<%=SelectionProcessConstants.FILTER_POSITION_TYPE %>);
	}
}
function applyApplicantFilter(){
	applyFilter('<%=SelectionProcessConstants.FILTER_APPLICANT%>',$('applicantName').value);
}

var observer=false;
function onApplicantFilterChange(event){
var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			if(observer) clearTimeout(observer);
			observer = setTimeout(applyApplicantFilter.bind(this), 300);
		}
		
	}
}
//criteria class code
function deleteCriteria(panelId){
	if(panelId=='<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>'){
		deleteStagePanel();
	}else if(panelId=='<%=SelectionProcessConstants.FILTER_APPLICANT %>'){
		$('applicantName').value='';
		applyFilter(panelId,'');
	}else {
		applyFilter(panelId,'');
	}
	loadActivePanel();
}
function deleteStagePanel(){
	checkboxListSelectionStage.resetSelected('',opts);
	showPanel('<%=SelectionProcessConstants.FILTER_SELECTION_STAGE %>');
	noStageSelected();
}

//called onload
function applyPreFilters(){
	var openPanels = 0;
	if(document.selectionProcessForm.departmentId.value!=''){
		activatePanel('<%=SelectionProcessConstants.FILTER_DEPARTMENT %>');
		openPanels++;
	}
	if(document.selectionProcessForm.positionId.value!=''){
		activatePanel('<%=SelectionProcessConstants.FILTER_POSITION %>');
		openPanels++;
	}	
	<logic:equal name="t" scope="request" value="<%=NavigationConstants.T_SELECT%>">
	if(checkboxListSelectionStage.getSelectedOptions()!=''){
		addStageCriteria(checkboxListSelectionStage.getSelectedOptions());
	}
	if(document.selectionProcessForm.actionRequired.value!=''){
		loadFilter('<%=SelectionProcessConstants.FILTER_ACTION %>');
		activatePanel('<%=SelectionProcessConstants.FILTER_ACTION %>');
		openPanels++;		
		if(document.selectionProcessForm.stepName.value!=''){
			criteriaPane.add(new criteriaOpt('<%=SelectionProcessConstants.FILTER_STEP%>', document.selectionProcessForm.stepName.value));
			<% if(!Utils.isBlankOrNull(request.getParameter("userName"))) { %>
			criteriaPane.add(new criteriaOpt('<%=SelectionProcessConstants.FILTER_USER%>', '<%=request.getParameter("userName")%>'));
			<% } %>
			criteriaPane.refreshCriteria();
		}
	}
	</logic:equal>
	if(openPanels>1){
		hideOtherPanels('<%=SelectionProcessConstants.FILTER_ACTION %>');
	}		
}

function viewPositionSummary(positionId){
	window.location.href=uncache("position.do?mode=positionSummary&positionId="+positionId);
}

var criteriaPane=null; //initialize this variable on window load

</script>
