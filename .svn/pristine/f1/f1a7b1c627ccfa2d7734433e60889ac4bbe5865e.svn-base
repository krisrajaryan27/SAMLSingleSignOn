<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.masters.form.MastersForm"%>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="javascript" type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>

<link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<link rel="shortcut icon" href="images/favicon.ico" />
<%@ page import="com.talentPool.admin.form.AdminForm,
							com.talentPool.notifier.TemplateConstants,
							com.talentPool.common.properties.TPApplicationProperties" %>

<script language="javascript" type="text/javascript">
tinyMCE.init({
	mode : "textareas",
	elements : "templateContent",
	theme : "advanced",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_buttons1 : "newdocument,bold,italic,underline,forecolor,backcolor,bullist,numlist,separator,undo,redo,cut,copy,paste,justifyleft,justifyright,separator,formatselect,fontselect,fontsizeselect",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	force_br_newlines: true,
	theme_advanced_disable : "anchor",
	theme_advanced_path : false
});

</script>

<logic:present name="update" scope="request">
<script>
window.top.hidePopWin(true);
</script>
</logic:present>   
<logic:notPresent name="update" scope="request">
<%
	MastersForm mastersForm = (MastersForm)request.getAttribute("mastersForm");
	String userId = (String) request.getSession().getAttribute("userId");
%>
<html:form action="/masters">
	<html:hidden property="mode" name="mastersForm"/>
	<html:hidden property="subMode" name="mastersForm"/>
	<html:hidden property="templateId" name="mastersForm"/>
	<html:hidden property="templateCode" name="mastersForm"/>
	<html:hidden property="templatePrivate" name="mastersForm"/>
	<html:hidden property="templateTypeId" name="mastersForm"/>
	<html:hidden property="templateVariables" name="mastersForm"/>
	<html:hidden property="templateOwnerId" name="mastersForm"/>
	<html:hidden property="templateIsDefault" name="mastersForm"/>
	<html:hidden property="templateIsSaveAsDraft" name="mastersForm"/>
	<html:hidden property="doShowSaveAsDraftOption" name="mastersForm"/>

