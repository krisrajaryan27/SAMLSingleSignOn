<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
                  com.talentPool.custom.constants.CustomFieldConstants,com.talentPool.common.properties.TPApplicationProperties,com.talentPool.common.utils.Utils"%>
<link rel="STYLESHEET" type="text/css" href="themes/default/Context.css">     
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>            
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/cookies.js"></script>
<style>
<!--
html{
scrollbar-arrow-color:#99CC33;scrollbar-3dlight-color:#FFFFFF;	scrollbar-darkshadow-color:#FFFFFF;	scrollbar-face-color:#FFFFFF;	scrollbar-highlight-color:#99CC33;	scrollbar-shadow-color:#99CC33;	scrollbar-track-color:#F2F2F2;
}
-->
</style>	
<logic:present name="update" scope="request">	
	<script>
		var returnVal = '<bean:write property="entityType" name="customFieldForm"/>';
		window.top.hidePopWin(true);
	</script>
</logic:present> 
<logic:notPresent name="update" scope="request">
<div class="contentDivPop" style="width: 600px;">
	<% if(request.getAttribute(Globals.ERROR_KEY)!=null){ %>
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
		<br>
	<% } %>
		<% if(request.getAttribute("success")!=null){ %>
		<table  id="m_errortable" > 
			<tr>
			    <td class="header">
			        <b>Saved</b>
			    </td>               
			</tr>
		</table>
		<br>
	<% } %>
	<div id="informationPlaceHolder" class="message"></div>
	<div class="outerDiv">
	<html:form action="/customFieldScreen">
	<html:hidden property="mode" name="customFieldForm" value="saveCustomField"/>				
	<html:hidden property="entityType" name="customFieldForm"/>	
	<html:hidden property="customFieldId" name="customFieldForm"/>
	<html:hidden property="customFieldType" name="customFieldForm"/>
	<html:hidden property="customFieldOptions" name="customFieldForm"/>
	<html:hidden property="customFieldRequired" name="customFieldForm"/>
	<html:hidden property="customFieldSearchable" name="customFieldForm"/>	
	<html:hidden property="customFieldTableColumnIds" name="customFieldForm"/>
	
	<div class="popupTop">
	<table class="tblPop" >
		<tr>
			<td nowrap="nowrap" width="150px;"><bean:message key="admin.custom_fields.label.field_for"/></td>
		    <td>
		    	<script language="JavaScript">
			    	var selectBoxEntityType = null;
			    	
			    	var opts = new Array();
			    	var m = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
			    	opts = opts.concat(m);
			    	
			    	var isEntityTypeTable = '<bean:write property="entityType" name="customFieldForm" />';
			    	if(isEntityTypeTable == '<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE%>'){
			    		m = [new SelectOption('<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE%>','Applicant Table')];
				    	opts = opts.concat(m);
			    	}else{
			    		m = [new SelectOption('<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT%>','Applicant')];
				    	opts = opts.concat(m);
				    	m = [new SelectOption('<%=CustomFieldConstants.ENTITY_TYPE_POSITION%>','Position')];
				    	opts = opts.concat(m);	
				    	m = [new SelectOption('<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD%>','Applicant Table Field')];
				    	opts = opts.concat(m);
			    	}
					selectBoxEntityType = new SelectBox(opts,'<bean:write property="entityType" name="customFieldForm" />','images/btn_dropdown.gif',{namesonly:false, width:'150px', size:20});
					document.write(selectBoxEntityType.getHtml());
					selectBoxEntityType.setOnChangeHandler('showHideSearchableRow');
	                selectBoxEntityType.init();
	                var selectBoxEntityTypeId =  selectBoxEntityType.getSelectedId();
		    	</script>
		    </td>
        </tr>	
        <tr>
			<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.name"/></td>
		    <td><html:text property="customFieldName"  styleId="customFieldName" name="customFieldForm" maxlength="250"/></td>
        </tr>
       <logic:notEqual name="customFieldForm" property="entityType" value='<%=""+CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE%>'>
       <tr>
			<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.display_name"/></td>
		    <td><html:text property="customFieldDisplayName" styleId="customFieldDisplayName" name="customFieldForm" maxlength="250"/></td>
        </tr>
        <tr>
			<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.type"/></td>
		    <td>
		    	<script language="JavaScript">
			    	var selectBoxType = null;

					var opts = new Array();
			    	var m = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_TEXT%>','<%=CustomFieldConstants.TYPE_TEXT%>')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_NUMBER%>','<%=CustomFieldConstants.TYPE_NUMBER%>')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_DATE%>','<%=CustomFieldConstants.TYPE_DATE%>')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_DROPDOWN%>','<%=CustomFieldConstants.TYPE_DROPDOWN%>')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_TEXTAREA%>','<%=CustomFieldConstants.TYPE_TEXTAREA%>')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_RADIO%>','<%=CustomFieldConstants.TYPE_RADIO%>')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_LISTBOX%>','<%=CustomFieldConstants.TYPE_LISTBOX%>')];
			    	opts = opts.concat(m);
			    	m = [new SelectOption('<%=CustomFieldConstants.TYPE_CHECKBOX%>','<%=CustomFieldConstants.TYPE_CHECKBOX%>')];
			    	opts = opts.concat(m);
				    	
					selectBoxType = new SelectBox(opts,'<bean:write property="customFieldType" name="customFieldForm" />','images/btn_dropdown.gif',{namesonly:false, width:'113px', size:20});
					document.write(selectBoxType.getHtml());
					selectBoxType.setOnChangeHandler('onChangeFilter');
	                selectBoxType.init();
		    	</script>
		    </td>
        </tr>
        <tr>
			<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.attributes"/></td>
		    <td><html:text property="customFieldAttributes" name="customFieldForm" size="42"/></td>
        </tr>
        <tr>
			<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.other_attributes"/></td>
		    <td><html:text property="customFieldOtherAttributes" name="customFieldForm" size="42"/></td>
        </tr>	
        <tr>
			<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.default_value"/></td>
		    <td><html:text property="customFieldDefaultValue" name="customFieldForm" maxlength="250"/></td>
        </tr>
        <tr id="selectOptionRow1" style="display:none;">
			<td nowrap="nowrap" style="vertical-align:top;"><bean:message key="admin.custom_fields.label.options"/></td>
		    <td><div id="optionsGridBox" style="width:220px;height:100px;"></div><br/></td>			    
        </tr>
        <tr id="selectOptionRow2" style="display:none;">
        	<td>&nbsp;</td>
        	<td>
        		<table width="100%">
		    		<tr>
		    			<td><input id="optionText" type="text" size="30" /></td>
		    			<td><a href="#" style="width:56px;margin-left:5px;" class="btn3" onclick="javascript:addOption($('optionText').value);$('optionText').value='';"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.add"/></a></td>
		    		</tr>
		    	</table>   	
		    </td>
        </tr>  
        <tr>
			<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.required"/></td>
		    <td height="17"><img src="images/checkboxunchecked.gif" id="isRequired" onclick="javascript: toggleCheckBox(this);"/></td>
        </tr>
        
        <tr id="searchableRow" style="display: block;">
      		<td nowrap="nowrap"><bean:message key="admin.custom_fields.label.searchable"/></td>
		    <td height="17"><img src="images/checkboxunchecked.gif" id="isSearchable" onclick="javascript: toggleCheckBox(this);"/></td>
        </tr>
        </logic:notEqual>
	</table>
	 <logic:equal name="customFieldForm" property="entityType" value='<%=""+CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE%>'>
	<table>
		<tr id="fieldsDiv">
		<td>
			<table cellpadding="0" cellspacing="0" class="innerReport">
				<tr>
					<td >
						<table cellpadding="0" cellspacing="0" style="padding-left: 6px;">
							<tr>
								<td class="gridborder">
									<div id="FIELDS_GRID" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td width="34px" align="center">
						<logic:notPresent name="customFieldForm" property="customFieldId">
						<a href="#" onclick="javascript: selectItem(fieldsGrid,selectedFieldsGrid);return false;" title="Add" ><img src="images/ico_rightarrow.gif"  border="0" /></a><br/>
						<a href="#" onclick="javascript: deselectItem(selectedFieldsGrid,fieldsGrid);return false;" title="Remove" ><img src="images/ico_leftarrow.gif"  border="0" style="margin-top: 10px;"/></a>
						</logic:notPresent> 
					</td>
					<td>
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td class="gridborder">
									<div id="FIELDS_GRID_SELECTED" style="width:225px;height: 150px;overflow: visible;"></div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
	</logic:equal>
	</div>
	<div class="popupBody">
	<table class="tblPop" width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<div class="navBtn" style="float:right;margin-left:5px;margin-top:5px;"><a href="#" style="width:50px;" class="active" onclick="javascript: submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a></div>
		</td>
	</tr>
	</table>  
	</div> 
