<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.applicant.dataobject.ImportFieldData"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.admin.AdminConstants"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/doClasses/ScreenConfigarationClass.js" type="text/javascript"></script>	
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script language="JavaScript">
	var _fields = new Array();
	<logic:notEmpty property="fieldList" name="adminForm">
		<logic:iterate id="field" property="fieldList" name="adminForm" type="ImportFieldData" indexId="counter">
			var _field = new Field();
			_field.setIndex('<bean:write name="counter" />');
			_field.setFieldId('<%=field.getFieldId()%>');
			_field.setFieldTitle('<%=field.getFieldTitle()%>');
			_field.setFieldType('<%=field.getFieldType()%>');
			_field.setFieldImportShow('<%=field.getFieldImportShow()%>');
			_field.setFieldEditShow('<%=field.getFieldEditShow()%>');
			_field.setFieldImportMandatory('<%=field.getFieldImportMandatory()%>');
			_field.setFieldVendorShow('<%=field.getFieldVendorShow()%>');
			_field.setFieldVendorMandatory('<%=field.getFieldVendorMandatory()%>');
			_field.setFieldEmployeeShow('<%=field.getFieldEmployeeShow()%>');
			_field.setFieldEmployeeMandatory('<%=field.getFieldEmployeeMandatory()%>');
			_field.setFieldWebsiteShow('<%=field.getFieldWebsiteShow()%>');
			_field.setFieldWebsiteMandatory('<%=field.getFieldWebsiteMandatory()%>');
			_field.setFieldConfidential('<%=field.getFieldConfidential()%>');
			_field.setIsProcessField('<%=field.isProcessField()%>');
			_fields[_fields.length]=_field;
		</logic:iterate>
	</logic:notEmpty>	
</script>

<html:form action="/adminHome">
	<html:hidden property="t" name="adminForm"/>
	<html:hidden property="mode"/>
	<html:hidden property="importFieldsString" name="adminForm"/>
	<input type="hidden" name="isSubmitted" value="1"/>
