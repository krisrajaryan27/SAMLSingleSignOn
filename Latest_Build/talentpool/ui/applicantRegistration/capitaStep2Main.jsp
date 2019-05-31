<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.talentPool.custom.dataobject.CustomFieldData" %>
<%@ page import="com.talentPool.custom.manager.CustomFieldManager" %>
<%@ page import="com.talentPool.common.utils.CommonUtils" %>
<%@ page import="com.talentPool.common.db.SimpleDataObject" %>

<link rel="stylesheet" type="text/css" href="themes/default/calender.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/autoComplete.css"/>
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css"/>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>		
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>

<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/monthYearCalender.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script language="JavaScript" src="js/customfields/customfieldvalidator.js"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>

<html:form action="/applicantRegistration">
<input type="hidden" name="mode" value="capitaStep3" />
<input type="hidden" name="positionId" value='<%=request.getParameter("positionId")%>'/>
<input type="hidden" name="locationId" value='<%=request.getParameter("locationId")%>'/>
<div class="contentDiv">
<font style="font-size: 20px; font-weight: bold;" ><bean:message key="applicant_registration.label.registration_form" /></font>
<br/><br/><br/>
	<% if (request.getAttribute(Globals.ERROR_KEY) != null) { %>
	<table id="m_errortable">
		<tr>
			<td class="header"><b><bean:message
				key="errors.following_errors" /></b></td>
		</tr>
		<tr>
			<td class="message"><html:errors /></td>
		</tr>
	</table>
	<br/>
	<% } %>
	<% CustomFieldManager manager = new CustomFieldManager(); %>
	<% CustomFieldData title = (CustomFieldData) manager.getCustomFieldByName("app_title",false); %>
	<% CustomFieldData firstName = (CustomFieldData) manager.getCustomFieldByName("app_firstName",false); %>
	<% CustomFieldData lastName = (CustomFieldData) manager.getCustomFieldByName("app_lastName",false); %>	
	<% CustomFieldData dateOfBirth = (CustomFieldData) manager.getCustomFieldByName("app_dateOfBirth",false); %>	
	<% CustomFieldData gender = (CustomFieldData) manager.getCustomFieldByName("app_gender",false); %>	
	<% CustomFieldData refererEmployeeName = (CustomFieldData) manager.getCustomFieldByName("app_refererEmployeeName",false); %>	
	<% CustomFieldData refererEmployeeCode = (CustomFieldData) manager.getCustomFieldByName("app_refererEmployeeCode",false); %>			
	<% CustomFieldData currentEmpFromDate = (CustomFieldData) manager.getCustomFieldByName("app_currentEmpFromDate",false); %>		
	<% CustomFieldData currentEmpToDate = (CustomFieldData) manager.getCustomFieldByName("app_currentEmpToDate",false); %>		
	<% CustomFieldData prevEmp = (CustomFieldData) manager.getCustomFieldByName("app_prevEmp",false); %>		
	<% CustomFieldData prevEmpFromDate = (CustomFieldData) manager.getCustomFieldByName("app_prevEmpFromDate",false); %>		
	<% CustomFieldData prevEmpToDate = (CustomFieldData) manager.getCustomFieldByName("app_prevEmpToDate",false); %>		
	<% CustomFieldData emergencyLine1 = (CustomFieldData) manager.getCustomFieldByName("app_emergencyLine1",false); %>		
	<% CustomFieldData emergencyLine2 = (CustomFieldData) manager.getCustomFieldByName("app_emergencyLine2",false); %>		
	<% CustomFieldData emergencyDist = (CustomFieldData)manager.getCustomFieldByName("app_emergencyDist",false); %>		
	<% CustomFieldData emergencyCity = (CustomFieldData) manager.getCustomFieldByName("app_emergencyCity",false); %>		
	<% CustomFieldData emergencyZip = (CustomFieldData) manager.getCustomFieldByName("app_emergencyZip",false); %>		
	<% CustomFieldData permanentLine1 = (CustomFieldData) manager.getCustomFieldByName("app_permanentLine1",false); %>		
	<% CustomFieldData permanentLine2 = (CustomFieldData) manager.getCustomFieldByName("app_permanentLine2",false); %>		
	<% CustomFieldData permanentDist = (CustomFieldData) manager.getCustomFieldByName("app_permanentDist",false); %>		
	<% CustomFieldData permanentCity = (CustomFieldData) manager.getCustomFieldByName("app_permanentCity",false); %>		
	<% CustomFieldData permanentZip = (CustomFieldData) manager.getCustomFieldByName("app_permanentZip",false); %>
	<% CustomFieldData process = (CustomFieldData) manager.getCustomFieldByName("app_process",false); %>		
	<% CustomFieldData industry = (CustomFieldData) manager.getCustomFieldByName("app_industry",false); %>		
	<% CustomFieldData vertical = (CustomFieldData) manager.getCustomFieldByName("app_vertical",false); %>	
	<% ArrayList degrees = (ArrayList) CommonUtils.getDegrees(); %>
	<% List sourceTypes = (List) request.getAttribute("sourceTypes"); %>
	<table cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td>
				<span class="heading" style="color: #000;"><bean:message key="applicant_registration.label.general_information" /></span>
				<br/><br/>
				<table class="tabinput">
					<tr>
						<td class="label"><%=title.getFieldDisplayName()%>* :</td>						
						<td class="label"><%=title.getUI()%></td>
						<td class="hGap"></td>
						<td class="label"><%=firstName.getFieldDisplayName()%>* :</td>
						<td class="label"><%=firstName.getUI()%></td>
						<td class="hGap"></td>
						<td class="label"><%=lastName.getFieldDisplayName()%>* :</td>
						<td class="label"><%=lastName.getUI()%></td>
					</tr>					
				</table>
				<br/>
				<table class="tabinput">
					<tr>
						<td class="label" ><bean:message key="applicant_registration.label.phone1"/>* :</td>						
						<td class="label"><input type="text" name="phone1" size="25" maxlength="25" class="Grey" /></td>
						<td class="hGap"></td>
						<td class="label"><bean:message key="applicant_registration.label.phone2"/>* :</td>
						<td class="label"><input type="text" name="phone2" size="25" maxlength="25" class="Grey" /></td>
					</tr>
				</table>
				<table class="tabinput">
					<tr>
						<td class="label" ><bean:message key="applicant_registration.label.email"/>* :</td>						
						<td class="label"><input type="text" name="email" size="35" maxlength="50" class="Grey" /></td>						
					</tr>
				</table>
				<br/>
				<table class="tabinput">
					<tr>
						<td class="label"><bean:message key="applicant_registration.label.source" />* :</td>						
						<td class="label">
							<select name="sourceType" onchange="javascript: onChangeSourceType(this);">
								<option value="-1"><bean:message key="common.selectlist.select"/></option>
								<% for(int i = 0; i < sourceTypes.size(); i++) { %>
									<% SimpleDataObject sourceType = (SimpleDataObject) sourceTypes.get(i); %>
									<option value='<%=sourceType.getString("sourceTypeId")%>'><%=sourceType.getString("sourceType")%></option>
								<% } %>
							</select>
						</td>
						<td class="hGap"></td>
						<td class="label"><bean:message key="applicant_registration.label.source_description" />* :</td>
						<td class="label">
							<select name="source">
								<option value="-1"><bean:message key="common.selectlist.select"/></option>
							</select>
						</td>
					</tr>
				</table>
				<table class="tabinput">
					<tr>
						<td class="label"><%=refererEmployeeName.getFieldDisplayName()%> :</td>						
						<td class="label"><%=refererEmployeeName.getUI()%></td>
						<td class="hGap"></td>
						<td class="label"><%=refererEmployeeCode.getFieldDisplayName()%> :</td>
						<td class="label"><%=refererEmployeeCode.getUI()%></td>
					</tr>
				</table>
				<table class="tabinput">
					<tr>
						<td class="label" ><%=gender.getFieldDisplayName()%>* :</td>						
						<td class="label"><%=gender.getUI()%></td>						
					</tr>
					<tr>
						<td class="label" ><%=dateOfBirth.getFieldDisplayName()%>* :</td>						
						<td class="label"><%=dateOfBirth.getUI()%> ( dd/mm/yyyy format )</td>						
					</tr>
				</table>
				<br/><br/>
				<span class="heading" style="color: #000;"><bean:message key="applicant_registration.label.educational_qualification" /></span>
				<br/><br/>
				<table class="tabinput">
					<tr>
						<td class="label"><bean:message key="applicant_registration.label.degree" />* :</td>						
						<td class="label">
							<select name="degree">
								<option value="-1"><bean:message key="common.selectlist.select"/></option>
								<% for(int i = 0; i < degrees.size(); i++) { %>
									<% SimpleDataObject degree = (SimpleDataObject) degrees.get(i); %>
									<option value='<%=degree.getString("degreeId")%>'><%=degree.getString("title")%></option>
								<% } %>
							</select>
						</td>
						<td class="label"><bean:message key="applicant_registration.label.institute_or_university" />* :</td>
						<td class="label"><input type="text" name="institute" size="35" maxlength="150" class="Grey" /></td>
					</tr>
				</table>
				<br/><br/>
				<span class="heading" style="color: #000;"><bean:message key="applicant_registration.label.work_experience" /></span>
				<br/><br/>
				<table class="tabinput">
					<tr>
						<input type="hidden" name="fresher" />
						<td class="label"><input type="checkbox" name="isFresher" style="border:none;" onclick="javascript: if(this.checked){document.forms[0].fresher.value=1;}else{document.forms[0].fresher.value=0;}"/></td>
						<td class="label"><bean:message key="applicant_registration.label.fresher" /></td>
					</tr>
				</table>
				<table class="tabinput">
					<tr>
						<td class="label"><bean:message key="applicant_registration.label.current_employer" /> :</td>						
						<td class="label"><input type="text" name="currentEmp" value="" maxlength="250" size="40" class="Grey" /></td>
					</tr>
					<tr>
						<td class="label"><%=currentEmpFromDate.getFieldDisplayName()%> :</td>						
						<td class="label"><%=currentEmpFromDate.getUI()%> ( dd/mm/yyyy format )</td>
					</tr>
					<tr>
						<td class="label"><%=currentEmpToDate.getFieldDisplayName()%> :</td>						
						<td class="label"><%=currentEmpToDate.getUI()%> ( dd/mm/yyyy format )</td>
					</tr>
					<tr>
						<td class="label"><%=prevEmp.getFieldDisplayName()%> :</td>						
						<td class="label"><%=prevEmp.getUI()%></td>
					</tr>
					<tr>
						<td class="label"><%=prevEmpFromDate.getFieldDisplayName()%> :</td>						
						<td class="label"><%=prevEmpFromDate.getUI()%> ( dd/mm/yyyy format )</td>
					</tr>
					<tr>
						<td class="label"><%=prevEmpToDate.getFieldDisplayName()%> :</td>						
						<td class="label"><%=prevEmpToDate.getUI()%> ( dd/mm/yyyy format )</td>
					</tr>
					<tr>
						<td class="label"><bean:message key="applicant_registration.label.current_ctc" /> :</td>						
						<td class="label"><input type="text" name="currentCTC" maxlength="10" size="10" class="Grey" /></td>
					</tr>
				</table>
				<br/><br/>
				<table cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td>
							<span class="heading" style="color:#000;"><bean:message key="applicant_registration.label.emergency_address" /></span>
							<br/><br/>
							<table class="tabinput">
								<tr>
									<td class="label"><%=emergencyLine1.getFieldDisplayName()%>* :</td>						
									<td class="label"><%=emergencyLine1.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=emergencyLine2.getFieldDisplayName()%> :</td>						
									<td class="label"><%=emergencyLine2.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=emergencyDist.getFieldDisplayName()%>* :</td>						
									<td class="label"><%=emergencyDist.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=emergencyCity.getFieldDisplayName()%>* :</td>						
									<td class="label"><%=emergencyCity.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=emergencyZip.getFieldDisplayName()%> :</td>						
									<td class="label"><%=emergencyZip.getUI()%></td>
								</tr>
							</table>
						</td>
						<td>
							<span class="heading" style="color:#000;"><bean:message key="applicant_registration.label.permanent_address" /></span>
							<br/><br/>
							<table class="tabinput">
								<tr>
									<td class="label"><%=permanentLine1.getFieldDisplayName()%> :</td>						
									<td class="label"><%=permanentLine1.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=permanentLine2.getFieldDisplayName()%> :</td>						
									<td class="label"><%=permanentLine2.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=permanentDist.getFieldDisplayName()%> :</td>						
									<td class="label"><%=permanentDist.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=permanentCity.getFieldDisplayName()%> :</td>						
									<td class="label"><%=permanentCity.getUI()%></td>
								</tr>
								<tr>
									<td class="label"><%=permanentZip.getFieldDisplayName()%> :</td>						
									<td class="label"><%=permanentZip.getUI()%></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<br/><br/>
				<span class="heading" style="color:#000;"><bean:message key="applicant_registration.label.others" /></span>
				<br/><br/>
				<table class="tabinput">
					<tr>
						<td class="label"><%=process.getFieldDisplayName()%>* :</td>						
						<td class="label"><%=process.getUI()%></td>
					</tr>
					<tr>
						<td class="label"><%=industry.getFieldDisplayName()%>* :</td>						
						<td class="label"><%=industry.getUI()%></td>
					</tr>
					<tr>
						<td class="label"><%=vertical.getFieldDisplayName()%>* :</td>						
						<td class="label"><%=vertical.getUI()%></td>
					</tr>
				</table>
			
			</td>			
		</tr>
	</table>
	<br/>
	<div class="navBtn" style="float: left;">
		<a href="#" style="width:60px;margin-top:5px;margin-left: 5px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit" /></a>
	</div>
	<br/><br/><br/><br/><br/>