</html:form>
</div>
</div>
<script>
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

function getValBySrc(obj) {
	if(obj){
		var src = obj.src;
		if (src.indexOf(deselectedCheckBox) != -1) {
			return 0;
		} else {
			return 1;
		}
	}
}
   
function submitForm(){
	errors = validateForm();
	if(errors.length > 0) {
		alert(errors);
		return false;
	}
	if(isTableFlag){
		document.customFieldForm.mode.value='saveTabularCustomField';
	}else{
		document.customFieldForm.entityType.value = selectBoxEntityType.getSelectedId();
		document.customFieldForm.customFieldType.value = selectBoxType.getSelectedId();
		document.customFieldForm.customFieldRequired.value = getValBySrc($("isRequired"));
		document.customFieldForm.customFieldSearchable.value = getValBySrc($("isSearchable"));
		var optionsStr = '';
		for(var i=0;i<optionsGridBox.getRowsNum();i++){ 
			var val = optionsGridBox.cells2(i,1).getValue();		
			optionsStr += '{' + val + '|' + val + '}';
		}
		document.customFieldForm.customFieldOptions.value = optionsStr;
	}
	document.customFieldForm.submit();
	return true;
}

function setSelectedFields(){
	var selectedFields = selectedFieldsGrid.getAllItemIds(',');
	var arr =  selectedFields.split(",");
	var selectedFieldNames='';
	if(arr.length==0){
		return false;
	}
	for(var i=0;i<arr.length;i++){
		selectedFieldNames = selectedFieldNames + selectedFieldsGrid.getUserData(arr[i],"key")+",";
	}
	selectedFieldNames= selectedFieldNames.replace(/,+$/, "");
	document.customFieldForm.customFieldTableColumnIds.value=selectedFieldNames;
	return true;
}

