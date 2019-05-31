<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="org.apache.struts.Globals,
								com.talentPool.positions.form.PositionForm,
								com.talentPool.positions.PositionConstants,
								com.talentPool.common.db.SimpleDataObject,
								com.talentPool.common.properties.TPApplicationProperties,
								com.talentPool.common.utils.Utils" %>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="java.util.List"%>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>							
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>													
<script language="JavaScript" src="js/doClasses/IdValueClass.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/doClasses/StatusMessageClass.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/doClasses/StepClass.js" type="text/javascript"></script>	
<script src="js/submodal/common.js"></script>
<script src="js/submodal/subModal.js"></script>
<script type="text/javascript">
var selectBoxPosition = null;
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
<%
boolean isDefault = false;
boolean isSchedulable = true;
%>
<html:form action="/position">
	<html:hidden property="mode" name="positionForm"/>
	<html:hidden property="dir" name="positionForm"/>
	<html:hidden property="dest" name="positionForm"/>
	<html:hidden property="step" name="positionForm"/>
	<html:hidden property="positionId" name="positionForm"/>	
	<html:hidden property="_positionId" name="positionForm"/>	
	<html:hidden property="showCondition" name="positionForm"/>	
	<html:hidden property="jsArrayHiringProcess" name="positionForm"/>		
	<html:hidden property="positionStatus" name="positionForm"/>
	<html:hidden property="copyFrom" name="positionForm"/>	
	<html:hidden property="notifyUserIds" name="positionForm"/>	
	<div class="outerDiv" style="border-top:0px; ">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td> 
					<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
					<logic:notEmpty name="positionForm" property="hiringProcess">
						<div class="contentDiv" style="margin-bottom: 10px;">
							<div class="outerDiv" style="margin-bottom: 10px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="hiringprocess" style="margin-bottom: 10px;">								
									<logic:iterate id="step" name="positionForm" property="hiringProcess" type="SimpleDataObject" indexId="counter">
										<logic:notEmpty name="step">
										<%
										isDefault = ((step.getInt("isDefault") == PositionConstants.STEP_DEFAULT));
										isSchedulable = (!Utils.isBlankOrNull(step.getString("scheduledByUsers")));
										%>
											<tr>
												<td class="normal">
													<table width="100%">
														<tr>
															<td width="90px;">
																<bean:message key="position.hiring_process.step" />&nbsp;<%=counter.intValue()+1%><bean:message key="common.colon" />
															</td>
															<td  align="left">
																<strong>
																	<%if(isDefault) {%>
																		<%=Utils.escapeHTML(step.getString("stepTitle"))%>
																	<%}else{%>
																		<font color="#669900"><%=Utils.escapeHTML(step.getString("stepTitle"))%></font>																				
																	<%}%>
																</strong>
																<% if (step.getInt("isOptional") == PositionConstants.STEP_OPTIONAL) { %>
																<bean:message key="common.openingRoundBracket" /><bean:message key="position.hiring_process.label.optional" /><bean:message key="common.closingRoundBracket" />
																<% } %>
															</td>	
														</tr>
														<%if(!isDefault) {%>
														<tr>
															<td valign="top" width="90px;" class="Grey">
																<bean:message key="position.hiring_process.step.assigned_to" />
															</td>
															<td class="Grey">
																<%=Utils.escapeHTML(step.getString("assignedToUsers"))%>
															</td>
														</tr>
														<%if(isSchedulable) {%>
														<tr>
															<td valign="top" width="90px;" class="Grey">
																<bean:message key="position.hiring_process.step.scheduled_by" />
															</td>
															<td class="Grey">
																<%=Utils.escapeHTML(step.getString("scheduledByUsers"))%>
															</td>
														</tr>
														<%} %>
														<tr>
															<td valign="top" width="90px;" class="Grey">
																<bean:message key="position.hiring_process.step.decision_maker" />
															</td>
															<td class="Grey">
																<%=Utils.escapeHTML(step.getString("decisionMakerUsers"))%>
															</td>
														</tr>
														<%} %>
													</table>
												</td>
											</tr>
										</logic:notEmpty>
									</logic:iterate>
								</table>
							</div>
							</br>
						</div>
						
					</logic:notEmpty>
					</logic:equal>
					<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
						<script language="JavaScript">
							var _users = new Array();
							var _hrUsers = new Array();
							var _steps = new Array();
							var _userMap = new Object();
							var key = null;  
							<logic:notEmpty property="users" name="positionForm">
								<logic:iterate id="user" property="users" name="positionForm" type="SimpleDataObject">
									var _user = new IdValueBean();			
									_user.setId('<%=user.getString("userId")%>');
									_user.setValue('<%=Utils.escapeJavaScript(user.getString("userName"))%>');
									_users[_users.length] = _user;
									key = '<%=user.getString("userId")%>'; 
									_userMap[key] = '<%=Utils.escapeJavaScript(user.getString("name"))%>';
								</logic:iterate>
							</logic:notEmpty>
							
							<logic:notEmpty property="hrUsers" name="positionForm">
								<logic:iterate id="hrUser" property="hrUsers" name="positionForm" type="SimpleDataObject">
									var _hrUser = new IdValueBean();			
									_hrUser.setId('<%=hrUser.getString("userId")%>');
									_hrUser.setValue('<%=Utils.escapeJavaScript(hrUser.getString("userName"))%>');
									_hrUsers[_hrUsers.length] = _hrUser;
								</logic:iterate>
							</logic:notEmpty>					
							
							<logic:notEmpty property="hiringProcess" name="positionForm">
								<logic:iterate id="step" property="hiringProcess" name="positionForm" type="SimpleDataObject" indexId="counter">
									var _step = new Step();
									_step.setIndex('<bean:write name="counter" />');
									_step.setStepId('<%=step.getString("stepId")%>');
									_step.setStepMasterId('<%=step.getString("stepMasterId")%>');
									_step.setStepTitle(unescapeHTML("<%=Utils.escapeHTML(step.getString("stepTitle"))%>"));
									_step.setIsDefault('<%=step.getString("isDefault")%>');
									_step.setAssignedTo('<%=step.getString("assignedTo")%>');
									_step.setAssignedToUsers('<%=Utils.escapeJavaScript(step.getString("assignedToUsers"))%>');
									_step.setIsScheduled('<%=step.getString("isScheduled")%>');
									_step.setScheduledBy('<%=(step.getString("scheduledBy")==null)?"":step.getString("scheduledBy")%>');
									_step.setScheduledByUsers('<%=(step.getString("scheduledByUsers")==null)?"":step.getString("scheduledByUsers")%>');
									_step.setIsInterviewerCanConfirm('<%=step.getString("isInterviewerCanConfirm")%>');
									_step.setIsDecisionMakerSameAsAssignedTo('<%=step.getString("isDecisionMakerSameAsAssignedTo")%>');
									_step.setDecisionMaker('<%=step.getString("decisionMaker")%>');
									_step.setDecisionMakerUsers('<%=Utils.escapeJavaScript(step.getString("decisionMakerUsers"))%>');
									_step.setIsOptional('<%=step.getString("isOptional")%>');
									_step.setFeedbackFormId('<%=step.getString("feedbackFormId")%>');
									<%-- _step.setApplicantFeedbackFormId('<%=step.getString("applicantFeedbackFormId")%>'); --%>
									_step.setStepLevel('<%=step.getString("stepLevel")%>');
									_step.setIsNotifyToCandidate('<%=step.getString("isNotifyToCandidate")%>');
									_step.setMessages(new Array());				
									<% List messages = (List) step.getAttribute("messages"); pageContext.setAttribute("messages", messages); %>
									<logic:notEmpty name="messages">
										<logic:iterate id="message" name="messages" type="SimpleDataObject">
											var _message = new StatusMessage();			
											_message.setMessageId('<%=message.getString("messageId")%>');
											_message.setMessage(unescapeHTML("<%=Utils.escapeHTML(message.getString("message"))%>"));
											_message.setIsDefault('<%=message.getString("isDefault")%>');
											_step.messages[_step.messages.length]=_message;
										</logic:iterate>
									</logic:notEmpty>
									_steps[_steps.length]=_step;
								</logic:iterate>
							</logic:notEmpty>
						</script>
						<div class="contentDiv">
						<%
						boolean showCopyProcess=true;
						%>
							<%
							if (showCopyProcess){
							%>
							<table class="posinput" border="0" cellspacing="0" cellpadding="0" style="margin-bottom:20px;">
								<tr>
									<td class="label" style="width:380px;">
										Copy <bean:message key="position.tabs.hiring_process" /> from <a href="#" class="green" onclick="checkCandidatesInprocess('positions');">another Position</a> or <a href="#" class="green" onclick="checkCandidatesInprocess('templates');">Template</a>
									</td>
								</tr>							
							</table>
							<% 
							}
							String divHeight="260px";
							if(showCopyProcess){
								divHeight="212px";
							}
							%>
							
							<div class="outerDiv" >					
								<table id="hiringProcessSteps" width="97%" border="0" cellspacing="0" cellpadding="0" class="hiringprocess">								
								</table>
							</div>	
							<a href="#" class="btn3" style="width:120px;margin-top:5px;margin-bottom:10px;" class="active" onclick="javascript: showStepPopup(-1);"><span class="rightC"></span><span class="leftC"></span><bean:message key="position.hiring_process.label.add_new_step"/></a>
						</div>
					</logic:notEqual>
				</td>
			</tr>
		</table>
	</div>
	</html:form>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_VIEW_POSITION%>">
			<tr>
				<td colspan="2"><br/><div class="navBtn" style="float:right;">
				<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_EDIT_POSITIONS">
					<a href="#" style="width:50px;" class="active" onclick="javascript:editHiringProcess();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
					</logic:equal>
				</logic:notEqual>
				<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
					<logic:equal value="true" name="permissionSet" scope="session" property="PERMISSION_CREATE_POSITION_TEMPLATE">
					<a href="#" style="width:50px;" class="active" onclick="javascript:editHiringProcess();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.edit"/></a>
					</logic:equal>
				</logic:equal>
				<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
				</td>
			</tr>
		</logic:equal>
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_EDIT_POSITION%>">
			<tr>
				<td colspan="2">
					<br/>
					<div class="navBtn" style="float:right;">						
						<a href="#" style="width:60px;" class="active" onclick="javascript:cancelEditHiringProcess();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</logic:equal>
		<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
			<tr>
				<td colspan="2">
					<br/>
					<div class="navBtn" style="float:right;">
						<a href="#" style="width:50px;" class="active" onclick="javascript:backToApproval();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.back"/></a>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:createPosition();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.finish"/></a>
						<a href="#" style="width:60px;margin-left:5px;" class="active" onclick="javascript:backToPositionHome();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
					</div>
				</td>
			</tr>
		</logic:equal>
	</table>	
