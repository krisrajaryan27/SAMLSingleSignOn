<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<%@page import="com.talentPool.positions.dataobject.PositionFieldData"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>

<%@page import="com.talentPool.budget.utils.BudgetUtils"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/doClasses/PositionScreenConfigarationClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript">
var budgetEnabled = false;
<% if (ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()) { %> 		
budgetEnabled = true;
<%
}
%>
	var _fields = new Array();
	<logic:notEmpty property="positionListFieldList" name="adminForm">
		<logic:iterate id="field" property="positionListFieldList" name="adminForm" type="PositionFieldData" indexId="counter">
			if(!(!budgetEnabled && <%= field.getFieldId().equals(PositionConfigurationConstants.FIELD_BUDGET_ITEM)%>)){
				var _field = new PosScreenConfFields();
				_field.setIndex('<bean:write name="counter" />');
				_field.setFieldId('<%=field.getFieldId()%>');
				_field.setFieldTitle('<%=field.getFieldTitle()%>');
				_field.setFieldType('<%=field.getFieldType()%>');
				_field.setFieldOnPositionPrintShow('<%=field.getFieldOnPositionPrintShow()%>');
				_field.setFieldPositionShow('<%=field.getFieldPositionShow()%>');
				_field.setFieldPositionMandatory('<%=field.getFieldPositionMandatory()%>');
				_field.setFieldVendorShow('<%=field.getFieldVendorShow()%>');
				_field.setFieldEmployeeShow('<%=field.getFieldEmployeeShow()%>');
				_fields[_fields.length]=_field;	
			}
		</logic:iterate>
	</logic:notEmpty>	
