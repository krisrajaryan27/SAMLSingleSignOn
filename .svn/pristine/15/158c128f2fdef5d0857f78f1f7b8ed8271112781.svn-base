<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.talentPool.custom.dataobject.CustomFieldData" %>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%>
<%@page import="com.talentPool.custom.utils.CustomFieldUtils"%>
<%@page import="com.talentPool.custom.utils.CustomFieldDataProcessor"%>
<%@ page import="com.talentPool.custom.manager.CustomFieldManager" %>
<%@page import="com.talentPool.custom.dataobject.CustomFieldCell"%>
<%@page import="com.talentPool.custom.dataobject.CustomFieldRow"%>
<%@page import="com.talentPool.custom.dataobject.CustomFieldTable"%>
<%@ page import="com.talentPool.common.utils.CommonUtils" %>
<%@ page import="com.talentPool.common.db.SimpleDataObject" %>
<%@ page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.dataobject.ImportFieldData"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.masters.utils.LocationUtils"%>
<link rel="stylesheet" type="text/css" href="themes/default/calender.css">
<script language="JavaScript" src="js/calender/CalendarPopup.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/controls.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/monthYearCalender.js" type="text/javascript"></script>
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script language="JavaScript" src="js/customfields/customfieldvalidator.js"></script>
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>


