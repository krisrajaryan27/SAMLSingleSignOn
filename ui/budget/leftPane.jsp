<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.budget.BudgetConstants,
                  com.talentPool.common.NavigationConstants,
                  com.talentPool.masters.constants.MastersConstants,
                  com.talentPool.common.properties.TPApplicationProperties"%>

<style>
<!--
.filterPanel{padding-left: 15px;padding-top: 5px;}
-->
</style>
<div style="margin-left:5px; ">
	<div style="margin-top:5px;width: 195px;background-color: #fdfdfd;border: 1px solid #99CC01; padding: 5px;">
		<table width="100%%">
			<tr>
			<td class="Grey"><bean:message key="common.filters"/></td>
			<td align="right" class="Grey">[<a href="#" onclick="resetFilters();return false;" class="green"><bean:message key="common.reset"/></a>]</td>
			</tr>
		</table>
	<div style="margin-top:5px; width: 195px; display: none" id="criteriaDiv" ></div>
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_down.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_BUDGET_ITEM %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_BUDGET_ITEM %>')" class="leftTitle"><bean:message key="common.budget_item"/></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_BUDGET_ITEM %>" class="filterPanel" style="display: block;">
		<table cellspacing="2" cellpadding="2">
			<tr> 
		        <td><input type="text" name="budgetItemName" id="budgetItemName" size="30" ></td> 
		    </tr> 
		</table>	
	</div>

	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1)%></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2)%></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3)%></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	
	<% if(MastersConstants.DEPARTMENT_LEVEL_4.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL)) || MastersConstants.DEPARTMENT_LEVEL_5.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL))){%>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4)%></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	<%} %>
	
	<% if(MastersConstants.DEPARTMENT_LEVEL_5.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL))){%>
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5)%></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>" class="filterPanel" style="display: none;">	</div>
	<%} %>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_OWNER %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_OWNER %>')" class="leftTitle"><bean:message key="common.owner"/></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_OWNER %>" class="filterPanel" style="display: none;">
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_POSITION %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_POSITION %>')" class="leftTitle"><bean:message key="common.position"/></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_POSITION %>" class="filterPanel" style="display: none;">
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_GRADE %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_GRADE %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL)%></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_GRADE %>" class="filterPanel" style="display: none;">		
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_BAND %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_BAND %>')" class="leftTitle"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL)%></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_BAND %>" class="filterPanel" style="display: none;">		
	</div>
	
	<div style="padding-top:15px;">
		<img src="images/ico_arrow_right.gif" style="margin-bottom: -2px;" id="filterimg_<%=BudgetConstants.FILTER_STATUS %>">&nbsp;<a href="#" onclick="activatePanel('<%=BudgetConstants.FILTER_STATUS %>')" class="leftTitle"><bean:message key="common.status"/></a>
	</div>
	<div id="filterpanel_<%=BudgetConstants.FILTER_STATUS %>" class="filterPanel"  style="display: none">			
	</div>
</div>

<script>
var rightArrow='images/ico_arrow_right.gif';
var downArrow='images/ico_arrow_down.gif';

function activatePanel(panelId){
	if($('filterimg_'+panelId).src.endsWith(rightArrow)){
		$('filterimg_'+panelId).src = downArrow;
		Effect.BlindDown('filterpanel_'+panelId,{duration:0.2});
		loadFilter(panelId);		
		hideOtherPanels(panelId);
	}else{
		hidePanel(panelId)
	}
}

var observer=false;
function onTextTypeFilterChange(event,panelId){
	var iKeyCode = event.keyCode;
	switch(iKeyCode) {
		default:
		if((iKeyCode>=48 && iKeyCode<=90) || (iKeyCode>=96 && iKeyCode<=105) || iKeyCode==Event.KEY_BACKSPACE || iKeyCode==Event.KEY_DELETE){
			if(observer) clearTimeout(observer);
			observer = setTimeout(applyTextTypeFilter.bind(this,panelId), 300);
		}
		
	}
}

