<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<%@page import="com.talentPool.positions.dataobject.PositionFieldData"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/doClasses/PositionListScreenConfigarationClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/PositionDetailsScreenConfigarationClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script src="js/ajaxfunctions.js"></script>
<script src="js/cookies.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>

<script language="JavaScript">
	var _fields = new Array();
	<logic:notEmpty property="positionListFieldList" name="adminForm">
		<logic:iterate id="field" property="positionListFieldList" name="adminForm" type="PositionFieldData" indexId="counter">
			var _field = new ListConfField();
			_field.setIndex('<bean:write name="counter" />');
			_field.setFieldId('<%=field.getFieldId()%>');
			_field.setFieldTitle('<%=field.getFieldTitle()%>');
			_field.setFieldType('<%=field.getFieldType()%>');
			_field.setFieldOnPositionListShow('<%=field.getFieldOnPositionListShow()%>');
			_field.setIsFilter('<%=field.getFieldIsFilter()%>');
			_field.setIsFilterEditable('<%=field.getFieldIsFilterEditable()%>');
			_fields[_fields.length]=_field;
		</logic:iterate>
	</logic:notEmpty>	

	var _fieldsDetails = new Array();
	<logic:notEmpty property="positionDetailsFieldList" name="adminForm">
		<logic:iterate id="field1" property="positionDetailsFieldList" name="adminForm" type="PositionFieldData" indexId="counter">
			var _field1 = new DetailsConfField();
			_field1.setIndex('<bean:write name="counter" />');
			_field1.setFieldId('<%=field1.getFieldId()%>');
			_field1.setFieldTitle('<%=field1.getFieldTitle()%>');
			_field1.setFieldType('<%=field1.getFieldType()%>');
			_field1.setFieldOnPositionDetailsShow('<%=field1.getFieldOnPositionDetailsShow()%>');
			_fieldsDetails[_fieldsDetails.length]=_field1;
		</logic:iterate>
	</logic:notEmpty>	
</script>


