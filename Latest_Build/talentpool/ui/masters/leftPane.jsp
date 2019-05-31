<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="com.talentPool.masters.constants.MastersConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.budget.utils.BudgetUtils"%>
<div style="margin-left:18px; margin-right:18px;"> 
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
      <td height="20"><strong class="Grey">Masters</strong></td> 
    </tr>
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BRANCH_MASTER">
    <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
		<logic:equal value="<%=MastersConstants.MASTER_TYPE_BRANCH%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_branches_master"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_BRANCH%>" name="masterType" scope="request">
			<a href="#" class="green" onclick="gotoMaster('masters.do?mode=manageBranchesMaster');"><bean:message key="admin_master_label_branches_master"/></a>
		</logic:notEqual>            	    
    	</td>
	</tr>
	</logic:equal>
    <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
		<logic:equal value="<%=MastersConstants.MASTER_TYPE_DEGREE_ALIASES%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_degree_master"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_DEGREE_ALIASES%>" name="masterType" scope="request">
			 <a href="#" class="green" onclick="gotoMaster('masters.do?mode=manageDegreeAliasesMaster');"><bean:message key="admin_master_label_degree_master"/></a>
		</logic:notEqual>            	    
    	</td>
	</tr>
    <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
		<logic:equal value="<%=MastersConstants.MASTER_TYPE_INSTITUTES%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_institute_master"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_INSTITUTES%>" name="masterType" scope="request">
			<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageInstitutesMaster');"><bean:message key="admin_master_label_institute_master"/></a>
		</logic:notEqual>
    	</td>
	</tr>
	<tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
		<logic:equal value="<%=MastersConstants.MASTER_TYPE_EMPLOYER%>" name="masterType" scope="request">
			Employer Master
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_EMPLOYER%>" name="masterType" scope="request">
			<a href="EmployerMaster.action" class="green">Employer Master</a>
		</logic:notEqual>
    	</td>
	</tr>
	
	<tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_DESIGNATION%>" name="masterType" scope="request">
				Designation Master
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_DESIGNATION%>" name="masterType" scope="request">
				<a href="DesignationMaster.action" class="green" >Designation Master</a>
			</logic:notEqual>    	
    	</td>
	</tr>
	
	