<div class="contentDiv" style="margin: 15px 0px 0px 15px;">
	<logic:equal name="mastersForm" property="templateOwnerId" value="<%=userId%>">
	<div class="popupTop" style="width:651px;padding-top: 5px; ">
		<table class="tblPop" >
		<logic:notEqual name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_SYSTEM%>">
		<tr>
			<td class="header"><bean:message key="template_master.template.label.template_for" /></td>
			<td>
				<script language="JavaScript">
					var options = <bean:write name="mastersForm" property="jsArrayTemplateFor" filter="false"/>;											
					option = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
					options = option.concat(options);
					selectBoxTemplateFor = new SelectBox(options,'<bean:write name="mastersForm" property="templateTypeId" />','images/btn_dropdown.gif',{namesonly:false, width:'260px', size:15});
					document.write(selectBoxTemplateFor.getHtml());
					selectBoxTemplateFor.setOnChangeHandler('changeOptions');
					selectBoxTemplateFor.init();
				</script>
			</td>
		</tr>			
		</logic:notEqual>
		<tr>
			<td class="header"><bean:message key="template_master.template.label.template_name" />
			<span class="star">*</span></td>
			<td><html:text name="mastersForm" property="templateName" size="70" /></td>
		</tr>
		<logic:notEqual name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_SYSTEM%>">
		<tr>
			<td class="header"></td>
			<td>
				<img id="level1" src="" onclick="javascript: toggleTemplatePrivate('<%=TemplateConstants.TEMPLATE_GLOBAL%>');" />&nbsp;&nbsp;<bean:message key="template_master.template.label.template_global" />&nbsp;&nbsp;
				<img id="level2" src="" onclick="javascript: toggleTemplatePrivate('<%=TemplateConstants.TEMPLATE_PRIVATE%>');" />&nbsp;&nbsp;<bean:message key="template_master.template.label.template_private" />
			</td>
		</tr>
		</logic:notEqual>
		<logic:equal name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_SYSTEM%>">
		<tr>
			<td class="header"><bean:message key="template_master.template.label.template_for" /></td>
			<td><bean:write name="mastersForm" property="templateType" /></td>
		</tr>	
		</logic:equal>
		<tr>
			<td class="header"><bean:message key="template_master.template.label.default" /></td>
			<td><img id="isdefault" src="images/checkboxunchecked.gif" onclick="javascript: toggleDefault();"></td>
		</tr>			
		<tr id="rowSaveAsDraft">
			<td class="header"><bean:message key="template_master.template.label.save_as_draft" /></td>
			<td><img id="isSaveAsDraft" src="images/checkboxunchecked.gif" onclick="javascript: toggleSaveDraft();"></td>
		</tr>	
		<tr>
			<td class="header"><bean:message key="template_master.template.label.template_subject" />
			<span class="star">*</span></td>
			<td><html:text name="mastersForm" property="templateSubject" size="70" /></td>
		</tr>
		</table>
		</div>
		
		<table cellpadding="0" cellspacing="0">	
		<tr>
			<td>
			<html:textarea property="templateContent" name="mastersForm" styleClass="inputBox" style="width:100%;height:330px;"></html:textarea>
			</td>
			<td style="padding-left: 5px; padding-right: 5px;">
			<div class="navBtn">
					<a href="#" style="width:30px;" class="active" onclick="javascript:addVariable();return false;"><span class="rightC"></span><span class="leftC"></span><img src="images/ico_leftarrow.gif"  border="0" align="absmiddle" /></a> 
			</div>
			</td>
			<td>
				<select id="selBox" size="27" style="width:200px;font-size: 10px;font-family: verdana;" ondblclick="javascript:addVariable();">
				</select>
			</td>
		</tr>
		</table>
		<div class="navBtn" style="float:left; margin-top: 10px; ">
			<a href="#" style="width:60px;" class="active" onclick="javascript:saveOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
			<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
		</div>
	</logic:equal>
	<logic:notEqual name="mastersForm" property="templateOwnerId" value="<%=userId%>">
	<div class="popupTop" style="width:651px; padding-top: 5px;">
	<table class="tblPop" >
		<tr>
			<td class="header"><bean:message key="template_master.template.label.template_for" /></td>
			<td><bean:write name="mastersForm" property="templateType" /></td>
		</tr>	
		<tr>
			<td class="header"><bean:message key="template_master.template.label.template_name" /></td>
			<td><html:text name="mastersForm" property="templateName" size="70" readonly="true"/></td>
		</tr>
		<tr>
			<td class="header"><bean:message key="template_master.template.label.default" /></td>
			<td><img id="isdefault" src="images/checkboxunchecked.gif" ></td>
		</tr>	
		<tr>
			<td class="header"><bean:message key="template_master.template.label.template_subject" /></td>
			<td><html:text name="mastersForm" property="templateSubject" size="70" readonly="true"/></td>
		</tr>
	</table>
	</div>			
	<html:textarea property="templateContent" name="mastersForm" styleClass="inputBox" style="width:100%;height:330px;"></html:textarea>	
	<div class="navBtn" style="float:left; margin-top: 10px;">
		<a href="#" style="width:60px;" class="active" onclick="javascript:cancelOperation();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.close"/></a>
	</div>
	</logic:notEqual>
</div>
</html:form>
<script language="JavaScript">
selectedRadioButton="images/checkedradiobutton.gif";
deselectedRadioButton="images/radiobutton.gif";

window.onload = onOnPopUpLoad;