</div>
<br></br>
<br></br>
<script language="JavaScript">
window.onload=doOnLoad;
function doOnLoad() {
	initPopUp();
	populateHiringProcessSteps();
}
// Start: View Hiring Process.
function editHiringProcess() {
	var canEditProcess = checkMigrationStatus();
	if(canEditProcess){
		document.positionForm.mode.value='<%=PositionConstants.MODE_HIRING_PROCESS%>';
		document.positionForm.dir.value='<%=PositionConstants.DIR_EDIT_POSITION%>';
		document.positionForm.submit();
		return true;		
	}else{
		alert('<bean:message key="steps_migration.message.complete_position_migration_process_to_edit" />');
		return false;
	}
}

function checkMigrationStatus(){
	var templatesMigrationPending=true;
	var openPositionsMigrationPending=true;
	var closedPositionsMigrationPending=true;
	<logic:present name="templatesMigrationPending" scope="request">
		templatesMigrationPending = false;
	</logic:present>
	<logic:present name="openPositionsMigrationPending" scope="request">
		openPositionsMigrationPending = false;
	</logic:present>
	<logic:present name="closedPositionsMigrationPending" scope="request">
		closedPositionsMigrationPending = false;
	</logic:present>
	if(document.positionForm.positionStatus.value==<%=PositionConstants.POSITION_STATUS_TEMPLATE %>)
		return templatesMigrationPending;
	else if(document.positionForm.positionStatus.value==<%=PositionConstants.POSITION_STATUS_CLOSED %>){
		return closedPositionsMigrationPending;
	}else {
		return openPositionsMigrationPending;		
	}
} 
// End: View Hiring Process.