function validateForm() {
	var errors = '';
	if(selectBoxEntityType.getSelectedId() == -1) {
		errors = addError(errors, '- Field For');
	}
	var val = $("customFieldName").value;
	if(val.trim() == '') {
		errors = addError(errors, '- Field Name');
	}
	if(isTableFlag){
		if(!setSelectedFields())
			errors = addError('Please select custom fields to map', errors);
	}else{
		if(val.trim().indexOf(" ") != -1) {
			errors = addError(errors, '- <bean:message key="admin.custom_fields.error.field_name_nospace"/>');
		}
		if(!validName($("customFieldName"))){
			errors = addError(errors, '- Please Enter Alphabet with combination of _ Only');			
		}
		val = $("customFieldDisplayName").value;	
		if(val.trim() == '') {
			errors = addError(errors, '- Field Display Name');
		}
		if(selectBoxType.getSelectedId() == -1) {
			errors = addError(errors, '- Field Type');
		}
		if(selectBoxEntityType.getSelectedId() == '<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD%>'){
			if(!(selectBoxType.getSelectedId()=='<%=CustomFieldConstants.TYPE_TEXT%>' || selectBoxType.getSelectedId() =='<%=CustomFieldConstants.TYPE_DATE%>')){
				errors = addError(errors,'Only text or date (dd-MM-YYYY) type can be selected for type applicant table field');
			}
		}
		if(!validateCharacters($("customFieldName").value) ||
				!validateCharacters($("customFieldDisplayName").value)  ){
			errors = addError('Following characters are not allowed: < > \" \' % ; ) ( &', errors);
		}
	}
	if(errors.trim() != '') {
		errors = addError('Following data is required:', errors);		
	}
	return errors;
}

