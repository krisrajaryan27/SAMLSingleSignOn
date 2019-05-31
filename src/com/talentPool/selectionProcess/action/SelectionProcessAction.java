/**
 * 
 */
package com.talentPool.selectionProcess.action;

import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.action.ApplicantAction;
import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantBlackListHistoryData;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.applicant.utils.ApplicantValidators;
import com.talentPool.audit.action.AuditAction;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.calendar.CalendarConstants;
import com.talentPool.calendar.dataobject.AppointmentData;
import com.talentPool.calendar.manager.CalendarManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.ErrorConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldDataProcessor;
import com.talentPool.dashboard.bc.DashboardBC;
import com.talentPool.dashboard.manager.RecentViewManager;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.manager.DocumentManager;
import com.talentPool.latestActivity.manager.LatestActivityManager;
import com.talentPool.masters.constants.FeedbackFieldsConstant;
import com.talentPool.masters.manager.FeedbackFieldsManager;
import com.talentPool.offerSheet.manager.OfferSheetManager;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.StepData;
import com.talentPool.positions.dataobject.TraitData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.reports.views.FeedbackFormView;
import com.talentPool.repository.TPIndexEvent;
import com.talentPool.repository.TPIndexEventQueue;
import com.talentPool.salaryStructure.databject.SalaryFormulaData;
import com.talentPool.salaryStructure.databject.SalaryStructure;
import com.talentPool.salaryStructure.exception.SalaryCalculationException;
import com.talentPool.salaryStructure.manager.SalaryCalculator;
import com.talentPool.salaryStructure.manager.SalaryStructureManager;
import com.talentPool.salaryStructure.utils.SalaryStructureXMLUtils;
import com.talentPool.search.utils.SearchUtils;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.dataobject.CommunicationData;
import com.talentPool.selectionProcess.dataobject.ConflictApplicantData;
import com.talentPool.selectionProcess.dataobject.FeedbackData;
import com.talentPool.selectionProcess.dataobject.OfferDetailsModifiedInteractionData;
import com.talentPool.selectionProcess.dataobject.SelectionProcessData;
import com.talentPool.selectionProcess.dataobject.UserData;
import com.talentPool.selectionProcess.exception.ApplicantBlacklistedException;
import com.talentPool.selectionProcess.exception.AppointmentExistsException;
import com.talentPool.selectionProcess.exception.InProcessException;
import com.talentPool.selectionProcess.form.SelectionProcessForm;
import com.talentPool.selectionProcess.manager.CommunicationManager;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.selectionProcess.utils.SelectionProcessUtils;
import com.talentPool.sms.SMSGateway;
import com.talentPool.sms.SMSGatewayImpl;
import com.talentPool.user.MessageConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.PermissionsManager;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.user.manager.UserManager;

/**
 * 
 * @author pallavi
 * 
 */
public class SelectionProcessAction extends TPDispatchAction {