// Start: Edit Hiring Process.
function cancelEditHiringProcess() {
	document.positionForm.dir.value='<%=PositionConstants.DIR_VIEW_POSITION%>';
	document.positionForm.mode.value='<%=PositionConstants.MODE_HIRING_PROCESS%>';
	document.positionForm.submit();
	return true;
} 

function backToApproval() {
	document.positionForm.mode.value='<%=PositionConstants.MODE_ADD_POSITION%>';
	document.positionForm.dest.value='<%=PositionConstants.DEST_APPROVAL%>';
	document.positionForm.jsArrayHiringProcess.value=_steps.toString();
	document.positionForm.submit();
	return true;
}

function createPosition() {
	document.positionForm.mode.value='<%=PositionConstants.MODE_ADD_POSITION%>';
	document.positionForm.dest.value='';
	document.positionForm.jsArrayHiringProcess.value=_steps.toString();
	document.positionForm.submit();
	return true;
}

function populateHiringProcessSteps() {
	tbl = $('hiringProcessSteps');
	var isDefault = false;
	if (tbl) {
		var tBody= tbl.getElementsByTagName("tbody")[0];
		if(tBody){
			var tRows = tBody.childNodes;
			for(var i = tRows.length - 1; i >= 0; i--){
					tBody.removeChild(tRows[i]);
			}
		}
		
		for (var i = 0; i < _steps.length; i++) {
			_step = _steps[i];
			_step.setIndex(i);
			if (_step.isDefault == '<%=PositionConstants.STEP_DEFAULT%>') 
			{
				isDefault= true;
			} else{
				isDefault= false;
			}
			
			if(tBody){
			}else{
				tBody = document.createElement("TBODY");
			}
			var tRow = document.createElement("TR");
	
			var tCell0 = document.createElement("TD");
			tCell0.className="normal";

			var tbl1 = document.createElement("TABLE");
			var tbl1_tbody = document.createElement("TBODY");
			var tbl1_trow1 = document.createElement("TR");
			var tbl1_trow1_td1 = document.createElement("TD");
			var tbl1_trow1_td2 = document.createElement("TD");
			var tbl1_trow1_td3 = document.createElement("TD");

			tbl1.style.width="100%";

			tCell0.appendChild(tbl1).appendChild(tbl1_tbody).appendChild(tbl1_trow1);

			tbl1_trow1_td1.innerHTML = '<bean:message key="position.hiring_process.step" />' + '&nbsp;' + (i + 1) + '<bean:message key="common.colon" />';

				innerHTML = '';
				if (!isDefault) {		
					innerHTML = '<a href="#" class="green" onclick="javascript: showStepPopup(' + i + ');"><strong>' + _step.stepTitle.escapeHTML() + '</strong></a>';
					if (_step.isOptional == '<%=PositionConstants.STEP_OPTIONAL%>') {
						innerHTML += '&nbsp;<bean:message key="common.openingRoundBracket" /><bean:message key="position.hiring_process.label.optional" /><bean:message key="common.closingRoundBracket" />'
					}
				} else {
					innerHTML = '<strong>' + _step.stepTitle.escapeHTML() + '</strong>';
				}
				
			tbl1_trow1_td2.innerHTML = innerHTML;
			if(isDefault)
				tbl1_trow1_td3.innerHTML = '&nbsp;';
			else 
				tbl1_trow1_td3.innerHTML = '<img style="cursor:pointer;" title="' + '<bean:message key="common.delete"/>' + '" src="images/ico_delete.gif" onclick="javascript: doDeleteStep(' + i + ');"/>';

				tbl1_trow1_td1.width = '90px';
				tbl1_trow1_td2.width = '400px';
				tbl1_trow1_td3.width = '30px';
					
			tbl1_trow1.appendChild(tbl1_trow1_td1);
			tbl1_trow1.appendChild(tbl1_trow1_td2);
			tbl1_trow1.appendChild(tbl1_trow1_td3);

			if(!isDefault){
				var tbl1_trow2 = document.createElement("TR");
				var tbl1_trow2_td1 = document.createElement("TD");
				var tbl1_trow2_td2 = document.createElement("TD");
				var tbl1_trow2_td3 = document.createElement("TD");

				tbl1_trow2_td1.className="Grey";
				tbl1_trow2_td2.className="Grey";
				tbl1_trow2_td1.vAlign="top";
				
				tbl1_trow2_td1.innerHTML='<bean:message key="position.hiring_process.step.assigned_to" />';
				tbl1_trow2_td2.innerHTML=_step.assignedToUsers;
				tbl1_trow2_td3.innerHTML='&nbsp;';

				tbl1_trow2_td1.width = '90px';
				tbl1_trow2_td2.width = '400px';
				tbl1_trow2_td3.width = '30px';
				
				tbl1_trow2.appendChild(tbl1_trow2_td1);
				tbl1_trow2.appendChild(tbl1_trow2_td2);
				tbl1_trow2.appendChild(tbl1_trow2_td3);
				tbl1_tbody.appendChild(tbl1_trow2);

				if(_step.scheduledByUsers!=null && _step.scheduledByUsers!=''){
					var tbl1_trow3 = document.createElement("TR");
					var tbl1_trow3_td1 = document.createElement("TD");
					var tbl1_trow3_td2 = document.createElement("TD");
					var tbl1_trow3_td3 = document.createElement("TD");

					tbl1_trow3_td1.className="Grey";
					tbl1_trow3_td2.className="Grey";
					tbl1_trow3_td1.vAlign="top";
					
					tbl1_trow3_td1.innerHTML='<bean:message key="position.hiring_process.step.scheduled_by" />';
					tbl1_trow3_td2.innerHTML=_step.scheduledByUsers;
					tbl1_trow3_td3.innerHTML='&nbsp;';

					tbl1_trow3_td1.width = '90px';
					tbl1_trow3_td2.width = '400px';
					tbl1_trow3_td3.width = '30px';

					tbl1_trow3.appendChild(tbl1_trow3_td1);
					tbl1_trow3.appendChild(tbl1_trow3_td2);
					tbl1_trow3.appendChild(tbl1_trow3_td3);
					tbl1_tbody.appendChild(tbl1_trow3);
				}

				var tbl1_trow4 = document.createElement("TR");
				var tbl1_trow4_td1 = document.createElement("TD");
				var tbl1_trow4_td2 = document.createElement("TD");
				var tbl1_trow4_td3 = document.createElement("TD");

				tbl1_trow4_td1.className="Grey";
				tbl1_trow4_td2.className="Grey";
				tbl1_trow4_td1.vAlign="top";
				
				tbl1_trow4_td1.innerHTML='<bean:message key="position.hiring_process.step.decision_maker" />';
				tbl1_trow4_td2.innerHTML=_step.decisionMakerUsers;
				tbl1_trow4_td3.innerHTML='&nbsp;';

				tbl1_trow4_td1.width = '90px';
				tbl1_trow4_td2.width = '400px';
				tbl1_trow4_td3.width = '30px';

				tbl1_trow4.appendChild(tbl1_trow4_td1);
				tbl1_trow4.appendChild(tbl1_trow4_td2);
				tbl1_trow4.appendChild(tbl1_trow4_td3);
				tbl1_tbody.appendChild(tbl1_trow4);
			}
			tRow.appendChild(tCell0);
			tBody.appendChild(tRow);
			tbl.appendChild(tBody);
		}
	}
}
function getUsersRow(_step){
	var tr = document.createElement("TR");
	var td = document.createElement("TD");
	var userTbl = document.createElement("TABLE");
	var userTbody = document.createElement("TBODY");
	var assignedToUsersRow = null;
	var scheduledByUsersRow = null;
	var decisionMakerUsersRow = null;
	if (_step.isDefault == '<%=PositionConstants.STEP_DEFAULT%>') {
		return null;
	}else{
		if(_step.assignedToUsers!=''){
			assignedToUsersRow = getAssignedToUserRow(_step);
			userTbody.appendChild(assignedToUsersRow);
		}
		if(_step.scheduledByUsers!=''){
			scheduledByUsersRow = getScheduledByUsersRow(_step);
			userTbody.appendChild(scheduledByUsersRow);
		}
		if(_step.decisionMakerUsers!=''){
			decisionMakerUsersRow = getDecisionMakerUsersRow(_step);
			userTbody.appendChild(decisionMakerUsersRow);
		}
		tr.appendChild(td).appendChild(userTbl).appendChild(userTbody);
		return tr;	
	}		
}
function getAssignedToUserRow(_step){
	var tr = document.createElement("TR");
	var td1 = document.createElement("TD");
	var td2 = document.createElement("TD");
	td1.innerHTML = 'AT: ';
	td2.innerHTML=_step.assignedToUsers;
	tr.appendChild(td1);
	tr.appendChild(td2);
	return tr;
}
function getScheduledByUsersRow(_step){
	var tr = document.createElement("TR");
	var td1 = document.createElement("TD");
	var td2 = document.createElement("TD");
	td1.innerHTML = 'SB: ';
	td2.innerHTML=_step.scheduledByUsers;
	tr.appendChild(td1);
	tr.appendChild(td2);
	return tr;
}
function getDecisionMakerUsersRow(_step){
	var tr = document.createElement("TR");
	var td1 = document.createElement("TD");
	var td2 = document.createElement("TD");
	td1.innerHTML = 'DM: ';
	td2.innerHTML=_step.decisionMakerUsers;
	tr.appendChild(td1);
	tr.appendChild(td2);
	return tr;
}
var stepId1, stepId2;