</script>
<html:form action="/adminHome">
	<html:hidden property="t" name="adminForm"/>
	<html:hidden property="mode"/>
	<html:hidden property="positionScreenFieldsString" name="adminForm"/>
	<html:hidden property="positionScreenViewOption" name="adminForm"/>
	<input type="hidden" name="isSubmitted" value="1"/>
	<div class="contentDiv">
		<div id="divError" style="display:block">
		<% 
			if(request.getAttribute(Globals.ERROR_KEY)!=null){
		%>
		<script>
			var isError=1;
		</script>
		<table  id="m_errortable" > 
			<tr>
			  <td class="header">
			    <b><bean:message key="errors.following_errors"/></b>
			  </td>               
			</tr>
			<tr>
			    <td class="message"><html:errors/></td>               
			</tr>
		</table>
		<br/>
		<% } %>
		<%
			String saved = (String)request.getAttribute("update");
			if(saved !=null){
		%>
				<table  id="m_errortable" > 
					<tr>
				    <td class="header">
				        <b>Settings updated successfully</b>
				    </td>               
					</tr>
				</table>
				<br/>
		<%
			}
		%>
		</div>
	</div>
	<div class="contentDivPop" style="padding-right:20px;">	
		<table class="navBtnTab2" style="margin-bottom: -3px;margin-left: -3px;">
			<tr>
				<td>
					<a class="active" href="adminHome.do?mode=managePositionScreen&positionScreenViewOption=2" name="link" id="2" 
						style="display:block;width:100px;"><span class="rightC"></span><span class="leftC"></span>
						<bean:message key="admin_postion_screen_configuration.label.description" />
					</a>
					<a class="" href="adminHome.do?mode=managePositionScreen&positionScreenViewOption=3" name="link" id="3" 
						style="display:block;width:110px;"><span class="rightC"></span><span class="leftC"></span>
						<bean:message key="admin_postion_screen_configuration.label.requirements" />
					</a>
					<%
					if (ModuleSet.isMODULE_VENDOR()){
					%>
					<a class="" href="adminHome.do?mode=managePositionScreen&positionScreenViewOption=<%=PositionConfigurationConstants.VIEW_OPTION_VENDOR%>" name="link" id="<%=PositionConfigurationConstants.VIEW_OPTION_VENDOR%>"
						style="display:block;width:100px;"><span class="rightC"></span><span class="leftC"></span>
						<bean:message key="admin_postion_screen_configuration.label.vendor" />
					</a>
					<%} %>			
					<%
					if (ModuleSet.isMODULE_EMPLOYEE()){
					%>
					<a class="" href="adminHome.do?mode=managePositionScreen&positionScreenViewOption=<%=PositionConfigurationConstants.VIEW_OPTION_EMPLOYEE%>" name="link" id="<%=PositionConfigurationConstants.VIEW_OPTION_EMPLOYEE%>"
						style="display:block;width:110px;"><span class="rightC"></span><span class="leftC"></span>
						<bean:message key="admin_postion_screen_configuration.label.employee" />
					</a>
					<%} %>
					<a class="" href="adminHome.do?mode=managePositionScreen&positionScreenViewOption=1" name="link" id="1" 
						style="display:block;width:100px;"><span class="rightC"></span><span class="leftC"></span>
						<bean:message key="admin_postion_screen_configuration.label.print" />
					</a>
				</td>
			</tr>
		</table>
		
		<div id="description" style="display: none;">
			<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
				<tr>
					<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>				
					<td class="header" height="18" style="width:140px;"><bean:message key="admin_position_screen_configuration.label.show_field_on_position_description" />?</td>
					<td class="header" height="18" style="width:100px;"><bean:message key="admin_position_screen_configuration.label.mandatory_for_position_creation" />?</td>
					<td class="header" height="18" style="width:70px;"><bean:message key="common.sequence" /></td>
				</tr>
			</table>
		</div>
		<div id="requirements" style="display: none;">
			<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
				<tr>
					<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>				
					<td class="header" height="18" style="width:140px;"><bean:message key="admin_position_screen_configuration.label.show_field_on_position_description" />?</td>
					<td class="header" height="18" style="width:100px;"><bean:message key="admin_position_screen_configuration.label.mandatory_for_position_creation" />?</td>
					<td class="header" height="18" style="width:70px;"><bean:message key="common.sequence" /></td>
				</tr>
			</table>
		</div>
		<div id="vendor" style="display: none;">
			<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
				<tr>
					<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>				
					<td class="header" height="18" style="width:326px;"><bean:message key="common.show" />?</td>										
				</tr>
			</table>
		</div>
		<div id="employee" style="display: none;">
			<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
				<tr>
					<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>				
					<td class="header" height="18" style="width:326px;"><bean:message key="common.show" />?</td>										
				</tr>
			</table>
		</div>
		<div id="print" style="display: block;">
			<table class="boxHeader" cellspacing="0" cellpading ="0" style="border-bottom: 0px;">
				<tr>
					<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>				
					<td class="header" height="18" style="width:248px;"><bean:message key="admin_position_screen_configuration.label.show_field_on_print" />?</td>
					<td class="header" height="18" style="width:70px;"><bean:message key="common.sequence" /></td>
				</tr>
			</table>
		</div>
		<div id="fldDiv" class="outerDiv" style="padding:0px;width:542px;">
				<table id="fldTable" cellspacing="0" cellpadding="0" class="boxContent" style="border:0px;">
				</table>
		</div>
		<br/>		
		<div class="navBtn" style="margin-top:5px;">
			<a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();">
			<span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
		</div>
		<br/>
		<br/>		
	</div>
	<br/>
</html:form>
<script language="javascript">

var chkedChkBoxSrc='images/checkboxchecked.gif';
var unchkedChkBoxSrc='images/checkboxunchecked.gif';
var view;

function showView(id){
	if(id==''){
		id='<%=PositionConfigurationConstants.VIEW_OPTION_DESCRIPTION%>';
	}
	var name = document.getElementsByName('link');
	for (i = 0; i < name.length; i++) {
		var obj = name[i];
		if( obj.id == id ){
			view = id;
			obj.className = "active";
		}else{
			obj.className = "";
		}
	}
	displayHeader();
	displayFields();	
}


