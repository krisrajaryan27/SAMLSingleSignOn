<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<script src="js/scripta/lib/prototype.js" ></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" ></script>
<script src="js/selectoption.js" ></script>
<script src="js/selectbox/selectbox.js" ></script>	
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	.divPrintOptions{
	 width: 300px;  
	 border: 2px solid #666; 
	 background-color: #fff; 
	 padding: 10px;
	 border-top: none;
	 border-right: none;
	}
	.divPrintOptions th{
	color:black;
	 border-bottom: solid 1px;
	 padding-right:2px;
	}
</style>
</head>
<script type="text/javascript">
var proposalActionBox;
</script>
<body>
<s:set name="applicantJoinedFlag" value="@com.talentPool.selectionProcess.SelectionProcessConstants@APPLICANT_JOINED" />
<s:form name="offerProposal" action="saveCTCComparisonGrid" method="POST"  >
	<s:hidden name="applicantId" />
	<s:hidden name="salCatsExistigValJson" id="salCatsExistigValJson" />
	<s:hidden name="offerProposalAction" id="offerProposalAction" />
	<div class="contentDivPop" style="width:755px;">
		<%@include file="../../common/errordiv.jspf" %>
		<div class="bottomDivSec">
		<div class="vpTop" >
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
	          <tr>
	            <td>
	            	<strong id="VP_TITLE" class="Grey">
	            		<s:property value="applicantData.applicantName"  />
	   				</strong>
	   			</td>
	          </tr>
	        </table>
		</div>
		</div>
		<div class="outerDiv">
			<div class="popupTop">
				<table class="tblPop" width="100%">
					<tr>
				         <td class="header" width="18%"><s:label key="common.position" />:</td>
				         <td width="32%"><s:property value="applicantData.applicantPositionTitle" /></td>
				         <td class="header" width="14%" ><s:label key="resume_summary.label.location" />&nbsp;</td>
				         <td width="36%"><s:property value="applicantData.applicantCity" /></td>
				    </tr>
				    <tr>
				         <td class="header" width="18%" valign="top"><s:label key="resume_summary.label.totalExperience" /></td>
				         <td width="30%" valign="top"><s:property value="applicantData.applicantExperience" /></td>
				         <td class="header" width="14%" valign="top"><s:label key="resume_summary.label.education" />:&nbsp;</td>
				         <td width="30%" valign="top" title="<s:property value="applicantData.fullEducationalDetailsFormatted" escapeHtml="false"   />"><s:property value="applicantData.getEducationalDetailsFormatted(2,40)" escapeHtml="false"  /></td>
				    </tr>
				    <tr>
				         <td class="header" width="18%"><s:label key="resume_summary.label.employer" /></td>
				         <td width="30%"><s:property value="applicantData.applicantCurrentEmployer" /></td>
				         <td class="header" width="14%"><s:label key="resume_summary.label.joining_date" />:&nbsp;</td>
				         <td width="30%"><s:property value="applicantData.applicantDateJoinedToDisplay" /></td>
				    </tr>
				    <tr>
				         <td class="header" width="18%"><s:label key="resume_summary.label.current_ctc" /></td>
				         <td width="30%"><s:property value="applicantData.currentCTC" /></td>
				         <td class="header" width="14%"><s:label key="resume_summary.label.expected_ctc" />&nbsp;</td>
				         <td width="30%"><s:property value="applicantData.expectedCTC" /></td>
				    </tr>
			    </table>
		    </div>
		    <s:if test="#request['ctc_comparison_warning']==null">
			    <div class="popupBody" style="border-bottom:1px dotted #999999;">
			    	<table>
			    		<tr>
			    			<td><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)%></td>
					         <td>
					         	<s:if test="offeredInputSalaryVariableEditable==true">
									<s:textfield key="offeredInputSalaryVariable" cssClass="proposedVal"  id="inputSalaryVariable" />				         		
					         	</s:if>
					         	<s:else>
					         		<s:textfield key="offeredInputSalaryVariable" cssClass="proposedVal Grey" disabled="true" id="inputSalaryVariable" />
					         	</s:else>
					         </td>
					         <s:if test="previewSalary==true">
								<td>
					         		<span id="waitToCalculate" style="width: 70px;">
					         			<a href="#" style="width:60px; margin-left: 5px;text-decoration:none;" class="active green" onclick="javascript: calculateSalary();return false;"><s:text name="selection_feedback.link.preview" /></a>
					         		</span>
					         	</td>						         		
				         	</s:if>
				         	<s:else>
				         		<td>&nbsp;</td>
				         	</s:else>
			    		</tr>
			    	</table>
			    </div>
	    	</s:if>
		    <div class="popupBody" style="height: 200px;overflow: auto;" >	
			    <table class="tblPop" width="100%" id="comparisonGrid">
					<tr>
				         <td class="header" width="20%"><s:label key="ctc_comparison_screen.header.comparison_grid_details" /></td>
				         <td class="header" width="20%"><s:label key="ctc_comparison_screen.header.comparison_grid_existing" /></td>
				         <td class="header" width="20%">
				         	<s:label key="ctc_comparison_screen.header.comparison_grid_proposed" />
				         	<s:if test="#request['ctc_comparison_warning']!=null">
					    		<strong>*</strong>
					    	</s:if>
				         </td>
				         <td class="header" width="40%"><s:label key="ctc_comparison_screen.header.comparison_grid_hike" /></td>
				    </tr>
				    <tr>
				         <td><s:text name="ctc_comparison_screen.text.comparison_grid_designation" /></td>
				         <td><s:textfield key="currentDesignation" /></td>
				         <td>
				          	<s:if test="offeredDesignationEditable==true">
								<s:textfield key="offeredDesignation" />				         		
				         	</s:if>
				         	<s:else>
				         		<s:textfield key="offeredDesignation" cssClass="Grey" disabled="true" />
				         	</s:else>
				         </td>
				         <td><s:label cssClass="hike" value="-" /></td>
				    </tr>
				    <tr>
				         <td><s:text name="ctc_comparison_screen.text.comparison_grid_level" /></td>
				         <td><s:textfield key="currentLevel" /></td>
				         <td>
				         	<s:if test="offeredLevelEditable==true">
								<s:textfield key="offeredLevel" />				         		
				         	</s:if>
				         	<s:else>
				         		<s:textfield key="offeredLevel" cssClass="Grey" disabled="true" />
				         	</s:else>
				         </td>
				         <td><s:label cssClass="hike" value="-" /></td>
				    </tr>
					<tr>
				         <td><s:text name="ctc_comparison_screen.text.comparison_grid_ctc" /></td>
				         <td>
				         	<s:if test="currentCTCEditable==true">
								<s:textfield key="currentCTC" cssClass="existingVal" />				         		
				         	</s:if>
				         	<s:else>
				         		<s:textfield key="currentCTC" cssClass="existingVal Grey" disabled="true" />
				         	</s:else>
				         </td>
				         <td>
				         	<s:if test="offeredCTCEditable==true">
								<s:textfield key="offeredCTC" cssClass="proposedVal" />				         		
				         	</s:if>
				         	<s:else>
				         		<s:textfield key="offeredCTC" cssClass="proposedVal Grey" disabled="true" />
				         	</s:else>
				         </td>
				         <td><s:label cssClass="hike" value="-" /></td>
				    </tr>
				    <tr>
				    	 <td><s:text name="ctc_comparison_screen.text.comparison_grid_basic" /></td>
				    	 <td><s:textfield key="currentBasic" cssClass="existingVal" /></td>
 				         <td>
				         	<s:if test="offeredBasicEditable==true">
								<s:textfield key="offeredBasic" cssClass="proposedVal" id="offeredBasic" />				         		
				         	</s:if>
				         	<s:else>
				         		<s:textfield key="offeredBasic" cssClass="proposedVal Grey" disabled="true" />
				         	</s:else>
				         </td>
				         <td><s:label cssClass="hike" value="-" /></td>
				    </tr>
				    <s:iterator value="salCatCompData">
				      	<tr id="<s:property value="categoryId" />" >
						 <td><s:property value="categoryName" /></td>
				    	 <td><s:textfield key="existingVal" cssClass="existingVal existinCatTotVal"  /></td>
				         <td><s:textfield key="proposedVal" cssClass="proposedVal proposedCatTotVal" disabled="true" /></td>
				         <td><s:label cssClass="hike" value="-" /></td>
				        </tr>  
					</s:iterator>
			    </table>
		    </div>
		    <div class="popupTop" style="border-top:1px dotted #999999;">
		    	<s:if test="#request['ctc_comparison_warning']!=null">
		    		<span>* <s:property value="#request['ctc_comparison_warning']" /></span>
		    	</s:if>
		    	 <s:else>
		    		<table class="tblPop">
				    	<tr>
				    		<td><s:text name="offer_proposal.label.offer_proposal_action" />:</td>
				    		<td>
					    		<script language="JavaScript">									
									var proposalActionOpts = <s:property value="#request['proposalActionJSArray']" />;											
									proposalActionBox = new SelectBox(proposalActionOpts,'','images/btn_dropdown.gif',{namesonly:false, width:'200px', size:15, textboxclass:'Grey'});
									document.write(proposalActionBox.getHtml());
									proposalActionBox.init();
								</script>
				    		</td>
				    	</tr>
				    </table>
		    	</s:else>
		    </div>
		    <div class="navBtn" style="float: right;"><br/>
		    	<s:if test="applicantData.applicantJoined!=#applicantJoinedFlag">
		    		<a href="#" style="width:60px; margin-right:5px;" class="active" onclick="javascript: saveCTCComparisonGridValues();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.save" /></a>
		    	</s:if>
				<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel" /></a>
			</div>
		</div>
	</div>
	<div id="divPrintOptions" class="divPrintOptions" style="top:0;right:0; position: absolute; display: none; "></div>
	<BR/>
