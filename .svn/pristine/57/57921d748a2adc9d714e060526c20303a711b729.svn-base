<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
								com.talentPool.positions.form.PositionForm,
								com.talentPool.positions.PositionConstants,
								com.talentPool.common.db.SimpleDataObject,
								com.talentPool.positions.manager.PositionScreenConfigurationManager,
								com.talentPool.positions.constants.PositionConfigurationConstants,
								com.talentPool.positions.dataobject.PositionFieldData,
								com.talentPool.common.properties.TPApplicationProperties" %>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.Utils"%><link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<script src="js/dhtmlxGrid/dhtmlXCommon.js"></script> 
<script src="js/dhtmlxGrid/dhtmlXGrid.js"></script> 
<script src="js/dhtmlxGrid/dhtmlxgrid_filter.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script  src="js/dhtmlxGrid/dhtmlXGrid_excell_link.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_drag.js"></script>
<script language="JavaScript" src="js/calender/CalendarPopup.js"></script>						
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>								
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/doClasses/IdValueClass.js" type="text/javascript"></script>
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script language="JavaScript" src="js/tpSelectListFunctions.js"></script>	
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_pgn.js"></script>
<script src="js/dhtmlxGrid/dhtmlXGrid_srnd.js"></script>


<style type="text/css">
.selectListSkills {
color:#000000;
background-color:#f2f2f2;
font-size:12px;
width:130px;
border-color:#000000;
}
</style>			
<% 
ArrayList positionFields = PositionScreenConfigurationManager.getPositionRequirementsFields();
%>		
<script>
var checkBoxDegree;
var checkBoxBranch;
</script>						
<script>
tinyMCE.init({
	mode : "exact",
	elements : "requirements",
	theme : "advanced",
	theme_advanced_toolbar_location : "top",
	theme_advanced_toolbar_align : "left",
	theme_advanced_buttons1 : "newdocument,bold,italic,underline,forecolor,backcolor,bullist,numlist,separator,undo,redo,cut,copy,paste,justifyleft,justifyright,formatselect,fontselect,fontsizeselect",
	theme_advanced_buttons2 : "",
	theme_advanced_buttons3 : "",
	force_br_newlines: true,
	theme_advanced_disable : "anchor",
	theme_advanced_path : false
});
var chkboxchked = "images/checkboxchecked.gif";
var chkboxunchked = "images/checkboxunchecked.gif";
</script>
<div class="contentDiv">			
	<%
		if(request.getAttribute(Globals.ERROR_KEY)!=null){
	%>
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
	<%@ include file="positionTabs.jsp"%>
	<div class="outerDiv" 
	<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
	style="border-top:0px;"
	</logic:equal>
	<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
	style="border-top:0px;"
	</logic:notEqual>
	>
		<html:form action="/position">
			<html:hidden property="mode" name="positionForm"/>
			<html:hidden property="dir" name="positionForm"/>
			<html:hidden property="step" name="positionForm"/>
			<html:hidden property="dest" name="positionForm"/>
			<html:hidden property="positionId" name="positionForm"/>			
			<html:hidden property="showCondition" name="positionForm"/>
			<html:hidden property="primarySkills" name="positionForm"/>
			<html:hidden property="secondarySkills" name="positionForm"/>			
			<html:hidden property="finishCopyPosition" name="positionForm"/>
			<html:hidden property="positionStatus" name="positionForm"/>	
			<html:hidden property="sendPositionChangeNotification" name="positionForm"/>			
			<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top" style="border-right: 1px solid #cccccc;">
							<div class="contentDiv">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">
									<%
								for(int j=0; j<positionFields.size();j++){
									PositionFieldData fieldData = (PositionFieldData)positionFields.get(j); 
									String fieldId = fieldData.getFieldId();
									String fieldType = fieldData.getFieldType();
									String showValue = fieldData.getFieldPositionShow();
									if(showValue.equals(PositionConfigurationConstants.FIELD_SHOW)){						
										if(fieldType.equals(PositionConfigurationConstants.FIELD_TYPE_NORMAL)){														
											if(fieldId.equals(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)){
									%>
									<tr>
										<td class="label" width="180px;"><bean:message key="position.requirements.primary_skills" />
										&nbsp;<span title='<bean:message key="common.searchable" />' class="searchableStar" >*</span></td>
										<td style="word-wrap:break-word;"><bean:write name="positionForm" property="primarySkills" /></td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_SECONDARY_SKILLS)){
									%>
									<tr>
										<td class="label"><bean:message key="position.requirements.secondary_skills" />
										&nbsp;<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span></td>
										<td style="word-wrap:break-word;"><bean:write name="positionForm" property="secondarySkills" /></td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_EDUCATION)){
									%>
									<tr>
										<td class="label" nowrap="nowrap"><bean:message key="position.requirements.minimum_education" />
										&nbsp;<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span></td>
										<td><bean:write name="positionForm" property="degreeTitle" /></td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_BRANCH)){
									%>
									<tr>
										<td class="label"><bean:message key="position.requirements.branch" />
										&nbsp;<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span></td>
										<td><bean:write name="positionForm" property="branchName" /></td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_EXPERIENCE)){
									%>
									<tr>
										<td class="label"><bean:message key="position.requirements.experience" />
										&nbsp;<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span>
										</td>
										<td><bean:write name="positionForm" property="minimumExperience" />&nbsp;<bean:message key="position.requirements.experience.to"/>&nbsp;<bean:write name="positionForm" property="maximumExperience" />&nbsp;<bean:message key="position.requirements.experience.years"/></td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_REQUIREMENTS)){
									%>
									<tr>
										<td class="label" colspan="2"><bean:message key="position.requirements.job_requirements" />:</td>
									</tr>
									<tr>
										<td colspan="2">
											<div>
												<html:textarea styleId="requirements"  property="requirements" name="positionForm" cols="90" rows="15"></html:textarea>
											</div>
										</td>
									</tr>
									<%}}}} %>
								</table>
							</div>
						</td>
						</tr>
				</table>	
							
			</logic:equal>
			<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
				<html:hidden property="degreeId" name="positionForm"/>			
				<html:hidden property="branchId" name="positionForm"/>
				<html:hidden property="draftId" name="positionForm"/>
				<html:hidden property="draftName" name="positionForm"/>			
				<html:hidden property="copyFrom" name="positionForm"/>					
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top" style="border-right: 1px solid #cccccc;">
							<div class="contentDiv">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="posinput">								
								<% for(int j=0; j<positionFields.size();j++){
										PositionFieldData fieldData = (PositionFieldData)positionFields.get(j); 
										String fieldId = fieldData.getFieldId();
										String fieldType = fieldData.getFieldType();
										String showValue = fieldData.getFieldPositionShow();
										String isMandatory = fieldData.getFieldPositionMandatory();
										if(showValue.equals(PositionConfigurationConstants.FIELD_SHOW)){%>						
										<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
											<% isMandatory = PositionConfigurationConstants.FIELD_NOT_MANDATORY;%>
										</logic:equal>
										<%											
											if(fieldType.equals(PositionConfigurationConstants.FIELD_TYPE_NORMAL)){												  													 
										%>
										
									<tr>
										<td colspan="2">
											<table cellspacing="0" cellpadding="0" border="0" >
											<tr>
												<td>													
													<div id="divSkillAndCategory" style="display:none;">
													<table cellspacing="0" cellpadding="0" border="0" >
													<tr>	
														<td class="label" >
															<bean:message key="admin_master_skill_cat.label.skill_category" />	
														</td>
														<td class="label">
															<bean:message key="admin_master_skill_cat.label.skills" />															
														</td>
													</tr>													
													<tr>	
														<td>
															<div class="gridbox" id="SKILL_CATEGORY_GRID" style="width:190px; height: 210px; overflow: visible;"></div>
														</td>
														<td>
															<div class="gridbox" id="SKILLS_GRID" style="width:200px; height: 210px; overflow: visible;"></div>
														</td>
													</tr>
													</table>
													</div>
													<%
														if(fieldId.equals(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)){  													 
													%>
													<script>
														$("divSkillAndCategory").style.display="block";
													</script>													
													<% } %>	
												</td>
												<td>
													<div id="divPrimarySkills" style="display:none;">
													<table cellspacing="0" cellpadding="0" border="0" >
													<tr>
														<td></td>
														<td class="label">
															<bean:message key="position.requirements.primary_skills" />
															&nbsp;
															<%if(isMandatory.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MANDATORY)){ %>
															<span class="star">*</span>
															<%} %>
															<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span>
														</td>
													</tr>
													<tr>													
														<td style="padding: 10px;vertical-align: middle;" >					
															<a href="#" onclick="javascript: resetGridItem(skillGrid,primarySkillsGrid);return false;" title="<bean:message key='common.add' />" >
															<img src="images/ico_rightarrow.gif"  border="0" />
															</a>
															<br/>
															<a href="#" onclick="javascript: resetGridItem(primarySkillsGrid,skillGrid);return false;" title="<bean:message key='common.remove' />" >
															<img src="images/ico_leftarrow.gif"  border="0" />
															</a>
														</td>
														
														<td class="gridborder" style="padding: 0px;height: 100px;">
															<div id="PRIMARY_SKILL_GRID" class="gridbox" style="width:200px; height: 100px;"></div>
														</td>
													</tr>
													</table>
													</div>
													<%
														if(fieldId.equals(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)){  													 
													%>
													<script>
														$("divPrimarySkills").style.display="block";
													</script>													
													<% } %>													
													<div id="divSecondarySkills" style="display:none;">
													<table cellspacing="0" cellpadding="0" border="0" >
													<tr>
														<td></td>
														<td class="label">
															<bean:message key="position.requirements.secondary_skills" />
															&nbsp;
															<%if(isMandatory.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MANDATORY)){ %>
															<span class="star">*</span>
															<%} %>
															<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span>
														</td>
													</tr>
													<tr>
														<td style="padding: 10px;vertical-align: middle;" >					
															<a href="#" onclick="javascript: resetGridItem(skillGrid,secondarySkillsGrid);return false;" title="<bean:message key='common.add' />" >
															<img src="images/ico_rightarrow.gif"  border="0" />
															</a>
															<br/>
															<a href="#" onclick="javascript: resetGridItem(secondarySkillsGrid,skillGrid);return false;" title="<bean:message key='common.remove' />" >
															<img src="images/ico_leftarrow.gif"  border="0" />
															</a>
														</td>
														<td class="gridborder" style="padding: 0px;height: 100px;">
															<div id="SECONDARY_SKILL_GRID" class="gridbox" style="width:200px; height: 100px;"></div>
														</td>
													</tr>
													</table>
													</div>
													<% 
														if(fieldId.equals(PositionConfigurationConstants.FIELD_SECONDARY_SKILLS)){
													%>
													<script>
														$("divSecondarySkills").style.display="block";
													</script>
													
													<% } %>
												</td>
											</tr>
											</table>
										</td>
									</tr>								
									<%								
										if(fieldId.equals(PositionConfigurationConstants.FIELD_EDUCATION)){
									%>
									<tr>
										<td class="label" nowrap="nowrap" style="width: 215px;">
										<img src="images/checkboxunchecked.gif" id="IMG_selectAllDegrees" onclick="javascript: selectAllDegrees(this);">&nbsp;
										<bean:message key="position.requirements.minimum_education" />
										&nbsp;
											<%if(isMandatory.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MANDATORY)){ %>
										<span class="star">*</span>
										<%} %>
											<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span>
										</td>										
									</tr>
									<tr>
										<td width="300">
											<script language="JavaScript">									
												var opts = <bean:write name="positionForm" property="jsArrayDegrees" filter="false"/>;											
												var opt = [new SelectOption('-1','<bean:message key="common.selectlist.default" />')];
												degrees = opt.concat(opts);
												var checkBoxDegree = new CheckBoxList(opts,'<bean:write name="positionForm" property="degreeId" />',{namesonly:false, layerclass:'checkboxlistdiv', width:'300px', size:5, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
												document.write(checkBoxDegree.getHtml());
												checkBoxDegree.init();
											</script>
										</td>										
									</tr>
									<tr>
										<td class="label" nowrap="nowrap">&nbsp;</td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_BRANCH)){
									%>
									<tr>
										<td class="label" nowrap="nowrap">
										<img src="images/checkboxunchecked.gif" id="IMG_selectAllBranches" onclick="javascript: selectAllBranches(this);">&nbsp;
										<bean:message key="position.requirements.branch" />
										&nbsp;
										<%if(isMandatory.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MANDATORY)){ %>
										<span class="star">*</span>
										<%} %>
										<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span></td>										
										<td>
									</tr>
									<tr>										
										<td width="300">
											<script language="JavaScript">									
												var opts = <bean:write name="positionForm" property="jsArrayBranches" filter="false"/>;											
												var opt = [new SelectOption('-1','<bean:message key="common.selectlist.any" />')];
												branches = opt.concat(opts);
												var checkBoxBranch = new CheckBoxList(opts,'<bean:write name="positionForm" property="branchId" />',{namesonly:false, layerclass:'checkboxlistdiv', width:'300px', size:5, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif'});
												document.write(checkBoxBranch.getHtml());
												checkBoxBranch.init();
											</script>
										</td>
									</tr>
									<tr>
										<td class="label" nowrap="nowrap">&nbsp;</td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_EXPERIENCE)){
									%>									
									<tr>
										<td class="label"><bean:message key="position.requirements.experience" />
										&nbsp;
										<%if(isMandatory.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MANDATORY)){ %>
										<span class="star">*</span>
										<%} %>
										<span title='<bean:message key="common.searchable" />' class="searchableStar">*</span>
										<html:text name="positionForm" property="minimumExperience" size="5" styleClass="Grey"/>&nbsp;<bean:message key="position.requirements.experience.to" />&nbsp;<html:text name="positionForm" property="maximumExperience" size="5" styleClass="Grey"/>&nbsp;<bean:message key="position.requirements.experience.years" /></td>
									</tr>
									<tr>
										<td class="label" colspan="2" nowrap="nowrap">&nbsp;</td>
									</tr>
									<%		
										}
										if(fieldId.equals(PositionConfigurationConstants.FIELD_REQUIREMENTS)){
									%>	
									<tr>
										<td class="label"><bean:message key="position.requirements.job_requirements" />:
										<%if(isMandatory.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MANDATORY)){ %>
										<span class="star">*</span>
										<%} %>
										</td>
									</tr>									
									<tr>
										<td class="label" style="width:380px;">
											Copy <bean:message key="position.tabs.requirements" /> from <a href="#" class="green" onclick="showPositionSelectBox();">another Position</a> or <a href="#" class="green" onclick="showTemplateSelectBox();">Template</a>
										</td>
									</tr>								
									<tr>
										<td>
											<div style="width:100px;">
												<html:textarea property="requirements" name="positionForm" styleClass="inputBox" cols="90" rows="15"></html:textarea>
											</div>
										</td>
									</tr>
									<tr>
										<td class="label" nowrap="nowrap">&nbsp;</td>
									</tr>
									<%}}}} %>
								</table>
							</div>
						</td>
						</tr>
				</table>				
			</logic:notEqual>
		</html:form>
		<br/>
	</div>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
			<tr>
				<td colspan="2"><br/><div class="navBtn" style="float:right;">
				<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_EDIT_POSITIONS">
					<a href="#" style="width:50px;" class="active" onclick="javascript:editRequirements();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
					</logic:equal>
				</logic:notEqual>
				<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CREATE_POSITION_TEMPLATE">
					<a href="#" style="width:50px;" class="active" onclick="javascript:editRequirements();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
					</logic:equal>
				</logic:equal>
				<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a></div></td>
			</tr>
		</logic:equal>
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_EDIT_POSITION%>">
			<tr>
				<td colspan="2">
					<br/>
					<div class="navBtn" style="float:right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:saveRequirements();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.save"/></a>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:cancelEditRequirements();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</logic:equal>
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
			<tr>
				<td colspan="2">
					<br/>
					<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<div class="navBtn" style="float:left;">
						<a href="#" style="width:110px;" class="active" onclick="javascript:saveAsDraft();"><span class="rightC"></span><span class="leftC"></span><bean:message key="add_position.label.save_as_draft"/></a>
					</div>
					</logic:notEqual>
					<div class="navBtn" style="float:right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:addRequirements('<%=PositionConstants.DEST_DESCRIPTION%>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
						<logic:equal value="true" name="requisitionManagement" scope="request" >
						<a href="#" style="width:50px;margin-left:5px;" class="active" onclick="javascript:addRequirements('<%=PositionConstants.DEST_APPROVAL%>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
						</logic:equal>
						<logic:notEqual value="true" name="requisitionManagement" scope="request" >
						<a href="#" style="width:50px;margin-left:5px;" class="active" onclick="javascript:addRequirements('<%=PositionConstants.DEST_HIRING_PROCESS%>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
						</logic:notEqual>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</logic:equal>
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
			<tr>
				<td colspan="2">
					<br/>			
					<div class="navBtn" style="float:left;">
						<a href="#" style="width:110px;" class="active" onclick="javascript:saveAsDraft();"><span class="rightC"></span><span class="leftC"></span><bean:message key="add_position.label.save_as_draft"/></a>
					</div>		
					<div class="navBtn" style="float:right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:addRequirements('<%=PositionConstants.DEST_DESCRIPTION%>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
						<logic:equal value="true" name="requisitionManagement" scope="request" >
						<a href="#" style="width:50px;margin-left:5px;" class="active" onclick="javascript:addRequirements('<%=PositionConstants.DEST_APPROVAL%>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
						</logic:equal>
						<logic:notEqual value="true" name="requisitionManagement" scope="request" >
						<a href="#" style="width:50px;margin-left:5px;" class="active" onclick="javascript:addRequirements('<%=PositionConstants.DEST_HIRING_PROCESS%>');"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.next"/></a>
						</logic:notEqual>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:copyPosition();return false;"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.finish"/></a>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</logic:equal>
	</table>	
