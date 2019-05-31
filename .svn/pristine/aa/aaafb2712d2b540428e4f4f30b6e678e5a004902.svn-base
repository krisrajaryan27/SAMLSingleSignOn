<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.talentPool.applicant.form.ApplicantFromWebForm,com.talentPool.applicant.ApplicantConstants,com.talentPool.common.utils.CommonUtils,com.talentPool.common.NavigationConstants,com.talentPool.common.properties.TPApplicationProperties"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.talentPool.custom.dataobject.CustomFieldData"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.dataobject.ImportFieldData"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%>
<%@page import="com.talentPool.custom.utils.CustomFieldUtils"%>
<%@ page import="com.talentPool.common.db.SimpleDataObject"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.masters.constants.RecentlyUsedSourceConstants"%>
<%@page import="com.talentPool.recaptcha.properties.ReCaptchaProperties"%>
<link rel="stylesheet" type="text/css" href="themes/default/calender.css"/>
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
<% ArrayList customFields = (ArrayList)request.getAttribute("customFields"); %>
<script>
var RecaptchaOptions = {
   theme : 'white'
};

<%=CustomFieldUtils.getArrayForCustomFields(customFields)%>
var isEducationDisplayed = false;
</script>

<%
int totalEduRows = 0;
ArrayList importFields = ImportConfigurationManager.getImportFields();
ApplicantFromWebForm applicantFromWebForm = (ApplicantFromWebForm) request.getAttribute("applicantFromWebForm");

ArrayList skills = CommonUtils.getSkills();
%>
<script language="JavaScript">

</script>
<div style="margin-left:10px; text-align:left;">
	<%
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
	<table  id="m_errortable" >
		<tr>
	    <td class="header">
	    	<b><label class="star"><bean:message key="errors.following_errors"/></b>
	    </td>
		</tr>
    <tr>
    	<td class="message"><label class="star"><html:errors/></label></td>
    </tr>
	</table>
	<br>
	<% } %>	
