<%@page import="com.talentPool.common.properties.GlobalConstants"%>
<%@page import="com.talentPool.common.properties.GlobalApplicationProperties"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.talentPool.common.utils.Utils"%>
<%@page import="com.talentPool.common.utils.CommonUtils"%>
<%@page import="com.talentPool.common.properties.TPApplicationProperties"%>
<%@page import="com.talentPool.selectionProcess.SelectionProcessConstants"%>
<%@page import="com.talentPool.selectionProcess.dataobject.SelectionProcessData"%>
<%@page import="com.talentPool.positions.dataobject.StepData"%>

<%@page import="com.talentPool.applicant.manager.ImportConfigurationManager"%>
<%@page import="com.talentPool.user.manager.PermissionSet"%><link rel="stylesheet" type="text/css" href="themes/default/popupiframe.css">
<link rel="stylesheet" type="text/css" href="themes/default/CalendarPopup.css">
<link rel="stylesheet" type="text/css" href="themes/default/timePopUp.css">

<script language="JavaScript" src="js/commonFunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/ajaxfunctions.js" type="text/javascript"></script>
<script language="JavaScript" src="js/scripta/lib/prototype.js" type="text/javascript"></script>	
<script language="JavaScript" src="js/scripta/src/effects.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectoption.js" type="text/javascript"></script>
<script language="JavaScript" src="js/selectbox/selectbox.js" type="text/javascript"></script>
<script language="JavaScript" src="js/criteriapane.js" type="text/javascript"></script>
<script language="JavaScript" src="js/submodal/common.js"></script>
<script language="JavaScript" src="js/submodal/subModal.js"></script>
<script language="JavaScript" src="js/utils/updater.js" type="text/javascript"></script>  
<script LANGUAGE="JavaScript" SRC="js/calender/CalendarPopup.js"></script>
<script language="JavaScript" src="js/calender/dateFormatter.js" type="text/javascript"></script>
<script language="JavaScript" src="js/calender/TimePopUp.js" type="text/javascript"></script>
<script language="JavaScript" src="js/checkboxlist/checkboxlist.js" type="text/javascript"></script>

<script language="JavaScript">
var commonIds = new Array();
</script>
<% 
	ArrayList feedback = (ArrayList)request.getAttribute("feedback");
	String jSCommonStepsArray = (String)request.getAttribute("jSCommonStepsArray");
	ArrayList allPositions = (ArrayList)request.getAttribute("allPositions");
	PermissionSet permissionSet 		= (PermissionSet)request.getSession(false).getAttribute("permissionSet");
	boolean ctcOfferedViewable 			= ImportConfigurationManager.isCTCOfferedViewable(permissionSet);
	boolean basicOfferedViewable 		= ImportConfigurationManager.isBasicOfferedViewable(permissionSet);
	boolean designationOfferedViewable 	= ImportConfigurationManager.isDesignationOfferedViewable(permissionSet);
	boolean levelOfferedViewable 		= ImportConfigurationManager.isLevelOfferedViewable(permissionSet);
	boolean inputSalaryVariableViewable	= ImportConfigurationManager.isInputSalaryVariableViewable(permissionSet);
	pageContext.setAttribute("ctcOfferedViewable",ctcOfferedViewable);
	pageContext.setAttribute("basicOfferedViewable",basicOfferedViewable);
	pageContext.setAttribute("designationOfferedViewable",designationOfferedViewable);
	pageContext.setAttribute("levelOfferedViewable",levelOfferedViewable);
	pageContext.setAttribute("inputSalaryVariableViewable",inputSalaryVariableViewable);