</div>
</html:form>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<script language="JavaScript">
window.onload=doOnLoad;
function doOnLoad() {
	if('<%=request.getParameter("showDuplicatePage")%>' == "1") {		
		frm = document.forms[0];
		frm.positionId.value='<%=request.getParameter("positionId")%>';	
		frm.locationId.value='<%=request.getParameter("locationId")%>';
		frm.app_currentEmpFromDate.value='<%=request.getParameter("app_currentEmpFromDate")%>';
		frm.app_currentEmpToDate.value='<%=request.getParameter("app_currentEmpToDate")%>';
		frm.app_prevEmp.value='<%=request.getParameter("app_prevEmp")%>';
		frm.app_prevEmpFromDate.value='<%=request.getParameter("app_prevEmpFromDate")%>';
		frm.app_prevEmpToDate.value='<%=request.getParameter("app_prevEmpToDate")%>';
		setVal(frm.app_title,'<%=request.getParameter("app_title")%>');
		frm.app_firstName.value='<%=request.getParameter("app_firstName")%>';
		frm.app_lastName.value='<%=request.getParameter("app_lastName")%>';
		for(var m = 0; m < frm.app_gender.length; m++) {
			if(frm.app_gender[m].value == '<%=request.getParameter("app_gender")%>') {
				frm.app_gender[m].checked=true;
			} else {
				frm.app_gender[m].checked=false;
			}
		}
		frm.app_dateOfBirth.value='<%=request.getParameter("app_dateOfBirth")%>';
		frm.app_refererEmployeeName.value='<%=request.getParameter("app_refererEmployeeName")%>';
		frm.app_refererEmployeeCode.value='<%=request.getParameter("app_refererEmployeeCode")%>';
		frm.app_emergencyLine1.value='<%=request.getParameter("app_emergencyLine1")%>';
		frm.app_emergencyLine2.value='<%=request.getParameter("app_emergencyLine2")%>';
		frm.app_emergencyDist.value='<%=request.getParameter("app_emergencyDist")%>';
		frm.app_emergencyCity.value='<%=request.getParameter("app_emergencyCity")%>';
		frm.app_emergencyZip.value='<%=request.getParameter("app_emergencyZip")%>';
		frm.app_permanentLine1.value='<%=request.getParameter("app_permanentLine1")%>';
		frm.app_permanentLine2.value='<%=request.getParameter("app_permanentLine2")%>';
		frm.app_permanentDist.value='<%=request.getParameter("app_permanentDist")%>';
		frm.app_permanentCity.value='<%=request.getParameter("app_permanentCity")%>';
		frm.app_permanentZip.value='<%=request.getParameter("app_permanentZip")%>';
		setVal(frm.app_process,'<%=request.getParameter("app_process")%>');
		setVal(frm.app_industry,'<%=request.getParameter("app_industry")%>');
		setVal(frm.app_vertical,'<%=request.getParameter("app_vertical")%>');
		frm.phone1.value='<%=request.getParameter("phone1")%>';
		frm.phone2.value='<%=request.getParameter("phone2")%>';
		frm.email.value='<%=request.getParameter("email")%>';
		setVal(frm.sourceType,'<%=request.getParameter("sourceTypeId")%>');
		onChangeSourceType(frm.sourceType);
						
		setVal(frm.degree,'<%=request.getParameter("degreeId")%>');
		frm.institute.value='<%=request.getParameter("institute")%>';
		if('<%=request.getParameter("fresher")%>' == 1) {
			frm.isFresher.checked = true;
		} else {
			frm.isFresher.checked = false;
		}
		frm.fresher.value='<%=request.getParameter("fresher")%>';
		frm.currentEmp.value='<%=request.getParameter("currentEmp")%>';
		frm.currentCTC.value='<%=request.getParameter("currentCTC")%>';
	} else {		
		dt = new Date();		
		document.forms[0].app_currentEmpToDate.value=dt.getDate() + '/'+ dt.getMonth() + '/' + dt.getFullYear();
	}
}