</div>
<br/><br/>
<script language="JavaScript">
function saveAsDraft() {
	var url="position.do?mode=savePositionAsDraft&positionId="+document.positionForm.positionId.value;
	window.setTimeout("showInPopUp('"+url+"',500,250,onGetDraftName,true);",10);
}

function onGetDraftName(returnVal) {	
	populateRequirements();
	document.positionForm.mode.value='savePositionAsDraft';
	document.positionForm.draftName.value=returnVal;	
	document.positionForm.submit();
	return true;
}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}
function doOnLoad() {
	initPopUp();	
	//populateRequirementsTable();
	<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
		initSkillCategoriesGrid();
		initSkillsGrid();		
	</logic:notEqual>
	<logic:notEmpty name="fieldValueData" scope="request">
	var msg = '<bean:write name="fieldValueData" scope="request"/>';
	msg = msg.replace(/:::/g, "\n");
	//msg = "Based on \n" + msg + "\nduplicate record(s) found.\nDo you want to see duplicate record(s)?"
	msg = '<bean:message key="create_applicant.label.based_on"/>' + msg + '<bean:message key="create_position.label.duplicate_records_found"/>';
	var retVal = confirm(msg);
	if(retVal) {
		
		return;
	}
	else{
		addRequirements('<%=PositionConstants.DEST_DESCRIPTION%>');
		return;
	}
	</logic:notEmpty>	
}

