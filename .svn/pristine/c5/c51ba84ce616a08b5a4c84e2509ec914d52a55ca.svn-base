//Class For Step
function Step(){
	this.index=0;
	this.stepId=0;
	this.stepMasterId=0;
	this.stepTitle='';
	this.isDefault=0;
	this.assignedTo='';
	this.assignedToUsers='';
	this.isScheduled=0;
	this.scheduledBy='';
	this.scheduledByUsers='';
	this.isInterviewerCanConfirm=0;
	this.isDecisionMakerSameAsAssignedTo=0;	
	this.decisionMaker='';
	this.decisionMakerUsers='';
	this.isOptional=0;
	this.stepLevel=0;
	this.messages=new Array();
	this.feedbackFormId='-1';
	this.applicantFeedbackFormId='-1';
	this.isNotifyToCandidate=0;
	
	this.setIndex=setIndex;
	this.setStepId=setStepId;
	this.setStepMasterId=setStepMasterId;
	this.setStepTitle=setStepTitle;
	this.setIsDefault=setIsDefault;
	this.setAssignedTo=setAssignedTo;
	this.setAssignedToUsers=setAssignedToUsers;
	this.setIsScheduled=setIsScheduled;
	this.setScheduledBy=setScheduledBy;
	this.setScheduledByUsers=setScheduledByUsers;
	this.setIsInterviewerCanConfirm=setIsInterviewerCanConfirm;
	this.setIsDecisionMakerSameAsAssignedTo=setIsDecisionMakerSameAsAssignedTo;	
	this.setDecisionMaker=setDecisionMaker;
	this.setDecisionMakerUsers=setDecisionMakerUsers;
	this.setIsOptional=setIsOptional;
	this.setStepLevel=setStepLevel;
	this.setFeedbackFormId=setFeedbackFormId;
	this.setApplicantFeedbackFormId=setApplicantFeedbackFormId;
	this.setMessages=setMessages;
	this.setIsNotifyToCandidate=setIsNotifyToCandidate;
	this.toString=toString;
}

function setIndex(index){this.index=index;}
function setStepId(stepId){this.stepId=stepId;}
function setStepMasterId(stepMasterId){this.stepMasterId=stepMasterId;}
function setStepTitle(stepTitle){this.stepTitle=stepTitle;}
function setIsDefault(isDefault){this.isDefault=isDefault;}
function setAssignedTo(assignedTo){this.assignedTo=assignedTo}
function setAssignedToUsers(assignedToUsers){this.assignedToUsers=assignedToUsers}
function setIsScheduled(isScheduled){this.isScheduled=isScheduled}
function setScheduledBy(scheduledBy){this.scheduledBy=scheduledBy}
function setScheduledByUsers(scheduledByUsers){this.scheduledByUsers=scheduledByUsers}
function setIsInterviewerCanConfirm(isInterviewerCanConfirm){this.isInterviewerCanConfirm=isInterviewerCanConfirm}
function setIsDecisionMakerSameAsAssignedTo(isDecisionMakerSameAsAssignedTo){this.isDecisionMakerSameAsAssignedTo=isDecisionMakerSameAsAssignedTo}
function setDecisionMaker(decisionMaker){this.decisionMaker=decisionMaker}
function setDecisionMakerUsers(decisionMakerUsers){this.decisionMakerUsers=decisionMakerUsers}
function setIsOptional(isOptional){this.isOptional=isOptional}
function setStepLevel(stepLevel){this.stepLevel=stepLevel}
function setFeedbackFormId(feedbackFormId){this.feedbackFormId=feedbackFormId}
function setApplicantFeedbackFormId(applicantFeedbackFormId){this.applicantFeedbackFormId=applicantFeedbackFormId}
function setMessages(messages){this.messages=messages}
function setIsNotifyToCandidate(isNotifyToCandidate){this.isNotifyToCandidate=isNotifyToCandidate}
function toString(){
	var str = "";
	str += formatString(this.stepId) + "|";
	str += formatString(this.stepTitle) + "|";
	str += formatString(this.isDefault) + "|";
	str += formatString(this.assignedTo) + "|";
	str += formatString(this.isScheduled) + "|";
	if (this.scheduledBy != null) {
		str += formatString(this.scheduledBy) + "|";
	} else {
		str += '' + "|";
	}
	str += formatString(this.isInterviewerCanConfirm) + "|";	
	str += formatString(this.isOptional) + "|";
	str += formatString(this.stepLevel) + "|";
	str += formatString(this.feedbackFormId) + "|";
	if (this.messages != null) {
		msgs = " ";
		for (i = 0; i < this.messages.length; i++) {
			if (msgs.length > 0) {
				msgs += ":";
			}
			msgs += formatString(this.messages[i].messageId) + "_";
			msgs += formatString(this.messages[i].message) + "_";
			msgs += formatString(this.messages[i].isDefault);
		}
		str += msgs + "|";
	}
	str += (this.index + 1) + "|";
	str += formatString(this.decisionMaker)+ "|";
	str += formatString(this.isDecisionMakerSameAsAssignedTo) + "|";
	str += formatString(this.isNotifyToCandidate)+ "|";
	str += formatString(this.assignedToUsers) + "|";
	str += formatString(this.scheduledByUsers) + "|";
	str += formatString(this.decisionMakerUsers) + "|";
	str += formatString(this.stepMasterId)+ "|";	
	str += formatString(this.applicantFeedbackFormId);
	return str;
}