function onOnPopUpLoad() {
	setPopupTitle();
	setDefault();
	<logic:equal name="mastersForm" property="doShowSaveAsDraftOption" value="<%=TemplateConstants.TEMPLATE_SHOW_SAVE_AS_DRAFT_OPTION%>">
	setSaveAsDraft();
	</logic:equal>
	<logic:notEqual name="mastersForm" property="doShowSaveAsDraftOption" value="<%=TemplateConstants.TEMPLATE_SHOW_SAVE_AS_DRAFT_OPTION%>">
		<logic:equal name="mastersForm" property="templateOwnerId" value="<%=userId%>">
		$("rowSaveAsDraft").hide();
		</logic:equal>
	</logic:notEqual>
	<logic:equal name="mastersForm" property="templateOwnerId" value="<%=userId%>">
		<logic:notEqual name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_SYSTEM%>">
			//populateTemplateType();
			populateIsTemplatePrivate();
		</logic:notEqual>
		populateSelectOptions();
	</logic:equal>
}
var chkedCheckBox = "images/checkboxchecked.gif";
var unChkedCheckBox = "images/checkboxunchecked.gif";
function setDefault() {
	var templateIsDefault = '<bean:write name="mastersForm" property="templateIsDefault" />';
	var elem = document.getElementById("isdefault");
	if (elem) {
		if ('<%=TemplateConstants.TEMPLATE_DEFAULT%>' == templateIsDefault) {
			elem.src = chkedCheckBox;	
		} else {
			elem.src = unChkedCheckBox;
		}
	}	
}
function setSaveAsDraft() {
	var templateIsSaveAsDraft = '<bean:write name="mastersForm" property="templateIsSaveAsDraft" />';
	var elem = document.getElementById("isSaveAsDraft");
	if (elem) {
		if ('<%=TemplateConstants.TEMPLATE_SAVE_AS_DRAFT%>' == templateIsSaveAsDraft) {
			elem.src = chkedCheckBox;	
		} else {
			elem.src = unChkedCheckBox;
		}
	}	
}
function getTemplateDefault() {
	var elem = document.getElementById("isdefault");
	if (elem) {
		if (elem.src.indexOf(chkedCheckBox) != -1) {
			return '<%=TemplateConstants.TEMPLATE_DEFAULT%>';
		} else {
			return '<%=TemplateConstants.TEMPLATE_NOT_DEFAULT%>';
		}	
	}
}
function getTemplateSaveAsDraft() {
	var elem = document.getElementById("isSaveAsDraft");
	if (elem) {
		if (elem.src.indexOf(chkedCheckBox) != -1) {
			return '<%=TemplateConstants.TEMPLATE_SAVE_AS_DRAFT%>';
		} else {
			return '<%=TemplateConstants.TEMPLATE_DONT_SAVE_AS_DRAFT%>';
		}	
	}
	return '<%=TemplateConstants.TEMPLATE_DONT_SAVE_AS_DRAFT%>';
}
function toggleDefault() {
	var elem = document.getElementById("isdefault");
	if (elem) {
		if (elem.src.indexOf(chkedCheckBox) != -1) {
			elem.src = unChkedCheckBox;
		} else {
			elem.src = chkedCheckBox;		
		}	
	}
}

function toggleSaveDraft() {
	var elem = document.getElementById("isSaveAsDraft");
	if (elem) {
		if (elem.src.indexOf(chkedCheckBox) != -1) {
			elem.src = unChkedCheckBox;
		} else {
			elem.src = chkedCheckBox;		
		}	
	}
}

function populateSelectOptions() {
	document.mastersForm.selBox.options.length=0;
	variables = document.mastersForm.templateVariables.value;
	if (variables != '') {
		parts = variables.split(',');
		for (var j = 0; j < parts.length; j++) {			
			document.mastersForm.selBox.options[j] = new Option(parts[j].trim(), parts[j].trim());
		}
	}
}

function addVariable() {
	if($('selBox').selectedIndex == -1) {
		return;
	}
	myValue = $('selBox').options[$('selBox').selectedIndex].value;
	tinyMCE.execInstanceCommand('templateContent', 'mceInsertContent', false, myValue);
}

function populateIsTemplatePrivate() {
	var elem1 = $('level1');
	var elem2 = $('level2');	
	<logic:equal name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_GLOBAL%>">
		elem1.src = selectedRadioButton;
		elem2.src = deselectedRadioButton;
		return;
	</logic:equal>
	<logic:equal name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_PRIVATE%>">
		elem1.src = deselectedRadioButton;
		elem2.src = selectedRadioButton;
		return;
	</logic:equal>
	elem1.src = deselectedRadioButton;
	elem2.src = deselectedRadioButton;
}

function populateTemplateType() {
	var type = document.mastersForm.templateType.value;
	if (type != '') {
		parts = type.split(',');
		for (var i = 0; i < options.length && type != ''; i++) {
			for (var j = 0; j < parts.length; j++) {			
				if (options[i].getId() == parts[j].trim()) {
					selectBoxTemplateFor.setSelected(selectBoxTemplateFor.getIndexWithId(parts[j].trim()));
					type = '';
					break;
				}
			}
		}
	}
}

function toggleTemplatePrivate(val) {
	var elem1 = $('level1');
	var elem2 = $('level2');	
	if (val == <%=TemplateConstants.TEMPLATE_GLOBAL%>) {
		elem1.src = selectedRadioButton;
		elem2.src = deselectedRadioButton;
	} else if (val == <%=TemplateConstants.TEMPLATE_PRIVATE%>) {
		elem1.src = deselectedRadioButton;
		elem2.src = selectedRadioButton;
	}
}

function setPopupTitle(){
	var popupTitle = '<b>' + '<bean:message key="template_master.template.label.add_template" />' + '</b>';	
	var name = document.mastersForm.templateName.value;
	if (name != '') {
		popupTitle = '<b>' + name + '</b>';	
	}
	window.top.setPopTitle(popupTitle);
}