function displayHeader(){
	if(view==undefined || view =='<%=PositionConfigurationConstants.VIEW_OPTION_DESCRIPTION%>'){
		$('print').hide();
		$('description').show();
		$('requirements').hide();
		$('vendor').hide();
		$('employee').hide();
	}else if(view=='<%=PositionConfigurationConstants.VIEW_OPTION_PRINT%>'){
		$('print').show();
		$('description').hide();
		$('requirements').hide();
		$('vendor').hide();
		$('employee').hide();
	}else if(view=='<%=PositionConfigurationConstants.VIEW_OPTION_REQUIREMENTS%>'){
		$('print').hide();
		$('description').hide();
		$('requirements').show();
		$('vendor').hide();
		$('employee').hide();
	}else if(view=='<%=PositionConfigurationConstants.VIEW_OPTION_VENDOR%>'){
		$('print').hide();
		$('description').hide();
		$('requirements').hide();
		$('vendor').show();
		$('employee').hide();
	}else if(view=='<%=PositionConfigurationConstants.VIEW_OPTION_EMPLOYEE%>'){
		$('print').hide();
		$('description').hide();
		$('requirements').hide();
		$('vendor').hide();
		$('employee').show();
	}
}

function changePositionScreenShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldOnPositionPrintShow != '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldOnPositionPrintShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldOnPositionPrintShow('<%=PositionConfigurationConstants.FIELD_NOT_SHOW%>');
		//changePositionListShowCheckBox(fieldId);
	}
}

function changePositionDescriptionShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldPositionShow != '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldPositionShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldPositionShow('<%=PositionConfigurationConstants.FIELD_NOT_SHOW%>');
		if(_field.fieldPositionMandatory ==  '<%=PositionConfigurationConstants.FIELD_MANDATORY%>'){
			document.getElementById('img_'+fieldId+'_MA').src=unchkedChkBoxSrc;
			_field.setFieldPositionMandatory('<%=PositionConfigurationConstants.FIELD_NOT_MANDATORY%>');
		}
	}
}

function changeVendorShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldVendorShow != '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldVendorShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldVendorShow('<%=PositionConfigurationConstants.FIELD_NOT_SHOW%>');
	}
}

function changeEmployeeShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldEmployeeShow != '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldEmployeeShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldEmployeeShow('<%=PositionConfigurationConstants.FIELD_NOT_SHOW%>');
	}
}

function changeMandatoryForPositionCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldPositionMandatory != '<%=PositionConfigurationConstants.FIELD_MANDATORY%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldPositionMandatory('<%=PositionConfigurationConstants.FIELD_MANDATORY%>');
		if(_field.fieldPositionShow !=  '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
			document.getElementById('img_'+fieldId+'_DS').src=chkedChkBoxSrc;
			_field.setFieldPositionShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
		}
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldPositionMandatory('<%=PositionConfigurationConstants.FIELD_NOT_MANDATORY%>');
	}
}

function changePositionListShowCheckBox(fieldId){
	if($('img_'+fieldId+'_PL')){
		$('img_'+fieldId+'_PL').src=chkedChkBoxSrc;
	}
}

function changePositionDetailsShowCheckBox(fieldId){
	if($('img_'+fieldId+'_PD')){
		$('img_'+fieldId+'_PD').src=chkedChkBoxSrc;
	}
}

function doChangeStepRankOfList(id1, id2) {
	if (id1 != -1 && id2 != _fields.length) {
		_temp = _fields[id1];
		_fields[id1]=_fields[id2];
		_fields[id2]=_temp;
		displayFields();			
	}
}