<html:form action="/adminHome">
	<html:hidden property="t" name="adminForm"/>
	<html:hidden property="mode"/>
	<html:hidden property="positionListFieldsString" name="adminForm"/>
	<html:hidden property="positionDetailsFieldsString" name="adminForm"/>
	<html:hidden property="isShowLabel" name="adminForm"/>
	<html:hidden property="firstLineFieldId" name="adminForm"/>
	<html:hidden property="isOtherField" name="adminForm"/>
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
	<table>
		<tr>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
				<td style="vertical-align: bottom;">
					<table cellpadding="0" cellspacing="0" class="boxETab">
					  <tr>
						  <td class="leftC"></td>
						  <td class="content"><bean:message key="common.position" />&nbsp;<bean:message key="admin_website_screen_configuration.label.position_list_screen_configuration" /></td>
						  <td class="rightC"></td>
					  </tr>
				  	</table>
			  	</td>			  
			</tr>
		</table>
		
		<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
			<tr>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.fields" /></td>
				<% 
				if (ModuleSet.isMODULE_WEB_INTEGRATION()){
				%>
					<td class="header" height="18" style="width:100px;"><bean:message key="common.show" /> ?</td>
					<td class="header" height="18" style="width:100px;"><bean:message key="common.filters" /> ?</td>
				<%} %>
				<td class="header" height="18" style="width:70px;"><bean:message key="common.sequence" /></td>
			</tr>
		</table>
		<div id="fldDiv" class="outerDiv" style="padding:0px; width: 374px;">
				<table id="fldTable" cellspacing="0" cellpadding="0" class="boxContent" style="border:0px;">
				</table>
		</div>
		</td>
		<td style="padding-left: 15px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
				<td style="vertical-align: bottom;">
					<table cellpadding="0" cellspacing="0" class="boxETab">
					  <tr>
						  <td class="leftC"></td>
						  <td class="content"><bean:message key="common.position" />&nbsp;<bean:message key="admin_website_screen_configuration.label.position_details_screen_configuration" /></td>
						  <td class="rightC"></td>
					  </tr>
				  	</table>
			  	</td>			  
			</tr>
		</table>
		<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
			<tr>
				<td class="header" height="18" style="width:150px;"><bean:message key="common.fields" /></td>
				<% 
				if (ModuleSet.isMODULE_WEB_INTEGRATION()){
				%>
					<td class="header" height="18" style="width:100px;"><bean:message key="common.show" /> ?</td>
				<%} %>
				<td class="header" height="18" style="width:70px;"><bean:message key="common.sequence" /></td>
			</tr>
		</table>
		<div id="fldDetailsDiv" class="outerDiv" style="padding:0px; width: 344px;">
				<table id="detailsFldTable" cellspacing="0" cellpadding="0" class="boxContent" style="border:0px;">
				</table>
		</div>
		</td>
		</tr>
		<tr>
			<td align="right">
				<a class="green" onclick="javascript:setPostionHomeHeader();" href="#"><bean:message key="admin_website_screen_configuration.label.filters_description" /></a>
			</td>
		</tr>
		</table>
		<table>
			<tr>
			<td><bean:message key="admin_website_screen_configuration.label.show_labels_on" />&nbsp;<bean:message key="common.position" />&nbsp;<bean:message key="admin_website_screen_configuration.label.list_page" /> :</td>
			<td style="padding-left: 15px;">
				<logic:equal name="adminForm" property="isShowLabel" value="<%=PositionConfigurationConstants.LABEL_SHOW%>">
					<img src="images/checkboxchecked.gif" name="labelShowChk" id='labelShowChk' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
				</logic:equal>
				<logic:equal name="adminForm" property="isShowLabel" value="<%=PositionConfigurationConstants.LABEL_DO_NOT_SHOW%>">
					<img src="images/checkboxunchecked.gif" name="labelShowChk" id='labelShowChk' onclick="javascript:toggleSource(this);" style="margin-bottom:-1px;"/>
				</logic:equal>
			</td>
			</tr>
		</table>
		<br/>
		
		<table>
			<tr>
			<td><bean:message key="admin_website_screen_configuration.label.first_line_field_on" />&nbsp;<bean:message key="common.positions" />&nbsp;<bean:message key="common.list" /></td>
			<td style="padding-left: 15px;">
				<script language="JavaScript">
					var opts = <bean:write name="adminForm" property="jsArrayPositionFields" filter="false"/>;
					var opt = [new SelectOption('-1','<bean:message key="common.selectlist.others" />')];
					opts = opts.concat(opt);											
					selectBoxField = new SelectBox(opts,'<bean:write name="adminForm" property="firstLineField" filter="false"/>','images/btn_dropdown.gif',{namesonly:false, width:'180px', size:15});
					document.write(selectBoxField.getHtml());
					selectBoxField.setOnChangeHandler('showDiv');
					selectBoxField.init();
				</script>
			</td>
			<td>
				<div id="otherValueDiv" style="display: none;">
						<html:text name="adminForm" property="otherFieldValue" size="50"/>	
				</div> 			
			</td>
			</tr>
		</table>
		
		<div class="navBtn" style="margin-top:5px;"><a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();">
		<span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
		<span class="grey">&nbsp;&nbsp;&nbsp;Note: Please restart the TalentPool service to make changes effective for Website.</span>
		</div>
		<br/>
		<br/>
	</div>
</html:form>

<script language="javascript">

var chkedChkBoxSrc='images/checkboxchecked.gif';
var unchkedChkBoxSrc='images/checkboxunchecked.gif';

function toggleSource(obj) {
	var source = obj.src;	
	if (source.indexOf(chkedChkBoxSrc) != -1) {
		obj.src = unchkedChkBoxSrc;
		document.adminForm.isShowLabel.value="<%=PositionConfigurationConstants.LABEL_DO_NOT_SHOW%>";
	} else {
		obj.src = chkedChkBoxSrc;
		document.adminForm.isShowLabel.value="<%=PositionConfigurationConstants.LABEL_SHOW%>";
	}
}

function changePositionListShowCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldOnPositionListShow != '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldOnPositionListShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldOnPositionListShow('<%=PositionConfigurationConstants.FIELD_NOT_SHOW%>');
		changePositionListShowCheckBox(fieldId);
	}
}