%>
<div class="contentDivPop">
	<logic:present name="errors" scope="request">             
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
	</logic:present> 
	<html:form action="/selectionProcess">
	<html:hidden property="mode" value="saveBulkFeedback"/>
	<html:hidden property="applicantId" />
	<html:hidden property="sessionId" />
	<html:hidden property="tab" />
	<table cellpadding="0" cellspacing="0" width="100%">
	<tr><td style="padding-bottom: 2px;">	
	<logic:equal value="<%=SelectionProcessConstants.SCREEN_TYPE_FWD%>" name="selectionProcessForm" property="screenType">
	<div class="popupTop" style="border: 1px solid #ccc;border-bottom: 0px; padding:4px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="4" class="tblPop">
		    <tr>
		       	<td style="width:210px;"><B><bean:message key="bulkfeedback.title"/></B></td>
		    	<td style="width:120px;vertical-align: top;"><img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.DECISION_APPROVED%>" name="all" onclick="onTopRadioChange('all',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.approve"/>
				</td>
				<td style="width:80px;vertical-align: top;"><img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.STEP_REJECT %>" name="all" onclick="onTopRadioChange('all',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.reject"/>
				</td>
				<td style="width:80px;vertical-align: top;"><img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.STEP_ON_HOLD %>" name="all" onclick="onTopRadioChange('all',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.hold"/>
				</td>
				<td style="vertical-align: top;">
				   	<div style="display: none;margin: 0px; padding: 0px;margin-bottom: 5px;width:100%;" id="divmoveto">
				   		<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td class="Grey" style="width:135px;text-align: right;">Move to :&nbsp;</td>
						       	<td>
									<script type="text/javascript">
									    var opts = <%=jSCommonStepsArray%>;
									    var m = [new SelectOption('-1','<bean:message key="selection_feedback.label.selectStep"/>')];
										opts = m.concat(opts);
										var selectBoxSteps = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'212px', size:10, textboxclass:'Grey'});
				        				selectBoxSteps.setOnChangeHandler('commonStepChanged');
				        				document.write(selectBoxSteps.getHtml());
				        				selectBoxSteps.init();
									</script>
						       	</td>
						    </tr>
					    </table>
				    </div>	
				    <div style="margin: 0px; padding: 0px;margin-bottom: 0px;width:100%;" id="divcomment">
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="bulkfeedback.common.comments"/> :&nbsp;</td>						       	
						       	<td class="Grey"><input type="text" size="40" name="comment" id="comment" class="Grey" onkeyup="commentAll(this);"></td>						
						    </tr>
					   	</table>
				   	</div>				   	
				</td>
			</tr>
	   	</table>
	</div>
	</logic:equal>	
	<logic:equal value="<%=SelectionProcessConstants.SCREEN_TYPE_POS%>" name="selectionProcessForm" property="screenType">
	<div class="popupTop" style="border: 1px solid #ccc;border-bottom: 0px; padding:4px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="4" class="tblPop">
		    <tr>
		       	<td style="width:210px;"><B><bean:message key="bulkfeedback.title"/></B>
		       </td>
		    	<td style="width:120px;vertical-align: top;">
				</td>
				<td style="width:80px;vertical-align: top;">
				</td>
				<td style="width:80px;vertical-align: top;">
				</td>
				<td style="vertical-align: top;">
				   	<div style="margin: 0px; padding: 0px;margin-bottom: 5px;width:100%;" id="divpos">
				   		<table border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
						       	<td class="Grey" style="width:135px;text-align: right;">Position:&nbsp;</td>
						       	<td>
									<script type="text/javascript">
									    var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties(allPositions,"positionId","positionTitle")%>;
									    var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
										opts = m.concat(opts);
										var selectBoxPosition = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'212px', size:10, textboxclass:'Grey'});
										selectBoxPosition.setOnChangeHandler('allPositionChanged');
				        				document.write(selectBoxPosition.getHtml());
				        				selectBoxPosition.init();
									</script>
						       	</td>
						    </tr>
					    </table>
				    </div>	
				    <div style="margin: 0px; padding: 0px;margin-bottom: 0px;width:100%;" id="divstep">
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
						    <tr>
				              <td class="Grey" style="width:135px;text-align: right;"><bean:message key="selection_feedback.label.step"/>:&nbsp;</td>
				              <td>
			                    <script type="text/javascript">
								    var opts = new Array();	
									opts[0] = new SelectOption('-1','<bean:message key="selection_feedback.label.selectStep"/>');
									selectBoxPositionStep = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'212px', size:10});
									selectBoxPositionStep.setOnChangeHandler('allStepChanged');
			        				document.write(selectBoxPositionStep.getHtml());
			        				selectBoxPositionStep.init();
									</script>
					  			</td>
				           </tr>
					   	</table>
				   	</div>				   	
				</td>
			</tr>
	   	</table>
	</div>
	</logic:equal>
	<logic:equal value="<%=SelectionProcessConstants.SCREEN_TYPE_CAT%>" name="selectionProcessForm" property="screenType">
	<div class="popupTop" style="border: 1px solid #ccc;border-bottom: 0px; padding:4px;">
		<table width="100%" border="0" cellspacing="0" cellpadding="4" class="tblPop">
			 <tr>
		       	<td style="width:210px;"><B><bean:message key="bulkfeedback.title"/></B>
		       </td>
		    	<td style="width:120px;vertical-align: top;"><img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.STEP_ATTENDED%>" name="all" onclick="onTopRadioChange('all',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.attended"/>
				</td>
				<td style="width:200px;vertical-align: top;"><img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.STEP_REPEAT%>" name="all" onclick="onTopRadioChange('all',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="moveupdown.label.move_to_not_attended"/> - <bean:message key="common.reschedule"/>
				</td>
				<td style="width:150px;vertical-align: top;"><img src="images/radiobutton.gif" id="<%=SelectionProcessConstants.STEP_NOT_ATTENDED%>" name="all" onclick="onTopRadioChange('all',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="moveupdown.label.move_to_not_attended"/> - <bean:message key="common.reject"/>
				</td>
				<td></td>
			</tr>	
		</table>
	</div>		
	</logic:equal>		
	</td></tr>
	</table>
	<table cellpadding="0" cellspacing="0" width="100%">
		<% 
			String prevPositionId="";
			for(int i=0; i<feedback.size(); i++){
				SelectionProcessData data = (SelectionProcessData)feedback.get(i);
				String positionId = data.getPositionId();
				String screenType = (String)data.getAttribute("screenType");
				ArrayList<StepData> toSteps = (ArrayList<StepData>)data.getAttribute("toSteps");
				String jSToStepsArray = (String)data.getAttribute("jSToStepsArray");
				String offeredStepIds = (String)data.getAttribute("offeredStepIds");
				String joinedStepIds = (String)data.getAttribute("joinedStepIds");
				String jsIntervierwersArray = (String)data.getAttribute("jsIntervierwersArray");
				ArrayList positions = (ArrayList)data.getAttribute("positions");
				
				jSToStepsArray = Utils.isBlankOrNull(jSToStepsArray)? "new Array()":jSToStepsArray;
				
				jsIntervierwersArray = Utils.isBlankOrNull(jsIntervierwersArray)? "new Array()":jsIntervierwersArray;
				
				String commonId=""+data.getApplicantId();
				
				if(!prevPositionId.equals(positionId)){
					prevPositionId = positionId;
					if(i>0){
		%>
		<tr>
			<td style="border-left: 1px solid #99CC33;border-right: 1px solid #99CC33;height:20px;">&nbsp;
			</td>
		</tr>				
		<% 			} %>
		<tr>
			<td>	
				<div class="popupTop" style="border: 1px solid #ccc;border-bottom: 0px; padding:4px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="vpTopTab">
			    <tr>
			       	<td style="width:380px;padding-left: 4px;"><%
			       	String row = Utils.escapeHTML(data.getPositionTitle()) + " - " + Utils.escapeHTML(data.getPositionStepTitle()) ;
			       	%><%=row %></td>
			    </tr>
			   	</table>
				</div>
			</td>
		</tr>				
		<%		}else{	%>
		<tr>
			<td style="border-bottom: 1px dotted #ccc;border-left: 1px solid #99CC33;border-right: 1px solid #99CC33;line-height: 1px;">&nbsp;</td>
		</tr>				
		<%		} %>
		<tr>
			<td style="border-left: 1px solid #99CC33;border-right: 1px solid #99CC33;padding: 2px; " >	
				<logic:equal value="<%=SelectionProcessConstants.SCREEN_TYPE_POS%>" name="selectionProcessForm" property="screenType">
					<input type="hidden" name="decision_<%=commonId %>" id="decision_<%=commonId %>" value="<%=SelectionProcessConstants.DECISION_POSITION %>" />
				</logic:equal>
				<logic:notEqual value="<%=SelectionProcessConstants.SCREEN_TYPE_POS%>" name="selectionProcessForm" property="screenType">
					<input type="hidden" name="decision_<%=commonId %>" id="decision_<%=commonId %>" value="" />
				</logic:notEqual>
				
				<input type="hidden" name="tostep_<%=commonId %>" id="tostep_<%=commonId %>" value=""/>
				<input type="hidden" name="fromstep_<%=commonId %>" id="fromstep_<%=commonId %>" value="<%=data.getCurrentStepId() %>"/>
				<input type="hidden" name="positionid_<%=commonId %>" id="positionid__<%=commonId %>" value="<%=data.getPositionId() %>"/>
				<input type="hidden" name="communicationid_<%=commonId %>" id="communicationid_<%=commonId %>" value="<%=data.getCommunicationId() %>"/>
				<input type="hidden" name="appointmentid_<%=commonId %>" id="appointmentid_<%=commonId %>" value="<%=data.getAppointmentId() %>"/>
				<input type="hidden" name="inetrviewerid_<%=commonId %>" id="inetrviewerid_<%=commonId %>" value=""/>
				<input type="hidden" name="newposid_<%=commonId %>" id="newposid_<%=commonId %>" value=""/>
				<input type="hidden" name="newstepid_<%=commonId %>" id="newstepid_<%=commonId %>" value=""/>
				<input type="hidden" name="feedbackformid_<%=commonId %>" id="feedbackformid_<%=commonId %>" value="<%=data.getFeedbackFormId() %>"/>
				
				<script language="JavaScript">
					commonIds[commonIds.length] = '<%=commonId %>';
				</script>
				
				<table class="tblPop" width="100%" cellspacing="0" cellpadding="4">
				<tr>
					<td style="width:210px; vertical-align: top;"><a href="#" onclick="onClickApplicant(<%=data.getApplicantId() %>);return false;"><%=Utils.escapeHTML(data.getApplicantName()) %></a><br/><%=Utils.escapeHTML(data.getApplicantExperience()) %>
					<% 
						String currentEmp = Utils.escapeHTML(data.getApplicantCurrentEmployer());
						if(!Utils.isBlankOrNull(currentEmp)){
							currentEmp = currentEmp.length()>16?currentEmp.substring(0,10) +"...":currentEmp;
					%><%=" - " + currentEmp %>
					<%} %>
					</td>
					<% if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_FWD)){%>
					<td style="width:120px;vertical-align: top;"><img src="images/radiobutton.gif" id="rdo_<%=commonId+"_x" %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.approve"/>
					</td>
					<td style="width:80px;vertical-align: top;"><img src="images/radiobutton.gif" id="rdo_<%=commonId+"_"+SelectionProcessConstants.STEP_REJECT %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.reject"/>
					</td>
					<td style="width:80px;vertical-align: top;"><img src="images/radiobutton.gif" id="rdo_<%=commonId+"_"+SelectionProcessConstants.STEP_ON_HOLD %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.hold"/>
					</td>
					<%} else if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_SCH)){%>
					<td style="width:120px;vertical-align: top;"><img src="images/radiobutton.gif" id="rdo_<%=commonId+"_s" %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.schedule"/>
					</td>
					<td style="width:80px;vertical-align: top;"><img src="images/radiobutton.gif" id="rdo_<%=commonId+"_"+SelectionProcessConstants.STEP_REJECT %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.reject"/>
					</td>
					<td style="width:80px;vertical-align: top;">&nbsp;
					</td>
					<%} else if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_CAT)){%>
					<td style="width:120px;vertical-align: top;">
					<img src="images/radiobutton.gif" id="rdo_<%=commonId+"_"+SelectionProcessConstants.STEP_ATTENDED %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.attended"/><br/>
					</td>
					<td style="width:200px;vertical-align: top;">
					<img src="images/radiobutton.gif" id="rdo_<%=commonId+"_"+ SelectionProcessConstants.STEP_REPEAT%>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="moveupdown.label.move_to_not_attended"/> - <bean:message key="common.reschedule"/> <br/>
					</td>
					<td style="width:150px;vertical-align: top;">
					<img src="images/radiobutton.gif" id="rdo_<%=commonId+"_"+SelectionProcessConstants.STEP_NOT_ATTENDED %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="moveupdown.label.move_to_not_attended"/> - <bean:message key="common.reject"/> 
					</td>
					<% }else if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_NIR)){%>
					<td style="width:120px;vertical-align: top;">&nbsp;
					</td>
					<td style="width:80px;vertical-align: top;"><img src="images/radiobutton.gif" id="rdo_<%=commonId+"_"+SelectionProcessConstants.STEP_REJECT %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.reject"/>
					</td>
					<td style="width:80px;vertical-align: top;">&nbsp;
					</td>
					<% }else if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_HLD)){%>
					<td style="width:120px;vertical-align: top;">&nbsp;
					</td>
					<td style="width:80px;vertical-align: top;">&nbsp;
					</td>
					<td style="width:80px;vertical-align: top;"><img src="images/checkedradiobutton.gif" id="rdo_<%=commonId+"_"+SelectionProcessConstants.STEP_ON_HOLD %>" name="rdo_<%=commonId %>" onclick="onRadioChange('rdo_<%=commonId %>',this)" style="margin-bottom: -1px;"/>&nbsp;<bean:message key="common.hold"/>					
					</td>
					<% }else if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_POS)){%>
					<td style="width:120px;vertical-align: top;">&nbsp;
					</td>
					<td style="width:80px;vertical-align: top;">&nbsp;
					</td>
					<td style="width:80px;vertical-align: top;">&nbsp;					
					</td>
					<%}else { %>
					<td style="width:280px;vertical-align: top;">&nbsp;<bean:message key="bulkfeedback.action.no_feedbcak_required"/>
					</td>
					<%} %>
					
					
					<td class="Grey" style="text-align: left;vertical-align: top;">				
						<div style="display: none; margin: 0px; padding: 0px;margin-bottom: 5px;width:100%;" id="divmoveto_<%=commonId %>">
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;">Move to :&nbsp;</td>
						       	<td>
									<script type="text/javascript">
									    var opts = <%=jSToStepsArray%>;
										var selectBoxSteps_<%=commonId %> = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'212px', size:10, textboxclass:'Grey', id:'<%=commonId %>', offerstepids:'<%=offeredStepIds%>', joiningstepids:'<%=joinedStepIds%>'});
				        				selectBoxSteps_<%=commonId %>.setOnChangeHandler('selectBoxStepChanged');
				        				document.write(selectBoxSteps_<%=commonId %>.getHtml());
				        				selectBoxSteps_<%=commonId %>.init();
				        				$('tostep_<%=commonId %>').value=selectBoxSteps_<%=commonId %>.getSelectedId();
									</script>
						       	</td>
						    </tr>
						   	</table>
						</div>
						
						<logic:equal value="<%=SelectionProcessConstants.SCREEN_TYPE_POS%>" name="selectionProcessForm" property="screenType">						
						<div style="display:block;margin: 0px; padding: 0px;margin-bottom: 5px;width:100%;" id="divposition_<%=commonId %>">
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;">Position:&nbsp;</td>
						       	<td>
									<script type="text/javascript">
									    var opts = <%=CommonUtils.getListJavaScriptArrayWithProperties(positions,"positionId","positionTitle")%>;
									    var m = [new SelectOption('-1','<bean:message key="common.selectlist.select"/>')];
										opts = m.concat(opts);
										var selectBoxPosition_<%=commonId %> = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'212px', size:10, textboxclass:'Grey', id:'<%=commonId %>'});
										selectBoxPosition_<%=commonId %>.setOnChangeHandler('selectedPositionChanged');
				        				document.write(selectBoxPosition_<%=commonId %>.getHtml());
				        				selectBoxPosition_<%=commonId %>.init();
									</script>
						       	</td>
						    </tr>
						     <tr>
					              <td class="Grey" style="width:135px;text-align: right;"><bean:message key="selection_feedback.label.step"/>:&nbsp;</td>
					              <td style="padding-top: 4px;">
				                    <script type="text/javascript">
									    var opts = new Array();	
										opts[0] = new SelectOption('-1','<bean:message key="selection_feedback.label.selectStep"/>');
										selectBoxPositionStep_<%=commonId %> = new SelectBox(opts,'','images/btn_dropdown.gif',{namesonly:false, width:'212px', size:10, id:'<%=commonId %>'});
										selectBoxPositionStep_<%=commonId %>.setOnChangeHandler('selectedPositionStepChanged');
				        				document.write(selectBoxPositionStep_<%=commonId %>.getHtml());
				        				selectBoxPositionStep_<%=commonId %>.init();
										</script>
						  			</td>
					           </tr>
						   	</table>
						</div>
						</logic:equal>
						
						<div style="display: none; margin: 0px; padding: 0px;margin-bottom: 5px;width:100%;" id="divjoiningdate_<%=commonId %>">
							<table border="0" cellspacing="0" cellpadding="0" width="100%">
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="common.joining_date"/>:&nbsp;</td>
						       	<td>
									<input type="text" size="12" maxlength="10" onblur="getFormattedDate(this);" name="joiningDate_<%=commonId %>" id="joiningDate_<%=commonId %>" value="<%=data.getJoiningDate() %>"/>
									<img src="images/ico_cal.gif" style="height:16px;margin-bottom:-3px;cursor:hand;" onclick="timePopUp.hidePopup();popUpCal.select(document.getElementById('joiningDate_<%=commonId %>'),'joiningDate_<%=commonId %>','dd/MM/yyyy'); return false;" />
						       	</td>
						    </tr>
							<%
							//SelectionProcessForm selectionProcessForm= (SelectionProcessForm)request.getAttribute("actionForm");
							String fldName="ctcOffered_"+commonId;
							%>
	         				<logic:equal value="true" name="ctcOfferedViewable" >
		         				<tr>
							       	<td class="Grey" style="width:135px;text-align: right;">CTC Offered:&nbsp;</td>
							       	<td>
									<input type="text" name="<%="ctcOffered_"+commonId%>" id="<%="ctcOffered_"+commonId%>" value="<%=Utils.isBlankOrNull(data.getOfferedCtc())? "":data.getOfferedCtc()%>" size="12" maxlength="10" onblur="validNumber(this);"/>
							       	</td>
							    </tr>
							</logic:equal>
							<logic:equal value="true" name="basicOfferedViewable" >
								<tr>
							       	<td class="Grey" style="width:135px;text-align: right;">Basic Offered:&nbsp;</td>
							       	<td>
									<input type="text" name="<%="basicOffered_"+commonId%>" id="<%="basicOffered_"+commonId%>" value="<%=Utils.isBlankOrNull(data.getBasicOffered())? "":data.getBasicOffered()%>" size="12" maxlength="10" onblur="validNumber(this);"/>
							       	</td>
							   	</tr>
							</logic:equal>
							<logic:equal value="true" name="designationOfferedViewable" >
								<tr>
							       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="selection_feedback.label.designation_offered"/>:&nbsp;</td>
							       	<td>
									<input type="text" name="<%="designationOffered_"+commonId%>" id="<%="designationOffered_"+commonId%>" value="<%=Utils.isBlankOrNull(data.getApplicantDesignationOffered())? "":data.getApplicantDesignationOffered()%>" size="12" maxlength="50"/>
							       	</td>
						    	</tr>
							</logic:equal>
							<logic:equal value="true" name="levelOfferedViewable" >
								<tr>
							       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="selection_feedback.label.level_offered"/>:&nbsp;</td>
							       	<td>
									<input type="text" name="<%="levelOffered_"+commonId%>" id="<%="levelOffered_"+commonId%>" value="<%=Utils.isBlankOrNull(data.getApplicantLevelOffered())? "":data.getApplicantLevelOffered()%>" size="12" maxlength="20" />
							       	</td>
							    </tr>
							</logic:equal>	
							<logic:equal value="true" name="inputSalaryVariableViewable" >
								<tr>
							       	<td class="Grey" style="width:135px;text-align: right;"><%=GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SALARY_VARIABLE_INPUT_SALARY_LABEL) %>:&nbsp;</td>
							       	<td>
									<input type="text" name="<%="inputSalaryVariable_"+commonId%>" id="<%="inputSalaryVariable_"+commonId%>" value="<%=Utils.getBlankIfNull(data.getInputSalaryVariable())%>" size="12" maxlength="20" />
							       	</td>
							    </tr>
							</logic:equal>	
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="selection_feedback.label.employee_code"/>:&nbsp;</td>
						       	<td>
								<input type="text" name="<%="employeeCode_"+commonId%>" id="<%="employeeCode_"+commonId%>" value="<%=Utils.isBlankOrNull(data.getEmployeeCode())? "":data.getEmployeeCode()%>" size="12" onblur="validNumber(this);"/>
						       	</td>
						    </tr>				    
						   	</table>
						</div>
						<div style="margin: 0px; padding: 0px;margin-bottom: 0px;width:100%;" id="divcomment_<%=commonId %>">
						<% if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_FWD) || 
								screenType.equals(SelectionProcessConstants.SCREEN_TYPE_NIR) ||
								screenType.equals(SelectionProcessConstants.SCREEN_TYPE_SCH) ||
								screenType.equals(SelectionProcessConstants.SCREEN_TYPE_HLD) ){%>
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="common.comments"/> :&nbsp;</td>						       	
						       	<td class="Grey"><input type="text" size="40" name="comment_<%=commonId %>" id="comment_<%=commonId %>" class="Grey" ></td>
						    </tr>
					   	</table>
					   	<%}else{ %>
					   	<%} %>
					   	</div>
						<div style="display: none; margin: 0px; padding: 0px;margin-bottom: 0px;width:100%;" id="divschedule_<%=commonId %>">
						<table border="0" cellspacing="0" cellpadding="0" width="100%">
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="common.date"/>:&nbsp;</td>
								<td>
									<input type="text" size="12" maxlength="10" onblur="getFormattedDate(this);" name="appointmentDate_<%=commonId %>" id="appointmentDate_<%=commonId %>"/>
									<img src="images/ico_cal.gif" style="height:16px;margin-bottom:-3px;cursor:hand;" onclick="timePopUp.hidePopup();popUpCal.select(document.getElementById('appointmentDate_<%=commonId %>'),'appointmentDate_<%=commonId %>','dd/MM/yyyy'); return false;" />
					
									<input type="text" size="9" maxlength="8" onblur="getFormattedTime(this);" name="fromTime_<%=commonId %>" id="fromTime_<%=commonId %>"/>
							 		<img src="images/clock.gif" style="height:16px;margin-bottom:-3px;cursor:hand;" onclick="timePopUp.showTime(document.getElementById('fromTime_<%=commonId %>'), 'fromTime_<%=commonId %>'); return false;" />
								</td>
						    </tr>
						    <tr>
						       	<td class="Grey" style="width:135px;text-align: right;"><bean:message key="new_appointment.label.interviewer"/>&nbsp;</td>
						       	<td>
							       	<script type="text/javascript">
							       		var opts = <%=jsIntervierwersArray%>;
					                    checkboxListAttendee_<%=commonId %> = new CheckBoxList(opts,'',{namesonly:false, layerclass:'checkboxlistdiv', width:'190px', size:10, imageclass:'chkboxclass', uncheckedimg:'images/checkboxunchecked.gif', checkedimg:'images/checkboxchecked.gif', id:'<%=commonId %>'});
					                    document.write(checkboxListAttendee_<%=commonId %>.getHtml());
					                    checkboxListAttendee_<%=commonId %>.init();
					             	 </script>
			             	 
						       	</td>
						    </tr>
					   	</table>
					   	</div>
					</td>
				</tr>
				</table>
			</td>
		</tr>
		<% if(i==feedback.size()-1){ %>
		<tr>
			<td style="border-left: 1px solid #99CC33;border-right: 1px solid #99CC33;border-bottom: 1px solid #99CC33;height:10px;">&nbsp;
			</td>
		</tr>				
		<% } %>				
		<% } %>
		<tr>
			<td>
				<div class="navBtn" style="float: right; padding-top: 10px;">
					<a href="#" style="width:60px;margin-right:5px;" class="active" onclick="javascript: submitForm();return false;" id="submit"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.submit"/></a>
					<a href="#" style="width:60px;" class="active" onclick="javascript: onCancel();"><span class="rightC"></span><span class="leftC"></span><bean:message key="common.cancel"/></a>
				</div>
			</td>
		</tr>
	</table>
	</html:form>
	<br/><br/><br/>