// Start: JS for Add Position Requirements.
function backToPositionHome() {
	if(document.positionForm.showCondition.value==<%=PositionConstants.POSITION_STATUS_TEMPLATE%>){
		window.location=uncache("positionTemplate.do?mode=positionTemplatesHome");
	}else{
		window.location=uncache("position.do?mode=positionsHome");
	}
	return true;
}

function copyRequirements(id) {
	var pars = "mode=copyRequirements&positionId=" + id;
	var myAjax = ajaxCall("position.do",'get',pars,updateRequirements, reportError);
}

function updateRequirements(request){
	xmlFile = request.responseXML;
	  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) return;
	  var respo = xmlFile.getElementsByTagName("Requirements")[0];
	  tinyMCE.activeEditor.setContent(respo.firstChild.nodeValue);
}

function populateRequirements(){	
	if(checkBoxDegree && checkBoxDegree.getSelectedIds() != -1){
		document.positionForm.degreeId.value=checkBoxDegree.getSelectedIds();
	}		
	if(checkBoxBranch && checkBoxBranch.getSelectedIds() != -1){
		document.positionForm.branchId.value=checkBoxBranch.getSelectedIds();
	}			
	if (primarySkillsGrid && getSelectedPrimarySkills().trim() != '') {
		document.positionForm.primarySkills.value=getSelectedPrimarySkills();
	}		
	if (secondarySkillsGrid && getSelectedSecondarySkills().trim() != '') {
		document.positionForm.secondarySkills.value=getSelectedSecondarySkills();
	}
	if (document.positionForm.minimumExperience.value.trim() == '') {
		document.positionForm.minimumExperience.value = '0';		
	}
	if (document.positionForm.maximumExperience.value.trim() == '') {
		document.positionForm.maximumExperience.value = '0';		
	}
}

