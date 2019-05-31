<%@page import="com.talentPool.socialNetwork.constants.SocialMediaConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
                  com.talentPool.common.NavigationConstants, 
                  com.talentPool.user.dataobject.RoleData,
                  com.talentPool.user.UserConstants,
                  com.talentPool.user.form.UserForm,
                  com.talentPool.common.utils.Utils,
                  com.talentPool.socialNetwork.manager.SocialMediaManager,
                  java.util.ArrayList"%>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>   

<script src="js/calender/CalendarPopup.js" type="text/javascript"></script>		
<script src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script src="js/scripta/src/effects.js" type="text/javascript"></script>
<script src="js/scripta/src/controls.js" type="text/javascript"></script>

<script src="js/commonFunctions.js" type="text/javascript"></script>
<script src="js/ajaxfunctions.js" type="text/javascript"></script>
<script src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script src="js/calender/monthYearCalender.js" type="text/javascript"></script>
<script src="js/selectoption.js" type="text/javascript"></script>
<script src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script src="js/utils/updater.js" type="text/javascript"></script>
<script src="js/customfields/customfield.js"></script>
<script src="js/customfields/customfieldvalidator.js"></script>
<script src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>

<% UserForm userForm = (UserForm) request.getAttribute("userForm"); %>
               
<html:form action="/user" onsubmit="return submitForm();">
<html:hidden property="mode" name="userForm" value="saveSocialProfile"/>
<html:hidden property="t" name="userForm"/>
<html:hidden property="st" name="userForm"/>
<html:hidden property="userId" name="userForm"/>
<html:hidden property="sourceId" name="userForm"/>

<input type="hidden" id="formChangedFlag" name="formChangedFlag"/>
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
	<br>
	<% } %>
	<%
		String saved = (String)request.getAttribute("saved");
		if(saved !=null){
	%>
			<table  id="m_errortable" > 
				<tr>
			    <td class="header">
			        <b><bean:message key="my_account.label.update_success"/></b>
			    </td>               
				</tr>
			</table>
			<br>
	<%
		}
	%>
	</div>