</div>
<DIV id="calDiv" style="position:absolute;background:#FFFFFF;z-index:1000;" ></DIV>
<DIV id="timePopUpDiv" style="position:absolute;z-index:500;background-color:#eee;display:none;" ></DIV>  

<script type="text/javascript">
var timePopUp = new TimePopUp("timePopUpDiv");

var popUpCal = new CalendarPopup("calDiv"); 
popUpCal.showNavigationDropdowns();

//DATE FORMATTER CODE AND FUNCTIONS
var dtf = new DateFormatter();
dtf.setDisplayFormat('DD/MM/YYYY');

function getFormattedDate(obj){
	if(obj.value != '' && obj.value!='dd/mm/yyyy'){
		if(!dtf.checkDate(obj)){
			obj.select();
			alert("Invalid <bean:message key="common.date"/>");
			obj.focus();
			return false;
		}else {
			return true;
		}
	}
}
	
	
function getFormattedTime(obj) {
	  val = obj.value.trim();
	  var errFlag=false;
	  timeSlotStr = '';
	  if(val!=''){
	    //(T = /^(\d\d|\d)(:|.|-)(\d\d|\d)\s?(([ap])\.?m\.?)?$/i.exec(val)
		 var T;
	    if ((T = /^(\d\d|\d)(:|.|-)(\d\d|\d)\s?(([ap])\.?m\.?)?$/i.exec(val)) == null) {
	        errFlag=true;
	    }
	    if (!errFlag && T[1] > 23) {
	      errFlag = true;
	    }
	    if (!errFlag && T[3] >= 60) {
	      errFlag = true;
	    }
	    if (!errFlag && T[4] == '') {
	      if (T[1] > 12) {
	        T[1] = T[1] - 12;
	        timeSlotStr = 'PM';
	      } else {
	        if (T[1] >= 1 && T[1] <= 8) {
	          timeSlotStr = 'PM';
	        } else if (T[1] > 8 && T[1] < 12) {
	          timeSlotStr = 'AM';
	        } else {
	          timeSlotStr = 'PM';
	        }
	      }
	    } else if (!errFlag) {
	      timeSlotStr = T[4].toUpperCase();
	    }
	    if (!errFlag) {
	      if (T[1].length == 1) {
	        T[1] = '0' + T[1];
	      }
	      if (T[3].length == 1) {
	        T[3] = '0' + T[3];
	      }
	      obj.value = T[1] + ':' + T[3] + ' ' + timeSlotStr;
	    } else {
	      alert("Please enter <bean:message key="common.time"/> in hh:mm format.");
	      obj.focus();
	    }    
	  }
	  return true;  
}
function validNumber(fieldObj){
	//var regExpNumber = /^\d*[0-9]*([,]*\d*[0-9])*\d*[0-9]$/;
	var regExpNumber = '^\d*[0-9]*[.]*\d*[0-9]*\d*[0-9]$';
	var val;		
	if(fieldObj.value.trim()==""){				
		val= true;
	}else if(!fieldObj.value.match(regExpNumber)){		
		alert("<bean:message key="common.please_enter_valid_number" />");
		fieldObj.focus();
		val= false;
	}else{
		val= true;
	}	
	return val;
}