function doChangeStepRank(id1, id2) {
	if (id2 != 1 && id2 != _steps.length) {
		if(_steps[id1].stepLevel<_steps[id2].stepLevel){
			alert('<bean:message key="position.hiring_process.step.error.change_rank_failed_for_different_stages" />');		
		}else{
			if ((_steps[id1].stepId == 0) && (_steps[id2].stepId == 0)) {
				swapSteps(id1, id2);
			} else {
				stepId1 = id1;
				stepId2 = id2;
				var pars = "mode=isChangeInStepRankPossible&stepId1=" + _steps[id1].stepId + "&stepId2=" + _steps[id2].stepId + "&stepRank1=" + (_steps[id1].index + 1) + "&stepRank2=" + (_steps[id2].index + 1);
				var myAjax = ajaxCall("position.do",'get',pars,onDoChangeStepRankResponse, reportError);
			}
		}
	}
}

function swapSteps(id1, id2) {               
	_temp = _steps[id1];
	_steps[id1]=_steps[id2];
	_steps[id2]=_temp;
	
	populateHiringProcessSteps();
}

function onDoChangeStepRankResponse(request){
  	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	 	if(isErrorXml(xmlFile)){
			errorElem = parseErrors(xmlFile);
			alert(errorElem);
			return;
		}	else {
			swapSteps(stepId1, stepId2);
		}
		stepId1=0;
		stepId2=0;
}
var stepId0;
function doDeleteStep(index) {
	if (_steps[index].stepId == 0) {
		deleteStep(index);
	} else {
		stepId0=index;
		var pars = "mode=isDeleteStepPossible&stepId=" + _steps[index].stepId + "&stepRank=" + (_steps[index].index + 1) + "&positionId=" + document.positionForm.positionId.value;
		var myAjax = ajaxCall("position.do",'get',pars,onDoDeleteStepResponse, reportError);
	}
}

