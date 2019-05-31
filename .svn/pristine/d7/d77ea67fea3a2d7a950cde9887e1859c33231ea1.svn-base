<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.talentPool.documents.DocumentConstants"%>
<%@page import="com.talentPool.offerSheet.OfferSheetConstants"%>
<%@ page import="org.apache.struts.Globals,
				com.talentPool.user.manager.PermissionSet,
                com.talentPool.applicant.manager.ImportConfigurationManager,
                com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@ page import="com.talentPool.offerSheet.dataobject.OfferSheetTemplateVariable"%>
<%@ page import="com.talentPool.common.properties.TPApplicationProperties"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script language="JavaScript">
var templateVars = new Array();
var templateMap = new Object();
<s:if test="#request['variableDataMap']!=null" >
	<s:iterator value="#request['variableDataMap']" >
		templateMap['<s:property value="key"/>'] = new String('<s:property value="value"/>'); 
	</s:iterator>
</s:if>
</script>
<div class="contentDivPop" style="width: 600px;">
	<%@include file="../common/errordiv.jspf" %>
	<div class="outerDiv">
	<s:form action="offerTemplateVariableMapping" method="POST">
  	<s:hidden name="applicantId" />
	<s:hidden name="modifyOffer" />
	<s:hidden name="offerSheetTemplateId" />
	<s:hidden name="offerCode" />
	<s:hidden name="offerFormat" />
	<s:hidden name="appOfferDetails.offeredCTC" />
	<s:hidden name="appOfferDetails.offeredBasic" />
	<s:hidden name="appOfferDetails.offeredDesignation" />
	<s:hidden name="appOfferDetails.offeredLevel" />
	<s:hidden name="appOfferDetails.inputSalaryVariable" />
	<s:set name="offerCodeVar" id="offerCodeVar" value="@com.talentPool.offerSheet.constants.OfferSheetConstants@OFFER_CODE" />
	<div class="popupTop">
		<table class="tblPop">
			<s:if test="#request['templateVariables']!=null" >
			<s:iterator value="#request['templateVariables']" >
			<script language="JavaScript">
				templateVars[templateVars.length] = '<s:property value="templateVariable"/>';
			</script>		
			<tr>
				<td width="150px" class="header"><s:property value="templateVariableDisplayName"/></td>
				<td>
					<s:if test="templateVariableAttribute==#offerCodeVar">
						<input type="text" readonly="readonly" title="<s:property value="templateVariableVal"/>" id="<s:property value="templateVariable"/>" name="<s:property value="templateVariable"/>" value="<s:property value="templateVariableVal"/>"/>
					</s:if>
					<s:else>
						<input type="text" id="<s:property value="templateVariable"/>" name="<s:property value="templateVariable"/>" value="<s:property value="templateVariableVal"/>"/>
					</s:else>
				</td>
				<td>
					<script>
						var m = [new SelectOption('-1','<s:text name="common.selectlist.default"/>')]; 
						var opts = <s:property value="#request['jsArrayMapping']" />;
						opts = m.concat(opts);
						selectBoxAttributes = new SelectBox(opts,'<s:property value="templateVariableAttribute"/>','images/btn_dropdown.gif',{controlname: '<s:property value="templateVariable"/>', namesonly:false, width:'232px', size:10});
						document.write(selectBoxAttributes.getHtml());
						selectBoxAttributes.setOnChangeHandler('onChangeSelectBoxAttributes');
						selectBoxAttributes.init();
					</script>
				</td>
			</tr>
			</s:iterator>		
			</s:if>
		</table>
	</div>	
	<div class="popupBody">
		<table class="tblPop" width="100%">	
		<tr>
			<td>&nbsp;</td>
			<td>
				<div class="navBtn" style="float: left;">
					<s:text name="generate_offer_sheet.option.offer_format"/>:&nbsp;
					<img src="images/checkedradiobutton.gif" name='offerFormat' id='offerFormat_<%=OfferSheetConstants.OFFER_FORMAT_TEMPLATE_TYPE%>' 
						 onclick="javascript:setOfferFormat('<%=OfferSheetConstants.OFFER_FORMAT_TEMPLATE_TYPE%>');" 
						 style="margin-bottom:-1px;cursor: pointer; "/>&nbsp;<s:text name="generate_offer_sheet.option.offer_format_template_type"/>&nbsp;&nbsp;
					<img src="images/radiobutton.gif" name='offerFormat' id='offerFormat_<%=OfferSheetConstants.OFFER_FORMAT_PDF%>' 
						 onclick="javascript:setOfferFormat('<%=OfferSheetConstants.OFFER_FORMAT_PDF%>');" 
						 style="margin-bottom:-1px;cursor: pointer;"/>&nbsp;<s:text name="generate_offer_sheet.option.offer_format_pdf"/>&nbsp;&nbsp;
				</div>
				<div id="waitToGenerate" style="float: right;">
					<div class="navBtn" style="float: right;">
							<a href="#" style="width:90px;" class="active" onclick="javascript: validateAndGenerate('generate');"><span class="rightC"></span><span class="leftC"></span><s:text name="generate_offer_sheet.label.save_offer"/></a>
							<a href="#" style="width:70px;margin-left: 5px;" class="active" onclick="javascript: validateAndGenerate('preview');"><span class="rightC"></span><span class="leftC"></span><s:text name="common.preview"/></a>
						<a href="#" style="width:60px; margin-left: 5px;" class="active" onclick="javascript: window.top.hidePopWin(false);return false;"><span class="rightC"></span><span class="leftC"></span><s:text name="common.close"/></a>
					</div>
				</div>
			</td>
		</tr>			
		</table>
	</div>	
	</s:form>
	</div>