function onClickApplicant(aId){
	url = "selectionProcess.do?mode=viewOriginalResume&applicantId=" + aId;
	window.open(url,aId,'width=1024,height=768,left=0,top=0,scrollbars=yes,resizable=yes,status=no');
	return false;
}

function selectBoxStepChanged(idx, ctl){
	var selectedGroupId = ctl.options.id;
	var selId = ctl.getSelectedId();
	$('tostep_'+selectedGroupId).value=selId;
	
	var joiningDateStepIds = ctl.options.offerstepids + "," + ctl.options.joiningstepids;
	
	if(existInIds(selId,joiningDateStepIds)){
		Element.show('divjoiningdate_'+selectedGroupId);
	}else{
		Element.hide('divjoiningdate_'+selectedGroupId);
	}
}

function existInIds(toStepId, stepIds){
	var idExist= false;
	if(stepIds.length>1){
		var ids = stepIds.split(',');
		for(var i=0; i<ids.length; i++){
			if(toStepId==ids[i]){
				idExist = true;
				break;
			}
		}
	}
	return idExist;
}

function selectBoxLocationChanged(idx, ctl){
	var selectedGroupId = ctl.options.id;
	
}

var checkedRadio="images/checkedradiobutton.gif";
var uncheckedRadio="images/radiobutton.gif";

