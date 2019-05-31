<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.masters.dataobject.RatingFieldsData"%>
<%@page import="org.apache.struts.Globals"%>

<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/doClasses/RatingField.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/RatingData.js" type="text/javascript"></script>
											
<script language="JavaScript">
var ratingFields = new Array();
<% 
ArrayList ratingFields = (ArrayList)request.getAttribute("ratingFields");
for(int i=0; ratingFields!=null && i<ratingFields.size(); i++){
	//create javascript array
	RatingFieldsData ratingFieldsData = (RatingFieldsData)ratingFields.get(i);
	String fieldId = ratingFieldsData.getRatingFieldId();
	String fieldDesc = ratingFieldsData.getRatingFieldDesc().replaceAll("'","\\\\'");
%>	
var objR = new RatingField('<%=fieldId%>','<%=fieldDesc%>');
ratingFields[ratingFields.length]=objR;
<%	
}
%>

</script>
<div class="contentDiv">
	<div id="divError" style="display:block">
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
	</div>
<html:form action="/masters">
	<html:hidden property="mode" name="mastersForm"/>
	<html:hidden property="subMode" name="mastersForm"/>
	<html:hidden property="ratingId" name="mastersForm"/>
	<html:hidden property="ratingFieldsString" name="mastersForm"/>
	<html:hidden property="submitted" name="mastersForm" value="1"/>
	<table border="0" cellspacing="0" cellpadding="0" class="posinput">
		<tr>
			<td class="label"><bean:message key="master_add_rating.label.rating_name" />
			<span class="star">*</span></td>
			<td><html:text name="mastersForm" property="ratingTitle" size="50"/></td>
		</tr>
	</table>
	<br/>
	
	<div id="emailSent" style="width:125px;cursor: pointer;" class="boxDarkTab" onclick="javascript: toggleFolderDisplay(2)"><span class="rightC"></span><span class="leftC"></span>&nbsp;<bean:message key="master_add_rating.label.rating_fields"/></div>
	<div class="outerDiv" style="padding:0px 0px 5px 0px">
	<table id="fields" cellspacing="0" cellpadding="0" class="posinput">
	<tbody>
	<tr>
		<td style="width:20px;" class="theader">&nbsp;</td>
		<td class="label theader" style="width:40px;"><bean:message key="master_add_rating.label.rating_rank" /></td>
		<td class="label theader" style="width:300px;"><bean:message key="master_add_rating.label.rating_desc" /></td>
		<td style="width:30px;" class="theader">&nbsp;</td>
		<td style="width:30px;" class="theader">&nbsp;</td>
	</tr>	
	</tbody>
	</table>
	<br/>
	<table border="0" cellspacing="0" cellpadding="0" class="posinput">
		<tr>
			<td>
				<a href="#" style="width:180px;" class="btn3" onclick="javascript:addNewField();"><span class="rightC"></span><span class="leftC"></span><bean:message key="master_add_rating.label.add_new_option"/></a>
			</td>
		</tr>
	</table>
	</div>
	<br/>
	<br/>
	<div class="navBtn" style="float:right;">
		<a href="#" style="width:50px;" class="active" onclick="javascript:saveRating();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
		<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
	</div>
	
</html:form>
</div>
<script language="JavaScript">
function populateFields(){
	tbl = $('fields');
	if (tbl) {
		var tBody= tbl.getElementsByTagName("tbody")[0];
		if(tBody){
			var tRows = tBody.childNodes;
			for(var i = tRows.length - 1; i >= 1; i--){
				tBody.removeChild(tRows[i]);
			}	
		}
		if(ratingFields.length>0){
			for (i = 0; i < ratingFields.length; i++) {	
				if(tBody){
				}else{
					tBody = document.createElement("TBODY");
				}
				var tRow = document.createElement("TR");
				
				var tCell0 = document.createElement("TD");
				tCell0.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.delete"/>' + '" src="images/ico_delete.gif" onclick="javascript: doDeleteTrait(' + i + ');"/>';		
				tRow.appendChild(tCell0);
				
				tCell0 = document.createElement("TD");
				tCell0.innerHTML = i+1;		
				tRow.appendChild(tCell0);
				
				tCell0 = document.createElement("TD");
				//var el = createFormElement("input","text",'fld_'+ i,ratingFields[i].getFieldDescription(),'');
				//el.maxLength = '255';
				//el.size='50';
				//el.name='fld_'+ i;
				tCell0.innerHTML = '<input type=\'text\' size=\'50\' maxlength=\'255\' id=\'fld_'+i+'\' name=\'fld_'+i+'\' value=\'\' />';
				//tCell0.appendChild(el);
				tRow.appendChild(tCell0);
				
				
				tCell0 = document.createElement("TD");
				tCell0.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_up"/>' + '" src="images/btn_uparrow.gif" onclick="javascript: swapFields(' + (i - 1) + ', ' + i + ');"/>';		
				tRow.appendChild(tCell0);
				
				tCell0 = document.createElement("TD");
				tCell0.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.move_down"/>' + '" src="images/btn_dwnarrow.gif" onclick="javascript: swapFields(' + i + ', ' + (i + 1) + ');"/>';		
				tRow.appendChild(tCell0);

				tBody.appendChild(tRow);
				tbl.appendChild(tBody);
				$('fld_'+i).value=ratingFields[i].getFieldDescription();
			}
		}
		
	}	
	
}
function doDeleteTrait(index){
	updateFieldsArray();
	ratingFields.splice(index, 1);
	populateFields();
}
function swapFields(id1, id2) { 	
	updateFieldsArray();
	if (id2 != 0 && id2 != ratingFields.length) {              
		_temp = ratingFields[id1];
		ratingFields[id1] = ratingFields[id2];
		ratingFields[id2] = _temp;
		populateFields();
	}
}
function addNewField(){
	var objR = new RatingField('0','');
	ratingFields[ratingFields.length]=objR;
	updateFieldsArray();
	populateFields();
}
function updateFieldsArray(){
	for (i = 0; i < ratingFields.length; i++) {
		var fld = $('fld_'+i);
		if(fld){
			ratingFields[i].setFieldDescription(fld.value);
		}
	}
}
function validateForm(){
	if(document.mastersForm.ratingTitle.value.trim()==''){
		alert('<bean:message key="master_add_rating.error.enter_rating_name"/>');
		document.mastersForm.ratingTitle.focus();
		return false;
	}
	if( ratingFields.length==0){
		alert('<bean:message key="master_add_rating.error.enter_options"/>');
		return false;
	}
	for (i = 0; i < ratingFields.length; i++) {
		var fld = $('fld_'+i);
		if(fld){
		
		if(fld.value.trim()==''){
			alert('<bean:message key="master_add_rating.error.enter_option_desc"/>');
			fld.focus();
			return false;
		}
		}
	}
	return true;
}
function constructAndSetFields(){
	updateFieldsArray();
	var str='';
	for (i = 0; i < ratingFields.length; i++) {
		var rf = ratingFields[i];
		if(i>0){
			str +='||';
		}
		var desc = rf.getFieldDescription().replace(/[|]/g ,"&#124;");
		str += rf.getFieldId()+'|'+ desc;
	}
	document.mastersForm.ratingFieldsString.value=str;
}
function saveRating(){
	if(validateForm()){
		constructAndSetFields();
		document.mastersForm.submit();
	}
}
function cancelOperation() {
	window.top.hidePopWin(false);
	return false;
}
function onPopUpLoad() {
	window.top.setPopTitle('<b><bean:message key="master_add_rating.title.add_rating" /></b>');
	populateFields();
}
window.onload = onPopUpLoad;
</script>