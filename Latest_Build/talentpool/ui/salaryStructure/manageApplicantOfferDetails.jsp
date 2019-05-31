<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>  
<SCRIPT LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>    
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
<s:form action="manageApplicantOfferDetails" method="POST">
<div class="contentDivPop" style="width: 550px;">
	<s:hidden name="applicantId" />
	<s:hidden name="modifyOffer" />
	<s:hidden name="offerSheetTemplateId" />
	<div class="vpTop" style="margin-bottom: 1px;" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
	      <tr>
	        <td>
	        	<strong id="VP_TITLE" class="Grey">
	            		<s:property value="feedbackData.applicantName"  />
	   			</strong>
	 		</td>
	      </tr>
	    </table>
	</div>
	<div class="outerDiv" style="border-top:1px;">
		<div class="popupTop">
			<table class="tblPop" >
				<tr>
		         <td class="header"><s:text name="common.position" /></td>
		         <td><s:property value="feedbackData.positionTitle"/></td>
		     	</tr>
		     	<tr>
		         <td class="header"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL)%></td>
		         <td><s:property value="feedbackData.gradeName"/></td>
		     	</tr>
		    </table>
		</div>
		<div class="popupBody">
			<table class="tblPop" width="100%">
				<tr>
					<td>
						<table>
		         			<tr>
				         		 <td>
		         					<s:text name="selection_feedback.label.level_offered"/>
		         				</td>
			         			<td>
			         				<s:textfield name="appOfferDetails.offeredLevel" value="%{feedbackData.levelOffered}"   />
			         			</td>
		         				<td style="width: 20px;">&nbsp;</td>      			
								<td>
			         				<s:text name="selection_feedback.label.designation_offered"/>
			         			</td>
			         			<td>
			         				<s:textfield name="appOfferDetails.offeredDesignation" value="%{feedbackData.designationOffered}"  />		         				
			         			</td>		         				
			         		</tr>
		         			<tr>
			         			<td>
		         					<s:text name="selection_feedback.label.ctc_offered"/>
		         				</td>
			         			<td>
			         				<s:textfield name="appOfferDetails.offeredCTC" id="ctcOffered" value="%{feedbackData.ctcOffered}"  />
			         			</td>
			         			<td>&nbsp;</td>
			         			<td>
			         				<s:text name="selection_feedback.label.basic_offered"/>
			         			</td>
			         			<td>
			         				<s:textfield name="appOfferDetails.offeredBasic" id="basicOffered" value="%{feedbackData.basicOffered}"  />
			         			</td>
		         			</tr>
		         			<tr>
		         				<td>
			         				<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL) %>
			         			</td>
			         			<td>
			         				<s:textfield name="appOfferDetails.inputSalaryVariable" id="inputSalaryVariable" value="%{feedbackData.inputSalaryVariable}"  />
			         			</td>
		         				<s:if test="previewSalary==true">
		         				<td>
					         		<span id="waitToCalculate" style="width: 70px;">
					         			<a href="#" style="width:60px; margin-left: 5px;text-decoration:none;" class="active green" onclick="javascript: calculateSalary();return false;"><s:text name="selection_feedback.link.preview" /></a>
					         		</span>
					         	</td>		
			         			</s:if>
			         			<s:else>
			         				<td colspan="3">&nbsp;</td>
			         			</s:else>
		         			</tr>
         				</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="popupBody">
			<table>
				<tr>
					<td>	
						<s:text name="generate_offer_sheet.label.select_offer_sheet_template" />:
					</td>
					<td>	
						<script type="text/javascript">
							var def = [new SelectOption('-1','<s:text name="common.selectlist.select" />')];
							var opts = <s:property value="jsArrayOfferSheetTemplates" />;
							opts = def.concat(opts);					
							selectOfferSheetTemplate = new SelectBox(opts,'<s:property value="offerSheetTemplateId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:15});
							document.write(selectOfferSheetTemplate.getHtml());
							selectOfferSheetTemplate.init();
						</script>			
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="navBtn" style="float: right;margin-top: 10px;">
		<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: nextAction();return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.next"/></a>
		<a href="#" style="width:60px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.cancel"/></a>
	</div>
	<div id="divPrintOptions" class="divPrintOptions" style="top:0;right:0; position: absolute; display: none; "></div>
</div>
</s:form>	
<br/>	
<DIV id="calDiv" style="position:absolute;z-index:10000;background-color:#F3F9DC;" ></DIV>
<script>
var enabled = <%=GlobalConstants.ENABLED%>;
var validateCtc = <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VALIDATE_CTC_AS_NUMERIC)%>;
var inputSalaryVariableLabel = '<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL) %>';