function onTopRadioChange(radioGroupName, elm){
	var selId = elm.id;

	var imgs = document.getElementsByName(radioGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id== selId) {
				theImage.src = checkedRadio;
				
			}else{
				theImage.src = uncheckedRadio;
			}
	}
	
	if(selId == '<%=SelectionProcessConstants.DECISION_APPROVED%>'){
		Element.show('divmoveto');
	}else{
		<logic:equal value="<%=SelectionProcessConstants.SCREEN_TYPE_FWD%>" name="selectionProcessForm" property="screenType">
		Element.hide('divmoveto');
		</logic:equal>
	}

	if(selId == '<%=SelectionProcessConstants.DECISION_POSITION%>'){
		Element.show('divpos');
		Element.show('divstep');
	}else{
		<logic:equal value="<%=SelectionProcessConstants.SCREEN_TYPE_POS%>" name="selectionProcessForm" property="screenType">
		Element.hide('divpos');
		Element.hide('divstep');
		</logic:equal>
	}
	
	var len=commonIds.length;
	for(j=0;j<len;j++){
		var cId = commonIds[j];
		var name = "rdo_"+cId;
		var elmt = document.getElementById('rdo_'+cId+'_'+selId);
		if(elmt){
			onRadioChange(name, elmt)
		}	
	}

}