var popUpCal = new CalendarPopup("calDiv");
popUpCal.showNavigationDropdowns();
function getFNumber(obj){
	if(obj.value.trim()!=''){
		if(isNaN(obj.value)){
			alert('<bean:message key="common.please_enter_valid_number" />');
			obj.focus();
			return false;
		}
	}
}

function onChangeSourceType(obj) {	
	var pars = "mode=getSourcesOfSourceType&sourceTypeId=" + obj.value;
	var myAjax = ajaxCall("applicantRegistration.do",'get',pars,populateSources, reportError);
}

function populateSources(request) {	
	xmlFile = request.responseXML;
	if(!isErrorXml(xmlFile)){		
		var val = request.responseText;		
		emptyDropDown();
		if(val != null && val.length > 0) {			
			parts = val.split("()");
			ids = parts[0].split(",");
			names = parts[1].split(",");
			for(i = 0; i < ids.length; i++) {
				var optn = document.createElement("OPTION");
				optn.text = names[i];
				optn.value = ids[i];
				document.forms[0].source[document.forms[0].source.length] = optn;
				
			}
			frm = document.forms[0];
			setVal(frm.source,<%=request.getParameter("sourceId")%>);		
		}
	} else {
		alert('<bean:message key="applicant_registration.error.populate_sources" />');
	}
}
function emptyDropDown() {
	for(var j = document.forms[0].source.length; j > 1; j--) {
		document.forms[0].source[j-1] = null;
	}
}