//function to display list field configuration
function displayFields(){
	tbl = $('fldTable');
	if (tbl) {
		if ( tbl.hasChildNodes() ){
		    while ( tbl.childNodes.length >= 1 ){
		        tbl.removeChild( tbl.firstChild );       
		    } 
		}
		var defaultFields = 0;
				
		for (var i = 0; i < _fields.length; i++) {
			_field = _fields[i];
			var fieldId = _field.fieldId;
			var positionListValue = _field.fieldOnPositionPrintShow;
			var showOnDescription= _field.fieldPositionShow;
			var isMandatory=_field.fieldPositionMandatory;
			var showVendor = _field.fieldVendorShow;
			var showEmployee = _field.fieldEmployeeShow;
			
			var tBody=document.createElement("TBODY");

			var tRow = document.createElement("TR");
			
			var tCell0 = document.createElement("TD");
			tCell0.style.width="200px";
			tCell0.style.height="20px";
			tCell0.className="label";
			tCell0.innerHTML = _field.fieldTitle;		
			tRow.appendChild(tCell0);			

			if(view =='<%=PositionConfigurationConstants.VIEW_OPTION_PRINT%>'){

				var tCell1 = document.createElement("TD");
				tCell1.className="normal";
				tCell1.style.width="248px";
				tCell1.style.wordWrap="break-word";
				
				innerHTML = '';	
				if(positionListValue=='<%=PositionConfigurationConstants.FIELD_SHOW%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_AS" name="showName" onclick="changePositionScreenShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_AS" name="showName" onclick="changePositionScreenShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				tCell1.innerHTML = innerHTML;	
					
				tRow.appendChild(tCell1);
				
				var tCell4 = document.createElement("TD");
				tCell4.className="normal";
				tCell4.style.width="70px";
				tCell4.style.wordWrap="break-word";
				innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: doChangeStepRankOfList(' + (i - 1) + ', ' + i + ');"/>';		
				innerHTML += '&nbsp;&nbsp;&nbsp;';
				innerHTML += '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: doChangeStepRankOfList(' + i + ', ' + (i + 1) + ');"/>';		
				tCell4.innerHTML = innerHTML;		
				tRow.appendChild(tCell4);

			}
			<%
			if (ModuleSet.isMODULE_VENDOR()) {
			%>
			else if(view =='<%=PositionConfigurationConstants.VIEW_OPTION_VENDOR%>') {
				
				var tCell2 = document.createElement("TD");
				tCell2.className="normal";
				tCell2.style.width="318px";
				tCell2.style.wordWrap="break-word";

				if(fieldId=='<%=PositionConfigurationConstants.FIELD_NAME%>'){
					tCell2.innerHTML = '<img src="images/item_chk1_dis.gif" />';
				}
				else{
					innerHTML = '';	
					if(showVendor=='<%=PositionConfigurationConstants.FIELD_SHOW%>'){ 
				  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_DS" name="showOnDescription" onclick="changeVendorShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
					  }else{
					  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_DS" name="showOnDescription" onclick="changeVendorShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
					  }
					tCell2.innerHTML = innerHTML;	
				}
				tRow.appendChild(tCell2);				
				
			} 
			<% } %>
			<%
			if (ModuleSet.isMODULE_EMPLOYEE()){
			%>
			else if(view =='<%=PositionConfigurationConstants.VIEW_OPTION_EMPLOYEE%>') {
				
				var tCell2 = document.createElement("TD");
				tCell2.className="normal";
				tCell2.style.width="318px";
				tCell2.style.wordWrap="break-word";

				if(fieldId=='<%=PositionConfigurationConstants.FIELD_NAME%>'){
					tCell2.innerHTML = '<img src="images/item_chk1_dis.gif" />';
				}
				else{
					innerHTML = '';	
					if(showEmployee=='<%=PositionConfigurationConstants.FIELD_SHOW%>'){ 
				  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_DS" name="showOnDescription" onclick="changeEmployeeShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
					  }else{
					  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_DS" name="showOnDescription" onclick="changeEmployeeShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
					  }
					tCell2.innerHTML = innerHTML;	
				}
				tRow.appendChild(tCell2);
				
			}
			<% } %>			
			else{
				
				var tCell2 = document.createElement("TD");
				tCell2.className="normal";
				tCell2.style.width="140px";
				tCell2.style.wordWrap="break-word";

				if(fieldId=='<%=PositionConfigurationConstants.FIELD_NAME%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_CODE%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_REQUESTEDBY%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_DEPARTMENT_LEVELS%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_BUDGET_ITEM%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_VACANCIES%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_HIRE_BY_DATE%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_PRIMARY_SKILLS%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_EXPERIENCE%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_EDUCATION%>' ){
					tCell2.innerHTML = '<img src="images/item_chk1_dis.gif" />';
				}
				else{
					innerHTML = '';	
					if(showOnDescription=='<%=PositionConfigurationConstants.FIELD_SHOW%>'){ 
				  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_DS" name="showOnDescription" onclick="changePositionDescriptionShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
					  }else{
					  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_DS" name="showOnDescription" onclick="changePositionDescriptionShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
					  }
					tCell2.innerHTML = innerHTML;	
				}
				tRow.appendChild(tCell2);

				var tCell3 = document.createElement("TD");
				tCell3.className="normal";
				tCell3.style.width="100px";
				tCell3.style.wordWrap="break-word";
				if(fieldId=='<%=PositionConfigurationConstants.FIELD_NAME%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_CODE%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_REQUESTEDBY%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_DEPARTMENT_LEVELS%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_BUDGET_ITEM%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_VACANCIES%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_HIRE_BY_DATE%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_PRIMARY_SKILLS%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_EXPERIENCE%>' ||
						fieldId=='<%=PositionConfigurationConstants.FIELD_EDUCATION%>'){
					tCell3.innerHTML = '<img src="images/item_chk1_dis.gif" />';
				}
				else{
					innerHTML = '';	
					if(isMandatory=='<%=PositionConfigurationConstants.FIELD_MANDATORY%>'){ 
				  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_MA" name="mandatory" onclick="changeMandatoryForPositionCheckboxState(this,\''+fieldId+'\', '+i+');" />';
					  }else{
					  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_MA" name="mandatory" onclick="changeMandatoryForPositionCheckboxState(this,\''+fieldId+'\', '+i+');" />'
					  }
					tCell3.innerHTML = innerHTML;	
				}
				tRow.appendChild(tCell3);
				
				var tCell4 = document.createElement("TD");
				tCell4.className="normal";
				tCell4.style.width="70px";
				tCell4.style.wordWrap="break-word";
				innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: doChangeStepRankOfList(' + (i - 1) + ', ' + i + ');"/>';		
				innerHTML += '&nbsp;&nbsp;&nbsp;';
				innerHTML += '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: doChangeStepRankOfList(' + i + ', ' + (i + 1) + ');"/>';		
				tCell4.innerHTML = innerHTML;		
				tRow.appendChild(tCell4);
			}
						
			tBody.appendChild(tRow);
			tbl.appendChild(tBody);
		}
	}	
}

function submitForm(){
	var frm=document.adminForm;
	frm.positionScreenFieldsString.value = listToString(_fields);
	frm.submit();
}

function validateData(){
	errors='';
	var selectedField = selectBoxField.getSelectedId();
	if(selectedField=='-1' && document.adminForm.otherFieldValue.value==''){
		errors = addError(errors, '<bean:message key="admin_website_screen_configuration.message.enter_first_field_name" />');
	}
	return errors;
}

function listToString(_fields){
	var str = "";
	for(var i =0;i<_fields.length;i++){
		str += formatString(''+_fields[i].fieldId) + "|";
		str += formatString(_fields[i].fieldType) + "|";
		str += formatString(''+_fields[i].fieldOnPositionPrintShow) + "|";
		str += formatString(''+_fields[i].fieldPositionShow) + "|";
		str += formatString(''+_fields[i].fieldPositionMandatory) + "|";
		str += formatString(''+_fields[i].fieldVendorShow) + "|";
		str += formatString(''+_fields[i].fieldEmployeeShow) + "|";
		str += (parseInt(_fields[i].index) + 1);
		if(i!=_fields.length-1){
			str +=",";
		}
	}
	return str;
}

function showDiv(){
	if(selectBoxField.getSelectedId()=='-1'){
		document.getElementById('otherValueDiv').style.display="block";
	}else{
		document.getElementById('otherValueDiv').style.display="none";
	}
}

function doOnLoad() {
	showView(document.adminForm.positionScreenViewOption.value);
}

window.onload=doOnLoad;
</script>