function commentAll(elm){
	var len=commonIds.length;
	for(j=0;j<len;j++){
		var cId = commonIds[j];	
		if(document.getElementById('comment_'+cId)){
			document.getElementById('comment_'+cId).value = elm.value;
		}
	}
}

function commonStepChanged(idx, ctl){
	var selId = ctl.getSelectedId();
	var commonStepName = ctl.getText(ctl.getSelectedIndex());
	if(selId!='-1'){
		var len=commonIds.length;
		for(j=0;j<len;j++){
			var cId = commonIds[j];	
			if(eval('selectBoxSteps_'+cId)){
				var obj = eval('selectBoxSteps_'+cId);
				obj.setSelected(obj.getIndexWithText(commonStepName));
			}
		}
	}
}

function allPositionChanged(idx, ctl){
	var selId = ctl.getSelectedId();
	var posName = ctl.getText(ctl.getSelectedIndex());
	if(selId!='-1'){
		var len=commonIds.length;
		for(j=0;j<len;j++){
			var cId = commonIds[j];	
			if(eval('selectBoxPosition_'+cId)){
				var obj = eval('selectBoxPosition_'+cId);
				obj.setSelected(obj.getIndexWithText(posName));
			}
		}
	}
	
	var pars = "mode=getStepsInXml&positionId=" + selId;
	var myAjax = ajaxCall("selectionProcess.do",'get',pars,updateAllSteps, reportError);
}