function onDoDeleteStepResponse(request){
  	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	 	if(isErrorXml(xmlFile)){	 		
			errorElem = parseErrors(xmlFile);
			alert(errorElem);
			return;
		}	else {
			deleteStep(stepId0);
			stepId0=0;
		}
}

function deleteStep(id) {
	_steps.splice(id, 1);
	populateHiringProcessSteps();
}

function showStepPopup(stepIndex) {
	showPopWin("position.do?mode=showStepPopup&stepIndex=" + stepIndex+"&positionId="+document.positionForm.positionId.value, "550", "500", setStepData,true);
}
var clone = null;
function setStepData(_newstep){
	
	clone = cloneStep(_newstep);
	if (clone.stepId == -1) {
		clone.setStepId(0);		
	} else {
		_steps[clone.index]=clone;
	}
	<logic:notEqual name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">		
		var pars = "mode=savePositionStep&jsArrayHiringProcess=" + encodeURIComponent(clone.toString()) + "&positionId=" + document.positionForm.positionId.value;
		if(<%= GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_POSITION_CHANGE_NOTIFICATION).equals(GlobalConstants.ENABLED)%>){
			if(document.positionForm.positionStatus.value!=<%=PositionConstants.POSITION_STATUS_TEMPLATE %> && confirm('<bean:message key="position.lable.confirm_send_position_change_notification"/>')){
				pars += "&sendPositionChangeNotification=<%=GlobalConstants.ENABLED%>";
		  	}else{
		  		pars += "&sendPositionChangeNotification=<%=GlobalConstants.DISABLED%>";
		  	}
		}
		var myAjax = ajaxCall("position.do",'post',pars,onSavePositionStepResponse, reportError);			
	</logic:notEqual>	
	<logic:equal name="positionForm" property="dir" value="<%=PositionConstants.DIR_ADD_POSITION%>">
		populateHiringProcessSteps();
	</logic:equal>
}