function addRequirements(dest) {
	if (dest == '<%=PositionConstants.DEST_HIRING_PROCESS%>' || dest == '<%=PositionConstants.DEST_APPROVAL%>' || document.positionForm.finishCopyPosition.value=="true") {
		errors = validateData();
		if (errors.length > 0) {
			alert(errors);
			return false;
		}
	}

	populateRequirements();
	
	//document.positionForm.jsArrayRequirements.value=_requirements.toString();
	<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
		document.positionForm.mode.value='<%=PositionConstants.MODE_COPY_POSITION%>';
	</logic:equal>
	<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
		document.positionForm.mode.value='<%=PositionConstants.MODE_ADD_POSITION%>';
	</logic:notEqual>
	
	document.positionForm.dest.value=dest;
	document.positionForm.submit();
	return true;
}

function copyPosition(){
	document.positionForm.finishCopyPosition.value='true';
	return addRequirements('') ;
}

function validateData() {
	errors = '';		
	var isNotTemplate = document.positionForm.positionStatus.value !=<%=PositionConstants.POSITION_STATUS_TEMPLATE %>;
	<%
	for(int j=0; j<positionFields.size();j++){
		PositionFieldData fieldData = (PositionFieldData)positionFields.get(j); 
		String fieldId = fieldData.getFieldId();
		String fieldType = fieldData.getFieldType();
		String showValue = fieldData.getFieldPositionShow();
		String isMandatory = fieldData.getFieldPositionMandatory();
		if(showValue.equals(PositionConfigurationConstants.FIELD_SHOW) && isMandatory.equalsIgnoreCase(PositionConfigurationConstants.FIELD_MANDATORY)){				
			if(fieldType.equals(PositionConfigurationConstants.FIELD_TYPE_NORMAL)){														
				if(fieldId.equals(PositionConfigurationConstants.FIELD_EDUCATION)){
		%>		
			if(isNotTemplate){				
				var degreeId = checkBoxDegree.getSelectedIds();
				if (degreeId == -1 || degreeId =='') {
					errors = addError(errors, '- <bean:message key="position.requirements.minimum_education" />');
				}	
			}
		<%		
			}
			if(fieldId.equals(PositionConfigurationConstants.FIELD_BRANCH)){
		%>
			var branchId = checkBoxBranch.getSelectedIds();
			if (isNotTemplate && (branchId == -1 || branchId =='')) {
				errors = addError(errors, '- <bean:message key="position.requirements.branch" />');
			}	
		<%		
			}
			if(fieldId.equals(PositionConfigurationConstants.FIELD_EXPERIENCE)){
		%>
			var minimumExperience = document.positionForm.minimumExperience.value;
			var maximumExperience = document.positionForm.maximumExperience.value;	
			if(isNaN(minimumExperience)){									
				errors = addError(errors, '- <bean:message key="common.please_enter_valid_number" /> in <bean:message key="position.requirements.minimum_experience" />');
			}
			if(isNaN(maximumExperience)){									
				errors = addError(errors, '- <bean:message key="common.please_enter_valid_number" /> in <bean:message key="position.requirements.maximum_experience" />');
			}
		<%		
			}
			if(fieldId.equals(PositionConfigurationConstants.FIELD_PRIMARY_SKILLS)){
		%>
			var pSkills = getSelectedPrimarySkills();
			if (isNotTemplate && pSkills.trim() == '') {
				errors = addError(errors, '- <bean:message key="position.requirements.primary_skills" />');
			}
		<%		
			}
			if(fieldId.equals(PositionConfigurationConstants.FIELD_SECONDARY_SKILLS)){
		%>
			var sSkills = getSelectedSecondarySkills();
			if (isNotTemplate && sSkills.trim() == '') {
				errors = addError(errors, '- <bean:message key="position.requirements.secondary_skills" />');
			}
		<%		
			}
			if(fieldId.equals(PositionConfigurationConstants.FIELD_REQUIREMENTS)){
		%>
			var requirements = tinyMCE.activeEditor.getContent();
			if (isNotTemplate && requirements.trim() == '') {
				errors = addError(errors, '- <bean:message key="position.requirements.job_requirements" />');
			}
	<%}}}}%>
	if (errors.length > 0) {
		errors = addError('<bean:message key="common.data_required" />', errors);
	}
	return errors;
}
/*function getFNumber(obj){
	if(obj.value.trim()!=''){
		if(isNaN(obj.value)){
			alert('<bean:message key="common.please_enter_valid_number" />');
			obj.focus();
			return false;
		}
	}
}*/

