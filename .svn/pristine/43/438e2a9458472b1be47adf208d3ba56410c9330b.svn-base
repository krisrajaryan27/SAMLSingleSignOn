<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.salaryStructure.constants.SalaryStructureConstants"%>
<script src="js/scripta/lib/prototype.js"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/commonFunctions.js"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript">
<logic:present name="update" scope="request">
	window.top.reloadWindow();
	window.top.hidePopWin(true);
</logic:present>                
</script>
<logic:notPresent name="update" scope="request">
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
  	<html:hidden property="salaryComponentType" name="salaryStructureForm"/>
  	<html:hidden property="salaryComponentCategoryId" name="salaryStructureForm"/>
  	<html:hidden property="mode" name="salaryStructureForm"/>
	<div class="popupTop">
		<table class="tblPop">
		   <tr>
			  <td class="header">
				  <bean:message key="admin_salary_components_label.name"/>
				  <span class="star">*</span>
			  </td>
			  <td>
				  <html:text property="salaryComponentName" name="salaryStructureForm" size="41" maxlength="49"></html:text>
			  </td>
		  </tr>
		  <tr>
			  <td class="header" valign="top">
				 <bean:message key="admin_salary_components_label.description"/>&nbsp; 
			  </td>
			  <td>
				 <html:textarea property="salaryComponentDescription" name="salaryStructureForm" cols="40" rows="5" ></html:textarea>
		     </td>
		 </tr>
		 <tr>
	  		<td class="header" valign="top">
	  			<bean:message key="master_salary_structure.salaryComponentType" />
	  		</td>
			<td>
			  	<logic:equal name="salaryStructureForm" property="salaryComponentType" value="<%=SalaryStructureConstants.SALARY_PERIOD_MONTHLY%>">
			      	<img src="images/checkedradiobutton.gif" name='imgSalaryComponentType' id='img_<%=SalaryStructureConstants.SALARY_PERIOD_MONTHLY%>' onclick="javascript:onRadioChange('imgSalaryComponentType','<%=SalaryStructureConstants.SALARY_PERIOD_MONTHLY%>');" style="margin-bottom:-1px;"/>
			      	<bean:message key="master_salary_structure.salaryComponentType_monthly" />
					<img src="images/radiobutton.gif" name='imgSalaryComponentType' id='img_<%=SalaryStructureConstants.SALARY_PERIOD_YEARLY%>' onclick="javascript:onRadioChange('imgSalaryComponentType','<%=SalaryStructureConstants.SALARY_PERIOD_YEARLY%>');" style="margin-bottom:-1px;"/>
					<bean:message key="master_salary_structure.salaryComponentType_yearly" />
				 </logic:equal>
				 <logic:notEqual name="salaryStructureForm" property="salaryComponentType" value="<%=SalaryStructureConstants.SALARY_PERIOD_MONTHLY%>">
				  	<img src="images/radiobutton.gif" name='imgSalaryComponentType' id='img_<%=SalaryStructureConstants.SALARY_PERIOD_MONTHLY%>' onclick="javascript:onRadioChange('imgSalaryComponentType','<%=SalaryStructureConstants.SALARY_PERIOD_MONTHLY%>');" style="margin-bottom:-1px;"/>
			      	<bean:message key="master_salary_structure.salaryComponentType_monthly" />
					<img src="images/checkedradiobutton.gif" name='imgSalaryComponentType' id='img_<%=SalaryStructureConstants.SALARY_PERIOD_YEARLY%>' onclick="javascript:onRadioChange('imgSalaryComponentType','<%=SalaryStructureConstants.SALARY_PERIOD_YEARLY%>');" style="margin-bottom:-1px;"/>
					<bean:message key="master_salary_structure.salaryComponentType_yearly" />
				 </logic:notEqual>
		    </td>
		  </tr>
		  <tr>
	  		<td class="header" valign="top">
	  			<bean:message key="master_salary_structure.salaryComponentCategory" />
	  		</td>
			<td>
			  	<script>									
					var salCompCatOpts = <bean:write name="salCompCatJSArray"  scope="request" filter="false" />;
					var salCompCatSelectBox = new SelectBox(salCompCatOpts,'<bean:write name="salaryStructureForm" property="salaryComponentCategoryId" />','images/btn_dropdown.gif',{namesonly:false, width:'250px', size:10, textboxclass:''});
					document.write(salCompCatSelectBox.getHtml());
					salCompCatSelectBox.init();
				</script>
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
var chkedRadBut='images/checkedradiobutton.gif';
var unchkedRadBut='images/radiobutton.gif';
function submitForm(){
	var name = document.salaryStructureForm.salaryComponentName.value;
	if(name.trim()==""){
		alert('<bean:message key="admin_salary_components.error.name" />');
		return;
	}
	validateTextArea();
	document.salaryStructureForm.salaryComponentCategoryId.value=salCompCatSelectBox.getSelectedId();
	document.salaryStructureForm.mode.value="saveSalaryComponent";
	if(document.salaryStructureForm.salaryComponentType.value=='')
		document.salaryStructureForm.salaryComponentType.value=getCheckedSalaryComponentTypeValue();
	document.salaryStructureForm.submit();
}
function actionOnLoad(){
	<logic:notEmpty name="salaryStructureForm" property="salaryComponentId">
		window.top.setPopTitle('<b><bean:message key="admin_salary_components_label.title.edit"/></b>');
	</logic:notEmpty>
	<logic:empty name="salaryStructureForm" property="salaryComponentId">
		window.top.setPopTitle('<b><bean:message key="admin_salary_components_label.title.add"/></b>');
	</logic:empty>
}
function validateTextArea(){
	var salaryComponentDescription = document.salaryStructureForm.salaryComponentDescription.value;
	if(salaryComponentDescription.lenght>248){
		alert('<bean:message key="admin_salary_components.error.description" />');
		return false;
	}
}
function onRadioChange(imgGroupName, salaryComponentType){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img") > -1) {
			if( theImage.id == 'img_'+salaryComponentType){
				theImage.src = chkedRadBut;
				document.salaryStructureForm.salaryComponentType.value=salaryComponentType;
			}else{
				theImage.src = unchkedRadBut;
			}
		}
	}
}
function getCheckedSalaryComponentTypeValue(){
	var imgs = document.getElementsByName('imgSalaryComponentType');
	var salaryComponentType='<%=SalaryStructureConstants.SALARY_PERIOD_YEARLY%>';
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if(theImage.src==chkedRadBut){
			if(theImage.id.indexOf("img_") > -1){
				salaryComponentType=theImage.id.substring(theImage.id.indexOf("img_"),theImage.id.length);
			}
		} 
	}
	return salaryComponentType;
}

window.onload=actionOnLoad;
</script>
</logic:notPresent>