</html:form>
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
		<a class="active" href="#" name="link" id="<%=AdminConstants.VIEW_TALENTPOOL%>"
			onclick="javascript: showView('<%=AdminConstants.VIEW_TALENTPOOL%>');"
			style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
			<bean:message key="admin_screen_configuration.applicant.label.import_configuration" />
		</a>
		<%
		if (ModuleSet.isMODULE_VENDOR()){
		%>
		<a class="" href="#" name="link" id="<%=AdminConstants.VIEW_VENDOR%>"
			onclick="javascript: showView('<%=AdminConstants.VIEW_VENDOR%>');"
			style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
			<bean:message key="admin_screen_configuration.applicant.label.vendor_configuration" />
		</a>
		<%} %>			
		<%
		if (ModuleSet.isMODULE_EMPLOYEE()){
		%>
		<a class="" href="#" name="link" id="<%=AdminConstants.VIEW_EMPLOYEE%>"
			onclick="javascript: showView('<%=AdminConstants.VIEW_EMPLOYEE%>');"
			style="display:block;width:130px;"><span class="rightC"></span><span class="leftC"></span>
			<bean:message key="admin_screen_configuration.applicant.label.employee_configuration" />
		</a>
		<%} %>
		<% 
		if (ModuleSet.isMODULE_WEB_INTEGRATION()){
		%>
		<a class="" href="#" name="link" id="<%=AdminConstants.VIEW_WEBSITE%>"
			onclick="javascript: showView('<%=AdminConstants.VIEW_WEBSITE%>');"
			style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
			<bean:message key="admin_screen_configuration.applicant.label.wesite_configuration" />
		</a> 
		<%} %>
	</td>
			</tr>
		</table>		


	
	<div id="pool" style="display: block;">
		<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
			<tr>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.fields" /></td>
				<td class="header" height="18" style="width:110px;"><bean:message key="admin_screen_configuration.label.field_show" /> ?</td>
				<td class="header" height="18" style="width:110px;"><bean:message key="admin_screen_configuration.label.field_edit" /> ?</td>
				<td class="header" height="18" style="width:110px;"><bean:message key="admin_screen_configuration.label.field_import_mandatory" /> ?</td>
				<td class="header" height="18" style="width:110px;"><bean:message key="common.confidential" /> ?</td>
				<td class="header" height="18" style="width:110px;"><bean:message key="common.sequence" /></td>
			</tr>
		</table>
	</div>
	<div id="vendor" style="display: none;">
		<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
			<tr>
				<% if (ModuleSet.isMODULE_VENDOR()){ %>
				<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.show" /> ?</td>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.mandatory" /> ?</td>
				<td class="header" height="18" style="width:210px;">&nbsp;</td>
				<%} %>			
			</tr>
		</table>
	</div>
	<div id="employee" style="display: none;">
		<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
			<tr>
				<% if (ModuleSet.isMODULE_EMPLOYEE()){ %>
				<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.show" /> ?</td>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.mandatory" /> ?</td>
				<td class="header" height="18" style="width:210px;">&nbsp;</td>			
				<%} %>
			</tr>
		</table>
	</div>
	<div id="site" style="display: none;">
		<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
			<tr>
				<% if (ModuleSet.isMODULE_WEB_INTEGRATION()){ %>
				<td class="header" height="18" style="width:200px;"><bean:message key="common.fields" /></td>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.show" /> ?</td>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.mandatory" /> ?</td>
				<td class="header" height="18" style="width:210px;">&nbsp;</td>
				<%} %>
			</tr>
		</table>
	</div>
	
	<div id="fldDiv" class="outerDiv" style="padding:0px; width: 739px;">
		<table id="fldTable" cellspacing="0" cellpadding="0" class="boxContent" style="border:0px;"></table>
	</div>
	<br/>
	
	<div class="navBtn" style="margin-top:5px;"><a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();">
		<span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
		<span class="grey">&nbsp;&nbsp;&nbsp;<bean:message key="admin_screen_configuration.note.restart_service" /></span>
	</div>
	
	<br/>
</div>
<br></br>
<script language="javascript">
var chkedChkBoxSrc='images/checkboxchecked.gif';
var unchkedChkBoxSrc='images/checkboxunchecked.gif';
var view;
/*  We cannot allow fields that show on the import screen but not on the edit screen. 
	Only vice versa should be allowed. -Nitin
*/

function showView(id){
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

function changeShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldImportShow != '<%=ImportConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldImportShow('<%=ImportConfigurationConstants.FIELD_SHOW%>');
		_field.setFieldEditShow('<%=ImportConfigurationConstants.FIELD_EDIT%>');
		changeEditCheckBox(fieldId);
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldImportShow('<%=ImportConfigurationConstants.FIELD_NOT_SHOW%>');
	}
}

function changeEditCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldEditShow != '<%=ImportConfigurationConstants.FIELD_EDIT%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldEditShow('<%=ImportConfigurationConstants.FIELD_EDIT%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldEditShow('<%=ImportConfigurationConstants.FIELD_NOT_EDIT%>');
		_field.setFieldImportShow('<%=ImportConfigurationConstants.FIELD_NOT_SHOW%>');
		changeShowCheckBox(fieldId);
	}
}

function changeMandatoryCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldImportMandatory != '<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldImportMandatory('<%=ImportConfigurationConstants.FIELD_MANDATORY%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldImportMandatory('<%=ImportConfigurationConstants.FIELD_NOT_MANDATORY%>');
	}
}

function changeVendorShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldVendorShow != '<%=ImportConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldVendorShow('<%=ImportConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldVendorShow('<%=ImportConfigurationConstants.FIELD_NOT_SHOW%>');
		_field.setFieldVendorMandatory('<%=ImportConfigurationConstants.FIELD_NOT_MANDATORY%>');
		changeVendorMandatoryCheckBox(fieldId);
	}
}

function changeVendorMandatoryCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldVendorMandatory != '<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldVendorMandatory('<%=ImportConfigurationConstants.FIELD_MANDATORY%>');
		_field.setFieldVendorShow('<%=ImportConfigurationConstants.FIELD_SHOW%>');
		changeVendorShowCheckBox(fieldId);
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldVendorMandatory('<%=ImportConfigurationConstants.FIELD_NOT_MANDATORY%>');
	}
}

//Employee Code
function changeEmployeeShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldEmployeeShow != '<%=ImportConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldEmployeeShow('<%=ImportConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldEmployeeShow('<%=ImportConfigurationConstants.FIELD_NOT_SHOW%>');
		_field.setFieldEmployeeMandatory('<%=ImportConfigurationConstants.FIELD_NOT_MANDATORY%>');
		changeEmployeeMandatoryCheckBox(fieldId);
	}
}

function changeEmployeeMandatoryCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldEmployeeMandatory != '<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldEmployeeMandatory('<%=ImportConfigurationConstants.FIELD_MANDATORY%>');
		_field.setFieldEmployeeShow('<%=ImportConfigurationConstants.FIELD_SHOW%>');
		changeEmployeeShowCheckBox(fieldId);
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldEmployeeMandatory('<%=ImportConfigurationConstants.FIELD_NOT_MANDATORY%>');
	}
}
//Employee Code

//Applicant fields show On Site
function changeApplicantShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldWebsiteShow != '<%=ImportConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldWebsiteShow('<%=ImportConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldWebsiteShow('<%=ImportConfigurationConstants.FIELD_NOT_SHOW%>');
		_field.setFieldWebsiteMandatory('<%=ImportConfigurationConstants.FIELD_NOT_MANDATORY%>');
		changeApplicantMandatoryCheckBox(fieldId);
	}
}

function changeApplicantMandatoryCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldWebsiteMandatory != '<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldWebsiteMandatory('<%=ImportConfigurationConstants.FIELD_MANDATORY%>');
		_field.setFieldWebsiteShow('<%=ImportConfigurationConstants.FIELD_SHOW%>');
		changeApplicantShowCheckBox(fieldId);
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldWebsiteMandatory('<%=ImportConfigurationConstants.FIELD_NOT_MANDATORY%>');
	}
}

function changeConfidentialCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldConfidential != '<%=ImportConfigurationConstants.FIELD_CONFIDENTIAL%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldConfidential('<%=ImportConfigurationConstants.FIELD_CONFIDENTIAL%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldConfidential('<%=ImportConfigurationConstants.FIELD_NOT_CONFIDENTIAL%>');
	}
}


function changeShowCheckBox(fieldId){
	if($('img_'+fieldId+'_S')){
		$('img_'+fieldId+'_S').src=unchkedChkBoxSrc;
	}
}

function changeEditCheckBox(fieldId){
	if($('img_'+fieldId+'_E')){
		$('img_'+fieldId+'_E').src=chkedChkBoxSrc;
	}
}

function changeVendorShowCheckBox(fieldId){
	if($('img_'+fieldId+'_VS')){
		$('img_'+fieldId+'_VS').src=chkedChkBoxSrc;
	}
}

function changeVendorMandatoryCheckBox(fieldId){
	if($('img_'+fieldId+'_VM')){
		$('img_'+fieldId+'_VM').src=unchkedChkBoxSrc;
	}
}

function changeEmployeeShowCheckBox(fieldId){
	if($('img_'+fieldId+'_ES')){
		$('img_'+fieldId+'_ES').src=chkedChkBoxSrc;
	}
}

function changeEmployeeMandatoryCheckBox(fieldId){
	if($('img_'+fieldId+'_EM')){
		$('img_'+fieldId+'_EM').src=unchkedChkBoxSrc;
	}
}

function changeApplicantShowCheckBox(fieldId){
	if($('img_'+fieldId+'_AS')){
		$('img_'+fieldId+'_AS').src=chkedChkBoxSrc;
	}
}