// End: JS for Add Position Requirements.

// Start: JS for View Position Requirements.
function editRequirements() {
	document.positionForm.dir.value='<%=PositionConstants.DIR_EDIT_POSITION%>';
	document.positionForm.submit();
	return true;
}
// End: JS for View Position Requirements.

function cancelEditRequirements() {
	document.positionForm.dir.value='<%=PositionConstants.DIR_VIEW_POSITION%>';
	document.positionForm.submit();
	return true;
}

function saveRequirements() {		
	errors = validateData();
	if (errors.length > 0) {
		alert(errors);
		return false;
	}
	//alert(_requirements.toString());l
	populateRequirements();
	
	if(<%= GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION).equals(GlobalConstants.ENABLED)%>){
		if(document.positionForm.positionStatus.value!=<%=PositionConstants.POSITION_STATUS_TEMPLATE %> && confirm('<bean:message key="position.lable.confirm_send_position_change_notification"/>')){
			document.positionForm.sendPositionChangeNotification.value='<%=GlobalConstants.ENABLED%>';
	  	}else{
	  		document.positionForm.sendPositionChangeNotification.value='<%=GlobalConstants.DISABLED%>';
	  	}
	}
	//document.positionForm.jsArrayRequirements.value=_requirements.toString();
	document.positionForm.mode.value='<%=PositionConstants.MODE_SAVE_REQUIREMENTS%>';
	document.positionForm.submit();
	return true;
}