function updateAllSteps(request){
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }
  //First remove all options
  var opts = new Array();
  
  var steps = xmlFile.getElementsByTagName("steps")[0];
  var step= steps.getElementsByTagName("step");
  if(step != null) {
  	for(var i = 0; i < step.length; i++){  		
  		var id = step[i].getAttribute("id");
  		var title = step[i].firstChild.nodeValue;
  		opts[i] = new SelectOption(id, title);
  	}
  }
  var m = [new SelectOption('-1', '<bean:message key='selection_feedback.label.selectStep' />')];
  opts = m.concat(opts);
  var ctl = eval('selectBoxPositionStep');
  ctl.reInitialize(opts, '');
}

function allStepChanged(idx, ctl){
	var selId = ctl.getSelectedId();
	var stepName = ctl.getText(ctl.getSelectedIndex());
	if(selId!='-1'){
		var len=commonIds.length;
		for(j=0;j<len;j++){
			var cId = commonIds[j];	
			if(eval('selectBoxPositionStep_'+cId)){
				var obj = eval('selectBoxPositionStep_'+cId);
				obj.setSelected(obj.getIndexWithText(stepName));
			}
		}
	}
}

function onRadioChange(radioGroupName, elm){
	var selId = elm.id;
	var imgs = document.getElementsByName(radioGroupName);
	for (i = 0; i < imgs.length; i++) {
			var theImage = imgs[i];
			if (theImage.id== selId) {
				theImage.src = checkedRadio;
				
			}else{
				theImage.src = uncheckedRadio;
			}
	}
	var selectedGroupId = radioGroupName.substring(4,radioGroupName.length);
	
	if(selId.endsWith('<%=SelectionProcessConstants.DECISION_APPROVED%>')){
		Element.show('divmoveto_'+selectedGroupId);
		Element.show('divcomment_'+selectedGroupId);
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.DECISION_APPROVED%>';
		selectBoxStepChanged('',eval('selectBoxSteps_'+selectedGroupId));
	}else{
		Element.hide('divmoveto_'+selectedGroupId);
		Element.hide('divjoiningdate_'+selectedGroupId);
	}
	if(selId.endsWith('<%=SelectionProcessConstants.STEP_REJECT%>')){
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.STEP_REJECT%>';
		Element.show('divcomment_'+selectedGroupId);
	}
	if(selId.endsWith('<%=SelectionProcessConstants.STEP_ON_HOLD%>')){
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.DECISION_APPROVED%>';
		$('tostep_'+selectedGroupId).value='<%=SelectionProcessConstants.STEP_ON_HOLD%>';		
	}
	if(selId.endsWith('<%=SelectionProcessConstants.STEP_ATTENDED%>')){
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.STEP_ATTENDED%>';
		Element.show('divcomment_'+selectedGroupId);
	}
	if(selId.endsWith('<%=SelectionProcessConstants.STEP_REPEAT%>')){
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.STEP_REPEAT%>';
		Element.show('divcomment_'+selectedGroupId);
	}
	if(selId.endsWith('<%=SelectionProcessConstants.STEP_NOT_ATTENDED%>')){
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.STEP_NOT_ATTENDED%>';
		Element.show('divcomment_'+selectedGroupId);
	}
	if(selId.endsWith('<%=SelectionProcessConstants.DECISION_SCHEDULE%>')){
		Element.hide('divcomment_'+selectedGroupId);
		Element.show('divschedule_'+selectedGroupId);
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.DECISION_SCHEDULE%>';
	}else{
		Element.hide('divschedule_'+selectedGroupId);
	}
	if(selId.endsWith('<%=SelectionProcessConstants.DECISION_POSITION%>')){
		Element.show('divposition_'+selectedGroupId);
		Element.show('divcomment_'+selectedGroupId);
		$('decision_'+selectedGroupId).value='<%=SelectionProcessConstants.DECISION_POSITION%>';
		$('tostep_'+selectedGroupId).value='<%=SelectionProcessConstants.STEP_REJECT%>';	
	}
}