</div>
<div class="contentDivPop" style="padding-right:20px;">
	<table style="width: 100%; border-spacing: 0; padding: 0;" class="boxHeader">		
		<tr>
			<td class="header" height="18"><strong><bean:message key="my_account.label.social_profile"/></strong></td>
		</tr>
	</table>
	<div class="outerDiv" style="border-top:none;padding:10px 0px 10px 0px;">
		<table style="border: 0; border-spacing: 0; padding: 0;" class="posinput">
	    <!-- <tr>
	        <td class="label" height="20"><bean:message key="account_settings.label.username"/></td>
	        <td></td>
	        <td><bean:write name="userForm" property="userName"/></td>
	    </tr>	-->						            
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.fname"/></td>
	        <td></td>
	        <td><html:text property="firstName" size="20" maxlength="15" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.lname"/></td>
	        <td></td>
	        <td><html:text property="lastName" size="20" maxlength="15" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.email"/></td>
	        <td></td>
	        <td><html:text property="email" size="50" maxlength="50" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.home_phone"/></td>
	        <td></td>
	        <td><html:text property="homePhone" size="20" maxlength="25" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="account_settings.label.cell_phone"/></td>
	        <td></td>
	        <td><html:text property="cellPhone" size="20" maxlength="25" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	        <td class="label"><bean:message key="master_locations.label.location"/></td>
	        <td></td>
	        <td><html:text property="location" size="20" maxlength="25" name="userForm"></html:text></td>
	    </tr>
	    <tr>
	    	<td class="label"><bean:message key="resume_summary.label.education"/></td>
	    </tr>
	    </table>
    	<table id="eduTable" style="width: 100px;">
				<tbody>
					<tr>
						<td class="eduHeader"></td>
						<td class="eduHeader"><bean:message key="common.year_of_passing" /></td>	
						<td class="eduHeader"><bean:message key="common.degree" /></td>
						<td class="eduHeader"><bean:message key="common.branch" /></td>
						<td class="eduHeader"><bean:message key="common.institute" /></td>						
					</tr>
					<tr>
						<td colspan="5" class="headerBottom"></td>
					</tr>
					<tr id="rowId0">
						<td><input type="radio" class="radio" name="selectedEdu" id="selectedEdu0" checked/></td>
						<td><input type="text" name="educationYearOfPassing" id="educationYearOfPassing0" size="4" onblur="getFormattedYear(this);" class="Grey" 
							value = "<%=(userForm.getEducationYearOfPassing()== null)?"":(userForm.getEducationYearOfPassing().length>0)?userForm.getEducationYearOfPassing()[0]:"" %>"/>
						</td>
						<td><input type="text" name="educationDegreeId" id="educationDegreeId0" size="25" maxlength="150" class="Grey" 
							value = "<%=(userForm.getEducationDegreeId()== null)?"":(userForm.getEducationDegreeId().length>0)?userForm.getEducationDegreeId()[0]:"" %>"/>
						</td>
						<td><input type="text" name="educationMajorId" id="educationMajorId0" size="25" maxlength="150" class="Grey" 
							value = "<%=(userForm.getEducationMajorId()== null)?"":(userForm.getEducationMajorId().length>0)?userForm.getEducationMajorId()[0]:"" %>"/>
						</td>		
						<td><input type="text" name="educationInstitute" id="educationInstitute0" size="25" maxlength="150" class="Grey" 
							value = "<%=(userForm.getEducationInstitute()== null)?"":(userForm.getEducationInstitute().length>0)?userForm.getEducationInstitute()[0]:"" %>"/>
						</td>
						</tr>
					</tbody>
				</table>
					
				<table>
					<tr>
						<td style="text-align: right;">
							<a href="#"	onclick="deleteEducation();"><bean:message key="common.delete" /> <bean:message key="common.selected" /></a>&nbsp;|&nbsp;
							<a href="#" onclick="addEducation();"><b><bean:message key="common.more" /></b></a>
						</td>
					</tr>
				    <tr>
	    				<td class="label"><bean:message key="common.employment_history"/></td>
	    			</tr>
	   		 	</table>
	    		<table id="empHistTable" style="width: 100px;">
					<tbody>
						<tr>
							<td class="eduHeader"></td>
							<td class="eduHeader"><bean:message key="common.employment_from_date" /></td>							
							<td class="eduHeader"><bean:message key="common.employment_to_date" /></td>
							<td class="eduHeader"><bean:message key="common.employer" /></td>
							<td class="eduHeader"><bean:message key="common.designation" /></td>
						</tr>						
						<tr>
							<td colspan="5" class="headerBottom"></td>
						</tr>
						<tr id="empHistRowId0">
							<td><input type="radio" class="radio" name="selectedEmpHist" id="selectedEmpHist0" checked/>
							</td>
							<td><input type="text" name="employmentFromDate" id="employmentFromDate0" size="12" maxlength="25" onblur="getFormattedDate(this);" class="Grey" 
							value = "<%=(userForm.getEmploymentFromDate()== null)?"":(userForm.getEmploymentFromDate().length>0)?userForm.getEmploymentFromDate()[0]:"" %>"
							/></td>							
							<td><input type="text" name="employmentToDate" id="employmentToDate0" size="12" maxlength="25" onblur="getFormattedDate(this);" class="Grey" 
							value = "<%=(userForm.getEmploymentToDate()== null)?"":(userForm.getEmploymentToDate().length>0)?userForm.getEmploymentToDate()[0]:"" %>"
							/></td>
							<td><input type="text" name="employmentEmployerId" id="employmentEmployerId0" size="25" maxlength="100" class="Grey" 
							value = "<%=(userForm.getEmploymentEmployerId()== null)?"":(userForm.getEmploymentEmployerId().length>0)?userForm.getEmploymentEmployerId()[0]:"" %>"
							/></td>
							<td><input type="text" name="employmentDesignationId" id="employmentDesignationId0" size="25" maxlength="100" class="Grey"
							value = "<%=(userForm.getEmploymentDesignationId()== null)?"":(userForm.getEmploymentDesignationId().length>0)?userForm.getEmploymentDesignationId()[0]:"" %>"
							/></td>
						</tr>	
					</tbody>
				</table>
		    	<table>
		    		<tr>
						<td style="text-align: right;">
							<a href="#"	onclick="deleteEmpHistory();"><bean:message key="common.delete" /> <bean:message key="common.selected" /></a>&nbsp;|&nbsp;
							<a href="#" onclick="addEmpHistory();"><b><bean:message key="common.more" /></b></a>
						</td>
					</tr>
				</table>		
	</div>
	<table style="width: 100%; border: 0; border-spacing: 0; padding: 0;">
		<tr>
			<td>
				<div class="navBtn" style="margin-top:5px;"><a href="#" style="width:50px;" class="active" onclick="javascript:submitForm()"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
				</div>
			</td>
		</tr>
	</table>
	<br/><br/>
	<table>
	
		<logic:iterate name="sources" id="source">
			<tr>
				<td>
					<div class="navBtn" style="margin-top:5px;">
					<a href="#" style="width:150px;" class="active" onclick="applyWithSocialMedia(<bean:write name="source" property="sourceId"/>)">
						<span class="rightC"></span><span class="leftC"></span>
						Share <bean:write name="source" property="sourceTitle"/> Info
					</a>
					</div>
				</td>
			</tr>
		</logic:iterate>	
		
	</table>