function changePositionListIsFilterCheckboxState(chkBox,fieldId, index){
	_field = _fields[index];
	if(_field.fieldIsFilter != '<%=PositionConfigurationConstants.FIELD_IS_FILTER%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setIsFilter('<%=PositionConfigurationConstants.FIELD_IS_FILTER%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setIsFilter('<%=PositionConfigurationConstants.FIELD_IS_NOT_A_FILTER%>');
	}
}

function changePositionDetailsShowCheckboxState(chkBox,fieldId, index){
	_field1 = _fieldsDetails[index];
	if(_field1.fieldOnPositionDetailsShow != '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field1.setFieldOnPositionDetailsShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field1.setFieldOnPositionDetailsShow('<%=PositionConfigurationConstants.FIELD_NOT_SHOW%>');
		changePositionDetailsShowCheckBox(fieldId);
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

function doChangeStepRankOfDetails(id1, id2) {
	if (id1 != -1 && id2 != _fieldsDetails.length) {
		_temp = _fieldsDetails[id1];
		_fieldsDetails[id1]=_fieldsDetails[id2];
		_fieldsDetails[id2]=_temp;
		
		displayDetailsFields();			
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
			var positionListValue = _field.fieldOnPositionListShow;
						
			var tBody=document.createElement("TBODY");

			var tRow = document.createElement("TR");
			
			var tCell0 = document.createElement("TD");
			tCell0.style.width="150px";
			tCell0.style.height="20px";
			tCell0.className="label";
			tCell0.innerHTML = _field.fieldTitle;		
			tRow.appendChild(tCell0);
			
			<%
			if (ModuleSet.isMODULE_WEB_INTEGRATION()){
			%>	
				var tCell1 = document.createElement("TD");
				tCell1.className="normal";
				tCell1.style.width="100px";
				tCell1.style.wordWrap="break-word";
				
				innerHTML = '';	
				if(positionListValue=='<%=PositionConfigurationConstants.FIELD_SHOW%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_AS" name="showName" onclick="changePositionListShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_AS" name="showName" onclick="changePositionListShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				tCell1.innerHTML = innerHTML;	
					
				tRow.appendChild(tCell1);

				var tCell2=getPositionListFilterCell(_field,i);	
					
				tRow.appendChild(tCell2);
			<%}%>

			var tCell3 = getUpDownCursorsForList(i);  
			tRow.appendChild(tCell3);						
		
			tBody.appendChild(tRow);
			tbl.appendChild(tBody);
		}
	}	
}

//function to display Details field configuration
function displayDetailsFields(){
	tbl = $('detailsFldTable');
	if (tbl ) {
		if ( tbl.hasChildNodes() ){
		    while ( tbl.childNodes.length >= 1 ){
		        tbl.removeChild( tbl.firstChild );       
		    } 
		}
		var defaultFields = 0;
				
		for (var i = 0; i < _fieldsDetails.length; i++) {
			_field1 = _fieldsDetails[i];
			var fieldId = _field1.fieldId;
			var positionDetailsValue = _field1.fieldOnPositionDetailsShow;
						
			var tBody=document.createElement("TBODY");

			var tRow = document.createElement("TR");
			
			var tCell0 = document.createElement("TD");
			tCell0.style.width="150px";
			tCell0.style.height="20px";
			tCell0.className="label";
			tCell0.innerHTML = _field1.fieldTitle;		
			tRow.appendChild(tCell0);
			
			<%
			if (ModuleSet.isMODULE_WEB_INTEGRATION()){
			%>	
				var tCell1 = document.createElement("TD");
				tCell1.className="normal";
				tCell1.style.width="100px";
				tCell1.style.wordWrap="break-word";
				
				innerHTML = '';				
				if(positionDetailsValue=='<%=PositionConfigurationConstants.FIELD_SHOW%>'){ 
			  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_AM" name="showName" onclick="changePositionDetailsShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
				  }else{
				  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_AM" name="showName" onclick="changePositionDetailsShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
				  }
				tCell1.innerHTML = innerHTML;
				tRow.appendChild(tCell1);
			<%}%>
		
			tRow.appendChild(getUpDownCursorsForDetails(i));						
		
			tBody.appendChild(tRow);
			tbl.appendChild(tBody);
		}
	}	
}

function getPositionListFilterCell(field,rowNum){
	var fieldId = field.fieldId;
	var fieldIsFilter = field.fieldIsFilter;
	var fieldIsFilterEditable = field.fieldIsFilterEditable;
	var innerHTML = '';
	var tCell = document.createElement("TD");
	tCell.className="normal";
	tCell.style.width="100px";
	tCell.style.wordWrap="break-word";
	if(fieldIsFilterEditable=='<%=PositionConfigurationConstants.FIELD_IS_FILTER_EDITABLE%>'){
		if(fieldIsFilter=='<%=PositionConfigurationConstants.FIELD_IS_FILTER%>'){
			innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_IF" name="isFilter" onclick="changePositionListIsFilterCheckboxState(this,\''+fieldId+'\', '+rowNum+');" />';
		}else{
			innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_IF" name="isFilter" onclick="changePositionListIsFilterCheckboxState(this,\''+fieldId+'\', '+rowNum+');" />';
		}
	}else{
		if(fieldIsFilter=='<%=PositionConfigurationConstants.FIELD_IS_FILTER%>'){
			innerHTML = '<img src="images/item_chk1_dis.gif" id="img_'+fieldId+'_IF" name="isFilter" />';
		}else{
			innerHTML = '<img src="images/item_chk0_dis.gif" id="img_'+fieldId+'_IF" name="isFilter" />';
		}
	}
	tCell.innerHTML = innerHTML;
	return tCell; 
}

function getUpDownCursorsForDetails(rowNum){
	var tCell = document.createElement("TD");
	tCell.className="normal";
	tCell.style.width="70px";
	tCell.style.wordWrap="break-word";
	innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: doChangeStepRankOfDetails(' + (rowNum - 1) + ', ' + rowNum + ');"/>';		
	innerHTML += '&nbsp;&nbsp;&nbsp;';
	innerHTML += '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: doChangeStepRankOfDetails(' + rowNum + ', ' + (rowNum + 1) + ');"/>';		
	tCell.innerHTML = innerHTML;
	return tCell; 		
}

function getUpDownCursorsForList(rowNum){
	var tCell = document.createElement("TD");
	tCell.className="normal";
	tCell.style.width="70px";
	tCell.style.wordWrap="break-word";
	innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: doChangeStepRankOfList(' + (rowNum - 1) + ', ' + rowNum + ');"/>';		
	innerHTML += '&nbsp;&nbsp;&nbsp;';
	innerHTML += '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: doChangeStepRankOfList(' + rowNum + ', ' + (rowNum + 1) + ');"/>';		
	tCell.innerHTML = innerHTML;
	return tCell; 		
}


function submitForm(){
	var frm=document.adminForm;
	frm.positionListFieldsString.value = listToString(_fields);
	frm.positionDetailsFieldsString.value = detailsToString(_fieldsDetails);
	var fieldId = selectBoxField.getSelectedId();
	errors = validateData();
	if (errors.length > 0) {
		alert(errors);
		return false;
	}
	frm.firstLineFieldId.value= fieldId;
	if(fieldId=='-1'){
		frm.isOtherField.value="1";
	}else{
		frm.isOtherField.value="0";
	}
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
		str += formatString(''+_fields[i].fieldOnPositionListShow) + "|";
		str += formatString(''+_fields[i].fieldIsFilter) + "|";
		str += formatString(''+_fields[i].fieldIsFilterEditable) + "|";
		str += (parseInt(_fields[i].index) + 1);
		if(i!=_fields.length-1){
			str +=",";
		}
	}
	return str;
}

function detailsToString(_fields){
	var str = "";
	for(var i =0;i<_fields.length;i++){
		str += formatString(''+_fields[i].fieldId) + "|";
		str += formatString(_fields[i].fieldType) + "|";
		str += formatString(''+_fields[i].fieldOnPositionDetailsShow) + "|";
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

function setPostionHomeHeader(){
	var url = "adminHome.do?mode=websitePositionHome";
	window.setTimeout("showInPopUp('"+url+"',620,380,null,true);");
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function doOnLoad() {
	initPopUp();
	displayFields();
	displayDetailsFields();
	showDiv();
}

window.onload=doOnLoad;
</script>
