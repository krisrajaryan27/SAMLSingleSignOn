<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<html>

<script language="JavaScript" src="js/doClasses/PositionDetailsScreenConfigarationClass.js" type="text/javascript"/>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/doClasses/PositionListScreenConfigarationClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/doClasses/PositionDetailsScreenConfigarationClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script src="js/submodal/common.js"/>
<script src="js/ajaxfunctions.js"></script>
<script src="js/cookies.js"></script>
<script type="text/javascript">

<s:set name="_fieldList" value="positionDetailsFieldList"/>
<s:if test="%{#_fieldList != null}">

var _fieldsDetails = new Array();
<s:iterator var="field" status="status" value="positionDetailsFieldList" >
	var _field = new DetailsConfField();
	_field.setIndex('<s:property value="%{#status.index}"/>');
	_field.setFieldId('<s:property value = "%{#field.fieldId}"/>');
	_field.setFieldTitle('<s:property value = "%{#field.fieldTitle}"/>');
	_field.setFieldType('<s:property value = "%{#field.fieldType}"/>');
	_field.setFieldOnPositionDetailsShow('<s:property value = "%{#field.fieldOnPositionDetailsShow}"/>');
	_fieldsDetails[_fieldsDetails.length]=_field;
	</s:iterator>
</s:if>
</script>


<body>	
<s:form action="/socialConfig.action">

<s:hidden id="positionDetailsFieldsString" name="positionDetailsFieldsString"/>
<s:hidden id="isSubmitted" name="isSubmitted"/>
	
		<div class="contentDiv">
		<div id="divError" style="display:block">
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
		<br><br>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
			<tr> 
				<td style="vertical-align: bottom;">
					<table cellpadding="0" cellspacing="0" class="boxETab">
					  <tr>
						  <td class="leftC"></td>
						  <td class="content"><s:text name="admin_position_screen_configuration_on_website.label.positionTable"/></td>
						  <td class="rightC"></td>
					  </tr>
				  	</table>
			  	</td>			  
			</tr>
		</table>
		
			<table class="boxHeader" cellspacing="0" cellpading="0" style="border-bottom: 0px;">
			<tr>
				<td class="header" height="18" style="width:187px;"><s:text name="common.fields" /></td>
				<% 
				if (ModuleSet.isMODULE_WEB_INTEGRATION()){
				%>
					<td class="header" height="18" style="width:113px;"><s:text name="common.show" /> ?</td>
				<%} %>
			
			</tr>
		</table>
		<div id="fldDetailsDiv" class="outerDiv" style="padding:0px; width: 316px;">
				<table id="detailsFldTable" cellspacing="0" cellpadding="0" class="boxContent" style="border:0px;">
				</table>
		</div>
		<br/>
		<br/>
		<div class="navBtn" style="margin-top:5px;"><a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();">
		<span class="rightC"></span><span class="leftC"></span><s:text name="common.save"/></a>
		
		</div>
		<br/>
		<br/>
		</table>
		</div>
</s:form>
</body>

<script type="text/javascript">
var chkedChkBoxSrc='images/checkboxchecked.gif';
var unchkedChkBoxSrc='images/checkboxunchecked.gif';

var tbl = document.getElementById("detailsFldTable");
function detailsFieldPopulate(){
	for(var i =0 ; i < _fieldsDetails.length ; i++){
		_field = _fieldsDetails[i];
		var fieldId = _field.fieldId;
		var positionDetailsValue = _field.fieldOnPositionDetailsShow;
		var tbody = document.createElement("TBODY");
		var tRow = document.createElement("TR");
		var tCell0 = document.createElement("TD");
		tCell0.style.width="200px";
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
		
		var innerHTML = '';				
		<%
		if (ModuleSet.isMODULE_WEB_INTEGRATION()){
		%>	
			var tCell1 = document.createElement("TD");
			tCell1.className="normal";
			tCell1.style.width="130px";
			
			
			innerHTML = '';				
			if(positionDetailsValue=='<%=PositionConfigurationConstants.FIELD_SHOW%>'){ 
		  		innerHTML = '<img src="images/checkboxchecked.gif" id="img_'+fieldId+'_AM" name="showName" onclick="changePositionDetailsShowCheckboxState(this,\''+fieldId+'\', '+i+');" />';
			  }else{
			  	innerHTML = '<img src="images/checkboxunchecked.gif" id="img_'+fieldId+'_AM" name="showName" onclick="changePositionDetailsShowCheckboxState(this,\''+fieldId+'\', '+i+');" />'
			  }
			tCell1.innerHTML = innerHTML;
			tRow.appendChild(tCell1);
		<%}%>
	
		tCell1.innerHTML = innerHTML;
		tRow.appendChild(tCell1);		
		<%}%>
		
		tbody.appendChild(tRow);
		tbl.appendChild(tbody);
	}
}


function changePositionDetailsShowCheckboxState(chkBox,fieldId, index){
	_field = _fieldsDetails[index];
	if(_field.fieldOnPositionDetailsShow != '<%=PositionConfigurationConstants.FIELD_SHOW%>'){
		chkBox.src=chkedChkBoxSrc;
		_field.setFieldOnPositionDetailsShow('<%=PositionConfigurationConstants.FIELD_SHOW%>');
	}else{
		chkBox.src=unchkedChkBoxSrc;
		_field.setFieldOnPositionDetailsShow('<%=PositionConfigurationConstants.FIELD_NOT_SHOW%>');
		changePositionDetailsShowCheckBox(fieldId);
	}
}


function changePositionDetailsShowCheckBox(fieldId){
	var val = document.getElementById('img_'+fieldId+'_PD');
	if(val){
		val.src=chkedChkBoxSrc;
	}
}

function submitForm(){
	
	document.getElementById("positionDetailsFieldsString").value = detailsToString(_fieldsDetails);
	document.getElementById("isSubmitted").value = true;
	document.forms[0].submit();
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

function doOnload(){
	document.getElementById("isSubmitted").value = false;
	detailsFieldPopulate();
	
}

window.onload = doOnload;
</script>

</html>