function addError(errors, error) {
	if (errors.length > 0) {
		errors += '\n';
	}
	errors += error;
	return errors;
}


function showPositionSelectBox(){
	var status = <%=PositionConstants.POSITION_STATUS_OPENED%>+","+
	<%=PositionConstants.POSITION_STATUS_CLOSED%>+","+
	<%=PositionConstants.POSITION_STATUS_INPROCESS%>+","+
	<%=PositionConstants.POSITION_STATUS_REJECTED%>+","+
	<%=PositionConstants.POSITION_STATUS_HOLD%>;
	var url="position.do?mode=showPositionSelectBox&subMode="+status;
	showInPopUp(url,650,300,copyRequirements,true);
}

function showTemplateSelectBox(){
	var url="position.do?mode=showPositionSelectBox&subMode=<%=PositionConstants.POSITION_STATUS_TEMPLATE%>";
	showInPopUp(url,650,300,copyRequirements,true);
}
// End: JS for Edit Position Requirements.
window.onload=doOnLoad;

<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">

function getSelectedPrimarySkills() {
	var pSkills = primarySkillsGrid.getAllItemIds();
	return pSkills;
}
function getSelectedSecondarySkills() {
	var sSkills = secondarySkillsGrid.getAllItemIds();
	return sSkills;
}