</s:form>
</body>
<script>
var enabled 					= <%=GlobalConstants.ENABLED%>;
var validateCtcAsNumeric		= <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC)%>;
var inputSalaryVariableLabel 	= '<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL)%>';
Event.observe(window, "load", function() {	
	window.top.setPopTitle('<b><s:text name="offer_proposal.title.offer_proposal" /></b>');
	$$('#comparisonGrid input.existingVal').each(function(node) {
		updateHike(node);
		Event.observe(node, "keyup", updateHikeEvent.bindAsEventListener(this)); 
	});
	$$('#comparisonGrid input.proposedVal').each(function(node) {
		Event.observe(node, "keyup", updateHikeEvent.bindAsEventListener(this)); 
	});
});

function calculateSalary(){
	var offeredCTC = document.offerProposal.offeredCTC?document.offerProposal.offeredCTC.value:'';
	var offeredBasic = document.offerProposal.offeredBasic?document.offerProposal.offeredBasic.value:'';
	var inputSalaryVariable = $('inputSalaryVariable')?$('inputSalaryVariable').value:'';
	var gradeId = '<s:property value="gradeId" />';
	showUpdater('waitToCalculate',{setHeight: false, setWidth: false, offsetLeft: 0});
	var pars = "mode=previewSalaryStructure&gradeId=" + gradeId+"&ctcOffered="+offeredCTC+"&basicOffered="+offeredBasic+"&inputSalaryVariable="+inputSalaryVariable;
	var myAjax = ajaxCall("selectionProcess.do",'get',pars,onPreviewSalary, reportError);	
}

