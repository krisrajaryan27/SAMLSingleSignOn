<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals" %>
<%@page import="com.talentPool.salaryStructure.constants.SalaryStructureConstants"%><script src="js/scripta/lib/prototype.js"></script>
<script src="js/commonFunctions.js"></script>
<script language="JavaScript">
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
			</table><br/><br/>
	<%
		}
	%> 
	<div class="outerDiv">
	<html:form action="/salaryStructure" onsubmit="submitForm();return false;">
  	<html:hidden property="ctcRoundingType" name="salaryStructureForm"/>
  	<html:hidden property="mode" name="salaryStructureForm"/>
	<div class="popupTop">
		<table class="tblPop">
		 <tr>
	  		<td class="header" valign="top">
	  			<bean:message key="master_salary_structure.ctcRounding" />
	  		</td>
			<td>
				<table>
					<tr>
						<td>
							<img src="images/radiobutton.gif" name='imgRoundingType' id='img_<%=SalaryStructureConstants.ROUNDING_TO_ZERO%>' onclick="javascript:onRadioChange('imgRoundingType','<%=SalaryStructureConstants.ROUNDING_TO_ZERO%>');" style="margin-bottom:-1px;"/>
			      			<bean:message key="master_salary_structure.ctcRounding.noRounding" />
						</td>
					</tr>
					<tr>
						<td>
							<img src="images/radiobutton.gif" name='imgRoundingType' id='img_<%=SalaryStructureConstants.ROUNDING_TO_TEN%>' onclick="javascript:onRadioChange('imgRoundingType','<%=SalaryStructureConstants.ROUNDING_TO_TEN%>');" style="margin-bottom:-1px;"/>
		      				<bean:message key="master_salary_structure.ctcRounding.roundToTen" />
						</td>
					</tr>
					<tr>
						<td>
							<img src="images/radiobutton.gif" name='imgRoundingType' id='img_<%=SalaryStructureConstants.ROUNDING_TO_HUNDRED%>' onclick="javascript:onRadioChange('imgRoundingType','<%=SalaryStructureConstants.ROUNDING_TO_HUNDRED%>');" style="margin-bottom:-1px;"/>
		      				<bean:message key="master_salary_structure.ctcRounding.roundToHundred" />
						</td>
					</tr>
					<tr>
						<td>
							<img src="images/radiobutton.gif" name='imgRoundingType' id='img_<%=SalaryStructureConstants.ROUNDING_TO_THOUSAND%>' onclick="javascript:onRadioChange('imgRoundingType','<%=SalaryStructureConstants.ROUNDING_TO_THOUSAND%>');" style="margin-bottom:-1px;"/>
		      				<bean:message key="master_salary_structure.ctcRounding.roundToThousand" />
						</td>
					</tr>
				</table>
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
	document.salaryStructureForm.mode.value='savCTCRounding';
	document.salaryStructureForm.submit();
}
function actionOnLoad(){
	window.top.setPopTitle('<b><bean:message key="master_salary_structure.ctcRounding.title.saveCTCRounding"/></b>');
	changeSelectedRounding();
}
function changeSelectedRounding(){
	var ctcRoundingType = '<bean:write name="salaryStructureForm" property="ctcRoundingType" />';
	var ctcRoundingTypeImg = 'img_'+ctcRoundingType;
	if($(ctcRoundingTypeImg)){
		$(ctcRoundingTypeImg).src=chkedRadBut;
		document.salaryStructureForm.ctcRoundingType.value=ctcRoundingType;
	}
}
function onRadioChange(imgGroupName, ctcRoundingType){
	var imgs = document.getElementsByName(imgGroupName);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if (theImage.id.indexOf("img") > -1) {
			if( theImage.id == 'img_'+ctcRoundingType){
				theImage.src = chkedRadBut;
				document.salaryStructureForm.ctcRoundingType.value=ctcRoundingType;
			}else{
				theImage.src = unchkedRadBut;
			}
		}
	}
}
window.onload=actionOnLoad;
</script>