function onSavePositionStepResponse(request) {
	xmlFile = request.responseXML;
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;
	 	if(isErrorXml(xmlFile)){	 		
	 		errorElem = parseErrors(xmlFile);
			alert(errorElem);
			return;
		}	else {
			if (clone.stepId == 0) {
				editHiringProcess();
			}
			populateHiringProcessSteps();			
		//	editHiringProcess();
			id = parseIds(xmlFile);
			_steps[clone.index].setStepId(id);
			clone = null;
		}		
}

function cloneStep(original) {
	var clone = new Step();
	clone.setIndex(original.index);
	clone.setStepId(original.stepId);
	clone.setStepMasterId(original.stepMasterId);
	clone.setIsDefault(original.isDefault);
	clone.setStepTitle(original.stepTitle);
	clone.setAssignedTo(original.assignedTo);
	clone.setIsScheduled(original.isScheduled);
	clone.setScheduledBy(original.scheduledBy);
	clone.setIsInterviewerCanConfirm(original.isInterviewerCanConfirm);	
	clone.setIsDecisionMakerSameAsAssignedTo(original.isDecisionMakerSameAsAssignedTo);	
	clone.setDecisionMaker(original.decisionMaker);
	clone.setIsOptional(original.isOptional);
	clone.setStepLevel(original.stepLevel);
	clone.setFeedbackFormId(original.feedbackFormId);
	clone.setIsNotifyToCandidate(original.isNotifyToCandidate);
	clone.setAssignedToUsers(original.assignedToUsers);
	clone.setScheduledByUsers(original.scheduledByUsers);
	clone.setDecisionMakerUsers(original.decisionMakerUsers);
//	clone.setApplicantFeedbackFormId(original.applicantFeedbackFormId);
	
	messages = new Array();
	for (i = 0; i < original.messages.length; i++) {
		message = new StatusMessage();
		message.setMessageId(original.messages[i].messageId);
		message.setMessage(original.messages[i].message);
		message.setIsDefault(original.messages[i].isDefault);	
		messages[messages.length]=message;
	}
	clone.setMessages(messages);
	return clone;
}

