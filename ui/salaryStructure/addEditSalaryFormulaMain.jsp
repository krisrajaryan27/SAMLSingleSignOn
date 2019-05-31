<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.salaryStructure.constants.SalaryStructureConstants"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.salaryStructure.utils.SalaryStructureUtils"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script src="js/ajaxfunctions.js"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.hidePopWin(true);
</logic:present>       
var gradeSelectBox;
var gradeOpts;
var salaryComponentBox;
var salaryComponentOpts;
var selectOpt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
</script>
<div class="contentDivPop" style="width:500px;">
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
			</table><br/><br/>
	<%
		}
	%> 
	<div class="outerDiv">
	<html:form action="/salaryStructure" onsubmit="submitForm();return false;">
  	<html:hidden property="salaryComponentId" name="salaryStructureForm"/>
  	<html:hidden property="mode" name="salaryStructureForm"/>
  	<html:hidden property="gradeId" name="salaryStructureForm"/>
  	<html:hidden property="variable1" name="salaryStructureForm"/>
  	<html:hidden property="isAdjustable" name="salaryStructureForm"/>
  	<html:hidden property="formulaId" name="salaryStructureForm"/>
  	<html:hidden property="roundingType" name="salaryStructureForm"/>
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header" valign="top">
				  <%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %>
				  <span class="star">*</span>
			  </td>
			  <td>
				 <logic:notEqual name="salaryStructureForm" property="formulaId" value="0">
					<bean:write name="gradeName" scope="request"  />
				 </logic:notEqual>
				 <logic:equal name="salaryStructureForm" property="formulaId" value="0">
				 	<script language="JavaScript">									
						gradeOpts = <bean:write name="jsArrayGrades" filter="false" scope="request"/>;											
						gradeOpts = selectOpt.concat(gradeOpts);
						gradeSelectBox = new SelectBox(gradeOpts,'<bean:write name="salaryStructureForm" property="gradeId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
						gradeSelectBox.setOnChangeHandler('onChangeGrade');
						document.write(gradeSelectBox.getHtml());
						gradeSelectBox.init();
					</script>
				</logic:equal>
			  </td>
		  </tr>
  		  <tr>
			  <td class="header" valign="top">
				  <bean:message key="salary_component_label" /> 
				  <span class="star">*</span>
			  </td>
			  <td>
			     <logic:notEqual name="salaryStructureForm" property="formulaId" value="0">
			     		<bean:write name="salaryComponentName" scope="request"  />
			 	 </logic:notEqual>
				 <logic:equal name="salaryStructureForm" property="formulaId" value="0">
					<script language="JavaScript">		
						salaryComponentOpts = <bean:write name="jsArraySalaryComponents" filter="false" scope="request"/>;
						salaryComponentOpts = selectOpt.concat(salaryComponentOpts); 		
						salaryComponentBox = new SelectBox(salaryComponentOpts,'<bean:write name="salaryStructureForm" property="salaryComponentId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
						document.write(salaryComponentBox.getHtml());
						salaryComponentBox.init();
					</script>
				</logic:equal>
			  </td>
		  </tr>
		  <tr>
		  	<td class="header" valign="top">
		  		<bean:message key="master_salary_components_label.formula" />
		  	</td>
		  	<td>
		  		<table>	
		  			<tr>
		  				<td>
		  					<html:text property="variable1Factor"  name="salaryStructureForm" size="7" styleId="skillCategory" onblur="javascript:validateDecimal(this)"></html:text>
		  				</td>
		  				<td>
		  					x&nbsp;
		  				</td>
		  				<td>
			  				<script language="JavaScript">		
								salaryVariablesOpts = <bean:write name="jsArraySalaryVariables" filter="false" scope="request"/>;
								salaryVariablesBox = new SelectBox(salaryVariablesOpts,'<bean:write name="salaryStructureForm" property="variable1" />','images/btn_dropdown.gif',{namesonly:false, width:'90px', size:15, textboxclass:'Grey'});
								document.write(salaryVariablesBox.getHtml());
								salaryVariablesBox.init();
							</script>
						</td>
		  				<td>
		  					&nbsp;+ 
		  				</td>
		  				<td>
		  					<html:text property="constantFactor"  name="salaryStructureForm" size="13" styleId="skillCategory" onblur="javascript:validateDecimal(this)"></html:text>
		  				</td>
		  			</tr>
		  		</table>
		  	</td>
		  </tr>
		  <tr> 
		  	<td class="header" valign="top">
		  		<bean:message key="master_salary_structure.max_limit" />
		  	</td>
		    <td>
		   		<html:text property="maxLimit"  name="salaryStructureForm" size="13" styleId="skillCategory" onblur="javascript:validateInteger(this);"></html:text>
	       	</td>
		  </tr>
		   <tr>
			  <td class="header" valign="top">
				  <bean:message key="master_salary_structure.component_rounding" /> 
				  <span class="star">*</span>
			  </td>
			  <td>
				<script language="JavaScript">		
					salaryComponentRoundingOpts = <bean:write name="jsArrayRoundingOptions" filter="false" scope="request"/>;
					salaryComponentRoundingBox = new SelectBox(salaryComponentRoundingOpts,'<bean:write name="salaryStructureForm" property="roundingType" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
					document.write(salaryComponentRoundingBox.getHtml());
					salaryComponentRoundingBox.init();
				</script>
			  </td>
		  </tr>
		  <tr>
		  	<td class="header" valign="top">
		  		<bean:message key="master_salary_components_label.is_adjustable" />
		  	</td>
		  	<td>
		  		<div id="isAdjustableEnabledDiv">
			  		<logic:equal name="salaryStructureForm" property="isAdjustable" value="<%=SalaryStructureConstants.ADJUSTABLE_COMPONENT%>">
				  		<img src="images/checkboxchecked.gif" id="IMG_isAdjustable" name="IMG_isAdjustable" 
			  							onclick="adjustableCheckBoxChange(this);" />
		  			</logic:equal>
				  	<logic:notEqual name="salaryStructureForm" property="isAdjustable" value="<%=SalaryStructureConstants.ADJUSTABLE_COMPONENT%>">
			  			<img src="images/checkboxunchecked.gif" id="IMG_isAdjustable" name="IMG_isAdjustable" 
		  							onclick="adjustableCheckBoxChange(this);" />
		  			</logic:notEqual>
	  			</div>
	  			<div id="isAdjustableDisabledDiv" style="display: none;">
	  				<img src="images/item_chk0_dis.gif" />
	  				<bean:message key="master_salary_structure.note.adjustable_component_exists" />
	  			</div>
		  	</td>
		  </tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
		<tr>
			<td>
			<div class="navBtn" style="float: right;">
			<a href="#" style="width:60px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
			<a href="#" style="width:60px; margin-left:5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
			</td>
		</tr>
		</table>
	</div>
	</html:form>	
</div>
</div>
<script type="text/javascript">
var chkedChkBox='images/checkboxchecked.gif';
var unchkedChkBox='images/checkboxunchecked.gif';
var disabledCheckBox =  'images/item_chk0_dis.gif';
var isEditPage = false;
<logic:notEqual name="salaryStructureForm" property="formulaId" value="0">
	isEditPage = true;
</logic:notEqual>
<logic:equal name="salaryStructureForm" property="formulaId" value="0">
	isEditPage = false;;
</logic:equal>
function submitForm(){
	if(formIsValid()){
		document.salaryStructureForm.mode.value="saveSalaryFormula";
		if(!isEditPage){
			document.salaryStructureForm.gradeId.value=gradeSelectBox.getSelectedId();
			document.salaryStructureForm.salaryComponentId.value=salaryComponentBox.getSelectedId();
		}
		document.salaryStructureForm.variable1.value=salaryVariablesBox.getSelectedId();
		document.salaryStructureForm.roundingType.value =salaryComponentRoundingBox.getSelectedId(); 
		setAdjustableComponentValue();
		document.salaryStructureForm.submit();
	}
}
function adjustableCheckBoxChange(obj){
	if(obj.src.indexOf(chkedChkBox) != -1) {
		obj.src = unchkedChkBox;
		document.salaryStructureForm.isAdjustable.value='<%=SalaryStructureConstants.NON_ADJUSTABLE_COMPONENT%>';
	} else {
		obj.src = chkedChkBox;
		document.salaryStructureForm.isAdjustable.value='<%=SalaryStructureConstants.ADJUSTABLE_COMPONENT%>';
	}
}
function setAdjustableComponentValue(){
	if($('isAdjustableDisabledDiv').style.display=='')
		document.salaryStructureForm.isAdjustable.value='<%=SalaryStructureConstants.NON_ADJUSTABLE_COMPONENT%>';
	else if(document.salaryStructureForm.isAdjustable.value=='')
		document.salaryStructureForm.isAdjustable.value='<%=SalaryStructureConstants.NON_ADJUSTABLE_COMPONENT%>';
}
function onChangeGrade(){
	var pars="mode=getSalaryComponentsForaGrade";
	pars+="&gradeId="+gradeSelectBox.getSelectedId();
	var myAjax = ajaxCall("salaryStructure.do","get",pars,populateSalaryComponents,reportError);
}
function populateSalaryComponents(response){
	var xmlFile = response.responseXML;
	var hasAdjustableComponent = '';
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		salaryComponentOpts = response.responseText;
		hasAdjustableComponent = salaryComponentOpts.substring(0,salaryComponentOpts.indexOf("|"));
		salaryComponentOpts = eval(salaryComponentOpts.substring(salaryComponentOpts.indexOf("|")+1));
		salaryComponentOpts = selectOpt.concat(salaryComponentOpts);
		salaryComponentBox.reInitialize(salaryComponentOpts);
		if(hasAdjustableComponent!='0' && hasAdjustableComponent!=0){
			$('isAdjustableDisabledDiv').show();
			$('isAdjustableEnabledDiv').hide();
		}else {
			$('isAdjustableDisabledDiv').hide();
			$('isAdjustableEnabledDiv').show();
		}
	}else{
		//alert('_c');
	}
	return false;
}
function formIsValid(){
	if(!isEditPage){
		if(gradeSelectBox.getSelectedId()==-1){
			alert('<bean:message key="master_salary_structure.error.fields_mandatory" />');
			return false;
		}else if(salaryComponentBox.getSelectedId()==-1){
			alert('<bean:message key="master_salary_structure.error.fields_mandatory" />');
			return false;
		}
	}
	return true;
}
function validateDecimal(obj){
	var val = obj.value;
	if(isNaN(val)){
		alert('<bean:message key="common.please_enter_valid_number" />');
		setTimeout(function(){obj.focus();obj.select();},10)
	}
}
function validateInteger(obj){
	var val = obj.value;
	if(!isInteger(val) || parseInt(val)<0){
		alert('<bean:message key="common.please_enter_valid_positive_integer" />');
		setTimeout(function(){obj.focus();obj.select();},10)
	}
}
function isInteger(n) {
    return (/^-?\d+$/.test(n+''));
}
function actionOnLoad(){
	<logic:notEqual name="salaryStructureForm" property="formulaId" value="0">
		window.top.setPopTitle('<b><bean:message key="master_salary_components_label.title.edit"/></b>');
	</logic:notEqual>
	<logic:equal name="salaryStructureForm" property="formulaId" value="0">
		window.top.setPopTitle('<b><bean:message key="master_salary_components_label.title.add"/></b>');
	</logic:equal>
	enableOrDisableIsAdjustableDiv();
}
function enableOrDisableIsAdjustableDiv(){
	<logic:notEqual name="salaryStructureForm" property="isAdjustable" value="1">
		<logic:notEqual name="hasAdjustableComponent" value="0">
			$('isAdjustableDisabledDiv').show();
			$('isAdjustableEnabledDiv').hide();
		</logic:notEqual>
	</logic:notEqual>
}
window.onload=actionOnLoad;
</script>