<table cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td>
			<html:form action="/applicantRegistrationFromWeb" enctype="multipart/form-data">
				<html:hidden property="mode" value="addApplicant" />
				<html:hidden property="primarySkillIds" name="applicantFromWebForm" /> 
				<html:hidden property="fresher" name="applicantFromWebForm" /> 
				<html:hidden property="positionId" name="applicantFromWebForm"/>								
				<%
					for(int j=0; j<importFields.size();j++){
						ImportFieldData fieldData = (ImportFieldData)importFields.get(j);
						String fieldId = fieldData.getFieldId();
						String fieldType = fieldData.getFieldType();
						String showValue = fieldData.getFieldWebsiteShow();
						String isMandatory = fieldData.getFieldWebsiteMandatory();
						
						if(showValue.equals(ImportConfigurationConstants.FIELD_SHOW)){						
						if(fieldType.equals(ImportConfigurationConstants.FIELD_TYPE_NORMAL)){
							if(fieldId.equals(ImportConfigurationConstants.FIELD_NAME)){
				%>
				<table>
				<tr>
					<td	style="width:200px;">
							<bean:message key="common.name" />
							<span class="star">*</span>
							:
					</td>
					<td>
						<html:text property="applicantName" name="applicantFromWebForm" size="35" maxlength="100" />
					</td>
				</tr>
				</table>
				<%	} if(fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL1)){
				%>
				<table>
				<tr>
					<td	style="width:200px;">
						<bean:message key="common.email1" />
						<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
						:
					</td>
					<td>
						<html:text property="applicantEmail1" name="applicantFromWebForm" size="35" maxlength="50"></html:text>							
					</td>
				</tr>
				</table>
				<%  } if(fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL2)){
				%>
				<table>
				<tr>
					<td	style="width: 200px;">
						<bean:message key="common.email2" />
						<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
						:
					</td>
					<td>
						<html:text property="applicantEmail2" name="applicantFromWebForm" size="35" maxlength="50"></html:text>
					</td>
				</tr>
				</table>
				<%  } if(fieldId.equals(ImportConfigurationConstants.FIELD_PHONE1)){
				%>
				<table>
				<tr>
					<td style="width:200px;"><bean:message key="common.phone1" />
					<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
					<% } %>
						:</td>
					<td> 
						<html:text property="applicantHomePhone" name="applicantFromWebForm" size="22" maxlength="25" />
					</td>
				</tr>
				</table>
				<%  } if(fieldId.equals(ImportConfigurationConstants.FIELD_PHONE2)){
				%>
				<table>
				<tr>
					<td style="width:200px;"><bean:message key="common.phone2" />
					<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
					:</td>
					<td>
						<html:text property="applicantWorkPhone" name="applicantFromWebForm" size="22" maxlength="25"/>
					</td>
				</tr>
				</table>
				<%	} if(fieldId.equals(ImportConfigurationConstants.FIELD_MOBILE)){
				%>
				<table>
				<tr>
					<td style="width:200px;"><bean:message key="common.mobile" />
					<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
					<% } %>
					:</td>
					<td>
						<html:text property="applicantCellPhone" name="applicantFromWebForm" size="22" maxlength="25" />
					</td>
				</tr>
				</table>
				<% } if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_LOCATION)){
				%> <table>
				<tr>
					<td style="width:200px;"><bean:message key="common.current_location" />
					<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
					<% } %>
					:</td>					
					<td>
						<html:text property="applicantCity"	name="applicantFromWebForm" size="35" styleId="applicantCity" maxlength="50" />
					</td>
				</tr>
				</table>
				<% }if(fieldId.equals(ImportConfigurationConstants.FIELD_SKILLS)){
				%>
				<table>
				<tr>
					<td style="width:200px;"><bean:message key="common.skills" />
					<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
					<% } %>
					</td>
				</tr>
				</table>
				<table>
					<tr>
						<input type="hidden" name="skillIds" value='<%=request.getParameter("skillIds")%>' />
						<td style="width:200px;">
							<select name="skills" size="5" class="select" style="width:200px;;">
								<% for(int i = 0; i < skills.size(); i++) { %>
									<% SimpleDataObject skill = (SimpleDataObject) skills.get(i); %>
									<option value='<%=skill.getString("skillId")%>'><%=Utils.escapeHTML(skill.getString("skillName"))%></option>
								<% } %>
							</select>
						</td>
						<td style="width:35px;">																												
							<div style="margin:2px;">
								<input type="button" onclick="javascript: add(document.forms[0].skills, document.forms[0].selectedSkills);return false;" title="Add" name="right" value=">>">
								<input type="button" onclick="javascript: removeOption(document.forms[0].skills, document.forms[0].selectedSkills);return false;" title="Remove" name="left" value="<<"> 
							</div>									
						</td>
						<td style="width:200px;">
							<select name="selectedSkills" size="5" style="width:200px;">
								
							</select>
						</td>
					</tr>
				</table>
				<% } if(fieldId.equals(ImportConfigurationConstants.FIELD_EDUCATION)){
				%>
				<script>
				isEducationDisplayed = true; 
				</script>
				<table>
				<tr>					
					<td style="width:200px;"><bean:message key="common.education" />
					<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
					<% } %>
					</td>
				</tr>
				</table>			
				<table id="eduTable" width="483">
					<tbody>
						<tr>
							<td class="header"></td>
							<td class="header"><bean:message key="add_applicant.label.from_year" /></td>
							<td class="header"><bean:message key="add_applicant.label.year_of_passing" /></td>
							<td class="header"><bean:message key="add_applicant.label.institute" /></td>
							<td class="header"><bean:message key="add_applicant.label.degree" /></td>
							<td class="header"><bean:message key="add_applicant.label.branch" /></td>
							<td class="header"><bean:message key="add_applicant.label.class" /></td>
							<td class="header"><bean:message key="add_applicant.label.remarks" /></td>
						</tr>
						<tr>
							<td colspan="6" class="headerBottom"></td>
						</tr>
						<tr id="rowId0">
							<td><input type="radio" class="radio" name="selectedEdu" id="selectedEdu0" checked/></td>
							<td><input type="text" name="fromYear" id="fromYear0" size="4" onblur="getFormattedYear(this);" class="Grey" /></td>
							<td><input type="text" name="yearOfPassing" id="yearOfPassing0" size="4" onblur="getFormattedYear(this);" class="Grey" /></td>
							<td><input type="text" name="institute" id="institute0" size="27" maxlength="150" class="Grey" /></td>
							<td>
								<select id="degreeSelect0" name="degreeSelect" style="width: 150px;">
								<option value="-1">--Select--</option>
								<% 
									ArrayList<SimpleDataObject> degreeObj = CommonUtils.getDegrees();
									for (Iterator iterator = degreeObj.iterator(); iterator.hasNext();) {
										SimpleDataObject degreeData = (SimpleDataObject)iterator.next();
								%>
									<option value="<%=degreeData.getString("degreeId") %>"><%=Utils.escapeHTML(degreeData.getString("title"))%> </option>
								<%	} %>
								</select>
							</td>
							<td>
								<select id="branchSelect0" name="branchSelect" style="width: 150px;">
								<option value="-1">--Select--</option>
								<% 
									ArrayList<SimpleDataObject> branchesObj = CommonUtils.getBranches();
									for (Iterator iterator = branchesObj.iterator(); iterator.hasNext();) {
										SimpleDataObject branchData = (SimpleDataObject)iterator.next();
								%>
									<option value="<%=branchData.getString("branchId") %>"><%=Utils.escapeHTML(branchData.getString("branchName"))%> </option>
								<%	} %>
								</select>
							</td>
							<td><input type="text" name="grade" id="grade0" size="6" maxlength="100"/></td>
							<td><textarea name="remarks" id="remarks0" rows="3" cols="8"></textarea></td>
						</tr>
					</tbody>
				</table>
				<table width="100%">
				<tr>
					<td style="text-align: right;">
						<a href="#"	onclick="deleteEducation();"><bean:message key="common.delete" /> <bean:message key="common.selected" /></a>&nbsp;|&nbsp;
						<a href="#" onclick="addEducation();"><b><bean:message key="common.more" />>></b></a>
					</td>
				</tr>
				</table>
				<% } if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPERIENCE)){
				%>
				<table>
					<tr>
						<td	style="width: 200px;">
							<bean:message key="common.working_since" />
							<span class="star">*</span>
							:&nbsp;
						</td>
						<td>
							<html:text property="applicantWorkingSince" name="applicantFromWebForm" size="10" maxlength="25" styleId="applicantWorkingSince" onblur="getFormattedDate(this);"/>&nbsp;
							<img src="images/ico_cal.gif" onclick="cal.showCalender('applicantWorkingSince','applicantWorkingSince');" class="CalImg" />&nbsp;
							<bean:message key="add_applicant.content.working_since" /> &nbsp;&nbsp;&nbsp;
							
							<input type="checkbox" class="checkbox" name="fresher" id="fresher" value="0" onclick="changeValue();">
							&nbsp;Fresher
						</td>
					</tr>
				</table>
				<% } if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER)){
				%>
				<table>
					<tr>
						<td style="width:200px;"><bean:message key="common.current_employer" />
						<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
						:</td>
						<td>
							<html:text property="applicantCurrentEmployer" name="applicantFromWebForm" size="40" maxlength="250" styleId="applicantCurrentEmployer"></html:text>							
						</td>
					</tr>
				</table>
				<% } if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_CTC)){
				%>
				<table>
					<tr>
						<td style="width:200px;"><bean:message key="common.current_ctc" />
						<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
						:</td>
						<td><html:text property="currentCTC" name="applicantFromWebForm" size="10" maxlength="10" styleId="currentCTC"/>
						</td>
					</tr>
				</table>
				<%	} if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPECTED_CTC)){
				%>
				<table>
					<tr>
						<td style="width: 200px;"><bean:message key="common.expected_ctc" />
						<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
						:</td>
						<td><html:text property="expectedCTC" name="applicantFromWebForm"	size="10" maxlength="10" styleId="expectedCTC" />
						</td>
					</tr>
				</table>
				<% } if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTICE_PERIOD)){
				%>
				<table>
					<tr>
						<td style="width:200px;"><bean:message	key="common.time_to_join" />
						<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
						:</td>
						<td><html:text property="noticePeriod" name="applicantFromWebForm" size="20"  styleId="timeToJoin" />
						</td>
					</tr>
				</table>
				<% } if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTE)){
				%>
				<table>
					<tr>
						<td style="width:200px;"><bean:message key="common.note" />
						<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
						<span class="star">*</span>
						<% } %>
						</td>
					</tr>
				</table>
				<table>
				<tr>
					<td>
						<html:textarea property="applicantNote"	name="applicantFromWebForm" rows="3" cols="65"></html:textarea>
					</td>
				</tr>
				</table>
				<% }	
					}else{
						if(customFields!=null && customFields.size()>0){ 
							for(int i=0; i<customFields.size();i++){
							CustomFieldData data = (CustomFieldData)customFields.get(i);
							String customFieldId = data.getFieldName();
								if(fieldId.equals(customFieldId)){
								%>
									<table>
									<tr>
										<td style="width: 200px;"><%=Utils.escapeHTML(data.getFieldDisplayName())%>
										<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
										<span class="star">*</span>
										<%} %>:</td>
										<td><%=data.getUI()%></td>
									</tr>
									</table>
								<%
								}
							} 
					 	} 
					}
				  }
				} 
				%>				
				<table>
					<tr>
						<td style="width:200px;">					
							<bean:message key="inbox.button.label.upload_resume"/>							
						</td>
						<td><html:file property="attachedFile" name="applicantFromWebForm" style="width:340px;height:20px; " value=""></html:file></td>
					</tr>
				</table>
				<table>
					<tr>
						<td>
							<script type="text/javascript"
							   	src="http://api.recaptcha.net/challenge?k=<%=ReCaptchaProperties.getProperty("public.key")%>">
							</script>						
							<noscript>
							   <iframe src="http://api.recaptcha.net/noscript?k=<%=ReCaptchaProperties.getProperty("public.key")%>"
							       height="300" width="500" frameborder="0"></iframe><br>
							   <textarea name="recaptcha_challenge_field" rows="3" cols="40">
							   </textarea>
							   <input type="hidden" name="recaptcha_response_field" 
							       value="manual_challenge"> 
							</noscript>
						</td>
					</tr> 			   		
				</table>
				<table>
					<tr>
					   <td style="width:200px;">	
							<input class="button" type="button" name="addApplicant" value="Submit" onclick="onSubmit();">
						</td>
					</tr>
				</table>				
			</html:form>
			</td>
		</tr>
	</table>
	
	<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
	</div>
	<div id="divCalender" class="myCalender"></div>