/************************* START :: Skill Grids Related Code **********************/ 
 //Grid for Skill Category
var skillCategoryGrid;
function initSkillCategoriesGrid(){	
	skillCategoryGrid = new dhtmlXGridObject('SKILL_CATEGORY_GRID');	 
	skillCategoryGrid.imgURL = "images/"; 
	skillCategoryGrid.setHeader("skillCategory");
	skillCategoryGrid.setNoHeader(true);
	skillCategoryGrid.setInitWidths("180");
	skillCategoryGrid.setColAlign("left");
	skillCategoryGrid.setColTypes("ro"); 
	skillCategoryGrid.setColSorting("skill_category_sort");		
	skillCategoryGrid.enableMultiselect('true');
	skillCategoryGrid.enableMultiline(true);
	skillCategoryGrid.init();
	skillCategoryGrid.enableSmartRendering(true);
	loadGridSkillCategory();
	skillCategoryGrid.attachEvent("onRowSelect",doOnSkillCategoryGridRowSelectHandler);
}

function loadGridSkillCategory(){
	skillCategoryGrid.clearAll();
	skillCategoryGrid.loadXML("masters.do?mode=getAllSkillCategories");	
}

function skill_category_sort(a,b,order,aId,bId) {
	a0 =skillCategoryGrid.getUserData(aId,"skillCategory");
	b0 = skillCategoryGrid.getUserData(bId,"skillCategory");	
	return sort_data(a0,b0,order);
}

function doOnSkillCategoryGridRowSelectHandler() {
	//setTimeout(loadSkillsForCategories,500);
	loadSkillsForCategories();
}

//Grid for Skills
var skillGrid;
function initSkillsGrid(){
	skillGrid = new dhtmlXGridObject('SKILLS_GRID');	 
	skillGrid.imgURL = "images/"; 
	skillGrid.setHeader("skillName");
	skillGrid.setNoHeader(true);
	skillGrid.setInitWidths("180");
	skillGrid.setColAlign("left");
	skillGrid.setColTypes("ro"); 
	skillGrid.setColSorting("skill_sort");
	skillGrid.enableMultiselect('true');
	skillGrid.enableMultiline(true);
	skillGrid.init();
	skillGrid.enableSmartRendering(true);
	loadGridSkill();	
}
function loadGridSkill(){	
	skillGrid.clearAll();
	skillGrid.loadXML("masters.do?mode=getAllSkills", loadPrimarySecondarySkills);					  
}

function loadPrimarySecondarySkills(){
	initPrimarySkillsGrid();
	<%
	for(int j=0; j<positionFields.size();j++){
		PositionFieldData fieldData = (PositionFieldData)positionFields.get(j); 
		String fieldId = fieldData.getFieldId();
		String fieldType = fieldData.getFieldType();
		String showValue = fieldData.getFieldPositionShow();
		if(showValue.equals(PositionConfigurationConstants.FIELD_SHOW)){						
			if(fieldType.equals(PositionConfigurationConstants.FIELD_TYPE_NORMAL)){														
				if(fieldId.equals(PositionConfigurationConstants.FIELD_SECONDARY_SKILLS)){
					%>							
						initSecondarySkillsGrid();						
					<%				
				}
			}
		}
	}
	%>
}

function skill_sort(a,b,order,aId,bId) {
	a0 =skillGrid.getUserData(aId,"skillName");
	b0 = skillGrid.getUserData(bId,"skillName");	
	return sort_data(a0,b0,order);
}

//GRID For Primary Skills
var primarySkillsGrid;
function initPrimarySkillsGrid(){
	primarySkillsGrid = new dhtmlXGridObject('PRIMARY_SKILL_GRID');	 
	primarySkillsGrid.imgURL = "images/"; 
	primarySkillsGrid.setHeader("skillName");
	primarySkillsGrid.setNoHeader(true);
	primarySkillsGrid.setInitWidths("180");
	primarySkillsGrid.setColAlign("left");
	primarySkillsGrid.setColTypes("ro"); 
	primarySkillsGrid.setColSorting("primary_skill_sort");
	primarySkillsGrid.enableMultiselect('true');
	primarySkillsGrid.enableMultiline(true);
	primarySkillsGrid.init();
	primarySkillsGrid.enableSmartRendering(true);
	
	primarySkillsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}
	
	primarySkillsGrid.attachEvent("onRowDblClicked",doOnPrimarySkillsGridRowDblClicked);	
	loadPrimarySkillsSelected();	
}