var gradeId = '<s:property value="feedbackData.gradeId" />';

function calculateSalary(){
	showUpdater('waitToCalculate',{setHeight: false, setWidth: false, offsetLeft: 0});
	var pars = "mode=previewSalaryStructure&gradeId=" + gradeId+"&ctcOffered="+$('ctcOffered').value+"&basicOffered="+$('basicOffered').value+"&inputSalaryVariable="+$('inputSalaryVariable').value;
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
	if(($('ctcOffered') && $('ctcOffered').value!=null) || ($('basicOffered') && $('basicOffered').value!=null)){
		if(confirm('<s:text name="selection_feedback.error.override_basic_and_target_ctc"/>')) {
			if($('ctcOffered'))
				$('ctcOffered').value=annnualTotal;
			if($('basicOffered'))
				$('basicOffered').value=basic;
		}else{
			return false;
		}
	}else {
		if($('ctcOffered'))
			$('ctcOffered').value=annnualTotal;
		if($('basicOffered'))
			$('basicOffered').value=basic;
	}
}

function hidePreviewSalary(){
	Effect.BlindUp('divPrintOptions',{duration:0.5});
}

function nextAction(){
	if(isFormValid()){
		document.manageApplicantOfferDetails.action="offerTemplateVariableMapping.action";
		document.manageApplicantOfferDetails.submit();
	}
}

function isFormValid(){
	var elem3 = $('ctcOffered');		 
    if(elem3 && enabled == validateCtc){
	    var ctcOffered = $('ctcOffered').value;
	    if(ctcOffered!= ''){
		    if(isNaN(ctcOffered)){
		    	alert('<s:text name="selection_feedback.error.please_enter_ctc_number" />');
		    	return false;
			}else if(ctcOffered<0){
				alert("<s:text name='selection_feedback.error.please_enter_ctc_number' />");
		    	return false;
			}
	    }
    }
    var elem4 = $('basicOffered');
    if(elem4 && enabled == validateCtc){
	    var basicOffered = $('basicOffered').value;
	    if(basicOffered!= ''){
		    if(isNaN(basicOffered)){
		    	alert('<s:text name='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}else if(basicOffered<0){
				alert('<s:text name='selection_feedback.error.please_enter_basic_number' />');
		    	return false;
			}
	    }
    }

    if($('inputSalaryVariable') && (!isInt($('inputSalaryVariable').value) || $('inputSalaryVariable').value<0)){
    	alert('<s:text name="selection_feedback.error.please_enter_input_salary_variable_number_as"  />' +' '+ inputSalaryVariableLabel);
    	return false;
    }

    var templateId = selectOfferSheetTemplate.getSelectedId();
    if(templateId != '-1') {
    	document.manageApplicantOfferDetails.offerSheetTemplateId.value=templateId;        
    }else{
    	alert('<s:text name="generate_offer_sheet.error.please_select_template" />');
		return false;
    }
	return true;	
}

function isInt(n) {
   return (!isNaN(n) && n % 1 == 0);
}

Event.observe(window, "load", function() {	
	window.top.setPopTitle('<s:text name="generate_offer_sheet.label.generate_offer" />');
	window.top.resizePopUp(600,300);
});
</script>