<script language="javascript">

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

function add(masterList, selectedList) {
	  var j;
	  for (var i = 0; i < masterList.length; i++) {
	    if (masterList[i].selected) {
	      val = masterList[i].value;
	      dispval = masterList[i].text;
	      for (j = 0; j < selectedList.length; j++) {
	        if (selectedList[j].value == val) {
	          break;
	        }
	      }
	      if (j == selectedList.length) {
	    	var newOption = createOption(dispval,val);  
	    	selectedList.appendChild(newOption);    
	        masterList[i] = null;
	        break;
	      }
	    }
	  }
}

function removeOption(masterList, selectedList) {
	  var j;
	  for (var i = 0; i < selectedList.length; i++) {
	    if (selectedList[i].selected) {
	      val = selectedList[i].value;
	      dispval = selectedList[i].text;
	      for (j = 0; j < masterList.length; j++) {
	        if (masterList[j].value == val) {
	          break;
	        }
	      }
	      if (j == masterList.length) {
	    	var newOption = createOption(dispval,val);      
	    	masterList.appendChild(newOption);
	        selectedList[i] = null;
	        break;
	      }
	    }
	  }
}

// set default value used in validation
document.getElementById('fresher').value=0;

function changeValue(){
	var obj = document.getElementById('fresher');
	if(obj.value==0){
		obj.value=1;
	}else{
		obj.value=0;
	}
}