	public ActionForward moveApplicantUpOrDown(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		
		String forward = "moveApplicantUpOrDown";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		String userId = (String) request.getSession(false).getAttribute("userId");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

		FeedbackData feedbackData = null;
		SelectionProcessManager selectionProcessManager = null;
		ApplicantManager applicantManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			applicantManager 		= new ApplicantManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			String communicationId = selectionProcessForm.getCommunicationId();
			
			if (Utils.isBlankOrNull(communicationId) && selectionProcessManager.isPositionStepSchedulable(applicantId)) {
				SimpleDataObject object = (SimpleDataObject) selectionProcessManager.getLatestAppointmentsInPast(applicantId);
				if (object == null || object.getInt("appointmentStatus") == CalendarConstants.APPOINTMENT_STATUS_NOSHOW) {
					forward = "notInterestedReject";
					feedbackData = selectionProcessManager.getApplicantSummaryData(applicantId);
					request.setAttribute("feedbackData", feedbackData);
					if (!selectionProcessManager.isUserAuthorizedToScheduleOrConfirmAttendance(userId, applicantId, permissionSet)) {
						ActionErrors errors = new ActionErrors();
						errors.add("selection_feedback.error.not_authorized_to_perform_operation", new ActionError("selection_feedback.error.not_authorized_to_perform_operation"));
						saveErrors(request, errors);
					}
					return mapping.findForward(forward);
				} else {
					SimpleDataObject fData = selectionProcessManager.getLatestFeedback(applicantId);
					if (Utils.isBlankOrNull(fData.getString("communicationId")) || SelectionProcessConstants.STEP_REPEAT.equalsIgnoreCase(fData.getString("stepIdTo"))) {
						forward = "confirmAttendance";
						if (!Utils.isBlankOrNull(fData.getString("communicationId"))) {
							selectionProcessForm.setCommunicationId(fData.getString("communicationId"));
						}
						selectionProcessForm.setAppointmentId(object.getString("appointmentId"));
						feedbackData = selectionProcessManager.getApplicantSummaryData(applicantId);
						request.setAttribute("feedbackData", feedbackData);

						setAttendeesInRequest(object.getString("appointmentId"), request);

						if (!selectionProcessManager.isUserAuthorizedToScheduleOrConfirmAttendance(userId, applicantId, permissionSet)) {
							ActionErrors errors = new ActionErrors();
							errors.add("selection_feedback.error.not_authorized_to_perform_operation", new ActionError("selection_feedback.error.not_authorized_to_perform_operation"));
							saveErrors(request, errors);
						}
						return mapping.findForward(forward);
					}
				}
			}

			boolean isEdit = true;
			boolean isMoveUpOrDown = false;

			List<UserData> interviewers = null;

			if (Utils.isBlankOrNull(communicationId)) {
				isEdit = false;
				communicationId = selectionProcessManager.getCommunicationId(applicantId);
				selectionProcessForm.setCommunicationId(communicationId);
			}
			if (Utils.isBlankOrNull(communicationId)) {
				isMoveUpOrDown = true;
				feedbackData = selectionProcessManager.getCurrentStepData(applicantId);
			} else {
				if(isEdit){
					String fbFormId=selectionProcessManager.getFeedbackFormIdForUserProcessApplicant(userId,communicationId);
					feedbackData = selectionProcessManager.getAllFeedBackDataWithProcessId(selectionProcessForm.getCommunicationId(),SelectionProcessConstants.DETAILED_FEEDBACK,fbFormId);
				}else{
					feedbackData = selectionProcessManager.getCurrentStepData(applicantId);
				}				
			}
			selectionProcessForm.setCurrentPositionStepId("" + feedbackData.getFromStepData().getStepId());
			List steps = selectionProcessManager.getNextStepData(feedbackData.getPositionId(), "" + feedbackData.getFromStepData().getStepId());
			if (steps == null) {
				StepData stepTo = new StepData();
				stepTo.setStepTitle(SelectionProcessConstants.STEP_TITLE_JOINED);
				steps = new ArrayList();
				steps.add(stepTo);
				if (isEdit && !SelectionProcessConstants.STEP_JOIN.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
					selectionProcessForm.setNextPositionStepId("" + feedbackData.getToStepData().getStepId());
				} else if (isEdit && SelectionProcessConstants.STEP_JOIN.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
					selectionProcessForm.setNextPositionStepId("");
				} else {
					// selectionProcessForm.setNextPositionStepId("");
					selectionProcessForm.setNextPositionStepId(SelectionProcessConstants.STEP_INVALID);
				}
			} else if (!isEdit) {
				// selectionProcessForm.setNextPositionStepId("" + ((StepData)
				// steps.get(steps.size() - 1)).getStepId());
				selectionProcessForm.setNextPositionStepId(SelectionProcessConstants.STEP_INVALID);
			} else {
				selectionProcessForm.setNextPositionStepId("" + feedbackData.getToStepData().getStepId());
			}
			feedbackData.setNextSteps(steps);

			/* Get position interviewers */
			boolean isUserAuthorizedToMakeDecision = selectionProcessManager.isUserAuthorizedToMakeDecision(userId, feedbackData.getFromStepData().getStepId());

			interviewers = selectionProcessManager.getPositionInterviewers(communicationId, "" + feedbackData.getFromStepData().getStepId());
			boolean doDisplaySaveButton = false;
			if (interviewers != null && interviewers.size() > 0) {
				Iterator<UserData> itr = interviewers.iterator();
				while (itr.hasNext()) {
					UserData data = itr.next();
					if (userId.equalsIgnoreCase("" + data.getUserId())) {
						doDisplaySaveButton = true;
						break;
					}
				}
			}
			Iterator<UserData> itr = interviewers.iterator();
			while (itr.hasNext()) {
				UserData data = (UserData) itr.next();
				if (!userId.equalsIgnoreCase("" + data.getUserId())) {
					itr.remove();
				}
			}

			String traitIds = selectionProcessManager.getTraitIdsAsString(feedbackData.getFromStepData().getTraits(), userId,true);
			selectionProcessForm.setTraitIds(traitIds);

			Map traitData = selectionProcessManager.getTraitData(isMoveUpOrDown, feedbackData.getFromStepData(), interviewers, userId);

			selectionProcessForm.setIsUserAuthorizedToMove("" + isUserAuthorizedToMakeDecision);
			List positions = selectionProcessManager.getOtherOpenPositions(feedbackData.getPositionId());
			
			PositionManager positionManager = new PositionManager();
			SimpleDataObject positionDescription = (SimpleDataObject) positionManager.getPositionDescriptionToEdit(feedbackData.getPositionId());
			if(positionDescription !=null && positionDescription.getString("gradeId")!=null){
				selectionProcessForm.setGradeId(!positionDescription.getString("gradeId").equals("-1")?positionDescription.getString("gradeId"):"");
			}else{
				selectionProcessForm.setGradeId("");
			}
			
			String stepsJSArray = SelectionProcessUtils.getJsArrayForSelectionSteps(steps,permissionSet);
			String pendingActionUsers = selectionProcessManager.getUsersWithPendingAction(communicationId, applicantId, "" + feedbackData.getFromStepData().getStepId(), userId);
			if(!Utils.isBlankOrNull(pendingActionUsers)){
				pendingActionUsers = (pendingActionUsers.length() > 83) ? pendingActionUsers.substring(0, 80) + "..." : pendingActionUsers;
			}
			ApplicantData aData = applicantManager.getApplicantData(applicantId, false, false, false,true,false);
			EducationalData eData = applicantManager.getLatestEducationalInfo(applicantId);
			processCustomFieldsValues(aData);
			request.setAttribute("feedbackData", feedbackData);
			request.setAttribute("interviewers", interviewers);
			request.setAttribute("traitData", traitData);
			request.setAttribute("positions", positions);
			request.setAttribute("doDisplaySaveButton", doDisplaySaveButton);
			request.setAttribute("stepsJSArray", stepsJSArray);
			request.setAttribute("pendingActionUsers", pendingActionUsers);
			request.setAttribute("appplicantData", aData);
			if(eData!=null){
				String formatedEducation = eData.getFormattedEducation(); 
				if(formatedEducation!=null && formatedEducation.length()>80)
					request.setAttribute("educationalInfoTrimmed", formatedEducation.substring(0,80)+"..");
				else
					request.setAttribute("educationalInfoTrimmed", formatedEducation);
				request.setAttribute("educationalInfo", formatedEducation);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while moving applicant", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveSelectionProcessResult(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "moveApplicantUpOrDown";
		String userId = (String) request.getSession(false).getAttribute("userId");
		if (!isTokenValid(request)){
			SessionManager.invalidateSession(request, response, userId);
			try {
				SessionManager.sessionExpireRedirect(mapping, actionForm, request, response, this, false);
				TPLogger.getLogger().error("Invalid CSRFToken while saving applicant feedback. Session invalidated because of suspicious activity.");
				return null;
			} catch (Exception e) {
				TPLogger.getLogger().error("Error While validating session", e);
			} 
		}
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),((SelectionProcessForm) actionForm).getPositionId(),null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
			if (errors == null) {
				errors = new ActionErrors();
			}
			validateSelectionProcessForm(errors, selectionProcessForm);
			String applicantId = selectionProcessForm.getApplicantId();
			String currentStep = selectionProcessForm.getCurrentPositionStepId();
			String moveToStep = selectionProcessForm.getNextPositionStepId();
			String communicationId = selectionProcessForm.getCommunicationId();
			String positionId = selectionProcessForm.getPositionId();
			String deleteAppointments = selectionProcessForm.getDeleteAppointments();
			String _positionId = selectionProcessForm.get_positionId();
			String _moveToStep = selectionProcessForm.get_nextPositionStepId();
			String attendeesId = selectionProcessForm.getAttendeeId();

			String ctcOffered = selectionProcessForm.getCtcOffered();
			String basicOffered = selectionProcessForm.getBasicOffered();
			String levelOffered = selectionProcessForm.getLevelOffered();
			String joiningBonus=selectionProcessForm.getJoiningBonus();
			String variableOffered=selectionProcessForm.getVariableOffered();
			String designationOffered = selectionProcessForm.getDesignationOffered();
			String inputSalaryVariable = selectionProcessForm.getInputSalaryVariable();
			String employeeCode = selectionProcessForm.getEmployeeCode();
			String feedbackFormId= selectionProcessForm.getFeedbackFormId();
			
			boolean moveToJoined = false;
			Map traitData = new HashMap();
			HashMap<String, String> ratingsIdValues = new HashMap<String, String>();
			HashMap<String, String> multipleSelectIdValues = new HashMap<String, String>();
			Map<String,String> applicantFieldValueMap = new HashMap<String, String>();
			String traitIds = selectionProcessForm.getTraitIds();
			processTraitDataFromRequest(traitIds, traitData, ratingsIdValues, multipleSelectIdValues, applicantFieldValueMap, request);
			validateApplicantFields(errors,applicantFieldValueMap);
			
			if (moveToStep == null || moveToStep.length() == 0) {
				if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_APPLY_POSITION_VACANCY_RESTRICTION).equals(GlobalConstants.ENABLED)){
					if(selectionProcessManager.isPositionVacancyFilled(positionId)) {
						errors.add("selection_feedback.error.vacancy_full", new ActionError("selection_feedback.error.vacancy_full")); 
					}
				}
			}
			
			if (errors.size() > 0) {
				saveErrors(request, errors);
				//request.setAttribute("errors", "1");
				return moveApplicantUpOrDown(mapping, actionForm, request, response);
			} else {
				String joinDate = selectionProcessForm.getJoiningDate();
				if (moveToStep == null || moveToStep.length() == 0) {
					moveToJoined = true;
					moveToStep = SelectionProcessConstants.STEP_JOIN;
				}

				if (!Utils.isBlankOrNull(_positionId) || SelectionProcessConstants.NOT_INTERESTED_REJECT.equalsIgnoreCase(moveToStep)) {
					deleteAppointments = Boolean.TRUE.toString();
				}
				String appointmentId = selectionProcessForm.getAppointmentId();
				String clientIpAddr = getClientIpAddr(request);
				boolean isConflictingConcurrentResult = selectionProcessManager.saveSelectionProcessResult(applicantId, positionId, currentStep, moveToStep, userId, traitData, ratingsIdValues, multipleSelectIdValues, 
													moveToJoined, joinDate, ctcOffered, basicOffered,levelOffered,
													designationOffered, inputSalaryVariable, communicationId, deleteAppointments,
													_positionId, _moveToStep, appointmentId, attendeesId, employeeCode,applicantFieldValueMap, 
													feedbackFormId, clientIpAddr,joiningBonus,variableOffered);
				if (isConflictingConcurrentResult) {
					errors = new ActionErrors();
					errors.add("selection_feedback.error.conflicting_concurrent_result", new ActionError("selection_feedback.error.conflicting_concurrent_result"));
					saveErrors(request, errors);
					request.setAttribute("errors", "1");
				} else {
					request.setAttribute("update", "1");				
					if(Utils.isBlankOrNull(_positionId) && Utils.isBlankOrNull(_moveToStep) 
						&& (SelectionProcessConstants.STEP_REJECT.equals(moveToStep) 
						|| SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT.equals(moveToStep)
						|| SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT.equals(moveToStep))
						|| SelectionProcessConstants.STEP_NOT_ATTENDED.endsWith(moveToStep)) {
						// If candidate is rejected, send email depending on settings.
						selectionProcessManager.sendRejectEmails(userId, applicantId, currentStep, positionId);					
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while moving candidate up", e);
			return moveApplicantUpOrDown(mapping, actionForm, request, response);
		}
		return mapping.findForward(forward);
	}

	public ActionForward viewInterviewLog(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewInterviewLog";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessManager selectionProcessManager = null;
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String communicationId = selectionProcessForm.getCommunicationId();
			
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_DATA() && selectionProcessManager.isSelectionProcessHidden(communicationId)) {
				errors.add("permission_denied.content", new ActionError("permission_denied.content"));
				throw new Exception();
			}
			
			//FeedbackData feedbackData = selectionProcessManager.getFeedBackDataWithProcessId(communicationId);
			FeedbackData feedbackData = null;
			
			if (SelectionProcessConstants.INTERACTION_APPLICANT_RESPONSE==Integer.parseInt(selectionProcessForm.getCommunicationType())){
				feedbackData = selectionProcessManager.getAllFeedBackDataWithProcessIdForCandidate(communicationId,SelectionProcessConstants.DETAILED_FEEDBACK,"");
			}else{
				feedbackData = selectionProcessManager.getAllFeedBackDataWithProcessId(communicationId,SelectionProcessConstants.DETAILED_FEEDBACK,"");
			}
			String userId = (String) request.getSession(false).getAttribute("userId");
			
			if(feedbackData==null){
				errors.add("select.latest_activity.noFeedaback", new ActionError("select.latest_activity.noFeedaback"));
				throw new Exception();
			}
			// Double check authorization if applicantId is tampered in URL
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, ""+feedbackData.getApplicantId(), null, null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
									
			boolean isUserAuthorizedToMakeDecision = selectionProcessManager.isUserAuthorizedToMakeDecision(userId, feedbackData.getFromStepData().getStepId());
			boolean isUserAtUpperLevelInFunnel = selectionProcessManager.isUserAtUpperLevelInFunnel(feedbackData.getPositionId(), "" + feedbackData.getFromStepData().getStepId(), userId);
			boolean isFeedbackLive = selectionProcessManager.isFeedbackLive(communicationId, "" + feedbackData.getApplicantId());
			if (SelectionProcessConstants.INTERACTION_APPLICANT_RESPONSE==Integer.parseInt(selectionProcessForm.getCommunicationType())){
				isFeedbackLive = true;
			}
			String stepIds = feedbackData.getFromStepData().getStepId() + ", " + feedbackData.getToStepData().getStepId();
			boolean isFeedbackEditable = false;
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(String.valueOf(feedbackData.getApplicantId()));
			List<UserData> interviewers = new ArrayList<UserData>();
			Map traitData = null;
			boolean doDisplayEditButton = false;
			if (SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
				traitData = selectionProcessManager.getTraitData(false, feedbackData.getFromStepData(), null, null);
			} else {
				
				if (SelectionProcessConstants.INTERACTION_APPLICANT_RESPONSE==Integer.parseInt(selectionProcessForm.getCommunicationType())){
					UserData interviewer = new UserData();
					interviewer.setUserId(-1);
					interviewer.setUserName("Applicant");
					interviewers.add(interviewer);
				}else {
				interviewers = selectionProcessManager.getPositionInterviewers(communicationId, "" + feedbackData.getFromStepData().getStepId());
				}
				/*
				 * if (!isUserAuthorizedToMakeDecision) { } else {
				 * isFeedbackEditable = true; }
				 */
				if (interviewers != null && interviewers.size() > 0) {
					Iterator<UserData> itr = interviewers.iterator();
					while (itr.hasNext()) {
						UserData data = itr.next();
						if (userId.equalsIgnoreCase("" + data.getUserId())) {
							doDisplayEditButton = true;
							break;
						}
					}
				}
				Iterator<UserData> itr = interviewers.iterator();
				while (itr.hasNext()) {
					UserData data = itr.next();

					if (!isUserAuthorizedToMakeDecision && !isUserAtUpperLevelInFunnel && !userId.equalsIgnoreCase("" + data.getUserId()) && permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
						if (!Utils.isBlankOrNull(feedbackData.getString("applicantStepId")) && isFeedbackLive) {
							itr.remove();
						}
					} else {
						isFeedbackEditable = true;
					}
				}
				traitData = selectionProcessManager.getTraitData(false, feedbackData.getFromStepData(), interviewers, null);
			}

			if (SelectionProcessConstants.STEP_REJECT.equalsIgnoreCase("" + feedbackData.getFromStepData().getStepId())) {
				request.removeAttribute("isEditable");
			} else if (SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
				request.removeAttribute("isEditable");
			} else if (SelectionProcessConstants.STEP_NOT_ATTENDED.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
				request.removeAttribute("isEditable");
			} else if (SelectionProcessConstants.STEP_ATTENDED.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
				request.removeAttribute("isEditable");
			} else if (SelectionProcessConstants.STEP_REPEAT.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
				request.removeAttribute("isEditable");
			} else if (selectionProcessManager.isAnyStepDeleted(stepIds)) {
				request.removeAttribute("isEditable");
			} else if (SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
				request.removeAttribute("isEditable");
			}else if (SelectionProcessConstants.APPLICANT_JOINED.equalsIgnoreCase("" + applicantData.getApplicantJoined())) {
				request.removeAttribute("isEditable");
			} else if (isFeedbackEditable) {
				int noOfNextStepsDone = selectionProcessManager.getNoOfFeedbackEnteredAfterThis(communicationId, "" + feedbackData.getApplicantId());
				if (noOfNextStepsDone == 0) {
					request.setAttribute("isEditable", "1");
				}
				if (!doDisplayEditButton) {
					request.removeAttribute("isEditable");
				}
			}
			request.setAttribute("feedbackData", feedbackData);
			request.setAttribute("interviewers", interviewers);
			request.setAttribute("traitData", traitData);
			request.setAttribute("applicantData", applicantData);
			selectionProcessForm.setReportFormat(ReportConstants.FORMAT_HTML);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		if (errors.size() > 0) {
			request.setAttribute(Globals.ERROR_KEY, errors);
			forward = "modalError";
			return mapping.findForward(forward);
		}
		return mapping.findForward(forward);
	}

	public ActionForward viewFeedbackReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewreport";
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String communicationId = selectionProcessForm.getCommunicationId();
			FeedbackData feedbackData = selectionProcessManager.getFeedBackDataWithProcessId(communicationId);
			boolean showIfExist = selectionProcessForm.getShowIfExist()!=""? Boolean.parseBoolean(selectionProcessForm.getShowIfExist()):false;
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

			boolean isUserAuthorizedToMakeDecision = selectionProcessManager.isUserAuthorizedToMakeDecision(userId, feedbackData.getFromStepData().getStepId());
			boolean isUserAtUpperLevelInFunnel = selectionProcessManager.isUserAtUpperLevelInFunnel(feedbackData.getPositionId(), "" + feedbackData.getFromStepData().getStepId(), userId);
			boolean isFeedbackLive = selectionProcessManager.isFeedbackLive(communicationId, "" + feedbackData.getApplicantId());

			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(String.valueOf(feedbackData.getApplicantId()));
			ArrayList<UserData> interviewers = new ArrayList<UserData>();
			Map traitData = null;

			if (SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
				traitData = selectionProcessManager.getTraitData(false, feedbackData.getFromStepData(), null, null);
			} else {
				interviewers = selectionProcessManager.getPositionInterviewers(communicationId, "" + feedbackData.getFromStepData().getStepId());

				Iterator<UserData> itr = interviewers.iterator();
				while (itr.hasNext()) {
					UserData data = itr.next();

					if (!isUserAuthorizedToMakeDecision && !isUserAtUpperLevelInFunnel && !userId.equalsIgnoreCase("" + data.getUserId()) && permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
						if (!Utils.isBlankOrNull(feedbackData.getString("applicantStepId")) && isFeedbackLive) {
							itr.remove();
						}
					}
				}
				traitData = selectionProcessManager.getTraitData(false, feedbackData.getFromStepData(), interviewers, null);
			}

			AppointmentData appointmentData = selectionProcessManager.getAppointmentDataForFeedback(communicationId);

			String ext = ReportUtils.getReportExtension(selectionProcessForm.getReportFormat());
			String outputFileName = request.getSession(false).getId() + String.valueOf(System.currentTimeMillis()) + ext;
			String jrXMLName = ReportConstants.JRXML_FEEDBACK_FORM_REPORT;
			if (SelectionProcessConstants.REPORT_TYPE_CONSOLIDATED.equals(selectionProcessForm.getReportType())) {
				jrXMLName = ReportConstants.JRXML_CONSOLIDATED_FEEDBACK_FORM_REPORT;
			}
			HashMap<String, String> params = getParamsToPrintFeedback(permissionSet);	

			interviewers = removeInterviewersNotSelected(interviewers, selectionProcessForm.getInterviewerIds());
			ArrayList<FeedbackFormView> reportData = selectionProcessManager.getFeedbackFormViewList(interviewers, feedbackData, traitData, applicantData, appointmentData, selectionProcessForm.getReportFormat(), selectionProcessForm.getReportType(),null,showIfExist, permissionSet);
			ReportManager reportManager = new ReportManager();
			String clientIpAddr = getClientIpAddr(request);
			reportManager.generateReport(jrXMLName, outputFileName, params, (ArrayList<FeedbackFormView>) reportData, 
					selectionProcessForm.getReportFormat(), userId, clientIpAddr);
			request.setAttribute("fileName", outputFileName);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while displaying interview log", e);
		}
		return mapping.findForward(forward);
	}

	private ArrayList<UserData> removeInterviewersNotSelected(ArrayList<UserData> interviewers, String interviewerIds) {
		if (!Utils.isBlankOrNull(interviewerIds)) {
			String[] ids = interviewerIds.split(",");
			for (int i = 0; interviewers != null && i < interviewers.size(); i++) {
				boolean interviewerSelected = false;
				for (int k = 0; k < ids.length; k++) {
					if (ids[k].trim().equals("" + interviewers.get(i).getUserId())) {
						interviewerSelected = true;
						break;
					}
				}
				if (!interviewerSelected) {
					interviewers.remove(i);
					i = i - 1;
				}
			}
		}
		return interviewers;
	}

	public ActionForward getApplicantInteractions(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		SelectionProcessManager selectionProcessManager = null;
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			selectionProcessManager = new SelectionProcessManager();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				ArrayList<SimpleDataObject> interactions = selectionProcessManager.getApplicantsInteractions(
								selectionProcessForm.getApplicantId());
				xmlFile = SelectionProcessUtils.getXMLForApplicantsInteractions(interactions, permissionSet);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error getting interactions in xml", e);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward addPhoneLog(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addPhoneLog";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			String applicantId = selectionProcessForm.getApplicantId();
			if (!Utils.isBlankOrNull(selectionProcessForm.getNote())) {
				ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
				if (errors == null) {
					errors = new ActionErrors();
				}

				Calendar cal = new GregorianCalendar();
				Date dtLogDate = cal.getTime();
				if (selectionProcessForm.getCommunicationType().equals("" + SelectionProcessConstants.INTERACTION_PHONE)) {
					dtLogDate = Utils.convertToDate(selectionProcessForm.getLogDate(), Utils.regEUDateFormat);
					if (dtLogDate == null) {
						errors.add("add_phone.error.logdate_invalid", new ActionError("add_phone.error.logdate_invalid"));
					}
					if (selectionProcessForm.getPhoneNo().equals(TPLabels.getLabel("add_phone.label.new_phone_val"))) {
						errors.add("add_phone.error.select_phone", new ActionError("add_phone.error.select_phone"));
					}
				}
				if (errors.size() > 0) {
					request.setAttribute(Globals.ERROR_KEY, errors);
				} else {
					CommunicationData cData = new CommunicationData();
					cData.setApplicantId(selectionProcessForm.getApplicantId());
					cData.setUserId(userId);
					cData.setCommunicationType(Integer.parseInt(selectionProcessForm.getCommunicationType()));
					cData.setCommunicationDate(new java.sql.Timestamp(dtLogDate.getTime()));
					cData.setCommunicationPhoneNo(selectionProcessForm.getPhoneNo());
					cData.setCommunicationText(selectionProcessForm.getNote());

					if (Utils.isBlankOrNull(selectionProcessForm.getCommunicationId())) {
						String[] applicantIds = cData.getApplicantId().split(",");
						for (int i = 0; i < applicantIds.length; i++) {
							cData.setApplicantId(applicantIds[i]);
							selectionProcessManager.addPhoneLog(cData);
						}						
					} else {
						// Update
						cData.setCommunicationId(selectionProcessForm.getCommunicationId());
						selectionProcessManager.updatePhoneLog(cData);
					}
					request.setAttribute("update", "1");
				}
			}
			// set form values if edit communication
			if (!Utils.isBlankOrNull(selectionProcessForm.getCommunicationId())) {
				CommunicationData communicationData = selectionProcessManager.getCommunicationData(selectionProcessForm.getCommunicationId());
				if (communicationData != null) {
					selectionProcessForm.setPhoneNo(communicationData.getCommunicationPhoneNo());
					selectionProcessForm.setLogDate(Utils.getDateConvertedToString(communicationData.getCommunicationDate(), Utils.regEUDateFormat));
					selectionProcessForm.setNote(communicationData.getCommunicationText());
				}
			}

			if (selectionProcessForm.getCommunicationType().equals("" + SelectionProcessConstants.INTERACTION_PHONE)) {
			// Get applicant Data for Phone Ids
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
			ArrayList<String> phoneIds = new ArrayList<String>();
			if (applicantData != null) {
				if (!Utils.isBlankOrNull(applicantData.getApplicantHomePhone()) && !SelectionProcessConstants.PHONE_INVALID.equalsIgnoreCase(applicantData.getApplicantHomePhoneIsInvalid())) {
					phoneIds.add(applicantData.getApplicantHomePhone());
				}
				if (!Utils.isBlankOrNull(applicantData.getApplicantWorkPhone()) && !SelectionProcessConstants.PHONE_INVALID.equalsIgnoreCase(applicantData.getApplicantWorkPhoneIsInvalid())) {
					phoneIds.add(applicantData.getApplicantWorkPhone());
				}
				if (!Utils.isBlankOrNull(applicantData.getApplicantCellPhone()) && !SelectionProcessConstants.PHONE_INVALID.equalsIgnoreCase(applicantData.getApplicantCellPhoneIsInvalid())) {
					phoneIds.add(applicantData.getApplicantCellPhone());
				}
			}
			request.setAttribute("applicantData", applicantData);
			request.setAttribute("phoneIds", phoneIds);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error adding phone interaction", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward sendSMS(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "sendSMS";
		
		Integer[] permissions = new Integer[2];
		permissions[0] = PermissionConstants.PERMISSION_SMS_SETTINGS;
		permissions[1] = PermissionConstants.PERMISSION_SEND_SMS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_SMS, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		selectionProcessForm.setCommunicationType("" + SelectionProcessConstants.INTERACTION_SMS);
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			String applicantId = selectionProcessForm.getApplicantId();
			if (!Utils.isBlankOrNull(selectionProcessForm.getNote())) {
				ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
				if (errors == null) {
					errors = new ActionErrors();
				}

				Calendar cal = new GregorianCalendar();
				Date dtLogDate = cal.getTime();
				if (Utils.isBlankOrNull(selectionProcessForm.getPhoneNo())) {
					errors.add("send_sms.error.select_number", new ActionError("send_sms.error.select_number"));
				}
				if (errors.size() > 0) {
					request.setAttribute(Globals.ERROR_KEY, errors);
				} else {
					CommunicationData cData = new CommunicationData();
					cData.setApplicantId(selectionProcessForm.getApplicantId());
					cData.setUserId(userId);
					cData.setCommunicationType(Integer.parseInt(selectionProcessForm.getCommunicationType()));
					cData.setCommunicationDate(new java.sql.Timestamp(dtLogDate.getTime()));
					cData.setCommunicationPhoneNo(selectionProcessForm.getPhoneNo());
					cData.setCommunicationText(selectionProcessForm.getNote());

					String senderId = (String) request.getSession(false).getAttribute("userCellPhone");
					SMSGateway gateway = new SMSGatewayImpl();
					gateway.send(cData.getCommunicationText(), cData.getCommunicationPhoneNo(), senderId);
					selectionProcessManager.addPhoneLog(cData);
					request.setAttribute("update", "1");
				}
			}

			// Get applicant Data for Phone Ids
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
			ApplicantData applicantPhones = applicantManager.getApplicantPhones(applicantId);
			ArrayList<String> phoneIds = new ArrayList<String>();
			ArrayList<String> phoneNames = new ArrayList<String>();
			String defaultNumber = "";
			if (applicantData != null) {
				if (!Utils.isBlankOrNull(applicantPhones.getSourceMobile())) {
					phoneIds.add(applicantPhones.getSourceMobile());
					phoneNames.add(applicantPhones.getSourceMobile() + " (" + applicantData.getApplicantSourceTitle() + ")");
					defaultNumber = applicantPhones.getSourceMobile();
				}
				if (!Utils.isBlankOrNull(applicantData.getApplicantHomePhone()) && !SelectionProcessConstants.PHONE_INVALID.equalsIgnoreCase(applicantData.getApplicantHomePhoneIsInvalid())) {
					phoneIds.add(applicantData.getApplicantHomePhone());
					phoneNames.add(applicantData.getApplicantHomePhone());
				}
				if (!Utils.isBlankOrNull(applicantData.getApplicantWorkPhone()) && !SelectionProcessConstants.PHONE_INVALID.equalsIgnoreCase(applicantData.getApplicantWorkPhoneIsInvalid())) {
					phoneIds.add(applicantData.getApplicantWorkPhone());
					phoneNames.add(applicantData.getApplicantWorkPhone());
				}
				if (!Utils.isBlankOrNull(applicantData.getApplicantCellPhone()) && !SelectionProcessConstants.PHONE_INVALID.equalsIgnoreCase(applicantData.getApplicantCellPhoneIsInvalid())) {
					phoneIds.add(applicantData.getApplicantCellPhone());
					phoneNames.add(applicantData.getApplicantCellPhone());
					if (Utils.isBlankOrNull(defaultNumber)) {
						defaultNumber = applicantData.getApplicantCellPhone();
					}
				}
			}
			request.setAttribute("applicantData", applicantData);
			request.setAttribute("phoneIds", phoneIds);
			request.setAttribute("phoneNames", phoneNames);
			request.setAttribute("defaultNumber", defaultNumber);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error adding phone interaction", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward updatePhones(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
			try {
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				selectionProcessManager.updateApplicantPhones(selectionProcessForm.getApplicantId(), selectionProcessForm.getApplicantHomePhone(), selectionProcessForm.getApplicantWorkPhone(), selectionProcessForm.getApplicantCellPhone(), selectionProcessForm.getApplicantHomePhoneIsInvalid(),
						selectionProcessForm.getApplicantWorkPhoneIsInvalid(), selectionProcessForm.getApplicantCellPhoneIsInvalid());
			} catch (Exception e) {
				TPLogger.getLogger().error("Error getting interactions in xml", e);
			}
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward viewPhoneLog(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewPhoneLog";

		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			CommunicationData communicationData = selectionProcessManager.getCommunicationData(selectionProcessForm.getCommunicationId());
			// Double check authorization if applicantId is tampered in URL
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, communicationData.getApplicantId(), null, null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
						
			if(!Utils.isBlankOrNull(communicationData.getDocumentId())) {
				forward = "getDocument";
				
				DocumentManager documentManager = new DocumentManager();
				DocumentData data = documentManager.getApplicantDocumentData(communicationData.getDocumentId());
				
				FileHandler fileHandler = new FileHandler();
				String contentType = fileHandler.getContentType(data.getRelativeFilePath());				
				request.setAttribute("filePath", Utils.concatFilePath(DocumentConstants.documentsPath, data.getRelativeFilePath()));
				request.setAttribute("contentType", contentType);
				request.setAttribute("fileName", data.getOriginalFileName());
				request.setAttribute("contentDisposition", DocumentConstants.CONTENT_DISPOSITION_ATTACHMENT);		
			} else {
				if (userId.equals(communicationData.getUserId())) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					if (communicationData.getCommunicationDate().compareTo(cal.getTime()) >= 0) {
						request.setAttribute("isEditable", "1");
					}
				}
				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantSummaryData(String.valueOf(communicationData.getApplicantId()));
	
				if (SelectionProcessConstants.APPLICANT_JOINED.equalsIgnoreCase("" + applicantData.getApplicantJoined())) {
					request.setAttribute("nonEditable","1");
				}
				request.setAttribute("communicationData", communicationData);
				request.setAttribute("applicantData", applicantData);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error view phone interaction", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward viewSMS(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewSMS";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_SMS_SETTINGS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_SMS, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			CommunicationData communicationData = selectionProcessManager.getCommunicationData(selectionProcessForm.getCommunicationId());
			// Double check authorization if applicantId is tampered in URL
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, String.valueOf(communicationData.getApplicantId()), null, null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(String.valueOf(communicationData.getApplicantId()));

			request.setAttribute("communicationData", communicationData);
			request.setAttribute("applicantData", applicantData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error view phone interaction", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward viewStatusMessage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewStatusMessage";
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			CommunicationData communicationData = selectionProcessManager.getStatusMessageData(selectionProcessForm.getCommunicationId());
			// Double check authorization if applicantId is tampered in URL
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, String.valueOf(communicationData.getApplicantId()), null, null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(String.valueOf(communicationData.getApplicantId()));

			request.setAttribute("communicationData", communicationData);
			request.setAttribute("applicantData", applicantData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error view phone interaction", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward viewAppointment(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}

		String forward = "viewAppointment";

		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		String appointmentId = selectionProcessForm.getCommunicationId();

		SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
		AppointmentData data = selectionProcessManager.getAppointmentData(appointmentId, userId);
		// Double check authorization if applicantId is tampered in URL 
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, ""+data.getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		ApplicantManager applicantManager = new ApplicantManager();
		ApplicantData applicantData = applicantManager.getApplicantSummaryData(String.valueOf(data.getApplicantId()));

		if (SelectionProcessConstants.APPLICANT_JOINED.equalsIgnoreCase("" + applicantData.getApplicantJoined())) {
			request.setAttribute("nonEditable","1");
		}
		request.setAttribute("appointmentData", data);
		request.setAttribute("applicantData", applicantData);

		return mapping.findForward(forward);
	}

	public ActionForward checkIfAppointmentExists(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();

			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List appointments = selectionProcessManager.getAppointmentsInFuture(applicantId);

			ArrayList<String> errors = null;
			if (appointments != null && appointments.size() > 0) {
				errors = new ArrayList<String>();
				errors.add("selection_feedback.error.appointment_exists");
			}

			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward getUsersAutoCompleteList(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		try {
			String xmlFile = "";
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String messageReceivedType = selectionProcessForm.getMessageReceivedType();
			ArrayList users = new ArrayList();
			UserManager userManager = new UserManager();
			if (messageReceivedType.equals("" + MessageConstants.MESSAGE_RECEIVED_AS_TO)) {
				users = userManager.getUsersLike(selectionProcessForm.getUserNameTo(), null, false);
			} else {
				users = userManager.getUsersLike(selectionProcessForm.getUserNamesCc(), null, false);
			}
			xmlFile = Utils.getListInXML(users, "name");
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while sending message", e);

		}
		return mapping.findForward(forward);
	}

	public ActionForward validateNames(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String ccNames = selectionProcessForm.getUserNamesCc();
			if (!Utils.isBlankOrNull(ccNames)) {
				String[] arrNames = ccNames.replaceAll(";", ",").split(",");
				PositionManager positionManager = new PositionManager();
				ArrayList<LoginData> users = positionManager.getUsersForRole(null);
				String invalidUsers = SelectionProcessUtils.getInvalidUserNames(arrNames, users);
				xmlFile = SelectionProcessUtils.getInvalidUsersXML(invalidUsers);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting invalid users list", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward addMessage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "addMessage";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			return null;
		}
		SelectionProcessManager selectionProcessManager = null;
		
		if(!isUserAuthorized(request,CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			String applicantId = selectionProcessForm.getApplicantId();
			if (!Utils.isBlankOrNull(selectionProcessForm.getUserNameTo())) {
				String toNames = selectionProcessForm.getUserNameTo();
				PositionManager positionManager = new PositionManager();
				ArrayList<LoginData> users = positionManager.getUsersForRole(null);
				String[] arrNames = toNames.replaceAll(";", ",").split(",");
				ArrayList<String> toUserIds = SelectionProcessUtils.getUserIdsFromNames(arrNames, users);

				String ccNames = selectionProcessForm.getUserNamesCc();
				ArrayList<String> ccUserIds = null;
				if (!Utils.isBlankOrNull(ccNames)) {
					arrNames = ccNames.replaceAll(";", ",").split(",");
					ccUserIds = SelectionProcessUtils.getUserIdsFromNames(arrNames, users);
					// remove from cc if to user exist
					for (int i = 0; i < toUserIds.size(); i++) {
						String uId = toUserIds.get(i);
						if (ccUserIds.contains(uId)) {
							ccUserIds.remove(uId);
						}
					}
				}
				if (toUserIds.size() > 0) {
					selectionProcessManager.postMessage(applicantId, userId, toUserIds, selectionProcessForm.getMessageText(), ccUserIds);
					request.setAttribute("update", "1");
				}
			}
			ArrayList allUsers = selectionProcessManager.getAllUsersForMessaging(userId);
			ArrayList userIds = new ArrayList();
			ArrayList userNames = new ArrayList();
			CommonUtils.populateIdsAndNames(allUsers, userIds, userNames, "userId", "userName", null);
			selectionProcessForm.setUserIds(userIds);
			selectionProcessForm.setUserNames(userNames);
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
			request.setAttribute("applicantData", applicantData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while sending message", e);

		}
		return mapping.findForward(forward);
	}

	public ActionForward viewMessage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewMessage";
		SimpleDataObject sdo = new SimpleDataObject();
		SelectionProcessManager selectionProcessManager = null;
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;

			String messageId = selectionProcessForm.getCommunicationId();
			String communicationType = selectionProcessForm.getCommunicationType();
			if (communicationType.equals("" + SelectionProcessConstants.INTERACTION_MESSAGE)) {
				sdo = selectionProcessManager.getMessageDetails(messageId);
			}
			selectionProcessForm.setApplicantId(sdo.getString("applicantId"));
			// Double check authorization if applicantId is tampered in URL
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, selectionProcessForm.getApplicantId(), null, null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			selectionProcessForm.setMessageText(sdo.getString("messageText"));
			selectionProcessForm.setCommunicationId(messageId);
			selectionProcessForm.setApplicantName(sdo.getString("applicantName"));
			selectionProcessForm.setUserIdFrom(sdo.getString("messageFrom"));
			selectionProcessForm.setUserIdTo(sdo.getString("messageTo"));
			selectionProcessForm.setUserNameFrom(sdo.getString("messageFromUser"));
			selectionProcessForm.setUserNameTo(sdo.getString("messageToUser"));
			selectionProcessForm.setUserNamesCc(sdo.getString("messageCcUser"));
			selectionProcessForm.setLogDate(DateUtils.getSystemDateTimeFormat(sdo.getDate("messageDate")));
			selectionProcessForm.setSourceTitle(sdo.getString("sourceTitle"));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error fetching message details", e);
		}
		return mapping.findForward(forward);

	}

	public ActionForward callListforApplicants(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewCallListReport";
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;

			String applicantIds = selectionProcessForm.getApplicantIds();
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			ArrayList applicants = selectionProcessManager.getApplicantsList(applicantIds, permissionSet);

			request.setAttribute("applicants", applicants);
			request.setAttribute("listGenratedOn", new Date());

		} catch (Exception e) {
			TPLogger.getLogger().error("Error fetching call list  for Applicants", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getPositionStepSchedulable(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				boolean isStepSchedulable = selectionProcessManager.isPositionStepSchedulable(selectionProcessForm.getApplicantId());
				if (!isStepSchedulable) {
					wr.startDocument();
					wr.startElement("errors");
					wr.startElement("error");
					wr.characters(TPLabels.getLabel("applicant_home.error.position_step_not_schedulable"));
					wr.endElement("error");
					wr.endElement("errors");
					wr.endDocument();
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while determining whether position step is schedulable or not", e);
		}
		xmlFile = sWr.getBuffer().toString();
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward getStepsInXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = null;

		SelectionProcessForm form = (SelectionProcessForm) actionForm;
		String positionId = form.getPositionId();
		String selectedGroupId = form.getSelectedGroupId();

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			xmlFile = selectionProcessManager.getStepsInXml(positionId,selectedGroupId);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward viewOriginalResume(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "viewOriginalResume";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		
		SelectionProcessForm form = (SelectionProcessForm) actionForm;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, form.getApplicantId(), form.getPositionId(), null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		String userId = (String) request.getSession(false).getAttribute("userId");
		String userRole = (String) request.getSession(false).getAttribute("userRoles");
		PermissionsManager permissionsManager = null;
		try {
			permissionsManager = new PermissionsManager();

			boolean viewRights = true;
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantDisplayData(applicantId, userId);
			request.setAttribute("applicantData", applicantData);
			request.setAttribute("pageTitle", applicantData.getApplicantName());
			request.setAttribute("applicantIsConfidential", applicantData.getIsConfidential());
			// String documentPath = DocumentConstants.documentsPath;
			// request.setAttribute("relativeDocumentPath", documentPath);

			// settings for Undo last Feedback
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			ArrayList<SelectionProcessData> applicantUserIdAndProcessStatus = selectionProcessManager.getApplicantUserIdAndPositionStatus(applicantId);
			String applicantUserId = "";
			String applicantPositionStatus = "";
			if (applicantUserIdAndProcessStatus.size() > 0) {
				SelectionProcessData sDo = applicantUserIdAndProcessStatus.get(0);
				applicantUserId = sDo.getUserId();
				applicantPositionStatus = sDo.getPositionStatus();
			}
			if (!Utils.isBlankOrNull(applicantUserId) && !Utils.isBlankOrNull(applicantPositionStatus)) {
				if (applicantPositionStatus.equals("1") && applicantUserId.equals(userId))
					request.setAttribute("showUndoFeedback", "1");
			}
			// end
			if (Utils.isBlankOrNull(applicantData.getApplicantOriginalResumePath())) {
				applicantData.setApplicantOriginalResumePath(applicantData.getApplicantOriginalDocPath());
			}
			selectionProcessForm.setOriginalResume(applicantData.getApplicantOriginalResumePath());
			selectionProcessForm.setOriginalDoc(applicantData.getApplicantOriginalDocPath());
			if (!Utils.isBlankOrNull(applicantData.getApplicantPositionId())) {
				if (applicantData.getApplicantJoined().equals(SelectionProcessConstants.APPLICANT_NOT_JOINED)) {
					request.setAttribute("inProcess", "1");
					if (applicantData.getStepScheduled().equals("1")) {
						request.setAttribute("isSchedulable", "1");
					}
				}
				if (permissionsManager.isRecruiter(userRole) || permissionsManager.isRequisitioner(userRole) || permissionsManager.isInterviewer(userRole)) {
					if (!((PermissionSet) request.getSession(false).getAttribute("permissionSet")).isPERMISSION_SHOW_ALL_CANDIDATES()){
						viewRights = permissionsManager.isPermittedForPosition(applicantData.getApplicantPositionId(), userId);
					}
				}
			}
			if (viewRights) {
				RecentViewManager recentViewManager = new RecentViewManager();
				recentViewManager.addLastViewedEntry(selectionProcessForm.getApplicantId(), UserConstants.ENTITY_CANDIDATE, userId);
			} else {
				forward = "permissionDenied";
			}
			
			SearchUtils.setViewed(request, applicantId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in view original resume", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward shortlist(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "shortlist";
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_SHORTLIST;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			if(applicantId.indexOf(",")==-1){
				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
				selectionProcessForm.setApplicantName(applicantData.getApplicantName());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward changeStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "changeStatus";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			return null;
		}
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE,null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			String userId = (String) request.getSession(false).getAttribute("userId");
			String statusMessage = selectionProcessForm.getStatusMessage();
			if (!Utils.isBlankOrNull(statusMessage)) {
				selectionProcessManager.saveStatusMessage(applicantId, statusMessage, userId);
				request.setAttribute("update", "1");
			}
			ArrayList status = (ArrayList) selectionProcessManager.getStatusMessages(applicantId);
			ArrayList statusIds = new ArrayList();
			ArrayList statusNames = new ArrayList();
			CommonUtils.populateIdsAndNames(status, statusIds, statusNames, "messageId", "message", null);
			selectionProcessForm.setMessageIds(statusIds);
			selectionProcessForm.setMessageNames(statusNames);
			String messageId = "0";
			if (status != null && status.size() > 0) {
				messageId = ((SimpleDataObject) status.get(0)).getString("selectedMessage");
			}
			request.setAttribute("messageId", messageId);
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
			request.setAttribute("applicantData", applicantData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while changing status for applicant", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward getStepsIfOptional(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String positionId = selectionProcessForm.getPositionId();
				if (!Utils.isBlankOrNull(positionId)) {
					ArrayList steps = selectionProcessManager.getStepsIfFirstStepOptional(positionId);
					if (steps.size() > 1) {
						xmlFile = selectionProcessManager.getXMLIfFirstStepOptional(steps);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in sending note:", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward shortListApplicant(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		ArrayList<String> errors = new ArrayList<String>();
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String positionId = selectionProcessForm.getPositionId();
				String applicantId = selectionProcessForm.getApplicantId();
				String stepId = selectionProcessForm.getNextPositionStepId();
				String userId = (String) request.getSession(false).getAttribute("userId");
				boolean shortlisted = false;
				String[] applicantIds = applicantId.split(",");
				for (int i = 0; i < applicantIds.length; i++) {
					try {
						shortlisted = selectionProcessManager.shortListApplicant(applicantIds[i], positionId, stepId, userId, true);
						if (!shortlisted) {
							errors.add(TPLabels.getLabel("view_original_resume.error.shortlisting"));
							xmlFile = Utils.getXMLForError(errors);
						}
					} catch (InProcessException ex) {
						errors.add(TPLabels.getLabel("view_original_resume.error.inprocess"));
						xmlFile = Utils.getXMLForError(errors);
					} catch (ApplicantBlacklistedException e) {
						errors.add(TPLabels.getLabel("view_original_resume.error.blacklisted"));
						xmlFile = Utils.getXMLForError(errors);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in shortlist applicant:", e);
			errors.add(TPLabels.getLabel("view_original_resume.error.shortlisting"));
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward showfeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "commonError";
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String stepId = (String) request.getAttribute("sId");
			if(Utils.isBlankOrNull(stepId)){
				stepId = selectionProcessForm.getStepId(); 
			}
			String applicantId = (String) request.getAttribute("aId");
			if(Utils.isBlankOrNull(applicantId)){
				applicantId = selectionProcessForm.getApplicantId();
			}
			
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData aData = applicantManager.getApplicantSummaryData(applicantId);
			if (aData != null) {
				if (stepId.equals(aData.getApplicantStepId())) {
					request.setAttribute("showfeedback", "1");
					request.setAttribute("applicantId", applicantId);
					if (aData.getPositionStepLevel().equals(PositionConstants.STEP_LEVEL_SELECT) || aData.getPositionStepLevel().equals(PositionConstants.STEP_LEVEL_SHORTLIST)) {
						return select(mapping, actionForm, request, response);
					}
					if (aData.getPositionStepLevel().equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
						return accept(mapping, actionForm, request, response);
					}
				}
			}
			DashboardBC.setLeftPanel(request, userId);
			request.setAttribute("applicantData", aData);
			request.setAttribute("errorCode", ErrorConstants.ERR_FEEDBACK);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in display of feedback", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward select(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "select";

		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_SELECT;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		String userId = (String) request.getSession(false).getAttribute("userId");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

		SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
		List positionFilters = selectionProcessManager.getPositionFilters(userId, permissionSet);
		List stepTitles = selectionProcessManager.getStepTitles(userId, PositionConstants.STEP_LEVEL_SELECT, permissionSet);

		request.setAttribute("positionFilters", positionFilters);
		request.setAttribute("stepTitles", stepTitles);

		String JSSelectionStageArray =  SelectionProcessUtils.getJSArrayForSelectionStageOnSelectTab();
		request.setAttribute("JSSelectionStageArray", JSSelectionStageArray);

		//DashboardBC.setLeftPanel(request, userId);
		request.setAttribute("t", NavigationConstants.T_SELECT);

		return mapping.findForward(forward);
	}

	public ActionForward accept(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "accept";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_HIRE;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}

		String userId = (String) request.getSession(false).getAttribute("userId");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

		SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
		List positionFilters = selectionProcessManager.getPositionFilters(userId, permissionSet);
		List stepTitles = selectionProcessManager.getStepTitles(userId, PositionConstants.STEP_LEVEL_ACCEPT, permissionSet);

		request.setAttribute("positionFilters", positionFilters);
		request.setAttribute("stepTitles", stepTitles);

		//DashboardBC.setLeftPanel(request, userId);
		request.setAttribute("t", NavigationConstants.T_HIRE);

		return mapping.findForward(forward);
	}

	public ActionForward getSelectApplicantXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			String userRole = (String) request.getSession(false).getAttribute("userRoles");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List<SelectionProcessData> applicantsInProcess = selectionProcessManager.getInProcessApplicants(permissionSet, userRole, userId, selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getApplicantName(), selectionProcessForm.getStepName(), selectionProcessForm.getStepLevel(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId(),NavigationConstants.T_SELECT);			
			xmlFile = selectionProcessManager.getXMLForSelectStageApplicants(applicantsInProcess, selectionProcessForm.getActionRequired());
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward getHireApplicantXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			String userRole = (String) request.getSession(false).getAttribute("userRoles");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List<SelectionProcessData> applicantsInProcess = selectionProcessManager.getInProcessApplicants(permissionSet, userRole, userId, selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getApplicantName(), selectionProcessForm.getStepName(), selectionProcessForm.getStepLevel(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getPositionTypeExtInt(), selectionProcessForm.getSelectedUserId(),selectionProcessForm.getSourceId(),NavigationConstants.T_HIRE);
			xmlFile = selectionProcessManager.getXMLForHireStageApplicants(applicantsInProcess);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward getJoinedApplicantXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String filterByName = selectionProcessForm.getFilterByName();
			String filterByPosition = selectionProcessForm.getFilterByPosition();
			String filterByDuration = selectionProcessForm.getFilterByDuration();

			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			xmlFile = selectionProcessManager.getXmlForJoinedApplicants(userId, filterByName, filterByPosition, filterByDuration, permissionSet);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward massEmailSelect(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "massEmailSelect";
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_MASS_EMAIL;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_MASS_EMAILS, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			String userRole = (String) request.getSession(false).getAttribute("userRoles");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List applicantsInProcess = selectionProcessManager.getApplicantsForMassEmail(permissionSet, userRole, userId, selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getApplicantName(), selectionProcessForm.getStepName(), selectionProcessForm.getStepLevel(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getActionRequired(), selectionProcessForm.getSelectedUserId(), null, null, null,selectionProcessForm.getSelectedIds());
			request.setAttribute("applicants", applicantsInProcess);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while mass email select", e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward filterForCalllist(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "callListFilter";
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_CALL_LIST;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			String userRole = (String) request.getSession(false).getAttribute("userRoles");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");

			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List applicantsInProcess = selectionProcessManager.getApplicants(permissionSet, userRole, userId, selectionProcessForm.getDepartmentId(), selectionProcessForm.getPositionId(), selectionProcessForm.getApplicantName(), selectionProcessForm.getStepName(), selectionProcessForm.getStepLevel(), selectionProcessForm.getLocationTitle(), selectionProcessForm.getActionRequired(), selectionProcessForm.getSelectedUserId(), null, null, null);
			request.setAttribute("applicants", applicantsInProcess);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while mass email select", e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward setJoiningDate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "setJoiningDate";
	
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
			if (errors == null) {
				errors = new ActionErrors();
			}
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);

			if (request.getParameter("updated") != null) {
				// form is submitted
				String prevDate = selectionProcessForm.getLogDate();
				String currentDate = selectionProcessForm.getJoiningDate();
				if (!Utils.isBlankOrNull(currentDate)) {
					Date dt = Utils.convertToDate(currentDate, Utils.regEUDateFormat);
					if (dt == null) {
						errors.add("set_joining_date.error.date_invalid", new ActionError("set_joining_date.error.date_invalid"));
					}
				}
				if (errors.size() == 0) {
					// update joining date
					try {
						if (!prevDate.equalsIgnoreCase(currentDate)) {
							SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
							selectionProcessManager.updateApplicantPositionStatus(applicantId, applicantData.getApplicantPositionId(), applicantData.getApplicantStepId(), applicantData.getApplicantJoined(), currentDate, applicantData.getCtcOffered(), applicantData.getBasicOffered(), applicantData.getLevelOffered(), applicantData
									.getDesignationOffered(), applicantData.getInputSalaryVariable(), applicantData.getEmployeeCode(), null,applicantData.getJoiningBonus(),applicantData.getVariableOffered());
//							if(ApplicantConstants.APPLICANT_JOINED.equals(applicantData.getApplicantJoined())) {
//								selectionProcessManager.createApplicantJoiningHistory(applicantId, applicantData.getApplicantPositionId(), currentDate, applicantData.getCtcOffered(), applicantData.getLevelOffered(), applicantData.getDesignationOffered(), null);
//							}
							// add joining date change note
							Date fDate = Utils.convertToDate(prevDate, Utils.regEUDateFormat);
							Date tDate = Utils.convertToDate(currentDate, Utils.regEUDateFormat);
							String fromDate = "";
							String toDate = "";
							if (fDate != null) {
								fromDate = Utils.getDateConvertedToString(fDate, Utils.regDDMMMYYYYFormat);
							}
							if (tDate != null) {
								toDate = Utils.getDateConvertedToString(tDate, Utils.regDDMMMYYYYFormat);
							}

							String note = TPLabels.getLabel("set_joining_date.label.joining_date") + " ";
							if (!Utils.isBlankOrNull(fromDate) && !Utils.isBlankOrNull(toDate)) {
								note += TPLabels.getLabel("set_joining_date.label.modified_from") + " " + fromDate + " " + TPLabels.getLabel("set_joining_date.label.to") + " " + toDate;
							} else if (Utils.isBlankOrNull(fromDate)) {
								note += TPLabels.getLabel("set_joining_date.label.set_to") + " " + toDate;
							} else {
								note += TPLabels.getLabel("set_joining_date.label.removed");
							}
							CommunicationData cData = new CommunicationData();
							cData.setApplicantId(applicantId);
							cData.setUserId(userId);
							cData.setCommunicationType(SelectionProcessConstants.INTERACTION_NOTE);
							Calendar cal = new GregorianCalendar();
							Date dtLogDate = cal.getTime();
							cData.setCommunicationDate(new java.sql.Timestamp(dtLogDate.getTime()));
							cData.setCommunicationText(note);
							selectionProcessManager.addPhoneLog(cData);

						}
						request.setAttribute("update", "1");
					} catch (Exception e) {
						TPLogger.getLogger().error("Error while setting joining date", e);
						errors.add("set_joining_date.error.update", new ActionError("set_joining_date.error.update"));
					}
				}

			} else {
				Date dateJoined = applicantData.getApplicantDateJoined();
				String joinedDate = "";
				if (dateJoined != null) {
					joinedDate = Utils.getDateConvertedToString(dateJoined, Utils.regEUDateFormat);
				}
				selectionProcessForm.setJoiningDate(joinedDate);
			}
			if (errors.size() > 0) {
				request.setAttribute(Globals.ERROR_KEY, errors);
			}
			request.setAttribute("applicantData", applicantData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while mass email select", e);
		}

		return mapping.findForward(forward);
	}

	public ActionForward setFlag(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "setFlag";
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_SET_FLAG;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		String userId = (String) request.getSession(false).getAttribute("userId");
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List<SimpleDataObject> flags = selectionProcessManager.getAllFlags(userId);
			String jsArrayFlags = CommonUtils.getListJavaScriptArrayForFlags(flags);
			selectionProcessForm.setJsArrayFlags(jsArrayFlags);
			selectionProcessForm.setMode("saveFlags");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting data", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveFlags(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "setFlag";
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_SET_FLAG;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			String selectedFlagIds = selectionProcessForm.getSelectedIds();
			String userId = (String) request.getSession(false).getAttribute("userId");

			ApplicantManager applicantManager = new ApplicantManager();
			
			String[] applicantIds = applicantId.split(",");
			for (int i = 0; i < applicantIds.length; i++) {
				applicantManager.setApplicantFlags(applicantIds[i], selectedFlagIds, userId);
				TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantIds[i], TPIndexEvent.PRIORITY_HIGH));
			}
			
			request.setAttribute("update", "1");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in setting flag", e);
			ActionErrors actionErrors = new ActionErrors();
			actionErrors.add("set_flag.error.set_flag", new ActionError("set_flag.error.set_flag"));
			saveErrors(request, actionErrors);
		}
		return mapping.findForward(forward);
	}

	public ActionForward setFlagToApplicants(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "setFlag";
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_SET_FLAG;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");

			String applicantIds = selectionProcessForm.getApplicantIds();

			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List<SimpleDataObject> flags = selectionProcessManager.getAllFlags(userId);

			String jsArrayFlags = CommonUtils.getListJavaScriptArrayForFlags(flags);
			selectionProcessForm.setJsArrayFlags(jsArrayFlags);
			if (!Utils.isBlankOrNull(applicantIds)) {
				ArrayList<SimpleDataObject> flagApplicants = selectionProcessManager.getFlagsWithApplicantCount(applicantIds, userId);
				setSelectedAndTristateFlagIds(selectionProcessForm, applicantIds, flagApplicants);
			}
			selectionProcessForm.setMode("saveFlagForApplicants");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in categorize", e);
		}
		return mapping.findForward(forward);
	}

	private void setSelectedAndTristateFlagIds(SelectionProcessForm selectionProcessForm, String applicantIds, ArrayList<SimpleDataObject> flagApplicants) throws Exception {
		String[] applicantIdsArray = applicantIds.split(",");
		StringBuffer sbSelected = new StringBuffer();
		StringBuffer sbTristate = new StringBuffer();
		for (int i = 0; flagApplicants != null && i < flagApplicants.size(); i++) {
			SimpleDataObject sDo = flagApplicants.get(i);
			String flagId = sDo.getString("flagId");
			int applicantCount = sDo.getInt("count");
			if (applicantCount < applicantIdsArray.length) {
				sbTristate.append(flagId + ",");
			} else {
				sbSelected.append(flagId + ",");
			}
		}
		String selectedIds = sbSelected.toString();
		String tristateIds = sbTristate.toString();
		if (selectedIds.length() > 0) {
			selectedIds = selectedIds.substring(0, selectedIds.length() - 1);
		}
		if (tristateIds.length() > 0) {
			tristateIds = tristateIds.substring(0, tristateIds.length() - 1);
		}
		selectionProcessForm.setSelectedIds(selectedIds);
		selectionProcessForm.setTristateIds(tristateIds);
	}

	public ActionForward saveFlagForApplicants(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_SET_FLAG;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");

			String applicantId = selectionProcessForm.getApplicantIds();
			String selectedIds = selectionProcessForm.getSelectedIds();
			String tristateIds = selectionProcessForm.getTristateIds();

			ApplicantManager applicantManager = new ApplicantManager();
			applicantManager.setApplicantFlags(applicantId, selectedIds, tristateIds, userId);
			if (!Utils.isBlankOrNull(applicantId)) {
				String[] applicantIds = applicantId.split(",");
				for (int i = 0; i < applicantIds.length; i++) {
					TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantIds[i], TPIndexEvent.PRIORITY_HIGH));
				}
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error in set flag", e);
			ActionErrors errors = new ActionErrors();
			errors.add("search_applicant_home.error.set_flag", new ActionError("search_applicant_home.error.set_flag"));
			saveErrors(request, errors);
			return setFlagToApplicants(mapping, actionForm, request, response);
		}
		return mapping.findForward(forward);
	}

	public ActionForward hideShowInteraction(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessForm sForm = (SelectionProcessForm) actionForm;
		SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
		try {
			String communicationId = sForm.getCommunicationId();
			String communicationType = sForm.getCommunicationType();

			if (!Utils.isBlankOrNull(communicationId) && !Utils.isBlankOrNull(communicationType)) {
				switch (Integer.parseInt(communicationType)) {
				case SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED:
				case SelectionProcessConstants.INTERACTION_EMAIL_SENT:
					selectionProcessManager.changeEmailHideInteraction(communicationId);
					break;
				case SelectionProcessConstants.INTERACTION_INTERVIEW:
					selectionProcessManager.changeSelectionProcessHideInteraction(communicationId);
					break;
				case SelectionProcessConstants.INTERACTION_PHONE:
				case SelectionProcessConstants.INTERACTION_SMS:
				case SelectionProcessConstants.INTERACTION_NOTE:
					selectionProcessManager.changeCommunicationHideInteraction(communicationId);
					break;
				case SelectionProcessConstants.INTERACTION_MESSAGE:
					selectionProcessManager.changeMessageHideInteraction(communicationId);
					break;
				case SelectionProcessConstants.INTERACTION_STATUS_MESSAGE:
					selectionProcessManager.changeStatusMessageHideInteraction(communicationId);
					break;
				case SelectionProcessConstants.INTERACTION_OFFER_PROPOSAL:
					selectionProcessManager.changeOfferDetailsModifiedHideInteraction(communicationId);
					break;
				case SelectionProcessConstants.INTERACTION_OFFER_SHEET_GENERATION:
					selectionProcessManager.changeOfferDetailsModifiedHideInteraction(communicationId);
					break;
				default:
				}
				
				//update show/hide in activity table
				LatestActivityManager activityManager = new LatestActivityManager();
				activityManager.updateShowHide(communicationId,communicationType);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while seting flag", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward undoLastFeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String applicantId = selectionProcessForm.getApplicantId();
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				selectionProcessManager.undolastFeedback(applicantId);
			}
		} catch (AppointmentExistsException e) {
			TPLogger.getLogger().error("Error while undo last feedback ", e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add(TPLabels.getLabel("selection_feedback.error.undolastfeedback_appointment_exists"));
			xmlFile = Utils.getXMLForError(errors);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while undo last feedback", e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add(TPLabels.getLabel("selection_feedback.error.undolastfeedback"));
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public void setAttendeesInRequest(String appointmentId, HttpServletRequest request) {
		String attendeesList = "";
		try {
			CalendarManager calendarManager = new CalendarManager();
			ArrayList attendees = (ArrayList) calendarManager.getAppointmentAttendees(appointmentId);
			ArrayList<String> attendeesId = new ArrayList<String>();
			ArrayList<String> attendeesName = new ArrayList<String>();
			for (int i = 0; i < attendees.size(); i++) {
				SimpleDataObject interviewer = (SimpleDataObject) attendees.get(i);
				String uid = interviewer.getString("userId");
				String uname = interviewer.getString("userName");
				if (!Utils.isBlankOrNull(uid) && !Utils.isBlankOrNull(uname)) {
					attendeesId.add(uid);
					attendeesName.add(uname);
				}
			}
			if (attendeesId.size() > 0 && attendeesName.size() > 0) {
				attendeesList = CommonUtils.getListJavaScriptArray(attendeesId, attendeesName);
				request.setAttribute("attendeesId", attendeesId);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting attendees", e);
		}
		request.setAttribute("attendeesList", attendeesList);
	}

	public ActionForward addReminder(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addReminder";

		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			String reminderId = selectionProcessForm.getReminderId();
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
			request.setAttribute("applicantData", applicantData);

			if (!Utils.isBlankOrNull(reminderId)) {
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				SelectionProcessData selectionProcessData = selectionProcessManager.getReminderData(reminderId);
				applicantId = String.valueOf(selectionProcessData.getApplicantId());
				String dateTime = selectionProcessData.getReminderDate();
				Date date = Utils.convertToDate(dateTime, DateConstants.DB_DATE_TIME_PATTERN);
				String dateStr = Utils.getDateConvertedToString(date, Utils.regEUDateFormat);
				String timeStr = Utils.getDateConvertedToString(date, "hh:mm a");
				selectionProcessForm.setReminderDate(dateStr);
				selectionProcessForm.setReminderTime(timeStr);
				selectionProcessForm.setReminderDesc(selectionProcessData.getReminderDesc());
			} else {
				String reminderDate = selectionProcessForm.getReminderDate();
				Calendar calendar = new GregorianCalendar();
				calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) + 5));
				Date dt = calendar.getTime();
				if (!Utils.isBlankOrNull(reminderDate)) {
					selectionProcessForm.setReminderDate(Utils.getDateConvertedToString(Utils.convertToDate(reminderDate, Utils.redYYYYMMDDFormat), Utils.regEUDateFormat));
				} else {
					selectionProcessForm.setReminderDate(Utils.getDateConvertedToString(dt, Utils.regEUDateFormat));
				}

				selectionProcessForm.setReminderTime(Utils.getDateConvertedToString(dt, "hh:mm a"));
			}
			if ("0".equals(applicantId)) {
				selectionProcessForm.setApplicantId("");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting old reminder", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveReminder(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			try {
				String userId = (String) request.getSession(false).getAttribute("userId");
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String applicantId = selectionProcessForm.getApplicantId();
				String reminderId = selectionProcessForm.getReminderId();
				String date = selectionProcessForm.getReminderDate();
				String time = selectionProcessForm.getReminderTime();
				String description = selectionProcessForm.getReminderDesc();

				String dateTimeStr = date + " " + time; // convert date/time in
				// format
				Date dateTime = Utils.convertToDate(dateTimeStr, "dd/MM/yyyy hh:mm a");
				if (dateTime.before(Calendar.getInstance().getTime())) {
					ArrayList<String> errors = new ArrayList<String>();
					errors.add(TPLabels.getLabel("add_reminder.label.valid_date"));
					xmlFile = Utils.getXMLForError(errors);
				} else {
					SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
					selectionProcessManager.saveAndUpdateReminder(userId, applicantId, reminderId, dateTime, description);
				}

			} catch (Exception e) {
				TPLogger.getLogger().error("Error updating reminder", e);
			}
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward searchApplicant(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String applicantName = selectionProcessForm.getApplicantName();
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				ArrayList applicants = selectionProcessManager.searchCandidate(applicantName);
				xmlFile = selectionProcessManager.getApplicantXML(applicants);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while searching applicant", e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward changeConfidentiality(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				String applicantId = selectionProcessForm.getApplicantId();
				String confidentiality = selectionProcessForm.getApplicantIsConfidential();
				ApplicantManager applicantManager = new ApplicantManager();
				applicantManager.changeConfidentiality(applicantId,confidentiality);
				TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("1");
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward bulkMoveApplicantUpOrDown(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "bulkMoveApplicantUpOrDown";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		String userId = (String) request.getSession(false).getAttribute("userId");
		try {
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			String screenType = selectionProcessForm.getScreenType();
			String tab = selectionProcessForm.getTab();
			String[] applicantIds = applicantId.split(",");
			ArrayList<StepData> commonSteps = new ArrayList<StepData>();
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			ArrayList<SelectionProcessData> applicants = selectionProcessManager.getLastProcessDataForBulkFeedback(applicantIds, permissionSet);
			ArrayList<SelectionProcessData> feedback = getConstructedListForBulkFeedback(applicants, screenType, permissionSet, userId, selectionProcessManager, commonSteps);
			selectionProcessForm.setSessionId(String.valueOf(System.currentTimeMillis()));
			//set all commonSteps
			String jSCommonStepsArray = CommonUtils.getListJavaScriptArrayWithProperties(commonSteps, "stepId", "stepTitle");
			List allPositions = selectionProcessManager.getOtherOpenPositions(null);
			
			request.setAttribute("feedback", feedback);
			request.setAttribute("jSCommonStepsArray", jSCommonStepsArray);
			request.setAttribute("allPositions", allPositions);
			
			request.getSession(false).setAttribute("feedbackList", null);
			if(tab.equals(SelectionProcessConstants.TAB_SELECT)){
				request.setAttribute("t", NavigationConstants.T_SELECT);
			}else if(tab.equals(SelectionProcessConstants.TAB_HIRE)){
				request.setAttribute("t", NavigationConstants.T_HIRE);
			} 
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	private ArrayList<StepData> getAllCommonPositionStep(ArrayList<StepData> toSteps, ArrayList<StepData> secondSteps, ArrayList<StepData> allSteps) {
		allSteps = new ArrayList<StepData>();
		try {			
			if(secondSteps==null || secondSteps.size()==0){
				for (int i = 0; i < toSteps.size(); i++) {
					StepData sd = toSteps.get(i);
					String stepTitle = sd.getStepTitle();
					
					StepData stepData = new StepData();
					stepData.setStepId(allSteps.size()+1);
					stepData.setStepTitle(stepTitle);
					allSteps.add(stepData);
				}
			}else{
				for (int i = 0; i < toSteps.size(); i++) {
					StepData sData = toSteps.get(i);
					String stepName = sData.getStepTitle();
					
					for (int j = 0; j < secondSteps.size(); j++) {
						StepData sd = secondSteps.get(j);
						String stepTitle = sd.getStepTitle();
						if(stepTitle.equals(stepName)){
							StepData stepData = new StepData();
							stepData.setStepId(allSteps.size()+1);
							stepData.setStepTitle(stepTitle);
							allSteps.add(stepData);
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return allSteps;
	}

	private ArrayList<SelectionProcessData> getConstructedListForBulkFeedback(ArrayList<SelectionProcessData> applicants, String action, PermissionSet permissionSet, String userId,
			SelectionProcessManager selectionProcessManager, ArrayList<StepData> commonSteps) throws Exception {
		ArrayList<SelectionProcessData> feedback = new ArrayList<SelectionProcessData>();
		ArrayList<StepData> allSteps = new ArrayList<StepData>();
		for (int i = 0; i < applicants.size(); i++) {
			SelectionProcessData selectionProcessData = applicants.get(i);
			
			String screenType = "";
			String offeredStepIds = "";
			String joinedStepIds = "";
			String jsIntervierwersArray ="";
			
			boolean isAuthorisedToDecision = selectionProcessManager.isUserAuthorizedToMakeDecision(userId, Integer.parseInt(selectionProcessData.getCurrentStepId()));
			boolean isAuthorisedToSchedule = selectionProcessManager.isUserAuthorizedToScheduleOrConfirmAttendance(userId, ""+selectionProcessData.getApplicantId(), permissionSet);
			
			if (isAuthorisedToDecision) {
				// get single feedback object constructed
				screenType = SelectionProcessConstants.SCREEN_TYPE_FWD;//forward to next step
			}
			if(isAuthorisedToSchedule){
				if (("" + PositionConstants.STEP_SCHEDULED).equals(selectionProcessData.getPositionStepIsscheduled()) && !selectionProcessData.getPositionStepIdTo().equals(SelectionProcessConstants.STEP_ON_HOLD)) {
					if (Utils.isBlankOrNull(selectionProcessData.getAppointmentId())) {
						screenType = SelectionProcessConstants.SCREEN_TYPE_SCH;// schedule the step
					} else if (Utils.isDateInFuture(selectionProcessData.getAppointmentFromDate())) {
						screenType = SelectionProcessConstants.SCREEN_TYPE_NIR; //not interested reject
					} else {
						if(selectionProcessData.getAppointmentStatus().equals(""+CalendarConstants.APPOINTMENT_STATUS_NOSHOW)) {
							screenType = SelectionProcessConstants.SCREEN_TYPE_SCH;// schedule the step
						} else if(!selectionProcessData.getPositionStepIdTo().equals(SelectionProcessConstants.STEP_ATTENDED)) {
							screenType = SelectionProcessConstants.SCREEN_TYPE_CAT;//confirm attendance
						} else if(selectionProcessData.getPositionStepIdTo().equals(SelectionProcessConstants.STEP_REPEAT)) {
							screenType = SelectionProcessConstants.SCREEN_TYPE_SCH;//confirm attendance
						}
					}
				}
			}
			
			boolean isAssignToUser = selectionProcessManager.isAutorisedUserForPositionStep(permissionSet, selectionProcessData.getCurrentStepId(), userId);
			
			if(screenType.equals("") && isAssignToUser && selectionProcessData.getPositionStepIdTo().equals(SelectionProcessConstants.STEP_ON_HOLD)){
				screenType = SelectionProcessConstants.SCREEN_TYPE_HLD; // ON hold position
			}
			
			if(!action.equals(screenType) && !action.equals(SelectionProcessConstants.SCREEN_TYPE_POS)){
				screenType ="";
			}else if(action.equals(SelectionProcessConstants.SCREEN_TYPE_POS)){
				screenType = SelectionProcessConstants.SCREEN_TYPE_POS;
			}
			
			ArrayList<StepData> toSteps = getToStepsForBulkFeedback(selectionProcessData.getProcessId(), selectionProcessData.getPositionId(), selectionProcessData.getCurrentStepId(), screenType,
					selectionProcessManager);
			//get intersection of all steps to select bulk position
			allSteps = getAllCommonPositionStep(toSteps, allSteps, allSteps);
			
			String jSToStepsArray = CommonUtils.getListJavaScriptArrayWithProperties(toSteps, "stepId", "stepTitle");

			String communicationId = null;
			
			ArrayList<UserData> interviewers = selectionProcessManager.getPositionInterviewers(communicationId, "" + selectionProcessData.getCurrentStepId());
			jsIntervierwersArray = CommonUtils.getListJavaScriptArrayWithProperties(interviewers, "userId", "userName");
			
			//set communicationId only when applicant appointment is scheduled -  to calculate INterviewers
			if (Utils.isBlankOrNull(communicationId)) {
				communicationId = selectionProcessManager.getCommunicationId(""+ selectionProcessData.getApplicantId());
				selectionProcessData.setCommunicationId(communicationId);
			}
			
			if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_FWD)) {
				offeredStepIds = getStepIdsInStage(toSteps, PositionConstants.STEP_LEVEL_ACCEPT);
				joinedStepIds = SelectionProcessConstants.STEP_JOIN;
			}

			List positions = selectionProcessManager.getOtherOpenPositions(selectionProcessData.getPositionId());
			
			selectionProcessData.setAttribute("positions", positions);			
			selectionProcessData.setAttribute("screenType", screenType);
			selectionProcessData.setAttribute("toSteps", toSteps);
			selectionProcessData.setAttribute("jSToStepsArray", jSToStepsArray);				
			selectionProcessData.setAttribute("offeredStepIds", offeredStepIds);
			selectionProcessData.setAttribute("joinedStepIds", joinedStepIds);
			selectionProcessData.setAttribute("jsIntervierwersArray", jsIntervierwersArray);
			
			feedback.add(selectionProcessData);
		}
		// set allCommenStep to commonStep
		for (int i = 0; i < allSteps.size(); i++) {
			commonSteps.add(allSteps.get(i));
		}
		
		return feedback;
	}
	
	private String getStepIdsInStage(ArrayList<StepData> toSteps, String stepLevel) {
		String stepIds = "";
		if (toSteps != null) {
			for (int i = 0; i < toSteps.size(); i++) {
				StepData stepData = toSteps.get(i);
				if (stepData.getStepLevel().equals(stepLevel)) {
					if (stepIds.length() > 0) {
						stepIds += ",";
					}
					stepIds += stepData.getStepId();
				}
			}
		}
		return stepIds;
	}
	
	private ArrayList<StepData> getToStepsForBulkFeedback(String communicationId, String positionId, String fromStepId, String screenType, SelectionProcessManager selectionProcessManager) {
		ArrayList<StepData> toSteps = new ArrayList<StepData>();
		if (screenType.equals(SelectionProcessConstants.SCREEN_TYPE_FWD)) {
			toSteps = selectionProcessManager.getAllNextStepsForPosition(positionId, fromStepId, null, null);
			int noOfNextSteps = 0;
			if (toSteps != null) {
				noOfNextSteps = toSteps.size();
				toSteps = selectionProcessManager.getStepsTillNextMandatory(toSteps);
			}
			if (noOfNextSteps == toSteps.size()) {
				toSteps.add(new StepData(Integer.parseInt(SelectionProcessConstants.STEP_JOIN), SelectionProcessConstants.STEP_TITLE_JOINED, PositionConstants.STEP_LEVEL_ACCEPT));
			}

		}
		return toSteps;
	}
	
	public ActionForward saveBulkFeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String tab = "select";
		try {
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String userId = (String) request.getSession(false).getAttribute("userId");
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();

			String applicantIds = selectionProcessForm.getApplicantId();
			tab = selectionProcessForm.getTab();
			String sessionId = selectionProcessForm.getSessionId();
			String[] applicantStrIds = applicantIds.split(",");
			ArrayList<ConflictApplicantData> feedbackList = new ArrayList<ConflictApplicantData>();

			for (int i = 0; i < applicantStrIds.length; i++) {
				String commonId = applicantStrIds[i];
				String decision = request.getParameter("decision_" + commonId);
				if (!Utils.isBlankOrNull(decision)) {
					ConflictApplicantData data = new ConflictApplicantData();
					String toStep = request.getParameter("tostep_" + commonId);
					String comment = request.getParameter("comment_" + commonId);
					String fromStep = request.getParameter("fromstep_" + commonId);
					String joiningDate = request.getParameter("joiningDate_" + commonId);
					String appointmentDate = request.getParameter("appointmentDate_" + commonId);
					String fromTime = request.getParameter("fromTime_" + commonId);
					String attendeesId = request.getParameter("inetrviewerid_" + commonId);
					String communicationId = request.getParameter("communicationid_" + commonId);
					String appointmentId = request.getParameter("appointmentid_" + commonId);
					String positionId = request.getParameter("positionid_" + commonId);
					String ctcOffered = request.getParameter("ctcOffered_" + commonId);
					String basicOffered = request.getParameter("basicOffered_" + commonId);
					//field added
					String joiningBonus = request.getParameter("joiningBonus_" + commonId);
					String variableOffered = request.getParameter("variableOffered_" + commonId);
					
					String levelOffered = request.getParameter("levelOffered_" + commonId);
					String designationOffered = request.getParameter("designationOffered_" + commonId);
					String inputSalaryVariable = request.getParameter("inputSalaryVariable_" + commonId);
					String employeeCode = request.getParameter("employeeCode_" + commonId);
					String _position = request.getParameter("newposid_" + commonId);
					String _step = request.getParameter("newstepid_" + commonId);
					String feedbackFormId = request.getParameter("feedbackformid_" + commonId);
					
					data.setApplicantId(commonId);
					String positionStepIdTo = decision;
					if (decision.equals(SelectionProcessConstants.DECISION_APPROVED)) {
						positionStepIdTo = toStep;
					}else if(decision.equals(SelectionProcessConstants.DECISION_POSITION)){
						positionStepIdTo = SelectionProcessConstants.STEP_REJECT;
					}

					data.setFromStepId(fromStep);
					data.setToStepId(positionStepIdTo);
					data.setCtc(ctcOffered);
					data.setBasicOffered(basicOffered);
					data.setLevelOffered(levelOffered);
					data.setDesignationOffered(designationOffered);
					data.setInputSalaryVariable(inputSalaryVariable);
					data.setComment(comment);
					data.setJoiningDate(joiningDate);
					data.setAppointmentDate(appointmentDate);
					data.setFromTime(fromTime);
					data.setAttendeesId(attendeesId);
					data.setCommunicationId(communicationId);
					data.setAppointmentId(appointmentId);
					data.setPositionId(positionId);
					data.setEmployeeCode(employeeCode);
					data.setNewPosition(_position);
					data.setNewStep(_step);
					data.setFeedbackFormId(feedbackFormId);
					//set values
					data.setJoiningBonus(joiningBonus);
					data.setVariableOffered(variableOffered);
					
					feedbackList.add(data);
				}
			}

			request.getSession(false).setAttribute("feedbackList", null);
			
			//generate trait data map
			Map<String, String> traitData = null;
			HashMap<String, String> ratingsIdValues = null;
			HashMap<String, String> multipleSelectIdValues = null;
			//get system generated feedback field
			FeedbackFieldsManager feedbackFieldsManager = new FeedbackFieldsManager();
			
			
			for (int i = 0; feedbackList != null && i < feedbackList.size(); i++) {
				ConflictApplicantData cData = feedbackList.get(i);
				try {
					if (!cData.getToStepId().equals(SelectionProcessConstants.DECISION_SCHEDULE)) {

							boolean moveToJoined = false;
							if(cData.getToStepId().equals(SelectionProcessConstants.STEP_JOIN)){
								moveToJoined = true;
							}
							String deleteAppointments = "FALSE";
							if (SelectionProcessConstants.NOT_INTERESTED_REJECT.equalsIgnoreCase(cData.getToStepId())) {
								deleteAppointments = Boolean.TRUE.toString();
							}

							if(cData.getCommunicationId()!=null){
								if(cData.getCommunicationId().equals("null")){
									cData.setCommunicationId("");
								}
							}
							
							ratingsIdValues = new HashMap<String, String>();
							multipleSelectIdValues = new HashMap<String, String>();
							traitData = new HashMap<String, String>();

							// buid trait data 
							ArrayList<TraitData> traitList = selectionProcessManager.getTempTrait(sessionId, cData.getApplicantId(), userId);
							if(traitList!=null && traitList.size()>0){
								buidTraitData(traitList,traitData,ratingsIdValues,multipleSelectIdValues, userId);
							}else if(!Utils.isBlankOrNull(cData.getFromStepId())/* && Integer.parseInt(cData.getToStepId()) > 0*/){
								String fieldId = feedbackFieldsManager.getSystemGeneratedFieldId(cData.getFromStepId());
								if(!Utils.isBlankOrNull(fieldId)){
									String traitId = fieldId + "_"+userId;
									traitData.put(traitId, cData.getComment());
								}
								}
							/**
							 * need to change
							 */
							String clientIpAddr = getClientIpAddr(request);
							selectionProcessManager.saveSelectionProcessResult(cData.getApplicantId(), cData.getPositionId(), 
									cData.getFromStepId(), cData.getToStepId(), userId, traitData, ratingsIdValues, 
									multipleSelectIdValues, moveToJoined, cData.getJoiningDate(), cData.getCtc(), 
									cData.getBasicOffered(), cData.getLevelOffered(), cData.getDesignationOffered(), 
									cData.getInputSalaryVariable(), cData.getCommunicationId(), deleteAppointments, 
									cData.getNewPosition(), cData.getNewStep(), cData.getApplicantId(), cData.getAttendeesId(), 
									cData.getEmployeeCode(), null, cData.getFeedbackFormId(), clientIpAddr,cData.getJoiningBonus(),cData.getVariableOffered());
					} else {
						setAppointment(cData, userId, permissionSet);
					}
				} catch (Exception e) {
					TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return bulkMoveApplicantUpOrDown(mapping, actionForm, request, response);
		}
		
		if(tab.equals(SelectionProcessConstants.TAB_SELECT)){
			return select(mapping, actionForm, request, response);
		} else{
			return accept(mapping, actionForm, request, response);
		}	
	}
	
	private void buidTraitData(ArrayList<TraitData> traitList, Map<String, String> traitData,
			HashMap<String, String> ratingsIdValues, HashMap<String, String> multipleSelectIdValues, String userId) {
		try {
			for (int i = 0; i < traitList.size(); i++) {
				TraitData tData = traitList.get(i);
				String key = tData.getFeedbackFormFieldId()+SelectionProcessConstants.UNDERSCORE+userId;
				traitData.put(key, tData.getTraitComment());
				if(!Utils.isBlankOrNull(tData.getRatingFieldId())){
					ratingsIdValues.put(key, tData.getRatingFieldId());
				}
				if(!Utils.isBlankOrNull(tData.getMultipleSelectFieldId())){
					multipleSelectIdValues.put(key, tData.getMultipleSelectFieldId());
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}

	private void setAppointment(ConflictApplicantData cData, String userId, PermissionSet permissionSet) {
		try {
			CalendarManager calendarManager = new CalendarManager();
			Date fromDateTime = Utils.convertToDate(cData.getAppointmentDate() + " " + cData.getFromTime(), Utils.regDateTimeFormat);
			Date toDateTime = Utils.adjustDateBy(fromDateTime, Calendar.MINUTE, 30);
			String fromTime = Utils.getDateConvertedToString(fromDateTime, DateConstants.DB_DATE_TIME_PATTERN);
			String toTime = Utils.getDateConvertedToString(toDateTime, DateConstants.DB_DATE_TIME_PATTERN);
			PositionManager positionManager = new PositionManager();
			String positionName = positionManager.getPositionName(cData.getPositionId());
			String stepName = positionManager.getPositionStepName(Integer.parseInt(cData.getFromStepId()));
			String subject = stepName + " for " + positionName;

			calendarManager.createNewAppointment(subject, fromTime, toTime, userId, cData.getApplicantId(),
					cData.getPositionId(), cData.getFromStepId(), "0", "0", "0","0", "0", "0", 
					"", "", "", "", "", "", cData.getAttendeesId(), ""+CalendarConstants.APPOINTMENT_STATUS_TENTATIVE, CalendarConstants.SEND_EMAIL_YES,"","");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public ActionForward applicantSingleFeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}

		String forward = "applicantSingleFeedback";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		String userId = (String) request.getSession(false).getAttribute("userId");

		FeedbackData feedbackData = null;
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantId = selectionProcessForm.getApplicantId();
			String communicationId = selectionProcessForm.getCommunicationId();
			String sessionId = selectionProcessForm.getSessionId();
			boolean isEdit = true;
			boolean isMoveUpOrDown = false;

			if (Utils.isBlankOrNull(communicationId)) {
				isEdit = false;
				communicationId = selectionProcessManager.getCommunicationId(applicantId);
				selectionProcessForm.setCommunicationId(communicationId);
			}

			if (Utils.isBlankOrNull(communicationId)) {
//				isMoveUpOrDown = true;
				feedbackData = selectionProcessManager.getCurrentStepData(applicantId);
			} else {
				feedbackData = selectionProcessManager.getFeedBackDataWithProcessId(selectionProcessForm.getCommunicationId());
			}
			
			selectionProcessForm.setCurrentPositionStepId("" + feedbackData.getFromStepData().getStepId());
			List<StepData> steps = selectionProcessManager.getNextStepData(feedbackData.getPositionId(), "" + feedbackData.getFromStepData().getStepId());
			if (steps == null) {
				StepData stepTo = new StepData();
				stepTo.setStepTitle(SelectionProcessConstants.STEP_TITLE_JOINED);
				steps = new ArrayList<StepData>();
				steps.add(stepTo);
				if (isEdit && !SelectionProcessConstants.STEP_JOIN.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
					selectionProcessForm.setNextPositionStepId("" + feedbackData.getToStepData().getStepId());
				} else if (isEdit && SelectionProcessConstants.STEP_JOIN.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
					selectionProcessForm.setNextPositionStepId("");
				} else {
					// selectionProcessForm.setNextPositionStepId("");
					selectionProcessForm.setNextPositionStepId(SelectionProcessConstants.STEP_INVALID);
				}
			} else if (!isEdit) {
				// selectionProcessForm.setNextPositionStepId("" + ((StepData)
				// steps.get(steps.size() - 1)).getStepId());
				selectionProcessForm.setNextPositionStepId(SelectionProcessConstants.STEP_INVALID);
			} else {
				selectionProcessForm.setNextPositionStepId("" + feedbackData.getToStepData().getStepId());
			}
			feedbackData.setNextSteps(steps);
			

			/* Get position interviewers */
			List<UserData> interviewers = selectionProcessManager.getPositionInterviewers(communicationId, "" + feedbackData.getFromStepData().getStepId());
			boolean doDisplaySaveButton = false;
			if (interviewers != null && interviewers.size() > 0) {
				Iterator<UserData> itr = interviewers.iterator();
				while (itr.hasNext()) {
					UserData data = itr.next();
					if (userId.equalsIgnoreCase("" + data.getUserId())) {
						doDisplaySaveButton = true;
						break;
					}
				}
			}
			Iterator<UserData> itr = interviewers.iterator();
			while (itr.hasNext()) {
				UserData data = itr.next();
				if (!userId.equalsIgnoreCase("" + data.getUserId())) {
					itr.remove();
				}
			}

			String traitIds = selectionProcessManager.getTraitIdsAsString(feedbackData.getFromStepData().getTraits(), userId,false);
			selectionProcessForm.setTraitIds(traitIds);

			// set trait values from temp table if exsits
			ArrayList<TraitData> traitList = selectionProcessManager.getTempTrait(sessionId, applicantId, userId);
			if(traitList!=null && traitList.size()>0){
				ArrayList<TraitData> traits = feedbackData.getFromStepData().getTraits();
				for (int i = 0; i < traits.size(); i++) {
					TraitData trait = traits.get(i);
					for (int j = 0; j < traitList.size(); j++) {
						TraitData tDatanew = traitList.get(j);
						if(trait.getFeedbackFormFieldId().equals(tDatanew.getFeedbackFormFieldId())){
							trait.setRatingFieldId(tDatanew.getRatingFieldId());
							trait.setTraitComment(tDatanew.getTraitComment());
							trait.setInterviewerId(Integer.parseInt(userId));
						}
					}
				}
				feedbackData.getFromStepData().setTraits(traits);
			}
		
			Map traitData = selectionProcessManager.getTraitData(isMoveUpOrDown, feedbackData.getFromStepData(), interviewers, userId);
			request.setAttribute("feedbackData", feedbackData);
			request.setAttribute("interviewers", interviewers);
			request.setAttribute("traitData", traitData);
			request.setAttribute("doDisplaySaveButton", doDisplaySaveButton);

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveTempTraits(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "moveApplicantUpOrDown";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),((SelectionProcessForm) actionForm).getPositionId(),null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessManager selectionProcessManager = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String sessionId = selectionProcessForm.getSessionId();
			String userId = (String) request.getSession(false).getAttribute("userId");
			String applicantId = selectionProcessForm.getApplicantId();

			Map<String, String> traitData = new HashMap<String, String>();
			HashMap<String, String> ratingsIdValues = new HashMap<String, String>();

			String traitIds = selectionProcessForm.getTraitIds();
			if (traitIds != null && traitIds.length() > 0) {
				String[] traits = traitIds.split(SelectionProcessConstants.COMMA);
				for (int indx = 0; indx < traits.length; indx++) {
					String feedback = request.getParameter(SelectionProcessConstants.TRAIT_ + traits[indx]);
					traitData.put(traits[indx], feedback);
					String ratingFieldId = (String) request.getParameter(SelectionProcessConstants.RATING_ + traits[indx]);
					if (!Utils.isBlankOrNull(ratingFieldId)) {
						ratingsIdValues.put(traits[indx], ratingFieldId);
						if (traitData.get(traits[indx]) == null) {
							traitData.put(traits[indx], null);
						}
					}
				}
			}
			
			selectionProcessManager.addTempTraits(sessionId, applicantId, userId, traitData, ratingsIdValues);
			request.setAttribute("update", "1");
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return applicantSingleFeedback(mapping, actionForm, request, response);
		}
		return mapping.findForward(forward);
	}
	
	
	public ActionForward selectBulkAction(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		} 
		String forward = "selectBulkAction";
		
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward getOfferSheetTemplateVariableVal(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm form = (SelectionProcessForm) actionForm;
				Integer[] permissions = new Integer[2];
				permissions[0] = PermissionConstants.PERMISSION_HIRE;
				permissions[1] = PermissionConstants.PERMISSION_GENERATE_OFFER;		
				if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions, form.getApplicantId(),null,null)) {
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
				} 
				
				OfferSheetManager offerSheetManager = new OfferSheetManager();
				//xmlFile = offerSheetManager.getTemplateVariableValue(form.getApplicantId(), form.getSelectedOfferSheetTemplate(), form.getTemplateVariable(), form.getTemplateVariableAttribute());
				offerSheetManager.updateOfferSheetTemplateVariableAttribute(form.getSelectedOfferSheetTemplate(), form.getTemplateVariable(), form.getTemplateVariableAttribute());
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			xmlFile = Utils.getXMLForError();
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}	
	
	public ActionForward printFeedback(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		ArrayList<FeedbackFormView> reportData = null;
		ArrayList<FeedbackFormView> finalReportData = new ArrayList<FeedbackFormView>();
		SelectionProcessManager selectionProcessManager = null;
		String forward = "viewreport";
		String processId = null;
		try{
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			selectionProcessManager = new SelectionProcessManager();
			String userId = (String) request.getSession(false).getAttribute("userId");
			String applicantId = selectionProcessForm.getApplicantId();
			String positionId = selectionProcessForm.getPositionId();
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			String feedbackType = selectionProcessForm.getFeedbackType();
			boolean printOnLoad = printFeedbackOnLoad(feedbackType);
			
			processId = selectionProcessManager.getApplicantProcessIds(applicantId,positionId);
			selectionProcessForm.setReportType(SelectionProcessConstants.REPORT_TYPE_CONSOLIDATED);
			if(Utils.isBlankOrNull(selectionProcessForm.getReportFormat())){
				selectionProcessForm.setReportFormat(ReportConstants.FORMAT_HTML);
			}
			selectionProcessForm.setShowIfExist("true");
			
			if(!Utils.isBlankOrNull(processId)){
				StringTokenizer st = new StringTokenizer(processId,",");
				while (st.hasMoreTokens()) {
					reportData = (ArrayList<FeedbackFormView>)getReportData(selectionProcessForm,st.nextToken(),userId,permissionSet,feedbackType);
					if(reportData!=null){
						finalReportData.addAll(reportData);
						reportData.clear();	
					}
				}	
			}
			
			String jrXMLName = ReportConstants.JRXML_ALL_CONSOLIDATED_FEEDBACK_FORM_REPORT;
			HashMap<String,String> params = getParamsToPrintFeedback(permissionSet);
			String outputFileName = request.getSession(false).getId() + String.valueOf(System.currentTimeMillis()) + ".html";
			ReportManager reportManager = new ReportManager();
			finalReportData = selectionProcessManager.validateFinalReportData(applicantId, finalReportData, feedbackType, permissionSet);
			String clientIpAddr = getClientIpAddr(request);
			reportManager.generateReport(jrXMLName, outputFileName, params, (ArrayList<FeedbackFormView>) finalReportData,
					ReportConstants.FORMAT_HTML, userId, printOnLoad, clientIpAddr);
			finalReportData.clear();
			request.setAttribute("fileName", outputFileName);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward checkIfSourceEmployeeExistInSelectionProcessStepForPosition(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		ArrayList<String> errors = null;

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String applicantIds = selectionProcessForm.getApplicantId();		
			String moveToStep = selectionProcessForm.getNextPositionStepId();
			String positionId = selectionProcessForm.getPositionId();
			String[] applicantIdsArray = applicantIds.split(CommonConstants.DEFAULT_DELIMITER);
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			errors = new ArrayList<String>();
			for(String applicantId: applicantIdsArray){
				if(Utils.isBlankOrNull(moveToStep)){
					moveToStep= selectionProcessManager.getStepToShortlist(positionId);
				}		
						
				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
				if(applicantData.getSourceTypeId().equals("5")){			
					
					ArrayList<LoginData> usersForSource = applicantManager.getUsersForSource(applicantData.getApplicantSourceId()+"");
				
					if(usersForSource.size()>0){
						LoginData sourceUser = usersForSource.get(0);
						
						
						List<SimpleDataObject> usersPresentInSelectionProcessStep = selectionProcessManager.getUsersPresentInSelectionProcessStep(moveToStep);
						for (SimpleDataObject sdo : usersPresentInSelectionProcessStep) {
							if (sourceUser.getUserId().equals(sdo.getString("userId"))) {							
								errors.add(sdo.getString("userTitle")+CommonConstants.DEFAULT_DELIMITER+applicantData.getApplicantName());
								break;
							}
						}		
					}			
				}
			}
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	
	private ArrayList<FeedbackFormView> getReportData(SelectionProcessForm selectionProcessForm,String communicationId,String userId,PermissionSet permissionSet,String feedbackType) {
		SelectionProcessManager selectionProcessManager = null;
		ArrayList<FeedbackFormView> reportData = null;
		try {
			selectionProcessManager = new SelectionProcessManager();
			//FeedbackData feedbackData = selectionProcessManager.getFeedBackDataWithProcessId(communicationId,feedbackType);
			FeedbackData feedbackData = selectionProcessManager.getAllFeedBackDataWithProcessId(communicationId, feedbackType, null);
			if(feedbackData!=null){
				boolean showIfExist = selectionProcessForm.getShowIfExist()!=""? Boolean.parseBoolean(selectionProcessForm.getShowIfExist()):false;

				boolean isUserAuthorizedToMakeDecision = selectionProcessManager.isUserAuthorizedToMakeDecision(userId, feedbackData.getFromStepData().getStepId());
				boolean isUserAtUpperLevelInFunnel = selectionProcessManager.isUserAtUpperLevelInFunnel(feedbackData.getPositionId(), "" + feedbackData.getFromStepData().getStepId(), userId);
				boolean isFeedbackLive = selectionProcessManager.isFeedbackLive(communicationId, "" + feedbackData.getApplicantId());

				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantSummaryData(String.valueOf(feedbackData.getApplicantId()));
				ArrayList<UserData> interviewers = new ArrayList<UserData>();
				Map traitData = null;

				if (SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT.equalsIgnoreCase("" + feedbackData.getToStepData().getStepId())) {
					traitData = selectionProcessManager.getTraitData(false, feedbackData.getFromStepData(), null, null);
				} else {
					interviewers = selectionProcessManager.getPositionInterviewers(communicationId, "" + feedbackData.getFromStepData().getStepId());

					Iterator itr = interviewers.iterator();
					while (itr.hasNext()) {
						UserData data = (UserData) itr.next();

						if (!isUserAuthorizedToMakeDecision && !isUserAtUpperLevelInFunnel && !userId.equalsIgnoreCase("" + data.getUserId()) && permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
							if (!Utils.isBlankOrNull(feedbackData.getString("applicantStepId")) && isFeedbackLive) {
								itr.remove();
							}
						}
					}
					traitData = selectionProcessManager.getTraitData(false, feedbackData.getFromStepData(), interviewers, null);
				}

				AppointmentData appointmentData = selectionProcessManager.getAppointmentDataForFeedback(communicationId);

				
				interviewers = removeInterviewersNotSelected(interviewers, selectionProcessForm.getInterviewerIds());
				reportData = selectionProcessManager.getFeedbackFormViewList(interviewers, feedbackData, traitData, applicantData, appointmentData, selectionProcessForm.getReportFormat(), selectionProcessForm.getReportType(), feedbackType, showIfExist, permissionSet);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return reportData;
	}
	private HashMap<String,String> getParamsToPrintFeedback(PermissionSet permissionSet) {
		
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("candidate_name", TPLabels.getLabel("feedback_form_report.label.candidate_name"));
		params.put("interviewer_name", TPLabels.getLabel("feedback_form_report.label.interviewer_name"));
		params.put("position_name", TPLabels.getLabel("common.position")+" :");		
		params.put("step_name", TPLabels.getLabel("feedback_form_report.label.step_name"));
		if (ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) ){  
			params.put("source_name", TPLabels.getLabel("feedback_form_report.label.source_name"));
		}else{
			params.put("source_name", "");		
		}
		params.put("interview_date", TPLabels.getLabel("feedback_form_report.label.interview_date"));
		params.put("feedback_date", TPLabels.getLabel("feedback_form_report.label.feedback_date"));
		params.put("feedback_by", TPLabels.getLabel("feedback_form_report.label.last_feedback_by"));
		params.put("feedback_result", TPLabels.getLabel("feedback_form_report.label.feedback_result"));
	
		return params; 
	}
	
	public ActionForward previewSalaryStructure(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		try {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
				int gradeId = Integer.parseInt(selectionProcessForm.getGradeId());
				SalaryStructureManager manager = new SalaryStructureManager();
				Map<String, List<SalaryFormulaData>> salaryFormulae = manager.getSalaryFormulaeCategoryWiseForaGrade(gradeId);
				SalaryCalculator calculator = new SalaryCalculator();				
				SalaryStructure salary = calculator.calculateSalary(selectionProcessForm.getGradeId(),selectionProcessForm.getCtcOffered(),selectionProcessForm.getBasicOffered(), selectionProcessForm.getInputSalaryVariable());
				xmlFile = SalaryStructureXMLUtils.getSalaryStructurePreviewXML(salaryFormulae, salary);
			}
		}catch(SalaryCalculationException sce){
			StringBuilder sb = new StringBuilder();
			sb.append("<b>"+sce.getMessage()+"</b><br/>");
			sb.append("<div class=\"navBtn\" style=\"\">" +
					"<a href=\"#\" style=\"float: left;width:60px;\" class=\"active\" onclick=\"javascript: hidePreviewSalary();return false;\"><span class=\"rightC\"></span><span class=\"leftC\"></span>"+TPLabels.getLabel("common.hide")+"</a>" +
							"</div>");
			xmlFile = sb.toString();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			List<String> errors = new ArrayList<String>();
			errors.add(TPLabels.getLabel("selection_feedback.error.invalid_inputs_for_preview"));
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	public ActionForward blackListApplicant(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "blackListApplicant";
		
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_BLACKLIST_APPLICANT;
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, permissions,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		String userId = (String) request.getSession(false).getAttribute("userId");
		ApplicantManager applicantManager = null;
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		try {
			applicantManager = new ApplicantManager();
			if(selectionProcessForm.getBlackListReason()!=null){
				if (!isTokenValid(request)){
					SessionManager.invalidateSession(request, response, userId);
					try {
						SessionManager.sessionExpireRedirect(mapping, actionForm, request, response, this, false);
						TPLogger.getLogger().error("Invalid CSRFToken while blacklisting applicant. Session invalidated because of suspicious activity.");
						return null;
					} catch (Exception e) {
						TPLogger.getLogger().error("Error While validating session", e);
					} 
				}
				String applicantId = selectionProcessForm.getApplicantId();
				String poitionId = selectionProcessForm.getPositionId();
				String blackListreason = selectionProcessForm.getBlackListReason();
				if(ApplicantConstants.APPLICANT_STATUS_BLACKLISTED.equals(selectionProcessForm.getApplicantStatus())){
					boolean unBlackListed = applicantManager.unBlackListApplicant(applicantId,blackListreason,userId);
					if(unBlackListed){
						request.setAttribute("update", "1");
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
					}else{
						errors.add("unBlack_list.error", new ActionError("unBlack_list.error",TPLabels.getLabel("common.applicant")));
					}
				} else if(ApplicantConstants.APPLICANT_STATUS_NORMAL.equals(selectionProcessForm.getApplicantStatus())){
					String clientIpAddr = getClientIpAddr(request);
					boolean blackListed = applicantManager.blackListApplicant(applicantId,poitionId,blackListreason,userId, clientIpAddr);
					AuditAction auditAction = new AuditAction();
					auditAction.insertAuditInfo(TPLabels.getLabel("common.candidate"), AuditConstants.TYPE_BLACKLISTED, 
							applicantId, AuditConstants.AUDIT_CANDIDATE, userId,
							null, null, null, true, clientIpAddr);
					if(blackListed){
						request.setAttribute("update", "1");
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
					}else{
						errors.add("black_list.error", new ActionError("black_list.error",TPLabels.getLabel("common.applicant")));
					}
				}
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			errors.add("black_list.error", new ActionError("black_list.error",TPLabels.getLabel("common.applicant")));
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward viewBlacklistReason(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewBlacklistReason";
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		ApplicantManager applicantManager = null;
		ApplicantBlackListHistoryData apbhd = null;
		ApplicantData applicantData  = null;
		try {
			applicantManager = new ApplicantManager();
			apbhd = applicantManager.getBlackListReason(selectionProcessForm.getCommunicationId());
			if(apbhd!=null){
				// Double check authorization if applicantId is tampered in URL
				if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null, apbhd.getApplicantId(), null, null)) {
					forward = "authorizationFailure";			
					return mapping.findForward(forward);
				}
				applicantData = applicantManager.getApplicantSummaryData(String.valueOf(apbhd.getAttribute("applicantId")));
			}
			request.setAttribute("blackListData", apbhd);
			request.setAttribute("applicantData", applicantData);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error view phone interaction", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward checkStepUsersActionIsPending(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		String users = null;
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			
			SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			
			String communicationId = selectionProcessForm.getCommunicationId();
			String applicantId = selectionProcessForm.getApplicantId();
			String currentStepId = selectionProcessForm.getCurrentPositionStepId();
			
			users = selectionProcessManager.getUsersWithPendingAction(communicationId,applicantId,currentStepId,userId);	
			
			ArrayList<String> errors = null;
			if (users!=null) {
				errors = new ArrayList<String>();
				errors.add(users);
			}
			xmlFile = Utils.getXMLForError(errors);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}
	
	/**
	 * Processes traitData from request. 
	 * <p>
	 * For eg: If traitIds is <code>3719_25|0__, 3744_25|0__, 3745_25|1__CurrentLocation</code> then 
	 * 1. Splits with delimiter ',' which results in a array <br> 
	 * <code>
	 *&nbsp; 3719_25|0__ <br>
	 *&nbsp; 3744_25|0__ <br>
	 *&nbsp; 3745_25|1__CurrentLocation <br>
	 * </code>
	 * 2. Then each element in array is again split using delimiter '|' which results in array of two elements <br>
	 * <code>
	 *&nbsp; 3745_25 <br>
	 *&nbsp; 1__CurrentLocation <br>
	 * </code>
	 * 3. Now the second element of above array is split with delimiter '__' which results in array of two elements <br>
	 * <code>
	 *&nbsp; 1 <br>
	 *&nbsp; CurrentLocation <br> 
	 * </code>
	 * </p>
	 * @param traitIds
	 * @param traitData
	 * @param ratingsIdValues
	 * @param multipleSelectIdValues
	 * @param applicantFieldValueMap
	 * @param request
	 * @throws ArrayIndexOutOfBoundsException
	 */
	private void processTraitDataFromRequest(String traitIds,Map traitData,HashMap<String, String> ratingsIdValues,HashMap<String, String> multipleSelectIdValues,Map<String,String> applicantFieldValueMap,HttpServletRequest request) throws ArrayIndexOutOfBoundsException{
		String traitId = null;
		String feedbackFieldType = null;
		String applicantFieldId = null;
		String feedback = null;
		String[] traits = null;
		String[] ffType_applicantFieldId = null;
		int indx = 0;
		try {
			if (traitIds != null && traitIds.length() > 0) {
				traits = traitIds.split(SelectionProcessConstants.COMMA);
				for (indx = 0; indx < traits.length; indx++) {
					traitId = traits[indx].split("\\"+SelectionProcessConstants.SINGLE_PIPE)[0];
					ffType_applicantFieldId = traits[indx].split("\\"+SelectionProcessConstants.SINGLE_PIPE)[1].split(SelectionProcessConstants.DOUBLE_UNDERSCORE);
					if(ffType_applicantFieldId!=null && ffType_applicantFieldId.length>0){
						feedbackFieldType = ffType_applicantFieldId[0];	
					}else{
						feedbackFieldType = FeedbackFieldsConstant.FIELD_TYPE_NORMAL;
					}
					
					if(FeedbackFieldsConstant.FIELD_TYPE_APPLICANT.equals(feedbackFieldType)){
						applicantFieldId = ffType_applicantFieldId[1];
						feedback = request.getParameter(SelectionProcessConstants.TRAIT_ + traitId);
						traitData.put(traitId, feedback);
						if(!Utils.isBlankOrNull(feedback))
							applicantFieldValueMap.put(applicantFieldId, feedback);
					}else {
						feedback = request.getParameter(SelectionProcessConstants.TRAIT_ + traitId);
						traitData.put(traitId, feedback);
						String ratingFieldId = (String) request.getParameter(SelectionProcessConstants.RATING_ + traitId);
						if (!Utils.isBlankOrNull(ratingFieldId)) {
							ratingsIdValues.put(traitId, ratingFieldId);
							if (traitData.get(traitId) == null) {
								traitData.put(traitId, null);
							}
						}
						String multipleSelectFieldId = (String) request.getParameter(SelectionProcessConstants.MULTIPLE_SELECT_ + traitId);
						if (!Utils.isBlankOrNull(multipleSelectFieldId)) {
							multipleSelectIdValues.put(traitId, multipleSelectFieldId);
							if (traitData.get(traitId) == null) {
								traitData.put(traitId, null);
							}
						}
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException aioob) {
			TPLogger.getLogger().error("Problem occured while processing Trait: "+traits[indx], aioob);
			throw aioob;
		}
	}

	private boolean printFeedbackOnLoad(String feedbackType){
		if(SelectionProcessConstants.SUMMARY_FEEDBACK.equals(feedbackType)){
			return false;
		}else if(SelectionProcessConstants.DETAILED_FEEDBACK.equals(feedbackType)){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * Applicant Fields are validated in this method. 
	 * This method is equivalent to validateForm method in {@link ApplicantAction}. 
	 * Make Sure any additional validations in this method should be in Sync with validateForm method in {@link ApplicantAction}.
	 * @param errors
	 * @param request
	 */
	private void validateApplicantFields(ActionErrors errors,Map<String,String> applicantFieldValueMap){
		ApplicantValidators.validateApplicantEmail(errors, applicantFieldValueMap.get(ImportConfigurationConstants.FIELD_EMAIL1), applicantFieldValueMap.get(ImportConfigurationConstants.FIELD_EMAIL2));
	}
	
	/**
	 * SelectionProcessForm is validated in this method while saving Selection process. 
	 * This method is equivalent to validateForm method in {@link ApplicantAction}. 
	 * Make Sure any additional validations in this method should be in Sync with validateForm method in {@link ApplicantAction}.
	 * @param errors
	 * @param request
	 */
	private void validateSelectionProcessForm(ActionErrors errors, SelectionProcessForm selectionProcessForm){
		ApplicantValidators.validateApplicantInputSalaryVariable(errors, selectionProcessForm.getInputSalaryVariable());
	}
	
	private void processCustomFieldsValues(ApplicantData aData){
		ArrayList<CustomFieldData> customFields = null;
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
			customFieldDataProcessor.setCustomFieldMapFromPreviousValues(customFields, aData.getCustomFieldsMap());
		}
	}
	
	public ActionForward viewOfferDetailsModifiedInteraction(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "viewOfferDetailsModifiedInteraction";
		if(!isUserAuthorized(request, CommonConstants.NO_MODULE, null,((SelectionProcessForm) actionForm).getApplicantId(),null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		SelectionProcessForm selectionProcessForm = (SelectionProcessForm) actionForm;
		CommunicationManager cm = new CommunicationManager();
		OfferDetailsModifiedInteractionData odmi = null; 
		try {
			odmi = cm.getOfferProposalInteractionData(selectionProcessForm.getCommunicationId());
			if(odmi!=null && !isUserAuthorized(request, CommonConstants.NO_MODULE, null, odmi.getApplicantId(), null, null)) {
				forward = "authorizationFailure";			
				return mapping.findForward(forward);
			}
			request.setAttribute("offerDetailsModifiedInteractionData", odmi);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error view offer proposal interaction interaction", e);
		}
		return mapping.findForward(forward);
	}
}