function validName(fieldObj){
	var regExpJavaName = /^[a-zA-Z]*([_]*[a-zA-Z]|[$]*[a-zA-Z])*[a-zA-Z]$/;
	var val;		
	if(fieldObj.value.trim()==""){				
		val= false;
	}else if(!fieldObj.value.trim().match(regExpJavaName)){		
		//alert("Please Enter Alphabet with combination of '_' Only");
		fieldObj.focus();
		val= false;
	}else{
		val= true;
	}	
	return val;
}

function addError(errors, error) {
	if(errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}

function addOption(val) {
	if(val != null && val.trim() != '') {				
		optionsGridBox.addRow(cnt,'string');
		optionsGridBox.cells(cnt,0).setValue('<img src="images/ico_delete.gif" title="Delete" alt="Delete" style="cursor:pointer;" onclick="javascript:deleteOpt('+cnt+');" />');
		optionsGridBox.cells(cnt,1).setValue(val);
		cnt++;
	}
}

function deleteOpt(rowId) {
	optionsGridBox.deleteRow(rowId);
}

function setPopupTitle(){
	<logic:empty name="customFieldForm" property="customFieldId">
	  title = '<b>Add Custom Field</b>';
	</logic:empty>
	<logic:notEmpty name="customFieldForm" property="customFieldId">
	  title = '<b>Edit Custom Field</b>';
	</logic:notEmpty>
	window.top.setPopTitle(title);
}

var fieldsGrid = null;
var selectedFieldsGrid = null;
function initFieldsGrid() {	
	fieldsGrid = new dhtmlXGridObject('FIELDS_GRID'); 
	fieldsGrid.imgURL = "images/"; 
	fieldsGrid.setHeader("<bean:message key="common.fields"/>");
	fieldsGrid.setNoHeader(true);
	fieldsGrid.setInitWidths("200");
	fieldsGrid.setColAlign("left");
	fieldsGrid.setColTypes("ro"); 
	fieldsGrid.setColSorting("str");
	fieldsGrid.enableMultiselect(true);	
	fieldsGrid.attachEvent("onXLE",doOnFieldsGridLoadingEnd);	     
	fieldsGrid.attachEvent("onKeyPress",onFieldsGridKeyPressed);
	fieldsGrid.attachEvent("onRowDblClicked",doOnFieldsGridRowDblClicked);
	fieldsGrid.attachEvent("onRowSelect",doOnFieldsGridRowSelectHandler);
	fieldsGrid.init();
	loadFieldsGrid();
	fieldsGrid.setSortImgState(true,0,"ASC");
	
	fieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function initSelectedFieldsGrid(){
	selectedFieldsGrid = new dhtmlXGridObject('FIELDS_GRID_SELECTED'); 
	selectedFieldsGrid.imgURL = "images/"; 
	selectedFieldsGrid.setHeader("<bean:message key="common.selected"/> <bean:message key="common.fields"/>");
	selectedFieldsGrid.setNoHeader(true);
	selectedFieldsGrid.setInitWidths("200");
	selectedFieldsGrid.setColAlign("left");
	selectedFieldsGrid.setColTypes("ro"); 
	selectedFieldsGrid.enableMultiselect(true);
	selectedFieldsGrid.init();     
	
	selectedFieldsGrid.attachEvent("onKeyPress",onSelectedFieldsGridPressed);
	selectedFieldsGrid.attachEvent("onRowDblClicked",doOnSelectedFieldsGridRowDblClicked);
	selectedFieldsGrid.attachEvent("onRowSelect",doOnSelectedFieldsRowSelectHandler);
	selectedFieldsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
}

function onFieldsGridKeyPressed(keyCode,ctrl,shift) {
	selectedFieldsGrid.clearSelection();
	onGridObjKeyPressed(fieldsGrid,selectedFieldsGrid,4,keyCode,ctrl,shift);
}

function onSelectedFieldsGridPressed(keyCode,ctrl,shift) {
	fieldsGrid.clearSelection();
	onGridObjKeyPressed(selectedFieldsGrid,fieldsGrid,4,keyCode,ctrl,shift);
}

function doOnFieldsGridRowDblClicked() {
	selectItem(fieldsGrid,selectedFieldsGrid);
}
function doOnSelectedFieldsGridRowDblClicked() {
	deselectItem(selectedFieldsGrid,fieldsGrid);
}

function doOnFieldsGridRowSelectHandler() {
	selectedFieldsGrid.clearSelection();
}

function doOnSelectedFieldsRowSelectHandler() {
	fieldsGrid.clearSelection();
}

function loadFieldsGrid(){
	var pars = "mode=fetchCustomFieldsForTableXML&customFieldId=" + document.customFieldForm.customFieldId.value;
	var myAjax = ajaxCall("customFieldScreen.do","get",pars,renderFieldsGrid,reportError);
	
}

function renderFieldsGrid(request){
	var xmlFile = request.responseXML;
	fieldsGrid.clearAll();
	fieldsGrid.parse(xmlFile);
}

function doOnFieldsGridLoadingEnd(grid,count){
	var tmp = alreadySelectedIds.split(',');
	fieldsGrid.forEachRow(function(id){
		var key = fieldsGrid.getUserData(id,"key");
		if(tmp.indexOf(key) !== -1){
			fieldsGrid.setSelectedRow(id,true,false,false);		
		}
	});
	selectItem(fieldsGrid,selectedFieldsGrid);
}
var isTableFlag=false;
var entityTypeId;
var alreadySelectedIds;
function doOnLoad() {
  	setPopupTitle();
  	entityTypeId = '<bean:write property="entityType" name="customFieldForm" />';
  	if(entityTypeId  == '<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE%>'){
  		isTableFlag=true;
  		initFieldsGrid();
		initSelectedFieldsGrid();
		alreadySelectedIds ='<bean:write property="customFieldTableColumnIds" name="customFieldForm" />';
  	}else{
  		setOptionsGrid();
  	  	showHideSearchableRow();
  	  	showRow1and2();
  	  	
  	  	<logic:equal property="customFieldRequired" name="customFieldForm" value="1">
  	  		toggleCheckBox($("isRequired"));
  	  	</logic:equal>
  	  	<logic:equal property="customFieldSearchable" name="customFieldForm" value="1">
  	  		toggleCheckBox($("isSearchable"));
  	  	</logic:equal>
  	  	var optionsStr = '<bean:write property="customFieldOptions" name="customFieldForm" />';
  	  	var parts = optionsStr.split('}');
  	  	for(var kk=0; kk < parts.length; kk++) {
  	  		if(parts[kk].trim() != '') {
  	  			var temp = parts[kk].split('|');
  	  			addOption(temp[1].trim());
  	  		}
  	  	}
  	}
}

function showRow1and2(){
	val = '<bean:write property="customFieldType" name="customFieldForm" />';
	row1 = $('selectOptionRow1');
	row2 = $('selectOptionRow2');
	if(val=='<%=CustomFieldConstants.TYPE_DROPDOWN%>' ||
		val=='<%=CustomFieldConstants.TYPE_RADIO%>' ||
		val=='<%=CustomFieldConstants.TYPE_LISTBOX%>' ||
		val=='<%=CustomFieldConstants.TYPE_CHECKBOX%>'){		
		Element.show(row1);				
		Element.show(row2);	
	}else {
		Element.hide(row1);				
		Element.hide(row2);	
	}
}

function showHideSearchableRow(){
	valOptions = selectBoxEntityType.getSelectedId();
	if(valOptions=='<%=CustomFieldConstants.ENTITY_TYPE_POSITION%>'){
			if($('searchableRow')){
				Element.hide($('searchableRow'));	
			}
	}else{
		Element.show($('searchableRow'));
	}
	if(valOptions=='<%=CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD%>'){
		document.getElementById('informationPlaceHolder').innerHTML='Please select only date(dd-MM-yyyy) or text field type for type Applicant Table Field';
	}else{
		document.getElementById('informationPlaceHolder').innerHTML='';
	}
}


function onChangeFilter(val){	
	valOptions = selectBoxType.getSelectedId();		
	row1 = $('selectOptionRow1');
	row2 = $('selectOptionRow2');
	if(valOptions=='-1'){				
		Element.hide(row1);				
		Element.hide(row2);			
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_TEXT%>'){
		attrib='<%=CustomFieldConstants.TYPE_TEXT_ATTRIBUTES%>';
		document.customFieldForm.customFieldAttributes.value=attrib;
		document.customFieldForm.customFieldOtherAttributes.value="";
		Element.hide(row1);				
		Element.hide(row2);				
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_NUMBER%>'){
		attrib='<%=CustomFieldConstants.TYPE_NUMBER_ATTRIBUTES%>';		
		document.customFieldForm.customFieldAttributes.value=attrib;
		document.customFieldForm.customFieldOtherAttributes.value="";	
		Element.hide(row1);				
		Element.hide(row2);	
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_DATE%>'){	
		attrib='<%=CustomFieldConstants.TYPE_DATE_ATTRIBUTES%>';
		document.customFieldForm.customFieldAttributes.value=attrib;
		document.customFieldForm.customFieldOtherAttributes.value='<%=CustomFieldConstants.TYPE_DATE_OTHER_ATTRIBUTES%>';
		Element.hide(row1);				
		Element.hide(row2);	
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_DROPDOWN%>'){		
		attrib='<%=CustomFieldConstants.TYPE_DROPDOWN_ATTRIBUTES%>';
		document.customFieldForm.customFieldAttributes.value=attrib;
		document.customFieldForm.customFieldOtherAttributes.value="";	
		Element.show(row1);				
		Element.show(row2);	
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_TEXTAREA%>'){
		attrib='<%=CustomFieldConstants.TYPE_TEXTAREA_ATTRIBUTES%>';	
		document.customFieldForm.customFieldAttributes.value=attrib;
		document.customFieldForm.customFieldOtherAttributes.value="";		
		Element.hide(row1);				
		Element.hide(row2);	
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_RADIO%>'){
		attrib='<%=CustomFieldConstants.TYPE_RADIO_ATTRIBUTES%>';
		document.customFieldForm.customFieldAttributes.value=attrib;
		document.customFieldForm.customFieldOtherAttributes.value="";		
		Element.show(row1);				
		Element.show(row2);	
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_LISTBOX%>'){
		attrib='<%=CustomFieldConstants.TYPE_LISTBOX_ATTRIBUTES%>';
		document.customFieldForm.customFieldAttributes.value=attrib;
		document.customFieldForm.customFieldOtherAttributes.value="";		
		Element.show(row1);				
		Element.show(row2);	
	}else if(valOptions=='<%=CustomFieldConstants.TYPE_CHECKBOX%>'){
		attrib='<%=CustomFieldConstants.TYPE_CHECKBOX_ATTRIBUTES%>';		
		document.customFieldForm.customFieldAttributes.value=attrib;		
		document.customFieldForm.customFieldOtherAttributes.value="";
		Element.show(row1);				
		Element.show(row2);	
	}else{
		Element.hide(row1);				
		Element.hide(row2);	
	}
}

var optionsGridBox;
var cnt=0;
function setOptionsGrid() {
	optionsGridBox = new dhtmlXGridObject('optionsGridBox'); 
	optionsGridBox.imgURL = "images/"; 
	optionsGridBox.setHeader("&nbsp;,<bean:message key='admin.custom_fields.label.option' />");
	optionsGridBox.setInitWidths("20,180");
	optionsGridBox.setColAlign("left,left");
	optionsGridBox.setColTypes("ro,ro"); 
	optionsGridBox.setColSorting("na,cstr");
	optionsGridBox.init();
}
window.onload = doOnLoad;
</script>
</logic:notPresent>													