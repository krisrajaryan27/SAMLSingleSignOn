<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script src="js/tpSelectListFunctions.js"></script>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script src="js/reports/candidateNamesSpecification.js" type="text/javascript"></script>
<style type="text/css">
.customReport {border:1px solid #99CC33; border-top:none;}
.customReport TD{border-bottom:1px dashed #C4C4C4;padding-left:4px; padding-right:4px; padding-top: 4px; padding-bottom: 4px;}
.customReportOuter TD.head{border-bottom:1px solid #F9FCF3; color:#666666; font-weight: bold; background:#D0E4A3;padding-left:4px; padding-right:4px; padding-top: 4px; padding-bottom: 4px;}
.customReportOuter TD.none{border-bottom:0px;padding: 0px;}
</style>
<s:form action="showCandidateNamesFor" method="POST">
<%@include file="include/commonReportHiddenFields.jspf" %>
<s:set name="stepLevelHire" id="stepLevelHire" value="@com.talentPool.masters.constants.StepConstants@STEP_STAGE_HIRE" />
<s:set name="stepDisabled" id="stepDisabled" value="@com.talentPool.masters.constants.StepConstants@FALSE" />
<div class="contentDiv" >
	<table style="padding: 0px;border: 0;" cellspacing="0" > 
		<tr> 
			<td>
				<div style="width:200px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>
					<s:label key="custom_report.title.names_for" />
				</div>
			</td> 
		</tr> 
	</table>
	<div class="outerDiv">
		<table class="posinput" style="padding: 10px;">	
			<tr>
				<td style="vertical-align: top;" width="140px;">
					<s:label key="custom_report.label.select_attributes" />:
				</td>
				<td>
					<script type="text/javascript">	
			 			var opts = <s:property value="#request.attributesJSArray"  />;
			 			var attrbutesCheckBoxList = new CheckBoxList(opts,'<s:property value="extraCandidateAttributes"  />',{namesonly:false, layerclass:'checkboxlistdiv',width:'210px', size:19, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
						document.write(attrbutesCheckBoxList.getHtml());
						attrbutesCheckBoxList.init();
			 		</script>	
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top;" width="140px;">
					<s:label key="custom_report.label.select_steps" /> :
				</td>
				<td>
					<table cellspacing="0" cellpadding="0" border="0" >
						<tr>
							<td rowspan="2" style="padding: 0px;" >
								<div id="STEPS_GRID"  style="border-bottom: 1px solid #99cc33;width:250px; height: 210px;" ></div>
							</td>
							<td style="padding:0px;padding-bottom:10px; padding-left: 10px;margin-bottom: 10px" >
								<div id="NAMES_GRID"  style="border-bottom: 1px solid #99cc33;width:250px;height: 98px;" ></div>
							</td>
						</tr>
						<tr >
							<td  style="padding:0px;padding-left: 10px;" >
								<div id="NAMES_AND_ATTRIBUTES"  style="border-bottom: 1px solid #99cc33;width:250px;height: 100px;" ></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table style="width: 100%; padding: 10px;">
			
		</table>
	</div>
	<table class="tblPop" style="width: 100%;">
		<tr>
			<td>
				<div class="navBtn" style="float: right;margin-top: 5px;">
					<a href="#" style="width:50px;" class="active" onclick="javascript: previousPage();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.back"/></a>
					<a href="#" style="width:70px; margin-left:5px;" class="active" onclick="javascript: nextPage();"><span class="rightC"></span><span class="leftC"></span><s:text name="common.next"/></a>
					<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: onCancel();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>
</div>	
</s:form>
<script>
var stepsGrid 				= new StepsGrid('STEPS_GRID','<s:text name="custom_report.label.steps_grid_title" />');
var namesGrid 				= new StepsGrid('NAMES_GRID','<s:text name="custom_report.label.candidate_names" />');
var namesAndAttributesGrid 	= new StepsGrid('NAMES_AND_ATTRIBUTES','<s:text name="custom_report.label.candidate_names_and_attributes" />');

function loadStepsGrid(){	
	var stepLevel = '<s:property value="#stepLevelHire"/>';
	var stepDisabled = '<s:property value="#stepDisabled"/>';
	stepsGrid.getGridObj().clearAll();
	var params = '&stepLevel='+stepLevel; 
	params+='&stepDisabled='+stepDisabled;
	stepsGrid.getGridObj().loadXML("step.do?mode=getStepsXMLInStage"+params);	
}

function loadSelectedFields(){
	loadNamesGrid();	
	loadNamesAndAttributesGrid();
}

function loadNamesGrid(){
	selectItems(document.showCandidateNamesFor.stepsToShowNames.value, stepsGrid.getGridObj(), namesGrid.getGridObj());
}

function loadNamesAndAttributesGrid(){
	selectItems(document.showCandidateNamesFor.stepsToShowNamesAndAttributes.value, stepsGrid.getGridObj(), namesAndAttributesGrid.getGridObj());	
}


function nextPage(){
	if(validateSteps()){
		document.showCandidateNamesFor.extraCandidateAttributes.value=attrbutesCheckBoxList.getSelectedIds();
		document.showCandidateNamesFor.stepsToShowNames.value=namesGrid.getGridObj().getAllItemIds(',');
		document.showCandidateNamesFor.stepsToShowNamesAndAttributes.value=namesAndAttributesGrid.getGridObj().getAllItemIds(',');
		document.showCandidateNamesFor.action="selectFilters.action";
		document.showCandidateNamesFor.submit();			
	}
}

function validateSteps(){
	if(attrbutesCheckBoxList.getSelectedIds()=='' && namesAndAttributesGrid.getGridObj().getAllItemIds(',')!=''){
		alert('<s:text name="custom_report.error.select_attributes" />');
		return false;	
	}else if(namesGrid.getGridObj().getAllItemIds(',')=='' && namesAndAttributesGrid.getGridObj().getAllItemIds(',')==''){
		if(confirm('<s:text name="custom_report.confirm.no_step_selected" />')){
			return true;
		}else {
			return false;	
		}
	}else{
		return true;
	}
}

function previousPage(){
	document.showCandidateNamesFor.extraCandidateAttributes.value=attrbutesCheckBoxList.getSelectedIds();
	document.showCandidateNamesFor.stepsToShowNames.value=namesGrid.getGridObj().getAllItemIds(',');
	document.showCandidateNamesFor.stepsToShowNamesAndAttributes.value=namesAndAttributesGrid.getGridObj().getAllItemIds(',');
	document.showCandidateNamesFor.action="customizeReport.action";
	document.showCandidateNamesFor.submit();
}

function onCancel(){
	window.location.href="reports.do?mode=reportFilter&reportName=";
}

Event.observe(window, "load", function() {
	initStepsGrid(stepsGrid);
	initStepsGrid(namesGrid);
	initStepsGrid(namesAndAttributesGrid);
	loadStepsGrid();
});
</script>