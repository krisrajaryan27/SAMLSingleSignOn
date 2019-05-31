<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page isELIgnored="false" %>
<%@page import="com.talentPool.positions.constants.PositionConfigurationConstants"%>
<%@page import="com.talentPool.user.manager.ModuleSet"%>
<%@page import="com.talentPool.budget.utils.BudgetUtils"%><link rel="stylesheet" type="text/css" href="themes/default/print.css"/>
<head>
<style>	
.pLables
{
   font-style:italic;
}	
.pNormal
{
   font-style:normal;
}	
</style>
</head>
<body onload="javascript:window.print();">
<bean:define id="posData" name="posData" scope="request" toScope="page" />    
<logic:present name="posFieldData" scope="request">
<table width="100%" class="" border="0" >
		<tr>
			<td align="center" >
				<bean:message key="positions_home.print.header"/>	
			</td>
		</tr>
		<tr>
			<td align="center">
				<H4><bean:write name="posData" property="positionTitle"/></H4>	
			</td>
		</tr>
</table>
<table width="100%" class="printTable" cellspacing="2" cellpadding="5" style="padding-left:20px;">
	<logic:iterate id="posFieldData"  name="posFieldData" scope="request" type="com.talentPool.positions.dataobject.PositionFieldData">
		<tr>
			<bean:define id="fieldId" name="posFieldData" property="fieldId" toScope="page" />
			<bean:define id="fieldType" name="posFieldData" property="fieldType" toScope="page" />
			<logic:empty name="posFieldData" property="fieldTitle" >
				<bean:define id="fieldTitle" name="posFieldData" property="fieldId" toScope="page" />
			</logic:empty>
			<logic:notEmpty name="posFieldData" property="fieldTitle" >
				<bean:define id="fieldTitle" name="posFieldData" property="fieldTitle" toScope="page" />
			</logic:notEmpty>
			<bean:define id="fieldOnPositionPrintShow" name="posFieldData" property="fieldOnPositionPrintShow" toScope="page" />
			<logic:equal name="fieldOnPositionPrintShow" value="<%=PositionConfigurationConstants.FIELD_SHOW%>">
				<logic:equal name="fieldType" value="<%=PositionConfigurationConstants.FIELD_TYPE_NORMAL%>">
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_NAME %>">
						<td valign="top" class="pLables">
								<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="positionTitle"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_CODE %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="positionReferenceCode"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_CREATED_ON %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="positionCreationDateToDisplay"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_POSITION_OWNER %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="positionOwnerName"/>	
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_LOCATION %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="locationName"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_1 %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="departmentName"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_2 %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="subDeptName"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_3 %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="groupName"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_4 %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="sub3DeptName"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_DEPARTMENT_LEVEL_5 %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="sub4DeptName"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_VACANCIES %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="noOfPositions"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_HIRE_BY_DATE %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="positionExpiryDateToDisplay"/>						
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_LEVEL %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="positionLevel"/>			
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_REFERAL_FEES %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>					
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="referalFees"/>		
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_GRADE %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>					
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="gradeName"/>		
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_BAND %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>					
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="bandName"/>		
						</td>
					</logic:equal>
					<% if (ModuleSet.isMODULE_BUDGET() && BudgetUtils.isBudgetModuleActive()) { %>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_BUDGET_ITEM %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>					
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="budgetItemName"/>		
						</td>
					</logic:equal>
					<%} %>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_RESPONSIBILITIES %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							${posData.responsibilities}
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_REQUIREMENTS %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							${posData.requirements}
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_PRIMARY_SKILLS %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="primarySkillsAsString"/>	
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_SECONDARY_SKILLS %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="secondarySkillsAsString"/>	
						</td>
					</logic:equal>
						<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_EDUCATION %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="education"/>	
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_BRANCH %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
						<logic:notEmpty name="posData" property="branchName" >
							<bean:write name="posData" property="branchName"/>
						</logic:notEmpty>
						<logic:empty name="posData" property="branchName" >
							Any
						</logic:empty>	
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_EXPERIENCE %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="experience"/>	
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_NOTE %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="positionNote"/>	
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_REQUESTEDBY %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="requestedBy"/>	
						</td>
					</logic:equal>
					<logic:equal name="fieldId" value="<%=PositionConfigurationConstants.FIELD_APPROVEDBY %>">
						<td valign="top" class="pLables">
							<bean:write name="fieldTitle"/>						
						</td>
						<td valign="top" class="pNormal">
							<bean:write name="posData" property="approvedBy"/>	
						</td>
					</logic:equal>
				</logic:equal>
				<logic:equal name="fieldType" value="<%=PositionConfigurationConstants.FIELD_TYPE_CUSTOM%>">
					<logic:notEmpty name="customFields" scope="request">
						<logic:iterate id="customFieldsData" name="customFields" type="com.talentPool.custom.dataobject.CustomFieldData">
							<logic:equal name="customFieldsData" property="fieldName"  value="${pageScope.fieldId}">
								<td valign="top" class="pLables">
									<bean:write name="fieldTitle" />
								</td>
								<td valign="top" class="pNormal">
									<bean:write name="customFieldsData" property="displayValue"/>	
								</td>
							</logic:equal>
						</logic:iterate>					
					</logic:notEmpty>
				</logic:equal>
			</logic:equal>	
		</tr>
	</logic:iterate>
	</table>
</logic:present>
</body>