var frm = document.applicantFromWebForm;

function onSubmit(){
	if(!validateForm()){
		return false;
	}
	setSelectedSkills();	
	document.applicantFromWebForm.submit();
}

function setSelectedSkills() {
	var skillsStr = '';
	for (var j = 0; j < document.forms[0].selectedSkills.length; j++) {
        if(skillsStr.length > 0) {
        	skillsStr += ',';
        }
        skillsStr += document.forms[0].selectedSkills[j].value;
    }
    document.forms[0].primarySkillIds.value=skillsStr;
}

function validateForm(){
	validCustomfields('<%=CustomFieldConstants.DEFAULT_SELECT_OPTION%>', '<%=CustomFieldConstants.TYPE_DROPDOWN%>', '<%=CustomFieldConstants.TYPE_LISTBOX%>', '<%=CustomFieldConstants.TYPE_CHECKBOX%>', '<%=CustomFieldConstants.TYPE_RADIO%>', false);
	<%
	for(int j=0; j<importFields.size();j++){
		ImportFieldData fieldData = (ImportFieldData)importFields.get(j);
		String fieldId = fieldData.getFieldId();
		String fieldType = fieldData.getFieldType();
		String showValue = fieldData.getFieldWebsiteShow();
		String mandatory = fieldData.getFieldWebsiteMandatory();
		
		if(showValue.equals(ImportConfigurationConstants.FIELD_SHOW)){						
			if(fieldType.equals(ImportConfigurationConstants.FIELD_TYPE_NORMAL)){
				if(fieldId.equals(ImportConfigurationConstants.FIELD_NAME)){
					%>
					if(frm.applicantName.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.name" />");
						frm.applicantName.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL1) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.applicantEmail1.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.email1" />");
						frm.applicantEmail1.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL2) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.applicantEmail2.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.email2" />");
						frm.applicantEmail2.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_PHONE1) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.applicantHomePhone.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.phone1" />");
						frm.applicantHomePhone.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_PHONE2)&& mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY) ){
					%>
					if(frm.applicantWorkPhone.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.phone2" />");
						frm.applicantWorkPhone.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_MOBILE)&& mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.applicantCellPhone.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.mobile" />");
						frm.applicantCellPhone.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_LOCATION) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.applicantCity.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.current_location" />");
						frm.applicantCity.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_SKILLS) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.primarySkillIds.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.skills" />");
						frm.rawPrimarySkills.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_EDUCATION) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					var rowPresent = false;
					ctlcnt=0;
					for(var z=0; z<=rowIndex; z++){
						if($('yearOfPassing'+z)){
							if($('yearOfPassing'+z).value.trim()!='' || 
								$('institute'+z).value.trim()!='' ||
								$('degreeSelect'+z).value!=-1 ||
								$('branchSelect'+z).value!=-1 ||
								$('grade'+z).value.trim()!='' || 
								$('remarks'+z).value.trim()!='' || 
								$('fromYear'+z).value.trim()!=''){
								ctlcnt++;
								rowPresent = true;
							}
						}
					}
					if(!rowPresent){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.education" />");
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPERIENCE)){
					%>
					if(document.getElementById('fresher').value!=1){
						if(frm.applicantWorkingSince.value==""){
							alert("<bean:message key="common.please_enter" /> <bean:message key="common.working_since" /> <bean:message key="common.date" /> ");
							frm.applicantWorkingSince.focus();
							return false;
						}
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.applicantCurrentEmployer.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.current_employer" />");
						frm.applicantCurrentEmployer.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_CTC) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.currentCTC.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.current_ctc" />");
						frm.currentCTC.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPECTED_CTC) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.expectedCTC.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.expected_ctc" />");
						frm.expectedCTC.focus();
						return false;
					}
					<%
				}
				if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTICE_PERIOD) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.timeToJoin.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.notice_period" />");
						frm.timeToJoin.focus();
						return false;
					}
					<%
				}
				
				if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTE) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
					%>
					if(frm.applicantNote.value.trim()==""){
						alert("<bean:message key="common.please_enter" /> <bean:message key="common.note" />");
						frm.applicantNote.focus();
						return false;
					}
					<%
				}
			}else {
				//validate custom fields			
				for (int c = 0; customFields != null && c < customFields.size(); c++) {
					CustomFieldData data = (CustomFieldData)customFields.get(c);
					if (fieldId.equals(data.getFieldName()) && showValue.equals(ImportConfigurationConstants.FIELD_SHOW) && mandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)) {
						%>
						if($('<%=data.getFieldName()%>').value == '') {
							alert("<bean:message key="common.please_enter" /> <%=data.getFieldDisplayName()%>");
							return false;
						}		
						<%
					}
				}
			}
		}
	}
	%>
	return true;	
}