function changeApplicantMandatoryCheckBox(fieldId){
	if($('img_'+fieldId+'_AM')){
		$('img_'+fieldId+'_AM').src=unchkedChkBoxSrc;
	}
}

function doChangeStepRank(id1, id2) {
	if (id1 != -1 && id2 != _fields.length) {
		_temp = _fields[id1];
		_fields[id1]=_fields[id2];
		_fields[id2]=_temp;
		
		displayFields();			
	}
}

function displayHeader(){
	if(view==undefined || view =='<%=AdminConstants.VIEW_TALENTPOOL%>'){
		$('pool').show();
		$('vendor').hide();
		$('employee').hide();
		$('site').hide();
	}else if(view=='<%=AdminConstants.VIEW_VENDOR%>'){
		$('pool').hide();
		$('vendor').show();
		$('employee').hide();
		$('site').hide();	
	}else if(view=='<%=AdminConstants.VIEW_EMPLOYEE%>'){
		$('pool').hide();
		$('vendor').hide();
		$('employee').show();
		$('site').hide();
	}else if(view=='<%=AdminConstants.VIEW_WEBSITE%>'){
		$('pool').hide();
		$('vendor').hide();
		$('employee').hide();
		$('site').show();
	}
}

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
			var showValue = _field.fieldImportShow;
			var editValue = _field.fieldEditShow;
			var mandatoryValue = _field.fieldImportMandatory;
			var vendorValue = _field.fieldVendorShow;
			var vendorMandatoryValue = _field.fieldVendorMandatory;
			
			var employeeValue = _field.fieldEmployeeShow;
			var employeeMandatoryValue = _field.fieldEmployeeMandatory;
			var applicantValue = _field.fieldWebsiteShow;
			var applicantMandatoryValue = _field.fieldWebsiteMandatory;
			var confidentialValue = _field.fieldConfidential;
			var isProcessField = _field.isProcessField;
						
			//var tBody= tbl.getElementsByTagName("tbody")[0];	
			var tBody=document.createElement("TBODY");
			//_field.setIndex(i);
			var tRow = document.createElement("TR");
	
			var tCell0 = document.createElement("TD");
			tCell0.style.width="150px";
			tCell0.style.height="20px";
			tCell0.className="label";
			tCell0.innerHTML = _field.fieldTitle;		
			tRow.appendChild(tCell0);
			
			if(view==undefined || view =='<%=AdminConstants.VIEW_TALENTPOOL%>'){
				
			var tCell1 = document.createElement("TD");
			tCell1.className="normal";
			tCell1.style.width="110px";
			tCell1.style.textAlign="center";
			tCell1.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell1.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EMAIL1%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EMAIL2%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_PHONE1%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_PHONE2%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_MOBILE%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>'){
				tCell1.innerHTML = '<img src="images/item_chk1_dis.gif" />';
			}else {
				innerHTML = '';				
				if(showValue=='<%=ImportConfigurationConstants.FIELD_SHOW%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_S" name="showName" onclick="changeShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_S" name="showName" onclick="changeShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				 tCell1.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell1);
			
			var tCell2 = document.createElement("TD");
			tCell2.className="normal";
			tCell2.style.width="110px";
			tCell2.style.textAlign="center";
			tCell2.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell2.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EMAIL1%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EMAIL2%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_PHONE1%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_PHONE2%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_MOBILE%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>' ||
				fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>'){
				tCell2.innerHTML = '<img src="images/item_chk1_dis.gif" />';	
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NOTE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>'){
				tCell2.innerHTML = '<img src="images/item_chk0_dis.gif" />';	
			}else{
				innerHTML = '';				
				if(editValue=='<%=ImportConfigurationConstants.FIELD_EDIT%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_E" name="showName" onclick="changeEditCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_E" name="showName" onclick="changeEditCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				 tCell2.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell2);
			
			var tCell3 = document.createElement("TD");
			tCell3.className="normal";
			tCell3.style.width="110px";
			tCell3.style.textAlign="center";
			tCell3.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell3.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>' ||
					 	fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>' ||
						fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>'){
				tCell3.innerHTML = '<img src="images/item_chk1_dis.gif" />';
			} else if(fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>'){
				tCell3.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else {
				innerHTML = '';				
				if(mandatoryValue=='<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_M" name="showName" onclick="changeMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_M" name="showName" onclick="changeMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				 tCell3.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell3);

			var tCell4 = document.createElement("TD");
			tCell4.className="normal";
			tCell4.style.width="110px";
			tCell4.style.textAlign="center";
			tCell4.style.wordWrap="break-word";
			tCell4.innerHTML = getConfidentialCellInnerHTML(fieldId,isProcessField,confidentialValue,i);
			tRow.appendChild(tCell4);
		}
		<%
		if (ModuleSet.isMODULE_VENDOR()){
		%>	
		if(view=='<%=AdminConstants.VIEW_VENDOR%>'){
			var tCell4 = document.createElement("TD");
			tCell4.className="normal";
			tCell4.style.width="150px";
			tCell4.style.textAlign="center";
			tCell4.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell4.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>'||
					fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>' ){
				tCell4.innerHTML = '<img src="images/item_chk1_dis.gif" />';	
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>'){
				tCell4.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			} else{
				innerHTML = '';				
				if(vendorValue=='<%=ImportConfigurationConstants.FIELD_SHOW%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_VS" name="showName" onclick="changeVendorShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_VS" name="showName" onclick="changeVendorShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				 tCell4.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell4);
		
			var tCell5 = document.createElement("TD");
			tCell5.className="normal";
			tCell5.style.width="150px";
			tCell5.style.textAlign="center";
			tCell5.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell5.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>'||
					fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>'){
				tCell5.innerHTML = '<img src="images/item_chk1_dis.gif" />';	
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>'){
				tCell5.innerHTML = '<img src="images/item_chk0_dis.gif" />';	
			}else{
				innerHTML = '';				
				if(vendorMandatoryValue=='<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_VM" name="showName" onclick="changeVendorMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_VM" name="showName" onclick="changeVendorMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				 tCell5.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell5);
		}
		<%}%>	
		//Employee Code
		<%
		if (ModuleSet.isMODULE_EMPLOYEE()){
		%>	
		if(view=='<%=AdminConstants.VIEW_EMPLOYEE%>'){
			var tCell6 = document.createElement("TD");
			tCell6.className="normal";
			tCell6.style.width="150px";
			tCell6.style.textAlign="center";
			tCell6.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell6.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>'||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>' ){
				tCell6.innerHTML = '<img src="images/item_chk1_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>' ){
				tCell6.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			} else{
				innerHTML = '';				
				if(employeeValue=='<%=ImportConfigurationConstants.FIELD_SHOW%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_ES" name="showName" onclick="changeEmployeeShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_ES" name="showName" onclick="changeEmployeeShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				 tCell6.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell6);
		
			var tCell7 = document.createElement("TD");
			tCell7.className="normal";
			tCell7.style.width="150px";
			tCell7.style.textAlign="center";
			tCell7.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell7.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>'||
				fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>'){
				tCell7.innerHTML = '<img src="images/item_chk1_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>' ){
				tCell7.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else{
				innerHTML = '';				
				if(employeeMandatoryValue=='<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_EM" name="showName" onclick="changeEmployeeMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_EM" name="showName" onclick="changeEmployeeMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				 tCell7.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell7);
		}
		<%}%>

		<%
		if (ModuleSet.isMODULE_WEB_INTEGRATION()){
		%>
		if(view=='<%=AdminConstants.VIEW_WEBSITE%>'){
			var tCell8 = document.createElement("TD");
			tCell8.className="normal";
			tCell8.style.width="150px";
			tCell8.style.textAlign="center";
			tCell8.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell8.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>'||
						fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_EMAIL1%>'){
				tCell8.innerHTML = '<img src="images/item_chk1_dis.gif" />';	
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>'){
				tCell8.innerHTML = '<img src="images/item_chk0_dis.gif" />';	
			} else{
				innerHTML = '';	
				if(applicantValue=='<%=ImportConfigurationConstants.FIELD_SHOW%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_AS" name="showName" onclick="changeApplicantShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_AS" name="showName" onclick="changeApplicantShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				tCell8.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell8);
		
			var tCell9 = document.createElement("TD");
			tCell9.className="normal";
			tCell9.style.width="150px";
			tCell9.style.textAlign="center";
			tCell9.style.wordWrap="break-word";
			if(isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>'){
				tCell9.innerHTML = '<img src="images/item_chk0_dis.gif" />';
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_NAME%>'||
						fieldId=='<%=ImportConfigurationConstants.FIELD_EXPERIENCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_EMAIL1%>'){
				tCell9.innerHTML = '<img src="images/item_chk1_dis.gif" />';	
			}else if(fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>' || fieldId=='<%=ImportConfigurationConstants.FIELD_Confidential%>'){
				tCell9.innerHTML = '<img src="images/item_chk0_dis.gif" />'
			}else{
				innerHTML = '';				
				if(applicantMandatoryValue=='<%=ImportConfigurationConstants.FIELD_MANDATORY%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_AM" name="showName" onclick="changeApplicantMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_AM" name="showName" onclick="changeApplicantMandatoryCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				tCell9.innerHTML = innerHTML;	
			}	
			tRow.appendChild(tCell9);
		}
		<%}%>
		
		
		if(view==undefined || view =='<%=AdminConstants.VIEW_TALENTPOOL%>'){
			var tCell8 = document.createElement("TD");
			tCell8.className="normal";
			tCell8.style.width="100px";
			tCell8.style.wordWrap="break-word";
			innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: doChangeStepRank(' + (i - 1) + ', ' + i + ');"/>';		
			innerHTML += '&nbsp;&nbsp;&nbsp;';
			innerHTML += '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: doChangeStepRank(' + i + ', ' + (i + 1) + ');"/>';		
			tCell8.innerHTML = innerHTML;		
			tRow.appendChild(tCell8);						
		}else{
			var tCell8 = document.createElement("TD");
			tCell8.className="normal";
			tCell8.style.width="260px";
			innerHTML = '&nbsp;';		
			tCell8.innerHTML = innerHTML;		
			tRow.appendChild(tCell8);
		}
			tBody.appendChild(tRow);
			tbl.appendChild(tBody);
		}
	}	
}

function getConfidentialCellInnerHTML(fieldId,isProcessField,confidentialValue,index){
	var innerHTML = ''; 
	if(fieldId=='<%=ImportConfigurationConstants.FIELD_SOURCE%>'
		|| fieldId=='<%=ImportConfigurationConstants.FIELD_CURRENT_CTC%>'
		|| fieldId=='<%=ImportConfigurationConstants.FIELD_EXPECTED_CTC%>'  
		|| isProcessField=='<%=ImportConfigurationConstants.FIELD_IS_PROCESS_FIELD%>' ){
		  if(confidentialValue=='<%=ImportConfigurationConstants.FIELD_CONFIDENTIAL%>'){ 
	  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_M" name="showName" onclick="changeConfidentialCheckboxState(this,\''+fieldId+'\', '+index+');" />';
		  }else{
		  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_M" name="showName" onclick="changeConfidentialCheckboxState(this,\''+fieldId+'\', '+index+');" />'
		  }
		innerHTML = innerHTML;
	} else {
		innerHTML = '<img src="images/item_chk0_dis.gif" />';
	}
	return innerHTML;	
} 

function submitForm(){
	var frm=document.adminForm;
	frm.importFieldsString.value = _fields.toString();
	frm.submit();
}

function doOnLoad() {
	displayFields();
}

window.onload=doOnLoad;

</script>