var dtfo = new DateFormatter();
function getFDate(obj,format){
	dtfo.setDisplayFormat(format);
	if(obj.value.trim()!=''){
	if(!dtfo.checkDate(obj)){
		obj.select();
		//alert("Please enter date in " + format + " format");
		alert('<bean:message key="applicant_registration.error.enter_date_in" />' + format + ' <bean:message key="applicant_registration.error.format" />');
		obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}

function submitForm() {
	errors = validateForm();
	if(errors.length > 0) {
		alert(errors);
		return false;
	} else {
		document.forms[0].submit();
		return true;
	}	
}
function validateForm() {
	errors = '';
	frm = document.forms[0];
	if(frm.app_firstName.value.strip() == '') {
		errors = addError(errors, '- first name');
	}
	if(frm.app_lastName.value.strip() == '') {
		errors = addError(errors, '- last name');
	}
	if(frm.phone1.value.strip() == '') {
		errors = addError(errors, '- phone 1');
	}
	if(frm.phone2.value.strip() == '') {
		errors = addError(errors, '- phone 2');
	}
	if(frm.email.value.strip() == '') {
		errors = addError(errors, '- email');
	} else {
		if(!validateEmailAddress(frm.email.value)) {
			errors = addError(errors, '- valid email');
		}
	}
	if(frm.app_dateOfBirth.value.strip() == '') {
		errors = addError(errors, '- date of birth');
	}
	var selectedGender = app_gender.getSelectedIds();	
	if(selectedGender.strip() == '') {
		errors = addError(errors, '- gender');
	}
	if(frm.sourceType.value.strip() == '-1') {
		errors = addError(errors, '- source type');
	}
	if(frm.source.value.strip() == '-1') {
		errors = addError(errors, '- source');
	}
	if(frm.degree.value.strip() == '-1') {
		errors = addError(errors, '- degree');
	}
	if(frm.institute.value.strip() == '') {
		errors = addError(errors, '- institute');
	}
	if(!frm.isFresher.checked) {
		if(frm.currentEmp.value.strip() == '') {
			errors = addError(errors, '- current employer');
		}
		if(frm.app_currentEmpFromDate.value.strip() == '') {
			errors = addError(errors, '- current employment from date');
		}
		if(frm.app_currentEmpToDate.value.strip() == '') {
			errors = addError(errors, '- current employment till date');
		}	
		if(frm.currentCTC.value.strip() == '') {
			errors = addError(errors, '- current CTC');
		}	
	}
	if(frm.app_emergencyLine1.value.strip() == '') {
		errors = addError(errors, '- emergency address line 1');
	}
	if(frm.app_emergencyDist.value.strip() == '') {
		errors = addError(errors, '- emergency address district');
	}
	if(frm.app_emergencyCity.value.strip() == '') {
		errors = addError(errors, '- emergency address city');
	}
	if(errors != '') {
		errors = addError('Following data is required/missing:', errors);
	}
	return errors;
}
function validateEmailAddress(email_address) {
	var pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
    if(pattern.test(email_address)){         
		return true;
    }else{   
		return false;
    }
}
function addError(errors, error) {
	if(errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}

function setVal(obj, val) {
	for(var k = 0; k < obj.length; k++) {
		if(obj[k].value == val) {
			obj[k].selected="selected";
			break;
		}
	}
}
</script>