var eduCount = 0;
var rowIndex = 0; 
var eduTable = $('eduTable');

//bind event to first row of table education if only education is allowed to show
if(isEducationDisplayed){ 
	addListener(document.getElementById("rowId0"));
}

function addEducation() {
	var tBody = eduTable.getElementsByTagName('tbody')[0];
	var myRow = document.createElement("tr");
	rowIndex++;
	eduCount++;
	lastElm = rowIndex;
	myRow.id="rowId"+lastElm;
	addListener(myRow);
	
	var myCell = document.createElement("td");
	myCell.innerHTML = "<input type=radio class=radio name=selectedEdu id=selectedEdu"+lastElm+">"
	myRow.appendChild(myCell);	
	
	myCell = document.createElement("td");
	var el;
	el = createFormElement("input","text","fromYear","","","fromYear"+lastElm);	
	el.size="4";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var el;
	el = createFormElement("input","text","yearOfPassing","","","yearOfPassing"+lastElm);	
	el.size="4";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	

	myCell = document.createElement("td");
	var el;
	el = createFormElement("input","text","institute","","","institute"+lastElm);	
	el.size="27";
	el.maxlength="150";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	

	myCell = document.createElement("td");
		
	var selectBoxDegree = createSelectBox('degreeSelect','degreeSelect'+lastElm)
	<% 
	ArrayList<SimpleDataObject> degreeObj = CommonUtils.getDegrees();
	for (Iterator iterator = degreeObj.iterator(); iterator.hasNext();) {
		SimpleDataObject degreeData = (SimpleDataObject)iterator.next();
	%>
		var newOption = document.createElement('OPTION');
		newOption.value = "<%=degreeData.getString("degreeId") %>";
		newOption.innerHTML = "<%=Utils.escapeHTML(degreeData.getString("title"))%>";
		selectBoxDegree.appendChild(newOption);
			
    <% } %>  
	
	myCell.appendChild(selectBoxDegree);	
	myRow.appendChild(myCell);

	
	myCell = document.createElement("td");
	var selectBoxBranch = createSelectBox('branchSelect','branchSelect'+lastElm)
	<% 
	ArrayList<SimpleDataObject> branchObj = CommonUtils.getBranches();
	for (Iterator iterator = branchObj.iterator(); iterator.hasNext();) {
		SimpleDataObject branchDataObj = (SimpleDataObject)iterator.next();
	%>
		var newOption = document.createElement('OPTION');
		newOption.value = "<%=branchDataObj.getString("branchId") %>";
		newOption.innerHTML = "<%=Utils.escapeHTML(branchDataObj.getString("branchName"))%>";
		selectBoxBranch.appendChild(newOption);
	    
    <% } %>
	myCell.appendChild(selectBoxBranch);
	myRow.appendChild(myCell);

	myCell = document.createElement("td");
	var el;
	el = createFormElement("input","text","grade","","","grade"+lastElm);	
	el.size="6";
	el.maxlength="100";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	
	
	myCell = document.createElement("td");
	var el=document.createElement("textarea");
	el.name="remarks";
	el.rows="3";
	el.cols="8";
	el.className+="Grey";
	el.id="remarks"+lastElm;
	myCell.appendChild(el);
	myRow.appendChild(myCell);
	
	tBody.appendChild(myRow);
}