</div>										
</html:form>

<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<div id="divCalender" class="myCalender"></div>

<script> 
var formChangedFlag;
var empHistCount = 0;
var empHistRowIndex = 0; 
var empHistTable = $('empHistTable');


var eduCount = 0;
var rowIndex = 0; 
var eduTable = $('eduTable');

//var returnLocation = window.location.href + "&update=1";
var returnLocation = window.location.href;
if (returnLocation.indexOf("?") == -1){
	returnLocation = returnLocation + "?mode=socialProfile";
}
window.onload=doOnLoad;
   

function submitForm(){
	var firstName = document.userForm.firstName.value;
	firstName = firstName.trim();
	var lastName = document.userForm.lastName.value;
	lastName = lastName.trim();
	var orows = $('eduTable').rows;
	var rowFlag = false;
	var rowFlag1 = false;
	var rowFlag2 = false;
	var rowFlag3 = false;
	for(var i=0;i<orows.length;i++){
		var cells = orows[i].cells;
		if(cells.length==5){
			var cell4 = cells[4].getElementsByTagName("input");
			if(cell4.length == 1){
				var instituteName = cell4[0].value;
				if(instituteName != ''){
					var cell1 = cells[1].getElementsByTagName("input");
					var yearOfPassing = cell1[0].value;
					if(yearOfPassing == ''){
						alert("Please enter year of Passing");
						cell1[0].focus();
						return false;
					}
					rowFlag = true;
				}else{
					rowFlag1 = true;
					var cell2 = cells[2].getElementsByTagName("input");
					var cell3 = cells[3].getElementsByTagName("input");
					var cell1 = cells[1].getElementsByTagName("input");
					var degree = cell2[0].value;
					var branch = cell3[0].value;
					var yearOfPassing = cell1[0].value;
					if(branch != '' || degree != '' || yearOfPassing != ''){
						alert("Institute field cant be left blank");
						cell4[0].focus();
						return false;
					}
				}
			}
				
		}
	}
	orows = $('empHistTable').rows;
	for(var i=0;i<orows.length;i++){
		var cells = orows[i].cells;
		if(cells.length==5){
			var cell3 = cells[3].getElementsByTagName("input");
			if(cell3.length == 1){
				var employerName = cell3[0].value;
				if(employerName != ''){
					var cell1 = cells[1].getElementsByTagName("input");
					var fromDate = cell1[0].value;
					if(fromDate == ''){
						alert("Please enter at least from date for the employment history record");
						cell1[0].focus();
						return false;
					}
					rowFlag2 = true;
				}else{
					rowFlag3 = true;
					var cell4 = cells[4].getElementsByTagName("input");
					var cell2 = cells[2].getElementsByTagName("input");
					var cell1 = cells[1].getElementsByTagName("input");
					var fromDate = cell1[0].value;
					var designation = cell4[0].value;
					var toDate = cell2[0].value;
					if(designation != '' || toDate != '' || fromDate != ''){
						alert("Employer field cant be left blank");
						cell3[0].focus();
						return false;
					}
				}
			}
				
		}
	}
	
		
	if(firstName.length == 0 || lastName.length == 0){
		if(firstName.length == 0){
			alert("First Name field cant be left blank");
			document.userForm.firstName.focus();
			return false;
		}
		if(lastName.length == 0){
			alert("Last Name field cant be left blank");
			document.userForm.lastName.focus();
			return false;
		}
	}
	
	if(rowFlag){
		if(rowFlag1){
			alert("Education History row cant be left blank");
			return false;
		}	
	}
	if(rowFlag2){
		if(rowFlag3){
			alert("Work History row cant be left blank");
			return false;
		}	
	}
	
	if(!rowFlag && !rowFlag2){
		alert("Atleast one record of education or work history is required");
		return false;
	}
	document.getElementById("formChangedFlag").value = formChangedFlag;
	document.userForm.submit();
}