function applyTextTypeFilter(panelId){
	if(panelId='<%=BudgetConstants.FILTER_BUDGET_ITEM %>'){
		applyFilter(panelId,$('budgetItemName').value);
	}
}

function hideOtherPanels(panelId){	

	if(panelId!='<%=BudgetConstants.FILTER_BUDGET_ITEM %>'){
		//hidePanel('<%=BudgetConstants.FILTER_POSITION %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_DEPARTMENT %>'){
		hidePanel('<%=BudgetConstants.FILTER_DEPARTMENT %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>'){
		hidePanel('<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>'){
		hidePanel('<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>'){
		hidePanel('<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>'){
		hidePanel('<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_POSITION %>'){
		hidePanel('<%=BudgetConstants.FILTER_POSITION %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_OWNER %>'){
		hidePanel('<%=BudgetConstants.FILTER_OWNER %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_GRADE %>'){
		hidePanel('<%=BudgetConstants.FILTER_GRADE %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_BAND %>'){
		hidePanel('<%=BudgetConstants.FILTER_BAND %>');
	}
	if(panelId!='<%=BudgetConstants.FILTER_STATUS %>'){
		hidePanel('<%=BudgetConstants.FILTER_STATUS %>');
	}
}

function hidePanel(panelId){
	$('filterimg_'+panelId).src = rightArrow;
	Effect.BlindUp('filterpanel_'+panelId,{duration:0.2});
}

function loadFilter(panelId){
	if(panelId!='<%=BudgetConstants.FILTER_BUDGET_ITEM %>' &&  panelId!='<%=BudgetConstants.FILTER_STATUS %>' ){
		setwait(panelId);
		var pars = 'mode=getHTMLForFilter&filterFor='+panelId+getCriteriaQryString();
		var myAjax = ajaxCall("budgets.do","get",pars,renderFilter,reportError);
	}
	if(panelId=='<%=BudgetConstants.FILTER_STATUS %>' ){
		var statusPanel = document.getElementById( 'filterpanel_'+'<%=BudgetConstants.FILTER_STATUS %>' ) ;	
		
		if(document.budgetForm.status.value=='0'){
			statusPanel.innerHTML ='<table><tr><td title="draft"><bean:message key="budget.home.label.tooltip_draft_budget_item" /></a></td></tr></table>';
		}
		else if(document.budgetForm.status.value=='1'){
			statusPanel.innerHTML ='<table><tr><td title="draft"><bean:message key="budget.home.label.tooltip_active_budget_item" /></a></td></tr></table>';
		}
		else{
			statusPanel.innerHTML ='<table><tr><td title="draft"><a class="green" onclick="applyFilter(<%=BudgetConstants.FILTER_STATUS %>,<%=BudgetConstants.BUDGET_ITEM_STATUS_DRAFT %>);" href="#"><bean:message key="budget.home.label.tooltip_draft_budget_item" /></a></td></tr><tr><td title="active"><a class="green" onclick="applyFilter(<%=BudgetConstants.FILTER_STATUS %>,<%=BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE %>);" href="#"><bean:message key="budget.home.label.tooltip_active_budget_item" /></a></td></tr></table>';
		}
		statusPanel.style.display = "block";
	}
}

function setwait(panelId){
		$('filterpanel_'+ panelId).innerHTML="<table style=\"border:0px;\"><tr><td style=\"border:0px;\"><img src=images/wait.gif /></td><td style=\"border:0px;\">&nbsp;<bean:message key="common.please_wait"/></td></tr></table>";
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
	if(panelId=='<%=BudgetConstants.FILTER_DEPARTMENT %>'){
		document.budgetForm.deptId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>'){
		document.budgetForm.subDeptId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>'){
		document.budgetForm.subSubDeptId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>'){
		document.budgetForm.sub3DeptId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>'){
		document.budgetForm.sub4DeptId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_POSITION %>'){
		document.budgetForm.positionId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_OWNER %>'){
		document.budgetForm.ownerId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_STATUS %>'){
		document.budgetForm.status.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_GRADE %>'){
		document.budgetForm.gradeId.value=filterId;
	}else if(panelId=='<%=BudgetConstants.FILTER_BAND %>'){
		document.budgetForm.bandId.value=filterId;
	}
	if(panelId=='<%=BudgetConstants.FILTER_STATUS %>'){
		if(filterId=='0'){
			filterId ='<bean:message key="budget.home.label.tooltip_draft_budget_item" />';
		}else if(filterId=='1'){
			filterId = '<bean:message key="budget.home.label.tooltip_active_budget_item" />';
		}		
		
		criteriaPane.add(new criteriaOpt(panelId, filterId));
		criteriaPane.refreshCriteria();
	}
	
	loadGrid();
	loadFilter(panelId);

	if(panelId=='<%=BudgetConstants.FILTER_BUDGET_ITEM %>' ){
		if(filterId!=''){
			filterId = filterId+'...';
		}		
		criteriaPane.add(new criteriaOpt(panelId, filterId));
		criteriaPane.refreshCriteria();
	}
	
}

//criteria class code
function deleteCriteria(panelId){
	if(panelId=='<%=BudgetConstants.FILTER_BUDGET_ITEM %>'){
		$('budgetItemName').value='';
	}
	applyFilter(panelId,'');
	loadActivePanel();
}

function resetFilters(){
	document.budgetForm.deptId.value='';
	document.budgetForm.subDeptId.value='';
	document.budgetForm.subSubDeptId.value='';
	document.budgetForm.sub3DeptId.value='';
	document.budgetForm.sub4DeptId.value='';
	document.budgetForm.positionId.value='';
	document.budgetForm.ownerId.value='';
	document.budgetForm.status.value='';
	document.budgetForm.gradeId.value='';
	document.budgetForm.bandId.value='';
	$('budgetItemName').value='';
	
	loadGrid();
	loadActivePanel();
	criteriaPane.clearAll();
	criteriaPane.refreshCriteria();
}

function loadActivePanel(){
	if($('filterimg_'+<%=BudgetConstants.FILTER_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_DEPARTMENT %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_POSITION %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_POSITION %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_OWNER %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_OWNER %>);		
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_BAND %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_BAND %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_GRADE %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_GRADE %>);
	}else if($('filterimg_'+<%=BudgetConstants.FILTER_STATUS %>).src.endsWith(downArrow)){
		loadFilter(<%=BudgetConstants.FILTER_STATUS %>);		
	}
}

function applyPreFilters(){
	var openPanels = 0;
	if(document.budgetForm.deptId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_DEPARTMENT %>');
		openPanels++;
	}
	if(document.budgetForm.subDeptId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_SUB_DEPARTMENT %>');
		openPanels++;
	}
	if(document.budgetForm.subSubDeptId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_SUB_SUB_DEPARTMENT %>');
		openPanels++;
	}
	if(document.budgetForm.sub3DeptId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_SUB3_DEPARTMENT %>');
		openPanels++;
	}
	if(document.budgetForm.sub4DeptId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_SUB4_DEPARTMENT %>');
		openPanels++;
	}
	if(document.budgetForm.positionId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_POSITION %>');
		openPanels++;
	}
	if(document.budgetForm.ownerId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_OWNER %>');
		openPanels++;
	}
	if(document.budgetForm.gradeId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_GRADE %>');
		openPanels++;
	}
	if(document.budgetForm.bandId.value!=''){
		activatePanel('<%=BudgetConstants.FILTER_BAND %>');
		openPanels++;
	}
	if(openPanels>1){
		hideOtherPanels('');
	}
}

var criteriaPane=null; //initializehtth this variable on window load
</script>