function createOption(displayValue,value){
	var newOption = document.createElement('OPTION');
	newOption.value = value;
	newOption.innerHTML = displayValue;
	return newOption;
}

function createSelectBox(name,id){
	var selectBox = document.createElement('SELECT');
	selectBox.name= name;
	selectBox.id = id;
	selectBox.style.width="150px";
	
	var newOption = document.createElement('OPTION');
	newOption.value = "-1";
	newOption.innerHTML = "--Select--";
	selectBox.appendChild(newOption);	

	return selectBox;
}


function addListener(row){
	Event.observe(row, "click", onRowClick.bindAsEventListener(this));
	Event.observe(row, "keydown", onRowKeyDown.bindAsEventListener(this));
	Event.observe(row, "keyup", onRowKeyUp.bindAsEventListener(this));
}

function onRowClick(event){
	var row = Event.findElement(event, 'tr');
	var radioId = "selectedEdu" + row.id.split("rowId")[1];
	var radioEle = document.getElementById(radioId);
	radioEle.checked= "true";
}

function onRowKeyDown(event){
	onRowClick(event);
}
function onRowKeyUp(event){
	onRowClick(event);
}
function onEduOptClick(event) {
	var el =Event.element(event); 
}

function getSelectedEducation() {
	var edu_id = '';
	var radioBtn = document.getElementsByName("selectedEdu");
	for(var i = 0; i < radioBtn.length; i++) {
		if(radioBtn[i].checked){
			edu_id=radioBtn[i].id;
		}
	}
	return edu_id;
}

