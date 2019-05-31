<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@page import="java.util.BitSet,
								com.talentPool.positions.PositionConstants,
								com.talentPool.common.NavigationConstants"%>
<style>
table.positionH B{
	font-size: 13px;
}
</style>
<table width="100%" class="positionH" cellspacing="0">
	<tr >
	  	<td>
	  		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
				<b><bean:message key="common.add_new" /> 
				<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<bean:message key="common.template" />
				</logic:equal>
				<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<bean:message key="common.position" />
				</logic:notEqual>
				</b>
			</logic:equal>
			<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
				<b>
				<logic:equal name="positionForm" property="showCondition" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<bean:message key="common.add_new" />
				</logic:equal>
				<logic:notEqual name="positionForm" property="showCondition" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<bean:message key="common.copy" />
				</logic:notEqual>
				<bean:message key="common.position" /></b>
			</logic:equal>
			<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_EDIT_POSITION%>">
				<logic:notEmpty name="positionName" scope="request">
			  		<b><bean:write name="positionName" scope="request" /></b>
			  		<logic:notEmpty name="positionCode" scope="request">
						<bean:message key="common.openingSquareBracket" /><bean:write name="positionCode" scope="request" /><bean:message key="common.closingSquareBracket" />
					</logic:notEmpty>
				</logic:notEmpty>
			</logic:equal>
		  	<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
			  	<logic:notEmpty name="positionName" scope="request">
			  		<b><bean:write name="positionName" scope="request" /></b>
			  		<logic:notEmpty name="positionCode" scope="request">
						<bean:message key="common.openingSquareBracket" /><bean:write name="positionCode" scope="request" /><bean:message key="common.closingSquareBracket" />
					</logic:notEmpty>
				</logic:notEmpty>
			</logic:equal>
		</td>
		<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
			<td align="right">
				<a href="#" onclick="viewPositionSummary();return false;" class="green">
	   				<bean:message key="common.applicants" />
	   			</a>
			</td>
		</logic:equal>
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_EDIT_POSITION%>">
			<td align="right">
				<a href="#" onclick="viewPositionSummary();return false;" class="green">
	   				<bean:message key="common.applicants" />
	   			</a>
			</td>
		</logic:equal>
		</logic:notEqual>
	</tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0" style="border-bottom:1px solid #99CC33;">	
	<tr>
		<td colspan="2" height="16"></td>
	</tr>
	<tr>
		<td>
			<div class="navBtnTab2" >
				<a  
					<logic:equal name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_DESCRIPTION%>"> 
						class="active" 
					</logic:equal>
					<logic:notEqual name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_DESCRIPTION%>"> 						
							href="#" 						
						<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">							
							<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
								onclick="javascript: submitForm('description','<%=PositionConstants.DIR_VIEW_POSITION%>');"
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
					style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="position.tabs.description" />
				</a>
				<a 
					<logic:equal name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_REQUIREMENTS%>"> 
						class="active" 
					</logic:equal>
					<logic:notEqual name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_REQUIREMENTS%>">						
							href="#" 						
						<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">							
							<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
								onclick="javascript: submitForm('requirements','<%=PositionConstants.DIR_VIEW_POSITION%>');"
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
					style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="position.tabs.requirements" />
				</a>
				<logic:equal value="true" name="requisitionManagement" scope="request" >
				<a 
					<logic:equal name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_APPROVAL%>"> 
						class="active" 
					</logic:equal>
					<logic:notEqual name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_APPROVAL%>">
						
							href="#" 
					
						<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">							
							<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
								onclick="javascript: submitForm('approval','<%=PositionConstants.DIR_VIEW_POSITION%>');"
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
					style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="position.tabs.approval" />
				</a>
				</logic:equal>
				<logic:equal value="true" name="requisitionManagement" scope="request" >
				
				<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
					<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
				<a 
					<logic:equal name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_HIRING_PROCESS%>">
						 class="active" 
					</logic:equal>
					<logic:notEqual name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_HIRING_PROCESS%>">
							href="#" onclick="javascript: submitForm('hiringProcess','<%=PositionConstants.DIR_VIEW_POSITION%>');"
					</logic:notEqual>
					style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="position.tabs.hiring_process" />
				</a> 
					</logic:notEqual>
					
					<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
						<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
						<a 
							<logic:equal name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_HIRING_PROCESS%>">
								 class="active" 
							</logic:equal>
							style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
							<bean:message key="position.tabs.hiring_process" />
						</a> 
						</logic:equal>
					</logic:equal>
					
				</logic:notEqual>
				</logic:equal>
				<logic:notEqual value="true" name="requisitionManagement" scope="request" >
				<a 
					<logic:equal name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_HIRING_PROCESS%>">
						 class="active" 
					</logic:equal>
					<logic:notEqual name="positionForm" property="step" value="<%=NavigationConstants.STEP_POSITION_HIRING_PROCESS%>">
						<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>"> 
							href="#" 
						</logic:equal>
						<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
							href="#" onclick="javascript: submitForm('hiringProcess','<%=PositionConstants.DIR_VIEW_POSITION%>');"
						</logic:notEqual>
					</logic:notEqual>
					style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="position.tabs.hiring_process" />
				</a> 
				</logic:notEqual>
				<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
					<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_COPY_POSITION%>">
				<a 
					<logic:equal name="positionForm" property="step" value="<%=NavigationConstants.ADD_POSITION_DOCUMENTS%>">
						 class="active" 
					</logic:equal>
					<logic:notEqual name="positionForm" property="step" value="<%=NavigationConstants.ADD_POSITION_DOCUMENTS%>">
							href="#" onclick="javascript: submitForm('positionDocuments','<%=PositionConstants.DIR_VIEW_POSITION%>');"
					</logic:notEqual>
					style="display:block;width:120px;"><span class="rightC"></span><span class="leftC"></span>
					<bean:message key="position.tabs.documents" />
				</a> 
					</logic:notEqual>
				</logic:notEqual>
			</div>		
		</td>
	</tr>
</table>
<script language="JavaScript">
function submitForm(mode,dir) {
	document.positionForm.mode.value=mode;	
	document.positionForm.dir.value=dir;
	document.positionForm.submit();
	return true;
}
function viewPositionSummary(){
	var positionId = '<bean:write name="positionForm" property="positionId" scope="request" />';
	window.location.href=uncache("position.do?mode=positionSummary&positionId="+positionId);
}
</script>