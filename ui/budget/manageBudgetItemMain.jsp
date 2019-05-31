<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.struts.Globals,
								com.talentPool.common.db.SimpleDataObject,
								com.talentPool.common.properties.TPApplicationProperties,
								com.talentPool.common.properties.GlobalApplicationProperties,
								com.talentPool.masters.constants.MastersConstants,
								com.talentPool.common.properties.GlobalConstants" %>
<%@page import="com.talentPool.budget.BudgetConstants"%>


<%@page import="com.talentPool.masters.constants.MastersConstants"%><link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>

<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>

<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>						
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>

<script language="JavaScript">
<logic:present name="update" scope="request">
	window.location.href="budgets.do?mode=budgetHome";
</logic:present>                
</script>
	
<script language="JavaScript">
var selectBoxDepartment=null;
var selectBoxSubDepartment=null;
var selectBoxSubSubDepartment=null;
var selectBoxOwner=null;

var maxDepartmentLevel = '<%= GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_DEPT_LEVEL)%>';
</script>
<div class="contentDiv">			
	<%
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
	<table  id="m_errortable" >
		<tr>
	    <td class="header" colspan="2">
	    	<b><bean:message key="errors.following_errors"/></b>
	    </td>
		</tr>
    <tr>
    	<td class="message" colspan="2"><html:errors/></td>
    </tr>
	</table>
	<br/>
	<% } %>
	
	
	<table width="100%" class="boxHeaderGrey" cellspacing="0">
		<tr>
			<td height="15" style="padding-left:20px;">
				<strong>
					<logic:equal name="budgetForm" property="subMode" value="<%=BudgetConstants.SUB_MODE_ADD%>">
						<bean:message key="budget.tabs.add_budget_item" />
					</logic:equal>
					<logic:equal name="budgetForm" property="subMode" value="<%=BudgetConstants.SUB_MODE_EDIT%>">
						<bean:message key="budet.tabs.edit_budget_item" />
					</logic:equal>
				</strong>
			</td>
		</tr>
	</table>

	<div class="outerDiv" style="border-top:0px;">
		<html:form action="/budgets">
			<html:hidden property="mode" name="budgetForm"/>		
			<html:hidden property="budgetItemId" name="budgetForm"/>	
			<html:hidden property="ownerId" name="budgetForm"/>			
			<html:hidden property="deptId" name="budgetForm"/>
			<html:hidden property="subDeptId" name="budgetForm"/>
			<html:hidden property="subSubDeptId" name="budgetForm"/>
			<html:hidden property="sub3DeptId" name="budgetForm"/>
			<html:hidden property="sub4DeptId" name="budgetForm"/>
			<html:hidden property="subMode" name="budgetForm"/>
			<html:hidden property="gradeId" name="budgetForm"/>
			<html:hidden property="bandId" name="budgetForm"/>
			<html:hidden property="status" name="budgetForm"/>
			<html:hidden property="createdByUserId" name="budgetForm"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top" width="50%" height="210px" style="border-right: 1px solid #cccccc;">
						<div class="contentDiv">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
								<tr>
									<td class="label2"><bean:message key="budget.manage.budget_item_name" />
									<span class="star">*</span></td>
									<td><html:text name="budgetForm" property="budgetItemName" size="41" styleClass="Grey" maxlength="100"/></td>
								</tr>
								<tr>
									<td class="label2"><bean:message key="budget.manage.owner_name" />
									<span class="star">*</span></td>
									<td>
										<script language="JavaScript">									
											var opts = <bean:write name="budgetForm" property="jsArrayOwners" filter="false"/>;											
											var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
											owners = opt.concat(opts);
											selectBoxOwner = new SelectBox(owners,'<bean:write name="budgetForm" property="ownerId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											document.write(selectBoxOwner.getHtml());
											selectBoxOwner.init();
										</script>
									</td>
								</tr>
								<tr>
									<td class="label2"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>
									<span class="star">*</span></td>
									<td>
										<script language="JavaScript">	
											var opts = <bean:write name="budgetForm" property="jsArrayDepartments" filter="false"/>;											
											var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
											departments = opt.concat(opts);
											selectBoxDepartment = new SelectBox(departments,'<bean:write name="budgetForm" property="deptId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											selectBoxDepartment.setOnChangeHandler('onChangeDepartment');
											document.write(selectBoxDepartment.getHtml());
											selectBoxDepartment.init();
										</script>
									</td>
								</tr>
								<tr style="display:none" id="subDepartmentRow">									
									<td class="label2"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2) %></td>
									<td>
										<script type="text/javascript">
											var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
											selectBoxSubDepartment = new SelectBox(opts,'<bean:write name="budgetForm" property="subDeptId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											selectBoxSubDepartment.setOnChangeHandler('onChangeSubDepartment');
											document.write(selectBoxSubDepartment.getHtml());
											selectBoxSubDepartment.init();
										</script>
									</td>								
								</tr>
								<tr style="display:none" id="subSubDepartmentRow">									
									<td class="label2"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3) %></td>
									<td>
									<script type="text/javascript">
										var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
										selectBoxSubSubDepartment = new SelectBox(opts,'<bean:write name="budgetForm" property="subSubDeptId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
										
										if('<%=MastersConstants.DEPARTMENT_LEVEL_4%>'==maxDepartmentLevel || '<%=MastersConstants.DEPARTMENT_LEVEL_5%>'==maxDepartmentLevel ){
											selectBoxSubSubDepartment.setOnChangeHandler('onChangeSubSubDepartment');
										}
										document.write(selectBoxSubSubDepartment.getHtml());
										selectBoxSubSubDepartment.init();
									</script>
									</td>												
								</tr>
								<tr style="display:none" id="sub3DepartmentRow">									
									<td class="label2"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_4) %></td>
									<td>
										<script type="text/javascript">
											var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
											selectBoxSub3Department = new SelectBox(opts,'<bean:write name="budgetForm" property="sub3DeptId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											if( '<%=MastersConstants.DEPARTMENT_LEVEL_5%>'==maxDepartmentLevel ){
												selectBoxSub3Department.setOnChangeHandler('onChangeSub3Department');
											}
											document.write(selectBoxSub3Department.getHtml());
											selectBoxSub3Department.init();
										</script>
									</td>								
								</tr>
								<tr style="display:none" id="sub4DepartmentRow">									
									<td class="label2"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_5) %></td>
									<td>
										<script type="text/javascript">
											var opts = [new SelectOption('-1','<bean:message key="common.selectlist.default"/>')];
											selectBoxSub4Department = new SelectBox(opts,'<bean:write name="budgetForm" property="sub4DeptId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											document.write(selectBoxSub4Department.getHtml());
											selectBoxSub4Department.init();
										</script>
									</td>								
								</tr>
								<tr>
									<td class="label2"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %><span class="star">*</span>
									</td>									
									<td>
										<script language="JavaScript">									
											var opts = <bean:write name="budgetForm" property="jsArrayGrades" filter="false"/>;											
											var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
											grades = opt.concat(opts);
											selectBoxGrade = new SelectBox(grades,'<bean:write name="budgetForm" property="gradeId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											document.write(selectBoxGrade.getHtml());
											selectBoxGrade.init();
										</script>								
									</td>
								</tr>
								<tr>
									<td class="label2"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL) %><span class="star">*</span>
									</td>									
									<td>
										<script language="JavaScript">									
											var opts = <bean:write name="budgetForm" property="jsArrayBands" filter="false"/>;											
											var opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
											bands = opt.concat(opts);
											selectBoxBand = new SelectBox(bands,'<bean:write name="budgetForm" property="bandId" />','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15, textboxclass:'Grey'});
											document.write(selectBoxBand.getHtml());
											selectBoxBand.init();
										</script>									
								</tr>
								<tr>
									<td class="label2"><bean:message key="budget.manage.head_count" /><span class="star">*</span></td>
									<td><html:text name="budgetForm" styleId="availableHeadCount" property="availableHeadCount" size="30" styleClass="Grey" maxlength="100"/></td>
								</tr>
								<tr>
									<td class="label2"><bean:message key="budget.manage.start_date" />
									<span class="star">*</span></td>
									<td>
										<html:text name="budgetForm" property="startTime" styleId="startTime" onblur="javascript: getFormattedDate(this);" size="12" maxlength="10" styleClass="Grey"/>
										<img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('startTime'),'startTime','dd/MM/yyyy'); return false;"/>	
									</td>
								</tr>
								<tr>
									<td class="label2"><bean:message key="budget.manage.end_date" />
									<span class="star">*</span></td>
									<td>
										<html:text name="budgetForm" property="endTime" styleId="endTime" onblur="javascript: getFormattedDate(this);" size="12" maxlength="10" styleClass="Grey"/>
										<img src="images/ico_cal.gif" onClick="popUpCal.select(document.getElementById('endTime'),'endTime','dd/MM/yyyy'); return false;"/>	
									</td>
								</tr>		
								<tr style="display:none;">
									<td class="label2"><bean:message key="budget.manage.budget_distribution" /></td>								
									<td>									
										<img src="images/radiobutton.gif" name='imgPermission' id='1' onclick="javascript:onRadioChange(this);" style="margin-bottom:-1px;"/>
										<bean:message key="budget.manage.monthly" />&nbsp;&nbsp;
										<img src="images/radiobutton.gif" name='imgPermission' id='2' onclick="javascript:onRadioChange(this);" style="margin-bottom:-1px;"/>
										<bean:message key="budget.manage.quarterly" />&nbsp;&nbsp;
										<img src="images/checkedradiobutton.gif" name='imgPermission' id='3' onclick="javascript:onRadioChange(this);" style="margin-bottom:-1px;"/>
										<bean:message key="budget.manage.custom" />				
									</td>								
								</tr>					
							</table>
						</div>
						
					</td>
				</tr>
				<tr>
					<td valign="top" width="50%" height="115px" style="border-right: 1px solid #cccccc;">
						<div class="contentDiv">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
								
							</table>
						</div>
					</td>
				</tr>
			</table>
		</html:form>
	</div>
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2">
			<br/>
			<div class="navBtn" style="float:right;">
				<a href="#" style="width:50px;" class="active" onclick="javascript:saveBudgetItem();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelProcess();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
			</div>
		</td>
	</tr>
</table>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
  	  if(!dtf.checkDate(obj)){
  		obj.select();
  		alert('<bean:message key="calendar.error.invalid_date"/>');
  		obj.focus();
  		return false;
  	  }else {
  		return true;
  	  }
	}
	return true;
}