function changeOptions() {	
	var id = selectBoxTemplateFor.getSelectedId();
	if (id == -1) {
		document.mastersForm.selBox.options.length=0;
	} else {
		var pars = "mode=getGenericVariables&templateTypeId=" + id;
		var myAjax = ajaxCall("masters.do",'get',pars,onChangeOptionsResponse, reportError);
	}
}

function onChangeOptionsResponse(request){
  	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
  	if(isErrorXml(xmlFile)){
		alert('<bean:message key="template_master.template.error.get_variables"/>');
		return;
	} 
	var parent = xmlFile.getElementsByTagName("variables")[0];
	
	if(parent != null) {
		var doShowSaveAsDraft = parent.getElementsByTagName("doShowSaveAsDraft")[0].firstChild.nodeValue;
	  	var elem = document.getElementById("rowSaveAsDraft");
	  	if(doShowSaveAsDraft == '<%=TemplateConstants.TEMPLATE_SHOW_SAVE_AS_DRAFT_OPTION%>') {
	  		elem.show();
	  	} else {
	  		elem.hide();
	  	}	
	  	document.mastersForm.doShowSaveAsDraftOption.value = doShowSaveAsDraft;
		var children = parent.getElementsByTagName("variable");	
		document.mastersForm.selBox.options.length=0;
		for (var I = 0 ; I < children.length ; I++) {
			document.mastersForm.selBox.options[I] = new Option(children[I].firstChild.nodeValue,children[I].firstChild.nodeValue);		
	  	}
	} else {
		document.mastersForm.selBox.options.length=0;
	}
}

function saveOperation() {
	var name = document.mastersForm.templateName.value;
	<logic:notEqual name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_SYSTEM%>">
	var type = '';
	var elem1 = $('level1');
	var elem2 = $('level2');	
	if (elem1.src.indexOf(selectedRadioButton) != -1) {
		type = '<%=TemplateConstants.TEMPLATE_GLOBAL%>';
	} else if (elem2.src.indexOf(selectedRadioButton) != -1) {
		type = '<%=TemplateConstants.TEMPLATE_PRIVATE%>';
	}
	</logic:notEqual>
	var subject = document.mastersForm.templateSubject.value;
	var content = tinyMCE.activeEditor.getContent();
	errors = '';
	if (name == '') {
		errors = addError(errors, '- <bean:message key="template_master.label.template_name"/>');
	}
	<logic:notEqual name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_SYSTEM%>">
	if (type == '') {
		errors = addError(errors, '- <bean:message key="template_master.template.label.template_for"/>');
	}
	</logic:notEqual>
	if (subject == '') {
		errors = addError(errors, '- <bean:message key="template_master.template.label.template_subject"/>');
	}
	if (content == '') {
		errors = addError(errors, '- <bean:message key="template_master.template.label.template_content"/>');
	}
	var templateIsDefault1 = getTemplateDefault();
	document.mastersForm.templateIsDefault.value = templateIsDefault1;
	
	var templateIsSaveAsDraft1 = getTemplateSaveAsDraft();
	document.mastersForm.templateIsSaveAsDraft.value = templateIsSaveAsDraft1;
	
	if (errors != '') {
		errors = addError('<bean:message key="template_master.error.data_required"/>', errors);
		alert(errors);
		return false;
	}
	<logic:notEqual name="mastersForm" property="templatePrivate" value="<%=TemplateConstants.TEMPLATE_SYSTEM%>">
	document.mastersForm.templatePrivate.value = type;
	if (selectBoxTemplateFor.getSelectedId() == -1) {
		document.mastersForm.templateTypeId.value = '';
	} else {
		document.mastersForm.templateTypeId.value = selectBoxTemplateFor.getSelectedId();
	}
	</logic:notEqual>
	document.mastersForm.templateContent.value=content;
	document.mastersForm.mode.value = 'manageTemplateMaster';
	<logic:notEmpty name="mastersForm" property="templateCode">
	document.mastersForm.subMode.value = '<%=MastersConstants.SUB_MODE_EDIT%>';
	</logic:notEmpty>
	<logic:empty name="mastersForm" property="templateCode">
	document.mastersForm.subMode.value = '<%=MastersConstants.SUB_MODE_ADD%>';
	</logic:empty>
	document.mastersForm.submit();
	return true;
}

function addError(errors, error) {
	if (errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}

function cancelOperation() {
	window.top.hidePopWin(false);
	return false;
}
</script>
</logic:notPresent>   