<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.custom.manager.CustomFieldManager"%>
<%@page import="com.talentPool.custom.constants.CustomFieldConstants"%>
<%@page import="com.talentPool.custom.dataobject.CustomFieldData"%>
<%@page import="com.talentPool.inbox.InboxConstants"%>
<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.applicant.constants.ImportConfigurationConstants"%>
<%@page import="com.talentPool.applicant.dataobject.ImportFieldData"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<html:form action="/inbox" onsubmit="submitForm();return false;">
   <html:hidden property="mode" name="inboxForm"/>
   <html:hidden property="filePath" name="inboxForm"/>
   <html:hidden property="sessionId" name="inboxForm"/>
   <html:hidden property="mappings" name="inboxForm"/>
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
                  <b>
                     <bean:message key="errors.following_errors"/>
                  </b>
               </td>
            </tr>
            <tr>
               <td class="message">
                  <html:errors/>
               </td>
            </tr>
         </table>
         <br/>
         <% } %>
      </div>
   </div>
   <div class="contentDivPop" style="padding-right:20px;" >
   <strong>Map the following column from excel file</strong>
   <br/><br/>
   <table width="100%" class="boxHeader" cellspacing="2" cellpading="2">
      <tr>
         <td class="header" height="18" style="width:300px;" ><b>Import file columns</b></td>
         <td style="width:300px;"><b>Fields in application</b></td>
         <td><b>Fields type</b></td>
      </tr>
   </table>
   <div class="outerDiv" style="padding:0px 0px 0px 0px;border-top:none;">
      <table cellspacing="2" cellpadding="5" class="boxContent" style="border:0px;width: 100%">
         <% int ctlNo=0;%>
         <logic:iterate id="names" name="excelFieldName" scope="request">
            <tr>
               <td style="width:300px;">
                  <bean:write name="names" />
               </td>
               <td style="width:300px;">
                  <script type="text/javascript">
                     var opts = new Array();
                     opts[opts.length] = new SelectOption('0','Skip this column');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_NAME%>','<bean:message key="common.name"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMAIL_1%>','<bean:message key="common.email1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMAIL_2%>','<bean:message key="common.email2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_PHONE_1%>','<bean:message key="common.phone1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_PHONE_2%>','<bean:message key="common.phone2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_MOBILE%>','<bean:message key="common.mobile"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_DATE_OF_BIRTH%>','<bean:message key="common.date_of_birth"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EXPERIENCE%>','<bean:message key="common.experience"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_SOURCE%>','<bean:message key="common.source"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_CURRENT_LOCATION%>','<bean:message key="common.current_location"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_SKILLS%>','<bean:message key="common.skills"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_YEAR_OF_PASSING_1%>','<bean:message key="common.year_of_passing_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_START_DATE_OF_PASSING_1%>','<bean:message key="common.start_date_of_passing_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_END_DATE_OF_PASSING_1%>','<bean:message key="common.end_date_of_passing_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_INSTITUTE_1%>','<bean:message key="common.institute_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_DEGREE_1%>','<bean:message key="common.degree_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_BRANCH_1%>','<bean:message key="common.branch_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_1%>','<bean:message key="common.university_of_passing_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_1%>','<bean:message key="common.type_of_program_of_passing_1"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_1%>','<bean:message key="common.percentage_passing_1"/>');						
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_YEAR_OF_PASSING_2%>','<bean:message key="common.year_of_passing_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_START_DATE_OF_PASSING_2%>','<bean:message key="common.start_date_of_passing_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_END_DATE_OF_PASSING_2%>','<bean:message key="common.end_date_of_passing_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_INSTITUTE_2%>','<bean:message key="common.institute_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_DEGREE_2%>','<bean:message key="common.degree_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_BRANCH_2%>','<bean:message key="common.branch_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_2%>','<bean:message key="common.university_of_passing_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_2%>','<bean:message key="common.type_of_program_of_passing_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_2%>','<bean:message key="common.percentage_passing_2"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_YEAR_OF_PASSING_3%>','<bean:message key="common.year_of_passing_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_START_DATE_OF_PASSING_3%>','<bean:message key="common.start_date_of_passing_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_END_DATE_OF_PASSING_3%>','<bean:message key="common.end_date_of_passing_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_INSTITUTE_3%>','<bean:message key="common.institute_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_DEGREE_3%>','<bean:message key="common.degree_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_BRANCH_3%>','<bean:message key="common.branch_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_3%>','<bean:message key="common.university_of_passing_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_3%>','<bean:message key="common.type_of_program_of_passing_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_3%>','<bean:message key="common.percentage_passing_3"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_YEAR_OF_PASSING_4%>','<bean:message key="common.year_of_passing_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_START_DATE_OF_PASSING_4%>','<bean:message key="common.start_date_of_passing_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_END_DATE_OF_PASSING_4%>','<bean:message key="common.end_date_of_passing_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_INSTITUTE_4%>','<bean:message key="common.institute_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_DEGREE_4%>','<bean:message key="common.degree_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_BRANCH_4%>','<bean:message key="common.branch_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_4%>','<bean:message key="common.university_of_passing_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_4%>','<bean:message key="common.type_of_program_of_passing_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_4%>','<bean:message key="common.percentage_passing_4"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_CURRENT_EMPLOYER%>','<bean:message key="common.current_employer"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_CURRENT_CTC%>','<bean:message key="common.current_ctc"/>');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EXPECTED_CTC%>','<bean:message key="common.expected_ctc"/>');						
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_NOTICE_PERIOD%>','<bean:message key="common.time_to_join"/>');						
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_NOTE%>','<bean:message key="common.note"/>');						
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_ORIGINAL_RESUME_PATH%>','<bean:message key="common.original_resume_path"/>');						
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_1%>','<bean:message key="common.employment_from_date"/>_1');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_1%>','<bean:message key="common.employment_to_date"/>_1');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_1%>','<bean:message key="common.employer"/>_1');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_1%>','<bean:message key="common.designation"/>_1');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_TYPE_1%>','<bean:message key="common.employment_type"/>_1');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_LOCATION_1%>','<bean:message key="common.employment_location"/>_1');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_COUNTRY_1%>','<bean:message key="common.employment_country"/>_1');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_2%>','<bean:message key="common.employment_from_date"/>_2');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_2%>','<bean:message key="common.employment_to_date"/>_2');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_2%>','<bean:message key="common.employer"/>_2');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_2%>','<bean:message key="common.designation"/>_2');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_TYPE_2%>','<bean:message key="common.employment_type"/>_2');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_LOCATION_2%>','<bean:message key="common.employment_location"/>_2');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_COUNTRY_2%>','<bean:message key="common.employment_country"/>_2');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_3%>','<bean:message key="common.employment_from_date"/>_3');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_3%>','<bean:message key="common.employment_to_date"/>_3');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_3%>','<bean:message key="common.employer"/>_3');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_3%>','<bean:message key="common.designation"/>_3');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_4%>','<bean:message key="common.employment_from_date"/>_4');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_4%>','<bean:message key="common.employment_to_date"/>_4');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_4%>','<bean:message key="common.employer"/>_4');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_4%>','<bean:message key="common.designation"/>_4');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_5%>','<bean:message key="common.employment_from_date"/>_5');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_5%>','<bean:message key="common.employment_to_date"/>_5');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_5%>','<bean:message key="common.employer"/>_5');
                     opts[opts.length] = new SelectOption('<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_5%>','<bean:message key="common.designation"/>_5');
                     
                     // Custom Fileds
                     <%
                        if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
                        	CustomFieldManager customFieldManager = new CustomFieldManager();
                        	ArrayList customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
                        	for (int c = 0; c < customFields.size(); c++) {
                        		CustomFieldData cData = (CustomFieldData) customFields.get(c);
                        	%>
                     		opts[opts.length] = new SelectOption('<%=cData.getFieldName()%>','<%=cData.getFieldDisplayName()%>');
                     	<%
                        }
                        }
                        %>
                        <%
                        if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
                        	CustomFieldManager customFieldManager = new CustomFieldManager();
                        	ArrayList customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
                        	for (int c = 0; c < customFields.size(); c++) {
                        		CustomFieldData cData = (CustomFieldData) customFields.get(c);
                        	%>
                     		opts[opts.length] = new SelectOption('<%=cData.getFieldName()%>','<%=cData.getFieldDisplayName()%>');
                     	<%
                        }
                        }
                        %>
                     col_<%=ctlNo%> = new SelectBox(opts,'0','images/btn_dropdown.gif',{namesonly:false, width:'160px', size:10, controlname:'col_<%=ctlNo%>'});
                     document.write(col_<%=ctlNo%>.getHtml());
                     col_<%=ctlNo%>.init();						
                     col_<%=ctlNo%>.setOnChangeHandler('showFieldType');
                  </script>
               </td>
               <td id="type_<%=ctlNo%>">&nbsp;</td>
            </tr>
            <%ctlNo++;%>
         </logic:iterate>
      </table>
   </div>
   <div id="divImportAllButton" class="navBtn" style="margin-top:15px;float: right;">
      <a href="#" style="width:60px;" class="active" onclick="javascript:submitForm();"><span class="rightC"></span><span class="leftC"></span>Import</a>
      <a href="#" style="width:70px; margin-left:5px;" class="active" onclick="cancelImport('inbox.do?mode=inbox');">
         <span class="rightC"></span><span class="leftC"></span>
         <bean:message key="common.cancel"/>
      </a>
   </div>
   <div id="divWaitForParsing" style="margin-top:15px;display:none;">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" align="right">
         <tr>
            <td style="text-align: right;" >
               <img src="images/wait.gif" style="margin-bottom: -3px;"/>
               <bean:message key="common.please_wait"/>
               ,					 
               Excel import is in process &nbsp;
            </td>
            <td>
               <div id="divParsedNumber" style="display:block;margin-bottom: -3px;" />
            </td>
         </tr>
      </table>
      </div>
   </div>
