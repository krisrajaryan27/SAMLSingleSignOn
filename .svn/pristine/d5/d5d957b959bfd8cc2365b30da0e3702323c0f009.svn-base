<%@page import="org.apache.struts.Globals"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.positions.PositionConstants"%>
<%@page import="com.talentPool.custom.dataobject.CustomFieldData"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%>
<%@page import="com.talentPool.custom.form.CustomFieldForm"%><script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>						
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<div class="contentDivPop" style="width:410px;">
<% 
if(request.getAttribute(Globals.ERROR_KEY)!=null){
%>
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
<script type="text/javascript">
var customFieldArray = new Array();
var customFieldData = null;
var customFieldName = null;
var customFieldId = null;
var customFieldType = null;
var customFieldDisplayName = null;
</script>
<div class="outerDiv" style="width: 500px;">
<html:form action="/selectionProcessFilters">
	<div class="popupTop">
		<table class="posinput">
			<tr>
				<td>
					<select id="customFiledsSelectBox"  name="customFiledsSelectBox" size="8" style="width: 200px; border:1px solid #99CC01;" onchange="javascipt: onChangeCustomFieldFilter(this)">
						<logic:notEmpty name="customFieldDataList" scope="request">
							<logic:iterate id="customFieldData" name="customFieldDataList" scope="request">
								<option value='<bean:write name="customFieldData" property="fieldId"/>' >
									<bean:write name="customFieldData" property="fieldDisplayName"/>
								</option>
								<script>
									customFieldName = '<bean:write name="customFieldData" property="fieldName"/>';
									customFieldDisplayName = '<bean:write name="customFieldData" property="fieldDisplayName"/>';
									customFieldType = '<bean:write name="customFieldData" property="fieldType"/>';
									customFieldId = '<bean:write name="customFieldData" property="fieldId"/>';
									customFieldArray[customFieldArray.length] = new CustomFieldData(customFieldName,customFieldDisplayName,customFieldType,'',customFieldId);
								</script>
							</logic:iterate>
						</logic:notEmpty>
					</select>
				</td>
				<td>
					<logic:notEmpty name="customFieldDataList" scope="request">
						<logic:iterate id="customFieldData" name="customFieldDataList" scope="request">
							<div  id='customFieldUIDiv_<bean:write name="customFieldData" property="fieldId"/>' style="display: none;">
								<bean:write name="customFieldData" property="UI" filter="false"/>
							</div>
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
		</table>
	</div>
	<div class="popupBody">
		<table class="tblPop" width="100%">
			<tr>
				<td>
					<bean:message key="position.home.custom_fields.filters.note"/>
				</td>
			</tr>
			<tr>
				<td>
					<div class="navBtn" style="float: right;">
						<a href="#" style="width:60px;" class="active" onclick="javascript: submitCustomFieldFilter();"><span class="rightC"></span><span class="leftC"></span>Filter</a>
						<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript:window.top.hidePopWin(false);"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</table>
	</div>
</html:form>
</div>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script>
var TYPE_DROPDOWN = '<%=CustomFieldConstants.TYPE_DROPDOWN%>';
var TYPE_CHECKBOX = '<%=CustomFieldConstants.TYPE_CHECKBOX%>';
var TYPE_RADIO = '<%=CustomFieldConstants.TYPE_RADIO%>';
var TYPE_LISTBOX = '<%=CustomFieldConstants.TYPE_LISTBOX%>';
var DEFAULT_SELECT_OPTION = '<%=CustomFieldConstants.DEFAULT_SELECT_OPTION%>';


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
function getFNumber(obj){
	if(obj.value.trim()!=''){
		if(isNaN(obj.value)){
			alert('<bean:message key="common.please_enter_valid_number" />');
			obj.focus();
			return false;
		}
	}
}
function onChangeCustomFieldFilter(obj){
	var selectedId = obj.options[obj.selectedIndex].value;
	var id = 'customFieldUIDiv_'+selectedId;
	showSelectedDiv(id);
}
function showSelectedDiv(id){
	var allaDivs = document.getElementsByTagName('div');
	for(var i = 0;i<allaDivs.length;i++){
		var divId = allaDivs[i].id;
		if(isCustomFieldDiv(divId)){
			if(divId==id){
				$(id).show();
			} else {
				$(divId).hide();
			}
		}
	}
}
function isCustomFieldDiv(id){
	if(id.indexOf('customFieldUIDiv_')!=-1)
		return true;
	else
		return false;
} 
function submitCustomFieldFilter(){
	var customFieldValue = '';
	var obj = $('customFiledsSelectBox');
	var customFieldObj = customFieldArray[obj.selectedIndex];
	customFieldValue =  getSelectedValues(customFieldObj);
	customFieldValue = orderCustomFieldValue(customFieldValue);
	customFieldObj.setFieldValue(customFieldValue);
	returnVal = customFieldObj;
	window.top.hidePopWin(true);
}
function orderCustomFieldValue(cVal){
	if(cVal.indexOf('|')!=-1){
		cVal = sortVal(cVal);
		return cVal; 
	}else {
		return cVal;
	}
}
function sortVal(val){
	var str = new Array(); 
	str = val.split('|');
	str = str.sort();
	return str.join('|');
}
function getSelectedValues(cData){
	if(cData.getType()==TYPE_DROPDOWN){
		val= eval(cData.getName()).getSelectedId();
		if(val==DEFAULT_SELECT_OPTION){
			val='';
		}
	}else if(cData.getType()==TYPE_LISTBOX){ 
		val= eval(cData.getName()).getSelectedItems(true,true,'|');
	}else if(cData.getType()==TYPE_CHECKBOX || cData.getType()==TYPE_RADIO){
		val= eval(cData.getName()).getSelectedItems(true,'|');
	}else{
		val=$(cData.getName()).value;
	}
	return val;
}
function doOnLoad(){
	var obj = $('customFiledsSelectBox');
	obj.selectedIndex = 0;
	onChangeCustomFieldFilter(obj);
	window.top.setPopTitle('<b><bean:message key="position.home.custom_fields.filters.title"/></b>');	
}
window.onload = doOnLoad;
</script>	