var dtfo = new DateFormatter();
function getFDate(obj,format){
	dtfo.setDisplayFormat(format);
	if(obj.value.trim()!=''){
	if(!dtfo.checkDate(obj)){
		obj.select();
		alert("Please enter date in " + format + " format");
		obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}


function onChangeDepartment(){
	var val ;
	if(document.budgetForm.deptId.value==''){
	  val = selectBoxDepartment.getSelectedId();
	}
	else{
		val = document.budgetForm.deptId.value;
	}
	  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
	  var myAjax = ajaxCall("department.do",'get',pars,updateSubdepartments, reportError);
}

function updateSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubDepartment.reInitialize(opts, document.budgetForm.subDeptId.value);
		var n = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		selectBoxSubSubDepartment.reInitialize(n, document.budgetForm.subSubDeptId.value);
		selectBoxSub3Department.reInitialize(opts, document.budgetForm.sub3DeptId.value);
		selectBoxSub4Department.reInitialize(opts, document.budgetForm.sub4DeptId.value);
		if(opts.length>1){
			Element.show('subDepartmentRow');
		}else{
			Element.hide('subDepartmentRow');
			Element.hide('subSubDepartmentRow');
			Element.hide('sub3DepartmentRow');
			Element.hide('sub4DepartmentRow');
		}
		
	}	
}