function selectedPositionChanged(idx, ctl) {
  var val = ctl.getSelectedId();
  var selectedGroupId = ctl.options.id;
  $('newposid_'+selectedGroupId).value=val;
  var pars = "mode=getStepsInXml&positionId=" + val+"&selectedGroupId="+selectedGroupId;
  var myAjax = ajaxCall("selectionProcess.do",'get',pars,updateSteps, reportError);
}

function updateSteps(request){
  xmlFile = request.responseXML;
  if(!isValidSession(xmlFile,'<%=TPApplicationProperties.getProperty("redirect_on_session_expired")%>')) {
    return;
  }
  //First remove all options
  var opts = new Array();
  
  var steps = xmlFile.getElementsByTagName("steps")[0];

  var selectedGroupId = steps.getElementsByTagName("selectedGroupId")[0];
  selectedGroupId = selectedGroupId.firstChild.nodeValue;
  
  var step= steps.getElementsByTagName("step");
  if(step != null) {
  	for(var i = 0; i < step.length; i++){  		
  		var id = step[i].getAttribute("id");
  		var title = step[i].firstChild.nodeValue;
  		opts[i] = new SelectOption(id, title);
  	}
  }
  var m = [new SelectOption('-1', '<bean:message key='selection_feedback.label.selectStep' />')];
  opts = m.concat(opts);
  var ctl = eval('selectBoxPositionStep_' + selectedGroupId);
  ctl.reInitialize(opts, '');
}

function selectedPositionStepChanged(idx, ctl){
	 var val = ctl.getSelectedId();
	 var selectedGroupId = ctl.options.id;
	 $('newstepid_'+selectedGroupId).value=val;
}

function submitForm(){
	showUpdater('submit',{setHeight: false, setWidth: false, offsetLeft: -50});
	if(validForm()){
		try{
			document.selectionProcessForm.submit();
		}catch(e){
			hideUpdater('submit');
		}
	}else{
			hideUpdater('submit');
	}
}

function validForm(){
	var len=commonIds.length;
	var validForm = true;
	for(i=0;i<len;i++){
		var cId = commonIds[i];
		var decision = $('decision_'+cId).value;
		if(decision.endsWith('<%=SelectionProcessConstants.DECISION_APPROVED%>')){
			var ctl = eval('selectBoxSteps_' + cId);
			if(existInIds(ctl.getSelectedId(), ctl.options.joiningstepids)){
				var joiningDateCtl = $('joiningDate_'+cId);
				if(joiningDateCtl.value.trim()==''){
					validForm = false;
					alert('<bean:message key="common.please_enter" /> <bean:message key="common.joining_date" />');
					joiningDateCtl.focus();
					break;
				}
				if(!validForm){
					break;
				}
			}
			
		}else if(decision.endsWith('<%=SelectionProcessConstants.DECISION_SCHEDULE%>')){
			var checkboxListAttendee = eval('checkboxListAttendee_'+cId);
			 $('inetrviewerid_'+cId).value=checkboxListAttendee.getSelectedIds();
			if($('appointmentDate_'+cId).value.trim()==''){
				validForm = false;
				alert('<bean:message key="common.please_enter" /> <bean:message key="common.date"/>');
				$('appointmentDate_'+cId).focus();
				break;
			}else if($('fromTime_'+cId).value.trim()==''){
				validForm = false;
				alert('<bean:message key="common.please_enter" /> <bean:message key="common.time"/>');
				$('fromTime_'+cId).focus();
				break;
			}else if(checkboxListAttendee.getSelectedIds()==''){
				validForm = false;
				alert('<bean:message key="common.please_select" /> <bean:message key="view_appointment.label.interviewers"/>');
				break;
			}
		}else if(decision.endsWith('<%=SelectionProcessConstants.DECISION_POSITION%>')){
			if($('newposid_'+cId).value==''){
				alert('<bean:message key="common.please_select" /> <bean:message key="common.position" />');
				validForm = false;
			}else if($('newstepid_'+cId).value==''){
				alert('<bean:message key="common.please_select" /> <bean:message key="common.step" />');
				validForm = false;
			}	
		}
	}
	return validForm;
}

function setOnHoldSelectedForOhHoldScreen(){
	<%for(int i=0; i<feedback.size(); i++){
		SelectionProcessData data = (SelectionProcessData)feedback.get(i);
		String positionId = data.getPositionId();
		String screenType = (String)data.getAttribute("screenType");
		String comId=""+data.getApplicantId();
		if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_HLD)){%>
			$('decision_<%=comId %>').value='<%=SelectionProcessConstants.DECISION_APPROVED%>';
			$('tostep_<%=comId %>').value='<%=SelectionProcessConstants.STEP_ON_HOLD%>';
	<%	}
	}%>
}

function singleFeedback(id){
	var url = 'selectionProcess.do?mode=applicantSingleFeedback&applicantId='+id+'&sessionId=<bean:write name="selectionProcessForm" property="sessionId"/>';
	window.setTimeout("showInPopUp('"+url+"',900, 600,doNothing,true);", 10);
}

function doNothing(){}

function showInPopUp(url,width,height,returnFun, close){
	showPopWin(url, width, height, returnFun,close);
}

function onCancel(){
	window.location = 'selectionProcess.do?mode=<bean:write name="selectionProcessForm" property="tab"/>';
}

function setComment(id,val){
	var obj = $('comment_'+id);
	obj.value = val;
}

function onWindowLoad(){
	initPopUp();
	setOnHoldSelectedForOhHoldScreen();
}
window.onload = onWindowLoad;
</script>

					