</div>
<BR/>
<BR/>
<BR/>
<script>
var checkedRadioImg = 'images/checkedradiobutton.gif';
var radioImg = 'images/radiobutton.gif';
var offerCodeVar = '<%=com.talentPool.offerSheet.constants.OfferSheetConstants.OFFER_CODE%>';

function validateAndGenerate(operation){
	var offerFormat = getOfferFormat();
	for(i = 0; i < templateVars.length; i++) {
		if($(templateVars[i]).value.trim() == '') {
			alert('<s:text name="generate_offer_sheet.error.enter_values_for_template_variables" />');
			hideUpdater('waitToGenerate');
			return false;
		}
	}
	if(offerFormat=='<%=OfferSheetConstants.OFFER_FORMAT_PDF%>' && 
			<%=DocumentConstants.IS_OFFICE_2003%>==true){
			alert('<s:text name="generate_offer_sheet.error.office_2003_to_pdf" />');
			hideUpdater('waitToGenerate');
			return false;
	}else {
		generateOfferSheet(operation);
	}
}

function generateOfferSheet(operation) {
	if('preview' == operation) {
		var d = new Date();
		//document.offerTemplateVariableMapping.target=d;
		document.offerTemplateVariableMapping.action="previewOfferSheet.action";
		//showUpdater('waitToGenerate',{setHeight: false, setWidth: false, offsetLeft: 0});
	} else {
		showUpdater('waitToGenerate',{setHeight: false, setWidth: false, offsetLeft: 0});
		document.offerTemplateVariableMapping.target='_self';
		document.offerTemplateVariableMapping.action="generateOfferSheet.action";
	}
	document.offerTemplateVariableMapping.offerFormat.value=getOfferFormat();
	document.offerTemplateVariableMapping.submit();
	return true;
}

function getFNumber(obj){
	if(obj.value.trim()!=''){
		if(isNaN(obj.value)){
			alert('<s:text name="common.please_enter_valid_number" />');
			obj.focus();
			return false;
		}
	}
}

function onOfferFormatChange(obj, offerFormatVal){
	var imgs = document.getElementsByName(obj.name);
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if( theImage.id == 'offerFormat_'+offerFormatVal){
			theImage.src = checkedRadioImg;
			document.offerTemplateVariableMapping.offerFormat.value=offerFormatVal;
		}else{
			theImage.src = radioImg;
		}
	}
}

function getOfferFormat(){
	var imgs = document.getElementsByName('offerFormat');
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if(theImage.src.indexOf(checkedRadioImg)!=-1){
			var val = theImage.id;
			return val.substring('offerFormat_'.length,val.length);
		}
	}
}

function setOfferFormat(offerFormatVal){
	var imgs = document.getElementsByName('offerFormat');
	for (i = 0; i < imgs.length; i++) {
		var theImage = imgs[i];
		if( theImage.id == 'offerFormat_'+offerFormatVal){
			theImage.src = checkedRadioImg;
			document.offerTemplateVariableMapping.offerFormat.value=offerFormatVal;
		}else{
			theImage.src = radioImg;
		}
	}
}

var tControlName = '';

function onChangeSelectBoxAttributes(indx, obj) {	
	tControlName = obj.getControlName();
	var selId = obj.getSelectedId();
	var value= templateMap[obj.getSelectedId()];
	if(value == undefined)
		$(tControlName).value='';
	else
		$(tControlName).value = value;

	if(selId==offerCodeVar){
		$(tControlName).readOnly=true;
		$(tControlName).title=$(tControlName).value;
	}else {
		$(tControlName).readOnly=false;
		$(tControlName).title='';
	}
	
	var pars = encodeURI("mode=getOfferSheetTemplateVariableVal&applicantId=" + document.offerTemplateVariableMapping.applicantId.value + "&selectedOfferSheetTemplate=" + document.offerTemplateVariableMapping.offerSheetTemplateId.value + "&templateVariable=" + tControlName + "&templateVariableAttribute=" + obj.getSelectedId());
	var myAjax = ajaxCall("selectionProcess.do",'post',pars,onCompleteAction, reportError);
}

function onCompleteAction(request) {
	xmlFile = request.responseXML;
	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	if(!isErrorXml(xmlFile)){
		tControlName = '';
	} else {
		alert('<s:text name="hire.error.get_template_variable_value" />');
	}
}

function setPopupTitle(){
	window.top.setPopTitle('<s:property value="applicantTitle"  />');
}

Event.observe(window, "load", function() {
	setPopupTitle();
	var height = Math.min(templateVars.length * 20 + 190, 450);
	window.top.resizePopUp(650,height);
	if(document.offerTemplateVariableMapping.offerFormat.value!='0'){
		setOfferFormat(document.offerTemplateVariableMapping.offerFormat.value);
	}
});

window.onload = setPopupTitle;
</script>
</logic:empty>