function onChangeSubDepartment(){
	var val;
	if(document.budgetForm.subDeptId.value==''){
  		val = selectBoxSubDepartment.getSelectedId();
	}
	else{
		val = document.budgetForm.subDeptId.value;
	}
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSubSubdepartments, reportError);
}

function updateSubSubdepartments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSubSubDepartment.reInitialize(opts, document.budgetForm.subSubDeptId.value);
		if(opts.length>1){
			Element.show('subSubDepartmentRow');
		}else{
			Element.hide('subSubDepartmentRow');
			Element.hide('sub3DepartmentRow');
			Element.hide('sub4DepartmentRow');
		}
		
	}	
}

function onChangeSubSubDepartment(){
	var val;
	if(document.budgetForm.subSubDeptId.value==''){
  		val = selectBoxSubSubDepartment.getSelectedId();
	}
	else{
		val = document.budgetForm.subSubDeptId.value;
	}	
	
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSub3departments, reportError);
}

function updateSub3departments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSub3Department.reInitialize(opts, document.budgetForm.sub3DeptId.value);
		if(opts.length>1){
			Element.show('sub3DepartmentRow');
		}else{		
			Element.hide('sub3DepartmentRow');
			Element.hide('sub4DepartmentRow');
		}		
	}	
}

function onChangeSub3Department(){
	var val;
	if(document.budgetForm.sub3DeptId.value==''){
  		val = selectBoxSub3Department.getSelectedId();
	}
	else{
		val = document.budgetForm.sub3DeptId.value;
	}	
  var pars = "mode=getSubDepartmentJS&departmentId=" + val;
  var myAjax = ajaxCall("department.do",'get',pars,updateSub4departments, reportError);
}

