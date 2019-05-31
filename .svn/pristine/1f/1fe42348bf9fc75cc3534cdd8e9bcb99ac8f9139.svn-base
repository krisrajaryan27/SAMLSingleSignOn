<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.masters.constants.MastersConstants,
	com.talentPool.masters.constants.StepConstants,
	com.talentPool.common.properties.TPApplicationProperties"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>

<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript">

var selectBoxStage=null;
var selectBoxInsertAfter=null;

<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>                
</script>

<div class="contentDivPop" style="width:450px;">
	<% 
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
			<table id="m_errortable" > 
				<tr>
			    	<td class='header'>
		        		<b><bean:message key="errors.following_errors"/></b>
			    	</td>               
				</tr>
		    	<tr>
	        		<td class="message"><html:errors/></td>               
		    	</tr>
			</table>
			<br/><br/>
	<%
		}
	%> 
	<div class="outerDiv">
	<html:form action="/step" onsubmit="submitForm();return false;">
  	<html:hidden property="stepId" name="mastersForm"/>
 	<html:hidden property="mode" name="mastersForm"/>
	<html:hidden property="stepLevel" name="mastersForm"/>
	<html:hidden property="stepSchedulable" name="mastersForm"/>
	<html:hidden property="stepDisabled" name="mastersForm"/>	
	<html:hidden property="stepRank" name="mastersForm"/>
	<div class="popupTop">
		<table class="tblPop">
			<tr>
				<td class="header">
					<bean:message key="master_steps.label.stage_name" />
					<span class="star">*</span>:
				</td>
				<td>
				<logic:empty name="mastersForm" property="stage">
					<script language="JavaScript">
						var options = [new SelectOption('-1', '<bean:message key='master_steps.label.select_stage' />')];				
						selectBoxStage = new SelectBox(options,'-1','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10});
						document.write(selectBoxStage.getHtml());
						selectBoxStage.setOnChangeHandler('onChangeStage');
						selectBoxStage.init();
					</script>
				</logic:empty>		
				<logic:notEmpty name="mastersForm" property="stage">
					<html:text property="stage" name="mastersForm" disabled="true"></html:text>
				</logic:notEmpty>
				</td>
			</tr>	
			<tr>
				<td class="header">
					<bean:message key="master_steps.label.step_name"/>
				  	<span class="star">*</span>:
			  	</td>
			  	<td>
					<html:text property="stepName" name="mastersForm" size="45" maxlength="50" ></html:text>
			  	</td>
		  	</tr>
		  	<logic:empty name="mastersForm" property="stage">					
		  	<tr>
				<td class="header">
					<bean:message key="master_steps.label.insert_after" />:
				</td>
				<td>
					<script language="JavaScript">
						var options = [new SelectOption('-1', '<bean:message key='master_steps.label.select_step' />')];
						selectBoxInsertAfter = new SelectBox(options,(options.length -1),'images/btn_dropdown.gif',{namesonly:false, width:'200px', size:15});
						document.write(selectBoxInsertAfter.getHtml());
						selectBoxInsertAfter.init();
					</script>					
				</td>
			</tr>
			</logic:empty>
		  	<tr>
				<td style="height: 20px;vertical-align: bottom;"class="header"><bean:message key="master_steps.label.schedulable" /> ?</td>
				<td style="height: 20px;vertical-align: bottom;"><img id="schedulable" src="" onclick="javascript: toggleCheckBox(this);" /></td>
			</tr>		
			<tr>
				<td class="header" style="vertical-align: top;">
					<bean:message key="master_steps.label.step_desc"/>:
			  	</td>
			  	<td>
					<html:textarea property="stepDesc" name="mastersForm" rows="4" cols="44" ></html:textarea>
			  	</td>
		  	</tr>
		  	<logic:notEmpty name="mastersForm" property="stage">
		  	<tr>
				<td style="height: 20px;vertical-align: bottom;"class="header"><bean:message key="master_steps.label.disable" /> ?</td>
				<td style="height: 20px;vertical-align: bottom;"><img id="disable" src="" onclick="javascript:confirmDisable(this);" /></td>
			</tr>
			</logic:notEmpty>
	 	</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();">
							<span class="rightC"></span><span class="leftC"></span>
							<bean:message key="common.submit"/>
						</a>
						<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;">
							<span class="rightC"></span><span class="leftC"></span>
							<bean:message key="common.cancel"/>
						</a>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</html:form>	
</div>
</div>


<script type="text/javascript">

selectedCheckBox="images/checkboxchecked.gif";
deselectedCheckBox="images/checkboxunchecked.gif";

function toggleCheckBox(obj) {
	if(obj){
		var src = obj.src;
		if (src.indexOf(deselectedCheckBox) != -1) {
			obj.src = selectedCheckBox;
		} else {
			obj.src = deselectedCheckBox;
		}
	}
}

function confirmDisable(obj) {
	var src = obj.src;
	if (src.indexOf(deselectedCheckBox) != -1) {
		var r=confirm('If you choose OK , this step will be disabled from the step master and cannot be used for any new position . Are you sure you want to continue?');
		if (r==true) {
			toggleCheckBox(obj);
		}
	} else {
		toggleCheckBox(obj);
	}
}

function populateSchedulable() {
	var elem = $('schedulable');
	elem.src = deselectedCheckBox;
	<logic:equal name="mastersForm" property="stepSchedulable" value="<%=StepConstants.TRUE%>">
		elem.src = selectedCheckBox;
	</logic:equal>			
}

function populateDisable() {
	if($('disable')){
		$('disable').src = deselectedCheckBox;
		<logic:equal name="mastersForm" property="stepDisabled" value="<%=StepConstants.TRUE%>">
			$('disable').src = selectedCheckBox;
		</logic:equal>		
	}
}

function submitForm(){
	
	var Name = document.mastersForm.stepName.value;
	if(Name.trim()==""){
		alert('Please enter the Step Name');
		return;
	}
	
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_ADD%>">
		if(selectBoxStage != null && selectBoxStage.getSelectedId() < 0) {
			alert('Please select Stage');
			return;
		}
		document.mastersForm.stepLevel.value = selectBoxStage.getSelectedId();
		document.mastersForm.stepRank.value = selectBoxInsertAfter.getSelectedId();		
		document.mastersForm.mode.value='saveStep';
	</logic:equal>
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_EDIT%>">
		document.mastersForm.mode.value='updateStep';
		var disable = $('disable');	
		document.mastersForm.stepDisabled.value = '<%=StepConstants.FALSE%>';
		if(disable.src.indexOf(selectedCheckBox) > 0)
			document.mastersForm.stepDisabled.value = '<%=StepConstants.TRUE%>';
	</logic:equal>
	
	var schedulable = $('schedulable');	
	document.mastersForm.stepSchedulable.value = '<%=StepConstants.FALSE%>';
	if(schedulable.src.indexOf(selectedCheckBox) > 0)
		document.mastersForm.stepSchedulable.value = '<%=StepConstants.TRUE%>';
	
	document.mastersForm.submit();
}

function populateStages() {
	var pars = "mode=getStageListInXML";
	var myAjax = ajaxCall("step.do","get",pars,onCompleteStageRequest,reportError);
}

function onCompleteStageRequest(request){
	xmlFile = request.responseXML;
	
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))
  		return;
	
	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_steps.error.unable_to_get_stages"/>');
		return;
	}
	var formsArray= xmlFile.getElementsByTagName("forms")[0].firstChild.nodeValue;
	selectBoxStage.reInitialize(eval(formsArray),'-1');
}