function getStepData(id){
	
}
// End: Edit Hiring Process.

// Start: Add Hiring Process.
function backToPositionHome() {
   if(document.positionForm.positionStatus.value==<%=PositionConstants.POSITION_STATUS_TEMPLATE%>){
		window.location=uncache("positionTemplate.do?mode=positionTemplatesHome");
	}else{
		document.positionForm.mode.value='<%=PositionConstants.MODE_POSITION_HOME%>';
		document.positionForm.submit();
	}
	return true;
	
}


/**
 *START "Copy Hiring Process"
 *Existing Hiring Process deleted and copied only if there are no canidates in process.
 *For Templates there is no check done.
 *
 * * */

function checkCandidatesInprocess(type){
	if(type=='positions'){
		<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
			var pars = "mode=checkCandidatesInProcess&positionId=" + document.positionForm.positionId.value;
			var myAjax = ajaxCall("position.do",'post',pars,onCheckShowPositionSelectBox, reportError);
		</logic:notEqual>
		<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
			showPositionSelectBox();
		</logic:equal>	
	}else if(type=='templates'){
		<logic:notEqual name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
			var pars = "mode=checkCandidatesInProcess&positionId=" + document.positionForm.positionId.value;
			var myAjax = ajaxCall("position.do",'post',pars,onCheckShowTemplateSelectBox, reportError);
		</logic:notEqual>
		<logic:equal name="positionForm" property="positionStatus" value="<%=PositionConstants.POSITION_STATUS_TEMPLATE%>">
			showTemplateSelectBox();
		</logic:equal>
	}
}