function deleteEducation() {
	var edu_id = getSelectedEducation();
	if(edu_id!=''){
		var row_id = "rowId" + edu_id.split('selectedEdu')[1];
		var row = document.getElementById(row_id);
		row.parentNode.removeChild(row);
		eduCount--;
	}else{
		alert("Please select the row to delete.");
	}
}

var cal = new MonthYearCalender('divCalender');
cal.setDisplayFormat('MMM-YYYY');
cal.setDateStyle('EU');

var dtf = new DateFormatter();
dtf.setDisplayFormat('MMM-YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
	if(!dtf.checkDate(obj)){
		obj.select();
		alert("<bean:message key="add_applicant.errors.working_since_invalid_format"/>");
		obj.focus();
		return false;
	}else {
		return true;
	}
	}
	return true;
}

var yrf= new DateFormatter();
yrf.setDisplayFormat('MMM-YYYY');

function getFormattedYear(obj){
	if(obj.value.trim()!=''){
		if(yrf.getDateObject(obj.value)){
			obj.value=yrf.getDateObject(obj.value).getFullYear();
		}else if(yrf.getDateObject('01-'+obj.value)){
			obj.value=yrf.getDateObject('01-'+obj.value).getFullYear();
		}else{
			alert('<bean:message key="add_applicant.errors.yearofoassing_invalid_format"/>');
			obj.focus();
			return false;
		}
	}
	return true;
}
</script>