function applyWithSocialMedia(socialMediaId){
	window.location = "./oauthredirect.action?socialMediaId="+socialMediaId+"&returnPath="+returnLocation.toString().replace("&","|");
}

function onApplyWithFacebook(){
	window.location = "./oauthredirect.action?socialMediaId=209&returnPath="+returnLocation.toString().replace("&","|");
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
		var sub_row_id = "subRowId" + edu_id.split('selectedEdu')[1];
		var row = document.getElementById(row_id);
		var subRow = document.getElementById(sub_row_id);
		row.parentNode.removeChild(row);
		//subRow.parentNode.removeChild(subRow);
		eduCount--;
		formChangedFlag="1";
	}else{
		alert("Please select the row to delete.");
	}
}
	
	function addEducation() {
		var tBody = eduTable.getElementsByTagName('tbody')[0];
		var myRow = document.createElement("tr");
		var myRow0 = document.createElement("tr");
		rowIndex++;
		eduCount++;
		lastElm = rowIndex;
		myRow.id="rowId"+lastElm;
		myRow0.id="subRowId"+lastElm;
		addListener(myRow);
		addListener(myRow0);
		
		var myCell = document.createElement("td");
		myCell.innerHTML = "<input type=radio class=radio name=selectedEdu id=selectedEdu"+lastElm+">"
		myRow.appendChild(myCell);	
		
		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationYearOfPassing","","","educationYearOfPassing"+lastElm);	
		el.size="4";
		Event.observe(el, "blur", onBlurEduYearOfPassing.bindAsEventListener(this));
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	

		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationDegreeId","","","educationDegreeId"+lastElm);	
		el.size="25";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	

		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationMajorId","","","educationMajorId"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	
		
		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationInstitute","","","educationInstitute"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	
		
		tBody.appendChild(myRow);
		tBody.appendChild(myRow0);
	}

	function addEducationFromSocialMedia(yop, institute, degreeId, branchId) {
		var tBody = eduTable.getElementsByTagName('tbody')[0];
		var myRow = document.createElement("tr");
		var myRow0 = document.createElement("tr");
		rowIndex++;
		eduCount++;
		lastElm = rowIndex;
		myRow.id="rowId"+lastElm;
		myRow0.id="subRowId"+lastElm;
		addListener(myRow);
		addListener(myRow0);
		
		var myCell = document.createElement("td");
		myCell.innerHTML = "<input type=radio class=radio name=selectedEdu id=selectedEdu"+lastElm+">"
		myRow.appendChild(myCell);	
		
		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationYearOfPassing",yop,"","educationYearOfPassing"+lastElm);	
		el.size="4";
		Event.observe(el, "blur", onBlurEduYearOfPassing.bindAsEventListener(this));
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	

		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationDegreeId",degreeId,"","educationDegreeId"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	
		myCell = document.createElement("td");
			
		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationMajorId",branchId,"","educationMajorId"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	

		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","educationInstitute",institute,"","educationInstitute"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	

		tBody.appendChild(myRow);
		tBody.appendChild(myRow0);
	}
	
	function addEmpHistory() {
		var tBody = empHistTable.getElementsByTagName('tbody')[0];
		var myRow = document.createElement("tr");
		var myRow0 = document.createElement("tr");
		empHistRowIndex++;
		empHistCount++;
		lastElm = empHistRowIndex;
		myRow.id="empHistRowId"+lastElm;	
		addListener(myRow);	
		
		var myCell = document.createElement("td");
		myCell.innerHTML = "<input type=radio class=radio name=selectedEmpHist id=selectedEmpHist"+lastElm+">"
		myRow.appendChild(myCell);	
		
		myCell = document.createElement("td");
		var elFrom;
		elFrom = createFormElement("input","text","employmentFromDate","","","employmentFromDate"+lastElm);	
		elFrom.size="12";
		elFrom.maxlength="25";
		Event.observe(elFrom, "blur", onBlurEmpHistoryFromDate.bindAsEventListener(this));
		myCell.appendChild(elFrom);	
		myRow.appendChild(myCell);

		myCell = document.createElement("td");
		var elTo;
		elTo = createFormElement("input","text","employmentToDate","","","employmentToDate"+lastElm);	
		elTo.size="12";
		elTo.maxlength="25";
		Event.observe(elTo, "blur", onBlurEmpHistoryToDate.bindAsEventListener(this));
		myCell.appendChild(elTo);	
		myRow.appendChild(myCell);	


		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","employmentEmployerId","","","employmentEmployerId"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	


		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","employmentDesignationId","","","employmentDesignationId"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	
		
		tBody.appendChild(myRow);	
	}

	function addEmpHistoryFromSocialMedia(empId, desId, fromDate, toDate) {
		var tBody = empHistTable.getElementsByTagName('tbody')[0];
		var myRow = document.createElement("tr");
		var myRow0 = document.createElement("tr");
		empHistRowIndex++;
		empHistCount++;
		lastElm = empHistRowIndex;
		myRow.id="empHistRowId"+lastElm;	
		addListener(myRow);	
		
		var myCell = document.createElement("td");
		myCell.innerHTML = "<input type=radio class=radio name=selectedEmpHist id=selectedEmpHist"+lastElm+">"
		myRow.appendChild(myCell);	
		
		myCell = document.createElement("td");
		var elFrom;
		elFrom = createFormElement("input","text","employmentFromDate",fromDate,"","employmentFromDate"+lastElm);	
		elFrom.size="12";
		elFrom.maxlength="25";
		Event.observe(elFrom, "blur", onBlurEmpHistoryFromDate.bindAsEventListener(this));
		myCell.appendChild(elFrom);	
		myRow.appendChild(myCell);

		myCell = document.createElement("td");
		var elTo;
		elTo = createFormElement("input","text","employmentToDate",toDate,"","employmentToDate"+lastElm);	
		elTo.size="12";
		elTo.maxlength="25";
		Event.observe(elTo, "blur", onBlurEmpHistoryToDate.bindAsEventListener(this));
		myCell.appendChild(elTo);	
		myRow.appendChild(myCell);	


		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","employmentEmployerId",empId,"","employmentEmployerId"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	


		myCell = document.createElement("td");
		var el;
		el = createFormElement("input","text","employmentDesignationId",desId,"","employmentDesignationId"+lastElm);	
		el.size="25";
		el.maxlength="100";
		myCell.appendChild(el);	
		myRow.appendChild(myCell);	
		
		tBody.appendChild(myRow);	
	}

	function onBlurEmpHistoryToDate(event){
		var el=Event.element(event);
		getFormattedDate(el);
	}
	function onBlurEmpHistoryFromDate(event){
		var el=Event.element(event);
		getFormattedDate(el);
	}
	
	function onBlurEduYearOfPassing(event){
		var e1 = Event.element(event);
		getFormattedYear(e1);
	}

	function getSelectedEmpHistory() {
		var emp_hist_id = '';
		var radioBtn = document.getElementsByName("selectedEmpHist");
		for(var i = 0; i < radioBtn.length; i++) {
			if(radioBtn[i].checked){
				emp_hist_id=radioBtn[i].id;
			}
		}
		return emp_hist_id;
	}

	function deleteEmpHistory() {
		var edu_id = getSelectedEmpHistory();
		if(edu_id!=''){
			var row_id = "empHistRowId" + edu_id.split('selectedEmpHist')[1];		
			var row = document.getElementById(row_id);		
			row.parentNode.removeChild(row);		
			empHistCount--;
			formChangedFlag="1";
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
			alert("Please enter date in format MMM-yyyy or MM-yyyy");
			obj.clear();
			setTimeout(function(){obj.focus()}, 0);
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
				alert("Please enter year in format yyyy or yy");
				obj.clear();
				setTimeout(function(){obj.focus()}, 0);
				return false;
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
	
	function addListener(row){
		Event.observe(row, "click", onRowClick.bindAsEventListener(this));
		Event.observe(row, "keydown", onRowKeyDown.bindAsEventListener(this));
		Event.observe(row, "keyup", onRowKeyUp.bindAsEventListener(this));
	}
	
	function onRowClick(event){
		var row = Event.findElement(event, 'tr');
		if(row.id.indexOf('rowId')!=-1){
			var radioId = "selectedEdu" + row.id.split("rowId")[1];
		}else if(row.id.indexOf('subRowId')!=-1){
			var radioId = "selectedEdu" + row.id.split("subRowId")[1];
		}else if(row.id.indexOf('empHistRowId')!=-1){
			var radioId = "selectedEmpHist" + row.id.split("empHistRowId")[1];
		}
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
	function doOnLoad() {
		//initPopUp();
		var degreeId = "";
		var branchId = "";
		var yop = "";
		var institute = "";
		formChangedFlag = '0';
		<% for(int i=1; userForm.getEducationYearOfPassing()!=null && i<userForm.getEducationYearOfPassing().length; i++){ %>
			yop = '<%=(userForm.getEducationYearOfPassing()== null)?"":(userForm.getEducationYearOfPassing().length>i)?userForm.getEducationYearOfPassing()[i]:"" %>';
			institute = '<%=(userForm.getEducationInstitute()== null)?"":(userForm.getEducationInstitute().length>i)?userForm.getEducationInstitute()[i]:"" %>';
			degreeId = '<%=(userForm.getEducationDegreeId()== null)?"":(userForm.getEducationDegreeId().length>i)?userForm.getEducationDegreeId()[i]:"" %>';
			branchId = '<%=(userForm.getEducationMajorId()== null)?"":(userForm.getEducationMajorId().length>i)?userForm.getEducationMajorId()[i]:"" %>';
		addEducationFromSocialMedia(yop, institute, degreeId, branchId);
		<% } %>
		
		var empId = "";
		var desId = "";
		var fromDate = "";
		var toDate = "";
		
		<% for(int i=1; userForm.getEmploymentEmployerId() != null && i<userForm.getEmploymentEmployerId().length; i++){ %>
		empId = '<%=(userForm.getEmploymentEmployerId()== null)?"":(userForm.getEmploymentEmployerId().length>i)?userForm.getEmploymentEmployerId()[i]:"" %>';
		desId = '<%=(userForm.getEmploymentDesignationId()== null)?"":(userForm.getEmploymentDesignationId().length>i)?userForm.getEmploymentDesignationId()[i]:"" %>';
		fromDate = '<%=(userForm.getEmploymentFromDate()== null)?"":(userForm.getEmploymentFromDate().length>i)?userForm.getEmploymentFromDate()[i]:"" %>';
		toDate = '<%=(userForm.getEmploymentToDate()== null)?"":(userForm.getEmploymentToDate().length>i)?userForm.getEmploymentToDate()[i]:"" %>';	
			
		addEmpHistoryFromSocialMedia(empId, desId, fromDate, toDate);
		<% } %>
		var allInputObjs = document.getElementsByTagName("input");
		for(var i=0;i<allInputObjs.length;i++){
			allInputObjs[i].onchange=function(){
				formChangedFlag = '1';
			}			
		}
	}

</script>													