function onPreviewSalary(request){	
	  if (request != null) {
	    xmlFile = request.responseText;		   
	    var div = document.getElementById('divPrintOptions');		    
	    div.innerHTML = xmlFile.toString();		    
	  }
	Effect.BlindDown('divPrintOptions',{duration:0.5});
	hideUpdater('waitToCalculate');
}

function updateSalary(basic, annnualTotal){
	var offeredCTC = document.offerProposal.offeredCTC;
	var offeredBasic = document.offerProposal.offeredBasic;
	if((offeredCTC && offeredCTC.value!=null) || (offeredBasic && offeredBasic.value!=null)){
		if(confirm('<s:text name="selection_feedback.error.override_basic_and_target_ctc"/>')) {
			if(offeredCTC)
				offeredCTC.value=annnualTotal;
			if(offeredBasic)
				offeredBasic.value=basic;
		}else{
			return false;
		}
	}else {
		if(offeredCTC)
			offeredCTC.value=annnualTotal;
		if(offeredBasic)
			offeredBasic.value=basic;
	}
	
	$$('#comparisonGrid input.proposedCatTotVal').each(function(node) {
		if(node.up('tr').id){
			var categoryId 	= node.up('tr').id;
			if($('annual_cat_total_'+categoryId)){
				node.value=$('annual_cat_total_'+categoryId).innerHTML;
				updateHike(node);
			}
		}
	});
}