</table>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
<div style="margin-left:18px; margin-right:18px;"> 
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_LOCATIONS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_locations_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_LOCATIONS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('location.do?mode=manageLocationsMaster');"><bean:message key="admin_master_label_locations_master"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	
	
    <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
		<logic:equal value="<%=MastersConstants.MASTER_TYPE_DEPARTMENT%>" name="masterType" scope="request">
			<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %> <bean:message key="admin_master_label_master"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_DEPARTMENT%>" name="masterType" scope="request">
			<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageDepartmentMaster');"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1) %> <bean:message key="admin_master_label_master"/></a>
		</logic:notEqual>            	    
    	
    	</td>
	</tr>		
	 <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_GRADE%>" name="masterType" scope="request">
				<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %> <bean:message key="admin_master_label_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_GRADE%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageBudgetGradeMaster');"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL) %> <bean:message key="admin_master_label_master"/></a>
			</logic:notEqual>   	
    	</td>
	</tr>
	 <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_BAND%>" name="masterType" scope="request">
				<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL) %> <bean:message key="admin_master_label_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_BAND%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageBudgetBandMaster');"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL) %> <bean:message key="admin_master_label_master"/></a>
			</logic:notEqual>    	
    	</td>
	</tr>
	<%if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_PROPERTY))){%>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_BUSINESS_UNIT_MASTER">
	 <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_BUSINESS_UNIT%>" name="masterType" scope="request">
				<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL)%>&nbsp;<bean:message key="admin_master_label_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_BUSINESS_UNIT%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageBusinessUnitMaster');"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL)%>&nbsp;<bean:message key="admin_master_label_master"/></a>
			</logic:notEqual>   	
    	</td>
	</tr>
	</logic:equal>
	<%}if("1".equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_PROPERTY))){%>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_COST_CENTER_MASTER">
	 <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_COST_CENTER%>" name="masterType" scope="request">
				<%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_LABEL)%>&nbsp;<bean:message key="admin_master_label_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_COST_CENTER%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageCostCenterMaster');"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_LABEL)%>&nbsp;<bean:message key="admin_master_label_master"/></a>
			</logic:notEqual>    	
    	</td>
	</tr>
	</logic:equal>
	<% } %>
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SKILL_MASTER">
    <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
		<logic:equal value="<%=MastersConstants.MASTER_TYPE_SKILLS_CATEGORY%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_skills_master"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SKILLS_CATEGORY%>" name="masterType" scope="request">
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_SKILLS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_skills_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SKILLS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageSkillCategoriesMaster');"><bean:message key="admin_master_label_skills_master"/></a>
			</logic:notEqual>            	    
		</logic:notEqual>            	    
    	
    	</td>
	</tr>
	</logic:equal>
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SOURCE_MASTER">
    <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
		<logic:equal value="<%=MastersConstants.MASTER_TYPE_SOURCE_TYPE%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_source_sourcetype_master"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SOURCE_TYPE%>" name="masterType" scope="request">
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_SOURCE%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_source_sourcetype_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SOURCE%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageSourceTypesMaster');"><bean:message key="admin_master_label_source_sourcetype_master"/></a>
			</logic:notEqual>            	    
		</logic:notEqual>   
    	</td>
	</tr>
	</logic:equal>
	
	<tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_RESUME_TYPE%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_resume_type"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_RESUME_TYPE%>" name="masterType" scope="request">
				<a href="ResumeTypeMaster.action" class="green" ><bean:message key="admin_master_label_resume_type"/></a>
			</logic:notEqual>    	
    	</td>
	</tr>
	 <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_STAGES%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_stages"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_STAGES%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('step.do?mode=manageStageMaster');"><bean:message key="admin_master_label_stages"/></a>
			</logic:notEqual>    	
    	</td>
	</tr>
	 <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_STEPS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_steps"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_STEPS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('step.do?mode=managePositionStepMaster');"><bean:message key="admin_master_label_steps"/></a>
			</logic:notEqual>    	
    	</td>
	</tr>
	<%-- <tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_STEPS_MIGRATION%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_process_steps_migration"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_STEPS_MIGRATION%>" name="masterType" scope="request">
				<a href="stepsMigrationStatus.action" class="green" ><bean:message key="admin_master_label_process_steps_migration"/></a>
			</logic:notEqual>    	
    	</td>
	</tr> --%>
	<tr>
    	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_SECURITY_QUESTIONS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_security_questions"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SECURITY_QUESTIONS%>" name="masterType" scope="request">
				<a href="SecurityQuestionMaster.action" class="green" ><bean:message key="admin_master_label_security_questions"/></a>
			</logic:notEqual>    	
    	</td>
	</tr>
</table>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
<div style="margin-left:18px; margin-right:18px;"> 
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_TEMPLATE_MASTER">
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_TEMPLATE%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_template_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_TEMPLATE%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageTemplateMaster');"><bean:message key="admin_master_label_template_master"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_REPORTS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_report_templates"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_REPORTS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('reportTemplates.do?mode=manageReportTemplates');"><bean:message key="admin_master_label_report_templates"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	</logic:equal>
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_FLAG_MASTER">
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_FLAG%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_flag_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_FLAG%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageFlagMaster');"><bean:message key="admin_master_label_flag_master"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	</logic:equal>	
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_INBOX_FOLDERS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_inbox_folders_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_INBOX_FOLDERS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageInboxFoldersMaster');"><bean:message key="admin_master_label_inbox_folders_master"/></a>
			</logic:notEqual>
  	</td>
	</tr>