</html:form>
<script language="javascript">
   var fldTypeMap = new Object(); 
   
   function getAllColumnMappings(){
   //var mappings = "";
  	//var mappings = "NAME,app_LastName,EMAIL_1,MOBILE,DATE_OF_BIRTH,app_Maritalstatus,app_PermanentAddress,app_CurrentAddress,EXPECTED_CTC,app_PrefLocDeatils,CURRENT_CTC,EXPERIENCE,NOTICE_PERIOD,app_gender,SOURCE";
   	var mappings = "0,NAME,app_MiddleName,app_LastName,EMAIL_1,MOBILE,PHONE_1,DATE_OF_BIRTH,app_gender,app_Maritalstatus,app_PAN,app_Disability,app_PermanentAddress,0,app_CurrentAddress,CURRENT_LOCATION,0,EXPECTED_CTC,app_PrefLocDeatils,CURRENT_CTC,EXPERIENCE,app_EmpGapDetails,NOTICE_PERIOD,START_DATE_OF_PASSING_1,END_DATE_OF_PASSING_1,TYPE_OF_PROGRAM_OF_PASSING_1,UNIVERSITY_OF_PASSING_1,INSTITUTE_1,BRANCH_1,PERCENTAGE_OF_PASSING_1,DEGREE_1,START_DATE_OF_PASSING_2,END_DATE_OF_PASSING_2,TYPE_OF_PROGRAM_OF_PASSING_2,UNIVERSITY_OF_PASSING_2,INSTITUTE_2,BRANCH_2,PERCENTAGE_OF_PASSING_2,DEGREE_2,START_DATE_OF_PASSING_3,END_DATE_OF_PASSING_3,TYPE_OF_PROGRAM_OF_PASSING_3,UNIVERSITY_OF_PASSING_3,INSTITUTE_3,BRANCH_3,PERCENTAGE_OF_PASSING_3,DEGREE_3,START_DATE_OF_PASSING_4,END_DATE_OF_PASSING_4,TYPE_OF_PROGRAM_OF_PASSING_4,UNIVERSITY_OF_PASSING_4,INSTITUTE_4,BRANCH_4,PERCENTAGE_OF_PASSING_4,DEGREE_4,0,EMPLOYMENT_FROM_DATE_1,EMPLOYMENT_TO_DATE_1,EMPLOYMENT_TYPE_1,EMPLOYMENT_DESIGNATION_1,EMPLOYMENT_EMPLOYER_1,0,EMPLOYMENT_LOCATION_1,EMPLOYMENT_COUNTRY_1,EMPLOYMENT_FROM_DATE_2,EMPLOYMENT_TO_DATE_2,EMPLOYMENT_TYPE_2,EMPLOYMENT_DESIGNATION_2,EMPLOYMENT_EMPLOYER_2,0,EMPLOYMENT_LOCATION_2,EMPLOYMENT_COUNTRY_2,Language,Read,Speak,Write,Language,Read,Speak,Write,Language,Read,Speak,Write,RefName,RefDesignation,RefAdd,RefCon,0,ORIGINAL_RESUME_PATH,SOURCE";
   	
   	<%-- 	  <%
      ArrayList fieldNames = (ArrayList) request.getAttribute("excelFieldName");
      for(int j=0; j<fieldNames.size();j++){
      String fieldName = (String)fieldNames.get(j);
      %>
   		if(mappings==""){
   			mappings = col_<%=j%>.getSelectedId();
   		}else{
   			mappings += "," + col_<%=j%>.getSelectedId();
   		}
   	<%}%> --%>
   	return mappings;
   }
   
   function showFieldType(index,obj){
   	var rowId = obj.getControlName();
   	var rowIds = rowId.split("_");
	var rowTypeId = "type_" + rowIds[1];
   	var selectedId = obj.getSelectedId();
   	
   	$(rowTypeId).innerHTML = fldTypeMap[selectedId];
   }
   
   function submitForm(){
   	var mappings = getAllColumnMappings();
   	errors = validate(mappings);
   	if (errors.length > 0) {
   		alert(errors);
   		return false;
   	}
   	var frm=document.inboxForm;	
   	frm.mappings.value=mappings;
   	frm.mode.value="showCSVImportData";
   	
   	$("divImportAllButton").style.display="none";
   	$("divWaitForParsing").style.display="block";
   	
   	frm.submit();
   }
   
   function validate(mappings){
       errors = '';
   
       <%
      CustomFieldManager customFieldManager = new CustomFieldManager();
      ArrayList customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
      %>
       var mapArr = mappings.split(",");
       var isReqArr = new Array();
       var isApplicantCustomArr = new Array();
       
       var isNameSelected = false;
       var isEmail1Selected = false;
       var isEmail2Selected = false;
       var isPhone1Selected = false;
       var isPhone2Selected = false;
       var isMobileSelected = false;
       var isDateOfBirthSelected = false;
       var isExpSelected = false;
       var isSourceSelected = false;
       var isCurrentLocationSelected = false;
       var isSkillsSelected = false;
       var isYearOfPassing1Selected = false;
       var isStartDateOfPassing1Selected = false;
       var isEndDatePassing1Selected = false;
       var isInst1Selected = false;
       var isDegree1Selected = false;
       var isBranch1Selected = false;
       var isUniversity1Selected = false;
       var isTypeOfProgram1Selected = false;
       var isPercentage1Selected = false;
       var isYearOfPassing2Selected = false;
       var isStartDateOfPassing2Selected = false;
       var isEndDatePassing2Selected = false;
       var isInst2Selected = false;
       var isDegree2Selected = false;
       var isBranch2Selected = false;
       var isUniversity2Selected = false;
       var isTypeOfProgram2Selected = false;
       var isPercentage2Selected = false;
       
       var isYearOfPassing3Selected = false;
       var isStartDateOfPassing3Selected = false;
       var isEndDatePassing3Selected = false;
       var isInst3Selected = false;
       var isDegree3Selected = false;
       var isBranch3Selected = false;
       var isUniversity3Selected = false;
       var isTypeOfProgram3Selected = false;
       var isPercentage3Selected = false;
       
       var isYearOfPassing4Selected = false;
       var isStartDateOfPassing4Selected = false;
       var isEndDatePassing4Selected = false;
       var isInst4Selected = false;
       var isDegree4Selected = false;
       var isBranch4Selected = false;
       var isUniversity4Selected = false;
       var isTypeOfProgram4Selected = false;
       var isPercentage4Selected = false;
       
       var isCurrentEmployerSelected = false;
       var isCurrentCTCSelected = false;
       var isExpectedCTCSelected = false;
       var isNoticePeriodSelected = false;
       var isNoteSelected = false;
       var isOriginalPathSelected = false;
       var isEmploymentFromDate1Selected = false;
       var isEmploymentToDate1Selected = false;
       var isEmploymentEmployer1Selected = false;
       var isEmploymentDesignation1Selected = false;
       var isEmploymentType1Selected = false;
       var isEmploymentLocation1Selected = false;
       var isEmploymentCountry1Selected = false;
       var isEmploymentFromDate2Selected = false;
       var isEmploymentToDate2Selected = false;
       var isEmploymentEmployer2Selected = false;
       var isEmploymentDesignation2Selected = false;
       var isEmploymentType2Selected = false;
       var isEmploymentLocation2Selected = false;
       var isEmploymentCountry2Selected = false;
       var isEmploymentFromDate3Selected = false;
       var isEmploymentToDate3Selected = false;
       var isEmploymentEmployer3Selected = false;
       var isEmploymentDesignation3Selected = false;
       var isEmploymentFromDate4Selected = false;
       var isEmploymentToDate4Selected = false;
       var isEmploymentEmployer4Selected = false;
       var isEmploymentDesignation4Selected = false;
       var isEmploymentFromDate5Selected = false;
       var isEmploymentToDate5Selected = false;
       var isEmploymentEmployer5Selected = false;
       var isEmploymentDesignation5Selected = false;
       
       for(var i=0; i<mapArr.length;i++){
           if(mapArr[i]=="<%=InboxConstants.FLD_NAME%>"){
   			isNameSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMAIL_1%>"){
   			isEmail1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMAIL_2%>"){
   			isEmail2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_PHONE_1%>"){
   			isPhone1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_PHONE_2%>"){
   			isPhone2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_MOBILE%>"){
   			isMobileSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_DATE_OF_BIRTH%>"){
   			isDateOfBirthSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EXPERIENCE%>"){
   			isExpSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_SOURCE%>"){
   			isSourceSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_CURRENT_LOCATION%>"){
   			isCurrentLocationSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_SKILLS%>"){
   			isSkillsSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_YEAR_OF_PASSING_1%>"){
   			isYearOfPassing1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_START_DATE_OF_PASSING_1%>"){
   			isStartDateOfPassing1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_END_DATE_OF_PASSING_1%>"){
   			isEndDatePassing1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_INSTITUTE_1%>"){
   			isInst1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_DEGREE_1%>"){
   			isDegree1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_BRANCH_1%>"){
   			isBranch1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_1%>"){
   			isUniversity1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_1%>"){
   			isTypeOfProgram1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_1%>"){
   			isPercentage1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_YEAR_OF_PASSING_2%>"){
   			isYearOfPassing2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_START_DATE_OF_PASSING_2%>"){
   			isStartDateOfPassing2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_END_DATE_OF_PASSING_2%>"){
   			isEndDatePassing2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_INSTITUTE_2%>"){
   			isInst2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_DEGREE_2%>"){
   			isDegree2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_BRANCH_2%>"){
   			isBranch2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_2%>"){
   			isUniversity2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_2%>"){
   			isTypeOfProgram2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_2%>"){
   			isPercentage2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_YEAR_OF_PASSING_3%>"){
   			isYearOfPassing3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_START_DATE_OF_PASSING_3%>"){
   			isStartDateOfPassing3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_END_DATE_OF_PASSING_3%>"){
   			isEndDatePassing3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_INSTITUTE_3%>"){
   			isInst3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_DEGREE_3%>"){
   			isDegree3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_BRANCH_3%>"){
   			isBranch3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_3%>"){
   			isUniversity3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_3%>"){
   			isTypeOfProgram3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_3%>"){
   			isPercentage3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_YEAR_OF_PASSING_4%>"){
   			isYearOfPassing4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_START_DATE_OF_PASSING_4%>"){
   			isStartDateOfPassing4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_END_DATE_OF_PASSING_4%>"){
   			isEndDatePassing4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_INSTITUTE_4%>"){
   			isInst4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_DEGREE_4%>"){
   			isDegree4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_BRANCH_4%>"){
   			isBranch4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_4%>"){
   			isUniversity4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_4%>"){
   			isTypeOfProgram4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_4%>"){
   			isPercentage4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_CURRENT_EMPLOYER%>"){
   			isCurrentEmployerSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_CURRENT_CTC%>"){
   			isCurrentCTCSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EXPECTED_CTC%>"){
   			isExpectedCTCSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_NOTICE_PERIOD%>"){
   			isNoticePeriodSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_NOTE%>"){
   			isNoteSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_ORIGINAL_RESUME_PATH%>"){
   			isOriginalPathSelected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_1%>"){
   			isEmploymentFromDate1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_1%>"){
   			isEmploymentToDate1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_1%>"){
   			isEmploymentEmployer1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_1%>"){
   			isEmploymentDesignation1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_TYPE_1%>"){
   			isEmploymentType1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_LOCATION_1%>"){
   			isEmploymentLocation1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_COUNTRY_1%>"){
   			isEmploymentCountry1Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_2%>"){
   			isEmploymentFromDate2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_2%>"){
   			isEmploymentToDate2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_2%>"){
   			isEmploymentEmployer2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_2%>"){
   			isEmploymentDesignation2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_TYPE_2%>"){
   			isEmploymentType2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_LOCATION_2%>"){
   			isEmploymentLocation2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_COUNTRY_2%>"){
   			isEmploymentCountry2Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_3%>"){
   			isEmploymentFromDate3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_3%>"){
   			isEmploymentToDate3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_3%>"){
   			isEmploymentEmployer3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_3%>"){
   			isEmploymentDesignation3Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_4%>"){
   			isEmploymentFromDate4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_4%>"){
   			isEmploymentToDate4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_4%>"){
   			isEmploymentEmployer4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_4%>"){
   			isEmploymentDesignation4Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_5%>"){
   			isEmploymentFromDate5Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_5%>"){
   			isEmploymentToDate5Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_5%>"){
   			isEmploymentEmployer5Selected = true;
   		}else if(mapArr[i]=="<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_5%>"){
   			isEmploymentDesignation5Selected = true;
   		}
       	
       }//end of for
   
   	if(!isNameSelected){
   		errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.name" />');
   	}
   
   	<%
      ArrayList importFields = ImportConfigurationManager.getImportFields(); 
         for(int j=0; j<importFields.size();j++){
      	ImportFieldData fieldData = (ImportFieldData)importFields.get(j);
      	String fieldId = fieldData.getFieldId();
      	String fieldType = fieldData.getFieldType();
      	String showValue = fieldData.getFieldImportShow();
      	String isMandatory = fieldData.getFieldImportMandatory();
      	
      	
      	if(fieldType.equalsIgnoreCase(ImportConfigurationConstants.FIELD_TYPE_NORMAL)){
      		if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
      			if(fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL1) ){
      	%>
   					if(!isEmail1Selected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.email1" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_EMAIL2)){
      %>
   					if(!isEmail2Selected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.email2" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_PHONE1)){
      %>
   					if(!isPhone1Selected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.phone1" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_PHONE2)){
      %>
   					if(!isPhone2Selected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.phone2" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_MOBILE)){
      %>
   					if(!isMobileSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.mobile" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_SOURCE)){
      %>
   					if(!isSourceSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.source" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_LOCATION)){
      %>
   					if(!isCurrentLocationSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.current_location" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_SKILLS)){
      %>
   					if(!isSkillsSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.skills" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_EDUCATION)){
      %>
   		if(!isMobileSelected && !isYearOfPassing1Selected && !isStartDateOfPassing1Selected && !isEndDatePassing1Selected
   							&& !isInst1Selected && !isDegree1Selected && !isBranch1Selected && ! isUniversity1Selected
   							&& !isTypeOfProgram1Selected && !isPercentage1Selected
   							&& !isYearOfPassing2Selected && !isStartDateOfPassing2Selected && !isEndDatePassing2Selected
   							&& !isInst2Selected && !isDegree2Selected && !isBranch2Selected && ! isUniversity2Selected
   							&& !isTypeOfProgram2Selected && !isPercentage2Selected
   							){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.education" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPERIENCE)){
      %>
   					if(!isExpSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.working_since" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER)){
      %>
   					if(!isCurrentEmployerSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.current_employer" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_CURRENT_CTC)){
      %>
   					if(!isCurrentCTCSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.current_ctc" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_EXPECTED_CTC)){
      %>
   					if(!isExpectedCTCSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.expected_ctc" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTICE_PERIOD)){
      %>
   					if(!isNoticePeriodSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.time_to_join" />');
   					}
   		<%	
      }else if(fieldId.equals(ImportConfigurationConstants.FIELD_NOTE)){
      %>
   					if(!isNoteSelected){
   						errors = addError(errors, '- <bean:message key="common.applicant" /> <bean:message key="common.note" />');
   					}
   		<%	
      }			
      }//end of if manadatory
      }else{
      if(customFields!=null && customFields.size()>0){ 
      for(int i=0; i<customFields.size();i++){
      	CustomFieldData data = (CustomFieldData)customFields.get(i);
      	String customFieldName = data.getFieldName();
      	if(fieldId.equals(customFieldName)){
      		if(isMandatory.equalsIgnoreCase(ImportConfigurationConstants.FIELD_MANDATORY)){
      %>
   							isReqArr['<%=customFieldName%>'] = <%=isMandatory%>
   							for(var i=0; i<mapArr.length;i++){
   								if(mapArr[i]=='<%=customFieldName%>'){
   				        			isApplicantCustomArr['<%=customFieldName%>'] = 1;
   				    			}
   							}
   		<% 		
      }
      }
      } 
      }	
      }
      }
      %>
   
   	<%
      customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
      	if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
      	for (int c = 0; c < customFields.size(); c++) {
      		CustomFieldData cData = (CustomFieldData) customFields.get(c);
      		String name = cData.getFieldName();
      %>
   			if(isReqArr['<%=name%>']==1 && (isApplicantCustomArr['<%=name%>']==undefined || isApplicantCustomArr['<%=name%>']!=1 )){
   				errors = addError(errors, '- '+'<%=cData.getFieldDisplayName()%>');
   			}
   	<%
      }
      }
      %>   
      <%
      customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
      	if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
      	for (int c = 0; c < customFields.size(); c++) {
      		CustomFieldData cData = (CustomFieldData) customFields.get(c);
      		String name = cData.getFieldName();
      %>
   			if(isReqArr['<%=name%>']==1 && (isApplicantCustomArr['<%=name%>']==undefined || isApplicantCustomArr['<%=name%>']!=1 )){
   				errors = addError(errors, '- '+'<%=cData.getFieldDisplayName()%>');
   			}
   	<%
      }
      }
      %> 
   	 
   
   	if (errors.length > 0) {
   		errors = addError('Please select mapping for following fields.', errors);
   	}
   	return errors;
   }
   
   function cancelImport(url){
   	window.location.href=url;
   }
   
   function generateMap(){
   
   	var text = "Must be text";
   	var text_path = "Must be text and also the path of the file is of server. Thus only resume kept at server are uploaded this way.";
   	var number = "Must be number";
   	var date = "Must be a dd/MM/yyyy format string";
   	var digit = "Must be a 4 digit number";
   	
   	fldTypeMap.<%=InboxConstants.FLD_NAME%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMAIL_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMAIL_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_PHONE_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_PHONE_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_MOBILE%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_DATE_OF_BIRTH%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EXPERIENCE%> = number;
   	fldTypeMap.<%=InboxConstants.FLD_SOURCE%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_CURRENT_LOCATION%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_SKILLS%> = text;
   	
   	
   	fldTypeMap.<%=InboxConstants.FLD_YEAR_OF_PASSING_1%> = digit;
   	fldTypeMap.<%=InboxConstants.FLD_START_DATE_OF_PASSING_1%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_END_DATE_OF_PASSING_1%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_INSTITUTE_1%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_DEGREE_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_BRANCH_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_1%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_1%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_1%> =text;
   	
   	
   	fldTypeMap.<%=InboxConstants.FLD_YEAR_OF_PASSING_2%> = digit;
   	fldTypeMap.<%=InboxConstants.FLD_START_DATE_OF_PASSING_2%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_END_DATE_OF_PASSING_2%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_INSTITUTE_2%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_DEGREE_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_BRANCH_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_2%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_2%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_2%> =text;
   	
   	fldTypeMap.<%=InboxConstants.FLD_YEAR_OF_PASSING_3%> = digit;
   	fldTypeMap.<%=InboxConstants.FLD_START_DATE_OF_PASSING_3%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_END_DATE_OF_PASSING_3%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_INSTITUTE_3%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_DEGREE_3%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_BRANCH_3%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_3%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_3%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_3%> =text;
   	
   	fldTypeMap.<%=InboxConstants.FLD_YEAR_OF_PASSING_4%> = digit;
   	fldTypeMap.<%=InboxConstants.FLD_START_DATE_OF_PASSING_4%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_END_DATE_OF_PASSING_4%> =date;
   	fldTypeMap.<%=InboxConstants.FLD_INSTITUTE_4%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_DEGREE_4%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_BRANCH_4%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_UNIVERSITY_OF_PASSING_4%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_4%> =text;
   	fldTypeMap.<%=InboxConstants.FLD_PERCENTAGE_OF_PASSING_4%> =text;
   	
   	fldTypeMap.<%=InboxConstants.FLD_CURRENT_EMPLOYER%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_CURRENT_CTC%> = number;
   	fldTypeMap.<%=InboxConstants.FLD_EXPECTED_CTC%> = number;
   	fldTypeMap.<%=InboxConstants.FLD_NOTICE_PERIOD%> = text;	
   	fldTypeMap.<%=InboxConstants.FLD_NOTE%> = text;	
   	fldTypeMap.<%=InboxConstants.FLD_ORIGINAL_RESUME_PATH%> = text_path;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_1%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_1%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_TYPE_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_BUSINESS_TYPE_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_LOCATION_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_COUNTRY_1%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_2%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_2%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_TYPE_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_BUSINESS_TYPE_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_LOCATION_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_COUNTRY_2%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_3%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_3%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_3%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_3%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_4%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_4%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_4%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_4%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_FROM_DATE_5%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_TO_DATE_5%> = date;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_EMPLOYER_5%> = text;
   	fldTypeMap.<%=InboxConstants.FLD_EMPLOYMENT_DESIGNATION_5%> = text;
   	
   	<%
      if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT) 
    		  || CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
      	for (int c = 0; c < customFields.size(); c++) {
      		CustomFieldData cData = (CustomFieldData) customFields.get(c);
      		String type = cData.getFieldType();
      		if(type.equals(CustomFieldConstants.TYPE_NUMBER)){
      		%>
   				fldTypeMap.<%=cData.getFieldName()%> = number;
   			<%
      }else if(type.equals(CustomFieldConstants.TYPE_DATE)){
      %>
   				fldTypeMap.<%=cData.getFieldName()%> = date;
   			<%
      }else{
      %>
   				fldTypeMap.<%=cData.getFieldName()%> = text;
   			<%
      }		
      }
      }
      %>
   	
   }
   
   function onWindowLoad(){	
   	generateMap();
   }
   window.onload=onWindowLoad;
   
</script>