function hidePreviewSalary(){
	Effect.BlindUp('divPrintOptions',{duration:0.5});
}

function updateHikeEvent(event){
	var node 	= Event.element(event);
	updateHike(node);
}

function updateHike(node){
	var hike 	= '';
	var tr 		= node.up('tr');
	var hikeNode = tr.down('label.hike');
	var existingVal = tr.down('input.existingVal').value;
	var proposedVal = tr.down('input.proposedVal').value;
	if(isNaN(existingVal) || isNaN(proposedVal)){
		hike='Existing or Proposed value is not valid.';
	}else if(existingVal==0){
		hike='-'
	}else {
		hike=calculatePercIncreased(existingVal, proposedVal);
		if(isNaN(hike))
			hike = '-';
		else
			hike=Math.round(hike*100)/100+'%'; 
	}
	hikeNode.update(hike);
}

function calculatePercIncreased(oldVal, newVal){
	return ((newVal-oldVal)/oldVal)*100;	
}

function saveCTCComparisonGridValues(){
	if(isFormValid()){
		<s:if test="#request['ctc_comparison_warning']!=null">
			alert('<s:property value="#request['ctc_comparison_warning']" />');
		</s:if>
		var salCatsExistigValJson = createExistingValuesJson();
		if(proposalActionBox)
			document.offerProposal.offerProposalAction.value=proposalActionBox.getSelectedId();
		$('salCatsExistigValJson').value = salCatsExistigValJson;
		setNullForNonEditableFields(); 
		document.offerProposal.submit();
	}
}

function isFormValid(){
	if(isNaN(document.offerProposal.currentBasic.value)){
		alert('<s:text name="ctc_comparison_screen.error.basic_numeric" />');
		document.offerProposal.currentBasic.focus();
		return false;
	}
	if(enabled==validateCtcAsNumeric){
	    var elem3 = document.offerProposal.offeredCTC;		 
	    if(elem3 && elem3.disabled==false){
		    var ctcOffered = document.offerProposal.offeredCTC.value;
		    if(ctcOffered!= ''){
			    if(isNaN(ctcOffered)){
			    	alert('<s:text name='selection_feedback.error.please_enter_ctc_number' />');
			    	return false;
				}else if(ctcOffered<0){
					alert('<s:text name='selection_feedback.error.please_enter_ctc_number' />');
			    	return false;
				}
		    }
	    }
	    var elem4 = document.offerProposal.offeredBasic;		
	    if(elem4 && elem4.disabled==false){
		    var basicOffered = document.offerProposal.offeredBasic.value;
		    if(basicOffered!= ''){
			    if(isNaN(basicOffered)){
			    	alert('<s:text name="selection_feedback.error.please_enter_basic_number" />');
			    	return false;
				}else if(basicOffered<0){
					alert('<s:text name="selection_feedback.error.please_enter_basic_number" />');
			    	return false;
				}
		    }
	    }
	}
	if($('inputSalaryVariable') && (!isInt($('inputSalaryVariable').value) || $('inputSalaryVariable').value<0)){
    	alert('<s:text name="selection_feedback.error.please_enter_input_salary_variable_number_as"  />' +' '+ inputSalaryVariableLabel);
    	return false;
    }
	var valid = true;
	$$('#comparisonGrid input.existinCatTotVal').each(function(node) {
		if(isNaN(node.value)){
			var salCatName = node.up('td').previous(0).innerHTML; 
			alert('Please enter a valid numeric '+salCatName+' value.');
			valid = false;
			throw $break;
		}
	});
	if(valid)
		return true;
	else
		return false;
	
	return true;
}

function isInt(n) {
   return (!isNaN(n) && n % 1 == 0);
}

function createExistingValuesJson(){
	var existingValsMap = $H();
	$$('#comparisonGrid input.existingVal').each(function(node) {
		if(node.up('tr').id){
			var categoryId 	= node.up('tr').id;
			var existingVal = node.value;
			existingValsMap.set(categoryId, existingVal);
		}
	});
	return Object.toJSON(existingValsMap);	
}

function setNullForNonEditableFields(){
	$$('#comparisonGrid input.proposedVal').each(function(node) {
		if(node.disabled==true){
			node.value=null;
		}
	});
}

</script>
</html>