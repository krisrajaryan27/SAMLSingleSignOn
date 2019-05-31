package com.talentPool.selectionProcess.dataobject;

import java.util.Date;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.dataobject.StepData;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class FeedbackData extends SimpleDataObject {
	/**
	 * @return Returns the applicantId.
	 */
	public int getApplicantId() {
		return getInt("applicantId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(int applicantId) {
		setAttribute("applicantId", new Integer(applicantId));
	}

	/**
	 * @return Returns the applicantName.
	 */
	public String getApplicantName() {
		return getString("applicantName");
	}

	/**
	 * @return Returns the applicantSourceTitle.
	 */
	public String getApplicantSourceTitle() {
		return getString("applicantSourceTitle");
	}

	/**
	 * @param applicantSourceTitle
	 *            The applicantSourceTitle to set.
	 */
	public void setApplicantSourceTitle(String applicantSourceTitle) {
		setAttribute("applicantSourceTitle", applicantSourceTitle);
	}

	/**
	 * @return Returns the applicantOriginalResumePath.
	 */
	public String getApplicantOriginalResumePath() {
		return getString("applicantOriginalResumePath");
	}

	/**
	 * @param applicantOriginalResumePath
	 *            The applicantOriginalResumePath to set.
	 */
	public void setApplicantOriginalResumePath(String applicantOriginalResumePath) {
		setAttribute("applicantOriginalResumePath", applicantOriginalResumePath);
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}

	public String getPositionId() {
		return getId("positionId");
	}

	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}

	public String getPositionTitle() {
		return getString("positionTitle");
	}

	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}
	
	// public String getProcessComment() {
	// return getString("processComment");
	// }

	// public void setProcessComment(String processComment) {
	// setAttribute("processComment", processComment);
	// }

	public String getUserId() {
		return getId("userId");
	}

	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	public StepData getFromStepData() {
		return (StepData) getAttribute("fromStepData");
	}

	public void setFromStepData(StepData fromStepData) {
		setAttribute("fromStepData", fromStepData);
	}

	public StepData getToStepData() {
		return (StepData) getAttribute("toStepData");
	}

	public void setToStepData(StepData toStepData) {
		setAttribute("toStepData", toStepData);
	}

	/**
	 * @return Returns the nextSteps.
	 */
	public List getNextSteps() {
		return (List) getAttribute("nextSteps");
	}

	/**
	 * @param nextSteps
	 *            The nextSteps to set.
	 */
	public void setNextSteps(List nextSteps) {
		setAttribute("nextSteps", nextSteps);
	}

	/**
	 * @return Returns the repeatStep.
	 */
	public int getRepeatStep() {
		return getInt("repeatStep");
	}

	/**
	 * 
	 * @param repeatStep
	 *            The repeatStep to set.
	 */
	public void setRepeatStep(int repeatStep) {
		setAttribute("repeatStep", new Integer(repeatStep));
	}

	/**
	 * @return the joiningDate
	 */
	public java.sql.Date getJoiningDate() {
		try{
			return getDate("joiningDate");	
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error while getting sql joiningDate: returning null", e);
		}
		return null;
	}
	
	/**
	 * @return the joiningDate to display in System Date Format
	 */
	public String getJoiningDateToDisplay() {
		return DateUtils.getSystemDateFormat(getJoiningDate());
	}
	
	

	/**
	 * @param joiningDate
	 *            the joiningDate to set
	 */
	public void setJoiningDate(java.sql.Date joiningDate) {
		setAttribute("joiningDate", joiningDate);
	}

	/**
	 * @return getString("the feedbackFormHeader
	 */
	public String getFeedbackFormHeader() {
		return getString("feedbackFormHeader");
	}

	/**
	 * @param feedbackFormHeader
	 *            the feedbackFormHeader to set
	 */
	public void setFeedbackFormHeader(String feedbackFormHeader) {
		setAttribute("feedbackFormHeader", feedbackFormHeader);
	}

	/**
	 * @return getString("the feedbackFormTitle
	 */
	public String getFeedbackFormTitle() {
		return getString("feedbackFormTitle");
	}

	/**
	 * @param feedbackFormTitle
	 *            the feedbackFormTitle to set
	 */
	public void setFeedbackFormTitle(String feedbackFormTitle) {
		setAttribute("feedbackFormTitle", feedbackFormTitle);
	}

	public void setProcessMovedDate(Date processMovedDate) {
		setAttribute("processMovedDate", processMovedDate);
	}

	public Date getProcessMovedDate() {
		return getDate("processMovedDate");
	}

	public String getFeedbackResultInStringFormat() {
		StringBuffer sb = new StringBuffer();
		sb.append("Moved to ");
		StepData toStepData = getToStepData();
		if (toStepData != null) {
			String toStepId = "" + toStepData.getStepId();
			if (!Utils.isBlankOrNull(toStepId)) {
				if (toStepId.equals(SelectionProcessConstants.STEP_REJECT)) {
					sb.append(TPLabels.getLabel("selection_feedback.label.rejected"));
				} else if (toStepId.equals(SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT)) {
					sb.append(TPLabels.getLabel("selection_feedback.label.notInterestedReject"));
				} else if (toStepId.equals(SelectionProcessConstants.STEP_ATTENDED)) {
					sb.append(TPLabels.getLabel("selection_feedback.label.attended"));
				} else if (toStepId.equals(SelectionProcessConstants.STEP_NOT_ATTENDED)) {
					sb.append(TPLabels.getLabel("selection_feedback.label.do_not_attended"));
				} else if (toStepId.equals(SelectionProcessConstants.STEP_ON_HOLD)) {
					sb.append(TPLabels.getLabel("selection_feedback.label.onHold"));
				} else if (toStepId.equals(SelectionProcessConstants.STEP_REPEAT)) {
					sb.append(TPLabels.getLabel("selection_feedback.label.repeatStep"));
				} else if (toStepId.equals(SelectionProcessConstants.STEP_JOIN)) {
					sb.append(TPLabels.getLabel("selection_feedback.label.joined"));
				} else if (toStepId.equals(SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT)) {
					sb.append(TPLabels.getLabel("common.position")+" "+TPLabels.getLabel("common.closed"));
				}
				if (!Utils.isBlankOrNull(toStepData.getStepTitle())) {
				sb.append(" " + toStepData.getStepTitle());
				}
				
			}
		}
		return sb.toString();
	}

	public void setAttendeesList(String attendeesList) {
		setAttribute("attendeesList", attendeesList);
	}

	public String getAttendeesList() {
		return getString("attendeesList");
	}
	
	public String getCtcOffered() {
		return getString("ctcOffered");
	}

	public void setCtcOffered(String ctcOffered) {
		setAttribute("ctcOffered", ctcOffered);
	}
	
	public String getBasicOffered() {
		return getString("basicOffered");
	}

	public void setBasicOffered(String basicOffered) {
		setAttribute("basicOffered", basicOffered);
	}
	
	public String getLevelOffered() {
		return getString("levelOffered");
	}

	public void setLevelOffered(String levelOffered) {
		setAttribute("levelOffered", levelOffered);
	}
	
	public String getDesignationOffered() {
		return getString("designationOffered");
	}

	public void setDesignationOffered(String designationOffered) {
		setAttribute("designationOffered", designationOffered);
	}
	
	public String getInputSalaryVariable() {
		return getString("inputSalaryVariable");
	}

	public void setInputSalaryVariable(String inputSalaryVariable) {
		setAttribute("inputSalaryVariable", inputSalaryVariable);
	}
	
	/**
	 * @return the lastFeedbackBy
	 */
	public String getLastFeedbackBy() {
		return getString("lastFeedbackBy");
	}

	/**
	 * @param lastFeedbackBy the lastFeedbackBy to set
	 */
	public void setLastFeedbackBy(String lastFeedbackBy) {
		setAttribute("lastFeedbackBy", lastFeedbackBy);
	}

	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return getString("employeeCode");
	}

	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		setAttribute("employeeCode", employeeCode);
	}	
	
	/**
	 * @return the gradeId
	 */
	public String getGradeId() {
		return getString("gradeId");
	}

	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(String gradeId) {
		setAttribute("gradeId", gradeId);
	}
	
	/**
	 * @return the gradeName
	 */
	public String getGradeName() {
		return getString("gradeName");
	}

	/**
	 * @param gradeName the gradeName to set
	 */
	public void setGradeName(String gradeName) {
		setAttribute("gradeName", gradeName);
	}
	
	/**
	 * @return getString("the feedbackFormId
	 */
	public String getFeedbackFormId() {
		return getString("feedbackFormId");
	}

	/**
	 * @param feedbackFormId
	 *            the feedbackFormId to set
	 */
	public void setFeedbackFormId(String feedbackFormId) {
		setAttribute("feedbackFormId", feedbackFormId);
	}
	
	/**
	 * @return the joiningBonus
	 */
	public String getJoiningBonus() {
		return getString("joiningBonus");
	}

	/**
	 * @param joiningBonus the joiningBonus to set
	 */
	public void setJoiningBonus(String joiningBonus) {
		setAttribute("joiningBonus", joiningBonus);
	}	
	
	/**
	 * @return the variableOffered
	 */
	public String getVariableOffered() {
		return getString("variableOffered");
	}

	/**
	 * @param variableOffered the variableOffered to set
	 */
	public void setVariableOffered(String variableOffered) {
		setAttribute("variableOffered", variableOffered);
	}	

}