function primary_skill_sort(a,b,order,aId,bId) {
	a0 =primarySkillsGrid.getUserData(aId,"skillName");
	b0 = primarySkillsGrid.getUserData(bId,"skillName");	
	return sort_data(a0,b0,order);
}

function loadPrimarySkillsSelected(){
	var pSkills = '<bean:write name="positionForm" property="primarySkills" />';	
	selectItems(pSkills,skillGrid,primarySkillsGrid);	
}

function doOnPrimarySkillsGridRowDblClicked(){
	var text = (primarySkillsGrid.cells(primarySkillsGrid.getSelectedId(),0)).getValue();
	resetGridItem(primarySkillsGrid,skillGrid);
}

//GRID For Secondary Skills
var secondarySkillsGrid;
function initSecondarySkillsGrid(){
	secondarySkillsGrid = new dhtmlXGridObject('SECONDARY_SKILL_GRID');	 
	secondarySkillsGrid.imgURL = "images/"; 
	secondarySkillsGrid.setHeader("skillName");
	secondarySkillsGrid.setNoHeader(true);
	secondarySkillsGrid.setInitWidths("180");
	secondarySkillsGrid.setColAlign("left");
	secondarySkillsGrid.setColTypes("ro"); 
	secondarySkillsGrid.setColSorting("secondary_skill_sort");	
	//secondarySkillsGrid.attachEvent("onRowDblClicked",onRowDoubleClick);	
	secondarySkillsGrid.enableMultiselect('true');
	secondarySkillsGrid.enableMultiline(true);
	secondarySkillsGrid.init();
	secondarySkillsGrid.enableSmartRendering(true);
	secondarySkillsGrid.gridToGrid = function(rowId,sgrid,tgrid){
	    var z=new Array();
		for(var i=0;i<sgrid.hdr.rows[0].cells.length;i++)
		z[i]=sgrid.cells(rowId,i).getValue();
		return z;
	}	
	secondarySkillsGrid.attachEvent("onRowDblClicked",doOnSecondarySkillsGridRowDblClicked);	
	loadSecondarySkillsSelected();	
}

function loadSecondarySkillsSelected(){
	var sSkills = '<bean:write name="positionForm" property="secondarySkills" />';
	selectItems(sSkills,skillGrid,secondarySkillsGrid);
}

function doOnSecondarySkillsGridRowDblClicked(){
	var text = (secondarySkillsGrid.cells(secondarySkillsGrid.getSelectedId(),0)).getValue();
	resetGridItem(secondarySkillsGrid,skillGrid);
}

function secondary_skill_sort(a,b,order,aId,bId) {
	a0 = secondarySkillsGrid.getUserData(aId,"skillName");
	b0 = secondarySkillsGrid.getUserData(bId,"skillName");	
	return sort_data(a0,b0,order);
}

//Common functions
function loadSkillsForCategories(){
	var selectedSkillCategories = skillCategoryGrid.getSelectedId();	
	skillGrid.clearAll();
	skillGrid.loadXML("masters.do?mode=getAllSkills&skillCategoryId="+selectedSkillCategories);	
	setTimeout(loadSkillGrid,500);
}

function loadSkillGrid(){
	var psSkills;
	if (primarySkillsGrid && getSelectedPrimarySkills().trim() != '') {		
		psSkills=getSelectedPrimarySkills();
	}
	if (secondarySkillsGrid && getSelectedSecondarySkills().trim() != '') {		
		psSkills=psSkills+","+getSelectedSecondarySkills();	
	}	
	deleteSelectedItems(psSkills,skillGrid);	
	/*if(psSkills!=null){
		var skills = psSkills.split(',');
		for(k=0;k<skills.length;k++){
			if(skillGrid.getRowId(skills[k])!='undefined'){
				alert(skillGrid.getRowId(skills[k]));
				skillGrid.deleteRow(skillGrid.getRowId(skills[k]));
			}
		}
	}*/
}

function sort_data(a,b,order) {
	if(order=="asc")
		return a.toLowerCase()>b.toLowerCase()?1:-1;
	else
		return a.toLowerCase()<b.toLowerCase()?1:-1;
}

function resetGridItem(srcGrid,destGrid){	
	selectItem(srcGrid,destGrid);		
}


/************************* END :: Skill Grids Related Code **********************/



function selectAllDegrees(obj){
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
		checkBoxDegree.resetSelected();
	} else {
		obj.src=chkboxchked;
		checkBoxDegree.selectAll(true);
	}
}
function selectAllBranches(obj){
	if (obj.src.indexOf(chkboxchked) != -1) {
		obj.src=chkboxunchked;
		checkBoxBranch.resetSelected();
	} else {
		obj.src=chkboxchked;
		checkBoxBranch.selectAll(true);
	}
}
</logic:notEqual>
</script>