function updateSub4departments(request){
 	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		var op = request.responseText;
		var opts = eval(op);
	
		var m = [new SelectOption('-1', '<bean:message key='common.selectlist.default' />')];
		opts = m.concat(opts);
		selectBoxSub4Department.reInitialize(opts, document.budgetForm.sub4DeptId.value);
		if(opts.length>1){
			Element.show('sub4DepartmentRow');
		}else{		
			Element.hide('sub4DepartmentRow');
		}		
	}	
}

function validateData(){

	errors = '';
	var budgetItemName = document.budgetForm.budgetItemName.value;
	var startTime = document.budgetForm.startTime.value;
	var endTime = document.budgetForm.endTime.value;	
	var availableHeadCount = document.budgetForm.availableHeadCount.value;	
	if (budgetItemName.trim() == '') {
		errors = addError(errors, '- <bean:message key="budget.manage.budget_item_name" />');
	}
	if (startTime.trim() == '') {
		errors = addError(errors, '- <bean:message key="budget.manage.start_date" />');
	}
	if (endTime.trim() == '') {
		errors = addError(errors, '- <bean:message key="budget.manage.end_date" />');
	}
	if (availableHeadCount.trim() == '' || isNaN(availableHeadCount) || availableHeadCount<0) {
		errors = addError(errors, '- <bean:message key="budget.manage.head_count" />');
	}
	if(selectBoxDepartment.getSelectedId()=="-1"){
		errors = addError(errors, '- '+'<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %>');
	}else{
		document.budgetForm.deptId.value=selectBoxDepartment.getSelectedId();
		document.budgetForm.subDeptId.value=selectBoxSubDepartment.getSelectedId();
		document.budgetForm.subSubDeptId.value=selectBoxSubSubDepartment.getSelectedId();
		document.budgetForm.sub3DeptId.value=selectBoxSub3Department.getSelectedId();
		document.budgetForm.sub4DeptId.value=selectBoxSub4Department.getSelectedId();
	}	
	if(selectBoxOwner.getSelectedId()=="-1"){
		errors = addError(errors, '- <bean:message key="budget.manage.owner_name" />');
	}else{
		document.budgetForm.ownerId.value=selectBoxOwner.getSelectedId();
	}
	if(selectBoxGrade.getSelectedId()=="-1"){
		errors = addError(errors, '- '+'<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %>');
	}else{
		document.budgetForm.gradeId.value=selectBoxGrade.getSelectedId();
	}
	if(selectBoxBand.getSelectedId()=="-1"){
		errors = addError(errors,  '- '+'<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL) %>');
	}else{
		document.budgetForm.bandId.value=selectBoxBand.getSelectedId();
	}

	  
	return errors;
}

function addError(errors, error) {
	if (errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}

function saveBudgetItem(){
	errors = validateData();
	if (errors.length > 0) {
		alert('<bean:message key="errors.following_errors"/>\n'+errors);
		return false;
	} 
	document.budgetForm.mode.value = '<%=BudgetConstants.MODE_SAVE_BUDGET_ITEM%>';
	document.budgetForm.submit();
}

function cancelProcess() {
	window.location.href="budgets.do?mode=budgetHome";
	return true;
}

var unchkedRadioButton='images/radiobutton.gif';
var chkedRadioButton='images/checkedradiobutton.gif';

function onRadioChange(obj){
	var source = obj.src;
	var objectId = parseInt(obj.id);	
	if (source.indexOf(chkedRadioButton) == -1) {
		var nextId = objectId%3+1;
		var prevId = objectId -1;
		if(prevId==0){
			prevId = 3;			
		}
		document.getElementById(prevId).src =unchkedRadioButton;
		document.getElementById(objectId).src =chkedRadioButton;
		document.getElementById(nextId).src =unchkedRadioButton;
	}
}

function doOnload(){
	if(document.budgetForm.deptId.value!= ''){
		onChangeDepartment();
	}
	if(document.budgetForm.subDeptId.value!= ''){
		onChangeSubDepartment();		
	}
	if(document.budgetForm.subSubDeptId.value!= ''){
		if('<%=MastersConstants.DEPARTMENT_LEVEL_4%>'==maxDepartmentLevel || '<%=MastersConstants.DEPARTMENT_LEVEL_5%>'==maxDepartmentLevel ){
			onChangeSubSubDepartment();		
		}		
	}
	if(document.budgetForm.sub3DeptId.value!= ''){
		if('<%=MastersConstants.DEPARTMENT_LEVEL_5%>'==maxDepartmentLevel ){
			onChangeSub3Department();	
		}				
	}
}

window.onLoad = doOnload();
</script>