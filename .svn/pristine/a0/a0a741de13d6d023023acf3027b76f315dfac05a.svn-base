<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<div class="contentDiv">
<font style="font-size: 20px; font-weight: bold;" ><bean:message key="applicant_registration.label.registration_form" /></font>
<br/><br/>
	<table class="tabinput">
		<tr>
			<td><bean:message key="applicant_registration.label.location" />:</td>
			<td>
				<script language="JavaScript">
					var opts = <bean:write name="strLocations" scope="request" filter="false"/>;
					var m = [new SelectOption('-1','<bean:message key="applicant_registration.label.select_location"/>')];
					opts = m.concat(opts);
					var selectBoxLocation = new SelectBox(opts,'-1','images/btn_dropdown.gif',{namesonly:false, width:'150px'});
					selectBoxLocation.setOnChangeHandler('onChangeSelectBoxLocation');
					document.write(selectBoxLocation.getHtml());
					selectBoxLocation.init();
				</script>
			</td>
		</tr>
	</table>
	<table class="tabinput" width="450px">
		<tr>			
			<td>
				<div class="popupBody outerDiv">
					<table class="tabinput">
						<tr>
							<td colspan="2">
								<input type="radio" name="registerOption" value="1" checked="true" style="border:none;background-color:#EFEFEF;" onclick="javascript: onChangeRadio(this);"/><bean:message key="applicant_registration.label.register_with_no_position" />&nbsp;<bean:message key="common.position" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="radio" name="registerOption" value="2" style="border:none;background-color:#EFEFEF;" onclick="javascript: onChangeRadio(this);"/><bean:message key="applicant_registration.label.register_with_position" />&nbsp;<bean:message key="common.position" />
							</td>
						</tr>
						<tr style="height:19px;">
							<td width="12px">&nbsp;</td>
							<td>
								<script language="JavaScript">
									var m = [new SelectOption('-1','<bean:message key="common.select_position_option"/>')];
									var selectBoxPosition = new SelectBox(m,'-1','images/btn_dropdown.gif',{namesonly:false, width:'350px'});
									document.write(selectBoxPosition.getHtml());
									selectBoxPosition.init();					
								</script>
							</td>
						</tr>
					</table>
				</div>
			</td>			
		</tr>
		<tr>
			<td colspan="2">
				<div class="navBtn" style="margin-top:5px;float:left;">
					<a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
				</div>
			</td>
		</tr>
	</table>
</div>
<script language="JavaScript">
function onChangeRadio(obj) {
	if(obj.value == "1") {
		selectBoxPosition.hideMe();
	} else {
		selectBoxPosition.showMe();
	}
}
function onChangeSelectBoxLocation() {
	var id = selectBoxLocation.getSelectedId();
	var pars = "mode=getPublishedForWalkInPositionsForALocation&locationId=" + id;
	var myAjax = ajaxCall("applicantRegistration.do",'get',pars,populatePositions, reportError);
}
function populatePositions(request) {	
	xmlFile = request.responseXML;
	if(!isErrorXml(xmlFile)){		
		var op = request.responseText;
		var opts = eval(op);		
        opt = [new SelectOption('-1','<bean:message key="common.select_position_option"/>')];
		m = opt.concat(opts);
		selectBoxPosition.reInitialize(m, '-1');
	} else {
		alert('<bean:message key="applicant_registration.error.populate_position" /> <bean:message key="common.positions" />.');
	}
}
function submitForm() {
	var errors = '';
	var id = selectBoxLocation.getSelectedId();
	if(id == '-1') {
		//errors += 'Please select location.';
		errors += '<bean:message key="applicant_registration.error.select_location"/>';
	}
	var val = '';
	for(var i = 0; i < document.getElementsByName("registerOption").length; i++) {
		if(registerOption[i].checked) {
			val = registerOption[i].value;
			break;
		}
	}
	if(val == 2 && selectBoxPosition.getSelectedId() == '-1') {
		if(errors.length > 0) { 
			errors += '\n';
		}
		//errors += 'Please select position to apply for.';
		errors += '<bean:message key="common.please_select"/> <bean:message key="common.position"/> <bean:message key="applicant_registration.error.select_position"/>';
		
		
	}
	if(errors.length > 0) {
		alert(errors);
	} else {
		var  url = "applicantRegistration.do?mode=capitaStep2&locationId=" + selectBoxLocation.getSelectedId() + "&positionId=" + selectBoxPosition.getSelectedId();
		window.location =  url;
	}
}
window.onload=doOnLoad;
function doOnLoad() {
	selectBoxPosition.hideMe();
}
</script>