function onChangeStage(newIdx){
	var selId = selectBoxStage.getSelectedId();
	if (selId!=null && selId!='-1') {
		var pars = "mode=getStepsInStage&stepLevel=" + selId + "&stepDisabled=0";	
		var myAjax = ajaxCall("step.do",'get',pars,onChangeStageResponse, reportError);	
	}
}

function onChangeStageResponse(request) {
	xmlFile = request.responseXML;
 	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
 	if(isErrorXml(xmlFile)){
		alert('<bean:message key="master_steps.error.change_stage"/>');
		selectStage();
		return;
	}	
 	var formsArray= xmlFile.getElementsByTagName("forms")[0].firstChild.nodeValue;
 	selectBoxInsertAfter.reInitialize(eval(formsArray),'-1');
}

function actionOnLoad(){
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_ADD%>">
		window.top.setPopTitle('<b><bean:message key="master_steps.label.add_step"/></b>');
	</logic:equal>
	<logic:equal name="mastersForm" property="subMode" value="<%=MastersConstants.SUB_MODE_EDIT%>">
		window.top.setPopTitle('<b><bean:message key="master_steps.label.edit_step"/></b>');
	</logic:equal>
	populateStages();
	populateSchedulable();
	populateDisable();
}

window.onload=actionOnLoad;

</script>