function onCheckShowPositionSelectBox(request){
	xmlFile = request.responseXML;
	var errorMsg = '';
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;  	
  	if(xmlFile && isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
		alert('<bean:message key="position.hiring_process.error.cannot_change" />');
		return;
	}else{
		showPositionSelectBox();
  	}
}

function onCheckShowTemplateSelectBox(request){
	xmlFile = request.responseXML;
	var errorMsg = '';
  	if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>'))return;  	
  	if(isErrorXml(xmlFile)){
  		errors = getErrors(xmlFile);
		alert('<bean:message key="position.hiring_process.error.cannot_change" />');
		return;
	}else{
		showTemplateSelectBox();	
  	}
}

function showPositionSelectBox(){
	var status = <%=PositionConstants.POSITION_STATUS_OPENED%>+","+
	<%=PositionConstants.POSITION_STATUS_CLOSED%>+","+
	<%=PositionConstants.POSITION_STATUS_INPROCESS%>+","+
	<%=PositionConstants.POSITION_STATUS_REJECTED%>+","+
	<%=PositionConstants.POSITION_STATUS_HOLD%>;
	var url="position.do?mode=showPositionSelectBox&subMode="+status;
	showInPopUp(url,650,300,copyHiringProcess,true);
}

function showTemplateSelectBox(){
	var url="position.do?mode=showPositionSelectBox&subMode=<%=PositionConstants.POSITION_STATUS_TEMPLATE%>";
	showInPopUp(url,650,300,copyHiringProcess,true);
} 

function copyHiringProcess(id) {
	document.positionForm._positionId.value = id;
	document.positionForm.mode.value='<%=PositionConstants.MODE_COPY_HIRING_PROCESS%>';
	document.positionForm.submit();
	return true;
}

/**
 * 
 *END "Copy Hiring Process"
 *
 * * */

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

</script>