</table>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
<div style="margin-left:18px; margin-right:18px;"> 
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_REQUISITION_APPROVAL_STEPS_SETTINGS">
	<tr>
	  <td style="line-height:18px;"><span class="greenBullet">&raquo;</span>&nbsp;
	  	<logic:equal value="<%=MastersConstants.MASTER_REQUISITION_TEMPLATES%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_requisition_approval_templates"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_REQUISITION_TEMPLATES%>" name="masterType" scope="request">
			<a href="#" class="green"  onclick="gotoMaster('requisition.do?mode=manageRequisitionApprovalTemplates');"><bean:message key="admin_master_label_requisition_approval_templates"/></a>
		</logic:notEqual>
	  </td> 
	</tr>
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_OFFER_SHEET_MASTER">
	<tr>
	  <td style="line-height:18px;"><span class="greenBullet">&raquo;</span>&nbsp;
	  	<logic:equal value="<%=MastersConstants.MASTER_TYPE_SALARY_SHEET_DESIGNER%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_offersheet_templates"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SALARY_SHEET_DESIGNER%>" name="masterType" scope="request">
			<a href="#" class="green"  onclick="gotoMaster('offerSheet.do?mode=manageOfferSheetTemplates');"><bean:message key="admin_master_label_offersheet_templates"/></a>
		</logic:notEqual>
	  </td> 
	</tr>
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_SALARY_CALCULATION_MASTER">
	<tr>
		<td style="line-height:18px;"><span class="greenBullet">&raquo;</span>&nbsp;
	  	<logic:equal value="<%=MastersConstants.MASTER_TYPE_SALARY_FORMULA_DESIGNER%>" name="masterType" scope="request">
			<bean:message key="admin_master_label_salary_formula_templates"/>
		</logic:equal>            	    
		<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SALARY_FORMULA_DESIGNER%>" name="masterType" scope="request">
			<a href="#" class="green"  onclick="gotoMaster('salaryStructure.do?mode=manageSalaryFormulaTemplates');"><bean:message key="admin_master_label_salary_formula_templates"/></a>
		</logic:notEqual>
	  </td>
	</tr>
	</logic:equal>
	<tr>
		<td style="line-height:18px;"><span class="greenBullet">&raquo;</span>&nbsp;
		  	<logic:equal value="<%=MastersConstants.MASTER_TYPE_SALARY_COMPONENT_CATEGORY%>" name="masterType" scope="request">
				<bean:message key="admin_master.label.salary_components_category_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_SALARY_COMPONENT_CATEGORY%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('salaryComponentCategoryMaster.action');"><bean:message key="admin_master.label.salary_components_category_master"/></a>
			</logic:notEqual>
	    </td>
	</tr>
</table>
</div>
<div style="border-bottom: 1px solid rgb(204, 204, 204);">&nbsp;</div>
<div style="margin-left:18px; margin-right:18px;"> 
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_FEEDBACK_FORM_MASTER">
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_FEEDBACK_FORMS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_feedback_forms"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_FEEDBACK_FORMS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('feedbackform.do?mode=feedbackFormHome');"><bean:message key="admin_master_label_feedback_forms"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_FEEDBACK_FIELDS_MASTER">
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_FEEDBACK_FIELDS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_feedback_fields"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_FEEDBACK_FIELDS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageFeedbackFieldsCategoriesMaster');"><bean:message key="admin_master_label_feedback_fields"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	</logic:equal>
	<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_MULTIPLE_SELECTS_MASTER">
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_MULTIPLE_SELECT%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_multiple_select_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_MULTIPLE_SELECT%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageMultipleSelectMaster');"><bean:message key="admin_master_label_multiple_select_master"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	</logic:equal>	
    <logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_RATINGS_MASTER">
	<tr>
   	<td height="18"><span class="greenBullet">&raquo;</span>&nbsp;
			<logic:equal value="<%=MastersConstants.MASTER_TYPE_RATINGS%>" name="masterType" scope="request">
				<bean:message key="admin_master_label_rating_master"/>
			</logic:equal>            	    
			<logic:notEqual value="<%=MastersConstants.MASTER_TYPE_RATINGS%>" name="masterType" scope="request">
				<a href="#" class="green"  onclick="gotoMaster('masters.do?mode=manageRatingMaster');"><bean:message key="admin_master_label_rating_master"/></a>
			</logic:notEqual>
  	</td>
	</tr>
	</logic:equal>	
</table>
</div>
<br>
<script language="javascript">
function gotoMaster(url){
	window.location.href=url;
}

</script>