/**
 * 
 */
package com.talentPool.positions.dataobject;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class StepData extends SimpleDataObject {

	public StepData() {

	}

	public StepData(int stepId, String stepTitle) {
		setStepId(stepId);
		setStepTitle(stepTitle);
	}

	public StepData(int stepId, String stepTitle, String stepLevel) {
		setStepId(stepId);
		setStepTitle(stepTitle);
		setStepLevel(stepLevel);
	}
	
	/**
	 * @return Returns the stepTypeId.
	 */
	public int getStepTypeId() {
		return getInt("stepTypeId");
	}

	/**
	 * @param stepTypeId
	 *            The stepTypeId to set.
	 */
	public void setStepTypeId(int stepTypeId) {
		setAttribute("stepTypeId", new Integer(stepTypeId));
	}

	/**
	 * @return Returns the stepId.
	 */
	public int getStepId() {
		return getInt("stepId");
	}

	/**
	 * @param stepId
	 *            The stepId to set.
	 */
	public void setStepId(int stepId) {
		setAttribute("stepId", new Integer(stepId));
	}

	/**
	 * @return Returns the stepRank.
	 */
	public int getStepRank() {
		return getInt("stepRank");
	}

	/**
	 * @param stepRank
	 *            The stepRank to set.
	 */
	public void setStepRank(int stepRank) {
		setAttribute("stepRank", new Integer(stepRank));
	}

	/**
	 * @return Returns the stepTitle.
	 */
	public String getStepTitle() {
		return getString("stepTitle");
	}

	/**
	 * @param stepTitle
	 *            The stepTitle to set.
	 */
	public void setStepTitle(String stepTitle) {
		setAttribute("stepTitle", stepTitle);
	}

	/**
	 * @return Returns the stepTypeTitle.
	 */
	public String getStepTypeTitle() {
		return getString("stepTypeTitle");
	}

	/**
	 * @param stepTypeTitle
	 *            The stepTypeTitle to set.
	 */
	public void setStepTypeTitle(String stepTypeTitle) {
		setAttribute("stepTypeTitle", stepTypeTitle);

	}

	public ArrayList<TraitData> getTraits() {
		return (ArrayList<TraitData>) getAttribute("traits");
	}

	public void setTraits(ArrayList<TraitData> traits) {
		setAttribute("traits", traits);
	}

	public String getPositionId() {
		return getId("positionId");
	}

	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}

	public void setDefaultFlag(String defaultStep) {
		setAttribute("defaultStep", defaultStep);
	}

	public String getDefaultFlag() {
		return getString("defaultStep");
	}

	public void setNoOfApplicants(int noOfApplicants) {
		setAttribute("noOfApplicants", new Integer(noOfApplicants));
	}

	public int getNoOfApplicants() {
		return getInt("noOfApplicants");
	}
	
	/**
	 * @return Returns the stepOptional.
	 */
	public int getStepOptional() {
		return getInt("stepOptional");
	}
	
	/**
	 * @param stepOptional
	 * 			The stepOptional to set.
	 */
	public void setStepOptional(int stepOptional) {
		setAttribute("stepOptional", new Integer(stepOptional));
	}
	
	/**
	 * @return Returns the stepScheduled.
	 */
	public int getStepScheduled() {
		return getInt("stepScheduled");
	}
	
	/**
	 * @param stepScheduled
	 * 			The stepScheduled to set.
	 */
	public void setStepScheduled(int stepScheduled) {
		setAttribute("stepScheduled", new Integer(stepScheduled));
	}
	
	/**
	 * @return Returns the usersResponsibleToCarryOutStep.
	 */
	public String getUsersResponsibleToCarryOutStep() {
		return getString("usersResponsibleToCarryOutStep");
	}

	/**
	 * @param usersResponsibleToCarryOutStep
	 *            The usersResponsibleToCarryOutStep to set.
	 */
	public void setUsersResponsibleToCarryOutStep(String usersResponsibleToCarryOutStep) {
		setAttribute("usersResponsibleToCarryOutStep", usersResponsibleToCarryOutStep);
	}
	
	/**
	 * @return Returns the usersResponsibleToScheduleStep.
	 */
	public String getUsersResponsibleToScheduleStep() {
		return getString("usersResponsibleToScheduleStep");
	}

	/**
	 * @param usersResponsibleToScheduleStep
	 *            The usersResponsibleToScheduleStep to set.
	 */
	public void setUsersResponsibleToScheduleStep(String usersResponsibleToScheduleStep) {
		setAttribute("usersResponsibleToScheduleStep", usersResponsibleToScheduleStep);
	}
	
	/**
	 * @return Returns the statusMessages.
	 */
	public List getStatusMessages() {
		return (List)getAttribute("statusMessages");
	}
	
	/**
	 * @param statusMessages
	 * 			The statusMessages to set.
	 */
	public void setStatusMessages(List statusMessages) {
		setAttribute("statusMessages", statusMessages);
	}
	
	/**
	 * @return Returns the stepLevel.
	 */
	public String getStepLevel() {
		return getString("stepLevel");
	}

	/**
	 * @param stepLevel 
	 * 			The stepLevel to set.
	 */
	public void setStepLevel(String stepLevel) {
		setAttribute("stepLevel", stepLevel);
	}
	
	public int getSendNotificationToCandidate() {
		return getInt("sendNotificationToCandidate");
	}
		
	public void setSendNotificationToCandidate(int sendNotificationToCandidate) {
		setAttribute("sendNotificationToCandidate", new Integer(sendNotificationToCandidate));
	}
	
	public int getStepStatus() {
		return getInt("stepStatus");
	}
		
	public void setStepStatus(int stepStatus) {
		setAttribute("stepStatus", new Integer(stepStatus));
	}

	public String getstepDescription() {
		return getString("stepDescription");
	}
		
	public void setstepDescription(String stepDescription) {
		setAttribute("stepDescription",stepDescription);
	}
	
	public int getStepInterviewerConfirm() {
		return getInt("stepInterviewerConfirm");
	}

	public void setStepInterviewerConfirm(int stepInterviewerConfirm) {
		setAttribute("stepInterviewerConfirm", new Integer(stepInterviewerConfirm));
	}
	
	public int getDeedbackFormId() {
		return getInt("feedbackFormId");
	}
	
	public void setFeedbackFormId(int feedbackFormId) {
		setAttribute("feedbackFormId", new Integer(feedbackFormId));
	}
	
	public int getStepMakerSameAsAsgnTo() {
		return getInt("stepMakerSameAsAsgnTo");
	}
	
	public void setStepMakerSameAsAsgnTo(int stepMakerSameAsAsgnTo) {
		setAttribute("stepMakerSameAsAsgnTo", new Integer(stepMakerSameAsAsgnTo));
	}
	

	public int getStepNotifyToCandidate() {
		return getInt("stepNotifyToCandidate");
	}
	
	public void setStepNotifyToCandidate(int stepNotifyToCandidate) {
		setAttribute("stepNotifyToCandidate", new Integer(stepNotifyToCandidate));
	}
	
	/**
	 * @return Returns the stepMasterId.
	 */
	public int getStepMasterId() {
		return getInt("stepMasterId");
	}

	/**
	 * @param stepMasterId
	 *            The stepMasterId to set.
	 */
	public void setStepMasterId(int stepMasterId) {
		setAttribute("stepMasterId", new Integer(stepMasterId));
	}	
}