<style>
.educationT {border:1px solid #99CC33;border-left:0px; border-collapse: collapse;}
.educationT TD{padding-left:3px; padding-right:4px;background:#FFFFFF; padding-top: 3px; padding-bottom: 2px;}
.educationT TD.header{border-bottom:1px solid #F9FCF3;color: #666666;background:#D0E4A3;}
.educationT TD.headerBottom{background:#99CC33;height:2px;}
.educationT td {
	position:relative;
	}
.tabinput td {
	position:relative;
	}

.tabinput td .calDiv {
	top: 20px !important;
}
</style>
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
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>
<script language="JavaScript" src="js/customfields/customfield.js"></script>
<script language="JavaScript" src="js/customfields/customfieldvalidator.js"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxandradiogroup/checkboxradiogroup.js" type="text/javascript"></script>
<% ArrayList customFields = (ArrayList)request.getAttribute("customFields"); %>
<% ArrayList customTables = (ArrayList)request.getAttribute("customTables"); %>
<script>
<%=CustomFieldUtils.getArrayForCustomFields(customFields)%>
</script>
<%
ArrayList skills = CommonUtils.getSkills(); 

%>

<table cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td>
<html:form action="/applicantRegistration" enctype="multipart/form-data">
<input type="hidden" name="mode" value="defaultStep2" />
<input type="hidden" name="eduRowIds" value='<%=request.getParameter("eduRowIds")%>'/>
<input type="hidden" name="empRowIds" value='<%=request.getParameter("empRowIds")%>'/>
<input type="hidden" name="positionId" value='<%=request.getParameter("positionId")%>'/>

<script language="JavaScript">
var sourceTypeFilters = null;
var sources = null;
</script>

<div style="margin-left:200px; text-align:left;">
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
	<div id="divCalender" class="myCalender"></div>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<div style="width:1000px;" class="boxTab"><span class="rightC"></span><span class="leftC"></span>&nbsp;<strong><bean:message key="applicant_registration.label.registration_form" /></strong></div>
			</td>			
		</tr>
	</table>

				<div class="bottomDiv">
				<div class="outerDiv" style="width:1000px;height:1000px; overflow: auto; ">
				<br />
				<table class="tabinput" cellspacing="0" cellpadding="5" border="0" style="width:562px">
					<tr>
						<td class="label"><bean:message key="add_applicant.label.name" /><span class="star">*</span>:</td>						
						<td><input type="text" name="name" maxlength="100" size="25" class="Grey" value='<%=request.getParameter("name")%>' /></td>
					</tr>
					<br/>
					
						<tr>
						<td class="label"><bean:message key="common.date_of_birth" /><span class="star">*</span>:</td>						
						<td><input type="text" id="dateOfBirth" name="dateOfBirth" maxlength="100" size="25" class="Grey" value='<%=request.getParameter("dateOfBirth")%>' onblur="getFDate(this,'dd/MM/yyyy');" class="Grey" /></td>
					</tr>
					<br/>
					
					<%-- <tr>
						<td class="label"><bean:message key="add_applicant.label.source_type" />:</td>
						<td><input type="hidden" name="sourceType" />
							<script language="JavaScript">
								var opts = <%=(String)request.getAttribute("jsArraySourceTypes")%>;
						        var m = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							    opts = m.concat(opts);
								sourceTypeFilters = new SelectBox(opts,'<%=request.getParameter("sourceType")%>','images/btn_dropdown.gif',{namesonly:false, width:'167px', size:20, textboxclass:'Grey'});
								sourceTypeFilters.setOnChangeHandler('onChangeSourceType');
								document.write(sourceTypeFilters.getHtml());
								sourceTypeFilters.init();
								
							</script>
						</td>
						<td class="vGap"></td>
					</tr>
					<tr>
						<td class="label"><bean:message key="add_applicant.label.source" /><span class="star">*</span>:</td>						
						<td>
							<input type="hidden" name="source" />
							<script language="JavaScript">								
						        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
								sources = new SelectBox(n,'-1','images/btn_dropdown.gif',{namesonly:false, width:'167px', size:20, textboxclass:'Grey'});
								sources.setOnChangeHandler('onChangeSource');
								document.write(sources.getHtml());
								sources.init();
							</script>
						</td>
					</tr> --%>
					<tr>
						<td class="label"><bean:message key="add_applicant.label.current_location" />:</td>
						<td class="label"><input type="text" name="currentLocation" maxlength="50" size="25" class="Grey" value='<%=request.getParameter("currentLocation")%>' /></td>
					</tr>
					<tr>
						 <td class="label"><bean:message key="add_applicant.label.phone1" /><span class="star">*</span>:</td>
						 <td class="label"><input type="text" name="phone1" size="25" maxlength="25" class="Grey" value='<%=request.getParameter("phone1")%>' /></td>
					</tr>
					<tr>
						 <td class="label"><bean:message key="add_applicant.label.phone2" />:</td>
					     <td class="label"><input type="text" name="phone2" size="25" maxlength="25" class="Grey" value='<%=request.getParameter("phone2")%>' /></td>
					</tr>
					<tr>
						 <td class="label"><bean:message key="add_applicant.label.mobile" />:</td>
						 <td><input type="text" name="mobile" size="25" maxlength="25" class="Grey" value='<%=request.getParameter("mobile")%>' /></td>
					</tr>
					<tr>
						<td class="label"><bean:message key="add_applicant.label.email1" /><span class="star">*</span>:</td>
						<td class="label"><input type="text" name="email1" size="35" maxlength="50" class="Grey" value='<%=request.getParameter("email1")%>' /></td>
					</tr>
					<tr>
						<td class="label"><bean:message key="add_applicant.label.email2" />:</td>
						<td class="label"><input type="text" name="email2" size="35" maxlength="50" class="Grey" value='<%=request.getParameter("email2")%>' /></td>
					</tr>					
				</table>

				<br />
			<%-- 	<span class="heading"><bean:message key="add_applicant.label.skills" /></span>
				<table class="outerDiv">
					<tr class="boxTab">
						<input type="hidden" name="skillIds" value='<%=request.getParameter("skillIds")%>' />
						<td class="label" style="width:200px;">
							<select name="skills" size="5" style="width:200px;border:none;">
								<% for(int i = 0; i < skills.size(); i++) { %>
									<% SimpleDataObject skill = (SimpleDataObject) skills.get(i); %>
									<option value='<%=skill.getString("skillId")%>'><%=skill.getString("skillName")%></option>
								<% } %>
							</select>
						</td>
						<td class="label" style="width:35px;">																												
							<div class="navBtn" style="margin:2px;">
								<a href="#" style="width:30px;" class="active" onclick="javascript: add(document.forms[0].skills, document.forms[0].selectedSkills);return false;" title="Add" ><span class="rightC"></span><span class="leftC"></span><img src="images/ico_rightarrow.gif"  border="0" /></a> 
								<a href="#" style="width:30px;margin-top:3px;" class="active" onclick="javascript: remove(document.forms[0].skills, document.forms[0].selectedSkills);return false;" title="Remove" ><span class="rightC"></span><span class="leftC"></span><img src="images/ico_leftarrow.gif" border="0" /></a> 
							</div>									
						</td>
						<td class="label" style="width:200px;">
							<select name="selectedSkills" size="5" style="width:200px;border:none;">
								
							</select>
						</td>
					</tr>
				</table>
				<br /> --%>
				
		
				<span class="heading"><bean:message key="add_applicant.label.education" /><span class="star">*</span> </span>
				<table class="educationT" id="eduTable" width="1000">
					<tbody>
						<tr>
							<td class="header"></td>
							<td class="header"><bean:message key="add_applicant.label.degree" /></td>
							<td class="header"><bean:message key="add_applicant.label.specialization" /></td>
							<td class="header"><bean:message key="add_applicant.label.start_date" /></td>
							<td class="header"><bean:message key="add_applicant.label.end_date" /></td>
							<td class="header"><bean:message key="add_applicant.label.institute" /></td>
							<td class="header"><bean:message key="add_applicant.label.university" /></td>
							<td class="header"><bean:message key="add_applicant.label.type_of_program" /></td>
							<td class="header"><bean:message key="add_applicant.label.marks" /></td>
						</tr>
						<tr>
							<td colspan="9" class="headerBottom"></td>
						</tr>
						<tr id="0">
							<td class="label"><img src="images/checkedradiobutton.gif" id="radio0" onclick="javascript: selectEdu(this);"/></td>
							<%-- <td class="label"><input type="text" name="yearOfPassing0" value='<%=request.getParameter("yearOfPassing0")%>' size="4" onblur="getFormattedYear(this);" class="Grey" /></td>
							 --%>
							 
							 	<td class="label">
								<input type="hidden" name="degree0" id="degree0" value='<%=request.getParameter("degree0")%>'/>
								<script language="JavaScript">						
									var degreeControls=new Array();
							        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							        var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties(CommonUtils.getDegrees(), "degreeId", "title")%>;
							        var degreeOptions = n.concat(opts);
									degrees = new SelectBox(degreeOptions,'<%=CommonUtils.getDegreeIdByName("10th")%>','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
									document.write(degrees.getHtml());
									degrees.init();
									degreeControls[degreeControls.length]=degrees;
								</script>
							</td>
								<td class="label">
								<input type="hidden" name="branch0" id="branch0" value='<%=request.getParameter("branch0")%>'/>
								<script language="JavaScript">		
									var branchControls=new Array();						
							        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							        var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties(CommonUtils.getBranches(), "branchId", "branchName")%>;
							        var branchOptions = n.concat(opts);
									branches = new SelectBox(branchOptions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
									document.write(branches.getHtml());
									branches.init();
									branchControls[branchControls.length]=branches;
								</script>
							</td>
							<td class="label"><input type="text" id="startDate0" name="startDate0" value='<%=request.getParameter("startDate0")%>' size="10" onblur="getFormattedDate(this);" class="Grey" />
							</td>
							<td class="label"><input type="text" id="endDate0" name="endDate0" value='<%=request.getParameter("endDate0")%>' size="10" onblur="getFormattedDate(this);" class="Grey" />
							</td>
							
							<td class="label"><input type="text" id="institute0" name="institute0" value='<%=request.getParameter("institute0")%>' size="27" maxlength="150" class="Grey" /></td>
						
							<td class="label"><input type="text" id="university0" name="university0" value='<%=request.getParameter("university0")%>' size="17" maxlength="150" class="Grey" /></td>
						
							
							
								<td class="label">
								<input type="hidden" name="typeOfProgram0" id="typeOfProgram0" value='<%=request.getParameter("typeOfProgram0")%>'/>
								<script language="JavaScript">						
									var typeOfProgramControls=new Array();
							        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							        var opts = <%=CommonUtils.getJsArrayTypeOfProgram()%>;
							        var typeOfProgramOptions = n.concat(opts);
							        typeOfPrograms = new SelectBox(typeOfProgramOptions,'<%=request.getParameter("typeOfProgram0")%>','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
									document.write(typeOfPrograms.getHtml());
									typeOfPrograms.init();
									typeOfProgramControls[typeOfProgramControls.length]=typeOfPrograms;
								</script>
							</td>
							
							<td class="label"><input type="text" id="grade0" name="grade0" value='<%=request.getParameter("grade0")%>' size="6" maxlength="100" class="Grey" /></td>
						</tr>
						
					</tbody>
				</table>
				
				<table class="tabinput" width="480">
					<tr>
						<td style="text-align: right;" class="Grey"><a href="#"
							onclick="deleteEducation();" class="green">
							<bean:message key="applicant_registration.label.delete_selected" />						
							</a>&nbsp;|&nbsp;<a
							href="#" onclick="addEducation();" class="green"><b>
							<bean:message key="applicant_registration.label.more" />
							</b></a>
						</td>
					</tr>
				</table>
				
				
				<br />
				<span class="heading"><bean:message key="add_applicant.label.work_exp" /></span>
				<table class="tabinput">
					<tr>
						<td class="label"><bean:message key="add_applicant.label.working_since"/><span class="star">*</span>:&nbsp;<bean:message key="add_applicant.content.working_since" /></td>
						<td class="hGap"></td>
						<td class="label">
						<bean:message key="applicant_registration.label.current_employer" />:
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" id="workingSince" name="workingSince" value='<%=request.getParameter("workingSince")%>' size="10" maxlength="25" onblur="getFormattedDate(this);" class="Grey" />
							<img src="images/ico_cal.gif" onclick="cal.showCalender('workingSince','workingSince');" class="CalImg" />
							&nbsp;&nbsp;&nbsp;
							<img src="images/checkboxunchecked.gif" id="cboFresher" onclick="toggleFresher()" style="margin-bottom: -2px;" />
							&nbsp;Fresher<input type="hidden" name="fresher" value="0"/>
						</td>
						<td class="hGap"></td>
						<td><input type="text" name="currentEmp" value='<%=request.getParameter("currentEmp")%>' maxlength="250" size="40" class="Grey" /></td>
					</tr>
				</table>
				<br/>
				
				
				<span class="heading">Employment History <span class="star">*</span></span>
				<table class="educationT"" id="empTable" width="1000">
					<tbody>
						<tr>
							<td class="header"></td>
							
							<td class="header"><bean:message key="add_applicant.label.start_date" /></td>
							<td class="header"><bean:message key="add_applicant.label.end_date" /></td>
							<td class="header">Company</td>
							<td class="header">Title/Designation</td>
							<td class="header">Employment Type</td>
							<td class="header">Location</td>
							<td class="header">Country</td>
							<td class="header">Reason For Leaving</td>
							<td class="header">Last Drawn Ctc</td>
						</tr>
						<tr>
							<td colspan="9" class="headerBottom"></td>
						</tr>
						<tr id="0">
							<td class="label"><img src="images/checkedradiobutton.gif" id="radio0" onclick="javascript: selectEmp(this);"/></td>
							
							<td class="label"><input type="text" id="empStartDate0" name="empStartDate0" value='<%=request.getParameter("empStartDate0")%>' size="10" onblur="getFormattedDate(this);" class="Grey" />
							</td>
							<td class="label"><input type="text" id="empEndDate0" name="empEndDate0" value='<%=request.getParameter("empEndDate0")%>' size="10" onblur="getFormattedDate(this);" class="Grey" />
							</td>
							
							<td class="label">
								<input type="hidden" name="employer0" id="employer0" value='<%=request.getParameter("employer0")%>'/>
								<script language="JavaScript">						
									var employerControls=new Array();
							        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							        var opts = <%=CommonUtils.getJsArrayEmployers()%>;
							        var employerOptions = n.concat(opts);
									employers = new SelectBox(employerOptions,'<%=request.getParameter("employer0")%>','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
									document.write(employers.getHtml());
									employers.init();
									employerControls[employerControls.length]=employers;
								</script>
							</td>
							<td class="label">
								<input type="text" name="designation0" id="designation0" value='<%=request.getParameter("designation0")%>'  size="10" maxlength="100" class="Grey"  />
							</td>
							
								<td class="label">
								<input type="hidden" name="empType0" id="empType0" value='<%=request.getParameter("empType0")%>'/>
								<script language="JavaScript">						
									var empTypeControls=new Array();
							        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							        var opts = <%=CommonUtils.getJsArrayEmploymentType()%>;
							        var empTypeOptions = n.concat(opts);
							        empTypes = new SelectBox(empTypeOptions,'<%=request.getParameter("empType0")%>','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
									document.write(empTypes.getHtml());
									empTypes.init();
									empTypeControls[empTypeControls.length]=empTypes;
								</script>
							</td>
							
							<%-- 		<td class="label">
								<input type="hidden" name="location0" id="location0" value='<%=request.getParameter("location0")%>'/>
								<script language="JavaScript">		
									var locationControls=new Array();						
							        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							        var opts = <%=LocationUtils.getJSArrayLocationsAP()%>;
							        var locationOptions = n.concat(opts);
									locations = new SelectBox(locationOptions,'<%=request.getParameter("location0")%>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
									document.write(locations.getHtml());
									locations.init();
									locationControls[locationControls.length]=locations;
								</script>
							</td> --%>
							
							<td class="label">
								<input type="text" name="location0" id="location0" value='<%=request.getParameter("location0")%>'  size="10" maxlength="100" class="Grey"  />
							</td>
									<td class="label">
								<input type="hidden" name="country0" id="country0" value='<%=request.getParameter("country0")%>'/>
								<script language="JavaScript">		
									var countryControls=new Array();						
							        var n = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
							        var opts = <%=CommonUtils.getJsArrayCountries()%>;
							        var countryOptions = n.concat(opts);
									countrys = new SelectBox(countryOptions,'<%=request.getParameter("country0")%>','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
									document.write(countrys.getHtml());
									countrys.init();
									countryControls[countryControls.length]=countrys;
								</script>
							</td>
							<td class="label"><input type="text" name="reasonForLeaving0" value='<%=request.getParameter("reasonForLeaving0")%>' size="10" maxlength="100" class="Grey" /></td>
							<td class="label"><input type="text" name="lastCtc0" value='<%=request.getParameter("lastCtc0")%>' size="10" maxlength="100" class="Grey" /></td>
						</tr>
					</tbody>
				</table>
				
				<table class="tabinput" width="480">
					<tr>
						<td style="text-align: right;" class="Grey"><a href="#"
							onclick="deleteEmployment();" class="green">
							<bean:message key="applicant_registration.label.delete_selected" />						
							</a>&nbsp;|&nbsp;<a
							href="#" onclick="addEmployment();" class="green"><b>
							<bean:message key="applicant_registration.label.more" />
							</b></a>
						</td>
					</tr>
				</table>
				
				
				<br />
				
				<table class="tabinput" cellspacing="0" cellpadding="5" border="0">
					<tr>
						<td class="label"><bean:message key="add_applicant.label.current_ctc" /></td>
						<td><input type="text" name="currentCTC" value='<%=request.getParameter("currentCTC")%>' maxlength="10" size="25" class="Grey" /></td>
					</tr>
					<tr>
						<td class="label"><bean:message key="add_applicant.label.expected_ctc" /></td>
						<td><input type="text" name="expectedCTC" value='<%=request.getParameter("expectedCTC")%>' maxlength="10" size="25" class="Grey" /></td>
					</tr>
					<tr>
						<td class="label"><bean:message key="add_applicant.label.notice_period" /></td>
						<td><input type="text" name="noticePeriod" value='<%=request.getParameter("noticePeriod")%>' maxlength="25" size="25" class="Grey" /></td>
					</tr>
				</table>
				<br/>
				<br/>
				<br/>
				<table>
				</table>
				
								<% 	
								ArrayList importFields = ImportConfigurationManager.getImportFields();
								for(int j=0; j<importFields.size();j++){
									ImportFieldData fieldData = (ImportFieldData)importFields.get(j);
									String fieldId = fieldData.getFieldId();
									String fieldType = fieldData.getFieldType();
									String showValue = fieldData.getFieldWebsiteShow();
									String isMandatory = fieldData.getFieldWebsiteMandatory();
									
									if(showValue.equals(ImportConfigurationConstants.FIELD_SHOW)){													
																		
						if(customFields!=null && customFields.size()>0){ 
							for(int i=0; i<customFields.size();i++){
							CustomFieldData data = (CustomFieldData)customFields.get(i);
							String customFieldId = data.getFieldName();
								if(fieldId.equals(customFieldId)){%>					
									<table class="tabinput">
									<tr>
										<td style="width: 500px;"class="heading"><%=Utils.escapeHTML(data.getFieldDisplayName())%>
										<%if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){ %>
										<span class="star">*</span>
										<%} %>:</td>
										<td><%=data.getUI()%></td>
									</tr></br>
									</table>
								<%
								}
							} 
					 	} 
									}
								}
								%>
								
								<%
								if(customTables!=null && customTables.size()>0){ 
									for(int i=0; i<customTables.size();i++){
									CustomFieldTable data = (CustomFieldTable)customTables.get(i);
									String customFieldId = data.getTableId();
									 CustomFieldRow row = data.getRows().get(0);
									 String tableColumns = "";
									 String tableColumnNames = "";
									 StringBuffer sb= new StringBuffer();
									 String attribs = "";
									%>									
											<table class="tabinput" width="500">
											<tr id="<%= data.getTableName() %>rowdiv" style="display:none"><%=data.getRowUI()%></tr>
											</table>
											<table id = "<%= data.getTableName() %>" class="tabinput">
											<tr>
												<td class="header" style="width: 100px;"><span class="heading"><%=Utils.escapeHTML(data.getTableName())%></span>
												</td>
											</tr>
											<%= data.getUI() %>
											</table>
											<table class="tabinput" width="850">
													<tr>
														<td style="text-align: right;" class="Grey">
															<a href="#"	onclick="deleteTableFields('<%= data.getTableName() %>');" class="green"><bean:message key="common.delete" /> <bean:message key="common.last.selected" /></a>&nbsp;|&nbsp;
															<a href="#" onclick="addTableFields('<%= data.getTableName() %>');" class="green"><b><bean:message key="common.more" />>></b></a>
														</td>
													</tr>
											</table>
									
										<%
									}
									}
										%>	
										<br/>
										
										<table class="tabinput" cellspacing="0" cellpadding="5" border="0">
					<tr>
						<td class="label"><pre><bean:message key="add_applicant.resume.upload" />:<span class="star">*</span></pre></td>
										
			<td><input type ="file" name="attachedFile" size="60" style="height:20px;"/></td>
			</tr>
			
				<tr>
				   <td><input type ="checkbox" name="declaration" id="declaration" value= "0"  size="60" style="height:20px; checked"/><span class="star">*</span></td>
			<td class="label"><pre>I confirm that the above information is correct to the best of my knowledge.I agree that in the event of my obtaining employment,my probationary appointment
confirmation as well as continued employment in the services of the company are subject to clearance of medical test and background verification check done by company.</pre></td>
			
			</tr>
			
	</table>
									   
				<div class="navBtn" style="float: left;">
		<a href="#" style="width:60px;margin-top:5px;margin-left: 5px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit" /></a>
	</div>
	
	<br/><br/><br/><br/><br/>
</div>	
</div>
				</html:form>
			</td>
		</tr>
	</table>
	
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>

<script language="JavaScript">
var flag=false;
var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();


function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function uploadDocument() {  
	var url="importResume.do?mode=addDocument&option="+0;
	showInPopUp(url,500,160,null);
}



function addTableFields(tableId){
	var tBody = document.getElementById(tableId);
	//var rowArray = tableColumnNames.split(",");
	//var attribsArray = attribs.split("|");
	var rows = tBody.rows.length;
	var row = tBody.insertRow(rows);
	var rowName = tableId+'rowdiv';
	rowUI = document.getElementById(rowName).innerHTML;
	var ui = rowUI.replace(/@@/g, (parseInt(rows)-1));
	row.innerHTML = ui;
	
}

function deleteTableFields(tableId){
	var tBody = document.getElementById(tableId);
	var rows = tBody.rows.length;
	tBody.deleteRow(parseInt(rows)-1);
}

function onChangeSource() {
	document.forms[0].source.value=sources.getSelectedId();
}
var chkedRadio='images/checkedradiobutton.gif';
var unchkedRadio='images/radiobutton.gif';
function selectEdu(obj) {
	obj.src = chkedRadio;
	obj_id = obj.id;
	for(var i = 0; i < eduIndices.length; i++) {
		radioBtn = $("radio"+i);		
		if(radioBtn != null && radioBtn.id != obj_id) {
			radioBtn.src=unchkedRadio;
		}
	}
}
function getSelectedEducation() {
	var edu_id = '';
	for(var i = 0; i <= eduIndices.length; i++) {
		radioBtn = $("radio"+i);		
		if(radioBtn != null && radioBtn.src.indexOf(chkedRadio) != -1) {
			eduIndices[i] = -1;
			edu_id=i;
			break;
		}
	}
	return edu_id;
}
function deleteRow(table,rowId){
	for(var i=0;i<table.rows.length;i++){
		if(table.rows[i].id !=""){
			if(table.rows[i].id == rowId){
				Element.remove(table.rows[i]);				
			}
		}
	}
}
function deleteEducation() {
	var row_id = getSelectedEducation();	
	deleteRow(eduTable,row_id);
}
function addListener(row){
	Event.observe(row, "click", onRowClick.bindAsEventListener(this));
	Event.observe(row, "keydown", onRowKeyDown.bindAsEventListener(this));
	Event.observe(row, "keyup", onRowKeyUp.bindAsEventListener(this));
}
function onRowClick(event){
	var row = Event.findElement(event, 'tr');
	selectEdu($("radio" + row.id));
}
function onRowKeyDown(event){
	onRowClick(event);
}
function onRowKeyUp(event){
	onRowClick(event);
}
function onBlurstrtDat(event){
	var el =Event.element(event);
	getFormattedDate(el);
}

function onBlurEndDat(event){
	var el=Event.element(event);
	getFormattedDate(el);	
}

function getDegreeOptionId(k){
	var TYPE_10th = '10th';
	var TYPE_12th = '12th'; 
	var TYPE_UG = 'BE'; 
 	var degreesIdNameMap = [];
	degreesIdNameMap = <%=CommonUtils.getListJavaScriptArrayWithProperties(CommonUtils.getDegrees(), "degreeId", "title")%>;
			for (var j = 0; j < degreesIdNameMap.length; j++) {
					if ( TYPE_12th == degreesIdNameMap[j].oText && k==1){
						return degreesIdNameMap[j].oId;
					}
					if ( TYPE_UG == degreesIdNameMap[j].oText && k==2){
						return degreesIdNameMap[j].oId;
					}
			}		
	
	return -1;
	
}


function addEducation(k) {
	var tBody = eduTable.getElementsByTagName('tbody')[0];
	var myRow = document.createElement("tr");
	var lastElm = updateEduIndicesArray();
	myRow.id=lastElm;
	addListener(myRow);
	var myCell = document.createElement("td");
	var elm = document.createElement("img");
	elm.src='images/radiobutton.gif';
	elm.id="radio"+lastElm;
	Event.observe(elm, "click", onEduOptClick.bindAsEventListener(this));
	elm.onclick="javascript: selectEdu(this);"
	myCell.appendChild(elm);
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var selectBoxDegree = null;
	if(deg[lastElm]) {
		selectBoxDegree = new SelectBox(degreeOptions,deg[lastElm],'images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	} else {
		selectBoxDegree = new SelectBox(degreeOptions, getDegreeOptionId(k),'images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	}	
	myCell.innerHTML = selectBoxDegree.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="degree'+ lastElm +'" id="degree'+ lastElm +'"/>';
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var selectBoxBranch = null;
	if(brnch[lastElm]) {
		selectBoxBranch = new SelectBox(branchOptions,brnch[lastElm],'images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
	} else {
		selectBoxBranch = new SelectBox(branchOptions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
	}	
	myCell.innerHTML = selectBoxBranch.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="branch'+lastElm+'" id="branch'+lastElm+'"/>';	
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var startDate;

	if(strtDat[lastElm]) {
		startDate = createFormElement("input","text","startDate"+lastElm,strtDat[lastElm],"Grey");
	} else {
		startDate = createFormElement("input","text","startDate"+lastElm,"","Grey");
	}	
	startDate.id="startDate"+lastElm;
	startDate.size="10";
	Event.observe(startDate, "blur", onBlurstrtDat.bindAsEventListener(this));
	myCell.appendChild(startDate);	
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var endDate;
	if(endDat[lastElm]) {
		endDate = createFormElement("input","text","endDate"+lastElm,endDat[lastElm],"Grey");
	} else {
		endDate = createFormElement("input","text","endDate"+lastElm,"","Grey");
	}	
	endDate.id="endDate"+lastElm;
	endDate.size="10";
	Event.observe(endDate, "blur", onBlurEndDat.bindAsEventListener(this));
	myCell.appendChild(endDate);	
	myRow.appendChild(myCell);	
	
	
	
	myCell = document.createElement("td");
	var el;
	if(insti[lastElm]) {
		el = createFormElement("input","text","institute"+lastElm,insti[lastElm],"Grey");
	} else {
		el = createFormElement("input","text","institute"+lastElm,"","Grey");
	}	
	el.id="institute"+lastElm;
	el.size="27";
	el.maxlength="150";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	

	
	 
	
	myCell = document.createElement("td");
	var el;
	if(univrsty[lastElm]) {
		el = createFormElement("input","text","university"+lastElm,univrsty[lastElm],"Grey");
	} else {
		el = createFormElement("input","text","university"+lastElm,"","Grey");
	}	
	el.id="university"+lastElm;
	el.size="17";
	el.maxlength="150";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);
	
	
	
	myCell = document.createElement("td");
	var selectBoxTypeOfProgram = null;
	if(typOfPrgm[lastElm]) {
		selectBoxTypeOfProgram = new SelectBox(typeOfProgramOptions,typOfPrgm[lastElm],'images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	} else {
		selectBoxTypeOfProgram = new SelectBox(typeOfProgramOptions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	}	
	myCell.innerHTML = selectBoxTypeOfProgram.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="typeOfProgram'+lastElm+'" id="typeOfProgram'+lastElm+'"/>';	
	myRow.appendChild(myCell);
	
	tBody.appendChild(myRow);
	
	myCell = document.createElement("td");
	var el;
	if(grad[lastElm]) {
		el = createFormElement("input","text","grade"+lastElm,grad[lastElm],"Grey");
	} else {
		el = createFormElement("input","text","grade"+lastElm,"","Grey");
	}	
	el.id="grade"+lastElm;
	el.size="6";
	el.maxlength="100";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	
	
	tBody.appendChild(myRow);

	selectBoxDegree.init();
	selectBoxBranch.init();
	selectBoxTypeOfProgram.init();
	degreeControls[degreeControls.length]=selectBoxDegree;
	branchControls[branchControls.length]=selectBoxBranch;	
	typeOfProgramControls[typeOfProgramControls.length]=selectBoxTypeOfProgram;	
	selectEdu($("radio"+lastElm));
	if(document.forms[0].eduRowIds.value=='' || document.forms[0].eduRowIds.value=='null') {
		$("startDate"+lastElm).value='';
		$("endDate"+lastElm).value='';		
		$("institute"+lastElm).value='';
		$("university"+lastElm).value='';
		$("grade"+lastElm).value='';
	}
	
}
function onEduOptClick(event) {
	var el =Event.element(event); 
	selectEdu($(el.id));
}
var eduTable = $('eduTable');
var eduIndices = new Array();
window.onload=doOnLoad;
var strtDat = new Array();
var endDat = new Array();
var insti = new Array();
var deg = new Array();
var univrsty = new Array();
var brnch = new Array();
var typOfPrgm = new Array();
var grad = new Array();

<% if(!Utils.isBlankOrNull(request.getParameter("eduRowIds"))) { %>
<% String[] temp = request.getParameter("eduRowIds").split(","); %>
<% for(int i = 0; i < temp.length; i++) { %>
strtDat[<%=temp[i]%>]='<%=request.getParameter("startDate"+temp[i])%>';
endDat[<%=temp[i]%>]='<%=request.getParameter("endDate"+temp[i])%>';
insti[<%=temp[i]%>]='<%=request.getParameter("institute"+temp[i])%>';
deg[<%=temp[i]%>]='<%=request.getParameter("degree"+temp[i])%>';
univrsty[<%=temp[i]%>]='<%=request.getParameter("university"+temp[i])%>';
brnch[<%=temp[i]%>]='<%=request.getParameter("branch"+temp[i])%>';
typOfPrgm[<%=temp[i]%>]='<%=request.getParameter("typeOfProgram"+temp[i])%>';
grad[<%=temp[i]%>]='<%=request.getParameter("grade"+temp[i])%>';
<% } %>
<% } %>

var empTable = $('empTable');
var empIndices = new Array();
var empStrtDat = new Array();
var empEndDat = new Array();
var empl = new Array();
var desg = new Array();
var emptyp = new Array();
var loc = new Array();
var cntry = new Array();
var resonfrlvng = new Array();
var lastCt = new Array();


<% if(!Utils.isBlankOrNull(request.getParameter("empRowIds"))) { %>
<% String[] temp = request.getParameter("empRowIds").split(","); %>
<% for(int i = 0; i < temp.length; i++) { %>
empStrtDat[<%=temp[i]%>]='<%=request.getParameter("empStartDate"+temp[i])%>';
empEndDat[<%=temp[i]%>]='<%=request.getParameter("empEndDate"+temp[i])%>';
empl[<%=temp[i]%>]='<%=request.getParameter("employer"+temp[i])%>';
desg[<%=temp[i]%>]='<%=request.getParameter("designation"+temp[i])%>';
emptyp[<%=temp[i]%>]='<%=request.getParameter("empType"+temp[i])%>';
loc[<%=temp[i]%>]='<%=request.getParameter("location"+temp[i])%>';
cntry[<%=temp[i]%>]='<%=request.getParameter("country"+temp[i])%>';
resonfrlvng[<%=temp[i]%>]='<%=request.getParameter("reasonForLeaving"+temp[i])%>';
lastCt[<%=temp[i]%>]='<%=request.getParameter("lastCtc"+temp[i])%>';
<% } %>
<% } %>

function doOnLoad() {
	updateEduIndicesArray();
	updateEmpIndicesArray();
	addListener($("0"));
	if('<%=request.getParameter("fresher")%>' == '1') {
		toggleFresher();
	}	
	//onChangeSourceType();
	frm = document.forms[0];
	<% if(Utils.isBlankOrNull(request.getParameter("showDuplicatePage"))) {	%>			
		frm.name.value='';
		frm.dateOfBirth.value='';
		frm.currentLocation.value='';
		frm.phone1.value='';
		frm.phone2.value='';
		frm.mobile.value='';
		frm.email1.value='';
		frm.email2.value='';
		frm.workingSince.value='';
		frm.currentEmp.value='';
		frm.currentCTC.value='';
		frm.expectedCTC.value='';
		frm.noticePeriod.value='';
		frm.startDate0.value='';
		frm.endDate0.value='';
		frm.institute0.value='';
		frm.university0.value='';
		frm.degree0.value='';
		frm.branch0.value='';
		frm.typeOfProgram0.value='';
		frm.grade0.value='';
		frm.empStartDate0.value='';
		frm.empEndDate0.value='';
		frm.employer0.value='';
		frm.designation0.value='';
		frm.empType0.value='';
		frm.location0.value='';
		frm.country0.value='';
		frm.reasonForLeaving0.value='';
		frm.lastCtc0.value='';
	<% } %>
	var temp = '<%=request.getParameter("eduRowIds")%>';
	temp='0,1,2';
	if(temp != '' && temp != 'null') {
		parts = temp.split(',');
		var val = '-1';
		for(var k = 0; k < parts.length; k++) {
			if(parts[k] > val) {
				val = parts[k];
			}
		}
		if(val != -1) {
			for(var k = 1; k <= val; k++) {
				addEducation(k);
			}
		}
		for(var k = 0; k < parts.length; k++) {
			if(parts[k] != k) {
				eduIndices[k] = -1;
				deleteRow(eduTable,k);
			}
		}
	}	
	var emptemp = '<%=request.getParameter("empRowIds")%>';
	if(emptemp != '' && emptemp != 'null') {
		parts = emptemp.split(',');
		var val = '-1';
		for(var k = 0; k < parts.length; k++) {
			if(parts[k] > val) {
				val = parts[k];
			}
		}
		if(val != -1) {
			for(var k = 1; k <= val; k++) {
				addEmployment();
			}
		}
		for(var k = 0; k < parts.length; k++) {
			if(parts[k] != k) {
				empIndices[k] = -1;
				deleteRow(empTable,k);
			}
		}
	}	
	<%-- var skills = '<%=request.getParameter("skillIds")%>';
	if(skills != '' && skills != 'null') {
		setSelectedItems(frm.skills, frm.selectedSkills, skills);
	} --%>
}

function setSelectedItems(masterList, selectedList, selectedItems) {
	if(selectedItems != null && selectedItems.length > 0) {
		parts = selectedItems.split(',');		
		for (var indx = 0; indx < parts.length; indx++) {
			for (var indx2 = 0; indx2 < masterList.length; indx2++) {
				if (masterList[indx2].value == parts[indx]) {
					val = masterList[indx2].value;
				    dispval = masterList[indx2].text;			    
					selectedList[selectedList.length] = new Option(dispval, val);
			        masterList[indx2] = null;
					break;
				}
			}
		}
	}
}
function updateEduIndicesArray() {
	var len = eduIndices.length;	
	eduIndices[len] = len;	
	return len
}

function onChangeSourceType() {	
	document.forms[0].sourceType.value=sourceTypeFilters.getSelectedId();
	var selectedSourceType = sourceTypeFilters.getSelectedId();
	var pars = "mode=getSourcesInJSArrayOfSourceType&sourceTypeId=" + selectedSourceType;
	var myAjax = ajaxCall("applicantRegistration.do",'get',pars,populateSources, reportError);
}

function populateSources(request) {	
	xmlFile = request.responseXML;
	if(!isErrorXml(xmlFile)){		
		var op = request.responseText;
		var opts = eval(op);		
        opt = [new SelectOption('-1','<bean:message key="common.selectlist.select" />')];
		m = opt.concat(opts);		
		sources.reInitialize(m, '<%=request.getParameter("source")%>');
		document.forms[0].source.value='<%=request.getParameter("source")%>';
	}else {
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
	if(!dtfo.checkDate(obj)&&!flag){
		flag=true;
		obj.select();
		//alert("Please enter date in " + format + " format");
		alert('<bean:message key="applicant_registration.error.enter_date_in" />' + format + ' <bean:message key="applicant_registration.error.format" />');
		window.setTimeout(function ()
			    {
					obj.focus();	
					flag=false;
			    }, 100);
		return false;
	}else {
		return true;
	}
	}
	return true;
}

function submitForm() {
	validCustomfields('<%=CustomFieldConstants.DEFAULT_SELECT_OPTION%>', '<%=CustomFieldConstants.TYPE_DROPDOWN%>', '<%=CustomFieldConstants.TYPE_LISTBOX%>', '<%=CustomFieldConstants.TYPE_CHECKBOX%>', '<%=CustomFieldConstants.TYPE_RADIO%>', false);
	errors = validateForm();
	if(errors.length > 0) {
		alert(errors);
		return false;
	} else {
	
		for(var i = 0; i < degreeControls.length; i++) {
			var control = $('degree'+i);
			if(control) {
				control.value = degreeControls[i].getSelectedId();
			}
		}
		for(var i = 0; i < branchControls.length; i++) {
			var control = $('branch'+i);
			if(control) {
				control.value = branchControls[i].getSelectedId();
			}
		}
		for(var i = 0; i < typeOfProgramControls.length; i++) {
			var control = $('typeOfProgram'+i);
			if(control) {
				control.value = typeOfProgramControls[i].getSelectedId();
			}
		}
		for(var i = 0; i < empTypeControls.length; i++) {
			var control = $('empType'+i);
			if(control) {
				control.value = empTypeControls[i].getSelectedId();
			}
		}
		for(var i = 0; i < countryControls.length; i++) {
			var control = $('country'+i);
			if(control) {
				control.value = countryControls[i].getSelectedId();
			}
		}
		for(var i = 0; i < employerControls.length; i++) {
			var control = $('employer'+i);
			if(control) {
				control.value = employerControls[i].getSelectedId();
			}
		}
		/* for(var i = 0; i < locationControls.length; i++) {
			var control = $('location'+i);
			if(control) {
				control.value = locationControls[i].getSelectedId();
			}
		} */
		var eduRows = '';
		for(var i = 0; i < eduIndices.length; i++) {
			if(eduIndices[i] != -1) {
				if(eduRows.length > 0) {
					eduRows += ',';
				}
				eduRows += eduIndices[i];
			}
		}
		
		var empRows = '';
		console.log('empIndices.length',empIndices.length);
		for(var i = 0; i < empIndices.length; i++) {
			if(empIndices[i] != -1) {
				if(empRows.length > 0) {
					empRows += ',';
				}
				empRows += empIndices[i];
			}
		}
		/* setSelectedSkills(); */
		document.forms[0].eduRowIds.value=eduRows;
		document.forms[0].empRowIds.value=empRows;
		document.forms[0].submit();
		return true;
	}	
}

function validateForm() {
	errors = '';
	console.log('frm.dateOfBirth.value',frm.dateOfBirth.value);
	frm = document.forms[0];
	if(!frm.declaration.checked){
		frm.declaration.focus();
		frm.declaration.value=0;
		errors = addError(errors, '- Please check declaration.');
		return errors;
	}else{
		frm.declaration.value=1;
	}
	if(frm.name.value.strip() == '') {
		frm.name.focus();
		errors = addError(errors, '- name is blank.');
		return errors;
	}
	if(frm.dateOfBirth.value.strip()==''){
		frm.dateOfBirth.focus();
		errors = addError(errors, '- date of birth is required.');
		return errors;
	}
	if(frm.phone1.value.strip() == '') {
		frm.phone1.focus();
		errors = addError(errors, '- phone is blank');
		return errors;
	}
	if(frm.attachedFile.value.strip() == '') {
		frm.attachedFile.focus();
		errors = addError(errors, '- please upload Resume');
		return errors;
	}
	if(frm.email1.value.strip() == '') {
		frm.email1.focus();
			errors = addError(errors, '-  email1 is empty.');
			return errors;
	}
	if(frm.email1.value.strip() != '') {
		if(!validateEmailAddress(frm.email1.value)) {
			frm.email1.focus();
			errors = addError(errors, '- not a valid email1');
			return errors;
		}
	}
	if(frm.email2.value.strip() != '') {
		if(!validateEmailAddress(frm.email2.value)) {
			frm.email2.focus();
			errors = addError(errors, '- not a valid email');
			return errors;
		}
	}
	/* if(frm.source.value.strip() == '' || frm.source.value == 'null') {
		frm.source.focus();
		errors = addError(errors, '- source is blank');
		return errors;
	} */
	if(frm.fresher.value=="0") {
		if(frm.workingSince.value.strip() == '') {
			frm.workingSince.focus();
			errors = addError(errors, '- working since date is not selected.');
			return errors;
		}
	    for(var i = 0; i < empIndices.length; i++) {
			if(empIndices[i] != -1) {
				if(frm['empStartDate'+i].value.strip()==''){
					frm['empStartDate'+i].focus();
					errors = addError(errors, '- employment start date is required.');
					return errors;
				}
				if(frm['empEndDate'+i].value.strip()==''){
					frm['empEndDate'+i].focus();
					errors = addError(errors, '- employment End date is required.');
					return errors;
				}
				if(frm['designation'+i].value.strip()==''){
					frm['designation'+i].focus();
					errors = addError(errors, '- designation is required.');
					return errors;
				}
				if(frm['location'+i].value.strip()==''){
					frm['location'+i].focus();
					errors = addError(errors, '- location is required.');
					return errors;
				}
				if(frm['reasonForLeaving'+i].value.strip()==''){
					frm['reasonForLeaving'+i].focus();
					errors = addError(errors, '- reason For Leaving is required.');
					return errors;
				}
				if(frm['lastCtc'+i].value.strip()==''){
					frm['lastCtc'+i].focus();
					errors = addError(errors, '- Last Drawn Ctc is required.');
					return errors;
				}
				if(frm['lastCtc'+i].value!=''){
					if(!validateNumber(frm['lastCtc'+i].value)) {
						frm['lastCtc'+i].focus();
						errors = addError(errors, '- Last Drawn Ctc should be  in Number or Decimal');
						return errors;
					}
				}
				
			}
		}
	}
	
	var TYPE_10TH = "4";
    var TYPE_12TH = "5";
    var TYPE_UG = "1";
    var is_10th_EducationDetails_Filled = false;
    var is_12th_EducationDetails_Filled = false;
    var is_UG_EducationDetails_Filled = false;
	 var degreesType = [];
	degreesType = <%=CommonUtils.getJsArrayDegreeType()%>;
	for(var i = 0; i < degreeControls.length; i++) {
		var selectedDegreeId = degreeControls[i].getSelectedId();
			for (var j = 0; j < degreesType.length; j++) {
				if ( selectedDegreeId == degreesType[j].oId){
					if ( TYPE_UG == degreesType[j].oText){
						is_UG_EducationDetails_Filled = true;
					}  if ( TYPE_10TH == degreesType[j].oText){
						is_10th_EducationDetails_Filled = true;
					}  if ( TYPE_12TH == degreesType[j].oText){
						is_12th_EducationDetails_Filled = true;
					}
				}
			}
		}
	
	if (!is_10th_EducationDetails_Filled) {
		errors = addError(errors, '- 10th details in Education Details');
		return errors;
    }
    if (!is_12th_EducationDetails_Filled) {
    	errors = addError(errors, '- 12th details in Education Details');
    	return errors;
    }

    if (!is_UG_EducationDetails_Filled) {
    	errors = addError(errors, '- UG details in Education Details');
    	return errors;
    } 
	
    if (is_10th_EducationDetails_Filled && is_12th_EducationDetails_Filled && is_UG_EducationDetails_Filled){
    	for(var i = 0; i < eduIndices.length; i++) {
    		if(eduIndices[i] != -1) {
    			if(frm['startDate'+i].value.strip()==''){
    				frm['startDate'+i].focus();
    				errors = addError(errors, '- Education start date is blank.');
    				return errors;
    			}
    			if(frm['endDate'+i].value.strip()==''){
    				frm['endDate'+i].focus();
    				errors = addError(errors, '- Education End date is blank.');
    				return errors;
    			}
    			if(frm['institute'+i].value.strip()==''){
    				frm['institute'+i].focus();
    				errors = addError(errors, '- Institue Name is blank.');
    				return errors;
    			}
    			if(frm['university'+i].value.strip()==''){
    				frm['university'+i].focus();
    				errors = addError(errors, '- Univerity Name or Board is blank.');
    				return errors;
    			}
    			if(frm['grade'+i].value.strip()==''){
    				frm['grade'+i].focus();
    				errors = addError(errors, '- Percentage Marks should not be blank .it should be in Number or decimal.');
    				return errors;
    			}
    			if(frm['grade'+i].value!=''){
    				if(!validateNumber(frm['grade'+i].value)) {
    					frm['grade'+i].focus();
    					errors = addError(errors, '- Percentage Marks should be in Number or Decimal');
    					return errors;
    				}
    				
    			}
    		}
    	}
    }
    

	<%
	ArrayList importFields = ImportConfigurationManager.getImportFields();
	for(int j=0; j<importFields.size();j++){
		ImportFieldData fieldData = (ImportFieldData)importFields.get(j);
		String fieldId = fieldData.getFieldId();
		String fieldType = fieldData.getFieldType();
		String showValue = fieldData.getFieldWebsiteShow();
		String isMandatory = fieldData.getFieldWebsiteMandatory();
		
		 if(showValue.equals(ImportConfigurationConstants.FIELD_SHOW)){ 												
			
			if(customFields!=null && customFields.size()>0){ 
				for(int i=0; i<customFields.size();i++){
				CustomFieldData data = (CustomFieldData)customFields.get(i);
				String customFieldId = data.getFieldName();
					if(fieldId.equals(customFieldId)&& showValue.equals(ImportConfigurationConstants.FIELD_SHOW) && isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
		%>	
		if($('<%=data.getFieldName()%>').value == '') {
			errors = addError(errors," <%=data.getFieldDisplayName()%>");
		}
		<%}
				}
			}
		}
	}
	%>	
	
	if(errors != '') {
		errors = addError('Following data is required/missing:', errors);
	}
	return errors;
}

function validateNumber(num){
	var pattern=/^\d+(\.\d{1,2})?$/;
    if(pattern.test(num)){         
		return true;
    }else{   
		return false;
    }
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
        selectedList[selectedList.length] = new Option(dispval, val);
        masterList[i] = null;
        break;
      }
    }
  }
}

function remove(masterList, selectedList) {
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
        masterList[masterList.length] = new Option(dispval, val);
        selectedList[i] = null;
        break;
      }
    }
  }
}
function setSelectedSkills() {
	var skillsStr = '';
	for (var j = 0; j < document.forms[0].selectedSkills.length; j++) {
        if(skillsStr.length > 0) {
        	skillsStr += ',';
        }
        skillsStr += document.forms[0].selectedSkills[j].value
    }
    document.forms[0].skillIds.value=skillsStr;
}
var dtf = new DateFormatter();
dtf.setDisplayFormat('MMM-YYYY');
function getFormattedDate(obj){
	if(obj.value.trim()!=''){
	if(!dtf.checkDate(obj)&&!flag){
		flag=true;
		obj.select();
		alert("<bean:message key="add_applicant.errors.working_since_invalid_format"/>");
		window.setTimeout(function ()
			    {
					obj.focus();	
					flag=false;
			    }, 100);
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
			flag=true;
			alert('<bean:message key="add_applicant.errors.yearofoassing_invalid_format"/>');
			window.setTimeout(function ()
				    {
						obj.focus();	
						flag=false;
				    }, 100);
			return false;
		}
	}
	return true;
}
var chkedChkBox="images/checkboxchecked.gif";
var unchkedChkBox="images/checkboxunchecked.gif";
function toggleFresher() {
	var cbo = $('cboFresher');
	if(cbo.src.indexOf(chkedChkBox) != -1) {
		document.forms[0].fresher.value=0;
		cbo.src=unchkedChkBox;
	} else {
		document.forms[0].fresher.value=1;
		cbo.src=chkedChkBox;
	}
}
var cal = new MonthYearCalender('divCalender');
cal.setDisplayFormat('MMM-YYYY');
cal.setDateStyle('EU');

function onBlurEmpHistoryFromDate(event){
	var el=Event.element(event);
	getFormattedDate(el);	
}

function onBlurEmpHistoryToDate(event){
	var el=Event.element(event);
	getFormattedDate(el);			
}


function addEmployment() {
	var tBody = empTable.getElementsByTagName('tbody')[0];
	var myRow = document.createElement("tr");
	var lastElm = updateEmpIndicesArray();
	myRow.id=lastElm;
	addListener(myRow);
	var myCell = document.createElement("td");
	var elm = document.createElement("img");
	elm.src='images/radiobutton.gif';
	elm.id="radio"+lastElm;
	Event.observe(elm, "click", onEmpOptClick.bindAsEventListener(this));
	elm.onclick="javascript: selectEmp(this);"
	myCell.appendChild(elm);
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var elFrom;

	if(empStrtDat[lastElm]) {
		elFrom = createFormElement("input","text","empStartDate"+lastElm,empStrtDat[lastElm],"Grey");
	} else {
		elFrom = createFormElement("input","text","empStartDate"+lastElm,"","Grey");
	}	
	elFrom.id="empStartDate"+lastElm;
	elFrom.size="10";
	Event.observe(elFrom, "blur", onBlurEmpHistoryFromDate.bindAsEventListener(this));
	myCell.appendChild(elFrom);	
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var elTo;
	if(empEndDat[lastElm]) {
		elTo = createFormElement("input","text","empEndDate"+lastElm,empEndDat[lastElm],"Grey");
	} else {
		elTo = createFormElement("input","text","empEndDate"+lastElm,"","Grey");
	}	
	elTo.id="empEndDate"+lastElm;
	elTo.size="10";
	Event.observe(elTo, "blur", onBlurEmpHistoryToDate.bindAsEventListener(this));
	myCell.appendChild(elTo);	
	myRow.appendChild(myCell);	
	

	myCell = document.createElement("td");
	var selectBoxEmployer = null;
	if(empl[lastElm]) {
		selectBoxEmployer = new SelectBox(employerOptions,empl[lastElm],'images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	} else {
		selectBoxEmployer = new SelectBox(employerOptions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	}	
	myCell.innerHTML = selectBoxEmployer.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="employer'+ lastElm +'" id="employer'+ lastElm +'"/>';
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var designationv;
	if(desg[lastElm]) {
		designationv = createFormElement("input","text","designation"+lastElm,desg[lastElm],"Grey");
	} else {
		designationv = createFormElement("input","text","designation"+lastElm,"","Grey");
	}	
	designationv.id="designation"+lastElm;
	designationv.size="10";
	designationv.maxlength="100";
	myCell.appendChild(designationv);	
	myRow.appendChild(myCell);	
	
	
	myCell = document.createElement("td");
	var selectBoxEmpType = null;
	if(emptyp[lastElm]) {
		selectBoxEmpType = new SelectBox(empTypeOptions,emptyp[lastElm],'images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	} else {
		selectBoxEmpType = new SelectBox(empTypeOptions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'75px', size:10, textboxclass:'Grey'});
	}	
	myCell.innerHTML = selectBoxEmpType.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="empType'+ lastElm +'" id="empType'+ lastElm +'"/>';
	myRow.appendChild(myCell);
	
/* 	myCell = document.createElement("td");
	var selectBoxLocation = null;
	if(loc[lastElm]) {
		selectBoxLocation = new SelectBox(locationOptions,loc[lastElm],'images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
	} else {
		selectBoxLocation = new SelectBox(locationOptions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
	}	
	myCell.innerHTML = selectBoxLocation.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="location'+lastElm+'" id="location'+lastElm+'"/>';	
	myRow.appendChild(myCell); */
	
	myCell = document.createElement("td");
	var locationv;
	if(loc[lastElm]) {
		locationv = createFormElement("input","text","location"+lastElm,loc[lastElm],"Grey");
	} else {
		locationv = createFormElement("input","text","location"+lastElm,"","Grey");
	}	
	locationv.id="location"+lastElm;
	locationv.size="10";
	locationv.maxlength="100";
	myCell.appendChild(locationv);	
	myRow.appendChild(myCell);	
	
	myCell = document.createElement("td");
	var selectBoxCountry = null;
	if(cntry[lastElm]) {
		selectBoxCountry = new SelectBox(countryOptions,cntry[lastElm],'images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
	} else {
		selectBoxCountry = new SelectBox(countryOptions,'-1','images/btn_dropdown.gif',{namesonly:false, width:'120px', size:10, textboxclass:'Grey'});
	}	
	myCell.innerHTML = selectBoxCountry.getHtml();
	myCell.innerHTML = myCell.innerHTML + '<input type="hidden" name="country'+ lastElm +'" id="country'+ lastElm +'"/>';
	myRow.appendChild(myCell);
	
	myCell = document.createElement("td");
	var el;
	if(resonfrlvng[lastElm]) {
		el = createFormElement("input","text","reasonForLeaving"+lastElm,resonfrlvng[lastElm],"Grey");
	} else {
		el = createFormElement("input","text","reasonForLeaving"+lastElm,"","Grey");
	}	
	el.id="reasonForLeaving"+lastElm;
	el.size="10";
	el.maxlength="100";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	
	
	myCell = document.createElement("td");
	var el;
	if(lastCt[lastElm]) {
		el = createFormElement("input","text","lastCtc"+lastElm,lastCt[lastElm],"Grey");
	} else {
		el = createFormElement("input","text","lastCtc"+lastElm,"","Grey");
	}	
	el.id="lastCtc"+lastElm;
	el.size="10";
	el.maxlength="100";
	myCell.appendChild(el);	
	myRow.appendChild(myCell);	
	
	tBody.appendChild(myRow);

	selectBoxEmployer.init();
	selectBoxCountry.init();
	selectBoxEmpType.init();
	employerControls[employerControls.length]=selectBoxEmployer;
	countryControls[countryControls.length]=selectBoxCountry;	
	empTypeControls[empTypeControls.length]=selectBoxEmpType;	
	selectEmp($("radio"+lastElm));
	if(document.forms[0].empRowIds.value=='' || document.forms[0].empRowIds.value=='null') {
		$("empStartDate"+lastElm).value='';
		$("empEndDate"+lastElm).value='';	
		$("designation"+lastElm).value='';
		$("location"+lastElm).value='';	
		$("reasonForLeaving"+lastElm).value='';
		$("lastCtc"+lastElm).value='';
	}
}

var chkedEmpRadio='images/checkedradiobutton.gif';
var unchkedEmpRadio='images/radiobutton.gif';
function selectEmp(obj) {
	obj.src = chkedEmpRadio;
	obj_id = obj.id;
	for(var i = 0; i < empIndices.length; i++) {
		radioBtn = $("radio"+i);		
		if(radioBtn != null && radioBtn.id != obj_id) {
			radioBtn.src=unchkedEmpRadio;
		}
	}
}
function getSelectedEmployment() {
	var edm_id = '';
	for(var i = 0; i <= empIndices.length; i++) {
		radioBtn = $("radio"+i);		
		if(radioBtn != null && radioBtn.src.indexOf(chkedEmpRadio) != -1) {
			empIndices[i] = -1;
			edm_id=i;
			break;
		}
	}
	return edm_id;
}

function updateEmpIndicesArray() {
	var len = empIndices.length;	
	empIndices[len] = len;	
	return len
}

function deleteEmployment() {
	var row_id = getSelectedEmployment();	
	deleteRow(empTable,row_id);
}

function onEmpOptClick(event) {
	var el =Event.element(event); 
	selectEmp($(el.id));
}

function escapeHtml(unsafe) {
    return unsafe
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;");
 }
</script>  